package com.glaf.form.rule;

import java.util.List;

import com.glaf.core.base.DataRequest;

public class DataSourceRequest extends DataRequest {
	private static final long serialVersionUID = 1L;
	private String rid;
	private List<?> models;
	private String prid;
	private String parentId;
	private String paramType;// 参数类型 query:查询参数; relative:关联参数
	private String params;// 其他参数字符串
	private String id ;

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public List<?> getModels() {
		return models;
	}

	public void setModels(List<?> models) {
		this.models = models;
	}

	public String getPrid() {
		return prid;
	}

	public void setPrid(String prid) {
		this.prid = prid;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
