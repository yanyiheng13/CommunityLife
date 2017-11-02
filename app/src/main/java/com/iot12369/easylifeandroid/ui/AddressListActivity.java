package com.iot12369.easylifeandroid.ui;

import android.app.Activity;
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
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.AddressVo;
import com.iot12369.easylifeandroid.mvp.AddressListPresenter;
import com.iot12369.easylifeandroid.mvp.contract.AddressListContract;
import com.iot12369.easylifeandroid.ui.view.EmptyView;
import com.iot12369.easylifeandroid.ui.view.WithBackTitleView;

import java.io.Serializable;
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
   public static String TAG_VO = "addressVo";

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

    private AddressVo mAddressData;
    private List<AddressVo> mListAddress;
    private BaseQuickAdapter<AddressVo, BaseViewHolder> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mListAddress = (List<AddressVo>) getIntent().getSerializableExtra(TAG_REQUEST_HOME);
            mAddressData = (AddressVo) getIntent().getSerializableExtra(TAG_VO);
        } else {
            mListAddress = (List<AddressVo>) savedInstanceState.getSerializable(TAG_REQUEST_HOME);
            mAddressData = (AddressVo) savedInstanceState.getSerializable(TAG_VO);
        }
        setContentView(R.layout.activity_trasaction_records);
        ButterKnife.bind(this);
        //设置标题
        mTitleView.setText(R.string.my_address).setImageResource(R.mipmap.icon_property);
        //设置刷新样式,这里其实可以抽出去,鉴于此项目刷新不多 没有抽取
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//        mEmptyView.setOnDataLoadStatusListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new BaseQuickAdapter<AddressVo, BaseViewHolder>(R.layout.view_address_item) {
            @Override
            protected void convert(BaseViewHolder helper, final AddressVo item) {
                helper.setText(R.id.address_item_tv,  item.communityRawAddress);//设置时间
                RelativeLayout rl = helper.getView(R.id.address_item_rl);

                if (mAddressData != null && mAddressData.communityName.equals(item.communityName)) {
                    rl.setVisibility(View.VISIBLE);
                } else {
                    rl.setVisibility(View.INVISIBLE);
                }

                if (mAddressData == null && helper.getPosition() == 0) {
                    rl.setVisibility(View.VISIBLE);
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
        mAdapter.setNewData(mListAddress);
        if (mListAddress == null || mListAddress.size() == 0) {
            mEmptyView.onEmpty();
        } else {
            mEmptyView.onSuccess();
        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getPresenter().addressList("", "", "");
//            }
//        }, 1000);
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

    public static void newIntent(Activity context, List<AddressVo> addressData, AddressVo addressVo, int requestCode) {
        Intent intent = new Intent(context, AddressListActivity.class);
        intent.putExtra(TAG_REQUEST_HOME, (Serializable) addressData);
        intent.putExtra(TAG_VO, addressVo);
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
    public void onSuccessAddress(AddressVo addressData) {

    }

    @Override
    public void onFailureAddress(String code, String msg) {
        if (mSwipeRefreshLayout == null) {
            return;
        }
        mSwipeRefreshLayout.setRefreshing(false);
        mEmptyView.onSuccess();
        if (isLoadMore) {
            List<AddressVo> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                AddressVo addressData = new AddressVo();
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
//            mRecordList.clear();
//            for (int i = 0; i < 10; i++) {
//                AddressVo addressData = new AddressVo();
//                addressData.address = "天津市塘沽区北清路天鑫家园小区\n1号楼单元406";
//                mRecordList.add(addressData);
//            }
//            mAdapter.setNewData(mRecordList);
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
        outState.putSerializable(TAG_REQUEST_HOME, (Serializable) mListAddress);
        outState.putSerializable(TAG_VO, mAddressData);
    }
}