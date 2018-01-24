package com.glaf.form.core.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.form.core.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class FormPageJsonFactory {

	public static FormPage jsonToObject(JSONObject jsonObject) {
		FormPage model = new FormPage();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("parentId")) {
			model.setParentId(jsonObject.getString("parentId"));
		}
		if (jsonObject.containsKey("deploymentId")) {
			model.setDeploymentId(jsonObject.getString("deploymentId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("designerHtml")) {
			model.setDesignerHtml(jsonObject.getString("designerHtml"));
		}
		if (jsonObject.containsKey("designerJson")) {
			model.setDesignerJson(jsonObject.getString("designerJson"));
		}
		if (jsonObject.containsKey("formHtml")) {
			model.setFormHtml(jsonObject.getString("formHtml"));
		}
		if (jsonObject.containsKey("formConfig")) {
			model.setFormConfig(jsonObject.getString("formConfig"));
		}
		if (jsonObject.containsKey("outputHtml")) {
			model.setOutputHtml(jsonObject.getString("outputHtml"));
		}
		if (jsonObject.containsKey("formType")) {
			model.setFormType(jsonObject.getString("formType"));
		}
		if (jsonObject.containsKey("accessType")) {
			model.setAccessType(jsonObject.getString("accessType"));
		}
		if (jsonObject.containsKey("perms")) {
			model.setPerms(jsonObject.getString("perms"));
		}
		if (jsonObject.containsKey("addressPerms")) {
			model.setAddressPerms(jsonObject.getString("addressPerms"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("cacheFlag")) {
			model.setCacheFlag(jsonObject.getString("cacheFlag"));
		}
		if (jsonObject.containsKey("publicFlag")) {
			model.setPublicFlag(jsonObject.getString("publicFlag"));
		}
		if (jsonObject.containsKey("userStyleFlag")) {
			model.setUserStyleFlag(jsonObject.getString("userStyleFlag"));
		}
		if (jsonObject.containsKey("businessTable")) {
			model.setBusinessTable(jsonObject.getString("businessTable"));
		}
		if (jsonObject.containsKey("primaryKeyColumn")) {
			model.setPrimaryKeyColumn(jsonObject.getString("primaryKeyColumn"));
		}
		if (jsonObject.containsKey("processName")) {
			model.setProcessName(jsonObject.getString("processName"));
		}
		if (jsonObject.containsKey("taskFlag")) {
			model.setTaskFlag(jsonObject.getString("taskFlag"));
		}
		if (jsonObject.containsKey("sortNo")) {
			model.setSortNo(jsonObject.getInteger("sortNo"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}
		if (jsonObject.containsKey("version")) {
			model.setVersion(jsonObject.getInteger("version"));
		}
		if (jsonObject.containsKey("pageCategory")) {
			model.setPageCategory(jsonObject.getInteger("pageCategory"));
		}
		if (jsonObject.containsKey("uiType")) {
			model.setUiType(jsonObject.getString("uiType"));
		}
		
		if (jsonObject.containsKey("themeTmpId")) {
			model.setThemeTmpId(jsonObject.getString("themeTmpId"));
		}
		if (jsonObject.containsKey("cacheTime")) {
			model.setCacheTime(jsonObject.getLong("cacheTime"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FormPage model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getParentId() != null) {
			jsonObject.put("parentId", model.getParentId());
		}
		if (model.getDeploymentId() != null) {
			jsonObject.put("deploymentId", model.getDeploymentId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getDesignerHtml() != null) {
			jsonObject.put("designerHtml", model.getDesignerHtml());
		}
		if (model.getDesignerJson() != null) {
			jsonObject.put("designerJson", model.getDesignerJson());
		}
		if (model.getFormHtml() != null) {
			jsonObject.put("formHtml", model.getFormHtml());
		}
		if (model.getFormConfig() != null) {
			jsonObject.put("formConfig", model.getFormConfig());
		}
		if (model.getOutputHtml() != null) {
			jsonObject.put("outputHtml", model.getOutputHtml());
		}
		if (model.getFormType() != null) {
			jsonObject.put("formType", model.getFormType());
		}
		if (model.getCacheFlag() != null) {
			jsonObject.put("cacheFlag", model.getCacheFlag());
		}

		jsonObject.put("cacheTime", model.getCacheTime());

		if (model.getPublicFlag() != null) {
			jsonObject.put("publicFlag", model.getPublicFlag());
		}
		if (model.getUserStyleFlag() != null) {
			jsonObject.put("userStyleFlag", model.getUserStyleFlag());
		}
		if (model.getBusinessTable() != null) {
			jsonObject.put("businessTable", model.getBusinessTable());
		}
		if (model.getPrimaryKeyColumn() != null) {
			jsonObject.put("primaryKeyColumn", model.getPrimaryKeyColumn());
		}
		if (model.getProcessName() != null) {
			jsonObject.put("processName", model.getProcessName());
		}
		if (model.getTaskFlag() != null) {
			jsonObject.put("taskFlag", model.getTaskFlag());
		}
		if (model.getAccessType() != null) {
			jsonObject.put("accessType", model.getAccessType());
		}
		if (model.getAddressPerms() != null) {
			jsonObject.put("addressPerms", model.getAddressPerms());
		}
		if (model.getPerms() != null) {
			jsonObject.put("perms", model.getPerms());
		}

		jsonObject.put("cacheTime", model.getCacheTime());
		jsonObject.put("sortNo", model.getSortNo());
		jsonObject.put("locked", model.getLocked());
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		jsonObject.put("version", model.getVersion());

		jsonObject.put("pageCategory", model.getPageCategory());
		if (model.getUiType() != null) {
			jsonObject.put("uiType", model.getUiType());
		}

		if (model.getCreateDate() != null) {
			jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate", DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date", DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime", DateUtils.getDateTime(model.getUpdateDate()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		
		if (model.getThemeTmpId() != null) {
			jsonObject.put("themeTmpId", model.getThemeTmpId());
		}
		
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FormPage model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getParentId() != null) {
			jsonObject.put("parentId", model.getParentId());
		}
		if (model.getDeploymentId() != null) {
			jsonObject.put("deploymentId", model.getDeploymentId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getDesignerHtml() != null) {
			jsonObject.put("designerHtml", model.getDesignerHtml());
		}
		if (model.getDesignerJson() != null) {
			jsonObject.put("designerJson", model.getDesignerJson());
		}
		if (model.getFormHtml() != null) {
			jsonObject.put("formHtml", model.getFormHtml());
		}
		if (model.getFormConfig() != null) {
			jsonObject.put("formConfig", model.getFormConfig());
		}
		if (model.getOutputHtml() != null) {
			jsonObject.put("outputHtml", model.getOutputHtml());
		}
		if (model.getFormType() != null) {
			jsonObject.put("formType", model.getFormType());
		}
		if (model.getAccessType() != null) {
			jsonObject.put("accessType", model.getAccessType());
		}
		if (model.getPerms() != null) {
			jsonObject.put("perms", model.getPerms());
		}
		if (model.getAddressPerms() != null) {
			jsonObject.put("addressPerms", model.getAddressPerms());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getCacheFlag() != null) {
			jsonObject.put("cacheFlag", model.getCacheFlag());
		}
		if (model.getPublicFlag() != null) {
			jsonObject.put("publicFlag", model.getPublicFlag());
		}
		if (model.getUserStyleFlag() != null) {
			jsonObject.put("userStyleFlag", model.getUserStyleFlag());
		}
		if (model.getBusinessTable() != null) {
			jsonObject.put("businessTable", model.getBusinessTable());
		}
		if (model.getPrimaryKeyColumn() != null) {
			jsonObject.put("primaryKeyColumn", model.getPrimaryKeyColumn());
		}
		if (model.getProcessName() != null) {
			jsonObject.put("processName", model.getProcessName());
		}
		if (model.getTaskFlag() != null) {
			jsonObject.put("taskFlag", model.getTaskFlag());
		}
		jsonObject.put("pageCategory", model.getPageCategory());
		if (model.getUiType() != null) {
			jsonObject.put("uiType", model.getUiType());
		}

		jsonObject.put("cacheTime", model.getCacheTime());
		jsonObject.put("sortNo", model.getSortNo());
		jsonObject.put("locked", model.getLocked());
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		jsonObject.put("version", model.getVersion());
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate", DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date", DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime", DateUtils.getDateTime(model.getUpdateDate()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		
		if (model.getThemeTmpId() != null) {
			jsonObject.put("themeTmpId", model.getThemeTmpId());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<FormPage> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormPage model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FormPage> arrayToList(JSONArray array) {
		java.util.List<FormPage> list = new java.util.ArrayList<FormPage>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormPage model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private FormPageJsonFactory() {

	}

}
