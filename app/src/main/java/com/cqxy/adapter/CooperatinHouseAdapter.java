package com.cqxy.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.cqxy.base.BaseUrl;
import com.cqxy.bean.Users;
import com.cqxy.fyb.R;
import com.cqxy.newbean.MyHouseSource;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.UserInf;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/8/30.
 */

public class CooperatinHouseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static String TAG = "CooperatinHouseAdapter";
    public static final int TITLE = 0;
    public static final int ITEMS = 1;


    private List<Object> datas;
    private Activity act;
    public MyItemClickListener myItemClickListener;
    public OnToggleShowDialog mShowDialogListener;

    public CooperatinHouseAdapter(List<Object> datas, Activity act) {
        this.datas = datas;
        this.act = act;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TITLE:
                View titleView = LayoutInflater.from(act).inflate(R.layout.recycle_head_view, parent, false);
                TitleViewHolder vh = new TitleViewHolder(titleView);
                return vh;
            case ITEMS:
                View view = LayoutInflater.from(act).inflate(R.layout.house_list_item, parent, false);
                ItemsViewHolder viewHolder = new ItemsViewHolder(view);
                return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: ----------------------" + holder.getItemViewType());
        switch (holder.getItemViewType()) {
            case TITLE:
                Log.d(TAG, "onBindViewHolder:-------------------title");
                if (holder instanceof TitleViewHolder) {
                    TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
                    String title = ((String) datas.get(position));
                    Log.d(TAG, "onBindViewHolder: ===================title = " + title);
                    titleViewHolder.tvHead.setText(title);
                } else
                    Log.d(TAG, "onBindViewHolder:-------------------not title");
                break;
            case ITEMS:
                ItemsViewHolder itemsViewHolder = (ItemsViewHolder) holder;
                final MyHouseSource myHouseSource = ((MyHouseSource) datas.get(position));
                Log.d(TAG, "onBindViewHolder: =============items"+myHouseSource.getId());
                final boolean ischecked = myHouseSource.getStates() == 1 ? true : false;
                itemsViewHolder.toggleButton.setChecked(ischecked);
                getUserPhone(myHouseSource.getUser_id(), itemsViewHolder);
                itemsViewHolder.headIcon.setBackgroundResource(R.mipmap.mine_man);
                itemsViewHolder.houselocation.setText(myHouseSource.getPosition());
                itemsViewHolder.houseprice.setText("金额：" + String.valueOf(myHouseSource.getRent()) + "元");
                itemsViewHolder.housesize.setText("使用面积：" + String.valueOf(myHouseSource.getHouse_area()) + "m²");
                String created_at = myHouseSource.getCreated_at();
                String time = created_at.substring(0, created_at.indexOf("T"));
                itemsViewHolder.housecreatetime.setText(time);
                itemsViewHolder.toggleButton.setVisibility(View.VISIBLE);
                itemsViewHolder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        int states = isChecked ? 1 : 0;
                        mShowDialogListener.onShowDialog(buttonView,position,ischecked);
                        FormBody formBody = new FormBody.Builder().add("states", states + "").build();
                        OkHttpUtils.okhttpPost(
                                formBody,
                                BaseUrl.URL + "/house_examples/changestate/" + myHouseSource.getId(),
                                new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {

                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        Log.d(TAG, "onResponse: ========response.body = " + response.body().string());
                                    }
                                });
                    }
                });
                itemsViewHolder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myItemClickListener.onItemClick(view, position);
                    }
                });
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (datas.get(position) instanceof String) {
            Log.d(TAG, "getItemViewType: ------------------------title============="+datas.get(position).toString());
            return TITLE;//标题
        } else if (datas.get(position) instanceof MyHouseSource) {
            Log.d(TAG, "getItemViewType: ------------------------items---"+((MyHouseSource) datas.get(position)).getId());
            return ITEMS;//数据项
        } else {
            Log.d(TAG, "getItemViewType: -------------------------1");
            return -1;
        }
    }


    @Override
    public int getItemCount() {
        if (datas != null) {
            return datas.size();
        } else {
            return 0;
        }
    }

    public void setOnItemListener(MyItemClickListener myItemListener) {
        this.myItemClickListener = myItemListener;
    }

    public void setmShowDialogListener(OnToggleShowDialog mShowDialogListener) {
        this.mShowDialogListener = mShowDialogListener;
    }

    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnToggleShowDialog {
        void onShowDialog(View view, int position,boolean ischecked);
    }

    public static class ItemsViewHolder extends RecyclerView.ViewHolder {
        public ImageView headIcon;
        public TextView name, housecreatetime, housesize, houseprice, houselocation, houseProfit;
        public RelativeLayout layout;
        public ToggleButton toggleButton;


        public ItemsViewHolder(View itemView) {
            super(itemView);
            headIcon = (ImageView) itemView.findViewById(R.id.housebase_head);
            name = (TextView) itemView.findViewById(R.id.housebase_name);
            houselocation = (TextView) itemView.findViewById(R.id.housebase_houselocation);
            housesize = (TextView) itemView.findViewById(R.id.housebase_housesize);
            houseprice = (TextView) itemView.findViewById(R.id.housebase_price);
            houseProfit = (TextView) itemView.findViewById(R.id.housebase_profit);
            housecreatetime = (TextView) itemView.findViewById(R.id.housebase_time);
            layout = (RelativeLayout) itemView.findViewById(R.id.housebase_layout);
            toggleButton = itemView.findViewById(R.id.tb_onoff);

        }


    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {

        private TextView tvHead;

        public TitleViewHolder(View itemView) {
            super(itemView);
            tvHead = itemView.findViewById(R.id.tv_head_text);
        }
    }

    public void getUserPhone(int userid, final ItemsViewHolder holder) {
        OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(act), UserInf.getUserPhonenum(act), BaseUrl.URL + "users/" + userid + ".json", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                final Users user = gson.fromJson(response.body().string(), Users.class);
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        holder.name.setText(user.getPhone_num());
                    }
                });

            }
        });
    }
}
