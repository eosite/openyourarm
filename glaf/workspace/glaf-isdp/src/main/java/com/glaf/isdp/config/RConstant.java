package com.glaf.isdp.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.template.util.TemplateUtils;

/**
 * 创建表格基础配置
 * 
 * @author klaus.wang
 *
 */
public class RConstant {

	public static final String USERADDFIX = "T";// useradd

	public static final String USERFIX = "C";// user

	/**
	 * 表名生成规则
	 */
	public static final String TABLENAME = "r_${schema}_${sort}" + USERADDFIX + "${maxUserId}"; //

	/**
	 * 列名生成规则
	 */
	public static final String COLUMNNAME = "${tableName}_" + USERFIX + "${maxUserId}"; //

	public static String getTableName(String schema, Integer maxUserId) {
		return getTableName(schema, maxUserId, "");
	}

	public static String getTableName(String schema, Integer maxUserId, String sort) {
		Map<String, Object> context = new HashMap<String, Object>();

		context.put("schema", schema);
		context.put("maxUserId", maxUserId);
		context.put("sort", StringUtils.isBlank(sort) ? "" : sort.replace("-", "_"));

		String tableName = TemplateUtils.process(context, TABLENAME);

		return tableName;
	}

	public static String getColumnName(String tableName, Integer maxUserId) {
		Map<String, Object> context = new HashMap<String, Object>();

		context.put("tableName", tableName);
		context.put("maxUserId", maxUserId);

		String columnName = TemplateUtils.process(context, COLUMNNAME);

		return columnName;
	}

	public static boolean isOracle() {
		return DBUtils.ORACLE.equalsIgnoreCase(DBConnectionFactory.getDatabaseType());
	}

	public static boolean isDM() {
		return DBUtils.DM_DBMS.equalsIgnoreCase(DBConnectionFactory.getDatabaseType());
	}

}
