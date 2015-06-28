package com.pig8.api.business.common.service.impl;

import com.pig8.api.business.common.entity.request.PageQueryRequest;
import com.pig8.api.business.common.entity.response.CommonResponse;
import com.pig8.api.business.common.service.CommentService;
import com.pig8.api.business.entity.CommentEntity;
import com.pig8.api.business.entity.UserEntity;
import com.pig8.api.platform.dao.CommentDao;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

/**
 * Created by bj on 2015/6/28.
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    CommentDao commentDao;

    public CommonResponse addComment(CommentEntity item) {
        commentDao.addComment(item);
        return null;
    }

    public CommonResponse addUser(UserEntity entity) {
        commentDao.addUser(entity);
        return CommonResponse.RESPONSE_SUCC;
    }

    public List<String> listWord(PageQueryRequest request) {
        return commentDao.getWordList(request);
    }

    public List<CommentEntity> listComment(UserEntity entity) {
        return commentDao.getComment(entity);
    }
}
