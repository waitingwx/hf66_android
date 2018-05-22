package com.cqxy.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wx on 2017/12/11.
 */

public class PayorderBean {

    /**
     * money : 1
     * quarter : 1
     */

    private int money;
    private int quarter;

    public static PayorderBean objectFromData(String str) {

        return new Gson().fromJson(str, PayorderBean.class);
    }

    public static PayorderBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), PayorderBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<PayorderBean> arrayPayorderBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<PayorderBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<PayorderBean> arrayPayorderBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<PayorderBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(key), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }
}
