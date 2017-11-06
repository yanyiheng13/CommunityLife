package com.iot12369.easylifeandroid.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.AnnouncementData;
import com.iot12369.easylifeandroid.model.AnnouncementVo;
import com.iot12369.easylifeandroid.mvp.AnnouncementPresenter;
import com.iot12369.easylifeandroid.mvp.contract.AnnouncementContract;
import com.iot12369.easylifeandroid.ui.AnnounceDetailActivity;
import com.iot12369.easylifeandroid.ui.BaseFragment;
import com.iot12369.easylifeandroid.ui.view.EmptyView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 功能说明：
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-27 下午9:03
 * @Copyright (c) 2017. Inc. All rights reserved.
 */

public class AnnouncementFragment extends BaseFragment<AnnouncementPresenter> implements EmptyView.OnDataLoadStatusListener, AnnouncementContract.View, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.announcement_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.announcement_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.empty_view)
    EmptyView mEmptyView;

    private BaseQuickAdapter<AnnouncementVo, BaseViewHolder> mAdapter;

    private int mTag;
    private boolean isLoadMore, isRefresh;

    @Override
    public int inflateId() {
        return R.layout.fragment_announcement_list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            mTag = getArguments().getInt("tag");
        } else {
            mTag = savedInstanceState.getInt("tag");
        }

        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new BaseQuickAdapter<AnnouncementVo, BaseViewHolder>(R.layout.view_announcement_item) {
            @Override
            protected void convert(BaseViewHolder helper, final AnnouncementVo item) {

                helper.setText(R.id.announcement_item_date_tv, item.createTime);//设置时间
                helper.setText(R.id.announcement_item_des_tv, item.noticeTitle);//设置描述

                helper.getView(R.id.view_announcement_item_root).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AnnounceDetailActivity.newIntent(getContext(), item);
                    }
                });
            }
        };
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mEmptyView.setOnDataLoadStatusListener(this);
        mEmptyView.onStart();
        if (mTag == 1) {
            getPresenter().announcementSystem("0", "10");
        } else {
            getPresenter().announcementCommunity("0", "10", LeApplication.mUserInfo.phone);
        }
    }

    @Override
    public void onRefresh() {
        if (isLoadMore) {
            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }
        isRefresh = true;
        isLoadMore = false;
        if (mTag == 1) {
            getPresenter().announcementSystem("0", "10");
        } else {
            getPresenter().announcementCommunity("0", "10", LeApplication.mUserInfo.phone);
        }
    }

    //上拉加载更多
    @Override
    public void onLoadMoreRequested() {
        if (isRefresh) {
            return;
        }
        isLoadMore = true;
        isRefresh = false;
        if (mTag == 1) {
            getPresenter().announcementSystem("0", "10");
        } else {
            getPresenter().announcementCommunity("0", "10", LeApplication.mUserInfo.phone);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tag", mTag);
    }

    public static AnnouncementFragment newIntent(int tag) {
        AnnouncementFragment fragment = new AnnouncementFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tag", tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onSuccessAnnouncement(AnnouncementData announcementData) {
        if (mSwipeRefreshLayout == null) {
            return;
        }
        mSwipeRefreshLayout.setRefreshing(false);
        mEmptyView.onSuccess();
        mAdapter.setNewData(announcementData.list);
        mAdapter.loadMoreEnd(false);
        isRefresh = false;
        isLoadMore = false;
    }

    @Override
    public void onErrorAnnouncement(String code, String msg) {

    }

    @Override
    public void onDataLoadAgain() {
        if (mTag == 1) {
            getPresenter().announcementSystem("0", "10");
        } else {
            getPresenter().announcementCommunity("0", "10", LeApplication.mUserInfo.phone);
        }
    }
}
