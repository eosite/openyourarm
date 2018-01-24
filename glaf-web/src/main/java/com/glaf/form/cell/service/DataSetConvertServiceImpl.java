package com.glaf.form.cell.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.convert.domain.ConvertPageTmpl;
import com.glaf.core.util.JdbcUtils;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.DataSetAudit;
import com.glaf.datamgr.util.DataSetJsonFactory;
import com.glaf.form.core.domain.FormRule;
import com.glaf.form.core.query.FormRuleQuery;

@Service("dataSetConvertService")
public class DataSetConvertServiceImpl extends CellConvertServiceImpl implements DataSetConvertService {

	public DataSetConvertServiceImpl() {
		sqlProperties = super.getCellPropertiesByName("dataset.sql.properties");
	}

	/**
	 * 转换 数据规则 单条转换
	 */
	public boolean convert(String cvtId) {
		logger.debug("-------------------start convert-------------------");
		String sql = super.getProperty("cell.datasetSingle.sql");// 要运行的SQL语句
		ResultSet rSet = null;
		Connection connection = null;
		PreparedStatement psmt = null;
		try {
			connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());

			psmt = connection.prepareStatement(sql.toString());
			psmt.setObject(1, Long.parseLong(cvtId));

			rSet = psmt.executeQuery();

			if (rSet != null && rSet.next()) {
				Map<String, Map<String, TableMsg>> map = new HashMap<String, Map<String, TableMsg>>();
				Map<String, TableMsg> tableMap;
				TableMsg tableMsg;
				do {
					String FILEDOT_FIELD_ID_ = rSet.getString("FILEDOT_FIELD_ID_");
					String DATA_TABLE_ID_ = rSet.getString("DATA_TABLE_ID_");
					String TABLE_NAME_ = rSet.getString("TABLE_NAME_");
					String FIELD_NAME_ = rSet.getString("FIELD_NAME_");
					String ELEM_ID_ = rSet.getString("ELEM_ID_");
					String ELEM_NAME_ = rSet.getString("ELEM_NAME_");
					int SUB_TABLE_ = rSet.getInt("SUB_TABLE_");
					if (!map.containsKey(FILEDOT_FIELD_ID_))
						map.put(FILEDOT_FIELD_ID_, new HashMap<String, TableMsg>());
					tableMap = map.get(FILEDOT_FIELD_ID_);
					if (!tableMap.containsKey(TABLE_NAME_))
						tableMap.put(TABLE_NAME_, new TableMsg());
					tableMsg = tableMap.get(TABLE_NAME_);
					tableMsg.setSubTable(SUB_TABLE_);
					tableMsg.setTableId(DATA_TABLE_ID_);
					tableMsg.setName(rSet.getString("CVT_SRC_FILENAME_"));
					tableMsg.setCvtId(rSet.getString("CVT_ID_"));

					tableMsg.addColumnModel(FIELD_NAME_, ELEM_ID_, ELEM_NAME_);
				} while (rSet.next());
				this.buildDataSet(map, connection);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("run batch error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(rSet);
			JdbcUtils.close(psmt);
			JdbcUtils.close(connection);
		}
		logger.debug("-------------------end convert-------------------");
		return false;

	}

	/**
	 * 转换 数据规则
	 */
	@Override
	public boolean convert() {

		logger.debug("-------------------start convert-------------------");

		String sql = super.getProperty("cell.dataset.sql");// 要运行的SQL语句

		ResultSet rSet = null;
		Connection connection = null;
		PreparedStatement psmt = null;
		try {
			connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
			psmt = connection.prepareStatement(sql.toString());

			rSet = psmt.executeQuery();

			if (rSet != null && rSet.next()) {
				Map<String, Map<String, TableMsg>> map = new HashMap<String, Map<String, TableMsg>>();
				Map<String, TableMsg> tableMap;
				TableMsg tableMsg;
				do {
					String FILEDOT_FIELD_ID_ = rSet.getString("FILEDOT_FIELD_ID_");
					String DATA_TABLE_ID_ = rSet.getString("DATA_TABLE_ID_");
					String TABLE_NAME_ = rSet.getString("TABLE_NAME_");
					String FIELD_NAME_ = rSet.getString("FIELD_NAME_");
					String ELEM_ID_ = rSet.getString("ELEM_ID_");
					String ELEM_NAME_ = rSet.getString("ELEM_NAME_");
					int SUB_TABLE_ = rSet.getInt("SUB_TABLE_");
					if (!map.containsKey(FILEDOT_FIELD_ID_))
						map.put(FILEDOT_FIELD_ID_, new HashMap<String, TableMsg>());
					tableMap = map.get(FILEDOT_FIELD_ID_);
					if (!tableMap.containsKey(TABLE_NAME_))
						tableMap.put(TABLE_NAME_, new TableMsg());
					tableMsg = tableMap.get(TABLE_NAME_);
					tableMsg.setSubTable(SUB_TABLE_);
					tableMsg.setTableId(DATA_TABLE_ID_);
					tableMsg.setName(rSet.getString("CVT_SRC_FILENAME_"));
					tableMsg.setCvtId(rSet.getString("CVT_ID_"));

					tableMsg.addColumnModel(FIELD_NAME_, ELEM_ID_, ELEM_NAME_);
				} while (rSet.next());
				this.buildDataSet(map, connection);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("run batch error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(rSet);
			JdbcUtils.close(psmt);
			JdbcUtils.close(connection);
		}
		logger.debug("-------------------end convert-------------------");
		return false;
	}

	/**
	 * 生成数据集
	 * 
	 * @param map
	 * @param conn
	 * @throws SQLException
	 */
	public void buildDataSet(Map<String, Map<String, TableMsg>> map, Connection conn) throws SQLException {

		if (map != null && map.size() > 0) {
			String sql = super.getProperty("dataset.field.sql");
			ResultSet rSet = null;
			PreparedStatement psmt = null;
			JSONObject button, inParameters, paraType, datas, pageColumn, pageDataSet, selectJson, fromJson,
					dataSource = new JSONObject() {
						private static final long serialVersionUID = 1L;

						{
							put("text", "默认");
							put("code", "default");
						}
					}, dataSetJson;

			String position[] = new String[] { "415,125", "867,125" };
			Map<String, Map<String, Map<String, String>>> componentMap = getComponentMap(conn);
			for (String key : map.keySet()) {
				String topTable = null;
				Map<String, TableMsg> msg = map.get(key);
				JSONArray pageColumns = new JSONArray(), selectSegments = new JSONArray(),
						fromSegments = new JSONArray(), linkageControl = new JSONArray();
				int tableIndex = 0;
				dataSetJson = new JSONObject();
				dataSetJson.put("selectSegments", selectSegments);
				dataSetJson.put("fromSegments", fromSegments);
				final Map<String, String> table = new HashMap<String, String>();
				dataSetJson.put("systemName", "default");

				pageDataSet = new JSONObject(); // 页面数据源
				pageDataSet.put("tables", msg.keySet());
				pageDataSet.put("columns", pageColumns);

				paraType = new JSONObject(); // 页面输入输出参数
				datas = new JSONObject(); // 页面输入输出参数
				paraType.put("datas", datas);
				paraType.put("name", "输入输出关系");

				inParameters = new JSONObject(); // 页面输入表达式定义

				button = new JSONObject();// 页面提交按钮
				JSONArray saveSourceSets = new JSONArray();
				for (String tblKey : msg.keySet()) {

					JSONArray columns = new JSONArray();
					JSONObject saveSourceSet = new JSONObject();
					JSONObject saveSourceSetTable = new JSONObject();// 页面提交按钮
					linkageControl = new JSONArray();
					saveSourceSet.put("linkageControl", linkageControl);
					saveSourceSet.put("columns", columns);
					saveSourceSet.put("index", 0);
					saveSourceSet.put("table", saveSourceSetTable);
					saveSourceSets.add(saveSourceSet);

					TableMsg tableMsg = msg.get(tblKey);
					Map<String, Map<String, String>> columnMap = tableMsg.getColumnMap();

					dataSetJson.put("title", "cell " + tableMsg.getName() + " 系统生成");
					dataSetJson.put("name", dataSetJson.get("title"));

					fromJson = new JSONObject();
					if (tableMsg.getSubTable() == 0) {// 主表
						table.put("sourceTable", tblKey);
						fromJson.put("position", position[0]);

						button.put("cvtId", tableMsg.getCvtId());

						topTable = tblKey;
					} else {
						table.put("targetTable", tblKey);
						fromJson.put("position", position[1]);

						saveSourceSetTable.put("batch", true);
						saveSourceSet.put("cell", true);
						saveSourceSetTable.put("batchRules", saveSourceSet);

					}

					saveSourceSet.put("name", dataSetJson.get("title"));
					saveSourceSetTable.put("isMaster", tableMsg.getSubTable() == 0);
					saveSourceSetTable.put("tableName", tblKey);
					saveSourceSetTable.put("name", tableMsg.getName());

					tableMsg.setCvtId(prefix + tableMsg.getCvtId());

					dataSetJson.put("id", tableMsg.getCvtId());

					psmt = conn.prepareStatement(sql);
					psmt.setString(1, tableMsg.getTableId());
					rSet = psmt.executeQuery();

					fromJson.put("tableName", tblKey);
					fromJson.put("tableId", tableMsg.getTableId());
					fromJson.put("dataSource", dataSource.clone());

					fromJson.put("ordinal", tableIndex++);
					fromJson.put("tableType", 2);

					fromSegments.add(fromJson);
					if (rSet != null && rSet.next()) {
						int index = 0;
						do {

							final String dname = this.getString(rSet, "dname");
							final String fname = this.getString(rSet, "fname");
							final String fieldId = rSet.getString("fieldid");
							final String tableNameCN = this.getString(rSet, "tableNameCN");
							final int strlen = rSet.getInt("strlen");
							final String dType = rSet.getString("dtype");
							if (tableMsg.getSubTable() == 0 && inParameters.isEmpty()) { // 输入表达式定义
								String name = "~F{default." + tableNameCN + ".id} 等于 ~F{id 参数},";

								final String fieldData = "{\"expVal\":\"~F{default." + tableNameCN
										+ ".id}\",\"expActVal\":\"~F{default." + tblKey
										+ ".id}\",\"varVal\":[{\"key\":\"~F{default." + tableNameCN
										+ ".id}\",\"value\":{\"t\":\"id\",\"dType\":\"string\",\"code\":\"~F{default."
										+ tblKey + ".id}\",\"value\":\"~F{default." + tableNameCN + ".id}\",\"name\":\""
										+ tableNameCN
										+ ".id\",\"pId\":2,\"level\":1,\"tId\":\"fieldTree_4\",\"parentTId\":\"fieldTree_3\",\"open\":false,\"isParent\":false,\"zAsync\":true,\"isFirstNode\":true,\"isLastNode\":false,\"isAjaxing\":false}}]}";
								final String paramData = "{\"expVal\":\"~F{id 参数}\",\"expActVal\":\"~F{cellId.cellId.cellId}\",\"varVal\":[{\"key\":\"~F{id 参数}\",\"value\":{\"t\":\"id 参数\",\"dType\":\"string\",\"code\":\"~F{cellId.cellId.cellId}\",\"value\":\"~F{id 参数}\",\"name\":\"id 参数\",\"pId\":1,\"level\":1,\"tId\":\"fieldTree_2\",\"parentTId\":\"fieldTree_1\",\"open\":false,\"isParent\":false,\"zAsync\":true,\"isFirstNode\":true,\"isLastNode\":true,\"isAjaxing\":false}}]}";
								//
								JSONArray collection = new JSONArray() {
									/**
									 * 
									 */
									private static final long serialVersionUID = 1L;

									{
										add(new JSONObject() {
											/**
											 * 
											 */
											private static final long serialVersionUID = 1L;

											{
												put("parentId", "1");
												put("ordinal", "2");
												put("fieldVal", "~F{default." + tableNameCN + ".id}");
												put("connector", "All");
												put("fieldData", fieldData);
												put("operator", "=");
												put("paramVal", "~F{id 参数}");
												put("paramData", paramData);
												put("dynamic", "N");
											}
										});
									}
								};

								inParameters.put("tabId", tableMsg.getCvtId());
								inParameters.put("name", name);
								inParameters.put("collection", collection);

								JSONArray collectionTree = new JSONArray() {
									/**
									 * 
									 */
									private static final long serialVersionUID = 1L;

									{
										add(new JSONObject() {
											/**
											 * 
											 */
											private static final long serialVersionUID = 1L;

											{
												put("name", "1");
												put("id", "1");
												put("pId", null);
												put("isParent", true);
												put("connector", "All");
											}
										});

										add(new JSONObject() {
											/**
											 * 
											 */
											private static final long serialVersionUID = 1L;

											{
												put("name", "1.1");
												put("id", "2");
												put("pId", "1");
												put("isParent", false);
											}
										});
									}
								};
								inParameters.put("collectionTree", collectionTree);
							}

							if (!tableMsg.contains(dname))
								continue;

							// 生成页面保存按钮主表规则
							final Map<String, String> c = columnMap.get(dname);
							if (c != null) {
								linkageControl.add(new JSONObject() {
									/**
									 * 
									 */
									private static final long serialVersionUID = 1L;

									{
										put("id", c.get("eleId"));
										put("text", c.get("eleName"));
									}
								});

								columns.add(new JSONObject() {
									/**
									 * 
									 */
									private static final long serialVersionUID = 1L;

									{
										put("id", c.get("eleId"));
										put("text", c.get("eleName"));
										put("strlen", strlen);
										put("dType", dType);
										put("fieldId", fieldId);
										put("fieldName", dname);
									}
								});
							}

							// 生成页面保存按钮从表规则

							selectJson = new JSONObject();

							this.populateSelectJson(tableNameCN, tblKey, dname, fname, dType, strlen, index,
									selectJson);

							selectSegments.add(selectJson);

							if (index == 0) {
								// 默认勾选id , topid, indexid
								String[] common = new String[] { "id", "topid", "index_id" };
								for (String k : common) {
									if (!tableMsg.contains(k)) {
										JSONObject selectJson1 = new JSONObject();
										this.populateSelectJson(tableNameCN, tblKey, k, k,
												k.equals("index_id") ? "int" : "string", 50, index++, selectJson1);
										selectSegments.add(selectJson1);
									}
								}
							}

							fromJson.put("tableCN", tableNameCN);

							// 页面数据集
							pageColumn = new JSONObject();
							pageColumn.put("columnLabel", selectJson.get("columnLabel"));
							pageColumn.put("title", fname);
							pageColumn.put("FieldLength", strlen);
							pageColumn.put("FieldType", dType);
							pageColumn.put("tableName", tblKey);
							pageColumn.put("datasetId", tableMsg.getCvtId());
							pageColumn.put("value", "~F{默认." + tableNameCN + "." + fname + "}");
							pageColumn.put("columnName", dname);
							pageColumn.put("tableNameCn", tableNameCN);
							pageColumn.put("code", "~F{default." + tblKey + "." + dname + "}");
							pageColumns.add(pageColumn);

							// 输入输出参数
							Map<String, String> cMap = columnMap.get(dname);
							String eleId = cMap.get("eleId");
							if (StringUtils.isNoneBlank(eleId)) {

								JSONArray eleArr = new JSONArray();

								JSONObject jsonObject = new JSONObject();
								jsonObject.put("inname", "数据源-->" + tableNameCN + "(" + fname + ")");
								jsonObject.put("param", "sys" + (System.currentTimeMillis() + index));
								jsonObject.put("outname", cMap.get("eleName"));
								jsonObject.put("inid", tableMsg.getCvtId());
								jsonObject.put("columnName", selectJson.get("columnLabel"));
								jsonObject.put("type", "getRow");
								jsonObject.put("outid", eleId);
								eleArr.add(jsonObject);

								datas.put(eleId, eleArr);

							}

							index++;
						} while (rSet.next());
						JdbcUtils.close(rSet);
						JdbcUtils.close(psmt);
					}
				}
				if (msg.size() > 1) {// 两张表 建立连线
					JSONArray joinSegments = new JSONArray() {
						private static final long serialVersionUID = 1L;

						{
							add(new JSONObject() {
								private static final long serialVersionUID = 1L;

								{
									put("connector", "left join");
									put("sourceTable", table.get("sourceTable"));
									put("sourceColumn", "id");
									put("targetTable", table.get("targetTable"));
									put("targetColumn", "topid");
									put("operator", "=");
									put("ordinal", 0);
								}
							});
						}
					};
					dataSetJson.put("joinSegments", joinSegments);
				}

				dataSetJson.put("databaseId", 0);
				dataSetJson.put("systemName", "default");

				DataSet dataSet = DataSetJsonFactory.jsonToObject(dataSetJson);
				dataSet.setExtend(dataSetService.getDataSet(dataSet.getId()) == null);
				dataSet.setDescription("系统生成cell查询数据集");
				dataSet.setCreateBy(createBy);
				dataSet.setDatabaseId((long) 0);
				dataSetService.save(dataSet);

				DataSetAudit audit = new DataSetAudit();
				audit.setContent(dataSetJson.toJSONString());
				audit.setDatasetId(dataSet.getId());
				audit.setSaveFlag("C");
				dataSetAuditService.save(audit);

				pageDataSet.put("databaseId", dataSet.getDatabaseId());
				pageDataSet.put("title", dataSet.getTitle());
				pageDataSet.put("name", dataSet.getTitle());

				final JSONObject datasource = dataSet.toJsonObject();
				datasource.put("datasetId", dataSet.getId());

				JSONArray dataSources = new JSONArray();

				dataSources.add(datasource);

				pageDataSet.put("datasource", dataSources);

				if (saveSourceSets.size() > 0) {
					for (int i = 0; i < saveSourceSets.size(); i++) {
						JSONObject jsonObject = saveSourceSets.getJSONObject(i);
						jsonObject.put("dataSetId", dataSet.getId());
					}
				}

				if (saveSourceSets.size() > 1) {
					final JSONObject batchRules = saveSourceSets.getJSONObject(1);
					batchRules.put("topTableName", topTable);
					dataSources.add(new JSONObject() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						{
							put("batchRules", batchRules);
						}
					});
				}

				this.setPageRule(dataSet.getId(), pageDataSet, paraType, inParameters, button.getLong("cvtId"));

				Map<String, Map<String, String>> Cap = componentMap.get("button");
				if (Cap != null) {
					String componentId = this.setValue(Cap, "id", button, "button0_" + button.getString("cvtId"));

					this.setValue(Cap, "savesourceset", button, saveSourceSets.toJSONString());
					this.setValue(Cap, "buttontype", button, "save");
					this.setValue(Cap, "html", button, "保存");
					this.setValue(Cap, "enabled", button, true);
					this.setValue(Cap, "visible", button, true);

					FormRule fRule = new FormRule();
					fRule.setPageId(prefix + button.getString("cvtId"));
					fRule.setName("button0_" + button.getString("cvtId"));
					if (componentId != null) {
						fRule.setComponentId(Long.parseLong(componentId));
					}
					fRule.setCreateBy(createBy);
					fRule.setCreateDate(new Date());
					fRule.setValue(button.toJSONString());
					formRuleService.save(fRule);
					formRulePropertyService.isRuleProperties(fRule);
					formRulePropertyService.saveComExt(fRule);
				}

			}
		}
	}

	/**
	 * 绑定页面
	 * 
	 * @param dataSet
	 * @param paraType
	 * @param inParameters
	 */
	public void setPageRule(String formPageId, final JSONObject dataSet, final JSONObject paraType,
			final JSONObject inParameters, Long cvtId) {

		String events = null;

		events = formulaToEventUtil.convertToEvents(cvtId);

		FormRuleQuery query = new FormRuleQuery();
		query.setName(formPageId);
		query.setPageId(formPageId);
		List<FormRule> list = formRuleService.list(query);
		FormRule fr = null;
		if (list != null && !list.isEmpty()) {
			fr = list.get(0);
		} else {
			fr = new FormRule();
		}

		fr.setName(formPageId);
		fr.setPageId(formPageId);
		fr.setCreateBy(createBy);
		fr.setComponentId((long) 38);

		JSONObject jObject = new JSONObject();
		jObject.put("296", new JSONArray() {// 页面数据源
			/**
			* 
			*/
			private static final long serialVersionUID = 1L;

			{
				add(dataSet);
			}
		});

		jObject.put("311", new JSONArray() {// 页面输入输出参数定义
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				add(paraType);
			}
		});

		jObject.put("312", new JSONArray() {// 页面输入表达式定义
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				add(inParameters);
			}
		});

		jObject.put("310", new JSONArray() {// 页面输入形参定义
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				add(new JSONObject() {
					/**
					* 
					*/
					private static final long serialVersionUID = 1L;

					{
						put("name", "id 参数");
						put("param", "cellId");
					}
				});
			}
		});

		if (StringUtils.isNotBlank(events)) { // 页面事件定义
			jObject.put("1438", JSON.parseArray(events));
		}

		fr.setValue(jObject.toJSONString());

		formRuleService.save(fr);
		formRulePropertyService.isRuleProperties(fr);
		formRulePropertyService.saveComExt(fr);

		ConvertPageTmpl arg0 = new ConvertPageTmpl();
		arg0.setCvtId(cvtId);
		arg0.setStatus(2);
		convertPageTmplService.save(arg0);

	}

	private void populateSelectJson(String tableNameCN, String tblKey, String dname, String fname, String dType,
			int strlen, int index, JSONObject selectJson) {
		selectJson.put("title", fname);
		selectJson.put("columnName", dname);
		selectJson.put("tableName", tblKey);
		selectJson.put("tableNameCN", tableNameCN);
		selectJson.put("columnLabel", tblKey + "_0_" + dname);
		selectJson.put("ordinal", index);
		selectJson.put("aggregate", "");
		selectJson.put("group_by", false);
		selectJson.put("output", true);

		JSONObject expression = new JSONObject();
		expression.put("columnCode", "~F{default." + tblKey + "." + dname + "}");
		expression.put("code", "~F{default." + tblKey + "." + dname + "}");
		expression.put("value", "~F{默认." + tableNameCN + "." + fname + "}");
		expression.put("FieldLength", strlen);
		expression.put("FieldType", dType);
		expression.put("tableNameCN", tableNameCN);
		selectJson.put("expression", expression);
	}

	public class TableMsg {
		private String cvtId;
		private String name;
		private int subTable = 0;
		private String tableId;
		private Set<String> columns = new HashSet<String>();
		private Map<String, Map<String, String>> columnMap = new HashMap<String, Map<String, String>>();

		public int getSubTable() {
			return subTable;
		}

		public void setSubTable(int subTable) {
			this.subTable = subTable;
		}

		public String getTableId() {
			return tableId;
		}

		public void setTableId(String tableId) {
			this.tableId = tableId;
		}

		public Set<String> getColumns() {
			return columns;
		}

		public void setColumns(Set<String> columns) {
			this.columns = columns;
		}

		public void addColumnModel(String column, String eleId, String eleName) {
			Map<String, String> cMap = new HashMap<String, String>();
			cMap.put("column", column);
			cMap.put("eleId", eleId);
			cMap.put("eleName", eleName);
			this.addColumn(column);
			columnMap.put(column, cMap);
		}

		public void addColumn(String column) {
			this.getColumns().add(column);
		}

		public boolean contains(String column) {
			return this.getColumns().contains(column);
		}

		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCvtId() {
			return cvtId;
		}

		public void setCvtId(String cvtId) {
			this.cvtId = cvtId;
		}

		public Map<String, Map<String, String>> getColumnMap() {
			return columnMap;
		}
	}

}
