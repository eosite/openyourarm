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
public class LoginModuleDomainFactory {

	public static final String TABLENAME = "LOGIN_MODULE";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	protected static TableModel tableModel;

	static {
		columnMap.put("id", "ID_");
		columnMap.put("title", "TITLE_");
		columnMap.put("content", "CONTENT_");
		columnMap.put("appId", "APPID_");
		columnMap.put("appSecret", "APPSECRET_");
		columnMap.put("publicKey", "PUBLICKEY_");
		columnMap.put("privateKey", "PRIVATEKEY_");
		columnMap.put("peerPublicKey", "PEERPUBLICKEY_");
		columnMap.put("token", "TOKEN_");
		columnMap.put("url", "URL_");
		columnMap.put("loginUrl", "LOGINURL_");
		columnMap.put("loginUserId", "LOGINUSERID_");
		columnMap.put("serverId", "SERVERID_");
		columnMap.put("systemCode", "SYSTEMCODE_");
		columnMap.put("timeLive", "TIMELIVE_");
		columnMap.put("type", "TYPE_");
		columnMap.put("locked", "LOCKED_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("createTime", "CREATETIME_");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("title", "String");
		javaTypeMap.put("content", "String");
		javaTypeMap.put("appId", "String");
		javaTypeMap.put("appSecret", "String");
		javaTypeMap.put("publicKey", "String");
		javaTypeMap.put("privateKey", "String");
		javaTypeMap.put("peerPublicKey", "String");
		javaTypeMap.put("token", "String");
		javaTypeMap.put("url", "String");
		javaTypeMap.put("loginUrl", "String");
		javaTypeMap.put("loginUserId", "String");
		javaTypeMap.put("serverId", "Long");
		javaTypeMap.put("systemCode", "String");
		javaTypeMap.put("timeLive", "Integer");
		javaTypeMap.put("type", "String");
		javaTypeMap.put("locked", "Integer");
		javaTypeMap.put("createBy", "String");
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
		tableDefinition.setName("LoginModule");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition title = new ColumnDefinition();
		title.setName("title");
		title.setColumnName("TITLE_");
		title.setJavaType("String");
		title.setLength(200);
		tableDefinition.addColumn(title);

		ColumnDefinition content = new ColumnDefinition();
		content.setName("content");
		content.setColumnName("CONTENT_");
		content.setJavaType("String");
		content.setLength(2000);
		tableDefinition.addColumn(content);

		ColumnDefinition appId = new ColumnDefinition();
		appId.setName("appId");
		appId.setColumnName("APPID_");
		appId.setJavaType("String");
		appId.setLength(200);
		tableDefinition.addColumn(appId);

		ColumnDefinition appSecret = new ColumnDefinition();
		appSecret.setName("appSecret");
		appSecret.setColumnName("APPSECRET_");
		appSecret.setJavaType("String");
		appSecret.setLength(200);
		tableDefinition.addColumn(appSecret);

		ColumnDefinition publicKey = new ColumnDefinition();
		publicKey.setName("publicKey");
		publicKey.setColumnName("PUBLICKEY_");
		publicKey.setJavaType("String");
		publicKey.setLength(4000);
		tableDefinition.addColumn(publicKey);

		ColumnDefinition privateKey = new ColumnDefinition();
		privateKey.setName("privateKey");
		privateKey.setColumnName("PRIVATEKEY_");
		privateKey.setJavaType("String");
		privateKey.setLength(4000);
		tableDefinition.addColumn(privateKey);

		ColumnDefinition peerPublicKey = new ColumnDefinition();
		peerPublicKey.setName("peerPublicKey");
		peerPublicKey.setColumnName("PEERPUBLICKEY_");
		peerPublicKey.setJavaType("String");
		peerPublicKey.setLength(4000);
		tableDefinition.addColumn(peerPublicKey);

		ColumnDefinition token = new ColumnDefinition();
		token.setName("token");
		token.setColumnName("TOKEN_");
		token.setJavaType("String");
		token.setLength(200);
		tableDefinition.addColumn(token);

		ColumnDefinition url = new ColumnDefinition();
		url.setName("url");
		url.setColumnName("URL_");
		url.setJavaType("String");
		url.setLength(500);
		tableDefinition.addColumn(url);

		ColumnDefinition loginUrl = new ColumnDefinition();
		loginUrl.setName("loginUrl");
		loginUrl.setColumnName("LOGINURL_");
		loginUrl.setJavaType("String");
		loginUrl.setLength(500);
		tableDefinition.addColumn(loginUrl);

		ColumnDefinition loginUserId = new ColumnDefinition();
		loginUserId.setName("loginUserId");
		loginUserId.setColumnName("LOGINUSERID_");
		loginUserId.setJavaType("String");
		loginUserId.setLength(50);
		tableDefinition.addColumn(loginUserId);

		ColumnDefinition serverId = new ColumnDefinition();
		serverId.setName("serverId");
		serverId.setColumnName("SERVERID_");
		serverId.setJavaType("Long");
		tableDefinition.addColumn(serverId);

		ColumnDefinition systemCode = new ColumnDefinition();
		systemCode.setName("systemCode");
		systemCode.setColumnName("SYSTEMCODE_");
		systemCode.setJavaType("String");
		systemCode.setLength(50);
		tableDefinition.addColumn(systemCode);

		ColumnDefinition timeLive = new ColumnDefinition();
		timeLive.setName("timeLive");
		timeLive.setColumnName("TIMELIVE_");
		timeLive.setJavaType("Integer");
		tableDefinition.addColumn(timeLive);

		ColumnDefinition type = new ColumnDefinition();
		type.setName("type");
		type.setColumnName("TYPE_");
		type.setJavaType("String");
		type.setLength(50);
		tableDefinition.addColumn(type);

		ColumnDefinition locked = new ColumnDefinition();
		locked.setName("locked");
		locked.setColumnName("LOCKED_");
		locked.setJavaType("Integer");
		tableDefinition.addColumn(locked);

		ColumnDefinition createBy = new ColumnDefinition();
		createBy.setName("createBy");
		createBy.setColumnName("CREATEBY_");
		createBy.setJavaType("String");
		createBy.setLength(50);
		tableDefinition.addColumn(createBy);

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

	private LoginModuleDomainFactory() {

	}

}
