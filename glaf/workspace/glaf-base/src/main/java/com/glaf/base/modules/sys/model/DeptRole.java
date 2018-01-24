/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.glaf.base.modules.sys.model;

import java.io.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.util.DeptRoleJsonFactory;
import com.glaf.core.base.JSONable;

/**
 * 部门角色关联表
 *
 */
@Entity
@Table(name = "sys_dept_role")
public class DeptRole implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键，唯一
	 */
	@Id
	@Column(name = "ID", nullable = false)
	private long id;

	/**
	 * 部门编号，关联部门表的ID
	 */
	@Column(name = "DEPTID")
	private long deptId;

	@Column(name = "DEPTFLAG")
	private int deptFlag;

	/**
	 * 部门名称
	 */
	@javax.persistence.Transient
	private String deptName;

	/**
	 * 菜单标识<br/>
	 * 0-下放角色 <br/>
	 * 9-菜单角色
	 */
	@Column(name = "MENUFLAG")
	private int menuFlag;

	/**
	 * 角色编号，关联角色表的ID
	 */
	@Column(name = "ROLEID")
	private long roleId;

	/**
	 * 角色名称
	 */
	@javax.persistence.Transient
	private String roleName;

	/**
	 * 是否向下传播
	 */
	@Column(name = "ISPROPAGATIONALLOWED_", length = 1)
	protected String isPropagationAllowed = "0";

	public DeptRole() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeptRole other = (DeptRole) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int getDeptFlag() {
		return deptFlag;
	}

	public long getDeptId() {
		return deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public long getId() {
		return id;
	}

	public String getIsPropagationAllowed() {
		return isPropagationAllowed;
	}

	public int getMenuFlag() {
		return menuFlag;
	}

	public long getRoleId() {
		return roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public DeptRole jsonToObject(JSONObject jsonObject) {
		return DeptRoleJsonFactory.jsonToObject(jsonObject);
	}

	public void setDeptFlag(int deptFlag) {
		this.deptFlag = deptFlag;
	}

	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIsPropagationAllowed(String isPropagationAllowed) {
		this.isPropagationAllowed = isPropagationAllowed;
	}

	public void setMenuFlag(int menuFlag) {
		this.menuFlag = menuFlag;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public JSONObject toJsonObject() {
		return DeptRoleJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DeptRoleJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
