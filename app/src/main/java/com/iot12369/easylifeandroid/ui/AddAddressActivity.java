package com.iot12369.easylifeandroid.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.XmlParserHandler;
import com.iot12369.easylifeandroid.model.AddressData;
import com.iot12369.easylifeandroid.model.AddressVo;
import com.iot12369.easylifeandroid.model.CityModel;
import com.iot12369.easylifeandroid.model.DistrictModel;
import com.iot12369.easylifeandroid.model.LoginData;
import com.iot12369.easylifeandroid.model.ProvinceModel;
import com.iot12369.easylifeandroid.mvp.AddAddressPresenter;
import com.iot12369.easylifeandroid.mvp.contract.AddAddressContract;
import com.iot12369.easylifeandroid.ui.view.LoadingDialog;
import com.iot12369.easylifeandroid.ui.view.MyDialog;
import com.iot12369.easylifeandroid.ui.view.WithBackTitleView;
import com.iot12369.easylifeandroid.util.ToastUtil;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

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
    @BindView(R.id.tv_province)
    TextView mTvProvince;
    @BindView(R.id.tv_city)
    TextView mTvCity;
    @BindView(R.id.add_address_men_tv)
    TextView mTvLouNum;
    @BindView(R.id.add_address_my_et)
    EditText mEtAddress;
    //所在小区
    @BindView(R.id.add_address_location_et)
    TextView mEtLocation;
    private String[] mData;

    public String mBudingDoorId;
    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName = "";

    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode = "";
    protected String communityId = "";

    private PopupWindow mPopCity;
    private AddressData addressData;
    private AddressData addressNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        mTitleView.setText(R.string.add_address_no).setImageResource(R.mipmap.icon_account_certification);
        mTvPhoneNum.setText(LeApplication.mUserInfo.phone);
        initProvinceDatas();
    }

    @OnClick({R.id.add_address_tv, R.id.add_address_location_et, R.id.ssss, R.id.add_address_men_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_address_location_et:
                if (mData == null || mData.length <= 0) {
                    LoadingDialog.show(this, false);
                    String province = mTvProvince.getText().toString();
                    String city = mTvCity.getText().toString();
                    String area = mTvQu.getText().toString();
                    if ("其他".equals(area)) {
                        area = "";
                    }
                    getPresenter().communityList(province, city, area);
                    break;
                }
                Dialog popupWindow = getPopupWindow(mData);
                if (popupWindow == null) {
                    break;
                }
                popupWindow.show();
                break;
            case R.id.add_address_tv:
                if (TextUtils.isEmpty(mEtName.getText().toString().trim())
                        || TextUtils.isEmpty(mEtAddress.getText().toString().trim())
                        || TextUtils.isEmpty(mTvPhoneNum.getText().toString().trim())
                        || TextUtils.isEmpty(mEtLocation.getText().toString().trim())
                        || TextUtils.isEmpty(mTvLouNum.getText().toString().trim())) {
                    return;
                }
                LoadingDialog.show(this, false);
                LoginData data = LeApplication.mUserInfo;
                getPresenter().addAddress(data.opopenId, data.memberId, data.phone, mEtName.getText().toString(),
                        mEtCertificationNum.getText().toString(), mEtLocation.getText().toString(),
                        mEtAddress.getText().toString(), mTvProvince.getText().toString(), mTvCity.getText().toString(), mTvQu.getText().toString(), communityId, mBudingDoorId);
                break;
            case R.id.ssss:
                getPopCity().showAtLocation(mTitleView, Gravity.BOTTOM,0, 0);
                break;
            case R.id.add_address_men_tv:
                if (TextUtils.isEmpty(communityId)) {
                    ToastUtil.toast(this, "请先选择小区");
                    break;
                }
                LoadingDialog.show(this, false);
                getPresenter().communityNum(communityId);
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
        if (addressData == null) {
            ToastUtil.toast(this, "提交失败");
        } else {
            if (!TextUtils.isEmpty(addressData.memberId)) {
                ToastUtil.toast(this, "提交成功,等待物业审核");
                finish();
            } else {
                getPopupWindow().show();
            }
        }

    }

    public Dialog getPopupWindow() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_certi, null);
        TextView txtCer = (TextView) contentView.findViewById(R.id.cer_tv);
        TextView txt = (TextView) contentView.findViewById(R.id.txt);
        txtCer.setText("知道了");
        txt.setText("已通知业主进行审核，请耐心等待!");
        TextView close = (TextView) contentView.findViewById(R.id.close);
        final MyDialog popWnd = new MyDialog(getContext());
        popWnd.setContentView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        popWnd.setCancelable(true);
        popWnd.setCanceledOnTouchOutside(true);
        txtCer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWnd.dismiss();
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

    public Dialog getPopupWindow(final String[] data) {
        if (data == null || data.length == 0) {
            ToastUtil.toast(this, "该地区暂未小区");
            return null;
        }
        final View contentView = LayoutInflater.from(AddAddressActivity.this).inflate(R.layout.popup_window, null);
        ListView listView = (ListView) contentView.findViewById(R.id.listView);
        final Dialog popWnd = new Dialog(this);
        popWnd.setContentView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        popWnd.setCancelable(true);
        popWnd.setCanceledOnTouchOutside(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popWnd.dismiss();
                mEtLocation.setText(mData[position]);
                communityId = addressData.list.get(position).communityId;
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

    public PopupWindow getPopCity() {
//        if (data == null || data.length == 0) {
//            return null;
//        }
        final View contentView = LayoutInflater.from(AddAddressActivity.this).inflate(R.layout.test, null);
        final WheelView viewProvince = (WheelView) contentView.findViewById(R.id.id_province);
        final WheelView viewCity = (WheelView) contentView.findViewById(R.id.id_city);
        final WheelView viewDistrict = (WheelView) contentView.findViewById(R.id.id_district);
        TextView tvCancel = (TextView) contentView.findViewById(R.id.tv_cancel);
        TextView tvSure = (TextView) contentView.findViewById(R.id.tv_sure);

        if (mPopCity == null) {
            // 设置可见条目数量
            mPopCity = new PopupWindow(this);
            mPopCity.setContentView(contentView);
            mPopCity.setOutsideTouchable(true);
           //设置PopupWindow弹出窗体的宽    
            mPopCity.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
              //设置PopupWindow弹出窗体的高    
            mPopCity.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
           //设置PopupWindow弹出窗体可点击    
            mPopCity.setFocusable(true);
           //设置SelectPicPopupWindow弹出窗体动画效果    
            mPopCity.setAnimationStyle(R.style.Animation);
           //实例化一个ColorDrawable颜色为半透明    
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            //设置SelectPicPopupWindow弹出窗体的背景    
            mPopCity.setBackgroundDrawable(dw);
            viewProvince.setVisibleItems(7);
            viewCity.setVisibleItems(7);
            viewDistrict.setVisibleItems(7);
            viewProvince.setViewAdapter(new ArrayWheelAdapter<String>(AddAddressActivity.this, mProvinceDatas));
            updateCities(viewProvince, viewCity, viewDistrict);
            updateAreas(viewCity, viewDistrict);

        }
        // 添加change事件
        viewProvince.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateCities(viewProvince, viewCity, viewDistrict);
            }
        });
        // 添加change事件
        viewCity.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateAreas(viewCity, viewDistrict);
            }
        });
        // 添加change事件
        viewDistrict.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
                mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopCity.dismiss();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopCity.dismiss();
                mTvProvince.setText(mCurrentProviceName);
                mTvCity.setText(mCurrentCityName);
                mTvQu.setText(mCurrentDistrictName);
                mData = null;
                communityId = "";
                mBudingDoorId = "";
                mTvLouNum.setText("");
                mEtLocation.setText("");
                mTvLouNum.setText("");
            }
        });

        return mPopCity;
    }

    protected void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            //*/ 初始化默认选中的省、市、区
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas(WheelView wheelViewCity, WheelView wheelViewDistrict) {
        int pCurrent = wheelViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        wheelViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        wheelViewDistrict.setCurrentItem(0);
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities(WheelView wheelViewProvince, WheelView wheelViewCity, WheelView wheelViewDistrict) {
        int pCurrent = wheelViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        wheelViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        wheelViewCity.setCurrentItem(0);
        updateAreas(wheelViewCity, wheelViewDistrict);
    }


    @Override
    public void onFailureAddress(String code, String msg) {
        LoadingDialog.hide();
    }

    @Override
    public void onSuccessAddressList(AddressData addressData) {
        LoadingDialog.hide();
        this.addressData = addressData;
        if (addressData != null && addressData.list != null && addressData.list.size() > 0) {
            int size = addressData.list.size();
            mData = new String[size];
            for (int i = 0; i < size; i++) {
                mData[i] = addressData.list.get(i).name;
            }
            Dialog popupWindow = getPopupWindow(mData);
            if (popupWindow == null) {
               return;
            }
            popupWindow.show();
        } else {
            ToastUtil.toast(this, "该地区暂未小区");
        }
    }

    @Override
    public void onFailureAddressList(String code, String msg) {
        LoadingDialog.hide();
    }

    @Override
    public void onSuccessNum(AddressData addressData) {
//        mBudingDoorId = addressVo.budingDoorId;
//        mTvLouNum.setText(addressVo.buildingDoor);
        this.addressNum = addressData;
        LoadingDialog.hide();
        if (addressData != null && addressData.list != null && addressData.list.size() !=0 ) {
            List<AddressVo> list = addressData.list;
            int size = list.size();
            String[] datas = new String[size];
            for (int i = 0; i < size; i++) {
                datas[i] = list.get(i).buildingDoor;
            }
            getNumDialog(datas).show();
        } else {
            ToastUtil.toast(this, "数据返回为空");
        }

    }

    @Override
    public void onFailureNum(String code, String msg) {
        LoadingDialog.hide();
    }

    public Dialog getNumDialog(final String[] data) {
        if (data == null || data.length == 0) {
            ToastUtil.toast(this, "该地区暂无楼号");
            return null;
        }
        final View contentView = LayoutInflater.from(AddAddressActivity.this).inflate(R.layout.popup_window, null);
        ListView listView = (ListView) contentView.findViewById(R.id.listView);
        final Dialog popWnd = new Dialog(this);
        popWnd.setContentView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        popWnd.setCancelable(true);
        popWnd.setCanceledOnTouchOutside(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popWnd.dismiss();
                mTvLouNum.setText(data[position]);
                mBudingDoorId = addressNum.list.get(position).budingDoorId;
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
}
