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

public  class RentResidentHouseBean implements Parcelable {
    /**
     * sourcetype : residence_rents
     * id : 2
     * position : 黑的呵护
     * rent : 8484540
     * rentornot : 1
     * rent_type : null
     * house_area :
     * house_area_type : null
     * lease_way : 整租
     * payment : 月付
     * house_type : 0室0厅0卫0厨0阳
     * floor : 0
     * floor_type : null
     * aspect : 东南
     * decoration_level : 毛坯
     * leixing : 佣金
     * bvalue : 20%
     * supporting_facility : 有线电视,宽带,电话,煤气/天然气,车库/车位,车库/车位
     * house_example_id : 37
     * note : 没啥注释
     * created_at : 2017-10-27T12:36:33.000Z
     * updated_at : 2017-10-27T12:36:33.000Z
     */

    private String sourcetype;
    private int id;
    private String position;
    private int rent;
    private int rentornot;
    private String house_area;
    private String lease_way;
    private String payment;
    private String house_type;
    private int floor;
    private String aspect;
    private String decoration_level;
    private String leixing;
    private String bvalue;
    private String supporting_facility;
    private int house_example_id;
    private String note;
    private int xiaoqu_id;
    private String detialedaddress;
    private String created_at;
    private String updated_at;

    protected RentResidentHouseBean(Parcel in) {
        sourcetype = in.readString();
        id = in.readInt();
        position = in.readString();
        rent = in.readInt();
        rentornot = in.readInt();
        house_area = in.readString();
        lease_way = in.readString();
        payment = in.readString();
        house_type = in.readString();
        floor = in.readInt();
        aspect = in.readString();
        decoration_level = in.readString();
        leixing = in.readString();
        bvalue = in.readString();
        supporting_facility = in.readString();
        house_example_id = in.readInt();
        note = in.readString();
        xiaoqu_id = in.readInt();
        detialedaddress = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
    }

    public static final Creator<RentResidentHouseBean> CREATOR = new Creator<RentResidentHouseBean>() {
        @Override
        public RentResidentHouseBean createFromParcel(Parcel in) {
            return new RentResidentHouseBean(in);
        }

        @Override
        public RentResidentHouseBean[] newArray(int size) {
            return new RentResidentHouseBean[size];
        }
    };

    public static RentResidentHouseBean objectFromData(String str) {

        return new Gson().fromJson(str, RentResidentHouseBean.class);
    }

    public static RentResidentHouseBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), RentResidentHouseBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<RentResidentHouseBean> arrayRentResidentHouseBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<RentResidentHouseBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<RentResidentHouseBean> arrayRentResidentHouseBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<RentResidentHouseBean>>() {
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

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public int getRentornot() {
        return rentornot;
    }

    public void setRentornot(int rentornot) {
        this.rentornot = rentornot;
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
        dest.writeString(sourcetype);
        dest.writeInt(id);
        dest.writeString(position);
        dest.writeInt(rent);
        dest.writeInt(rentornot);
        dest.writeString(house_area);
        dest.writeString(lease_way);
        dest.writeString(payment);
        dest.writeString(house_type);
        dest.writeInt(floor);
        dest.writeString(aspect);
        dest.writeString(decoration_level);
        dest.writeString(leixing);
        dest.writeString(bvalue);
        dest.writeString(supporting_facility);
        dest.writeInt(house_example_id);
        dest.writeString(note);
        dest.writeInt(xiaoqu_id);
        dest.writeString(detialedaddress);
        dest.writeString(created_at);
        dest.writeString(updated_at);
    }

    @Override
    public String toString() {
        return "RentResidentHouseBean{" +
                "sourcetype='" + sourcetype + '\'' +
                ", id=" + id +
                ", position='" + position + '\'' +
                ", rent=" + rent +
                ", rentornot=" + rentornot +
                ", house_area='" + house_area + '\'' +
                ", lease_way='" + lease_way + '\'' +
                ", payment='" + payment + '\'' +
                ", house_type='" + house_type + '\'' +
                ", floor=" + floor +
                ", aspect='" + aspect + '\'' +
                ", decoration_level='" + decoration_level + '\'' +
                ", leixing='" + leixing + '\'' +
                ", bvalue='" + bvalue + '\'' +
                ", supporting_facility='" + supporting_facility + '\'' +
                ", house_example_id=" + house_example_id +
                ", note='" + note + '\'' +
                ", xiaoqu_id=" + xiaoqu_id +
                ", detialedaddress='" + detialedaddress + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
