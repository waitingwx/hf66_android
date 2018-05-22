package com.cqxy.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cqxy.fyb.R;
import com.youth.banner.loader.ImageLoader;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.bumptech.glide.request.RequestOptions.errorOf;
import static com.bumptech.glide.request.RequestOptions.placeholderOf;

/**
 * Created by wx on 2017/11/9.
 */

public class BlurGlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .apply(bitmapTransform(new BlurTransformation(50)))
                .apply(placeholderOf(R.mipmap.placeholder))
                .apply(errorOf(R.mipmap.placeholder))
                .into(imageView);
    }
}
