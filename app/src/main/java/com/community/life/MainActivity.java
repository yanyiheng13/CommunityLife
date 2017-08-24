package com.community.life;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.community.life.ui.BaseActivity;
import com.community.life.ui.fragment.ComplainFragment;
import com.community.life.ui.fragment.HomeFragment;
import com.community.life.ui.fragment.MaintainFragment;
import com.community.life.ui.fragment.MineFragment;
import com.community.life.ui.fragment.UnlockingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能说明： 主Activity
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-24 上午12:01
 * @Copyright (c) 2017. Inc. All rights reserved.
 */
public class MainActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {
    //底部导航控件
    @BindView(R.id.tabLayout)
    TabLayout mBottomView;

    private Fragment oldFragment;

    //底部导航资源id
    private TextView[] mTxtArray = new TextView[5];
    private ImageView[] mImageArray = new ImageView[5];
    private int[] mStringId = {R.string.title_home, R.string.title_maintain, R.string.title_unlocking, R.string.title_complain, R.string.title_mine};
    private int[] mDrawableId = {R.drawable.nav_home, R.drawable.nav_maintain, R.drawable.nav_unlocking, R.drawable.nav_complain, R.drawable.nav_mine};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 初始操作
     */
    private void init() {
        for (int i = 0; i < 5; i++) {
            mBottomView.addTab(mBottomView.newTab().setCustomView(getCustomTabView(i)));
        }
        mBottomView.addOnTabSelectedListener(this);
        setCurrentTab(2);
    }

    private View getCustomTabView(int position) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_tab_item, null);
        mImageArray[position] = (ImageView) view.findViewById(R.id.tab_image);
        mTxtArray[position] = (TextView) view.findViewById(R.id.tab_text);

        mImageArray[position].setImageResource(mDrawableId[position]);
        mTxtArray[position].setText(mStringId[position]);

        return view;
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
                    currentFragment = new HomeFragment();
                    break;
                case 1:
                    currentFragment = new MaintainFragment();
                    break;
                case 2:
                    currentFragment = new UnlockingFragment();
                    break;
                case 3:
                    currentFragment = new ComplainFragment();
                    break;
                case 4:
                    currentFragment = new MineFragment();
                    break;
                default:
                    break;
            }
            ft.add(R.id.content, currentFragment, String.valueOf(position));
        } else {
            ft.show(currentFragment);
        }
        oldFragment = currentFragment;
        ft.commitAllowingStateLoss();
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        setCurrentTab(tab.getPosition());
        setTabTextIcon(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public static void newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    private void setTabTextIcon(int position) {
        setCurrentTab(position);
        for (int i = 0; i < 2; i++) {
            if (i == position) {
                mImageArray[i].setSelected(true);
                mTxtArray[i].setSelected(true);
            } else {
                mImageArray[i].setSelected(false);
                mTxtArray[i].setSelected(false);
            }
        }
    }
}
