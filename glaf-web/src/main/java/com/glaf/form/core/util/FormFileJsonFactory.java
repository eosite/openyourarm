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
public class FormFileJsonFactory {

	public static FormFile jsonToObject(JSONObject jsonObject) {
            FormFile model = new FormFile();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("parent")) {
			model.setParent(jsonObject.getString("parent"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("fieldType")) {
			model.setFieldType(jsonObject.getString("fieldType"));
		}
		if (jsonObject.containsKey("fileName")) {
			model.setFileName(jsonObject.getString("fileName"));
		}
		if (jsonObject.containsKey("fileSize")) {
			model.setFileSize(jsonObject.getInteger("fileSize"));
		}
//		if (jsonObject.containsKey("fileContent")) {
//			model.setFileContent(jsonObject.getString("fileContent"));
//		}
		if (jsonObject.containsKey("saveServicePath")) {
			model.setSaveServicePath(jsonObject.getString("saveServicePath"));
		}
		if (jsonObject.containsKey("vision")) {
			model.setVision(jsonObject.getString("vision"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("error")) {
			model.setError(jsonObject.getString("error"));
		}
		if (jsonObject.containsKey("business")) {
			model.setBusiness(jsonObject.getString("business"));
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

	public static JSONObject toJsonObject(FormFile model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getParent() != null) {
			jsonObject.put("parent", model.getParent());
		} 
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		} 
		if (model.getFieldType() != null) {
			jsonObject.put("fieldType", model.getFieldType());
		} 
		if (model.getFileName() != null) {
			jsonObject.put("fileName", model.getFileName());
		} 
        jsonObject.put("fileSize", model.getFileSize());
//		if (model.getFileContent() != null) {
//			jsonObject.put("fileContent", model.getFileContent());
//		} 
		if (model.getSaveServicePath() != null) {
			jsonObject.put("saveServicePath", model.getSaveServicePath());
		} 
        jsonObject.put("vision", model.getVision());
        jsonObject.put("status", model.getStatus());
		if (model.getError() != null) {
			jsonObject.put("error", model.getError());
		} 
		if (model.getBusiness() != null) {
			jsonObject.put("business", model.getBusiness());
		} 
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


	public static ObjectNode toObjectNode(FormFile model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getParent() != null) {
                     jsonObject.put("parent", model.getParent());
                } 
                if (model.getType() != null) {
                     jsonObject.put("type", model.getType());
                } 
                if (model.getFieldType() != null) {
                     jsonObject.put("fieldType", model.getFieldType());
                } 
                if (model.getFileName() != null) {
                     jsonObject.put("fileName", model.getFileName());
                } 
                jsonObject.put("fileSize", model.getFileSize());
//                if (model.getFileContent() != null) {
//                     jsonObject.put("fileContent", model.getFileContent());
//                } 
                if (model.getSaveServicePath() != null) {
                     jsonObject.put("saveServicePath", model.getSaveServicePath());
                } 
                jsonObject.put("vision", model.getVision());
                jsonObject.put("status", model.getStatus());
                if (model.getError() != null) {
                     jsonObject.put("error", model.getError());
                } 
                if (model.getBusiness() != null) {
                     jsonObject.put("business", model.getBusiness());
                } 
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

	
	public static JSONArray listToArray(java.util.List<FormFile> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormFile model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FormFile> arrayToList(JSONArray array) {
		java.util.List<FormFile> list = new java.util.ArrayList<FormFile>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormFile model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private FormFileJsonFactory() {

	}

}
