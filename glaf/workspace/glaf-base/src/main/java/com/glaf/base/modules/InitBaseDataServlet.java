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

package com.glaf.base.modules;

import java.util.List;

import javax.servlet.http.HttpServlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.business.SqlUpdateBean;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.utils.ContextUtil;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;

public class InitBaseDataServlet extends HttpServlet {
	private static final long serialVersionUID = 2072103368980714549L;

	private final static Log logger = LogFactory.getLog(InitBaseDataServlet.class);

	private BaseDataManager bdm = BaseDataManager.getInstance();// 基础信息管理

	public void init() {
		long startTime = System.currentTimeMillis();
		try {
			SqlUpdateBean bean = new SqlUpdateBean();
			bean.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("执行数据库脚本错误！");
		}

		try {
			SysUserService sysUserService = ContextFactory.getBean("sysUserService");
			if (sysUserService.findById("admin") == null) {
				SysUser bean = new SysUser();
				bean.setActorId("admin");
				bean.setName("admin");
				bean.setAdminFlag("1");
				bean.setLocked(0);
				bean.setCreateBy("system");
				bean.setId(1);
				bean.setLocked(0);
				bean.setDeleteFlag(0);
				bean.setAccountType(9);
				bean.setUserType(9);
				String password = "888888";
				bean.setPasswordHash(password);
				sysUserService.create(bean);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("初始化系统用户信息失败！");
		}

		logger.info("初始化基础信息...");
		try {
			bdm.refreshBaseData();// 刷新数据
			bdm.startScheduler();// 启动定时调度,刷新数据
			logger.info("初始化基础信息完成.");
			// 装载系统功能列表
			ContextUtil.put("function", bdm.getBaseData(SysConstants.SYS_FUNCTIONS));
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("初始化基础信息失败！");
		}

		try {
			InitDataBean bean = new InitDataBean();
			bean.reload();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("初始化数据失败！");
		}

		java.sql.Connection conn = null;
		try {
			List<String> tables = DBUtils.getTables();
			conn = DBConnectionFactory.getConnection();
			conn.setAutoCommit(false);
			String ddlStatements = "";
			for (String table : tables) {
				if (StringUtils.startsWithIgnoreCase(table, "log_")) {
					ddlStatements = " TRUNCATE TABLE " + table;
					DBUtils.executeSchemaResourceIgnoreException(conn, ddlStatements);
					logger.debug(ddlStatements);
					ddlStatements = " DROP TABLE " + table;
					DBUtils.executeSchemaResourceIgnoreException(conn, ddlStatements);
					logger.debug(ddlStatements);
					conn.commit();
				}
			}
			conn.commit();
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			JdbcUtils.close(conn);
		}
		logger.info("耗时：" + (System.currentTimeMillis() - startTime) + " ms.");
	}
}