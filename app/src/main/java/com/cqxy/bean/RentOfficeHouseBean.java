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

public class RentOfficeHouseBean implements Parcelable {
    /**
     * leixing :
     * bvalue :
     * sourcetype : office_building_rents
     * id : 17
     * position : 正式錄入租赁1
     * rent : 20000
     * rentornot : 1
     * rent_type : null
     * house_area : 2000
     * house_area_type : null
     * payment : 月付
     * including_property_fee_or_not : 是
     * floor : 20
     * floor_type : null
     * property_fee : 500
     * property_fee_type : null
     * office_building_level : 甲级
     * office_building_type :
     * house_example_id : 17
     * note :
     * created_at : 2017-10-27T06:46:27.000Z
     * updated_at : 2017-10-27T06:46:27.000Z
     */

    private String leixing;
    private String bvalue;
    private String sourcetype;
    private int id;
    private String position;
    private int rent;
    private int rentornot;
    private int house_area;
    private String payment;
    private String including_property_fee_or_not;
    private int floor;
    private int property_fee;
    private String office_building_level;
    private String office_building_type;
    private int house_example_id;
    private String note;
    private int shangquan_id;
    private String detialedaddress;
    private String created_at;
    private String updated_at;

    protected RentOfficeHouseBean(Parcel in) {
        leixing = in.readString();
        bvalue = in.readString();
        sourcetype = in.readString();
        id = in.readInt();
        position = in.readString();
        rent = in.readInt();
        rentornot = in.readInt();
        house_area = in.readInt();
        payment = in.readString();
        including_property_fee_or_not = in.readString();
        floor = in.readInt();
        property_fee = in.readInt();
        office_building_level = in.readString();
        office_building_type = in.readString();
        house_example_id = in.readInt();
        note = in.readString();
        shangquan_id = in.readInt();
        detialedaddress = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
    }

    public static final Creator<RentOfficeHouseBean> CREATOR = new Creator<RentOfficeHouseBean>() {
        @Override
        public RentOfficeHouseBean createFromParcel(Parcel in) {
            return new RentOfficeHouseBean(in);
        }

        @Override
        public RentOfficeHouseBean[] newArray(int size) {
            return new RentOfficeHouseBean[size];
        }
    };

    public static RentOfficeHouseBean objectFromData(String str) {

        return new Gson().fromJson(str, RentOfficeHouseBean.class);
    }

    public static RentOfficeHouseBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), RentOfficeHouseBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<RentOfficeHouseBean> arrayRentOfficeHouseBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<RentOfficeHouseBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<RentOfficeHouseBean> arrayRentOfficeHouseBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<RentOfficeHouseBean>>() {
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

    public int getHouse_area() {
        return house_area;
    }

    public void setHouse_area(int house_area) {
        this.house_area = house_area;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getIncluding_property_fee_or_not() {
        return including_property_fee_or_not;
    }

    public void setIncluding_property_fee_or_not(String including_property_fee_or_not) {
        this.including_property_fee_or_not = including_property_fee_or_not;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getProperty_fee() {
        return property_fee;
    }

    public void setProperty_fee(int property_fee) {
        this.property_fee = property_fee;
    }

    public String getOffice_building_level() {
        return office_building_level;
    }

    public void setOffice_building_level(String office_building_level) {
        this.office_building_level = office_building_level;
    }

    public String getOffice_building_type() {
        return office_building_type;
    }

    public void setOffice_building_type(String office_building_type) {
        this.office_building_type = office_building_type;
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
        dest.writeInt(rent);
        dest.writeInt(rentornot);
        dest.writeInt(house_area);
        dest.writeString(payment);
        dest.writeString(including_property_fee_or_not);
        dest.writeInt(floor);
        dest.writeInt(property_fee);
        dest.writeString(office_building_level);
        dest.writeString(office_building_type);
        dest.writeInt(house_example_id);
        dest.writeString(note);
        dest.writeInt(shangquan_id);
        dest.writeString(detialedaddress);
        dest.writeString(created_at);
        dest.writeString(updated_at);
    }

    @Override
    public String toString() {
        return "RentOfficeHouseBean{" +
                "leixing='" + leixing + '\'' +
                ", bvalue='" + bvalue + '\'' +
                ", sourcetype='" + sourcetype + '\'' +
                ", id=" + id +
                ", position='" + position + '\'' +
                ", rent=" + rent +
                ", rentornot=" + rentornot +
                ", house_area=" + house_area +
                ", payment='" + payment + '\'' +
                ", including_property_fee_or_not='" + including_property_fee_or_not + '\'' +
                ", floor=" + floor +
                ", property_fee=" + property_fee +
                ", office_building_level='" + office_building_level + '\'' +
                ", office_building_type='" + office_building_type + '\'' +
                ", house_example_id=" + house_example_id +
                ", note='" + note + '\'' +
                ", shangquan_id='" + shangquan_id + '\'' +
                ", detialedaddress='" + detialedaddress + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
