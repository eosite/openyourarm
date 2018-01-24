package com.glaf.report.core.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.report.core.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class ReportTmpDataSetMappingJsonFactory {

	public static ReportTmpDataSetMapping jsonToObject(JSONObject jsonObject) {
            ReportTmpDataSetMapping model = new ReportTmpDataSetMapping();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("tmpMappingId")) {
			model.setTmpMappingId(jsonObject.getString("tmpMappingId"));
		}
		if (jsonObject.containsKey("templateId")) {
			model.setTemplateId(jsonObject.getString("templateId"));
		}
		if (jsonObject.containsKey("dataSetCode")) {
			model.setDataSetCode(jsonObject.getString("dataSetCode"));
		}
		if (jsonObject.containsKey("dataSetName")) {
			model.setDataSetName(jsonObject.getString("dataSetName"));
		}
		if (jsonObject.containsKey("mappingDataSetId")) {
			model.setMappingDataSetId(jsonObject.getString("mappingDataSetId"));
		}
		if (jsonObject.containsKey("mappingDataSetCode")) {
			model.setMappingDataSetCode(jsonObject.getString("mappingDataSetCode"));
		}
		if (jsonObject.containsKey("mappingDataSetName")) {
			model.setMappingDataSetName(jsonObject.getString("mappingDataSetName"));
		}
		if (jsonObject.containsKey("creator")) {
			model.setCreator(jsonObject.getString("creator"));
		}
		if (jsonObject.containsKey("createDatetime")) {
			model.setCreateDatetime(jsonObject.getDate("createDatetime"));
		}
		if (jsonObject.containsKey("modifier")) {
			model.setModifier(jsonObject.getString("modifier"));
		}
		if (jsonObject.containsKey("modifyDatetime")) {
			model.setModifyDatetime(jsonObject.getDate("modifyDatetime"));
		}

            return model;
	}

	public static JSONObject toJsonObject(ReportTmpDataSetMapping model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTmpMappingId() != null) {
			jsonObject.put("tmpMappingId", model.getTmpMappingId());
		} 
		if (model.getTemplateId() != null) {
			jsonObject.put("templateId", model.getTemplateId());
		} 
		if (model.getDataSetCode() != null) {
			jsonObject.put("dataSetCode", model.getDataSetCode());
		} 
		if (model.getDataSetName() != null) {
			jsonObject.put("dataSetName", model.getDataSetName());
		} 
		if (model.getMappingDataSetId() != null) {
			jsonObject.put("mappingDataSetId", model.getMappingDataSetId());
		} 
		if (model.getMappingDataSetCode() != null) {
			jsonObject.put("mappingDataSetCode", model.getMappingDataSetCode());
		} 
		if (model.getMappingDataSetName() != null) {
			jsonObject.put("mappingDataSetName", model.getMappingDataSetName());
		} 
		if (model.getCreator() != null) {
			jsonObject.put("creator", model.getCreator());
		} 
                if (model.getCreateDatetime() != null) {
                      jsonObject.put("createDatetime", DateUtils.getDate(model.getCreateDatetime()));
		      jsonObject.put("createDatetime_date", DateUtils.getDate(model.getCreateDatetime()));
		      jsonObject.put("createDatetime_datetime", DateUtils.getDateTime(model.getCreateDatetime()));
                }
		if (model.getModifier() != null) {
			jsonObject.put("modifier", model.getModifier());
		} 
                if (model.getModifyDatetime() != null) {
                      jsonObject.put("modifyDatetime", DateUtils.getDate(model.getModifyDatetime()));
		      jsonObject.put("modifyDatetime_date", DateUtils.getDate(model.getModifyDatetime()));
		      jsonObject.put("modifyDatetime_datetime", DateUtils.getDateTime(model.getModifyDatetime()));
                }
		return jsonObject;
	}


	public static ObjectNode toObjectNode(ReportTmpDataSetMapping model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getTmpMappingId() != null) {
                     jsonObject.put("tmpMappingId", model.getTmpMappingId());
                } 
                if (model.getTemplateId() != null) {
                     jsonObject.put("templateId", model.getTemplateId());
                } 
                if (model.getDataSetCode() != null) {
                     jsonObject.put("dataSetCode", model.getDataSetCode());
                } 
                if (model.getDataSetName() != null) {
                     jsonObject.put("dataSetName", model.getDataSetName());
                } 
                if (model.getMappingDataSetId() != null) {
                     jsonObject.put("mappingDataSetId", model.getMappingDataSetId());
                } 
                if (model.getMappingDataSetCode() != null) {
                     jsonObject.put("mappingDataSetCode", model.getMappingDataSetCode());
                } 
                if (model.getMappingDataSetName() != null) {
                     jsonObject.put("mappingDataSetName", model.getMappingDataSetName());
                } 
                if (model.getCreator() != null) {
                     jsonObject.put("creator", model.getCreator());
                } 
                if (model.getCreateDatetime() != null) {
                      jsonObject.put("createDatetime", DateUtils.getDate(model.getCreateDatetime()));
		      jsonObject.put("createDatetime_date", DateUtils.getDate(model.getCreateDatetime()));
		      jsonObject.put("createDatetime_datetime", DateUtils.getDateTime(model.getCreateDatetime()));
                }
                if (model.getModifier() != null) {
                     jsonObject.put("modifier", model.getModifier());
                } 
                if (model.getModifyDatetime() != null) {
                      jsonObject.put("modifyDatetime", DateUtils.getDate(model.getModifyDatetime()));
		      jsonObject.put("modifyDatetime_date", DateUtils.getDate(model.getModifyDatetime()));
		      jsonObject.put("modifyDatetime_datetime", DateUtils.getDateTime(model.getModifyDatetime()));
                }
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<ReportTmpDataSetMapping> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ReportTmpDataSetMapping model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ReportTmpDataSetMapping> arrayToList(JSONArray array) {
		java.util.List<ReportTmpDataSetMapping> list = new java.util.ArrayList<ReportTmpDataSetMapping>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ReportTmpDataSetMapping model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private ReportTmpDataSetMappingJsonFactory() {

	}

}
