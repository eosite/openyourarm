package com.glaf.form.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.domain.UserSqliteUpload;


/**
 * 
 * JSON工厂类
 *
 */
public class UserSqliteUploadJsonFactory {

	public static UserSqliteUpload jsonToObject(JSONObject jsonObject) {
            UserSqliteUpload model = new UserSqliteUpload();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("userId")) {
			model.setUserId(jsonObject.getString("userId"));
		}
		if (jsonObject.containsKey("fileName")) {
			model.setFileName(jsonObject.getString("fileName"));
		}
		if (jsonObject.containsKey("filePath")) {
			model.setFilePath(jsonObject.getString("filePath"));
		}
		if (jsonObject.containsKey("logfilePath")) {
			model.setLogfilePath(jsonObject.getString("logfilePath"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("uploadDate")) {
			model.setUploadDate(jsonObject.getDate("uploadDate"));
		}

            return model;
	}

	public static JSONObject toJsonObject(UserSqliteUpload model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getUserId() != null) {
			jsonObject.put("userId", model.getUserId());
		} 
		if (model.getFileName() != null) {
			jsonObject.put("fileName", model.getFileName());
		} 
		if (model.getFilePath() != null) {
			jsonObject.put("filePath", model.getFilePath());
		} 
		if (model.getLogfilePath() != null) {
			jsonObject.put("logfilePath", model.getLogfilePath());
		} 
        jsonObject.put("status", model.getStatus());
                if (model.getUploadDate() != null) {
                      jsonObject.put("uploadDate", DateUtils.getDate(model.getUploadDate()));
		      jsonObject.put("uploadDate_date", DateUtils.getDate(model.getUploadDate()));
		      jsonObject.put("uploadDate_datetime", DateUtils.getDateTime(model.getUploadDate()));
                }
		return jsonObject;
	}


	public static ObjectNode toObjectNode(UserSqliteUpload model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getUserId() != null) {
                     jsonObject.put("userId", model.getUserId());
                } 
                if (model.getFileName() != null) {
                     jsonObject.put("fileName", model.getFileName());
                } 
                if (model.getFilePath() != null) {
                     jsonObject.put("filePath", model.getFilePath());
                } 
                if (model.getLogfilePath() != null) {
                     jsonObject.put("logfilePath", model.getLogfilePath());
                } 
                jsonObject.put("status", model.getStatus());
                if (model.getUploadDate() != null) {
                      jsonObject.put("uploadDate", DateUtils.getDate(model.getUploadDate()));
		      jsonObject.put("uploadDate_date", DateUtils.getDate(model.getUploadDate()));
		      jsonObject.put("uploadDate_datetime", DateUtils.getDateTime(model.getUploadDate()));
                }
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<UserSqliteUpload> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (UserSqliteUpload model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<UserSqliteUpload> arrayToList(JSONArray array) {
		java.util.List<UserSqliteUpload> list = new java.util.ArrayList<UserSqliteUpload>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			UserSqliteUpload model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private UserSqliteUploadJsonFactory() {

	}

}
