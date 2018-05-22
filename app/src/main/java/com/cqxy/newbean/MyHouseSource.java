package com.cqxy.newbean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wx on 2017/12/25.
 */

public class MyHouseSource {

    /**
     * id : 4
     * rentornot : 0
     * sourcetype : shop_sellings
     * position : 丰台台风商铺
     * leixing : 佣金
     * bvalue : 2%
     * states : 0
     * rent : 20000
     * floor : 4
     * house_area : 200
     * user_id : 1
     * created_at : 2017-12-18T08:33:14.000Z
     * updated_at : 2017-12-25T07:38:19.000Z
     */

    private int id;
    private int rentornot;
    private String sourcetype;
    private String position;
    private String leixing;
    private String bvalue;
    private int states;
    private int rent;
    private int floor;
    private int house_area;
    private int user_id;
    private String created_at;
    private String updated_at;

    public static MyHouseSource objectFromData(String str) {

        return new Gson().fromJson(str, MyHouseSource.class);
    }

    public static MyHouseSource objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), MyHouseSource.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<MyHouseSource> arrayMyHouseSourceFromData(String str) {

        Type listType = new TypeToken<ArrayList<MyHouseSource>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<MyHouseSource> arrayMyHouseSourceFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<MyHouseSource>>() {
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

    public int getRentornot() {
        return rentornot;
    }

    public void setRentornot(int rentornot) {
        this.rentornot = rentornot;
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

    public String getLeixing() {
        return leixing;
    }

    public void setLeixing(String leixing) {
        this.leixing = leixing;
    }

    public String getBvalue() {
        return bvalue;
    }

    public void setBvalue(String bvalue) {
        this.bvalue = bvalue;
    }

    public int getStates() {
        return states;
    }

    public void setStates(int states) {
        this.states = states;
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
