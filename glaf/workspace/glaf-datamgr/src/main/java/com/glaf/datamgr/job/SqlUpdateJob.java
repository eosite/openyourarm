/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.datamgr.job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.SysDataLog;
import com.glaf.core.factory.SysLogFactory;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.job.BaseJob;
import com.glaf.core.query.DatabaseQuery;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.datamgr.domain.SqlDefinition;
import com.glaf.datamgr.domain.SqlResult;
import com.glaf.datamgr.query.SqlDefinitionQuery;
import com.glaf.datamgr.service.SqlDefinitionService;
import com.glaf.datamgr.service.SqlResultService;

public class SqlUpdateJob extends BaseJob {

	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());

	@Override
	public void runJob(JobExecutionContext context) throws JobExecutionException {
		if ((System.currentTimeMillis() - lastExecuteTime.get()) < 60000) {
			return;
		}
		DatabaseConnectionConfig config = new DatabaseConnectionConfig();
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		SqlDefinitionService sqlDefinitionService = ContextFactory.getBean("sqlDefinitionService");
		SqlResultService sqlResultService = ContextFactory.getBean("sqlResultService");

		SqlDefinitionQuery q = new SqlDefinitionQuery();
		List<SqlDefinition> sqlList = sqlDefinitionService.list(q);
		DatabaseQuery query = new DatabaseQuery();
		query.active("1");
		List<Database> databases = databaseService.list(query);
		if (databases != null && !databases.isEmpty() && sqlList != null && !sqlList.isEmpty()) {
			for (Database database : databases) {
				if (!"1".equals(database.getActive())) {
					continue;
				}
				if (!config.checkConfig(database)) {
					continue;
				}
				logger.debug(database.getHost() + " start execute update...");
				String sql = null;
				for (SqlDefinition sqlDef : sqlList) {
					if (!StringUtils.equals(sqlDef.getScheduleFlag(), "Y")) {
						continue;
					}
					if (!StringUtils.equals(sqlDef.getOperation(), "update")) {
						continue;
					}
					if (sqlDef.getParentId() != null && sqlDef.getParentId() > 0) {
						continue;
					}
					logger.debug(sqlDef.getTitle() + " start execute ...");
					long start = System.currentTimeMillis();
					Connection connection = null;
					PreparedStatement psmt = null;
					ResultSet rs = null;
					int total = 0;
					try {
						connection = DBConnectionFactory.getConnection(database.getName());
						psmt = connection.prepareStatement(sqlDef.getSql());
						total = psmt.executeUpdate();
						JdbcUtils.close(psmt);
						connection.commit();
					} catch (SQLException ex) {
						total = -1;
						logger.error("count sql:" + sql);
						logger.error("execute count error", ex);
					} finally {
						JdbcUtils.close(rs);
						JdbcUtils.close(psmt);
						JdbcUtils.close(connection);
					}
					if (total > 0) {
						try {
							SqlResult bean = new SqlResult();
							bean.setCreateBy("system");
							bean.setCreateTime(new Date());
							bean.setRunDay(DateUtils.getNowYearMonthDay());
							bean.setDatabaseId(database.getId());
							bean.setSqlDefId(sqlDef.getId());
							// bean.setSql(sqlDef.getSql());
							bean.setOperation(sqlDef.getOperation());
							bean.setCount(total);
							sqlResultService.save(bean);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						try {
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("rowKey", sqlDef.getId());
							jsonObject.put("subject", sqlDef.getTitle());
							jsonObject.put("total", total);
							SysDataLog log = new SysDataLog();
							log.setActorId("system");
							log.setCreateTime(new Date());
							log.setFlag(1);
							log.setIp("127.0.0.1");
							log.setModuleId("database");
							log.setTimeMS((int) (System.currentTimeMillis() - start));
							log.setContent(jsonObject.toJSONString());
							SysLogFactory.getInstance().addLog(log);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		}
		lastExecuteTime.set(System.currentTimeMillis());
	}

}
