package com.glaf.datamgr.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

import com.glaf.core.base.LowerLinkedMap;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.QueryUtils;
import com.glaf.datamgr.domain.DynamicSqlTree;
import com.glaf.datamgr.query.DynamicSqlTreeQuery;
import com.glaf.datamgr.service.DynamicSqlTreeService;

public class DynamicSqlHelper {

	protected DynamicSqlTreeService dynamicSqlTreeService;

	public DynamicSqlHelper() {

	}

	public SqlExecutor buildSql(String moduleId, String businessKey, Map<String, Object> params) {
		SqlExecutor sqlExecutor = new SqlExecutor();
		sqlExecutor.setSql("");
		if (params != null && !params.isEmpty()) {
			DynamicSqlTreeQuery query = new DynamicSqlTreeQuery();
			query.moduleId(moduleId);
			query.businessKey(businessKey);
			List<DynamicSqlTree> list = getDynamicSqlTreeService().list(query);
			if (list != null && !list.isEmpty()) {
				String tmp = null;
				Object value = null;
				String operator = null;
				String condition = null;
				String columnType = null;
				String tableAlias = null;
				StringTokenizer token = null;
				StringBuilder buffer = new StringBuilder();
				List<Object> values = new ArrayList<Object>();
				LowerLinkedMap dataMap = new LowerLinkedMap();
				dataMap.putAll(params);
				for (DynamicSqlTree m : list) {
					if (m.getParamName() != null) {
						for (int level = 0; level < 10; level++) {
							if (level == m.getLevel()) {
								value = dataMap.get(m.getParamName().toLowerCase());
								if (value != null) {
									tableAlias = m.getTableAlias();
									columnType = m.getColumnType();

									if (StringUtils.isEmpty(condition)) {
										condition = " AND ";
									}

									buffer.append(condition);// 增加连接条件

									/**
									 * 判断是否为集合参数
									 */
									if (StringUtils.equals(m.getCollectionFlag(), "Y")) {
										String separator = ",";
										if (StringUtils.isNotEmpty(m.getSeparator())) {
											separator = m.getSeparator();
										}
										buffer.append(" ");
										if (StringUtils.isNotEmpty(tableAlias)) {
											buffer.append(tableAlias).append(".");
										}
										buffer.append(m.getColumnName()).append(" IN ( ");
										token = new StringTokenizer(value.toString(), separator);
										while (token.hasMoreTokens()) {
											tmp = token.nextToken();
											switch (columnType) {
											case "Integer":
												values.add(Integer.parseInt(tmp));
												buffer.append(" ?, ");
												break;
											case "Long":
												values.add(Long.parseLong(tmp));
												buffer.append(" ?, ");
												break;
											case "Double":
												values.add(Double.parseDouble(tmp));
												buffer.append(" ?, ");
												break;
											case "Date":
												values.add(DateUtils.toDate(tmp));
												buffer.append(" ?, ");
												break;
											case "String":
												values.add(tmp);
												buffer.append(" ?, ");
												break;
											default:
												values.add(tmp);
												buffer.append(" ?, ");
												break;
											}
										}
										buffer.delete(buffer.length() - 2, buffer.length());
										buffer.append(" ) ");
									} else {
										tmp = value.toString();
										if (StringUtils.isNotEmpty(m.getSql())) {
											String sql = m.getSql();
											sql = QueryUtils.replaceSQLParas(sql, params);// 动态参数替换
											buffer.append(sql);
										} else {
											buffer.append(" ");
											if (StringUtils.isNotEmpty(tableAlias)) {
												buffer.append(tableAlias).append(".");
											}
											if (StringUtils.isEmpty(operator)) {
												operator = "=";
											}
											buffer.append(m.getColumnName()).append(" ").append(operator).append(" ? ");

											switch (columnType) {
											case "Integer":
												values.add(Integer.parseInt(tmp));
												break;
											case "Long":
												values.add(Long.parseLong(tmp));
												break;
											case "Double":
												values.add(Double.parseDouble(tmp));
												break;
											case "Date":
												values.add(DateUtils.toDate(tmp));
												break;
											case "String":
												if (StringUtils.equalsIgnoreCase(operator, "LIKE")
														|| StringUtils.equalsIgnoreCase(operator, "NOT LIKE")) {
													if (StringUtils.contains(tmp, "%")) {
														values.add("%" + tmp + "%");
													} else {
														values.add(tmp);
													}
												} else {
													values.add(tmp);
												}
												break;
											default:
												values.add(tmp);
												break;
											}
										}
									}
								} else {
									/**
									 * 参数必须但如果参数没有值，抛出异常
									 */
									if (StringUtils.equals(m.getRequiredFlag(), "Y")) {
										throw new java.lang.IllegalArgumentException(m.getParamName() + " is null");
									}
								}
								break;
							}
						}
					}
				}
				sqlExecutor.setParameter(values);
				sqlExecutor.setSql(buffer.toString());
			}
		}
		return sqlExecutor;
	}

	public DynamicSqlTreeService getDynamicSqlTreeService() {
		if (dynamicSqlTreeService == null) {
			dynamicSqlTreeService = ContextFactory.getBean("com.glaf.datamgr.service.dynamicSqlTreeService");
		}
		return dynamicSqlTreeService;
	}

	public void setDynamicSqlTreeService(DynamicSqlTreeService dynamicSqlTreeService) {
		this.dynamicSqlTreeService = dynamicSqlTreeService;
	}

}
