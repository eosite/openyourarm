package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.isdp.domain.Pfile;

/**
 * 
 * JSON工厂类
 *
 */
public class PfileJsonFactory {

	public static Pfile jsonToObject(JSONObject jsonObject) {
		Pfile model = new Pfile();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("efileNum")) {
			model.setEfileNum(jsonObject.getInteger("efileNum"));
		}
		if (jsonObject.containsKey("listNum")) {
			model.setListNum(jsonObject.getString("listNum"));
		}
		if (jsonObject.containsKey("listId")) {
			model.setListId(jsonObject.getString("listId"));
		}
		if (jsonObject.containsKey("pid")) {
			model.setPid(jsonObject.getInteger("pid"));
		}
		if (jsonObject.containsKey("projId")) {
			model.setProjId(jsonObject.getInteger("projId"));
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
		if (jsonObject.containsKey("topNodeM")) {
			model.setTopNodeM(jsonObject.getString("topNodeM"));
		}
		if (jsonObject.containsKey("vid")) {
			model.setVid(jsonObject.getString("vid"));
		}
		if (jsonObject.containsKey("oldVid")) {
			model.setOldVid(jsonObject.getString("oldVid"));
		}
		if (jsonObject.containsKey("visFlag")) {
			model.setVisFlag(jsonObject.getString("visFlag"));
		}
		if (jsonObject.containsKey("listNo")) {
			model.setListNo(jsonObject.getInteger("listNo"));
		}
		if (jsonObject.containsKey("filingFlag")) {
			model.setFilingFlag(jsonObject.getString("filingFlag"));
		}
		if (jsonObject.containsKey("saveFlag")) {
			model.setSaveFlag(jsonObject.getString("saveFlag"));
		}
		if (jsonObject.containsKey("openFlag")) {
			model.setOpenFlag(jsonObject.getString("openFlag"));
		}
		if (jsonObject.containsKey("checkupFlag")) {
			model.setCheckupFlag(jsonObject.getString("checkupFlag"));
		}
		if (jsonObject.containsKey("finishFlag")) {
			model.setFinishFlag(jsonObject.getString("finishFlag"));
		}
		if (jsonObject.containsKey("fromID")) {
			model.setFromID(jsonObject.getString("fromID"));
		}
		if (jsonObject.containsKey("tname")) {
			model.setTname(jsonObject.getString("tname"));
		}
		if (jsonObject.containsKey("duty")) {
			model.setDuty(jsonObject.getString("duty"));
		}
		if (jsonObject.containsKey("tagnum")) {
			model.setTagnum(jsonObject.getString("tagnum"));
		}
		if (jsonObject.containsKey("fileNum")) {
			model.setFileNum(jsonObject.getString("fileNum"));
		}
		if (jsonObject.containsKey("thematic")) {
			model.setThematic(jsonObject.getString("thematic"));
		}
		if (jsonObject.containsKey("ctime")) {
			model.setCtime(jsonObject.getDate("ctime"));
		}
		if (jsonObject.containsKey("pageNo")) {
			model.setPageNo(jsonObject.getString("pageNo"));
		}
		if (jsonObject.containsKey("Level")) {
			model.setLevel(jsonObject.getString("Level"));
		}
		if (jsonObject.containsKey("page")) {
			model.setPage(jsonObject.getInteger("page"));
		}
		if (jsonObject.containsKey("fileType")) {
			model.setFileType(jsonObject.getString("fileType"));
		}
		if (jsonObject.containsKey("fontsNum")) {
			model.setFontsNum(jsonObject.getString("fontsNum"));
		}
		if (jsonObject.containsKey("saveTime")) {
			model.setSaveTime(jsonObject.getString("saveTime"));
		}
		if (jsonObject.containsKey("company")) {
			model.setCompany(jsonObject.getString("company"));
		}
		if (jsonObject.containsKey("year")) {
			model.setYear(jsonObject.getInteger("year"));
		}
		if (jsonObject.containsKey("fileAtt")) {
			model.setFileAtt(jsonObject.getString("fileAtt"));
		}
		if (jsonObject.containsKey("annotations")) {
			model.setAnnotations(jsonObject.getString("annotations"));
		}
		if (jsonObject.containsKey("carrierType")) {
			model.setCarrierType(jsonObject.getString("carrierType"));
		}
		if (jsonObject.containsKey("summary")) {
			model.setSummary(jsonObject.getString("summary"));
		}
		if (jsonObject.containsKey("ptext")) {
			model.setPtext(jsonObject.getString("ptext"));
		}
		if (jsonObject.containsKey("carrieru")) {
			model.setCarrieru(jsonObject.getString("carrieru"));
		}
		if (jsonObject.containsKey("glideNum")) {
			model.setGlideNum(jsonObject.getString("glideNum"));
		}
		if (jsonObject.containsKey("efile")) {
			model.setEfile(jsonObject.getString("efile"));
		}
		if (jsonObject.containsKey("ftime")) {
			model.setFtime(jsonObject.getDate("ftime"));
		}
		if (jsonObject.containsKey("totalNum")) {
			model.setTotalNum(jsonObject.getString("totalNum"));
		}
		if (jsonObject.containsKey("inputMan")) {
			model.setInputMan(jsonObject.getString("inputMan"));
		}
		if (jsonObject.containsKey("etime")) {
			model.setEtime(jsonObject.getDate("etime"));
		}
		if (jsonObject.containsKey("dotNum")) {
			model.setDotNum(jsonObject.getString("dotNum"));
		}
		if (jsonObject.containsKey("picNum")) {
			model.setPicNum(jsonObject.getString("picNum"));
		}
		if (jsonObject.containsKey("recNum")) {
			model.setRecNum(jsonObject.getString("recNum"));
		}
		if (jsonObject.containsKey("total")) {
			model.setTotal(jsonObject.getInteger("total"));
		}
		if (jsonObject.containsKey("pageType")) {
			model.setPageType(jsonObject.getString("pageType"));
		}
		if (jsonObject.containsKey("isCom")) {
			model.setIsCom(jsonObject.getString("isCom"));
		}
		if (jsonObject.containsKey("isLocate")) {
			model.setIsLocate(jsonObject.getString("isLocate"));
		}
		if (jsonObject.containsKey("wcompany")) {
			model.setWcompany(jsonObject.getString("wcompany"));
		}
		if (jsonObject.containsKey("wcompanyID")) {
			model.setWcompanyID(jsonObject.getString("wcompanyID"));
		}
		if (jsonObject.containsKey("sendFlag")) {
			model.setSendFlag(jsonObject.getString("sendFlag"));
		}
		if (jsonObject.containsKey("lcontent")) {
			model.setLcontent(jsonObject.getString("lcontent"));
		}
		if (jsonObject.containsKey("lcompany")) {
			model.setLcompany(jsonObject.getString("lcompany"));
		}
		if (jsonObject.containsKey("lman")) {
			model.setLman(jsonObject.getString("lman"));
		}
		if (jsonObject.containsKey("llen")) {
			model.setLlen(jsonObject.getString("llen"));
		}
		if (jsonObject.containsKey("ldate")) {
			model.setLdate(jsonObject.getDate("ldate"));
		}
		if (jsonObject.containsKey("jconten")) {
			model.setJconten(jsonObject.getString("jconten"));
		}
		if (jsonObject.containsKey("jplace")) {
			model.setJplace(jsonObject.getString("jplace"));
		}
		if (jsonObject.containsKey("jman")) {
			model.setJman(jsonObject.getString("jman"));
		}
		if (jsonObject.containsKey("jback")) {
			model.setJback(jsonObject.getString("jback"));
		}
		if (jsonObject.containsKey("jactor")) {
			model.setJactor(jsonObject.getString("jactor"));
		}
		if (jsonObject.containsKey("jnum")) {
			model.setJnum(jsonObject.getString("jnum"));
		}
		if (jsonObject.containsKey("jbnum")) {
			model.setJbnum(jsonObject.getString("jbnum"));
		}
		if (jsonObject.containsKey("tnum")) {
			model.setTnum(jsonObject.getString("tnum"));
		}
		if (jsonObject.containsKey("tzoom")) {
			model.setTzoom(jsonObject.getString("tzoom"));
		}
		if (jsonObject.containsKey("tflag")) {
			model.setTflag(jsonObject.getString("tflag"));
		}
		if (jsonObject.containsKey("tdesigner")) {
			model.setTdesigner(jsonObject.getString("tdesigner"));
		}
		if (jsonObject.containsKey("tviewer")) {
			model.setTviewer(jsonObject.getString("tviewer"));
		}
		if (jsonObject.containsKey("tassessor")) {
			model.setTassessor(jsonObject.getString("tassessor"));
		}
		if (jsonObject.containsKey("tvision")) {
			model.setTvision(jsonObject.getString("tvision"));
		}
		if (jsonObject.containsKey("clistNo")) {
			model.setClistNo(jsonObject.getInteger("clistNo"));
		}
		if (jsonObject.containsKey("cpageNo")) {
			model.setCpageNo(jsonObject.getString("cpageNo"));
		}
		if (jsonObject.containsKey("vnum")) {
			model.setVnum(jsonObject.getString("vnum"));
		}
		if (jsonObject.containsKey("cvnum")) {
			model.setCvnum(jsonObject.getString("cvnum"));
		}
		if (jsonObject.containsKey("treedListStr")) {
			model.setTreedListStr(jsonObject.getString("treedListStr"));
		}
		if (jsonObject.containsKey("ctimeEnd")) {
			model.setCtimeEnd(jsonObject.getDate("ctimeEnd"));
		}
		if (jsonObject.containsKey("projIndex")) {
			model.setProjIndex(jsonObject.getInteger("projIndex"));
		}
		if (jsonObject.containsKey("treeParent")) {
			model.setTreeParent(jsonObject.getInteger("treeParent"));
		}
		if (jsonObject.containsKey("treeList")) {
			model.setTreeList(jsonObject.getInteger("treeList"));
		}

		return model;
	}

	public static JSONObject toJsonObject(Pfile model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("efileNum", model.getEfileNum());
		if (model.getListNum() != null) {
			jsonObject.put("listNum", model.getListNum());
		}
		if (model.getListId() != null) {
			jsonObject.put("listId", model.getListId());
		}
		jsonObject.put("pid", model.getPid());
		jsonObject.put("projId", model.getProjId());
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
		if (model.getTopNodeM() != null) {
			jsonObject.put("topNodeM", model.getTopNodeM());
		}
		if (model.getVid() != null) {
			jsonObject.put("vid", model.getVid());
		}
		if (model.getOldVid() != null) {
			jsonObject.put("oldVid", model.getOldVid());
		}
		if (model.getVisFlag() != null) {
			jsonObject.put("visFlag", model.getVisFlag());
		}
		jsonObject.put("listNo", model.getListNo());
		if (model.getFilingFlag() != null) {
			jsonObject.put("filingFlag", model.getFilingFlag());
		}
		if (model.getSaveFlag() != null) {
			jsonObject.put("saveFlag", model.getSaveFlag());
		}
		if (model.getOpenFlag() != null) {
			jsonObject.put("openFlag", model.getOpenFlag());
		}
		if (model.getCheckupFlag() != null) {
			jsonObject.put("checkupFlag", model.getCheckupFlag());
		}
		if (model.getFinishFlag() != null) {
			jsonObject.put("finishFlag", model.getFinishFlag());
		}
		if (model.getFromID() != null) {
			jsonObject.put("fromID", model.getFromID());
		}
		if (model.getTname() != null) {
			jsonObject.put("tname", model.getTname());
		}
		if (model.getDuty() != null) {
			jsonObject.put("duty", model.getDuty());
		}
		if (model.getTagnum() != null) {
			jsonObject.put("tagnum", model.getTagnum());
		}
		if (model.getFileNum() != null) {
			jsonObject.put("fileNum", model.getFileNum());
		}
		if (model.getThematic() != null) {
			jsonObject.put("thematic", model.getThematic());
		}
		if (model.getCtime() != null) {
			jsonObject.put("ctime", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_date", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_datetime",
					DateUtils.getDateTime(model.getCtime()));
		}
		if (model.getPageNo() != null) {
			jsonObject.put("pageNo", model.getPageNo());
		}
		if (model.getLevel() != null) {
			jsonObject.put("Level", model.getLevel());
		}
		jsonObject.put("page", model.getPage());
		if (model.getFileType() != null) {
			jsonObject.put("fileType", model.getFileType());
		}
		if (model.getFontsNum() != null) {
			jsonObject.put("fontsNum", model.getFontsNum());
		}
		if (model.getSaveTime() != null) {
			jsonObject.put("saveTime", model.getSaveTime());
		}
		if (model.getCompany() != null) {
			jsonObject.put("company", model.getCompany());
		}
		jsonObject.put("year", model.getYear());
		if (model.getFileAtt() != null) {
			jsonObject.put("fileAtt", model.getFileAtt());
		}
		if (model.getAnnotations() != null) {
			jsonObject.put("annotations", model.getAnnotations());
		}
		if (model.getCarrierType() != null) {
			jsonObject.put("carrierType", model.getCarrierType());
		}
		if (model.getSummary() != null) {
			jsonObject.put("summary", model.getSummary());
		}
		if (model.getPtext() != null) {
			jsonObject.put("ptext", model.getPtext());
		}
		if (model.getCarrieru() != null) {
			jsonObject.put("carrieru", model.getCarrieru());
		}
		if (model.getGlideNum() != null) {
			jsonObject.put("glideNum", model.getGlideNum());
		}
		if (model.getEfile() != null) {
			jsonObject.put("efile", model.getEfile());
		}
		if (model.getFtime() != null) {
			jsonObject.put("ftime", DateUtils.getDate(model.getFtime()));
			jsonObject.put("ftime_date", DateUtils.getDate(model.getFtime()));
			jsonObject.put("ftime_datetime",
					DateUtils.getDateTime(model.getFtime()));
		}
		if (model.getTotalNum() != null) {
			jsonObject.put("totalNum", model.getTotalNum());
		}
		if (model.getInputMan() != null) {
			jsonObject.put("inputMan", model.getInputMan());
		}
		if (model.getEtime() != null) {
			jsonObject.put("etime", DateUtils.getDate(model.getEtime()));
			jsonObject.put("etime_date", DateUtils.getDate(model.getEtime()));
			jsonObject.put("etime_datetime",
					DateUtils.getDateTime(model.getEtime()));
		}
		if (model.getDotNum() != null) {
			jsonObject.put("dotNum", model.getDotNum());
		}
		if (model.getPicNum() != null) {
			jsonObject.put("picNum", model.getPicNum());
		}
		if (model.getRecNum() != null) {
			jsonObject.put("recNum", model.getRecNum());
		}
		jsonObject.put("total", model.getTotal());
		if (model.getPageType() != null) {
			jsonObject.put("pageType", model.getPageType());
		}
		if (model.getIsCom() != null) {
			jsonObject.put("isCom", model.getIsCom());
		}
		if (model.getIsLocate() != null) {
			jsonObject.put("isLocate", model.getIsLocate());
		}
		if (model.getWcompany() != null) {
			jsonObject.put("wcompany", model.getWcompany());
		}
		if (model.getWcompanyID() != null) {
			jsonObject.put("wcompanyID", model.getWcompanyID());
		}
		if (model.getSendFlag() != null) {
			jsonObject.put("sendFlag", model.getSendFlag());
		}
		if (model.getLcontent() != null) {
			jsonObject.put("lcontent", model.getLcontent());
		}
		if (model.getLcompany() != null) {
			jsonObject.put("lcompany", model.getLcompany());
		}
		if (model.getLman() != null) {
			jsonObject.put("lman", model.getLman());
		}
		if (model.getLlen() != null) {
			jsonObject.put("llen", model.getLlen());
		}
		if (model.getLdate() != null) {
			jsonObject.put("ldate", DateUtils.getDate(model.getLdate()));
			jsonObject.put("ldate_date", DateUtils.getDate(model.getLdate()));
			jsonObject.put("ldate_datetime",
					DateUtils.getDateTime(model.getLdate()));
		}
		if (model.getJconten() != null) {
			jsonObject.put("jconten", model.getJconten());
		}
		if (model.getJplace() != null) {
			jsonObject.put("jplace", model.getJplace());
		}
		if (model.getJman() != null) {
			jsonObject.put("jman", model.getJman());
		}
		if (model.getJback() != null) {
			jsonObject.put("jback", model.getJback());
		}
		if (model.getJactor() != null) {
			jsonObject.put("jactor", model.getJactor());
		}
		if (model.getJnum() != null) {
			jsonObject.put("jnum", model.getJnum());
		}
		if (model.getJbnum() != null) {
			jsonObject.put("jbnum", model.getJbnum());
		}
		if (model.getTnum() != null) {
			jsonObject.put("tnum", model.getTnum());
		}
		if (model.getTzoom() != null) {
			jsonObject.put("tzoom", model.getTzoom());
		}
		if (model.getTflag() != null) {
			jsonObject.put("tflag", model.getTflag());
		}
		if (model.getTdesigner() != null) {
			jsonObject.put("tdesigner", model.getTdesigner());
		}
		if (model.getTviewer() != null) {
			jsonObject.put("tviewer", model.getTviewer());
		}
		if (model.getTassessor() != null) {
			jsonObject.put("tassessor", model.getTassessor());
		}
		if (model.getTvision() != null) {
			jsonObject.put("tvision", model.getTvision());
		}
		jsonObject.put("clistNo", model.getClistNo());
		if (model.getCpageNo() != null) {
			jsonObject.put("cpageNo", model.getCpageNo());
		}
		if (model.getVnum() != null) {
			jsonObject.put("vnum", model.getVnum());
		}
		if (model.getCvnum() != null) {
			jsonObject.put("cvnum", model.getCvnum());
		}
		if (model.getTreedListStr() != null) {
			jsonObject.put("treedListStr", model.getTreedListStr());
		}
		if (model.getCtimeEnd() != null) {
			jsonObject.put("ctimeEnd", DateUtils.getDate(model.getCtimeEnd()));
			jsonObject.put("ctimeEnd_date",
					DateUtils.getDate(model.getCtimeEnd()));
			jsonObject.put("ctimeEnd_datetime",
					DateUtils.getDateTime(model.getCtimeEnd()));
		}
		jsonObject.put("projIndex", model.getProjIndex());
		jsonObject.put("treeParent", model.getTreeParent());
		jsonObject.put("treeList", model.getTreeList());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(Pfile model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("efileNum", model.getEfileNum());
		if (model.getListNum() != null) {
			jsonObject.put("listNum", model.getListNum());
		}
		if (model.getListId() != null) {
			jsonObject.put("listId", model.getListId());
		}
		jsonObject.put("pid", model.getPid());
		jsonObject.put("projId", model.getProjId());
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
		if (model.getTopNodeM() != null) {
			jsonObject.put("topNodeM", model.getTopNodeM());
		}
		if (model.getVid() != null) {
			jsonObject.put("vid", model.getVid());
		}
		if (model.getOldVid() != null) {
			jsonObject.put("oldVid", model.getOldVid());
		}
		if (model.getVisFlag() != null) {
			jsonObject.put("visFlag", model.getVisFlag());
		}
		jsonObject.put("listNo", model.getListNo());
		if (model.getFilingFlag() != null) {
			jsonObject.put("filingFlag", model.getFilingFlag());
		}
		if (model.getSaveFlag() != null) {
			jsonObject.put("saveFlag", model.getSaveFlag());
		}
		if (model.getOpenFlag() != null) {
			jsonObject.put("openFlag", model.getOpenFlag());
		}
		if (model.getCheckupFlag() != null) {
			jsonObject.put("checkupFlag", model.getCheckupFlag());
		}
		if (model.getFinishFlag() != null) {
			jsonObject.put("finishFlag", model.getFinishFlag());
		}
		if (model.getFromID() != null) {
			jsonObject.put("fromID", model.getFromID());
		}
		if (model.getTname() != null) {
			jsonObject.put("tname", model.getTname());
		}
		if (model.getDuty() != null) {
			jsonObject.put("duty", model.getDuty());
		}
		if (model.getTagnum() != null) {
			jsonObject.put("tagnum", model.getTagnum());
		}
		if (model.getFileNum() != null) {
			jsonObject.put("fileNum", model.getFileNum());
		}
		if (model.getThematic() != null) {
			jsonObject.put("thematic", model.getThematic());
		}
		if (model.getCtime() != null) {
			jsonObject.put("ctime", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_date", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_datetime",
					DateUtils.getDateTime(model.getCtime()));
		}
		if (model.getPageNo() != null) {
			jsonObject.put("pageNo", model.getPageNo());
		}
		if (model.getLevel() != null) {
			jsonObject.put("Level", model.getLevel());
		}
		jsonObject.put("page", model.getPage());
		if (model.getFileType() != null) {
			jsonObject.put("fileType", model.getFileType());
		}
		if (model.getFontsNum() != null) {
			jsonObject.put("fontsNum", model.getFontsNum());
		}
		if (model.getSaveTime() != null) {
			jsonObject.put("saveTime", model.getSaveTime());
		}
		if (model.getCompany() != null) {
			jsonObject.put("company", model.getCompany());
		}
		jsonObject.put("year", model.getYear());
		if (model.getFileAtt() != null) {
			jsonObject.put("fileAtt", model.getFileAtt());
		}
		if (model.getAnnotations() != null) {
			jsonObject.put("annotations", model.getAnnotations());
		}
		if (model.getCarrierType() != null) {
			jsonObject.put("carrierType", model.getCarrierType());
		}
		if (model.getSummary() != null) {
			jsonObject.put("summary", model.getSummary());
		}
		if (model.getPtext() != null) {
			jsonObject.put("ptext", model.getPtext());
		}
		if (model.getCarrieru() != null) {
			jsonObject.put("carrieru", model.getCarrieru());
		}
		if (model.getGlideNum() != null) {
			jsonObject.put("glideNum", model.getGlideNum());
		}
		if (model.getEfile() != null) {
			jsonObject.put("efile", model.getEfile());
		}
		if (model.getFtime() != null) {
			jsonObject.put("ftime", DateUtils.getDate(model.getFtime()));
			jsonObject.put("ftime_date", DateUtils.getDate(model.getFtime()));
			jsonObject.put("ftime_datetime",
					DateUtils.getDateTime(model.getFtime()));
		}
		if (model.getTotalNum() != null) {
			jsonObject.put("totalNum", model.getTotalNum());
		}
		if (model.getInputMan() != null) {
			jsonObject.put("inputMan", model.getInputMan());
		}
		if (model.getEtime() != null) {
			jsonObject.put("etime", DateUtils.getDate(model.getEtime()));
			jsonObject.put("etime_date", DateUtils.getDate(model.getEtime()));
			jsonObject.put("etime_datetime",
					DateUtils.getDateTime(model.getEtime()));
		}
		if (model.getDotNum() != null) {
			jsonObject.put("dotNum", model.getDotNum());
		}
		if (model.getPicNum() != null) {
			jsonObject.put("picNum", model.getPicNum());
		}
		if (model.getRecNum() != null) {
			jsonObject.put("recNum", model.getRecNum());
		}
		jsonObject.put("total", model.getTotal());
		if (model.getPageType() != null) {
			jsonObject.put("pageType", model.getPageType());
		}
		if (model.getIsCom() != null) {
			jsonObject.put("isCom", model.getIsCom());
		}
		if (model.getIsLocate() != null) {
			jsonObject.put("isLocate", model.getIsLocate());
		}
		if (model.getWcompany() != null) {
			jsonObject.put("wcompany", model.getWcompany());
		}
		if (model.getWcompanyID() != null) {
			jsonObject.put("wcompanyID", model.getWcompanyID());
		}
		if (model.getSendFlag() != null) {
			jsonObject.put("sendFlag", model.getSendFlag());
		}
		if (model.getLcontent() != null) {
			jsonObject.put("lcontent", model.getLcontent());
		}
		if (model.getLcompany() != null) {
			jsonObject.put("lcompany", model.getLcompany());
		}
		if (model.getLman() != null) {
			jsonObject.put("lman", model.getLman());
		}
		if (model.getLlen() != null) {
			jsonObject.put("llen", model.getLlen());
		}
		if (model.getLdate() != null) {
			jsonObject.put("ldate", DateUtils.getDate(model.getLdate()));
			jsonObject.put("ldate_date", DateUtils.getDate(model.getLdate()));
			jsonObject.put("ldate_datetime",
					DateUtils.getDateTime(model.getLdate()));
		}
		if (model.getJconten() != null) {
			jsonObject.put("jconten", model.getJconten());
		}
		if (model.getJplace() != null) {
			jsonObject.put("jplace", model.getJplace());
		}
		if (model.getJman() != null) {
			jsonObject.put("jman", model.getJman());
		}
		if (model.getJback() != null) {
			jsonObject.put("jback", model.getJback());
		}
		if (model.getJactor() != null) {
			jsonObject.put("jactor", model.getJactor());
		}
		if (model.getJnum() != null) {
			jsonObject.put("jnum", model.getJnum());
		}
		if (model.getJbnum() != null) {
			jsonObject.put("jbnum", model.getJbnum());
		}
		if (model.getTnum() != null) {
			jsonObject.put("tnum", model.getTnum());
		}
		if (model.getTzoom() != null) {
			jsonObject.put("tzoom", model.getTzoom());
		}
		if (model.getTflag() != null) {
			jsonObject.put("tflag", model.getTflag());
		}
		if (model.getTdesigner() != null) {
			jsonObject.put("tdesigner", model.getTdesigner());
		}
		if (model.getTviewer() != null) {
			jsonObject.put("tviewer", model.getTviewer());
		}
		if (model.getTassessor() != null) {
			jsonObject.put("tassessor", model.getTassessor());
		}
		if (model.getTvision() != null) {
			jsonObject.put("tvision", model.getTvision());
		}
		jsonObject.put("clistNo", model.getClistNo());
		if (model.getCpageNo() != null) {
			jsonObject.put("cpageNo", model.getCpageNo());
		}
		if (model.getVnum() != null) {
			jsonObject.put("vnum", model.getVnum());
		}
		if (model.getCvnum() != null) {
			jsonObject.put("cvnum", model.getCvnum());
		}
		if (model.getTreedListStr() != null) {
			jsonObject.put("treedListStr", model.getTreedListStr());
		}
		if (model.getCtimeEnd() != null) {
			jsonObject.put("ctimeEnd", DateUtils.getDate(model.getCtimeEnd()));
			jsonObject.put("ctimeEnd_date",
					DateUtils.getDate(model.getCtimeEnd()));
			jsonObject.put("ctimeEnd_datetime",
					DateUtils.getDateTime(model.getCtimeEnd()));
		}
		jsonObject.put("projIndex", model.getProjIndex());
		jsonObject.put("treeParent", model.getTreeParent());
		jsonObject.put("treeList", model.getTreeList());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<Pfile> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Pfile model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<Pfile> arrayToList(JSONArray array) {
		java.util.List<Pfile> list = new java.util.ArrayList<Pfile>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Pfile model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private PfileJsonFactory() {

	}

}
