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
@Table(name = "DEP_BASE_PROP")
public class DepBaseProp implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "RULEID_", nullable = false)
	protected String ruleId;

	/**
	 * 规则代码
	 */
	@Column(name = "RULECODE", length = 50)
	protected String ruleCode;

	/**
	 * 规则名称
	 */
	@Column(name = "RULENAME_", length = 50)
	protected String ruleName;

	/**
	 * 规则描述
	 */
	@Column(name = "RULEDESC", length = 300)
	protected String ruleDesc;

	/**
	 * 系统规则分类
	 */
	@Column(name = "SYSCATEGORY_", length = 50)
	protected String sysCategory;

	/**
	 * 使用规则分类
	 */
	@Column(name = "USECATEGORY_", length = 50)
	protected String useCategory;

	/**
	 * 开放用户
	 */
	@Column(name = "OPENFLAG_", length = 1)
	protected String openFlag;

	/**
	 * 排序号
	 */
	@Column(name = "ORDERNO_")
	protected Integer orderNo;

	/**
	 * 只读
	 */
	@Column(name = "READONLY_", length = 1)
	protected String readOnly;

	/**
	 * 是否允许重复
	 */
	@Column(name = "REPEATFLAG_", length = 1)
	protected String repeatFlag;

	/**
	 * 必要性
	 */
	@Column(name = "NOTNULL_", length = 1)
	protected String notNull;

	/**
	 * 录入方式
	 */
	@Column(name = "INPUTTYPE_", length = 20)
	protected String inputType;

	/**
	 * 默认值
	 */
	@Column(name = "DEFAULTVAL_", length = 100)
	protected String defaultVal;

	/**
	 * 扩展属性
	 */
	@Column(name = "EXTJSON_", length = 255)
	protected String extJson;

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
	 * 修改人
	 */
	@Column(name = "MODIFIER_", length = 20)
	protected String modifier;

	/**
	 * 修改时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFYDATETIME_")
	protected Date modifyDateTime;

	/**
	 * 删除标识
	 */
	@Column(name = "DELFLAG_", length = 1)
	protected String delFlag;
	
	@javax.persistence.Transient
	protected List<DepBaseComponent> components;

	public DepBaseProp() {

	}

	public String getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleCode() {
		return this.ruleCode;
	}

	public String getRuleName() {
		return this.ruleName;
	}

	public String getRuleDesc() {
		return this.ruleDesc;
	}

	public String getSysCategory() {
		return this.sysCategory;
	}

	public String getUseCategory() {
		return this.useCategory;
	}

	public String getOpenFlag() {
		return this.openFlag;
	}

	public Integer getOrderNo() {
		return this.orderNo;
	}

	public String getReadOnly() {
		return this.readOnly;
	}

	public String getRepeatFlag() {
		return this.repeatFlag;
	}

	public String getNotNull() {
		return this.notNull;
	}

	public String getInputType() {
		return this.inputType;
	}

	public String getDefaultVal() {
		return this.defaultVal;
	}

	public String getExtJson() {
		return this.extJson;
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

	public String getModifier() {
		return this.modifier;
	}

	public Date getModifyDateTime() {
		return this.modifyDateTime;
	}

	public String getModifyDateTimeString() {
		if (this.modifyDateTime != null) {
			return DateUtils.getDateTime(this.modifyDateTime);
		}
		return "";
	}

	public String getDelFlag() {
		return this.delFlag;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	public void setSysCategory(String sysCategory) {
		this.sysCategory = sysCategory;
	}

	public void setUseCategory(String useCategory) {
		this.useCategory = useCategory;
	}

	public void setOpenFlag(String openFlag) {
		this.openFlag = openFlag;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	public void setRepeatFlag(String repeatFlag) {
		this.repeatFlag = repeatFlag;
	}

	public void setNotNull(String notNull) {
		this.notNull = notNull;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}

	public void setExtJson(String extJson) {
		this.extJson = extJson;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public void setModifyDateTime(Date modifyDateTime) {
		this.modifyDateTime = modifyDateTime;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public List<DepBaseComponent> getComponents() {
		return components;
	}

	public void setComponents(List<DepBaseComponent> components) {
		this.components = components;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepBaseProp other = (DepBaseProp) obj;
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

	public DepBaseProp jsonToObject(JSONObject jsonObject) {
		return DepBasePropJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return DepBasePropJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DepBasePropJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
