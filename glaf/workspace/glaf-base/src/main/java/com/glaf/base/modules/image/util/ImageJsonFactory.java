package com.glaf.base.modules.image.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.base.modules.image.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class ImageJsonFactory {

	public static Image jsonToObject(JSONObject jsonObject) {
		Image model = new Image();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("parentId")) {
			model.setParentId(jsonObject.getLong("parentId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("desc")) {
			model.setDesc(jsonObject.getString("desc"));
		}
		if (jsonObject.containsKey("filename")) {
			model.setFilename(jsonObject.getString("filename"));
		}
		if (jsonObject.containsKey("imagePath")) {
			model.setImagePath(jsonObject.getString("imagePath"));
		}
		if (jsonObject.containsKey("width")) {
			model.setWidth(jsonObject.getInteger("width"));
		}
		if (jsonObject.containsKey("height")) {
			model.setHeight(jsonObject.getInteger("height"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("updateDate")) {
			model.setUpdateDate(jsonObject.getDate("updateDate"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}

		return model;
	}

	public static JSONObject toJsonObject(Image model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("parentId", model.getParentId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		}
		if (model.getFilename() != null) {
			jsonObject.put("filename", model.getFilename());
		}
		if (model.getImagePath() != null) {
			jsonObject.put("imagePath", model.getImagePath());
		}
		jsonObject.put("width", model.getWidth());
		jsonObject.put("height", model.getHeight());
		jsonObject.put("locked", model.getLocked());
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime",
					DateUtils.getDateTime(model.getUpdateDate()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(Image model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("parentId", model.getParentId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		}
		if (model.getFilename() != null) {
			jsonObject.put("filename", model.getFilename());
		}
		if (model.getImagePath() != null) {
			jsonObject.put("imagePath", model.getImagePath());
		}
		jsonObject.put("width", model.getWidth());
		jsonObject.put("height", model.getHeight());
		jsonObject.put("locked", model.getLocked());
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime",
					DateUtils.getDateTime(model.getUpdateDate()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<Image> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Image model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<Image> arrayToList(JSONArray array) {
		java.util.List<Image> list = new java.util.ArrayList<Image>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Image model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private ImageJsonFactory() {

	}

}
