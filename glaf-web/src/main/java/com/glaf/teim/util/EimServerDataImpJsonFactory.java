package com.glaf.teim.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.teim.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class EimServerDataImpJsonFactory {

	public static EimServerDataImp jsonToObject(JSONObject jsonObject) {
            EimServerDataImp model = new EimServerDataImp();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("appId")) {
			model.setAppId(jsonObject.getString("appId"));
		}
		if (jsonObject.containsKey("tmpId")) {
			model.setTmpId(jsonObject.getString("tmpId"));
		}
		if (jsonObject.containsKey("emptyTable")) {
			model.setEmptyTable(jsonObject.getInteger("emptyTable"));
		}
		if (jsonObject.containsKey("preSql")) {
			model.setPreSql(jsonObject.getString("preSql"));
		}
		if (jsonObject.containsKey("incrementFlag")) {
			model.setIncrementFlag(jsonObject.getInteger("incrementFlag"));
		}
		if (jsonObject.containsKey("params")) {
			model.setParams(jsonObject.getString("params"));
		}
		if (jsonObject.containsKey("targetDatabase")) {
			model.setTargetDatabase(jsonObject.getString("targetDatabase"));
		}
		if (jsonObject.containsKey("targetTable")) {
			model.setTargetTable(jsonObject.getString("targetTable"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}
		if (jsonObject.containsKey("updateTime")) {
			model.setUpdateTime(jsonObject.getDate("updateTime"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}

            return model;
	}

	public static JSONObject toJsonObject(EimServerDataImp model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		} 
		if (model.getAppId() != null) {
			jsonObject.put("appId", model.getAppId());
		} 
		if (model.getTmpId() != null) {
			jsonObject.put("tmpId", model.getTmpId());
		} 
        jsonObject.put("emptyTable", model.getEmptyTable());
		if (model.getPreSql() != null) {
			jsonObject.put("preSql", model.getPreSql());
		} 
        jsonObject.put("incrementFlag", model.getIncrementFlag());
		if (model.getParams() != null) {
			jsonObject.put("params", model.getParams());
		} 
		if (model.getTargetDatabase() != null) {
			jsonObject.put("targetDatabase", model.getTargetDatabase());
		} 
		if (model.getTargetTable() != null) {
			jsonObject.put("targetTable", model.getTargetTable());
		} 
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		} 
                if (model.getCreateTime() != null) {
                      jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
		      jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
		      jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
                }
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		} 
                if (model.getUpdateTime() != null) {
                      jsonObject.put("updateTime", DateUtils.getDate(model.getUpdateTime()));
		      jsonObject.put("updateTime_date", DateUtils.getDate(model.getUpdateTime()));
		      jsonObject.put("updateTime_datetime", DateUtils.getDateTime(model.getUpdateTime()));
                }
        jsonObject.put("deleteFlag", model.getDeleteFlag());
		return jsonObject;
	}


	public static ObjectNode toObjectNode(EimServerDataImp model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getName() != null) {
                     jsonObject.put("name", model.getName());
                } 
                if (model.getAppId() != null) {
                     jsonObject.put("appId", model.getAppId());
                } 
                if (model.getTmpId() != null) {
                     jsonObject.put("tmpId", model.getTmpId());
                } 
                jsonObject.put("emptyTable", model.getEmptyTable());
                if (model.getPreSql() != null) {
                     jsonObject.put("preSql", model.getPreSql());
                } 
                jsonObject.put("incrementFlag", model.getIncrementFlag());
                if (model.getParams() != null) {
                     jsonObject.put("params", model.getParams());
                } 
                if (model.getTargetDatabase() != null) {
                     jsonObject.put("targetDatabase", model.getTargetDatabase());
                } 
                if (model.getTargetTable() != null) {
                     jsonObject.put("targetTable", model.getTargetTable());
                } 
                if (model.getCreateBy() != null) {
                     jsonObject.put("createBy", model.getCreateBy());
                } 
                if (model.getCreateTime() != null) {
                      jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
		      jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
		      jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
                }
                if (model.getUpdateBy() != null) {
                     jsonObject.put("updateBy", model.getUpdateBy());
                } 
                if (model.getUpdateTime() != null) {
                      jsonObject.put("updateTime", DateUtils.getDate(model.getUpdateTime()));
		      jsonObject.put("updateTime_date", DateUtils.getDate(model.getUpdateTime()));
		      jsonObject.put("updateTime_datetime", DateUtils.getDateTime(model.getUpdateTime()));
                }
                jsonObject.put("deleteFlag", model.getDeleteFlag());
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<EimServerDataImp> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (EimServerDataImp model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<EimServerDataImp> arrayToList(JSONArray array) {
		java.util.List<EimServerDataImp> list = new java.util.ArrayList<EimServerDataImp>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			EimServerDataImp model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private EimServerDataImpJsonFactory() {

	}

}
