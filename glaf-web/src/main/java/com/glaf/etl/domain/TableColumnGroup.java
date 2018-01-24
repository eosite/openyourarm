package com.glaf.etl.domain;

import java.util.List;

public class TableColumnGroup {

	private List<String> srcGroupColumn;
	private String srcTransColumn;
	private String srcValColumn;

	public List<String> getSrcGroupColumn() {
		return srcGroupColumn;
	}

	public String getSrcTransColumn() {
		return srcTransColumn;
	}

	public String getSrcValColumn() {
		return srcValColumn;
	}

	public void setSrcGroupColumn(List<String> srcGroupColumn) {
		this.srcGroupColumn = srcGroupColumn;
	}

	public void setSrcTransColumn(String srcTransColumn) {
		this.srcTransColumn = srcTransColumn;
	}

	public void setSrcValColumn(String srcValColumn) {
		this.srcValColumn = srcValColumn;
	}
}
