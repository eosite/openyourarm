package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.isdp.domain.Pinfo;

/**
 * 
 * JSON工厂类
 *
 */
public class PinfoJsonFactory {

	public static Pinfo jsonToObject(JSONObject jsonObject) {
		Pinfo model = new Pinfo();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("indexId")) {
			model.setIndexId(jsonObject.getInteger("indexId"));
		}
		if (jsonObject.containsKey("itemNum")) {
			model.setItemNum(jsonObject.getString("itemNum"));
		}
		if (jsonObject.containsKey("dtag")) {
			model.setDtag(jsonObject.getString("dtag"));
		}
		if (jsonObject.containsKey("ftag")) {
			model.setFtag(jsonObject.getString("ftag"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("allName")) {
			model.setAllName(jsonObject.getString("allName"));
		}
		if (jsonObject.containsKey("bcompany")) {
			model.setBcompany(jsonObject.getString("bcompany"));
		}
		if (jsonObject.containsKey("ccompany")) {
			model.setCcompany(jsonObject.getString("ccompany"));
		}
		if (jsonObject.containsKey("dcompany")) {
			model.setDcompany(jsonObject.getString("dcompany"));
		}
		if (jsonObject.containsKey("conCompany")) {
			model.setConCompany(jsonObject.getString("conCompany"));
		}
		if (jsonObject.containsKey("icompany")) {
			model.setIcompany(jsonObject.getString("icompany"));
		}
		if (jsonObject.containsKey("cmark")) {
			model.setCmark(jsonObject.getString("cmark"));
		}
		if (jsonObject.containsKey("pmark")) {
			model.setPmark(jsonObject.getString("pmark"));
		}
		if (jsonObject.containsKey("tpmark")) {
			model.setTpmark(jsonObject.getString("tpmark"));
		}
		if (jsonObject.containsKey("conMark")) {
			model.setConMark(jsonObject.getString("conMark"));
		}
		if (jsonObject.containsKey("mapNum")) {
			model.setMapNum(jsonObject.getString("mapNum"));
		}
		if (jsonObject.containsKey("conStart")) {
			model.setConStart(jsonObject.getString("conStart"));
		}
		if (jsonObject.containsKey("conEnd")) {
			model.setConEnd(jsonObject.getString("conEnd"));
		}
		if (jsonObject.containsKey("totalLen")) {
			model.setTotalLen(jsonObject.getDouble("totalLen"));
		}
		if (jsonObject.containsKey("startDate")) {
			model.setStartDate(jsonObject.getDate("startDate"));
		}
		if (jsonObject.containsKey("endDate")) {
			model.setEndDate(jsonObject.getDate("endDate"));
		}
		if (jsonObject.containsKey("cost")) {
			model.setCost(jsonObject.getDouble("cost"));
		}
		if (jsonObject.containsKey("balance")) {
			model.setBalance(jsonObject.getDouble("balance"));
		}
		if (jsonObject.containsKey("pmannager")) {
			model.setPmannager(jsonObject.getString("pmannager"));
		}
		if (jsonObject.containsKey("enginee")) {
			model.setEnginee(jsonObject.getString("enginee"));
		}
		if (jsonObject.containsKey("owner")) {
			model.setOwner(jsonObject.getString("owner"));
		}
		if (jsonObject.containsKey("planDate")) {
			model.setPlanDate(jsonObject.getDate("planDate"));
		}
		if (jsonObject.containsKey("hintDay")) {
			model.setHintDay(jsonObject.getInteger("hintDay"));
		}
		if (jsonObject.containsKey("duty")) {
			model.setDuty(jsonObject.getString("duty"));
		}
		if (jsonObject.containsKey("removeDate")) {
			model.setRemoveDate(jsonObject.getDate("removeDate"));
		}
		if (jsonObject.containsKey("removeDuty")) {
			model.setRemoveDuty(jsonObject.getString("removeDuty"));
		}
		if (jsonObject.containsKey("removeFile")) {
			model.setRemoveFile(jsonObject.getString("removeFile"));
		}
		if (jsonObject.containsKey("partProj")) {
			model.setPartProj(jsonObject.getString("partProj"));
		}
		if (jsonObject.containsKey("cnum")) {
			model.setCnum(jsonObject.getString("cnum"));
		}
		if (jsonObject.containsKey("concompany2")) {
			model.setConcompany2(jsonObject.getString("concompany2"));
		}
		if (jsonObject.containsKey("icompany2")) {
			model.setIcompany2(jsonObject.getString("icompany2"));
		}
		if (jsonObject.containsKey("mileAge")) {
			model.setMileAge(jsonObject.getDouble("mileAge"));
		}
		if (jsonObject.containsKey("place")) {
			model.setPlace(jsonObject.getString("place"));
		}
		if (jsonObject.containsKey("place1")) {
			model.setPlace1(jsonObject.getDouble("place1"));
		}
		if (jsonObject.containsKey("place2")) {
			model.setPlace2(jsonObject.getDouble("place2"));
		}
		if (jsonObject.containsKey("place3")) {
			model.setPlace3(jsonObject.getDouble("place3"));
		}
		if (jsonObject.containsKey("place4")) {
			model.setPlace4(jsonObject.getDouble("place4"));
		}
		if (jsonObject.containsKey("setPlace")) {
			model.setSetPlace(jsonObject.getString("setPlace"));
		}
		if (jsonObject.containsKey("setTemp")) {
			model.setSetTemp(jsonObject.getString("setTemp"));
		}
		if (jsonObject.containsKey("bdNum")) {
			model.setBdNum(jsonObject.getString("bdNum"));
		}
		if (jsonObject.containsKey("dtNum")) {
			model.setDtNum(jsonObject.getString("dtNum"));
		}
		if (jsonObject.containsKey("pinfoUser2")) {
			model.setPinfoUser2(jsonObject.getString("pinfoUser2"));
		}
		if (jsonObject.containsKey("pinfoUser3")) {
			model.setPinfoUser3(jsonObject.getDouble("pinfoUser3"));
		}
		if (jsonObject.containsKey("pinfoUser4")) {
			model.setPinfoUser4(jsonObject.getDouble("pinfoUser4"));
		}
		if (jsonObject.containsKey("pinfoUser5")) {
			model.setPinfoUser5(jsonObject.getString("pinfoUser5"));
		}
		if (jsonObject.containsKey("pinfoUser6")) {
			model.setPinfoUser6(jsonObject.getInteger("pinfoUser6"));
		}
		if (jsonObject.containsKey("pinfoUser7")) {
			model.setPinfoUser7(jsonObject.getDouble("pinfoUser7"));
		}
		if (jsonObject.containsKey("pinfoUser8")) {
			model.setPinfoUser8(jsonObject.getDouble("pinfoUser8"));
		}
		if (jsonObject.containsKey("pinfoUser9")) {
			model.setPinfoUser9(jsonObject.getString("pinfoUser9"));
		}

		return model;
	}

	public static JSONObject toJsonObject(Pinfo model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("indexId", model.getIndexId());
		if (model.getItemNum() != null) {
			jsonObject.put("itemNum", model.getItemNum());
		}
		if (model.getDtag() != null) {
			jsonObject.put("dtag", model.getDtag());
		}
		if (model.getFtag() != null) {
			jsonObject.put("ftag", model.getFtag());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getAllName() != null) {
			jsonObject.put("allName", model.getAllName());
		}
		if (model.getBcompany() != null) {
			jsonObject.put("bcompany", model.getBcompany());
		}
		if (model.getCcompany() != null) {
			jsonObject.put("ccompany", model.getCcompany());
		}
		if (model.getDcompany() != null) {
			jsonObject.put("dcompany", model.getDcompany());
		}
		if (model.getConCompany() != null) {
			jsonObject.put("conCompany", model.getConCompany());
		}
		if (model.getIcompany() != null) {
			jsonObject.put("icompany", model.getIcompany());
		}
		if (model.getCmark() != null) {
			jsonObject.put("cmark", model.getCmark());
		}
		if (model.getPmark() != null) {
			jsonObject.put("pmark", model.getPmark());
		}
		if (model.getTpmark() != null) {
			jsonObject.put("tpmark", model.getTpmark());
		}
		if (model.getConMark() != null) {
			jsonObject.put("conMark", model.getConMark());
		}
		if (model.getMapNum() != null) {
			jsonObject.put("mapNum", model.getMapNum());
		}
		if (model.getConStart() != null) {
			jsonObject.put("conStart", model.getConStart());
		}
		if (model.getConEnd() != null) {
			jsonObject.put("conEnd", model.getConEnd());
		}
		jsonObject.put("totalLen", model.getTotalLen());
		if (model.getStartDate() != null) {
			jsonObject
					.put("startDate", DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_date",
					DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_datetime",
					DateUtils.getDateTime(model.getStartDate()));
		}
		if (model.getEndDate() != null) {
			jsonObject.put("endDate", DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_date",
					DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_datetime",
					DateUtils.getDateTime(model.getEndDate()));
		}
		jsonObject.put("cost", model.getCost());
		jsonObject.put("balance", model.getBalance());
		if (model.getPmannager() != null) {
			jsonObject.put("pmannager", model.getPmannager());
		}
		if (model.getEnginee() != null) {
			jsonObject.put("enginee", model.getEnginee());
		}
		if (model.getOwner() != null) {
			jsonObject.put("owner", model.getOwner());
		}
		if (model.getPlanDate() != null) {
			jsonObject.put("planDate", DateUtils.getDate(model.getPlanDate()));
			jsonObject.put("planDate_date",
					DateUtils.getDate(model.getPlanDate()));
			jsonObject.put("planDate_datetime",
					DateUtils.getDateTime(model.getPlanDate()));
		}
		jsonObject.put("hintDay", model.getHintDay());
		if (model.getDuty() != null) {
			jsonObject.put("duty", model.getDuty());
		}
		if (model.getRemoveDate() != null) {
			jsonObject.put("removeDate",
					DateUtils.getDate(model.getRemoveDate()));
			jsonObject.put("removeDate_date",
					DateUtils.getDate(model.getRemoveDate()));
			jsonObject.put("removeDate_datetime",
					DateUtils.getDateTime(model.getRemoveDate()));
		}
		if (model.getRemoveDuty() != null) {
			jsonObject.put("removeDuty", model.getRemoveDuty());
		}
		if (model.getRemoveFile() != null) {
			jsonObject.put("removeFile", model.getRemoveFile());
		}
		if (model.getPartProj() != null) {
			jsonObject.put("partProj", model.getPartProj());
		}
		if (model.getCnum() != null) {
			jsonObject.put("cnum", model.getCnum());
		}
		if (model.getConcompany2() != null) {
			jsonObject.put("concompany2", model.getConcompany2());
		}
		if (model.getIcompany2() != null) {
			jsonObject.put("icompany2", model.getIcompany2());
		}
		jsonObject.put("mileAge", model.getMileAge());
		if (model.getPlace() != null) {
			jsonObject.put("place", model.getPlace());
		}
		jsonObject.put("place1", model.getPlace1());
		jsonObject.put("place2", model.getPlace2());
		jsonObject.put("place3", model.getPlace3());
		jsonObject.put("place4", model.getPlace4());
		if (model.getSetPlace() != null) {
			jsonObject.put("setPlace", model.getSetPlace());
		}
		if (model.getSetTemp() != null) {
			jsonObject.put("setTemp", model.getSetTemp());
		}
		if (model.getBdNum() != null) {
			jsonObject.put("bdNum", model.getBdNum());
		}
		if (model.getDtNum() != null) {
			jsonObject.put("dtNum", model.getDtNum());
		}
		if (model.getPinfoUser2() != null) {
			jsonObject.put("pinfoUser2", model.getPinfoUser2());
		}
		jsonObject.put("pinfoUser3", model.getPinfoUser3());
		jsonObject.put("pinfoUser4", model.getPinfoUser4());
		if (model.getPinfoUser5() != null) {
			jsonObject.put("pinfoUser5", model.getPinfoUser5());
		}
		jsonObject.put("pinfoUser6", model.getPinfoUser6());
		jsonObject.put("pinfoUser7", model.getPinfoUser7());
		jsonObject.put("pinfoUser8", model.getPinfoUser8());
		if (model.getPinfoUser9() != null) {
			jsonObject.put("pinfoUser9", model.getPinfoUser9());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(Pinfo model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("indexId", model.getIndexId());
		if (model.getItemNum() != null) {
			jsonObject.put("itemNum", model.getItemNum());
		}
		if (model.getDtag() != null) {
			jsonObject.put("dtag", model.getDtag());
		}
		if (model.getFtag() != null) {
			jsonObject.put("ftag", model.getFtag());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getAllName() != null) {
			jsonObject.put("allName", model.getAllName());
		}
		if (model.getBcompany() != null) {
			jsonObject.put("bcompany", model.getBcompany());
		}
		if (model.getCcompany() != null) {
			jsonObject.put("ccompany", model.getCcompany());
		}
		if (model.getDcompany() != null) {
			jsonObject.put("dcompany", model.getDcompany());
		}
		if (model.getConCompany() != null) {
			jsonObject.put("conCompany", model.getConCompany());
		}
		if (model.getIcompany() != null) {
			jsonObject.put("icompany", model.getIcompany());
		}
		if (model.getCmark() != null) {
			jsonObject.put("cmark", model.getCmark());
		}
		if (model.getPmark() != null) {
			jsonObject.put("pmark", model.getPmark());
		}
		if (model.getTpmark() != null) {
			jsonObject.put("tpmark", model.getTpmark());
		}
		if (model.getConMark() != null) {
			jsonObject.put("conMark", model.getConMark());
		}
		if (model.getMapNum() != null) {
			jsonObject.put("mapNum", model.getMapNum());
		}
		if (model.getConStart() != null) {
			jsonObject.put("conStart", model.getConStart());
		}
		if (model.getConEnd() != null) {
			jsonObject.put("conEnd", model.getConEnd());
		}
		jsonObject.put("totalLen", model.getTotalLen());
		if (model.getStartDate() != null) {
			jsonObject
					.put("startDate", DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_date",
					DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_datetime",
					DateUtils.getDateTime(model.getStartDate()));
		}
		if (model.getEndDate() != null) {
			jsonObject.put("endDate", DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_date",
					DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_datetime",
					DateUtils.getDateTime(model.getEndDate()));
		}
		jsonObject.put("cost", model.getCost());
		jsonObject.put("balance", model.getBalance());
		if (model.getPmannager() != null) {
			jsonObject.put("pmannager", model.getPmannager());
		}
		if (model.getEnginee() != null) {
			jsonObject.put("enginee", model.getEnginee());
		}
		if (model.getOwner() != null) {
			jsonObject.put("owner", model.getOwner());
		}
		if (model.getPlanDate() != null) {
			jsonObject.put("planDate", DateUtils.getDate(model.getPlanDate()));
			jsonObject.put("planDate_date",
					DateUtils.getDate(model.getPlanDate()));
			jsonObject.put("planDate_datetime",
					DateUtils.getDateTime(model.getPlanDate()));
		}
		jsonObject.put("hintDay", model.getHintDay());
		if (model.getDuty() != null) {
			jsonObject.put("duty", model.getDuty());
		}
		if (model.getRemoveDate() != null) {
			jsonObject.put("removeDate",
					DateUtils.getDate(model.getRemoveDate()));
			jsonObject.put("removeDate_date",
					DateUtils.getDate(model.getRemoveDate()));
			jsonObject.put("removeDate_datetime",
					DateUtils.getDateTime(model.getRemoveDate()));
		}
		if (model.getRemoveDuty() != null) {
			jsonObject.put("removeDuty", model.getRemoveDuty());
		}
		if (model.getRemoveFile() != null) {
			jsonObject.put("removeFile", model.getRemoveFile());
		}
		if (model.getPartProj() != null) {
			jsonObject.put("partProj", model.getPartProj());
		}
		if (model.getCnum() != null) {
			jsonObject.put("cnum", model.getCnum());
		}
		if (model.getConcompany2() != null) {
			jsonObject.put("concompany2", model.getConcompany2());
		}
		if (model.getIcompany2() != null) {
			jsonObject.put("icompany2", model.getIcompany2());
		}
		jsonObject.put("mileAge", model.getMileAge());
		if (model.getPlace() != null) {
			jsonObject.put("place", model.getPlace());
		}
		jsonObject.put("place1", model.getPlace1());
		jsonObject.put("place2", model.getPlace2());
		jsonObject.put("place3", model.getPlace3());
		jsonObject.put("place4", model.getPlace4());
		if (model.getSetPlace() != null) {
			jsonObject.put("setPlace", model.getSetPlace());
		}
		if (model.getSetTemp() != null) {
			jsonObject.put("setTemp", model.getSetTemp());
		}
		if (model.getBdNum() != null) {
			jsonObject.put("bdNum", model.getBdNum());
		}
		if (model.getDtNum() != null) {
			jsonObject.put("dtNum", model.getDtNum());
		}
		if (model.getPinfoUser2() != null) {
			jsonObject.put("pinfoUser2", model.getPinfoUser2());
		}
		jsonObject.put("pinfoUser3", model.getPinfoUser3());
		jsonObject.put("pinfoUser4", model.getPinfoUser4());
		if (model.getPinfoUser5() != null) {
			jsonObject.put("pinfoUser5", model.getPinfoUser5());
		}
		jsonObject.put("pinfoUser6", model.getPinfoUser6());
		jsonObject.put("pinfoUser7", model.getPinfoUser7());
		jsonObject.put("pinfoUser8", model.getPinfoUser8());
		if (model.getPinfoUser9() != null) {
			jsonObject.put("pinfoUser9", model.getPinfoUser9());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<Pinfo> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Pinfo model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<Pinfo> arrayToList(JSONArray array) {
		java.util.List<Pinfo> list = new java.util.ArrayList<Pinfo>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Pinfo model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private PinfoJsonFactory() {

	}

}
