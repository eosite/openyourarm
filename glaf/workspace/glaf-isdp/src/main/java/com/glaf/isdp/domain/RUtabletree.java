package com.glaf.isdp.domain;

import java.io.*;

import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;

import com.glaf.isdp.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "R_UTABLETREE")
public class RUtabletree implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "INDEX_ID", nullable = false)
	protected Integer indexId;

	/**
	 * id
	 */
	@Column(name = "ID", length = 100)
	protected String id;

	/**
	 * parentId
	 */
	@Column(name = "PARENT_ID")
	protected Integer parentId;

	/**
	 * indexName
	 */
	@Column(name = "INDEX_NAME", length = 255)
	protected String indexName;

	/**
	 * nlevel
	 */
	@Column(name = "NLEVEL")
	protected Integer nlevel;

	/**
	 * nodeico
	 */
	@Column(name = "NODEICO")
	protected Integer nodeico;

	/**
	 * listno
	 */
	@Column(name = "LISTNO")
	protected Integer listno;

	/**
	 * tabletype
	 */
	@Column(name = "TABLETYPE")
	protected Integer tabletype;

	/**
	 * intdel
	 */
	@Column(name = "INTDEL")
	protected Integer intdel;

	/**
	 * busiessId
	 */
	@Column(name = "BUSIESS_ID", length = 50)
	protected String busiessId;

	/**
	 * content
	 */
	@Column(name = "CONTENT", length = 250)
	protected String content;

	/**
	 * num
	 */
	@Column(name = "NUM", length = 50)
	protected String num;

	/**
	 * menuindex
	 */
	@Column(name = "MENUINDEX")
	protected Integer menuindex;

	/**
	 * domainIndex
	 */
	@Column(name = "DOMAIN_INDEX")
	protected Integer domainIndex;

	/**
	 * winWidth
	 */
	@Column(name = "WIN_WIDTH")
	protected Integer winWidth;

	/**
	 * winHeight
	 */
	@Column(name = "WIN_HEIGHT")
	protected Integer winHeight;

	public RUtabletree() {

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

	public Integer getNodeico() {
		return this.nodeico;
	}

	public Integer getListno() {
		return this.listno;
	}

	public Integer getTabletype() {
		return this.tabletype;
	}

	public Integer getIntdel() {
		return this.intdel;
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

	public Integer getMenuindex() {
		return this.menuindex;
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

	public void setNodeico(Integer nodeico) {
		this.nodeico = nodeico;
	}

	public void setListno(Integer listno) {
		this.listno = listno;
	}

	public void setTabletype(Integer tabletype) {
		this.tabletype = tabletype;
	}

	public void setIntdel(Integer intdel) {
		this.intdel = intdel;
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

	public void setMenuindex(Integer menuindex) {
		this.menuindex = menuindex;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RUtabletree other = (RUtabletree) obj;
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

	public RUtabletree jsonToObject(JSONObject jsonObject) {
		return RUtabletreeJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return RUtabletreeJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return RUtabletreeJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
