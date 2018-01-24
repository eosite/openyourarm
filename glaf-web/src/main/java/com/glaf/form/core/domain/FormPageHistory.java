package com.glaf.form.core.domain;

import java.io.*;
import java.util.*;

import javax.persistence.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "FORM_PAGE_HISTORY")
public class FormPageHistory implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;

	/**
	 * 页面编号
	 */
	@Column(name = "PAGEID_", length = 50)
	protected String pageId;

	/**
	 * 部署编号
	 */
	@Column(name = "DEPLOYMENTID_", length = 100)
	protected String deploymentId;

	/**
	 * HTML
	 */
	@Lob
	@Column(name = "FORMHTML_", length = 2000)
	protected String formHtml;

	/**
	 * 配置
	 */
	@Lob
	@Column(name = "FORMCONFIG_", length = 2000)
	protected String formConfig;

	/**
	 * 输出HTML
	 */
	@Lob
	@Column(name = "OUTPUTHTML_", length = 2000)
	protected String outputHtml;

	/**
	 * 类型
	 */
	@Column(name = "FORMTYPE_", length = 50)
	protected String formType;

	/**
	 * version
	 */
	@Column(name = "VERSION_")
	protected Integer version;

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

	public FormPageHistory() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormPageHistory other = (FormPageHistory) obj;
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

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getCreateDateString() {
		if (this.createDate != null) {
			return DateUtils.getDateTime(this.createDate);
		}
		return "";
	}

	public String getDeploymentId() {
		return this.deploymentId;
	}

	public String getFormConfig() {
		return this.formConfig;
	}

	public String getFormHtml() {
		return this.formHtml;
	}

	public String getFormType() {
		return this.formType;
	}

	public String getId() {
		return this.id;
	}

	public String getOutputHtml() {
		return this.outputHtml;
	}

	public String getPageId() {
		return this.pageId;
	}

	public Integer getVersion() {
		return this.version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public FormPageHistory jsonToObject(JSONObject jsonObject) {
		return FormPageHistoryJsonFactory.jsonToObject(jsonObject);
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public void setFormConfig(String formConfig) {
		this.formConfig = formConfig;
	}

	public void setFormHtml(String formHtml) {
		this.formHtml = formHtml;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setOutputHtml(String outputHtml) {
		this.outputHtml = outputHtml;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public JSONObject toJsonObject() {
		return FormPageHistoryJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FormPageHistoryJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	@Lob
	@Column(name = "DESIGNERHTML_")
	protected String designerHtml;

	@Lob
	@Column(name = "DESIGNERJSON_")
	protected String designerJson;

	public String getDesignerHtml() {
		return designerHtml;
	}

	public void setDesignerHtml(String designerHtml) {
		this.designerHtml = designerHtml;
	}

	public String getDesignerJson() {
		return designerJson;
	}

	public void setDesignerJson(String designerJson) {
		this.designerJson = designerJson;
	}

	
}
