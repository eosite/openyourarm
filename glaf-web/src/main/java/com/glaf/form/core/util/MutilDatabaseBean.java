package com.glaf.form.core.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.metamodel.query.Query;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.TableModel;
import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.DBConfiguration;
import com.glaf.core.config.Environment;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.dialect.Dialect;
import com.glaf.core.domain.Database;
import com.glaf.core.factory.DataServiceFactory;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.jdbc.QueryConnectionFactory;
import com.glaf.core.service.EntityService;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.datamgr.jdbc.ValueConverter;
import com.glaf.dep.base.util.SqlFormat;
import com.glaf.form.core.service.MutilDataBeanService;
import com.glaf.isdp.service.CellUserAddService;
import com.glaf.isdp.service.TableActionService;

/*
 *每列增加小写字段
 *column.toLowerCase()
 */
@Component
public class MutilDatabaseBean {

	protected static volatile Configuration conf = BaseConfiguration.create();

	protected IDatabaseService databaseService;
	protected CellUserAddService cellUserAddService;
	protected EntityService entityService;
	protected TableActionService tableActionService;
	protected MutilDataBeanService mutilDataBeanService;

	protected static final Log logger = LogFactory.getLog(MutilDatabaseBean.class);

	public List<Map<String, Object>> basesql(String systemName, String sql, Integer start, Integer limit) {
		// conf.set("sql_query_timeout", "60000");
		long ts = 0;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		boolean supportsPhysicalPage = false;
		try {
			conn = DBConnectionFactory.getConnection(systemName);

			ts = System.currentTimeMillis();
			 QueryConnectionFactory.getInstance().register(ts, conn);
			if (start != null && limit != null) {
				Dialect dialect = DBConfiguration.getDatabaseDialect(conn);
				if (dialect != null && dialect.supportsPhysicalPage()) {
					supportsPhysicalPage = true;
					sql = dialect.getLimitString(sql, start, limit);
				}
			}
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();

			if (!supportsPhysicalPage) {
				if (start != null) {
					JdbcUtils.skipRows(rs, start);
				}
			}

			int pageSize = 50000;
			if (limit != null && limit > 0 && limit <= 50000) {
				pageSize = limit;
			}
			ResultSetMetaData metaData = rs.getMetaData();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> dataMap = null;
			int index = 0;
			Object value = null;
			String column = null;
			if (start == null) {
				start = 0;
			}
			int rowNumber = start;

			boolean sql_tranlate = SystemConfig.getBoolean("sql_tranlate");

			while (rs.next() && index++ < pageSize) {
				dataMap = new HashMap<String, Object>();
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					if (sql_tranlate) {
						column = com.glaf.datamgr.sqlparser.TranlateFactory.getColumn(metaData.getColumnName(i));//
					} else {
						column = metaData.getColumnName(i);
					}

					value = ValueConverter.convert(rs.getObject(i));

					dataMap.put(column, value);
					dataMap.put(column.toLowerCase(), value);
				}
				dataMap.put("startIndex", index);
				dataMap.put("row_number", ++rowNumber);
				list.add(dataMap);
			}
			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (conn != null) {
				 QueryConnectionFactory.getInstance().unregister(ts, conn);
			}
			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);
			JdbcUtils.close(conn);
		}
		return new ArrayList<>();
	}

	public List<Map<String, Object>> getDataListBySql(String sql, Long databaseId) {
		List<Map<String, Object>> dataMaps = new ArrayList<>();
		// 切换数据库链接
		Database database = databaseService.getDatabaseById(databaseId);
		// String currSystemName = Environment.getCurrentSystemName();
		String systemName = Environment.DEFAULT_SYSTEM_NAME;
		try {
			if (database != null) {
				systemName = database.getName();
			}
			// Environment.setCurrentSystemName(systemName);

			// 执行查询

			dataMaps = basesql(systemName, sql, null, null);
			// cellUserAddService.getDataListBySql(sql);

			// 执行查询

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// Environment.setCurrentSystemName(currSystemName);
		}
		// 切换数据库链接
		return dataMaps;
	}

	public List<Map<String, Object>> getDataListByQueryCriteria(String sql, int start, int limit, Long databaseId) {
		List<Map<String, Object>> dataMaps = new ArrayList<>();
		// 切换数据库链接
		Database database = databaseService.getDatabaseById(databaseId);
		// String currSystemName = Environment.getCurrentSystemName();
		String systemName = Environment.DEFAULT_SYSTEM_NAME;
		try {
			if (database != null) {
				systemName = database.getName();
			}
			// Environment.setCurrentSystemName(systemName);

			// 执行查询
			dataMaps = basesql(systemName, sql, start, limit);
			// cellUserAddService.getDataListByQueryCriteria(sql, start, limit);
			// 执行查询

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// Environment.setCurrentSystemName(currSystemName);
		}
		// 切换数据库链接
		return dataMaps;
	}

	public List<Map<String, Object>> getDataListBySqlCriteria(String sql, int start, int limit, Long databaseId) {
		List<Map<String, Object>> dataMaps = new ArrayList<>();
		// 切换数据库链接
		Database database = databaseService.getDatabaseById(databaseId);
		// String currSystemName = Environment.getCurrentSystemName();
		String systemName = Environment.DEFAULT_SYSTEM_NAME;
		try {
			if (database != null) {
				systemName = database.getName();
			}
			// Environment.setCurrentSystemName(systemName);

			// 执行查询
			dataMaps = basesql(systemName, sql, start, limit);
			// cellUserAddService.getDataListBySqlCriteria(sqlc);
			// 执行查询

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// Environment.setCurrentSystemName(currSystemName);
		}
		// 切换数据库链接
		return dataMaps;
	}

	public String getNextId(String tableName, String idColumn, String creatyBy, Long databaseId) {
		String nextId = null;
		// 切换数据库链接
		Database database = databaseService.getDatabaseById(databaseId);
		String currSystemName = Environment.getCurrentSystemName();
		String systemName = Environment.DEFAULT_SYSTEM_NAME;
		try {
			if (database != null) {
				systemName = database.getName();
			}
			Environment.setCurrentSystemName(systemName);

			// 执行查询
			nextId = entityService.getNextId(tableName, idColumn, creatyBy);
			// 执行查询

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currSystemName);
		}
		// 切换数据库链接
		return nextId;
	}

	public void updateTableData(TableModel model, Long databaseId) {
		// 切换数据库链接
		Database database = databaseService.getDatabaseById(databaseId);
		String currSystemName = Environment.getCurrentSystemName();
		String systemName = Environment.DEFAULT_SYSTEM_NAME;
		try {
			if (database != null) {
				systemName = database.getName();
			}
			Environment.setCurrentSystemName(systemName);

			// 执行查询   
			/*
			 * 当数值型字段值为空时,需要将字段内容清空,该方法无法满足条件
			 * */
//			DataServiceFactory.getInstance().updateTableData(model);    
			// 执行查询
			mutilDataBeanService.updateTableData(model);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currSystemName);
		}
	}

	public void updateTableDataByWhere(TableModel model, Long databaseId) {
		// 切换数据库链接
		Database database = databaseService.getDatabaseById(databaseId);
		String currSystemName = Environment.getCurrentSystemName();
		String systemName = Environment.DEFAULT_SYSTEM_NAME;
		try {
			if (database != null) {
				systemName = database.getName();
			}
			Environment.setCurrentSystemName(systemName);

			// 执行查询
			DataServiceFactory.getInstance().updateTableDataByWhere(model);
			// 执行查询

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currSystemName);
		}
	}
	
	public void insertTableData2(TableModel model, Long databaseId) {
		// 切换数据库链接
		Database database = databaseService.getDatabaseById(databaseId);
		String currSystemName = Environment.getCurrentSystemName();
		String systemName = Environment.DEFAULT_SYSTEM_NAME;
		try {
			if (database != null) {
				systemName = database.getName();
			}
			Environment.setCurrentSystemName(systemName);

			// 执行查询
			DataServiceFactory.getInstance().insertTableData(model);
			// 执行查询

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			Environment.setCurrentSystemName(currSystemName);
		}
	}

	public void insertTableData(TableModel model, Long databaseId) {
		// 切换数据库链接
		Database database = databaseService.getDatabaseById(databaseId);
		String currSystemName = Environment.getCurrentSystemName();
		String systemName = Environment.DEFAULT_SYSTEM_NAME;
		try {
			if (database != null) {
				systemName = database.getName();
			}
			Environment.setCurrentSystemName(systemName);

			// 执行查询
			DataServiceFactory.getInstance().insertTableData(model);
			// 执行查询

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currSystemName);
		}
	}

	public void deleteTableByWhereCause(String tableName, String whereSql, Long databaseId) {
		// 切换数据库链接
		Database database = databaseService.getDatabaseById(databaseId);
		String currSystemName = Environment.getCurrentSystemName();
		String systemName = Environment.DEFAULT_SYSTEM_NAME;
		try {
			if (database != null) {
				systemName = database.getName();
			}
			Environment.setCurrentSystemName(systemName);

			// 执行查询
			tableActionService.deleteTableByWhereCause(tableName, whereSql);
			// 执行查询

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currSystemName);
		}
	}

	public void execSql(String sql, String tableName, Long databaseId) {
		// 切换数据库链接
		Database database = databaseService.getDatabaseById(databaseId);
		String currSystemName = Environment.getCurrentSystemName();
		String systemName = Environment.DEFAULT_SYSTEM_NAME;
		try {
			if (database != null) {
				systemName = database.getName();
			}
			Environment.setCurrentSystemName(systemName);

			// 执行更新操作
			tableActionService.executeSQL(sql, tableName);
			// 执行更新操作

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currSystemName);
		}
	}

	public Map<String, Object> getTableDataByPrimaryKey(TableModel model, Long databaseId) {
		Map<String, Object> map = new HashMap<>();
		// 切换数据库链接
		Database database = databaseService.getDatabaseById(databaseId);
		String currSystemName = Environment.getCurrentSystemName();
		String systemName = Environment.DEFAULT_SYSTEM_NAME;
		try {
			if (database != null) {
				systemName = database.getName();
			}
			Environment.setCurrentSystemName(systemName);

			// 执行查询
			map = DataServiceFactory.getInstance().getTableDataByPrimaryKey(model);
			// 执行查询

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currSystemName);
		}
		return map;
	}

	public void deleteTableData(TableModel model, Long databaseId) {
		// 切换数据库链接
		Database database = databaseService.getDatabaseById(databaseId);
		String currSystemName = Environment.getCurrentSystemName();
		String systemName = Environment.DEFAULT_SYSTEM_NAME;
		try {
			if (database != null) {
				systemName = database.getName();
			}
			Environment.setCurrentSystemName(systemName);

			// 执行查询
			DataServiceFactory.getInstance().deleteTableData(model);
			// 执行查询

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currSystemName);
		}
		// 切换数据库链接
	}

	public int getDataCountBySql(Connection conn, String sql, List<Object> parameters) {
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("SELECT COUNT(*) FROM (" + sql + ")TOTAL");
			if (CollectionUtils.isNotEmpty(parameters))
				JdbcUtils.fillStatement(pstmt, parameters);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(pstmt);
		}
		return 0;
	}

	public JSONObject queryBySqlCriteria(String sql, int pageNo, int pageSize, String systemName, String orderBy,
			Map<String, Object> parameter) throws Exception {
		if (!DBUtils.isLegalQuerySql(sql)) {
			throw new RuntimeException("不允许的查询!");
		}
		String currSystemName = Environment.getCurrentSystemName();
		JSONObject result = new JSONObject();
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			if (systemName == null) {
				systemName = Environment.DEFAULT_SYSTEM_NAME;
			}
			// 切换数据库链接
			Environment.setCurrentSystemName(systemName);
			conn = DBConnectionFactory.getConnection(systemName);
			// 执行查询
			// int total = cellUserAddService.getDataCountBySql("SELECT COUNT(*)
			// FROM (" + sql + ")TOTAL");

			SqlFormat sf = SqlFormat.format(sql, parameter);
			sql = sf.getSql();
			List<Object> parameters = sf.getParameters();
			int total = this.getDataCountBySql(conn, sql, parameters);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			if (total == 0) {
				result.put("total", total);
				result.put("rows", list);
				return result;
			}

			Dialect dialect = DBConfiguration.getCurrentDialect();
			if (pageNo <= 0)
				pageNo = 1;
			int start = (pageNo - 1) * pageSize;
			sql = dialect.getLimitString(sql + orderBy, start, pageSize);
			pstmt = conn.prepareStatement(sql);
			if (CollectionUtils.isNotEmpty(parameters))
				JdbcUtils.fillStatement(pstmt, parameters);
			ResultSet rs = pstmt.executeQuery();
			if (rs != null) {
				Object value = null;
				String column = null;
				Map<String, Object> map = null;
				ResultSetMetaData rsmd = rs.getMetaData();
				int rc = rsmd.getColumnCount(), index = 0;
				boolean sql_tranlate = SystemConfig.getBoolean("sql_tranlate");
				List<String> columns = new ArrayList<String>();
				while (rs.next()) {
					map = new HashMap<String, Object>();
					for (int i = 1; i <= rc; i++) {
						switch (column = rsmd.getColumnLabel(i)) {
						case "__row_number__":
							column = "rowIndex";
							break;
						default:
							break;
						}

						if (sql_tranlate) {
							column = com.glaf.datamgr.sqlparser.TranlateFactory.getColumn(column);//
						}

						value = ValueConverter.convert(rs.getObject(i));

						map.put(column, value);
						map.put(column.toLowerCase(), value);
						if (index == 0) {
							columns.add(column);
						}
					}
					index++;
					list.add(map);
				}
				result.put("columns", columns);
				com.glaf.core.util.JdbcUtils.close(rs);
			}
			logger.debug(sql);
			result.put("total", total);
			result.put("rows", list);
		} catch (Exception ex) {
			throw ex;
		} finally {
			com.glaf.core.util.JdbcUtils.close(pstmt);
			com.glaf.core.util.JdbcUtils.close(conn);
			Environment.setCurrentSystemName(currSystemName);
		}
		// 切换数据库链接
		return result;
	}

	public JSONObject queryBySqlCriteria(String sql, int pageNo, int pageSize, String systemName) throws Exception {
		if (!DBUtils.isLegalQuerySql(sql)) {
			throw new RuntimeException("不允许的查询!");
		}
		String currSystemName = Environment.getCurrentSystemName();
		JSONObject result = new JSONObject();
		Statement stmt = null;
		Connection conn = null;
		try {
			if (systemName == null) {
				systemName = Environment.DEFAULT_SYSTEM_NAME;
			}
			// 切换数据库链接
			Environment.setCurrentSystemName(systemName);
			conn = DBConnectionFactory.getConnection(systemName);
			// 执行查询
			int total = cellUserAddService.getDataCountBySql("SELECT COUNT(*) FROM (" + sql + ")TOTAL");
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Dialect dialect = DBConfiguration.getCurrentDialect();
			if (pageNo <= 0)
				pageNo = 1;
			int start = (pageNo - 1) * pageSize;
			sql = dialect.getLimitString(sql, start, pageSize);

			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs != null) {
				Object value = null;
				String column = null;
				Map<String, Object> map = null;
				ResultSetMetaData rsmd = rs.getMetaData();
				int rc = rsmd.getColumnCount(), index = 0;
				List<String> columns = new ArrayList<String>();
				boolean sql_tranlate = SystemConfig.getBoolean("sql_tranlate");
				while (rs.next()) {
					map = new HashMap<String, Object>();
					for (int i = 1; i <= rc; i++) {
						switch (column = rsmd.getColumnLabel(i)) {
						case "__row_number__":
							column = "rowIndex";
							break;
						default:
							break;
						}

						if (sql_tranlate) {
							column = com.glaf.datamgr.sqlparser.TranlateFactory.getColumn(column);//
						}
						map.put(column, value = rs.getObject(i));
						map.put(column.toLowerCase(), value);
						if (index == 0) {
							columns.add(column);
						}
					}
					index++;
					list.add(map);
				}
				result.put("columns", columns);
				com.glaf.core.util.JdbcUtils.close(rs);
			}
			logger.debug(sql);
			result.put("total", total);
			result.put("rows", list);
		} catch (Exception ex) {
			throw ex;
		} finally {
			com.glaf.core.util.JdbcUtils.close(stmt);
			com.glaf.core.util.JdbcUtils.close(conn);
			Environment.setCurrentSystemName(currSystemName);
		}
		// 切换数据库链接
		return result;
	}
	@javax.annotation.Resource(name = "com.glaf.form.core.service.mutilDataBeanService")
	public void setMutilDataBeanService(MutilDataBeanService mutilDataBeanService) {
		this.mutilDataBeanService = mutilDataBeanService;
	}
	@javax.annotation.Resource(name = "com.glaf.isdp.service.tableActionService")
	public void setTableActionService(TableActionService tableActionService) {
		this.tableActionService = tableActionService;
	}

	@javax.annotation.Resource
	public void setCellUserAddService(CellUserAddService cellUserAddService) {
		this.cellUserAddService = cellUserAddService;
	}

	@javax.annotation.Resource
	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	public int getCountBySql(Query query, Long databaseId) {
		String sql = query.toSql();

		Database database = databaseService.getDatabaseById(databaseId);
		String systemName = Environment.DEFAULT_SYSTEM_NAME;

		long ts = 0;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int total = 0;
		try {
			if (database != null) {
				systemName = database.getName();
			}
			conn = DBConnectionFactory.getConnection(systemName);

			boolean isOracle = StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType(conn));
			if (isOracle) {

			} else {
				if (sql.indexOf("SELECT DISTINCT") == 0) {
					sql = sql.replaceFirst("SELECT DISTINCT ", "SELECT DISTINCT TOP 100000000000 ");
				} else {
					sql = sql.replaceFirst("SELECT", "SELECT TOP 100000000000 ");
				}
			}

			ts = System.currentTimeMillis();
			QueryConnectionFactory.getInstance().register(ts, conn);
			psmt = conn.prepareStatement("SELECT COUNT(*) as counts FROM (" + sql + ")TOTAL");
			rs = psmt.executeQuery();

			if (rs.next()) {
				total = rs.getInt("counts");
			}

			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (conn != null) {
				QueryConnectionFactory.getInstance().unregister(ts, conn);
			}
			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);
			JdbcUtils.close(conn);
		}
		return total;
	}

	public int getMaxListNo(String tableName, String columnField, Long databaseId) {
		int nextMaxNo = 1;
		try {

			// 执行查询
			List<Map<String, Object>> list = this
					.getDataListBySql("select max(" + columnField + ") as LISTNO from  " + tableName, databaseId);
			Object listNo = list.get(0).get("LISTNO");
			if (listNo != null) {
				nextMaxNo = Integer.parseInt(listNo.toString()) + 1;
			}
			// 执行查询

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return nextMaxNo;
	}

}
