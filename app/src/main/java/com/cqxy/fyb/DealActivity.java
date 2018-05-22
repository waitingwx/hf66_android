package com.cqxy.fyb;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqxy.adapter.TabViewPagerAdapter;
import com.cqxy.base.BaseUrl;
import com.cqxy.fragment.DealFragmentRent;
import com.cqxy.utils.UserInf;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DealActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.house_deal_tab)
    TabLayout houseDealTab;
    @BindView(R.id.house_deal_viewpager)
    ViewPager houseDealViewpager;
    @BindView(R.id.house_enter_back)
    ImageView houseEnterBack;
    @BindView(R.id.title_text)
    TextView titleText;
    private DealFragmentRent rentfrement, rentfrement2;
    private static String TAG ="DealActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal);
        ButterKnife.bind(this);
        initTabs();
        initFragment();
        initDatas();

    }

    private void initDatas() {
        List<Fragment> datas = new ArrayList<>();
        datas.add(rentfrement);
        datas.add(rentfrement2);
        FragmentManager fm = getSupportFragmentManager();
        TabViewPagerAdapter adapter = new TabViewPagerAdapter(this, datas, fm, 1);
        houseDealViewpager.setAdapter(adapter);
        houseDealTab.setupWithViewPager(houseDealViewpager);

    }

    private void initFragment() {
        String fragUrla = BaseUrl.URL + "cooperation_sellhouses?user_id=" + UserInf.getUserId(this);
        String fragUrlb = BaseUrl.URL + "cooperation_houses?user_id=" + UserInf.getUserId(this);
        Log.d(TAG, "initFragment: fraga.url = " +fragUrla +"\nfragb.url = " +fragUrlb);
        rentfrement = new DealFragmentRent(fragUrla);
        rentfrement2 = new DealFragmentRent(fragUrlb);
    }

    private void initTabs() {
        titleText.setText("成交管理");
        houseDealTab.addOnTabSelectedListener(this);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @OnClick(R.id.house_enter_back)
    public void onViewClicked() {
        this.finish();
    }
}
