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

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.job.BaseJob;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.StringTools;
import com.glaf.datamgr.domain.FileHistory;
import com.glaf.datamgr.query.FileHistoryQuery;
import com.glaf.datamgr.service.FileHistoryService;

public class FileBackupJob extends BaseJob {
	protected static final Log logger = LogFactory.getLog(FileBackupJob.class);

	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());

	public void doProcess() {
		String appPath = SystemProperties.getAppPath();
		File dir = new File(appPath);
		if (dir.exists() && dir.isDirectory()) {
			FileHistoryService fileHistoryService = ContextFactory.getBean("fileHistoryService");
			this.process(dir, fileHistoryService);
		}
	}

	public void process(File dir, FileHistoryService fileHistoryService) {
		if (dir.exists() && dir.isDirectory()) {
			String appPath = SystemProperties.getAppPath();
			String path00 = dir.getAbsolutePath();
			path00 = StringTools.replace(path00, "\\", "/");
			path00 = path00.substring(appPath.length(), path00.length());
			FileHistoryQuery query = new FileHistoryQuery();
			query.pathLike(path00 + "/%");
			List<FileHistory> list = fileHistoryService.list(query);
			Map<String, Long> fileMap = new HashMap<String, Long>();
			if (list != null && !list.isEmpty()) {
				for (FileHistory f : list) {
					fileMap.put(f.getPath(), f.getLastModified());
				}
			}

			File[] files = dir.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.length() > FileUtils.MB_SIZE * 20) {
						continue;
					}
					try {
						if (file.isFile()) {
							String path = file.getAbsolutePath();
							path = StringTools.replace(path, "\\", "/");
							path = path.substring(appPath.length(), path.length());
							if (fileMap.containsKey(path) && fileMap.get(path).longValue() == file.lastModified()) {
								continue;
							}
							if (file.getName().endsWith(".class") || file.getName().endsWith(".tmp")
									|| file.getName().endsWith(".scc") || file.getName().endsWith(".svn")
									|| file.getName().endsWith(".bak") || StringUtils.contains(path, ".log")) {
								continue;
							}
							FileHistory model = new FileHistory();
							model.setCreateBy("system");
							model.setFileContent(FileUtils.getBytes(file));
							model.setFileName(file.getName());
							model.setPath(path);
							model.setLastModified(file.lastModified());
							model.setFileSize((int) file.length());
							fileHistoryService.save(model);
						} else {
							this.process(file, fileHistoryService);
						}
					} catch (Exception ex) {
						logger.error(ex);
					}
				}
			}
		}

	}

	@Override
	public void runJob(JobExecutionContext context) throws JobExecutionException {
		if ((System.currentTimeMillis() - lastExecuteTime.get()) < 60000) {
			return;
		}
		doProcess();
		lastExecuteTime.set(System.currentTimeMillis());
	}
}
