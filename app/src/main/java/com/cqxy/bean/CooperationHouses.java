package com.cqxy.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lpl on 17-9-29.
 */

public class CooperationHouses {

    private List<CommentsBean> comments;

    public static CooperationHouses objectFromData(String str) {

        return new Gson().fromJson(str, CooperationHouses.class);
    }

    public static CooperationHouses objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), CooperationHouses.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<CooperationHouses> arrayHouseExampleBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<CooperationHouses>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<CooperationHouses> arrayHouseExampleBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<CooperationHouses>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(key), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class CommentsBean {
        /**
         * id : 2
         * sourcetype : residence_sellings
         * position : 望京小区
         * rent : 20000
         * floor : 1
         * house_area : 200
         * user_id : 15
         * created_at : 2017-10-18T08:30:41.008Z
         * updated_at : 2017-10-18T08:30:41.008Z
         */

        private int id;
        private String sourcetype;
        private String position;
        private int rent;
        private int floor;
        private int house_area;
        private int user_id;
        private String created_at;
        private String updated_at;

        public static CommentsBean objectFromData(String str) {

            return new Gson().fromJson(str, CommentsBean.class);
        }

        public static CommentsBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(key), CommentsBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<CommentsBean> arrayCommentsBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<CommentsBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<CommentsBean> arrayCommentsBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<CommentsBean>>() {
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

        public String getSourcetype() {
            return sourcetype;
        }

        public void setSourcetype(String sourcetype) {
            this.sourcetype = sourcetype;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public int getRent() {
            return rent;
        }

        public void setRent(int rent) {
            this.rent = rent;
        }

        public int getFloor() {
            return floor;
        }

        public void setFloor(int floor) {
            this.floor = floor;
        }

        public int getHouse_area() {
            return house_area;
        }

        public void setHouse_area(int house_area) {
            this.house_area = house_area;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}
