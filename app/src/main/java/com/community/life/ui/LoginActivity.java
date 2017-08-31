package com.community.life.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.community.life.MainActivity;
import com.community.life.R;
import com.community.life.model.IsOkData;
import com.community.life.model.LoginData;
import com.community.life.mvp.LoginPresent;
import com.community.life.mvp.contract.LoginContract;
import com.community.life.ui.view.LoadingDialog;
import com.community.life.ui.view.WithBackTitleView;
import com.community.life.util.ToastUtil;

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

    @BindView(R.id.title_view)
    WithBackTitleView mTitleView;

    @BindView(R.id.login_get_code_tv)
    TextView mTvGetCode;

    @BindView(R.id.login_phone_edit)
    EditText mEditPhone;
    @BindView(R.id.login_code_edit)
    EditText mEditCode;

    private CountDownTimer mDownTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mTitleView.setText(R.string.login);
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
                break;
            case R.id.btn_login:
                if (judgeInput()) {
                    getPresenter().login("", "", "");
                    LoadingDialog.show(this, false);
                }
                break;
            //语音短信验证码
            case R.id.login_by_voice_tv:
                break;
            default:
                break;
        }
    }

    public static void newIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onSuccessLogin(LoginData loginData) {

    }

    @Override
    public void onFailureLogin(String code, String msg) {
        LoadingDialog.hide();
        MainActivity.newIntent(this);
    }

    @Override
    public void onSuccessCode(IsOkData isOkData) {

    }

    @Override
    public void onFailureCode(String code, String msg) {

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
