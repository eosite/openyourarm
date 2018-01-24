package com.glaf.convert.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.convert.domain.ConvertPageTmpl;
import com.glaf.core.util.DateUtils;


/**
 * 
 * JSON工厂类
 *
 */
public class ConvertPageTmplJsonFactory {

	public static ConvertPageTmpl jsonToObject(JSONObject jsonObject) {
            ConvertPageTmpl model = new ConvertPageTmpl();
            if (jsonObject.containsKey("cvtId")) {
		    model.setCvtId(jsonObject.getLong("cvtId"));
		}
		if (jsonObject.containsKey("fileDotFieldId")) {
			model.setFileDotFieldId(jsonObject.getString("fileDotFieldId"));
		}
		if (jsonObject.containsKey("cvtType")) {
			model.setCvtType(jsonObject.getString("cvtType"));
		}
		if (jsonObject.containsKey("cvtSrcFileName")) {
			model.setCvtSrcFileName(jsonObject.getString("cvtSrcFileName"));
		}
		if (jsonObject.containsKey("cvtSrcName")) {
			model.setCvtSrcName(jsonObject.getString("cvtSrcName"));
		}
		if (jsonObject.containsKey("cvtDesExt")) {
			model.setCvtDesExt(jsonObject.getString("cvtDesExt"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("effectiveFlag")) {
			model.setEffectiveFlag(jsonObject.getInteger("effectiveFlag"));
		}
		if (jsonObject.containsKey("cvtStatus")) {
			model.setCvtStatus(jsonObject.getInteger("cvtStatus"));
		}
		if (jsonObject.containsKey("createDatetime")) {
			model.setCreateDatetime(jsonObject.getDate("createDatetime"));
		}
		if (jsonObject.containsKey("modifyDatetime")) {
			model.setModifyDatetime(jsonObject.getDate("modifyDatetime"));
		}

            return model;
	}

	public static JSONObject toJsonObject(ConvertPageTmpl model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("cvtId", model.getCvtId());
		jsonObject.put("_cvtId_", model.getCvtId());
		if (model.getFileDotFieldId() != null) {
			jsonObject.put("fileDotFieldId", model.getFileDotFieldId());
		} 
		if (model.getCvtType() != null) {
			jsonObject.put("cvtType", model.getCvtType());
		} 
		if (model.getCvtSrcContent() != null) {
			jsonObject.put("cvtSrcContent", model.getCvtSrcContent());
		} 
		if (model.getCvtSrcFileName() != null) {
			jsonObject.put("cvtSrcFileName", model.getCvtSrcFileName());
		} 
		if (model.getCvtSrcName() != null) {
			jsonObject.put("cvtSrcName", model.getCvtSrcName());
		} 
		if (model.getCvtXmlContent() != null) {
			jsonObject.put("cvtXmlContent", model.getCvtXmlContent());
		} 
		if (model.getCvtDesContent() != null) {
			jsonObject.put("cvtDesContent", model.getCvtDesContent());
		} 
		if (model.getCvtDesExt() != null) {
			jsonObject.put("cvtDesExt", model.getCvtDesExt());
		} 
        jsonObject.put("status", model.getStatus());
        jsonObject.put("effectiveFlag", model.getEffectiveFlag());
        jsonObject.put("cvtStatus", model.getCvtStatus());
                if (model.getCreateDatetime() != null) {
                      jsonObject.put("createDatetime", DateUtils.getDate(model.getCreateDatetime()));
		      jsonObject.put("createDatetime_date", DateUtils.getDate(model.getCreateDatetime()));
		      jsonObject.put("createDatetime_datetime", DateUtils.getDateTime(model.getCreateDatetime()));
                }
                if (model.getModifyDatetime() != null) {
                      jsonObject.put("modifyDatetime", DateUtils.getDate(model.getModifyDatetime()));
		      jsonObject.put("modifyDatetime_date", DateUtils.getDate(model.getModifyDatetime()));
		      jsonObject.put("modifyDatetime_datetime", DateUtils.getDateTime(model.getModifyDatetime()));
                }
		return jsonObject;
	}


	public static ObjectNode toObjectNode(ConvertPageTmpl model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("cvtId", model.getCvtId());
		jsonObject.put("_cvtId_", model.getCvtId());
                if (model.getFileDotFieldId() != null) {
                     jsonObject.put("fileDotFieldId", model.getFileDotFieldId());
                } 
                if (model.getCvtType() != null) {
                     jsonObject.put("cvtType", model.getCvtType());
                } 
                if (model.getCvtSrcContent() != null) {
                     jsonObject.put("cvtSrcContent", model.getCvtSrcContent());
                } 
                if (model.getCvtSrcFileName() != null) {
                     jsonObject.put("cvtSrcFileName", model.getCvtSrcFileName());
                } 
                if (model.getCvtSrcName() != null) {
                     jsonObject.put("cvtSrcName", model.getCvtSrcName());
                } 
                if (model.getCvtXmlContent() != null) {
                     jsonObject.put("cvtXmlContent", model.getCvtXmlContent());
                } 
                if (model.getCvtDesContent() != null) {
                     jsonObject.put("cvtDesContent", model.getCvtDesContent());
                } 
                if (model.getCvtDesExt() != null) {
                     jsonObject.put("cvtDesExt", model.getCvtDesExt());
                } 
                jsonObject.put("status", model.getStatus());
                jsonObject.put("effectiveFlag", model.getEffectiveFlag());
                jsonObject.put("cvtStatus", model.getCvtStatus());
                if (model.getCreateDatetime() != null) {
                      jsonObject.put("createDatetime", DateUtils.getDate(model.getCreateDatetime()));
		      jsonObject.put("createDatetime_date", DateUtils.getDate(model.getCreateDatetime()));
		      jsonObject.put("createDatetime_datetime", DateUtils.getDateTime(model.getCreateDatetime()));
                }
                if (model.getModifyDatetime() != null) {
                      jsonObject.put("modifyDatetime", DateUtils.getDate(model.getModifyDatetime()));
		      jsonObject.put("modifyDatetime_date", DateUtils.getDate(model.getModifyDatetime()));
		      jsonObject.put("modifyDatetime_datetime", DateUtils.getDateTime(model.getModifyDatetime()));
                }
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<ConvertPageTmpl> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ConvertPageTmpl model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ConvertPageTmpl> arrayToList(JSONArray array) {
		java.util.List<ConvertPageTmpl> list = new java.util.ArrayList<ConvertPageTmpl>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ConvertPageTmpl model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private ConvertPageTmplJsonFactory() {

	}

}
