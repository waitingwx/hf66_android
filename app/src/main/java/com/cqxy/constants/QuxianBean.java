package com.cqxy.constants;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wx on 2017/11/13.
 */

public class QuxianBean {
    /**
     * id : 1
     * name : 朝阳
     * city_id : 1
     */

    private int id;
    private String name;
    private int city_id;

    public static QuxianBean objectFromData(String str) {

        return new Gson().fromJson(str, QuxianBean.class);
    }

    public static QuxianBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), QuxianBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<QuxianBean> arrayQuxianBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<QuxianBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<QuxianBean> arrayQuxianBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<QuxianBean>>() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }
}
