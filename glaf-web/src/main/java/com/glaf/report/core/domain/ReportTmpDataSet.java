package com.glaf.report.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.util.DateUtils;
import com.glaf.report.core.util.ReportTmpDataSetJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "REPORT_TMP_DATASET")
public class ReportTmpDataSet implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;

	/**
	 * TEMPLATE_ID_
	 */
	@Column(name = "TEMPLATE_ID_", length = 64)
	protected String templateId;

	/**
	 * CODE_
	 */
	@Column(name = "CODE_", length = 50)
	protected String code;

	/**
	 * NAME_
	 */
	@Column(name = "NAME_", length = 100)
	protected String name;

	/**
	 * TITLE_
	 */
	@Column(name = "TITLE_", length = 100)
	protected String title;

	/**
	 * TYPE_
	 */
	@Column(name = "TYPE_", length = 20)
	protected String type;

	/**
	 * CREATOR_
	 */
	@Column(name = "CREATOR_", length = 20)
	protected String creator;

	/**
	 * CREATEDATETIME_
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATETIME_")
	protected Date createDatetime;

	/**
	 * MODIFIER_
	 */
	@Column(name = "MODIFIER_", length = 20)
	protected String modifier;

	/**
	 * MODIFYDATETIME_
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFYDATETIME_")
	protected Date modifyDatetime;

	@javax.persistence.Transient
	protected List<ReportTmpCol> reportTmpCols = new ArrayList<ReportTmpCol>();

	public ReportTmpDataSet() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTemplateId() {
		return this.templateId;
	}

	public String getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	public String getTitle() {
		return this.title;
	}

	public String getType() {
		return this.type;
	}

	public String getCreator() {
		return this.creator;
	}

	public Date getCreateDatetime() {
		return this.createDatetime;
	}

	public String getCreateDatetimeString() {
		if (this.createDatetime != null) {
			return DateUtils.getDateTime(this.createDatetime);
		}
		return "";
	}

	public String getModifier() {
		return this.modifier;
	}

	public Date getModifyDatetime() {
		return this.modifyDatetime;
	}

	public String getModifyDatetimeString() {
		if (this.modifyDatetime != null) {
			return DateUtils.getDateTime(this.modifyDatetime);
		}
		return "";
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public void setModifyDatetime(Date modifyDatetime) {
		this.modifyDatetime = modifyDatetime;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportTmpDataSet other = (ReportTmpDataSet) obj;
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

	public ReportTmpDataSet jsonToObject(JSONObject jsonObject) {
		return ReportTmpDataSetJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return ReportTmpDataSetJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return ReportTmpDataSetJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public List<ReportTmpCol> getReportTmpCols() {
		return reportTmpCols;
	}

	public void setReportTmpCols(List<ReportTmpCol> reportTmpCols) {
		this.reportTmpCols = reportTmpCols;
	}

}
