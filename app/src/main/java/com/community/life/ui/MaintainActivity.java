package com.community.life.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.community.life.R;
import com.community.life.ui.view.WithBackTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能说明： 报修界面
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-25 下午11:36
 * @Copyright (c) 2017. Inc. All rights reserved.
 */

public class MaintainActivity extends BaseActivity {
    @BindView(R.id.title_view)
    WithBackTitleView mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain);
        ButterKnife.bind(this);
        mTitle.setText(R.string.maintain);
    }

    public static void newIntent(Context context) {
        Intent intent = new Intent(context, MaintainActivity.class);
        context.startActivity(intent);
    }
}
