package com.cqxy.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cqxy.base.BaseUrl;
import com.cqxy.bean.HouseExampleBeans;
import com.cqxy.bean.HouseSellBean;
import com.cqxy.bean.Users;
import com.cqxy.fyb.R;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.UserInf;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by Administrator on 2017/8/30.
 */

public class HouseSellAdapter extends RecyclerView.Adapter<HouseSellAdapter.ViewHolder> {
    private List<HouseSellBean.CommentsBean> datas;
    private Activity act;
    public MyItemClickListener myItemClickListener;

    public HouseSellAdapter(List<HouseSellBean.CommentsBean> datas, Activity act) {
        this.datas = datas;
        this.act = act;

    }

    @Override
    public HouseSellAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(act).inflate(R.layout.house_list_item, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HouseSellAdapter.ViewHolder holder, final int position) {
        HouseSellBean.CommentsBean commentsBean = datas.get(position);
        getUserPhone(commentsBean.getHouse_example_id(),holder);
        String userimg = commentsBean.getUserasset();
        int endIndex = userimg.indexOf("?");
        if (endIndex != -1) {
            userimg = userimg.substring(0, endIndex);
            Glide.with(act)
                    .load(BaseUrl.IMGURL + userimg)
                    .apply(RequestOptions.placeholderOf(R.mipmap.mine_man))
                    .apply(RequestOptions.errorOf(R.mipmap.mine_man))
                    .apply(bitmapTransform(new CropCircleTransformation()))
                    .into(holder.headIcon);
        }
        holder.houselocation.setText(commentsBean.getPosition());
        holder.houseprice.setText("售价：" + commentsBean.getHouse_price()+"元");
        holder.housesize.setText("使用面积："+ commentsBean.getHouse_area());
        holder.housefloor.setText("楼层："+commentsBean.getFloor()+"");
        String leixing = commentsBean.getLeixing();
        String bvalue = commentsBean.getBvalue();
        if (null == leixing || null == bvalue)
            holder.houseProfit.setText("盈利：");
        else
            holder.houseProfit.setText("盈利：" + leixing + bvalue + "");
        String created_at = datas.get(position).getCreated_at();
        String time=created_at.substring(0,created_at.indexOf("T"));
        holder.housecreatetime.setText(time);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myItemClickListener.onItemClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(null == datas)
            return 0;
        else
            return datas.size() == 0 ? 0 : datas.size();
    }

    public void setOnItemListener(MyItemClickListener myItemListener)
    {
        this.myItemClickListener=myItemListener;
    }


    public interface MyItemClickListener{
        void onItemClick(View view, int position);
    }
    public  static class ViewHolder extends RecyclerView.ViewHolder  {
        public ImageView headIcon;
        public TextView housecreatetime,name,housefloor,housesize,houseprice,houselocation,houseProfit;
        public RelativeLayout layout;


        public ViewHolder(View itemView) {
            super(itemView);
            headIcon=(ImageView)itemView.findViewById(R.id.housebase_head);
            name=(TextView) itemView.findViewById(R.id.housebase_name);
            houselocation=(TextView) itemView.findViewById(R.id.housebase_houselocation);
            housesize=(TextView) itemView.findViewById(R.id.housebase_housesize);
            houseprice=(TextView) itemView.findViewById(R.id.housebase_price);
            houseProfit=(TextView) itemView.findViewById(R.id.housebase_profit);
            housefloor=(TextView) itemView.findViewById(R.id.housebase_housefloor);
            housecreatetime=(TextView) itemView.findViewById(R.id.housebase_time);
            layout=(RelativeLayout)itemView.findViewById(R.id.housebase_layout);
        }




    }

    public void getUserPhoneByid(int userid, final HouseSellAdapter.ViewHolder holder)
    {
        OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(act),UserInf.getUserPhonenum(act),BaseUrl.URL+"users/"+userid+".json", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String responseBody = response.body().string();
                Log.d("HouseSellAdapter", "onResponse: ----------------responseBody - " + responseBody);

                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson=new Gson();
                        Log.d("HouseSellAdapter", "run: =====================responsebody = " +responseBody);
                        Users user=Users.objectFromData(responseBody,"user");
                        String phone_num = user.getPhone_num();
                        Log.d("HouseSellAdapter", "run: =======================setPhoneNum = " + phone_num);
                        holder.name.setText(phone_num.substring(0,3)+"*****");
                        holder.name.invalidate();
                    }
                });

            }
        });
    }

    public void getUserPhone(int houseid, final HouseSellAdapter.ViewHolder holder)
    {
        OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(act),UserInf.getUserPhonenum(act),BaseUrl.URL+"house_examples/"+houseid+".json", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson=new Gson();
                final HouseExampleBeans.HouseexampleBean house=gson.fromJson(response.body().string(),HouseExampleBeans.class).getHouseexample();
//                final HouseExampleBeans.HouseexampleBean house= HouseExampleBean.CommentsBean.arrayCommentsBeanFromData(response.body().string());
//                act.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
                        getUserPhoneByid(house.getUser_id(),holder);
//                    }
//                });

            }
        });
    }
}
