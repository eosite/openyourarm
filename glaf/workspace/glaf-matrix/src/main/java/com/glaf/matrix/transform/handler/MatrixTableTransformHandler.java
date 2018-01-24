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

package com.glaf.matrix.transform.handler;

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
import com.glaf.datamgr.handler.DataExecutionHandler;
import com.glaf.datamgr.util.ExecutionUtils;
import com.glaf.matrix.transform.domain.MatrixTableTransform;
import com.glaf.matrix.transform.service.MatrixTableTransformService;
import com.glaf.matrix.transform.task.MatrixTableTransformTask;

public class MatrixTableTransformHandler implements DataExecutionHandler {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public void execute(Object id, Map<String, Object> context) {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		MatrixTableTransformService matrixTableTransformService = ContextFactory.getBean("matrixTableTransformService");
		MatrixTableTransform matrixTableTransform = matrixTableTransformService.getMatrixTableTransform(id.toString());
		if (matrixTableTransform != null && StringUtils.isNotEmpty(matrixTableTransform.getDatabaseIds())) {
			int errorCount = 0;
			String jobNo = null;
			Database database = null;
			MatrixTableTransformTask task = null;
			matrixTableTransform.setTransformTime(new Date());
			String runDay = DateUtils.getNowYearMonthDayHour();
			StringTokenizer token = new StringTokenizer(matrixTableTransform.getDatabaseIds(), ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
					jobNo = "matrix_table_transform_" + matrixTableTransform.getTransformId() + "_" + x + "_" + runDay;
					try {
						database = databaseService.getDatabaseById(Long.parseLong(x));
						if (database != null) {
							ExecutionUtils.put(jobNo, new Date());
							task = new MatrixTableTransformTask(database.getId(), matrixTableTransform.getTransformId(),
									jobNo, null);
							if (!task.execute()) {
								errorCount++;
							}
						}
					} catch (Exception ex) {
						errorCount++;
						logger.error("table transform error", ex);
					} finally {
						ExecutionUtils.remove(jobNo);
					}
				}
			}

			if (errorCount == 0) {
				matrixTableTransform.setTransformStatus(1);
			} else {
				matrixTableTransform.setTransformStatus(-1);
			}
			matrixTableTransformService.updateMatrixTableTransformStatus(matrixTableTransform);
		}
	}

}
