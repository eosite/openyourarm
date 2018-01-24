package com.glaf.base.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.glaf.base.textsearch.domain.ElasticSearchFieldProperties;
import com.glaf.base.textsearch.domain.ElasticSearchProperties;

public class ElasticSearchUtils {
	private static final Log logger = LogFactory.getLog(ElasticSearchUtils.class);

	/**
	 * 创建文档
	 * 
	 * @param indexName
	 *            索引名称，相当于数据库名称
	 * @param typeName
	 *            索引类型，相当于数据库中的表名
	 * @param id
	 *            id名称，相当于每个表中某一行记录的标识
	 * @param jsonData
	 *            json数据
	 */
	public void createDocument(String server, String indexName, String typeName, String id, String jsonData) {
		HttpClient httpClient = new HttpClient();
		String url = server + "/" + indexName + "/" + typeName + "/" + id;
		PutMethod putMethod = new PutMethod(url);
		RequestEntity se = null;
		try {
			se = new StringRequestEntity(jsonData, "application/json", "UTF-8");
			putMethod.setRequestEntity(se);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			int statusCode = httpClient.executeMethod(putMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + putMethod.getStatusLine());
			} else {
				String responseBody = putMethod.getResponseBodyAsString();
				JSONObject responseJson = JSONObject.parseObject(responseBody);
				if (responseJson != null && "true".equals(responseJson.getString("created"))) {
					logger.info("create index " + indexName + "/" + typeName + " successful!");
				} else {
					logger.info("create index " + indexName + "/" + typeName + " unsuccessful!");
				}
			}
		} catch (IOException e) {
			logger.error("Method error: " + e.getMessage());
		} finally {
			putMethod.releaseConnection();
		}
	}

	/**
	 * 创建索引自定义
	 * 
	 * @param elasticSearchProperties
	 * @throws IOException
	 */
	public void createIndex(ElasticSearchProperties elasticSearchProperties) throws IOException {
		HttpClient httpClient = new HttpClient();
		String server = elasticSearchProperties.getEsserver();
		String indexName = elasticSearchProperties.getIndexname();
		String url = server + "/" + indexName + "/";
		JSONObject analysisJson = new JSONObject();
		PutMethod putMethod = new PutMethod(url);
		RequestEntity se = null;
		try {
			analysisJson = generatorAnalysisJSON(elasticSearchProperties);
			String jsonData = analysisJson.toJSONString();
			se = new StringRequestEntity(jsonData, "application/json", "UTF-8");
			putMethod.setRequestEntity(se);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			int statusCode = httpClient.executeMethod(putMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + putMethod.getStatusLine());
			} else {
				String responseBody = putMethod.getResponseBodyAsString();
				JSONObject responseJson = JSONObject.parseObject(responseBody);
				if (responseJson != null && "true".equals(responseJson.getString("created"))) {
					logger.info("create index " + indexName + " successful!");
				} else {
					logger.info("create index " + indexName + " unsuccessful!");
				}
			}
		} catch (IOException e) {
			logger.error("Method error: " + e.getMessage());
			throw e;
		} finally {
			putMethod.releaseConnection();
		}
	}

	/**
	 * 生成索引分词配置
	 * 
	 * @param elasticSearchProperties
	 * @return
	 */
	public JSONObject generatorAnalysisJSON(ElasticSearchProperties elasticSearchProperties) {
		JSONObject analysisAllJson = new JSONObject();
		JSONObject analysisJson = new JSONObject();
		JSONObject analyzerJson = new JSONObject();
		JSONObject analyzerDetailJson = new JSONObject();
		String analyzerName = elasticSearchProperties.getAnalyzerName();
		if (StringUtils.isEmpty(analyzerName)) {
			analyzerName = "ik_pinyin_analyzer";
		}
		String analyzerType = elasticSearchProperties.getAnalyzerType();
		String tokenizer = elasticSearchProperties.getTokenizer();
		String tokenfilter = elasticSearchProperties.getTokenfilter();
		String characterfilter = elasticSearchProperties.getCharacterfilter();
		if (StringUtils.isEmpty(analyzerType)) {
			analyzerType = "custom";
		}
		analyzerDetailJson.put("type", analyzerType);
		if (StringUtils.isEmpty(tokenizer)) {
			analyzerType = "ik_smart";
		}
		analyzerDetailJson.put("tokenizer", tokenizer);
		if (StringUtils.isEmpty(tokenfilter)) {
			tokenfilter = "my_pinyin,word_delimiter";
		}
		analyzerDetailJson.put("filter", tokenfilter.split(","));
		if (StringUtils.isNotEmpty(characterfilter)) {
			analyzerDetailJson.put("char_filter", characterfilter);
		}
		analyzerJson.put(analyzerName, analyzerDetailJson);
		analysisJson.put("analyzer", analyzerJson);
		String filter = elasticSearchProperties.getFilter();
		if (StringUtils.isNotEmpty(filter)) {
			analysisJson.put("filter", JSONObject.parseObject(filter));
		}
		analysisAllJson.put("analysis", analysisJson);
		return analysisAllJson;
	}

	/**
	 * 创建索引自定义
	 * 
	 * @param elasticSearchProperties
	 * @throws IOException
	 */
	public void createMapping(ElasticSearchProperties elasticSearchProperties) throws IOException {
		HttpClient httpClient = new HttpClient();
		String server = elasticSearchProperties.getEsserver();
		String indexName = elasticSearchProperties.getIndexname();
		String typeName = elasticSearchProperties.getType();
		String url = server + "/" + indexName + "/" + typeName + "/_mapping";
		JSONObject analysisJson = new JSONObject();
		PutMethod putMethod = new PutMethod(url);
		RequestEntity se = null;
		try {
			analysisJson = generatorMappingJSON(elasticSearchProperties);
			String jsonData = analysisJson.toJSONString();
			se = new StringRequestEntity(jsonData, "application/json", "UTF-8");
			putMethod.setRequestEntity(se);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			int statusCode = httpClient.executeMethod(putMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + putMethod.getStatusLine());
			} else {
				String responseBody = putMethod.getResponseBodyAsString();
				JSONObject responseJson = JSONObject.parseObject(responseBody);
				if (responseJson != null && "true".equals(responseJson.getString("created"))) {
					logger.info("create createMapping " + indexName + "/" + typeName + " successful!");
				} else {
					logger.info("create createMapping " + indexName + "/" + typeName + " unsuccessful!");
				}
			}
		} catch (IOException e) {
			logger.error("Method error: " + e.getMessage());
			throw e;
		} finally {
			putMethod.releaseConnection();
		}
	}

	/**
	 * 生成Mapping结构体
	 * 
	 * @param elasticSearchProperties
	 * @return
	 */
	public JSONObject generatorMappingJSON(ElasticSearchProperties elasticSearchProperties) {
		List<ElasticSearchFieldProperties> elasticSearchFieldPropertiesList = elasticSearchProperties
				.getElasticSearchFieldsProp();
		JSONObject mappingAllJson = new JSONObject();
		JSONObject mappingJson = new JSONObject();
		JSONObject fieldJson = null;
		JSONObject pinyinFieldJson = null;
		JSONObject pinyinFieldItemJson = null;
		for (ElasticSearchFieldProperties elasticSearchFieldProperties : elasticSearchFieldPropertiesList) {

			fieldJson = new JSONObject();
			String fieldCode = elasticSearchFieldProperties.getCode();
			// 映射代码
			String mappingCode = elasticSearchFieldProperties.getMappingCode();
			// 字段类型
			String fieldType = elasticSearchFieldProperties.getType();
			fieldJson.put("type", fieldType);
			String format = elasticSearchFieldProperties.getFormat();
			if (StringUtils.isNotEmpty(format)) {
				fieldJson.put("format", format);
			}
			// 不分词标识
			Long analyzerFlag = elasticSearchFieldProperties.getAnalyzerFlag();
			if (analyzerFlag != null && analyzerFlag == 1) {
				if (fieldCode.equals("_all")) {
					fieldJson.put("enabled", false);
				} else {
					fieldJson.put("index", "not_analyzed");
				}
			} else {
				// 索引分词器
				String analyzer = elasticSearchFieldProperties.getAnalyzer();
				if (StringUtils.isEmpty(analyzer)) {
					analyzer = elasticSearchProperties.getAnalyzerName();
				}
				if (fieldCode.equals("_all")) {
					fieldJson.put("analyzer", analyzer);
				} else {
					// 字段分词器
					String search_analyzer = elasticSearchFieldProperties.getSearch_analyzer();
					if (StringUtils.isEmpty(search_analyzer)) {
						search_analyzer = analyzer;
					}
					// 词向量
					String term_vector = elasticSearchFieldProperties.getTerm_vector();
					// 包含拼音分词器则单独对字段创建拼音索引
					if (search_analyzer.indexOf("pinyin") > -1) {
						pinyinFieldJson = new JSONObject();
						pinyinFieldItemJson = new JSONObject();
						pinyinFieldItemJson.put("type", fieldType);
						if (StringUtils.isNotEmpty(format)) {
							pinyinFieldItemJson.put("format", format);
						}
						pinyinFieldItemJson.put("analyzer", analyzer);
						pinyinFieldItemJson.put("search_analyzer", search_analyzer);
						pinyinFieldItemJson.put("boost", 10);
						pinyinFieldItemJson.put("term_vector", term_vector);
						pinyinFieldItemJson.put("store", "no");
						pinyinFieldJson.put("pinyin", pinyinFieldItemJson);
						fieldJson.put("fields", pinyinFieldJson);
					} else {
						fieldJson.put("analyzer", analyzer);
						fieldJson.put("search_analyzer", search_analyzer);
						fieldJson.put("term_vector", term_vector);
						pinyinFieldItemJson.put("store", "no");
						pinyinFieldItemJson.put("boost", 10);
					}
				}
			}
			if (fieldCode.equals("_all")) {
				mappingAllJson.put("_all", fieldJson);
			} else {
				mappingJson.put(fieldCode, fieldJson);
			}
			mappingAllJson.put("properties", mappingJson);
		}
		return mappingAllJson;
	}

	/**
	 * 执行搜索
	 * 
	 * @param indexname
	 *            索引名称
	 * @param type
	 *            索引类型
	 * @param queryBuilder
	 *            查询条件
	 * @return
	 */
	public JSONObject searcher(String server, String indexName, String typeName, String queryJson) {
		JSONObject resultJson = null;
		HttpClient httpClient = new HttpClient();
		String url = server + "/" + indexName + "/" + typeName + "/_search";
		PostMethod postMethod = new PostMethod(url);
		RequestEntity se = null;
		try {
			se = new StringRequestEntity(queryJson, "application/json", "UTF-8");
			postMethod.setRequestEntity(se);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + postMethod.getStatusLine());
			} else {
				String responseBody = postMethod.getResponseBodyAsString();
				resultJson = JSONObject.parseObject(responseBody);
			}
		} catch (IOException e) {
			logger.error("Method error: " + e.getMessage());
		} finally {
			postMethod.releaseConnection();
		}
		return resultJson;
	}

	/**
	 * 分页执行搜索
	 * 
	 * @param indexname
	 *            索引名称
	 * @param type
	 *            索引类型
	 * @param queryBuilder
	 *            查询条件
	 * @return
	 */
	public JSONObject pagingSearcher(String server, String indexName, String typeName, String queryJson, long pageNo,
			int pagesize) {
		JSONObject resultJson = null;
		HttpClient httpClient = new HttpClient();
		String url = server + "/" + indexName + "/" + typeName + "/_search";
		PostMethod postMethod = new PostMethod(url);
		RequestEntity se = null;
		try {
			JSONObject queryJsonObject = JSONObject.parseObject(queryJson);
			long from = (pageNo - 1) * pagesize;
			queryJsonObject.put("from", from);
			queryJsonObject.put("size", pagesize);
			queryJson = queryJsonObject.toJSONString();
			se = new StringRequestEntity(queryJson, "application/json", "UTF-8");
			postMethod.setRequestEntity(se);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + postMethod.getStatusLine());
			} else {
				String responseBody = postMethod.getResponseBodyAsString();
				resultJson = JSONObject.parseObject(responseBody);
			}
		} catch (IOException e) {
			logger.error("Method error: " + e.getMessage());
		} finally {
			postMethod.releaseConnection();
		}
		return resultJson;
	}

	/**
	 * 获取总记录数
	 * 
	 * @param server
	 * @param indexName
	 * @param typeName
	 * @param queryJson
	 * @return
	 */
	public long searcherRecords(String server, String indexName, String typeName, String queryJson) {
		JSONObject resultJson = null;
		HttpClient httpClient = new HttpClient();
		String url = server + "/" + indexName + "/" + typeName + "/_search";
		PostMethod postMethod = new PostMethod(url);
		RequestEntity se = null;
		long total = 0l;
		try {
			JSONObject queryJsonObject = JSONObject.parseObject(queryJson);
			queryJsonObject.put("from", 0);
			queryJsonObject.put("size", 0);
			queryJson = queryJsonObject.toJSONString();
			se = new StringRequestEntity(queryJson, "application/json", "UTF-8");
			postMethod.setRequestEntity(se);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + postMethod.getStatusLine());
			} else {
				String responseBody = postMethod.getResponseBodyAsString();
				resultJson = JSONObject.parseObject(responseBody);
				JSONObject hits = resultJson.getJSONObject("hits");
				if (hits != null) {
					total = hits.getLong("total");
				}

			}
		} catch (IOException e) {
			logger.error("Method error: " + e.getMessage());
		} finally {
			postMethod.releaseConnection();
		}
		return total;
	}

	/**
	 * 更新文档
	 * 
	 * @param indexName
	 *            索引名称
	 * @param typeName
	 *            索引类型
	 * @param id
	 *            文档id
	 * @param jsonData
	 *            json数据
	 */
	public void updateDocument(String server, String indexName, String typeName, String id, String jsonData) {
		HttpClient httpClient = new HttpClient();
		String url = server + "/" + indexName + "/" + typeName + "/" + id;
		PutMethod putMethod = new PutMethod(url);
		RequestEntity se = null;
		try {
			se = new StringRequestEntity(jsonData, "application/json", "UTF-8");
			putMethod.setRequestEntity(se);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			int statusCode = httpClient.executeMethod(putMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + putMethod.getStatusLine());
			} else {
				String responseBody = putMethod.getResponseBodyAsString();
				JSONObject responseJson = JSONObject.parseObject(responseBody);
				if (responseJson != null && "false".equals(responseJson.getString("created"))
						&& responseJson.getInteger("_version") > 1) {
					logger.info("update doc " + indexName + "/" + typeName + " successful!");
				} else {
					logger.info("update index " + indexName + "/" + typeName + " unsuccessful!");
				}
			}
		} catch (IOException e) {
			logger.error("Method error: " + e.getMessage());
		} finally {
			putMethod.releaseConnection();
		}
	}

	/**
	 * 更新索引
	 * 
	 * @param server
	 * @param indexName
	 * @param jsonData
	 */
	public void updateIndex(String server, String indexName, String jsonData) {
		HttpClient httpClient = new HttpClient();
		String url = server + "/" + indexName;
		PutMethod putMethod = new PutMethod(url);
		RequestEntity se = null;
		try {
			se = new StringRequestEntity(jsonData, "application/json", "UTF-8");
			putMethod.setRequestEntity(se);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			int statusCode = httpClient.executeMethod(putMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + putMethod.getStatusLine());
			} else {
				String responseBody = putMethod.getResponseBodyAsString();
				JSONObject responseJson = JSONObject.parseObject(responseBody);
				if (responseJson != null && "false".equals(responseJson.getString("created"))
						&& responseJson.getInteger("_version") > 1) {
					logger.info("update index " + indexName + " successful!");
				} else {
					logger.info("update index " + indexName + " unsuccessful!");
				}
			}
		} catch (IOException e) {
			logger.error("Method error: " + e.getMessage());
		} finally {
			putMethod.releaseConnection();
		}
	}

	/**
	 * 删除文档
	 * 
	 * @param indexName
	 * @param typeName
	 * @param id
	 */
	public void deleteDocument(String server, String indexName, String typeName, String id) {
		HttpClient httpClient = new HttpClient();
		String url = server + "/" + indexName + "/" + typeName + "/" + id;
		DeleteMethod deleteMethod = new DeleteMethod(url);
		try {
			int statusCode = httpClient.executeMethod(deleteMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + deleteMethod.getStatusLine());
			} else {
				String responseBody = deleteMethod.getResponseBodyAsString();
				JSONObject responseJson = JSONObject.parseObject(responseBody);
				if (responseJson != null && "false".equals(responseJson.getString("deleted"))) {
					logger.info("delete index " + indexName + "/" + typeName + " successful!");
				} else {
					logger.info("delete index " + indexName + "/" + typeName + " unsuccessful!");
				}
			}
		} catch (IOException e) {
			logger.error("Method error: " + e.getMessage());
		} finally {
			deleteMethod.releaseConnection();
		}
	}

	/**
	 * 删除索引
	 * 
	 * @param server
	 * @param indexName
	 */
	public void deleteIndex(String server, String indexName) {
		HttpClient httpClient = new HttpClient();
		String url = server + "/" + indexName;
		DeleteMethod deleteMethod = new DeleteMethod(url);
		try {
			int statusCode = httpClient.executeMethod(deleteMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + deleteMethod.getStatusLine());
			} else {
				String responseBody = deleteMethod.getResponseBodyAsString();
				JSONObject responseJson = JSONObject.parseObject(responseBody);
				if (responseJson != null && "false".equals(responseJson.getString("deleted"))) {
					logger.info("delete index " + indexName + " successful!");
				} else {
					logger.info("delete index " + indexName + " unsuccessful!");
				}
			}
		} catch (IOException e) {
			logger.error("Method error: " + e.getMessage());
		} finally {
			deleteMethod.releaseConnection();
		}
	}

	/**
	 * 重建索引
	 * 
	 * @param server
	 * @param oldIndexName
	 * @param newIndexName
	 */
	public void reIndex(String server, String oldIndexName, String newIndexName) {
		HttpClient httpClient = new HttpClient();
		String url = server + "/_reindex";
		PostMethod postMethod = new PostMethod(url);
		RequestEntity se = null;
		String paramJson = "{\"source\": {\"index\": \"" + oldIndexName + "\"},\"dest\": {\"index\": \"" + newIndexName
				+ "\"}}";
		try {
			se = new StringRequestEntity(paramJson, "application/json", "UTF-8");
			postMethod.setRequestEntity(se);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + postMethod.getStatusLine());
			} else {
				String responseBody = postMethod.getResponseBodyAsString();
				JSONObject responseJson = JSONObject.parseObject(responseBody);
				if (responseJson != null && "true".equals(responseJson.getString("created"))) {
					logger.info(oldIndexName + " reIndex index " + newIndexName + " successful!");
				} else {
					logger.info(oldIndexName + " reIndex index " + newIndexName + " unsuccessful!");
				}
			}
		} catch (IOException e) {
			logger.error("Method error: " + e.getMessage());
		} finally {
			postMethod.releaseConnection();
		}
	}

	/**
	 * 获取查询所有字段的查询体JSON字符串
	 * 
	 * @param queryContent
	 * @return
	 */
	public String getQueryAllJsonStr(String queryContent, String className) {
		JSONObject queryJson = new JSONObject();
		JSONObject matchJson = new JSONObject();
		JSONObject allJson = new JSONObject();
		allJson.put("_all", queryContent);
		matchJson.put("match", allJson);
		queryJson.put("query", matchJson);
		JSONObject highlightJSON = getHighLightJSON(className, null);
		queryJson.put("highlight", highlightJSON);
		return queryJson.toJSONString();
	}

	/**
	 * 获取多字段查询体JSON字符串
	 * 
	 * @param queryContent
	 * @param matchType
	 *            best_fields 完全匹配占的分值高，部分匹配分值需乘以系数 most_fields 越多匹配分值越高
	 * @param queryFields
	 *            查询字段
	 * @param returnFields
	 *            返回字段
	 * @param className
	 *            高亮样式名称
	 * @return
	 */
	public String getMultiMatchQueryAllJsonStr(String queryContent, String matchType, List<String> queryFields,
			List<String> returnFields, String className) {
		JSONObject queryJson = new JSONObject();
		JSONObject multiMatchJson = new JSONObject();
		JSONObject multiMatchContentJson = new JSONObject();
		if (CollectionUtils.isNotEmpty(returnFields)) {
			queryJson.put("_source", returnFields);
		}
		multiMatchContentJson.put("query", queryContent);
		multiMatchContentJson.put("type", matchType);
		multiMatchContentJson.put("fields", queryFields);
		multiMatchJson.put("multi_match", multiMatchContentJson);
		queryJson.put("query", multiMatchJson);
		JSONObject highlightJSON = getHighLightJSON(className, queryFields);
		queryJson.put("highlight", highlightJSON);
		return queryJson.toJSONString();
	}

	/**
	 * 获取查询体高亮显示设置
	 * 
	 * @param className
	 * @param fields
	 * @return
	 */
	public JSONObject getHighLightJSON(String className, List<String> fields) {
		JSONObject highlightJSON = new JSONObject();
		List<String> preTagsList = new ArrayList<String>();
		List<String> postTagsList = new ArrayList<String>();
		JSONObject fieldJson = new JSONObject();
		if (fields != null) {
			for (String field : fields) {
				preTagsList.add("<em class=\"" + className + "\">");
				postTagsList.add("</em>");
				fieldJson.put(field, new JSONObject());
			}
		} else {
			preTagsList.add("<em class=\"" + className + "\">");
			postTagsList.add("</em>");
			highlightJSON.put("require_field_match", false);
			fieldJson.put("*", new JSONObject());
		}
		highlightJSON.put("fields", fieldJson);
		highlightJSON.put("pre_tags", preTagsList);
		highlightJSON.put("post_tags", postTagsList);
		return highlightJSON;
	}

	public static void main(String args[]) {
		ElasticSearchUtils elasticSearchUtils = new ElasticSearchUtils();
		String server = "http://192.168.1.57:9200";
		String indexName = "index";
		String typeName = "fulltext";
		String id = "4";
		String jsonData = "{\"content\":\"中韩渔警冲突调查：韩警平均每天扣1艘中国渔船\"}";
		elasticSearchUtils.createDocument(server, indexName, typeName, id, jsonData);
		String queryJson = "{\"query\":\"中国\"}";
		JSONObject object = elasticSearchUtils.pagingSearcher(server, indexName, typeName, queryJson, 1, 10);
		System.out.println(object.toJSONString());
		List<String> fields = new ArrayList<String>();
		fields.add("content");
		String queryStr = elasticSearchUtils.getMultiMatchQueryAllJsonStr("中韩", "most_fields", fields, fields,
				"red-highlight");
		object = elasticSearchUtils.pagingSearcher(server, indexName, typeName, queryStr, 1, 1);
		System.out.println(object.toJSONString());
	}
}
