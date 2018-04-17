package com.jjc.imgup.common.test.trace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Traces {
	static final Logger LOG = LoggerFactory.getLogger(Traces.class);
	
	public static final double MILLION = 1000000.0;

	private static final boolean TRACE = true;
	
	public static void trace(Traceable traceable) {
		trace(1, traceable);
	}

	public static void trace(int times, Traceable traceable) {
		long s = System.nanoTime();
		if (times <= 1) {
			traceable.invoke();
		} else {
			for (int i = times; i > 0; i--) {
				traceable.invoke();
			}
		}
		
		traceTime("[Trace] �� " + traceable.getName(),times, s);
	}

	public static void traceExcludeFirstTime(int times, Traceable traceable) {
		traceable.invoke();
		trace(times, traceable);
	}

	public static void traceTime(String traceName, long s) {
		if (TRACE && LOG.isDebugEnabled()) {
			double cost = (System.nanoTime() - s) / MILLION;
			LOG.debug(String.format("%s 耗时：%2$,.3f ms", traceName, cost));
		}
	}

	public static void traceTime(String traceName, int times, long s) {
		if(times <= 1) {
			traceTime(traceName, s);
		} else {
			if (TRACE && LOG.isDebugEnabled()) {
				double cost = (System.nanoTime() - s) / MILLION;
				double p = cost / times;
				LOG.debug(String.format("%s (%d times) 总耗时：%3$,.3f ms ,单次耗时 %4$,.6f ms", traceName,
						times, cost, p));
			}
		}
		
	}
	
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// ignore 
		}
	}
}
