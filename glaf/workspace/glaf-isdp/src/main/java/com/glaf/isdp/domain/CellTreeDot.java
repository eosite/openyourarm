package com.glaf.isdp.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.isdp.util.CellTreeDotJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "CELL_TREEDOT")
public class CellTreeDot implements Serializable, JSONable {
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
	protected Integer Level;

	@Column(name = "NUM", length = 50)
	protected String num;

	@Column(name = "CONTENT", length = 255)
	protected String content;

	@Column(name = "NODEICO")
	protected Integer nodeIco;

	@Column(name = "SINDEX_NAME", length = 255)
	protected String sindexName;

	@Column(name = "LISTNO")
	protected Integer listNo;

	@Column(name = "VIEWTYPE")
	protected Integer viewType;

	@Column(name = "ISMODE", length = 1)
	protected String isMode;

	@Column(name = "MODETABLE_ID", length = 50)
	protected String modeTableId;

	@Column(name = "ISSYSTEM")
	protected Integer isSystem;

	@Column(name = "CUSTOMDATA", length = 50)
	protected String customData;

	@Column(name = "INTSYSTEMSELECT")
	protected Integer intSystemSelect;

	@Column(name = "INTUSED")
	protected Integer intUsed;

	@Column(name = "INTDEL")
	protected Integer intDel;

	@Column(name = "TYPE_TABLENAME", length = 50)
	protected String typeTableName;

	@Column(name = "INTOPERATION")
	protected Integer intOperation;

	@Column(name = "PICFILE", length = 250)
	protected String picFile;

	@Lob
	@Column(name = "FILE_CONTENT")
	protected byte[] fileContent;

	@Column(name = "INTMUIFRM")
	protected Integer intMuiFrm;

	@Column(name = "INTNOSHOW")
	protected Integer intNoShow;

	@Column(name = "TYPE_BASETABLE", length = 50)
	protected String typeBaseTable;

	@Column(name = "TYPE_INDEX")
	protected Integer typeIndex;

	@Column(name = "GID", length = 50)
	protected String gid;

	@Column(name = "FILE_NAME", length = 255)
	protected String fileName;

	@Lob
	@Column(name = "LINK_FILE_CONTENT")
	protected String linkFileContent;

	@Column(name = "LINK_FILE_NAME", length = 255)
	protected String linkFileName;

	public CellTreeDot() {

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

	public Integer getLevel() {
		return this.Level;
	}

	public String getNum() {
		return this.num;
	}

	public String getContent() {
		return this.content;
	}

	public Integer getNodeIco() {
		return this.nodeIco;
	}

	public String getSindexName() {
		return this.sindexName;
	}

	public Integer getListNo() {
		return this.listNo;
	}

	public Integer getViewType() {
		return this.viewType;
	}

	public String getIsMode() {
		return this.isMode;
	}

	public String getModeTableId() {
		return this.modeTableId;
	}

	public Integer getIsSystem() {
		return this.isSystem;
	}

	public String getCustomData() {
		return this.customData;
	}

	public Integer getIntSystemSelect() {
		return this.intSystemSelect;
	}

	public Integer getIntUsed() {
		return this.intUsed;
	}

	public Integer getIntDel() {
		return this.intDel;
	}

	public String getTypeTableName() {
		return this.typeTableName;
	}

	public Integer getIntOperation() {
		return this.intOperation;
	}

	public String getPicFile() {
		return this.picFile;
	}

	public byte[] getFileContent() {
		return this.fileContent;
	}

	public Integer getIntMuiFrm() {
		return this.intMuiFrm;
	}

	public Integer getIntNoShow() {
		return this.intNoShow;
	}

	public String getTypeBaseTable() {
		return this.typeBaseTable;
	}

	public Integer getTypeIndex() {
		return this.typeIndex;
	}

	public String getGid() {
		return this.gid;
	}

	public String getFileName() {
		return this.fileName;
	}

	public String getLinkFileContent() {
		return this.linkFileContent;
	}

	public String getLinkFileName() {
		return this.linkFileName;
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

	public void setLevel(Integer Level) {
		this.Level = Level;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setNodeIco(Integer nodeIco) {
		this.nodeIco = nodeIco;
	}

	public void setSindexName(String sindexName) {
		this.sindexName = sindexName;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public void setViewType(Integer viewType) {
		this.viewType = viewType;
	}

	public void setIsMode(String isMode) {
		this.isMode = isMode;
	}

	public void setModeTableId(String modeTableId) {
		this.modeTableId = modeTableId;
	}

	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}

	public void setCustomData(String customData) {
		this.customData = customData;
	}

	public void setIntSystemSelect(Integer intSystemSelect) {
		this.intSystemSelect = intSystemSelect;
	}

	public void setIntUsed(Integer intUsed) {
		this.intUsed = intUsed;
	}

	public void setIntDel(Integer intDel) {
		this.intDel = intDel;
	}

	public void setTypeTableName(String typeTableName) {
		this.typeTableName = typeTableName;
	}

	public void setIntOperation(Integer intOperation) {
		this.intOperation = intOperation;
	}

	public void setPicFile(String picFile) {
		this.picFile = picFile;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public void setIntMuiFrm(Integer intMuiFrm) {
		this.intMuiFrm = intMuiFrm;
	}

	public void setIntNoShow(Integer intNoShow) {
		this.intNoShow = intNoShow;
	}

	public void setTypeBaseTable(String typeBaseTable) {
		this.typeBaseTable = typeBaseTable;
	}

	public void setTypeIndex(Integer typeIndex) {
		this.typeIndex = typeIndex;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setLinkFileContent(String linkFileContent) {
		this.linkFileContent = linkFileContent;
	}

	public void setLinkFileName(String linkFileName) {
		this.linkFileName = linkFileName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CellTreeDot other = (CellTreeDot) obj;
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

	public CellTreeDot jsonToObject(JSONObject jsonObject) {
		return CellTreeDotJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return CellTreeDotJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return CellTreeDotJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
