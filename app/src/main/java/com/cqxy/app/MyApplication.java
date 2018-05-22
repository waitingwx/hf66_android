package com.cqxy.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.cqxy.base.BaseUrl;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.SpUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.mob.MobSDK;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * Created by Administrator on 2017/8/31.
 */

public class MyApplication extends Application {
    private static MyApplication instance;

    protected String a() {
        return null;
    }

    protected String b() {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MobSDK.init(this, this.a(), this.b());

        //环信
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        options.setAutoLogin(true);
        EaseUI.getInstance().init(this, options);
        //初始化
        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
        OkHttpUtils.okhttpGet(BaseUrl.URL + "shangquans", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d("City_shangquan", "onResponse: =========string = " + string);
                SpUtil.putString(MyApplication.this, "shangquan", "responsebody", string);
            }
        });
    }


    public MyApplication() {
    }

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(this);
        super.attachBaseContext(base);
    }


}
