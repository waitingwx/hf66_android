package com.cqxy.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cqxy.adapter.CooperatinHouseAdapter;
import com.cqxy.base.BaseUrl;
import com.cqxy.bean.Users;
import com.cqxy.fyb.HouseDetialActivity;
import com.cqxy.fyb.R;
import com.cqxy.fyb.individualActivity;
import com.cqxy.newbean.MyHouseSource;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.RecycleUtils;
import com.cqxy.utils.UserInf;
import com.cqxy.view.AffirmDialog;

import java.io.IOException;
import java.util.ArrayList;
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

public class Individual_Fragment extends Fragment implements CooperatinHouseAdapter.OnToggleShowDialog,AffirmDialog.ReloadDataListener{
    @BindView(R.id.individual_recyclerview)
    RecyclerView individualRecyclerview;
    private individualActivity act;
    private Unbinder unbinder;
    private CooperatinHouseAdapter adapter;
    private List<MyHouseSource> displayed;
    private List<MyHouseSource> undisplayed;
    private List<Object> datas;

    private String extraurl;
    private boolean isrent;
    private String TAG = "Individual_Fragment";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.act = (individualActivity) context;
    }

    public Individual_Fragment(String extraurl, boolean isrent) {
        this.extraurl = extraurl;
        this.isrent = isrent;
    }

    public Individual_Fragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_individual_coolayout, null);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.extraurl = getArguments().getString("url");
        this.isrent = getArguments().getBoolean("isrent");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecycleview();
        initDatas();

    }


    private void initRecycleview() {
        RecycleUtils.initLinearLayoutRecyclerViews(individualRecyclerview, act);
    }

    public void initDatas() {
        Log.d(TAG, "initDatas: ---------------------");
        OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(act), UserInf.getUserPhonenum(act), BaseUrl.URL + extraurl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("------", "false");
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.d(TAG, "onResponse: ==================str = " + str);
                displayed = MyHouseSource.arrayMyHouseSourceFromData(str, "displayed");
                undisplayed = MyHouseSource.arrayMyHouseSourceFromData(str, "undisplayed");
                if (0 == displayed.size())
                    return;
                datas = new ArrayList<>();
                datas.add(0, "已上架");
                datas.addAll(displayed);
                datas.add("未上架");
                datas.addAll(undisplayed);
                adapter = new CooperatinHouseAdapter(datas, act);
                adapter.setmShowDialogListener(Individual_Fragment.this);
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        individualRecyclerview.setAdapter(adapter);
                        adapter.setOnItemListener(new CooperatinHouseAdapter.MyItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                switch (adapter.getItemViewType(position)) {
                                    case CooperatinHouseAdapter.TITLE:
                                        break;
                                    case CooperatinHouseAdapter.ITEMS:
                                        MyHouseSource myHouseSource = ((MyHouseSource) datas.get(position));
                                        Log.d(TAG, "onItemClick: userid = " + myHouseSource.getUser_id() + ",houseid = " + myHouseSource.getId() + ",sourcetype=" + myHouseSource.getSourcetype());
                                        getUserPhoneByid(myHouseSource.getUser_id(), myHouseSource.getId(), myHouseSource.getSourcetype());
                                        break;
                                }
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

    public void getUserPhoneByid(final int userid, final int houseexampleid, final String type) {
        String url = BaseUrl.URL + "users/" + userid + ".json";
        Log.d(TAG, "getUserPhoneByid: url = " + url);
        OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(act), UserInf.getUserPhonenum(act), url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                Log.d(TAG, "onResponse: -----------------------responseBody = " + responseBody);
                Users user = Users.objectFromData(responseBody, "user");
                String phone = user.getPhone_num();
                Intent intent = new Intent(act, HouseDetialActivity.class);
                Log.d(TAG, "onResponse: phone = " + phone + ",\nmode = " + type + ",\nhouseexample = " + houseexampleid + "\nuserid = " + userid + "\nuser.getid = " + user.getId());
                intent.addFlags(BaseUrl.INDIVIDUAL_FRAGMENT);
                intent.putExtra("phone", phone);
                intent.putExtra("mode", type);
                intent.putExtra("ismaoboli", false);
                intent.putExtra("isrent", isrent);
                intent.putExtra("touserid", userid);
                intent.putExtra("houseexampleid", houseexampleid);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onShowDialog(View view, int position, boolean ischecked) {
        AffirmDialog affirmDialog = new AffirmDialog(act,ischecked);
        affirmDialog.setReloadDataListener(this);
        affirmDialog.show();
    }

    @Override
    public void reloadData() {
        initDatas();
    }
}
