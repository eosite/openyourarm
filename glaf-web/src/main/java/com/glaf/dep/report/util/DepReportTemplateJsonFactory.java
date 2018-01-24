package com.glaf.dep.report.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.dep.report.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class DepReportTemplateJsonFactory {

	public static DepReportTemplate jsonToObject(JSONObject jsonObject) {
            DepReportTemplate model = new DepReportTemplate();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("tmpJson")) {
			model.setTmpJson(jsonObject.getString("tmpJson"));
		}
		if (jsonObject.containsKey("ruleJson")) {
			model.setRuleJson(jsonObject.getString("ruleJson"));
		}
		if (jsonObject.containsKey("ver")) {
			model.setVer(jsonObject.getInteger("ver"));
		}
		if (jsonObject.containsKey("creator")) {
			model.setCreator(jsonObject.getString("creator"));
		}
		if (jsonObject.containsKey("createDateTime")) {
			model.setCreateDateTime(jsonObject.getDate("createDateTime"));
		}
		if (jsonObject.containsKey("modifier")) {
			model.setModifier(jsonObject.getString("modifier"));
		}
		if (jsonObject.containsKey("modifyDateTime")) {
			model.setModifyDateTime(jsonObject.getDate("modifyDateTime"));
		}
		if (jsonObject.containsKey("delFlag")) {
			model.setDelFlag(jsonObject.getString("delFlag"));
		}

            return model;
	}

	public static JSONObject toJsonObject(DepReportTemplate model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		} 
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		} 
		if (model.getTmpJson() != null) {
			jsonObject.put("tmpJson", model.getTmpJson());
		} 
		if (model.getRuleJson() != null) {
			jsonObject.put("ruleJson", model.getRuleJson());
		} 
        jsonObject.put("ver", model.getVer());
		if (model.getCreator() != null) {
			jsonObject.put("creator", model.getCreator());
		} 
                if (model.getCreateDateTime() != null) {
                      jsonObject.put("createDateTime", DateUtils.getDate(model.getCreateDateTime()));
		      jsonObject.put("createDateTime_date", DateUtils.getDate(model.getCreateDateTime()));
		      jsonObject.put("createDateTime_datetime", DateUtils.getDateTime(model.getCreateDateTime()));
                }
		if (model.getModifier() != null) {
			jsonObject.put("modifier", model.getModifier());
		} 
                if (model.getModifyDateTime() != null) {
                      jsonObject.put("modifyDateTime", DateUtils.getDate(model.getModifyDateTime()));
		      jsonObject.put("modifyDateTime_date", DateUtils.getDate(model.getModifyDateTime()));
		      jsonObject.put("modifyDateTime_datetime", DateUtils.getDateTime(model.getModifyDateTime()));
                }
		if (model.getDelFlag() != null) {
			jsonObject.put("delFlag", model.getDelFlag());
		} 
		return jsonObject;
	}


	public static ObjectNode toObjectNode(DepReportTemplate model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getCode() != null) {
                     jsonObject.put("code", model.getCode());
                } 
                if (model.getName() != null) {
                     jsonObject.put("name", model.getName());
                } 
                if (model.getTmpJson() != null) {
                     jsonObject.put("tmpJson", model.getTmpJson());
                } 
                if (model.getRuleJson() != null) {
                     jsonObject.put("ruleJson", model.getRuleJson());
                } 
                jsonObject.put("ver", model.getVer());
                if (model.getCreator() != null) {
                     jsonObject.put("creator", model.getCreator());
                } 
                if (model.getCreateDateTime() != null) {
                      jsonObject.put("createDateTime", DateUtils.getDate(model.getCreateDateTime()));
		      jsonObject.put("createDateTime_date", DateUtils.getDate(model.getCreateDateTime()));
		      jsonObject.put("createDateTime_datetime", DateUtils.getDateTime(model.getCreateDateTime()));
                }
                if (model.getModifier() != null) {
                     jsonObject.put("modifier", model.getModifier());
                } 
                if (model.getModifyDateTime() != null) {
                      jsonObject.put("modifyDateTime", DateUtils.getDate(model.getModifyDateTime()));
		      jsonObject.put("modifyDateTime_date", DateUtils.getDate(model.getModifyDateTime()));
		      jsonObject.put("modifyDateTime_datetime", DateUtils.getDateTime(model.getModifyDateTime()));
                }
                if (model.getDelFlag() != null) {
                     jsonObject.put("delFlag", model.getDelFlag());
                } 
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<DepReportTemplate> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DepReportTemplate model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DepReportTemplate> arrayToList(JSONArray array) {
		java.util.List<DepReportTemplate> list = new java.util.ArrayList<DepReportTemplate>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DepReportTemplate model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private DepReportTemplateJsonFactory() {

	}

}
