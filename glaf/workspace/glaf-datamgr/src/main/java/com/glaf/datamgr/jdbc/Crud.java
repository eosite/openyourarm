package com.glaf.datamgr.jdbc;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.ReflectUtils;

public abstract class Crud<T> {

	private int size = 1000;

	protected StringBuffer sqlBuffer;

	protected List<Field> fields;

	protected Connection connection;

	protected DataSource dataSource;

	protected void init(T t) {
	}

	public Object execute(T t) throws SQLException {
		return this.addBatch(t).executeBatch();
	}

	public Crud<T> addBatch(T t) {
		this.getTList().add(t);
		return this;
	}

	public Object execute(String sql) throws SQLException {
		return this.addBatch(sql).executeBatch();
	}

	public Crud<T> addBatch(String sql) {
		this.getSqlList().add(sql);
		return this;
	}

	public Object executeBatch() throws SQLException {
		int index = 0, batchSize = this.batchSize();
		if (batchSize > 0) {
			if (this.getTList().size() > 0) {
				if (this.sqlBuffer == null) {
					this.init(this.getTList().get(0));
				}
				if (this.sqlBuffer != null) {
					PreparedStatement pstmt = null;
					try {
						pstmt = this.getConnection().prepareStatement(this.sqlBuffer.toString());
						for (T t : tList) {
							List<Object> values = new ArrayList<Object>();
							for (int i = 0; i < fields.size(); i++) {
								Field field = fields.get(i);
								values.add(ReflectUtils.getFieldValue(t, field.getName()));
							}
							JdbcUtils.fillStatement(pstmt, values);
							pstmt.addBatch();
							index++;
							if (index % this.getSize() == 0) {
								pstmt.executeBatch();
								pstmt.clearBatch();
								index = 0;
							}
						}
						if (index > 0) {
							pstmt.executeBatch();
						}
					} finally {
						JdbcUtils.close(pstmt);
					}
				}
			}
			if (this.getSqlList().size() > 0) {
				index = 0;
				Statement stmt = null;
				try {
					stmt = this.getConnection().createStatement();
					for (String sql : this.getSqlList()) {
						stmt.addBatch(sql);
						index++;
						if (index % this.getSize() == 0) {
							stmt.executeBatch();
							stmt.clearBatch();
							index = 0;
						}
					}
					if (index > 0) {
						stmt.executeBatch();
					}
				} finally {
					JdbcUtils.close(stmt);
				}
			}
			this.clear();
			this.close();
		} else {
			throw new RuntimeException("batchSize is null...");
		}
		return batchSize;
	}

	public void clear() {
		this.tList = new ArrayList<T>();
		this.sqlList = new ArrayList<String>();
	}

	protected List<T> tList = new ArrayList<T>();

	protected List<String> sqlList = new ArrayList<String>();

	protected List<String> getSqlList() {
		return this.sqlList;
	}

	protected List<T> getTList() {
		return this.tList;
	}

	public int batchSize() {
		return this.tList.size() + this.sqlList.size();
	}

	protected boolean conn = false;

	public void setConnection(Connection connection) {
		this.connection = connection;
		this.conn = true;
	}

	protected Connection getConnection() throws SQLException {
		if (this.connection == null) {
			this.connection = DataSourceUtils.getConnection(dataSource);
		}
		if (this.connection != null && !this.connection.getAutoCommit()) {
			//this.connection.setAutoCommit(true);
		}
		return this.connection;
	}

	protected void close() {
		if (!this.conn) { // 由dataSource 里面拿出来的connection需要关闭
			JdbcUtils.close(this.connection);
			this.connection = null;
		}
	}

	protected DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
