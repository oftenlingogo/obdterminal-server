package com.casic.server;

import com.casic.common.JsonDateValueProcessor;
import com.casic.entity.OBDDataChanged;
import com.casic.utils.SystemConstants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.log4j.Logger;

public class SaveDataThreadNew implements Runnable {
	private OBDDataQueue queue;
	private File file = null;
	private String path = null;
	private String filePath = null;
	private String pathHis = null;
	private String pathIn = null;

	private static Logger logger = Logger.getLogger(SaveDataThreadNew.class);

	public SaveDataThreadNew(OBDDataQueue queue) {


		this.path = SystemConstants.RT_DATAPATH;
		this.pathHis = SystemConstants.HIS_DATAPATH;
		this.queue = queue;
	}

	public void run() {
		for (int i = 0; i < this.queue.getLength(); i++) {
			OBDDataChanged[] data = this.queue.get();
			if (savefile(data).booleanValue())
				logger.info("++++ 成功存储数据 ++++");
			else {
				logger.info("---- 存储数据失败 ----");
			}
		}
	}

	@SuppressWarnings("deprecation")
	private Boolean savefile(OBDDataChanged[] data) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());

		SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat fT = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat fT1 = new SimpleDateFormat("yyyyMMddHH");
		SimpleDateFormat fT2 = new SimpleDateFormat("yyyyMMddHHmm");

		if (new Date().getTime() - data[0].getGPS_TIME().getTime() < 300000L)
			this.filePath = this.path;
		else {
			this.filePath = this.pathHis;
		}
		if (data[0].getGPS_TIME().getSeconds() < 30) {
			this.pathIn = (this.filePath + "/" + fT.format(data[0].getGPS_TIME()) + "/"
					+ fT1.format(data[0].getGPS_TIME()) + "/" + fT2.format(data[0].getGPS_TIME()) + "00/");
			
		} else {
			this.pathIn = (this.filePath + "/" + fT.format(data[0].getGPS_TIME()) + "/"
					+ fT1.format(data[0].getGPS_TIME()) + "/" + fT2.format(data[0].getGPS_TIME()) + "30/");
			
		}

		String filename = this.pathIn + data[0].getIMEI() + "_" + formatDate.format(data[0].getGPS_TIME()) + ".txt";
		this.file = new File(filename);
		if (!this.file.exists()) {
			try {
				this.file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return Boolean.valueOf(false);
			}
		}

		try {
			FileWriter writer = new FileWriter(filename, false);
			String strdata = "";
			for (int i = 0; i < data.length; i++) {
				JSONObject jsonObject = JSONObject.fromObject(data[i], jsonConfig);
				strdata = strdata + jsonObject.toString() + "\r\n";
			}

			writer.write(strdata);
			writer.close();
			return Boolean.valueOf(true);
		} catch (IOException e) {
		}
		return Boolean.valueOf(false);
	}

	
}