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
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.TransactionData;
import com.iot12369.easylifeandroid.model.TransactionVo;
import com.iot12369.easylifeandroid.mvp.TransactionPresenter;
import com.iot12369.easylifeandroid.mvp.contract.TransactionContract;
import com.iot12369.easylifeandroid.ui.view.EmptyView;
import com.iot12369.easylifeandroid.ui.view.WithBackTitleView;
import com.iot12369.easylifeandroid.util.CommonUtil;

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

    private List<TransactionVo> mRecordList;
    private BaseQuickAdapter<TransactionVo, BaseViewHolder> mAdapter;

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
        mAdapter = new BaseQuickAdapter<TransactionVo, BaseViewHolder>(R.layout.view_recoder_item) {
            @Override
            protected void convert(BaseViewHolder helper, final TransactionVo item) {
                View viewGap = helper.getView(R.id.transaction_top_gap_view);
                if (helper.getPosition() == 0) {
                    viewGap.setVisibility(View.GONE);
                } else {
                    viewGap.setVisibility(View.VISIBLE);
                }
                helper.setText(R.id.transaction_item_date_tv, item.recordCreateTime);//设置时间
                helper.setText(R.id.transaction_item_order_tv, "订单号" + item.orderNo);//设置订单号
                helper.setText(R.id.transaction_item_money, String.format(getString(R.string.yuan_s), CommonUtil.formatAmountByKeepTwo(item.amount)));//金额
                helper.setText(R.id.transaction_item_goods_name, item.title);
                helper.setText(R.id.transaction_item_address, item.body);//金额
                ImageView img = helper.getView(R.id.img_pay_type);
                if ("1".equals(item.chargeType)) {
                    img.setVisibility(View.VISIBLE);
                    img.setImageResource(R.mipmap.pay_wechat);
                } else if ("2".equals(item.chargeType))  {
                    img.setVisibility(View.VISIBLE);
                    img.setImageResource(R.mipmap.pay_zhifubao);
                } else {
                    img.setVisibility(View.GONE);
                }
                helper.getView(R.id.view_transaction_item_root).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        ProgressActivity.newIntent(getContext(), item, 1);
                    }
                });
            }
        };
//        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        mEmptyView.onStart();
        getPresenter().transaction();
    }

    @Override
    public void onRefresh() {
        if (isLoadMore) {
            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }
        isRefresh = true;
        isLoadMore = false;
        getPresenter().transaction();
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
//        isLoadMore = true;
//        isRefresh = false;
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getPresenter().transaction("", "", "", "", "", "");
//            }
//        }, 1000);
    }

    @Override
    public void onDataLoadAgain() {
        getPresenter().transaction();
    }

    @Override
    public void onSuccess(TransactionData transactionData) {
        isRefresh = false;
        isLoadMore = false;
        mSwipeRefreshLayout.setRefreshing(false);
        mRecordList = transactionData.list;
        if (mRecordList != null && mRecordList.size() != 0) {
            mEmptyView.onSuccess();
            mAdapter.setNewData(mRecordList);
        } else {
            mEmptyView.onEmpty();
        }

    }

    @Override
    public void onFailure(String code, String msg) {
        mSwipeRefreshLayout.setRefreshing(false);
        isRefresh = false;
        isLoadMore = false;
        mEmptyView.onError();

    }
}
