package com.glaf.etl.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.etl.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class EtlTransferTaskInstJsonFactory {

	public static EtlTransferTaskInst jsonToObject(JSONObject jsonObject) {
            EtlTransferTaskInst model = new EtlTransferTaskInst();
            if (jsonObject.containsKey("id_")) {
		    model.setId_(jsonObject.getString("id_"));
		}
		if (jsonObject.containsKey("taskId_")) {
			model.setTaskId_(jsonObject.getString("taskId_"));
		}
		if (jsonObject.containsKey("startTime_")) {
			model.setStartTime_(jsonObject.getDate("startTime_"));
		}
		if (jsonObject.containsKey("endTime_")) {
			model.setEndTime_(jsonObject.getDate("endTime_"));
		}
		if (jsonObject.containsKey("succeed_")) {
			model.setSucceed_(jsonObject.getInteger("succeed_"));
		}
		if (jsonObject.containsKey("srcSuccessRows_")) {
			model.setSrcSuccessRows_(jsonObject.getLong("srcSuccessRows_"));
		}
		if (jsonObject.containsKey("srcFailedRows_")) {
			model.setSrcFailedRows_(jsonObject.getLong("srcFailedRows_"));
		}
		if (jsonObject.containsKey("targetSuccessRows_")) {
			model.setTargetSuccessRows_(jsonObject.getLong("targetSuccessRows_"));
		}
		if (jsonObject.containsKey("targetFailedRows_")) {
			model.setTargetFailedRows_(jsonObject.getLong("targetFailedRows_"));
		}

            return model;
	}

	public static JSONObject toJsonObject(EtlTransferTaskInst model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id_", model.getId_());
		jsonObject.put("_id__", model.getId_());
		if (model.getTaskId_() != null) {
			jsonObject.put("taskId_", model.getTaskId_());
		} 
                if (model.getStartTime_() != null) {
                      jsonObject.put("startTime_", DateUtils.getDate(model.getStartTime_()));
		      jsonObject.put("startTime__date", DateUtils.getDate(model.getStartTime_()));
		      jsonObject.put("startTime__datetime", DateUtils.getDateTime(model.getStartTime_()));
                }
                if (model.getEndTime_() != null) {
                      jsonObject.put("endTime_", DateUtils.getDate(model.getEndTime_()));
		      jsonObject.put("endTime__date", DateUtils.getDate(model.getEndTime_()));
		      jsonObject.put("endTime__datetime", DateUtils.getDateTime(model.getEndTime_()));
                }
        jsonObject.put("succeed_", model.getSucceed_());
        jsonObject.put("srcSuccessRows_", model.getSrcSuccessRows_());
        jsonObject.put("srcFailedRows_", model.getSrcFailedRows_());
        jsonObject.put("targetSuccessRows_", model.getTargetSuccessRows_());
        jsonObject.put("targetFailedRows_", model.getTargetFailedRows_());
		return jsonObject;
	}


	public static ObjectNode toObjectNode(EtlTransferTaskInst model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id_", model.getId_());
		jsonObject.put("_id__", model.getId_());
                if (model.getTaskId_() != null) {
                     jsonObject.put("taskId_", model.getTaskId_());
                } 
                if (model.getStartTime_() != null) {
                      jsonObject.put("startTime_", DateUtils.getDate(model.getStartTime_()));
		      jsonObject.put("startTime__date", DateUtils.getDate(model.getStartTime_()));
		      jsonObject.put("startTime__datetime", DateUtils.getDateTime(model.getStartTime_()));
                }
                if (model.getEndTime_() != null) {
                      jsonObject.put("endTime_", DateUtils.getDate(model.getEndTime_()));
		      jsonObject.put("endTime__date", DateUtils.getDate(model.getEndTime_()));
		      jsonObject.put("endTime__datetime", DateUtils.getDateTime(model.getEndTime_()));
                }
                jsonObject.put("succeed_", model.getSucceed_());
                jsonObject.put("srcSuccessRows_", model.getSrcSuccessRows_());
                jsonObject.put("srcFailedRows_", model.getSrcFailedRows_());
                jsonObject.put("targetSuccessRows_", model.getTargetSuccessRows_());
                jsonObject.put("targetFailedRows_", model.getTargetFailedRows_());
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<EtlTransferTaskInst> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (EtlTransferTaskInst model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<EtlTransferTaskInst> arrayToList(JSONArray array) {
		java.util.List<EtlTransferTaskInst> list = new java.util.ArrayList<EtlTransferTaskInst>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			EtlTransferTaskInst model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private EtlTransferTaskInstJsonFactory() {

	}

}
