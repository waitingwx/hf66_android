package com.cqxy.bean;

/**
 * Created by lpl on 17-9-21.
 */

public class PointGridBean {
    private String num,content;

    public PointGridBean(String num, String content) {
        this.num = num;
        this.content = content;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
