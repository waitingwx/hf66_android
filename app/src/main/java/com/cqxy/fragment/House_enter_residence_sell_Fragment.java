package com.cqxy.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqxy.base.BaseUrl;
import com.cqxy.bean.HideLodingEvent;
import com.cqxy.bean.SellResidentHouseBean;
import com.cqxy.bean.ShowLoadingEvent;
import com.cqxy.constants.FindHouseContants;
import com.cqxy.constants.SellResidentConstants;
import com.cqxy.fyb.ChooseActivity;
import com.cqxy.fyb.FindHouseActivity;
import com.cqxy.fyb.House_enterActivity;
import com.cqxy.fyb.R;
import com.cqxy.utils.GetImgFromAlbum;
import com.cqxy.utils.ImageTask;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.TextContentUtils;
import com.cqxy.utils.UserInf;
import com.cqxy.utils.YingLiUtils;
import com.cqxy.view.DropListView;
import com.cqxy.view.MyWheelViewDialog;
import com.cqxy.view.SupportChectBox;
import com.nanchen.compresshelper.CompressHelper;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/9/12.
 */

public class House_enter_residence_sell_Fragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, DropListView.LoadChooseDataListener {
    //调用系统相册-选择图片
    protected static final int REQUEST_CODE_LOCAL = 1;
    private EditText[] allEdit;
    @BindView(R.id.fragment_residence_sell_houseprice)
    EditText etPrice;
    @BindView(R.id.fragment_residence_sell_housesize)
    EditText etSize;
    @BindView(R.id.fragment_residence_sell_housetype_text)
    TextView fragmentResidenceSellHousetypeText;
    @BindView(R.id.fragment_residence_sell_housetype)
    ImageView fragmentResidenceSellHousetype;
    @BindView(R.id.fragment_residence_sell_buildage)
    EditText etAge;
    @BindView(R.id.fragment_residence_sell_orientation_text)
    TextView fragmentResidenceSellOrientationText;
    @BindView(R.id.fragment_residence_sell_orientation)
    ImageView fragmentResidenceSellOrientation;

    @BindView(R.id.fragment_residence_sell_yongjin)
    TextView fragmentResidenceSellYongjin;
    @BindView(R.id.fragment_residence_sell_jingde)
    TextView fragmentResidenceSellJingde;
    @BindView(R.id.fragment_residence_sell_fanfei)
    TextView fragmentResidenceSellFanfei;
    @BindView(R.id.fragment_residence_sell_commit)
    Button fragmentResidenceSellCommit;
    @BindView(R.id.fragment_residence_sell_proxyimg1)
    ImageView fragmentResidenceSellProxyimg1;
    @BindView(R.id.fragment_residence_sell_proxyimg2)
    ImageView fragmentResidenceSellProxyimg2;
    @BindView(R.id.fragment_residence_sell_houseimg1)
    ImageView fragmentResidenceSellHouseimg1;
    @BindView(R.id.fragment_residence_sell_houseimg2)
    ImageView fragmentResidenceSellHouseimg2;
    @BindView(R.id.fragment_residence_sell_houseimg3)
    ImageView fragmentResidenceSellHouseimg3;
    @BindView(R.id.fragment_residence_sell_houseimg4)
    ImageView fragmentResidenceSellHouseimg4;
    @BindView(R.id.fragment_residence_sell_houseimg5)
    ImageView fragmentResidenceSellHouseimg5;
    @BindView(R.id.fragment_residence_sell_houseimg6)
    ImageView fragmentResidenceSellHouseimg6;
    @BindView(R.id.fragment_residence_sell_houseimg7)
    ImageView fragmentResidenceSellHouseimg7;
    @BindView(R.id.fragment_residence_sell_houseimg8)
    ImageView fragmentResidenceSellHouseimg8;
    @BindView(R.id.fragment_residence_sell_floor)
    ImageView fragmentResidenceSellFloor;
    @BindView(R.id.fragment_residence_sell_floor_text)
    TextView fragmentResidenceSellFloorText;
    @BindView(R.id.fragment_residence_sell_support)
    ImageView fragmentResidenceSellSupport;
    @BindView(R.id.fragment_residence_sell_note)
    EditText etNote;
    @BindView(R.id.fragment_residence_sell_decoration)
    AppCompatSpinner fragmentResidenceSellDecoration;
    @BindView(R.id.fragment_residence_sell_buildingstructure)
    AppCompatSpinner fragmentResidenceSellBuildingstructure;
    @BindView(R.id.fragment_residence_sell_propertynature)
    AppCompatSpinner fragmentResidenceSellPropertynature;
    @BindView(R.id.fragment_residence_sell_buildingcategory)
    AppCompatSpinner fragmentResidenceSellBuildingcategory;
    @BindView(R.id.fragment_residence_sell_yingli)
    EditText etYingli;
    @BindView(R.id.fragment_residence_sell_unit)
    TextView fragmentResidenceSellUnit;

    private TextView tvXiaoqu;

    private EditText etDetail;

    private House_enterActivity act;
    private Unbinder unbinder;
    private int index = 0;
    private ImageView[] imgs;
    private SupportChectBox supportChectBox;
    private String TAG = "residence_sell";


    private String decoration_level, builtstructure, nature_ofpropertyright, builttype;
    private String type;
    private ImageView[] selectphotos;

    private int rentornot;
    private TextView[] textViews = new TextView[3];
    private String btype = "佣金";

    private int shangQuanId;
    private FrameLayout flChoose;
    private SellResidentConstants sellResidentConstants;
    private int houseid;
    private boolean isedit = false;
    private int xiaoquid;
    private String result;

    public SellResidentConstants getSellResidentConstants() {
        return sellResidentConstants;
    }

    public void setSellResidentConstants(SellResidentConstants sellResidentConstants) {
        this.sellResidentConstants = sellResidentConstants;
    }

    public int getShangQuanId() {
        return shangQuanId;
    }

    public void setShangQuanId(int shangQuanId) {
        this.shangQuanId = shangQuanId;
    }

    public House_enter_residence_sell_Fragment(String type, int rentornot) {
        this.type = type;
        this.rentornot = rentornot;
    }

    public House_enter_residence_sell_Fragment(String type, int rentornot, int houseid, boolean isedit) {
        this.type = type;
        this.rentornot = rentornot;
        this.houseid = houseid;
        this.isedit = isedit;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: =================================");
        Bundle arguments = getArguments();
        if (null == arguments)
            return;
        houseid = arguments.getInt("houseid");
        type = arguments.getString("type");
        isedit = arguments.getBoolean("isedit");
        Log.d(TAG, "onCreate: =================houseid = " + houseid + "type = " + type + ",isedit =" + isedit);
        if (isedit) {
            Log.d(TAG, "onCreate: --------------------post");
            EventBus.getDefault().post(new ShowLoadingEvent());
        }
    }

    ImageTask it = new ImageTask(new ImageTask.IRequest() {
        @Override
        public Object doRequest() {
            return OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(act), UserInf.getUserPhonenum(act), BaseUrl.URL + type + "/" + houseid + ".json");
        }

        @Override
        public Object doPraseResult(Object object) {
            String string = (String) object;
            Log.d(TAG, "doPraseResult: ---------------------string = " + string);
            sellResidentConstants = SellResidentConstants.objectFromData(string);
            return sellResidentConstants;
        }
    }, new ImageTask.IRequestCallback() {
        @Override
        public void onSuccess(Object object) {
            Log.d(TAG, "onSuccess: ---------------------");
            showText();
            EventBus.getDefault().post(new HideLodingEvent());
        }

        @Override
        public void onError() {

        }
    });


    private void showText() {
        Log.d(TAG, "showText: =======================");
        if (null == sellResidentConstants)
            return;
        SellResidentHouseBean house = sellResidentConstants.getHouse();
        Log.d(TAG, "showText: house.tostring = " + house.toString());
        String house_price = 0 == house.getHouse_price() ? "" : house.getHouse_price() + "";
        String house_area = 0 == house.getHouse_area() ? "" : house.getHouse_area() + "";
        String built_age = 0 == house.getBuilt_age() ? "" : house.getBuilt_age() + "";
        String note = null == house.getNote() ? "" : house.getNote();
        String position = null == house.getPosition() ? "" : house.getPosition();
        String housedetail = null == house.getDetialedaddress() ? "" : house.getDetialedaddress();

        tvXiaoqu.setText(position);
        etDetail.setText(housedetail);
        if(null == etPrice)
            etPrice = (EditText) act.findViewById(R.id.fragment_residence_sell_houseprice);
        etPrice.setText(house_price);
        etSize.setText(house_area);
        etAge.setText(built_age);
        etNote.setText(note);
    }


    public House_enter_residence_sell_Fragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.act = (House_enterActivity) context;
        Log.d(TAG, "onAttach: --------------");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.house_enter_fragment_residence_sell_layout, null);
        Log.d(TAG, "onCreateView: ========================");
        flChoose = ((FrameLayout) inflate.findViewById(R.id.fl_choose));
        unbinder = ButterKnife.bind(this, inflate);
        tvXiaoqu = inflate.findViewById(R.id.tv_xiaoqu);
        etDetail = inflate.findViewById(R.id.et_detail_address);
        Log.d(TAG, "onCreateView: ------------------------------------------unbinder");
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: =====================");
        allEdit = new EditText[]{etDetail, etPrice, etSize, etAge, etYingli};

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: =====================");
        initselectimgs();
        initTextViews();
        initImgListener();
        if (isedit)
            it.execute();
    }


    private void initTextViews() {
        textViews[0] = fragmentResidenceSellYongjin;
        textViews[1] = fragmentResidenceSellJingde;
        textViews[2] = fragmentResidenceSellFanfei;


    }

    private void initselectimgs() {
        selectphotos = new ImageView[]{fragmentResidenceSellHouseimg1, fragmentResidenceSellHouseimg2, fragmentResidenceSellHouseimg3,
                fragmentResidenceSellHouseimg4, fragmentResidenceSellHouseimg5, fragmentResidenceSellHouseimg6,
                fragmentResidenceSellHouseimg7, fragmentResidenceSellHouseimg8};
    }

    private void initImgListener() {
        imgs = new ImageView[]{fragmentResidenceSellProxyimg1, fragmentResidenceSellProxyimg2, fragmentResidenceSellHouseimg1, fragmentResidenceSellHouseimg2, fragmentResidenceSellHouseimg3, fragmentResidenceSellHouseimg4, fragmentResidenceSellHouseimg5, fragmentResidenceSellHouseimg6, fragmentResidenceSellHouseimg7, fragmentResidenceSellHouseimg8};
        for (int i = 0; i < imgs.length; i++) {
            imgs[i].setOnClickListener(this);

        }
        fragmentResidenceSellOrientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyWheelViewDialog wheelViewDialog = new MyWheelViewDialog(act, MyWheelViewDialog.ORIENTATIONS);
                wheelViewDialog.initWheel1(act, MyWheelViewDialog.ORIENTATIONS);
                wheelViewDialog.showWheelDialog();
                wheelViewDialog.InitWheelViewCommit(fragmentResidenceSellOrientationText, MyWheelViewDialog.ORIENTATIONS);
            }
        });
        fragmentResidenceSellSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (supportChectBox == null) {
                    supportChectBox = new SupportChectBox(act);
                }
                supportChectBox.showDialog();

            }
        });
        fragmentResidenceSellHousetype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyWheelViewDialog wheelViewDialog = new MyWheelViewDialog(act, MyWheelViewDialog.HOUSETYPE);
                wheelViewDialog.initWheel1(act, MyWheelViewDialog.HOUSETYPE);
                wheelViewDialog.showWheelDialog();
                wheelViewDialog.InitWheelViewCommit(fragmentResidenceSellHousetypeText, MyWheelViewDialog.HOUSETYPE);
            }
        });


        fragmentResidenceSellDecoration.setOnItemSelectedListener(this);
        fragmentResidenceSellBuildingstructure.setOnItemSelectedListener(this);
        fragmentResidenceSellBuildingcategory.setOnItemSelectedListener(this);
        fragmentResidenceSellPropertynature.setOnItemSelectedListener(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fragment_residence_sell_commit,
            R.id.fragment_residence_sell_yongjin,
            R.id.fragment_residence_sell_jingde,
            R.id.fragment_residence_sell_fanfei,
            R.id.tv_xiaoqu})
    public void onViewClicked(View view) {
        int k = 0;
        switch (view.getId()) {
            case R.id.fragment_residence_sell_commit:
                if (isedit) {
                    putChanges();
                    return;
                }
                Log.d(TAG, "onViewClicked========================start commit");
                for (int i = 0; i < allEdit.length; i++) {
                    Log.d(TAG, "onViewClicked: -----------------i = " + i);
                    if (allEdit[i].getText().length() == 0) {
                        allEdit[i].setHintTextColor(getContext().getColor(R.color.colorRed));
                        allEdit[i].setHint("此字段不能为空");
                        allEdit[i].requestFocus();
                        return;
                    }
                }
                EventBus.getDefault().post(new ShowLoadingEvent());
                String housedetail = TextContentUtils.getViewContent(etDetail);
                String position = result;
                String houseprice = TextContentUtils.getViewContent(etPrice);
                String housearea = TextContentUtils.getViewContent(etSize);
                String housetype = TextContentUtils.getViewContent(fragmentResidenceSellHousetypeText);
                String floor = TextContentUtils.getViewContent(fragmentResidenceSellFloorText);

                String supporting;
                if (supportChectBox == null) {
                    supporting = "";
                } else {
                    supporting = supportChectBox.getSelectText();
                }
                int userid = UserInf.getUserId(act);
                String aspect = TextContentUtils.getViewContent(fragmentResidenceSellOrientationText);
                String note = TextContentUtils.getViewContent(etNote);
                String bvalue = TextContentUtils.getViewContent(etYingli) + TextContentUtils.getViewContent(fragmentResidenceSellUnit);
                String builtage = TextContentUtils.getViewContent(etAge);
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                addListImages(builder);
                builder.addFormDataPart("position", position)
                        .addFormDataPart("house_price", houseprice)
                        .addFormDataPart("house_area", housearea)
                        .addFormDataPart("built_structure", builtstructure)
                        .addFormDataPart("built_type", builttype)
                        .addFormDataPart("house_type", housetype)
                        .addFormDataPart("floor", floor)
                        .addFormDataPart("decoration_level", decoration_level)
                        .addFormDataPart("supporting_facility", supporting)
                        .addFormDataPart("user_id", userid + "")
                        .addFormDataPart("aspect", aspect)
                        .addFormDataPart("note", note)
                        .addFormDataPart("nature_of_property_right", nature_ofpropertyright)
                        .addFormDataPart("built_age", builtage)
                        .addFormDataPart("sourcetype", type)
                        .addFormDataPart("rentornot", rentornot + "")
                        .addFormDataPart("leixing", btype + "")
                        .addFormDataPart("bvalue", bvalue)
                        .addFormDataPart("xiaoquid", xiaoquid + "")
                        .addFormDataPart("detailedaddress", housedetail);
                MultipartBody mpb = builder.build();
                Log.d(TAG, "onViewClicked: type = " + type + "-----rentornot" + rentornot);
                Log.d(TAG, "onViewClicked: ------------" + UserInf.getUserToken(act) + ",num = " + UserInf.getUserPhonenum(act));
                OkHttpUtils.okhttpPostWithHead(UserInf.getUserToken(act),
                        UserInf.getUserPhonenum(act),
                        mpb, BaseUrl.URL + "residence_sellings", new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.e(TAG, "e.tostring = " + e.toString());
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                EventBus.getDefault().post(new HideLodingEvent());
                                String responseBody = response.body().string();
                                Log.e(TAG, "responsebody = " + responseBody);
                                Intent mIntent = new Intent(act, FindHouseActivity.class);
                                mIntent.putExtra("flag", FindHouseContants.RESIDENCE_SELL);
                                startActivity(mIntent);
                                act.finish();
                            }
                        });
                break;
            case R.id.fragment_residence_sell_yongjin:
                k = 0;
                btype = "佣金";
                break;
            case R.id.fragment_residence_sell_jingde:
                k = 1;
                btype = "净得";
                break;
            case R.id.fragment_residence_sell_fanfei:
                k = 2;
                btype = "返费";
                break;
            case R.id.tv_xiaoqu:
                if (isedit)
                    return;
                Intent intent = new Intent(act, ChooseActivity.class);
                startActivityForResult(intent, 0x001);
                break;
        }
        if (getId() != R.id.fragment_residence_sell_commit) {
            YingLiUtils.selectTextViews(act, textViews, k, etYingli, fragmentResidenceSellUnit);
        }

    }


    private void putChanges() {
        FormBody.Builder builder = new FormBody.Builder();
        SellResidentHouseBean house = sellResidentConstants.getHouse();
        String houseprice = TextContentUtils.getViewContent(etPrice);
        if (house.getHouse_price() + "" != houseprice && houseprice != "")
            builder.add("residence_selling[house_price]", houseprice + "");

        String housedetail = TextContentUtils.getViewContent(etDetail);
        if (house.getDetialedaddress() + "" != housedetail && "" != housedetail)
            builder.add("residence_selling[detialedaddress]", housedetail);

        String housearea = TextContentUtils.getViewContent(etSize);
        if (house.getHouse_area() + "" != housearea && "" != housearea)
            builder.add("residence_selling[house_area]", housearea + "");

        String housetype = TextContentUtils.getViewContent(fragmentResidenceSellHousetypeText);
        if (house.getHouse_type() + "" != housetype && "" != housetype)
            builder.add("residence_selling[house_type]", housetype);

        String floor = TextContentUtils.getViewContent(fragmentResidenceSellFloorText);
        if (house.getFloor() + "" != floor && "" != floor)
            builder.add("residence_selling[floor]", floor);
        String supporting;
        if (supportChectBox == null) {
            supporting = "";
        } else {
            supporting = supportChectBox.getSelectText();
        }
        if (house.getSupporting_facility() + "" != supporting && "" != supporting)
            builder.add("residence_selling[supporting_facility]", supporting);

        String aspect = TextContentUtils.getViewContent(fragmentResidenceSellOrientationText);
        if (house.getAspect() + "" != aspect && "" != aspect)
            builder.add("residence_selling[aspect]", aspect);

        String note = TextContentUtils.getViewContent(etNote);
        if (house.getNote() + "" != note && "" != note)
            builder.add("residence_selling[note]", note);

        String bvalue = TextContentUtils.getViewContent(etYingli) + TextContentUtils.getViewContent(fragmentResidenceSellUnit);
        Log.d(TAG, "putChanges: 0--------bvalue = " + bvalue + ",house.getbvalue = " + house.getBvalue());
        if (house.getBvalue() + "" != bvalue && "%" != bvalue && "元" != bvalue)
            builder.add("residence_selling[bvalue]", bvalue);

        String builtage = TextContentUtils.getViewContent(etAge);
        if (house.getBuilt_age() + "" != builtage && "" != builtage)
            builder.add("residence_selling[built_age]", builtage);

        if (house.getLeixing() + "" != btype)
            builder.add("residence_selling[leixing]", btype + "");

        FormBody formBody = builder.build();
        Log.d(TAG, "onViewClicked: type = " + type + "-----rentornot" + rentornot);
        Log.d(TAG, "onViewClicked: ------------" + UserInf.getUserToken(act) + ",num = " + UserInf.getUserPhonenum(act));
        OkHttpUtils.okhttpPut(BaseUrl.URL + "residence_sellings/" + houseid, formBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG, response.body().string());
                Intent mIntent = new Intent(act, FindHouseActivity.class);
                mIntent.putExtra("flag", FindHouseContants.RESIDENCE_SELL);
                startActivity(mIntent);
                act.finish();
            }
        });
        sellResidentConstants = null;
        isedit = false;
    }

    private void addListImages(MultipartBody.Builder builder) {
        ArrayList<String> mImgUrls = GetImgFromAlbum.imgUrls;
        Log.d(TAG, "onViewClicked: ==============mImageUrls.size = " + mImgUrls.size());
        for (int i = 0; i < mImgUrls.size(); i++) {
            File imgFile = new File(mImgUrls.get(i));
            File compress = CompressHelper.getDefault(act).compressToFile(imgFile);
            if (compress != null) {
                Log.d(TAG, "onViewClicked: =====i=" + i);
                builder.addFormDataPart("assets" + i,
                        imgFile.getName(),
                        RequestBody.create(MediaType.parse("image/*"), compress));
            }
        }
        GetImgFromAlbum.imgUrls.clear();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0x002) {
            xiaoquid = data.getIntExtra("xiaoquid", 1);
            result = data.getStringExtra("result");
            tvXiaoqu.setText(result);
            Log.d(TAG, "onActivityResult: --------------xiaoquid = " + xiaoquid + ",result = " + result);
        }
        GetImgFromAlbum.CodeActivityResult(requestCode, resultCode, data, REQUEST_CODE_LOCAL, imgs[index], this, selectphotos, index - 2);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.fragment_residence_sell_proxyimg1:
                index = 0;
                break;
            case R.id.fragment_residence_sell_proxyimg2:
                index = 1;
                break;
            case R.id.fragment_residence_sell_houseimg1:
                index = 2;
                break;
            case R.id.fragment_residence_sell_houseimg2:
                index = 3;
                break;
            case R.id.fragment_residence_sell_houseimg3:
                index = 4;
                break;
            case R.id.fragment_residence_sell_houseimg4:
                index = 5;
                break;
            case R.id.fragment_residence_sell_houseimg5:
                index = 6;
                break;
            case R.id.fragment_residence_sell_houseimg6:
                index = 7;
                break;
            case R.id.fragment_residence_sell_houseimg7:
                index = 8;
                break;
            case R.id.fragment_residence_sell_houseimg8:
                index = 9;
                break;
        }
        GetImgFromAlbum.selectPicFromLocal(this, REQUEST_CODE_LOCAL);
    }


    @OnClick(R.id.fragment_residence_sell_floor)
    public void onViewClicked() {
        MyWheelViewDialog wheelViewDialog = new MyWheelViewDialog(act, MyWheelViewDialog.FLOORS);
        wheelViewDialog.initWheel1(act, MyWheelViewDialog.FLOORS);
        wheelViewDialog.showWheelDialog();
        wheelViewDialog.InitWheelViewCommit(fragmentResidenceSellFloorText, MyWheelViewDialog.FLOORS);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (adapterView.getId()) {
            case R.id.fragment_residence_sell_decoration:
                decoration_level = fragmentResidenceSellDecoration.getItemAtPosition(i).toString();
                break;
            case R.id.fragment_residence_sell_buildingstructure:
                builtstructure = fragmentResidenceSellBuildingstructure.getItemAtPosition(i).toString();
                break;
            case R.id.fragment_residence_sell_buildingcategory:
                builttype = fragmentResidenceSellBuildingcategory.getItemAtPosition(i).toString();
                break;
            case R.id.fragment_residence_sell_propertynature:
                nature_ofpropertyright = fragmentResidenceSellPropertynature.getItemAtPosition(i).toString();
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void loadData(int xiaoquid) {
        Log.d(TAG, "loadData: ---------------");
    }
}
