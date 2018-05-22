package com.cqxy.fyb;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqxy.adapter.tabsViewPagerAdapter;
import com.cqxy.fragment.Tax_HouseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaxFeeActivity extends AppCompatActivity {

    @BindView(R.id.tax_tablayout)
    TabLayout taxTablayout;
    @BindView(R.id.tax_viewpager)
    ViewPager taxViewpager;
    @BindView(R.id.house_enter_back)
    ImageView houseEnterBack;
    @BindView(R.id.title_text)
    TextView titleText;

    private Tax_HouseFragment tax_houseFragment1, tax_houseFragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax_fee);
        ButterKnife.bind(this);
        initFragment();
        initDatas();
    }

    private void initFragment() {
        tax_houseFragment1 = new Tax_HouseFragment(true);
        tax_houseFragment2 = new Tax_HouseFragment(false);
    }

    private void initDatas() {
        titleText.setText("税费计算器");
        FragmentManager fm = getSupportFragmentManager();
        List<Tax_HouseFragment> datas = new ArrayList<>();

        datas.add(tax_houseFragment1);
        datas.add(tax_houseFragment2);
        tabsViewPagerAdapter adapter = new tabsViewPagerAdapter(this, datas, fm, 0);
        taxViewpager.setAdapter(adapter);
        taxTablayout.setupWithViewPager(taxViewpager);
    }


    @OnClick(R.id.house_enter_back)
    public void onViewClicked() {
        this.finish();
    }
}
