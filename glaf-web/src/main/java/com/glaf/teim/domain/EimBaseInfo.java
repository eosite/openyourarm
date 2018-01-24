package com.glaf.teim.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.teim.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "EIM_BASEINFO")
public class EimBaseInfo implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;

	@Column(name = "NAME_", length = 100)
	protected String name;

	/**
	 * ip
	 */
	@Column(name = "IP_", length = 30)
	protected String ip;

	/**
	 * host
	 */
	@Column(name = "HOST_", length = 50)
	protected String host;

	/**
	 * secret
	 */
	@Column(name = "SECRET_", length = 150)
	protected String secret;

	/**
	 * paasId
	 */
	@Column(name = "PAASID_", length = 100)
	protected String paasId;

	/**
	 * createBy
	 */
	@Column(name = "CREATEBY_", length = 30)
	protected String createBy;

	/**
	 * createTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	/**
	 * updateBy
	 */
	@Column(name = "UPDATEBY_", length = 30)
	protected String updateBy;

	/**
	 * updateTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATETIME_")
	protected Date updateTime;

	/**
	 * deleteFlag
	 */
	@Column(name = "DELETE_FLAG_")
	protected Integer deleteFlag=0;

	public EimBaseInfo() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return this.ip;
	}

	public String getHost() {
		return this.host;
	}

	public String getSecret() {
		return this.secret;
	}

	public String getPaasId() {
		return this.paasId;
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

	public String getUpdateBy() {
		return this.updateBy;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public String getUpdateTimeString() {
		if (this.updateTime != null) {
			return DateUtils.getDateTime(this.updateTime);
		}
		return "";
	}

	public Integer getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public void setPaasId(String paasId) {
		this.paasId = paasId;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EimBaseInfo other = (EimBaseInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public EimBaseInfo jsonToObject(JSONObject jsonObject) {
		return EimBaseInfoJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return EimBaseInfoJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return EimBaseInfoJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
