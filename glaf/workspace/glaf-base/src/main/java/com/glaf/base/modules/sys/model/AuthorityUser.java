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
 * 将角色的权限下放给用户，再逐级分配下级角色。
 *
 */

@Entity
@Table(name = "SYS_AUTHORITY_USER")
public class AuthorityUser implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected long id;

	/**
	 * 用户编号
	 */
	@Column(name = "ACTORID_", length = 50)
	protected String actorId;

	/**
	 * 角色编号
	 */
	@Column(name = "ROLEID_")
	protected long roleId;

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

	public AuthorityUser() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthorityUser other = (AuthorityUser) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getActorId() {
		return this.actorId;
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

	public long getId() {
		return this.id;
	}

	public long getRoleId() {
		return this.roleId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public AuthorityUser jsonToObject(JSONObject jsonObject) {
		return AuthorityUserJsonFactory.jsonToObject(jsonObject);
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public JSONObject toJsonObject() {
		return AuthorityUserJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return AuthorityUserJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
