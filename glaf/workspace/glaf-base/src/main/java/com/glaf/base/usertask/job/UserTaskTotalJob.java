package com.glaf.base.usertask.job;

import java.util.concurrent.atomic.AtomicLong;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.base.usertask.bean.UserTaskTotalBean;
import com.glaf.core.job.BaseJob;
import com.glaf.core.util.DateUtils;

public class UserTaskTotalJob extends BaseJob {

	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());

	@Override
	public void runJob(JobExecutionContext context) throws JobExecutionException {
		if ((System.currentTimeMillis() - lastExecuteTime.get()) < DateUtils.MINUTE * 5) {// 5分钟
			return;
		}
		UserTaskTotalBean bean = new UserTaskTotalBean();
		bean.countTask();
		lastExecuteTime.set(System.currentTimeMillis());
	}

}
