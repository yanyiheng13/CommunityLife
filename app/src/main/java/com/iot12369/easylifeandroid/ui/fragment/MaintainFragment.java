package com.iot12369.easylifeandroid.ui.fragment;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.MaintainData;
import com.iot12369.easylifeandroid.model.MaintainVo;
import com.iot12369.easylifeandroid.mvp.MaintainPresenter;
import com.iot12369.easylifeandroid.mvp.contract.MaintainContract;
import com.iot12369.easylifeandroid.ui.BaseFragment;
import com.iot12369.easylifeandroid.ui.SubmitProblemActivity;
import com.iot12369.easylifeandroid.ui.ProgressActivity;
import com.iot12369.easylifeandroid.ui.view.EmptyView;
import com.iot12369.easylifeandroid.ui.view.IconTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 功能说明： 维修列表
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-8-24
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class MaintainFragment extends BaseFragment<MaintainPresenter> implements EmptyView.OnDataLoadStatusListener, SwipeRefreshLayout.OnRefreshListener, MaintainContract.View {
    @BindView(R.id.title_view)
    IconTitleView mTitleView;
    @BindView(R.id.maintain_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.maintain_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.add_ll)
    LinearLayout mLiAdd;

    @BindView(R.id.empty_view)
    EmptyView mEmptyView;

    private BaseQuickAdapter<MaintainVo, BaseViewHolder> mAdapter;
    private List<MaintainVo> mListMaintain = new ArrayList<>();

    @Override
    public int inflateId() {
        return R.layout.fragment_maintain;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置标题title和标题资源
        mTitleView.setText(R.string.title_maintain).setImageResource(R.mipmap.title_maintain);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new BaseQuickAdapter<MaintainVo, BaseViewHolder>(R.layout.view_maintain_item) {
            @Override
            protected void convert(BaseViewHolder helper, final MaintainVo item) {
                View viewGap = helper.getView(R.id.maintain_top_gap_view);
                if (helper.getPosition() == 0) {
                    viewGap.setVisibility(View.VISIBLE);
                } else {
                    viewGap.setVisibility(View.GONE);
                }
                helper.setText(R.id.maintain_item_date_tv, String.format(getString(R.string.maintain_date), item.workorder_ctime));//设置时间
                helper.setText(R.id.maintain_item_des_tv, TextUtils.isEmpty(item.workorder_desc) ? "仅有图片" : item.workorder_desc);//设置描述
                helper.setText(R.id.maintain_item_order_tv, String.format(getString(R.string.maintain_order_number), item.workorder_sn));//设置订单号

                Drawable d = MaintainFragment.this.getContext().getResources().getDrawable(R.drawable.bg_maintain_status);
                GradientDrawable customBackGround = (GradientDrawable)d;
                String statusDes = "";
                int color = 0XFFF39D77;
                switch (item.workorder_state) {
                    case "1":
                    case "2":
                        statusDes = "处理中";
                        color = 0XFFF39D77;
                        break;
                    case "3":
                        statusDes = "待反馈";
                        color = 0XFFE8641B;
                        break;
                    case "4":
                        statusDes = "已解决";
                        color = 0XFF3FA343;
                        break;
                    default:
                        break;
                }

                //维修状态
                TextView tvStatus = helper.getView(R.id.maintain_item_status_tv);
                if (customBackGround != null) {
                    customBackGround.setColor(color);
                }
                tvStatus.setBackground(customBackGround);
                tvStatus.setText(statusDes);

                helper.getView(R.id.view_maintain_item_root).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProgressActivity.newIntent(getContext(), item, 1);
                    }
                });
            }
        };
//        View footView = LayoutInflater.from(getContext()).inflate(R.layout.view_maintain_add, null);
//        mAdapter.addFooterView(footView);
        mLiAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitProblemActivity.newIntent(MaintainFragment.this.getContext(), 1);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.loadMoreEnd(false);
        mEmptyView.onStart();
        getPresenter().maintain(LeApplication.mUserInfo.phone, "1");
    }

    @Override
    public void onRefresh() {
        getPresenter().maintain(LeApplication.mUserInfo.phone, "1");
    }

    @Override
    public void onSuccessMaintain(MaintainData maintainBean) {
        if (mSwipeRefreshLayout == null) {
            return;
        }
        mSwipeRefreshLayout.setRefreshing(false);
        mEmptyView.onSuccess();
        mLiAdd.setVisibility(View.VISIBLE);
        mAdapter.setNewData(maintainBean.list);

    }

    @Override
    public void onFailureMaintain(String code, String msg) {
        if (mSwipeRefreshLayout == null) {
            return;
        }
        mEmptyView.onError();
    }

    @Override
    public void onDataLoadAgain() {

    }
}
