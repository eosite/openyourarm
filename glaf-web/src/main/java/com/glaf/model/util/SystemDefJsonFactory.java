package com.glaf.model.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.model.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class SystemDefJsonFactory {

	public static SystemDef jsonToObject(JSONObject jsonObject) {
            SystemDef model = new SystemDef();
            if (jsonObject.containsKey("sysId")) {
		    model.setSysId(jsonObject.getString("sysId"));
		}
		if (jsonObject.containsKey("sysName")) {
			model.setSysName(jsonObject.getString("sysName"));
		}
		if (jsonObject.containsKey("sysCode")) {
			model.setSysCode(jsonObject.getString("sysCode"));
		}
		if (jsonObject.containsKey("sysDesc")) {
			model.setSysDesc(jsonObject.getString("sysDesc"));
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
		if (jsonObject.containsKey("version")) {
			model.setVersion(jsonObject.getString("version"));
		}
		if (jsonObject.containsKey("publisher")) {
			model.setPublisher(jsonObject.getString("publisher"));
		}
		if (jsonObject.containsKey("publishTime")) {
			model.setPublishTime(jsonObject.getDate("publishTime"));
		}

            return model;
	}

	public static JSONObject toJsonObject(SystemDef model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("sysId", model.getSysId());
		jsonObject.put("_sysId_", model.getSysId());
		if (model.getSysName() != null) {
			jsonObject.put("sysName", model.getSysName());
		} 
		if (model.getSysCode() != null) {
			jsonObject.put("sysCode", model.getSysCode());
		} 
		if (model.getSysDesc() != null) {
			jsonObject.put("sysDesc", model.getSysDesc());
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
		if (model.getVersion() != null) {
			jsonObject.put("version", model.getVersion());
		} 
		if (model.getPublisher() != null) {
			jsonObject.put("publisher", model.getPublisher());
		} 
                if (model.getPublishTime() != null) {
                      jsonObject.put("publishTime", DateUtils.getDate(model.getPublishTime()));
		      jsonObject.put("publishTime_date", DateUtils.getDate(model.getPublishTime()));
		      jsonObject.put("publishTime_datetime", DateUtils.getDateTime(model.getPublishTime()));
                }
		return jsonObject;
	}


	public static ObjectNode toObjectNode(SystemDef model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("sysId", model.getSysId());
		jsonObject.put("_sysId_", model.getSysId());
                if (model.getSysName() != null) {
                     jsonObject.put("sysName", model.getSysName());
                } 
                if (model.getSysCode() != null) {
                     jsonObject.put("sysCode", model.getSysCode());
                } 
                if (model.getSysDesc() != null) {
                     jsonObject.put("sysDesc", model.getSysDesc());
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
                if (model.getVersion() != null) {
                     jsonObject.put("version", model.getVersion());
                } 
                if (model.getPublisher() != null) {
                     jsonObject.put("publisher", model.getPublisher());
                } 
                if (model.getPublishTime() != null) {
                      jsonObject.put("publishTime", DateUtils.getDate(model.getPublishTime()));
		      jsonObject.put("publishTime_date", DateUtils.getDate(model.getPublishTime()));
		      jsonObject.put("publishTime_datetime", DateUtils.getDateTime(model.getPublishTime()));
                }
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<SystemDef> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SystemDef model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SystemDef> arrayToList(JSONArray array) {
		java.util.List<SystemDef> list = new java.util.ArrayList<SystemDef>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SystemDef model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private SystemDefJsonFactory() {

	}

}
