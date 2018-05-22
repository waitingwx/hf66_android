package com.cqxy.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by WX on 2016/10/13 0013.
 */

public abstract class CommonImagePagerAdapter extends PagerAdapter {

    private List<ImageView> viewList;
    List<?> innerDatas;

    private Context context;

    public CommonImagePagerAdapter(Context context, List<?> innerDatas, List<ImageView> viewList) {
        this.viewList = viewList;
        this.innerDatas = innerDatas;
        this.context = context;

    }


    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int index = position % viewList.size();
        ImageView imageView = viewList.get(index);
        ViewGroup parent = (ViewGroup) imageView.getParent();
        if(parent != null) {
            parent.removeAllViews();
        }
        container.addView(imageView);

        showImage(index,imageView);
        return imageView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int index = position % viewList.size();
        ImageView imageView = viewList.get(index);
        container.removeView(imageView);
    }

    public abstract void showImage(int position,ImageView imageView);
}
