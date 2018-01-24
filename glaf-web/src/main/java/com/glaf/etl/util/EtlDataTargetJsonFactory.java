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
public class EtlDataTargetJsonFactory {

	public static EtlDataTarget jsonToObject(JSONObject jsonObject) {
            EtlDataTarget model = new EtlDataTarget();
            if (jsonObject.containsKey("targetId_")) {
		    model.setTargetId_(jsonObject.getString("targetId_"));
		}
		if (jsonObject.containsKey("targetName_")) {
			model.setTargetName_(jsonObject.getString("targetName_"));
		}
		if (jsonObject.containsKey("databaseId_")) {
			model.setDatabaseId_(jsonObject.getLong("databaseId_"));
		}
		if (jsonObject.containsKey("databaseName_")) {
			model.setDatabaseName_(jsonObject.getString("databaseName_"));
		}
		if (jsonObject.containsKey("tableName_")) {
			model.setTableName_(jsonObject.getString("tableName_"));
		}
		if (jsonObject.containsKey("createBy_")) {
			model.setCreateBy_(jsonObject.getString("createBy_"));
		}
		if (jsonObject.containsKey("createTime_")) {
			model.setCreateTime_(jsonObject.getDate("createTime_"));
		}
		if (jsonObject.containsKey("updateBy_")) {
			model.setUpdateBy_(jsonObject.getString("updateBy_"));
		}
		if (jsonObject.containsKey("updateTime_")) {
			model.setUpdateTime_(jsonObject.getDate("updateTime_"));
		}

            return model;
	}

	public static JSONObject toJsonObject(EtlDataTarget model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("targetId_", model.getTargetId_());
		jsonObject.put("_targetId__", model.getTargetId_());
		if (model.getTargetName_() != null) {
			jsonObject.put("targetName_", model.getTargetName_());
		} 
		if (model.getColumns_() != null) {
			jsonObject.put("columns_", model.getColumns_());
		} 
		if (model.getDatabaseId_() != null) {
			jsonObject.put("databaseId_", model.getDatabaseId_());
		} 
		if (model.getDatabaseName_() != null) {
			jsonObject.put("databaseName_", model.getDatabaseName_());
		} 
		if (model.getTableName_() != null) {
			jsonObject.put("tableName_", model.getTableName_());
		} 
		if (model.getCreateBy_() != null) {
			jsonObject.put("createBy_", model.getCreateBy_());
		} 
                if (model.getCreateTime_() != null) {
                      jsonObject.put("createTime_", DateUtils.getDate(model.getCreateTime_()));
		      jsonObject.put("createTime__date", DateUtils.getDate(model.getCreateTime_()));
		      jsonObject.put("createTime__datetime", DateUtils.getDateTime(model.getCreateTime_()));
                }
		if (model.getUpdateBy_() != null) {
			jsonObject.put("updateBy_", model.getUpdateBy_());
		} 
                if (model.getUpdateTime_() != null) {
                      jsonObject.put("updateTime_", DateUtils.getDate(model.getUpdateTime_()));
		      jsonObject.put("updateTime__date", DateUtils.getDate(model.getUpdateTime_()));
		      jsonObject.put("updateTime__datetime", DateUtils.getDateTime(model.getUpdateTime_()));
                }
		return jsonObject;
	}


	public static ObjectNode toObjectNode(EtlDataTarget model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("targetId_", model.getTargetId_());
		jsonObject.put("_targetId__", model.getTargetId_());
                if (model.getTargetName_() != null) {
                     jsonObject.put("targetName_", model.getTargetName_());
                } 
                if (model.getColumns_() != null) {
                     jsonObject.put("columns_", model.getColumns_());
                } 
                if (model.getDatabaseId_() != null) {
                     jsonObject.put("databaseId_", model.getDatabaseId_());
                } 
                if (model.getDatabaseName_() != null) {
                     jsonObject.put("databaseName_", model.getDatabaseName_());
                } 
                if (model.getTableName_() != null) {
                     jsonObject.put("tableName_", model.getTableName_());
                } 
                if (model.getCreateBy_() != null) {
                     jsonObject.put("createBy_", model.getCreateBy_());
                } 
                if (model.getCreateTime_() != null) {
                      jsonObject.put("createTime_", DateUtils.getDate(model.getCreateTime_()));
		      jsonObject.put("createTime__date", DateUtils.getDate(model.getCreateTime_()));
		      jsonObject.put("createTime__datetime", DateUtils.getDateTime(model.getCreateTime_()));
                }
                if (model.getUpdateBy_() != null) {
                     jsonObject.put("updateBy_", model.getUpdateBy_());
                } 
                if (model.getUpdateTime_() != null) {
                      jsonObject.put("updateTime_", DateUtils.getDate(model.getUpdateTime_()));
		      jsonObject.put("updateTime__date", DateUtils.getDate(model.getUpdateTime_()));
		      jsonObject.put("updateTime__datetime", DateUtils.getDateTime(model.getUpdateTime_()));
                }
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<EtlDataTarget> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (EtlDataTarget model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<EtlDataTarget> arrayToList(JSONArray array) {
		java.util.List<EtlDataTarget> list = new java.util.ArrayList<EtlDataTarget>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			EtlDataTarget model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private EtlDataTargetJsonFactory() {

	}

}
