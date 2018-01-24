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

package com.glaf.matrix.data.sync.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表同步定义
 *
 */
public class TableSyncDefinition implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	protected String id;

	/**
	 * 来源表名
	 */
	protected String table;

	/**
	 * 主题
	 */
	protected String title;

	/**
	 * 主键
	 */
	protected String primaryKey;

	/**
	 * 转换器
	 */
	protected String converter;

	/**
	 * 数据库命名
	 */
	protected String systemName;

	protected List<TargetTable> targetTables = new ArrayList<TargetTable>();

	protected Map<String, Object> attributeMap = new HashMap<String, Object>();

	public TableSyncDefinition() {

	}

	public void addTargetTable(TargetTable table) {
		if (targetTables == null) {
			targetTables = new ArrayList<TargetTable>();
		}
		targetTables.add(table);
	}

	public Map<String, Object> getAttributeMap() {
		return attributeMap;
	}

	public String getConverter() {
		return converter;
	}

	public String getId() {
		return id;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public String getSystemName() {
		return systemName;
	}

	public String getTable() {
		return table;
	}

	public List<TargetTable> getTargetTables() {
		return targetTables;
	}

	public String getTitle() {
		return title;
	}

	public void setAttributeMap(Map<String, Object> attributeMap) {
		this.attributeMap = attributeMap;
	}

	public void setConverter(String converter) {
		this.converter = converter;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public void setTargetTables(List<TargetTable> targetTables) {
		this.targetTables = targetTables;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
