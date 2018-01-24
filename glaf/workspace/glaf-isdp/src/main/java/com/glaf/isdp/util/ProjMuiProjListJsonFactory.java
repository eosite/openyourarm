package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.isdp.domain.ProjMuiProjList;

/**
 * 
 * JSON工厂类
 *
 */
public class ProjMuiProjListJsonFactory {

	public static ProjMuiProjList jsonToObject(JSONObject jsonObject) {
		ProjMuiProjList model = new ProjMuiProjList();
		if (jsonObject.containsKey("indexId")) {
			model.setIndexId(jsonObject.getInteger("indexId"));
		}
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("intFlag")) {
			model.setIntFlag(jsonObject.getInteger("intFlag"));
		}
		if (jsonObject.containsKey("sysId")) {
			model.setSysId(jsonObject.getString("sysId"));
		}
		if (jsonObject.containsKey("projName")) {
			model.setProjName(jsonObject.getString("projName"));
		}
		if (jsonObject.containsKey("num")) {
			model.setNum(jsonObject.getString("num"));
		}
		if (jsonObject.containsKey("ctime")) {
			model.setCtime(jsonObject.getDate("ctime"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("dbName")) {
			model.setDbName(jsonObject.getString("dbName"));
		}
		if (jsonObject.containsKey("serverName")) {
			model.setServerName(jsonObject.getString("serverName"));
		}
		if (jsonObject.containsKey("user")) {
			model.setUser(jsonObject.getString("user"));
		}
		if (jsonObject.containsKey("password")) {
			model.setPassword(jsonObject.getString("password"));
		}
		if (jsonObject.containsKey("listNo")) {
			model.setListNo(jsonObject.getInteger("listNo"));
		}
		if (jsonObject.containsKey("email")) {
			model.setEmail(jsonObject.getString("email"));
		}
		if (jsonObject.containsKey("parentId")) {
			model.setParentId(jsonObject.getInteger("parentId"));
		}
		if (jsonObject.containsKey("nodeIco")) {
			model.setNodeIco(jsonObject.getInteger("nodeIco"));
		}
		if (jsonObject.containsKey("intLine")) {
			model.setIntLine(jsonObject.getInteger("intLine"));
		}
		if (jsonObject.containsKey("domainIndex")) {
			model.setDomainIndex(jsonObject.getInteger("domainIndex"));
		}
		if (jsonObject.containsKey("intLocal")) {
			model.setIntLocal(jsonObject.getInteger("intLocal"));
		}
		if (jsonObject.containsKey("emailPSW")) {
			model.setEmailPSW(jsonObject.getString("emailPSW"));
		}
		if (jsonObject.containsKey("intConnected")) {
			model.setIntConnected(jsonObject.getInteger("intConnected"));
		}
		if (jsonObject.containsKey("emails")) {
			model.setEmails(jsonObject.getString("emails"));
		}
		if (jsonObject.containsKey("intorgLevel")) {
			model.setIntorgLevel(jsonObject.getInteger("intorgLevel"));
		}
		if (jsonObject.containsKey("intSendType")) {
			model.setIntSendType(jsonObject.getInteger("intSendType"));
		}
		if (jsonObject.containsKey("emailBackup")) {
			model.setEmailBackup(jsonObject.getString("emailBackup"));
		}
		if (jsonObject.containsKey("emailImplement")) {
			model.setEmailImplement(jsonObject.getString("emailImplement"));
		}

		return model;
	}

	public static JSONObject toJsonObject(ProjMuiProjList model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("indexId", model.getIndexId());
		jsonObject.put("_indexId_", model.getIndexId());
		if (model.getId() != null) {
			jsonObject.put("id", model.getId());
			jsonObject.put("_id_", model.getId());
			jsonObject.put("_oid_", model.getId());
		}
		jsonObject.put("intFlag", model.getIntFlag());
		if (model.getSysId() != null) {
			jsonObject.put("sysId", model.getSysId());
		}
		if (model.getProjName() != null) {
			jsonObject.put("projName", model.getProjName());
		}
		if (model.getNum() != null) {
			jsonObject.put("num", model.getNum());
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
		if (model.getDbName() != null) {
			jsonObject.put("dbName", model.getDbName());
		}
		if (model.getServerName() != null) {
			jsonObject.put("serverName", model.getServerName());
		}
		if (model.getUser() != null) {
			jsonObject.put("user", model.getUser());
		}
		if (model.getPassword() != null) {
			jsonObject.put("password", model.getPassword());
		}
		jsonObject.put("listNo", model.getListNo());
		if (model.getEmail() != null) {
			jsonObject.put("email", model.getEmail());
		}
		jsonObject.put("parentId", model.getParentId());
		jsonObject.put("nodeIco", model.getNodeIco());
		jsonObject.put("intLine", model.getIntLine());
		jsonObject.put("domainIndex", model.getDomainIndex());
		jsonObject.put("intLocal", model.getIntLocal());
		if (model.getEmailPSW() != null) {
			jsonObject.put("emailPSW", model.getEmailPSW());
		}
		jsonObject.put("intConnected", model.getIntConnected());
		if (model.getEmails() != null) {
			jsonObject.put("emails", model.getEmails());
		}
		jsonObject.put("intorgLevel", model.getIntorgLevel());
		jsonObject.put("intSendType", model.getIntSendType());
		if (model.getEmailBackup() != null) {
			jsonObject.put("emailBackup", model.getEmailBackup());
		}
		if (model.getEmailImplement() != null) {
			jsonObject.put("emailImplement", model.getEmailImplement());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(ProjMuiProjList model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("indexId", model.getIndexId());
		jsonObject.put("_indexId_", model.getIndexId());
		if (model.getId() != null) {
			jsonObject.put("id", model.getId());
			jsonObject.put("_id_", model.getId());
			jsonObject.put("_oid_", model.getId());
		}
		jsonObject.put("intFlag", model.getIntFlag());
		if (model.getSysId() != null) {
			jsonObject.put("sysId", model.getSysId());
		}
		if (model.getProjName() != null) {
			jsonObject.put("projName", model.getProjName());
		}
		if (model.getNum() != null) {
			jsonObject.put("num", model.getNum());
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
		if (model.getDbName() != null) {
			jsonObject.put("dbName", model.getDbName());
		}
		if (model.getServerName() != null) {
			jsonObject.put("serverName", model.getServerName());
		}
		if (model.getUser() != null) {
			jsonObject.put("user", model.getUser());
		}
		if (model.getPassword() != null) {
			jsonObject.put("password", model.getPassword());
		}
		jsonObject.put("listNo", model.getListNo());
		if (model.getEmail() != null) {
			jsonObject.put("email", model.getEmail());
		}
		jsonObject.put("parentId", model.getParentId());
		jsonObject.put("nodeIco", model.getNodeIco());
		jsonObject.put("intLine", model.getIntLine());
		jsonObject.put("domainIndex", model.getDomainIndex());
		jsonObject.put("intLocal", model.getIntLocal());
		if (model.getEmailPSW() != null) {
			jsonObject.put("emailPSW", model.getEmailPSW());
		}
		jsonObject.put("intConnected", model.getIntConnected());
		if (model.getEmails() != null) {
			jsonObject.put("emails", model.getEmails());
		}
		jsonObject.put("intorgLevel", model.getIntorgLevel());
		jsonObject.put("intSendType", model.getIntSendType());
		if (model.getEmailBackup() != null) {
			jsonObject.put("emailBackup", model.getEmailBackup());
		}
		if (model.getEmailImplement() != null) {
			jsonObject.put("emailImplement", model.getEmailImplement());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<ProjMuiProjList> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ProjMuiProjList model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ProjMuiProjList> arrayToList(JSONArray array) {
		java.util.List<ProjMuiProjList> list = new java.util.ArrayList<ProjMuiProjList>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ProjMuiProjList model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private ProjMuiProjListJsonFactory() {

	}

}
