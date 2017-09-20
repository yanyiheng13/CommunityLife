package com.iot12369.easylifeandroid.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.gson.Gson;
import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.PersonData;
import com.iot12369.easylifeandroid.mvp.PersonInfoPresenter;
import com.iot12369.easylifeandroid.mvp.contract.PersonInfoContract;
import com.iot12369.easylifeandroid.ui.AboutUsActivity;
import com.iot12369.easylifeandroid.ui.AuthorizationActivity;
import com.iot12369.easylifeandroid.ui.BaseFragment;
import com.iot12369.easylifeandroid.ui.CertificationActivity;
import com.iot12369.easylifeandroid.ui.LoginSelectActivity;
import com.iot12369.easylifeandroid.ui.TransactionRecordsActivity;
import com.iot12369.easylifeandroid.ui.view.IconTitleView;
import com.iot12369.easylifeandroid.ui.view.PropertyAddressView;
import com.iot12369.easylifeandroid.util.SharePrefrenceUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 功能说明： 我的页面
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-8-24
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class MineFragment extends BaseFragment<PersonInfoPresenter> implements PersonInfoContract.View {
    @BindView(R.id.title_view)
    IconTitleView mTitleView;
    //用户头像
    @BindView(R.id.mine_head_img)
    ImageView mImageHead;
    //用户等级
    @BindView(R.id.mine_level_img)
    ImageView mImageLevel;
    //名字
    @BindView(R.id.mine_name_tv)
    TextView mTvName;
    //微信号
    @BindView(R.id.mine_wechat_num_tv)
    TextView mTvWeChatNum;

    @BindView(R.id.mine_property_view)
    PropertyAddressView mAddressView;
    //退出对话框
    private NiftyDialogBuilder mDialogBuilder;

    //已选中的图片
    private List<LocalMedia> selectList = new ArrayList<>();
    @Override
    public int inflateId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置标题和标题icon
        mTitleView.setImageResource(R.mipmap.title_mine).setText(R.string.title_mine);
        mAddressView.updateData();
    }

    @OnClick(R.id.mine_head_img)
    public void onHeadClick() {
        PictureSelector.create(getActivity())
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(3)// 每行显示个数
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .previewVideo(false)// 是否可预览视频
                .enablePreviewAudio(false) // 是否可播放音频
                .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(true)// 是否裁剪
                .compress(true)// 是否压缩
                .compressMode(PictureConfig.SYSTEM_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(true)// 是否开启点击声音
                .selectionMedia(selectList)// 是否传入已选图片
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                //.compressMaxKB()//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效
                //.compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled() // 裁剪是否可旋转图片
                //.scaleEnabled()// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult result
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    LocalMedia media = selectList.get(0);
                    int mimeType = media.getMimeType();
                    String path = "";
                    if (media.isCut() && !media.isCompressed()) {
                        // 裁剪过
                        path = media.getCutPath();
                    } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                        // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                        path = media.getCompressPath();
                    } else {
                        // 原图
                        path = media.getPath();
                    }
                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.color.pic_bg)
                            .diskCacheStrategy(DiskCacheStrategy.ALL);
                    Glide.with(getContext())
                            .load(path)
                            .apply(options)
                            .into(mImageHead);
                    break;
            }
        }
    }

    @OnClick({R.id.mine_about_us_ll, R.id.mine_pay_record_ll, R.id.mine_account_authorise_ll, R.id.mine_account_certification_ll, R.id.exit_tv})
    public void onMineItemClick(View view) {
        switch (view.getId()) {
            //缴费记录
            case R.id.mine_pay_record_ll:
                TransactionRecordsActivity.newIntent(getContext());
                break;
            case R.id.mine_account_certification_ll:
                CertificationActivity.newIntent(getContext());
                break;
            //账号授权
            case R.id.mine_account_authorise_ll:
                AuthorizationActivity.newIntent(getContext());
                break;
            //关于我们
            case R.id.mine_about_us_ll:
                AboutUsActivity.newIntent(getContext());
                break;
            case R.id.exit_tv:
                getDialog().show();
                break;
            default:
                break;
        }
    }


    @Override
    public void onSuccessPerson(PersonData data) {

    }

    @Override
    public void onFailurePerson(String code, String msg) {

    }

    /**
     * 获取退出提示dialog
     */
    public NiftyDialogBuilder getDialog() {
        if (mDialogBuilder == null) {
            mDialogBuilder = new NiftyDialogBuilder(getContext());
            mDialogBuilder
                    .withTitle(null)                                  //.withTitle(null)  no title
                    .withMessage(R.string.sure_to_logout)
                    .withMessageColor(0xFF333333)
                    .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                    .withDuration(400)                                          //def
                    .withEffect(Effectstype.Fall)
                    .withButtonText(R.string.app_cancel, R.string.app_logout)
                    .isCorner(true)
                    .withButton1Color(getResources().getColor(R.color.colorLoginTxt))
                    .withButton2Color(getResources().getColor(R.color.colorAccent))
                    .setButton2Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialogBuilder.dismiss();
                            LeApplication.mUserInfo = null;
                            SharePrefrenceUtil.setString("config", "user", "");
                            LoginSelectActivity.newIntent(getContext());
                            getActivity().finish();
                        }
                    });
        }
        return mDialogBuilder;
    }
}
