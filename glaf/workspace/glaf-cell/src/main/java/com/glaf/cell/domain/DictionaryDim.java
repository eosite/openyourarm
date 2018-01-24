package com.glaf.cell.domain;

import java.io.Serializable;
/**
 * 字典表维度对象
 * @author Dane
 *
 */
public class DictionaryDim implements Serializable {
 
	private static final long serialVersionUID = 1L;
	private String dimId;
	private int startRowIndex;
	private int startColIndex;
	private int endRowIndex;
	private int endColIndex;
	private int dimWay;
	private String fName;
	private String content;
	public String getDimId() {
		return dimId;
	}
	public int getStartRowIndex() {
		return startRowIndex;
	}
	public int getStartColIndex() {
		return startColIndex;
	}
	public int getEndRowIndex() {
		return endRowIndex;
	}
	public int getEndColIndex() {
		return endColIndex;
	}
	public String getfName() {
		return fName;
	}
	public String getContent() {
		return content;
	}
	public void setDimId(String dimId) {
		this.dimId = dimId;
	}
	public void setStartRowIndex(int startRowIndex) {
		this.startRowIndex = startRowIndex;
	}
	public void setStartColIndex(int startColIndex) {
		this.startColIndex = startColIndex;
	}
	public void setEndRowIndex(int endRowIndex) {
		this.endRowIndex = endRowIndex;
	}
	public void setEndColIndex(int endColIndex) {
		this.endColIndex = endColIndex;
	}
	public int getDimWay() {
		return dimWay;
	}
	public void setDimWay(int dimWay) {
		this.dimWay = dimWay;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
