package com.glaf.form.web.springmvc;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.metamodel.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataRequest.FilterDescriptor;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.dep.base.factory.DataProcessFactory;
import com.glaf.dep.base.service.DepBaseWdataSetService;
import com.glaf.form.core.helper.SqlHelper;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.form.core.util.MutilDatabaseBean;
import com.glaf.form.rule.DataSourceRequest;
import com.glaf.form.rule.ParamsSqlHelper;
import com.glaf.form.rule.util.InjectUtils;

@Controller("/form/gantt")
@RequestMapping("/form/gantt")
public class FormGanttDataController {

	protected static final Log logger = LogFactory.getLog(FormGanttDataController.class);

	protected MutilDatabaseBean mutilDatabaseBean;

	protected FormRulePropertyService formRulePropertyService;
	
	protected DepBaseWdataSetService depBaseWdataSetService;

	@Resource
	public void setMutilDatabaseBean(MutilDatabaseBean mutilDatabaseBean) {
		this.mutilDatabaseBean = mutilDatabaseBean;
	}

	@Resource
	public void setFormRulePropertyService(FormRulePropertyService formRulePropertyService) {
		this.formRulePropertyService = formRulePropertyService;
	}
	
	@javax.annotation.Resource
	public void setDepBaseWdataSetService(
			DepBaseWdataSetService depBaseWdataSetService) {
		this.depBaseWdataSetService = depBaseWdataSetService;
	}

	private Map<String, String> getRuleMap(String rid) {
		return this.formRulePropertyService.getRuleMap(rid);
	}

	@RequestMapping("/data")
	public @ResponseBody
	byte[] getGridData0(HttpServletRequest request,
			@RequestBody DataSourceRequest dataSourceRequest) throws Exception {
		// 获取参数
		String rid = dataSourceRequest.getRid();// 规则id

		// 获取规则MAP
		Map<String, String> ruleMap = this.getRuleMap(rid);
		if (ruleMap == null) {
			return new JSONArray().toJSONString().getBytes("UTF-8");
		}

		// 获取
		String dataSourceSet = ruleMap.get("dataSourceSet");
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONArray selectDatasourceJSONArray = null;
		JSONObject datasourceJSONObject = null;
		JSONArray selectColumnsJSONArray = null;

		JSONObject mapping = new JSONObject();
		if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
			JSONObject datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
			selectDatasourceJSONArray = datasourceSetJSONObject.getJSONArray("selectDatasource");

			selectColumnsJSONArray = datasourceSetJSONObject.getJSONArray("selectColumns");

			String id = null;
			String parent_id = null;
			if (selectColumnsJSONArray != null) {
				for (Object json : selectColumnsJSONArray) {
					JSONObject o = (JSONObject) json;
					String ctype = o.getString("ctype");
					if (StringUtils.isNotEmpty(ctype)) {
						mapping.put(o.getString("columnName"), ctype);
					}
					if("id".equals(o.getString("title"))){
						id = o.getString("columnName");
					}
					if("parent_id".equals(o.getString("title"))){
						parent_id = o.getString("columnName");
					}
				}
			}

			for (Object object : selectDatasourceJSONArray) {
				datasourceJSONObject = (JSONObject) object;
				if (datasourceJSONObject == null || datasourceJSONObject.size() == 0) {
					continue;
				}
				// 构建联动语句 start
				String psql = "";
				// 获取 输入表达式定义
				String inParameters = ruleMap.get("inParameters");
				// 获取页面传递参数
				String params = dataSourceRequest.getParams();
				
				JSONObject paramsObj = new JSONObject();
				paramsObj = JSON.parseObject(params);
				//sql注入
				InjectUtils.escapeSql(paramsObj);
				
				psql = ParamsSqlHelper.getParamSql(paramsObj.toJSONString(), inParameters,datasourceJSONObject.getString("id"));

				if (StringUtils.isNotEmpty(psql)) {
					continue;
				}

				// 构建联动语句 end
				// 构建sql start
				DataSetBuilder builder = new DataSetBuilder();
				Map<String, Object> parameter = new HashMap<String, Object>();
				parameter.put("HttpServletRequest", request);
				Query query = builder.buildQuery(datasourceJSONObject.getString("datasetId"), psql, "", parameter);
				String sql = query.toSql();
				// 构建sql end
				// 判断是否服务器分页
				List<Map<String, Object>> dataMaps = new ArrayList<Map<String, Object>>();
				// 数据源ID
				Long databaseId = datasourceJSONObject.getLong("databaseId");
				// 执行查询
				dataMaps = mutilDatabaseBean.getDataListBySql(sql, databaseId);
				int total = dataMaps.size();

				// 返回
				if (total > 0) {
					// 节点列表（散列表，用于临时存储节点对象）
					List<JSONObject> nodeList = new ArrayList<JSONObject>();
					JSONObject node = null;

					// 根据结果集构造节点列表（存入散列表）
					for (Map<String, Object> map : dataMaps) {
						Set<String> keys = map.keySet();
						Iterator<String> iterator = keys.iterator();

						node = new JSONObject();
						node.putAll(map);
						while (iterator.hasNext()) {
							String key = iterator.next();
							if (mapping.containsKey(key)) {
								node.put(mapping.getString(key), map.get(key));
							}
						}
						node.put("items", new JSONArray());
						node.put("level", convertLevel(node.getString(id)));//level for gantt
						node.put("startIsMilestone", "false");
						if(node.getLong(parent_id)==-1){
							node.put("startIsMilestone", "true");//startIsMilestone for gantt
						}
						Integer duration = node.getInteger("duration");
						Date start = node.getDate("start");
						Date end = node.getDate("end");
						if(duration==null && start!=null && end!=null){
							node.put("duration", DateUtils.getDaysBetween(start, end));
						}
						nodeList.add(node);
					}

					JSONObject res = new JSONObject();
					res.put("data", JSONArray.parseArray(nodeList.toString()));
					return res.toJSONString().getBytes("UTF-8");
				}
			}
		}
		return new JSONArray().toJSONString().getBytes("UTF-8");
	}
	
	@RequestMapping("/gridData")
	public @ResponseBody byte[] getGridData(HttpServletRequest request,
			@RequestBody DataSourceRequest dataSourceRequest) throws Exception {
		// 获取参数
		String rid = dataSourceRequest.getRid();// 规则id

		// 获取规则MAP
		Map<String, String> ruleMap = getRuleMap(rid);
		if (ruleMap == null) {
			return null;
		}
		String dataSourceSet = ruleMap.get("dataSourceSet");
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONObject datasourceSetJSONObject = null;
		if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
			datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
		}
		if (datasourceSetJSONObject == null) {
			return null;
		}
		
		String dataSourceSetUpdate = ruleMap.get("dataSourceSetUpdate");
		JSONArray dataSourceSetUpdateJSONArray = JSON.parseArray(dataSourceSetUpdate);
		JSONObject dataSourceSetUpdateJSONObject = dataSourceSetUpdateJSONArray.getJSONObject(0);
		String tableMsgString = dataSourceSetUpdateJSONObject.getString("tableMsg");
		JSONObject tableMsg = JSON.parseArray(tableMsgString).getJSONObject(0);
		

		// 数据源ID
		Long databaseId = datasourceSetJSONObject.getLong("databaseId");
		String params = dataSourceRequest.getParams();
		JSONObject paramsObj = new JSONObject();
		//sql注入
		
		JSONObject jo = null;
		if (params != null && !"".equals(params)) {
			paramsObj = JSON.parseObject(params);
			InjectUtils.escapeSql(paramsObj);
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

		String repStr = "_0_";
		String repToStr = ".";
		Map<String, String> keyMap = getKeyMap(ruleMap, false);
		String idkey = keyMap.get("idKey") == null ? "treeid" : keyMap.get("idKey").replace(repStr, repToStr);
		String indexkey = keyMap.get("indexKey") == null ? "index_id"
				: keyMap.get("indexKey").replace(repStr, repToStr);
		String pidkey = keyMap.get("pIdKey") == null ? "parent_id" : keyMap.get("pIdKey").replace(repStr, repToStr);

		// 构建联动语句 start
		String linkageSql = "";
		// 获取 输入表达式定义
		String inParameters = ruleMap.get("inParameters");
		// 获取页面传递参数
		linkageSql = ParamsSqlHelper.getParamSql(paramsObj.toJSONString(), inParameters, datasourceSetJSONObject.getString("datasetId"));
		
		// 构建联动语句 end
		
		
		// 根据输入表达式定义 和 传递参数 获取到 parentidkey 的值 父节点值
		String parentIdKeyValue = ParamsSqlHelper.getTreeListPidkeyValue(pidkey, paramsObj.toJSONString(), inParameters);
		// 排序
		List<SortDescriptor> sorts = dataSourceRequest.getSort();
		String sortSql = "";
		if (sorts != null && sorts.size() > 0) {
			for (SortDescriptor sortDescriptor : sorts) {
				sortSql += sortDescriptor.getField() + " " + sortDescriptor.getDir()
						+ ((sorts.indexOf(sortDescriptor) == sorts.size() - 1) ? " " : ", ");
			}
		}
		// 排序 end

		// where 查询 用于 过滤查询
		FilterDescriptor filterDescriptor = dataSourceRequest.getFilter();
		StringBuilder filterSql = new StringBuilder("");
		if (filterDescriptor != null && filterDescriptor.getFilters() != null
				&& filterDescriptor.getFilters().size() > 0) {
			SqlHelper helper = new SqlHelper();
			helper.getWhereSql(filterSql, filterDescriptor, 1, false);
		}
		// where end

		DataSetBuilder builder = new DataSetBuilder();
		Map<String, Object> parameter = new HashMap<String, Object>();
		Map<String, Object> parameter2 = new HashMap<String, Object>();
		parameter.put("HttpServletRequest", request);
		parameter2.put("HttpServletRequest", request);
		if (jo != null){
			parameter.putAll(paramsObj);
			parameter2.putAll(paramsObj);
		}

		JSONObject result = new JSONObject();// 返回信息
		JSONArray rowsJSON = new JSONArray();
		JSONArray colsJSON = new JSONArray();
		
		// 是否异步加载
		boolean isSync = Boolean.parseBoolean(ruleMap.get("isSync"));
		String sql = "" ;
		String qsql = linkageSql + (!isNullOrEmpty(linkageSql) && !isNullOrEmpty(filterSql.toString()) ? " and " : "")
				+ filterSql.toString() ;
		if(isSync){
			if(keyMap.get("idKey") == null){ //如果没有设置 idkey 的值 走普通查询
				String defaultQuerySql = "(" + pidkey +" is null or  convert(varchar(50)," + pidkey + ") = '-1' or convert(varchar(50),"+pidkey+") = '0' ) " ;
				if (StringUtils.isNotEmpty(dataSourceRequest.getId())) { // 如果当前id不为空  则表明是异步展开节点
					defaultQuerySql = "convert(varchar(50),"+pidkey + ")='" + dataSourceRequest.getId()+"'" ;
				}
				Query query = builder.buildQuery(datasourceSetJSONObject.getString("datasetId"), "".equals(qsql.trim())?defaultQuerySql:qsql, null,parameter);
				sql = query.toSql() ;
			}else{
				String idKs = keyMap.get("idKey") == null ? "base.treeid" : ("base." + keyMap.get("idKey")) ;
				String indexKs = keyMap.get("indexKey") == null ? "base.index_id" : ("base." +keyMap.get("indexKey")) ;
				String parentKs = keyMap.get("pIdKey") == null ? "base.parent_id" : ("base." +keyMap.get("pIdKey")) ;
				parameter.put("buildWhereAndHaving", true); //如果此条件为不动态拼接where sql
				Query baseQuery = builder.buildQuery(datasourceSetJSONObject.getString("datasetId"), null, null,parameter);
				String baseSql = baseQuery.toSql();
				//String orderby = baseSql.substring(baseSql.indexOf("ORDER BY")).replaceAll("\\w+\\.", "base\\.") ;
				if(baseSql.indexOf("SELECT DISTINCT")==0){
					baseSql = baseSql.replaceFirst("SELECT DISTINCT ", "SELECT DISTINCT TOP 100000000000 ") ;
				}else{
					baseSql = baseSql.replaceFirst("SELECT", "SELECT TOP 100000000000 ") ;
				}
				if(baseSql.indexOf("ORDER BY")!=-1){
					baseSql = baseSql.substring(0,baseSql.indexOf("ORDER BY"));
				}
				
				qsql += ("".equals(qsql.trim())? " " : " and ") + idkey + " like " + idKs + "+'%'" ;
				Query query = builder.buildQuery(datasourceSetJSONObject.getString("datasetId"), "".equals(qsql.trim())?null:qsql, null,parameter2);
				String querySql = query.toSql();
				String orderby = "" ;
				if(querySql.indexOf("ORDER BY")!=-1){
					orderby = querySql.substring(querySql.indexOf("ORDER BY")) ;
				}
				String wrapSql = " select * from (" + baseSql+" "+orderby + ") base where exists ( " ;
				StringBuilder syncSql = new StringBuilder("");  //执行sql
				if(querySql.indexOf("ORDER BY")!=-1){
					wrapSql += querySql.substring(0,querySql.indexOf("ORDER BY")) ;
				}else{
					wrapSql += querySql ;
				}
				if (StringUtils.isNotEmpty(dataSourceRequest.getId())) { // 如果当前id不为空  则表明是异步展开节点
					wrapSql +=  ") and " + parentKs + "=" + dataSourceRequest.getId() ;
				}else{
					wrapSql += " ) and " + idKs + " = convert(varchar," + indexKs + ")+'|'" ;
				}
				syncSql.append(wrapSql);
				sql = syncSql.toString();
			}
		}else{
			Query query = builder.buildQuery(datasourceSetJSONObject.getString("datasetId"), "".equals(qsql.trim())?null:qsql, null,parameter);
			sql = query.toSql() ;
		}
		
		List<Map<String, Object>> dataMaps = new ArrayList<Map<String, Object>>();
		dataMaps = mutilDatabaseBean.getDataListBySql(sql, databaseId);
		int total = dataMaps.size();
		if (total > 0) {
			// 过滤
			int count = 1;
			String tablePref = keyMap.get("pIdKey")!=null ? keyMap.get("pIdKey").toString().split(repStr)[0]:"";
			for (Map<String, Object> map : dataMaps) {
				Set<String> keys = map.keySet();
				Iterator<String> iterator = keys.iterator();
				JSONObject josnObj = new JSONObject();
				josnObj.put("startIndex", count);
				
				/*
				 * gantt
				 */
				josnObj.put("startIsMilestone", "false");
				Date start = null;
				Date end = null;
				
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					String[] kk = key.split("_0_");
					String value = map.get(key) != null ? map.get(key).toString() : "";
					if (kk.length > 1 && "id".equalsIgnoreCase(kk[1]) && tablePref.equalsIgnoreCase(kk[0])) {
						josnObj.put("startId", value); // treelist隐藏的id
						josnObj.put(key, value);
					} else {
						josnObj.put(key, value);
					}
					
					if(key.endsWith("nlevel")){
						josnObj.put("level", value);//level for gantt
					}
					
					if((key.endsWith("nlevel") && "0".equals(value)) || (key.endsWith("parent_id") && "-1".equals(value))){
						josnObj.put("startIsMilestone", "true");//startIsMilestone for gantt
					}
					/*
					if(keyMap.get("startKey").equals(key)){
						if(value!=null){
							start = DateUtils.toDate(value);//start for gantt
						}
					}
					
					if(keyMap.get("endKey").equals(key)){
						if(value!=null){
							end = DateUtils.toDate(value);//start for gantt
						}
					}
					*/
				}
				
				//gantt
				/*if(start!=null && end!=null){
					//josnObj.put("duration", DateUtils.getDaysBetween(start, end));
				}
*/
				rowsJSON.add(josnObj);
				count++;
			}
			//数据字段
			/*Map<String,String> kmap = getKeyMap(ruleMap, true);
			//更新集
			Iterator<Object> it = getColumns(tableMsg).iterator();
			
			while(it.hasNext()){
				JSONObject obj = (JSONObject) it.next();
				JSONObject col = new JSONObject();
				String key = kmap.get(obj.getString("fieldName"));
				if("idKey".equals(key)){
					continue;
				}
				key = key!=null? key : obj.getString("id");
				key = "indexKey".equals(key)? "id" : key;
				
				col.put("dType", obj.getString("dType"));
				col.put("protoCode", obj.getString("id"));
				col.put("fieldCode", key.replace("Key", ""));
				col.put("fieldName", obj.getString("fieldName"));
				colsJSON.add(col);
			}
			*/
			result.put("total", total);
			result.put("data", rowsJSON);
			//result.put("cols", colsJSON);
			return result.toJSONString().getBytes("UTF-8");
		}
		result.put("total", 0);
		result.put("data", rowsJSON);
		result.put("cols", colsJSON);
		return result.toJSONString().getBytes("UTF-8");
	}
	
	public Map<String,JSONObject> getMapping(Map<String, String> ruleMap,JSONObject tableMsg) {
		
		Iterator<Object> it = tableMsg.getJSONArray("columns").iterator();
		
		Map<Integer, JSONObject> colsMap = new HashMap<Integer, JSONObject>();
		while(it.hasNext()){
			JSONObject obj = (JSONObject) it.next();
			colsMap.put(obj.getInteger("id"), obj);
		}
		Map<String,JSONObject> map = new HashMap<>();
		
		JSONArray columnTemplateJSONArray = JSONArray.parseArray(ruleMap.get("columnTemplate"));
		for(Object o : columnTemplateJSONArray){
			JSONObject colTmpl = (JSONObject) o;
			Integer id = colTmpl.getInteger("id");
			String columnName =  colTmpl.getString("columnName");
			if(colsMap.containsKey(id)){
				JSONObject col = new JSONObject();
				JSONObject obj = colsMap.get(id);
				col.put("dType", obj.getString("dType"));
				col.put("fieldName", obj.getString("fieldName"));
				col.put("columnName", columnName);
				map.put(columnName, col);
			}
		}
		
		return map;
	}
	
	private JSONArray getColumns(JSONObject tableMsg) {
		JSONArray columns = tableMsg.getJSONArray("columns");
		if(columns!=null){
			return columns;
		}
		return new JSONArray();
	}

	private Map<String, JSONObject> getColumnMapping(JSONObject tableMsg) {
		Map<String,JSONObject> mapping = new HashMap<String, JSONObject>();
		Iterator<Object> it = getColumns(tableMsg).iterator();
		while(it.hasNext()){
			JSONObject obj = (JSONObject) it.next();
			mapping.put("c"+obj.getString("id"), obj);
		}
		return mapping;
	}

	private static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}
	
	private Map<String, String> getKeyMap(Map<String, String> ruleMap, boolean reverse) {
		Map<String, String> map = new HashMap<>();
		String treelistColumns = ruleMap.get("columns");
		JSONArray treelistJSONArray = new JSONArray();
		if (StringUtils.isNotEmpty(treelistColumns)) {
			treelistJSONArray = JSON.parseArray(treelistColumns);
			for (Object object : treelistJSONArray) {
				JSONObject treelistJSONObject = (JSONObject) object;
				if(reverse){
					map.put(treelistJSONObject.getString("ColumnName"), treelistJSONObject.getString("columnType"));
				}else{
					map.put(treelistJSONObject.getString("columnType"), treelistJSONObject.getString("ColumnName"));
				}
			}
		}
		return map;
	}

	@RequestMapping("/saveFormData")
	public @ResponseBody
	byte[] saveFormData(HttpServletRequest request, @RequestBody DataSourceRequest dataSourceRequest) throws Exception {
		
		// 获取参数
		String rid = dataSourceRequest.getRid();// 规则id
		List<?> models = dataSourceRequest.getModels();
		if (models == null || models.size() <= 0) {
			return null;
		}
		
		// 获取规则MAP
		Map<String, String> ruleMap = this.getRuleMap(rid);
		if (ruleMap == null) {
			return ResponseUtils.responseJsonResult(false);
		}
		
		Map<String, String> keyMap = getKeyMap(ruleMap, false);
		
		// 获取
		String dataSourceSetUpdate = ruleMap.get("dataSourceSetUpdate");
		JSONArray dataSourceSetUpdateJSONArray = JSON.parseArray(dataSourceSetUpdate);
		JSONObject dataSourceSetUpdateJSONObject = dataSourceSetUpdateJSONArray.getJSONObject(0);
		JSONObject wdataSet = dataSourceSetUpdateJSONObject.getJSONObject("wdataSet");
		Long wdataSetId = wdataSet.getLong("id");
		String tableMsgString = dataSourceSetUpdateJSONObject.getString("tableMsg");
		JSONObject tableMsg = JSON.parseArray(tableMsgString).getJSONObject(0);
		
		//String tableMsgData = RequestUtils.getString(request, "tableMsg", "");
		
		Map<String, JSONObject> mapping = getMapping(ruleMap,tableMsg);
				//getColumnMapping(tableMsg);
		
		List<Map<String, Object>> params = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> deleteParams = new ArrayList<Map<String, Object>>();
		Map<String, Object> param = null;
		//JSONArray datas = getColumns(tableMsg);
		//JSONArray datas = JSON.parseArray(tableMsgData);
		
		for (int i = 0; i < models.size(); i++) {
			Map<String, Object> obj = (Map<String, Object>) models.get(i);
			Object tasksObj = obj.get("tasks");
			if(tasksObj!=null){
				List<Map<String, Object>> tasks = (List<Map<String, Object>>) tasksObj;
				for (Map<String, Object> object : tasks) {
					param = convertDataField(object, mapping);
					param.putAll(convertData2(object, keyMap));
					param.putAll(convertDataField(param, mapping));
					if(param.isEmpty()){
						continue;
					}
					params.add(param);
				}
			}
			Object deletedTaskIdsObj = obj.get("deletedTaskIds");
			if(deletedTaskIdsObj!=null){
				List<String> deletedTaskIds = (List<String>) deletedTaskIdsObj;
				for (String string : deletedTaskIds) {
					param = new HashMap<>();
					param.put("id", string);
					deleteParams.add(param);
				}
			}
		}
		//depBaseWdataSetService.execBatch(wdataSetId, params);
		try {
			if(!params.isEmpty()){
				DataProcessFactory.getInstance().execBatch(wdataSetId, params);
			}
			if(!deleteParams.isEmpty()){
				DataProcessFactory.getInstance().execRemove(wdataSetId, deleteParams);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.responseJsonResult(false);
		}

		return ResponseUtils.responseJsonResult(true);
	}
	
	private Map<String,Object> convertDataField(Map<String, Object> obj, Map<String, JSONObject> mapping){
		Map<String,Object> data = new HashMap<String,Object>();
		Iterator<String> it = obj.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			if(mapping.containsKey(key)){
				JSONObject json = mapping.get(key);
				String filedName = json.getString("fieldName");
				Object value = obj.get(key);
				if("datetime".equals(json.getString("dType")) && value != null){
					if(value instanceof Date){
						
					}else if(value instanceof String && StringUtils.isNotEmpty(value.toString())){
						value = DateUtils.toDate(value.toString());
					}else if(value instanceof Number){
						value = getDateTime((Long)value);
					}
				}
				data.put(filedName, value);
			}
		}
		return data;
	}
	
	private Map<String, Object> convertData1(Map<String, Object> obj, Map<String, JSONObject> mapping) {
		Iterator<String> it = obj.keySet().iterator();
		Map<String,Object> data = new HashMap<String,Object>();
		while(it.hasNext()){
			String key = it.next();
			if(mapping.containsKey(key)){
				JSONObject json = mapping.get(key);
				String filedName = json.getString("fieldName");
				Object value = obj.get(key);
				if("datetime".equals(json.getString("dType"))){
					value = getDateTime((Long)obj.get(key));
				}
				data.put(filedName, value);
			}
		}
		return data;
	}
	
	private String getDateTime(Long time) {
		Date date = new Date();
		date.setTime(time);
		return DateUtils.getDateTime(date);
	}

	private Map<String, Object> convertData2(Map<String, Object> obj, Map<String, String> keyMap) {
		Map<String,Object> data = new HashMap<String,Object>();
		if(obj.get("id")!=null && !obj.get("id").toString().startsWith("tmp_fk")){
			data.put("id", obj.get("id"));
		}
		data.put(getFieldName(keyMap.get("nameKey")), obj.get("name"));
		data.put(getFieldName(keyMap.get("startKey")), getDateTime((Long)obj.get("start")));
		data.put(getFieldName(keyMap.get("endKey")), getDateTime((Long)obj.get("end")));
		data.put(getFieldName(keyMap.get("durationKey")), obj.get("duration"));
		data.put(getFieldName(keyMap.get("preTaskKey")), obj.get("depends"));
		//data.put("nlevel", obj.get("level"));
		
		return data;
	}

	private String getFieldName(String columnName) {
		columnName = columnName.replace("_0_", ".");
		return columnName.substring(columnName.indexOf(".")+1);
	}

	/**
	 * 加载gantt
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
	@RequestMapping("/view")
	public ModelAndView gantt(HttpServletRequest request, ModelMap modelMap) {
		String rid = RequestUtils.getString(request, "rid", "");
		String frameId = RequestUtils.getString(request, "frameId", "");
		String dataRole = RequestUtils.getString(request, "dataRole", "");
		modelMap.addAttribute("comId", frameId.replace("_iframe", ""));
		modelMap.addAttribute("rid", rid);
		modelMap.addAttribute("frameId", frameId);
		modelMap.addAttribute("dataRole", dataRole);
		return new ModelAndView("/gantt/view", modelMap);
	}

	private Integer convertLevel(String treeId) {
		String[] array = treeId.split("\\|");
		return array.length;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/deleteData")
	public @ResponseBody byte[] deleteData(HttpServletRequest request, @RequestBody DataSourceRequest dataSourceRequest)
			throws Exception {
		// 获取参数
		String rid = dataSourceRequest.getRid();// 规则id
		List<?> models = dataSourceRequest.getModels();
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
		if (tablesJSONArray == null || (tablesJSONArray != null && tablesJSONArray.size() > 1)) { // 如果是多表的
			// 不支持操作
			return null;
		}

		String tableName = tablesJSONArray.getString(0);

		String prefixTableName = tableName + "_0_";

		// 数据源ID
		Long databaseId = datasourceSetJSONObject.getLong("databaseId");
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

		Map<String, Object> map = null;
		String id = null;
		try {
			for (Object object : models) {
				map = (Map<String, Object>) object;
				id = map.get(prefixTableName + "id").toString();
				if (StringUtils.isNotEmpty(id)) {// id不为null时
					mutilDatabaseBean.deleteTableByWhereCause(tableName, " and id = '" + id + "'", databaseId);
					// 级联删除属性
					String casDelete = ruleMap.get("casDelete");
					if ("true".equalsIgnoreCase(casDelete)) {
						String sql = "select TABLENAME from cell_data_table where topid = ( select id from cell_data_table where tablename =  '"
								+ tableName + "')";
						List<Map<String, Object>> subTables = mutilDatabaseBean.getDataListBySql(sql, databaseId);
						if (subTables != null && subTables.size() > 0) {
							for (Map<String, Object> map2 : subTables) {
								Map<String, Object> subTableMap = map2;
								String subTableName = (String) subTableMap.get("TABLENAME");
								mutilDatabaseBean.deleteTableByWhereCause(subTableName, " and topid = '" + id + "'",
										databaseId);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("data delete" + e.getMessage());
			return ResponseUtils.responseJsonResult(false);
		}
		//return this.getGridData(request, dataSourceRequest);
		return ResponseUtils.responseJsonResult(true);
	}
}
