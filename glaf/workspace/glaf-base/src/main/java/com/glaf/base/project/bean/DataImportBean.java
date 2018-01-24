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

package com.glaf.base.project.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.Database;
import com.glaf.core.identity.User;
import com.glaf.core.security.Authentication;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.ITableDataService;
import com.glaf.base.project.domain.Project;
import com.glaf.base.project.service.ProjectService;

public class DataImportBean {
	protected final static Log logger = LogFactory.getLog(DataImportBean.class);

	public void doImport(java.io.InputStream inputStream) {
		logger.debug("----------------DataImportBean------------------");
		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(inputStream);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (wb != null) {
				try {
					wb.close();
				} catch (IOException e) {
				}
			}
		}

		HSSFSheet sheet = wb.getSheetAt(0);
		this.importProjects(sheet);
		try {
			sheet = wb.getSheetAt(1);
			if (sheet != null) {
				this.importProjectUsers(sheet);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			sheet = wb.getSheetAt(2);
			if (sheet != null) {
				this.importDatabaseUsers(sheet);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public IDatabaseService getDatabaseService() {
		IDatabaseService databaseService = ContextFactory.getBean("databaseService");
		return databaseService;
	}

	public ProjectService getProjectService() {
		ProjectService projectService = ContextFactory.getBean("projectService");
		return projectService;
	}

	public ITableDataService getTableDataService() {
		ITableDataService tableDataService = ContextFactory.getBean("tableDataService");
		return tableDataService;
	}

	public void importDatabaseUsers(HSSFSheet sheet) {
		int rows = sheet.getLastRowNum();
		logger.debug("row num:" + rows);
		List<Map<String, Object>> users = new ArrayList<Map<String, Object>>();
		for (int rowIndex = 1; rowIndex <= rows; rowIndex++) {
			HSSFRow row = sheet.getRow(rowIndex);
			if (row == null) {
				continue;
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();
			int cells = row.getLastCellNum();
			for (int colIndex = 0; colIndex < cells; colIndex++) {
				HSSFCell cell = row.getCell(colIndex);
				if (cell != null) {
					String cellValue = null;
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_FORMULA:
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						cellValue = String.valueOf(cell.getNumericCellValue());
						if (cellValue.indexOf(".") != -1) {
							cellValue = cellValue.substring(0, cellValue.indexOf("."));
						}
						break;
					case HSSFCell.CELL_TYPE_STRING:
						cellValue = cell.getRichStringCellValue().getString();
						break;
					default:
						cellValue = cell.getStringCellValue();
						break;
					}
					if (cellValue == null) {
						cellValue = "";
					}
					cellValue = cellValue.trim();
					switch (colIndex) {
					case 0:
						dataMap.put("name", cellValue);
						break;
					case 1:
						dataMap.put("actorId", cellValue);
						break;
					case 3:
						dataMap.put("section", cellValue);
						break;
					default:
						break;
					}
				}
			}
			users.add(dataMap);
		}
		if (users != null && !users.isEmpty()) {
			IDatabaseService databaseService = this.getDatabaseService();
			ITableDataService tableDataService = this.getTableDataService();
			Map<String, List<String>> listMap = new HashMap<String, List<String>>();
			Map<String, User> userMap = IdentityFactory.getUserMap();
			for (Map<String, Object> rowMap : users) {
				if (userMap.get(rowMap.get("actorId")) == null) {
					TableModel table = new TableModel();
					table.setTableName("UserInfo");

					ColumnModel actorIdColumn = new ColumnModel();
					actorIdColumn.setColumnName("USERID");
					actorIdColumn.setJavaType("String");
					actorIdColumn.setValue(rowMap.get("actorId"));
					table.addColumn(actorIdColumn);

					ColumnModel nameColumn = new ColumnModel();
					nameColumn.setColumnName("USERNAME");
					nameColumn.setJavaType("String");
					nameColumn.setValue(rowMap.get("name"));
					table.addColumn(nameColumn);

					ColumnModel status = new ColumnModel();
					status.setColumnName("STATUS");
					status.setJavaType("String");
					status.setValue("0");
					table.addColumn(status);

					ColumnModel adminFlag = new ColumnModel();
					adminFlag.setColumnName("ISSYSTEM");
					adminFlag.setJavaType("String");
					adminFlag.setValue("0");
					table.addColumn(adminFlag);

					ColumnModel userType = new ColumnModel();
					userType.setColumnName("USERTYPE");
					userType.setJavaType("Integer");
					userType.setValue(0);
					table.addColumn(userType);

					tableDataService.insertTableData(table);
				}

				String section = (String) rowMap.get("section");
				String actorId = (String) rowMap.get("actorId");
				List<String> list = listMap.get(section);
				if (list == null) {
					list = new ArrayList<String>();
				}
				list.add(actorId);
				listMap.put(section, list);
			}

			Set<Entry<String, List<String>>> entrySet = listMap.entrySet();
			for (Entry<String, List<String>> entry : entrySet) {
				String key = entry.getKey();
				List<String> accessors = entry.getValue();
				Database database = databaseService.getDatabaseByMapping(key);
				if (database != null) {
					databaseService.saveAccessors(database.getId(), accessors);
				}
			}
		}
	}

	public void importProjects(HSSFSheet sheet) {
		int rows = sheet.getLastRowNum();
		logger.debug("row num:" + rows);
		List<Project> projects = new ArrayList<Project>();
		for (int rowIndex = 1; rowIndex <= rows; rowIndex++) {
			HSSFRow row = sheet.getRow(rowIndex);
			if (row == null) {
				continue;
			}
			Project project = new Project();
			int cells = row.getLastCellNum();
			for (int colIndex = 0; colIndex < cells; colIndex++) {
				HSSFCell cell = row.getCell(colIndex);
				if (cell != null) {
					String cellValue = null;
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_FORMULA:
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						cellValue = String.valueOf(cell.getNumericCellValue());
						if (cellValue.indexOf(".") != -1) {
							cellValue = cellValue.substring(0, cellValue.indexOf("."));
						}
						break;
					case HSSFCell.CELL_TYPE_STRING:
						cellValue = cell.getRichStringCellValue().getString();
						break;
					default:
						cellValue = cell.getStringCellValue();
						break;
					}
					if (cellValue == null) {
						cellValue = "";
					}
					cellValue = cellValue.trim();
					switch (colIndex) {
					case 0:
						project.setName(cellValue);
						project.setTitle(cellValue);
						break;
					case 1:
						project.setCode(cellValue);
						break;
					default:
						break;
					}
				}
			}
			projects.add(project);
		}

		if (projects != null && !projects.isEmpty()) {
			ProjectService projectService = this.getProjectService();
			for (Project project : projects) {
				if (projectService.getProjectByCode(project.getCode()) == null) {
					project.setActive("1");
					project.setCreateBy(Authentication.getAuthenticatedActorId());
					projectService.save(project);
				}
			}
		}
	}

	public void importProjectUsers(HSSFSheet sheet) {
		int rows = sheet.getLastRowNum();
		logger.debug("row num:" + rows);
		List<Map<String, Object>> users = new ArrayList<Map<String, Object>>();
		for (int rowIndex = 1; rowIndex <= rows; rowIndex++) {
			HSSFRow row = sheet.getRow(rowIndex);
			if (row == null) {
				continue;
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();
			int cells = row.getLastCellNum();
			for (int colIndex = 0; colIndex < cells; colIndex++) {
				HSSFCell cell = row.getCell(colIndex);
				if (cell != null) {
					String cellValue = null;
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_FORMULA:
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						cellValue = String.valueOf(cell.getNumericCellValue());
						if (cellValue.indexOf(".") != -1) {
							cellValue = cellValue.substring(0, cellValue.indexOf("."));
						}
						break;
					case HSSFCell.CELL_TYPE_STRING:
						cellValue = cell.getRichStringCellValue().getString();
						break;
					default:
						cellValue = cell.getStringCellValue();
						break;
					}
					if (cellValue == null) {
						cellValue = "";
					}
					cellValue = cellValue.trim();
					switch (colIndex) {
					case 0:
						dataMap.put("name", cellValue);
						break;
					case 1:
						dataMap.put("actorId", cellValue);
						break;
					case 2:
						dataMap.put("projectCode", cellValue);
						break;
					default:
						break;
					}
				}
			}
			users.add(dataMap);
		}
		if (users != null && !users.isEmpty()) {
			ProjectService projectService = this.getProjectService();
			Map<String, List<String>> listMap = new HashMap<String, List<String>>();
			for (Map<String, Object> rowMap : users) {
				String projectCode = (String) rowMap.get("projectCode");
				String actorId = (String) rowMap.get("actorId");
				List<String> list = listMap.get(projectCode);
				if (list == null) {
					list = new ArrayList<String>();
				}
				list.add(actorId);
				listMap.put(projectCode, list);
			}

			Set<Entry<String, List<String>>> entrySet = listMap.entrySet();
			for (Entry<String, List<String>> entry : entrySet) {
				String key = entry.getKey();
				List<String> accessors = entry.getValue();
				Project project = projectService.getProjectByCode(key);
				if (project != null) {
					projectService.saveAccessors(project.getId(), accessors);
				}
			}
		}
	}

}
