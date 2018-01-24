package com.glaf.base.modules.sys.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.FilterDescriptor;
import com.glaf.core.base.TableModel;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;

/**
 * 
 * 实体数据工厂类
 *
 */
public class LoginMessageDomainFactory {

	public static final String TABLENAME = "SYS_LOGIN_INFO";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	protected static TableModel tableModel;

	static {
		columnMap.put("userId", "USERID_");
		columnMap.put("serverId", "SERVERID_");
		columnMap.put("clientIP", "CLIENTIP_");
		columnMap.put("token", "TOKEN_");
		columnMap.put("section", "SECTION_");
		columnMap.put("content", "CONTENT_");
		columnMap.put("uid", "UID_");
		columnMap.put("flowid", "FLOWID_");
		columnMap.put("cellTreedotIndex", "CELLTREEDOTINDEX_");
		columnMap.put("position", "POSITION_");
		columnMap.put("timeLive", "TIMELIVE_");
		columnMap.put("loginTime", "LOGINTIME_");
		columnMap.put("createTime", "CREATETIME_");

		javaTypeMap.put("userId", "String");
		javaTypeMap.put("serverId", "Long");
		javaTypeMap.put("clientIP", "String");
		javaTypeMap.put("token", "String");
		javaTypeMap.put("section", "String");
		javaTypeMap.put("content", "String");
		javaTypeMap.put("uid", "String");
		javaTypeMap.put("flowid", "String");
		javaTypeMap.put("cellTreedotIndex", "String");
		javaTypeMap.put("position", "String");
		javaTypeMap.put("timeLive", "Integer");
		javaTypeMap.put("loginTime", "Long");
		javaTypeMap.put("createTime", "Date");
	}

	public static Map<String, String> getColumnMap() {
		return columnMap;
	}

	public static Map<String, String> getJavaTypeMap() {
		return javaTypeMap;
	}

	public static TableDefinition getTableDefinition() {
		return getTableDefinition(TABLENAME);
	}

	public static TableDefinition getTableDefinition(String tableName) {
		tableName = tableName.toUpperCase();
		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition.setTableName(tableName);
		tableDefinition.setName("LoginMessage");

		ColumnDefinition token = new ColumnDefinition();
		token.setName("token");
		token.setColumnName("TOKEN_");
		token.setJavaType("String");
		token.setLength(200);
		tableDefinition.setIdColumn(token);

		ColumnDefinition serverId = new ColumnDefinition();
		serverId.setName("serverId");
		serverId.setColumnName("SERVERID_");
		serverId.setJavaType("Long");
		tableDefinition.addColumn(serverId);

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("userId");
		idColumn.setColumnName("USERID_");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition clientIP = new ColumnDefinition();
		clientIP.setName("clientIP");
		clientIP.setColumnName("CLIENTIP_");
		clientIP.setJavaType("String");
		clientIP.setLength(200);
		tableDefinition.addColumn(clientIP);

		ColumnDefinition section = new ColumnDefinition();
		section.setName("section");
		section.setColumnName("SECTION_");
		section.setJavaType("String");
		section.setLength(50);
		tableDefinition.addColumn(section);

		ColumnDefinition content = new ColumnDefinition();
		content.setName("content");
		content.setColumnName("CONTENT_");
		content.setJavaType("String");
		content.setLength(2000);
		tableDefinition.addColumn(content);

		ColumnDefinition uid = new ColumnDefinition();
		uid.setName("uid");
		uid.setColumnName("UID_");
		uid.setJavaType("String");
		uid.setLength(500);
		tableDefinition.addColumn(uid);

		ColumnDefinition flowid = new ColumnDefinition();
		flowid.setName("flowid");
		flowid.setColumnName("FLOWID_");
		flowid.setJavaType("String");
		flowid.setLength(200);
		tableDefinition.addColumn(flowid);

		ColumnDefinition cellTreedotIndex = new ColumnDefinition();
		cellTreedotIndex.setName("cellTreedotIndex");
		cellTreedotIndex.setColumnName("CELLTREEDOTINDEX_");
		cellTreedotIndex.setJavaType("String");
		cellTreedotIndex.setLength(50);
		tableDefinition.addColumn(cellTreedotIndex);

		ColumnDefinition position = new ColumnDefinition();
		position.setName("position");
		position.setColumnName("POSITION_");
		position.setJavaType("String");
		position.setLength(50);
		tableDefinition.addColumn(position);

		ColumnDefinition timeLive = new ColumnDefinition();
		timeLive.setName("timeLive");
		timeLive.setColumnName("TIMELIVE_");
		timeLive.setJavaType("Integer");
		tableDefinition.addColumn(timeLive);

		ColumnDefinition loginTime = new ColumnDefinition();
		loginTime.setName("loginTime");
		loginTime.setColumnName("LOGINTIME_");
		loginTime.setJavaType("Long");
		tableDefinition.addColumn(loginTime);

		ColumnDefinition createTime = new ColumnDefinition();
		createTime.setName("createTime");
		createTime.setColumnName("CREATETIME_");
		createTime.setJavaType("Date");
		tableDefinition.addColumn(createTime);

		return tableDefinition;
	}

	public static TableDefinition createTable() {
		TableDefinition tableDefinition = getTableDefinition(TABLENAME);
		if (!DBUtils.tableExists(TABLENAME)) {
			DBUtils.createTable(tableDefinition);
		} else {
			DBUtils.alterTable(tableDefinition);
		}
		return tableDefinition;
	}

	public static TableDefinition createTable(String tableName) {
		TableDefinition tableDefinition = getTableDefinition(tableName);
		if (!DBUtils.tableExists(tableName)) {
			DBUtils.createTable(tableDefinition);
		} else {
			DBUtils.alterTable(tableDefinition);
		}
		return tableDefinition;
	}

	public static void processDataRequest(DataRequest dataRequest) {
		if (dataRequest != null) {
			if (dataRequest.getFilter() != null) {
				if (dataRequest.getFilter().getField() != null) {
					dataRequest.getFilter().setColumn(columnMap.get(dataRequest.getFilter().getField()));
					dataRequest.getFilter().setJavaType(javaTypeMap.get(dataRequest.getFilter().getField()));
				}

				List<FilterDescriptor> filters = dataRequest.getFilter().getFilters();
				for (FilterDescriptor filter : filters) {
					filter.setParent(dataRequest.getFilter());
					if (filter.getField() != null) {
						filter.setColumn(columnMap.get(filter.getField()));
						filter.setJavaType(javaTypeMap.get(filter.getField()));
					}

					List<FilterDescriptor> subFilters = filter.getFilters();
					for (FilterDescriptor f : subFilters) {
						f.setColumn(columnMap.get(f.getField()));
						f.setJavaType(javaTypeMap.get(f.getField()));
						f.setParent(filter);
					}
				}
			}
		}
	}

	private LoginMessageDomainFactory() {

	}

}
