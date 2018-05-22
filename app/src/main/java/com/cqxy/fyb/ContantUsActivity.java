package com.cqxy.fyb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqxy.view.ContactDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContantUsActivity extends AppCompatActivity {

    @BindView(R.id.house_enter_back)
    ImageView houseEnterBack;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.contant_emial)
    TextView contantEmial;
    @BindView(R.id.contant_qq)
    TextView contantQq;
    @BindView(R.id.contant_worktime)
    TextView contantWorktime;
    @BindView(R.id.btn_cooperations)
    Button btnCooperations;

    @BindView(R.id.contact_erweima_img)
    ImageView contactErweimaImg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contant_us);
        ButterKnife.bind(this);
        titleText.setText("联系我们");
    }

    @OnClick({R.id.house_enter_back,R.id.btn_cooperations})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.house_enter_back:
                this.finish();
                break;
            case R.id.btn_cooperations:
                ContactDialog contactDialog = new ContactDialog(this);
                contactDialog.show();
                break;



        }
    }
}
