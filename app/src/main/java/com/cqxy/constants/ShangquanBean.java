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

public  class ShangquanBean {
    /**
     * id : 1
     * name : 阿斯顿萨达
     * pinyin :
     * quxian_id : 1
     */

    private int id;
    private String name;
    private String pinyin;
    private int quxian_id;

    public static ShangquanBean objectFromData(String str) {

        return new Gson().fromJson(str, ShangquanBean.class);
    }

    public static ShangquanBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), ShangquanBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<ShangquanBean> arrayShangquanBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<ShangquanBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<ShangquanBean> arrayShangquanBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<ShangquanBean>>() {
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

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public int getQuxian_id() {
        return quxian_id;
    }

    public void setQuxian_id(int quxian_id) {
        this.quxian_id = quxian_id;
    }
}
