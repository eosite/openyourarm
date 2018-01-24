package com.glaf.datamgr.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.Database;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.ITableDataService;
import com.glaf.datamgr.domain.TreeTableSynthetic;
import com.glaf.datamgr.domain.TreeTableSyntheticRule;

public class TreeTableSyntheticMetaBean {
	protected static final Log logger = LogFactory.getLog(TreeTableSyntheticMetaBean.class);

	protected IDatabaseService databaseService;

	protected ITableDataService tableDataService;

	public IDatabaseService getDatabaseService() {
		if (databaseService == null) {
			databaseService = ContextFactory.getBean("databaseService");
		}
		return databaseService;
	}

	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	public ITableDataService getTableDataService() {
		if (tableDataService == null) {
			tableDataService = ContextFactory.getBean("tableDataService");
		}
		return tableDataService;
	}

	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

	public void updateTreeTableSyntheticMeta(TreeTableSynthetic tableSynthetic, List<TreeTableSyntheticRule> rules) {
		if (tableSynthetic != null && StringUtils.isNotEmpty(tableSynthetic.getTargetTableName())) {
			String systemName = null;
			Database targetDatabase = null;
			try {
				Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
				if (tableSynthetic.getTargetDatabaseId() > 0) {
					targetDatabase = getDatabaseService().getDatabaseById(tableSynthetic.getTargetDatabaseId());
					if (targetDatabase != null) {
						systemName = targetDatabase.getName();
					}
				}
				if (StringUtils.isNotEmpty(tableSynthetic.getTargetTableName())) {

					TableModel tableModel1 = new TableModel();
					tableModel1.setTableName("cell_data_table");

					ColumnModel idColumn = new ColumnModel();
					idColumn.setColumnName("id");
					idColumn.setJavaType("String");
					idColumn.setValue(tableSynthetic.getTargetTableName());
					tableModel1.addColumn(idColumn);
					try {
						Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
						getTableDataService().deleteTableData(tableModel1);
					} catch (Exception ex) {
					}
					try {
						if (StringUtils.isNotEmpty(systemName)) {
							Environment.setCurrentSystemName(systemName);
							getTableDataService().deleteTableData(tableModel1);
						}
					} catch (Exception ex) {
					}

					ColumnModel cm1 = new ColumnModel();
					cm1.setColumnName("tablename");
					cm1.setJavaType("String");
					cm1.setValue(tableSynthetic.getTargetTableName());
					tableModel1.addColumn(cm1);

					ColumnModel cm2 = new ColumnModel();
					cm2.setColumnName("index_id");
					cm2.setJavaType("Integer");
					cm2.setValue(1001);// 增加的分类编号
					tableModel1.addColumn(cm2);

					ColumnModel cm3 = new ColumnModel();
					cm3.setColumnName("name");
					cm3.setJavaType("String");
					cm3.setValue(tableSynthetic.getTitle());
					tableModel1.addColumn(cm3);

					ColumnModel cm4 = new ColumnModel();
					cm4.setColumnName("addtype");
					cm4.setJavaType("String");
					cm4.setValue("3");
					tableModel1.addColumn(cm4);

					ColumnModel cm5 = new ColumnModel();
					cm5.setColumnName("userid");
					cm5.setJavaType("String");
					cm5.setValue("system");
					tableModel1.addColumn(cm5);

					ColumnModel cm6 = new ColumnModel();
					cm6.setColumnName("ctime");
					cm6.setJavaType("Date");
					cm6.setValue(new Date());
					tableModel1.addColumn(cm6);

					ColumnModel cm7 = new ColumnModel();
					cm7.setColumnName("content");
					cm7.setJavaType("String");
					cm7.setValue(tableSynthetic.getTitle());
					tableModel1.addColumn(cm7);

					ColumnModel cm8 = new ColumnModel();
					cm8.setColumnName("issubtable");
					cm8.setJavaType("String");
					cm8.setValue("0");
					tableModel1.addColumn(cm8);

					try {
						Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
						getTableDataService().insertTableData(tableModel1);
					} catch (Exception ex) {
					}

					try {
						if (StringUtils.isNotEmpty(systemName)) {
							Environment.setCurrentSystemName(systemName);
							getTableDataService().insertTableData(tableModel1);
						}
					} catch (Exception ex) {
					}

					if (rules != null && !rules.isEmpty()) {

						List<TableModel> rows = new ArrayList<TableModel>();

						for (TreeTableSyntheticRule rule : rules) {
							TableModel tableModel3 = new TableModel();
							tableModel3.setTableName("interface");

							ColumnModel cm30 = new ColumnModel();
							cm30.setColumnName("index_id");
							cm30.setJavaType("String");
							cm30.setValue("1001");
							tableModel3.addColumn(cm30);

							ColumnModel cm31 = new ColumnModel();
							cm31.setColumnName("frmtype");
							cm31.setJavaType("String");
							cm31.setValue(tableSynthetic.getTargetTableName());
							tableModel3.addColumn(cm31);

							ColumnModel cm32 = new ColumnModel();
							cm32.setColumnName("listId");
							cm32.setJavaType("String");
							cm32.setValue(tableSynthetic.getTargetTableName() + "_" + rule.getId());
							tableModel3.addColumn(cm32);

							ColumnModel cm33 = new ColumnModel();
							cm33.setColumnName("issystem");
							cm33.setJavaType("String");
							cm33.setValue("1");
							tableModel3.addColumn(cm33);

							ColumnModel cm34 = new ColumnModel();
							cm34.setColumnName("fname");
							cm34.setJavaType("String");
							cm34.setValue(rule.getColumnTitle());
							tableModel3.addColumn(cm34);

							ColumnModel cm35 = new ColumnModel();
							cm35.setColumnName("dname");
							cm35.setJavaType("String");
							cm35.setValue(rule.getColumnName());
							tableModel3.addColumn(cm35);

							ColumnModel cm36 = new ColumnModel();
							cm36.setColumnName("dtype");
							cm36.setJavaType("String");

							ColumnModel cm37 = new ColumnModel();
							cm37.setColumnName("strlen");
							cm37.setJavaType("Integer");

							ColumnModel cm38 = new ColumnModel();
							cm38.setColumnName("intype");
							cm38.setJavaType("String");

							if ("Date".equals(rule.getType())) {
								cm36.setValue("datetime");
								cm37.setValue(-1);
								cm38.setValue("dp");
							} else if ("Integer".equals(rule.getType())) {
								cm36.setValue("i4");
								cm37.setValue(-1);
								cm38.setValue("edt");
							} else if ("Long".equals(rule.getType())) {
								cm36.setValue("r8");
								cm37.setValue(-1);
								cm38.setValue("edt");
							} else if ("Double".equals(rule.getType())) {
								cm36.setValue("r8");
								cm37.setValue(-1);
								cm38.setValue("edt");
							} else {
								cm36.setValue("string");
								cm37.setValue(500);
								cm38.setValue("edt");
							}
							tableModel3.addColumn(cm36);
							tableModel3.addColumn(cm37);
							tableModel3.addColumn(cm38);

							ColumnModel cm39 = new ColumnModel();
							cm39.setColumnName("listno");
							cm39.setJavaType("Integer");
							cm39.setValue((int) rule.getId());
							tableModel3.addColumn(cm39);

							ColumnModel cm40 = new ColumnModel();
							cm40.setColumnName("isListShow");
							cm40.setJavaType("String");
							cm40.setValue("1");
							tableModel3.addColumn(cm40);

							rows.add(tableModel3);

							TableModel tableModel4 = new TableModel();
							tableModel4.setTableName("cell_data_field");

							ColumnModel cm42 = new ColumnModel();
							cm42.setColumnName("tableid");
							cm42.setJavaType("String");
							cm42.setValue(tableSynthetic.getTargetTableName());
							tableModel4.addColumn(cm42);

							ColumnModel cm41 = new ColumnModel();
							cm41.setColumnName("id");
							cm41.setJavaType("String");
							cm41.setValue(tableSynthetic.getTargetTableName() + "_" + rule.getId());
							tableModel4.addColumn(cm41);

							ColumnModel cm43 = new ColumnModel();
							cm43.setColumnName("fieldname");
							cm43.setJavaType("String");
							cm43.setValue(rule.getColumnName());
							tableModel4.addColumn(cm43);

							ColumnModel cm44 = new ColumnModel();
							cm44.setColumnName("userid");
							cm44.setJavaType("String");
							cm44.setValue("system");
							tableModel4.addColumn(cm44);

							ColumnModel cm45 = new ColumnModel();
							cm45.setColumnName("maxuser");
							cm45.setJavaType("Integer");
							cm45.setValue((int) rule.getId());
							tableModel4.addColumn(cm45);

							ColumnModel cm46 = new ColumnModel();
							cm46.setColumnName("ctime");
							cm46.setJavaType("Date");
							cm46.setValue(new Date());
							tableModel4.addColumn(cm46);

							rows.add(tableModel4);

						}

						TableModel tableModel5 = new TableModel();
						tableModel5.setTableName("interface");

						ColumnModel cm51 = new ColumnModel();
						cm51.setColumnName("frmtype");
						cm51.setJavaType("String");
						cm51.setValue(tableSynthetic.getTargetTableName());
						tableModel5.addColumn(cm51);

						try {
							Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
							getTableDataService().deleteTableData(tableModel5);
						} catch (Exception ex) {
						}

						try {
							if (StringUtils.isNotEmpty(systemName)) {
								Environment.setCurrentSystemName(systemName);
								getTableDataService().deleteTableData(tableModel5);
							}
						} catch (Exception ex) {
						}

						TableModel tableModel6 = new TableModel();
						tableModel6.setTableName("cell_data_field");

						ColumnModel cm62 = new ColumnModel();
						cm62.setColumnName("tableid");
						cm62.setJavaType("String");
						cm62.setValue(tableSynthetic.getTargetTableName());
						tableModel6.addColumn(cm62);

						try {
							Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
							getTableDataService().deleteTableData(tableModel6);
						} catch (Exception ex) {
						}

						try {
							if (StringUtils.isNotEmpty(systemName)) {
								Environment.setCurrentSystemName(systemName);
								getTableDataService().deleteTableData(tableModel6);
							}
						} catch (Exception ex) {
						}

						try {
							Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
							getTableDataService().insertAllTableData(rows);
						} catch (Exception ex) {
						}

						try {
							if (StringUtils.isNotEmpty(systemName)) {
								Environment.setCurrentSystemName(systemName);
								getTableDataService().insertAllTableData(rows);
							}
						} catch (Exception ex) {
						}
					}
				}
			} catch (Exception ex) {
				logger.error(ex);
				throw new RuntimeException(ex);
			}
		}
	}

}