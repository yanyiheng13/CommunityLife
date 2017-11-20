package com.iot12369.easylifeandroid.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iot12369.easylifeandroid.LeApplication;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.model.AddressVo;
import com.iot12369.easylifeandroid.model.IsOkData;
import com.iot12369.easylifeandroid.model.LoginData;
import com.iot12369.easylifeandroid.mvp.UpLoadPresenter;
import com.iot12369.easylifeandroid.mvp.contract.UploadContract;
import com.iot12369.easylifeandroid.ui.view.GridSpacingItemDecoration;
import com.iot12369.easylifeandroid.ui.view.LoadingDialog;
import com.iot12369.easylifeandroid.ui.view.UploadPicView;
import com.iot12369.easylifeandroid.ui.view.WithBackTitleView;
import com.iot12369.easylifeandroid.util.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.mr.http.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.luck.picture.lib.config.PictureConfig.LUBAN_COMPRESS_MODE;

/**
 * 功能说明： 报修界面
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-25 下午11:36
 * @Copyright (c) 2017. Inc. All rights reserved.
 */

public class SubmitProblemActivity extends BaseActivity<UpLoadPresenter> implements UploadContract.View {

    @BindView(R.id.title_view)
    WithBackTitleView mTitle;

    @BindView(R.id.maintain_recycler_view)
    RecyclerView mRecyclerView;

    private TextView mTvSubmit;
    private TextView mTvTip;
    private EditText mEtDes;

    private GridSpacingItemDecoration mItemDecoration;

    private List<LocalMedia> mListPic = new ArrayList<>();
    private List<LocalMedia> selectList = new ArrayList<>();
    private BaseQuickAdapter<LocalMedia, BaseViewHolder> mAdapter;

    private int mType = 0;//1 维修进度  2 反馈进度

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mType = getIntent().getIntExtra("type", 0);
        } else {
            mType = savedInstanceState.getInt("type", 0);
        }
        setContentView(R.layout.activity_submit_problem);
        ButterKnife.bind(this);
        mTitle.setText(mType == 1 ? R.string.maintain : R.string.complain_advice);
        init();
    }

    private void init() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        mItemDecoration = new GridSpacingItemDecoration(3, getResources().getDimensionPixelSize(R.dimen.padding_middle), true);
        mRecyclerView.addItemDecoration(mItemDecoration);
//        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new BaseQuickAdapter<LocalMedia, BaseViewHolder>(R.layout.maintain_pic_item) {
            @Override
            protected void convert(BaseViewHolder helper, final LocalMedia item) {
                final UploadPicView images = helper.getView(R.id.maintain_pic_img);
                final ImageView clearImage = helper.getView(R.id.maintain_pic_clear_img);
                //图片显示区域
                images.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.isAddPic) {
                            intoPics();
                        } else {
                            intoPre();
                        }
                    }
                });
                //清除按钮
                clearImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListPic.remove(item);
                        selectList.remove(item);
                        mItemDecoration.setSize(mListPic.size() + 2);
                        mAdapter.setNewData(mListPic);
                    }
                });
                if (item.isAddPic) {
                    clearImage.setVisibility(View.GONE);
                    images.setIsShow(false);
                    if (mListPic.size() == 10) {
                        images.setVisibility(View.GONE);
                    } else {
                        images.setImageResource(R.mipmap.icon_add_pic);
                        images.setVisibility(View.VISIBLE);
                    }
                    return;
                } else {
                    images.setIsShow(true);
                    images.setVisibility(View.VISIBLE);
                    clearImage.setVisibility(View.VISIBLE);
                }
                // 例如 LocalMedia 里面返回三种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
//                int mimeType = media.getMimeType();
                String path = "";
                if (item.isCut() && !item.isCompressed()) {
                    // 裁剪过
                    path = item.getCutPath();
                } else if (item.isCompressed() || (item.isCut() && item.isCompressed())) {
                    // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                    path = item.getCompressPath();
                } else {
                    // 原图
                    path = item.getPath();
                }
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.color.pic_bg)
                        .diskCacheStrategy(DiskCacheStrategy.ALL);
                Glide.with(getContext())
                        .load(path)
                        .apply(options)
                        .into(images.imgView());

//                upLoadData(path, images);

            }
        };
        View headView = LayoutInflater.from(this).inflate(R.layout.view_maintain_head, null);
        mEtDes = (EditText) headView.findViewById(R.id.miantain_et);
        mEtDes.clearFocus();
        mAdapter.addHeaderView(headView);

        View footView = LayoutInflater.from(this).inflate(R.layout.view_maintain_foot, null);
        mTvSubmit = (TextView) footView.findViewById(R.id.maintian_footer_submit_tv);
        mTvTip = (TextView) footView.findViewById(R.id.tv_content);
        AddressVo addressVo = LeApplication.mAddressVo;
        StringBuilder builder = new StringBuilder();
        builder.append(addressVo.communityName);
        builder.append(addressVo.communityRawAddress);
        builder.append("\n");
        builder.append("为保证质量，请在当前物业中选择正确得地址后继续操作!");
        mTvTip.setText(builder.toString());
        mTvSubmit.setOnClickListener(clickListener);
        mAdapter.addFooterView(footView);

        mRecyclerView.setAdapter(mAdapter);
        LocalMedia media = new LocalMedia();
        media.isAddPic = true;
        mListPic.add(media);
        mAdapter.setNewData(mListPic);
        mAdapter.loadMoreEnd(false);
    }

    private void intoPre() {
    }

    private void intoPics() {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(SubmitProblemActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(9)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(3)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .previewVideo(false)// 是否可预览视频
                .enablePreviewAudio(false) // 是否可播放音频
                .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .compressMode(LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                .hideBottomControls(cb_hide.isChecked() ? false : true)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
//                .freeStyleCropEnabled(cb_styleCrop.isChecked())// 裁剪框是否可拖拽
//                .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
//                .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
//                .openClickSound(cb_voice.isChecked())// 是否开启点击声音
                .selectionMedia(null)// 是否传入已选图片
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
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
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }


    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String content = mEtDes.getText().toString();
            if (TextUtils.isEmpty(content) || content.length() < 10) {
                ToastUtil.toast(SubmitProblemActivity.this, "文字描述不得少于十个字");
                return;
            }
            AddressVo addressVo = LeApplication.mAddressVo;
            if (mType == 1) {//提交维修
                LoadingDialog.show(SubmitProblemActivity.this, false);
                LoginData loginData = LeApplication.mUserInfo;
                getPresenter().upMaintainRequireOrder(loginData.phone, addressVo != null ? addressVo.communityName : "",
                        addressVo != null ? addressVo.communityRawAddress : "", content, selectList);
            } else {// 2提交
                LoadingDialog.show(SubmitProblemActivity.this, false);
                LoginData loginData = LeApplication.mUserInfo;
                getPresenter().upComplainRequireOrder(loginData.phone, addressVo != null ? addressVo.communityName : "",
                        addressVo != null ? addressVo.communityRawAddress : "", content, selectList);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    mListPic.clear();
                    mListPic.addAll(selectList);
                    LocalMedia media = new LocalMedia();
                    media.isAddPic = true;
                    mListPic.add(media);
                    mItemDecoration.setSize(mListPic.size() + 2);
                    mAdapter.setNewData(mListPic);
                    break;
            }
        }
    }

    public static void newIntent(Context context, int type) {
        Intent intent = new Intent(context, SubmitProblemActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("type", mType);
    }

    @Override
    public void onUpSuccess(IsOkData isOkData) {
        LoadingDialog.hide();
        ToastUtils.show(this, "提交成功");
        finish();
    }

    @Override
    public void onUpError(String code, String msg) {
        LoadingDialog.hide();
    }
}
