package com.cqxy.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cqxy.fyb.R;

/**
 *
 * 自动滚动的ViewPager
 */
public class AutoScrollViewPager extends LinearLayout {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private int currItem;
    private int pagerCount;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            viewPager.setCurrentItem(++currItem);
            autoScroll();
        }
    };


    private void autoScroll() {
        if(null != handler) {
            handler.sendEmptyMessageDelayed(10001,2000);
        }
    }

    public AutoScrollViewPager(Context context) {
        this(context,null);
    }

    public AutoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        //加载指定的布局到当前的容器中
        inflate(context, R.layout.widget_auto_scroll_viewpager, this);

        viewPager = (ViewPager) findViewById(R.id.widget_atsv_vp);
        int width, height;
        width = context.getResources().getDisplayMetrics().widthPixels;
        height = width * 1 / 2;
        ViewGroup.LayoutParams layoutParams = viewPager.getLayoutParams();
        layoutParams.height = height;
        layoutParams.width = width;
        viewPager.setLayoutParams(layoutParams);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int index = position % pagerCount;
                tabLayout.getTabAt(index).select();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout = (TabLayout) findViewById(R.id.widget_atsv_tl);
    }

    /**
     * 显示数据
     *
     * @param adapter
     */
    public void setPagerAdapter(PagerAdapter adapter, int pagerCount)
    {
        this.pagerCount = pagerCount;
        viewPager.setAdapter(adapter);
        for (int i = 0; i < pagerCount; i++) {
            tabLayout.addTab(tabLayout.newTab());
        }
    autoScroll();
    }



    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler = null;
    }
}
