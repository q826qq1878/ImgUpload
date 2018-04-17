package com.jjc.imgup.web.util;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public final class RedirectUtil {

	private static String loginURL = "http://sso.hc360.com/ssologin";
	
	public static void sendRedirect(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		StringBuffer sb = req.getRequestURL();
		if (req.getQueryString() != null && req.getQueryString().length() > 0)
			sb.append("?").append(req.getQueryString());

		StringBuffer redirect =new StringBuffer(loginURL);
		redirect.append("?ReturnURL=");
		redirect.append(URLEncoder.encode(sb.toString(), "UTF8"));
		res.sendRedirect(redirect.toString());
	}
	
	/**
	 * 
	 * login back sso
	 * 
	 * @param req
	 * @param res
	 * @throws IOException
	 */
	public static void sendBackRedirect(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		StringBuffer sb = req.getRequestURL();
		if (req.getQueryString() != null && req.getQueryString().length() > 0)
			sb.append("?").append(req.getQueryString());

		StringBuffer redirect =new StringBuffer("http://sso.hc360.com/internallogin");
		redirect.append("?ReturnURL=");
		redirect.append(URLEncoder.encode(sb.toString(), "UTF8"));
		res.sendRedirect(redirect.toString());
	}	
}
