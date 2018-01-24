package com.glaf.form.core.domain;

import java.io.Serializable;
import java.util.Date;

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
import com.glaf.form.core.util.FormEventJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "FORM_EVENT")
public class FormEvent implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected String id;

	/**
	 * 图形XML
	 */
	@Lob
	@Column(name = "DIAGRAM_")
	protected String diagram;

	/**
	 * 规则保存
	 */
	@Lob
	@Column(name = "RULE_")
	protected String rule;

	/**
	 * 页面ID
	 */
	@Column(name = "PAGEID_", length = 50)
	protected String pageId;

	/**
	 * 元素ID
	 */
	@Column(name = "ELEID_", length = 50)
	protected String eleId;

	/**
	 * 版本号
	 */
	@Column(name = "VERSION_", length = 10)
	protected String version;

	/**
	 * 创建日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	/**
	 * 修改人
	 */
	@Column(name = "UPDATEBY_", length = 50)
	protected String updateBy;

	/**
	 * 修改日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDATE_")
	protected Date updateDate;

	public FormEvent() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDiagram() {
		return this.diagram;
	}

	public String getRule() {
		return this.rule;
	}

	public String getPageId() {
		return this.pageId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getCreateDateString() {
		if (this.createDate != null) {
			return DateUtils.getDateTime(this.createDate);
		}
		return "";
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public String getUpdateDateString() {
		if (this.updateDate != null) {
			return DateUtils.getDateTime(this.updateDate);
		}
		return "";
	}

	public void setDiagram(String diagram) {
		this.diagram = diagram;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getEleId() {
		return eleId;
	}

	public void setEleId(String eleId) {
		this.eleId = eleId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormEvent other = (FormEvent) obj;
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

	public FormEvent jsonToObject(JSONObject jsonObject) {
		return FormEventJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return FormEventJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FormEventJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
