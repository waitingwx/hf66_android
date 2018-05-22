package com.cqxy.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wx on 2017/11/24.
 */

public class EaseTokenBean {

    /**
     * access_token : YWMtuICYJtDQEee7a-klYB_pEQAAAAAAAAAAAAAAAAAAAAFQsy5gvSER55RMNy2zYn3EAgMAAAFf7E3zsQBPGgDdVeGZWAOGTt2w_eBuoJcHbocJF4eqxUYs8xGwrhAd6g
     * expires_in : 5170493
     * application : 50b32e60-bd21-11e7-944c-372db3627dc4
     */

    private String access_token;
    private int expires_in;
    private String application;

    public static EaseTokenBean objectFromData(String str) {

        return new Gson().fromJson(str, EaseTokenBean.class);
    }

    public static EaseTokenBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), EaseTokenBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<EaseTokenBean> arrayEaseTokenBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<EaseTokenBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<EaseTokenBean> arrayEaseTokenBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<EaseTokenBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(key), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }
}
