/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.form.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.domain.FormAttachment;

public class FormAttachmentJsonFactory {

	public static java.util.List<FormAttachment> arrayToList(JSONArray array) {
		java.util.List<FormAttachment> list = new java.util.ArrayList<FormAttachment>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormAttachment model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static FormAttachment jsonToObject(JSONObject jsonObject) {
		FormAttachment model = new FormAttachment();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("parent")) {
			model.setParent(jsonObject.getString("parent"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("fileName")) {
			model.setFileName(jsonObject.getString("fileName"));
		}
		if (jsonObject.containsKey("fileSize")) {
			model.setFileSize(jsonObject.getIntValue("fileSize"));
		}
		// if (jsonObject.containsKey("fileContent")) {
		// model.setFileContent(jsonObject.getBytes("fileContent"));
		// }
		if (jsonObject.containsKey("saveServicePath")) {
			model.setSaveServicePath(jsonObject.getString("saveServicePath"));
		}
		if (jsonObject.containsKey("version")) {
			model.setVersion(jsonObject.getString("version"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getIntValue("status"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}

		if (jsonObject.containsKey("updateDate")) {
			model.setUpdateDate(jsonObject.getDate("updateDate"));
		}

		return model;
	}

	public static FormAttachment jsonToObjectFull(JSONObject jsonObject) {
		FormAttachment model = new FormAttachment();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("parent")) {
			model.setParent(jsonObject.getString("parent"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("fileName")) {
			model.setFileName(jsonObject.getString("fileName"));
		}
		if (jsonObject.containsKey("fileSize")) {
			model.setFileSize(jsonObject.getIntValue("fileSize"));
		}
		if (jsonObject.containsKey("fileContent")) {
			model.setFileContent(jsonObject.getBytes("fileContent"));
		}
		if (jsonObject.containsKey("saveServicePath")) {
			model.setSaveServicePath(jsonObject.getString("saveServicePath"));
		}
		if (jsonObject.containsKey("version")) {
			model.setVersion(jsonObject.getString("version"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getIntValue("status"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}

		if (jsonObject.containsKey("updateDate")) {
			model.setUpdateDate(jsonObject.getDate("updateDate"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<FormAttachment> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormAttachment model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(FormAttachment model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("parent", model.getParent());
		if (model.getFileName() != null) {
			jsonObject.put("fileName", model.getFileName());
		}
		jsonObject.put("fileSize", model.getFileSize());
		// if (model.getFileContent() != null) {
		// jsonObject.put("fileContent", model.getFileContent());
		// }
		if (model.getSaveServicePath() != null) {
			jsonObject.put("saveServicePath", model.getSaveServicePath());
		}
		jsonObject.put("type", model.getType());
		if (model.getVersion() != null) {
			jsonObject.put("version", model.getVersion());
		}
		jsonObject.put("status", model.getStatus());

		if (model.getCreateDate() != null) {
			jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate", DateUtils.getDate(model.getUpdateDate()));
		}
		if (model.getError() != null) {
			jsonObject.put("error", model.getError());
		}

		return jsonObject;
	}

	public static JSONObject toJsonObjectFull(FormAttachment model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("parent", model.getParent());
		if (model.getFileName() != null) {
			jsonObject.put("fileName", model.getFileName());
		}
		jsonObject.put("fileSize", model.getFileSize());
		if (model.getFileContent() != null) {
			jsonObject.put("fileContent", model.getFileContent());
		}
		if (model.getSaveServicePath() != null) {
			jsonObject.put("saveServicePath", model.getSaveServicePath());
		}
		if (model.getVersion() != null) {
			jsonObject.put("version", model.getVersion());
		}
		jsonObject.put("status", model.getStatus());

		if (model.getCreateDate() != null) {
			jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate", DateUtils.getDate(model.getUpdateDate()));
		}
		if (model.getError() != null) {
			jsonObject.put("error", model.getError());
		}

		return jsonObject;
	}

	public static ObjectNode toObjectNode(FormAttachment model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("parent", model.getParent());
		if (model.getFileName() != null) {
			jsonObject.put("fileName", model.getFileName());
		}
		jsonObject.put("fileSize", model.getFileSize());
		// if (model.getFileContent() != null) {
		// jsonObject.put("fileContent", model.getFileContent());
		// }
		if (model.getSaveServicePath() != null) {
			jsonObject.put("saveServicePath", model.getSaveServicePath());
		}
		if (model.getVersion() != null) {
			jsonObject.put("version", model.getVersion());
		}
		jsonObject.put("status", model.getStatus());

		if (model.getCreateDate() != null) {
			jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate", DateUtils.getDate(model.getUpdateDate()));
		}

		return jsonObject;
	}

	private FormAttachmentJsonFactory() {

	}

}
