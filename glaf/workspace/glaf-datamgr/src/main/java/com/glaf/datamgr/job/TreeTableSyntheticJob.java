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
import com.glaf.datamgr.domain.ExecutionLog;
import com.glaf.datamgr.domain.TreeTableSynthetic;
import com.glaf.datamgr.query.TreeTableSyntheticQuery;
import com.glaf.datamgr.service.TreeTableSyntheticService;
import com.glaf.datamgr.task.TreeTableSyntheticTask;
import com.glaf.datamgr.util.ExecutionLogFactory;
import com.glaf.datamgr.util.ExecutionUtils;

public class TreeTableSyntheticJob extends BaseJob {

	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());

	public void runJob(JobExecutionContext context) throws JobExecutionException {
		if ((System.currentTimeMillis() - lastExecuteTime.get()) < DateUtils.HOUR) {// 1小时
			return;
		}
		try {
			TimeUnit.SECONDS.sleep(1 + new Random().nextInt(120));// 随机等待，避免Job同时执行
		} catch (InterruptedException e) {
		}
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		TreeTableSyntheticService treeTableSyntheticService = ContextFactory.getBean("treeTableSyntheticService");
		TreeTableSyntheticQuery query = new TreeTableSyntheticQuery();
		query.deleteFlag(0);
		query.locked(0);
		treeTableSyntheticService.resetAllTreeTableSyntheticStatus();
		List<TreeTableSynthetic> list = treeTableSyntheticService.list(query);
		if (list != null && !list.isEmpty()) {
			int errorCount = 0;
			String jobNo = null;
			Database database = null;
			long targetDatabaseId = 0;
			StringTokenizer token = null;
			List<ExecutionLog> logs = null;
			TreeTableSyntheticTask task = null;
			String runDay = DateUtils.getNowYearMonthDayHour();
			for (TreeTableSynthetic tableSynthetic : list) {
				if (StringUtils.isNotEmpty(tableSynthetic.getSourceDatabaseIds())) {
					errorCount = 0;
					try {
						token = new StringTokenizer(tableSynthetic.getSourceDatabaseIds(), ",");
						while (token.hasMoreTokens()) {
							String x = token.nextToken();
							if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
								targetDatabaseId = Long.parseLong(x);
								if (tableSynthetic.getTargetDatabaseId() > 0) {
									targetDatabaseId = tableSynthetic.getTargetDatabaseId();
								}
								jobNo = "treetable_synthetic_" + tableSynthetic.getId() + "_" + x + "_"
										+ targetDatabaseId + "_" + runDay;
								if (!ExecutionUtils.contains(jobNo)) {
									try {
										/**
										 * 判断是否可以执行
										 */
										logs = ExecutionLogFactory.getInstance().getTodayExecutionLogs(
												"treetable_synthetic", String.valueOf(tableSynthetic.getId()));
										if (!ExecutionLogFactory.getInstance().canExecution(logs, jobNo)) {
											continue;
										}
										database = databaseService.getDatabaseById(Long.parseLong(x));
										if (database != null) {
											ExecutionUtils.put(jobNo, new Date());
											task = new TreeTableSyntheticTask(database.getId(), targetDatabaseId,
													tableSynthetic.getId(), jobNo);
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
							tableSynthetic.setSyncStatus(1);
						} else {
							tableSynthetic.setSyncStatus(-1);
						}
						treeTableSyntheticService.updateTreeTableSyntheticStatus(tableSynthetic);
					} catch (Exception ex) {
						logger.error(ex);
					}
				}
			}
		}
		lastExecuteTime.set(System.currentTimeMillis());
	}

}
