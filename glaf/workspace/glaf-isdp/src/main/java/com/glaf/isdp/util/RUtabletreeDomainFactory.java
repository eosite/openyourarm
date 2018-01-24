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
public class RUtabletreeDomainFactory {

    public static final String TABLENAME = "R_UTABLETREE";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("indexId", "INDEX_ID");
        columnMap.put("id", "ID");
        columnMap.put("parentId", "PARENT_ID");
        columnMap.put("indexName", "INDEX_NAME");
        columnMap.put("nlevel", "NLEVEL");
        columnMap.put("nodeico", "NODEICO");
        columnMap.put("listno", "LISTNO");
        columnMap.put("tabletype", "TABLETYPE");
        columnMap.put("intdel", "INTDEL");
        columnMap.put("busiessId", "BUSIESS_ID");
        columnMap.put("content", "CONTENT");
        columnMap.put("num", "NUM");
        columnMap.put("menuindex", "MENUINDEX");
        columnMap.put("domainIndex", "DOMAIN_INDEX");
        columnMap.put("winWidth", "WIN_WIDTH");
        columnMap.put("winHeight", "WIN_HEIGHT");

	javaTypeMap.put("indexId", "Integer");
        javaTypeMap.put("id", "String");
        javaTypeMap.put("parentId", "Integer");
        javaTypeMap.put("indexName", "String");
        javaTypeMap.put("nlevel", "Integer");
        javaTypeMap.put("nodeico", "Integer");
        javaTypeMap.put("listno", "Integer");
        javaTypeMap.put("tabletype", "Integer");
        javaTypeMap.put("intdel", "Integer");
        javaTypeMap.put("busiessId", "String");
        javaTypeMap.put("content", "String");
        javaTypeMap.put("num", "String");
        javaTypeMap.put("menuindex", "Integer");
        javaTypeMap.put("domainIndex", "Integer");
        javaTypeMap.put("winWidth", "Integer");
        javaTypeMap.put("winHeight", "Integer");
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
        tableDefinition.setName("RUtabletree");

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

        ColumnDefinition nlevel = new ColumnDefinition();
        nlevel.setName("nlevel");
        nlevel.setColumnName("NLEVEL");
        nlevel.setJavaType("Integer");
        tableDefinition.addColumn(nlevel);

        ColumnDefinition nodeico = new ColumnDefinition();
        nodeico.setName("nodeico");
        nodeico.setColumnName("NODEICO");
        nodeico.setJavaType("Integer");
        tableDefinition.addColumn(nodeico);

        ColumnDefinition listno = new ColumnDefinition();
        listno.setName("listno");
        listno.setColumnName("LISTNO");
        listno.setJavaType("Integer");
        tableDefinition.addColumn(listno);

        ColumnDefinition tabletype = new ColumnDefinition();
        tabletype.setName("tabletype");
        tabletype.setColumnName("TABLETYPE");
        tabletype.setJavaType("Integer");
        tableDefinition.addColumn(tabletype);

        ColumnDefinition intdel = new ColumnDefinition();
        intdel.setName("intdel");
        intdel.setColumnName("INTDEL");
        intdel.setJavaType("Integer");
        tableDefinition.addColumn(intdel);

        ColumnDefinition busiessId = new ColumnDefinition();
        busiessId.setName("busiessId");
        busiessId.setColumnName("BUSIESS_ID");
        busiessId.setJavaType("String");
        busiessId.setLength(50);
        tableDefinition.addColumn(busiessId);

        ColumnDefinition content = new ColumnDefinition();
        content.setName("content");
        content.setColumnName("CONTENT");
        content.setJavaType("String");
        content.setLength(250);
        tableDefinition.addColumn(content);

        ColumnDefinition num = new ColumnDefinition();
        num.setName("num");
        num.setColumnName("NUM");
        num.setJavaType("String");
        num.setLength(50);
        tableDefinition.addColumn(num);

        ColumnDefinition menuindex = new ColumnDefinition();
        menuindex.setName("menuindex");
        menuindex.setColumnName("MENUINDEX");
        menuindex.setJavaType("Integer");
        tableDefinition.addColumn(menuindex);

        ColumnDefinition domainIndex = new ColumnDefinition();
        domainIndex.setName("domainIndex");
        domainIndex.setColumnName("DOMAIN_INDEX");
        domainIndex.setJavaType("Integer");
        tableDefinition.addColumn(domainIndex);

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

    private RUtabletreeDomainFactory() {

    }

}
