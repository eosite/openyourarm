package com.glaf.cell.domain;

import java.io.Serializable;

public class ElementExtProp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6851935466497658400L;
	private String listId;
	private String dataTableId;
	private String fName;
	private String dType;
	private int cellType;
	private String inType;
	private int strlen;
	private int dataPoint; 
	private String defaultValue;
	private String orderId;
	private int ost_row;
	private int ost_col;
	private String isPrintValue;
	private String isListShow;
	private String isMustfill;
	private String name;
	private String tableName;
	private String columnName;
	private String isSubTable;
	private String isDataOnly;
	private String dataTableName;
	private String dataFieldName;
	private int nodeIndex;
	private String userIndex;
	private int orderCon;
	private int orderIndex;
	private String parentDName;
	

	public String getListId() {
		return listId;
	}

	public void setListId(String listId) {
		this.listId = listId;
	}

	public String getfName() {
		return fName;
	}

	public String getdType() {
		return dType;
	}

	public String getInType() {
		return inType;
	}

	public int getStrlen() {
		return strlen;
	}

	public void setStrlen(int strlen) {
		this.strlen = strlen;
	}

	public int getDataPoint() {
		return dataPoint;
	}

	public void setDataPoint(int dataPoint) {
		this.dataPoint = dataPoint;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public int getOst_row() {
		return ost_row;
	}

	public int getOst_col() {
		return ost_col;
	}

	public String getIsPrintValue() {
		return isPrintValue;
	}

	public String getIsListShow() {
		return isListShow;
	}

	public String getIsMustfill() {
		return isMustfill;
	}

	public String getTableName() {
		return tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public String getIsDataOnly() {
		return isDataOnly;
	}

	public String getDataTableName() {
		return dataTableName;
	}

	public String getDataFieldName() {
		return dataFieldName;
	}

	public int getNodeIndex() {
		return nodeIndex;
	}

	public String getUserIndex() {
		return userIndex;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public String getParentDName() {
		return parentDName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public void setdType(String dType) {
		this.dType = dType;
	}

	public void setInType(String inType) {
		this.inType = inType;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setOst_row(int ost_row) {
		this.ost_row = ost_row;
	}

	public void setOst_col(int ost_col) {
		this.ost_col = ost_col;
	}

	public void setIsPrintValue(String isPrintValue) {
		this.isPrintValue = isPrintValue;
	}

	public void setIsListShow(String isListShow) {
		this.isListShow = isListShow;
	}

	public void setIsMustfill(String isMustfill) {
		this.isMustfill = isMustfill;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setIsDataOnly(String isDataOnly) {
		this.isDataOnly = isDataOnly;
	}

	public void setDataTableName(String dataTableName) {
		this.dataTableName = dataTableName;
	}

	public void setDataFieldName(String dataFieldName) {
		this.dataFieldName = dataFieldName;
	}

	public void setNodeIndex(int nodeIndex) {
		this.nodeIndex = nodeIndex;
	}

	public void setUserIndex(String userIndex) {
		this.userIndex = userIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public void setParentDName(String parentDName) {
		this.parentDName = parentDName;
	}

	public String getIsSubTable() {
		return isSubTable;
	}

	public void setIsSubTable(String isSubTable) {
		this.isSubTable = isSubTable;
	}

	public String getDataTableId() {
		return dataTableId;
	}

	public void setDataTableId(String dataTableId) {
		this.dataTableId = dataTableId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getCellType() {
		return cellType;
	}

	public void setCellType(int cellType) {
		this.cellType = cellType;
	}

	public int getOrderCon() {
		return orderCon;
	}

	public void setOrderCon(int orderCon) {
		this.orderCon = orderCon;
	}
}
