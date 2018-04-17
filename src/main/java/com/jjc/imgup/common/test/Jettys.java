package com.jjc.imgup.common.test;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Jettys {
	private static final Logger LOG = LoggerFactory.getLogger(Jettys.class);

	private static final int PORT = 1988;
	
	public static final String SERVLET_PATH;
	
	private static Server SERVER = null;
	
	private Jettys() {}
	
	static {
		SERVLET_PATH = "http://localhost:" + PORT;
		buildNormalServer(PORT, "");
	}

	private static Server buildNormalServer(int port, String contextPath) {
		SERVER = new Server(port);
		WebAppContext webContext = new WebAppContext("src/main/webapp", contextPath);
		System.setProperty("spring.profiles.active", "test");
		SERVER.setHandler(webContext);
		SERVER.setStopAtShutdown(true);
		return SERVER;
	}
	
	public static void start() {
		if(SERVER.isRunning()) {
			return;
		}
		try {
			SERVER.start();
			System.out.println("jetty is running!");
		} catch (Exception e) {
			LOG.error("启动jetty server 出错", e);
		}
	}
	
	public static void main(String[] args) {
		start();
	}
}
