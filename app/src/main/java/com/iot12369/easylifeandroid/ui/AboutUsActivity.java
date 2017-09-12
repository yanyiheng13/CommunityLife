package com.iot12369.easylifeandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.ui.view.NoLineTextView;
import com.iot12369.easylifeandroid.ui.view.WithBackTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能说明：关于我们
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-26 下午7:42
 * @Copyright (c) 2017. Inc. All rights reserved.
 */

public class AboutUsActivity extends BaseActivity {


    @BindView(R.id.title_view)
    WithBackTitleView mTitleView;
    //版本号
    @BindView(R.id.about_us_top_version)
    TextView mTvVersion;
    //电话1
    @BindView(R.id.about_us_phone1_tv)
    NoLineTextView mTvPhoneOne;
    //电话2
    @BindView(R.id.about_us_phone2_tv)
    NoLineTextView mTvPhoneTwo;
    //微信公众号
    @BindView(R.id.about_us_wechat_tv)
    TextView mTvWeChatNum;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        //设置标题
        mTitleView.setText(R.string.about_us).setImageResource(R.mipmap.icon_about_us);
        init();
    }

    private void init() {
        mTvVersion.setText(String.format(getString(R.string.version), "V1.0.0"));
        mTvWeChatNum.setText(String.format(getString(R.string.wechat_num), "10000eerss"));
    }


    public static void newIntent(Context context) {
        Intent intent = new Intent(context, AboutUsActivity.class);
        context.startActivity(intent);
    }
}
