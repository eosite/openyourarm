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
public class SysThemeTmpControlAreaJsonFactory {

	public static SysThemeTmpControlArea jsonToObject(JSONObject jsonObject) {
            SysThemeTmpControlArea model = new SysThemeTmpControlArea();
            if (jsonObject.containsKey("ControlAreaId")) {
		    model.setControlAreaId(jsonObject.getString("ControlAreaId"));
		}
		if (jsonObject.containsKey("controlId")) {
			model.setControlId(jsonObject.getString("controlId"));
		}
		if (jsonObject.containsKey("areaName")) {
			model.setAreaName(jsonObject.getString("areaName"));
		}
		if (jsonObject.containsKey("areaCode")) {
			model.setAreaCode(jsonObject.getString("areaCode"));
		}
		if (jsonObject.containsKey("compType")) {
			model.setCompType(jsonObject.getString("compType"));
		}
		if (jsonObject.containsKey("elemCode")) {
			model.setElemCode(jsonObject.getString("elemCode"));
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

            return model;
	}

	public static JSONObject toJsonObject(SysThemeTmpControlArea model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ControlAreaId", model.getControlAreaId());
		jsonObject.put("_ControlAreaId_", model.getControlAreaId());
		if (model.getControlId() != null) {
			jsonObject.put("controlId", model.getControlId());
		} 
		if (model.getAreaName() != null) {
			jsonObject.put("areaName", model.getAreaName());
		} 
		if (model.getAreaCode() != null) {
			jsonObject.put("areaCode", model.getAreaCode());
		} 
		if (model.getCompType() != null) {
			jsonObject.put("compType", model.getCompType());
		} 
		if (model.getElemCode() != null) {
			jsonObject.put("elemCode", model.getElemCode());
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
		return jsonObject;
	}


	public static ObjectNode toObjectNode(SysThemeTmpControlArea model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("ControlAreaId", model.getControlAreaId());
		jsonObject.put("_ControlAreaId_", model.getControlAreaId());
                if (model.getControlId() != null) {
                     jsonObject.put("controlId", model.getControlId());
                } 
                if (model.getAreaName() != null) {
                     jsonObject.put("areaName", model.getAreaName());
                } 
                if (model.getAreaCode() != null) {
                     jsonObject.put("areaCode", model.getAreaCode());
                } 
                if (model.getCompType() != null) {
                     jsonObject.put("compType", model.getCompType());
                } 
                if (model.getElemCode() != null) {
                     jsonObject.put("elemCode", model.getElemCode());
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
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<SysThemeTmpControlArea> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SysThemeTmpControlArea model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SysThemeTmpControlArea> arrayToList(JSONArray array) {
		java.util.List<SysThemeTmpControlArea> list = new java.util.ArrayList<SysThemeTmpControlArea>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SysThemeTmpControlArea model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private SysThemeTmpControlAreaJsonFactory() {

	}

}
