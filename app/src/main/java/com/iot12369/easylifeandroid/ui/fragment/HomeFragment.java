package com.iot12369.easylifeandroid.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.iot12369.easylifeandroid.model.UpdateData;
import com.iot12369.easylifeandroid.mvp.HomePresenter;
import com.iot12369.easylifeandroid.mvp.contract.HomeContract;
import com.iot12369.easylifeandroid.ui.AddressListActivity;
import com.iot12369.easylifeandroid.ui.AnnouncementActivity;
import com.iot12369.easylifeandroid.ui.BaseFragment;
import com.iot12369.easylifeandroid.ui.view.LoadingDialog;
import com.iot12369.easylifeandroid.ui.view.LockView;
import com.iot12369.easylifeandroid.ui.view.MyDialog;
import com.iot12369.easylifeandroid.ui.view.NewLockView;
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
//    @BindView(R.id.lock_view)
//    LockView mNewLockView;
    @BindView(R.id.new_lock_view)
    NewLockView mNewLockView;
    private AddressVo mAddress;
    private List<AddressVo> mAddressList;

    public String version = "1.0.0";

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
        //设置顶部图片的宽高
        if (mImageTop.getLayoutParams() != null) {
            mImageTop.getLayoutParams().height = (int) (1161 / 1620.0 * width);
        }
        if (mRlMore.getLayoutParams() != null) {
            mRlMore.getLayoutParams().height = (int) (161 / 1620.0 * width);
        }
        if (mRlBrief.getLayoutParams() != null) {
            mRlBrief.getLayoutParams().height = (int) (758 / 1620.0 * width);
        }
        mNewLockView.setOnStatusChangeListener(new NewLockView.OnStatusChangeListener() {
            @Override
            public void onStatusChange(int status, final int tab) {
                switch (status) {
                    case LockView.STATE_ING:
                        if (mAddress != null) {
                            LoginData data = LeApplication.mUserInfo;
                            getPresenter().lock(mAddress.memberId, data.phone, null,tab +"");
                        }
                        break;
                }
            }
        });
        RelativeLayout.LayoutParams paramsNewLockView = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        int marginTop =  - ((int)(width * (90 / 540.00)) - 8);
        paramsNewLockView.setMargins(0, marginTop, 0, 0);
        paramsNewLockView.addRule(RelativeLayout.BELOW, R.id.home_announcement_brief_ll);
        mNewLockView.setLayoutParams(paramsNewLockView);
        version = getVersion();
        getPresenter().homeThreeNotice();
        LoadingDialog.show(getActivity(), false);
        getPresenter().update();

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
    public void onSuccessLock(final IsOkData isOkData) {
//        if (isOkData.isOk()) {
//            mNewLockView.update(LockView.STATE_SUCCESS);
//        } else {
//            mNewLockView.update(LockView.STATE_FAILURE);
//        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isOkData.isOk()) {
                    mNewLockView.update(LockView.STATE_SUCCESS);
                } else {
                    mNewLockView.update(LockView.STATE_FAILURE);
                }
            }
        }, 500);

    }

    @Override
    public void onFailureLock(String code, String msg) {
//        mNewLockView.update(LockView.STATE_FAILURE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mNewLockView.update(LockView.STATE_FAILURE);
            }
        }, 500);
    }

    @Override
    public void onSuccessAddressList(AddressData addressData) {
        mAddressList = addressData.list;
        mNewLockView.setAddAdress(mAddressList);
        if (!isAlreadyCertification(addressData)) {
            mTvTopAddress.setText("暂无通过认证的物业");
            return;
        }
        mAddress = getCurrentAddress(addressData);
        LeApplication.mAddressVo = mAddress;
        if (mAddress != null) {
            StringBuilder builder = new StringBuilder();
            builder.append(mAddress.communityName);//小区名字
            //兼容老的
            if (!TextUtils.isEmpty(mAddress.communityBuiding) && !"null".equals(mAddress.communityBuiding)) {
                builder.append(mAddress.communityBuiding);//几号楼
                builder.append("号楼");//几号楼
            }
            if (!TextUtils.isEmpty(mAddress.communityUnit) && !"null".equals(mAddress.communityUnit)) {
                builder.append(mAddress.communityUnit);//几门
                builder.append("门");//几门
            }
            builder.append(mAddress.communityRawAddress);//原始门牌号
            mTvTopAddress.setText(builder.toString());
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
        if (noticeData.list != null && noticeData.list.size() >= 3) {
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

    public Dialog getUpdate(final UpdateData updateData, boolean isForce) {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_update, null);
        TextView txtUpdate = (TextView) contentView.findViewById(R.id.cer_update);
        TextView txtNoUpdate = (TextView) contentView.findViewById(R.id.cer_no_update);
        TextView tip = (TextView) contentView.findViewById(R.id.txt_version);
        tip.setText("版本提示：" + "当前版本" + version + "最新版本" + updateData.latestVersion);
        final MyDialog popWnd = new MyDialog(getContext());
//        popWnd.set
        popWnd.setContentView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        popWnd.setCancelable(false);
        popWnd.setCanceledOnTouchOutside(false);
        if (isForce) {
            txtNoUpdate.setVisibility(View.GONE);
        }
        txtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWnd.dismiss();
                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(updateData.downloadUrl);
                intent.setData(content_url);
                startActivity(intent);
                getActivity().finish();
            }
        });
        txtNoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWnd.dismiss();
            }
        });
        return popWnd;
    }

    @Override
    public void onSuccessUpdateData(UpdateData updateData) {
        LoadingDialog.hide();
        if (!TextUtils.isEmpty(version) && !TextUtils.isEmpty(updateData.latestVersion)) {
            String versionString = version.replace(".", "");
            String latesVersion = updateData.latestVersion.replace(".", "");
            int vers = Integer.valueOf(versionString);
            int lates = Integer.valueOf(latesVersion);
            if (lates > vers) {
                getUpdate(updateData, "1".equals(updateData.forceRenew)).show();
            }
        }
    }

    @Override
    public void onFailureUpdateData(String code, String msg) {
        LoadingDialog.hide();
    }

    public String getVersion() {
        try {
            PackageManager manager = getActivity().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getActivity().getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "1.0.0";
        }
    }
}
