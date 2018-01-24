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
public class ReportTmpColMappingJsonFactory {

	public static ReportTmpColMapping jsonToObject(JSONObject jsonObject) {
            ReportTmpColMapping model = new ReportTmpColMapping();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("dataSetMappingId")) {
			model.setDataSetMappingId(jsonObject.getString("dataSetMappingId"));
		}
		if (jsonObject.containsKey("colCode")) {
			model.setColCode(jsonObject.getString("colCode"));
		}
		if (jsonObject.containsKey("colName")) {
			model.setColName(jsonObject.getString("colName"));
		}
		if (jsonObject.containsKey("colTitle")) {
			model.setColTitle(jsonObject.getString("colTitle"));
		}
		if (jsonObject.containsKey("colDtype")) {
			model.setColDtype(jsonObject.getString("colDtype"));
		}
		if (jsonObject.containsKey("colMappingCode")) {
			model.setColMappingCode(jsonObject.getString("colMappingCode"));
		}
		if (jsonObject.containsKey("colMappingName")) {
			model.setColMappingName(jsonObject.getString("colMappingName"));
		}
		if (jsonObject.containsKey("colMappingTitle")) {
			model.setColMappingTitle(jsonObject.getString("colMappingTitle"));
		}
		if (jsonObject.containsKey("colMappingDtype")) {
			model.setColMappingDtype(jsonObject.getString("colMappingDtype"));
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

	public static JSONObject toJsonObject(ReportTmpColMapping model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getDataSetMappingId() != null) {
			jsonObject.put("dataSetMappingId", model.getDataSetMappingId());
		} 
		if (model.getColCode() != null) {
			jsonObject.put("colCode", model.getColCode());
		} 
		if (model.getColName() != null) {
			jsonObject.put("colName", model.getColName());
		} 
		if (model.getColTitle() != null) {
			jsonObject.put("colTitle", model.getColTitle());
		} 
		if (model.getColDtype() != null) {
			jsonObject.put("colDtype", model.getColDtype());
		} 
		if (model.getColMappingCode() != null) {
			jsonObject.put("colMappingCode", model.getColMappingCode());
		} 
		if (model.getColMappingName() != null) {
			jsonObject.put("colMappingName", model.getColMappingName());
		} 
		if (model.getColMappingTitle() != null) {
			jsonObject.put("colMappingTitle", model.getColMappingTitle());
		} 
		if (model.getColMappingDtype() != null) {
			jsonObject.put("colMappingDtype", model.getColMappingDtype());
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


	public static ObjectNode toObjectNode(ReportTmpColMapping model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getDataSetMappingId() != null) {
                     jsonObject.put("dataSetMappingId", model.getDataSetMappingId());
                } 
                if (model.getColCode() != null) {
                     jsonObject.put("colCode", model.getColCode());
                } 
                if (model.getColName() != null) {
                     jsonObject.put("colName", model.getColName());
                } 
                if (model.getColTitle() != null) {
                     jsonObject.put("colTitle", model.getColTitle());
                } 
                if (model.getColDtype() != null) {
                     jsonObject.put("colDtype", model.getColDtype());
                } 
                if (model.getColMappingCode() != null) {
                     jsonObject.put("colMappingCode", model.getColMappingCode());
                } 
                if (model.getColMappingName() != null) {
                     jsonObject.put("colMappingName", model.getColMappingName());
                } 
                if (model.getColMappingTitle() != null) {
                     jsonObject.put("colMappingTitle", model.getColMappingTitle());
                } 
                if (model.getColMappingDtype() != null) {
                     jsonObject.put("colMappingDtype", model.getColMappingDtype());
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

	
	public static JSONArray listToArray(java.util.List<ReportTmpColMapping> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ReportTmpColMapping model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ReportTmpColMapping> arrayToList(JSONArray array) {
		java.util.List<ReportTmpColMapping> list = new java.util.ArrayList<ReportTmpColMapping>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ReportTmpColMapping model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private ReportTmpColMappingJsonFactory() {

	}

}
