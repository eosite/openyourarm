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
public class ObjectTemplateStyleJsonFactory {

	public static ObjectTemplateStyle jsonToObject(JSONObject jsonObject) {
            ObjectTemplateStyle model = new ObjectTemplateStyle();
            if (jsonObject.containsKey("styleId")) {
		    model.setStyleId(jsonObject.getLong("styleId"));
		}
	
		if (jsonObject.containsKey("delFlag")) {
			model.setDelFlag(jsonObject.getInteger("delFlag"));
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

	public static JSONObject toJsonObject(ObjectTemplateStyle model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("styleId", model.getStyleId());
		jsonObject.put("_styleId_", model.getStyleId());
		if (model.getRuleContent() != null) {
			jsonObject.put("ruleContent", model.getRuleContent());
		} 
		if (model.getStyleContent() != null) {
			jsonObject.put("styleContent", model.getStyleContent());
		} 
        jsonObject.put("delFlag", model.getDelFlag());
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


	public static ObjectNode toObjectNode(ObjectTemplateStyle model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("styleId", model.getStyleId());
		jsonObject.put("_styleId_", model.getStyleId());
                if (model.getRuleContent() != null) {
                     jsonObject.put("ruleContent", model.getRuleContent());
                } 
                if (model.getStyleContent() != null) {
                     jsonObject.put("styleContent", model.getStyleContent());
                } 
                jsonObject.put("delFlag", model.getDelFlag());
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

	
	public static JSONArray listToArray(java.util.List<ObjectTemplateStyle> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ObjectTemplateStyle model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ObjectTemplateStyle> arrayToList(JSONArray array) {
		java.util.List<ObjectTemplateStyle> list = new java.util.ArrayList<ObjectTemplateStyle>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ObjectTemplateStyle model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private ObjectTemplateStyleJsonFactory() {

	}

}
