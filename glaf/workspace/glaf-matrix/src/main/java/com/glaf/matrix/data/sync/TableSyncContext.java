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

package com.glaf.matrix.data.sync;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.glaf.core.domain.ColumnDefinition;
import com.glaf.matrix.data.sync.model.TargetTable;

public class TableSyncContext {

	protected AtomicInteger currentId;

	protected Object initData;

	protected TargetTable targetTable;

	protected Map<String, Object> masterData;

	protected List<Map<String, Object>> dataList;

	protected List<Map<String, Object>> processedDataList;

	protected ColumnDefinition idColumn;

	protected List<ColumnDefinition> columns;

	public TableSyncContext() {

	}

	public List<ColumnDefinition> getColumns() {
		return columns;
	}

	public int getCurrentId() {
		return currentId.incrementAndGet();
	}

	public List<Map<String, Object>> getDataList() {
		return dataList;
	}

	public ColumnDefinition getIdColumn() {
		return idColumn;
	}

	public Object getInitData() {
		return initData;
	}

	public Map<String, Object> getMasterData() {
		return masterData;
	}

	public List<Map<String, Object>> getProcessedDataList() {
		return processedDataList;
	}

	public TargetTable getTargetTable() {
		return targetTable;
	}

	public void setColumns(List<ColumnDefinition> columns) {
		this.columns = columns;
	}

	public void setCurrentId(int nowId) {
		if (currentId == null) {
			currentId = new AtomicInteger();
		}
		currentId.set(nowId);
	}

	public void setDataList(List<Map<String, Object>> dataList) {
		this.dataList = dataList;
	}

	public void setIdColumn(ColumnDefinition idColumn) {
		this.idColumn = idColumn;
	}

	public void setInitData(Object initData) {
		this.initData = initData;
	}

	public void setMasterData(Map<String, Object> masterData) {
		this.masterData = masterData;
	}

	public void setProcessedDataList(List<Map<String, Object>> processedDataList) {
		this.processedDataList = processedDataList;
	}

	public void setTargetTable(TargetTable targetTable) {
		this.targetTable = targetTable;
	}

}
