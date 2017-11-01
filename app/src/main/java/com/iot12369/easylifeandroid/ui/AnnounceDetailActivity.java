package com.iot12369.easylifeandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.ui.view.WithBackTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能说明：
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-11-1
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class AnnounceDetailActivity extends BaseActivity {
    @BindView(R.id.title_view)
    WithBackTitleView mTitleView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_detail);
        ButterKnife.bind(this);
        mTitleView.setText(R.string.announce_detail);
    }

    public static void newIntent(Context context) {
        Intent intent = new Intent(context, AnnounceDetailActivity.class);
        context.startActivity(intent);
    }
}
