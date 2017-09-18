package com.iot12369.easylifeandroid.ui.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.AttrRes;
import android.support.annotation.BoolRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.ViewUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.util.ToastUtil;

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

    private Long mStartTime;

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
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mCurrentStatus == STATE_ING) {
                    break;
                }
                mImgCircles.setImageResource(R.drawable.lock_rotate);
                mImgCircles.setVisibility(View.VISIBLE);
                mImgLeft.setVisibility(View.VISIBLE);
                mImgRight.setVisibility(View.VISIBLE);

                ((AnimationDrawable) mImgCircles.getDrawable()).start();
                ((AnimationDrawable) mImgLeft.getDrawable()).start();
                ((AnimationDrawable) mImgRight.getDrawable()).start();
                mStartTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                if ((System.currentTimeMillis() - mStartTime) / 1000 < 3) {
                    ToastUtil.toastLong(getContext(), "请长按开锁按钮三秒开锁");
                    mImgCircles.setVisibility(View.GONE);
                    mImgLeft.setVisibility(View.GONE);
                    mImgRight.setVisibility(View.GONE);
                    update(STATE_NORMAL);
                    break;
                }
                update(STATE_ING);
                break;
            default:
                break;
        }
        return true;
    }

    public void update(int status) {
        switch (status) {
            case STATE_NORMAL:
                mImgStatus.setImageResource(R.mipmap.icon_state_one);
                break;
            case STATE_SUCCESS:
                mImgStatus.setImageResource(R.mipmap.icon_state_three);
                break;
            case STATE_FAILURE:
                mImgStatus.setImageResource(R.mipmap.icon_state_four);
                break;
            case STATE_ING:
                mImgStatus.setImageResource(R.mipmap.icon_state_two);
                mCurrentStatus = STATE_ING;
                break;
            default:
                break;
        }
    }

    public OnStatusChangeListener listener;
    public interface OnStatusChangeListener {
        void onStatusChange();
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
