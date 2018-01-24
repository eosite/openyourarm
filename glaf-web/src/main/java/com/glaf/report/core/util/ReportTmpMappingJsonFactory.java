package com.glaf.report.core.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.report.core.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class ReportTmpMappingJsonFactory {

	public static ReportTmpMapping jsonToObject(JSONObject jsonObject) {
            ReportTmpMapping model = new ReportTmpMapping();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("systemId")) {
			model.setSystemId(jsonObject.getString("systemId"));
		}
		if (jsonObject.containsKey("templateId")) {
			model.setTemplateId(jsonObject.getString("templateId"));
		}
		if (jsonObject.containsKey("templateCode")) {
			model.setTemplateCode(jsonObject.getString("templateCode"));
		}
		if (jsonObject.containsKey("templateName")) {
			model.setTemplateName(jsonObject.getString("templateName"));
		}
		if (jsonObject.containsKey("desc")) {
			model.setDesc(jsonObject.getString("desc"));
		}
		if (jsonObject.containsKey("creator")) {
			model.setCreator(jsonObject.getString("creator"));
		}
		if (jsonObject.containsKey("createDatetime")) {
			model.setCreateDatetime(jsonObject.getDate("createDatetime"));
		}
		if (jsonObject.containsKey("modifier")) {
			model.setModifier(jsonObject.getString("modifier"));
		}
		if (jsonObject.containsKey("modifyDatetime")) {
			model.setModifyDatetime(jsonObject.getDate("modifyDatetime"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}

            return model;
	}

	public static JSONObject toJsonObject(ReportTmpMapping model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getSystemId() != null) {
			jsonObject.put("systemId", model.getSystemId());
		} 
		if (model.getTemplateId() != null) {
			jsonObject.put("templateId", model.getTemplateId());
		} 
		if (model.getTemplateCode() != null) {
			jsonObject.put("templateCode", model.getTemplateCode());
		} 
		if (model.getTemplateName() != null) {
			jsonObject.put("templateName", model.getTemplateName());
		} 
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		} 
		if (model.getCreator() != null) {
			jsonObject.put("creator", model.getCreator());
		} 
                if (model.getCreateDatetime() != null) {
                      jsonObject.put("createDatetime", DateUtils.getDate(model.getCreateDatetime()));
		      jsonObject.put("createDatetime_date", DateUtils.getDate(model.getCreateDatetime()));
		      jsonObject.put("createDatetime_datetime", DateUtils.getDateTime(model.getCreateDatetime()));
                }
		if (model.getModifier() != null) {
			jsonObject.put("modifier", model.getModifier());
		} 
                if (model.getModifyDatetime() != null) {
                      jsonObject.put("modifyDatetime", DateUtils.getDate(model.getModifyDatetime()));
		      jsonObject.put("modifyDatetime_date", DateUtils.getDate(model.getModifyDatetime()));
		      jsonObject.put("modifyDatetime_datetime", DateUtils.getDateTime(model.getModifyDatetime()));
                }
        jsonObject.put("deleteFlag", model.getDeleteFlag());
		return jsonObject;
	}


	public static ObjectNode toObjectNode(ReportTmpMapping model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getSystemId() != null) {
                     jsonObject.put("systemId", model.getSystemId());
                } 
                if (model.getTemplateId() != null) {
                     jsonObject.put("templateId", model.getTemplateId());
                } 
                if (model.getTemplateCode() != null) {
                     jsonObject.put("templateCode", model.getTemplateCode());
                } 
                if (model.getTemplateName() != null) {
                     jsonObject.put("templateName", model.getTemplateName());
                } 
                if (model.getDesc() != null) {
                     jsonObject.put("desc", model.getDesc());
                } 
                if (model.getCreator() != null) {
                     jsonObject.put("creator", model.getCreator());
                } 
                if (model.getCreateDatetime() != null) {
                      jsonObject.put("createDatetime", DateUtils.getDate(model.getCreateDatetime()));
		      jsonObject.put("createDatetime_date", DateUtils.getDate(model.getCreateDatetime()));
		      jsonObject.put("createDatetime_datetime", DateUtils.getDateTime(model.getCreateDatetime()));
                }
                if (model.getModifier() != null) {
                     jsonObject.put("modifier", model.getModifier());
                } 
                if (model.getModifyDatetime() != null) {
                      jsonObject.put("modifyDatetime", DateUtils.getDate(model.getModifyDatetime()));
		      jsonObject.put("modifyDatetime_date", DateUtils.getDate(model.getModifyDatetime()));
		      jsonObject.put("modifyDatetime_datetime", DateUtils.getDateTime(model.getModifyDatetime()));
                }
                jsonObject.put("deleteFlag", model.getDeleteFlag());
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<ReportTmpMapping> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ReportTmpMapping model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ReportTmpMapping> arrayToList(JSONArray array) {
		java.util.List<ReportTmpMapping> list = new java.util.ArrayList<ReportTmpMapping>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ReportTmpMapping model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private ReportTmpMappingJsonFactory() {

	}

}
