package com.glaf.isdp.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.isdp.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class TreepInfoCountJsonFactory {

	public static TreepInfoCount jsonToObject(JSONObject jsonObject) {
		TreepInfoCount model = new TreepInfoCount();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("databaseId")) {
			model.setDatabaseId(jsonObject.getLong("databaseId"));
		}
		if (jsonObject.containsKey("mapping")) {
			model.setMapping(jsonObject.getString("mapping"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("section")) {
			model.setSection(jsonObject.getString("section"));
		}
		if (jsonObject.containsKey("indexId")) {
			model.setIndexId(jsonObject.getInteger("indexId"));
		}
		if (jsonObject.containsKey("parentId")) {
			model.setParentId(jsonObject.getInteger("parentId"));
		}
		if (jsonObject.containsKey("indexName")) {
			model.setIndexName(jsonObject.getString("indexName"));
		}
		if (jsonObject.containsKey("wbsIndex")) {
			model.setWbsIndex(jsonObject.getInteger("wbsIndex"));
		}
		if (jsonObject.containsKey("doubleValue1")) {
			model.setDoubleValue1(jsonObject.getDouble("doubleValue1"));
		}
		if (jsonObject.containsKey("doubleValue2")) {
			model.setDoubleValue2(jsonObject.getDouble("doubleValue2"));
		}
		if (jsonObject.containsKey("doubleValue3")) {
			model.setDoubleValue3(jsonObject.getDouble("doubleValue3"));
		}
		if (jsonObject.containsKey("doubleValue4")) {
			model.setDoubleValue4(jsonObject.getDouble("doubleValue4"));
		}
		if (jsonObject.containsKey("doubleValue5")) {
			model.setDoubleValue5(jsonObject.getDouble("doubleValue5"));
		}
		if (jsonObject.containsKey("doubleValue6")) {
			model.setDoubleValue6(jsonObject.getDouble("doubleValue6"));
		}
		if (jsonObject.containsKey("doubleValue7")) {
			model.setDoubleValue7(jsonObject.getDouble("doubleValue7"));
		}
		if (jsonObject.containsKey("doubleValue8")) {
			model.setDoubleValue8(jsonObject.getDouble("doubleValue8"));
		}
		if (jsonObject.containsKey("doubleValue9")) {
			model.setDoubleValue9(jsonObject.getDouble("doubleValue9"));
		}
		if (jsonObject.containsKey("doubleValue10")) {
			model.setDoubleValue10(jsonObject.getDouble("doubleValue10"));
		}
		if (jsonObject.containsKey("intValue1")) {
			model.setIntValue1(jsonObject.getInteger("intValue1"));
		}
		if (jsonObject.containsKey("intValue2")) {
			model.setIntValue2(jsonObject.getInteger("intValue2"));
		}
		if (jsonObject.containsKey("intValue3")) {
			model.setIntValue3(jsonObject.getInteger("intValue3"));
		}
		if (jsonObject.containsKey("intValue4")) {
			model.setIntValue4(jsonObject.getInteger("intValue4"));
		}
		if (jsonObject.containsKey("intValue5")) {
			model.setIntValue5(jsonObject.getInteger("intValue5"));
		}
		if (jsonObject.containsKey("intValue6")) {
			model.setIntValue6(jsonObject.getInteger("intValue6"));
		}
		if (jsonObject.containsKey("intValue7")) {
			model.setIntValue7(jsonObject.getInteger("intValue7"));
		}
		if (jsonObject.containsKey("intValue8")) {
			model.setIntValue8(jsonObject.getInteger("intValue8"));
		}
		if (jsonObject.containsKey("intValue9")) {
			model.setIntValue9(jsonObject.getInteger("intValue9"));
		}
		if (jsonObject.containsKey("intValue10")) {
			model.setIntValue10(jsonObject.getInteger("intValue10"));
		}
		if (jsonObject.containsKey("longValue1")) {
			model.setLongValue1(jsonObject.getLong("longValue1"));
		}
		if (jsonObject.containsKey("longValue2")) {
			model.setLongValue2(jsonObject.getLong("longValue2"));
		}
		if (jsonObject.containsKey("longValue3")) {
			model.setLongValue3(jsonObject.getLong("longValue3"));
		}
		if (jsonObject.containsKey("longValue4")) {
			model.setLongValue4(jsonObject.getLong("longValue4"));
		}
		if (jsonObject.containsKey("longValue5")) {
			model.setLongValue5(jsonObject.getLong("longValue5"));
		}
		if (jsonObject.containsKey("longValue6")) {
			model.setLongValue6(jsonObject.getLong("longValue6"));
		}
		if (jsonObject.containsKey("longValue7")) {
			model.setLongValue7(jsonObject.getLong("longValue7"));
		}
		if (jsonObject.containsKey("longValue8")) {
			model.setLongValue8(jsonObject.getLong("longValue8"));
		}
		if (jsonObject.containsKey("longValue9")) {
			model.setLongValue9(jsonObject.getLong("longValue9"));
		}
		if (jsonObject.containsKey("longValue10")) {
			model.setLongValue10(jsonObject.getLong("longValue10"));
		}
		if (jsonObject.containsKey("stringValue1")) {
			model.setStringValue1(jsonObject.getString("stringValue1"));
		}
		if (jsonObject.containsKey("stringValue2")) {
			model.setStringValue2(jsonObject.getString("stringValue2"));
		}
		if (jsonObject.containsKey("stringValue3")) {
			model.setStringValue3(jsonObject.getString("stringValue3"));
		}
		if (jsonObject.containsKey("stringValue4")) {
			model.setStringValue4(jsonObject.getString("stringValue4"));
		}
		if (jsonObject.containsKey("stringValue5")) {
			model.setStringValue5(jsonObject.getString("stringValue5"));
		}
		if (jsonObject.containsKey("stringValue6")) {
			model.setStringValue6(jsonObject.getString("stringValue6"));
		}
		if (jsonObject.containsKey("stringValue7")) {
			model.setStringValue7(jsonObject.getString("stringValue7"));
		}
		if (jsonObject.containsKey("stringValue8")) {
			model.setStringValue8(jsonObject.getString("stringValue8"));
		}
		if (jsonObject.containsKey("stringValue9")) {
			model.setStringValue9(jsonObject.getString("stringValue9"));
		}
		if (jsonObject.containsKey("stringValue10")) {
			model.setStringValue10(jsonObject.getString("stringValue10"));
		}
		if (jsonObject.containsKey("runYear")) {
			model.setRunYear(jsonObject.getInteger("runYear"));
		}
		if (jsonObject.containsKey("runMonth")) {
			model.setRunMonth(jsonObject.getInteger("runMonth"));
		}
		if (jsonObject.containsKey("runWeek")) {
			model.setRunWeek(jsonObject.getInteger("runWeek"));
		}
		if (jsonObject.containsKey("runQuarter")) {
			model.setRunQuarter(jsonObject.getInteger("runQuarter"));
		}
		if (jsonObject.containsKey("runDay")) {
			model.setRunDay(jsonObject.getInteger("runDay"));
		}
		if (jsonObject.containsKey("jobNo")) {
			model.setJobNo(jsonObject.getString("jobNo"));
		}

		return model;
	}

	public static JSONObject toJsonObject(TreepInfoCount model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("databaseId", model.getDatabaseId());
		if (model.getMapping() != null) {
			jsonObject.put("mapping", model.getMapping());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getSection() != null) {
			jsonObject.put("section", model.getSection());
		}
		if (model.getIndexId() != null) {
			jsonObject.put("indexId", model.getIndexId());
		}
		if (model.getParentId() != null) {
			jsonObject.put("parentId", model.getParentId());
		}
		if (model.getIndexName() != null) {
			jsonObject.put("indexName", model.getIndexName());
		}
		if (model.getWbsIndex() != null) {
			jsonObject.put("wbsIndex", model.getWbsIndex());
		}
		jsonObject.put("wbsIndex", model.getWbsIndex());
		jsonObject.put("doubleValue1", model.getDoubleValue1());
		jsonObject.put("doubleValue2", model.getDoubleValue2());
		jsonObject.put("doubleValue3", model.getDoubleValue3());
		jsonObject.put("doubleValue4", model.getDoubleValue4());
		jsonObject.put("doubleValue5", model.getDoubleValue5());
		jsonObject.put("doubleValue6", model.getDoubleValue6());
		jsonObject.put("doubleValue7", model.getDoubleValue7());
		jsonObject.put("doubleValue8", model.getDoubleValue8());
		jsonObject.put("doubleValue9", model.getDoubleValue9());
		jsonObject.put("doubleValue10", model.getDoubleValue10());
		jsonObject.put("intValue1", model.getIntValue1());
		jsonObject.put("intValue2", model.getIntValue2());
		jsonObject.put("intValue3", model.getIntValue3());
		jsonObject.put("intValue4", model.getIntValue4());
		jsonObject.put("intValue5", model.getIntValue5());
		jsonObject.put("intValue6", model.getIntValue6());
		jsonObject.put("intValue7", model.getIntValue7());
		jsonObject.put("intValue8", model.getIntValue8());
		jsonObject.put("intValue9", model.getIntValue9());
		jsonObject.put("intValue10", model.getIntValue10());
		jsonObject.put("longValue1", model.getLongValue1());
		jsonObject.put("longValue2", model.getLongValue2());
		jsonObject.put("longValue3", model.getLongValue3());
		jsonObject.put("longValue4", model.getLongValue4());
		jsonObject.put("longValue5", model.getLongValue5());
		jsonObject.put("longValue6", model.getLongValue6());
		jsonObject.put("longValue7", model.getLongValue7());
		jsonObject.put("longValue8", model.getLongValue8());
		jsonObject.put("longValue9", model.getLongValue9());
		jsonObject.put("longValue10", model.getLongValue10());
		if (model.getStringValue1() != null) {
			jsonObject.put("stringValue1", model.getStringValue1());
		}
		if (model.getStringValue2() != null) {
			jsonObject.put("stringValue2", model.getStringValue2());
		}
		if (model.getStringValue3() != null) {
			jsonObject.put("stringValue3", model.getStringValue3());
		}
		if (model.getStringValue4() != null) {
			jsonObject.put("stringValue4", model.getStringValue4());
		}
		if (model.getStringValue5() != null) {
			jsonObject.put("stringValue5", model.getStringValue5());
		}
		if (model.getStringValue6() != null) {
			jsonObject.put("stringValue6", model.getStringValue6());
		}
		if (model.getStringValue7() != null) {
			jsonObject.put("stringValue7", model.getStringValue7());
		}
		if (model.getStringValue8() != null) {
			jsonObject.put("stringValue8", model.getStringValue8());
		}
		if (model.getStringValue9() != null) {
			jsonObject.put("stringValue9", model.getStringValue9());
		}
		if (model.getStringValue10() != null) {
			jsonObject.put("stringValue10", model.getStringValue10());
		}
		jsonObject.put("runYear", model.getRunYear());
		jsonObject.put("runMonth", model.getRunMonth());
		jsonObject.put("runWeek", model.getRunWeek());
		jsonObject.put("runQuarter", model.getRunQuarter());
		jsonObject.put("runDay", model.getRunDay());
		if (model.getJobNo() != null) {
			jsonObject.put("jobNo", model.getJobNo());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(TreepInfoCount model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("databaseId", model.getDatabaseId());
		if (model.getMapping() != null) {
			jsonObject.put("mapping", model.getMapping());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getSection() != null) {
			jsonObject.put("section", model.getSection());
		}
		if (model.getIndexId() != null) {
			jsonObject.put("indexId", model.getIndexId());
		}
		if (model.getParentId() != null) {
			jsonObject.put("parentId", model.getParentId());
		}
		if (model.getIndexName() != null) {
			jsonObject.put("indexName", model.getIndexName());
		}
		if (model.getWbsIndex() != null) {
			jsonObject.put("wbsIndex", model.getWbsIndex());
		}
		jsonObject.put("doubleValue1", model.getDoubleValue1());
		jsonObject.put("doubleValue2", model.getDoubleValue2());
		jsonObject.put("doubleValue3", model.getDoubleValue3());
		jsonObject.put("doubleValue4", model.getDoubleValue4());
		jsonObject.put("doubleValue5", model.getDoubleValue5());
		jsonObject.put("doubleValue6", model.getDoubleValue6());
		jsonObject.put("doubleValue7", model.getDoubleValue7());
		jsonObject.put("doubleValue8", model.getDoubleValue8());
		jsonObject.put("doubleValue9", model.getDoubleValue9());
		jsonObject.put("doubleValue10", model.getDoubleValue10());
		jsonObject.put("intValue1", model.getIntValue1());
		jsonObject.put("intValue2", model.getIntValue2());
		jsonObject.put("intValue3", model.getIntValue3());
		jsonObject.put("intValue4", model.getIntValue4());
		jsonObject.put("intValue5", model.getIntValue5());
		jsonObject.put("intValue6", model.getIntValue6());
		jsonObject.put("intValue7", model.getIntValue7());
		jsonObject.put("intValue8", model.getIntValue8());
		jsonObject.put("intValue9", model.getIntValue9());
		jsonObject.put("intValue10", model.getIntValue10());
		jsonObject.put("longValue1", model.getLongValue1());
		jsonObject.put("longValue2", model.getLongValue2());
		jsonObject.put("longValue3", model.getLongValue3());
		jsonObject.put("longValue4", model.getLongValue4());
		jsonObject.put("longValue5", model.getLongValue5());
		jsonObject.put("longValue6", model.getLongValue6());
		jsonObject.put("longValue7", model.getLongValue7());
		jsonObject.put("longValue8", model.getLongValue8());
		jsonObject.put("longValue9", model.getLongValue9());
		jsonObject.put("longValue10", model.getLongValue10());
		if (model.getStringValue1() != null) {
			jsonObject.put("stringValue1", model.getStringValue1());
		}
		if (model.getStringValue2() != null) {
			jsonObject.put("stringValue2", model.getStringValue2());
		}
		if (model.getStringValue3() != null) {
			jsonObject.put("stringValue3", model.getStringValue3());
		}
		if (model.getStringValue4() != null) {
			jsonObject.put("stringValue4", model.getStringValue4());
		}
		if (model.getStringValue5() != null) {
			jsonObject.put("stringValue5", model.getStringValue5());
		}
		if (model.getStringValue6() != null) {
			jsonObject.put("stringValue6", model.getStringValue6());
		}
		if (model.getStringValue7() != null) {
			jsonObject.put("stringValue7", model.getStringValue7());
		}
		if (model.getStringValue8() != null) {
			jsonObject.put("stringValue8", model.getStringValue8());
		}
		if (model.getStringValue9() != null) {
			jsonObject.put("stringValue9", model.getStringValue9());
		}
		if (model.getStringValue10() != null) {
			jsonObject.put("stringValue10", model.getStringValue10());
		}
		jsonObject.put("runYear", model.getRunYear());
		jsonObject.put("runMonth", model.getRunMonth());
		jsonObject.put("runWeek", model.getRunWeek());
		jsonObject.put("runQuarter", model.getRunQuarter());
		jsonObject.put("runDay", model.getRunDay());
		if (model.getJobNo() != null) {
			jsonObject.put("jobNo", model.getJobNo());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<TreepInfoCount> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (TreepInfoCount model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<TreepInfoCount> arrayToList(JSONArray array) {
		java.util.List<TreepInfoCount> list = new java.util.ArrayList<TreepInfoCount>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			TreepInfoCount model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private TreepInfoCountJsonFactory() {

	}

}
