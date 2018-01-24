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
public class RdataFieldDomainFactory {

    public static final String TABLENAME = "R_DATA_FIELD";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID");
        columnMap.put("tableid", "TABLEID");
        columnMap.put("fieldname", "FIELDNAME");
        columnMap.put("userid", "USERID");
        columnMap.put("maxuser", "MAXUSER");
        columnMap.put("maxsys", "MAXSYS");
        columnMap.put("ctime", "CTIME");
        columnMap.put("sysnum", "SYSNUM");
        columnMap.put("tablename", "TABLENAME");
        columnMap.put("dname", "DNAME");
        columnMap.put("userindex", "USERINDEX");
        columnMap.put("treetablenameB", "TREETABLENAME_B");
        columnMap.put("formula", "FORMULA");
        columnMap.put("lgcexpress", "LGCEXPRESS");

	javaTypeMap.put("id", "String");
        javaTypeMap.put("tableid", "String");
        javaTypeMap.put("fieldname", "String");
        javaTypeMap.put("userid", "String");
        javaTypeMap.put("maxuser", "Integer");
        javaTypeMap.put("maxsys", "Integer");
        javaTypeMap.put("ctime", "Date");
        javaTypeMap.put("sysnum", "String");
        javaTypeMap.put("tablename", "String");
        javaTypeMap.put("dname", "String");
        javaTypeMap.put("userindex", "String");
        javaTypeMap.put("treetablenameB", "String");
        javaTypeMap.put("formula", "String");
        javaTypeMap.put("lgcexpress", "String");
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
        tableDefinition.setName("RdataField");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition tableid = new ColumnDefinition();
        tableid.setName("tableid");
        tableid.setColumnName("TABLEID");
        tableid.setJavaType("String");
        tableid.setLength(50);
        tableDefinition.addColumn(tableid);

        ColumnDefinition fieldname = new ColumnDefinition();
        fieldname.setName("fieldname");
        fieldname.setColumnName("FIELDNAME");
        fieldname.setJavaType("String");
        fieldname.setLength(50);
        tableDefinition.addColumn(fieldname);

        ColumnDefinition userid = new ColumnDefinition();
        userid.setName("userid");
        userid.setColumnName("USERID");
        userid.setJavaType("String");
        userid.setLength(50);
        tableDefinition.addColumn(userid);

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

        ColumnDefinition ctime = new ColumnDefinition();
        ctime.setName("ctime");
        ctime.setColumnName("CTIME");
        ctime.setJavaType("Date");
        tableDefinition.addColumn(ctime);

        ColumnDefinition sysnum = new ColumnDefinition();
        sysnum.setName("sysnum");
        sysnum.setColumnName("SYSNUM");
        sysnum.setJavaType("String");
        sysnum.setLength(100);
        tableDefinition.addColumn(sysnum);

        ColumnDefinition tablename = new ColumnDefinition();
        tablename.setName("tablename");
        tablename.setColumnName("TABLENAME");
        tablename.setJavaType("String");
        tablename.setLength(50);
        tableDefinition.addColumn(tablename);

        ColumnDefinition dname = new ColumnDefinition();
        dname.setName("dname");
        dname.setColumnName("DNAME");
        dname.setJavaType("String");
        dname.setLength(50);
        tableDefinition.addColumn(dname);

        ColumnDefinition userindex = new ColumnDefinition();
        userindex.setName("userindex");
        userindex.setColumnName("USERINDEX");
        userindex.setJavaType("String");
        userindex.setLength(50);
        tableDefinition.addColumn(userindex);

        ColumnDefinition treetablenameB = new ColumnDefinition();
        treetablenameB.setName("treetablenameB");
        treetablenameB.setColumnName("TREETABLENAME_B");
        treetablenameB.setJavaType("String");
        treetablenameB.setLength(50);
        tableDefinition.addColumn(treetablenameB);

        ColumnDefinition formula = new ColumnDefinition();
        formula.setName("formula");
        formula.setColumnName("FORMULA");
        formula.setJavaType("String");
        formula.setLength(1000);
        tableDefinition.addColumn(formula);

        ColumnDefinition lgcexpress = new ColumnDefinition();
        lgcexpress.setName("lgcexpress");
        lgcexpress.setColumnName("LGCEXPRESS");
        lgcexpress.setJavaType("String");
        lgcexpress.setLength(1000);
        tableDefinition.addColumn(lgcexpress);

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

    private RdataFieldDomainFactory() {

    }

}
