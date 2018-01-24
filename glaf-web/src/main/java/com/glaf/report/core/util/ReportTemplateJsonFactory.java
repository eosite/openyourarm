package com.glaf.report.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.report.core.domain.ReportTemplate;


/**
 * 
 * JSON工厂类
 *
 */
public class ReportTemplateJsonFactory {

	public static ReportTemplate jsonToObject(JSONObject jsonObject) {
            ReportTemplate model = new ReportTemplate();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("rev")) {
			model.setRev(jsonObject.getInteger("rev"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("creator")) {
			model.setCreator(jsonObject.getString("creator"));
		}
		if (jsonObject.containsKey("createDatatime")) {
			model.setCreateDatatime(jsonObject.getDate("createDatatime"));
		}
		if (jsonObject.containsKey("modifier")) {
			model.setModifier(jsonObject.getString("modifier"));
		}
		if (jsonObject.containsKey("modifyDatatime")) {
			model.setModifyDatatime(jsonObject.getDate("modifyDatatime"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("publish")) {
			model.setPublish(jsonObject.getInteger("publish"));
		}
		if (jsonObject.containsKey("publishUser")) {
			model.setPublishUser(jsonObject.getString("publishUser"));
		}
		if (jsonObject.containsKey("publishDatetime")) {
			model.setPublishDatetime(jsonObject.getDate("publishDatetime"));
		}
		if (jsonObject.containsKey("fileName")) {
			model.setFileName(jsonObject.getString("fileName"));
		}
		if (jsonObject.containsKey("ext")) {
			model.setExt(jsonObject.getString("ext"));
		}

            return model;
	}

	public static JSONObject toJsonObject(ReportTemplate model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
        jsonObject.put("rev", model.getRev());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		} 
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		} 
		
		if (model.getCreator() != null) {
			jsonObject.put("creator", model.getCreator());
		} 
                if (model.getCreateDatatime() != null) {
                      jsonObject.put("createDatatime", DateUtils.getDate(model.getCreateDatatime()));
		      jsonObject.put("createDatatime_date", DateUtils.getDate(model.getCreateDatatime()));
		      jsonObject.put("createDatatime_datetime", DateUtils.getDateTime(model.getCreateDatatime()));
                }
		if (model.getModifier() != null) {
			jsonObject.put("modifier", model.getModifier());
		} 
                if (model.getModifyDatatime() != null) {
                      jsonObject.put("modifyDatatime", DateUtils.getDate(model.getModifyDatatime()));
		      jsonObject.put("modifyDatatime_date", DateUtils.getDate(model.getModifyDatatime()));
		      jsonObject.put("modifyDatatime_datetime", DateUtils.getDateTime(model.getModifyDatatime()));
                }
        jsonObject.put("status", model.getStatus());
        jsonObject.put("publish", model.getPublish());
		if (model.getPublishUser() != null) {
			jsonObject.put("publishUser", model.getPublishUser());
		} 
                if (model.getPublishDatetime() != null) {
                      jsonObject.put("publishDatetime", DateUtils.getDate(model.getPublishDatetime()));
		      jsonObject.put("publishDatetime_date", DateUtils.getDate(model.getPublishDatetime()));
		      jsonObject.put("publishDatetime_datetime", DateUtils.getDateTime(model.getPublishDatetime()));
                }
		if (model.getFileName() != null) {
			jsonObject.put("fileName", model.getFileName());
		} 
		if (model.getExt() != null) {
			jsonObject.put("ext", model.getExt());
		} 
		return jsonObject;
	}


	public static ObjectNode toObjectNode(ReportTemplate model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                jsonObject.put("rev", model.getRev());
                if (model.getName() != null) {
                     jsonObject.put("name", model.getName());
                } 
                if (model.getCode() != null) {
                     jsonObject.put("code", model.getCode());
                } 
                if (model.getCreator() != null) {
                     jsonObject.put("creator", model.getCreator());
                } 
                if (model.getCreateDatatime() != null) {
                      jsonObject.put("createDatatime", DateUtils.getDate(model.getCreateDatatime()));
		      jsonObject.put("createDatatime_date", DateUtils.getDate(model.getCreateDatatime()));
		      jsonObject.put("createDatatime_datetime", DateUtils.getDateTime(model.getCreateDatatime()));
                }
                if (model.getModifier() != null) {
                     jsonObject.put("modifier", model.getModifier());
                } 
                if (model.getModifyDatatime() != null) {
                      jsonObject.put("modifyDatatime", DateUtils.getDate(model.getModifyDatatime()));
		      jsonObject.put("modifyDatatime_date", DateUtils.getDate(model.getModifyDatatime()));
		      jsonObject.put("modifyDatatime_datetime", DateUtils.getDateTime(model.getModifyDatatime()));
                }
                jsonObject.put("status", model.getStatus());
                jsonObject.put("publish", model.getPublish());
                if (model.getPublishUser() != null) {
                     jsonObject.put("publishUser", model.getPublishUser());
                } 
                if (model.getPublishDatetime() != null) {
                      jsonObject.put("publishDatetime", DateUtils.getDate(model.getPublishDatetime()));
		      jsonObject.put("publishDatetime_date", DateUtils.getDate(model.getPublishDatetime()));
		      jsonObject.put("publishDatetime_datetime", DateUtils.getDateTime(model.getPublishDatetime()));
                }
                if (model.getFileName() != null) {
                     jsonObject.put("fileName", model.getFileName());
                } 
                if (model.getExt() != null) {
                     jsonObject.put("ext", model.getExt());
                } 
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<ReportTemplate> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ReportTemplate model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ReportTemplate> arrayToList(JSONArray array) {
		java.util.List<ReportTemplate> list = new java.util.ArrayList<ReportTemplate>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ReportTemplate model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private ReportTemplateJsonFactory() {

	}

}
