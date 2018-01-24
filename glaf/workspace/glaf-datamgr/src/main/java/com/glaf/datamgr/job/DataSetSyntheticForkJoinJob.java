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
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
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
import com.glaf.datamgr.domain.DataSetSynthetic;
import com.glaf.datamgr.domain.ExecutionLog;
import com.glaf.datamgr.query.DataSetSyntheticQuery;
import com.glaf.datamgr.service.DataSetSyntheticService;
import com.glaf.datamgr.task.DataSetSyntheticTask;
import com.glaf.datamgr.util.ExecutionLogFactory;
import com.glaf.datamgr.util.ExecutionUtils;

public class DataSetSyntheticForkJoinJob extends BaseJob {

	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());

	public void runJob(JobExecutionContext context) throws JobExecutionException {
		if ((System.currentTimeMillis() - lastExecuteTime.get()) < DateUtils.HOUR) {// 1小时
			return;
		}
		try {
			TimeUnit.SECONDS.sleep(2 + new Random().nextInt(120));// 随机等待，避免Job同时执行
		} catch (InterruptedException e) {
		}
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		DataSetSyntheticService dataSetSyntheticService = ContextFactory.getBean("dataSetSyntheticService");
		DataSetSyntheticQuery query = new DataSetSyntheticQuery();
		query.deleteFlag(0);
		query.locked(0);
		dataSetSyntheticService.resetAllDataSetSyntheticStatus();
		List<DataSetSynthetic> list = dataSetSyntheticService.list(query);
		if (list != null && !list.isEmpty()) {
			int errorCount = 0;
			String jobNo = null;
			Database database = null;
			StringTokenizer token = null;
			DataSetSyntheticTask task = null;
			Future<Boolean> future = null;
			ForkJoinPool forkJoinPool = null;
			List<ExecutionLog> logs = null;
			String runDay = DateUtils.getNowYearMonthDayHour();
			for (DataSetSynthetic dataSetSynthetic : list) {
				if (StringUtils.isNotEmpty(dataSetSynthetic.getSourceDatabaseIds())) {
					errorCount = 0;
					forkJoinPool = new ForkJoinPool();
					try {
						token = new StringTokenizer(dataSetSynthetic.getSourceDatabaseIds(), ",");
						while (token.hasMoreTokens()) {
							String x = token.nextToken();
							if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
								jobNo = "dataset_synthetic_" + dataSetSynthetic.getId() + "_" + x + "_"
										+ dataSetSynthetic.getTargetDatabaseId() + "_" + runDay;
								if (!ExecutionUtils.contains(jobNo)) {
									try {
										/**
										 * 判断是否可以执行
										 */
										logs = ExecutionLogFactory.getInstance().getTodayExecutionLogs(
												"dataset_synthetic", String.valueOf(dataSetSynthetic.getId()));
										if (!ExecutionLogFactory.getInstance().canExecution(logs, jobNo)) {
											continue;
										}
										database = databaseService.getDatabaseById(Long.parseLong(x));
										if (database != null) {
											ExecutionUtils.put(jobNo, new Date());
											task = new DataSetSyntheticTask(database.getId(),
													dataSetSynthetic.getTargetDatabaseId(), dataSetSynthetic.getId(),
													jobNo);
											future = forkJoinPool.submit(task);
											if (!future.get()) {
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

						try {
							forkJoinPool.awaitTermination(2, TimeUnit.SECONDS);
						} catch (InterruptedException ex) {
						}
						forkJoinPool.shutdown();

						if (errorCount == 0) {
							dataSetSynthetic.setSyncStatus(1);
						} else {
							dataSetSynthetic.setSyncStatus(-1);
						}
						dataSetSyntheticService.updateDataSetSyntheticStatus(dataSetSynthetic);
					} catch (Exception ex) {
						logger.error(ex);
					}
				}
			}
		}
		lastExecuteTime.set(System.currentTimeMillis());
	}
}
