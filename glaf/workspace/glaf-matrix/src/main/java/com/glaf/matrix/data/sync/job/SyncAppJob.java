package com.glaf.matrix.data.sync.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.Database;
import com.glaf.core.job.BaseJob;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.StringTools;

import com.glaf.datamgr.util.ExecutionUtils;
import com.glaf.matrix.data.sync.domain.SyncApp;
import com.glaf.matrix.data.sync.domain.SyncHistory;
import com.glaf.matrix.data.sync.query.SyncAppQuery;
import com.glaf.matrix.data.sync.service.SyncAppService;
import com.glaf.matrix.data.sync.service.SyncHistoryService;
import com.glaf.matrix.data.sync.task.SyncAppTask;
import com.glaf.matrix.util.SysParams;

public class SyncAppJob extends BaseJob {

	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());

	public static String getNowYearMonthDayHH() {
		String returnStr = null;
		System.currentTimeMillis();
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHH");
		Date date = new Date();
		returnStr = f.format(date);
		return returnStr;
	}

	@Override
	public void runJob(JobExecutionContext context) throws JobExecutionException {
		logger.debug("--------------------------SyncAppJob--------------------------");
		if ((System.currentTimeMillis() - lastExecuteTime.get()) < DateUtils.MINUTE * 2) {
			return;
		}
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		SyncAppService syncAppService = ContextFactory.getBean("com.glaf.matrix.data.sync.service.syncAppService");
		SyncHistoryService syncHistoryService = ContextFactory
				.getBean("com.glaf.matrix.data.sync.service.syncHistoryService");
		SyncAppQuery query = new SyncAppQuery();
		query.autoSyncFlag("Y");
		query.active("Y");

		List<SyncApp> rows = syncAppService.list(query);
		if (rows != null && !rows.isEmpty()) {
			Map<String, Object> parameter = new HashMap<String, Object>();
			SysParams.putInternalParams(parameter);
			String runDay = DateUtils.getNowYearMonthDayHour();
			SyncAppTask task = null;
			Database database = null;
			String jobNo = null;
			for (SyncApp app : rows) {
				if (StringUtils.isNotEmpty(app.getTargetDatabaseIds())) {
					List<Long> targetDatabaseIds = StringTools.splitToLong(app.getTargetDatabaseIds(), ",");
					for (long targetDatabaseId : targetDatabaseIds) {
						jobNo = "sync_app_" + app.getId() + "_" + targetDatabaseId + "_" + runDay;
						long ts = System.currentTimeMillis();
						try {
							/**
							 * 判断是否可以执行
							 */
							SyncHistory last = syncHistoryService.getLatestSyncHistory(app.getId(), targetDatabaseId);
							if (last != null && last.getStatus() == 1) {
								/**
								 * 判断是否满足时间间隔
								 */
								if ((ts - last.getCreateTime().getTime()) < app.getInterval() * 60 * 1000) {
									continue;
								}
							}
							database = databaseService.getDatabaseById(targetDatabaseId);
							if (database != null) {
								task = new SyncAppTask(app.getSrcDatabaseId(), targetDatabaseId, app.getId(), jobNo,
										parameter);
								ExecutionUtils.put(jobNo, new Date());
								SyncHistory his = new SyncHistory();
								his.setCreateBy("system");
								his.setDatabaseId(targetDatabaseId);
								his.setDeploymentId(app.getDeploymentId());
								his.setSyncId(app.getId());

								if (!task.execute()) {
									his.setStatus(-1);
								} else {
									his.setStatus(1);
								}

								his.setTotalTime((int) (System.currentTimeMillis() - ts));
								his.setDatabaseName(database.getTitle() + "[" + database.getHost() + "]");
								syncHistoryService.save(his);
							}
						} catch (Exception ex) {
							logger.error(ex);
						} finally {
							ExecutionUtils.remove(jobNo);
						}
					}
				}
			}
		}
		lastExecuteTime.set(System.currentTimeMillis());
	}

}
