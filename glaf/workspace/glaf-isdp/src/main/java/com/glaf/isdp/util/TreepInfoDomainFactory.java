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
public class TreepInfoDomainFactory {

	public static final String TABLENAME = "TREEPINFO";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("indexId", "INDEX_ID");
		columnMap.put("id", "ID");
		columnMap.put("num", "NUM");
		columnMap.put("topId", "TOP_ID");
		columnMap.put("parentId", "PARENT_ID");
		columnMap.put("indexName", "INDEX_NAME");
		columnMap.put("Level", "NLEVEL");
		columnMap.put("nodeType", "NODETYPE");
		columnMap.put("fromId", "FROMID");
		columnMap.put("partid", "PART_ID");
		columnMap.put("showId", "SHOWID");
		columnMap.put("sindexName", "SINDEX_NAME");
		columnMap.put("fileNum", "FILENUM");
		columnMap.put("fileNum0", "FILENUM0");
		columnMap.put("fileNum1", "FILENUM1");
		columnMap.put("filenum2", "FILENUM2");
		columnMap.put("projType", "PROJTYPE");
		columnMap.put("cid", "CID");
		columnMap.put("dwid", "DWID");
		columnMap.put("fxid", "FXID");
		columnMap.put("fbid", "FBID");
		columnMap.put("jid", "JID");
		columnMap.put("flid", "FLID");
		columnMap.put("topNode", "TOPNODE");
		columnMap.put("nodeIco", "NODEICO");
		columnMap.put("outFlag", "OUTFLAG");
		columnMap.put("inFlag", "INFLAG");
		columnMap.put("password", "PASSWORD");
		columnMap.put("listNum", "LISTNUM");
		columnMap.put("wcompany", "WCOMPANY");
		columnMap.put("listNo", "LISTNO");
		columnMap.put("userNmu", "USERNMU");
		columnMap.put("indexIdOld", "INDEX_ID_OLD");
		columnMap.put("pindexId", "PINDEX_ID");
		columnMap.put("finishInt", "FINISHINT");
		columnMap.put("typeTableName", "TYPE_TABLENAME");
		columnMap.put("treedListStr", "TREE_DLISTSTR");
		columnMap.put("pfilesIndex", "PFILES_INDEX");
		columnMap.put("bdate", "BDATE");
		columnMap.put("edate", "EDATE");
		columnMap.put("wbsIndex", "WBSINDEX");
		columnMap.put("bdates", "BDATE_S");
		columnMap.put("edates", "EDATE_S");
		columnMap.put("typeId", "TYPE_ID");
		columnMap.put("cell1", "CELL1");
		columnMap.put("cell2", "CELL2");
		columnMap.put("cell3", "CELL3");
		columnMap.put("strFileLocate", "STRFILELOCATE");
		columnMap.put("intpFile1", "INTPFILE1");
		columnMap.put("intpFile2", "INTPFILE2");
		columnMap.put("intpFile3", "INTPFILE3");
		columnMap.put("intCellFinish", "INTCELLFINISH");
		columnMap.put("sysId", "SYS_ID");
		columnMap.put("indexIn", "INDEX_IN");
		columnMap.put("strButtonStar", "STRBUTTONSTAR");
		columnMap.put("intIsUse", "INTISUSE");
		columnMap.put("wbsIndexIn", "WBSINDEX_IN");
		columnMap.put("uindexId", "UINDEX_ID");
		columnMap.put("lisnoWBS", "LISNO_WBS");
		columnMap.put("sysIdAdd", "SYS_ID_ADD");
		columnMap.put("indexInAdd", "INDEX_IN_ADD");

		javaTypeMap.put("indexId", "Integer");
		javaTypeMap.put("id", "String");
		javaTypeMap.put("num", "String");
		javaTypeMap.put("topId", "Integer");
		javaTypeMap.put("parentId", "Integer");
		javaTypeMap.put("indexName", "String");
		javaTypeMap.put("Level", "Integer");
		javaTypeMap.put("nodeType", "String");
		javaTypeMap.put("fromId", "Integer");
		javaTypeMap.put("partid", "Integer");
		javaTypeMap.put("showId", "Integer");
		javaTypeMap.put("sindexName", "String");
		javaTypeMap.put("fileNum", "Integer");
		javaTypeMap.put("fileNum0", "Integer");
		javaTypeMap.put("fileNum1", "Integer");
		javaTypeMap.put("filenum2", "Integer");
		javaTypeMap.put("projType", "String");
		javaTypeMap.put("cid", "String");
		javaTypeMap.put("dwid", "Integer");
		javaTypeMap.put("fxid", "Integer");
		javaTypeMap.put("fbid", "Integer");
		javaTypeMap.put("jid", "String");
		javaTypeMap.put("flid", "String");
		javaTypeMap.put("topNode", "String");
		javaTypeMap.put("nodeIco", "Integer");
		javaTypeMap.put("outFlag", "String");
		javaTypeMap.put("inFlag", "String");
		javaTypeMap.put("password", "String");
		javaTypeMap.put("listNum", "String");
		javaTypeMap.put("wcompany", "String");
		javaTypeMap.put("listNo", "Integer");
		javaTypeMap.put("userNmu", "String");
		javaTypeMap.put("indexIdOld", "Integer");
		javaTypeMap.put("pindexId", "Integer");
		javaTypeMap.put("finishInt", "Integer");
		javaTypeMap.put("typeTableName", "String");
		javaTypeMap.put("treedListStr", "String");
		javaTypeMap.put("pfilesIndex", "Integer");
		javaTypeMap.put("bdate", "Date");
		javaTypeMap.put("edate", "Date");
		javaTypeMap.put("wbsIndex", "Integer");
		javaTypeMap.put("bdates", "Date");
		javaTypeMap.put("edates", "Date");
		javaTypeMap.put("typeId", "String");
		javaTypeMap.put("cell1", "Integer");
		javaTypeMap.put("cell2", "Integer");
		javaTypeMap.put("cell3", "Integer");
		javaTypeMap.put("strFileLocate", "String");
		javaTypeMap.put("intpFile1", "Integer");
		javaTypeMap.put("intpFile2", "Integer");
		javaTypeMap.put("intpFile3", "Integer");
		javaTypeMap.put("intCellFinish", "Integer");
		javaTypeMap.put("sysId", "String");
		javaTypeMap.put("indexIn", "Integer");
		javaTypeMap.put("strButtonStar", "String");
		javaTypeMap.put("intIsUse", "Integer");
		javaTypeMap.put("wbsIndexIn", "Integer");
		javaTypeMap.put("uindexId", "Integer");
		javaTypeMap.put("lisnoWBS", "Integer");
		javaTypeMap.put("sysIdAdd", "String");
		javaTypeMap.put("indexInAdd", "Integer");
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
		tableDefinition.setName("TreepInfo");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("indexId");
		idColumn.setColumnName("INDEX_ID");
		idColumn.setJavaType("Integer");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition id = new ColumnDefinition();
		id.setName("id");
		id.setColumnName("ID");
		id.setJavaType("String");
		id.setLength(100);
		tableDefinition.addColumn(id);

		ColumnDefinition num = new ColumnDefinition();
		num.setName("num");
		num.setColumnName("NUM");
		num.setJavaType("String");
		num.setLength(50);
		tableDefinition.addColumn(num);

		ColumnDefinition topId = new ColumnDefinition();
		topId.setName("topId");
		topId.setColumnName("TOP_ID");
		topId.setJavaType("Integer");
		tableDefinition.addColumn(topId);

		ColumnDefinition parentId = new ColumnDefinition();
		parentId.setName("parentId");
		parentId.setColumnName("PARENT_ID");
		parentId.setJavaType("Integer");
		tableDefinition.addColumn(parentId);

		ColumnDefinition indexName = new ColumnDefinition();
		indexName.setName("indexName");
		indexName.setColumnName("INDEX_NAME");
		indexName.setJavaType("String");
		indexName.setLength(255);
		tableDefinition.addColumn(indexName);

		ColumnDefinition Level = new ColumnDefinition();
		Level.setName("Level");
		Level.setColumnName("NLEVEL");
		Level.setJavaType("Integer");
		tableDefinition.addColumn(Level);

		ColumnDefinition nodeType = new ColumnDefinition();
		nodeType.setName("nodeType");
		nodeType.setColumnName("NODETYPE");
		nodeType.setJavaType("String");
		nodeType.setLength(1);
		tableDefinition.addColumn(nodeType);

		ColumnDefinition fromId = new ColumnDefinition();
		fromId.setName("fromId");
		fromId.setColumnName("FROMID");
		fromId.setJavaType("Integer");
		tableDefinition.addColumn(fromId);

		ColumnDefinition partid = new ColumnDefinition();
		partid.setName("partid");
		partid.setColumnName("PART_ID");
		partid.setJavaType("Integer");
		tableDefinition.addColumn(partid);

		ColumnDefinition showId = new ColumnDefinition();
		showId.setName("showId");
		showId.setColumnName("SHOWID");
		showId.setJavaType("Integer");
		tableDefinition.addColumn(showId);

		ColumnDefinition sindexName = new ColumnDefinition();
		sindexName.setName("sindexName");
		sindexName.setColumnName("SINDEX_NAME");
		sindexName.setJavaType("String");
		sindexName.setLength(255);
		tableDefinition.addColumn(sindexName);

		ColumnDefinition fileNum = new ColumnDefinition();
		fileNum.setName("fileNum");
		fileNum.setColumnName("FILENUM");
		fileNum.setJavaType("Integer");
		tableDefinition.addColumn(fileNum);

		ColumnDefinition fileNum0 = new ColumnDefinition();
		fileNum0.setName("fileNum0");
		fileNum0.setColumnName("FILENUM0");
		fileNum0.setJavaType("Integer");
		tableDefinition.addColumn(fileNum0);

		ColumnDefinition fileNum1 = new ColumnDefinition();
		fileNum1.setName("fileNum1");
		fileNum1.setColumnName("FILENUM1");
		fileNum1.setJavaType("Integer");
		tableDefinition.addColumn(fileNum1);

		ColumnDefinition filenum2 = new ColumnDefinition();
		filenum2.setName("filenum2");
		filenum2.setColumnName("FILENUM2");
		filenum2.setJavaType("Integer");
		tableDefinition.addColumn(filenum2);

		ColumnDefinition projType = new ColumnDefinition();
		projType.setName("projType");
		projType.setColumnName("PROJTYPE");
		projType.setJavaType("String");
		projType.setLength(1);
		tableDefinition.addColumn(projType);

		ColumnDefinition cid = new ColumnDefinition();
		cid.setName("cid");
		cid.setColumnName("CID");
		cid.setJavaType("String");
		cid.setLength(50);
		tableDefinition.addColumn(cid);

		ColumnDefinition dwid = new ColumnDefinition();
		dwid.setName("dwid");
		dwid.setColumnName("DWID");
		dwid.setJavaType("Integer");
		tableDefinition.addColumn(dwid);

		ColumnDefinition fxid = new ColumnDefinition();
		fxid.setName("fxid");
		fxid.setColumnName("FXID");
		fxid.setJavaType("Integer");
		tableDefinition.addColumn(fxid);

		ColumnDefinition fbid = new ColumnDefinition();
		fbid.setName("fbid");
		fbid.setColumnName("FBID");
		fbid.setJavaType("Integer");
		tableDefinition.addColumn(fbid);

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
		topNode.setLength(250);
		tableDefinition.addColumn(topNode);

		ColumnDefinition nodeIco = new ColumnDefinition();
		nodeIco.setName("nodeIco");
		nodeIco.setColumnName("NODEICO");
		nodeIco.setJavaType("Integer");
		tableDefinition.addColumn(nodeIco);

		ColumnDefinition outFlag = new ColumnDefinition();
		outFlag.setName("outFlag");
		outFlag.setColumnName("OUTFLAG");
		outFlag.setJavaType("String");
		outFlag.setLength(1);
		tableDefinition.addColumn(outFlag);

		ColumnDefinition inFlag = new ColumnDefinition();
		inFlag.setName("inFlag");
		inFlag.setColumnName("INFLAG");
		inFlag.setJavaType("String");
		inFlag.setLength(1);
		tableDefinition.addColumn(inFlag);

		ColumnDefinition password = new ColumnDefinition();
		password.setName("password");
		password.setColumnName("PASSWORD");
		password.setJavaType("String");
		password.setLength(10);
		tableDefinition.addColumn(password);

		ColumnDefinition listNum = new ColumnDefinition();
		listNum.setName("listNum");
		listNum.setColumnName("LISTNUM");
		listNum.setJavaType("String");
		listNum.setLength(200);
		tableDefinition.addColumn(listNum);

		ColumnDefinition wcompany = new ColumnDefinition();
		wcompany.setName("wcompany");
		wcompany.setColumnName("WCOMPANY");
		wcompany.setJavaType("String");
		wcompany.setLength(250);
		tableDefinition.addColumn(wcompany);

		ColumnDefinition listNo = new ColumnDefinition();
		listNo.setName("listNo");
		listNo.setColumnName("LISTNO");
		listNo.setJavaType("Integer");
		tableDefinition.addColumn(listNo);

		ColumnDefinition userNmu = new ColumnDefinition();
		userNmu.setName("userNmu");
		userNmu.setColumnName("USERNMU");
		userNmu.setJavaType("String");
		userNmu.setLength(100);
		tableDefinition.addColumn(userNmu);

		ColumnDefinition indexIdOld = new ColumnDefinition();
		indexIdOld.setName("indexIdOld");
		indexIdOld.setColumnName("INDEX_ID_OLD");
		indexIdOld.setJavaType("Integer");
		tableDefinition.addColumn(indexIdOld);

		ColumnDefinition pindexId = new ColumnDefinition();
		pindexId.setName("pindexId");
		pindexId.setColumnName("PINDEX_ID");
		pindexId.setJavaType("Integer");
		tableDefinition.addColumn(pindexId);

		ColumnDefinition finishInt = new ColumnDefinition();
		finishInt.setName("finishInt");
		finishInt.setColumnName("FINISHINT");
		finishInt.setJavaType("Integer");
		tableDefinition.addColumn(finishInt);

		ColumnDefinition typeTableName = new ColumnDefinition();
		typeTableName.setName("typeTableName");
		typeTableName.setColumnName("TYPE_TABLENAME");
		typeTableName.setJavaType("String");
		typeTableName.setLength(50);
		tableDefinition.addColumn(typeTableName);

		ColumnDefinition treedListStr = new ColumnDefinition();
		treedListStr.setName("treedListStr");
		treedListStr.setColumnName("TREE_DLISTSTR");
		treedListStr.setJavaType("String");
		treedListStr.setLength(200);
		tableDefinition.addColumn(treedListStr);

		ColumnDefinition pfilesIndex = new ColumnDefinition();
		pfilesIndex.setName("pfilesIndex");
		pfilesIndex.setColumnName("PFILES_INDEX");
		pfilesIndex.setJavaType("Integer");
		tableDefinition.addColumn(pfilesIndex);

		ColumnDefinition bdate = new ColumnDefinition();
		bdate.setName("bdate");
		bdate.setColumnName("BDATE");
		bdate.setJavaType("Date");
		tableDefinition.addColumn(bdate);

		ColumnDefinition edate = new ColumnDefinition();
		edate.setName("edate");
		edate.setColumnName("EDATE");
		edate.setJavaType("Date");
		tableDefinition.addColumn(edate);

		ColumnDefinition wbsIndex = new ColumnDefinition();
		wbsIndex.setName("wbsIndex");
		wbsIndex.setColumnName("WBSINDEX");
		wbsIndex.setJavaType("Integer");
		tableDefinition.addColumn(wbsIndex);

		ColumnDefinition bdates = new ColumnDefinition();
		bdates.setName("bdates");
		bdates.setColumnName("BDATE_S");
		bdates.setJavaType("Date");
		tableDefinition.addColumn(bdates);

		ColumnDefinition edates = new ColumnDefinition();
		edates.setName("edates");
		edates.setColumnName("EDATE_S");
		edates.setJavaType("Date");
		tableDefinition.addColumn(edates);

		ColumnDefinition typeId = new ColumnDefinition();
		typeId.setName("typeId");
		typeId.setColumnName("TYPE_ID");
		typeId.setJavaType("String");
		typeId.setLength(50);
		tableDefinition.addColumn(typeId);

		ColumnDefinition cell1 = new ColumnDefinition();
		cell1.setName("cell1");
		cell1.setColumnName("CELL1");
		cell1.setJavaType("Integer");
		tableDefinition.addColumn(cell1);

		ColumnDefinition cell2 = new ColumnDefinition();
		cell2.setName("cell2");
		cell2.setColumnName("CELL2");
		cell2.setJavaType("Integer");
		tableDefinition.addColumn(cell2);

		ColumnDefinition cell3 = new ColumnDefinition();
		cell3.setName("cell3");
		cell3.setColumnName("CELL3");
		cell3.setJavaType("Integer");
		tableDefinition.addColumn(cell3);

		ColumnDefinition strFileLocate = new ColumnDefinition();
		strFileLocate.setName("strFileLocate");
		strFileLocate.setColumnName("STRFILELOCATE");
		strFileLocate.setJavaType("String");
		strFileLocate.setLength(50);
		tableDefinition.addColumn(strFileLocate);

		ColumnDefinition intpFile1 = new ColumnDefinition();
		intpFile1.setName("intpFile1");
		intpFile1.setColumnName("INTPFILE1");
		intpFile1.setJavaType("Integer");
		tableDefinition.addColumn(intpFile1);

		ColumnDefinition intpFile2 = new ColumnDefinition();
		intpFile2.setName("intpFile2");
		intpFile2.setColumnName("INTPFILE2");
		intpFile2.setJavaType("Integer");
		tableDefinition.addColumn(intpFile2);

		ColumnDefinition intpFile3 = new ColumnDefinition();
		intpFile3.setName("intpFile3");
		intpFile3.setColumnName("INTPFILE3");
		intpFile3.setJavaType("Integer");
		tableDefinition.addColumn(intpFile3);

		ColumnDefinition intCellFinish = new ColumnDefinition();
		intCellFinish.setName("intCellFinish");
		intCellFinish.setColumnName("INTCELLFINISH");
		intCellFinish.setJavaType("Integer");
		tableDefinition.addColumn(intCellFinish);

		ColumnDefinition sysId = new ColumnDefinition();
		sysId.setName("sysId");
		sysId.setColumnName("SYS_ID");
		sysId.setJavaType("String");
		sysId.setLength(50);
		tableDefinition.addColumn(sysId);

		ColumnDefinition indexIn = new ColumnDefinition();
		indexIn.setName("indexIn");
		indexIn.setColumnName("INDEX_IN");
		indexIn.setJavaType("Integer");
		tableDefinition.addColumn(indexIn);

		ColumnDefinition strButtonStar = new ColumnDefinition();
		strButtonStar.setName("strButtonStar");
		strButtonStar.setColumnName("STRBUTTONSTAR");
		strButtonStar.setJavaType("String");
		strButtonStar.setLength(20);
		tableDefinition.addColumn(strButtonStar);

		ColumnDefinition intIsUse = new ColumnDefinition();
		intIsUse.setName("intIsUse");
		intIsUse.setColumnName("INTISUSE");
		intIsUse.setJavaType("Integer");
		tableDefinition.addColumn(intIsUse);

		ColumnDefinition wbsIndexIn = new ColumnDefinition();
		wbsIndexIn.setName("wbsIndexIn");
		wbsIndexIn.setColumnName("WBSINDEX_IN");
		wbsIndexIn.setJavaType("Integer");
		tableDefinition.addColumn(wbsIndexIn);

		ColumnDefinition uindexId = new ColumnDefinition();
		uindexId.setName("uindexId");
		uindexId.setColumnName("UINDEX_ID");
		uindexId.setJavaType("Integer");
		tableDefinition.addColumn(uindexId);

		ColumnDefinition lisnoWBS = new ColumnDefinition();
		lisnoWBS.setName("lisnoWBS");
		lisnoWBS.setColumnName("LISNO_WBS");
		lisnoWBS.setJavaType("Integer");
		tableDefinition.addColumn(lisnoWBS);

		ColumnDefinition sysIdAdd = new ColumnDefinition();
		sysIdAdd.setName("sysIdAdd");
		sysIdAdd.setColumnName("SYS_ID_ADD");
		sysIdAdd.setJavaType("String");
		sysIdAdd.setLength(50);
		tableDefinition.addColumn(sysIdAdd);

		ColumnDefinition indexInAdd = new ColumnDefinition();
		indexInAdd.setName("indexInAdd");
		indexInAdd.setColumnName("INDEX_IN_ADD");
		indexInAdd.setJavaType("Integer");
		tableDefinition.addColumn(indexInAdd);

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

	private TreepInfoDomainFactory() {

	}

}
