package com.cqxy.bean;

/**
 * Created by PeLon on 2017/9/19.
 */

public class downbean {
    private String filename;
    private int fileimg;

    public downbean(String filename, int fileimg) {
        this.filename = filename;
        this.fileimg = fileimg;
    }

    public String getfilename() {
        return filename;
    }

    public void setfilename(String filename) {
        this.filename = filename;
    }

    public int getfileimg() {
        return fileimg;
    }

    public void setfileimg(int fileimg) {
        this.fileimg = fileimg;
    }

}
