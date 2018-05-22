package com.cqxy.bean;

/**
 * Created by Administrator on 2017/8/30.
 */

public class GridViewItem {
    private int res_imgId;
    private String function_name;

    public GridViewItem(int res_imgId, String function_name) {
        this.res_imgId = res_imgId;
        this.function_name = function_name;
    }

    public int getRes_imgId() {
        return res_imgId;
    }

    public void setRes_imgId(int res_imgId) {
        this.res_imgId = res_imgId;
    }

    public String getFunction_name() {
        return function_name;
    }

    public void setFunction_name(String function_name) {
        this.function_name = function_name;
    }
}
