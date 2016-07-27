package com.casic.server;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimerTask;
import org.apache.log4j.Logger;

import com.casic.utils.SystemConstants;

public class CreatFilepathDailyThread extends TimerTask {
	private final static Logger LOGGER = Logger.getLogger(OBDServerApp.class);
	public void run() {
		String filePath =SystemConstants.RT_DATAPATH;
		String filePathHis = SystemConstants.HIS_DATAPATH;
		SimpleDateFormat fT = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat fT2 = new SimpleDateFormat("yyyyMMddHH");
		SimpleDateFormat fT3 = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar calendar = Calendar.getInstance();
		Calendar calendar1 = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar1.setTime(new Date());

		calendar.add(5, 1);
		calendar1.add(5, 2);

		Calendar tommorowDate = new GregorianCalendar(calendar.get(1), calendar.get(2), calendar.get(5), 0, 0, 0);

		Date datein = tommorowDate.getTime();

		for (int i = 0; i <= 2880; i++) {
			datein = tommorowDate.getTime();
			String filePathIn = filePath + "/" + fT.format(datein) + "/" + fT2.format(datein) + "/"
					+ fT3.format(datein);
			String filePathInHis = filePathHis + "/" + fT.format(datein) + "/" + fT2.format(datein) + "/"
					+ fT3.format(datein);

			tommorowDate.add(13, 30);
			File myFilePath = new File(filePathIn);
			File myFilePathHis = new File(filePathInHis);
			if (!myFilePath.exists()) {
				myFilePath.mkdirs();
			}

			if (!myFilePathHis.exists()) {
				myFilePathHis.mkdirs();
			}
		}

		LOGGER.info("++++ 成功创建完成今日文件夹 ++++");
	}

	
}