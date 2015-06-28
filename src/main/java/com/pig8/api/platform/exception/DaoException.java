package com.pig8.api.platform.exception;

/**
 * @author navy
 *
 */
public class DaoException extends BaseException {

	private static final long serialVersionUID = 3683410505240807189L;

	public DaoException(String errCode, String desc) {
		super(errCode, desc);
	}

	public DaoException(String errCode) {
		super(errCode, "");
	}

	public DaoException(String msg, Throwable t) {
		super(msg, t);
	}
}
