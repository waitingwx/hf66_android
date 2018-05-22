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
import android.widget.Toast;

import com.cqxy.base.BaseUrl;
import com.cqxy.bean.HideLodingEvent;
import com.cqxy.bean.RentResidentHouseBean;
import com.cqxy.bean.ShowLoadingEvent;
import com.cqxy.constants.FindHouseContants;
import com.cqxy.constants.RentResidentConstants;
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

public class House_enter_residence_rent_Fragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, DropListView.LoadChooseDataListener {
    //调用系统相册-选择图片
    protected static final int REQUEST_CODE_LOCAL = 1;
    private EditText[] allEdit;
    @BindView(R.id.fragment_residence_rent_houseprice)
    EditText etPrice;
    @BindView(R.id.fragment_residence_rent_housesize)
    EditText etHouseSize;
    @BindView(R.id.fragment_residence_rent_housetype_text)
    TextView fragmentResidenceRentHousetypeText;
    @BindView(R.id.fragment_residence_rent_housetype)
    ImageView fragmentResidenceRentHousetype;
    @BindView(R.id.fragment_residence_rent_orientation_text)
    TextView fragmentResidenceRentOrientationText;
    @BindView(R.id.fragment_residence_rent_orientation)
    ImageView fragmentResidenceRentOrientation;
    @BindView(R.id.fragment_residence_rent_yongjin)
    TextView fragmentResidenceRentYongjin;
    @BindView(R.id.fragment_residence_rent_jingde)
    TextView fragmentResidenceRentJingde;
    @BindView(R.id.fragment_residence_rent_fanfei)
    TextView fragmentResidenceRentFanfei;
    @BindView(R.id.fragment_residence_rent_commit)
    Button fragmentResidenceRentCommit;
    @BindView(R.id.select_button1)
    RadioButton selectButton1;
    @BindView(R.id.select_button2)
    RadioButton selectButton2;
    @BindView(R.id.select_group)
    SegmentedGroup selectGroup;
    @BindView(R.id.fragment_residence_rent_houseimg1)
    ImageView fragmentResidenceRentHouseimg1;
    @BindView(R.id.fragment_residence_rent_houseimg2)
    ImageView fragmentResidenceRentHouseimg2;
    @BindView(R.id.fragment_residence_rent_houseimg3)
    ImageView fragmentResidenceRentHouseimg3;
    @BindView(R.id.fragment_residence_rent_houseimg4)
    ImageView fragmentResidenceRentHouseimg4;
    @BindView(R.id.fragment_residence_rent_houseimg5)
    ImageView fragmentResidenceRentHouseimg5;
    @BindView(R.id.fragment_residence_rent_houseimg6)
    ImageView fragmentResidenceRentHouseimg6;
    @BindView(R.id.fragment_residence_rent_houseimg7)
    ImageView fragmentResidenceRentHouseimg7;
    @BindView(R.id.fragment_residence_rent_houseimg8)
    ImageView fragmentResidenceRentHouseimg8;
    @BindView(R.id.fragment_residence_rent_floor_text)
    TextView fragmentResidenceRentFloorText;
    @BindView(R.id.fragment_residence_rent_floor)
    ImageView fragmentResidenceRentFloor;
    @BindView(R.id.fragment_residence_rent_support)
    ImageView fragmentResidenceRentSupport;
    @BindView(R.id.fragment_residence_rent_note)
    EditText etNote;
    @BindView(R.id.fragment_residence_rent_paytype)
    AppCompatSpinner fragmentResidenceRentPaytype;
    @BindView(R.id.fragment_residence_rent_decoration)
    AppCompatSpinner fragmentResidenceRentDecoration;
    @BindView(R.id.fragment_residence_rent_yingli)
    EditText etYingli;
    @BindView(R.id.fragment_residence_rent_unit)
    TextView fragmentResidenceRentUnit;
    private TextView tvXiaoqu;

    private EditText etDetail;
    private int xiaoquid;
    private String result;

    private String leaseway = "整租";
    private House_enterActivity act;
    private Unbinder unbinder;
    private int index = 0;
    private ImageView[] imgs;
    private SupportChectBox supportChectBox;
    private ImageView[] selectphotos;
    private String payment, decorationlevel;
    private String type;
    private TextView[] textViews = new TextView[3];
    private int rentornot;
    private String btype = "佣金";
    private String TAG = "residence_rent";
    private RentResidentConstants rentResidentConstants;

    public RentResidentConstants getRentResidentConstants() {
        return rentResidentConstants;
    }

    public void setRentResidentConstants(RentResidentConstants rentResidentConstants) {
        this.rentResidentConstants = rentResidentConstants;
    }

    public House_enter_residence_rent_Fragment(String type, int rentornot, int houseid, boolean isedit) {
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
            rentResidentConstants = RentResidentConstants.objectFromData(string);
            return rentResidentConstants;
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
        if (null == rentResidentConstants)
            return;
        RentResidentHouseBean house = rentResidentConstants.getHouse();
        String house_price = 0 == house.getRent() ? "" : house.getRent() + "";
        String house_area = null == house.getHouse_area() ? "" : house.getHouse_area() + "";
        String note = null == house.getNote() ? "" : house.getNote();
        String position = null == house.getPosition() ? "" : house.getPosition();
        String housedetail = null == house.getDetialedaddress() ? "" : house.getDetialedaddress();
        tvXiaoqu.setText(position);
        etDetail.setText(housedetail);
        etPrice.setText(house_price);
        etHouseSize.setText(house_area);
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
        Log.d(TAG, "onCreateView: ----------------------");
        View inflate = inflater.inflate(R.layout.house_enter_fragment_residence_rent_layout, null);
        unbinder = ButterKnife.bind(this, inflate);
        tvXiaoqu = inflate.findViewById(R.id.tv_xiaoqu);
        etDetail = inflate.findViewById(R.id.et_detail_address);
        Log.d(TAG, "onCreateView: ---------------------------------tvxiaoqu");
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allEdit = new EditText[]{etDetail, etPrice, etHouseSize, etYingli};
        Log.d(TAG, "onViewCreated: =====================");
        if (isedit)
            it.execute();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initselectimgs();
        initTextViews();
        initListener();

    }


    private void initTextViews() {
        textViews[0] = fragmentResidenceRentYongjin;
        textViews[1] = fragmentResidenceRentJingde;
        textViews[2] = fragmentResidenceRentFanfei;


    }

    private void initselectimgs() {
        selectphotos = new ImageView[]{fragmentResidenceRentHouseimg1, fragmentResidenceRentHouseimg2, fragmentResidenceRentHouseimg3,
                fragmentResidenceRentHouseimg4, fragmentResidenceRentHouseimg5, fragmentResidenceRentHouseimg6,
                fragmentResidenceRentHouseimg7, fragmentResidenceRentHouseimg8};
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

        imgs = new ImageView[]{fragmentResidenceRentHouseimg1, fragmentResidenceRentHouseimg2, fragmentResidenceRentHouseimg3, fragmentResidenceRentHouseimg4, fragmentResidenceRentHouseimg5, fragmentResidenceRentHouseimg6, fragmentResidenceRentHouseimg7, fragmentResidenceRentHouseimg8};
        for (int i = 0; i < imgs.length; i++) {
            imgs[i].setOnClickListener(this);

        }
        fragmentResidenceRentFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyWheelViewDialog wheelViewDialog = new MyWheelViewDialog(act, MyWheelViewDialog.FLOORS);
                wheelViewDialog.initWheel1(act, MyWheelViewDialog.FLOORS);
                wheelViewDialog.showWheelDialog();
                wheelViewDialog.InitWheelViewCommit(fragmentResidenceRentFloorText, MyWheelViewDialog.FLOORS);
            }
        });
        fragmentResidenceRentOrientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyWheelViewDialog wheelViewDialog = new MyWheelViewDialog(act, MyWheelViewDialog.FLOORS);
                wheelViewDialog.initWheel1(act, MyWheelViewDialog.ORIENTATIONS);
                wheelViewDialog.showWheelDialog();
                wheelViewDialog.InitWheelViewCommit(fragmentResidenceRentOrientationText, MyWheelViewDialog.FLOORS);
            }
        });
        fragmentResidenceRentSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (supportChectBox == null) {
                    supportChectBox = new SupportChectBox(act);
                }
                supportChectBox.showDialog();
            }
        });

        fragmentResidenceRentHousetype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyWheelViewDialog wheelViewDialog = new MyWheelViewDialog(act, MyWheelViewDialog.HOUSETYPE);
                wheelViewDialog.initWheel1(act, MyWheelViewDialog.HOUSETYPE);
                wheelViewDialog.showWheelDialog();
                wheelViewDialog.InitWheelViewCommit(fragmentResidenceRentHousetypeText, MyWheelViewDialog.HOUSETYPE);
            }
        });
        fragmentResidenceRentPaytype.setOnItemSelectedListener(this);
        fragmentResidenceRentDecoration.setOnItemSelectedListener(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fragment_residence_rent_commit,
            R.id.fragment_residence_rent_yongjin,
            R.id.fragment_residence_rent_jingde,
            R.id.fragment_residence_rent_fanfei,
            R.id.tv_xiaoqu})
    public void onViewClicked(View view) {
        int k = 0;
        switch (view.getId()) {
            case R.id.fragment_residence_rent_commit:
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
                String housedetail = TextContentUtils.getViewContent(etDetail);
                String position = result;
                String houseprice = TextContentUtils.getViewContent(etPrice);
                String housearea = TextContentUtils.getViewContent(etHouseSize);
                String housetype = TextContentUtils.getViewContent(fragmentResidenceRentHousetypeText);
                String floor = TextContentUtils.getViewContent(fragmentResidenceRentFloorText);
                String supporting;
                if (supportChectBox == null) {
                    supporting = "";
                } else {
                    supporting = supportChectBox.getSelectText();
                }

                int userid = UserInf.getUserId(act);
                String aspect = TextContentUtils.getViewContent(fragmentResidenceRentOrientationText);
                String note = TextContentUtils.getViewContent(etNote);
                String bvalue = TextContentUtils.getViewContent(etYingli) + TextContentUtils.getViewContent(fragmentResidenceRentUnit);
                Log.e("----BTYPE:", btype + "---bvalue:" + bvalue);
                Log.e("Token：", UserInf.getUserToken(act) + "phone:" + UserInf.getUserPhonenum(act));
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
                        .addFormDataPart("aspect", aspect)
                        .addFormDataPart("note", note)
                        .addFormDataPart("sourcetype", type)
                        .addFormDataPart("rentornot", rentornot + "")
                        .addFormDataPart("leixing", btype)
                        .addFormDataPart("bvalue", bvalue)
                        .addFormDataPart("xiaoquid", xiaoquid + "")
                        .addFormDataPart("detailedaddress", housedetail);
                Log.d(TAG, "onViewClicked: -----------------------------xiaoquid = " + xiaoquid + ",housedetail = " + housedetail);
                MultipartBody mpb = builder.build();
                OkHttpUtils.okhttpPostWithHead(UserInf.getUserToken(act),
                        UserInf.getUserPhonenum(act),
                        mpb, BaseUrl.URL + "residence_rents", new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                final String error = e.toString();
                                Log.d(TAG, "onFailure: -----------e.tostring = " + error);
                                act.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(act, error, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                EventBus.getDefault().post(new HideLodingEvent());
                                String responseBody = response.body().string();
                                Log.d(TAG, "onResponse: --------------responseBody = " + responseBody);
                                Intent mIntent = new Intent(act, FindHouseActivity.class);
                                mIntent.putExtra("flag", FindHouseContants.RESIDENCE_RENT);
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
            case R.id.tv_xiaoqu:
                if (isedit)
                    return;
                Intent intent = new Intent(act, ChooseActivity.class);
                startActivityForResult(intent, 0x001);
                break;
        }
        if (getId() != R.id.fragment_residence_rent_commit) {
            YingLiUtils.selectTextViews(act, textViews, k, etYingli, fragmentResidenceRentUnit);
        }
    }

    private void putChanges() {
        RentResidentHouseBean house = rentResidentConstants.getHouse();
        FormBody.Builder builder = new FormBody.Builder();
        String houseprice = TextContentUtils.getViewContent(etPrice);

        if (house.getRent() + "" != houseprice && houseprice != "")
            builder.add("residence_rent[rent]", houseprice);
        String housedetail = TextContentUtils.getViewContent(etDetail);
        if (house.getDetialedaddress() + "" != housedetail && housedetail != "")
            builder.add("residence_rent[detialedaddress]", housedetail);
        String housearea = TextContentUtils.getViewContent(etHouseSize);
        if (house.getHouse_area() + "" != housearea && housearea != "")
            builder.add("residence_rent[house_area]", housearea);
        String housetype = TextContentUtils.getViewContent(fragmentResidenceRentHousetypeText);
        if (house.getHouse_type() + "" != housetype && housetype != "")
            builder.add("residence_rent[house_type]", housetype);
        String floor = TextContentUtils.getViewContent(fragmentResidenceRentFloorText);
        if (house.getFloor() + "" != floor && floor != "")
            builder.add("residence_rent[floor]", floor);

        String supporting;
        if (supportChectBox == null) {
            supporting = "";
        } else {
            supporting = supportChectBox.getSelectText();
        }
        if (house.getSupporting_facility() + "" != supporting && supporting != "")
            builder.add("residence_rent[supporting_facility]", supporting);
        String note = TextContentUtils.getViewContent(etNote);
        if (house.getNote() + "" != note && note != "")
            builder.add("residence_rent[note]", note);

        String aspect = TextContentUtils.getViewContent(fragmentResidenceRentOrientationText);

        String bvalue = TextContentUtils.getViewContent(etYingli) + TextContentUtils.getViewContent(fragmentResidenceRentUnit);
        if (house.getBvalue() + "" != bvalue && "%" != bvalue && "元" != bvalue)
            builder.add("residence_rent[bvalue]", bvalue);

        if (house.getLeixing() + "" != btype)
            builder.add("residence_rent[leixing]", btype + "");
        FormBody formBody = builder.build();
        OkHttpUtils.okhttpPut(BaseUrl.URL + "residence_rents", formBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Intent mIntent = new Intent(act, FindHouseActivity.class);
                mIntent.putExtra("flag", FindHouseContants.RESIDENCE_RENT);
                startActivity(mIntent);
                act.finish();
            }
        });
        rentResidentConstants = null;
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
            case R.id.fragment_residence_rent_houseimg1:
                index = 0;
                break;
            case R.id.fragment_residence_rent_houseimg2:
                index = 1;
                break;
            case R.id.fragment_residence_rent_houseimg3:
                index = 2;
                break;
            case R.id.fragment_residence_rent_houseimg4:
                index = 3;
                break;
            case R.id.fragment_residence_rent_houseimg5:
                index = 4;
                break;
            case R.id.fragment_residence_rent_houseimg6:
                index = 5;
                break;
            case R.id.fragment_residence_rent_houseimg7:
                index = 6;
                break;
            case R.id.fragment_residence_rent_houseimg8:
                index = 7;
                break;
        }
        GetImgFromAlbum.selectPicFromLocal(this, REQUEST_CODE_LOCAL);
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
        GetImgFromAlbum.CodeActivityResult(requestCode, resultCode, data, REQUEST_CODE_LOCAL, imgs[index], this, selectphotos, index);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.fragment_residence_rent_paytype:
                payment = fragmentResidenceRentPaytype.getItemAtPosition(i).toString();
                break;
            case R.id.fragment_residence_rent_decoration:
                decorationlevel = fragmentResidenceRentDecoration.getItemAtPosition(i).toString();
                break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void loadData(int xiaoquid) {
        Log.d(TAG, "loadData: -----------------");
    }
}
