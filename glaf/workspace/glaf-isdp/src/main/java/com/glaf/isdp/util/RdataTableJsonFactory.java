package com.glaf.isdp.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.isdp.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class RdataTableJsonFactory {

	public static RdataTable jsonToObject(JSONObject jsonObject) {
		RdataTable model = new RdataTable();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("tablename")) {
			model.setTablename(jsonObject.getString("tablename"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("addtype")) {
			model.setAddtype(jsonObject.getInteger("addtype"));
		}
		if (jsonObject.containsKey("maxuser")) {
			model.setMaxuser(jsonObject.getInteger("maxuser"));
		}
		if (jsonObject.containsKey("maxsys")) {
			model.setMaxsys(jsonObject.getInteger("maxsys"));
		}
		if (jsonObject.containsKey("userid")) {
			model.setUserid(jsonObject.getString("userid"));
		}
		if (jsonObject.containsKey("ctime")) {
			model.setCtime(jsonObject.getDate("ctime"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("sysnum")) {
			model.setSysnum(jsonObject.getString("sysnum"));
		}
		if (jsonObject.containsKey("issubtable")) {
			model.setIssubtable(jsonObject.getString("issubtable"));
		}
		if (jsonObject.containsKey("topid")) {
			model.setTopid(jsonObject.getString("topid"));
		}
		if (jsonObject.containsKey("filedotFileid")) {
			model.setFiledotFileid(jsonObject.getString("filedotFileid"));
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
		if (jsonObject.containsKey("printfileid")) {
			model.setPrintfileid(jsonObject.getString("printfileid"));
		}
		if (jsonObject.containsKey("INTUSESTREEWBS")) {
			model.setINTUSESTREEWBS(jsonObject.getInteger("INTUSESTREEWBS"));
		}
		if (jsonObject.containsKey("intUseIf")) {
			model.setIntUseIf(jsonObject.getInteger("intUseIf"));
		}

		return model;
	}

	public static JSONObject toJsonObject(RdataTable model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTablename() != null) {
			jsonObject.put("tablename", model.getTablename());
			jsonObject.put("tableName", model.getTablename());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		jsonObject.put("addtype", model.getAddtype());
		jsonObject.put("addType", model.getAddtype());
		jsonObject.put("maxuser", model.getMaxuser());
		jsonObject.put("maxUser", model.getMaxuser());
		jsonObject.put("maxsys", model.getMaxsys());
		if (model.getUserid() != null) {
			jsonObject.put("userid", model.getUserid());
			jsonObject.put("userId", model.getUserid());
		}
		if (model.getCtime() != null) {
			jsonObject.put("ctime", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_date", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_datetime", DateUtils.getDateTime(model.getCtime()));
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getSysnum() != null) {
			jsonObject.put("sysnum", model.getSysnum());
		}
		if (model.getIssubtable() != null) {
			jsonObject.put("issubtable", model.getIssubtable());
			jsonObject.put("isSubTable", model.getIssubtable());
		}
		if (model.getTopid() != null) {
			jsonObject.put("topId", model.getTopid());
		}
		if (model.getFiledotFileid() != null) {
			jsonObject.put("filedotFileid", model.getFiledotFileid());
		}
		jsonObject.put("indexId", model.getIndexId());
		jsonObject.put("winWidth", model.getWinWidth());
		jsonObject.put("winHeight", model.getWinHeight());
		jsonObject.put("intQuote", model.getIntQuote());
		jsonObject.put("intLineEdit", model.getIntLineEdit());
		if (model.getPrintfileid() != null) {
			jsonObject.put("printfileid", model.getPrintfileid());
		}
		jsonObject.put("INTUSESTREEWBS", model.getINTUSESTREEWBS());
		jsonObject.put("intUseIf", model.getIntUseIf());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(RdataTable model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTablename() != null) {
			jsonObject.put("tablename", model.getTablename());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		jsonObject.put("addtype", model.getAddtype());
		jsonObject.put("maxuser", model.getMaxuser());
		jsonObject.put("maxsys", model.getMaxsys());
		if (model.getUserid() != null) {
			jsonObject.put("userid", model.getUserid());
		}
		if (model.getCtime() != null) {
			jsonObject.put("ctime", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_date", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_datetime", DateUtils.getDateTime(model.getCtime()));
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getSysnum() != null) {
			jsonObject.put("sysnum", model.getSysnum());
		}
		if (model.getIssubtable() != null) {
			jsonObject.put("issubtable", model.getIssubtable());
		}
		if (model.getTopid() != null) {
			jsonObject.put("topid", model.getTopid());
		}
		if (model.getFiledotFileid() != null) {
			jsonObject.put("filedotFileid", model.getFiledotFileid());
		}
		jsonObject.put("indexId", model.getIndexId());
		jsonObject.put("winWidth", model.getWinWidth());
		jsonObject.put("winHeight", model.getWinHeight());
		jsonObject.put("intQuote", model.getIntQuote());
		jsonObject.put("intLineEdit", model.getIntLineEdit());
		if (model.getPrintfileid() != null) {
			jsonObject.put("printfileid", model.getPrintfileid());
		}
		jsonObject.put("INTUSESTREEWBS", model.getINTUSESTREEWBS());
		jsonObject.put("intUseIf", model.getIntUseIf());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<RdataTable> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (RdataTable model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<RdataTable> arrayToList(JSONArray array) {
		java.util.List<RdataTable> list = new java.util.ArrayList<RdataTable>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			RdataTable model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private RdataTableJsonFactory() {

	}

}
