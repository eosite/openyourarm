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
public class EtlTransferTaskSrcJsonFactory {

	public static EtlTransferTaskSrc jsonToObject(JSONObject jsonObject) {
            EtlTransferTaskSrc model = new EtlTransferTaskSrc();
        if (jsonObject.containsKey("id_")) {
		    model.setId_(jsonObject.getString("id_"));
		}
		if (jsonObject.containsKey("taskId_")) {
			model.setTaskId_(jsonObject.getString("taskId_"));
		}
		if (jsonObject.containsKey("srcId_")) {
			model.setSrcId_(jsonObject.getString("srcId_"));
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
		if (jsonObject.containsKey("distinctFlag_")) {
			model.setDistinctFlag_(jsonObject.getInteger("distinctFlag_"));
		}
		if (jsonObject.containsKey("querySQL_")) {
			if(StringUtils.isNotEmpty(jsonObject.getString("querySQL_"))){
				try {
					model.setQuerySQL_(jsonObject.getString("querySQL_").getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}			
			}
		}
		if (jsonObject.containsKey("ownerName_")) {
			model.setOwnerName_(jsonObject.getString("ownerName_"));
		}
		if (jsonObject.containsKey("taskConnId_")) {
			model.setTaskConnId_(jsonObject.getLong("taskConnId_"));
		}
		if (jsonObject.containsKey("taskDatabaseName_")) {
			model.setTaskDatabaseName_(jsonObject.getString("taskDatabaseName_"));
		}
		if (jsonObject.containsKey("orderMapping_")) {
			model.setOrderMapping_(jsonObject.getInteger("orderMapping_"));
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

	public static JSONObject toJsonObject(EtlTransferTaskSrc model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id_", model.getId_());
		jsonObject.put("_id__", model.getId_());
		if (model.getTaskId_() != null) {
			jsonObject.put("taskId_", model.getTaskId_());
		} 
		if (model.getSrcId_() != null) {
			jsonObject.put("srcId_", model.getSrcId_());
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
        jsonObject.put("distinctFlag_", model.getDistinctFlag_());
		if (model.getQuerySQL_() != null) {
			try {
				jsonObject.put("querySQL_", new String(model.getQuerySQL_(),"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} 
		if (model.getOwnerName_() != null) {
			jsonObject.put("ownerName_", model.getOwnerName_());
		} 
        jsonObject.put("taskConnId_", model.getTaskConnId_());
		if (model.getTaskDatabaseName_() != null) {
			jsonObject.put("taskDatabaseName_", model.getTaskDatabaseName_());
		} 
        jsonObject.put("orderMapping_", model.getOrderMapping_());
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


	public static ObjectNode toObjectNode(EtlTransferTaskSrc model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id_", model.getId_());
		jsonObject.put("_id__", model.getId_());
                if (model.getTaskId_() != null) {
                     jsonObject.put("taskId_", model.getTaskId_());
                } 
                if (model.getSrcId_() != null) {
                     jsonObject.put("srcId_", model.getSrcId_());
                } 
                if (model.getPreSQL_() != null) {
                     jsonObject.put("preSQL_", model.getPreSQL_());
                } 
                if (model.getPostSQL_() != null) {
                     jsonObject.put("postSQL_", model.getPostSQL_());
                } 
                jsonObject.put("distinctFlag_", model.getDistinctFlag_());
                if (model.getQuerySQL_() != null) {
                     jsonObject.put("querySQL_", model.getQuerySQL_());
                } 
                if (model.getOwnerName_() != null) {
                     jsonObject.put("ownerName_", model.getOwnerName_());
                } 
                jsonObject.put("taskConnId_", model.getTaskConnId_());
                if (model.getTaskDatabaseName_() != null) {
                     jsonObject.put("taskDatabaseName_", model.getTaskDatabaseName_());
                } 
                jsonObject.put("orderMapping_", model.getOrderMapping_());
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

	
	public static JSONArray listToArray(java.util.List<EtlTransferTaskSrc> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (EtlTransferTaskSrc model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<EtlTransferTaskSrc> arrayToList(JSONArray array) {
		java.util.List<EtlTransferTaskSrc> list = new java.util.ArrayList<EtlTransferTaskSrc>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			EtlTransferTaskSrc model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private EtlTransferTaskSrcJsonFactory() {

	}

}
