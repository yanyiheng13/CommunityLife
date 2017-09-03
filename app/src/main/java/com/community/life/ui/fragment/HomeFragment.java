package com.community.life.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.community.life.R;
import com.community.life.model.AddressData;
import com.community.life.model.HomeData;
import com.community.life.mvp.HomePresenter;
import com.community.life.mvp.contract.HomeContract;
import com.community.life.ui.AddressListActivity;
import com.community.life.ui.AnnouncementActivity;
import com.community.life.ui.BaseFragment;
import com.community.life.ui.view.LoadingDialog;
import com.community.life.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能说明： 首页  也就是第三个tab
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-8-24
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {
    @BindView(R.id.home_top_img)
    ImageView mImageTop;

    @BindView(R.id.home_announcement_more_rl)
    RelativeLayout mRlMore;

    @BindView(R.id.home_announcement_brief_ll)
    LinearLayout mRlBrief;

    //三条公告的父布局 当公告显示个数低于三条的话控制隐藏
    @BindView(R.id.home_announcement_one_ll)
    LinearLayout mLlBriefOne;
    @BindView(R.id.home_announcement_two_ll)
    LinearLayout mLlBriefTwo;
    @BindView(R.id.home_announcement_three_ll)
    LinearLayout mLlBriefThree;

    //三条公告
    @BindView(R.id.home_announcement_one_tv)
    TextView mTvBriefOne;
    @BindView(R.id.home_announcement_two_tv)
    TextView mTvBriefTwo;
    @BindView(R.id.home_announcement_three_tv)
    TextView mTvBriefThree;

    @BindView(R.id.home_top_address_tv)
    TextView mTvTopAddress;

    private AddressData mAddress = new AddressData();

    @Override
    public int inflateId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //获取屏幕宽高
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        //设置顶部图片的宽高
        if (mImageTop.getLayoutParams() != null) {
            mImageTop.getLayoutParams().height = (int)(1161 / 1620.0 * width);
        }
        if (mRlMore.getLayoutParams() != null) {
            mRlMore.getLayoutParams().height = (int)(161 / 1620.0 * width);
        }
        if (mRlBrief.getLayoutParams() != null) {
            mRlBrief.getLayoutParams().height = (int)(758 / 1620.0 * width);
        }
    }

    @OnClick({R.id.home_announcement_more_rl, R.id.home_announcement_key_img, R.id.home_top_key_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_announcement_more_rl:
                AnnouncementActivity.newIntent(getContext());
                break;
            case R.id.home_announcement_key_img:
                break;
            case R.id.home_top_key_img:
                AddressListActivity.newIntent(getActivity(), mAddress, 100);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            mAddress = (AddressData) data.getSerializableExtra(AddressListActivity.TAG_REQUEST_HOME);
            if (mAddress != null) {
                mTvTopAddress.setText(mAddress.address);
            }
        }
    }

    @Override
    public void onSuccessWork(HomeData homeData) {

    }

    @Override
    public void onFailureWork(String code, String msg) {

    }
}
