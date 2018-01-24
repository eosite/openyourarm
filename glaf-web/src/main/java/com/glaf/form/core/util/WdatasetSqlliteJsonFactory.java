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
public class WdatasetSqlliteJsonFactory {

	public static WdatasetSqllite jsonToObject(JSONObject jsonObject) {
            WdatasetSqllite model = new WdatasetSqllite();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("sqlliteRuleCode")) {
			model.setSqlliteRuleCode(jsonObject.getString("sqlliteRuleCode"));
		}
		if (jsonObject.containsKey("sqlliteRuleDesc")) {
			model.setSqlliteRuleDesc(jsonObject.getString("sqlliteRuleDesc"));
		}
		if (jsonObject.containsKey("sqlliteRuleName")) {
			model.setSqlliteRuleName(jsonObject.getString("sqlliteRuleName"));
		}
		if (jsonObject.containsKey("dataSetsName")) {
			model.setDataSetsName(jsonObject.getString("dataSetsName"));
		}
		if (jsonObject.containsKey("delflag")) {
			model.setDelflag(jsonObject.getString("delflag"));
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

            return model;
	}

	public static JSONObject toJsonObject(WdatasetSqllite model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getSqlliteRuleCode() != null) {
			jsonObject.put("sqlliteRuleCode", model.getSqlliteRuleCode());
		} 
		if (model.getSqlliteRuleDesc() != null) {
			jsonObject.put("sqlliteRuleDesc", model.getSqlliteRuleDesc());
		} 
		if (model.getSqlliteRuleName() != null) {
			jsonObject.put("sqlliteRuleName", model.getSqlliteRuleName());
		} 
		if (model.getDataSetsName() != null) {
			jsonObject.put("dataSetsName", model.getDataSetsName());
		} 
		if (model.getDelflag() != null) {
			jsonObject.put("delflag", model.getDelflag());
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
		return jsonObject;
	}


	public static ObjectNode toObjectNode(WdatasetSqllite model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getSqlliteRuleCode() != null) {
                     jsonObject.put("sqlliteRuleCode", model.getSqlliteRuleCode());
                } 
                if (model.getSqlliteRuleDesc() != null) {
                     jsonObject.put("sqlliteRuleDesc", model.getSqlliteRuleDesc());
                } 
                if (model.getSqlliteRuleName() != null) {
                     jsonObject.put("sqlliteRuleName", model.getSqlliteRuleName());
                } 
                if (model.getDataSetsName() != null) {
                     jsonObject.put("dataSetsName", model.getDataSetsName());
                } 
                if (model.getDelflag() != null) {
                     jsonObject.put("delflag", model.getDelflag());
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
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<WdatasetSqllite> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (WdatasetSqllite model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<WdatasetSqllite> arrayToList(JSONArray array) {
		java.util.List<WdatasetSqllite> list = new java.util.ArrayList<WdatasetSqllite>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			WdatasetSqllite model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private WdatasetSqlliteJsonFactory() {

	}

}
