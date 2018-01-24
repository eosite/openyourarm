package com.glaf.matrix.combination.handler;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.Database;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DateUtils;

import com.glaf.datamgr.handler.DataExecutionHandler;
import com.glaf.datamgr.util.ExecutionUtils;

import com.glaf.matrix.combination.domain.SqlToTableApp;
import com.glaf.matrix.combination.domain.SqlToTableHistory;
import com.glaf.matrix.combination.service.SqlToTableAppService;
import com.glaf.matrix.combination.service.SqlToTableHistoryService;
import com.glaf.matrix.combination.task.SqlToTableAppTask;

public class SqlToTableHandler implements DataExecutionHandler {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public void execute(Object id, Map<String, Object> context) {
		long syncId = Long.parseLong(id.toString());
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		SqlToTableAppService sqlToTableAppService = ContextFactory
				.getBean("com.glaf.matrix.combination.service.sqlToTableAppService");
		SqlToTableHistoryService sqlToTableHistoryService = ContextFactory
				.getBean("com.glaf.matrix.combination.service.sqlToTableHistoryService");
		SqlToTableApp sqlToTableApp = sqlToTableAppService.getSqlToTableApp(syncId);
		if (sqlToTableApp == null || !StringUtils.equals(sqlToTableApp.getActive(), "Y")) {
			return;
		}
		String jobNo = null;
		Database database = null;
		String runDay = DateUtils.getNowYearMonthDayHour();
		SqlToTableAppTask task = null;
		jobNo = "sqltotable_app_" + sqlToTableApp.getId() + "_" + sqlToTableApp.getTargetDatabaseId() + "_" + runDay;
		try {
			database = databaseService.getDatabaseById(sqlToTableApp.getTargetDatabaseId());
			if (database != null) {
				ExecutionUtils.put(jobNo, new Date());
				long start = System.currentTimeMillis();

				task = new SqlToTableAppTask(syncId, jobNo, context);

				SqlToTableHistory his = new SqlToTableHistory();
				his.setCreateBy("system");
				his.setDatabaseId(database.getId());
				his.setDatabaseName(database.getTitle());
				his.setDeploymentId(sqlToTableApp.getDeploymentId());
				his.setSyncId(sqlToTableApp.getId());

				if (!task.execute()) {
					his.setStatus(-1);
				} else {
					his.setStatus(1);
				}
				his.setTotalTime((int) (System.currentTimeMillis() - start));

				sqlToTableHistoryService.save(his);
			}
		} catch (Exception ex) {
		}
	}

}
