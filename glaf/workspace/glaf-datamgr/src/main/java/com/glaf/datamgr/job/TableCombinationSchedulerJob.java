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

import com.glaf.datamgr.domain.TableCombination;
import com.glaf.datamgr.query.TableCombinationQuery;
import com.glaf.datamgr.service.TableCombinationService;
import com.glaf.datamgr.task.TableCombinationTask;
import com.glaf.datamgr.util.ExecutionUtils;

public class TableCombinationSchedulerJob extends BaseJob {

	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());

	public void runJob(JobExecutionContext context) throws JobExecutionException {
		try {
			TimeUnit.SECONDS.sleep(5 + new Random().nextInt(120));// 随机等待，避免Job同时执行
		} catch (InterruptedException e) {
		}
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		TableCombinationService tableCombinationService = ContextFactory.getBean("tableCombinationService");
		TableCombinationQuery query = new TableCombinationQuery();
		query.deleteFlag(0);
		query.locked(0);
		query.setScheduleFlag("Y");
		tableCombinationService.resetAllTableCombinationStatus();
		List<TableCombination> list = tableCombinationService.list(query);
		if (list != null && !list.isEmpty()) {
			int errorCount = 0;
			String jobNo = null;
			Database database = null;
			StringTokenizer token = null;
			TableCombinationTask task = null;
			String runDay = DateUtils.getNowYearMonthDayHour();
			long targetDatabaseId = 0;
			for (TableCombination tableCombination : list) {
				if (StringUtils.isNotEmpty(tableCombination.getDatabaseIds())) {
					errorCount = 0;
					try {
						tableCombination.setSyncStatus(0);
						tableCombination.setSyncTime(new Date());
						token = new StringTokenizer(tableCombination.getDatabaseIds(), ",");
						while (token.hasMoreTokens()) {
							String x = token.nextToken();
							if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
								if (tableCombination.getTargetDatabaseId() == 0) {
									targetDatabaseId = Long.parseLong(x);
								} else {
									targetDatabaseId = tableCombination.getTargetDatabaseId();
								}
								jobNo = "table_combination_" + tableCombination.getId() + "_" + x + "_"
										+ targetDatabaseId + "_" + runDay;
								if (!ExecutionUtils.contains(jobNo)) {
									try {
										database = databaseService.getDatabaseById(Long.parseLong(x));
										if (database != null) {
											ExecutionUtils.put(jobNo, new Date());
											task = new TableCombinationTask(database.getId(), targetDatabaseId,
													tableCombination.getId(), jobNo);
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
							tableCombination.setSyncStatus(1);
						} else {
							tableCombination.setSyncStatus(-1);
						}
						tableCombinationService.updateTableCombinationStatus(tableCombination);
					} catch (Exception ex) {
						logger.error(ex);
					}
				}
			}
		}
		lastExecuteTime.set(System.currentTimeMillis());
	}

}
