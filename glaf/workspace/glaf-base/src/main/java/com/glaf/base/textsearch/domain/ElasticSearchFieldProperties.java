package com.glaf.base.textsearch.domain;

public class ElasticSearchFieldProperties {
	   
	    private String code = null;
	    //映射代码
	    private String mappingCode = null;
	    //字段类型
		private String type = null;
		//格式化
		private String format = null;
		//索引分词器
		private String analyzer = null;
		//字段分词器
		private String search_analyzer = null;
		//词向量
		private String term_vector=null;
		//分词标识
		private Long analyzerFlag=null;
		//查询返回标识
		private Long searchReturnFlag=null;
		public String getCode() {
			return code;
		}
		public String getMappingCode() {
			return mappingCode;
		}
		public String getType() {
			return type;
		}
		public String getAnalyzer() {
			return analyzer;
		}
		public String getSearch_analyzer() {
			return search_analyzer;
		}
		public String getTerm_vector() {
			return term_vector;
		}
		public Long getAnalyzerFlag() {
			return analyzerFlag;
		}
		public Long getSearchReturnFlag() {
			return searchReturnFlag;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public void setMappingCode(String mappingCode) {
			this.mappingCode = mappingCode;
		}
		public void setType(String type) {
			this.type = type;
		}
		public void setAnalyzer(String analyzer) {
			this.analyzer = analyzer;
		}
		public void setSearch_analyzer(String search_analyzer) {
			this.search_analyzer = search_analyzer;
		}
		public void setTerm_vector(String term_vector) {
			this.term_vector = term_vector;
		}
		public void setAnalyzerFlag(Long analyzerFlag) {
			this.analyzerFlag = analyzerFlag;
		}
		public void setSearchReturnFlag(Long searchReturnFlag) {
			this.searchReturnFlag = searchReturnFlag;
		}
		public String getFormat() {
			return format;
		}
		public void setFormat(String format) {
			this.format = format;
		}
}
