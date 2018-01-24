package com.glaf.base.modules.uis.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.base.modules.uis.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class UisAppUserJsonFactory {

	public static UisAppUser jsonToObject(JSONObject jsonObject) {
            UisAppUser model = new UisAppUser();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("userId")) {
			model.setUserId(jsonObject.getString("userId"));
		}
		if (jsonObject.containsKey("appId")) {
			model.setAppId(jsonObject.getString("appId"));
		}
		if (jsonObject.containsKey("userName")) {
			model.setUserName(jsonObject.getString("userName"));
		}
		if (jsonObject.containsKey("email")) {
			model.setEmail(jsonObject.getString("email"));
		}
		if (jsonObject.containsKey("tel")) {
			model.setTel(jsonObject.getString("tel"));
		}
		if (jsonObject.containsKey("mobile")) {
			model.setMobile(jsonObject.getString("mobile"));
		}
		if (jsonObject.containsKey("age")) {
			model.setAge(jsonObject.getInteger("age"));
		}
		if (jsonObject.containsKey("desc")) {
			model.setDesc(jsonObject.getString("desc"));
		}
		if (jsonObject.containsKey("sex")) {
			model.setSex(jsonObject.getInteger("sex"));
		}
		if (jsonObject.containsKey("qq")) {
			model.setQq(jsonObject.getString("qq"));
		}
		if (jsonObject.containsKey("weq")) {
			model.setWeq(jsonObject.getString("weq"));
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

	public static JSONObject toJsonObject(UisAppUser model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getUserId() != null) {
			jsonObject.put("userId", model.getUserId());
		} 
		if (model.getAppId() != null) {
			jsonObject.put("appId", model.getAppId());
		} 
		if (model.getUserName() != null) {
			jsonObject.put("userName", model.getUserName());
		} 
		if (model.getEmail() != null) {
			jsonObject.put("email", model.getEmail());
		} 
		if (model.getTel() != null) {
			jsonObject.put("tel", model.getTel());
		} 
		if (model.getMobile() != null) {
			jsonObject.put("mobile", model.getMobile());
		} 
        jsonObject.put("age", model.getAge());
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		} 
        jsonObject.put("sex", model.getSex());
		if (model.getQq() != null) {
			jsonObject.put("qq", model.getQq());
		} 
		if (model.getWeq() != null) {
			jsonObject.put("weq", model.getWeq());
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


	public static ObjectNode toObjectNode(UisAppUser model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getUserId() != null) {
                     jsonObject.put("userId", model.getUserId());
                } 
                if (model.getAppId() != null) {
                     jsonObject.put("appId", model.getAppId());
                } 
                if (model.getUserName() != null) {
                     jsonObject.put("userName", model.getUserName());
                } 
                if (model.getEmail() != null) {
                     jsonObject.put("email", model.getEmail());
                } 
                if (model.getTel() != null) {
                     jsonObject.put("tel", model.getTel());
                } 
                if (model.getMobile() != null) {
                     jsonObject.put("mobile", model.getMobile());
                } 
                jsonObject.put("age", model.getAge());
                if (model.getDesc() != null) {
                     jsonObject.put("desc", model.getDesc());
                } 
                jsonObject.put("sex", model.getSex());
                if (model.getQq() != null) {
                     jsonObject.put("qq", model.getQq());
                } 
                if (model.getWeq() != null) {
                     jsonObject.put("weq", model.getWeq());
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

	
	public static JSONArray listToArray(java.util.List<UisAppUser> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (UisAppUser model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<UisAppUser> arrayToList(JSONArray array) {
		java.util.List<UisAppUser> list = new java.util.ArrayList<UisAppUser>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			UisAppUser model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private UisAppUserJsonFactory() {

	}

}
