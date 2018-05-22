package com.cqxy.fyb;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqxy.adapter.tabsViewPagerAdapter;
import com.cqxy.fragment.CommercialLoanFragment;
import com.cqxy.fragment.FundslLoanFragment;
import com.cqxy.fragment.GroupLoanFragment;
import com.cqxy.fragment.Tax_HouseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HouseLoanActivity extends AppCompatActivity {

    @BindView(R.id.house_enter_back)
    ImageView houseEnterBack;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.Houseloan_tablayout)
    TabLayout HouseloanTablayout;
    @BindView(R.id.Houseloan_viewpager)
    ViewPager HouseloanViewpager;

    private CommercialLoanFragment commercialLoanFragment;
    private FundslLoanFragment fundslLoanFragment;
    private GroupLoanFragment groupLoanFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_loan);
        ButterKnife.bind(this);
        titleText.setText("房贷计算器");
        initFragment();
        initDatas();
    }
    private void initFragment() {
        commercialLoanFragment = new CommercialLoanFragment();
        fundslLoanFragment = new FundslLoanFragment();
        groupLoanFragment = new GroupLoanFragment();
    }

    private void initDatas() {
        titleText.setText("房贷计算器");
        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> datas = new ArrayList<>();
        datas.add(commercialLoanFragment);
        datas.add(fundslLoanFragment);
        datas.add(groupLoanFragment);
        tabsViewPagerAdapter adapter = new tabsViewPagerAdapter(this, datas, fm, 3);
        HouseloanViewpager.setAdapter(adapter);
        HouseloanTablayout.setupWithViewPager(HouseloanViewpager);
    }

    @OnClick(R.id.house_enter_back)
    public void onViewClicked() {
        this.finish();
    }
}
