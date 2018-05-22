package com.cqxy.fyb;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.cqxy.base.BaseUrl;
import com.cqxy.bean.Users;
import com.cqxy.fragment.FindFragment;
import com.cqxy.fragment.HomeFragment;
import com.cqxy.fragment.MessageFragment;
import com.cqxy.fragment.PersonalFragment;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.SelectHignImg;
import com.cqxy.utils.SpUtil;
import com.cqxy.utils.UserInf;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.home_home)
    LinearLayout home_home;
    @BindView(R.id.home_find)
    LinearLayout home_find;
    @BindView(R.id.home_message)
    LinearLayout home_message;
    @BindView(R.id.home_my)
    LinearLayout home_my;
    private FragmentManager fm;
    private HomeFragment home_fragment;
    private FindFragment findFragment;
    private MessageFragment messageFragment;
    private PersonalFragment personalFragment;
    private int img_hs[] = new int[]{R.mipmap.home_home_h, R.mipmap.home_room_h, R.mipmap.home_news_h, R.mipmap.home_mine_h};
    private int img_ns[] = new int[]{R.mipmap.home_home_n, R.mipmap.home_room_n, R.mipmap.home_news_n, R.mipmap.home_mine_n};
    private LinearLayout layouts[] = new LinearLayout[4];
    private String TAG = "HomeActivity";
    private boolean isfirst = true;
    private Fragment[] fragments;
    private int lastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        checkInstanceState(savedInstanceState);
        ButterKnife.bind(this);
        SayHello();
        initFragments();
        initListeners();
        loadUserInfo();
    }

    private void checkInstanceState(Bundle savedInstanceState) {
        FragmentManager fm = getSupportFragmentManager();
        if (savedInstanceState != null) {
            home_fragment = (HomeFragment) fm.findFragmentByTag(HomeFragment.class.getName());
            findFragment = (FindFragment) fm.findFragmentByTag(FindFragment.class.getName());
            messageFragment = (MessageFragment) fm.findFragmentByTag(MessageFragment.class.getName());
            personalFragment = (PersonalFragment) fm.findFragmentByTag(PersonalFragment.class.getName());
        } else {
            home_fragment = new HomeFragment();
            findFragment = new FindFragment();
            messageFragment = new MessageFragment();
            personalFragment = new PersonalFragment();
        }
    }

    private void loadUserInfo() {
        int anInt = SpUtil.getInt(this, SpUtil.SP_USER, SpUtil.USER_ID, -1);
        Log.d(TAG, "loadUserInfo: ---------------id = " + anInt);
        //如果为-1，就提示登录信息过期，重新登录
        if(-1 == anInt)
            reLogin();
        String url = BaseUrl.URL + "users/" + UserInf.getUserId(this) + ".json";
        Log.d(TAG, "loadUserInfo: ==============url = " + url);
        OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(this),
                UserInf.getUserPhonenum(this),
                url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseBody = response.body().string();
                        Log.d(TAG, "onResponse: -----------------responseBody = " + responseBody);
                        Users users = Users.objectFromData(responseBody, "user");
                        if (null == users)
                            return;
                        SpUtil.putInt(HomeActivity.this, SpUtil.SP_USER, SpUtil.USER_ID, users.getId());
                        SpUtil.putString(HomeActivity.this, SpUtil.SP_USER, SpUtil.USER_PHONENUM, users.getPhone_num());
                        SpUtil.putString(HomeActivity.this, SpUtil.SP_USER, SpUtil.USER_QIANMING, users.getQianming());
                        SpUtil.putString(HomeActivity.this, SpUtil.SP_USER, SpUtil.USER_SHANGQUAN, users.getShangquan());
                        SpUtil.putString(HomeActivity.this, SpUtil.SP_USER, SpUtil.USER_GENDER, users.getGender());
                        SpUtil.putString(HomeActivity.this, SpUtil.SP_USER, SpUtil.USER_NAME, users.getUser_name());
                        SpUtil.putString(HomeActivity.this, SpUtil.SP_USER, SpUtil.USER_RECOMMEND, users.getRecommend());
                        SpUtil.putString(HomeActivity.this, SpUtil.SP_USER, SpUtil.USER_RECOMM_NAME, users.getRecomm_name());
//                        AppPreferences appPreferences = new AppPreferences(HomeActivity.this);
//                        appPreferences.put(SpUtil.USER_PHONENUM, users.getPhone_num());
//                        appPreferences.put(SpUtil.USER_QIANMING, users.getQianming());
//                        appPreferences.put(SpUtil.USER_SHANGQUAN, users.getShangquan());
//                        appPreferences.put(SpUtil.USER_GENDER, users.getGender());
//                        appPreferences.put(SpUtil.USER_NAME, users.getUser_name());
//                        appPreferences.put(SpUtil.USER_RECOMMEND, users.getRecommend());
//                        appPreferences.put(SpUtil.USER_RECOMM_NAME, users.getRecomm_name());
//                        appPreferences.put(SpUtil.USER_ID,users.getId());
                    }
                });
    }

    private void reLogin() {
        Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void SayHello() {
        layouts[0] = home_home;
        layouts[1] = home_find;
        layouts[2] = home_message;
        layouts[3] = home_my;


    }

    private void initListeners() {
        home_my.setOnClickListener(this);
        home_home.setOnClickListener(this);
        home_find.setOnClickListener(this);
        home_message.setOnClickListener(this);
        home_find.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (isfirst) {
                    presentShowcaseSequence();
                    isfirst = false;
                }
            }
        });
    }

    private void presentShowcaseSequence() {

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, "homeactivity");

        sequence.setOnItemShownListener(new MaterialShowcaseSequence.OnSequenceItemShownListener() {
            @Override
            public void onShow(MaterialShowcaseView itemView, int position) {
//                Toast.makeText(itemView.getContext(), "Item #" + position, Toast.LENGTH_SHORT).show();
            }
        });

        sequence.setConfig(config);
        int maskColor = addAlphaToColor(R.color.colorBlue, 40, HomeActivity.this);
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(home_find)
                        .setDismissText("知道了")
                        .setDismissTextColor(Color.argb(255, 45, 125, 222))
                        .setContentText("帮你寻找房源")
                        .setMaskColour(Color.argb(150, 0, 0, 0))
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(home_message)
                        .setDismissText("知道了")
                        .setContentText("查看所有聊天")
                        .setMaskColour(Color.argb(150, 0, 0, 0))
                        .setDismissTextColor(Color.argb(255, 45, 125, 222))
                        .build()
        );
        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(home_my)
                        .setDismissText("知道了")
                        .setDismissTextColor(Color.argb(255, 45, 125, 222))
                        .setMaskColour(Color.argb(150, 0, 0, 0))
                        .setContentText("个人资料在这里")
                        .build()
        );

        sequence.start();

    }

    private void initFragments() {
        Log.d(TAG, "initFragments: ------------------------start");
        fm = getSupportFragmentManager();
        fragments = new Fragment[]{home_fragment, findFragment, messageFragment, personalFragment};
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        checkAdded(fragmentTransaction);

        for (int i = 0; i < fragments.length; i++) {
            fragmentTransaction.hide(fragments[i]);
        }
        fragmentTransaction.show(fragments[0]).commit();
    }

    private void checkAdded(FragmentTransaction fragmentTransaction) {
        for (int i = 0; i < fragments.length; i++) {
            if (!fragments[i].isAdded())
                fragmentTransaction.add(R.id.home_fragment, fragments[i], fragments[i].getClass().getName());
        }
    }

    @Override
    public void onClick(View view) {
        int index = 0;
        FragmentTransaction transaction = fm.beginTransaction();
        switch (view.getId()) {
            case R.id.home_home:
                index = 0;
                break;
            case R.id.home_find:
                index = 1;
                break;
            case R.id.home_message:
                index = 2;
                break;
            case R.id.home_my:
                index = 3;
                break;
        }
        //隐藏上一次显示的页面
        transaction.hide(fragments[lastFragment]);

        transaction.show(fragments[index]);

        transaction.commit();
        //重新记录下标
        lastFragment = index;
        if (view.getId() == R.id.home_home || view.getId() == R.id.home_my)
            presentShowcaseSequence();
        SelectHignImg.SelectHigh(img_hs, img_ns, layouts, index, this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private int addAlphaToColor(int color, int alpha) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    private int addAlphaToColor(int colorId, int alpha, Context context) {
        return addAlphaToColor(ContextCompat.getColor(context, colorId), alpha);
    }

}
