package com.glaf.etl.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;

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
public class EtlTransferTaskTargetJsonFactory {

	public static EtlTransferTaskTarget jsonToObject(JSONObject jsonObject) {
            EtlTransferTaskTarget model = new EtlTransferTaskTarget();
        if (jsonObject.containsKey("id_")) {
		    model.setId_(jsonObject.getString("id_"));
		}
		if (jsonObject.containsKey("taskId_")) {
			model.setTaskId_(jsonObject.getString("taskId_"));
		}
		if (jsonObject.containsKey("targetId_")) {
			model.setTargetId_(jsonObject.getString("targetId_"));
		}
		if (jsonObject.containsKey("preSQL_")) {
			if(StringUtils.isNotEmpty(jsonObject.getString("preSQL_"))){
				try {
					model.setPreSQL_(jsonObject.getString("preSQL_").getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}			
			}
		} 
		if (jsonObject.containsKey("postSQL_")) {
			if(StringUtils.isNotEmpty(jsonObject.getString("postSQL_"))){
				try {
					model.setPostSQL_(jsonObject.getString("postSQL_").getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}			
			}
		} 
		if (jsonObject.containsKey("taskConnId_")) {
			model.setTaskConnId_(jsonObject.getLong("taskConnId_"));
		}
		if (jsonObject.containsKey("taskDatabaseName_")) {
			model.setTaskDatabaseName_(jsonObject.getString("taskDatabaseName_"));
		}
		if (jsonObject.containsKey("taskTableName_")) {
			model.setTaskTableName_(jsonObject.getString("taskTableName_"));
		}
		if (jsonObject.containsKey("tableNamePrefix_")) {
			model.setTableNamePrefix_(jsonObject.getString("tableNamePrefix_"));
		}
		if (jsonObject.containsKey("preTuncateFlag_")) {
			model.setPreTuncateFlag_(jsonObject.getInteger("preTuncateFlag_"));
		}
		if (jsonObject.containsKey("treatment_methd_")) {
			model.setTreatment_methd_(jsonObject.getInteger("treatment_methd_"));
		}
		if (jsonObject.containsKey("createBy_")) {
			model.setCreateBy_(jsonObject.getString("createBy_"));
		}
		if (jsonObject.containsKey("createTime_")) {
			model.setCreateTime_(jsonObject.getDate("createTime_"));
		}
		if (jsonObject.containsKey("updateBy_")) {
			model.setUpdateBy_(jsonObject.getString("updateBy_"));
		}
		if (jsonObject.containsKey("updateTime_")) {
			model.setUpdateTime_(jsonObject.getDate("updateTime_"));
		}

            return model;
	}

	public static JSONObject toJsonObject(EtlTransferTaskTarget model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id_", model.getId_());
		jsonObject.put("_id__", model.getId_());
		if (model.getTaskId_() != null) {
			jsonObject.put("taskId_", model.getTaskId_());
		} 
		if (model.getTargetId_() != null) {
			jsonObject.put("targetId_", model.getTargetId_());
		} 
		if (model.getPreSQL_() != null) {
			try {
				jsonObject.put("preSQL_", new String(model.getPreSQL_(),"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} 
		if (model.getPostSQL_() != null) {
			try {
				jsonObject.put("postSQL_", new String(model.getPostSQL_(),"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} 
        jsonObject.put("taskConnId_", model.getTaskConnId_());
		if (model.getTaskDatabaseName_() != null) {
			jsonObject.put("taskDatabaseName_", model.getTaskDatabaseName_());
		} 
		if (model.getTaskTableName_() != null) {
			jsonObject.put("taskTableName_", model.getTaskTableName_());
		} 
		if (model.getTableNamePrefix_() != null) {
			jsonObject.put("tableNamePrefix_", model.getTableNamePrefix_());
		} 
        jsonObject.put("preTuncateFlag_", model.getPreTuncateFlag_());
        jsonObject.put("treatment_methd_", model.getTreatment_methd_());
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
		return jsonObject;
	}


	public static ObjectNode toObjectNode(EtlTransferTaskTarget model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id_", model.getId_());
		jsonObject.put("_id__", model.getId_());
                if (model.getTaskId_() != null) {
                     jsonObject.put("taskId_", model.getTaskId_());
                } 
                if (model.getTargetId_() != null) {
                     jsonObject.put("targetId_", model.getTargetId_());
                } 
                if (model.getPreSQL_() != null) {
                     jsonObject.put("preSQL_", model.getPreSQL_());
                } 
                if (model.getPostSQL_() != null) {
                     jsonObject.put("postSQL_", model.getPostSQL_());
                } 
                jsonObject.put("taskConnId_", model.getTaskConnId_());
                if (model.getTaskDatabaseName_() != null) {
                     jsonObject.put("taskDatabaseName_", model.getTaskDatabaseName_());
                } 
                if (model.getTaskTableName_() != null) {
                     jsonObject.put("taskTableName_", model.getTaskTableName_());
                } 
                if (model.getTableNamePrefix_() != null) {
                     jsonObject.put("tableNamePrefix_", model.getTableNamePrefix_());
                } 
                jsonObject.put("preTuncateFlag_", model.getPreTuncateFlag_());
                jsonObject.put("treatment_methd_", model.getTreatment_methd_());
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
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<EtlTransferTaskTarget> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (EtlTransferTaskTarget model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<EtlTransferTaskTarget> arrayToList(JSONArray array) {
		java.util.List<EtlTransferTaskTarget> list = new java.util.ArrayList<EtlTransferTaskTarget>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			EtlTransferTaskTarget model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private EtlTransferTaskTargetJsonFactory() {

	}

}
