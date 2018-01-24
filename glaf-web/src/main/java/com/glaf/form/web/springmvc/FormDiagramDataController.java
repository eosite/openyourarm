package com.glaf.form.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.metamodel.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.util.RequestUtils;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.form.core.util.MutilDatabaseBean;
import com.glaf.form.rule.ParamsSqlHelper;
import com.glaf.form.rule.util.InjectUtils;

@Controller("/form/diagram")
@RequestMapping("/form/diagram")
public class FormDiagramDataController {

	protected static final Log logger = LogFactory.getLog(FormDiagramDataController.class);

	protected MutilDatabaseBean mutilDatabaseBean;

	protected FormRulePropertyService formRulePropertyService;

	@Resource
	public void setMutilDatabaseBean(MutilDatabaseBean mutilDatabaseBean) {
		this.mutilDatabaseBean = mutilDatabaseBean;
	}

	@Resource
	public void setFormRulePropertyService(FormRulePropertyService formRulePropertyService) {
		this.formRulePropertyService = formRulePropertyService;
	}

	private Map<String, String> getRuleMap(String rid) {
		return this.formRulePropertyService.getRuleMap(rid);
	}

	@ResponseBody
	@RequestMapping("/data")
	public byte[] getDiagramSet(HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		JSONObject paramsObj = new JSONObject();
		if(!params.isEmpty()){
			paramsObj = (JSONObject) JSONObject.toJSON(params);
			InjectUtils.escapeSql(paramsObj);
		}
		
		String rid = paramsObj.get("rid") + "";

		// 获取规则MAP
		Map<String, String> ruleMap = this.getRuleMap(rid);
		if (ruleMap == null) {
			return null;
		}

		// 获取
		String dataSourceSet = ruleMap.get("dataSourceSetByDiagram");
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONArray selectDatasourceJSONArray = null;
		JSONObject datasourceJSONObject = null;
		JSONArray selectColumnsJSONArray = null;

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
					if (StringUtils.isNotEmpty(ctype)) {
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
				psql = ParamsSqlHelper.getParamSql(paramsObj.toJSONString(), inParameters,
						datasourceJSONObject.getString("id"));

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
					HashMap<Integer, JSONObject> nodeList = new HashMap<Integer, JSONObject>();
					JSONObject node = null;

					// 根据结果集构造节点列表（存入散列表）
					for (Map<String, Object> map : dataMaps) {
						Set<String> keys = map.keySet();
						Iterator<String> iterator = keys.iterator();

						node = new JSONObject();
						node.putAll(map);
						while (iterator.hasNext()) {
							String key = iterator.next();
							if (result.containsKey(key)) {
								node.put(result.getString(key), map.get(key));
							}
						}
						node.put("items", new JSONArray());
						nodeList.put(node.getInteger("index_id"), node);
					}

					// 构造无序的多叉树
					node = null;
					List<JSONObject> rootList = new ArrayList<JSONObject>();
					Set<Entry<Integer, JSONObject>> entrySet = nodeList.entrySet();
					for (Iterator<Entry<Integer, JSONObject>> it = entrySet.iterator(); it.hasNext();) {
						Entry<Integer, JSONObject> entry = it.next();
						node = entry.getValue();
						if (node.getInteger("parent_id") == null || node.getInteger("parent_id") == -1) {
							rootList.add(node);
						} else {
							JSONObject obj = nodeList.get(node.getInteger("parent_id"));
							obj.getJSONArray("items").add(node);
						}
					}
					return JSONArray.parseArray(rootList.toString()).toJSONString().getBytes("UTF-8");
				}
			}
		}
		return new JSONArray().toJSONString().getBytes("UTF-8");
	}
}
