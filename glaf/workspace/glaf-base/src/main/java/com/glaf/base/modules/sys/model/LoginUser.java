package com.glaf.base.modules.sys.model;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.base.modules.sys.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "LOGIN_USER")
public class LoginUser implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 200, nullable = false)
	protected String id;

	/**
	 * 用户ID
	 */
	@Column(name = "USERID_", length = 200)
	protected String userId;

	/**
	 * 姓名
	 */
	@Column(name = "USERNAME_", length = 250)
	protected String name;

	/**
	 * 登录ID
	 */
	@Column(name = "LOGINID_", length = 500)
	protected String loginId;

	/**
	 * 登录密码
	 */
	@Column(name = "PASSWORD_", length = 500)
	protected String password;

	/**
	 * 密码类型
	 */
	@Column(name = "PASSWORDTYPE_", length = 50)
	protected String passwordType;

	/**
	 * 系统代码
	 */
	@Column(name = "SYSTEMCODE_", length = 50)
	protected String systemCode;

	/**
	 * 机构名称
	 */
	@Column(name = "ORGANIZATION_", length = 250)
	protected String organization;

	/**
	 * 部门名称
	 */
	@Column(name = "DEPARTMENT_", length = 250)
	protected String department;

	/**
	 * 岗位名称
	 */
	@Column(name = "POSITION_", length = 250)
	protected String position;

	/**
	 * 邮箱
	 */
	@Column(name = "MAIL_", length = 200)
	protected String mail;

	/**
	 * 手机
	 */
	@Column(name = "MOBILE_", length = 50)
	protected String mobile;

	/**
	 * 有效时长
	 */
	@Column(name = "TIMELIVE_")
	protected int timeLive;

	/**
	 * 登录时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOGINTIME_")
	protected Date loginTime;

	/**
	 * 扩展属性
	 */
	@Lob
	@Column(name = "ATTRIBUTE_", length = 2000)
	protected String attribute;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	public LoginUser() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginUser other = (LoginUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getAttribute() {
		return this.attribute;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public String getCreateTimeString() {
		if (this.createTime != null) {
			return DateUtils.getDateTime(this.createTime);
		}
		return "";
	}

	public String getDepartment() {
		return this.department;
	}

	public String getId() {
		return this.id;
	}

	public String getLoginId() {
		return this.loginId;
	}

	public Date getLoginTime() {
		return this.loginTime;
	}

	public String getLoginTimeString() {
		if (this.loginTime != null) {
			return DateUtils.getDateTime(this.loginTime);
		}
		return "";
	}

	public String getMail() {
		return this.mail;
	}

	public String getMobile() {
		return this.mobile;
	}

	public String getName() {
		return this.name;
	}

	public String getOrganization() {
		return this.organization;
	}

	public String getPassword() {
		return this.password;
	}

	public String getPasswordType() {
		return this.passwordType;
	}

	public String getPosition() {
		return this.position;
	}

	public String getSystemCode() {
		return this.systemCode;
	}

	public int getTimeLive() {
		return this.timeLive;
	}

	public String getUserId() {
		return this.userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public LoginUser jsonToObject(JSONObject jsonObject) {
		return LoginUserJsonFactory.jsonToObject(jsonObject);
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPasswordType(String passwordType) {
		this.passwordType = passwordType;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public void setTimeLive(int timeLive) {
		this.timeLive = timeLive;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public JSONObject toJsonObject() {
		return LoginUserJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return LoginUserJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
