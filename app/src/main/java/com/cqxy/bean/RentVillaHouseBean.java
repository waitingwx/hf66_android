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

public  class RentVillaHouseBean implements Parcelable {
    /**
     * leixing : 佣金
     * bvalue : 20%
     * sourcetype : villa_rents
     * id : 1
     * position : 别墅租赁
     * rentornot : 1
     * rent : 84845
     * rent_type : null
     * house_area : 望京
     * house_area_type : null
     * lease_way : 整租
     * payment : 月付
     * house_type :
     * floor : 20
     * floor_type : null
     * built_age : 2010
     * built_type : 独栋
     * decoration_level : 毛坯
     * supporting_facility :
     * house_example_id : 35
     * note :
     * created_at : 2017-10-27T09:05:35.000Z
     * updated_at : 2017-10-27T09:05:35.000Z
     */

    private String leixing;
    private String bvalue;
    private String sourcetype;
    private int id;
    private String position;
    private int rentornot;
    private int rent;
    private String house_area;
    private String lease_way;
    private String payment;
    private String house_type;
    private int floor;
    private int built_age;
    private String built_type;
    private String decoration_level;
    private String supporting_facility;
    private int house_example_id;
    private String note;
    private int shangquan_id;
    private String detialedaddress;
    private String created_at;
    private String updated_at;

    protected RentVillaHouseBean(Parcel in) {
        leixing = in.readString();
        bvalue = in.readString();
        sourcetype = in.readString();
        id = in.readInt();
        position = in.readString();
        rentornot = in.readInt();
        rent = in.readInt();
        house_area = in.readString();
        lease_way = in.readString();
        payment = in.readString();
        house_type = in.readString();
        floor = in.readInt();
        built_age = in.readInt();
        built_type = in.readString();
        decoration_level = in.readString();
        supporting_facility = in.readString();
        house_example_id = in.readInt();
        note = in.readString();
        shangquan_id = in.readInt();
        detialedaddress = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
    }

    public static final Creator<RentVillaHouseBean> CREATOR = new Creator<RentVillaHouseBean>() {
        @Override
        public RentVillaHouseBean createFromParcel(Parcel in) {
            return new RentVillaHouseBean(in);
        }

        @Override
        public RentVillaHouseBean[] newArray(int size) {
            return new RentVillaHouseBean[size];
        }
    };

    public static RentVillaHouseBean objectFromData(String str) {

        return new Gson().fromJson(str, RentVillaHouseBean.class);
    }

    public static RentVillaHouseBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), RentVillaHouseBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<RentVillaHouseBean> arrayRentVillaHouseBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<RentVillaHouseBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<RentVillaHouseBean> arrayRentVillaHouseBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<RentVillaHouseBean>>() {
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

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public String getHouse_area() {
        return house_area;
    }

    public void setHouse_area(String house_area) {
        this.house_area = house_area;
    }

    public String getLease_way() {
        return lease_way;
    }

    public void setLease_way(String lease_way) {
        this.lease_way = lease_way;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
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

    public int getBuilt_age() {
        return built_age;
    }

    public void setBuilt_age(int built_age) {
        this.built_age = built_age;
    }

    public String getBuilt_type() {
        return built_type;
    }

    public void setBuilt_type(String built_type) {
        this.built_type = built_type;
    }

    public String getDecoration_level() {
        return decoration_level;
    }

    public void setDecoration_level(String decoration_level) {
        this.decoration_level = decoration_level;
    }

    public String getSupporting_facility() {
        return supporting_facility;
    }

    public void setSupporting_facility(String supporting_facility) {
        this.supporting_facility = supporting_facility;
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
        dest.writeString(leixing);
        dest.writeString(bvalue);
        dest.writeString(sourcetype);
        dest.writeInt(id);
        dest.writeString(position);
        dest.writeInt(rentornot);
        dest.writeInt(rent);
        dest.writeString(house_area);
        dest.writeString(lease_way);
        dest.writeString(payment);
        dest.writeString(house_type);
        dest.writeInt(floor);
        dest.writeInt(built_age);
        dest.writeString(built_type);
        dest.writeString(decoration_level);
        dest.writeString(supporting_facility);
        dest.writeInt(house_example_id);
        dest.writeString(note);
        dest.writeInt(shangquan_id);
        dest.writeString(detialedaddress);
        dest.writeString(created_at);
        dest.writeString(updated_at);
    }

    @Override
    public String toString() {
        return "RentVillaHouseBean{" +
                "leixing='" + leixing + '\'' +
                ", bvalue='" + bvalue + '\'' +
                ", sourcetype='" + sourcetype + '\'' +
                ", id=" + id +
                ", position='" + position + '\'' +
                ", rentornot=" + rentornot +
                ", rent=" + rent +
                ", house_area='" + house_area + '\'' +
                ", lease_way='" + lease_way + '\'' +
                ", payment='" + payment + '\'' +
                ", house_type='" + house_type + '\'' +
                ", floor=" + floor +
                ", built_age=" + built_age +
                ", built_type='" + built_type + '\'' +
                ", decoration_level='" + decoration_level + '\'' +
                ", supporting_facility='" + supporting_facility + '\'' +
                ", house_example_id=" + house_example_id +
                ", note='" + note + '\'' +
                ", shangquan_id='" + shangquan_id + '\'' +
                ", detialedaddress='" + detialedaddress + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
