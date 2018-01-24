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

package com.glaf.datamgr.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.Database;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.query.DatabaseQuery;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.datamgr.domain.SqlDefinition;
import com.glaf.datamgr.domain.SqlResult;
import com.glaf.datamgr.query.SqlDefinitionQuery;
import com.glaf.datamgr.service.SqlDefinitionService;
import com.glaf.datamgr.service.SqlResultService;

public class SqlCountBean {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public void execute(String actorId, List<Long> databaseIds, List<Long> sqlDefIds) {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		SqlDefinitionService sqlDefinitionService = ContextFactory.getBean("sqlDefinitionService");
		SqlResultService sqlResultService = ContextFactory.getBean("sqlResultService");
		SqlDefinitionQuery q = new SqlDefinitionQuery();
		q.setLocked(0);
		List<SqlDefinition> sqlList = sqlDefinitionService.list(q);
		DatabaseQuery query = new DatabaseQuery();
		query.active("1");
		List<Database> databases = null;
		if ((StringUtils.equalsIgnoreCase(actorId, "admin") || StringUtils.equalsIgnoreCase(actorId, "system"))) {
			databases = databaseService.list(query);
		} else {
			databases = databaseService.getDatabases(actorId);
		}

		if (databases != null && !databases.isEmpty() && sqlList != null && !sqlList.isEmpty()) {
			DatabaseConnectionConfig config = new DatabaseConnectionConfig();
			SqlResult bean = null;
			Connection connection = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			String sql = null;
			int total = 0;
			for (Database database : databases) {
				if (!databaseIds.contains(database.getId())) {
					continue;
				}
				if (!config.checkConfig(database)) {
					continue;
				}
				logger.debug(database.getHost() + " start execute query...");

				for (SqlDefinition sqlDef : sqlList) {
					if (sqlDef.getLocked() != 0) {
						continue;
					}
					if (!sqlDefIds.contains(sqlDef.getId())) {
						continue;
					}
					if (!StringUtils.equals(sqlDef.getOperation(), "select")) {
						continue;
					}
					if (sqlDef.getParentId() != null && sqlDef.getParentId() > 0) {
						continue;
					}
					logger.debug(sqlDef.getTitle() + " start execute ...");
					total = 0;
					try {
						connection = DBConnectionFactory.getConnection(database.getName());
						if (StringUtils.isNotEmpty(sqlDef.getCountSql())) {
							sql = sqlDef.getCountSql();
							psmt = connection.prepareStatement(sqlDef.getCountSql());
							rs = psmt.executeQuery();
							if (rs.next()) {
								total = rs.getInt(1);
							}
						} else {
							sql = sqlDef.getSql();
							psmt = connection.prepareStatement(sqlDef.getSql());
							rs = psmt.executeQuery();
							while (rs.next()) {
								total = total + 1;
							}
						}
					} catch (SQLException ex) {
						total = -1;
						logger.error("count sql:" + sql);
						logger.error("execute count error", ex);
					} finally {
						JdbcUtils.close(rs);
						JdbcUtils.close(psmt);
						JdbcUtils.close(connection);
					}
					try {
						bean = new SqlResult();
						bean.setCreateBy(actorId);
						bean.setCreateTime(new Date());
						bean.setRunDay(DateUtils.getNowYearMonthDay());
						bean.setDatabaseId(database.getId());
						bean.setSqlDefId(sqlDef.getId());
						bean.setOperation(sqlDef.getOperation());
						bean.setCount(total);
						if (StringUtils.equals("Y", sqlDef.getSaveFlag())) {
							sqlResultService.save(bean);
						} else {
							if (total >= 0) {
								sqlResultService.save(bean);
							}
						}
					} catch (Exception ex) {
						logger.error("save result:" + ex);
					}
				}
			}
		}
	}

	public void executeAll(String actorId) {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		SqlDefinitionService sqlDefinitionService = ContextFactory.getBean("sqlDefinitionService");
		SqlResultService sqlResultService = ContextFactory.getBean("sqlResultService");
		SqlDefinitionQuery q = new SqlDefinitionQuery();
		q.setLocked(0);
		List<SqlDefinition> sqlList = sqlDefinitionService.list(q);
		DatabaseQuery query = new DatabaseQuery();
		query.active("1");
		List<Database> databases = null;
		if ((StringUtils.equalsIgnoreCase(actorId, "admin") || StringUtils.equalsIgnoreCase(actorId, "system"))) {
			databases = databaseService.list(query);
		} else {
			databases = databaseService.getDatabases(actorId);
		}

		if (databases != null && !databases.isEmpty() && sqlList != null && !sqlList.isEmpty()) {
			DatabaseConnectionConfig config = new DatabaseConnectionConfig();
			SqlResult bean = null;
			Connection connection = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;
			String sql = null;
			int total = 0;
			for (Database database : databases) {
				if (!config.checkConfig(database)) {
					continue;
				}
				logger.debug(database.getHost() + " start execute query...");

				for (SqlDefinition sqlDef : sqlList) {
					if (sqlDef.getLocked() != 0) {
						continue;
					}
					if (!StringUtils.equals(sqlDef.getOperation(), "select")) {
						continue;
					}
					if (sqlDef.getParentId() != null && sqlDef.getParentId() > 0) {
						continue;
					}
					logger.debug(sqlDef.getTitle() + " start execute ...");

					total = 0;
					try {
						connection = DBConnectionFactory.getConnection(database.getName());
						if (StringUtils.isNotEmpty(sqlDef.getCountSql())) {
							sql = sqlDef.getCountSql();
							psmt = connection.prepareStatement(sqlDef.getCountSql());
							rs = psmt.executeQuery();
							if (rs.next()) {
								total = rs.getInt(1);
							}
						} else {
							sql = sqlDef.getSql();
							psmt = connection.prepareStatement(sqlDef.getSql());
							rs = psmt.executeQuery();
							while (rs.next()) {
								total = total + 1;
							}
						}
					} catch (SQLException ex) {
						total = -1;
						logger.error("count sql:" + sql);
						logger.error("execute count error", ex);
					} finally {
						JdbcUtils.close(rs);
						JdbcUtils.close(psmt);
						JdbcUtils.close(connection);
					}
					try {
						bean = new SqlResult();
						bean.setCreateBy(actorId);
						bean.setCreateTime(new Date());
						bean.setRunDay(DateUtils.getNowYearMonthDay());
						bean.setDatabaseId(database.getId());
						bean.setSqlDefId(sqlDef.getId());
						bean.setOperation(sqlDef.getOperation());
						bean.setCount(total);
						if (StringUtils.equals("Y", sqlDef.getSaveFlag())) {
							sqlResultService.save(bean);
						} else {
							if (total >= 0) {
								sqlResultService.save(bean);
							}
						}
					} catch (Exception ex) {
						logger.error("save result:" + ex);
					}
				}
			}
		}
	}

}
