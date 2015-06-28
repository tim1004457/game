package com.pig8.api.platform.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by bj on 2015/6/18.
 */
public class LogUtils {
    private static Logger logger = LoggerFactory.getLogger("error_log");

    public static void error(Exception e) {
        logger.error(e.getMessage(), e);
    }
}
