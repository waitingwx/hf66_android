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
import android.widget.Toast;

import com.cqxy.adapter.CooperationAdapter;
import com.cqxy.base.BaseUrl;
import com.cqxy.bean.CooperationHouses;
import com.cqxy.bean.Users;
import com.cqxy.fyb.DealActivity;
import com.cqxy.fyb.HouseDetialActivity;
import com.cqxy.fyb.R;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.UserInf;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/9/18.
 */

public class DealFragmentRent extends Fragment {
    @BindView(R.id.house_deal_rent_recycle)
    RecyclerView houseDealRentRecycle;
    @BindView(R.id.house_deal_rent_refreshlayout)
    SwipeRefreshLayout houseDealRentRefreshlayout;
    private DealActivity act;
    private Unbinder unbinder;
    private CooperationAdapter adapter;
    private List<CooperationHouses.CommentsBean> datas;

    public DealFragmentRent() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.act = (DealActivity) context;
    }

    private String url;
    public DealFragmentRent(String url) {
        this.url = url;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_deal_rentlayout, null);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecycleview();
        initDatas();
        initRefresh();
    }

    private void initRefresh() {
        houseDealRentRefreshlayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        houseDealRentRefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter = new CooperationAdapter(datas, act);
                Toast.makeText(act, "刷新成功！", Toast.LENGTH_SHORT).show();
                houseDealRentRefreshlayout.setRefreshing(false);
            }
        });
    }


    private void initRecycleview() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(act);
        houseDealRentRecycle.setLayoutManager(layoutManager);
        houseDealRentRecycle.setItemAnimator(new DefaultItemAnimator());
        houseDealRentRecycle.setHasFixedSize(true);
    }

    private void initDatas() {
        datas = null;
        OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(act),UserInf.getUserPhonenum(act),url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("------", "false");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.d("detialActivity", "onResponse: response.body = " +str);
                datas = CooperationHouses.objectFromData(str).getComments();
                if(null == datas) {
                    return;
                }
                adapter = new CooperationAdapter(datas, act);
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        houseDealRentRecycle.setAdapter(adapter);
                        adapter.setOnItemListener(new CooperationAdapter.MyItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                CooperationHouses.CommentsBean commentsBean = datas.get(position);
                                Log.d("detialActivity", "onItemClick: userid = " + commentsBean.getUser_id() + ",houseid = " + commentsBean.getId() + ",sourcetype=" + commentsBean.getSourcetype());
                                getUserPhoneByid(commentsBean.getUser_id(), commentsBean.getId(), commentsBean.getSourcetype());
                            }
                        });
                    }
                });

            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void getUserPhoneByid(int userid, final int houseexampleid, final String type) {
        String url = BaseUrl.URL + "users/" + userid + ".json";
        Log.d("FindHOuse_COntentfra", "getUserPhoneByid: url = " + url);
        OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(act),UserInf.getUserPhonenum(act),url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                Users user = Users.objectFromData(responseBody,"user");
                String phone = user.getPhone_num();
                Intent intent = new Intent(act, HouseDetialActivity.class);
                Log.d("detialActivity", "onResponse: phone = " + phone + ",mode = " + type + ",houseexample = " + houseexampleid);
                intent.addFlags(BaseUrl.DEAL_FRAGMENT);
                intent.putExtra("phone", phone);
                intent.putExtra("mode", type);
                intent.putExtra("houseexampleid", houseexampleid);
                intent.putExtra("ismaoboli",false);
                intent.putExtra("isrent",false);
                intent.putExtra("touserid",user.getId());
                startActivity(intent);
            }
        });
    }
}
