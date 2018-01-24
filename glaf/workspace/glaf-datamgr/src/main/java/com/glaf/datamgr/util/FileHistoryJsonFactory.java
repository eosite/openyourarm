package com.glaf.datamgr.util;

import java.util.Date;

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
public class FileHistoryJsonFactory {

	public static FileHistory jsonToObject(JSONObject jsonObject) {
		FileHistory model = new FileHistory();
		if (jsonObject.containsKey("fileId")) {
			model.setFileId(jsonObject.getString("fileId"));
		}
		if (jsonObject.containsKey("fileName")) {
			model.setFileName(jsonObject.getString("fileName"));
		}
		if (jsonObject.containsKey("fileSize")) {
			model.setFileSize(jsonObject.getInteger("fileSize"));
		}

		if (jsonObject.containsKey("lastModified")) {
			model.setLastModified(jsonObject.getLong("lastModified"));
		}
		if (jsonObject.containsKey("md5")) {
			model.setMd5(jsonObject.getString("md5"));
		}
		if (jsonObject.containsKey("path")) {
			model.setPath(jsonObject.getString("path"));
		}
		if (jsonObject.containsKey("version")) {
			model.setVersion(jsonObject.getInteger("version"));
		}

		if (jsonObject.containsKey("pkgStatus")) {
			model.setPkgStatus(jsonObject.getString("pkgStatus"));
		}

		if (jsonObject.containsKey("pkgUpdateTime")) {
			model.setPkgUpdateTime(jsonObject.getDate("pkgUpdateTime"));
		}

		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FileHistory model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("fileId", model.getFileId());
		jsonObject.put("_fileId_", model.getFileId());
		if (model.getFileName() != null) {
			jsonObject.put("fileName", model.getFileName());
		}
		jsonObject.put("fileSize", model.getFileSize());
		if (model.getFileContent() != null) {
			jsonObject.put("fileContent", model.getFileContent());
		}
		jsonObject.put("lastModified", model.getLastModified());
		jsonObject.put("modifyDate", new Date(model.getLastModified()));
		jsonObject.put("modifyDate_datetime", DateUtils.getDateTime(new Date(model.getLastModified())));

		if (model.getMd5() != null) {
			jsonObject.put("md5", model.getMd5());
		}
		if (model.getPath() != null) {
			jsonObject.put("path", model.getPath());
		}
		jsonObject.put("version", model.getVersion());

		if (model.getPkgStatus() != null) {
			jsonObject.put("pkgStatus", model.getPkgStatus());
		}

		if (model.getPkgUpdateTime() != null) {
			jsonObject.put("pkgUpdateTime", DateUtils.getDate(model.getPkgUpdateTime()));
			jsonObject.put("pkgUpdateTime_date", DateUtils.getDate(model.getPkgUpdateTime()));
			jsonObject.put("pkgUpdateTime_datetime", DateUtils.getDateTime(model.getPkgUpdateTime()));
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

	public static ObjectNode toObjectNode(FileHistory model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("fileId", model.getFileId());
		jsonObject.put("_fileId_", model.getFileId());
		if (model.getFileName() != null) {
			jsonObject.put("fileName", model.getFileName());
		}
		jsonObject.put("fileSize", model.getFileSize());
		if (model.getFileContent() != null) {
			jsonObject.put("fileContent", model.getFileContent());
		}
		jsonObject.put("lastModified", model.getLastModified());
		jsonObject.put("modifyDate_datetime", DateUtils.getDateTime(new Date(model.getLastModified())));

		if (model.getMd5() != null) {
			jsonObject.put("md5", model.getMd5());
		}
		if (model.getPath() != null) {
			jsonObject.put("path", model.getPath());
		}
		jsonObject.put("version", model.getVersion());

		if (model.getPkgStatus() != null) {
			jsonObject.put("pkgStatus", model.getPkgStatus());
		}

		if (model.getPkgUpdateTime() != null) {
			jsonObject.put("pkgUpdateTime", DateUtils.getDate(model.getPkgUpdateTime()));
			jsonObject.put("pkgUpdateTime_date", DateUtils.getDate(model.getPkgUpdateTime()));
			jsonObject.put("pkgUpdateTime_datetime", DateUtils.getDateTime(model.getPkgUpdateTime()));
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

	public static JSONArray listToArray(java.util.List<FileHistory> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FileHistory model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FileHistory> arrayToList(JSONArray array) {
		java.util.List<FileHistory> list = new java.util.ArrayList<FileHistory>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FileHistory model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private FileHistoryJsonFactory() {

	}

}
