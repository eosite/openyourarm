package com.glaf.mqdata.web.dubbo.server;

public interface SendDataService {
	 byte[] sendMsgtoSys(String sysId, byte[] msg);
}
