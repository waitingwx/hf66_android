package com.cqxy.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wx on 2017/10/20.
 */

public class PolicyBean {
public static String COMMENTS = "comments";
    private List<CommentsBean> comments;

    public static PolicyBean objectFromData(String str) {

        return new Gson().fromJson(str, PolicyBean.class);
    }

    public static PolicyBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), PolicyBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<PolicyBean> arrayPolicyBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<PolicyBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<PolicyBean> arrayPolicyBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<PolicyBean>>() {
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
         * id : 1
         * pname : 十九大重要讲话
         * pcontent : 全员放假，坐等收钱。
         * created_at : 2017-10-20T08:33:30.604Z
         * updated_at : 2017-10-20T08:33:30.604Z
         */

        private int id;
        private String pname;
        private String pcontent;
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

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public String getPcontent() {
            return pcontent;
        }

        public void setPcontent(String pcontent) {
            this.pcontent = pcontent;
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
