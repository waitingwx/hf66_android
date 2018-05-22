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

public class EaseUserBean {

    /**
     * action : get
     * path : /users
     * uri : http://a1.easemob.com/1160171030178595/hf66/users/18612247659
     * entities : [{"uuid":"ab9bb470-bd24-11e7-9f52-0980719cc343","type":"user","created":1509335085367,"modified":1509505082977,"username":"18612247659","activated":true}]
     * timestamp : 1511518725683
     * duration : 4
     * count : 1
     */
    private String action;
    private String path;
    private String uri;
    private long timestamp;
    private int duration;
    private int count;
    private List<EntitiesBean> entities;

    public static EaseUserBean objectFromData(String str) {

        return new Gson().fromJson(str, EaseUserBean.class);
    }

    public static EaseUserBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), EaseUserBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<EaseUserBean> arrayEaseUserBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<EaseUserBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<EaseUserBean> arrayEaseUserBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<EaseUserBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(key), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<EntitiesBean> getEntities() {
        return entities;
    }

    public void setEntities(List<EntitiesBean> entities) {
        this.entities = entities;
    }

    public static class EntitiesBean {
        /**
         * uuid : ab9bb470-bd24-11e7-9f52-0980719cc343
         * type : user
         * created : 1509335085367
         * modified : 1509505082977
         * username : 18612247659
         * activated : true
         */

        private String uuid;
        private String type;
        private long created;
        private long modified;
        private String username;
        private boolean activated;

        public static EntitiesBean objectFromData(String str) {

            return new Gson().fromJson(str, EntitiesBean.class);
        }

        public static EntitiesBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(key), EntitiesBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<EntitiesBean> arrayEntitiesBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<EntitiesBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<EntitiesBean> arrayEntitiesBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<EntitiesBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(key), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getCreated() {
            return created;
        }

        public void setCreated(long created) {
            this.created = created;
        }

        public long getModified() {
            return modified;
        }

        public void setModified(long modified) {
            this.modified = modified;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public boolean isActivated() {
            return activated;
        }

        public void setActivated(boolean activated) {
            this.activated = activated;
        }
    }

    @Override
    public String toString() {
        return "EaseUserBean{" +
                "action='" + action + '\'' +
                ", path='" + path + '\'' +
                ", uri='" + uri + '\'' +
                ", timestamp=" + timestamp +
                ", duration=" + duration +
                ", count=" + count +
                ", entities=" + entities +
                '}';
    }
}
