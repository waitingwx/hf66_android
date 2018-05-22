package com.cqxy.bean;

import java.util.List;

/**
 * Created by lpl on 17-10-19.
 */

public class ZhanBaoBean {

    private List<CommentsBean> comments;

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class CommentsBean {
        /**
         * content : 喜羊羊和灰太郎完成了一笔交易
         * created_at : 2017-10-19T03:02:13.733Z
         * updated_at : 2017-10-19T03:02:13.733Z
         */

        private String content;
        private String created_at;
        private String updated_at;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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
