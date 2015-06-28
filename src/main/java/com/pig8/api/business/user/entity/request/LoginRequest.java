/**
 * 
 */
package com.pig8.api.business.user.entity.request;

/**
 * @author navy
 *
 */
public class LoginRequest {
	//登录名，可以是email, mobile
	private String loginName;
	private String password;
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
