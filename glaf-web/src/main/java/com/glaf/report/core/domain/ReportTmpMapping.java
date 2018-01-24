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
import com.glaf.report.core.util.ReportTmpMappingJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "REPORT_TMP_MAPPING")
public class ReportTmpMapping implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;

	/**
	 * SYSTEM_ID_
	 */
	@Column(name = "SYSTEM_ID_", length = 64)
	protected String systemId;

	/**
	 * TEMPLATE_ID_
	 */
	@Column(name = "TEMPLATE_ID_", length = 64)
	protected String templateId;

	/**
	 * TEMPLATE_CODE_
	 */
	@Column(name = "TEMPLATE_CODE_", length = 50)
	protected String templateCode;

	/**
	 * TEMPLATE_NAME_
	 */
	@Column(name = "TEMPLATE_NAME_", length = 100)
	protected String templateName;

	/**
	 * DESC_
	 */
	@Column(name = "DESC_", length = 150)
	protected String desc;

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

	/**
	 * DELETE_FLAG_
	 */
	@Column(name = "DELETE_FLAG_")
	protected Integer deleteFlag;

	@javax.persistence.Transient
	protected List<ReportTmpDataSetMapping> reportTmpDataSetMappings = new ArrayList<ReportTmpDataSetMapping>();

	public ReportTmpMapping() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSystemId() {
		return this.systemId;
	}

	public String getTemplateId() {
		return this.templateId;
	}

	public String getTemplateCode() {
		return this.templateCode;
	}

	public String getTemplateName() {
		return this.templateName;
	}

	public String getDesc() {
		return this.desc;
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

	public Integer getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportTmpMapping other = (ReportTmpMapping) obj;
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

	public ReportTmpMapping jsonToObject(JSONObject jsonObject) {
		return ReportTmpMappingJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return ReportTmpMappingJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return ReportTmpMappingJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public List<ReportTmpDataSetMapping> getReportTmpDataSetMappings() {
		return reportTmpDataSetMappings;
	}

	public void setReportTmpDataSetMappings(List<ReportTmpDataSetMapping> reportTmpDataSetMappings) {
		this.reportTmpDataSetMappings = reportTmpDataSetMappings;
	}

}
