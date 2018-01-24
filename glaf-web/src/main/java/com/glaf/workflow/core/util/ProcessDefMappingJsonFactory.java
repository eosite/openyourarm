package com.glaf.workflow.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.workflow.core.domain.ProcessDefMapping;


/**
 * 
 * JSON工厂类
 *
 */
public class ProcessDefMappingJsonFactory {

	public static ProcessDefMapping jsonToObject(JSONObject jsonObject) {
            ProcessDefMapping model = new ProcessDefMapping();
            if (jsonObject.containsKey("iD")) {
		    model.setID(jsonObject.getString("iD"));
		}
		if (jsonObject.containsKey("srcProcDefId")) {
			model.setSrcProcDefId(jsonObject.getString("srcProcDefId"));
		}
		if (jsonObject.containsKey("desProcDefId")) {
			model.setDesProcDefId(jsonObject.getString("desProcDefId"));
		}
		if (jsonObject.containsKey("srcSysId")) {
			model.setSrcSysId(jsonObject.getString("srcSysId"));
		}
		if (jsonObject.containsKey("desSysId")) {
			model.setDesSysId(jsonObject.getString("desSysId"));
		}
		if (jsonObject.containsKey("createDatetime")) {
			model.setCreateDatetime(jsonObject.getDate("createDatetime"));
		}
		if (jsonObject.containsKey("creator")) {
			model.setCreator(jsonObject.getString("creator"));
		}

            return model;
	}

	public static JSONObject toJsonObject(ProcessDefMapping model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("iD", model.getID());
		jsonObject.put("_iD_", model.getID());
		if (model.getSrcProcDefId() != null) {
			jsonObject.put("srcProcDefId", model.getSrcProcDefId());
		} 
		if (model.getDesProcDefId() != null) {
			jsonObject.put("desProcDefId", model.getDesProcDefId());
		} 
		if (model.getSrcSysId() != null) {
			jsonObject.put("srcSysId", model.getSrcSysId());
		} 
		if (model.getDesSysId() != null) {
			jsonObject.put("desSysId", model.getDesSysId());
		} 
                if (model.getCreateDatetime() != null) {
                      jsonObject.put("createDatetime", DateUtils.getDate(model.getCreateDatetime()));
		      jsonObject.put("createDatetime_date", DateUtils.getDate(model.getCreateDatetime()));
		      jsonObject.put("createDatetime_datetime", DateUtils.getDateTime(model.getCreateDatetime()));
                }
		if (model.getCreator() != null) {
			jsonObject.put("creator", model.getCreator());
		} 
		return jsonObject;
	}


	public static ObjectNode toObjectNode(ProcessDefMapping model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("iD", model.getID());
		jsonObject.put("_iD_", model.getID());
                if (model.getSrcProcDefId() != null) {
                     jsonObject.put("srcProcDefId", model.getSrcProcDefId());
                } 
                if (model.getDesProcDefId() != null) {
                     jsonObject.put("desProcDefId", model.getDesProcDefId());
                } 
                if (model.getSrcSysId() != null) {
                     jsonObject.put("srcSysId", model.getSrcSysId());
                } 
                if (model.getDesSysId() != null) {
                     jsonObject.put("desSysId", model.getDesSysId());
                } 
                if (model.getCreateDatetime() != null) {
                      jsonObject.put("createDatetime", DateUtils.getDate(model.getCreateDatetime()));
		      jsonObject.put("createDatetime_date", DateUtils.getDate(model.getCreateDatetime()));
		      jsonObject.put("createDatetime_datetime", DateUtils.getDateTime(model.getCreateDatetime()));
                }
                if (model.getCreator() != null) {
                     jsonObject.put("creator", model.getCreator());
                } 
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<ProcessDefMapping> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ProcessDefMapping model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ProcessDefMapping> arrayToList(JSONArray array) {
		java.util.List<ProcessDefMapping> list = new java.util.ArrayList<ProcessDefMapping>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ProcessDefMapping model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private ProcessDefMappingJsonFactory() {

	}

}
