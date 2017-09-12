package com.iot12369.easylifeandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.ui.view.WithBackTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能说明： 添加人员
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-8-29
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class AddPeopleActivity extends BaseActivity {
    @BindView(R.id.title_view)
    WithBackTitleView mTitleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_people);
        ButterKnife.bind(this);
        mTitleView.setText(R.string.add_people);
    }

    @OnClick(R.id.add_people_tv)
    public void onClick() {

    }

    public static void newIntent(Context context) {
        Intent intent = new Intent(context, AddPeopleActivity.class);
        context.startActivity(intent);
    }
}
