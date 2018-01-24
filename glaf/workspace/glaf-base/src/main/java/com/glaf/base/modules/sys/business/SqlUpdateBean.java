package com.glaf.base.modules.sys.business;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import com.glaf.base.modules.sys.model.SqlStatement;
import com.glaf.base.modules.sys.util.SqlStatementDomainFactory;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JdbcUtils;

public class SqlUpdateBean {

	protected static final Log logger = LogFactory.getLog(SqlUpdateBean.class);

	public void execute() {
		java.io.InputStream inputStream = null;
		java.sql.Connection conn = null;
		try {
			logger.info("检查系统表是否存在...");
			if (!DBUtils.tableExists("sys_permission")) {
				conn = DBConnectionFactory.getConnection();
				String dbType = DBConnectionFactory.getDatabaseType(conn);
				if (StringUtils.equals(dbType, DBUtils.ORACLE)) {
					inputStream = SqlUpdateBean.class.getResourceAsStream("/createTable_oracle.sql");
				} else {
					inputStream = SqlUpdateBean.class.getResourceAsStream("/createTable.sql");
				}
				if (inputStream != null) {
					String ddlStatements = new String(FileUtils.getBytes(inputStream));
					conn.setAutoCommit(false);
					DBUtils.executeSchemaResourceIgnoreException(conn, ddlStatements);
					conn.commit();
					JdbcUtils.close(conn);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JdbcUtils.close(conn);
			IOUtils.closeQuietly(inputStream);
		}

		try {
			inputStream = SqlUpdateBean.class.getResourceAsStream("/update_sql.xml");
			if (inputStream != null) {
				logger.info("准备执行数据库升级脚本...");
				List<SqlStatement> statements = this.read(inputStream);
				if (statements != null && !statements.isEmpty()) {
					if (!DBUtils.tableExists("SQL_STATEMENT")) {
						TableDefinition tableDefinition = SqlStatementDomainFactory.getTableDefinition();
						DBUtils.createTable(tableDefinition);
					}
					this.insert(statements);
					this.update(statements);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
	}

	public void insert(List<SqlStatement> statements) {
		String sql = " select STATUS_ from SQL_STATEMENT where ID_ = ? ";
		String sql_insert = " insert into SQL_STATEMENT(ID_, TITLE_, SQL_, STATUS_) values(?, ?, ?, ?) ";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionFactory.getConnection();
			conn.setAutoCommit(false);
			for (SqlStatement statement : statements) {
				try {
					psmt = conn.prepareStatement(sql);
					psmt.setString(1, statement.getId());
					rs = psmt.executeQuery();
					if (rs.next()) {
						continue;
					}
					JdbcUtils.close(rs);
					JdbcUtils.close(psmt);

					if (StringUtils.isNotEmpty(statement.getTableExists())) {
						if (!DBUtils.tableExists(conn, statement.getTableExists())) {
							continue;
						}
					}

					psmt = conn.prepareStatement(sql_insert);
					psmt.setString(1, statement.getId());
					psmt.setString(2, statement.getTitle());
					psmt.setString(3, statement.getSql());
					psmt.setInt(4, 0);
					psmt.executeUpdate();
					JdbcUtils.close(psmt);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JdbcUtils.close(conn);
		}
	}

	public List<SqlStatement> read(java.io.InputStream inputStream) {
		List<SqlStatement> statements = new ArrayList<SqlStatement>();
		SAXReader xmlReader = new SAXReader();
		try {
			Document doc = xmlReader.read(inputStream);
			Element root = doc.getRootElement();
			List<?> list = root.elements("statement");
			if (list != null && !list.isEmpty()) {
				Iterator<?> iterator = list.iterator();
				while (iterator.hasNext()) {
					Element elem = (Element) iterator.next();
					SqlStatement model = new SqlStatement();
					model.setId(elem.attributeValue("id"));
					model.setTitle(elem.attributeValue("title"));
					model.setTableExists(elem.attributeValue("tableExists"));
					model.setSql(elem.getStringValue());
					statements.add(model);
				}
			}
			return statements;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	public void update(List<SqlStatement> statements) {
		String sql = " select STATUS_ from SQL_STATEMENT where ID_ = ? ";
		String sql_update = " update SQL_STATEMENT set STATUS_ = ?, EXECUTETIME_ = ?, SQL_ = ? where ID_ = ? ";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionFactory.getConnection();
			conn.setAutoCommit(false);
			for (SqlStatement statement : statements) {
				try {
					psmt = conn.prepareStatement(sql);
					psmt.setString(1, statement.getId());
					rs = psmt.executeQuery();
					if (rs.next()) {
						int status = rs.getInt(1);
						if (status == 1) {
							continue;
						}
					}
					JdbcUtils.close(rs);
					JdbcUtils.close(psmt);

					if (StringUtils.isNotEmpty(statement.getTableExists())) {
						if (!DBUtils.tableExists(conn, statement.getTableExists())) {
							continue;
						}
					}

					DBUtils.executeSchemaResource(conn, statement.getSql());

					psmt = conn.prepareStatement(sql_update);
					psmt.setInt(1, 1);
					psmt.setTimestamp(2, DateUtils.toTimestamp(new java.util.Date()));
					psmt.setString(3, statement.getSql());
					psmt.setString(4, statement.getId());
					psmt.executeUpdate();
					JdbcUtils.close(psmt);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			JdbcUtils.close(conn);
		}
	}

}
