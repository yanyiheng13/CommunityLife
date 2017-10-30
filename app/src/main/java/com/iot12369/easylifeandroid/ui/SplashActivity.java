package com.iot12369.easylifeandroid.ui;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.MainActivity;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.LoginData;
import com.iot12369.easylifeandroid.model.UserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能说明： 启动页
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-24 上午12:27
 * @Copyright (c) 2017. Inc. All rights reserved.
 */

public class SplashActivity extends BaseActivity {
    @BindView(R.id.image_loading)
    ImageView mImageLoading;
    //旋转动画
    private Animation mAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.RESTART);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setDuration(800);
        mImageLoading.startAnimation(mAnimation);

        new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                 judgeJump();
             }
         }, 1000);
    }

    private void judgeJump() {
        if (LeApplication.isLogin()) {
            MainActivity.newIntent(SplashActivity.this);
            SplashActivity.this.finish();
        } else {
            LoginSelectActivity.newIntent(this);
            SplashActivity.this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImageLoading != null) {
            mImageLoading.clearAnimation();
        }
        if (mAnimation != null) {
            mAnimation.cancel();
            mAnimation = null;
        }
    }
}
