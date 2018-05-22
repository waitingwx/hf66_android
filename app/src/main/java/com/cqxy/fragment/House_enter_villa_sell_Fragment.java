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
import com.cqxy.bean.SellVillaHouseBean;
import com.cqxy.bean.ShangqBean;
import com.cqxy.bean.ShowLoadingEvent;
import com.cqxy.constants.FindHouseContants;
import com.cqxy.constants.QuxianBean;
import com.cqxy.constants.SellVillaContants;
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

public class House_enter_villa_sell_Fragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    //调用系统相册-选择图片
    private EditText allEdit[];
    protected static final int REQUEST_CODE_LOCAL = 1;
    @BindView(R.id.fragment_villa_sell_housename)
    EditText etPosition;
    @BindView(R.id.fragment_villa_sell_houseprice)
    EditText etPrice;
    @BindView(R.id.fragment_villa_sell_housesize)
    EditText etSize;
    @BindView(R.id.fragment_villa_sell_housetype_text)
    TextView fragmentVillaSellHousetypeText;
    @BindView(R.id.fragment_villa_sell_housetype)
    ImageView fragmentVillaSellHousetype;
    @BindView(R.id.fragment_villa_sell_buildage)
    EditText etAge;
    @BindView(R.id.fragment_villa_sell_orientation_text)
    TextView fragmentVillaSellOrientationText;
    @BindView(R.id.fragment_villa_sell_orientation)
    ImageView fragmentVillaSellOrientation;

    @BindView(R.id.fragment_villa_sell_yongjin)
    TextView fragmentVillaSellYongjin;
    @BindView(R.id.fragment_villa_sell_jingde)
    TextView fragmentVillaSellJingde;
    @BindView(R.id.fragment_villa_sell_fanfei)
    TextView fragmentVillaSellFanfei;
    @BindView(R.id.fragment_villa_sell_commit)
    Button fragmentVillaSellCommit;
    @BindView(R.id.fragment_villa_sell_houseimg1)
    ImageView fragmentVillaSellHouseimg1;
    @BindView(R.id.fragment_villa_sell_houseimg2)
    ImageView fragmentVillaSellHouseimg2;
    @BindView(R.id.fragment_villa_sell_houseimg3)
    ImageView fragmentVillaSellHouseimg3;
    @BindView(R.id.fragment_villa_sell_houseimg4)
    ImageView fragmentVillaSellHouseimg4;
    @BindView(R.id.fragment_villa_sell_houseimg5)
    ImageView fragmentVillaSellHouseimg5;
    @BindView(R.id.fragment_villa_sell_houseimg6)
    ImageView fragmentVillaSellHouseimg6;
    @BindView(R.id.fragment_villa_sell_houseimg7)
    ImageView fragmentVillaSellHouseimg7;
    @BindView(R.id.fragment_villa_sell_houseimg8)
    ImageView fragmentVillaSellHouseimg8;
    @BindView(R.id.fragment_villa_sell_houseimg9)
    ImageView fragmentVillaSellHouseimg9;
    @BindView(R.id.fragment_villa_sell_houseimg10)
    ImageView fragmentVillaSellHouseimg10;
    @BindView(R.id.fragment_villa_sell_floor_text)
    TextView fragmentVillaSellFloorText;
    @BindView(R.id.fragment_villa_sell_floor)
    ImageView fragmentVillaSellFloor;
    @BindView(R.id.fragment_villa_sell_support)
    ImageView fragmentVillaSellSupport;
    @BindView(R.id.fragment_villa_sell_note)
    EditText etNote;
    @BindView(R.id.fragment_villa_sell_decoration)
    AppCompatSpinner fragmentVillaSellDecoration;
    @BindView(R.id.fragment_villa_sell_buildingcategory)
    AppCompatSpinner fragmentVillaSellBuildingcategory;
    @BindView(R.id.fragment_villa_sell_yingli)
    EditText etYingli;
    @BindView(R.id.fragment_villa_sell_unit)
    TextView fragmentVillaSellUnit;
    @BindView(R.id.sp_quxian_villa_sell)
    AppCompatSpinner spQuxian;
    @BindView(R.id.sp_shangquan_villa_sell)
    AppCompatSpinner spShangquan;
    private List<ShangquanBean> shangquanList;
    private House_enterActivity act;
    private Unbinder unbinder;


    private int index = 0;
    private ImageView[] imgs;
    private SupportChectBox supportChectBox;
    private String builttype, decorationlevel;
    private String type;

    private int rentornot;
    private ImageView[] selectphotos;
    private TextView[] textViews = new TextView[3];
    private String btype = "佣金";
    private SellVillaContants sellVillaContants;
    private String TAG = "villa_sell";
    private int shangQuanId;
    private String[] shangquanStr = new String[3];

    public SellVillaContants getSellVillaContants() {
        return sellVillaContants;
    }

    public void setSellVillaContants(SellVillaContants sellVillaContants) {
        this.sellVillaContants = sellVillaContants;
    }

    public House_enter_villa_sell_Fragment() {
    }

    public House_enter_villa_sell_Fragment(String type, int rentornot, int houseid, boolean isedit) {
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

    private void showText() {
        Log.d(TAG, "showText: =======================");
        if (null == sellVillaContants)
            return;
        SellVillaHouseBean house = sellVillaContants.getHouse();
        String house_price = 0 == house.getHouse_price() ? "" : house.getHouse_price() + "";
        String house_area = 0 == house.getHouse_area() ? "" : house.getHouse_area() + "";
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

    public House_enter_villa_sell_Fragment(String type, int rentornot, @Nullable SellVillaContants sellVillaContants) {
        this.type = type;
        this.rentornot = rentornot;
        if (null != sellVillaContants)
            this.sellVillaContants = sellVillaContants;
        else
            Log.d(TAG, "House_enter_villa_sell_Fragment: -----------------");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        act = (House_enterActivity) context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.house_enter_fragment_villa_sell_layout, null);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

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
        textViews[0] = fragmentVillaSellYongjin;
        textViews[1] = fragmentVillaSellJingde;
        textViews[2] = fragmentVillaSellFanfei;


    }

    private void initselectimgs() {
        selectphotos = new ImageView[]{fragmentVillaSellHouseimg3, fragmentVillaSellHouseimg4, fragmentVillaSellHouseimg5,
                fragmentVillaSellHouseimg6, fragmentVillaSellHouseimg7, fragmentVillaSellHouseimg8,
                fragmentVillaSellHouseimg9, fragmentVillaSellHouseimg10};
    }

    private void initListener() {
        imgs = new ImageView[]{fragmentVillaSellHouseimg1, fragmentVillaSellHouseimg2, fragmentVillaSellHouseimg3, fragmentVillaSellHouseimg4, fragmentVillaSellHouseimg5, fragmentVillaSellHouseimg6, fragmentVillaSellHouseimg7, fragmentVillaSellHouseimg8, fragmentVillaSellHouseimg9, fragmentVillaSellHouseimg10};
        for (int i = 0; i < imgs.length; i++) {
            imgs[i].setOnClickListener(this);

        }
        fragmentVillaSellFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyWheelViewDialog wheelViewDialog = new MyWheelViewDialog(act, MyWheelViewDialog.FLOORS);
                wheelViewDialog.initWheel1(act, MyWheelViewDialog.FLOORS);
                wheelViewDialog.showWheelDialog();
                wheelViewDialog.InitWheelViewCommit(fragmentVillaSellFloorText, MyWheelViewDialog.FLOORS);
            }
        });
        fragmentVillaSellOrientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyWheelViewDialog wheelViewDialog = new MyWheelViewDialog(act, MyWheelViewDialog.ORIENTATIONS);
                wheelViewDialog.initWheel1(act, MyWheelViewDialog.ORIENTATIONS);
                wheelViewDialog.showWheelDialog();
                wheelViewDialog.InitWheelViewCommit(fragmentVillaSellOrientationText, MyWheelViewDialog.ORIENTATIONS);
            }
        });
        fragmentVillaSellSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (supportChectBox == null) {
                    supportChectBox = new SupportChectBox(act);
                }
                supportChectBox.showDialog();
            }
        });

        fragmentVillaSellHousetype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyWheelViewDialog wheelViewDialog = new MyWheelViewDialog(act, MyWheelViewDialog.HOUSETYPE);
                wheelViewDialog.initWheel1(act, MyWheelViewDialog.HOUSETYPE);
                wheelViewDialog.showWheelDialog();
                wheelViewDialog.InitWheelViewCommit(fragmentVillaSellHousetypeText, MyWheelViewDialog.HOUSETYPE);
            }
        });

        fragmentVillaSellDecoration.setOnItemSelectedListener(this);
        fragmentVillaSellBuildingcategory.setOnItemSelectedListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allEdit = new EditText[]{etPosition, etPrice, etSize, etAge, etYingli};
        Log.d(TAG, "onViewCreated: =====================");
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
            sellVillaContants = SellVillaContants.objectFromData(string);
            return sellVillaContants;
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

    @OnClick({R.id.fragment_villa_sell_commit, R.id.fragment_villa_sell_yongjin, R.id.fragment_villa_sell_jingde, R.id.fragment_villa_sell_fanfei})
    public void onViewClicked(View view) {
        int k = 0;
        switch (view.getId()) {
            case R.id.fragment_villa_sell_commit:
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
                EventBus.getDefault().post(new ShowLoadingEvent());
                shangquanStr[2] = TextContentUtils.getViewContent(etPosition);
                String position = shangquanStr[0] + shangquanStr[1] + shangquanStr[2];
                String houseprice = TextContentUtils.getViewContent(etPrice);
                String housearea = TextContentUtils.getViewContent(etSize);
                String housetype = TextContentUtils.getViewContent(fragmentVillaSellHousetypeText);
                String floor = TextContentUtils.getViewContent(fragmentVillaSellFloorText);
                String supporting;
                if (supportChectBox == null) {
                    supporting = "";
                } else {
                    supporting = supportChectBox.getSelectText();
                }
                int userid = UserInf.getUserId(act);
                String aspect = TextContentUtils.getViewContent(fragmentVillaSellOrientationText);
                String note = TextContentUtils.getViewContent(etNote);
                String builtage = TextContentUtils.getViewContent(etAge);
                String bvalue = TextContentUtils.getViewContent(etYingli) + TextContentUtils.getViewContent(fragmentVillaSellUnit);
                // mImgUrls为存放图片的url集合
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                addListImages(builder);
                builder.addFormDataPart("position", position)
                        .addFormDataPart("house_price", houseprice)
                        .addFormDataPart("house_area", housearea)
                        .addFormDataPart("built_type", builttype)
                        .addFormDataPart("aspect", aspect)
                        .addFormDataPart("house_type", housetype)
                        .addFormDataPart("floor", floor)
                        .addFormDataPart("decoration_level", decorationlevel)
                        .addFormDataPart("supporting_facility", supporting)
                        .addFormDataPart("user_id", userid + "")
                        .addFormDataPart("note", note)
                        .addFormDataPart("built_age", builtage)
                        .addFormDataPart("sourcetype", type)
                        .addFormDataPart("rentornot", rentornot + "")
                        .addFormDataPart("leixing", btype)
                        .addFormDataPart("bvalue", bvalue)
                        .addFormDataPart("shangquanid", shangQuanId + "");
                MultipartBody mpb = builder.build();
                Log.d(TAG, "onViewClicked: --------------------");
                OkHttpUtils.okhttpPostWithHead(UserInf.getUserToken(act),
                        UserInf.getUserPhonenum(act),
                        mpb, BaseUrl.URL + "villa_sellings", new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String responseBody = response.body().string();
                                EventBus.getDefault().post(new ShowLoadingEvent());
                                Log.d(TAG, "onResponse: responseBody = " + responseBody);
                                Intent mIntent = new Intent(act, FindHouseActivity.class);
                                mIntent.putExtra("flag", FindHouseContants.VILLA_SELL);
                                act.startActivity(mIntent);
                                act.finish();
                            }
                        });


                break;
            case R.id.fragment_villa_sell_yongjin:
                k = 0;
                btype = "佣金";
                break;
            case R.id.fragment_villa_sell_jingde:
                k = 1;
                btype = "净得";
                break;
            case R.id.fragment_villa_sell_fanfei:
                k = 2;
                btype = "返费";
                break;
        }
        if (getId() != R.id.fragment_villa_sell_commit) {
            YingLiUtils.selectTextViews(act, textViews, k, etYingli, fragmentVillaSellUnit);
        }
    }

    private void putChanges() {
        FormBody.Builder builder = new FormBody.Builder();
        SellVillaHouseBean house = sellVillaContants.getHouse();
        String houseprice = TextContentUtils.getViewContent(etPrice);
        if (house.getHouse_price() + "" != houseprice && "" != houseprice)
            builder.add("villa_selling[house_price]", houseprice);

        String housearea = TextContentUtils.getViewContent(etSize);
        if (house.getHouse_area() + "" != housearea && "" != housearea)
            builder.add("villa_selling[house_area]", housearea);

        String housetype = TextContentUtils.getViewContent(fragmentVillaSellHousetypeText);
        if (house.getHouse_type() + "" != housetype && "" != housetype)
            builder.add("villa_selling[house_type]", housetype);

        String floor = TextContentUtils.getViewContent(fragmentVillaSellFloorText);
        if (house.getFloor() + "" != floor && "" != floor)
            builder.add("villa_selling[floor]", floor);

        String supporting;
        if (supportChectBox == null) {
            supporting = "";
        } else {
            supporting = supportChectBox.getSelectText();
        }
        if (house.getSupporting_facility() + "" != supporting && "" != supporting)
            builder.add("villa_selling[supporting_facility]", supporting);

        String aspect = TextContentUtils.getViewContent(fragmentVillaSellOrientationText);
        if (house.getAspect() + "" != aspect && "" != aspect)
            builder.add("villa_selling[aspect]", aspect);

        String note = TextContentUtils.getViewContent(etNote);
        if (house.getNote() + "" != note && "" != note)
            builder.add("villa_selling[note]", note);

        String builtage = TextContentUtils.getViewContent(etAge);
        if (house.getBuilt_age() + "" != builtage && "" != builtage)
            builder.add("villa_selling[built_age]", builtage);

        String bvalue = TextContentUtils.getViewContent(etYingli) + TextContentUtils.getViewContent(fragmentVillaSellUnit);
        if (house.getBvalue() + "" != bvalue && "%" != bvalue && "元" != bvalue)
            builder.add("villa_selling[bvalue]", bvalue);

        if (house.getLeixing() + "" != btype && "" != btype)
            builder.add("villa_selling[leixing]", btype);


        FormBody formBody = builder.build();
        OkHttpUtils.okhttpPut(BaseUrl.URL + "villa_sellings", formBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Intent mIntent = new Intent(act, FindHouseActivity.class);
                mIntent.putExtra("flag", FindHouseContants.VILLA_SELL);
                act.startActivity(mIntent);
                act.finish();
            }
        });
        sellVillaContants = null;
        isedit = false;
    }

    private void addListImages(MultipartBody.Builder builder) {
        ArrayList<String> mImgUrls = GetImgFromAlbum.imgUrls;
        Log.d(TAG, "onViewClicked: ==============mImageUrls.size = " + mImgUrls.size());
        for (int i = 0; i < mImgUrls.size(); i++) {
            File imgFile = new File(mImgUrls.get(i));
            File compress = CompressHelper.getDefault(act).compressToFile(imgFile);
            if (compress != null) {
                Log.d(TAG, "addListImages: ------------------i - " + i);
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
            case R.id.fragment_villa_sell_houseimg1:
                index = 0;
                break;
            case R.id.fragment_villa_sell_houseimg2:
                index = 1;
                break;
            case R.id.fragment_villa_sell_houseimg3:
                index = 2;
                break;
            case R.id.fragment_villa_sell_houseimg4:
                index = 3;
                break;
            case R.id.fragment_villa_sell_houseimg5:
                index = 4;
                break;
            case R.id.fragment_villa_sell_houseimg6:
                index = 5;
                break;
            case R.id.fragment_villa_sell_houseimg7:
                index = 6;
                break;
            case R.id.fragment_villa_sell_houseimg8:
                index = 7;
                break;
            case R.id.fragment_villa_sell_houseimg9:
                index = 8;
                break;
            case R.id.fragment_villa_sell_houseimg10:
                index = 9;
                break;
        }
        GetImgFromAlbum.selectPicFromLocal(this, REQUEST_CODE_LOCAL);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        GetImgFromAlbum.CodeActivityResult(requestCode, resultCode, data, REQUEST_CODE_LOCAL, imgs[index], this, selectphotos, index - 2);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.fragment_villa_sell_decoration:
                decorationlevel = fragmentVillaSellDecoration.getItemAtPosition(i).toString();
                break;
            case R.id.fragment_villa_sell_buildingcategory:
                builttype = fragmentVillaSellBuildingcategory.getItemAtPosition(i).toString();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
