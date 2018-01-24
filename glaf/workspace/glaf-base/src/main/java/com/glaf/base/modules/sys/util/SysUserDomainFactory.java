/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
public class SysUserDomainFactory {

	public static final String TABLENAME = "USERINFO";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("account", "USERID");
		columnMap.put("accountType", "ACCOUNTTYPE");
		columnMap.put("adminFlag", "ISSYSTEM");
		columnMap.put("status", "STATUS");
		columnMap.put("createBy", "CREATEBY");
		columnMap.put("createTime", "CTIME");
		columnMap.put("deptId", "DEPID");
		columnMap.put("deptName", "DEPTNAME");
		columnMap.put("roleName", "ROLENAME");
		columnMap.put("email", "EMAIL");
		columnMap.put("evection", "EVECTION");
		columnMap.put("fax", "FAX");
		columnMap.put("gender", "GENDER");
		columnMap.put("headship", "HEADSHIP");
		columnMap.put("lastLoginIP", "LASTLOGINIP");
		columnMap.put("lastLoginTime", "LASTLOGINTIME");
		columnMap.put("mqLoginFlag", "MQLOGINFLAG_");
		columnMap.put("secretLoginFlag", "SECRETLOGINFLAG_");
		columnMap.put("loginSecret", "LOGINSECRET_");
		columnMap.put("loginSecretUpdateTime", "LOGINSECRETUPDATETIME_");
		columnMap.put("mobile", "MOBILE");
		columnMap.put("name", "USERNAME");
		columnMap.put("showName", "SHOWNAME");
		columnMap.put("code", "CODE");
		columnMap.put("password", "PASSWORD");
		columnMap.put("passwordHash", "PASSWORD_HASH");
		columnMap.put("superiorIds", "SUPERIORIDS");
		columnMap.put("telephone", "TELEPHONE");
		columnMap.put("updateBy", "UPDATEBY");
		columnMap.put("updateDate", "UPDATEDATE");
		columnMap.put("userType", "USERTYPE");
		columnMap.put("lastLoginTime", "LASTLOGINTIME");
		columnMap.put("lastLoginIP", "LASTLOGINIP");
		columnMap.put("lockLoginTime", "LOCKLOGINTIME");
		columnMap.put("loginRetry", "LOGINRETRY");
		columnMap.put("appId", "APPID");
		columnMap.put("appSecret", "APPSECRET");
		columnMap.put("token", "TOKEN");
		columnMap.put("deleteFlag", "DELETEFLAG");
		columnMap.put("deleteTime", "DELETETIME");

		javaTypeMap.put("account", "String");
		javaTypeMap.put("accountType", "Integer");
		javaTypeMap.put("adminFlag", "String");
		javaTypeMap.put("status", "String");
		javaTypeMap.put("createBy", "String");
		javaTypeMap.put("createTime", "Date");
		javaTypeMap.put("deptId", "Long");
		javaTypeMap.put("deptName", "String");
		javaTypeMap.put("roleName", "String");
		javaTypeMap.put("email", "String");
		javaTypeMap.put("evection", "Integer");
		javaTypeMap.put("fax", "String");
		javaTypeMap.put("gender", "Integer");
		javaTypeMap.put("headship", "String");
		javaTypeMap.put("lastLoginIP", "String");
		javaTypeMap.put("lastLoginTime", "Date");
		javaTypeMap.put("mqLoginFlag", "String");
		javaTypeMap.put("secretLoginFlag", "String");
		javaTypeMap.put("loginSecret", "String");
		javaTypeMap.put("loginSecretUpdateTime", "Date");
		javaTypeMap.put("mobile", "String");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("showName", "String");
		javaTypeMap.put("code", "String");
		javaTypeMap.put("password", "String");
		javaTypeMap.put("passwordHash", "String");
		javaTypeMap.put("superiorIds", "String");
		javaTypeMap.put("telephone", "String");
		javaTypeMap.put("updateBy", "String");
		javaTypeMap.put("updateDate", "Date");
		javaTypeMap.put("userType", "Integer");
		javaTypeMap.put("lastLoginTime", "Date");
		javaTypeMap.put("lastLoginIP", "String");
		javaTypeMap.put("lockLoginTime", "Date");
		javaTypeMap.put("loginRetry", "Integer");
		javaTypeMap.put("appId", "String");
		javaTypeMap.put("appSecret", "String");
		javaTypeMap.put("token", "String");
		javaTypeMap.put("deleteFlag", "Integer");
		javaTypeMap.put("deleteTime", "Date");
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
		tableDefinition.setName("SysUser");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("account");
		idColumn.setColumnName("USERID");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition accountType = new ColumnDefinition();
		accountType.setName("accountType");
		accountType.setColumnName("ACCOUNTTYPE");
		accountType.setJavaType("Integer");
		tableDefinition.addColumn(accountType);

		ColumnDefinition adminFlag = new ColumnDefinition();
		adminFlag.setName("adminFlag");
		adminFlag.setColumnName("ISSYSTEM");
		adminFlag.setJavaType("String");
		adminFlag.setLength(10);
		tableDefinition.addColumn(adminFlag);

		ColumnDefinition status = new ColumnDefinition();
		status.setName("status");
		status.setColumnName("STATUS");
		status.setJavaType("String");
		status.setLength(255);
		tableDefinition.addColumn(status);

		ColumnDefinition createBy = new ColumnDefinition();
		createBy.setName("createBy");
		createBy.setColumnName("CREATEBY");
		createBy.setJavaType("String");
		createBy.setLength(50);
		tableDefinition.addColumn(createBy);

		ColumnDefinition createTime = new ColumnDefinition();
		createTime.setName("createTime");
		createTime.setColumnName("CTIME");
		createTime.setJavaType("Date");
		tableDefinition.addColumn(createTime);

		ColumnDefinition deptId = new ColumnDefinition();
		deptId.setName("deptId");
		deptId.setColumnName("DEPID");
		deptId.setJavaType("Long");
		tableDefinition.addColumn(deptId);

		ColumnDefinition deptName = new ColumnDefinition();
		deptName.setName("deptName");
		deptName.setColumnName("DEPTNAME");
		deptName.setJavaType("String");
		tableDefinition.addColumn(deptName);

		ColumnDefinition roleName = new ColumnDefinition();
		roleName.setName("roleName");
		roleName.setColumnName("ROLENAME");
		roleName.setJavaType("String");
		tableDefinition.addColumn(roleName);

		ColumnDefinition email = new ColumnDefinition();
		email.setName("email");
		email.setColumnName("EMAIL");
		email.setJavaType("String");
		email.setLength(200);
		tableDefinition.addColumn(email);

		ColumnDefinition evection = new ColumnDefinition();
		evection.setName("evection");
		evection.setColumnName("EVECTION");
		evection.setJavaType("Integer");
		tableDefinition.addColumn(evection);

		ColumnDefinition fax = new ColumnDefinition();
		fax.setName("fax");
		fax.setColumnName("FAX");
		fax.setJavaType("String");
		fax.setLength(50);
		tableDefinition.addColumn(fax);

		ColumnDefinition gender = new ColumnDefinition();
		gender.setName("gender");
		gender.setColumnName("GENDER");
		gender.setJavaType("Integer");
		tableDefinition.addColumn(gender);

		ColumnDefinition headship = new ColumnDefinition();
		headship.setName("headship");
		headship.setColumnName("HEADSHIP");
		headship.setJavaType("String");
		headship.setLength(50);
		tableDefinition.addColumn(headship);

		ColumnDefinition lastLoginIP = new ColumnDefinition();
		lastLoginIP.setName("lastLoginIP");
		lastLoginIP.setColumnName("LASTLOGINIP");
		lastLoginIP.setJavaType("String");
		lastLoginIP.setLength(80);
		tableDefinition.addColumn(lastLoginIP);

		ColumnDefinition lastLoginTime = new ColumnDefinition();
		lastLoginTime.setName("lastLoginTime");
		lastLoginTime.setColumnName("LASTLOGINTIME");
		lastLoginTime.setJavaType("Date");
		tableDefinition.addColumn(lastLoginTime);

		ColumnDefinition mqLoginFlag = new ColumnDefinition();
		mqLoginFlag.setName("mqLoginFlag");
		mqLoginFlag.setColumnName("MQLOGINFLAG_");
		mqLoginFlag.setJavaType("String");
		mqLoginFlag.setLength(1);
		tableDefinition.addColumn(mqLoginFlag);

		ColumnDefinition secretLoginFlag = new ColumnDefinition();
		secretLoginFlag.setName("secretLoginFlag");
		secretLoginFlag.setColumnName("SECRETLOGINFLAG_");
		secretLoginFlag.setJavaType("String");
		secretLoginFlag.setLength(1);
		tableDefinition.addColumn(secretLoginFlag);

		ColumnDefinition loginSecret = new ColumnDefinition();
		loginSecret.setName("loginSecret");
		loginSecret.setColumnName("LOGINSECRET_");
		loginSecret.setJavaType("String");
		loginSecret.setLength(200);
		tableDefinition.addColumn(loginSecret);

		ColumnDefinition loginSecretUpdateTime = new ColumnDefinition();
		loginSecretUpdateTime.setName("loginSecretUpdateTime");
		loginSecretUpdateTime.setColumnName("LOGINSECRETUPDATETIME_");
		loginSecretUpdateTime.setJavaType("Date");
		tableDefinition.addColumn(loginSecretUpdateTime);

		ColumnDefinition mobile = new ColumnDefinition();
		mobile.setName("mobile");
		mobile.setColumnName("MOBILE");
		mobile.setJavaType("String");
		mobile.setLength(50);
		tableDefinition.addColumn(mobile);

		ColumnDefinition name = new ColumnDefinition();
		name.setName("name");
		name.setColumnName("USERNAME");
		name.setJavaType("String");
		name.setLength(50);
		tableDefinition.addColumn(name);

		ColumnDefinition showName = new ColumnDefinition();
		showName.setName("showName");
		showName.setColumnName("SHOWNAME");
		showName.setJavaType("String");
		showName.setLength(250);
		tableDefinition.addColumn(showName);

		ColumnDefinition code = new ColumnDefinition();
		code.setName("code");
		code.setColumnName("CODE");
		code.setJavaType("String");
		code.setLength(50);
		tableDefinition.addColumn(code);

		ColumnDefinition password = new ColumnDefinition();
		password.setName("password");
		password.setColumnName("PASSWORD");
		password.setJavaType("String");
		password.setLength(250);
		tableDefinition.addColumn(password);

		ColumnDefinition passwordHash = new ColumnDefinition();
		passwordHash.setName("password");
		passwordHash.setColumnName("PASSWORD_HASH");
		passwordHash.setJavaType("String");
		passwordHash.setLength(250);
		tableDefinition.addColumn(passwordHash);

		ColumnDefinition superiorIds = new ColumnDefinition();
		superiorIds.setName("superiorIds");
		superiorIds.setColumnName("SUPERIORIDS");
		superiorIds.setJavaType("String");
		superiorIds.setLength(250);
		tableDefinition.addColumn(superiorIds);

		ColumnDefinition telephone = new ColumnDefinition();
		telephone.setName("telephone");
		telephone.setColumnName("TELEPHONE");
		telephone.setJavaType("String");
		telephone.setLength(50);
		tableDefinition.addColumn(telephone);

		ColumnDefinition updateBy = new ColumnDefinition();
		updateBy.setName("updateBy");
		updateBy.setColumnName("UPDATEBY");
		updateBy.setJavaType("String");
		updateBy.setLength(50);
		tableDefinition.addColumn(updateBy);

		ColumnDefinition updateDate = new ColumnDefinition();
		updateDate.setName("updateDate");
		updateDate.setColumnName("UPDATEDATE");
		updateDate.setJavaType("Date");
		tableDefinition.addColumn(updateDate);

		ColumnDefinition userType = new ColumnDefinition();
		userType.setName("userType");
		userType.setColumnName("USERTYPE");
		userType.setJavaType("Integer");
		tableDefinition.addColumn(userType);

		ColumnDefinition lockLoginTime = new ColumnDefinition();
		lockLoginTime.setName("lockLoginTime");
		lockLoginTime.setColumnName("LOCKLOGINTIME");
		lockLoginTime.setJavaType("Date");
		tableDefinition.addColumn(lockLoginTime);

		ColumnDefinition loginRetry = new ColumnDefinition();
		loginRetry.setName("loginRetry");
		loginRetry.setColumnName("LOGINRETRY");
		loginRetry.setJavaType("Integer");
		tableDefinition.addColumn(loginRetry);

		ColumnDefinition appId = new ColumnDefinition();
		appId.setName("appId");
		appId.setColumnName("APPID");
		appId.setJavaType("String");
		appId.setLength(50);
		tableDefinition.addColumn(appId);

		ColumnDefinition appSecret = new ColumnDefinition();
		appSecret.setName("appSecret");
		appSecret.setColumnName("APPSECRET");
		appSecret.setJavaType("String");
		appSecret.setLength(50);
		tableDefinition.addColumn(appSecret);

		ColumnDefinition token = new ColumnDefinition();
		token.setName("token");
		token.setColumnName("TOKEN");
		token.setJavaType("String");
		token.setLength(250);
		tableDefinition.addColumn(token);

		ColumnDefinition deleteFlag = new ColumnDefinition();
		deleteFlag.setName("deleteFlag");
		deleteFlag.setColumnName("DELETEFLAG");
		deleteFlag.setJavaType("Integer");
		tableDefinition.addColumn(deleteFlag);

		ColumnDefinition deleteTime = new ColumnDefinition();
		deleteTime.setName("deleteTime");
		deleteTime.setColumnName("DELETETIME");
		deleteTime.setJavaType("Date");
		tableDefinition.addColumn(deleteTime);

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

	private SysUserDomainFactory() {

	}

}
