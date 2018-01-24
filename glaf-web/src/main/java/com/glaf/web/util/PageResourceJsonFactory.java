package com.glaf.web.util;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.web.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class PageResourceJsonFactory {

	public static PageResource jsonToObject(JSONObject jsonObject) {
		PageResource model = new PageResource();
		if (jsonObject.containsKey("resId")) {
			model.setResId(jsonObject.getLong("resId"));
		}
		if (jsonObject.containsKey("resPath")) {
			model.setResPath(jsonObject.getString("resPath"));
		}
		if (jsonObject.containsKey("resFileName")) {
			model.setResFileName(jsonObject.getString("resFileName"));
		}
		if (jsonObject.containsKey("resName")) {
			model.setResName(jsonObject.getString("resName"));
		}
		if (jsonObject.containsKey("resType")) {
			model.setResType(jsonObject.getString("resType"));
		}
		if (jsonObject.containsKey("resMime")) {
			model.setResMime(jsonObject.getString("resMime"));
		}
		if (jsonObject.containsKey("resCrDatetime")) {
			model.setResCrDatetime(jsonObject.getDate("resCrDatetime"));
		}

		return model;
	}

	public static PageResource jsonToObjectFull(JSONObject jsonObject) {
		PageResource model = new PageResource();
		if (jsonObject.containsKey("resId")) {
			model.setResId(jsonObject.getLong("resId"));
		}
		if (jsonObject.containsKey("resPath")) {
			model.setResPath(jsonObject.getString("resPath"));
		}
		if (jsonObject.containsKey("resFileName")) {
			model.setResFileName(jsonObject.getString("resFileName"));
		}
		if (jsonObject.containsKey("resContent")) {
			model.setResContent(jsonObject.getBytes("resContent"));
		}
		if (jsonObject.containsKey("resName")) {
			model.setResName(jsonObject.getString("resName"));
		}
		if (jsonObject.containsKey("resType")) {
			model.setResType(jsonObject.getString("resType"));
		}
		if (jsonObject.containsKey("resMime")) {
			model.setResMime(jsonObject.getString("resMime"));
		}
		if (jsonObject.containsKey("resCrDatetime")) {
			model.setResCrDatetime(jsonObject.getDate("resCrDatetime"));
		}

		return model;
	}

	public static JSONObject toJsonObject(PageResource model) throws UnsupportedEncodingException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("resId", model.getResId());
		jsonObject.put("_resId_", model.getResId());
		if (model.getResPath() != null) {
			jsonObject.put("resPath", model.getResPath());
		}
		if (model.getResFileName() != null) {
			jsonObject.put("resFileName", model.getResFileName());
		}
		if (model.getResName() != null) {
			jsonObject.put("resName", model.getResName());
		}
		if (model.getResContent() != null) {
			jsonObject.put("resContent",model.getResContent());
		}
		if (model.getResType() != null) {
			jsonObject.put("resType", model.getResType());
		}
		if (model.getResMime() != null) {
			jsonObject.put("resMime", model.getResMime());
		}
		if (model.getResCrDatetime() != null) {
			jsonObject.put("resCrDatetime", DateUtils.getDate(model.getResCrDatetime()));
			jsonObject.put("resCrDatetime_date", DateUtils.getDate(model.getResCrDatetime()));
			jsonObject.put("resCrDatetime_datetime", DateUtils.getDateTime(model.getResCrDatetime()));
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(PageResource model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("resId", model.getResId());
		jsonObject.put("_resId_", model.getResId());
		if (model.getResPath() != null) {
			jsonObject.put("resPath", model.getResPath());
		}
		if (model.getResFileName() != null) {
			jsonObject.put("resFileName", model.getResFileName());
		}
		if (model.getResName() != null) {
			jsonObject.put("resName", model.getResName());
		}
		if (model.getResType() != null) {
			jsonObject.put("resType", model.getResType());
		}
		if (model.getResMime() != null) {
			jsonObject.put("resMime", model.getResMime());
		}
		if (model.getResCrDatetime() != null) {
			jsonObject.put("resCrDatetime", DateUtils.getDate(model.getResCrDatetime()));
			jsonObject.put("resCrDatetime_date", DateUtils.getDate(model.getResCrDatetime()));
			jsonObject.put("resCrDatetime_datetime", DateUtils.getDateTime(model.getResCrDatetime()));
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<PageResource> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (PageResource model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<PageResource> arrayToList(JSONArray array) {
		java.util.List<PageResource> list = new java.util.ArrayList<PageResource>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			PageResource model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private PageResourceJsonFactory() {

	}

}
