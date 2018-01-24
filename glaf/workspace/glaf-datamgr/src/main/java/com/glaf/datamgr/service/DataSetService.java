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

package com.glaf.datamgr.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.SelectSegment;
import com.glaf.datamgr.query.DataSetQuery;

@Transactional(readOnly = true)
public interface DataSetService {

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<DataSet> chartList(DataSetQuery query);

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<String> ids);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	DataSet getDataSet(String id);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getDataSetCountByQueryCriteria(DataSetQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<DataSet> getDataSetsByQueryCriteria(int start, int pageSize, DataSetQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<DataSet> list(DataSetQuery query);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(DataSet dataSet);

	/**
	 * 保存一条记录 (只保存数据集主表)
	 * 
	 * @return
	 */
	@Transactional
	public void saveOrUpdate(DataSet dataSet);

	/**
	 * 另存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void saveAs(DataSet dataSet);

	/**
	 * 另存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void saveAs(String dataSetId);

	@Transactional
	void updateDataSetExportStatus(DataSet model);

	@Transactional
	void updateMappings(String datasetId, List<SelectSegment> segments);

	JSONArray getDataSetParams(String dataSetId);

	/**
	 * 获取数据集目录树
	 * 
	 * @param code
	 * @return
	 */
	List<Map<String, Object>> getDataSetTree(String code);

	/**
	 * 获取数据集查询字段
	 * 
	 * @param datasetId
	 * @return
	 */
	JSONArray getDataSetFields(String datasetId);

	/**
	 * 获取数据集元数据
	 */
	JSONObject getDataSetMetadata(String dataSetId);

	/**
	 * 获取数据集加密列
	 * @param dataSetId
	 * @return
	 */
	JSONArray getDataSetEncrypts(String dataSetId);
}
