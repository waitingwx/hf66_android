package com.cqxy.constants;

import android.os.Parcel;
import android.os.Parcelable;

import com.cqxy.bean.SellShopHouseBean;
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

public class SellShopConstants implements Parcelable{
    public static final String POSITION = "楼盘名称";
    public static final String HOUSE_PRICE = "价格";
    public static final String HOUSE_AREA = "建筑面积";
    public static final String PROPERTY_FEE = "物业费";
    public static final String FLOOR = "楼层";
    public static final String DECORATION_LEVE = "装修程度";
    public static final String SHOP_TYPE = "商铺类型";
    public static final String CEDING_OR_NOT = "是否割让";
    public static final String TARGET_FORMATS = "目标业态";
    public static final String SHOP_SELL = "shop_selling";
    public static final String NOTE = "备注";

    /**
     * house : {"sourcetype":"shop_sellings","id":2,"position":"上去买卖","rentornot":0,"house_price":84545,"house_price_type":null,"house_area":"","house_area_type":null,"property_fee":10,"property_fee_type":null,"floor":20,"floor_type":null,"decoration_level":"毛坯","supporting_facility":"","shop_type":"住宅底商","ceding_or_not":"是","target_formats":"百货超市","house_example_id":33,"note":"","leixing":"","bvalue":"","created_at":"2017-10-27T09:01:02.000Z","updated_at":"2017-10-27T09:01:02.000Z"}
     * imageresources : []
     */

    private SellShopHouseBean house;
    private List<ImageresourcesBean> imageresources;

    protected SellShopConstants(Parcel in) {
        imageresources = in.createTypedArrayList(ImageresourcesBean.CREATOR);
    }

    public static final Creator<SellShopConstants> CREATOR = new Creator<SellShopConstants>() {
        @Override
        public SellShopConstants createFromParcel(Parcel in) {
            return new SellShopConstants(in);
        }

        @Override
        public SellShopConstants[] newArray(int size) {
            return new SellShopConstants[size];
        }
    };

    public static SellShopConstants objectFromData(String str) {

        return new Gson().fromJson(str, SellShopConstants.class);
    }

    public static SellShopConstants objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), SellShopConstants.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<SellShopConstants> arraySellShopConstantsFromData(String str) {

        Type listType = new TypeToken<ArrayList<SellShopConstants>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<SellShopConstants> arraySellShopConstantsFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<SellShopConstants>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(key), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public SellShopHouseBean getHouse() {
        return house;
    }

    public void setHouse(SellShopHouseBean house) {
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
        return "SellShopConstants{" +
                "house=" + house +
                ", imageresources=" + imageresources +
                '}';
    }
}
