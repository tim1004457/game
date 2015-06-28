/**
 * 
 */
package com.pig8.api.business.user.entity.request;

/**
 * @author navy
 *
 */
public class UpdateUserInfoRequest {
	
	private int id;
	private int sex;
	private String nickname;//昵称
	private String birthday;//生日
	private String avatar;//头像
	private Integer continentId;
	private Integer countryId;
	private Integer provinceId;
	private Integer cityId;
	private String selfIntroduction;//自我介绍
	
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
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getSelfIntroduction() {
		return selfIntroduction;
	}
	public void setSelfIntroduction(String selfIntroduction) {
		this.selfIntroduction = selfIntroduction;
	}
	public Integer getContinentId() {
		return continentId;
	}
	public void setContinentId(Integer continentId) {
		this.continentId = continentId;
	}

}
