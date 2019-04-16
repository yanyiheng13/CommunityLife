package com.iot12369.easylifeandroid.ui.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.AddressData;
import com.iot12369.easylifeandroid.model.AddressVo;
import com.iot12369.easylifeandroid.model.FamilVo;
import com.iot12369.easylifeandroid.model.FamilyData;
import com.iot12369.easylifeandroid.ui.AddAddressActivity;
import com.iot12369.easylifeandroid.util.SharePrefrenceUtil;
import com.iot12369.easylifeandroid.util.ToastUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能说明：物业地址显示和更改view(公用)
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-27 下午3:46
 * @Copyright (c) 2017. Inc. All rights reserved.
 */
//TODO:下面的几个方法后续整理一下 保留一个
public class PropertyAddressView extends LinearLayout {
    //左边icon
    @BindView(R.id.property_icon_img)
    ImageView mIconImg;
    //右边箭头
    @BindView(R.id.property_change_arrow_img)
    ImageView mImgArrow;
    //左边文字
    @BindView(R.id.property_icon_tv)
    TextView mTvLeft;
    //地址显示
    @BindView(R.id.property_address_tv)
    TextView mTvAddress;
    @BindView(R.id.tv_change_address)
    TextView mTvText;
    //地址容器
    @BindView(R.id.property_address_content_ll)
    LinearLayout mLlAddressContain;
    //地址容器
    @BindView(R.id.mine_account_property_rl)
    RelativeLayout mRlGroup;
    @BindView(R.id.property_change_arrow_rl)
    RelativeLayout mRlArrowBg;
    //布局载入器
    private LayoutInflater mInflater;

    //itemView列表
    private List<View> mListView = new ArrayList<>();

    AddressData addressData;
    boolean isMyAddress;



    public PropertyAddressView(Context context) {
        this(context, null);
    }

    public PropertyAddressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PropertyAddressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_property_address, this);
        ButterKnife.bind(this, this);
        mInflater = LayoutInflater.from(context);
        mRlArrowBg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAlreadyCertification(addressData) && !isMyAddress) {
                    ToastUtil.toast(getContext(), "暂无通过认证的物业");
                    AddAddressActivity.newIntent(getContext());
                    return;
                }
                visible();
            }
        });
    }

    public PropertyAddressView updateData() {
        mListView.clear();
        mLlAddressContain.removeAllViews();
        for (int i = 0; i < 2; i++) {
            View view = mInflater.inflate(R.layout.view_property_address_item, null);
            TextView tv = (TextView) view.findViewById(R.id.property_address_item_tv);
            ImageView imgStatus = (ImageView) view.findViewById(R.id.property_address_status_img);
            view.setVisibility(GONE);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            mListView.add(view);
            mLlAddressContain.addView(view);
        }
        return this;
    }

    public PropertyAddressView updateData(final AddressData addressData, boolean isMyAddress) {
        mListView.clear();
        mLlAddressContain.removeAllViews();
        this.isMyAddress = isMyAddress;
        this.addressData = addressData;
        mImgArrow.setEnabled(true);
        List<AddressVo> list = addressData.list;
        if (list == null || list.size() == 0) {
            return this;
        }
        AddressVo currentAddressVo = null;
        if (isAlreadyCertification(addressData)) {
            if (!isMyAddress) {
                currentAddressVo = getCurrentAddress(addressData);
                if (currentAddressVo == null) {
                    return this;
                }
                StringBuilder builder = new StringBuilder();
                builder.append(currentAddressVo.communityName);//小区名字
                //兼容老的
                if (!TextUtils.isEmpty(currentAddressVo.communityBuiding) && !"null".equals(currentAddressVo.communityBuiding)) {
                    builder.append(currentAddressVo.communityBuiding);//几号楼
                    builder.append("号楼");//几号楼
                }
                if (!TextUtils.isEmpty(currentAddressVo.communityUnit) && !"null".equals(currentAddressVo.communityUnit)) {
                    builder.append(currentAddressVo.communityUnit);//几门
                    builder.append("门");//几门
                }
                builder.append(currentAddressVo.communityRawAddress);//原始门牌号
                mTvAddress.setText(builder.toString());
            }
        } else {
            if (!isMyAddress) {
                mTvAddress.setText("暂无通过认证的物业");
                return this;
            }
        }
        //下面是我的物业逻辑
        if (isMyAddress) {
            for (int i = 0; i < list.size(); i++) {
                final AddressVo addressVo = list.get(i);
//                if (!isMyAddress currentAddressVo.memberId.equals(addressVo.memberId)) {
//                    continue;
//                }
                View view = mInflater.inflate(R.layout.view_property_address_item, null);
                TextView tv = (TextView) view.findViewById(R.id.property_address_item_tv);
                ImageView imgStatus = (ImageView) view.findViewById(R.id.property_address_status_img);
                ImageView imgChange = (ImageView) view.findViewById(R.id.property_address_item_img);
                tv.setGravity(Gravity.RIGHT);
                view.setVisibility(GONE);
                imgChange.setVisibility(GONE);
                imgStatus.setVisibility(VISIBLE);
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                StringBuilder builder = new StringBuilder();
                builder.append(addressVo.communityName);//小区名字
                //兼容老的
                if (!TextUtils.isEmpty(addressVo.communityBuiding) && !"null".equals(addressVo.communityBuiding)) {
                    builder.append(addressVo.communityBuiding);//几号楼
                    builder.append("号楼");//几号楼
                }
                if (!TextUtils.isEmpty(addressVo.communityUnit) && !"null".equals(addressVo.communityUnit)) {
                    builder.append(addressVo.communityUnit);//几门
                    builder.append("门");//几门
                }
                builder.append(addressVo.communityRawAddress);//原始门牌号
                tv.setText(builder.toString());
                if ("2".equals(addressVo.estateAuditStatus)) {//已认证
                    imgStatus.setImageResource(R.mipmap.icon_certification);
                } else if ("1".equals(addressVo.estateAuditStatus) || "0".equals(addressVo.estateAuditStatus)) {
                    imgStatus.setImageResource(R.mipmap.icon_checking);
                } else if ("3".equals(addressVo.estateAuditStatus)){
                    imgStatus.setImageResource(R.mipmap.icon_pass);
                }
                mListView.add(view);
                mLlAddressContain.addView(view);
            }
        } else {
            for (int i = 0; i < list.size(); i++) {
                final AddressVo addressVo = list.get(i);
                if (addressVo == null || !"2".equals(addressVo.estateAuditStatus)) {
                    continue;
                }
                if (currentAddressVo == null || currentAddressVo.memberId.equals(addressVo.memberId)) {
                    continue;
                }
                View view = mInflater.inflate(R.layout.view_property_address_item, null);
                TextView tv = (TextView) view.findViewById(R.id.property_address_item_tv);
                ImageView imgStatus = (ImageView) view.findViewById(R.id.property_address_status_img);
                view.setVisibility(GONE);
                tv.setGravity(Gravity.RIGHT);
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null ) {
                            mListener.onItemClick(addressVo.memberId);
                        }
                    }
                });
                StringBuilder builder = new StringBuilder();
                builder.append(addressVo.communityName);//小区名字
                //兼容老的
                if (!TextUtils.isEmpty(addressVo.communityBuiding) && !"null".equals(addressVo.communityBuiding)) {
                    builder.append(addressVo.communityBuiding);//几号楼
                    builder.append("号楼");//几号楼
                }
                if (!TextUtils.isEmpty(addressVo.communityUnit) && !"null".equals(addressVo.communityUnit)) {
                    builder.append(addressVo.communityUnit);//几门
                    builder.append("门");//几门
                }
                builder.append(addressVo.communityRawAddress);//原始门牌号
                tv.setText(builder.toString());
                mListView.add(view);
                mLlAddressContain.addView(view);
            }
        }
//        visible();
        return this;
    }

    public void setLeftTextColor(int color) {
        mTvLeft.setTextColor(color);
    }

    public boolean isAlreadyCertification(AddressData addressData) {
        if (addressData == null || addressData.list == null || addressData.list.size() == 0) {
            return false;
        }
        List<AddressVo> list = addressData.list;
        boolean isAlready = false;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            AddressVo addressVo = list.get(i);
            if ("2".equals(addressVo.estateAuditStatus)) {
                if (LeApplication.mAddressVo == null || TextUtils.isEmpty(LeApplication.mAddressVo.communityId)) {
                    SharePrefrenceUtil.setString("config", "communityId", addressVo.communityId);
                }
                isAlready = true;
                break;
            }
        }
        return isAlready;
    }

    public AddressVo getCurrentAddress(AddressData addressData) {
        if (addressData == null || addressData.list == null || addressData.list.size() == 0) {
            return null;
        }
        List<AddressVo> list = addressData.list;
        AddressVo address = null;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            AddressVo addressVo = list.get(i);
            if ("1".equals(addressVo.currentEstate)) {
                if (LeApplication.mAddressVo != null && !TextUtils.isEmpty(LeApplication.mAddressVo.communityId) && !LeApplication.mAddressVo.communityId.equals(addressVo.communityId)) {
                    SharePrefrenceUtil.setString("config", "communityId", addressVo.communityId);
                } else {
                    if (LeApplication.mAddressVo != null) {
                        LeApplication.mAddressVo.communityId = addressVo.communityId;
                        SharePrefrenceUtil.setString("config", "list", new Gson().toJson(addressData));
                    }
                }
                address = addressVo;
                break;
            }

        }
        return address;
    }

    public PropertyAddressView updateData(final String level, FamilyData familyData) {
        if (familyData == null) {
            return this;
        }
        mListView.clear();
        mLlAddressContain.removeAllViews();
        List<FamilVo> list = familyData.list;
        if (list == null || list.size() == 0) {
            return this;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            final FamilVo familVo = list.get(i);
            if (!level.equals(familVo.level)) {
                continue;
            }
            View view = mInflater.inflate(R.layout.view_address_item_two, null);
            TextView tvName = (TextView) view.findViewById(R.id.property_name_tv);
            TextView tvPhone = (TextView) view.findViewById(R.id.property_phone_tv);
            ImageView imgStatus = (ImageView) view.findViewById(R.id.property_address_status_img);//R.mipmap.icon_binded  R.mipmap.icon_not_bind
            LinearLayout llAcceptAndDelete = view.findViewById(R.id.llAcceptAndDelete);//R.mipmap.icon_binded  R.mipmap.icon_not_bind
            final TextView imgDelete = (TextView) view.findViewById(R.id.property_address_delete);//R.mipmap.icon_binded  R.mipmap.icon_not_bind
            final ImageView imgArrow = (ImageView) view.findViewById(R.id.property_arrow_img);
            final ImageView btnAccept = (ImageView) view.findViewById(R.id.btnAccept);
            final ImageView btnRejct = (ImageView) view.findViewById(R.id.btnReject);


            // 当前 是家庭成员的时候
            if ("2".equals(familVo.level)) {
                if ("已授权".equals(familVo.state)) {
                    llAcceptAndDelete.setVisibility(View.GONE);
                    imgArrow.setBackgroundResource(R.mipmap.icon_shouquan);
                } else {
                    llAcceptAndDelete.setVisibility(View.VISIBLE);
                    imgArrow.setBackgroundResource(R.mipmap.icon_noshouquan);
                }
            } else {
                imgArrow.setBackgroundResource(R.mipmap.icon_shouquan);
            }
            tvName.setText(familVo.name);
            tvPhone.setText(familVo.phone);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imgArrow.isEnabled()) {
                        translationX(imgDelete);
                    } else {
                        imgDelete.setVisibility(View.INVISIBLE);
                    }
                    imgArrow.setEnabled(!imgArrow.isEnabled());
                }
            });
            imgDelete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPeopleListener != null) {
                        mPeopleListener.onItemClick(familVo.memberId, familVo.state, level);
                    }
                }
            });
            btnAccept.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPeopleListener != null) {
                        mPeopleListener.onItemAcceptClick(familVo.memberId);
                    }
                }
            });
            btnRejct.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPeopleListener != null) {
                        mPeopleListener.onItemRejectClick(familVo.memberId);
                    }
                }
            });
            mListView.add(view);
            mLlAddressContain.addView(view);
        }
        return this;
    }

    /**
     * 动画显示删除按钮
     */
    public void translationX(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
            return;
        }
        view.setVisibility(View.VISIBLE);
        ObjectAnimator transAnim = ObjectAnimator.ofFloat(view, "alpha", 0.6f, 1f);
        transAnim.setDuration(400);
        transAnim.start();
    }

    private PropertyAddressView visible() {
        for (View view: mListView) {
            if (mImgArrow.isEnabled()) {
                view.setVisibility(VISIBLE);
            } else {
                view.setVisibility(GONE);
            }
        }
        mImgArrow.setEnabled(!mImgArrow.isEnabled());
        return this;
    }

    public PropertyAddressView goneGroup() {
        mRlGroup.setVisibility(View.GONE);
        return this;
    }

    public PropertyAddressView goneIcon() {
        mIconImg.setVisibility(View.GONE);
        return this;
    }

    public PropertyAddressView setText(int id) {
        mTvLeft.setText(id);
        return this;
    }

    public PropertyAddressView setGoneTxt() {
        mRlArrowBg.setBackgroundColor(0xFFFFFFFF);
        mTvText.setVisibility(View.GONE);
        return this;
    }

    public void goneTxt() {
        mTvAddress.setVisibility(GONE);
    }


    public OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(String memberid);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public OnPeopleItemClickListener mPeopleListener;
    public interface OnPeopleItemClickListener {
        void onItemClick(String memberid, String status, String level);
        void onItemAcceptClick(String memberid);
        void onItemRejectClick(String memberid);
    }
    public void setOnPeopleItemClickListener(OnPeopleItemClickListener listener) {
        mPeopleListener = listener;
    }
}
