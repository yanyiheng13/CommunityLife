package com.iot12369.easylifeandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.ComplainProgressData;
import com.iot12369.easylifeandroid.model.IsOkData;
import com.iot12369.easylifeandroid.model.MaintainData;
import com.iot12369.easylifeandroid.model.MaintainProgressData;
import com.iot12369.easylifeandroid.mvp.ProgressPresenter;
import com.iot12369.easylifeandroid.mvp.contract.ProgressContract;
import com.iot12369.easylifeandroid.ui.view.EmptyView;
import com.iot12369.easylifeandroid.ui.view.LoadingDialog;
import com.iot12369.easylifeandroid.ui.view.WithBackTitleView;

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

public class ProgressActivity extends BaseActivity<ProgressPresenter> implements ProgressContract.View, EmptyView.OnDataLoadStatusListener {

    @BindView(R.id.title_view)
    WithBackTitleView mTitle;
    //维修问题内容显示框
    @BindView(R.id.maintain_des_tv)
    TextView mTvContent;
    //维修订单号
    @BindView(R.id.maintain_detail_order_tv)
    TextView mTvOrderNum;

    @BindView(R.id.maintain_phone_num_tv)
    TextView mTvPhone;

    @BindView(R.id.maintain_cant_complete_tv)
    TextView mTvComplete;
    @BindView(R.id.empty_view)
    EmptyView mEmptyView;

    private int mType = 0;//1 维修进度  2 反馈进度
    private MaintainData mMaintainBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mType = getIntent().getIntExtra("type", 0);
            mMaintainBean = (MaintainData) getIntent().getSerializableExtra("maintain");
        } else {
            mType = savedInstanceState.getInt("type", 0);
            mMaintainBean = (MaintainData) savedInstanceState.getSerializable("maintain");
        }
        setContentView(R.layout.activity_maintain_progress);
        ButterKnife.bind(this);
        //设置标题
        mTitle.setText(mType == 1 ? R.string.maintain_progress : R.string.feedback_progress);
        mTvContent.setText(mMaintainBean.des);
        mTvOrderNum.setText(String.format(getString(R.string.maintain_order_number), mMaintainBean.orderNum));
        mEmptyView.setOnDataLoadStatusListener(this);

        if (mType == 2) {//反馈进度
            mTvComplete.setText(R.string.very_nice);
        }
        mEmptyView.onStart();
        askData();

    }

    @OnClick({R.id.maintain_cant_resolve_tv, R.id.maintain_cant_complete_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.maintain_cant_resolve_tv:
                //无法解决按钮点击事件
                LoadingDialog.show(this);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getPresenter().noSolve("");
                    }
                }, 1000);
                break;
            case R.id.maintain_cant_complete_tv:
                //完成维修按钮点击
                LoadingDialog.show(this);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getPresenter().statistic("");
                    }
                }, 1000);
                break;
        }

    }

    public void askData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mType == 1) {
                    getPresenter().maintainProgress("");
                } else {
                    getPresenter().complainProgress("");
                }
            }
        }, 1000);
    }

    @Override
    public void onSuccessMaintain(MaintainProgressData maintainBean) {

    }

    @Override
    public void onFailureMaintain(String code, String msg) {
        mEmptyView.onSuccess();
    }

    @Override
    public void onSuccessComplain(ComplainProgressData complainData) {

    }

    @Override
    public void onFailureComplain(String code, String msg) {
        mEmptyView.onSuccess();
    }

    @Override
    public void onSuccessNoSolve(IsOkData isOkData) {

    }

    @Override
    public void onFailureNoSolve(String code, String msg) {
        if (mEmptyView == null) {
            return;
        }
        LoadingDialog.hide();
        Toast.makeText(this, "您的反馈信息,我们将第一时间核查处理", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccessSatisfaction(IsOkData isOkData) {

    }

    @Override
    public void onFailureSatisfaction(String code, String msg) {
        if (mEmptyView == null) {
            return;
        }
        LoadingDialog.hide();
        Toast.makeText(this, "您的满意是我们前进的动力", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDataLoadAgain() {
        askData();
    }

    public static void newIntent(Context context, MaintainData bean, int type) {
        Intent intent = new Intent(context, ProgressActivity.class);
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
