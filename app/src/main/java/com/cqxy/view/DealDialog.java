package com.cqxy.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.cqxy.fyb.R;

/**
 * Created by Administrator on 2017/9/8.
 */

public class DealDialog  {
    private Dialog dialog;

    private Context context;
    private TextView dialog_chat,dialog_deal;

    public DealDialog( Context context) {
        this.context = context;
    }

    public void showDialog(){
        dialog=new Dialog(context, R.style.Theme_Dialog);
        dialog.setContentView(R.layout.deal_dialog_layout);
        dialog_chat=dialog.findViewById(R.id.dialog_chat);
        dialog_deal=dialog.findViewById(R.id.dialog_deal);
        dialog.show();
    }

    public  void dismissDialog()
    {
        if (dialog!=null)
        {
            dialog.dismiss();
        }
    }

    public interface itemInterface{
        void setOnItemListener();
    }

    public itemInterface itemInterfaces;



    public void setDealListener( itemInterface itemInterface)
    {
        final itemInterface itf=itemInterface;
        dialog_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itf.setOnItemListener();
            }
        });
    }
    public void setonitemfaceListener(itemInterface itemInterface)
    {
        this.itemInterfaces=itemInterface;
    }



    public void setChatListener( itemInterface itemInterface)
    {
        final itemInterface itf=itemInterface;
        dialog_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itf.setOnItemListener();
            }
        });
    }


}
