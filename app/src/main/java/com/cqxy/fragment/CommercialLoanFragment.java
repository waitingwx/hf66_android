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

public class CommercialLoanFragment extends Fragment {
    //    @BindView(R.id.Houseloan_commercialloan_repaytype)
//    TextView HouseloanCommercialloanRepaytype;
    @BindView(R.id.Houseloan_commercialloan_repaylayout)
    LinearLayout HouseloanCommercialloanRepaylayout;
    //    @BindView(R.id.Houseloan_commercialloan_yearnum)
//    TextView HouseloanCommercialloanYearnum;
    @BindView(R.id.Houseloan_commercialloan_yearnumlayout)
    LinearLayout HouseloanCommercialloanYearnumlayout;
    //    @BindView(R.id.Houseloan_commercialloan_interesttype)
//    TextView HouseloanCommercialloanInteresttype;
    @BindView(R.id.Houseloan_commercialloan_interestlayout)
    LinearLayout HouseloanCommercialloanInterestlayout;
    //    @BindView(R.id.Houseloan_commercialloan_interest)
//    TextView HouseloanCommercialloanInterest;
    @BindView(R.id.Houseloan_commercialloan_loanaccount)
    EditText HouseloanCommercialloanLoanaccount;
    @BindView(R.id.Houseloan_commercialloan_loanaccountlayout)
    LinearLayout HouseloanCommercialloanLoanaccountlayout;
    @BindView(R.id.Houseloan_commercialloan_calculate)
    Button HouseloanCommercialloanCalculate;
    @BindView(R.id.Houseloan_commercialloan_houseprice)
    TextView HouseloanCommercialloanHouseprice;
    @BindView(R.id.Houseloan_commercialloan_loan)
    TextView HouseloanCommercialloanLoan;
    @BindView(R.id.Houseloan_commercialloan_repayment)
    TextView HouseloanCommercialloanRepayment;
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
        View inflate = inflater.inflate(R.layout.fragment_commercialloan_layout, null);
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

    @OnClick({R.id.Houseloan_commercialloan_repaylayout, R.id.Houseloan_commercialloan_yearnumlayout, R.id.Houseloan_commercialloan_interestlayout, R.id.Houseloan_commercialloan_calculate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Houseloan_commercialloan_repaylayout:
                break;
            case R.id.Houseloan_commercialloan_yearnumlayout:
                break;
            case R.id.Houseloan_commercialloan_interestlayout:
                break;
            case R.id.Houseloan_commercialloan_calculate:
                HouseloanCommercialloanHouseprice.setText(12.21+"");
                HouseloanCommercialloanLoan.setText(18482+"");
                HouseloanCommercialloanRepayment.setText(3112+"");
                break;
        }
    }
}
