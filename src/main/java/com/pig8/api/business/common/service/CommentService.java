package com.pig8.api.business.common.service;

import com.pig8.api.business.common.entity.request.PageQueryRequest;
import com.pig8.api.business.common.entity.response.CommonResponse;
import com.pig8.api.business.entity.CommentEntity;
import com.pig8.api.business.entity.UserEntity;

import java.util.List;

/**
 * Created by bj on 2015/6/28.
 */

public interface CommentService {
    public CommonResponse addComment(CommentEntity item);

    public CommonResponse addUser(UserEntity entity);

    public List<String> listWord(PageQueryRequest request);

    public List<CommentEntity> listComment(UserEntity entity);
}
