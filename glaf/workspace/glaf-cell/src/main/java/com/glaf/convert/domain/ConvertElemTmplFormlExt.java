package com.glaf.convert.domain;

import javax.persistence.Transient;

public class ConvertElemTmplFormlExt extends ConvertElemTmplForml {

	private static final long serialVersionUID = 1L;
	
	@Transient
	protected Long cvtId;

	public Long getCvtId() {
		return cvtId;
	}

	public void setCvtId(Long cvtId) {
		this.cvtId = cvtId;
	}
}
