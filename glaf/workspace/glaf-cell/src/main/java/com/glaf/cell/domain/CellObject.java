package com.glaf.cell.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.glaf.convert.domain.ConvertElemTmpl;

public class CellObject implements Serializable {
 
	private static final long serialVersionUID = 1L;
	private Long cvtId;
	private String fileid;
	private byte[] desContent;
	private List<Map<String, String>> propList;
	private Map<String,ConvertElemTmpl> elems;

	public Long getCvtId() {
		return cvtId;
	}

	public void setCvtId(Long cvtId) {
		this.cvtId = cvtId;
	}

	public byte[] getDesContent() {
		return desContent;
	}

	public void setDesContent(byte[] desContent) {
		this.desContent = desContent;
	}

	public List<Map<String, String>> getPropList() {
		return propList;
	}

	public void setPropList(List<Map<String, String>> propList) {
		this.propList = propList;
	}

	public Map<String, ConvertElemTmpl> getElems() {
		return elems;
	}

	public void setElems(Map<String, ConvertElemTmpl> elems) {
		this.elems = elems;
	}

	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

}
