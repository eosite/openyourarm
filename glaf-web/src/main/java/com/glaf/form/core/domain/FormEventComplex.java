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
import com.glaf.form.core.util.FormEventComplexJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "FORM_EVENT_COMPLEX")
public class FormEventComplex implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected String id;

	/**
	 * 复合构件名称
	 */
	@Column(name = "NAME_", length = 50)
	protected String name;

	/**
	 * 备注
	 */
	@Column(name = "REMARK_", length = 400)
	protected String remark;

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
	 * 复合暴露接口规则
	 */
	@Lob
	@Column(name = "COMPLEX_RULE_")
	protected String complexRule;

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
	
	
	/**
	 * 视图类型  1 为列表  0或者null 为流程视图
	 */
	@Column(name = "VIEWTYPE_", length = 50)
	protected String viewType;
	
	
	/**
	 * 规则保存(列表模式)
	 */
	@Lob
	@Column(name = "TABLE_RULE_")
	protected String tableRule;
	
	/**
	 * 复合暴露接口规则(列表模式)
	 */
	@Lob
	@Column(name = "COMPLEX_TABLE_RULE_")
	protected String complexTableRule;

	public FormEventComplex() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
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

	public String getComplexRule() {
		return this.complexRule;
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

	public void setName(String name) {
		this.name = name;
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

	public void setComplexRule(String complexRule) {
		this.complexRule = complexRule;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormEventComplex other = (FormEventComplex) obj;
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

	public FormEventComplex jsonToObject(JSONObject jsonObject) {
		return FormEventComplexJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return FormEventComplexJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FormEventComplexJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public String getTableRule() {
		return tableRule;
	}

	public void setTableRule(String tableRule) {
		this.tableRule = tableRule;
	}

	public String getComplexTableRule() {
		return complexTableRule;
	}

	public void setComplexTableRule(String complexTableRule) {
		this.complexTableRule = complexTableRule;
	}

}
