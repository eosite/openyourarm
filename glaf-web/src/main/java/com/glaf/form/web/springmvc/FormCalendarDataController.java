package com.glaf.form.web.springmvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.form.core.util.MutilDatabaseBean;
import com.glaf.form.rule.DataSourceRequest;
import com.glaf.form.rule.ParamsSqlHelper;
import com.glaf.form.rule.util.InjectUtils;

/**
 * 通用日历数据调用
 * 
 * @author lvd
 *
 */
@Controller("/form/calendar")
@RequestMapping("/form/calendar")
public class FormCalendarDataController {
	protected static final Log logger = LogFactory.getLog(FormCalendarDataController.class);

	protected MutilDatabaseBean mutilDatabaseBean;

	protected FormRulePropertyService formRulePropertyService;

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
	 * 获取日历事项记录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/data")
	public @ResponseBody byte[] getCalendarData(HttpServletRequest request,
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
		if (datasourceSetJSONObject == null || datasourceSetJSONObject.isEmpty()) {
			return null;
		}

		// 构建联动语句 start
		
		//数据集
		JSONArray selectDataSources = datasourceSetJSONObject.getJSONArray("selectDatasource");
		if(selectDataSources.isEmpty()){
			return null;
		}
		JSONObject selectDataSource = selectDataSources.getJSONObject(0) ;
		String onlyId = selectDataSource.getString("id");
		String datasetId = selectDataSource.getString("datasetId");
		//数据字段
		JSONArray selectColumns = datasourceSetJSONObject.getJSONArray("selectColumns");
		JSONObject selectColumn = null ;
		String ctype = "" ;
		String columnName = "" ;

		String typeColumn = "" ;  //日历类型 字段
		String titleColumn = "" ; //日历事项标题 字段
		String startColumn = "" ; //日历事项开始时间 字段
		String stopColumn = "" ; //日历事项结束时间 字段
		String bgcColumn = "" ; //日历事项背景色       字段
		String allDayColumn = "";//日历事项是否全天 字段
		String urlColumn = "";//日历事项跳转链接 字段
		String remarkColumn = "" ; //日历事项详细备注 字段
		for (Object object : selectColumns) {
			selectColumn = (JSONObject) object ;
			ctype = selectColumn.getString("ctype");
			columnName = selectColumn.getString("columnName");
		    if("type".equalsIgnoreCase(ctype)){
				typeColumn = columnName ;
			}else if("title".equalsIgnoreCase(ctype)){
				titleColumn = columnName ;
			}else if("start".equalsIgnoreCase(ctype)){
				startColumn = columnName ;
			}else if("end".equalsIgnoreCase(ctype)){
				stopColumn = columnName ;
			}else if("backgroundColor".equalsIgnoreCase(ctype)){
				bgcColumn = columnName ;
			}else if("allDay".equalsIgnoreCase(ctype)){
				allDayColumn = columnName ;
			}else if("remark".equalsIgnoreCase(ctype)){
				remarkColumn = columnName ;
			}
		}
		
		String psql = "";
		// 获取 输入表达式定义
		String inParameters = ruleMap.get("inParameters");
		// 获取页面传递参数
		String params = dataSourceRequest.getParams();
		JSONObject paramsObj = new JSONObject();
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("HttpServletRequest", request);
		if (!isNullOrEmpty(params)) {
			paramsObj = JSON.parseObject(params);
			InjectUtils.escapeSql(paramsObj);
			parameter.putAll(paramsObj);
		}
		psql = ParamsSqlHelper.getParamSql(paramsObj.toJSONString(), inParameters, onlyId);

		// 构建联动语句 end
		// //System.out.println("联动语句"+psql);

		// 返回
		JSONObject result = new JSONObject();

		// 构建sql start
		DataSetBuilder builder = new DataSetBuilder();
		
		
		Query query = builder.buildQuery(datasetId, psql, "", parameter);
		String sql = query.toSql();
		// 构建sql end


		// 数据源ID
		Long databaseId = selectDataSource.getLong("databaseId");
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
		List<Map<String, Object>> dataMaps = mutilDatabaseBean.getDataListBySql(sql, databaseId);
		int total = dataMaps.size();

		// 执行查询	
		if (total > 0) {
			
			String type = "" ;  //日历类型 字段
			String title = "" ; //日历事项标题 字段
			String start = "" ; //日历事项开始时间 字段
			String stop = "" ; //日历事项结束时间 字段
			String bgc = "" ; //日历事项背景色       字段
			String allDay = "";//日历事项是否全天 字段
			String remark = "" ; //日历事项详细备注 字段
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List calendarList = new ArrayList<>();
			
			
			for(Map<String, Object> map:dataMaps){
				Map<String,String> calendarMap = new HashMap<String,String>();
				Set<String> keys = map.keySet();
				Iterator<String> iterator = keys.iterator();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					if (typeColumn.equalsIgnoreCase(key)) {
						type = (String) map.get(key);
					}else if(titleColumn.equalsIgnoreCase(key)){
						title = (String) map.get(key);
					}else if(startColumn.equalsIgnoreCase(key)){
						if(map.get(key)!=null){
							start = sdf.format(map.get(key));							
						}else{
							start = "";
						}
					}else if(stopColumn.equalsIgnoreCase(key)){
						if(map.get(key)!=null){
							stop = sdf.format(map.get(key));
						}else{
							stop = "";
						}
					}else if(bgcColumn.equalsIgnoreCase(key)){
						bgc = (String) map.get(key);
					}else if(allDayColumn.equalsIgnoreCase(key)){
						allDay = (String) map.get(key);
					}else if(remarkColumn.equalsIgnoreCase(key)){
						remark = (String) map.get(key);
					}
					calendarMap.put(key, map.get(key)!=null?map.get(key).toString():null);
				}
				calendarMap.put("type",type);
				calendarMap.put("title",title);
				calendarMap.put("start",start);
				calendarMap.put("end",stop);
				calendarMap.put("backgroundColor",bgc);
				calendarMap.put("allDay",allDay);
				calendarMap.put("remark",remark);
				calendarList.add(calendarMap);
			}
			JSONArray jArray = new JSONArray();
			jArray.addAll(calendarList);
			byte[] input = jArray.toJSONString().getBytes("UTF-8");				
			return input;
		} else {
			return new JSONArray().toJSONString().getBytes("UTF-8");
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
