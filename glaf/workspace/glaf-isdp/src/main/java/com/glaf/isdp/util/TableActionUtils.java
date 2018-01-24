package com.glaf.isdp.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.Environment;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;
import com.glaf.template.util.TemplateUtils;

public class TableActionUtils {

	private static Map<String, JSONObject> expValue = null;

	static {
		initDefaultExpValue();
	}

	public static void initDefaultExpValue() {
		expValue = new HashMap<>();

		String expTmpl = "{\"expVal\":\"${code}\",\"expActVal\":\"${code}\",\"varVal\":[{\"key\":\"${code}\",\"value\":{\"code\":\"${code}\",\"t\":\"${name}\",\"name\":\"${title}\",\"value\":\"${code}\"}}]}";

		String[][] exps = new String[][] { //
				{ "id", "$USER_ID(~CONST)", "用户自增ID", "USERID 用户自增ID" }//
				, { "ctime", "$SYSDATE()", "系统时间", "系统时间" } //
				, { "createdate", "$SYSDATE()", "系统时间", "系统时间" } //
				, { "updatedate", "$SYSDATE()", "系统时间", "系统时间" } //
				, { "updater", "$CURR_ACTORID(~CONST)", "当前用户", "当前用户" } //
				, { "creater", "$CURR_ACTORID(~CONST)", "当前用户", "当前用户" } //

		};

		String[] keys = { "", "code", "name", "title" };

		int i;
		JSONObject json;
		for (String[] exp : exps) {
			json = new JSONObject();
			for (i = 0; i < keys.length; i++) {
				if (StringUtils.isEmpty(keys[i])) {
					expValue.put(exp[i], json);
				} else {
					json.put(keys[i], exp[i]);
				}
			}
		}

		for (String key : expValue.keySet()) {
			expValue.put(key, JSON.parseObject(TemplateUtils.process(expValue.get(key), expTmpl)));
		}
	}

	public static TableDefinition getDefaultTableDefinition(String tableName) {

		TableDefinition td = new TableDefinition();
		td.setTableName(tableName);

		ColumnDefinition c = new ColumnDefinition();
		c.setColumnName("id");
		c.setLength(100);
		c.setJavaType("String");
		td.setIdColumn(c);

		c = new ColumnDefinition();
		c.setColumnName("topid");
		c.setLength(100);
		c.setJavaType("String");
		td.addColumn(c);

		c = new ColumnDefinition();
		c.setColumnName("index_id");
		c.setJavaType("Integer");
		td.addColumn(c);

		c = new ColumnDefinition();
		c.setColumnName("parent_id");
		c.setJavaType("Integer");
		td.addColumn(c);

		c = new ColumnDefinition();
		c.setColumnName("treeid");
		c.setLength(100);
		c.setJavaType("String");
		td.addColumn(c);

		c = new ColumnDefinition();
		c.setColumnName("nlevel");
		c.setJavaType("Integer");
		td.addColumn(c);

		c = new ColumnDefinition();
		c.setColumnName("listno");
		c.setJavaType("Integer");
		td.addColumn(c);

		c = new ColumnDefinition();
		c.setColumnName("old_id");
		c.setLength(100);
		c.setJavaType("String");
		td.addColumn(c);

		c = new ColumnDefinition();
		c.setColumnName("tzflag");
		c.setJavaType("Integer");
		td.addColumn(c);

		c = new ColumnDefinition();
		c.setColumnName("type_id");
		c.setLength(50);
		c.setJavaType("String");
		td.addColumn(c);

		c = new ColumnDefinition();
		c.setColumnName("type_baseid");
		c.setLength(50);
		c.setJavaType("String");
		td.addColumn(c);

		c = new ColumnDefinition();
		c.setColumnName("lockrec");
		c.setJavaType("Integer");
		td.addColumn(c);

		c = new ColumnDefinition();
		c.setColumnName("cell_locate");
		c.setLength(50);
		c.setJavaType("String");
		td.addColumn(c);

		c = new ColumnDefinition();
		c.setColumnName("isdel");
		c.setJavaType("Integer");
		td.addColumn(c);

		c = new ColumnDefinition();
		c.setColumnName("unitid");
		c.setLength(100);
		c.setJavaType("String");
		td.addColumn(c);

		c = new ColumnDefinition();
		c.setColumnName("sys_id");
		c.setLength(50);
		c.setJavaType("String");
		td.addColumn(c);

		c = new ColumnDefinition();
		c.setColumnName("lessessid");
		c.setLength(100);
		c.setJavaType("String");
		td.addColumn(c);

		c = new ColumnDefinition();
		c.setColumnName("ctime");
		c.setJavaType("Date");
		td.addColumn(c);

		c = new ColumnDefinition();
		c.setColumnName("createdate");
		c.setJavaType("Date");
		td.addColumn(c);

		c = new ColumnDefinition();
		c.setColumnName("updatedate");
		c.setJavaType("Date");
		td.addColumn(c);

		c = new ColumnDefinition();
		c.setColumnName("creater");
		c.setLength(50);
		c.setJavaType("String");
		td.addColumn(c);

		c = new ColumnDefinition();
		c.setColumnName("updater");
		c.setLength(50);
		c.setJavaType("String");
		td.addColumn(c);

		return td;
	}

	/**
	 * 创建默认字段
	 * 
	 * @param tableName
	 */
	public static void createDefaultTable(String tableName) {

		if (!DBUtils.isAllowedTable(tableName)) {
			throw new RuntimeException("不允许访问系统表。");
		}

		TableDefinition tableDefinition = getDefaultTableDefinition(tableName);

		DBUtils.createTable(Environment.getCurrentSystemName(), tableDefinition);

	}

	public static Map<String, JSONObject> getDefaultExpValue() {
		return expValue;
	}

}
