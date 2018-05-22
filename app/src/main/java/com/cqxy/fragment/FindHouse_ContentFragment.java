package com.cqxy.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxy.adapter.HouseRentAdapter;
import com.cqxy.adapter.HouseSellAdapter;
import com.cqxy.base.BaseUrl;
import com.cqxy.bean.HideLodingEvent;
import com.cqxy.bean.HouseExampleBeans;
import com.cqxy.bean.HouseRentBean;
import com.cqxy.bean.HouseSellBean;
import com.cqxy.bean.LoadFilteredEvent;
import com.cqxy.bean.ShowLoadingEvent;
import com.cqxy.bean.Users;
import com.cqxy.constants.ModeConstants;
import com.cqxy.fyb.FindHouseActivity;
import com.cqxy.fyb.HouseDetialActivity;
import com.cqxy.fyb.R;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.SpaceItemDecoration;
import com.cqxy.utils.UserInf;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/9/1.
 */

public class FindHouse_ContentFragment extends Fragment {
    private int flag = 1;
    private static String TAG = "FindHouse_Content";
    @BindView(R.id.finghouse_content_recyclerview)
    RecyclerView finghouseContentRecyclerview;
    @BindView(R.id.finghouse_content_refreshlayout)
    SwipeRefreshLayout finghouseContentRefreshlayout;
    @BindView(R.id.tv_none_data)
    TextView tvNoneData;
    Unbinder unbinder;
    private FindHouseActivity act;
    private HouseRentAdapter rentadapter;
    private HouseSellAdapter selladapter;
    private List<HouseRentBean.CommentsBean> rentdatas;
    private List<HouseSellBean.CommentsBean> selldatas;
    private boolean isrent = false;
    private String extraurl;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.act = (FindHouseActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_findhouse_contentlayout, null);
        unbinder = ButterKnife.bind(this, view);
        finghouseContentRecyclerview  = view.findViewById(R.id.finghouse_content_recyclerview);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        flag = getArguments().getInt("flag");
        initRecycleview();
        initDatas();
        initRefresh();
    }

    private void initRefresh() {
        finghouseContentRefreshlayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        finghouseContentRefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isrent) {
                    rentadapter = new HouseRentAdapter(rentdatas, act);
                } else {
                    selladapter = new HouseSellAdapter(selldatas, act);
                }
                Toast.makeText(act, "刷新成功！", Toast.LENGTH_SHORT).show();
                finghouseContentRefreshlayout.setRefreshing(false);
            }
        });
    }

    public boolean isrent() {
        return isrent;
    }

    public void setIsrent(boolean isrent) {
        this.isrent = isrent;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }


    public int getFlag() {
        return flag;
    }


    private void initRecycleview() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(act);
        finghouseContentRecyclerview.setLayoutManager(layoutManager);
        finghouseContentRecyclerview.setItemAnimator(new DefaultItemAnimator());
        finghouseContentRecyclerview.setHasFixedSize(true);
    }

    public void initDatas() {
        Log.d(TAG, "initDatas: =======================no params ----- flag = " + flag);
        tvNoneData.setVisibility(View.GONE);
        rentdatas = new ArrayList<>();
        selldatas = new ArrayList<>();
        switch (flag) {
            case 1:
                extraurl = ModeConstants.RESIDENCE_SELL;
                break;
            case 2:
                extraurl = ModeConstants.VILLA_SELL;
                break;
            case 3:
                extraurl = ModeConstants.OFFICEBUILDING_SELL;
                break;
            case 4:
                extraurl = ModeConstants.SHOP_SELL;
                break;
            case 5:
                extraurl = ModeConstants.RESIDENCE_RENT;
                break;
            case 6:
                extraurl = ModeConstants.VILLA_RENT;
                break;
            case 7:
                extraurl = ModeConstants.OFFICEBUILDING_RENT;
                break;
            case 8:
                extraurl = ModeConstants.SHOP_RENT;
                break;
        }
        getDatas(extraurl, isrent);

    }

    public void initDatas(String filterItem, int filterId) {
        EventBus.getDefault().post(new ShowLoadingEvent());
        rentdatas = new ArrayList<>();
        selldatas = new ArrayList<>();
        switch (flag) {
            case 1:
                extraurl = ModeConstants.RESIDENCE_SELL;
                break;
            case 2:
                extraurl = ModeConstants.VILLA_SELL;
                break;
            case 3:
                extraurl = ModeConstants.OFFICEBUILDING_SELL;
                break;
            case 4:
                extraurl = ModeConstants.SHOP_SELL;
                break;
            case 5:
                extraurl = ModeConstants.RESIDENCE_RENT;
                break;
            case 6:
                extraurl = ModeConstants.VILLA_RENT;
                break;
            case 7:
                extraurl = ModeConstants.OFFICEBUILDING_RENT;
                break;
            case 8:
                extraurl = ModeConstants.SHOP_RENT;
                break;
        }
        getDatas(extraurl, filterItem, filterId, isrent);

    }


    private void getDatas(String extraurl, final boolean isrent) {
        String url = BaseUrl.URL + extraurl;
        Log.d(TAG, "getDatas: -------------------------------------no params----url = " + url);
        OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(act), UserInf.getUserPhonenum(act), url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "false:" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.d(TAG, "onResponse: str = " + str);
                Gson gson = new Gson();
                if (isrent) {
                    rentdatas = gson.fromJson(str, HouseRentBean.class).getComments();
                    rentadapter = new HouseRentAdapter(rentdatas, act);
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "run: -------setrentadapter");
                            finghouseContentRecyclerview.addItemDecoration(new SpaceItemDecoration(4));
                            finghouseContentRecyclerview.setAdapter(rentadapter);
                            rentadapter.setOnItemListener(new HouseRentAdapter.MyItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    startActivityWithPhone(rentdatas.get(position).getHouse_example_id());
                                }
                            });
                        }
                    });
                } else {
                    selldatas = gson.fromJson(str, HouseSellBean.class).getComments();
                    selladapter = new HouseSellAdapter(selldatas, act);
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "run: -------set sell adapter");
                            finghouseContentRecyclerview.setAdapter(selladapter);
                            selladapter.setOnItemListener(new HouseSellAdapter.MyItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    startActivityWithPhone(selldatas.get(position).getHouse_example_id());
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    private void getDatas(String extraurl, String filterItem, int filterid, final boolean isrent) {
        Log.d(TAG, "getDatas: ============================================with params");
        OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(act), UserInf.getUserPhonenum(act),
                BaseUrl.URL + extraurl + filterItem + filterid + ".json",
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("------", "false:" + e.toString());
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {

                        String str = response.body().string();
                        Log.d(TAG, "onResponse: str = " + str);
                        Gson gson = new Gson();
                        EventBus.getDefault().post(new HideLodingEvent());
                        if (isrent) {
                            Log.d(TAG, "onResponse: ----------------------------isrent = true");
                            rentdatas = gson.fromJson(str, HouseRentBean.class).getComments();
                            rentadapter = new HouseRentAdapter(rentdatas, act);
                            act.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d(TAG, "run: ============rentdatas.size() = " + rentdatas.size());
                                    if (rentdatas.size() == 0) {
                                        Log.d(TAG, "run: ===========================rentdatas.size = 0");
                                        tvNoneData.setVisibility(View.VISIBLE);
                                    } else {
                                        Log.d(TAG, "run: ===========================rentdatas.size != 0");
                                        tvNoneData.setVisibility(View.GONE);
                                    }
                                    finghouseContentRecyclerview.addItemDecoration(new SpaceItemDecoration(4));
                                    finghouseContentRecyclerview.setAdapter(rentadapter);
                                    rentadapter.setOnItemListener(new HouseRentAdapter.MyItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            startActivityWithPhone(rentdatas.get(position).getHouse_example_id());
                                        }
                                    });
                                }
                            });
                        } else {
                            Log.d(TAG, "onResponse: ----------------------------isrent = false");
                            selldatas = gson.fromJson(str, HouseSellBean.class).getComments();
                            selladapter = new HouseSellAdapter(selldatas, act);
                            Log.d(TAG, "run: ============rentdatas.size() = " + selldatas.size());
                            act.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (selldatas.size() == 0) {
                                        Log.d(TAG, "run: ===========================rentdatas.size = 0");
                                        tvNoneData.setVisibility(View.VISIBLE);
                                    } else {
                                        Log.d(TAG, "run: ===========================rentdatas.size != 0");
                                        tvNoneData.setVisibility(View.GONE);
                                    }
                                    finghouseContentRecyclerview.setAdapter(selladapter);
                                    selladapter.setOnItemListener(new HouseSellAdapter.MyItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            startActivityWithPhone(selldatas.get(position).getHouse_example_id());
                                        }
                                    });
                                }
                            });
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    public void startActivityWithPhone(int houseid) {

        String url = BaseUrl.URL + "house_examples/" + houseid + ".json";
        OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(act), UserInf.getUserPhonenum(act), url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                final HouseExampleBeans.HouseexampleBean house = gson.fromJson(response.body().string(), HouseExampleBeans.class).getHouseexample();
                final int houseexample_id = house.getId();
                final int touserid = house.getUser_id();
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getUserPhoneByid(house.getUser_id(), houseexample_id, isrent, touserid);
                    }
                });

            }
        });
    }

    public void getUserPhoneByid(int userid, final int houseexampleid, final boolean isrent, final int touserid) {
        String url = BaseUrl.URL + "users/" + userid + ".json";
        Log.d(TAG, "getUserPhoneByid: url = " + url + "\nuserid = " + userid + "\ntourserid =" + userid);
        OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(act), UserInf.getUserPhonenum(act), url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String responseBody = response.body().string();
                Log.d(TAG, "onResponse: =====================responseBody = " + responseBody);
                Users user = Users.objectFromData(responseBody, "user");
                String phone = user.getPhone_num();
                Intent intent = new Intent(act, HouseDetialActivity.class);
                intent.addFlags(BaseUrl.FINDHOUSE_FRAGMENT);
                intent.putExtra("phone", phone);
                intent.putExtra("mode", extraurl);
                intent.putExtra("houseexampleid", houseexampleid);
                intent.putExtra("isrent", isrent);
                intent.putExtra("touserid", touserid);
                intent.putExtra("ismaoboli", true);
                startActivity(intent);
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEventBackgroundThread(LoadFilteredEvent event) {
        getDatas(extraurl, isrent, event);
    }

    private void getDatas(String extraurl, final boolean isrent, LoadFilteredEvent event) {
        Log.d(TAG, "getDatas: ------------------------------------LoadFilteredEvent");

        FormBody.Builder builder = new FormBody.Builder();
        if (null != event.getStrPrevalue())
            builder.add("prevalue", event.getStrPrevalue());
        if (null != event.getStrEndvalue())
            builder.add("endvalue", event.getStrEndvalue());
        if (null != event.getStrPrearea())
            builder.add("prearea", event.getStrPrearea());
        if (null != event.getStrEndarea())
            builder.add("endarea", event.getStrEndarea());
        if (null != event.getStrAspect())
            builder.add("aspect", event.getStrAspect());
        if (null != event.getStrDecoration())
            builder.add("decoration", event.getStrDecoration());
        if (null != event.getHousetype())
            builder.add("housetype", event.getHousetype());
        if (null != event.getStrPreage())
            builder.add("preage",event.getStrPreage());
        if(null != event.getStrEndage())
            builder.add("endage",event.getStrEndage());
        if(null != event.getStrPrefloor())
            builder.add("prefloor",event.getStrPrefloor());
        if(null != event.getStrEndfloor())
            builder.add("endfloor",event.getStrEndfloor());
        FormBody formBody = builder.build();
        OkHttpUtils.okhttpPost(formBody, BaseUrl.URL + extraurl + "/filter", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "false:" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String str = response.body().string();
                Log.d(TAG, "onResponse: str = " + str);
                Gson gson = new Gson();
                if (isrent) {
                    HouseRentBean houseRentBean = gson.fromJson(str, HouseRentBean.class);
                    if (null == houseRentBean)
                        return;
                    Log.d(TAG, "onResponse: ---------------------houserentbean != null----------111");
                    rentdatas = houseRentBean.getComments();
                    rentadapter = new HouseRentAdapter(rentdatas, act);
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "run: -------setrentadapter");
                            finghouseContentRecyclerview.addItemDecoration(new SpaceItemDecoration(4));
                            if(null == rentadapter)
                                return;
                            Log.d(TAG, "run: ------------rentadapter != null");
                            finghouseContentRecyclerview.setAdapter(rentadapter);
                            rentadapter.notifyDataSetChanged();
                            rentadapter.setOnItemListener(new HouseRentAdapter.MyItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    startActivityWithPhone(rentdatas.get(position).getHouse_example_id());
                                }
                            });
                        }
                    });
                } else {
                    HouseSellBean houseSellBean = gson.fromJson(str, HouseSellBean.class);
                    if(null == houseSellBean)
                        return;
                    Log.d(TAG, "onResponse: ---------------------houseSellBean != null----------111");
                    selldatas = houseSellBean.getComments();
                    selladapter = new HouseSellAdapter(selldatas, act);
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "run: -------set sell adapter");
                            if(null == selladapter)
                                return;
                            Log.d(TAG, "run: ------------selladapter != null");
                            finghouseContentRecyclerview.setAdapter(selladapter);
                            selladapter.notifyDataSetChanged();
                            selladapter.setOnItemListener(new HouseSellAdapter.MyItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    startActivityWithPhone(selldatas.get(position).getHouse_example_id());
                                }
                            });
                        }
                    });
                }
            }
        });
    }
}
