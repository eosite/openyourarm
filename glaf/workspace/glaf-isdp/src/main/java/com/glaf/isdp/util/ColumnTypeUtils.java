package com.glaf.isdp.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.glaf.core.config.Environment;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.util.DBUtils;
import com.glaf.isdp.config.RConstant;

public class ColumnTypeUtils {

	protected static final Log logger = LogFactory.getLog(ColumnTypeUtils.class);

	public static String getColumnType(String fieldType, int fieldLen) {
		return getColumnType(fieldType, fieldLen, null);
	}

	/**
	 * 构建字段类型
	 * 
	 * @param fieldType
	 * @param fieldLen
	 * @return VARCHAR(50)
	 */
	public static String getColumnType(String fieldType, int fieldLen, String dbtype) {

		boolean isOracle = DBUtils.ORACLE.equals(dbtype) || DBUtils.DM_DBMS.equals(dbtype);

		String columnType = "";
		if ("i4".equalsIgnoreCase(fieldType)) {
			columnType = "INT";
		} else if ("r8".equalsIgnoreCase(fieldType)) {
			columnType = "FLOAT";
		} else if ("datetime".equalsIgnoreCase(fieldType)) {
			if (isOracle) {
				columnType = "TIMESTAMP";
			} else {
				columnType = "DATETIME";
			}
		} else if ("image".equalsIgnoreCase(fieldType)) {
			columnType = "VARBINARY(MAX)";
		} else if ("char".equalsIgnoreCase(fieldType)) {
			columnType = "CHAR(" + fieldLen + ")";
		} else {
			if (fieldLen > 4000 && isOracle) {
				columnType = "CLOB";
			} else if (fieldLen > 8000) {
				columnType = "nvarchar(max)".toUpperCase();
			} else {
				columnType = "VARCHAR(" + fieldLen + ")";
			}
		}
		return columnType;
	}

	public static void alterColumn(String tableName, String alterType, String columnName, String columnType,
			JdbcTemplate jdbcTemplate) {

		if (!DBUtils.isAllowedTable(tableName)) {
			throw new RuntimeException("不允许访问系统表。");
		}
		columnName = columnName.toLowerCase();
		List<ColumnDefinition> columns = DBUtils.getColumnDefinitions(Environment.getCurrentSystemName(), tableName);
		Map<String, ColumnDefinition> map = new HashMap<String, //
				ColumnDefinition>(columns.size());
		if (CollectionUtils.isNotEmpty(columns)) {
			for (ColumnDefinition column : columns) {
				map.put(column.getColumnName().toLowerCase(), column);
			}
		} else {
			throw new RuntimeException("不允许访问系统表。");
		}

		StringBuffer sb = new StringBuffer();
		sb.append("ALTER TABLE ").append(tableName).append(" ");
		boolean rst = true;

		boolean isOracle = RConstant.isOracle() || RConstant.isDM();
		if ("add".equalsIgnoreCase(alterType) && //
				!map.containsKey(columnName)) {
			// 新增列
			sb.append(alterType).append(" ").append(columnName);
			sb.append(" ").append(columnType);
		} else if ("modify".equalsIgnoreCase(alterType) && //
				map.containsKey(columnName)) {
			// 修改列
			if (isOracle) {

				ColumnDefinition cd = map.get(columnName);
				if (cd.getLength() <= 4000 && columnType.equalsIgnoreCase("clob")) {
					String col = "c" + System.currentTimeMillis();
					
					/**
					 * 临时新增一列
					 */
					String sql = String.format("alter table %s add %s clob", tableName, col);
					jdbcTemplate.execute(sql);
					
					/**
					 * 保存已有数据
					 */
					sql = String.format("update %s set %s = %s", tableName, col, columnName);
					jdbcTemplate.execute(sql);
					
					/**
					 * 删除已有列
					 */
					sql = String.format("ALTER TABLE %s DROP COLUMN %s", tableName, columnName);
					jdbcTemplate.execute(sql);
					
					/**
					 * 修改临时列名
					 */
					sql = String.format("alter table %s rename column %s to %s", tableName, col, columnName);
					jdbcTemplate.execute(sql);
					
					
					return;
				}

				sb.append(" modify ");
			} else {
				sb.append(" ALTER COLUMN ");
			}
			sb.append(columnName);
			sb.append(" ").append(columnType);
		} else if ("drop".equalsIgnoreCase(alterType) && //
				map.containsKey(columnName)) {
			// 删除列
			sb.append(alterType).append(" ").append(" COLUMN ");
			sb.append(columnName);
		} else {
			// throw new RuntimeException("无可更新的列!");
			rst = false;
		}
		if (rst) {
			try {
				logger.debug(sb.toString());
				jdbcTemplate.execute(sb.toString());
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}
	}

}
