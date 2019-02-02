package com.iot12369.easylifeandroid.ui;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.iot12369.easylifeandroid.MainActivity;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.util.SharePrefrenceUtil;
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
        SharePrefrenceUtil.setString("config", "actionTypeFlag", "haspush");
//        Log.d("yanyiheng", "启动了");
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
                if (!isTaskRoot()) {
//                    Log.d("yanyiheng", "跳转了");
                    pushJump();
                    finish();
                    return;
                }
            }
        }, 300);
        new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                judgePermission();
             }
         }, 1000);
    }

    private void pushJump() {
        String value = SharePrefrenceUtil.getString("config", "actionType");
        if ("homePage".equals(value)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if ("messagePage".equals(value)) {
            Intent intent = new Intent(this, AnnouncementActivity.class);
            startActivity(intent);
        } else if ("payPage".equals(value)) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("tab", "pay");
            startActivity(intent);
        } else if ("authorizationPage".equals(value)) {
            Intent intent = new Intent(this, AuthorizationActivity.class);
            this.startActivity(intent);
        }
        SharePrefrenceUtil.setString("config", "actionType", "");
    }

    public  void judgePermission() {
        if (isFinishing() || isDestroyed()) {
            return;
        }
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
                            AdActivity.newIntent(SplashActivity.this);
                            finish();
                        } else {
                            new AlertDialog.Builder(SplashActivity.this)
                                    .setMessage("需要开启内存卡读写权限才能使用此功能")
                                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //引导用户到设置中去进行设置
                                            Intent intent = new Intent();
                                            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                            intent.setData(Uri.fromParts("package", getPackageName(), null));
                                            startActivity(intent);
                                            finish();

                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                                    .create()
                                    .show();
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
