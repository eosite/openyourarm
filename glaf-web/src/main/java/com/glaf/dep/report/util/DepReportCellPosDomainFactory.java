package com.glaf.dep.report.util;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.FilterDescriptor;
import com.glaf.core.base.TableModel;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.Resources;
import com.glaf.core.xml.XmlMappingReader;


/**
 * 
 * 实体数据工厂类
 *
 */
public class DepReportCellPosDomainFactory {

    public static final String TABLENAME = "DEP_REPORT_CELL_POS";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    protected static TableModel tableModel;

    static{
        columnMap.put("id", "ID_");
        columnMap.put("cellId", "CELL_ID_");
        columnMap.put("rowIndex", "ROW_INDEX_");
        columnMap.put("colIndex", "COL_INDEX_");
        columnMap.put("varFalg", "VARFALG_");
        columnMap.put("direction", "DIRECTION_");
        columnMap.put("endRowIndex", "END_ROW_INDEX_");
        columnMap.put("endColIndex", "END_COL_INDEX_");

	javaTypeMap.put("id", "Long");
        javaTypeMap.put("cellId", "Long");
        javaTypeMap.put("rowIndex", "Integer");
        javaTypeMap.put("colIndex", "Integer");
        javaTypeMap.put("varFalg", "String");
        javaTypeMap.put("direction", "String");
        javaTypeMap.put("endRowIndex", "Integer");
        javaTypeMap.put("endColIndex", "Integer");
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
        tableDefinition.setName("DepReportCellPos");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("Long");
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition cellId = new ColumnDefinition();
        cellId.setName("cellId");
        cellId.setColumnName("CELL_ID_");
        cellId.setJavaType("Long");
        tableDefinition.addColumn(cellId);

        ColumnDefinition rowIndex = new ColumnDefinition();
        rowIndex.setName("rowIndex");
        rowIndex.setColumnName("ROW_INDEX_");
        rowIndex.setJavaType("Integer");
        tableDefinition.addColumn(rowIndex);

        ColumnDefinition colIndex = new ColumnDefinition();
        colIndex.setName("colIndex");
        colIndex.setColumnName("COL_INDEX_");
        colIndex.setJavaType("Integer");
        tableDefinition.addColumn(colIndex);

        ColumnDefinition varFalg = new ColumnDefinition();
        varFalg.setName("varFalg");
        varFalg.setColumnName("VARFALG_");
        varFalg.setJavaType("String");
        varFalg.setLength(1);
        tableDefinition.addColumn(varFalg);

        ColumnDefinition direction = new ColumnDefinition();
        direction.setName("direction");
        direction.setColumnName("DIRECTION_");
        direction.setJavaType("String");
        direction.setLength(1);
        tableDefinition.addColumn(direction);

        ColumnDefinition endRowIndex = new ColumnDefinition();
        endRowIndex.setName("endRowIndex");
        endRowIndex.setColumnName("END_ROW_INDEX_");
        endRowIndex.setJavaType("Integer");
        tableDefinition.addColumn(endRowIndex);

        ColumnDefinition endColIndex = new ColumnDefinition();
        endColIndex.setName("endColIndex");
        endColIndex.setColumnName("END_COL_INDEX_");
        endColIndex.setJavaType("Integer");
        tableDefinition.addColumn(endColIndex);

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

    public static TableModel readTableModel() {
		if (tableModel == null) {
			XmlMappingReader reader = new XmlMappingReader();
			InputStream inputStream = null;
			try {
				inputStream = Resources
						.getResourceAsStream("com/glaf/dep/report/domain/DepReportCellPos.mapping.xml");
				tableModel = reader.read(inputStream);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
		}
		TableModel bean = new TableModel();
		try {
			BeanUtils.copyProperties(bean, tableModel);
		} catch (Exception ex) {
			org.springframework.beans.BeanUtils
					.copyProperties(tableModel, bean);
		}
		return bean;
	}

    private DepReportCellPosDomainFactory() {

    }

}
