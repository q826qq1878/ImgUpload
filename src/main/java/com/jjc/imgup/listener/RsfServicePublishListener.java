/**
 * *.java huguoqing
 * Copyright(c) 2000-2010 HC360.COM, All Rights Reserved.
 */
package com.jjc.imgup.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 * ע�����ķ��񷢲�ע�������
 * @author huguoqing
 * @version 4.0 May 8, 2012
 * @since 4.0	
 */
public class RsfServicePublishListener implements ServletContextListener {
	final Logger log = LoggerFactory.getLogger(RsfServicePublishListener.class);
	
	private static ApplicationContext applicationContext;
	
	@Autowired
//	RegisterService registerService;applicationContext
	
    public final void contextDestroyed(ServletContextEvent sce) {
    	log.info("register server is Destroyed.");
    }

    public final void contextInitialized(ServletContextEvent sce) {
    	applicationContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        start();
    } 
    
	private void start() {
		try{
	        //��������
		}catch(Exception e){
			log.error("---------======= Server start listener throw exception=====-----",e);
		}
	}	
}
