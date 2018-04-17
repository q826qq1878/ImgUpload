
/**
 * Copyright(c) 2000-2013 HC360.COM, All Rights Reserved.
 * Project: transaction 
 * Author: dixingxing
 * Version: 1.0
 */

package com.jjc.imgup.common.test;

import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class FunctionalTests extends SpringTests{
	protected static WebDriver driver;
	
	@BeforeClass
	public static void beforeClass() {
		Jettys.start();
		driver = new HtmlUnitDriver();
	}
}
