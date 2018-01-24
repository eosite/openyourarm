package com.glaf.datamgr.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;

import com.glaf.core.util.JdbcUtils;

public class DBAM {

	public static int executeUpdate(DataSource dataSource, String sql, Object... parameters) throws SQLException {
		return executeUpdate(dataSource, sql, Arrays.asList(parameters));
	}

	public static int executeUpdate(DataSource dataSource, String sql, List<Object> parameters) throws SQLException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			return executeUpdate(conn, sql, parameters);
		} finally {
			JdbcUtils.close(conn);
		}
	}

	public static int executeUpdate(Connection conn, String sql, List<Object> parameters) throws SQLException {
		PreparedStatement stmt = null;

		int updateCount;
		try {
			stmt = conn.prepareStatement(sql);

			setParameters(stmt, parameters);

			updateCount = stmt.executeUpdate();
		} finally {
			JdbcUtils.close(stmt);
		}

		return updateCount;
	}

	public static void execute(DataSource dataSource, String sql, Object... parameters) throws SQLException {
		execute(dataSource, sql, Arrays.asList(parameters));
	}

	public static void execute(DataSource dataSource, String sql, List<Object> parameters) throws SQLException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			execute(conn, sql, parameters);
		} finally {
			JdbcUtils.close(conn);
		}
	}

	public static void execute(Connection conn, String sql, List<Object> parameters) throws SQLException {
		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(sql);

			setParameters(stmt, parameters);

			stmt.executeUpdate();
		} finally {
			JdbcUtils.close(stmt);
		}
	}

	public static List<Map<String, Object>> executeQuery(DataSource dataSource, String sql, Object... parameters)
			throws SQLException {
		return executeQuery(dataSource, sql, Arrays.asList(parameters));
	}

	public static List<Map<String, Object>> executeQuery(DataSource dataSource, String sql, List<Object> parameters)
			throws SQLException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			return executeQuery(conn, sql, parameters);
		} finally {
			JdbcUtils.close(conn);
		}
	}

	public static List<Map<String, Object>> executeQuery(Connection conn, String sql, List<Object> parameters)
			throws SQLException {
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql); 

			setParameters(stmt, parameters);

			rs = stmt.executeQuery();

			ResultSetMetaData rsMeta = rs.getMetaData();

			while (rs.next()) {
				Map<String, Object> row = new LinkedHashMap<String, Object>();

				for (int i = 0, size = rsMeta.getColumnCount(); i < size; ++i) {
					String columName = rsMeta.getColumnLabel(i + 1);
					Object value = rs.getObject(i + 1);
					row.put(columName, value);
				}

				rows.add(row);
			}
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(stmt);
		}

		return rows;
	}

	private static void setParameters(PreparedStatement stmt, List<Object> parameters) throws SQLException {
		for (int i = 0, size = parameters.size(); i < size; ++i) {
			Object param = parameters.get(i);
			stmt.setObject(i + 1, param);
		}
	}

	public static void insertToTable(DataSource dataSource, String tableName, List<Map<String, Object>> datas)
			throws SQLException {
		if (CollectionUtils.isNotEmpty(datas)) {
			for (Map<String, Object> data : datas)
				insertToTable(dataSource, tableName, data);
		}
	}

	public static void insertToTable(DataSource dataSource, String tableName, Map<String, Object> data)
			throws SQLException {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			insertToTable(conn, tableName, data);
		} finally {
			JdbcUtils.close(conn);
		}
	}

	public static void insertToTable(Connection conn, String tableName, List<Map<String, Object>> datas)
			throws SQLException {
		if (CollectionUtils.isNotEmpty(datas)) {
			for (Map<String, Object> data : datas)
				insertToTable(conn, tableName, data);
		}
	}

	public static void insertToTable(Connection conn, String tableName, Map<String, Object> data) throws SQLException {
		String sql = makeInsertToTableSql(tableName, data.keySet());
		List<Object> parameters = new ArrayList<Object>(data.values());
		execute(conn, sql, parameters);
	}

	public static String makeInsertToTableSql(String tableName, Collection<String> names) {
		StringBuilder sql = new StringBuilder() //
				.append("insert into ") //
				.append(tableName) //
				.append("("); //

		int nameCount = 0;
		for (String name : names) {
			if (nameCount > 0) {
				sql.append(",");
			}
			sql.append(name);
			nameCount++;
		}
		sql.append(") values (");
		for (int i = 0; i < nameCount; ++i) {
			if (i != 0) {
				sql.append(",");
			}
			sql.append("?");
		}
		sql.append(")");

		return sql.toString();
	}
}
