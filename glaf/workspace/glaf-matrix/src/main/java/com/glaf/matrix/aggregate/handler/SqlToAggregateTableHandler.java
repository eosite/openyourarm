package com.glaf.matrix.aggregate.handler;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.Database;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.StringTools;
import com.glaf.datamgr.handler.DataExecutionHandler;
import com.glaf.matrix.aggregate.bean.SqlToAggregateTableBean;
import com.glaf.matrix.aggregate.domain.TableAggregateApp;
import com.glaf.matrix.aggregate.domain.TableAggregateHistory;
import com.glaf.matrix.aggregate.service.TableAggregateAppService;
import com.glaf.matrix.aggregate.service.TableAggregateHistoryService;

public class SqlToAggregateTableHandler implements DataExecutionHandler {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public void execute(Object id, Map<String, Object> context) {
		long syncId = Long.parseLong(id.toString());
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		TableAggregateAppService tableAggregateAppService = ContextFactory
				.getBean("com.glaf.matrix.aggregate.service.tableAggregateAppService");
		TableAggregateHistoryService tableAggregateHistoryService = ContextFactory
				.getBean("com.glaf.matrix.aggregate.service.tableAggregateHistoryService");
		TableAggregateApp tableAggregateApp = tableAggregateAppService.getTableAggregateApp(syncId);
		if (tableAggregateApp == null || !StringUtils.equals(tableAggregateApp.getActive(), "Y")) {
			return;
		}
		List<Long> databaseIds = StringTools.splitToLong(tableAggregateApp.getTargetDatabaseIds());
		if (databaseIds != null && !databaseIds.isEmpty()) {
			Database database = null;
			SqlToAggregateTableBean bean = new SqlToAggregateTableBean();
			for (long targetDatabaseId : databaseIds) {
				try {
					long start = System.currentTimeMillis();
					bean.execute(tableAggregateApp.getSrcDatabaseId(), targetDatabaseId, syncId, context);

					database = databaseService.getDatabaseById(targetDatabaseId);
					if (database != null) {
						TableAggregateHistory his = new TableAggregateHistory();
						his.setCreateBy("system");
						his.setDatabaseId(targetDatabaseId);
						his.setDeploymentId(tableAggregateApp.getDeploymentId());
						his.setSyncId(tableAggregateApp.getId());
						his.setStatus(1);
						his.setTotalTime((int) (System.currentTimeMillis() - start));
						his.setDatabaseName(database.getTitle() + "[" + database.getHost() + "]");
						tableAggregateHistoryService.save(his);
					}
				} catch (Exception ex) {
				}
			}
		}
	}

}
