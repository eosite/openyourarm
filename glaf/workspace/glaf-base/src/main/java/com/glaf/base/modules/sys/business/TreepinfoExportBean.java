package com.glaf.base.modules.sys.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataFile;
import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.IBlobService;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JdbcUtils;

public class TreepinfoExportBean {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public JSONArray exportJson(long databaseId) {
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

		ColumnDefinition syncFlagCol = new ColumnDefinition();
		syncFlagCol.setName("syncFlag");
		syncFlagCol.setColumnName("syncFlag");
		syncFlagCol.setJavaType("Integer");
		columns.add(syncFlagCol);

		ColumnDefinition syncTimeCol = new ColumnDefinition();
		syncTimeCol.setName("syncTime");
		syncTimeCol.setColumnName("syncTime");
		syncTimeCol.setJavaType("Date");
		columns.add(syncTimeCol);

		ColumnDefinition syncOperatorTypeCol = new ColumnDefinition();
		syncOperatorTypeCol.setName("syncOperatorType");
		syncOperatorTypeCol.setColumnName("syncOperatorType");
		syncOperatorTypeCol.setJavaType("String");
		syncOperatorTypeCol.setLength(10);
		columns.add(syncOperatorTypeCol);

		JSONArray result = new JSONArray();
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		Database database = databaseService.getDatabaseById(databaseId);
		String systemName = Environment.DEFAULT_SYSTEM_NAME;
		java.util.Date exportTime = new java.util.Date();
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
			psmt = conn.prepareStatement(" select max(index_id) from treepinfo ");
			rs = psmt.executeQuery();
			int maxIndexId = 0;
			if (rs.next()) {
				maxIndexId = rs.getInt(1);
			}
			int maxForEach = maxIndexId / 10000 + 1;
			java.sql.Timestamp time = DateUtils.toTimestamp(exportTime);
			List<Integer> exportList = new ArrayList<Integer>();

			for (int i = 0; i < maxForEach; i++) {
				exportList.clear();
				psmt = conn.prepareStatement(
						" select id, index_id, parent_id, index_name from treepinfo where exportFlag = 'Y' and index_id >= "
								+ i * 10000 + " and index_id <= " + ((i + 1) * 10000));
				rs = psmt.executeQuery();
				while (rs.next()) {
					int index_id = rs.getInt(2);
					exportList.add(index_id);
					JSONObject json = new JSONObject();
					json.put("id", rs.getString(1));
					json.put("index_id", index_id);
					json.put("parent_id", rs.getInt(3));
					json.put("index_name", rs.getString(4));
					result.add(json);
				}

				JdbcUtils.close(rs);
				JdbcUtils.close(psmt);

				if (exportList.size() > 0) {
					conn.setAutoCommit(false);
					psmt = conn
							.prepareStatement(" update treepinfo set SYNCFLAG = 1, SYNCTIME = ? where index_id = ? ");
					for (Integer index_id : exportList) {
						psmt.setTimestamp(1, time);
						psmt.setInt(2, index_id);
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

		if (!result.isEmpty()) {
			String currSystemName = Environment.CURRENT_SYSTEM_NAME;
			try {
				Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
				IBlobService blobService = ContextFactory.getBean("blobService");
				DataFile dataFile = new BlobItemEntity();
				dataFile.setCreateBy("system");
				dataFile.setCreateDate(exportTime);
				dataFile.setData(result.toJSONString().getBytes());
				dataFile.setSize(result.toJSONString().length());
				dataFile.setDeleteFlag(0);
				dataFile.setId("treepinfo_" + databaseId + "_" + exportTime.getTime());
				dataFile.setFileId(dataFile.getId());
				dataFile.setFilename("treepinfo_" + databaseId + "_" + DateUtils.getNowYearMonthDayHHmmss() + ".json");
				dataFile.setContentType("application/json");
				dataFile.setLastModified(exportTime.getTime());
				dataFile.setLocked(0);
				dataFile.setName("WBS");
				dataFile.setObjectId("databaseId");
				dataFile.setObjectValue(String.valueOf(databaseId));
				dataFile.setBusinessKey("treepinfo_" + databaseId);
				dataFile.setServiceKey("treepinfo");
				dataFile.setStatus(9);
				dataFile.setType("export");
				blobService.insertBlob(dataFile);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				Environment.setCurrentSystemName(currSystemName);
			}
		}
		return result;
	}
}
