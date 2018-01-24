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

import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.Database;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DateUtils;
import com.glaf.datamgr.domain.TaskTable;
import com.glaf.datamgr.service.TaskTableService;
import com.glaf.datamgr.task.TaskTableTask;
import com.glaf.datamgr.util.ExecutionUtils;

public class TaskTableHandler implements DataExecutionHandler {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public void execute(Object id, Map<String, Object> context) {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		TaskTableService taskTableService = ContextFactory.getBean("taskTableService");
		long syntheticId = Long.parseLong(id.toString());
		TaskTable taskTable = taskTableService.getTaskTable(syntheticId);
		if (taskTable != null && StringUtils.isNotEmpty(taskTable.getDatabaseIds())) {
			if (StringUtils.isNotEmpty(taskTable.getDatabaseIds())) {
				int errorCount = 0;
				String jobNo = null;
				Database database = null;
				long targetDatabaseId = 0;
				TaskTableTask task = null;

				int runDay = DateUtils.getNowYearMonthDay();
				StringTokenizer token = new StringTokenizer(taskTable.getDatabaseIds(), ",");
				while (token.hasMoreTokens()) {
					String x = token.nextToken();
					if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
						try {
							database = databaseService.getDatabaseById(Long.parseLong(x));
							if (database != null) {
								jobNo = "task_table_" + taskTable.getId() + "_" + x + "_" + targetDatabaseId + "_"
										+ runDay;
								task = new TaskTableTask(database.getId(), taskTable.getId(), jobNo, null);
								if (!task.execute()) {
									errorCount++;
								}
							}
						} catch (Exception ex) {
							errorCount++;
							logger.error("table combination error", ex);
						} finally {
							ExecutionUtils.remove(jobNo);
						}
					}
				}
				if (errorCount == 0) {

				} else {

				}
			}
		}
	}

}
