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

public class FundslLoanFragment extends Fragment {

    @BindView(R.id.Houseloan_funds_repaytype)
    TextView HouseloanFundsRepaytype;
    @BindView(R.id.Houseloan_funds_repaylayout)
    LinearLayout HouseloanFundsRepaylayout;
    @BindView(R.id.Houseloan_funds_yearnum)
    TextView HouseloanFundsYearnum;
    @BindView(R.id.Houseloan_funds_yearnumlayout)
    LinearLayout HouseloanFundsYearnumlayout;
    @BindView(R.id.Houseloan_funds_interesttype)
    TextView HouseloanFundsInteresttype;
    @BindView(R.id.Houseloan_funds_interestlayout)
    LinearLayout HouseloanFundsInterestlayout;
    @BindView(R.id.Houseloan_funds_interest)
    TextView HouseloanFundsInterest;
    @BindView(R.id.Houseloan_funds_loanaccount)
    EditText HouseloanFundsLoanaccount;
    @BindView(R.id.Houseloan_funds_loanaccountlayout)
    LinearLayout HouseloanFundsLoanaccountlayout;
    @BindView(R.id.Houseloan_funds_calculate)
    Button HouseloanFundsCalculate;
    @BindView(R.id.Houseloan_fundsloan_houseprice)
    TextView HouseloanFundsloanHouseprice;
    @BindView(R.id.Houseloan_fundsloan_loan)
    TextView HouseloanFundsloanLoan;
    @BindView(R.id.Houseloan_fundsloan_repayment)
    TextView HouseloanFundsloanRepayment;
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
        View inflate = inflater.inflate(R.layout.fragment_fundsloan_layout, null);
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

    @OnClick({R.id.Houseloan_funds_repaylayout, R.id.Houseloan_funds_yearnumlayout, R.id.Houseloan_funds_interestlayout, R.id.Houseloan_funds_calculate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Houseloan_funds_repaylayout:
                break;
            case R.id.Houseloan_funds_yearnumlayout:
                break;
            case R.id.Houseloan_funds_interestlayout:
                break;
            case R.id.Houseloan_funds_calculate:
                HouseloanFundsloanHouseprice.setText(12.21+"");
                HouseloanFundsloanLoan.setText(18482+"");
                HouseloanFundsloanRepayment.setText(3112+"");
                break;
        }
    }
}
