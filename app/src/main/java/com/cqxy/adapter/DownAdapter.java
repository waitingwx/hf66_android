package com.cqxy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqxy.bean.downbean;
import com.cqxy.fyb.R;

import java.util.List;

/**
 * Created by PeLon on 2017/9/19.
 */

public class DownAdapter extends RecyclerView.Adapter<DownAdapter.ViewHolder> {
    private List<downbean> datas;
    private Context context;

    public DownAdapter(List<downbean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public DownAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.down_adapter_layout, parent, false);
        ViewHolder viewholder = new ViewHolder(inflate);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(DownAdapter.ViewHolder holder, final int position) {
        holder.down_fileimg.setBackgroundResource(datas.get(position).getfileimg());
        holder.down_filename.setText(datas.get(position).getfilename());
        holder.down_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myItemClickListener.onItemClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView  down_filename;
        public ImageView down_fileimg;
        public LinearLayout down_item_layout;
        public ViewHolder(View view) {
            super(view);
            down_filename = (TextView) view.findViewById(R.id.down_filename);
            down_fileimg = (ImageView) view.findViewById(R.id.down_fileimg);
            down_item_layout=(LinearLayout)view.findViewById(R.id.down_item_layout);
        }

    }

    public void setOnItemListener(MyItemClickListener myItemListener)
    {
        this.myItemClickListener=myItemListener;
    }


    public interface MyItemClickListener{
        void onItemClick(View view, int position);
    }
    public MyItemClickListener myItemClickListener;

}