package com.casic.utils;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.InitializingBean;

public class SystemConstants implements InitializingBean {
	public static String RT_DATAPATH = "";
	public static String HIS_DATAPATH = "";
	public static String PORT = "";

	public void afterPropertiesSet() throws Exception {
		initLog4jProperties();
		Properties properties = new Properties();
		InputStream propsFile = getClass().getClassLoader().getResourceAsStream("conf/config.properties");
		properties.load(propsFile);
		propsFile.close();
		RT_DATAPATH = properties.getProperty("rt-datapath");
		HIS_DATAPATH = properties.getProperty("his-datapath");
		PORT = properties.getProperty("port");
	}

	private void initLog4jProperties() {
		// 未打包时读取配置
		String file = this.getClass().getClassLoader().getResource("conf/log4j.properties").getFile();
		if (new java.io.File(file).exists()) {
			PropertyConfigurator.configure(file);
			return;
		}

	}
}