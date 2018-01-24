package com.glaf.table.tool.model;

import java.io.Serializable;

public class TableModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tableName;
	private String name;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
