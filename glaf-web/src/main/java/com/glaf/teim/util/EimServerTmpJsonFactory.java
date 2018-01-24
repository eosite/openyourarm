package com.glaf.teim.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.teim.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class EimServerTmpJsonFactory {

	public static EimServerTmp jsonToObject(JSONObject jsonObject) {
            EimServerTmp model = new EimServerTmp();
            if (jsonObject.containsKey("tmpId")) {
		    model.setTmpId(jsonObject.getString("tmpId"));
		}
		if (jsonObject.containsKey("categoryId")) {
			model.setCategoryId(jsonObject.getLong("categoryId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("path_")) {
			model.setPath_(jsonObject.getString("path_"));
		}
		if (jsonObject.containsKey("reqUrlParam")) {
			model.setReqUrlParam(jsonObject.getString("reqUrlParam"));
		}
		if (jsonObject.containsKey("reqType")) {
			model.setReqType(jsonObject.getString("reqType"));
		}
		if (jsonObject.containsKey("reqHeader")) {
			model.setReqHeader(jsonObject.getString("reqHeader"));
		}
		if (jsonObject.containsKey("reqContentType")) {
			model.setReqContentType(jsonObject.getString("reqContentType"));
		}
		if (jsonObject.containsKey("resContentType")) {
			model.setResContentType(jsonObject.getString("resContentType"));
		}
		if (jsonObject.containsKey("reqBody")) {
			model.setReqBody(jsonObject.getString("reqBody"));
		}
		if (jsonObject.containsKey("response_")) {
			model.setResponse_(jsonObject.getString("response_"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}
		if (jsonObject.containsKey("updateTime")) {
			model.setUpdateTime(jsonObject.getDate("updateTime"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}

            return model;
	}

	public static JSONObject toJsonObject(EimServerTmp model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("tmpId", model.getTmpId());
		jsonObject.put("_tmpId_", model.getTmpId());
        jsonObject.put("categoryId", model.getCategoryId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		} 
		if (model.getPath_() != null) {
			jsonObject.put("path_", model.getPath_());
		} 
		if (model.getReqUrlParam() != null) {
			jsonObject.put("reqUrlParam", model.getReqUrlParam());
		} 
		if (model.getReqType() != null) {
			jsonObject.put("reqType", model.getReqType());
		} 
		if (model.getReqHeader() != null) {
			jsonObject.put("reqHeader", model.getReqHeader());
		} 
		if (model.getReqContentType() != null) {
			jsonObject.put("reqContentType", model.getReqContentType());
		} 
		if (model.getResContentType() != null) {
			jsonObject.put("resContentType", model.getResContentType());
		} 
		if (model.getReqBody() != null) {
			jsonObject.put("reqBody", model.getReqBody());
		} 
		if (model.getResponse_() != null) {
			jsonObject.put("response_", model.getResponse_());
		} 
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		} 
                if (model.getCreateTime() != null) {
                      jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
		      jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
		      jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
                }
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		} 
                if (model.getUpdateTime() != null) {
                      jsonObject.put("updateTime", DateUtils.getDate(model.getUpdateTime()));
		      jsonObject.put("updateTime_date", DateUtils.getDate(model.getUpdateTime()));
		      jsonObject.put("updateTime_datetime", DateUtils.getDateTime(model.getUpdateTime()));
                }
        if(model.getDeleteFlag()!=null)
        jsonObject.put("deleteFlag", model.getDeleteFlag());
		return jsonObject;
	}


	public static ObjectNode toObjectNode(EimServerTmp model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("tmpId", model.getTmpId());
		jsonObject.put("_tmpId_", model.getTmpId());
                jsonObject.put("categoryId", model.getCategoryId());
                if (model.getName() != null) {
                     jsonObject.put("name", model.getName());
                } 
                if (model.getPath_() != null) {
                     jsonObject.put("path_", model.getPath_());
                } 
                if (model.getReqUrlParam() != null) {
                     jsonObject.put("reqUrlParam", model.getReqUrlParam());
                } 
                if (model.getReqType() != null) {
                     jsonObject.put("reqType", model.getReqType());
                } 
                if (model.getReqHeader() != null) {
                     jsonObject.put("reqHeader", model.getReqHeader());
                } 
                if (model.getReqContentType() != null) {
                     jsonObject.put("reqContentType", model.getReqContentType());
                } 
                if (model.getResContentType() != null) {
                     jsonObject.put("resContentType", model.getResContentType());
                } 
                if (model.getReqBody() != null) {
                     jsonObject.put("reqBody", model.getReqBody());
                } 
                if (model.getResponse_() != null) {
                     jsonObject.put("response_", model.getResponse_());
                } 
                if (model.getCreateBy() != null) {
                     jsonObject.put("createBy", model.getCreateBy());
                } 
                if (model.getCreateTime() != null) {
                      jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
		      jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
		      jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
                }
                if (model.getUpdateBy() != null) {
                     jsonObject.put("updateBy", model.getUpdateBy());
                } 
                if (model.getUpdateTime() != null) {
                      jsonObject.put("updateTime", DateUtils.getDate(model.getUpdateTime()));
		      jsonObject.put("updateTime_date", DateUtils.getDate(model.getUpdateTime()));
		      jsonObject.put("updateTime_datetime", DateUtils.getDateTime(model.getUpdateTime()));
                }
                if(model.getDeleteFlag()!=null)
                jsonObject.put("deleteFlag", model.getDeleteFlag());
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<EimServerTmp> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (EimServerTmp model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<EimServerTmp> arrayToList(JSONArray array) {
		java.util.List<EimServerTmp> list = new java.util.ArrayList<EimServerTmp>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			EimServerTmp model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private EimServerTmpJsonFactory() {

	}

}
