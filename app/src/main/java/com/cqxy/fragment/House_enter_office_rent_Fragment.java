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
import android.widget.ImageView;
import android.widget.TextView;

import com.cqxy.adapter.QuxianAdapter;
import com.cqxy.adapter.ShangqAdapter;
import com.cqxy.base.BaseUrl;
import com.cqxy.bean.HideLodingEvent;
import com.cqxy.bean.RentOfficeHouseBean;
import com.cqxy.bean.ShangqBean;
import com.cqxy.bean.ShowLoadingEvent;
import com.cqxy.constants.FindHouseContants;
import com.cqxy.constants.QuxianBean;
import com.cqxy.constants.RentOfficeContants;
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

public class House_enter_office_rent_Fragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    //调用系统相册-选择图片
    protected static final int REQUEST_CODE_LOCAL = 1;

    @BindView(R.id.fragment_office_rent_housename)
    EditText etPosition;
    @BindView(R.id.fragment_office_rent_houseprice)
    EditText etPrice;
    @BindView(R.id.fragment_office_rent_housesize)
    EditText etHouseSize;
    @BindView(R.id.fragment_office_rent_propertyfee)
    EditText etPropertyfee;
    @BindView(R.id.fragment_office_rent_yongjin)
    TextView fragmentOfficeRentYongjin;
    @BindView(R.id.fragment_office_rent_jingde)
    TextView fragmentOfficeRentJingde;
    @BindView(R.id.fragment_office_rent_fanfei)
    TextView fragmentOfficeRentFanfei;
    @BindView(R.id.fragment_office_rent_commit)
    Button btnCommit;
    @BindView(R.id.fragment_office_rent_houseimg1)
    ImageView iva;
    @BindView(R.id.fragment_office_rent_houseimg2)
    ImageView fragmentOfficeRentHouseimg2;
    @BindView(R.id.fragment_office_rent_houseimg3)
    ImageView fragmentOfficeRentHouseimg3;
    @BindView(R.id.fragment_office_rent_houseimg4)
    ImageView fragmentOfficeRentHouseimg4;
    @BindView(R.id.fragment_office_rent_houseimg5)
    ImageView fragmentOfficeRentHouseimg5;
    @BindView(R.id.fragment_office_rent_houseimg6)
    ImageView fragmentOfficeRentHouseimg6;
    @BindView(R.id.fragment_office_rent_houseimg7)
    ImageView fragmentOfficeRentHouseimg7;
    @BindView(R.id.fragment_office_rent_houseimg8)
    ImageView fragmentOfficeRentHouseimg8;
    @BindView(R.id.fragment_office_rent_floor_text)
    TextView tvFloor;
    @BindView(R.id.fragment_office_rent_floor)
    ImageView fragmentOfficeRentFloor;
    @BindView(R.id.fragment_office_rent_note)
    EditText etNote;
    @BindView(R.id.fragment_office_rent_paytype)
    AppCompatSpinner asPayType;
    @BindView(R.id.fragment_office_rent_ispropertyfee)
    AppCompatSpinner aspropertyfee;
    @BindView(R.id.fragment_office_rent_buildingtype)
    AppCompatSpinner fragmentOfficeRentBuildingtype;
    @BindView(R.id.fragment_office_rent_officelevel)
    AppCompatSpinner fragmentOfficeRentOfficelevel;
    @BindView(R.id.fragment_office_rent_yingli)
    EditText etYingli;
    @BindView(R.id.fragment_office_rent_unit)
    TextView fragmentOfficeRentUnit;
    @BindView(R.id.sp_quxian_office_rent)
    AppCompatSpinner spQuxian;
    @BindView(R.id.sp_shangquan_office_rent)
    AppCompatSpinner spShangquan;
    private EditText[] allEdit;
    private House_enterActivity act;
    private Unbinder unbinder;
    private int index = 0;
    private ImageView[] selectphotos;
    private ImageView[] imgs;
    private String payment, officebuildinglevel, builttype, including_property_fee_or_not;
    private String type;
    private int rentornot;
    private TextView[] textViews = new TextView[3];
    private String btype = "佣金";
    private String TAG = "office_rent";
    private QuxianAdapter quxianAdapter;
    private ShangqAdapter shangquanAdapter;
    private List<ShangquanBean> shangquanList;
    private int shangQuanId;
    private RentOfficeContants rentOfficeContants;
    private String[] shangquanStr = new String[3];

    public RentOfficeContants getRentOfficeContants() {
        return rentOfficeContants;
    }

    public void setRentOfficeContants(RentOfficeContants rentOfficeContants) {
        this.rentOfficeContants = rentOfficeContants;
    }

    public House_enter_office_rent_Fragment(String type, int rentornot) {
        this.type = type;
        this.rentornot = rentornot;
    }

    public House_enter_office_rent_Fragment(String type, int rentornot, int houseid, boolean isedit) {
        this.type = type;
        this.rentornot = rentornot;
        this.houseid = houseid;
        this.isedit = isedit;
    }

    private boolean isedit = false;

    private int houseid;

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

    private void showText() {
        Log.d(TAG, "showText: =======================");
        if (null == rentOfficeContants)
            return;
        RentOfficeHouseBean house = rentOfficeContants.getHouse();
        String house_price = 0 == house.getRent() ? "" : house.getRent() + "";
        String house_area = 0 == house.getHouse_area() ? "" : house.getHouse_area() + "";
        String note = null == house.getNote() ? "" : house.getNote();
        String position = null == house.getPosition() ? "" : house.getPosition();
        String property_fee = 0 == house.getProperty_fee() ? "" : house.getProperty_fee() + "";
        etPosition.setText(position);
        etPrice.setText(house_price);
        etHouseSize.setText(house_area);
        etPropertyfee.setText(property_fee);
        etNote.setText(note);
    }

    public House_enter_office_rent_Fragment(String type, int rentornot, @Nullable RentOfficeContants rentOfficeContants) {
        this.type = type;
        this.rentornot = rentornot;
        if (null != rentOfficeContants)
            this.rentOfficeContants = rentOfficeContants;
        else
            Log.d(TAG, "House_enter_office_rent_Fragment: ------------------");
    }

    public House_enter_office_rent_Fragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.act = (House_enterActivity) context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.house_enter_fragment_office_rent_layout, null);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: =====================");
        allEdit = new EditText[]{etPosition, etPrice, etHouseSize, etPropertyfee, etYingli};
        if (isedit)
            it.execute();
    }

    ImageTask it = new ImageTask(new ImageTask.IRequest() {
        @Override
        public Object doRequest() {
            return OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(act), UserInf.getUserPhonenum(act), BaseUrl.URL + type + "/" + houseid + ".json");
        }

        @Override
        public Object doPraseResult(Object object) {
            String string = (String) object;
            rentOfficeContants = RentOfficeContants.objectFromData(string);
            return rentOfficeContants;
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initselectimgs();
        initTextViews();
        initListener();
        bindSpinner();

    }

    private void bindSpinner() {
        quxianBeen = new ArrayList<>();
        String responsebody = SpUtil.getString(act, "shangquan", "responsebody");
        final List<ShangqBean.CityBean> city = ShangqBean.objectFromData(responsebody).getCity();
        for (int i = 0; i < city.size(); i++) {
            quxianBeen.add(city.get(i).getQuxian());
        }
        quxianAdapter = new QuxianAdapter(act, quxianBeen);
        spQuxian.setAdapter(quxianAdapter);
        spQuxian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                shangquanList = city.get(position).getShangquan();
                shangquanStr[0] = quxianBeen.get(position).getName();
                shangquanAdapter = new ShangqAdapter(act, shangquanList);
                spShangquan.setAdapter(shangquanAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spShangquan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
        textViews[0] = fragmentOfficeRentYongjin;
        textViews[1] = fragmentOfficeRentJingde;
        textViews[2] = fragmentOfficeRentFanfei;


    }

    private void initselectimgs() {
        selectphotos = new ImageView[]{iva, fragmentOfficeRentHouseimg2, fragmentOfficeRentHouseimg3,
                fragmentOfficeRentHouseimg4, fragmentOfficeRentHouseimg5, fragmentOfficeRentHouseimg6,
                fragmentOfficeRentHouseimg7, fragmentOfficeRentHouseimg8};
    }

    private void initListener() {
        imgs = new ImageView[]{iva, fragmentOfficeRentHouseimg2, fragmentOfficeRentHouseimg3, fragmentOfficeRentHouseimg4, fragmentOfficeRentHouseimg5, fragmentOfficeRentHouseimg6, fragmentOfficeRentHouseimg7, fragmentOfficeRentHouseimg8};
        for (int i = 0; i < imgs.length; i++) {
            imgs[i].setOnClickListener(this);

        }
        fragmentOfficeRentFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyWheelViewDialog wheelViewDialog = new MyWheelViewDialog(act, MyWheelViewDialog.FLOORS);
                wheelViewDialog.initWheel1(act, MyWheelViewDialog.FLOORS);
                wheelViewDialog.showWheelDialog();
                wheelViewDialog.InitWheelViewCommit(tvFloor, MyWheelViewDialog.FLOORS);
            }
        });
        asPayType.setOnItemSelectedListener(this);
        aspropertyfee.setOnItemSelectedListener(this);
        fragmentOfficeRentBuildingtype.setOnItemSelectedListener(this);
        fragmentOfficeRentOfficelevel.setOnItemSelectedListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.fragment_office_rent_commit,
            R.id.fragment_office_rent_yongjin,
            R.id.fragment_office_rent_jingde,
            R.id.fragment_office_rent_fanfei})
    public void onViewClicked(View view) {
        int k = 0;
        switch (view.getId()) {
            case R.id.fragment_office_rent_commit:
                if (isedit) {
                    putChanges();
                    return;
                }
                shangquanStr[2] = TextContentUtils.getViewContent(etPosition);
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
                String position = shangquanStr[0] + shangquanStr[1] + shangquanStr[2];
                String houseprice = TextContentUtils.getViewContent(etPrice);
                String housearea = TextContentUtils.getViewContent(etHouseSize);
                String floor = TextContentUtils.getViewContent(tvFloor);
                String bvalue = TextContentUtils.getViewContent(etYingli) + TextContentUtils.getViewContent(fragmentOfficeRentUnit);
                String property_fee = TextContentUtils.getViewContent(etPropertyfee);
                int userid = UserInf.getUserId(act);
                String note = TextContentUtils.getViewContent(etNote);

                // mImgUrls为存放图片的url集合
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                addListImages(builder);
                builder.addFormDataPart("position", position)
                        .addFormDataPart("rent", houseprice)
                        .addFormDataPart("house_area", housearea)
                        .addFormDataPart("payment", payment)
                        .addFormDataPart("office_building_level", officebuildinglevel)
                        .addFormDataPart("floor", floor)
                        .addFormDataPart("user_id", userid + "")
                        .addFormDataPart("including_property_fee_or_not", including_property_fee_or_not)
                        .addFormDataPart("note", note)
                        .addFormDataPart("built_type", builttype)
                        .addFormDataPart("property_fee", property_fee)
                        .addFormDataPart("sourcetype", type)
                        .addFormDataPart("rentornot", rentornot + "")
                        .addFormDataPart("leixing", btype)
                        .addFormDataPart("bvalue", bvalue)
                        .addFormDataPart("shangquanid", shangQuanId + "");
                MultipartBody mpb = builder.build();

                OkHttpUtils.okhttpPostWithHead(UserInf.getUserToken(act),
                        UserInf.getUserPhonenum(act),
                        mpb, BaseUrl.URL + "office_building_rents", new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                EventBus.getDefault().post(new HideLodingEvent());
                                String string = response.body().string();
                                if (response.isSuccessful()) {
                                    Intent mIntent = new Intent(act, FindHouseActivity.class);
                                    mIntent.putExtra("flag", FindHouseContants.OFFICEBUILDING_RENT);
                                    startActivity(mIntent);
                                    act.finish();
                                } else {
                                    Log.d("Office_rent", "onResponse: --------------response.message = " + response.message() + "\tresponsebody = " + string);
                                }
                            }
                        });
                break;

            case R.id.fragment_office_rent_yongjin:
                k = 0;
                btype = "佣金";
                break;
            case R.id.fragment_office_rent_jingde:
                k = 1;
                btype = "净得";
                break;
            case R.id.fragment_office_rent_fanfei:
                k = 2;
                btype = "返费";
                break;
        }
        if (getId() != R.id.fragment_office_rent_commit) {
            YingLiUtils.selectTextViews(act, textViews, k, etYingli, fragmentOfficeRentUnit);
        }

    }

    private void putChanges() {
        String houseprice = TextContentUtils.getViewContent(etPrice);
        RentOfficeHouseBean house = rentOfficeContants.getHouse();
        FormBody.Builder builder = new FormBody.Builder();
        if (house.getRent() + "" != houseprice && "" != houseprice)
            builder.add("office_rent[rent]", houseprice);

        String housearea = TextContentUtils.getViewContent(etHouseSize);
        if (house.getHouse_area() + "" != housearea && "" != housearea)
            builder.add("office_rent[house_area]", housearea);

        String floor = TextContentUtils.getViewContent(tvFloor);
        if (house.getFloor() + "" != floor && "" != floor)
            builder.add("office_rent[floor]", floor);

        String bvalue = TextContentUtils.getViewContent(etYingli) + TextContentUtils.getViewContent(fragmentOfficeRentUnit);
        if (house.getBvalue() + "" != bvalue && "" != bvalue)
            builder.add("office_rent[bvalue]", bvalue);

        String property_fee = TextContentUtils.getViewContent(etPropertyfee);
        if (house.getProperty_fee() + "" != property_fee && "" != property_fee)
            builder.add("office_rent[property_fee]", property_fee);

        String note = TextContentUtils.getViewContent(etNote);
        if (house.getNote() + "" != note && "" != note)
            builder.add("office_rent[note]", note);
        if (house.getLeixing() + "" != btype && "" != btype)
            builder.add("office_rent[leixing]", btype);
        FormBody formBody = builder.build();

        OkHttpUtils.okhttpPut(BaseUrl.URL + "office_building_rents", formBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                if (response.isSuccessful()) {
                    Intent mIntent = new Intent(act, FindHouseActivity.class);
                    mIntent.putExtra("flag", FindHouseContants.OFFICEBUILDING_RENT);
                    startActivity(mIntent);
                    act.finish();
                } else {
                    Log.d("Office_rent", "onResponse: --------------response.message = " + response.message() + "\tresponsebody = " + string);
                }
            }
        });
        rentOfficeContants = null;
        isedit = false;
    }

    private void addListImages(MultipartBody.Builder builder) {
        ArrayList<String> mImgUrls = GetImgFromAlbum.imgUrls;
        Log.d("Office_rent", "onViewClicked: ==============mImageUrls.size = " + mImgUrls.size());
        for (int i = 0; i < mImgUrls.size(); i++) {
            File imgFile = new File(mImgUrls.get(i));
            File compress = CompressHelper.getDefault(act).compressToFile(imgFile);
            if (compress != null) {
                builder.addFormDataPart("assets" + i,
                        imgFile.getName(),
                        RequestBody.create(MediaType.parse("image/*"), compress));
            }
        }
        GetImgFromAlbum.imgUrls.clear();//使用完成后清空，避免提交多次相同数据，TODO：查看是否需要去重
    }

    private void postImgs(String remoteUrl) {
        ArrayList<String> imageUrls = GetImgFromAlbum.imgUrls;
        if (imageUrls.size() == 0)
            return;
        OkHttpUtils.uploadImg(remoteUrl, imageUrls);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_office_rent_houseimg1:
                index = 0;
                break;
            case R.id.fragment_office_rent_houseimg2:
                index = 1;
                break;
            case R.id.fragment_office_rent_houseimg3:
                index = 2;
                break;
            case R.id.fragment_office_rent_houseimg4:
                index = 3;
                break;
            case R.id.fragment_office_rent_houseimg5:
                index = 4;
                break;
            case R.id.fragment_office_rent_houseimg6:
                index = 5;
                break;
            case R.id.fragment_office_rent_houseimg7:
                index = 6;
                break;
            case R.id.fragment_office_rent_houseimg8:
                index = 7;
                break;
        }
        GetImgFromAlbum.selectPicFromLocal(this, REQUEST_CODE_LOCAL);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        GetImgFromAlbum.CodeActivityResult(requestCode, resultCode, data, REQUEST_CODE_LOCAL, imgs[index], this, selectphotos, index);
        Log.d("offic_rent", "onActivityResult: " + GetImgFromAlbum.imgUrls.size());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.fragment_office_rent_paytype:
                payment = asPayType.getItemAtPosition(i).toString();
                break;
            case R.id.fragment_office_rent_ispropertyfee:
                including_property_fee_or_not = aspropertyfee.getItemAtPosition(i).toString();
                break;
            case R.id.fragment_office_rent_buildingtype:
                builttype = fragmentOfficeRentBuildingtype.getItemAtPosition(i).toString();
                break;
            case R.id.fragment_office_rent_officelevel:
                officebuildinglevel = fragmentOfficeRentOfficelevel.getItemAtPosition(i).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
