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

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.sys.util.SysUserJsonFactory;
import com.glaf.core.base.JSONable;
import com.glaf.core.identity.User;
import com.glaf.core.util.RequestUtils;

@Entity
@Table(name = "UserInfo")
public class SysUser implements Serializable, User, JSONable {
	private static final long serialVersionUID = -7677600372139823989L;

	/**
	 * 用户编码
	 */
	@Id
	@Column(name = "USERID", length = 50)
	protected String account;

	/**
	 * 账号类型
	 */
	@Column(name = "ACCOUNTTYPE")
	protected int accountType;

	/**
	 * 管理员标识
	 */
	@Column(name = "ISSYSTEM", length = 10)
	protected String adminFlag;

	/**
	 * 员工编码
	 */
	@Column(name = "CODE", length = 50)
	protected String code;

	/**
	 * 启用标记
	 */
	@Column(name = "STATUS")
	protected String status;

	/**
	 * 删除标记
	 */
	@Column(name = "DELETEFLAG")
	protected int deleteFlag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DELETETIME")
	protected Date deleteTime;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY", length = 50)
	protected String createBy;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CTIME")
	protected Date createTime;

	/**
	 * 部门编号
	 */
	@Column(name = "DEPID")
	protected long deptId;

	/**
	 * 邮件
	 */
	@Column(name = "EMAIL", length = 200)
	protected String email;

	/**
	 * 出差
	 */
	@Column(name = "EVECTION")
	protected int evection;

	/**
	 * 传真
	 */
	@Column(name = "FAX", length = 50)
	protected String fax;

	/**
	 * 性别：0-代表男性（默认），1-代表女性
	 */
	@Column(name = "GENDER")
	protected int gender;

	/**
	 * 职务
	 */
	@Column(name = "HEADSHIP", length = 50)
	protected String headship;

	/**
	 * 最后登录IP
	 */
	@Column(name = "LASTLOGINIP", length = 200)
	protected String lastLoginIP;

	/**
	 * 最后登录时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LASTLOGINTIME")
	protected Date lastLoginTime;

	/**
	 * 锁定登录时间，密码输入次数超过最大重试次数后设定
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOCKLOGINTIME")
	protected Date lockLoginTime;

	/**
	 * 登录重试次数
	 */
	@Column(name = "LOGINRETRY")
	protected int loginRetry;

	@javax.persistence.Transient
	protected String menus;

	/**
	 * 手机
	 */
	@Column(name = "MOBILE", length = 150)
	protected String mobile;

	/**
	 * 用户名
	 */
	@Column(name = "USERNAME", length = 150)
	protected String name;

	/**
	 * 显示名称
	 */
	@Column(name = "SHOWNAME", length = 250)
	protected String showName;

	/**
	 * Hash密码
	 */
	@Column(name = "PASSWORD_HASH", length = 250)
	protected String passwordHash;

	/**
	 * 上级领导
	 */
	@Column(name = "SUPERIORIDS", length = 250)
	protected String superiorIds;

	/**
	 * 电话
	 */
	@Column(name = "TELEPHONE", length = 150)
	protected String telephone;

	/**
	 * 修改人
	 */
	@Column(name = "UPDATEBY", length = 50)
	protected String updateBy;

	/**
	 * 修改日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDATE")
	protected Date updateDate;

	/**
	 * 用户类别
	 */
	@Column(name = "USERTYPE")
	protected int userType;

	@Column(name = "APPID", length = 200)
	protected String appId;

	@Column(name = "APPSECRET", length = 200)
	protected String appSecret;

	/**
	 * Token
	 */
	@Column(name = "TOKEN", length = 250)
	protected String token;

	/**
	 * TOKEN创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TOKENTIME")
	protected Date tokenTime;

	/**
	 * 用户同步标记
	 */
	@Column(name = "SYNCFLAG", columnDefinition = "int default 0")
	protected Integer syncFlag;

	/**
	 * 用户同步时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SYNCTIME")
	protected Date syncTime;

	/**
	 * 同步类型 用于判断该用户是新增、修改或删除等操作
	 */
	@Column(name = "SYNCOPERATORTYPE", length = 50)
	protected String syncOperatorType;

	/**
	 * MQ登录标记
	 */
	@Column(name = "MQLOGINFLAG_", length = 1)
	protected String mqLoginFlag;

	/**
	 * 第三方密锁登录标记
	 */
	@Column(name = "SECRETLOGINFLAG_", length = 1)
	protected String secretLoginFlag;

	/**
	 * 登录密锁
	 */
	@Column(name = "LOGINSECRET_", length = 200)
	protected String loginSecret;

	/**
	 * 密码修改标识
	 */
	@Column(name = "PWDUPDATEFLAG")
	protected int pwdUpdateFlag;

	/**
	 * 登录密锁更新时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOGINSECRETUPDATETIME_")
	protected Date loginSecretUpdateTime;

	/**
	 * 租户编号
	 */
	@Column(name = "TENANTID_", length = 50)
	protected String tenantId;
	
	/**
	 * 截止日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DEADLINETIME")
	protected Date deadlineTime;
	

	@javax.persistence.Transient
	protected String attribute;

	@javax.persistence.Transient
	protected String deptName;

	@javax.persistence.Transient
	protected String deptCode;

	@javax.persistence.Transient
	private SysDepartment department;

	@javax.persistence.Transient
	private List<SysDepartment> nestingDepartment;

	@javax.persistence.Transient
	private Set<SysApplication> apps = new HashSet<SysApplication>();

	@javax.persistence.Transient
	private Set<SysFunction> functions = new HashSet<SysFunction>();

	@javax.persistence.Transient
	private Collection<SysRole> roles = new HashSet<SysRole>();

	@javax.persistence.Transient
	private Set<SysUserRole> userRoles = new HashSet<SysUserRole>();

	@javax.persistence.Transient
	private Set<String> roleCodes = new HashSet<String>();

	@javax.persistence.Transient
	private Collection<String> rowKeys = new HashSet<String>();

	@javax.persistence.Transient
	private Collection<Object> objectIds = new HashSet<Object>();

	public SysUser() {

	}

	public void addObjectId(Object rowId) {
		if (objectIds == null) {
			objectIds = new HashSet<Object>();
		}
		objectIds.add(rowId);
	}

	public void addRowKey(String rowKey) {
		if (rowKeys == null) {
			rowKeys = new HashSet<String>();
		}
		rowKeys.add(rowKey);
	}

	public Date getDeadlineTime() {
		return deadlineTime;
	}

	public void setDeadlineTime(Date deadlineTime) {
		this.deadlineTime = deadlineTime;
	}

	public String getAccount() {
		return account;
	}

	public int getAccountType() {
		return accountType;
	}

	public String getActorId() {
		return account;
	}

	public String getAdminFlag() {
		return adminFlag;
	}

	public String getAppId() {
		return appId;
	}

	public Set<SysApplication> getApps() {
		if (apps == null) {
			apps = new HashSet<SysApplication>();
		}
		return apps;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public String getAttribute() {
		return attribute;
	}

	public String getCode() {
		return code;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public SysDepartment getDepartment() {
		return department;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public long getDeptId() {
		if (department != null) {
			return department.getId();
		}
		return deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public String getEmail() {
		return email;
	}

	public String getEncodeActorId() {
		return RequestUtils.encodeString(this.getActorId());
	}

	public int getEvection() {
		return evection;
	}

	public String getFax() {
		return fax;
	}

	public Set<SysFunction> getFunctions() {
		if (functions == null) {
			functions = new HashSet<SysFunction>();
		}
		return functions;
	}

	public int getGender() {
		return gender;
	}

	public String getHeadship() {
		return headship;
	}

	public long getId() {
		return account.hashCode();
	}

	public Date getLastLoginDate() {
		return lastLoginTime;
	}

	public String getLastLoginIP() {
		return lastLoginIP;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public int getLocked() {
		if (StringUtils.equals(status, "1")) {
			return 1;
		}
		return 0;
	}

	public Date getLockLoginTime() {
		return lockLoginTime;
	}

	public String getLoginIP() {
		return lastLoginIP;
	}

	public int getLoginRetry() {
		return loginRetry;
	}

	public String getLoginSecret() {
		return loginSecret;
	}

	public Date getLoginSecretUpdateTime() {
		return loginSecretUpdateTime;
	}

	public String getMail() {
		return email;
	}

	public String getMenus() {
		return menus;
	}

	public String getMobile() {
		return mobile;
	}

	public String getMqLoginFlag() {
		return mqLoginFlag;
	}

	public String getName() {
		return name;
	}

	public List<SysDepartment> getNestingDepartment() {
		return nestingDepartment;
	}

	public Collection<Object> getObjectIds() {
		return objectIds;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public int getPwdUpdateFlag() {
		return pwdUpdateFlag;
	}

	public String getRemark() {
		return null;
	}

	public Set<String> getRoleCodes() {
		if (roleCodes == null) {
			roleCodes = new HashSet<String>();
		}
		return roleCodes;
	}

	public Collection<SysRole> getRoles() {
		if (roles == null) {
			roles = new HashSet<SysRole>();
		}
		return roles;
	}

	public Collection<String> getRowKeys() {
		return rowKeys;
	}

	public String getSecretLoginFlag() {
		return secretLoginFlag;
	}

	public String getShowName() {
		return showName;
	}

	public String getStatus() {
		return status;
	}

	public String getSuperiorIds() {
		return superiorIds;
	}

	public Integer getSyncFlag() {
		return syncFlag;
	}

	public String getSyncOperatorType() {
		return syncOperatorType;
	}

	public Date getSyncTime() {
		return syncTime;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getTenantId() {
		return tenantId;
	}

	public String getToken() {
		return token;
	}

	public Date getTokenTime() {
		return tokenTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public Set<SysUserRole> getUserRoles() {
		if (userRoles == null) {
			userRoles = new HashSet<SysUserRole>();
		}
		return userRoles;
	}

	public int getUserType() {
		return userType;
	}

	public boolean hasApplicationAccess(long appId) {
		boolean hasAccess = false;
		if (apps != null && !apps.isEmpty()) {
			for (SysApplication app : apps) {
				if (appId == app.getId()) {
					hasAccess = true;
					break;
				}
			}
		}
		return hasAccess;
	}

	public boolean isDepartmentAdmin() {
		boolean isDeptAdmin = false;

		if (roles != null && !roles.isEmpty()) {
			for (SysRole r : roles) {
				if ("R006".equals(r.getCode()) || "DepartmentManager".equals(r.getCode())) {
					isDeptAdmin = true;
					break;
				}
			}
		}

		if (!isDeptAdmin) {
			if (userRoles != null && !userRoles.isEmpty()) {
				for (SysUserRole r : userRoles) {
					if (r.getRole() != null) {
						if ("R006".equals(r.getRole().getCode()) || "DepartmentManager".equals(r.getRole().getCode())) {
							isDeptAdmin = true;
							break;
						}
					}
				}
			}
		}
		return isDeptAdmin;
	}

	public boolean isSystemAdmin() {
		return isSystemAdministrator();
	}

	public boolean isSystemAdministrator() {
		boolean isAdmin = false;

		if (StringUtils.equals(adminFlag, "1")
				&& (StringUtils.equals(account, "admin") || StringUtils.equals(account, "root"))) {
			isAdmin = true;
			return isAdmin;
		}

		if (roles != null && !roles.isEmpty()) {
			for (SysRole r : roles) {
				if ("SystemAdministrator".equals(r.getCode())) {
					// isAdmin = true;
					break;
				}
			}
		}

		if (!isAdmin) {
			if (userRoles != null && !userRoles.isEmpty()) {
				for (SysUserRole r : userRoles) {
					if (r.getRole() != null) {
						if ("SystemAdministrator".equals(r.getRole().getCode())) {
							// isAdmin = true;
							break;
						}
					}
				}
			}
		}

		return isAdmin;
	}

	public SysUser jsonToObject(JSONObject jsonObject) {
		return SysUserJsonFactory.jsonToObject(jsonObject);
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public void setActorId(String actorId) {
		this.account = actorId;
	}

	public void setAdminFlag(String adminFlag) {
		this.adminFlag = adminFlag;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setApps(Set<SysApplication> apps) {
		this.apps = apps;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createTime = createDate;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public void setDepartment(SysDepartment department) {
		this.department = department;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEvection(int evection) {
		this.evection = evection;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public void setFunctions(Set<SysFunction> functions) {
		this.functions = functions;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public void setHeadship(String headship) {
		this.headship = headship;
	}

	public void setId(long id) {

	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginTime = lastLoginDate;
	}

	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public void setLocked(int locked) {
		this.status = String.valueOf(locked);
	}

	public void setLockLoginTime(Date lockLoginTime) {
		this.lockLoginTime = lockLoginTime;
	}

	public void setLoginIP(String loginIP) {
		this.lastLoginIP = loginIP;
	}

	public void setLoginRetry(int loginRetry) {
		this.loginRetry = loginRetry;
	}

	public void setLoginSecret(String loginSecret) {
		this.loginSecret = loginSecret;
	}

	public void setLoginSecretUpdateTime(Date loginSecretUpdateTime) {
		this.loginSecretUpdateTime = loginSecretUpdateTime;
	}

	public void setMail(String mail) {
		this.email = mail;
	}

	public void setMenus(String menus) {
		this.menus = menus;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setMqLoginFlag(String mqLoginFlag) {
		this.mqLoginFlag = mqLoginFlag;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNestingDepartment(List<SysDepartment> nestingDepartment) {
		this.nestingDepartment = nestingDepartment;
	}

	public void setObjectIds(Collection<Object> objectIds) {
		this.objectIds = objectIds;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public void setPwdUpdateFlag(int pwdUpdateFlag) {
		this.pwdUpdateFlag = pwdUpdateFlag;
	}

	public void setRemark(String remark) {

	}

	public void setRoleCodes(Set<String> roleCodes) {
		this.roleCodes = roleCodes;
	}

	public void setRoles(Collection<SysRole> roles) {
		this.roles = roles;
	}

	public void setRowKeys(Collection<String> rowKeys) {
		this.rowKeys = rowKeys;
	}

	public void setSecretLoginFlag(String secretLoginFlag) {
		this.secretLoginFlag = secretLoginFlag;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setSuperiorIds(String superiorIds) {
		this.superiorIds = superiorIds;
	}

	public void setSyncFlag(Integer syncFlag) {
		this.syncFlag = syncFlag;
	}

	public void setSyncOperatorType(String syncOperatorType) {
		this.syncOperatorType = syncOperatorType;
	}

	public void setSyncTime(Date syncTime) {
		this.syncTime = syncTime;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setTokenTime(Date tokenTime) {
		this.tokenTime = tokenTime;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setUserRoles(Set<SysUserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public JSONObject toJsonObject() {
		return SysUserJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SysUserJsonFactory.toObjectNode(this);
	}

	public String toString() {
		return toJsonObject().toJSONString();
	}

}