package com.iot12369.easylifeandroid.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.AddressData;
import com.iot12369.easylifeandroid.model.AddressVo;
import com.iot12369.easylifeandroid.model.AnnouncementVo;
import com.iot12369.easylifeandroid.model.IsOkData;
import com.iot12369.easylifeandroid.model.LoginData;
import com.iot12369.easylifeandroid.model.NoticeData;
import com.iot12369.easylifeandroid.model.NoticeVo;
import com.iot12369.easylifeandroid.mvp.HomePresenter;
import com.iot12369.easylifeandroid.mvp.contract.HomeContract;
import com.iot12369.easylifeandroid.ui.AddressListActivity;
import com.iot12369.easylifeandroid.ui.AnnouncementActivity;
import com.iot12369.easylifeandroid.ui.BaseFragment;
import com.iot12369.easylifeandroid.ui.view.LockView;
import com.iot12369.easylifeandroid.util.SharePrefrenceUtil;

import java.util.List;

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
    @BindView(R.id.lock_view)
    LockView mLockView;
    private AddressVo mAddress;
    private List<AddressVo> mAddressList;

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
        mLockView.setOnStatusChangeListener(new LockView.OnStatusChangeListener() {
            @Override
            public void onStatusChange(int status) {
                switch (status) {
                    case LockView.STATE_ING:
                        if (mAddress != null) {
                            LoginData data = LeApplication.mUserInfo;
                            getPresenter().lock(mAddress.memberId, data.phone, null);
                        }
                        break;
                }
            }
        });
        getPresenter().homeThreeNotice();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isResumed() && LeApplication.mCurrentTag == LeApplication.TAG_HOME) {
            getPresenter().addressList(LeApplication.mUserInfo.phone);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            getPresenter().addressList(LeApplication.mUserInfo.phone);
        }
    }

    @OnClick({R.id.home_announcement_more_rl, R.id.home_top_key_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_announcement_more_rl:
                AnnouncementActivity.newIntent(getContext());
                break;
            case R.id.home_top_key_img:
                AddressListActivity.newIntent(getActivity(), mAddressList, mAddress, 100);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            mAddress = (AddressVo) data.getSerializableExtra(AddressListActivity.TAG_REQUEST_HOME);
            if (mAddress != null) {
                mTvTopAddress.setText(mAddress.communityRawAddress);
            }
        }
    }

    @Override
    public void onSuccessLock(IsOkData isOkData) {
        if (isOkData.isOk()) {
            mLockView.update(LockView.STATE_SUCCESS);
        } else {
            mLockView.update(LockView.STATE_FAILURE);
        }
    }

    @Override
    public void onFailureLock(String code, String msg) {
        mLockView.update(LockView.STATE_FAILURE);
    }

    @Override
    public void onSuccessAddressList(AddressData addressData) {
        mAddressList = addressData.list;
        mLockView.setAddAdress(mAddressList);
        if (!isAlreadyCertification(addressData)) {
            mTvTopAddress.setText("暂无通过认证的物业");
            return;
        }
        mAddress = getCurrentAddress(addressData);
        LeApplication.mAddressVo = mAddress;
        if (mAddress != null) {
            mTvTopAddress.setText(mAddress.communityRawAddress + mAddress.communityName);
        }
        SharePrefrenceUtil.setString("config", "list", new Gson().toJson(addressData));
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
                address = addressVo;
                break;
            }

        }
        return address;
    }

    @Override
    public void onFailureAddressList(String code, String msg) {

    }

    @Override
    public void onSuccessNoticeData(NoticeData noticeData) {
        if (noticeData.list != null && noticeData.list.size() >=  3) {
            List<AnnouncementVo> list = noticeData.list;
            AnnouncementVo vo1 = list.get(0);
            AnnouncementVo vo2 = list.get(1);
            AnnouncementVo vo3 = list.get(2);

            mTvBriefOne.setText(vo1.noticeTitle);
            mTvBriefTwo.setText(vo2.noticeTitle);
            mTvBriefThree.setText(vo3.noticeTitle);
        }
    }

    @Override
    public void onFailureNoticeData(String code, String msg) {

    }
}
