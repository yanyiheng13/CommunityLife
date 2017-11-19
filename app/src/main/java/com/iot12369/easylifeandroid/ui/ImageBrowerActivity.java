package com.iot12369.easylifeandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.iot12369.easylifeandroid.R;
import com.iot12369.easylifeandroid.ui.view.WithBackTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by ubuntu on 2017/11/19.
 */

public class ImageBrowerActivity extends BaseActivity {

    @BindView(R.id.preview_image)
    public PhotoView mPhotoView;
    @BindView(R.id.title_view)
    public WithBackTitleView mTitleView;
    private String mImageUrl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mImageUrl = getIntent().getStringExtra("img");
        } else {
            mImageUrl = savedInstanceState.getString("img");
        }
        setContentView(R.layout.activity_pic_brower);
        ButterKnife.bind(this);
        mTitleView.setText(R.string.photo_look);
        Glide.with(ImageBrowerActivity.this)
                .load(mImageUrl)
                .into(mPhotoView);
    }

    public static void newIntent(Context context, String imgUrl) {
        Intent intent = new Intent(context, ImageBrowerActivity.class);
        intent.putExtra("img", imgUrl);
        context.startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("img", mImageUrl);
    }
}
