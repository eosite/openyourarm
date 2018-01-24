package com.glaf.oa.doc.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.util.DateUtils;
import com.glaf.oa.doc.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class ReceiveDocApplyJsonFactory {

	public static ReceiveDocApply jsonToObject(JSONObject jsonObject) {
            ReceiveDocApply model = new ReceiveDocApply();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getInteger("id"));
		}
		if (jsonObject.containsKey("subject")) {
			model.setSubject(jsonObject.getString("subject"));
		}
		if (jsonObject.containsKey("securityLevel")) {
			model.setSecurityLevel(jsonObject.getInteger("securityLevel"));
		}
		if (jsonObject.containsKey("urgencyLevel")) {
			model.setUrgencyLevel(jsonObject.getInteger("urgencyLevel"));
		}
		if (jsonObject.containsKey("receiveDocTime")) {
			model.setReceiveDocTime(jsonObject.getDate("receiveDocTime"));
		}
		if (jsonObject.containsKey("docType")) {
			model.setDocType(jsonObject.getString("docType"));
		}
		if (jsonObject.containsKey("fromCompany")) {
			model.setFromCompany(jsonObject.getString("fromCompany"));
		}
		if (jsonObject.containsKey("serialNumber")) {
			model.setSerialNumber(jsonObject.getString("serialNumber"));
		}
		if (jsonObject.containsKey("fromDocNo")) {
			model.setFromDocNo(jsonObject.getString("fromDocNo"));
		}
		if (jsonObject.containsKey("receiveDocNo")) {
			model.setReceiveDocNo(jsonObject.getString("receiveDocNo"));
		}
		if (jsonObject.containsKey("distributeCompany")) {
			model.setDistributeCompany(jsonObject.getString("distributeCompany"));
		}
		if (jsonObject.containsKey("nibanOption")) {
			model.setNibanOption(jsonObject.getString("nibanOption"));
		}
		if (jsonObject.containsKey("leadOption")) {
			model.setLeadOption(jsonObject.getString("leadOption"));
		}
		if (jsonObject.containsKey("chengbanOption")) {
			model.setChengbanOption(jsonObject.getString("chengbanOption"));
		}
		if (jsonObject.containsKey("remark")) {
			model.setRemark(jsonObject.getString("remark"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("updateDate")) {
			model.setUpdateDate(jsonObject.getDate("updateDate"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}

            return model;
	}

	public static JSONObject toJsonObject(ReceiveDocApply model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getSubject() != null) {
			jsonObject.put("subject", model.getSubject());
		} 
        jsonObject.put("securityLevel", model.getSecurityLevel());
        jsonObject.put("urgencyLevel", model.getUrgencyLevel());
                if (model.getReceiveDocTime() != null) {
                      jsonObject.put("receiveDocTime", DateUtils.getDate(model.getReceiveDocTime()));
		      jsonObject.put("receiveDocTime_date", DateUtils.getDate(model.getReceiveDocTime()));
		      jsonObject.put("receiveDocTime_datetime", DateUtils.getDateTime(model.getReceiveDocTime()));
                }
		if (model.getDocType() != null) {
			jsonObject.put("docType", model.getDocType());
		} 
		if (model.getFromCompany() != null) {
			jsonObject.put("fromCompany", model.getFromCompany());
		} 
		if (model.getSerialNumber() != null) {
			jsonObject.put("serialNumber", model.getSerialNumber());
		} 
		if (model.getFromDocNo() != null) {
			jsonObject.put("fromDocNo", model.getFromDocNo());
		} 
		if (model.getReceiveDocNo() != null) {
			jsonObject.put("receiveDocNo", model.getReceiveDocNo());
		} 
		if (model.getDistributeCompany() != null) {
			jsonObject.put("distributeCompany", model.getDistributeCompany());
		} 
		if (model.getNibanOption() != null) {
			jsonObject.put("nibanOption", model.getNibanOption());
		} 
		if (model.getLeadOption() != null) {
			jsonObject.put("leadOption", model.getLeadOption());
		} 
		if (model.getChengbanOption() != null) {
			jsonObject.put("chengbanOption", model.getChengbanOption());
		} 
		if (model.getRemark() != null) {
			jsonObject.put("remark", model.getRemark());
		} 
        jsonObject.put("status", model.getStatus());
                if (model.getCreateDate() != null) {
                      jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
                }
                if (model.getUpdateDate() != null) {
                      jsonObject.put("updateDate", DateUtils.getDate(model.getUpdateDate()));
		      jsonObject.put("updateDate_date", DateUtils.getDate(model.getUpdateDate()));
		      jsonObject.put("updateDate_datetime", DateUtils.getDateTime(model.getUpdateDate()));
                }
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		} 
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		} 
		return jsonObject;
	}


	public static ObjectNode toObjectNode(ReceiveDocApply model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getSubject() != null) {
                     jsonObject.put("subject", model.getSubject());
                } 
                jsonObject.put("securityLevel", model.getSecurityLevel());
                jsonObject.put("urgencyLevel", model.getUrgencyLevel());
                if (model.getReceiveDocTime() != null) {
                      jsonObject.put("receiveDocTime", DateUtils.getDate(model.getReceiveDocTime()));
		      jsonObject.put("receiveDocTime_date", DateUtils.getDate(model.getReceiveDocTime()));
		      jsonObject.put("receiveDocTime_datetime", DateUtils.getDateTime(model.getReceiveDocTime()));
                }
                if (model.getDocType() != null) {
                     jsonObject.put("docType", model.getDocType());
                } 
                if (model.getFromCompany() != null) {
                     jsonObject.put("fromCompany", model.getFromCompany());
                } 
                if (model.getSerialNumber() != null) {
                     jsonObject.put("serialNumber", model.getSerialNumber());
                } 
                if (model.getFromDocNo() != null) {
                     jsonObject.put("fromDocNo", model.getFromDocNo());
                } 
                if (model.getReceiveDocNo() != null) {
                     jsonObject.put("receiveDocNo", model.getReceiveDocNo());
                } 
                if (model.getDistributeCompany() != null) {
                     jsonObject.put("distributeCompany", model.getDistributeCompany());
                } 
                if (model.getNibanOption() != null) {
                     jsonObject.put("nibanOption", model.getNibanOption());
                } 
                if (model.getLeadOption() != null) {
                     jsonObject.put("leadOption", model.getLeadOption());
                } 
                if (model.getChengbanOption() != null) {
                     jsonObject.put("chengbanOption", model.getChengbanOption());
                } 
                if (model.getRemark() != null) {
                     jsonObject.put("remark", model.getRemark());
                } 
                jsonObject.put("status", model.getStatus());
                if (model.getCreateDate() != null) {
                      jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
                }
                if (model.getUpdateDate() != null) {
                      jsonObject.put("updateDate", DateUtils.getDate(model.getUpdateDate()));
		      jsonObject.put("updateDate_date", DateUtils.getDate(model.getUpdateDate()));
		      jsonObject.put("updateDate_datetime", DateUtils.getDateTime(model.getUpdateDate()));
                }
                if (model.getCreateBy() != null) {
                     jsonObject.put("createBy", model.getCreateBy());
                } 
                if (model.getUpdateBy() != null) {
                     jsonObject.put("updateBy", model.getUpdateBy());
                } 
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<ReceiveDocApply> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ReceiveDocApply model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ReceiveDocApply> arrayToList(JSONArray array) {
		java.util.List<ReceiveDocApply> list = new java.util.ArrayList<ReceiveDocApply>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ReceiveDocApply model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private ReceiveDocApplyJsonFactory() {

	}

}
