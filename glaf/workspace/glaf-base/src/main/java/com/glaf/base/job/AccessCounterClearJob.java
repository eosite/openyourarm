package com.glaf.base.job;

import java.util.concurrent.atomic.AtomicLong;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.base.utils.ContextUtil;
import com.glaf.core.job.BaseJob;
import com.glaf.core.util.DateUtils;

public class AccessCounterClearJob extends BaseJob {

	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());

	@Override
	public void runJob(JobExecutionContext context) throws JobExecutionException {
		if ((System.currentTimeMillis() - lastExecuteTime.get()) < DateUtils.MINUTE) {
			return;
		}
		ContextUtil.clear();
		lastExecuteTime.set(System.currentTimeMillis());
	}

}
