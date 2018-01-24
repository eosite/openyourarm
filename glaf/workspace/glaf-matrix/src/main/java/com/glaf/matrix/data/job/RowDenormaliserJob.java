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

package com.glaf.matrix.data.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.glaf.datamgr.util.ExecutionLogFactory;
import com.glaf.datamgr.util.ExecutionUtils;
import com.glaf.matrix.data.domain.RowDenormaliser;
import com.glaf.matrix.data.query.RowDenormaliserQuery;
import com.glaf.matrix.data.service.RowDenormaliserService;
import com.glaf.matrix.data.task.RowDenormaliserTask;
import com.glaf.matrix.util.SysParams;

public class RowDenormaliserJob extends BaseJob {

	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());

	public static String getNowYearMonthDayHH() {
		String returnStr = null;
		System.currentTimeMillis();
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHH");
		Date date = new Date();
		returnStr = f.format(date);
		return returnStr;
	}

	public void runJob(JobExecutionContext context) throws JobExecutionException {
		if ((System.currentTimeMillis() - lastExecuteTime.get()) < DateUtils.HOUR) {// 1小时
			return;
		}
		try {
			TimeUnit.SECONDS.sleep(4 + new Random().nextInt(120));// 随机等待，避免Job同时执行
		} catch (InterruptedException e) {
		}
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		RowDenormaliserService rowDenormaliserService = ContextFactory
				.getBean("com.glaf.matrix.data.service.rowDenormaliserService");
		RowDenormaliserQuery query = new RowDenormaliserQuery();
		query.locked(0);
		query.scheduleFlag("Y");
		// rowDenormaliserService.resetAllRowDenormaliserStatus();
		List<RowDenormaliser> list = rowDenormaliserService.list(query);
		if (list != null && !list.isEmpty()) {
			int errorCount = 0;
			String jobNo = null;
			Database database = null;
			StringTokenizer token = null;
			List<ExecutionLog> logs = null;
			RowDenormaliserTask task = null;
			Map<String, Object> parameter = new HashMap<String, Object>();
			SysParams.putInternalParams(parameter);
			String runDay = getNowYearMonthDayHH();
			for (RowDenormaliser rowDenormaliser : list) {
				if (StringUtils.isNotEmpty(rowDenormaliser.getDatabaseIds())) {
					errorCount = 0;
					try {
						token = new StringTokenizer(rowDenormaliser.getDatabaseIds(), ",");
						while (token.hasMoreTokens()) {
							String x = token.nextToken();
							if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
								jobNo = "row_denormaliser_" + rowDenormaliser.getId() + "_" + x + "_" + runDay;
								if (!ExecutionUtils.contains(jobNo)) {
									try {
										/**
										 * 判断是否可以执行
										 */
										logs = ExecutionLogFactory.getInstance()
												.getTodayExecutionLogs("row_denormaliser", rowDenormaliser.getId());
										if (!ExecutionLogFactory.getInstance().canExecution(logs, jobNo)) {
											continue;
										}
										database = databaseService.getDatabaseById(Long.parseLong(x));
										if (database != null) {
											ExecutionUtils.put(jobNo, new Date());
											task = new RowDenormaliserTask(database.getId(), rowDenormaliser.getId(),
													jobNo, parameter);
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
							rowDenormaliser.setTransformStatus(1);
						} else {
							rowDenormaliser.setTransformStatus(-1);
						}
						rowDenormaliserService.save(rowDenormaliser);
					} catch (Exception ex) {
						logger.error(ex);
					}
				}
			}
		}
		lastExecuteTime.set(System.currentTimeMillis());
	}

}
