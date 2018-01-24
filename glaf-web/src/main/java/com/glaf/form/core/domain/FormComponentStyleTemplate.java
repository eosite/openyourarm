package com.glaf.form.core.domain;

import java.io.Serializable;

public class FormComponentStyleTemplate implements Serializable {
	private String id;
	private String componentId;
	private String dataRole;
	private String name;
	private Long imglen;
	private String template;

	public String getId() {
		return id;
	}

	public String getComponentId() {
		return componentId;
	}

	public String getDataRole() {
		return dataRole;
	}

	public String getName() {
		return name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public void setDataRole(String dataRole) {
		this.dataRole = dataRole;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getImglen() {
		return imglen;
	}

	public void setImglen(Long imglen) {
		this.imglen = imglen;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
}
