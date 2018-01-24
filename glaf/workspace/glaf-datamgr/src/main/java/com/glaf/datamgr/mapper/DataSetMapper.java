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
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface DataSetMapper {

	void deleteDataSets(DataSetQuery query);

	void deleteDataSetById(String id);

	DataSet getDataSetById(String id);

	int getDataSetCount(DataSetQuery query);

	List<DataSet> getDataSets(DataSetQuery query);

	void insertDataSet(DataSet model);

	void updateDataSet(DataSet model);

	void updateDataSetExportStatus(DataSet model);

	List<Map<String, Object>> getDataSetTree(@Param("code") String code);

	List<Map<String, Object>> getDataSetTree_oracle(@Param("code") String code);

}