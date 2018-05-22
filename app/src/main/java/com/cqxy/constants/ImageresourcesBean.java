package com.cqxy.constants;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wx on 2017/10/31.
 */

public  class ImageresourcesBean implements Parcelable{
    /**
     * filename : 54c7ffde81727699.jpg
     * url : /system/imageresources/assets/000/001/659/original/54c7ffde81727699.jpg?1509430578
     * name : ABC
     * filetype : image/jpeg
     */

    private String filename;
    private String url;
    private String name;
    private String filetype;

    protected ImageresourcesBean(Parcel in) {
        filename = in.readString();
        url = in.readString();
        name = in.readString();
        filetype = in.readString();
    }

    public static final Creator<ImageresourcesBean> CREATOR = new Creator<ImageresourcesBean>() {
        @Override
        public ImageresourcesBean createFromParcel(Parcel in) {
            return new ImageresourcesBean(in);
        }

        @Override
        public ImageresourcesBean[] newArray(int size) {
            return new ImageresourcesBean[size];
        }
    };

    public static ImageresourcesBean objectFromData(String str) {

        return new Gson().fromJson(str, ImageresourcesBean.class);
    }

    public static ImageresourcesBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), ImageresourcesBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<ImageresourcesBean> arrayImageresourcesBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<ImageresourcesBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<ImageresourcesBean> arrayImageresourcesBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<ImageresourcesBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(key), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(filename);
        dest.writeString(url);
        dest.writeString(name);
        dest.writeString(filetype);
    }

    @Override
    public String toString() {
        return "ImageresourcesBean{" +
                "filename='" + filename + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", filetype='" + filetype + '\'' +
                '}';
    }
}
