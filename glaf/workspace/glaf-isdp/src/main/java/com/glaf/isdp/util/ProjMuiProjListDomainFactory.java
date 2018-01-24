package com.glaf.isdp.util;

import java.util.List;
import java.util.Map;
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
public class ProjMuiProjListDomainFactory {

	public static final String TABLENAME = "PROJ_MUIPROJLIST";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("indexId", "INDEX_ID");
		columnMap.put("id", "ID");
		columnMap.put("intFlag", "INTFLAG");
		columnMap.put("sysId", "SYS_ID");
		columnMap.put("projName", "PROJNAME");
		columnMap.put("num", "NUM");
		columnMap.put("ctime", "CTIME");
		columnMap.put("content", "CONTENT");
		columnMap.put("dbName", "SDBNAME");
		columnMap.put("serverName", "SSERVERNAME");
		columnMap.put("user", "SUSER");
		columnMap.put("password", "SPASSWORD");
		columnMap.put("listNo", "LISTNO");
		columnMap.put("email", "EMAIL");
		columnMap.put("parentId", "PARENT_ID");
		columnMap.put("nodeIco", "NODEICO");
		columnMap.put("intLine", "INTLINE");
		columnMap.put("domainIndex", "DOMAIN_INDEX");
		columnMap.put("intLocal", "INTLOCAL");
		columnMap.put("emailPSW", "EMAIL_PSW");
		columnMap.put("intConnected", "INTCONNECTED");
		columnMap.put("emails", "EMAIL_S");
		columnMap.put("intorgLevel", "INTORGLEVEL");
		columnMap.put("intSendType", "INTSENDTYPE");
		columnMap.put("emailBackup", "EMAIL_BACKUP");
		columnMap.put("emailImplement", "EMAIL_IMPLEMENT");

		javaTypeMap.put("indexId", "Integer");
		javaTypeMap.put("id", "String");
		javaTypeMap.put("intFlag", "Integer");
		javaTypeMap.put("sysId", "String");
		javaTypeMap.put("projName", "String");
		javaTypeMap.put("num", "String");
		javaTypeMap.put("ctime", "Date");
		javaTypeMap.put("content", "String");
		javaTypeMap.put("dbName", "String");
		javaTypeMap.put("serverName", "String");
		javaTypeMap.put("user", "String");
		javaTypeMap.put("password", "String");
		javaTypeMap.put("listNo", "Integer");
		javaTypeMap.put("email", "String");
		javaTypeMap.put("parentId", "Integer");
		javaTypeMap.put("nodeIco", "Integer");
		javaTypeMap.put("intLine", "Integer");
		javaTypeMap.put("domainIndex", "Integer");
		javaTypeMap.put("intLocal", "Integer");
		javaTypeMap.put("emailPSW", "String");
		javaTypeMap.put("intConnected", "Integer");
		javaTypeMap.put("emails", "String");
		javaTypeMap.put("intorgLevel", "Integer");
		javaTypeMap.put("intSendType", "Integer");
		javaTypeMap.put("emailBackup", "String");
		javaTypeMap.put("emailImplement", "String");
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
		tableDefinition.setName("ProjMuiProjList");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("indexId");
		idColumn.setColumnName("INDEX_ID");
		idColumn.setJavaType("Integer");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition id = new ColumnDefinition();
		id.setName("id");
		id.setColumnName("ID");
		id.setJavaType("String");
		id.setLength(100);
		tableDefinition.addColumn(id);

		ColumnDefinition intFlag = new ColumnDefinition();
		intFlag.setName("intFlag");
		intFlag.setColumnName("INTFLAG");
		intFlag.setJavaType("Integer");
		tableDefinition.addColumn(intFlag);

		ColumnDefinition sysId = new ColumnDefinition();
		sysId.setName("sysId");
		sysId.setColumnName("SYS_ID");
		sysId.setJavaType("String");
		sysId.setLength(50);
		tableDefinition.addColumn(sysId);

		ColumnDefinition projName = new ColumnDefinition();
		projName.setName("projName");
		projName.setColumnName("PROJNAME");
		projName.setJavaType("String");
		projName.setLength(250);
		tableDefinition.addColumn(projName);

		ColumnDefinition num = new ColumnDefinition();
		num.setName("num");
		num.setColumnName("NUM");
		num.setJavaType("String");
		num.setLength(50);
		tableDefinition.addColumn(num);

		ColumnDefinition ctime = new ColumnDefinition();
		ctime.setName("ctime");
		ctime.setColumnName("CTIME");
		ctime.setJavaType("Date");
		tableDefinition.addColumn(ctime);

		ColumnDefinition content = new ColumnDefinition();
		content.setName("content");
		content.setColumnName("CONTENT");
		content.setJavaType("String");
		content.setLength(200);
		tableDefinition.addColumn(content);

		ColumnDefinition dbName = new ColumnDefinition();
		dbName.setName("dbName");
		dbName.setColumnName("SDBNAME");
		dbName.setJavaType("String");
		dbName.setLength(100);
		tableDefinition.addColumn(dbName);

		ColumnDefinition serverName = new ColumnDefinition();
		serverName.setName("serverName");
		serverName.setColumnName("SSERVERNAME");
		serverName.setJavaType("String");
		serverName.setLength(150);
		tableDefinition.addColumn(serverName);

		ColumnDefinition user = new ColumnDefinition();
		user.setName("user");
		user.setColumnName("SUSER");
		user.setJavaType("String");
		user.setLength(100);
		tableDefinition.addColumn(user);

		ColumnDefinition password = new ColumnDefinition();
		password.setName("password");
		password.setColumnName("SPASSWORD");
		password.setJavaType("String");
		password.setLength(50);
		tableDefinition.addColumn(password);

		ColumnDefinition listNo = new ColumnDefinition();
		listNo.setName("listNo");
		listNo.setColumnName("LISTNO");
		listNo.setJavaType("Integer");
		tableDefinition.addColumn(listNo);

		ColumnDefinition email = new ColumnDefinition();
		email.setName("email");
		email.setColumnName("EMAIL");
		email.setJavaType("String");
		email.setLength(100);
		tableDefinition.addColumn(email);

		ColumnDefinition parentId = new ColumnDefinition();
		parentId.setName("parentId");
		parentId.setColumnName("PARENT_ID");
		parentId.setJavaType("Integer");
		tableDefinition.addColumn(parentId);

		ColumnDefinition nodeIco = new ColumnDefinition();
		nodeIco.setName("nodeIco");
		nodeIco.setColumnName("NODEICO");
		nodeIco.setJavaType("Integer");
		tableDefinition.addColumn(nodeIco);

		ColumnDefinition intLine = new ColumnDefinition();
		intLine.setName("intLine");
		intLine.setColumnName("INTLINE");
		intLine.setJavaType("Integer");
		tableDefinition.addColumn(intLine);

		ColumnDefinition domainIndex = new ColumnDefinition();
		domainIndex.setName("domainIndex");
		domainIndex.setColumnName("DOMAIN_INDEX");
		domainIndex.setJavaType("Integer");
		tableDefinition.addColumn(domainIndex);

		ColumnDefinition intLocal = new ColumnDefinition();
		intLocal.setName("intLocal");
		intLocal.setColumnName("INTLOCAL");
		intLocal.setJavaType("Integer");
		tableDefinition.addColumn(intLocal);

		ColumnDefinition emailPSW = new ColumnDefinition();
		emailPSW.setName("emailPSW");
		emailPSW.setColumnName("EMAIL_PSW");
		emailPSW.setJavaType("String");
		emailPSW.setLength(50);
		tableDefinition.addColumn(emailPSW);

		ColumnDefinition intConnected = new ColumnDefinition();
		intConnected.setName("intConnected");
		intConnected.setColumnName("INTCONNECTED");
		intConnected.setJavaType("Integer");
		tableDefinition.addColumn(intConnected);

		ColumnDefinition emails = new ColumnDefinition();
		emails.setName("emails");
		emails.setColumnName("EMAIL_S");
		emails.setJavaType("String");
		emails.setLength(100);
		tableDefinition.addColumn(emails);

		ColumnDefinition intorgLevel = new ColumnDefinition();
		intorgLevel.setName("intorgLevel");
		intorgLevel.setColumnName("INTORGLEVEL");
		intorgLevel.setJavaType("Integer");
		tableDefinition.addColumn(intorgLevel);

		ColumnDefinition intSendType = new ColumnDefinition();
		intSendType.setName("intSendType");
		intSendType.setColumnName("INTSENDTYPE");
		intSendType.setJavaType("Integer");
		tableDefinition.addColumn(intSendType);

		ColumnDefinition emailBackup = new ColumnDefinition();
		emailBackup.setName("emailBackup");
		emailBackup.setColumnName("EMAIL_BACKUP");
		emailBackup.setJavaType("String");
		emailBackup.setLength(50);
		tableDefinition.addColumn(emailBackup);

		ColumnDefinition emailImplement = new ColumnDefinition();
		emailImplement.setName("emailImplement");
		emailImplement.setColumnName("EMAIL_IMPLEMENT");
		emailImplement.setJavaType("String");
		emailImplement.setLength(50);
		tableDefinition.addColumn(emailImplement);

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
		if (dataRequest.getFilter() != null) {
			if (dataRequest.getFilter().getField() != null) {
				dataRequest.getFilter().setColumn(
						columnMap.get(dataRequest.getFilter().getField()));
				dataRequest.getFilter().setJavaType(
						javaTypeMap.get(dataRequest.getFilter().getField()));
			}

			List<FilterDescriptor> filters = dataRequest.getFilter()
					.getFilters();
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

	private ProjMuiProjListDomainFactory() {

	}

}
