package com.glaf.theme.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.theme.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class SysThemeTmpBytearrayJsonFactory {

	public static SysThemeTmpBytearray jsonToObject(JSONObject jsonObject) {
            SysThemeTmpBytearray model = new SysThemeTmpBytearray();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("bussType")) {
			model.setBussType(jsonObject.getString("bussType"));
		}
		if (jsonObject.containsKey("bussKey")) {
			model.setBussKey(jsonObject.getString("bussKey"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
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

	public static JSONObject toJsonObject(SysThemeTmpBytearray model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		} 
		if (model.getBytes() != null) {
			jsonObject.put("bytes", model.getBytes());
		} 
		if (model.getBussType() != null) {
			jsonObject.put("bussType", model.getBussType());
		} 
		if (model.getBussKey() != null) {
			jsonObject.put("bussKey", model.getBussKey());
		} 
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
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


	public static ObjectNode toObjectNode(SysThemeTmpBytearray model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getName() != null) {
                     jsonObject.put("name", model.getName());
                } 
                if (model.getBytes() != null) {
                     jsonObject.put("bytes", model.getBytes());
                } 
                if (model.getBussType() != null) {
                     jsonObject.put("bussType", model.getBussType());
                } 
                if (model.getBussKey() != null) {
                     jsonObject.put("bussKey", model.getBussKey());
                } 
                if (model.getType() != null) {
                     jsonObject.put("type", model.getType());
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

	
	public static JSONArray listToArray(java.util.List<SysThemeTmpBytearray> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SysThemeTmpBytearray model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SysThemeTmpBytearray> arrayToList(JSONArray array) {
		java.util.List<SysThemeTmpBytearray> list = new java.util.ArrayList<SysThemeTmpBytearray>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SysThemeTmpBytearray model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private SysThemeTmpBytearrayJsonFactory() {

	}

}
