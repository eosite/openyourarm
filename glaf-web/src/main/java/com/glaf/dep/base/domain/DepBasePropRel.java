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
@Table(name = "DEP_BASE_PROPREL")
public class DepBasePropRel implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "RULEID_", nullable = false)
	protected String ruleId;

	/**
	 * 规则属_规则标识
	 */
	@Id
	@Column(name = "RELRULEID_", length = 50)
	protected String relRuleId;

	/**
	 * 关系类型
	 */
	@Column(name = "RELTYPE_", length = 20)
	protected String relType;

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
	
	/**
	 * 关联规则属性名称
	 */
	@javax.persistence.Transient
	@Column(name = "RELRULENAME_")
	protected String relRuleName;

	public DepBasePropRel() {

	}

	public String getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getRelRuleId() {
		return this.relRuleId;
	}

	public String getRelType() {
		return this.relType;
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

	public void setRelRuleId(String relRuleId) {
		this.relRuleId = relRuleId;
	}

	public void setRelType(String relType) {
		this.relType = relType;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getRelRuleName() {
		return relRuleName;
	}

	public void setRelRuleName(String relRuleName) {
		this.relRuleName = relRuleName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepBasePropRel other = (DepBasePropRel) obj;
		if (ruleId == null) {
			if (other.ruleId != null)
				return false;
		} else if (!ruleId.equals(other.ruleId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ruleId == null) ? 0 : ruleId.hashCode());
		return result;
	}

	public DepBasePropRel jsonToObject(JSONObject jsonObject) {
		return DepBasePropRelJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return DepBasePropRelJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DepBasePropRelJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
