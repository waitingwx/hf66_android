package com.cqxy.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wx on 2017/11/23.
 */

public class IsCooperation {

    /**
     * status : -1
     */

    private int status;

    public static IsCooperation objectFromData(String str) {

        return new Gson().fromJson(str, IsCooperation.class);
    }

    public static IsCooperation objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), IsCooperation.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<IsCooperation> arrayIsCooperationFromData(String str) {

        Type listType = new TypeToken<ArrayList<IsCooperation>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<IsCooperation> arrayIsCooperationFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<IsCooperation>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(key), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
