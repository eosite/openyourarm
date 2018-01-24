package com.glaf.report.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.report.core.domain.SysReportTemplate;

/**
 * 
 * JSON工厂类
 *
 */
public class SysReportTemplateJsonFactory {

	public static SysReportTemplate jsonToObject(JSONObject jsonObject) {
		SysReportTemplate model = new SysReportTemplate();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("reportTemplateName")) {
			model.setReportTemplateName(jsonObject
					.getString("reportTemplateName"));
		}
		if (jsonObject.containsKey("reportTemplateFile")) {
			// model.setReportTemplateFile(jsonObject.getString("reportTemplateFile"));
		}
		if (jsonObject.containsKey("ctime")) {
			model.setCtime(jsonObject.getDate("ctime"));
		}
		if (jsonObject.containsKey("utime")) {
			model.setUtime(jsonObject.getDate("utime"));
		}

		return model;
	}

	public static JSONObject toJsonObject(SysReportTemplate model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getReportTemplateName() != null) {
			jsonObject.put("reportTemplateName", model.getReportTemplateName());
		}
		if (model.getReportTemplateFile() != null) {
			jsonObject.put("reportTemplateFile", model.getReportTemplateFile());
		}
		if (model.getCtime() != null) {
			jsonObject.put("ctime", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_date", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_datetime",
					DateUtils.getDateTime(model.getCtime()));
		}
		if (model.getUtime() != null) {
			jsonObject.put("utime", DateUtils.getDate(model.getUtime()));
			jsonObject.put("utime_date", DateUtils.getDate(model.getUtime()));
			jsonObject.put("utime_datetime",
					DateUtils.getDateTime(model.getUtime()));
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SysReportTemplate model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getReportTemplateName() != null) {
			jsonObject.put("reportTemplateName", model.getReportTemplateName());
		}
		if (model.getReportTemplateFile() != null) {
			jsonObject.put("reportTemplateFile", model.getReportTemplateFile());
		}
		if (model.getCtime() != null) {
			jsonObject.put("ctime", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_date", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_datetime",
					DateUtils.getDateTime(model.getCtime()));
		}
		if (model.getUtime() != null) {
			jsonObject.put("utime", DateUtils.getDate(model.getUtime()));
			jsonObject.put("utime_date", DateUtils.getDate(model.getUtime()));
			jsonObject.put("utime_datetime",
					DateUtils.getDateTime(model.getUtime()));
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<SysReportTemplate> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SysReportTemplate model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SysReportTemplate> arrayToList(JSONArray array) {
		java.util.List<SysReportTemplate> list = new java.util.ArrayList<SysReportTemplate>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SysReportTemplate model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private SysReportTemplateJsonFactory() {

	}

}
