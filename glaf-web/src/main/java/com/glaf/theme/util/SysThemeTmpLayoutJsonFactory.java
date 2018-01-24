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
public class SysThemeTmpLayoutJsonFactory {

	public static SysThemeTmpLayout jsonToObject(JSONObject jsonObject) {
            SysThemeTmpLayout model = new SysThemeTmpLayout();
            if (jsonObject.containsKey("layoutId")) {
		    model.setLayoutId(jsonObject.getString("layoutId"));
		}
		if (jsonObject.containsKey("themeTmpId")) {
			model.setThemeTmpId(jsonObject.getString("themeTmpId"));
		}
		if (jsonObject.containsKey("layoutName")) {
			model.setLayoutName(jsonObject.getString("layoutName"));
		}
		if (jsonObject.containsKey("layoutPlan")) {
			model.setLayoutPlan(jsonObject.getString("layoutPlan"));
		}
		if (jsonObject.containsKey("layoutCode")) {
			model.setLayoutCode(jsonObject.getString("layoutCode"));
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

	public static JSONObject toJsonObject(SysThemeTmpLayout model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("layoutId", model.getLayoutId());
		jsonObject.put("_layoutId_", model.getLayoutId());
		if (model.getThemeTmpId() != null) {
			jsonObject.put("themeTmpId", model.getThemeTmpId());
		} 
		if (model.getLayoutName() != null) {
			jsonObject.put("layoutName", model.getLayoutName());
		} 
		if (model.getLayoutPlan() != null) {
			jsonObject.put("layoutPlan", model.getLayoutPlan());
		} 
		if (model.getLayoutCode() != null) {
			jsonObject.put("layoutCode", model.getLayoutCode());
		} 
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
		return jsonObject;
	}


	public static ObjectNode toObjectNode(SysThemeTmpLayout model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("layoutId", model.getLayoutId());
		jsonObject.put("_layoutId_", model.getLayoutId());
                if (model.getThemeTmpId() != null) {
                     jsonObject.put("themeTmpId", model.getThemeTmpId());
                } 
                if (model.getLayoutName() != null) {
                     jsonObject.put("layoutName", model.getLayoutName());
                } 
                if (model.getLayoutPlan() != null) {
                     jsonObject.put("layoutPlan", model.getLayoutPlan());
                } 
                if (model.getLayoutCode() != null) {
                     jsonObject.put("layoutCode", model.getLayoutCode());
                } 
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
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<SysThemeTmpLayout> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SysThemeTmpLayout model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SysThemeTmpLayout> arrayToList(JSONArray array) {
		java.util.List<SysThemeTmpLayout> list = new java.util.ArrayList<SysThemeTmpLayout>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SysThemeTmpLayout model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private SysThemeTmpLayoutJsonFactory() {

	}

}
