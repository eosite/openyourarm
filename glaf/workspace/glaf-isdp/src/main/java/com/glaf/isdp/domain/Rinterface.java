package com.glaf.isdp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.util.DateUtils;
import com.glaf.isdp.util.RinterfaceJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "R_INTERFACE")
public class Rinterface implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	/**
	 * listId
	 */
	@Id
	@Column(name = "LISTID", length = 50)
	protected String listId;

	/**
	 * indexId
	 */
	@Column(name = "INDEX_ID", length = 100)
	protected String indexId;

	/**
	 * frmtype
	 */
	@Column(name = "FRMTYPE", length = 30)
	protected String frmtype;

	/**
	 * issystem
	 */
	@Column(name = "ISSYSTEM", length = 1)
	protected String issystem;

	/**
	 * fname
	 */
	@Column(name = "FNAME", length = 255)
	protected String fname;

	/**
	 * dname
	 */
	@Column(name = "DNAME", length = 100)
	protected String dname;

	/**
	 * dtype
	 */
	@Column(name = "DTYPE", length = 50)
	protected String dtype;

	/**
	 * showtype
	 */
	@Column(name = "SHOWTYPE", length = 50)
	protected String showtype;

	/**
	 * strlen
	 */
	@Column(name = "STRLEN")
	protected Integer strlen;

	/**
	 * form
	 */
	@Column(name = "FORM", length = 50)
	protected String form;

	/**
	 * intype
	 */
	@Column(name = "INTYPE", length = 10)
	protected String intype;

	/**
	 * hintID
	 */
	@Column(name = "HINTID", length = 50)
	protected String hintID;

	/**
	 * listno
	 */
	@Column(name = "LISTNO")
	protected Integer listno;

	/**
	 * ztype
	 */
	@Column(name = "ZTYPE", length = 1)
	protected String ztype;

	/**
	 * ismustfill
	 */
	@Column(name = "ISMUSTFILL", length = 1)
	protected String ismustfill;

	/**
	 * isListShow
	 */
	@Column(name = "ISLISTSHOW", length = 1)
	protected String isListShow;

	/**
	 * listweigth
	 */
	@Column(name = "LISTWEIGTH")
	protected Integer listweigth;

	/**
	 * isallwidth
	 */
	@Column(name = "ISALLWIDTH", length = 1)
	protected String isallwidth;

	/**
	 * istname
	 */
	@Column(name = "ISTNAME", length = 1)
	protected String istname;

	/**
	 * importType
	 */
	@Column(name = "IMPORT_TYPE")
	protected Integer importType;

	/**
	 * datapoint
	 */
	@Column(name = "DATAPOINT")
	protected Integer datapoint;

	/**
	 * createDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE")
	protected Date createDate;

	/**
	 * updateDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDATE")
	protected Date updateDate;

	/**
	 * createBy
	 */
	@Column(name = "CREATEBY", length = 50)
	protected String createBy;

	/**
	 * updateBy
	 */
	@Column(name = "UPDATEBY", length = 50)
	protected String updateBy;

	/**
	 * isPrimaryKey
	 */
	@Column(name = "ISPRIMARYKEY", length = 1)
	protected String isPrimaryKey;

	/**
	 * isGroupBy
	 */
	@Column(name = "ISGROUPBY", length = 1)
	protected String isGroupBy;

	public Rinterface() {

	}

	public String getIndexId() {
		return this.indexId;
	}

	public String getFrmtype() {
		return this.frmtype;
	}

	public String getListId() {
		return this.listId;
	}

	public String getIssystem() {
		return this.issystem;
	}

	public String getFname() {
		return this.fname;
	}

	public String getDname() {
		return this.dname;
	}

	public String getDtype() {
		return this.dtype;
	}

	public String getShowtype() {
		return this.showtype;
	}

	public Integer getStrlen() {
		return this.strlen;
	}

	public String getForm() {
		return this.form;
	}

	public String getIntype() {
		return this.intype;
	}

	public String getHintID() {
		return this.hintID;
	}

	public Integer getListno() {
		return this.listno;
	}

	public String getZtype() {
		return this.ztype;
	}

	public String getIsmustfill() {
		return this.ismustfill;
	}

	public String getIsListShow() {
		return this.isListShow;
	}

	public Integer getListweigth() {
		return this.listweigth;
	}

	public String getIsallwidth() {
		return this.isallwidth;
	}

	public String getIstname() {
		return this.istname;
	}

	public Integer getImportType() {
		return this.importType;
	}

	public Integer getDatapoint() {
		return this.datapoint;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getCreateDateString() {
		if (this.createDate != null) {
			return DateUtils.getDateTime(this.createDate);
		}
		return "";
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public String getUpdateDateString() {
		if (this.updateDate != null) {
			return DateUtils.getDateTime(this.updateDate);
		}
		return "";
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public String getIsPrimaryKey() {
		return this.isPrimaryKey;
	}

	public String getIsGroupBy() {
		return this.isGroupBy;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	public void setFrmtype(String frmtype) {
		this.frmtype = frmtype;
	}

	public void setListId(String listId) {
		this.listId = listId;
	}

	public void setIssystem(String issystem) {
		this.issystem = issystem;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public void setDtype(String dtype) {
		this.dtype = dtype;
	}

	public void setShowtype(String showtype) {
		this.showtype = showtype;
	}

	public void setStrlen(Integer strlen) {
		this.strlen = strlen;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public void setIntype(String intype) {
		this.intype = intype;
	}

	public void setHintID(String hintID) {
		this.hintID = hintID;
	}

	public void setListno(Integer listno) {
		this.listno = listno;
	}

	public void setZtype(String ztype) {
		this.ztype = ztype;
	}

	public void setIsmustfill(String ismustfill) {
		this.ismustfill = ismustfill;
	}

	public void setIsListShow(String isListShow) {
		this.isListShow = isListShow;
	}

	public void setListweigth(Integer listweigth) {
		this.listweigth = listweigth;
	}

	public void setIsallwidth(String isallwidth) {
		this.isallwidth = isallwidth;
	}

	public void setIstname(String istname) {
		this.istname = istname;
	}

	public void setImportType(Integer importType) {
		this.importType = importType;
	}

	public void setDatapoint(Integer datapoint) {
		this.datapoint = datapoint;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setIsPrimaryKey(String isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public void setIsGroupBy(String isGroupBy) {
		this.isGroupBy = isGroupBy;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rinterface other = (Rinterface) obj;
		if (listId == null) {
			if (other.listId != null)
				return false;
		} else if (!listId.equals(other.listId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listId == null) ? 0 : listId.hashCode());
		return result;
	}

	public Rinterface jsonToObject(JSONObject jsonObject) {
		return RinterfaceJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return RinterfaceJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return RinterfaceJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
