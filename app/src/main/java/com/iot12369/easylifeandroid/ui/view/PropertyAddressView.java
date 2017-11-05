package com.iot12369.easylifeandroid.ui.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.AddressData;
import com.iot12369.easylifeandroid.model.AddressVo;
import com.iot12369.easylifeandroid.model.FamilVo;
import com.iot12369.easylifeandroid.model.FamilyData;
import com.iot12369.easylifeandroid.ui.AddAddressActivity;
import com.iot12369.easylifeandroid.util.ToastUtil;

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
        List<AddressVo> list = addressData.list;
        if (list == null || list.size() == 0) {
            return this;
        }
        if (isAlreadyCertification(addressData)) {
            if (!isMyAddress) {
                AddressVo addressVo = getCurrentAddress(addressData);
                if (addressVo == null) {
                    return this;
                }
                mTvAddress.setText(addressVo.communityRawAddress  + "\n" + addressVo.communityName);
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
                View view = mInflater.inflate(R.layout.view_property_address_item, null);
                TextView tv = (TextView) view.findViewById(R.id.property_address_item_tv);
                ImageView imgStatus = (ImageView) view.findViewById(R.id.property_address_status_img);
                ImageView imgChange = (ImageView) view.findViewById(R.id.property_address_item_img);
                view.setVisibility(GONE);
                imgChange.setVisibility(GONE);
                imgStatus.setVisibility(VISIBLE);
                final AddressVo addressVo = list.get(i);
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                tv.setText(addressVo.communityRawAddress  + "\n" + addressVo.communityName);
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
                    return this;
                }
                View view = mInflater.inflate(R.layout.view_property_address_item, null);
                TextView tv = (TextView) view.findViewById(R.id.property_address_item_tv);
                ImageView imgStatus = (ImageView) view.findViewById(R.id.property_address_status_img);
                view.setVisibility(GONE);
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null ) {
                            mListener.onItemClick(addressVo.memberId);
                        }
                    }
                });

                tv.setText(addressVo.communityRawAddress  + "\n" + addressVo.communityName);
                mListView.add(view);
                mLlAddressContain.addView(view);
            }
        }
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
                isAlready = true;
            }
            break;
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
                address = addressVo;
                break;
            }

        }
        return address;
    }

    public PropertyAddressView updateData(String level, FamilyData familyData) {
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
            final TextView imgDelete = (TextView) view.findViewById(R.id.property_address_delete);//R.mipmap.icon_binded  R.mipmap.icon_not_bind
            final ImageView imgArrow = (ImageView) view.findViewById(R.id.property_arrow_img);
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
                        mPeopleListener.onItemClick(familVo.memberId);
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

    @OnClick({R.id.property_change_arrow_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.property_change_arrow_rl:
                if (!isAlreadyCertification(addressData) && !isMyAddress) {
                    ToastUtil.toast(getContext(), "暂无通过认证的物业");
                    AddAddressActivity.newIntent(getContext());
                    break;
                }
                visible();
                break;
            default:
                break;
        }
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
        void onItemClick(String memberid);
    }
    public void setOnPeopleItemClickListener(OnPeopleItemClickListener listener) {
        mPeopleListener = listener;
    }
}
