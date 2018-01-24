package com.glaf.form.web.springmvc;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.metamodel.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.form.core.service.FormRuleService;
import com.glaf.form.core.util.MutilDatabaseBean;
import com.glaf.form.rule.DataSourceRequest;
import com.glaf.form.rule.ParamsSqlHelper;
import com.glaf.form.rule.util.InjectUtils;

/**
 * 通用数据调用
 * 
 */
@Controller("/form/cellset")
@RequestMapping("/form/cellset")
public class FormCellSetController {
	protected static final Log logger = LogFactory.getLog(FormCellSetController.class);

	private static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	private static String trim(String str, String syn) {
		if (!isNullOrEmpty(str) && !isNullOrEmpty(syn)) {
			if (str.startsWith(syn))
				str = str.trim().substring(syn.length());
			if (str.endsWith(syn))
				str = str.trim().substring(0, str.length() - syn.length());
		}
		return str;
	}

	protected FormRulePropertyService formRulePropertyService;

	protected MutilDatabaseBean mutilDatabaseBean;

	protected FormRuleService formRuleService;

	public FormCellSetController() {

	}

	@RequestMapping("/datas")
	public @ResponseBody byte[] getChartsDatas(HttpServletRequest request,
			@RequestBody DataSourceRequest dataSourceRequest) throws Exception {
		// 获取参数
		String rid = dataSourceRequest.getRid();// 规则id

		// 获取规则MAP
		Map<String, String> ruleMap = getRuleMap(rid);
		if (ruleMap == null) {
			return null;
		}

		// 获取
		String dataSourceSet = ruleMap.get("dataSourceSet");
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONArray selectDatasourceJSONArray = null;
		JSONObject datasourceJSONObject = null;

		JSONArray retArray = new JSONArray();
		JSONObject result = new JSONObject();
		int count = 0;
		String params = dataSourceRequest.getParams();
		JSONObject paramsObj = new JSONObject();
		if(StringUtils.isNotEmpty(params)){
			paramsObj = JSONObject.parseObject(params);
			InjectUtils.escapeSql(paramsObj);
		}
		if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
			JSONObject datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
			selectDatasourceJSONArray = datasourceSetJSONObject.getJSONArray("selectDatasource");
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
				psql = ParamsSqlHelper.getParamSql(paramsObj.toJSONString(), inParameters, datasourceJSONObject.getString("id"));
				// 构建联动语句 end
				// 构建sql start
				DataSetBuilder builder = new DataSetBuilder();
				Map<String, Object> parameter = new HashMap<String, Object>();
				parameter.put("HttpServletRequest", request);
				if (!paramsObj.isEmpty()) {
					parameter.putAll(paramsObj);
				}
				Query query = builder.buildQuery(datasourceJSONObject.getString("datasetId"), psql, "", parameter);
				String sql = query.toSql();
				// 构建sql end
				// 判断是否服务器分页
				List<Map<String, Object>> dataMaps = new ArrayList<Map<String, Object>>();
				int total = 0;
				// 数据源ID
				Long databaseId = datasourceJSONObject.getLong("databaseId");

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
				// 过滤条数
				// dataMaps = mutilDatabaseBean.getDataListByQueryCriteria(sql,
				// 0, 1, databaseId);
				// 返回
				if (total > 0) {

					int limit = 0;

					if (parameter != null && parameter.containsKey("_ROWNUMBER_") && //
							MapUtils.getInteger(parameter, "_ROWNUMBER_") > 0) {
						limit = MapUtils.getInteger(parameter, "_ROWNUMBER_");
					}

					if (limit > 0)
						dataMaps = mutilDatabaseBean.getDataListBySqlCriteria(sql, 0, limit, databaseId);
					else
						dataMaps = mutilDatabaseBean.getDataListBySql(sql, databaseId);

					JSONArray rowsJSON = new JSONArray();
					for (Map<String, Object> map : dataMaps) {
						Set<String> keys = map.keySet();
						Iterator<String> iterator = keys.iterator();
						JSONObject josnObj = new JSONObject();
						while (iterator.hasNext()) {
							String key = (String) iterator.next();
							if (!key.toLowerCase().equals(key)) {
								josnObj.put(key, map.get(key) != null ? map.get(key).toString() : "");
							}
							josnObj.put(key.toLowerCase(), map.get(key) != null ? map.get(key).toString() : "");
						}
						josnObj.put("databaseId", databaseId + "");
						rowsJSON.add(josnObj);
					}
					result = new JSONObject();
					result.put("id", datasourceJSONObject.getString("id"));
					result.put("data", rowsJSON);
					retArray.add(result);
				}
			}
		}
		if (count > 0) {
			String curveType = ruleMap.get("curveType");
			JSONArray curveTypeJSONArray = JSON.parseArray(curveType);

			if (curveTypeJSONArray != null && curveTypeJSONArray.size() > 0) {
				JSONObject curveTypeJSON = (JSONObject) curveTypeJSONArray.get(0);
				int typeNum = curveTypeJSON.getIntValue("type");
				// 1为正态分布
				// n个数据集
				for (int i = 0; i < retArray.size(); i++) {
					// 一个数据集的数据
					JSONObject jsonObj = retArray.getJSONObject(i);
					JSONArray rowsJSON = new JSONArray();
					if (typeNum == 1) {
						rowsJSON = normalDistribution(curveTypeJSON, jsonObj.getJSONArray("data"),
								jsonObj.getString("id"));
					} else if (typeNum == 2 || typeNum == 3 || typeNum == 4) {
						// 获取页面传递参数
						rowsJSON = xbarMethod(curveTypeJSON, jsonObj.getJSONArray("data"), jsonObj.getString("id"),
								typeNum, paramsObj.toJSONString());
					}
					jsonObj.put("data", rowsJSON);
				}
			}

			return retArray.toJSONString().getBytes("UTF-8");
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("id", "");
			result.put("data", rowsJSON);
			retArray.add(result);
			return retArray.toJSONString().getBytes("UTF-8");
		}

	}
	
	@RequestMapping("/downLoadFile")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] downLoadFile(HttpServletRequest request ,HttpServletResponse response) throws Exception {
		
		// 获取参数
		String rid = RequestUtils.getString(request, "rid");// 规则id
		Map<String, Object> parameterMap = RequestUtils.getParameterMap(request);

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

		// 构建联动语句 start
		
		//数据集
		JSONArray selectDataSources = datasourceSetJSONObject.getJSONArray("selectDatasource");
		JSONObject selectDataSource = selectDataSources.getJSONObject(0) ;
		String onlyId = selectDataSource.getString("id");
		String datasetId = selectDataSource.getString("datasetId");
		//数据字段
		JSONArray selectColumns = datasourceSetJSONObject.getJSONArray("selectColumns");
		JSONObject selectColumn = null ;
		String ntype = "" ;
		String columnName = "fileContext" ;
		for (Object object : selectColumns) {
			selectColumn = (JSONObject) object ;
			
			if(selectColumn.getString("ctype").equals("fileContent")){
				columnName = selectColumn.getString("columnName");	
			}

		}
		
		String psql = "";
		// 获取 输入表达式定义
		String inParameters = ruleMap.get("inParameters");
		// 获取页面传递参数

		// 构建联动语句 end
		// //System.out.println("联动语句"+psql);

		// 返回
		JSONObject result = new JSONObject();
		
		JSONObject jo = (JSONObject) JSON.toJSON(parameterMap);

		// 构建sql start
		DataSetBuilder builder = new DataSetBuilder();
		Map<String, Object> parameter = new HashMap<String, Object>();
		String param = String.valueOf(parameterMap.get("params"));
		if(parameterMap!=null && !parameterMap.isEmpty()){
			parameter.putAll(parameterMap);
		}
		if(param!=null && !param.isEmpty()){
			parameter.putAll(JSON.parseObject(param));
		}
		parameter.put("HttpServletRequest", request);

		Query query = builder.buildQuery(datasetId, psql, "", parameter);
		String sql = query.toSql();
		// 构建sql end


		// 数据源ID
		Long databaseId = selectDataSource.getLong("databaseId");
		// 获取页面传递参数
		if (parameterMap != null && !parameterMap.isEmpty()) {
			try {
				Long dbid = jo.getLong("databaseId");
				
				if (dbid != null) {
					databaseId = dbid;
				}
				else{
					if(param!=null && !param.isEmpty()){
						dbid = JSON.parseObject(param).getLong("databaseId");
						if (dbid != null) {
							databaseId = dbid;
						}
					}
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
			
			Object fileContextObj = null ;  //摄像头安全码 字段
			
			Map<String, Object> map = dataMaps.get(0) ;
			Set<String> keys = map.keySet();
			Iterator<String> iterator = keys.iterator();
			
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				if (columnName.equalsIgnoreCase(key)) {
					fileContextObj = map.get(key);
				}
			}
			Date date = new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			ResponseUtils.download(request, response, (byte[])fileContextObj, sdf.format(new Date()));
			
			return (byte[])fileContextObj;
		} else {
			return "".getBytes("UTF-8");
		}
	}
	/**
	 * 正态分布计算方法
	 * 
	 * @param curveTypeJSON
	 * @return
	 */
	private JSONArray normalDistribution(JSONObject curveTypeJSON, JSONArray rowsJSON, String seq) {
		// 返回信息
		JSONArray retArray = new JSONArray();

		// 左边字段列表
		JSONArray leftColumns = curveTypeJSON.getJSONArray("leftColumns");
		// 正态分布计算的列 字段名称
		String columnName = "";
		for (int i = 0; i < leftColumns.size(); i++) {
			JSONObject leftColumn = leftColumns.getJSONObject(i);
			if (leftColumn.getString("seq").equals(seq)) {
				columnName = leftColumn.getString("columnName");
				break;
			}
		}

		// 属性值
		JSONObject prosJSON = curveTypeJSON.getJSONObject("pros");
		// 组数
		int groupNum = prosJSON.getJSONObject(seq).getIntValue("groupNum");
		// 分组后的值
		List<BigDecimal> rowsValue = new ArrayList<BigDecimal>();
		BigDecimal mean = new BigDecimal("0");// 数学期望
		BigDecimal standardDev = new BigDecimal("0"); // 标准差
		BigDecimal min = new BigDecimal("0"); // 数据中最小值
		BigDecimal max = new BigDecimal("0"); // 数据中最大值
		BigDecimal degree = new BigDecimal("0"); // 精度（中间间隔）

		boolean first = true; // 第一次
		int n = 0;

		// 遍历数据，获取数学期望
		for (n = 0; n < rowsJSON.size(); n++) {
			JSONObject row = rowsJSON.getJSONObject(n);
			// 获取列的值
			BigDecimal value = new BigDecimal(
					row.get(columnName) != null && !"".equals(row.get(columnName)) ? row.getString(columnName) : "0");
			rowsValue.add(value);
			// 赋值最小最大值
			if (first) {
				min = value;
				max = value;
				first = false;
			} else {
				min = value.compareTo(min) < 0 ? value : min;
				max = value.compareTo(max) > 0 ? value : max;
			}

			mean = mean.add(value);
		}
		// 对于样本超大时，数学期望近似于平均值
		mean = mean.divide(new BigDecimal(n), 10, BigDecimal.ROUND_HALF_UP);

		rowsValue.sort(new Comparator<BigDecimal>() {
			@Override
			public int compare(BigDecimal o1, BigDecimal o2) {
				return o1.compareTo(o2);
			}
		});

		// 计算方差
		for (BigDecimal rowValue : rowsValue) {
			standardDev = standardDev.add(rowValue.subtract(mean).pow(2));
		}
		// 标准差为0不可能
		if (standardDev.compareTo(new BigDecimal(0)) == 0) {
			return retArray;
		}
		BigDecimal b = n <= 1 ? new BigDecimal(1) : new BigDecimal(n - 1);
		standardDev = standardDev.divide(b, 2, BigDecimal.ROUND_HALF_UP);

		// BigDecimal minFloor = new BigDecimal(Math.floor(min.doubleValue()));
		// //最小值向下取整
		// BigDecimal maxCeil = new BigDecimal(Math.ceil(max.doubleValue()));
		// //最大值向上取整
		degree = max.subtract(min).divide(new BigDecimal(groupNum), 3, BigDecimal.ROUND_HALF_UP); // 精度,中间间隔
		NormalDistribution normalDistributioin = new NormalDistribution(mean.doubleValue(),
				Math.sqrt(standardDev.doubleValue()));
		int k = 0;
		BigDecimal group = min.setScale(2, BigDecimal.ROUND_HALF_UP);
		do {
			// 返回的每一行信息
			JSONObject groupObject = new JSONObject();
			groupObject.put("groupName", group.doubleValue());
			int frequentNum = 0;
			for (; k < rowsValue.size(); k++) {
				BigDecimal rowValue = rowsValue.get(k);
				if (rowValue.compareTo(group.subtract(degree)) >= 0 && rowValue.compareTo(group) < 0) {
					frequentNum++;
				}
				if (rowValue.compareTo(group) > 0) {
					break;
				}
			}
			groupObject.put("frequentNum", frequentNum * 1.0 / n); // 频度
			groupObject.put("valueName", normalDistributioin.density(group.doubleValue()));
			retArray.add(groupObject);
			group = group.add(degree);
		} while (group.compareTo(max) < 0);

		return retArray;
	}

	private JSONArray xbarMethod(JSONObject curveTypeJSON, JSONArray rowsJSON, String seq, int typeNum, String params) {
		// 返回信息
		JSONArray retArray = new JSONArray();

		// 左边字段列表
		JSONArray leftColumns = curveTypeJSON.getJSONArray("leftColumns");
		// 正态分布计算的列 字段名称
		String datacol = "";
		String indexCol = "";
		String indexColFieldType = "";
		for (int i = 0; i < leftColumns.size(); i++) {
			JSONObject leftColumn = leftColumns.getJSONObject(i);
			if (leftColumn.getString("seq").equals(seq)) {
				if (leftColumn.getString("calculeType").equals("dataCol")) {
					datacol = leftColumn.getString("columnName");
				}
				if (leftColumn.getString("calculeType").equals("indexCol")) {
					indexCol = leftColumn.getString("columnName");
					indexColFieldType = leftColumn.getString("FieldType");
				}
			}
		}

		// 属性值
		JSONObject prosJSON = curveTypeJSON.getJSONObject("pros");
		// 属性
		int dataNum = prosJSON.getJSONObject(seq).getIntValue("dataNum"); // 组数
		int avgNum = prosJSON.getJSONObject(seq).getIntValue("avgNum"); // 平均组数

		double uplimit = prosJSON.getJSONObject(seq).getDoubleValue("uplimit"); // 上限
		double downlimit = prosJSON.getJSONObject(seq).getDoubleValue("downlimit"); // 下限
		JSONObject jo = null;
		// 获取页面传递参数
		if (params != null && !"".equals(params)) {
			jo = JSON.parseObject(params);
			try {
				if (jo.get(seq + ".组数") != null) {
					dataNum = jo.getIntValue(seq + ".组数");
				}
				if (jo.get(seq + ".平均组数") != null) {
					avgNum = jo.getIntValue(seq + ".平均组数");
				}
				if (jo.get(seq + ".容许上限") != null) {
					uplimit = jo.getIntValue(seq + ".容许上限");
				}
				if (jo.get(seq + ".容许下限") != null) {
					downlimit = jo.getIntValue(seq + ".容许下限");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("x-bar图页面参数转换失败" + e.getMessage());
			}
		}

		final String indexColStr = indexCol;
		final String indexColFieldTypeStr = indexColFieldType;
		// 对rowJSON根据indexCol进行排序
		rowsJSON.sort(new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				JSONObject k1 = (JSONObject) o1;
				JSONObject k2 = (JSONObject) o2;

				if (indexColFieldTypeStr.equals("datetime")) {
					// 若是时间类型
					return k1.getDate(indexColStr).compareTo(k2.getDate(indexColStr));
				}

				return k1.getDouble(indexColStr).compareTo(k2.getDouble(indexColStr));
			}

		});

		// 计算xbar与x2bar
		List<Map<String, Object>> resultList = new ArrayList();
		qualityControl(resultList, rowsJSON, 0, dataNum, avgNum, datacol, indexCol);

		if (typeNum == 2 || typeNum == 4) {
			double a2 = prosJSON.getJSONObject(seq).getDoubleValue("a2"); // A2
			// 获取页面传递参数
			if (jo != null) {
				try {
					if (jo.get(seq + ".A2") != null) {
						a2 = jo.getDoubleValue(seq + ".A2");
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("x-bar图页面参数A2转换失败" + e.getMessage());
				}
			}
			double datum1 = prosJSON.getJSONObject(seq).getDoubleValue("datum1"); // datum1
			double datum2 = prosJSON.getJSONObject(seq).getDoubleValue("datum2"); // datum2
			double datum3 = prosJSON.getJSONObject(seq).getDoubleValue("datum3"); // datum3
			double datum4 = prosJSON.getJSONObject(seq).getDoubleValue("datum4"); // datum4
			double datum5 = prosJSON.getJSONObject(seq).getDoubleValue("datum5"); // datum5
			// 获取页面传递参数
			if (typeNum == 4 && jo != null) {
				try {
					if (jo.get(seq + ".datum1") != null) {
						datum1 = jo.getDoubleValue(seq + ".datum1");
					}
					datum2 = jo.get(seq + ".datum2") != null ? jo.getDoubleValue(seq + ".datum2") : datum2;
					datum3 = jo.get(seq + ".datum3") != null ? jo.getDoubleValue(seq + ".datum3") : datum3;
					datum4 = jo.get(seq + ".datum4") != null ? jo.getDoubleValue(seq + ".datum4") : datum4;
					datum5 = jo.get(seq + ".datum5") != null ? jo.getDoubleValue(seq + ".datum5") : datum5;
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("cp图页面参数基准线转换失败" + e.getMessage());
				}
			}
			// X-BAR
			int i = 0;
			int k = 1;
			for (Map<String, Object> resultMap : resultList) {
				double x2bar = ((BigDecimal) resultMap.get("x2bar")).doubleValue();
				double xbar = ((BigDecimal) resultMap.get("xbar")).doubleValue();
				double r = ((BigDecimal) resultMap.get("r")).doubleValue();
				double rbar = ((BigDecimal) resultMap.get("rbar")).doubleValue();
				double a2rbar = a2 * rbar;
				if (typeNum == 2) {
					JSONObject groupObject = new JSONObject();
					groupObject.put("x2bar", resultMap.get("x2bar"));
					groupObject.put("xbar", resultMap.get("xbar"));
					groupObject.put("r", resultMap.get("r"));
					groupObject.put("rbar", resultMap.get("rbar"));
					groupObject.put("indexCol", resultMap.get("indexCol"));
					groupObject.put("ucl", new BigDecimal(x2bar + a2rbar).setScale(3, BigDecimal.ROUND_HALF_UP));
					groupObject.put("lcl", new BigDecimal(x2bar - a2rbar).setScale(3, BigDecimal.ROUND_HALF_UP));
					groupObject.put("uplimit", new BigDecimal(uplimit).setScale(3, BigDecimal.ROUND_HALF_UP));
					groupObject.put("downlimit", new BigDecimal(downlimit).setScale(3, BigDecimal.ROUND_HALF_UP));
					retArray.add(groupObject);
				} else if (i % dataNum == 0) {
					BigDecimal cpk = new BigDecimal(uplimit - x2bar).divide(new BigDecimal(a2rbar), 3,
							BigDecimal.ROUND_HALF_UP);
					BigDecimal cpk2 = new BigDecimal(downlimit - x2bar).divide(new BigDecimal(a2rbar), 3,
							BigDecimal.ROUND_HALF_UP);
					JSONObject groupObject = new JSONObject();
					groupObject.put("x2bar", resultMap.get("x2bar"));
					groupObject.put("xbar", resultMap.get("xbar"));
					groupObject.put("r", resultMap.get("r"));
					groupObject.put("rbar", resultMap.get("rbar"));
					groupObject.put("indexCol", "第" + k + "阶段");
					groupObject.put("datum(" + datum1 + ")", datum1);
					groupObject.put("datum(" + datum2 + ")", datum2);
					groupObject.put("datum(" + datum3 + ")", datum3);
					groupObject.put("datum(" + datum4 + ")", datum4);
					groupObject.put("datum(" + datum5 + ")", datum5);
					groupObject.put("ucl", new BigDecimal(x2bar + a2rbar).setScale(3, BigDecimal.ROUND_HALF_UP));
					groupObject.put("lcl", new BigDecimal(x2bar - a2rbar).setScale(3, BigDecimal.ROUND_HALF_UP));
					groupObject.put("cp", new BigDecimal(uplimit - downlimit).divide(new BigDecimal(2 * a2rbar), 3,
							BigDecimal.ROUND_HALF_UP));
					groupObject.put("cpk", cpk.compareTo(cpk2) > 0 ? cpk : cpk2);
					groupObject.put("uplimit", new BigDecimal(uplimit).setScale(3, BigDecimal.ROUND_HALF_UP));
					groupObject.put("downlimit", new BigDecimal(downlimit).setScale(3, BigDecimal.ROUND_HALF_UP));
					retArray.add(groupObject);
					k++;
				}
				i++;
			}
		} else if (typeNum == 3) {
			double d4 = prosJSON.getJSONObject(seq).getDoubleValue("d4"); // D4
			double d3 = prosJSON.getJSONObject(seq).getDoubleValue("d3"); // D3
			// 获取页面传递参数
			if (jo != null) {
				try {
					if (jo.get(seq + ".D3") != null) {
						d3 = jo.getDoubleValue(seq + ".D3");
					}
					if (jo.get(seq + ".D4") != null) {
						d4 = jo.getDoubleValue(seq + ".D4");
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("x-bar图页面参数D3、D4转换失败" + e.getMessage());
				}
			}
			// R 图
			for (Map<String, Object> resultMap : resultList) {
				double x2bar = ((BigDecimal) resultMap.get("x2bar")).doubleValue();
				double xbar = ((BigDecimal) resultMap.get("xbar")).doubleValue();
				double r = ((BigDecimal) resultMap.get("r")).doubleValue();
				double rbar = ((BigDecimal) resultMap.get("rbar")).doubleValue();

				JSONObject groupObject = new JSONObject();
				groupObject.put("x2bar", resultMap.get("x2bar"));
				groupObject.put("xbar", resultMap.get("xbar"));
				groupObject.put("r", resultMap.get("r"));
				groupObject.put("rbar", resultMap.get("rbar"));
				groupObject.put("indexCol", resultMap.get("indexCol"));

				groupObject.put("ucl", new BigDecimal(d4 * rbar).setScale(3, BigDecimal.ROUND_HALF_UP));
				groupObject.put("lcl", new BigDecimal(d3 * rbar).setScale(3, BigDecimal.ROUND_HALF_UP));
				groupObject.put("uplimit", new BigDecimal(uplimit).setScale(3, BigDecimal.ROUND_HALF_UP));
				groupObject.put("downlimit", new BigDecimal(downlimit).setScale(3, BigDecimal.ROUND_HALF_UP));

				retArray.add(groupObject);
			}
		}

		return retArray;
	}

	/**
	 * 计算X-bar 、 R 、 X-2bar 、 R-bar的值
	 * 
	 * @param resultList
	 * @param rowsJSON
	 * @param n
	 *            从0开始
	 * @param dataNum
	 * @param avgNum
	 * @param datacol
	 *            数据名称
	 * @param indexcol
	 *            序号
	 */
	private void qualityControl(List<Map<String, Object>> resultList, JSONArray rowsJSON, int n, int dataNum,
			int avgNum, String datacol, String indexcol) {
		// 计算完毕
		if (n >= rowsJSON.size()) {
			return;
		}
		JSONObject row = rowsJSON.getJSONObject(n);

		if (n >= dataNum - 1) {
			// 数据
			String indexValue = row.getString(indexcol);

			// 计算前X次平均值
			Map<String, Object> map = new HashMap<>();

			// 计算前X次平均值和极差
			Double avg = 0.0; // 平均值
			Double max = null; // 最大值
			Double min = null; // 最小值
			for (int i = n - dataNum + 1; i <= n; i++) {
				JSONObject row2 = rowsJSON.getJSONObject(i);
				Double dataValue = row2.getDouble(datacol);
				max = max == null ? dataValue : (max.compareTo(dataValue) >= 0 ? max : dataValue);
				min = min == null ? dataValue : (min.compareTo(dataValue) <= 0 ? min : dataValue);
				avg += dataValue;
			}
			// avg = avg / dataNum;
			double r = max - min; // 极差
			map.put("xbar", new BigDecimal(avg).divide(new BigDecimal(dataNum), 2, BigDecimal.ROUND_HALF_UP));
			map.put("r", new BigDecimal(r));

			map.put("indexCol", indexValue);
			resultList.add(map);

			// 计算Y次平均的平均值
			int size = resultList.size();
			int modValue = size % avgNum;
			if (modValue == 0 || n == rowsJSON.size() - 1) {
				double x2bar = 0.0;
				double rbar = 0.0;
				double avgCount = modValue == 0 ? avgNum : modValue;
				for (int i = 1; i <= avgCount; i++) {
					x2bar += ((BigDecimal) resultList.get(size - i).get("xbar")).doubleValue();
					rbar += ((BigDecimal) resultList.get(size - i).get("r")).doubleValue();
				}
				BigDecimal acgCountBig = new BigDecimal(avgCount);
				BigDecimal x2barbig = new BigDecimal(x2bar).divide(acgCountBig, 2, BigDecimal.ROUND_HALF_UP);
				BigDecimal rbarbig = new BigDecimal(rbar).divide(acgCountBig, 2, BigDecimal.ROUND_HALF_UP);
				for (int i = 1; i <= avgCount; i++) {
					resultList.get(size - i).put("x2bar", x2barbig);
					resultList.get(size - i).put("rbar", rbarbig);
				}
			}
		}
		qualityControl(resultList, rowsJSON, n + 1, dataNum, avgNum, datacol, indexcol);

		// Double xbarValue =

	}

	private Map<String, String> getRuleMap(String rid) {
		return this.formRulePropertyService.getRuleMap(rid);
	}

	@javax.annotation.Resource
	public void setFormRulePropertyService(FormRulePropertyService formRulePropertyService) {
		this.formRulePropertyService = formRulePropertyService;
	}

	@javax.annotation.Resource
	public void setFormRuleService(FormRuleService formRuleService) {
		this.formRuleService = formRuleService;
	}

	@javax.annotation.Resource
	public void setMutilDatabaseBean(MutilDatabaseBean mutilDatabaseBean) {
		this.mutilDatabaseBean = mutilDatabaseBean;
	}

}
