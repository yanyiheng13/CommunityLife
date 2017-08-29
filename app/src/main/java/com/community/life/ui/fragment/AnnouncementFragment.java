package com.community.life.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.community.life.R;
import com.community.life.model.AnnouncementBean;
import com.community.life.ui.BaseFragment;

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

public class AnnouncementFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.announcement_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.announcement_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private BaseQuickAdapter<AnnouncementBean, BaseViewHolder> mAdapter;
    private List<AnnouncementBean> mListMaintain;

    private boolean isLoadMore, isRefresh;

    @Override
    public int inflateId() {
        return R.layout.fragment_announcement_list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new BaseQuickAdapter<AnnouncementBean, BaseViewHolder>(R.layout.view_announcement_item) {
            @Override
            protected void convert(BaseViewHolder helper, final AnnouncementBean item) {

                helper.setText(R.id.announcement_item_date_tv, item.time);//设置时间
                helper.setText(R.id.announcement_item_des_tv, item.content);//设置描述

                helper.getView(R.id.view_announcement_item_root).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        MaintainProgressActivity.newIntent(getContext(), item, 1);
                    }
                });
            }
        };
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mListMaintain = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            AnnouncementBean maintainBean = new AnnouncementBean();
            maintainBean.content = "十分疯狂开始说的方法是第三方的速度大多数第三方第三方";
            maintainBean.time = "2017-08-10";
            mListMaintain.add(maintainBean);
        }
        mAdapter.setNewData(mListMaintain);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        }, 2000);
    }

    //上拉加载更多
    @Override
    public void onLoadMoreRequested() {

    }
}
