package com.glaf.datamgr.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.core.job.BaseJob;
import com.glaf.datamgr.jdbc.DataSetBuilder;

public class ClearDataSetCacheJob extends BaseJob {

	public void runJob(JobExecutionContext context) throws JobExecutionException {
		logger.debug("================清理数据集缓存--start===============");
		DataSetBuilder.clearAllCache();
		logger.debug("================清理数据集缓存--end===============");
	}

}
