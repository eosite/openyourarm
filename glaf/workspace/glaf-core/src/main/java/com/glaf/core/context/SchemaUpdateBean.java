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
package com.glaf.core.context;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.SystemProperties;

public class SchemaUpdateBean {
	protected static final Log logger = LogFactory.getLog(SchemaUpdateBean.class);

	public void executeUpdate() {
		String path = SystemProperties.getConfigRootPath() + "/conf/schema";
		File dir = new File(path);
		if (dir.exists() && dir.isDirectory()) {
			logger.info("load schema path:" + path);
			File[] filelist = dir.listFiles();
			if (filelist != null) {
				for (int i = 0, len = filelist.length; i < len; i++) {
					File file = filelist[i];
					if (file.isFile() && file.getName().endsWith(".mapping.xml")) {
						SchemaUpdateThread command = new SchemaUpdateThread(file);
						com.glaf.core.util.ThreadFactory.execute(command);
					}
				}
			}
		}
	}

}
