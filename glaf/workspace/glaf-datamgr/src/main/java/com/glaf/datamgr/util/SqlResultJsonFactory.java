package com.glaf.datamgr.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.datamgr.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class SqlResultJsonFactory {

	public static SqlResult jsonToObject(JSONObject jsonObject) {
		SqlResult model = new SqlResult();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("databaseId")) {
			model.setDatabaseId(jsonObject.getLong("databaseId"));
		}
		if (jsonObject.containsKey("sqlDefId")) {
			model.setSqlDefId(jsonObject.getLong("sqlDefId"));
		}
		if (jsonObject.containsKey("projectId")) {
			model.setProjectId(jsonObject.getLong("projectId"));
		}
		if (jsonObject.containsKey("count")) {
			model.setCount(jsonObject.getInteger("count"));
		}
		if (jsonObject.containsKey("value")) {
			model.setValue(jsonObject.getDouble("value"));
		}
		if (jsonObject.containsKey("ext1")) {
			model.setExt1(jsonObject.getString("ext1"));
		}
		if (jsonObject.containsKey("ext2")) {
			model.setExt2(jsonObject.getString("ext2"));
		}
		if (jsonObject.containsKey("ext3")) {
			model.setExt3(jsonObject.getString("ext3"));
		}
		if (jsonObject.containsKey("ext4")) {
			model.setExt4(jsonObject.getString("ext4"));
		}
		if (jsonObject.containsKey("ext5")) {
			model.setExt5(jsonObject.getString("ext5"));
		}
		if (jsonObject.containsKey("ext6")) {
			model.setExt6(jsonObject.getString("ext6"));
		}
		if (jsonObject.containsKey("ext7")) {
			model.setExt7(jsonObject.getString("ext7"));
		}
		if (jsonObject.containsKey("ext8")) {
			model.setExt8(jsonObject.getString("ext8"));
		}
		if (jsonObject.containsKey("ext9")) {
			model.setExt9(jsonObject.getString("ext9"));
		}
		if (jsonObject.containsKey("ext10")) {
			model.setExt10(jsonObject.getString("ext10"));
		}

		if (jsonObject.containsKey("ext101")) {
			model.setExt101(jsonObject.getDouble("ext101"));
		}
		if (jsonObject.containsKey("ext102")) {
			model.setExt102(jsonObject.getDouble("ext102"));
		}
		if (jsonObject.containsKey("ext103")) {
			model.setExt103(jsonObject.getDouble("ext103"));
		}
		if (jsonObject.containsKey("ext104")) {
			model.setExt104(jsonObject.getDouble("ext104"));
		}
		if (jsonObject.containsKey("ext105")) {
			model.setExt105(jsonObject.getDouble("ext105"));
		}
		if (jsonObject.containsKey("ext106")) {
			model.setExt106(jsonObject.getDouble("ext106"));
		}
		if (jsonObject.containsKey("ext107")) {
			model.setExt107(jsonObject.getDouble("ext107"));
		}
		if (jsonObject.containsKey("ext108")) {
			model.setExt108(jsonObject.getDouble("ext108"));
		}
		if (jsonObject.containsKey("ext109")) {
			model.setExt109(jsonObject.getDouble("ext109"));
		}
		if (jsonObject.containsKey("ext110")) {
			model.setExt110(jsonObject.getDouble("ext110"));
		}
		if (jsonObject.containsKey("ext111")) {
			model.setExt111(jsonObject.getDouble("ext111"));
		}
		if (jsonObject.containsKey("ext112")) {
			model.setExt112(jsonObject.getDouble("ext112"));
		}
		if (jsonObject.containsKey("ext113")) {
			model.setExt113(jsonObject.getDouble("ext113"));
		}
		if (jsonObject.containsKey("ext114")) {
			model.setExt114(jsonObject.getDouble("ext114"));
		}
		if (jsonObject.containsKey("ext115")) {
			model.setExt115(jsonObject.getDouble("ext115"));
		}
		if (jsonObject.containsKey("ext116")) {
			model.setExt116(jsonObject.getDouble("ext116"));
		}
		if (jsonObject.containsKey("ext117")) {
			model.setExt117(jsonObject.getDouble("ext117"));
		}
		if (jsonObject.containsKey("ext118")) {
			model.setExt118(jsonObject.getDouble("ext118"));
		}
		if (jsonObject.containsKey("ext119")) {
			model.setExt119(jsonObject.getDouble("ext119"));
		}
		if (jsonObject.containsKey("ext120")) {
			model.setExt120(jsonObject.getDouble("ext120"));
		}
		if (jsonObject.containsKey("ext201")) {
			model.setExt201(jsonObject.getLong("ext201"));
		}
		if (jsonObject.containsKey("ext202")) {
			model.setExt202(jsonObject.getLong("ext202"));
		}
		if (jsonObject.containsKey("ext203")) {
			model.setExt203(jsonObject.getLong("ext203"));
		}
		if (jsonObject.containsKey("ext204")) {
			model.setExt204(jsonObject.getLong("ext204"));
		}
		if (jsonObject.containsKey("ext205")) {
			model.setExt205(jsonObject.getLong("ext205"));
		}
		if (jsonObject.containsKey("ext206")) {
			model.setExt206(jsonObject.getLong("ext206"));
		}
		if (jsonObject.containsKey("ext207")) {
			model.setExt207(jsonObject.getLong("ext207"));
		}
		if (jsonObject.containsKey("ext208")) {
			model.setExt208(jsonObject.getLong("ext208"));
		}
		if (jsonObject.containsKey("ext209")) {
			model.setExt209(jsonObject.getLong("ext209"));
		}
		if (jsonObject.containsKey("ext210")) {
			model.setExt210(jsonObject.getLong("ext210"));
		}
		if (jsonObject.containsKey("ext301")) {
			model.setExt301(jsonObject.getInteger("ext301"));
		}
		if (jsonObject.containsKey("ext302")) {
			model.setExt302(jsonObject.getInteger("ext302"));
		}
		if (jsonObject.containsKey("ext303")) {
			model.setExt303(jsonObject.getInteger("ext303"));
		}
		if (jsonObject.containsKey("ext304")) {
			model.setExt304(jsonObject.getInteger("ext304"));
		}
		if (jsonObject.containsKey("ext305")) {
			model.setExt305(jsonObject.getInteger("ext305"));
		}
		if (jsonObject.containsKey("ext306")) {
			model.setExt306(jsonObject.getInteger("ext306"));
		}
		if (jsonObject.containsKey("ext307")) {
			model.setExt307(jsonObject.getInteger("ext307"));
		}
		if (jsonObject.containsKey("ext308")) {
			model.setExt308(jsonObject.getInteger("ext308"));
		}
		if (jsonObject.containsKey("ext309")) {
			model.setExt309(jsonObject.getInteger("ext309"));
		}
		if (jsonObject.containsKey("ext310")) {
			model.setExt310(jsonObject.getInteger("ext310"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
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
		if (jsonObject.containsKey("operation")) {
			model.setOperation(jsonObject.getString("operation"));
		}

		return model;
	}

	public static JSONObject toJsonObject(SqlResult model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("databaseId", model.getDatabaseId());
		jsonObject.put("sqlDefId", model.getSqlDefId());
		jsonObject.put("projectId", model.getProjectId());
		jsonObject.put("count", model.getCount());
		jsonObject.put("value", model.getValue());
		if (model.getExt1() != null) {
			jsonObject.put("ext1", model.getExt1());
		}
		if (model.getExt2() != null) {
			jsonObject.put("ext2", model.getExt2());
		}
		if (model.getExt3() != null) {
			jsonObject.put("ext3", model.getExt3());
		}
		if (model.getExt4() != null) {
			jsonObject.put("ext4", model.getExt4());
		}
		if (model.getExt5() != null) {
			jsonObject.put("ext5", model.getExt5());
		}
		if (model.getExt6() != null) {
			jsonObject.put("ext6", model.getExt6());
		}
		if (model.getExt7() != null) {
			jsonObject.put("ext7", model.getExt7());
		}
		if (model.getExt8() != null) {
			jsonObject.put("ext8", model.getExt8());
		}
		if (model.getExt9() != null) {
			jsonObject.put("ext9", model.getExt9());
		}
		if (model.getExt10() != null) {
			jsonObject.put("ext10", model.getExt10());
		}

		jsonObject.put("ext101", model.getExt101());
		jsonObject.put("ext102", model.getExt102());
		jsonObject.put("ext103", model.getExt103());
		jsonObject.put("ext104", model.getExt104());
		jsonObject.put("ext105", model.getExt105());
		jsonObject.put("ext106", model.getExt106());
		jsonObject.put("ext107", model.getExt107());
		jsonObject.put("ext108", model.getExt108());
		jsonObject.put("ext109", model.getExt109());
		jsonObject.put("ext110", model.getExt110());
		jsonObject.put("ext111", model.getExt111());
		jsonObject.put("ext112", model.getExt112());
		jsonObject.put("ext113", model.getExt113());
		jsonObject.put("ext114", model.getExt114());
		jsonObject.put("ext115", model.getExt115());
		jsonObject.put("ext116", model.getExt116());
		jsonObject.put("ext117", model.getExt117());
		jsonObject.put("ext118", model.getExt118());
		jsonObject.put("ext119", model.getExt119());
		jsonObject.put("ext120", model.getExt120());
		jsonObject.put("ext201", model.getExt201());
		jsonObject.put("ext202", model.getExt202());
		jsonObject.put("ext203", model.getExt203());
		jsonObject.put("ext204", model.getExt204());
		jsonObject.put("ext205", model.getExt205());
		jsonObject.put("ext206", model.getExt206());
		jsonObject.put("ext207", model.getExt207());
		jsonObject.put("ext208", model.getExt208());
		jsonObject.put("ext209", model.getExt209());
		jsonObject.put("ext210", model.getExt210());
		jsonObject.put("ext301", model.getExt301());
		jsonObject.put("ext302", model.getExt302());
		jsonObject.put("ext303", model.getExt303());
		jsonObject.put("ext304", model.getExt304());
		jsonObject.put("ext305", model.getExt305());
		jsonObject.put("ext306", model.getExt306());
		jsonObject.put("ext307", model.getExt307());
		jsonObject.put("ext308", model.getExt308());
		jsonObject.put("ext309", model.getExt309());
		jsonObject.put("ext310", model.getExt310());
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		jsonObject.put("runYear", model.getRunYear());
		jsonObject.put("runMonth", model.getRunMonth());
		jsonObject.put("runWeek", model.getRunWeek());
		jsonObject.put("runQuarter", model.getRunQuarter());
		jsonObject.put("runDay", model.getRunDay());
		if (model.getJobNo() != null) {
			jsonObject.put("jobNo", model.getJobNo());
		}
		if (model.getOperation() != null) {
			jsonObject.put("operation", model.getOperation());
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SqlResult model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("databaseId", model.getDatabaseId());
		jsonObject.put("sqlDefId", model.getSqlDefId());
		jsonObject.put("projectId", model.getProjectId());
		jsonObject.put("count", model.getCount());
		jsonObject.put("value", model.getValue());
		if (model.getExt1() != null) {
			jsonObject.put("ext1", model.getExt1());
		}
		if (model.getExt2() != null) {
			jsonObject.put("ext2", model.getExt2());
		}
		if (model.getExt3() != null) {
			jsonObject.put("ext3", model.getExt3());
		}
		if (model.getExt4() != null) {
			jsonObject.put("ext4", model.getExt4());
		}
		if (model.getExt5() != null) {
			jsonObject.put("ext5", model.getExt5());
		}
		if (model.getExt6() != null) {
			jsonObject.put("ext6", model.getExt6());
		}
		if (model.getExt7() != null) {
			jsonObject.put("ext7", model.getExt7());
		}
		if (model.getExt8() != null) {
			jsonObject.put("ext8", model.getExt8());
		}
		if (model.getExt9() != null) {
			jsonObject.put("ext9", model.getExt9());
		}
		if (model.getExt10() != null) {
			jsonObject.put("ext10", model.getExt10());
		}

		jsonObject.put("ext101", model.getExt101());
		jsonObject.put("ext102", model.getExt102());
		jsonObject.put("ext103", model.getExt103());
		jsonObject.put("ext104", model.getExt104());
		jsonObject.put("ext105", model.getExt105());
		jsonObject.put("ext106", model.getExt106());
		jsonObject.put("ext107", model.getExt107());
		jsonObject.put("ext108", model.getExt108());
		jsonObject.put("ext109", model.getExt109());
		jsonObject.put("ext110", model.getExt110());
		jsonObject.put("ext111", model.getExt111());
		jsonObject.put("ext112", model.getExt112());
		jsonObject.put("ext113", model.getExt113());
		jsonObject.put("ext114", model.getExt114());
		jsonObject.put("ext115", model.getExt115());
		jsonObject.put("ext116", model.getExt116());
		jsonObject.put("ext117", model.getExt117());
		jsonObject.put("ext118", model.getExt118());
		jsonObject.put("ext119", model.getExt119());
		jsonObject.put("ext120", model.getExt120());
		jsonObject.put("ext201", model.getExt201());
		jsonObject.put("ext202", model.getExt202());
		jsonObject.put("ext203", model.getExt203());
		jsonObject.put("ext204", model.getExt204());
		jsonObject.put("ext205", model.getExt205());
		jsonObject.put("ext206", model.getExt206());
		jsonObject.put("ext207", model.getExt207());
		jsonObject.put("ext208", model.getExt208());
		jsonObject.put("ext209", model.getExt209());
		jsonObject.put("ext210", model.getExt210());
		jsonObject.put("ext301", model.getExt301());
		jsonObject.put("ext302", model.getExt302());
		jsonObject.put("ext303", model.getExt303());
		jsonObject.put("ext304", model.getExt304());
		jsonObject.put("ext305", model.getExt305());
		jsonObject.put("ext306", model.getExt306());
		jsonObject.put("ext307", model.getExt307());
		jsonObject.put("ext308", model.getExt308());
		jsonObject.put("ext309", model.getExt309());
		jsonObject.put("ext310", model.getExt310());
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		jsonObject.put("runYear", model.getRunYear());
		jsonObject.put("runMonth", model.getRunMonth());
		jsonObject.put("runWeek", model.getRunWeek());
		jsonObject.put("runQuarter", model.getRunQuarter());
		jsonObject.put("runDay", model.getRunDay());
		if (model.getJobNo() != null) {
			jsonObject.put("jobNo", model.getJobNo());
		}
		if (model.getOperation() != null) {
			jsonObject.put("operation", model.getOperation());
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<SqlResult> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SqlResult model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SqlResult> arrayToList(JSONArray array) {
		java.util.List<SqlResult> list = new java.util.ArrayList<SqlResult>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SqlResult model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private SqlResultJsonFactory() {

	}

}
