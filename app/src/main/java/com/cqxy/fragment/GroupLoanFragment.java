package com.cqxy.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqxy.fyb.HouseLoanActivity;
import com.cqxy.fyb.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by lpl on 17-9-21.
 */

public class GroupLoanFragment extends Fragment {
    @BindView(R.id.Houseloan_grouploan_repaytype)
    TextView HouseloanGrouploanRepaytype;
    @BindView(R.id.Houseloan_grouploan_repaylayout)
    LinearLayout HouseloanGrouploanRepaylayout;
    @BindView(R.id.Houseloan_grouploan_yearnum)
    TextView HouseloanGrouploanYearnum;
    @BindView(R.id.Houseloan_grouploan_yearnumlayout)
    LinearLayout HouseloanGrouploanYearnumlayout;
    @BindView(R.id.Houseloan_grouploan_interesttype)
    TextView HouseloanGrouploanInteresttype;
    @BindView(R.id.Houseloan_grouploan_interestlayout)
    LinearLayout HouseloanGrouploanInterestlayout;
    @BindView(R.id.Houseloan_grouploan_commercialinterest)
    TextView HouseloanGrouploanCommercialinterest;
    @BindView(R.id.Houseloan_grouploan_fundsinterest)
    TextView HouseloanGrouploanFundsinterest;
    @BindView(R.id.Houseloan_grouploan_commercialloanaccount)
    EditText HouseloanGrouploanCommercialloanaccount;
//    @BindView(R.id.Houseloan_grouploan_fundsloanaccount)
//    EditText HouseloanGrouploanFundsloanaccount;
    @BindView(R.id.Houseloan_grouploan_loanaccountlayout)
    LinearLayout HouseloanGrouploanLoanaccountlayout;
    @BindView(R.id.Houseloan_grouploan_calculate)
    Button HouseloanGrouploanCalculate;
    @BindView(R.id.Houseloan_grouploan_houseprice)
    TextView HouseloanGrouploanHouseprice;
    @BindView(R.id.Houseloan_grouploan_loan)
    TextView HouseloanGrouploanLoan;
    @BindView(R.id.Houseloan_grouploan_repayment)
    TextView HouseloanGrouploanRepayment;
    private HouseLoanActivity act;
    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.act = (HouseLoanActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_grouploan_layout, null);
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

    @OnClick({R.id.Houseloan_grouploan_repaylayout, R.id.Houseloan_grouploan_yearnumlayout, R.id.Houseloan_grouploan_interestlayout, R.id.Houseloan_grouploan_calculate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Houseloan_grouploan_repaylayout:
                break;
            case R.id.Houseloan_grouploan_yearnumlayout:
                break;
            case R.id.Houseloan_grouploan_interestlayout:
                break;
            case R.id.Houseloan_grouploan_calculate:
                HouseloanGrouploanHouseprice.setText(12.21+"");
                HouseloanGrouploanLoan.setText(18482+"");
                HouseloanGrouploanRepayment.setText(3112+"");
                break;
        }
    }
}
