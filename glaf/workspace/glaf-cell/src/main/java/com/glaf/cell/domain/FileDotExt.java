package com.glaf.cell.domain;


import com.glaf.isdp.domain.FileDot;

public class FileDotExt extends FileDot{

	private static final long serialVersionUID = 1L;
	protected byte[] xmlContent;

	public byte[] getXmlContent() {
		return xmlContent;
	}

	public void setXmlContent(byte[] xmlContent) {
		this.xmlContent = xmlContent;
	}
}
