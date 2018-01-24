package com.glaf.etl.util;

import java.io.UnsupportedEncodingException;

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
public class ETLDataTransferJsonFactory {

	public static ETLDataTransfer jsonToObject(JSONObject jsonObject) {
            ETLDataTransfer model = new ETLDataTransfer();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("dataSrcId")) {
			model.setDataSrcId(jsonObject.getString("dataSrcId"));
		}
		if (jsonObject.containsKey("targetId")) {
			model.setTargetId(jsonObject.getString("targetId"));
		}
		if (jsonObject.containsKey("transName")) {
			model.setTransName(jsonObject.getString("transName"));
		}
		if (jsonObject.containsKey("groupColumns")) {
			try {
				model.setGroupColumns(jsonObject.getString("groupColumns").getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if (jsonObject.containsKey("transferColumns")) {
			try {
				model.setTransferColumns(jsonObject.getString("transferColumns").getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if (jsonObject.containsKey("valueColumns")) {
			try {
				model.setValueColumns(jsonObject.getString("valueColumns").getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if (jsonObject.containsKey("columnsMapping")) {
			try {
				model.setColumnsMapping(jsonObject.getString("columnsMapping").getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
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
		if (jsonObject.containsKey("routRule")) {
			try {
				model.setRoutRule(jsonObject.getString("routRule").getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

            return model;
	}

	public static JSONObject toJsonObject(ETLDataTransfer model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getDataSrcId() != null) {
			jsonObject.put("dataSrcId", model.getDataSrcId());
		} 
		if (model.getTargetId() != null) {
			jsonObject.put("targetId", model.getTargetId());
		} 
		if (model.getTransName() != null) {
			jsonObject.put("transName", model.getTransName());
		} 
		
		if (model.getGroupColumns() != null) {
			try {
				jsonObject.put("groupColumns", new String(model.getGroupColumns(),"UTF-8"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		if (model.getTransferColumns() != null) {
			try {
				jsonObject.put("transferColumns", new String(model.getTransferColumns(),"UTF-8"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		if (model.getValueColumns() != null) {
			try {
				jsonObject.put("valueColumns", new String(model.getValueColumns(),"UTF-8"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		if (model.getColumnsMapping() != null) {
			try {
				jsonObject.put("columnsMapping", new String(model.getColumnsMapping(),"UTF-8"));
			} catch (Exception e) {
				e.printStackTrace();
			}
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
        if (model.getRoutRule() != null) {
			try {
				jsonObject.put("routRule", new String(model.getRoutRule(),"UTF-8"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jsonObject;
	}


	public static ObjectNode toObjectNode(ETLDataTransfer model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getDataSrcId() != null) {
                     jsonObject.put("dataSrcId", model.getDataSrcId());
                } 
                if (model.getTargetId() != null) {
                     jsonObject.put("targetId", model.getTargetId());
                } 
                if (model.getTransName() != null) {
                     jsonObject.put("transName", model.getTransName());
                } 
                if (model.getGroupColumns() != null) {
        			try {
        				jsonObject.put("groupColumns", new String(model.getGroupColumns(),"UTF-8"));
        			} catch (Exception e) {
        				e.printStackTrace();
        			}
        		} 
        		if (model.getTransferColumns() != null) {
        			try {
        				jsonObject.put("transferColumns", new String(model.getTransferColumns(),"UTF-8"));
        			} catch (Exception e) {
        				e.printStackTrace();
        			}
        		} 
        		if (model.getValueColumns() != null) {
        			try {
        				jsonObject.put("valueColumns", new String(model.getValueColumns(),"UTF-8"));
        			} catch (Exception e) {
        				e.printStackTrace();
        			}
        		} 
        		if (model.getColumnsMapping() != null) {
        			try {
        				jsonObject.put("columnsMapping", new String(model.getColumnsMapping(),"UTF-8"));
        			} catch (Exception e) {
        				e.printStackTrace();
        			}
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
                if (model.getRoutRule() != null) {
        			try {
        				jsonObject.put("routRule", new String(model.getRoutRule(),"UTF-8"));
        			} catch (Exception e) {
        				e.printStackTrace();
        			}
        		}
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<ETLDataTransfer> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ETLDataTransfer model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ETLDataTransfer> arrayToList(JSONArray array) {
		java.util.List<ETLDataTransfer> list = new java.util.ArrayList<ETLDataTransfer>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ETLDataTransfer model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private ETLDataTransferJsonFactory() {

	}

}
