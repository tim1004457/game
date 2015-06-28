package com.pig8.api.platform.util;

import com.pig8.api.platform.global.Constants;

/**
 * Created by bj on 2015/6/1.
 */
public class ImageUtils {

    public static String getImgUrl(String imgPath) {
        if (imgPath != null && !imgPath.startsWith("http")) {
            imgPath = Constants.IMG_DOMAIN + imgPath;
        }
        return imgPath;
    }
}
