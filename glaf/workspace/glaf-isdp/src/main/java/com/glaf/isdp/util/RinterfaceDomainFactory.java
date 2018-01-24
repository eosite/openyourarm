package com.glaf.isdp.util;

import java.util.*;
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
public class RinterfaceDomainFactory {

    public static final String TABLENAME = "R_INTERFACE";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID_");
        columnMap.put("indexId", "INDEX_ID");
        columnMap.put("frmtype", "FRMTYPE");
        columnMap.put("listId", "LISTID");
        columnMap.put("issystem", "ISSYSTEM");
        columnMap.put("fname", "FNAME");
        columnMap.put("dname", "DNAME");
        columnMap.put("dtype", "DTYPE");
        columnMap.put("showtype", "SHOWTYPE");
        columnMap.put("strlen", "STRLEN");
        columnMap.put("form", "FORM");
        columnMap.put("intype", "INTYPE");
        columnMap.put("hintID", "HINTID");
        columnMap.put("listno", "LISTNO");
        columnMap.put("ztype", "ZTYPE");
        columnMap.put("ismustfill", "ISMUSTFILL");
        columnMap.put("isListShow", "ISLISTSHOW");
        columnMap.put("listweigth", "LISTWEIGTH");
        columnMap.put("isallwidth", "ISALLWIDTH");
        columnMap.put("istname", "ISTNAME");
        columnMap.put("importType", "IMPORT_TYPE");
        columnMap.put("datapoint", "DATAPOINT");
        columnMap.put("createDate", "CREATEDATE");
        columnMap.put("updateDate", "UPDATEDATE");
        columnMap.put("createBy", "CREATEBY");
        columnMap.put("updateBy", "UPDATEBY");
        columnMap.put("isPrimaryKey", "ISPRIMARYKEY");
        columnMap.put("isGroupBy", "ISGROUPBY");

	javaTypeMap.put("id", "Long");
        javaTypeMap.put("indexId", "String");
        javaTypeMap.put("frmtype", "String");
        javaTypeMap.put("listId", "String");
        javaTypeMap.put("issystem", "String");
        javaTypeMap.put("fname", "String");
        javaTypeMap.put("dname", "String");
        javaTypeMap.put("dtype", "String");
        javaTypeMap.put("showtype", "String");
        javaTypeMap.put("strlen", "Integer");
        javaTypeMap.put("form", "String");
        javaTypeMap.put("intype", "String");
        javaTypeMap.put("hintID", "String");
        javaTypeMap.put("listno", "Integer");
        javaTypeMap.put("ztype", "String");
        javaTypeMap.put("ismustfill", "String");
        javaTypeMap.put("isListShow", "String");
        javaTypeMap.put("listweigth", "Integer");
        javaTypeMap.put("isallwidth", "String");
        javaTypeMap.put("istname", "String");
        javaTypeMap.put("importType", "Integer");
        javaTypeMap.put("datapoint", "Integer");
        javaTypeMap.put("createDate", "Date");
        javaTypeMap.put("updateDate", "Date");
        javaTypeMap.put("createBy", "String");
        javaTypeMap.put("updateBy", "String");
        javaTypeMap.put("isPrimaryKey", "String");
        javaTypeMap.put("isGroupBy", "String");
    }


    public static Map<String, String> getColumnMap() {
	return columnMap;
    }

    public static Map<String, String> getJavaTypeMap() {
	return javaTypeMap;
    }

    public static TableDefinition getTableDefinition(){
        return getTableDefinition(TABLENAME);
    }

    public static TableDefinition getTableDefinition(String tableName) {
        tableName = tableName.toUpperCase();
        TableDefinition tableDefinition = new TableDefinition();
        tableDefinition.setTableName(tableName);
        tableDefinition.setName("Rinterface");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("Long");
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition indexId = new ColumnDefinition();
        indexId.setName("indexId");
        indexId.setColumnName("INDEX_ID");
        indexId.setJavaType("String");
        indexId.setLength(100);
        tableDefinition.addColumn(indexId);

        ColumnDefinition frmtype = new ColumnDefinition();
        frmtype.setName("frmtype");
        frmtype.setColumnName("FRMTYPE");
        frmtype.setJavaType("String");
        frmtype.setLength(30);
        tableDefinition.addColumn(frmtype);

        ColumnDefinition listId = new ColumnDefinition();
        listId.setName("listId");
        listId.setColumnName("LISTID");
        listId.setJavaType("String");
        listId.setLength(50);
        tableDefinition.addColumn(listId);

        ColumnDefinition issystem = new ColumnDefinition();
        issystem.setName("issystem");
        issystem.setColumnName("ISSYSTEM");
        issystem.setJavaType("String");
        issystem.setLength(1);
        tableDefinition.addColumn(issystem);

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
        dname.setLength(100);
        tableDefinition.addColumn(dname);

        ColumnDefinition dtype = new ColumnDefinition();
        dtype.setName("dtype");
        dtype.setColumnName("DTYPE");
        dtype.setJavaType("String");
        dtype.setLength(50);
        tableDefinition.addColumn(dtype);

        ColumnDefinition showtype = new ColumnDefinition();
        showtype.setName("showtype");
        showtype.setColumnName("SHOWTYPE");
        showtype.setJavaType("String");
        showtype.setLength(50);
        tableDefinition.addColumn(showtype);

        ColumnDefinition strlen = new ColumnDefinition();
        strlen.setName("strlen");
        strlen.setColumnName("STRLEN");
        strlen.setJavaType("Integer");
        tableDefinition.addColumn(strlen);

        ColumnDefinition form = new ColumnDefinition();
        form.setName("form");
        form.setColumnName("FORM");
        form.setJavaType("String");
        form.setLength(50);
        tableDefinition.addColumn(form);

        ColumnDefinition intype = new ColumnDefinition();
        intype.setName("intype");
        intype.setColumnName("INTYPE");
        intype.setJavaType("String");
        intype.setLength(10);
        tableDefinition.addColumn(intype);

        ColumnDefinition hintID = new ColumnDefinition();
        hintID.setName("hintID");
        hintID.setColumnName("HINTID");
        hintID.setJavaType("String");
        hintID.setLength(50);
        tableDefinition.addColumn(hintID);

        ColumnDefinition listno = new ColumnDefinition();
        listno.setName("listno");
        listno.setColumnName("LISTNO");
        listno.setJavaType("Integer");
        tableDefinition.addColumn(listno);

        ColumnDefinition ztype = new ColumnDefinition();
        ztype.setName("ztype");
        ztype.setColumnName("ZTYPE");
        ztype.setJavaType("String");
        ztype.setLength(1);
        tableDefinition.addColumn(ztype);

        ColumnDefinition ismustfill = new ColumnDefinition();
        ismustfill.setName("ismustfill");
        ismustfill.setColumnName("ISMUSTFILL");
        ismustfill.setJavaType("String");
        ismustfill.setLength(1);
        tableDefinition.addColumn(ismustfill);

        ColumnDefinition isListShow = new ColumnDefinition();
        isListShow.setName("isListShow");
        isListShow.setColumnName("ISLISTSHOW");
        isListShow.setJavaType("String");
        isListShow.setLength(1);
        tableDefinition.addColumn(isListShow);

        ColumnDefinition listweigth = new ColumnDefinition();
        listweigth.setName("listweigth");
        listweigth.setColumnName("LISTWEIGTH");
        listweigth.setJavaType("Integer");
        tableDefinition.addColumn(listweigth);

        ColumnDefinition isallwidth = new ColumnDefinition();
        isallwidth.setName("isallwidth");
        isallwidth.setColumnName("ISALLWIDTH");
        isallwidth.setJavaType("String");
        isallwidth.setLength(1);
        tableDefinition.addColumn(isallwidth);

        ColumnDefinition istname = new ColumnDefinition();
        istname.setName("istname");
        istname.setColumnName("ISTNAME");
        istname.setJavaType("String");
        istname.setLength(1);
        tableDefinition.addColumn(istname);

        ColumnDefinition importType = new ColumnDefinition();
        importType.setName("importType");
        importType.setColumnName("IMPORT_TYPE");
        importType.setJavaType("Integer");
        tableDefinition.addColumn(importType);

        ColumnDefinition datapoint = new ColumnDefinition();
        datapoint.setName("datapoint");
        datapoint.setColumnName("DATAPOINT");
        datapoint.setJavaType("Integer");
        tableDefinition.addColumn(datapoint);

        ColumnDefinition createDate = new ColumnDefinition();
        createDate.setName("createDate");
        createDate.setColumnName("CREATEDATE");
        createDate.setJavaType("Date");
        tableDefinition.addColumn(createDate);

        ColumnDefinition updateDate = new ColumnDefinition();
        updateDate.setName("updateDate");
        updateDate.setColumnName("UPDATEDATE");
        updateDate.setJavaType("Date");
        tableDefinition.addColumn(updateDate);

        ColumnDefinition createBy = new ColumnDefinition();
        createBy.setName("createBy");
        createBy.setColumnName("CREATEBY");
        createBy.setJavaType("String");
        createBy.setLength(50);
        tableDefinition.addColumn(createBy);

        ColumnDefinition updateBy = new ColumnDefinition();
        updateBy.setName("updateBy");
        updateBy.setColumnName("UPDATEBY");
        updateBy.setJavaType("String");
        updateBy.setLength(50);
        tableDefinition.addColumn(updateBy);

        ColumnDefinition isPrimaryKey = new ColumnDefinition();
        isPrimaryKey.setName("isPrimaryKey");
        isPrimaryKey.setColumnName("ISPRIMARYKEY");
        isPrimaryKey.setJavaType("String");
        isPrimaryKey.setLength(1);
        tableDefinition.addColumn(isPrimaryKey);

        ColumnDefinition isGroupBy = new ColumnDefinition();
        isGroupBy.setName("isGroupBy");
        isGroupBy.setColumnName("ISGROUPBY");
        isGroupBy.setJavaType("String");
        isGroupBy.setLength(1);
        tableDefinition.addColumn(isGroupBy);

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
	    if (dataRequest != null) {
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
    }

    private RinterfaceDomainFactory() {

    }

}
