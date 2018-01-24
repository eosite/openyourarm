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
import com.glaf.datamgr.domain.DataSetSynthetic;
import com.glaf.datamgr.service.DataSetSyntheticService;
import com.glaf.datamgr.task.DataSetSyntheticTask;
import com.glaf.datamgr.util.ExecutionUtils;

public class DataSetSyntheticHandler implements DataExecutionHandler {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public void execute(Object id, Map<String, Object> context) {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		DataSetSyntheticService dataSetSyntheticService = ContextFactory.getBean("dataSetSyntheticService");
		long dataSetSyntheticId = Long.parseLong(id.toString());
		DataSetSynthetic dataSetSynthetic = dataSetSyntheticService.getDataSetSynthetic(dataSetSyntheticId);
		if (dataSetSynthetic != null && StringUtils.isNotEmpty(dataSetSynthetic.getSourceDatabaseIds())) {
			if (StringUtils.isNotEmpty(dataSetSynthetic.getSourceDatabaseIds())) {
				int errorCount = 0;
				String jobNo = null;
				Database database = null;
				DataSetSyntheticTask task = null;
				dataSetSynthetic.setSyncTime(new Date());
				int runDay = DateUtils.getNowYearMonthDay();
				long targetDatabaseId = dataSetSynthetic.getTargetDatabaseId();
				StringTokenizer token = new StringTokenizer(dataSetSynthetic.getSourceDatabaseIds(), ",");
				while (token.hasMoreTokens()) {
					String x = token.nextToken();
					if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
						try {
							database = databaseService.getDatabaseById(Long.parseLong(x));
							if (database != null) {
								if (dataSetSynthetic.getTargetDatabaseId() == 0) {
									targetDatabaseId = database.getId();
								}
								jobNo = "dataset_synthetic_" + dataSetSynthetic.getId() + "_" + x + "_"
										+ targetDatabaseId + "_" + runDay;
								task = new DataSetSyntheticTask(database.getId(), targetDatabaseId,
										dataSetSynthetic.getId(), jobNo);
								if (!task.execute()) {
									errorCount++;
								}
							}
						} catch (Exception ex) {
							errorCount++;
							logger.error("dataset synthetic error", ex);
						} finally {
							ExecutionUtils.remove(jobNo);
						}
					}
				}

				if (errorCount == 0) {
					dataSetSynthetic.setSyncStatus(1);
				} else {
					dataSetSynthetic.setSyncStatus(-1);
				}
				dataSetSyntheticService.updateDataSetSyntheticStatus(dataSetSynthetic);
			}
		}
	}

}
