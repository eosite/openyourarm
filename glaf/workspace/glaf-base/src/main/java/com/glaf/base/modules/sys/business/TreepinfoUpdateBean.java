package com.glaf.base.modules.sys.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;

public class TreepinfoUpdateBean {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public void updateExportFlag(long databaseId) {
		List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();
		ColumnDefinition md5Col = new ColumnDefinition();
		md5Col.setName("md5");
		md5Col.setColumnName("md5");
		md5Col.setJavaType("String");
		md5Col.setLength(200);
		columns.add(md5Col);

		ColumnDefinition exportFlagCol = new ColumnDefinition();
		exportFlagCol.setName("exportFlag");
		exportFlagCol.setColumnName("exportFlag");
		exportFlagCol.setJavaType("String");
		exportFlagCol.setLength(20);
		columns.add(exportFlagCol);

		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		Database database = databaseService.getDatabaseById(databaseId);
		String systemName = Environment.DEFAULT_SYSTEM_NAME;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			if (database != null && StringUtils.isNotEmpty(database.getName())) {
				systemName = database.getName();
			}
			logger.debug("systemName:" + systemName);
			DBUtils.alterTable(systemName, "treepinfo", columns);
			conn = DBConnectionFactory.getConnection(systemName);
			conn.setAutoCommit(false);
			psmt = conn.prepareStatement(" update treepinfo set exportFlag = 'N' ");
			psmt.executeUpdate();
			conn.commit();
			JdbcUtils.close(psmt);
			psmt = conn.prepareStatement(" select max(index_id) from treepinfo ");
			rs = psmt.executeQuery();
			int maxIndexId = 0;
			if (rs.next()) {
				maxIndexId = rs.getInt(1);
			}
			int maxForEach = maxIndexId / 10000 + 1;
			Map<Integer, String> updateMap = new HashMap<Integer, String>();
			StringBuilder buffer = new StringBuilder();
			for (int i = 0; i < maxForEach; i++) {
				updateMap.clear();
				psmt = conn.prepareStatement(
						" select id, index_id, parent_id, index_name, md5 from treepinfo where index_id >= " + i * 10000
								+ " and index_id <= " + ((i + 1) * 10000));
				rs = psmt.executeQuery();
				while (rs.next()) {
					buffer.delete(0, buffer.length());
					int index_id = rs.getInt(2);
					buffer.append(rs.getString(1)).append("_").append(index_id).append("_").append(rs.getInt(3))
							.append("_").append(rs.getString(4));
					String md5 = DigestUtils.md5Hex(buffer.toString());
					if (!StringUtils.equals(md5, rs.getString(5))) {
						updateMap.put(index_id, md5);
					}
				}

				JdbcUtils.close(rs);
				JdbcUtils.close(psmt);

				if (updateMap.size() > 0) {
					conn.setAutoCommit(false);
					psmt = conn.prepareStatement(" update treepinfo set exportFlag = 'Y', md5 = ? where index_id = ? ");
					Set<Entry<Integer, String>> entrySet = updateMap.entrySet();
					for (Entry<Integer, String> entry : entrySet) {
						Integer key = entry.getKey();
						String value = entry.getValue();
						psmt.setString(1, value);
						psmt.setInt(2, key);
						psmt.addBatch();
					}
					psmt.executeBatch();
					conn.commit();
				}
			}
		} catch (Exception ex) {
			logger.error("update error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);
			JdbcUtils.close(conn);
		}
	}
}
