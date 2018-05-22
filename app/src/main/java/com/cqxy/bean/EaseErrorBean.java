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

public class EaseErrorBean {

    /**
     * error : entity_not_found
     * timestamp : 1511516517407
     * duration : 0
     * exception : org.apache.usergrid.persistence.exceptions.EntityNotFoundException
     * error_description : User null not found
     */

    private String error;
    private long timestamp;
    private int duration;
    private String exception;
    private String error_description;

    public static EaseErrorBean objectFromData(String str) {

        return new Gson().fromJson(str, EaseErrorBean.class);
    }

    public static EaseErrorBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), EaseErrorBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<EaseErrorBean> arrayEaseErrorBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<EaseErrorBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<EaseErrorBean> arrayEaseErrorBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<EaseErrorBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(key), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    @Override
    public String toString() {
        return "EaseErrorBean{" +
                "error='" + error + '\'' +
                ", timestamp=" + timestamp +
                ", duration=" + duration +
                ", exception='" + exception + '\'' +
                ", error_description='" + error_description + '\'' +
                '}';
    }
}
