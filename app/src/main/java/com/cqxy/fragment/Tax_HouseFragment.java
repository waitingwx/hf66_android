package com.cqxy.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cqxy.fyb.R;
import com.cqxy.fyb.TaxFeeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by PeLon on 2017/9/19.
 */

public class Tax_HouseFragment extends Fragment {
    @BindView(R.id.taxfee_price)
    EditText taxfeePrice;
    @BindView(R.id.taxfee_size)
    EditText taxfeeSize;
    @BindView(R.id.taxfee_calculator)
    Button taxfeeCalculator;
    @BindView(R.id.tax_houseprice)
    TextView taxHouseprice;
    @BindView(R.id.tax_shuifei)
    TextView taxShuifei;
    @BindView(R.id.tax_qishui)
    TextView taxQishui;
    @BindView(R.id.tax_yinhuashui)
    TextView taxYinhuashui;
    private TaxFeeActivity act;
    private Unbinder unbinder;
    private boolean isFirst;


    public Tax_HouseFragment(boolean isFirst) {
        this.isFirst = isFirst;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.act = (TaxFeeActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_taxhouse_layout, null);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.taxfee_calculator)
    public void onViewClicked() {
        double money=Float.valueOf(taxfeePrice.getText().toString().trim());
        double size=Float.valueOf(taxfeeSize.getText().toString().trim());

        double houseprice=money*size;
        String price=money * size/10000.0+"";


        taxHouseprice.setText(getTwoDouble(price));
        double qiShui = getQiShui(houseprice);
        taxQishui.setText(getTwoDouble(qiShui+""));
        double yinHuaShui = getYinHuaShui(houseprice);
        taxYinhuashui.setText(getTwoDouble(yinHuaShui+""));
        double shuifei=qiShui+yinHuaShui;
        taxShuifei.setText(getTwoDouble(shuifei+""));

    }

    public double getQiShui(double houseprice)
    {
        if (isFirst)
        {
            return houseprice*0.01;
        }else {
            return houseprice * 0.015;
        }
    }

    public double getYinHuaShui(double houseprice)
    {
        return houseprice*0.0005;
    }

    public String getTwoDouble(String price)
    {
        Log.e("---",price);
        String one = price.substring(0, price.indexOf("."));
        if (price.length()-price.indexOf(".")>2) {
            String two = price.substring(price.indexOf("."), price.indexOf(".") + 3);
            return one+two;
        }
        return  price;

    }

}
