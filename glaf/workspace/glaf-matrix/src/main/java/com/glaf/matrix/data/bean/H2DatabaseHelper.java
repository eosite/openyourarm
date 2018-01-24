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
package com.glaf.matrix.data.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.SystemProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class H2DatabaseHelper {

	protected static final Log logger = LogFactory.getLog(H2DatabaseHelper.class);

	public HikariDataSource getDataSource(String database) {
		String dbpath = SystemProperties.getConfigRootPath() + "/db/" + database;
		String jdbcUrl = "jdbc:h2:" + dbpath;
		String jdbcDriverClass = "org.h2.Driver";
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(jdbcDriverClass);
		config.setJdbcUrl(jdbcUrl);
		config.setMaximumPoolSize(10);
		config.setMaxLifetime(1000L * 3600 * 8);
		config.setUsername("sa");
		config.setPassword("");
		HikariDataSource ds = new HikariDataSource(config);
		logger.info("h2 path : " + dbpath);
		return ds;
	}

	public HikariDataSource getTempDataSource(String database) {
		String dbpath = SystemProperties.getConfigRootPath() + "/temp/" + database;
		String jdbcUrl = "jdbc:h2:" + dbpath;
		String jdbcDriverClass = "org.h2.Driver";
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(jdbcDriverClass);
		config.setJdbcUrl(jdbcUrl);
		config.setMaximumPoolSize(10);
		config.setMaxLifetime(1000L * 3600 * 8);
		config.setUsername("sa");
		config.setPassword("");
		HikariDataSource ds = new HikariDataSource(config);
		logger.info("h2 temp path : " + dbpath);
		return ds;
	}

}
