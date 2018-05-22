package com.cqxy.fyb;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqxy.adapter.ListViewAdapter;
import com.cqxy.adapter.QuxianAdapter;
import com.cqxy.adapter.ShangqAdapter;
import com.cqxy.base.BaseUrl;
import com.cqxy.bean.HideLodingEvent;
import com.cqxy.bean.ShangqBean;
import com.cqxy.bean.ShowLoadingEvent;
import com.cqxy.constants.QuxianBean;
import com.cqxy.constants.ShangquanBean;
import com.cqxy.constants.XiaoquBean;
import com.cqxy.fragment.FindHouse_ContentFragment;
import com.cqxy.fragment.FindHouse_RightFrigment;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.SpUtil;
import com.cqxy.view.LoadingView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Callback;
import okhttp3.Response;

import static com.cqxy.constants.FindHouseContants.OFFICEBUILDING_RENT;
import static com.cqxy.constants.FindHouseContants.OFFICEBUILDING_SELL;
import static com.cqxy.constants.FindHouseContants.RESIDENCE_RENT;
import static com.cqxy.constants.FindHouseContants.RESIDENCE_SELL;
import static com.cqxy.constants.FindHouseContants.SHOP_RENT;
import static com.cqxy.constants.FindHouseContants.SHOP_SELL;
import static com.cqxy.constants.FindHouseContants.VILLA_RENT;
import static com.cqxy.constants.FindHouseContants.VILLA_SELL;

public class FindHouseActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.find_back)
    ImageView find_back;
    @BindView(R.id.find_drawerlayout)
    DrawerLayout find_drawerlayout;
    @BindView(R.id.find_left)
    FrameLayout find_left;
    @BindView(R.id.find_right)
    FrameLayout find_right;
    @BindView(R.id.find_selectarea)
    AppCompatSpinner findSelectarea;
    @BindView(R.id.find_selectshop)
    AppCompatSpinner findSelectshop;
    @BindView(R.id.find_selecthouse)
    AppCompatSpinner findSelecthouse;
    @BindView(R.id.find_content)
    FrameLayout findContent;
    @BindView(R.id.find_others)
    TextView findMore;
    @BindView(R.id.tv_head_text)
    TextView tvHead;
    private boolean isShangquanFirst = true;
    private boolean isXiaoquFirst = true;

    private FragmentManager fragmentManager;
    private int width, height;
    private FindHouse_ContentFragment contentFragment;
    private int flag;
    ArrayList<QuxianBean> qxBeanList = new ArrayList<>();
    ArrayList<XiaoquBean.XiaoqusBean> xiaoqusDatas = new ArrayList<>();
    List<ShangquanBean> shangquanDatas = new ArrayList<>();
    private String TAG = "FindHouseActivity";
    private LoadingView loadingView;
    private FindHouse_RightFrigment filterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_house);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        getWindows();
        initFragment();
        setTitleText();
        initListener();
        bindSpinner();
    }

    private void setTitleText() {
        flag = getIntent().getIntExtra("flag", 1);
        switch (flag) {
            case RESIDENCE_RENT:
                tvHead.setText("住房租赁");
                contentFragment.setIsrent(true);
                break;
            case RESIDENCE_SELL:
                tvHead.setText("住房买卖");
                contentFragment.setIsrent(false);
                break;
            case OFFICEBUILDING_RENT:
                tvHead.setText("写字楼租赁");
                contentFragment.setIsrent(true);
                break;
            case OFFICEBUILDING_SELL:
                tvHead.setText("写字楼买卖");
                contentFragment.setIsrent(false);
                break;
            case VILLA_RENT:
                tvHead.setText("别墅租赁");
                contentFragment.setIsrent(true);
                break;
            case VILLA_SELL:
                tvHead.setText("别墅买卖");
                contentFragment.setIsrent(false);
                break;
            case SHOP_RENT:
                tvHead.setText("商铺租赁");
                contentFragment.setIsrent(true);
                break;
            case SHOP_SELL:
                tvHead.setText("商铺买卖");
                contentFragment.setIsrent(false);
                break;

        }
    }

    private void bindSpinner() {
        QuxianBean element = new QuxianBean();
        element.setName("全部");
        qxBeanList.add(0, element);
        QuxianAdapter quxianAdapter = new QuxianAdapter(this, qxBeanList);
        findSelectarea.setAdapter(quxianAdapter);
        findSelectarea.setSelection(0, true);

        ShangquanBean bean = new ShangquanBean();
        bean.setName("全部");
        shangquanDatas.add(0, bean);
        final ShangqAdapter shangqAdapter = new ShangqAdapter(FindHouseActivity.this, shangquanDatas);
        findSelectshop.setAdapter(shangqAdapter);
        findSelectshop.setSelection(0, true);

        String responseBody = SpUtil.getString(this, "shangquan", "responsebody");
        if (responseBody == "")
            return;//TODO:重新获取
        //转换为商圈bean
        final List<ShangqBean.CityBean> city = ShangqBean.objectFromData(responseBody).getCity();

        for (int i = 0; i < city.size(); i++) {
            QuxianBean quxian = city.get(i).getQuxian();
            qxBeanList.add(quxian);
        }
        quxianAdapter.notifyDataSetChanged();
        findSelectarea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    return;
                shangquanDatas.addAll(city.get(position - 1).getShangquan());
                shangqAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (flag == RESIDENCE_RENT && flag == RESIDENCE_SELL)
            return;
        findSelectshop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    return;
                int shangquanId = shangquanDatas.get(position).getId();
                if (flag != RESIDENCE_SELL && flag != RESIDENCE_RENT) {
                    contentFragment.initDatas("/shangquans/", shangquanId);
                } else {
                    loadXiaoqu(shangquanId);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        findSelecthouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (0 == xiaoqusDatas.size())
                    return;
                contentFragment.initDatas("/xiaoqus/", xiaoqusDatas.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void loadXiaoqu(int shangQuanId) {
        OkHttpUtils.okhttpGet(BaseUrl.URL + "xiaoqus/" + shangQuanId + ".json", new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d(TAG, "onResponse: string = " + string);
                if (xiaoqusDatas.size() != 0)
                    xiaoqusDatas.clear();
                xiaoqusDatas.addAll(XiaoquBean.objectFromData(string).getXiaoqus());
                FindHouseActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run: ---------------");
                        findSelecthouse.setVisibility(View.VISIBLE);
                        findSelecthouse.setAdapter(new ListViewAdapter<XiaoquBean.XiaoqusBean>(xiaoqusDatas, FindHouseActivity.this) {
                            @Override
                            public void showTexts(int position, TextView covertView) {
                                covertView.setText(xiaoqusDatas.get(position).getName());
                            }
                        });
                    }
                });
            }
        });
    }


    private void initListener() {
        findMore.setOnClickListener(this);
        find_back.setOnClickListener(this);
    }

    public void getWindows() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;

        find_right.getLayoutParams().width = width / 4 * 3;
        loadingView = ((LoadingView) findViewById(R.id.loading_enter));
    }

    private void initFragment() {
        flag = getIntent().getIntExtra("flag", 1);
        fragmentManager = getSupportFragmentManager();
        contentFragment = new FindHouse_ContentFragment();
        Bundle cont = new Bundle();
        cont.putInt("flag", flag);
        contentFragment.setFlag(flag);
        contentFragment.setArguments(cont);
        findSelecthouse.setVisibility(View.GONE);
        filterFragment = new FindHouse_RightFrigment();
        Bundle args = new Bundle();
        args.putInt("flag", flag);
        filterFragment.setArguments(args);
        fragmentManager.beginTransaction()
                .add(R.id.find_content, contentFragment)
                .add(R.id.find_right, filterFragment).commit();

        find_drawerlayout.addDrawerListener(new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                findSelectarea.setVisibility(View.GONE);
                findSelecthouse.setVisibility(View.GONE);
                findSelectshop.setVisibility(View.GONE);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                findSelectarea.setVisibility(View.VISIBLE);
                findSelectshop.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }


    @Override
    public void onClick(View view) {
        int index = 0;
        switch (view.getId()) {
            case R.id.find_back:
                finish();
                break;
            case R.id.find_others:
                find_drawerlayout.openDrawer(Gravity.RIGHT);
                break;
        }

    }


    public void selectButton(int index, Button[] buttons) {
        for (int i = 0; i < buttons.length; i++) {
            if (index == i) {
                buttons[i].setTextColor(getColor(R.color.colorBlue));
            } else {
                buttons[i].setTextColor(getColor(R.color.black9a));
            }
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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
