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

public  class SellResidentHouseBean implements Parcelable {

    protected SellResidentHouseBean(Parcel in) {
        id = in.readInt();
        leixing = in.readString();
        bvalue = in.readString();
        position = in.readString();
        sourcetype = in.readString();
        house_price = in.readInt();
        rentornot = in.readInt();
        house_area = in.readInt();
        house_type = in.readString();
        floor = in.readInt();
        aspect = in.readString();
        decoration_level = in.readString();
        built_type = in.readString();
        built_structure = in.readString();
        built_age = in.readInt();
        nature_of_property_right = in.readString();
        supporting_facility = in.readString();
        adding_proxy = in.readString();
        house_example_id = in.readInt();
        note = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
    }

    public static final Creator<SellResidentHouseBean> CREATOR = new Creator<SellResidentHouseBean>() {
        @Override
        public SellResidentHouseBean createFromParcel(Parcel in) {
            return new SellResidentHouseBean(in);
        }

        @Override
        public SellResidentHouseBean[] newArray(int size) {
            return new SellResidentHouseBean[size];
        }
    };

    /**
     * id : 10
     * leixing :
     * bvalue :
     * position : 住宅录入
     * sourcetype : residence_sellings
     * house_price : 1231620
     * rentornot : 0
     * house_area : 54545
     * house_type : 0室0厅0卫0厨0阳
     * floor : 20
     * aspect : 东南东南
     * decoration_level : 毛坯
     * built_type : 普通住宅
     * built_structure : 平层
     * built_age : 2010
     * nature_of_property_right : 商品房
     * supporting_facility :
     * adding_proxy :
     * house_example_id : 39
     * note : 无
     * created_at : 2017-10-31T06:14:19.000Z
     * updated_at : 2017-10-31T06:14:19.000Z
     */

    private int id;
    private String leixing;
    private String bvalue;
    private String position;
    private String sourcetype;
    private int house_price;
    private int rentornot;
    private int house_area;
    private String house_type;
    private int floor;
    private String aspect;
    private String decoration_level;
    private String built_type;
    private String built_structure;
    private int built_age;
    private String nature_of_property_right;
    private String supporting_facility;
    private String adding_proxy;
    private int house_example_id;
    private String note;
    private int xiaoqu_id;
    private String detialedaddress;
    private String created_at;
    private String updated_at;

    public static SellResidentHouseBean objectFromData(String str) {

        return new Gson().fromJson(str, SellResidentHouseBean.class);
    }

    public static SellResidentHouseBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), SellResidentHouseBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<SellResidentHouseBean> arraySellResidentHouseBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<SellResidentHouseBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<SellResidentHouseBean> arraySellResidentHouseBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<SellResidentHouseBean>>() {
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSourcetype() {
        return sourcetype;
    }

    public void setSourcetype(String sourcetype) {
        this.sourcetype = sourcetype;
    }

    public int getHouse_price() {
        return house_price;
    }

    public void setHouse_price(int house_price) {
        this.house_price = house_price;
    }

    public int getRentornot() {
        return rentornot;
    }

    public void setRentornot(int rentornot) {
        this.rentornot = rentornot;
    }

    public int getHouse_area() {
        return house_area;
    }

    public void setHouse_area(int house_area) {
        this.house_area = house_area;
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

    public String getAspect() {
        return aspect;
    }

    public void setAspect(String aspect) {
        this.aspect = aspect;
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

    public String getBuilt_structure() {
        return built_structure;
    }

    public void setBuilt_structure(String built_structure) {
        this.built_structure = built_structure;
    }

    public int getBuilt_age() {
        return built_age;
    }

    public void setBuilt_age(int built_age) {
        this.built_age = built_age;
    }

    public String getNature_of_property_right() {
        return nature_of_property_right;
    }

    public void setNature_of_property_right(String nature_of_property_right) {
        this.nature_of_property_right = nature_of_property_right;
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

    public int getXiaoqu_id() {
        return xiaoqu_id;
    }

    public void setXiaoqu_id(int xiaoqu_id) {
        this.xiaoqu_id = xiaoqu_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDetialedaddress() {
        return detialedaddress;
    }

    public void setDetialedaddress(String detialedaddress) {
        this.detialedaddress = detialedaddress;
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
        dest.writeInt(id);
        dest.writeString(leixing);
        dest.writeString(bvalue);
        dest.writeString(position);
        dest.writeString(sourcetype);
        dest.writeInt(house_price);
        dest.writeInt(rentornot);
        dest.writeInt(house_area);
        dest.writeString(house_type);
        dest.writeInt(floor);
        dest.writeString(aspect);
        dest.writeString(decoration_level);
        dest.writeString(built_type);
        dest.writeString(built_structure);
        dest.writeInt(built_age);
        dest.writeString(nature_of_property_right);
        dest.writeString(supporting_facility);
        dest.writeString(adding_proxy);
        dest.writeInt(house_example_id);
        dest.writeString(note);
        dest.writeInt(xiaoqu_id);
        dest.writeString(detialedaddress);
        dest.writeString(created_at);
        dest.writeString(updated_at);
    }

    @Override
    public String toString() {
        return "SellResidentHouseBean{" +
                "id=" + id +
                ", leixing='" + leixing + '\'' +
                ", bvalue='" + bvalue + '\'' +
                ", position='" + position + '\'' +
                ", sourcetype='" + sourcetype + '\'' +
                ", house_price=" + house_price +
                ", rentornot=" + rentornot +
                ", house_area=" + house_area +
                ", house_type='" + house_type + '\'' +
                ", floor=" + floor +
                ", aspect='" + aspect + '\'' +
                ", decoration_level='" + decoration_level + '\'' +
                ", built_type='" + built_type + '\'' +
                ", built_structure='" + built_structure + '\'' +
                ", built_age=" + built_age +
                ", nature_of_property_right='" + nature_of_property_right + '\'' +
                ", supporting_facility='" + supporting_facility + '\'' +
                ", adding_proxy='" + adding_proxy + '\'' +
                ", house_example_id=" + house_example_id +
                ", note='" + note + '\'' +
                ", xiaoqu_id=" + xiaoqu_id +
                ", detialedaddress='" + detialedaddress + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
