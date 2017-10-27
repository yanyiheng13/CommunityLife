package com.iot12369.easylifeandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.MainActivity;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.IsOkData;
import com.iot12369.easylifeandroid.model.LoginData;
import com.iot12369.easylifeandroid.mvp.LoginPresent;
import com.iot12369.easylifeandroid.mvp.contract.LoginContract;
import com.iot12369.easylifeandroid.ui.view.LoadingDialog;
import com.iot12369.easylifeandroid.ui.view.WithBackTitleView;
import com.iot12369.easylifeandroid.util.SharePrefrenceUtil;
import com.iot12369.easylifeandroid.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能说明： 登录界面
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-24 下午11:04
 * @Copyright (c) 2017. Inc. All rights reserved.
 */

public class LoginActivity extends BaseActivity<LoginPresent> implements LoginContract.View{
    public static int TYPE_BIND = 0x11;
    public static int TYPE_LOGIN = 0x12;

    @BindView(R.id.title_view)
    WithBackTitleView mTitleView;

    @BindView(R.id.login_get_code_tv)
    TextView mTvGetCode;

    @BindView(R.id.login_phone_edit)
    EditText mEditPhone;
    @BindView(R.id.login_code_edit)
    EditText mEditCode;

    private CountDownTimer mDownTimer;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            type = getIntent().getIntExtra("type", 0);
        } else {
            type = savedInstanceState.getInt("type");
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        type = getIntent().getIntExtra("type", 0);
    }

    private void init() {
        mTitleView.setText(type == TYPE_BIND ? R.string.bind_phone : R.string.login);
        mTvGetCode.setEnabled(true);
        mDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTvGetCode.setText(String.format(getString(R.string.retry), millisUntilFinished / 1000 + ""));
            }

            @Override
            public void onFinish() {
                mTvGetCode.setEnabled(true);
                mTvGetCode.setText(R.string.get_code);
            }
        };
    }


    @OnClick({R.id.login_get_code_tv, R.id.btn_login, R.id.login_by_voice_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            //短信
            case R.id.login_get_code_tv:
                mTvGetCode.setEnabled(false);
                mDownTimer.start();
                getPresenter().verificationCode(mEditPhone.getText().toString());
                LoadingDialog.show(this, false);
                break;
            case R.id.btn_login:
                if (!judgeInput()) {
                    break;
                }
                if (type == TYPE_BIND) {
                    String openid = SharePrefrenceUtil.getString("config", "openid");
                    getPresenter().bindPhone(mEditPhone.getText().toString(), mEditCode.getText().toString(), openid);
                } else {
                    getPresenter().loginPhone(mEditPhone.getText().toString(), mEditCode.getText().toString());
                }

                LoadingDialog.show(this, false);
                break;
            //语音短信验证码
            case R.id.login_by_voice_tv:
                getPresenter().verificationVoiceCode(mEditPhone.getText().toString());
                LoadingDialog.show(this, false);
                break;
            default:
                break;
        }
    }

    public static void newIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void newIntent(Context context, int type) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    public void onSuccessLogin(LoginData loginData) {
        LeApplication.mUserInfo = loginData;
        SharePrefrenceUtil.setString("config", "user", new Gson().toJson(loginData));
        LoadingDialog.hide();
        MainActivity.newIntent(this);
    }

    @Override
    public void onFailureLogin(String code, String msg) {
        LoadingDialog.hide();
    }

    @Override
    public void onSuccessCode(IsOkData isOkData) {
        ToastUtil.toast(this, "验证码已下发");
        LoadingDialog.hide();
    }

    @Override
    public void onFailureCode(String code, String msg) {
        LoadingDialog.hide();
    }

    @Override
    public void onSuccessVoice(IsOkData isOkData) {
        ToastUtil.toast(this, "请注意接听");
        LoadingDialog.hide();
    }

    @Override
    public void onFailureVoice(String code, String msg) {
        LoadingDialog.hide();
    }

    public boolean judgeInput() {
        String phone = mEditPhone.getText().toString();
        if (TextUtils.isEmpty(phone) || !phone.startsWith("1") || phone.length() != 11) {
            ToastUtil.toast(this, "请输入正确的手机号");
            return false;
        }
        String code = mEditCode.getText().toString();
        if (TextUtils.isEmpty(code)) {
            ToastUtil.toast(this, "请输入短信验证码");
            return false;
        }
        return true;
    }

}
