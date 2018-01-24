package com.glaf.teim.job;

import java.util.concurrent.atomic.AtomicLong;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.job.BaseJob;
import com.glaf.teim.service.EimServerSyncService;

public class DataImpJob  extends BaseJob{
	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());
	@Override
	public void runJob(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		EimServerSyncService eimServerSyncService = ContextFactory.getBean("com.glaf.teim.service.eimServerSyncService");
	    //获取任务定义ID
		JobDataMap jobDataMap=context.getJobDetail().getJobDataMap();
		String taskId=jobDataMap.getString("taskId");
		try {
			eimServerSyncService.sysncDataByTaskId(taskId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
	}
}
