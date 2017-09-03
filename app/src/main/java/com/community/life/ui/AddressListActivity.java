package com.community.life.ui;

import android.app.Activity;
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
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.community.life.R;
import com.community.life.model.AddressData;
import com.community.life.model.TransactionData;
import com.community.life.mvp.AddressListPresenter;
import com.community.life.mvp.contract.AddressListContract;
import com.community.life.ui.view.EmptyView;
import com.community.life.ui.view.WithBackTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能说明： 地址列表
 *
 * @author: 闫毅恒
 * @email： yanyiheng@le.com
 * @version: 1.0
 * @date: 17-8-31
 * @Copyright (c) 2017. 闫毅恒 Inc. All rights reserved.
 */

public class AddressListActivity extends BaseActivity<AddressListPresenter> implements BaseQuickAdapter.RequestLoadMoreListener, AddressListContract.View, SwipeRefreshLayout.OnRefreshListener, EmptyView.OnDataLoadStatusListener {
   public static String TAG_REQUEST_HOME = "addressData";

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

    private boolean isLoadMore, isRefresh;

    private AddressData mAddressData;
    private List<AddressData> mRecordList = new ArrayList<>();
    private BaseQuickAdapter<AddressData, BaseViewHolder> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mAddressData = (AddressData) getIntent().getSerializableExtra(TAG_REQUEST_HOME);
        } else {
            mAddressData = (AddressData) savedInstanceState.getSerializable(TAG_REQUEST_HOME);
        }
        setContentView(R.layout.activity_trasaction_records);
        ButterKnife.bind(this);
        //设置标题
        mTitleView.setText(R.string.my_address).setImageResource(R.mipmap.icon_property);
        //设置刷新样式,这里其实可以抽出去,鉴于此项目刷新不多 没有抽取
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mEmptyView.setOnDataLoadStatusListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new BaseQuickAdapter<AddressData, BaseViewHolder>(R.layout.view_address_item) {
            @Override
            protected void convert(BaseViewHolder helper, final AddressData item) {
                helper.setText(R.id.address_item_tv,  item.address + "==position==" + helper.getPosition());//设置时间

                RelativeLayout rl = helper.getView(R.id.address_item_rl);
                if (helper.getPosition() == 0) {
                    rl.setVisibility(View.VISIBLE);
                } else {
                    rl.setVisibility(View.INVISIBLE);
                }

                helper.getView(R.id.view_item_root).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra(TAG_REQUEST_HOME, item);
                        setResult(Activity.RESULT_OK, intent);
                        AddressListActivity.this.finish();
                    }
                });
            }
        };
        mRecyclerView.setAdapter(mAdapter);

        mEmptyView.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getPresenter().addressList("", "", "");
            }
        }, 1000);
    }

    @Override
    public void onRefresh() {
        if (isLoadMore) {
            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getPresenter().addressList("", "", "");
            }
        }, 1000);
    }

    public static void newIntent(Activity context, AddressData addressData, int requestCode) {
        Intent intent = new Intent(context, AddressListActivity.class);
        intent.putExtra(TAG_REQUEST_HOME, addressData);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onDataLoadAgain() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getPresenter().addressList("", "", "");
            }
        }, 1000);
    }


    @Override
    public void onSuccessAddress(AddressData addressData) {

    }

    @Override
    public void onFailureAddress(String code, String msg) {
        if (mSwipeRefreshLayout == null) {
            return;
        }
        mSwipeRefreshLayout.setRefreshing(false);
        mEmptyView.onSuccess();
        if (isLoadMore) {
            List<AddressData> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                AddressData addressData = new AddressData();
                addressData.address = "天津市塘沽区北清路天鑫家园小区\n1号楼单元406";
                list.add(addressData);
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
                AddressData addressData = new AddressData();
                addressData.address = "天津市塘沽区北清路天鑫家园小区\n1号楼单元406";
                mRecordList.add(addressData);
            }
            mAdapter.setNewData(mRecordList);
        }
        isLoadMore = false;
        isRefresh = false;
    }

    @Override
    public void onLoadMoreRequested() {
        if (isRefresh) {
            return;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getPresenter().addressList("", "", "");
            }
        }, 1000);
        isLoadMore = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(TAG_REQUEST_HOME, mAddressData);
    }
}