package com.glaf.base.usertask.bean;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.query.SysUserQuery;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.usertask.domain.UserTaskTotal;
import com.glaf.base.usertask.service.UserTaskTotalService;
import com.glaf.base.usertask.util.UserTaskTotalDomainFactory;
import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.jdbc.QueryConnectionFactory;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.query.DatabaseQuery;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.UUID32;
import com.glaf.datamgr.domain.SqlDefinition;
import com.glaf.datamgr.query.SqlDefinitionQuery;
import com.glaf.datamgr.service.SqlDefinitionService;

public class UserTaskTotalBean {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public void countTask() {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		SqlDefinitionService sqlDefinitionService = ContextFactory.getBean("sqlDefinitionService");
		SysUserService sysUserService = ContextFactory.getBean("sysUserService");
		UserTaskTotalService userTaskTotalService = ContextFactory
				.getBean("com.glaf.base.usertask.service.userTaskTotalService");
		DatabaseQuery query = new DatabaseQuery();
		query.active("1");
		query.type("sqlserver");
		Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
		List<Database> databases = databaseService.list(query);
		SqlDefinitionQuery q = new SqlDefinitionQuery();
		q.locked(0);
		List<SqlDefinition> sqlDefs = sqlDefinitionService.list(q);
		if (databases != null && !databases.isEmpty() && sqlDefs != null && !sqlDefs.isEmpty()) {
			Map<String, SqlDefinition> sqlDefMap = new HashMap<String, SqlDefinition>();
			for (SqlDefinition sqlDef : sqlDefs) {
				if (StringUtils.isNotEmpty(sqlDef.getCode()) && StringUtils.isNotEmpty(sqlDef.getSql())) {
					sqlDefMap.put(sqlDef.getCode().toLowerCase(), sqlDef);
				}
			}
			SysUserQuery uq = new SysUserQuery();
			Map<String, Object> params = new HashMap<String, Object>();
			List<UserTaskTotal> list = new ArrayList<UserTaskTotal>();
			DatabaseConnectionConfig cfg = new DatabaseConnectionConfig();
			QueryHelper helper = new QueryHelper();
			List<SysUser> users = null;
			for (Database database : databases) {
				long ts = 0;
				Connection conn = null;
				SqlDefinition sqlDef = null;
				try {
					if (cfg.checkConnectionImmediately(database)) {
						Environment.setCurrentSystemName(database.getName());
						users = sysUserService.getAllUsers(uq);
						if (users != null && !users.isEmpty()) {
							list.clear();
							params.clear();
							conn = DBConnectionFactory.getConnection(database.getName());
							ts = System.currentTimeMillis();
							QueryConnectionFactory.getInstance().register(ts, conn);
							SysUser u = users.get(0);
							if (u != null) {
								params.put("user", u);
								params.put("userid", u.getActorId());
								params.put("userId", u.getActorId());
								UserTaskTotal bean = new UserTaskTotal();
								bean.setCreateBy("system");
								bean.setCreateTime(new Date());
								bean.setDatabaseId(database.getId());
								bean.setId(UUID32.getUUID());
								bean.setUserId(u.getActorId());
								bean.setName(u.getName());
								if (sqlDefMap.get("sql_usertask_total") != null) {
									sqlDef = sqlDefMap.get("sql_usertask_total");
									bean.setTotal(helper.getTotal(conn, sqlDef.getSql(), params));
								}

								if (sqlDefMap.get("sql_usertask_finished") != null) {
									sqlDef = sqlDefMap.get("sql_usertask_finished");
									bean.setFinished(helper.getTotal(conn, sqlDef.getSql(), params));
								}

								if (sqlDefMap.get("sql_usertask_pending") != null) {
									sqlDef = sqlDefMap.get("sql_usertask_pending");
									bean.setPending(helper.getTotal(conn, sqlDef.getSql(), params));
								}

								if (sqlDefMap.get("sql_usertask_quantity1") != null) {
									sqlDef = sqlDefMap.get("sql_usertask_quantity1");
									bean.setQuantity1(helper.getTotal(conn, sqlDef.getSql(), params));
								}

								if (sqlDefMap.get("sql_usertask_quantity2") != null) {
									sqlDef = sqlDefMap.get("sql_usertask_quantity2");
									bean.setQuantity2(helper.getTotal(conn, sqlDef.getSql(), params));
								}

								if (sqlDefMap.get("sql_usertask_quantity3") != null) {
									sqlDef = sqlDefMap.get("sql_usertask_quantity3");
									bean.setQuantity3(helper.getTotal(conn, sqlDef.getSql(), params));
								}

								if (sqlDefMap.get("sql_usertask_quantity4") != null) {
									sqlDef = sqlDefMap.get("sql_usertask_quantity4");
									bean.setQuantity4(helper.getTotal(conn, sqlDef.getSql(), params));
								}

								if (sqlDefMap.get("sql_usertask_quantity5") != null) {
									sqlDef = sqlDefMap.get("sql_usertask_quantity5");
									bean.setQuantity5(helper.getTotal(conn, sqlDef.getSql(), params));
								}

								if (sqlDefMap.get("sql_usertask_quantity6") != null) {
									sqlDef = sqlDefMap.get("sql_usertask_quantity6");
									bean.setQuantity6(helper.getTotal(conn, sqlDef.getSql(), params));
								}

							}
							if (conn != null) {
								QueryConnectionFactory.getInstance().unregister(ts, conn);
								JdbcUtils.close(conn);
								conn = null;
							}

							long time = System.currentTimeMillis() - ts;
							if (time < 10000) {// 判断获取一个用户的待办任务时间是否超时
								conn = DBConnectionFactory.getConnection(database.getName());
								for (SysUser user : users) {
									if (!"-1".equals(user.getStatus())) {
										params.put("user", user);
										params.put("userid", user.getActorId());
										params.put("userId", user.getActorId());
										UserTaskTotal bean = new UserTaskTotal();
										bean.setCreateBy("system");
										bean.setCreateTime(new Date());
										bean.setDatabaseId(database.getId());
										bean.setId(UUID32.getUUID());
										bean.setUserId(user.getActorId());
										bean.setName(user.getName());
										if (sqlDefMap.get("sql_usertask_total") != null) {
											sqlDef = sqlDefMap.get("sql_usertask_total");
											bean.setTotal(helper.getTotal(conn, sqlDef.getSql(), params));
										}

										if (sqlDefMap.get("sql_usertask_finished") != null) {
											sqlDef = sqlDefMap.get("sql_usertask_finished");
											bean.setFinished(helper.getTotal(conn, sqlDef.getSql(), params));
										}

										if (sqlDefMap.get("sql_usertask_pending") != null) {
											sqlDef = sqlDefMap.get("sql_usertask_pending");
											bean.setPending(helper.getTotal(conn, sqlDef.getSql(), params));
										}

										if (sqlDefMap.get("sql_usertask_quantity1") != null) {
											sqlDef = sqlDefMap.get("sql_usertask_quantity1");
											bean.setQuantity1(helper.getTotal(conn, sqlDef.getSql(), params));
										}

										if (sqlDefMap.get("sql_usertask_quantity2") != null) {
											sqlDef = sqlDefMap.get("sql_usertask_quantity2");
											bean.setQuantity2(helper.getTotal(conn, sqlDef.getSql(), params));
										}

										if (sqlDefMap.get("sql_usertask_quantity3") != null) {
											sqlDef = sqlDefMap.get("sql_usertask_quantity3");
											bean.setQuantity3(helper.getTotal(conn, sqlDef.getSql(), params));
										}

										if (sqlDefMap.get("sql_usertask_quantity4") != null) {
											sqlDef = sqlDefMap.get("sql_usertask_quantity4");
											bean.setQuantity4(helper.getTotal(conn, sqlDef.getSql(), params));
										}

										if (sqlDefMap.get("sql_usertask_quantity5") != null) {
											sqlDef = sqlDefMap.get("sql_usertask_quantity5");
											bean.setQuantity5(helper.getTotal(conn, sqlDef.getSql(), params));
										}

										if (sqlDefMap.get("sql_usertask_quantity6") != null) {
											sqlDef = sqlDefMap.get("sql_usertask_quantity6");
											bean.setQuantity6(helper.getTotal(conn, sqlDef.getSql(), params));
										}

										list.add(bean);
									}
								}

								JdbcUtils.close(conn);

								if (!list.isEmpty()) {
									TableDefinition tableDefinition = UserTaskTotalDomainFactory
											.getTableDefinition("USER_TASK_TOTAL");
									if (!DBUtils.tableExists(database.getName(), "USER_TASK_TOTAL")) {
										DBUtils.createTable(database.getName(), tableDefinition);
									} else {
										DBUtils.alterTable(database.getName(), tableDefinition);
									}
									Environment.setCurrentSystemName(database.getName());
									userTaskTotalService.deleteByDatabaseId(database.getId());
									userTaskTotalService.bulkInsert(list);

									Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
									userTaskTotalService.deleteByDatabaseId(database.getId());
									userTaskTotalService.bulkInsert(list);
								}
							}
						}
					}
				} catch (Exception ex) {
					logger.error(" execute error " + database.getTitle(), ex);
					//throw new RuntimeException(" execute error " + database.getTitle(), ex);
				} finally {
					if (conn != null) {
						QueryConnectionFactory.getInstance().unregister(ts, conn);
					}
					JdbcUtils.close(conn);
				}
			}
		}
		Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
	}

}
