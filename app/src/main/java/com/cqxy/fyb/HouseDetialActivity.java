package com.cqxy.fyb;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxy.adapter.BlurGlideImageLoader;
import com.cqxy.adapter.MyListAdapter;
import com.cqxy.adapter.NomalGlideImageLoader;
import com.cqxy.base.BaseUrl;
import com.cqxy.bean.HideLodingEvent;
import com.cqxy.bean.IsCooperation;
import com.cqxy.bean.RentOfficeHouseBean;
import com.cqxy.bean.RentResidentHouseBean;
import com.cqxy.bean.RentShopHouseBean;
import com.cqxy.bean.RentVillaHouseBean;
import com.cqxy.bean.SellOfficeHouseBean;
import com.cqxy.bean.SellResidentHouseBean;
import com.cqxy.bean.SellShopHouseBean;
import com.cqxy.bean.SellVillaHouseBean;
import com.cqxy.bean.ShowLoadingEvent;
import com.cqxy.constants.ImageresourcesBean;
import com.cqxy.constants.ModeConstants;
import com.cqxy.constants.RentOfficeContants;
import com.cqxy.constants.RentResidentConstants;
import com.cqxy.constants.RentShopContants;
import com.cqxy.constants.RentVillaContants;
import com.cqxy.constants.SellOfficeConstants;
import com.cqxy.constants.SellResidentConstants;
import com.cqxy.constants.SellShopConstants;
import com.cqxy.constants.SellVillaContants;
import com.cqxy.utils.ImageTask;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.UserInf;
import com.cqxy.view.DealDialog;
import com.cqxy.view.LoadingView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.youth.banner.Banner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

public class HouseDetialActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.housedetial_cooperate)
    Button housedetial_cooperate;
    @BindView(R.id.housedetail_back)
    ImageView housedetailBack;
    @BindView(R.id.housedetial_textview)
    TextView housedetialTextview;
    @BindView(R.id.housedetial_0)
    TextView housedetial0;
    @BindView(R.id.housedetial_time)
    TextView housedetialTime;
    @BindView(R.id.housedetial_houselocation)
    TextView housedetialHouselocation;
    @BindView(R.id.housedetial_houseprice)
    TextView housedetialHouseprice;
    @BindView(R.id.tv_supporting_facility)
    TextView housedetialSupporting;
    @BindView(R.id.housedetail_evaluate)
    TextView housedetailEvaluate;
    @BindView(R.id.housedetail_allevaluate)
    Button housedetailAllevaluate;
    @BindView(R.id.tv_note)
    TextView tvNote;
    @BindView(R.id.individual_certification)
    Button btnEdit;
    @BindView(R.id.btn_tel)
    Button btnTel;
    @BindView(R.id.btn_chat)
    Button btnChat;
    @BindView(R.id.ll_tel)
    LinearLayout llTel;
    private GridView gvDetails;
    private String mode;
    private Intent mIntent;
    private int intentFlag;
    private ArrayList<ImageresourcesBean> imageBeans = new ArrayList<>();
    private String TAG = "HouseDetialActivity";
    private int mWidth, mHeight;
    Banner banner;
    int houseexampleid;
    Parcelable parcelable;
    ArrayList<String> picPaths;
    LoadingView loadingView;

    boolean isrent;
    boolean ismaoboli;

    int touserid;

    //判断是否是当前自己发布的
    String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detial);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mWidth = getResources().getDisplayMetrics().widthPixels;
        loadingView = ((LoadingView) findViewById(R.id.loading_detail));
        mHeight = mWidth * 1 / 2;
        initDatas();
        initListener();
        intentFlag = mIntent.getFlags();
        phone = mIntent.getStringExtra("phone");
        houseexampleid = mIntent.getIntExtra("houseexampleid", 1);
        isrent = mIntent.getBooleanExtra("isrent", true);
        touserid = mIntent.getIntExtra("touserid", 1);
        ismaoboli = mIntent.getBooleanExtra("ismaoboli", true);
        btnTel.setText("电话号码："+phone);
        Log.d(TAG, "onCreate: intent.flag = " + intentFlag +
                ",\nphone = " + phone +
                ",\n mode = " + mode +
                ",\nhouseexampleid = " + houseexampleid +
                ",\nisrent = " + isrent +
                ",\ntouserid = " + touserid +
                ",\nismaoboli = " + ismaoboli);
        changeBtnText(intentFlag);
        checkRealation(touserid);
        final String url = BaseUrl.URL + mode + "/" + houseexampleid + ".json";
        Log.d("HouseDetailActivity", "onCreate: ----------url = " + url);
        ImageTask getHouseTask = new ImageTask(new ImageTask.IRequest() {
            @Override
            public Object doRequest() {
                return OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(HouseDetialActivity.this)
                        , UserInf.getUserPhonenum(HouseDetialActivity.this)
                        , url);
            }

            @Override
            public Object doPraseResult(Object object) {
                if (null != object) {
                    Log.d(TAG, "doPraseResult: ==============Parcelable====================strat");
                    String responseBody = (String) object;
                    parcelable = switchMode(responseBody, mode);
                    Log.d(TAG, "doPraseResult: ============Parcelable = " + parcelable.toString());
                    return parcelable;
                } else {
                    return null;
                }
            }
        }, new ImageTask.IRequestCallback() {
            @Override
            public void onSuccess(Object object) {
                //1.转化为对应实体类
                //2取实体类的值
                //3.获取图片路径，glide加载
                switch (mode) {
                    case ModeConstants.OFFICEBUILDING_RENT:
                        showOfficRent(object);
                        break;
                    case ModeConstants.OFFICEBUILDING_SELL:
                        showOfficSell(object);
                        break;
                    case ModeConstants.RESIDENCE_SELL:
                        showResidenceSell(object);
                        break;
                    case ModeConstants.RESIDENCE_RENT:
                        showResidenceRent(object);
                        break;
                    case ModeConstants.SHOP_RENT:
                        showShopRent(object);
                        break;
                    case ModeConstants.SHOP_SELL:
                        showShopSell(object);
                        break;
                    case ModeConstants.VILLA_RENT:
                        showVillaRent(object);
                        break;
                    case ModeConstants.VILLA_SELL:
                        showVillaSell(object);
                        break;
                }
            }

            @Override
            public void onError() {

            }
        });
        getHouseTask.execute();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ------------------start");
                Intent intent = new Intent(HouseDetialActivity.this, House_enterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("mode", mode);
                bundle.putBoolean("isrent", isrent);
                bundle.putInt("houseid", houseexampleid);
                Log.d(TAG, "jumpToEdit: -----Parcelable--------------mode = " + mode + "\nisrent = " + isrent + "\nhousebean = " + parcelable.toString());
                intent.putExtra("bundle", bundle);
                startActivity(intent);
                Log.d(TAG, "onClick: -----------------------------------end");
            }
        });
        it.execute();
    }

    private void checkRealation(int touserid) {
        Log.d(TAG, "checkRealation: touserid = " + touserid + "\nUserInf.getUserId() = " + UserInf.getUserId(this));
        if (touserid == UserInf.getUserId(this)) {
            Log.d(TAG, "checkRealation: -------------------------touserid == UseInf.getUserId(this) = true");
            setIsmaoboli(false);
            btnEdit.setVisibility(View.VISIBLE);
            housedetial_cooperate.setVisibility(View.GONE);
        } else {
            Log.d(TAG, "checkRealation: -------------------------touserid == UserInf.getUserId(this) = false");
        }
    }


    private void showVillaSell(Object object) {
        SellVillaContants sellVilla = (SellVillaContants) object;
        List<ImageresourcesBean> imageresources = sellVilla.getImageresources();
        SellVillaHouseBean house = sellVilla.getHouse();
        ArrayList<String> ss = new ArrayList<>();
        ss.add(house.getPosition());
        ss.add(house.getHouse_price() + "元");
        String created_at = house.getCreated_at();
        ss.add(created_at.substring(0, created_at.indexOf("T")));

        ss.add(house.getSupporting_facility());
        ss.add(house.getNote());

        ss.add(SellVillaContants.HOUSE_AREA + ": " + house.getHouse_area());
        ss.add(SellVillaContants.HOUSE_TYPE + ": " + house.getHouse_type());
        ss.add(SellVillaContants.FLOOR + ": " + house.getFloor());
        ss.add(SellVillaContants.BUILT_AGE + ": " + house.getBuilt_age());
        ss.add(SellVillaContants.BUILT_TYPE + ": " + house.getBuilt_type());
        ss.add(SellVillaContants.DECORATION_LEVE + ": " + house.getDecoration_level());
        String leixing = null == house.getLeixing() ? "" : house.getLeixing();
        String bvalue = null == house.getBvalue() ? "" : house.getBvalue();
        ss.add("盈利：" + leixing + bvalue);

        showDetailText(ss);
        showDetailImage(imageresources);
    }

    private void showVillaRent(Object object) {
        RentVillaContants rentVilla = (RentVillaContants) object;
        RentVillaHouseBean house = rentVilla.getHouse();
        List<ImageresourcesBean> imageresources = rentVilla.getImageresources();
        ArrayList<String> ss = new ArrayList<>();
        ss.add(house.getPosition());
        ss.add(house.getRent() + "元/月");
        String created_at = house.getCreated_at();
        ss.add(created_at.substring(0, created_at.indexOf("T")));

        ss.add(house.getSupporting_facility());
        ss.add(house.getNote());

        ss.add(RentVillaContants.HOUSE_AREA + ": " + house.getHouse_area());
        ss.add(RentVillaContants.LEASE_WAY + ": " + house.getLease_way());
        ss.add(RentVillaContants.PAYMENT + ": " + house.getPayment());
        ss.add(RentVillaContants.HOUSE_TYPE + ": " + house.getHouse_type());
        ss.add(RentVillaContants.FLOOR + ": " + house.getFloor());
        ss.add(RentVillaContants.BUILT_AGE + ": " + house.getBuilt_age());
        ss.add(RentVillaContants.BUILT_TYPE + ": " + house.getBuilt_type());
        ss.add(RentVillaContants.DECORATION_LEVE + ": " + house.getDecoration_level());
        String leixing = null == house.getLeixing() ? "" : house.getLeixing();
        String bvalue = null == house.getBvalue() ? "" : house.getBvalue();
        ss.add("盈利：" + leixing + bvalue);

        showDetailText(ss);
        showDetailImage(imageresources);
    }

    private void showShopSell(Object object) {
        SellShopConstants sellShop = (SellShopConstants) object;
        SellShopHouseBean house = sellShop.getHouse();
        List<ImageresourcesBean> imageresources = sellShop.getImageresources();
        ArrayList<String> ss = new ArrayList<>();
        ss.add(house.getPosition());
        ss.add(house.getHouse_price() + "元");
        String created_at = house.getCreated_at();
        ss.add(created_at.substring(0, created_at.indexOf("T")));

        ss.add(house.getSupporting_facility());
        ss.add(house.getNote());

        ss.add(SellShopConstants.HOUSE_AREA + ": " + house.getHouse_area());
        ss.add(SellShopConstants.PROPERTY_FEE + ": " + house.getProperty_fee());
        ss.add(SellShopConstants.FLOOR + ": " + house.getFloor());
        ss.add(SellShopConstants.DECORATION_LEVE + ": " + house.getDecoration_level());
        ss.add(SellShopConstants.SHOP_TYPE + ": " + house.getShop_type());
        ss.add(SellShopConstants.CEDING_OR_NOT + ": " + house.getCeding_or_not());
        ss.add(SellShopConstants.TARGET_FORMATS + ": " + house.getTarget_formats());
        String leixing = null == house.getLeixing() ? "" : house.getLeixing();
        String bvalue = null == house.getBvalue() ? "" : house.getBvalue();
        ss.add("盈利：" + leixing + bvalue);

        showDetailText(ss);
        showDetailImage(imageresources);
    }

    private void showShopRent(Object object) {
        RentShopContants rentShop = (RentShopContants) object;
        RentShopHouseBean house = rentShop.getHouse();
        List<ImageresourcesBean> imageresources = rentShop.getImageresources();
        ArrayList<String> ss = new ArrayList<>();
        ss.add(house.getPosition());
        ss.add(house.getRent() + "元/月");
        String created_at = house.getCreated_at();
        ss.add(created_at.substring(0, created_at.indexOf("T")));

        ss.add(house.getSupporting_facility());
        ss.add(house.getNote());

        ss.add(RentShopContants.HOUSE_AREA + ": " + house.getHouse_area());
        ss.add(RentShopContants.INCLUDING_PROPERTY_FEE_OR_NOT + ": " + house.getIncluding_property_fee_or_not());
        ss.add(RentShopContants.PAYMENT + ": " + house.getPayment());
        ss.add(RentShopContants.PROPERTY_FEE + ": " + house.getProperty_fee());
        ss.add(RentShopContants.FLOOR + ": " + house.getFloor());
        ss.add(RentShopContants.SHOP_STATING + ": " + house.getShop_stating());
        ss.add(RentShopContants.SHOP_TYPE + ": " + house.getShop_type());
        ss.add(RentShopContants.CEDING_OR_NOT + ": " + house.getCeding_or_not());
        ss.add(RentShopContants.TRANSFER_OR_NOT + ": " + house.getTransfer_or_not());
        ss.add(RentShopContants.TARGET_FORMATS + ": " + house.getTarget_formats());
        ss.add(RentShopContants.DECORATION_LEVEL + ": " + house.getDecoration_level());
        String leixing = null == house.getLeixing() ? "" : house.getLeixing();
        String bvalue = null == house.getBvalue() ? "" : house.getBvalue();
        ss.add("盈利：" + leixing + bvalue);

        showDetailText(ss);
        showDetailImage(imageresources);
    }

    private void showResidenceRent(Object object) {
        RentResidentConstants rentResident = (RentResidentConstants) object;
        RentResidentHouseBean house = rentResident.getHouse();
        List<ImageresourcesBean> imageresources = rentResident.getImageresources();
        ArrayList<String> ss = new ArrayList<>();
        ss.add(house.getPosition());
        ss.add(house.getRent() + "元/月");
        String created_at = house.getCreated_at();
        ss.add(created_at.substring(0, created_at.indexOf("T")));

        ss.add(house.getSupporting_facility());
        ss.add(house.getNote());

        ss.add(RentResidentConstants.LEASE_WAY + ": " + house.getLease_way());
        ss.add(RentResidentConstants.PAYMENT + ": " + house.getPayment());
        ss.add(RentResidentConstants.FLOOR + ": " + house.getFloor());
        ss.add(RentResidentConstants.ASPECT + ": " + house.getAspect());
        ss.add(RentResidentConstants.DECORATION_LEVE + ": " + house.getDecoration_level());
        ss.add(RentResidentConstants.HOUSE_TYPE + ": " + house.getHouse_type());
        ss.add(RentResidentConstants.HOUSE_AREA + ": " + house.getHouse_area());
        String leixing = null == house.getLeixing() ? "" : house.getLeixing();
        String bvalue = null == house.getBvalue() ? "" : house.getBvalue();
        ss.add("盈利：" + leixing + bvalue);

        showDetailText(ss);
        showDetailImage(imageresources);
    }

    private void showResidenceSell(Object object) {
        SellResidentConstants sellResident = (SellResidentConstants) object;
        SellResidentHouseBean house = sellResident.getHouse();
        List<ImageresourcesBean> imageresources = sellResident.getImageresources();
        ArrayList<String> ss = new ArrayList<>();
        ss.add(house.getPosition());
        ss.add(house.getHouse_price() + "元");
        String created_at = house.getCreated_at();
        ss.add(created_at.substring(0, created_at.indexOf("T")));

        ss.add(house.getSupporting_facility());
        ss.add(house.getNote());

        ss.add(SellResidentConstants.BUILT_AGE + ": " + house.getBuilt_age());
        ss.add(SellResidentConstants.FLOOR + ": " + house.getFloor());
        ss.add(SellResidentConstants.NATURE_OF_PROPERTY_RIGHT + ": " + house.getNature_of_property_right());
        ss.add(SellResidentConstants.BUILT_STRUCTURE + ": " + house.getBuilt_structure());
        ss.add(SellResidentConstants.ASPECT + ": " + house.getAspect());
        ss.add(SellResidentConstants.BUILT_TYPE + ": " + house.getBuilt_type());
        ss.add(SellResidentConstants.DECORATION_LEVE + ": " + house.getDecoration_level());
        ss.add(SellResidentConstants.HOUSE_TYPE + ": " + house.getHouse_type());
        ss.add(SellResidentConstants.HOUSE_AREA + ": " + house.getHouse_area());
        String leixing = null == house.getLeixing() ? "" : house.getLeixing();
        String bvalue = null == house.getBvalue() ? "" : house.getBvalue();
        ss.add("盈利：" + leixing + bvalue);

        showDetailText(ss);
        showDetailImage(imageresources);
    }

    private void showOfficSell(Object object) {
        SellOfficeConstants sellOffice = (SellOfficeConstants) object;
        SellOfficeHouseBean house = sellOffice.getHouse();
        List<ImageresourcesBean> imageresources = sellOffice.getImageresources();
        ArrayList<String> ss = new ArrayList<>();
        ss.add(house.getPosition());
        ss.add(house.getHouse_price() + "元");
        String created_at = house.getCreated_at();
        ss.add(created_at.substring(0, created_at.indexOf("T")));

        ss.add("");
        ss.add(house.getNote());

        ss.add(SellOfficeConstants.HOUSE_AREA + ": " + house.getHouse_area());
        ss.add(SellOfficeConstants.FLOOR + ": " + house.getFloor());
        ss.add(SellOfficeConstants.BUILT_AGE + ": " + house.getBuilt_age());
        ss.add(SellOfficeConstants.PROPERTY_FEE + ": " + house.getProperty_fee());
        ss.add(SellOfficeConstants.OFFICE_BUILDING_LEVEL + ": " + house.getOffice_building_level());
        ss.add(RentOfficeContants.HOUSE_AREA + ": " + house.getHouse_area());
        String leixing = null == house.getLeixing() ? "" : house.getLeixing();
        String bvalue = null == house.getBvalue() ? "" : house.getBvalue();
        ss.add("盈利：" + leixing + bvalue);

        showDetailText(ss);
        showDetailImage(imageresources);
    }

    private void showOfficRent(Object object) {
        RentOfficeContants rentOffice = (RentOfficeContants) object;
        RentOfficeHouseBean house = rentOffice.getHouse();
        List<ImageresourcesBean> imageresources = rentOffice.getImageresources();
        ArrayList<String> ss = new ArrayList<>();
        ss.add(house.getPosition());
        ss.add(house.getRent() + "元/月");
        String created_at = house.getCreated_at();
        ss.add(created_at.substring(0, created_at.indexOf("T")));

        ss.add("");
        ss.add(house.getNote());

        ss.add(RentOfficeContants.HOUSE_AREA + ": " + house.getHouse_area());
        ss.add(RentOfficeContants.INCLUDING_PROPERTY_FEE_OR_NOT + ": " + house.getIncluding_property_fee_or_not());
        ss.add(RentOfficeContants.PAYMENT + ": " + house.getPayment());
        ss.add(RentOfficeContants.PROPERTY_FEE + ": " + house.getProperty_fee());
        ss.add(RentOfficeContants.FLOOR + ": " + house.getFloor());
        ss.add(RentOfficeContants.OFFICE_BUILDING_LEVEL + ": " + house.getOffice_building_level());
        ss.add(RentOfficeContants.HOUSE_AREA + ": " + house.getHouse_area());
        String leixing = null == house.getLeixing() ? "" : house.getLeixing();
        String bvalue = null == house.getBvalue() ? "" : house.getBvalue();
        ss.add("盈利：" + leixing + bvalue);

        showDetailText(ss);
        showDetailImage(imageresources);
    }

    private void showDetailImage(final List<ImageresourcesBean> imageresources) {
        ArrayList<Integer> picUrls = null;
        Integer[] localPics = {R.mipmap.placeholder, R.mipmap.placeholder, R.mipmap.placeholder};
        if (null == imageresources || imageresources.size() == 0) {
            picUrls = new ArrayList<>(Arrays.asList(localPics));
            banner.setImages(picUrls)
                    .setImageLoader(new NomalGlideImageLoader())
                    .start();
            return;
        }
        picPaths = new ArrayList<>();
        for (int i = 0; i < imageresources.size(); i++) {
            String picurl = imageresources.get(i).getUrl();
            String url = BaseUrl.IMGURL + picurl.substring(0, picurl.indexOf("?"));
            Log.d(TAG, "showDetailImage: url = " + url);
            picPaths.add(url);
        }
        if (ismaoboli) {
            banner.setImages(picPaths)
                    .setImageLoader(new BlurGlideImageLoader())
                    .start();
        } else {
            banner.setImages(picPaths)
                    .setImageLoader(new NomalGlideImageLoader())
                    .start();
        }
    }

    //详细文字
    private void showDetailText(ArrayList<String> ss) {
        housedetialHouselocation.setText(ss.get(0));
        housedetialHouseprice.setText(ss.get(1));
        housedetialTime.setText(ss.get(2));
        String supportStr = addSpaceStr(ss.get(3)) == null ? "" : addSpaceStr(ss.get(3));
        housedetialSupporting.setText(supportStr);
        tvNote.setText(ss.get(4));

        ArrayList<String> subString = new ArrayList<String>(ss.subList(5, ss.size()));
        Log.d("-------", "run: strDetail.get(0) = " + ss.get(0) + ",\nget1" + ss.get(1) + ",\nget2" + supportStr + ",\nsubStrting.size = " + subString.size());
        MyListAdapter myListAdapter = new MyListAdapter(HouseDetialActivity.this, subString);
        gvDetails.setAdapter(myListAdapter);
        setGridViewHeight(gvDetails);
        myListAdapter.notifyDataSetChanged();
    }

    private String addSpaceStr(String supportStr) {
        String[] split = supportStr.split(",");
        StringBuffer sb = new StringBuffer();
        if (null != split || split.length != 0) {
            sb.append(split[0]);
            for (int i = 1; i < split.length; i++) {
                sb.append("   " + split[i]);
            }
        }
        return sb.toString();
    }

    private void changeBtnText(int intentFlag) {
        switch (intentFlag) {
            case 1:
                housedetial_cooperate.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.GONE);
                housedetial_cooperate.setText("寻求合作");
                break;
            case 3:
                Log.d(TAG, "changeBtnText: -----flag = 3 ====coo  =======setgone");
                btnEdit.setVisibility(View.INVISIBLE);
                llTel.setVisibility(View.VISIBLE);
                housedetial_cooperate.setVisibility(View.GONE);
                break;
            case 2:
                housedetial_cooperate.setText("与TA聊天");
                btnEdit.setVisibility(View.VISIBLE);
                housedetial_cooperate.setVisibility(View.GONE);
                break;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }


    private Parcelable switchMode(String responseBody, String mode) {
        Log.d(TAG, "switchMode: _____stringResult = " + responseBody + "__________mode = " + mode);
        imageBeans.clear();
        Parcelable parcelable = null;
        switch (mode) {
            case ModeConstants.OFFICEBUILDING_RENT:
                parcelable = RentOfficeContants.objectFromData(responseBody);
                break;
            case ModeConstants.OFFICEBUILDING_SELL:
                parcelable = SellOfficeConstants.objectFromData(responseBody);
                break;
            case ModeConstants.RESIDENCE_SELL:
                parcelable = SellResidentConstants.objectFromData(responseBody);
                break;
            case ModeConstants.RESIDENCE_RENT:
                parcelable = RentResidentConstants.objectFromData(responseBody);
                break;
            case ModeConstants.SHOP_RENT:
                parcelable = RentShopContants.objectFromData(responseBody);
                break;
            case ModeConstants.SHOP_SELL:
                parcelable = SellShopConstants.objectFromData(responseBody);
                break;
            case ModeConstants.VILLA_RENT:
                parcelable = RentVillaContants.objectFromData(responseBody);
                break;
            case ModeConstants.VILLA_SELL:
                parcelable = SellVillaContants.objectFromData(responseBody);
                break;
        }
        return parcelable;
    }


    private void initListener() {
        housedetailAllevaluate.setOnClickListener(this);
        housedetial_cooperate.setOnClickListener(this);
        btnTel.setOnClickListener(this);
        btnChat.setOnClickListener(this);
    }

    private void initItemListener(DealDialog dealDialog) {

        dealDialog.setChatListener(new DealDialog.itemInterface() {
            @Override
            public void setOnItemListener() {
                Log.d(TAG, "setOnItemListener: -------dealDialog----------setChatListener-----");
                startChating();
            }
        });
        dealDialog.setDealListener(new DealDialog.itemInterface() {
            @Override
            public void setOnItemListener() {
                Log.d(TAG, "setOnItemListener: ---------dealDialog-------------setDealListener-");
                addToCooperation(UserInf.getUserId(HouseDetialActivity.this));
            }
        });
    }

    private void addToCooperation(int userid) {

        FormBody formBody = new FormBody.Builder().add("user_id", userid + "").add("house_example_id", houseexampleid + "").build();
        String exctraurl = "";
        if (isrent) {
            exctraurl = "cooperation_houses";
            Log.d(TAG, exctraurl);
        } else {
            exctraurl = "cooperation_sellhouses";
        }
        OkHttpUtils.okhttpPostWithHead(UserInf.getUserToken(HouseDetialActivity.this),
                UserInf.getUserPhonenum(HouseDetialActivity.this),
                formBody, BaseUrl.URL + exctraurl, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: ---------e.getmessage = " + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseBody = response.body().string();
                        Log.d(TAG, "onResponse: ------------responseBody = " + responseBody);
                        EventBus.getDefault().post(new HideLodingEvent());
                    }

                });
    }

    private void startChating() {
        Intent intent2 = new Intent(HouseDetialActivity.this, ChatActivity.class);
        String phone = mIntent.getStringExtra("phone");
        if (phone.equals(UserInf.getUserPhonenum(HouseDetialActivity.this))) {
            Toast.makeText(HouseDetialActivity.this, "不可与自己聊天", Toast.LENGTH_SHORT).show();
        } else {
            try {
                //TODO:点击聊天同时添加到好友列表
                EMClient.getInstance().contactManager().addContact(phone, "");
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
            intent2.putExtra("phone", phone);
            startActivity(intent2);
        }
    }

    private void initDatas() {
        Log.d(TAG, "initDatas: ======================start");
        gvDetails = (GridView) findViewById(R.id.gv_details);
        mIntent = getIntent();
        mode = mIntent.getStringExtra("mode");
        if (mode.contains("sell")) {
            housedetialTextview.setText("买卖房源");
        } else {
            housedetialTextview.setText("租赁房源");
        }
        banner = (Banner) findViewById(R.id.banner);
        Log.d(TAG, "initDatas: ======================end");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.housedetial_cooperate:
                EventBus.getDefault().post(new ShowLoadingEvent());
                addToCooperation(UserInf.getUserId(HouseDetialActivity.this));
                break;

            case R.id.housedetail_allevaluate:
                Intent intent = new Intent(HouseDetialActivity.this, CommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("exampleid", houseexampleid);
                intent.putExtra("ids", bundle);
                startActivity(intent);
                break;
            case R.id.btn_chat:
                startChating();
                break;
            case R.id.btn_tel:
                callPhone();
                break;
        }
    }

    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    ImageTask it = new ImageTask(new ImageTask.IRequest() {
        @Override
        public Object doRequest() {
            String responseBody = null;
            if (mode.contains("sell")) {
                responseBody = OkHttpUtils.okhttpGet(BaseUrl.URL + "cooperation_sellhouses/checkexist/"
                        + UserInf.getUserId(HouseDetialActivity.this) + "/" + houseexampleid + ".json");
            } else {
                responseBody = OkHttpUtils.okhttpGet(BaseUrl.URL + "cooperation_houses/checkexist/"
                        + UserInf.getUserId(HouseDetialActivity.this) + "/" + houseexampleid + ".json");
            }
            Log.d(TAG, "doRequest: ---------------------responsebody = " + responseBody);
            return responseBody;
        }

        @Override
        public Object doPraseResult(Object object) {
            return IsCooperation.objectFromData(((String) object));
        }
    }, new ImageTask.IRequestCallback() {
        @Override
        public void onSuccess(Object object) {
            IsCooperation isCooperation = (IsCooperation) object;
            mStatus = isCooperation.getStatus();
            if (mStatus == 0) {//是已经合作房源
                housedetial_cooperate.setVisibility(View.GONE);
                llTel.setVisibility(View.VISIBLE);
                ismaoboli = false;
                if (null != picPaths)
                    banner.setImages(picPaths)
                            .setImageLoader(new NomalGlideImageLoader())
                            .start();
            } else if (mStatus == -1) {//没有合作房源
                housedetial_cooperate.setText("寻求合作");
            }
        }

        @Override
        public void onError() {

        }
    });

    int mStatus;

    //去主线程中更改UI
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ShowLoadingEvent event) {
        Log.d(TAG, "onEventMainThread: -----------------------");
        loadingView.startAnimation();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(HideLodingEvent event) {
        Log.d(TAG, "onEventMainThread: ----------------------");
        loadingView.stopAnimation();
        llTel.setVisibility(View.VISIBLE);
        housedetial_cooperate.setVisibility(View.GONE);
        btnTel.setText("联系电话：" + phone);
        if (null != picPaths)
            banner.setImages(picPaths)
                    .setImageLoader(new NomalGlideImageLoader())
                    .start();
    }

    @NonNull
    private DealDialog showDealDialog() {
        DealDialog dealDialog = new DealDialog(this);
        dealDialog.showDialog();
        return dealDialog;
    }


    @OnClick(R.id.housedetail_back)
    public void onViewClicked() {
        this.finish();
    }


    public void setGridViewHeight(GridView gridview) {
        // 获取gridview的adapter
        ListAdapter listAdapter = gridview.getAdapter();
        if (listAdapter == null) {
            Log.d(TAG, "setGridViewHeight: -----lsitadapter = null");
            return;
        }
        // 固定列宽，有多少列
        int numColumns = gridview.getNumColumns(); //2
        int totalHeight = 0;
        int totalverticalSpacing = 0;
        // 计算每一列的高度之和
        int count = listAdapter.getCount();
        Log.d(TAG, "setGridViewHeight:  ===============count = " + count);
        for (int i = 0; i < count; i += numColumns) {
            // 获取gridview的每一个item
            View listItem = listAdapter.getView(i, null, gridview);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
            totalverticalSpacing += gridview.getVerticalSpacing();
        }
        // 获取gridview的布局参数
        ViewGroup.LayoutParams params = gridview.getLayoutParams();
        params.height = totalHeight + totalverticalSpacing + gridview.getPaddingTop() + gridview.getPaddingBottom();
        gridview.setLayoutParams(params);
    }

    public boolean ismaoboli() {
        return ismaoboli;
    }

    public void setIsmaoboli(boolean ismaoboli) {
        this.ismaoboli = ismaoboli;
    }
}
