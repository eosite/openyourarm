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
public class DepBaseWdataSetColumnJsonFactory {

	public static DepBaseWdataSetColumn jsonToObject(JSONObject jsonObject) {
            DepBaseWdataSetColumn model = new DepBaseWdataSetColumn();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("wdataSetId")) {
			model.setWdataSetId(jsonObject.getLong("wdataSetId"));
		}
		if (jsonObject.containsKey("columnName")) {
			model.setColumnName(jsonObject.getString("columnName"));
		}
		if (jsonObject.containsKey("dataColumnName")) {
			model.setDataColumnName(jsonObject.getString("dataColumnName"));
		}
		if (jsonObject.containsKey("defaultVal")) {
			model.setDefaultVal(jsonObject.getString("defaultVal"));
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

            return model;
	}

	public static JSONObject toJsonObject(DepBaseWdataSetColumn model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
        jsonObject.put("wdataSetId", model.getWdataSetId());
		if (model.getColumnName() != null) {
			jsonObject.put("columnName", model.getColumnName());
		} 
		if (model.getDataColumnName() != null) {
			jsonObject.put("dataColumnName", model.getDataColumnName());
		} 
		if (model.getDefaultVal() != null) {
			jsonObject.put("defaultVal", model.getDefaultVal());
		} 
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
		return jsonObject;
	}


	public static ObjectNode toObjectNode(DepBaseWdataSetColumn model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                jsonObject.put("wdataSetId", model.getWdataSetId());
                if (model.getColumnName() != null) {
                     jsonObject.put("columnName", model.getColumnName());
                } 
                if (model.getDataColumnName() != null) {
                     jsonObject.put("dataColumnName", model.getDataColumnName());
                } 
                if (model.getDefaultVal() != null) {
                     jsonObject.put("defaultVal", model.getDefaultVal());
                } 
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
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<DepBaseWdataSetColumn> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DepBaseWdataSetColumn model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DepBaseWdataSetColumn> arrayToList(JSONArray array) {
		java.util.List<DepBaseWdataSetColumn> list = new java.util.ArrayList<DepBaseWdataSetColumn>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DepBaseWdataSetColumn model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private DepBaseWdataSetColumnJsonFactory() {

	}

}
