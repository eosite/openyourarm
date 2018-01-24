package com.glaf.matrix.combination.job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.Database;
import com.glaf.core.job.BaseJob;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DateUtils;

import com.glaf.datamgr.util.ExecutionUtils;
import com.glaf.matrix.combination.domain.SqlToTableApp;
import com.glaf.matrix.combination.domain.SqlToTableHistory;
import com.glaf.matrix.combination.query.SqlToTableAppQuery;
import com.glaf.matrix.combination.service.SqlToTableAppService;
import com.glaf.matrix.combination.service.SqlToTableHistoryService;
import com.glaf.matrix.combination.task.SqlToTableAppTask;
import com.glaf.matrix.util.SysParams;

public class SqlToTableAppJob extends BaseJob {

	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());

	@Override
	public void runJob(JobExecutionContext context) throws JobExecutionException {
		logger.debug("-------------------------SqlToTableAppJob-----------------------");
		if ((System.currentTimeMillis() - lastExecuteTime.get()) < DateUtils.MINUTE) {
			return;
		}
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		SqlToTableAppService sqlToTableAppService = ContextFactory
				.getBean("com.glaf.matrix.combination.service.sqlToTableAppService");
		SqlToTableHistoryService sqlToTableHistoryService = ContextFactory
				.getBean("com.glaf.matrix.combination.service.sqlToTableHistoryService");
		SqlToTableAppQuery query = new SqlToTableAppQuery();
		query.autoSyncFlag("Y");
		query.active("Y");

		List<SqlToTableApp> rows = sqlToTableAppService.list(query);
		if (rows != null && !rows.isEmpty()) {
			Map<String, Object> parameter = new HashMap<String, Object>();
			SysParams.putInternalParams(parameter);
			SqlToTableAppTask task = null;
			Database database = null;
			String jobNo = null;
			for (SqlToTableApp app : rows) {
				long ts = System.currentTimeMillis();
				try {
					SqlToTableHistory last = sqlToTableHistoryService.getLatestSqlToTableHistory(app.getId(),
							app.getTargetDatabaseId());
					if (last != null && last.getStatus() == 1) {
						/**
						 * 判断是否满足时间间隔
						 */
						if ((ts - last.getCreateTime().getTime()) < app.getInterval() * 60 * 1000) {
							continue;
						}
					}
					long start = System.currentTimeMillis();
					database = databaseService.getDatabaseById(app.getTargetDatabaseId());
					if (database != null) {
						jobNo = "sqltotable_app_" + app.getId() + "_" + app.getTargetDatabaseId() + "_"
								+ DateUtils.getNowYearMonthDayHHmmss();
						ExecutionUtils.put(jobNo, new Date());

						task = new SqlToTableAppTask(app.getId(), jobNo, parameter);

						SqlToTableHistory his = new SqlToTableHistory();
						his.setCreateBy("system");
						his.setDatabaseId(app.getTargetDatabaseId());
						his.setDeploymentId(app.getDeploymentId());
						his.setSyncId(app.getId());
						if (!task.execute()) {
							his.setStatus(-1);
						} else {
							his.setStatus(1);
						}
						his.setTotalTime((int) (System.currentTimeMillis() - start));
						his.setDatabaseName(database.getTitle() + "[" + database.getHost() + "]");
						sqlToTableHistoryService.save(his);
					}
				} catch (Exception ex) {
					logger.error(ex);
				} finally {
					ExecutionUtils.remove(jobNo);
				}
			}
		}

		lastExecuteTime.set(System.currentTimeMillis());
	}

}
