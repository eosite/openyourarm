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

package com.glaf.datamgr.util;

import java.util.List;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.datamgr.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class DataSetJsonFactory {

	public static java.util.List<DataSet> arrayToList(JSONArray array) {
		java.util.List<DataSet> list = new java.util.ArrayList<DataSet>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DataSet model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static DataSet jsonToObject(JSONObject jsonObject) {
		DataSet model = new DataSet();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}

		if (jsonObject.containsKey("databaseId")) {
			model.setDatabaseId(jsonObject.getLong("databaseId"));
		}

		if (jsonObject.containsKey("executeDatabaseIds")) {
			model.setExecuteDatabaseIds(jsonObject.getString("executeDatabaseIds"));
		}

		if (jsonObject.containsKey("nodeId")) {
			model.setNodeId(jsonObject.getLong("nodeId"));
		}

		if (jsonObject.containsKey("category")) {
			model.setCategory(jsonObject.getString("category"));
		}

		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}

		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}

		if (jsonObject.containsKey("description")) {
			model.setDescription(jsonObject.getString("description"));
		}

		if (jsonObject.containsKey("statementId")) {
			model.setStatementId(jsonObject.getString("statementId"));
		}

		if (jsonObject.containsKey("sql")) {
			model.setSql(jsonObject.getString("sql"));
		}

		if (jsonObject.containsKey("sourceRuleId")) {
			model.setSourceRuleId(jsonObject.getString("sourceRuleId"));
		}

		if (jsonObject.containsKey("sourceRuleType")) {
			model.setSourceRuleType(jsonObject.getString("sourceRuleType"));
		}

		if (jsonObject.containsKey("sourceTables")) {
			model.setSourceTables(jsonObject.getString("sourceTables"));
		}

		if (jsonObject.containsKey("accessType")) {
			model.setAccessType(jsonObject.getString("accessType"));
		}

		if (jsonObject.containsKey("perms")) {
			model.setPerms(jsonObject.getString("perms"));
		}

		if (jsonObject.containsKey("addressPerms")) {
			model.setAddressPerms(jsonObject.getString("addressPerms"));
		}

		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}

		if (jsonObject.containsKey("active")) {
			model.setActive(jsonObject.getString("active"));
		}

		if (jsonObject.containsKey("cacheFlag")) {
			model.setCacheFlag(jsonObject.getString("cacheFlag"));
		}

		if (jsonObject.containsKey("chartFlag")) {
			model.setChartFlag(jsonObject.getString("chartFlag"));
		}

		if (jsonObject.containsKey("dynamicFlag")) {
			model.setDynamicFlag(jsonObject.getString("dynamicFlag"));
		}
		if (jsonObject.containsKey("dynamicDataSet")) {
			model.setDynamicDataSet(jsonObject.getString("dynamicDataSet"));
		}

		if (jsonObject.containsKey("distinctFlag")) {
			model.setDistinctFlag(jsonObject.getString("distinctFlag"));
		}

		if (jsonObject.containsKey("shareFlag")) {
			model.setShareFlag(jsonObject.getString("shareFlag"));
		}

		if (jsonObject.containsKey("scheduleFlag")) {
			model.setScheduleFlag(jsonObject.getString("scheduleFlag"));
		}

		if (jsonObject.containsKey("publicFlag")) {
			model.setPublicFlag(jsonObject.getString("publicFlag"));
		}

		if (jsonObject.containsKey("saveFlag")) {
			model.setSaveFlag(jsonObject.getString("saveFlag"));
		}

		if (jsonObject.containsKey("verify")) {
			model.setVerify(jsonObject.getString("verify"));
		}

		if (jsonObject.containsKey("initFlag")) {
			model.setInitFlag(jsonObject.getString("initFlag"));
		}

		if (jsonObject.containsKey("deleteFetch")) {
			model.setDeleteFetch(jsonObject.getString("deleteFetch"));
		}

		if (jsonObject.containsKey("exportFlag")) {
			model.setExportFlag(jsonObject.getString("exportFlag"));
		}

		if (jsonObject.containsKey("exportTableName")) {
			model.setExportTableName(jsonObject.getString("exportTableName"));
		}

		if (jsonObject.containsKey("exportDBName")) {
			model.setExportDBName(jsonObject.getString("exportDBName"));
		}

		if (jsonObject.containsKey("lastExportStatus")) {
			model.setLastExportStatus(jsonObject.getInteger("lastExportStatus"));
		}

		if (jsonObject.containsKey("lastExportTime")) {
			model.setLastExportTime(jsonObject.getDate("lastExportTime"));
		}

		if (jsonObject.containsKey("rowKey")) {
			model.setRowKey(jsonObject.getString("rowKey"));
		}

		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}

		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}

		if (jsonObject.containsKey("primaryKeys")) {
			model.setPrimaryKeys(jsonObject.getString("primaryKeys"));
		}

		if (jsonObject.containsKey("topId")) {
			model.setTopId(jsonObject.getString("topId"));
		}

		if (jsonObject.containsKey("treeId")) {
			model.setTreeId(jsonObject.getString("treeId"));
		}

		if (jsonObject.containsKey("selectSegments")) {
			JSONArray array = jsonObject.getJSONArray("selectSegments");
			List<SelectSegment> selectSegments = SelectSegmentJsonFactory.arrayToList(array);
			model.setSelectSegments(selectSegments);
		}

		if (jsonObject.containsKey("fromSegments")) {
			JSONArray array = jsonObject.getJSONArray("fromSegments");
			List<FromSegment> fromSegments = FromSegmentJsonFactory.arrayToList(array);
			model.setFromSegments(fromSegments);
		}

		if (jsonObject.containsKey("joinSegments")) {
			JSONArray array = jsonObject.getJSONArray("joinSegments");
			List<JoinSegment> joinSegments = JoinSegmentJsonFactory.arrayToList(array);
			model.setJoinSegments(joinSegments);
		}

		if (jsonObject.containsKey("whereSegments")) {
			JSONArray array = jsonObject.getJSONArray("whereSegments");
			List<WhereSegment> whereSegments = WhereSegmentJsonFactory.arrayToList(array);
			model.setWhereSegments(whereSegments);
		}

		if (jsonObject.containsKey("havingSegments")) {
			JSONArray array = jsonObject.getJSONArray("havingSegments");
			List<HavingSegment> havingSegments = HavingSegmentJsonFactory.arrayToList(array);
			model.setHavingSegments(havingSegments);
		}

		if (jsonObject.containsKey("groupBySegments")) {
			JSONArray array = jsonObject.getJSONArray("groupBySegments");
			List<GroupBySegment> groupBySegments = GroupBySegmentJsonFactory.arrayToList(array);
			model.setGroupBySegments(groupBySegments);
		}

		if (jsonObject.containsKey("orderBySegments")) {
			JSONArray array = jsonObject.getJSONArray("orderBySegments");
			List<OrderBySegment> orderBySegments = OrderBySegmentJsonFactory.arrayToList(array);
			model.setOrderBySegments(orderBySegments);
		}

		if (jsonObject.containsKey("parameters")) {
			JSONArray array = jsonObject.getJSONArray("parameters");
			List<SqlParameter> parameters = SqlParameterJsonFactory.arrayToList(array);
			model.setParameters(parameters);
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<DataSet> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DataSet model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static String toJSON(DataSet dataset) {
		ObjectNode responseJSON = DataSetJsonFactory.toObjectNode(dataset);

		if (dataset.getSelectSegments() != null && !dataset.getSelectSegments().isEmpty()) {
			ArrayNode selectJSON = new ObjectMapper().createArrayNode();
			responseJSON.set("selectSegments", selectJSON);

			for (SelectSegment seg : dataset.getSelectSegments()) {
				ObjectNode objectNode = new ObjectMapper().createObjectNode();
				if (seg.getColumnLabel() != null) {
					objectNode.put("columnLabel", seg.getColumnLabel());
				}
				if (seg.getExpression() != null) {
					objectNode.put("expression", seg.getExpression());
				}
				if (seg.getChartCoord() != null) {
					objectNode.put("chartCoord", seg.getChartCoord());
				}
				if (seg.getAggregate() != null) {
					objectNode.put("aggregate", seg.getAggregate());
				}
				if (seg.getOutput() != null) {
					objectNode.put("output", seg.getOutput());
				}
				if (seg.getMapping() != null) {
					objectNode.put("mapping", seg.getMapping());
				}
				objectNode.put("columnName", seg.getColumnName());
				objectNode.put("tableName", seg.getTableName());
				objectNode.put("title", seg.getTitle());
				objectNode.put("ordinal", seg.getOrdinal());
				selectJSON.add(objectNode);
			}

			if (dataset.getFromSegments() != null && !dataset.getFromSegments().isEmpty()) {
				ArrayNode fromJSON = new ObjectMapper().createArrayNode();
				responseJSON.set("fromSegments", fromJSON);
				for (FromSegment seg : dataset.getFromSegments()) {
					ObjectNode objectNode = new ObjectMapper().createObjectNode();
					objectNode.put("tableName", seg.getTableName());
					objectNode.put("tableId", seg.getTableId());
					objectNode.put("tableCN", seg.getTableCN());
					objectNode.put("position", seg.getPosition());
					objectNode.put("ordinal", seg.getOrdinal());
					fromJSON.add(objectNode);
				}
			}

			if (dataset.getJoinSegments() != null && !dataset.getJoinSegments().isEmpty()) {
				ArrayNode joinJSON = new ObjectMapper().createArrayNode();
				responseJSON.set("joinSegments", joinJSON);
				for (JoinSegment seg : dataset.getJoinSegments()) {
					ObjectNode objectNode = new ObjectMapper().createObjectNode();
					if (seg.getConnector() != null) {
						objectNode.put("connector", seg.getConnector());
					}
					objectNode.put("sourceTable", seg.getSourceTable());
					objectNode.put("sourceColumn", seg.getSourceColumn());
					objectNode.put("targetTable", seg.getTargetTable());
					objectNode.put("targetColumn", seg.getTargetColumn());
					objectNode.put("ordinal", seg.getOrdinal());
					joinJSON.add(objectNode);
				}
			}

			if (dataset.getWhereSegments() != null && !dataset.getWhereSegments().isEmpty()) {
				ArrayNode whereJSON = new ObjectMapper().createArrayNode();
				responseJSON.set("whereSegments", whereJSON);
				for (WhereSegment seg : dataset.getWhereSegments()) {
					ObjectNode objectNode = new ObjectMapper().createObjectNode();
					if (seg.getParentId() != null && seg.getParentId() > 0) {
						objectNode.put("parentId", seg.getParentId());
					}
					if (seg.getConnector() != null) {
						objectNode.put("connector", seg.getConnector());
					}
					if (seg.getOperator() != null) {
						objectNode.put("operator", seg.getOperator());
					}
					if (seg.getExpression() != null) {
						objectNode.put("expression", seg.getExpression());
					}
					if (seg.getCollection() != null) {
						objectNode.put("collection", seg.getCollection());
					}
					if (seg.getTableName() != null) {
						objectNode.put("tableName", seg.getTableName());
					}
					if (seg.getColumnName() != null) {
						objectNode.put("columnName", seg.getColumnName());
					}
					if (seg.getParameName() != null) {
						objectNode.put("parameName", seg.getParameName());
					}
					if (seg.getParameType() != null) {
						objectNode.put("parameType", seg.getParameType());
					}
					if (seg.getRequired() != null) {
						objectNode.put("required", seg.getRequired());
					}
					if (seg.getValue() != null) {
						objectNode.put("value", seg.getValue().toString());
					}
					objectNode.put("ordinal", seg.getOrdinal());
					whereJSON.add(objectNode);
				}
			}

			if (dataset.getGroupBySegments() != null && !dataset.getGroupBySegments().isEmpty()) {
				ArrayNode groupByJSON = new ObjectMapper().createArrayNode();
				responseJSON.set("groupBySegments", groupByJSON);
				for (GroupBySegment seg : dataset.getGroupBySegments()) {
					ObjectNode objectNode = new ObjectMapper().createObjectNode();
					objectNode.put("tableName", seg.getTableName());
					objectNode.put("columnName", seg.getColumnName());
					objectNode.put("ordinal", seg.getOrdinal());
					groupByJSON.add(objectNode);
				}
			}

			if (dataset.getHavingSegments() != null && !dataset.getHavingSegments().isEmpty()) {
				ArrayNode havingJSON = new ObjectMapper().createArrayNode();
				responseJSON.set("havingSegments", havingJSON);
				for (HavingSegment seg : dataset.getHavingSegments()) {
					ObjectNode objectNode = new ObjectMapper().createObjectNode();
					if (seg.getConnector() != null) {
						objectNode.put("connector", seg.getConnector());
					}
					if (seg.getOperator() != null) {
						objectNode.put("operator", seg.getOperator());
					}
					if (seg.getExpression() != null) {
						objectNode.put("expression", seg.getExpression());
					}
					if (seg.getCollection() != null) {
						objectNode.put("collection", seg.getCollection());
					}
					if (seg.getTableName() != null) {
						objectNode.put("tableName", seg.getTableName());
					}
					if (seg.getColumnName() != null) {
						objectNode.put("columnName", seg.getColumnName());
					}
					if (seg.getParameName() != null) {
						objectNode.put("parameName", seg.getParameName());
					}
					if (seg.getParameType() != null) {
						objectNode.put("parameType", seg.getParameType());
					}
					if (seg.getValue() != null) {
						objectNode.put("value", seg.getValue().toString());
					}
					objectNode.put("ordinal", seg.getOrdinal());
					havingJSON.add(objectNode);
				}

				if (dataset.getOrderBySegments() != null && !dataset.getOrderBySegments().isEmpty()) {
					ArrayNode orderByJSON = new ObjectMapper().createArrayNode();
					responseJSON.set("orderBySegments", orderByJSON);
					for (OrderBySegment seg : dataset.getOrderBySegments()) {
						ObjectNode objectNode = new ObjectMapper().createObjectNode();
						objectNode.put("columnName", seg.getColumnName());
						objectNode.put("tableName", seg.getTableName());
						objectNode.put("sort", seg.getSort());
						objectNode.put("ordinal", seg.getOrdinal());
						orderByJSON.add(objectNode);
					}
				}

				if (dataset.getParameters() != null && !dataset.getParameters().isEmpty()) {
					ArrayNode parametersJSON = new ObjectMapper().createArrayNode();
					responseJSON.set("parameters", parametersJSON);
					for (SqlParameter param : dataset.getParameters()) {
						ObjectNode objectNode = new ObjectMapper().createObjectNode();
						if (param.getSqlDefId() != null) {
							objectNode.put("sqlDefId", param.getSqlDefId());
						}
						if (param.getDatasetId() != null) {
							objectNode.put("datasetId", param.getDatasetId());
						}
						if (param.getName() != null) {
							objectNode.put("name", param.getName());
						}
						if (param.getMapping() != null) {
							objectNode.put("mapping", param.getMapping());
						}
						if (param.getTitle() != null) {
							objectNode.put("title", param.getTitle());
						}
						if (param.getDefaultValue() != null) {
							objectNode.put("defaultValue", param.getDefaultValue());
						}
						if (param.getType() != null) {
							objectNode.put("type", param.getType());
						}
						if (param.getCollection() != null) {
							objectNode.put("collection", param.getCollection());
						}
						parametersJSON.add(objectNode);
					}
				}
			}
		}

		return responseJSON.toString();
	}

	public static JSONObject toJsonObject(DataSet model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());

		jsonObject.put("databaseId", model.getDatabaseId());

		if (model.getExecuteDatabaseIds() != null) {
			jsonObject.put("executeDatabaseIds", model.getExecuteDatabaseIds());
		}

		jsonObject.put("nodeId", model.getNodeId());

		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getCategory() != null) {
			jsonObject.put("category", model.getCategory());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getDescription() != null) {
			jsonObject.put("description", model.getDescription());
		}
		if (model.getStatementId() != null) {
			jsonObject.put("statementId", model.getStatementId());
		}
		if (model.getSql() != null) {
			jsonObject.put("sql", model.getSql());
		}
		if (model.getSourceRuleId() != null) {
			jsonObject.put("sourceRuleId", model.getSourceRuleId());
		}
		if (model.getSourceRuleType() != null) {
			jsonObject.put("sourceRuleType", model.getSourceRuleType());
		}
		if (model.getSourceTables() != null) {
			jsonObject.put("sourceTables", model.getSourceTables());
		}
		if (model.getAccessType() != null) {
			jsonObject.put("accessType", model.getAccessType());
		}
		if (model.getPerms() != null) {
			jsonObject.put("perms", model.getPerms());
		}
		if (model.getAddressPerms() != null) {
			jsonObject.put("addressPerms", model.getAddressPerms());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}

		if (model.getActive() != null) {
			jsonObject.put("active", model.getActive());
		}
		if (model.getScheduleFlag() != null) {
			jsonObject.put("scheduleFlag", model.getScheduleFlag());
		}

		if (model.getCacheFlag() != null) {
			jsonObject.put("cacheFlag", model.getCacheFlag());
		}

		if (model.getChartFlag() != null) {
			jsonObject.put("chartFlag", model.getChartFlag());
		}

		if (model.getDynamicFlag() != null) {
			jsonObject.put("dynamicFlag", model.getDynamicFlag());
		}
		if (model.getDynamicDataSet() != null) {
			jsonObject.put("dynamicDataSet", model.getDynamicDataSet());
		}

		if (model.getDistinctFlag() != null) {
			jsonObject.put("distinctFlag", model.getDistinctFlag());
		}

		if (model.getPublicFlag() != null) {
			jsonObject.put("publicFlag", model.getPublicFlag());
		}
		if (model.getShareFlag() != null) {
			jsonObject.put("shareFlag", model.getShareFlag());
		}
		if (model.getSaveFlag() != null) {
			jsonObject.put("saveFlag", model.getSaveFlag());
		}
		if (model.getUpdateFlag() != null) {
			jsonObject.put("updateFlag", model.getUpdateFlag());
		}
		if (model.getVerify() != null) {
			jsonObject.put("verify", model.getVerify());
		}
		if (model.getInitFlag() != null) {
			jsonObject.put("initFlag", model.getInitFlag());
		}
		if (model.getDeleteFetch() != null) {
			jsonObject.put("deleteFetch", model.getDeleteFetch());
		}
		if (model.getExportFlag() != null) {
			jsonObject.put("exportFlag", model.getExportFlag());
		}
		if (model.getExportDBName() != null) {
			jsonObject.put("exportDBName", model.getExportDBName());
		}
		if (model.getExportTableName() != null) {
			jsonObject.put("exportTableName", model.getExportTableName());
		}
		jsonObject.put("lastExportStatus", model.getLastExportStatus());
		if (model.getLastExportTime() != null) {
			jsonObject.put("lastExportTime", DateUtils.getDate(model.getLastExportTime()));
			jsonObject.put("lastExportTime_date", DateUtils.getDate(model.getLastExportTime()));
			jsonObject.put("lastExportTime_datetime", DateUtils.getDateTime(model.getLastExportTime()));
		}

		if (model.getRowKey() != null) {
			jsonObject.put("rowKey", model.getRowKey());
		}

		jsonObject.put("locked", model.getLocked());
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateTime() != null) {
			jsonObject.put("updateTime", DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_date", DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_datetime", DateUtils.getDateTime(model.getUpdateTime()));
		}
		if (model.getPrimaryKeys() != null) {
			jsonObject.put("primaryKeys", model.getPrimaryKeys());
		}

		// if (model.getTopId() != null) {
		jsonObject.put("topId", model.getTopId());
		// }
		if (model.getTreeId() != null) {
			jsonObject.put("treeId", model.getTreeId());
		}

		return jsonObject;
	}

	public static ObjectNode toObjectNode(DataSet model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());

		jsonObject.put("databaseId", model.getDatabaseId());

		if (model.getExecuteDatabaseIds() != null) {
			jsonObject.put("executeDatabaseIds", model.getExecuteDatabaseIds());
		}

		jsonObject.put("nodeId", model.getNodeId());

		if (model.getCategory() != null) {
			jsonObject.put("category", model.getCategory());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getDescription() != null) {
			jsonObject.put("description", model.getDescription());
		}

		if (model.getStatementId() != null) {
			jsonObject.put("statementId", model.getStatementId());
		}

		if (model.getSql() != null) {
			jsonObject.put("sql", model.getSql());
		}

		if (model.getSourceRuleId() != null) {
			jsonObject.put("sourceRuleId", model.getSourceRuleId());
		}
		if (model.getSourceRuleType() != null) {
			jsonObject.put("sourceRuleType", model.getSourceRuleType());
		}
		if (model.getSourceTables() != null) {
			jsonObject.put("sourceTables", model.getSourceTables());
		}

		if (model.getAccessType() != null) {
			jsonObject.put("accessType", model.getAccessType());
		}
		if (model.getPerms() != null) {
			jsonObject.put("perms", model.getPerms());
		}
		if (model.getAddressPerms() != null) {
			jsonObject.put("addressPerms", model.getAddressPerms());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}

		if (model.getActive() != null) {
			jsonObject.put("active", model.getActive());
		}
		if (model.getScheduleFlag() != null) {
			jsonObject.put("scheduleFlag", model.getScheduleFlag());
		}

		if (model.getCacheFlag() != null) {
			jsonObject.put("cacheFlag", model.getCacheFlag());
		}

		if (model.getChartFlag() != null) {
			jsonObject.put("chartFlag", model.getChartFlag());
		}

		if (model.getDynamicFlag() != null) {
			jsonObject.put("dynamicFlag", model.getDynamicFlag());
		}
		if (model.getDynamicDataSet() != null) {
			jsonObject.put("dynamicDataSet", model.getDynamicDataSet());
		}

		if (model.getDistinctFlag() != null) {
			jsonObject.put("distinctFlag", model.getDistinctFlag());
		}

		if (model.getPublicFlag() != null) {
			jsonObject.put("publicFlag", model.getPublicFlag());
		}
		if (model.getShareFlag() != null) {
			jsonObject.put("shareFlag", model.getShareFlag());
		}
		if (model.getSaveFlag() != null) {
			jsonObject.put("saveFlag", model.getSaveFlag());
		}
		if (model.getUpdateFlag() != null) {
			jsonObject.put("updateFlag", model.getUpdateFlag());
		}
		if (model.getVerify() != null) {
			jsonObject.put("verify", model.getVerify());
		}
		if (model.getInitFlag() != null) {
			jsonObject.put("initFlag", model.getInitFlag());
		}

		if (model.getDeleteFetch() != null) {
			jsonObject.put("deleteFetch", model.getDeleteFetch());
		}
		if (model.getExportFlag() != null) {
			jsonObject.put("exportFlag", model.getExportFlag());
		}
		if (model.getExportDBName() != null) {
			jsonObject.put("exportDBName", model.getExportDBName());
		}
		if (model.getExportTableName() != null) {
			jsonObject.put("exportTableName", model.getExportTableName());
		}
		jsonObject.put("lastExportStatus", model.getLastExportStatus());
		if (model.getLastExportTime() != null) {
			jsonObject.put("lastExportTime", DateUtils.getDate(model.getLastExportTime()));
			jsonObject.put("lastExportTime_date", DateUtils.getDate(model.getLastExportTime()));
			jsonObject.put("lastExportTime_datetime", DateUtils.getDateTime(model.getLastExportTime()));
		}

		if (model.getRowKey() != null) {
			jsonObject.put("rowKey", model.getRowKey());
		}

		jsonObject.put("locked", model.getLocked());
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateTime() != null) {
			jsonObject.put("updateTime", DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_date", DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_datetime", DateUtils.getDateTime(model.getUpdateTime()));
		}

		if (model.getPrimaryKeys() != null) {
			jsonObject.put("primaryKeys", model.getPrimaryKeys());
		}
		if (model.getTopId() != null) {
			jsonObject.put("topId", model.getTopId());
		}
		if (model.getTreeId() != null) {
			jsonObject.put("treeId", model.getTreeId());
		}
		return jsonObject;
	}

	private DataSetJsonFactory() {

	}

}
