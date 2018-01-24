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
public class SysThemeTmpLayoutAreaJsonFactory {

	public static SysThemeTmpLayoutArea jsonToObject(JSONObject jsonObject) {
            SysThemeTmpLayoutArea model = new SysThemeTmpLayoutArea();
            if (jsonObject.containsKey("areaId")) {
		    model.setAreaId(jsonObject.getString("areaId"));
		}
		if (jsonObject.containsKey("layoutId")) {
			model.setLayoutId(jsonObject.getString("layoutId"));
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

	public static JSONObject toJsonObject(SysThemeTmpLayoutArea model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("areaId", model.getAreaId());
		jsonObject.put("_areaId_", model.getAreaId());
		if (model.getLayoutId() != null) {
			jsonObject.put("layoutId", model.getLayoutId());
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


	public static ObjectNode toObjectNode(SysThemeTmpLayoutArea model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("areaId", model.getAreaId());
		jsonObject.put("_areaId_", model.getAreaId());
                if (model.getLayoutId() != null) {
                     jsonObject.put("layoutId", model.getLayoutId());
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

	
	public static JSONArray listToArray(java.util.List<SysThemeTmpLayoutArea> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SysThemeTmpLayoutArea model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SysThemeTmpLayoutArea> arrayToList(JSONArray array) {
		java.util.List<SysThemeTmpLayoutArea> list = new java.util.ArrayList<SysThemeTmpLayoutArea>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SysThemeTmpLayoutArea model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private SysThemeTmpLayoutAreaJsonFactory() {

	}

}
