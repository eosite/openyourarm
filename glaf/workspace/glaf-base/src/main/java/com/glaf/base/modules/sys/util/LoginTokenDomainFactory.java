package com.glaf.base.modules.sys.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.FilterDescriptor;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;

/**
 * 
 * 实体数据工厂类
 *
 */
public class LoginTokenDomainFactory {

	public static final String TABLENAME = "SYS_LOGIN_TOKEN";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID_");
		columnMap.put("serverId", "SERVERID_");
		columnMap.put("userId", "USERID_");
		columnMap.put("clientIP", "CLIENTIP_");
		columnMap.put("signature", "SIGNATURE_");
		columnMap.put("token", "TOKEN_");
		columnMap.put("nonce", "NONCE_");
		columnMap.put("sysCode", "SYSCODE_");
		columnMap.put("loginModuleId", "LOGINMODULEID_");
		columnMap.put("timeLive", "TIMELIVE_");
		columnMap.put("timeMillis", "TIMEMILLIS_");
		columnMap.put("createTime", "CREATETIME_");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("serverId", "Long");
		javaTypeMap.put("userId", "String");
		javaTypeMap.put("clientIP", "String");
		javaTypeMap.put("signature", "String");
		javaTypeMap.put("token", "String");
		javaTypeMap.put("nonce", "String");
		javaTypeMap.put("sysCode", "String");
		javaTypeMap.put("loginModuleId", "String");
		javaTypeMap.put("timeLive", "Integer");
		javaTypeMap.put("timeMillis", "Long");
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
		tableDefinition.setName("LoginToken");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("String");
		idColumn.setLength(100);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition serverId = new ColumnDefinition();
		serverId.setName("serverId");
		serverId.setColumnName("SERVERID_");
		serverId.setJavaType("Long");
		tableDefinition.addColumn(serverId);

		ColumnDefinition userId = new ColumnDefinition();
		userId.setName("userId");
		userId.setColumnName("USERID_");
		userId.setJavaType("String");
		userId.setLength(200);
		tableDefinition.addColumn(userId);

		ColumnDefinition clientIP = new ColumnDefinition();
		clientIP.setName("clientIP");
		clientIP.setColumnName("CLIENTIP_");
		clientIP.setJavaType("String");
		clientIP.setLength(200);
		tableDefinition.addColumn(clientIP);

		ColumnDefinition signature = new ColumnDefinition();
		signature.setName("signature");
		signature.setColumnName("SIGNATURE_");
		signature.setJavaType("String");
		signature.setLength(500);
		tableDefinition.addColumn(signature);

		ColumnDefinition token = new ColumnDefinition();
		token.setName("token");
		token.setColumnName("TOKEN_");
		token.setJavaType("String");
		token.setLength(200);
		tableDefinition.addColumn(token);

		ColumnDefinition sysCode = new ColumnDefinition();
		sysCode.setName("sysCode");
		sysCode.setColumnName("SYSCODE_");
		sysCode.setJavaType("String");
		sysCode.setLength(50);
		tableDefinition.addColumn(sysCode);

		ColumnDefinition nonce = new ColumnDefinition();
		nonce.setName("nonce");
		nonce.setColumnName("NONCE_");
		nonce.setJavaType("String");
		nonce.setLength(50);
		tableDefinition.addColumn(nonce);

		ColumnDefinition loginModuleId = new ColumnDefinition();
		loginModuleId.setName("loginModuleId");
		loginModuleId.setColumnName("LOGINMODULEID_");
		loginModuleId.setJavaType("String");
		loginModuleId.setLength(50);
		tableDefinition.addColumn(loginModuleId);

		ColumnDefinition timeLive = new ColumnDefinition();
		timeLive.setName("timeLive");
		timeLive.setColumnName("TIMELIVE_");
		timeLive.setJavaType("Integer");
		tableDefinition.addColumn(timeLive);

		ColumnDefinition timeMillis = new ColumnDefinition();
		timeMillis.setName("timeMillis");
		timeMillis.setColumnName("TIMEMILLIS_");
		timeMillis.setJavaType("Long");
		tableDefinition.addColumn(timeMillis);

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

	private LoginTokenDomainFactory() {

	}

}
