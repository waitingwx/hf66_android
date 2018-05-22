package com.cqxy.bean;

/**
 * Created by lpl on 17-9-20.
 */

public class RecommendBean {
    private String name;
    private String getmoney;
    private String time;

    public RecommendBean(String name, String getmoney, String time) {
        this.name = name;
        this.getmoney = getmoney;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGetmoney() {
        return getmoney;
    }

    public void setGetmoney(String getmoney) {
        this.getmoney = getmoney;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
