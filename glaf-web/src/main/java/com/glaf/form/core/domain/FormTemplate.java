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
@Table(name = "FORM_TEMPLATE")
public class FormTemplate implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Integer id;

	@Column(name = "NAME_", length = 50)
	protected String name;

	@Column(name = "COMPONENTID_")
	protected Integer componentId;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	@Column(name = "TYPE_", length = 50)
	protected String type;
	
	@Lob
	@Column(name = "TEMPLATE_")
	protected String template;
	
	@Lob
	@Column(name = "IMAGE_")
	protected byte[] image;

	@Column(name = "DELETEFLAG_")
	protected Integer deleteFlag;
	
	@javax.persistence.Transient
	protected Long image_exists;

	public FormTemplate() {

	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public Integer getComponentId() {
		return this.componentId;
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

	public String getType() {
		return this.type;
	}

	public String getTemplate() {
		return this.template;
	}
	
	public byte[] getImage() {
		return this.image;
	}

	public Integer getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setComponentId(Integer componentId) {
		this.componentId = componentId;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Long getImage_exists() {
		return image_exists;
	}

	public void setImage_exists(Long image_exists) {
		this.image_exists = image_exists;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormTemplate other = (FormTemplate) obj;
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

	public FormTemplate jsonToObject(JSONObject jsonObject) {
		return FormTemplateJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return FormTemplateJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FormTemplateJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
