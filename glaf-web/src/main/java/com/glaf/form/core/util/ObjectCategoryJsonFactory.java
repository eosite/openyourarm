package com.glaf.form.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.domain.ObjectCategory;


/**
 * 
 * JSON工厂类
 *
 */
public class ObjectCategoryJsonFactory {

	public static ObjectCategory jsonToObject(JSONObject jsonObject) {
            ObjectCategory model = new ObjectCategory();
            if (jsonObject.containsKey("categoryId")) {
		    model.setCategoryId(jsonObject.getLong("categoryId"));
		}
		if (jsonObject.containsKey("treeID")) {
			model.setTreeID(jsonObject.getString("treeID"));
		}
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("custom")) {
			model.setCustom(jsonObject.getInteger("custom"));
		}
		if (jsonObject.containsKey("owner")) {
			model.setOwner(jsonObject.getString("owner"));
		}
		if (jsonObject.containsKey("parentId")) {
			model.setParentId(jsonObject.getLong("parentId"));
		}
		if (jsonObject.containsKey("orderNo")) {
			model.setOrderNo(jsonObject.getInteger("orderNo"));
		}
		if (jsonObject.containsKey("level")) {
			model.setLevel(jsonObject.getInteger("level"));
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

	public static JSONObject toJsonObject(ObjectCategory model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("categoryId", model.getCategoryId());
		jsonObject.put("_categoryId_", model.getCategoryId());
		if (model.getTreeID() != null) {
			jsonObject.put("treeID", model.getTreeID());
		} 
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		} 
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		} 
        jsonObject.put("custom", model.getCustom());
		if (model.getOwner() != null) {
			jsonObject.put("owner", model.getOwner());
		} 
        jsonObject.put("parentId", model.getParentId());
        jsonObject.put("orderNo", model.getOrderNo());
        jsonObject.put("level", model.getLevel());
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


	public static ObjectNode toObjectNode(ObjectCategory model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("categoryId", model.getCategoryId());
		jsonObject.put("_categoryId_", model.getCategoryId());
                if (model.getTreeID() != null) {
                     jsonObject.put("treeID", model.getTreeID());
                } 
                if (model.getCode() != null) {
                     jsonObject.put("code", model.getCode());
                } 
                if (model.getName() != null) {
                     jsonObject.put("name", model.getName());
                } 
                jsonObject.put("custom", model.getCustom());
                if (model.getOwner() != null) {
                     jsonObject.put("owner", model.getOwner());
                } 
                jsonObject.put("parentId", model.getParentId());
                jsonObject.put("orderNo", model.getOrderNo());
                jsonObject.put("level", model.getLevel());
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

	
	public static JSONArray listToArray(java.util.List<ObjectCategory> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ObjectCategory model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ObjectCategory> arrayToList(JSONArray array) {
		java.util.List<ObjectCategory> list = new java.util.ArrayList<ObjectCategory>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ObjectCategory model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private ObjectCategoryJsonFactory() {

	}

}
