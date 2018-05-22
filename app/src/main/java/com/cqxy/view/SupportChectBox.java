package com.cqxy.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IntegerRes;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cqxy.fyb.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lpl on 17-9-24.
 */

public class SupportChectBox {
    private Context context;
    private Dialog dialog;
    private LinearLayout supportlayout1,supportlayout2,supportlayout3;
    private Button commit;
    private LinearLayout[] layouts=new LinearLayout[3];
    private List<Integer> selectid;
    private List<String> selectContent;
    public SupportChectBox(Context context){
        this.context=context;
        dialog=new Dialog(context, R.style.Theme_Dialog);
        dialog.setContentView(R.layout.suportchectbox_layout);
        initViews();
        initListener();
    }
    public void initViews()
    {
        supportlayout1=(LinearLayout)dialog.findViewById(R.id.support_layout1);
        supportlayout2=(LinearLayout)dialog.findViewById(R.id.support_layout2);
        supportlayout3=(LinearLayout)dialog.findViewById(R.id.support_layout3);
            layouts[0]=supportlayout1;
            layouts[1]=supportlayout2;
            layouts[2]=supportlayout3;
        commit=(Button)dialog.findViewById(R.id.support_commit);
    }




    public void showDialog()
    {
            dialog.show();
    }

    public void initListener()
    {
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveSelectContent();
                dialog.dismiss();

                }
        });
    }

    public String getSelectText()
    {
        StringBuffer buffer=new StringBuffer();
        for (int i = 0; i <selectContent.size() ; i++) {
            buffer.append(selectContent.get(i).toString()+",");
            if (i==selectContent.size()-1)
            {
                buffer.append(selectContent.get(i).toString());
            }
        }
        return buffer.toString();
    }

    private void saveSelectContent() {
        selectContent = new ArrayList<String>();
        for (int i = 0; i < layouts.length; i++) {
            for (int j = 0; j < layouts[i].getChildCount(); j++) {
                CheckBox checkbox = (CheckBox) layouts[i].getChildAt(j);
                if (checkbox.isChecked()) {
                    selectContent.add((String) checkbox.getText());
                }
            }
        }
    }
}
