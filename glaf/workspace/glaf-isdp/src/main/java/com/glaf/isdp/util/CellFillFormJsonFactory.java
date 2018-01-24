package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.isdp.domain.CellFillForm;

/**
 * 
 * JSON工厂类
 *
 */
public class CellFillFormJsonFactory {

	public static CellFillForm jsonToObject(JSONObject jsonObject) {
		CellFillForm model = new CellFillForm();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("topId")) {
			model.setTopId(jsonObject.getString("topId"));
		}
		if (jsonObject.containsKey("indexId")) {
			model.setIndexId(jsonObject.getInteger("indexId"));
		}
		if (jsonObject.containsKey("taskId")) {
			model.setTaskId(jsonObject.getString("taskId"));
		}
		if (jsonObject.containsKey("pfileFlag")) {
			model.setPfileFlag(jsonObject.getInteger("pfileFlag"));
		}
		if (jsonObject.containsKey("fileDotFileId")) {
			model.setFileDotFileId(jsonObject.getString("fileDotFileId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("chkNum")) {
			model.setChkNum(jsonObject.getString("chkNum"));
		}
		if (jsonObject.containsKey("listNo")) {
			model.setListNo(jsonObject.getInteger("listNo"));
		}
		if (jsonObject.containsKey("chkTotal")) {
			model.setChkTotal(jsonObject.getInteger("chkTotal"));
		}
		if (jsonObject.containsKey("chkResult")) {
			model.setChkResult(jsonObject.getInteger("chkResult"));
		}
		if (jsonObject.containsKey("pfileId")) {
			model.setPfileId(jsonObject.getString("pfileId"));
		}
		if (jsonObject.containsKey("tempSave")) {
			model.setTempSave(jsonObject.getInteger("tempSave"));
		}
		if (jsonObject.containsKey("userId")) {
			model.setUserId(jsonObject.getString("userId"));
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
		if (jsonObject.containsKey("roleId")) {
			model.setRoleId(jsonObject.getInteger("roleId"));
		}
		if (jsonObject.containsKey("isFinish")) {
			model.setIsFinish(jsonObject.getInteger("isFinish"));
		}
		if (jsonObject.containsKey("typeTableName")) {
			model.setTypeTableName(jsonObject.getString("typeTableName"));
		}
		if (jsonObject.containsKey("typeId")) {
			model.setTypeId(jsonObject.getString("typeId"));
		}
		if (jsonObject.containsKey("typeIndexId")) {
			model.setTypeIndexId(jsonObject.getInteger("typeIndexId"));
		}
		if (jsonObject.containsKey("mainId")) {
			model.setMainId(jsonObject.getString("mainId"));
		}
		if (jsonObject.containsKey("intLastPage")) {
			model.setIntLastPage(jsonObject.getInteger("intLastPage"));
		}
		if (jsonObject.containsKey("intSheets")) {
			model.setIntSheets(jsonObject.getInteger("intSheets"));
		}

		return model;
	}

	public static JSONObject toJsonObject(CellFillForm model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTopId() != null) {
			jsonObject.put("topId", model.getTopId());
		}
		jsonObject.put("indexId", model.getIndexId());
		if (model.getTaskId() != null) {
			jsonObject.put("taskId", model.getTaskId());
		}
		jsonObject.put("pfileFlag", model.getPfileFlag());
		if (model.getFileDotFileId() != null) {
			jsonObject.put("fileDotFileId", model.getFileDotFileId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getChkNum() != null) {
			jsonObject.put("chkNum", model.getChkNum());
		}
		jsonObject.put("listNo", model.getListNo());
		jsonObject.put("chkTotal", model.getChkTotal());
		jsonObject.put("chkResult", model.getChkResult());
		if (model.getPfileId() != null) {
			jsonObject.put("pfileId", model.getPfileId());
		}
		jsonObject.put("tempSave", model.getTempSave());
		if (model.getUserId() != null) {
			jsonObject.put("userId", model.getUserId());
		}
		jsonObject.put("refillFlag", model.getRefillFlag());
		jsonObject.put("groupId", model.getGroupId());
		if (model.getOldId() != null) {
			jsonObject.put("oldId", model.getOldId());
		}
		jsonObject.put("roleId", model.getRoleId());
		jsonObject.put("isFinish", model.getIsFinish());
		if (model.getTypeTableName() != null) {
			jsonObject.put("typeTableName", model.getTypeTableName());
		}
		if (model.getTypeId() != null) {
			jsonObject.put("typeId", model.getTypeId());
		}
		jsonObject.put("typeIndexId", model.getTypeIndexId());
		if (model.getMainId() != null) {
			jsonObject.put("mainId", model.getMainId());
		}
		jsonObject.put("intLastPage", model.getIntLastPage());
		jsonObject.put("intSheets", model.getIntSheets());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(CellFillForm model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTopId() != null) {
			jsonObject.put("topId", model.getTopId());
		}
		jsonObject.put("indexId", model.getIndexId());
		if (model.getTaskId() != null) {
			jsonObject.put("taskId", model.getTaskId());
		}
		jsonObject.put("pfileFlag", model.getPfileFlag());
		if (model.getFileDotFileId() != null) {
			jsonObject.put("fileDotFileId", model.getFileDotFileId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getChkNum() != null) {
			jsonObject.put("chkNum", model.getChkNum());
		}
		jsonObject.put("listNo", model.getListNo());
		jsonObject.put("chkTotal", model.getChkTotal());
		jsonObject.put("chkResult", model.getChkResult());
		if (model.getPfileId() != null) {
			jsonObject.put("pfileId", model.getPfileId());
		}
		jsonObject.put("tempSave", model.getTempSave());
		if (model.getUserId() != null) {
			jsonObject.put("userId", model.getUserId());
		}
		jsonObject.put("refillFlag", model.getRefillFlag());
		jsonObject.put("groupId", model.getGroupId());
		if (model.getOldId() != null) {
			jsonObject.put("oldId", model.getOldId());
		}
		jsonObject.put("roleId", model.getRoleId());
		jsonObject.put("isFinish", model.getIsFinish());
		if (model.getTypeTableName() != null) {
			jsonObject.put("typeTableName", model.getTypeTableName());
		}
		if (model.getTypeId() != null) {
			jsonObject.put("typeId", model.getTypeId());
		}
		jsonObject.put("typeIndexId", model.getTypeIndexId());
		if (model.getMainId() != null) {
			jsonObject.put("mainId", model.getMainId());
		}
		jsonObject.put("intLastPage", model.getIntLastPage());
		jsonObject.put("intSheets", model.getIntSheets());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<CellFillForm> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (CellFillForm model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<CellFillForm> arrayToList(JSONArray array) {
		java.util.List<CellFillForm> list = new java.util.ArrayList<CellFillForm>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			CellFillForm model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private CellFillFormJsonFactory() {

	}

}
