package com.glaf.maildata.job;

import java.util.concurrent.atomic.AtomicLong;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.job.BaseJob;
import com.glaf.workflow.core.service.StartImpProcessService;
/**
 * 根据接口流程数据启动本地流程
 * @author Dane
 *
 */
public class StartLocalWorkFlowJob extends BaseJob{
	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());
	@Override
	public void runJob(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		StartImpProcessService startImpProcessService = ContextFactory.getBean("com.glaf.workflow.core.service.StartImpProcessService");
		startImpProcessService.startProcesses();
		lastExecuteTime.set(System.currentTimeMillis());
	}

}
