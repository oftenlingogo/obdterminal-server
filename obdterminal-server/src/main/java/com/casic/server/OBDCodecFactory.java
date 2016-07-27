package com.casic.server;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class OBDCodecFactory implements ProtocolCodecFactory {
	private ProtocolDecoder decoder;

	public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
		return this.decoder;
	}
	
	

	public ProtocolDecoder getDecoder() {
		return decoder;
	}



	public void setDecoder(ProtocolDecoder decoder) {
		this.decoder = decoder;
	}



	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		return null;
	}
}