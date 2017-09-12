package com.iot12369.easylifeandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.TransactionData;
import com.iot12369.easylifeandroid.mvp.TransactionPresenter;
import com.iot12369.easylifeandroid.mvp.contract.TransactionContract;
import com.iot12369.easylifeandroid.ui.view.EmptyView;
import com.iot12369.easylifeandroid.ui.view.WithBackTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能说明： 交易记录
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-27 下午2:52
 * @Copyright (c) 2017. Inc. All rights reserved.
 */

public class TransactionRecordsActivity extends BaseActivity<TransactionPresenter> implements TransactionContract.View, EmptyView.OnDataLoadStatusListener, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{
    //标题
    @BindView(R.id.title_view)
    WithBackTitleView mTitleView;
    //刷新
    @BindView(R.id.transaction_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.transaction_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.empty_view)
    EmptyView mEmptyView;

    private boolean isRefresh, isLoadMore;

    private List<TransactionData> mRecordList = new ArrayList<>();
    private BaseQuickAdapter<TransactionData, BaseViewHolder> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trasaction_records);
        ButterKnife.bind(this);
        //设置标题
        mTitleView.setText(R.string.pay_recode).setImageResource(R.mipmap.icon_pay_record);
        //设置刷新样式,这里其实可以抽出去,鉴于此项目刷新不多 没有抽取
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mEmptyView.setOnDataLoadStatusListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new BaseQuickAdapter<TransactionData, BaseViewHolder>(R.layout.view_recoder_item) {
            @Override
            protected void convert(BaseViewHolder helper, final TransactionData item) {
                View viewGap = helper.getView(R.id.transaction_top_gap_view);
                if (helper.getPosition() == 0) {
                    viewGap.setVisibility(View.VISIBLE);
                } else {
                    viewGap.setVisibility(View.GONE);
                }
                helper.setText(R.id.transaction_item_date_tv, String.format(getString(R.string.maintain_date), item.time));//设置时间
                //TODO : 这里的金额涉及单位换算  后续需要修改
                helper.setText(R.id.transaction_item_money_tv, String.format(getString(R.string.transaction_money), item.money));//设置金额
                helper.setText(R.id.transaction_item_order_tv, String.format(getString(R.string.maintain_order_number), item.orderNum));//设置订单号

                helper.getView(R.id.view_transaction_item_root).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        ProgressActivity.newIntent(getContext(), item, 1);
                    }
                });
            }
        };
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        mEmptyView.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getPresenter().transaction("", "", "", "", "", "");
            }
        }, 1000);
    }

    @Override
    public void onRefresh() {
        if (isLoadMore) {
            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }
        isRefresh = true;
        isLoadMore = false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getPresenter().transaction("", "", "", "", "", "");
            }
        }, 1000);
    }

    public static void newIntent(Context context) {
        Intent intent = new Intent(context, TransactionRecordsActivity.class);
        context.startActivity(intent);
    }

    //上拉加载更多
    @Override
    public void onLoadMoreRequested() {
        if (isRefresh) {
            return;
        }
        isLoadMore = true;
        isRefresh = false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getPresenter().transaction("", "", "", "", "", "");
            }
        }, 1000);
    }

    @Override
    public void onDataLoadAgain() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getPresenter().transaction("", "", "", "", "", "");
            }
        }, 1000);
    }

    @Override
    public void onSuccess(TransactionData transactionData) {

    }

    @Override
    public void onFailure(String code, String msg) {
        if (mSwipeRefreshLayout == null) {
            return;
        }
        mSwipeRefreshLayout.setRefreshing(false);
        mEmptyView.onSuccess();
        if (isLoadMore) {
            List<TransactionData> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                TransactionData recordsBean = new TransactionData();
                recordsBean.money = "4,000";
                recordsBean.orderNum = "100055522";
                recordsBean.time = "2017-08-12";
                list.add(recordsBean);
            }
//            if ("1".equals(mStaffList.isLastPage)) {
//                mAdapter.loadMoreEnd(true);
//            } else {
//                mAdapter.setEnableLoadMore(true);
//            }
            mAdapter.addData(list);
            mAdapter.loadMoreEnd(false);
        } else {
            mRecordList.clear();
            for (int i = 0; i < 10; i++) {
                TransactionData recordsBean = new TransactionData();
                recordsBean.money = "4,000";
                recordsBean.orderNum = "100055522";
                recordsBean.time = "2017-08-12";
                mRecordList.add(recordsBean);
            }
            mAdapter.setNewData(mRecordList);
        }
        isRefresh = false;
        isLoadMore = false;
    }
}
