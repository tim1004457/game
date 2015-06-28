package com.pig8.api.business.entity;

/**
 * Created by bj on 2015/6/28.
 */
public class CommentEntity {
    String uid;
    String comment;
    String commentUserNickName;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentUserNickName() {
        return commentUserNickName;
    }

    public void setCommentUserNickName(String commentUserNickName) {
        this.commentUserNickName = commentUserNickName;
    }
}
