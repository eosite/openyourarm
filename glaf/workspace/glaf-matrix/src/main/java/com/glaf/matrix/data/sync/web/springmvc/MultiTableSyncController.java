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

package com.glaf.matrix.data.sync.web.springmvc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.matrix.data.sync.bean.MultiTableSyncBean;
import com.glaf.matrix.data.sync.model.TableSyncDefinition;
import com.glaf.matrix.data.sync.xml.TableSyncXmlReader;

@Controller("/matrix/tableSync")
@RequestMapping("/matrix/tableSync")
public class MultiTableSyncController {

	protected final static Log logger = LogFactory.getLog(MultiTableSyncController.class);

	@ResponseBody
	@RequestMapping("/execute")
	public byte[] execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		if (loginContext.isSystemAdministrator()) {
			String path = SystemProperties.getConfigRootPath() + "/conf/matrix/sync";
			TableSyncXmlReader xmlReader = new TableSyncXmlReader();
			MultiTableSyncBean bean = new MultiTableSyncBean();
			StringBuilder buff = new StringBuilder();
			InputStream inputStream = null;
			boolean result = true;
			try {
				File directory = new File(path);
				if (directory.exists() && directory.isDirectory()) {
					logger.debug(path);
					File[] filelist = directory.listFiles();
					if (filelist != null) {
						for (int i = 0, len = filelist.length; i < len; i++) {
							File file = filelist[i];
							if (file.isFile() && file.getName().endsWith(".xml")) {
								logger.debug(file.getName());
								inputStream = new FileInputStream(file);
								List<TableSyncDefinition> rows = xmlReader.read(inputStream);
								IOUtils.closeStream(inputStream);
								if (rows != null && !rows.isEmpty()) {
									for (TableSyncDefinition sync : rows) {
										try {
											bean.execute(0L, sync);
										} catch (Exception ex) {
											// ex.printStackTrace();
											result = false;
											buff.append(sync.getTitle() + "执行错误:" + ex.getCause().getMessage());
											buff.append(FileUtils.newline);
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
			return ResponseUtils.responseJsonResult(result, buff.toString());
		}
		return ResponseUtils.responseJsonResult(false);
	}

}
