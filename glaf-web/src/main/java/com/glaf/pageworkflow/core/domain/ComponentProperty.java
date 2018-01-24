package com.glaf.pageworkflow.core.domain;

import com.glaf.form.core.domain.FormComponentProperty;

public class ComponentProperty extends FormComponentProperty{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3327785005818758160L;

	public String getCompType() {
		return compType;
	}

	public void setCompType(String compType) {
		this.compType = compType;
	}

	private String compType;
}
