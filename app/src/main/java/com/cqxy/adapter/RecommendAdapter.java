package com.cqxy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxy.bean.MoneyDetailBean;
import com.cqxy.fyb.R;

import java.util.List;

/**
 * Created by lpl on 17-9-20.
 */

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {
    private Context context;
    private List<MoneyDetailBean> datas;
    private static String TAG = "RecommendAdapter";

    public RecommendAdapter(Context context, List<MoneyDetailBean> datas) {
        this.context = context;
        this.datas = datas;

    }

    @Override
    public RecommendAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.recommend_adapter_layout, parent, false);
        ViewHolder viewholder = new ViewHolder(inflate);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(RecommendAdapter.ViewHolder holder, final int position) {
        holder.tvMoney.setText(datas.get(position).getAmount() + "");
        String created_at = datas.get(position).getTime();
        String time = created_at.substring(0, created_at.indexOf("T"));
        holder.tvTime.setText(time + "");
        String recommendname = "";
        if (null == datas.get(position).getRecommendname()) {
            recommendname = datas.get(position).getDetails() + " 个月";
            holder.btnDetail.setVisibility(View.GONE);
        } else {
            recommendname = datas.get(position).getRecommendname();
        }
        holder.tvName.setText(recommendname);
        holder.recommend_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myItemClickListener.onItemClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (datas != null) {
            return datas.size();
        } else {
            return 0;
        }
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMoney, tvTime, tvName;
        public RelativeLayout recommend_layout;
        public Button btnDetail;

        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.recommend_name);
            tvMoney = (TextView) view.findViewById(R.id.recommend_getmoney);
            tvTime = (TextView) view.findViewById(R.id.recommend_time);
            btnDetail = (Button) view.findViewById(R.id.btn_detail);
            recommend_layout = (RelativeLayout) view.findViewById(R.id.recommend_layout);
        }
    }

    public void setOnItemListener(MyItemClickListener myItemListener) {
        this.myItemClickListener = myItemListener;
    }


    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    public MyItemClickListener myItemClickListener;

}
