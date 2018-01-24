package com.glaf.etl.util;

import java.util.Date;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.etl.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class EtlTransferTaskJsonFactory {

	public static EtlTransferTask jsonToObject(JSONObject jsonObject) {
            EtlTransferTask model = new EtlTransferTask();
            if (jsonObject.containsKey("id_")) {
		    model.setId_(jsonObject.getString("id_"));
		}
		if (jsonObject.containsKey("transId_")) {
			model.setTransId_(jsonObject.getString("transId_"));
		}
		if (jsonObject.containsKey("taskName_")) {
			model.setTaskName_(jsonObject.getString("taskName_"));
		}
		if (jsonObject.containsKey("taskDesc_")) {
			model.setTaskDesc_(jsonObject.getString("taskDesc_"));
		}
		if (jsonObject.containsKey("commitInterval_")) {
			model.setCommitInterval_(jsonObject.getInteger("commitInterval_"));
		}
		if (jsonObject.containsKey("rollbackTransFlag_")) {
			model.setRollbackTransFlag_(jsonObject.getInteger("rollbackTransFlag_"));
		}
		if (jsonObject.containsKey("retryFlag_")) {
			model.setRetryFlag_(jsonObject.getInteger("retryFlag_"));
		}
		if (jsonObject.containsKey("retryTimes_")) {
			model.setRetryTimes_(jsonObject.getInteger("retryTimes_"));
		}
		if (jsonObject.containsKey("onPrePostErrorStop_")) {
			model.setOnPrePostErrorStop_(jsonObject.getInteger("onPrePostErrorStop_"));
		}
		if (jsonObject.containsKey("sendMailFlag_")) {
			model.setSendMailFlag_(jsonObject.getInteger("sendMailFlag_"));
		}
		if (jsonObject.containsKey("emailAddress_")) {
			model.setEmailAddress_(jsonObject.getString("emailAddress_"));
		}
		if (jsonObject.containsKey("createBy_")) {
			model.setCreateBy_(jsonObject.getString("createBy_"));
		}
		if (jsonObject.containsKey("createTime_")) {
			model.setCreateTime_(jsonObject.getTimestamp("updateTime_"));
		}
		if (jsonObject.containsKey("updateBy_")) {
			model.setUpdateBy_(jsonObject.getString("updateBy_"));
		}
		if (jsonObject.containsKey("updateTime_")) {
			model.setUpdateTime_(jsonObject.getTimestamp("updateTime_"));
		}
		if (jsonObject.containsKey("deleteFlag_")) {
			model.setDeleteFlag_(jsonObject.getInteger("deleteFlag_"));
		}
		if (jsonObject.containsKey("lastStartTime_")) {
			String parsePatterns[] = { "yyyy-MM-dd+HH:mm:ss", "yyyy-MM-dd" };
			Date date = DateUtils.parseDate(jsonObject.getString("lastStartTime_"),parsePatterns);
			model.setLastStartTime_(date);
		}
		if (jsonObject.containsKey("lastEndTime_")) {
			String parsePatterns[] = { "yyyy-MM-dd+HH:mm:ss", "yyyy-MM-dd" };
			Date date = DateUtils.parseDate(jsonObject.getString("lastEndTime_"),parsePatterns);
			model.setLastEndTime_(date);
		}
		if (jsonObject.containsKey("succeed_")) {
			model.setSucceed_(jsonObject.getInteger("succeed_"));
		}
		if (jsonObject.containsKey("locked_")) {
			model.setLocked_(jsonObject.getInteger("locked_"));
		}
		if (jsonObject.containsKey("errorStopAutoRun_")) {
			model.setErrorStopAutoRun_(jsonObject.getInteger("errorStopAutoRun_"));
		}

            return model;
	}

	public static JSONObject toJsonObject(EtlTransferTask model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id_", model.getId_());
		jsonObject.put("_id__", model.getId_());
		if (model.getTransId_() != null) {
			jsonObject.put("transId_", model.getTransId_());
		} 
		if (model.getTaskName_() != null) {
			jsonObject.put("taskName_", model.getTaskName_());
		} 
		if (model.getTaskDesc_() != null) {
			jsonObject.put("taskDesc_", model.getTaskDesc_());
		} 
        jsonObject.put("commitInterval_", model.getCommitInterval_());
        jsonObject.put("rollbackTransFlag_", model.getRollbackTransFlag_());
        jsonObject.put("retryFlag_", model.getRetryFlag_());
        jsonObject.put("retryTimes_", model.getRetryTimes_());
        jsonObject.put("onPrePostErrorStop_", model.getOnPrePostErrorStop_());
        jsonObject.put("sendMailFlag_", model.getSendMailFlag_());
		if (model.getEmailAddress_() != null) {
			jsonObject.put("emailAddress_", model.getEmailAddress_());
		} 
		if (model.getCreateBy_() != null) {
			jsonObject.put("createBy_", model.getCreateBy_());
		} 
                if (model.getCreateTime_() != null) {
                      jsonObject.put("createTime_", DateUtils.getDateTime(model.getCreateTime_()));
		      jsonObject.put("createTime__date", DateUtils.getDate(model.getCreateTime_()));
		      jsonObject.put("createTime__datetime", DateUtils.getDateTime(model.getCreateTime_()));
                }
		if (model.getUpdateBy_() != null) {
			jsonObject.put("updateBy_", model.getUpdateBy_());
		} 
                if (model.getUpdateTime_() != null) {
                      jsonObject.put("updateTime_", DateUtils.getDateTime(model.getUpdateTime_()));
		      jsonObject.put("updateTime__date", DateUtils.getDate(model.getUpdateTime_()));
		      jsonObject.put("updateTime__datetime", DateUtils.getDateTime(model.getUpdateTime_()));
                }
        jsonObject.put("deleteFlag_", model.getDeleteFlag_());
                if (model.getLastStartTime_() != null) {
                      jsonObject.put("lastStartTime_", DateUtils.getDateTime(model.getLastStartTime_()));
		      jsonObject.put("lastStartTime__date", DateUtils.getDate(model.getLastStartTime_()));
		      jsonObject.put("lastStartTime__datetime", DateUtils.getDateTime(model.getLastStartTime_()));
                }
                if (model.getLastEndTime_() != null) {
                      jsonObject.put("lastEndTime_", DateUtils.getDateTime(model.getLastEndTime_()));
		      jsonObject.put("lastEndTime__date", DateUtils.getDate(model.getLastEndTime_()));
		      jsonObject.put("lastEndTime__datetime", DateUtils.getDateTime(model.getLastEndTime_()));
                }
        jsonObject.put("succeed_", model.getSucceed_());
        jsonObject.put("locked_", model.getLocked_());
        jsonObject.put("errorStopAutoRun_", model.getErrorStopAutoRun_());
		return jsonObject;
	}


	public static ObjectNode toObjectNode(EtlTransferTask model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id_", model.getId_());
		jsonObject.put("_id__", model.getId_());
                if (model.getTransId_() != null) {
                     jsonObject.put("transId_", model.getTransId_());
                } 
                if (model.getTaskName_() != null) {
                     jsonObject.put("taskName_", model.getTaskName_());
                } 
                if (model.getTaskDesc_() != null) {
                     jsonObject.put("taskDesc_", model.getTaskDesc_());
                } 
                jsonObject.put("commitInterval_", model.getCommitInterval_());
                jsonObject.put("rollbackTransFlag_", model.getRollbackTransFlag_());
                jsonObject.put("retryFlag_", model.getRetryFlag_());
                jsonObject.put("retryTimes_", model.getRetryTimes_());
                jsonObject.put("onPrePostErrorStop_", model.getOnPrePostErrorStop_());
                jsonObject.put("sendMailFlag_", model.getSendMailFlag_());
                if (model.getEmailAddress_() != null) {
                     jsonObject.put("emailAddress_", model.getEmailAddress_());
                } 
                if (model.getCreateBy_() != null) {
                     jsonObject.put("createBy_", model.getCreateBy_());
                } 
                if (model.getCreateTime_() != null) {
                      jsonObject.put("createTime_", DateUtils.getDate(model.getCreateTime_()));
		      jsonObject.put("createTime__date", DateUtils.getDate(model.getCreateTime_()));
		      jsonObject.put("createTime__datetime", DateUtils.getDateTime(model.getCreateTime_()));
                }
                if (model.getUpdateBy_() != null) {
                     jsonObject.put("updateBy_", model.getUpdateBy_());
                } 
                if (model.getUpdateTime_() != null) {
                      jsonObject.put("updateTime_", DateUtils.getDate(model.getUpdateTime_()));
		      jsonObject.put("updateTime__date", DateUtils.getDate(model.getUpdateTime_()));
		      jsonObject.put("updateTime__datetime", DateUtils.getDateTime(model.getUpdateTime_()));
                }
                jsonObject.put("deleteFlag_", model.getDeleteFlag_());
                if (model.getLastStartTime_() != null) {
                      jsonObject.put("lastStartTime_", DateUtils.getDate(model.getLastStartTime_()));
		      jsonObject.put("lastStartTime__date", DateUtils.getDate(model.getLastStartTime_()));
		      jsonObject.put("lastStartTime__datetime", DateUtils.getDateTime(model.getLastStartTime_()));
                }
                if (model.getLastEndTime_() != null) {
                      jsonObject.put("lastEndTime_", DateUtils.getDate(model.getLastEndTime_()));
		      jsonObject.put("lastEndTime__date", DateUtils.getDate(model.getLastEndTime_()));
		      jsonObject.put("lastEndTime__datetime", DateUtils.getDateTime(model.getLastEndTime_()));
                }
                jsonObject.put("succeed_", model.getSucceed_());
                jsonObject.put("locked_", model.getLocked_());
                jsonObject.put("errorStopAutoRun_", model.getErrorStopAutoRun_());
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<EtlTransferTask> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (EtlTransferTask model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<EtlTransferTask> arrayToList(JSONArray array) {
		java.util.List<EtlTransferTask> list = new java.util.ArrayList<EtlTransferTask>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			EtlTransferTask model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private EtlTransferTaskJsonFactory() {

	}

}
