package com.cqxy.fyb;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqxy.adapter.tabsViewPagerAdapter;
import com.cqxy.base.BaseUrl;
import com.cqxy.bean.Users;
import com.cqxy.fragment.Wealth_recommend_Fragment;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.UserInf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyWealthActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.title_back)
    ImageView title_back;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.wealth_money)
    TextView wealthMoney;
    @BindView(R.id.wealth_tablayout)
    TabLayout wealthTablayout;
    @BindView(R.id.wealth_viewpager)
    ViewPager wealthViewpager;
    @BindView(R.id.columna)
    TextView tvColumna;
    @BindView(R.id.columnb)
    TextView tvColumnb;
    @BindView(R.id.columnc)
    TextView tvColumnc;

    private Wealth_recommend_Fragment recommendfragment1, recommendfragment2;
    private String fragRecommendUrl, fragDetailUrl;
    private static String TAG = "MyWealthActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wealth);
        ButterKnife.bind(this);
        initTabs();
        initFragment();
        initDatas();
    }


    private void initDatas() {
        setWealthMoney();
        List<Fragment> datas = new ArrayList<>();
        datas.add(recommendfragment1);
        datas.add(recommendfragment2);
        FragmentManager fm = getSupportFragmentManager();
        tabsViewPagerAdapter adapter = new tabsViewPagerAdapter(this, datas, fm, 2);
        wealthViewpager.setAdapter(adapter);
        wealthTablayout.setupWithViewPager(wealthViewpager);


    }

    //获取个人账户总额
    public void setWealthMoney() {
        Log.d(TAG, "setWealthMoney: ------1");
        OkHttpUtils.okhttpGetNeedHead(
                UserInf.getUserToken(MyWealthActivity.this),
                UserInf.getUserPhonenum(MyWealthActivity.this),
                BaseUrl.URL + "users/" + UserInf.getUserId(this), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String str = response.body().string();
                        final Users user = Users.objectFromData(str,"user");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                wealthMoney.setText(user.getUser_amount() + "");
                            }
                        });
                    }
                });
    }

    private void initFragment() {
        fragRecommendUrl = BaseUrl.URL + "recommends?user_id=" + UserInf.getUserId(this);
        fragDetailUrl = BaseUrl.URL + "detailed_lists?user_id=" + UserInf.getUserId(this);
        recommendfragment1 = new Wealth_recommend_Fragment(fragRecommendUrl);
        recommendfragment2 = new Wealth_recommend_Fragment(fragDetailUrl);


    }

    private void initTabs() {
        titleText.setText("我的财富");
        wealthTablayout.addOnTabSelectedListener(this);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                tvColumna.setText("奖励来源");
                tvColumnb.setText("奖励金额");
                tvColumnc.setText("时间");
                break;
            case 1:
                tvColumna.setText("商品类型");
                tvColumnb.setText("金额");
                tvColumnc.setText("时间");
                break;

        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @OnClick({R.id.title_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                this.finish();
                break;

        }
    }
}
