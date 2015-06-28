/**
 * 
 */
package com.pig8.api.business.user.entity.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author navy
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterRequest {
	//mobile、email二选一
	private String mobile;
	private String email;
	private String password;
	private String avatar;//头像
	private int id;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
