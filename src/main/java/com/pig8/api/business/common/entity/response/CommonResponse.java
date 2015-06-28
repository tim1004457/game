package com.pig8.api.business.common.entity.response;

import com.pig8.api.platform.global.ReturnEnum;

/**
 * @author navy
 */
public class   CommonResponse<T> {
    public static final CommonResponse RESPONSE_SUCC = new CommonResponse();
    public static final CommonResponse RESPONSE_FAILED = new CommonResponse(ReturnEnum.ERROR_SYSTEM);

    private int returnCode = ReturnEnum.SUCCESS.getReturnCode();// 返回码
    private String returnMsg = ReturnEnum.SUCCESS.getReturnMsg();// 返回描述
    ReturnEnum returnEnum = ReturnEnum.SUCCESS;
    private T data;

    public String toJsonString() {
        return String.format("{\"returnCode\":%d, \"returnMsg\":\"%s\", \"data\":%s}", returnCode,
                             returnMsg, data);
    }

    public CommonResponse() {
    }
    public ReturnEnum getReturn(){
        return returnEnum;
    }

    public CommonResponse(ReturnEnum returnEnum) {
        this.returnEnum = returnEnum;
        this.returnCode = returnEnum.getReturnCode();
        this.returnMsg = returnEnum.getReturnMsg();
    }
    public CommonResponse(T data) {
        this.data = data;
    }


    public CommonResponse(int returnCode, String returnMsg, T data) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
        this.data = data;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
