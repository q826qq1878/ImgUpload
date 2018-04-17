/**
 * *.java huguoqing
 * Copyright(c) 2000-2010 HC360.COM, All Rights Reserved.
 */
package com.jjc.imgup.web.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 
 * ��������
 * �����ߣ� huguoqing
 * ��Ŀ��ƣ� transaction
 * ����ʱ�䣺 Jan 10, 2013 11:55:01 AM
 * �汾�ţ� v1.0
 */
public class GenericBean {
	private static GenericBean instance;
	
	private GenericBean() {}

	public static GenericBean getInstance() {
	    if(instance == null)
	    	instance = new GenericBean();
	    return instance; 
	}

	public static UserSession getLoginUserSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        UserSession userSession = (UserSession) session.getAttribute("AppConstants.REGISTER_SESSION_KEY");
        return userSession;
	}
	
	public static String getLocalIp(){
		String ip = null;
		try {
			InetAddress localhost = InetAddress.getLocalHost();
			ip = localhost.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip;
	}
}
