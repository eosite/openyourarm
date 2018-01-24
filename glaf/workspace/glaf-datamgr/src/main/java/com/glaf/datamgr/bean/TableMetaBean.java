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

package com.glaf.datamgr.bean;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.ITableDataService;

public class TableMetaBean {

	protected static final Log logger = LogFactory.getLog(TableMetaBean.class);

	protected IDatabaseService databaseService;

	protected ITableDataService tableDataService;

	public IDatabaseService getDatabaseService() {
		if (databaseService == null) {
			databaseService = ContextFactory.getBean("databaseService");
		}
		return databaseService;
	}

	public ITableDataService getTableDataService() {
		if (tableDataService == null) {
			tableDataService = ContextFactory.getBean("tableDataService");
		}
		return tableDataService;
	}

	public void saveTableMeta(String createBy, String systemName, String tableName, String tableNameCn,
			List<ColumnDefinition> columns) {
		if (columns != null && !columns.isEmpty()) {
			TableModel tableModel01 = new TableModel();
			tableModel01.setTableName("cell_data_table");

			ColumnModel idColumn01 = new ColumnModel();
			idColumn01.setColumnName("id");
			idColumn01.setJavaType("String");
			idColumn01.setValue(tableName);
			tableModel01.addColumn(idColumn01);
			tableModel01.setIdColumn(idColumn01);

			ColumnModel column02 = new ColumnModel();
			column02.setColumnName("tablename");
			column02.setJavaType("String");
			column02.setValue(tableName);
			tableModel01.addColumn(column02);

			ColumnModel column03 = new ColumnModel();
			column03.setColumnName("index_id");
			column03.setJavaType("Integer");
			column03.setValue(1);
			tableModel01.addColumn(column03);

			ColumnModel column04 = new ColumnModel();
			column04.setColumnName("name");
			column04.setJavaType("String");
			column04.setValue(tableNameCn);
			tableModel01.addColumn(column04);

			ColumnModel column05 = new ColumnModel();
			column05.setColumnName("addtype");
			column05.setJavaType("Integer");
			column05.setValue(3);
			tableModel01.addColumn(column05);

			ColumnModel column06 = new ColumnModel();
			column06.setColumnName("userid");
			column06.setJavaType("String");
			column06.setValue(createBy);
			tableModel01.addColumn(column06);

			ColumnModel column07 = new ColumnModel();
			column07.setColumnName("ctime");
			column07.setJavaType("Date");
			column07.setValue(new Date());
			tableModel01.addColumn(column07);

			ColumnModel column08 = new ColumnModel();
			column08.setColumnName("content");
			column08.setJavaType("String");
			column08.setValue(tableNameCn);
			tableModel01.addColumn(column08);

			ColumnModel column09 = new ColumnModel();
			column09.setColumnName("issubtable");
			column09.setJavaType("Integer");
			column09.setValue(0);
			tableModel01.addColumn(column09);

			if (systemName != null) {
				try {
					Environment.setCurrentSystemName(systemName);
					if (getTableDataService().getTableDataByPrimaryKey(tableModel01) == null) {
						getTableDataService().insertTableData(tableModel01);
					} else {
						getTableDataService().updateTableData(tableModel01);
					}
				} catch (Exception ex) {
					logger.error(ex);
				}
			}

			try {
				Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
				if (getTableDataService().getTableDataByPrimaryKey(tableModel01) == null) {
					getTableDataService().insertTableData(tableModel01);
				} else {
					getTableDataService().updateTableData(tableModel01);
				}
			} catch (Exception ex) {
				logger.error(ex);
			}

			int index = 0;
			for (ColumnDefinition column : columns) {
				if (StringUtils.equalsIgnoreCase(column.getColumnName(), "id")) {
					continue;
				}
				index++;
				TableModel tableModel02 = new TableModel();
				tableModel02.setTableName("cell_data_field");

				ColumnModel idColumn02 = new ColumnModel();
				idColumn02.setColumnName("id");
				idColumn02.setJavaType("String");
				idColumn02.setValue(tableName + "_" + index);
				tableModel02.addColumn(idColumn02);
				tableModel02.setIdColumn(idColumn02);

				ColumnModel tableid = new ColumnModel();
				tableid.setColumnName("tableid");
				tableid.setJavaType("String");
				tableid.setValue(tableName);
				tableModel02.addColumn(tableid);

				ColumnModel fieldname = new ColumnModel();
				fieldname.setColumnName("fieldname");
				fieldname.setJavaType("String");
				fieldname.setValue(column.getColumnName().toLowerCase());
				tableModel02.addColumn(fieldname);

				ColumnModel userid = new ColumnModel();
				userid.setColumnName("userid");
				userid.setJavaType("String");
				userid.setValue(createBy);
				tableModel02.addColumn(userid);

				ColumnModel maxuser = new ColumnModel();
				maxuser.setColumnName("maxuser");
				maxuser.setJavaType("Integer");
				maxuser.setValue(1);
				tableModel02.addColumn(maxuser);

				ColumnModel ctime = new ColumnModel();
				ctime.setColumnName("ctime");
				ctime.setJavaType("Date");
				ctime.setValue(new Date());
				tableModel02.addColumn(ctime);

				if (systemName != null) {
					try {
						Environment.setCurrentSystemName(systemName);
						if (getTableDataService().getTableDataByPrimaryKey(tableModel02) == null) {
							getTableDataService().insertTableData(tableModel02);
						} else {
							getTableDataService().updateTableData(tableModel02);
						}
					} catch (Exception ex) {
						logger.error(ex);
					}
				}

				try {
					Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
					if (getTableDataService().getTableDataByPrimaryKey(tableModel02) == null) {
						getTableDataService().insertTableData(tableModel02);
					} else {
						getTableDataService().updateTableData(tableModel02);
					}
				} catch (Exception ex) {
					logger.error(ex);
				}

				TableModel tableModel03 = new TableModel();
				tableModel03.setTableName("interface");

				ColumnModel idColumn03 = new ColumnModel();
				idColumn03.setColumnName("listId");
				idColumn03.setJavaType("String");
				idColumn03.setValue(tableName + "_" + index);
				tableModel03.addColumn(idColumn03);
				tableModel03.setIdColumn(idColumn03);

				ColumnModel frmtype = new ColumnModel();
				frmtype.setColumnName("frmtype");
				frmtype.setJavaType("String");
				frmtype.setValue(tableName);
				tableModel03.addColumn(frmtype);

				ColumnModel issystem = new ColumnModel();
				issystem.setColumnName("issystem");
				issystem.setJavaType("String");
				issystem.setValue("1");
				tableModel03.addColumn(issystem);

				ColumnModel fname = new ColumnModel();
				fname.setColumnName("fname");
				fname.setJavaType("String");
				fname.setValue(column.getTitle());
				tableModel03.addColumn(fname);

				ColumnModel dname = new ColumnModel();
				dname.setColumnName("dname");
				dname.setJavaType("String");
				dname.setValue(column.getColumnName().toLowerCase());
				tableModel03.addColumn(dname);

				ColumnModel ismustfill = new ColumnModel();
				ismustfill.setColumnName("ismustfill");
				ismustfill.setJavaType("String");
				ismustfill.setValue(column.isRequired() ? "1" : "0");
				tableModel03.addColumn(ismustfill);

				ColumnModel dtype = new ColumnModel();
				dtype.setColumnName("dtype");
				dtype.setJavaType("String");
				switch (column.getJavaType()) {
				case "Integer":
					dtype.setValue("i4");
					break;
				case "Long":
					dtype.setValue("r8");
					break;
				case "Double":
					dtype.setValue("r8");
					break;
				case "Date":
					dtype.setValue("datetime");
					break;
				default:
					dtype.setValue("string");
					break;
				}
				tableModel03.addColumn(dtype);

				ColumnModel strlen = new ColumnModel();
				strlen.setColumnName("strlen");
				strlen.setJavaType("Integer");
				strlen.setValue(column.getLength());
				tableModel03.addColumn(strlen);

				ColumnModel listno = new ColumnModel();
				listno.setColumnName("listno");
				listno.setJavaType("Integer");
				listno.setValue(index);
				tableModel03.addColumn(listno);

				if (systemName != null) {
					try {
						Environment.setCurrentSystemName(systemName);
						if (getTableDataService().getTableDataByPrimaryKey(tableModel03) == null) {
							getTableDataService().insertTableData(tableModel03);
						} else {
							getTableDataService().updateTableData(tableModel03);
						}
					} catch (Exception ex) {
						logger.error(ex);
					}
				}

				try {
					Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
					if (getTableDataService().getTableDataByPrimaryKey(tableModel03) == null) {
						getTableDataService().insertTableData(tableModel03);
					} else {
						getTableDataService().updateTableData(tableModel03);
					}
				} catch (Exception ex) {
					logger.error(ex);
				}
			}
		}
	}

	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

}
