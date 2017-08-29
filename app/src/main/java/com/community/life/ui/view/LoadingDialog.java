package com.community.life.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.community.life.R;


/**
 * 功能说明： 提交加载中信息
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 2017/8/29
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */
public class LoadingDialog {

    private static Dialog mDialog = null;

    private static void create(Context context) {
//        Context context = GlobalData.getInstance().getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);

        mDialog = new Dialog(context, R.style.MyDialog);
        //某些手机不允许系统级别的弹窗
//        mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setContentView(view);
        startAnim();
    }

    private static void startAnim() {
        ImageView img_out = (ImageView) mDialog.findViewById(R.id.img_load_out);
        img_out.startAnimation(getAnim());
    }

    public static void stopAnim() {
        ImageView img_out = (ImageView) mDialog.findViewById(R.id.img_load_out);
        img_out.clearAnimation();
    }

    private static Animation getAnim() {
        Animation animation = AnimationUtils.loadAnimation(mDialog.getContext(), R.anim.rotate);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(800);
        return animation;
    }

    public static void show(Context context) {
        show(context, true);
    }


    public static void show(Context context, boolean cancelable) {
        show(context, cancelable, null);
    }

    public static void show(Context context, boolean cancelable, DialogInterface.OnCancelListener listener) {
        hide();
        create(context);
        mDialog.setCancelable(cancelable);
        mDialog.setOnCancelListener(listener);
        mDialog.show();
    }

    public static void hide() {
        if (mDialog != null) {
            stopAnim();
            try {
                mDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mDialog = null;
        }
    }
}
