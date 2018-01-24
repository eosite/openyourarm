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
import com.glaf.isdp.util.CellUTableTreeJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "CELL_UTABLETREE")
public class CellUTableTree implements Serializable, JSONable {
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

	@Column(name = "NODEICO")
	protected Integer nodeIco;

	@Column(name = "LISTNO")
	protected Integer listNo;

	@Column(name = "TABLETYPE")
	protected Integer tableType;

	@Column(name = "INTDEL")
	protected Integer intDel;

	@Column(name = "BUSIESS_ID", length = 50)
	protected String busiessId;

	@Column(name = "CONTENT", length = 250)
	protected String content;

	@Column(name = "NUM", length = 50)
	protected String num;

	@Column(name = "MENUINDEX")
	protected Integer menuIndex;

	@Column(name = "DOMAIN_INDEX")
	protected Integer domainIndex;

	@Column(name = "WIN_WIDTH")
	protected Integer winWidth;

	@Column(name = "WIN_HEIGHT")
	protected Integer winHeight;
	
	@javax.persistence.Transient
	protected Integer childrenCount;

	public CellUTableTree() {

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

	public Integer getNodeIco() {
		return this.nodeIco;
	}

	public Integer getListNo() {
		return this.listNo;
	}

	public Integer getTableType() {
		return this.tableType;
	}

	public Integer getIntDel() {
		return this.intDel;
	}

	public String getBusiessId() {
		return this.busiessId;
	}

	public String getContent() {
		return this.content;
	}

	public String getNum() {
		return this.num;
	}

	public Integer getMenuIndex() {
		return this.menuIndex;
	}

	public Integer getDomainIndex() {
		return this.domainIndex;
	}

	public Integer getWinWidth() {
		return this.winWidth;
	}

	public Integer getWinHeight() {
		return this.winHeight;
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

	public void setNodeIco(Integer nodeIco) {
		this.nodeIco = nodeIco;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public void setTableType(Integer tableType) {
		this.tableType = tableType;
	}

	public void setIntDel(Integer intDel) {
		this.intDel = intDel;
	}

	public void setBusiessId(String busiessId) {
		this.busiessId = busiessId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public void setMenuIndex(Integer menuIndex) {
		this.menuIndex = menuIndex;
	}

	public void setDomainIndex(Integer domainIndex) {
		this.domainIndex = domainIndex;
	}

	public void setWinWidth(Integer winWidth) {
		this.winWidth = winWidth;
	}

	public void setWinHeight(Integer winHeight) {
		this.winHeight = winHeight;
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
		CellUTableTree other = (CellUTableTree) obj;
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

	public CellUTableTree jsonToObject(JSONObject jsonObject) {
		return CellUTableTreeJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return CellUTableTreeJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return CellUTableTreeJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
