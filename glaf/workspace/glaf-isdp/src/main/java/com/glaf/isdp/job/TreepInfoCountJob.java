package com.glaf.isdp.job;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.Database;
import com.glaf.core.job.BaseJob;
import com.glaf.core.query.DatabaseQuery;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.isdp.domain.TreepInfoCount;
import com.glaf.isdp.service.ProjInspectionService;
import com.glaf.isdp.service.TreepInfoService;

public class TreepInfoCountJob extends BaseJob {

	@Override
	public void runJob(JobExecutionContext context) throws JobExecutionException {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		TreepInfoService treepInfoService = ContextFactory.getBean("com.glaf.isdp.service.treepInfoService");
		ProjInspectionService projInspectionService = ContextFactory
				.getBean("com.glaf.isdp.service.projInspectionService");
		DatabaseQuery query = new DatabaseQuery();
		query.active("1");
		List<Database> databases = databaseService.getDatabases();
		if (databases != null && !databases.isEmpty()) {
			List<TreepInfoCount> list = new ArrayList<TreepInfoCount>();
			for (Database database : databases) {
				DatabaseConnectionConfig config = new DatabaseConnectionConfig();
				if (!config.checkConfig(database)) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(database.getType(), "sqlserver")) {
					try {
						Environment.setCurrentSystemName(database.getName());
						if (DBUtils.tableExists(database.getName(), "treepinfo")) {
							TreepInfoCount bean = new TreepInfoCount();
							bean.setDatabaseId(database.getId());
							bean.setMapping(database.getMapping());
							bean.setTitle(database.getTitle());
							bean.setType("x_proj_type_1");
							bean.setIntValue1(treepInfoService.countProjectType("1"));// 单位工程总数
							bean.setIntValue2(treepInfoService.countProjectType("2"));// 分部工程总数
							bean.setIntValue3(treepInfoService.countProjectType("3"));// 分项工程总数
							bean.setIntValue4(treepInfoService.countProjectType("4"));// 工序总数
							bean.setIntValue5(projInspectionService.countIntCheck(0));// 未报审总数
							bean.setIntValue6(projInspectionService.countIntCheck(1));// 报审中总数
							bean.setIntValue7(projInspectionService.countIntCheck(2));// 已报审总数
							list.add(bean);
							logger.debug(database.getTitle() + " 已报审总数:" + bean.getIntValue7());
						}
					} catch (java.lang.Throwable ex) {
						logger.error(ex);
					}
				}
			}
			try {
				Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
				treepInfoService.insertAll("x_proj_type_1", list);
			} catch (Exception ex) {
				logger.error(ex);
			}
		}
	}

}
