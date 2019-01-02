package com.iot12369.easylifeandroid.ui.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.AdData;
import com.iot12369.easylifeandroid.model.AddressVo;
import com.iot12369.easylifeandroid.ui.AddAddressActivity;
import com.iot12369.easylifeandroid.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能说明
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/6 17:15
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class NewLockView extends RelativeLayout {
    public static final int  STATE_NORMAL = 1;
    public static final int  STATE_SUCCESS = 2;
    public static final int  STATE_FAILURE = 3;
    public static final int  STATE_ING = 4;

    private boolean isDialogShow;

    public List<AddressVo> mListAddress;

    private MyDialog myDialogUnlock;
    private ImageView mImgAd;
    private ImageView mImgUnlockOne;
    private ImageView mImgUnlockTwo;
    private ImageView mImgLockView;

    private TextView mTvTipOne;
    private TextView mTvTipTwo;

    private CountDownTimer mTimer1;
    private CountDownTimer mTimer2;
    private int mWidth;
    private int mImageHeight;
    private int mImageWidth;

    private AdData mAdData;

    public NewLockView(Context context) {
        this(context, null);
    }

    public NewLockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("ClickableViewAccessibility")
    public NewLockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_new_lock, this);
        ButterKnife.bind(this, this);
        mImgLockView = findViewById(R.id.imgClickLock);
        //获取屏幕宽高
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        mWidth = wm.getDefaultDisplay().getWidth();
        RelativeLayout.LayoutParams params = (LayoutParams) mImgLockView.getLayoutParams();
        if (params != null) {
            params.height = (int) (mWidth / 3.5);
            params.width = (int) (mWidth / 3.5);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            params.topMargin = (int) (mWidth / 10.5);
        }

        mImgLockView.setLayoutParams(params);
        mTimer1 = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                update(STATE_NORMAL, 1);
            }
        };
        mTimer2 = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                update(STATE_NORMAL, 2);
            }
        };
    }

    public void onClickKind(int kind) {
//        if (!isAlreadyCertification(mListAddress)) {
//            getPopupWindow().show();
//            return;
//        }
        if (!isDialogShow || mImgUnlockOne == null || mImgUnlockTwo == null) {
            return;
        }
        if (mImageWidth == 0) {
            mImageWidth = mImgUnlockOne.getLayoutParams().width;
        }
        if (mImageHeight == 0) {
            mImageHeight = mImgUnlockOne.getLayoutParams().height;
        }
        update(STATE_ING, kind);
    }

    public boolean judge() {
        if (!isAlreadyCertification(mListAddress)) {
            getPopupWindow().show();
            return false;
        }
        return true;
    }

    public Dialog getPopupWindow() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_certi, null);
        TextView txtCer = (TextView) contentView.findViewById(R.id.cer_tv);
        TextView close = (TextView) contentView.findViewById(R.id.close);
        final MyDialog  popWnd = new MyDialog(getContext());
        popWnd.setContentView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        popWnd.setCancelable(true);
        popWnd.setCanceledOnTouchOutside(true);
        txtCer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popWnd.dismiss();
                AddAddressActivity.newIntent(getContext());
            }
        });
        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popWnd.dismiss();
            }
        });
        return popWnd;
    }

    public MyDialog  createLockDialog() {
        if (myDialogUnlock == null) {
            myDialogUnlock = new MyDialog(getContext());
        }
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_unlock, null);
        mImgAd = (ImageView) contentView.findViewById(R.id.img_unlock_ad);
        mImgUnlockOne = (ImageView) contentView.findViewById(R.id.img_community_door);
        mImgUnlockTwo = (ImageView) contentView.findViewById(R.id.img_unit_door);
        mTvTipOne = (TextView) contentView.findViewById(R.id.tv_community_door);
        mTvTipTwo = (TextView) contentView.findViewById(R.id.tv_unit_door);
        if (mAdData != null && !TextUtils.isEmpty(mAdData.index_4)) {
            Glide.with(getContext()).load(mAdData.index_4).into(mImgUnlockOne);
        }
        ImageView imgClose = (ImageView) contentView.findViewById(R.id.img_close);
        if (mImgAd.getLayoutParams() != null) {
            mImgAd.getLayoutParams().height = (int)((662 / 1080.00) * (mWidth - 100));
        }
        myDialogUnlock.setContentView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        myDialogUnlock.setCancelable(false);
        myDialogUnlock.setCanceledOnTouchOutside(false);
        imgClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialogUnlock.dismiss();
                isDialogShow = false;
            }
        });
        mImgUnlockOne.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAdData != null && !TextUtils.isEmpty(mAdData.index_4)) {
                    Glide.with(getContext()).load(mAdData.index_4).into(mImgUnlockOne);
                }
                onClickKind(1);
            }
        });
        mImgUnlockTwo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAdData != null && !TextUtils.isEmpty(mAdData.index_5)) {
                    Glide.with(getContext()).load(mAdData.index_5).into(mImgUnlockTwo);
                }
                onClickKind(2);
            }
        });
        isDialogShow = true;
        return myDialogUnlock;
    }

    public void setAddAddress(List<AddressVo> listAddress) {
        mListAddress = listAddress;
    }
    public boolean isAlreadyCertification(List<AddressVo> addressData) {
        if (addressData == null || addressData.size() == 0) {
            return false;
        }
        boolean isAlready = false;
        int size = addressData.size();
        for (int i = 0; i < size; i++) {
            AddressVo addressVo = addressData.get(i);
            if ("2".equals(addressVo.estateAuditStatus)) {
                isAlready = true;
                break;
            }
        }
        return isAlready;
    }

    public void setData(AdData adData) {
        this.mAdData = adData;
    }

    public void update(int status, int kind) { // tab 1: 小区门  2: 单元门
        switch (status) {
            case STATE_NORMAL:
                if (!isDialogShow) { //弹窗已经关闭
                    break;
                }
                if (kind == 1) {
                    if (mImgUnlockOne.getLayoutParams() != null) {
                        mImgUnlockOne.getLayoutParams().height = mImageHeight;
                        mImgUnlockOne.getLayoutParams().width = mImageWidth;
                    }
                    mTvTipOne.setText("");
                } else if (kind == 2) {
                    if (mImgUnlockTwo.getLayoutParams() != null) {
                        mImgUnlockTwo.getLayoutParams().height = mImageHeight;
                        mImgUnlockTwo.getLayoutParams().width = mImageWidth;
                    }
                    mTvTipTwo.setText("");
                }
                break;
            case STATE_SUCCESS:
                if (!isDialogShow) { //弹窗已经关闭
                    break;
                }
                if (kind == 1) {
                    if (mImgUnlockOne.getLayoutParams() != null) {
                        mImgUnlockOne.getLayoutParams().height = mImageHeight;
                        mImgUnlockOne.getLayoutParams().width = mImageWidth;
                    }
                    mTvTipOne.setText("开锁成功");
                    mTimer1.start();
                } else if (kind == 2) {
                    if (mImgUnlockTwo.getLayoutParams() != null) {
                        mImgUnlockTwo.getLayoutParams().height = mImageHeight;
                        mImgUnlockTwo.getLayoutParams().width = mImageWidth;
                    }
                    mTvTipTwo.setText("开锁成功");
                    mTimer2.start();
                }
                break;
            case STATE_FAILURE:
                if (!isDialogShow) { //弹窗已经关闭
                    break;
                }
                if (kind == 1) {
                    if (mImgUnlockOne.getLayoutParams() != null) {
                        mImgUnlockOne.getLayoutParams().height = mImageHeight;
                        mImgUnlockOne.getLayoutParams().width = mImageWidth;
                    }
                    mTvTipOne.setText("开锁失败");
                    mTimer1.start();
                } else if (kind == 2) {
                    if (mImgUnlockTwo.getLayoutParams() != null) {
                        mImgUnlockTwo.getLayoutParams().height = mImageHeight;
                        mImgUnlockTwo.getLayoutParams().width = mImageWidth;
                    }
                    mTvTipTwo.setText("开锁失败");
                    mTimer2.start();
                }
                break;
            case STATE_ING:
                if (!isDialogShow) { //弹窗已经关闭
                    break;
                }
                if (kind == 1) {
                    if (mImgUnlockOne.getLayoutParams() != null) {
                        mImgUnlockOne.getLayoutParams().height = (int)(mImageHeight * 0.9);
                        mImgUnlockOne.getLayoutParams().width = (int)(mImageWidth * 0.9);
                    }
                    mTvTipOne.setText("开锁中...");
                } else if (kind == 2) {
                    if (mImgUnlockTwo.getLayoutParams() != null) {
                        mImgUnlockTwo.getLayoutParams().height = (int)(mImageHeight * 0.9);
                        mImgUnlockTwo.getLayoutParams().width = (int)(mImageWidth * 0.9);
                    }
                    mTvTipTwo.setText("开锁中...");
                }
                if (listener != null) {
                    listener.onStatusChange(status, kind);
                }
                break;
            default:
                break;
        }
    }

    public OnStatusChangeListener listener;
    public interface OnStatusChangeListener {
        void onStatusChange(int status, int tab);
    }
    public void setOnStatusChangeListener(OnStatusChangeListener listener) {
        this.listener = listener;
    }
}
