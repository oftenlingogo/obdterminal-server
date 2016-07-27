package com.casic.server;

import com.casic.entity.Message;
import com.casic.entity.OBDDataChanged;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class OBDIoHandler extends IoHandlerAdapter {
	private static Logger logger = Logger.getLogger(OBDIoHandler.class);

	public void sessionOpened(IoSession session) throws Exception {
	}

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		Message data = (Message) message;

		@SuppressWarnings("unchecked")
		ArrayList<OBDDataChanged[]> ls = (ArrayList<OBDDataChanged[]>) data.getBody();
		for (OBDDataChanged[] p : ls) {
			OBDDataQueue queue = OBDDataQueue.getInstance();
			queue.add(p);
		}
	}

	public void sessionCreated(IoSession session) throws Exception {
		logger.info("++++新链接到来++++!");
		session.setAttribute("RcvCount", Integer.valueOf(0));
		session.setAttribute("DecodeCount", Integer.valueOf(0));
		session.setAttribute("DecodeCount", Integer.valueOf(0));
		session.setAttribute("HistoryData", new byte[0]);
		session.setAttribute("terminalId", "******");
		super.sessionCreated(session);
	}

	public void sessionClosed(IoSession session) throws Exception {
		String terminalId = session.getAttribute("terminalId").toString();
		logger.info("----终端进入 " + terminalId.toString() + "空闲状态，即将关闭连接----");
		super.sessionClosed(session);
	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		String terminalId = session.getAttribute("terminalId").toString();
		logger.info("----关闭了终端：" + terminalId.toString() + " 的连接----");
		session.close(false);
		super.sessionIdle(session, status);
	}
}