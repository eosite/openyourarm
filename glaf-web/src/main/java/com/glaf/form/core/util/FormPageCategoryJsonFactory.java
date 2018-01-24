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
public class FormPageCategoryJsonFactory {

	public static FormPageCategory jsonToObject(JSONObject jsonObject) {
            FormPageCategory model = new FormPageCategory();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getInteger("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}
		if (jsonObject.containsKey("sortNo")) {
			model.setSortNo(jsonObject.getInteger("sortNo"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("permission")) {
			model.setPermission(jsonObject.getString("permission"));
		}
		if (jsonObject.containsKey("ext1")) {
			model.setExt1(jsonObject.getString("ext1"));
		}
		if (jsonObject.containsKey("ext2")) {
			model.setExt2(jsonObject.getString("ext2"));
		}
		if (jsonObject.containsKey("ext3")) {
			model.setExt3(jsonObject.getString("ext3"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}

            return model;
	}

	public static JSONObject toJsonObject(FormPageCategory model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		} 
        jsonObject.put("deleteFlag", model.getDeleteFlag());
        jsonObject.put("sortNo", model.getSortNo());
        jsonObject.put("locked", model.getLocked());
		if (model.getPermission() != null) {
			jsonObject.put("permission", model.getPermission());
		} 
		if (model.getExt1() != null) {
			jsonObject.put("ext1", model.getExt1());
		} 
		if (model.getExt2() != null) {
			jsonObject.put("ext2", model.getExt2());
		} 
		if (model.getExt3() != null) {
			jsonObject.put("ext3", model.getExt3());
		} 
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		} 
                if (model.getCreateDate() != null) {
                      jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
                }
		return jsonObject;
	}


	public static ObjectNode toObjectNode(FormPageCategory model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getName() != null) {
                     jsonObject.put("name", model.getName());
                } 
                jsonObject.put("deleteFlag", model.getDeleteFlag());
                jsonObject.put("sortNo", model.getSortNo());
                jsonObject.put("locked", model.getLocked());
                if (model.getPermission() != null) {
                     jsonObject.put("permission", model.getPermission());
                } 
                if (model.getExt1() != null) {
                     jsonObject.put("ext1", model.getExt1());
                } 
                if (model.getExt2() != null) {
                     jsonObject.put("ext2", model.getExt2());
                } 
                if (model.getExt3() != null) {
                     jsonObject.put("ext3", model.getExt3());
                } 
                if (model.getCreateBy() != null) {
                     jsonObject.put("createBy", model.getCreateBy());
                } 
                if (model.getCreateDate() != null) {
                      jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
                }
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<FormPageCategory> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormPageCategory model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FormPageCategory> arrayToList(JSONArray array) {
		java.util.List<FormPageCategory> list = new java.util.ArrayList<FormPageCategory>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormPageCategory model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private FormPageCategoryJsonFactory() {

	}

}
