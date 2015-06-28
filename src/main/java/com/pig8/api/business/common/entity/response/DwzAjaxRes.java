package com.pig8.api.business.common.entity.response;

import com.pig8.api.platform.global.ReturnEnum;

/**
 * 描述：dwz ajax响应实体
 * 作者：Milton
 * 时间：2014-7-9
 */
public class DwzAjaxRes {
    private String statusCode = "200";//200：成功，300：操作失败，301会话超时
    private String message = "操作成功";
    private String navTabId = "";
    private String rel = "";
    private String callbackType = "closeCurrent";
    private String forwardUrl = "";
    public final static String STATUSCODE_300 = "300";
    public static final DwzAjaxRes TIME_OUT=new DwzAjaxRes("301","会话超时","","","","");
    public static final DwzAjaxRes NOT_LOGIN=new DwzAjaxRes("301","尚未登录","","","","");
    public static final DwzAjaxRes SUCCESS=new DwzAjaxRes("200","","","","closeCurrent","");
    public static final DwzAjaxRes FAILED=new DwzAjaxRes("300","","","","closeCurrent","");
    public DwzAjaxRes() {
        super();
    }

    public DwzAjaxRes(String statusCode, String message, String navTabId, String rel,
                      String callbackType, String forwardUrl) {
        super();
        this.statusCode = statusCode;
        this.message = message;
        this.navTabId = navTabId;
        this.rel = rel;
        this.callbackType = callbackType;
        this.forwardUrl = forwardUrl;
    }

    public DwzAjaxRes(String returnMsg) {
        this.message = returnMsg;
    }

    public DwzAjaxRes(ReturnEnum returnEnum) {
        if (returnEnum != ReturnEnum.SUCCESS) {
            this.statusCode = STATUSCODE_300;
        }
        this.message = returnEnum.getReturnMsg();
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNavTabId() {
        return navTabId;
    }

    public void setNavTabId(String navTabId) {
        this.navTabId = navTabId;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getCallbackType() {
        return callbackType;
    }

    public void setCallbackType(String callbackType) {
        this.callbackType = callbackType;
    }

    public String getForwardUrl() {
        return forwardUrl;
    }

    public void setForwardUrl(String forwardUrl) {
        this.forwardUrl = forwardUrl;
    }

}
