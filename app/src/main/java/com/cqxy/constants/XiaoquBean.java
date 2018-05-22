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
 * Created by wx on 2017/11/14.
 */

public class XiaoquBean {

    private List<XiaoqusBean> xiaoqus;

    public static XiaoquBean objectFromData(String str) {

        return new Gson().fromJson(str, XiaoquBean.class);
    }

    public static XiaoquBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), XiaoquBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<XiaoquBean> arrayXiaoquBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<XiaoquBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<XiaoquBean> arrayXiaoquBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<XiaoquBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(key), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public List<XiaoqusBean> getXiaoqus() {
        return xiaoqus;
    }

    public void setXiaoqus(List<XiaoqusBean> xiaoqus) {
        this.xiaoqus = xiaoqus;
    }

    public static class XiaoqusBean implements Parcelable{
        /**
         * id : 2
         * name : 北苑家园
         * location : 宝那
         * quxian_id : 1
         * shangquan_id : 2
         */

        private int id;
        private String name;
        private String location;
        private int quxian_id;
        private int shangquan_id;

        public XiaoqusBean(Parcel in) {
            id = in.readInt();
            name = in.readString();
            location = in.readString();
            quxian_id = in.readInt();
            shangquan_id = in.readInt();
        }

        public static final Creator<XiaoqusBean> CREATOR = new Creator<XiaoqusBean>() {
            @Override
            public XiaoqusBean createFromParcel(Parcel in) {
                return new XiaoqusBean(in);
            }

            @Override
            public XiaoqusBean[] newArray(int size) {
                return new XiaoqusBean[size];
            }
        };

        public static XiaoqusBean objectFromData(String str) {

            return new Gson().fromJson(str, XiaoqusBean.class);
        }

        public static XiaoqusBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(key), XiaoqusBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<XiaoqusBean> arrayXiaoqusBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<XiaoqusBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<XiaoqusBean> arrayXiaoqusBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<XiaoqusBean>>() {
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

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getQuxian_id() {
            return quxian_id;
        }

        public void setQuxian_id(int quxian_id) {
            this.quxian_id = quxian_id;
        }

        public int getShangquan_id() {
            return shangquan_id;
        }

        public void setShangquan_id(int shangquan_id) {
            this.shangquan_id = shangquan_id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(name);
            dest.writeString(location);
            dest.writeInt(quxian_id);
            dest.writeInt(shangquan_id);
        }
    }
}
