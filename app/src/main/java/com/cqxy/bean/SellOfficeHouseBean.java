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

public  class SellOfficeHouseBean implements Parcelable {
    /**
     * sourcetype : office_building_sellings
     * id : 4
     * position : 写字楼买卖6
     * rentornot : 0
     * house_price : 84665
     * leixing : 佣金
     * bvalue : 2
     * house_price_type : null
     * house_area :
     * built_age : 6464
     * house_area_type : null
     * property_fee : 9464
     * property_fee_type : null
     * floor : 20
     * floor_type : null
     * office_building_level : 甲级
     * house_example_id : 42
     * note : 无
     * created_at : 2017-10-31T09:21:51.000Z
     * updated_at : 2017-10-31T09:21:51.000Z
     */

    private String sourcetype;
    private int id;
    private String position;
    private int rentornot;
    private int house_price;
    private String leixing;
    private String bvalue;
    private String house_area;
    private int built_age;
    private int property_fee;
    private Object property_fee_type;
    private int floor;
    private String office_building_level;
    private int house_example_id;
    private String note;
    private int shangquan_id;
    private String detialedaddress;
    private String created_at;
    private String updated_at;

    protected SellOfficeHouseBean(Parcel in) {
        sourcetype = in.readString();
        id = in.readInt();
        position = in.readString();
        rentornot = in.readInt();
        house_price = in.readInt();
        leixing = in.readString();
        bvalue = in.readString();
        house_area = in.readString();
        built_age = in.readInt();
        property_fee = in.readInt();
        floor = in.readInt();
        office_building_level = in.readString();
        house_example_id = in.readInt();
        note = in.readString();
        shangquan_id = in.readInt();
        detialedaddress = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
    }

    public static final Creator<SellOfficeHouseBean> CREATOR = new Creator<SellOfficeHouseBean>() {
        @Override
        public SellOfficeHouseBean createFromParcel(Parcel in) {
            return new SellOfficeHouseBean(in);
        }

        @Override
        public SellOfficeHouseBean[] newArray(int size) {
            return new SellOfficeHouseBean[size];
        }
    };

    public static SellOfficeHouseBean objectFromData(String str) {

        return new Gson().fromJson(str, SellOfficeHouseBean.class);
    }

    public static SellOfficeHouseBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), SellOfficeHouseBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<SellOfficeHouseBean> arraySellOfficeHouseBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<SellOfficeHouseBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<SellOfficeHouseBean> arraySellOfficeHouseBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<SellOfficeHouseBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(key), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


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

    public String getHouse_area() {
        return house_area;
    }

    public void setHouse_area(String house_area) {
        this.house_area = house_area;
    }

    public int getBuilt_age() {
        return built_age;
    }

    public void setBuilt_age(int built_age) {
        this.built_age = built_age;
    }

    public int getProperty_fee() {
        return property_fee;
    }

    public void setProperty_fee(int property_fee) {
        this.property_fee = property_fee;
    }

    public Object getProperty_fee_type() {
        return property_fee_type;
    }

    public void setProperty_fee_type(Object property_fee_type) {
        this.property_fee_type = property_fee_type;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getOffice_building_level() {
        return office_building_level;
    }

    public void setOffice_building_level(String office_building_level) {
        this.office_building_level = office_building_level;
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
        dest.writeString(sourcetype);
        dest.writeInt(id);
        dest.writeString(position);
        dest.writeInt(rentornot);
        dest.writeInt(house_price);
        dest.writeString(leixing);
        dest.writeString(bvalue);
        dest.writeString(house_area);
        dest.writeInt(built_age);
        dest.writeInt(property_fee);
        dest.writeInt(floor);
        dest.writeString(office_building_level);
        dest.writeInt(house_example_id);
        dest.writeString(note);
        dest.writeInt(shangquan_id);
        dest.writeString(detialedaddress);
        dest.writeString(created_at);
        dest.writeString(updated_at);
    }

    @Override
    public String toString() {
        return "SellOfficeHouseBean{" +
                "sourcetype='" + sourcetype + '\'' +
                ", id=" + id +
                ", position='" + position + '\'' +
                ", rentornot=" + rentornot +
                ", house_price=" + house_price +
                ", leixing='" + leixing + '\'' +
                ", bvalue='" + bvalue + '\'' +
                ", house_area='" + house_area + '\'' +
                ", built_age=" + built_age +
                ", property_fee=" + property_fee +
                ", property_fee_type=" + property_fee_type +
                ", floor=" + floor +
                ", office_building_level='" + office_building_level + '\'' +
                ", house_example_id=" + house_example_id +
                ", note='" + note + '\'' +
                ", shangquan_id='" + shangquan_id + '\'' +
                ", detialedaddress='" + detialedaddress + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
