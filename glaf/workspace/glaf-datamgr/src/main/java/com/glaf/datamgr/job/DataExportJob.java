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

package com.glaf.datamgr.job;

import java.io.File;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.core.base.DataFile;
import com.glaf.core.base.TableModel;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.job.BaseJob;
import com.glaf.core.service.IBlobService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.GZIPUtils;
import com.glaf.core.util.StringTools;

import com.glaf.datamgr.bean.DataExportSqliteBean;
import com.glaf.datamgr.bean.DataExportUpdateBean;
import com.glaf.datamgr.domain.DataExport;
import com.glaf.datamgr.query.DataExportQuery;
import com.glaf.datamgr.service.DataExportService;
import com.glaf.datamgr.util.TableModelReader;

public class DataExportJob extends BaseJob {

	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());

	public void runJob(JobExecutionContext context) throws JobExecutionException {
		if ((System.currentTimeMillis() - lastExecuteTime.get()) < DateUtils.HOUR) {// 1小时
			return;
		}
		IBlobService blobService = ContextFactory.getBean("blobService");
		DataExportService dataExportService = ContextFactory.getBean("dataExportService");
		DataExportQuery query = new DataExportQuery();
		query.deleteFlag(0);
		query.locked(0);
		List<DataExport> list = dataExportService.list(query);
		if (list != null && !list.isEmpty()) {
			TableModelReader reader = new TableModelReader();
			List<TableModel> tables = reader.readModels();
			DataExportUpdateBean updateBean = new DataExportUpdateBean();
			DataExportSqliteBean bean = new DataExportSqliteBean();
			for (DataExport dataExport : list) {
				java.util.Date exportTime = new java.util.Date();
				boolean success = false;
				int retry = 0;
				while (retry < 3 && !success) {
					try {
						retry++;
						List<String> tableNames = StringTools.split(dataExport.getTables());
						if (tables != null && !tables.isEmpty() && tableNames != null && !tableNames.isEmpty()) {
							String serviceKey = dataExport.getServiceKey();
							long databaseId = dataExport.getDatabaseId();
							String dbpath = SystemProperties.getConfigRootPath() + "/db/" + dataExport.getServiceKey();
							FileUtils.deleteFile(dbpath);
							for (TableModel tableModel : tables) {
								if (tableNames.contains(tableModel.getTableName())
										|| tableNames.contains(tableModel.getTableName().toUpperCase())) {
									updateBean.updateExportFlag(databaseId, tableModel);
									bean.exportSqlite(databaseId, dataExport.getServiceKey(), tableModel, false);
								}
							}
							File file = new File(dbpath);
							if (file.exists() && file.isFile()) {
								byte[] data = FileUtils.getBytes(dbpath);
								data = GZIPUtils.zip(data);
								DataFile dataFile = new BlobItemEntity();
								dataFile.setCreateBy("system");
								dataFile.setCreateDate(exportTime);
								dataFile.setData(data);
								dataFile.setSize(data.length);
								dataFile.setDeleteFlag(0);
								dataFile.setId(serviceKey + "_" + databaseId + "_" + exportTime.getTime());
								dataFile.setFileId(dataFile.getId());
								dataFile.setFilename(serviceKey + "_" + databaseId + "_"
										+ DateUtils.getNowYearMonthDayHHmmss() + ".sqlite.db");
								dataFile.setContentType("application/octet-stream");
								dataFile.setLastModified(exportTime.getTime());
								dataFile.setLocked(0);
								dataFile.setName(serviceKey);
								dataFile.setObjectId("databaseId");
								dataFile.setObjectValue(String.valueOf(databaseId));
								dataFile.setBusinessKey(serviceKey + "_" + databaseId);
								dataFile.setServiceKey(serviceKey);
								dataFile.setStatus(9);
								dataFile.setType("export");
								blobService.insertBlob(dataFile);

								dataExport.setLastExportStatus(9);
								dataExport.setLastExportTime(exportTime);
								dataExportService.updateExportStatus(dataExport);
								success = true;
							} else {
								retry = 5;
							}
						}
					} catch (Exception ex) {
						logger.error(ex);
					}
				}
			}
		}
		lastExecuteTime.set(System.currentTimeMillis());
	}

}
