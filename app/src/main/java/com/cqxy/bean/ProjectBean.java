package com.cqxy.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wx on 2017/12/10.
 */

public class ProjectBean {

    /**
     * detailedlist : {"id":9,"transaction_amount":25,"details":"微信","user_id":7,"created_at":"2017-12-10T04:55:53.000Z","updated_at":"2017-12-10T04:55:53.000Z","wxresponse":{"appid":"wx169ac11d00fd5940","partnerid":"1492845742","package":"Sign=WXPay","timestamp":"1512881753","prepayid":"1101000000140415649af9fc314aa427","noncestr":"1101000000140429eb40476f8896f4c9","sign":"206290D6039B69075067A8D2D0B2D101"}}
     */

    private DetailedlistBean detailedlist;

    public static ProjectBean objectFromData(String str) {

        return new Gson().fromJson(str, ProjectBean.class);
    }

    public static ProjectBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), ProjectBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<ProjectBean> arrayprojectBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<ProjectBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<ProjectBean> arrayprojectBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<ProjectBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(key), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public DetailedlistBean getDetailedlist() {
        return detailedlist;
    }

    public void setDetailedlist(DetailedlistBean detailedlist) {
        this.detailedlist = detailedlist;
    }

    public static class DetailedlistBean {
        /**
         * id : 9
         * transaction_amount : 25.0
         * details : 微信
         * user_id : 7
         * created_at : 2017-12-10T04:55:53.000Z
         * updated_at : 2017-12-10T04:55:53.000Z
         * wxresponse : {"appid":"wx169ac11d00fd5940","partnerid":"1492845742","package":"Sign=WXPay","timestamp":"1512881753","prepayid":"1101000000140415649af9fc314aa427","noncestr":"1101000000140429eb40476f8896f4c9","sign":"206290D6039B69075067A8D2D0B2D101"}
         */

        private int id;
        private double transaction_amount;
        private String details;
        private int user_id;
        private String created_at;
        private String updated_at;
//        private String wxresponse;
        private WxresponseBean wxresponse;

        public static DetailedlistBean objectFromData(String str) {

            return new Gson().fromJson(str, DetailedlistBean.class);
        }

        public static DetailedlistBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(key), DetailedlistBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<DetailedlistBean> arrayDetailedlistBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<DetailedlistBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<DetailedlistBean> arrayDetailedlistBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<DetailedlistBean>>() {
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

        public double getTransaction_amount() {
            return transaction_amount;
        }

        public void setTransaction_amount(double transaction_amount) {
            this.transaction_amount = transaction_amount;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
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

        public WxresponseBean getWxresponse() {
            return wxresponse;
        }

        public void setWxresponse(WxresponseBean wxresponse) {
            this.wxresponse = wxresponse;
        }
//        public String getWxresponse() {
//            return wxresponse;
//        }
//
//        public void setWxresponse(String wxresponse) {
//            this.wxresponse = wxresponse;
//        }

        public static class WxresponseBean {
            /**
             * appid : wx169ac11d00fd5940
             * partnerid : 1492845742
             * package : Sign=WXPay
             * timestamp : 1512881753
             * prepayid : 1101000000140415649af9fc314aa427
             * noncestr : 1101000000140429eb40476f8896f4c9
             * sign : 206290D6039B69075067A8D2D0B2D101
             */

            private String appid;
            private String partnerid;
            @SerializedName("package")
            private String packageX;
            private String timestamp;
            private String prepayid;
            private String noncestr;
            private String sign;

            public static WxresponseBean objectFromData(String str) {

                return new Gson().fromJson(str, WxresponseBean.class);
            }

            public static WxresponseBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(key), WxresponseBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<WxresponseBean> arrayWxresponseBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<WxresponseBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<WxresponseBean> arrayWxresponseBeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<WxresponseBean>>() {
                    }.getType();

                    return new Gson().fromJson(jsonObject.getString(key), listType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new ArrayList();


            }

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            @Override
            public String toString() {
                return "{" +
                        "\"appid\":\"" + appid + "\"" +
                        ",\"partnerid\":\"" + partnerid + "\"" +
                        ",\"package\":\"" + packageX + "\"" +
                        ",\"timestamp\":\"" + timestamp + "\"" +
                        ",\"prepayid\":\"" + prepayid + "\"" +
                        ",\"noncestr\":\"" + noncestr + "\"" +
                        ",\"sign\":\"" + sign + "\"" +
                        '}';
            }
        }
    }
}
