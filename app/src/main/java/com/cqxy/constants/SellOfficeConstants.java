package com.cqxy.constants;

import android.os.Parcel;
import android.os.Parcelable;

import com.cqxy.bean.SellOfficeHouseBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wx on 2017/10/13.
 */

public class SellOfficeConstants implements Parcelable{
    public static final String POSITION = "楼盘名称";
    public static final String HOUSE_PRICE = "价格";
    public static final String HOUSE_AREA = "建筑面积";
    public static final String BUILT_AGE = "建筑年代";
    public static final String PROPERTY_FEE = "物业费";
    public static final String FLOOR = "楼层";
    public static final String OFFICE_BUILDING_LEVEL = "写字楼级别";
    public static final String NOTE = "备注";
    public static final String OFFICE_BUILDING_SELLING = "office_building_selling";

    /**
     * house : {"sourcetype":"office_building_sellings","id":4,"position":"写字楼买卖6","rentornot":0,"house_price":84665,"leixing":"佣金","bvalue":"2","house_price_type":null,"house_area":"","built_age":6464,"house_area_type":null,"property_fee":9464,"property_fee_type":null,"floor":20,"floor_type":null,"office_building_level":"甲级","house_example_id":42,"note":"无","created_at":"2017-10-31T09:21:51.000Z","updated_at":"2017-10-31T09:21:51.000Z"}
     * imageresources : []
     */

    private SellOfficeHouseBean house;
    private List<ImageresourcesBean> imageresources;

    protected SellOfficeConstants(Parcel in) {
        imageresources = in.createTypedArrayList(ImageresourcesBean.CREATOR);
    }

    public static final Creator<SellOfficeConstants> CREATOR = new Creator<SellOfficeConstants>() {
        @Override
        public SellOfficeConstants createFromParcel(Parcel in) {
            return new SellOfficeConstants(in);
        }

        @Override
        public SellOfficeConstants[] newArray(int size) {
            return new SellOfficeConstants[size];
        }
    };

    public static SellOfficeConstants objectFromData(String str) {

        return new Gson().fromJson(str, SellOfficeConstants.class);
    }

    public static SellOfficeConstants objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), SellOfficeConstants.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<SellOfficeConstants> arraySellOfficeConstantsFromData(String str) {

        Type listType = new TypeToken<ArrayList<SellOfficeConstants>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<SellOfficeConstants> arraySellOfficeConstantsFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<SellOfficeConstants>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(key), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public SellOfficeHouseBean getHouse() {
        return house;
    }

    public void setHouse(SellOfficeHouseBean house) {
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
        return "SellOfficeConstants{" +
                "house=" + house +
                ", imageresources=" + imageresources +
                '}';
    }
}
