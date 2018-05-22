package com.cqxy.bean;

import java.util.List;

/**
 * Created by PeLon on 2017/10/15.
 */

public class HouseRentBean {

    private List<CommentsBean> comments;

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class CommentsBean {

        /**
         * position : 风格雅苑写字楼
         * rent : 24553
         * floor : null
         * house_area : 388
         * house_example_id : 15
         * created_at : 2017-10-12T09:11:11.556Z
         * updated_at : 2017-10-12T09:11:11.556Z
         * "userasset":"/system/users/assets/000/000/001/original/Redocn_2013092813294920.jpg?1514280747"
         */

        private String position;
        private int rent;
        private int floor;
        private int house_area;
        private int house_example_id;
        private String created_at;
        private String leixing;
        private String bvalue;
        private String updated_at;
        private String userasset;

        public String getLeixing() {
            return leixing;
        }

        public void setLeixing(String leixing) {
            this.leixing = leixing;
        }

        public String getBvalue() {
            return bvalue;
        }

        public void setBvalue(String bvalue) {
            this.bvalue = bvalue;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public int getRent() {
            return rent;
        }

        public void setRent(int rent) {
            this.rent = rent;
        }

        public int getFloor() {
            return floor;
        }

        public void setFloor(int floor) {
            this.floor = floor;
        }

        public int getHouse_area() {
            return house_area;
        }

        public void setHouse_area(int house_area) {
            this.house_area = house_area;
        }

        public int getHouse_example_id() {
            return house_example_id;
        }

        public void setHouse_example_id(int house_example_id) {
            this.house_example_id = house_example_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
        public String getUserasset() {
            return userasset;
        }

        public void setUserasset(String userasset) {
            this.userasset = userasset;
        }
    }
}
