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

package com.glaf.core.db;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.expression.ExpressionFactory;
import com.glaf.core.factory.EntityFactory;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.EntityService;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.ExpressionConstants;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.QueryUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.UUID32;

public class TableDataManager {
	protected final static Log logger = LogFactory.getLog(TableDataManager.class);

	protected EntityService entityService;

	protected ITableDataService tableDataService;

	protected ITableDefinitionService tableDefinitionService;

	protected SqlSessionFactory sqlSessionFactory;

	public TableDataManager() {

	}

	public EntityService getEntityService() {
		if (entityService == null) {
			entityService = ContextFactory.getBean("entityService");
		}
		return entityService;
	}

	public SqlSessionFactory getSqlSessionFactory() {
		if (sqlSessionFactory == null) {
			sqlSessionFactory = EntityFactory.getInstance().getSqlSessionFactory();
		}
		return sqlSessionFactory;
	}

	public ITableDataService getTableDataService() {
		if (tableDataService == null) {
			tableDataService = ContextFactory.getBean("tableDataService");
		}
		return tableDataService;
	}

	public ITableDefinitionService getTableDefinitionService() {
		if (tableDefinitionService == null) {
			tableDefinitionService = ContextFactory.getBean("tableDefinitionService");
		}
		return tableDefinitionService;
	}

	public List<Map<String, Object>> getTablePrimaryKeyMap(String systemName, String tableName, String columnName) {
		TableModel tableModel = new TableModel();
		ColumnModel idColumn = new ColumnModel();
		idColumn.setColumnName(columnName);
		tableModel.setTableName(tableName.toUpperCase());
		tableModel.setIdColumn(idColumn);
		SqlSession sqlSession = null;
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection(systemName);
			sqlSession = getSqlSessionFactory().openSession(conn);
			List<Map<String, Object>> list = sqlSession.selectList("getTablePrimaryKeyMap", tableModel);
			logger.debug(list);
			return list;
		} catch (Exception ex) {
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(sqlSession);
			JdbcUtils.close(conn);
		}
	}

	public Collection<TableModel> insertAll(String systemName, TableDefinition tableDefinition, String seqNo,
			Collection<TableModel> rows) {
		com.glaf.core.config.Environment.setCurrentSystemName(systemName);
		logger.debug("tableDefinition=" + tableDefinition);
		logger.debug("idColumn=" + tableDefinition.getIdColumn().toString());
		if (tableDefinition.getTableName() != null) {
			tableDefinition.setTableName(tableDefinition.getTableName().toUpperCase());
		}
		Map<String, Object> colMap = new HashMap<String, Object>();

		Map<String, String> exprMap = new HashMap<String, String>();
		List<ColumnDefinition> exprColumns = new ArrayList<ColumnDefinition>();

		ColumnModel idColumn = new ColumnModel();

		ColumnDefinition idCol = tableDefinition.getIdColumn();
		if (idCol != null && idCol.getColumnName() != null) {
			idColumn.setColumnName(idCol.getColumnName());
			idColumn.setJavaType(idCol.getJavaType());
			idColumn.setValueExpression(idCol.getValueExpression());
			exprColumns.add(idCol);
			exprMap.put(idCol.getColumnName().toLowerCase(), idCol.getValueExpression());
		}

		Iterator<ColumnDefinition> iter = tableDefinition.getColumns().iterator();
		while (iter.hasNext()) {
			ColumnDefinition cell = iter.next();
			if (StringUtils.isNotEmpty(cell.getValueExpression())) {
				exprMap.put(cell.getColumnName().toLowerCase(), cell.getValueExpression());
				exprColumns.add(cell);
			}
		}

		logger.debug("expr map:" + exprMap);
		logger.debug(" rows size = " + rows.size());
		// logger.debug(" key map: " + keyMap);

		List<TableModel> inertRows = new ArrayList<TableModel>();

		Iterator<TableModel> iterator = rows.iterator();
		while (iterator.hasNext()) {
			TableModel tableData = iterator.next();
			ColumnModel myPK = tableData.getIdColumn();
			ColumnModel pkColumn = new ColumnModel();
			pkColumn.setColumnName(idColumn.getColumnName());
			pkColumn.setJavaType(idColumn.getJavaType());
			pkColumn.setValueExpression(idColumn.getValueExpression());

			for (ColumnModel column : tableData.getColumns()) {
				colMap.put(column.getColumnName(), column.getValue());
			}

			for (ColumnDefinition c : exprColumns) {
				ColumnModel x = new ColumnModel();
				x.setColumnName(c.getColumnName());
				x.setJavaType(c.getJavaType());
				x.setValueExpression(c.getValueExpression());
				tableData.addColumn(x);
			}

			for (ColumnModel cell : tableData.getColumns()) {
				String expr = exprMap.get(cell.getColumnName().toLowerCase());
				if (StringUtils.isNotEmpty(expr)) {
					if (ExpressionConstants.NOW_EXPRESSION.equals(expr)) {
						if (cell.getDateValue() == null) {
							cell.setDateValue(new Date());
							cell.setValue(cell.getDateValue());
						}
					}
					if (ExpressionConstants.ID_EXPRESSION.equals(expr)) {
						if (cell.getValue() == null) {
							if (StringUtils.equals(cell.getJavaType(), "Integer")) {
								cell.setValue(getEntityService().nextId().intValue());
							} else if (StringUtils.equals(cell.getJavaType(), "Long")) {
								cell.setValue(getEntityService().nextId());
							} else if (StringUtils.equals(cell.getValueExpression(),
									ExpressionConstants.UUID_EXPRESSION)) {
								cell.setValue(UUID32.getUUID());
							} else {
								cell.setValue(getEntityService().nextId());
							}
						}
					}
					if (StringUtils.startsWith(expr, ExpressionConstants.ID_PREFIX_EXPRESSION)) {
						if (cell.getValue() == null) {
							String name = expr.substring(ExpressionConstants.ID_PREFIX_EXPRESSION.length(),
									expr.length() - 1);
							if (StringUtils.equals(cell.getJavaType(), "Integer")) {
								cell.setValue(getEntityService().nextId(name).intValue());
							} else if (StringUtils.equals(cell.getJavaType(), "Long")) {
								cell.setValue(getEntityService().nextId(name));
							} else {
								cell.setValue(getEntityService().nextId(name));
							}
						}
					}
					if (ExpressionConstants.SEQNO_EXPRESSION.equals(expr)) {
						if (cell.getValue() == null) {
							cell.setValue(seqNo);
						}
					}

					Map<String, Object> context = new HashMap<String, Object>();

					if (cell.getValue() == null) {
						cell.setValue(ExpressionFactory.getInstance().evaluate(expr, context));
					}

				}
			}

			if (myPK != null && myPK.getValue() != null) {
				pkColumn.setValue(myPK.getValue());
			} else {
				if (StringUtils.equals(pkColumn.getJavaType(), "Integer")) {
					pkColumn.setValue(getEntityService().nextId().intValue());
				} else if (StringUtils.equals(pkColumn.getJavaType(), "Long")) {
					pkColumn.setValue(getEntityService().nextId());
				} else if (StringUtils.equals(pkColumn.getValueExpression(), ExpressionConstants.UUID_EXPRESSION)) {
					pkColumn.setValue(UUID32.getUUID());
				} else {
					pkColumn.setValue(getEntityService().nextId());
				}
			}

			tableData.removeColumn(pkColumn);
			tableData.addColumn(pkColumn);
			tableData.setIdColumn(pkColumn);

			inertRows.add(tableData);
		}

		if (!inertRows.isEmpty()) {
			logger.debug("inert rows size:" + inertRows.size());
			for (TableModel tableData : inertRows) {
				tableData.setTableName(tableDefinition.getTableName());
				logger.debug(tableData.toString());
				SqlSession sqlSession = null;
				Connection conn = null;
				try {
					conn = DBConnectionFactory.getConnection(systemName);
					conn.setAutoCommit(false);
					sqlSession = getSqlSessionFactory().openSession(ExecutorType.BATCH, conn);
					if (StringUtils.equals(tableDefinition.getDbType(), "hbase")) {
						tableData.setDbType("hbase");
					}
					if (StringUtils.equals(tableData.getDbType(), "hbase")) {
						sqlSession.insert("insertHBaseTableData", tableData);
					} else {
						sqlSession.insert("insertTableData", tableData);
					}
					sqlSession.commit();
					conn.commit();
				} catch (Exception ex) {
					JdbcUtils.rollback(conn);
					logger.error(ex);
					throw new RuntimeException(ex);
				} finally {
					JdbcUtils.close(sqlSession);
					JdbcUtils.close(conn);
				}
			}
		}

		return inertRows;
	}

	public void insertAllTableData(String systemName, List<TableModel> rows) {
		SqlSession sqlSession = null;
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection(systemName);
			conn.setAutoCommit(false);
			sqlSession = getSqlSessionFactory().openSession(ExecutorType.BATCH, conn);
			for (TableModel model : rows) {
				if (model.getTableName() != null) {
					model.setTableName(model.getTableName().toUpperCase());
				}
				if (StringUtils.equals(model.getDbType(), "hbase")) {
					sqlSession.insert("insertHBaseTableData", model);
				} else {
					sqlSession.insert("insertTableData", model);
				}
			}
			sqlSession.commit();
			conn.commit();
		} catch (Exception ex) {
			JdbcUtils.rollback(conn);
			logger.error(ex);
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(sqlSession);
			JdbcUtils.close(conn);
		}
	}

	public void insertAllTableData(String systemName, String dbType, List<TableModel> rows) {
		SqlSession sqlSession = null;
		Connection conn = null;
		try {
			conn = DBConnectionFactory.getConnection(systemName);
			conn.setAutoCommit(false);
			sqlSession = getSqlSessionFactory().openSession(ExecutorType.BATCH, conn);
			for (TableModel model : rows) {
				if (model.getTableName() != null) {
					model.setTableName(model.getTableName().toUpperCase());
				}
				if (dbType != null) {
					model.setDbType(dbType);
				}
				if (StringUtils.equals(dbType, "hbase")) {
					sqlSession.insert("insertHBaseTableData", model);
				} else {
					sqlSession.insert("insertTableData", model);
				}
			}
			sqlSession.commit();
			conn.commit();
		} catch (Exception ex) {
			JdbcUtils.rollback(conn);
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(sqlSession);
			JdbcUtils.close(conn);
		}
	}

	public void insertTableData(String systemName, String dbType, List<TableModel> rows) {
		if (rows != null && !rows.isEmpty()) {
			SqlSession sqlSession = null;
			Connection conn = null;
			try {
				conn = DBConnectionFactory.getConnection(systemName);
				conn.setAutoCommit(false);
				sqlSession = getSqlSessionFactory().openSession(ExecutorType.BATCH, conn);
				for (TableModel t : rows) {
					if (t.getTableName() != null) {
						t.setTableName(t.getTableName().toUpperCase());
					}
					if (dbType != null) {
						t.setDbType(dbType);
					}
					if (StringUtils.equals(dbType, "hbase")) {
						sqlSession.insert("insertHBaseTableData", t);
					} else {
						sqlSession.insert("insertTableData", t);
					}
				}
				sqlSession.commit();
				conn.commit();
			} catch (Exception ex) {
				JdbcUtils.rollback(conn);
				logger.error(ex);
				throw new RuntimeException(ex);
			} finally {
				JdbcUtils.close(sqlSession);
				JdbcUtils.close(conn);
			}
		}
	}

	public void insertTableData(String systemName, String dbType, String tableName, List<Map<String, Object>> rows) {
		com.glaf.core.config.Environment.setCurrentSystemName(systemName);
		TableDefinition tableDefinition = getTableDefinitionService().getTableDefinition(tableName);
		if (tableDefinition != null) {
			if (tableDefinition.getTableName() != null) {
				tableDefinition.setTableName(tableDefinition.getTableName().toUpperCase());
			}
			List<ColumnDefinition> columns = getTableDefinitionService().getColumnDefinitionsByTableName(tableName);
			if (columns != null && !columns.isEmpty()) {
				SqlSession sqlSession = null;
				Connection conn = null;
				try {
					conn = DBConnectionFactory.getConnection(systemName);
					conn.setAutoCommit(false);
					sqlSession = getSqlSessionFactory().openSession(ExecutorType.BATCH, conn);
					Iterator<Map<String, Object>> iterator = rows.iterator();
					while (iterator.hasNext()) {
						TableModel table = new TableModel();
						table.setTableName(tableName);
						Map<String, Object> dataMap = iterator.next();
						for (ColumnDefinition column : columns) {
							String javaType = column.getJavaType();
							String name = column.getColumnName();
							ColumnModel c = new ColumnModel();
							c.setColumnName(name);
							c.setJavaType(javaType);
							Object value = dataMap.get(name);
							if (value == null) {
								value = dataMap.get(name.toLowerCase());
							}
							if (value == null) {
								if (column.getName() != null) {
									value = dataMap.get(column.getName());
									if (value == null) {
										value = dataMap.get(column.getName().toLowerCase());
									}
								}
							}
							if (value != null) {
								if ("Integer".equals(javaType)) {
									value = ParamUtils.getInt(dataMap, name);
								} else if ("Long".equals(javaType)) {
									value = ParamUtils.getLong(dataMap, name);
								} else if ("Double".equals(javaType)) {
									value = ParamUtils.getDouble(dataMap, name);
								} else if ("Date".equals(javaType)) {
									value = ParamUtils.getTimestamp(dataMap, name);
								} else if ("String".equals(javaType)) {
									value = ParamUtils.getString(dataMap, name);
								} else if ("Clob".equals(javaType)) {
									value = ParamUtils.getString(dataMap, name);
								}
								c.setValue(value);
								table.addColumn(c);
							}
						}
						if (dbType != null) {
							table.setDbType(dbType);
						}
						if (StringUtils.equals(dbType, "hbase")) {
							sqlSession.insert("insertHBaseTableData", table);
						} else {
							sqlSession.insert("insertTableData", table);
						}
					}
					sqlSession.commit();
					conn.commit();
				} catch (Exception ex) {
					JdbcUtils.rollback(conn);
					logger.error(ex);
					throw new RuntimeException(ex);
				} finally {
					JdbcUtils.close(sqlSession);
					JdbcUtils.close(conn);
				}
			}
		}
	}

	public void insertTableData(String systemName, TableDefinition tableDefinition, List<Map<String, Object>> rows) {
		if (tableDefinition.getTableName() != null) {
			tableDefinition.setTableName(tableDefinition.getTableName().toUpperCase());
		}
		List<ColumnDefinition> columns = tableDefinition.getColumns();
		if (columns != null && !columns.isEmpty()) {
			SqlSession sqlSession = null;
			Connection conn = null;
			try {
				conn = DBConnectionFactory.getConnection(systemName);
				conn.setAutoCommit(false);
				sqlSession = getSqlSessionFactory().openSession(ExecutorType.BATCH, conn);
				Iterator<Map<String, Object>> iterator = rows.iterator();
				while (iterator.hasNext()) {
					TableModel table = new TableModel();
					table.setTableName(tableDefinition.getTableName());
					Map<String, Object> dataMap = iterator.next();
					for (ColumnDefinition column : columns) {
						String javaType = column.getJavaType();
						String name = column.getColumnName();
						ColumnModel c = new ColumnModel();
						c.setColumnName(name);
						c.setJavaType(javaType);
						Object value = dataMap.get(name);
						if (value == null) {
							value = dataMap.get(name.toLowerCase());
						}
						if (value == null) {
							if (column.getName() != null) {
								value = dataMap.get(column.getName());
								if (value == null) {
									value = dataMap.get(column.getName().toLowerCase());
								}
							}
						}
						if (value != null) {
							if ("Integer".equals(javaType)) {
								value = ParamUtils.getInt(dataMap, name);
							} else if ("Long".equals(javaType)) {
								value = ParamUtils.getLong(dataMap, name);
							} else if ("Double".equals(javaType)) {
								value = ParamUtils.getDouble(dataMap, name);
							} else if ("Date".equals(javaType)) {
								value = ParamUtils.getTimestamp(dataMap, name);
							} else if ("String".equals(javaType)) {
								value = ParamUtils.getString(dataMap, name);
							} else if ("Clob".equals(javaType)) {
								value = ParamUtils.getString(dataMap, name);
							}
							c.setValue(value);
							table.addColumn(c);
						}
					}
					if (StringUtils.equals(tableDefinition.getDbType(), "hbase")) {
						table.setDbType("hbase");
					}
					if (StringUtils.equals(table.getDbType(), "hbase")) {
						sqlSession.insert("insertHBaseTableData", table);
					} else {
						sqlSession.insert("insertTableData", table);
					}
				}
				sqlSession.commit();
				conn.commit();
			} catch (Exception ex) {
				JdbcUtils.rollback(conn);
				logger.error(ex);
				throw new RuntimeException(ex);
			} finally {
				JdbcUtils.close(sqlSession);
				JdbcUtils.close(conn);
			}
		}
	}

	public void saveAll(String systemName, String tableName, String seqNo, Collection<TableModel> rows) {
		com.glaf.core.config.Environment.setCurrentSystemName(systemName);
		TableDefinition tableDefinition = getTableDefinitionService().getTableDefinition(tableName);
		if (tableDefinition != null && tableDefinition.getIdColumn() != null
				&& tableDefinition.getAggregationKeys() != null) {
			this.saveAll(systemName, tableDefinition, seqNo, rows);
		}
	}

	public Collection<TableModel> saveAll(String systemName, TableDefinition tableDefinition, String seqNo,
			Collection<TableModel> rows) {
		com.glaf.core.config.Environment.setCurrentSystemName(systemName);
		logger.debug("tableDefinition=" + tableDefinition);
		logger.debug("idColumn=" + tableDefinition.getIdColumn().toString());
		if (tableDefinition.getTableName() != null) {
			tableDefinition.setTableName(tableDefinition.getTableName().toUpperCase());
		}
		if (tableDefinition.isInsertOnly()) {
			return this.insertAll(systemName, tableDefinition, seqNo, rows);
		}

		Collection<String> aggregationKeys = new HashSet<String>();

		Map<String, Object> keyMap = new HashMap<String, Object>();
		Map<String, String> exprMap = new HashMap<String, String>();
		List<ColumnDefinition> exprColumns = new ArrayList<ColumnDefinition>();

		ColumnModel idColumn = new ColumnModel();

		ColumnDefinition idCol = tableDefinition.getIdColumn();
		if (idCol != null && idCol.getColumnName() != null) {
			idColumn.setColumnName(idCol.getColumnName());
			idColumn.setJavaType(idCol.getJavaType());
			idColumn.setValueExpression(idCol.getValueExpression());
		}

		Iterator<ColumnDefinition> iter = tableDefinition.getColumns().iterator();
		while (iter.hasNext()) {
			ColumnDefinition cell = iter.next();
			if (StringUtils.isNotEmpty(cell.getValueExpression())) {
				exprMap.put(cell.getColumnName(), cell.getValueExpression());
				exprColumns.add(cell);
			}
		}

		logger.debug(exprMap);

		String keyCloumns = tableDefinition.getAggregationKeys();
		logger.debug("keyCloumns:" + keyCloumns);
		if (StringUtils.isNotEmpty(keyCloumns)) {
			List<String> cols = StringTools.split(keyCloumns);
			if (cols != null && !cols.isEmpty()) {
				StringBuilder buffer = new StringBuilder(500);
				Map<String, Object> colMap = new HashMap<String, Object>();
				Iterator<TableModel> iterator = rows.iterator();
				while (iterator.hasNext()) {
					TableModel tableData = iterator.next();
					// logger.debug("scan...");
					/**
					 * 使用聚合主键判断
					 */
					colMap.clear();
					buffer.delete(0, buffer.length());
					for (ColumnModel cell : tableData.getColumns()) {
						colMap.put(cell.getColumnName().toLowerCase(), cell.getValue());
					}

					if (tableData.getIdColumn() != null && tableData.getIdColumn().getValue() != null) {
						colMap.put(tableData.getIdColumn().getColumnName().toLowerCase(),
								tableData.getIdColumn().getValue());
					}

					// logger.debug("colMap="+colMap);
					Iterator<String> it = cols.iterator();
					while (it.hasNext()) {
						String key = it.next().toLowerCase();
						Object val = colMap.get(key);
						if (val != null) {
							if (val instanceof Date) {
								buffer.append(DateUtils.getDateTime(DateUtils.FULL_DATE_FORMAT, (Date) val));
							} else {
								buffer.append(val.toString());
							}
						} else {
							buffer.append("");
						}
						if (it.hasNext()) {
							buffer.append("_");
						}
					}
					String aggregationKey = buffer.toString();
					aggregationKeys.add(aggregationKey);
					tableData.setAggregationKey(aggregationKey);// 设置聚合主键值
					// logger.debug("aggregationKey="+aggregationKey);

					if (aggregationKeys.size() > 0 && (aggregationKeys.size() % 500 == 0)) {
						TableModel model = new TableModel();
						model.setTableName(tableDefinition.getTableName());
						model.setIdColumn(idColumn);
						model.setAggregationKeys(aggregationKeys);
						List<Map<String, Object>> list = getTableDataService().getTableKeyMap(model);
						if (list != null && !list.isEmpty()) {
							for (Map<String, Object> map : list) {
								Map<String, Object> dataMap = QueryUtils.lowerKeyMap(map);
								Object id = ParamUtils.getObject(dataMap, "id");
								String aggregationKey2 = ParamUtils.getString(dataMap, "aggregationkey");
								keyMap.put(aggregationKey2, id);
							}
						}
						aggregationKeys.clear();
					}
				}
			}

			if (aggregationKeys.size() > 0) {
				TableModel model = new TableModel();
				model.setTableName(tableDefinition.getTableName());
				model.setIdColumn(idColumn);
				model.setAggregationKeys(aggregationKeys);
				List<Map<String, Object>> list = getTableDataService().getTableKeyMap(model);
				if (list != null && !list.isEmpty()) {
					for (Map<String, Object> map : list) {
						Map<String, Object> dataMap = QueryUtils.lowerKeyMap(map);
						Object id = ParamUtils.getObject(dataMap, "id");
						String aggregationKey = ParamUtils.getString(dataMap, "aggregationkey");
						keyMap.put(aggregationKey, id);
					}
				}
				aggregationKeys.clear();
			}

			List<TableModel> inertRows = new ArrayList<TableModel>();
			List<TableModel> updateRows = new ArrayList<TableModel>();
			// logger.debug("rows size = " + rows.size());
			// logger.debug("keyMap:" + keyMap);
			Map<String, Object> colMap = new HashMap<String, Object>();
			Iterator<TableModel> iterator = rows.iterator();
			while (iterator.hasNext()) {
				colMap.clear();
				TableModel tableData = iterator.next();
				ColumnModel myPK = tableData.getIdColumn();
				ColumnModel pkColumn = new ColumnModel();
				pkColumn.setColumnName(idColumn.getColumnName());
				pkColumn.setJavaType(idColumn.getJavaType());
				pkColumn.setValueExpression(idColumn.getValueExpression());

				for (ColumnModel column : tableData.getColumns()) {
					colMap.put(column.getColumnName(), column.getValue());
				}

				if (tableData.getIdColumn() != null && tableData.getIdColumn().getValue() != null) {
					colMap.put(tableData.getIdColumn().getColumnName().toLowerCase(),
							tableData.getIdColumn().getValue());
				}

				if (keyMap.containsKey(tableData.getAggregationKey())) {
					Object id = keyMap.get(tableData.getAggregationKey());
					pkColumn.setValue(id);
					tableData.setIdColumn(pkColumn);
					tableData.removeColumn(pkColumn);
					updateRows.add(tableData);
				} else {
					ColumnModel col = new ColumnModel();
					col.setColumnName("AGGREGATIONKEY_");
					col.setJavaType("String");
					col.setValue(tableData.getAggregationKey());
					tableData.removeColumn(col);
					tableData.addColumn(col);

					for (ColumnDefinition c : exprColumns) {
						ColumnModel x = new ColumnModel();
						x.setColumnName(c.getColumnName());
						x.setJavaType(c.getJavaType());
						x.setValueExpression(c.getValueExpression());
						tableData.addColumn(x);
					}

					for (ColumnModel cell : tableData.getColumns()) {
						String expr = exprMap.get(cell.getColumnName());
						if (StringUtils.isNotEmpty(expr)) {
							if (ExpressionConstants.NOW_EXPRESSION.equals(expr)) {
								if (cell.getDateValue() == null) {
									cell.setDateValue(new Date());
									cell.setValue(cell.getDateValue());
								}
							}
							if (ExpressionConstants.ID_EXPRESSION.equals(expr)) {
								if (cell.getValue() == null) {
									if (StringUtils.equals(cell.getJavaType(), "Integer")) {
										cell.setValue(getEntityService().nextId().intValue());
									} else if (StringUtils.equals(cell.getJavaType(), "Long")) {
										cell.setValue(getEntityService().nextId());
									} else if (StringUtils.equals(cell.getValueExpression(),
											ExpressionConstants.UUID_EXPRESSION)) {
										cell.setValue(UUID32.getUUID());
									} else {
										cell.setValue(getEntityService().nextId());
									}
								}
							}

							if (StringUtils.startsWith(expr, ExpressionConstants.ID_PREFIX_EXPRESSION)) {
								if (cell.getValue() == null) {
									String name = expr.substring(ExpressionConstants.ID_PREFIX_EXPRESSION.length(),
											expr.length() - 1);
									if (StringUtils.equals(cell.getJavaType(), "Integer")) {
										cell.setValue(getEntityService().nextId(name).intValue());
									} else if (StringUtils.equals(cell.getJavaType(), "Long")) {
										cell.setValue(getEntityService().nextId(name));
									} else {
										cell.setValue(getEntityService().nextId(name));
									}
								}
							}

							if (ExpressionConstants.SEQNO_EXPRESSION.equals(expr)) {
								if (cell.getValue() == null) {
									cell.setValue(seqNo);
								}
							}
							Map<String, Object> context = new HashMap<String, Object>();

							if (cell.getValue() == null) {
								cell.setValue(ExpressionFactory.getInstance().evaluate(expr, context));
							}
						}
					}

					if (myPK != null && myPK.getValue() != null) {
						pkColumn.setValue(myPK.getValue());
					} else {
						if (StringUtils.equals(pkColumn.getJavaType(), "Integer")) {
							pkColumn.setValue(getEntityService().nextId().intValue());
						} else if (StringUtils.equals(pkColumn.getJavaType(), "Long")) {
							pkColumn.setValue(getEntityService().nextId());
						} else if (StringUtils.equals(pkColumn.getValueExpression(),
								ExpressionConstants.UUID_EXPRESSION)) {
							pkColumn.setValue(UUID32.getUUID());
						} else {
							pkColumn.setValue(getEntityService().nextId());
						}
					}

					tableData.removeColumn(pkColumn);
					tableData.addColumn(pkColumn);
					tableData.setIdColumn(pkColumn);

					inertRows.add(tableData);
				}
			}

			SqlSession sqlSession = null;
			Connection conn = null;
			try {
				conn = DBConnectionFactory.getConnection(systemName);
				conn.setAutoCommit(false);
				sqlSession = getSqlSessionFactory().openSession(ExecutorType.BATCH, conn);
				if (inertRows.size() > 0) {
					logger.debug("inert rows size:" + inertRows.size());
					for (TableModel tableData : inertRows) {
						tableData.setTableName(tableDefinition.getTableName());
						// logger.debug(tableData.toString());
						if (StringUtils.equals(tableDefinition.getDbType(), "hbase")) {
							tableData.setDbType("hbase");
						}
						if (StringUtils.equals(tableData.getDbType(), "hbase")) {
							sqlSession.insert("insertHBaseTableData", tableData);
						} else {
							sqlSession.insert("insertTableData", tableData);
						}
					}
				}
				if (updateRows.size() > 0 && tableDefinition.isUpdateAllowed()) {
					logger.debug("update rows size:" + updateRows.size());
					for (TableModel tableData : updateRows) {
						tableData.setTableName(tableDefinition.getTableName());
						if (StringUtils.equals(tableDefinition.getDbType(), "hbase")) {
							tableData.setDbType("hbase");
							sqlSession.insert("insertHBaseTableData", tableData);
						} else {
							sqlSession.insert("updateTableDataByPrimaryKey", tableData);
						}
					}
				}
				sqlSession.commit();
				conn.commit();
			} catch (Exception ex) {
				JdbcUtils.rollback(conn);
				logger.error(ex);
				throw new RuntimeException(ex);
			} finally {
				JdbcUtils.close(sqlSession);
				JdbcUtils.close(conn);
			}
			return rows;
		} else {
			throw new RuntimeException("aggregationKeys is required.");
		}
	}

	public void saveOrUpdate(String systemName, String dbType, String tableName, boolean updatable,
			List<Map<String, Object>> rows) {
		com.glaf.core.config.Environment.setCurrentSystemName(systemName);
		TableDefinition tableDefinition = getTableDefinitionService().getTableDefinition(tableName);
		if (tableDefinition != null) {
			if (tableDefinition.getTableName() != null) {
				tableDefinition.setTableName(tableDefinition.getTableName().toUpperCase());
			}
			String idColumnName = null;
			List<Map<String, Object>> rowIds = null;
			List<ColumnDefinition> columns = getTableDefinitionService().getColumnDefinitionsByTableName(tableName);
			if (columns != null && !columns.isEmpty()) {
				for (ColumnDefinition column : columns) {
					if (column.isPrimaryKey()) {
						idColumnName = column.getColumnName();
						rowIds = this.getTablePrimaryKeyMap(systemName, tableName, idColumnName);
						break;
					}
				}
			}

			Collection<String> keys = new HashSet<String>();

			if (rowIds != null && !rowIds.isEmpty()) {
				Iterator<Map<String, Object>> iter = rowIds.iterator();
				while (iter.hasNext()) {
					Map<String, Object> dataMap = iter.next();
					if (dataMap.get("id") != null) {
						keys.add(dataMap.get("id").toString());
					}
					if (dataMap.get("ID") != null) {
						keys.add(dataMap.get("ID").toString());
					}
				}
			}

			List<Map<String, Object>> inertRows = new java.util.ArrayList<Map<String, Object>>();
			List<Map<String, Object>> updateRows = new java.util.ArrayList<Map<String, Object>>();
			Iterator<Map<String, Object>> iterator = rows.iterator();
			while (iterator.hasNext()) {
				Map<String, Object> dataMap = iterator.next();
				Object id = dataMap.get(idColumnName);
				if (id != null) {
					if (keys.contains(id.toString())) {
						updateRows.add(dataMap);
					} else {
						inertRows.add(dataMap);
					}
				}
			}

			if (!inertRows.isEmpty()) {
				this.insertTableData(systemName, dbType, tableName, inertRows);
			}

			if (!updateRows.isEmpty()) {
				this.updateTableData(systemName, dbType, tableName, updateRows);
			}
		}
	}

	public void saveOrUpdate(String systemName, TableDefinition tableDefinition, List<Map<String, Object>> rows) {
		if (tableDefinition.getTableName() != null) {
			tableDefinition.setTableName(tableDefinition.getTableName().toUpperCase());
		}

		String idColumnName = null;
		if (tableDefinition.getIdColumn() != null) {
			idColumnName = tableDefinition.getIdColumn().getColumnName();
		}

		if (idColumnName == null) {
			List<ColumnDefinition> columns = tableDefinition.getColumns();
			if (columns != null && !columns.isEmpty()) {
				for (ColumnDefinition column : columns) {
					if (column.isPrimaryKey()) {
						idColumnName = column.getColumnName();
						break;
					}
				}
			}
		}

		List<Map<String, Object>> rowIds = null;
		if (idColumnName != null) {
			String tableName = tableDefinition.getTableName();
			rowIds = this.getTablePrimaryKeyMap(systemName, tableName, idColumnName.toLowerCase());
		}

		Collection<String> keys = new HashSet<String>();

		if (rowIds != null && !rowIds.isEmpty()) {
			Iterator<Map<String, Object>> iter = rowIds.iterator();
			while (iter.hasNext()) {
				Map<String, Object> dataMap = iter.next();
				if (dataMap.get("id") != null) {
					keys.add(dataMap.get("id").toString());
				}
				if (dataMap.get("ID") != null) {
					keys.add(dataMap.get("ID").toString());
				}
			}
		}

		logger.debug("rowIds:" + rowIds);
		logger.debug("keys:" + keys);

		List<Map<String, Object>> inertRows = new java.util.ArrayList<Map<String, Object>>();
		List<Map<String, Object>> updateRows = new java.util.ArrayList<Map<String, Object>>();
		Iterator<Map<String, Object>> iterator = rows.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> dataMap = iterator.next();
			Object id = dataMap.get(idColumnName);
			if (id == null) {
				id = dataMap.get(idColumnName.toLowerCase());
			}
			if (id != null) {
				if (keys.contains(id.toString())) {
					updateRows.add(dataMap);
				} else {
					inertRows.add(dataMap);
				}
			}
		}

		if (!inertRows.isEmpty()) {
			this.insertTableData(systemName, tableDefinition, inertRows);
		}

		if (!updateRows.isEmpty()) {
			this.updateTableData(systemName, tableDefinition, updateRows);
		}

	}

	/**
	 * 保存JSON数据到指定的表
	 * 
	 * @param tableName
	 * @param jsonObject
	 */
	public void saveTableData(SqlSession sqlSession, String systemName, String tableName, JSONObject jsonObject) {
		com.glaf.core.config.Environment.setCurrentSystemName(systemName);
		TableDefinition tableDefinition = getTableDefinitionService().getTableDefinition(tableName);
		if (tableDefinition != null && tableDefinition.getIdColumn() != null) {
			if (tableDefinition.getTableName() != null) {
				tableDefinition.setTableName(tableDefinition.getTableName().toUpperCase());
			}
			ColumnDefinition idColumn = tableDefinition.getIdColumn();

			boolean insertData = true;
			Object primaryKey = null;
			if (idColumn.getColumnName() != null) {
				primaryKey = jsonObject.get(idColumn.getColumnName());
			} else if (idColumn.getName() != null) {
				primaryKey = jsonObject.get(idColumn.getName());
			}
			if (primaryKey != null) {
				insertData = false;
			}

			TableModel tableModel = new TableModel();
			tableModel.setTableName(tableName);

			List<ColumnDefinition> columns = tableDefinition.getColumns();
			if (columns != null && !columns.isEmpty()) {
				for (ColumnDefinition col : columns) {
					if (StringUtils.equalsIgnoreCase(idColumn.getColumnName(), col.getColumnName())) {
						continue;
					}
					String javaType = col.getJavaType();
					String columnName = col.getColumnName();
					String name = col.getName();
					ColumnModel cm = new ColumnModel();
					cm.setJavaType(javaType);
					cm.setColumnName(col.getColumnName());
					Object value = null;

					if (jsonObject.containsKey(columnName)) {
						value = jsonObject.get(columnName);
						if (StringUtils.equalsIgnoreCase("Integer", javaType)) {
							if (value instanceof Integer) {
								cm.setValue(jsonObject.getInteger(columnName));
							} else {
								cm.setValue(Integer.parseInt(value.toString()));
							}
						} else if (StringUtils.equalsIgnoreCase("Long", javaType)) {
							if (value instanceof Long) {
								cm.setValue(jsonObject.getLong(columnName));
							} else {
								cm.setValue(Long.parseLong(value.toString()));
							}
						} else if (StringUtils.equalsIgnoreCase("Double", javaType)) {
							if (value instanceof Double) {
								cm.setValue(jsonObject.getDouble(columnName));
							} else {
								cm.setValue(Double.parseDouble(value.toString()));
							}
						} else if (StringUtils.equalsIgnoreCase("Date", javaType)) {
							if (value instanceof Date) {
								cm.setValue(jsonObject.getDate(columnName));
							} else if (value instanceof Long) {
								Long t = jsonObject.getLong(columnName);
								cm.setValue(new Date(t));
							} else {
								cm.setValue(DateUtils.toDate(value.toString()));
							}
						} else if (StringUtils.equalsIgnoreCase("String", javaType)) {
							if (value instanceof String) {
								cm.setValue(jsonObject.getString(columnName));
							} else {
								cm.setValue(value.toString());
							}
						} else {
							cm.setValue(value);
						}
					} else if (jsonObject.containsKey(name)) {
						value = jsonObject.get(name);
						if (StringUtils.equalsIgnoreCase("Integer", javaType)) {
							if (value instanceof Integer) {
								cm.setValue(jsonObject.getInteger(name));
							} else {
								cm.setValue(Integer.parseInt(value.toString()));
							}
						} else if (StringUtils.equalsIgnoreCase("Long", javaType)) {
							if (value instanceof Long) {
								cm.setValue(jsonObject.getLong(name));
							} else {
								cm.setValue(Long.parseLong(value.toString()));
							}
						} else if (StringUtils.equalsIgnoreCase("Double", javaType)) {
							if (value instanceof Double) {
								cm.setValue(jsonObject.getDouble(name));
							} else {
								cm.setValue(Double.parseDouble(value.toString()));
							}
						} else if (StringUtils.equalsIgnoreCase("Date", javaType)) {
							if (value instanceof Date) {
								cm.setValue(jsonObject.getDate(name));
							} else if (value instanceof Long) {
								Long t = jsonObject.getLong(name);
								cm.setValue(new Date(t));
							} else {
								cm.setValue(DateUtils.toDate(value.toString()));
							}
						} else if (StringUtils.equalsIgnoreCase("String", javaType)) {
							if (value instanceof String) {
								cm.setValue(jsonObject.getString(name));
							} else {
								cm.setValue(value.toString());
							}
						} else {
							cm.setValue(value);
						}
					}
					tableModel.addColumn(cm);
				}
			}

			if (insertData) {
				ColumnModel idCol = new ColumnModel();
				idCol.setJavaType(idColumn.getJavaType());
				if (StringUtils.equalsIgnoreCase("Integer", idColumn.getJavaType())) {
					idCol.setValue(getEntityService().nextId().intValue());
				} else if (StringUtils.equalsIgnoreCase("Long", idColumn.getJavaType())) {
					idCol.setValue(getEntityService().nextId());
				} else if (StringUtils.equalsIgnoreCase(ExpressionConstants.UUID_EXPRESSION,
						idColumn.getValueExpression())) {
					idCol.setValue(UUID32.getUUID());
				} else {
					idCol.setValue(getEntityService().nextId());
				}
				tableModel.setIdColumn(idCol);

				sqlSession.insert("insertTableData", tableModel);

			} else {
				ColumnModel idCol = new ColumnModel();
				idCol.setJavaType(idColumn.getJavaType());
				idCol.setValue(primaryKey);
				tableModel.setIdColumn(idCol);
				sqlSession.update("updateTableDataByPrimaryKey", tableModel);
			}
		}
	}

	/**
	 * 保存JSON数组数据到指定的表
	 * 
	 * @param tableName
	 * @param rows
	 */
	public void saveTableData(String systemName, String tableName, JSONArray rows) {
		if (rows == null || rows.isEmpty()) {
			return;
		}
		com.glaf.core.config.Environment.setCurrentSystemName(systemName);
		TableDefinition tableDefinition = getTableDefinitionService().getTableDefinition(tableName);
		if (tableDefinition != null && tableDefinition.getIdColumn() != null) {
			if (tableDefinition.getTableName() != null) {
				tableDefinition.setTableName(tableDefinition.getTableName().toUpperCase());
			}

			SqlSession sqlSession = null;
			Connection conn = null;
			try {
				conn = DBConnectionFactory.getConnection(systemName);
				conn.setAutoCommit(false);
				sqlSession = getSqlSessionFactory().openSession(ExecutorType.BATCH, conn);
				for (int i = 0, len = rows.size(); i < len; i++) {
					JSONObject jsonObject = rows.getJSONObject(i);
					this.saveTableData(sqlSession, systemName, tableName, jsonObject);
				}
				sqlSession.commit();
				conn.commit();
			} catch (Exception ex) {
				JdbcUtils.rollback(conn);
				logger.error(ex);
				throw new RuntimeException(ex);
			} finally {
				JdbcUtils.close(sqlSession);
				JdbcUtils.close(conn);
			}
		}
	}

	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

	public void setTableDefinitionService(ITableDefinitionService tableDefinitionService) {
		this.tableDefinitionService = tableDefinitionService;
	}

	public void updateTableData(String systemName, List<TableModel> rows) {
		if (rows != null && !rows.isEmpty()) {
			SqlSession sqlSession = null;
			Connection conn = null;
			try {
				conn = DBConnectionFactory.getConnection(systemName);
				conn.setAutoCommit(false);
				sqlSession = getSqlSessionFactory().openSession(ExecutorType.BATCH, conn);
				for (TableModel t : rows) {
					if (t.getTableName() != null) {
						t.setTableName(t.getTableName().toUpperCase());
					}
					sqlSession.update("updateTableDataByPrimaryKey", t);
				}
				sqlSession.commit();
				conn.commit();
			} catch (Exception ex) {
				JdbcUtils.rollback(conn);
				logger.error(ex);
				throw new RuntimeException(ex);
			} finally {
				JdbcUtils.close(sqlSession);
				JdbcUtils.close(conn);
			}
		}
	}

	public void updateTableData(String systemName, String dbType, String tableName, List<Map<String, Object>> rows) {
		com.glaf.core.config.Environment.setCurrentSystemName(systemName);
		TableDefinition tableDefinition = getTableDefinitionService().getTableDefinition(tableName);
		if (tableDefinition != null) {
			if (tableDefinition.getTableName() != null) {
				tableDefinition.setTableName(tableDefinition.getTableName().toUpperCase());
			}
			List<ColumnDefinition> columns = getTableDefinitionService().getColumnDefinitionsByTableName(tableName);
			if (columns != null && !columns.isEmpty()) {
				SqlSession sqlSession = null;
				Connection conn = null;
				try {
					conn = DBConnectionFactory.getConnection(systemName);
					conn.setAutoCommit(false);
					sqlSession = getSqlSessionFactory().openSession(ExecutorType.BATCH, conn);
					Iterator<Map<String, Object>> iterator = rows.iterator();
					while (iterator.hasNext()) {
						TableModel table = new TableModel();
						table.setTableName(tableName);
						Map<String, Object> dataMap = iterator.next();
						for (ColumnDefinition column : columns) {
							String javaType = column.getJavaType();
							String name = column.getColumnName();
							ColumnModel c = new ColumnModel();
							c.setColumnName(name);
							c.setJavaType(javaType);
							Object value = dataMap.get(name);
							if (value == null) {
								value = dataMap.get(name.toLowerCase());
							}
							if (value == null) {
								if (column.getName() != null) {
									value = dataMap.get(column.getName());
									if (value == null) {
										value = dataMap.get(column.getName().toLowerCase());
									}
								}
							}
							if (value != null) {
								if ("Integer".equals(javaType)) {
									value = ParamUtils.getInt(dataMap, name);
								} else if ("Long".equals(javaType)) {
									value = ParamUtils.getLong(dataMap, name);
								} else if ("Double".equals(javaType)) {
									value = ParamUtils.getDouble(dataMap, name);
								} else if ("Date".equals(javaType)) {
									value = ParamUtils.getTimestamp(dataMap, name);
								} else if ("String".equals(javaType)) {
									value = ParamUtils.getString(dataMap, name);
								} else if ("Clob".equals(javaType)) {
									value = ParamUtils.getString(dataMap, name);
								}
								c.setValue(value);
								if (column.isPrimaryKey()) {
									table.setIdColumn(c);
								} else {
									table.addColumn(c);
								}
							}
						}
						sqlSession.update("updateTableDataByPrimaryKey", table);
					}
					sqlSession.commit();
					conn.commit();
				} catch (Exception ex) {
					JdbcUtils.rollback(conn);
					logger.error(ex);
					throw new RuntimeException(ex);
				} finally {
					JdbcUtils.close(sqlSession);
					JdbcUtils.close(conn);
				}
			}
		}
	}

	public void updateTableData(String systemName, TableDefinition tableDefinition, List<Map<String, Object>> rows) {
		if (tableDefinition.getTableName() != null) {
			tableDefinition.setTableName(tableDefinition.getTableName().toUpperCase());
		}
		List<ColumnDefinition> columns = tableDefinition.getColumns();
		if (columns != null && !columns.isEmpty()) {
			SqlSession sqlSession = null;
			Connection conn = null;
			try {
				conn = DBConnectionFactory.getConnection(systemName);
				conn.setAutoCommit(false);
				sqlSession = getSqlSessionFactory().openSession(ExecutorType.BATCH, conn);
				Iterator<Map<String, Object>> iterator = rows.iterator();
				while (iterator.hasNext()) {
					TableModel table = new TableModel();
					table.setTableName(tableDefinition.getTableName());
					Map<String, Object> dataMap = iterator.next();
					for (ColumnDefinition column : columns) {
						String javaType = column.getJavaType();
						String name = column.getColumnName();
						ColumnModel c = new ColumnModel();
						c.setColumnName(name);
						c.setJavaType(javaType);
						Object value = dataMap.get(name);
						if (value == null) {
							value = dataMap.get(name.toLowerCase());
						}
						if (value == null) {
							if (column.getName() != null) {
								value = dataMap.get(column.getName());
								if (value == null) {
									value = dataMap.get(column.getName().toLowerCase());
								}
							}
						}
						if (value != null) {
							if ("Integer".equals(javaType)) {
								value = ParamUtils.getInt(dataMap, name);
							} else if ("Long".equals(javaType)) {
								value = ParamUtils.getLong(dataMap, name);
							} else if ("Double".equals(javaType)) {
								value = ParamUtils.getDouble(dataMap, name);
							} else if ("Date".equals(javaType)) {
								value = ParamUtils.getTimestamp(dataMap, name);
							} else if ("String".equals(javaType)) {
								value = ParamUtils.getString(dataMap, name);
							} else if ("Clob".equals(javaType)) {
								value = ParamUtils.getString(dataMap, name);
							}
							c.setValue(value);
							if (column.isPrimaryKey()) {
								table.setIdColumn(c);
							} else {
								table.addColumn(c);
							}
						}
					}
					sqlSession.update("updateTableDataByPrimaryKey", table);
				}
				sqlSession.commit();
				conn.commit();
			} catch (Exception ex) {
				JdbcUtils.rollback(conn);
				logger.error(ex);
				throw new RuntimeException(ex);
			} finally {
				JdbcUtils.close(sqlSession);
				JdbcUtils.close(conn);
			}
		}
	}

}