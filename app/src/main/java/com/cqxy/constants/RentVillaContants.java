package com.cqxy.constants;

import android.os.Parcel;
import android.os.Parcelable;

import com.cqxy.bean.RentVillaHouseBean;
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

public class RentVillaContants implements Parcelable{
    public static final String POSITION = "楼盘名称";
    public static final String HOUSE_PRICE = "价格";
    public static final String HOUSE_TYPE = "户型";
    public static final String HOUSE_AREA = "建筑面积";
    public static final String FLOOR = "楼层";
    public static final String LEASE_WAY = "出租方式";
    public static final String PAYMENT = "支付方式";
    public static final String DECORATION_LEVE = "装修程度";
    public static final String BUILT_TYPE = "建筑类别";
    public static final String BUILT_AGE = "建筑年代";
    public static final String VILLA_RENT = "villa_rent";

    public static final String NOTE = "备注";

    /**
     * house : {"leixing":"佣金","bvalue":"20%","sourcetype":"villa_rents","id":1,"position":"别墅租赁","rentornot":1,"rent":84845,"rent_type":null,"house_area":"望京","house_area_type":null,"lease_way":"整租","payment":"月付","house_type":"","floor":20,"floor_type":null,"built_age":2010,"built_type":"独栋","decoration_level":"毛坯","supporting_facility":"","house_example_id":35,"note":"","created_at":"2017-10-27T09:05:35.000Z","updated_at":"2017-10-27T09:05:35.000Z"}
     * imageresources : []
     */

    private RentVillaHouseBean house;
    private List<ImageresourcesBean> imageresources;

    protected RentVillaContants(Parcel in) {
        imageresources = in.createTypedArrayList(ImageresourcesBean.CREATOR);
    }

    public static final Creator<RentVillaContants> CREATOR = new Creator<RentVillaContants>() {
        @Override
        public RentVillaContants createFromParcel(Parcel in) {
            return new RentVillaContants(in);
        }

        @Override
        public RentVillaContants[] newArray(int size) {
            return new RentVillaContants[size];
        }
    };

    public static RentVillaContants objectFromData(String str) {

        return new Gson().fromJson(str, RentVillaContants.class);
    }

    public static RentVillaContants objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), RentVillaContants.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<RentVillaContants> arrayRentVillaContantsFromData(String str) {

        Type listType = new TypeToken<ArrayList<RentVillaContants>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<RentVillaContants> arrayRentVillaContantsFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<RentVillaContants>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(key), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public RentVillaHouseBean getHouse() {
        return house;
    }

    public void setHouse(RentVillaHouseBean house) {
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
        return "RentVillaContants{" +
                "house=" + house +
                ", imageresources=" + imageresources +
                '}';
    }
}
