package com.iot12369.easylifeandroid.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iot12369.easylifeandroid.R;
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

public class NewLockView extends RelativeLayout{
    public static final int  STATE_NORMAL = 1;
    public static final int  STATE_SUCCESS = 2;
    public static final int  STATE_FAILURE = 3;
    public static final int  STATE_ING = 4;

    public static int mCurrentStatus = 1;
    public static int mTab = 0;

    @BindView(R.id.img_lock_left)
    ImageView mImgLeft;
    @BindView(R.id.img_lock_right)
    ImageView mImgRight;
    @BindView(R.id.tv_lock_bottom)
    TextView mTvBottom;
    @BindView(R.id.img_lock_left_top)
    ImageView mImgTopLeft;
    @BindView(R.id.img_lock_right_top)
    ImageView mImgTopRight;
    @BindView(R.id.img_lock_animal)
    ImageView mImgAnimal;

    private boolean isAddAdress;

    private Long mStartTime;
    public List<AddressVo> mListAddress;

    private CountDownTimer mTimer;

    public NewLockView(Context context) {
        this(context, null);
    }

    public NewLockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewLockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_new_lock, this);
        ButterKnife.bind(this, this);
        //获取屏幕宽高
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        //获取紫色部分LayoutParams
        RelativeLayout.LayoutParams paramsLeft = (LayoutParams) mImgLeft.getLayoutParams();
        RelativeLayout.LayoutParams paramsRight = (LayoutParams) mImgRight.getLayoutParams();
        //left: 135 * 347  bottom 1080*301  240-387 540
        double bottomHeight = width * (301 / 1080.00);//底部圆弧高度
        if (paramsLeft != null && paramsRight != null) {
            double leftWidth = width * 135.00 / 1080;
            paramsLeft.width = (int) leftWidth;
            paramsRight.width = (int) leftWidth;
            double leftHeight = leftWidth * 347 / 135;
            paramsLeft.height = (int) leftHeight;
            paramsRight.height = (int) leftHeight;
        }
        RelativeLayout.LayoutParams paramsAnimal = (LayoutParams) mImgAnimal.getLayoutParams();
        RelativeLayout.LayoutParams paramsBottom = (LayoutParams) mTvBottom.getLayoutParams();
        if (paramsBottom != null) {
            paramsAnimal.width = width;
            paramsAnimal.height = (int) bottomHeight;
            paramsBottom.width = width;
            paramsBottom.height = (int) bottomHeight;
        }
        double topHeight = width / 2.00 * (387 / 540.00);
        double marginHeight = (topHeight * (240.00 / 387.00 - 1.00) + bottomHeight);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (width / 2.00), (int) topHeight);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams((int) (width / 2.00), (int) topHeight);
        params.setMargins(0, 0, 0, (int) marginHeight);
        params1.setMargins(0, 0, 0, (int) marginHeight);
        params1.addRule(RelativeLayout.ALIGN_PARENT_END);
        mImgTopLeft.setLayoutParams(params);
        mImgTopRight.setLayoutParams(params1);
        mTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                update(STATE_NORMAL);
            }
        };
        mImgTopLeft.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mTab = 1;
                myTouchListener(event);
                return true;
            }
        });
        mImgTopRight.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mTab = 2;
                myTouchListener(event);
                return true;
            }
        });
        mTvBottom.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    public void myTouchListener(MotionEvent event) {
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
                if (mTab == 1) {
                    mImgTopLeft.setImageResource(R.mipmap.lock_left_top_press);
                } else {
                    mImgTopRight.setImageResource(R.mipmap.lock_right_top_press);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isAddAdress) {
                    isAddAdress = false;
                    break;
                }
                if (mTab == 1) {
                    mImgTopLeft.setImageResource(R.mipmap.lock_left_top);
                } else {
                    mImgTopRight.setImageResource(R.mipmap.lock_right_top);
                }
                if ((System.currentTimeMillis() - mStartTime) / 1000 < 1) {
                    ToastUtil.toastLong(getContext(), "请长按开锁按钮1秒开锁");
                    update(STATE_NORMAL);
                    break;
                }
                update(STATE_ING);
                if (listener != null) {
                    listener.onStatusChange(STATE_ING, mTab);
                }
                break;
            default:
                break;
        }
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

    public void update(int status) {
        switch (status) {
            case STATE_NORMAL:
                mImgAnimal.setVisibility(View.GONE);
                ((AnimationDrawable) mImgAnimal.getDrawable()).stop();
                mTvBottom.setText("长按上方按钮开锁");
                break;
            case STATE_SUCCESS:
                mImgAnimal.setVisibility(View.GONE);
                ((AnimationDrawable) mImgAnimal.getDrawable()).stop();
                mTvBottom.setText("开锁成功");
                mTimer.start();
                break;
            case STATE_FAILURE:
                mImgAnimal.setVisibility(View.GONE);
                ((AnimationDrawable) mImgAnimal.getDrawable()).stop();
                mTimer.start();
                mTvBottom.setText("开锁失败");
                break;
            case STATE_ING:
                mImgAnimal.setVisibility(View.VISIBLE);
                ((AnimationDrawable) mImgAnimal.getDrawable()).start();
                mTvBottom.setText("开锁中...");
                break;
            default:
                break;
        }
        mCurrentStatus = status;
    }

    public OnStatusChangeListener listener;
    public interface OnStatusChangeListener {
        void onStatusChange(int status, int tab);
    }
    public void setOnStatusChangeListener(OnStatusChangeListener listener) {
        this.listener = listener;
    }
}
