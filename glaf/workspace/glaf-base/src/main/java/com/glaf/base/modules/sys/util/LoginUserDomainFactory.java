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
public class LoginUserDomainFactory {

	public static final String TABLENAME = "LOGIN_USER";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	protected static TableModel tableModel;

	static {
		columnMap.put("id", "ID_");
		columnMap.put("userId", "USERID_");
		columnMap.put("name", "USERNAME_");
		columnMap.put("loginId", "LOGINID_");
		columnMap.put("password", "PASSWORD_");
		columnMap.put("passwordType", "PASSWORDTYPE_");
		columnMap.put("systemCode", "SYSTEMCODE_");
		columnMap.put("organization", "ORGANIZATION_");
		columnMap.put("department", "DEPARTMENT_");
		columnMap.put("position", "POSITION_");
		columnMap.put("mail", "MAIL_");
		columnMap.put("mobile", "MOBILE_");
		columnMap.put("timeLive", "TIMELIVE_");
		columnMap.put("loginTime", "LOGINTIME_");
		columnMap.put("attribute", "ATTRIBUTE_");
		columnMap.put("createBy", "CREATEBY_");
		columnMap.put("createTime", "CREATETIME_");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("userId", "String");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("loginId", "String");
		javaTypeMap.put("password", "String");
		javaTypeMap.put("passwordType", "String");
		javaTypeMap.put("systemCode", "String");
		javaTypeMap.put("organization", "String");
		javaTypeMap.put("department", "String");
		javaTypeMap.put("position", "String");
		javaTypeMap.put("mail", "String");
		javaTypeMap.put("mobile", "String");
		javaTypeMap.put("timeLive", "Integer");
		javaTypeMap.put("loginTime", "Date");
		javaTypeMap.put("attribute", "String");
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
		tableDefinition.setName("LoginUser");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition userId = new ColumnDefinition();
		userId.setName("userId");
		userId.setColumnName("USERID_");
		userId.setJavaType("String");
		userId.setLength(200);
		tableDefinition.addColumn(userId);

		ColumnDefinition name = new ColumnDefinition();
		name.setName("name");
		name.setColumnName("USERNAME_");
		name.setJavaType("String");
		name.setLength(50);
		tableDefinition.addColumn(name);

		ColumnDefinition loginId = new ColumnDefinition();
		loginId.setName("loginId");
		loginId.setColumnName("LOGINID_");
		loginId.setJavaType("String");
		loginId.setLength(500);
		tableDefinition.addColumn(loginId);

		ColumnDefinition password = new ColumnDefinition();
		password.setName("password");
		password.setColumnName("PASSWORD_");
		password.setJavaType("String");
		password.setLength(500);
		tableDefinition.addColumn(password);

		ColumnDefinition passwordType = new ColumnDefinition();
		passwordType.setName("passwordType");
		passwordType.setColumnName("PASSWORDTYPE_");
		passwordType.setJavaType("String");
		passwordType.setLength(50);
		tableDefinition.addColumn(passwordType);

		ColumnDefinition systemCode = new ColumnDefinition();
		systemCode.setName("systemCode");
		systemCode.setColumnName("SYSTEMCODE_");
		systemCode.setJavaType("String");
		systemCode.setLength(50);
		tableDefinition.addColumn(systemCode);

		ColumnDefinition organization = new ColumnDefinition();
		organization.setName("organization");
		organization.setColumnName("ORGANIZATION_");
		organization.setJavaType("String");
		organization.setLength(250);
		tableDefinition.addColumn(organization);

		ColumnDefinition department = new ColumnDefinition();
		department.setName("department");
		department.setColumnName("DEPARTMENT_");
		department.setJavaType("String");
		department.setLength(250);
		tableDefinition.addColumn(department);

		ColumnDefinition position = new ColumnDefinition();
		position.setName("position");
		position.setColumnName("POSITION_");
		position.setJavaType("String");
		position.setLength(250);
		tableDefinition.addColumn(position);

		ColumnDefinition mail = new ColumnDefinition();
		mail.setName("mail");
		mail.setColumnName("MAIL_");
		mail.setJavaType("String");
		mail.setLength(200);
		tableDefinition.addColumn(mail);

		ColumnDefinition mobile = new ColumnDefinition();
		mobile.setName("mobile");
		mobile.setColumnName("MOBILE_");
		mobile.setJavaType("String");
		mobile.setLength(50);
		tableDefinition.addColumn(mobile);

		ColumnDefinition timeLive = new ColumnDefinition();
		timeLive.setName("timeLive");
		timeLive.setColumnName("TIMELIVE_");
		timeLive.setJavaType("Integer");
		tableDefinition.addColumn(timeLive);

		ColumnDefinition loginTime = new ColumnDefinition();
		loginTime.setName("loginTime");
		loginTime.setColumnName("LOGINTIME_");
		loginTime.setJavaType("Date");
		tableDefinition.addColumn(loginTime);

		ColumnDefinition attribute = new ColumnDefinition();
		attribute.setName("attribute");
		attribute.setColumnName("ATTRIBUTE_");
		attribute.setJavaType("Clob");
		attribute.setLength(2000);
		tableDefinition.addColumn(attribute);

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

	private LoginUserDomainFactory() {

	}

}
