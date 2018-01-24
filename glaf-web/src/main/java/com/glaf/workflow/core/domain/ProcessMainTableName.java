package com.glaf.workflow.core.domain;

import java.io.Serializable;

public class ProcessMainTableName implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4676642247922870973L;
	private String fillFormId;
	private String fileDotFileid;
	private String tableName;
	private String useId;
	
	public String getFillFormId() {
		return fillFormId;
	}

	public void setFillFormId(String fillFormId) {
		this.fillFormId = fillFormId;
	}

	public String getFileDotFileid() {
		return fileDotFileid;
	}

	public void setFileDotFileid(String fileDotFileid) {
		this.fileDotFileid = fileDotFileid;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getUseId() {
		return useId;
	}

	public void setUseId(String useId) {
		this.useId = useId;
	}
}
