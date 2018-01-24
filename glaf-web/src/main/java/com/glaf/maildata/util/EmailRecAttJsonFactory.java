package com.glaf.maildata.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.maildata.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class EmailRecAttJsonFactory {

	public static EmailRecAtt jsonToObject(JSONObject jsonObject) {
            EmailRecAtt model = new EmailRecAtt();
            if (jsonObject.containsKey("fileId")) {
		    model.setFileId(jsonObject.getString("fileId"));
		}
		if (jsonObject.containsKey("topId")) {
			model.setTopId(jsonObject.getString("topId"));
		}
		if (jsonObject.containsKey("fileName")) {
			model.setFileName(jsonObject.getString("fileName"));
		}
		if (jsonObject.containsKey("cTime")) {
			model.setCTime(jsonObject.getDate("cTime"));
		}
		if (jsonObject.containsKey("fileSize")) {
			model.setFileSize(jsonObject.getInteger("fileSize"));
		}

            return model;
	}

	public static JSONObject toJsonObject(EmailRecAtt model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("fileId", model.getFileId());
		jsonObject.put("_fileId_", model.getFileId());
		if (model.getTopId() != null) {
			jsonObject.put("topId", model.getTopId());
		} 
		if (model.getFileName() != null) {
			jsonObject.put("fileName", model.getFileName());
		} 
		if (model.getFileContent() != null) {
			jsonObject.put("fileContent", model.getFileContent());
		} 
                if (model.getCTime() != null) {
                      jsonObject.put("cTime", DateUtils.getDate(model.getCTime()));
		      jsonObject.put("cTime_date", DateUtils.getDate(model.getCTime()));
		      jsonObject.put("cTime_datetime", DateUtils.getDateTime(model.getCTime()));
                }
        jsonObject.put("fileSize", model.getFileSize());
		return jsonObject;
	}


	public static ObjectNode toObjectNode(EmailRecAtt model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("fileId", model.getFileId());
		jsonObject.put("_fileId_", model.getFileId());
                if (model.getTopId() != null) {
                     jsonObject.put("topId", model.getTopId());
                } 
                if (model.getFileName() != null) {
                     jsonObject.put("fileName", model.getFileName());
                } 
                if (model.getFileContent() != null) {
                     jsonObject.put("fileContent", model.getFileContent());
                } 
                if (model.getCTime() != null) {
                      jsonObject.put("cTime", DateUtils.getDate(model.getCTime()));
		      jsonObject.put("cTime_date", DateUtils.getDate(model.getCTime()));
		      jsonObject.put("cTime_datetime", DateUtils.getDateTime(model.getCTime()));
                }
                jsonObject.put("fileSize", model.getFileSize());
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<EmailRecAtt> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (EmailRecAtt model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<EmailRecAtt> arrayToList(JSONArray array) {
		java.util.List<EmailRecAtt> list = new java.util.ArrayList<EmailRecAtt>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			EmailRecAtt model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private EmailRecAttJsonFactory() {

	}

}
