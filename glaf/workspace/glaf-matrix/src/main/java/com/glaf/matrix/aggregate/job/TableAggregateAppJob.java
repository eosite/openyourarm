package com.glaf.matrix.aggregate.job;

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
import com.glaf.matrix.aggregate.bean.SqlToAggregateTableBean;
import com.glaf.matrix.aggregate.domain.TableAggregateApp;
import com.glaf.matrix.aggregate.domain.TableAggregateHistory;
import com.glaf.matrix.aggregate.query.TableAggregateAppQuery;
import com.glaf.matrix.aggregate.service.TableAggregateAppService;
import com.glaf.matrix.aggregate.service.TableAggregateHistoryService;
import com.glaf.matrix.util.SysParams;

public class TableAggregateAppJob extends BaseJob {

	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());

	@Override
	public void runJob(JobExecutionContext context) throws JobExecutionException {
		logger.debug("----------------------------TableAggregateAppJob-----------------------------");
		if ((System.currentTimeMillis() - lastExecuteTime.get()) < DateUtils.MINUTE) {
			return;
		}
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		TableAggregateAppService tableAggregateAppService = ContextFactory
				.getBean("com.glaf.matrix.aggregate.service.tableAggregateAppService");
		TableAggregateHistoryService tableAggregateHistoryService = ContextFactory
				.getBean("com.glaf.matrix.aggregate.service.tableAggregateHistoryService");
		TableAggregateAppQuery query = new TableAggregateAppQuery();
		query.autoSyncFlag("Y");
		query.active("Y");

		List<TableAggregateApp> rows = tableAggregateAppService.list(query);
		if (rows != null && !rows.isEmpty()) {
			SqlToAggregateTableBean bean = new SqlToAggregateTableBean();
			Map<String, Object> parameter = new HashMap<String, Object>();
			SysParams.putInternalParams(parameter);
			long ts = System.currentTimeMillis();
			Database database = null;
			for (TableAggregateApp app : rows) {
				if (StringUtils.isNotEmpty(app.getTargetDatabaseIds())) {
					List<Long> targetDatabaseIds = StringTools.splitToLong(app.getTargetDatabaseIds(), ",");
					for (long targetDatabaseId : targetDatabaseIds) {
						try {
							TableAggregateHistory last = tableAggregateHistoryService
									.getLatestTableAggregateHistory(app.getId(), targetDatabaseId);
							if (last != null && last.getStatus() == 1) {
								/**
								 * 判断是否满足时间间隔
								 */
								if ((ts - last.getCreateTime().getTime()) < app.getInterval() * 60 * 1000) {
									continue;
								}
							}
							long start = System.currentTimeMillis();
							bean.execute(app.getSrcDatabaseId(), targetDatabaseId, app.getId(), parameter);

							database = databaseService.getDatabaseById(targetDatabaseId);
							if (database != null) {
								TableAggregateHistory his = new TableAggregateHistory();
								his.setCreateBy("system");
								his.setDatabaseId(targetDatabaseId);
								his.setDeploymentId(app.getDeploymentId());
								his.setSyncId(app.getId());
								his.setStatus(1);
								his.setTotalTime((int) (System.currentTimeMillis() - start));
								his.setDatabaseName(database.getTitle() + "[" + database.getHost() + "]");
								tableAggregateHistoryService.save(his);
							}
						} catch (Exception ex) {
							logger.error(ex);
						}
					}
				}
			}
		}
		lastExecuteTime.set(System.currentTimeMillis());
	}

}
