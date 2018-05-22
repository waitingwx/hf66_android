package com.cqxy.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;

import com.cqxy.bean.LoadFilteredEvent;
import com.cqxy.fyb.FindHouseActivity;
import com.cqxy.fyb.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by PeLon on 2017/9/4.
 */

public class FindHouse_RightFrigment extends Fragment {
    public int RESIDENCE_RENT=5;
    public int RESIDENCE_SELL=1;
    public int VILLA_RENT=6;
    public int VILLA_SELL=2;
    public int OFFICEBUILDING_RENT=7;
    public int OFFICEBUILDING_SELL=3;
    public int SHOP_RENT=8;
    public int SHOP_SELL=4;

    @BindView(R.id.rg_aspect)
    RadioGroup rgAspect;
    @BindView(R.id.rg_balcony)
    RadioGroup rgBalcony;
    @BindView(R.id.rg_bathroom)
    RadioGroup rgBathroom;
    @BindView(R.id.rg_bedroom)
    RadioGroup rgBedroom;
    @BindView(R.id.rg_kitchen)
    RadioGroup rgKitchen;
    @BindView(R.id.rg_livingroom)
    RadioGroup rgLivingroom;
    @BindView(R.id.rg_decoration)
    RadioGroup rgDecoration;

    @BindView(R.id.tr_aspect)
    TableRow trAspect;
    @BindView(R.id.tr_balcony)
    TableRow trBalcony;
    @BindView(R.id.tr_bathroom)
    TableRow trBathroom;
    @BindView(R.id.tr_bedroom)
    TableRow trBedroom;
    @BindView(R.id.tr_kitchen)
    TableRow trKitchen;
    @BindView(R.id.tr_livingroom)
    TableRow trLivingroom;
    @BindView(R.id.tr_decoration)
    TableRow trDecoration;
    @BindView(R.id.tr_floor)
    TableRow trFloor; @BindView(R.id.tr_age)
    TableRow trAge;

    @BindView(R.id.et_prevalue)
    EditText etprevalue;
    @BindView(R.id.et_endvalue)
    EditText etendvalue;

    @BindView(R.id.et_prearea)
    EditText etprearea;
    @BindView(R.id.et_endarea)
    EditText etendarea;
    @BindView(R.id.et_preage)
    EditText etpreage;
    @BindView(R.id.et_endage)
    EditText etendage;
    @BindView(R.id.et_prefloor)
    EditText etprefloor;
    @BindView(R.id.et_endfloor)
    EditText etendfloor;

    String strBalcony = "0阳";
    String strBathroom = "0卫";
    String strBedroom = "0室";
    String strKitchen = "0厨";
    String strLivingroom = "0厅";

    String strPrevalue, strEndvalue, strPrearea, strEndarea, strPreage, strEndage, strPrefloor, strEndfloor;
    String strDecoration;
    String strAspect;
    private static String TAG = "FilterFragment";

    private FindHouseActivity act;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.act = (FindHouseActivity) context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_layout, null);
        view.setClickable(true);
        ButterKnife.bind(this, view);
        int flag = getArguments().getInt("flag");
        invisibleOrNot(flag);
        return view;

    }

    private void invisibleOrNot(int flag) {
        switch (flag) {
            case 8:
            case 4://shop
                trAge.setVisibility(View.GONE);
                break;
            case 5:
            case 1://residence
                trBedroom.setVisibility(View.VISIBLE);
                trAspect.setVisibility(View.VISIBLE);
                trBalcony.setVisibility(View.VISIBLE);
                trBathroom.setVisibility(View.VISIBLE);
                trKitchen.setVisibility(View.VISIBLE);
                trLivingroom.setVisibility(View.VISIBLE);
                break;
            case 6:
            case 2:
                trFloor.setVisibility(View.GONE);
                break;

        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rgAspect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                strAspect = ((RadioButton) act.findViewById(checkedId)).getText().toString().trim();
            }
        });
        rgBalcony.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                strBalcony = ((RadioButton) act.findViewById(checkedId)).getText().toString().trim();
            }
        });
        rgBathroom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                strBathroom = ((RadioButton) act.findViewById(checkedId)).getText().toString().trim();
            }
        });
        rgBedroom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                strBedroom = ((RadioButton) act.findViewById(checkedId)).getText().toString().trim();
            }
        });
        rgDecoration.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                strDecoration = ((RadioButton) act.findViewById(checkedId)).getText().toString().trim();
            }
        });
        rgKitchen.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                strKitchen = ((RadioButton) act.findViewById(checkedId)).getText().toString().trim();
            }
        });
        rgLivingroom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                strLivingroom = ((RadioButton) act.findViewById(checkedId)).getText().toString().trim();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.btn_confirm})
    public void onViewClicked(View view) {
        strPrearea = etprearea.getText().toString().trim();
        strEndarea = etendarea.getText().toString().trim();
        strPrevalue = etprevalue.getText().toString().trim();
        strEndvalue = etendvalue.getText().toString().trim();
        strPreage = etpreage.getText().toString().trim();
        strEndage = etendage.getText().toString().trim();
        strPrefloor = etprefloor.getText().toString().trim();
        strEndfloor = etendfloor.getText().toString().trim();
        Log.d(TAG, "onViewClicked: strPrearea = " + strPrearea +
                ",\nstrEndarea = " + strEndarea +
                ",\nstrPrevalue = " + strPrevalue +
                ",\nstrEndvalue = " + strEndvalue +
                ",\nstrAspect = " + strAspect +
                ",\nstrDecoration = " + strDecoration +
                ",\nstrBedroom = " + strBedroom +
                ",\nstrLivingroom = " + strLivingroom +
                ",\nstrBathroom = " + strBathroom +
                ",\nstrKitchen = " + strKitchen +
                ",\nstrBalcony = " + strBalcony);
        LoadFilteredEvent event = new LoadFilteredEvent(strPrevalue, strEndvalue);
        event.setStrAspect(strAspect)
                .setStrDecoration(strDecoration)
                .setStrPrearea(strPrearea)
                .setStrEndarea(strEndarea)
                .setStrPreage(strPreage)
                .setStrEndage(strEndage);
        if (trFloor.getVisibility() != View.GONE) {
            event.setStrPrefloor(strPrefloor).setStrEndfloor(strEndfloor);
        }
        if (trBedroom.getVisibility() != View.GONE)
            event.setHousetype(strBedroom + strLivingroom + strBathroom + strKitchen + strBalcony);

        EventBus.getDefault().post(event);
    }

}
