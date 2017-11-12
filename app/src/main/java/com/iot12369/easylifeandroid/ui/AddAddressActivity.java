package com.iot12369.easylifeandroid.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.AddressData;
import com.iot12369.easylifeandroid.model.AddressVo;
import com.iot12369.easylifeandroid.model.LoginData;
import com.iot12369.easylifeandroid.mvp.AddAddressPresenter;
import com.iot12369.easylifeandroid.mvp.contract.AddAddressContract;
import com.iot12369.easylifeandroid.ui.view.LoadingDialog;
import com.iot12369.easylifeandroid.ui.view.WithBackTitleView;
import com.iot12369.easylifeandroid.util.ToastUtil;

import java.lang.reflect.Array;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能说明： 添加地址
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-8-28
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class AddAddressActivity extends BaseActivity<AddAddressPresenter> implements AddAddressContract.View {
    @BindView(R.id.title_view)
    WithBackTitleView mTitleView;

    @BindView(R.id.add_address_name_et)
    EditText mEtName;
    @BindView(R.id.add_address_num_et)
    EditText mEtCertificationNum;
    @BindView(R.id.add_address_tel_tv)
    TextView mTvPhoneNum;
    @BindView(R.id.tv_qu)
    TextView mTvQu;
    @BindView(R.id.add_address_my_et)
    EditText mEtAddress;
    //所在小区
    @BindView(R.id.add_address_location_et)
    TextView mEtLocation;
    private String[] mData;
    private String[] mQuData = {"市区", "和平区", "河西区", "河东区", "河北区",
            "南开区", "红桥区", "东丽区", "西青区", "北辰区", "津南区", "滨海新区", "宝坻区", "宁和区",
            "静海区", "武清区", "蓟县"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        mTitleView.setText(R.string.add_address_no).setImageResource(R.mipmap.icon_account_certification);
        mTvPhoneNum.setText(LeApplication.mUserInfo.phone);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().communityList();
    }

    @OnClick({R.id.add_address_tv, R.id.add_address_location_et, R.id.tv_qu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_address_location_et:
                Dialog popupWindow = getPopupWindow(mData);
                if (popupWindow == null) {
                    break;
                }
                popupWindow.show();
                break;
            case R.id.add_address_tv:
                if (TextUtils.isEmpty(mEtName.getText().toString())
                        || TextUtils.isEmpty(mEtAddress.getText().toString())
                        || TextUtils.isEmpty(mTvPhoneNum.getText().toString())
                        || TextUtils.isEmpty(mEtLocation.getText().toString())) {
                    return;
                }
                LoadingDialog.show(this, false);
                LoginData data = LeApplication.mUserInfo;
                getPresenter().addAddress(data.opopenId, data.memberId, data.phone, mEtName.getText().toString(),
                        mEtCertificationNum.getText().toString(), mEtLocation.getText().toString(), mEtAddress.getText().toString(), mTvQu.getText().toString());
                break;
            case R.id.tv_qu:
                Dialog qu = getQu(mQuData);
                if (qu != null) {
                    qu.show();
                }
                break;
            default:
                break;
        }

    }

    public static void newIntent(Context context) {
        Intent intent = new Intent(context, AddAddressActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onSuccessAddress(AddressVo addressData) {
        LoadingDialog.hide();
        if (!TextUtils.isEmpty(addressData.memberId)) {
            ToastUtil.toast(this, "认证成功");
            finish();
        }
    }

    public Dialog getPopupWindow(final String[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        final View contentView = LayoutInflater.from(AddAddressActivity.this).inflate(R.layout.popup_window, null);
        ListView listView = (ListView) contentView.findViewById(R.id.listView);
        final Dialog  popWnd = new Dialog(this);
        popWnd.setContentView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        popWnd.setCancelable(true);
        popWnd.setCanceledOnTouchOutside(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popWnd.dismiss();
                mEtLocation.setText(mData[position]);
            }
        });

        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return data == null ? 0 : data.length;
            }

            @Override
            public Object getItem(int position) {
                return data[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder vh;
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_item, null);
                    vh = new ViewHolder();
                    vh.tv = (TextView) convertView.findViewById(R.id.text1);
                    convertView.setTag(vh);
                } else {
                    vh = (ViewHolder) convertView.getTag();

                }
                vh.tv.setText(data[position]);
                return convertView;
            }

            class ViewHolder {
                TextView tv;
            }
        };
        listView.setAdapter(adapter);
        return popWnd;
    }

    public Dialog getQu(final String[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        final View contentView = LayoutInflater.from(AddAddressActivity.this).inflate(R.layout.popup_window, null);
        ListView listView = (ListView) contentView.findViewById(R.id.listView);
        final Dialog  popWnd = new Dialog(this);
        popWnd.setContentView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        popWnd.setCancelable(true);
        popWnd.setCanceledOnTouchOutside(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popWnd.dismiss();
                mTvQu.setText(mQuData[position]);
            }
        });

        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return data == null ? 0 : data.length;
            }

            @Override
            public Object getItem(int position) {
                return data[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder vh;
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_item, null);
                    vh = new ViewHolder();
                    vh.tv = (TextView) convertView.findViewById(R.id.text1);
                    convertView.setTag(vh);
                } else {
                    vh = (ViewHolder) convertView.getTag();

                }
                vh.tv.setText(data[position]);
                return convertView;
            }

            class ViewHolder {
                TextView tv;
            }
        };
        listView.setAdapter(adapter);
        return popWnd;
    }


    @Override
    public void onFailureAddress(String code, String msg) {
        LoadingDialog.hide();
    }

    @Override
    public void onSuccessAddressList(AddressData addressData) {
        if (addressData != null && addressData.list != null && addressData.list.size() > 0) {
            int size = addressData.list.size();
            mData = new String[size];
            for (int i = 0; i < size; i++) {
                mData[i] = addressData.list.get(i).name;
            }
        }
    }

    @Override
    public void onFailureAddressList(String code, String msg) {

    }
}
