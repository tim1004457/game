package com.pig8.api.test;

import com.pig8.api.business.common.service.CommentService;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by bj on 2015/6/28.
 */
@ContextConfiguration(locations = {"classpath*:spring/application-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class Test {

    @Autowired
    CommentService commentService;

    @org.junit.Test
    public void test() {

    }
}
