package com.glaf.base.modules.sys.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;

import com.glaf.base.modules.sys.model.FiledotUse;

/**
 * 
 * JSON工厂类
 *
 */
public class FiledotUseJsonFactory {

	public static FiledotUse jsonToObject(JSONObject jsonObject) {
		FiledotUse model = new FiledotUse();
		if (jsonObject.containsKey("fileID")) {
			model.setFileID(jsonObject.getString("fileID"));
		}
		if (jsonObject.containsKey("indexId")) {
			model.setIndexId(jsonObject.getInteger("indexId"));
		}
		if (jsonObject.containsKey("projid")) {
			model.setProjid(jsonObject.getInteger("projid"));
		}
		if (jsonObject.containsKey("pid")) {
			model.setPid(jsonObject.getInteger("pid"));
		}
		if (jsonObject.containsKey("dotid")) {
			model.setDotid(jsonObject.getString("dotid"));
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
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}
		if (jsonObject.containsKey("fileName")) {
			model.setFileName(jsonObject.getString("fileName"));
		}

		if (jsonObject.containsKey("filesize")) {
			model.setFilesize(jsonObject.getInteger("filesize"));
		}
		if (jsonObject.containsKey("vision")) {
			model.setVision(jsonObject.getString("vision"));
		}
		if (jsonObject.containsKey("savetime")) {
			model.setSavetime(jsonObject.getString("savetime"));
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
		if (jsonObject.containsKey("topnode")) {
			model.setTopnode(jsonObject.getString("topnode"));
		}
		if (jsonObject.containsKey("topid")) {
			model.setTopid(jsonObject.getString("topid"));
		}

		if (jsonObject.containsKey("fname")) {
			model.setFname(jsonObject.getString("fname"));
		}
		if (jsonObject.containsKey("ischeck")) {
			model.setIscheck(jsonObject.getInteger("ischeck"));
		}
		if (jsonObject.containsKey("isink")) {
			model.setIsink(jsonObject.getString("isink"));
		}
		if (jsonObject.containsKey("oldid")) {
			model.setOldid(jsonObject.getString("oldid"));
		}
		if (jsonObject.containsKey("taskid")) {
			model.setTaskid(jsonObject.getString("taskid"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getInteger("type"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FiledotUse model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("fileID", model.getFileID());
		jsonObject.put("_fileID_", model.getFileID());
		jsonObject.put("indexId", model.getIndexId());
		jsonObject.put("projid", model.getProjid());
		jsonObject.put("pid", model.getPid());
		if (model.getDotid() != null) {
			jsonObject.put("dotid", model.getDotid());
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
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getFileName() != null) {
			jsonObject.put("fileName", model.getFileName());
		}
		if (model.getFileContent() != null) {
			jsonObject.put("fileContent", model.getFileContent());
		}
		jsonObject.put("filesize", model.getFilesize());
		if (model.getVision() != null) {
			jsonObject.put("vision", model.getVision());
		}
		if (model.getSavetime() != null) {
			jsonObject.put("savetime", model.getSavetime());
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
		if (model.getTopnode() != null) {
			jsonObject.put("topnode", model.getTopnode());
		}
		if (model.getTopid() != null) {
			jsonObject.put("topid", model.getTopid());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getFname() != null) {
			jsonObject.put("fname", model.getFname());
		}
		jsonObject.put("ischeck", model.getIscheck());
		if (model.getIsink() != null) {
			jsonObject.put("isink", model.getIsink());
		}
		if (model.getOldid() != null) {
			jsonObject.put("oldid", model.getOldid());
		}
		if (model.getTaskid() != null) {
			jsonObject.put("taskid", model.getTaskid());
		}
		jsonObject.put("type", model.getType());

		return jsonObject;
	}

	public static ObjectNode toObjectNode(FiledotUse model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("fileID", model.getFileID());
		jsonObject.put("_fileID_", model.getFileID());
		jsonObject.put("indexId", model.getIndexId());
		jsonObject.put("projid", model.getProjid());
		jsonObject.put("pid", model.getPid());
		if (model.getDotid() != null) {
			jsonObject.put("dotid", model.getDotid());
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
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getFileName() != null) {
			jsonObject.put("fileName", model.getFileName());
		}
		if (model.getFileContent() != null) {
			jsonObject.put("fileContent", model.getFileContent());
		}
		jsonObject.put("filesize", model.getFilesize());
		if (model.getVision() != null) {
			jsonObject.put("vision", model.getVision());
		}
		if (model.getSavetime() != null) {
			jsonObject.put("savetime", model.getSavetime());
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
		if (model.getTopnode() != null) {
			jsonObject.put("topnode", model.getTopnode());
		}
		if (model.getTopid() != null) {
			jsonObject.put("topid", model.getTopid());
		}

		if (model.getFname() != null) {
			jsonObject.put("fname", model.getFname());
		}
		jsonObject.put("ischeck", model.getIscheck());
		if (model.getIsink() != null) {
			jsonObject.put("isink", model.getIsink());
		}
		if (model.getOldid() != null) {
			jsonObject.put("oldid", model.getOldid());
		}
		if (model.getTaskid() != null) {
			jsonObject.put("taskid", model.getTaskid());
		}
		jsonObject.put("type", model.getType());

		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<FiledotUse> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FiledotUse model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FiledotUse> arrayToList(JSONArray array) {
		java.util.List<FiledotUse> list = new java.util.ArrayList<FiledotUse>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FiledotUse model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private FiledotUseJsonFactory() {

	}

}
