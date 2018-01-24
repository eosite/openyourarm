package com.glaf.form.core.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class FormTaskmainJsonFactory {

	public static FormTaskmain jsonToObject(JSONObject jsonObject) {
            FormTaskmain model = new FormTaskmain();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("planId")) {
			model.setPlanId(jsonObject.getLong("planId"));
		}
		if (jsonObject.containsKey("defId")) {
			model.setDefId(jsonObject.getString("defId"));
		}
		if (jsonObject.containsKey("definedId")) {
			model.setDefinedId(jsonObject.getString("definedId"));
		}
		if (jsonObject.containsKey("processId")) {
			model.setProcessId(jsonObject.getString("processId"));
		}
		if (jsonObject.containsKey("variableVal")) {
			model.setVariableVal(jsonObject.getString("variableVal"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}
		if (jsonObject.containsKey("updateDate")) {
			model.setUpdateDate(jsonObject.getDate("updateDate"));
		}

            return model;
	}

	public static JSONObject toJsonObject(FormTaskmain model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
        jsonObject.put("planId", model.getPlanId());
		if (model.getDefId() != null) {
			jsonObject.put("defId", model.getDefId());
		} 
		if (model.getDefinedId() != null) {
			jsonObject.put("definedId", model.getDefinedId());
		} 
		if (model.getProcessId() != null) {
			jsonObject.put("processId", model.getProcessId());
		} 
		if (model.getVariableVal() != null) {
			jsonObject.put("variableVal", model.getVariableVal());
		} 
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		} 
        jsonObject.put("status", model.getStatus());
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		} 
                if (model.getCreateDate() != null) {
                      jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
                }
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		} 
                if (model.getUpdateDate() != null) {
                      jsonObject.put("updateDate", DateUtils.getDate(model.getUpdateDate()));
		      jsonObject.put("updateDate_date", DateUtils.getDate(model.getUpdateDate()));
		      jsonObject.put("updateDate_datetime", DateUtils.getDateTime(model.getUpdateDate()));
                }
		return jsonObject;
	}


	public static ObjectNode toObjectNode(FormTaskmain model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                jsonObject.put("planId", model.getPlanId());
                if (model.getDefId() != null) {
                     jsonObject.put("defId", model.getDefId());
                } 
                if (model.getDefinedId() != null) {
                     jsonObject.put("definedId", model.getDefinedId());
                } 
                if (model.getProcessId() != null) {
                     jsonObject.put("processId", model.getProcessId());
                } 
                if (model.getVariableVal() != null) {
                     jsonObject.put("variableVal", model.getVariableVal());
                } 
                if (model.getName() != null) {
                     jsonObject.put("name", model.getName());
                } 
                jsonObject.put("status", model.getStatus());
                if (model.getCreateBy() != null) {
                     jsonObject.put("createBy", model.getCreateBy());
                } 
                if (model.getCreateDate() != null) {
                      jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
                }
                if (model.getUpdateBy() != null) {
                     jsonObject.put("updateBy", model.getUpdateBy());
                } 
                if (model.getUpdateDate() != null) {
                      jsonObject.put("updateDate", DateUtils.getDate(model.getUpdateDate()));
		      jsonObject.put("updateDate_date", DateUtils.getDate(model.getUpdateDate()));
		      jsonObject.put("updateDate_datetime", DateUtils.getDateTime(model.getUpdateDate()));
                }
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<FormTaskmain> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormTaskmain model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FormTaskmain> arrayToList(JSONArray array) {
		java.util.List<FormTaskmain> list = new java.util.ArrayList<FormTaskmain>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormTaskmain model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private FormTaskmainJsonFactory() {

	}

}
