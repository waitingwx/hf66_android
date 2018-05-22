package com.cqxy.bean;

/**
 * Created by wx on 2017/11/30.
 */

public class LoadFilteredEvent {
    String strBalcony = "0阳";
    String strBathroom = "0卫";
    String strBedroom = "0室";
    String strKitchen = "0厨";
    String strLivingroom = "0厅";

    String housetype  = "";
    String strPrevalue, strEndvalue, strPrearea, strEndarea, strPreage, strEndage, strPrefloor, strEndfloor;
    String strDecoration;
    String strAspect;

    public LoadFilteredEvent(String strPrevalue, String strEndvalue) {
        this.strPrevalue = strPrevalue;
        this.strEndvalue = strEndvalue;
    }

    public String getHousetype() {
        return housetype;
    }

    public LoadFilteredEvent setHousetype(String housetype) {
        this.housetype = housetype;
        return this;
    }

    public LoadFilteredEvent(String strPrevalue, String strEndvalue, String strPrearea, String strEndarea, String strDecoration, String strAspect) {
        this.strPrevalue = strPrevalue;
        this.strEndvalue = strEndvalue;
        this.strPrearea = strPrearea;
        this.strEndarea = strEndarea;
        this.strDecoration = strDecoration;
        this.strAspect = strAspect;
    }

    public String getStrBalcony() {
        return strBalcony;
    }

    public LoadFilteredEvent setStrBalcony(String strBalcony) {
        this.strBalcony = strBalcony;
        return this;
    }

    public String getStrBathroom() {
        return strBathroom;
    }

    public LoadFilteredEvent setStrBathroom(String strBathroom) {
        this.strBathroom = strBathroom;
        return this;
    }

    public String getStrBedroom() {
        return strBedroom;
    }

    public LoadFilteredEvent setStrBedroom(String strBedroom) {
        this.strBedroom = strBedroom;
        return this;
    }

    public String getStrKitchen() {
        return strKitchen;
    }

    public LoadFilteredEvent setStrKitchen(String strKitchen) {
        this.strKitchen = strKitchen;
        return this;
    }

    public String getStrLivingroom() {
        return strLivingroom;
    }

    public LoadFilteredEvent setStrLivingroom(String strLivingroom) {
        this.strLivingroom = strLivingroom;
        return this;
    }

    public String getStrPrevalue() {
        return strPrevalue;
    }

    public LoadFilteredEvent setStrPrevalue(String strPrevalue) {
        this.strPrevalue = strPrevalue;
        return this;
    }

    public String getStrEndvalue() {
        return strEndvalue;
    }

    public LoadFilteredEvent setStrEndvalue(String strEndvalue) {
        this.strEndvalue = strEndvalue;
        return this;
    }

    public String getStrPrearea() {
        return strPrearea;
    }

    public LoadFilteredEvent setStrPrearea(String strPrearea) {
        this.strPrearea = strPrearea;
        return this;
    }

    public String getStrEndarea() {
        return strEndarea;
    }

    public LoadFilteredEvent setStrEndarea(String strEndarea) {
        this.strEndarea = strEndarea;
        return this;
    }

    public String getStrDecoration() {
        return strDecoration;
    }

    public LoadFilteredEvent setStrDecoration(String strDecoration) {
        this.strDecoration = strDecoration;
        return this;
    }

    public String getStrAspect() {
        return strAspect;
    }

    public LoadFilteredEvent setStrAspect(String strAspect) {
        this.strAspect = strAspect;
        return this;
    }

    public String getStrPreage() {
        return strPreage;
    }

    public LoadFilteredEvent setStrPreage(String strPreage) {
        this.strPreage = strPreage;
        return this;
    }

    public String getStrEndage() {
        return strEndage;
    }

    public LoadFilteredEvent setStrEndage(String strEndage) {
        this.strEndage = strEndage;
        return this;
    }

    public String getStrPrefloor() {
        return strPrefloor;
    }

    public LoadFilteredEvent setStrPrefloor(String strPrefloor) {
        this.strPrefloor = strPrefloor;
        return this;
    }

    public String getStrEndfloor() {
        return strEndfloor;
    }

    public LoadFilteredEvent setStrEndfloor(String strEndfloor) {
        this.strEndfloor = strEndfloor;
        return this;
    }
}
