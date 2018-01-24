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
public interface DataExportMapper {

	void bulkInsertDataExport(List<DataExport> list);

	void bulkInsertDataExport_oracle(List<DataExport> list);

	void deleteDataExportById(Long id);

	void deleteDataExports(DataExportQuery query);

	DataExport getDataExportById(Long id);
	
	DataExport getDataExportByUserId(String userId);

	int getDataExportCount(DataExportQuery query);

	List<DataExport> getDataExports(DataExportQuery query);

	void insertDataExport(DataExport model);

	void updateDataExport(DataExport model);

	void updateDataExportExportStatus(DataExport model);

}