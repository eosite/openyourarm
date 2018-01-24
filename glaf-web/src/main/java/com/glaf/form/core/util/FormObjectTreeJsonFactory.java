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
public class FormObjectTreeJsonFactory {

	public static FormObjectTree jsonToObject(JSONObject jsonObject) {
            FormObjectTree model = new FormObjectTree();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("desc")) {
			model.setDesc(jsonObject.getString("desc"));
		}
		if (jsonObject.containsKey("topId")) {
			model.setTopId(jsonObject.getString("topId"));
		}
		if (jsonObject.containsKey("treeId")) {
			model.setTreeId(jsonObject.getString("treeId"));
		}
		if (jsonObject.containsKey("level")) {
			model.setLevel(jsonObject.getInteger("level"));
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

	public static JSONObject toJsonObject(FormObjectTree model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		} 
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		} 
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		} 
		if (model.getTopId() != null) {
			jsonObject.put("topId", model.getTopId());
		} 
		if (model.getTreeId() != null) {
			jsonObject.put("treeId", model.getTreeId());
		} 
        jsonObject.put("level", model.getLevel());
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


	public static ObjectNode toObjectNode(FormObjectTree model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getName() != null) {
                     jsonObject.put("name", model.getName());
                } 
                if (model.getCode() != null) {
                     jsonObject.put("code", model.getCode());
                } 
                if (model.getDesc() != null) {
                     jsonObject.put("desc", model.getDesc());
                } 
                if (model.getTopId() != null) {
                     jsonObject.put("topId", model.getTopId());
                } 
                if (model.getTreeId() != null) {
                     jsonObject.put("treeId", model.getTreeId());
                } 
                jsonObject.put("level", model.getLevel());
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

	
	public static JSONArray listToArray(java.util.List<FormObjectTree> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormObjectTree model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FormObjectTree> arrayToList(JSONArray array) {
		java.util.List<FormObjectTree> list = new java.util.ArrayList<FormObjectTree>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormObjectTree model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private FormObjectTreeJsonFactory() {

	}

}
