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
import com.glaf.isdp.util.ProjCellAndFilesJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "PROJ_CELLANDFILES")
public class ProjCellAndFiles implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected String id;

	@Column(name = "INDEX_ID")
	protected Integer indexId;

	@Column(name = "INTTYPE")
	protected Integer intType;

	@Column(name = "NAME", length = 200)
	protected String name;

	@Column(name = "DEFID", length = 50)
	protected String defId;

	@Column(name = "USEID", length = 50)
	protected String useId;

	@Column(name = "INTPAGE0")
	protected Integer intPage0;

	@Column(name = "INTPAGE1")
	protected Integer intPage1;

	@Column(name = "INTPAGE2")
	protected Integer intPage2;

	@Column(name = "INTFINISH")
	protected Integer intFinish;
	@javax.persistence.Transient
	protected String fileDotNum;

	public ProjCellAndFiles() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getIndexId() {
		return this.indexId;
	}

	public Integer getIntType() {
		return this.intType;
	}

	public String getName() {
		return this.name;
	}

	public String getDefId() {
		return this.defId;
	}

	public String getUseId() {
		return this.useId;
	}

	public Integer getIntPage0() {
		return this.intPage0;
	}

	public Integer getIntPage1() {
		return this.intPage1;
	}

	public Integer getIntPage2() {
		return this.intPage2;
	}

	public Integer getIntFinish() {
		return this.intFinish;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public void setIntType(Integer intType) {
		this.intType = intType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDefId(String defId) {
		this.defId = defId;
	}

	public void setUseId(String useId) {
		this.useId = useId;
	}

	public void setIntPage0(Integer intPage0) {
		this.intPage0 = intPage0;
	}

	public void setIntPage1(Integer intPage1) {
		this.intPage1 = intPage1;
	}

	public void setIntPage2(Integer intPage2) {
		this.intPage2 = intPage2;
	}

	public void setIntFinish(Integer intFinish) {
		this.intFinish = intFinish;
	}

	public String getFileDotNum() {
		return fileDotNum;
	}

	public void setFileDotNum(String fileDotNum) {
		this.fileDotNum = fileDotNum;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjCellAndFiles other = (ProjCellAndFiles) obj;
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

	public ProjCellAndFiles jsonToObject(JSONObject jsonObject) {
		return ProjCellAndFilesJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return ProjCellAndFilesJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return ProjCellAndFilesJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
