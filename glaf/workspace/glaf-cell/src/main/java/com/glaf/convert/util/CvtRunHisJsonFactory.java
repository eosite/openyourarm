package com.glaf.convert.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.convert.domain.CvtRunHis;
import com.glaf.core.util.DateUtils;


/**
 * 
 * JSON工厂类
 *
 */
public class CvtRunHisJsonFactory {

	public static CvtRunHis jsonToObject(JSONObject jsonObject) {
            CvtRunHis model = new CvtRunHis();
            if (jsonObject.containsKey("taskCode")) {
		    model.setTaskCode(jsonObject.getString("taskCode"));
		}
		if (jsonObject.containsKey("taskName")) {
			model.setTaskName(jsonObject.getString("taskName"));
		}
		if (jsonObject.containsKey("runTime")) {
			model.setRunTime(jsonObject.getDate("runTime"));
		}
		if (jsonObject.containsKey("runFlag")) {
			model.setRunFlag(jsonObject.getInteger("runFlag"));
		}
		if (jsonObject.containsKey("succCount")) {
			model.setSuccCount(jsonObject.getInteger("succCount"));
		}
		if (jsonObject.containsKey("failCount")) {
			model.setFailCount(jsonObject.getInteger("failCount"));
		}

            return model;
	}

	public static JSONObject toJsonObject(CvtRunHis model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("taskCode", model.getTaskCode());
		jsonObject.put("_taskCode_", model.getTaskCode());
		if (model.getTaskName() != null) {
			jsonObject.put("taskName", model.getTaskName());
		} 
                if (model.getRunTime() != null) {
                      jsonObject.put("runTime", DateUtils.getDate(model.getRunTime()));
		      jsonObject.put("runTime_date", DateUtils.getDate(model.getRunTime()));
		      jsonObject.put("runTime_datetime", DateUtils.getDateTime(model.getRunTime()));
                }
        jsonObject.put("runFlag", model.getRunFlag());
        jsonObject.put("succCount", model.getSuccCount());
        jsonObject.put("failCount", model.getFailCount());
		return jsonObject;
	}


	public static ObjectNode toObjectNode(CvtRunHis model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("taskCode", model.getTaskCode());
		jsonObject.put("_taskCode_", model.getTaskCode());
                if (model.getTaskName() != null) {
                     jsonObject.put("taskName", model.getTaskName());
                } 
                if (model.getRunTime() != null) {
                      jsonObject.put("runTime", DateUtils.getDate(model.getRunTime()));
		      jsonObject.put("runTime_date", DateUtils.getDate(model.getRunTime()));
		      jsonObject.put("runTime_datetime", DateUtils.getDateTime(model.getRunTime()));
                }
                jsonObject.put("runFlag", model.getRunFlag());
                jsonObject.put("succCount", model.getSuccCount());
                jsonObject.put("failCount", model.getFailCount());

                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<CvtRunHis> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (CvtRunHis model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<CvtRunHis> arrayToList(JSONArray array) {
		java.util.List<CvtRunHis> list = new java.util.ArrayList<CvtRunHis>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			CvtRunHis model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private CvtRunHisJsonFactory() {

	}

}