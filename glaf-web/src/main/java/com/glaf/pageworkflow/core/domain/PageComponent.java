package com.glaf.pageworkflow.core.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PageComponent implements Serializable {

	private static final long serialVersionUID = 752256370797084291L;
	//控件ID
	protected Long compId;
	//控件Name
	protected String compName;
	//控件类型
	protected String compType;
	//Kendo控件类型
	protected String compkdType;
	//页面控件实例名称
	protected String pageCompName;
	//页面控件规则ID
	protected String pageCompRuleId;
	//页面控件实例ID
	protected String pageCompId;
    //图标
	protected String icon;
	
	public Long getCompId() {
		return compId;
	}

	public void setCompId(Long compId) {
		this.compId = compId;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getCompType() {
		return compType;
	}

	public void setCompType(String compType) {
		this.compType = compType;
	}

	public String getCompkdType() {
		return compkdType;
	}

	public void setCompkdType(String compkdType) {
		this.compkdType = compkdType;
	}

	public String getPageCompName() {
		return pageCompName;
	}

	public void setPageCompName(String pageCompName) {
		this.pageCompName = pageCompName;
	}

	public String getPageCompRuleId() {
		return pageCompRuleId;
	}

	public void setPageCompRuleId(String pageCompRuleId) {
		this.pageCompRuleId = pageCompRuleId;
	}

	public String getPageCompId() {
		return pageCompId;
	}

	public void setPageCompId(String pageCompId) {
		this.pageCompId = pageCompId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
