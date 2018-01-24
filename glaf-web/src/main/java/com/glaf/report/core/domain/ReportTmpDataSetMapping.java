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
import com.glaf.report.core.util.ReportTmpDataSetMappingJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "REPORT_TMP_DATASET_MAPPING")
public class ReportTmpDataSetMapping implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;

	/**
	 * TMP_MAPPING_ID_
	 */
	@Column(name = "TMP_MAPPING_ID_", length = 64)
	protected String tmpMappingId;

	/**
	 * TEMPLATE_ID_
	 */
	@Column(name = "TEMPLATE_ID_", length = 64)
	protected String templateId;

	/**
	 * DATASET_CODE_
	 */
	@Column(name = "DATASET_CODE_", length = 50)
	protected String dataSetCode;

	/**
	 * DATASET_NAME_
	 */
	@Column(name = "DATASET_NAME_", length = 100)
	protected String dataSetName;

	/**
	 * MAPPING_DATASET_ID_
	 */
	@Column(name = "MAPPING_DATASET_ID_", length = 64)
	protected String mappingDataSetId;

	/**
	 * MAPPING_DATASET_CODE_
	 */
	@Column(name = "MAPPING_DATASET_CODE_", length = 64)
	protected String mappingDataSetCode;

	/**
	 * MAPPING_DATASET_NAME_
	 */
	@Column(name = "MAPPING_DATASET_NAME_", length = 150)
	protected String mappingDataSetName;

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
	protected List<ReportTmpColMapping> reportTmpColMappings = new ArrayList<ReportTmpColMapping>();

	public ReportTmpDataSetMapping() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTmpMappingId() {
		return this.tmpMappingId;
	}

	public String getTemplateId() {
		return this.templateId;
	}

	public String getDataSetCode() {
		return this.dataSetCode;
	}

	public String getDataSetName() {
		return this.dataSetName;
	}

	public String getMappingDataSetId() {
		return this.mappingDataSetId;
	}

	public String getMappingDataSetCode() {
		return this.mappingDataSetCode;
	}

	public String getMappingDataSetName() {
		return this.mappingDataSetName;
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

	public void setTmpMappingId(String tmpMappingId) {
		this.tmpMappingId = tmpMappingId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public void setDataSetCode(String dataSetCode) {
		this.dataSetCode = dataSetCode;
	}

	public void setDataSetName(String dataSetName) {
		this.dataSetName = dataSetName;
	}

	public void setMappingDataSetId(String mappingDataSetId) {
		this.mappingDataSetId = mappingDataSetId;
	}

	public void setMappingDataSetCode(String mappingDataSetCode) {
		this.mappingDataSetCode = mappingDataSetCode;
	}

	public void setMappingDataSetName(String mappingDataSetName) {
		this.mappingDataSetName = mappingDataSetName;
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
		ReportTmpDataSetMapping other = (ReportTmpDataSetMapping) obj;
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

	public ReportTmpDataSetMapping jsonToObject(JSONObject jsonObject) {
		return ReportTmpDataSetMappingJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return ReportTmpDataSetMappingJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return ReportTmpDataSetMappingJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public List<ReportTmpColMapping> getReportTmpColMappings() {
		return reportTmpColMappings;
	}

	public void setReportTmpColMappings(List<ReportTmpColMapping> reportTmpColMappings) {
		this.reportTmpColMappings = reportTmpColMappings;
	}

}
