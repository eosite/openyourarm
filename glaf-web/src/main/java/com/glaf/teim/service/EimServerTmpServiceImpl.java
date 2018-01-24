package com.glaf.teim.service;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.uis.domain.UisAppUserIdMapping;
import com.glaf.base.modules.uis.mapper.UisAppUserIdMappingMapper;
import com.glaf.base.modules.uis.query.UisAppUserIdMappingQuery;
import com.glaf.base.utils.HttpClientUtil;
import com.glaf.base.utils.HttpSoapClientUtil;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.UUID32;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.teim.domain.EimBaseInfo;
import com.glaf.teim.domain.EimServerTmp;
import com.glaf.teim.domain.EimServerTmpTree;
import com.glaf.teim.mapper.EimBaseInfoMapper;
import com.glaf.teim.mapper.EimServerTmpMapper;
import com.glaf.teim.query.EimServerTmpQuery;
import com.glaf.teim.web.rest.EimServerTmpResource;

@Service("com.glaf.teim.service.eimServerTmpService")
@Transactional(readOnly = true)
public class EimServerTmpServiceImpl implements EimServerTmpService {
	protected static final Log logger = LogFactory.getLog(EimServerTmpServiceImpl.class);
	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected EimServerTmpMapper eimServerTmpMapper;

	protected EimBaseInfoMapper eimBaseInfoMapper;

	protected UisAppUserIdMappingMapper uisAppUserIdMappingMapper;

	public EimServerTmpServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			eimServerTmpMapper.deleteEimServerTmpById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> tmpIds) {
		if (tmpIds != null && !tmpIds.isEmpty()) {
			for (String id : tmpIds) {
				eimServerTmpMapper.deleteEimServerTmpById(id);
			}
		}
	}

	public int count(EimServerTmpQuery query) {
		return eimServerTmpMapper.getEimServerTmpCount(query);
	}

	public List<EimServerTmp> list(EimServerTmpQuery query) {
		List<EimServerTmp> list = eimServerTmpMapper.getEimServerTmps(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getEimServerTmpCountByQueryCriteria(EimServerTmpQuery query) {
		return eimServerTmpMapper.getEimServerTmpCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<EimServerTmp> getEimServerTmpsByQueryCriteria(int start, int pageSize, EimServerTmpQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<EimServerTmp> rows = sqlSessionTemplate.selectList("getEimServerTmps", query, rowBounds);
		return rows;
	}

	public EimServerTmp getEimServerTmp(String id) {
		if (id == null) {
			return null;
		}
		EimServerTmp eimServerTmp = eimServerTmpMapper.getEimServerTmpById(id);
		return eimServerTmp;
	}

	@Transactional
	public void save(EimServerTmp eimServerTmp) {
		if (StringUtils.isEmpty(eimServerTmp.getTmpId())) {
			eimServerTmp.setTmpId(UUID32.getUUID());
			// eimServerTmp.setCreateDate(new Date());
			// eimServerTmp.setDeleteFlag(0);
			eimServerTmpMapper.insertEimServerTmp(eimServerTmp);
		} else {
			eimServerTmpMapper.updateEimServerTmp(eimServerTmp);
		}
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.teim.mapper.EimServerTmpMapper")
	public void setEimServerTmpMapper(EimServerTmpMapper eimServerTmpMapper) {
		this.eimServerTmpMapper = eimServerTmpMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource(name = "com.glaf.teim.mapper.EimBaseInfoMapper")
	public void setEimBaseInfoMapper(EimBaseInfoMapper eimBaseInfoMapper) {
		this.eimBaseInfoMapper = eimBaseInfoMapper;
	}

	@Override
	public void moveToCategory(String tmpId, String categoryId) {
		// TODO Auto-generated method stub
		eimServerTmpMapper.moveToCategory(tmpId, categoryId);
	}

	@Override
	public JSONObject callService(String tmpId, String baseId, JSONObject paramsJson) {
		// TODO Auto-generated method stub
		JSONObject returnJSON = new JSONObject();
		// 获取腾讯通企业通应用实例信息
		EimBaseInfo baseInfo = eimBaseInfoMapper.getEimBaseInfoById(baseId);
		// 获取模板对象
		EimServerTmp tmp = getEimServerTmp(tmpId);
		// 获取服务协议
		String proptocal = tmp.getReqType().toLowerCase();
		// 获取头模板信息
		String reqHeaderTmp = tmp.getReqHeader();
		Map<String, String> requestHead = new HashMap<String, String>();
		long currentTimestamp = new Date().getTime() / 1000;
		String rioseq = RandomStringUtils.randomNumeric(8);
		String appsecret = baseInfo.getSecret();
		String signature = signature(appsecret, rioseq, "" + currentTimestamp);
		paramsJson.put("x-rio-seq", rioseq);
		paramsJson.put("timestamp", "" + currentTimestamp);
		paramsJson.put("signature", signature);
		if (StringUtils.isNotEmpty(reqHeaderTmp)) {
			requestHead = createMapByParamsJson(paramsJson, reqHeaderTmp);
		}
		Map<String, String> queryParams = new HashMap<String, String>();
		// 获取URL请求参数模板
		String queryParamTmp = tmp.getReqUrlParam();
		if (StringUtils.isNotEmpty(queryParamTmp)) {
			queryParams = createMapByParamsJson(paramsJson, queryParamTmp);
		}
		JSONObject requestBody = new JSONObject();
		// 获取请求体模板
		String reqBodyTmp = tmp.getReqBody();
		if (StringUtils.isNotEmpty(reqBodyTmp)) {
			requestBody = createJSONByParamsJson(paramsJson, reqBodyTmp);
		}
		String url = baseInfo.getIp() + tmp.getPath_();
		if (proptocal.startsWith("soap")) {
			String serviceName = requestHead.get("serviceName");
			String namespace = requestHead.get("namespace");
			String methodName = requestHead.get("methodName");
			Map<String, String> paramJson = new HashMap<String, String>();
			paramJson.putAll(queryParams);
			for (Entry<String, Object> entry : requestBody.entrySet()) {
				paramJson.put(entry.getKey(), entry.getValue().toString());
			}
			return HttpSoapClientUtil.postSoap(proptocal, url, namespace, methodName, paramJson);

		}
		// 获取自定义请求体模板
		String requestBodyCustom = tmp.getReqBodyCustom();
		if (StringUtils.isNotEmpty(requestBodyCustom)) {
			requestBody = createCustomRequestBody(requestBody, requestBodyCustom);
		}
		String reqContentType = tmp.getReqContentType();
		if (StringUtils.isEmpty(reqContentType)) {
			requestHead.put("content-Type", "text/json;charset=utf-8");
		} else {
			requestHead.put("content-Type", reqContentType);
		}
		String resContentType = tmp.getResContentType();
		if (StringUtils.isEmpty(resContentType)) {
			resContentType = "application/json";
		}

		try {
			// 获取请求方式
			String reqType = tmp.getReqType();
			logger.error("-----------------------------------调用外部服务-------------------------------");
			logger.error("url:" + url);
			logger.error("queryParams:" + queryParams);
			logger.error("requestBody:" + requestBody);
			logger.debug("-----------------------------------调用外部服务-------------------------------");
			logger.debug("url:" + url);
			logger.debug("queryParams:" + queryParams);
			logger.debug("requestBody:" + requestBody);
			logger.info("-----------------------------------调用外部服务-------------------------------");
			logger.info("url:" + url);
			logger.info("queryParams:" + queryParams);
			logger.info("requestBody:" + requestBody);
			logger.warn("-----------------------------------调用外部服务-------------------------------");
			logger.warn("url:" + url);
			logger.warn("queryParams:" + queryParams);
			logger.warn("requestBody:" + requestBody);
			if (reqType.equals("POST")) {
				returnJSON = HttpClientUtil.postRequest(url, queryParams, requestHead, resContentType, requestBody,
						3000);
			} else if (reqType.equals("GET")) {
				returnJSON = HttpClientUtil.getRequest(url, queryParams, requestHead, resContentType, 3000);
			} else if (reqType.equals("DELETE")) {
				returnJSON = HttpClientUtil.deleteRequest(url, queryParams, requestHead, resContentType, 3000);
			} else if (reqType.equals("PUT")) {
				returnJSON = HttpClientUtil.putRequest(url, queryParams, requestHead, resContentType, requestBody,
						3000);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return returnJSON;
	}

	/**
	 * 根据参数值和模板生成实例
	 * 
	 * @param paramsJson
	 * @param tmpJsonStr
	 * @return
	 */
	public Map<String, String> createMapByParamsJson(JSONObject paramsJson, String tmpJsonStr) {
		Map<String, String> map = new HashMap<String, String>();
		JSONObject tmpJson = JSONObject.parseObject(tmpJsonStr);
		String key = null;
		JSONObject itemJSON = null;
		String val = null;
		for (Entry<String, Object> entry : tmpJson.entrySet()) {
			key = entry.getKey();
			itemJSON = entry.getValue() != null ? (JSONObject) entry.getValue() : null;
			if (StringUtils.isNotEmpty(key) && itemJSON != null) {
				// 获取值
				val = paramsJson.getString(key);
				if (StringUtils.isEmpty(val)) {
					val = itemJSON.getString("defaultval");
					if (StringUtils.isEmpty(val)) {
						continue;
					}
					if (val.startsWith("$GETCOOKIE")) {
						String cookieName = val.substring(val.indexOf("'") + 1, val.lastIndexOf("'"));
						val = paramsJson.getString(cookieName);
					} else if (val.startsWith("$SYSUSER")) {
						String appId = val.substring(val.indexOf("'") + 1, val.lastIndexOf("'"));
						// 获取当前用户
						String currentUser = paramsJson.getString("actorId");
						UisAppUserIdMappingQuery query = new UisAppUserIdMappingQuery();
						query.setAppId(appId);
						query.setUserId(currentUser);
						List<UisAppUserIdMapping> uisAppUserIdMapping = uisAppUserIdMappingMapper
								.getUisAppUserIdMappings(query);
						if (uisAppUserIdMapping != null && uisAppUserIdMapping.size() == 1)
							val = uisAppUserIdMapping.get(0).getAppUserId();
					} else if (val.startsWith("$DATASET")) {
						val = getParamValFromDataSet(val, new HashMap<String, Object>());
					}
				}
				if (StringUtils.isNotEmpty(val)) {
					map.put(key, val);
				}
			}
		}
		return map;
	}

	/**
	 * 自定义请求体
	 * 
	 * @param requestBody
	 * @param requestBodyCustom
	 * @return
	 */
	private JSONObject createCustomRequestBody(JSONObject requestBody, String requestBodyCustom) {
		// TODO Auto-generated method stub
		JSONObject customRequestBody = new JSONObject();
		String paramName = null;
		String paramStrVal = null;
		Object paramVal = null;
		for (Entry<String, Object> param : requestBody.entrySet()) {
			paramName = param.getKey();
			paramVal = param.getValue();
			if (paramVal instanceof String) {
				paramStrVal = (String) paramVal;
			} else if (paramVal instanceof Integer) {
				paramStrVal = ((Integer) paramVal).toString();
			} else if (paramVal instanceof Float) {
				paramStrVal = ((Float) paramVal).toString();
			} else if (paramVal instanceof Double) {
				paramStrVal = ((Double) paramVal).toString();
			} else if (paramVal instanceof Long) {
				paramStrVal = ((Long) paramVal).toString();
			}
			requestBodyCustom = requestBodyCustom.replaceAll("\\$" + paramName, paramStrVal);
		}
		customRequestBody = JSONObject.parseObject(requestBodyCustom);
		return customRequestBody;
	}

	/**
	 * 获取数据集的值
	 * 
	 * @param exp
	 * @param params
	 * @return
	 */
	public String getParamValFromDataSet(String exp, Map<String, Object> params) {
		String returnval = "";
		// 获取参数
		String pattern = "(?<=[\\(|,])[^,]*(?=[,|\\)])";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(exp);
		int i = 0;
		String datasetId = null;
		String columnId = null;
		while (m.find()) {
			if (i == 0)
				datasetId = m.group(0);
			else
				columnId = m.group(0);
			i++;
		}
		// 查询数据集
		JSONObject json = new DataSetBuilder().getJson(datasetId, 0, 1, params);
		// 获取数据
		JSONArray rows = json.getJSONArray("rows");
		if (rows != null && rows.size() == 1) {
			JSONObject valJson = rows.getJSONObject(0);
			String columnName = null;
			for (Entry<String, Object> entry : valJson.entrySet()) {
				columnName = entry.getKey();
				if (columnId == null || columnName.toLowerCase().equals(columnId.toLowerCase())) {
					returnval = (String) entry.getValue();
					break;
				}
			}
		}
		return returnval;
	}

	/**
	 * 根据参数值和模板生成实例
	 * 
	 * @param paramsJson
	 * @param tmpJsonStr
	 * @return
	 */
	public JSONObject createJSONByParamsJson(JSONObject paramsJson, String tmpJsonStr) {
		JSONObject json = new JSONObject();
		JSONObject tmpJson = JSONObject.parseObject(tmpJsonStr);
		String key = null;
		JSONObject itemJSON = null;
		String type = null;
		Object val = null;
		for (Entry<String, Object> entry : tmpJson.entrySet()) {
			key = entry.getKey();
			itemJSON = entry.getValue() != null ? (JSONObject) entry.getValue() : null;
			if (StringUtils.isNotEmpty(key) && itemJSON != null) {
				// 获取值
				type = itemJSON.getString("type");
				if (type.equals("string")) {
					val = paramsJson.getString(key);
					if (StringUtils.isEmpty((String) val)) {
						val = itemJSON.getString("defaultval");
						String value = itemJSON.getString("defaultval");
						if (value.startsWith("$GETCOOKIE")) {
							String cookieName = value.substring(value.indexOf("'") + 1, value.lastIndexOf("'"));
							val = paramsJson.getString(cookieName);
						} else if (value.startsWith("$SYSUSER")) {
							String appId = value.substring(value.indexOf("'") + 1, value.lastIndexOf("'"));
							// 获取当前用户
							String currentUser = paramsJson.getString("actorId");
							UisAppUserIdMappingQuery query = new UisAppUserIdMappingQuery();
							query.setAppId(appId);
							query.setUserId(currentUser);
							List<UisAppUserIdMapping> uisAppUserIdMapping = uisAppUserIdMappingMapper
									.getUisAppUserIdMappings(query);
							if (uisAppUserIdMapping != null && uisAppUserIdMapping.size() == 1)
								val = uisAppUserIdMapping.get(0).getAppUserId();
						} else if (value.startsWith("$DATASET")) {
							val = getParamValFromDataSet(value, new HashMap<String, Object>());
						}
					}
					if (StringUtils.isNotEmpty((String) val)) {
						json.put(key, (String) val);
					}
				} else if (type.equals("int")) {
					val = paramsJson.getInteger(key);
					if (val == null) {
						val = itemJSON.getInteger("defaultval");
					}
					if (val != null) {
						json.put(key, (Integer) val);
					}
				}

			}
		}
		return json;
	}

	public String signature(String appsecret, String x_rio_seq, String timestamp) {
		String sign_str = timestamp + appsecret + x_rio_seq + timestamp;

		return SHA(sign_str, "SHA-256");
	}

	/**
	 * 字符串 SHA 加密
	 * 
	 * @param strSourceText
	 * @return
	 */
	private String SHA(final String strText, final String strType) {
		// 返回值
		String strResult = null;

		// 是否是有效字符串
		if (strText != null && strText.length() > 0) {
			try {
				// SHA 加密开始
				// 创建加密对象 并傳入加密類型
				MessageDigest messageDigest = MessageDigest.getInstance(strType);
				// 传入要加密的字符串
				messageDigest.update(strText.getBytes());
				// 得到 byte 類型结果
				byte byteBuffer[] = messageDigest.digest();

				// 將 byte 轉換爲 string
				StringBuffer strHexString = new StringBuffer();
				// 遍歷 byte buffer
				for (int i = 0; i < byteBuffer.length; i++) {
					String hex = Integer.toHexString(0xff & byteBuffer[i]);
					if (hex.length() == 1) {
						strHexString.append('0');
					}
					strHexString.append(hex);
				}
				// 得到返回結果
				strResult = strHexString.toString();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}

		return strResult;
	}

	@Override
	public JSONObject callServiceNoToken(String tmpId, String baseId, JSONObject paramsJson) {
		// TODO Auto-generated method stub
		JSONObject returnJSON = new JSONObject();
		// 获取腾讯通企业通应用实例信息
		EimBaseInfo baseInfo = eimBaseInfoMapper.getEimBaseInfoById(baseId);
		// 获取模板对象
		EimServerTmp tmp = getEimServerTmp(tmpId);
		// 获取头模板信息
		String reqHeaderTmp = tmp.getReqHeader();
		Map<String, String> requestHead = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(reqHeaderTmp)) {
			requestHead = createMapByParamsJson(paramsJson, reqHeaderTmp);
		}
		String reqContentType = tmp.getReqContentType();
		if (StringUtils.isNotEmpty(reqContentType)) {
			requestHead.put("content-Type", "text/json;charset=utf-8");
		}
		String resContentType = tmp.getResContentType();
		if (StringUtils.isEmpty(resContentType)) {
			resContentType = "application/json";
		}
		String url = baseInfo.getIp() + tmp.getPath_();
		Map<String, String> queryParams = new HashMap<String, String>();
		// 获取URL请求参数模板
		String queryParamTmp = tmp.getReqUrlParam();
		if (StringUtils.isNotEmpty(queryParamTmp)) {
			queryParams = createMapByParamsJson(paramsJson, queryParamTmp);
		}
		// 动态URL替换
		if (url.indexOf("{") > -1) {
			url = createDymcUrl(url, queryParams);
		}
		JSONObject requestBody = new JSONObject();
		// 获取请求体模板
		String reqBodyTmp = tmp.getReqBody();
		if (StringUtils.isNotEmpty(reqBodyTmp)) {
			requestBody = createJSONByParamsJson(paramsJson, reqBodyTmp);
		}
		try {
			// 获取请求方式
			String reqType = tmp.getReqType();
			if (reqType.equals("POST")) {
				returnJSON = HttpClientUtil.postRequest(url, queryParams, requestHead, resContentType, requestBody,
						30000);
			} else if (reqType.equals("GET")) {
				returnJSON = HttpClientUtil.getRequest(url, queryParams, requestHead, resContentType, 30000);
			} else if (reqType.equals("DELETE")) {
				returnJSON = HttpClientUtil.deleteRequest(url, queryParams, requestHead, resContentType, 30000);
			} else if (reqType.equals("PUT")) {
				returnJSON = HttpClientUtil.putRequest(url, queryParams, requestHead, resContentType, requestBody,
						30000);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return returnJSON;
	}

	/**
	 * 创建动态URL
	 * 
	 * @param url
	 * @param queryParams
	 * @return
	 */
	private String createDymcUrl(String url, Map<String, String> queryParams) {
		String paramName = null;
		String paramVal = null;
		for (Entry<String, String> entry : queryParams.entrySet()) {
			paramName = entry.getKey();
			paramVal = entry.getValue();
			url = StringUtils.replace(url, "${" + paramName + "}", paramVal);
		}
		return url;
	}

	@Override
	public List<EimServerTmpTree> getEimServerTmpTreeData() {
		List<EimServerTmpTree> treeList = new ArrayList<EimServerTmpTree>();
		List<EimServerTmpTree> categoryTree = eimServerTmpMapper.getEimServerTmpCategoryTree();
		if (categoryTree != null) {
			treeList.addAll(categoryTree);
		}
		List<EimServerTmpTree> tmpTree = eimServerTmpMapper.getEimServerTmpTree();
		if (tmpTree != null) {
			treeList.addAll(tmpTree);
		}
		return treeList;
	}

	@Override
	public JSONObject getWebServiceMethodAndParams(String wsUrl) throws DocumentException {
		// TODO Auto-generated method stub
		JSONObject wsdlJson = new JSONObject();
		// 获取wsdl内容
		String wsdlContent = HttpSoapClientUtil.getWsdl(wsUrl);
		if (StringUtils.isNotEmpty(wsdlContent)) {
			Document document = DocumentHelper.parseText(wsdlContent);
			// 获取根节点元素对象
			Element root = document.getRootElement();
			// 判断是否包含s命名空间
			Namespace snm = root.getNamespaceForPrefix("wsdl");
			String wsdlType = null;
			if (snm != null && snm.getURI().equals("http://schemas.xmlsoap.org/wsdl/")) {
				wsdlType = "1";
			}
			// 获取目标命名空间
			String targetNamespace = root.attributeValue("targetNamespace");
			wsdlJson.put("targetNamespace", targetNamespace);
			// 获取协议版本
			String soapProtocal = root.getNamespaceForPrefix("soap12") != null ? "soap1.2" : "soap1.1";
			wsdlJson.put("soapProtocal", soapProtocal);
			// 获取服务名称
			String name = root.attributeValue("name");
			wsdlJson.put("name", name);
			// 获取服务地址
			Element soapAddress = soapProtocal.equals("1.1") ? (Element) root.selectSingleNode("//soap:address")
					: (Element) root.selectSingleNode("//soap12:address");
			wsdlJson.put("soapAddress", soapAddress.attributeValue("location"));
			// 获取xsd文件路径 不同框架提供的webservice服务端wsdl xml 结构描述有差异
			// 查找节点xsd:import 对应的schemaLocation
			Element schemaLocationNode = null;
			try {
				schemaLocationNode = root.selectSingleNode("//xsd:import") != null
						? (Element) root.selectSingleNode("//xsd:import")
						: null;
			} catch (Exception e) {

			}
			JSONObject methodParamJson = null;
			if (schemaLocationNode != null) {
				// 获取XDL文件路径
				String schemaLocation = schemaLocationNode.attributeValue("schemaLocation");
				if (StringUtils.isNotEmpty(schemaLocation)) {
					methodParamJson = getWebServiceWsdlXsd(schemaLocation);

				} else {
					// 获取schema节点所在位置
					Element schemaElem = root.selectSingleNode("//schema") != null
							? (Element) root.selectSingleNode("//schema")
							: null;
					if (schemaElem != null)
						methodParamJson = getWebServiceWsdlXsdByElement(schemaElem, wsdlType);
				}

			} else {
				// 获取schema节点所在位置
				Element schemaElem = root.selectSingleNode("//schema") != null
						? (Element) root.selectSingleNode("//schema")
						: null;
				if (schemaElem == null) {
					schemaElem = root.selectSingleNode("//s:schema") != null
							? (Element) root.selectSingleNode("//s:schema")
							: null;
				}
				if (schemaElem != null)
					methodParamJson = getWebServiceWsdlXsdByElement(schemaElem, wsdlType);
			}
			wsdlJson.put("methods", methodParamJson);
		}
		return wsdlJson;
	}

	/**
	 * 获取xml xsd 方法参数
	 * 
	 * @param xsdLocation
	 * @return
	 * @throws DocumentException
	 */
	public JSONObject getWebServiceWsdlXsd(String xsdLocation) throws DocumentException {
		JSONObject methodParamJson = new JSONObject();
		// 获取xsd内容
		String xsdContent = HttpSoapClientUtil.getWsdl(xsdLocation);
		if (StringUtils.isNotEmpty(xsdContent)) {
			Document document = DocumentHelper.parseText(xsdContent);
			// 获取根节点元素对象
			Element root = document.getRootElement();
			// 获取方法元素
			List methodElements = root.elements("element");
			Element methodElement = null;
			String methodName = null;
			JSONObject paramJson = null;
			for (int i = 0; i < methodElements.size(); i++) {
				methodElement = (Element) methodElements.get(i);
				methodName = methodElement.attributeValue("name");
				if (methodName.endsWith("Response")) {
					continue;
				} else {
					paramJson = new JSONObject();
					methodParamJson.put(methodName, paramJson);
				}
			}
			// 获取参数描述元素
			List paramElements = root.elements("complexType");
			Element paramElement = null;
			Element sequenceElement = null;
			methodName = null;
			String paramType = null;
			// 参数集合
			List paramItemsElement = null;
			// 单个参数
			Element paramItemElement = null;
			// 参数数据类型
			String paramDType = null;
			// 参数名称
			String paramName = null;
			// 参数JSON
			JSONObject json = null;
			// 参数JSON
			JSONObject itemJson = null;
			for (int i = 0; i < paramElements.size(); i++) {
				paramElement = (Element) paramElements.get(i);
				methodName = paramElement.attributeValue("name");
				if (methodName.endsWith("Response")) {
					paramType = "output";
					methodName = methodName.substring(0, methodName.indexOf("Response"));
				} else {
					paramType = "input";
				}
				paramJson = methodParamJson.getJSONObject(methodName);
				// 获取xs:sequence
				sequenceElement = paramElement.element("sequence");
				// 获取所有参数元素
				paramItemsElement = sequenceElement.elements();
				json = new JSONObject();
				for (int j = 0; j < paramItemsElement.size(); j++) {
					paramItemElement = (Element) paramItemsElement.get(j);
					paramDType = paramItemElement.attributeValue("type");
					paramDType = StringUtils.replace(paramDType, "xs:", "");
					paramName = paramItemElement.attributeValue("name");
					itemJson = new JSONObject();
					itemJson.put("type", paramDType);
					itemJson.put("name", "");
					json.put(paramName, itemJson);
				}
				paramJson.put(paramType, json);
				methodParamJson.put(methodName, paramJson);
			}

		}
		return methodParamJson;
	}

	/**
	 * 获取方法参数节点描述 xsd
	 * 
	 * @param Element
	 *            schema根节点
	 * @return
	 * @throws DocumentException
	 */
	public JSONObject getWebServiceWsdlXsdByElement(Element root, String wsdlType) throws DocumentException {
		JSONObject methodParamJson = new JSONObject();
		// 获取方法元素
		List methodElements = root.elements("element");
		Element methodElement = null;
		String methodName = null;
		JSONObject paramJson = null;
		for (int i = 0; i < methodElements.size(); i++) {
			methodElement = (Element) methodElements.get(i);
			methodName = methodElement.attributeValue("name");
			if (methodName.endsWith("Response")) {
				continue;
			} else {
				paramJson = new JSONObject();
				Element paramElement = null;
				Element sequenceElement = null;
				String paramType = "input";
				// 参数集合
				List paramItemsElement = null;
				// 单个参数
				Element paramItemElement = null;
				// 参数数据类型
				String paramDType = null;
				// 参数名称
				String paramName = null;
				// 参数JSON
				JSONObject json = null;
				// 参数JSON
				JSONObject itemJson = null;
				if ("1".equals(wsdlType)) {
					paramElement = methodElement.element("complexType");
					if (paramElement == null) {
						continue;
					}
					// 获取xs:sequence
					sequenceElement = paramElement.element("sequence");
					// 获取所有参数元素
					paramItemsElement = sequenceElement.elements();
					json = new JSONObject();
					for (int j = 0; j < paramItemsElement.size(); j++) {
						paramItemElement = (Element) paramItemsElement.get(j);
						paramDType = paramItemElement.attributeValue("type");
						paramDType = StringUtils.replace(paramDType, "xs:", "");
						paramDType = StringUtils.replace(paramDType, "s:", "");
						paramName = paramItemElement.attributeValue("name");
						itemJson = new JSONObject();
						itemJson.put("type", paramDType);
						itemJson.put("name", "");
						json.put(paramName, itemJson);
					}
					paramJson.put(paramType, json);
				}
				methodParamJson.put(methodName, paramJson);
			}
		}
		if (wsdlType == null) {
			// 获取参数描述元素
			List paramElements = root.elements("complexType");
			Element paramElement = null;
			Element sequenceElement = null;
			methodName = null;
			String paramType = null;
			// 参数集合
			List paramItemsElement = null;
			// 单个参数
			Element paramItemElement = null;
			// 参数数据类型
			String paramDType = null;
			// 参数名称
			String paramName = null;
			// 参数JSON
			JSONObject json = null;
			// 参数JSON
			JSONObject itemJson = null;
			for (int i = 0; i < paramElements.size(); i++) {
				paramElement = (Element) paramElements.get(i);
				methodName = paramElement.attributeValue("name");
				if (methodName.endsWith("Response")) {
					paramType = "output";
					methodName = methodName.substring(0, methodName.indexOf("Response"));
				} else {
					paramType = "input";
				}
				paramJson = methodParamJson.getJSONObject(methodName);
				// 获取xs:sequence
				sequenceElement = paramElement.element("sequence");
				// 获取所有参数元素
				paramItemsElement = sequenceElement.elements();
				json = new JSONObject();
				for (int j = 0; j < paramItemsElement.size(); j++) {
					paramItemElement = (Element) paramItemsElement.get(j);
					paramDType = paramItemElement.attributeValue("type");
					paramDType = StringUtils.replace(paramDType, "xs:", "");
					paramName = paramItemElement.attributeValue("name");
					itemJson = new JSONObject();
					itemJson.put("type", paramDType);
					itemJson.put("name", "");
					json.put(paramName, itemJson);
				}
				paramJson.put(paramType, json);
				methodParamJson.put(methodName, paramJson);
			}
		}

		return methodParamJson;
	}

	@Resource
	public void setUisAppUserIdMappingMapper(UisAppUserIdMappingMapper uisAppUserIdMappingMapper) {
		this.uisAppUserIdMappingMapper = uisAppUserIdMappingMapper;
	}

}
