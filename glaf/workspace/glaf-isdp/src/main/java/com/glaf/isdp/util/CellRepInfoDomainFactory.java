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
public class CellRepInfoDomainFactory {

	public static final String TABLENAME = "CELL_REPINFO";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("listId", "LISTID");
		columnMap.put("indexId", "INDEX_ID");
		columnMap.put("frmType", "FRMTYPE");
		columnMap.put("isSystem", "ISSYSTEM");
		columnMap.put("fname", "FNAME");
		columnMap.put("dname", "DNAME");
		columnMap.put("dtype", "DTYPE");
		columnMap.put("showType", "SHOWTYPE");
		columnMap.put("strLen", "STRLEN");
		columnMap.put("form", "FORM");
		columnMap.put("inType", "INTYPE");
		columnMap.put("hintID", "HINTID");
		columnMap.put("listNo", "LISTNO");
		columnMap.put("ztype", "ZTYPE");
		columnMap.put("isMustFill", "ISMUSTFILL");
		columnMap.put("isListShow", "ISLISTSHOW");
		columnMap.put("listWeigth", "LISTWEIGTH");
		columnMap.put("panid", "PANID");
		columnMap.put("isAllWidth", "ISALLWIDTH");
		columnMap.put("istName", "ISTNAME");
		columnMap.put("fileDotFileId", "FILEDOT_FILEID");
		columnMap.put("dataPoint", "DATAPOINT");
		columnMap.put("defaultValue", "DEFAULTVALUE");
		columnMap.put("isSubTable", "ISSUBTABLE");
		columnMap.put("isDataOnly", "ISDATAONLY");
		columnMap.put("checkType", "CHECKTYPE");
		columnMap.put("orderId", "ORDERID");
		columnMap.put("cellType", "CELLTYPE");
		columnMap.put("ostTableName", "OST_TABLENAME");
		columnMap.put("ostRow", "OST_ROW");
		columnMap.put("ostCol", "OST_COL");
		columnMap.put("hintList", "HINTLIST");
		columnMap.put("ostCellId", "OST_CELLID");
		columnMap.put("oldDName", "OLDDNAME");
		columnMap.put("ostCellName", "OST_CELLNAME");
		columnMap.put("ostFormula", "OST_FORMULA");
		columnMap.put("ostColor", "OST_COLOR");
		columnMap.put("ostSumType", "OST_SUMTYPE");
		columnMap.put("dataTableName", "DATA_TABLENAME");
		columnMap.put("dataFieldName", "DATA_FIELDNAME");
		columnMap.put("orderCon", "ORDER_CON");
		columnMap.put("conNum", "CONNUM");
		columnMap.put("nodeIndex", "NODE_INDEX");
		columnMap.put("typeId", "TYPE_ID");
		columnMap.put("userIndex", "USERINDEX");
		columnMap.put("orderIndex", "ORDER_INDEX");
		columnMap.put("parentDName", "PARENT_DNAME");
		columnMap.put("sysAuto", "SYSAUTO");
		columnMap.put("orderIndexF", "ORDER_INDEX_F");
		columnMap.put("orderIdF", "ORDERID_F");
		columnMap.put("orderConF", "ORDER_CON_F");
		columnMap.put("isPrintValue", "ISPRINTVALUE");
		columnMap.put("isShowValueOnLast", "ISSHOWVALUEONLAST");
		columnMap.put("isBankRoundType", "ISBANKROUNDTYPE");
		columnMap.put("floatBankRound", "FLOATBANKROUND");
		columnMap.put("intUseWBSPlace", "INTUSEWBSPLACE");

		javaTypeMap.put("listId", "String");
		javaTypeMap.put("indexId", "String");
		javaTypeMap.put("frmType", "String");
		javaTypeMap.put("isSystem", "String");
		javaTypeMap.put("fname", "String");
		javaTypeMap.put("dname", "String");
		javaTypeMap.put("dtype", "String");
		javaTypeMap.put("showType", "String");
		javaTypeMap.put("strLen", "Integer");
		javaTypeMap.put("form", "String");
		javaTypeMap.put("inType", "String");
		javaTypeMap.put("hintID", "String");
		javaTypeMap.put("listNo", "Integer");
		javaTypeMap.put("ztype", "String");
		javaTypeMap.put("isMustFill", "String");
		javaTypeMap.put("isListShow", "String");
		javaTypeMap.put("listWeigth", "Integer");
		javaTypeMap.put("panid", "String");
		javaTypeMap.put("isAllWidth", "String");
		javaTypeMap.put("istName", "String");
		javaTypeMap.put("fileDotFileId", "String");
		javaTypeMap.put("dataPoint", "Integer");
		javaTypeMap.put("defaultValue", "String");
		javaTypeMap.put("isSubTable", "String");
		javaTypeMap.put("isDataOnly", "String");
		javaTypeMap.put("checkType", "Integer");
		javaTypeMap.put("orderId", "String");
		javaTypeMap.put("cellType", "Integer");
		javaTypeMap.put("ostTableName", "String");
		javaTypeMap.put("ostRow", "Integer");
		javaTypeMap.put("ostCol", "Integer");
		javaTypeMap.put("hintList", "String");
		javaTypeMap.put("ostCellId", "String");
		javaTypeMap.put("oldDName", "String");
		javaTypeMap.put("ostCellName", "String");
		javaTypeMap.put("ostFormula", "String");
		javaTypeMap.put("ostColor", "Integer");
		javaTypeMap.put("ostSumType", "Integer");
		javaTypeMap.put("dataTableName", "String");
		javaTypeMap.put("dataFieldName", "String");
		javaTypeMap.put("orderCon", "Integer");
		javaTypeMap.put("conNum", "Integer");
		javaTypeMap.put("nodeIndex", "Integer");
		javaTypeMap.put("typeId", "String");
		javaTypeMap.put("userIndex", "String");
		javaTypeMap.put("orderIndex", "Integer");
		javaTypeMap.put("parentDName", "String");
		javaTypeMap.put("sysAuto", "Integer");
		javaTypeMap.put("orderIndexF", "Integer");
		javaTypeMap.put("orderIdF", "String");
		javaTypeMap.put("orderConF", "Integer");
		javaTypeMap.put("isPrintValue", "Integer");
		javaTypeMap.put("isShowValueOnLast", "Integer");
		javaTypeMap.put("isBankRoundType", "Integer");
		javaTypeMap.put("floatBankRound", "Double");
		javaTypeMap.put("intUseWBSPlace", "Integer");
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
		tableDefinition.setName("CellRepInfo");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("listId");
		idColumn.setColumnName("LISTID");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition indexId = new ColumnDefinition();
		indexId.setName("indexId");
		indexId.setColumnName("INDEX_ID");
		indexId.setJavaType("String");
		indexId.setLength(100);
		tableDefinition.addColumn(indexId);

		ColumnDefinition frmType = new ColumnDefinition();
		frmType.setName("frmType");
		frmType.setColumnName("FRMTYPE");
		frmType.setJavaType("String");
		frmType.setLength(50);
		tableDefinition.addColumn(frmType);

		ColumnDefinition isSystem = new ColumnDefinition();
		isSystem.setName("isSystem");
		isSystem.setColumnName("ISSYSTEM");
		isSystem.setJavaType("String");
		isSystem.setLength(1);
		tableDefinition.addColumn(isSystem);

		ColumnDefinition fname = new ColumnDefinition();
		fname.setName("fname");
		fname.setColumnName("FNAME");
		fname.setJavaType("String");
		fname.setLength(255);
		tableDefinition.addColumn(fname);

		ColumnDefinition dname = new ColumnDefinition();
		dname.setName("dname");
		dname.setColumnName("DNAME");
		dname.setJavaType("String");
		dname.setLength(50);
		tableDefinition.addColumn(dname);

		ColumnDefinition dtype = new ColumnDefinition();
		dtype.setName("dtype");
		dtype.setColumnName("DTYPE");
		dtype.setJavaType("String");
		dtype.setLength(50);
		tableDefinition.addColumn(dtype);

		ColumnDefinition showType = new ColumnDefinition();
		showType.setName("showType");
		showType.setColumnName("SHOWTYPE");
		showType.setJavaType("String");
		showType.setLength(50);
		tableDefinition.addColumn(showType);

		ColumnDefinition strLen = new ColumnDefinition();
		strLen.setName("strLen");
		strLen.setColumnName("STRLEN");
		strLen.setJavaType("Integer");
		tableDefinition.addColumn(strLen);

		ColumnDefinition form = new ColumnDefinition();
		form.setName("form");
		form.setColumnName("FORM");
		form.setJavaType("String");
		form.setLength(50);
		tableDefinition.addColumn(form);

		ColumnDefinition inType = new ColumnDefinition();
		inType.setName("inType");
		inType.setColumnName("INTYPE");
		inType.setJavaType("String");
		inType.setLength(10);
		tableDefinition.addColumn(inType);

		ColumnDefinition hintID = new ColumnDefinition();
		hintID.setName("hintID");
		hintID.setColumnName("HINTID");
		hintID.setJavaType("String");
		hintID.setLength(50);
		tableDefinition.addColumn(hintID);

		ColumnDefinition listNo = new ColumnDefinition();
		listNo.setName("listNo");
		listNo.setColumnName("LISTNO");
		listNo.setJavaType("Integer");
		tableDefinition.addColumn(listNo);

		ColumnDefinition ztype = new ColumnDefinition();
		ztype.setName("ztype");
		ztype.setColumnName("ZTYPE");
		ztype.setJavaType("String");
		ztype.setLength(1);
		tableDefinition.addColumn(ztype);

		ColumnDefinition isMustFill = new ColumnDefinition();
		isMustFill.setName("isMustFill");
		isMustFill.setColumnName("ISMUSTFILL");
		isMustFill.setJavaType("String");
		isMustFill.setLength(1);
		tableDefinition.addColumn(isMustFill);

		ColumnDefinition isListShow = new ColumnDefinition();
		isListShow.setName("isListShow");
		isListShow.setColumnName("ISLISTSHOW");
		isListShow.setJavaType("String");
		isListShow.setLength(1);
		tableDefinition.addColumn(isListShow);

		ColumnDefinition listWeigth = new ColumnDefinition();
		listWeigth.setName("listWeigth");
		listWeigth.setColumnName("LISTWEIGTH");
		listWeigth.setJavaType("Integer");
		tableDefinition.addColumn(listWeigth);

		ColumnDefinition panid = new ColumnDefinition();
		panid.setName("panid");
		panid.setColumnName("PANID");
		panid.setJavaType("String");
		panid.setLength(50);
		tableDefinition.addColumn(panid);

		ColumnDefinition isAllWidth = new ColumnDefinition();
		isAllWidth.setName("isAllWidth");
		isAllWidth.setColumnName("ISALLWIDTH");
		isAllWidth.setJavaType("String");
		isAllWidth.setLength(1);
		tableDefinition.addColumn(isAllWidth);

		ColumnDefinition istName = new ColumnDefinition();
		istName.setName("istName");
		istName.setColumnName("ISTNAME");
		istName.setJavaType("String");
		istName.setLength(1);
		tableDefinition.addColumn(istName);

		ColumnDefinition fileDotFileId = new ColumnDefinition();
		fileDotFileId.setName("fileDotFileId");
		fileDotFileId.setColumnName("FILEDOT_FILEID");
		fileDotFileId.setJavaType("String");
		fileDotFileId.setLength(50);
		tableDefinition.addColumn(fileDotFileId);

		ColumnDefinition dataPoint = new ColumnDefinition();
		dataPoint.setName("dataPoint");
		dataPoint.setColumnName("DATAPOINT");
		dataPoint.setJavaType("Integer");
		tableDefinition.addColumn(dataPoint);

		ColumnDefinition defaultValue = new ColumnDefinition();
		defaultValue.setName("defaultValue");
		defaultValue.setColumnName("DEFAULTVALUE");
		defaultValue.setJavaType("String");
		defaultValue.setLength(50);
		tableDefinition.addColumn(defaultValue);

		ColumnDefinition isSubTable = new ColumnDefinition();
		isSubTable.setName("isSubTable");
		isSubTable.setColumnName("ISSUBTABLE");
		isSubTable.setJavaType("String");
		isSubTable.setLength(1);
		tableDefinition.addColumn(isSubTable);

		ColumnDefinition isDataOnly = new ColumnDefinition();
		isDataOnly.setName("isDataOnly");
		isDataOnly.setColumnName("ISDATAONLY");
		isDataOnly.setJavaType("String");
		isDataOnly.setLength(1);
		tableDefinition.addColumn(isDataOnly);

		ColumnDefinition checkType = new ColumnDefinition();
		checkType.setName("checkType");
		checkType.setColumnName("CHECKTYPE");
		checkType.setJavaType("Integer");
		tableDefinition.addColumn(checkType);

		ColumnDefinition orderId = new ColumnDefinition();
		orderId.setName("orderId");
		orderId.setColumnName("ORDERID");
		orderId.setJavaType("String");
		orderId.setLength(50);
		tableDefinition.addColumn(orderId);

		ColumnDefinition cellType = new ColumnDefinition();
		cellType.setName("cellType");
		cellType.setColumnName("CELLTYPE");
		cellType.setJavaType("Integer");
		tableDefinition.addColumn(cellType);

		ColumnDefinition ostTableName = new ColumnDefinition();
		ostTableName.setName("ostTableName");
		ostTableName.setColumnName("OST_TABLENAME");
		ostTableName.setJavaType("String");
		ostTableName.setLength(50);
		tableDefinition.addColumn(ostTableName);

		ColumnDefinition ostRow = new ColumnDefinition();
		ostRow.setName("ostRow");
		ostRow.setColumnName("OST_ROW");
		ostRow.setJavaType("Integer");
		tableDefinition.addColumn(ostRow);

		ColumnDefinition ostCol = new ColumnDefinition();
		ostCol.setName("ostCol");
		ostCol.setColumnName("OST_COL");
		ostCol.setJavaType("Integer");
		tableDefinition.addColumn(ostCol);

		ColumnDefinition hintList = new ColumnDefinition();
		hintList.setName("hintList");
		hintList.setColumnName("HINTLIST");
		hintList.setJavaType("String");
		hintList.setLength(250);
		tableDefinition.addColumn(hintList);

		ColumnDefinition ostCellId = new ColumnDefinition();
		ostCellId.setName("ostCellId");
		ostCellId.setColumnName("OST_CELLID");
		ostCellId.setJavaType("String");
		ostCellId.setLength(20);
		tableDefinition.addColumn(ostCellId);

		ColumnDefinition oldDName = new ColumnDefinition();
		oldDName.setName("oldDName");
		oldDName.setColumnName("OLDDNAME");
		oldDName.setJavaType("String");
		oldDName.setLength(50);
		tableDefinition.addColumn(oldDName);

		ColumnDefinition ostCellName = new ColumnDefinition();
		ostCellName.setName("ostCellName");
		ostCellName.setColumnName("OST_CELLNAME");
		ostCellName.setJavaType("String");
		ostCellName.setLength(20);
		tableDefinition.addColumn(ostCellName);

		ColumnDefinition ostFormula = new ColumnDefinition();
		ostFormula.setName("ostFormula");
		ostFormula.setColumnName("OST_FORMULA");
		ostFormula.setJavaType("String");
		ostFormula.setLength(0);
		tableDefinition.addColumn(ostFormula);

		ColumnDefinition ostColor = new ColumnDefinition();
		ostColor.setName("ostColor");
		ostColor.setColumnName("OST_COLOR");
		ostColor.setJavaType("Integer");
		tableDefinition.addColumn(ostColor);

		ColumnDefinition ostSumType = new ColumnDefinition();
		ostSumType.setName("ostSumType");
		ostSumType.setColumnName("OST_SUMTYPE");
		ostSumType.setJavaType("Integer");
		tableDefinition.addColumn(ostSumType);

		ColumnDefinition dataTableName = new ColumnDefinition();
		dataTableName.setName("dataTableName");
		dataTableName.setColumnName("DATA_TABLENAME");
		dataTableName.setJavaType("String");
		dataTableName.setLength(50);
		tableDefinition.addColumn(dataTableName);

		ColumnDefinition dataFieldName = new ColumnDefinition();
		dataFieldName.setName("dataFieldName");
		dataFieldName.setColumnName("DATA_FIELDNAME");
		dataFieldName.setJavaType("String");
		dataFieldName.setLength(50);
		tableDefinition.addColumn(dataFieldName);

		ColumnDefinition orderCon = new ColumnDefinition();
		orderCon.setName("orderCon");
		orderCon.setColumnName("ORDER_CON");
		orderCon.setJavaType("Integer");
		tableDefinition.addColumn(orderCon);

		ColumnDefinition conNum = new ColumnDefinition();
		conNum.setName("conNum");
		conNum.setColumnName("CONNUM");
		conNum.setJavaType("Integer");
		tableDefinition.addColumn(conNum);

		ColumnDefinition nodeIndex = new ColumnDefinition();
		nodeIndex.setName("nodeIndex");
		nodeIndex.setColumnName("NODE_INDEX");
		nodeIndex.setJavaType("Integer");
		tableDefinition.addColumn(nodeIndex);

		ColumnDefinition typeId = new ColumnDefinition();
		typeId.setName("typeId");
		typeId.setColumnName("TYPE_ID");
		typeId.setJavaType("String");
		typeId.setLength(50);
		tableDefinition.addColumn(typeId);

		ColumnDefinition userIndex = new ColumnDefinition();
		userIndex.setName("userIndex");
		userIndex.setColumnName("USERINDEX");
		userIndex.setJavaType("String");
		userIndex.setLength(50);
		tableDefinition.addColumn(userIndex);

		ColumnDefinition orderIndex = new ColumnDefinition();
		orderIndex.setName("orderIndex");
		orderIndex.setColumnName("ORDER_INDEX");
		orderIndex.setJavaType("Integer");
		tableDefinition.addColumn(orderIndex);

		ColumnDefinition parentDName = new ColumnDefinition();
		parentDName.setName("parentDName");
		parentDName.setColumnName("PARENT_DNAME");
		parentDName.setJavaType("String");
		parentDName.setLength(50);
		tableDefinition.addColumn(parentDName);

		ColumnDefinition sysAuto = new ColumnDefinition();
		sysAuto.setName("sysAuto");
		sysAuto.setColumnName("SYSAUTO");
		sysAuto.setJavaType("Integer");
		tableDefinition.addColumn(sysAuto);

		ColumnDefinition orderIndexF = new ColumnDefinition();
		orderIndexF.setName("orderIndexF");
		orderIndexF.setColumnName("ORDER_INDEX_F");
		orderIndexF.setJavaType("Integer");
		tableDefinition.addColumn(orderIndexF);

		ColumnDefinition orderIdF = new ColumnDefinition();
		orderIdF.setName("orderIdF");
		orderIdF.setColumnName("ORDERID_F");
		orderIdF.setJavaType("String");
		orderIdF.setLength(50);
		tableDefinition.addColumn(orderIdF);

		ColumnDefinition orderConF = new ColumnDefinition();
		orderConF.setName("orderConF");
		orderConF.setColumnName("ORDER_CON_F");
		orderConF.setJavaType("Integer");
		tableDefinition.addColumn(orderConF);

		ColumnDefinition isPrintValue = new ColumnDefinition();
		isPrintValue.setName("isPrintValue");
		isPrintValue.setColumnName("ISPRINTVALUE");
		isPrintValue.setJavaType("Integer");
		tableDefinition.addColumn(isPrintValue);

		ColumnDefinition isShowValueOnLast = new ColumnDefinition();
		isShowValueOnLast.setName("isShowValueOnLast");
		isShowValueOnLast.setColumnName("ISSHOWVALUEONLAST");
		isShowValueOnLast.setJavaType("Integer");
		tableDefinition.addColumn(isShowValueOnLast);

		ColumnDefinition isBankRoundType = new ColumnDefinition();
		isBankRoundType.setName("isBankRoundType");
		isBankRoundType.setColumnName("ISBANKROUNDTYPE");
		isBankRoundType.setJavaType("Integer");
		tableDefinition.addColumn(isBankRoundType);

		ColumnDefinition floatBankRound = new ColumnDefinition();
		floatBankRound.setName("floatBankRound");
		floatBankRound.setColumnName("FLOATBANKROUND");
		floatBankRound.setJavaType("Double");
		tableDefinition.addColumn(floatBankRound);

		ColumnDefinition intUseWBSPlace = new ColumnDefinition();
		intUseWBSPlace.setName("intUseWBSPlace");
		intUseWBSPlace.setColumnName("INTUSEWBSPLACE");
		intUseWBSPlace.setJavaType("Integer");
		tableDefinition.addColumn(intUseWBSPlace);

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

	private CellRepInfoDomainFactory() {

	}

}
