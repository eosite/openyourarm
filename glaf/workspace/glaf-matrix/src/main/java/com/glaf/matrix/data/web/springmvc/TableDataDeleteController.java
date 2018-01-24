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

package com.glaf.matrix.data.web.springmvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.base.LowerLinkedMap;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.EntityService;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.service.ITablePageService;
import com.glaf.core.util.Constants;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.matrix.data.bean.TableDataBean;
import com.glaf.matrix.data.domain.SysTable;
import com.glaf.matrix.data.domain.TableColumn;
import com.glaf.matrix.data.domain.TableCorrelation;
import com.glaf.matrix.data.query.TableCorrelationQuery;
import com.glaf.matrix.data.service.ITableService;
import com.glaf.matrix.data.service.TableCorrelationService;

@Controller("/tableDataDelete")
@RequestMapping("/tableDataDelete")
public class TableDataDeleteController {

	protected static final Log logger = LogFactory.getLog(TableDataDeleteController.class);

	protected EntityService entityService;

	protected ITableDataService tableDataService;

	protected ITableService tableService;

	protected ITablePageService tablePageService;

	protected TableCorrelationService tableCorrelationService;

	@ResponseBody
	@RequestMapping("/deleteById")
	public byte[] deleteById(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		RequestUtils.setRequestParameterToAttribute(request);
		String tableId = request.getParameter("tableId");
		if (StringUtils.isNotEmpty(tableId)) {
			SysTable tableDefinition = tableService.getSysTableById(tableId);
			if (tableDefinition != null) {
				TableDataBean tableDataBean = new TableDataBean();
				if (!tableDataBean.hasPermission(loginContext, tableDefinition, Constants.PRIVILEGE_READ_WRITE)) {
					return ResponseUtils.responseJsonResult(false, "您没有操作权限，请联系您的管理员授权。");
				}
				TableCorrelationQuery query = new TableCorrelationQuery();
				query.masterTableId(tableId);
				List<TableCorrelation> list = tableCorrelationService.list(query);
				List<TableColumn> columns = tableService.getTableColumnsByTableId(tableId);
				if (columns != null && !columns.isEmpty()) {
					Map<String, Object> rowMap = null;
					String uuid = request.getParameter("uuid");
					if (StringUtils.isNotEmpty(uuid)) {
						rowMap = tableDataBean.getRowMap(loginContext, tableDefinition, uuid);
					}

					LowerLinkedMap dataMap = new LowerLinkedMap();
					if (rowMap != null && !rowMap.isEmpty()) {
						dataMap.putAll(rowMap);
						boolean canDelete = false;

						if (loginContext.isSystemAdministrator()) {
							canDelete = true;
						} else {
							if (StringUtils.equals(loginContext.getActorId(),
									ParamUtils.getString(dataMap, "createby_"))) {
								canDelete = true;
							}
							if (loginContext.getRoles() != null && loginContext.getRoles().contains("OrgAdmin")) {
								if (loginContext.getUser().getDeptId() == ParamUtils.getLong(dataMap,
										"organizationid_")) {
									canDelete = true;
								}
							}
						}

						if (canDelete && list != null && !list.isEmpty()) {
							/**
							 * 检查是否存在从表，并检查是否可以删除从表数据
							 */
							for (TableCorrelation t : list) {
								/**
								 * 先删除子表才能删除主表的情况，必须检查子表是否存在记录，存在不允许删除，不存在才可以删除。
								 */
								if (StringUtils.equals(t.getDeleteCascade(), "M")) {
									canDelete = false;
									StringBuilder sqlBuffer = new StringBuilder();
									params = new HashMap<String, Object>();
									params.put("topId", ParamUtils.getLong(dataMap, "id_"));
									sqlBuffer.append(" select E.* from ").append(t.getSlaveTableName()).append(" E ");
									sqlBuffer.append(" where 1=1 ");
									sqlBuffer.append(" and E.TOPID_ = #{topId} ");
									rowMap = tablePageService.getOne(sqlBuffer.toString(), params);
									if (rowMap == null || rowMap.isEmpty()) {
										canDelete = true;
									} else {
										return ResponseUtils.responseJsonResult(false, "请先删除从表记录再删除主表记录。");
									}
								} else if (StringUtils.equals(t.getDeleteCascade(), "N")) {
									canDelete = false;
									return ResponseUtils.responseJsonResult(false, "从表记录已经存在记录且不允许删除。");
								}
							}
						}

						if (canDelete) {
							List<TableModel> rows = new ArrayList<TableModel>();

							if (tableDefinition.getDeleteCascade() == 0) {
								if (list != null && !list.isEmpty()) {
									/**
									 * 检查是否存在从表，并检查是否可以删除从表数据
									 */
									for (TableCorrelation t : list) {
										/**
										 * 与主表一同删除
										 */
										if (StringUtils.equals(t.getDeleteCascade(), "S")) {
											TableModel tableModel = new TableModel();
											tableModel.setTableName(t.getSlaveTableName());
											ColumnModel topIdColumn = new ColumnModel();
											topIdColumn.setColumnName("TOPID_");
											topIdColumn.setValue(ParamUtils.getLong(dataMap, "id_"));
											tableModel.setIdColumn(topIdColumn);
											tableModel.addColumn(topIdColumn);
											rows.add(tableModel);
										}
									}
								}

								TableModel tableModel = new TableModel();
								tableModel.setTableName(tableDefinition.getTableName());
								ColumnModel idColumn = new ColumnModel();
								idColumn.setColumnName("ID_");
								idColumn.setValue(ParamUtils.getLong(dataMap, "id_"));
								tableModel.setIdColumn(idColumn);
								tableModel.addColumn(idColumn);
								rows.add(tableModel);
								tableDataService.deleteTableDataList(rows);

								/**
								 * 判断是否存在附件，如果有附件，删除附件表的内容
								 */

							} else if (tableDefinition.getDeleteCascade() == 1) {
								if (list != null && !list.isEmpty()) {
									/**
									 * 检查是否存在从表，并检查是否可以删除从表数据
									 */
									for (TableCorrelation t : list) {
										/**
										 * 与主表一同删除
										 */
										if (StringUtils.equals(t.getDeleteCascade(), "S")) {
											TableModel tableModel = new TableModel();
											tableModel.setTableName(t.getSlaveTableName());
											ColumnModel topIdColumn = new ColumnModel();
											topIdColumn.setColumnName("TOPID_");
											topIdColumn.setValue(ParamUtils.getLong(dataMap, "id_"));
											topIdColumn.setJavaType("Long");
											tableModel.setIdColumn(topIdColumn);

											ColumnModel deleteFlagColumn = new ColumnModel();
											deleteFlagColumn.setColumnName("DELETEFLAG_");
											deleteFlagColumn.setValue(1);
											tableModel.addColumn(deleteFlagColumn);

											ColumnModel deleteTimeColumn = new ColumnModel();
											deleteTimeColumn.setColumnName("DELETETIME_");
											deleteTimeColumn.setValue(new java.util.Date());
											deleteTimeColumn.setJavaType("Date");
											tableModel.addColumn(deleteTimeColumn);

											rows.add(tableModel);
										}
									}
								}

								TableModel tableModel = new TableModel();
								tableModel.setTableName(tableDefinition.getTableName());
								ColumnModel idColumn = new ColumnModel();
								idColumn.setColumnName("ID_");
								idColumn.setValue(ParamUtils.getLong(dataMap, "id_"));
								idColumn.setJavaType("Long");
								tableModel.setIdColumn(idColumn);

								ColumnModel deleteFlagColumn = new ColumnModel();
								deleteFlagColumn.setColumnName("DELETEFLAG_");
								deleteFlagColumn.setValue(1);
								tableModel.addColumn(deleteFlagColumn);

								ColumnModel deleteTimeColumn = new ColumnModel();
								deleteTimeColumn.setColumnName("DELETETIME_");
								deleteTimeColumn.setValue(new java.util.Date());
								deleteTimeColumn.setJavaType("Date");
								tableModel.addColumn(deleteTimeColumn);
								rows.add(tableModel);
								/**
								 * 更新记录，对记录打删除标记
								 */
								tableDataService.updateAllTableData(rows);

								/**
								 * 判断是否存在附件，如果有附件， 标识附件表的内容
								 */

							}

							return ResponseUtils.responseResult(true);
						}
					}
				}
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@javax.annotation.Resource
	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}

	@javax.annotation.Resource
	public void setTableCorrelationService(TableCorrelationService tableCorrelationService) {
		this.tableCorrelationService = tableCorrelationService;
	}

	@javax.annotation.Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

	@javax.annotation.Resource
	public void setTablePageService(ITablePageService tablePageService) {
		this.tablePageService = tablePageService;
	}

	@javax.annotation.Resource
	public void setTableService(ITableService tableService) {
		this.tableService = tableService;
	}
}
