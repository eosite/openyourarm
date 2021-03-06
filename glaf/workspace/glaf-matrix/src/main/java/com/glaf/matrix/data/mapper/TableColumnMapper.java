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

package com.glaf.matrix.data.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.matrix.data.domain.TableColumn;
import com.glaf.matrix.data.query.TableColumnQuery;

@Component
public interface TableColumnMapper {

	void deleteTableColumnById(String id);

	void deleteTableColumnByTableId(String tableId);

	void deleteTableColumnByTargetId(String targetId);

	TableColumn getTableColumnById(String id);

	int getTableColumnCount(TableColumnQuery query);

	List<TableColumn> getTableColumns(TableColumnQuery query);

	List<TableColumn> getTableColumnsByQueryId(String queryId);

	List<TableColumn> getTableColumnsByTableId(String tableId);

	List<TableColumn> getTableColumnsByTableName(String tableName);

	List<TableColumn> getTableColumnsByTargetId(String targetId);

	void insertTableColumn(TableColumn model);

	void updateTableColumn(TableColumn model);

}