package com.glaf.mqdata.job;

import java.util.concurrent.atomic.AtomicLong;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.job.BaseJob;
import com.glaf.mqdata.service.MQManagerService;

public class CreateMQConnectionPropJob extends BaseJob{
	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());
	@Override
	public void runJob(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		MQManagerService mqManagerService = ContextFactory.getBean("com.glaf.mqdata.service.mqManagerServiceImpl");
		mqManagerService.createMQConnectionProperties();
		lastExecuteTime.set(System.currentTimeMillis());
	}
}
