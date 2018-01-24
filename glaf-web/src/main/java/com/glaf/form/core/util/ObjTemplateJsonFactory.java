package com.glaf.form.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.domain.ObjTemplate;


/**
 * 
 * JSON工厂类
 *
 */
public class ObjTemplateJsonFactory {

	public static ObjTemplate jsonToObject(JSONObject jsonObject) {
            ObjTemplate model = new ObjTemplate();
            if (jsonObject.containsKey("templateId")) {
		    model.setTemplateId(jsonObject.getLong("templateId"));
		}
		if (jsonObject.containsKey("tmpName")) {
			model.setTmpName(jsonObject.getString("tmpName"));
		}
		if (jsonObject.containsKey("objType")) {
			model.setObjType(jsonObject.getString("objType"));
		}
		if (jsonObject.containsKey("owner")) {
			model.setOwner(jsonObject.getString("owner"));
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

	public static JSONObject toJsonObject(ObjTemplate model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("templateId", model.getTemplateId());
		jsonObject.put("_templateId_", model.getTemplateId());
		if (model.getTmpName() != null) {
			jsonObject.put("tmpName", model.getTmpName());
		} 
		if (model.getObjType() != null) {
			jsonObject.put("objType", model.getObjType());
		} 
		
		if (model.getOwner() != null) {
			jsonObject.put("owner", model.getOwner());
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


	public static ObjectNode toObjectNode(ObjTemplate model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("templateId", model.getTemplateId());
		jsonObject.put("_templateId_", model.getTemplateId());
                if (model.getTmpName() != null) {
                     jsonObject.put("tmpName", model.getTmpName());
                } 
                if (model.getObjType() != null) {
                     jsonObject.put("objType", model.getObjType());
                } 
              
                if (model.getOwner() != null) {
                     jsonObject.put("owner", model.getOwner());
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

	
	public static JSONArray listToArray(java.util.List<ObjTemplate> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ObjTemplate model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ObjTemplate> arrayToList(JSONArray array) {
		java.util.List<ObjTemplate> list = new java.util.ArrayList<ObjTemplate>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ObjTemplate model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private ObjTemplateJsonFactory() {

	}

}
