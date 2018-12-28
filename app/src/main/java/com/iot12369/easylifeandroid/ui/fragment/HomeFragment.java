package com.iot12369.easylifeandroid.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iot12369.easylifeandroid.BuildConfig;
import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.AdData;
import com.iot12369.easylifeandroid.model.AddressData;
import com.iot12369.easylifeandroid.model.AddressVo;
import com.iot12369.easylifeandroid.model.AnnouncementVo;
import com.iot12369.easylifeandroid.model.ContactsInfo;
import com.iot12369.easylifeandroid.model.IsOkData;
import com.iot12369.easylifeandroid.model.LoginData;
import com.iot12369.easylifeandroid.model.NoticeData;
import com.iot12369.easylifeandroid.model.UpdateData;
import com.iot12369.easylifeandroid.mvp.HomePresenter;
import com.iot12369.easylifeandroid.mvp.contract.HomeContract;
import com.iot12369.easylifeandroid.ui.AddressListActivity;
import com.iot12369.easylifeandroid.ui.AnnouncementActivity;
import com.iot12369.easylifeandroid.ui.BaseFragment;
import com.iot12369.easylifeandroid.ui.view.BadgeView;
import com.iot12369.easylifeandroid.ui.view.LoadingDialog;
import com.iot12369.easylifeandroid.ui.view.LockView;
import com.iot12369.easylifeandroid.ui.view.MyDialog;
import com.iot12369.easylifeandroid.ui.view.NewLockView;
import com.iot12369.easylifeandroid.util.SharePrefrenceUtil;
import com.luck.picture.lib.permissions.RxPermissions;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.transformer.FlipHorizontalTransformer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 功能说明： 首页  也就是第三个tab
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-8-24
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View, AMapLocationListener {
    @BindView(R.id.home_top_img)
    Banner mBanner;

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
    @BindView(R.id.new_lock_view)
    NewLockView mNewLockView;
    @BindView(R.id.icon_announcement_img)
    ImageView mImgMsgNotRead;
    @BindView(R.id.badgeView)
    BadgeView mBadgeView;
    private AddressVo mAddress;
    private List<AddressVo> mAddressList;
    private RxPermissions rxPermissions;
    private ReadContactsThread mReadContacts;
    //读取联系人信息
    private ContentResolver contentResolver;
    private AdData mAdData;

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
        //设置顶部banner的宽高
        if (mBanner.getLayoutParams() != null) {
            mBanner.getLayoutParams().height = (int) (662 / 1080.0 * width);
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
                    case NewLockView.STATE_ING:
                        if (mAddress != null) {
                            LoginData data = LeApplication.mUserInfo;
                            getPresenter().lock(mAddress.memberId, data.phone, null, tab + "");
                        }
                        break;
                }
            }
        });
        mNewLockView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNewLockView.createLockDialog().show();
            }
        });
        RelativeLayout.LayoutParams paramsNewLockView = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                (int) (width * (538 / 1000.00)));
        int marginTop = -((int) (width * (100 / 540.00)) - 8);
        paramsNewLockView.setMargins(0, marginTop, 0, 0);
        paramsNewLockView.addRule(RelativeLayout.BELOW, R.id.home_announcement_brief_ll);
        mNewLockView.setLayoutParams(paramsNewLockView);
        getPresenter().homeThreeNotice();
        LoadingDialog.show(getActivity(), false);
        getPresenter().update();

        String ad = SharePrefrenceUtil.getString("config", "adData");
        if (!TextUtils.isEmpty(ad) && ad.length() > 10) {
            mAdData = new Gson().fromJson(ad, new TypeToken<AdData>() {
            }.getType());
        }
        List<String> listData = new ArrayList<>();
        if (mAdData != null) {
            if (!TextUtils.isEmpty(mAdData.index_3_1)) {
                listData.add(mAdData.index_3_1);
            }
            if (!TextUtils.isEmpty(mAdData.index_3_2)) {
                listData.add(mAdData.index_3_2);
            }
            if (!TextUtils.isEmpty(mAdData.index_3_3)) {
                listData.add(mAdData.index_3_3);
            }
        }
        if (listData.size() == 0) {
            listData.add("file:///android_asset/icon_banner.png");
        }
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setBannerAnimation(FlipHorizontalTransformer.class);
        //banner设置方法全部调用完毕时最后调用
        //设置图片集合
        mBanner.setImages(listData);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
        mBadgeView.hide();
        getPresenter().notReadMsg(LeApplication.mUserInfo.phone);

        // 获取地理位置然后上报

        // 检测读取联系人权限，如果同意读取联系人信息，则在子线程中读取电话信息，然后上报
        detectionContactInformation();
        detectionLocation();

        String jsonString = SharePrefrenceUtil.getString("config", "list");
        AddressData addressData = null;
        if (!TextUtils.isEmpty(jsonString) && jsonString.length() > 10) {
            addressData = new Gson().fromJson(jsonString, new TypeToken<AddressData>(){}.getType());
            if (addressData != null) {
                onSuccessAddressList(addressData);
            }
        }
    }

    public void detectionLocation() {
        if (rxPermissions == null) {
            rxPermissions = new RxPermissions(getActivity());
        }
        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            isLocation();
                        } else {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    private void isLocation() {
        //声明mLocationOption对象
        mlocationClient = new AMapLocationClient(getContext());
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(1000 * 60 * 60 * 5);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                getPresenter().uploadLocation(LeApplication.mUserInfo.phone, amapLocation.getAddress());
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
//                Log.e("AmapError", "location Error, ErrCode:"
//                        + amapLocation.getErrorCode() + ", errInfo:"
//                        + amapLocation.getErrorInfo());
            }
        }
    }

    /**
     * 检测联系人信息
     */
    private void detectionContactInformation() {
        if (rxPermissions == null) {
            rxPermissions = new RxPermissions(getActivity());
        }
        if (mReadContacts == null) {
            mReadContacts = new ReadContactsThread();
        }
        if (mHandler == null) {
            mHandler = new WeakReferenceHandler(this);
        }
        rxPermissions.request(Manifest.permission.READ_CONTACTS)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            contentResolver = getContext().getContentResolver();
                            mReadContacts.start();
                        } else {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    class ReadContactsThread extends Thread {
        @Override
        public void run() {
            super.run();
            List<ContactsInfo> list = new ArrayList<>();

            //获取内容解析者
            ContentResolver contentResolver = getActivity().getContentResolver();
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String[] projection = new String[]{
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID
            };
            Cursor cursor = contentResolver.query(uri, projection, null, null, null);
            //解析cursor获取数据
            while (cursor.moveToNext()) {
                String name = cursor.getString(0);
                String number = cursor.getString(1);
//                int contactId = cursor.getInt(2);

                ContactsInfo contactsInfo = new ContactsInfo();
                contactsInfo.p = number;
                contactsInfo.n = name;
                list.add(contactsInfo);
            }
            Message msg = mHandler.obtainMessage();
            msg.obj = list;
            mHandler.sendMessage(msg);
            cursor.close();
        }
    }

    private Handler mHandler = null;

    public static class WeakReferenceHandler extends Handler {
        WeakReference<Fragment> weakReference = null;

        public WeakReferenceHandler(Fragment activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (weakReference != null && weakReference.get() != null) {
                if ((msg != null) && msg.obj != null) {
                    List<ContactsInfo> list = (ArrayList<ContactsInfo>) msg.obj;
                    if (list != null && list.size() > 0) {
//                        List<ContactsInfo> list = new ArrayList<>();
//                        ContactsInfo contactsInfo = new ContactsInfo();
//                        contactsInfo.n = "张三";
//                        contactsInfo.p = "17090024334";
//                        list.add(contactsInfo);
                        ((HomeFragment) weakReference.get()).getPresenter().uploadContacts(LeApplication.mUserInfo.phone, new Gson().toJson(list));
                    }
                }

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isResumed() && LeApplication.mCurrentTag == LeApplication.TAG_HOME) {
            getPresenter().addressList(LeApplication.mUserInfo.phone);
            mBanner.startAutoPlay();
            getPresenter().notReadMsg(LeApplication.mUserInfo.phone);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            getPresenter().addressList(LeApplication.mUserInfo.phone);
            getPresenter().homeThreeNotice();
            mBanner.startAutoPlay();
        } else {
            mBanner.stopAutoPlay();
        }
    }

    @OnClick({R.id.home_announcement_more_rl, R.id.home_top_key_img, R.id.new_lock_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_announcement_more_rl:
                AnnouncementActivity.newIntent(getContext());
                break;
            case R.id.home_top_key_img:
                AddressListActivity.newIntent(getActivity(), mAddressList, mAddress, 100);
                break;
            case R.id.new_lock_view:
//                showUnlockDialog();
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
        }
    }

    @Override
    public void onSuccessLock(final IsOkData isOkData, final String kind) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isOkData.isOk()) {
                    mNewLockView.update(LockView.STATE_SUCCESS, Integer.parseInt(kind));
                } else {
                    mNewLockView.update(LockView.STATE_FAILURE, Integer.parseInt(kind));
                }
            }
        }, 500);

    }

    @Override
    public void onFailureLock(String code, String msg, final String kind) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mNewLockView.update(LockView.STATE_FAILURE, Integer.parseInt(kind));
            }
        }, 500);
    }

    @Override
    public void onSuccessAddressList(AddressData addressData) {
        mAddressList = addressData.list;
        mNewLockView.setAddAddress(mAddressList);
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
        tip.setText("版本提示：" + "当前版本" + BuildConfig.VERSION_NAME + "最新版本" + updateData.latestVersion);
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
                Intent intent = new Intent();
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
        if (!TextUtils.isEmpty(BuildConfig.VERSION_NAME) && !TextUtils.isEmpty(updateData.latestVersion)) {
            String versionString = BuildConfig.VERSION_NAME.replace(".", "");
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

    @Override
    public void onSuccessMsgCount(IsOkData notReadMsgCount) {
        if (notReadMsgCount != null && !TextUtils.isEmpty(notReadMsgCount.countOfunread)) {
            mBadgeView.setVisibility("0".equals(notReadMsgCount.countOfunread) ? View.GONE : View.VISIBLE);
            mBadgeView.setText(notReadMsgCount.countOfunread);
            mBadgeView.setTextSize(9);
        } else {
            mBadgeView.hide();
        }
    }

    @Override
    public void onFailureMsgCode(String code, String msg) {

    }

    @Override
    public void onSuccessUploadPhonBook(String isOkData) {

    }

    @Override
    public void onFailureUploadPhoneBook(String code, String msg) {

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

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);
        }
    }
}
