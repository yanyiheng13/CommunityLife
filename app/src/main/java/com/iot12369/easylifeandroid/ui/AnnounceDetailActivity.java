package com.iot12369.easylifeandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.widget.TextView;

import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.AnnouncementVo;
import com.iot12369.easylifeandroid.mvp.AnnouncementDetailPresenter;
import com.iot12369.easylifeandroid.mvp.contract.AnnouncementDetailContract;
import com.iot12369.easylifeandroid.ui.view.WithBackTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能说明：
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-11-1
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class AnnounceDetailActivity extends BaseActivity<AnnouncementDetailPresenter> implements AnnouncementDetailContract.View {
    @BindView(R.id.title_view)
    WithBackTitleView mTitleView;
    @BindView(R.id.announcement_item_date_tv)
    TextView mTvDate;
    @BindView(R.id.announcement_item_des_tv)
    TextView mTvDescrip;
    @BindView(R.id.web_view)
    WebView mWebView;

    private AnnouncementVo mAnnouncementVo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mAnnouncementVo = (AnnouncementVo) getIntent().getSerializableExtra("announcement");
        } else {
            mAnnouncementVo = (AnnouncementVo) savedInstanceState.getSerializable("announcement");
        }
        setContentView(R.layout.activity_announcement_detail);
        ButterKnife.bind(this);
        mTitleView.setText(R.string.announce_detail);
//        mTvContent.setText(mAnnouncementVo.noticeContent);
        mWebView.loadDataWithBaseURL(null, mAnnouncementVo.noticeContent, "text/html", "utf-8", null);
        mTvDate.setText(mAnnouncementVo.ctime);
        mTvDescrip.setText(mAnnouncementVo.noticeTitle);
//        getPresenter().announcementDetail(mAnnouncementId);
    }

    public static void newIntent(Context context, AnnouncementVo announcementVo) {
        Intent intent = new Intent(context, AnnounceDetailActivity.class);
        intent.putExtra("announcement", announcementVo);
        context.startActivity(intent);
    }

    @Override
    public void onSuccessAnnouncement(AnnouncementVo announcementVo) {
        mTvDate.setText(announcementVo.ctime);
        mTvDescrip.setText(announcementVo.noticeContent);
    }

    @Override
    public void onErrorAnnouncement(String code, String msg) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("announcement", mAnnouncementVo);
    }
}
