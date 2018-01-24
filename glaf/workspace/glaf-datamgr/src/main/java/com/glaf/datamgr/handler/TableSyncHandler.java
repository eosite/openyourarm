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

package com.glaf.datamgr.handler;

import java.util.Date;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.Database;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DateUtils;

import com.glaf.datamgr.domain.TableSync;
import com.glaf.datamgr.service.TableSyncService;
import com.glaf.datamgr.task.TableSyncTask;
import com.glaf.datamgr.util.ExecutionUtils;

public class TableSyncHandler implements DataExecutionHandler {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public void execute(Object id, Map<String, Object> context) {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		TableSyncService tableSyncService = ContextFactory.getBean("tableSyncService");
		long syntheticId = Long.parseLong(id.toString());
		TableSync tableSync = tableSyncService.getTableSync(syntheticId);
		if (tableSync != null && StringUtils.isNotEmpty(tableSync.getTargetDatabaseIds())) {
			if (StringUtils.isNotEmpty(tableSync.getTargetDatabaseIds())) {
				int errorCount = 0;
				String jobNo = null;
				Database database = null;
				TableSyncTask task = null;
				tableSync.setSyncTime(new Date());
				int runDay = DateUtils.getNowYearMonthDay();
				StringTokenizer token = new StringTokenizer(tableSync.getTargetDatabaseIds(), ",");
				while (token.hasMoreTokens()) {
					String x = token.nextToken();
					if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
						jobNo = "table_sync_" + tableSync.getId() + "_" + tableSync.getSourceDatabaseId() + "_" + x
								+ "_" + runDay;
						try {
							database = databaseService.getDatabaseById(Long.parseLong(x));
							if (database != null) {
								ExecutionUtils.put(jobNo, new Date());
								task = new TableSyncTask(tableSync.getSourceDatabaseId(), database.getId(),
										tableSync.getId(), jobNo);
								if (!task.execute()) {
									errorCount++;
								}
							}
						} catch (Exception ex) {
							errorCount++;
							logger.error("table sync error", ex);
						} finally {
							ExecutionUtils.remove(jobNo);
						}
					}
				}
				if (errorCount == 0) {
					tableSync.setSyncStatus(1);
				} else {
					tableSync.setSyncStatus(-1);
				}
				tableSyncService.updateTableSyncStatus(tableSync);
			}
		}
	}

}
