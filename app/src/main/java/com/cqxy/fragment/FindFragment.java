package com.cqxy.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cqxy.constants.FindHouseContants;
import com.cqxy.fyb.FindHouseActivity;
import com.cqxy.fyb.HomeActivity;
import com.cqxy.fyb.R;

/**
 * Created by Administrator on 2017/8/31.
 */

public class FindFragment extends Fragment implements View.OnClickListener {
    private HomeActivity act;
    private LinearLayout llResidenceRent, llVillaRent, llOfficeRent, llShopRent,llResidenceSell, llVillaSell, llOfficeSell, llShopSell;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.act = (HomeActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        llResidenceRent = view.findViewById(R.id.ll_residence_rent);
        llVillaRent = view.findViewById(R.id.ll_villa_rent);
        llOfficeRent = view.findViewById(R.id.ll_office_rent);
        llShopRent = view.findViewById(R.id.ll_shop_rent);
        llResidenceSell = view.findViewById(R.id.ll_residence_sell);
        llVillaSell = view.findViewById(R.id.ll_villa_sell);
        llOfficeSell = view.findViewById(R.id.ll_office_sell);
        llShopSell = view.findViewById(R.id.ll_shop_sell);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initListeners();

    }

    private void initListeners() {
        llResidenceRent.setOnClickListener(this);
        llVillaRent.setOnClickListener(this);
        llShopRent.setOnClickListener(this);
        llOfficeRent.setOnClickListener(this);
        llResidenceSell.setOnClickListener(this);
        llVillaSell.setOnClickListener(this);
        llShopSell.setOnClickListener(this);
        llOfficeSell.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(act, FindHouseActivity.class);
        int i=0;

        switch (view.getId())
        {
            case R.id.ll_residence_rent:
                i= FindHouseContants.RESIDENCE_RENT;
                break;
            case R.id.ll_villa_rent:
                i=FindHouseContants.VILLA_RENT;
                break;
            case R.id.ll_office_rent:
                i=FindHouseContants.OFFICEBUILDING_RENT;
                break;
            case R.id.ll_shop_rent:
                i=FindHouseContants.SHOP_RENT;
                break;
            case R.id.ll_residence_sell:
                i=FindHouseContants.RESIDENCE_SELL;

                break;
            case R.id.ll_villa_sell:
                i=FindHouseContants.VILLA_SELL;
                break;
            case R.id.ll_office_sell:
                i=FindHouseContants.OFFICEBUILDING_SELL;
                break;
            case R.id.ll_shop_sell:
                i=FindHouseContants.SHOP_SELL;
                break;
        }
        intent.putExtra("flag",i);
        startActivity(intent);
    }
}
