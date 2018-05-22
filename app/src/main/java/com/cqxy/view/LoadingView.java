package com.cqxy.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxy.fyb.R;

/**
 * Created by wx on 2017/11/20.
 */

public class LoadingView extends RelativeLayout {

    private ProgressBar loadingbar;
    private TextView tvMessage;
    ValueAnimator va;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }
    public LoadingView setMessage(String message) {
        this.tvMessage.setText(message);
        return this;
    }

    private void initViews(Context context) {
        this.setVisibility(GONE);
        View view = inflate(context, R.layout.loading_view, this);
        tvMessage = findViewById(R.id.tv_loading);
        loadingbar = ((ProgressBar) view.findViewById(R.id.pb_loading));
        loadingbar.setMax(100);
        va = new ValueAnimator();
        va.setRepeatMode(ValueAnimator.RESTART);
        va.setIntValues(0, 100);
        va.setDuration(3 * 60 * 1000);
        va.setTarget(loadingbar);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                loadingbar.setProgress(animatedValue);
            }
        });
    }

    public void stopAnimation() {
        va.cancel();
        this.setVisibility(GONE);
    }

    public void startAnimation() {
        va.start();
        this.setVisibility(VISIBLE);
    }

    public void dismiss() {
        this.setVisibility(GONE);
    }

    public void bevisible() {
        this.setVisibility(VISIBLE);
    }

}
