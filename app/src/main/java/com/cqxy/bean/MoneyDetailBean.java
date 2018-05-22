package com.cqxy.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wx on 2017/10/19.
 */

public class MoneyDetailBean {

    /**
     * recommendname : wangxu
     * amount : 16
     * orderno : 15
     * time : 2017-12-27T10:04:10.000Z
     */

    private String recommendname;
    private double amount;
    private int orderno;
    private String time;
    private String details;
    private String uname;

    public static MoneyDetailBean objectFromData(String str) {

        return new Gson().fromJson(str, MoneyDetailBean.class);
    }

    public static MoneyDetailBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), MoneyDetailBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<MoneyDetailBean> arrayMoneyDetailBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<MoneyDetailBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<MoneyDetailBean> arrayMoneyDetailBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<MoneyDetailBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(key), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getRecommendname() {
        return recommendname;
    }

    public void setRecommendname(String recommendname) {
        this.recommendname = recommendname;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getOrderno() {
        return orderno;
    }

    public void setOrderno(int orderno) {
        this.orderno = orderno;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
