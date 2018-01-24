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
import com.glaf.isdp.util.TreepNameJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "TREEPNAME")
public class TreepName implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "INDEX_ID", nullable = false)
	protected Integer indexId;

	@Column(name = "ID", length = 100)
	protected String id;

	@Column(name = "NUM", length = 50)
	protected String num;

	@Column(name = "PARENT_ID")
	protected Integer parentId;

	@Column(name = "INDEX_NAME", length = 255)
	protected String indexName;

	@Column(name = "NLEVEL")
	protected Integer Level;

	@Column(name = "SHOWID")
	protected Integer showId;

	@Column(name = "RULEID", length = 50)
	protected String ruleId;

	@Column(name = "NODEICO")
	protected Integer nodeIco;

	@Column(name = "FRULEID", length = 50)
	protected String fruleId;

	@Column(name = "WCOMPANY", length = 250)
	protected String wcompany;

	@Column(name = "LISTNO")
	protected Integer listNo;

	@Column(name = "SYS_ID", length = 50)
	protected String sysId;

	@Column(name = "DOMAIN_INDEX")
	protected Integer domainIndex;

	public TreepName() {

	}

	public Integer getIndexId() {
		return this.indexId;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public String getId() {
		return this.id;
	}

	public String getNum() {
		return this.num;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public String getIndexName() {
		return this.indexName;
	}

	public Integer getLevel() {
		return this.Level;
	}

	public Integer getShowId() {
		return this.showId;
	}

	public String getRuleId() {
		return this.ruleId;
	}

	public Integer getNodeIco() {
		return this.nodeIco;
	}

	public String getFruleId() {
		return this.fruleId;
	}

	public String getWcompany() {
		return this.wcompany;
	}

	public Integer getListNo() {
		return this.listNo;
	}

	public String getSysId() {
		return this.sysId;
	}

	public Integer getDomainIndex() {
		return this.domainIndex;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public void setLevel(Integer Level) {
		this.Level = Level;
	}

	public void setShowId(Integer showId) {
		this.showId = showId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public void setNodeIco(Integer nodeIco) {
		this.nodeIco = nodeIco;
	}

	public void setFruleId(String fruleId) {
		this.fruleId = fruleId;
	}

	public void setWcompany(String wcompany) {
		this.wcompany = wcompany;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public void setDomainIndex(Integer domainIndex) {
		this.domainIndex = domainIndex;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreepName other = (TreepName) obj;
		if (indexId == null) {
			if (other.indexId != null)
				return false;
		} else if (!indexId.equals(other.indexId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((indexId == null) ? 0 : indexId.hashCode());
		return result;
	}

	public TreepName jsonToObject(JSONObject jsonObject) {
		return TreepNameJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return TreepNameJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return TreepNameJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
