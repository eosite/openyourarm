package com.glaf.isdp.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.FilterDescriptor;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;

/**
 * 
 * 实体数据工厂类
 *
 */
public class PfileDomainFactory {

	public static final String TABLENAME = "PFILE";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID");
		columnMap.put("efileNum", "EFILENUM");
		columnMap.put("listNum", "LISTNUM");
		columnMap.put("listId", "LIST_ID");
		columnMap.put("pid", "PID");
		columnMap.put("projId", "PROJID");
		columnMap.put("dwid", "DWID");
		columnMap.put("fbid", "FBID");
		columnMap.put("fxid", "FXID");
		columnMap.put("jid", "JID");
		columnMap.put("flid", "FLID");
		columnMap.put("topNode", "TOPNODE");
		columnMap.put("topNodeM", "TOPNODEM");
		columnMap.put("vid", "VID");
		columnMap.put("oldVid", "OLDVID");
		columnMap.put("visFlag", "VISFLAG");
		columnMap.put("listNo", "LISTNO");
		columnMap.put("filingFlag", "FILINGFLAG");
		columnMap.put("saveFlag", "SAVEFLAG");
		columnMap.put("openFlag", "OPENFLAG");
		columnMap.put("checkupFlag", "CHECKUPFLAG");
		columnMap.put("finishFlag", "FINISHFLAG");
		columnMap.put("fromID", "FROMID");
		columnMap.put("tname", "TNAME");
		columnMap.put("duty", "DUTY");
		columnMap.put("tagnum", "TAGNUM");
		columnMap.put("fileNum", "FILENUM");
		columnMap.put("thematic", "THEMATIC");
		columnMap.put("ctime", "CTIME");
		columnMap.put("pageNo", "PAGENO");
		columnMap.put("Level", "SLEVEL");
		columnMap.put("page", "PAGE");
		columnMap.put("fileType", "FILETYPE");
		columnMap.put("fontsNum", "FONTSNUM");
		columnMap.put("saveTime", "SAVETIME");
		columnMap.put("company", "COMPANY");
		columnMap.put("year", "YEAR");
		columnMap.put("fileAtt", "FILEATT");
		columnMap.put("annotations", "ANNOTATIONS");
		columnMap.put("carrierType", "CARRIERTYPE");
		columnMap.put("summary", "SUMMARY");
		columnMap.put("ptext", "PTEXT");
		columnMap.put("carrieru", "CARRIERU");
		columnMap.put("glideNum", "GLIDENUM");
		columnMap.put("efile", "EFILE");
		columnMap.put("ftime", "FTIME");
		columnMap.put("totalNum", "TOTALNUM");
		columnMap.put("inputMan", "INPUTMAN");
		columnMap.put("etime", "ETIME");
		columnMap.put("dotNum", "DOTNUM");
		columnMap.put("picNum", "PICNUM");
		columnMap.put("recNum", "RECNUM");
		columnMap.put("total", "TOTAL");
		columnMap.put("pageType", "PAGETYPE");
		columnMap.put("isCom", "ISCOM");
		columnMap.put("isLocate", "ISLOCATE");
		columnMap.put("wcompany", "WCOMPANY");
		columnMap.put("wcompanyID", "WCOMPANYID");
		columnMap.put("sendFlag", "SENDFLAG");
		columnMap.put("lcontent", "LCONTENT");
		columnMap.put("lcompany", "LCOMPANY");
		columnMap.put("lman", "LMAN");
		columnMap.put("llen", "LLEN");
		columnMap.put("ldate", "LDATE");
		columnMap.put("jconten", "JCONTEN");
		columnMap.put("jplace", "JPLACE");
		columnMap.put("jman", "JMAN");
		columnMap.put("jback", "JBACK");
		columnMap.put("jactor", "JACTOR");
		columnMap.put("jnum", "JNUM");
		columnMap.put("jbnum", "JBNUM");
		columnMap.put("tnum", "TNUM");
		columnMap.put("tzoom", "TZOOM");
		columnMap.put("tflag", "TFLAG");
		columnMap.put("tdesigner", "TDESIGNER");
		columnMap.put("tviewer", "TVIEWER");
		columnMap.put("tassessor", "TASSESSOR");
		columnMap.put("tvision", "TVISION");
		columnMap.put("clistNo", "CLISTNO");
		columnMap.put("cpageNo", "CPAGENO");
		columnMap.put("vnum", "VNUM");
		columnMap.put("cvnum", "CVNUM");
		columnMap.put("treedListStr", "TREE_DLISTSTR");
		columnMap.put("ctimeEnd", "CTIME_END");
		columnMap.put("projIndex", "PROJ_INDEX");
		columnMap.put("treeParent", "TREE_PARENT");
		columnMap.put("treeList", "TREE_LIST");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("efileNum", "Integer");
		javaTypeMap.put("listNum", "String");
		javaTypeMap.put("listId", "String");
		javaTypeMap.put("pid", "Integer");
		javaTypeMap.put("projId", "Integer");
		javaTypeMap.put("dwid", "Integer");
		javaTypeMap.put("fbid", "Integer");
		javaTypeMap.put("fxid", "Integer");
		javaTypeMap.put("jid", "String");
		javaTypeMap.put("flid", "String");
		javaTypeMap.put("topNode", "String");
		javaTypeMap.put("topNodeM", "String");
		javaTypeMap.put("vid", "String");
		javaTypeMap.put("oldVid", "String");
		javaTypeMap.put("visFlag", "String");
		javaTypeMap.put("listNo", "Integer");
		javaTypeMap.put("filingFlag", "String");
		javaTypeMap.put("saveFlag", "String");
		javaTypeMap.put("openFlag", "String");
		javaTypeMap.put("checkupFlag", "String");
		javaTypeMap.put("finishFlag", "String");
		javaTypeMap.put("fromID", "String");
		javaTypeMap.put("tname", "String");
		javaTypeMap.put("duty", "String");
		javaTypeMap.put("tagnum", "String");
		javaTypeMap.put("fileNum", "String");
		javaTypeMap.put("thematic", "String");
		javaTypeMap.put("ctime", "Date");
		javaTypeMap.put("pageNo", "String");
		javaTypeMap.put("Level", "String");
		javaTypeMap.put("page", "Integer");
		javaTypeMap.put("fileType", "String");
		javaTypeMap.put("fontsNum", "String");
		javaTypeMap.put("saveTime", "String");
		javaTypeMap.put("company", "String");
		javaTypeMap.put("year", "Integer");
		javaTypeMap.put("fileAtt", "String");
		javaTypeMap.put("annotations", "String");
		javaTypeMap.put("carrierType", "String");
		javaTypeMap.put("summary", "String");
		javaTypeMap.put("ptext", "String");
		javaTypeMap.put("carrieru", "String");
		javaTypeMap.put("glideNum", "String");
		javaTypeMap.put("efile", "String");
		javaTypeMap.put("ftime", "Date");
		javaTypeMap.put("totalNum", "String");
		javaTypeMap.put("inputMan", "String");
		javaTypeMap.put("etime", "Date");
		javaTypeMap.put("dotNum", "String");
		javaTypeMap.put("picNum", "String");
		javaTypeMap.put("recNum", "String");
		javaTypeMap.put("total", "Integer");
		javaTypeMap.put("pageType", "String");
		javaTypeMap.put("isCom", "String");
		javaTypeMap.put("isLocate", "String");
		javaTypeMap.put("wcompany", "String");
		javaTypeMap.put("wcompanyID", "String");
		javaTypeMap.put("sendFlag", "String");
		javaTypeMap.put("lcontent", "String");
		javaTypeMap.put("lcompany", "String");
		javaTypeMap.put("lman", "String");
		javaTypeMap.put("llen", "String");
		javaTypeMap.put("ldate", "Date");
		javaTypeMap.put("jconten", "String");
		javaTypeMap.put("jplace", "String");
		javaTypeMap.put("jman", "String");
		javaTypeMap.put("jback", "String");
		javaTypeMap.put("jactor", "String");
		javaTypeMap.put("jnum", "String");
		javaTypeMap.put("jbnum", "String");
		javaTypeMap.put("tnum", "String");
		javaTypeMap.put("tzoom", "String");
		javaTypeMap.put("tflag", "String");
		javaTypeMap.put("tdesigner", "String");
		javaTypeMap.put("tviewer", "String");
		javaTypeMap.put("tassessor", "String");
		javaTypeMap.put("tvision", "String");
		javaTypeMap.put("clistNo", "Integer");
		javaTypeMap.put("cpageNo", "String");
		javaTypeMap.put("vnum", "String");
		javaTypeMap.put("cvnum", "String");
		javaTypeMap.put("treedListStr", "String");
		javaTypeMap.put("ctimeEnd", "Date");
		javaTypeMap.put("projIndex", "Integer");
		javaTypeMap.put("treeParent", "Integer");
		javaTypeMap.put("treeList", "Integer");
	}

	public static Map<String, String> getColumnMap() {
		return columnMap;
	}

	public static Map<String, String> getJavaTypeMap() {
		return javaTypeMap;
	}

	public static TableDefinition getTableDefinition() {
		return getTableDefinition(TABLENAME);
	}

	public static TableDefinition getTableDefinition(String tableName) {
		tableName = tableName.toUpperCase();
		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition.setTableName(tableName);
		tableDefinition.setName("Pfile");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition efileNum = new ColumnDefinition();
		efileNum.setName("efileNum");
		efileNum.setColumnName("EFILENUM");
		efileNum.setJavaType("Integer");
		tableDefinition.addColumn(efileNum);

		ColumnDefinition listNum = new ColumnDefinition();
		listNum.setName("listNum");
		listNum.setColumnName("LISTNUM");
		listNum.setJavaType("String");
		listNum.setLength(100);
		tableDefinition.addColumn(listNum);

		ColumnDefinition listId = new ColumnDefinition();
		listId.setName("listId");
		listId.setColumnName("LIST_ID");
		listId.setJavaType("String");
		listId.setLength(19);
		tableDefinition.addColumn(listId);

		ColumnDefinition pid = new ColumnDefinition();
		pid.setName("pid");
		pid.setColumnName("PID");
		pid.setJavaType("Integer");
		tableDefinition.addColumn(pid);

		ColumnDefinition projId = new ColumnDefinition();
		projId.setName("projId");
		projId.setColumnName("PROJID");
		projId.setJavaType("Integer");
		tableDefinition.addColumn(projId);

		ColumnDefinition dwid = new ColumnDefinition();
		dwid.setName("dwid");
		dwid.setColumnName("DWID");
		dwid.setJavaType("Integer");
		tableDefinition.addColumn(dwid);

		ColumnDefinition fbid = new ColumnDefinition();
		fbid.setName("fbid");
		fbid.setColumnName("FBID");
		fbid.setJavaType("Integer");
		tableDefinition.addColumn(fbid);

		ColumnDefinition fxid = new ColumnDefinition();
		fxid.setName("fxid");
		fxid.setColumnName("FXID");
		fxid.setJavaType("Integer");
		tableDefinition.addColumn(fxid);

		ColumnDefinition jid = new ColumnDefinition();
		jid.setName("jid");
		jid.setColumnName("JID");
		jid.setJavaType("String");
		jid.setLength(50);
		tableDefinition.addColumn(jid);

		ColumnDefinition flid = new ColumnDefinition();
		flid.setName("flid");
		flid.setColumnName("FLID");
		flid.setJavaType("String");
		flid.setLength(50);
		tableDefinition.addColumn(flid);

		ColumnDefinition topNode = new ColumnDefinition();
		topNode.setName("topNode");
		topNode.setColumnName("TOPNODE");
		topNode.setJavaType("String");
		topNode.setLength(150);
		tableDefinition.addColumn(topNode);

		ColumnDefinition topNodeM = new ColumnDefinition();
		topNodeM.setName("topNodeM");
		topNodeM.setColumnName("TOPNODEM");
		topNodeM.setJavaType("String");
		topNodeM.setLength(150);
		tableDefinition.addColumn(topNodeM);

		ColumnDefinition vid = new ColumnDefinition();
		vid.setName("vid");
		vid.setColumnName("VID");
		vid.setJavaType("String");
		vid.setLength(50);
		tableDefinition.addColumn(vid);

		ColumnDefinition oldVid = new ColumnDefinition();
		oldVid.setName("oldVid");
		oldVid.setColumnName("OLDVID");
		oldVid.setJavaType("String");
		oldVid.setLength(50);
		tableDefinition.addColumn(oldVid);

		ColumnDefinition visFlag = new ColumnDefinition();
		visFlag.setName("visFlag");
		visFlag.setColumnName("VISFLAG");
		visFlag.setJavaType("String");
		visFlag.setLength(1);
		tableDefinition.addColumn(visFlag);

		ColumnDefinition listNo = new ColumnDefinition();
		listNo.setName("listNo");
		listNo.setColumnName("LISTNO");
		listNo.setJavaType("Integer");
		tableDefinition.addColumn(listNo);

		ColumnDefinition filingFlag = new ColumnDefinition();
		filingFlag.setName("filingFlag");
		filingFlag.setColumnName("FILINGFLAG");
		filingFlag.setJavaType("String");
		filingFlag.setLength(1);
		tableDefinition.addColumn(filingFlag);

		ColumnDefinition saveFlag = new ColumnDefinition();
		saveFlag.setName("saveFlag");
		saveFlag.setColumnName("SAVEFLAG");
		saveFlag.setJavaType("String");
		saveFlag.setLength(1);
		tableDefinition.addColumn(saveFlag);

		ColumnDefinition openFlag = new ColumnDefinition();
		openFlag.setName("openFlag");
		openFlag.setColumnName("OPENFLAG");
		openFlag.setJavaType("String");
		openFlag.setLength(1);
		tableDefinition.addColumn(openFlag);

		ColumnDefinition checkupFlag = new ColumnDefinition();
		checkupFlag.setName("checkupFlag");
		checkupFlag.setColumnName("CHECKUPFLAG");
		checkupFlag.setJavaType("String");
		checkupFlag.setLength(1);
		tableDefinition.addColumn(checkupFlag);

		ColumnDefinition finishFlag = new ColumnDefinition();
		finishFlag.setName("finishFlag");
		finishFlag.setColumnName("FINISHFLAG");
		finishFlag.setJavaType("String");
		finishFlag.setLength(1);
		tableDefinition.addColumn(finishFlag);

		ColumnDefinition fromID = new ColumnDefinition();
		fromID.setName("fromID");
		fromID.setColumnName("FROMID");
		fromID.setJavaType("String");
		fromID.setLength(50);
		tableDefinition.addColumn(fromID);

		ColumnDefinition tname = new ColumnDefinition();
		tname.setName("tname");
		tname.setColumnName("TNAME");
		tname.setJavaType("String");
		tname.setLength(250);
		tableDefinition.addColumn(tname);

		ColumnDefinition duty = new ColumnDefinition();
		duty.setName("duty");
		duty.setColumnName("DUTY");
		duty.setJavaType("String");
		duty.setLength(100);
		tableDefinition.addColumn(duty);

		ColumnDefinition tagnum = new ColumnDefinition();
		tagnum.setName("tagnum");
		tagnum.setColumnName("TAGNUM");
		tagnum.setJavaType("String");
		tagnum.setLength(50);
		tableDefinition.addColumn(tagnum);

		ColumnDefinition fileNum = new ColumnDefinition();
		fileNum.setName("fileNum");
		fileNum.setColumnName("FILENUM");
		fileNum.setJavaType("String");
		fileNum.setLength(50);
		tableDefinition.addColumn(fileNum);

		ColumnDefinition thematic = new ColumnDefinition();
		thematic.setName("thematic");
		thematic.setColumnName("THEMATIC");
		thematic.setJavaType("String");
		thematic.setLength(100);
		tableDefinition.addColumn(thematic);

		ColumnDefinition ctime = new ColumnDefinition();
		ctime.setName("ctime");
		ctime.setColumnName("CTIME");
		ctime.setJavaType("Date");
		tableDefinition.addColumn(ctime);

		ColumnDefinition pageNo = new ColumnDefinition();
		pageNo.setName("pageNo");
		pageNo.setColumnName("PAGENO");
		pageNo.setJavaType("String");
		pageNo.setLength(20);
		tableDefinition.addColumn(pageNo);

		ColumnDefinition Level = new ColumnDefinition();
		Level.setName("Level");
		Level.setColumnName("SLEVEL");
		Level.setJavaType("String");
		Level.setLength(30);
		tableDefinition.addColumn(Level);

		ColumnDefinition page = new ColumnDefinition();
		page.setName("page");
		page.setColumnName("PAGE");
		page.setJavaType("Integer");
		tableDefinition.addColumn(page);

		ColumnDefinition fileType = new ColumnDefinition();
		fileType.setName("fileType");
		fileType.setColumnName("FILETYPE");
		fileType.setJavaType("String");
		fileType.setLength(50);
		tableDefinition.addColumn(fileType);

		ColumnDefinition fontsNum = new ColumnDefinition();
		fontsNum.setName("fontsNum");
		fontsNum.setColumnName("FONTSNUM");
		fontsNum.setJavaType("String");
		fontsNum.setLength(30);
		tableDefinition.addColumn(fontsNum);

		ColumnDefinition saveTime = new ColumnDefinition();
		saveTime.setName("saveTime");
		saveTime.setColumnName("SAVETIME");
		saveTime.setJavaType("String");
		saveTime.setLength(10);
		tableDefinition.addColumn(saveTime);

		ColumnDefinition company = new ColumnDefinition();
		company.setName("company");
		company.setColumnName("COMPANY");
		company.setJavaType("String");
		company.setLength(250);
		tableDefinition.addColumn(company);

		ColumnDefinition year = new ColumnDefinition();
		year.setName("year");
		year.setColumnName("YEAR");
		year.setJavaType("Integer");
		tableDefinition.addColumn(year);

		ColumnDefinition fileAtt = new ColumnDefinition();
		fileAtt.setName("fileAtt");
		fileAtt.setColumnName("FILEATT");
		fileAtt.setJavaType("String");
		fileAtt.setLength(50);
		tableDefinition.addColumn(fileAtt);

		ColumnDefinition annotations = new ColumnDefinition();
		annotations.setName("annotations");
		annotations.setColumnName("ANNOTATIONS");
		annotations.setJavaType("String");
		annotations.setLength(100);
		tableDefinition.addColumn(annotations);

		ColumnDefinition carrierType = new ColumnDefinition();
		carrierType.setName("carrierType");
		carrierType.setColumnName("CARRIERTYPE");
		carrierType.setJavaType("String");
		carrierType.setLength(50);
		tableDefinition.addColumn(carrierType);

		ColumnDefinition summary = new ColumnDefinition();
		summary.setName("summary");
		summary.setColumnName("SUMMARY");
		summary.setJavaType("String");
		summary.setLength(200);
		tableDefinition.addColumn(summary);

		ColumnDefinition ptext = new ColumnDefinition();
		ptext.setName("ptext");
		ptext.setColumnName("PTEXT");
		ptext.setJavaType("String");
		ptext.setLength(30);
		tableDefinition.addColumn(ptext);

		ColumnDefinition carrieru = new ColumnDefinition();
		carrieru.setName("carrieru");
		carrieru.setColumnName("CARRIERU");
		carrieru.setJavaType("String");
		carrieru.setLength(50);
		tableDefinition.addColumn(carrieru);

		ColumnDefinition glideNum = new ColumnDefinition();
		glideNum.setName("glideNum");
		glideNum.setColumnName("GLIDENUM");
		glideNum.setJavaType("String");
		glideNum.setLength(50);
		tableDefinition.addColumn(glideNum);

		ColumnDefinition efile = new ColumnDefinition();
		efile.setName("efile");
		efile.setColumnName("EFILE");
		efile.setJavaType("String");
		efile.setLength(50);
		tableDefinition.addColumn(efile);

		ColumnDefinition ftime = new ColumnDefinition();
		ftime.setName("ftime");
		ftime.setColumnName("FTIME");
		ftime.setJavaType("Date");
		tableDefinition.addColumn(ftime);

		ColumnDefinition totalNum = new ColumnDefinition();
		totalNum.setName("totalNum");
		totalNum.setColumnName("TOTALNUM");
		totalNum.setJavaType("String");
		totalNum.setLength(50);
		tableDefinition.addColumn(totalNum);

		ColumnDefinition inputMan = new ColumnDefinition();
		inputMan.setName("inputMan");
		inputMan.setColumnName("INPUTMAN");
		inputMan.setJavaType("String");
		inputMan.setLength(20);
		tableDefinition.addColumn(inputMan);

		ColumnDefinition etime = new ColumnDefinition();
		etime.setName("etime");
		etime.setColumnName("ETIME");
		etime.setJavaType("Date");
		tableDefinition.addColumn(etime);

		ColumnDefinition dotNum = new ColumnDefinition();
		dotNum.setName("dotNum");
		dotNum.setColumnName("DOTNUM");
		dotNum.setJavaType("String");
		dotNum.setLength(20);
		tableDefinition.addColumn(dotNum);

		ColumnDefinition picNum = new ColumnDefinition();
		picNum.setName("picNum");
		picNum.setColumnName("PICNUM");
		picNum.setJavaType("String");
		picNum.setLength(30);
		tableDefinition.addColumn(picNum);

		ColumnDefinition recNum = new ColumnDefinition();
		recNum.setName("recNum");
		recNum.setColumnName("RECNUM");
		recNum.setJavaType("String");
		recNum.setLength(20);
		tableDefinition.addColumn(recNum);

		ColumnDefinition total = new ColumnDefinition();
		total.setName("total");
		total.setColumnName("TOTAL");
		total.setJavaType("Integer");
		tableDefinition.addColumn(total);

		ColumnDefinition pageType = new ColumnDefinition();
		pageType.setName("pageType");
		pageType.setColumnName("PAGETYPE");
		pageType.setJavaType("String");
		pageType.setLength(100);
		tableDefinition.addColumn(pageType);

		ColumnDefinition isCom = new ColumnDefinition();
		isCom.setName("isCom");
		isCom.setColumnName("ISCOM");
		isCom.setJavaType("String");
		isCom.setLength(1);
		tableDefinition.addColumn(isCom);

		ColumnDefinition isLocate = new ColumnDefinition();
		isLocate.setName("isLocate");
		isLocate.setColumnName("ISLOCATE");
		isLocate.setJavaType("String");
		isLocate.setLength(1);
		tableDefinition.addColumn(isLocate);

		ColumnDefinition wcompany = new ColumnDefinition();
		wcompany.setName("wcompany");
		wcompany.setColumnName("WCOMPANY");
		wcompany.setJavaType("String");
		wcompany.setLength(200);
		tableDefinition.addColumn(wcompany);

		ColumnDefinition wcompanyID = new ColumnDefinition();
		wcompanyID.setName("wcompanyID");
		wcompanyID.setColumnName("WCOMPANYID");
		wcompanyID.setJavaType("String");
		wcompanyID.setLength(50);
		tableDefinition.addColumn(wcompanyID);

		ColumnDefinition sendFlag = new ColumnDefinition();
		sendFlag.setName("sendFlag");
		sendFlag.setColumnName("SENDFLAG");
		sendFlag.setJavaType("String");
		sendFlag.setLength(1);
		tableDefinition.addColumn(sendFlag);

		ColumnDefinition lcontent = new ColumnDefinition();
		lcontent.setName("lcontent");
		lcontent.setColumnName("LCONTENT");
		lcontent.setJavaType("String");
		lcontent.setLength(100);
		tableDefinition.addColumn(lcontent);

		ColumnDefinition lcompany = new ColumnDefinition();
		lcompany.setName("lcompany");
		lcompany.setColumnName("LCOMPANY");
		lcompany.setJavaType("String");
		lcompany.setLength(250);
		tableDefinition.addColumn(lcompany);

		ColumnDefinition lman = new ColumnDefinition();
		lman.setName("lman");
		lman.setColumnName("LMAN");
		lman.setJavaType("String");
		lman.setLength(50);
		tableDefinition.addColumn(lman);

		ColumnDefinition llen = new ColumnDefinition();
		llen.setName("llen");
		llen.setColumnName("LLEN");
		llen.setJavaType("String");
		llen.setLength(50);
		tableDefinition.addColumn(llen);

		ColumnDefinition ldate = new ColumnDefinition();
		ldate.setName("ldate");
		ldate.setColumnName("LDATE");
		ldate.setJavaType("Date");
		tableDefinition.addColumn(ldate);

		ColumnDefinition jconten = new ColumnDefinition();
		jconten.setName("jconten");
		jconten.setColumnName("JCONTEN");
		jconten.setJavaType("String");
		jconten.setLength(100);
		tableDefinition.addColumn(jconten);

		ColumnDefinition jplace = new ColumnDefinition();
		jplace.setName("jplace");
		jplace.setColumnName("JPLACE");
		jplace.setJavaType("String");
		jplace.setLength(100);
		tableDefinition.addColumn(jplace);

		ColumnDefinition jman = new ColumnDefinition();
		jman.setName("jman");
		jman.setColumnName("JMAN");
		jman.setJavaType("String");
		jman.setLength(50);
		tableDefinition.addColumn(jman);

		ColumnDefinition jback = new ColumnDefinition();
		jback.setName("jback");
		jback.setColumnName("JBACK");
		jback.setJavaType("String");
		jback.setLength(50);
		tableDefinition.addColumn(jback);

		ColumnDefinition jactor = new ColumnDefinition();
		jactor.setName("jactor");
		jactor.setColumnName("JACTOR");
		jactor.setJavaType("String");
		jactor.setLength(50);
		tableDefinition.addColumn(jactor);

		ColumnDefinition jnum = new ColumnDefinition();
		jnum.setName("jnum");
		jnum.setColumnName("JNUM");
		jnum.setJavaType("String");
		jnum.setLength(30);
		tableDefinition.addColumn(jnum);

		ColumnDefinition jbnum = new ColumnDefinition();
		jbnum.setName("jbnum");
		jbnum.setColumnName("JBNUM");
		jbnum.setJavaType("String");
		jbnum.setLength(30);
		tableDefinition.addColumn(jbnum);

		ColumnDefinition tnum = new ColumnDefinition();
		tnum.setName("tnum");
		tnum.setColumnName("TNUM");
		tnum.setJavaType("String");
		tnum.setLength(50);
		tableDefinition.addColumn(tnum);

		ColumnDefinition tzoom = new ColumnDefinition();
		tzoom.setName("tzoom");
		tzoom.setColumnName("TZOOM");
		tzoom.setJavaType("String");
		tzoom.setLength(20);
		tableDefinition.addColumn(tzoom);

		ColumnDefinition tflag = new ColumnDefinition();
		tflag.setName("tflag");
		tflag.setColumnName("TFLAG");
		tflag.setJavaType("String");
		tflag.setLength(30);
		tableDefinition.addColumn(tflag);

		ColumnDefinition tdesigner = new ColumnDefinition();
		tdesigner.setName("tdesigner");
		tdesigner.setColumnName("TDESIGNER");
		tdesigner.setJavaType("String");
		tdesigner.setLength(20);
		tableDefinition.addColumn(tdesigner);

		ColumnDefinition tviewer = new ColumnDefinition();
		tviewer.setName("tviewer");
		tviewer.setColumnName("TVIEWER");
		tviewer.setJavaType("String");
		tviewer.setLength(200);
		tableDefinition.addColumn(tviewer);

		ColumnDefinition tassessor = new ColumnDefinition();
		tassessor.setName("tassessor");
		tassessor.setColumnName("TASSESSOR");
		tassessor.setJavaType("String");
		tassessor.setLength(20);
		tableDefinition.addColumn(tassessor);

		ColumnDefinition tvision = new ColumnDefinition();
		tvision.setName("tvision");
		tvision.setColumnName("TVISION");
		tvision.setJavaType("String");
		tvision.setLength(20);
		tableDefinition.addColumn(tvision);

		ColumnDefinition clistNo = new ColumnDefinition();
		clistNo.setName("clistNo");
		clistNo.setColumnName("CLISTNO");
		clistNo.setJavaType("Integer");
		tableDefinition.addColumn(clistNo);

		ColumnDefinition cpageNo = new ColumnDefinition();
		cpageNo.setName("cpageNo");
		cpageNo.setColumnName("CPAGENO");
		cpageNo.setJavaType("String");
		cpageNo.setLength(20);
		tableDefinition.addColumn(cpageNo);

		ColumnDefinition vnum = new ColumnDefinition();
		vnum.setName("vnum");
		vnum.setColumnName("VNUM");
		vnum.setJavaType("String");
		vnum.setLength(200);
		tableDefinition.addColumn(vnum);

		ColumnDefinition cvnum = new ColumnDefinition();
		cvnum.setName("cvnum");
		cvnum.setColumnName("CVNUM");
		cvnum.setJavaType("String");
		cvnum.setLength(200);
		tableDefinition.addColumn(cvnum);

		ColumnDefinition treedListStr = new ColumnDefinition();
		treedListStr.setName("treedListStr");
		treedListStr.setColumnName("TREE_DLISTSTR");
		treedListStr.setJavaType("String");
		treedListStr.setLength(50);
		tableDefinition.addColumn(treedListStr);

		ColumnDefinition ctimeEnd = new ColumnDefinition();
		ctimeEnd.setName("ctimeEnd");
		ctimeEnd.setColumnName("CTIME_END");
		ctimeEnd.setJavaType("Date");
		tableDefinition.addColumn(ctimeEnd);

		ColumnDefinition projIndex = new ColumnDefinition();
		projIndex.setName("projIndex");
		projIndex.setColumnName("PROJ_INDEX");
		projIndex.setJavaType("Integer");
		tableDefinition.addColumn(projIndex);

		ColumnDefinition treeParent = new ColumnDefinition();
		treeParent.setName("treeParent");
		treeParent.setColumnName("TREE_PARENT");
		treeParent.setJavaType("Integer");
		tableDefinition.addColumn(treeParent);

		ColumnDefinition treeList = new ColumnDefinition();
		treeList.setName("treeList");
		treeList.setColumnName("TREE_LIST");
		treeList.setJavaType("Integer");
		tableDefinition.addColumn(treeList);

		return tableDefinition;
	}

	public static TableDefinition createTable() {
		TableDefinition tableDefinition = getTableDefinition(TABLENAME);
		if (!DBUtils.tableExists(TABLENAME)) {
			DBUtils.createTable(tableDefinition);
		} else {
			DBUtils.alterTable(tableDefinition);
		}
		return tableDefinition;
	}

	public static TableDefinition createTable(String tableName) {
		TableDefinition tableDefinition = getTableDefinition(tableName);
		if (!DBUtils.tableExists(tableName)) {
			DBUtils.createTable(tableDefinition);
		} else {
			DBUtils.alterTable(tableDefinition);
		}
		return tableDefinition;
	}

	public static void processDataRequest(DataRequest dataRequest) {
		if (dataRequest.getFilter() != null) {
			if (dataRequest.getFilter().getField() != null) {
				dataRequest.getFilter().setColumn(
						columnMap.get(dataRequest.getFilter().getField()));
				dataRequest.getFilter().setJavaType(
						javaTypeMap.get(dataRequest.getFilter().getField()));
			}

			List<FilterDescriptor> filters = dataRequest.getFilter()
					.getFilters();
			for (FilterDescriptor filter : filters) {
				filter.setParent(dataRequest.getFilter());
				if (filter.getField() != null) {
					filter.setColumn(columnMap.get(filter.getField()));
					filter.setJavaType(javaTypeMap.get(filter.getField()));
				}

				List<FilterDescriptor> subFilters = filter.getFilters();
				for (FilterDescriptor f : subFilters) {
					f.setColumn(columnMap.get(f.getField()));
					f.setJavaType(javaTypeMap.get(f.getField()));
					f.setParent(filter);
				}
			}
		}
	}

	private PfileDomainFactory() {

	}

}
