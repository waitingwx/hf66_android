package com.cqxy.fyb;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cqxy.base.BaseActivty;
import com.cqxy.bean.HideLodingEvent;
import com.cqxy.bean.ShowLoadingEvent;
import com.cqxy.constants.ModeConstants;
import com.cqxy.fragment.House_enter_office_rent_Fragment;
import com.cqxy.fragment.House_enter_office_sell_Fragment;
import com.cqxy.fragment.House_enter_residence_rent_Fragment;
import com.cqxy.fragment.House_enter_residence_sell_Fragment;
import com.cqxy.fragment.House_enter_shop_rent_Fragment;
import com.cqxy.fragment.House_enter_shop_sell_Fragment;
import com.cqxy.fragment.House_enter_villa_rent_Fragment;
import com.cqxy.fragment.House_enter_villa_sell_Fragment;
import com.cqxy.utils.SelectHignImg;
import com.cqxy.view.LoadingView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.cqxy.fyb.R.id.house_enter_residence;

public class House_enterActivity extends BaseActivty implements TabLayout.OnTabSelectedListener {
    @BindView(R.id.house_enter_back)
    ImageView house_enter_back;
    @BindView(R.id.house_enter_tablayout)
    TabLayout house_enter_tablayout;
    @BindView(house_enter_residence)
    LinearLayout houseEnterResidence;
    @BindView(R.id.house_enter_villa)
    LinearLayout houseEnterVilla;
    @BindView(R.id.house_enter_office)
    LinearLayout houseEnterOffice;
    @BindView(R.id.house_enter_shop)
    LinearLayout houseEnterShop;
    @BindView(R.id.house_enter_fragment)
    FrameLayout houseEnterFragment;

    private FragmentManager fm;
    private boolean isRent = false;
    private int rentornot = 0;
    private int imgsh[] = {R.mipmap.inputing_residence_h, R.mipmap.inputing_villa_h, R.mipmap.inputing_building_h, R.mipmap.inputing_shops_h};
    private int imgsn[] = {R.mipmap.inputing_residence_n, R.mipmap.inputing_villa_n, R.mipmap.inputing_building_n, R.mipmap.inputing_shops_n};
    private LinearLayout layouts[] = new LinearLayout[4];
    private String type = ModeConstants.RESIDENCE_SELL;
    private String TAG = "House_enterActivity";
    private int houseid;
    private boolean isedit = false;
    private LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_enter);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        getWindows();
        loadIntent();
        initTabs();
        initFragment();
        initlisteners();

    }

    private void loadIntent() {
        Bundle extras = getIntent().getBundleExtra("bundle");
        if (null == extras) {
            isedit = false;
            return;
        } else {
            isedit = true;
            String mode = extras.getString("mode");
            boolean intentIsRent = extras.getBoolean("isrent");
            houseid = extras.getInt("houseid");
            Log.d(TAG, "loadIntent: ------------------houseid = " + houseid);
            isRent = intentIsRent;
            type = mode;
            Log.d(TAG, "jumpToEdit: ---------Parcelable----------mode = " + mode + "\nintentIsrent = " + intentIsRent + ",isRent = " + isRent);
        }
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

    private void switchMode(String mode) {
        Log.d(TAG, "switchMode: -----Parcelable----------");
        switch (mode) {
            case ModeConstants.OFFICEBUILDING_RENT:
                houseEnterOffice.performClick();

                break;
            case ModeConstants.OFFICEBUILDING_SELL:
                houseEnterOffice.performClick();

                break;
            case ModeConstants.RESIDENCE_SELL:
                houseEnterResidence.performClick();

                break;
            case ModeConstants.RESIDENCE_RENT:
                houseEnterResidence.performClick();
                break;
            case ModeConstants.SHOP_RENT:
                houseEnterShop.performClick();
                break;
            case ModeConstants.SHOP_SELL:
                houseEnterShop.performClick();
                break;
            case ModeConstants.VILLA_RENT:
                houseEnterVilla.performClick();
                break;
            case ModeConstants.VILLA_SELL:
                houseEnterVilla.performClick();
                break;
        }
    }

    public void getWindows() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;


        layouts[0] = houseEnterResidence;
        layouts[1] = houseEnterVilla;
        layouts[2] = houseEnterOffice;
        layouts[3] = houseEnterShop;
        loadingView = ((LoadingView) findViewById(R.id.loading_enter));
    }

    private void initFragment() {
        //TODO:isedit--->根据type初始化fragment
        fm = getSupportFragmentManager();
        if (!isedit) {
            House_enter_residence_sell_Fragment residence_sell_fragment = new House_enter_residence_sell_Fragment(type, rentornot, houseid, isedit);
            Bundle args = new Bundle();
            args.putInt("houseid", houseid);
            args.putString("type", type);
            args.putBoolean("isedit", isedit);
            residence_sell_fragment.setArguments(args);
            fm.beginTransaction().add(R.id.house_enter_fragment, residence_sell_fragment).commit();
            if (!isRent)
                house_enter_tablayout.getTabAt(0).select();
            else
                house_enter_tablayout.getTabAt(1).select();
        } else
            switchMode(type);
    }


    private void initTabs() {
        house_enter_tablayout.addTab(house_enter_tablayout.newTab().setText("房源买卖"));
        house_enter_tablayout.addTab(house_enter_tablayout.newTab().setText("房源租赁"));
        house_enter_tablayout.getTabAt(0).select();
    }

    private void initlisteners() {
        house_enter_tablayout.addOnTabSelectedListener(this);
    }


    //--------------------------------------------------------------------------------------------------
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                isRent = false;
                rentornot = 0;
                type = ModeConstants.RESIDENCE_SELL;
                House_enter_residence_sell_Fragment fragment = new House_enter_residence_sell_Fragment(type, rentornot, houseid, isedit);
                Bundle args = new Bundle();
                args.putInt("houseid", houseid);
                args.putString("type", type);
                args.putBoolean("isedit", isedit);
                fragment.setArguments(args);
                fm.beginTransaction().replace(R.id.house_enter_fragment, fragment).commit();
                break;
            case 1:
                isRent = true;
                rentornot = 1;
                type = ModeConstants.RESIDENCE_RENT;
                House_enter_residence_rent_Fragment fragment1 = new House_enter_residence_rent_Fragment(type, rentornot, houseid, isedit);
                Bundle args1 = new Bundle();
                args1.putInt("houseid", houseid);
                args1.putString("type", type);
                args1.putBoolean("isedit", isedit);
                fragment1.setArguments(args1);
                fm.beginTransaction().replace(R.id.house_enter_fragment, fragment1).commit();
                break;

        }
        SelectHignImg.SelectHigh(imgsh, imgsn, layouts, 0, this);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @OnClick({R.id.house_enter_back, house_enter_residence, R.id.house_enter_villa, R.id.house_enter_office, R.id.house_enter_shop})
    public void onViewClicked(View view) {
        int index = 0;
        switch (view.getId()) {
            case R.id.house_enter_back:
                this.finish();
                break;
            case house_enter_residence:
                index = 0;
                if (!isRent) {
                    type = ModeConstants.RESIDENCE_SELL;
                    rentornot = 0;
                    House_enter_residence_sell_Fragment fragment = new House_enter_residence_sell_Fragment(type, rentornot, houseid, isedit);
                    Bundle args = new Bundle();
                    args.putString("type", type);
                    args.putInt("rentornot", rentornot);
                    args.putInt("houseid", houseid);
                    args.putBoolean("isedit", isedit);
                    fragment.setArguments(args);
                    fm.beginTransaction().replace(R.id.house_enter_fragment, fragment).commit();
                } else {
                    type = ModeConstants.RESIDENCE_RENT;
                    rentornot = 1;
                    House_enter_residence_rent_Fragment fragment = new House_enter_residence_rent_Fragment(type, rentornot, houseid, isedit);
                    Bundle args = new Bundle();
                    args.putString("type", type);
                    args.putInt("rentornot", rentornot);
                    args.putInt("houseid", houseid);
                    args.putBoolean("isedit", isedit);
                    fragment.setArguments(args);
                    fm.beginTransaction().replace(R.id.house_enter_fragment, fragment).commit();
                }
                break;
            case R.id.house_enter_villa:
                index = 1;
                if (!isRent) {
                    type = ModeConstants.VILLA_SELL;
                    rentornot = 0;
                    Log.d(TAG, "onViewClicked: ----------houseid = " + houseid);
                    House_enter_villa_sell_Fragment fragment = new House_enter_villa_sell_Fragment(type, rentornot, houseid, isedit);
                    Bundle args = new Bundle();
                    args.putString("type", type);
                    args.putInt("rentornot", rentornot);
                    args.putInt("houseid", houseid);
                    args.putBoolean("isedit", isedit);
                    fragment.setArguments(args);
                    fm.beginTransaction().replace(R.id.house_enter_fragment, fragment).commit();
                } else {
                    rentornot = 1;
                    type = ModeConstants.VILLA_RENT;
                    Log.d(TAG, "onViewClicked: ----------houseid = " + houseid);
                    House_enter_villa_rent_Fragment fragment = new House_enter_villa_rent_Fragment(type, rentornot, houseid, isedit);
                    Bundle args = new Bundle();
                    args.putString("type", type);
                    args.putInt("rentornot", rentornot);
                    args.putInt("houseid", houseid);
                    args.putBoolean("isedit", isedit);
                    fragment.setArguments(args);
                    fm.beginTransaction().replace(R.id.house_enter_fragment, fragment).commit();
                }
                break;
            case R.id.house_enter_office:
                index = 2;
                if (!isRent) {
                    rentornot = 0;
                    type = ModeConstants.OFFICEBUILDING_SELL;
                    Log.d(TAG, "onViewClicked: ----------houseid = " + houseid);
                    House_enter_office_sell_Fragment fragment = new House_enter_office_sell_Fragment(type, rentornot, houseid, isedit);
                    Bundle args = new Bundle();
                    args.putString("type", type);
                    args.putInt("rentornot", rentornot);
                    args.putInt("houseid", houseid);
                    args.putBoolean("isedit", isedit);
                    fragment.setArguments(args);
                    fm.beginTransaction().replace(R.id.house_enter_fragment, fragment).commit();
                } else {
                    rentornot = 1;
                    type = ModeConstants.OFFICEBUILDING_RENT;
                    Log.d(TAG, "onViewClicked: ----------houseid = " + houseid);
                    House_enter_office_rent_Fragment fragment = new House_enter_office_rent_Fragment(type, rentornot, houseid, isedit);
                    Bundle args = new Bundle();
                    args.putString("type", type);
                    args.putInt("rentornot", rentornot);
                    args.putInt("houseid", houseid);
                    args.putBoolean("isedit", isedit);
                    fragment.setArguments(args);
                    fm.beginTransaction().replace(R.id.house_enter_fragment, fragment).commit();
                }
                break;
            case R.id.house_enter_shop:
                index = 3;
                if (!isRent) {
                    rentornot = 0;
                    type = ModeConstants.SHOP_SELL;
                    Log.d(TAG, "onViewClicked: ----------houseid = " + houseid);
                    House_enter_shop_sell_Fragment fragment = new House_enter_shop_sell_Fragment(type, rentornot, houseid, isedit);
                    Bundle args = new Bundle();
                    args.putString("type", type);
                    args.putInt("rentornot", rentornot);
                    args.putInt("houseid", houseid);
                    args.putBoolean("isedit", isedit);
                    fragment.setArguments(args);
                    fm.beginTransaction().replace(R.id.house_enter_fragment, fragment).commit();

                } else {
                    rentornot = 1;
                    type = ModeConstants.SHOP_RENT;
                    Log.d(TAG, "onViewClicked: ----------houseid = " + houseid);
                    House_enter_shop_rent_Fragment fragment = new House_enter_shop_rent_Fragment(type, rentornot, houseid, isedit);
                    Bundle args = new Bundle();
                    args.putString("type", type);
                    args.putInt("rentornot", rentornot);
                    args.putInt("houseid", houseid);
                    args.putBoolean("isedit", isedit);
                    fragment.setArguments(args);
                    fm.beginTransaction().replace(R.id.house_enter_fragment, fragment).commit();
                }
                break;
        }
        SelectHignImg.SelectHigh(imgsh, imgsn, layouts, index, this);
    }

    //去主线程中更改UI
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ShowLoadingEvent event) {
        Log.d(TAG, "onEventMainThread: -----------------------");
        loadingView.startAnimation();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(HideLodingEvent event) {
        Log.d(TAG, "onEventMainThread: ----------------------");
        loadingView.stopAnimation();
    }
}
