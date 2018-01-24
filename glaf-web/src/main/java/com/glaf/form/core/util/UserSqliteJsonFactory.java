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
public class UserSqliteJsonFactory {

	public static UserSqlite jsonToObject(JSONObject jsonObject) {
            UserSqlite model = new UserSqlite();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("userId")) {
			model.setUserId(jsonObject.getString("userId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("desc")) {
			model.setDesc(jsonObject.getString("desc"));
		}
		if (jsonObject.containsKey("sqliteCode")) {
			model.setSqliteCode(jsonObject.getString("sqliteCode"));
		}
		if (jsonObject.containsKey("ruleJson")) {
			model.setRuleJson(jsonObject.getString("ruleJson"));
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
		if (jsonObject.containsKey("fileDate")) {
			model.setFileDate(jsonObject.getDate("fileDate"));
		}
		if (jsonObject.containsKey("downloadNum")) {
			model.setDownloadNum(jsonObject.getInteger("downloadNum"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("errorMessage")) {
			model.setErrorMessage(jsonObject.getString("errorMessage"));
		}

            return model;
	}

	public static JSONObject toJsonObject(UserSqlite model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getUserId() != null) {
			jsonObject.put("userId", model.getUserId());
		} 
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		} 
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		} 
		if (model.getSqliteCode() != null) {
			jsonObject.put("sqliteCode", model.getSqliteCode());
		} 
		if (model.getRuleJson() != null) {
			jsonObject.put("ruleJson", model.getRuleJson());
		} 
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
                if (model.getFileDate() != null) {
                      jsonObject.put("fileDate", DateUtils.getDate(model.getFileDate()));
		      jsonObject.put("fileDate_date", DateUtils.getDate(model.getFileDate()));
		      jsonObject.put("fileDate_datetime", DateUtils.getDateTime(model.getFileDate()));
                }
        jsonObject.put("downloadNum", model.getDownloadNum());
        jsonObject.put("status", model.getStatus());
		if (model.getErrorMessage() != null) {
			jsonObject.put("errorMessage", model.getErrorMessage());
		} 
		return jsonObject;
	}


	public static ObjectNode toObjectNode(UserSqlite model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getUserId() != null) {
                     jsonObject.put("userId", model.getUserId());
                } 
                if (model.getName() != null) {
                     jsonObject.put("name", model.getName());
                } 
                if (model.getDesc() != null) {
                     jsonObject.put("desc", model.getDesc());
                } 
                if (model.getSqliteCode() != null) {
                     jsonObject.put("sqliteCode", model.getSqliteCode());
                } 
                if (model.getRuleJson() != null) {
                     jsonObject.put("ruleJson", model.getRuleJson());
                } 
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
                if (model.getFileDate() != null) {
                      jsonObject.put("fileDate", DateUtils.getDate(model.getFileDate()));
		      jsonObject.put("fileDate_date", DateUtils.getDate(model.getFileDate()));
		      jsonObject.put("fileDate_datetime", DateUtils.getDateTime(model.getFileDate()));
                }
                jsonObject.put("downloadNum", model.getDownloadNum());
                jsonObject.put("status", model.getStatus());
                if (model.getErrorMessage() != null) {
                     jsonObject.put("errorMessage", model.getErrorMessage());
                } 
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<UserSqlite> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (UserSqlite model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<UserSqlite> arrayToList(JSONArray array) {
		java.util.List<UserSqlite> list = new java.util.ArrayList<UserSqlite>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			UserSqlite model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private UserSqliteJsonFactory() {

	}

}
