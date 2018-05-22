package com.cqxy.bean;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wx on 2018/1/3.
 */

public class DateBean {

    /**
     * id : 1
     * time : 2017-12-27T10:04:10.000Z
     */

    private int id;
    private String time;

    public static DateBean objectFromData(String str) {

        return new Gson().fromJson(str, DateBean.class);
    }

    public static DateBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), DateBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<DateBean> arrayDateBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<DateBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<DateBean> arrayDateBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<DateBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(key), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
//        2017-12-27T10:04:10.000Z
        if (time.indexOf("T") == -1)
            return null;
        else {
            String subTime = time.substring(0, time.indexOf("T"));
            Log.d("DateBean", "getTime: ------------subTime = " + subTime);
            return subTime;
        }
    }

    public void setTime(String time) {
        this.time = time;
    }
}
