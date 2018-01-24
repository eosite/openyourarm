package com.glaf.teim.domain;

import java.io.Serializable;

public class EimServerTmpTree  implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;
	
	protected String id;
	
	protected Long parentId;
	
	protected String name;
	
	protected Integer type;

	public String getId() {
		return id;
	}

	public Long getParentId() {
		return parentId;
	}

	public String getName() {
		return name;
	}

	public Integer getType() {
		return type;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
