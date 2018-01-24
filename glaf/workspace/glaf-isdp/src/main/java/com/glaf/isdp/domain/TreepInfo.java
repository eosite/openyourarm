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
import com.glaf.isdp.util.TreepInfoJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "TREEPINFO")
public class TreepInfo implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "INDEX_ID", nullable = false)
	protected Integer indexId;

	@Column(name = "ID", length = 100)
	protected String id;

	@Column(name = "NUM", length = 50)
	protected String num;

	@Column(name = "TOP_ID")
	protected Integer topId;

	@Column(name = "PARENT_ID")
	protected Integer parentId;

	@Column(name = "INDEX_NAME", length = 255)
	protected String indexName;

	@Column(name = "NLEVEL")
	protected Integer level;

	@Column(name = "NODETYPE", length = 1)
	protected String nodeType;

	@Column(name = "FROMID")
	protected Integer fromId;

	@Column(name = "PART_ID")
	protected Integer partid;

	@Column(name = "SHOWID")
	protected Integer showId;

	@Column(name = "SINDEX_NAME", length = 255)
	protected String sindexName;

	@Column(name = "FILENUM")
	protected Integer fileNum;

	@Column(name = "FILENUM0")
	protected Integer fileNum0;

	@Column(name = "FILENUM1")
	protected Integer fileNum1;

	@Column(name = "FILENUM2")
	protected Integer filenum2;

	@Column(name = "PROJTYPE", length = 1)
	protected String projType;

	@Column(name = "CID", length = 50)
	protected String cid;

	@Column(name = "DWID")
	protected Integer dwid;

	@Column(name = "FXID")
	protected Integer fxid;

	@Column(name = "FBID")
	protected Integer fbid;

	@Column(name = "JID", length = 50)
	protected String jid;

	@Column(name = "FLID", length = 50)
	protected String flid;

	@Column(name = "TOPNODE", length = 250)
	protected String topNode;

	@Column(name = "NODEICO")
	protected Integer nodeIco;

	@Column(name = "OUTFLAG", length = 1)
	protected String outFlag;

	@Column(name = "INFLAG", length = 1)
	protected String inFlag;

	@Column(name = "PASSWORD", length = 10)
	protected String password;

	@Column(name = "LISTNUM", length = 200)
	protected String listNum;

	@Column(name = "WCOMPANY", length = 250)
	protected String wcompany;

	@Column(name = "LISTNO")
	protected Integer listNo;

	@Column(name = "USERNMU", length = 100)
	protected String userNmu;

	@Column(name = "INDEX_ID_OLD")
	protected Integer indexIdOld;

	@Column(name = "PINDEX_ID")
	protected Integer pindexId;

	@Column(name = "FINISHINT")
	protected Integer finishInt;

	@Column(name = "TYPE_TABLENAME", length = 50)
	protected String typeTableName;

	@Column(name = "TREE_DLISTSTR", length = 200)
	protected String treedListStr;

	@Column(name = "PFILES_INDEX")
	protected Integer pfilesIndex;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BDATE")
	protected Date bdate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EDATE")
	protected Date edate;

	@Column(name = "WBSINDEX")
	protected Integer wbsIndex;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BDATE_S")
	protected Date bdates;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EDATE_S")
	protected Date edates;

	@Column(name = "TYPE_ID", length = 50)
	protected String typeId;

	@Column(name = "CELL1")
	protected Integer cell1;

	@Column(name = "CELL2")
	protected Integer cell2;

	@Column(name = "CELL3")
	protected Integer cell3;

	@Column(name = "STRFILELOCATE", length = 50)
	protected String strFileLocate;

	@Column(name = "INTPFILE1")
	protected Integer intPfile1;

	@Column(name = "INTPFILE2")
	protected Integer intPfile2;

	@Column(name = "INTPFILE3")
	protected Integer intPfile3;

	@Column(name = "INTCELLFINISH")
	protected Integer intCellFinish;

	@Column(name = "SYS_ID", length = 50)
	protected String sysId;

	@Column(name = "INDEX_IN")
	protected Integer indexIn;

	@Column(name = "STRBUTTONSTAR", length = 20)
	protected String strButtonStar;

	@Column(name = "INTISUSE")
	protected Integer intIsUse;

	@Column(name = "WBSINDEX_IN")
	protected Integer wbsIndexIn;

	@Column(name = "UINDEX_ID")
	protected Integer uindexId;

	@Column(name = "LISNO_WBS")
	protected Integer lisnoWBS;

	@Column(name = "SYS_ID_ADD", length = 50)
	protected String sysIdAdd;

	@Column(name = "INDEX_IN_ADD")
	protected Integer indexInAdd;
	
	@Column(name = "INTMUSTFILLCELLNUM")
	protected Integer intMustFillCellNum;
	
	@javax.persistence.Transient
	protected Integer treeProjIndexId;
	@javax.persistence.Transient
	protected String treeProjIndexName;
	@javax.persistence.Transient
	protected Integer treeProjParentId;
	@javax.persistence.Transient
	protected String treeProjType;
	@javax.persistence.Transient
	protected String finishString;
	@javax.persistence.Transient
	protected String intCellFinishString;
	@javax.persistence.Transient
	protected String intCellFinishIcon;
	@javax.persistence.Transient
	@Column(name = "childrenCount")
	protected Integer childrenCount;
	
	public TreepInfo() {

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
		return this.level;
	}

	public String getNodeType() {
		return this.nodeType;
	}

	public Integer getFromId() {
		return this.fromId;
	}

	public Integer getPartid() {
		return this.partid;
	}

	public Integer getShowId() {
		return this.showId;
	}

	public String getSindexName() {
		return this.sindexName;
	}

	public Integer getFileNum() {
		return this.fileNum;
	}

	public Integer getFileNum0() {
		return this.fileNum0;
	}

	public Integer getFileNum1() {
		return this.fileNum1;
	}

	public Integer getFilenum2() {
		return this.filenum2;
	}

	public String getProjType() {
		return this.projType;
	}

	public String getCid() {
		return this.cid;
	}

	public Integer getDwid() {
		return this.dwid;
	}

	public Integer getFxid() {
		return this.fxid;
	}

	public Integer getFbid() {
		return this.fbid;
	}

	public String getJid() {
		return this.jid;
	}

	public String getFlid() {
		return this.flid;
	}

	public String getTopNode() {
		return this.topNode;
	}

	public Integer getNodeIco() {
		return this.nodeIco;
	}

	public String getOutFlag() {
		return this.outFlag;
	}

	public String getInFlag() {
		return this.inFlag;
	}

	public String getPassword() {
		return this.password;
	}

	public String getListNum() {
		return this.listNum;
	}

	public String getWcompany() {
		return this.wcompany;
	}

	public Integer getListNo() {
		return this.listNo;
	}

	public String getUserNmu() {
		return this.userNmu;
	}

	public Integer getIndexIdOld() {
		return this.indexIdOld;
	}

	public Integer getPindexId() {
		return this.pindexId;
	}

	public Integer getFinishInt() {
		return this.finishInt;
	}

	public String getTypeTableName() {
		return this.typeTableName;
	}

	public String getTreedListStr() {
		return this.treedListStr;
	}

	public Integer getPfilesIndex() {
		return this.pfilesIndex;
	}

	public Date getBdate() {
		return this.bdate;
	}

	public String getBdateString() {
		if (this.bdate != null) {
			return DateUtils.getDateTime(this.bdate);
		}
		return "";
	}

	public Date getEdate() {
		return this.edate;
	}

	public String getEdateString() {
		if (this.edate != null) {
			return DateUtils.getDateTime(this.edate);
		}
		return "";
	}

	public Integer getWbsIndex() {
		return this.wbsIndex;
	}

	public Date getBdates() {
		return this.bdates;
	}

	public String getBdatesString() {
		if (this.bdates != null) {
			return DateUtils.getDateTime(this.bdates);
		}
		return "";
	}

	public Date getEdates() {
		return this.edates;
	}

	public String getEdatesString() {
		if (this.edates != null) {
			return DateUtils.getDateTime(this.edates);
		}
		return "";
	}

	public String getTypeId() {
		return this.typeId;
	}

	public Integer getCell1() {
		return this.cell1;
	}

	public Integer getCell2() {
		return this.cell2;
	}

	public Integer getCell3() {
		return this.cell3;
	}

	public String getStrFileLocate() {
		return this.strFileLocate;
	}

	public Integer getIntPfile1() {
		return intPfile1;
	}

	public Integer getIntPfile2() {
		return intPfile2;
	}

	public Integer getIntPfile3() {
		return intPfile3;
	}

	public Integer getIntCellFinish() {
		return this.intCellFinish;
	}

	public String getSysId() {
		return this.sysId;
	}

	public Integer getIndexIn() {
		return this.indexIn;
	}

	public String getStrButtonStar() {
		return this.strButtonStar;
	}

	public Integer getIntIsUse() {
		return this.intIsUse;
	}

	public Integer getWbsIndexIn() {
		return this.wbsIndexIn;
	}

	public Integer getUindexId() {
		return this.uindexId;
	}

	public Integer getLisnoWBS() {
		return this.lisnoWBS;
	}

	public String getSysIdAdd() {
		return this.sysIdAdd;
	}

	public Integer getIndexInAdd() {
		return this.indexInAdd;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNum(String num) {
		this.num = num;
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

	public void setLevel(Integer level) {
		this.level = level;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public void setFromId(Integer fromId) {
		this.fromId = fromId;
	}

	public void setPartid(Integer partid) {
		this.partid = partid;
	}

	public void setShowId(Integer showId) {
		this.showId = showId;
	}

	public void setSindexName(String sindexName) {
		this.sindexName = sindexName;
	}

	public void setFileNum(Integer fileNum) {
		this.fileNum = fileNum;
	}

	public void setFileNum0(Integer fileNum0) {
		this.fileNum0 = fileNum0;
	}

	public void setFileNum1(Integer fileNum1) {
		this.fileNum1 = fileNum1;
	}

	public void setFilenum2(Integer filenum2) {
		this.filenum2 = filenum2;
	}

	public void setProjType(String projType) {
		this.projType = projType;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public void setDwid(Integer dwid) {
		this.dwid = dwid;
	}

	public void setFxid(Integer fxid) {
		this.fxid = fxid;
	}

	public void setFbid(Integer fbid) {
		this.fbid = fbid;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}

	public void setFlid(String flid) {
		this.flid = flid;
	}

	public void setTopNode(String topNode) {
		this.topNode = topNode;
	}

	public void setNodeIco(Integer nodeIco) {
		this.nodeIco = nodeIco;
	}

	public void setOutFlag(String outFlag) {
		this.outFlag = outFlag;
	}

	public void setInFlag(String inFlag) {
		this.inFlag = inFlag;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setListNum(String listNum) {
		this.listNum = listNum;
	}

	public void setWcompany(String wcompany) {
		this.wcompany = wcompany;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public void setUserNmu(String userNmu) {
		this.userNmu = userNmu;
	}

	public void setIndexIdOld(Integer indexIdOld) {
		this.indexIdOld = indexIdOld;
	}

	public void setPindexId(Integer pindexId) {
		this.pindexId = pindexId;
	}

	public void setFinishInt(Integer finishInt) {
		this.finishInt = finishInt;
	}

	public void setTypeTableName(String typeTableName) {
		this.typeTableName = typeTableName;
	}

	public void setTreedListStr(String treedListStr) {
		this.treedListStr = treedListStr;
	}

	public void setPfilesIndex(Integer pfilesIndex) {
		this.pfilesIndex = pfilesIndex;
	}

	public void setBdate(Date bdate) {
		this.bdate = bdate;
	}

	public void setEdate(Date edate) {
		this.edate = edate;
	}

	public void setWbsIndex(Integer wbsIndex) {
		this.wbsIndex = wbsIndex;
	}

	public void setBdates(Date bdates) {
		this.bdates = bdates;
	}

	public void setEdates(Date edates) {
		this.edates = edates;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public void setCell1(Integer cell1) {
		this.cell1 = cell1;
	}

	public void setCell2(Integer cell2) {
		this.cell2 = cell2;
	}

	public void setCell3(Integer cell3) {
		this.cell3 = cell3;
	}

	public void setStrFileLocate(String strFileLocate) {
		this.strFileLocate = strFileLocate;
	}


	public void setIntPfile1(Integer intPfile1) {
		this.intPfile1 = intPfile1;
	}

	public void setIntPfile2(Integer intPfile2) {
		this.intPfile2 = intPfile2;
	}

	public void setIntPfile3(Integer intPfile3) {
		this.intPfile3 = intPfile3;
	}

	public void setIntCellFinish(Integer intCellFinish) {
		this.intCellFinish = intCellFinish;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public void setIndexIn(Integer indexIn) {
		this.indexIn = indexIn;
	}

	public void setStrButtonStar(String strButtonStar) {
		this.strButtonStar = strButtonStar;
	}

	public void setIntIsUse(Integer intIsUse) {
		this.intIsUse = intIsUse;
	}

	public void setWbsIndexIn(Integer wbsIndexIn) {
		this.wbsIndexIn = wbsIndexIn;
	}

	public void setUindexId(Integer uindexId) {
		this.uindexId = uindexId;
	}

	public void setLisnoWBS(Integer lisnoWBS) {
		this.lisnoWBS = lisnoWBS;
	}

	public void setSysIdAdd(String sysIdAdd) {
		this.sysIdAdd = sysIdAdd;
	}

	public void setIndexInAdd(Integer indexInAdd) {
		this.indexInAdd = indexInAdd;
	}

	public Integer getTreeProjIndexId() {
		return treeProjIndexId;
	}

	public void setTreeProjIndexId(Integer treeProjIndexId) {
		this.treeProjIndexId = treeProjIndexId;
	}

	public String getTreeProjIndexName() {
		return treeProjIndexName;
	}

	public void setTreeProjIndexName(String treeProjIndexName) {
		this.treeProjIndexName = treeProjIndexName;
	}

	public Integer getTreeProjParentId() {
		return treeProjParentId;
	}

	public void setTreeProjParentId(Integer treeProjParentId) {
		this.treeProjParentId = treeProjParentId;
	}

	public String getTreeProjType() {
		return treeProjType;
	}

	public void setTreeProjType(String treeProjType) {
		this.treeProjType = treeProjType;
	}

	public Integer getIntMustFillCellNum() {
		return intMustFillCellNum;
	}

	public void setIntMustFillCellNum(Integer intMustFillCellNum) {
		this.intMustFillCellNum = intMustFillCellNum;
	}

	public String getFinishString() {
		switch(finishInt){
			case 0:
				finishString = "计划";
				break;
			case 1:
				finishString = "实施";
				break;
			case -1:
				finishString = "取消";
				break;
			case -2:
				finishString = "终止";
				break;
			default:
				finishString = "";
		}
		return finishString;
	}

	public void setFinishString(String finishString) {
		this.finishString = finishString;
	}

	public String getIntCellFinishString() {
		if(intCellFinish==null)
			return "";
		
		switch(intCellFinish){
			case 1:
				intCellFinishString = "完成";
				break;
			case 2:
				intCellFinishString = "只完成表格";
				break;
			case 3:
				intCellFinishString = "不合格";
				break;
			default:
				intCellFinishString = "未完成";
		}
		return intCellFinishString;
	}

	public void setIntCellFinishString(String intCellFinishString) {
		this.intCellFinishString = intCellFinishString;
	}

	public String getIntCellFinishIcon() {
		if(intCellFinish==null)
			return "";
		
		switch(intCellFinish){
			case 1:
				intCellFinishIcon = "○";
				break;
			case 2:
				intCellFinishIcon = "□";
				break;
			case 3:
				intCellFinishIcon = "△";
				break;
			default:
				intCellFinishIcon = "×";
		}
		return intCellFinishIcon;
	}

	public void setIntCellFinishIcon(String intCellFinishIcon) {
		this.intCellFinishIcon = intCellFinishIcon;
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
		TreepInfo other = (TreepInfo) obj;
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

	public TreepInfo jsonToObject(JSONObject jsonObject) {
		return TreepInfoJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return TreepInfoJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return TreepInfoJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
