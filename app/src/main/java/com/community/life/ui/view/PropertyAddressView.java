package com.community.life.ui.view;

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

import com.community.life.R;

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

    public void updateData(boolean isShow) {
        mListView.clear();
        mLlAddressContain.removeAllViews();
        mRlArrowBg.setBackgroundColor(0xFFFFFFFF);
        for (int i = 0; i < 2; i++) {
            View view = mInflater.inflate(R.layout.view_property_address_item, null);
            TextView tv = (TextView) view.findViewById(R.id.property_address_item_tv);
            ImageView imgStatus = (ImageView) view.findViewById(R.id.property_address_status_img);
            if (isShow) { imgStatus.setVisibility(VISIBLE); }
            view.setVisibility(GONE);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            mListView.add(view);
            mLlAddressContain.addView(view);
        }
    }

    public PropertyAddressView updateData(String source) {
        mListView.clear();
        mLlAddressContain.removeAllViews();
        for (int i = 0; i < 2; i++) {
            View view = mInflater.inflate(R.layout.view_address_item_two, null);
            TextView tvName = (TextView) view.findViewById(R.id.property_name_tv);
            TextView tvPhone = (TextView) view.findViewById(R.id.property_phone_tv);
            ImageView imgStatus = (ImageView) view.findViewById(R.id.property_address_status_img);//R.mipmap.icon_binded  R.mipmap.icon_not_bind
            final TextView imgDelete = (TextView) view.findViewById(R.id.property_address_delete);//R.mipmap.icon_binded  R.mipmap.icon_not_bind
            final ImageView imgArrow = (ImageView) view.findViewById(R.id.property_arrow_img);
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

    public void goneTxt() {
        mTvAddress.setVisibility(GONE);
    }

    @OnClick({R.id.property_change_arrow_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.property_change_arrow_rl:
                visible();
                break;
            default:
                break;
        }
    }
}
