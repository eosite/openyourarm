package com.glaf.convert.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.convert.util.*;

/**
 * 
 * 实体对象
 * 
 */

@Entity
@Table(name = "CVT_ELEM_TMPL")
public class ConvertElemTmpl implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CVT_ELEM_ID_", nullable = false)
	protected Long cvtElemId;

	/**
	 * cvtId
	 */
	@Column(name = "CVT_ID_")
	protected Long cvtId;

	/**
	 * elemType
	 */
	@Column(name = "ELEM_TYPE_", length = 20)
	protected String elemType;

	/**
	 * elemName
	 */
	@Column(name = "ELEM_NAME_", length = 50)
	protected String elemName;

	/**
	 * elemId
	 */
	@Column(name = "ELEM_ID_", length = 30)
	protected String elemId;

	/**
	 * dType
	 */
	@Column(name = "DTYPE_", length = 10)
	protected String dType;

	/**
	 * len
	 */
	@Column(name = "LEN_")
	protected Integer len;

	/**
	 * digit
	 */
	@Column(name = "DIGIT_")
	protected Integer digit;

	/**
	 * defaultVal
	 */
	@Column(name = "DEFAULT_VAL_", length = 50)
	protected String defaultVal;

	/**
	 * rowIndex
	 */
	@Column(name = "ROW_INDEX_")
	protected Integer rowIndex;

	/**
	 * colIndex
	 */
	@Column(name = "COL_INDEX_")
	protected Integer colIndex;

	/**
	 * readOnly
	 */
	@Column(name = "READONLY_", length = 1)
	protected String readOnly;

	/**
	 * print
	 */
	@Column(name = "PRINT_", length = 1)
	protected String print;

	/**
	 * display
	 */
	@Column(name = "DISPLAY_", length = 1)
	protected String display;

	/**
	 * isMustFill
	 */
	@Column(name = "ISMUSTFILL_", length = 1)
	protected String isMustFill;

	/**
	 * isDataOnly
	 */
	@Column(name = "ISDATAONLY_", length = 1)
	protected String isDataOnly;

	/**
	 * direction
	 */
	@Column(name = "DIRECTION_", length = 1)
	protected String direction;

	/**
	 * vararea
	 */
	@Column(name = "VAR_AREA_", length = 1)
	protected String vararea;

	/**
	 * endRowIndex
	 */
	@Column(name = "END_ROW_INDEX_")
	protected Integer endRowIndex;

	/**
	 * endColIndex
	 */
	@Column(name = "END_COL_INDEX_")
	protected Integer endColIndex;

	/**
	 * createDatetime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATETIME_")
	protected Date createDatetime;

	/**
	 * modifyDatetime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_DATETIME_")
	protected Date modifyDatetime;

	/**
	 * repinfoListId
	 */
	@Column(name = "REPINFO_LISTID_", length = 50)
	protected String repinfoListId;
	/**
	 * dataRole
	 */
	@Column(name = "DATAROLE_", length = 30)
	protected String dataRole;

	public ConvertElemTmpl() {

	}

	public Long getCvtElemId() {
		return this.cvtElemId;
	}

	public void setCvtElemId(Long cvtElemId) {
		this.cvtElemId = cvtElemId;
	}

	public Long getCvtId() {
		return this.cvtId;
	}

	public String getElemType() {
		return this.elemType;
	}

	public String getElemName() {
		return this.elemName;
	}

	public String getElemId() {
		return this.elemId;
	}

	public String getDType() {
		return this.dType;
	}

	public Integer getLen() {
		return this.len;
	}

	public Integer getDigit() {
		return this.digit;
	}

	public String getDefaultVal() {
		return this.defaultVal;
	}

	public Integer getRowIndex() {
		return this.rowIndex;
	}

	public Integer getColIndex() {
		return this.colIndex;
	}

	public String getReadOnly() {
		return this.readOnly;
	}

	public String getPrint() {
		return this.print;
	}

	public String getDisplay() {
		return this.display;
	}

	public String getIsMustFill() {
		return this.isMustFill;
	}

	public String getIsDataOnly() {
		return this.isDataOnly;
	}

	public String getDirection() {
		return this.direction;
	}

	public String getVararea() {
		return this.vararea;
	}

	public Integer getEndRowIndex() {
		return this.endRowIndex;
	}

	public Integer getEndColIndex() {
		return this.endColIndex;
	}

	public Date getCreateDatetime() {
		return this.createDatetime;
	}

	public String getCreateDatetimeString() {
		if (this.createDatetime != null) {
			return DateUtils.getDateTime(this.createDatetime);
		}
		return "";
	}

	public Date getModifyDatetime() {
		return this.modifyDatetime;
	}

	public String getModifyDatetimeString() {
		if (this.modifyDatetime != null) {
			return DateUtils.getDateTime(this.modifyDatetime);
		}
		return "";
	}

	public String getRepinfoListId() {
		return this.repinfoListId;
	}

	public void setCvtId(Long cvtId) {
		this.cvtId = cvtId;
	}

	public void setElemType(String elemType) {
		this.elemType = elemType;
	}

	public void setElemName(String elemName) {
		this.elemName = elemName;
	}

	public void setElemId(String elemId) {
		this.elemId = elemId;
	}

	public void setDType(String dType) {
		this.dType = dType;
	}

	public void setLen(Integer len) {
		this.len = len;
	}

	public void setDigit(Integer digit) {
		this.digit = digit;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}

	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}

	public void setColIndex(Integer colIndex) {
		this.colIndex = colIndex;
	}

	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	public void setPrint(String print) {
		this.print = print;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public void setIsMustFill(String isMustFill) {
		this.isMustFill = isMustFill;
	}

	public void setIsDataOnly(String isDataOnly) {
		this.isDataOnly = isDataOnly;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void setVararea(String vararea) {
		this.vararea = vararea;
	}

	public void setEndRowIndex(Integer endRowIndex) {
		this.endRowIndex = endRowIndex;
	}

	public void setEndColIndex(Integer endColIndex) {
		this.endColIndex = endColIndex;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public void setModifyDatetime(Date modifyDatetime) {
		this.modifyDatetime = modifyDatetime;
	}

	public void setRepinfoListId(String repinfoListId) {
		this.repinfoListId = repinfoListId;
	}

	public String getDataRole() {
		return dataRole;
	}

	public void setDataRole(String dataRole) {
		this.dataRole = dataRole;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConvertElemTmpl other = (ConvertElemTmpl) obj;
		if (cvtElemId == null) {
			if (other.cvtElemId != null)
				return false;
		} else if (!cvtElemId.equals(other.cvtElemId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cvtElemId == null) ? 0 : cvtElemId.hashCode());
		return result;
	}

	public ConvertElemTmpl jsonToObject(JSONObject jsonObject) {
		return ConvertElemTmplJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return ConvertElemTmplJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return ConvertElemTmplJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
