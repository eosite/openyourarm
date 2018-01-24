package com.glaf.form.web.springmvc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.metamodel.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.glaf.activiti.model.TaskItem;
import com.glaf.activiti.service.ActivitiTaskQueryService;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.utils.StringUtil;
import com.glaf.conver.mergepdf.MergePdf;
import com.glaf.conver.pdf2tif.Pdf2Tif;
import com.glaf.conver.spread2excel.Spread2Excel;
import com.glaf.conver.spread2excel.Spread2ExcelByBack;
import com.glaf.conver.spread2pdf.Spread2Pdf;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.DataRequest.FilterDescriptor;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.base.TableModel;
import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.factory.DataServiceFactory;
import com.glaf.core.factory.TableFactory;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.jdbc.BulkInsertBean;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.security.SecurityUtils;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.UUID32;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.DataSetAudit;
import com.glaf.datamgr.domain.TableCombination;
import com.glaf.datamgr.domain.TaskTable;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.datamgr.service.DataSetAuditService;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.datamgr.service.TableCombinationService;
import com.glaf.datamgr.service.TaskTableService;
import com.glaf.datamgr.task.TableCombinationTask;
import com.glaf.datamgr.task.TaskTableTask;
import com.glaf.datamgr.util.ExceptionUtils;
import com.glaf.dep.base.factory.DataProcessFactory;
import com.glaf.dep.base.service.DepBaseWdataSetService;
import com.glaf.dep.report.domain.DepReportTemplate;
import com.glaf.dep.report.service.DepReportTemplateService;
import com.glaf.expression.core.util.ExpressionConvertUtil;
import com.glaf.form.core.domain.FormAttachment;
import com.glaf.form.core.domain.FormDictory;
import com.glaf.form.core.domain.FormRule;
import com.glaf.form.core.domain.FormTaskmain;
import com.glaf.form.core.domain.FormWorkFlowRule;
import com.glaf.form.core.domain.FormWorkflowPlan;
import com.glaf.form.core.helper.SqlHelper;
import com.glaf.form.core.query.FormAttachmentQuery;
import com.glaf.form.core.query.FormTaskmainQuery;
import com.glaf.form.core.query.FormWorkFlowRuleQuery;
import com.glaf.form.core.query.FormWorkflowPlanQuery;
import com.glaf.form.core.service.ActReBusinessService;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.form.core.service.FormRuleService;
import com.glaf.form.core.service.FormTaskmainService;
import com.glaf.form.core.service.FormWorkFlowRuleService;
import com.glaf.form.core.service.FormWorkflowPlanService;
import com.glaf.form.core.service.IFormAttachmentService;
import com.glaf.form.core.service.IFormDictoryService;
import com.glaf.form.core.util.FormTableOperateUtils;
import com.glaf.form.core.util.MutilDatabaseBean;
import com.glaf.form.rule.DataSourceRequest;
import com.glaf.form.rule.Global;
import com.glaf.form.rule.ParamsSqlHelper;
import com.glaf.form.rule.RequestSendRedirect;
import com.glaf.form.rule.util.InjectUtils;
import com.glaf.form.rule.util.ListUtil;
import com.glaf.template.util.TemplateUtils;

/**
 * 通用数据调用
 * 
 */
@Controller("/form/data")
@RequestMapping("/form/data")
public class FormDataController {
	protected static final Log logger = LogFactory.getLog(FormDataController.class);

	protected FormRulePropertyService formRulePropertyService;

	protected FormWorkFlowRuleService formWorkFlowRuleService;

	protected FormWorkflowPlanService formWorkflowPlanService;

	protected ActReBusinessService actReBusinessService;

	protected ActivitiTaskQueryService activitiTaskQueryService;

	protected MutilDatabaseBean mutilDatabaseBean;

	protected FormRuleService formRuleService;

	protected DataSetService dataSetService;

	protected IdGenerator idGenerator;

	protected IFormDictoryService formDictoryService;

	protected DepBaseWdataSetService depBaseWdataSetService;

	protected FormTaskmainService formTaskmainService;

	@Autowired
	protected DataSetAuditService dataSetAuditService;

	@Autowired
	protected IDatabaseService databaseService;

	@Autowired
	protected IFormAttachmentService formAttachmentService;

	@Autowired
	protected DepReportTemplateService depReportTemplateService;

	@Autowired
	protected TaskTableService taskTableService;

	@Autowired
	protected TableCombinationService tableCombinationService;

	public FormDataController() {

	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/createData")
	public @ResponseBody byte[] createData(HttpServletRequest request, @RequestBody DataSourceRequest dataSourceRequest)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);

		// 获取参数
		String rid = dataSourceRequest.getRid();// 规则id
		List<Map<String, Object>> models = (List<Map<String, Object>>) dataSourceRequest.getModels();
		if (models == null || models.size() <= 0) {
			return null;
		}
		Map<String, String> ruleMap = getRuleMap(rid);// 获取所有规则

		String dataSourceSet = ruleMap.get("dataSourceSet");
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONObject datasourceSetJSONObject = null;
		if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
			datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
		}
		if (datasourceSetJSONObject == null) {
			return null;
		}
		String tables = datasourceSetJSONObject.getString("tables");
		JSONArray tablesJSONArray = null;
		if (!isNullOrEmpty(tables)) {
			tablesJSONArray = JSON.parseArray(tables);
		}
		if (tablesJSONArray == null || tablesJSONArray.size() != 1) { // 如果是多表的
																		// 不支持操作
			return null;
		}
		String tableName = tablesJSONArray.getString(0);
		// end

		Long databaseId = datasourceSetJSONObject.getLongValue("databaseId");

		// 获取页面传递参数
		String params = dataSourceRequest.getParams();
		if (params != null && !"".equals(params)) {
			JSONObject jo = JSON.parseObject(params);
			try {
				Long dbid = jo.getLong("databaseId");
				if (dbid != null) {
					databaseId = dbid;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("databaseId转换失败" + e.getMessage());
			}
		}

		List<ColumnDefinition> columns = TableFactory.getColumnDefinitions(databaseId, tableName);

		ColumnDefinition idColumn = null;

		for (ColumnDefinition column : columns) {
			if (column.isPrimaryKey()) {
				idColumn = column;
				break;
			}
		}

		Map<String, ColumnDefinition> columnMap = new HashMap<String, ColumnDefinition>();

		for (ColumnDefinition column : columns) {
			columnMap.put(column.getColumnName().toLowerCase(), column);
		}

		if (!DBUtils.isAllowedTable(tableName)) {
			throw new RuntimeException("不允许访问系统表。");
		}

		JSONObject metadata = dataSetService.getDataSetMetadata(datasourceSetJSONObject.getString("datasetId")),
				mapping = null;
		if (metadata != null) {
			mapping = metadata.getJSONObject("mapping");
		}

		// getBindParamMap 获取传递参数 end
		for (Map<String, Object> map : models) {
			TableModel tableModel = new TableModel();
			tableModel.setTableName(tableName);

			String prefixTableName = tableName + "_0_";

			// 数据源ID

			// getBindParamMap 获取传递参数 start
			String inParameters = ruleMap.get("inParameters");
			// 获取页面传递参数
			Map<String, String> paramsMap = ParamsSqlHelper.getBindParamMap(params, inParameters);
			Set<String> set = paramsMap.keySet();
			for (String key : set) {
				String colkey = key.substring(key.indexOf(".") + 1);
				if (columnMap.containsKey(colkey.toLowerCase().replace(prefixTableName.toLowerCase(), ""))) {
					ColumnDefinition column = columnMap.get(colkey);
					ColumnModel cm = new ColumnModel();
					cm.setColumnName(column.getColumnName());
					cm.setJavaType(column.getJavaType());
					cm.setValue(paramsMap.get(key));
					tableModel.addColumn(cm);
				}
			}

			Set<String> keys = map.keySet();
			boolean updateFlag = false;
			String key;
			/*
			 * if (StringUtils.isNotEmpty(map.get((prefixTableName +
			 * idColumn.getColumnName().toLowerCase())) == null ? null :
			 * map.get( (prefixTableName +
			 * idColumn.getColumnName().toLowerCase())).toString()) ||
			 * StringUtils.isNotEmpty(map.get((prefixTableName +
			 * idColumn.getColumnName().toUpperCase())) == null ? null :
			 * map.get( (prefixTableName +
			 * idColumn.getColumnName().toUpperCase())).toString()) ||
			 * StringUtils.isNotEmpty(map.get(prefixTableName + "id") == null ?
			 * null : map.get( prefixTableName + "id").toString()) ||
			 * StringUtils.isNotEmpty(map.get(prefixTableName + "ID") == null ?
			 * null : map.get( prefixTableName + "ID").toString())) {//
			 * id不为null时 updateFlag = true; for (String skey : keys) { key =
			 * Global.getOriginalColumnName(mapping, skey); if ((prefixTableName
			 * + idColumn.getColumnName()).equalsIgnoreCase(key) ||
			 * (prefixTableName + "id").equalsIgnoreCase(key)) { ColumnModel
			 * idCol = new ColumnModel();
			 * idCol.setColumnName(idColumn.getColumnName());
			 * idCol.setJavaType(idColumn.getJavaType());
			 * idCol.setValue(map.get(skey)); tableModel.setIdColumn(idCol);
			 * continue; } else if ("__row_number__".equalsIgnoreCase(key)) {
			 * continue; } if
			 * (columnMap.containsKey(key.toLowerCase().replace(prefixTableName.
			 * toLowerCase(), "")) ||
			 * columnMap.containsKey(key.toUpperCase().replace(prefixTableName.
			 * toUpperCase(), ""))) { ColumnDefinition column =
			 * columnMap.get(key.toLowerCase().replace(prefixTableName.
			 * toLowerCase(), "")) != null ?
			 * columnMap.get(key.toLowerCase().replace(
			 * prefixTableName.toLowerCase(), "")) :
			 * columnMap.get(key.toUpperCase().replace(prefixTableName.
			 * toUpperCase(), "")); ColumnModel col = new ColumnModel();
			 * col.setColumnName(column.getColumnName());
			 * col.setJavaType(column.getJavaType()); if
			 * (StringUtils.equalsIgnoreCase(column.getJavaType(), "Integer")) {
			 * col.setValue(ParamUtils.getIntValue(map, skey)); } else if
			 * (StringUtils.equalsIgnoreCase(column.getJavaType(), "Long")) {
			 * col.setValue(ParamUtils.getLongValue(map, skey)); } else if
			 * (StringUtils.equalsIgnoreCase(column.getJavaType(), "Double")) {
			 * col.setValue(ParamUtils.getDoubleValue(map, skey)); } else if
			 * (StringUtils.equalsIgnoreCase(column.getJavaType(), "Date")) {
			 * col.setValue(ParamUtils.getDate(map, skey)); } else {
			 * col.setValue(map.get(skey)); } tableModel.addColumn(col); } } }
			 * else {
			 */
			String nextId = null;
			for (String skey : keys) {
				key = Global.getOriginalColumnName(mapping, skey);
				if ((prefixTableName + idColumn.getColumnName()).equalsIgnoreCase(key)
						|| (prefixTableName + "id").equalsIgnoreCase(key)) {
					ColumnModel idCol = new ColumnModel();
					idCol.setColumnName(idColumn.getColumnName());
					idCol.setJavaType(idColumn.getJavaType());
					if (map.get(skey) != null) {
						updateFlag = true;
						idCol.setValue(map.get(skey));
						tableModel.setIdColumn(idCol);
					} else {
						nextId = mutilDatabaseBean.getNextId(tableName, "id", loginContext.getActorId(), databaseId);
						// 创建ID
						idCol.setValue(nextId);
						tableModel.addColumn(idCol);
					}
				} else if ((prefixTableName + "listno").equalsIgnoreCase(key)) {
					ColumnDefinition column = columnMap
							.get(key.toLowerCase().replace(prefixTableName.toLowerCase(), ""));
					ColumnModel col = new ColumnModel();
					col.setColumnName(column.getColumnName());
					col.setJavaType(column.getJavaType());
					if (ParamUtils.getIntValue(map, skey) == null) {
						int maxListNo = mutilDatabaseBean.getMaxListNo(tableName, "listno", databaseId);
						col.setValue(maxListNo);
					} else {
						col.setValue(ParamUtils.getIntValue(map, skey));
					}
					tableModel.addColumn(col);
				} else {
					if (columnMap.containsKey(key.toLowerCase().replace(prefixTableName.toLowerCase(), ""))) {
						ColumnDefinition column = columnMap
								.get(key.toLowerCase().replace(prefixTableName.toLowerCase(), ""));
						ColumnModel col = new ColumnModel();
						col.setColumnName(column.getColumnName());
						col.setJavaType(column.getJavaType());
						if (StringUtils.equalsIgnoreCase(column.getJavaType(), "Integer")) {
							col.setValue(ParamUtils.getIntValue(map, skey));
						} else if (StringUtils.equalsIgnoreCase(column.getJavaType(), "Long")) {
							col.setValue(ParamUtils.getLongValue(map, skey));
						} else if (StringUtils.equalsIgnoreCase(column.getJavaType(), "Double")) {
							col.setValue(ParamUtils.getDoubleValue(map, skey));
						} else if (StringUtils.equalsIgnoreCase(column.getJavaType(), "Date")) {
							col.setValue(ParamUtils.getDate(map, skey));
						} else {
							col.setValue(map.get(skey));
						}
						tableModel.addColumn(col);
					}
				}
			}
			if (nextId != null) {
				models.get(0).put(prefixTableName + "id", nextId);
			}
			// }
			try {
				// 执行创建 或者 更新操作
				if (updateFlag) {
					// 执行更新操作
					mutilDatabaseBean.updateTableData(tableModel, databaseId);
				} else {
					// 执行创建操作
					mutilDatabaseBean.insertTableData(tableModel, databaseId);
				}
			} catch (Exception e) {
				logger.error("data create error " + e.getMessage());
				return null;
			}
		}

		JSONObject result = new JSONObject();
		result.put("data", models);
		result.put("total", models.size());
		return result.toJSONString().getBytes("UTF-8");
	}

	// RequestBody接受前台的json数据；
	@SuppressWarnings("unchecked")
	@RequestMapping("/deleteData")
	public @ResponseBody byte[] deleteData(HttpServletRequest request, @RequestBody DataSourceRequest dataSourceRequest)
			throws Exception {
		// 获取参数
		String rid = dataSourceRequest.getRid();
		List<?> models = dataSourceRequest.getModels();
		if (models == null || models.size() <= 0) {
			return null;
		}
		Map<String, String> ruleMap = getRuleMap(rid);// 获取所有规则

		String dataSourceSet = ruleMap.get("dataSourceSet");
		// 装换成json数组
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONObject datasourceSetJSONObject = null;
		// 将json数组转化成json对象
		if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
			datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
		}
		if (datasourceSetJSONObject == null) {
			return null;
		}
		String tables = datasourceSetJSONObject.getString("tables");
		JSONArray tablesJSONArray = null;
		if (!isNullOrEmpty(tables)) {
			tablesJSONArray = JSON.parseArray(tables);
		}
		if (tablesJSONArray == null || (tablesJSONArray != null && tablesJSONArray.size() > 1)) { // 如果是多表的
			// 不支持操作
			return null;
		}

		String tableName = tablesJSONArray.getString(0);

		String prefixTableName = tableName + "_0_";

		// 数据源ID
		Long databaseId = datasourceSetJSONObject.getLong("databaseId");

		JSONObject metadata = dataSetService.getDataSetMetadata(datasourceSetJSONObject.getString("datasetId")),
				mapping = null;
		if (metadata != null) {
			mapping = metadata.getJSONObject("mapping");
		}

		String params = dataSourceRequest.getParams();
		// 将参数params转化成json对象
		if (params != null && !"".equals(params)) {
			JSONObject jo = JSON.parseObject(params);
			try {
				Long dbid = jo.getLong("databaseId");
				if (dbid != null) {
					databaseId = dbid;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("databaseId转换失败" + e.getMessage());
			}
		}

		Map<String, Object> map = null;
		String id = null;
		try {
			for (Object object : models) {
				map = (Map<String, Object>) object;
				Set<String> keys = map.keySet();
				String key;
				for (String skey : keys) {
					key = Global.getOriginalColumnName(mapping, skey);
					if (key.equalsIgnoreCase(prefixTableName + "id")) {
						id = map.get(skey).toString();
						if (StringUtils.isNotEmpty(id)) {// id不为null时
							mutilDatabaseBean.deleteTableByWhereCause(tableName, " and id = '" + id + "'", databaseId);
							// 级联删除属性
							String casDelete = ruleMap.get("casDelete");
							if ("true".equalsIgnoreCase(casDelete)) {
								String dataTableName = "cell_data_table";
								if (tableName.startsWith("R_") || tableName.startsWith("r_")) {
									dataTableName = "R_DATA_TABLE";
								}
								String sql = "select TABLENAME from " + dataTableName
										+ " where topid = ( select id from " + dataTableName + " where tablename =  '"
										+ tableName + "')";
								List<Map<String, Object>> subTables = mutilDatabaseBean.getDataListBySql(sql,
										databaseId);
								if (subTables != null && subTables.size() > 0) {
									for (Map<String, Object> map2 : subTables) {
										Map<String, Object> subTableMap = map2;
										String subTableName = (String) subTableMap.get("TABLENAME");
										mutilDatabaseBean.deleteTableByWhereCause(subTableName,
												" and topid = '" + id + "'", databaseId);
									}
								}
							}
						}
					}
				}

			}
		} catch (Exception e) {
			logger.error("data delete" + e.getMessage());
			return null;
		}
		return this.getGridData(request, dataSourceRequest);
	}

	/*
	 * @ResponseBody作用：该注解用于将Controller的方法返回的对象，
	 * 通过适当的HttpMessageConverter转换为指定格式后，写入到Response对象的body数据区。
	 * 
	 * @RequestBody作用： i)
	 * 该注解用于读取Request请求的body部分数据，使用系统默认配置的HttpMessageConverter进行解析，
	 * 然后把相应的数据绑定到要返回的对象上； ii) 再把HttpMessageConverter返回的对象数据绑定到
	 * controller中方法的参数上。
	 */
	@RequestMapping("/gridData")
	public @ResponseBody byte[] getGridData(HttpServletRequest request,
			@RequestBody DataSourceRequest dataSourceRequest) throws Exception {
		// 获取参数
		String rid = dataSourceRequest.getRid();// 规则id

		// 获取规则MAP
		Map<String, String> ruleMap = getRuleMap(rid);
		if (ruleMap == null) {
			logger.info("ruleMap 找不到规则:" + rid);
			return null;
		}
		String __ret = RequestSendRedirect.redirect(ruleMap, dataSourceRequest);
		if (StringUtils.isNotBlank(__ret)) {
			return __ret.getBytes("UTF-8");
		}

		String tableSource = ruleMap.get("tableSource");
		JSONArray tableSourceJSONArray = JSON.parseArray(tableSource);
		// 若选择了物理表来源
		if (tableSourceJSONArray != null && tableSourceJSONArray.size() > 0) {
			JSONObject ret = getGridDataFromTable(ruleMap, tableSourceJSONArray.getJSONObject(0), dataSourceRequest);
			return ret.toJSONString().getBytes("UTF-8");
		}

		String dataSourceSet = ruleMap.get("dataSourceSet");
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONObject datasourceSetJSONObject = null;
		if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
			datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
		}
		if (datasourceSetJSONObject == null) {
			logger.info("数据集错误");
			return null;
		}

		// 构建联动语句 start

		String psql = "";

		String params = dataSourceRequest.getParams();
		JSONObject paramsObj = new JSONObject();
		if (!isNullOrEmpty(params)) {
			paramsObj = JSON.parseObject(params);
		}
		// 防止sql注入
		InjectUtils.escapeSql(paramsObj);

		// 获取 输入表达式定义
		String inParameters = ruleMap.get("inParameters");
		// 获取页面传递参数
		psql = ParamsSqlHelper.getParamSql(paramsObj.toJSONString(), inParameters,
				datasourceSetJSONObject.getString("datasetId"));

		JSONObject metadata = dataSetService.getDataSetMetadata(datasourceSetJSONObject.getString("datasetId")),
				mapping = null;
		if (metadata != null) {
			mapping = metadata.getJSONObject("mapping");
		}

		// 构建联动语句 end
		// //System.out.println("联动语句"+psql);

		// 返回
		JSONObject result = new JSONObject();

		// 分页 start
		int start = 0;
		int limit = PageResult.DEFAULT_PAGE_SIZE;
		int pageNo = dataSourceRequest.getPage();
		limit = dataSourceRequest.getPageSize();

		start = (pageNo - 1) * limit;
		if (start < 0) {
			start = 0;
		}
		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}
		// 分页 end

		// 排序
		List<SortDescriptor> sorts = dataSourceRequest.getSort();
		String sortSql = "";
		if (sorts != null && sorts.size() > 0) {
			for (SortDescriptor sortDescriptor : sorts) {
				sortSql += Global.getOriginalColumnName(mapping, sortDescriptor.getField()).split("_0_")[1] + " "
						+ sortDescriptor.getDir() + ((sorts.indexOf(sortDescriptor) == sorts.size() - 1) ? "" : ",");
			}
		}
		// 排序 end

		// where 查询
		FilterDescriptor filterDescriptor = dataSourceRequest.getFilter();
		StringBuilder whereSql = new StringBuilder(" ");
		if (filterDescriptor != null && filterDescriptor.getFilters() != null
				&& filterDescriptor.getFilters().size() > 0) {
			SqlHelper helper = new SqlHelper();
			helper.getWhereSql(whereSql, filterDescriptor, 1, false);
		}
		// where end

		// 构建sql start
		DataSetBuilder builder = new DataSetBuilder();
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("HttpServletRequest", request);

		if (!paramsObj.isEmpty()) {
			parameter.putAll(paramsObj);
		}

		Query query = builder.buildQuery(datasourceSetJSONObject.getString("datasetId"),
				whereSql.toString().trim() + psql, sortSql, parameter);

		String sql = query.toSql();

		// 构建sql end

		// String sql = datasourceSetJSONObject.getString("sql");

		// 判断是否服务器分页
		String serverPaging = ruleMap.get("serverPaging");
		List<Map<String, Object>> dataMaps = new ArrayList<Map<String, Object>>();
		int total = 0;

		// 数据源ID
		Long databaseId = datasourceSetJSONObject.getLong("databaseId");
		// 获取页面传递参数
		if (params != null && !"".equals(params)) {
			JSONObject jo = JSON.parseObject(params);
			try {
				Long dbid = jo.getLong("databaseId");
				if (dbid != null) {
					databaseId = dbid;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("databaseId转换失败" + e.getMessage());
			}
		}
		// 执行查询
		total = mutilDatabaseBean.getCountBySql(query, databaseId);

		JSONArray encrypts = dataSetService.getDataSetEncrypts(datasourceSetJSONObject.getString("datasetId"));
		if (total > 0) {
			if (StringUtils.isEmpty(serverPaging) || !"true".equalsIgnoreCase(serverPaging)) {
				if (MapUtils.getBooleanValue(ruleMap, "pageable", false)) {
					// 这里为什么客户端分页还需要在服务端做分页 处理？
					if (MapUtils.getIntValue(ruleMap, "pageSize") != 0) {
						limit = MapUtils.getIntValue(ruleMap, "pageSize");
					}
					dataMaps = mutilDatabaseBean.getDataListBySqlCriteria(sql, start, limit, databaseId);
				} else {
					dataMaps = mutilDatabaseBean.getDataListBySql(sql, databaseId);
				}
				// 客服端分页
			} else {
				// 服务端分页
				// dataMaps = mutilDatabaseBean.getDataListByQueryCriteria(sql,
				// start,
				// limit, databaseId);
				dataMaps = mutilDatabaseBean.getDataListBySqlCriteria(sql, start, limit, databaseId);
			}
			// 执行查询
			JSONArray rowsJSON = new JSONArray();
			int count = 1;
			for (Map<String, Object> map : dataMaps) {
				Set<String> keys = map.keySet();
				Iterator<String> iterator = keys.iterator();
				JSONObject josnObj = new JSONObject();
				josnObj.put("checkbox", count);
				josnObj.put("startIndex", count);
				josnObj.put("databaseId", databaseId);
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					// if (map.get(key) != null) {
					if (!key.equals(key.toLowerCase())) {
						josnObj.put(key, map.get(key) != null ? map.get(key).toString() : "");
					}
					josnObj.put(key.toLowerCase(), map.get(key) != null ? map.get(key).toString() : "");
					// }
				}
				rowsJSON.add(josnObj);
				count++;
			}

			result.put("total", total);
			result.put("data", rowsJSON);

			if (!encrypts.isEmpty()) {
				JSONObject encryptsObj = new JSONObject();
				encryptsObj.put("___chinaiss___hmtd___encrypts___", true);
				encryptsObj.put("encrypts", encrypts);
				encryptsObj.put("result", result);
				return encryptsObj.toJSONString().getBytes("UTF-8");
			}
			return result.toJSONString().getBytes("UTF-8");
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("data", rowsJSON);
			result.put("total", total);

			return result.toJSONString().getBytes("UTF-8");
		}
	}

	/**
	 * 根据物理表直接获取grid数据
	 * 
	 * @param ruleMap
	 *            规则信息
	 * @param tableSource
	 *            物理表信息
	 * @param dataSourceRequest
	 *            页面参数
	 * @return
	 */
	private JSONObject getGridDataFromTable(Map<String, String> ruleMap, JSONObject tableSource,
			DataSourceRequest dataSourceRequest) {
		JSONObject result = new JSONObject();
		// 表格数据，包括表信息即字段信息
		JSONObject tableData = tableSource.getJSONObject("table");
		if (tableData == null || tableData.isEmpty()) {
			JSONArray rowsJSON = new JSONArray();
			result.put("data", rowsJSON);
			result.put("total", 0);
			logger.error("无物理表信息，无法查询");
			return result;
		}
		// 表信息
		JSONObject tableInformation = tableData.getJSONObject("table");
		// 物理表真实表名
		String tableName = tableInformation.getString("tableName");
		// 标段ID
		String systemName = tableData.getString("systemName");
		if (systemName == null) {
			systemName = Environment.DEFAULT_SYSTEM_NAME;
		}

		// 分页 start
		int start = 0;
		int limit = PageResult.DEFAULT_PAGE_SIZE;
		int pageNo = dataSourceRequest.getPage();
		limit = dataSourceRequest.getPageSize();

		start = (pageNo - 1) * limit;
		if (start < 0) {
			start = 0;
		}
		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}
		// 分页 end

		// 排序
		List<SortDescriptor> sorts = dataSourceRequest.getSort();
		String sortSql = "";
		if (sorts != null && sorts.size() > 0) {
			for (SortDescriptor sortDescriptor : sorts) {
				sortSql += sortDescriptor.getField() + " "
						+ (sortDescriptor.getDir().isEmpty() ? " desc " : sortDescriptor.getDir())
						+ ((sorts.indexOf(sortDescriptor) == sorts.size() - 1) ? "" : ",");
			}
		}

		// 执行查询
		String sql = "select * from " + tableName;
		String countSql = "select count(1) as total from " + tableName;

		if (!sortSql.isEmpty()) {
			sql += " order by " + sortSql;
		}

		Long total = 0l;
		List<Map<String, Object>> dataCountList = mutilDatabaseBean.basesql(systemName, countSql, null, null);
		if (dataCountList != null && dataCountList.size() > 0) {
			total = Long.parseLong(dataCountList.get(0).get("total").toString());
		}

		// 判断是否服务器分页
		String serverPaging = ruleMap.get("serverPaging");
		if (total > 0) {
			List<Map<String, Object>> dataMaps = null;
			if (StringUtils.isEmpty(serverPaging) || !"true".equalsIgnoreCase(serverPaging)) {
				// 客服端分页
				dataMaps = mutilDatabaseBean.basesql(systemName, sql, null, null);
			} else {
				// 服务端分页
				dataMaps = mutilDatabaseBean.basesql(systemName, sql, start, limit);
			}
			// 执行查询
			JSONArray rowsJSON = new JSONArray();
			int count = 1;
			for (Map<String, Object> map : dataMaps) {
				Set<String> keys = map.keySet();
				Iterator<String> iterator = keys.iterator();
				JSONObject josnObj = new JSONObject();
				josnObj.put("checkbox", count);
				josnObj.put("startIndex", count);
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					// if (map.get(key) != null) {
					josnObj.put(key.toLowerCase(), map.get(key) != null ? map.get(key).toString() : "");
					// }
				}
				rowsJSON.add(josnObj);
				count++;
			}

			result.put("total", total);
			result.put("data", rowsJSON);
			return result;
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("data", rowsJSON);
			result.put("total", total);
			return result;
		}
	}

	private static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * grid表单元格数据源获取
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/widgetSource")
	public @ResponseBody byte[] getWidgetSource(HttpServletRequest request) throws Exception {
		// 获取参数
		String dataSetId = RequestUtils.getString(request, "dataSetId", null);// 数据集id
		Long databaseId = RequestUtils.getLong(request, "databaseId", 0);// 数据库id
		String params = RequestUtils.getString(request, "params", null);// 额外参数
		// String text = RequestUtils.getString(request, "text", null);// 父节点ID
		// String value = RequestUtils.getString(request, "value", null);//
		// 父节点ID

		// if (!DBUtils.isAllowedTable(tableName)) {
		// throw new RuntimeException("不允许访问系统表。");
		// }
		//
		// String sql = " select distinct " + text + (value == null ? "" : (","
		// + value)) + " from " + tableName;
		List<Map<String, Object>> dataMaps = new ArrayList<Map<String, Object>>();

		// 构建sql start
		DataSetBuilder builder = new DataSetBuilder();
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("HttpServletRequest", request);
		if (!isNullOrEmpty(params)) {
			parameter.putAll(JSON.parseObject(params));
		}

		Query query = builder.buildQuery(dataSetId, null, null, parameter);
		String sql = query.toSql();
		// 构建sql end

		// 客服端分页
		dataMaps = mutilDatabaseBean.getDataListBySql(sql, databaseId);

		return JSON.toJSONString(dataMaps).getBytes("UTF-8");
	}

	/**
	 * grid表单元格数据源获取
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/widgetSource2")
	public @ResponseBody byte[] getWidgetSource2(HttpServletRequest request) throws Exception {
		// 获取参数
		String tableName = RequestUtils.getString(request, "tableName", null);// 规则id
		String text = RequestUtils.getString(request, "text", null);// 父节点ID
		String value = RequestUtils.getString(request, "value", null);// 父节点ID

		if (!DBUtils.isAllowedTable(tableName)) {
			throw new RuntimeException("不允许访问系统表。");
		}

		String sql = " select distinct " + text + (value == null ? "" : ("," + value)) + " from " + tableName;
		List<Map<String, Object>> dataMaps = new ArrayList<Map<String, Object>>();

		// 客服端分页
		dataMaps = mutilDatabaseBean.getDataListBySql(sql, null);

		return JSON.toJSONString(dataMaps).getBytes("UTF-8");
	}

	private Map<String, String> getRuleMap(String rid) {
		return this.formRulePropertyService.getRuleMap(rid);
	}

	/**
	 * 过滤 zj 特殊参数
	 * 
	 * @param params
	 * @param ruleMap
	 */
	private void paramsFilter(Map<String, Object> params, Map<String, String> ruleMap) {

		if (params.get("flow-param") != null) { // pageId_0_id
			// String flowParam = params.get("flow-param").toString();
			// if (flowParam.contains("_0_")) {
			String inParameters = ruleMap.get("inParameters"), param = null;
			if (!isNullOrEmpty(inParameters)) {
				JSONArray jsonArray0 = JSONArray.parseArray(inParameters);
				if (jsonArray0 != null && !jsonArray0.isEmpty()) {
					for (int i = 0; i < jsonArray0.size(); i++) {
						JSONArray jsonArray = jsonArray0.getJSONObject(i).getJSONArray("collection");
						for (int ii = 0; ii < jsonArray.size(); ii++) {
							JSONObject jsonObject = jsonArray.getJSONObject(ii);
							String strs = jsonObject.getString("paramData");
							String expActVal = JSON.parseObject(strs).getString("expActVal");
							expActVal = ExpressionConvertUtil.expressionConvert(expActVal,
									ExpressionConvertUtil.DATABASE_TYPE);

							if (!isNullOrEmpty(expActVal))
								param = StringUtils.split(expActVal, ".")[0];

							strs = jsonObject.getString("fieldData");
							expActVal = JSON.parseObject(strs).getString("expActVal");
							expActVal = ExpressionConvertUtil.expressionConvert(expActVal,
									ExpressionConvertUtil.DATABASE_TYPE);
							if (!isNullOrEmpty(expActVal)) {
								String fieldName = StringUtils.split(expActVal, ".")[1];
								if (fieldName.equalsIgnoreCase("id") && param != null) {
									params.put(param, params.get("flow-param"));
								}
							}
						}
						// }
					}
				}
			}
		}
	}

	/**
	 * 页面初始化赋值
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/initPageDetail")
	public byte[] initPageDetail(HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String pageId = ParamUtils.getString(params, "id");// pageId
		if (pageId == null)
			throw new RuntimeException("pageId is null");
		JSONObject result = new JSONObject(params);
		// LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, String> ruleMap = this.getRuleMapByPageIdAndEleId(pageId, pageId);

		// List<FormRule> frs = formRuleService.getRules(pageId);

		// FormRuleQuery formRuleQueryquery = new FormRuleQuery();
		// formRuleQueryquery.setName(pageId);
		// formRuleQueryquery.setPageId(pageId);
		// List<FormRule> frs = formRuleService.list(formRuleQueryquery);
		// if (frs != null && frs.size() > 0) {
		// FormRule fr = frs.get(0);
		// result.put("pageRuleId", fr.getId());
		// ruleMap = this.getRuleMap(fr.getId());
		// }
		/*
		 * for (FormRule fr : frs) { if (fr.getName().equalsIgnoreCase(pageId))
		 * { result.put("pageRuleId", fr.getId()); ruleMap =
		 * this.getRuleMap(fr.getId()); break; } }
		 */
		this.paramsFilter(params, ruleMap);
		if (ruleMap != null) {
			result.put("pageRuleId", ruleMap.get("__pageRuleId"));
			JSONArray jsonArray = null;
			JSONObject controlRule = null;
			// 页面输入输出参数对应
			String param = null;
			JSONObject jsonObject = null;
			Map<String, Object> datas = new HashMap<String, Object>();// 存储参数值,查询结果
			String inParamDefinedStr = ruleMap.get("inParamDefined");// 页面级参数
			if (!isNullOrEmpty(inParamDefinedStr)) {
				jsonArray = JSONArray.parseArray(inParamDefinedStr);
				for (int i = 0; i < jsonArray.size(); i++) {
					jsonObject = jsonArray.getJSONObject(i);
					param = jsonObject.getString("param");
					if (params.get(param) != null) {
						datas.put(param, "" + params.get(param) + "");
					}
				}
			}

			String str = ruleMap.get("paraType");
			if (!isNullOrEmpty(str)) {
				jsonArray = JSONArray.parseArray(str);
				str = jsonArray.getJSONObject(0).getString("datas");
				controlRule = JSON.parseObject(str);
				result.put("controlRule", controlRule);
			}

			str = ruleMap.get("dataSourceSet"); // 页面数据源
			if (!isNullOrEmpty(str)) {
				String dataSetId = null;
				JSONArray tmp = JSONArray.parseArray(str);
				if (tmp != null && !tmp.isEmpty()) {
					JSONObject dataSource = JSONArray.parseArray(str).getJSONObject(0);
					String dataSourceStr = dataSource.getString("datasource");
					if (StringUtils.isNotBlank(dataSourceStr)) {
						jsonArray = JSONArray.parseArray(dataSourceStr);
					} else {
						/**
						 * 单个数据集
						 */
						jsonArray = JSONArray.parseArray(str);
					}
					List<JSONObject> batchRules = new ArrayList<JSONObject>();
					if (jsonArray != null && !jsonArray.isEmpty()) {
						for (int i = 0; i < jsonArray.size(); i++) {
							jsonObject = jsonArray.getJSONObject(i);
							String batchRule = jsonObject.getString("batchRules");
							if (!isNullOrEmpty(batchRule)) {
								batchRules.add(JSON.parseObject(batchRule));
								continue;
							}

							dataSetId = jsonObject.getString("datasetId");
							String inParameters = ruleMap.get("inParameters");
							// 获取页面传递参数
							String psql = ParamsSqlHelper.getParamSql(JSON.toJSONString(datas), inParameters,
									dataSetId);
							logger.info(psql);
							boolean ok = true;
							if (!isNullOrEmpty(inParameters)) {
								ok = this.initParameters(inParameters, datas, result);
							}
							if (StringUtils.isNotBlank(dataSetId) && (ok || !"".equals(psql))) {
								// 构建sql start
								try {
									DataSetBuilder builder = new DataSetBuilder();
									JSONObject json = builder.getJson(dataSetId, psql, null, 0, 1, datas);
									if (json != null) {
										JSONArray arr = json.getJSONArray("rows");
										if (arr != null && arr.size() > 0)
											params.putAll(arr.getJSONObject(0));
									}
								} catch (Exception e) {
									e.printStackTrace();
									logger.debug(e.getMessage());
									throw e;
								}
							}
						}
					}
					result.put("batchRules", batchRules);
				}
			}
			String idValue = null;
			if (controlRule != null) {
				Map<String, Object> data = new HashMap<String, Object>();// 页面控件id对应值
				for (String key : controlRule.keySet()) {
					jsonArray = JSONArray.parseArray(controlRule.getString(key));
					for (int k = 0; k < jsonArray.size(); k++) {
						JSONObject j = jsonArray.getJSONObject(k);
						String columnName = j.getString("columnName");
						if (StringUtils.isBlank(columnName)) {
							columnName = j.getString("inparam");
						}
						if (columnName != null) {
							j.put("idValue", params.get(columnName.split("_0_")[0] + "_0_id"));
							j.put("value", params.get(columnName));
						}
						if (idValue == null)
							idValue = j.getString("idValue");
					}
					data.put(key, JSON.toJSON(jsonArray));
				}
				result.put("data", data);
			}
			result.put("__idValue", idValue);

			str = ruleMap.get("saveSourceSet"); // 保存设置
			result.put("handleColumns", str == null ? ruleMap.get("linkageControlIn") : str);

		}

		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 页面工作流加载
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/initPageFlow")
	public byte[] initPageFlow(HttpServletRequest request) throws IOException {
		String idValue = RequestUtils.getString(request, "idValue");
		String pageId = RequestUtils.getString(request, "pageId");
		Map<String, String> ruleMap = this.getRuleMapByPageIdAndEleId(pageId, pageId);

		Map<String, Object> params = RequestUtils.getParameterMap(request);
		if (ruleMap != null) {
			LoginContext loginContext = RequestUtils.getLoginContext(request);
			JSONObject result = new JSONObject();
			this.commonFlow(idValue, pageId, loginContext.getActorId(), result, ruleMap, params);
			return result.toJSONString().getBytes("UTF-8");
		}
		return null;
	}

	private void commonFlow(String idValue, String pageId, String actorId, JSONObject result,
			Map<String, String> ruleMap, Map<String, Object> params) {
		String str = ruleMap.get("flowDefined"); // 页面工作流定义
		String defId, processId = MapUtils.getString(params, "processId");
		if (!isNullOrEmpty(idValue) || StringUtils.isNotEmpty(processId)) {
			FormTaskmain main = null;
			if (StringUtils.isNotEmpty(processId)) {
				FormTaskmainQuery query = new FormTaskmainQuery();
				query.setProcessId(processId);
				List<FormTaskmain> mains = formTaskmainService.list(query);
				if (CollectionUtils.isNotEmpty(mains)) {
					main = mains.get(0);
				}
				// main = new FormTaskmain();
			} else {
				main = this.formTaskmainService.getFormTaskMainByPageIdAndIdValue(pageId, idValue);
			}
			if (main != null) {
				defId = main.getDefId();
				if (!isNullOrEmpty(defId)) { // 工作流模版启动方式
					Integer version = this.formWorkflowPlanService.getNextVersionByDefId(defId) - 1;
					FormWorkflowPlanQuery formWorkflowPlanQuery = new FormWorkflowPlanQuery();
					formWorkflowPlanQuery.setDefId(defId);
					formWorkflowPlanQuery.setVersion(version);
					formWorkflowPlanQuery.setPageId(pageId);
					List<FormWorkflowPlan> plans = this.formWorkflowPlanService.list(formWorkflowPlanQuery);
					if (plans != null && !plans.isEmpty()) {
						FormWorkflowPlan plan = plans.get(0);
						if (!isNullOrEmpty(plan.getBytes())) {
							JSONObject processDefined = JSONArray.parseArray(plan.getBytes()).getJSONObject(0);
							if (processDefined != null) {
								result.put("flowDefined", processDefined);
								processDefined.put("processInstanceId", main.getProcessId());
								this.initFlow(idValue, pageId, actorId, defId, processDefined);
							}
						}
					}
				}

			} else if (!isNullOrEmpty(str)) {// 页面工作流定义
				JSONObject processDefined = JSONArray.parseArray(str).getJSONObject(0);
				result.put("flowDefined", processDefined);
				this.initFlow(idValue, pageId, actorId, null, processDefined);
			}
		}
	}

	public void initFlow(String idValue, String pageId, String actorId, String defId, JSONObject processDefined) {
		// ProcessInstance processInstance = this.workFlowDefinedService
		// .getProcessInstanceByDKeyAndPKeyAndBKey(processDefined.getString("key"),
		// pageId, idValue);
		String processInstanceId = processDefined.getString("processInstanceId");
		if (StringUtils.isNotBlank(processInstanceId)) {

			// }
			// if (processInstance != null) {// 流程已经开始 查找流程走向(到哪了)
			// String processInstanceId = processInstance.getId();
			List<Task> tasks = this.activitiTaskQueryService.getTasks(processInstanceId);
			if (tasks != null) {
				Integer version = this.getNextVersion(defId, pageId);
				FormWorkFlowRuleQuery query = new FormWorkFlowRuleQuery();
				query.setPageId(pageId);
				query.setVersion(version);
				query.setDefId(defId);

				List<TaskItem> items = this.activitiTaskQueryService.getTaskItems(processInstanceId);

				Map<String, Object> params = Global.getGlobalVariables();

				for (TaskItem item : items) {
					if ((item.getActorId() != null && item.getActorId().equalsIgnoreCase(actorId))) {// 需要当前人的任务才执行
						query.setActTaskId(item.getTaskDefinitionKey());
						List<FormWorkFlowRule> list = this.formWorkFlowRuleService.list(query);
						if (list != null && !list.isEmpty()) {
							for (FormWorkFlowRule ffr : list) {
								Boolean rst = populateFlow(ffr, processDefined, defId, item, params);
								if (!rst)
									break;
							}
						}
					}
				}
			}
		}
	}

	public static Boolean populateFlow(FormWorkFlowRule ffr, JSONObject processDefined, String defId, TaskItem item,
			Map<String, Object> params) {
		if (StringUtils.equalsIgnoreCase(ffr.getDefId(), defId)) {
			JSONArray rules = JSONArray.parseArray(ffr.getBytes());
			if (rules != null && !rules.isEmpty()) {
				if (item != null) {
					processDefined.put("_taskName", item.getTaskName());
					processDefined.put("_taskId", item.getTaskDefinitionKey());
				}
				JSONObject elements = new JSONObject();
				String defaultVal;
				String defaultValKey = "defaultVal", taskWdataSetKey = "taskWdataSet";
				for (int i = 0; i < rules.size(); i++) {
					JSONObject r = rules.getJSONObject(i);
					if (StringUtils.isNotEmpty(defaultVal = r.getString(defaultValKey))) {
						r.put(defaultValKey, expressionValue(defaultVal));
					}
					if (StringUtils.isNotEmpty(r.getString(taskWdataSetKey))) {
						processDefined.put(taskWdataSetKey, r.getJSONArray(taskWdataSetKey));
					}
					elements.put(r.getString("t"), r);
				}

				String elementsStr = elements.toJSONString();

				elementsStr = TemplateUtils.process(params, elementsStr);

				processDefined.put("elements", JSON.parseObject(elementsStr));
			}
			return false;
		}

		return true;
	}

	protected static Object expressionValue(String expActVal) {
		return ExpressionConvertUtil.expressionConvert(expActVal, ExpressionConvertUtil.JAVASCRIPT_TYPE);
	}

	private Integer getNextVersion(String defId, String pageId) {
		Integer version = 0;
		if (!isNullOrEmpty(defId)) {
			version = this.formWorkflowPlanService.getNextVersionByDefId(defId);
		} else {
			version = this.formWorkFlowRuleService.getNextVersionByPageId(pageId);
		}
		return version - 1;
	}

	/**
	 * 页面初始化赋值(变长区)
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/initPageBatch")
	public byte[] initPageBatch(HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String datasetId = RequestUtils.getString(request, "dataSetId");
		// 分页
		boolean isPage = RequestUtils.getBoolean(request, "isPage");
		int page = RequestUtils.getInt(request, "page", 1);
		int pageSize = RequestUtils.getInt(request, "pageSize", 5);

		if (StringUtils.isBlank(datasetId)) {
			throw new RuntimeException("datasetId is null!");
		}
		String topId = RequestUtils.getString(request, "topId");
		JSONObject result = new JSONObject(params);
		DataSetBuilder builder = new DataSetBuilder();
		String currentSystemName = Environment.getCurrentSystemName();
		try {
			String orderBy = /* "listno" */ null;
			Query query = null;
			if (RequestUtils.getBoolean(request, "cell")) {
				query = builder.buildQuery(datasetId,
						RequestUtils.getString(request, "topTableName") + ".id='" + topId + "'", null, params);
			} else if (StringUtils.isNotBlank(topId)) {

				boolean contains = this.containsParam(params, datasetId);
				if (contains) {
					query = builder.buildQuery(datasetId, null, orderBy, params);
				} else {
					query = builder.buildQuery(datasetId, "topId='" + topId + "'", orderBy, params);
				}

			} else {
				// 判断 如果传递的参数没有则返回null
				// DataSetAudit dataSetAudit =
				// dataSetAuditService.getLastestDataSetAudit(datasetId);
				// int count = 0;
				// if (dataSetAudit != null) {
				// String content = dataSetAudit.getContent();
				// if (StringUtils.isNotBlank(content)) {
				// JSONObject jsonObject = JSON.parseObject(content);
				// if (jsonObject.getString("params") != null) {
				// JSONArray paramsAry =
				// JSON.parseArray(jsonObject.getString("params"));
				// JSONObject paramObj = null;
				// String param = null;
				// for (Object object : paramsAry) {
				// paramObj = (JSONObject) object;
				// param = paramObj.getString("param");
				// if (params.containsKey(param)) {
				// count++;
				// }
				// }
				// }
				// }
				// }

				boolean contains = this.containsParam(params, datasetId);

				if (contains) {
					query = builder.buildQuery(datasetId, null, orderBy, params);
				} else { // TODO 没传条件的时候是否查询所有，还是不查询?
					// return "".getBytes("UTF-8");
					// if(RequestUtils.getBoolean(request,
					// "custom")){//用户手动匹配的数据集
					// query = builder.buildQuery(datasetId, null, "listno",
					// params);
					// } else {
					return "".getBytes("UTF-8");
					// }
				}
			}
			Long databaseId = 0L;
			DataSet ds = dataSetService.getDataSet(datasetId);
			if (ds != null) {
				databaseId = ds.getDatabaseId();
			}
			List<Map<String, Object>> list = new ArrayList<>();
			if (isPage) {
				int total = mutilDatabaseBean.getCountBySql(query, databaseId);
				if (total > 0) {
					result.put("total", total);
					int start = (page - 1) * pageSize;
					if (start > total) {
						start = total;
					}
					list = mutilDatabaseBean.getDataListByQueryCriteria(query.toSql(), start, pageSize, databaseId);
				}
			} else {
				list = mutilDatabaseBean.getDataListBySql(query.toSql(), databaseId);
			}
			result.put("rows", list);
			result.put("ok", true);
		} catch (Exception ex) {
			logger.debug(ex.getMessage());
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	protected boolean containsParam(Map<String, Object> params, String datasetId) {
		boolean rst = false;
		DataSetAudit dataSetAudit = dataSetAuditService.getLastestDataSetAudit(datasetId);
		if (dataSetAudit != null) {
			String content = dataSetAudit.getContent();
			if (StringUtils.isNotBlank(content)) {
				JSONObject jsonObject = JSON.parseObject(content);
				if (jsonObject.getString("params") != null) {
					JSONArray paramsAry = JSON.parseArray(jsonObject.getString("params"));
					JSONObject paramObj = null;
					String param = null;
					for (Object object : paramsAry) {
						paramObj = (JSONObject) object;
						param = paramObj.getString("param");
						if (params.containsKey(param)) {
							rst = true;
						}
					}
				}
			}
		}
		return rst;
	}

	/**
	 * 判断并获取数据库名称
	 * 
	 * @param systemName
	 * @param databaseId
	 * @return
	 */
	private String getSystemName(String systemName, Long databaseId) {
		if ((systemName == null || Environment.DEFAULT_SYSTEM_NAME.equalsIgnoreCase(systemName))
				&& (databaseId != null && databaseId > 0L)) {
			IDatabaseService databaseService = ContextFactory.getBean("databaseService");
			Database database = databaseService.getDatabaseById(databaseId);
			if (database != null) {
				systemName = database.getName();
			}
		}
		return systemName == null ? Environment.DEFAULT_SYSTEM_NAME : systemName;
	}

	private Map<String, String> getRuleMapByPageIdAndEleId(String pageId, String eleId) {
		Map<String, String> ruleMap = null;
		List<FormRule> frs = formRuleService.getRulesByEleId(pageId, eleId);
		if (frs != null && frs.size() > 0) {
			FormRule fr = frs.get(0);
			ruleMap = this.getRuleMap(fr.getId());
			ruleMap.put("__pageRuleId", fr.getId());
		}
		return ruleMap;
	}

	/**
	 * 转换页面参数
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/initPageParameters")
	public byte[] initPageParameters(HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		JSONObject result = new JSONObject(params);

		Map<String, String> ruleMap = null;
		String id = ParamUtils.getString(params, "id");// pageId
		List<FormRule> frs = formRuleService.getRules(id);
		for (FormRule fr : frs) {
			if (fr.getName().equalsIgnoreCase(id)) {
				ruleMap = this.getRuleMap(fr.getId());
				break;
			}
		}
		if (ruleMap != null) {
			// 页面级参数
			String str = ruleMap.get("inParamDefined");
			if (!isNullOrEmpty(str)) {
				String param = null;
				JSONObject jsonObject = null;
				JSONArray jsonArray = JSONArray.parseArray(str);
				Map<String, Object> datas = new HashMap<String, Object>();// 存储参数值,查询结果
				for (int i = 0; i < jsonArray.size(); i++) {
					jsonObject = jsonArray.getJSONObject(i);
					param = jsonObject.getString("param");
					if (params.get(param) != null) {
						datas.put(param, "" + params.get(param) + "");
					}
				}

				String inParameters = ruleMap.get("inParameters");
				if (!isNullOrEmpty(inParameters)) {
					this.initParameters(inParameters, datas, result);
				}

				// 页面输入输出参数对应
				str = ruleMap.get("paraType");
				if (!isNullOrEmpty(str)) {
					jsonArray = JSONArray.parseArray(str);
					str = jsonArray.getJSONObject(0).getString("datas");
					jsonObject = JSON.parseObject(str);
					result.put("controlRule", jsonObject);
				}
			}
		}

		return result.toJSONString().getBytes("UTF-8");
	}

	private boolean initParameters(String inParameters, Map<String, Object> datas, JSONObject result) {
		boolean rst = false;
		if (!isNullOrEmpty(inParameters)) {
			Map<String, List<Map<String, Object>>> tables = new HashMap<String, List<Map<String, Object>>>();
			JSONArray jsonArray0 = JSONArray.parseArray(inParameters);
			if (jsonArray0 != null && !jsonArray0.isEmpty()) {
				for (int i = 0; i < jsonArray0.size(); i++) {
					JSONArray jsonArray = jsonArray0.getJSONObject(i).getJSONArray("collection");
					for (int ii = 0; ii < jsonArray.size(); ii++) {
						JSONObject jsonObject = jsonArray.getJSONObject(ii);
						String strs = jsonObject.getString("paramData");
						String expActVal = JSON.parseObject(strs).getString("expActVal");
						expActVal = ExpressionConvertUtil.expressionConvert(expActVal,
								ExpressionConvertUtil.DATABASE_TYPE);

						if (!isNullOrEmpty(expActVal))
							expActVal = StringUtils.split(expActVal, ".")[0];

						Object val = datas.get(expActVal);
						if (val == null) {
							continue;
						}

						strs = jsonObject.getString("fieldData");
						expActVal = JSON.parseObject(strs).getString("expActVal");
						expActVal = ExpressionConvertUtil.expressionConvert(expActVal,
								ExpressionConvertUtil.DATABASE_TYPE);
						if (!isNullOrEmpty(expActVal)) {
							String fieldName = StringUtils.split(expActVal, ".")[1];
							// if (fieldName.equalsIgnoreCase("id"))
							// continue;
							rst = true;
							String tableName = StringUtils.split(expActVal, ".")[0];
							Map<String, Object> m = new HashMap<String, Object>();
							if (!tables.containsKey(tableName))
								tables.put(tableName, new ArrayList<Map<String, Object>>());
							// String key = tableName + "_0_" + fieldName;
							m.put("fieldName", fieldName);
							m.put("value", val);
							tables.get(tableName).add(m);
						}
					}
				}
			}
			result.put("tables", tables);
		}
		return rst;
	}

	@ResponseBody
	@RequestMapping("/getVideoSetByTraffic")
	public byte[] getVideoSetByTraffic(HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		String rid = params.get("rid") + "";

		JSONObject paramsObj = null;
		if (params.containsKey("params") && params.get("params") != null) {
			paramsObj = JSON.parseObject(params.get("params").toString());
		}

		// String datas = params.get("datas") + "";

		// 获取规则MAP
		Map<String, String> ruleMap = this.getRuleMap(rid);
		if (ruleMap == null) {
			return null;
		}

		// 获取
		String dataSourceSet = ruleMap.get("dataSourceSetByVideos");
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONArray selectDatasourceJSONArray = null;
		JSONObject datasourceJSONObject = null;
		JSONArray selectColumnsJSONArray = null;

		JSONArray retArray = new JSONArray();
		JSONObject result = new JSONObject();
		int count = 0;
		if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
			JSONObject datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
			selectDatasourceJSONArray = datasourceSetJSONObject.getJSONArray("selectDatasource");

			selectColumnsJSONArray = datasourceSetJSONObject.getJSONArray("selectColumns");

			if (selectColumnsJSONArray != null) {
				for (Object json : selectColumnsJSONArray) {
					JSONObject o = (JSONObject) json;
					String rtype = o.getString("rtype");
					if (!isNullOrEmpty(rtype)) {
						result.put(o.getString("columnName"), rtype);
					}
				}
			}

			for (Object object : selectDatasourceJSONArray) {
				datasourceJSONObject = (JSONObject) object;
				if (datasourceJSONObject == null || datasourceJSONObject.size() == 0) {
					continue;
				}
				count++;// 用来计次
				// 构建联动语句 start
				String psql = "";
				// 获取 输入表达式定义
				String inParameters = ruleMap.get("inParameters");
				// 获取页面传递参数
				// String params = dataSourceRequest.getParams();
				psql = ParamsSqlHelper.getParamSql(JSON.toJSONString(params), inParameters,
						datasourceJSONObject.getString("id"));

				if (isNullOrEmpty(psql) && paramsObj == null) {
					continue;
				}

				// 构建联动语句 end
				// 构建sql start
				DataSetBuilder builder = new DataSetBuilder();
				Map<String, Object> parameter = new HashMap<String, Object>();
				parameter.put("HttpServletRequest", request);
				parameter.putAll(paramsObj);
				Query query = builder.buildQuery(datasourceJSONObject.getString("datasetId"), psql, "", parameter);
				String sql = query.toSql();
				// 构建sql end
				// 判断是否服务器分页
				List<Map<String, Object>> dataMaps = new ArrayList<Map<String, Object>>();
				int total = 0;
				// 数据源ID
				Long databaseId = datasourceJSONObject.getLong("databaseId");
				// 执行查询
				dataMaps = mutilDatabaseBean.getDataListBySql(sql, databaseId);
				total = dataMaps.size();
				// 过滤条数
				// dataMaps = mutilDatabaseBean.getDataListByQueryCriteria(sql,
				// 0, 1, databaseId);
				// 返回
				if (total > 0) {
					JSONArray rowsJSON = new JSONArray();

					for (Map<String, Object> map : dataMaps) {
						Set<String> keys = map.keySet();
						Iterator<String> iterator = keys.iterator();
						JSONObject josnObj = new JSONObject();
						while (iterator.hasNext()) {
							String key = (String) iterator.next();
							if (result.containsKey(key)) {
								result.put(result.getString(key), map.get(key).toString());
							}
							if (map.get(key) != null) {
								josnObj.put(key, map.get(key).toString());
							}
						}
						rowsJSON.add(josnObj);
					}
					// result = new JSONObject();
					result.put("id", datasourceJSONObject.getString("id"));
					result.put("data", rowsJSON);
					retArray.add(result);
				}
			}
		}
		if (count > 0) {
			return retArray.toJSONString().getBytes("UTF-8");
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("id", "");
			result.put("data", rowsJSON);
			retArray.add(result);
			return retArray.toJSONString().getBytes("UTF-8");
		}

	}

	@ResponseBody
	@RequestMapping("/getVideoSet")
	public byte[] getVideoSet(HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		String rid = params.get("rid") + "";

		JSONObject paramsObj = null;
		if (params.containsKey("params") && params.get("params") != null) {
			paramsObj = JSON.parseObject(params.get("params").toString());
		}

		// String datas = params.get("datas") + "";

		// 获取规则MAP
		Map<String, String> ruleMap = this.getRuleMap(rid);
		if (ruleMap == null) {
			return null;
		}

		// 获取
		String dataSourceSet = ruleMap.get("dataSourceSetByVideos");
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONArray selectDatasourceJSONArray = null;
		JSONObject datasourceJSONObject = null;
		JSONArray selectColumnsJSONArray = null;

		JSONArray retArray = new JSONArray();
		JSONObject result = new JSONObject();
		int count = 0;
		if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
			JSONObject datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
			selectDatasourceJSONArray = datasourceSetJSONObject.getJSONArray("selectDatasource");

			selectColumnsJSONArray = datasourceSetJSONObject.getJSONArray("selectColumns");

			if (selectColumnsJSONArray != null) {
				for (Object json : selectColumnsJSONArray) {
					JSONObject o = (JSONObject) json;
					String ctype = o.getString("ctype");
					if (!isNullOrEmpty(ctype)) {
						result.put(o.getString("columnName"), ctype);
					}
				}
			}

			for (Object object : selectDatasourceJSONArray) {
				datasourceJSONObject = (JSONObject) object;
				if (datasourceJSONObject == null || datasourceJSONObject.size() == 0) {
					continue;
				}
				count++;// 用来计次
				// 构建联动语句 start
				String psql = "";
				// 获取 输入表达式定义
				String inParameters = ruleMap.get("inParameters");
				// 获取页面传递参数
				// String params = dataSourceRequest.getParams();
				psql = ParamsSqlHelper.getParamSql(JSON.toJSONString(params), inParameters,
						datasourceJSONObject.getString("id"));

				if (isNullOrEmpty(psql) && paramsObj == null) {
					continue;
				}

				// 构建联动语句 end
				// 构建sql start
				DataSetBuilder builder = new DataSetBuilder();
				Map<String, Object> parameter = new HashMap<String, Object>();
				parameter.put("HttpServletRequest", request);
				parameter.putAll(paramsObj);
				Query query = builder.buildQuery(datasourceJSONObject.getString("datasetId"), psql, "", parameter);
				String sql = query.toSql();
				// 构建sql end
				// 判断是否服务器分页
				List<Map<String, Object>> dataMaps = new ArrayList<Map<String, Object>>();
				int total = 0;
				// 数据源ID
				Long databaseId = datasourceJSONObject.getLong("databaseId");
				// 执行查询
				dataMaps = mutilDatabaseBean.getDataListBySql(sql, databaseId);
				total = dataMaps.size();
				// 过滤条数
				// dataMaps = mutilDatabaseBean.getDataListByQueryCriteria(sql,
				// 0, 1, databaseId);
				// 返回
				if (total > 0) {
					JSONArray rowsJSON = new JSONArray();

					for (Map<String, Object> map : dataMaps) {
						Set<String> keys = map.keySet();
						Iterator<String> iterator = keys.iterator();
						JSONObject josnObj = new JSONObject();
						while (iterator.hasNext()) {
							String key = (String) iterator.next();
							if (result.containsKey(key)) {
								result.put(result.getString(key), map.get(key).toString());
							}
							if (map.get(key) != null) {
								josnObj.put(key, map.get(key).toString());
							}
						}
						rowsJSON.add(josnObj);
					}
					// result = new JSONObject();
					result.put("id", datasourceJSONObject.getString("id"));
					result.put("data", rowsJSON);
					retArray.add(result);
				}
			}
		}
		if (count > 0) {
			return retArray.toJSONString().getBytes("UTF-8");
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("id", "");
			result.put("data", rowsJSON);
			retArray.add(result);
			return retArray.toJSONString().getBytes("UTF-8");
		}

	}

	@ResponseBody
	@RequestMapping("/saveFormData")
	public byte[] saveFormData(HttpServletRequest request) throws IOException {
		String tableMsg = RequestUtils.getString(request, "tableMsg");
		/*
		 * LoginContext loginContext = RequestUtils.getLoginContext(request);
		 * String currentSystemName = Environment.getCurrentSystemName();
		 */

		JSONObject result = new JSONObject();
		if (!isNullOrEmpty(tableMsg)) {
			try {
				JSONObject json = null, table;
				JSONArray array = JSONArray.parseArray(tableMsg);
				int i, size = array.size();
				String relation = RequestUtils.getString(request, "relation");

				if (StringUtils.isNotBlank(relation)) {
					JSONArray relationArray = JSON.parseArray(relation);
					Map<String, JSONObject> datasMap = new HashMap<String, JSONObject>();
					for (i = 0; i < size; i++) {
						json = array.getJSONObject(i);
						if (json.containsKey("table")) {
							table = json.getJSONObject("table");
							datasMap.put(table.getString("id"), json);
						}
					}
					this.iteratorSave(relationArray, result, datasMap);
				} else {
					for (i = 0; i < size; i++) {
						json = array.getJSONObject(i);
						Object value = this.saveFormData(json, null);
						result.put(json.getJSONObject("table").getString("tableName"), value);
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				result.put("rst", false);
				result.put("message", e.getMessage());
				result.put("statusCode", 500);
				return result.toJSONString().getBytes("UTF-8");
			}
		} else {
			result.put("rst", false);
			result.put("message", "未定义保存设置。");
			result.put("statusCode", 500);
			return result.toJSONString().getBytes("UTF-8");
		}

		result.put("rst", true);
		result.put("message", "操作成功!");
		result.put("statusCode", 200);
		return result.toJSONString().getBytes("UTF-8");
	}

	public Object saveFormData(JSONObject json, Map<String, Object> other) {
		Object value = null;
		if (json == null) {
			return value;
		}
		Long id = json.getLong("id");
		JSONArray dels = json.getJSONArray("dels");
		if (dels != null && !dels.isEmpty()) {
			List<Map<String, Object>> delParams = new ArrayList<Map<String, Object>>();
			for (Object object : dels) {
				delParams.add(new HashMap<String, Object>((JSONObject) object));
			}
			// depBaseWdataSetService.execRemove(id, delParams);
			DataProcessFactory.getInstance().execRemove(id, delParams);
		}
		Map<String, Object> param;
		List<Map<String, Object>> params = new ArrayList<Map<String, Object>>();
		JSONArray datas = json.getJSONArray("datas");
		int i = 0, size = datas.size();
		for (; i < size; i++) {
			JSONObject job = datas.getJSONObject(i);
			param = new HashMap<String, Object>(job);
			if (other != null)
				param.putAll(other);
			params.add(param);
		}
		// depBaseWdataSetService.execBatch(id, params);
		DataProcessFactory.getInstance().execBatch(id, params);

		/**
		 * 变长区id 保存
		 */
		for (i = 0; i < size; i++) {
			param = params.get(i);
			if (param.containsKey("batchId")) {
				other.put(MapUtils.getString(param, "batchId"), //
						this.getIdValue(Arrays.asList(param)));
			}
		}

		return value = this.getIdValue(params);
	}

	private Object getIdValue(List<Map<String, Object>> params) {
		Object value = null;
		Map<String, Object> param;
		int i = 0, size = params.size();
		h1: for (; i < size; i++) {
			param = params.get(i);
			if (param != null) {
				for (String key : param.keySet()) {
					if (StringUtils.startsWithIgnoreCase(key, "id")) {
						value = param.get(key);
						break h1;
					}
				}
			}
		}
		return value;
	}

	public void iteratorSave(JSONArray array, Map<String, Object> tableMap, Map<String, JSONObject> datasMap) {
		String id, topId;
		Object value;
		String childrenKey = "children", batchIdsKey = "batchIds";
		JSONObject json = null;
		JSONArray children = null;
		Map<String, Object> param;
		if (tableMap == null) {
			tableMap = new HashMap<String, Object>();
		}
		int i = 0, size = array.size();
		for (; i < size; i++) {
			json = array.getJSONObject(i);
			id = json.getString("id");
			topId = json.getString("topId");
			param = null;
			param = new HashMap<String, Object>();
			if ((value = tableMap.get(topId)) != null) {
				param.put("topid", value);
			}
			value = this.saveFormData(datasMap.get(id), param);

			/**
			 * 保存变长区Id
			 */
			if (param != null) {
				if (!tableMap.containsKey(batchIdsKey)) {
					tableMap.put(batchIdsKey, new HashMap<String, Object>());
				}
				MapUtils.getMap(tableMap, batchIdsKey).putAll(param);
			}
			tableMap.put(id, value);
			if (json.containsKey(childrenKey)) {
				children = json.getJSONArray(childrenKey);
				if (children.size() > 0) {
					this.iteratorSave(children, tableMap, datasMap);
				}
			}
		}
	}

	@ResponseBody
	@RequestMapping("/gridDataServer")
	public byte[] saveServer(HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String tableName = RequestUtils.getString(request, "tableName");
		String databaseId = RequestUtils.getString(request, "databaseId");

		FormTableOperateUtils formtable = new FormTableOperateUtils();
		List<Map<String, Object>> list = formtable.queryTable(databaseId, "select * from " + tableName);
		JSONArray jsonObject = ListToJsonArray(list);

		return jsonObject.toJSONString().getBytes("UTF-8");
	}

	/**
	 * @param request
	 * @return
	 * @throws IOException
	 *             系统服务-复制功能（单表复制）
	 */
	@ResponseBody
	@RequestMapping("/saveDataServer")
	public byte[] saveDataServer(HttpServletRequest request) throws IOException {
		FormTableOperateUtils formtable = new FormTableOperateUtils();
		String tableName = RequestUtils.getString(request, "tableName"); // 表名
		String databaseId = RequestUtils.getString(request, "databaseId"); // 数据库ID
		String indexId = RequestUtils.getString(request, "indexId"); // indexId
		String t_indexId = RequestUtils.getString(request, "tindexId"); // 目标indexId
		String indexKey = RequestUtils.getString(request, "indexKey"); // indexId所属字段
		String parentKey = RequestUtils.getString(request, "parentKey"); // 父节点ID所属字段
		String treeKey = RequestUtils.getString(request, "treeKey"); // 树Id
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(); //
		List<Map<String, Object>> tlist = new ArrayList<Map<String, Object>>();
		String treeid = "";
		String sql = "select * from " + tableName;
		int count = formtable.queryCount(databaseId, sql).intValue(); // 查询表数据数量
		if (StringUtils.isNotEmpty(t_indexId)) {
			StringTokenizer tokentIndex = new StringTokenizer(t_indexId, ",");
			while (tokentIndex.hasMoreTokens()) {
				String tindex = tokentIndex.nextToken();
				if (StringUtils.isNotEmpty(tindex) && StringUtils.isNotEmpty(indexId)) {
					StringTokenizer token = new StringTokenizer(indexId, ",");
					while (token.hasMoreTokens()) {
						String tsql = "select  max(index_id) as 'index_id' from " + tableName;
						list = formtable.queryTable(databaseId, tsql);
						for (Map<String, Object> map : list) {
							count = Integer.valueOf(String.valueOf((map.get("index_id"))));
						}
						String index = token.nextToken();
						String tsql1 = sql + " where index_id=" + index;
						tlist = formtable.queryTable(databaseId, tsql1);
						dataSetMapListServer(tlist, count, parentKey, treeKey, indexKey, tindex, treeid, 2, formtable,
								sql, databaseId, tableName);
					}
				}
			}
		} else {
			if (StringUtils.isNotEmpty(indexId)) {
				// 复制ID
				if (StringUtils.isNotEmpty(indexId)) {
					StringTokenizer token = new StringTokenizer(indexId, ",");
					while (token.hasMoreTokens()) {
						String tsql = "select  max(index_id) as 'index_id' from " + tableName;
						list = formtable.queryTable(databaseId, tsql);
						for (Map<String, Object> map : list) {
							count = Integer.valueOf(String.valueOf((map.get("index_id"))));
						}
						String index = token.nextToken();
						String tsql1 = sql + " where index_id=" + index;
						tlist = formtable.queryTable(databaseId, tsql1);
						dataSetMapListServer(tlist, count, parentKey, treeKey, indexKey, null, treeid, 2, formtable,
								sql, databaseId, tableName);

					}
				}
			}
		}
		return ResponseUtils.responseResult(true);
	}

	public void dataSetMapListServer(List<Map<String, Object>> list, int count, String parentKey, String treeKey,
			String indexKey, String parentid, String treeid, int type, FormTableOperateUtils formtable, String sql,
			String databaseId, String tableName) {

		int paramCount = type == 1 || type == 4 ? count : 0;
		for (Map<String, Object> map : list) {
			List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
			count = count + Integer.valueOf(1);
			int counts = count;
			String maxsql = "select max(index_id) as 'index_id' from " + tableName;
			List<Map<String, Object>> list5 = formtable.queryTable(databaseId, maxsql);
			for (Map<String, Object> map5 : list5) {
				counts = Integer.valueOf(String.valueOf((map5.get("index_id")))) + 1;
			}

			String ztreeid = map.get(treeKey) != null ? String.valueOf(map.get(treeKey)) : null;
			String pparentid = map.get(parentKey) != null ? String.valueOf(map.get(parentKey)) : null;
			pparentid = parentid != null && parentid != "" ? parentid : pparentid;
			String indexid = String.valueOf(map.get(indexKey));
			String tsql = sql + " where parent_id=" + map.get(indexKey) + " order by index_id";
			if (type == 0) {
				String param = ztreeid.substring(0, ztreeid.indexOf(pparentid) + 1);
				param = ztreeid.replace(param, treeid);
				map.put(treeKey, param + "|" + count + "|");
				map.put(parentKey, parentid);
				map.put(indexKey, count);
				map.put("ID", count);
			} else if (type == 2) {
				if (Integer.valueOf(pparentid) != -1 && pparentid != null) {
					String param = ztreeid.replace("|" + map.get(indexKey), "|" + count + "");
					map.put(treeKey, param);
					map.put(indexKey, count);
					map.put("ID", count);
				} else {
					String param = ztreeid.replace(ztreeid, counts + "|");
					map.put(treeKey, param);
					map.put(indexKey, count);
					map.put("ID", count);
				}
			} else if (type == 1) {
				String param = ztreeid.substring(0, ztreeid.indexOf(pparentid) + 1);
				param = ztreeid.replace(param, ztreeid);
				map.put(treeKey, param + "|" + count + "|");
				map.put(parentKey, parentid);
				map.put(indexKey, count);
				map.put("ID", count);
			} else if (type == 4) {

				map.put(treeKey, treeid + "" + counts + "|");
				map.put(parentKey, paramCount);
				map.put(indexKey, counts);
				map.put("ID", counts);
			}
			list2.add(map);

			List<Map<String, Object>> list3 = formtable.queryTable(databaseId, tsql);
			if (list3.size() > 0) {
				formtable.bulkInsertTableAuto(databaseId, tableName, list2);
				dataSetMapListServer(list3, counts, parentKey, treeKey, indexKey, indexid,
						String.valueOf(map.get("treeid")), 4, formtable, sql, databaseId, tableName);
			} else {
				formtable.bulkInsertTableAuto(databaseId, tableName, list2);
			}
		}
	}

	@ResponseBody
	@RequestMapping("/saveOrUpdateDetail")
	public byte[] saveOrUpdateDetail(HttpServletRequest request) throws IOException {
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		// RequestUtils.getString:根据"tableMsg"获取参数值，判断为不为空，如果为空返回null，如果不为空，则将控件的值以，链接起来，在转化成字符创返回出来；
		// tableMsg：即拿到页面上的参数
		String tableMsg = RequestUtils.getString(request, "tableMsg");
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String currentSystemName = Environment.getCurrentSystemName();
		if (!isNullOrEmpty(tableMsg)) {
			// 转化成json数组
			JSONArray array = JSONArray.parseArray(tableMsg);
			JSONObject result = new JSONObject();
			ColumnModel masterID = null;
			List<TableModel> tms = new ArrayList<TableModel>();
			Map<String, String> topIdMap = new HashMap<String, String>();
			for (int i = 0; i < array.size(); i++) {
				JSONObject json = array.getJSONObject(i);
				String dataSetId = json.getString("dataSetId");
				if (!isNullOrEmpty(dataSetId)) {
					DataSet ds = dataSetService.getDataSet(dataSetId);
					IDatabaseService databaseService = ContextFactory.getBean("databaseService");
					Database database = databaseService.getDatabaseById(ds.getDatabaseId());
					if (database != null) {
						Environment.setCurrentSystemName(database.getName());
					}
				}
				if (!isNullOrEmpty(json.getString("delete"))) {// 删除明细(部分)
					String id = json.getString("id");
					if (id != null) {
						TableModel tm = new TableModel();
						tm.setTableName(json.getString("tableName"));
						tm.addColumn("id", "String", id);
						tm.setIdColumn(new ColumnModel("id", "String", id));
						DataServiceFactory.getInstance().deleteTableData(tm);
					}
					continue;
				}
				TableModel tm = this.getTableModel(json);
				if (tm.getIdColumn() == null) {
					this.setOtherField(tm);// 设置公共字段
					if (!isNullOrEmpty(tm.getPrimaryKey())) {

						final String id = idGenerator.getNextId(tm.getTableName(), "id", loginContext.getActorId());

						String children = tm.getPrimaryKey(); // 保存从表 topId
						try {
							List<String> cls = JSONArray.parseArray(children, String.class);
							for (String c : cls) {
								topIdMap.put(c, id);
								topIdMap.put(c.toUpperCase(), id);
								topIdMap.put(c.toLowerCase(), id);
							}
						} catch (Exception ex) {
							ex.printStackTrace();
							logger.error(ex.getMessage());
							throw ex;
						}

						masterID = new ColumnModel();
						masterID.setJavaType("String");
						masterID.setColumnName("id");
						// String tablename, String idColumn, String createBy
						masterID.setValue(id);
						tm.addColumn(masterID);
						result.put(tm.getTableName().toLowerCase(), new HashMap<String, Object>() {
							/**
							* 
							*/
							private static final long serialVersionUID = 1L;

							{
								put("idValue", id);
							}
						});
					}
				}
				tms.add(tm);
			}

			for (TableModel tm : tms) {
				try {
					if (tm.getIdColumn() == null) { // 增加
						if (isNullOrEmpty(tm.getPrimaryKey()) || topIdMap.containsKey(tm.getTableName())) { // 从表

							if (topIdMap.containsKey(tm.getTableName())) {

								ColumnModel topIdField = new ColumnModel("topId", "String",
										topIdMap.get(tm.getTableName()));

								tm.removeColumn(topIdField);

								tm.addColumn(topIdField);
							}

							final String id = idGenerator.getNextId(tm.getTableName(), "id", loginContext.getActorId());
							ColumnModel idField = new ColumnModel();
							idField.setJavaType("String");
							idField.setColumnName("id");
							idField.setValue(id);
							tm.addColumn(idField);
							tm.setPrimaryKey("topId");

							result.put(tm.getTableName().toLowerCase(), new HashMap<String, Object>() {

								/**
								* 
								*/
								private static final long serialVersionUID = 1L;

								{
									put("idValue", id);
								}
							});
						}
						DataServiceFactory.getInstance().insertTableData(tm);
					} else {
						DataServiceFactory.getInstance().updateTableData(tm);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error(ex.getMessage());
					throw ex;
				} finally {
					Environment.setCurrentSystemName(currentSystemName);
				}
			}
			return result.toJSONString().getBytes("UTF-8");
		}
		return ResponseUtils.responseResult(false);
	}

	private TableModel getTableModel(JSONObject json) {
		if (json != null) {
			JSONObject table = json.getJSONObject("table");
			JSONArray array = json.getJSONArray("columns");
			if (table != null && array != null && array.size() > 0) {
				TableModel tableModel = new TableModel();
				tableModel.setTableName(table.getString("tableName"));
				if (table.getString("isMaster") != null && table.getString("isMaster").equalsIgnoreCase("true")) { // 主表
					tableModel.setPrimaryKey(table.getString("children"));
				}
				if (!isNullOrEmpty(json.getString("idValue"))) {
					ColumnModel idColumn = new ColumnModel();
					idColumn.setColumnName("id");
					idColumn.setJavaType("String");
					idColumn.setValue(json.getString("idValue"));
					tableModel.setIdColumn(idColumn);
				}
				Object value;
				for (int i = 0; i < array.size(); i++) {
					JSONObject j = array.getJSONObject(i);
					if (!j.containsKey("fieldName")) {
						continue;
					}
					Class<?> cls = getKlass(j.getString("dType"));
					value = j.get("value");
					tableModel.addColumn(j.getString("fieldName"), cls.getSimpleName(),
							value == null ? value
									: (cls.getName().equalsIgnoreCase("java.lang.Boolean")
											? j.getObject("value", String.class) : j.getObject("value", cls)));
				}
				return tableModel;
			}
		}
		return null;
	}

	/**
	 * 公共字段 set set set
	 * 
	 * @param tableModel
	 */
	private void setOtherField(TableModel tableModel) {
		tableModel.addColumn("ctime", "Date", new Date());
	}

	private static volatile Map<String, Class<?>> dTypeMap;

	public static Class<?> getKlass(String dType) {

		if (dTypeMap == null) {
			dTypeMap = new HashMap<String, Class<?>>();

			IFormDictoryService formDictoryService = (IFormDictoryService) BaseDataManager.getInstance()
					.getBean("formDictoryService");

			List<FormDictory> list = formDictoryService.getFormDictoryList("dType");

			if (list != null) {
				for (FormDictory bdi : list) {
					try {
						dTypeMap.put(bdi.getCode(), Class.forName(bdi.getDesc()));
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}

		if (dTypeMap.containsKey(dType)) {
			return dTypeMap.get(dType);
		}
		return Object.class;
	}

	/**
	 * 获取引用参数
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/getQuoteDatas")
	public byte[] getQuoteDatas(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String dataSetId = RequestUtils.getString(request, "dataSetId");
		String params = RequestUtils.getString(request, "params");

		DataSetBuilder builder = new DataSetBuilder();
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("HttpServletRequest", request);
		if (!isNullOrEmpty(params)) {
			parameter.putAll(JSON.parseObject(params));
		}
		Query query = builder.buildQuery(dataSetId, "", "", parameter);
		String sql = query.toSql();

		List<Map<String, Object>> dataMaps = mutilDatabaseBean.getDataListBySql(sql, 0l);
		if (dataMaps != null && dataMaps.size() > 0) {
			for (Map<String, Object> map : dataMaps) {
				for (String key : map.keySet()) {
					return map.get(key).toString().getBytes("UTF-8");
				}
			}
		}

		return "".getBytes("UTF-8");
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setFormRulePropertyService(FormRulePropertyService formRulePropertyService) {
		this.formRulePropertyService = formRulePropertyService;
	}

	@javax.annotation.Resource
	public void setMutilDatabaseBean(MutilDatabaseBean mutilDatabaseBean) {
		this.mutilDatabaseBean = mutilDatabaseBean;
	}

	@javax.annotation.Resource
	public void setFormRuleService(FormRuleService formRuleService) {
		this.formRuleService = formRuleService;
	}

	@javax.annotation.Resource
	public void setDataSetService(DataSetService dataSetService) {
		this.dataSetService = dataSetService;
	}

	@javax.annotation.Resource
	public void setActivitiTaskQueryService(ActivitiTaskQueryService activitiTaskQueryService) {
		this.activitiTaskQueryService = activitiTaskQueryService;
	}

	@javax.annotation.Resource
	public void setFormWorkFlowRuleService(FormWorkFlowRuleService formWorkFlowRuleService) {
		this.formWorkFlowRuleService = formWorkFlowRuleService;
	}

	@javax.annotation.Resource
	public void setFormDictoryService(IFormDictoryService formDictoryService) {
		this.formDictoryService = formDictoryService;
	}

	@javax.annotation.Resource
	public void setActReBusinessService(ActReBusinessService actReBusinessService) {
		this.actReBusinessService = actReBusinessService;
	}

	@javax.annotation.Resource
	public void setFormWorkflowPlanService(FormWorkflowPlanService formWorkflowPlanService) {
		this.formWorkflowPlanService = formWorkflowPlanService;
	}

	@javax.annotation.Resource
	public void setDepBaseWdataSetService(DepBaseWdataSetService depBaseWdataSetService) {
		this.depBaseWdataSetService = depBaseWdataSetService;
	}

	@javax.annotation.Resource
	public void setFormTaskmainService(FormTaskmainService formTaskmainService) {
		this.formTaskmainService = formTaskmainService;
	}

	/**
	 * 下发数据 （整表下发）
	 * 
	 * @param request
	 * @param dataSourceRequest
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/sendDown")
	public @ResponseBody byte[] sendDown(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 源标段 只有一个
		long sourceTenders = RequestUtils.getLong(request, "sourceTenders", 0);
		Database sourceDatabase = databaseService.getDatabaseById(sourceTenders);
		String databaseType = sourceDatabase.getType();
		// 目标标段 可能多个 用,分隔
		String targetTenders = RequestUtils.getString(request, "targetTenders", "");
		String[] targetTenderAry = targetTenders.split(",");
		// 表名 可能多个 用,分隔
		String sendData = RequestUtils.getString(request, "sendData", "");
		if (!StringUtils.startsWithIgnoreCase(sendData, "CELL")) {
			return ResponseUtils.responseJsonResult(false, "只能对CELL表做下发");
		}
		String[] sendDataAry = sendData.split(",");
		if (sendDataAry.length != 1) {
			return ResponseUtils.responseJsonResult(false, "只能单表下发");
		}
		if (DBUtils.SQLSERVER.equalsIgnoreCase(databaseType)) {
			String sourceIp = sourceDatabase.getHost();
			String sourceDbName = sourceDatabase.getDbname();
			String sourceUser = sourceDatabase.getUser();
			String sourcePass = SecurityUtils.decode(sourceDatabase.getKey(), sourceDatabase.getPassword());
			// 0:目标数据库表名 1:源数据库ip地址 2:源数据库用户名 3:源数据库密码 4:源数据库实例名称 5:源数据库表名
			String sql = "select * into %s from opendatasource('SQLOLEDB','Data Source=%s;User ID=%s; Password=%s').%s.dbo.%s";
			// 0/1：为表名 2：时间戳
			String bakTable = "EXEC sp_rename '%s' , '%s_%s'";
			sql = sql.format(sql, "%s", sourceIp, sourceUser, sourcePass, sourceDbName, "%s");
			PreparedStatement psmt = null;
			Connection conn = null;
			Database targetDatabase = null;
			try {
				for (String targetTender : targetTenderAry) {
					if (StringUtils.isNotEmpty(targetTender)) {
						for (String tableName : sendDataAry) {
							targetDatabase = databaseService.getDatabaseById(Long.parseLong(targetTender));
							conn = DBConnectionFactory.getConnection(targetDatabase.getName());

							psmt = conn.prepareStatement(
									bakTable.format(bakTable, tableName, tableName, new Date().getTime()));
							psmt.executeUpdate();

							psmt = conn.prepareStatement(sql.format(sql, tableName, tableName));
							psmt.executeUpdate();
						}
					}
				}
				conn.commit();
			} catch (Exception e) {
				e.printStackTrace();
				JdbcUtils.close(psmt);
				JdbcUtils.close(conn);
				logger.error(e.getMessage());
				logger.info("目标数据库开启Ad Hoc Distributed Queries重试....");
				// 如果没有开启 则开启Ad Hoc Distributed Queries 配置
				String openS = "sp_configure 'show advanced options', 1;RECONFIGURE;";
				String openQ = "sp_configure 'Ad Hoc Distributed Queries', 1;RECONFIGURE;";
				Connection conne = null;
				PreparedStatement psmts = null;
				try {
					for (String targetTender : targetTenderAry) {
						boolean isExec = false;
						if (StringUtils.isNotEmpty(targetTender)) {
							for (String tableName : sendDataAry) {
								targetDatabase = databaseService.getDatabaseById(Long.parseLong(targetTender));
								conn = DBConnectionFactory.getConnection(targetDatabase.getName());
								if (!isExec) {
									// 开启Ad Hoc Distributed Queries配置
									// Connection conne =
									// DBConnectionFactory.getConnection(targetDatabase.getName());
									try {
										conne = DBConnectionFactory.getConnection(targetDatabase.getName());
										conne.setAutoCommit(true);
										// PreparedStatement psmts =
										// conne.prepareStatement(openS);
										psmts = conne.prepareStatement(openS);
										psmts.executeUpdate();

										psmts = conne.prepareStatement(openQ);
										psmts.executeUpdate();
										// psmts.close();
										// conne.close();
										isExec = true;
									} catch (Exception ex) {
										logger.error(ex.getMessage());
									} finally {
										JdbcUtils.close(psmts);
										JdbcUtils.close(conne);
									}
								}

								psmt = conn.prepareStatement(
										bakTable.format(bakTable, tableName, tableName, new Date().getTime()));
								psmt.executeUpdate();

								psmt = conn.prepareStatement(sql.format(sql, tableName, tableName));
								psmt.executeUpdate();
							}
						}
					}
					conn.commit();
				} catch (Exception e2) {
					e2.printStackTrace();
					logger.error(e2.getMessage());
					return ResponseUtils.responseJsonResult(false, e2.getMessage());
				}
			} finally {
				JdbcUtils.close(psmt);
				JdbcUtils.close(conn);
			}
		} else {
			Connection targetConn = null;
			try {
				String querySql = " select * from %s ";
				long targetDatabaseId = Long.parseLong(targetTenders);
				TableDefinition tableDefinition = new TableDefinition();
				tableDefinition.setTableName(sendData);
				tableDefinition.setColumns(TableFactory.getColumnDefinitions(targetDatabaseId, sendData));

				List<Map<String, Object>> datas = mutilDatabaseBean
						.getDataListBySql(querySql.format(querySql, sendData), sourceDatabase.getId());

				Database targetDatabase = databaseService.getDatabaseById(targetDatabaseId);
				targetConn = DBConnectionFactory.getConnection(targetDatabase.getName());
				mutilDatabaseBean.deleteTableByWhereCause(sendData, "", targetDatabaseId);
				targetConn.setAutoCommit(false);
				BulkInsertBean bulkInsertBean = new BulkInsertBean();
				bulkInsertBean.bulkInsert(targetConn, tableDefinition, datas);
				targetConn.commit();

			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				return ResponseUtils.responseJsonResult(false, e.getMessage());
			} finally {
				JdbcUtils.close(targetConn);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	/**
	 * 下发数据
	 * 
	 * @param request
	 * @param dataSourceRequest
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/sendDown2")
	public @ResponseBody byte[] sendDown2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 源标段 只有一个
		long sourceTenders = RequestUtils.getLong(request, "sourceTenders", 0);
		Database sourceDatabase = databaseService.getDatabaseById(sourceTenders);
		String databaseType = sourceDatabase.getType();
		// 目标标段 可能多个 用,分隔
		String targetTenders = RequestUtils.getString(request, "targetTenders", "");
		String[] targetTenderAry = targetTenders.split(",");

		// 为json对象 {"表名-主键":"值,值"}
		String sendData = RequestUtils.getString(request, "sendData", null);

		if (DBUtils.SQLSERVER.equalsIgnoreCase(databaseType)) {
			String sourceIp = sourceDatabase.getHost();
			String sourceDbName = sourceDatabase.getDbname();
			String sourceUser = sourceDatabase.getUser();
			String sourcePass = SecurityUtils.decode(sourceDatabase.getKey(), sourceDatabase.getPassword());

			String searchTableColumnsSchema = "select name from sys.columns where object_id = object_id('%s')";
			if (sendData != null && !"".equals(sendData)) {
				// 获取表字段的信息 0: 表名
				// 0:目标数据库表名 2:主键
				String delSql = "delete %s where %s in (%s)";
				// 0:目标数据库表名 1:列查询字段 2:源数据库ip地址 3:源数据库用户名 4:源数据库密码 5:源数据库实例名称
				// 6:源数据库表名 7：主键
				String insertSql = "insert into %s select %s from opendatasource('SQLOLEDB','Data Source=%s;User ID=%s; Password=%s').%s.dbo.%s where %s in (%s)";
				insertSql = String.format(insertSql, "%s", "%s", sourceIp, sourceUser, sourcePass, sourceDbName,
						"%s", "%s", "%s");

				PreparedStatement psmt = null;
				Connection conn = null;
				Database targetDatabase = null;

				PreparedStatement sourcePsmt = null;
				Connection sourceConn = null;

				JSONObject sendDataObj = JSON.parseObject(sendData);
				Set<String> set = sendDataObj.keySet();
				for (String key : set) {
					String[] keys = key.split("-");
					String tableName = keys[0];
					String primaryKey = keys[1];

					String columnStr = "";

					String[] sendDataAry = sendDataObj.getString(key).split(",");
					int len = sendDataAry.length;
					List<String> paramAry = new ArrayList<>();
					for (int i = 1; i <= len; i++) {
						paramAry.add("?");
					}
					String params = ListUtil.joinList(paramAry, ",");
					if (!params.isEmpty()) {
						try {
							sourceConn = DBConnectionFactory.getConnection(sourceDatabase.getName());
							sourcePsmt = sourceConn.prepareStatement(
									String.format(searchTableColumnsSchema, tableName));
							ResultSet executeQuery = sourcePsmt.executeQuery();

							while (executeQuery.next()) {
								columnStr += executeQuery.getString(1) + ",";
							}
							columnStr = columnStr.substring(0, columnStr.length() - 1);

							for (String targetTender : targetTenderAry) {
								if (StringUtils.isNotEmpty(targetTender)) {
									targetDatabase = databaseService.getDatabaseById(Long.parseLong(targetTender));
									conn = DBConnectionFactory.getConnection(targetDatabase.getName());

									psmt = conn.prepareStatement(String.format(delSql, tableName, primaryKey, params));
									for (int i = 1; i <= len; i++) {
										psmt.setString(i, sendDataAry[i - 1]);
									}
									psmt.executeUpdate();
									String iSql = String.format(insertSql, tableName + " (" + columnStr + ") ",
											columnStr, tableName, primaryKey, params);
									logger.info("下发执行sql-->" + iSql);
									psmt = conn.prepareStatement(iSql);
									for (int i = 1; i <= len; i++) {
										psmt.setString(i, sendDataAry[i - 1]);
									}
									psmt.executeUpdate();

								}
							}
							conn.commit();
						} catch (SQLException e) {
							e.printStackTrace();
							JdbcUtils.close(psmt);
							JdbcUtils.close(conn);
							JdbcUtils.close(sourceConn);
							JdbcUtils.close(sourcePsmt);
							logger.error(e.getMessage());
							logger.info("目标数据库开启Ad Hoc Distributed Queries重试....");
							// 如果没有开启 则开启Ad Hoc Distributed Queries 配置
							String openS = "sp_configure 'show advanced options', 1;RECONFIGURE;";
							String openQ = "sp_configure 'Ad Hoc Distributed Queries', 1;RECONFIGURE;";
							Connection conne = null;
							PreparedStatement psmts = null;
							try {
								for (String targetTender : targetTenderAry) {
									boolean isExec = false;
									if (StringUtils.isNotEmpty(targetTender)) {
										targetDatabase = databaseService.getDatabaseById(Long.parseLong(targetTender));
										conn = DBConnectionFactory.getConnection(targetDatabase.getName());
										if (!isExec) {
											// 开启Ad Hoc Distributed Queries配置
											// Connection conne =
											// DBConnectionFactory.getConnection(targetDatabase.getName());
											try {
												conne = DBConnectionFactory.getConnection(targetDatabase.getName());
												conne.setAutoCommit(true);
												// PreparedStatement psmts =
												// conne.prepareStatement(openS);
												psmts = conne.prepareStatement(openS);
												psmts.executeUpdate();

												psmts = conne.prepareStatement(openQ);
												psmts.executeUpdate();
												// psmts.close();
												// conne.close();
												isExec = true;
											} catch (Exception ex) {
												logger.error(ex.getMessage());
											} finally {
												JdbcUtils.close(psmts);
												JdbcUtils.close(conne);
											}
										}

										psmt = conn
												.prepareStatement(String.format(delSql, tableName, primaryKey, params));
										for (int i = 1; i <= len; i++) {
											psmt.setString(i, sendDataAry[i - 1]);
										}
										psmt.executeUpdate();
										String iSql = String.format(insertSql, tableName + " (" + columnStr + ") ",
												columnStr, tableName, primaryKey, params);
										logger.info("下发执行sql-->" + iSql);
										psmt = conn.prepareStatement(iSql);
										for (int i = 1; i <= len; i++) {
											psmt.setString(i, sendDataAry[i - 1]);
										}
										psmt.executeUpdate();
									}
								}
								conn.commit();
							} catch (Exception e2) {
								e2.printStackTrace();
								logger.error(e2.getMessage());
								return ResponseUtils.responseJsonResult(false, e2.getMessage());
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.error(e.getMessage());
							return ResponseUtils.responseJsonResult(false, e.getMessage());
						} finally {
							JdbcUtils.close(psmt);
							JdbcUtils.close(conn);
							JdbcUtils.close(sourceConn);
							JdbcUtils.close(sourcePsmt);
						}

					}
				}

			}
		} else {
			PreparedStatement sourcePsmt = null;
			Connection sourceConn = null;
			String delWhereSqlTemp = " %s in (%s) ";
			String selectSql = " select * from %s where " + delWhereSqlTemp;
			String delWhereSql = null;
			String inSql = null;
			Long targetDatabaseId = null;
			Connection targetConn = null;
			try {
				JSONObject sendDataObj = JSON.parseObject(sendData);
				Set<String> set = sendDataObj.keySet();
				for (String key : set) {
					String[] keys = key.split("-");
					String tableName = keys[0];
					String primaryKey = keys[1];
					String value = sendDataObj.getString(key);
					if (StringUtils.isEmpty(value)) {
						continue;
					}
					String[] sendDataAry = value.split(",");
					List<String> paramAry = new ArrayList<>();
					for (String sendDataStr : sendDataAry) {
						paramAry.add("'" + sendDataStr + "'");
					}
					inSql = ListUtil.joinList(paramAry, ",");
					String querySql = String.format(selectSql, tableName, primaryKey, inSql);
					logger.info("下发查询数据sql-->" + querySql);
					List<Map<String, Object>> datas = mutilDatabaseBean.getDataListBySql(querySql,
							sourceDatabase.getId());
					delWhereSql = " and " + String.format(delWhereSqlTemp, primaryKey, inSql);
					for (String targetTender : targetTenderAry) {
						targetDatabaseId = Long.parseLong(targetTender);
						// 清理数据
						mutilDatabaseBean.deleteTableByWhereCause(tableName, delWhereSql, targetDatabaseId);
						// 插入数据
						TableDefinition tableDefinition = new TableDefinition();
						tableDefinition.setTableName(tableName);
						tableDefinition.setColumns(TableFactory.getColumnDefinitions(targetDatabaseId, tableName));

						Database targetDatabase = databaseService.getDatabaseById(targetDatabaseId);
						targetConn = DBConnectionFactory.getConnection(targetDatabase.getName());

						targetConn.setAutoCommit(false);
						BulkInsertBean bulkInsertBean = new BulkInsertBean();
						bulkInsertBean.bulkInsert(targetConn, tableDefinition, datas);
						targetConn.commit();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				return ResponseUtils.responseJsonResult(false, e.getMessage());
			} finally {
				JdbcUtils.close(targetConn);
			}
		}

		return ResponseUtils.responseJsonResult(true);
	}

	/**
	 * CRUD
	 * 
	 * @param request
	 * @param dataSourceRequest
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/mtcrud")
	public @ResponseBody byte[] mtcrud(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 更新集 id
		long useId = RequestUtils.getLong(request, "useId", 0);
		// 传入的参数
		String params = RequestUtils.getString(request, "params", null);
		// 操作类型
		String type = RequestUtils.getString(request, "type", "cu");

		try {
			if (StringUtils.isNotEmpty(params)) {
				JSONObject paramsObj = JSON.parseObject(params);
				// 防止sql注入
				InjectUtils.escapeSql(paramsObj);
				InjectUtils.escapeHtml(paramsObj);
				if (!paramsObj.isEmpty()) {

					List<Map<String, Object>> paramList = new ArrayList<>();
					// 批处理
					Boolean isBatch = paramsObj.getBoolean("isBatch");
					if (isBatch != null && isBatch) {
						String[] exclude = { "isBatch", "isAlt" };
						List<String> excludes = Arrays.asList(exclude);
						Set<String> keys = paramsObj.keySet();
						keys.removeAll(excludes);
						String[] nodes = null;
						if (keys.size() == 0) {
							return ResponseUtils.responseJsonResult(false, "没有字段形参！");
						}

						// 获取最大行
						int maxLen = keys.stream().mapToInt((y) -> {
							String paramValue = paramsObj.getString(y); // 参数值
							try {
								// 转换为json数组的格式
								JSONArray paramValueAry = JSON.parseArray(paramValue);
								if (paramValueAry == null) {
									throw new JSONException();
								}
								return paramValueAry.size();
							} catch (JSONException e1) {
								return paramsObj.getString(y).split(",").length;
							}
						}).summaryStatistics().getMax();
						for (int i = 0; i < maxLen; i++) {
							paramList.add(new HashMap<>());
						}
						Map<String, Object> map = null;
						int nodeLen = 0;
						String paramValue = null; // 参数值
						JSONArray paramValueAry = null; // 参数的json格式
						for (String key : keys) {
							paramValue = paramsObj.getString(key);
							try {
								// 转换为json数组的格式
								paramValueAry = JSON.parseArray(paramValue);
								if (paramValueAry == null) {
									throw new JSONException();
								}
								if (paramValueAry.size() == 1) {
									// 只有一条记录，其他都使用这一条记录
									for (int i = 0; i < maxLen; i++) {
										map = paramList.get(i);
										map.put(key, paramValueAry.getString(0));
									}
								} else if (paramValueAry.size() == maxLen) { // 长度正好与
									for (int i = 0; i < maxLen; i++) {
										map = paramList.get(i);
										map.put(key, paramValueAry.getString(i));
									}
								} else {// 长度不相等
									for (int i = 0; i < paramValueAry.size(); i++) {
										map = paramList.get(i);
										map.put(key, paramValueAry.getString(i));
									}
								}
							} catch (JSONException e1) {
								nodes = paramValue.split(",");
								nodeLen = nodes.length;
								if (nodeLen == 1 && paramsObj.getString(key).indexOf(",") == -1) { // 只有一条记录
									for (int i = 0; i < maxLen; i++) {
										map = paramList.get(i);
										map.put(key, paramsObj.getString(key));
									}
								} else if (nodeLen == maxLen) { // 长度正好与
									for (int i = 0; i < maxLen; i++) {
										map = paramList.get(i);
										map.put(key, nodes[i]);
									}
								} else {
									for (int i = 0; i < nodeLen; i++) {
										map = paramList.get(i);
										map.put(key, nodes[i]);
									}
									/*
									 * for (int i = nodeLen; i < maxLen -
									 * nodeLen + 1; i++) { map =
									 * paramList.get(i); map.put(key, null); }
									 */
								}
							}
						}

						if (keys.contains("fiid") && keys.contains(
								"fpid") /*
										 * && keys.contains("tiid") &&
										 * keys.contains("ttreeid")
										 */) {
							List<Map<String, Object>> ary = Global.transformToTreeFormat(paramList, "fiid", "fpid",
									"children");
							paramList = iteratorTree(ary, null, useId);
							Map<String, Object> retMap = paramList.get(0);
							JSONObject retObj = (JSONObject) JSON.toJSON(retMap);
							return retObj.toJSONString().getBytes("UTF-8");
						}

					} else {
						// paramList.add(paramsObj.toJavaObject(Map.class));
						paramList.add(JSON.toJavaObject(paramsObj, Map.class));
					}

					if ("cu".equalsIgnoreCase(type)) {
						// depBaseWdataSetService.execBatch(useId, paramList);
						DataProcessFactory.getInstance().execBatch(useId, paramList);
					} else {
						// depBaseWdataSetService.execRemove(useId, paramList);
						DataProcessFactory.getInstance().execRemove(useId, paramList);
					}

					Map<String, Object> retMap = paramList.get(0);
					JSONObject retObj = (JSONObject) JSON.toJSON(retMap);
					return retObj.toJSONString().getBytes("UTF-8");
					// return ResponseUtils.responseResult(true);

				}
			}
		} catch (Exception e) {
			logger.error("mtcrud" + e.getMessage());
			return ResponseUtils.responseJsonResult(false, e.getMessage());
		}
		// throw new Exception("sssss异常");

		return ResponseUtils.responseJsonResult(false, "未传递任何参数信息！");
	}

	private List<Map<String, Object>> iteratorTree(List<Map<String, Object>> ary, Map<String, Object> pNode,
			Long useId) {
		List<Map<String, Object>> paramList = null;
		List<Map<String, Object>> childList = null;
		for (Map<String, Object> map : ary) {
			if (pNode != null) {
				map.put("ttreeid", pNode.get("treeid"));
				map.put("tiid", pNode.get("index_id"));
			}
			if (map.get("tiid") == null)
				map.put("tiid", "-1");
			paramList = new ArrayList<>();
			paramList.add(map);
			DataProcessFactory.getInstance().execBatch(useId, paramList);
			if (map.containsKey("children")
					&& (childList = (List<Map<String, Object>>) map.get("children")).size() > 0) {
				iteratorTree(childList, map, useId);
			}
		}
		return ary;
	}

	/**
	 * cell转pdf服务事件
	 * 
	 * @param request
	 * @param dataSourceRequest
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/cell2pdf")
	public @ResponseBody byte[] cell2pdf(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 传入的参数
		String params = RequestUtils.getString(request, "params", null);
		try {
			if (StringUtils.isNotEmpty(params)) {
				JSONObject paramsObj = JSON.parseObject(params);
				if (!paramsObj.isEmpty()) {
					String cellValue = paramsObj.getString("cellValue");
					String fileName = paramsObj.getString("fileName");
					byte[] file = null;
					FormAttachment model = new FormAttachment();
					if (StringUtils.isNotEmpty(cellValue)) {
						ByteArrayOutputStream os = new ByteArrayOutputStream();
						ByteArrayInputStream is = null;
						try {
							Spread2Pdf s2p = new Spread2Pdf(JSONObject.parseObject(cellValue), os);
							s2p.convert();
							file = os.toByteArray();
							model.setFileContent(file);
							model.setFileSize(file.length);
							model.setFileName(fileName + ".pdf");
							model.setCreateBy(loginContext.getActorId());
							model.setCreateDate(new Date());
							model.setParent(UUID32.getUUID());
							model.setType("1");
							model.setStatus(0);

							formAttachmentService.save(model);

							JSONObject retObj = new JSONObject();
							retObj.put("id", model.getId());
							retObj.put("fileName", model.getFileName());
							retObj.put("fileSize", model.getFileSize());
							retObj.put("parent", model.getParent());
							return retObj.toJSONString().getBytes("UTF-8");
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							os.close();
							if (is != null) {
								is.close();
							}
						}
					}
					// return ResponseUtils.responseResult(true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("convertService" + e.getMessage());
			return ResponseUtils.responseJsonResult(false, e.getMessage());
		}

		return ResponseUtils.responseResult(false);
	}
	
	/**
	 * pdf 合成服务
	 * 
	 * @param request
	 * @param dataSourceRequest
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/pdfmerge")
	public @ResponseBody byte[] pdfmerge(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 传入的参数
		String params = RequestUtils.getString(request, "params", null);
		try {
			if (StringUtils.isNotEmpty(params)) {
				JSONObject paramsObj = JSON.parseObject(params);
				if (!paramsObj.isEmpty()) {
					String ids = paramsObj.getString("ids");
					String fileName = paramsObj.getString("fileName");
					byte[] file = null;
					FormAttachment model = new FormAttachment();
					if (StringUtils.isNotEmpty(ids)) {
						String[] idstrs = ids.split(",");
						List<String> idList = (List<String>) StringUtil.toList(idstrs);
						
						ByteArrayOutputStream os = new ByteArrayOutputStream();
						try {
							FormAttachmentQuery query = new FormAttachmentQuery();
							query.setParents(idList);
							List<FormAttachment> allList = new ArrayList<>();
							List<FormAttachment> list = formAttachmentService.list(query);
							allList.addAll(list);
							
							query = new FormAttachmentQuery(); 
							query.setRowIds(idList);
							list = formAttachmentService.list(query);
							allList.addAll(list);
							
							List<InputStream> inputStreams = new ArrayList<>();
							allList.sort((a,b)->{
								int aIndex = idList.indexOf(a.getId());
								if(aIndex<0){
									aIndex = idList.indexOf(a.getParent());
								}
								int bIndex = idList.indexOf(b.getId());
								if(bIndex<0){
									bIndex = idList.indexOf(b.getParent());
								}
								return aIndex - bIndex;
							});
							if(allList!=null && !allList.isEmpty()){
								for (FormAttachment formAttachment : allList) {
									if("1".equals(formAttachment.getType())){
										inputStreams.add(new ByteArrayInputStream(formAttachment.getFileContent()));
									}else{
										String projectpath = request.getSession().getServletContext().getRealPath("/");
										File file1 = new File(projectpath + formAttachment.getSaveServicePath());
										inputStreams.add(new ByteArrayInputStream(FileUtils.getBytes(file1)));
									}
								}
							}
							MergePdf mergePdf = new MergePdf(os);
							mergePdf.merge(inputStreams);
							file = os.toByteArray();
							model.setFileContent(file);
							model.setFileSize(file.length);
							model.setFileName(fileName + ".pdf");
							model.setCreateBy(loginContext.getActorId());
							model.setCreateDate(new Date());
							model.setParent(UUID32.getUUID());
							model.setType("1");
							model.setStatus(0);

							formAttachmentService.save(model);

							JSONObject retObj = new JSONObject();
							retObj.put("id", model.getId());
							retObj.put("fileName", model.getFileName());
							retObj.put("fileSize", model.getFileSize());
							retObj.put("parent", model.getParent());
							return retObj.toJSONString().getBytes("UTF-8");
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							os.close();
						}
					}
					// return ResponseUtils.responseResult(true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("convertService" + e.getMessage());
			return ResponseUtils.responseJsonResult(false, e.getMessage());
		}

		return ResponseUtils.responseResult(false);
	}
	
	/**
	 * cell转tif服务事件
	 * 
	 * @param request
	 * @param dataSourceRequest
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/convertService")
	public @ResponseBody byte[] convertService(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 传入的参数
		String params = RequestUtils.getString(request, "params", null);
		try {
			if (StringUtils.isNotEmpty(params)) {
				JSONObject paramsObj = JSON.parseObject(params);
				if (!paramsObj.isEmpty()) {
					String cellValue = paramsObj.getString("cellValue");
					String fileName = paramsObj.getString("fileName");
					Integer dpi = paramsObj.getInteger("dpi");
					byte[] file = null;
					FormAttachment model = new FormAttachment();
					if (StringUtils.isNotEmpty(cellValue)) {
						ByteArrayOutputStream os = new ByteArrayOutputStream();
						ByteArrayInputStream is = null;
						try {
							Spread2Pdf s2p = new Spread2Pdf(JSONObject.parseObject(cellValue), os);
							s2p.convert();
							file = os.toByteArray();
							is = new ByteArrayInputStream(file);
							file = Pdf2Tif.convert(is);
							model.setFileContent(file);
							model.setFileSize(file.length);
							model.setFileName(fileName + ".tif");
							model.setCreateBy(loginContext.getActorId());
							model.setCreateDate(new Date());
							model.setParent(UUID32.getUUID());
							model.setType("1");
							model.setStatus(0);

							formAttachmentService.save(model);

							JSONObject retObj = new JSONObject();
							retObj.put("id", model.getId());
							retObj.put("fileName", model.getFileName());
							retObj.put("fileSize", model.getFileSize());
							retObj.put("parent", model.getParent());
							return retObj.toJSONString().getBytes("UTF-8");
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							os.close();
							if (is != null) {
								is.close();
							}
						}
					}
					// return ResponseUtils.responseResult(true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("convertService" + e.getMessage());
			return ResponseUtils.responseJsonResult(false, e.getMessage());
		}

		return ResponseUtils.responseResult(false);
	}

	/**
	 * spread 导出excel服务
	 * 
	 * @param request
	 * @param dataSourceRequest
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/spread2xls")
	public void spread2xls(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 传入的参数
		String exprotValue = RequestUtils.getString(request, "exprotValue", null);
		String exprotName = RequestUtils.getString(request, "exprotName", null);
		String mtUserId = RequestUtils.getString(request, "mtUserId", null);
		InputStream fis = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if (mtUserId != null) {
				DepReportTemplate depReportTemplate = depReportTemplateService
						.getDepReportTemplate(Long.parseLong(mtUserId));
				new Spread2ExcelByBack(depReportTemplate, baos,
						exprotValue != null ? JSON.parseObject(exprotValue) : null, mutilDatabaseBean, request,
						formAttachmentService).convert();
				fis = new ByteArrayInputStream(baos.toByteArray());
			} else {
				if (exprotValue != null) {
					new Spread2Excel(JSON.parseObject(exprotValue), baos).convert();
					fis = new ByteArrayInputStream(baos.toByteArray());
				}
			}

			ResponseUtils.download(request, response, fis,
					(StringUtils.isNotEmpty(exprotName) ? exprotName : new Date().getTime()) + ".xls");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("formDataController->spread2xls:"+e.getMessage());
		} finally {
			IOUtils.closeStream(fis);
		}

	}

	/**
	 * search服务
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mtsearch")
	public @ResponseBody byte[] mtsearch(HttpServletRequest request) throws Exception {
		// 返回
		JSONObject result = new JSONObject();

		// 分页 start
		int start = 0;
		int limit = PageResult.DEFAULT_PAGE_SIZE;
		int pageNo = 1;

		start = (pageNo - 1) * limit;
		// 分页 end

		String datasetId = RequestUtils.getString(request, "did", null);
		String params = RequestUtils.getString(request, "params", null);
		Long databaseId = 0l;

		if (datasetId == null) {
			logger.error("部门获取到数据集ID，无法执行查询服务！");
		}

		// 构建sql start
		DataSetBuilder builder = new DataSetBuilder();
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("HttpServletRequest", request);
		if (!isNullOrEmpty(params)) {
			try {
				JSONObject paramsObj = JSON.parseObject(params);
				// sql注入
				InjectUtils.escapeSql(paramsObj);
				parameter.putAll(paramsObj);
			} catch (Exception e) {
				e.printStackTrace();
				String message = "参数转换为JSON错误，参数为：" + params;
				logger.error(message);
				// throw new
				// ClassCastException("无任何参数信息或参数信息转换为JSON格式错误！参数为："+params);

				JSONArray rowsJSON = new JSONArray();
				result.put("data", rowsJSON);
				result.put("total", 0);
				result.put("statusCode", 500);
				result.put("message", message);
				return result.toJSONString().getBytes("UTF-8");
			}
		}

		parameter.put("rows", limit);
		String __ret = RequestSendRedirect.redirect(buildDataSetRuleMap(datasetId), parameter);
		if (StringUtils.isNotBlank(__ret)) {
			return __ret.getBytes("UTF-8");
		}

		Query query = builder.buildQuery(datasetId, null, null, parameter);

		String sql = query.toSql();

		List<Map<String, Object>> dataMaps = new ArrayList<Map<String, Object>>();
		int total = 0;

		// 数据源ID
		// 获取页面传递参数
		if (params != null && !"".equals(params)) {
			JSONObject jo = JSON.parseObject(params);
			try {
				Long dbid = jo.getLong("databaseId");
				if (dbid != null) {
					databaseId = dbid;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("databaseId转换失败" + e.getMessage());
			}
		}

		// 执行查询
		total = mutilDatabaseBean.getCountBySql(query, databaseId);

		if (total > 0) {
			dataMaps = mutilDatabaseBean.getDataListBySqlCriteria(sql, start, limit, databaseId);
			// 执行查询
			JSONArray rowsJSON = new JSONArray();
			int count = 1;
			for (Map<String, Object> map : dataMaps) {
				Set<String> keys = map.keySet();
				Iterator<String> iterator = keys.iterator();
				JSONObject josnObj = new JSONObject();
				josnObj.put("checkbox", count);
				josnObj.put("startIndex", count);
				josnObj.put("databaseId", databaseId);
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					josnObj.put(key, map.get(key) != null ? map.get(key).toString() : "");
				}
				rowsJSON.add(josnObj);
				count++;
			}
			result.put("total", total);
			result.put("data", rowsJSON);
			return result.toJSONString().getBytes("UTF-8");
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("data", rowsJSON);
			result.put("total", total);
			return result.toJSONString().getBytes("UTF-8");
		}
	}

	private Map<String, String> buildDataSetRuleMap(String dataSetId) {

		Map<String, String> rule = new HashMap<>(), dataSet = new HashMap<>();

		dataSet.put("datasetId", dataSetId);

		JSONArray array = new JSONArray();

		array.add(dataSet);

		rule.put("dataSourceSet", array.toJSONString());

		return rule;
	}

	/**
	 * 排序
	 * 
	 * @param request
	 * @param dataSourceRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ward")
	public @ResponseBody byte[] ward(HttpServletRequest request, @RequestBody DataSourceRequest dataSourceRequest)
			throws Exception {
		// 获取参数
		String rid = dataSourceRequest.getRid();
		String paramType = dataSourceRequest.getParamType();
		List<?> models = dataSourceRequest.getModels();
		if (models == null || models.size() <= 0) {
			return null;
		}
		Map<String, String> ruleMap = getRuleMap(rid);// 获取所有规则

		String dataSourceSet = ruleMap.get("dataSourceSet");
		// 装换成json数组
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONObject datasourceSetJSONObject = null;
		// 将json数组转化成json对象
		if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
			datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
		}
		if (datasourceSetJSONObject == null) {
			return null;
		}
		String tables = datasourceSetJSONObject.getString("tables");
		JSONArray tablesJSONArray = null;
		if (!isNullOrEmpty(tables)) {
			tablesJSONArray = JSON.parseArray(tables);
		}
		if (tablesJSONArray == null || (tablesJSONArray != null && tablesJSONArray.size() > 1)) { // 如果是多表的
			// 不支持操作
			return null;
		}

		String tableName = tablesJSONArray.getString(0);

		String prefixTableName = tableName + "_0_";

		// 数据源ID
		Long databaseId = datasourceSetJSONObject.getLong("databaseId");

		JSONObject metadata = dataSetService.getDataSetMetadata(datasourceSetJSONObject.getString("datasetId")),
				mapping = null;
		if (metadata != null) {
			mapping = metadata.getJSONObject("mapping");
		}

		String params = dataSourceRequest.getParams();
		// 将参数params转化成json对象
		if (params != null && !"".equals(params)) {
			JSONObject jo = JSON.parseObject(params);
			try {
				Long dbid = jo.getLong("databaseId");
				if (dbid != null) {
					databaseId = dbid;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("databaseId转换失败" + e.getMessage());
			}
		}

		Map<String, Object> map = null;
		String id = null;
		int listno = 0;
		try {
			for (Object object : models) {
				map = (Map<String, Object>) object;

				Set<String> keys = map.keySet();
				String key;
				String idKey = null;
				String listNoKey = null;
				for (String skey : keys) {
					key = Global.getOriginalColumnName(mapping, skey);
					if (key.equalsIgnoreCase(prefixTableName + "id")) {
						idKey = skey;
					} else if (key.equalsIgnoreCase(prefixTableName + "listno")) {
						listNoKey = skey;
					}
				}
				if (idKey != null && listNoKey != null) {
					id = map.get(idKey).toString();
					listno = map.get(listNoKey) != null && !"".equals(map.get(listNoKey))
							? Integer.parseInt(map.get(listNoKey).toString()) : 1;
					if (StringUtils.isNotEmpty(id)) {// id不为null时
						boolean isAsc = "true".equalsIgnoreCase(paramType);
						int nextlistno = isAsc ? (listno + 1) : (listno - 1);
						String sql = "update " + tableName + " set listno=" + listno + " where listno = (select "
								+ (isAsc ? "min" : "max") + "(listno) from " + tableName + " where listno "
								+ (isAsc ? ">" : "<") + " (select listno from " + tableName + "  where id = '" + id
								+ "')) ";
						mutilDatabaseBean.execSql(sql, tableName, databaseId);
						sql = "update " + tableName + " set listno=" + nextlistno + " where id='" + id + "'";
						mutilDatabaseBean.execSql(sql, tableName, databaseId);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("data delete" + e.getMessage());
			return null;
		}
		return this.getGridData(request, dataSourceRequest);
	}

	/**
	 * 拖拽排序
	 * 
	 * @param request
	 * @param dataSourceRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wardBydrag")
	public @ResponseBody byte[] wardBydrag(HttpServletRequest request, @RequestBody DataSourceRequest dataSourceRequest)
			throws Exception {
		// 获取参数
		String rid = dataSourceRequest.getRid();
		String paramType = dataSourceRequest.getParamType();
		List<?> models = dataSourceRequest.getModels();
		JSONObject jasonObject = JSONObject.parseObject(dataSourceRequest.getParams());
		int listno = jasonObject.get("listno") != null ? Integer.valueOf((String) jasonObject.get("listno")) : 0;
		int nextlistno = jasonObject.get("nextlistno") != null ? Integer.valueOf((String) jasonObject.get("nextlistno"))
				: 0;
		if (models == null || models.size() <= 0) {
			return null;
		}
		Map<String, String> ruleMap = getRuleMap(rid);// 获取所有规则

		String dataSourceSet = ruleMap.get("dataSourceSet");
		// 装换成json数组
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONObject datasourceSetJSONObject = null;
		// 将json数组转化成json对象
		if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
			datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
		}
		if (datasourceSetJSONObject == null) {
			return null;
		}
		String tables = datasourceSetJSONObject.getString("tables");
		JSONArray tablesJSONArray = null;
		if (!isNullOrEmpty(tables)) {
			tablesJSONArray = JSON.parseArray(tables);
		}
		if (tablesJSONArray == null || (tablesJSONArray != null && tablesJSONArray.size() > 1)) { // 如果是多表的
			// 不支持操作
			return null;
		}

		String tableName = tablesJSONArray.getString(0);

		String prefixTableName = tableName + "_0_";

		// 数据源ID
		Long databaseId = datasourceSetJSONObject.getLong("databaseId");

		JSONObject metadata = dataSetService.getDataSetMetadata(datasourceSetJSONObject.getString("datasetId")),
				mapping = null;
		if (metadata != null) {
			mapping = metadata.getJSONObject("mapping");
		}

		String params = dataSourceRequest.getParams();
		// 将参数params转化成json对象
		if (params != null && !"".equals(params)) {
			JSONObject jo = JSON.parseObject(params);
			try {
				Long dbid = jo.getLong("databaseId");
				if (dbid != null) {
					databaseId = dbid;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("databaseId转换失败" + e.getMessage());
			}
		}

		Map<String, Object> map = null;
		String id = null;
		try {
			for (Object object : models) {
				map = (Map<String, Object>) object;

				Set<String> keys = map.keySet();
				String key;
				String idKey = null;
				String listNoKey = null;
				for (String skey : keys) {
					key = Global.getOriginalColumnName(mapping, skey);
					if (key.equalsIgnoreCase(prefixTableName + "id")) {
						idKey = skey;
					} else if (key.equalsIgnoreCase(prefixTableName + "listno")) {
						listNoKey = skey;
					}
				}
				if (idKey != null && listNoKey != null) {
					id = map.get(idKey).toString();
					if (StringUtils.isNotEmpty(id)) {// id不为null时
						boolean isAsc = "true".equalsIgnoreCase(paramType);
						String sql = "";
						if (listno > nextlistno) {
							nextlistno = nextlistno + 1;
							int curlistno = nextlistno;
							while (nextlistno < listno) {
								int currentListNo = listno - 1;
								sql = "update " + tableName + " set listno=" + listno + " where listno = "
										+ currentListNo;
								mutilDatabaseBean.execSql(sql, tableName, databaseId);
								listno--;
							}
							sql = "update " + tableName + " set listno=" + curlistno + " where id='" + id + "'";
							mutilDatabaseBean.execSql(sql, tableName, databaseId);
						} else {
							while (listno < nextlistno - 1) {
								int currentListNo = listno + 1;
								sql = "update " + tableName + " set listno=" + listno + " where listno = "
										+ currentListNo;
								mutilDatabaseBean.execSql(sql, tableName, databaseId);
								listno++;
							}
							sql = "update " + tableName + " set listno=" + (nextlistno - 1) + " where id='" + id + "'";
							mutilDatabaseBean.execSql(sql, tableName, databaseId);
						}

					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("data delete" + e.getMessage());
			return null;
		}
		return this.getGridData(request, dataSourceRequest);
	}

	/**
	 * 批量任务调度
	 * 
	 * @param request
	 * @param dataSourceRequest
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/taskTable")
	public @ResponseBody byte[] taskTable(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 更新集 id
		long useId = RequestUtils.getLong(request, "useId", 0);
		// 传入的参数
		String params = RequestUtils.getString(request, "params", null);

		try {
			TaskTable taskTable = taskTableService.getTaskTable(useId);
			if (taskTable != null && taskTable.getLocked() == 0) {
				JSONObject paramsObj = StringUtils.isNotEmpty(params) ? JSON.parseObject(params) : new JSONObject();
				if (!paramsObj.isEmpty()) {
					String jobNo = null;
					String runDay = DateUtils.getNowYearMonthDayHHmmss();
					StringTokenizer token = new StringTokenizer(taskTable.getDatabaseIds(), ",");
					while (token.hasMoreTokens()) {
						String x = token.nextToken();
						if (StringUtils.isNotEmpty(x)) {
							long databaseId = Long.parseLong(x);
							if (databaseId != 0) {
								jobNo = "task_table_" + taskTable.getId() + "_" + databaseId + "_" + runDay;
								TaskTableTask taskTableBean = new TaskTableTask(databaseId, taskTable.getId(), jobNo,
										paramsObj);
								taskTableBean.execute();
							}
						}
					}
				}
				return ResponseUtils.responseJsonResult(true, "执行成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("taskTable" + e.getMessage());
			return ResponseUtils.responseJsonResult(false, e.getMessage());
		}
		return ResponseUtils.responseJsonResult(false, "未查找到任务，或者任务被锁定！");
	}

	/**
	 * 组合表调度
	 * 
	 * @param request
	 * @param dataSourceRequest
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/tableCombination")
	public @ResponseBody byte[] tableCombination(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 更新集 id
		long useId = RequestUtils.getLong(request, "useId", 0);
		// 传入的参数
		String params = RequestUtils.getString(request, "params", null);

		try {
			int errorCount = 0;
			String jobNo = null;
			Database database = null;
			String runDay = DateUtils.getNowYearMonthDayHHmmss();
			StringBuilder buffer = new StringBuilder();
			TableCombination tableCombination = tableCombinationService.getTableCombination(useId);
			if (tableCombination != null && StringUtils.isNotEmpty(tableCombination.getDatabaseIds())) {
				if (StringUtils.isNotEmpty(tableCombination.getDatabaseIds())) {
					tableCombination.setSyncTime(new Date());
					StringTokenizer token = new StringTokenizer(tableCombination.getDatabaseIds(), ",");
					while (token.hasMoreTokens()) {
						String x = token.nextToken();
						if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
							jobNo = "table_combination_" + tableCombination.getId() + "_" + x + "_"
									+ tableCombination.getTargetDatabaseId() + "_" + runDay;
							try {
								database = databaseService.getDatabaseById(Long.parseLong(x));
								if (database != null) {
									TableCombinationTask task = new TableCombinationTask(database.getId(),
											tableCombination.getTargetDatabaseId(), tableCombination.getId(), jobNo);
									if (task.execute()) {
										buffer.append(database.getTitle()).append("[").append(database.getMapping())
												.append("]执行成功。\n");
									} else {
										errorCount++;
										buffer.append(database.getTitle()).append("[").append(database.getMapping())
												.append("]执行失败。\n");
										StringBuffer sb = ExceptionUtils
												.getMsgBuffer("table_combination_" + tableCombination.getId());
										buffer.append(sb != null ? sb.toString() : "");
									}
								}
							} catch (Exception ex) {
								errorCount++;
							} finally {
								ExceptionUtils.clear("table_combination_" + tableCombination.getId());
							}
						}
					}
					if (errorCount == 0) {
						tableCombination.setSyncStatus(1);
					} else {
						tableCombination.setSyncStatus(-1);
					}
					tableCombinationService.updateTableCombinationStatus(tableCombination);
				}
			}

			return ResponseUtils.responseJsonResult(errorCount == 0 ? true : false, buffer.toString());
		} catch (Exception e) {
			logger.error("taskTable" + e.getMessage());
			return ResponseUtils.responseJsonResult(false, e.getMessage());
		}
	}

	public static JSONArray ListToJsonArray(List<Map<String, Object>> list) {
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(list);
		return jsonArray;
	}

	public static void main(String[] args) {
		String k = "'a','b'";
		System.out.println(k.substring(0, k.length() - 1));
	}
}
