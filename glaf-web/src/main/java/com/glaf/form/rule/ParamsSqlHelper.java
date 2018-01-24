package com.glaf.form.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.util.DateUtils;
import com.glaf.expression.core.util.ExpressionConvertUtil;
import com.glaf.form.website.springmvc.FormPageController;

public class ParamsSqlHelper {
	protected static final Log logger = LogFactory.getLog(ParamsSqlHelper.class);
	
	private static String replaceParams(String params, String sql) {
		JSONObject paramsObj = JSON.parseObject(params);
		Set<String> keys = paramsObj.keySet();
		String value = "" ;
		for (String key : keys) {
			if (paramsObj.getString(key) != null) {
				value = paramsObj.getString(key);
				//sql = replaceDateParams(sql,key,value) ;
				sql = sql.replace(key + "." + key, value);
			}
		}
		return sql;
	}
	/**
	 * 日期处理
	 * @param params
	 * @param sql
	 * @return
	 */
	private static String replaceDate(String params, String sql){
		JSONObject paramsObj = JSON.parseObject(params);
		Set<String> keys = paramsObj.keySet();
		String value = "" ;
		for (String key : keys) {
			if (paramsObj.getString(key) != null) {
				value = paramsObj.getString(key);
				sql = replaceDateParams(sql,key,value) ;
			}
		}
		return sql;
	}
	/**
	 * 替换日期格式
	 * @param sql
	 * @return
	 */
	private static String replaceDateParams(String sql,String key,String value) {
		//(?!.*(#)).*  -- > "#=(.*)("+key+"\\."+key+")(.*)#"; 
		//(?!((\w)*java(\w)*)
		String regex = "~F\\{"+key+"\\."+key+"\\."+key+"\\}"; 
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(sql);
		StringBuffer sb = new StringBuffer(); //替换后的字符串
		while(matcher.find()) {   
			matcher.appendReplacement(sb,"'"+DateUtils.getDateTime(DateUtils.toDate(value))+"'"); 
		}  
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 查找pidkey的值
	 * 
	 * @param pidkey
	 * @param params
	 * @param inParameters
	 * @return
	 */
	public static String getTreeListPidkeyValue(String pidkey, String params, String inParameters) {
		if (StringUtils.isNotEmpty(params) && StringUtils.isNotEmpty(inParameters)) {
			JSONArray inParamsArray = JSON.parseArray(inParameters);
			JSONObject inParamObj = inParamsArray.getJSONObject(0);
			JSONArray collectionArray = inParamObj.getJSONArray("collection");
			if (!collectionArray.isEmpty()) {
				for (Object object : collectionArray) {
					JSONObject jsonObject = (JSONObject) object;
					String f = jsonObject.getString("fieldData");
					String p = jsonObject.getString("paramData");
					JSONObject fieldData = JSON.parseObject(f);
					JSONObject paramData = JSON.parseObject(p);
					String field = fieldData.getString("expActVal");
					String param = paramData.getString("expActVal");
					String key = ExpressionConvertUtil.expressionConvert(field, ExpressionConvertUtil.DATABASE_TYPE);
					if (pidkey.equalsIgnoreCase(key)) {
						String value = replaceParams(params, ExpressionConvertUtil.expressionConvert(param, ExpressionConvertUtil.DATABASE_TYPE));
						return value;
					}
				}
			}
		}
		return "";
	}

	/**
	 * 获取绑定的value
	 * 
	 * @param params
	 * @param inParameters
	 * @return
	 */
	public static Map<String, String> getBindParamMap(String params, String inParameters) {
		Map<String, String> map = new HashMap<>();
		if (StringUtils.isNotEmpty(params) && StringUtils.isNotEmpty(inParameters)) {
			JSONArray inParamsArray = JSON.parseArray(inParameters);
			JSONObject inParamObj = inParamsArray.getJSONObject(0);
			JSONArray collectionArray = inParamObj.getJSONArray("collection");
			if (!collectionArray.isEmpty()) {
				for (Object object : collectionArray) {
					JSONObject jsonObject = (JSONObject) object;
					if (!"Y".equalsIgnoreCase(jsonObject.getString("dynamic"))) {
						continue;
					}
					String f = jsonObject.getString("fieldData");
					String p = jsonObject.getString("paramData");
					JSONObject fieldData = JSON.parseObject(f);
					JSONObject paramData = JSON.parseObject(p);
					String field = fieldData.getString("expActVal");
					String param = paramData.getString("expActVal");
					String key = ExpressionConvertUtil.expressionConvert(field, ExpressionConvertUtil.DATABASE_TYPE);
					String value = replaceParams(params, ExpressionConvertUtil.expressionConvert(param, ExpressionConvertUtil.DATABASE_TYPE));
					map.put(key, value);
				}
			}
		}
		return map;
	}

	/**
	 * 拼装SQL
	 * 
	 * @param params
	 *            实际传参
	 * @param inParameters
	 *            表达定义参数
	 * @return
	 */
	public static String getParamSql(String params, String inParameters,String datasetId) {
		String sql = "";
		if (StringUtils.isNotEmpty(params) && StringUtils.isNotEmpty(inParameters)) {
			if (JSON.parseObject(params).isEmpty()) {
				return "";
			}
			JSONArray inParamsArray = JSON.parseArray(inParameters);
			for (Object object : inParamsArray) {
				JSONObject inParamObj = (JSONObject) object;
				JSONArray collectionArray = inParamObj.getJSONArray("collection");// 叶子节点数据集合
				
				if(collectionArray == null || collectionArray.isEmpty())
					continue;
				
				if(inParamObj.get("tabId")!=null && !datasetId.equals(inParamObj.get("tabId")) ){ // 如果数据集
					continue ;
				}
				
				JSONArray collectionTreeArray = inParamObj.getJSONArray("collectionTree"); // 树形关系集合
				
				sql = getEffectiveSql(collectionArray, collectionTreeArray, params);
				sql = ExpressionConvertUtil.expressionConvert(sql,ExpressionConvertUtil.DATABASE_TYPE);
				sql = replaceParams(params, sql) ;
			}
		}
		logger.info("拼装sql为: "+sql);
		return sql;
	}
	
	/**
	 * 获取有效的参数表达式sql
	 * @param collectionArray
	 * @param collectionTreeArray
	 * @param params
	 * @return
	 */
	private static String getEffectiveSql(JSONArray collectionArray, JSONArray collectionTreeArray,String params) {
		Map<String, JSONObject> collectionMap = getCollectionMapForOrdinal(collectionArray); // 根据Ordinal返回map
		JSONArray array = getCollectionTreeNodeByParentId(collectionMap, collectionTreeArray, null);
		JSONObject obj = null;
		JSONArray childrens = null;
		String sql = null ;
		for (Object object : array) {
			obj = (JSONObject) object;
			String connector = obj.getString("connector"); // 链接符号
			childrens = obj.getJSONArray("childrens"); // 链接符号
			if (!childrens.isEmpty()) {
				sql = buildsql(childrens, params, connector);
			}

		}
		return sql;
	}
	
	/**
	 * 构建sql
	 * @param childrens
	 * @param params
	 * @param connector
	 * @return
	 */
	private static String buildsql(JSONArray childrens,String params,String connector){
		StringBuffer sql = new StringBuffer();
		if(!childrens.isEmpty()){
			JSONObject obj = null ;
			JSONArray crs = null ;
			JSONObject val = null ;
			List<String> effectiveList = new ArrayList<String>();
			StringBuffer nodeSQL = null;
			for (Object object : childrens) {
				obj = (JSONObject) object ;
				boolean isParent = obj.getBoolean("isParent");
				if(isParent){//首先判断此对象是否为跟节点
					crs = obj.getJSONArray("childrens");//获取下级节点
					String childsql = buildsql(crs,params,obj.getString("connector")) ;
					if(childsql!=null && !"".equals(childsql)){
						effectiveList.add("("+childsql+")"); //循环构建sql build
					}
				}else{ //还是为叶子节点
					val = obj.getJSONObject("value");//叶子节点参数
					////System.out.println("叶子节点"+val);
					JSONObject paramData = JSON.parseObject(val.getString("paramData"));
					if(isEffectiveParams(params, paramData)){ //是否为有效节点
						nodeSQL = new StringBuffer();
						String operator = val.getString("operator");// 操作符号 = like
						JSONObject fieldData = JSON.parseObject(val.getString("fieldData"));
						
						String field = fieldData.getString("expActVal");
						String param = paramData.getString("expActVal");
						
						String likeParam = "" ;
						boolean isDataToStr = false ; //如果使用like函数  则日期用字符串来使用
						switch (operator) {
							case "like":
							case "not like":
								likeParam = "%" + param +"%";
								isDataToStr = true ;
								break;
							case "leftLike":
								likeParam = param +"%";
								operator = "like" ;
								isDataToStr = true ;
								break;
							case "rightLike":
								likeParam = "%" + param;
								operator = "like" ;
								isDataToStr = true ;
								break;
							default:
								likeParam = param ;
								break;
						}
						
						nodeSQL.append(field).append(" " + operator + " ");
						
						//根据类型转换字段
						String type = getFieldType(fieldData);
						if("date".equalsIgnoreCase(type) && !isDataToStr){
							param = replaceDate(params, param);
							nodeSQL.append(param);
						}/*else if("int".equalsIgnoreCase(type) || "double".equalsIgnoreCase(type)){
							nodeSQL.append(param);
						}*/else{
							nodeSQL.append("'"+likeParam+"'");
						}
						
						effectiveList.add(nodeSQL.toString());
					}
				}
			}
			int size = effectiveList.size() ;
			String connect = "" ;
			if ("Any".equals(connector)) {
				connect = " or " ;
			} else if ("All".equals(connector)) {
				connect = " and " ;
			}
			for (String str : effectiveList) {
				sql.append(str);
				if( size>1 && effectiveList.indexOf(str)!= (size-1) ){
					sql.append(connect);
				}
			}
		}
		return sql.toString() ;
	}
	
	/**
	 * 获取字段类型， 如果里面的字段包含多个则  默认为字符串类型
	 * @param fieldData
	 * @return
	 */
	private static String getFieldType(JSONObject fieldData) {
		JSONArray varVals = fieldData.getJSONArray("varVal");
		String expVal = fieldData.getString("expVal");
		JSONObject varVal = null ;
		String key = null ;
		JSONObject value = null ;
		String dType = null ;
		List<String> existKey = new ArrayList<>();
		for (Object object : varVals) {
			varVal = (JSONObject) object ;
			key = varVal.getString("key");
			if(expVal.indexOf(key)!=-1){
				value = varVal.getJSONObject("value");
				dType = value.getString("dType");
				if(!existKey.contains(dType)){
					existKey.add(dType);
				}
			}
		}
		return existKey.size()==1?dType:"String";
	}

	/**
	 * 检查是否为有效的节点
	 * 
	 * @param params
	 * @param paramData
	 * @return
	 */
	private static boolean isEffectiveParams(String params, JSONObject paramData) {
		JSONObject paramsObj = JSON.parseObject(params);
		Set<String> keys = paramsObj.keySet();
		for (String key : keys) {
			if (paramData.getString("expActVal").indexOf(key) != -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 返回json 树结构
	 * 
	 * @param collectionTreeArray
	 * @param parentId
	 * @return
	 */
	private static JSONArray getCollectionTreeNodeByParentId(Map<String, JSONObject> collectionMap, JSONArray collectionTreeArray, String parentId) {
		JSONArray retArray = new JSONArray();
		JSONObject obj = null;
		for (Object object : collectionTreeArray) {
			obj = (JSONObject) object;
			String pid = obj.getString("pId");
			if (parentId == null ? pid == null : parentId.equals(pid)) {
				String id = obj.getString("id");
				obj.put("childrens", getCollectionTreeNodeByParentId(collectionMap, collectionTreeArray, id));
				obj.put("value", collectionMap.get(id));
				retArray.add(obj);
			}
		}
		return retArray;
	}

	private static Map<String, JSONObject> getCollectionMapForOrdinal(JSONArray collectionArray) {
		Map<String, JSONObject> map = new HashMap<>();
		for (Object object : collectionArray) {
			JSONObject collectionObj = (JSONObject) object;
			String ordinal = collectionObj.getString("ordinal");
			map.put(ordinal, collectionObj);
		}
		return map;
	}

	public static void main(String[] args) {
	/*	Map<String, JSONObject> map = new HashMap<String, JSONObject>();
		map.put("2",
				JSON.parseObject("{\"parentId\": \"1\", \"ordinal\": \"2\",\"fieldVal\": \"~F{default.项目基本情况一览表.id}\", \"connector\": \"All\", \"operator\": \"like\", \"paramVal\": \"~F{id 参数}\", \"dynamic\": \"N\"}"));
		map.put("3",
				JSON.parseObject("{\"parentId\": \"1\", \"ordinal\": \"2\",\"fieldVal\": \"~F{default.项目基本情况一览表.id}\", \"connector\": \"All\", \"operator\": \"like\", \"paramVal\": \"~F{id 参数}\", \"dynamic\": \"N\"}"));
		map.put("parent-4",
				JSON.parseObject("{\"parentId\": \"1\", \"ordinal\": \"2\",\"fieldVal\": \"~F{default.项目基本情况一览表.id}\", \"connector\": \"All\", \"operator\": \"like\", \"paramVal\": \"~F{id 参数}\", \"dynamic\": \"N\"}"));
		map.put("parent-9",
				JSON.parseObject("{\"parentId\": \"1\", \"ordinal\": \"2\",\"fieldVal\": \"~F{default.项目基本情况一览表.id}\", \"connector\": \"All\", \"operator\": \"like\", \"paramVal\": \"~F{id 参数}\", \"dynamic\": \"N\"}"));
		String dd = "[{\"name\":\"1\",\"id\":\"1\",\"pId\":null,\"isParent\":true,\"connector\":\"All\"},{\"name\":\"1.1\",\"id\":\"2\",\"pId\":\"1\",\"isParent\":false},{\"name\":\"1.2\",\"id\":\"3\",\"pId\":\"1\",\"isParent\":false},{\"name\":\"1.3\",\"id\":\"4\",\"pId\":\"1\",\"isParent\":true,\"connector\":\"All\"},{\"name\":\"1.3.1\",\"id\":\"parent-4\",\"pId\":\"4\",\"isParent\":false},{\"name\":\"1.3.2\",\"id\":\"9\",\"pId\":\"4\",\"isParent\":true,\"connector\":\"All\"},{\"name\":\"1.3.2.1\",\"id\":\"parent-9\",\"pId\":\"9\",\"isParent\":false}]";
		JSONArray collectionTreeArray = JSON.parseArray(dd);
		JSONArray jo = getCollectionTreeNodeByParentId(map, collectionTreeArray, null);
		//System.out.println(jo);*/
		////System.out.println(replaceDateParams("#=2016-01-15 17:15:48.437#","==="));
		String k = "#.*~F\\{(\\w*\\.?){1,3}\\}.*#";
		String d = "#(2016)~F{x.x.x} 17:15:48.437#" ;
		String s = d.replaceAll(k, "=="); 
		
	}

}
