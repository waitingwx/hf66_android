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

import com.bumptech.glide.Glide;
import com.cqxy.adapter.tabsViewPagerAdapter;
import com.cqxy.fragment.Individual_Fragment;
import com.cqxy.utils.UserInf;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static com.bumptech.glide.request.RequestOptions.overrideOf;
import static com.bumptech.glide.request.RequestOptions.placeholderOf;


public class individualActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.title_back)
    ImageView title_back;
    @BindView(R.id.individual_headimg)
    ImageView individualHeadimg;
    @BindView(R.id.iv_bg)
    ImageView ivBg;
//    @BindView(R.id.individual_name)
//    TextView individualName;
    @BindView(R.id.individual_tablayout)
    TabLayout individualTablayout;
    @BindView(R.id.individual_viewpager)
    ViewPager individualViewpager;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.tv_signature)
    TextView tvSignature;

    private Individual_Fragment individual_fragment1, individual_fragment2;
    private String TAG = "individualActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual);
        ButterKnife.bind(this);
        initTabs();
        initFragment();
        initDatas();
    }


    @OnClick({R.id.title_back, R.id.individual_headimg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                this.finish();
                break;
            case R.id.individual_headimg:

                break;
        }
    }

    private void initDatas() {
        List<Fragment> datas = new ArrayList<>();
        datas.add(individual_fragment1);
        datas.add(individual_fragment2);
        FragmentManager fm = getSupportFragmentManager();
        tabsViewPagerAdapter adapter = new tabsViewPagerAdapter(this, datas, fm, 1);
        individualViewpager.setAdapter(adapter);
        individualTablayout.setupWithViewPager(individualViewpager);
//        if ("" != UserInf.getUserName(this))
//            individualName.setText(UserInf.getUserName(this));
//        else
//            individualName.setText(UserInf.getUserPhonenum(this));
        if ("" != UserInf.getUserQianming(this))
            tvSignature.setText(UserInf.getUserQianming(this));
        loadImage();
    }

    private void loadImage() {
        String userRemoteAvatar = UserInf.getUserRemoteAvatar(this);
        Log.d(TAG, "loadImage: ------------" + userRemoteAvatar);
        if ("" != userRemoteAvatar) {
            Glide.with(this)
                    .load(userRemoteAvatar)
                    .apply(overrideOf(40, 40))
                    .apply(placeholderOf(R.mipmap.mine_man))
                    .apply(bitmapTransform(new CropCircleTransformation()))
                    .into(individualHeadimg);
            Glide.with(this)
                    .load(userRemoteAvatar)
                    .apply(bitmapTransform(new BlurTransformation(25)))
                    .into(ivBg);
        } else {
            String userLocalAvatar = UserInf.getUserLocalAvatar(this);
            Log.d(TAG, "loadImage: --------------" + userLocalAvatar);
            Glide.with(this)
                    .load(userLocalAvatar)
                    .apply(placeholderOf(R.mipmap.mine_man))
                    .apply(bitmapTransform(new CropCircleTransformation()))
                    .into(individualHeadimg);
            Glide.with(this)
                    .load(userLocalAvatar)
                    .apply(bitmapTransform(new BlurTransformation(25)))
                    .into(ivBg);
        }
    }

    private void initFragment() {
        String extraurl = "house_examples?user_id=" + UserInf.getUserId(this) + "&rentornot=0&states=0";
        individual_fragment1 = new Individual_Fragment();
        Bundle args1 = new Bundle();
        args1.putString("url", extraurl);
        args1.putBoolean("isrent", false);
        individual_fragment1.setArguments(args1);
        String extraurl2 = "house_examples?user_id=" + UserInf.getUserId(this) + "&rentornot=1&states=0";
        individual_fragment2 = new Individual_Fragment();
        Bundle args2 = new Bundle();
        args2.putString("url", extraurl2);
        args2.putBoolean("isrent", true);
        individual_fragment2.setArguments(args2);
    }

    private void initTabs() {
        if ("" != UserInf.getUserName(this))
            titleText.setText(UserInf.getUserName(this) + "的微店铺");
        else
            titleText.setText("微店铺");
        individualTablayout.addOnTabSelectedListener(this);
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
