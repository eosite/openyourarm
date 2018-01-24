package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.isdp.domain.CellRepInfo2;

/**
 * 
 * JSON工厂类
 *
 */
public class CellRepInfo2JsonFactory {

	public static CellRepInfo2 jsonToObject(JSONObject jsonObject) {
		CellRepInfo2 model = new CellRepInfo2();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("frmType")) {
			model.setFrmType(jsonObject.getString("frmType"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getInteger("type"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("formula")) {
			model.setFormula(jsonObject.getString("formula"));
		}
		if (jsonObject.containsKey("ostTableName")) {
			model.setOstTableName(jsonObject.getString("ostTableName"));
		}
		if (jsonObject.containsKey("ostRow")) {
			model.setOstRow(jsonObject.getInteger("ostRow"));
		}
		if (jsonObject.containsKey("ostCol")) {
			model.setOstCol(jsonObject.getInteger("ostCol"));
		}
		if (jsonObject.containsKey("ostRowEnd")) {
			model.setOstRowEnd(jsonObject.getInteger("ostRowEnd"));
		}
		if (jsonObject.containsKey("ostColEnd")) {
			model.setOstColEnd(jsonObject.getInteger("ostColEnd"));
		}
		if (jsonObject.containsKey("ostCellId")) {
			model.setOstCellId(jsonObject.getString("ostCellId"));
		}
		if (jsonObject.containsKey("fileDotFileId")) {
			model.setFileDotFileId(jsonObject.getString("fileDotFileId"));
		}
		if (jsonObject.containsKey("ostColor")) {
			model.setOstColor(jsonObject.getInteger("ostColor"));
		}
		if (jsonObject.containsKey("ostWay")) {
			model.setOstWay(jsonObject.getInteger("ostWay"));
		}
		if (jsonObject.containsKey("roleId")) {
			model.setRoleId(jsonObject.getInteger("roleId"));
		}
		if (jsonObject.containsKey("tableName")) {
			model.setTableName(jsonObject.getString("tableName"));
		}
		if (jsonObject.containsKey("fname")) {
			model.setFname(jsonObject.getString("fname"));
		}
		if (jsonObject.containsKey("dname")) {
			model.setDname(jsonObject.getString("dname"));
		}
		if (jsonObject.containsKey("isSubTable")) {
			model.setIsSubTable(jsonObject.getString("isSubTable"));
		}
		if (jsonObject.containsKey("tableName2")) {
			model.setTableName2(jsonObject.getString("tableName2"));
		}
		if (jsonObject.containsKey("intAutoinValue")) {
			model.setIntAutoinValue(jsonObject.getInteger("intAutoinValue"));
		}
		if (jsonObject.containsKey("intSelfClick")) {
			model.setIntSelfClick(jsonObject.getInteger("intSelfClick"));
		}

		return model;
	}

	public static JSONObject toJsonObject(CellRepInfo2 model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getFrmType() != null) {
			jsonObject.put("frmType", model.getFrmType());
		}
		jsonObject.put("type", model.getType());
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getFormula() != null) {
			jsonObject.put("formula", model.getFormula());
		}
		if (model.getOstTableName() != null) {
			jsonObject.put("ostTableName", model.getOstTableName());
		}
		jsonObject.put("ostRow", model.getOstRow());
		jsonObject.put("ostCol", model.getOstCol());
		jsonObject.put("ostRowEnd", model.getOstRowEnd());
		jsonObject.put("ostColEnd", model.getOstColEnd());
		if (model.getOstCellId() != null) {
			jsonObject.put("ostCellId", model.getOstCellId());
		}
		if (model.getFileDotFileId() != null) {
			jsonObject.put("fileDotFileId", model.getFileDotFileId());
		}
		jsonObject.put("ostColor", model.getOstColor());
		jsonObject.put("ostWay", model.getOstWay());
		jsonObject.put("roleId", model.getRoleId());
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		}
		if (model.getFname() != null) {
			jsonObject.put("fname", model.getFname());
		}
		if (model.getDname() != null) {
			jsonObject.put("dname", model.getDname());
		}
		if (model.getIsSubTable() != null) {
			jsonObject.put("isSubTable", model.getIsSubTable());
		}
		if (model.getTableName2() != null) {
			jsonObject.put("tableName2", model.getTableName2());
		}
		jsonObject.put("intAutoinValue", model.getIntAutoinValue());
		jsonObject.put("intSelfClick", model.getIntSelfClick());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(CellRepInfo2 model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getFrmType() != null) {
			jsonObject.put("frmType", model.getFrmType());
		}
		jsonObject.put("type", model.getType());
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getFormula() != null) {
			jsonObject.put("formula", model.getFormula());
		}
		if (model.getOstTableName() != null) {
			jsonObject.put("ostTableName", model.getOstTableName());
		}
		jsonObject.put("ostRow", model.getOstRow());
		jsonObject.put("ostCol", model.getOstCol());
		jsonObject.put("ostRowEnd", model.getOstRowEnd());
		jsonObject.put("ostColEnd", model.getOstColEnd());
		if (model.getOstCellId() != null) {
			jsonObject.put("ostCellId", model.getOstCellId());
		}
		if (model.getFileDotFileId() != null) {
			jsonObject.put("fileDotFileId", model.getFileDotFileId());
		}
		jsonObject.put("ostColor", model.getOstColor());
		jsonObject.put("ostWay", model.getOstWay());
		jsonObject.put("roleId", model.getRoleId());
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		}
		if (model.getFname() != null) {
			jsonObject.put("fname", model.getFname());
		}
		if (model.getDname() != null) {
			jsonObject.put("dname", model.getDname());
		}
		if (model.getIsSubTable() != null) {
			jsonObject.put("isSubTable", model.getIsSubTable());
		}
		if (model.getTableName2() != null) {
			jsonObject.put("tableName2", model.getTableName2());
		}
		jsonObject.put("intAutoinValue", model.getIntAutoinValue());
		jsonObject.put("intSelfClick", model.getIntSelfClick());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<CellRepInfo2> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (CellRepInfo2 model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<CellRepInfo2> arrayToList(JSONArray array) {
		java.util.List<CellRepInfo2> list = new java.util.ArrayList<CellRepInfo2>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			CellRepInfo2 model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private CellRepInfo2JsonFactory() {

	}

}
