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
import com.glaf.isdp.util.TreeProjJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "TREEPROJ")
public class TreeProj implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "INDEX_ID", nullable = false)
	protected Integer indexId;

	@Column(name = "PROJTYPE", length = 1)
	protected String projType;

	@Column(name = "ID", length = 100)
	protected String id;

	@Column(name = "TOPID")
	protected Integer topId;

	@Column(name = "PARENT_ID")
	protected Integer parentId;

	@Column(name = "INDEX_NAME", length = 255)
	protected String indexName;

	@Column(name = "NLEVEL")
	protected Integer Level;

	@Column(name = "NUM", length = 255)
	protected String num;

	@Column(name = "CONTENT", length = 255)
	protected String content;

	@Column(name = "USEID", length = 50)
	protected String useId;

	@Column(name = "SINDEX_NAME", length = 255)
	protected String sindexName;

	@Column(name = "CONTENT2", length = 255)
	protected String content2;

	@Column(name = "TOPNODE", length = 255)
	protected String topNode;

	@Column(name = "NODEICO")
	protected Integer nodeIco;

	@Column(name = "UNITNUM", length = 50)
	protected String unitNum;

	@Column(name = "SHOWID")
	protected Integer showId;

	@Column(name = "SCALE_Q")
	protected Double scaleQ;

	@Column(name = "ISPEGWORK", length = 1)
	protected String isPegWork;

	@Column(name = "TREEPROJ_USER2", length = 1000)
	protected String treeProjUser2;

	public TreeProj() {

	}

	public Integer getIndexId() {
		return this.indexId;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public String getProjType() {
		return this.projType;
	}

	public String getId() {
		return this.id;
	}

	public Integer getTopId() {
		return this.topId;
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

	public String getNum() {
		return this.num;
	}

	public String getContent() {
		return this.content;
	}

	public String getUseId() {
		return this.useId;
	}

	public String getSindexName() {
		return this.sindexName;
	}

	public String getContent2() {
		return this.content2;
	}

	public String getTopNode() {
		return this.topNode;
	}

	public Integer getNodeIco() {
		return this.nodeIco;
	}

	public String getUnitNum() {
		return this.unitNum;
	}

	public Integer getShowId() {
		return this.showId;
	}

	public Double getScaleQ() {
		return this.scaleQ;
	}

	public String getIsPegWork() {
		return this.isPegWork;
	}

	public String getTreeProjUser2() {
		return this.treeProjUser2;
	}

	public void setProjType(String projType) {
		this.projType = projType;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTopId(Integer topId) {
		this.topId = topId;
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

	public void setNum(String num) {
		this.num = num;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setUseId(String useId) {
		this.useId = useId;
	}

	public void setSindexName(String sindexName) {
		this.sindexName = sindexName;
	}

	public void setContent2(String content2) {
		this.content2 = content2;
	}

	public void setTopNode(String topNode) {
		this.topNode = topNode;
	}

	public void setNodeIco(Integer nodeIco) {
		this.nodeIco = nodeIco;
	}

	public void setUnitNum(String unitNum) {
		this.unitNum = unitNum;
	}

	public void setShowId(Integer showId) {
		this.showId = showId;
	}

	public void setScaleQ(Double scaleQ) {
		this.scaleQ = scaleQ;
	}

	public void setIsPegWork(String isPegWork) {
		this.isPegWork = isPegWork;
	}

	public void setTreeProjUser2(String treeProjUser2) {
		this.treeProjUser2 = treeProjUser2;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreeProj other = (TreeProj) obj;
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

	public TreeProj jsonToObject(JSONObject jsonObject) {
		return TreeProjJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return TreeProjJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return TreeProjJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
