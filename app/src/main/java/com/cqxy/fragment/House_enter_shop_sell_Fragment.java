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
import com.cqxy.bean.SellShopHouseBean;
import com.cqxy.bean.ShangqBean;
import com.cqxy.bean.ShowLoadingEvent;
import com.cqxy.constants.FindHouseContants;
import com.cqxy.constants.QuxianBean;
import com.cqxy.constants.SellShopConstants;
import com.cqxy.constants.ShangquanBean;
import com.cqxy.fyb.FindHouseActivity;
import com.cqxy.fyb.House_enterActivity;
import com.cqxy.fyb.R;
import com.cqxy.utils.GetImgFromAlbum;
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

public class House_enter_shop_sell_Fragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    //调用系统相册-选择图片
    private EditText[] allEdit;
    protected static final int REQUEST_CODE_LOCAL = 1;
    @BindView(R.id.fragment_shop_sell_housename)
    EditText etPosition;
    @BindView(R.id.fragment_shop_sell_houseprice)
    EditText etPrice;
    @BindView(R.id.fragment_shop_sell_housesize)
    EditText etSize;
    @BindView(R.id.fragment_shop_sell_propertyfee)
    EditText etPropertyFee;

    @BindView(R.id.fragment_shop_sell_yongjin)
    TextView fragmentShopSellYongjin;
    @BindView(R.id.fragment_shop_sell_jingde)
    TextView fragmentShopSellJingde;
    @BindView(R.id.fragment_shop_sell_fanfei)
    TextView fragmentShopSellFanfei;
    @BindView(R.id.fragment_shop_sell_commit)
    Button btnCommit;
    @BindView(R.id.fragment_shop_sell_houseimg1)
    ImageView fragmentShopSellHouseimg1;
    @BindView(R.id.fragment_shop_sell_houseimg2)
    ImageView fragmentShopSellHouseimg2;
    @BindView(R.id.fragment_shop_sell_houseimg3)
    ImageView fragmentShopSellHouseimg3;
    @BindView(R.id.fragment_shop_sell_houseimg4)
    ImageView fragmentShopSellHouseimg4;
    @BindView(R.id.fragment_shop_sell_houseimg5)
    ImageView fragmentShopSellHouseimg5;
    @BindView(R.id.fragment_shop_sell_houseimg6)
    ImageView fragmentShopSellHouseimg6;
    @BindView(R.id.fragment_shop_sell_houseimg7)
    ImageView fragmentShopSellHouseimg7;
    @BindView(R.id.fragment_shop_sell_houseimg8)
    ImageView fragmentShopSellHouseimg8;
    @BindView(R.id.fragment_shop_sell_floor_text)
    TextView fragmentShopSellFloorText;
    @BindView(R.id.fragment_shop_sell_floor)
    ImageView fragmentShopSellFloor;
    @BindView(R.id.fragment_shop_sell_support)
    ImageView fragmentShopSellSupport;
    @BindView(R.id.fragment_shop_sell_note)
    EditText etNote;
    @BindView(R.id.fragment_shop_sell_iscession)
    AppCompatSpinner fragmentShopSellIscession;
    @BindView(R.id.fragment_shop_sell_decoration)
    AppCompatSpinner fragmentShopSellDecoration;
    @BindView(R.id.fragment_shop_sell_targetformat)
    AppCompatSpinner fragmentShopSellTargetformat;
    @BindView(R.id.fragment_shop_sell_shopcategory)
    AppCompatSpinner fragmentShopSellShopcategory;
    @BindView(R.id.fragment_shop_sell_yingli)
    EditText etYingli;
    @BindView(R.id.fragment_shop_sell_unit)
    TextView fragmentShopSellUnit;
    @BindView(R.id.sp_quxian_shop_sell)
    AppCompatSpinner spQuxian;
    @BindView(R.id.sp_shangquan_shop_sell)
    AppCompatSpinner spShangquan;

    private House_enterActivity act;
    private Unbinder unbinder;
    private String btype = "佣金";

    private int index = 0;
    private ImageView[] imgs;
    private SupportChectBox supportChectBox;
    private ImageView[] selectphotos;
    private String shop_type, ceding_or_not, decorationlevel, target_formats;
    private String type;
    private String TAG = "shop_sell";

    private int rentornot;
    private TextView[] textViews = new TextView[3];
    private SellShopConstants sellShopConstants;
    private List<ShangquanBean> shangquanList;
    private int shangQuanId;
    private String[] shangquanStr = new String[3];

    public SellShopConstants getSellShopConstants() {
        return sellShopConstants;
    }

    public void setSellShopConstants(SellShopConstants sellShopConstants) {
        this.sellShopConstants = sellShopConstants;
    }

    public House_enter_shop_sell_Fragment(String type, int rentornot, int houseid, boolean isedit) {
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
        if (!isedit)
            return;
        OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(act), UserInf.getUserPhonenum(act), BaseUrl.URL + type + "/" + houseid + ".json", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d(TAG, "onResponse: ========================string = " + string);
                sellShopConstants = SellShopConstants.objectFromData(string);
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showText();
                    }
                });
            }
        });
    }

    private void showText() {
        Log.d(TAG, "showText: =======================");
        if (null == sellShopConstants)
            return;
        SellShopHouseBean house = sellShopConstants.getHouse();
        String house_price = 0 == house.getHouse_price() ? "" : house.getHouse_price() + "";
        String house_area = null == house.getHouse_area() ? "" : house.getHouse_area() + "";
        String note = null == house.getNote() ? "" : house.getNote();
        String position = null == house.getPosition() ? "" : house.getPosition();
        String property_fee = 0 == house.getProperty_fee() ? "" : house.getProperty_fee() + "";
        etPosition.setText(position);
        etPrice.setText(house_price);
        etSize.setText(house_area);
        etPropertyFee.setText(property_fee);
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
        View inflate = inflater.inflate(R.layout.house_enter_fragment_shop_sell_layout, null);
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
        textViews[0] = fragmentShopSellYongjin;
        textViews[1] = fragmentShopSellJingde;
        textViews[2] = fragmentShopSellFanfei;


    }

    private void initselectimgs() {
        selectphotos = new ImageView[]{fragmentShopSellHouseimg1, fragmentShopSellHouseimg2, fragmentShopSellHouseimg3,
                fragmentShopSellHouseimg4, fragmentShopSellHouseimg5, fragmentShopSellHouseimg6,
                fragmentShopSellHouseimg7, fragmentShopSellHouseimg8};
    }


    private void initListener() {
        imgs = new ImageView[]{fragmentShopSellHouseimg1, fragmentShopSellHouseimg2, fragmentShopSellHouseimg3, fragmentShopSellHouseimg4, fragmentShopSellHouseimg5, fragmentShopSellHouseimg6, fragmentShopSellHouseimg7, fragmentShopSellHouseimg8};
        for (int i = 0; i < imgs.length; i++) {
            imgs[i].setOnClickListener(this);

        }
        fragmentShopSellFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyWheelViewDialog wheelViewDialog = new MyWheelViewDialog(act, MyWheelViewDialog.FLOORS);
                wheelViewDialog.initWheel1(act, MyWheelViewDialog.FLOORS);
                wheelViewDialog.showWheelDialog();
                wheelViewDialog.InitWheelViewCommit(fragmentShopSellFloorText, MyWheelViewDialog.FLOORS);
            }
        });
        fragmentShopSellSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (supportChectBox == null) {
                    supportChectBox = new SupportChectBox(act);
                }
                supportChectBox.showDialog();
            }

        });
        fragmentShopSellIscession.setOnItemSelectedListener(this);
        fragmentShopSellDecoration.setOnItemSelectedListener(this);
        fragmentShopSellTargetformat.setOnItemSelectedListener(this);
        fragmentShopSellShopcategory.setOnItemSelectedListener(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allEdit = new EditText[]{etPosition, etPrice, etSize, etPropertyFee, etYingli};
    }

    @OnClick({R.id.fragment_shop_sell_commit, R.id.fragment_shop_sell_yongjin, R.id.fragment_shop_sell_jingde, R.id.fragment_shop_sell_fanfei})
    public void onViewClicked(View view) {
        int k = 0;
        switch (view.getId()) {
            case R.id.fragment_shop_sell_commit:
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
                String property_fee = TextContentUtils.getViewContent(etPropertyFee);
                String bvalue = TextContentUtils.getViewContent(etYingli) + TextContentUtils.getViewContent(fragmentShopSellUnit);
                String floor = TextContentUtils.getViewContent(fragmentShopSellFloorText);
                String supporting;
                if (supportChectBox == null) {
                    supporting = "";
                } else {
                    supporting = supportChectBox.getSelectText();
                }
                int userid = UserInf.getUserId(act);
                String note = TextContentUtils.getViewContent(etNote);
                // mImgUrls为存放图片的url集合
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                addListImages(builder);
                builder.addFormDataPart("position", position)
                        .addFormDataPart("house_price", houseprice)
                        .addFormDataPart("house_area", housearea)
                        .addFormDataPart("property_fee", property_fee)
                        .addFormDataPart("shop_type", shop_type)
                        .addFormDataPart("ceding_or_not", ceding_or_not)
                        .addFormDataPart("floor", floor)
                        .addFormDataPart("decoration_level", decorationlevel)
                        .addFormDataPart("supporting_facility", supporting)
                        .addFormDataPart("user_id", userid + "")
                        .addFormDataPart("note", note)
                        .addFormDataPart("target_formats", target_formats)
                        .addFormDataPart("sourcetype", type)
                        .addFormDataPart("rentornot", rentornot + "")
                        .addFormDataPart("leixing", btype)
                        .addFormDataPart("bvalue", bvalue)
                        .addFormDataPart("shangquanid", shangQuanId + "");
                Log.d("type", "onViewClicked: type = " + type);
                MultipartBody mpb = builder.build();
                OkHttpUtils.okhttpPostWithHead(UserInf.getUserToken(act),
                        UserInf.getUserPhonenum(act),
                        mpb, BaseUrl.URL + "shop_sellings", new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                EventBus.getDefault().post(new HideLodingEvent());
                                Intent mIntent = new Intent(act, FindHouseActivity.class);
                                mIntent.putExtra("flag", FindHouseContants.SHOP_SELL);
                                startActivity(mIntent);
                                act.finish();
                            }
                        });

                break;
            case R.id.fragment_shop_sell_yongjin:
                k = 0;
                btype = "佣金";
                break;
            case R.id.fragment_shop_sell_jingde:
                k = 1;
                btype = "净得";
                break;
            case R.id.fragment_shop_sell_fanfei:
                k = 2;
                btype = "返费";
                break;
        }
        if (getId() != R.id.fragment_shop_sell_commit) {
            YingLiUtils.selectTextViews(act, textViews, k, etYingli, fragmentShopSellUnit);
        }
    }

    private void putChanges() {
        SellShopHouseBean house = sellShopConstants.getHouse();
        FormBody.Builder builder = new FormBody.Builder();
        String houseprice = TextContentUtils.getViewContent(etPrice);
        if (house.getHouse_price() + "" != houseprice && "" != houseprice)
            builder.add("shop_selling[house_price]", houseprice);

        String housearea = TextContentUtils.getViewContent(etSize);
        if (house.getHouse_area() + "" != housearea && "" != housearea)
            builder.add("shop_selling[house_area]", housearea);

        String property_fee = TextContentUtils.getViewContent(etPropertyFee);
        if (house.getProperty_fee() + "" != property_fee && "" != property_fee)
            builder.add("shop_selling[property_fee]", property_fee);
        String bvalue = TextContentUtils.getViewContent(etYingli) + TextContentUtils.getViewContent(fragmentShopSellUnit);
        if (house.getBvalue() + "" != bvalue && "%" != bvalue && "元" != bvalue)
            builder.add("shop_selling[bvalue]", bvalue);
        String floor = TextContentUtils.getViewContent(fragmentShopSellFloorText);
        if (house.getProperty_fee() + "" != property_fee && "" != property_fee)
            builder.add("shop_selling[floor]", floor);

        String supporting;
        if (supportChectBox == null) {
            supporting = "";
        } else {
            supporting = supportChectBox.getSelectText();
        }
        if (house.getSupporting_facility() + "" != supporting && "" != supporting)
            builder.add("shop_selling[supporting_facility]", supporting);

        String note = TextContentUtils.getViewContent(etNote);
        if (house.getNote() + "" != note && "" != note)
            builder.add("shop_selling[note]", note);
        if (house.getLeixing() + "" != btype && "" != type)
            builder.add("shop_selling[leixing]", btype);

        FormBody formBody = builder.build();
        OkHttpUtils.okhttpPut(BaseUrl.URL + "shop_sellings", formBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Intent mIntent = new Intent(act, FindHouseActivity.class);
                mIntent.putExtra("flag", FindHouseContants.SHOP_SELL);
                startActivity(mIntent);
                act.finish();
            }
        });
        sellShopConstants = null;
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_shop_sell_houseimg1:
                index = 0;
                break;
            case R.id.fragment_shop_sell_houseimg2:
                index = 1;
                break;
            case R.id.fragment_shop_sell_houseimg3:
                index = 2;
                break;
            case R.id.fragment_shop_sell_houseimg4:
                index = 3;
                break;
            case R.id.fragment_shop_sell_houseimg5:
                index = 4;
                break;
            case R.id.fragment_shop_sell_houseimg6:
                index = 5;
                break;
            case R.id.fragment_shop_sell_houseimg7:
                index = 6;
                break;
            case R.id.fragment_shop_sell_houseimg8:
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
            case R.id.fragment_shop_sell_iscession:
                ceding_or_not = fragmentShopSellIscession.getItemAtPosition(i).toString();
                break;
            case R.id.fragment_shop_sell_decoration:
                decorationlevel = fragmentShopSellDecoration.getItemAtPosition(i).toString();
                break;
            case R.id.fragment_shop_sell_targetformat:
                target_formats = fragmentShopSellTargetformat.getItemAtPosition(i).toString();
                break;
            case R.id.fragment_shop_sell_shopcategory:
                shop_type = fragmentShopSellShopcategory.getItemAtPosition(i).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
