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
import com.glaf.isdp.util.CellRepInfoJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "CELL_REPINFO")
public class CellRepInfo implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "LISTID", nullable = false)
	protected String listId;

	@Column(name = "INDEX_ID", length = 100)
	protected String indexId;

	@Column(name = "FRMTYPE", length = 50)
	protected String frmType;

	@Column(name = "ISSYSTEM", length = 1)
	protected String isSystem;

	@Column(name = "FNAME", length = 255)
	protected String fname;//中文名称

	@Column(name = "DNAME", length = 50)
	protected String dname;//数据表字段名称

	@Column(name = "DTYPE", length = 50)
	protected String dtype;//数据类型 i4:整型;r8:浮点数;datetime:日期;image:二进制类型;其他:字符串

	@Column(name = "SHOWTYPE", length = 50)
	protected String showType;

	@Column(name = "STRLEN")
	protected Integer strLen;//长度

	@Column(name = "FORM", length = 50)
	protected String form;

	@Column(name = "INTYPE", length = 10)
	protected String inType;

	@Column(name = "HINTID", length = 50)
	protected String hintID;

	@Column(name = "LISTNO")
	protected Integer listNo;

	@Column(name = "ZTYPE", length = 1)
	protected String ztype;

	@Column(name = "ISMUSTFILL", length = 1)
	protected String isMustFill;

	@Column(name = "ISLISTSHOW", length = 1)
	protected String isListShow;

	@Column(name = "LISTWEIGTH")
	protected Integer listWeigth;

	@Column(name = "PANID", length = 50)
	protected String panid;

	@Column(name = "ISALLWIDTH", length = 1)
	protected String isAllWidth;

	@Column(name = "ISTNAME", length = 1)
	protected String istName;

	@Column(name = "FILEDOT_FILEID", length = 50)
	protected String fileDotFileId;

	@Column(name = "DATAPOINT")
	protected Integer dataPoint;

	@Column(name = "DEFAULTVALUE", length = 50)
	protected String defaultValue;

	@Column(name = "ISSUBTABLE", length = 1)
	protected String isSubTable;

	@Column(name = "ISDATAONLY", length = 1)
	protected String isDataOnly;

	@Column(name = "CHECKTYPE")
	protected Integer checkType;

	@Column(name = "ORDERID", length = 50)
	protected String orderId;

	@Column(name = "CELLTYPE")
	protected Integer cellType;

	@Column(name = "OST_TABLENAME", length = 50)
	protected String ostTableName;

	@Column(name = "OST_ROW")
	protected Integer ostRow;

	@Column(name = "OST_COL")
	protected Integer ostCol;

	@Column(name = "HINTLIST", length = 250)
	protected String hintList;

	@Column(name = "OST_CELLID", length = 20)
	protected String ostCellId;

	@Column(name = "OLDDNAME", length = 50)
	protected String oldDName;

	@Column(name = "OST_CELLNAME", length = 20)
	protected String ostCellName;

	@Column(name = "OST_FORMULA", length = 200)
	protected String ostFormula;

	@Column(name = "OST_COLOR")
	protected Integer ostColor;

	@Column(name = "OST_SUMTYPE")
	protected Integer ostSumType;

	@Column(name = "DATA_TABLENAME", length = 50)
	protected String dataTableName;

	@Column(name = "DATA_FIELDNAME", length = 50)
	protected String dataFieldName;

	@Column(name = "ORDER_CON")
	protected Integer orderCon;

	@Column(name = "CONNUM")
	protected Integer conNum;

	@Column(name = "NODE_INDEX")
	protected Integer nodeIndex;

	@Column(name = "TYPE_ID", length = 50)
	protected String typeId;

	@Column(name = "USERINDEX", length = 50)
	protected String userIndex;

	@Column(name = "ORDER_INDEX")
	protected Integer orderIndex;

	@Column(name = "PARENT_DNAME", length = 50)
	protected String parentDName;

	@Column(name = "SYSAUTO")
	protected Integer sysAuto;

	@Column(name = "ORDER_INDEX_F")
	protected Integer orderIndexF;

	@Column(name = "ORDERID_F", length = 50)
	protected String orderIdF;

	@Column(name = "ORDER_CON_F")
	protected Integer orderConF;

	@Column(name = "ISPRINTVALUE")
	protected Integer isPrintValue;

	@Column(name = "ISSHOWVALUEONLAST")
	protected Integer isShowValueOnLast;

	@Column(name = "ISBANKROUNDTYPE")
	protected Integer isBankRoundType;

	@Column(name = "FLOATBANKROUND")
	protected Double floatBankRound;

	@Column(name = "INTUSEWBSPLACE")
	protected Integer intUseWBSPlace;

	public CellRepInfo() {

	}

	public String getListId() {
		return this.listId;
	}

	public void setListId(String listId) {
		this.listId = listId;
	}

	public String getIndexId() {
		return this.indexId;
	}

	public String getFrmType() {
		return this.frmType;
	}

	public String getIsSystem() {
		return this.isSystem;
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

	public String getShowType() {
		return this.showType;
	}

	public Integer getStrLen() {
		return this.strLen;
	}

	public String getForm() {
		return this.form;
	}

	public String getInType() {
		return this.inType;
	}

	public String getHintID() {
		return this.hintID;
	}

	public Integer getListNo() {
		return this.listNo;
	}

	public String getZtype() {
		return this.ztype;
	}

	public String getIsMustFill() {
		return this.isMustFill;
	}

	public String getIsListShow() {
		return this.isListShow;
	}

	public Integer getListWeigth() {
		return this.listWeigth;
	}

	public String getPanid() {
		return this.panid;
	}

	public String getIsAllWidth() {
		return this.isAllWidth;
	}

	public String getIstName() {
		return this.istName;
	}

	public String getFileDotFileId() {
		return this.fileDotFileId;
	}

	public Integer getDataPoint() {
		return this.dataPoint;
	}

	public String getDefaultValue() {
		return this.defaultValue;
	}

	public String getIsSubTable() {
		return this.isSubTable;
	}

	public String getIsDataOnly() {
		return this.isDataOnly;
	}

	public Integer getCheckType() {
		return this.checkType;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public Integer getCellType() {
		return this.cellType;
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

	public String getHintList() {
		return this.hintList;
	}

	public String getOstCellId() {
		return this.ostCellId;
	}

	public String getOldDName() {
		return this.oldDName;
	}

	public String getOstCellName() {
		return this.ostCellName;
	}

	public String getOstFormula() {
		return this.ostFormula;
	}

	public Integer getOstColor() {
		return this.ostColor;
	}

	public Integer getOstSumType() {
		return this.ostSumType;
	}

	public String getDataTableName() {
		return this.dataTableName;
	}

	public String getDataFieldName() {
		return this.dataFieldName;
	}

	public Integer getOrderCon() {
		return this.orderCon;
	}

	public Integer getConNum() {
		return this.conNum;
	}

	public Integer getNodeIndex() {
		return this.nodeIndex;
	}

	public String getTypeId() {
		return this.typeId;
	}

	public String getUserIndex() {
		return this.userIndex;
	}

	public Integer getOrderIndex() {
		return this.orderIndex;
	}

	public String getParentDName() {
		return this.parentDName;
	}

	public Integer getSysAuto() {
		return this.sysAuto;
	}

	public Integer getOrderIndexF() {
		return this.orderIndexF;
	}

	public String getOrderIdF() {
		return this.orderIdF;
	}

	public Integer getOrderConF() {
		return this.orderConF;
	}

	public Integer getIsPrintValue() {
		return this.isPrintValue;
	}

	public Integer getIsShowValueOnLast() {
		return this.isShowValueOnLast;
	}

	public Integer getIsBankRoundType() {
		return this.isBankRoundType;
	}

	public Double getFloatBankRound() {
		return this.floatBankRound;
	}

	public Integer getIntUseWBSPlace() {
		return this.intUseWBSPlace;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	public void setFrmType(String frmType) {
		this.frmType = frmType;
	}

	public void setIsSystem(String isSystem) {
		this.isSystem = isSystem;
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

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public void setStrLen(Integer strLen) {
		this.strLen = strLen;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public void setInType(String inType) {
		this.inType = inType;
	}

	public void setHintID(String hintID) {
		this.hintID = hintID;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public void setZtype(String ztype) {
		this.ztype = ztype;
	}

	public void setIsMustFill(String isMustFill) {
		this.isMustFill = isMustFill;
	}

	public void setIsListShow(String isListShow) {
		this.isListShow = isListShow;
	}

	public void setListWeigth(Integer listWeigth) {
		this.listWeigth = listWeigth;
	}

	public void setPanid(String panid) {
		this.panid = panid;
	}

	public void setIsAllWidth(String isAllWidth) {
		this.isAllWidth = isAllWidth;
	}

	public void setIstName(String istName) {
		this.istName = istName;
	}

	public void setFileDotFileId(String fileDotFileId) {
		this.fileDotFileId = fileDotFileId;
	}

	public void setDataPoint(Integer dataPoint) {
		this.dataPoint = dataPoint;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setIsSubTable(String isSubTable) {
		this.isSubTable = isSubTable;
	}

	public void setIsDataOnly(String isDataOnly) {
		this.isDataOnly = isDataOnly;
	}

	public void setCheckType(Integer checkType) {
		this.checkType = checkType;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setCellType(Integer cellType) {
		this.cellType = cellType;
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

	public void setHintList(String hintList) {
		this.hintList = hintList;
	}

	public void setOstCellId(String ostCellId) {
		this.ostCellId = ostCellId;
	}

	public void setOldDName(String oldDName) {
		this.oldDName = oldDName;
	}

	public void setOstCellName(String ostCellName) {
		this.ostCellName = ostCellName;
	}

	public void setOstFormula(String ostFormula) {
		this.ostFormula = ostFormula;
	}

	public void setOstColor(Integer ostColor) {
		this.ostColor = ostColor;
	}

	public void setOstSumType(Integer ostSumType) {
		this.ostSumType = ostSumType;
	}

	public void setDataTableName(String dataTableName) {
		this.dataTableName = dataTableName;
	}

	public void setDataFieldName(String dataFieldName) {
		this.dataFieldName = dataFieldName;
	}

	public void setOrderCon(Integer orderCon) {
		this.orderCon = orderCon;
	}

	public void setConNum(Integer conNum) {
		this.conNum = conNum;
	}

	public void setNodeIndex(Integer nodeIndex) {
		this.nodeIndex = nodeIndex;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public void setUserIndex(String userIndex) {
		this.userIndex = userIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public void setParentDName(String parentDName) {
		this.parentDName = parentDName;
	}

	public void setSysAuto(Integer sysAuto) {
		this.sysAuto = sysAuto;
	}

	public void setOrderIndexF(Integer orderIndexF) {
		this.orderIndexF = orderIndexF;
	}

	public void setOrderIdF(String orderIdF) {
		this.orderIdF = orderIdF;
	}

	public void setOrderConF(Integer orderConF) {
		this.orderConF = orderConF;
	}

	public void setIsPrintValue(Integer isPrintValue) {
		this.isPrintValue = isPrintValue;
	}

	public void setIsShowValueOnLast(Integer isShowValueOnLast) {
		this.isShowValueOnLast = isShowValueOnLast;
	}

	public void setIsBankRoundType(Integer isBankRoundType) {
		this.isBankRoundType = isBankRoundType;
	}

	public void setFloatBankRound(Double floatBankRound) {
		this.floatBankRound = floatBankRound;
	}

	public void setIntUseWBSPlace(Integer intUseWBSPlace) {
		this.intUseWBSPlace = intUseWBSPlace;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CellRepInfo other = (CellRepInfo) obj;
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

	public CellRepInfo jsonToObject(JSONObject jsonObject) {
		return CellRepInfoJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return CellRepInfoJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return CellRepInfoJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
