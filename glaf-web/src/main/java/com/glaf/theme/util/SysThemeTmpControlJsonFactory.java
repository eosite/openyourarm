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
public class SysThemeTmpControlJsonFactory {

	public static SysThemeTmpControl jsonToObject(JSONObject jsonObject) {
            SysThemeTmpControl model = new SysThemeTmpControl();
            if (jsonObject.containsKey("controlId")) {
		    model.setControlId(jsonObject.getString("controlId"));
		}
		if (jsonObject.containsKey("themeTmpId")) {
			model.setThemeTmpId(jsonObject.getString("themeTmpId"));
		}
		if (jsonObject.containsKey("commonFlag")) {
			model.setCommonFlag(jsonObject.getInteger("commonFlag"));
		}
		if (jsonObject.containsKey("controlName")) {
			model.setControlName(jsonObject.getString("controlName"));
		}
		if (jsonObject.containsKey("controlCode")) {
			model.setControlCode(jsonObject.getString("controlCode"));
		}
		if (jsonObject.containsKey("compType")) {
			model.setCompType(jsonObject.getString("compType"));
		}
		if (jsonObject.containsKey("elemCode")) {
			model.setElemCode(jsonObject.getString("elemCode"));
		}
		if (jsonObject.containsKey("compositionFlag")) {
			model.setCompositionFlag(jsonObject.getInteger("compositionFlag"));
		}
		if (jsonObject.containsKey("containerFlag")) {
			model.setContainerFlag(jsonObject.getInteger("containerFlag"));
		}
		if (jsonObject.containsKey("selectorExp")) {
			model.setSelectorExp(jsonObject.getString("selectorExp"));
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
		if (jsonObject.containsKey("defaultFlag")) {
			model.setDefaultFlag(jsonObject.getInteger("defaultFlag"));
		}
            return model;
	}

	public static JSONObject toJsonObject(SysThemeTmpControl model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("controlId", model.getControlId());
		jsonObject.put("_controlId_", model.getControlId());
		if (model.getThemeTmpId() != null) {
			jsonObject.put("themeTmpId", model.getThemeTmpId());
		} 
        jsonObject.put("commonFlag", model.getCommonFlag());
		if (model.getControlName() != null) {
			jsonObject.put("controlName", model.getControlName());
		} 
		if (model.getControlCode() != null) {
			jsonObject.put("controlCode", model.getControlCode());
		} 
		if (model.getCompType() != null) {
			jsonObject.put("compType", model.getCompType());
		} 
		if (model.getElemCode() != null) {
			jsonObject.put("elemCode", model.getElemCode());
		} 
        jsonObject.put("compositionFlag", model.getCompositionFlag());
        jsonObject.put("containerFlag", model.getContainerFlag());
		if (model.getThumbnail() != null) {
			jsonObject.put("thumbnail", model.getThumbnail());
		} 
		if (model.getSelectorExp() != null) {
			jsonObject.put("selectorExp", model.getSelectorExp());
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
        if (model.getHtmlText() != null) {
			jsonObject.put("htmlText", model.getHtmlText());
		}
        if (model.getCssText() != null) {
			jsonObject.put("cssText", model.getCssText());
		}
        jsonObject.put("deleteFlag", model.getDeleteFlag());
        
        jsonObject.put("defaultFlag", model.getDefaultFlag());
		return jsonObject;
	}


	public static ObjectNode toObjectNode(SysThemeTmpControl model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("controlId", model.getControlId());
		jsonObject.put("_controlId_", model.getControlId());
                if (model.getThemeTmpId() != null) {
                     jsonObject.put("themeTmpId", model.getThemeTmpId());
                } 
                jsonObject.put("commonFlag", model.getCommonFlag());
                if (model.getControlName() != null) {
                     jsonObject.put("controlName", model.getControlName());
                } 
                if (model.getControlCode() != null) {
                     jsonObject.put("controlCode", model.getControlCode());
                } 
                if (model.getCompType() != null) {
                     jsonObject.put("compType", model.getCompType());
                } 
                if (model.getElemCode() != null) {
                     jsonObject.put("elemCode", model.getElemCode());
                } 
                jsonObject.put("compositionFlag", model.getCompositionFlag());
                jsonObject.put("containerFlag", model.getContainerFlag());
                if (model.getThumbnail() != null) {
                     jsonObject.put("thumbnail", model.getThumbnail());
                } 
                if (model.getSelectorExp() != null) {
                     jsonObject.put("selectorExp", model.getSelectorExp());
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
                if (model.getHtmlText() != null) {
        			jsonObject.put("htmlText", model.getHtmlText());
        		}
                if (model.getCssText() != null) {
        			jsonObject.put("cssText", model.getCssText());
        		}
                
                jsonObject.put("defaultFlag", model.getDefaultFlag());
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<SysThemeTmpControl> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SysThemeTmpControl model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SysThemeTmpControl> arrayToList(JSONArray array) {
		java.util.List<SysThemeTmpControl> list = new java.util.ArrayList<SysThemeTmpControl>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SysThemeTmpControl model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private SysThemeTmpControlJsonFactory() {

	}

}
