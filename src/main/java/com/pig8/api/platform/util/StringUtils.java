package com.pig8.api.platform.util;

import com.pig8.api.business.common.entity.response.CommonResponse;
import com.pig8.api.business.common.entity.response.DwzAjaxRes;
import com.pig8.api.platform.global.ReturnEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author navy
 */
public class StringUtils {

    /**
     * 通过解析模版
     */
    public static String parser(String inputString, Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        boolean hasPrefix = false;
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i<inputString.length(); i++) {
            char c = inputString.charAt(i);

            switch (c) {
                case '$':
                    if (i + 1<inputString.length() && inputString.charAt(i + 1) == '{') {
                        if (hasPrefix) {
                            throw new RuntimeException("语法错误!");
                        }
                        hasPrefix = true;
                        i++;
                        break;
                    }

                case '}':
                    if (hasPrefix && (c == '}')) {
                        String key = temp.toString().trim();
                        String value = map.get(key);
                        if (value != null && value.trim().length()>0) {
                            sb.append(map.get(key));
                            hasPrefix = false;
                            temp = new StringBuilder();
                            break;
                        }

                    }

                default:
                    if (hasPrefix) {
                        temp.append(c);
                    } else {
                        sb.append(c);
                    }
                    break;
            }
        }
        if (hasPrefix) {
            sb.append("${" + temp.toString());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("a", " ");
        String a = "%${a }%";
        String s = parser(a, map);
        System.out.println(s);
    }

    /**
     * 是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.equals("")) {
            return true;
        } else {
            if (str.trim().equals(""))
                return true;
        }

        return false;
    }

    /**
     * 是否为空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        if (str == null || str.equals("")) {
            return false;
        } else {
            if (str.trim().equals(""))
                return false;
        }

        return true;
    }

    /**
     * 返回整形数的指定长度，指定填充因子的字符串
     * @param number ,指定整形数
     * @param destLength指定长度
     * @param paddedChar指定填充因子
     * @return 如果该整形数长度大于指定长度。截到一部分，如果小于指定长度，左填充指定填充因子
     */
    public static String formatNumber(int number, int destLength, char paddedChar) {
        String oldString = String.valueOf(number);
        StringBuffer newString = new StringBuffer("");
        int oldLength = oldString.length();
        if (oldLength>destLength) {
            newString.append(oldString.substring(oldLength - destLength));
        } else if (oldLength == destLength) {
            newString.append(oldString);
        } else {
            for (int i = 0; i<destLength - oldLength; i++) {
                newString.append(paddedChar);
            }
            newString.append(oldString);
        }
        return newString.toString();
    }

    /**
     * 生成指定长度的随机字符串（数字）
     * @param length
     * @return
     */
    public static final String randomNumber(int length) {
        Random randGen = null;
        char[] numbersAndLetters = null;
        Object initLock = new Object();

        if (length<1) {
            return null;
        }
        if (randGen == null) {
            synchronized (initLock) {
                if (randGen == null) {
                    randGen = new Random();
                    numbersAndLetters = ("0123456789").toCharArray();
                }
            }
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i<randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(9)];
            // randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
        }
        return new String(randBuffer);
    }

    /**
     * 检查是否为手机号码
     * @param sMobile 被检查的字串
     * @return
     */
    public static boolean isMobileNumber(String sMobile) {
        if (sMobile == null) {
            return false;
        }
        return java.util.regex.Pattern.matches(" *1\\d{10} *", sMobile);
    }

    public static DwzAjaxRes parseFrom(CommonResponse commonResponse) {
        DwzAjaxRes res = new DwzAjaxRes();
        if (commonResponse.getReturn() != ReturnEnum.SUCCESS) {
            res.setStatusCode(DwzAjaxRes.STATUSCODE_300);
        }
        res.setMessage(commonResponse.getReturnMsg());
        return res;
    }
}
