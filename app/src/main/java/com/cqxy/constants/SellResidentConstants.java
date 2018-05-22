package com.cqxy.constants;

import android.os.Parcel;
import android.os.Parcelable;

import com.cqxy.bean.SellResidentHouseBean;
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

public class SellResidentConstants implements Parcelable{
    public static final String POSITION = "楼盘名称";
    public static final String HOUSE_PRICE = "价格";
    public static final String HOUSE_TYPE = "户型";
    public static final String HOUSE_AREA = "建筑面积";
    public static final String FLOOR = "楼层";
    public static final String ASPECT = "朝向";
    public static final String DECORATION_LEVE = "装修程度";
    public static final String BUILT_TYPE = "建筑类别";
    public static final String BUILT_STRUCTURE = "结构";
    public static final String NATURE_OF_PROPERTY_RIGHT = "产权性质";
    public static final String SUPPORTING_FACILITY = "配套设施";
    public static final String BUILT_AGE = "建筑年代";
    public static final String RESIDENCE_SELLING = "residence_selling";
    public static final String NOTE = "备注";

    /**
     * house : {"id":10,"leixing":"","bvalue":"","position":"住宅录入","sourcetype":"residence_sellings","house_price":1231620,"rentornot":0,"house_price_type":null,"house_area":54545,"house_area_type":null,"house_type":"0室0厅0卫0厨0阳","floor":20,"floor_type":null,"aspect":"东南东南","decoration_level":"毛坯","built_type":"普通住宅","built_structure":"平层","built_age":2010,"nature_of_property_right":"商品房","supporting_facility":"","adding_proxy":"","house_example_id":39,"note":"无","created_at":"2017-10-31T06:14:19.000Z","updated_at":"2017-10-31T06:14:19.000Z"}
     * imageresources : []
     */

    private SellResidentHouseBean house;
    private List<ImageresourcesBean> imageresources;

    protected SellResidentConstants(Parcel in) {
        imageresources = in.createTypedArrayList(ImageresourcesBean.CREATOR);
    }

    public static final Creator<SellResidentConstants> CREATOR = new Creator<SellResidentConstants>() {
        @Override
        public SellResidentConstants createFromParcel(Parcel in) {
            return new SellResidentConstants(in);
        }

        @Override
        public SellResidentConstants[] newArray(int size) {
            return new SellResidentConstants[size];
        }
    };

    public static SellResidentConstants objectFromData(String str) {

        return new Gson().fromJson(str, SellResidentConstants.class);
    }

    public static SellResidentConstants objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), SellResidentConstants.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<SellResidentConstants> arraySellResidentConstantsFromData(String str) {

        Type listType = new TypeToken<ArrayList<SellResidentConstants>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<SellResidentConstants> arraySellResidentConstantsFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<SellResidentConstants>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(key), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public SellResidentHouseBean getHouse() {
        return house;
    }

    public void setHouse(SellResidentHouseBean house) {
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
        return "SellResidentConstants{" +
                "house=" + house +
                ", imageresources=" + imageresources +
                '}';
    }
}
