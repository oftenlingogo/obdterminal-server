package com.casic.utils;

import java.io.InputStream;
import java.util.Properties;
import org.springframework.beans.factory.InitializingBean;

public class SystemConstants implements InitializingBean {
	public static String RT_DATAPATH = "";
	public static String HIS_DATAPATH = "";
	public static String PORT = "";

	public void afterPropertiesSet() throws Exception {
		Properties properties = new Properties();
		InputStream propsFile = getClass().getClassLoader()
				.getResourceAsStream("conf/config.properties");
		properties.load(propsFile);
		propsFile.close();
		RT_DATAPATH = properties.getProperty("rt-datapath");
		HIS_DATAPATH = properties.getProperty("his-datapath");
		PORT =  properties.getProperty("port");
	}
}