package com.iot12369.easylifeandroid;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iot12369.easylifeandroid.model.AddressData;
import com.iot12369.easylifeandroid.model.AddressVo;
import com.iot12369.easylifeandroid.ui.AddAddressActivity;
import com.iot12369.easylifeandroid.ui.BaseActivity;
import com.iot12369.easylifeandroid.ui.fragment.ComplainFragment;
import com.iot12369.easylifeandroid.ui.fragment.PayFragment;
import com.iot12369.easylifeandroid.ui.fragment.MaintainFragment;
import com.iot12369.easylifeandroid.ui.fragment.MineFragment;
import com.iot12369.easylifeandroid.ui.fragment.HomeFragment;
import com.iot12369.easylifeandroid.ui.view.MyDialog;
import com.iot12369.easylifeandroid.util.SharePrefrenceUtil;
import com.iot12369.easylifeandroid.util.UiTitleBarUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

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
    @BindView(R.id.virtual_status_bar)
    View mStatusBar;

    private Fragment oldFragment;

    //底部导航资源id
    private TextView[] mTxtArray = new TextView[5];
    private ImageView[] mImageArray = new ImageView[5];
//    private int[] mStringId = {R.string.title_home, R.string.title_unlocking, R.string.title_mine};
        private int[] mStringId = {R.string.title_home, R.string.title_maintain, R.string.title_unlocking, R.string.title_complain, R.string.title_mine};
    private int[] mDrawableId = {R.drawable.nav_home, R.drawable.nav_maintain, R.drawable.nav_unlocking, R.drawable.nav_complain, R.drawable.nav_mine};
//    private int[] mDrawableId = {R.drawable.nav_home, R.drawable.nav_unlocking, R.drawable.nav_mine};

    //按两次返回退出应用
    private static boolean isExit;
    private int position = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiTitleBarUtil uiTitleBarUtil = new UiTitleBarUtil(this);
        uiTitleBarUtil.setType(UiTitleBarUtil.ONLY_STATUS_BAR);
        uiTitleBarUtil.setTransparentBar(Color.BLACK, 30);
        setContentView(R.layout.activity_home);
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
        mStatusBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mStatausBarHeight));
        mStatusBar.setVisibility(View.GONE);
        selectTab(2);
        setTabTextIcon(2);
    }

    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public void onBackPressed() {
        exit();
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, "连续按两次返回键退出", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            LeApplication.isExit = true;
            finish();
        }
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
        LeApplication.mCurrentTag = position;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(position));
        if (oldFragment != null) {
            ft.hide(oldFragment);
        }
        if (currentFragment == null) {
            switch (position) {
                case 0:
                    currentFragment = new PayFragment();
                    break;
                case 1:
                    currentFragment = new MaintainFragment();
                    break;
                case 2:
                    currentFragment = new HomeFragment();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (oldFragment != null) {
            oldFragment.onActivityResult(requestCode, resultCode, data);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        setTabTextIcon(tab.getPosition());
//        setCurrentTab(position);
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
        if (position == 0) {
            String json = SharePrefrenceUtil.getString("config", "list");
            if (TextUtils.isEmpty(json)) {
                selectTab(this.position);
                getPopupWindow().show();
                return;
            }
            AddressData data = new Gson().fromJson(json, new TypeToken<AddressData>() {}.getType());
            if (!isAlreadyCertification(data != null ? data.list : null)) {
                selectTab(this.position);
                getPopupWindow().show();
                return;
            }
        }
        this.position = position;
        if (position == 2) {
            mStatusBar.setVisibility(View.GONE);
        } else {
            mStatusBar.setVisibility(View.VISIBLE);
        }
        setCurrentTab(position);
//        for (int i = 0; i < 3; i++) {
//            if (i == position) {
//                mImageArray[i].setSelected(true);
//                mTxtArray[i].setSelected(true);
//            } else {
//                mImageArray[i].setSelected(false);
//                mTxtArray[i].setSelected(false);
//            }
//        }
    }

    public Dialog getPopupWindow() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_certi, null);
        TextView txtCer = (TextView) contentView.findViewById(R.id.cer_tv);
        TextView close = (TextView) contentView.findViewById(R.id.close);
        final MyDialog popWnd = new MyDialog(getContext());
//        popWnd.set
        popWnd.setContentView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        popWnd.setCancelable(true);
        popWnd.setCanceledOnTouchOutside(true);
        txtCer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWnd.dismiss();
                AddAddressActivity.newIntent(getContext());
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWnd.dismiss();
            }
        });
        return popWnd;
    }

    public boolean isAlreadyCertification(List<AddressVo> addressData) {
        if (addressData == null || addressData.size() == 0) {
            return false;
        }
        boolean isAlready = false;
        int size = addressData.size();
        for (int i = 0; i < size; i++) {
            AddressVo addressVo = addressData.get(i);
            if ("2".equals(addressVo.estateAuditStatus)) {
                isAlready = true;
                break;
            }
        }
        return isAlready;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    @Override
    protected boolean isCalculateHeight() {
        return true;
    }

    @Override
    protected boolean isTitleBarSetting() {
        return false;
    }

    private void selectTab(int position) {
        try {
            Method selectTab = mBottomView.getClass().getDeclaredMethod("selectTab", TabLayout.Tab.class);
            selectTab.setAccessible(true);
            try {
                selectTab.invoke(mBottomView, mBottomView.getTabAt(position));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}
