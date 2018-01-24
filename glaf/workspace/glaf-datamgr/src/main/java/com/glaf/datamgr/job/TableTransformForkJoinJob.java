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
import com.glaf.datamgr.domain.ExecutionLog;
import com.glaf.datamgr.domain.TableTransform;
import com.glaf.datamgr.query.TableTransformQuery;
import com.glaf.datamgr.service.TableTransformService;
import com.glaf.datamgr.task.TableTransformTask;
import com.glaf.datamgr.util.ExecutionLogFactory;
import com.glaf.datamgr.util.ExecutionUtils;

public class TableTransformForkJoinJob extends BaseJob {

	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());

	public void runJob(JobExecutionContext context) throws JobExecutionException {
		if ((System.currentTimeMillis() - lastExecuteTime.get()) < DateUtils.HOUR) {// 1小时
			return;
		}
		try {
			TimeUnit.SECONDS.sleep(4 + new Random().nextInt(120));// 随机等待，避免Job同时执行
		} catch (InterruptedException e) {
		}
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		TableTransformService tableTransformService = ContextFactory.getBean("tableTransformService");
		TableTransformQuery query = new TableTransformQuery();
		query.deleteFlag(0);
		query.locked(0);
		tableTransformService.resetAllTableTransformStatus();
		List<TableTransform> list = tableTransformService.list(query);
		if (list != null && !list.isEmpty()) {
			int errorCount = 0;
			String jobNo = null;
			Database database = null;
			TableTransformTask task = null;
			StringTokenizer token = null;
			Future<Boolean> future = null;
			ForkJoinPool forkJoinPool = null;
			List<ExecutionLog> logs = null;
			String runDay = DateUtils.getNowYearMonthDayHour();
			for (TableTransform tableTransform : list) {
				if (StringUtils.isNotEmpty(tableTransform.getDatabaseIds())) {
					errorCount = 0;
					forkJoinPool = new ForkJoinPool();
					try {
						token = new StringTokenizer(tableTransform.getDatabaseIds(), ",");
						while (token.hasMoreTokens()) {
							String x = token.nextToken();
							if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
								jobNo = "table_transform_" + tableTransform.getTableName() + "_" + x + "_" + runDay;
								if (!ExecutionUtils.contains(jobNo)) {
									try {
										/**
										 * 判断是否可以执行
										 */
										logs = ExecutionLogFactory.getInstance().getTodayExecutionLogs(
												"table_transform", String.valueOf(tableTransform.getTableName()));
										if (!ExecutionLogFactory.getInstance().canExecution(logs, jobNo)) {
											continue;
										}
										database = databaseService.getDatabaseById(Long.parseLong(x));
										if (database != null) {
											ExecutionUtils.put(jobNo, new Date());
											task = new TableTransformTask(database.getId(),
													tableTransform.getTableName(), jobNo, null);
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
							tableTransform.setTransformStatus(1);
						} else {
							tableTransform.setTransformStatus(-1);
						}
						tableTransformService.updateTableTransformStatus(tableTransform);
					} catch (Exception ex) {
						logger.error(ex);
					}
				}
			}
		}
		lastExecuteTime.set(System.currentTimeMillis());
	}

}
