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

package com.glaf.base.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.FieldInterface;
import com.glaf.base.modules.sys.model.TableEntity;
import com.glaf.base.modules.sys.query.FieldInterfaceQuery;

@Transactional(readOnly = true)
public interface IFieldInterfaceService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FieldInterface getFieldInterface(String id);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFieldInterfaceCount(Map<String, Object> parameter);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFieldInterfaceCountByQueryCriteria(FieldInterfaceQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<FieldInterface> getFieldInterfaces(Map<String, Object> parameter);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FieldInterface> getFieldInterfacesByQueryCriteria(int start,
			int pageSize, FieldInterfaceQuery query);

	/**
	 * 根据表格类型获取字段信息
	 * 
	 * @param frmType
	 * @return
	 */
	List<FieldInterface> getFieldsByFrmType(String frmType);

	List<FieldInterface> getListShowFields(String frmType, int indexId);

	/**
	 * 根据表格类型获取列表显示字段信息
	 * 
	 * @param frmType
	 * @return
	 */
	List<FieldInterface> getListShowFieldsByFrmType(String frmType);

	Map<String, Object> getRowData(String tableName, Object rowId);

	int getRowDataCountByQueryCriteria(TableEntity tableModel);

	List<Map<String, Object>> getRowDataListByQueryCriteria(int start,
			int pageSize, TableEntity tableModel);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<FieldInterface> list(FieldInterfaceQuery query);

	/**
	 * 转载字段的基础数据定义列表
	 * 
	 * @param fields
	 */
	void loadFieldHintList(List<FieldInterface> fields);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FieldInterface fieldInterface);

	@Transactional
	void saveData(String tableName, Map<String, Object> dataMap);

	@Transactional
	void saveDataList(String tableName, List<Map<String, Object>> dataList);

	/**
	 * 根据cell_data_table的id查询字段记录
	 * 
	 * @param frmType
	 * @return
	 */
	List<FieldInterface> getInterfacesByTableId(String frmType);

	List<FieldInterface> getInterfaceListByQuery(FieldInterfaceQuery query);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FieldInterface fieldInterface, String actorId);

	/**
	 * 根据表id及字段查询记录
	 * 
	 * @param tableId
	 * @param fieldName
	 * @return
	 */
	FieldInterface getFieldInterface(String tableId, String fieldName);
}
