package com.cqxy.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wx on 2017/12/28.
 */

public class detailBean {

    /**
     * amount : 0.01
     * details : 1
     * time : 2017-12-27T10:04:10.000Z
     */

    private double amount;
    private String details;
    private String time;

    public static detailBean objectFromData(String str) {

        return new Gson().fromJson(str, detailBean.class);
    }

    public static detailBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), detailBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<detailBean> arraydetailBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<detailBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<detailBean> arraydetailBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<detailBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(key), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
