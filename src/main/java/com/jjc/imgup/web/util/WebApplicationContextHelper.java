/**
 * *.java huguoqing
 * Copyright(c) 2000-2010 HC360.COM, All Rights Reserved.
 */
package com.jjc.imgup.web.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * ����������ȡspring����ȫ�ֵ�context����
 * �����ߣ� huguoqing
 * ��Ŀ��ƣ� transaction
 * ����ʱ�䣺 Jan 10, 2013 11:56:25 AM
 * �汾�ţ� v1.0
 */
public class WebApplicationContextHelper implements ApplicationContextAware {
	
	private static ApplicationContext context;
	
	/**
	 * function description
	 * 
	 * @param arg0
	 * @throws BeansException
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context=context;
	}
	
	/**
	 * 
	 * function description
	 * 
	 * @return
	 */
	public static ApplicationContext getApplicationContext(){
		return context;
	}

}
