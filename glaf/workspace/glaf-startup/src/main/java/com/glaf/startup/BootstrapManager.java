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

package com.glaf.startup;

import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.base.ConnectionDefinition;
import com.glaf.core.config.ConnectionConfig;
import com.glaf.core.config.DBConfiguration;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.ClassUtils;

public class BootstrapManager {
	private static class BootstrapManagerHolder {
		public static BootstrapManager instance = new BootstrapManager();
	}

	private static final Log logger = LogFactory.getLog(BootstrapManager.class);

	protected static AtomicBoolean running = new AtomicBoolean(false);

	public static BootstrapManager getInstance() {
		return BootstrapManagerHolder.instance;
	}

	private BootstrapManager() {

	}

	public void startup(ServletContext context) {
		if (!running.get()) {
			try {
				running.set(true);
				BootstrapProperties.reload();
				Properties props = BootstrapProperties.getProperties();
				if (props != null && props.keys().hasMoreElements()) {
					Enumeration<?> e = props.keys();
					while (e.hasMoreElements()) {
						String className = (String) e.nextElement();
						String value = props.getProperty(className);
						try {
							Object obj = ClassUtils
									.instantiateObject(className);
							if (obj instanceof Bootstrap) {
								Bootstrap bootstrap = (Bootstrap) obj;
								bootstrap.startup(context, value);
							}
						} catch (Exception ex) {
							ex.printStackTrace();
							logger.error(ex);
						}
					}
				}

				ConnectionConfigProperties.reload();
				Properties p = ConnectionConfigProperties.getProperties();
				if (p != null && p.keys().hasMoreElements()) {
					Enumeration<?> e = p.keys();
					while (e.hasMoreElements()) {
						String className = (String) e.nextElement();
						try {
							Object object = ClassUtils
									.instantiateObject(className);
							if (object instanceof ConnectionConfig) {
								ConnectionConfig model = (ConnectionConfig) object;
								List<ConnectionDefinition> rows = model
										.getConnectionDefinitions();
								if (rows != null && !rows.isEmpty()) {
									for (ConnectionDefinition conn : rows) {
										if (DBConnectionFactory
												.checkConnection(conn.getName())) {
											continue;
										}
										String dbType = conn.getType();
										String host = conn.getHost();

										int port = conn.getPort();
										String databaseName = conn
												.getDatabase();
										String user = conn.getUser();
										String password = conn.getPassword();

										DBConfiguration
												.addDataSourceProperties(
														conn.getName(), dbType,
														host, port,
														databaseName, user,
														password);
									}
								}
							}
						} catch (Exception ex) {
							ex.printStackTrace();
							logger.error(ex);
						}
					}
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			} finally {
				running.set(false);
			}
		}
	}

	public static void main(String[] args) {
		BootstrapManager.getInstance().startup(null);
	}

}
