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

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.Database;
import com.glaf.core.job.BaseJob;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DateUtils;

import com.glaf.datamgr.domain.TableSync;
import com.glaf.datamgr.query.TableSyncQuery;
import com.glaf.datamgr.service.TableSyncService;
import com.glaf.datamgr.task.TableSyncTask;
import com.glaf.datamgr.util.ExecutionUtils;

public class TableSyncSchedulerJob extends BaseJob {

	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());

	public void runJob(JobExecutionContext context) throws JobExecutionException {
		try {
			TimeUnit.SECONDS.sleep(5 + new Random().nextInt(120));// 随机等待，避免Job同时执行
		} catch (InterruptedException e) {
		}
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		TableSyncService tableSyncService = ContextFactory.getBean("tableSyncService");
		TableSyncQuery query = new TableSyncQuery();
		query.deleteFlag(0);
		query.locked(0);
		query.setScheduleFlag("Y");
		tableSyncService.resetAllTableSyncStatus();
		List<TableSync> list = tableSyncService.list(query);
		if (list != null && !list.isEmpty()) {
			int errorCount = 0;
			String jobNo = null;
			Database database = null;
			TableSyncTask task = null;
			StringTokenizer token = null;
			String runDay = DateUtils.getNowYearMonthDayHour();
			for (TableSync tableSync : list) {
				if (StringUtils.isNotEmpty(tableSync.getTargetDatabaseIds())) {
					errorCount = 0;
					jobNo = "table_sync_" + tableSync.getId() + "_" + DateUtils.getNowYearMonthDayHHmmss();
					try {
						tableSync.setJobNo(jobNo);
						tableSync.setSyncStatus(0);
						tableSync.setSyncTime(new Date());
						token = new StringTokenizer(tableSync.getTargetDatabaseIds(), ",");
						while (token.hasMoreTokens()) {
							String x = token.nextToken();
							if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
								jobNo = "table_sync_" + tableSync.getId() + "_" + tableSync.getSourceDatabaseId() + "_"
										+ x + "_" + runDay;
								if (!ExecutionUtils.contains(jobNo)) {
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
										logger.error(ex);
									} finally {
										ExecutionUtils.remove(jobNo);
									}
								}
							}
						}
						if (errorCount == 0) {
							tableSync.setSyncStatus(1);
						} else {
							tableSync.setSyncStatus(-1);
						}
						tableSyncService.updateTableSyncStatus(tableSync);
					} catch (Exception ex) {
						logger.error(ex);
					}
				}
			}
		}
		lastExecuteTime.set(System.currentTimeMillis());
	}

}
