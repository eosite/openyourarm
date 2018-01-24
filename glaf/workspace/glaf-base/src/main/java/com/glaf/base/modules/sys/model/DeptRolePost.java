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

/**
 * 部门角色岗位关联表
 *
 */
@Entity
@Table(name = "sys_dept_role_post")
public class DeptRolePost implements Serializable {
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
	@Column(name = "DEPTID", nullable = false)
	private long deptId;

	@Column(name = "DEPTFLAG", nullable = false)
	private int deptFlag;

	/**
	 * 部门名称
	 */
	@javax.persistence.Transient
	private String deptName;

	/**
	 * 角色编号，关联角色表的ID
	 */
	@Column(name = "ROLEID", nullable = false)
	private long roleId;

	/**
	 * 角色名称
	 */
	@javax.persistence.Transient
	private String roleName;

	/**
	 * 岗位编号，关联岗位表的ID
	 */
	@Column(name = "POSTID", nullable = false)
	private long postId;

	/**
	 * 岗位名称
	 */
	@javax.persistence.Transient
	private String postName;

	public DeptRolePost() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeptRolePost other = (DeptRolePost) obj;
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

	public long getPostId() {
		return postId;
	}

	public String getPostName() {
		return postName;
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

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
