package com.iot12369.easylifeandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.MainActivity;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.AdData;
import com.iot12369.easylifeandroid.model.LoginData;
import com.iot12369.easylifeandroid.model.UserInfo;
import com.iot12369.easylifeandroid.mvp.AdPresenter;
import com.iot12369.easylifeandroid.mvp.contract.AdContract;
import com.iot12369.easylifeandroid.util.SharePrefrenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能说明：广告展示界面
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-26 下午7:42
 * @Copyright (c) 2017. Inc. All rights reserved.
 */
public class AdActivity extends BaseActivity<AdPresenter> implements AdContract.View {
    /**
     * 顶部图片
     */
    @BindView(R.id.ad_img_top)
    ImageView mImageTop;
    /**
     * 底部内容父控件
     */
    @BindView(R.id.ad_bottom_ll)
    LinearLayout mLlBottom;
    /**
     * 广告倒计时
     */
    @BindView(R.id.ad_time)
    TextView mTvTime;

    private AdData mAdData;
    private boolean isIntoHome = false;
    private boolean isIntoAd = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_layout);
        ButterKnife.bind(this);
        //获取屏幕宽高
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        //设置顶部banner的宽高
        int topHeight = 0;
        if (mImageTop.getLayoutParams() != null) {
            topHeight = (int) (740 / 525.00 * width);
            mImageTop.getLayoutParams().height = topHeight;
        }
        //设置下面部分的高度，完成适配
        if (mLlBottom.getLayoutParams() != null) {
            mLlBottom.getLayoutParams().height = height - topHeight;
        }
        String ad = SharePrefrenceUtil.getString("config", "adData");
        if (!TextUtils.isEmpty(ad) && ad.length() > 10) {
            mAdData = new Gson().fromJson(ad, new TypeToken<AdData>(){}.getType());
            if (mAdData != null && !TextUtils.isEmpty(mAdData.index_1)) {
                Glide.with(this).load(mAdData.index_1).into(mImageTop);
            }
        }
        LoginData userInfo = LeApplication.mUserInfo;
        getPresenter().getAdlist(userInfo == null ? "" : userInfo.communityId);
        countDownTimer.start();
    }

    /**
     * CountDownTimer 实现倒计时
     */
    private CountDownTimer countDownTimer = new CountDownTimer(4000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            String value = String.valueOf((int) (millisUntilFinished / 1000));
            mTvTime.setText(String.format(getString(R.string.ad_count_time), value));
        }

        @Override
        public void onFinish() {
            if (!isDestroyed()) {
                if (isIntoAd) {
                    return;
                }
                isIntoHome = true;
                judgeJump();
            }
        }
    };

    @Override
    protected void onRestart() {
        super.onRestart();
        judgeJump();
    }

    @OnClick(R.id.ad_img_top)
    public void onClickAd() {
        if (mAdData == null || TextUtils.isEmpty(mAdData.index_1_target_url) || !mAdData.index_1_target_url.startsWith("http") || isIntoHome) {
            return;
        }
        isIntoAd = true;
        countDownTimer.cancel();
        WebViewActivity.newIntent(this, mAdData.index_1_target_url);
    }

    @OnClick(R.id.ad_time)
    public void onClick(View view) {
        countDownTimer.cancel();
        if (!isDestroyed()) {
            judgeJump();
        }
    }

    private void judgeJump() {
        if (LeApplication.isLogin()) {
            MainActivity.newIntent(AdActivity.this);
        } else {
            LoginSelectActivity.newIntent(this);
        }
        finish();
    }

    public static void newIntent(Context context) {
        Intent intent = new Intent(context, AdActivity.class);
        context.startActivity(intent);
    }

    /**
     * 广告请求
     * @param data
     */
    @Override
    public void onSuccessAd(AdData data) {
        if (data != null) {
            SharePrefrenceUtil.setString("config", "adData", new Gson().toJson(data));
            if (!isDestroyed()) {
                if (mAdData == null) {
                    Glide.with(this).load(data.index_1).into(mImageTop);
                } else {
                    if (!TextUtils.isEmpty(mAdData.index_1) && !mAdData.index_1.equals(data.index_1)) {
                        Glide.with(this).load(data.index_1).into(mImageTop);
                    }
                }

            }
        }
    }

    @Override
    public void onFailureAd(String code, String msg) {

    }
}
