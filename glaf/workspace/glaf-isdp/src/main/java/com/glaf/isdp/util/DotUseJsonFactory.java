package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.isdp.domain.DotUse;

/**
 * 
 * JSON工厂类
 *
 */
public class DotUseJsonFactory {

	public static DotUse jsonToObject(JSONObject jsonObject) {
		DotUse model = new DotUse();
		if (jsonObject.containsKey("fileID")) {
			model.setFileID(jsonObject.getString("fileID"));
		}
		if (jsonObject.containsKey("indexId")) {
			model.setIndexId(jsonObject.getInteger("indexId"));
		}
		if (jsonObject.containsKey("projId")) {
			model.setProjId(jsonObject.getInteger("projId"));
		}
		if (jsonObject.containsKey("pid")) {
			model.setPid(jsonObject.getInteger("pid"));
		}
		if (jsonObject.containsKey("dotId")) {
			model.setDotId(jsonObject.getString("dotId"));
		}
		if (jsonObject.containsKey("num")) {
			model.setNum(jsonObject.getString("num"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("cman")) {
			model.setCman(jsonObject.getString("cman"));
		}
		if (jsonObject.containsKey("ctime")) {
			model.setCtime(jsonObject.getDate("ctime"));
		}
		if (jsonObject.containsKey("fileName")) {
			model.setFileName(jsonObject.getString("fileName"));
		}
		if (jsonObject.containsKey("fileContent")) {
			//model.setFileContent(jsonObject.getString("fileContent"));
		}
		if (jsonObject.containsKey("fileSize")) {
			model.setFileSize(jsonObject.getInteger("fileSize"));
		}
		if (jsonObject.containsKey("vision")) {
			model.setVision(jsonObject.getString("vision"));
		}
		if (jsonObject.containsKey("saveTime")) {
			model.setSaveTime(jsonObject.getString("saveTime"));
		}
		if (jsonObject.containsKey("remark")) {
			model.setRemark(jsonObject.getString("remark"));
		}
		if (jsonObject.containsKey("dwid")) {
			model.setDwid(jsonObject.getInteger("dwid"));
		}
		if (jsonObject.containsKey("fbid")) {
			model.setFbid(jsonObject.getInteger("fbid"));
		}
		if (jsonObject.containsKey("fxid")) {
			model.setFxid(jsonObject.getInteger("fxid"));
		}
		if (jsonObject.containsKey("jid")) {
			model.setJid(jsonObject.getString("jid"));
		}
		if (jsonObject.containsKey("flid")) {
			model.setFlid(jsonObject.getString("flid"));
		}
		if (jsonObject.containsKey("topNode")) {
			model.setTopNode(jsonObject.getString("topNode"));
		}
		if (jsonObject.containsKey("topId")) {
			model.setTopId(jsonObject.getString("topId"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getInteger("type"));
		}
		if (jsonObject.containsKey("fname")) {
			model.setFname(jsonObject.getString("fname"));
		}
		if (jsonObject.containsKey("isInk")) {
			model.setIsInk(jsonObject.getString("isInk"));
		}
		if (jsonObject.containsKey("oldId")) {
			model.setOldId(jsonObject.getString("oldId"));
		}
		if (jsonObject.containsKey("taskId")) {
			model.setTaskId(jsonObject.getString("taskId"));
		}
		if (jsonObject.containsKey("isCheck")) {
			model.setIsCheck(jsonObject.getInteger("isCheck"));
		}

		return model;
	}

	public static JSONObject toJsonObject(DotUse model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("fileID", model.getFileID());
		jsonObject.put("_fileID_", model.getFileID());
		jsonObject.put("indexId", model.getIndexId());
		jsonObject.put("projId", model.getProjId());
		jsonObject.put("pid", model.getPid());
		if (model.getDotId() != null) {
			jsonObject.put("dotId", model.getDotId());
		}
		if (model.getNum() != null) {
			jsonObject.put("num", model.getNum());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getCman() != null) {
			jsonObject.put("cman", model.getCman());
		}
		if (model.getCtime() != null) {
			jsonObject.put("ctime", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_date", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_datetime",
					DateUtils.getDateTime(model.getCtime()));
		}
		if (model.getFileName() != null) {
			jsonObject.put("fileName", model.getFileName());
		}
		if (model.getFileContent() != null) {
			jsonObject.put("fileContent", model.getFileContent());
		}
		jsonObject.put("fileSize", model.getFileSize());
		if (model.getVision() != null) {
			jsonObject.put("vision", model.getVision());
		}
		if (model.getSaveTime() != null) {
			jsonObject.put("saveTime", model.getSaveTime());
		}
		if (model.getRemark() != null) {
			jsonObject.put("remark", model.getRemark());
		}
		jsonObject.put("dwid", model.getDwid());
		jsonObject.put("fbid", model.getFbid());
		jsonObject.put("fxid", model.getFxid());
		if (model.getJid() != null) {
			jsonObject.put("jid", model.getJid());
		}
		if (model.getFlid() != null) {
			jsonObject.put("flid", model.getFlid());
		}
		if (model.getTopNode() != null) {
			jsonObject.put("topNode", model.getTopNode());
		}
		if (model.getTopId() != null) {
			jsonObject.put("topId", model.getTopId());
		}
		jsonObject.put("type", model.getType());
		if (model.getFname() != null) {
			jsonObject.put("fname", model.getFname());
		}
		if (model.getIsInk() != null) {
			jsonObject.put("isInk", model.getIsInk());
		}
		if (model.getOldId() != null) {
			jsonObject.put("oldId", model.getOldId());
		}
		if (model.getTaskId() != null) {
			jsonObject.put("taskId", model.getTaskId());
		}
		jsonObject.put("isCheck", model.getIsCheck());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(DotUse model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("fileID", model.getFileID());
		jsonObject.put("_fileID_", model.getFileID());
		jsonObject.put("indexId", model.getIndexId());
		jsonObject.put("projId", model.getProjId());
		jsonObject.put("pid", model.getPid());
		if (model.getDotId() != null) {
			jsonObject.put("dotId", model.getDotId());
		}
		if (model.getNum() != null) {
			jsonObject.put("num", model.getNum());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getCman() != null) {
			jsonObject.put("cman", model.getCman());
		}
		if (model.getCtime() != null) {
			jsonObject.put("ctime", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_date", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_datetime",
					DateUtils.getDateTime(model.getCtime()));
		}
		if (model.getFileName() != null) {
			jsonObject.put("fileName", model.getFileName());
		}
		if (model.getFileContent() != null) {
			jsonObject.put("fileContent", model.getFileContent());
		}
		jsonObject.put("fileSize", model.getFileSize());
		if (model.getVision() != null) {
			jsonObject.put("vision", model.getVision());
		}
		if (model.getSaveTime() != null) {
			jsonObject.put("saveTime", model.getSaveTime());
		}
		if (model.getRemark() != null) {
			jsonObject.put("remark", model.getRemark());
		}
		jsonObject.put("dwid", model.getDwid());
		jsonObject.put("fbid", model.getFbid());
		jsonObject.put("fxid", model.getFxid());
		if (model.getJid() != null) {
			jsonObject.put("jid", model.getJid());
		}
		if (model.getFlid() != null) {
			jsonObject.put("flid", model.getFlid());
		}
		if (model.getTopNode() != null) {
			jsonObject.put("topNode", model.getTopNode());
		}
		if (model.getTopId() != null) {
			jsonObject.put("topId", model.getTopId());
		}
		jsonObject.put("type", model.getType());
		if (model.getFname() != null) {
			jsonObject.put("fname", model.getFname());
		}
		if (model.getIsInk() != null) {
			jsonObject.put("isInk", model.getIsInk());
		}
		if (model.getOldId() != null) {
			jsonObject.put("oldId", model.getOldId());
		}
		if (model.getTaskId() != null) {
			jsonObject.put("taskId", model.getTaskId());
		}
		jsonObject.put("isCheck", model.getIsCheck());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<DotUse> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DotUse model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DotUse> arrayToList(JSONArray array) {
		java.util.List<DotUse> list = new java.util.ArrayList<DotUse>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DotUse model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private DotUseJsonFactory() {

	}

}
