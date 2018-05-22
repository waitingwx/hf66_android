package com.cqxy.constants;

import android.os.Parcel;
import android.os.Parcelable;

import com.cqxy.bean.RentShopHouseBean;
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

public class RentShopContants implements Parcelable{
    public static final String POSITION = "楼盘名称";
    public static final String RENT = "价格";
    public static final String HOUSE_AREA = "建筑面积";
    public static final String INCLUDING_PROPERTY_FEE_OR_NOT = "包含物业费";
    public static final String PAYMENT = "支付方式";
    public static final String PROPERTY_FEE = "物业费";
    public static final String FLOOR = "楼层";
    public static final String SHOP_STATING = "商铺状态";
    public static final String SHOP_TYPE = "商铺类型";
    public static final String CEDING_OR_NOT = "是否割让";
    public static final String TRANSFER_OR_NOT = "是否转让";
    public static final String TARGET_FORMATS = "目标业态";
    public static final String DECORATION_LEVEL = "装修程度";
    public static final String SUPPORTING_FACILITY = "配套设施";
    public static final String SHOP_RENT = "shop_rent";
    public static final String NOTE = "备注";

    /**
     * house : {"sourcetype":"shop_rents","id":1,"position":"商铺租赁","rent":846454,"rentornot":1,"rent_type":null,"house_area":94949,"house_area_type":null,"including_property_fee_or_not":"是","payment":"半年付","property_fee":64649,"property_fee_type":null,"floor":10,"floor_type":null,"shop_stating":"营业中","shop_type":"住宅底商","ceding_or_not":"是","transfer_or_not":"百货超市","target_formats":"美容美发","decoration_level":"毛坯","supporting_facility":"空调,热水器,宽带,厨具,露台/花园,储藏室/地下室,储藏室/地下室","house_example_id":43,"note":"无","leixing":"","bvalue":"","created_at":"2017-11-02T08:04:50.000Z","updated_at":"2017-11-02T08:04:50.000Z"}
     * imageresources : []
     */

    private RentShopHouseBean house;
    private List<ImageresourcesBean> imageresources;

    protected RentShopContants(Parcel in) {
        imageresources = in.createTypedArrayList(ImageresourcesBean.CREATOR);
    }

    public static final Creator<RentShopContants> CREATOR = new Creator<RentShopContants>() {
        @Override
        public RentShopContants createFromParcel(Parcel in) {
            return new RentShopContants(in);
        }

        @Override
        public RentShopContants[] newArray(int size) {
            return new RentShopContants[size];
        }
    };

    public static RentShopContants objectFromData(String str) {

        return new Gson().fromJson(str, RentShopContants.class);
    }

    public static RentShopContants objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), RentShopContants.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<RentShopContants> arrayRentShopContantsFromData(String str) {

        Type listType = new TypeToken<ArrayList<RentShopContants>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<RentShopContants> arrayRentShopContantsFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<RentShopContants>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(key), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public RentShopHouseBean getHouse() {
        return house;
    }

    public void setHouse(RentShopHouseBean house) {
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
        return "RentShopContants{" +
                "house=" + house +
                ", imageresources=" + imageresources +
                '}';
    }
}
