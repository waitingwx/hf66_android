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
import com.cqxy.bean.RentShopHouseBean;
import com.cqxy.bean.ShangqBean;
import com.cqxy.bean.ShowLoadingEvent;
import com.cqxy.constants.FindHouseContants;
import com.cqxy.constants.QuxianBean;
import com.cqxy.constants.RentShopContants;
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
 * Created by Administrator on 2017/9/12.
 */

public class House_enter_shop_rent_Fragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    //调用系统相册-选择图片
    protected static final int REQUEST_CODE_LOCAL = 1;
    private EditText[] allEdit;
    @BindView(R.id.fragment_shop_rent_housename)
    EditText etPosition;
    @BindView(R.id.fragment_shop_rent_houseprice)
    EditText etPrice;
    @BindView(R.id.fragment_shop_rent_housesize)
    EditText etSize;
    @BindView(R.id.fragment_shop_rent_propertyfee)
    EditText etPropertyFee;
    @BindView(R.id.fragment_shop_rent_yongjin)
    TextView fragmentShopRentYongjin;
    @BindView(R.id.fragment_shop_rent_jingde)
    TextView fragmentShopRentJingde;
    @BindView(R.id.fragment_shop_rent_fanfei)
    TextView fragmentShopRentFanfei;
    @BindView(R.id.fragment_shop_rent_commit)
    Button fragmentShopRentCommit;
    @BindView(R.id.fragment_shop_rent_houseimg1)
    ImageView fragmentShopRentHouseimg1;
    @BindView(R.id.fragment_shop_rent_houseimg2)
    ImageView fragmentShopRentHouseimg2;
    @BindView(R.id.fragment_shop_rent_houseimg3)
    ImageView fragmentShopRentHouseimg3;
    @BindView(R.id.fragment_shop_rent_houseimg4)
    ImageView fragmentShopRentHouseimg4;
    @BindView(R.id.fragment_shop_rent_houseimg5)
    ImageView fragmentShopRentHouseimg5;
    @BindView(R.id.fragment_shop_rent_houseimg6)
    ImageView fragmentShopRentHouseimg6;
    @BindView(R.id.fragment_shop_rent_houseimg7)
    ImageView fragmentShopRentHouseimg7;
    @BindView(R.id.fragment_shop_rent_houseimg8)
    ImageView fragmentShopRentHouseimg8;
    @BindView(R.id.fragment_shop_rent_floor_text)
    TextView fragmentShopRentFloorText;
    @BindView(R.id.fragment_shop_rent_floor)
    ImageView fragmentShopRentFloor;
    @BindView(R.id.fragment_shop_rent_support)
    ImageView fragmentShopRentSupport;
    @BindView(R.id.fragment_shop_rent_note)
    EditText etNote;
    @BindView(R.id.fragment_shop_rent_ispropertyfee)
    AppCompatSpinner fragmentShopRentIspropertyfee;
    @BindView(R.id.fragment_shop_rent_shopstate)
    AppCompatSpinner fragmentShopRentShopstate;
    @BindView(R.id.fragment_shop_rent_shoptype)
    AppCompatSpinner fragmentShopRentShoptype;
    @BindView(R.id.fragment_shop_rent_istransfer)
    AppCompatSpinner fragmentShopRentIstransfer;
    @BindView(R.id.fragment_shop_rent_iscession)
    AppCompatSpinner fragmentShopRentIscession;
    @BindView(R.id.fragment_shop_rent_decoration)
    AppCompatSpinner fragmentShopRentDecoration;
    @BindView(R.id.fragment_shop_rent_targetformat)
    AppCompatSpinner fragmentShopRentTargetformat;
    @BindView(R.id.fragment_shop_rent_paytype)
    AppCompatSpinner fragmentShopRentPaytype;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.fragment_shop_rent_yingli)
    EditText etYingli;
    @BindView(R.id.fragment_shop_rent_unit)
    TextView fragmentShopRentUnit;
    @BindView(R.id.sp_quxian_shop_rent)
    AppCompatSpinner spQuxian;
    @BindView(R.id.sp_shangquan_shop_rent)
    AppCompatSpinner spShangquan;

    private House_enterActivity act;
    private Unbinder unbinder;
    private int index = 0;
    private ImageView[] imgs;
    private SupportChectBox supportChectBox;
    private ImageView[] selectphotos;
    private String shop_stating, including_property_fee_or_not, shop_type, payment, target_formats, ceding_or_not, decoration_level, transfer_or_not;
    private String type;
    private TextView[] textViews = new TextView[3];
    private int rentornot;
    private String btype = "佣金";
    private String TAG = "shop_rent";
    private RentShopContants rentShopContants;
    private List<ShangquanBean> shangquanList;
    private int shangQuanId;
    private String[] shangquanStr = new String[3];

    public RentShopContants getRentShopContants() {
        return rentShopContants;
    }

    public void setRentShopContants(RentShopContants rentShopContants) {
        this.rentShopContants = rentShopContants;
    }

    public House_enter_shop_rent_Fragment() {
    }

    public House_enter_shop_rent_Fragment(String type, int rentornot, int houseid, boolean isedit) {
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
        if (null == rentShopContants)
            return;
        RentShopHouseBean house = rentShopContants.getHouse();
        String house_price = 0 == house.getRent() ? "" : house.getRent() + "";
        String house_area = 0 == house.getHouse_area() ? "" : house.getHouse_area() + "";
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
        this.act = (House_enterActivity) context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.house_enter_fragment_shop_rent_layout, null);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: =====================");
        allEdit = new EditText[]{etPosition,etPrice,etSize,etPropertyFee,etYingli};
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
            rentShopContants = RentShopContants.objectFromData(string);
            return rentShopContants;
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
        Log.d(TAG, "onActivityCreated: =======================");
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
        textViews[0] = fragmentShopRentYongjin;
        textViews[1] = fragmentShopRentJingde;
        textViews[2] = fragmentShopRentFanfei;


    }

    private void initselectimgs() {
        Log.d(TAG, "initselectimgs: ------------------");
        selectphotos = new ImageView[]{fragmentShopRentHouseimg1, fragmentShopRentHouseimg2, fragmentShopRentHouseimg3,
                fragmentShopRentHouseimg4, fragmentShopRentHouseimg5, fragmentShopRentHouseimg6,
                fragmentShopRentHouseimg7, fragmentShopRentHouseimg8};
    }

    private void initListener() {
        imgs = new ImageView[]{fragmentShopRentHouseimg1, fragmentShopRentHouseimg2, fragmentShopRentHouseimg3, fragmentShopRentHouseimg4, fragmentShopRentHouseimg5, fragmentShopRentHouseimg6, fragmentShopRentHouseimg7, fragmentShopRentHouseimg8};
        for (int i = 0; i < imgs.length; i++) {
            imgs[i].setOnClickListener(this);

        }
        fragmentShopRentFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyWheelViewDialog wheelViewDialog = new MyWheelViewDialog(act, MyWheelViewDialog.FLOORS);
                wheelViewDialog.initWheel1(act, MyWheelViewDialog.FLOORS);
                wheelViewDialog.showWheelDialog();
                wheelViewDialog.InitWheelViewCommit(fragmentShopRentFloorText, MyWheelViewDialog.FLOORS);
            }
        });
        fragmentShopRentSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (supportChectBox == null) {
                    supportChectBox = new SupportChectBox(act);
                }
                supportChectBox.showDialog();
            }
        });
        fragmentShopRentIspropertyfee.setOnItemSelectedListener(this);
        fragmentShopRentShopstate.setOnItemSelectedListener(this);
        fragmentShopRentShoptype.setOnItemSelectedListener(this);
        fragmentShopRentIstransfer.setOnItemSelectedListener(this);
        fragmentShopRentIscession.setOnItemSelectedListener(this);
        fragmentShopRentDecoration.setOnItemSelectedListener(this);
        fragmentShopRentPaytype.setOnItemSelectedListener(this);
        fragmentShopRentTargetformat.setOnItemSelectedListener(this);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fragment_shop_rent_commit, R.id.fragment_shop_rent_yongjin, R.id.fragment_shop_rent_jingde, R.id.fragment_shop_rent_fanfei})
    public void onViewClicked(View view) {
        int k = 0;
        switch (view.getId()) {
            case R.id.fragment_shop_rent_commit:
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
                String floor = TextContentUtils.getViewContent(fragmentShopRentFloorText);
                String bvalue = TextContentUtils.getViewContent(etYingli) + TextContentUtils.getViewContent(fragmentShopRentUnit);
                String property_fee = TextContentUtils.getViewContent(etPropertyFee);
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
                        .addFormDataPart("rent", houseprice)
                        .addFormDataPart("transfer_or_not", transfer_or_not)
                        .addFormDataPart("house_area", housearea)
                        .addFormDataPart("payment", payment + "")
                        .addFormDataPart("shop_stating", shop_stating)
                        .addFormDataPart("ceding_or_not", ceding_or_not)
                        .addFormDataPart("target_formats", target_formats)
                        .addFormDataPart("decoration_level", decoration_level)
                        .addFormDataPart("supporting_facility", supporting)
                        .addFormDataPart("floor", floor)
                        .addFormDataPart("user_id", userid + "")
                        .addFormDataPart("including_property_fee_or_not", including_property_fee_or_not)
                        .addFormDataPart("note", note)
                        .addFormDataPart("shop_type", shop_type)
                        .addFormDataPart("property_fee", property_fee)
                        .addFormDataPart("sourcetype", type)
                        .addFormDataPart("rentornot", rentornot + "")
                        .addFormDataPart("leixing", btype)
                        .addFormDataPart("bvalue", bvalue)
                        .addFormDataPart("shangquanid", shangQuanId + "");
                MultipartBody mpb = builder.build();
                OkHttpUtils.okhttpPostWithHead(UserInf.getUserToken(act),
                        UserInf.getUserPhonenum(act),
                        mpb, BaseUrl.URL + "shop_rents", new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.d(TAG, "onFailure: ---------------------e.tostring = " + e.getMessage());
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                EventBus.getDefault().post(new HideLodingEvent());
                                Intent mIntent = new Intent(act, FindHouseActivity.class);
                                mIntent.putExtra("flag", FindHouseContants.SHOP_RENT);
                                startActivity(mIntent);
                                act.finish();
                            }
                        });

                break;
            case R.id.fragment_shop_rent_yongjin:
                k = 0;
                btype = "佣金";
                break;
            case R.id.fragment_shop_rent_jingde:
                k = 1;
                btype = "净得";
                break;
            case R.id.fragment_shop_rent_fanfei:
                k = 2;
                btype = "返费";
                break;
        }
        if (getId() != R.id.fragment_shop_rent_commit) {
            YingLiUtils.selectTextViews(act, textViews, k, etYingli, fragmentShopRentUnit);
        }
    }

    private void putChanges() {
        FormBody.Builder builder = new FormBody.Builder();
        RentShopHouseBean house = rentShopContants.getHouse();

        String houseprice = TextContentUtils.getViewContent(etPrice);
        if (house.getRent() + "" != houseprice && "" != houseprice)
            builder.add("shop_rent[rent]", houseprice);

        String housearea = TextContentUtils.getViewContent(etSize);
        if (house.getHouse_area() + "" != housearea && "" != housearea)
            builder.add("shop_rent[house_area]", housearea);

        String floor = TextContentUtils.getViewContent(fragmentShopRentFloorText);
        if (house.getFloor() + "" != floor && "" != floor)
            builder.add("shop_rent[floor]", floor);

        String bvalue = TextContentUtils.getViewContent(etYingli) + TextContentUtils.getViewContent(fragmentShopRentUnit);
        if (house.getBvalue() + "" != bvalue && "" != bvalue)
            builder.add("shop_rent[bvalue]", bvalue);

        String property_fee = TextContentUtils.getViewContent(etPropertyFee);
        if (house.getProperty_fee() + "" != property_fee && "" != property_fee)
            builder.add("shop_rent[property_fee]", property_fee);

        String supporting;
        if (supportChectBox == null) {
            supporting = "";
        } else {
            supporting = supportChectBox.getSelectText();
        }
        if (house.getSupporting_facility() + "" != supporting && "" != supporting)
            builder.add("shop_rent[supporting_facility]", supporting);

        String note = TextContentUtils.getViewContent(etNote);
        if (house.getNote() + "" != note && "" != note)
            builder.add("shop_rent[note]", note);
        if (house.getTransfer_or_not() + "" != transfer_or_not && "" != transfer_or_not)
            builder.add("shop_rent[transfer_or_not]", transfer_or_not);
        if (house.getLeixing() + "" != btype && "" != btype)
            builder.add("shop_rent[leixing]", btype);

        FormBody formBody = builder.build();
        OkHttpUtils.okhttpPut(BaseUrl.URL + "shop_rents", formBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Intent mIntent = new Intent(act, FindHouseActivity.class);
                mIntent.putExtra("flag", FindHouseContants.SHOP_RENT);
                startActivity(mIntent);
                act.finish();
            }
        });
        rentShopContants = null;
        isedit = false;
    }


    private void toFindActivity() {
        Intent mIntent = new Intent(act, FindHouseActivity.class);
        mIntent.putExtra("flag", FindHouseContants.SHOP_RENT);
        startActivity(mIntent);
        act.finish();
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
            case R.id.fragment_shop_rent_houseimg1:
                index = 0;
                break;
            case R.id.fragment_shop_rent_houseimg2:
                index = 1;
                break;
            case R.id.fragment_shop_rent_houseimg3:
                index = 2;
                break;
            case R.id.fragment_shop_rent_houseimg4:
                index = 3;
                break;
            case R.id.fragment_shop_rent_houseimg5:
                index = 4;
                break;
            case R.id.fragment_shop_rent_houseimg6:
                index = 5;
                break;
            case R.id.fragment_shop_rent_houseimg7:
                index = 6;
                break;
            case R.id.fragment_shop_rent_houseimg8:
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
        Log.d("shop_rent_fragment", "onItemSelected: ========================");
        switch (adapterView.getId()) {
            case R.id.fragment_shop_rent_ispropertyfee:
                including_property_fee_or_not = fragmentShopRentIspropertyfee.getItemAtPosition(i).toString();
                break;
            case R.id.fragment_shop_rent_shopstate:
                shop_stating = fragmentShopRentShopstate.getItemAtPosition(i).toString();
                break;
            case R.id.fragment_shop_rent_shoptype:
                shop_type = fragmentShopRentShoptype.getItemAtPosition(i).toString();
                break;
            case R.id.fragment_shop_rent_istransfer:
                transfer_or_not = fragmentShopRentTargetformat.getItemAtPosition(i).toString();
                break;
            case R.id.fragment_shop_rent_iscession:
                ceding_or_not = fragmentShopRentIscession.getItemAtPosition(i).toString();
                break;
            case R.id.fragment_shop_rent_decoration:
                decoration_level = fragmentShopRentDecoration.getItemAtPosition(i).toString();
                break;
            case R.id.fragment_shop_rent_targetformat:
                target_formats = fragmentShopRentTargetformat.getItemAtPosition(i).toString();
                break;
            case R.id.fragment_shop_rent_paytype:
                payment = fragmentShopRentPaytype.getItemAtPosition(i).toString();
                break;

        }
    }

    private String getAppCompateSpinnerText(AppCompatSpinner spinner, int position) {
        String selectItem = spinner.getSelectedItem().toString();
        if (null != selectItem)
            return selectItem;
        else
            return "";
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        adapterView.setSelection(0);
    }
}
