package com.glaf.mqdata.web.dubbo.server;

public interface ReceiveDataService {
	byte[] receiveMsg(String sysId, boolean receivedFlag);
}
