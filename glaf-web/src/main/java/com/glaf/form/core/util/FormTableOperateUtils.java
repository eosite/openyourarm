package com.glaf.form.core.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.DBConfiguration;
import com.glaf.core.dialect.Dialect;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.BulkInsertBean;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;

public class FormTableOperateUtils {
	Map<String, Connection> conMap = new HashMap();

	public FormTableOperateUtils() {
		// TODO Auto-generated constructor stub
	}

	public List<Map<String, Object>> queryTable(String databaseid, String sql) {
		Connection conn = null;
		QueryRunner qr = new QueryRunner(true);
		List<Map<String, Object>> datas = new ArrayList();
		try {
			conn = DBConnectionFactory.getConnection(databaseid);
			datas = qr.query(conn, sql, new MapListHandler());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.close(conn);
		}
		return datas;
	}

	public Long queryCount(String databaseid, String sql) {
		Long total = 0L;
		Connection conn = null;
		QueryRunner qr = new QueryRunner(true);
		List<Map<String, Object>> datas = new ArrayList();
		try {
			conn = DBConnectionFactory.getConnection(databaseid);
			
			total = qr.query(conn, new StringBuffer("SELECT COUNT(*) total from (").append(sql).append(" )num").toString(),
					new ResultSetHandler<Long>(){
						public Long handle(ResultSet arg0) throws SQLException {
							Long total = 0L;
							if(arg0.next()){
								total = arg0.getLong(1);
							}
							return total;
						}});
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.close(conn);
		}
		return total;
	}

	/**
	 * 
	 * @param databaseid
	 * @param sql
	 * @param offset
	 *            跳过数
	 * @param limit
	 *            查询数（每页条数）
	 * @return
	 */
	public List<Map<String, Object>> queryTable(String databaseid, String sql, int offset, int limit) {
		Connection conn = null;
		QueryRunner qr = new QueryRunner(true);
		List<Map<String, Object>> datas = new ArrayList();
		try {
			conn = DBConnectionFactory.getConnection(databaseid);
			Dialect dialect = DBConfiguration.getDatabaseDialect(conn);
			sql = dialect.getLimitString(sql, offset, limit);
			datas = qr.query(conn, sql, new MapListHandler());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.close(conn);
		}
		return datas;
	}

	/**
	 * 自动批量插入，自动获取conn
	 * 
	 * @param databaseid
	 * @param sql
	 * @param offset
	 * @param limit
	 * @return
	 */
	public boolean bulkInsertTableAuto(String databaseId, String tableName, List<Map<String, Object>> datalist) {
		Connection conn = null;
		boolean flag = false;
		try {
			conn = DBConnectionFactory.getConnection(databaseId);
			conn.setAutoCommit(false);
			BulkInsertBean bean = new BulkInsertBean();

			// 设置表信息
			TableDefinition tableDefinition = new TableDefinition();
			tableDefinition.setTableName(tableName);
			tableDefinition.setName(tableName);
			// 设置表字段信息
			List<ColumnDefinition> columnDefinitions = DBUtils.getColumnDefinitions(databaseId, tableName);
			tableDefinition.setColumns(columnDefinitions);

			bean.bulkInsert(conn, tableDefinition, datalist);
			flag = true;
			conn.commit();
		} catch (Exception e) {

		} finally {
			JdbcUtils.close(conn);
		}

		return flag;
	}

	/**
	 * 手动批量插入,传conn进来,自己关闭conn。
	 * 
	 * @param databaseid
	 * @param sql
	 * @param offset
	 * @param limit
	 * @return
	 */
	public boolean bulkInsertTable(Connection conn, String databaseId, String tableName,
			List<Map<String, Object>> datalist) throws Exception {
		boolean flag = false;
		BulkInsertBean bean = new BulkInsertBean();

		// 设置表信息
		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition.setTableName(tableName);
		tableDefinition.setName(tableName);
		// 设置表字段信息
		List<ColumnDefinition> columnDefinitions = DBUtils.getColumnDefinitions(databaseId, tableName);
		tableDefinition.setColumns(columnDefinitions);

		bean.bulkInsert(conn, tableDefinition, datalist);
		flag = true;

		return flag;
	}
	
}
