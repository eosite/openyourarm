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
@Table(name = "DEP_REPORT_TEMPLATE")
public class DepReportTemplate implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	/**
	 * 模板代码
	 */
	@Column(name = "CODE_", length = 50)
	protected String code;

	/**
	 * 模板名称
	 */
	@Column(name = "NAME_", length = 100)
	protected String name;

	/**
	 * 模板JSON
	 */
	@Lob
	@Column(name = "TMPJSON_")
	protected String tmpJson;

	/**
	 * 模板规则JSON
	 */
	@Lob
	@Column(name = "RULEJSON_")
	protected String ruleJson;

	/**
	 * 版本号
	 */
	@Column(name = "VER_")
	protected Integer ver;

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

	public DepReportTemplate() {

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

	public String getTmpJson() {
		return this.tmpJson;
	}

	public String getRuleJson() {
		return this.ruleJson;
	}

	public Integer getVer() {
		return this.ver;
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

	public void setTmpJson(String tmpJson) {
		this.tmpJson = tmpJson;
	}

	public void setRuleJson(String ruleJson) {
		this.ruleJson = ruleJson;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
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
		DepReportTemplate other = (DepReportTemplate) obj;
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

	public DepReportTemplate jsonToObject(JSONObject jsonObject) {
		return DepReportTemplateJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return DepReportTemplateJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DepReportTemplateJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
