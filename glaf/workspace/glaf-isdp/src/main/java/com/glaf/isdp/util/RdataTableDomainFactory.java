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
public class RdataTableDomainFactory {

    public static final String TABLENAME = "R_DATA_TABLE";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID");
        columnMap.put("tablename", "TABLENAME");
        columnMap.put("name", "NAME");
        columnMap.put("addtype", "ADDTYPE");
        columnMap.put("maxuser", "MAXUSER");
        columnMap.put("maxsys", "MAXSYS");
        columnMap.put("userid", "USERID");
        columnMap.put("ctime", "CTIME");
        columnMap.put("content", "CONTENT");
        columnMap.put("sysnum", "SYSNUM");
        columnMap.put("issubtable", "ISSUBTABLE");
        columnMap.put("topid", "TOPID");
        columnMap.put("filedotFileid", "FILEDOT_FILEID");
        columnMap.put("indexId", "INDEX_ID");
        columnMap.put("winWidth", "WIN_WIDTH");
        columnMap.put("winHeight", "WIN_HEIGHT");
        columnMap.put("intQuote", "INTQUOTE");
        columnMap.put("intLineEdit", "INTLINEEDIT");
        columnMap.put("printfileid", "PRINTFILEID");
        columnMap.put("INTUSESTREEWBS", "INTUSESTREEWBS");
        columnMap.put("intUseIf", "INTUSEIF");

	javaTypeMap.put("id", "String");
        javaTypeMap.put("tablename", "String");
        javaTypeMap.put("name", "String");
        javaTypeMap.put("addtype", "Integer");
        javaTypeMap.put("maxuser", "Integer");
        javaTypeMap.put("maxsys", "Integer");
        javaTypeMap.put("userid", "String");
        javaTypeMap.put("ctime", "Date");
        javaTypeMap.put("content", "String");
        javaTypeMap.put("sysnum", "String");
        javaTypeMap.put("issubtable", "String");
        javaTypeMap.put("topid", "String");
        javaTypeMap.put("filedotFileid", "String");
        javaTypeMap.put("indexId", "Integer");
        javaTypeMap.put("winWidth", "Integer");
        javaTypeMap.put("winHeight", "Integer");
        javaTypeMap.put("intQuote", "Integer");
        javaTypeMap.put("intLineEdit", "Integer");
        javaTypeMap.put("printfileid", "String");
        javaTypeMap.put("INTUSESTREEWBS", "Integer");
        javaTypeMap.put("intUseIf", "Integer");
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
        tableDefinition.setName("RdataTable");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition tablename = new ColumnDefinition();
        tablename.setName("tablename");
        tablename.setColumnName("TABLENAME");
        tablename.setJavaType("String");
        tablename.setLength(50);
        tableDefinition.addColumn(tablename);

        ColumnDefinition name = new ColumnDefinition();
        name.setName("name");
        name.setColumnName("NAME");
        name.setJavaType("String");
        name.setLength(255);
        tableDefinition.addColumn(name);

        ColumnDefinition addtype = new ColumnDefinition();
        addtype.setName("addtype");
        addtype.setColumnName("ADDTYPE");
        addtype.setJavaType("Integer");
        tableDefinition.addColumn(addtype);

        ColumnDefinition maxuser = new ColumnDefinition();
        maxuser.setName("maxuser");
        maxuser.setColumnName("MAXUSER");
        maxuser.setJavaType("Integer");
        tableDefinition.addColumn(maxuser);

        ColumnDefinition maxsys = new ColumnDefinition();
        maxsys.setName("maxsys");
        maxsys.setColumnName("MAXSYS");
        maxsys.setJavaType("Integer");
        tableDefinition.addColumn(maxsys);

        ColumnDefinition userid = new ColumnDefinition();
        userid.setName("userid");
        userid.setColumnName("USERID");
        userid.setJavaType("String");
        userid.setLength(50);
        tableDefinition.addColumn(userid);

        ColumnDefinition ctime = new ColumnDefinition();
        ctime.setName("ctime");
        ctime.setColumnName("CTIME");
        ctime.setJavaType("Date");
        tableDefinition.addColumn(ctime);

        ColumnDefinition content = new ColumnDefinition();
        content.setName("content");
        content.setColumnName("CONTENT");
        content.setJavaType("String");
        content.setLength(250);
        tableDefinition.addColumn(content);

        ColumnDefinition sysnum = new ColumnDefinition();
        sysnum.setName("sysnum");
        sysnum.setColumnName("SYSNUM");
        sysnum.setJavaType("String");
        sysnum.setLength(100);
        tableDefinition.addColumn(sysnum);

        ColumnDefinition issubtable = new ColumnDefinition();
        issubtable.setName("issubtable");
        issubtable.setColumnName("ISSUBTABLE");
        issubtable.setJavaType("String");
        issubtable.setLength(1);
        tableDefinition.addColumn(issubtable);

        ColumnDefinition topid = new ColumnDefinition();
        topid.setName("topid");
        topid.setColumnName("TOPID");
        topid.setJavaType("String");
        topid.setLength(50);
        tableDefinition.addColumn(topid);

        ColumnDefinition filedotFileid = new ColumnDefinition();
        filedotFileid.setName("filedotFileid");
        filedotFileid.setColumnName("FILEDOT_FILEID");
        filedotFileid.setJavaType("String");
        filedotFileid.setLength(50);
        tableDefinition.addColumn(filedotFileid);

        ColumnDefinition indexId = new ColumnDefinition();
        indexId.setName("indexId");
        indexId.setColumnName("INDEX_ID");
        indexId.setJavaType("Integer");
        tableDefinition.addColumn(indexId);

        ColumnDefinition winWidth = new ColumnDefinition();
        winWidth.setName("winWidth");
        winWidth.setColumnName("WIN_WIDTH");
        winWidth.setJavaType("Integer");
        tableDefinition.addColumn(winWidth);

        ColumnDefinition winHeight = new ColumnDefinition();
        winHeight.setName("winHeight");
        winHeight.setColumnName("WIN_HEIGHT");
        winHeight.setJavaType("Integer");
        tableDefinition.addColumn(winHeight);

        ColumnDefinition intQuote = new ColumnDefinition();
        intQuote.setName("intQuote");
        intQuote.setColumnName("INTQUOTE");
        intQuote.setJavaType("Integer");
        tableDefinition.addColumn(intQuote);

        ColumnDefinition intLineEdit = new ColumnDefinition();
        intLineEdit.setName("intLineEdit");
        intLineEdit.setColumnName("INTLINEEDIT");
        intLineEdit.setJavaType("Integer");
        tableDefinition.addColumn(intLineEdit);

        ColumnDefinition printfileid = new ColumnDefinition();
        printfileid.setName("printfileid");
        printfileid.setColumnName("PRINTFILEID");
        printfileid.setJavaType("String");
        printfileid.setLength(50);
        tableDefinition.addColumn(printfileid);

        ColumnDefinition INTUSESTREEWBS = new ColumnDefinition();
        INTUSESTREEWBS.setName("INTUSESTREEWBS");
        INTUSESTREEWBS.setColumnName("INTUSESTREEWBS");
        INTUSESTREEWBS.setJavaType("Integer");
        tableDefinition.addColumn(INTUSESTREEWBS);

        ColumnDefinition intUseIf = new ColumnDefinition();
        intUseIf.setName("intUseIf");
        intUseIf.setColumnName("INTUSEIF");
        intUseIf.setJavaType("Integer");
        tableDefinition.addColumn(intUseIf);

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

    private RdataTableDomainFactory() {

    }

}
