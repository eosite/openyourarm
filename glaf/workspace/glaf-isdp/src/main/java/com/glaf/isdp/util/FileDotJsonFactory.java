package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.isdp.domain.FileDot;

/**
 * 
 * JSON工厂类
 *
 */
public class FileDotJsonFactory {

	public static FileDot jsonToObject(JSONObject jsonObject) {
		FileDot model = new FileDot();
		if (jsonObject.containsKey("fileID")) {
			model.setFileID(jsonObject.getString("fileID"));
		}
		if (jsonObject.containsKey("listId")) {
			model.setListId(jsonObject.getString("listId"));
		}
		if (jsonObject.containsKey("indexId")) {
			model.setIndexId(jsonObject.getInteger("indexId"));
		}
		if (jsonObject.containsKey("fbelong")) {
			model.setFbelong(jsonObject.getString("fbelong"));
		}
		if (jsonObject.containsKey("fnum")) {
			model.setFnum(jsonObject.getString("fnum"));
		}
		if (jsonObject.containsKey("pbelong")) {
			model.setPbelong(jsonObject.getString("pbelong"));
		}
		if (jsonObject.containsKey("num")) {
			model.setNum(jsonObject.getString("num"));
		}
		if (jsonObject.containsKey("fname")) {
			model.setFname(jsonObject.getString("fname"));
		}
		if (jsonObject.containsKey("dotName")) {
			model.setDotName(jsonObject.getString("dotName"));
		}
		if (jsonObject.containsKey("ctime")) {
			model.setCtime(jsonObject.getDate("ctime"));
		}
		if (jsonObject.containsKey("dtime")) {
			model.setDtime(jsonObject.getDate("dtime"));
		}
		if (jsonObject.containsKey("fileName")) {
			model.setFileName(jsonObject.getString("fileName"));
		}
		if (jsonObject.containsKey("fileContent")) {
			model.setFileContent(jsonObject.getBytes("fileContent"));
		}
		if (jsonObject.containsKey("fileSize")) {
			model.setFileSize(jsonObject.getInteger("fileSize"));
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
		if (jsonObject.containsKey("cman")) {
			model.setCman(jsonObject.getString("cman"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("listFlag")) {
			model.setListFlag(jsonObject.getString("listFlag"));
		}
		if (jsonObject.containsKey("toFile")) {
			model.setToFile(jsonObject.getInteger("toFile"));
		}
		if (jsonObject.containsKey("isInk")) {
			model.setIsInk(jsonObject.getString("isInk"));
		}
		if (jsonObject.containsKey("dotType")) {
			model.setDotType(jsonObject.getInteger("dotType"));
		}
		if (jsonObject.containsKey("ctimeDName")) {
			model.setCtimeDName(jsonObject.getString("ctimeDName"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getInteger("type"));
		}
		if (jsonObject.containsKey("listNo")) {
			model.setListNo(jsonObject.getInteger("listNo"));
		}
		if (jsonObject.containsKey("utreeIndex")) {
			model.setUtreeIndex(jsonObject.getInteger("utreeIndex"));
		}
		if (jsonObject.containsKey("isQuan")) {
			model.setIsQuan(jsonObject.getString("isQuan"));
		}
		if (jsonObject.containsKey("intSysForm")) {
			model.setIntSysForm(jsonObject.getInteger("intSysForm"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FileDot model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("fileID", model.getFileID());
		jsonObject.put("_fileID_", model.getFileID());
		if (model.getListId() != null) {
			jsonObject.put("listId", model.getListId());
		}
		jsonObject.put("indexId", model.getIndexId());
		if (model.getFbelong() != null) {
			jsonObject.put("fbelong", model.getFbelong());
		}
		if (model.getFnum() != null) {
			jsonObject.put("fnum", model.getFnum());
		}
		if (model.getPbelong() != null) {
			jsonObject.put("pbelong", model.getPbelong());
		}
		if (model.getNum() != null) {
			jsonObject.put("num", model.getNum());
		}
		if (model.getFname() != null) {
			jsonObject.put("fname", model.getFname());
		}
		if (model.getDotName() != null) {
			jsonObject.put("dotName", model.getDotName());
		}
		if (model.getCtime() != null) {
			jsonObject.put("ctime", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_date", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_datetime",
					DateUtils.getDateTime(model.getCtime()));
		}
		if (model.getDtime() != null) {
			jsonObject.put("dtime", DateUtils.getDate(model.getDtime()));
			jsonObject.put("dtime_date", DateUtils.getDate(model.getDtime()));
			jsonObject.put("dtime_datetime",
					DateUtils.getDateTime(model.getDtime()));
		}
		if (model.getFileName() != null) {
			jsonObject.put("fileName", model.getFileName());
		}
		if (model.getFileContent() != null) {
			jsonObject.put("fileContent", model.getFileContent());
		}
		jsonObject.put("fileSize", model.getFileSize());
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
		if (model.getCman() != null) {
			jsonObject.put("cman", model.getCman());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getListFlag() != null) {
			jsonObject.put("listFlag", model.getListFlag());
		}
		jsonObject.put("toFile", model.getToFile());
		if (model.getIsInk() != null) {
			jsonObject.put("isInk", model.getIsInk());
		}
		jsonObject.put("dotType", model.getDotType());
		if (model.getCtimeDName() != null) {
			jsonObject.put("ctimeDName", model.getCtimeDName());
		}
		jsonObject.put("type", model.getType());
		jsonObject.put("listNo", model.getListNo());
		jsonObject.put("utreeIndex", model.getUtreeIndex());
		if (model.getIsQuan() != null) {
			jsonObject.put("isQuan", model.getIsQuan());
		}
		jsonObject.put("intSysForm", model.getIntSysForm());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FileDot model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("fileID", model.getFileID());
		jsonObject.put("_fileID_", model.getFileID());
		if (model.getListId() != null) {
			jsonObject.put("listId", model.getListId());
		}
		jsonObject.put("indexId", model.getIndexId());
		if (model.getFbelong() != null) {
			jsonObject.put("fbelong", model.getFbelong());
		}
		if (model.getFnum() != null) {
			jsonObject.put("fnum", model.getFnum());
		}
		if (model.getPbelong() != null) {
			jsonObject.put("pbelong", model.getPbelong());
		}
		if (model.getNum() != null) {
			jsonObject.put("num", model.getNum());
		}
		if (model.getFname() != null) {
			jsonObject.put("fname", model.getFname());
		}
		if (model.getDotName() != null) {
			jsonObject.put("dotName", model.getDotName());
		}
		if (model.getCtime() != null) {
			jsonObject.put("ctime", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_date", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_datetime",
					DateUtils.getDateTime(model.getCtime()));
		}
		if (model.getDtime() != null) {
			jsonObject.put("dtime", DateUtils.getDate(model.getDtime()));
			jsonObject.put("dtime_date", DateUtils.getDate(model.getDtime()));
			jsonObject.put("dtime_datetime",
					DateUtils.getDateTime(model.getDtime()));
		}
		if (model.getFileName() != null) {
			jsonObject.put("fileName", model.getFileName());
		}
		if (model.getFileContent() != null) {
			jsonObject.put("fileContent", model.getFileContent());
		}
		jsonObject.put("fileSize", model.getFileSize());
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
		if (model.getCman() != null) {
			jsonObject.put("cman", model.getCman());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getListFlag() != null) {
			jsonObject.put("listFlag", model.getListFlag());
		}
		jsonObject.put("toFile", model.getToFile());
		if (model.getIsInk() != null) {
			jsonObject.put("isInk", model.getIsInk());
		}
		jsonObject.put("dotType", model.getDotType());
		if (model.getCtimeDName() != null) {
			jsonObject.put("ctimeDName", model.getCtimeDName());
		}
		jsonObject.put("type", model.getType());
		jsonObject.put("listNo", model.getListNo());
		jsonObject.put("utreeIndex", model.getUtreeIndex());
		if (model.getIsQuan() != null) {
			jsonObject.put("isQuan", model.getIsQuan());
		}
		jsonObject.put("intSysForm", model.getIntSysForm());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<FileDot> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FileDot model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FileDot> arrayToList(JSONArray array) {
		java.util.List<FileDot> list = new java.util.ArrayList<FileDot>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FileDot model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private FileDotJsonFactory() {

	}

}
