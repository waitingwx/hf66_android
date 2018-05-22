package com.cqxy.bean;

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
 * Created by wx on 2017/11/19.
 */

public  class SellVillaHouseBean implements Parcelable {
    /**
     * leixing : 佣金
     * bvalue : 2%
     * sourcetype : villa_sellings
     * id : 3
     * position : 别墅买卖1
     * rentornot : 0
     * house_price : 28555900
     * house_price_type : null
     * house_area : 8855
     * aspect :
     * house_area_type : null
     * house_type : 0室0厅0卫0厨0阳
     * floor : 10
     * floor_type : null
     * decoration_level : 毛坯
     * built_type : 独栋
     * supporting_facility : 有线电视,煤气/天然气,暖气,露台/花园,微波炉,微波炉
     * adding_proxy :
     * house_example_id : 28
     * note :
     * built_age : 2010
     * created_at : 2017-10-27T08:36:32.000Z
     * updated_at : 2017-10-27T08:36:32.000Z
     */

    private String leixing;
    private String bvalue;
    private String sourcetype;
    private int id;
    private String position;
    private int rentornot;
    private int house_price;
    private int house_area;
    private String aspect;
    private String house_type;
    private int floor;
    private String decoration_level;
    private String built_type;
    private String supporting_facility;
    private String adding_proxy;
    private int house_example_id;
    private String note;
    private int shangquan_id;
    private String detialedaddress;
    private int built_age;
    private String created_at;
    private String updated_at;

    protected SellVillaHouseBean(Parcel in) {
        leixing = in.readString();
        bvalue = in.readString();
        sourcetype = in.readString();
        id = in.readInt();
        position = in.readString();
        rentornot = in.readInt();
        house_price = in.readInt();
        house_area = in.readInt();
        aspect = in.readString();
        house_type = in.readString();
        floor = in.readInt();
        decoration_level = in.readString();
        built_type = in.readString();
        supporting_facility = in.readString();
        adding_proxy = in.readString();
        house_example_id = in.readInt();
        note = in.readString();
        shangquan_id = in.readInt();
        detialedaddress = in.readString();
        built_age = in.readInt();
        created_at = in.readString();
        updated_at = in.readString();
    }

    public static final Creator<SellVillaHouseBean> CREATOR = new Creator<SellVillaHouseBean>() {
        @Override
        public SellVillaHouseBean createFromParcel(Parcel in) {
            return new SellVillaHouseBean(in);
        }

        @Override
        public SellVillaHouseBean[] newArray(int size) {
            return new SellVillaHouseBean[size];
        }
    };

    public static SellVillaHouseBean objectFromData(String str) {

        return new Gson().fromJson(str, SellVillaHouseBean.class);
    }

    public static SellVillaHouseBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), SellVillaHouseBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<SellVillaHouseBean> arraySellVillaHouseBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<SellVillaHouseBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<SellVillaHouseBean> arraySellVillaHouseBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<SellVillaHouseBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(key), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


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

    public String getSourcetype() {
        return sourcetype;
    }

    public void setSourcetype(String sourcetype) {
        this.sourcetype = sourcetype;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getRentornot() {
        return rentornot;
    }

    public void setRentornot(int rentornot) {
        this.rentornot = rentornot;
    }

    public int getHouse_price() {
        return house_price;
    }

    public void setHouse_price(int house_price) {
        this.house_price = house_price;
    }

    public int getHouse_area() {
        return house_area;
    }

    public void setHouse_area(int house_area) {
        this.house_area = house_area;
    }

    public String getAspect() {
        return aspect;
    }

    public void setAspect(String aspect) {
        this.aspect = aspect;
    }

    public String getHouse_type() {
        return house_type;
    }

    public void setHouse_type(String house_type) {
        this.house_type = house_type;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getDecoration_level() {
        return decoration_level;
    }

    public void setDecoration_level(String decoration_level) {
        this.decoration_level = decoration_level;
    }

    public String getBuilt_type() {
        return built_type;
    }

    public void setBuilt_type(String built_type) {
        this.built_type = built_type;
    }

    public String getSupporting_facility() {
        return supporting_facility;
    }

    public void setSupporting_facility(String supporting_facility) {
        this.supporting_facility = supporting_facility;
    }

    public String getAdding_proxy() {
        return adding_proxy;
    }

    public void setAdding_proxy(String adding_proxy) {
        this.adding_proxy = adding_proxy;
    }

    public int getHouse_example_id() {
        return house_example_id;
    }

    public void setHouse_example_id(int house_example_id) {
        this.house_example_id = house_example_id;
    }
    public int getShangquan_id() {
        return shangquan_id;
    }

    public void setShangquan_id(int shangquan_id) {
        this.shangquan_id = shangquan_id;
    }

    public String getDetialedaddress() {
        return detialedaddress;
    }

    public void setDetialedaddress(String detialedaddress) {
        this.detialedaddress = detialedaddress;
    }
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getBuilt_age() {
        return built_age;
    }

    public void setBuilt_age(int built_age) {
        this.built_age = built_age;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(leixing);
        dest.writeString(bvalue);
        dest.writeString(sourcetype);
        dest.writeInt(id);
        dest.writeString(position);
        dest.writeInt(rentornot);
        dest.writeInt(house_price);
        dest.writeInt(house_area);
        dest.writeString(aspect);
        dest.writeString(house_type);
        dest.writeInt(floor);
        dest.writeString(decoration_level);
        dest.writeString(built_type);
        dest.writeString(supporting_facility);
        dest.writeString(adding_proxy);
        dest.writeInt(house_example_id);
        dest.writeString(note);
        dest.writeInt(shangquan_id);
        dest.writeString(detialedaddress);
        dest.writeInt(built_age);
        dest.writeString(created_at);
        dest.writeString(updated_at);
    }

    @Override
    public String toString() {
        return "SellVillaHouseBean{" +
                "leixing='" + leixing + '\'' +
                ", bvalue='" + bvalue + '\'' +
                ", sourcetype='" + sourcetype + '\'' +
                ", id=" + id +
                ", position='" + position + '\'' +
                ", rentornot=" + rentornot +
                ", house_price=" + house_price +
                ", house_area=" + house_area +
                ", aspect='" + aspect + '\'' +
                ", house_type='" + house_type + '\'' +
                ", floor=" + floor +
                ", decoration_level='" + decoration_level + '\'' +
                ", built_type='" + built_type + '\'' +
                ", supporting_facility='" + supporting_facility + '\'' +
                ", adding_proxy='" + adding_proxy + '\'' +
                ", house_example_id=" + house_example_id +
                ", note='" + note + '\'' +
                ", shangquan_id='" + shangquan_id + '\'' +
                ", detialedaddress='" + detialedaddress + '\'' +
                ", built_age=" + built_age +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
