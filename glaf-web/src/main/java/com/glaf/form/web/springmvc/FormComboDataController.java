package com.glaf.form.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.metamodel.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.service.DictoryService;
import com.glaf.base.modules.sys.service.IFieldInterfaceService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.core.base.DataRequest.FilterDescriptor;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.service.EntityService;
import com.glaf.core.service.ITablePageService;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.RequestUtils;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.form.core.helper.SqlHelper;
import com.glaf.form.core.service.FormComponentPropertyService;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.form.core.service.FormRuleService;
import com.glaf.form.core.service.FormTemplateService;
import com.glaf.form.core.util.MutilDatabaseBean;
import com.glaf.form.rule.DataSourceRequest;
import com.glaf.form.rule.ParamsSqlHelper;
import com.glaf.form.rule.util.InjectUtils;
import com.glaf.isdp.service.CellUserAddService;
import com.glaf.isdp.service.TableActionService;

/**
 * 通用数据调用
 * 
 * 
 */
@Controller("/form/combo")
@RequestMapping("/form/combo")
public class FormComboDataController {

	protected static final Log logger = LogFactory
			.getLog(FormComboDataController.class);

	protected DictoryService dictoryService;

	protected SysTreeService sysTreeService;

	protected FormTemplateService formTemplateService;

	protected FormRuleService formRuleService;

	protected FormComponentPropertyService formComponentPropertyService;

	protected IFieldInterfaceService iFieldInterfaceService;

	protected ITablePageService tablePageService;

	protected CellUserAddService cellUserAddService;

	protected EntityService entityService;

	protected TableActionService tableActionService;

	protected FormRulePropertyService formRulePropertyService;

	protected MutilDatabaseBean mutilDatabaseBean;

	public FormComboDataController() {

	}

	@ResponseBody
	@RequestMapping("/comboboxData")
	public byte[] getComboboxData(HttpServletRequest request,
			HttpServletResponse response, DataSourceRequest dataSourceRequest)
			throws Exception {
		// LoginContext loginContext = RequestUtils.getLoginContext(request);
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		// 获取参数
		String rid = dataSourceRequest.getRid();// 规则id

		// 获取规则MAP
		Map<String, String> ruleMap = getRuleMap(rid);
		if (ruleMap == null) {
			return null;
		}

		String enumValue = ruleMap.get("enumValue");
		if (StringUtils.isNotEmpty(enumValue)) {// 字典里的枚举数据
			return jsonArray(enumValue);
		} else {
			/*
			 * List<String> tableNames = getTableNames(ruleMap);
			 * 
			 * String tableName = tableNames.get(0);
			 * 
			 * if (!DBUtils.isAllowedTable(tableName)) { throw new
			 * RuntimeException(tableName + " access deny."); }
			 * 
			 * String sql = " select distinct * from " + tableName;
			 * 
			 * List<Map<String, Object>> list = cellUserAddService
			 * .getDataListBySql(sql);
			 */
			String str = ruleMap.get("dataSourceSet");
			String dataSetId = null;
			JSONArray jsonArray = new JSONArray();
			if (!isNullOrEmpty(str)) {
				jsonArray = JSONArray.parseArray(str);

				JSONObject datasourceSetJSONObject = jsonArray.getJSONObject(0);

				dataSetId = datasourceSetJSONObject.getString("datasetId");

				String psql = "";
				// 获取 输入表达式定义
				String inParameters = ruleMap.get("inParameters");
				// 获取页面传递参数
				String params = dataSourceRequest.getParams();
				JSONObject paramsObj = new JSONObject();
				if(StringUtils.isNotEmpty(params)){
					paramsObj = JSONObject.parseObject(params);
					InjectUtils.escapeSql(paramsObj);
				}
				psql = ParamsSqlHelper.getParamSql(paramsObj.toJSONString(), inParameters,
						dataSetId);

				// where 查询
				FilterDescriptor filterDescriptor = dataSourceRequest
						.getFilter();
				StringBuilder whereSql = new StringBuilder(" ");
				if (filterDescriptor != null
						&& filterDescriptor.getFilters() != null
						&& filterDescriptor.getFilters().size() > 0) {
					SqlHelper helper = new SqlHelper();
					helper.getWhereSql(whereSql, filterDescriptor, 1, false);
				}
				// where end
				try {
					Map<String, Object> parameter = new HashMap<String, Object>();
					parameter.put("HttpServletRequest", request);
					if (!paramsObj.isEmpty()) {
						parameter.putAll(paramsObj);
					}

					DataSetBuilder builder = new DataSetBuilder();

					Query query = builder.buildQuery(dataSetId, whereSql
							.toString().trim() + psql, null, parameter);

					String sql = query.toSql();

					// 数据源ID
					Long databaseId = datasourceSetJSONObject
							.getLong("databaseId");
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
					List<Map<String, Object>> dataMaps = mutilDatabaseBean
							.getDataListBySql(sql, databaseId);
					jsonArray = new JSONArray();
					if (dataMaps != null && !dataMaps.isEmpty()) {
						jsonArray.addAll(dataMaps);
					}

					// DataSetBuilder builder = new DataSetBuilder();
					// jsonArray = builder.getJsonArray(dataSetId, null);
				} catch (Exception e) {
					jsonArray = new JSONArray();
				}
			}
			return jsonArray.toString().getBytes("UTF-8");
		}

	}

	private boolean isNullOrEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	@ResponseBody
	@RequestMapping("/getTestData")
	public byte[] getTestData(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		/*
		 * Map<String, Object> params = RequestUtils.getParameterMap(request);
		 * // String tableName = RequestUtils.getString(request, "tableName");
		 * List<FieldDefinition> list = TableFactory
		 * .getFieldDefinitions("FORM_COMPONENT_PROPERTY"); String columnName;
		 * for (FieldDefinition fd : list) { columnName = fd.getColumnName();
		 * //System.out.println(fd); // //System.out.println(columnName); }
		 */
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		// //System.out.println("params:" + params);
		logger.debug("params:" + params);
		String category = RequestUtils.getString(request, "code");

		if (StringUtils.isNotEmpty(category)) {
			return jsonArray(category);
		}

		return null;
	}

	@RequestMapping("/comboboxData1")
	public @ResponseBody
	byte[] getGridData(HttpServletRequest request,
			@RequestBody DataSourceRequest dataSourceRequest) throws Exception {
		// LoginContext loginContext = RequestUtils.getLoginContext(request);
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		// 获取参数
		String rid = dataSourceRequest.getRid();// 规则id

		// 获取规则MAP
		Map<String, String> ruleMap = getRuleMap(rid);
		if (ruleMap == null) {
			return null;
		}
		List<String> tableNames = getTableNames(ruleMap);
		String tableName = tableNames.get(0);

		/**
		 * 规则2
		 */
		// Map<String, String> ruleMap2 = null;
		String prid = dataSourceRequest.getPrid();// 父规则id
		String parentId = dataSourceRequest.getParentId();// 父规则id
		String psql = "";
		if (prid != null) {
			// 主表id = 从表 topid
			// ruleMap2 = getRuleMap(prid);
			psql = " and topid = '" + InjectUtils.escapeSql(parentId) + "' ";
		}

		// 服务器分页"take":2,"skip":0,"page":1,"pageSize":2
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

		// 服务器排序"sort":[{"field":"listweigth","dir":"asc"}]
		// String sortSql = ParamUtils.getString(params, "sort[0][field]","id")
		// ;
		List<SortDescriptor> sorts = dataSourceRequest.getSort();
		String sortSql = " id asc ";
		if (sorts != null && sorts.size() > 0) {
			sortSql = sorts.get(0).getField() + " " + sorts.get(0).getDir();
		}
		// ParamUtils.getString(params, "sort[0][field]", " id ") + " " +
		// ParamUtils.getString(params, "sort[0][dir]", " asc ");
		FilterDescriptor filterDescriptor = dataSourceRequest.getFilter();
		StringBuilder whereSql = null;
		if (filterDescriptor.getFilters() != null
				&& filterDescriptor.getFilters().size() > 0) {
			whereSql = new StringBuilder(" where 1=1 ");
			SqlHelper helper = new SqlHelper();
			helper.getWhereSql(whereSql, filterDescriptor, 1, false);
		}

		// String whereSql = getWhereSql(params);
		/*
		 * 获取表信息 wbs 相关 根据wbs权限控制 wbs 无关 走正常流程，跟工作流相关
		 */

		// 判断是否服务器分页
		String serverPaging = ruleMap.get("serverPaging");

		JSONObject result = new JSONObject();// 返回信息

		String sql = " select * from " + tableName
				+ (whereSql == null ? " where 1=1 " : whereSql) + psql
				+ " order by " + sortSql;
		int total = cellUserAddService.getDataCount(tableName,
				(whereSql == null ? " where 1=1 " : whereSql) + psql);
		if (total > 0) {
			List<Map<String, Object>> dataMaps = new ArrayList<Map<String, Object>>();

			if (StringUtils.isEmpty(serverPaging)
					|| !"true".equalsIgnoreCase(serverPaging)) {
				// 客服端分页
				dataMaps = cellUserAddService.getDataListBySql(sql);
				// dataMaps = tablePageService.getListData(sql, null);
			} else {
				// 服务端分页
				dataMaps = cellUserAddService.getDataListByQueryCriteria(sql,
						start, limit);
				// dataMaps = tablePageService.getListData(sql, null, start,
				// limit);
			}
			// DataResponse dataResponse = new DataResponse();//如果是日期类型
			// 会返回long值产生问题
			// dataResponse.setData(dataMaps);
			// dataResponse.setTotal(total);
			// return dataResponse ;
			JSONArray rowsJSON = new JSONArray();
			for (Map<String, Object> map : dataMaps) {
				Set<String> keys = map.keySet();
				Iterator<String> iterator = keys.iterator();
				JSONObject josnObj = new JSONObject();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					if (map.get(key) != null) {
						josnObj.put(key, map.get(key).toString());
					}
				}
				rowsJSON.add(josnObj);
			}

			result.put("total", total);
			result.put("data", rowsJSON);
			return result.toJSONString().getBytes("UTF-8");
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
			return result.toJSONString().getBytes("UTF-8");
			// DataResponse dataResponse = new DataResponse();
			// dataResponse.setTotal(total);
			// return dataResponse ;
		}
	}

	private Map<String, String> getRuleMap(String rid) {
		return this.formRulePropertyService.getRuleMap(rid);
	}

	private List<String> getTableNames(Map<String, String> ruleMap) {
		List<String> tableNames = new ArrayList<String>();
		// 获取表名称
		String tables = ruleMap.get("table");
		JSONArray tablesArray = (JSONArray) JSON.parse(tables);
		JSONObject tableJson = null;
		for (Object object : tablesArray) {
			tableJson = (JSONObject) object;
			tableNames.add(tableJson.getString("TableName"));
		}
		return tableNames;
	}

	private byte[] jsonArray(String category) {
		try {
			JSONArray array = new JSONArray();
			SysTree tree = sysTreeService.getSysTreeByCode(category);
			if (tree != null) {
				List<Dictory> list = dictoryService
						.getAvailableDictoryList(tree.getId());
				int index = 0;
				for (Dictory item : list) {
					index++;
					JSONObject json = item.toJsonObject();
					json.put("index", index);
					json.put("listno", index);
					array.add(json);
				}
			}

			return array.toString().getBytes("UTF-8");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.cellUserAddService")
	public void setCellUserAddService(CellUserAddService cellUserAddService) {
		this.cellUserAddService = cellUserAddService;
	}

	@javax.annotation.Resource
	public void setDictoryService(DictoryService dictoryService) {
		this.dictoryService = dictoryService;
	}

	@javax.annotation.Resource
	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}

	@javax.annotation.Resource
	public void setFormComponentPropertyService(
			FormComponentPropertyService formComponentPropertyService) {
		this.formComponentPropertyService = formComponentPropertyService;
	}

	@javax.annotation.Resource
	public void setFormRuleService(FormRuleService formRuleService) {
		this.formRuleService = formRuleService;
	}

	@javax.annotation.Resource
	public void setFormRulePropertyService(
			FormRulePropertyService formRulePropertyService) {
		this.formRulePropertyService = formRulePropertyService;
	}

	@javax.annotation.Resource
	public void setFormTemplateService(FormTemplateService formTemplateService) {
		this.formTemplateService = formTemplateService;
	}

	@javax.annotation.Resource
	public void setiFieldInterfaceService(
			IFieldInterfaceService iFieldInterfaceService) {
		this.iFieldInterfaceService = iFieldInterfaceService;
	}

	@javax.annotation.Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.tableActionService")
	public void setTableActionService(TableActionService tableActionService) {
		this.tableActionService = tableActionService;
	}

	@javax.annotation.Resource
	public void setTablePageService(ITablePageService tablePageService) {
		this.tablePageService = tablePageService;
	}

	@javax.annotation.Resource
	public void setMutilDatabaseBean(MutilDatabaseBean mutilDatabaseBean) {
		this.mutilDatabaseBean = mutilDatabaseBean;
	}

}
