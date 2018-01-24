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
import com.glaf.datamgr.domain.TableCombination;
import com.glaf.datamgr.service.TableCombinationService;
import com.glaf.datamgr.task.TableCombinationTask;
import com.glaf.datamgr.util.ExecutionUtils;

public class TableCombinationHandler implements DataExecutionHandler {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public void execute(Object id, Map<String, Object> context) {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		TableCombinationService tableCombinationService = ContextFactory.getBean("tableCombinationService");
		long syntheticId = Long.parseLong(id.toString());
		TableCombination tableCombination = tableCombinationService.getTableCombination(syntheticId);
		if (tableCombination != null && StringUtils.isNotEmpty(tableCombination.getDatabaseIds())) {
			if (StringUtils.isNotEmpty(tableCombination.getDatabaseIds())) {
				int errorCount = 0;
				String jobNo = null;
				Database database = null;
				long targetDatabaseId = 0;
				TableCombinationTask task = null;
				tableCombination.setSyncTime(new Date());
				int runDay = DateUtils.getNowYearMonthDay();
				StringTokenizer token = new StringTokenizer(tableCombination.getDatabaseIds(), ",");
				while (token.hasMoreTokens()) {
					String x = token.nextToken();
					if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
						try {
							database = databaseService.getDatabaseById(Long.parseLong(x));
							if (database != null) {
								targetDatabaseId = tableCombination.getTargetDatabaseId();
								if (tableCombination.getTargetDatabaseId() == 0) {
									targetDatabaseId = database.getId();
								}
								jobNo = "table_combination_" + tableCombination.getId() + "_" + x + "_"
										+ targetDatabaseId + "_" + runDay;
								task = new TableCombinationTask(database.getId(), targetDatabaseId,
										tableCombination.getId(), jobNo);
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
					tableCombination.setSyncStatus(1);
				} else {
					tableCombination.setSyncStatus(-1);
				}
				tableCombinationService.updateTableCombinationStatus(tableCombination);
			}
		}
	}

}
