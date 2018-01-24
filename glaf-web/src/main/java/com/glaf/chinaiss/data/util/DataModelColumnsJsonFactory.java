package com.glaf.chinaiss.data.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.chinaiss.data.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class DataModelColumnsJsonFactory {

	public static DataModelColumns jsonToObject(JSONObject jsonObject) {
            DataModelColumns model = new DataModelColumns();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("columnName")) {
			model.setColumnName(jsonObject.getString("columnName"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("tableName")) {
			model.setTableName(jsonObject.getString("tableName"));
		}
		if (jsonObject.containsKey("description")) {
			model.setDescription(jsonObject.getString("description"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("length")) {
			model.setLength(jsonObject.getInteger("length"));
		}
		if (jsonObject.containsKey("topId")) {
			model.setTopId(jsonObject.getString("topId"));
		}
		if (jsonObject.containsKey("parentId")) {
			model.setParentId(jsonObject.getString("parentId"));
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
		if (jsonObject.containsKey("listNo")) {
			model.setListNo(jsonObject.getInteger("listNo"));
		}

            return model;
	}

	public static JSONObject toJsonObject(DataModelColumns model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getColumnName() != null) {
			jsonObject.put("columnName", model.getColumnName());
		} 
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		} 
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		} 
		if (model.getDescription() != null) {
			jsonObject.put("description", model.getDescription());
		} 
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		} 
        jsonObject.put("length", model.getLength());
		if (model.getTopId() != null) {
			jsonObject.put("topId", model.getTopId());
		} 
		if (model.getParentId() != null) {
			jsonObject.put("parentId", model.getParentId());
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
        jsonObject.put("listNo", model.getListNo());
		return jsonObject;
	}


	public static ObjectNode toObjectNode(DataModelColumns model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getColumnName() != null) {
                     jsonObject.put("columnName", model.getColumnName());
                } 
                if (model.getName() != null) {
                     jsonObject.put("name", model.getName());
                } 
                if (model.getTableName() != null) {
                     jsonObject.put("tableName", model.getTableName());
                } 
                if (model.getDescription() != null) {
                     jsonObject.put("description", model.getDescription());
                } 
                if (model.getType() != null) {
                     jsonObject.put("type", model.getType());
                } 
                jsonObject.put("length", model.getLength());
                if (model.getTopId() != null) {
                     jsonObject.put("topId", model.getTopId());
                } 
                if (model.getParentId() != null) {
                     jsonObject.put("parentId", model.getParentId());
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
                jsonObject.put("listNo", model.getListNo());
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<DataModelColumns> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DataModelColumns model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DataModelColumns> arrayToList(JSONArray array) {
		java.util.List<DataModelColumns> list = new java.util.ArrayList<DataModelColumns>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DataModelColumns model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private DataModelColumnsJsonFactory() {

	}

}
