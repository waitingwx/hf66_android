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
import android.widget.Toast;

import com.cqxy.adapter.QuxianAdapter;
import com.cqxy.adapter.ShangqAdapter;
import com.cqxy.base.BaseUrl;
import com.cqxy.bean.HideLodingEvent;
import com.cqxy.bean.SellOfficeHouseBean;
import com.cqxy.bean.ShangqBean;
import com.cqxy.bean.ShowLoadingEvent;
import com.cqxy.constants.FindHouseContants;
import com.cqxy.constants.QuxianBean;
import com.cqxy.constants.SellOfficeConstants;
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
 * Created by Administrator on 2017/9/14.
 */

public class House_enter_office_sell_Fragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //调用系统相册-选择图片
    protected static final int REQUEST_CODE_LOCAL = 1;
    private EditText[] allEdit;
    @BindView(R.id.fragment_office_sell_housename)
    EditText etPosition;
    @BindView(R.id.fragment_office_sell_houseprice)
    EditText etPrice;
    @BindView(R.id.fragment_office_sell_housesize)
    EditText etHouseSize;
    @BindView(R.id.fragment_office_sell_buildage)
    EditText etAge;
    @BindView(R.id.fragment_office_sell_fee)
    EditText etPropertyfee;
    @BindView(R.id.fragment_office_sell_yongjin)
    TextView fragmentOfficeSellYongjin;
    @BindView(R.id.fragment_office_sell_jingde)
    TextView fragmentOfficeSellJingde;
    @BindView(R.id.fragment_office_sell_fanfei)
    TextView fragmentOfficeSellFanfei;
    @BindView(R.id.fragment_office_sell_commit)
    Button fragmentOfficeSellCommit;
    @BindView(R.id.fragment_office_sell_proxyimg1)
    ImageView fragmentOfficeSellProxyimg1;
    @BindView(R.id.fragment_office_sell_proxyimg2)
    ImageView fragmentOfficeSellProxyimg2;
    @BindView(R.id.fragment_office_sell_houseimg1)
    ImageView fragmentOfficeSellHouseimg1;
    @BindView(R.id.fragment_office_sell_houseimg2)
    ImageView fragmentOfficeSellHouseimg2;
    @BindView(R.id.fragment_office_sell_houseimg3)
    ImageView fragmentOfficeSellHouseimg3;
    @BindView(R.id.fragment_office_sell_houseimg4)
    ImageView fragmentOfficeSellHouseimg4;
    @BindView(R.id.fragment_office_sell_houseimg5)
    ImageView fragmentOfficeSellHouseimg5;
    @BindView(R.id.fragment_office_sell_houseimg6)
    ImageView fragmentOfficeSellHouseimg6;
    @BindView(R.id.fragment_office_sell_houseimg7)
    ImageView fragmentOfficeSellHouseimg7;
    @BindView(R.id.fragment_office_sell_houseimg8)
    ImageView fragmentOfficeSellHouseimg8;
    @BindView(R.id.fragment_office_sell_floor_text)
    TextView fragmentOfficeSellFloorText;
    @BindView(R.id.fragment_office_sell_floor)
    ImageView fragmentOfficeSellFloor;
    @BindView(R.id.fragment_office_sell_note)
    EditText etNote;
    @BindView(R.id.fragment_office_sell_buildinglevel)
    AppCompatSpinner fragmentOfficeSellBuildinglevel;
    @BindView(R.id.fragment_office_sell_yingli)
    EditText etYingli;
    @BindView(R.id.fragment_office_sell_unit)
    TextView fragmentOfficeSellUnit;
    @BindView(R.id.sp_quxian_office_sell)
    AppCompatSpinner spQuxian;
    @BindView(R.id.sp_shangquan_office_sell)
    AppCompatSpinner spShangquan;

    private House_enterActivity act;
    private Unbinder unbinder;

    private int index = 0;
    private ImageView[] imgs;

    private String office_building_level;
    private String type;

    private ImageView[] selectphotos;
    private int rentornot;
    private TextView[] textViews = new TextView[3];
    private String btype = "佣金";

    private String TAG = "office_sell";
    private SellOfficeConstants sellOfficeConstants;
    private int shangquanid = -1;
    private String[] shangquanStr = new String[3];
    List<ShangquanBean> shangquan;

    public SellOfficeConstants getSellOfficeConstants() {
        return sellOfficeConstants;
    }

    public void setSellOfficeConstants(SellOfficeConstants sellOfficeConstants) {
        this.sellOfficeConstants = sellOfficeConstants;
    }


    public House_enter_office_sell_Fragment() {
    }

    public House_enter_office_sell_Fragment(String type, int rentornot, int houseid, boolean isedit) {
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

    ImageTask it = new ImageTask(new ImageTask.IRequest() {
        @Override
        public Object doRequest() {
            return OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(act), UserInf.getUserPhonenum(act), BaseUrl.URL + type + "/" + houseid + ".json");
        }

        @Override
        public Object doPraseResult(Object object) {
            String string = (String) object;
            sellOfficeConstants = SellOfficeConstants.objectFromData(string);
            return sellOfficeConstants;
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
        if (null == sellOfficeConstants)
            return;
        SellOfficeHouseBean house = sellOfficeConstants.getHouse();
        String house_price = 0 == house.getHouse_price() ? "" : house.getHouse_price() + "";
        String house_area = null == house.getHouse_area() ? "" : house.getHouse_area() + "";
        String note = null == house.getNote() ? "" : house.getNote();
        String position = null == house.getPosition() ? "" : house.getPosition();
        String property_fee = 0 == house.getProperty_fee() ? "" : house.getProperty_fee() + "";
        String built_age = 0 == house.getBuilt_age() ? "" : house.getBuilt_age() + "";
        etPosition.setText(position);
        etPrice.setText(house_price);
        etHouseSize.setText(house_area);
        etAge.setText(built_age);
        etPropertyfee.setText(property_fee);
        etNote.setText(note);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        act = (House_enterActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.house_enter_fragment_office_sell_layout, null);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initselectimgs();
        initTextViews();
        initImgListener();
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
                shangquan = city.get(position - 1).getShangquan();
                ShangquanBean shangquanBean = new ShangquanBean();
                shangquanBean.setName("全部");
                shangquan.add(0, shangquanBean);
                spShangquan.setAdapter(new ShangqAdapter(act, shangquan));
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
                shangquanStr[1] = shangquan.get(position).getName();
                shangquanid = shangquan.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    List<QuxianBean> quxianBeen;

    private void initTextViews() {
        textViews[0] = fragmentOfficeSellYongjin;
        textViews[1] = fragmentOfficeSellJingde;
        textViews[2] = fragmentOfficeSellFanfei;


    }

    private void initselectimgs() {
        selectphotos = new ImageView[]{fragmentOfficeSellHouseimg1, fragmentOfficeSellHouseimg2, fragmentOfficeSellHouseimg3,
                fragmentOfficeSellHouseimg4, fragmentOfficeSellHouseimg5, fragmentOfficeSellHouseimg6,
                fragmentOfficeSellHouseimg7, fragmentOfficeSellHouseimg8};
    }


    private void initImgListener() {
        imgs = new ImageView[]{fragmentOfficeSellProxyimg1, fragmentOfficeSellProxyimg2, fragmentOfficeSellHouseimg1, fragmentOfficeSellHouseimg2, fragmentOfficeSellHouseimg3, fragmentOfficeSellHouseimg4, fragmentOfficeSellHouseimg5, fragmentOfficeSellHouseimg6, fragmentOfficeSellHouseimg7, fragmentOfficeSellHouseimg8};
        for (int i = 0; i < imgs.length; i++) {
            imgs[i].setOnClickListener(this);

        }
        fragmentOfficeSellFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyWheelViewDialog wheelViewDialog = new MyWheelViewDialog(act, MyWheelViewDialog.FLOORS);
                wheelViewDialog.initWheel1(act, MyWheelViewDialog.FLOORS);
                wheelViewDialog.showWheelDialog();
                wheelViewDialog.InitWheelViewCommit(fragmentOfficeSellFloorText, MyWheelViewDialog.FLOORS);
            }
        });
        fragmentOfficeSellBuildinglevel.setOnItemSelectedListener(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allEdit = new EditText[]{etPosition, etPrice, etHouseSize, etAge, etPropertyfee, etYingli};
        Log.d(TAG, "onViewCreated: =====================");
        if (isedit)
            it.execute();
    }

    @OnClick({R.id.fragment_office_sell_commit, R.id.fragment_office_sell_yongjin, R.id.fragment_office_sell_jingde, R.id.fragment_office_sell_fanfei})
    public void onViewClicked(View view) {
        int k = 0;
        switch (view.getId()) {
            case R.id.fragment_office_sell_commit:
                if (isedit) {
                    putChanges();
                    return;
                }
                for (int i = 0; i < allEdit.length; i++) {
                    Log.d(TAG, "onViewClicked: -----------------i = " + i);
                    if (allEdit[i].getText().length() == 0) {
                        allEdit[i].setHintTextColor(getContext().getColor(R.color.colorRed));
                        allEdit[i].setHint("此字段不能为空");
                        allEdit[i].requestFocus();
                        return;
                    }
                }
                shangquanStr[2] = TextContentUtils.getViewContent(etPosition);
                String position = shangquanStr[0] + shangquanStr[1] + shangquanStr[2];
                String houseprice = TextContentUtils.getViewContent(etPrice);

                String housearea = TextContentUtils.getViewContent(etHouseSize);
                String property_fee = TextContentUtils.getViewContent(etPropertyfee);

                String floor = TextContentUtils.getViewContent(fragmentOfficeSellFloorText);
                int userid = UserInf.getUserId(act);
                String note = TextContentUtils.getViewContent(etNote);
                String builtage = TextContentUtils.getViewContent(etAge);
                String bvalue = TextContentUtils.getViewContent(etYingli) + TextContentUtils.getViewContent(fragmentOfficeSellUnit);
                Log.d(TAG, "onViewClicked: ----------------position = " + position + "\nshangquanid = " + shangquanid);
                if (shangquanid == -1) {
                    Toast.makeText(act, "您未选择所在商圈", Toast.LENGTH_SHORT).show();
                    spShangquan.requestFocus();
                    return;
                }
                if (shangquanStr[2] == "") {
                    Toast.makeText(act, "您未填写写字楼位置", Toast.LENGTH_SHORT).show();
                    etPosition.requestFocus();
                    return;
                }
                EventBus.getDefault().post(new ShowLoadingEvent());
                // mImgUrls为存放图片的url集合
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                addListImages(builder);
                builder.addFormDataPart("position", position)
                        .addFormDataPart("house_price", houseprice + "")
                        .addFormDataPart("house_area", housearea + "")
                        .addFormDataPart("property_fee", property_fee + "")
                        .addFormDataPart("office_building_level", office_building_level)
                        .addFormDataPart("floor", floor)
                        .addFormDataPart("user_id", userid + "")
                        .addFormDataPart("note", note)
                        .addFormDataPart("built_age", builtage)
                        .addFormDataPart("sourcetype", type)
                        .addFormDataPart("rentornot", rentornot + "")
                        .addFormDataPart("leixing", btype)
                        .addFormDataPart("shangquanid", shangquanid + "")
                        .addFormDataPart("detailedaddress", shangquanStr[2])
                        .addFormDataPart("bvalue", bvalue);
                MultipartBody mpb = builder.build();
                Log.d(TAG, "onViewClicked: --------------bvalue = " + bvalue);
                OkHttpUtils.okhttpPostWithHead(UserInf.getUserToken(act),
                        UserInf.getUserPhonenum(act),
                        mpb,
                        BaseUrl.URL + "office_building_sellings",
                        new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d(TAG, "onFailure: e.tostring = " + e.getMessage().toString());
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                EventBus.getDefault().post(new HideLodingEvent());
                                Log.d("OfficeSell", "onResponse:===================responseBody = " + response.body().string());
                                Intent mIntent = new Intent(act, FindHouseActivity.class);
                                mIntent.putExtra("flag", FindHouseContants.OFFICEBUILDING_SELL);
                                startActivity(mIntent);
                                act.finish();
                            }
                        });
                break;
            case R.id.fragment_residence_rent_yongjin:
                k = 0;
                btype = "佣金";
                break;
            case R.id.fragment_residence_rent_jingde:
                k = 1;
                btype = "净得";
                break;
            case R.id.fragment_residence_rent_fanfei:
                k = 2;
                btype = "返费";
                break;
        }
        if (getId() != R.id.fragment_residence_rent_commit) {
            YingLiUtils.selectTextViews(act, textViews, k, etYingli, fragmentOfficeSellUnit);
        }
    }

    private void putChanges() {
        SellOfficeHouseBean house = sellOfficeConstants.getHouse();
        FormBody.Builder builder = new FormBody.Builder();
        String houseprice = TextContentUtils.getViewContent(etPrice);
        if (house.getHouse_price() + "" != houseprice && "" != houseprice)
            builder.add("office_selling[house_price]", houseprice);

        String housearea = TextContentUtils.getViewContent(etHouseSize);
        if (house.getHouse_area() + "" != housearea && "" != housearea)
            builder.add("office_selling[house_area]", housearea);

        String property_fee = TextContentUtils.getViewContent(etPropertyfee);
        if (house.getProperty_fee() + "" != property_fee && "" != property_fee)
            builder.add("office_selling[property_fee]", property_fee);
        String floor = TextContentUtils.getViewContent(fragmentOfficeSellFloorText);
        if (house.getFloor() + "" != floor && "" != floor)
            builder.add("office_selling[floor]", floor);

        String note = TextContentUtils.getViewContent(etNote);
        if (house.getNote() + "" != note && "" != note)
            builder.add("office_selling[note]", note);
        String builtage = TextContentUtils.getViewContent(etAge);
        if (house.getBuilt_age() + "" != builtage && "" != builtage)
            builder.add("office_selling[built_age]", builtage);

        String bvalue = TextContentUtils.getViewContent(etYingli) + TextContentUtils.getViewContent(fragmentOfficeSellUnit);
        if (house.getBvalue() + "" != bvalue && "%" != bvalue && "元" != bvalue)
            builder.add("office_selling[bvalue]", bvalue);
        if (house.getLeixing() + "" != btype && "" != btype)
            builder.add("office_selling[leixing]", btype);

        FormBody formBody = builder.build();
        OkHttpUtils.okhttpPut(
                BaseUrl.URL + "office_building_sellings",
                formBody,
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("OfficeSell", "onResponse:===================responseBody = " + response.body().string());
                        Intent mIntent = new Intent(act, FindHouseActivity.class);
                        mIntent.putExtra("flag", FindHouseContants.OFFICEBUILDING_SELL);
                        startActivity(mIntent);
                        act.finish();
                    }
                });
        sellOfficeConstants = null;
        isedit = false;
    }

    private void addListImages(MultipartBody.Builder builder) {
        ArrayList<String> mImgUrls = GetImgFromAlbum.imgUrls;
        Log.d("Office_rent", "onViewClicked: ==============mImageUrls.size = " + mImgUrls.size());
        for (int i = 0; i < mImgUrls.size(); i++) {
            File imgFile = new File(mImgUrls.get(i));
            File compress = CompressHelper.getDefault(act).compressToFile(imgFile);
            if (compress != null) {
                Log.d("Office_rent", "onViewClicked: =====i=" + i);//TODO:找到通过同一key，上传多个值变为数组或者对象，而不是通过区别key上传
                builder.addFormDataPart("assets" + i,
                        imgFile.getName(),
                        RequestBody.create(MediaType.parse("image/*"), compress));
            }
        }
        GetImgFromAlbum.imgUrls.clear();//使用完成后清空，避免提交多次相同数据，TODO：查看是否需要去重
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        GetImgFromAlbum.CodeActivityResult(requestCode, resultCode, data, REQUEST_CODE_LOCAL, imgs[index], this, selectphotos, index - 2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_office_sell_proxyimg1:
                index = 0;
                break;
            case R.id.fragment_office_sell_proxyimg2:
                index = 1;
                break;
            case R.id.fragment_office_sell_houseimg1:
                index = 2;
                break;
            case R.id.fragment_office_sell_houseimg2:
                index = 3;
                break;
            case R.id.fragment_office_sell_houseimg3:
                index = 4;
                break;
            case R.id.fragment_office_sell_houseimg4:
                index = 5;
                break;
            case R.id.fragment_office_sell_houseimg5:
                index = 6;
                break;
            case R.id.fragment_office_sell_houseimg6:
                index = 7;
                break;
            case R.id.fragment_office_sell_houseimg7:
                index = 8;
                break;
            case R.id.fragment_office_sell_houseimg8:
                index = 9;
                break;
        }
        GetImgFromAlbum.selectPicFromLocal(this, REQUEST_CODE_LOCAL);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        office_building_level = fragmentOfficeSellBuildinglevel.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}