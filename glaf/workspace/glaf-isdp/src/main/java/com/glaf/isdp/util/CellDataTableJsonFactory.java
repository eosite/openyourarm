package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.isdp.domain.CellDataTable;

/**
 * 
 * JSON工厂类
 *
 */
public class CellDataTableJsonFactory {

	public static CellDataTable jsonToObject(JSONObject jsonObject) {
		CellDataTable model = new CellDataTable();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("tableName")) {
			model.setTableName(jsonObject.getString("tableName"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("addType")) {
			model.setAddType(jsonObject.getInteger("addType"));
		}
		if (jsonObject.containsKey("maxUser")) {
			model.setMaxUser(jsonObject.getInteger("maxUser"));
		}
		if (jsonObject.containsKey("maxSys")) {
			model.setMaxSys(jsonObject.getInteger("maxSys"));
		}
		if (jsonObject.containsKey("userId")) {
			model.setUserId(jsonObject.getString("userId"));
		}
		if (jsonObject.containsKey("ctime")) {
			model.setCtime(jsonObject.getDate("ctime"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("sysNum")) {
			model.setSysNum(jsonObject.getString("sysNum"));
		}
		if (jsonObject.containsKey("isSubTable")) {
			model.setIsSubTable(jsonObject.getString("isSubTable"));
		}
		if (jsonObject.containsKey("topId")) {
			model.setTopId(jsonObject.getString("topId"));
		}
		if (jsonObject.containsKey("fileDotFileId")) {
			model.setFileDotFileId(jsonObject.getString("fileDotFileId"));
		}
		if (jsonObject.containsKey("indexId")) {
			model.setIndexId(jsonObject.getInteger("indexId"));
		}
		if (jsonObject.containsKey("winWidth")) {
			model.setWinWidth(jsonObject.getInteger("winWidth"));
		}
		if (jsonObject.containsKey("winHeight")) {
			model.setWinHeight(jsonObject.getInteger("winHeight"));
		}
		if (jsonObject.containsKey("intQuote")) {
			model.setIntQuote(jsonObject.getInteger("intQuote"));
		}
		if (jsonObject.containsKey("intLineEdit")) {
			model.setIntLineEdit(jsonObject.getInteger("intLineEdit"));
		}
		if (jsonObject.containsKey("printFileId")) {
			model.setPrintFileId(jsonObject.getString("printFileId"));
		}
		if (jsonObject.containsKey("intUseSTreeWBS")) {
			model.setIntUseSTreeWBS(jsonObject.getInteger("intUseSTreeWBS"));
		}
		if (jsonObject.containsKey("fileDotIndexId")) {
			model.setFileDotIndexId(jsonObject.getInteger("fileDotIndexId"));
		}

		return model;
	}

	public static JSONObject toJsonObject(CellDataTable model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		jsonObject.put("addType", model.getAddType());
		jsonObject.put("maxUser", model.getMaxUser());
		jsonObject.put("maxSys", model.getMaxSys());
		if (model.getUserId() != null) {
			jsonObject.put("userId", model.getUserId());
		}
		if (model.getCtime() != null) {
			jsonObject.put("ctime", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_date", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_datetime",
					DateUtils.getDateTime(model.getCtime()));
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getSysNum() != null) {
			jsonObject.put("sysNum", model.getSysNum());
		}
		if (model.getIsSubTable() != null) {
			jsonObject.put("isSubTable", model.getIsSubTable());
		}
		if (model.getTopId() != null) {
			jsonObject.put("topId", model.getTopId());
		}
		if (model.getFileDotFileId() != null) {
			jsonObject.put("fileDotFileId", model.getFileDotFileId());
		}
		jsonObject.put("indexId", model.getIndexId());
		jsonObject.put("winWidth", model.getWinWidth());
		jsonObject.put("winHeight", model.getWinHeight());
		jsonObject.put("intQuote", model.getIntQuote());
		jsonObject.put("intLineEdit", model.getIntLineEdit());
		if (model.getPrintFileId() != null) {
			jsonObject.put("printFileId", model.getPrintFileId());
		}
		jsonObject.put("intUseSTreeWBS", model.getIntUseSTreeWBS());
		jsonObject.put("fileDotIndexId", model.getFileDotIndexId());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(CellDataTable model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		jsonObject.put("addType", model.getAddType());
		jsonObject.put("maxUser", model.getMaxUser());
		jsonObject.put("maxSys", model.getMaxSys());
		if (model.getUserId() != null) {
			jsonObject.put("userId", model.getUserId());
		}
		if (model.getCtime() != null) {
			jsonObject.put("ctime", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_date", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_datetime",
					DateUtils.getDateTime(model.getCtime()));
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getSysNum() != null) {
			jsonObject.put("sysNum", model.getSysNum());
		}
		if (model.getIsSubTable() != null) {
			jsonObject.put("isSubTable", model.getIsSubTable());
		}
		if (model.getTopId() != null) {
			jsonObject.put("topId", model.getTopId());
		}
		if (model.getFileDotFileId() != null) {
			jsonObject.put("fileDotFileId", model.getFileDotFileId());
		}
		jsonObject.put("indexId", model.getIndexId());
		jsonObject.put("winWidth", model.getWinWidth());
		jsonObject.put("winHeight", model.getWinHeight());
		jsonObject.put("intQuote", model.getIntQuote());
		jsonObject.put("intLineEdit", model.getIntLineEdit());
		if (model.getPrintFileId() != null) {
			jsonObject.put("printFileId", model.getPrintFileId());
		}
		jsonObject.put("intUseSTreeWBS", model.getIntUseSTreeWBS());
		jsonObject.put("fileDotIndexId", model.getFileDotIndexId());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<CellDataTable> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (CellDataTable model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<CellDataTable> arrayToList(JSONArray array) {
		java.util.List<CellDataTable> list = new java.util.ArrayList<CellDataTable>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			CellDataTable model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private CellDataTableJsonFactory() {

	}

}
