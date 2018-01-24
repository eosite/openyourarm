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
public class ETLDataSrcJsonFactory {

	public static ETLDataSrc jsonToObject(JSONObject jsonObject) {
            ETLDataSrc model = new ETLDataSrc();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("databaseId")) {
			model.setDatabaseId(jsonObject.getLong("databaseId"));
		}
		if (jsonObject.containsKey("databaseName")) {
			model.setDatabaseName(jsonObject.getString("databaseName"));
		}
		if (jsonObject.containsKey("sourceName")) {
			model.setSourceName(jsonObject.getString("sourceName"));
		}
		if (jsonObject.containsKey("tableName")) {
			model.setTableName(jsonObject.getString("tableName"));
		}
		if (jsonObject.containsKey("columnInfos")) {
			model.setColumnInfos(jsonObject.getBytes("columnInfos"));
		}
		if (jsonObject.containsKey("sql")) {
			model.setSql(jsonObject.getBytes("sql"));
		}
		if (jsonObject.containsKey("restoreJson")) {
			model.setRestoreJson(jsonObject.getBytes("restoreJson"));
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

            return model;
	}

	public static JSONObject toJsonObject(ETLDataSrc model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getDatabaseId() != null) {
			jsonObject.put("databaseId", model.getDatabaseId());
		} 
		if (model.getDatabaseName() != null) {
			jsonObject.put("databaseName", model.getDatabaseName());
		} 
		if (model.getSourceName() != null) {
			jsonObject.put("sourceName", model.getSourceName());
		} 
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		} 
		if (model.getColumnInfos() != null) {
			try {
				jsonObject.put("columnInfos", new String(model.getColumnInfos(),"UTF-8"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		if (model.getSql() != null) {
			try {
				jsonObject.put("sql", new String(model.getSql(),"UTF-8"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		if (model.getRestoreJson() != null) {
			try {
				jsonObject.put("restoreJson", new String(model.getRestoreJson(),"UTF-8"));
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
		return jsonObject;
	}


	public static ObjectNode toObjectNode(ETLDataSrc model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getDatabaseId() != null) {
                     jsonObject.put("databaseId", model.getDatabaseId());
                } 
                if (model.getDatabaseName() != null) {
                     jsonObject.put("databaseName", model.getDatabaseName());
                } 
                if (model.getSourceName() != null) {
                     jsonObject.put("sourceName", model.getSourceName());
                } 
                if (model.getTableName() != null) {
                     jsonObject.put("tableName", model.getTableName());
                } 
                
                if (model.getColumnInfos() != null) {
        			try {
        				jsonObject.put("columnInfos", new String(model.getColumnInfos(),"UTF-8"));
        			} catch (Exception e) {
        				e.printStackTrace();
        			}
        		} 

        		if (model.getSql() != null) {
        			try {
        				jsonObject.put("sql", new String(model.getSql(),"UTF-8"));
        			} catch (Exception e) {
        				e.printStackTrace();
        			}
        		} 

        		if (model.getRestoreJson() != null) {
        			try {
        				jsonObject.put("restoreJson", new String(model.getRestoreJson(),"UTF-8"));
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
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<ETLDataSrc> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ETLDataSrc model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ETLDataSrc> arrayToList(JSONArray array) {
		java.util.List<ETLDataSrc> list = new java.util.ArrayList<ETLDataSrc>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ETLDataSrc model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private ETLDataSrcJsonFactory() {

	}

}
