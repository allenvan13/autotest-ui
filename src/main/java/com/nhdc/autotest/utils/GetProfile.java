package com.nhdc.autotest.utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

@Slf4j
public class GetProfile {

	private static final String TEST_ELEMENTS_PATH = System.getProperty("user.dir")+"\\src\\main\\resources\\properties\\WebElements.properties";
	private static final String ENVIRRONMENT_PATH = System.getProperty("user.dir")+"\\src\\main\\resources\\properties\\Environment.properties";

	/**
	 * 获取环境配置（目前是URL）
	 * @param key
	 * @return
	 */
	public static String getEnvirConfig(String key) {
		ProUtil proper = new ProUtil(ENVIRRONMENT_PATH);
		if (proper.getPro(key) != null) {
			return proper.getPro(key);
		}else {
			log.error("获取[{}]失败",key);
			return null;
		}
	}

	/**
	 * 获取By，用于定位元素
	 * @param key
	 * @return
	 */
	public static By getBy(String key) {
		ProUtil proper = new ProUtil(TEST_ELEMENTS_PATH);

		String locator = proper.getPro(key);
		String locatorType = locator.split(">")[0]; //按>拆分，第1个值
		String locatorTypeValue = locator.split(">")[1]; //按>拆分，第2个值
		
		switch (locatorType) {
			case "id":
				return By.id(locatorTypeValue);
			case "name":
				return By.name(locatorTypeValue);
			case "className":
				return By.className(locatorTypeValue);
			case "cssSelector":
				return By.cssSelector(locatorTypeValue);
			case "tagName":
				return By.tagName(locatorTypeValue);
			case "linkText":
				return By.linkText(locatorTypeValue);
			case "partialLinkText":
				return By.partialLinkText(locatorTypeValue);	
			case "xpath":
				return By.xpath(locatorTypeValue);
			default:
				return null;
		}
		
	}
}
