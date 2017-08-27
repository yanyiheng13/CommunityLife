package com.community.life.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.community.life.R;
import com.community.life.ui.fragment.AnnouncementFragment;
import com.community.life.ui.fragment.ComplainFragment;
import com.community.life.ui.fragment.HomeFragment;
import com.community.life.ui.fragment.MaintainFragment;
import com.community.life.ui.fragment.MineFragment;
import com.community.life.ui.fragment.PayFragment;
import com.community.life.ui.view.WithBackTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能说明： 公告列表
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-27 下午8:29
 * @Copyright (c) 2017. Inc. All rights reserved.
 */

public class AnnouncementActivity extends BaseActivity {

    @BindView(R.id.title_view)
    WithBackTitleView mTitleView;

    @BindView(R.id.announcement_system_view)
    View mSystemLine;
    @BindView(R.id.announcement_community_view)
    View mCommunityLine;

    private Fragment oldFragment;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        ButterKnife.bind(this);
        //TODO: 这里公告的图标没有切  需要后期替换
        mTitleView.setText(R.string.announcement).setImageResource(R.mipmap.icon_pay_record);
        setCurrentTab(0);
    }

    @OnClick({R.id.announcement_system_rl, R.id.announcement_community_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.announcement_system_rl:
                mSystemLine.setVisibility(View.VISIBLE);
                mCommunityLine.setVisibility(View.INVISIBLE);
                setCurrentTab(0);
                break;
            case R.id.announcement_community_rl:
                mCommunityLine.setVisibility(View.VISIBLE);
                mSystemLine.setVisibility(View.INVISIBLE);
                setCurrentTab(1);
                break;
            default:
                break;
        }
    }

    /**
     * fragment的切换
     */
    private void setCurrentTab(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(position));
        if (oldFragment != null) {
            ft.hide(oldFragment);
        }
        if (currentFragment == null) {
            switch (position) {
                case 0:
                    currentFragment = new AnnouncementFragment();
                    break;
                case 1:
                    currentFragment = new AnnouncementFragment();
                    break;
            }
            ft.add(R.id.content, currentFragment, String.valueOf(position));
        } else {
            ft.show(currentFragment);
        }
        oldFragment = currentFragment;
        ft.commitAllowingStateLoss();
    }

    public static void newIntent(Context context) {
        Intent intent = new Intent(context, AnnouncementActivity.class);
        context.startActivity(intent);
    }

    /**
     * 处理至于后台长时间回来界面重叠的bug
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }
}
