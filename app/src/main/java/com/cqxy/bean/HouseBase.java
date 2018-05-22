package com.cqxy.bean;

/**
 * Created by Administrator on 2017/9/1.
 */

public class HouseBase {
    private int headIcon;
    private String name,houselocation,decoration,housetype,houseclass,orientations,profit;
    private float housesize;
    private int floors;
    private float houseprice;

    public HouseBase(int headIcon, String name, String houselocation, String decoration, String housetype, String houseclass, String orientations, String profit, float housesize, int floors, float houseprice) {
        this.headIcon = headIcon;
        this.name = name;
        this.houselocation = houselocation;
        this.decoration = decoration;
        this.housetype = housetype;
        this.houseclass = houseclass;
        this.orientations = orientations;
        this.profit = profit;
        this.housesize = housesize;
        this.floors = floors;
        this.houseprice = houseprice;
    }

    public int getHeadIcon() {
        return headIcon;
    }

    public String getHouseclass() {
        return houseclass;
    }

    public void setHouseclass(String houseclass) {
        this.houseclass = houseclass;
    }

    public void setHeadIcon(int headIcon) {
        this.headIcon = headIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouselocation() {
        return houselocation;
    }

    public void setHouselocation(String houselocation) {
        this.houselocation = houselocation;
    }

    public String getDecoration() {
        return decoration;
    }

    public void setDecoration(String decoration) {
        this.decoration = decoration;
    }

    public String getHousetype() {
        return housetype;
    }

    public void setHousetype(String housetype) {
        this.housetype = housetype;
    }

    public String getOrientations() {
        return orientations;
    }

    public void setOrientations(String orientations) {
        this.orientations = orientations;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public float getHousesize() {
        return housesize;
    }

    public void setHousesize(float housesize) {
        this.housesize = housesize;
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public float getHouseprice() {
        return houseprice;
    }

    public void setHouseprice(float houseprice) {
        this.houseprice = houseprice;
    }
}
