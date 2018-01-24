package com.glaf.workflow.core.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.workflow.core.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class ActReTaskHisJsonFactory {

	public static ActReTaskHis jsonToObject(JSONObject jsonObject) {
            ActReTaskHis model = new ActReTaskHis();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getInteger("id"));
		}
		if (jsonObject.containsKey("actorId")) {
			model.setActorId(jsonObject.getString("actorId"));
		}
		if (jsonObject.containsKey("taskId")) {
			model.setTaskId(jsonObject.getString("taskId"));
		}
		if (jsonObject.containsKey("taskName")) {
			model.setTaskName(jsonObject.getString("taskName"));
		}
		if (jsonObject.containsKey("taskKey")) {
			model.setTaskKey(jsonObject.getString("taskKey"));
		}
		if (jsonObject.containsKey("processId")) {
			model.setProcessId(jsonObject.getString("processId"));
		}
		if (jsonObject.containsKey("fromKey")) {
			model.setFromKey(jsonObject.getString("fromKey"));
		}
		if (jsonObject.containsKey("fromId")) {
			model.setFromId(jsonObject.getString("fromId"));
		}
		if (jsonObject.containsKey("fromName")) {
			model.setFromName(jsonObject.getString("fromName"));
		}
		if (jsonObject.containsKey("fromActorId")) {
			model.setFromActorId(jsonObject.getString("fromActorId"));
		}
		if (jsonObject.containsKey("variables")) {
			model.setVariables(jsonObject.getString("variables"));
		}
		if (jsonObject.containsKey("subType")) {
			model.setSubType(jsonObject.getString("subType"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}

            return model;
	}

	public static JSONObject toJsonObject(ActReTaskHis model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getActorId() != null) {
			jsonObject.put("actorId", model.getActorId());
		} 
		if (model.getTaskId() != null) {
			jsonObject.put("taskId", model.getTaskId());
		} 
		if (model.getTaskName() != null) {
			jsonObject.put("taskName", model.getTaskName());
		} 
		if (model.getTaskKey() != null) {
			jsonObject.put("taskKey", model.getTaskKey());
		} 
		if (model.getProcessId() != null) {
			jsonObject.put("processId", model.getProcessId());
		} 
		if (model.getFromKey() != null) {
			jsonObject.put("fromKey", model.getFromKey());
		} 
		if (model.getFromId() != null) {
			jsonObject.put("fromId", model.getFromId());
		} 
		if (model.getFromName() != null) {
			jsonObject.put("fromName", model.getFromName());
		} 
		if (model.getFromActorId() != null) {
			jsonObject.put("fromActorId", model.getFromActorId());
		} 
		if (model.getVariables() != null) {
			jsonObject.put("variables", model.getVariables());
		} 
		if (model.getSubType() != null) {
			jsonObject.put("subType", model.getSubType());
		} 
                if (model.getCreateDate() != null) {
                      jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
                }
		return jsonObject;
	}


	public static ObjectNode toObjectNode(ActReTaskHis model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getActorId() != null) {
                     jsonObject.put("actorId", model.getActorId());
                } 
                if (model.getTaskId() != null) {
                     jsonObject.put("taskId", model.getTaskId());
                } 
                if (model.getTaskName() != null) {
                     jsonObject.put("taskName", model.getTaskName());
                } 
                if (model.getTaskKey() != null) {
                     jsonObject.put("taskKey", model.getTaskKey());
                } 
                if (model.getProcessId() != null) {
                     jsonObject.put("processId", model.getProcessId());
                } 
                if (model.getFromKey() != null) {
                     jsonObject.put("fromKey", model.getFromKey());
                } 
                if (model.getFromId() != null) {
                     jsonObject.put("fromId", model.getFromId());
                } 
                if (model.getFromName() != null) {
                     jsonObject.put("fromName", model.getFromName());
                } 
                if (model.getFromActorId() != null) {
                     jsonObject.put("fromActorId", model.getFromActorId());
                } 
                if (model.getVariables() != null) {
                     jsonObject.put("variables", model.getVariables());
                } 
                if (model.getSubType() != null) {
                     jsonObject.put("subType", model.getSubType());
                } 
                if (model.getCreateDate() != null) {
                      jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
                }
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<ActReTaskHis> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ActReTaskHis model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ActReTaskHis> arrayToList(JSONArray array) {
		java.util.List<ActReTaskHis> list = new java.util.ArrayList<ActReTaskHis>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ActReTaskHis model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private ActReTaskHisJsonFactory() {

	}

}
