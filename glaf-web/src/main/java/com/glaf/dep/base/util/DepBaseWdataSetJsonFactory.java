package com.glaf.dep.base.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.dep.base.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class DepBaseWdataSetJsonFactory {

	public static DepBaseWdataSet jsonToObject(JSONObject jsonObject) {
            DepBaseWdataSet model = new DepBaseWdataSet();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("dataSetCode")) {
			model.setDataSetCode(jsonObject.getString("dataSetCode"));
		}
		if (jsonObject.containsKey("dataSetName")) {
			model.setDataSetName(jsonObject.getString("dataSetName"));
		}
		if (jsonObject.containsKey("dataSetDesc")) {
			model.setDataSetDesc(jsonObject.getString("dataSetDesc"));
		}
		if (jsonObject.containsKey("ruleJson")) {
			model.setRuleJson(jsonObject.getString("ruleJson"));
		}
		if (jsonObject.containsKey("tableName")) {
			model.setTableName(jsonObject.getString("tableName"));
		}
		if (jsonObject.containsKey("dataTableName")) {
			model.setDataTableName(jsonObject.getString("dataTableName"));
		}
		if (jsonObject.containsKey("wtype")) {
			model.setWtype(jsonObject.getString("wtype"));
		}
		if (jsonObject.containsKey("ver")) {
			model.setVer(jsonObject.getInteger("ver"));
		}
		if (jsonObject.containsKey("creator")) {
			model.setCreator(jsonObject.getString("creator"));
		}
		if (jsonObject.containsKey("createDatetime")) {
			model.setCreateDatetime(jsonObject.getDate("createDatetime"));
		}
		if (jsonObject.containsKey("modifier")) {
			model.setModifier(jsonObject.getString("modifier"));
		}
		if (jsonObject.containsKey("modifyDatetime")) {
			model.setModifyDatetime(jsonObject.getDate("modifyDatetime"));
		}
		if (jsonObject.containsKey("delFlag")) {
			model.setDelFlag(jsonObject.getString("delFlag"));
		}

            return model;
	}

	public static JSONObject toJsonObject(DepBaseWdataSet model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getDataSetCode() != null) {
			jsonObject.put("dataSetCode", model.getDataSetCode());
		} 
		if (model.getDataSetName() != null) {
			jsonObject.put("dataSetName", model.getDataSetName());
		} 
		if (model.getDataSetDesc() != null) {
			jsonObject.put("dataSetDesc", model.getDataSetDesc());
		} 
		if (model.getRuleJson() != null) {
			jsonObject.put("ruleJson", model.getRuleJson());
		} 
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		} 
		if (model.getDataTableName() != null) {
			jsonObject.put("dataTableName", model.getDataTableName());
		} 
		if (model.getWtype() != null) {
			jsonObject.put("wtype", model.getWtype());
		} 
        jsonObject.put("ver", model.getVer());
		if (model.getCreator() != null) {
			jsonObject.put("creator", model.getCreator());
		} 
                if (model.getCreateDatetime() != null) {
                      jsonObject.put("createDatetime", DateUtils.getDate(model.getCreateDatetime()));
		      jsonObject.put("createDatetime_date", DateUtils.getDate(model.getCreateDatetime()));
		      jsonObject.put("createDatetime_datetime", DateUtils.getDateTime(model.getCreateDatetime()));
                }
		if (model.getModifier() != null) {
			jsonObject.put("modifier", model.getModifier());
		} 
                if (model.getModifyDatetime() != null) {
                      jsonObject.put("modifyDatetime", DateUtils.getDate(model.getModifyDatetime()));
		      jsonObject.put("modifyDatetime_date", DateUtils.getDate(model.getModifyDatetime()));
		      jsonObject.put("modifyDatetime_datetime", DateUtils.getDateTime(model.getModifyDatetime()));
                }
		if (model.getDelFlag() != null) {
			jsonObject.put("delFlag", model.getDelFlag());
		} 
		return jsonObject;
	}


	public static ObjectNode toObjectNode(DepBaseWdataSet model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getDataSetCode() != null) {
                     jsonObject.put("dataSetCode", model.getDataSetCode());
                } 
                if (model.getDataSetName() != null) {
                     jsonObject.put("dataSetName", model.getDataSetName());
                } 
                if (model.getDataSetDesc() != null) {
                     jsonObject.put("dataSetDesc", model.getDataSetDesc());
                } 
                if (model.getRuleJson() != null) {
                     jsonObject.put("ruleJson", model.getRuleJson());
                } 
                if (model.getTableName() != null) {
                     jsonObject.put("tableName", model.getTableName());
                } 
                if (model.getDataTableName() != null) {
                     jsonObject.put("dataTableName", model.getDataTableName());
                } 
                if (model.getWtype() != null) {
                     jsonObject.put("wtype", model.getWtype());
                } 
                jsonObject.put("ver", model.getVer());
                if (model.getCreator() != null) {
                     jsonObject.put("creator", model.getCreator());
                } 
                if (model.getCreateDatetime() != null) {
                      jsonObject.put("createDatetime", DateUtils.getDate(model.getCreateDatetime()));
		      jsonObject.put("createDatetime_date", DateUtils.getDate(model.getCreateDatetime()));
		      jsonObject.put("createDatetime_datetime", DateUtils.getDateTime(model.getCreateDatetime()));
                }
                if (model.getModifier() != null) {
                     jsonObject.put("modifier", model.getModifier());
                } 
                if (model.getModifyDatetime() != null) {
                      jsonObject.put("modifyDatetime", DateUtils.getDate(model.getModifyDatetime()));
		      jsonObject.put("modifyDatetime_date", DateUtils.getDate(model.getModifyDatetime()));
		      jsonObject.put("modifyDatetime_datetime", DateUtils.getDateTime(model.getModifyDatetime()));
                }
                if (model.getDelFlag() != null) {
                     jsonObject.put("delFlag", model.getDelFlag());
                } 
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<DepBaseWdataSet> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DepBaseWdataSet model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DepBaseWdataSet> arrayToList(JSONArray array) {
		java.util.List<DepBaseWdataSet> list = new java.util.ArrayList<DepBaseWdataSet>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DepBaseWdataSet model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private DepBaseWdataSetJsonFactory() {

	}

}
