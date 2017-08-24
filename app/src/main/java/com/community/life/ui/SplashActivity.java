package com.community.life.ui;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.community.life.LeApplication;
import com.community.life.MainActivity;
import com.community.life.R;

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
        mAnimation = new RotateAnimation(0f,360f,Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        mAnimation.setDuration(600);
        mAnimation.setRepeatCount(Integer.MAX_VALUE);
        mAnimation.setFillAfter(true);
        mAnimation.setInterpolator(new LinearInterpolator());
        mImageLoading.startAnimation(mAnimation);

        new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                 judgeJump();
             }
         }, 2000);
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
