package com.cqxy.bean;

import java.util.List;

/**
 * Created by lpl on 17-10-19.
 */

public class HomeImgs {


    private List<HomeimgsBean> homeimgs;

    public List<HomeimgsBean> getHomeimgs() {
        return homeimgs;
    }

    public void setHomeimgs(List<HomeimgsBean> homeimgs) {
        this.homeimgs = homeimgs;
    }

    public static class HomeimgsBean {
        /**
         * filename : room_one_bg.png
         * url : /system/homeimgs/assets/000/000/007/original/room_one_bg.png?1508412409
         * name : 别墅图片
         */

        private String filename;
        private String url;
        private String name;

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
