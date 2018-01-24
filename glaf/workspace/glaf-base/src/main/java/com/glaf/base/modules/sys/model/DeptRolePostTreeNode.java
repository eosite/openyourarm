package com.glaf.base.modules.sys.model;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;

public class DeptRolePostTreeNode implements Serializable, JSONable {

	private static final long serialVersionUID = 1L;
	private String name;
	private Long deptId;
	private String deptCode;
	private Long treeId;
	private Long pTreeId;
	private Long postId;
	private String postCode;
	private Long roleId;

	public String getName() {
		return name;
	}

	public Long getDeptId() {
		return deptId;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public Long getTreeId() {
		return treeId;
	}

	public Long getpTreeId() {
		return pTreeId;
	}

	public Long getPostId() {
		return postId;
	}

	public String getPostCode() {
		return postCode;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public void setTreeId(Long treeId) {
		this.treeId = treeId;
	}

	public void setpTreeId(Long pTreeId) {
		this.pTreeId = pTreeId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", this.getName());
		jsonObject.put("deptId", this.getDeptId());
		jsonObject.put("deptCode", this.getDeptCode());
		jsonObject.put("treeId", this.getTreeId());
		jsonObject.put("pTreeId", this.getpTreeId());
		jsonObject.put("postId", this.getPostId());
		jsonObject.put("postCode", this.getPostCode());
		jsonObject.put("roleId", this.getRoleId());
		return jsonObject;
	}

	public ObjectNode toObjectNode() {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("name", this.getName());
		jsonObject.put("deptId", this.getDeptId());
		jsonObject.put("deptCode", this.getDeptCode());
		jsonObject.put("treeId", this.getTreeId());
		jsonObject.put("pTreeId", this.getpTreeId());
		jsonObject.put("postId", this.getPostId());
		jsonObject.put("postCode", this.getPostCode());
		jsonObject.put("roleId", this.getRoleId());
		return jsonObject;
	}

	public String toString() {
		return toJsonObject().toJSONString();
	}

	public DeptRolePostTreeNode jsonToObject(JSONObject jsonObject) {
		DeptRolePostTreeNode deptRolePostTreeNode = new DeptRolePostTreeNode();
		if (jsonObject.containsKey("name")) {
			deptRolePostTreeNode.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("deptId")) {
			deptRolePostTreeNode.setDeptId(jsonObject.getLong("deptId"));
		}
		if (jsonObject.containsKey("deptCode")) {
			deptRolePostTreeNode.setDeptCode(jsonObject.getString("deptCode"));
		}
		if (jsonObject.containsKey("treeId")) {
			deptRolePostTreeNode.setTreeId(jsonObject.getLong("treeId"));
		}
		if (jsonObject.containsKey("pTreeId")) {
			deptRolePostTreeNode.setpTreeId(jsonObject.getLong("pTreeId"));
		}
		if (jsonObject.containsKey("postId")) {
			deptRolePostTreeNode.setPostId(jsonObject.getLong("postId"));
		}
		if (jsonObject.containsKey("postCode")) {
			deptRolePostTreeNode.setPostCode(jsonObject.getString("postCode"));
		}
		if (jsonObject.containsKey("roleId")) {
			deptRolePostTreeNode.setRoleId(jsonObject.getLong("roleId"));
		}
		return deptRolePostTreeNode;
	}

}
