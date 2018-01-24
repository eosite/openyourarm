package com.glaf.mqdata.job;

import java.util.concurrent.atomic.AtomicLong;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.job.BaseJob;
import com.glaf.mqdata.web.dubbo.client.MQClientService;

public class SysMQReceiveJob extends BaseJob{
	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());
	@Override
	public void runJob(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		MQClientService mqClientService = ContextFactory.getBean("com.glaf.mqdata.web.dubbo.client.mqClientService");
		mqClientService.receiveData();
		lastExecuteTime.set(System.currentTimeMillis());
	}

}
