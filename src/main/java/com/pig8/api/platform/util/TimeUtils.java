package com.pig8.api.platform.util;

import java.text.SimpleDateFormat;

/**
 * Created by bj on 2015/6/3.
 */
public class TimeUtils {
    public static final  long TIME_STEVE = 10000000000l;
    public static final String format(long time) {
        if ( time < TIME_STEVE)
            time*=1000;
        return fullTimeFormat.get().format(time);
    }

    private static ThreadLocal<SimpleDateFormat> fullTimeFormat =
            new ThreadLocal<SimpleDateFormat>() {
                @Override
                protected SimpleDateFormat initialValue() {
                    return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                }
            };
}
