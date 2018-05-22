package com.cqxy.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lpl on 17-10-11.
 */

public class Users implements Parcelable {

    /**
     * id : 6
     * phone_num : 18612247659
     * user_name : 张浩
     * user_amount : 0
     * created_at : 2017-10-30T03:44:45.000Z
     * updated_at : 2017-11-01T06:34:31.000Z
     * qianming : 哈哈哈
     * shangquan : 哈哈哈
     * gender : 哈哈哈
     * asset : /system/users/assets/000/000/006/original/1532985349.jpg?1509518071
     * idfront : /system/users/assets/000/000/006/original/1532985349.jpg?1509518071
     * idback : /system/users/assets/000/000/006/original/1532985349.jpg?1509518071
     * recommend : 15111237315
     * recomm_name:许可
     * idcheck:许可
     */

    private int id;
    private String phone_num;
    private String user_name;
    private int user_amount;
    private String created_at;
    private String updated_at;
    private String qianming;
    private String shangquan;
    private String gender;
    private String asset;
    private String idfront;
    private String idback;
    private String recommend;
    private String recomm_name;
    private String idcheck;

    protected Users(Parcel in) {
        id = in.readInt();
        phone_num = in.readString();
        user_name = in.readString();
        user_amount = in.readInt();
        created_at = in.readString();
        updated_at = in.readString();
        qianming = in.readString();
        shangquan = in.readString();
        gender = in.readString();
        asset = in.readString();
        idfront = in.readString();
        idback = in.readString();
        recommend = in.readString();
        recomm_name = in.readString();
        idcheck = in.readString();
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

    public static Users objectFromData(String str) {

        return new Gson().fromJson(str, Users.class);
    }

    public static Users objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), Users.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Users> arrayUsersFromData(String str) {

        Type listType = new TypeToken<ArrayList<Users>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<Users> arrayUsersFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<Users>>() {
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

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUser_amount() {
        return user_amount;
    }

    public void setUser_amount(int user_amount) {
        this.user_amount = user_amount;
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

    public String getQianming() {
        return qianming;
    }

    public void setQianming(String qianming) {
        this.qianming = qianming;
    }

    public String getShangquan() {
        return shangquan;
    }

    public void setShangquan(String shangquan) {
        this.shangquan = shangquan;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getIdfront() {
        return idfront;
    }

    public void setIdfront(String idfront) {
        this.idfront = idfront;
    }

    public String getIdback() {
        return idback;
    }

    public void setIdback(String idback) {
        this.idback = idback;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getRecomm_name() {
        return recomm_name;
    }

    public void setRecomm_name(String recomm_name) {
        this.recomm_name = recomm_name;
    }

    public String getIdcheck() {
        if(idcheck.equals("")){
            return null;
        }
        Integer checkid = Integer.valueOf(idcheck);
        String str  = "";
        switch (checkid) {
            case 0://未上传
                str = "未上传";
                break;
            case 1://未认证
                str = "未认证";
                break;
            case 2://认证成功
                str = "认证成功";
                break;
            case 3://认证失败
                str = "认证失败";
                break;
        }
        Log.d("user", "getIdcheck: -------------str = " +str);
        return str;
    }

    public void setIdcheck(String idcheck) {
        this.idcheck = idcheck;

    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", phone_num='" + phone_num + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_amount=" + user_amount +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", qianming='" + qianming + '\'' +
                ", shangquan='" + shangquan + '\'' +
                ", gender='" + gender + '\'' +
                ", asset='" + asset + '\'' +
                ", idfront='" + idfront + '\'' +
                ", idback='" + idback + '\'' +
                ", recommend='" + recommend + '\'' +
                ", recomm_name='" + recomm_name + '\'' +
                ", idcheck='" + idcheck + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(phone_num);
        dest.writeString(user_name);
        dest.writeInt(user_amount);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeString(qianming);
        dest.writeString(shangquan);
        dest.writeString(gender);
        dest.writeString(asset);
        dest.writeString(idfront);
        dest.writeString(idback);
        dest.writeString(recommend);
        dest.writeString(recomm_name);
        dest.writeString(idcheck);
    }
}
