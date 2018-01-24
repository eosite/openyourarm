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

package com.glaf.datamgr.bean;

import java.util.Map;
import java.util.concurrent.RecursiveTask;

import com.glaf.core.domain.Database;
import com.glaf.datamgr.domain.SqlDefinition;

public class SqlExportJsonDataTask extends RecursiveTask<StringBuffer> {

	private static final long serialVersionUID = 1L;

	private Database database;
	private SqlDefinition sqlDef;
	private int start;
	private int limit;
	private Map<String, Object> params;

	public SqlExportJsonDataTask(Database database, SqlDefinition sqlDef, int start, int limit,
			Map<String, Object> params) {
		this.database = database;
		this.sqlDef = sqlDef;
		this.start = start;
		this.limit = limit;
		this.params = params;
	}

	@Override
	protected StringBuffer compute() {
		SqlExportJsonDataBean bean = new SqlExportJsonDataBean();
		return bean.doExport(sqlDef, database, start, limit, params);
	}

}
