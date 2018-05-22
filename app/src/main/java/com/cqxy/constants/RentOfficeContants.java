package com.cqxy.constants;

import android.os.Parcel;
import android.os.Parcelable;

import com.cqxy.bean.RentOfficeHouseBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wx on 2017/10/17.
 */

public class RentOfficeContants implements Parcelable {
    public static final String POSITION = "楼盘名称";
    public static final String RENT = "价格";
    public static final String HOUSE_AREA = "建筑面积";
    public static final String INCLUDING_PROPERTY_FEE_OR_NOT = "包含物业费";
    public static final String PAYMENT = "支付方式";
    public static final String PROPERTY_FEE = "物业费";
    public static final String FLOOR = "楼层";
    public static final String OFFICE_BUILDING_LEVEL = "写字楼级别";
    public static final String NOTE = "备注";
    public static final String OFFICE_BUILDING_RENT = "office_building_rent";

    /**
     * house : {"leixing":"","bvalue":"","sourcetype":"office_building_rents","id":17,"position":"正式錄入租赁1","rent":20000,"rentornot":1,"rent_type":null,"house_area":2000,"house_area_type":null,"payment":"月付","including_property_fee_or_not":"是","floor":20,"floor_type":null,"property_fee":500,"property_fee_type":null,"office_building_level":"甲级","office_building_type":"","house_example_id":17,"note":"","created_at":"2017-10-27T06:46:27.000Z","updated_at":"2017-10-27T06:46:27.000Z"}
     * imageresources : []
     */

    private RentOfficeHouseBean house;
    private List<ImageresourcesBean> imageresources;

    protected RentOfficeContants(Parcel in) {
        imageresources = in.createTypedArrayList(ImageresourcesBean.CREATOR);
    }

    public static final Creator<RentOfficeContants> CREATOR = new Creator<RentOfficeContants>() {
        @Override
        public RentOfficeContants createFromParcel(Parcel in) {
            return new RentOfficeContants(in);
        }

        @Override
        public RentOfficeContants[] newArray(int size) {
            return new RentOfficeContants[size];
        }
    };

    public static RentOfficeContants objectFromData(String str) {

        return new Gson().fromJson(str, RentOfficeContants.class);
    }

    public static RentOfficeContants objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), RentOfficeContants.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<RentOfficeContants> arrayRentOfficeContantsFromData(String str) {

        Type listType = new TypeToken<ArrayList<RentOfficeContants>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<RentOfficeContants> arrayRentOfficeContantsFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<RentOfficeContants>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(key), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public RentOfficeHouseBean getHouse() {
        return house;
    }

    public void setHouse(RentOfficeHouseBean house) {
        this.house = house;
    }

    public List<ImageresourcesBean> getImageresources() {
        return imageresources;
    }

    public void setImageresources(List<ImageresourcesBean> imageresources) {
        this.imageresources = imageresources;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(imageresources);
    }



    @Override
    public String toString() {
        return "RentOfficeContants{" +
                "house=" + house +
                ", imageresources=" + imageresources +
                '}';
    }
}
