package com.glaf.datamgr.website.springmvc;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.RequestUtils;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.ExecutionLog;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.datamgr.util.ExecutionLogFactory;

@Controller("/ws/dataset")
@RequestMapping("/ws/dataset")
public class DataSetWebsiteController {

	protected DataSetService dataSetService;

	/**
	 * 获取总数
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/api/total")
	public byte[] total(HttpServletRequest request) throws IOException {
		String datasetId = request.getParameter("id");

		JSONObject result = this.valiate(request);
		if (result != null) {
			return result.toJSONString().getBytes("UTF-8");
		}
		Date s = new Date();
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		result = new JSONObject();
		JSONObject json = new DataSetBuilder().getJson(datasetId, 1, 1, params);

		result.put("total", json != null ? json.get("total") : 0);

		String msg = String.format("api/total id:%s, 共 %s 条记录!", //
				datasetId, result.get("total"));

		this.writeLog(request, msg, s, "total");

		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 获取数据集json
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/api/json", produces = "text/html;charset=utf-8")
	public byte[] json(HttpServletRequest request) throws IOException {
		return this.getJson(request).toJSONString().getBytes("UTF-8");
	}

	protected JSONObject valiate(HttpServletRequest request) {
		String msg = null;
		JSONObject json = null;
		String datasetId = RequestUtils.getString(request, "id");
		DataSet ds = dataSetService.getDataSet(datasetId);
		if (ds != null) {
			if (!StringUtils.equalsIgnoreCase(//
					ds.getAccessType(), "PUB")) {
				msg = String.format("数据集 id:%s 未开放!", datasetId);
			}
		} else {
			msg = String.format("数据集 id:%s 不存在!", datasetId);
		}
		if (msg != null) {
			json = new JSONObject();
			json.put("msg", msg);
			this.writeLog(request, msg, new Date(), "error");
		}
		return json;
	}

	void writeLog(HttpServletRequest request, String msg, Date s, String type) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.put("msg", msg);
		params.put("ip", RequestUtils.getIPAddress(request));

		Date e = new Date();

		ExecutionLog log = new ExecutionLog();
		log.setBusinessKey(MapUtils.getString(params, "id"));
		log.setContent(JSON.toJSONString(params));
		log.setCreateBy(RequestUtils.getActorId(request));
		log.setStartTime(s);
		log.setEndTime(e);
		log.setRunTime(e.getTime() - s.getTime());
		log.setRunHour(Calendar.HOUR);
		log.setRunDay(DateUtils.getNowYearMonthDay());
		log.setType("dataset_api_" + type);

		ExecutionLogFactory.getInstance().addLog(log);
	}

	protected JSONObject getJson(HttpServletRequest request) {
		String datasetId = request.getParameter("id");
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		String paramsStr = MapUtils.getString(params, "params", null);
		if (StringUtils.isNotBlank(paramsStr)) {
			params.putAll(JSON.parseObject(paramsStr));
		}

		JSONObject result = this.valiate(request);
		if (result != null) {
			return result;
		}
		Date s = new Date();
		result = new JSONObject();
		int pageNo = RequestUtils.getInt(request, "page", 1);
		int limit = RequestUtils.getInt(request, "rows", 10), rows = limit;
		int start = (pageNo - 1) * limit;
		if (start <= 0) {
			start = 0;
		}
		if (limit <= 0) {
			limit = Paging.DEFAULT_PAGE_SIZE;
		}
		limit = pageNo * limit;
		if (!params.containsKey("om"))
			params.put("om", true);
		JSONObject json = new DataSetBuilder().getJson(datasetId, start, rows, params);

		String rows_key = MapUtils.getString(params, "rows_key", "rows"),
				total_key = MapUtils.getString(params, "total_key", "total");

		if (json == null) {
			json = new JSONObject();
			json.put(total_key, 0);
			json.put(rows_key, new JSONArray());
		} else {
			result.put(total_key, json.get("total"));
			result.put(rows_key, json.get("rows"));
		}

		String msg = String.format("api/json id:%s, 共 %s 条记录!", //
				datasetId, result.get(total_key));

		this.writeLog(request, msg, s, "json");

		return result;
	}

	/**
	 * 获取更新集参数
	 * 
	 * @param request
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/api/params", produces = "text/html;charset=utf-8")
	public byte[] params(HttpServletRequest request, @RequestParam Map<String, Object> parameter) throws Exception {

		JSONObject result = new JSONObject();

		String dataSetId = MapUtils.getString(parameter, "id");

		JSONArray jsonArray = dataSetService.getDataSetFields(dataSetId);
		if (CollectionUtils.isNotEmpty(jsonArray)) {
			JSONArray collection = new JSONArray();
			jsonArray.forEach(json -> {
				JSONObject j = (JSONObject) json, row = new JSONObject();
				for (String key : new String[] { "columnLabel", "title", "columnMapping" }) {
					row.put(key, j.get(key));
				}
				collection.add(row);
			});
			result.put("fields", collection);
		}

		jsonArray = dataSetService.getDataSetParams(dataSetId);
		if (CollectionUtils.isNotEmpty(jsonArray)) {
			JSONArray collection = new JSONArray();
			jsonArray.forEach(json -> {
				JSONObject j = (JSONObject) json, row = new JSONObject();
				for (String key : new String[] { "name", "param" }) {
					row.put(key, j.get(key));
				}
				collection.add(row);
			});
			result.put("whereParams", collection);
		}

		return result.toJSONString().getBytes("UTF-8");
	}

	public DataSetService getDataSetService() {
		return dataSetService;
	}

	@Resource
	public void setDataSetService(DataSetService dataSetService) {
		this.dataSetService = dataSetService;
	}

}
