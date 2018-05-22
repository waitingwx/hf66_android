package com.cqxy.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.cqxy.adapter.FunctionAdapter;
import com.cqxy.adapter.NomalGlideImageLoader;
import com.cqxy.adapter.ZhanBaoAdapter;
import com.cqxy.base.BaseUrl;
import com.cqxy.bean.GridViewItem;
import com.cqxy.bean.ZhanBaoBean;
import com.cqxy.constants.ImageresourcesBean;
import com.cqxy.fyb.AfterServiceActivity;
import com.cqxy.fyb.BuyProjectActivity;
import com.cqxy.fyb.CalendarActivity;
import com.cqxy.fyb.DealActivity;
import com.cqxy.fyb.DownActivity;
import com.cqxy.fyb.HomeActivity;
import com.cqxy.fyb.House_enterActivity;
import com.cqxy.fyb.R;
import com.cqxy.fyb.ToolsActivity;
import com.cqxy.utils.ImageTask;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.SpaceItemDecoration;
import com.google.gson.Gson;
import com.youth.banner.Banner;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private Button home_qiandao;
    private RecyclerView home_zhanbao;
    private RecyclerView home_function;

    private HomeActivity act;

    //战报轮播
    private ZhanBaoAdapter zhanbaoadapter;
    //功能表
    private FunctionAdapter functionAdapter;
    private List<GridViewItem> functiondatas;
    Banner banner;

    private int[] function_img = {R.mipmap.home_inputing,
            R.mipmap.home_makedeal,
            R.mipmap.home_service,
            R.mipmap.home_download,
            R.mipmap.home_tools,
            R.mipmap.home_information,
            R.mipmap.home_preservation,
            R.mipmap.home_products};
    private String[] function_contents = {"房源录入", "成交管理", "后期服务", "下载管理", "常用工具", "客户信息", "保权", "购买产品"};
    private MyHandler handle = new MyHandler(this);
    final List<String> tempList = new ArrayList<>();
    private String TAG = "HomeFragment";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.act = (HomeActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, null);

        ButterKnife.bind(view);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        home_qiandao = view.findViewById(R.id.home_qiandao);
        home_function = view.findViewById(R.id.home_function);
        home_zhanbao = view.findViewById(R.id.home_zhanbao);
        banner = view.findViewById(R.id.banner);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
        initDatas();
        initlisteners();
        getZhanBao();
    }


    private void initRecyclerView() {
        home_zhanbao.setLayoutManager(new LinearLayoutManager(act));
        home_zhanbao.setItemAnimator(new DefaultItemAnimator());
        home_zhanbao.setHasFixedSize(true);

        home_function.setLayoutManager(new GridLayoutManager(act, 4));
        home_function.setItemAnimator(new DefaultItemAnimator());
        home_function.setHasFixedSize(true);
        home_function.addItemDecoration(new SpaceItemDecoration(40));

    }

    Thread repateThread = new Thread() {
        @Override
        public void run() {
            super.run();
            while (tempList.size() != 0) {
                try {
                    Message msg = new Message();
                    msg.what = 3;
                    handle.sendMessage(msg);
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void initDatas() {
        //功能表实现
        functiondatas = new ArrayList<>();
        for (int i = 0; i < function_contents.length; i++) {
            functiondatas.add(new GridViewItem(function_img[i], function_contents[i]));
        }
        functionAdapter = new FunctionAdapter(functiondatas, act);
        home_function.setAdapter(functionAdapter);

    }


    private void initlisteners() {

        home_qiandao.setOnClickListener(this);
        //功能表点击事件
        functionAdapter.setOnItemListener(new FunctionAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(act, House_enterActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(act, DealActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(act, AfterServiceActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(act, DownActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(act, ToolsActivity.class));
                        break;
                    case 5:
                        Toast.makeText(act, "该功能暂未开放", Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        Toast.makeText(act, "该功能暂未开放", Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        startActivity(new Intent(act, BuyProjectActivity.class));
                        break;


                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_qiandao:
                startActivity(new Intent(act, CalendarActivity.class));
                break;
        }
    }

    private class MyHandler extends Handler {
        private HomeFragment homeFragment;
        private final WeakReference<HomeFragment> mhomefragment;

        public MyHandler(HomeFragment homeFragment) {
            mhomefragment = new WeakReference<HomeFragment>(homeFragment);
            this.homeFragment = homeFragment;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 3) {
                tempList.add(tempList.get(0));
                tempList.remove(0);
                zhanbaoadapter.notifyItemRemoved(0);
            }
        }
    }


    private void getZhanBao() {
        OkHttpUtils.okhttpGet(BaseUrl.URL + "zhanbaotexts", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String message = response.message();
                Log.e("zhanbao message:", message);
                Gson gson = new Gson();
                String string = response.body().string();
                Log.d("response-----", "onResponse: " + string);
                List<ZhanBaoBean.CommentsBean> zhanbaodatas = null;
                zhanbaodatas = gson.fromJson(string, ZhanBaoBean.class).getComments();
                Log.d("LoginActivity", "onResponse: ============zhanbaodatas==null" + (null == zhanbaodatas));
                if (null == zhanbaodatas || zhanbaodatas.size() == 0) {
                    return;
                } else {
                    Log.d("LoginActivity", "onResponse: ============zhanbaodatas.size" + zhanbaodatas.size());
                    for (int i = 0; i < 3; i++) {
                        tempList.add(zhanbaodatas.get(i).getContent());
                    }
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //战报功能
                            zhanbaoadapter = new ZhanBaoAdapter(act, tempList);
                            home_zhanbao.setAdapter(zhanbaoadapter);
                            zhanbaoadapter.notifyDataSetChanged();
                        }
                    });
                    repateThread.run();
                }
            }

        });
        if (it.getStatus() == AsyncTask.Status.PENDING)
            it.execute();
    }

    @Override
    public void onDetach() {
        repateThread.interrupt();
        super.onDetach();
    }


    ImageTask it = new ImageTask(new ImageTask.IRequest() {
        @Override
        public Object doRequest() {
            return OkHttpUtils.okhttpGet(BaseUrl.URL + "homeimgs");
        }

        @Override
        public Object doPraseResult(Object object) {
            String responseBody = (String) object;
            Log.d(TAG, "doPraseResult: ===========responseBody" + responseBody);
            List<ImageresourcesBean> imageresources = ImageresourcesBean.arrayImageresourcesBeanFromData(responseBody, "imageresources");
            return imageresources;
        }
    }, new ImageTask.IRequestCallback() {
        @Override
        public void onSuccess(Object object) {
            List<ImageresourcesBean> imageresourcesBeen = (List<ImageresourcesBean>) object;
            ArrayList<Integer> picUrls = null;
            Integer[] localPics = {R.mipmap.placeholder, R.mipmap.placeholder, R.mipmap.placeholder};
            if (null == imageresourcesBeen || imageresourcesBeen.size() == 0) {
                picUrls = new ArrayList<>(Arrays.asList(localPics));
                banner.setImages(picUrls)
                        .setImageLoader(new NomalGlideImageLoader())
                        .start();
                return;
            }
            ArrayList<String> picPaths = new ArrayList<>();
            for (int i = 0; i < imageresourcesBeen.size(); i++) {
                String picurl = imageresourcesBeen.get(i).getUrl();
                String url = BaseUrl.IMGURL + picurl.substring(0, picurl.indexOf("?"));
                Log.d(TAG, "showDetailImage: url = " + url);
                picPaths.add(url);
            }
            banner.setImages(picPaths)
                    .setImageLoader(new NomalGlideImageLoader())
                    .start();
        }

        @Override
        public void onError() {

        }
    });
}
