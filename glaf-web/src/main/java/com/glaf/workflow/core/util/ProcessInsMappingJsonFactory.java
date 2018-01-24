package com.glaf.workflow.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.workflow.core.domain.ProcessInsMapping;


/**
 * 
 * JSON工厂类
 *
 */
public class ProcessInsMappingJsonFactory {

	public static ProcessInsMapping jsonToObject(JSONObject jsonObject) {
            ProcessInsMapping model = new ProcessInsMapping();
            if (jsonObject.containsKey("iD")) {
		    model.setID(jsonObject.getString("iD"));
		}
		if (jsonObject.containsKey("srcWbsDefId")) {
			model.setSrcWbsDefId(jsonObject.getInteger("srcWbsDefId"));
		}
		if (jsonObject.containsKey("srcWbsInsId")) {
			model.setSrcWbsInsId(jsonObject.getInteger("srcWbsInsId"));
		}
		if (jsonObject.containsKey("srcProcDefId")) {
			model.setSrcProcDefId(jsonObject.getString("srcProcDefId"));
		}
		if (jsonObject.containsKey("srcProcInsId")) {
			model.setSrcProcInsId(jsonObject.getString("srcProcInsId"));
		}
		if (jsonObject.containsKey("desWbsDefId")) {
			model.setDesWbsDefId(jsonObject.getInteger("desWbsDefId"));
		}
		if (jsonObject.containsKey("desWbsInsId")) {
			model.setDesWbsInsId(jsonObject.getInteger("desWbsInsId"));
		}
		if (jsonObject.containsKey("desProcDefId")) {
			model.setDesProcDefId(jsonObject.getString("desProcDefId"));
		}
		if (jsonObject.containsKey("desProcInsId")) {
			model.setDesProcInsId(jsonObject.getString("desProcInsId"));
		}
		if (jsonObject.containsKey("srcSysId")) {
			model.setSrcSysId(jsonObject.getString("srcSysId"));
		}
		if (jsonObject.containsKey("desSysId")) {
			model.setDesSysId(jsonObject.getString("desSysId"));
		}
		if (jsonObject.containsKey("procStatus")) {
			model.setProcStatus(jsonObject.getInteger("procStatus"));
		}
		if (jsonObject.containsKey("procResult")) {
			model.setProcResult(jsonObject.getInteger("procResult"));
		}
		if (jsonObject.containsKey("procStartTime")) {
			model.setProcStartTime(jsonObject.getDate("procStartTime"));
		}
		if (jsonObject.containsKey("procCompTime")) {
			model.setProcCompTime(jsonObject.getDate("procCompTime"));
		}

            return model;
	}

	public static JSONObject toJsonObject(ProcessInsMapping model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("iD", model.getID());
		jsonObject.put("_iD_", model.getID());
        jsonObject.put("srcWbsDefId", model.getSrcWbsDefId());
        jsonObject.put("srcWbsInsId", model.getSrcWbsInsId());
		if (model.getSrcProcDefId() != null) {
			jsonObject.put("srcProcDefId", model.getSrcProcDefId());
		} 
		if (model.getSrcProcInsId() != null) {
			jsonObject.put("srcProcInsId", model.getSrcProcInsId());
		} 
        jsonObject.put("desWbsDefId", model.getDesWbsDefId());
        jsonObject.put("desWbsInsId", model.getDesWbsInsId());
		if (model.getDesProcDefId() != null) {
			jsonObject.put("desProcDefId", model.getDesProcDefId());
		} 
		if (model.getDesProcInsId() != null) {
			jsonObject.put("desProcInsId", model.getDesProcInsId());
		} 
		if (model.getSrcSysId() != null) {
			jsonObject.put("srcSysId", model.getSrcSysId());
		} 
		if (model.getDesSysId() != null) {
			jsonObject.put("desSysId", model.getDesSysId());
		} 
        jsonObject.put("procStatus", model.getProcStatus());
        jsonObject.put("procResult", model.getProcResult());
                if (model.getProcStartTime() != null) {
                      jsonObject.put("procStartTime", DateUtils.getDate(model.getProcStartTime()));
		      jsonObject.put("procStartTime_date", DateUtils.getDate(model.getProcStartTime()));
		      jsonObject.put("procStartTime_datetime", DateUtils.getDateTime(model.getProcStartTime()));
                }
                if (model.getProcCompTime() != null) {
                      jsonObject.put("procCompTime", DateUtils.getDate(model.getProcCompTime()));
		      jsonObject.put("procCompTime_date", DateUtils.getDate(model.getProcCompTime()));
		      jsonObject.put("procCompTime_datetime", DateUtils.getDateTime(model.getProcCompTime()));
                }
		return jsonObject;
	}


	public static ObjectNode toObjectNode(ProcessInsMapping model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("iD", model.getID());
		jsonObject.put("_iD_", model.getID());
                jsonObject.put("srcWbsDefId", model.getSrcWbsDefId());
                jsonObject.put("srcWbsInsId", model.getSrcWbsInsId());
                if (model.getSrcProcDefId() != null) {
                     jsonObject.put("srcProcDefId", model.getSrcProcDefId());
                } 
                if (model.getSrcProcInsId() != null) {
                     jsonObject.put("srcProcInsId", model.getSrcProcInsId());
                } 
                jsonObject.put("desWbsDefId", model.getDesWbsDefId());
                jsonObject.put("desWbsInsId", model.getDesWbsInsId());
                if (model.getDesProcDefId() != null) {
                     jsonObject.put("desProcDefId", model.getDesProcDefId());
                } 
                if (model.getDesProcInsId() != null) {
                     jsonObject.put("desProcInsId", model.getDesProcInsId());
                } 
                if (model.getSrcSysId() != null) {
                     jsonObject.put("srcSysId", model.getSrcSysId());
                } 
                if (model.getDesSysId() != null) {
                     jsonObject.put("desSysId", model.getDesSysId());
                } 
                jsonObject.put("procStatus", model.getProcStatus());
                jsonObject.put("procResult", model.getProcResult());
                if (model.getProcStartTime() != null) {
                      jsonObject.put("procStartTime", DateUtils.getDate(model.getProcStartTime()));
		      jsonObject.put("procStartTime_date", DateUtils.getDate(model.getProcStartTime()));
		      jsonObject.put("procStartTime_datetime", DateUtils.getDateTime(model.getProcStartTime()));
                }
                if (model.getProcCompTime() != null) {
                      jsonObject.put("procCompTime", DateUtils.getDate(model.getProcCompTime()));
		      jsonObject.put("procCompTime_date", DateUtils.getDate(model.getProcCompTime()));
		      jsonObject.put("procCompTime_datetime", DateUtils.getDateTime(model.getProcCompTime()));
                }
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<ProcessInsMapping> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ProcessInsMapping model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ProcessInsMapping> arrayToList(JSONArray array) {
		java.util.List<ProcessInsMapping> list = new java.util.ArrayList<ProcessInsMapping>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ProcessInsMapping model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private ProcessInsMappingJsonFactory() {

	}

}
