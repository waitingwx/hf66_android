package com.cqxy.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lpl on 17-10-23.
 */

public class CommentBean {

    private List<CommentsBean> comments;

    public static CommentBean objectFromData(String str) {

        return new Gson().fromJson(str, CommentBean.class);
    }

    public static CommentBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), CommentBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<CommentBean> arrayCommentBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<CommentBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<CommentBean> arrayCommentBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<CommentBean>>() {
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
         * user_id : 2
         * body : ajgoajeago
         * commentable_id : 3
         * created_at : 2017-10-23T09:15:54.373Z
         * updated_at : 2017-10-23T09:15:54.373Z
         */

        private int id;
        private int user_id;
        private String body;
        private int commentable_id;
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

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public int getCommentable_id() {
            return commentable_id;
        }

        public void setCommentable_id(int commentable_id) {
            this.commentable_id = commentable_id;
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
