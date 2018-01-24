package com.glaf.dep.base.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.util.DateUtils;
import com.glaf.dep.base.util.DepBaseCategoryJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "DEP_BASE_CATEGORY")
public class DepBaseCategory implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	/**
	 * 类型代码
	 */
	@Column(name = "CODE_", length = 50)
	protected String code;

	/**
	 * 类型名称
	 */
	@Column(name = "NAME_", length = 50)
	protected String name;

	/**
	 * 树形结构编码
	 */
	@Column(name = "TREEID_", length = 100)
	protected String treeId;

	/**
	 * 默认展开
	 */
	@Column(name = "EXPANDFLAG_", length = 1)
	protected String expandFlag;

	/**
	 * 父分类标识
	 */
	@Column(name = "PID_")
	protected Long pid;

	/**
	 * 排序号
	 */
	@Column(name = "ORDERNO_")
	protected Integer orderNo;

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
	
	@Lob
	@Column(name="TOOLBAR_TEMPLATE_")
	protected String toolBarTemplate;
	
	@javax.persistence.Transient
	protected Integer childrenNum;
	
	@javax.persistence.Transient
	protected List<DepBaseCategory> childrens = new ArrayList<DepBaseCategory>();

	public DepBaseCategory() {

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

	public String getExpandFlag() {
		return this.expandFlag;
	}

	public Long getPid() {
		return this.pid;
	}

	public Integer getOrderNo() {
		return this.orderNo;
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

	public void setExpandFlag(String expandFlag) {
		this.expandFlag = expandFlag;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
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

	public Integer getChildrenNum() {
		return childrenNum;
	}

	public void setChildrenNum(Integer childrenNum) {
		this.childrenNum = childrenNum;
	}

	public List<DepBaseCategory> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<DepBaseCategory> childrens) {
		this.childrens = childrens;
	}

	public String getToolBarTemplate() {
		return toolBarTemplate;
	}

	public void setToolBarTemplate(String toolBarTemplate) {
		this.toolBarTemplate = toolBarTemplate;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepBaseCategory other = (DepBaseCategory) obj;
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

	public DepBaseCategory jsonToObject(JSONObject jsonObject) {
		return DepBaseCategoryJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return DepBaseCategoryJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DepBaseCategoryJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
