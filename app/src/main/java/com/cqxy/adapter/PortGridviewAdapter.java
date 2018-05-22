package com.cqxy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqxy.bean.PointGridBean;
import com.cqxy.fyb.R;

import java.util.List;

/**
 * Created by lpl on 17-9-21.
 */

public class PortGridviewAdapter extends RecyclerView.Adapter<PortGridviewAdapter.ViewHolder> {

    private List<PointGridBean> datas;
    private Context context;

    public PortGridviewAdapter(List<PointGridBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public PortGridviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.port_gridview_layout, parent, false);
        ViewHolder viewHolder=new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PortGridviewAdapter.ViewHolder holder, final int position) {
            holder.port_num.setText(datas.get(position).getNum());
            holder.port_content.setText(datas.get(position).getContent());
            holder.port_layout.setOnClickListener(new View.OnClickListener() {
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

    public void setOnItemListener(MyItemClickListener myItemListener)
    {
        this.myItemClickListener=myItemListener;
    }


    public interface MyItemClickListener{
        void onItemClick(View view, int position);
    }

    public MyItemClickListener myItemClickListener;

    public  static class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView port_num,port_content;
        public LinearLayout port_layout;
        public ViewHolder(View itemView) {
            super(itemView);
            port_content=(TextView)itemView.findViewById(R.id.port_content);
            port_num=(TextView)itemView.findViewById(R.id.port_num);
            port_layout=(LinearLayout)itemView.findViewById(R.id.port_layout);
        }
    }
}
