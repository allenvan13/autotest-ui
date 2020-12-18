package com.nhdc.autotest.base;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SelectDriver {

	public WebDriver driverName(String browser) {

		//后期再添加其他几种浏览器驱动
		if(browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "D:\\Program Files\\Java\\webdriver\\chromedriver.exe");
			return new ChromeDriver();
		}else if(browser.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver", "D:\\Program Files\\Java\\webdriver\\IEDriverServer.exe");
			return new InternetExplorerDriver();
		}else if(browser.equalsIgnoreCase("Firefox")) {
			System.setProperty("webdriver.gecko.driver", "D:\\Program Files\\Java\\webdriver\\geckodriver.exe");
			return new FirefoxDriver();
		}else if(browser.equalsIgnoreCase("AppChrome")){
			//声明ChromeOptions，主要是给chrome设置参数
		    ChromeOptions options = new ChromeOptions();
		    Map<String, String> mobileEmulation = new HashMap<>();
		    
//		    mobileEmulation.put("deviceName", "iPhone 4");
//		    mobileEmulation.put("deviceName", "iPhone 6");
		    mobileEmulation.put("deviceName", "iPhone X");
		    options.setExperimentalOption("useAutomationExtension", false);
	        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
		    options.setExperimentalOption("mobileEmulation", mobileEmulation);
		    //设置驱动位置
		    System.setProperty("webdriver.chrome.driver", "D:\\Program Files\\Java\\webdriver\\chromedriver.exe");
			return new ChromeDriver(options);
		}else {
			log.error("没有这个浏览器");
			return null;
		}
	}
}

