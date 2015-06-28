package com.pig8.api.platform.exception;

import org.springframework.core.NestedRuntimeException;

/**
 * @author navy
 *
 */
public class BaseException extends NestedRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4726774628915446659L;
	
	// 异常代码
	private String errCode;

	// 异常信息
	private String errMessage;
		
	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	public BaseException(String errCode, String msg) {
		super(msg);
		this.errCode = errCode;
		this.errMessage = msg;
	}

	public BaseException(String errCode, String msg, Throwable cause) {
		super(msg, cause);
		this.errCode = errCode;
		this.errMessage = msg;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public BaseException(String msg, Throwable cause) {
		super(msg, cause);
		this.errMessage = msg;
	}

	public BaseException(String msg) {
		super(msg);
		this.errMessage = msg;
	}
	
	@Override
	public String toString() {
		return errCode+" : "+getMessage();
	}
}
