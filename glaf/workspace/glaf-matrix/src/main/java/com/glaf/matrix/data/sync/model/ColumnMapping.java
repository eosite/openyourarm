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

import java.util.HashMap;
import java.util.Map;

/**
 * 转换列定义
 *
 */
public class ColumnMapping implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 源表字段
	 */
	protected String srcTableColumn;

	/**
	 * 目标表字段
	 */
	protected String targetTableColumn;

	/**
	 * 值表达式
	 */
	protected String valueExpression;

	/**
	 * 初始值
	 */
	protected String initValue;

	/**
	 * 主题
	 */
	protected String title;

	protected Map<String, Object> attributeMap = new HashMap<String, Object>();

	public ColumnMapping() {

	}

	public Map<String, Object> getAttributeMap() {
		return attributeMap;
	}

	public String getInitValue() {
		return initValue;
	}

	public String getSrcTableColumn() {
		return srcTableColumn;
	}

	public String getTargetTableColumn() {
		return targetTableColumn;
	}

	public String getTitle() {
		return title;
	}

	public String getValueExpression() {
		return valueExpression;
	}

	public void setAttributeMap(Map<String, Object> attributeMap) {
		this.attributeMap = attributeMap;
	}

	public void setInitValue(String initValue) {
		this.initValue = initValue;
	}

	public void setSrcTableColumn(String srcTableColumn) {
		this.srcTableColumn = srcTableColumn;
	}

	public void setTargetTableColumn(String targetTableColumn) {
		this.targetTableColumn = targetTableColumn;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setValueExpression(String valueExpression) {
		this.valueExpression = valueExpression;
	}

}
