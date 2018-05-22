package com.cqxy.fyb;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxy.base.BaseUrl;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.UserInf;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

import static com.cqxy.fyb.R.id.afterservice_commercial;
import static com.cqxy.fyb.R.id.afterservice_commercial_radiogroup;
import static com.cqxy.fyb.R.id.afterservice_residence_funds;
import static com.cqxy.fyb.R.id.afterservice_residence_funds_group;


public class AfterServiceActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.house_enter_back)
    ImageView houseEnterBack;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.afterservice_servicetype)
    TextView afterserviceServicetype;
    @BindView(R.id.afterservice_residence)
    RadioButton afterserviceResidence;
    @BindView(afterservice_commercial)
    RadioButton afterserviceCommercial;
    @BindView(R.id.afterservice_group)
    RadioGroup afterserviceGroup;
    @BindView(R.id.afterservice_residence_fullmoney)
    RadioButton afterserviceResidenceFullmoney;
    @BindView(R.id.afterservice_residence_loan)
    RadioButton afterserviceResidenceLoan;
    @BindView(afterservice_residence_funds)
    RadioButton afterserviceResidenceFunds;
    @BindView(R.id.afterservice_residence_radiogroup)
    RadioGroup afterserviceResidenceRadiogroup;
    @BindView(R.id.afterservice_commercial_fullmoney)
    RadioButton afterserviceCommercialFullmoney;
    @BindView(R.id.afterservice_commercial_radiogroup)
    RadioGroup afterserviceCommercialRadiogroup;
    @BindView(R.id.afterservice_residence_funds_group_state_control)
    RadioButton afterserviceResidenceFundsGroupStateControl;
    @BindView(R.id.afterservice_residence_funds_group_city_control)
    RadioButton afterserviceResidenceFundsGroupCityControl;
    @BindView(R.id.afterservice_residence_funds_group)
    RadioGroup afterserviceResidenceFundsGroup;
    @BindView(R.id.afterservice_fullfee)
    TextView afterserviceFullfee;
    @BindView(R.id.afterservice_loan)
    TextView afterserviceLoan;
    @BindView(R.id.afterservice_funds)
    TextView afterserviceFunds;
    @BindView(R.id.afterservice_commit)
    Button afterserviceCommit;
    private RadioGroup[] radioGroups;
    private RadioButton[] radioButtons;
    private String standardFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_service);
        ButterKnife.bind(this);
        titleText.setText("后期服务");
        initRadios();

    }


    @OnClick({R.id.house_enter_back, R.id.afterservice_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.house_enter_back:
                this.finish();
                break;
            case R.id.afterservice_commit:
                String servicecontent = getCheckedText();
                Toast.makeText(this, "您选择了：" + servicecontent, Toast.LENGTH_SHORT).show();
                String price = null;
                if (servicecontent.contains("全款"))
                    price = "3000";
                if (servicecontent.contains("贷款"))
                    price = "5000";
                if (servicecontent.contains("公积金"))
                    price = "6000";
                FormBody formBody = new FormBody.Builder()
                        .add("user_id", UserInf.getUserId(AfterServiceActivity.this)+"")
                        .add("price", price)
                        .add("servicecontent", servicecontent).build();
                OkHttpUtils.okhttpPostWithHead(UserInf.getUserToken(AfterServiceActivity.this),
                        UserInf.getUserPhonenum(AfterServiceActivity.this),
                        formBody, BaseUrl.URL + "afterservices", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("------res",e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AfterServiceActivity.this,"您已提交成功",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        afterserviceCommercialFullmoney.setChecked(false);
        switch (radioGroup.getId()) {
            case R.id.afterservice_group:
                int index = 0;
                switch (i) {
                    case R.id.afterservice_residence:
                        index = 0;
                        afterserviceResidence.setChecked(true);
                        afterserviceCommercialRadiogroup.clearCheck();
                        break;
                    case afterservice_commercial:
                        index = 1;
                        afterserviceCommercial.setChecked(true);
                        afterserviceResidenceRadiogroup.clearCheck();
                        afterserviceResidenceFundsGroup.clearCheck();
                        break;
                }
                SelectRadio(radioGroups, index);
                break;
            case R.id.afterservice_residence_radiogroup:
                switch (i) {
                    case R.id.afterservice_residence_fullmoney:
                        afterserviceResidenceFundsGroup.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.afterservice_residence_loan:
                        afterserviceResidenceFundsGroup.setVisibility(View.INVISIBLE);
                        break;
                    case afterservice_residence_funds:
                        afterserviceResidenceFundsGroup.setVisibility(View.VISIBLE);
                        break;
                }
                break;
            //疑问
            case afterservice_commercial_radiogroup:
                switch (i) {
                    case R.id.afterservice_commercial_fullmoney:
                        afterserviceCommercialFullmoney.setChecked(true);
                        break;
                }
                break;

            case afterservice_residence_funds_group:
                switch (i) {
                    case R.id.afterservice_residence_funds_group_state_control:
                        afterserviceResidenceFundsGroupStateControl.setChecked(true);
                        afterserviceResidenceFundsGroupCityControl.setChecked(false);
                        break;
                    case R.id.afterservice_residence_funds_group_city_control:
                        afterserviceResidenceFundsGroupCityControl.setChecked(true);
                        afterserviceResidenceFundsGroupStateControl.setChecked(false);
                        break;
                }
                break;
        }


    }

    public void SelectRadio(RadioGroup[] radioGroup, int index) {
        for (int i = 0; i < radioGroup.length; i++) {
            if (i == index) {
                radioGroup[i].setVisibility(View.VISIBLE);
            } else {
                radioGroup[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    public String getSelectRadioButton(RadioButton[] radiobuttons) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < radiobuttons.length; i++) {
            if (radiobuttons[i].isChecked()) {
                buffer.append(radiobuttons[i].getText() + ",");
            }
        }
        return buffer.toString();
    }

    public void setRadioButtonsCancleSelect(RadioButton[] radiobuttons) {
        for (int i = 0; i < radiobuttons.length; i++) {
            Log.d("cancel", "setRadioButtonsCancleSelect: text = " + radiobuttons[i].getText().toString());
            radiobuttons[i].setChecked(false);
        }
    }

    private void initRadios() {
        radioGroups = new RadioGroup[]{
                afterserviceResidenceRadiogroup,
                afterserviceCommercialRadiogroup,
                afterserviceResidenceFundsGroup};
        radioButtons = new RadioButton[]{afterserviceResidence,
                afterserviceCommercial,
                afterserviceResidenceFullmoney,
                afterserviceResidenceLoan,
                afterserviceResidenceFunds,
                afterserviceCommercialFullmoney,
                afterserviceResidenceFundsGroupStateControl,
                afterserviceResidenceFundsGroupCityControl};
        afterserviceGroup.setOnCheckedChangeListener(this);
        afterserviceResidenceRadiogroup.setOnCheckedChangeListener(this);
        afterserviceCommercialRadiogroup.setOnCheckedChangeListener(this);
        afterserviceResidenceFundsGroup.setOnCheckedChangeListener(this);
        afterserviceCommercialFullmoney.setChecked(false);
    }

    String getCheckedText() {
        StringBuilder sb = new StringBuilder();
        switch (afterserviceGroup.getCheckedRadioButtonId()) {
            case R.id.afterservice_residence:
                sb.append(afterserviceResidence.getText().toString() + ",");
                getResidenceSecondText(sb);
                break;
            case R.id.afterservice_commercial:
                sb.append(afterserviceCommercial.getText().toString() + ",");
                getCommercialSecondText(sb);
                break;
        }
        return sb.toString();
    }

    private void getCommercialSecondText(StringBuilder sb) {
        sb.append(afterserviceCommercialFullmoney.getText().toString());
    }

    private void getResidenceSecondText(StringBuilder sb) {
        switch (afterserviceResidenceRadiogroup.getCheckedRadioButtonId()) {
            case R.id.afterservice_residence_fullmoney:
                sb.append(afterserviceResidenceFullmoney.getText().toString());
                break;
            case R.id.afterservice_residence_loan:
                sb.append(afterserviceResidenceLoan.getText().toString());
                break;
            case R.id.afterservice_residence_funds:
                sb.append(afterserviceResidenceFunds.getText().toString() + ",");
                getThirdText(sb);
                break;
        }
    }

    private void getThirdText(StringBuilder sb) {
        switch (afterserviceResidenceFundsGroup.getCheckedRadioButtonId()) {
            case R.id.afterservice_residence_funds_group_state_control:
                sb.append(afterserviceResidenceFundsGroupStateControl.getText().toString());
                break;
            case R.id.afterservice_residence_funds_group_city_control:
                sb.append(afterserviceResidenceFundsGroupCityControl.getText().toString());
                break;
        }
    }
}
