package com.cqxy.utils;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cqxy.fyb.House_enterActivity;
import com.cqxy.fyb.R;

/**
 * Created by Administrator on 2017/9/14.
 */

public class PopWindowUtils {
    public static void ShowPopwin(House_enterActivity context, View view, ListAdapter adapter, final TextView textView) {
        final PopupWindow popMenu = new PopupWindow(
                context.width/ 3,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        View v = context.getLayoutInflater().inflate(R.layout.popwindow_layout, null);
        ListView pop_listview = (ListView) v.findViewById(R.id.pop_list);
        pop_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                textView.setText((String) adapterView.getItemAtPosition(i));
                popMenu.dismiss();
            }
        });
        pop_listview.setAdapter(adapter);
        popMenu.setContentView(v);
        popMenu.setOutsideTouchable(true);
        popMenu.setBackgroundDrawable(context.getResources().getDrawable(R.color.colorWhite));
        popMenu.setFocusable(true);
        popMenu.showAsDropDown(view);


    }
}
