package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.isdp.domain.Rinterface;

/**
 * 
 * JSON工厂类
 *
 */
public class RinterfaceJsonFactory {

	public static Rinterface jsonToObject(JSONObject jsonObject) {
		Rinterface model = new Rinterface();
		if (jsonObject.containsKey("id")) {
			// model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("indexId")) {
			model.setIndexId(jsonObject.getString("indexId"));
		}
		if (jsonObject.containsKey("frmtype")) {
			model.setFrmtype(jsonObject.getString("frmtype"));
		}
		if (jsonObject.containsKey("listId")) {
			model.setListId(jsonObject.getString("listId"));
		}
		if (jsonObject.containsKey("issystem")) {
			model.setIssystem(jsonObject.getString("issystem"));
		}
		if (jsonObject.containsKey("fname")) {
			model.setFname(jsonObject.getString("fname"));
		}
		if (jsonObject.containsKey("dname")) {
			model.setDname(jsonObject.getString("dname"));
		}
		if (jsonObject.containsKey("dtype")) {
			model.setDtype(jsonObject.getString("dtype"));
		}
		if (jsonObject.containsKey("showtype")) {
			model.setShowtype(jsonObject.getString("showtype"));
		}
		if (jsonObject.containsKey("strlen")) {
			model.setStrlen(jsonObject.getInteger("strlen"));
		}
		if (jsonObject.containsKey("form")) {
			model.setForm(jsonObject.getString("form"));
		}
		if (jsonObject.containsKey("intype")) {
			model.setIntype(jsonObject.getString("intype"));
		}
		if (jsonObject.containsKey("hintID")) {
			model.setHintID(jsonObject.getString("hintID"));
		}
		if (jsonObject.containsKey("listno")) {
			model.setListno(jsonObject.getInteger("listno"));
		}
		if (jsonObject.containsKey("ztype")) {
			model.setZtype(jsonObject.getString("ztype"));
		}
		if (jsonObject.containsKey("ismustfill")) {
			model.setIsmustfill(jsonObject.getString("ismustfill"));
		}
		if (jsonObject.containsKey("isListShow")) {
			model.setIsListShow(jsonObject.getString("isListShow"));
		}
		if (jsonObject.containsKey("listweigth")) {
			model.setListweigth(jsonObject.getInteger("listweigth"));
		}
		if (jsonObject.containsKey("isallwidth")) {
			model.setIsallwidth(jsonObject.getString("isallwidth"));
		}
		if (jsonObject.containsKey("istname")) {
			model.setIstname(jsonObject.getString("istname"));
		}
		if (jsonObject.containsKey("importType")) {
			model.setImportType(jsonObject.getInteger("importType"));
		}
		if (jsonObject.containsKey("datapoint")) {
			model.setDatapoint(jsonObject.getInteger("datapoint"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("updateDate")) {
			model.setUpdateDate(jsonObject.getDate("updateDate"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}
		if (jsonObject.containsKey("isPrimaryKey")) {
			model.setIsPrimaryKey(jsonObject.getString("isPrimaryKey"));
		}
		if (jsonObject.containsKey("isGroupBy")) {
			model.setIsGroupBy(jsonObject.getString("isGroupBy"));
		}

		return model;
	}

	public static JSONObject toJsonObject(Rinterface model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getListId());
		jsonObject.put("_id_", model.getListId());
		jsonObject.put("_oid_", model.getListId());
		if (model.getIndexId() != null) {
			jsonObject.put("indexId", model.getIndexId());
		}
		if (model.getFrmtype() != null) {
			jsonObject.put("frmtype", model.getFrmtype());
		}
		if (model.getListId() != null) {
			jsonObject.put("listId", model.getListId());
		}
		if (model.getIssystem() != null) {
			jsonObject.put("issystem", model.getIssystem());
		}
		if (model.getFname() != null) {
			jsonObject.put("fname", model.getFname());
		}
		if (model.getDname() != null) {
			jsonObject.put("dname", model.getDname());
		}
		if (model.getDtype() != null) {
			jsonObject.put("dtype", model.getDtype());
		}
		if (model.getShowtype() != null) {
			jsonObject.put("showtype", model.getShowtype());
		}
		jsonObject.put("strlen", model.getStrlen());
		if (model.getForm() != null) {
			jsonObject.put("form", model.getForm());
		}
		if (model.getIntype() != null) {
			jsonObject.put("intype", model.getIntype());
		}
		if (model.getHintID() != null) {
			jsonObject.put("hintID", model.getHintID());
		}
		jsonObject.put("listno", model.getListno());
		if (model.getZtype() != null) {
			jsonObject.put("ztype", model.getZtype());
		}
		if (model.getIsmustfill() != null) {
			jsonObject.put("ismustfill", model.getIsmustfill());
		}
		if (model.getIsListShow() != null) {
			jsonObject.put("isListShow", model.getIsListShow());
		}
		jsonObject.put("listweigth", model.getListweigth());
		if (model.getIsallwidth() != null) {
			jsonObject.put("isallwidth", model.getIsallwidth());
		}
		if (model.getIstname() != null) {
			jsonObject.put("istname", model.getIstname());
		}
		jsonObject.put("importType", model.getImportType());
		jsonObject.put("datapoint", model.getDatapoint());
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate", DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date", DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime", DateUtils.getDateTime(model.getUpdateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getIsPrimaryKey() != null) {
			jsonObject.put("isPrimaryKey", model.getIsPrimaryKey());
		}
		if (model.getIsGroupBy() != null) {
			jsonObject.put("isGroupBy", model.getIsGroupBy());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(Rinterface model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getListId());
		jsonObject.put("_id_", model.getListId());
		jsonObject.put("_oid_", model.getListId());
		if (model.getIndexId() != null) {
			jsonObject.put("indexId", model.getIndexId());
		}
		if (model.getFrmtype() != null) {
			jsonObject.put("frmtype", model.getFrmtype());
		}
		if (model.getListId() != null) {
			jsonObject.put("listId", model.getListId());
		}
		if (model.getIssystem() != null) {
			jsonObject.put("issystem", model.getIssystem());
		}
		if (model.getFname() != null) {
			jsonObject.put("fname", model.getFname());
		}
		if (model.getDname() != null) {
			jsonObject.put("dname", model.getDname());
		}
		if (model.getDtype() != null) {
			jsonObject.put("dtype", model.getDtype());
		}
		if (model.getShowtype() != null) {
			jsonObject.put("showtype", model.getShowtype());
		}
		jsonObject.put("strlen", model.getStrlen());
		if (model.getForm() != null) {
			jsonObject.put("form", model.getForm());
		}
		if (model.getIntype() != null) {
			jsonObject.put("intype", model.getIntype());
		}
		if (model.getHintID() != null) {
			jsonObject.put("hintID", model.getHintID());
		}
		jsonObject.put("listno", model.getListno());
		if (model.getZtype() != null) {
			jsonObject.put("ztype", model.getZtype());
		}
		if (model.getIsmustfill() != null) {
			jsonObject.put("ismustfill", model.getIsmustfill());
		}
		if (model.getIsListShow() != null) {
			jsonObject.put("isListShow", model.getIsListShow());
		}
		jsonObject.put("listweigth", model.getListweigth());
		if (model.getIsallwidth() != null) {
			jsonObject.put("isallwidth", model.getIsallwidth());
		}
		if (model.getIstname() != null) {
			jsonObject.put("istname", model.getIstname());
		}
		jsonObject.put("importType", model.getImportType());
		jsonObject.put("datapoint", model.getDatapoint());
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate", DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date", DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime", DateUtils.getDateTime(model.getUpdateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getIsPrimaryKey() != null) {
			jsonObject.put("isPrimaryKey", model.getIsPrimaryKey());
		}
		if (model.getIsGroupBy() != null) {
			jsonObject.put("isGroupBy", model.getIsGroupBy());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<Rinterface> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Rinterface model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<Rinterface> arrayToList(JSONArray array) {
		java.util.List<Rinterface> list = new java.util.ArrayList<Rinterface>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Rinterface model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private RinterfaceJsonFactory() {

	}

}
