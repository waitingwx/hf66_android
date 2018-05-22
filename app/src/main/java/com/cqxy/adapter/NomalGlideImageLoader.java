package com.cqxy.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cqxy.fyb.R;
import com.youth.banner.loader.ImageLoader;

import static com.bumptech.glide.request.RequestOptions.errorOf;
import static com.bumptech.glide.request.RequestOptions.placeholderOf;

/**
 * Created by wx on 2017/11/9.
 */

public class NomalGlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .apply(placeholderOf(R.mipmap.placeholder))
                .apply(errorOf(R.mipmap.placeholder))
                .into(imageView);
    }
}
