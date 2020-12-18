package com.nhdc.autotest.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Properties;

@Slf4j
public class ProUtil {

	public Properties prop;
	public String filePath_now;
	
	/**
	  * 构造方法，初始化就传入文件路径，并加载文件
	 * */
	public ProUtil(String filePath) {
		this.filePath_now = filePath;
		this.prop = readProperties();
	}
	
	public ProUtil() {
	}
	
	/**
	  * 读取配置文件
	 * */
	private Properties readProperties() {
		
		Properties properties = new Properties();
		
		InputStream inputStream = null;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(filePath_now));
			properties.load(new InputStreamReader(inputStream,"UTF-8"));
			
//			InputStream inputStream = new FileInputStream(filePath_now);
//			BufferedInputStream input = new BufferedInputStream(inputStream);
//			properties.load(input);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
		
	}

	/**
	  * 获取内容，getProperty
	 * */
	public String getPro(String key) {
		
		if (prop.containsKey(key)) {
			String value = prop.getProperty(key);
			return value;
		}else {
			log.error("获取KEY值[ {}]不正确",key);
//			System.out.println("获取KEY值不正确");
			return "";
		}
	}
	
	/**
         * 写入内容，setProperty
     * */
    public void writePro(String key,String value){
        Properties pro = new Properties();
            try {
                FileOutputStream file = new FileOutputStream(filePath_now);
                pro.setProperty(key, value);
                pro.store(file, key);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
}
