package com.glaf.form.core.domain;

import java.io.Serializable;

public class FormComponentTemplate implements Serializable{
   
	private String componentId;
	private String template;

	public String getTemplate() {
		return template;
	}
	
	public void setTemplate(String template) {
		this.template = template;
	}

	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}
}
