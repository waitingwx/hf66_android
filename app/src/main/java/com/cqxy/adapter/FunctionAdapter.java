package com.cqxy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxy.bean.GridViewItem;
import com.cqxy.fyb.HomeActivity;
import com.cqxy.fyb.R;

import java.util.List;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

/**
 * Created by Administrator on 2017/8/30.
 */

public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.ViewHolder> {
    private List<GridViewItem> datas;
    private HomeActivity act;
    public MyItemClickListener myItemClickListener;
    public LinearLayout llItem;



    public LinearLayout getLlItem() {
        return llItem;
    }

    public void setLlItem(LinearLayout llItem) {
        this.llItem = llItem;
    }

    public FunctionAdapter(List<GridViewItem> datas, HomeActivity act) {
        this.datas = datas;
        this.act = act;
    }

    @Override
    public FunctionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(act).inflate(R.layout.gridviewadapterlayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FunctionAdapter.ViewHolder holder, final int position) {
        holder.function_img.setBackgroundResource(datas.get(position).getRes_imgId());
        holder.function_content.setText(datas.get(position).getFunction_name());
//        if (position == 0)
//            presentShowcaseSequence(holder.function_layout);
        holder.function_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myItemClickListener.onItemClick(view, position);
            }
        });
    }

    private void presentShowcaseSequence(LinearLayout llItem) {

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(act, "funcktionadatper");

        sequence.setOnItemShownListener(new MaterialShowcaseSequence.OnSequenceItemShownListener() {
            @Override
            public void onShow(MaterialShowcaseView itemView, int position) {
                Toast.makeText(itemView.getContext(), "Item #" + position, Toast.LENGTH_SHORT).show();
            }
        });

        sequence.setConfig(config);

        sequence.addSequenceItem(llItem, "录入房源入口", "知道了");
        sequence.start();
//        thread.run();//判断当前是否执行完成
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setOnItemListener(MyItemClickListener myItemListener) {
        this.myItemClickListener = myItemListener;
    }


    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView function_img;
        public TextView function_content;
        public LinearLayout function_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            function_img = (ImageView) itemView.findViewById(R.id.function_img);
            function_content = (TextView) itemView.findViewById(R.id.function_content);
            function_layout = (LinearLayout) itemView.findViewById(R.id.function_layout);
        }
    }
}
