package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.isdp.domain.ProjInspection;

/**
 * 
 * JSON工厂类
 *
 */
public class ProjInspectionJsonFactory {

	public static ProjInspection jsonToObject(JSONObject jsonObject) {
		ProjInspection model = new ProjInspection();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("indexId")) {
			model.setIndexId(jsonObject.getInteger("indexId"));
		}
		if (jsonObject.containsKey("intFlag")) {
			model.setIntFlag(jsonObject.getInteger("intFlag"));
		}
		if (jsonObject.containsKey("cellTmpFileTypeId")) {
			model.setCellTmpFileTypeId(jsonObject
					.getString("cellTmpFileTypeId"));
		}
		if (jsonObject.containsKey("listNo")) {
			model.setListNo(jsonObject.getInteger("listNo"));
		}
		if (jsonObject.containsKey("chkResult")) {
			model.setChkResult(jsonObject.getInteger("chkResult"));
		}
		if (jsonObject.containsKey("pfileId")) {
			model.setPfileId(jsonObject.getString("pfileId"));
		}
		if (jsonObject.containsKey("refillFlag")) {
			model.setRefillFlag(jsonObject.getInteger("refillFlag"));
		}
		if (jsonObject.containsKey("groupId")) {
			model.setGroupId(jsonObject.getInteger("groupId"));
		}
		if (jsonObject.containsKey("oldId")) {
			model.setOldId(jsonObject.getString("oldId"));
		}
		if (jsonObject.containsKey("emailId")) {
			model.setEmailId(jsonObject.getString("emailId"));
		}
		if (jsonObject.containsKey("recemailId")) {
			model.setRecemailId(jsonObject.getString("recemailId"));
		}
		if (jsonObject.containsKey("mainId")) {
			model.setMainId(jsonObject.getString("mainId"));
		}
		if (jsonObject.containsKey("tagNum")) {
			model.setTagNum(jsonObject.getString("tagNum"));
		}
		if (jsonObject.containsKey("ctime")) {
			model.setCtime(jsonObject.getDate("ctime"));
		}
		if (jsonObject.containsKey("tname")) {
			model.setTname(jsonObject.getString("tname"));
		}
		if (jsonObject.containsKey("page")) {
			model.setPage(jsonObject.getInteger("page"));
		}
		if (jsonObject.containsKey("duty")) {
			model.setDuty(jsonObject.getString("duty"));
		}
		if (jsonObject.containsKey("thematic")) {
			model.setThematic(jsonObject.getString("thematic"));
		}
		if (jsonObject.containsKey("annotations")) {
			model.setAnnotations(jsonObject.getString("annotations"));
		}

		return model;
	}

	public static JSONObject toJsonObject(ProjInspection model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("indexId", model.getIndexId());
		jsonObject.put("intFlag", model.getIntFlag());
		if (model.getCellTmpFileTypeId() != null) {
			jsonObject.put("cellTmpFileTypeId", model.getCellTmpFileTypeId());
		}
		jsonObject.put("listNo", model.getListNo());
		jsonObject.put("chkResult", model.getChkResult());
		if (model.getPfileId() != null) {
			jsonObject.put("pfileId", model.getPfileId());
		}
		jsonObject.put("refillFlag", model.getRefillFlag());
		jsonObject.put("groupId", model.getGroupId());
		if (model.getOldId() != null) {
			jsonObject.put("oldId", model.getOldId());
		}
		if (model.getEmailId() != null) {
			jsonObject.put("emailId", model.getEmailId());
		}
		if (model.getRecemailId() != null) {
			jsonObject.put("recemailId", model.getRecemailId());
		}
		if (model.getMainId() != null) {
			jsonObject.put("mainId", model.getMainId());
		}
		if (model.getTagNum() != null) {
			jsonObject.put("tagNum", model.getTagNum());
		}
		if (model.getCtime() != null) {
			jsonObject.put("ctime", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_date", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_datetime",
					DateUtils.getDateTime(model.getCtime()));
		}
		if (model.getTname() != null) {
			jsonObject.put("tname", model.getTname());
		}
		jsonObject.put("page", model.getPage());
		if (model.getDuty() != null) {
			jsonObject.put("duty", model.getDuty());
		}
		if (model.getThematic() != null) {
			jsonObject.put("thematic", model.getThematic());
		}
		if (model.getAnnotations() != null) {
			jsonObject.put("annotations", model.getAnnotations());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(ProjInspection model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("indexId", model.getIndexId());
		jsonObject.put("intFlag", model.getIntFlag());
		if (model.getCellTmpFileTypeId() != null) {
			jsonObject.put("cellTmpFileTypeId", model.getCellTmpFileTypeId());
		}
		jsonObject.put("listNo", model.getListNo());
		jsonObject.put("chkResult", model.getChkResult());
		if (model.getPfileId() != null) {
			jsonObject.put("pfileId", model.getPfileId());
		}
		jsonObject.put("refillFlag", model.getRefillFlag());
		jsonObject.put("groupId", model.getGroupId());
		if (model.getOldId() != null) {
			jsonObject.put("oldId", model.getOldId());
		}
		if (model.getEmailId() != null) {
			jsonObject.put("emailId", model.getEmailId());
		}
		if (model.getRecemailId() != null) {
			jsonObject.put("recemailId", model.getRecemailId());
		}
		if (model.getMainId() != null) {
			jsonObject.put("mainId", model.getMainId());
		}
		if (model.getTagNum() != null) {
			jsonObject.put("tagNum", model.getTagNum());
		}
		if (model.getCtime() != null) {
			jsonObject.put("ctime", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_date", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_datetime",
					DateUtils.getDateTime(model.getCtime()));
		}
		if (model.getTname() != null) {
			jsonObject.put("tname", model.getTname());
		}
		jsonObject.put("page", model.getPage());
		if (model.getDuty() != null) {
			jsonObject.put("duty", model.getDuty());
		}
		if (model.getThematic() != null) {
			jsonObject.put("thematic", model.getThematic());
		}
		if (model.getAnnotations() != null) {
			jsonObject.put("annotations", model.getAnnotations());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<ProjInspection> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ProjInspection model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ProjInspection> arrayToList(JSONArray array) {
		java.util.List<ProjInspection> list = new java.util.ArrayList<ProjInspection>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ProjInspection model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private ProjInspectionJsonFactory() {

	}

}
