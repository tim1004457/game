/**
 * 
 */
package com.pig8.api.business.user.entity.request;

/**
 * @author navy
 *
 */
public class GuideInfoRequest {

	//真实姓名
	private String realName;
	//手机号码
	private String mobile;
	//邮箱
	private String email;
	//微信号
	private String wechat;
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
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
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
}
