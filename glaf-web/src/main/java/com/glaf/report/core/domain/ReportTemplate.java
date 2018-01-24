package com.glaf.report.core.domain;

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
import com.glaf.report.core.util.ReportTemplateJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "REPORT_TEMPLATE")
public class ReportTemplate implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;

	/**
	 * REV_
	 */
	@Column(name = "REV_")
	protected Integer rev;

	/**
	 * NAME_
	 */
	@Column(name = "NAME_", length = 50)
	protected String name;

	/**
	 * CODE_
	 */
	@Column(name = "CODE_", length = 50)
	protected String code;

	/**
	 * BYTES_
	 */
	@Lob
	@Column(name = "BYTES_")
	protected byte[] bytes;

	/**
	 * CREATOR_
	 */
	@Column(name = "CREATOR_", length = 50)
	protected String creator;

	/**
	 * CREATEDATETIME_
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATETIME_")
	protected Date createDatatime;

	/**
	 * MODIFIER_
	 */
	@Column(name = "MODIFIER_", length = 50)
	protected String modifier;

	/**
	 * MODIFYDATETIME_
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFYDATETIME_")
	protected Date modifyDatatime;

	/**
	 * STATUS
	 */
	@Column(name = "STATUS")
	protected Integer status;

	/**
	 * PUBLISH_
	 */
	@Column(name = "PUBLISH_")
	protected Integer publish;

	/**
	 * PUBLISH_USER_
	 */
	@Column(name = "PUBLISH_USER_", length = 50)
	protected String publishUser;

	/**
	 * PUBLISHDATETIME
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PUBLISHDATETIME")
	protected Date publishDatetime;

	/**
	 * FILENAME_
	 */
	@Column(name = "FILENAME_", length = 100)
	protected String fileName;

	/**
	 * EXT_
	 */
	@Column(name = "EXT_", length = 10)
	protected String ext;

	@javax.persistence.Transient
	protected List<ReportTmpDataSet> reportTmpDataSets = new ArrayList<ReportTmpDataSet>();

	public ReportTemplate() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getRev() {
		return this.rev;
	}

	public String getName() {
		return this.name;
	}

	public String getCode() {
		return this.code;
	}

	public byte[] getBytes() {
		return this.bytes;
	}

	public String getCreator() {
		return this.creator;
	}

	public Date getCreateDatatime() {
		return this.createDatatime;
	}

	public String getCreateDatatimeString() {
		if (this.createDatatime != null) {
			return DateUtils.getDateTime(this.createDatatime);
		}
		return "";
	}

	public String getModifier() {
		return this.modifier;
	}

	public Date getModifyDatatime() {
		return this.modifyDatatime;
	}

	public String getModifyDatatimeString() {
		if (this.modifyDatatime != null) {
			return DateUtils.getDateTime(this.modifyDatatime);
		}
		return "";
	}

	public Integer getStatus() {
		return this.status;
	}

	public Integer getPublish() {
		return this.publish;
	}

	public String getPublishUser() {
		return this.publishUser;
	}

	public Date getPublishDatetime() {
		return this.publishDatetime;
	}

	public String getPublishDatetimeString() {
		if (this.publishDatetime != null) {
			return DateUtils.getDateTime(this.publishDatetime);
		}
		return "";
	}

	public String getFileName() {
		return this.fileName;
	}

	public String getExt() {
		return this.ext;
	}

	public void setRev(Integer rev) {
		this.rev = rev;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setCreateDatatime(Date createDatatime) {
		this.createDatatime = createDatatime;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public void setModifyDatatime(Date modifyDatatime) {
		this.modifyDatatime = modifyDatatime;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setPublish(Integer publish) {
		this.publish = publish;
	}

	public void setPublishUser(String publishUser) {
		this.publishUser = publishUser;
	}

	public void setPublishDatetime(Date publishDatetime) {
		this.publishDatetime = publishDatetime;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportTemplate other = (ReportTemplate) obj;
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

	@Override
	public ReportTemplate jsonToObject(JSONObject jsonObject) {
		return ReportTemplateJsonFactory.jsonToObject(jsonObject);
	}

	@Override
	public JSONObject toJsonObject() {
		return ReportTemplateJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return ReportTemplateJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public List<ReportTmpDataSet> getReportTmpDataSets() {
		return reportTmpDataSets;
	}

	public void setReportTmpDataSets(List<ReportTmpDataSet> reportTmpDataSets) {
		this.reportTmpDataSets = reportTmpDataSets;
	}

}
