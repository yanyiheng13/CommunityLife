package com.community.life.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.community.life.R;
import com.community.life.model.MaintainDta;
import com.community.life.ui.view.WithBackTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能说明： 维修进度和反馈进度
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-26 下午5:30
 * @Copyright (c) 2017. Inc. All rights reserved.
 */

public class MaintainProgressActivity extends BaseActivity {

    @BindView(R.id.title_view)
    WithBackTitleView mTitle;
    //维修问题内容显示框
    @BindView(R.id.maintain_des_tv)
    TextView mTvContent;
    //维修订单号
    @BindView(R.id.maintain_detail_order_tv)
    TextView mTvOrderNum;

    @BindView(R.id.maintain_cant_complete_tv)
    TextView mTvComplete;

    private int mType = 0;//1 维修进度  2 反馈进度
    private MaintainDta mMaintainBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mType = getIntent().getIntExtra("type", 0);
            mMaintainBean = (MaintainDta) getIntent().getSerializableExtra("maintain");
        } else {
            mType = savedInstanceState.getInt("type", 0);
            mMaintainBean = (MaintainDta) savedInstanceState.getSerializable("maintain");
        }
        setContentView(R.layout.activity_maintain_progress);
        ButterKnife.bind(this);
        //设置标题
        mTitle.setText(mType == 1 ? R.string.maintain_progress : R.string.feedback_progress);
        mTvContent.setText(mMaintainBean.des);
        mTvOrderNum.setText(String.format(getString(R.string.maintain_order_number), mMaintainBean.orderNum));

        if (mType == 2) {//反馈进度
            mTvComplete.setText(R.string.very_nice);
        }

    }

    @OnClick({R.id.maintain_cant_resolve_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.maintain_cant_resolve_tv:
                //无法解决按钮点击事件
                break;
            case R.id.maintain_cant_complete_tv:
                //完成维修按钮点击
                break;
        }
    }

    public static void newIntent(Context context, MaintainDta bean, int type) {
        Intent intent = new Intent(context, MaintainProgressActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("maintain", bean);
        context.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("type", mType);
        outState.putSerializable("maintain", mMaintainBean);
    }
}
