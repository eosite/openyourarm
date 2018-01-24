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
import com.glaf.isdp.util.CellRepInfo2JsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "CELL_REPINFO2")
public class CellRepInfo2 implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected String id;

	@Column(name = "FRMTYPE", length = 50)
	protected String frmType;

	@Column(name = "TYPE")
	protected Integer type;

	@Lob
	@Column(name = "CONTENT")
	protected String content;

	@Lob
	@Column(name = "FORMULA")
	protected String formula;

	@Column(name = "OST_TABLENAME", length = 50)
	protected String ostTableName;

	@Column(name = "OST_ROW")
	protected Integer ostRow;

	@Column(name = "OST_COL")
	protected Integer ostCol;

	@Column(name = "OST_ROWEND")
	protected Integer ostRowEnd;

	@Column(name = "OST_COLEND")
	protected Integer ostColEnd;

	@Column(name = "OST_CELLID", length = 20)
	protected String ostCellId;

	@Column(name = "FILEDOT_FILEID", length = 50)
	protected String fileDotFileId;

	@Column(name = "OST_COLOR")
	protected Integer ostColor;

	@Column(name = "OST_WAY")
	protected Integer ostWay;

	@Column(name = "ROLE_ID")
	protected Integer roleId;

	@Column(name = "TABLENAME", length = 50)
	protected String tableName;

	@Column(name = "FNAME", length = 255)
	protected String fname;

	@Column(name = "DNAME", length = 50)
	protected String dname;

	@Column(name = "ISSUBTABLE", length = 1)
	protected String isSubTable;

	@Column(name = "TABLENAME2", length = 50)
	protected String tableName2;

	@Column(name = "INTAUTOINVALUE")
	protected Integer intAutoinValue;

	@Column(name = "INTSELFCLICK")
	protected Integer intSelfClick;

	public CellRepInfo2() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFrmType() {
		return this.frmType;
	}

	public Integer getType() {
		return this.type;
	}

	public String getContent() {
		return this.content;
	}

	public String getFormula() {
		return this.formula;
	}

	public String getOstTableName() {
		return this.ostTableName;
	}

	public Integer getOstRow() {
		return this.ostRow;
	}

	public Integer getOstCol() {
		return this.ostCol;
	}

	public Integer getOstRowEnd() {
		return this.ostRowEnd;
	}

	public Integer getOstColEnd() {
		return this.ostColEnd;
	}

	public String getOstCellId() {
		return this.ostCellId;
	}

	public String getFileDotFileId() {
		return this.fileDotFileId;
	}

	public Integer getOstColor() {
		return this.ostColor;
	}

	public Integer getOstWay() {
		return this.ostWay;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public String getTableName() {
		return this.tableName;
	}

	public String getFname() {
		return this.fname;
	}

	public String getDname() {
		return this.dname;
	}

	public String getIsSubTable() {
		return this.isSubTable;
	}

	public String getTableName2() {
		return this.tableName2;
	}

	public Integer getIntAutoinValue() {
		return this.intAutoinValue;
	}

	public Integer getIntSelfClick() {
		return this.intSelfClick;
	}

	public void setFrmType(String frmType) {
		this.frmType = frmType;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public void setOstTableName(String ostTableName) {
		this.ostTableName = ostTableName;
	}

	public void setOstRow(Integer ostRow) {
		this.ostRow = ostRow;
	}

	public void setOstCol(Integer ostCol) {
		this.ostCol = ostCol;
	}

	public void setOstRowEnd(Integer ostRowEnd) {
		this.ostRowEnd = ostRowEnd;
	}

	public void setOstColEnd(Integer ostColEnd) {
		this.ostColEnd = ostColEnd;
	}

	public void setOstCellId(String ostCellId) {
		this.ostCellId = ostCellId;
	}

	public void setFileDotFileId(String fileDotFileId) {
		this.fileDotFileId = fileDotFileId;
	}

	public void setOstColor(Integer ostColor) {
		this.ostColor = ostColor;
	}

	public void setOstWay(Integer ostWay) {
		this.ostWay = ostWay;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public void setIsSubTable(String isSubTable) {
		this.isSubTable = isSubTable;
	}

	public void setTableName2(String tableName2) {
		this.tableName2 = tableName2;
	}

	public void setIntAutoinValue(Integer intAutoinValue) {
		this.intAutoinValue = intAutoinValue;
	}

	public void setIntSelfClick(Integer intSelfClick) {
		this.intSelfClick = intSelfClick;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CellRepInfo2 other = (CellRepInfo2) obj;
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

	public CellRepInfo2 jsonToObject(JSONObject jsonObject) {
		return CellRepInfo2JsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return CellRepInfo2JsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return CellRepInfo2JsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
