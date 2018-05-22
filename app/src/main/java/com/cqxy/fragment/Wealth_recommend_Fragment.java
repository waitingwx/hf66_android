package com.cqxy.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cqxy.adapter.RecommendAdapter;
import com.cqxy.bean.MoneyDetailBean;
import com.cqxy.fyb.MyWealthActivity;
import com.cqxy.fyb.R;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.RecycleUtils;
import com.cqxy.utils.UserInf;
import com.cqxy.view.DetailDialog;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by lpl on 17-9-20.
 */

public class Wealth_recommend_Fragment extends Fragment {

    @BindView(R.id.wealth_recyclerview)
    RecyclerView wealthRecyclerview;
    private MyWealthActivity act;
    private Unbinder unbinder;
    String dataurl = "";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.act = (MyWealthActivity) context;
    }

    public Wealth_recommend_Fragment(String dataurl) {
        this.dataurl = dataurl;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_recommendlayout, null);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecycleview();
        initDatas();

    }


    private void initRecycleview() {
        RecycleUtils.initLinearLayoutRecyclerViews(wealthRecyclerview, act);
    }

    private void initDatas() {
        OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(act), UserInf.getUserPhonenum(act), dataurl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responeBody = response.body().string();
                Log.d("WealthRecommentFragment", "onResponse: response.body = " + responeBody);
                final List<MoneyDetailBean> detailList = MoneyDetailBean.arrayMoneyDetailBeanFromData(responeBody, "money");
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RecommendAdapter adapter = new RecommendAdapter(act, detailList);
                        wealthRecyclerview.setAdapter(adapter);
                        adapter.setOnItemListener(new RecommendAdapter.MyItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                int orderno = detailList.get(position).getOrderno();
                                if (0 != orderno)
                                    showDialog(orderno);
                            }
                        });
                    }
                });
            }
        });

    }

    private void showDialog(int position) {
        new DetailDialog(act, position).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
