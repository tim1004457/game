package com.pig8.api.platform.dao;

import com.pig8.api.business.common.entity.request.PageQueryRequest;
import com.pig8.api.business.entity.CommentEntity;
import com.pig8.api.business.entity.UserEntity;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by bj on 2015/6/28.
 */
@Repository
public class CommentDao extends BaseDaoSupport {
    public boolean addUser(UserEntity entity) {
        return insert("insertUser", entity);
    }

    public boolean addComment(CommentEntity entity) {
        return insert("addUserComment",entity);
    }

    public List<CommentEntity> getComment(UserEntity entity) {
        return find("getUserComent", entity);
    }

    public List<String> getWordList(PageQueryRequest request) {
        return find("getWord", request.getQueryMap());
    }
}
