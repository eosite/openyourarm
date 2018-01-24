package com.glaf.dep.base.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.dep.base.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "DEP_BASE_COMPSCOPE")
public class DepBaseCompScope implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "DEP_BASE_COMPONENT_ID_", nullable = false)
	protected String depBaseComponentId;

	/**
	 * 组件_组件标识
	 */
	@Id
	@Column(name = "DEP_BASE_UI_ID_", nullable = false)
	protected String depBaseUIId;

	/**
	 * 创建人
	 */
	@Column(name = "CREATOR_", length = 20)
	protected String creator;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATETIME_")
	protected Date createDateTime;

	public DepBaseCompScope() {

	}

	public void setDepBaseComponentId(String depBaseComponentId) {
		this.depBaseComponentId = depBaseComponentId;
	}

	public void setDepBaseUIId(String depBaseUIId) {
		this.depBaseUIId = depBaseUIId;
	}

	public String getCreator() {
		return this.creator;
	}

	public Date getCreateDateTime() {
		return this.createDateTime;
	}

	public String getCreateDateTimeString() {
		if (this.createDateTime != null) {
			return DateUtils.getDateTime(this.createDateTime);
		}
		return "";
	}

	public String getDepBaseUIId() {
		return depBaseUIId;
	}

	public String getDepBaseComponentId() {
		return depBaseComponentId;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepBaseCompScope other = (DepBaseCompScope) obj;
		if (depBaseComponentId == null) {
			if (other.depBaseComponentId != null)
				return false;
		} else if (!depBaseComponentId.equals(other.depBaseComponentId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((depBaseComponentId == null) ? 0 : depBaseComponentId
						.hashCode());
		return result;
	}

	public DepBaseCompScope jsonToObject(JSONObject jsonObject) {
		return DepBaseCompScopeJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return DepBaseCompScopeJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DepBaseCompScopeJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
