package com.glaf.datamgr.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Schema;
import org.apache.metamodel.schema.Table;

import com.alibaba.fastjson.JSON;
import com.glaf.core.config.Environment;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.datamgr.bean.SqliteDBHelper;

public class Sqlite2DataBase {

	private static Set<String> initTableSet;

	public static final String METADATA = "metadata.db";;

	static {
		String[] initTables = new String[] { "INTERFACE", "CELL_DATA_FIELD", //
				"CELL_DATA_TABLE", "R_DATA_TABLE", "R_DATA_FIELD", "R_INTERFACE" };

		initTableSet = new HashSet<String>();
		for (String t : initTables) {
			initTableSet.add(t);
		}
	}

	public static void main(String[] args) throws SQLException {

		String db = METADATA;

		String[] tbs = new String[] { "cell_useradd9202", "cell_useradd9206", "cell_useradd9207", "cell_useradd2020",
				"cell_useradd2080" };

		transDataToSqlite(Arrays.asList(tbs), db);

		// refreshMetaDataBySqlite("metadata.db");

		// test();
	}

	private static boolean filter(String tableName) {
		return StringUtils.startsWith(tableName, "cell_useradd".toUpperCase());
	}

	static void test() {

		String db = METADATA;

		Connection targetConnection = DBConnectionFactory.getConnection();

		// DataSource dataSource = SqliteFactory.getDataSource(db);

		DataContext dc = new JdbcDataContext(targetConnection);

		Schema schema = dc.getDefaultSchema();

		Table[] tables = schema.getTables();
		String tableName;
		List<String> tableNames = new ArrayList<String>();
		for (Table table : tables) {
			tableName = table.getName().toUpperCase();
			if (filter(tableName)) {
				tableNames.add(table.getName());
			}
		}
		transDataToSqlite(tableNames, db);
	}

	public static void refreshMetaDataBySqlite(String db, String systemName) throws SQLException {

		if (StringUtils.isBlank(db)) {
			db = METADATA;
		}

		if (StringUtils.isBlank(systemName)) {
			systemName = Environment.DEFAULT_SYSTEM_NAME;
		}

		DataSource dataSource = SqliteFactory.getDataSource(db);

		Connection targetConnection = DBConnectionFactory.getConnection(systemName);

		// cell_data_table

		String sourceTable = "cell_data_table", tableNameKey = "TABLENAME", //
				tableDefinitionKey = "tableDefinition".toUpperCase();

		String sql = String.format("SELECT %s ,%s FROM %s ", //
				tableNameKey, tableDefinitionKey, sourceTable);
		try {
			List<Map<String, Object>> list = DBAM.//
					executeQuery(dataSource, sql, new ArrayList<Object>());

			if (CollectionUtils.isNotEmpty(list)) {
				String tableName, tableDefinitionStr;
				TableDefinition tableDefinition;
				for (Map<String, Object> data : list) {
					tableName = MapUtils.getString(data, tableNameKey);
					tableDefinitionStr = MapUtils.getString(data, tableDefinitionKey);
					tableDefinition = JSON.parseObject(tableDefinitionStr, TableDefinition.class);
					if (!DBUtils.tableExists(targetConnection, tableName)) { // 表格不存在创建
						DBUtils.createTable(targetConnection, tableDefinition);
					} else {
						DBUtils.alterTable(targetConnection, tableDefinition);
					}
				}
				if (!targetConnection.getAutoCommit())
					targetConnection.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			JdbcUtils.close(targetConnection);
		}
	}

	/**
	 * 根据sqlite刷新表结构
	 * 
	 * @param db
	 * @throws SQLException
	 */
	public static void refreshMetaDataBySqlite(String db) throws SQLException {
		refreshMetaDataBySqlite(db, null);
	}

	/**
	 * 根据表名加载元数据到sqlite
	 * 
	 * @param tableName
	 */
	public static void transDataToSqlite(List<String> tableNames, String db, String systemName) {
		if (StringUtils.isBlank(db)) {
			db = METADATA;
		}
		if (StringUtils.isBlank(systemName)) {
			systemName = Environment.DEFAULT_SYSTEM_NAME;
		}
		
		/**
		 * 先清空sqlite
		 */
		try {
			String path = SystemProperties.getConfigRootPath() + "/db/" + db;
			FileUtils.deleteFile(path);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		Connection sourceConnection = null, targetConnection = null;
		try {
			sourceConnection = DBConnectionFactory.getConnection(systemName);
			targetConnection = SqliteFactory.getDataSource(db).getConnection();
			initTables(sourceConnection, targetConnection, initTableSet, true);
			for (String tableName : tableNames) {
				transDataToSqlite_(sourceConnection, targetConnection, tableName);
			}
			if (!targetConnection.getAutoCommit())
				targetConnection.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JdbcUtils.close(sourceConnection);
			JdbcUtils.close(targetConnection);
		}
	}
	/**
	 * 根据表名加载元数据到sqlite
	 * 
	 * @param tableName
	 */
	public static void transDataToSqlite(List<String> tableNames, String db) {
		transDataToSqlite(tableNames, db, null);
	}

	/**
	 * 传输表元数据
	 * 
	 * @param sourceConnection
	 * @param targetConnection
	 * @param tableName
	 * @throws SQLException
	 */
	public static void transDataToSqlite_(Connection sourceConnection, //
			Connection targetConnection, String tableName) throws SQLException {
		if (!DBUtils.tableExists(sourceConnection, tableName)) {
			return;
		}
		TableDefinition tableDefinition = createTableLike(sourceConnection, targetConnection, tableName, false);

		// cell_data_table
		String sourceTable = "cell_data_table";
		String sql = String.format("SELECT * FROM %s where tablename = ?", sourceTable);

		List<Object> parameters = new ArrayList<Object>();
		parameters.add(tableName);
		List<Map<String, Object>> list = DBAM.executeQuery(sourceConnection, sql, parameters);

		Map<String, Object> row = null;
		if (CollectionUtils.isEmpty(list)) {
			row = new HashMap<String, Object>();
			row.put("tableName", tableName);
		} else {
			row = list.get(0);
		}
		Object tableId = row.get("id");

		String value = "";

		try {
			value = JSON.toJSONString(tableDefinition);
		} catch (Exception e) {
			e.printStackTrace();
		}

		row.put("tableDefinition".toUpperCase(), value);

		DBAM.insertToTable(targetConnection, sourceTable, row);

		if (tableId != null) {
			// cell_data_field
			sourceTable = "cell_data_field";
			sql = String.format("SELECT * FROM %s where tableid = ?", sourceTable);

			parameters = new ArrayList<Object>();
			parameters.add(tableId);
			list = DBAM.executeQuery(sourceConnection, sql, parameters);
			DBAM.insertToTable(targetConnection, sourceTable, list);

			// interface
			sourceTable = "interface";
			sql = String.format("SELECT * FROM %s where frmtype = ?", "interface");
			list = DBAM.executeQuery(sourceConnection, sql, parameters);
			DBAM.insertToTable(targetConnection, sourceTable, list);
		}

	}

	/**
	 * 初始化表
	 * 
	 * @param sourceDataContext
	 * @param targetDataContext
	 * @param tables
	 */
	public static void initTables(Connection sourceConnection, //
			Connection targetConnection, Set<String> tables, boolean drop) {
		for (String tableName : tables) {
			createTableLike(sourceConnection, targetConnection, tableName, drop);
		}
	}

	/**
	 * 创建元数据表
	 * 
	 * @param targetDataContext
	 * @param tableLike
	 * @param drop
	 */
	public static TableDefinition createTableLike(Connection sourceConnection, //
			Connection targetConnection, String tableName, final boolean drop) {
		List<ColumnDefinition> columns = DBUtils.getColumnDefinitions(sourceConnection, tableName);
		if (CollectionUtils.isEmpty(columns)) {
			return null;
		}
		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition.setTableName(tableName);
		tableDefinition.setColumns(columns);
		if (StringUtils.equalsIgnoreCase(tableName, "cell_data_table")) {
			ColumnDefinition column = new ColumnDefinition();
			column.setColumnName("tableDefinition".toUpperCase());
			column.setJavaType("String");
			tableDefinition.addColumn(column);
		}
		if (drop)
			DBUtils.dropAndCreateTable(targetConnection, tableDefinition);
		return tableDefinition;
	}

	public static class SqliteFactory {
		public static DataSource getDataSource(String db) {
			SqliteDBHelper helper = new SqliteDBHelper();
			return helper.getDataSource(db);
		}
	}

}
