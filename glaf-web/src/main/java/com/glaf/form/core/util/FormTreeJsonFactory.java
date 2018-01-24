package com.glaf.form.core.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
 
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class FormTreeJsonFactory {

	public static FormTree jsonToObject(JSONObject jsonObject) {
            FormTree model = new FormTree();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("nodeDesc")) {
			model.setNodeDesc(jsonObject.getString("nodeDesc"));
		}
		if (jsonObject.containsKey("discriminator")) {
			model.setDiscriminator(jsonObject.getString("discriminator"));
		}
		if (jsonObject.containsKey("icon")) {
			model.setIcon(jsonObject.getString("icon"));
		}
		if (jsonObject.containsKey("iconCls")) {
			model.setIconCls(jsonObject.getString("iconCls"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("moveable")) {
			model.setMoveable(jsonObject.getString("moveable"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("parent")) {
			model.setParent(jsonObject.getLong("parent"));
		}
		if (jsonObject.containsKey("sort")) {
			model.setSort(jsonObject.getInteger("sort"));
		}
		if (jsonObject.containsKey("treeId")) {
			model.setTreeId(jsonObject.getString("treeId"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}
		if (jsonObject.containsKey("updateDate")) {
			model.setUpdateDate(jsonObject.getDate("updateDate"));
		}
		if (jsonObject.containsKey("url")) {
			model.setUrl(jsonObject.getString("url"));
		}
		if (jsonObject.containsKey("category")) {
			model.setCategory(jsonObject.getString("category"));
		}

            return model;
	}

	public static JSONObject toJsonObject(FormTree model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		} 
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		} 
                if (model.getCreateDate() != null) {
                      jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
                }
		if (model.getNodeDesc() != null) {
			jsonObject.put("nodeDesc", model.getNodeDesc());
		} 
		if (model.getDiscriminator() != null) {
			jsonObject.put("discriminator", model.getDiscriminator());
		} 
		if (model.getIcon() != null) {
			jsonObject.put("icon", model.getIcon());
		} 
		if (model.getIconCls() != null) {
			jsonObject.put("iconCls", model.getIconCls());
		} 
        jsonObject.put("locked", model.getLocked());
		if (model.getMoveable() != null) {
			jsonObject.put("moveable", model.getMoveable());
		} 
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		} 
        jsonObject.put("parent", model.getParent());
        jsonObject.put("sort", model.getSort());
		if (model.getTreeId() != null) {
			jsonObject.put("treeId", model.getTreeId());
		} 
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		} 
                if (model.getUpdateDate() != null) {
                      jsonObject.put("updateDate", DateUtils.getDate(model.getUpdateDate()));
		      jsonObject.put("updateDate_date", DateUtils.getDate(model.getUpdateDate()));
		      jsonObject.put("updateDate_datetime", DateUtils.getDateTime(model.getUpdateDate()));
                }
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		} 
		if (model.getCategory() != null) {
			jsonObject.put("category", model.getCategory());
		} 
		return jsonObject;
	}


	public static ObjectNode toObjectNode(FormTree model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getCode() != null) {
                     jsonObject.put("code", model.getCode());
                } 
                if (model.getCreateBy() != null) {
                     jsonObject.put("createBy", model.getCreateBy());
                } 
                if (model.getCreateDate() != null) {
                      jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
                }
                if (model.getNodeDesc() != null) {
                     jsonObject.put("nodeDesc", model.getNodeDesc());
                } 
                if (model.getDiscriminator() != null) {
                     jsonObject.put("discriminator", model.getDiscriminator());
                } 
                if (model.getIcon() != null) {
                     jsonObject.put("icon", model.getIcon());
                } 
                if (model.getIconCls() != null) {
                     jsonObject.put("iconCls", model.getIconCls());
                } 
                jsonObject.put("locked", model.getLocked());
                if (model.getMoveable() != null) {
                     jsonObject.put("moveable", model.getMoveable());
                } 
                if (model.getName() != null) {
                     jsonObject.put("name", model.getName());
                } 
                jsonObject.put("parent", model.getParent());
                jsonObject.put("sort", model.getSort());
                if (model.getTreeId() != null) {
                     jsonObject.put("treeId", model.getTreeId());
                } 
                if (model.getUpdateBy() != null) {
                     jsonObject.put("updateBy", model.getUpdateBy());
                } 
                if (model.getUpdateDate() != null) {
                      jsonObject.put("updateDate", DateUtils.getDate(model.getUpdateDate()));
		      jsonObject.put("updateDate_date", DateUtils.getDate(model.getUpdateDate()));
		      jsonObject.put("updateDate_datetime", DateUtils.getDateTime(model.getUpdateDate()));
                }
                if (model.getUrl() != null) {
                     jsonObject.put("url", model.getUrl());
                } 
                if (model.getCategory() != null) {
                     jsonObject.put("category", model.getCategory());
                } 
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<FormTree> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormTree model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FormTree> arrayToList(JSONArray array) {
		java.util.List<FormTree> list = new java.util.ArrayList<FormTree>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormTree model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private FormTreeJsonFactory() {

	}

}
