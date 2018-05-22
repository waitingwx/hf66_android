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

public class tabsViewPagerAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private List<Fragment> datas;
    private ArrayList<Fragment> innerFragments;
    private int type;
    public tabsViewPagerAdapter(Context context, List datas, FragmentManager fm, int type) {
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

    @Override
    public CharSequence getPageTitle(int position) {
        if(type==0) {
            if (position == 0) {
                return "新房";
            } else {
                return "二手房";
            }
        }else if(type==1) {
            if (position == 0) {
                return "发布买卖房源";
            } else {
                return "发布租赁房源";
            }
        }else if(type==2)
        {
            if (position == 0) {
                return "推荐奖励";
            } else {
                return "我的支出";
            }
        }
        else if(type==3)
        {
            if (position == 0) {
                return "商业贷款";
            } else if (position == 1){
                return "公积金贷款";
            }else {
                return "组合贷款";
            }
        }

        return getPageTitle(position);
    }
}
