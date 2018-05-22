package com.cqxy.fyb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ToolsActivity extends AppCompatActivity {


    @BindView(R.id.tools_taxfee_calculator)
    LinearLayout toolsTaxfeeCalculator;
    @BindView(R.id.tools_houseloan_calculator)
    LinearLayout toolsHouseloanCalculator;
    @BindView(R.id.tools_policy)
    LinearLayout toolsPolicy;
    @BindView(R.id.house_enter_back)
    ImageView houseEnterBack;
    @BindView(R.id.title_text)
    TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);
        ButterKnife.bind(this);
        titleText.setText("常用工具");
    }

    @OnClick({R.id.house_enter_back, R.id.tools_taxfee_calculator, R.id.tools_houseloan_calculator, R.id.tools_policy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.house_enter_back:
                this.finish();
                break;
            case R.id.tools_taxfee_calculator:
                startActivity(new Intent(ToolsActivity.this, TaxFeeActivity.class));
                break;
            case R.id.tools_houseloan_calculator:
                startActivity(new Intent(ToolsActivity.this, HouseLoanActivity.class));
                break;
            case R.id.tools_policy:
                startActivity(new Intent(ToolsActivity.this, PolicyActivity.class));
                break;
        }
    }

}
