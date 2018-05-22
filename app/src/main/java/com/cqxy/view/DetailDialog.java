package com.cqxy.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cqxy.base.BaseUrl;
import com.cqxy.bean.MoneyDetailBean;
import com.cqxy.fyb.MyWealthActivity;
import com.cqxy.fyb.R;
import com.cqxy.utils.OkHttpUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by wx on 2017/12/28.
 */

public class DetailDialog extends Dialog implements View.OnClickListener {
    private int detailid;
    private Context mContext;
    private ImageView ivBack;
    private TextView tvTitle;
    private EditText etEnter;
    private ListView lvList;
    private Button btnAffirm;
    private String TAG = "DetailDialog";
    private LinearLayout llTitle;

    public DetailDialog(@NonNull final Context context, int detailid) {
        super(context);
        setContentView(R.layout.dialog_contact_us);
        findViews();
        this.mContext = context;
        this.detailid = detailid;
        OkHttpUtils.okhttpGet(BaseUrl.URL + "recommends?order_no=" + detailid, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                final List<MoneyDetailBean> moneyDetailBeen = MoneyDetailBean.arrayMoneyDetailBeanFromData(responseBody, "money");
                Log.d(TAG, "onResponse: =================responseBody  = " + responseBody + "=======size = " + moneyDetailBeen.size());
                ((MyWealthActivity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run: ------------------------");
                        lvList.setAdapter(new MyListAdapter(mContext,moneyDetailBeen));
                    }
                });
            }
        });
    }

    private void findViews() {
        ivBack = ((ImageView) findViewById(R.id.iv_back));
        tvTitle = ((TextView) findViewById(R.id.tv_title));
        etEnter = ((EditText) findViewById(R.id.et_enter));
        lvList = ((ListView) findViewById(R.id.lv_list));
        btnAffirm = ((Button) findViewById(R.id.btn_submit));
        llTitle = ((LinearLayout) findViewById(R.id.ll_title));
        tvTitle.setText("奖励明细");
        btnAffirm.setText("确认");
        llTitle.setVisibility(View.VISIBLE);
        etEnter.setVisibility(View.GONE);
        lvList.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(this);
        btnAffirm.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                dismiss();
                break;
            case R.id.btn_submit:
                dismiss();
                break;
        }
    }

    class MyListAdapter extends BaseAdapter {
        private Context mContext;
        private List<MoneyDetailBean> moneyDetailBeen;
        private TextView tvName;
        private TextView tvMoney;
        private TextView tvTime;
        private TextView tvUname;

        public MyListAdapter(Context mContext, List<MoneyDetailBean> moneyDetailBeen) {
            this.mContext = mContext;
            this.moneyDetailBeen = moneyDetailBeen;
        }

        @Override
        public int getCount() {
            return null == moneyDetailBeen ? -1 : moneyDetailBeen.size();
        }

        @Override
        public Object getItem(int position) {
            return moneyDetailBeen.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView)
                convertView = View.inflate(mContext, R.layout.detail_dialog_item, null);
            tvName = ((TextView) convertView.findViewById(R.id.recommend_name));
            tvMoney = ((TextView) convertView.findViewById(R.id.recommend_getmoney));
            tvTime = ((TextView) convertView.findViewById(R.id.recommend_time));
            tvUname = ((TextView) convertView.findViewById(R.id.recommend_username));
            tvName.setText(moneyDetailBeen.get(position).getRecommendname());
            tvMoney.setText(moneyDetailBeen.get(position).getAmount()+"");
            tvUname.setText(moneyDetailBeen.get(position).getUname());
            String created_at = moneyDetailBeen.get(position).getTime();
            String time = created_at.substring(0, created_at.indexOf("T"));
            tvTime.setText(time + "");
            return convertView;
        }
    }
}
