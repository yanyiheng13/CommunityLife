package com.community.life.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    public void updateData() {
        mListView.clear();
        mLlAddressContain.removeAllViews();
        for (int i = 0; i < 2; i++) {
            View view = mInflater.inflate(R.layout.view_property_address_item, null);
            TextView tv = (TextView) view.findViewById(R.id.property_address_item_tv);
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

    private void visible() {
        for (View view: mListView) {
            if (mImgArrow.isEnabled()) {
                view.setVisibility(VISIBLE);
            } else {
                view.setVisibility(GONE);
            }
        }
        mImgArrow.setEnabled(!mImgArrow.isEnabled());
    }

    public void goneIcon() {
        mIconImg.setVisibility(View.GONE);
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
