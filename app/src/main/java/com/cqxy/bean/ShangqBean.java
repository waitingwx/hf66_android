package com.cqxy.bean;

import com.cqxy.constants.QuxianBean;
import com.cqxy.constants.ShangquanBean;
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

public class ShangqBean {

    private List<CityBean> city;

    public static ShangqBean objectFromData(String str) {

        return new Gson().fromJson(str, ShangqBean.class);
    }

    public static ShangqBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), ShangqBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<ShangqBean> arrayShangquanBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<ShangqBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<ShangqBean> arrayShangquanBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<ShangqBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(key), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }

    public static class CityBean {
        /**
         * city_id : 1
         * quxian : {"id":1,"name":"朝阳","city_id":1}
         * shangquan : [{"id":1,"name":"阿斯顿萨达","pinyin":"","quxian_id":1},{"id":2,"name":"国贸","pinyin":"guomao","quxian_id":1},{"id":3,"name":"亚运村","pinyin":"yayuncun","quxian_id":1}]
         */

        private int city_id;
        private QuxianBean quxian;
        private List<ShangquanBean> shangquan;

        public static CityBean objectFromData(String str) {

            return new Gson().fromJson(str, CityBean.class);
        }

        public static CityBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(key), CityBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<CityBean> arrayCityBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<CityBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<CityBean> arrayCityBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<CityBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(key), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public int getCity_id() {
            return city_id;
        }

        public void setCity_id(int city_id) {
            this.city_id = city_id;
        }

        public QuxianBean getQuxian() {
            return quxian;
        }

        public void setQuxian(QuxianBean quxian) {
            this.quxian = quxian;
        }

        public List<ShangquanBean> getShangquan() {
            return shangquan;
        }

        public void setShangquan(List<ShangquanBean> shangquan) {
            this.shangquan = shangquan;
        }




    }
}
