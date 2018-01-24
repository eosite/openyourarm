package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.isdp.domain.FileAtt;

/**
 * 
 * JSON工厂类
 *
 */
public class FileAttJsonFactory {

	public static FileAtt jsonToObject(JSONObject jsonObject) {
		FileAtt model = new FileAtt();
		if (jsonObject.containsKey("fileID")) {
			model.setFileID(jsonObject.getString("fileID"));
		}
		if (jsonObject.containsKey("topId")) {
			model.setTopId(jsonObject.getString("topId"));
		}
		if (jsonObject.containsKey("num")) {
			model.setNum(jsonObject.getString("num"));
		}
		if (jsonObject.containsKey("actor")) {
			model.setActor(jsonObject.getString("actor"));
		}
		if (jsonObject.containsKey("fname")) {
			model.setFname(jsonObject.getString("fname"));
		}
		if (jsonObject.containsKey("fileName")) {
			model.setFileName(jsonObject.getString("fileName"));
		}
		if (jsonObject.containsKey("fileContent")) {
			model.setFileContent(jsonObject.getString("fileContent"));
		}
		if (jsonObject.containsKey("vision")) {
			model.setVision(jsonObject.getString("vision"));
		}
		if (jsonObject.containsKey("ctime")) {
			model.setCtime(jsonObject.getDate("ctime"));
		}
		if (jsonObject.containsKey("fileSize")) {
			model.setFileSize(jsonObject.getInteger("fileSize"));
		}
		if (jsonObject.containsKey("visID")) {
			model.setVisID(jsonObject.getString("visID"));
		}
		if (jsonObject.containsKey("attID")) {
			model.setAttID(jsonObject.getString("attID"));
		}
		if (jsonObject.containsKey("isTextContent")) {
			model.setIsTextContent(jsonObject.getInteger("isTextContent"));
		}
		if (jsonObject.containsKey("textContent")) {
			model.setTextContent(jsonObject.getString("textContent"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FileAtt model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("fileID", model.getFileID());
		jsonObject.put("_fileID_", model.getFileID());
		if (model.getTopId() != null) {
			jsonObject.put("topId", model.getTopId());
		}
		if (model.getNum() != null) {
			jsonObject.put("num", model.getNum());
		}
		if (model.getActor() != null) {
			jsonObject.put("actor", model.getActor());
		}
		if (model.getFname() != null) {
			jsonObject.put("fname", model.getFname());
		}
		if (model.getFileName() != null) {
			jsonObject.put("fileName", model.getFileName());
		}
		if (model.getFileContent() != null) {
			jsonObject.put("fileContent", model.getFileContent());
		}
		if (model.getVision() != null) {
			jsonObject.put("vision", model.getVision());
		}
		if (model.getCtime() != null) {
			jsonObject.put("ctime", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_date", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_datetime",
					DateUtils.getDateTime(model.getCtime()));
		}
		jsonObject.put("fileSize", model.getFileSize());
		if (model.getVisID() != null) {
			jsonObject.put("visID", model.getVisID());
		}
		if (model.getAttID() != null) {
			jsonObject.put("attID", model.getAttID());
		}
		jsonObject.put("isTextContent", model.getIsTextContent());
		if (model.getTextContent() != null) {
			jsonObject.put("textContent", model.getTextContent());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FileAtt model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("fileID", model.getFileID());
		jsonObject.put("_fileID_", model.getFileID());
		if (model.getTopId() != null) {
			jsonObject.put("topId", model.getTopId());
		}
		if (model.getNum() != null) {
			jsonObject.put("num", model.getNum());
		}
		if (model.getActor() != null) {
			jsonObject.put("actor", model.getActor());
		}
		if (model.getFname() != null) {
			jsonObject.put("fname", model.getFname());
		}
		if (model.getFileName() != null) {
			jsonObject.put("fileName", model.getFileName());
		}
		if (model.getFileContent() != null) {
			jsonObject.put("fileContent", model.getFileContent());
		}
		if (model.getVision() != null) {
			jsonObject.put("vision", model.getVision());
		}
		if (model.getCtime() != null) {
			jsonObject.put("ctime", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_date", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_datetime",
					DateUtils.getDateTime(model.getCtime()));
		}
		jsonObject.put("fileSize", model.getFileSize());
		if (model.getVisID() != null) {
			jsonObject.put("visID", model.getVisID());
		}
		if (model.getAttID() != null) {
			jsonObject.put("attID", model.getAttID());
		}
		jsonObject.put("isTextContent", model.getIsTextContent());
		if (model.getTextContent() != null) {
			jsonObject.put("textContent", model.getTextContent());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<FileAtt> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FileAtt model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FileAtt> arrayToList(JSONArray array) {
		java.util.List<FileAtt> list = new java.util.ArrayList<FileAtt>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FileAtt model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private FileAttJsonFactory() {

	}

}
