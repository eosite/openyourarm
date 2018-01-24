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

package com.glaf.base.modules.sys.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.JdbcUtils;

public class SysApplicationFactory {
	private static class SysApplicationHolder {
		public static SysApplicationFactory instance = new SysApplicationFactory();
	}

	protected static List<SysApplication> sysApplications = new CopyOnWriteArrayList<SysApplication>();

	protected static List<SysApplication> activeSysApplications = new CopyOnWriteArrayList<SysApplication>();

	protected static ConcurrentMap<Long, String> sysApplicationNames = new ConcurrentHashMap<Long, String>();

	protected static ConcurrentMap<String, String> pageNames = new ConcurrentHashMap<String, String>();

	public static synchronized void clearAll() {
		sysApplications.clear();
		sysApplicationNames.clear();
		activeSysApplications.clear();
	}

	public static List<SysApplication> getActiveSysApplications() {
		return activeSysApplications;
	}

	public static SysApplicationFactory getInstance() {
		return SysApplicationHolder.instance;
	}

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private SysApplicationFactory() {

	}

	public synchronized void clearSysApplications() {
		pageNames.clear();
		sysApplications.clear();
		sysApplicationNames.clear();
		activeSysApplications.clear();
	}

	public synchronized Map<String, String> getPageNames() {
		if (!pageNames.isEmpty()) {
			return pageNames;
		}
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionFactory.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(" select id_, name_ from form_page ");
			while (rs.next()) {
				pageNames.put(rs.getString(1), rs.getString(2));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("get form page error", ex);
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(stmt);
			JdbcUtils.close(conn);
		}
		return pageNames;
	}

	public synchronized List<SysApplication> getSysApplications() {
		if (!sysApplications.isEmpty()) {
			return sysApplications;
		}
		List<SysApplication> list = new ArrayList<SysApplication>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnectionFactory.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(" select * from SYS_APPLICATION ");
			while (rs.next()) {
				SysApplication model = new SysApplication();
				model.setId(rs.getLong("ID"));
				model.setName(rs.getString("NAME"));
				model.setCode(rs.getString("CODE"));
				model.setNodeId(rs.getLong("NODEID"));
				model.setLocked(rs.getInt("LOCKED"));
				model.setSort(rs.getInt("SORT"));
				model.setUrl(rs.getString("URL"));
				list.add(model);
			}
			rs.close();
			stmt.close();
			rs = null;
			stmt = null;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("get apps error", ex);
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(stmt);
			JdbcUtils.close(conn);
		}

		if (list != null && !list.isEmpty()) {
			for (SysApplication sysApplication : list) {
				sysApplications.add(sysApplication);
			}
		}
		logger.debug("sysApplications size:" + sysApplications.size());
		return sysApplications;
	}
}