package com.cqxy.utils;

import android.support.design.widget.TabLayout;

/**
 * Created by PeLon on 2017/9/19.
 */

public class TabUtils {
    public static void initTabs(TabLayout tabLayout,String []titles)
    {
        for (int i = 0; i < titles.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(titles[i]));
        }
    }
}
