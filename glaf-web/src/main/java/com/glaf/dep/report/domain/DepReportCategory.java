package com.glaf.dep.report.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.dep.report.util.*;

/**
 * 
 * 实体对象
 * 
 */

@Entity
@Table(name = "DEP_REPORT_CATEGORY")
public class DepReportCategory implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	/**
	 * 模板分类代码
	 */
	@Column(name = "CODE_", length = 30)
	protected String code;

	/**
	 * 模板名称
	 */
	@Column(name = "NAME_", length = 100)
	protected String name;

	/**
	 * 层级代码
	 */
	@Column(name = "TREEID_", length = 50)
	protected String treeId;

	/**
	 * 父分类唯一标识
	 */
	@Column(name = "PID_")
	protected Long pId;

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

	public DepReportCategory() {

	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	public String getTreeId() {
		return this.treeId;
	}

	public Long getPId() {
		return this.pId;
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

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public void setPId(Long pId) {
		this.pId = pId;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepReportCategory other = (DepReportCategory) obj;
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

	public DepReportCategory jsonToObject(JSONObject jsonObject) {
		return DepReportCategoryJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return DepReportCategoryJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DepReportCategoryJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
