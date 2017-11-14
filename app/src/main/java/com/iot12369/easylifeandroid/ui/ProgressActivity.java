package com.iot12369.easylifeandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.IsOkData;
import com.iot12369.easylifeandroid.model.MaintainVo;
import com.iot12369.easylifeandroid.model.RepairOrderDetailData;
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
    //维修状态的文字显示
    @BindView(R.id.maintain_progress_one_tv)
    TextView mTvStatusOne;
    @BindView(R.id.maintain_progress_two_tv)
    TextView mTvStatusTwo;
    @BindView(R.id.maintain_progress_three_tv)
    TextView mTvStatuThree;
    @BindView(R.id.maintain_progress_four_tv)
    TextView mTvStatuFour;
    //维修状态的icon显示
    @BindView(R.id.maintain_progress_one_img)
    ImageView mImgStatusOne;
    @BindView(R.id.maintain_progress_two_img)
    ImageView mImgStatusTwo;
    @BindView(R.id.maintain_progress_three_img)
    ImageView mImgStatuThree;
    @BindView(R.id.maintain_progress_four_img)
    ImageView mImgStatuFour;

    ImageView[] mImgArray = null;
    TextView[] mTvArray = null;

    String[] mTxtArray = {"投诉工单已提交等待物业公司调查中...", "投诉已安排工作人员处理中",
            "投诉已处理点击下方按钮反馈状态", "已完结"};

    private int mType = 0;//1 维修进度  2 反馈进度
    private MaintainVo mMaintainBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mType = getIntent().getIntExtra("type", 0);
            mMaintainBean = (MaintainVo) getIntent().getSerializableExtra("maintain");
        } else {
            mType = savedInstanceState.getInt("type", 0);
            mMaintainBean = (MaintainVo) savedInstanceState.getSerializable("maintain");
        }
        setContentView(R.layout.activity_maintain_progress);
        ButterKnife.bind(this);
        mImgArray = new ImageView[]{mImgStatusOne, mImgStatusTwo, mImgStatuThree, mImgStatuFour};
        mTvArray = new TextView[]{mTvStatusOne, mTvStatusTwo, mTvStatuThree, mTvStatuFour};
        //设置标题
        mTitle.setText(mType == 1 ? R.string.maintain_progress : R.string.feedback_progress);
        mTvContent.setText(mMaintainBean.workorder_desc);
        mTvOrderNum.setText(String.format(getString(R.string.maintain_order_number), mMaintainBean.workorder_sn));
        mEmptyView.setOnDataLoadStatusListener(this);
        if (mType == 2) {//反馈进度
            mTvComplete.setText(R.string.very_nice);
        }
        String status = mMaintainBean.workorder_state;
        int statusNum = 0;
        if (!TextUtils.isEmpty(status)) {
            statusNum = Integer.valueOf(status);
        }
        for (int i = 1; i < 5; i++) {
            if (i <= statusNum) {
                mImgArray[i - 1].setBackgroundResource(R.mipmap.icon_arrow_light);
            } else {
                mImgArray[i - 1].setBackgroundResource(R.mipmap.icon_arrow_dark);
            }
            if (mType == 2) {
                mTvArray[i - 1].setText(mTxtArray[i - 1]);
            }
        }
        mEmptyView.onStart();
        askData();

    }

    @OnClick({R.id.maintain_cant_resolve_tv, R.id.maintain_cant_complete_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.maintain_cant_resolve_tv:
                //无法解决按钮点击事件
                LoadingDialog.show(this, false);
                if (mType == 2) {//投诉
                    getPresenter().repairOrderComplainBack(mMaintainBean.workorder_sn, "2");//2不能解决
                } else {//维修
                    getPresenter().repairOrderMaintainBack(mMaintainBean.workorder_sn, "2");//不能解决
                }
                break;
            case R.id.maintain_cant_complete_tv:
                LoadingDialog.show(this, false);
                //完成维修按钮点击
                if (mType == 2) {//投诉
                    getPresenter().setRepairOrderComplainState(mMaintainBean.workorder_sn, "4");
                } else {//维修
                    getPresenter().setRepairOrderStateMaintain(mMaintainBean.workorder_sn, "4");
                }
                break;
        }

    }

    public void askData() {
        getPresenter().repairOrderDetail(mMaintainBean.workorder_sn);
    }

    @Override
    public void onSuccessRepairOrderDetail(RepairOrderDetailData maintainBean) {
        mEmptyView.onSuccess();
    }

    @Override
    public void onFailureRepairOrderDetail(String code, String msg) {
        mEmptyView.onSuccess();
    }

    @Override
    public void onSuccessRepairOrderMaintain(IsOkData isOkData) {
        if (mEmptyView == null) {
            return;
        }
        LoadingDialog.hide();
        Toast.makeText(this, "反馈成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailureRepairOrderMaintain(String code, String msg) {
        if (mEmptyView == null) {
            return;
        }
        LoadingDialog.hide();
    }

    @Override
    public void onSuccessRepairOrderComplain(IsOkData isOkData) {
        if (mEmptyView == null) {
            return;
        }
        LoadingDialog.hide();
        Toast.makeText(this, "反馈成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailureRepairOrderComplain(String code, String msg) {
        if (mEmptyView == null) {
            return;
        }
        LoadingDialog.hide();
    }

    @Override
    public void onSuccessRepairOrderMaintainBack(IsOkData isOkData) {
        if (mEmptyView == null) {
            return;
        }
        LoadingDialog.hide();
        Toast.makeText(this, "反馈成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailureRepairOrderMaintainBack(String code, String msg) {
        if (mEmptyView == null) {
            return;
        }
        LoadingDialog.hide();
    }

    @Override
    public void onSuccessRepairOrderComplainBack(IsOkData isOkData) {
        if (mEmptyView == null) {
            return;
        }
        LoadingDialog.hide();
        Toast.makeText(this, "反馈成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailureRepairOrderComplainBack(String code, String msg) {
        if (mEmptyView == null) {
            return;
        }
        LoadingDialog.hide();
    }

    @Override
    public void onDataLoadAgain() {
        askData();
    }

    public static void newIntent(Context context, MaintainVo bean, int type) {
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
