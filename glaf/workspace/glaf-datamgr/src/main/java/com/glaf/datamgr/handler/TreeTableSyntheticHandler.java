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

import com.glaf.datamgr.domain.TreeTableSynthetic;
import com.glaf.datamgr.service.TreeTableSyntheticService;
import com.glaf.datamgr.task.TreeTableSyntheticTask;
import com.glaf.datamgr.util.ExecutionUtils;

public class TreeTableSyntheticHandler implements DataExecutionHandler {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public void execute(Object id, Map<String, Object> context) {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		TreeTableSyntheticService treeTableSyntheticService = ContextFactory.getBean("treeTableSyntheticService");
		long syntheticId = Long.parseLong(id.toString());
		TreeTableSynthetic tableSynthetic = treeTableSyntheticService.getTreeTableSynthetic(syntheticId);
		if (tableSynthetic != null && StringUtils.isNotEmpty(tableSynthetic.getSourceDatabaseIds())) {
			if (StringUtils.isNotEmpty(tableSynthetic.getSourceDatabaseIds())) {
				int errorCount = 0;
				String jobNo = null;
				Database database = null;
				long targetDatabaseId = 0;
				TreeTableSyntheticTask task = null;
				tableSynthetic.setSyncTime(new Date());
				int runDay = DateUtils.getNowYearMonthDay();
				StringTokenizer token = new StringTokenizer(tableSynthetic.getSourceDatabaseIds(), ",");
				while (token.hasMoreTokens()) {
					String x = token.nextToken();
					if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
						targetDatabaseId = Long.parseLong(x);
						if (tableSynthetic.getTargetDatabaseId() > 0) {
							targetDatabaseId = tableSynthetic.getTargetDatabaseId();
						}
						jobNo = "treetable_synthetic_" + tableSynthetic.getId() + "_" + x + "_" + targetDatabaseId + "_"
								+ runDay;
						try {
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
							logger.error("tree table synthetic error", ex);
						} finally {
							ExecutionUtils.remove(jobNo);
						}
					}
				}

				if (errorCount == 0) {
					tableSynthetic.setSyncStatus(1);
				} else {
					tableSynthetic.setSyncStatus(-1);
				}
				treeTableSyntheticService.updateTreeTableSyntheticStatus(tableSynthetic);
			}
		}
	}

}
