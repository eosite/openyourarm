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

package com.glaf.matrix.data.sync.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.job.BaseJob;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.matrix.data.sync.bean.MultiTableSyncBean;
import com.glaf.matrix.data.sync.model.TableSyncDefinition;
import com.glaf.matrix.data.sync.xml.TableSyncXmlReader;

public class MultiTableSyncJob extends BaseJob {

	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());

	public void runJob(JobExecutionContext context) throws JobExecutionException {
		logger.debug("--------------------MultiTableSyncJob-------------------------");
		if ((System.currentTimeMillis() - lastExecuteTime.get()) < DateUtils.MINUTE * 5) {
			return;
		}

		String path = SystemProperties.getConfigRootPath() + "/conf/matrix/sync";
		TableSyncXmlReader xmlReader = new TableSyncXmlReader();
		MultiTableSyncBean bean = new MultiTableSyncBean();
		InputStream inputStream = null;
		try {
			File directory = new File(path);
			if (directory.exists() && directory.isDirectory()) {
				File[] filelist = directory.listFiles();
				if (filelist != null) {
					for (int i = 0, len = filelist.length; i < len; i++) {
						File file = filelist[i];
						if (file.isFile() && file.getName().endsWith(".xml")) {
							inputStream = new FileInputStream(file);
							List<TableSyncDefinition> rows = xmlReader.read(inputStream);
							if (rows != null && !rows.isEmpty()) {
								for (TableSyncDefinition sync : rows) {
									try {
										bean.execute(0L, sync);
									} catch (Exception ex) {
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeStream(inputStream);
		}
		lastExecuteTime.set(System.currentTimeMillis());
	}

}
