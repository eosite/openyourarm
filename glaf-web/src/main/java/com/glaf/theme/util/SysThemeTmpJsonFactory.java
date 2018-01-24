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
public class SysThemeTmpJsonFactory {

	public static SysThemeTmp jsonToObject(JSONObject jsonObject) {
            SysThemeTmp model = new SysThemeTmp();
            if (jsonObject.containsKey("themeTmpId")) {
		    model.setThemeTmpId(jsonObject.getString("themeTmpId"));
		}
		if (jsonObject.containsKey("themeTmpName")) {
			model.setThemeTmpName(jsonObject.getString("themeTmpName"));
		}
		if (jsonObject.containsKey("themeTmpCode")) {
			model.setThemeTmpCode(jsonObject.getString("themeTmpCode"));
		}
		if (jsonObject.containsKey("ui")) {
			model.setUi(jsonObject.getString("ui"));
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
		if (jsonObject.containsKey("publisher")) {
			model.setPublisher(jsonObject.getString("publisher"));
		}
		if (jsonObject.containsKey("publishTime")) {
			model.setPublishTime(jsonObject.getDate("publishTime"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("ver")) {
			model.setVer(jsonObject.getInteger("ver"));
		}
		if (jsonObject.containsKey("defaultFlag")) {
			model.setDefaultFlag(jsonObject.getInteger("defaultFlag"));
		}
		
            return model;
	}

	public static JSONObject toJsonObject(SysThemeTmp model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("themeTmpId", model.getThemeTmpId());
		jsonObject.put("_themeTmpId_", model.getThemeTmpId());
		if (model.getThemeTmpName() != null) {
			jsonObject.put("themeTmpName", model.getThemeTmpName());
		} 
		if (model.getThemeTmpCode() != null) {
			jsonObject.put("themeTmpCode", model.getThemeTmpCode());
		} 
		if (model.getThumbnail() != null) {
			jsonObject.put("thumbnail", model.getThumbnail());
		} 
		if (model.getUi() != null) {
			jsonObject.put("ui", model.getUi());
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
		if (model.getPublisher() != null) {
			jsonObject.put("publisher", model.getPublisher());
		} 
                if (model.getPublishTime() != null) {
                      jsonObject.put("publishTime", DateUtils.getDate(model.getPublishTime()));
		      jsonObject.put("publishTime_date", DateUtils.getDate(model.getPublishTime()));
		      jsonObject.put("publishTime_datetime", DateUtils.getDateTime(model.getPublishTime()));
                }
        jsonObject.put("status", model.getStatus());
        jsonObject.put("ver", model.getVer());
        jsonObject.put("defaultFlag", model.getDefaultFlag());
		return jsonObject;
	}


	public static ObjectNode toObjectNode(SysThemeTmp model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("themeTmpId", model.getThemeTmpId());
		jsonObject.put("_themeTmpId_", model.getThemeTmpId());
                if (model.getThemeTmpName() != null) {
                     jsonObject.put("themeTmpName", model.getThemeTmpName());
                } 
                if (model.getThemeTmpCode() != null) {
                     jsonObject.put("themeTmpCode", model.getThemeTmpCode());
                } 
                if (model.getUi() != null) {
                     jsonObject.put("ui", model.getUi());
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
                if (model.getPublisher() != null) {
                     jsonObject.put("publisher", model.getPublisher());
                } 
                if (model.getPublishTime() != null) {
                      jsonObject.put("publishTime", DateUtils.getDate(model.getPublishTime()));
		      jsonObject.put("publishTime_date", DateUtils.getDate(model.getPublishTime()));
		      jsonObject.put("publishTime_datetime", DateUtils.getDateTime(model.getPublishTime()));
                }
                jsonObject.put("status", model.getStatus());
                jsonObject.put("ver", model.getVer());
                jsonObject.put("defaultFlag", model.getDefaultFlag());
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<SysThemeTmp> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SysThemeTmp model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SysThemeTmp> arrayToList(JSONArray array) {
		java.util.List<SysThemeTmp> list = new java.util.ArrayList<SysThemeTmp>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SysThemeTmp model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private SysThemeTmpJsonFactory() {

	}

}
