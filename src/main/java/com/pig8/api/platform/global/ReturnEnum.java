package com.pig8.api.platform.global;


	
public enum ReturnEnum {
	
	SUCCESS(0, "成功"),
	ERROR_SYSTEM(1001, "系统错误"),
	ERROR_PWD(1002, "密码错误"),
	ERROR_LOGINNAME_NOT_EXIST(1003, "用户名不存在"),
	ERROR_EMAIL_EXIST(1004, "此email已注册"),
	ERROR_MOBILE_EXIST(1005, "此手机号码已注册"),
	ERROR_TOKEN_EXPIRE(1006, "TOKEN已失效，请重新登录"),
	ERROR_NOT_LOGIN(1007, "未登录，请登录"),
	ERROR_WECHANT_AUTH(1008, "微信授权失败"),
	ERROR_File_UPLOAD(2001, "文件上传失败"),
	ERROR_EXIST_TOPIC(3001, "专题已存在"),
	ERROR_EXIST_SN(3002, "排位冲突"),
	ERROR_EXIST_DEST(3004, "目的地已存在"),
	ERROR_EXIST_JOURNEY(3005, "行程已存在"),
	ERROR_EXIST_APP_HOME_ONE_DEST(3006, "首页只允许添加一个目的地类型卡片"),
	ERROR_EXIST_APP_HOME_ONE_TOPIC(3007, "首页只允许添加一个目的地类型卡片"),
	ERROR_EXIST_DEST_ERROR(3008, "目的地输入有误"),
	ERROR_EXIST_TOPIC_ERROR(3009, "专题输入有误"),
	ERROR_LOGIN_FAILE(3010, "登录失败，账号密码错误"),
	ERROR_EXIST_GUIDE(9999, "达人已存在");



	private int returnCode;
	private String returnMsg;

	ReturnEnum(int returnCode, String returnMsg) {
		this.returnCode = returnCode;
		this.returnMsg = returnMsg;
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

}
