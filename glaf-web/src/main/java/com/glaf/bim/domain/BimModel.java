package com.glaf.bim.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "FORM_BIMMODEL")
public class BimModel implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;

	/**
	 * nodeId
	 */
	@Column(name = "NODEID_", length = 50)
	protected String nodeId;

	/**
	 * ParentId
	 */
	@Column(name = "PARENTID_", length = 50)
	protected String parentId;

	/**
	 * Name
	 */
	@Column(name = "NAME_", length = 50)
	protected String name;

	/**
	 * NodeType
	 */
	@Column(name = "NODETYPE_", length = 50)
	protected String nodeType;

	/**
	 * Describe
	 */
	@Column(name = "DESCRIBE_", length = 100)
	protected String describe;

	/**
	 * Describe
	 */
	@Column(name = "MODELCODE_", length = 100)
	protected String modelCode;

	public BimModel() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return this.parentId;
	}

	public String getName() {
		return this.name;
	}

	public String getNodeType() {
		return this.nodeType;
	}

	public String getDescribe() {
		return this.describe;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	@Override
	public Object jsonToObject(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject toJsonObject() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

}
