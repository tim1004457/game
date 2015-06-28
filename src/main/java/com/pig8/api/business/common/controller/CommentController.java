package com.pig8.api.business.common.controller;

import com.pig8.api.business.common.entity.request.PageQueryRequest;
import com.pig8.api.business.common.entity.response.CommonResponse;
import com.pig8.api.business.common.service.CommentService;
import com.pig8.api.business.entity.CommentEntity;
import com.pig8.api.business.entity.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by bj on 2015/6/28.
 */
@Controller
public class CommentController {
    @Autowired
    CommentService commentServicer;

    @RequestMapping(value = "/comment/add/user")
    @ResponseBody
    public CommonResponse addUser(UserEntity entity) {
        return commentServicer.addUser(entity);
    }

    @RequestMapping(value = "/comment/add/comment")
    @ResponseBody
    public CommonResponse addComment(CommentEntity entity) {
        return commentServicer.addComment(entity);
    }

    @RequestMapping(value = "/comment/list")
    @ResponseBody
    public List<CommentEntity> listComment(UserEntity entity) {
        return commentServicer.listComment(entity);
    }

    @RequestMapping(value = "/word/list")
    @ResponseBody
    public List<String > listWord(PageQueryRequest request) {
        return commentServicer.listWord(request);
    }
}
