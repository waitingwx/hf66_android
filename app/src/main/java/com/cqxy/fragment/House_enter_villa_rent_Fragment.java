package com.cqxy.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cqxy.adapter.QuxianAdapter;
import com.cqxy.adapter.ShangqAdapter;
import com.cqxy.base.BaseUrl;
import com.cqxy.bean.HideLodingEvent;
import com.cqxy.bean.RentVillaHouseBean;
import com.cqxy.bean.ShangqBean;
import com.cqxy.bean.ShowLoadingEvent;
import com.cqxy.constants.FindHouseContants;
import com.cqxy.constants.QuxianBean;
import com.cqxy.constants.RentVillaContants;
import com.cqxy.constants.ShangquanBean;
import com.cqxy.fyb.FindHouseActivity;
import com.cqxy.fyb.House_enterActivity;
import com.cqxy.fyb.R;
import com.cqxy.utils.GetImgFromAlbum;
import com.cqxy.utils.ImageTask;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.SpUtil;
import com.cqxy.utils.TextContentUtils;
import com.cqxy.utils.UserInf;
import com.cqxy.utils.YingLiUtils;
import com.cqxy.view.MyWheelViewDialog;
import com.cqxy.view.SupportChectBox;
import com.nanchen.compresshelper.CompressHelper;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import info.hoang8f.android.segmented.SegmentedGroup;
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

public class House_enter_villa_rent_Fragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    //调用系统相册-选择图片
    protected static final int REQUEST_CODE_LOCAL = 1;
    private EditText[] allEdit;
    @BindView(R.id.fragment_villa_rent_housename)
    EditText etPosition;
    @BindView(R.id.fragment_villa_rent_houseprice)
    EditText etPrice;
    @BindView(R.id.fragment_villa_rent_housesize)
    EditText etSize;

    @BindView(R.id.fragment_villa_rent_housetype_text)
    TextView fragmentVillaRentHousetypeText;
    @BindView(R.id.fragment_villa_rent_housetype)
    ImageView fragmentVillaRentHousetype;

    @BindView(R.id.fragment_villa_rent_buildage)
    EditText etAge;

    @BindView(R.id.fragment_villa_rent_yongjin)
    TextView fragmentVillaRentYongjin;
    @BindView(R.id.fragment_villa_rent_jingde)
    TextView fragmentVillaRentJingde;
    @BindView(R.id.fragment_villa_rent_fanfei)
    TextView fragmentVillaRentFanfei;
    @BindView(R.id.fragment_villa_rent_commit)
    Button fragmentVillaRentCommit;
    @BindView(R.id.select_button1)
    RadioButton selectButton1;
    @BindView(R.id.select_button2)
    RadioButton selectButton2;
    @BindView(R.id.select_group)
    SegmentedGroup selectGroup;
    @BindView(R.id.fragment_villa_rent_houseimg1)
    ImageView fragmentVillaRentHouseimg1;
    @BindView(R.id.fragment_villa_rent_houseimg2)
    ImageView fragmentVillaRentHouseimg2;
    @BindView(R.id.fragment_villa_rent_houseimg3)
    ImageView fragmentVillaRentHouseimg3;
    @BindView(R.id.fragment_villa_rent_houseimg4)
    ImageView fragmentVillaRentHouseimg4;
    @BindView(R.id.fragment_villa_rent_houseimg5)
    ImageView fragmentVillaRentHouseimg5;
    @BindView(R.id.fragment_villa_rent_houseimg6)
    ImageView fragmentVillaRentHouseimg6;
    @BindView(R.id.fragment_villa_rent_houseimg7)
    ImageView fragmentVillaRentHouseimg7;
    @BindView(R.id.fragment_villa_rent_houseimg8)
    ImageView fragmentVillaRentHouseimg8;
    @BindView(R.id.fragment_villa_rent_floor_text)
    TextView fragmentVillaRentFloorText;
    @BindView(R.id.fragment_villa_rent_floor)
    ImageView fragmentVillaRentFloor;
    @BindView(R.id.fragment_villa_rent_support)
    ImageView fragmentVillaRentSupport;
    @BindView(R.id.fragment_villa_rent_note)
    EditText etNote;
    @BindView(R.id.fragment_villa_rent_paytype)
    AppCompatSpinner fragmentVillaRentPaytype;
    @BindView(R.id.fragment_villa_rent_buildingtype)
    AppCompatSpinner fragmentVillaRentBuildingtype;
    @BindView(R.id.fragment_villa_rent_decoration)
    AppCompatSpinner fragmentVillaRentDecoration;
    @BindView(R.id.fragment_villa_rent_yingli)
    EditText etYingli;
    @BindView(R.id.fragment_villa_rent_unit)
    TextView fragmentVillaRentUnit;
    @BindView(R.id.sp_quxian_villa_rent)
    AppCompatSpinner spQuxian;
    @BindView(R.id.sp_shangquan_villa_rent)
    AppCompatSpinner spShangquan;

    private House_enterActivity act;
    private Unbinder unbinder;

    private String leaseway = "整租";
    private int index = 0;
    private ImageView[] imgs;
    private SupportChectBox supportChectBox;
    private String payment, decorationlevel, builttype;
    private String type;
    private ImageView[] selectphotos;
    private int rentornot;
    private TextView[] textViews = new TextView[3];
    private String btype = "佣金";
    private RentVillaContants rentVillaContants;
    private static String TAG = "villa_rent";
    private List<ShangquanBean> shangquanList;
    private int shangQuanId;
    private String[] shangquanStr = new String[3];

    public RentVillaContants getRentVillaContants() {
        return rentVillaContants;
    }

    public void setRentVillaContants(RentVillaContants rentVillaContants) {
        this.rentVillaContants = rentVillaContants;
    }

    public House_enter_villa_rent_Fragment(String type, int rentornot) {
        this.type = type;
        this.rentornot = rentornot;
    }

    public House_enter_villa_rent_Fragment(String type, int rentornot, int houseid, boolean isedit) {
        this.type = type;
        this.rentornot = rentornot;
        this.houseid = houseid;
        this.isedit = isedit;
    }

    private boolean isedit = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: =================================");
        Bundle arguments = getArguments();
        if (null == arguments)
            return;
        houseid = arguments.getInt("houseid");
        type = arguments.getString("type");
        Log.d(TAG, "onCreate: =================houseid = " + houseid + "type = " + type);
        if (isedit)
            EventBus.getDefault().post(new ShowLoadingEvent());
    }

    ImageTask it = new ImageTask(new ImageTask.IRequest() {
        @Override
        public Object doRequest() {
            return OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(act), UserInf.getUserPhonenum(act), BaseUrl.URL + type + "/" + houseid + ".json");
        }

        @Override
        public Object doPraseResult(Object object) {
            String string = (String) object;
            rentVillaContants = RentVillaContants.objectFromData(string);
            return rentVillaContants;
        }
    }, new ImageTask.IRequestCallback() {
        @Override
        public void onSuccess(Object object) {
            EventBus.getDefault().post(new HideLodingEvent());
            showText();
        }

        @Override
        public void onError() {

        }
    });

    private void showText() {
        Log.d(TAG, "showText: =======================");
        if (null == rentVillaContants)
            return;
        RentVillaHouseBean house = rentVillaContants.getHouse();
        String house_price = 0 == house.getRent() ? "" : house.getRent() + "";
        String house_area = null == house.getHouse_area() ? "" : house.getHouse_area() + "";
        String note = null == house.getNote() ? "" : house.getNote();
        String position = null == house.getPosition() ? "" : house.getPosition();
        String built_age = 0 == house.getBuilt_age() ? "" : house.getBuilt_age() + "";
        etPosition.setText(position);
        etPrice.setText(house_price);
        etSize.setText(house_area);
        etAge.setText(built_age);
        etNote.setText(note);
    }

    private int houseid;

    public House_enter_villa_rent_Fragment(String type, int rentornot, @Nullable RentVillaContants rentVillaContants) {
        this.type = type;
        this.rentornot = rentornot;
        if (null != rentVillaContants)
            this.rentVillaContants = rentVillaContants;
        else
            Log.d(TAG, "House_enter_villa_rent_Fragment: ---------------");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.act = (House_enterActivity) context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.house_enter_fragment_villa_rent_layout, null);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allEdit = new EditText[]{etPosition,etPrice,etSize,etAge,etYingli};
        Log.d(TAG, "onViewCreated: =====================");
        if (isedit)
            it.execute();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initselectimgs();
        initListener();
        initTextViews();
        bindSpinner();
    }

    private void bindSpinner() {
        quxianBeen = new ArrayList<>();
        String responsebody = SpUtil.getString(act, "shangquan", "responsebody");
        final List<ShangqBean.CityBean> city = ShangqBean.objectFromData(responsebody).getCity();
        for (int i = 0; i < city.size(); i++) {
            quxianBeen.add(city.get(i).getQuxian());
        }
        QuxianBean element = new QuxianBean();
        element.setName("全部");
        quxianBeen.add(0, element);
        spQuxian.setAdapter(new QuxianAdapter(act, quxianBeen));
        spQuxian.setSelection(0);
        spQuxian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    return;
                shangquanStr[0] = quxianBeen.get(position).getName();
                shangquanList = city.get(position - 1).getShangquan();
                ShangquanBean shangquanBean = new ShangquanBean();
                shangquanBean.setName("全部");
                shangquanList.add(0, shangquanBean);
                spShangquan.setAdapter(new ShangqAdapter(act, shangquanList));
                spShangquan.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spShangquan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    return;
                shangquanStr[1] = shangquanList.get(position).getName();
                shangQuanId = shangquanList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    List<QuxianBean> quxianBeen;

    private void initTextViews() {
        textViews[0] = fragmentVillaRentYongjin;
        textViews[1] = fragmentVillaRentJingde;
        textViews[2] = fragmentVillaRentFanfei;


    }


    private void initselectimgs() {
        selectphotos = new ImageView[]{fragmentVillaRentHouseimg1, fragmentVillaRentHouseimg2, fragmentVillaRentHouseimg3,
                fragmentVillaRentHouseimg4, fragmentVillaRentHouseimg5, fragmentVillaRentHouseimg6,
                fragmentVillaRentHouseimg7, fragmentVillaRentHouseimg8};
    }


    private void initListener() {
        selectButton1.setText("整租");
        selectButton1.setChecked(true);
        selectButton2.setText("合租");
        selectGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.select_button1:
                        leaseway = (String) selectButton1.getText();
                        break;
                    case R.id.select_button2:
                        leaseway = (String) selectButton2.getText();
                        break;
                }
            }
        });
        imgs = new ImageView[]{fragmentVillaRentHouseimg1, fragmentVillaRentHouseimg2, fragmentVillaRentHouseimg3, fragmentVillaRentHouseimg4, fragmentVillaRentHouseimg5, fragmentVillaRentHouseimg6, fragmentVillaRentHouseimg7, fragmentVillaRentHouseimg8};
        for (int i = 0; i < imgs.length; i++) {
            imgs[i].setOnClickListener(this);

        }
        fragmentVillaRentFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyWheelViewDialog wheelViewDialog = new MyWheelViewDialog(act, MyWheelViewDialog.FLOORS);
                wheelViewDialog.initWheel1(act, MyWheelViewDialog.FLOORS);
                wheelViewDialog.showWheelDialog();
                wheelViewDialog.InitWheelViewCommit(fragmentVillaRentFloorText, MyWheelViewDialog.FLOORS);
            }
        });
        fragmentVillaRentSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (supportChectBox == null) {
                    supportChectBox = new SupportChectBox(act);
                }
                supportChectBox.showDialog();
            }
        });

        fragmentVillaRentHousetype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyWheelViewDialog wheelViewDialog = new MyWheelViewDialog(act, MyWheelViewDialog.HOUSETYPE);
                wheelViewDialog.initWheel1(act, MyWheelViewDialog.HOUSETYPE);
                wheelViewDialog.showWheelDialog();
                wheelViewDialog.InitWheelViewCommit(fragmentVillaRentHousetypeText, MyWheelViewDialog.HOUSETYPE);
            }
        });

        fragmentVillaRentPaytype.setOnItemSelectedListener(this);
        fragmentVillaRentBuildingtype.setOnItemSelectedListener(this);
        fragmentVillaRentDecoration.setOnItemSelectedListener(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fragment_villa_rent_commit, R.id.fragment_villa_rent_yongjin, R.id.fragment_villa_rent_jingde, R.id.fragment_villa_rent_fanfei})
    public void onViewClicked(View view) {
        int k = 0;
        switch (view.getId()) {

            case R.id.fragment_villa_rent_commit:
                if (isedit) {
                    putChanges();
                    return;
                }
                for (int i = 0; i < allEdit.length; i++) {
                    if (allEdit[i].getText().length() == 0) {
                        allEdit[i].setHintTextColor(getContext().getColor(R.color.colorRed));
                        allEdit[i].setHint("此字段不能为空");
                        allEdit[i].requestFocus();
                        return;
                    }
                }
                EventBus.getDefault().post(new ShowLoadingEvent());

                shangquanStr[2] = TextContentUtils.getViewContent(etPosition);
                String position = shangquanStr[0] + shangquanStr[1] + shangquanStr[2];
                String houseprice = TextContentUtils.getViewContent(etPrice);
                String housearea = TextContentUtils.getViewContent(etSize);
                String housetype = TextContentUtils.getViewContent(fragmentVillaRentHousetypeText);
                String floor = TextContentUtils.getViewContent(fragmentVillaRentFloorText);
                String supporting;
                if (supportChectBox == null) {
                    supporting = "";
                } else {
                    supporting = supportChectBox.getSelectText();
                }
                int userid = UserInf.getUserId(act);
                Log.d(TAG, "onViewClicked: -------------------------------userid = " + userid);
                String builtage = TextContentUtils.getViewContent(etAge);
                String note = TextContentUtils.getViewContent(etNote);
                String bvalue = TextContentUtils.getViewContent(etYingli) + TextContentUtils.getViewContent(fragmentVillaRentUnit);
                // mImgUrls为存放图片的url集合
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                addListImages(builder);
                builder.addFormDataPart("position", position)
                        .addFormDataPart("rent", houseprice)
                        .addFormDataPart("house_area", housearea)
                        .addFormDataPart("payment", payment)
                        .addFormDataPart("lease_way", leaseway)
                        .addFormDataPart("house_type", housetype)
                        .addFormDataPart("floor", floor)
                        .addFormDataPart("decoration_level", decorationlevel)
                        .addFormDataPart("supporting_facility", supporting)
                        .addFormDataPart("user_id", userid + "")
                        .addFormDataPart("built_age", builtage)
                        .addFormDataPart("note", note)
                        .addFormDataPart("built_type", builttype)
                        .addFormDataPart("sourcetype", type)
                        .addFormDataPart("rentornot", rentornot + "")
                        .addFormDataPart("leixing", btype)
                        .addFormDataPart("bvalue", bvalue)
                        .addFormDataPart("shangquanid", shangQuanId + "");
                MultipartBody mpb = builder.build();
                OkHttpUtils.okhttpPostWithHead(UserInf.getUserToken(act),
                        UserInf.getUserPhonenum(act),
                        mpb, BaseUrl.URL + "villa_rents", new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d(TAG, "onFailure: ----------------------e.tostring = " +e.getMessage());
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                EventBus.getDefault().post(new HideLodingEvent());
                                String responseBody = response.body().string();
                                Log.d(TAG, "onResponse: ------------responseBody =" + responseBody);
                                Intent mIntent = new Intent(act, FindHouseActivity.class);
                                mIntent.putExtra("flag", FindHouseContants.VILLA_RENT);
                                startActivity(mIntent);
                                act.finish();
                            }
                        });

                break;
            case R.id.fragment_villa_rent_yongjin:
                k = 0;
                btype = "佣金";
                break;
            case R.id.fragment_villa_rent_jingde:
                k = 1;
                btype = "净得";
                break;
            case R.id.fragment_villa_rent_fanfei:
                k = 2;
                btype = "返费";
                break;
        }
        if (getId() != R.id.fragment_villa_rent_commit) {
            YingLiUtils.selectTextViews(act, textViews, k, etYingli, fragmentVillaRentUnit);
        }
    }

    private void putChanges() {
        FormBody.Builder builder = new FormBody.Builder();
        RentVillaHouseBean house = rentVillaContants.getHouse();

        String houseprice = TextContentUtils.getViewContent(etPrice);
        if (house.getRent() + "" != houseprice && "" != houseprice)
            builder.add("villa_rent[rent]", houseprice);

        String housearea = TextContentUtils.getViewContent(etSize);
        if (house.getHouse_area() + "" != housearea && "" != housearea)
            builder.add("villa_rent[house_area]", housearea);

        if (house.getPayment() + "" != payment && "" != payment)
            builder.add("villa_rent[payment]", payment);

        String housetype = TextContentUtils.getViewContent(fragmentVillaRentHousetypeText);
        if (house.getHouse_type() + "" != housetype && "" != housetype)
            builder.add("villa_rent[house_type]", housetype);

        String floor = TextContentUtils.getViewContent(fragmentVillaRentFloorText);
        if (house.getFloor() + "" != floor && "" != floor)
            builder.add("villa_rent[floor]", floor);

        String supporting;
        if (supportChectBox == null) {
            supporting = "";
        } else {
            supporting = supportChectBox.getSelectText();
        }
        if (house.getSupporting_facility() + "" != supporting && "" != supporting)
            builder.add("villa_rent[supporting_facility]", supporting);

        String builtage = TextContentUtils.getViewContent(etAge);
        if (house.getBuilt_age() + "" != builtage && "" != builtage)
            builder.add("villa_rent[built_age]", builtage);

        String note = TextContentUtils.getViewContent(etNote);
        if (house.getNote() + "" != note && "" != note)
            builder.add("villa_rent[note]", note);

        String bvalue = TextContentUtils.getViewContent(etYingli) + TextContentUtils.getViewContent(fragmentVillaRentUnit);
        if (house.getNote() + "" != note && "" != note)
            builder.add("villa_rent[bvalue]", bvalue);
        if (house.getLease_way() + "" != leaseway)
            builder.add("villa_rent[lease_way]", leaseway);
        if (house.getLeixing() + "" != btype)
            builder.add("villa_rent[leixing]", btype);

        FormBody formBody = builder.build();
        OkHttpUtils.okhttpPut(BaseUrl.URL + "villa_rents", formBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Intent mIntent = new Intent(act, FindHouseActivity.class);
                mIntent.putExtra("flag", FindHouseContants.VILLA_RENT);
                startActivity(mIntent);
                act.finish();
            }
        });
        rentVillaContants = null;
        isedit = false;
    }

    private void addListImages(MultipartBody.Builder builder) {
        ArrayList<String> mImgUrls = GetImgFromAlbum.imgUrls;
        Log.d(TAG, "onViewClicked: ==============mImageUrls.size = " + mImgUrls.size());
        for (int i = 0; i < mImgUrls.size(); i++) {
            File imgFile = new File(mImgUrls.get(i));
            File compress = CompressHelper.getDefault(act).compressToFile(imgFile);
            if (compress != null) {
                Log.d(TAG, "addListImages: -------------i = " + i);
                builder.addFormDataPart("assets" + i,
                        imgFile.getName(),
                        RequestBody.create(MediaType.parse("image/*"), compress));
            }
        }
        GetImgFromAlbum.imgUrls.clear();//使用完成后清空，避免提交多次相同数据，TODO：查看是否需要去重
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_villa_rent_houseimg1:
                index = 0;
                break;
            case R.id.fragment_villa_rent_houseimg2:
                index = 1;
                break;
            case R.id.fragment_villa_rent_houseimg3:
                index = 2;
                break;
            case R.id.fragment_villa_rent_houseimg4:
                index = 3;
                break;
            case R.id.fragment_villa_rent_houseimg5:
                index = 4;
                break;
            case R.id.fragment_villa_rent_houseimg6:
                index = 5;
                break;
            case R.id.fragment_villa_rent_houseimg7:
                index = 6;
                break;
            case R.id.fragment_villa_rent_houseimg8:
                index = 7;
                break;
        }
        GetImgFromAlbum.selectPicFromLocal(this, REQUEST_CODE_LOCAL);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        GetImgFromAlbum.CodeActivityResult(requestCode, resultCode, data, REQUEST_CODE_LOCAL, imgs[index], this, selectphotos, index);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.fragment_villa_rent_paytype:
                payment = fragmentVillaRentPaytype.getItemAtPosition(i).toString();
                break;
            case R.id.fragment_villa_rent_buildingtype:
                builttype = fragmentVillaRentBuildingtype.getItemAtPosition(i).toString();
                break;
            case R.id.fragment_villa_rent_decoration:
                decorationlevel = fragmentVillaRentDecoration.getItemAtPosition(i).toString();
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
