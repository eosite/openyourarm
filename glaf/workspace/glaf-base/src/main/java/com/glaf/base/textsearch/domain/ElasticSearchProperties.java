package com.glaf.base.textsearch.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.druid.util.StringUtils;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.BaseDataInfo;

public class ElasticSearchProperties {

	private static ElasticSearchProperties elasticSearchProperties;

	public static ElasticSearchProperties getInstance() {
		if (elasticSearchProperties == null) {
			elasticSearchProperties = new ElasticSearchProperties();
			elasticSearchProperties.init();
		}
		return elasticSearchProperties;
	}

	public static void init() {
		List<BaseDataInfo> basedatas = BaseDataManager.getInstance().getBaseData("fulltextsearch");
		String code = null;
		// 获取全文检索字典配置
		String val = null;
		for (BaseDataInfo baseDataInfo : basedatas) {
			code = baseDataInfo.getCode();
			val = baseDataInfo.getValue();
			switch (code) {
			case "esserver":
				elasticSearchProperties.setEsserver(val);
				break;
			case "indexname":
				elasticSearchProperties.setIndexname(val);
				break;
			case "analyzerName":
				elasticSearchProperties.setAnalyzerName(val);
				break;
			case "analyzerType":
				elasticSearchProperties.setAnalyzerType(val);
				break;
			case "type":
				elasticSearchProperties.setType(val);
				break;
			case "tokenizer":
				elasticSearchProperties.setTokenizer(val);
				break;
			case "tokenfilter":
				elasticSearchProperties.setTokenfilter(val);
				break;
			case "characterfilter":
				elasticSearchProperties.setCharacterfilter(val);
				break;
			case "filter":
				elasticSearchProperties.setFilter(val);
				break;
			default:
				break;
			}
		}
		// 获取字段映射定义
		List<BaseDataInfo> fielddatas = BaseDataManager.getInstance().getBaseData("fulltextsearchfield");
		if (CollectionUtils.isNotEmpty(fielddatas)) {
			List<ElasticSearchFieldProperties> elasticSearchFieldsProp = new ArrayList<ElasticSearchFieldProperties>();
			ElasticSearchFieldProperties elasticSearchFieldProperties = null;
			String fieldCode = null;
			// 映射代码
			String mappingCode = null;
			// 字段类型
			String fieldType = null;
			// 格式化
			String format = null;
			// 索引分词器
			String analyzer = null;
			// 字段分词器
			String search_analyzer = null;
			// 词向量
			String term_vector = null;
			// 分词标识
			Long analyzerFlag = null;
			// 查询返回标识
			Long searchReturnFlag = null;
			//返回字段
			List<String> searchReturnFields = new ArrayList<String>();
			//查询字段
			List<String> queryFields = new ArrayList<String>();
			for (BaseDataInfo fieldData : fielddatas) {
				elasticSearchFieldProperties = new ElasticSearchFieldProperties();
				fieldCode = fieldData.getCode();
				elasticSearchFieldProperties.setCode(fieldCode);
				mappingCode = fieldData.getValue();
				elasticSearchFieldProperties.setMappingCode(mappingCode);
				fieldType = fieldData.getExt1();
				elasticSearchFieldProperties.setType(fieldType);
				format = fieldData.getDesc();
				elasticSearchFieldProperties.setFormat(format);
				analyzer = fieldData.getExt2();
				if(StringUtils.isEmpty(analyzer)){
					analyzer=elasticSearchProperties.getAnalyzerName();
				}
				elasticSearchFieldProperties.setAnalyzer(analyzer);
				search_analyzer = fieldData.getExt4();
				if(StringUtils.isEmpty(search_analyzer)){
					search_analyzer=analyzer;
				}
				elasticSearchFieldProperties.setSearch_analyzer(search_analyzer);
				term_vector = fieldData.getExt3();
				elasticSearchFieldProperties.setTerm_vector(term_vector);
				analyzerFlag = fieldData.getExt12();
				elasticSearchFieldProperties.setAnalyzerFlag(analyzerFlag);
				searchReturnFlag = fieldData.getExt11();
				elasticSearchFieldProperties.setSearchReturnFlag(searchReturnFlag);
				if (searchReturnFlag != null && searchReturnFlag == 1) {
					searchReturnFields.add(fieldCode);
				}
				if(!fieldCode.equals("_all"))
				{
					if(search_analyzer!=null&&search_analyzer.indexOf("pinyin")>-1){
						fieldCode=fieldCode+".pinyin";
					}
					queryFields.add(fieldCode);
				}
				elasticSearchFieldsProp.add(elasticSearchFieldProperties);
			}
			elasticSearchProperties.setQueryFields(queryFields);
			elasticSearchProperties.setSearchReturnFields(searchReturnFields);
			elasticSearchProperties.setElasticSearchFieldsProp(elasticSearchFieldsProp);
		}
	}

	public void refresh() {
		init();
	}

	// ES服务器地址
	private String esserver = null;
	// 索引名称
	private String indexname = null;
	// 分类
	private String type = null;
	// 默认分词器
	private String tokenizer = null;
	// 词元处理器
	private String tokenfilter = null;
	// 字符处理器
	private String characterfilter = null;
	// 分析器名称
	private String analyzerName = null;
	// 分析器类型
	private String analyzerType = null;
	// 自定义过滤器
	private String filter = null;
	// 字段映射定义
	private List<ElasticSearchFieldProperties> elasticSearchFieldsProp = null;
	// 返回查询字段
	private List<String> searchReturnFields = null;
	// 查询字段
	private List<String> queryFields = null;

	public String getEsserver() {
		return esserver;
	}

	public String getIndexname() {
		return indexname;
	}

	public String getType() {
		return type;
	}

	public String getTokenizer() {
		return tokenizer;
	}

	public String getTokenfilter() {
		return tokenfilter;
	}

	public String getCharacterfilter() {
		return characterfilter;
	}

	public void setEsserver(String esserver) {
		this.esserver = esserver;
	}

	public void setIndexname(String indexname) {
		this.indexname = indexname;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTokenizer(String tokenizer) {
		this.tokenizer = tokenizer;
	}

	public void setTokenfilter(String tokenfilter) {
		this.tokenfilter = tokenfilter;
	}

	public void setCharacterfilter(String characterfilter) {
		this.characterfilter = characterfilter;
	}

	public String getAnalyzerName() {
		return analyzerName;
	}

	public String getAnalyzerType() {
		return analyzerType;
	}

	public void setAnalyzerName(String analyzerName) {
		this.analyzerName = analyzerName;
	}

	public void setAnalyzerType(String analyzerType) {
		this.analyzerType = analyzerType;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public List<ElasticSearchFieldProperties> getElasticSearchFieldsProp() {
		return elasticSearchFieldsProp;
	}

	public void setElasticSearchFieldsProp(List<ElasticSearchFieldProperties> elasticSearchFieldsProp) {
		this.elasticSearchFieldsProp = elasticSearchFieldsProp;
	}

	public List<String> getSearchReturnFields() {
		return searchReturnFields;
	}

	public void setSearchReturnFields(List<String> searchReturnFields) {
		this.searchReturnFields = searchReturnFields;
	}

	public List<String> getQueryFields() {
		return queryFields;
	}

	public void setQueryFields(List<String> queryFields) {
		this.queryFields = queryFields;
	}
}
