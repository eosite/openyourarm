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
import com.glaf.isdp.util.TreeDotJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "TREEDOT")
public class TreeDot implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "INDEX_ID", nullable = false)
	protected Integer indexId;

	@Column(name = "ID", length = 100)
	protected String id;

	@Column(name = "PARENT_ID")
	protected Integer parentId;

	@Column(name = "INDEX_NAME", length = 255)
	protected String indexName;

	@Column(name = "NLEVEL")
	protected Integer nlevel;

	@Column(name = "NUM", length = 50)
	protected String num;

	@Column(name = "CONTENT", length = 255)
	protected String content;

	@Column(name = "SINDEX_NAME", length = 255)
	protected String sindexName;

	@Column(name = "NODEICO")
	protected Integer nodeIco;

	@Column(name = "LISTNO")
	protected Integer listNo;

	@Column(name = "MENUID")
	protected Integer menuId;

	@Column(name = "ISEND")
	protected Integer isEnd;

	@Column(name = "SYSMENUID", length = 500)
	protected String sysMenuId;

	@Column(name = "TYPE")
	protected Integer type;

	@Column(name = "FILENUMID", length = 50)
	protected String fileNumId;

	@Column(name = "FILENUMID2", length = 50)
	protected String fileNumId2;

	@Column(name = "PROJ_INDEX")
	protected Integer projIndex;

	@Column(name = "DOMAIN_INDEX")
	protected Integer domainIndex;
	
	@javax.persistence.Transient
	protected Integer childrenCount;

	public TreeDot() {

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

	public Integer getParentId() {
		return this.parentId;
	}

	public String getIndexName() {
		return this.indexName;
	}

	public Integer getNlevel() {
		return this.nlevel;
	}

	public String getNum() {
		return this.num;
	}

	public String getContent() {
		return this.content;
	}

	public String getSindexName() {
		return this.sindexName;
	}

	public Integer getNodeIco() {
		return this.nodeIco;
	}

	public Integer getListNo() {
		return this.listNo;
	}

	public Integer getMenuId() {
		return this.menuId;
	}

	public Integer getIsEnd() {
		return this.isEnd;
	}

	public String getSysMenuId() {
		return this.sysMenuId;
	}

	public Integer getType() {
		return this.type;
	}

	public String getFileNumId() {
		return this.fileNumId;
	}

	public String getFileNumId2() {
		return this.fileNumId2;
	}

	public Integer getProjIndex() {
		return this.projIndex;
	}

	public Integer getDomainIndex() {
		return this.domainIndex;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public void setNlevel(Integer nlevel) {
		this.nlevel = nlevel;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setSindexName(String sindexName) {
		this.sindexName = sindexName;
	}

	public void setNodeIco(Integer nodeIco) {
		this.nodeIco = nodeIco;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public void setIsEnd(Integer isEnd) {
		this.isEnd = isEnd;
	}

	public void setSysMenuId(String sysMenuId) {
		this.sysMenuId = sysMenuId;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setFileNumId(String fileNumId) {
		this.fileNumId = fileNumId;
	}

	public void setFileNumId2(String fileNumId2) {
		this.fileNumId2 = fileNumId2;
	}

	public void setProjIndex(Integer projIndex) {
		this.projIndex = projIndex;
	}

	public void setDomainIndex(Integer domainIndex) {
		this.domainIndex = domainIndex;
	}

	public Integer getChildrenCount() {
		return childrenCount;
	}

	public void setChildrenCount(Integer childrenCount) {
		this.childrenCount = childrenCount;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreeDot other = (TreeDot) obj;
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

	public TreeDot jsonToObject(JSONObject jsonObject) {
		return TreeDotJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return TreeDotJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return TreeDotJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
