package com.glaf.form.web.springmvc;

import java.util.ArrayList;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
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
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.security.DESUtils;
import com.glaf.core.security.SecurityUtils;
import com.glaf.core.util.Hex;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.UUID32;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.form.core.util.MutilDatabaseBean;
import com.glaf.form.rule.DataSourceRequest;
import com.glaf.form.rule.Global;
import com.glaf.form.rule.ParamsSqlHelper;
import com.glaf.form.rule.util.InjectUtils;
import com.glaf.video.domain.VideoDeveloper;
import com.glaf.video.service.VideoDeveloperService;

/**
 * 通用树结构数据调用
 * 
 * @author Administrator
 *
 */
@Controller("/form/videoData")
@RequestMapping("/form/videoData")
public class FormVideoDataController {
	protected static final Log logger = LogFactory.getLog(FormVideoDataController.class);

	protected MutilDatabaseBean mutilDatabaseBean;

	protected FormRulePropertyService formRulePropertyService;
	
	@Autowired
	protected VideoDeveloperService videoDeveloperService;
	
	@Autowired
	protected DataSetService dataSetService;

	private Map<String, String> getRuleMap(String rid) {
		return this.formRulePropertyService.getRuleMap(rid);
	}

	private String getRuleMapValueByKey(Map<String, String> ruleMap, String key) {
		String dataSourceSet = ruleMap.get("dataSourceSet");
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONObject datasourceSetJSONObject = null;
		if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
			datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
		}
		JSONObject metadata = dataSetService.getDataSetMetadata(datasourceSetJSONObject.getString("datasetId")), 
				mapping = null; 
		if (metadata != null) { 
			mapping = metadata.getJSONObject("mapping"); 
		}
		String columnsStr = ruleMap.get("columns");
		JSONArray columnsArray = JSON.parseArray(columnsStr);
		JSONObject jo = null;
		String keyValue = null;
		for (Object object : columnsArray) {
			jo = (JSONObject) object;
			if (key.equalsIgnoreCase(jo.getString("columnType"))) {
				keyValue = Global.getOriginalColumnName(mapping, jo.getString("ColumnName")).split("_0_")[1];
			}
		}
		return keyValue;
	}

	/**
	 * 获取萤石云视频监控
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/videoData")
	public @ResponseBody byte[] getVideoData(HttpServletRequest request,
			@RequestBody DataSourceRequest dataSourceRequest) throws Exception {
		// 获取参数
		String rid = dataSourceRequest.getRid();// 规则id

		// 获取规则MAP
		Map<String, String> ruleMap = getRuleMap(rid);
		if (ruleMap == null) {
			return null;
		}

		String dataSourceSet = ruleMap.get("dataSourceSetByVideos");
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
		String columnName = "" ;
		String safeKeyColumn = "" ;  //摄像头安全码 字段
		String cameraIdColumn = "" ; //摄像头ID 字段
		String developerIdColumn = "" ; //开发者ID 字段
		for (Object object : selectColumns) {
			selectColumn = (JSONObject) object ;
			ntype = selectColumn.getString("ntype");
			columnName = selectColumn.getString("columnName");
			if("developerId".equalsIgnoreCase(ntype)){
				developerIdColumn = columnName ;
			}else if("safeKey".equalsIgnoreCase(ntype)){
				safeKeyColumn = columnName ;
			}else if("cameraId".equalsIgnoreCase(ntype)){
				cameraIdColumn = columnName ;
			}
		}
		
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
		psql = ParamsSqlHelper.getParamSql(paramsObj.toJSONString(), inParameters, onlyId);

		// 构建联动语句 end
		// //System.out.println("联动语句"+psql);

		// 返回
		JSONObject result = new JSONObject();

		// 构建sql start
		DataSetBuilder builder = new DataSetBuilder();
		Map<String, Object> parameter = new HashMap<String, Object>();
		if(!paramsObj.isEmpty()){
			parameter.putAll(paramsObj);
		}
		parameter.put("HttpServletRequest", request);

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
			
			String safeKey = "" ;  //摄像头安全码 字段
			String cameraId = "" ; //摄像头ID 字段
			String developerId = "" ; //开发者ID 字段
			
			Map<String, Object> map = dataMaps.get(0) ;
			Set<String> keys = map.keySet();
			Iterator<String> iterator = keys.iterator();
			
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				if (safeKeyColumn.equalsIgnoreCase(key)) {
					safeKey = (String) map.get(key);
				}else if(cameraIdColumn.equalsIgnoreCase(key)){
					cameraId = (String) map.get(key);
				}else if(developerIdColumn.equalsIgnoreCase(key)){
					developerId = (String) map.get(key);
				}
			}
			
			if(isNullOrEmpty(safeKey) || isNullOrEmpty(cameraId) || isNullOrEmpty(developerId)){ //如果有null值
				return "".getBytes("UTF-8");
			}
			
			VideoDeveloper videoDeveloper = videoDeveloperService.getVideoDeveloper(developerId);

			JSONObject json = new JSONObject();
			json.put("ApiUrl", videoDeveloper.getApiUrl() + "/method");
			json.put("PhoneNumber", videoDeveloper.getPhoneNumber());
			json.put("UserId", videoDeveloper.getUserId());
			json.put("AppKey", videoDeveloper.getAppKey());
			json.put("SecretKey", videoDeveloper.getSecretKey());
			json.put("CameraId", cameraId);
			json.put("SafeKey", safeKey);
			byte[] input = json.toJSONString().getBytes("UTF-8");
			byte[] secretKey = SecurityUtils.getKeyBytes(UUID32.getUUID());
			byte[] secretIv = SecurityUtils.getKeyIvBytes(UUID32.getUUID());
			byte[] data = DESUtils.ecrypt3DES(input, secretKey, secretIv);
			
			StringBuilder buff = new StringBuilder();
			buff.append(Hex.bytesToHex(secretKey)).append(Hex.bytesToHex(data)).append(Hex.bytesToHex(secretIv));
			logger.debug("secretKey:" + Hex.bytesToHex(secretKey));
			logger.debug("secretIv:" + Hex.bytesToHex(secretIv));

			return buff.toString().getBytes("UTF-8");
		} else {
			return "".getBytes("UTF-8");
		}
		
		
	}
	@RequestMapping("/plateformVideos")
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
		String dataSourceSet = ruleMap.get("dataSourceSetByVideos");
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
				if (!isNullOrEmpty(params)) {
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
					
					jsonObj.put("data", rowsJSON);
				}
			}
			JSONObject jsonSource = new JSONObject();
			jsonSource.put("source", ruleMap);
			retArray.add(jsonSource);
			return retArray.toJSONString().getBytes("UTF-8");
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("id", "");
			result.put("data", rowsJSON);
			retArray.add(result);
			return retArray.toJSONString().getBytes("UTF-8");
		}

	}
	public static void main(String[] args) throws Exception {
		JSONObject json = new JSONObject();
		json.put("ApiUrl", "https://open.ys7.com/api//method");
		json.put("PhoneNumber", "13950420819");
		json.put("UserId", "chenzl2015");
		json.put("AppKey", "e9cb69ab7f1a4b77ae655b32f7ab936c");
		json.put("SecretKey", "3a0eedb4bb21ab1cf71f18e642c1941a");
		String carmeraId = "62855221521046f6adaf46ce0cd27edb";
		carmeraId = "c7057ccc862246dc941a331074621e39";
		json.put("CameraId", carmeraId);
		json.put("SafeKey", "ABCDEF");
		byte[] input = json.toJSONString().getBytes("UTF-8");
		byte[] secretKey = SecurityUtils.getKeyBytes(UUID32.getUUID());
		byte[] secretIv = SecurityUtils.getKeyIvBytes(UUID32.getUUID());
		byte[] data = DESUtils.ecrypt3DES(input, secretKey, secretIv);
		
		StringBuilder buff = new StringBuilder();
		buff.append(Hex.bytesToHex(secretKey)).append(Hex.bytesToHex(data)).append(Hex.bytesToHex(secretIv));
		System.out.println(Hex.bytesToHex(secretKey).length());
		System.out.println(Hex.bytesToHex(secretIv).length());
		System.out.println(buff.toString()); 
		
		String kkks = "abcdefg" ;
		System.out.println(kkks.substring(0, 2));
		System.out.println(kkks.substring(2, kkks.length()-2));
		System.out.println(kkks.substring(kkks.length()-2, kkks.length()));
		
		String str1 = "1cecfb582913d75f07715bf8b388a4dd1cecfb582913d75f7b0ea608677ec3e4f157db74c463c0890ccfaad516bc98a7d499e98ce1e13eff04d095162cebbfa651b6a78919953903ccdf8476c4e887fed14577fcae0851259be9c593ad9f5d4af92f878c9e96de3f7a88016da022d76f86024d983795d56df47af2dcad452a223d2a0b368dfac1c62fface8df320353bb36c2de402f40404769680dc9b754ed35f59481ee76df0777af9db78576d91d54da90c692bec405de4a8e511d176ceb4f31b5b3c86a5cf39e0e1fa1ed050e43f40bdbd690e84a422d77eda48d195b35d7a022ccf4b8c7d43423157cae2845973889fa7776b946d10ba240f167a441ad7b991649d0e09bf0a5314c6e662a18e29d499796ff7a66eb4f996090c414f91aa";
		//str1 = "36f438cff2f57711ef45cd412626ef4c36f438cff2f577115f4459f57e4f767521ec298402620833810670d2a0ae1e5452ee61091f37cb59e5e2be946bd2c16cb80c2652d2898c408948c48eabf99f60cda743fc96c43769e48312478287b50f366131dddaa52a2006a37e6bdae194fc01696fa0527d38c60de58a53a53fb2c8ed6cc433e80a393bfd112484e4b3cc048c0c8f14bda1257366f91d8b3ca4b659d30b08da5d6e8a22d490350774c8fccb290e6c62b9dd7e7a4d39521edb7b7ff208d2212b5bd874f597cda09124678b3e676b940653ba8c654ce3dc1225b5fa3bb7597a49da8e73f98369b56f0a7d94353c80f117684b5456c31c21b5cc44a863e1bbbfd08b72605c942110ada12b0d2031033772f8f7366032607eac2fe7577e";
		//str1 = "6d66995ba264031a318f0c81085e6a4d6d66995ba264031aa62ae3aa73722bb3df987138ae7ca009d982b7134460fcae19e83e4d1150aae0ad18964888a19129affdff04c9d3267b3228afb12ed37a7f05ce7eb1fcddb4e467fb0025dc8befe0770e303c9d45a7fe69a1250321678697ab8bbdfe9bee77abf72a9364ae1730d4bd77ee4416854d0e5726f6bac86ff7bbb3b04ef9fdb5e5f50aabb9d34eb5dbf7a6af77d8cd4759ae9181275254ba4d3527a7c1f3f9b2182ffaad5ee2f597e62bc0a54eb4fc299cfc54f31582afff8ab841259731cbe5aad67d95735268620321768c81acc18fca4abab70af3cac644b208253b7a723b1bd0cfee87428d500563bd97b80b5e346e101f922860e03b0da8ce074fe2a227876c45e819430cddd858";
		System.out.println(str1.length());
		String ss = str1.substring(0, 48);
		String si = str1.substring(str1.length()-16, str1.length());
		String dd = str1.substring(48, str1.length()-16);
		System.out.println(ss.length());
		System.out.println(si.length());
		System.out.println(dd.length());
		byte[] byted = DESUtils.decrypt3DES(Hex.decodeHex(dd),Hex.decodeHex(ss), Hex.decodeHex(si));
		
		System.out.println(new String(byted));
	}

		
	private static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	
	@RequestMapping("/videoMobileData")
	public @ResponseBody byte[] getVideoMobileData(HttpServletRequest request) throws Exception {
		/**
		 *通道
		 */
		String cameraNo = RequestUtils.getString(request, "cameraNo");
		/**
		 * 序列号
		 */
		String deviceSerial = RequestUtils.getString(request, "deviceSerial");
		/**
		 * 开发者ID
		 */
		String developerId = RequestUtils.getString(request, "developerId");
		
		if(isNullOrEmpty(cameraNo) || isNullOrEmpty(deviceSerial) || isNullOrEmpty(developerId)){ //如果有null值
			return "".getBytes("UTF-8");
		}
		
		VideoDeveloper videoDeveloper = videoDeveloperService.getVideoDeveloper(developerId);
		
		JSONObject json = new JSONObject();
		json.put("cameraNo", cameraNo);
		json.put("deviceSerial", deviceSerial);
		json.put("appKey", videoDeveloper.getAppKey());
		json.put("AccessToken", videoDeveloper.getAccessToken());
		
		return json.toJSONString().getBytes("UTF-8");
		
		/*byte[] input = json.toJSONString().getBytes("UTF-8");
		byte[] secretKey = SecurityUtils.getKeyBytes(UUID32.getUUID());
		byte[] secretIv = SecurityUtils.getKeyIvBytes(UUID32.getUUID());
		byte[] data = DESUtils.ecrypt3DES(input, secretKey, secretIv);
		StringBuilder buff = new StringBuilder();
		buff.append(Hex.bytesToHex(secretKey)).append(Hex.bytesToHex(data)).append(Hex.bytesToHex(secretIv));
		
		logger.debug("secretKey:" + Hex.bytesToHex(secretKey));
		logger.debug("secretIv:" + Hex.bytesToHex(secretIv));
		
		return buff.toString().getBytes("UTF-8");*/
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
