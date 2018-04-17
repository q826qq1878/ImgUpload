/**
 * *.java huguoqing
 * Copyright(c) 2000-2010 HC360.COM, All Rights Reserved.
 */
package com.jjc.imgup.web.util;
/**
 * class description
 * 
 * @author huguoqing
 * @version 4.0 May 7, 2012
 * @since 4.0	
 */
public class UserSession {
	private String userName;
	private String loginname;
	private long ssoid;
	private String email;
	private String isAdmin;
	/**
	 * @return
	 */
	public String getIsAdmin() {
		return isAdmin;
	}
	/**
	 * @param
	 */
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
	/**
	 * @return
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return
	 */
	public String getLoginname() {
		return loginname;
	}
	/**
	 * @param
	 */
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	/**
	 * @return
	 */
	public long getSsoid() {
		return ssoid;
	}
	/**
	 * @param
	 */
	public void setSsoid(long ssoid) {
		this.ssoid = ssoid;
	}
	/**
	 * @return
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
