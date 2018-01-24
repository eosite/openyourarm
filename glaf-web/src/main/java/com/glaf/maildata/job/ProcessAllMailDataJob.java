package com.glaf.maildata.job;

import java.util.concurrent.atomic.AtomicLong;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.core.job.BaseJob;
import com.glaf.ws.client.ProcessMailDataWebService;
import com.glaf.ws.client.ProcessMailDataWebServiceService;

public class ProcessAllMailDataJob extends BaseJob{
	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());
	@Override
	public void runJob(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		// 发送流程邮件 数据
		ProcessMailDataWebServiceService processMailDataWebServiceService = new ProcessMailDataWebServiceService();
		ProcessMailDataWebService sendProcessMailDataWebService = processMailDataWebServiceService
				.getProcessMailDataWebServicePort();
		sendProcessMailDataWebService.processAll();
		lastExecuteTime.set(System.currentTimeMillis());
	}
}
