package com.glaf.isdp.query;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.glaf.core.query.DataQuery;

public class TreepInfoQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Integer> indexIds;
	protected Integer indexId;
	protected Integer indexIdNotEqual;
	protected Collection<String> appActorIds;
	protected String id;
	protected String idLike;
	protected List<String> ids;
	protected String num;
	protected String numLike;
	protected List<String> nums;
	protected Integer topId;
	protected Integer topIdGreaterThanOrEqual;
	protected Integer topIdLessThanOrEqual;
	protected List<Integer> topIds;
	protected String indexName;
	protected String indexNameLike;
	protected List<String> indexNames;
	protected Integer level;
	protected Integer levelGreaterThanOrEqual;
	protected Integer levelLessThanOrEqual;
	protected List<Integer> levels;
	protected String nodeType;
	protected String nodeTypeLike;
	protected List<String> nodeTypes;
	protected Integer fromId;
	protected Integer fromIdGreaterThanOrEqual;
	protected Integer fromIdLessThanOrEqual;
	protected List<Integer> fromIds;
	protected Integer partid;
	protected Integer partidGreaterThanOrEqual;
	protected Integer partidLessThanOrEqual;
	protected List<Integer> partids;
	protected Integer showId;
	protected Integer showIdGreaterThanOrEqual;
	protected Integer showIdLessThanOrEqual;
	protected List<Integer> showIds;
	protected String sindexName;
	protected String sindexNameLike;
	protected List<String> sindexNames;
	protected Integer fileNum;
	protected Integer fileNumGreaterThanOrEqual;
	protected Integer fileNumLessThanOrEqual;
	protected List<Integer> fileNums;
	protected Integer fileNum0;
	protected Integer fileNum0GreaterThanOrEqual;
	protected Integer fileNum0LessThanOrEqual;
	protected List<Integer> fileNum0s;
	protected Integer fileNum1;
	protected Integer fileNum1GreaterThanOrEqual;
	protected Integer fileNum1LessThanOrEqual;
	protected List<Integer> fileNum1s;
	protected Integer filenum2;
	protected Integer filenum2GreaterThanOrEqual;
	protected Integer filenum2LessThanOrEqual;
	protected List<Integer> filenum2s;
	protected String projType;
	protected String projTypeLike;
	protected List<String> projTypes;
	protected String cid;
	protected String cidLike;
	protected List<String> cids;
	protected Integer dwid;
	protected Integer dwidGreaterThanOrEqual;
	protected Integer dwidLessThanOrEqual;
	protected List<Integer> dwids;
	protected Integer fxid;
	protected Integer fxidGreaterThanOrEqual;
	protected Integer fxidLessThanOrEqual;
	protected List<Integer> fxids;
	protected Integer fbid;
	protected Integer fbidGreaterThanOrEqual;
	protected Integer fbidLessThanOrEqual;
	protected List<Integer> fbids;
	protected String jid;
	protected String jidLike;
	protected List<String> jids;
	protected String flid;
	protected String flidLike;
	protected List<String> flids;
	protected String topNode;
	protected String topNodeLike;
	protected List<String> topNodes;
	protected Integer nodeIco;
	protected Integer nodeIcoGreaterThanOrEqual;
	protected Integer nodeIcoLessThanOrEqual;
	protected List<Integer> nodeIcos;
	protected String outFlag;
	protected String outFlagLike;
	protected List<String> outFlags;
	protected String inFlag;
	protected String inFlagLike;
	protected List<String> inFlags;
	protected String password;
	protected String passwordLike;
	protected List<String> passwords;
	protected String listNum;
	protected String listNumLike;
	protected List<String> listNums;
	protected String wcompany;
	protected String wcompanyLike;
	protected List<String> wcompanys;
	protected Integer listNo;
	protected Integer listNoGreaterThanOrEqual;
	protected Integer listNoLessThanOrEqual;
	protected List<Integer> listNos;
	protected String userNmu;
	protected String userNmuLike;
	protected List<String> userNmus;
	protected Integer indexIdOld;
	protected Integer indexIdOldGreaterThanOrEqual;
	protected Integer indexIdOldLessThanOrEqual;
	protected List<Integer> indexIdOlds;
	protected Integer pindexId;
	protected Integer pindexIdGreaterThanOrEqual;
	protected Integer pindexIdLessThanOrEqual;
	protected List<Integer> pindexIds;
	protected Integer finishInt;
	protected Integer finishIntGreaterThanOrEqual;
	protected Integer finishIntLessThanOrEqual;
	protected List<Integer> finishInts;
	protected String typeTableName;
	protected String typeTableNameLike;
	protected List<String> typeTableNames;
	protected String treedListStr;
	protected String treedListStrLike;
	protected List<String> treedListStrs;
	protected Integer pfilesIndex;
	protected Integer pfilesIndexGreaterThanOrEqual;
	protected Integer pfilesIndexLessThanOrEqual;
	protected List<Integer> pfilesIndexs;
	protected Date bdateGreaterThanOrEqual;
	protected Date bdateLessThanOrEqual;
	protected Date edateGreaterThanOrEqual;
	protected Date edateLessThanOrEqual;
	protected Integer wbsIndex;
	protected Integer wbsIndexGreaterThanOrEqual;
	protected Integer wbsIndexLessThanOrEqual;
	protected List<Integer> wbsIndexs;
	protected Date bdatesGreaterThanOrEqual;
	protected Date bdatesLessThanOrEqual;
	protected Date bdatesLessThan;
	protected List<Date> bdatess;
	protected Date edates;
	protected Date edatesGreaterThanOrEqual;
	protected Date edatesLessThanOrEqual;
	protected List<Date> edatess;
	protected String typeId;
	protected String typeIdLike;
	protected List<String> typeIds;
	protected Integer cell1;
	protected Integer cell1GreaterThanOrEqual;
	protected Integer cell1LessThanOrEqual;
	protected List<Integer> cell1s;
	protected Integer cell2;
	protected Integer cell2GreaterThanOrEqual;
	protected Integer cell2LessThanOrEqual;
	protected List<Integer> cell2s;
	protected Integer cell3;
	protected Integer cell3GreaterThanOrEqual;
	protected Integer cell3LessThanOrEqual;
	protected List<Integer> cell3s;
	protected String strFileLocate;
	protected String strFileLocateLike;
	protected List<String> strFileLocates;
	protected Integer intPfile1;
	protected Integer intPfile1GreaterThanOrEqual;
	protected Integer intPfile1LessThanOrEqual;
	protected List<Integer> intPfile1s;
	protected Integer intPfile2;
	protected Integer intPfile2GreaterThanOrEqual;
	protected Integer intPfile2LessThanOrEqual;
	protected List<Integer> intPfile2s;
	protected Integer intPfile3;
	protected Integer intPfile3GreaterThanOrEqual;
	protected Integer intPfile3LessThanOrEqual;
	protected List<Integer> intPfile3s;
	protected Integer intCellFinish;
	protected Integer intCellFinishGreaterThanOrEqual;
	protected Integer intCellFinishLessThanOrEqual;
	protected List<Integer> intCellFinishs;
	protected String sysId;
	protected String sysIdLike;
	protected List<String> sysIds;
	protected Integer indexIn;
	protected Integer indexInGreaterThanOrEqual;
	protected Integer indexInLessThanOrEqual;
	protected List<Integer> indexIns;
	protected String strButtonStar;
	protected String strButtonStarLike;
	protected List<String> strButtonStars;
	protected Integer intIsUse;
	protected Integer intIsUseGreaterThanOrEqual;
	protected Integer intIsUseLessThanOrEqual;
	protected List<Integer> intIsUses;
	protected Integer wbsIndexIn;
	protected Integer wbsIndexInGreaterThanOrEqual;
	protected Integer wbsIndexInLessThanOrEqual;
	protected List<Integer> wbsIndexIns;
	protected Integer uindexId;
	protected Integer uindexIdGreaterThanOrEqual;
	protected Integer uindexIdLessThanOrEqual;
	protected List<Integer> uindexIds;
	protected Integer lisnoWBS;
	protected Integer lisnoWBSGreaterThanOrEqual;
	protected Integer lisnoWBSLessThanOrEqual;
	protected List<Integer> lisnoWBSs;
	protected String sysIdAdd;
	protected String sysIdAddLike;
	protected List<String> sysIdAdds;
	protected Integer indexInAdd;
	protected Integer indexInAddGreaterThanOrEqual;
	protected Integer indexInAddLessThanOrEqual;
	protected List<Integer> indexInAdds;
	
	protected String createByLike;
	protected List<String> createBys;
	
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected List<Date> createDates;
	protected Date updateDate;
	protected Date updateDateGreaterThanOrEqual;
	protected Date updateDateLessThanOrEqual;
	protected List<Date> updateDates;
	protected String updateBy;
	protected String updateByLike;
	protected List<String> updateBys;

	protected List<String> idLikeList;
	protected Boolean bdatesIsNotNull;
	protected String conditionString;
	protected String projTypeNotEqual;

	public TreepInfoQuery() {

	}

	public Date getBdatesLessThan() {
		return bdatesLessThan;
	}

	public void setBdatesLessThan(Date bdatesLessThan) {
		this.bdatesLessThan = bdatesLessThan;
	}

	public List<Date> getBdatess() {
		return bdatess;
	}

	public void setBdatess(List<Date> bdatess) {
		this.bdatess = bdatess;
	}

	public Date getEdates() {
		return edates;
	}

	public void setEdates(Date edates) {
		this.edates = edates;
	}

	public List<Date> getEdatess() {
		return edatess;
	}

	public void setEdatess(List<Date> edatess) {
		this.edatess = edatess;
	}

	public String getCreateByLike() {
		return createByLike;
	}

	public void setCreateByLike(String createByLike) {
		this.createByLike = createByLike;
	}

	public List<String> getCreateBys() {
		return createBys;
	}

	public void setCreateBys(List<String> createBys) {
		this.createBys = createBys;
	}

	public Date getCreateDateGreaterThanOrEqual() {
		return createDateGreaterThanOrEqual;
	}

	public void setCreateDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual) {
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
	}

	public Date getCreateDateLessThanOrEqual() {
		return createDateLessThanOrEqual;
	}

	public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
	}

	public List<Date> getCreateDates() {
		return createDates;
	}

	public void setCreateDates(List<Date> createDates) {
		this.createDates = createDates;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getUpdateDateGreaterThanOrEqual() {
		return updateDateGreaterThanOrEqual;
	}

	public void setUpdateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual) {
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
	}

	public Date getUpdateDateLessThanOrEqual() {
		return updateDateLessThanOrEqual;
	}

	public void setUpdateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
	}

	public List<Date> getUpdateDates() {
		return updateDates;
	}

	public void setUpdateDates(List<Date> updateDates) {
		this.updateDates = updateDates;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateByLike() {
		return updateByLike;
	}

	public void setUpdateByLike(String updateByLike) {
		this.updateByLike = updateByLike;
	}

	public List<String> getUpdateBys() {
		return updateBys;
	}

	public void setUpdateBys(List<String> updateBys) {
		this.updateBys = updateBys;
	}

	public List<String> getIdLikeList() {
		return idLikeList;
	}

	public void setIdLikeList(List<String> idLikeList) {
		this.idLikeList = idLikeList;
	}

	public Boolean getBdatesIsNotNull() {
		return bdatesIsNotNull;
	}

	public void setBdatesIsNotNull(Boolean bdatesIsNotNull) {
		this.bdatesIsNotNull = bdatesIsNotNull;
	}

	public String getConditionString() {
		return conditionString;
	}

	public void setConditionString(String conditionString) {
		this.conditionString = conditionString;
	}

	public String getProjTypeNotEqual() {
		return projTypeNotEqual;
	}

	public void setProjTypeNotEqual(String projTypeNotEqual) {
		this.projTypeNotEqual = projTypeNotEqual;
	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getId() {
		return id;
	}

	public String getIdLike() {
		if (idLike != null && idLike.trim().length() > 0) {
			if (!idLike.startsWith("%")) {
				idLike = "%" + idLike;
			}
			if (!idLike.endsWith("%")) {
				idLike = idLike + "%";
			}
		}
		return idLike;
	}

	public List<String> getIds() {
		return ids;
	}

	public String getNum() {
		return num;
	}

	public String getNumLike() {
		if (numLike != null && numLike.trim().length() > 0) {
			if (!numLike.startsWith("%")) {
				numLike = "%" + numLike;
			}
			if (!numLike.endsWith("%")) {
				numLike = numLike + "%";
			}
		}
		return numLike;
	}

	public List<String> getNums() {
		return nums;
	}

	public Integer getTopId() {
		return topId;
	}

	public Integer getTopIdGreaterThanOrEqual() {
		return topIdGreaterThanOrEqual;
	}

	public Integer getTopIdLessThanOrEqual() {
		return topIdLessThanOrEqual;
	}

	public List<Integer> getTopIds() {
		return topIds;
	}

	// public Integer getParentId() {
	// return parentId;
	// }
	//
	// public Integer getParentIdGreaterThanOrEqual() {
	// return parentIdGreaterThanOrEqual;
	// }
	//
	// public Integer getParentIdLessThanOrEqual() {
	// return parentIdLessThanOrEqual;
	// }
	//
	// public List<Integer> getParentIds() {
	// return parentIds;
	// }

	public String getIndexName() {
		return indexName;
	}

	public String getIndexNameLike() {
		if (indexNameLike != null && indexNameLike.trim().length() > 0) {
			if (!indexNameLike.startsWith("%")) {
				indexNameLike = "%" + indexNameLike;
			}
			if (!indexNameLike.endsWith("%")) {
				indexNameLike = indexNameLike + "%";
			}
		}
		return indexNameLike;
	}

	public List<String> getIndexNames() {
		return indexNames;
	}

	public Integer getLevel() {
		return level;
	}

	public Integer getLevelGreaterThanOrEqual() {
		return levelGreaterThanOrEqual;
	}

	public Integer getLevelLessThanOrEqual() {
		return levelLessThanOrEqual;
	}

	public List<Integer> getLevels() {
		return levels;
	}

	public String getNodeType() {
		return nodeType;
	}

	public String getNodeTypeLike() {
		if (nodeTypeLike != null && nodeTypeLike.trim().length() > 0) {
			if (!nodeTypeLike.startsWith("%")) {
				nodeTypeLike = "%" + nodeTypeLike;
			}
			if (!nodeTypeLike.endsWith("%")) {
				nodeTypeLike = nodeTypeLike + "%";
			}
		}
		return nodeTypeLike;
	}

	public List<String> getNodeTypes() {
		return nodeTypes;
	}

	public Integer getFromId() {
		return fromId;
	}

	public Integer getFromIdGreaterThanOrEqual() {
		return fromIdGreaterThanOrEqual;
	}

	public Integer getFromIdLessThanOrEqual() {
		return fromIdLessThanOrEqual;
	}

	public List<Integer> getFromIds() {
		return fromIds;
	}

	public Integer getPartid() {
		return partid;
	}

	public Integer getPartidGreaterThanOrEqual() {
		return partidGreaterThanOrEqual;
	}

	public Integer getPartidLessThanOrEqual() {
		return partidLessThanOrEqual;
	}

	public List<Integer> getPartids() {
		return partids;
	}

	public Integer getShowId() {
		return showId;
	}

	public Integer getShowIdGreaterThanOrEqual() {
		return showIdGreaterThanOrEqual;
	}

	public Integer getShowIdLessThanOrEqual() {
		return showIdLessThanOrEqual;
	}

	public List<Integer> getShowIds() {
		return showIds;
	}

	public String getSindexName() {
		return sindexName;
	}

	public String getSindexNameLike() {
		if (sindexNameLike != null && sindexNameLike.trim().length() > 0) {
			if (!sindexNameLike.startsWith("%")) {
				sindexNameLike = "%" + sindexNameLike;
			}
			if (!sindexNameLike.endsWith("%")) {
				sindexNameLike = sindexNameLike + "%";
			}
		}
		return sindexNameLike;
	}

	public List<String> getSindexNames() {
		return sindexNames;
	}

	public Integer getFileNum() {
		return fileNum;
	}

	public Integer getFileNumGreaterThanOrEqual() {
		return fileNumGreaterThanOrEqual;
	}

	public Integer getFileNumLessThanOrEqual() {
		return fileNumLessThanOrEqual;
	}

	public List<Integer> getFileNums() {
		return fileNums;
	}

	public Integer getFileNum0() {
		return fileNum0;
	}

	public Integer getFileNum0GreaterThanOrEqual() {
		return fileNum0GreaterThanOrEqual;
	}

	public Integer getFileNum0LessThanOrEqual() {
		return fileNum0LessThanOrEqual;
	}

	public List<Integer> getFileNum0s() {
		return fileNum0s;
	}

	public Integer getFileNum1() {
		return fileNum1;
	}

	public Integer getFileNum1GreaterThanOrEqual() {
		return fileNum1GreaterThanOrEqual;
	}

	public Integer getFileNum1LessThanOrEqual() {
		return fileNum1LessThanOrEqual;
	}

	public List<Integer> getFileNum1s() {
		return fileNum1s;
	}

	public Integer getFilenum2() {
		return filenum2;
	}

	public Integer getFilenum2GreaterThanOrEqual() {
		return filenum2GreaterThanOrEqual;
	}

	public Integer getFilenum2LessThanOrEqual() {
		return filenum2LessThanOrEqual;
	}

	public List<Integer> getFilenum2s() {
		return filenum2s;
	}

	public String getProjType() {
		return projType;
	}

	public String getProjTypeLike() {
		if (projTypeLike != null && projTypeLike.trim().length() > 0) {
			if (!projTypeLike.startsWith("%")) {
				projTypeLike = "%" + projTypeLike;
			}
			if (!projTypeLike.endsWith("%")) {
				projTypeLike = projTypeLike + "%";
			}
		}
		return projTypeLike;
	}

	public List<String> getProjTypes() {
		return projTypes;
	}

	public String getCid() {
		return cid;
	}

	public String getCidLike() {
		if (cidLike != null && cidLike.trim().length() > 0) {
			if (!cidLike.startsWith("%")) {
				cidLike = "%" + cidLike;
			}
			if (!cidLike.endsWith("%")) {
				cidLike = cidLike + "%";
			}
		}
		return cidLike;
	}

	public List<String> getCids() {
		return cids;
	}

	public Integer getDwid() {
		return dwid;
	}

	public Integer getDwidGreaterThanOrEqual() {
		return dwidGreaterThanOrEqual;
	}

	public Integer getDwidLessThanOrEqual() {
		return dwidLessThanOrEqual;
	}

	public List<Integer> getDwids() {
		return dwids;
	}

	public Integer getFxid() {
		return fxid;
	}

	public Integer getFxidGreaterThanOrEqual() {
		return fxidGreaterThanOrEqual;
	}

	public Integer getFxidLessThanOrEqual() {
		return fxidLessThanOrEqual;
	}

	public List<Integer> getFxids() {
		return fxids;
	}

	public Integer getFbid() {
		return fbid;
	}

	public Integer getFbidGreaterThanOrEqual() {
		return fbidGreaterThanOrEqual;
	}

	public Integer getFbidLessThanOrEqual() {
		return fbidLessThanOrEqual;
	}

	public List<Integer> getFbids() {
		return fbids;
	}

	public String getJid() {
		return jid;
	}

	public String getJidLike() {
		if (jidLike != null && jidLike.trim().length() > 0) {
			if (!jidLike.startsWith("%")) {
				jidLike = "%" + jidLike;
			}
			if (!jidLike.endsWith("%")) {
				jidLike = jidLike + "%";
			}
		}
		return jidLike;
	}

	public List<String> getJids() {
		return jids;
	}

	public String getFlid() {
		return flid;
	}

	public String getFlidLike() {
		if (flidLike != null && flidLike.trim().length() > 0) {
			if (!flidLike.startsWith("%")) {
				flidLike = "%" + flidLike;
			}
			if (!flidLike.endsWith("%")) {
				flidLike = flidLike + "%";
			}
		}
		return flidLike;
	}

	public List<String> getFlids() {
		return flids;
	}

	public String getTopNode() {
		return topNode;
	}

	public String getTopNodeLike() {
		if (topNodeLike != null && topNodeLike.trim().length() > 0) {
			if (!topNodeLike.startsWith("%")) {
				topNodeLike = "%" + topNodeLike;
			}
			if (!topNodeLike.endsWith("%")) {
				topNodeLike = topNodeLike + "%";
			}
		}
		return topNodeLike;
	}

	public List<String> getTopNodes() {
		return topNodes;
	}

	public Integer getNodeIco() {
		return nodeIco;
	}

	public Integer getNodeIcoGreaterThanOrEqual() {
		return nodeIcoGreaterThanOrEqual;
	}

	public Integer getNodeIcoLessThanOrEqual() {
		return nodeIcoLessThanOrEqual;
	}

	public List<Integer> getNodeIcos() {
		return nodeIcos;
	}

	public String getOutFlag() {
		return outFlag;
	}

	public String getOutFlagLike() {
		if (outFlagLike != null && outFlagLike.trim().length() > 0) {
			if (!outFlagLike.startsWith("%")) {
				outFlagLike = "%" + outFlagLike;
			}
			if (!outFlagLike.endsWith("%")) {
				outFlagLike = outFlagLike + "%";
			}
		}
		return outFlagLike;
	}

	public List<String> getOutFlags() {
		return outFlags;
	}

	public String getInFlag() {
		return inFlag;
	}

	public String getInFlagLike() {
		if (inFlagLike != null && inFlagLike.trim().length() > 0) {
			if (!inFlagLike.startsWith("%")) {
				inFlagLike = "%" + inFlagLike;
			}
			if (!inFlagLike.endsWith("%")) {
				inFlagLike = inFlagLike + "%";
			}
		}
		return inFlagLike;
	}

	public List<String> getInFlags() {
		return inFlags;
	}

	public String getPassword() {
		return password;
	}

	public String getPasswordLike() {
		if (passwordLike != null && passwordLike.trim().length() > 0) {
			if (!passwordLike.startsWith("%")) {
				passwordLike = "%" + passwordLike;
			}
			if (!passwordLike.endsWith("%")) {
				passwordLike = passwordLike + "%";
			}
		}
		return passwordLike;
	}

	public List<String> getPasswords() {
		return passwords;
	}

	public String getListNum() {
		return listNum;
	}

	public String getListNumLike() {
		if (listNumLike != null && listNumLike.trim().length() > 0) {
			if (!listNumLike.startsWith("%")) {
				listNumLike = "%" + listNumLike;
			}
			if (!listNumLike.endsWith("%")) {
				listNumLike = listNumLike + "%";
			}
		}
		return listNumLike;
	}

	public List<String> getListNums() {
		return listNums;
	}

	public String getWcompany() {
		return wcompany;
	}

	public String getWcompanyLike() {
		if (wcompanyLike != null && wcompanyLike.trim().length() > 0) {
			if (!wcompanyLike.startsWith("%")) {
				wcompanyLike = "%" + wcompanyLike;
			}
			if (!wcompanyLike.endsWith("%")) {
				wcompanyLike = wcompanyLike + "%";
			}
		}
		return wcompanyLike;
	}

	public List<String> getWcompanys() {
		return wcompanys;
	}

	public Integer getListNo() {
		return listNo;
	}

	public Integer getListNoGreaterThanOrEqual() {
		return listNoGreaterThanOrEqual;
	}

	public Integer getListNoLessThanOrEqual() {
		return listNoLessThanOrEqual;
	}

	public List<Integer> getListNos() {
		return listNos;
	}

	public String getUserNmu() {
		return userNmu;
	}

	public String getUserNmuLike() {
		if (userNmuLike != null && userNmuLike.trim().length() > 0) {
			if (!userNmuLike.startsWith("%")) {
				userNmuLike = "%" + userNmuLike;
			}
			if (!userNmuLike.endsWith("%")) {
				userNmuLike = userNmuLike + "%";
			}
		}
		return userNmuLike;
	}

	public List<String> getUserNmus() {
		return userNmus;
	}

	public Integer getIndexIdOld() {
		return indexIdOld;
	}

	public Integer getIndexIdOldGreaterThanOrEqual() {
		return indexIdOldGreaterThanOrEqual;
	}

	public Integer getIndexIdOldLessThanOrEqual() {
		return indexIdOldLessThanOrEqual;
	}

	public List<Integer> getIndexIdOlds() {
		return indexIdOlds;
	}

	public Integer getPindexId() {
		return pindexId;
	}

	public Integer getPindexIdGreaterThanOrEqual() {
		return pindexIdGreaterThanOrEqual;
	}

	public Integer getPindexIdLessThanOrEqual() {
		return pindexIdLessThanOrEqual;
	}

	public List<Integer> getPindexIds() {
		return pindexIds;
	}

	public Integer getFinishInt() {
		return finishInt;
	}

	public Integer getFinishIntGreaterThanOrEqual() {
		return finishIntGreaterThanOrEqual;
	}

	public Integer getFinishIntLessThanOrEqual() {
		return finishIntLessThanOrEqual;
	}

	public List<Integer> getFinishInts() {
		return finishInts;
	}

	public String getTypeTableName() {
		return typeTableName;
	}

	public String getTypeTableNameLike() {
		if (typeTableNameLike != null && typeTableNameLike.trim().length() > 0) {
			if (!typeTableNameLike.startsWith("%")) {
				typeTableNameLike = "%" + typeTableNameLike;
			}
			if (!typeTableNameLike.endsWith("%")) {
				typeTableNameLike = typeTableNameLike + "%";
			}
		}
		return typeTableNameLike;
	}

	public List<String> getTypeTableNames() {
		return typeTableNames;
	}

	public String getTreedListStr() {
		return treedListStr;
	}

	public String getTreedListStrLike() {
		if (treedListStrLike != null && treedListStrLike.trim().length() > 0) {
			if (!treedListStrLike.startsWith("%")) {
				treedListStrLike = "%" + treedListStrLike;
			}
			if (!treedListStrLike.endsWith("%")) {
				treedListStrLike = treedListStrLike + "%";
			}
		}
		return treedListStrLike;
	}

	public List<String> getTreedListStrs() {
		return treedListStrs;
	}

	public Integer getPfilesIndex() {
		return pfilesIndex;
	}

	public Integer getPfilesIndexGreaterThanOrEqual() {
		return pfilesIndexGreaterThanOrEqual;
	}

	public Integer getPfilesIndexLessThanOrEqual() {
		return pfilesIndexLessThanOrEqual;
	}

	public List<Integer> getPfilesIndexs() {
		return pfilesIndexs;
	}

	public Date getBdateGreaterThanOrEqual() {
		return bdateGreaterThanOrEqual;
	}

	public Date getBdateLessThanOrEqual() {
		return bdateLessThanOrEqual;
	}

	public Date getEdateGreaterThanOrEqual() {
		return edateGreaterThanOrEqual;
	}

	public Date getEdateLessThanOrEqual() {
		return edateLessThanOrEqual;
	}

	public Integer getWbsIndex() {
		return wbsIndex;
	}

	public Integer getWbsIndexGreaterThanOrEqual() {
		return wbsIndexGreaterThanOrEqual;
	}

	public Integer getWbsIndexLessThanOrEqual() {
		return wbsIndexLessThanOrEqual;
	}

	public List<Integer> getWbsIndexs() {
		return wbsIndexs;
	}

	public Date getBdatesGreaterThanOrEqual() {
		return bdatesGreaterThanOrEqual;
	}

	public Date getBdatesLessThanOrEqual() {
		return bdatesLessThanOrEqual;
	}

	public Date getEdatesGreaterThanOrEqual() {
		return edatesGreaterThanOrEqual;
	}

	public Date getEdatesLessThanOrEqual() {
		return edatesLessThanOrEqual;
	}

	public String getTypeId() {
		return typeId;
	}

	public String getTypeIdLike() {
		if (typeIdLike != null && typeIdLike.trim().length() > 0) {
			if (!typeIdLike.startsWith("%")) {
				typeIdLike = "%" + typeIdLike;
			}
			if (!typeIdLike.endsWith("%")) {
				typeIdLike = typeIdLike + "%";
			}
		}
		return typeIdLike;
	}

	public List<String> getTypeIds() {
		return typeIds;
	}

	public Integer getCell1() {
		return cell1;
	}

	public Integer getCell1GreaterThanOrEqual() {
		return cell1GreaterThanOrEqual;
	}

	public Integer getCell1LessThanOrEqual() {
		return cell1LessThanOrEqual;
	}

	public List<Integer> getCell1s() {
		return cell1s;
	}

	public Integer getCell2() {
		return cell2;
	}

	public Integer getCell2GreaterThanOrEqual() {
		return cell2GreaterThanOrEqual;
	}

	public Integer getCell2LessThanOrEqual() {
		return cell2LessThanOrEqual;
	}

	public List<Integer> getCell2s() {
		return cell2s;
	}

	public Integer getCell3() {
		return cell3;
	}

	public Integer getCell3GreaterThanOrEqual() {
		return cell3GreaterThanOrEqual;
	}

	public Integer getCell3LessThanOrEqual() {
		return cell3LessThanOrEqual;
	}

	public List<Integer> getCell3s() {
		return cell3s;
	}

	public String getStrFileLocate() {
		return strFileLocate;
	}

	public String getStrFileLocateLike() {
		if (strFileLocateLike != null && strFileLocateLike.trim().length() > 0) {
			if (!strFileLocateLike.startsWith("%")) {
				strFileLocateLike = "%" + strFileLocateLike;
			}
			if (!strFileLocateLike.endsWith("%")) {
				strFileLocateLike = strFileLocateLike + "%";
			}
		}
		return strFileLocateLike;
	}

	public List<String> getStrFileLocates() {
		return strFileLocates;
	}

	public Integer getIntPfile1() {
		return intPfile1;
	}

	public Integer getIntPfile1GreaterThanOrEqual() {
		return intPfile1GreaterThanOrEqual;
	}

	public Integer getIntPfile1LessThanOrEqual() {
		return intPfile1LessThanOrEqual;
	}

	public List<Integer> getIntPfile1s() {
		return intPfile1s;
	}

	public Integer getIntPfile2() {
		return intPfile2;
	}

	public Integer getIntPfile2GreaterThanOrEqual() {
		return intPfile2GreaterThanOrEqual;
	}

	public Integer getIntPfile2LessThanOrEqual() {
		return intPfile2LessThanOrEqual;
	}

	public List<Integer> getIntPfile2s() {
		return intPfile2s;
	}

	public Integer getIntPfile3() {
		return intPfile3;
	}

	public Integer getIntPfile3GreaterThanOrEqual() {
		return intPfile3GreaterThanOrEqual;
	}

	public Integer getIntPfile3LessThanOrEqual() {
		return intPfile3LessThanOrEqual;
	}

	public List<Integer> getIntPfile3s() {
		return intPfile3s;
	}

	public Integer getIntCellFinish() {
		return intCellFinish;
	}

	public Integer getIntCellFinishGreaterThanOrEqual() {
		return intCellFinishGreaterThanOrEqual;
	}

	public Integer getIntCellFinishLessThanOrEqual() {
		return intCellFinishLessThanOrEqual;
	}

	public List<Integer> getIntCellFinishs() {
		return intCellFinishs;
	}

	public String getSysId() {
		return sysId;
	}

	public String getSysIdLike() {
		if (sysIdLike != null && sysIdLike.trim().length() > 0) {
			if (!sysIdLike.startsWith("%")) {
				sysIdLike = "%" + sysIdLike;
			}
			if (!sysIdLike.endsWith("%")) {
				sysIdLike = sysIdLike + "%";
			}
		}
		return sysIdLike;
	}

	public List<String> getSysIds() {
		return sysIds;
	}

	public Integer getIndexIn() {
		return indexIn;
	}

	public Integer getIndexInGreaterThanOrEqual() {
		return indexInGreaterThanOrEqual;
	}

	public Integer getIndexInLessThanOrEqual() {
		return indexInLessThanOrEqual;
	}

	public List<Integer> getIndexIns() {
		return indexIns;
	}

	public String getStrButtonStar() {
		return strButtonStar;
	}

	public String getStrButtonStarLike() {
		if (strButtonStarLike != null && strButtonStarLike.trim().length() > 0) {
			if (!strButtonStarLike.startsWith("%")) {
				strButtonStarLike = "%" + strButtonStarLike;
			}
			if (!strButtonStarLike.endsWith("%")) {
				strButtonStarLike = strButtonStarLike + "%";
			}
		}
		return strButtonStarLike;
	}

	public List<String> getStrButtonStars() {
		return strButtonStars;
	}

	public Integer getIntIsUse() {
		return intIsUse;
	}

	public Integer getIntIsUseGreaterThanOrEqual() {
		return intIsUseGreaterThanOrEqual;
	}

	public Integer getIntIsUseLessThanOrEqual() {
		return intIsUseLessThanOrEqual;
	}

	public List<Integer> getIntIsUses() {
		return intIsUses;
	}

	public Integer getWbsIndexIn() {
		return wbsIndexIn;
	}

	public Integer getWbsIndexInGreaterThanOrEqual() {
		return wbsIndexInGreaterThanOrEqual;
	}

	public Integer getWbsIndexInLessThanOrEqual() {
		return wbsIndexInLessThanOrEqual;
	}

	public List<Integer> getWbsIndexIns() {
		return wbsIndexIns;
	}

	public Integer getUindexId() {
		return uindexId;
	}

	public Integer getUindexIdGreaterThanOrEqual() {
		return uindexIdGreaterThanOrEqual;
	}

	public Integer getUindexIdLessThanOrEqual() {
		return uindexIdLessThanOrEqual;
	}

	public List<Integer> getUindexIds() {
		return uindexIds;
	}

	public Integer getLisnoWBS() {
		return lisnoWBS;
	}

	public Integer getLisnoWBSGreaterThanOrEqual() {
		return lisnoWBSGreaterThanOrEqual;
	}

	public Integer getLisnoWBSLessThanOrEqual() {
		return lisnoWBSLessThanOrEqual;
	}

	public List<Integer> getLisnoWBSs() {
		return lisnoWBSs;
	}

	public String getSysIdAdd() {
		return sysIdAdd;
	}

	public String getSysIdAddLike() {
		if (sysIdAddLike != null && sysIdAddLike.trim().length() > 0) {
			if (!sysIdAddLike.startsWith("%")) {
				sysIdAddLike = "%" + sysIdAddLike;
			}
			if (!sysIdAddLike.endsWith("%")) {
				sysIdAddLike = sysIdAddLike + "%";
			}
		}
		return sysIdAddLike;
	}

	public List<String> getSysIdAdds() {
		return sysIdAdds;
	}

	public Integer getIndexInAdd() {
		return indexInAdd;
	}

	public Integer getIndexInAddGreaterThanOrEqual() {
		return indexInAddGreaterThanOrEqual;
	}

	public Integer getIndexInAddLessThanOrEqual() {
		return indexInAddLessThanOrEqual;
	}

	public List<Integer> getIndexInAdds() {
		return indexInAdds;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIdLike(String idLike) {
		this.idLike = idLike;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public void setNumLike(String numLike) {
		this.numLike = numLike;
	}

	public void setNums(List<String> nums) {
		this.nums = nums;
	}

	public void setTopId(Integer topId) {
		this.topId = topId;
	}

	public void setTopIdGreaterThanOrEqual(Integer topIdGreaterThanOrEqual) {
		this.topIdGreaterThanOrEqual = topIdGreaterThanOrEqual;
	}

	public void setTopIdLessThanOrEqual(Integer topIdLessThanOrEqual) {
		this.topIdLessThanOrEqual = topIdLessThanOrEqual;
	}

	public void setTopIds(List<Integer> topIds) {
		this.topIds = topIds;
	}

	// public void setParentId(Integer parentId) {
	// this.parentId = parentId;
	// }
	//
	// public void setParentIdGreaterThanOrEqual(Integer
	// parentIdGreaterThanOrEqual) {
	// this.parentIdGreaterThanOrEqual = parentIdGreaterThanOrEqual;
	// }
	//
	// public void setParentIdLessThanOrEqual(Integer parentIdLessThanOrEqual) {
	// this.parentIdLessThanOrEqual = parentIdLessThanOrEqual;
	// }
	//
	// public void setParentIds(List<Integer> parentIds) {
	// this.parentIds = parentIds;
	// }

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public void setIndexNameLike(String indexNameLike) {
		this.indexNameLike = indexNameLike;
	}

	public void setIndexNames(List<String> indexNames) {
		this.indexNames = indexNames;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public void setLevelGreaterThanOrEqual(Integer levelGreaterThanOrEqual) {
		this.levelGreaterThanOrEqual = levelGreaterThanOrEqual;
	}

	public void setLevelLessThanOrEqual(Integer levelLessThanOrEqual) {
		this.levelLessThanOrEqual = levelLessThanOrEqual;
	}

	public void setLevels(List<Integer> levels) {
		this.levels = levels;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public void setNodeTypeLike(String nodeTypeLike) {
		this.nodeTypeLike = nodeTypeLike;
	}

	public void setNodeTypes(List<String> nodeTypes) {
		this.nodeTypes = nodeTypes;
	}

	public void setFromId(Integer fromId) {
		this.fromId = fromId;
	}

	public void setFromIdGreaterThanOrEqual(Integer fromIdGreaterThanOrEqual) {
		this.fromIdGreaterThanOrEqual = fromIdGreaterThanOrEqual;
	}

	public void setFromIdLessThanOrEqual(Integer fromIdLessThanOrEqual) {
		this.fromIdLessThanOrEqual = fromIdLessThanOrEqual;
	}

	public void setFromIds(List<Integer> fromIds) {
		this.fromIds = fromIds;
	}

	public void setPartid(Integer partid) {
		this.partid = partid;
	}

	public void setPartidGreaterThanOrEqual(Integer partidGreaterThanOrEqual) {
		this.partidGreaterThanOrEqual = partidGreaterThanOrEqual;
	}

	public void setPartidLessThanOrEqual(Integer partidLessThanOrEqual) {
		this.partidLessThanOrEqual = partidLessThanOrEqual;
	}

	public void setPartids(List<Integer> partids) {
		this.partids = partids;
	}

	public void setShowId(Integer showId) {
		this.showId = showId;
	}

	public void setShowIdGreaterThanOrEqual(Integer showIdGreaterThanOrEqual) {
		this.showIdGreaterThanOrEqual = showIdGreaterThanOrEqual;
	}

	public void setShowIdLessThanOrEqual(Integer showIdLessThanOrEqual) {
		this.showIdLessThanOrEqual = showIdLessThanOrEqual;
	}

	public void setShowIds(List<Integer> showIds) {
		this.showIds = showIds;
	}

	public void setSindexName(String sindexName) {
		this.sindexName = sindexName;
	}

	public void setSindexNameLike(String sindexNameLike) {
		this.sindexNameLike = sindexNameLike;
	}

	public void setSindexNames(List<String> sindexNames) {
		this.sindexNames = sindexNames;
	}

	public void setFileNum(Integer fileNum) {
		this.fileNum = fileNum;
	}

	public void setFileNumGreaterThanOrEqual(Integer fileNumGreaterThanOrEqual) {
		this.fileNumGreaterThanOrEqual = fileNumGreaterThanOrEqual;
	}

	public void setFileNumLessThanOrEqual(Integer fileNumLessThanOrEqual) {
		this.fileNumLessThanOrEqual = fileNumLessThanOrEqual;
	}

	public void setFileNums(List<Integer> fileNums) {
		this.fileNums = fileNums;
	}

	public void setFileNum0(Integer fileNum0) {
		this.fileNum0 = fileNum0;
	}

	public void setFileNum0GreaterThanOrEqual(Integer fileNum0GreaterThanOrEqual) {
		this.fileNum0GreaterThanOrEqual = fileNum0GreaterThanOrEqual;
	}

	public void setFileNum0LessThanOrEqual(Integer fileNum0LessThanOrEqual) {
		this.fileNum0LessThanOrEqual = fileNum0LessThanOrEqual;
	}

	public void setFileNum0s(List<Integer> fileNum0s) {
		this.fileNum0s = fileNum0s;
	}

	public void setFileNum1(Integer fileNum1) {
		this.fileNum1 = fileNum1;
	}

	public void setFileNum1GreaterThanOrEqual(Integer fileNum1GreaterThanOrEqual) {
		this.fileNum1GreaterThanOrEqual = fileNum1GreaterThanOrEqual;
	}

	public void setFileNum1LessThanOrEqual(Integer fileNum1LessThanOrEqual) {
		this.fileNum1LessThanOrEqual = fileNum1LessThanOrEqual;
	}

	public void setFileNum1s(List<Integer> fileNum1s) {
		this.fileNum1s = fileNum1s;
	}

	public void setFilenum2(Integer filenum2) {
		this.filenum2 = filenum2;
	}

	public void setFilenum2GreaterThanOrEqual(Integer filenum2GreaterThanOrEqual) {
		this.filenum2GreaterThanOrEqual = filenum2GreaterThanOrEqual;
	}

	public void setFilenum2LessThanOrEqual(Integer filenum2LessThanOrEqual) {
		this.filenum2LessThanOrEqual = filenum2LessThanOrEqual;
	}

	public void setFilenum2s(List<Integer> filenum2s) {
		this.filenum2s = filenum2s;
	}

	public void setProjType(String projType) {
		this.projType = projType;
	}

	public void setProjTypeLike(String projTypeLike) {
		this.projTypeLike = projTypeLike;
	}

	public void setProjTypes(List<String> projTypes) {
		this.projTypes = projTypes;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public void setCidLike(String cidLike) {
		this.cidLike = cidLike;
	}

	public void setCids(List<String> cids) {
		this.cids = cids;
	}

	public void setDwid(Integer dwid) {
		this.dwid = dwid;
	}

	public void setDwidGreaterThanOrEqual(Integer dwidGreaterThanOrEqual) {
		this.dwidGreaterThanOrEqual = dwidGreaterThanOrEqual;
	}

	public void setDwidLessThanOrEqual(Integer dwidLessThanOrEqual) {
		this.dwidLessThanOrEqual = dwidLessThanOrEqual;
	}

	public void setDwids(List<Integer> dwids) {
		this.dwids = dwids;
	}

	public void setFxid(Integer fxid) {
		this.fxid = fxid;
	}

	public void setFxidGreaterThanOrEqual(Integer fxidGreaterThanOrEqual) {
		this.fxidGreaterThanOrEqual = fxidGreaterThanOrEqual;
	}

	public void setFxidLessThanOrEqual(Integer fxidLessThanOrEqual) {
		this.fxidLessThanOrEqual = fxidLessThanOrEqual;
	}

	public void setFxids(List<Integer> fxids) {
		this.fxids = fxids;
	}

	public void setFbid(Integer fbid) {
		this.fbid = fbid;
	}

	public void setFbidGreaterThanOrEqual(Integer fbidGreaterThanOrEqual) {
		this.fbidGreaterThanOrEqual = fbidGreaterThanOrEqual;
	}

	public void setFbidLessThanOrEqual(Integer fbidLessThanOrEqual) {
		this.fbidLessThanOrEqual = fbidLessThanOrEqual;
	}

	public void setFbids(List<Integer> fbids) {
		this.fbids = fbids;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}

	public void setJidLike(String jidLike) {
		this.jidLike = jidLike;
	}

	public void setJids(List<String> jids) {
		this.jids = jids;
	}

	public void setFlid(String flid) {
		this.flid = flid;
	}

	public void setFlidLike(String flidLike) {
		this.flidLike = flidLike;
	}

	public void setFlids(List<String> flids) {
		this.flids = flids;
	}

	public void setTopNode(String topNode) {
		this.topNode = topNode;
	}

	public void setTopNodeLike(String topNodeLike) {
		this.topNodeLike = topNodeLike;
	}

	public void setTopNodes(List<String> topNodes) {
		this.topNodes = topNodes;
	}

	public void setNodeIco(Integer nodeIco) {
		this.nodeIco = nodeIco;
	}

	public void setNodeIcoGreaterThanOrEqual(Integer nodeIcoGreaterThanOrEqual) {
		this.nodeIcoGreaterThanOrEqual = nodeIcoGreaterThanOrEqual;
	}

	public void setNodeIcoLessThanOrEqual(Integer nodeIcoLessThanOrEqual) {
		this.nodeIcoLessThanOrEqual = nodeIcoLessThanOrEqual;
	}

	public void setNodeIcos(List<Integer> nodeIcos) {
		this.nodeIcos = nodeIcos;
	}

	public void setOutFlag(String outFlag) {
		this.outFlag = outFlag;
	}

	public void setOutFlagLike(String outFlagLike) {
		this.outFlagLike = outFlagLike;
	}

	public void setOutFlags(List<String> outFlags) {
		this.outFlags = outFlags;
	}

	public void setInFlag(String inFlag) {
		this.inFlag = inFlag;
	}

	public void setInFlagLike(String inFlagLike) {
		this.inFlagLike = inFlagLike;
	}

	public void setInFlags(List<String> inFlags) {
		this.inFlags = inFlags;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPasswordLike(String passwordLike) {
		this.passwordLike = passwordLike;
	}

	public void setPasswords(List<String> passwords) {
		this.passwords = passwords;
	}

	public void setListNum(String listNum) {
		this.listNum = listNum;
	}

	public void setListNumLike(String listNumLike) {
		this.listNumLike = listNumLike;
	}

	public void setListNums(List<String> listNums) {
		this.listNums = listNums;
	}

	public void setWcompany(String wcompany) {
		this.wcompany = wcompany;
	}

	public void setWcompanyLike(String wcompanyLike) {
		this.wcompanyLike = wcompanyLike;
	}

	public void setWcompanys(List<String> wcompanys) {
		this.wcompanys = wcompanys;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public void setListNoGreaterThanOrEqual(Integer listNoGreaterThanOrEqual) {
		this.listNoGreaterThanOrEqual = listNoGreaterThanOrEqual;
	}

	public void setListNoLessThanOrEqual(Integer listNoLessThanOrEqual) {
		this.listNoLessThanOrEqual = listNoLessThanOrEqual;
	}

	public void setListNos(List<Integer> listNos) {
		this.listNos = listNos;
	}

	public void setUserNmu(String userNmu) {
		this.userNmu = userNmu;
	}

	public void setUserNmuLike(String userNmuLike) {
		this.userNmuLike = userNmuLike;
	}

	public void setUserNmus(List<String> userNmus) {
		this.userNmus = userNmus;
	}

	public void setIndexIdOld(Integer indexIdOld) {
		this.indexIdOld = indexIdOld;
	}

	public void setIndexIdOldGreaterThanOrEqual(
			Integer indexIdOldGreaterThanOrEqual) {
		this.indexIdOldGreaterThanOrEqual = indexIdOldGreaterThanOrEqual;
	}

	public void setIndexIdOldLessThanOrEqual(Integer indexIdOldLessThanOrEqual) {
		this.indexIdOldLessThanOrEqual = indexIdOldLessThanOrEqual;
	}

	public void setIndexIdOlds(List<Integer> indexIdOlds) {
		this.indexIdOlds = indexIdOlds;
	}

	public void setPindexId(Integer pindexId) {
		this.pindexId = pindexId;
	}

	public void setPindexIdGreaterThanOrEqual(Integer pindexIdGreaterThanOrEqual) {
		this.pindexIdGreaterThanOrEqual = pindexIdGreaterThanOrEqual;
	}

	public void setPindexIdLessThanOrEqual(Integer pindexIdLessThanOrEqual) {
		this.pindexIdLessThanOrEqual = pindexIdLessThanOrEqual;
	}

	public void setPindexIds(List<Integer> pindexIds) {
		this.pindexIds = pindexIds;
	}

	public void setFinishInt(Integer finishInt) {
		this.finishInt = finishInt;
	}

	public void setFinishIntGreaterThanOrEqual(
			Integer finishIntGreaterThanOrEqual) {
		this.finishIntGreaterThanOrEqual = finishIntGreaterThanOrEqual;
	}

	public void setFinishIntLessThanOrEqual(Integer finishIntLessThanOrEqual) {
		this.finishIntLessThanOrEqual = finishIntLessThanOrEqual;
	}

	public void setFinishInts(List<Integer> finishInts) {
		this.finishInts = finishInts;
	}

	public void setTypeTableName(String typeTableName) {
		this.typeTableName = typeTableName;
	}

	public void setTypeTableNameLike(String typeTableNameLike) {
		this.typeTableNameLike = typeTableNameLike;
	}

	public void setTypeTableNames(List<String> typeTableNames) {
		this.typeTableNames = typeTableNames;
	}

	public void setTreedListStr(String treedListStr) {
		this.treedListStr = treedListStr;
	}

	public void setTreedListStrLike(String treedListStrLike) {
		this.treedListStrLike = treedListStrLike;
	}

	public void setTreedListStrs(List<String> treedListStrs) {
		this.treedListStrs = treedListStrs;
	}

	public void setPfilesIndex(Integer pfilesIndex) {
		this.pfilesIndex = pfilesIndex;
	}

	public void setPfilesIndexGreaterThanOrEqual(
			Integer pfilesIndexGreaterThanOrEqual) {
		this.pfilesIndexGreaterThanOrEqual = pfilesIndexGreaterThanOrEqual;
	}

	public void setPfilesIndexLessThanOrEqual(Integer pfilesIndexLessThanOrEqual) {
		this.pfilesIndexLessThanOrEqual = pfilesIndexLessThanOrEqual;
	}

	public void setPfilesIndexs(List<Integer> pfilesIndexs) {
		this.pfilesIndexs = pfilesIndexs;
	}

	public void setBdateGreaterThanOrEqual(Date bdateGreaterThanOrEqual) {
		this.bdateGreaterThanOrEqual = bdateGreaterThanOrEqual;
	}

	public void setBdateLessThanOrEqual(Date bdateLessThanOrEqual) {
		this.bdateLessThanOrEqual = bdateLessThanOrEqual;
	}

	public void setEdateGreaterThanOrEqual(Date edateGreaterThanOrEqual) {
		this.edateGreaterThanOrEqual = edateGreaterThanOrEqual;
	}

	public void setEdateLessThanOrEqual(Date edateLessThanOrEqual) {
		this.edateLessThanOrEqual = edateLessThanOrEqual;
	}

	public void setWbsIndex(Integer wbsIndex) {
		this.wbsIndex = wbsIndex;
	}

	public void setWbsIndexGreaterThanOrEqual(Integer wbsIndexGreaterThanOrEqual) {
		this.wbsIndexGreaterThanOrEqual = wbsIndexGreaterThanOrEqual;
	}

	public void setWbsIndexLessThanOrEqual(Integer wbsIndexLessThanOrEqual) {
		this.wbsIndexLessThanOrEqual = wbsIndexLessThanOrEqual;
	}

	public void setWbsIndexs(List<Integer> wbsIndexs) {
		this.wbsIndexs = wbsIndexs;
	}

	public void setBdatesGreaterThanOrEqual(Date bdatesGreaterThanOrEqual) {
		this.bdatesGreaterThanOrEqual = bdatesGreaterThanOrEqual;
	}

	public void setBdatesLessThanOrEqual(Date bdatesLessThanOrEqual) {
		this.bdatesLessThanOrEqual = bdatesLessThanOrEqual;
	}

	public void setEdatesGreaterThanOrEqual(Date edatesGreaterThanOrEqual) {
		this.edatesGreaterThanOrEqual = edatesGreaterThanOrEqual;
	}

	public void setEdatesLessThanOrEqual(Date edatesLessThanOrEqual) {
		this.edatesLessThanOrEqual = edatesLessThanOrEqual;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public void setTypeIdLike(String typeIdLike) {
		this.typeIdLike = typeIdLike;
	}

	public void setTypeIds(List<String> typeIds) {
		this.typeIds = typeIds;
	}

	public void setCell1(Integer cell1) {
		this.cell1 = cell1;
	}

	public void setCell1GreaterThanOrEqual(Integer cell1GreaterThanOrEqual) {
		this.cell1GreaterThanOrEqual = cell1GreaterThanOrEqual;
	}

	public void setCell1LessThanOrEqual(Integer cell1LessThanOrEqual) {
		this.cell1LessThanOrEqual = cell1LessThanOrEqual;
	}

	public void setCell1s(List<Integer> cell1s) {
		this.cell1s = cell1s;
	}

	public void setCell2(Integer cell2) {
		this.cell2 = cell2;
	}

	public void setCell2GreaterThanOrEqual(Integer cell2GreaterThanOrEqual) {
		this.cell2GreaterThanOrEqual = cell2GreaterThanOrEqual;
	}

	public void setCell2LessThanOrEqual(Integer cell2LessThanOrEqual) {
		this.cell2LessThanOrEqual = cell2LessThanOrEqual;
	}

	public void setCell2s(List<Integer> cell2s) {
		this.cell2s = cell2s;
	}

	public void setCell3(Integer cell3) {
		this.cell3 = cell3;
	}

	public void setCell3GreaterThanOrEqual(Integer cell3GreaterThanOrEqual) {
		this.cell3GreaterThanOrEqual = cell3GreaterThanOrEqual;
	}

	public void setCell3LessThanOrEqual(Integer cell3LessThanOrEqual) {
		this.cell3LessThanOrEqual = cell3LessThanOrEqual;
	}

	public void setCell3s(List<Integer> cell3s) {
		this.cell3s = cell3s;
	}

	public void setStrFileLocate(String strFileLocate) {
		this.strFileLocate = strFileLocate;
	}

	public void setStrFileLocateLike(String strFileLocateLike) {
		this.strFileLocateLike = strFileLocateLike;
	}

	public void setStrFileLocates(List<String> strFileLocates) {
		this.strFileLocates = strFileLocates;
	}

	public void setIntPfile1(Integer intPfile1) {
		this.intPfile1 = intPfile1;
	}

	public void setIntPfile1GreaterThanOrEqual(
			Integer intPfile1GreaterThanOrEqual) {
		this.intPfile1GreaterThanOrEqual = intPfile1GreaterThanOrEqual;
	}

	public void setIntPfile1LessThanOrEqual(Integer intPfile1LessThanOrEqual) {
		this.intPfile1LessThanOrEqual = intPfile1LessThanOrEqual;
	}

	public void setIntPfile1s(List<Integer> intPfile1s) {
		this.intPfile1s = intPfile1s;
	}

	public void setIntPfile2(Integer intPfile2) {
		this.intPfile2 = intPfile2;
	}

	public void setIntPfile2GreaterThanOrEqual(
			Integer intPfile2GreaterThanOrEqual) {
		this.intPfile2GreaterThanOrEqual = intPfile2GreaterThanOrEqual;
	}

	public void setIntPfile2LessThanOrEqual(Integer intPfile2LessThanOrEqual) {
		this.intPfile2LessThanOrEqual = intPfile2LessThanOrEqual;
	}

	public void setIntPfile2s(List<Integer> intPfile2s) {
		this.intPfile2s = intPfile2s;
	}

	public void setIntPfile3(Integer intPfile3) {
		this.intPfile3 = intPfile3;
	}

	public void setIntPfile3GreaterThanOrEqual(
			Integer intPfile3GreaterThanOrEqual) {
		this.intPfile3GreaterThanOrEqual = intPfile3GreaterThanOrEqual;
	}

	public void setIntPfile3LessThanOrEqual(Integer intPfile3LessThanOrEqual) {
		this.intPfile3LessThanOrEqual = intPfile3LessThanOrEqual;
	}

	public void setIntPfile3s(List<Integer> intPfile3s) {
		this.intPfile3s = intPfile3s;
	}

	public void setIntCellFinish(Integer intCellFinish) {
		this.intCellFinish = intCellFinish;
	}

	public void setIntCellFinishGreaterThanOrEqual(
			Integer intCellFinishGreaterThanOrEqual) {
		this.intCellFinishGreaterThanOrEqual = intCellFinishGreaterThanOrEqual;
	}

	public void setIntCellFinishLessThanOrEqual(
			Integer intCellFinishLessThanOrEqual) {
		this.intCellFinishLessThanOrEqual = intCellFinishLessThanOrEqual;
	}

	public void setIntCellFinishs(List<Integer> intCellFinishs) {
		this.intCellFinishs = intCellFinishs;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public void setSysIdLike(String sysIdLike) {
		this.sysIdLike = sysIdLike;
	}

	public void setSysIds(List<String> sysIds) {
		this.sysIds = sysIds;
	}

	public void setIndexIn(Integer indexIn) {
		this.indexIn = indexIn;
	}

	public void setIndexInGreaterThanOrEqual(Integer indexInGreaterThanOrEqual) {
		this.indexInGreaterThanOrEqual = indexInGreaterThanOrEqual;
	}

	public void setIndexInLessThanOrEqual(Integer indexInLessThanOrEqual) {
		this.indexInLessThanOrEqual = indexInLessThanOrEqual;
	}

	public void setIndexIns(List<Integer> indexIns) {
		this.indexIns = indexIns;
	}

	public void setStrButtonStar(String strButtonStar) {
		this.strButtonStar = strButtonStar;
	}

	public void setStrButtonStarLike(String strButtonStarLike) {
		this.strButtonStarLike = strButtonStarLike;
	}

	public void setStrButtonStars(List<String> strButtonStars) {
		this.strButtonStars = strButtonStars;
	}

	public void setIntIsUse(Integer intIsUse) {
		this.intIsUse = intIsUse;
	}

	public void setIntIsUseGreaterThanOrEqual(Integer intIsUseGreaterThanOrEqual) {
		this.intIsUseGreaterThanOrEqual = intIsUseGreaterThanOrEqual;
	}

	public void setIntIsUseLessThanOrEqual(Integer intIsUseLessThanOrEqual) {
		this.intIsUseLessThanOrEqual = intIsUseLessThanOrEqual;
	}

	public void setIntIsUses(List<Integer> intIsUses) {
		this.intIsUses = intIsUses;
	}

	public void setWbsIndexIn(Integer wbsIndexIn) {
		this.wbsIndexIn = wbsIndexIn;
	}

	public void setWbsIndexInGreaterThanOrEqual(
			Integer wbsIndexInGreaterThanOrEqual) {
		this.wbsIndexInGreaterThanOrEqual = wbsIndexInGreaterThanOrEqual;
	}

	public void setWbsIndexInLessThanOrEqual(Integer wbsIndexInLessThanOrEqual) {
		this.wbsIndexInLessThanOrEqual = wbsIndexInLessThanOrEqual;
	}

	public void setWbsIndexIns(List<Integer> wbsIndexIns) {
		this.wbsIndexIns = wbsIndexIns;
	}

	public void setUindexId(Integer uindexId) {
		this.uindexId = uindexId;
	}

	public void setUindexIdGreaterThanOrEqual(Integer uindexIdGreaterThanOrEqual) {
		this.uindexIdGreaterThanOrEqual = uindexIdGreaterThanOrEqual;
	}

	public void setUindexIdLessThanOrEqual(Integer uindexIdLessThanOrEqual) {
		this.uindexIdLessThanOrEqual = uindexIdLessThanOrEqual;
	}

	public void setUindexIds(List<Integer> uindexIds) {
		this.uindexIds = uindexIds;
	}

	public void setLisnoWBS(Integer lisnoWBS) {
		this.lisnoWBS = lisnoWBS;
	}

	public void setLisnoWBSGreaterThanOrEqual(Integer lisnoWBSGreaterThanOrEqual) {
		this.lisnoWBSGreaterThanOrEqual = lisnoWBSGreaterThanOrEqual;
	}

	public void setLisnoWBSLessThanOrEqual(Integer lisnoWBSLessThanOrEqual) {
		this.lisnoWBSLessThanOrEqual = lisnoWBSLessThanOrEqual;
	}

	public void setLisnoWBSs(List<Integer> lisnoWBSs) {
		this.lisnoWBSs = lisnoWBSs;
	}

	public void setSysIdAdd(String sysIdAdd) {
		this.sysIdAdd = sysIdAdd;
	}

	public void setSysIdAddLike(String sysIdAddLike) {
		this.sysIdAddLike = sysIdAddLike;
	}

	public void setSysIdAdds(List<String> sysIdAdds) {
		this.sysIdAdds = sysIdAdds;
	}

	public void setIndexInAdd(Integer indexInAdd) {
		this.indexInAdd = indexInAdd;
	}

	public void setIndexInAddGreaterThanOrEqual(
			Integer indexInAddGreaterThanOrEqual) {
		this.indexInAddGreaterThanOrEqual = indexInAddGreaterThanOrEqual;
	}

	public void setIndexInAddLessThanOrEqual(Integer indexInAddLessThanOrEqual) {
		this.indexInAddLessThanOrEqual = indexInAddLessThanOrEqual;
	}

	public void setIndexInAdds(List<Integer> indexInAdds) {
		this.indexInAdds = indexInAdds;
	}

	public List<Integer> getIndexIds() {
		return indexIds;
	}

	public void setIndexIds(List<Integer> indexIds) {
		this.indexIds = indexIds;
	}

	public Integer getIndexId() {
		return indexId;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public Integer getIndexIdNotEqual() {
		return indexIdNotEqual;
	}

	public void setIndexIdNotEqual(Integer indexIdNotEqual) {
		this.indexIdNotEqual = indexIdNotEqual;
	}

	public TreepInfoQuery id(String id) {
		if (id == null) {
			throw new RuntimeException("id is null");
		}
		this.id = id;
		return this;
	}

	public TreepInfoQuery idLike(String idLike) {
		if (idLike == null) {
			throw new RuntimeException("id is null");
		}
		this.idLike = idLike;
		return this;
	}

	public TreepInfoQuery ids(List<String> ids) {
		if (ids == null) {
			throw new RuntimeException("ids is empty ");
		}
		this.ids = ids;
		return this;
	}

	public TreepInfoQuery num(String num) {
		if (num == null) {
			throw new RuntimeException("num is null");
		}
		this.num = num;
		return this;
	}

	public TreepInfoQuery numLike(String numLike) {
		if (numLike == null) {
			throw new RuntimeException("num is null");
		}
		this.numLike = numLike;
		return this;
	}

	public TreepInfoQuery nums(List<String> nums) {
		if (nums == null) {
			throw new RuntimeException("nums is empty ");
		}
		this.nums = nums;
		return this;
	}

	public TreepInfoQuery topId(Integer topId) {
		if (topId == null) {
			throw new RuntimeException("topId is null");
		}
		this.topId = topId;
		return this;
	}

	public TreepInfoQuery topIdGreaterThanOrEqual(
			Integer topIdGreaterThanOrEqual) {
		if (topIdGreaterThanOrEqual == null) {
			throw new RuntimeException("topId is null");
		}
		this.topIdGreaterThanOrEqual = topIdGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery topIdLessThanOrEqual(Integer topIdLessThanOrEqual) {
		if (topIdLessThanOrEqual == null) {
			throw new RuntimeException("topId is null");
		}
		this.topIdLessThanOrEqual = topIdLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery topIds(List<Integer> topIds) {
		if (topIds == null) {
			throw new RuntimeException("topIds is empty ");
		}
		this.topIds = topIds;
		return this;
	}

	public TreepInfoQuery indexName(String indexName) {
		if (indexName == null) {
			throw new RuntimeException("indexName is null");
		}
		this.indexName = indexName;
		return this;
	}

	public TreepInfoQuery indexNameLike(String indexNameLike) {
		if (indexNameLike == null) {
			throw new RuntimeException("indexName is null");
		}
		this.indexNameLike = indexNameLike;
		return this;
	}

	public TreepInfoQuery indexNames(List<String> indexNames) {
		if (indexNames == null) {
			throw new RuntimeException("indexNames is empty ");
		}
		this.indexNames = indexNames;
		return this;
	}

	public TreepInfoQuery level(Integer level) {
		if (level == null) {
			throw new RuntimeException("level is null");
		}
		this.level = level;
		return this;
	}

	public TreepInfoQuery levelGreaterThanOrEqual(
			Integer levelGreaterThanOrEqual) {
		if (levelGreaterThanOrEqual == null) {
			throw new RuntimeException("level is null");
		}
		this.levelGreaterThanOrEqual = levelGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery levelLessThanOrEqual(Integer levelLessThanOrEqual) {
		if (levelLessThanOrEqual == null) {
			throw new RuntimeException("level is null");
		}
		this.levelLessThanOrEqual = levelLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery levels(List<Integer> levels) {
		if (levels == null) {
			throw new RuntimeException("levels is empty ");
		}
		this.levels = levels;
		return this;
	}

	public TreepInfoQuery nodeType(String nodeType) {
		if (nodeType == null) {
			throw new RuntimeException("nodeType is null");
		}
		this.nodeType = nodeType;
		return this;
	}

	public TreepInfoQuery nodeTypeLike(String nodeTypeLike) {
		if (nodeTypeLike == null) {
			throw new RuntimeException("nodeType is null");
		}
		this.nodeTypeLike = nodeTypeLike;
		return this;
	}

	public TreepInfoQuery nodeTypes(List<String> nodeTypes) {
		if (nodeTypes == null) {
			throw new RuntimeException("nodeTypes is empty ");
		}
		this.nodeTypes = nodeTypes;
		return this;
	}

	public TreepInfoQuery fromId(Integer fromId) {
		if (fromId == null) {
			throw new RuntimeException("fromId is null");
		}
		this.fromId = fromId;
		return this;
	}

	public TreepInfoQuery fromIdGreaterThanOrEqual(
			Integer fromIdGreaterThanOrEqual) {
		if (fromIdGreaterThanOrEqual == null) {
			throw new RuntimeException("fromId is null");
		}
		this.fromIdGreaterThanOrEqual = fromIdGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery fromIdLessThanOrEqual(Integer fromIdLessThanOrEqual) {
		if (fromIdLessThanOrEqual == null) {
			throw new RuntimeException("fromId is null");
		}
		this.fromIdLessThanOrEqual = fromIdLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery fromIds(List<Integer> fromIds) {
		if (fromIds == null) {
			throw new RuntimeException("fromIds is empty ");
		}
		this.fromIds = fromIds;
		return this;
	}

	public TreepInfoQuery partid(Integer partid) {
		if (partid == null) {
			throw new RuntimeException("partid is null");
		}
		this.partid = partid;
		return this;
	}

	public TreepInfoQuery partidGreaterThanOrEqual(
			Integer partidGreaterThanOrEqual) {
		if (partidGreaterThanOrEqual == null) {
			throw new RuntimeException("partid is null");
		}
		this.partidGreaterThanOrEqual = partidGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery partidLessThanOrEqual(Integer partidLessThanOrEqual) {
		if (partidLessThanOrEqual == null) {
			throw new RuntimeException("partid is null");
		}
		this.partidLessThanOrEqual = partidLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery partids(List<Integer> partids) {
		if (partids == null) {
			throw new RuntimeException("partids is empty ");
		}
		this.partids = partids;
		return this;
	}

	public TreepInfoQuery showId(Integer showId) {
		if (showId == null) {
			throw new RuntimeException("showId is null");
		}
		this.showId = showId;
		return this;
	}

	public TreepInfoQuery showIdGreaterThanOrEqual(
			Integer showIdGreaterThanOrEqual) {
		if (showIdGreaterThanOrEqual == null) {
			throw new RuntimeException("showId is null");
		}
		this.showIdGreaterThanOrEqual = showIdGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery showIdLessThanOrEqual(Integer showIdLessThanOrEqual) {
		if (showIdLessThanOrEqual == null) {
			throw new RuntimeException("showId is null");
		}
		this.showIdLessThanOrEqual = showIdLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery showIds(List<Integer> showIds) {
		if (showIds == null) {
			throw new RuntimeException("showIds is empty ");
		}
		this.showIds = showIds;
		return this;
	}

	public TreepInfoQuery sindexName(String sindexName) {
		if (sindexName == null) {
			throw new RuntimeException("sindexName is null");
		}
		this.sindexName = sindexName;
		return this;
	}

	public TreepInfoQuery sindexNameLike(String sindexNameLike) {
		if (sindexNameLike == null) {
			throw new RuntimeException("sindexName is null");
		}
		this.sindexNameLike = sindexNameLike;
		return this;
	}

	public TreepInfoQuery sindexNames(List<String> sindexNames) {
		if (sindexNames == null) {
			throw new RuntimeException("sindexNames is empty ");
		}
		this.sindexNames = sindexNames;
		return this;
	}

	public TreepInfoQuery fileNum(Integer fileNum) {
		if (fileNum == null) {
			throw new RuntimeException("fileNum is null");
		}
		this.fileNum = fileNum;
		return this;
	}

	public TreepInfoQuery fileNumGreaterThanOrEqual(
			Integer fileNumGreaterThanOrEqual) {
		if (fileNumGreaterThanOrEqual == null) {
			throw new RuntimeException("fileNum is null");
		}
		this.fileNumGreaterThanOrEqual = fileNumGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery fileNumLessThanOrEqual(Integer fileNumLessThanOrEqual) {
		if (fileNumLessThanOrEqual == null) {
			throw new RuntimeException("fileNum is null");
		}
		this.fileNumLessThanOrEqual = fileNumLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery fileNums(List<Integer> fileNums) {
		if (fileNums == null) {
			throw new RuntimeException("fileNums is empty ");
		}
		this.fileNums = fileNums;
		return this;
	}

	public TreepInfoQuery fileNum0(Integer fileNum0) {
		if (fileNum0 == null) {
			throw new RuntimeException("fileNum0 is null");
		}
		this.fileNum0 = fileNum0;
		return this;
	}

	public TreepInfoQuery fileNum0GreaterThanOrEqual(
			Integer fileNum0GreaterThanOrEqual) {
		if (fileNum0GreaterThanOrEqual == null) {
			throw new RuntimeException("fileNum0 is null");
		}
		this.fileNum0GreaterThanOrEqual = fileNum0GreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery fileNum0LessThanOrEqual(
			Integer fileNum0LessThanOrEqual) {
		if (fileNum0LessThanOrEqual == null) {
			throw new RuntimeException("fileNum0 is null");
		}
		this.fileNum0LessThanOrEqual = fileNum0LessThanOrEqual;
		return this;
	}

	public TreepInfoQuery fileNum0s(List<Integer> fileNum0s) {
		if (fileNum0s == null) {
			throw new RuntimeException("fileNum0s is empty ");
		}
		this.fileNum0s = fileNum0s;
		return this;
	}

	public TreepInfoQuery fileNum1(Integer fileNum1) {
		if (fileNum1 == null) {
			throw new RuntimeException("fileNum1 is null");
		}
		this.fileNum1 = fileNum1;
		return this;
	}

	public TreepInfoQuery fileNum1GreaterThanOrEqual(
			Integer fileNum1GreaterThanOrEqual) {
		if (fileNum1GreaterThanOrEqual == null) {
			throw new RuntimeException("fileNum1 is null");
		}
		this.fileNum1GreaterThanOrEqual = fileNum1GreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery fileNum1LessThanOrEqual(
			Integer fileNum1LessThanOrEqual) {
		if (fileNum1LessThanOrEqual == null) {
			throw new RuntimeException("fileNum1 is null");
		}
		this.fileNum1LessThanOrEqual = fileNum1LessThanOrEqual;
		return this;
	}

	public TreepInfoQuery fileNum1s(List<Integer> fileNum1s) {
		if (fileNum1s == null) {
			throw new RuntimeException("fileNum1s is empty ");
		}
		this.fileNum1s = fileNum1s;
		return this;
	}

	public TreepInfoQuery filenum2(Integer filenum2) {
		if (filenum2 == null) {
			throw new RuntimeException("filenum2 is null");
		}
		this.filenum2 = filenum2;
		return this;
	}

	public TreepInfoQuery filenum2GreaterThanOrEqual(
			Integer filenum2GreaterThanOrEqual) {
		if (filenum2GreaterThanOrEqual == null) {
			throw new RuntimeException("filenum2 is null");
		}
		this.filenum2GreaterThanOrEqual = filenum2GreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery filenum2LessThanOrEqual(
			Integer filenum2LessThanOrEqual) {
		if (filenum2LessThanOrEqual == null) {
			throw new RuntimeException("filenum2 is null");
		}
		this.filenum2LessThanOrEqual = filenum2LessThanOrEqual;
		return this;
	}

	public TreepInfoQuery filenum2s(List<Integer> filenum2s) {
		if (filenum2s == null) {
			throw new RuntimeException("filenum2s is empty ");
		}
		this.filenum2s = filenum2s;
		return this;
	}

	public TreepInfoQuery projType(String projType) {
		if (projType == null) {
			throw new RuntimeException("projType is null");
		}
		this.projType = projType;
		return this;
	}

	public TreepInfoQuery projTypeLike(String projTypeLike) {
		if (projTypeLike == null) {
			throw new RuntimeException("projType is null");
		}
		this.projTypeLike = projTypeLike;
		return this;
	}

	public TreepInfoQuery projTypes(List<String> projTypes) {
		if (projTypes == null) {
			throw new RuntimeException("projTypes is empty ");
		}
		this.projTypes = projTypes;
		return this;
	}

	public TreepInfoQuery cid(String cid) {
		if (cid == null) {
			throw new RuntimeException("cid is null");
		}
		this.cid = cid;
		return this;
	}

	public TreepInfoQuery cidLike(String cidLike) {
		if (cidLike == null) {
			throw new RuntimeException("cid is null");
		}
		this.cidLike = cidLike;
		return this;
	}

	public TreepInfoQuery cids(List<String> cids) {
		if (cids == null) {
			throw new RuntimeException("cids is empty ");
		}
		this.cids = cids;
		return this;
	}

	public TreepInfoQuery dwid(Integer dwid) {
		if (dwid == null) {
			throw new RuntimeException("dwid is null");
		}
		this.dwid = dwid;
		return this;
	}

	public TreepInfoQuery dwidGreaterThanOrEqual(Integer dwidGreaterThanOrEqual) {
		if (dwidGreaterThanOrEqual == null) {
			throw new RuntimeException("dwid is null");
		}
		this.dwidGreaterThanOrEqual = dwidGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery dwidLessThanOrEqual(Integer dwidLessThanOrEqual) {
		if (dwidLessThanOrEqual == null) {
			throw new RuntimeException("dwid is null");
		}
		this.dwidLessThanOrEqual = dwidLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery dwids(List<Integer> dwids) {
		if (dwids == null) {
			throw new RuntimeException("dwids is empty ");
		}
		this.dwids = dwids;
		return this;
	}

	public TreepInfoQuery fxid(Integer fxid) {
		if (fxid == null) {
			throw new RuntimeException("fxid is null");
		}
		this.fxid = fxid;
		return this;
	}

	public TreepInfoQuery fxidGreaterThanOrEqual(Integer fxidGreaterThanOrEqual) {
		if (fxidGreaterThanOrEqual == null) {
			throw new RuntimeException("fxid is null");
		}
		this.fxidGreaterThanOrEqual = fxidGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery fxidLessThanOrEqual(Integer fxidLessThanOrEqual) {
		if (fxidLessThanOrEqual == null) {
			throw new RuntimeException("fxid is null");
		}
		this.fxidLessThanOrEqual = fxidLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery fxids(List<Integer> fxids) {
		if (fxids == null) {
			throw new RuntimeException("fxids is empty ");
		}
		this.fxids = fxids;
		return this;
	}

	public TreepInfoQuery fbid(Integer fbid) {
		if (fbid == null) {
			throw new RuntimeException("fbid is null");
		}
		this.fbid = fbid;
		return this;
	}

	public TreepInfoQuery fbidGreaterThanOrEqual(Integer fbidGreaterThanOrEqual) {
		if (fbidGreaterThanOrEqual == null) {
			throw new RuntimeException("fbid is null");
		}
		this.fbidGreaterThanOrEqual = fbidGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery fbidLessThanOrEqual(Integer fbidLessThanOrEqual) {
		if (fbidLessThanOrEqual == null) {
			throw new RuntimeException("fbid is null");
		}
		this.fbidLessThanOrEqual = fbidLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery fbids(List<Integer> fbids) {
		if (fbids == null) {
			throw new RuntimeException("fbids is empty ");
		}
		this.fbids = fbids;
		return this;
	}

	public TreepInfoQuery jid(String jid) {
		if (jid == null) {
			throw new RuntimeException("jid is null");
		}
		this.jid = jid;
		return this;
	}

	public TreepInfoQuery jidLike(String jidLike) {
		if (jidLike == null) {
			throw new RuntimeException("jid is null");
		}
		this.jidLike = jidLike;
		return this;
	}

	public TreepInfoQuery jids(List<String> jids) {
		if (jids == null) {
			throw new RuntimeException("jids is empty ");
		}
		this.jids = jids;
		return this;
	}

	public TreepInfoQuery flid(String flid) {
		if (flid == null) {
			throw new RuntimeException("flid is null");
		}
		this.flid = flid;
		return this;
	}

	public TreepInfoQuery flidLike(String flidLike) {
		if (flidLike == null) {
			throw new RuntimeException("flid is null");
		}
		this.flidLike = flidLike;
		return this;
	}

	public TreepInfoQuery flids(List<String> flids) {
		if (flids == null) {
			throw new RuntimeException("flids is empty ");
		}
		this.flids = flids;
		return this;
	}

	public TreepInfoQuery topNode(String topNode) {
		if (topNode == null) {
			throw new RuntimeException("topNode is null");
		}
		this.topNode = topNode;
		return this;
	}

	public TreepInfoQuery topNodeLike(String topNodeLike) {
		if (topNodeLike == null) {
			throw new RuntimeException("topNode is null");
		}
		this.topNodeLike = topNodeLike;
		return this;
	}

	public TreepInfoQuery topNodes(List<String> topNodes) {
		if (topNodes == null) {
			throw new RuntimeException("topNodes is empty ");
		}
		this.topNodes = topNodes;
		return this;
	}

	public TreepInfoQuery nodeIco(Integer nodeIco) {
		if (nodeIco == null) {
			throw new RuntimeException("nodeIco is null");
		}
		this.nodeIco = nodeIco;
		return this;
	}

	public TreepInfoQuery nodeIcoGreaterThanOrEqual(
			Integer nodeIcoGreaterThanOrEqual) {
		if (nodeIcoGreaterThanOrEqual == null) {
			throw new RuntimeException("nodeIco is null");
		}
		this.nodeIcoGreaterThanOrEqual = nodeIcoGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery nodeIcoLessThanOrEqual(Integer nodeIcoLessThanOrEqual) {
		if (nodeIcoLessThanOrEqual == null) {
			throw new RuntimeException("nodeIco is null");
		}
		this.nodeIcoLessThanOrEqual = nodeIcoLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery nodeIcos(List<Integer> nodeIcos) {
		if (nodeIcos == null) {
			throw new RuntimeException("nodeIcos is empty ");
		}
		this.nodeIcos = nodeIcos;
		return this;
	}

	public TreepInfoQuery outFlag(String outFlag) {
		if (outFlag == null) {
			throw new RuntimeException("outFlag is null");
		}
		this.outFlag = outFlag;
		return this;
	}

	public TreepInfoQuery outFlagLike(String outFlagLike) {
		if (outFlagLike == null) {
			throw new RuntimeException("outFlag is null");
		}
		this.outFlagLike = outFlagLike;
		return this;
	}

	public TreepInfoQuery outFlags(List<String> outFlags) {
		if (outFlags == null) {
			throw new RuntimeException("outFlags is empty ");
		}
		this.outFlags = outFlags;
		return this;
	}

	public TreepInfoQuery inFlag(String inFlag) {
		if (inFlag == null) {
			throw new RuntimeException("inFlag is null");
		}
		this.inFlag = inFlag;
		return this;
	}

	public TreepInfoQuery inFlagLike(String inFlagLike) {
		if (inFlagLike == null) {
			throw new RuntimeException("inFlag is null");
		}
		this.inFlagLike = inFlagLike;
		return this;
	}

	public TreepInfoQuery inFlags(List<String> inFlags) {
		if (inFlags == null) {
			throw new RuntimeException("inFlags is empty ");
		}
		this.inFlags = inFlags;
		return this;
	}

	public TreepInfoQuery password(String password) {
		if (password == null) {
			throw new RuntimeException("password is null");
		}
		this.password = password;
		return this;
	}

	public TreepInfoQuery passwordLike(String passwordLike) {
		if (passwordLike == null) {
			throw new RuntimeException("password is null");
		}
		this.passwordLike = passwordLike;
		return this;
	}

	public TreepInfoQuery passwords(List<String> passwords) {
		if (passwords == null) {
			throw new RuntimeException("passwords is empty ");
		}
		this.passwords = passwords;
		return this;
	}

	public TreepInfoQuery listNum(String listNum) {
		if (listNum == null) {
			throw new RuntimeException("listNum is null");
		}
		this.listNum = listNum;
		return this;
	}

	public TreepInfoQuery listNumLike(String listNumLike) {
		if (listNumLike == null) {
			throw new RuntimeException("listNum is null");
		}
		this.listNumLike = listNumLike;
		return this;
	}

	public TreepInfoQuery listNums(List<String> listNums) {
		if (listNums == null) {
			throw new RuntimeException("listNums is empty ");
		}
		this.listNums = listNums;
		return this;
	}

	public TreepInfoQuery wcompany(String wcompany) {
		if (wcompany == null) {
			throw new RuntimeException("wcompany is null");
		}
		this.wcompany = wcompany;
		return this;
	}

	public TreepInfoQuery wcompanyLike(String wcompanyLike) {
		if (wcompanyLike == null) {
			throw new RuntimeException("wcompany is null");
		}
		this.wcompanyLike = wcompanyLike;
		return this;
	}

	public TreepInfoQuery wcompanys(List<String> wcompanys) {
		if (wcompanys == null) {
			throw new RuntimeException("wcompanys is empty ");
		}
		this.wcompanys = wcompanys;
		return this;
	}

	public TreepInfoQuery listNo(Integer listNo) {
		if (listNo == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNo = listNo;
		return this;
	}

	public TreepInfoQuery listNoGreaterThanOrEqual(
			Integer listNoGreaterThanOrEqual) {
		if (listNoGreaterThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoGreaterThanOrEqual = listNoGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery listNoLessThanOrEqual(Integer listNoLessThanOrEqual) {
		if (listNoLessThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoLessThanOrEqual = listNoLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery listNos(List<Integer> listNos) {
		if (listNos == null) {
			throw new RuntimeException("listNos is empty ");
		}
		this.listNos = listNos;
		return this;
	}

	public TreepInfoQuery userNmu(String userNmu) {
		if (userNmu == null) {
			throw new RuntimeException("userNmu is null");
		}
		this.userNmu = userNmu;
		return this;
	}

	public TreepInfoQuery userNmuLike(String userNmuLike) {
		if (userNmuLike == null) {
			throw new RuntimeException("userNmu is null");
		}
		this.userNmuLike = userNmuLike;
		return this;
	}

	public TreepInfoQuery userNmus(List<String> userNmus) {
		if (userNmus == null) {
			throw new RuntimeException("userNmus is empty ");
		}
		this.userNmus = userNmus;
		return this;
	}

	public TreepInfoQuery indexIdOld(Integer indexIdOld) {
		if (indexIdOld == null) {
			throw new RuntimeException("indexIdOld is null");
		}
		this.indexIdOld = indexIdOld;
		return this;
	}

	public TreepInfoQuery indexIdOldGreaterThanOrEqual(
			Integer indexIdOldGreaterThanOrEqual) {
		if (indexIdOldGreaterThanOrEqual == null) {
			throw new RuntimeException("indexIdOld is null");
		}
		this.indexIdOldGreaterThanOrEqual = indexIdOldGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery indexIdOldLessThanOrEqual(
			Integer indexIdOldLessThanOrEqual) {
		if (indexIdOldLessThanOrEqual == null) {
			throw new RuntimeException("indexIdOld is null");
		}
		this.indexIdOldLessThanOrEqual = indexIdOldLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery indexIdOlds(List<Integer> indexIdOlds) {
		if (indexIdOlds == null) {
			throw new RuntimeException("indexIdOlds is empty ");
		}
		this.indexIdOlds = indexIdOlds;
		return this;
	}

	public TreepInfoQuery pindexId(Integer pindexId) {
		if (pindexId == null) {
			throw new RuntimeException("pindexId is null");
		}
		this.pindexId = pindexId;
		return this;
	}

	public TreepInfoQuery pindexIdGreaterThanOrEqual(
			Integer pindexIdGreaterThanOrEqual) {
		if (pindexIdGreaterThanOrEqual == null) {
			throw new RuntimeException("pindexId is null");
		}
		this.pindexIdGreaterThanOrEqual = pindexIdGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery pindexIdLessThanOrEqual(
			Integer pindexIdLessThanOrEqual) {
		if (pindexIdLessThanOrEqual == null) {
			throw new RuntimeException("pindexId is null");
		}
		this.pindexIdLessThanOrEqual = pindexIdLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery pindexIds(List<Integer> pindexIds) {
		if (pindexIds == null) {
			throw new RuntimeException("pindexIds is empty ");
		}
		this.pindexIds = pindexIds;
		return this;
	}

	public TreepInfoQuery finishInt(Integer finishInt) {
		if (finishInt == null) {
			throw new RuntimeException("finishInt is null");
		}
		this.finishInt = finishInt;
		return this;
	}

	public TreepInfoQuery finishIntGreaterThanOrEqual(
			Integer finishIntGreaterThanOrEqual) {
		if (finishIntGreaterThanOrEqual == null) {
			throw new RuntimeException("finishInt is null");
		}
		this.finishIntGreaterThanOrEqual = finishIntGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery finishIntLessThanOrEqual(
			Integer finishIntLessThanOrEqual) {
		if (finishIntLessThanOrEqual == null) {
			throw new RuntimeException("finishInt is null");
		}
		this.finishIntLessThanOrEqual = finishIntLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery finishInts(List<Integer> finishInts) {
		if (finishInts == null) {
			throw new RuntimeException("finishInts is empty ");
		}
		this.finishInts = finishInts;
		return this;
	}

	public TreepInfoQuery typeTableName(String typeTableName) {
		if (typeTableName == null) {
			throw new RuntimeException("typeTableName is null");
		}
		this.typeTableName = typeTableName;
		return this;
	}

	public TreepInfoQuery typeTableNameLike(String typeTableNameLike) {
		if (typeTableNameLike == null) {
			throw new RuntimeException("typeTableName is null");
		}
		this.typeTableNameLike = typeTableNameLike;
		return this;
	}

	public TreepInfoQuery typeTableNames(List<String> typeTableNames) {
		if (typeTableNames == null) {
			throw new RuntimeException("typeTableNames is empty ");
		}
		this.typeTableNames = typeTableNames;
		return this;
	}

	public TreepInfoQuery treedListStr(String treedListStr) {
		if (treedListStr == null) {
			throw new RuntimeException("treedListStr is null");
		}
		this.treedListStr = treedListStr;
		return this;
	}

	public TreepInfoQuery treedListStrLike(String treedListStrLike) {
		if (treedListStrLike == null) {
			throw new RuntimeException("treedListStr is null");
		}
		this.treedListStrLike = treedListStrLike;
		return this;
	}

	public TreepInfoQuery treedListStrs(List<String> treedListStrs) {
		if (treedListStrs == null) {
			throw new RuntimeException("treedListStrs is empty ");
		}
		this.treedListStrs = treedListStrs;
		return this;
	}

	public TreepInfoQuery pfilesIndex(Integer pfilesIndex) {
		if (pfilesIndex == null) {
			throw new RuntimeException("pfilesIndex is null");
		}
		this.pfilesIndex = pfilesIndex;
		return this;
	}

	public TreepInfoQuery pfilesIndexGreaterThanOrEqual(
			Integer pfilesIndexGreaterThanOrEqual) {
		if (pfilesIndexGreaterThanOrEqual == null) {
			throw new RuntimeException("pfilesIndex is null");
		}
		this.pfilesIndexGreaterThanOrEqual = pfilesIndexGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery pfilesIndexLessThanOrEqual(
			Integer pfilesIndexLessThanOrEqual) {
		if (pfilesIndexLessThanOrEqual == null) {
			throw new RuntimeException("pfilesIndex is null");
		}
		this.pfilesIndexLessThanOrEqual = pfilesIndexLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery pfilesIndexs(List<Integer> pfilesIndexs) {
		if (pfilesIndexs == null) {
			throw new RuntimeException("pfilesIndexs is empty ");
		}
		this.pfilesIndexs = pfilesIndexs;
		return this;
	}

	public TreepInfoQuery bdateGreaterThanOrEqual(Date bdateGreaterThanOrEqual) {
		if (bdateGreaterThanOrEqual == null) {
			throw new RuntimeException("bdate is null");
		}
		this.bdateGreaterThanOrEqual = bdateGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery bdateLessThanOrEqual(Date bdateLessThanOrEqual) {
		if (bdateLessThanOrEqual == null) {
			throw new RuntimeException("bdate is null");
		}
		this.bdateLessThanOrEqual = bdateLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery edateGreaterThanOrEqual(Date edateGreaterThanOrEqual) {
		if (edateGreaterThanOrEqual == null) {
			throw new RuntimeException("edate is null");
		}
		this.edateGreaterThanOrEqual = edateGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery edateLessThanOrEqual(Date edateLessThanOrEqual) {
		if (edateLessThanOrEqual == null) {
			throw new RuntimeException("edate is null");
		}
		this.edateLessThanOrEqual = edateLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery wbsIndex(Integer wbsIndex) {
		if (wbsIndex == null) {
			throw new RuntimeException("wbsIndex is null");
		}
		this.wbsIndex = wbsIndex;
		return this;
	}

	public TreepInfoQuery wbsIndexGreaterThanOrEqual(
			Integer wbsIndexGreaterThanOrEqual) {
		if (wbsIndexGreaterThanOrEqual == null) {
			throw new RuntimeException("wbsIndex is null");
		}
		this.wbsIndexGreaterThanOrEqual = wbsIndexGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery wbsIndexLessThanOrEqual(
			Integer wbsIndexLessThanOrEqual) {
		if (wbsIndexLessThanOrEqual == null) {
			throw new RuntimeException("wbsIndex is null");
		}
		this.wbsIndexLessThanOrEqual = wbsIndexLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery wbsIndexs(List<Integer> wbsIndexs) {
		if (wbsIndexs == null) {
			throw new RuntimeException("wbsIndexs is empty ");
		}
		this.wbsIndexs = wbsIndexs;
		return this;
	}

	public TreepInfoQuery bdatesGreaterThanOrEqual(Date bdatesGreaterThanOrEqual) {
		if (bdatesGreaterThanOrEqual == null) {
			throw new RuntimeException("bdates is null");
		}
		this.bdatesGreaterThanOrEqual = bdatesGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery bdatesLessThanOrEqual(Date bdatesLessThanOrEqual) {
		if (bdatesLessThanOrEqual == null) {
			throw new RuntimeException("bdates is null");
		}
		this.bdatesLessThanOrEqual = bdatesLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery edatesGreaterThanOrEqual(Date edatesGreaterThanOrEqual) {
		if (edatesGreaterThanOrEqual == null) {
			throw new RuntimeException("edates is null");
		}
		this.edatesGreaterThanOrEqual = edatesGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery edatesLessThanOrEqual(Date edatesLessThanOrEqual) {
		if (edatesLessThanOrEqual == null) {
			throw new RuntimeException("edates is null");
		}
		this.edatesLessThanOrEqual = edatesLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery typeId(String typeId) {
		if (typeId == null) {
			throw new RuntimeException("typeId is null");
		}
		this.typeId = typeId;
		return this;
	}

	public TreepInfoQuery typeIdLike(String typeIdLike) {
		if (typeIdLike == null) {
			throw new RuntimeException("typeId is null");
		}
		this.typeIdLike = typeIdLike;
		return this;
	}

	public TreepInfoQuery typeIds(List<String> typeIds) {
		if (typeIds == null) {
			throw new RuntimeException("typeIds is empty ");
		}
		this.typeIds = typeIds;
		return this;
	}

	public TreepInfoQuery cell1(Integer cell1) {
		if (cell1 == null) {
			throw new RuntimeException("cell1 is null");
		}
		this.cell1 = cell1;
		return this;
	}

	public TreepInfoQuery cell1GreaterThanOrEqual(
			Integer cell1GreaterThanOrEqual) {
		if (cell1GreaterThanOrEqual == null) {
			throw new RuntimeException("cell1 is null");
		}
		this.cell1GreaterThanOrEqual = cell1GreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery cell1LessThanOrEqual(Integer cell1LessThanOrEqual) {
		if (cell1LessThanOrEqual == null) {
			throw new RuntimeException("cell1 is null");
		}
		this.cell1LessThanOrEqual = cell1LessThanOrEqual;
		return this;
	}

	public TreepInfoQuery cell1s(List<Integer> cell1s) {
		if (cell1s == null) {
			throw new RuntimeException("cell1s is empty ");
		}
		this.cell1s = cell1s;
		return this;
	}

	public TreepInfoQuery cell2(Integer cell2) {
		if (cell2 == null) {
			throw new RuntimeException("cell2 is null");
		}
		this.cell2 = cell2;
		return this;
	}

	public TreepInfoQuery cell2GreaterThanOrEqual(
			Integer cell2GreaterThanOrEqual) {
		if (cell2GreaterThanOrEqual == null) {
			throw new RuntimeException("cell2 is null");
		}
		this.cell2GreaterThanOrEqual = cell2GreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery cell2LessThanOrEqual(Integer cell2LessThanOrEqual) {
		if (cell2LessThanOrEqual == null) {
			throw new RuntimeException("cell2 is null");
		}
		this.cell2LessThanOrEqual = cell2LessThanOrEqual;
		return this;
	}

	public TreepInfoQuery cell2s(List<Integer> cell2s) {
		if (cell2s == null) {
			throw new RuntimeException("cell2s is empty ");
		}
		this.cell2s = cell2s;
		return this;
	}

	public TreepInfoQuery cell3(Integer cell3) {
		if (cell3 == null) {
			throw new RuntimeException("cell3 is null");
		}
		this.cell3 = cell3;
		return this;
	}

	public TreepInfoQuery cell3GreaterThanOrEqual(
			Integer cell3GreaterThanOrEqual) {
		if (cell3GreaterThanOrEqual == null) {
			throw new RuntimeException("cell3 is null");
		}
		this.cell3GreaterThanOrEqual = cell3GreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery cell3LessThanOrEqual(Integer cell3LessThanOrEqual) {
		if (cell3LessThanOrEqual == null) {
			throw new RuntimeException("cell3 is null");
		}
		this.cell3LessThanOrEqual = cell3LessThanOrEqual;
		return this;
	}

	public TreepInfoQuery cell3s(List<Integer> cell3s) {
		if (cell3s == null) {
			throw new RuntimeException("cell3s is empty ");
		}
		this.cell3s = cell3s;
		return this;
	}

	public TreepInfoQuery strFileLocate(String strFileLocate) {
		if (strFileLocate == null) {
			throw new RuntimeException("strFileLocate is null");
		}
		this.strFileLocate = strFileLocate;
		return this;
	}

	public TreepInfoQuery strFileLocateLike(String strFileLocateLike) {
		if (strFileLocateLike == null) {
			throw new RuntimeException("strFileLocate is null");
		}
		this.strFileLocateLike = strFileLocateLike;
		return this;
	}

	public TreepInfoQuery strFileLocates(List<String> strFileLocates) {
		if (strFileLocates == null) {
			throw new RuntimeException("strFileLocates is empty ");
		}
		this.strFileLocates = strFileLocates;
		return this;
	}

	public TreepInfoQuery intPfile1(Integer intPfile1) {
		if (intPfile1 == null) {
			throw new RuntimeException("intPfile1 is null");
		}
		this.intPfile1 = intPfile1;
		return this;
	}

	public TreepInfoQuery intPfile1GreaterThanOrEqual(
			Integer intPfile1GreaterThanOrEqual) {
		if (intPfile1GreaterThanOrEqual == null) {
			throw new RuntimeException("intPfile1 is null");
		}
		this.intPfile1GreaterThanOrEqual = intPfile1GreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery intPfile1LessThanOrEqual(
			Integer intPfile1LessThanOrEqual) {
		if (intPfile1LessThanOrEqual == null) {
			throw new RuntimeException("intPfile1 is null");
		}
		this.intPfile1LessThanOrEqual = intPfile1LessThanOrEqual;
		return this;
	}

	public TreepInfoQuery intPfile1s(List<Integer> intPfile1s) {
		if (intPfile1s == null) {
			throw new RuntimeException("intPfile1s is empty ");
		}
		this.intPfile1s = intPfile1s;
		return this;
	}

	public TreepInfoQuery intPfile2(Integer intPfile2) {
		if (intPfile2 == null) {
			throw new RuntimeException("intPfile2 is null");
		}
		this.intPfile2 = intPfile2;
		return this;
	}

	public TreepInfoQuery intPfile2GreaterThanOrEqual(
			Integer intPfile2GreaterThanOrEqual) {
		if (intPfile2GreaterThanOrEqual == null) {
			throw new RuntimeException("intPfile2 is null");
		}
		this.intPfile2GreaterThanOrEqual = intPfile2GreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery intPfile2LessThanOrEqual(
			Integer intPfile2LessThanOrEqual) {
		if (intPfile2LessThanOrEqual == null) {
			throw new RuntimeException("intPfile2 is null");
		}
		this.intPfile2LessThanOrEqual = intPfile2LessThanOrEqual;
		return this;
	}

	public TreepInfoQuery intPfile2s(List<Integer> intPfile2s) {
		if (intPfile2s == null) {
			throw new RuntimeException("intPfile2s is empty ");
		}
		this.intPfile2s = intPfile2s;
		return this;
	}

	public TreepInfoQuery intPfile3(Integer intPfile3) {
		if (intPfile3 == null) {
			throw new RuntimeException("intPfile3 is null");
		}
		this.intPfile3 = intPfile3;
		return this;
	}

	public TreepInfoQuery intPfile3GreaterThanOrEqual(
			Integer intPfile3GreaterThanOrEqual) {
		if (intPfile3GreaterThanOrEqual == null) {
			throw new RuntimeException("intPfile3 is null");
		}
		this.intPfile3GreaterThanOrEqual = intPfile3GreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery intPfile3LessThanOrEqual(
			Integer intPfile3LessThanOrEqual) {
		if (intPfile3LessThanOrEqual == null) {
			throw new RuntimeException("intPfile3 is null");
		}
		this.intPfile3LessThanOrEqual = intPfile3LessThanOrEqual;
		return this;
	}

	public TreepInfoQuery intPfile3s(List<Integer> intPfile3s) {
		if (intPfile3s == null) {
			throw new RuntimeException("intPfile3s is empty ");
		}
		this.intPfile3s = intPfile3s;
		return this;
	}

	public TreepInfoQuery intCellFinish(Integer intCellFinish) {
		if (intCellFinish == null) {
			throw new RuntimeException("intCellFinish is null");
		}
		this.intCellFinish = intCellFinish;
		return this;
	}

	public TreepInfoQuery intCellFinishGreaterThanOrEqual(
			Integer intCellFinishGreaterThanOrEqual) {
		if (intCellFinishGreaterThanOrEqual == null) {
			throw new RuntimeException("intCellFinish is null");
		}
		this.intCellFinishGreaterThanOrEqual = intCellFinishGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery intCellFinishLessThanOrEqual(
			Integer intCellFinishLessThanOrEqual) {
		if (intCellFinishLessThanOrEqual == null) {
			throw new RuntimeException("intCellFinish is null");
		}
		this.intCellFinishLessThanOrEqual = intCellFinishLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery intCellFinishs(List<Integer> intCellFinishs) {
		if (intCellFinishs == null) {
			throw new RuntimeException("intCellFinishs is empty ");
		}
		this.intCellFinishs = intCellFinishs;
		return this;
	}

	public TreepInfoQuery sysId(String sysId) {
		if (sysId == null) {
			throw new RuntimeException("sysId is null");
		}
		this.sysId = sysId;
		return this;
	}

	public TreepInfoQuery sysIdLike(String sysIdLike) {
		if (sysIdLike == null) {
			throw new RuntimeException("sysId is null");
		}
		this.sysIdLike = sysIdLike;
		return this;
	}

	public TreepInfoQuery sysIds(List<String> sysIds) {
		if (sysIds == null) {
			throw new RuntimeException("sysIds is empty ");
		}
		this.sysIds = sysIds;
		return this;
	}

	public TreepInfoQuery indexIn(Integer indexIn) {
		if (indexIn == null) {
			throw new RuntimeException("indexIn is null");
		}
		this.indexIn = indexIn;
		return this;
	}

	public TreepInfoQuery indexInGreaterThanOrEqual(
			Integer indexInGreaterThanOrEqual) {
		if (indexInGreaterThanOrEqual == null) {
			throw new RuntimeException("indexIn is null");
		}
		this.indexInGreaterThanOrEqual = indexInGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery indexInLessThanOrEqual(Integer indexInLessThanOrEqual) {
		if (indexInLessThanOrEqual == null) {
			throw new RuntimeException("indexIn is null");
		}
		this.indexInLessThanOrEqual = indexInLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery indexIns(List<Integer> indexIns) {
		if (indexIns == null) {
			throw new RuntimeException("indexIns is empty ");
		}
		this.indexIns = indexIns;
		return this;
	}

	public TreepInfoQuery strButtonStar(String strButtonStar) {
		if (strButtonStar == null) {
			throw new RuntimeException("strButtonStar is null");
		}
		this.strButtonStar = strButtonStar;
		return this;
	}

	public TreepInfoQuery strButtonStarLike(String strButtonStarLike) {
		if (strButtonStarLike == null) {
			throw new RuntimeException("strButtonStar is null");
		}
		this.strButtonStarLike = strButtonStarLike;
		return this;
	}

	public TreepInfoQuery strButtonStars(List<String> strButtonStars) {
		if (strButtonStars == null) {
			throw new RuntimeException("strButtonStars is empty ");
		}
		this.strButtonStars = strButtonStars;
		return this;
	}

	public TreepInfoQuery intIsUse(Integer intIsUse) {
		if (intIsUse == null) {
			throw new RuntimeException("intIsUse is null");
		}
		this.intIsUse = intIsUse;
		return this;
	}

	public TreepInfoQuery intIsUseGreaterThanOrEqual(
			Integer intIsUseGreaterThanOrEqual) {
		if (intIsUseGreaterThanOrEqual == null) {
			throw new RuntimeException("intIsUse is null");
		}
		this.intIsUseGreaterThanOrEqual = intIsUseGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery intIsUseLessThanOrEqual(
			Integer intIsUseLessThanOrEqual) {
		if (intIsUseLessThanOrEqual == null) {
			throw new RuntimeException("intIsUse is null");
		}
		this.intIsUseLessThanOrEqual = intIsUseLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery intIsUses(List<Integer> intIsUses) {
		if (intIsUses == null) {
			throw new RuntimeException("intIsUses is empty ");
		}
		this.intIsUses = intIsUses;
		return this;
	}

	public TreepInfoQuery wbsIndexIn(Integer wbsIndexIn) {
		if (wbsIndexIn == null) {
			throw new RuntimeException("wbsIndexIn is null");
		}
		this.wbsIndexIn = wbsIndexIn;
		return this;
	}

	public TreepInfoQuery wbsIndexInGreaterThanOrEqual(
			Integer wbsIndexInGreaterThanOrEqual) {
		if (wbsIndexInGreaterThanOrEqual == null) {
			throw new RuntimeException("wbsIndexIn is null");
		}
		this.wbsIndexInGreaterThanOrEqual = wbsIndexInGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery wbsIndexInLessThanOrEqual(
			Integer wbsIndexInLessThanOrEqual) {
		if (wbsIndexInLessThanOrEqual == null) {
			throw new RuntimeException("wbsIndexIn is null");
		}
		this.wbsIndexInLessThanOrEqual = wbsIndexInLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery wbsIndexIns(List<Integer> wbsIndexIns) {
		if (wbsIndexIns == null) {
			throw new RuntimeException("wbsIndexIns is empty ");
		}
		this.wbsIndexIns = wbsIndexIns;
		return this;
	}

	public TreepInfoQuery uindexId(Integer uindexId) {
		if (uindexId == null) {
			throw new RuntimeException("uindexId is null");
		}
		this.uindexId = uindexId;
		return this;
	}

	public TreepInfoQuery uindexIdGreaterThanOrEqual(
			Integer uindexIdGreaterThanOrEqual) {
		if (uindexIdGreaterThanOrEqual == null) {
			throw new RuntimeException("uindexId is null");
		}
		this.uindexIdGreaterThanOrEqual = uindexIdGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery uindexIdLessThanOrEqual(
			Integer uindexIdLessThanOrEqual) {
		if (uindexIdLessThanOrEqual == null) {
			throw new RuntimeException("uindexId is null");
		}
		this.uindexIdLessThanOrEqual = uindexIdLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery uindexIds(List<Integer> uindexIds) {
		if (uindexIds == null) {
			throw new RuntimeException("uindexIds is empty ");
		}
		this.uindexIds = uindexIds;
		return this;
	}

	public TreepInfoQuery lisnoWBS(Integer lisnoWBS) {
		if (lisnoWBS == null) {
			throw new RuntimeException("lisnoWBS is null");
		}
		this.lisnoWBS = lisnoWBS;
		return this;
	}

	public TreepInfoQuery lisnoWBSGreaterThanOrEqual(
			Integer lisnoWBSGreaterThanOrEqual) {
		if (lisnoWBSGreaterThanOrEqual == null) {
			throw new RuntimeException("lisnoWBS is null");
		}
		this.lisnoWBSGreaterThanOrEqual = lisnoWBSGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery lisnoWBSLessThanOrEqual(
			Integer lisnoWBSLessThanOrEqual) {
		if (lisnoWBSLessThanOrEqual == null) {
			throw new RuntimeException("lisnoWBS is null");
		}
		this.lisnoWBSLessThanOrEqual = lisnoWBSLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery lisnoWBSs(List<Integer> lisnoWBSs) {
		if (lisnoWBSs == null) {
			throw new RuntimeException("lisnoWBSs is empty ");
		}
		this.lisnoWBSs = lisnoWBSs;
		return this;
	}

	public TreepInfoQuery sysIdAdd(String sysIdAdd) {
		if (sysIdAdd == null) {
			throw new RuntimeException("sysIdAdd is null");
		}
		this.sysIdAdd = sysIdAdd;
		return this;
	}

	public TreepInfoQuery sysIdAddLike(String sysIdAddLike) {
		if (sysIdAddLike == null) {
			throw new RuntimeException("sysIdAdd is null");
		}
		this.sysIdAddLike = sysIdAddLike;
		return this;
	}

	public TreepInfoQuery sysIdAdds(List<String> sysIdAdds) {
		if (sysIdAdds == null) {
			throw new RuntimeException("sysIdAdds is empty ");
		}
		this.sysIdAdds = sysIdAdds;
		return this;
	}

	public TreepInfoQuery indexInAdd(Integer indexInAdd) {
		if (indexInAdd == null) {
			throw new RuntimeException("indexInAdd is null");
		}
		this.indexInAdd = indexInAdd;
		return this;
	}

	public TreepInfoQuery indexInAddGreaterThanOrEqual(
			Integer indexInAddGreaterThanOrEqual) {
		if (indexInAddGreaterThanOrEqual == null) {
			throw new RuntimeException("indexInAdd is null");
		}
		this.indexInAddGreaterThanOrEqual = indexInAddGreaterThanOrEqual;
		return this;
	}

	public TreepInfoQuery indexInAddLessThanOrEqual(
			Integer indexInAddLessThanOrEqual) {
		if (indexInAddLessThanOrEqual == null) {
			throw new RuntimeException("indexInAdd is null");
		}
		this.indexInAddLessThanOrEqual = indexInAddLessThanOrEqual;
		return this;
	}

	public TreepInfoQuery indexInAdds(List<Integer> indexInAdds) {
		if (indexInAdds == null) {
			throw new RuntimeException("indexInAdds is empty ");
		}
		this.indexInAdds = indexInAdds;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("id".equals(sortColumn)) {
				orderBy = "E.ID" + a_x;
			}

			if ("num".equals(sortColumn)) {
				orderBy = "E.NUM" + a_x;
			}

			if ("topId".equals(sortColumn)) {
				orderBy = "E.TOP_ID" + a_x;
			}

			if ("parentId".equals(sortColumn)) {
				orderBy = "E.PARENT_ID" + a_x;
			}

			if ("indexName".equals(sortColumn)) {
				orderBy = "E.INDEX_NAME" + a_x;
			}

			if ("level".equals(sortColumn)) {
				orderBy = "E.NLEVEL" + a_x;
			}

			if ("nodeType".equals(sortColumn)) {
				orderBy = "E.NODETYPE" + a_x;
			}

			if ("fromId".equals(sortColumn)) {
				orderBy = "E.FROMID" + a_x;
			}

			if ("partid".equals(sortColumn)) {
				orderBy = "E.PART_ID" + a_x;
			}

			if ("showId".equals(sortColumn)) {
				orderBy = "E.SHOWID" + a_x;
			}

			if ("sindexName".equals(sortColumn)) {
				orderBy = "E.SINDEX_NAME" + a_x;
			}

			if ("fileNum".equals(sortColumn)) {
				orderBy = "E.FILENUM" + a_x;
			}

			if ("fileNum0".equals(sortColumn)) {
				orderBy = "E.FILENUM0" + a_x;
			}

			if ("fileNum1".equals(sortColumn)) {
				orderBy = "E.FILENUM1" + a_x;
			}

			if ("filenum2".equals(sortColumn)) {
				orderBy = "E.FILENUM2" + a_x;
			}

			if ("projType".equals(sortColumn)) {
				orderBy = "E.PROJTYPE" + a_x;
			}

			if ("cid".equals(sortColumn)) {
				orderBy = "E.CID" + a_x;
			}

			if ("dwid".equals(sortColumn)) {
				orderBy = "E.DWID" + a_x;
			}

			if ("fxid".equals(sortColumn)) {
				orderBy = "E.FXID" + a_x;
			}

			if ("fbid".equals(sortColumn)) {
				orderBy = "E.FBID" + a_x;
			}

			if ("jid".equals(sortColumn)) {
				orderBy = "E.JID" + a_x;
			}

			if ("flid".equals(sortColumn)) {
				orderBy = "E.FLID" + a_x;
			}

			if ("topNode".equals(sortColumn)) {
				orderBy = "E.TOPNODE" + a_x;
			}

			if ("nodeIco".equals(sortColumn)) {
				orderBy = "E.NODEICO" + a_x;
			}

			if ("outFlag".equals(sortColumn)) {
				orderBy = "E.OUTFLAG" + a_x;
			}

			if ("inFlag".equals(sortColumn)) {
				orderBy = "E.INFLAG" + a_x;
			}

			if ("password".equals(sortColumn)) {
				orderBy = "E.PASSWORD" + a_x;
			}

			if ("listNum".equals(sortColumn)) {
				orderBy = "E.LISTNUM" + a_x;
			}

			if ("wcompany".equals(sortColumn)) {
				orderBy = "E.WCOMPANY" + a_x;
			}

			if ("listNo".equals(sortColumn)) {
				orderBy = "E.LISTNO" + a_x;
			}

			if ("userNmu".equals(sortColumn)) {
				orderBy = "E.USERNMU" + a_x;
			}

			if ("indexIdOld".equals(sortColumn)) {
				orderBy = "E.INDEX_ID_OLD" + a_x;
			}

			if ("pindexId".equals(sortColumn)) {
				orderBy = "E.PINDEX_ID" + a_x;
			}

			if ("finishInt".equals(sortColumn)) {
				orderBy = "E.FINISHINT" + a_x;
			}

			if ("typeTableName".equals(sortColumn)) {
				orderBy = "E.TYPE_TABLENAME" + a_x;
			}

			if ("treedListStr".equals(sortColumn)) {
				orderBy = "E.TREE_DLISTSTR" + a_x;
			}

			if ("pfilesIndex".equals(sortColumn)) {
				orderBy = "E.PFILES_INDEX" + a_x;
			}

			if ("bdate".equals(sortColumn)) {
				orderBy = "E.BDATE" + a_x;
			}

			if ("edate".equals(sortColumn)) {
				orderBy = "E.EDATE" + a_x;
			}

			if ("wbsIndex".equals(sortColumn)) {
				orderBy = "E.WBSINDEX" + a_x;
			}

			if ("bdates".equals(sortColumn)) {
				orderBy = "E.BDATE_S" + a_x;
			}

			if ("edates".equals(sortColumn)) {
				orderBy = "E.EDATE_S" + a_x;
			}

			if ("typeId".equals(sortColumn)) {
				orderBy = "E.TYPE_ID" + a_x;
			}

			if ("cell1".equals(sortColumn)) {
				orderBy = "E.CELL1" + a_x;
			}

			if ("cell2".equals(sortColumn)) {
				orderBy = "E.CELL2" + a_x;
			}

			if ("cell3".equals(sortColumn)) {
				orderBy = "E.CELL3" + a_x;
			}

			if ("strFileLocate".equals(sortColumn)) {
				orderBy = "E.STRFILELOCATE" + a_x;
			}

			if ("intPfile1".equals(sortColumn)) {
				orderBy = "E.INTPFILE1" + a_x;
			}

			if ("intPfile2".equals(sortColumn)) {
				orderBy = "E.INTPFILE2" + a_x;
			}

			if ("intPfile3".equals(sortColumn)) {
				orderBy = "E.INTPFILE3" + a_x;
			}

			if ("intCellFinish".equals(sortColumn)) {
				orderBy = "E.INTCELLFINISH" + a_x;
			}

			if ("sysId".equals(sortColumn)) {
				orderBy = "E.SYS_ID" + a_x;
			}

			if ("indexIn".equals(sortColumn)) {
				orderBy = "E.INDEX_IN" + a_x;
			}

			if ("strButtonStar".equals(sortColumn)) {
				orderBy = "E.STRBUTTONSTAR" + a_x;
			}

			if ("intIsUse".equals(sortColumn)) {
				orderBy = "E.INTISUSE" + a_x;
			}

			if ("wbsIndexIn".equals(sortColumn)) {
				orderBy = "E.WBSINDEX_IN" + a_x;
			}

			if ("uindexId".equals(sortColumn)) {
				orderBy = "E.UINDEX_ID" + a_x;
			}

			if ("lisnoWBS".equals(sortColumn)) {
				orderBy = "E.LISNO_WBS" + a_x;
			}

			if ("sysIdAdd".equals(sortColumn)) {
				orderBy = "E.SYS_ID_ADD" + a_x;
			}

			if ("indexInAdd".equals(sortColumn)) {
				orderBy = "E.INDEX_IN_ADD" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("indexId", "INDEX_ID");
		addColumn("id", "ID");
		addColumn("num", "NUM");
		addColumn("topId", "TOP_ID");
		addColumn("parentId", "PARENT_ID");
		addColumn("indexName", "INDEX_NAME");
		addColumn("level", "NLEVEL");
		addColumn("nodeType", "NODETYPE");
		addColumn("fromId", "FROMID");
		addColumn("partid", "PART_ID");
		addColumn("showId", "SHOWID");
		addColumn("sindexName", "SINDEX_NAME");
		addColumn("fileNum", "FILENUM");
		addColumn("fileNum0", "FILENUM0");
		addColumn("fileNum1", "FILENUM1");
		addColumn("filenum2", "FILENUM2");
		addColumn("projType", "PROJTYPE");
		addColumn("cid", "CID");
		addColumn("dwid", "DWID");
		addColumn("fxid", "FXID");
		addColumn("fbid", "FBID");
		addColumn("jid", "JID");
		addColumn("flid", "FLID");
		addColumn("topNode", "TOPNODE");
		addColumn("nodeIco", "NODEICO");
		addColumn("outFlag", "OUTFLAG");
		addColumn("inFlag", "INFLAG");
		addColumn("password", "PASSWORD");
		addColumn("listNum", "LISTNUM");
		addColumn("wcompany", "WCOMPANY");
		addColumn("listNo", "LISTNO");
		addColumn("userNmu", "USERNMU");
		addColumn("indexIdOld", "INDEX_ID_OLD");
		addColumn("pindexId", "PINDEX_ID");
		addColumn("finishInt", "FINISHINT");
		addColumn("typeTableName", "TYPE_TABLENAME");
		addColumn("treedListStr", "TREE_DLISTSTR");
		addColumn("pfilesIndex", "PFILES_INDEX");
		addColumn("bdate", "BDATE");
		addColumn("edate", "EDATE");
		addColumn("wbsIndex", "WBSINDEX");
		addColumn("bdates", "BDATE_S");
		addColumn("edates", "EDATE_S");
		addColumn("typeId", "TYPE_ID");
		addColumn("cell1", "CELL1");
		addColumn("cell2", "CELL2");
		addColumn("cell3", "CELL3");
		addColumn("strFileLocate", "STRFILELOCATE");
		addColumn("intPfile1", "INTPFILE1");
		addColumn("intPfile2", "INTPFILE2");
		addColumn("intPfile3", "INTPFILE3");
		addColumn("intCellFinish", "INTCELLFINISH");
		addColumn("sysId", "SYS_ID");
		addColumn("indexIn", "INDEX_IN");
		addColumn("strButtonStar", "STRBUTTONSTAR");
		addColumn("intIsUse", "INTISUSE");
		addColumn("wbsIndexIn", "WBSINDEX_IN");
		addColumn("uindexId", "UINDEX_ID");
		addColumn("lisnoWBS", "LISNO_WBS");
		addColumn("sysIdAdd", "SYS_ID_ADD");
		addColumn("indexInAdd", "INDEX_IN_ADD");
	}

}