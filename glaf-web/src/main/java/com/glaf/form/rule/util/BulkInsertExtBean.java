package com.glaf.form.rule.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.BulkInsertBean;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.ParamUtils;

public class BulkInsertExtBean extends BulkInsertBean {

	private String prefix = "";

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void bulkInsertInner(Connection conn, String dbType, TableDefinition tableDefinition,
			List<Map<String, Object>> dataList) {
		List<ColumnDefinition> columns = tableDefinition.getColumns();
		StringBuffer insertBuffer = new StringBuffer();
		insertBuffer.append(" insert into ").append(tableDefinition.getTableName()).append(" (");
		for (ColumnDefinition column : columns) {
			insertBuffer.append(column.getColumnName()).append(", ");
		}
		insertBuffer.delete(insertBuffer.length() - 2, insertBuffer.length());
		insertBuffer.append(" ) values ");
		for (int k = 0, l = dataList.size(); k < l; k++) {
			if (k > 0) {
				insertBuffer.append(", ");
			}
			insertBuffer.append(" ( ");
			for (int i = 0, len = columns.size(); i < len; i++) {
				insertBuffer.append("? ");
				if (i < len - 1) {
					insertBuffer.append(",");
				}
			}
			insertBuffer.append(" ) ");
		}
		int index = 1;
		String columnName = null;
		String javaType = null;
		PreparedStatement psmt = null;
		ByteArrayInputStream bais = null;
		BufferedInputStream bis = null;
		try {
			psmt = conn.prepareStatement(insertBuffer.toString());
			for (int k = 0, l = dataList.size(); k < l; k++) {
				Map<String, Object> dataMap = dataList.get(k);
				// logger.debug("dataMap:" + dataMap);
				for (ColumnDefinition column : columns) {
					columnName = (prefix + column.getColumnName()).toUpperCase();
					javaType = column.getJavaType();
					switch (javaType) {
					case "Integer":
						if (dataMap.get(columnName) != null) {
							psmt.setInt(index++, ParamUtils.getInt(dataMap, columnName));
						} else {
							psmt.setInt(index++, Types.NULL);
						}
						break;
					case "Long":
						if (dataMap.get(columnName) != null) {
							psmt.setLong(index++, ParamUtils.getLong(dataMap, columnName));
						} else {
							psmt.setLong(index++, Types.NULL);
						}
						break;
					case "Double":
						if (dataMap.get(columnName) != null) {
							psmt.setDouble(index++, ParamUtils.getDouble(dataMap, columnName));
						} else {
							psmt.setDouble(index++, Types.NULL);
						}
						break;
					case "Date":
						psmt.setTimestamp(index++, ParamUtils.getTimestamp(dataMap, columnName));
						break;
					case "String":
						psmt.setString(index++, ParamUtils.getString(dataMap, columnName));
						break;
					case "Blob":
						int index2 = index++;
						byte[] data = (byte[]) dataMap.get(columnName);
						if (data != null) {
							try {
								bais = new ByteArrayInputStream(data);
								bis = new BufferedInputStream(bais);
								psmt.setBinaryStream(index2, bis);
							} catch (SQLException ex) {
								psmt.setBytes(index2, data);
							}
						} else {
							psmt.setNull(index2, Types.NULL);
						}
						break;
					default:
						psmt.setObject(index++, ParamUtils.getObject(dataMap, columnName));
						break;
					}
				}
			}
			psmt.executeUpdate();
			psmt.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeQuietly(bais);
			IOUtils.closeQuietly(bis);
			JdbcUtils.close(psmt);
		}
	}
}
