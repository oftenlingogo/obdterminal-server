package com.casic.server;

import com.casic.entity.BDData;
import com.casic.entity.Message;
import com.casic.entity.OBDDataChanged;
import com.casic.entity.ObdPerSecondData;
import com.casic.entity.ObdTenSecondData;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class OBDDataDecoderCZPASATImpl extends CumulativeProtocolDecoder {
	private static Logger logger = Logger.getLogger(OBDDataDecoderCZPASATImpl.class);
	private static Logger logger2 = Logger.getLogger("OBDDataDecoder");
	private static final int PACKAGE_LENGTH = 490;
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		int rcvCnt = ((Integer) session.getAttribute("RcvCount")).intValue();
		int decCnt = ((Integer) session.getAttribute("DecodeCount")).intValue();

		if (rcvCnt - decCnt > 10) {
			session.close(false);
			return false;
		}
		rcvCnt++;
		session.setAttribute("RcvCount", Integer.valueOf(rcvCnt));

		byte[] data = new byte[in.limit()];
		in.get(data);
		logger.info("++++成功接收到第" + rcvCnt + "条数据，长度为:" + data.length + " ++++");

		byte[] historydata = (byte[]) session.getAttribute("HistoryData");

		data = byteMerger(new byte[][] { historydata, data });
		if (historydata.length != 0) {
			logger.info("+++成功拼接数据包++++!");
		}
		byte[] decodeData = new byte[PACKAGE_LENGTH];
		List<byte[]> ls = new ArrayList<byte[]>();
		while (data.length / PACKAGE_LENGTH > 0) {
			decodeData = byteSplit(data, 0, PACKAGE_LENGTH);
			ls.add(decodeData);
			data = byteSplit(data, PACKAGE_LENGTH, data.length - PACKAGE_LENGTH);
		}

		if (data.length == 0)
			session.setAttribute("HistoryData", new byte[0]);
		else if (data.length > 0) {
			session.setAttribute("HistoryData", data);
		}
		List<OBDDataChanged[]> lsData = new ArrayList<OBDDataChanged[]>();
		Message msg = new Message();
		for (byte[] d : ls) {
			OBDDataChanged[] r = decodeObdData(session, d);
			if (r != null)
				lsData.add(r);
		}
		msg.setBody(lsData);
		out.write(msg);
		return true;
	}

	private OBDDataChanged[] decodeObdData(IoSession session, byte[] data) {
		OBDDataChanged[] packetChanged = new OBDDataChanged[30];
		byte[] c = new byte[4];
		c = byteSplit(data, 0, 4);

		String header = new String(c);
		if ((header.equals("flsh")) || (header.equals("scan"))) {
			try {
				c = byteSplit(data, 4, 6);

				String terminalId = new String(c);
				session.setAttribute("terminalId", terminalId);
				c = byteSplit(data, 10, 12);

				SimpleDateFormat formatTime = new SimpleDateFormat("HHmmssddMMyy");
				String timeString = new String(c);

				ObdTenSecondData[] obd10data = new ObdTenSecondData[3];
				for (int i = 0; i < 3; i++) {
					obd10data[i] = decodeTenSecondOBDData2(data, i);
				}

				ObdPerSecondData[] obd1data = new ObdPerSecondData[30];
				for (int k = 0; k < 30; k++) {
					obd1data[k] = decodePerSecondData2(data, k);
				}

				BDData[] bddata = new BDData[15];
				for (int j = 0; j < 15; j++) {
					bddata[j] = decodeBDData2(data, j);
				}

				for (int i = 0; i < 30; i++) {
					packetChanged[i] = new OBDDataChanged();

					packetChanged[i].setIMEI(terminalId);
					Date date = formatTime.parse(timeString);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(10, 8);
					calendar.add(13, i);
					packetChanged[i].setGPS_TIME(calendar.getTime());

					packetChanged[i].setBdoneNo_after_mileage(obd10data[(i / 10)].getMileageAfterBreak());
					packetChanged[i].setBdoneNo_zero_mileage(obd10data[(i / 10)].getMileageBeforeBreak());
					packetChanged[i].setFront_oxygen_sensor(obd10data[(i / 10)].getFrontOxgenSensorVal());
					packetChanged[i].setAfter_oxygen_sensor(obd10data[(i / 10)].getBackOxgenSensorVal());
					packetChanged[i].setAir_condion_state(obd10data[(i / 10)].getAirConditionerStatus());
					packetChanged[i].setTotal_fuel(obd10data[(i / 10)].getOilVal());

					packetChanged[i].setInstant_fuel(obd1data[i].getFuelValue());
					packetChanged[i].setDb_speed(obd1data[i].getObdspeed());
					packetChanged[i].setSpeed(obd1data[i].getRspeed());
					packetChanged[i].setTorque(obd1data[i].getTorque());

					if (i % 2 == 1) {
						packetChanged[i].setGPS_STATE(bddata[(i / 2)].getBdStatus());
						packetChanged[i].setGPS_LONG(bddata[(i / 2)].getLongitude());
						packetChanged[i].setGPS_LAT(bddata[(i / 2)].getLatitude());
						packetChanged[i].setGPS_SPEED(bddata[(i / 2)].getBdspeed());
						packetChanged[i].setDirection_angle(bddata[(i / 2)].getBearing());
					} else if (i == 0) {
						packetChanged[i].setGPS_STATE(bddata[0].getBdStatus());
						packetChanged[i].setGPS_LONG(2.0D * bddata[0].getLongitude() - bddata[1].getLongitude());
						packetChanged[i].setGPS_LAT(2.0D * bddata[0].getLatitude() - bddata[1].getLatitude());
						packetChanged[i].setGPS_SPEED(2.0D * bddata[0].getBdspeed() - bddata[1].getBdspeed());
						packetChanged[i].setDirection_angle(2.0D * bddata[0].getBearing() - bddata[1].getBearing());
					} else {
						packetChanged[i].setGPS_STATE(bddata[((i - 1) / 2)].getBdStatus());
						packetChanged[i].setGPS_LONG(
								(bddata[((i - 1) / 2)].getLongitude() + bddata[((i + 1) / 2)].getLongitude()) / 2.0D);
						packetChanged[i].setGPS_LAT(
								(bddata[((i - 1) / 2)].getLatitude() + bddata[((i + 1) / 2)].getLatitude()) / 2.0D);
						packetChanged[i].setGPS_SPEED(
								(bddata[((i - 1) / 2)].getBdspeed() + bddata[((i + 1) / 2)].getBdspeed()) / 2.0D);
						packetChanged[i].setDirection_angle(
								(bddata[((i - 1) / 2)].getBearing() + bddata[((i + 1) / 2)].getBearing()) / 2.0D);
					}

				}

				int cnt = ((Integer) session.getAttribute("DecodeCount")).intValue();
				cnt++;
				session.setAttribute("DecodeCount", Integer.valueOf(cnt));
				logger2.info(" ++++ 成功解析了第 " + cnt + " 条数据，终端编号：" + terminalId + " ++++");
			} catch (Exception ex) {
				String terminalId = session.getAttribute("terminalId").toString();
				logger2.info(" ---- 无法解析正常数据包（包头正确），终端编号：" + terminalId + " ----");
				logger2.info("数据包原始数据：" + bytes2HexString(data));
				packetChanged = (OBDDataChanged[]) null;
			}
		} else {
			logger2.info(" ---- 无法解析异常数据包（包头错误）----");
			logger2.info(bytes2HexString(data));
			packetChanged = (OBDDataChanged[]) null;
		}
		return packetChanged;
	}

	private ObdTenSecondData decodeTenSecondOBDData2(byte[] data, int num) {
		ObdTenSecondData obddata = new ObdTenSecondData();
		byte[] d = (byte[]) null;
		d = byteSplit(data, 22 + 11 * num, 4);
		obddata.setMileageBeforeBreak(byteArrayToInt(d, 4));
		d = byteSplit(data, 22 + 11 * num + 4, 1);
		obddata.setFrontOxgenSensorVal(byteArrayToInt(d, 1));
		d = byteSplit(data, 22 + 11 * num + 5, 1);
		obddata.setBackOxgenSensorVal(byteArrayToInt(d, 1));
		d = byteSplit(data, 22 + 11 * num + 6, 1);
		obddata.setAirConditionerStatus(byteArrayToInt(d, 1) - 40);
		d = byteSplit(data, 22 + 11 * num + 7, 4);
		obddata.setOilVal(byteArrayToInt(d, 4));
		return obddata;
	}

	private BDData decodeBDData2(byte[] data, int num) {
		byte[] d = (byte[]) null;
		BDData bddata = new BDData();
		d = byteSplit(data, 235 + 17 * num, 1);
		bddata.setBdStatus(new String(d));

		d = byteSplit(data, 235 + 17 * num + 1, 2);
		int lat1 = byteArrayToInt(d, 2);
		d = byteSplit(data, 235 + 17 * num + 3, 2);
		int lat2 = byteArrayToInt(d, 2);

		double lat = (lat1 *10000+lat2)/1000000.0D;
		DecimalFormat df = new DecimalFormat("0.000000 ");
		bddata.setLatitude(Double.valueOf(df.format(lat)).doubleValue());

		d = byteSplit(data, 235 + 17 * num + 5, 2);
		int long1 = byteArrayToInt(d, 2);
		d = byteSplit(data, 235 + 17 * num + 7, 2);
		int long2 = byteArrayToInt(d, 2);

		//double ln = long1 / 100 + (long1 % 100.0D + long2 / 10000.0D) / 60.0D;
		double ln = (long1 *10000+long2)/1000000.0D;
		bddata.setLongitude(Double.valueOf(df.format(ln)).doubleValue());

		d = byteSplit(data, 235 + 17 * num + 9, 2);
		int speed1 = byteArrayToInt(d, 2);
		d = byteSplit(data, 235 + 17 * num + 11, 2);
		int speed2 = byteArrayToInt(d, 2);
		bddata.setBdspeed((speed1 + speed2 / 10.0D) * 1.852D);

		d = byteSplit(data, 235 + 17 * num + 13, 2);
		int bear1 = byteArrayToInt(d, 2);
		d = byteSplit(data, 235 + 17 * num + 15, 2);
		int bear2 = byteArrayToInt(d, 2);
		bddata.setBearing(bear1 + bear2 / 1000.0D);

		return bddata;
	}

	private ObdPerSecondData decodePerSecondData2(byte[] data, int num) {
		ObdPerSecondData obddata = new ObdPerSecondData();
		byte[] d = (byte[]) null;
		d = byteSplit(data, 55 + 6 * num, 2);
		obddata.setFuelValue(byteArrayToInt(d, 2));
		d = byteSplit(data, 55 + 6 * num + 2, 1);
		obddata.setObdspeed(byteArrayToInt(d, 1));
		d = byteSplit(data, 55 + 6 * num + 3, 2);
		obddata.setRspeed(byteArrayToInt(d, 2) / 4);
		d = byteSplit(data, 55 + 6 * num + 5, 1);
		obddata.setTorque(byteArrayToInt(d, 1) * 100 / 255);
		return obddata;
	}

	public byte[] byteSplit(byte[] source, int start, int lengthOf) {
		byte[] value = new byte[lengthOf];
		System.arraycopy(source, start, value, 0, lengthOf);
		return value;
	}

	public byte[] byteMerger(byte[][] params) {
		int desLength = 0;
		for (int i = 0; i < params.length; i++) {
			desLength += params[i].length;
		}
		byte[] byte_3 = new byte[desLength];
		desLength = 0;
		for (int i = 0; i < params.length; i++) {
			System.arraycopy(params[i], 0, byte_3, desLength, params[i].length);
			desLength += params[i].length;
		}
		return byte_3;
	}

	public int byteArrayToInt(byte[] b, int num) {
		int value = 0;
		for (int i = 0; i < num; i++) {
			int shift = i * 8;
			value += ((b[i] & 0xFF) << shift);
		}
		return value;
	}

	public String bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret = ret + hex.toUpperCase();
		}
		return ret;
	}
}