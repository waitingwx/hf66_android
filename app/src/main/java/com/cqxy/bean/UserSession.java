package com.cqxy.bean;

/**
 * Created by lpl on 17-9-28.
 */

public class UserSession {


    /**
     * session : {"phone_num":"18435205083","id":13,"token":"vXTIa3JF49aiXVv2WWj4c3iBTh6u3C95h38Tn+yck6PFZbhPgu9FQ7iuJD0PnGrZEsxXOAMjHMTso318F9/NmA=="}
     */

    private SessionBean session;

    public SessionBean getSession() {
        return session;
    }

    public void setSession(SessionBean session) {
        this.session = session;
    }

    public static class SessionBean {
        /**
         * phone_num : 18435205083
         * id : 13
         * token : vXTIa3JF49aiXVv2WWj4c3iBTh6u3C95h38Tn+yck6PFZbhPgu9FQ7iuJD0PnGrZEsxXOAMjHMTso318F9/NmA==
         */

        private String phone_num;
        private int id;
        private String token;

        public String getPhone_num() {
            return phone_num;
        }

        public void setPhone_num(String phone_num) {
            this.phone_num = phone_num;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return "SessionBean{" +
                    "phone_num='" + phone_num + '\'' +
                    ", id=" + id +
                    ", token='" + token + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "session=" + session +
                '}';
    }
}
