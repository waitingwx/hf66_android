package com.cqxy.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cqxy.fyb.R;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * Created by lpl on 17-9-22.
 */

public class MyWheelViewDialog {
    public static int FLOORS = 1, ORIENTATIONS = 2, HOUSETYPE = 3;

    private Dialog dialog;
    private Context context;
    private WheelView wheelView1, wheelView2, wheelView3, wheelView4, wheelView5;
    private TextView title;
    private Button commit;
    private String string1, string2, string3, string4, string5;

    public MyWheelViewDialog(Context context, int type) {
        this.context = context;
        dialog = new Dialog(context, R.style.Theme_Dialog);
        dialog.setContentView(R.layout.wheeldialog_layout);
        initViews();
        getCommitContent(type);
    }

    private void initViews() {
        wheelView1 = (WheelView) dialog.findViewById(R.id.wheeldialog_wheelview1);
        wheelView2 = (WheelView) dialog.findViewById(R.id.wheeldialog_wheelview2);
        wheelView3 = (WheelView) dialog.findViewById(R.id.wheeldialog_wheelview3);
        wheelView4 = (WheelView) dialog.findViewById(R.id.wheeldialog_wheelview4);
        wheelView5 = (WheelView) dialog.findViewById(R.id.wheeldialog_wheelview5);
        title = (TextView) dialog.findViewById(R.id.wheeldialog_title);
        commit = (Button) dialog.findViewById(R.id.wheeldialog_commit);
    }

    public void showWheelDialog() {

        dialog.show();

    }


    public void InitWheelViewCommit(final TextView textView, final int type) {
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = getCommitContent(type);
                if (text.contains("无")) {
                    text = text.replace("无", "");
                }
                textView.setText(text);
                dialog.dismiss();
            }

        });

    }


    public String getCommitContent(int type) {
        wheelView1.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                string1 = (String) wheelView1.getItemAtPosition(position);
            }
        });
        wheelView2.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                string2 = (String) wheelView2.getItemAtPosition(position);
            }
        });
        if (type == HOUSETYPE) {
            wheelView3.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
                @Override
                public void onItemSelected(int position, Object o) {
                    string3 = (String) wheelView3.getItemAtPosition(position);
                }
            });
            wheelView4.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
                @Override
                public void onItemSelected(int position, Object o) {
                    string4 = (String) wheelView4.getItemAtPosition(position);
                }
            });
            wheelView5.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
                @Override
                public void onItemSelected(int position, Object o) {
                    string5 = (String) wheelView5.getItemAtPosition(position);
                }
            });
            return string1 + string2 + string3 + string4 + string5;
        } else if (type == ORIENTATIONS) {
            wheelView3.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
                @Override
                public void onItemSelected(int position, Object o) {
                    string3 = (String) wheelView3.getItemAtPosition(position);
                }
            });
            wheelView4.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
                @Override
                public void onItemSelected(int position, Object o) {
                    string4 = (String) wheelView4.getItemAtPosition(position);
                }
            });
            return string1 + string2 + string3 + string4;
        }


        return string1 + string2;

    }

    public void initWheel1(Context context, int type) {

        wheelView1.setWheelAdapter(new ArrayWheelAdapter(context));  //设置滚轮数据适配器s
        wheelView1.setSkin(WheelView.Skin.Holo);  //设置背景颜色  
        wheelView1.setWheelData(createMainDatas(type)); //设置滚轮数据
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle(); //设置选中与未选中字体的样式  
        style.selectedTextSize = 20;
        style.textSize = 16;
        wheelView1.setStyle(style);

        wheelView2.setWheelAdapter(new ArrayWheelAdapter(context));
        wheelView2.setSkin(WheelView.Skin.Holo);
        wheelView2.setWheelData(createSubDatas(type).get(createMainDatas(type).get(wheelView1.getSelection())));
        wheelView2.setStyle(style);
        wheelView1.join(wheelView2); //连接副WheelView
        wheelView1.joinDatas(createSubDatas(type)); //副WheelView

        if (type == HOUSETYPE) {

            wheelView3.setWheelAdapter(new ArrayWheelAdapter(context));
            wheelView3.setSkin(WheelView.Skin.Holo);
            wheelView3.setWheelData(createChildDatas(type).get(createSubDatas(type).get(createMainDatas(type).get(wheelView1.getSelection())).get(wheelView2.getSelection())));
            wheelView3.setStyle(style);
            wheelView2.join(wheelView3); //连接副WheelView
            wheelView2.joinDatas(createChildDatas(type)); //副WheelView

            wheelView4.setWheelAdapter(new ArrayWheelAdapter(context));
            wheelView4.setSkin(WheelView.Skin.Holo);
            wheelView4.setWheelData(createChildDatas2(type).get(createChildDatas(type).get(createSubDatas(type).get(createMainDatas(type).get(wheelView1.getSelection())).get(wheelView2.getSelection())).get(wheelView3.getSelection())));
            wheelView4.setStyle(style);
            wheelView3.join(wheelView4); //连接副WheelView
            wheelView3.joinDatas(createChildDatas2(type)); //副WheelView

            wheelView5.setWheelAdapter(new ArrayWheelAdapter(context));
            wheelView5.setSkin(WheelView.Skin.Holo);
            wheelView5.setWheelData(createChildDatas3().get(createChildDatas2(type).get(createChildDatas(type).get(createSubDatas(type).get(createMainDatas(type).get(wheelView1.getSelection())).get(wheelView2.getSelection())).get(wheelView3.getSelection())).get(wheelView3.getSelection())));
            wheelView5.setStyle(style);
            wheelView4.join(wheelView5); //连接副WheelView
            wheelView4.joinDatas(createChildDatas3()); //副WheelView

        } else if (type == ORIENTATIONS) {
            wheelView3.setWheelAdapter(new ArrayWheelAdapter(context));
            wheelView3.setSkin(WheelView.Skin.Holo);
            wheelView3.setWheelData(createChildDatas(type).get(createSubDatas(type).get(createMainDatas(type).get(wheelView1.getSelection())).get(wheelView2.getSelection())));
            wheelView3.setStyle(style);
            wheelView2.join(wheelView3); //连接副WheelView
            wheelView2.joinDatas(createChildDatas(type)); //副WheelView

            wheelView4.setWheelAdapter(new ArrayWheelAdapter(context));
            wheelView4.setSkin(WheelView.Skin.Holo);
            wheelView4.setWheelData(createChildDatas2(type).get(createChildDatas(type).get(createSubDatas(type).get(createMainDatas(type).get(wheelView1.getSelection())).get(wheelView2.getSelection())).get(wheelView3.getSelection())));
            wheelView4.setStyle(style);
            wheelView3.join(wheelView4); //连接副WheelView
            wheelView3.joinDatas(createChildDatas2(type)); //副WheelView

        }


    }

    private List<String> createMainDatas(int type) {
        String[] strings = null;
        if (type == FLOORS) {
            strings = new String[]{"-0", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        } else if (type == ORIENTATIONS) {
            wheelView3.setVisibility(View.VISIBLE);
            wheelView4.setVisibility(View.VISIBLE);
            strings = new String[]{"东", "南", "西", "北"};
        } else if (type == HOUSETYPE) {
            wheelView3.setVisibility(View.VISIBLE);
            wheelView4.setVisibility(View.VISIBLE);
            wheelView5.setVisibility(View.VISIBLE);
            strings = new String[]{"0室", "1室", "2室", "3室", "3室以上"};
        }

        return Arrays.asList(strings);
    }

    private HashMap<String, List> createSubDatas(int type) {
        HashMap<String, List> map = new HashMap<String, List>();

        if (type == FLOORS) {
            title.setText("设置楼层");
            String[] strings = {"-0", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
            String[] s1 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
            String[] s2 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
            String[] s3 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
            String[] s4 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
            String[] s5 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
            String[] s6 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
            String[] s7 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
            String[] s8 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
            String[] s9 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
            String[] s10 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
            String[] s11 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
            String[][] ss = {s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11};

            for (int i = 0; i < strings.length; i++) {
                map.put(strings[i], Arrays.asList(ss[i]));
            }
        } else if (type == ORIENTATIONS) {
            title.setText("设置朝向");

            String[] strings = {"东", "南", "西", "北"};
            String[] s1 = {"无", "南", "西", "北"};
            String[] s2 = {"无", "东", "西", "北"};
            String[] s3 = {"无", "东", "南", "北"};
            String[] s4 = {"无", "东", "南", "西"};
            String[][] ss = {s1, s2, s3, s4};

            for (int i = 0; i < strings.length; i++) {
                map.put(strings[i], Arrays.asList(ss[i]));
            }
        } else if (type == HOUSETYPE) {
            title.setText("设置户型");
            String[] strings = {"0室", "1室", "2室", "3室", "3室以上"};
            String[] s1 = {"0厅", "1厅", "2厅", "3厅", "3厅以上"};
            String[] s2 = {"0厅", "1厅", "2厅", "3厅", "3厅以上"};
            String[] s3 = {"0厅", "1厅", "2厅", "3厅", "3厅以上"};
            String[] s4 = {"0厅", "1厅", "2厅", "3厅", "3厅以上"};
            String[] s5 = {"0厅", "1厅", "2厅", "3厅", "3厅以上"};


            String[][] ss = {s1, s2, s3, s4, s5};

            for (int i = 0; i < strings.length; i++) {
                map.put(strings[i], Arrays.asList(ss[i]));
            }
        }

        return map;
    }

    private HashMap<String, List> createChildDatas(int type) {

        HashMap<String, List> map = new HashMap<String, List>();
        if (type == HOUSETYPE) {

            String[] strings = {"0厅", "1厅", "2厅", "3厅", "3厅以上"};
            String[] s1 = {"0卫", "1卫", "2卫", "3卫", "3卫以上"};
            String[] s2 = {"0卫", "1卫", "2卫", "3卫", "3卫以上"};
            String[] s3 = {"0卫", "1卫", "2卫", "3卫", "3卫以上"};
            String[] s4 = {"0卫", "1卫", "2卫", "3卫", "3卫以上"};
            String[] s5 = {"0卫", "1卫", "2卫", "3卫", "3卫以上"};

            String[][] ss = {s1, s2, s3, s4, s5};


            for (int i = 0; i < strings.length; i++) {
                map.put(strings[i], Arrays.asList(ss[i]));
            }
        } else if (type == ORIENTATIONS) {
            String[] strings = {"东", "南", "西", "北", "无"};
            String[] s1 = {"无", "南", "西", "北"};
            String[] s2 = {"无", "东", "西", "北"};
            String[] s3 = {"无", "东", "南", "北"};
            String[] s4 = {"无", "东", "南", "西"};
            String[] s5 = {"无", "东", "南", "西"};
            String[][] ss = {s1, s2, s3, s4, s5};

            for (int i = 0; i < strings.length; i++) {
                map.put(strings[i], Arrays.asList(ss[i]));
            }
        }
        return map;
    }

    private HashMap<String, List> createChildDatas2(int type) {
        HashMap<String, List> map = new HashMap<String, List>();
        if (type == HOUSETYPE) {
            String[] strings = {"0卫", "1卫", "2卫", "3卫", "3卫以上"};
            String[] s1 = {"0厨", "1厨", "2厨", "3厨", "3厨以上"};
            String[] s2 = {"0厨", "1厨", "2厨", "3厨", "3厨以上"};
            String[] s3 = {"0厨", "1厨", "2厨", "3厨", "3厨以上"};
            String[] s4 = {"0厨", "1厨", "2厨", "3厨", "3厨以上"};
            String[] s5 = {"0厨", "1厨", "2厨", "3厨", "3厨以上"};

            String[][] ss = {s1, s2, s3, s4, s5};

            for (int i = 0; i < strings.length; i++) {
                map.put(strings[i], Arrays.asList(ss[i]));
            }
        } else if (type == ORIENTATIONS) {
            String[] strings = {"东", "南", "西", "北", "无"};
            String[] s1 = {"无", "南", "西", "北"};
            String[] s2 = {"无", "东", "西", "北"};
            String[] s3 = {"无", "东", "南", "北"};
            String[] s4 = {"无", "东", "南", "西"};
            String[] s5 = {"东", "南", "西", "北"};
            String[][] ss = {s1, s2, s3, s4, s5};

            for (int i = 0; i < strings.length; i++) {
                map.put(strings[i], Arrays.asList(ss[i]));
            }
        }
        return map;
    }

    private HashMap<String, List> createChildDatas3() {
        HashMap<String, List> map = new HashMap<String, List>();
        String[] strings = {"0厨", "1厨", "2厨", "3厨", "3厨以上"};
        String[] s1 = {"0阳", "1阳", "2阳", "3阳", "3阳以上"};
        String[] s2 = {"0阳", "1阳", "2阳", "3阳", "3阳以上"};
        String[] s3 = {"0阳", "1阳", "2阳", "3阳", "3阳以上"};
        String[] s4 = {"0阳", "1阳", "2阳", "3阳", "3阳以上"};
        String[] s5 = {"0阳", "1阳", "2阳", "3阳", "3阳以上"};

        String[][] ss = {s1, s2, s3, s4, s5};

        for (int i = 0; i < strings.length; i++) {
            map.put(strings[i], Arrays.asList(ss[i]));
        }
        return map;
    }

}
