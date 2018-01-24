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

package com.glaf.etl.bean;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.datamgr.util.ExceptionUtils;
import com.glaf.etl.domain.ETLDataTransfer;
import com.glaf.etl.domain.EtlDataTarget;
import com.glaf.etl.domain.EtlTransferTask;
import com.glaf.etl.domain.EtlTransferTaskSrc;
import com.glaf.etl.domain.EtlTransferTaskTarget;
import com.glaf.etl.domain.TableColumnGroup;
import com.glaf.etl.service.ETLDataTransferService;
import com.glaf.etl.service.EtlDataTargetService;
import com.glaf.etl.service.EtlTransferTaskService;
import com.glaf.etl.service.EtlTransferTaskSrcService;
import com.glaf.etl.service.EtlTransferTaskTargetService;
import com.glaf.etl.util.RwlDBDialectUtil;

public class RankBean {

	protected static Configuration conf = BaseConfiguration.create();

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public boolean execute(String srcId, String targetId, EtlTransferTask transferTask) {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		ETLDataTransferService etlDataTransferService = ContextFactory
				.getBean("com.glaf.etl.service.eTLDataTransferService");
		EtlTransferTaskService etlTransferTaskService = ContextFactory
				.getBean("com.glaf.etl.service.etlTransferTaskService");
		EtlTransferTaskSrcService etlTransferTaskSrcService = ContextFactory
				.getBean("com.glaf.etl.service.etlTransferTaskSrcService");
		EtlTransferTaskTargetService etlTransferTaskTargetService = ContextFactory
				.getBean("com.glaf.etl.service.etlTransferTaskTargetService");
		EtlDataTargetService etlDataTargetService = ContextFactory.getBean("com.glaf.etl.service.etlDataTargetService");
		// int batchSize = conf.getInt("batchSize", 1000);
		int batchSize = transferTask.getCommitInterval_() == null ? 1000 : transferTask.getCommitInterval_();
		Connection srcConn = null;
		PreparedStatement psmt = null;
		PreparedStatement psmt2 = null;
		ResultSet rs = null;

		Connection targetConn = null;
		PreparedStatement targetPsmt = null;
		Statement targetSmt = null;
		// 转换定义
		ETLDataTransfer etlDataTransfer = null;
		// 任务源
		EtlTransferTaskSrc etlTransferTaskSrc = null;
		// 任务目标
		EtlTransferTaskTarget etlTransferTaskTarget = null;
		// 查询语句
		String querySQL = null;
		// 表字段分组信息
		TableColumnGroup tableColumnGroup = new TableColumnGroup();
		try {
			// 获取转换定义
			etlDataTransfer = etlDataTransferService.getETLDataTransfer(transferTask.getTransId_());

			// 获取任务源定义
			etlTransferTaskSrc = etlTransferTaskSrcService.getEtlTransferTaskSrc(srcId);
			Database srcDatabase = databaseService.getDatabaseById(etlTransferTaskSrc.getTaskConnId_());
			if(srcDatabase==null)
			{
				throw new RuntimeException("任务【"+transferTask.getTaskName_()+"】获取不到源数据库信息");
			}
			srcConn = DBConnectionFactory.getConnection(srcDatabase.getName());
			if(srcConn==null)
			{
				throw new RuntimeException("任务【"+transferTask.getTaskName_()+"】获取源数据库连接失败");
			}
			// 获取任务目标定义
			etlTransferTaskTarget = etlTransferTaskTargetService.getEtlTransferTaskTarget(targetId);
			Database targetDatabase = databaseService.getDatabaseById(etlTransferTaskTarget.getTaskConnId_());
			if(targetDatabase==null)
			{
				throw new RuntimeException("任务【"+transferTask.getTaskName_()+"】获取不到目标数据库信息");
			}
			targetConn = DBConnectionFactory.getConnection(targetDatabase.getName());
			if(targetConn==null)
			{
				throw new RuntimeException("任务【"+transferTask.getTaskName_()+"】获取目标数据库连接失败");
			}
			// 获取源执行前执行SQL
			byte[] preSQL = etlTransferTaskSrc.getPreSQL_();
			if (preSQL != null && preSQL.length > 0) {
				psmt = srcConn.prepareStatement(new String(preSQL,"UTF-8"));
				psmt.execute();
			}
			// 写入目标前执行SQL
			preSQL = etlTransferTaskTarget.getPreSQL_();
			if (preSQL != null && preSQL.length > 0) {
				psmt = targetConn.prepareStatement(new String(preSQL,"UTF-8"));
				targetPsmt.execute();
			}
			
			// 获取查询语句
			byte[] querySQLByte = etlTransferTaskSrc.getQuerySQL_();
			if (querySQLByte != null && querySQLByte.length > 0) {
				querySQL = new String(querySQLByte,"UTF-8");
				querySQL = querySQL.toLowerCase();
				
				
				// 获取总记录数
				int recordCount = getRecordCount(srcConn, querySQL);
				if (recordCount == 0) {
					return true;
				}
				int pageCount = getPageCount(recordCount, batchSize);
				String pageSQL = null;
				JSONArray srcDefColumns = null;
				ResultSetMetaData metaData = null;
				// 运行时查询与定义时源数据列映射关系
				Map<String, String> columnMapping = null;
				// 目标列与转换列映射关系
				Map<String, String> targetColumnMapping = new HashMap<String, String>();
				// 目标列元数据信息
				Map<String, JSONObject> tartgetColumnMeta = new HashMap<String, JSONObject>();
				EtlDataTarget etlDataTarget = null;
				if (!StringUtils.isEmpty(etlDataTransfer.getTargetId())) {
					etlDataTarget = etlDataTargetService.getEtlDataTarget(etlDataTransfer.getTargetId());
				} else {
					etlDataTarget = new EtlDataTarget();
				}
				byte[] targetColumnMetaByte = etlDataTarget.getColumns_();
				byte[] targetColumnMappingByte = etlDataTransfer.getColumnsMapping();
				if (targetColumnMappingByte != null) {
					initTargetColumnMapping(JSONObject.parseObject(new String(targetColumnMappingByte,"UTF-8")),
							targetColumnMapping, targetColumnMetaByte != null
									? JSONArray.parseArray(new String(targetColumnMetaByte,"UTF-8")) : null,
							tartgetColumnMeta);
				}
				int colCount = 0;
				String columnName = null;
				String dbColumnName = null;
				// 当前目标表分组值
				List<String> targetGroupColumnValue = null;
				// 获取分组列
				JSONArray groupCols = JSONArray.parseArray(new String(etlDataTransfer.getGroupColumns(),"UTF-8"));
				// 目标分组列
				String[] targetGroupColumn = new String[groupCols.size()];
				TableDefinition tableDefinition = null;
				for (int pageNo = 0; pageNo < pageCount; pageNo++) {
					pageSQL = getPaginationSql(pageNo * batchSize, 1000, querySQL);
					psmt2 = srcConn.prepareStatement(pageSQL);
					// 获取查询列信息
					if (metaData == null) {
						metaData = psmt2.getMetaData();
						colCount = metaData.getColumnCount();
						// 获取ETL_DATASRC定义源列信息
						srcDefColumns = etlDataTransferService.getSrcDefColumns(etlTransferTaskSrc.getSrcId_());
						columnMapping = new HashMap<String, String>();
						for (int i = 0; i < srcDefColumns.size(); i++) {
							columnName = srcDefColumns.getJSONObject(i).getString("columnName");
							dbColumnName = metaData.getColumnName(i + 2);
							// 运行时查询与定义时源数据列按顺序映射
							if (etlTransferTaskSrc.getOrderMapping_() == 1) {
								columnMapping.put(columnName.toLowerCase(), dbColumnName.toLowerCase());
							} // 运行时查询与定义时源数据列按名称映射
							else {
								columnMapping.put(columnName.toLowerCase(), columnName.toLowerCase());
							}
						}
						// 更新目标表结构
						tableDefinition = updateTargetTable(srcConn,targetConn, targetDatabase, querySQL, etlDataTransfer,
								etlTransferTaskTarget, columnMapping, targetColumnMapping, tartgetColumnMeta,
								tableColumnGroup);
						// 更新目标表元数据
						updateMetaData(targetConn, tableDefinition);
						// 更新转换模板目标定义以及转换列与目标列映射
						updateTransferTarget(etlDataTransferService, etlDataTargetService, etlTransferTaskTargetService,
								etlDataTarget, etlDataTransfer, etlTransferTaskTarget, tartgetColumnMeta,
								targetColumnMapping);
						// 获取当前目标表分组值
						targetGroupColumnValue = getTargetGroupColumnValue(targetConn, tableDefinition.getTableName(),
								targetColumnMapping, groupCols, targetGroupColumn);
						//插入数据前清空数据表
						if(targetConn!=null&&etlTransferTaskTarget!=null&&etlTransferTaskTarget.getPreTuncateFlag_()!=null&&etlTransferTaskTarget.getPreTuncateFlag_()==1)
						{
						   String sql="TRUNCATE TABLE "+tableDefinition.getTableName();
						   targetPsmt=targetConn.prepareStatement(sql);
						   targetPsmt.execute();
						}
					}

					rs = psmt2.executeQuery();
					LinkedHashMap<String, Object> columnValueMap = new LinkedHashMap<String, Object>();
					columnName = null;
					dbColumnName = null;
					String groupColumnVal = null;
					boolean treatInsert = true;
					targetSmt = targetConn.createStatement();
					targetConn.setAutoCommit(false);
					while (rs.next()) {
						for (int i = 0; i < srcDefColumns.size(); i++) {
							columnName = srcDefColumns.getJSONObject(i).getString("columnName");
							dbColumnName = columnMapping.get(columnName.toLowerCase());
							columnValueMap.put(columnName.toLowerCase(), rs.getObject(dbColumnName));
							for (int j = 0; j < groupCols.size(); j++) {
								if (j == 0)
									groupColumnVal = rs.getString(groupCols.getJSONObject(j).getString("columnName"));
								else
									groupColumnVal += "_"
											+ rs.getString(groupCols.getJSONObject(j).getString("columnName"));
							}

						}
						if (targetGroupColumnValue.contains(groupColumnVal)) {
							treatInsert = false;
							saveRecordToTarget(targetSmt, tableDefinition.getTableName(), targetColumnMapping,
									columnValueMap, tableColumnGroup, treatInsert);
						} else {
							treatInsert = true;
							saveRecordToTarget(targetSmt, tableDefinition.getTableName(), targetColumnMapping,
									columnValueMap, tableColumnGroup, treatInsert);
							targetGroupColumnValue.add(groupColumnVal);
						}
					}
					targetSmt.executeBatch();
					targetConn.commit();
					JdbcUtils.close(rs);
					JdbcUtils.close(psmt2);
				}
			}
			return true;
		} catch (

		Exception ex)

		{
			ExceptionUtils.addMsg("table_rank_" + querySQL, ex.getMessage());
			logger.error("任务【"+transferTask.getTaskName_()+"】execute error", ex);
			throw new RuntimeException(ex);
		} finally

		{
			// 关闭目标
			JdbcUtils.close(targetSmt);
			JdbcUtils.close(targetPsmt);
			JdbcUtils.close(targetConn);
			// 关闭源
			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);
			JdbcUtils.close(psmt2);
			JdbcUtils.close(srcConn);
		}

	}

	/**
	 * 获取查询记录数SQL
	 * 
	 * @param querySQL
	 * @return
	 */
	public String getRecordCountSQL(String querySQL) {
		String countSQL = "select count(*) " + querySQL.substring(querySQL.indexOf("from"), querySQL.length());
		return countSQL;
	}

	/**
	 * 获取总记录数
	 * 
	 * @param conn
	 * @param querySQL
	 * @return
	 * @throws SQLException
	 */
	public int getRecordCount(Connection conn, String querySQL) throws SQLException {
		int count = 0;
		String countSQL = getRecordCountSQL(querySQL);
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			psmt = conn.prepareStatement(countSQL);
			rs = psmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("getRecordCount:::" + e.getMessage());
			throw e;
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);
		}
		return count;
	}

	/**
	 * 获取总页面
	 * 
	 * @param count
	 * @param pageSize
	 * @return
	 */
	public int getPageCount(int count, int pageSize) {
		int pageCount = (int) (Math.ceil((double) count / (double) pageSize));
		return pageCount;
	}

	/**
	 * 获取分页语句
	 * 
	 * @param start
	 * @param limit
	 * @param sql
	 * @return
	 */
	public String getPaginationSql(int start, int limit, String sql) {
		RwlDBDialectUtil rwlDBDialectUtil = RwlDBDialectUtil.getInstance();
		rwlDBDialectUtil.setCurrentDialect(RwlDBDialectUtil.dbtype.sqlserver);
		String pageSQL = rwlDBDialectUtil.getSqlLimit(sql, true, start, limit);
		return pageSQL;
	}

	/**
	 * 更新目标表结构
	 * 
	 * @param srcConn
	 *            源表连接
	 * @param querySQL
	 *            查询语句
	 * @param etlDataTransfer
	 *            转换模板对象
	 * @param etlTransferTaskTarget
	 *            转换任务目标对象
	 * @param columnMapping
	 *            转换模板列与转换任务查询列映射
	 * @param targetColumnMapping
	 *            转换模板列与转换目标列映射
	 * @throws SQLException
	 * @throws UnsupportedEncodingException 
	 */
	public TableDefinition updateTargetTable(Connection srcConn,Connection targetConn, Database targetDatabase, String querySQL,
			ETLDataTransfer etlDataTransfer, EtlTransferTaskTarget etlTransferTaskTarget,
			Map<String, String> columnMapping, Map<String, String> targetColumnMapping,
			Map<String, JSONObject> tartgetColumnMeta, TableColumnGroup tableColumnGroup) throws SQLException, UnsupportedEncodingException {
		TableDefinition tableDefinition = new TableDefinition();
		// 创建列
		List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();
		// 获取分组列
		JSONArray groupCols = JSONArray.parseArray(new String(etlDataTransfer.getGroupColumns(),"UTF-8"));

		// 获取转换列
		JSONArray transCol = JSONArray.parseArray(new String(etlDataTransfer.getTransferColumns(),"UTF-8"));
		// createTableColumns(groupCols,columns);
		// 获取值列
		JSONArray valCol = JSONArray.parseArray(new String(etlDataTransfer.getValueColumns(),"UTF-8"));
		// 获取转换列值
		List<String> rankCols = null;
		try {
			// 检查目标表是否存在
			String tableName = null;
			if (StringUtils.isEmpty(etlTransferTaskTarget.getTargetId_())) {
				// 新建表
				//tableName = "ETL_RANK_" + etlDataTransfer.getId();
				tableName=getNewTableName(targetConn);
				tableDefinition.setTableName(tableName);
				etlTransferTaskTarget.setTaskTableName_(tableName);
			} else {
				tableName = etlTransferTaskTarget.getTaskTableName_();
				tableDefinition.setTableName(tableName);
			}
			tableDefinition.setTitle(etlDataTransfer.getTransName());
			createTableColumns(groupCols, columns, targetColumnMapping, tableName, tartgetColumnMeta, tableColumnGroup);
			rankCols = getRankColumnValues(srcConn, querySQL, transCol, columnMapping, tableColumnGroup);
			createTableRankColumns(rankCols, valCol, columns, targetColumnMapping, tableName, tartgetColumnMeta,
					tableColumnGroup);
			tableDefinition.setColumns(columns);
			// 检测现有表是否存在
			if (DBUtils.tableExists(targetDatabase.getName(), etlTransferTaskTarget.getTaskTableName_())) {
				DBUtils.alterTable(targetDatabase.getName(), tableDefinition);
			} else {
				DBUtils.createTable(targetDatabase.getName(), tableDefinition);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			throw e;
		}
		return tableDefinition;
	}

	public void updateMetaData(Connection targetConn, TableDefinition tableDefinition) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			targetConn.setAutoCommit(false);
			String tableName = tableDefinition.getTableName().toLowerCase();
			String sql = "delete from cell_data_table where id='" + tableName + "'";
			pstmt = targetConn.prepareStatement(sql);
			pstmt.execute();
			sql = "insert into cell_data_table (id,tablename,index_id,name,addtype,userid,ctime,content,issubtable) values(?,?,?,?,?,?,?,?,?)";
			pstmt = targetConn.prepareStatement(sql);
			pstmt.setString(1, tableName);
			pstmt.setString(2, tableName);
			pstmt.setInt(3, 1);
			pstmt.setString(4, tableDefinition.getTitle());
			pstmt.setInt(5, 3);
			pstmt.setString(6, "system");
			java.sql.Date date = new java.sql.Date(new Date().getTime());
			pstmt.setDate(7, date);
			pstmt.setString(8, tableDefinition.getTitle());
			pstmt.setInt(9, 0);
			pstmt.execute();
			// 删除字段定义
			sql = "delete from interface where frmtype='" + tableName + "'";
			pstmt = targetConn.prepareStatement(sql);
			pstmt.execute();
			// 插入字段定义
			sql = "insert into interface(frmtype,listId,issystem,fname,dname,dtype,strlen,intype,listno,isListShow) values(?,?,1,?,?,?,-1,'edt',?,1);";
			pstmt = targetConn.prepareStatement(sql);
			int seq = 1;
			String dType = null;
			for (ColumnDefinition columnDefinition : tableDefinition.getColumns()) {
				pstmt.setString(1, tableName);
				pstmt.setString(2, tableName + "_" + seq);
				pstmt.setString(3, columnDefinition.getColumnLabel());
				pstmt.setString(4, columnDefinition.getColumnName());
				// 类型
				dType = getCellDataTypeByJavaType(columnDefinition.getJavaType());
				pstmt.setString(5, dType);
				pstmt.setInt(6, seq);
				pstmt.addBatch();
				seq++;
			}
			pstmt.executeBatch();
			targetConn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error(e.getMessage());
			throw e;
		} finally {
			pstmt.close();
			targetConn.setAutoCommit(true);
		}

	}

	/**
	 * 获取Cell表数据类型
	 * 
	 * @param javaType
	 * @return
	 */
	public String getCellDataTypeByJavaType(String javaType) {

		String dataType = null;
		switch (javaType) {
		case "Short":
			dataType = "i4";
			break;
		case "Long":
			dataType = "i4";
			break;
		case "String":
			dataType = "string";
			break;
		case "Date":
			dataType = "datetime";
			break;
		case "Timestamp":
			dataType = "datetime";
			break;
		case "Time":
			dataType = "datetime";
			break;
		case "Integer":
			dataType = "i4";
			break;
		case "BigDecimal":
			dataType = "r8";
			break;
		case "Float":
			dataType = "r8";
			break;
		case "Double":
			dataType = "r8";
			break;
		case "Boolean":
			dataType = "char";
			break;
		default:
			dataType = "string";
			break;
		}
		return dataType;
	}

	public String getTableDefinitionColumnDataTypeByJavaType(String javaType) {

		String dataType = null;
		switch (javaType) {
		case "Short":
			dataType = "Integer";
			break;
		case "Long":
			dataType = "Long";
			break;
		case "String":
			dataType = "String";
			break;
		case "Date":
			dataType = "Date";
			break;
		case "Timestamp":
			dataType = "Date";
			break;
		case "Time":
			dataType = "Date";
			break;
		case "Integer":
			dataType = "Integer";
			break;
		case "BigDecimal":
			dataType = "Double";
			break;
		case "Float":
			dataType = "Double";
			break;
		case "Double":
			dataType = "Double";
			break;
		case "Boolean":
			dataType = "String";
			break;
		default:
			dataType = "String";
			break;
		}
		return dataType;
	}
	/**
	 * 获取转换列内容（列转行 生成行列名称）
	 * 
	 * @param srcConn
	 * @param valCol
	 * @param columnMapping
	 * @return
	 * @throws SQLException
	 */
	public List<String> getRankColumnValues(Connection srcConn, String querySQL, JSONArray transCol,
			Map<String, String> columnMapping, TableColumnGroup tableColumnGroup) throws SQLException {
		if (transCol == null || columnMapping.isEmpty()) {
			return null;
		}
		List<String> columnValues = new ArrayList<String>();
		// 获取定义的转换列
		JSONObject defColObj = transCol.getJSONObject(0);
		if (defColObj == null) {
			return null;
		}
		String defTransColumnName = defColObj.getString("columnName");
		if (StringUtils.isEmpty(defTransColumnName)) {
			return null;
		}
		tableColumnGroup.setSrcTransColumn(defTransColumnName.toLowerCase());
		// 获取运行任务查询的转换列
		String taskTransColumnName = columnMapping.get(defTransColumnName.toLowerCase());
		if (StringUtils.isEmpty(taskTransColumnName)) {
			return null;
		}
		String getRankColumnNameSQL = "select distinct " + taskTransColumnName + " from (" + querySQL + ") a";
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			psmt = srcConn.prepareStatement(getRankColumnNameSQL);
			rs = psmt.executeQuery();
			while (rs.next()) {
				columnValues.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error(e.getMessage());
			throw e;
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);
		}
		return columnValues;
	}

	/**
	 * 创建列定义
	 * 
	 * @param cols
	 */
	public void createTableColumns(JSONArray cols, List<ColumnDefinition> columns,
			Map<String, String> targetColumnMapping, String tableName, Map<String, JSONObject> tartgetColumnMeta,
			TableColumnGroup tableColumnGroup) {
		if (cols != null) {
			JSONObject jsonObj = null;
			ColumnDefinition colDef = null;
			String columnName = null;
			String javaType = null;
			List<String> columnList = new ArrayList<String>();
			for (int i = 0; i < cols.size(); i++) {
				jsonObj = cols.getJSONObject(i);
				colDef = new ColumnDefinition();
				columnList.add(jsonObj.getString("columnName").toLowerCase());
				if (targetColumnMapping.containsKey(jsonObj.getString("columnName").toLowerCase())) {
					columnName = targetColumnMapping.get(jsonObj.getString("columnName").toLowerCase());
				} else {
					// 生成目标表列名
					columnName = "col_" + new Date().getTime() + i + 1;
					targetColumnMapping.put(jsonObj.getString("columnName").toLowerCase(), columnName.toLowerCase());
				}
				javaType = jsonObj.getString("javaType").lastIndexOf(".") > -1
						? jsonObj.getString("javaType").substring(jsonObj.getString("javaType").lastIndexOf(".") + 1,
								jsonObj.getString("javaType").length())
						: jsonObj.getString("javaType");
				javaType=getTableDefinitionColumnDataTypeByJavaType(javaType);
				colDef.setColumnName(columnName.toLowerCase());
				colDef.setColumnLabel(jsonObj.getString("columnNameCN"));
				colDef.setJavaType(javaType);
				colDef.setLength(jsonObj.getIntValue("size"));
				colDef.setScale(jsonObj.getIntValue("scale"));
				colDef.setPrecision(jsonObj.getIntValue("precision"));
				columns.add(colDef);
				if (!tartgetColumnMeta.containsKey(columnName.toLowerCase())) {
					jsonObj.put("columnName", colDef.getColumnName());
					jsonObj.put("columnNameCN", colDef.getColumnLabel());
					tartgetColumnMeta.put(columnName.toLowerCase(), jsonObj);
				}

			}
			tableColumnGroup.setSrcGroupColumn(columnList);
		}
	}

	/**
	 * 创建转换列定义
	 * 
	 * @param columnNames
	 * @param valCol
	 * @param columns
	 */
	public void createTableRankColumns(List<String> columnNames, JSONArray valCol, List<ColumnDefinition> columns,
			Map<String, String> targetColumnMapping, String tableName, Map<String, JSONObject> tartgetColumnMeta,
			TableColumnGroup tableColumnGroup) {
		if (valCol != null && columnNames.size() > 0) {
			JSONObject jsonObj = valCol.getJSONObject(0);
			String columnLabel = jsonObj.getString("columnNameCN");
			String javaType = jsonObj.getString("javaType").lastIndexOf(".") > -1
					? jsonObj.getString("javaType").substring(jsonObj.getString("javaType").lastIndexOf(".") + 1,
							jsonObj.getString("javaType").length())
					: jsonObj.getString("javaType");
			javaType=getTableDefinitionColumnDataTypeByJavaType(javaType);
			int size = jsonObj.getIntValue("size");
			int scale = jsonObj.getIntValue("scale");
			int precision = jsonObj.getIntValue("precision");
			tableColumnGroup.setSrcValColumn(jsonObj.getString("columnName").toLowerCase());
			ColumnDefinition colDef = null;
			String nColumnName = null;
			int i = 0;
			for (String columnName : columnNames) {
				colDef = new ColumnDefinition();
				if (targetColumnMapping.containsKey(columnName.toLowerCase())) {
					nColumnName = targetColumnMapping.get(columnName.toLowerCase());
				} else {
					// 生成目标表列名
					nColumnName = "col_" + new Date().getTime() + i + 1;
					targetColumnMapping.put(columnName.toLowerCase(), nColumnName.toLowerCase());
					i++;
				}
				colDef.setColumnName(nColumnName);
				colDef.setColumnLabel(columnName.toLowerCase());
				colDef.setJavaType(javaType);
				colDef.setLength(size);
				colDef.setScale(scale);
				colDef.setPrecision(precision);
				columns.add(colDef);
				if (!tartgetColumnMeta.containsKey(nColumnName.toLowerCase())) {
					JSONObject njsonObj = JSONObject.parseObject(jsonObj.toJSONString());
					njsonObj.put("columnName", colDef.getColumnName());
					njsonObj.put("columnNameCN", colDef.getColumnLabel());
					tartgetColumnMeta.put(nColumnName.toLowerCase(), njsonObj);
				}

			}
		}
	}

	/**
	 * 初始化目标列与转换列间的映射关系
	 * 
	 * @return
	 */
	public void initTargetColumnMapping(JSONObject columnMappingJSON, Map<String, String> targetColumnMapping,
			JSONArray tartgetColumnJSONArray, Map<String, JSONObject> tartgetColumnMeta) {
		Set<String> transColumnSet = columnMappingJSON.keySet();
		for (String transColmn : transColumnSet) {
			targetColumnMapping.put(transColmn, columnMappingJSON.getString(transColmn));
		}
		if (tartgetColumnJSONArray != null) {
			for (int i = 0; i < tartgetColumnJSONArray.size(); i++) {
				tartgetColumnMeta.put(tartgetColumnJSONArray.getJSONObject(i).getString("columnName"),
						tartgetColumnJSONArray.getJSONObject(i));
			}
		}
	}
    /**
     * 获取表名
     * @param targetConn
     * @return
     * @throws SQLException
     */
	public String getNewTableName(Connection targetConn) throws SQLException {
		String sql = "select top 1 tablename from cell_data_table where tablename like 'etl_rank_%' order by len(tablename) desc,tablename desc";
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String tableName = null;
		int seq = 1;
		try {
			psmt = targetConn.prepareStatement(sql);
			rs = psmt.executeQuery();
			if (rs.next()) {
				tableName = rs.getString(1);
				seq = Integer.parseInt(tableName.substring(tableName.lastIndexOf("_") + 1, tableName.length())) + 1;
			}
			tableName = "etl_rank_" + seq;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error(e.getMessage());
			throw e;
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);
		}
		return tableName;
	}

	/**
	 * 更新转换模板目标定义以及目标列与转换列映射
	 * 
	 * @param etlDataTargetService
	 * @param etlDataTarget
	 * @param etlDataTransfer
	 * @param etlTransferTaskTarget
	 * @param tartgetColumnMeta
	 * @throws UnsupportedEncodingException 
	 */
	public void updateTransferTarget(ETLDataTransferService etlDataTransferService,
			EtlDataTargetService etlDataTargetService, EtlTransferTaskTargetService etlTransferTaskTargetService,
			EtlDataTarget etlDataTarget, ETLDataTransfer etlDataTransfer, EtlTransferTaskTarget etlTransferTaskTarget,
			Map<String, JSONObject> tartgetColumnMeta, Map<String, String> targetColumnMapping) throws UnsupportedEncodingException {

		// 根据column生成columnJSON
		JSONArray columnJsonArr = new JSONArray();
		if (tartgetColumnMeta != null && tartgetColumnMeta.size() > 0) {
			columnJsonArr.addAll(tartgetColumnMeta.values());
		}
		etlDataTarget.setColumns_(columnJsonArr.toJSONString().getBytes());
		etlDataTarget.setDatabaseId_(etlTransferTaskTarget.getTaskConnId_());
		etlDataTarget.setDatabaseName_(etlTransferTaskTarget.getTaskDatabaseName_());
		if (StringUtils.isEmpty(etlDataTransfer.getTargetId())) {
			etlDataTarget.setCreateBy_("admin");
			etlDataTarget.setCreateTime_(new Date());
		}
		etlDataTarget.setUpdateBy_("admin");
		etlDataTarget.setUpdateTime_(new Date());
		etlDataTarget.setTableName_(etlTransferTaskTarget.getTaskTableName_());
		etlDataTarget.setTargetName_(etlDataTransfer.getTransName() + "-目标");
		etlDataTargetService.save(etlDataTarget);

		// 更新目标列与转换列映射
		JSONObject mappingJSON = new JSONObject();
		//mappingJSON.fluentPutAll(targetColumnMapping);
		mappingJSON.putAll(targetColumnMapping);
		etlDataTransferService.updateColumnMapping(etlDataTarget.getTargetId_(), etlDataTransfer.getId(),
				mappingJSON.toJSONString(), "admin");

		etlTransferTaskTarget.setTargetId_(etlDataTarget.getTargetId_());
		// 更新任务目标
		etlTransferTaskTarget.setUpdateBy_("admin");
		etlTransferTaskTarget.setUpdateTime_(new Date());
		etlTransferTaskTargetService.save(etlTransferTaskTarget);
	}

	/**
	 * 获取目标分组值（用于缓慢维数据处理）
	 * 
	 * @param targetConn
	 * @param tableName
	 * @param targetColumnMapping
	 * @param groupCols
	 * @return
	 */
	public List<String> getTargetGroupColumnValue(Connection targetConn, String tableName,
			Map<String, String> targetColumnMapping, JSONArray groupCols, String[] targetGroupColumn) {
		List<String> targetGroupColumnValue = new ArrayList<String>();
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select distinct ");
		String srcColumnName = null;
		String targetColumnName = null;
		for (int i = 0; i < groupCols.size(); i++) {
			srcColumnName = groupCols.getJSONObject(i).getString("columnName");
			targetColumnName = targetColumnMapping.get(srcColumnName.toLowerCase());
			targetGroupColumn[i] = targetColumnName;
			if (i > 0)
				sqlBuf.append("," + targetColumnName);
			else
				sqlBuf.append(targetColumnName);
		}
		sqlBuf.append(" from " + tableName);
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String groupColumnVal = null;
		try {
			psmt = targetConn.prepareStatement(sqlBuf.toString());
			rs = psmt.executeQuery();
			while (rs.next()) {
				for (int j = 0; j < groupCols.size(); j++) {
					if (j == 0)
						groupColumnVal = rs.getString(j + 1);
					else
						groupColumnVal += "_" + rs.getString(j + 1);
				}
				targetGroupColumnValue.add(groupColumnVal);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);
		}

		return targetGroupColumnValue;

	}

	public void saveRecordToTarget(Statement pstm, String tableName, Map<String, String> targetColumnMapping,
			LinkedHashMap<String, Object> columnValueMap, TableColumnGroup tableColumnGroup, boolean treatInsert)
					throws SQLException {
		StringBuffer sqlBuffer = new StringBuffer();
		List<String> srcGroupColumn = tableColumnGroup.getSrcGroupColumn();
		String srcTransColumn = tableColumnGroup.getSrcTransColumn();
		String srcValColumn = tableColumnGroup.getSrcValColumn();
		if (srcGroupColumn == null || StringUtils.isEmpty(srcTransColumn) || StringUtils.isEmpty(srcValColumn)) {
			return;
		}
		if (treatInsert) {
			StringBuffer columnStr = new StringBuffer();
			StringBuffer columnValStr = new StringBuffer();
			sqlBuffer.append("insert " + tableName);
			Object val = null;
			String strValue;
			String srcColumnName = null;
			String targetColumnName = null;
			for (Entry<String, Object> entry : columnValueMap.entrySet()) {
				val = entry.getValue();
				srcColumnName = entry.getKey();
				if (srcGroupColumn.contains(srcColumnName)) {
					targetColumnName = targetColumnMapping.get(srcColumnName);
				} else if (srcColumnName.equals(srcTransColumn)) {
					targetColumnName = targetColumnMapping.get("" + val);
					//
					val = columnValueMap.get(srcValColumn);
				} else {
					continue;
				}
				if (val instanceof String || val instanceof java.sql.Date|| val instanceof java.sql.Timestamp) {
					// if we have a String, include '' in the saved value
					strValue = "'" + val + "'";
				} else {
					if (val == null) {
						// convert null to the string null
						strValue = "null";
					} else {
						// unknown object (includes all Numbers), just call
						// toString
						strValue = val.toString();
					}
				}
				if (columnStr.length() == 0) {
					columnStr.append(targetColumnName);
					columnValStr.append(strValue);
				} else {
					columnStr.append("," + targetColumnName);
					columnValStr.append("," + strValue);
				}
			}
			sqlBuffer.append(" (");
			sqlBuffer.append(columnStr);
			sqlBuffer.append(") ");
			sqlBuffer.append(" values (");
			sqlBuffer.append(columnValStr);
			sqlBuffer.append(")");

		} else {
			Object val = null;
			String strValue;
			StringBuffer whereClause = new StringBuffer();
			sqlBuffer.append("update " + tableName + " set ");
			String targetValCoulum = targetColumnMapping.get("" + columnValueMap.get(srcTransColumn));
			sqlBuffer.append(targetValCoulum);
			sqlBuffer.append("=");
			val = columnValueMap.get(srcValColumn);
			if (val instanceof String || val instanceof java.sql.Date) {
				// if we have a String, include '' in the saved value
				strValue = "'" + val + "'";
			} else {
				if (val == null) {
					// convert null to the string null
					strValue = "null";
				} else {
					// unknown object (includes all Numbers), just call
					// toString
					strValue = val.toString();
				}
			}
			sqlBuffer.append(strValue);
			String targetColumn = null;

			for (String groupColumn : srcGroupColumn) {
				targetColumn = targetColumnMapping.get(groupColumn);
				val = columnValueMap.get(groupColumn);
				if (val instanceof String || val instanceof java.sql.Date|| val instanceof java.sql.Timestamp) {
					// if we have a String, include '' in the saved value
					strValue = "'" + val + "'";
				} else {
					if (val == null) {
						// convert null to the string null
						strValue = "null";
					} else {
						// unknown object (includes all Numbers), just call
						// toString
						strValue = val.toString();
					}
				}
				if (whereClause.length() == 0)
					whereClause.append(targetColumn + "=" + strValue);
				else
					whereClause.append(" and " + targetColumn + "=" + strValue);
			}
			sqlBuffer.append(" where ");
			sqlBuffer.append(whereClause);
		}
		pstm.addBatch(sqlBuffer.toString());
	}
}
