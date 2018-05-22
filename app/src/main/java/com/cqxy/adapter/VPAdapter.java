package com.cqxy.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cqxy.bean.ViewPagerItem;
import com.cqxy.fyb.R;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.bumptech.glide.request.RequestOptions.errorOf;
import static com.bumptech.glide.request.RequestOptions.placeholderOf;

/**
 * Created by Administrator on 2017/8/29.
 */

public class VPAdapter extends PagerAdapter {
    private List<ViewPagerItem> datas;
    private boolean isNeedMaoBoli=false;
    private Context context;
    public VPAdapter(List<ViewPagerItem> datas,Context context) {
        this.datas = datas;
        this.isNeedMaoBoli=false;
        this.context=context;
    }

    public VPAdapter(List<ViewPagerItem> datas, boolean isNeedMaoBoLi,Context context) {
        this.datas = datas;
        this.isNeedMaoBoli=isNeedMaoBoLi;
        this.context=context;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d("VPAdapter", "instantiateItem: "+Thread.currentThread().getName());
        long start = System.currentTimeMillis();
        position = position % datas.size();
        ImageView child = new ImageView(container.getContext());
        child.setScaleType(ImageView.ScaleType.FIT_XY);
        if (isNeedMaoBoli)
        {
//            Glide.with(context).load().bitmapTransform(new BlurTransformation(context, 25), new CenterCrop(context))
//                    .into(child);
            Glide.with(context)
                    .load(datas.get(position).getImgurl())
                    .apply(bitmapTransform(new BlurTransformation(25)))
                    .apply(placeholderOf(R.mipmap.placeholder))
                    .apply(errorOf(R.mipmap.placeholder))
                    .into(child);
        }else {
            Glide.with(context).load(datas.get(position).getImgurl()).into(child);
        }
        container.addView(child);
        long end = System.currentTimeMillis();
        Log.d("VPAdapter", "instantiateItem: time = "+(end - start));
        return child;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    

}
