package com.glaf.base.job;

import java.util.concurrent.atomic.AtomicLong;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.base.config.BaseConfiguration;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.core.config.Configuration;
import com.glaf.core.job.BaseJob;
import com.glaf.core.util.DateUtils;

public class BaseDataRefreshJob extends BaseJob {

	private static BaseDataManager manager = BaseDataManager.getInstance();// 基础信息管理

	protected static final Configuration conf = BaseConfiguration.create();

	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());

	@Override
	public void runJob(JobExecutionContext context) throws JobExecutionException {
		if ((System.currentTimeMillis() - lastExecuteTime.get()) < DateUtils.MINUTE * 5) {
			return;
		}
		if (conf.getBoolean("base_data_refresh_auto", true)) {
			manager.refreshBaseData();
		}
	}

}
