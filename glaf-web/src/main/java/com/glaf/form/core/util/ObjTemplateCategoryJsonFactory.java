package com.glaf.form.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.domain.ObjTemplateCategory;


/**
 * 
 * JSON工厂类
 *
 */
public class ObjTemplateCategoryJsonFactory {

	public static ObjTemplateCategory jsonToObject(JSONObject jsonObject) {
            ObjTemplateCategory model = new ObjTemplateCategory();
            if (jsonObject.containsKey("categoryId")) {
		    model.setCategoryId(jsonObject.getLong("categoryId"));
		}
		if (jsonObject.containsKey("templateId")) {
			model.setTemplateId(jsonObject.getLong("templateId"));
		}
		if (jsonObject.containsKey("creator")) {
			model.setCreator(jsonObject.getString("creator"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}
		if (jsonObject.containsKey("modifier")) {
			model.setModifier(jsonObject.getString("modifier"));
		}
		if (jsonObject.containsKey("updateTime")) {
			model.setUpdateTime(jsonObject.getDate("updateTime"));
		}

            return model;
	}

	public static JSONObject toJsonObject(ObjTemplateCategory model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("categoryId", model.getCategoryId());
		jsonObject.put("_categoryId_", model.getCategoryId());
        jsonObject.put("templateId", model.getTemplateId());
		if (model.getCreator() != null) {
			jsonObject.put("creator", model.getCreator());
		} 
                if (model.getCreateTime() != null) {
                      jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
		      jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
		      jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
                }
		if (model.getModifier() != null) {
			jsonObject.put("modifier", model.getModifier());
		} 
                if (model.getUpdateTime() != null) {
                      jsonObject.put("updateTime", DateUtils.getDate(model.getUpdateTime()));
		      jsonObject.put("updateTime_date", DateUtils.getDate(model.getUpdateTime()));
		      jsonObject.put("updateTime_datetime", DateUtils.getDateTime(model.getUpdateTime()));
                }
		return jsonObject;
	}


	public static ObjectNode toObjectNode(ObjTemplateCategory model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("categoryId", model.getCategoryId());
		jsonObject.put("_categoryId_", model.getCategoryId());
                jsonObject.put("templateId", model.getTemplateId());
                if (model.getCreator() != null) {
                     jsonObject.put("creator", model.getCreator());
                } 
                if (model.getCreateTime() != null) {
                      jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
		      jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
		      jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
                }
                if (model.getModifier() != null) {
                     jsonObject.put("modifier", model.getModifier());
                } 
                if (model.getUpdateTime() != null) {
                      jsonObject.put("updateTime", DateUtils.getDate(model.getUpdateTime()));
		      jsonObject.put("updateTime_date", DateUtils.getDate(model.getUpdateTime()));
		      jsonObject.put("updateTime_datetime", DateUtils.getDateTime(model.getUpdateTime()));
                }
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<ObjTemplateCategory> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ObjTemplateCategory model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ObjTemplateCategory> arrayToList(JSONArray array) {
		java.util.List<ObjTemplateCategory> list = new java.util.ArrayList<ObjTemplateCategory>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ObjTemplateCategory model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private ObjTemplateCategoryJsonFactory() {

	}

}
