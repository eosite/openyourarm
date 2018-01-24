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
@Table(name = "DEP_BASE_PROPCATEGORY")
public class DepBasePropCategory implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "RULEID_", nullable = false)
	protected String ruleId;

	/**
	 * 分类唯一标识
	 */
	@Id
	@Column(name = "DEP_BASE_CATEGORY_ID_")
	protected Long depBaseCategoryId;

	public DepBasePropCategory() {

	}

	public String getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public Long getDepBaseCategoryId() {
		return depBaseCategoryId;
	}

	public void setDepBaseCategoryId(Long depBaseCategoryId) {
		this.depBaseCategoryId = depBaseCategoryId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepBasePropCategory other = (DepBasePropCategory) obj;
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

	public DepBasePropCategory jsonToObject(JSONObject jsonObject) {
		return DepBasePropCategoryJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return DepBasePropCategoryJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DepBasePropCategoryJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
