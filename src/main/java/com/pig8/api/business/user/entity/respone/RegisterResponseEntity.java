/**
 * 
 */
package com.pig8.api.business.user.entity.respone;

/**
 * @author navy
 *
 */
public class RegisterResponseEntity {
	
	private int id;
	//昵称
	private String nickname;
	//头像
	private String avatar;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}
