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

package com.glaf.datamgr.web.springmvc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.service.ISystemParamService;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.ZipUtils;
import com.glaf.datamgr.dataexport.DbTableToSqliteExporter;
import com.glaf.datamgr.dataexport.DbToDBMyBatisExporter;

@Controller("/sys/export")
@RequestMapping("/sys/export")
public class DbExportController {
	protected static final Log logger = LogFactory.getLog(DbExportController.class);

	protected static Configuration conf = BaseConfiguration.create();

	protected static AtomicBoolean running = new AtomicBoolean(false);

	protected ITableDataService tableDataService;

	protected ITableDefinitionService tableDefinitionService;

	protected ISystemParamService systemParamService;

	@ResponseBody
	@RequestMapping("/exportDB")
	public void exportDB(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String systemName = request.getParameter("systemName");
		if (systemName == null) {
			systemName = "default";
		}
		String dataPath = null;
		String filename = null;
		String dbType = request.getParameter("dbType");
		logger.debug("dbType:" + dbType);
		if (StringUtils.isNotEmpty(dbType)) {
			if (!running.get()) {
				InputStream inputStream = null;
				try {
					running.set(true);
					if (StringUtils.equals(dbType, "h2")) {
						dataPath = "/data/" + DateUtils.getNowYearMonthDay() + "/glafdb";
						filename = "/data/" + DateUtils.getNowYearMonthDay() + "/glafdb.h2.db";
						FileUtils.mkdirs("/data/" + DateUtils.getNowYearMonthDay());
						DbToDBMyBatisExporter exp = new DbToDBMyBatisExporter();
						exp.exportTables(systemName, "h2", dataPath);
					} else if (StringUtils.equals(dbType, "sqlite")) {
						dataPath = "/data/" + DateUtils.getNowYearMonthDay() + "/glafdb.db";
						filename = dataPath;
						FileUtils.mkdirs("/data/" + DateUtils.getNowYearMonthDay());
						DbToDBMyBatisExporter exp = new DbToDBMyBatisExporter();
						exp.exportTables(systemName, "sqlite", dataPath);
					}
					if (dataPath != null) {
						File file = new File(filename);
						File[] files = { file };
						ZipUtils.compressFile(files, filename + ".zip");
						inputStream = FileUtils.getInputStream(filename + ".zip");
						ResponseUtils.download(request, response, inputStream, "glafdb_" + dbType + ".zip");
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					running.set(false);
					IOUtils.closeStream(inputStream);
				}
			}
		}
	}

	@ResponseBody
	@RequestMapping("/exportSQlite")
	public void exportSQlite(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String rows = request.getParameter("tables");
		if (StringUtils.isNotEmpty(rows)) {
			List<String> list = StringTools.split(rows);
			List<String> tables = new ArrayList<String>();
			if (list != null && !list.isEmpty()) {
				for (String table : list) {
					if (!DBUtils.isAllowedTable(table)) {
						continue;
					}
					tables.add(table);
				}
			}
			DbTableToSqliteExporter exp = new DbTableToSqliteExporter();
			InputStream inputStream = null;
			try {

				String dbpath = SystemProperties.getConfigRootPath() + "/temp/glaf_sqlite.db";
				FileUtils.deleteFile(dbpath);
				exp.exportTables("default", dbpath, tables);
				inputStream = FileUtils.getInputStream(dbpath);
				ResponseUtils.download(request, response, inputStream, "glaf_sqlite.db");
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				running.set(false);
				IOUtils.closeStream(inputStream);
			}
		}
	}

	@javax.annotation.Resource
	public void setSystemParamService(ISystemParamService systemParamService) {
		this.systemParamService = systemParamService;
	}

	@javax.annotation.Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

	@javax.annotation.Resource
	public void setTableDefinitionService(ITableDefinitionService tableDefinitionService) {
		this.tableDefinitionService = tableDefinitionService;
	}

}
