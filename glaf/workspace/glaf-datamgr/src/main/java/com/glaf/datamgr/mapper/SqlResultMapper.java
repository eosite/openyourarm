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

package com.glaf.datamgr.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface SqlResultMapper {

	void bulkInsertSqlResult(List<SqlResult> list);

	void bulkInsertSqlResult_oracle(List<SqlResult> list);

	void deleteSqlResults(SqlResultQuery query);

	void deleteSqlResultById(SqlResultQuery query);

	void deleteByDatabaseId(SqlResultQuery query);

	void deleteBySqlDefId(SqlResultQuery query);

	SqlResult getSqlResultById(SqlResultQuery query);

	int getSqlResultCount(SqlResultQuery query);

	List<SqlResult> getSqlResults(SqlResultQuery query);

	void insertSqlResult(SqlResult model);

}
