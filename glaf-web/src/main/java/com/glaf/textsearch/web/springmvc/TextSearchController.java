package com.glaf.textsearch.web.springmvc;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.textsearch.domain.ElasticSearchProperties;
import com.glaf.base.utils.ElasticSearchUtils;
import com.glaf.core.util.RequestUtils;

@Controller("/textsearch")
@RequestMapping("/search")
public class TextSearchController {
	private static final Log logger = LogFactory.getLog(TextSearchController.class);

	@RequestMapping
	public ModelAndView searchIndex(HttpServletRequest request, ModelMap modelMap) {
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		return new ModelAndView("/textsearch/index", modelMap);
	}

	@RequestMapping("/d")
	public ModelAndView searchDetail(HttpServletRequest request, ModelMap modelMap) {
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		return new ModelAndView("/textsearch/detail", modelMap);
	}

	@RequestMapping("/r")
	@ResponseBody
	public byte[] search(@Context HttpServletRequest request) throws UnsupportedEncodingException {
		JSONObject jsonObj = new JSONObject();
		String searchWord = request.getParameter("w");
		ElasticSearchUtils elasticSearchUtils = new ElasticSearchUtils();
		//查询_all字段，效率低不建议使用
		//String queryString = elasticSearchUtils.getQueryAllJsonStr(searchWord, "search-highlight-red");
		ElasticSearchProperties elasticSearchProperties=ElasticSearchProperties.getInstance();
		//获取返回字段
		List<String> returnFields=elasticSearchProperties.getSearchReturnFields();
		//查询字段
		List<String> queryFields=elasticSearchProperties.getQueryFields();
		String queryString = elasticSearchUtils.getMultiMatchQueryAllJsonStr(searchWord,"most_fields",queryFields,returnFields,"search-highlight-red");
		// 获取总记录数
		String server = elasticSearchProperties.getEsserver();
		String indexName = elasticSearchProperties.getIndexname();
		String typeName = elasticSearchProperties.getType();
		// 获取单页条数
		int pagesize = RequestUtils.getInteger(request, "pagesize", 10);
		// 获取当前页数
		long pageNo = RequestUtils.getLong(request, "pageNo", 1l);
		long records = RequestUtils.getLong(request, "records", 0l);
		if (pageNo == 1) {
			records = elasticSearchUtils.searcherRecords(server, indexName, typeName, queryString);
		}
		jsonObj.put("pagesize", pagesize);
		jsonObj.put("pageNo", pageNo);
		jsonObj.put("records", records);
		// 分页查询
		if (records > 0l) {
			JSONObject resultObj = elasticSearchUtils.pagingSearcher(server, indexName, typeName, queryString, pageNo,
					pagesize);
			if (resultObj != null)
				jsonObj.putAll(resultObj);
		}
		return JSON.toJSONString(jsonObj).getBytes("UTF-8");
	}

	/**
	 * 创建索引
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/c/index")
	@ResponseBody
	public byte[] createIndex(@Context HttpServletRequest request) throws UnsupportedEncodingException {
		JSONObject jsonObj = new JSONObject();
		
		ElasticSearchUtils elasticSearchUtils = new ElasticSearchUtils();
		try {
			ElasticSearchProperties elasticSearchProperties=ElasticSearchProperties.getInstance();
			elasticSearchUtils.createIndex(elasticSearchProperties);
			jsonObj.put("result", 1);
		} catch (Exception e) {
			logger.error(e.getMessage());
			jsonObj.put("result", -1);
		}
		return JSON.toJSONString(jsonObj).getBytes("UTF-8");
	}

	/**
	 * 创建分类映射
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/c/mapping")
	@ResponseBody
	public byte[] createTypeMapping(@Context HttpServletRequest request) throws UnsupportedEncodingException {
		JSONObject jsonObj = new JSONObject();
		ElasticSearchUtils elasticSearchUtils = new ElasticSearchUtils();
		try {
			ElasticSearchProperties elasticSearchProperties=ElasticSearchProperties.getInstance();
			elasticSearchUtils.createMapping(elasticSearchProperties);
			jsonObj.put("result", 1);
		} catch (Exception e) {
			logger.error(e.getMessage());
			jsonObj.put("result", -1);
		}
		return JSON.toJSONString(jsonObj).getBytes("UTF-8");
	}
}
