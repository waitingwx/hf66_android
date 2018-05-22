package com.cqxy.fyb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cqxy.adapter.MyArrayAdapter;
import com.cqxy.base.BaseUrl;
import com.cqxy.bean.PolicyBean;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.UserInf;
import com.cqxy.view.PolicyContentDialog;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PolicyActivity extends AppCompatActivity {


    @BindView(R.id.house_enter_back)
    ImageView houseEnterBack;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.lv_policies)
    ListView lvPolicies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);
        ButterKnife.bind(this);
        titleText.setText("相关政策");
        String policyUrl = BaseUrl.URL+"policies";
        OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(this),UserInf.getUserPhonenum(this),policyUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                Log.d("PolicyActivity", "onResponse: =============responseBody -= " +responseBody);
                final ArrayList<PolicyBean.CommentsBean> comments = (ArrayList<PolicyBean.CommentsBean>) PolicyBean.objectFromData(responseBody).getComments();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final MyArrayAdapter adapter = new MyArrayAdapter(PolicyActivity.this, comments);
                        lvPolicies.setAdapter(adapter);
//                        lvPolicies.setDividerHeight(ScaleUtil.dp2px(PolicyActivity.this,4));
                        lvPolicies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                PolicyBean.CommentsBean commentsBean = comments.get(position);
                                PolicyContentDialog policyContentDialog = new PolicyContentDialog(PolicyActivity.this, commentsBean);
                                policyContentDialog.show(getSupportFragmentManager(),"");
                            }
                        });
                    }
                });
            }
        });
    }



    @OnClick(R.id.house_enter_back)
    public void onViewClicked() {
        this.finish();
    }
}
