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

package com.glaf.datamgr.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.UUID32;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.DataSetAudit;
import com.glaf.datamgr.domain.FromSegment;
import com.glaf.datamgr.domain.GroupBySegment;
import com.glaf.datamgr.domain.HavingSegment;
import com.glaf.datamgr.domain.JoinSegment;
import com.glaf.datamgr.domain.OrderBySegment;
import com.glaf.datamgr.domain.SelectSegment;
import com.glaf.datamgr.domain.SqlParameter;
import com.glaf.datamgr.domain.WhereSegment;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.datamgr.mapper.DataSetMapper;
import com.glaf.datamgr.mapper.FromSegmentMapper;
import com.glaf.datamgr.mapper.GroupBySegmentMapper;
import com.glaf.datamgr.mapper.HavingSegmentMapper;
import com.glaf.datamgr.mapper.JoinSegmentMapper;
import com.glaf.datamgr.mapper.OrderBySegmentMapper;
import com.glaf.datamgr.mapper.SelectSegmentMapper;
import com.glaf.datamgr.mapper.SqlParameterMapper;
import com.glaf.datamgr.mapper.WhereSegmentMapper;
import com.glaf.datamgr.query.DataSetQuery;
import com.glaf.datamgr.service.DataSetAuditService;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.datamgr.util.DataSetJsonFactory;

@Service("dataSetService")
@Transactional(readOnly = true)
public class DataSetServiceImpl implements DataSetService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DataSetMapper dataSetMapper;

	protected SelectSegmentMapper selectSegmentMapper;

	protected FromSegmentMapper fromSegmentMapper;

	protected WhereSegmentMapper whereSegmentMapper;

	protected JoinSegmentMapper joinSegmentMapper;

	protected OrderBySegmentMapper orderBySegmentMapper;

	protected GroupBySegmentMapper groupBySegmentMapper;

	protected HavingSegmentMapper havingSegmentMapper;

	protected SqlParameterMapper sqlParameterMapper;

	protected DataSetAuditService dataSetAuditService;

	protected ITableDataService tableDataService;

	public DataSetServiceImpl() {

	}

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	public List<DataSet> chartList(DataSetQuery query) {
		query.chartFlag("Y");
		List<DataSet> list = dataSetMapper.getDataSets(query);
		return list;
	}

	public int count(DataSetQuery query) {
		return dataSetMapper.getDataSetCount(query);
	}

	@Transactional
	public void deleteById(String datasetId) {
		if (datasetId != null) {
			if (SystemConfig.getBoolean("use_dataset_cache")) {
				String cacheKey = "cache_dataset_" + datasetId;
				CacheFactory.remove("dataset", cacheKey);
			}
			DataSetBuilder.clearCache(datasetId);
			selectSegmentMapper.deleteSelectSegmentByDataSetId(datasetId);
			fromSegmentMapper.deleteFromSegmentByDataSetId(datasetId);
			whereSegmentMapper.deleteWhereSegmentByDataSetId(datasetId);
			joinSegmentMapper.deleteJoinSegmentByDataSetId(datasetId);
			orderBySegmentMapper.deleteOrderBySegmentByDataSetId(datasetId);
			groupBySegmentMapper.deleteGroupBySegmentByDataSetId(datasetId);
			havingSegmentMapper.deleteHavingSegmentByDataSetId(datasetId);
			sqlParameterMapper.getSqlParametersByDatasetId(datasetId);
			// dataSetMapper.deleteDataSetById(datasetId);
		}
	}

	@Transactional
	public void deleteByIds(List<String> datasetIds) {
		if (datasetIds != null && !datasetIds.isEmpty()) {
			for (String datasetId : datasetIds) {
				if (SystemConfig.getBoolean("use_dataset_cache")) {
					String cacheKey = "cache_dataset_" + datasetId;
					CacheFactory.remove("dataset", cacheKey);
				}
				DataSetBuilder.clearCache(datasetId);
				selectSegmentMapper.deleteSelectSegmentByDataSetId(datasetId);
				fromSegmentMapper.deleteFromSegmentByDataSetId(datasetId);
				whereSegmentMapper.deleteWhereSegmentByDataSetId(datasetId);
				joinSegmentMapper.deleteJoinSegmentByDataSetId(datasetId);
				orderBySegmentMapper.deleteOrderBySegmentByDataSetId(datasetId);
				groupBySegmentMapper.deleteGroupBySegmentByDataSetId(datasetId);
				havingSegmentMapper.deleteHavingSegmentByDataSetId(datasetId);
				sqlParameterMapper.getSqlParametersByDatasetId(datasetId);
				// dataSetMapper.deleteDataSetById(datasetId);
			}
		}
	}

	public DataSet getDataSet(String datasetId) {
		if (datasetId == null) {
			return null;
		}
		DataSet dataSet = null;
		String cacheKey = "cache_dataset_" + datasetId;

		if (SystemConfig.getBoolean("use_dataset_cache")) {
			String text = CacheFactory.getString("dataset", cacheKey);
			if (StringUtils.isNotEmpty(text)) {
				try {
					text = new String(Base64.decodeBase64(text));
					JSONObject json = JSON.parseObject(text);
					if (json != null) {
						// return DataSetJsonFactory.jsonToObject(json);
					}
				} catch (Exception ex) {
				}
			}
		}
		dataSet = dataSetMapper.getDataSetById(datasetId);
		if (dataSet != null) {
			dataSet.setSelectSegments(selectSegmentMapper.getSelectSegmentsByDataSetId(datasetId));
			dataSet.setFromSegments(fromSegmentMapper.getFromSegmentsByDataSetId(datasetId));
			dataSet.setWhereSegments(whereSegmentMapper.getWhereSegmentsByDataSetId(datasetId));
			dataSet.setJoinSegments(joinSegmentMapper.getJoinSegmentsByDataSetId(datasetId));
			dataSet.setOrderBySegments(orderBySegmentMapper.getOrderBySegmentsByDataSetId(datasetId));
			dataSet.setGroupBySegments(groupBySegmentMapper.getGroupBySegmentsByDataSetId(datasetId));
			dataSet.setHavingSegments(havingSegmentMapper.getHavingSegmentsByDataSetId(datasetId));
			dataSet.setParameters(sqlParameterMapper.getSqlParametersByDatasetId(datasetId));

			if (SystemConfig.getBoolean("use_dataset_cache")) {
				String text = DataSetJsonFactory.toJsonObject(dataSet).toJSONString();
				text = Base64.encodeBase64String(text.getBytes());
				// text = Base64.encodeBase64URLSafeString(text.getBytes());
				CacheFactory.put("dataset", cacheKey, text);
			}
		}
		return dataSet;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDataSetCountByQueryCriteria(DataSetQuery query) {
		return dataSetMapper.getDataSetCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DataSet> getDataSetsByQueryCriteria(int start, int pageSize, DataSetQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DataSet> rows = sqlSessionTemplate.selectList("getDataSets", query, rowBounds);
		return rows;
	}

	public List<DataSet> list(DataSetQuery query) {
		List<DataSet> list = dataSetMapper.getDataSets(query);
		return list;
	}

	@Transactional
	public void saveOrUpdate(DataSet dataSet) {
		if (StringUtils.isEmpty(dataSet.getId())) {
			dataSet.setId(UUID32.getUUID());
			dataSet.setCreateTime(new Date());
			dataSetMapper.insertDataSet(dataSet);
		} else {
			dataSet.setUpdateTime(new Date());
			dataSetMapper.updateDataSet(dataSet);
		}
	}

	@Transactional
	public void save(DataSet dataSet) {
		boolean isUpdate = false;
		boolean isChart = false;
		DataSetAudit audit = new DataSetAudit();

		if (dataSet.getSelectSegments() != null && !dataSet.getSelectSegments().isEmpty()) {
			for (SelectSegment seg : dataSet.getSelectSegments()) {
				if (StringUtils.isNotEmpty(seg.getChartCoord())) {
					isChart = true;
					break;
				}
			}
		}

		if (StringUtils.isEmpty(dataSet.getId()) || dataSet.isExtend()) {
			if (StringUtils.isEmpty(dataSet.getId())) {
				dataSet.setId(UUID32.getUUID());
			}
			dataSet.setCreateTime(new Date());
			if (isChart) {
				dataSet.setChartFlag("Y");
			} else {
				dataSet.setChartFlag("N");
			}
			dataSetMapper.insertDataSet(dataSet);
			if (dataSet.getSaveFlag() == null) {
				audit.setSaveFlag("C");
			}
		} else {
			isUpdate = true;

			if (isChart) {
				dataSet.setChartFlag("Y");
			} else {
				dataSet.setChartFlag("N");
			}

			dataSetMapper.updateDataSet(dataSet);

			if (SystemConfig.getBoolean("use_dataset_cache")) {
				String cacheKey = "cache_dataset_" + dataSet.getId();
				CacheFactory.remove("dataset", cacheKey);
			}

			DataSetBuilder.clearCache(dataSet.getId());

			if (dataSet.getSaveFlag() == null) {
				audit.setSaveFlag("U");
			}
			if (dataSet.getDeleteFlag() == 1) {
				audit.setSaveFlag("D");
			}
		}

		audit.setDatasetId(dataSet.getId());
		audit.setContent(DataSetJsonFactory.toJSON(dataSet));

		if (StringUtils.isEmpty(dataSet.getId())) {
			audit.setCreateBy(dataSet.getCreateBy());
		} else {
			audit.setCreateBy(dataSet.getUpdateBy());
		}

		// dataSetAuditService.save(audit);

		if (isUpdate && dataSet.getSelectSegments() != null && !dataSet.getSelectSegments().isEmpty()) {
			this.deleteById(dataSet.getId());
		}

		if (dataSet.getSelectSegments() != null && !dataSet.getSelectSegments().isEmpty()) {

			for (SelectSegment seg : dataSet.getSelectSegments()) {
				seg.setId(idGenerator.nextId());
				seg.setDatasetId(dataSet.getId());
				selectSegmentMapper.insertSelectSegment(seg);
			}

			/*
			 * JdbcInsert<SelectSegment> insert = new
			 * JdbcInsert<SelectSegment>();
			 * insert.setConnection(DataSourceUtils.getConnection(jdbcTemplate
			 * .getDataSource())); for (SelectSegment seg :
			 * dataSet.getSelectSegments()) { seg.setId(idGenerator.nextId());
			 * seg.setDatasetId(dataSet.getId()); insert.addBatch(seg); } if
			 * (insert.batchSize() > 0) { try { insert.executeBatch(); } catch
			 * (SQLException e) { e.printStackTrace();
			 * logger.error(e.getMessage()); } }
			 */

			if (dataSet.getFromSegments() != null && !dataSet.getFromSegments().isEmpty()) {
				for (FromSegment seg : dataSet.getFromSegments()) {
					seg.setId(idGenerator.nextId());
					seg.setDatasetId(dataSet.getId());
					fromSegmentMapper.insertFromSegment(seg);
				}
			}

			if (dataSet.getWhereSegments() != null && !dataSet.getWhereSegments().isEmpty()) {
				for (WhereSegment seg : dataSet.getWhereSegments()) {
					seg.setId(idGenerator.nextId());
					seg.setDatasetId(dataSet.getId());
					whereSegmentMapper.insertWhereSegment(seg);
				}
			}

			if (dataSet.getJoinSegments() != null && !dataSet.getJoinSegments().isEmpty()) {
				for (JoinSegment seg : dataSet.getJoinSegments()) {
					seg.setId(idGenerator.nextId());
					seg.setDatasetId(dataSet.getId());
					joinSegmentMapper.insertJoinSegment(seg);
				}
			}

			if (dataSet.getOrderBySegments() != null && !dataSet.getOrderBySegments().isEmpty()) {
				for (OrderBySegment seg : dataSet.getOrderBySegments()) {
					seg.setId(idGenerator.nextId());
					seg.setDatasetId(dataSet.getId());
					orderBySegmentMapper.insertOrderBySegment(seg);
				}
			}

			if (dataSet.getGroupBySegments() != null && !dataSet.getGroupBySegments().isEmpty()) {
				for (GroupBySegment seg : dataSet.getGroupBySegments()) {
					seg.setId(idGenerator.nextId());
					seg.setDatasetId(dataSet.getId());
					groupBySegmentMapper.insertGroupBySegment(seg);
				}
			}

			if (dataSet.getHavingSegments() != null && !dataSet.getHavingSegments().isEmpty()) {
				for (HavingSegment seg : dataSet.getHavingSegments()) {
					seg.setId(idGenerator.nextId());
					seg.setDatasetId(dataSet.getId());
					havingSegmentMapper.insertHavingSegment(seg);
				}
			}

			if (dataSet.getParameters() != null && !dataSet.getParameters().isEmpty()) {
				for (SqlParameter param : dataSet.getParameters()) {
					param.setId(idGenerator.nextId());
					param.setDatasetId(dataSet.getId());
					sqlParameterMapper.insertSqlParameter(param);
				}
			}

		}
	}

	@Transactional
	public void saveAs(DataSet dataSet) {
		boolean isChart = false;

		if (dataSet.getSelectSegments() != null && !dataSet.getSelectSegments().isEmpty()) {
			for (SelectSegment seg : dataSet.getSelectSegments()) {
				if (StringUtils.isNotEmpty(seg.getChartCoord())) {
					isChart = true;
					break;
				}
			}
		}

		dataSet.setId(UUID32.getUUID());
		dataSet.setCreateTime(new Date());
		if (isChart) {
			dataSet.setChartFlag("Y");
		} else {
			dataSet.setChartFlag("N");
		}
		dataSetMapper.insertDataSet(dataSet);

		DataSetAudit audit = new DataSetAudit();
		audit.setDatasetId(dataSet.getId());
		audit.setContent(DataSetJsonFactory.toJSON(dataSet));
		audit.setCreateBy(dataSet.getCreateBy());
		audit.setCreateTime(new Date());

		if (dataSet.getSaveFlag() == null) {
			audit.setSaveFlag("C");
		}
		if (isChart) {
			dataSet.setChartFlag("Y");
		} else {
			dataSet.setChartFlag("N");
		}

		if (dataSet.getSelectSegments() != null && !dataSet.getSelectSegments().isEmpty()) {
			for (SelectSegment seg : dataSet.getSelectSegments()) {
				seg.setId(idGenerator.nextId());
				seg.setDatasetId(dataSet.getId());
				selectSegmentMapper.insertSelectSegment(seg);
			}

			/*
			 * JdbcInsert<SelectSegment> insert = new
			 * JdbcInsert<SelectSegment>();
			 * insert.setConnection(DataSourceUtils.getConnection(jdbcTemplate
			 * .getDataSource())); for (SelectSegment seg :
			 * dataSet.getSelectSegments()) { seg.setId(idGenerator.nextId());
			 * seg.setDatasetId(dataSet.getId()); insert.addBatch(seg); } if
			 * (insert.batchSize() > 0) { try { insert.executeBatch(); } catch
			 * (SQLException e) { e.printStackTrace();
			 * logger.error(e.getMessage()); } }
			 */

			if (dataSet.getFromSegments() != null && !dataSet.getFromSegments().isEmpty()) {
				for (FromSegment seg : dataSet.getFromSegments()) {
					seg.setId(idGenerator.nextId());
					seg.setDatasetId(dataSet.getId());
					fromSegmentMapper.insertFromSegment(seg);
				}
			}

			if (dataSet.getWhereSegments() != null && !dataSet.getWhereSegments().isEmpty()) {
				for (WhereSegment seg : dataSet.getWhereSegments()) {
					seg.setId(idGenerator.nextId());
					seg.setDatasetId(dataSet.getId());
					whereSegmentMapper.insertWhereSegment(seg);
				}
			}

			if (dataSet.getJoinSegments() != null && !dataSet.getJoinSegments().isEmpty()) {
				for (JoinSegment seg : dataSet.getJoinSegments()) {
					seg.setId(idGenerator.nextId());
					seg.setDatasetId(dataSet.getId());
					joinSegmentMapper.insertJoinSegment(seg);
				}
			}

			if (dataSet.getOrderBySegments() != null && !dataSet.getOrderBySegments().isEmpty()) {
				for (OrderBySegment seg : dataSet.getOrderBySegments()) {
					seg.setId(idGenerator.nextId());
					seg.setDatasetId(dataSet.getId());
					orderBySegmentMapper.insertOrderBySegment(seg);
				}
			}

			if (dataSet.getGroupBySegments() != null && !dataSet.getGroupBySegments().isEmpty()) {
				for (GroupBySegment seg : dataSet.getGroupBySegments()) {
					seg.setId(idGenerator.nextId());
					seg.setDatasetId(dataSet.getId());
					groupBySegmentMapper.insertGroupBySegment(seg);
				}
			}

			if (dataSet.getHavingSegments() != null && !dataSet.getHavingSegments().isEmpty()) {
				for (HavingSegment seg : dataSet.getHavingSegments()) {
					seg.setId(idGenerator.nextId());
					seg.setDatasetId(dataSet.getId());
					havingSegmentMapper.insertHavingSegment(seg);
				}
			}

			if (dataSet.getParameters() != null && !dataSet.getParameters().isEmpty()) {
				for (SqlParameter param : dataSet.getParameters()) {
					param.setId(idGenerator.nextId());
					param.setDatasetId(dataSet.getId());
					sqlParameterMapper.insertSqlParameter(param);
				}
			}
		}
	}

	/**
	 * 另存一条记录
	 * 
	 * @return
	 */
	@Transactional
	public void saveAs(String dataSetId) {
		DataSet dataSet = this.getDataSet(dataSetId);
		if (dataSet != null) {
			this.saveAs(dataSet);
		}
	}

	/**
	 * 获取数据集后台参数
	 * 
	 * @param dataSetId
	 * @return
	 */
	public JSONArray getDataSetParams(String dataSetId) {
		DataSetAudit dataSetAudit = dataSetAuditService.getLastestDataSetAudit(dataSetId);
		if (dataSetAudit != null) {
			String content = dataSetAudit.getContent();
			if (StringUtils.isNotBlank(content)) {
				JSONObject jsonObject = JSON.parseObject(content);
				if (jsonObject.getString("params") != null) {
					return jsonObject.getJSONArray("params");
				}
			}
		}
		return null;
	}

	@javax.annotation.Resource
	public void setDataSetAuditService(DataSetAuditService dataSetAuditService) {
		this.dataSetAuditService = dataSetAuditService;
	}

	@javax.annotation.Resource
	public void setDataSetMapper(DataSetMapper dataSetMapper) {
		this.dataSetMapper = dataSetMapper;
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setFromSegmentMapper(FromSegmentMapper fromSegmentMapper) {
		this.fromSegmentMapper = fromSegmentMapper;
	}

	@javax.annotation.Resource
	public void setGroupBySegmentMapper(GroupBySegmentMapper groupBySegmentMapper) {
		this.groupBySegmentMapper = groupBySegmentMapper;
	}

	@javax.annotation.Resource
	public void setHavingSegmentMapper(HavingSegmentMapper havingSegmentMapper) {
		this.havingSegmentMapper = havingSegmentMapper;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setJoinSegmentMapper(JoinSegmentMapper joinSegmentMapper) {
		this.joinSegmentMapper = joinSegmentMapper;
	}

	@javax.annotation.Resource
	public void setOrderBySegmentMapper(OrderBySegmentMapper orderBySegmentMapper) {
		this.orderBySegmentMapper = orderBySegmentMapper;
	}

	@javax.annotation.Resource
	public void setSelectSegmentMapper(SelectSegmentMapper selectSegmentMapper) {
		this.selectSegmentMapper = selectSegmentMapper;
	}

	@javax.annotation.Resource
	public void setSqlParameterMapper(SqlParameterMapper sqlParameterMapper) {
		this.sqlParameterMapper = sqlParameterMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

	@javax.annotation.Resource
	public void setWhereSegmentMapper(WhereSegmentMapper whereSegmentMapper) {
		this.whereSegmentMapper = whereSegmentMapper;
	}

	@Transactional
	public void updateDataSetExportStatus(DataSet model) {
		if (SystemConfig.getBoolean("use_dataset_cache")) {
			String cacheKey = "cache_dataset_" + model.getId();
			CacheFactory.remove("dataset", cacheKey);
		}
		DataSetBuilder.clearCache(model.getId());
		dataSetMapper.updateDataSetExportStatus(model);
	}

	@Transactional
	public void updateMappings(String datasetId, List<SelectSegment> segments) {
		if (StringUtils.isNotEmpty(datasetId)) {
			if (SystemConfig.getBoolean("use_dataset_cache")) {
				String cacheKey = "cache_dataset_" + datasetId;
				CacheFactory.remove("dataset", cacheKey);
			}

			DataSetBuilder.clearCache(datasetId);

			TableModel tableModel = new TableModel();
			tableModel.setTableName("SYS_DATA_SELECT");
			ColumnModel idColumn = new ColumnModel();
			idColumn.setColumnName("DATASETID_");
			idColumn.setJavaType("String");
			idColumn.setValue(datasetId);
			tableModel.setIdColumn(idColumn);

			ColumnModel mappingColumn = new ColumnModel();
			mappingColumn.setColumnName("MAPPING_");
			mappingColumn.setJavaType("String");
			mappingColumn.setValue("");
			tableModel.addColumn(mappingColumn);
			tableDataService.updateTableData(tableModel);
		}
		if (StringUtils.isNotEmpty(datasetId)) {
			TableModel tableModel = new TableModel();
			tableModel.setTableName("SYS_DATASET");
			ColumnModel idColumn = new ColumnModel();
			idColumn.setColumnName("ID_");
			idColumn.setJavaType("String");
			idColumn.setValue(datasetId);
			tableModel.setIdColumn(idColumn);

			ColumnModel column = new ColumnModel();
			column.setColumnName("CHARTFLAG_");
			column.setJavaType("String");
			column.setValue("Y");
			tableModel.addColumn(column);
			tableDataService.updateTableData(tableModel);
		}
		for (SelectSegment segment : segments) {
			TableModel tableModel = new TableModel();
			tableModel.setTableName("SYS_DATA_SELECT");
			ColumnModel idColumn = new ColumnModel();
			idColumn.setColumnName("ID_");
			idColumn.setJavaType("Long");
			idColumn.setValue(segment.getId());
			tableModel.setIdColumn(idColumn);

			ColumnModel mappingColumn = new ColumnModel();
			mappingColumn.setColumnName("MAPPING_");
			mappingColumn.setJavaType("String");
			mappingColumn.setValue(segment.getMapping());
			tableModel.addColumn(mappingColumn);
			tableDataService.updateTableData(tableModel);
		}
	}

	@Override
	public List<Map<String, Object>> getDataSetTree(String code) {
		List<Map<String, Object>> list = null;
		String dt = DBConnectionFactory.getDatabaseType();
		if (StringUtils.equals(DBUtils.ORACLE, dt) || StringUtils.equals(DBUtils.DM_DBMS, dt)) {
			list = this.dataSetMapper.getDataSetTree_oracle(code);
		} else {
			list = this.dataSetMapper.getDataSetTree(code);
		}
		if (CollectionUtils.isNotEmpty(list)) {
			String dsKey = "ds";
			String indexIdKey = "indexId";
			String parentIdKey = "parentId";
			for (Map<String, Object> map : list) {
				if ("F".equalsIgnoreCase(MapUtils.getString(map, dsKey))) { // 数据集目录(转化一下id，避免跟其他目录id冲突)
					map.put(indexIdKey, map.get(indexIdKey) + dsKey);
				}
				map.put(parentIdKey, map.get(parentIdKey) + dsKey);
				// map.put("name", map.get("indexName"));
			}
		}
		return list;
	}

	@Override
	public JSONArray getDataSetFields(String datasetId) {
		JSONArray array = new JSONArray();
		List<SelectSegment> list = this.selectSegmentMapper.//
				getSelectSegmentsByDataSetId(datasetId);
		if (CollectionUtils.isNotEmpty(list)) {
			JSONObject json = null;
			for (SelectSegment segment : list) {
				if (StringUtils.equalsIgnoreCase(segment.getOutput(), "true")) {
					json = segment.toJsonObject();
					json.put("dname", segment.getColumnLabel());
					json.put("fname", segment.getTitle());
					array.add(json);
				}
			}
		}
		return array;
	}

	public JSONArray getDataSetEncrypts(String dataSetId) {
		String selectSegmentsKey = "selectSegments";
		JSONObject lastJson = this.getLastestDataSetAudit(dataSetId), json, j;
		if (lastJson != null && lastJson.containsKey(selectSegmentsKey)) {

			/**
			 * 需要哪些列 > 加
			 */
			String[] keys = new String[] { "columnLabel", "columnName", //
					"columnMapping", "tableName" };
			JSONArray encrypts = new JSONArray(), //
					selectSegments = lastJson.getJSONArray(selectSegmentsKey);
			for (int i = 0; i < selectSegments.size(); i++) {
				j = new JSONObject();
				json = selectSegments.getJSONObject(i);
				if (!json.getBooleanValue("encrypt")) {
					continue;
				}
				for (String key : keys) {
					j.put(key, json.get(key));
				}
				encrypts.add(j);
			}
			return encrypts;
		}
		return null;

	}

	@Override
	public JSONObject getDataSetMetadata(String dataSetId) {
		String metadataKey = "metadata", selectSegmentsKey = "selectSegments";
		JSONObject lastJson = this.getLastestDataSetAudit(dataSetId), json, mapping;
		if (lastJson != null && lastJson.containsKey(metadataKey)) {
			JSONObject metadata = lastJson.getJSONObject(metadataKey);
			if (lastJson.containsKey(selectSegmentsKey)) {
				String tableName;
				mapping = new JSONObject();
				JSONArray selectSegments = lastJson.getJSONArray(selectSegmentsKey);
				for (int i = 0; i < selectSegments.size(); i++) {
					json = selectSegments.getJSONObject(i);
					tableName = json.getString("tableName");
					mapping.put(json.getString("columnLabel"), tableName //
							+ "_0_" + json.getString("columnName"));
				}
				metadata.put("mapping", mapping);
			}
			return metadata;
		}
		return null;
	}

	protected JSONObject getLastestDataSetAudit(String dataSetId) {
		JSONObject dataSetJson = null;
		try {
			boolean use_query_cache = SystemConfig.getBoolean(//
					DataSetBuilder.use_query_cacheKey);
			if (use_query_cache) {
				String str = CacheFactory.getString(DataSetBuilder.dr, dataSetId);
				if (StringUtils.isNotBlank(str)) {
					DataSet ds = JSON.parseObject(str, DataSet.class);
					if (ds != null) {
						if (StringUtils.isNotBlank(ds.getLastestJson())) {
							dataSetJson = JSON.parseObject(ds.getLastestJson());
						} else
							dataSetJson = ds.getLastJson();
					}
				}
			}

			if (dataSetJson == null) {
				DataSetAudit dataSetAudit = dataSetAuditService//
						.getLastestDataSetAudit(dataSetId);
				String lastestJson = dataSetAudit.getContent();
				dataSetJson = JSON.parseObject(lastestJson);
			}

		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return dataSetJson;
	}

}
