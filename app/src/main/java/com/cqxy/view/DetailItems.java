package com.cqxy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.cqxy.fyb.R;

import java.util.ArrayList;

/**
 * Created by wx on 2017/10/13.
 */

public class DetailItems extends GridLayout {
    private ArrayList<String> strDeatils;
    private Context context;
    private int itemWidth,itemHeight;

    public void setStrDeatils(ArrayList<String> strDeatils) {
        this.strDeatils = strDeatils;
        setColumnCount(strDeatils.size()/2);

    }

    public DetailItems(Context context) {
        this(context,null);
    }

    public DetailItems(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }


    public DetailItems(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void initViews(Context context) {
        this.context = context;
        if(null == this.strDeatils)
            return;
        inflate(context, R.layout.detail_items,this);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        int mParentWidth = 0;
        int mParentHeigth = 0;
        ViewGroup parent = (ViewGroup) getParent();
        if(null != parent) {
            mParentHeigth = parent.getHeight();
            mParentWidth = parent.getWidth();
        }
//        itemWidth = (mParentWidth-getPaddingLeft()-getPaddingRight()) / 2;
        itemWidth = (ScaleUtil.getScreenWidth(context) - getPaddingLeft() -getPaddingRight())/2;
        for (int i = 0; i < strDeatils.size(); i++) {
            TextView textView = new TextView(context);
            textView.setText(strDeatils.get(i));
//            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(getResources().getColor(R.color.colorBlue));
            textView.setTextSize(ScaleUtil.sp2px(context,4));
            GridLayout.Spec rowSpec = GridLayout.spec(i / 2, 1f);
            GridLayout.Spec columnSpec = GridLayout.spec(i % 2, 1f);
            //将Spec传入GridLayout.LayoutParams并设置宽高为0，必须设置宽高，否则视图异常
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, columnSpec);
            layoutParams.width = itemWidth;
            layoutParams.setGravity(Gravity.CENTER_HORIZONTAL);
            layoutParams.height = LayoutParams.WRAP_CONTENT;
            this.addView(textView,layoutParams);
        }
        Log.d("-------------", "onMeasure: mParentWidth"+mParentWidth+",itemWidth = " +itemWidth + ",PADDINGrIGHT"+getPaddingRight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
