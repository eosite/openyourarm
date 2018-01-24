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
@Table(name = "DEP_BASE_COMPPROP")
public class DepBaseCompProp implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "DEP_BASE_COMPONENT_ID_", nullable = false)
	protected String depBaseComponentId;

	/**
	 * 规则标识
	 */
	@Id
	@Column(name = "RULEID_", nullable = false)
	protected String ruleId;
	
	@javax.persistence.Transient
	protected List<DepBaseComponent> components;
	
	@javax.persistence.Transient
	protected List<DepBaseProp> props;

	public DepBaseCompProp() {

	}

	public String getDepBaseComponentId() {
		return depBaseComponentId;
	}

	public void setDepBaseComponentId(String depBaseComponentId) {
		this.depBaseComponentId = depBaseComponentId;
	}

	public String getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public List<DepBaseComponent> getComponents() {
		return components;
	}

	public void setComponents(List<DepBaseComponent> components) {
		this.components = components;
	}

	public List<DepBaseProp> getProps() {
		return props;
	}

	public void setProps(List<DepBaseProp> props) {
		this.props = props;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepBaseCompProp other = (DepBaseCompProp) obj;
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

	public DepBaseCompProp jsonToObject(JSONObject jsonObject) {
		return DepBaseCompPropJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return DepBaseCompPropJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DepBaseCompPropJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
