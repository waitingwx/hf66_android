package com.cqxy.constants;

import android.os.Parcel;
import android.os.Parcelable;

import com.cqxy.bean.SellVillaHouseBean;
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

public class SellVillaContants implements Parcelable{
    public static final String POSITION = "楼盘名称";
    public static final String HOUSE_PRICE = "价格";
    public static final String HOUSE_TYPE = "户型";
    public static final String HOUSE_AREA = "建筑面积";
    public static final String ASPECT = "朝向";
    public static final String FLOOR = "楼层";
    public static final String LEASE_WAY = "出租方式";
    public static final String PAYMENT = "支付方式";
    public static final String DECORATION_LEVE = "装修程度";
    public static final String BUILT_TYPE = "建筑类别";
    public static final String VILLA_SELL = "villa_selling";
    public static final String BUILT_AGE = "建筑年代";


    /**
     * house : {"leixing":"佣金","bvalue":"2%","sourcetype":"villa_sellings","id":3,"position":"别墅买卖1","rentornot":0,"house_price":28555900,"house_price_type":null,"house_area":8855,"aspect":"","house_area_type":null,"house_type":"0室0厅0卫0厨0阳","floor":10,"floor_type":null,"decoration_level":"毛坯","built_type":"独栋","supporting_facility":"有线电视,煤气/天然气,暖气,露台/花园,微波炉,微波炉","adding_proxy":"","house_example_id":28,"note":"","built_age":2010,"created_at":"2017-10-27T08:36:32.000Z","updated_at":"2017-10-27T08:36:32.000Z"}
     * imageresources : []
     */

    private SellVillaHouseBean house;
    private List<ImageresourcesBean> imageresources;

    protected SellVillaContants(Parcel in) {
        imageresources = in.createTypedArrayList(ImageresourcesBean.CREATOR);
    }

    public static final Creator<SellVillaContants> CREATOR = new Creator<SellVillaContants>() {
        @Override
        public SellVillaContants createFromParcel(Parcel in) {
            return new SellVillaContants(in);
        }

        @Override
        public SellVillaContants[] newArray(int size) {
            return new SellVillaContants[size];
        }
    };

    public static SellVillaContants objectFromData(String str) {

        return new Gson().fromJson(str, SellVillaContants.class);
    }

    public static SellVillaContants objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), SellVillaContants.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<SellVillaContants> arraySellVillaContantsFromData(String str) {

        Type listType = new TypeToken<ArrayList<SellVillaContants>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<SellVillaContants> arraySellVillaContantsFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<SellVillaContants>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(key), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public SellVillaHouseBean getHouse() {
        return house;
    }

    public void setHouse(SellVillaHouseBean house) {
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
        return "SellVillaContants{" +
                "house=" + house +
                ", imageresources=" + imageresources +
                '}';
    }
}
