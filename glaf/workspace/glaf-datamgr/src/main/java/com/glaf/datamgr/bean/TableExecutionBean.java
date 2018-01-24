package com.glaf.datamgr.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.Database;
import com.glaf.core.el.Mvel2ExpressionEvaluator;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JdbcUtils;

import com.glaf.datamgr.domain.TableExecution;
import com.glaf.datamgr.domain.TableExecutionColumn;
import com.glaf.datamgr.jdbc.DynamicSqlHelper;
import com.glaf.datamgr.service.TableExecutionService;

/**
 * create table tmp_1(id varchar(50) not null,date1 datetime ,state int,primary
 * key(id));<br/>
 * insert into tmp_1 values('111', null, null);<br/>
 * insert into tmp_1 values('112', null, null);<br/>
 * insert into tmp_1 values('113', null, null);<br/>
 *
 */
public class TableExecutionBean {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected long databaseId;

	protected List<String> executionIds;

	protected Map<String, Object> parameter;

	protected boolean result;

	public TableExecutionBean() {

	}

	public TableExecutionBean(long databaseId, List<String> executionIds, Map<String, Object> parameter) {
		this.databaseId = databaseId;
		this.executionIds = executionIds;
		this.parameter = parameter;
	}

	@SuppressWarnings("unchecked")
	public boolean execute(Connection conn, List<TableExecution> executions, Map<String, Object> parameter) {
		DynamicSqlHelper sqlHelper = new DynamicSqlHelper();
		List<Object> values = new ArrayList<Object>();
		StringBuilder sqlBuffer = new StringBuilder();

		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		if (parameter == null) {
			parameter = new HashMap<String, Object>();
		}

		parameter.put("now", now);
		parameter.put("_now_", now);
		parameter.put("_year_", year);
		parameter.put("_month_", month);
		parameter.put("_week_", week);
		parameter.put("_day_", day);
		parameter.put("_nowYearMonth_", DateUtils.getNowYearMonth());
		parameter.put("_nowYearMonthDay_", DateUtils.getNowYearMonthDay());

		List<TableExecutionColumn> cols = null;
		Iterator<Object> iterator = null;
		SqlExecutor sqlExecutor = null;
		Object value = null;

		PreparedStatement psmt = null;
		int index = 1;
		try {

			if (executions.size() > 0) {
				for (TableExecution execution : executions) {
					sqlBuffer.delete(0, sqlBuffer.length());
					if (StringUtils.equals(execution.getType(), "update")) {
						sqlBuffer.append(" update ").append(execution.getTableName()).append(" set ");
						cols = execution.getColumns();
						values.clear();
						for (TableExecutionColumn col : cols) {
							sqlBuffer.append(col.getColumnName()).append(" = ?, ");
							value = col.getValueExpression();
							if (StringUtils.equals(col.getValueExpression(), "#{now}")) {
								values.add(calendar.getTime());
							} else {
								if (StringUtils.startsWith(col.getValueExpression(), "#{")
										&& StringUtils.endsWith(col.getValueExpression(), "}")) {
									value = Mvel2ExpressionEvaluator.evaluate(col.getValueExpression(), parameter);
								}
								if (value != null) {
									switch (col.getType()) {
									case "Integer":
										values.add(Integer.valueOf(value.toString()));
										break;
									case "Long":
										values.add(Long.valueOf(value.toString()));
										break;
									case "Double":
										values.add(Double.valueOf(value.toString()));
										break;
									case "Date":
										values.add(DateUtils.toDate(value.toString()));
										break;
									case "String":
										values.add(value.toString());
										break;
									default:
										values.add(value);
										break;
									}
								} else {
									values.add(null);
								}
							}
						}
						sqlBuffer.delete(sqlBuffer.length() - 2, sqlBuffer.length());
						sqlBuffer.append(" where 1=1 ");
						sqlExecutor = sqlHelper.buildSql(execution.getTableName(), execution.getId(), parameter);

						if (sqlExecutor.getParameter() != null) {
							sqlBuffer.append(sqlExecutor.getSql());
							values.addAll((List<Object>) sqlExecutor.getParameter());
						}
						logger.debug("sql:" + sqlBuffer.toString());
						logger.debug("values:" + values);
						index = 1;
						psmt = conn.prepareStatement(sqlBuffer.toString());
						iterator = values.iterator();
						while (iterator.hasNext()) {
							value = iterator.next();
							if (value != null) {
								if (value instanceof Integer) {
									psmt.setInt(index++, (Integer) value);
								} else if (value instanceof Long) {
									psmt.setLong(index++, (Long) value);
								} else if (value instanceof Double) {
									psmt.setDouble(index++, (Double) value);
								} else if (value instanceof Date) {
									psmt.setTimestamp(index++, DateUtils.toTimestamp((Date) value));
								} else if (value instanceof String) {
									psmt.setString(index++, value.toString());
								} else {
									psmt.setObject(index++, value);
								}
							} else {
								psmt.setNull(index++, Types.NULL);
							}
						}
						psmt.executeUpdate();
						JdbcUtils.close(psmt);
					}
				}
			}
			return true;
		} catch (Exception ex) {
			logger.error("execute error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
		}
	}

	public boolean execute(long databaseId, List<String> executionIds, Map<String, Object> parameter) {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		TableExecutionService tableExecutionService = ContextFactory
				.getBean("com.glaf.datamgr.service.tableExecutionService");
		List<TableExecution> executions = new ArrayList<TableExecution>();
		Connection conn = null;
		try {
			for (String executionId : executionIds) {
				TableExecution tableExecution = tableExecutionService.getTableExecution(executionId);
				if (tableExecution != null && tableExecution.getLocked() == 0) {
					executions.add(tableExecution);
				}
			}

			Database database = databaseService.getDatabaseById(databaseId);
			if (database != null) {
				conn = DBConnectionFactory.getConnection(database.getName());
			} else {
				conn = DBConnectionFactory.getConnection();
			}
			conn.setAutoCommit(false);
			this.execute(conn, executions, parameter);
			conn.commit();
			return true;
		} catch (Exception ex) {
			logger.error("execute error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(conn);
		}
	}

	public List<TableExecution> getTableExecutions(List<String> executionIds) {
		TableExecutionService tableExecutionService = ContextFactory
				.getBean("com.glaf.datamgr.service.tableExecutionService");
		List<TableExecution> executions = new ArrayList<TableExecution>();
		try {
			for (String executionId : executionIds) {
				TableExecution tableExecution = tableExecutionService.getTableExecution(executionId);
				if (tableExecution != null && tableExecution.getLocked() == 0) {
					executions.add(tableExecution);
				}
			}
			return executions;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
