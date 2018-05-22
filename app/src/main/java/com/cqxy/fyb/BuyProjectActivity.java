package com.cqxy.fyb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxy.base.BaseUrl;
import com.cqxy.bean.PayorderBean;
import com.cqxy.bean.ProjectBean;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.UserInf;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

public class BuyProjectActivity extends AppCompatActivity {
    //
    @BindView(R.id.house_buyproject_back)
    ImageView houseBuyprojectBack;
    @BindView(R.id.buyproject_customservice)
    TextView buyprojectCustomservice;
    @BindView(R.id.tv_monthfee)
    TextView tvMonthfee;
    @BindView(R.id.tv_yealfee)
    TextView tvYealfee;
    @BindView(R.id.tv_quarterfee)
    TextView tvQuartefee;
    @BindView(R.id.tv_yearfee)
    TextView tvYearfee;
    @BindView(R.id.ll_weixin)
    LinearLayout llWeixin;
    @BindView(R.id.ll_month)
    LinearLayout llMonth;
    @BindView(R.id.ll_sesaon)
    LinearLayout llSesaon;
    @BindView(R.id.ll_halfyear)
    LinearLayout llHalfYear;
    @BindView(R.id.ll_year)
    LinearLayout llYear;
    private static String TAG = "BuyProject";
    private IWXAPI mWXApi;
    int index = 0;

    private LinearLayout layouts[] = new LinearLayout[4];
    private int[] selectimgs_h = {R.mipmap.products_the_price_months_h, R.mipmap.products_the_price_season_h, R.mipmap.products_the_price_monthly_h, R.mipmap.products_the_price_year_h};
    private int[] selectimgs_n = {R.mipmap.products_the_price_months_n, R.mipmap.products_the_price_season_n, R.mipmap.products_the_price_monthly_n, R.mipmap.products_the_price_year_n};
    private TextView[] textviews = new TextView[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_project);
        ButterKnife.bind(this);
        initTextViews();
        initLayous();
        initWxApi();
        loadPrices();
    }

    private void loadPrices() {
        OkHttpUtils.okhttpGet(BaseUrl.URL + "payorders", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responsebody = response.body().string();
                Log.d(TAG, "onResponse: resonsebody = " + responsebody);
                final List<PayorderBean> payorder = PayorderBean.arrayPayorderBeanFromData(responsebody, "payorder");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < payorder.size(); i++) {
                            PayorderBean payorderBean = payorder.get(i);
                            double money = payorderBean.getMoney() * 0.01;
                            switch (payorderBean.getQuarter()) {
                                case 1:
                                    tvMonthfee.setText(money + " 元");
                                    break;
                                case 3:
                                    tvQuartefee.setText(money + " 元");
                                    break;
                                case 6:
                                    tvYealfee.setText(money + " 元");
                                    break;
                                case 12:
                                    tvYearfee.setText(money + " 元");
                                    break;
                            }
                        }
                    }
                });
            }
        });
    }

    private void initWxApi() {
        mWXApi = WXAPIFactory.createWXAPI(this, null);
        mWXApi.registerApp("wx169ac11d00fd5940");
    }

    private void initTextViews() {
        textviews[0] = tvMonthfee;
        textviews[1] = tvQuartefee;
        textviews[2] = tvYealfee;
        textviews[3] = tvYearfee;
    }

    private void initLayous() {
        layouts[0] = llMonth;
        layouts[1] = llSesaon;
        layouts[2] = llHalfYear;
        layouts[3] = llYear;
    }

    @OnClick({R.id.house_buyproject_back,
            R.id.buyproject_customservice,
            R.id.ll_month,
            R.id.ll_sesaon,
            R.id.ll_halfyear,
            R.id.ll_year,
            R.id.ll_weixin})
    public void onViewClicked(View view) {
        String payWay = "微信";

        switch (view.getId()) {
            case R.id.house_buyproject_back:
                this.finish();
                break;
            case R.id.buyproject_customservice:
                Toast.makeText(this, "您的问题已提交，稍后与您联系", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_month:
                index = 0;
                break;
            case R.id.ll_sesaon:
                index = 1;
                break;
            case R.id.ll_halfyear:
                index = 2;
                break;
            case R.id.ll_year:
                index = 3;
                break;
            case R.id.ll_weixin:
                payWay = "微信";
                postPayment(payWay, index);
                break;

        }
        if (view.getId() != R.id.house_buyproject_back) {
            selectImgBg(index);
        }
    }


    private void postPayment(String payWay, int index) {
//        int money = Integer.valueOf(textviews[index].getText().toString().split(" ")[0]);
        int money = (int) (Double.valueOf(textviews[index].getText().toString().split(" ")[0]) * 100);
        Log.d(TAG, "postPayment: ----------------money = " + money);
        String quarter = switchIndex(index);
        FormBody formBody = new FormBody.Builder()
                .add("details", payWay)
                .add("user_id", UserInf.getUserId(BuyProjectActivity.this) + "")
                .add("transaction_amount", money+"")
                .add("quarter",quarter).build();
        OkHttpUtils.okhttpPostWithHead(UserInf.getUserToken(BuyProjectActivity.this),
                UserInf.getUserPhonenum(BuyProjectActivity.this),
                formBody, BaseUrl.URL + "detailed_lists", new Callback() {


                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseBody = response.body().string();
                        Log.d(TAG, "onResponse: ---------response.body = " + responseBody);
                        if (responseBody.startsWith("<"))
                            return;
                        ProjectBean projectBean = ProjectBean.objectFromData(responseBody);
                        final String wxreponse = projectBean.getDetailedlist().getWxresponse().toString();

                        Log.d(TAG, "onResponse: ---------wxreponse = " + wxreponse);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                toweixin(wxreponse);
                            }
                        });

                    }
                });
    }

    private String switchIndex(int index) {
        String quater = "";
        switch (index) {
            case 0:
                quater = 1 + "";
                break;
            case 1:
                quater = 3 + "";
                break;
            case 2:
                quater = 6 + "";
                break;
            case 3:
                quater = 12 + "";
                break;
        }
        return quater;
    }


    private void toweixin(String wxreponse) {
        sendRequest(wxreponse);
    }

    private void sendRequest(String wxreponse) {
        Log.d(TAG, "sendRequest: =================string111 = " + wxreponse);
        try {
            JSONObject param = new JSONObject(wxreponse);
            if (TextUtils.isEmpty(param.optString("appid")) || TextUtils.isEmpty(param.optString("partnerid"))
                    || TextUtils.isEmpty(param.optString("prepayid")) || TextUtils.isEmpty(param.optString("package")) ||
                    TextUtils.isEmpty(param.optString("noncestr")) || TextUtils.isEmpty(param.optString("timestamp")) ||
                    TextUtils.isEmpty(param.optString("sign")))
                return;
            Log.d(TAG, "sendRequest: ======================================222");
            PayReq req = new PayReq();
            req.appId = param.optString("appid");
            req.partnerId = param.optString("partnerid");
            req.prepayId = param.optString("prepayid");
            req.packageValue = param.optString("package");
            req.nonceStr = param.optString("noncestr");
            req.timeStamp = param.optString("timestamp");
            req.sign = param.optString("sign");
            Log.d(TAG, "sendRequest: ---------------------------send----333");
            mWXApi.sendReq(req);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void selectImgBg(int index) {
        for (int i = 0; i < 4; i++) {
            if (i == index) {
                layouts[i].setBackgroundResource(selectimgs_h[i]);
                textviews[i].setTextColor(getResources().getColor(R.color.colorRed));
            } else {
                layouts[i].setBackgroundResource(selectimgs_n[i]);
                textviews[i].setTextColor(getResources().getColor(R.color.gray_normal));
            }
        }
    }

}
