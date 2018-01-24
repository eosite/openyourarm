package com.glaf.convert.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.convert.domain.ConvertElemTmpl;
import com.glaf.core.util.DateUtils;


/**
 * 
 * JSON工厂类
 *
 */
public class ConvertElemTmplJsonFactory {

	public static ConvertElemTmpl jsonToObject(JSONObject jsonObject) {
            ConvertElemTmpl model = new ConvertElemTmpl();
            if (jsonObject.containsKey("cvtElemId")) {
		    model.setCvtElemId(jsonObject.getLong("cvtElemId"));
		}
		if (jsonObject.containsKey("cvtId")) {
			model.setCvtId(jsonObject.getLong("cvtId"));
		}
		if (jsonObject.containsKey("elemType")) {
			model.setElemType(jsonObject.getString("elemType"));
		}
		if (jsonObject.containsKey("elemName")) {
			model.setElemName(jsonObject.getString("elemName"));
		}
		if (jsonObject.containsKey("elemId")) {
			model.setElemId(jsonObject.getString("elemId"));
		}
		if (jsonObject.containsKey("dType")) {
			model.setDType(jsonObject.getString("dType"));
		}
		if (jsonObject.containsKey("len")) {
			model.setLen(jsonObject.getInteger("len"));
		}
		if (jsonObject.containsKey("digit")) {
			model.setDigit(jsonObject.getInteger("digit"));
		}
		if (jsonObject.containsKey("defaultVal")) {
			model.setDefaultVal(jsonObject.getString("defaultVal"));
		}
		if (jsonObject.containsKey("rowIndex")) {
			model.setRowIndex(jsonObject.getInteger("rowIndex"));
		}
		if (jsonObject.containsKey("colIndex")) {
			model.setColIndex(jsonObject.getInteger("colIndex"));
		}
		if (jsonObject.containsKey("readOnly")) {
			model.setReadOnly(jsonObject.getString("readOnly"));
		}
		if (jsonObject.containsKey("print")) {
			model.setPrint(jsonObject.getString("print"));
		}
		if (jsonObject.containsKey("display")) {
			model.setDisplay(jsonObject.getString("display"));
		}
		if (jsonObject.containsKey("isMustFill")) {
			model.setIsMustFill(jsonObject.getString("isMustFill"));
		}
		if (jsonObject.containsKey("isDataOnly")) {
			model.setIsDataOnly(jsonObject.getString("isDataOnly"));
		}
		if (jsonObject.containsKey("direction")) {
			model.setDirection(jsonObject.getString("direction"));
		}
		if (jsonObject.containsKey("vararea")) {
			model.setVararea(jsonObject.getString("vararea"));
		}
		if (jsonObject.containsKey("endRowIndex")) {
			model.setEndRowIndex(jsonObject.getInteger("endRowIndex"));
		}
		if (jsonObject.containsKey("endColIndex")) {
			model.setEndColIndex(jsonObject.getInteger("endColIndex"));
		}
		if (jsonObject.containsKey("createDatetime")) {
			model.setCreateDatetime(jsonObject.getDate("createDatetime"));
		}
		if (jsonObject.containsKey("modifyDatetime")) {
			model.setModifyDatetime(jsonObject.getDate("modifyDatetime"));
		}
		if (jsonObject.containsKey("repinfoListId")) {
			model.setRepinfoListId(jsonObject.getString("repinfoListId"));
		}

            return model;
	}

	public static JSONObject toJsonObject(ConvertElemTmpl model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("cvtElemId", model.getCvtElemId());
		jsonObject.put("_cvtElemId_", model.getCvtElemId());
        jsonObject.put("cvtId", model.getCvtId());
		if (model.getElemType() != null) {
			jsonObject.put("elemType", model.getElemType());
		} 
		if (model.getElemName() != null) {
			jsonObject.put("elemName", model.getElemName());
		} 
		if (model.getElemId() != null) {
			jsonObject.put("elemId", model.getElemId());
		} 
		if (model.getDType() != null) {
			jsonObject.put("dType", model.getDType());
		} 
        jsonObject.put("len", model.getLen());
        jsonObject.put("digit", model.getDigit());
		if (model.getDefaultVal() != null) {
			jsonObject.put("defaultVal", model.getDefaultVal());
		} 
        jsonObject.put("rowIndex", model.getRowIndex());
        jsonObject.put("colIndex", model.getColIndex());
		if (model.getReadOnly() != null) {
			jsonObject.put("readOnly", model.getReadOnly());
		} 
		if (model.getPrint() != null) {
			jsonObject.put("print", model.getPrint());
		} 
		if (model.getDisplay() != null) {
			jsonObject.put("display", model.getDisplay());
		} 
		if (model.getIsMustFill() != null) {
			jsonObject.put("isMustFill", model.getIsMustFill());
		} 
		if (model.getIsDataOnly() != null) {
			jsonObject.put("isDataOnly", model.getIsDataOnly());
		} 
		if (model.getDirection() != null) {
			jsonObject.put("direction", model.getDirection());
		} 
		if (model.getVararea() != null) {
			jsonObject.put("vararea", model.getVararea());
		} 
        jsonObject.put("endRowIndex", model.getEndRowIndex());
        jsonObject.put("endColIndex", model.getEndColIndex());
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
		if (model.getRepinfoListId() != null) {
			jsonObject.put("repinfoListId", model.getRepinfoListId());
		} 
		return jsonObject;
	}


	public static ObjectNode toObjectNode(ConvertElemTmpl model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("cvtElemId", model.getCvtElemId());
		jsonObject.put("_cvtElemId_", model.getCvtElemId());
                jsonObject.put("cvtId", model.getCvtId());
                if (model.getElemType() != null) {
                     jsonObject.put("elemType", model.getElemType());
                } 
                if (model.getElemName() != null) {
                     jsonObject.put("elemName", model.getElemName());
                } 
                if (model.getElemId() != null) {
                     jsonObject.put("elemId", model.getElemId());
                } 
                if (model.getDType() != null) {
                     jsonObject.put("dType", model.getDType());
                } 
                jsonObject.put("len", model.getLen());
                jsonObject.put("digit", model.getDigit());
                if (model.getDefaultVal() != null) {
                     jsonObject.put("defaultVal", model.getDefaultVal());
                } 
                jsonObject.put("rowIndex", model.getRowIndex());
                jsonObject.put("colIndex", model.getColIndex());
                if (model.getReadOnly() != null) {
                     jsonObject.put("readOnly", model.getReadOnly());
                } 
                if (model.getPrint() != null) {
                     jsonObject.put("print", model.getPrint());
                } 
                if (model.getDisplay() != null) {
                     jsonObject.put("display", model.getDisplay());
                } 
                if (model.getIsMustFill() != null) {
                     jsonObject.put("isMustFill", model.getIsMustFill());
                } 
                if (model.getIsDataOnly() != null) {
                     jsonObject.put("isDataOnly", model.getIsDataOnly());
                } 
                if (model.getDirection() != null) {
                     jsonObject.put("direction", model.getDirection());
                } 
                if (model.getVararea() != null) {
                     jsonObject.put("vararea", model.getVararea());
                } 
                jsonObject.put("endRowIndex", model.getEndRowIndex());
                jsonObject.put("endColIndex", model.getEndColIndex());
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
                if (model.getRepinfoListId() != null) {
                     jsonObject.put("repinfoListId", model.getRepinfoListId());
                } 
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<ConvertElemTmpl> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ConvertElemTmpl model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ConvertElemTmpl> arrayToList(JSONArray array) {
		java.util.List<ConvertElemTmpl> list = new java.util.ArrayList<ConvertElemTmpl>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ConvertElemTmpl model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private ConvertElemTmplJsonFactory() {

	}

}
