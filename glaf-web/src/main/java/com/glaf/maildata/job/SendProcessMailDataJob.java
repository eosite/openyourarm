package com.glaf.maildata.job;

import java.util.concurrent.atomic.AtomicLong;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.core.job.BaseJob;
import com.glaf.ws.client.SendProcessMailDataWebService;
import com.glaf.ws.client.SendProcessMailDataWebServiceService;


public class SendProcessMailDataJob extends BaseJob{
	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());
	@Override
	public void runJob(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		// 发送流程邮件 数据
		SendProcessMailDataWebServiceService sendProcessMailDataWebServiceService = new SendProcessMailDataWebServiceService();
		SendProcessMailDataWebService sendProcessMailDataWebService = sendProcessMailDataWebServiceService
				.getSendProcessMailDataWebServicePort();
		sendProcessMailDataWebService.sendAll();
		lastExecuteTime.set(System.currentTimeMillis());
	}
}
