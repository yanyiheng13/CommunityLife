package com.iot12369.easylifeandroid.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.support.annotation.AttrRes;
import android.support.annotation.BoolRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.ViewUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.AddressData;
import com.iot12369.easylifeandroid.model.AddressVo;
import com.iot12369.easylifeandroid.ui.AddAddressActivity;
import com.iot12369.easylifeandroid.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author YiHeng Yan
 * @Description
 * @Email yanyiheng86@163.com
 * @date 2017/9/17 20:59
 */

public class LockView extends LinearLayout {

    public static final int  STATE_NORMAL = 1;
    public static final int  STATE_SUCCESS = 2;
    public static final int  STATE_FAILURE = 3;
    public static final int  STATE_ING = 4;

    public static int mCurrentStatus = 1;

    //状态
    @BindView(R.id.home_announcement_lock_status)
    ImageView mImgStatus;
    //转圈
    @BindView(R.id.home_announcement_lock_circle)
    ImageView mImgCircles;
    //
    @BindView(R.id.lock_right_img)
    ImageView mImgRight;
    @BindView(R.id.lock_left_img)
    ImageView mImgLeft;

    private boolean isAddAdress;

    private Long mStartTime;

    private CountDownTimer mTimer;
    public List<AddressVo> mListAddress;

    public LockView(@NonNull Context context) {
        this(context, null);
    }

    public LockView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LockView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_lock, this);
        ButterKnife.bind(this, this);
        mImgLeft.setImageResource(R.drawable.lock_animal_left);
        mImgRight.setImageResource(R.drawable.lock_animal_right);
        mTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                update(STATE_NORMAL);
            }
        };
    }
    public void setAddAdress(List<AddressVo> listAddress) {
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
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!isAlreadyCertification(mListAddress)) {
                    isAddAdress = true;
                    getPopupWindow().show();
                    break;
                }
                if (mCurrentStatus != STATE_NORMAL) {
                    break;
                }
                mStartTime = System.currentTimeMillis();
                mImgCircles.setImageResource(R.drawable.lock_rotate);
                mImgCircles.setVisibility(VISIBLE);
                mImgLeft.setVisibility(VISIBLE);
                mImgRight.setVisibility(VISIBLE);

                ((AnimationDrawable) mImgCircles.getDrawable()).start();
                ((AnimationDrawable) mImgLeft.getDrawable()).start();
                ((AnimationDrawable) mImgRight.getDrawable()).start();
                break;
            case MotionEvent.ACTION_UP:
                if (isAddAdress == true) {
                    isAddAdress = false;
                    break;
                }
                if ((System.currentTimeMillis() - mStartTime) / 1000 < 1) {
                    ToastUtil.toastLong(getContext(), "请长按开锁按钮1秒开锁");
                    mImgCircles.setVisibility(GONE);
                    mImgLeft.setVisibility(GONE);
                    mImgRight.setVisibility(GONE);
                    update(STATE_NORMAL);
                    break;
                }
                update(STATE_ING);
                if (listener != null) {
                    listener.onStatusChange(STATE_ING);
                }
                break;
            default:
                break;
        }
        return true;
    }

    public Dialog getPopupWindow() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_certi, null);
        TextView txtCer = (TextView) contentView.findViewById(R.id.cer_tv);
        TextView close = (TextView) contentView.findViewById(R.id.close);
        final MyDialog  popWnd = new MyDialog(getContext());
//        popWnd.set
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

    public void update(int status) {
        switch (status) {
            case STATE_NORMAL:
                mImgStatus.setImageResource(R.mipmap.icon_state_one);
                mImgLeft.setVisibility(GONE);
                mImgRight.setVisibility(GONE);
                mImgCircles.setVisibility(GONE);
                break;
            case STATE_SUCCESS:
                mImgStatus.setImageResource(R.mipmap.icon_state_three);
                mImgLeft.setVisibility(GONE);
                mImgRight.setVisibility(GONE);
                mTimer.start();
                break;
            case STATE_FAILURE:
                mImgStatus.setImageResource(R.mipmap.icon_state_four);
                mImgLeft.setVisibility(GONE);
                mImgRight.setVisibility(GONE);
                mTimer.start();
                break;
            case STATE_ING:
                mImgStatus.setImageResource(R.mipmap.icon_state_two);
                break;
            default:
                break;
        }
        mCurrentStatus = status;
    }

    public OnStatusChangeListener listener;
    public interface OnStatusChangeListener {
        void onStatusChange(int status);
    }
    public void setOnStatusChangeListener(OnStatusChangeListener listener) {
        this.listener = listener;
    }

//    private Animation getAnim() {
//        Animation animation = AnimationUtils.loadAnimation(getContext(), );
//        animation.setRepeatCount(Animation.INFINITE);
//        animation.setRepeatMode(Animation.RESTART);
//        animation.setInterpolator(new LinearInterpolator());
//        animation.setDuration(800);
//        return animation;
//    }
}
