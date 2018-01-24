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
public class DepReportCellJsonFactory {

	public static DepReportCell jsonToObject(JSONObject jsonObject) {
            DepReportCell model = new DepReportCell();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("repTemplateId")) {
			model.setRepTemplateId(jsonObject.getLong("repTemplateId"));
		}
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("desc")) {
			model.setDesc(jsonObject.getString("desc"));
		}
		if (jsonObject.containsKey("hide")) {
			model.setHide(jsonObject.getString("hide"));
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
		if (jsonObject.containsKey("delflag")) {
			model.setDelflag(jsonObject.getString("delflag"));
		}

            return model;
	}

	public static JSONObject toJsonObject(DepReportCell model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
        jsonObject.put("repTemplateId", model.getRepTemplateId());
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		} 
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		} 
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		} 
		if (model.getHide() != null) {
			jsonObject.put("hide", model.getHide());
		} 
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
		if (model.getDelflag() != null) {
			jsonObject.put("delflag", model.getDelflag());
		} 
		return jsonObject;
	}


	public static ObjectNode toObjectNode(DepReportCell model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                jsonObject.put("repTemplateId", model.getRepTemplateId());
                if (model.getCode() != null) {
                     jsonObject.put("code", model.getCode());
                } 
                if (model.getName() != null) {
                     jsonObject.put("name", model.getName());
                } 
                if (model.getDesc() != null) {
                     jsonObject.put("desc", model.getDesc());
                } 
                if (model.getHide() != null) {
                     jsonObject.put("hide", model.getHide());
                } 
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
                if (model.getDelflag() != null) {
                     jsonObject.put("delflag", model.getDelflag());
                } 
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<DepReportCell> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DepReportCell model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DepReportCell> arrayToList(JSONArray array) {
		java.util.List<DepReportCell> list = new java.util.ArrayList<DepReportCell>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DepReportCell model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private DepReportCellJsonFactory() {

	}

}
