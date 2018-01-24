package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.isdp.domain.CellRepInfo;

/**
 * 
 * JSON工厂类
 *
 */
public class CellRepInfoJsonFactory {

	public static CellRepInfo jsonToObject(JSONObject jsonObject) {
		CellRepInfo model = new CellRepInfo();
		if (jsonObject.containsKey("listId")) {
			model.setListId(jsonObject.getString("listId"));
		}
		if (jsonObject.containsKey("indexId")) {
			model.setIndexId(jsonObject.getString("indexId"));
		}
		if (jsonObject.containsKey("frmType")) {
			model.setFrmType(jsonObject.getString("frmType"));
		}
		if (jsonObject.containsKey("isSystem")) {
			model.setIsSystem(jsonObject.getString("isSystem"));
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
		if (jsonObject.containsKey("showType")) {
			model.setShowType(jsonObject.getString("showType"));
		}
		if (jsonObject.containsKey("strLen")) {
			model.setStrLen(jsonObject.getInteger("strLen"));
		}
		if (jsonObject.containsKey("form")) {
			model.setForm(jsonObject.getString("form"));
		}
		if (jsonObject.containsKey("inType")) {
			model.setInType(jsonObject.getString("inType"));
		}
		if (jsonObject.containsKey("hintID")) {
			model.setHintID(jsonObject.getString("hintID"));
		}
		if (jsonObject.containsKey("listNo")) {
			model.setListNo(jsonObject.getInteger("listNo"));
		}
		if (jsonObject.containsKey("ztype")) {
			model.setZtype(jsonObject.getString("ztype"));
		}
		if (jsonObject.containsKey("isMustFill")) {
			model.setIsMustFill(jsonObject.getString("isMustFill"));
		}
		if (jsonObject.containsKey("isListShow")) {
			model.setIsListShow(jsonObject.getString("isListShow"));
		}
		if (jsonObject.containsKey("listWeigth")) {
			model.setListWeigth(jsonObject.getInteger("listWeigth"));
		}
		if (jsonObject.containsKey("panid")) {
			model.setPanid(jsonObject.getString("panid"));
		}
		if (jsonObject.containsKey("isAllWidth")) {
			model.setIsAllWidth(jsonObject.getString("isAllWidth"));
		}
		if (jsonObject.containsKey("istName")) {
			model.setIstName(jsonObject.getString("istName"));
		}
		if (jsonObject.containsKey("fileDotFileId")) {
			model.setFileDotFileId(jsonObject.getString("fileDotFileId"));
		}
		if (jsonObject.containsKey("dataPoint")) {
			model.setDataPoint(jsonObject.getInteger("dataPoint"));
		}
		if (jsonObject.containsKey("defaultValue")) {
			model.setDefaultValue(jsonObject.getString("defaultValue"));
		}
		if (jsonObject.containsKey("isSubTable")) {
			model.setIsSubTable(jsonObject.getString("isSubTable"));
		}
		if (jsonObject.containsKey("isDataOnly")) {
			model.setIsDataOnly(jsonObject.getString("isDataOnly"));
		}
		if (jsonObject.containsKey("checkType")) {
			model.setCheckType(jsonObject.getInteger("checkType"));
		}
		if (jsonObject.containsKey("orderId")) {
			model.setOrderId(jsonObject.getString("orderId"));
		}
		if (jsonObject.containsKey("cellType")) {
			model.setCellType(jsonObject.getInteger("cellType"));
		}
		if (jsonObject.containsKey("ostTableName")) {
			model.setOstTableName(jsonObject.getString("ostTableName"));
		}
		if (jsonObject.containsKey("ostRow")) {
			model.setOstRow(jsonObject.getInteger("ostRow"));
		}
		if (jsonObject.containsKey("ostCol")) {
			model.setOstCol(jsonObject.getInteger("ostCol"));
		}
		if (jsonObject.containsKey("hintList")) {
			model.setHintList(jsonObject.getString("hintList"));
		}
		if (jsonObject.containsKey("ostCellId")) {
			model.setOstCellId(jsonObject.getString("ostCellId"));
		}
		if (jsonObject.containsKey("oldDName")) {
			model.setOldDName(jsonObject.getString("oldDName"));
		}
		if (jsonObject.containsKey("ostCellName")) {
			model.setOstCellName(jsonObject.getString("ostCellName"));
		}
		if (jsonObject.containsKey("ostFormula")) {
			model.setOstFormula(jsonObject.getString("ostFormula"));
		}
		if (jsonObject.containsKey("ostColor")) {
			model.setOstColor(jsonObject.getInteger("ostColor"));
		}
		if (jsonObject.containsKey("ostSumType")) {
			model.setOstSumType(jsonObject.getInteger("ostSumType"));
		}
		if (jsonObject.containsKey("dataTableName")) {
			model.setDataTableName(jsonObject.getString("dataTableName"));
		}
		if (jsonObject.containsKey("dataFieldName")) {
			model.setDataFieldName(jsonObject.getString("dataFieldName"));
		}
		if (jsonObject.containsKey("orderCon")) {
			model.setOrderCon(jsonObject.getInteger("orderCon"));
		}
		if (jsonObject.containsKey("conNum")) {
			model.setConNum(jsonObject.getInteger("conNum"));
		}
		if (jsonObject.containsKey("nodeIndex")) {
			model.setNodeIndex(jsonObject.getInteger("nodeIndex"));
		}
		if (jsonObject.containsKey("typeId")) {
			model.setTypeId(jsonObject.getString("typeId"));
		}
		if (jsonObject.containsKey("userIndex")) {
			model.setUserIndex(jsonObject.getString("userIndex"));
		}
		if (jsonObject.containsKey("orderIndex")) {
			model.setOrderIndex(jsonObject.getInteger("orderIndex"));
		}
		if (jsonObject.containsKey("parentDName")) {
			model.setParentDName(jsonObject.getString("parentDName"));
		}
		if (jsonObject.containsKey("sysAuto")) {
			model.setSysAuto(jsonObject.getInteger("sysAuto"));
		}
		if (jsonObject.containsKey("orderIndexF")) {
			model.setOrderIndexF(jsonObject.getInteger("orderIndexF"));
		}
		if (jsonObject.containsKey("orderIdF")) {
			model.setOrderIdF(jsonObject.getString("orderIdF"));
		}
		if (jsonObject.containsKey("orderConF")) {
			model.setOrderConF(jsonObject.getInteger("orderConF"));
		}
		if (jsonObject.containsKey("isPrintValue")) {
			model.setIsPrintValue(jsonObject.getInteger("isPrintValue"));
		}
		if (jsonObject.containsKey("isShowValueOnLast")) {
			model.setIsShowValueOnLast(jsonObject
					.getInteger("isShowValueOnLast"));
		}
		if (jsonObject.containsKey("isBankRoundType")) {
			model.setIsBankRoundType(jsonObject.getInteger("isBankRoundType"));
		}
		if (jsonObject.containsKey("floatBankRound")) {
			model.setFloatBankRound(jsonObject.getDouble("floatBankRound"));
		}
		if (jsonObject.containsKey("intUseWBSPlace")) {
			model.setIntUseWBSPlace(jsonObject.getInteger("intUseWBSPlace"));
		}

		return model;
	}

	public static JSONObject toJsonObject(CellRepInfo model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("listId", model.getListId());
		jsonObject.put("_listId_", model.getListId());
		if (model.getIndexId() != null) {
			jsonObject.put("indexId", model.getIndexId());
		}
		if (model.getFrmType() != null) {
			jsonObject.put("frmType", model.getFrmType());
		}
		if (model.getIsSystem() != null) {
			jsonObject.put("isSystem", model.getIsSystem());
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
		if (model.getShowType() != null) {
			jsonObject.put("showType", model.getShowType());
		}
		jsonObject.put("strLen", model.getStrLen());
		if (model.getForm() != null) {
			jsonObject.put("form", model.getForm());
		}
		if (model.getInType() != null) {
			jsonObject.put("inType", model.getInType());
		}
		if (model.getHintID() != null) {
			jsonObject.put("hintID", model.getHintID());
		}
		jsonObject.put("listNo", model.getListNo());
		if (model.getZtype() != null) {
			jsonObject.put("ztype", model.getZtype());
		}
		if (model.getIsMustFill() != null) {
			jsonObject.put("isMustFill", model.getIsMustFill());
		}
		if (model.getIsListShow() != null) {
			jsonObject.put("isListShow", model.getIsListShow());
		}
		jsonObject.put("listWeigth", model.getListWeigth());
		if (model.getPanid() != null) {
			jsonObject.put("panid", model.getPanid());
		}
		if (model.getIsAllWidth() != null) {
			jsonObject.put("isAllWidth", model.getIsAllWidth());
		}
		if (model.getIstName() != null) {
			jsonObject.put("istName", model.getIstName());
		}
		if (model.getFileDotFileId() != null) {
			jsonObject.put("fileDotFileId", model.getFileDotFileId());
		}
		jsonObject.put("dataPoint", model.getDataPoint());
		if (model.getDefaultValue() != null) {
			jsonObject.put("defaultValue", model.getDefaultValue());
		}
		if (model.getIsSubTable() != null) {
			jsonObject.put("isSubTable", model.getIsSubTable());
		}
		if (model.getIsDataOnly() != null) {
			jsonObject.put("isDataOnly", model.getIsDataOnly());
		}
		jsonObject.put("checkType", model.getCheckType());
		if (model.getOrderId() != null) {
			jsonObject.put("orderId", model.getOrderId());
		}
		jsonObject.put("cellType", model.getCellType());
		if (model.getOstTableName() != null) {
			jsonObject.put("ostTableName", model.getOstTableName());
		}
		jsonObject.put("ostRow", model.getOstRow());
		jsonObject.put("ostCol", model.getOstCol());
		if (model.getHintList() != null) {
			jsonObject.put("hintList", model.getHintList());
		}
		if (model.getOstCellId() != null) {
			jsonObject.put("ostCellId", model.getOstCellId());
		}
		if (model.getOldDName() != null) {
			jsonObject.put("oldDName", model.getOldDName());
		}
		if (model.getOstCellName() != null) {
			jsonObject.put("ostCellName", model.getOstCellName());
		}
		if (model.getOstFormula() != null) {
			jsonObject.put("ostFormula", model.getOstFormula());
		}
		jsonObject.put("ostColor", model.getOstColor());
		jsonObject.put("ostSumType", model.getOstSumType());
		if (model.getDataTableName() != null) {
			jsonObject.put("dataTableName", model.getDataTableName());
		}
		if (model.getDataFieldName() != null) {
			jsonObject.put("dataFieldName", model.getDataFieldName());
		}
		jsonObject.put("orderCon", model.getOrderCon());
		jsonObject.put("conNum", model.getConNum());
		jsonObject.put("nodeIndex", model.getNodeIndex());
		if (model.getTypeId() != null) {
			jsonObject.put("typeId", model.getTypeId());
		}
		if (model.getUserIndex() != null) {
			jsonObject.put("userIndex", model.getUserIndex());
		}
		jsonObject.put("orderIndex", model.getOrderIndex());
		if (model.getParentDName() != null) {
			jsonObject.put("parentDName", model.getParentDName());
		}
		jsonObject.put("sysAuto", model.getSysAuto());
		jsonObject.put("orderIndexF", model.getOrderIndexF());
		if (model.getOrderIdF() != null) {
			jsonObject.put("orderIdF", model.getOrderIdF());
		}
		jsonObject.put("orderConF", model.getOrderConF());
		jsonObject.put("isPrintValue", model.getIsPrintValue());
		jsonObject.put("isShowValueOnLast", model.getIsShowValueOnLast());
		jsonObject.put("isBankRoundType", model.getIsBankRoundType());
		jsonObject.put("floatBankRound", model.getFloatBankRound());
		jsonObject.put("intUseWBSPlace", model.getIntUseWBSPlace());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(CellRepInfo model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("listId", model.getListId());
		jsonObject.put("_listId_", model.getListId());
		if (model.getIndexId() != null) {
			jsonObject.put("indexId", model.getIndexId());
		}
		if (model.getFrmType() != null) {
			jsonObject.put("frmType", model.getFrmType());
		}
		if (model.getIsSystem() != null) {
			jsonObject.put("isSystem", model.getIsSystem());
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
		if (model.getShowType() != null) {
			jsonObject.put("showType", model.getShowType());
		}
		jsonObject.put("strLen", model.getStrLen());
		if (model.getForm() != null) {
			jsonObject.put("form", model.getForm());
		}
		if (model.getInType() != null) {
			jsonObject.put("inType", model.getInType());
		}
		if (model.getHintID() != null) {
			jsonObject.put("hintID", model.getHintID());
		}
		jsonObject.put("listNo", model.getListNo());
		if (model.getZtype() != null) {
			jsonObject.put("ztype", model.getZtype());
		}
		if (model.getIsMustFill() != null) {
			jsonObject.put("isMustFill", model.getIsMustFill());
		}
		if (model.getIsListShow() != null) {
			jsonObject.put("isListShow", model.getIsListShow());
		}
		jsonObject.put("listWeigth", model.getListWeigth());
		if (model.getPanid() != null) {
			jsonObject.put("panid", model.getPanid());
		}
		if (model.getIsAllWidth() != null) {
			jsonObject.put("isAllWidth", model.getIsAllWidth());
		}
		if (model.getIstName() != null) {
			jsonObject.put("istName", model.getIstName());
		}
		if (model.getFileDotFileId() != null) {
			jsonObject.put("fileDotFileId", model.getFileDotFileId());
		}
		jsonObject.put("dataPoint", model.getDataPoint());
		if (model.getDefaultValue() != null) {
			jsonObject.put("defaultValue", model.getDefaultValue());
		}
		if (model.getIsSubTable() != null) {
			jsonObject.put("isSubTable", model.getIsSubTable());
		}
		if (model.getIsDataOnly() != null) {
			jsonObject.put("isDataOnly", model.getIsDataOnly());
		}
		jsonObject.put("checkType", model.getCheckType());
		if (model.getOrderId() != null) {
			jsonObject.put("orderId", model.getOrderId());
		}
		jsonObject.put("cellType", model.getCellType());
		if (model.getOstTableName() != null) {
			jsonObject.put("ostTableName", model.getOstTableName());
		}
		jsonObject.put("ostRow", model.getOstRow());
		jsonObject.put("ostCol", model.getOstCol());
		if (model.getHintList() != null) {
			jsonObject.put("hintList", model.getHintList());
		}
		if (model.getOstCellId() != null) {
			jsonObject.put("ostCellId", model.getOstCellId());
		}
		if (model.getOldDName() != null) {
			jsonObject.put("oldDName", model.getOldDName());
		}
		if (model.getOstCellName() != null) {
			jsonObject.put("ostCellName", model.getOstCellName());
		}
		if (model.getOstFormula() != null) {
			jsonObject.put("ostFormula", model.getOstFormula());
		}
		jsonObject.put("ostColor", model.getOstColor());
		jsonObject.put("ostSumType", model.getOstSumType());
		if (model.getDataTableName() != null) {
			jsonObject.put("dataTableName", model.getDataTableName());
		}
		if (model.getDataFieldName() != null) {
			jsonObject.put("dataFieldName", model.getDataFieldName());
		}
		jsonObject.put("orderCon", model.getOrderCon());
		jsonObject.put("conNum", model.getConNum());
		jsonObject.put("nodeIndex", model.getNodeIndex());
		if (model.getTypeId() != null) {
			jsonObject.put("typeId", model.getTypeId());
		}
		if (model.getUserIndex() != null) {
			jsonObject.put("userIndex", model.getUserIndex());
		}
		jsonObject.put("orderIndex", model.getOrderIndex());
		if (model.getParentDName() != null) {
			jsonObject.put("parentDName", model.getParentDName());
		}
		jsonObject.put("sysAuto", model.getSysAuto());
		jsonObject.put("orderIndexF", model.getOrderIndexF());
		if (model.getOrderIdF() != null) {
			jsonObject.put("orderIdF", model.getOrderIdF());
		}
		jsonObject.put("orderConF", model.getOrderConF());
		jsonObject.put("isPrintValue", model.getIsPrintValue());
		jsonObject.put("isShowValueOnLast", model.getIsShowValueOnLast());
		jsonObject.put("isBankRoundType", model.getIsBankRoundType());
		jsonObject.put("floatBankRound", model.getFloatBankRound());
		jsonObject.put("intUseWBSPlace", model.getIntUseWBSPlace());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<CellRepInfo> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (CellRepInfo model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<CellRepInfo> arrayToList(JSONArray array) {
		java.util.List<CellRepInfo> list = new java.util.ArrayList<CellRepInfo>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			CellRepInfo model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private CellRepInfoJsonFactory() {

	}

}
