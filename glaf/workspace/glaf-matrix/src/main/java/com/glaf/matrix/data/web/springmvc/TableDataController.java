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

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.BaseItem;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.DataFile;
import com.glaf.core.base.LowerLinkedMap;
import com.glaf.core.base.TableModel;
import com.glaf.core.db.TableDataManager;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.EntityService;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.service.ITablePageService;
import com.glaf.core.util.Constants;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.datamgr.parse.POIExcelParser;
import com.glaf.datamgr.parse.POIExcelXlsxParser;
import com.glaf.matrix.data.bean.TableDataBean;
import com.glaf.matrix.data.bean.TableExcelExportBean;
import com.glaf.matrix.data.domain.Comment;
import com.glaf.matrix.data.domain.SysTable;
import com.glaf.matrix.data.domain.TableColumn;
import com.glaf.matrix.data.domain.TableCorrelation;
import com.glaf.matrix.data.factory.DataFileFactory;
import com.glaf.matrix.data.factory.DataItemFactory;
import com.glaf.matrix.data.query.DataFileQuery;
import com.glaf.matrix.data.query.TableCorrelationQuery;
import com.glaf.matrix.data.service.ITableService;
import com.glaf.matrix.data.service.TableCorrelationService;

@Controller("/tableData")
@RequestMapping("/tableData")
public class TableDataController {

	protected static final Log logger = LogFactory.getLog(TableDataController.class);

	protected EntityService entityService;

	protected ITableDataService tableDataService;

	protected ITableService tableService;

	protected ITablePageService tablePageService;

	protected TableCorrelationService tableCorrelationService;

	@RequestMapping("/audit")
	public ModelAndView audit(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		RequestUtils.setRequestParameterToAttribute(request);
		request.setAttribute("canUpdate", false);
		String tableId = request.getParameter("tableId");
		if (StringUtils.isNotEmpty(tableId)) {
			SysTable tableDefinition = tableService.getSysTableById(tableId);
			if (tableDefinition != null) {
				TableDataBean tableDataBean = new TableDataBean();
				if (tableDataBean.hasPermission(loginContext, tableDefinition, Constants.PRIVILEGE_READ)) {
					List<TableColumn> columns = tableService.getTableColumnsByTableId(tableId);
					if (columns != null && !columns.isEmpty()) {
						Map<String, Object> rowMap = null;
						String uuid = request.getParameter("uuid");
						if (StringUtils.isNotEmpty(uuid)) {
							rowMap = tableDataBean.getRowMap(loginContext, tableDefinition, uuid);
							if (rowMap != null && !rowMap.isEmpty()) {
								boolean canUpdate = tableDataBean.canUpdate(loginContext, tableDefinition, uuid);
								request.setAttribute("canUpdate", canUpdate);
								request.setAttribute("status", 1);
							}
						} else {
							request.setAttribute("canUpdate", true);
						}

						LowerLinkedMap dataMap = new LowerLinkedMap();
						if (rowMap != null && !rowMap.isEmpty()) {
							dataMap.putAll(rowMap);
						}

						logger.debug("dataMap:" + dataMap);

						List<TableColumn> list = new ArrayList<TableColumn>();
						for (TableColumn column : columns) {
							column.setValue(dataMap.get(column.getColumnName().toLowerCase()));
							if (StringUtils.isNotEmpty(column.getDataCode())) {
								if (StringUtils.startsWith(column.getDataCode(), "@sys_dict:")) {
									long nodeId = Long.parseLong(
											column.getDataCode().substring(10, column.getDataCode().length()));
									List<BaseItem> items = DataItemFactory.getInstance().getDictoryItems(nodeId);
									column.setItems(items);
								} else if (StringUtils.startsWith(column.getDataCode(), "@table:")) {
									String rowId = column.getDataCode().substring(7, column.getDataCode().length());
									List<BaseItem> items = DataItemFactory.getInstance().getTableItems(loginContext,
											rowId, params);
									column.setItems(items);
								}
							}
							list.add(column);
						}

						request.setAttribute("columns", columns);
						request.setAttribute("table", tableDefinition);
						request.setAttribute("tableDefinition", tableDefinition);
					}
				}
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/matrix/tableData/audit", modelMap);
	}

	@RequestMapping("/auditdatalist")
	public ModelAndView auditdatalist(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug("params:" + params);
		String tableId = request.getParameter("tableId");
		if (StringUtils.isNotEmpty(tableId)) {
			SysTable tableDefinition = tableService.getSysTableById(tableId);
			if (tableDefinition != null) {
				List<TableColumn> columns = tableService.getTableColumnsByTableId(tableId);
				if (columns != null && !columns.isEmpty()) {
					List<TableColumn> list = new ArrayList<TableColumn>();
					for (TableColumn column : columns) {
						if (column.getDisplayType() == 2 || column.getDisplayType() == 4) {
							list.add(column);
						}
					}
					request.setAttribute("columns", columns);
					request.setAttribute("table", tableDefinition);
					request.setAttribute("tableDefinition", tableDefinition);
				}
			}

			TableCorrelationQuery query = new TableCorrelationQuery();
			query.masterTableId(tableId);
			List<TableCorrelation> list = tableCorrelationService.list(query);
			if (list != null && !list.isEmpty()) {
				List<SysTable> correlations = new ArrayList<SysTable>();
				for (TableCorrelation t : list) {
					SysTable table = tableService.getSysTableById(t.getSlaveTableId());
					table.setTableCorrelation(t);
					correlations.add(table);
				}
				request.setAttribute("correlations", correlations);
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/matrix/tableData/auditdatalist", modelMap);
	}

	@RequestMapping("/comments")
	public ModelAndView comments(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);
		String tableId = request.getParameter("tableId");
		long topId = RequestUtils.getLong(request, "topId");
		logger.debug("topId:" + topId);
		if (StringUtils.isNotEmpty(tableId)) {
			SysTable tableDefinition = tableService.getSysTableById(tableId);
			if (tableDefinition != null) {
				TableDataBean tableDataBean = new TableDataBean();
				if (tableDataBean.hasPermission(loginContext, tableDefinition, Constants.PRIVILEGE_READ)) {
					tableDefinition.setTableName(tableDefinition.getTableName() + "_AUDIT");
					List<Comment> comments = tableDataBean.getComments(loginContext, tableDefinition, topId);
					request.setAttribute("comments", comments);
				}
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/matrix/tableData/comments", modelMap);
	}

	@RequestMapping("/datalist")
	public ModelAndView datalist(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);
		request.setAttribute("canEdit", true);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug("params:" + params);
		String tableId = request.getParameter("tableId");
		if (StringUtils.isNotEmpty(tableId)) {
			SysTable tableDefinition = tableService.getSysTableById(tableId);
			if (tableDefinition != null) {
				List<TableColumn> columns = tableService.getTableColumnsByTableId(tableId);
				if (columns != null && !columns.isEmpty()) {
					List<TableColumn> list = new ArrayList<TableColumn>();
					for (TableColumn column : columns) {
						if (column.getDisplayType() == 2 || column.getDisplayType() == 4) {
							list.add(column);
						}
					}
					request.setAttribute("columns", columns);
					request.setAttribute("table", tableDefinition);
					request.setAttribute("tableDefinition", tableDefinition);
				}
				TableCorrelationQuery query = new TableCorrelationQuery();
				query.masterTableId(tableId);
				List<TableCorrelation> list = tableCorrelationService.list(query);
				if (list != null && !list.isEmpty()) {
					List<SysTable> correlations = new ArrayList<SysTable>();
					for (TableCorrelation t : list) {
						SysTable table = tableService.getSysTableById(t.getSlaveTableId());
						table.setTableCorrelation(t);
						correlations.add(table);
					}
					request.setAttribute("correlations", correlations);
				}
				query = new TableCorrelationQuery();
				query.slaveTableId(tableId);
				list = tableCorrelationService.list(query);
				if (list != null && !list.isEmpty()) {
					TableCorrelation tc = list.get(0);
					SysTable masterTable = tableService.getSysTableById(tc.getMasterTableId());
					long topId = RequestUtils.getLong(request, "topId");
					if (topId > 0) {
						TableDataBean tableDataBean = new TableDataBean();
						Map<String, Object> rowMap = tableDataBean.getRowMapById(loginContext, masterTable, topId);
						if (rowMap != null && !rowMap.isEmpty()) {
							LowerLinkedMap dataMap = new LowerLinkedMap();
							dataMap.putAll(rowMap);
							int business_status = ParamUtils.getInt(dataMap, "business_status_");
							if (business_status == 9) {
								request.setAttribute("canEdit", false);
							}
						}
					}
				}
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/matrix/tableData/datalist", modelMap);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		RequestUtils.setRequestParameterToAttribute(request);
		request.setAttribute("canUpdate", false);
		request.setAttribute("canEdit", true);
		request.setAttribute("status", 0);
		String tableId = request.getParameter("tableId");
		long topId = RequestUtils.getLong(request, "topId");
		if (StringUtils.isNotEmpty(tableId)) {
			SysTable tableDefinition = tableService.getSysTableById(tableId);
			if (tableDefinition != null) {
				TableDataBean tableDataBean = new TableDataBean();
				if (tableDataBean.hasPermission(loginContext, tableDefinition, Constants.PRIVILEGE_READ)) {
					List<TableColumn> columns = tableService.getTableColumnsByTableId(tableId);
					if (columns != null && !columns.isEmpty()) {
						Map<String, Object> rowMap = null;
						String uuid = request.getParameter("uuid");
						if (StringUtils.isNotEmpty(uuid)) {
							rowMap = tableDataBean.getRowMap(loginContext, tableDefinition, uuid);
							if (rowMap != null && !rowMap.isEmpty()) {
								boolean canUpdate = tableDataBean.canUpdate(loginContext, tableDefinition, uuid);
								int status = ParamUtils.getInt(rowMap, "business_status_");
								if (status == 9) {
									canUpdate = false;
								}
								request.setAttribute("status", 1);
								request.setAttribute("canUpdate", canUpdate);
								if (rowMap.get("topid_") != null) {
									topId = ParamUtils.getInt(rowMap, "topid_");
									logger.debug("topId:" + topId);
								}
							}
						} else {
							request.setAttribute("canUpdate", true);
						}

						if (topId > 0) {
							TableCorrelationQuery query = new TableCorrelationQuery();
							query.slaveTableId(tableId);
							List<TableCorrelation> list = tableCorrelationService.list(query);
							// logger.debug("list:" + list);
							if (list != null && !list.isEmpty()) {
								TableCorrelation tc = list.get(0);
								SysTable masterTable = tableService.getSysTableById(tc.getMasterTableId());
								Map<String, Object> rowMap2 = tableDataBean.getRowMapById(loginContext, masterTable,
										topId);
								if (rowMap2 != null && !rowMap2.isEmpty()) {
									// logger.debug("master data:" + rowMap2);
									int business_status = ParamUtils.getInt(rowMap2, "business_status_");
									if (business_status == 9) {
										request.setAttribute("canEdit", false);
										request.setAttribute("canUpdate", false);
									}
								}
							}
						}

						LowerLinkedMap dataMap = new LowerLinkedMap();
						if (rowMap != null && !rowMap.isEmpty()) {
							dataMap.putAll(rowMap);
						}

						logger.debug("dataMap:" + dataMap);

						List<TableColumn> list = new ArrayList<TableColumn>();
						for (TableColumn column : columns) {
							column.setValue(dataMap.get(column.getColumnName().toLowerCase()));
							if (StringUtils.isNotEmpty(column.getDataCode())) {
								if (StringUtils.startsWith(column.getDataCode(), "@sys_dict:")) {
									long nodeId = Long.parseLong(
											column.getDataCode().substring(10, column.getDataCode().length()));
									List<BaseItem> items = DataItemFactory.getInstance().getDictoryItems(nodeId);
									column.setItems(items);
								} else if (StringUtils.startsWith(column.getDataCode(), "@table:")) {
									String rowId = column.getDataCode().substring(7, column.getDataCode().length());
									List<BaseItem> items = DataItemFactory.getInstance().getTableItems(loginContext,
											rowId, params);
									column.setItems(items);
								}
							}
							list.add(column);
						}

						request.setAttribute("columns", columns);
						request.setAttribute("table", tableDefinition);
						request.setAttribute("tableDefinition", tableDefinition);
					}
				}
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/matrix/tableData/edit", modelMap);
	}

	@ResponseBody
	@RequestMapping("/exportXls")
	public void exportXls(HttpServletRequest request, HttpServletResponse response) {
		// LoginContext loginContext = RequestUtils.getLoginContext(request);
		// String systemName = Environment.getCurrentSystemName();
		ByteArrayOutputStream output = null;
		BufferedOutputStream bos = null;
		try {
			// EnvUtils.setEnv(loginContext, false);// 切换到读库
			request.setAttribute("exportXls", true);
			TableExcelExportBean exportBean = new TableExcelExportBean();
			// long databaseId = loginContext.getTenant().getDatabaseId();
			XSSFWorkbook wb = exportBean.export(request);
			output = new ByteArrayOutputStream();
			bos = new BufferedOutputStream(output);
			wb.write(bos);
			bos.flush();
			output.flush();
			byte[] bytes = output.toByteArray();
			ResponseUtils.download(request, response, bytes, "export" + DateUtils.getNowYearMonthDayHHmmss() + ".xlsx");
		} catch (Exception ex) {
			logger.error(ex);
		} finally {
			// Environment.setCurrentSystemName(systemName);
			IOUtils.closeQuietly(output);
			IOUtils.closeQuietly(bos);
		}
	}

	@ResponseBody
	@RequestMapping("/importXls")
	public byte[] importXls(HttpServletRequest request) throws IOException {
		logger.debug("-------------------------importXls----------------------");
		// LoginContext loginContext = RequestUtils.getLoginContext(request);
		JSONObject result = new JSONObject();
		// 将当前上下文初始化给 CommonsMutipartResolver（多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
			String type = req.getParameter("type");
			if (StringUtils.isEmpty(type)) {
				type = "0";
			}

			String tableId = req.getParameter("tableId");
			if (StringUtils.isEmpty(tableId)) {
				return result.toJSONString().getBytes();
			}

			Map<String, Object> paramMap = RequestUtils.getParameterMap(req);
			logger.debug("paramMap:" + paramMap);

			try {
				SysTable sysTable = tableService.getSysTableById(tableId);
				if (sysTable != null) {
					Map<String, MultipartFile> fileMap = req.getFileMap();
					Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
					for (Entry<String, MultipartFile> entry : entrySet) {
						MultipartFile mFile = entry.getValue();
						if (mFile.getOriginalFilename() != null && mFile.getSize() > 0) {
							String filename = mFile.getOriginalFilename();
							logger.debug("upload file:" + filename);
							TableModel metadata = new TableModel();
							metadata.setStartRow(sysTable.getStartRowIndex());
							metadata.setTableName(sysTable.getTableName());
							if (sysTable.getAggregationKeys() != null) {
								metadata.setAggregationKey(sysTable.getAggregationKeys().toLowerCase());
							}

							ColumnModel idColumn = new ColumnModel();
							idColumn.setName("id");
							idColumn.setColumnName("ID_");
							idColumn.setType("Long");
							idColumn.setJavaType("Long");
							metadata.setIdColumn(idColumn);

							List<TableColumn> columns = sysTable.getColumns();
							if (columns != null && !columns.isEmpty()) {
								for (TableColumn col : columns) {
									ColumnModel c = new ColumnModel();
									c.setColumnName(col.getColumnName());
									c.setName(col.getName());
									c.setType(col.getJavaType());
									c.setJavaType(col.getJavaType());
									c.setPosition(col.getColIndex());
									metadata.addColumn(c);
								}
							}

							List<TableModel> rows = null;
							if (StringUtils.endsWithIgnoreCase(filename, ".xls")) {
								POIExcelParser parser = new POIExcelParser();
								rows = parser.parse(metadata, mFile.getInputStream());
							} else if (StringUtils.endsWithIgnoreCase(filename, ".xlsx")) {
								POIExcelXlsxParser parser = new POIExcelXlsxParser();
								rows = parser.parse(metadata, mFile.getInputStream());
							}

							if (rows != null && !rows.isEmpty()) {
								TableDefinition table = new TableDefinition();
								table.setTableName(sysTable.getTableName());
								List<ColumnDefinition> xcolumns = DBUtils.getColumnDefinitions(sysTable.getTableName());
								for (ColumnDefinition colDef : xcolumns) {
									if (StringUtils.equals(colDef.getColumnName().toUpperCase(), "ID_")) {
										table.setIdColumn(colDef);
										xcolumns.remove(colDef);
										break;
									}
								}
								table.setColumns(xcolumns);
								if (sysTable.getAggregationKeys() != null) {
									table.setAggregationKeys(sysTable.getAggregationKeys().toLowerCase());
								}
								if (StringUtils.equals(sysTable.getInsertOnly(), "Y")) {
									table.setInsertOnly(true);
								}
								TableDataManager manager = new TableDataManager();
								String seqNo = String.valueOf(System.currentTimeMillis());
								manager.saveAll("default", table, seqNo, rows);
								return ResponseUtils.responseJsonResult(true, "数据已经成功导入！");
							}
						}
					}
				}
			} catch (Exception ex) {
				logger.debug(ex);
			}

		}
		// logger.debug("json:" + result.toJSONString());
		return result.toJSONString().getBytes("UTF-8");
	}

	@ResponseBody
	@RequestMapping("/json")
	public byte[] json(HttpServletRequest request) throws IOException {
		TableDataBean tableDataBean = new TableDataBean();
		JSONObject result = tableDataBean.toJson(request);
		return result.toJSONString().getBytes("UTF-8");
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

	@RequestMapping("/showImport")
	public ModelAndView showImport(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String tableId = request.getParameter("tableId");
		if (StringUtils.isNotEmpty(tableId)) {
			SysTable tableDefinition = tableService.getSysTableById(tableId);
			if (tableDefinition != null) {
				request.setAttribute("table", tableDefinition);
				request.setAttribute("tableDefinition", tableDefinition);
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/matrix/tableData/showImport", modelMap);
	}

	@RequestMapping("/showUpload")
	public ModelAndView showUpload(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		RequestUtils.setRequestParameterToAttribute(request);
		String tableId = request.getParameter("tableId");
		if (StringUtils.isEmpty(tableId)) {
			modelMap.put("error_message", "您没有提供必要的信息，tableId是必须的！");
			return new ModelAndView("/error");
		}
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug("paramMap:" + paramMap);
		String businessKey = request.getParameter("businessKey");
		int status = ParamUtils.getInt(paramMap, "status");
		try {
			List<DataFile> dataFiles = new ArrayList<DataFile>();
			SysTable tableDefinition = tableService.getSysTableById(tableId);
			if (tableDefinition != null) {
				if (StringUtils.equals(tableDefinition.getAttachmentFlag(), "1")
						|| StringUtils.equals(tableDefinition.getAttachmentFlag(), "2")) {
					if (StringUtils.isNotEmpty(businessKey)) {
						DataFileQuery query = new DataFileQuery();
						query.serviceKey(tableId);
						query.businessKey(businessKey);
						List<DataFile> rows = DataFileFactory.getInstance().getDataFileList(query);
						if (rows != null && rows.size() > 0) {
							dataFiles.addAll(rows);
						}
					}

					DataFileQuery query = new DataFileQuery();
					query.status(status);
					query.createBy(loginContext.getActorId());
					query.serviceKey(tableId);
					if (StringUtils.isNotEmpty(businessKey)) {
						query.businessKey(businessKey);
					} else {
						query.setBusinessKeyIsNull("true");
					}

					List<DataFile> rows = DataFileFactory.getInstance().getDataFileList(query);
					if (rows != null && rows.size() > 0) {
						Iterator<DataFile> iterator = rows.iterator();
						while (iterator.hasNext()) {
							DataFile dataFile = iterator.next();
							if (!dataFiles.contains(dataFile)) {
								if (StringUtils.isNotEmpty(businessKey)) {
									if (StringUtils.equals(dataFile.getBusinessKey(), businessKey)) {
										dataFiles.add(dataFile);
									}
								} else {
									if (StringUtils.isEmpty(businessKey)
											&& StringUtils.isEmpty(dataFile.getBusinessKey())) {
										dataFiles.add(dataFile);
									}
								}
							}
						}
					}

					if (dataFiles.size() > 0) {
						request.setAttribute("dataFiles", dataFiles);
					}
				}
				request.setAttribute("table", tableDefinition);
				request.setAttribute("tableDefinition", tableDefinition);

				Map<String, Object> rowMap = null;

				boolean canUpdate = false;

				if (StringUtils.isNotEmpty(businessKey)) {
					TableDataBean tableDataBean = new TableDataBean();
					rowMap = tableDataBean.getRowMap(loginContext, tableDefinition, businessKey);
					if (rowMap != null && !rowMap.isEmpty()) {
						canUpdate = tableDataBean.canUpdate(loginContext, tableDefinition, businessKey);
						logger.debug(loginContext.getActorId() + " canUpdate:" + canUpdate);
						int bstatus = ParamUtils.getInt(rowMap, "business_status_");
						if (bstatus == 9) {
							canUpdate = false;
						}
						request.setAttribute("canUpdate", canUpdate);
					}
				} else {
					canUpdate = true;
					request.setAttribute("canUpdate", true);
				}

				request.setAttribute("canUpload", false);
				if (StringUtils.equals(tableDefinition.getAttachmentFlag(), "1")) {
					if (dataFiles.size() == 0) {
						request.setAttribute("canUpload", true);
					}
				} else if (StringUtils.equals(tableDefinition.getAttachmentFlag(), "2")) {
					if (canUpdate) {
						request.setAttribute("canUpload", true);
					}
				}

				String audit = request.getParameter("audit");
				if (StringUtils.equals(audit, "true")) {
					request.setAttribute("canUpdate", false);
					request.setAttribute("canUpload", false);
				}
			}
		} catch (Exception ex) {
			logger.debug(ex);
			modelMap.put("error_message", "不能获取文件信息。");
			return new ModelAndView("/error");
		}

		String view = request.getParameter("view");
		if (StringUtils.isEmpty(view)) {
			view = "/matrix/tableData/showUpload";
		}

		return new ModelAndView(view, modelMap);
	}
}
