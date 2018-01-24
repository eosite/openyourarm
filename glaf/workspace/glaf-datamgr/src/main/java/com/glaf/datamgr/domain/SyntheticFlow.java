package com.glaf.datamgr.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.datamgr.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "SYS_SYNTHETIC_FLOW")
public class SyntheticFlow implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	/**
	 * 当前步骤
	 */
	@Column(name = "CURRENTSTEP_", length = 200)
	protected String currentStep;

	/**
	 * 当前类型
	 */
	@Column(name = "CURRENTTYPE_", length = 50)
	protected String currentType;

	/**
	 * 前一步骤
	 */
	@Column(name = "PREVIOUSSTEP_", length = 200)
	protected String previousStep;

	/**
	 * 前一步骤类型
	 */
	@Column(name = "PREVIOUSTYPE_", length = 50)
	protected String previousType;

	/**
	 * 后一步骤
	 */
	@Column(name = "NEXTSTEP_", length = 200)
	protected String nextStep;

	/**
	 * 后一步骤类型
	 */
	@Column(name = "NEXTTYPE_", length = 50)
	protected String nextType;

	/**
	 * 顺序
	 */
	@Column(name = "SORT_")
	protected int sort;

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

	public SyntheticFlow() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SyntheticFlow other = (SyntheticFlow) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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

	public String getCurrentStep() {
		return this.currentStep;
	}

	public String getCurrentType() {
		return this.currentType;
	}

	public Long getId() {
		return this.id;
	}

	public String getNextStep() {
		return this.nextStep;
	}

	public String getNextType() {
		return this.nextType;
	}

	public String getPreviousStep() {
		return this.previousStep;
	}

	public String getPreviousType() {
		return this.previousType;
	}

	public int getSort() {
		return this.sort;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public SyntheticFlow jsonToObject(JSONObject jsonObject) {
		return SyntheticFlowJsonFactory.jsonToObject(jsonObject);
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setCurrentStep(String currentStep) {
		this.currentStep = currentStep;
	}

	public void setCurrentType(String currentType) {
		this.currentType = currentType;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNextStep(String nextStep) {
		this.nextStep = nextStep;
	}

	public void setNextType(String nextType) {
		this.nextType = nextType;
	}

	public void setPreviousStep(String previousStep) {
		this.previousStep = previousStep;
	}

	public void setPreviousType(String previousType) {
		this.previousType = previousType;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public JSONObject toJsonObject() {
		return SyntheticFlowJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SyntheticFlowJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
