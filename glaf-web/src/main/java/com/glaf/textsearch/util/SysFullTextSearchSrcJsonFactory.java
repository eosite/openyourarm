package com.glaf.textsearch.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.textsearch.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class SysFullTextSearchSrcJsonFactory {

	public static SysFullTextSearchSrc jsonToObject(JSONObject jsonObject) {
            SysFullTextSearchSrc model = new SysFullTextSearchSrc();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("serviceName_")) {
			model.setServiceName_(jsonObject.getString("serviceName_"));
		}
		if (jsonObject.containsKey("serviceAddress_")) {
			model.setServiceAddress_(jsonObject.getString("serviceAddress_"));
		}
		if (jsonObject.containsKey("rule_")) {
			model.setRule_(jsonObject.getString("rule_"));
		}
		if (jsonObject.containsKey("fullTextServer_")) {
			model.setFullTextServer_(jsonObject.getString("fullTextServer_"));
		}
		if (jsonObject.containsKey("indexName_")) {
			model.setIndexName_(jsonObject.getString("indexName_"));
		}
		if (jsonObject.containsKey("typeName_")) {
			model.setTypeName_(jsonObject.getString("typeName_"));
		}
		if (jsonObject.containsKey("createBy_")) {
			model.setCreateBy_(jsonObject.getString("createBy_"));
		}
		if (jsonObject.containsKey("createTime_")) {
			model.setCreateTime_(jsonObject.getDate("createTime_"));
		}
		if (jsonObject.containsKey("updateBy_")) {
			model.setUpdateBy_(jsonObject.getString("updateBy_"));
		}
		if (jsonObject.containsKey("updateTime_")) {
			model.setUpdateTime_(jsonObject.getDate("updateTime_"));
		}
		if (jsonObject.containsKey("deleteFlag_")) {
			model.setDeleteFlag_(jsonObject.getInteger("deleteFlag_"));
		}

            return model;
	}

	public static JSONObject toJsonObject(SysFullTextSearchSrc model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getServiceName_() != null) {
			jsonObject.put("serviceName_", model.getServiceName_());
		} 
		if (model.getServiceAddress_() != null) {
			jsonObject.put("serviceAddress_", model.getServiceAddress_());
		} 
		if (model.getRule_() != null) {
			jsonObject.put("rule_", model.getRule_());
		} 
		if (model.getFullTextServer_() != null) {
			jsonObject.put("fullTextServer_", model.getFullTextServer_());
		} 
		if (model.getIndexName_() != null) {
			jsonObject.put("indexName_", model.getIndexName_());
		} 
		if (model.getTypeName_() != null) {
			jsonObject.put("typeName_", model.getTypeName_());
		} 
		if (model.getCreateBy_() != null) {
			jsonObject.put("createBy_", model.getCreateBy_());
		} 
                if (model.getCreateTime_() != null) {
                      jsonObject.put("createTime_", DateUtils.getDate(model.getCreateTime_()));
		      jsonObject.put("createTime__date", DateUtils.getDate(model.getCreateTime_()));
		      jsonObject.put("createTime__datetime", DateUtils.getDateTime(model.getCreateTime_()));
                }
		if (model.getUpdateBy_() != null) {
			jsonObject.put("updateBy_", model.getUpdateBy_());
		} 
                if (model.getUpdateTime_() != null) {
                      jsonObject.put("updateTime_", DateUtils.getDate(model.getUpdateTime_()));
		      jsonObject.put("updateTime__date", DateUtils.getDate(model.getUpdateTime_()));
		      jsonObject.put("updateTime__datetime", DateUtils.getDateTime(model.getUpdateTime_()));
                }
        jsonObject.put("deleteFlag_", model.getDeleteFlag_());
		return jsonObject;
	}


	public static ObjectNode toObjectNode(SysFullTextSearchSrc model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getServiceName_() != null) {
                     jsonObject.put("serviceName_", model.getServiceName_());
                } 
                if (model.getServiceAddress_() != null) {
                     jsonObject.put("serviceAddress_", model.getServiceAddress_());
                } 
                if (model.getRule_() != null) {
                     jsonObject.put("rule_", model.getRule_());
                } 
                if (model.getFullTextServer_() != null) {
                     jsonObject.put("fullTextServer_", model.getFullTextServer_());
                } 
                if (model.getIndexName_() != null) {
                     jsonObject.put("indexName_", model.getIndexName_());
                } 
                if (model.getTypeName_() != null) {
                     jsonObject.put("typeName_", model.getTypeName_());
                } 
                if (model.getCreateBy_() != null) {
                     jsonObject.put("createBy_", model.getCreateBy_());
                } 
                if (model.getCreateTime_() != null) {
                      jsonObject.put("createTime_", DateUtils.getDate(model.getCreateTime_()));
		      jsonObject.put("createTime__date", DateUtils.getDate(model.getCreateTime_()));
		      jsonObject.put("createTime__datetime", DateUtils.getDateTime(model.getCreateTime_()));
                }
                if (model.getUpdateBy_() != null) {
                     jsonObject.put("updateBy_", model.getUpdateBy_());
                } 
                if (model.getUpdateTime_() != null) {
                      jsonObject.put("updateTime_", DateUtils.getDate(model.getUpdateTime_()));
		      jsonObject.put("updateTime__date", DateUtils.getDate(model.getUpdateTime_()));
		      jsonObject.put("updateTime__datetime", DateUtils.getDateTime(model.getUpdateTime_()));
                }
                jsonObject.put("deleteFlag_", model.getDeleteFlag_());
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<SysFullTextSearchSrc> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SysFullTextSearchSrc model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SysFullTextSearchSrc> arrayToList(JSONArray array) {
		java.util.List<SysFullTextSearchSrc> list = new java.util.ArrayList<SysFullTextSearchSrc>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SysFullTextSearchSrc model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private SysFullTextSearchSrcJsonFactory() {

	}

}
