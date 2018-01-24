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
public class PinfoDomainFactory {

	public static final String TABLENAME = "PINFO";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("id", "ID");
		columnMap.put("indexId", "INDEX_ID");
		columnMap.put("itemNum", "ITEMNUM");
		columnMap.put("dtag", "DTAG");
		columnMap.put("ftag", "FTAG");
		columnMap.put("name", "NAME");
		columnMap.put("allName", "ALLNAME");
		columnMap.put("bcompany", "BCOMPANY");
		columnMap.put("ccompany", "CCOMPANY");
		columnMap.put("dcompany", "DCOMPANY");
		columnMap.put("conCompany", "CONCOMPANY");
		columnMap.put("icompany", "ICOMPANY");
		columnMap.put("cmark", "CMARK");
		columnMap.put("pmark", "PMARK");
		columnMap.put("tpmark", "TPMARK");
		columnMap.put("conMark", "CONMARK");
		columnMap.put("mapNum", "MAPNUM");
		columnMap.put("conStart", "CONSTART");
		columnMap.put("conEnd", "CONEND");
		columnMap.put("totalLen", "TOTALLEN");
		columnMap.put("startDate", "STARTDATE");
		columnMap.put("endDate", "ENDDATE");
		columnMap.put("cost", "COST");
		columnMap.put("balance", "BALANCE");
		columnMap.put("pmannager", "PMANNAGER");
		columnMap.put("enginee", "ENGINEE");
		columnMap.put("owner", "OWNER");
		columnMap.put("planDate", "PLANDATE");
		columnMap.put("hintDay", "HINTDAY");
		columnMap.put("duty", "DUTY");
		columnMap.put("removeDate", "REMOVEDATE");
		columnMap.put("removeDuty", "REMOVEDUTY");
		columnMap.put("removeFile", "REMOVEFILE");
		columnMap.put("partProj", "PARTPROJ");
		columnMap.put("cnum", "CNUM");
		columnMap.put("concompany2", "CONCOMPANY2");
		columnMap.put("icompany2", "ICOMPANY2");
		columnMap.put("mileAge", "MILEAGE");
		columnMap.put("place", "PLACE");
		columnMap.put("place1", "PLACE1");
		columnMap.put("place2", "PLACE2");
		columnMap.put("place3", "PLACE3");
		columnMap.put("place4", "PLACE4");
		columnMap.put("setPlace", "SETPLACE");
		columnMap.put("setTemp", "SETTEMP");
		columnMap.put("bdNum", "BDNUM");
		columnMap.put("dtNum", "DTNUM");
		columnMap.put("pinfoUser2", "PINFO_USER2");
		columnMap.put("pinfoUser3", "PINFO_USER3");
		columnMap.put("pinfoUser4", "PINFO_USER4");
		columnMap.put("pinfoUser5", "PINFO_USER5");
		columnMap.put("pinfoUser6", "PINFO_USER6");
		columnMap.put("pinfoUser7", "PINFO_USER7");
		columnMap.put("pinfoUser8", "PINFO_USER8");
		columnMap.put("pinfoUser9", "PINFO_USER9");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("indexId", "Integer");
		javaTypeMap.put("itemNum", "String");
		javaTypeMap.put("dtag", "String");
		javaTypeMap.put("ftag", "String");
		javaTypeMap.put("name", "String");
		javaTypeMap.put("allName", "String");
		javaTypeMap.put("bcompany", "String");
		javaTypeMap.put("ccompany", "String");
		javaTypeMap.put("dcompany", "String");
		javaTypeMap.put("conCompany", "String");
		javaTypeMap.put("icompany", "String");
		javaTypeMap.put("cmark", "String");
		javaTypeMap.put("pmark", "String");
		javaTypeMap.put("tpmark", "String");
		javaTypeMap.put("conMark", "String");
		javaTypeMap.put("mapNum", "String");
		javaTypeMap.put("conStart", "String");
		javaTypeMap.put("conEnd", "String");
		javaTypeMap.put("totalLen", "Double");
		javaTypeMap.put("startDate", "Date");
		javaTypeMap.put("endDate", "Date");
		javaTypeMap.put("cost", "Double");
		javaTypeMap.put("balance", "Double");
		javaTypeMap.put("pmannager", "String");
		javaTypeMap.put("enginee", "String");
		javaTypeMap.put("owner", "String");
		javaTypeMap.put("planDate", "Date");
		javaTypeMap.put("hintDay", "Integer");
		javaTypeMap.put("duty", "String");
		javaTypeMap.put("removeDate", "Date");
		javaTypeMap.put("removeDuty", "String");
		javaTypeMap.put("removeFile", "String");
		javaTypeMap.put("partProj", "String");
		javaTypeMap.put("cnum", "String");
		javaTypeMap.put("concompany2", "String");
		javaTypeMap.put("icompany2", "String");
		javaTypeMap.put("mileAge", "Double");
		javaTypeMap.put("place", "String");
		javaTypeMap.put("place1", "Double");
		javaTypeMap.put("place2", "Double");
		javaTypeMap.put("place3", "Double");
		javaTypeMap.put("place4", "Double");
		javaTypeMap.put("setPlace", "String");
		javaTypeMap.put("setTemp", "String");
		javaTypeMap.put("bdNum", "String");
		javaTypeMap.put("dtNum", "String");
		javaTypeMap.put("pinfoUser2", "String");
		javaTypeMap.put("pinfoUser3", "Double");
		javaTypeMap.put("pinfoUser4", "Double");
		javaTypeMap.put("pinfoUser5", "String");
		javaTypeMap.put("pinfoUser6", "Integer");
		javaTypeMap.put("pinfoUser7", "Double");
		javaTypeMap.put("pinfoUser8", "Double");
		javaTypeMap.put("pinfoUser9", "String");
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
		tableDefinition.setName("Pinfo");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition indexId = new ColumnDefinition();
		indexId.setName("indexId");
		indexId.setColumnName("INDEX_ID");
		indexId.setJavaType("Integer");
		tableDefinition.addColumn(indexId);

		ColumnDefinition itemNum = new ColumnDefinition();
		itemNum.setName("itemNum");
		itemNum.setColumnName("ITEMNUM");
		itemNum.setJavaType("String");
		itemNum.setLength(50);
		tableDefinition.addColumn(itemNum);

		ColumnDefinition dtag = new ColumnDefinition();
		dtag.setName("dtag");
		dtag.setColumnName("DTAG");
		dtag.setJavaType("String");
		dtag.setLength(1);
		tableDefinition.addColumn(dtag);

		ColumnDefinition ftag = new ColumnDefinition();
		ftag.setName("ftag");
		ftag.setColumnName("FTAG");
		ftag.setJavaType("String");
		ftag.setLength(1);
		tableDefinition.addColumn(ftag);

		ColumnDefinition name = new ColumnDefinition();
		name.setName("name");
		name.setColumnName("NAME");
		name.setJavaType("String");
		name.setLength(100);
		tableDefinition.addColumn(name);

		ColumnDefinition allName = new ColumnDefinition();
		allName.setName("allName");
		allName.setColumnName("ALLNAME");
		allName.setJavaType("String");
		allName.setLength(100);
		tableDefinition.addColumn(allName);

		ColumnDefinition bcompany = new ColumnDefinition();
		bcompany.setName("bcompany");
		bcompany.setColumnName("BCOMPANY");
		bcompany.setJavaType("String");
		bcompany.setLength(100);
		tableDefinition.addColumn(bcompany);

		ColumnDefinition ccompany = new ColumnDefinition();
		ccompany.setName("ccompany");
		ccompany.setColumnName("CCOMPANY");
		ccompany.setJavaType("String");
		ccompany.setLength(100);
		tableDefinition.addColumn(ccompany);

		ColumnDefinition dcompany = new ColumnDefinition();
		dcompany.setName("dcompany");
		dcompany.setColumnName("DCOMPANY");
		dcompany.setJavaType("String");
		dcompany.setLength(100);
		tableDefinition.addColumn(dcompany);

		ColumnDefinition conCompany = new ColumnDefinition();
		conCompany.setName("conCompany");
		conCompany.setColumnName("CONCOMPANY");
		conCompany.setJavaType("String");
		conCompany.setLength(100);
		tableDefinition.addColumn(conCompany);

		ColumnDefinition icompany = new ColumnDefinition();
		icompany.setName("icompany");
		icompany.setColumnName("ICOMPANY");
		icompany.setJavaType("String");
		icompany.setLength(100);
		tableDefinition.addColumn(icompany);

		ColumnDefinition cmark = new ColumnDefinition();
		cmark.setName("cmark");
		cmark.setColumnName("CMARK");
		cmark.setJavaType("String");
		cmark.setLength(30);
		tableDefinition.addColumn(cmark);

		ColumnDefinition pmark = new ColumnDefinition();
		pmark.setName("pmark");
		pmark.setColumnName("PMARK");
		pmark.setJavaType("String");
		pmark.setLength(30);
		tableDefinition.addColumn(pmark);

		ColumnDefinition tpmark = new ColumnDefinition();
		tpmark.setName("tpmark");
		tpmark.setColumnName("TPMARK");
		tpmark.setJavaType("String");
		tpmark.setLength(30);
		tableDefinition.addColumn(tpmark);

		ColumnDefinition conMark = new ColumnDefinition();
		conMark.setName("conMark");
		conMark.setColumnName("CONMARK");
		conMark.setJavaType("String");
		conMark.setLength(30);
		tableDefinition.addColumn(conMark);

		ColumnDefinition mapNum = new ColumnDefinition();
		mapNum.setName("mapNum");
		mapNum.setColumnName("MAPNUM");
		mapNum.setJavaType("String");
		mapNum.setLength(30);
		tableDefinition.addColumn(mapNum);

		ColumnDefinition conStart = new ColumnDefinition();
		conStart.setName("conStart");
		conStart.setColumnName("CONSTART");
		conStart.setJavaType("String");
		conStart.setLength(100);
		tableDefinition.addColumn(conStart);

		ColumnDefinition conEnd = new ColumnDefinition();
		conEnd.setName("conEnd");
		conEnd.setColumnName("CONEND");
		conEnd.setJavaType("String");
		conEnd.setLength(100);
		tableDefinition.addColumn(conEnd);

		ColumnDefinition totalLen = new ColumnDefinition();
		totalLen.setName("totalLen");
		totalLen.setColumnName("TOTALLEN");
		totalLen.setJavaType("Double");
		tableDefinition.addColumn(totalLen);

		ColumnDefinition startDate = new ColumnDefinition();
		startDate.setName("startDate");
		startDate.setColumnName("STARTDATE");
		startDate.setJavaType("Date");
		tableDefinition.addColumn(startDate);

		ColumnDefinition endDate = new ColumnDefinition();
		endDate.setName("endDate");
		endDate.setColumnName("ENDDATE");
		endDate.setJavaType("Date");
		tableDefinition.addColumn(endDate);

		ColumnDefinition cost = new ColumnDefinition();
		cost.setName("cost");
		cost.setColumnName("COST");
		cost.setJavaType("Double");
		tableDefinition.addColumn(cost);

		ColumnDefinition balance = new ColumnDefinition();
		balance.setName("balance");
		balance.setColumnName("BALANCE");
		balance.setJavaType("Double");
		tableDefinition.addColumn(balance);

		ColumnDefinition pmannager = new ColumnDefinition();
		pmannager.setName("pmannager");
		pmannager.setColumnName("PMANNAGER");
		pmannager.setJavaType("String");
		pmannager.setLength(20);
		tableDefinition.addColumn(pmannager);

		ColumnDefinition enginee = new ColumnDefinition();
		enginee.setName("enginee");
		enginee.setColumnName("ENGINEE");
		enginee.setJavaType("String");
		enginee.setLength(20);
		tableDefinition.addColumn(enginee);

		ColumnDefinition owner = new ColumnDefinition();
		owner.setName("owner");
		owner.setColumnName("OWNER");
		owner.setJavaType("String");
		owner.setLength(20);
		tableDefinition.addColumn(owner);

		ColumnDefinition planDate = new ColumnDefinition();
		planDate.setName("planDate");
		planDate.setColumnName("PLANDATE");
		planDate.setJavaType("Date");
		tableDefinition.addColumn(planDate);

		ColumnDefinition hintDay = new ColumnDefinition();
		hintDay.setName("hintDay");
		hintDay.setColumnName("HINTDAY");
		hintDay.setJavaType("Integer");
		tableDefinition.addColumn(hintDay);

		ColumnDefinition duty = new ColumnDefinition();
		duty.setName("duty");
		duty.setColumnName("DUTY");
		duty.setJavaType("String");
		duty.setLength(20);
		tableDefinition.addColumn(duty);

		ColumnDefinition removeDate = new ColumnDefinition();
		removeDate.setName("removeDate");
		removeDate.setColumnName("REMOVEDATE");
		removeDate.setJavaType("Date");
		tableDefinition.addColumn(removeDate);

		ColumnDefinition removeDuty = new ColumnDefinition();
		removeDuty.setName("removeDuty");
		removeDuty.setColumnName("REMOVEDUTY");
		removeDuty.setJavaType("String");
		removeDuty.setLength(20);
		tableDefinition.addColumn(removeDuty);

		ColumnDefinition removeFile = new ColumnDefinition();
		removeFile.setName("removeFile");
		removeFile.setColumnName("REMOVEFILE");
		removeFile.setJavaType("String");
		removeFile.setLength(50);
		tableDefinition.addColumn(removeFile);

		ColumnDefinition partProj = new ColumnDefinition();
		partProj.setName("partProj");
		partProj.setColumnName("PARTPROJ");
		partProj.setJavaType("String");
		partProj.setLength(100);
		tableDefinition.addColumn(partProj);

		ColumnDefinition cnum = new ColumnDefinition();
		cnum.setName("cnum");
		cnum.setColumnName("CNUM");
		cnum.setJavaType("String");
		cnum.setLength(20);
		tableDefinition.addColumn(cnum);

		ColumnDefinition concompany2 = new ColumnDefinition();
		concompany2.setName("concompany2");
		concompany2.setColumnName("CONCOMPANY2");
		concompany2.setJavaType("String");
		concompany2.setLength(100);
		tableDefinition.addColumn(concompany2);

		ColumnDefinition icompany2 = new ColumnDefinition();
		icompany2.setName("icompany2");
		icompany2.setColumnName("ICOMPANY2");
		icompany2.setJavaType("String");
		icompany2.setLength(100);
		tableDefinition.addColumn(icompany2);

		ColumnDefinition mileAge = new ColumnDefinition();
		mileAge.setName("mileAge");
		mileAge.setColumnName("MILEAGE");
		mileAge.setJavaType("Double");
		tableDefinition.addColumn(mileAge);

		ColumnDefinition place = new ColumnDefinition();
		place.setName("place");
		place.setColumnName("PLACE");
		place.setJavaType("String");
		place.setLength(100);
		tableDefinition.addColumn(place);

		ColumnDefinition place1 = new ColumnDefinition();
		place1.setName("place1");
		place1.setColumnName("PLACE1");
		place1.setJavaType("Double");
		tableDefinition.addColumn(place1);

		ColumnDefinition place2 = new ColumnDefinition();
		place2.setName("place2");
		place2.setColumnName("PLACE2");
		place2.setJavaType("Double");
		tableDefinition.addColumn(place2);

		ColumnDefinition place3 = new ColumnDefinition();
		place3.setName("place3");
		place3.setColumnName("PLACE3");
		place3.setJavaType("Double");
		tableDefinition.addColumn(place3);

		ColumnDefinition place4 = new ColumnDefinition();
		place4.setName("place4");
		place4.setColumnName("PLACE4");
		place4.setJavaType("Double");
		tableDefinition.addColumn(place4);

		ColumnDefinition setPlace = new ColumnDefinition();
		setPlace.setName("setPlace");
		setPlace.setColumnName("SETPLACE");
		setPlace.setJavaType("String");
		setPlace.setLength(20);
		tableDefinition.addColumn(setPlace);

		ColumnDefinition setTemp = new ColumnDefinition();
		setTemp.setName("setTemp");
		setTemp.setColumnName("SETTEMP");
		setTemp.setJavaType("String");
		setTemp.setLength(20);
		tableDefinition.addColumn(setTemp);

		ColumnDefinition bdNum = new ColumnDefinition();
		bdNum.setName("bdNum");
		bdNum.setColumnName("BDNUM");
		bdNum.setJavaType("String");
		bdNum.setLength(50);
		tableDefinition.addColumn(bdNum);

		ColumnDefinition dtNum = new ColumnDefinition();
		dtNum.setName("dtNum");
		dtNum.setColumnName("DTNUM");
		dtNum.setJavaType("String");
		dtNum.setLength(50);
		tableDefinition.addColumn(dtNum);

		ColumnDefinition pinfoUser2 = new ColumnDefinition();
		pinfoUser2.setName("pinfoUser2");
		pinfoUser2.setColumnName("PINFO_USER2");
		pinfoUser2.setJavaType("String");
		pinfoUser2.setLength(100);
		tableDefinition.addColumn(pinfoUser2);

		ColumnDefinition pinfoUser3 = new ColumnDefinition();
		pinfoUser3.setName("pinfoUser3");
		pinfoUser3.setColumnName("PINFO_USER3");
		pinfoUser3.setJavaType("Double");
		tableDefinition.addColumn(pinfoUser3);

		ColumnDefinition pinfoUser4 = new ColumnDefinition();
		pinfoUser4.setName("pinfoUser4");
		pinfoUser4.setColumnName("PINFO_USER4");
		pinfoUser4.setJavaType("Double");
		tableDefinition.addColumn(pinfoUser4);

		ColumnDefinition pinfoUser5 = new ColumnDefinition();
		pinfoUser5.setName("pinfoUser5");
		pinfoUser5.setColumnName("PINFO_USER5");
		pinfoUser5.setJavaType("String");
		pinfoUser5.setLength(20);
		tableDefinition.addColumn(pinfoUser5);

		ColumnDefinition pinfoUser6 = new ColumnDefinition();
		pinfoUser6.setName("pinfoUser6");
		pinfoUser6.setColumnName("PINFO_USER6");
		pinfoUser6.setJavaType("Integer");
		tableDefinition.addColumn(pinfoUser6);

		ColumnDefinition pinfoUser7 = new ColumnDefinition();
		pinfoUser7.setName("pinfoUser7");
		pinfoUser7.setColumnName("PINFO_USER7");
		pinfoUser7.setJavaType("Double");
		tableDefinition.addColumn(pinfoUser7);

		ColumnDefinition pinfoUser8 = new ColumnDefinition();
		pinfoUser8.setName("pinfoUser8");
		pinfoUser8.setColumnName("PINFO_USER8");
		pinfoUser8.setJavaType("Double");
		tableDefinition.addColumn(pinfoUser8);

		ColumnDefinition pinfoUser9 = new ColumnDefinition();
		pinfoUser9.setName("pinfoUser9");
		pinfoUser9.setColumnName("PINFO_USER9");
		pinfoUser9.setJavaType("String");
		pinfoUser9.setLength(100);
		tableDefinition.addColumn(pinfoUser9);

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

	private PinfoDomainFactory() {

	}

}
