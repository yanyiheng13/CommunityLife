package com.iot12369.easylifeandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.ui.fragment.AnnouncementFragment;
import com.iot12369.easylifeandroid.ui.view.SegmentControl;
import com.iot12369.easylifeandroid.ui.view.WithBackTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @BindView(R.id.segment)
    SegmentControl mSegmentControl;

    private Fragment oldFragment;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        ButterKnife.bind(this);
        mTitleView.setText(R.string.announcement).setImageResource2(R.mipmap.icon_notice_new);
        mSegmentControl.setOnSegmentChangedListener(new SegmentControl.OnSegmentChangedListener() {
            @Override
            public void onSegmentChanged(int newSelectedIndex) {
                setCurrentTab(newSelectedIndex);
            }
        });
        setCurrentTab(0);
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
                    currentFragment = AnnouncementFragment.newIntent(1);
                    break;
                case 1:
                    currentFragment = AnnouncementFragment.newIntent(2);
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
