package com.casic.server;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.casic.utils.SystemConstants;

public class OBDServerApp {

	private final static Logger LOGGER = Logger.getLogger(OBDServerApp.class);

	public static void main(String[] args) throws IOException {
		OBDDataQueue queue = OBDDataQueue.getInstance();
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext ct = new ClassPathXmlApplicationContext("conf/applicationContext-mina.xml");
		
		LOGGER.debug(ct.toString());
		LOGGER.info("Server started,Listening Port : "+SystemConstants.PORT);

		new CreatFilepathOnce().creat();

		Thread thread = new Thread(new SaveDataThreadNew(queue));
		thread.start();

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Calendar creatTime = new GregorianCalendar(cal.get(1), cal.get(2), cal.get(5), 23, 30, 0);

		Timer timer = new Timer();
		timer.schedule(new CreatFilepathDailyThread(), creatTime.getTime(), 86400000L);

	}
}