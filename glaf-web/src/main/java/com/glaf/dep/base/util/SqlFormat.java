package com.glaf.dep.base.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.MapUtils;

/**
 * 参数名称替换 {name : "ssb"} Select * from sys_user where name_ = ${name}
 * 
 * @param sql
 * @param parameter
 * @return
 */
public class SqlFormat {

	private static final Pattern $pattern = Pattern.compile("\\$\\{(.*?)\\}");

	private List<Object> parameters = new ArrayList<Object>();

	private String sql;

	public SqlFormat(String sql, List<Object> parameters) {
		this.parameters = parameters;
		this.sql = sql;
	}

	private SqlFormat() {
	}

	/**
	 * 
	 * @param sql
	 * @param parameter
	 * @return
	 */
	public static SqlFormat format(String sql, Map<String, Object> parameter) {
		return formatView(sql, null, parameter);
	}

	/**
	 * 
	 * @param sql
	 * @param views : 非预处理参数，直接拼接SQL
	 * @param parameter
	 * @return
	 */
	public static SqlFormat formatView(String sql, Map<String, Object> views, Map<String, Object> parameter) {
		String key = null;
		StringBuffer sb = new StringBuffer();
		SqlFormat sqlFormat = new SqlFormat();
		Matcher m = $pattern.matcher(sql);
		while (m.find()) {
			key = m.group(1);
			if (views != null && views.containsKey(key)) {
				m.appendReplacement(sb, MapUtils.getString(views, key, "null"));
			} else {
				m.appendReplacement(sb, "?");
				sqlFormat.addParameter(parameter.get(key));
			}
		}
		m.appendTail(sb);
		sqlFormat.setSql(sb.toString());
		return sqlFormat;
	}

	public void addParameter(Object value) {
		this.getParameters().add(value);
	}

	public List<Object> getParameters() {
		return parameters;
	}

	public void setParameters(List<Object> parameters) {
		this.parameters = parameters;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	@Override
	public String toString() {
		return "SqlFormat [parameters=" + parameters + ", sql=" + sql + "]";
	}

}
