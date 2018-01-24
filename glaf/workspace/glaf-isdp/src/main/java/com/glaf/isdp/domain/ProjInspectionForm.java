package com.glaf.isdp.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.isdp.util.ProjInspectionFormJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "PROJ_INSPECTION_FORM")
public class ProjInspectionForm implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected String id;

	@Column(name = "PROJ_INSPECTION_ID", length = 50)
	protected String projInspectionId;

	@Column(name = "CELL_FORM_ID", length = 50)
	protected String cellFormId;

	@Column(name = "DOTUSE_ID", length = 50)
	protected String dotUseId;

	@Column(name = "LISTNO")
	protected Integer listNo;

	@Column(name = "INTISCHECK")
	protected Integer intIsCheck;

	@Column(name = "INTMUST")
	protected Integer intMust;

	public ProjInspectionForm() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjInspectionId() {
		return this.projInspectionId;
	}

	public String getCellFormId() {
		return this.cellFormId;
	}

	public String getDotUseId() {
		return this.dotUseId;
	}

	public Integer getListNo() {
		return this.listNo;
	}

	public Integer getIntIsCheck() {
		return this.intIsCheck;
	}

	public Integer getIntMust() {
		return this.intMust;
	}

	public void setProjInspectionId(String projInspectionId) {
		this.projInspectionId = projInspectionId;
	}

	public void setCellFormId(String cellFormId) {
		this.cellFormId = cellFormId;
	}

	public void setDotUseId(String dotUseId) {
		this.dotUseId = dotUseId;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public void setIntIsCheck(Integer intIsCheck) {
		this.intIsCheck = intIsCheck;
	}

	public void setIntMust(Integer intMust) {
		this.intMust = intMust;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjInspectionForm other = (ProjInspectionForm) obj;
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

	public ProjInspectionForm jsonToObject(JSONObject jsonObject) {
		return ProjInspectionFormJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return ProjInspectionFormJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return ProjInspectionFormJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
