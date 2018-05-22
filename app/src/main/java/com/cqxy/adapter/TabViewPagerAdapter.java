package com.cqxy.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/18.
 */

public class TabViewPagerAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private List<Fragment> datas;
    private ArrayList<Fragment> innerFragments;
    private int type;
    public TabViewPagerAdapter(Context context, List datas, FragmentManager fm, int type) {
        super(fm);
        this.context=context;
        this.datas=datas;
        this.type=type;
    }



    @Override
    public Fragment getItem(int position) {
        return datas.get(position);
    }

    @Override
    public int getCount() {
        return datas.size();
    }


    /**
     *
     * @param type   0为设置录入房源的tab的title设置  1为成交管理的title设置   2是下载管理的title
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        if(type==0) {
            if (position == 0) {
                return "房源买卖";
            } else {
                return "房源租赁";
            }
        }else if(type==1){
            if (position == 0) {
                return "已买卖的房源";
            } else {
                return "已租赁的房源";
            }
        }else if(type==2)
        {
            if (position == 0) {
                return "查看下载文件";
            } else {
                return "已下载";
            }
        }

        return getPageTitle(position);
    }
}
