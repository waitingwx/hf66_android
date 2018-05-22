package com.cqxy.constants;

import android.os.Parcel;
import android.os.Parcelable;

import com.cqxy.bean.RentResidentHouseBean;
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

public class RentResidentConstants implements Parcelable{
    public static final String RESIDENCE_RENT = "residence_rent";
    public static final String POSITION = "楼盘名称";
    public static final String HOUSE_PRICE = "价格";
    public static final String HOUSE_TYPE = "户型";
    public static final String HOUSE_AREA = "建筑面积";
    public static final String FLOOR = "楼层";
    public static final String ASPECT = "朝向";
    public static final String LEASE_WAY = "出租方式";
    public static final String PAYMENT = "支付方式";
    public static final String DECORATION_LEVE = "装修程度";
    public static final String BUILT_TYPE = "建筑类别";
    public static final String BUILT_STRUCTURE = "结构";
    public static final String NATURE_OF_PROPERTY_RIGHT = "产权性质";
    public static final String SUPPORTING_FACILITY = "配套设施";
    public static final String BUILT_AGE = "建筑年代";
    public static final String RESIDENCE_SELLING = "residence_selling";
    public static final String NOTE = "备注";


    /**
     * house : {"sourcetype":"residence_rents","id":2,"position":"黑的呵护","rent":8484540,"rentornot":1,"rent_type":null,"house_area":"","house_area_type":null,"lease_way":"整租","payment":"月付","house_type":"0室0厅0卫0厨0阳","floor":0,"floor_type":null,"aspect":"东南","decoration_level":"毛坯","leixing":"佣金","bvalue":"20%","supporting_facility":"有线电视,宽带,电话,煤气/天然气,车库/车位,车库/车位","house_example_id":37,"note":"没啥注释","created_at":"2017-10-27T12:36:33.000Z","updated_at":"2017-10-27T12:36:33.000Z"}
     * imageresources : []
     */

    private RentResidentHouseBean house;
    private List<ImageresourcesBean> imageresources;

    protected RentResidentConstants(Parcel in) {
        imageresources = in.createTypedArrayList(ImageresourcesBean.CREATOR);
    }

    public static final Creator<RentResidentConstants> CREATOR = new Creator<RentResidentConstants>() {
        @Override
        public RentResidentConstants createFromParcel(Parcel in) {
            return new RentResidentConstants(in);
        }

        @Override
        public RentResidentConstants[] newArray(int size) {
            return new RentResidentConstants[size];
        }
    };

    public static RentResidentConstants objectFromData(String str) {

        return new Gson().fromJson(str, RentResidentConstants.class);
    }

    public static RentResidentConstants objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(key), RentResidentConstants.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<RentResidentConstants> arrayRentResidentConstantsFromData(String str) {

        Type listType = new TypeToken<ArrayList<RentResidentConstants>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<RentResidentConstants> arrayRentResidentConstantsFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<RentResidentConstants>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(key), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public RentResidentHouseBean getHouse() {
        return house;
    }

    public void setHouse(RentResidentHouseBean house) {
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
        return "RentResidentConstants{" +
                "house=" + house +
                ", imageresources=" + imageresources +
                '}';
    }
}
