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
public class SendDocApplyJsonFactory {

	public static SendDocApply jsonToObject(JSONObject jsonObject) {
            SendDocApply model = new SendDocApply();
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
		if (jsonObject.containsKey("docNo")) {
			model.setDocNo(jsonObject.getString("docNo"));
		}
		if (jsonObject.containsKey("docType")) {
			model.setDocType(jsonObject.getString("docType"));
		}
		if (jsonObject.containsKey("draftName")) {
			model.setDraftName(jsonObject.getString("draftName"));
		}
		if (jsonObject.containsKey("draftDate")) {
			model.setDraftDate(jsonObject.getDate("draftDate"));
		}
		if (jsonObject.containsKey("sendDocDate")) {
			model.setSendDocDate(jsonObject.getDate("sendDocDate"));
		}
		if (jsonObject.containsKey("serialNumber")) {
			model.setSerialNumber(jsonObject.getString("serialNumber"));
		}
		if (jsonObject.containsKey("fromCompany")) {
			model.setFromCompany(jsonObject.getString("fromCompany"));
		}
		if (jsonObject.containsKey("keywords")) {
			model.setKeywords(jsonObject.getString("keywords"));
		}
		if (jsonObject.containsKey("docToCompany")) {
			model.setDocToCompany(jsonObject.getString("docToCompany"));
		}
		if (jsonObject.containsKey("docCCCompany")) {
			model.setDocCCCompany(jsonObject.getString("docCCCompany"));
		}
		if (jsonObject.containsKey("checkOpinion")) {
			model.setCheckOpinion(jsonObject.getString("checkOpinion"));
		}
		if (jsonObject.containsKey("countersignOpinion")) {
			model.setCountersignOpinion(jsonObject.getString("countersignOpinion"));
		}
		if (jsonObject.containsKey("signAndIssueOpinion")) {
			model.setSignAndIssueOpinion(jsonObject.getString("signAndIssueOpinion"));
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

	public static JSONObject toJsonObject(SendDocApply model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getSubject() != null) {
			jsonObject.put("subject", model.getSubject());
		} 
        jsonObject.put("securityLevel", model.getSecurityLevel());
        jsonObject.put("urgencyLevel", model.getUrgencyLevel());
		if (model.getDocNo() != null) {
			jsonObject.put("docNo", model.getDocNo());
		} 
		if (model.getDocType() != null) {
			jsonObject.put("docType", model.getDocType());
		} 
		if (model.getDraftName() != null) {
			jsonObject.put("draftName", model.getDraftName());
		} 
                if (model.getDraftDate() != null) {
                      jsonObject.put("draftDate", DateUtils.getDate(model.getDraftDate()));
		      jsonObject.put("draftDate_date", DateUtils.getDate(model.getDraftDate()));
		      jsonObject.put("draftDate_datetime", DateUtils.getDateTime(model.getDraftDate()));
                }
                if (model.getSendDocDate() != null) {
                      jsonObject.put("sendDocDate", DateUtils.getDate(model.getSendDocDate()));
		      jsonObject.put("sendDocDate_date", DateUtils.getDate(model.getSendDocDate()));
		      jsonObject.put("sendDocDate_datetime", DateUtils.getDateTime(model.getSendDocDate()));
                }
		if (model.getSerialNumber() != null) {
			jsonObject.put("serialNumber", model.getSerialNumber());
		} 
		if (model.getFromCompany() != null) {
			jsonObject.put("fromCompany", model.getFromCompany());
		} 
		if (model.getKeywords() != null) {
			jsonObject.put("keywords", model.getKeywords());
		} 
		if (model.getDocToCompany() != null) {
			jsonObject.put("docToCompany", model.getDocToCompany());
		} 
		if (model.getDocCCCompany() != null) {
			jsonObject.put("docCCCompany", model.getDocCCCompany());
		} 
		if (model.getCheckOpinion() != null) {
			jsonObject.put("checkOpinion", model.getCheckOpinion());
		} 
		if (model.getCountersignOpinion() != null) {
			jsonObject.put("countersignOpinion", model.getCountersignOpinion());
		} 
		if (model.getSignAndIssueOpinion() != null) {
			jsonObject.put("signAndIssueOpinion", model.getSignAndIssueOpinion());
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


	public static ObjectNode toObjectNode(SendDocApply model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getSubject() != null) {
                     jsonObject.put("subject", model.getSubject());
                } 
                jsonObject.put("securityLevel", model.getSecurityLevel());
                jsonObject.put("urgencyLevel", model.getUrgencyLevel());
                if (model.getDocNo() != null) {
                     jsonObject.put("docNo", model.getDocNo());
                } 
                if (model.getDocType() != null) {
                     jsonObject.put("docType", model.getDocType());
                } 
                if (model.getDraftName() != null) {
                     jsonObject.put("draftName", model.getDraftName());
                } 
                if (model.getDraftDate() != null) {
                      jsonObject.put("draftDate", DateUtils.getDate(model.getDraftDate()));
		      jsonObject.put("draftDate_date", DateUtils.getDate(model.getDraftDate()));
		      jsonObject.put("draftDate_datetime", DateUtils.getDateTime(model.getDraftDate()));
                }
                if (model.getSendDocDate() != null) {
                      jsonObject.put("sendDocDate", DateUtils.getDate(model.getSendDocDate()));
		      jsonObject.put("sendDocDate_date", DateUtils.getDate(model.getSendDocDate()));
		      jsonObject.put("sendDocDate_datetime", DateUtils.getDateTime(model.getSendDocDate()));
                }
                if (model.getSerialNumber() != null) {
                     jsonObject.put("serialNumber", model.getSerialNumber());
                } 
                if (model.getFromCompany() != null) {
                     jsonObject.put("fromCompany", model.getFromCompany());
                } 
                if (model.getKeywords() != null) {
                     jsonObject.put("keywords", model.getKeywords());
                } 
                if (model.getDocToCompany() != null) {
                     jsonObject.put("docToCompany", model.getDocToCompany());
                } 
                if (model.getDocCCCompany() != null) {
                     jsonObject.put("docCCCompany", model.getDocCCCompany());
                } 
                if (model.getCheckOpinion() != null) {
                     jsonObject.put("checkOpinion", model.getCheckOpinion());
                } 
                if (model.getCountersignOpinion() != null) {
                     jsonObject.put("countersignOpinion", model.getCountersignOpinion());
                } 
                if (model.getSignAndIssueOpinion() != null) {
                     jsonObject.put("signAndIssueOpinion", model.getSignAndIssueOpinion());
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

	
	public static JSONArray listToArray(java.util.List<SendDocApply> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SendDocApply model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SendDocApply> arrayToList(JSONArray array) {
		java.util.List<SendDocApply> list = new java.util.ArrayList<SendDocApply>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SendDocApply model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private SendDocApplyJsonFactory() {

	}

}
