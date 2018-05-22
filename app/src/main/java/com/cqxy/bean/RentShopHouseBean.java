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

public  class RentShopHouseBean implements Parcelable {
    /**
     * sourcetype : shop_rents
     * id : 1
     * position : 商铺租赁
     * rent : 846454
     * rentornot : 1
     * rent_type : null
     * house_area : 94949
     * house_area_type : null
     * including_property_fee_or_not : 是
     * payment : 半年付
     * property_fee : 64649
     * property_fee_type : null
     * floor : 10
     * floor_type : null
     * shop_stating : 营业中
     * shop_type : 住宅底商
     * ceding_or_not : 是
     * transfer_or_not : 百货超市
     * target_formats : 美容美发
     * decoration_level : 毛坯
     * supporting_facility : 空调,热水器,宽带,厨具,露台/花园,储藏室/地下室,储藏室/地下室
     * house_example_id : 43
     * note : 无
     * leixing :
     * bvalue :
     * created_at : 2017-11-02T08:04:50.000Z
     * updated_at : 2017-11-02T08:04:50.000Z
     */

    private String sourcetype;
    private int id;
    private String position;
    private int rent;
    private int rentornot;
    private int house_area;
    private String including_property_fee_or_not;
    private String payment;
    private int property_fee;
    private int floor;
    private String shop_stating;
    private String shop_type;
    private String ceding_or_not;
    private String transfer_or_not;
    private String target_formats;
    private String decoration_level;
    private String supporting_facility;
    private int house_example_id;
    private String note;
    private int shangquan_id;
    private String detialedaddress;
    private String leixing;
    private String bvalue;
    private String created_at;
    private String updated_at;

    protected RentShopHouseBean(Parcel in) {
        sourcetype = in.readString();
        id = in.readInt();
        position = in.readString();
        rent = in.readInt();
        rentornot = in.readInt();
        house_area = in.readInt();
        including_property_fee_or_not = in.readString();
        payment = in.readString();
        property_fee = in.readInt();
        floor = in.readInt();
        shop_stating = in.readString();
        shop_type = in.readString();
        ceding_or_not = in.readString();
        transfer_or_not = in.readString();
        target_formats = in.readString();
        decoration_level = in.readString();
        supporting_facility = in.readString();
        house_example_id = in.readInt();
        note = in.readString();
        shangquan_id = in.readInt();
        detialedaddress = in.readString();
        leixing = in.readString();
        bvalue = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
    }

    public static final Creator<RentShopHouseBean> CREATOR = new Creator<RentShopHouseBean>() {
        @Override
        public RentShopHouseBean createFromParcel(Parcel in) {
            return new RentShopHouseBean(in);
        }

        @Override
        public RentShopHouseBean[] newArray(int size) {
            return new RentShopHouseBean[size];
        }
    };

    public static RentShopHouseBean objectFromData(String str) {

        return new Gson().fromJson(str, RentShopHouseBean.class);
    }

    public static RentShopHouseBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), RentShopHouseBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<RentShopHouseBean> arrayRentShopHouseBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<RentShopHouseBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<RentShopHouseBean> arrayRentShopHouseBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<RentShopHouseBean>>() {
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

    public int getHouse_area() {
        return house_area;
    }

    public void setHouse_area(int house_area) {
        this.house_area = house_area;
    }

    public String getIncluding_property_fee_or_not() {
        return including_property_fee_or_not;
    }

    public void setIncluding_property_fee_or_not(String including_property_fee_or_not) {
        this.including_property_fee_or_not = including_property_fee_or_not;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
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

    public String getShop_stating() {
        return shop_stating;
    }

    public void setShop_stating(String shop_stating) {
        this.shop_stating = shop_stating;
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

    public String getTransfer_or_not() {
        return transfer_or_not;
    }

    public void setTransfer_or_not(String transfer_or_not) {
        this.transfer_or_not = transfer_or_not;
    }

    public String getTarget_formats() {
        return target_formats;
    }

    public void setTarget_formats(String target_formats) {
        this.target_formats = target_formats;
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
        dest.writeInt(rent);
        dest.writeInt(rentornot);
        dest.writeInt(house_area);
        dest.writeString(including_property_fee_or_not);
        dest.writeString(payment);
        dest.writeInt(property_fee);
        dest.writeInt(floor);
        dest.writeString(shop_stating);
        dest.writeString(shop_type);
        dest.writeString(ceding_or_not);
        dest.writeString(transfer_or_not);
        dest.writeString(target_formats);
        dest.writeString(decoration_level);
        dest.writeString(supporting_facility);
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
        return "RentShopHouseBean{" +
                "sourcetype='" + sourcetype + '\'' +
                ", id=" + id +
                ", position='" + position + '\'' +
                ", rent=" + rent +
                ", rentornot=" + rentornot +
                ", house_area=" + house_area +
                ", including_property_fee_or_not='" + including_property_fee_or_not + '\'' +
                ", payment='" + payment + '\'' +
                ", property_fee=" + property_fee +
                ", floor=" + floor +
                ", shop_stating='" + shop_stating + '\'' +
                ", shop_type='" + shop_type + '\'' +
                ", ceding_or_not='" + ceding_or_not + '\'' +
                ", transfer_or_not='" + transfer_or_not + '\'' +
                ", target_formats='" + target_formats + '\'' +
                ", decoration_level='" + decoration_level + '\'' +
                ", supporting_facility='" + supporting_facility + '\'' +
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
