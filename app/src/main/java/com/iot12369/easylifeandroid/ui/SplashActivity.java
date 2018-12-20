package com.iot12369.easylifeandroid.ui;


import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.iot12369.easylifeandroid.R;
import com.luck.picture.lib.permissions.RxPermissions;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

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
    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XGPushClickedResult click = XGPushManager.onActivityStarted(this);
        if (click != null) {
            finish();
            return;
        }
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
                 AdActivity.newIntent(SplashActivity.this);
                 finish();
             }
         }, 1000);
    }

    public  void judgePermission() {
        if (rxPermissions == null) {
            rxPermissions = new RxPermissions(this);
        }
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {

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
