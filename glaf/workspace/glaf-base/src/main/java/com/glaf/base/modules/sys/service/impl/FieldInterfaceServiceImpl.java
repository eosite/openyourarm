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

package com.glaf.base.modules.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.mapper.FieldInterfaceMapper;
import com.glaf.base.modules.sys.model.FieldInterface;
import com.glaf.base.modules.sys.model.TableEntity;
import com.glaf.base.modules.sys.query.FieldInterfaceQuery;
import com.glaf.base.modules.sys.service.IFieldInterfaceService;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.util.ParamUtils;

@Service("fieldInterfaceService")
@Transactional(readOnly = true)
public class FieldInterfaceServiceImpl implements IFieldInterfaceService {
	protected static final Log logger = LogFactory
			.getLog(FieldInterfaceServiceImpl.class);

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected FieldInterfaceMapper fieldInterfaceMapper;

	protected ITableDataService tableDataService;

	public FieldInterfaceServiceImpl() {

	}

	public int count(FieldInterfaceQuery query) {
		return fieldInterfaceMapper
				.getFieldInterfaceCountByQueryCriteria(query);
	}

	@Transactional
	public void deleteById(String id) {
		fieldInterfaceMapper.deleteFieldInterfaceById(id);
	}

	public FieldInterface getFieldInterface(String id) {
		FieldInterface fieldInterface = fieldInterfaceMapper
				.getFieldInterfaceById(id);
		return fieldInterface;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFieldInterfaceCount(Map<String, Object> parameter) {
		return fieldInterfaceMapper.getFieldInterfaceCount(parameter);
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFieldInterfaceCountByQueryCriteria(FieldInterfaceQuery query) {
		return fieldInterfaceMapper
				.getFieldInterfaceCountByQueryCriteria(query);
	}

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	public List<FieldInterface> getFieldInterfaces(Map<String, Object> parameter) {
		List<FieldInterface> fields = fieldInterfaceMapper
				.getFieldInterfaces(parameter);
		this.loadFieldHintList(fields);
		return fields;
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FieldInterface> getFieldInterfacesByQueryCriteria(int start,
			int pageSize, FieldInterfaceQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FieldInterface> fields = sqlSession.selectList(
				"getFieldInterfacesByQueryCriteria", query, rowBounds);
		this.loadFieldHintList(fields);
		return fields;
	}

	public List<FieldInterface> getFieldsByFrmType(String frmType) {
		List<FieldInterface> fields = fieldInterfaceMapper
				.getFieldsByFrmType(frmType);
		this.loadFieldHintList(fields);
		return fields;
	}

	public List<FieldInterface> getListShowFields(String frmType, int indexId) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("frmType", frmType);
		parameter.put("indexId", indexId);
		List<FieldInterface> fields = fieldInterfaceMapper
				.getListShowFields(parameter);
		this.loadFieldHintList(fields);
		return fields;
	}

	public List<FieldInterface> getListShowFieldsByFrmType(String frmType) {
		List<FieldInterface> fields = fieldInterfaceMapper
				.getListShowFieldsByFrmType(frmType);
		this.loadFieldHintList(fields);
		return fields;
	}

	public Map<String, Object> getRowData(String tableName, Object rowId) {
		List<FieldInterface> fields = this.getFieldsByFrmType(tableName);
		FieldInterface idField = null;
		if (fields != null && !fields.isEmpty()) {
			for (FieldInterface field : fields) {
				if ("1".equals(field.getIsPrimaryKey())) {
					idField = field;
					break;
				}
			}
		}
		if (idField != null) {
			idField.setValue(rowId);
			TableEntity tableModel = new TableEntity();
			tableModel.setTableName(tableName);
			tableModel.setIdField(idField);
			tableModel.setFields(fields);
			return fieldInterfaceMapper.getRowDataById(tableModel);
		} else {
			throw new RuntimeException(tableName + " primary key not found");
		}
	}

	public int getRowDataCountByQueryCriteria(TableEntity tableModel) {
		return fieldInterfaceMapper.getRowDataCountByQueryCriteria(tableModel);
	}

	public List<Map<String, Object>> getRowDataListByQueryCriteria(int start,
			int pageSize, TableEntity tableModel) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Map<String, Object>> list = sqlSession.selectList(
				"getRowDataListByQueryCriteria", tableModel, rowBounds);
		return list;
	}

	public List<FieldInterface> list(FieldInterfaceQuery query) {
		List<FieldInterface> fields = fieldInterfaceMapper
				.getFieldInterfacesByQueryCriteria(query);
		this.loadFieldHintList(fields);
		return fields;
	}

	public void loadFieldHintList(List<FieldInterface> fields) {
		if (fields != null && !fields.isEmpty()) {
			for (FieldInterface f : fields) {
				if (f.getListweigth() <= 0) {
					f.setListweigth(120);
				}
				if (f.getListweigth() > 500) {
					f.setListweigth(500);
				}
				if (f.getListweigth() < 60) {
					f.setListweigth(60);
				}
				if (StringUtils.isEmpty(f.getAlign())) {
					if ("i4".equalsIgnoreCase(f.getDtype())
							|| "int".equalsIgnoreCase(f.getDtype())) {
						f.setAlign("right");
					} else if ("i8".equalsIgnoreCase(f.getDtype())
							|| "long".equalsIgnoreCase(f.getDtype())) {
						f.setAlign("right");
					} else if ("r8".equalsIgnoreCase(f.getDtype())
							|| "double".equalsIgnoreCase(f.getDtype())) {
						f.setAlign("right");
					} else if ("date".equalsIgnoreCase(f.getDtype())
							|| "datetime".equalsIgnoreCase(f.getDtype())) {
						f.setAlign("center");
					} else {
						f.setAlign("left");
					}
				}
				if (StringUtils.isNotEmpty(f.getHintID())) {

				}
			}
		}
	}

	@Transactional
	public void save(FieldInterface fieldInterface) {
		if (StringUtils.isEmpty(fieldInterface.getListId())) {
			fieldInterface.setListId(idGenerator.getNextId());
			// fieldInterface.setListId(idGenerator.getNextId());
			// fieldInterface.setCreateDate(new Date());
			fieldInterfaceMapper.insertFieldInterface(fieldInterface);
		} else {
			FieldInterface model = this.getFieldInterface(fieldInterface
					.getListId());
			if (model != null) {
				if (fieldInterface.getIndexId() != null) {
					model.setIndexId(fieldInterface.getIndexId());
				}
				if (fieldInterface.getFrmtype() != null) {
					model.setFrmtype(fieldInterface.getFrmtype());
				}
				if (fieldInterface.getIssystem() != null) {
					model.setIssystem(fieldInterface.getIssystem());
				}
				if (fieldInterface.getFname() != null) {
					model.setFname(fieldInterface.getFname());
				}
				if (fieldInterface.getDname() != null) {
					model.setDname(fieldInterface.getDname());
				}
				if (fieldInterface.getDtype() != null) {
					model.setDtype(fieldInterface.getDtype());
				}
				if (fieldInterface.getShowtype() != null) {
					model.setShowtype(fieldInterface.getShowtype());
				}
				model.setStrlen(fieldInterface.getStrlen());
				if (fieldInterface.getForm() != null) {
					model.setForm(fieldInterface.getForm());
				}
				if (fieldInterface.getIntype() != null) {
					model.setIntype(fieldInterface.getIntype());
				}
				if (fieldInterface.getHintID() != null) {
					model.setHintID(fieldInterface.getHintID());
				}
				model.setListno(fieldInterface.getListno());
				if (fieldInterface.getZtype() != null) {
					model.setZtype(fieldInterface.getZtype());
				}
				if (fieldInterface.getIsmustfill() != null) {
					model.setIsmustfill(fieldInterface.getIsmustfill());
				}
				if (fieldInterface.getIsListShow() != null) {
					model.setIsListShow(fieldInterface.getIsListShow());
				}
				model.setListweigth(fieldInterface.getListweigth());
				if (fieldInterface.getIsallwidth() != null) {
					model.setIsallwidth(fieldInterface.getIsallwidth());
				}
				if (fieldInterface.getIstname() != null) {
					model.setIstname(fieldInterface.getIstname());
				}
				model.setImportType(fieldInterface.getImportType());
				model.setDatapoint(fieldInterface.getDatapoint());
				fieldInterfaceMapper.updateFieldInterface(model);
			}
		}
	}

	@Override
	public void save(FieldInterface fieldInterface, String actorId) {
		if (StringUtils.isEmpty(fieldInterface.getListId())) {
			String id = idGenerator.getNextId("interface", "listId", actorId);
			fieldInterface.setListId(id);
			// fieldInterface.setListId(idGenerator.getNextId());
			// fieldInterface.setCreateDate(new Date());
			fieldInterfaceMapper.insertFieldInterface(fieldInterface);
		} else {
			fieldInterfaceMapper.updateFieldInterface(fieldInterface);
		}
	}

	@Transactional
	public void saveData(String tableName, Map<String, Object> dataMap) {
		List<FieldInterface> fields = this.getFieldsByFrmType(tableName);
		FieldInterface idField = null;
		if (fields != null && !fields.isEmpty()) {
			for (FieldInterface field : fields) {
				if ("1".equals(field.getIsPrimaryKey())) {
					idField = field;
					break;
				}
			}
		}
		if (idField != null) {
			TableModel tableModel = new TableModel();
			tableModel.setTableName(tableName);
			ColumnModel idColumn = new ColumnModel();
			idColumn.setColumnName(idField.getDname());

			if (StringUtils.equalsIgnoreCase(idField.getDtype(), "i4")) {
				idColumn.setJavaType("Integer");
			} else if (StringUtils.equalsIgnoreCase(idField.getDtype(), "int")) {
				idColumn.setJavaType("Integer");
			} else if (StringUtils.equalsIgnoreCase(idField.getDtype(),
					"integer")) {
				idColumn.setJavaType("Integer");
			} else if (StringUtils.equalsIgnoreCase(idField.getDtype(), "i8")) {
				idColumn.setJavaType("Long");
			} else if (StringUtils.equalsIgnoreCase(idField.getDtype(), "long")) {
				idColumn.setJavaType("Long");
			} else {
				idColumn.setJavaType("String");
			}
			tableModel.setIdColumn(idColumn);

			for (FieldInterface field : fields) {
				if (StringUtils.equalsIgnoreCase(field.getDname(),
						idField.getDname())) {
					continue;
				}
				logger.debug(field.getDname() + "->" + field.getDtype());
				ColumnModel column = new ColumnModel();
				column.setColumnName(field.getDname());
				if (StringUtils.equalsIgnoreCase(field.getDtype(), "i4")) {
					column.setJavaType("Integer");
					column.setValue(ParamUtils.getIntValue(dataMap,
							field.getDname()));
				} else if (StringUtils
						.equalsIgnoreCase(field.getDtype(), "int")) {
					column.setJavaType("Integer");
					column.setValue(ParamUtils.getIntValue(dataMap,
							field.getDname()));
				} else if (StringUtils.equalsIgnoreCase(field.getDtype(),
						"integer")) {
					column.setJavaType("Integer");
					column.setValue(ParamUtils.getIntValue(dataMap,
							field.getDname()));
				} else if (StringUtils.equalsIgnoreCase(field.getDtype(), "i8")) {
					column.setJavaType("Long");
					column.setValue(ParamUtils.getLongValue(dataMap,
							field.getDname()));
				} else if (StringUtils.equalsIgnoreCase(field.getDtype(),
						"long")) {
					column.setJavaType("Long");
					column.setValue(ParamUtils.getLongValue(dataMap,
							field.getDname()));
				} else if (StringUtils.equalsIgnoreCase(field.getDtype(),
						"float")) {
					column.setJavaType("Double");
					column.setDoubleValue(ParamUtils.getDoubleValue(dataMap,
							field.getDname()));
				} else if (StringUtils.equalsIgnoreCase(field.getDtype(),
						"double")) {
					column.setJavaType("Double");
					column.setValue(ParamUtils.getDoubleValue(dataMap,
							field.getDname()));
				} else if (StringUtils.equalsIgnoreCase(field.getDtype(), "r8")) {
					column.setJavaType("Double");
					column.setDoubleValue(ParamUtils.getDoubleValue(dataMap,
							field.getDname()));
				} else if (StringUtils.equalsIgnoreCase(field.getDtype(),
						"date")) {
					column.setJavaType("Date");
					column.setValue(ParamUtils.getDate(dataMap,
							field.getDname()));
				} else if (StringUtils.equalsIgnoreCase(field.getDtype(),
						"datetime")) {
					column.setJavaType("Date");
					column.setValue(ParamUtils.getDate(dataMap,
							field.getDname()));
					// logger.debug(field.getDname() + ":" + column.getValue());
				} else {
					column.setJavaType("String");
					column.setValue(ParamUtils.getString(dataMap,
							field.getDname()));
				}
				if (column.getValue() != null) {
					tableModel.addColumn(column);
					logger.debug(field.getDname() + ":" + column.getValue());
				}
			}
			Object rowId = ParamUtils.getObject(dataMap, idField.getDname());
			Map<String, Object> rowMap = this.getRowData(tableName, rowId);
			if (rowMap != null && !rowMap.isEmpty()) {
				idColumn.setValue(rowId);
				tableDataService.updateTableData(tableModel);
			} else {
				if (rowId == null) {
					if (StringUtils.equalsIgnoreCase(idColumn.getJavaType(),
							"Integer")) {
						idColumn.setValue(idGenerator.nextId(tableName)
								.intValue());
					} else if (StringUtils.equalsIgnoreCase(
							idColumn.getJavaType(), "Long")) {
						idColumn.setValue(idGenerator.nextId(tableName));
					} else {
						idColumn.setValue(idGenerator.getNextId(tableName));
					}
				}
				logger.debug("insert " + idColumn.getName() + "="
						+ idColumn.getValue());
				tableModel.addColumn(idColumn);
				tableModel.setIdColumn(idColumn);
				tableDataService.insertTableData(tableModel);
			}
		} else {
			throw new RuntimeException(tableName + " primary key not found");
		}
	}

	@Transactional
	public void saveDataList(String tableName,
			List<Map<String, Object>> dataList) {
		for (Map<String, Object> dataMap : dataList) {
			this.saveData(tableName, dataMap);
		}
	}

	@Override
	public FieldInterface getFieldInterface(String tableId, String fieldName) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("tableId", tableId);
		params.put("fieldName", fieldName);
		return fieldInterfaceMapper.getFieldInterface(params);
	}

	@Override
	public List<FieldInterface> getInterfacesByTableId(String frmType) {
		FieldInterfaceQuery query = new FieldInterfaceQuery();
		query.setFrmtype(frmType);
		query.setOrderBy("listno");
		return this.list(query);
	}

	@Override
	public List<FieldInterface> getInterfaceListByQuery(FieldInterfaceQuery query) {
		return fieldInterfaceMapper.getInterfaceListByQuery(query);
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setFieldInterfaceMapper(
			FieldInterfaceMapper fieldInterfaceMapper) {
		this.fieldInterfaceMapper = fieldInterfaceMapper;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@javax.annotation.Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

}
