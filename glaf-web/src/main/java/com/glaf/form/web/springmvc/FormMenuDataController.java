package com.glaf.form.web.springmvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.metamodel.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.base.TreeModel;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.factory.TableFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.form.core.util.MutilDatabaseBean;
import com.glaf.form.rule.DataSourceRequest;
import com.glaf.form.rule.ParamsSqlHelper;
import com.glaf.form.rule.util.InjectUtils;

/**
 * 通用菜单数据调用
 * 
 * @author Administrator
 *
 */
@Controller("/form/menuData")
@RequestMapping("/form/menuData")
public class FormMenuDataController {
	protected static final Log logger = LogFactory.getLog(FormMenuDataController.class);

	@Autowired
	protected MutilDatabaseBean mutilDatabaseBean;
	@Autowired
	protected FormRulePropertyService formRulePropertyService;
	
	@Autowired
	SysApplicationService sysApplicationService;

	public FormMenuDataController() {

	}

	private Map<String, String> getRuleMap(String rid) {
		return this.formRulePropertyService.getRuleMap(rid);
	}

	private String getRuleMapValueByKey(Map<String, String> ruleMap, String key) {
		String columnsStr = ruleMap.get("columns");
		JSONArray columnsArray = JSON.parseArray(columnsStr);
		JSONObject jo = null;
		String keyValue = null;
		for (Object object : columnsArray) {
			jo = (JSONObject) object;
			if (key.equalsIgnoreCase(jo.getString("columnType"))) {
				keyValue = jo.getString("ColumnName").split("_0_")[1];
			}
		}
		return keyValue;
	}
	
	/**
	 * 根据系统权限获取菜单
	 * @param request
	 * @return
	 */
	private JSONArray getSysMenu(HttpServletRequest request){
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		long appId = RequestUtils.getLong(request, "appId", 3);
		// 特殊菜单
		TreeModel root = sysApplicationService.getTreeModelByAppId(appId);
		List<TreeModel> treeNodes = new ArrayList<>();
		try {
			if (root != null) {
				treeNodes = sysApplicationService.getTreeModels(root.getId(), loginContext.getActorId());
				if (treeNodes != null) {
					Collections.sort(treeNodes);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSON.parseArray(JSON.toJSONString(treeNodes));
	}

	/**
	 * 获取树节点数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/data")
	public @ResponseBody byte[] getMenuData(HttpServletRequest request, HttpServletResponse response,
												@RequestBody DataSourceRequest dataSourceRequest) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 获取参数
//		String rid = RequestUtils.getString(request, "rid", null);// 规则id
//		Integer pid = RequestUtils.getInteger(request, "pid", -1);// 父节点ID
		
		// 获取参数
		String rid = dataSourceRequest.getRid();// 规则id
		String pid = dataSourceRequest.getParentId();
		// 获取规则MAP
		Map<String, String> ruleMap = getRuleMap(rid);
		
		if (ruleMap == null) {
			logger.info("ruleMap 找不到规则:" + rid);
			return null;
		}
		JSONObject result = new JSONObject();
		if("true".equalsIgnoreCase(ruleMap.get("sysmenu"))){
			JSONArray datas = getSysMenu(request);
			result.put("data", datas);
			result.put("total", datas.size());
			return result.toJSONString().getBytes("UTF-8");
		}

		String dataSourceSet = ruleMap.get("dataSourceSet");
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONObject datasourceSetJSONObject = null;
		if (datasourceSetJSONArray!=null && !datasourceSetJSONArray.isEmpty()) {
			datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
		}
		if (datasourceSetJSONObject == null) {
			logger.info("数据集错误");
			return null;
		}
		
		//获取定义的数据字段 
		String prefix  = "_0_" ;
		String pIdKey = null;
		String idKey = null;
		String indexKey = null;
		String columnsStr = ruleMap.get("columns");
		JSONArray columnsArray = JSON.parseArray(columnsStr);
		if(columnsArray!=null && !columnsArray.isEmpty()){
			for (Object object : columnsArray) {
				JSONObject jo = (JSONObject) object;
				if ("pIdKey".equalsIgnoreCase(jo.getString("columnType"))) {
					pIdKey = jo.getString("ColumnName").replace(prefix, ".");
				} else if ("idKey".equalsIgnoreCase(jo.getString("columnType"))) {
					idKey = jo.getString("ColumnName").replace(prefix, ".");
				} else if ("indexKey".equalsIgnoreCase(jo.getString("columnType"))) {
					indexKey = jo.getString("ColumnName").replace(prefix, ".");
				}
			}
		}
				
				
		// 构建联动语句 start
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
		psql = ParamsSqlHelper.getParamSql(paramsObj.toJSONString(), inParameters, datasourceSetJSONObject.getString("datasetId"));
		// 构建联动语句 end
		
		
		// 判断是否服务器分页
		String pageable = ruleMap.get("pageable");
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
		
	
		// 数据源ID
		Long databaseId = datasourceSetJSONObject.getLong("databaseId");
		if (!paramsObj.isEmpty()) {
			try {
				Long dbid = paramsObj.getLong("databaseId");
				if (dbid != null) {
					databaseId = dbid;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("databaseId转换失败" + e.getMessage());
			}
		}
	
		// 构建sql start
		DataSetBuilder builder = new DataSetBuilder();
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("HttpServletRequest", request);
		if (!paramsObj.isEmpty()) {
			parameter.putAll(paramsObj);
		}
		
		String whereSql = "";
		Query query = null;
		String sql = "";
		if (pIdKey != null && StringUtils.isNotEmpty(pid)) {
			// 构建sql start
			query = builder.buildQuery(datasourceSetJSONObject.getString("datasetId"), indexKey + "=" + pid, "", parameter);
			sql = query.toSql();
			List<Map<String, Object>> dm = mutilDatabaseBean.getDataListBySql(sql + psql, databaseId);
			// 构建sql end 
			if (dm != null && dm.size() > 0) {
				Map<String, Object> model = dm.get(0);
				whereSql = idKey + " like '" + model.get(idKey.replace(".", prefix)) + "_%'";
			}
		}

		// 构建sql start
		query = builder.buildQuery(datasourceSetJSONObject.getString("datasetId"), whereSql + psql, "", parameter);
		sql = query.toSql();
		// 构建sql end
		
		// 返回
		
		List<Map<String, Object>> dataMaps = new ArrayList<Map<String, Object>>();
		int total = mutilDatabaseBean.getCountBySql(query, databaseId);
		
		if (total > 0) {
			if (pageable == null || StringUtils.isEmpty(pageable) || !"true".equalsIgnoreCase(pageable)) {
				dataMaps = mutilDatabaseBean.getDataListBySql(sql, databaseId);// 不分页
			} else {
				dataMaps = mutilDatabaseBean.getDataListBySqlCriteria(sql, start, limit, databaseId);
			}
			
			JSONArray rowsJSON = new JSONArray();
//			int count = 1;
			for (Map<String, Object> map : dataMaps) {
				Set<String> keys = map.keySet();
				Iterator<String> iterator = keys.iterator();
				JSONObject josnObj = new JSONObject();
//				josnObj.put("checkbox", count);
//				josnObj.put("startIndex", count);
//				josnObj.put("databaseId", databaseId);
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					// if (map.get(key) != null) {
					josnObj.put(key, map.get(key) != null ? map.get(key).toString() : "");
					// }
				}
				rowsJSON.add(josnObj);
//				count++;
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

	private static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}
	
	@javax.annotation.Resource
	public void setFormRulePropertyService(FormRulePropertyService formRulePropertyService) {
		this.formRulePropertyService = formRulePropertyService;
	}

	@javax.annotation.Resource
	public void setMutilDatabaseBean(MutilDatabaseBean mutilDatabaseBean) {
		this.mutilDatabaseBean = mutilDatabaseBean;
	}
	
	
}
