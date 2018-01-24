package com.glaf.report.core.domain;

import java.io.Serializable;
import java.util.Date;

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
import com.glaf.report.core.util.SysReportTemplateJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "SYSREPORTTEMPLATE")
public class SysReportTemplate implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected String id;

	/**
	 * 报表模板名称
	 */
	@Column(name = "REPORT_TEMPLATE_NAME", length = 100)
	protected String reportTemplateName;

	/**
	 * 报表文件
	 */
	@Column(name = "REPORT_TEMPLATE_FILE")
	protected byte[] reportTemplateFile;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CTIME")
	protected Date ctime;

	/**
	 * 更新时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UTIME")
	protected Date utime;

	public SysReportTemplate() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReportTemplateName() {
		return this.reportTemplateName;
	}

	public byte[] getReportTemplateFile() {
		return this.reportTemplateFile;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public String getCtimeString() {
		if (this.ctime != null) {
			return DateUtils.getDateTime(this.ctime);
		}
		return "";
	}

	public Date getUtime() {
		return this.utime;
	}

	public String getUtimeString() {
		if (this.utime != null) {
			return DateUtils.getDateTime(this.utime);
		}
		return "";
	}

	public void setReportTemplateName(String reportTemplateName) {
		this.reportTemplateName = reportTemplateName;
	}

	public void setReportTemplateFile(byte[] reportTemplateFile) {
		this.reportTemplateFile = reportTemplateFile;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysReportTemplate other = (SysReportTemplate) obj;
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

	public SysReportTemplate jsonToObject(JSONObject jsonObject) {
		return SysReportTemplateJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return SysReportTemplateJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SysReportTemplateJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
