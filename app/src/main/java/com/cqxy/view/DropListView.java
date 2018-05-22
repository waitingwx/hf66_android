package com.cqxy.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxy.adapter.ListViewAdapter;
import com.cqxy.base.BaseUrl;
import com.cqxy.constants.QuxianBean;
import com.cqxy.constants.ShangquanBean;
import com.cqxy.constants.XiaoquBean;
import com.cqxy.bean.ShangqBean;
import com.cqxy.fyb.R;
import com.cqxy.listener.ViewBaseAction;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.SpUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by wx on 2017/11/15.
 */

public class DropListView<T extends AppCompatActivity > extends RelativeLayout implements ViewBaseAction{
    private static String TAG = "DropListView";
    private LoadChooseDataListener loadChooseDataListener;

    private String sqResponseBody;
    List<ShangqBean.CityBean> city;
    private T mContext;
    private String[] chooseItems = new String[3];
    View view;
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private ListView quxianListView;
    private ListView shangquanListView;
    private ListView xiaoquListView;
    private int xiaoquId;

    private ArrayList<QuxianBean> quxianDatas = new ArrayList<>();
    private ArrayList<ShangquanBean> shangquanDatas = new ArrayList<>();
    private ArrayList<XiaoquBean.XiaoqusBean> xiaoqusDatas = new ArrayList<>();

    ListViewAdapter<QuxianBean> quxianBeanListViewAdapter;
    ListViewAdapter<ShangquanBean> shangquanBeanListViewAdapter;
    ListViewAdapter<XiaoquBean.XiaoqusBean> xiaoquBeanListViewAdapter;
    private Button btnConfirm;
    private TextView textChoose;
    private LinearLayout lists;

    public TextView getTextChoose() {
        return textChoose;
    }

    public void setTextChoose(TextView textChoose) {
        this.textChoose = textChoose;
    }

    public void setXiaoquId(int xiaoquId) {
        this.xiaoquId = xiaoquId;
        Log.d(TAG, "setXiaoquId: -----xiaoquId = " + this.xiaoquId);
    }

    public DropListView(Context context) {
        this(context, null);
        Log.d(TAG, "DropListView: ---------------");
    }

    public DropListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "DropListView: -------------------");
        this.mContext = (T) context;
        initData(context);
        init(context);
    }

    private void initData(Context context) {
        Log.d(TAG, "initData: ===================");
        sqResponseBody = SpUtil.getString(mContext, "shangquan", "responsebody");
        try {
            int length = sqResponseBody.getBytes("utf-8").length;
            Log.d(TAG, "initData: ================length = " + length);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        city = ShangqBean.objectFromData(sqResponseBody).getCity();
        Log.d(TAG, "initData: -------sqResponseBody = " + sqResponseBody);
        for (int i = 0; i < city.size(); i++) {
            quxianDatas.add(city.get(i).getQuxian());
        }
    }

    private void init(final Context context) {
        Log.d(TAG, "init: -----------------");
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.drop_list, this);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        lists = ((LinearLayout) view.findViewById(R.id.ll_list));
        btnConfirm = ((Button) view.findViewById(R.id.btn_confirm));
        textChoose = ((TextView) view.findViewById(R.id.tv_choose_text));
        quxianListView = view.findViewById(R.id.ll_quxian);
        quxianListView.setDividerHeight(ScaleUtil.dp2px(mContext, 4));
        shangquanListView = view.findViewById(R.id.ll_shangquan);
        shangquanListView.setDividerHeight(ScaleUtil.dp2px(mContext, 4));
        xiaoquListView = view.findViewById(R.id.ll_xiaoqu);
        xiaoquListView.setDividerHeight(ScaleUtil.dp2px(mContext, 4));
        quxianBeanListViewAdapter = new ListViewAdapter<QuxianBean>(getQuxianDatas(), mContext) {
            @Override
            public void showTexts(int position, TextView covertView) {
                String name = getQuxianDatas().get(position).getName();
                covertView.setText(name);
            }
        };
        shangquanBeanListViewAdapter = new ListViewAdapter<ShangquanBean>(getShangquanDatas(), mContext) {
            @Override
            public void showTexts(int position, TextView covertView) {
                String name = getShangquanDatas().get(position).getName();
                covertView.setText(name);
            }
        };
        xiaoquBeanListViewAdapter = new ListViewAdapter<XiaoquBean.XiaoqusBean>(getXiaoqusDatas(), mContext) {
            @Override
            public void showTexts(int position, TextView covertView) {
                String name = getXiaoqusDatas().get(position).getName();
                covertView.setText(name);
            }
        };
        setListener();
    }


    private void setListener() {
        Log.d(TAG, "setListener: ----------------");
        quxianListView.setAdapter(quxianBeanListViewAdapter);
        quxianListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getShangquanDatas().size() != 0)
                    shangquanDatas.clear();
                shangquanDatas.addAll(city.get(position).getShangquan());
//                sb.append(getQuxianDatas().get(position).getName());
                chooseItems[0] = getQuxianDatas().get(position).getName();
                Log.d(TAG, "onItemClick: ===============quxianListener---------\nshangquandatas.size() ="
                        + getShangquanDatas().size() + "\nquxian = "
                        + getQuxianDatas().get(position).getName()
                        + "\nsahgnquandatas.get(0) =" + getShangquanDatas().get(0).getName());
                shangquanListView.setAdapter(shangquanBeanListViewAdapter);
                shangquanBeanListViewAdapter.notifyDataSetChanged();
            }
        });

        shangquanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: -----------shangquanlistview");
//                sb.append(shangquanDatas.get(position).getName());
                chooseItems[1] = getShangquanDatas().get(position).getName();
                loadXiaoqu(shangquanDatas.get(position).getId());
            }
        });

        xiaoquListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: =========================xiaoqulistview");
//                sb.append(xiaoqusDatas.get(position).getName());
                chooseItems[2] = getXiaoqusDatas().get(position).getName();
                setXiaoquId(xiaoqusDatas.get(position).getId());
                Log.d(TAG, "onItemSelected: -------------------sb = " + chooseItems[0]+chooseItems[1]+chooseItems[2]);
                String text = chooseItems[0] + chooseItems[1] + chooseItems[2];
                textChoose.setText(text);
                setResult(text);
                loadChooseDataListener.loadData(getXiaoquId());
            }
        });
        btnConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(GONE);
                lists.setVisibility(GONE);
            }
        });
        textChoose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断visibilty
                if(lists.getVisibility() == GONE)
                    lists.setVisibility(VISIBLE);
                if(btnConfirm.getVisibility() == GONE)
                    btnConfirm.setVisibility(VISIBLE);

            }
        });
    }

    private void loadXiaoqu(int shangQuanId) {
        OkHttpUtils.okhttpGet(BaseUrl.URL + "xiaoqus/" + shangQuanId + ".json", new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d(TAG, "onResponse: string = " + string);
                if (xiaoqusDatas.size() != 0)
                    xiaoqusDatas.clear();
                xiaoqusDatas.addAll(XiaoquBean.objectFromData(string).getXiaoqus());
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run: ---------------");
                        xiaoquListView.setAdapter(xiaoquBeanListViewAdapter);
                        xiaoquBeanListViewAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }
    public int getXiaoquId() {
        return xiaoquId;
    }

    public ArrayList<QuxianBean> getQuxianDatas() {
        return quxianDatas;
    }

    public void setQuxianDatas(ArrayList<QuxianBean> quxianDatas) {
        this.quxianDatas = quxianDatas;
    }

    public ArrayList<ShangquanBean> getShangquanDatas() {
        return shangquanDatas;
    }

    public void setShangquanDatas(ArrayList<ShangquanBean> shangquanDatas) {
        this.shangquanDatas = shangquanDatas;
    }

    public ArrayList<XiaoquBean.XiaoqusBean> getXiaoqusDatas() {
        return xiaoqusDatas;
    }

    public void setXiaoqusDatas(ArrayList<XiaoquBean.XiaoqusBean> xiaoqusDatas) {
        this.xiaoqusDatas = xiaoqusDatas;
    }

    public void setLoadChooseDataListener(LoadChooseDataListener loadChooseDataListener) {
        this.loadChooseDataListener = loadChooseDataListener;
    }

    public interface LoadChooseDataListener {
        void loadData(int xiaoquid);
    }
}
