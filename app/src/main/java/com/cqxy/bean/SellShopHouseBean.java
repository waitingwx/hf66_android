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

public  class SellShopHouseBean implements Parcelable {
    /**
     * sourcetype : shop_sellings
     * id : 2
     * position : 上去买卖
     * rentornot : 0
     * house_price : 84545
     * house_price_type : null
     * house_area :
     * house_area_type : null
     * property_fee : 10
     * property_fee_type : null
     * floor : 20
     * floor_type : null
     * decoration_level : 毛坯
     * supporting_facility :
     * shop_type : 住宅底商
     * ceding_or_not : 是
     * target_formats : 百货超市
     * house_example_id : 33
     * note :
     * leixing :
     * bvalue :
     * created_at : 2017-10-27T09:01:02.000Z
     * updated_at : 2017-10-27T09:01:02.000Z
     */

    private String sourcetype;
    private int id;
    private String position;
    private int rentornot;
    private int house_price;
    private String house_area;
    private int property_fee;
    private int floor;
    private String decoration_level;
    private String supporting_facility;
    private String shop_type;
    private String ceding_or_not;
    private String target_formats;
    private int house_example_id;
    private String note;
    private int shangquan_id;
    private String detialedaddress;
    private String leixing;
    private String bvalue;
    private String created_at;
    private String updated_at;

    protected SellShopHouseBean(Parcel in) {
        sourcetype = in.readString();
        id = in.readInt();
        position = in.readString();
        rentornot = in.readInt();
        house_price = in.readInt();
        house_area = in.readString();
        property_fee = in.readInt();
        floor = in.readInt();
        decoration_level = in.readString();
        supporting_facility = in.readString();
        shop_type = in.readString();
        ceding_or_not = in.readString();
        target_formats = in.readString();
        house_example_id = in.readInt();
        note = in.readString();
        shangquan_id = in.readInt();
        detialedaddress = in.readString();
        leixing = in.readString();
        bvalue = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
    }

    public static final Creator<SellShopHouseBean> CREATOR = new Creator<SellShopHouseBean>() {
        @Override
        public SellShopHouseBean createFromParcel(Parcel in) {
            return new SellShopHouseBean(in);
        }

        @Override
        public SellShopHouseBean[] newArray(int size) {
            return new SellShopHouseBean[size];
        }
    };

    public static SellShopHouseBean objectFromData(String str) {

        return new Gson().fromJson(str, SellShopHouseBean.class);
    }

    public static SellShopHouseBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), SellShopHouseBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<SellShopHouseBean> arraySellShopHouseBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<SellShopHouseBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<SellShopHouseBean> arraySellShopHouseBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<SellShopHouseBean>>() {
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

    public String getHouse_area() {
        return house_area;
    }

    public void setHouse_area(String house_area) {
        this.house_area = house_area;
    }

    public int getProperty_fee() {
        return property_fee;
    }

    public void setProperty_fee(int property_fee) {
        this.property_fee = property_fee;
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

    public String getSupporting_facility() {
        return supporting_facility;
    }

    public void setSupporting_facility(String supporting_facility) {
        this.supporting_facility = supporting_facility;
    }

    public String getShop_type() {
        return shop_type;
    }

    public void setShop_type(String shop_type) {
        this.shop_type = shop_type;
    }

    public String getCeding_or_not() {
        return ceding_or_not;
    }

    public void setCeding_or_not(String ceding_or_not) {
        this.ceding_or_not = ceding_or_not;
    }

    public String getTarget_formats() {
        return target_formats;
    }

    public void setTarget_formats(String target_formats) {
        this.target_formats = target_formats;
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
        dest.writeString(house_area);
        dest.writeInt(property_fee);
        dest.writeInt(floor);
        dest.writeString(decoration_level);
        dest.writeString(supporting_facility);
        dest.writeString(shop_type);
        dest.writeString(ceding_or_not);
        dest.writeString(target_formats);
        dest.writeInt(house_example_id);
        dest.writeString(note);
        dest.writeInt(shangquan_id);
        dest.writeString(detialedaddress);
        dest.writeString(leixing);
        dest.writeString(bvalue);
        dest.writeString(created_at);
        dest.writeString(updated_at);
    }

    @Override
    public String toString() {
        return "SellShopHouseBean{" +
                "sourcetype='" + sourcetype + '\'' +
                ", id=" + id +
                ", position='" + position + '\'' +
                ", rentornot=" + rentornot +
                ", house_price=" + house_price +
                ", house_area='" + house_area + '\'' +
                ", property_fee=" + property_fee +
                ", floor=" + floor +
                ", decoration_level='" + decoration_level + '\'' +
                ", supporting_facility='" + supporting_facility + '\'' +
                ", shop_type='" + shop_type + '\'' +
                ", ceding_or_not='" + ceding_or_not + '\'' +
                ", target_formats='" + target_formats + '\'' +
                ", house_example_id=" + house_example_id +
                ", note='" + note + '\'' +
                ", shangquan_id='" + shangquan_id + '\'' +
                ", detialedaddress='" + detialedaddress + '\'' +
                ", leixing='" + leixing + '\'' +
                ", bvalue='" + bvalue + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
