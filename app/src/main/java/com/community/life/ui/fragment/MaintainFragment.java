package com.community.life.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.community.life.R;
import com.community.life.model.MaintainBean;
import com.community.life.ui.BaseFragment;
import com.community.life.ui.MaintainActivity;
import com.community.life.ui.view.IconTitleView;

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
public class MaintainFragment extends BaseFragment {
    @BindView(R.id.title_view)
    IconTitleView mTitleView;
    @BindView(R.id.maintain_recycler_view)
    RecyclerView mRecyclerView;

    private BaseQuickAdapter<MaintainBean, BaseViewHolder> mAdapter;
    private List<MaintainBean> mListMaintain;

    @Override
    public int inflateId() {
        return R.layout.fragment_maintain;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置标题title和标题资源
        mTitleView.setText(R.string.title_maintain).setImageResource(R.mipmap.title_maintain);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new BaseQuickAdapter<MaintainBean, BaseViewHolder>(R.layout.view_maintain_item) {
            @Override
            protected void convert(BaseViewHolder helper, MaintainBean item) {
                View viewGap = helper.getView(R.id.maintain_top_gap_view);
                if (helper.getPosition() == 0) {
                    viewGap.setVisibility(View.VISIBLE);
                } else {
                    viewGap.setVisibility(View.GONE);
                }
                helper.setText(R.id.maintain_item_date_tv, String.format(getString(R.string.maintain_date), item.time));//设置时间
                helper.setText(R.id.maintain_item_des_tv, item.des);//设置描述
                helper.setText(R.id.maintain_item_order_tv, String.format(getString(R.string.maintain_order_number), item.orderNum));//设置订单号
                String statusDes = "";
                switch (item.status) {
                    case "1":
                        statusDes = "待处理";
                        break;
                    case "2":
                        statusDes = "有问题";
                        break;
                    case "3":
                        statusDes = "已解决";
                        break;
                    default:
                        break;
                }
                //维修状态
                TextView tvStatus = helper.getView(R.id.maintain_item_status_tv);
                tvStatus.setText(statusDes);
            }
        };
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_maintain_add, null);
        mAdapter.addFooterView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaintainActivity.newIntent(MaintainFragment.this.getContext());
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mListMaintain = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MaintainBean maintainBean = new MaintainBean();
            maintainBean.des = "十分疯狂开始说的方法是第三方的速度大多数第三方第三方";
            maintainBean.orderNum = "100055522";
            if (i % 3 == 0) {
                maintainBean.status = "1";
            } else if (i % 3 == 1) {
                maintainBean.status = "2";
            } else {
                maintainBean.status = "3";
            }
            maintainBean.time = "2017-08-12";
            mListMaintain.add(maintainBean);
        }
        mAdapter.setNewData(mListMaintain);
        mAdapter.loadMoreEnd(false);

    }
}
