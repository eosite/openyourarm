package com.glaf.cell.domain;

import java.io.Serializable;

public class CellCategory implements Serializable {
 
	private static final long serialVersionUID = 1L;
	private String categoryName;
	private int row_index;
	private int col_index;

	public String getCategoryName() {
		return categoryName;
	}

	public int getRow_index() {
		return row_index;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setRow_index(int row_index) {
		this.row_index = row_index;
	}

	public int getCol_index() {
		return col_index;
	}

	public void setCol_index(int col_index) {
		this.col_index = col_index;
	}

}
