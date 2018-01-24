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
 * 目标表定义
 *
 */
public class TargetTable implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 目标表名
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
	 * 主键表达式
	 */
	protected String primaryKeyExpression;

	/**
	 * 是否一主多从
	 */
	protected boolean multi;

	/**
	 * 切分符
	 */
	protected String split;

	/**
	 * 源表切分字段
	 */
	protected String splitSrcColumn;

	/**
	 * 目标表字段
	 */
	protected String splitToColumns;

	/**
	 * 转换器
	 */
	protected String converter;

	/**
	 * 预处理器
	 */
	protected String reprocessor;

	/**
	 * 后置处理器
	 */
	protected String postprocessor;

	/**
	 * 数据处理程序
	 */
	protected String dataHandler;

	protected TargetTable parent;

	/**
	 * 数据库命名
	 */
	protected String systemName;

	/**
	 * 保存前执行的SQL语句
	 */
	protected String beforeSql;

	/**
	 * 保存后执行的SQL语句
	 */
	protected String afterSql;

	protected List<Map<String, Object>> dataList;

	protected List<String> afterDynamicSqls = new ArrayList<String>();

	protected List<TargetTable> children = new ArrayList<TargetTable>();

	protected List<ColumnMapping> columns = new ArrayList<ColumnMapping>();

	protected Map<String, Object> attributeMap = new HashMap<String, Object>();

	public TargetTable() {

	}

	public void addAfterDynamicSql(String afterDynamicSql) {
		if (afterDynamicSqls == null) {
			afterDynamicSqls = new ArrayList<String>();
		}
		afterDynamicSqls.add(afterDynamicSql);
	}

	public void addChild(TargetTable child) {
		if (children == null) {
			children = new ArrayList<TargetTable>();
		}
		children.add(child);
	}

	public void addColumn(ColumnMapping col) {
		if (columns == null) {
			columns = new ArrayList<ColumnMapping>();
		}
		columns.add(col);
	}

	public List<String> getAfterDynamicSqls() {
		return afterDynamicSqls;
	}

	public String getAfterSql() {
		return afterSql;
	}

	public Map<String, Object> getAttributeMap() {
		return attributeMap;
	}

	public String getBeforeSql() {
		return beforeSql;
	}

	public List<TargetTable> getChildren() {
		return children;
	}

	public Map<String, ColumnMapping> getColumnMap() {
		Map<String, ColumnMapping> columnMap = new HashMap<String, ColumnMapping>();
		if (columns != null) {
			for (ColumnMapping col : columns) {
				columnMap.put(col.getSrcTableColumn(), col);
			}
		}
		return columnMap;
	}

	public List<ColumnMapping> getColumns() {
		return columns;
	}

	public String getConverter() {
		return converter;
	}

	public String getDataHandler() {
		return dataHandler;
	}

	public List<Map<String, Object>> getDataList() {
		return dataList;
	}

	public TargetTable getParent() {
		return parent;
	}

	public String getPostprocessor() {
		return postprocessor;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public String getPrimaryKeyExpression() {
		return primaryKeyExpression;
	}

	public String getReprocessor() {
		return reprocessor;
	}

	public String getSplit() {
		return split;
	}

	public String getSplitSrcColumn() {
		return splitSrcColumn;
	}

	public String getSplitToColumns() {
		return splitToColumns;
	}

	public String getSystemName() {
		return systemName;
	}

	public String getTable() {
		return table;
	}

	public String getTitle() {
		return title;
	}

	public boolean isMulti() {
		return multi;
	}

	public void setAfterDynamicSqls(List<String> afterDynamicSqls) {
		this.afterDynamicSqls = afterDynamicSqls;
	}

	public void setAfterSql(String afterSql) {
		this.afterSql = afterSql;
	}

	public void setAttributeMap(Map<String, Object> attributeMap) {
		this.attributeMap = attributeMap;
	}

	public void setBeforeSql(String beforeSql) {
		this.beforeSql = beforeSql;
	}

	public void setChildren(List<TargetTable> children) {
		this.children = children;
	}

	public void setColumns(List<ColumnMapping> columns) {
		this.columns = columns;
	}

	public void setConverter(String converter) {
		this.converter = converter;
	}

	public void setDataHandler(String dataHandler) {
		this.dataHandler = dataHandler;
	}

	public void setDataList(List<Map<String, Object>> dataList) {
		this.dataList = dataList;
	}

	public void setMulti(boolean multi) {
		this.multi = multi;
	}

	public void setParent(TargetTable parent) {
		this.parent = parent;
	}

	public void setPostprocessor(String postprocessor) {
		this.postprocessor = postprocessor;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public void setPrimaryKeyExpression(String primaryKeyExpression) {
		this.primaryKeyExpression = primaryKeyExpression;
	}

	public void setReprocessor(String reprocessor) {
		this.reprocessor = reprocessor;
	}

	public void setSplit(String split) {
		this.split = split;
	}

	public void setSplitSrcColumn(String splitSrcColumn) {
		this.splitSrcColumn = splitSrcColumn;
	}

	public void setSplitToColumns(String splitToColumns) {
		this.splitToColumns = splitToColumns;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
