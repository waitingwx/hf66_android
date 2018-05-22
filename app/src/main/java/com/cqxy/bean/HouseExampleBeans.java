package com.cqxy.bean;

/**
 * Created by PeLon on 2017/10/15.
 */

public class HouseExampleBeans {

    /**
     * houseexample : {"id":1,"position":"","rent":2000,"floor":20,"house_area":200,"user_id":2,"created_at":"2017-09-29T08:13:56.234Z","updated_at":"2017-10-11T11:25:35.576Z"}
     */

    private HouseexampleBean houseexample;

    public HouseexampleBean getHouseexample() {
        return houseexample;
    }

    public void setHouseexample(HouseexampleBean houseexample) {
        this.houseexample = houseexample;
    }

    public static class HouseexampleBean {
        /**
         * id : 1
         * position :
         * rent : 2000
         * floor : 20
         * house_area : 200
         * user_id : 2
         * created_at : 2017-09-29T08:13:56.234Z
         * updated_at : 2017-10-11T11:25:35.576Z
         */

        private int id;
        private String position;
        private int rent;
        private int floor;
        private int house_area;
        private int user_id;
        private String created_at;
        private String updated_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
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
    }
}
