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
public class SysThemeTmpLayoutAreaControlJsonFactory {

	public static SysThemeTmpLayoutAreaControl jsonToObject(JSONObject jsonObject) {
            SysThemeTmpLayoutAreaControl model = new SysThemeTmpLayoutAreaControl();
            if (jsonObject.containsKey("controlId")) {
		    model.setControlId(jsonObject.getString("controlId"));
		}
		if (jsonObject.containsKey("themeTmpControlId")) {
			model.setThemeTmpControlId(jsonObject.getString("themeTmpControlId"));
		}
		if (jsonObject.containsKey("areaId")) {
			model.setAreaId(jsonObject.getString("areaId"));
		}
		if (jsonObject.containsKey("layoutId")) {
			model.setLayoutId(jsonObject.getString("layoutId"));
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
		if (jsonObject.containsKey("selectorExp")) {
			model.setSelectorExp(jsonObject.getString("selectorExp"));
		}
		if (jsonObject.containsKey("commonFlag")) {
			model.setCommonFlag(jsonObject.getInteger("commonFlag"));
		}
		if (jsonObject.containsKey("containerFlag")) {
			model.setContainerFlag(jsonObject.getInteger("containerFlag"));
		}
		if (jsonObject.containsKey("pcontrolId")) {
			model.setPcontrolId(jsonObject.getString("pcontrolId"));
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

	public static JSONObject toJsonObject(SysThemeTmpLayoutAreaControl model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("controlId", model.getControlId());
		jsonObject.put("_controlId_", model.getControlId());
		if (model.getThemeTmpControlId() != null) {
			jsonObject.put("themeTmpControlId", model.getThemeTmpControlId());
		} 
		if (model.getAreaId() != null) {
			jsonObject.put("areaId", model.getAreaId());
		} 
		if (model.getLayoutId() != null) {
			jsonObject.put("layoutId", model.getLayoutId());
		} 
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
		if (model.getThumbnail() != null) {
			jsonObject.put("thumbnail", model.getThumbnail());
		} 
		if (model.getSelectorExp() != null) {
			jsonObject.put("selectorExp", model.getSelectorExp());
		} 
        jsonObject.put("commonFlag", model.getCommonFlag());
        jsonObject.put("containerFlag", model.getContainerFlag());
		if (model.getPcontrolId() != null) {
			jsonObject.put("pcontrolId", model.getPcontrolId());
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


	public static ObjectNode toObjectNode(SysThemeTmpLayoutAreaControl model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("controlId", model.getControlId());
		jsonObject.put("_controlId_", model.getControlId());
                if (model.getThemeTmpControlId() != null) {
                     jsonObject.put("themeTmpControlId", model.getThemeTmpControlId());
                } 
                if (model.getAreaId() != null) {
                     jsonObject.put("areaId", model.getAreaId());
                } 
                if (model.getLayoutId() != null) {
                     jsonObject.put("layoutId", model.getLayoutId());
                } 
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
                if (model.getThumbnail() != null) {
                     jsonObject.put("thumbnail", model.getThumbnail());
                } 
                if (model.getSelectorExp() != null) {
                     jsonObject.put("selectorExp", model.getSelectorExp());
                } 
                jsonObject.put("commonFlag", model.getCommonFlag());
                jsonObject.put("containerFlag", model.getContainerFlag());
                if (model.getPcontrolId() != null) {
                     jsonObject.put("pcontrolId", model.getPcontrolId());
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

	
	public static JSONArray listToArray(java.util.List<SysThemeTmpLayoutAreaControl> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SysThemeTmpLayoutAreaControl model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SysThemeTmpLayoutAreaControl> arrayToList(JSONArray array) {
		java.util.List<SysThemeTmpLayoutAreaControl> list = new java.util.ArrayList<SysThemeTmpLayoutAreaControl>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SysThemeTmpLayoutAreaControl model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private SysThemeTmpLayoutAreaControlJsonFactory() {

	}

}
