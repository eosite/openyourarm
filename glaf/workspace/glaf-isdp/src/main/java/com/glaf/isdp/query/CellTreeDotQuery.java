package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class CellTreeDotQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Integer> indexIds;
	protected Collection<String> appActorIds;
	protected String id;
	protected String idLike;
	protected List<String> ids;
	protected String indexName;
	protected String indexNameLike;
	protected List<String> indexNames;
	protected Integer level;
	protected Integer levelGreaterThanOrEqual;
	protected Integer levelLessThanOrEqual;
	protected List<Integer> levels;
	protected String num;
	protected String numLike;
	protected List<String> nums;
	protected String content;
	protected String contentLike;
	protected List<String> contents;
	protected Integer nodeIco;
	protected Integer nodeIcoGreaterThanOrEqual;
	protected Integer nodeIcoLessThanOrEqual;
	protected List<Integer> nodeIcos;
	protected String sindexName;
	protected String sindexNameLike;
	protected List<String> sindexNames;
	protected Integer listNo;
	protected Integer listNoGreaterThanOrEqual;
	protected Integer listNoLessThanOrEqual;
	protected List<Integer> listNos;
	protected Integer viewType;
	protected Integer viewTypeGreaterThanOrEqual;
	protected Integer viewTypeLessThanOrEqual;
	protected List<Integer> viewTypes;
	protected String isMode;
	protected String isModeLike;
	protected List<String> isModes;
	protected String modeTableId;
	protected String modeTableIdLike;
	protected List<String> modeTableIds;
	protected Integer isSystem;
	protected Integer isSystemGreaterThanOrEqual;
	protected Integer isSystemLessThanOrEqual;
	protected List<Integer> isSystems;
	protected String customData;
	protected String customDataLike;
	protected List<String> customDatas;
	protected Integer intSystemSelect;
	protected Integer intSystemSelectGreaterThanOrEqual;
	protected Integer intSystemSelectLessThanOrEqual;
	protected List<Integer> intSystemSelects;
	protected Integer intUsed;
	protected Integer intUsedGreaterThanOrEqual;
	protected Integer intUsedLessThanOrEqual;
	protected List<Integer> intUseds;
	protected Integer intDel;
	protected Integer intDelGreaterThanOrEqual;
	protected Integer intDelLessThanOrEqual;
	protected List<Integer> intDels;
	protected String typeTableName;
	protected String typeTableNameLike;
	protected List<String> typeTableNames;
	protected Integer intOperation;
	protected Integer intOperationGreaterThanOrEqual;
	protected Integer intOperationLessThanOrEqual;
	protected List<Integer> intOperations;
	protected String picFile;
	protected String picFileLike;
	protected List<String> picFiles;
	protected String fileContent;
	protected String fileContentLike;
	protected List<String> fileContents;
	protected Integer intMuiFrm;
	protected Integer intMuiFrmGreaterThanOrEqual;
	protected Integer intMuiFrmLessThanOrEqual;
	protected List<Integer> intMuiFrms;
	protected Integer intNoShow;
	protected Integer intNoShowGreaterThanOrEqual;
	protected Integer intNoShowLessThanOrEqual;
	protected List<Integer> intNoShows;
	protected String typeBaseTable;
	protected String typeBaseTableLike;
	protected List<String> typeBaseTables;
	protected Integer typeIndex;
	protected Integer typeIndexGreaterThanOrEqual;
	protected Integer typeIndexLessThanOrEqual;
	protected List<Integer> typeIndexs;
	protected String gid;
	protected String gidLike;
	protected List<String> gids;
	protected String fileName;
	protected String fileNameLike;
	protected List<String> fileNames;
	protected String linkFileContent;
	protected String linkFileContentLike;
	protected List<String> linkFileContents;
	protected String linkFileName;
	protected String linkFileNameLike;
	protected List<String> linkFileNames;

	public CellTreeDotQuery() {

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

	public String getContent() {
		return content;
	}

	public String getContentLike() {
		if (contentLike != null && contentLike.trim().length() > 0) {
			if (!contentLike.startsWith("%")) {
				contentLike = "%" + contentLike;
			}
			if (!contentLike.endsWith("%")) {
				contentLike = contentLike + "%";
			}
		}
		return contentLike;
	}

	public List<String> getContents() {
		return contents;
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

	public Integer getViewType() {
		return viewType;
	}

	public Integer getViewTypeGreaterThanOrEqual() {
		return viewTypeGreaterThanOrEqual;
	}

	public Integer getViewTypeLessThanOrEqual() {
		return viewTypeLessThanOrEqual;
	}

	public List<Integer> getViewTypes() {
		return viewTypes;
	}

	public String getIsMode() {
		return isMode;
	}

	public String getIsModeLike() {
		if (isModeLike != null && isModeLike.trim().length() > 0) {
			if (!isModeLike.startsWith("%")) {
				isModeLike = "%" + isModeLike;
			}
			if (!isModeLike.endsWith("%")) {
				isModeLike = isModeLike + "%";
			}
		}
		return isModeLike;
	}

	public List<String> getIsModes() {
		return isModes;
	}

	public String getModeTableId() {
		return modeTableId;
	}

	public String getModeTableIdLike() {
		if (modeTableIdLike != null && modeTableIdLike.trim().length() > 0) {
			if (!modeTableIdLike.startsWith("%")) {
				modeTableIdLike = "%" + modeTableIdLike;
			}
			if (!modeTableIdLike.endsWith("%")) {
				modeTableIdLike = modeTableIdLike + "%";
			}
		}
		return modeTableIdLike;
	}

	public List<String> getModeTableIds() {
		return modeTableIds;
	}

	public Integer getIsSystem() {
		return isSystem;
	}

	public Integer getIsSystemGreaterThanOrEqual() {
		return isSystemGreaterThanOrEqual;
	}

	public Integer getIsSystemLessThanOrEqual() {
		return isSystemLessThanOrEqual;
	}

	public List<Integer> getIsSystems() {
		return isSystems;
	}

	public String getCustomData() {
		return customData;
	}

	public String getCustomDataLike() {
		if (customDataLike != null && customDataLike.trim().length() > 0) {
			if (!customDataLike.startsWith("%")) {
				customDataLike = "%" + customDataLike;
			}
			if (!customDataLike.endsWith("%")) {
				customDataLike = customDataLike + "%";
			}
		}
		return customDataLike;
	}

	public List<String> getCustomDatas() {
		return customDatas;
	}

	public Integer getIntSystemSelect() {
		return intSystemSelect;
	}

	public Integer getIntSystemSelectGreaterThanOrEqual() {
		return intSystemSelectGreaterThanOrEqual;
	}

	public Integer getIntSystemSelectLessThanOrEqual() {
		return intSystemSelectLessThanOrEqual;
	}

	public List<Integer> getIntSystemSelects() {
		return intSystemSelects;
	}

	public Integer getIntUsed() {
		return intUsed;
	}

	public Integer getIntUsedGreaterThanOrEqual() {
		return intUsedGreaterThanOrEqual;
	}

	public Integer getIntUsedLessThanOrEqual() {
		return intUsedLessThanOrEqual;
	}

	public List<Integer> getIntUseds() {
		return intUseds;
	}

	public Integer getIntDel() {
		return intDel;
	}

	public Integer getIntDelGreaterThanOrEqual() {
		return intDelGreaterThanOrEqual;
	}

	public Integer getIntDelLessThanOrEqual() {
		return intDelLessThanOrEqual;
	}

	public List<Integer> getIntDels() {
		return intDels;
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

	public Integer getIntOperation() {
		return intOperation;
	}

	public Integer getIntOperationGreaterThanOrEqual() {
		return intOperationGreaterThanOrEqual;
	}

	public Integer getIntOperationLessThanOrEqual() {
		return intOperationLessThanOrEqual;
	}

	public List<Integer> getIntOperations() {
		return intOperations;
	}

	public String getPicFile() {
		return picFile;
	}

	public String getPicFileLike() {
		if (picFileLike != null && picFileLike.trim().length() > 0) {
			if (!picFileLike.startsWith("%")) {
				picFileLike = "%" + picFileLike;
			}
			if (!picFileLike.endsWith("%")) {
				picFileLike = picFileLike + "%";
			}
		}
		return picFileLike;
	}

	public List<String> getPicFiles() {
		return picFiles;
	}

	public String getFileContent() {
		return fileContent;
	}

	public String getFileContentLike() {
		if (fileContentLike != null && fileContentLike.trim().length() > 0) {
			if (!fileContentLike.startsWith("%")) {
				fileContentLike = "%" + fileContentLike;
			}
			if (!fileContentLike.endsWith("%")) {
				fileContentLike = fileContentLike + "%";
			}
		}
		return fileContentLike;
	}

	public List<String> getFileContents() {
		return fileContents;
	}

	public Integer getIntMuiFrm() {
		return intMuiFrm;
	}

	public Integer getIntMuiFrmGreaterThanOrEqual() {
		return intMuiFrmGreaterThanOrEqual;
	}

	public Integer getIntMuiFrmLessThanOrEqual() {
		return intMuiFrmLessThanOrEqual;
	}

	public List<Integer> getIntMuiFrms() {
		return intMuiFrms;
	}

	public Integer getIntNoShow() {
		return intNoShow;
	}

	public Integer getIntNoShowGreaterThanOrEqual() {
		return intNoShowGreaterThanOrEqual;
	}

	public Integer getIntNoShowLessThanOrEqual() {
		return intNoShowLessThanOrEqual;
	}

	public List<Integer> getIntNoShows() {
		return intNoShows;
	}

	public String getTypeBaseTable() {
		return typeBaseTable;
	}

	public String getTypeBaseTableLike() {
		if (typeBaseTableLike != null && typeBaseTableLike.trim().length() > 0) {
			if (!typeBaseTableLike.startsWith("%")) {
				typeBaseTableLike = "%" + typeBaseTableLike;
			}
			if (!typeBaseTableLike.endsWith("%")) {
				typeBaseTableLike = typeBaseTableLike + "%";
			}
		}
		return typeBaseTableLike;
	}

	public List<String> getTypeBaseTables() {
		return typeBaseTables;
	}

	public Integer getTypeIndex() {
		return typeIndex;
	}

	public Integer getTypeIndexGreaterThanOrEqual() {
		return typeIndexGreaterThanOrEqual;
	}

	public Integer getTypeIndexLessThanOrEqual() {
		return typeIndexLessThanOrEqual;
	}

	public List<Integer> getTypeIndexs() {
		return typeIndexs;
	}

	public String getGid() {
		return gid;
	}

	public String getGidLike() {
		if (gidLike != null && gidLike.trim().length() > 0) {
			if (!gidLike.startsWith("%")) {
				gidLike = "%" + gidLike;
			}
			if (!gidLike.endsWith("%")) {
				gidLike = gidLike + "%";
			}
		}
		return gidLike;
	}

	public List<String> getGids() {
		return gids;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileNameLike() {
		if (fileNameLike != null && fileNameLike.trim().length() > 0) {
			if (!fileNameLike.startsWith("%")) {
				fileNameLike = "%" + fileNameLike;
			}
			if (!fileNameLike.endsWith("%")) {
				fileNameLike = fileNameLike + "%";
			}
		}
		return fileNameLike;
	}

	public List<String> getFileNames() {
		return fileNames;
	}

	public String getLinkFileContent() {
		return linkFileContent;
	}

	public String getLinkFileContentLike() {
		if (linkFileContentLike != null
				&& linkFileContentLike.trim().length() > 0) {
			if (!linkFileContentLike.startsWith("%")) {
				linkFileContentLike = "%" + linkFileContentLike;
			}
			if (!linkFileContentLike.endsWith("%")) {
				linkFileContentLike = linkFileContentLike + "%";
			}
		}
		return linkFileContentLike;
	}

	public List<String> getLinkFileContents() {
		return linkFileContents;
	}

	public String getLinkFileName() {
		return linkFileName;
	}

	public String getLinkFileNameLike() {
		if (linkFileNameLike != null && linkFileNameLike.trim().length() > 0) {
			if (!linkFileNameLike.startsWith("%")) {
				linkFileNameLike = "%" + linkFileNameLike;
			}
			if (!linkFileNameLike.endsWith("%")) {
				linkFileNameLike = linkFileNameLike + "%";
			}
		}
		return linkFileNameLike;
	}

	public List<String> getLinkFileNames() {
		return linkFileNames;
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

	public void setNum(String num) {
		this.num = num;
	}

	public void setNumLike(String numLike) {
		this.numLike = numLike;
	}

	public void setNums(List<String> nums) {
		this.nums = nums;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setContentLike(String contentLike) {
		this.contentLike = contentLike;
	}

	public void setContents(List<String> contents) {
		this.contents = contents;
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

	public void setSindexName(String sindexName) {
		this.sindexName = sindexName;
	}

	public void setSindexNameLike(String sindexNameLike) {
		this.sindexNameLike = sindexNameLike;
	}

	public void setSindexNames(List<String> sindexNames) {
		this.sindexNames = sindexNames;
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

	public void setViewType(Integer viewType) {
		this.viewType = viewType;
	}

	public void setViewTypeGreaterThanOrEqual(Integer viewTypeGreaterThanOrEqual) {
		this.viewTypeGreaterThanOrEqual = viewTypeGreaterThanOrEqual;
	}

	public void setViewTypeLessThanOrEqual(Integer viewTypeLessThanOrEqual) {
		this.viewTypeLessThanOrEqual = viewTypeLessThanOrEqual;
	}

	public void setViewTypes(List<Integer> viewTypes) {
		this.viewTypes = viewTypes;
	}

	public void setIsMode(String isMode) {
		this.isMode = isMode;
	}

	public void setIsModeLike(String isModeLike) {
		this.isModeLike = isModeLike;
	}

	public void setIsModes(List<String> isModes) {
		this.isModes = isModes;
	}

	public void setModeTableId(String modeTableId) {
		this.modeTableId = modeTableId;
	}

	public void setModeTableIdLike(String modeTableIdLike) {
		this.modeTableIdLike = modeTableIdLike;
	}

	public void setModeTableIds(List<String> modeTableIds) {
		this.modeTableIds = modeTableIds;
	}

	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}

	public void setIsSystemGreaterThanOrEqual(Integer isSystemGreaterThanOrEqual) {
		this.isSystemGreaterThanOrEqual = isSystemGreaterThanOrEqual;
	}

	public void setIsSystemLessThanOrEqual(Integer isSystemLessThanOrEqual) {
		this.isSystemLessThanOrEqual = isSystemLessThanOrEqual;
	}

	public void setIsSystems(List<Integer> isSystems) {
		this.isSystems = isSystems;
	}

	public void setCustomData(String customData) {
		this.customData = customData;
	}

	public void setCustomDataLike(String customDataLike) {
		this.customDataLike = customDataLike;
	}

	public void setCustomDatas(List<String> customDatas) {
		this.customDatas = customDatas;
	}

	public void setIntSystemSelect(Integer intSystemSelect) {
		this.intSystemSelect = intSystemSelect;
	}

	public void setIntSystemSelectGreaterThanOrEqual(
			Integer intSystemSelectGreaterThanOrEqual) {
		this.intSystemSelectGreaterThanOrEqual = intSystemSelectGreaterThanOrEqual;
	}

	public void setIntSystemSelectLessThanOrEqual(
			Integer intSystemSelectLessThanOrEqual) {
		this.intSystemSelectLessThanOrEqual = intSystemSelectLessThanOrEqual;
	}

	public void setIntSystemSelects(List<Integer> intSystemSelects) {
		this.intSystemSelects = intSystemSelects;
	}

	public void setIntUsed(Integer intUsed) {
		this.intUsed = intUsed;
	}

	public void setIntUsedGreaterThanOrEqual(Integer intUsedGreaterThanOrEqual) {
		this.intUsedGreaterThanOrEqual = intUsedGreaterThanOrEqual;
	}

	public void setIntUsedLessThanOrEqual(Integer intUsedLessThanOrEqual) {
		this.intUsedLessThanOrEqual = intUsedLessThanOrEqual;
	}

	public void setIntUseds(List<Integer> intUseds) {
		this.intUseds = intUseds;
	}

	public void setIntDel(Integer intDel) {
		this.intDel = intDel;
	}

	public void setIntDelGreaterThanOrEqual(Integer intDelGreaterThanOrEqual) {
		this.intDelGreaterThanOrEqual = intDelGreaterThanOrEqual;
	}

	public void setIntDelLessThanOrEqual(Integer intDelLessThanOrEqual) {
		this.intDelLessThanOrEqual = intDelLessThanOrEqual;
	}

	public void setIntDels(List<Integer> intDels) {
		this.intDels = intDels;
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

	public void setIntOperation(Integer intOperation) {
		this.intOperation = intOperation;
	}

	public void setIntOperationGreaterThanOrEqual(
			Integer intOperationGreaterThanOrEqual) {
		this.intOperationGreaterThanOrEqual = intOperationGreaterThanOrEqual;
	}

	public void setIntOperationLessThanOrEqual(
			Integer intOperationLessThanOrEqual) {
		this.intOperationLessThanOrEqual = intOperationLessThanOrEqual;
	}

	public void setIntOperations(List<Integer> intOperations) {
		this.intOperations = intOperations;
	}

	public void setPicFile(String picFile) {
		this.picFile = picFile;
	}

	public void setPicFileLike(String picFileLike) {
		this.picFileLike = picFileLike;
	}

	public void setPicFiles(List<String> picFiles) {
		this.picFiles = picFiles;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public void setFileContentLike(String fileContentLike) {
		this.fileContentLike = fileContentLike;
	}

	public void setFileContents(List<String> fileContents) {
		this.fileContents = fileContents;
	}

	public void setIntMuiFrm(Integer intMuiFrm) {
		this.intMuiFrm = intMuiFrm;
	}

	public void setIntMuiFrmGreaterThanOrEqual(
			Integer intMuiFrmGreaterThanOrEqual) {
		this.intMuiFrmGreaterThanOrEqual = intMuiFrmGreaterThanOrEqual;
	}

	public void setIntMuiFrmLessThanOrEqual(Integer intMuiFrmLessThanOrEqual) {
		this.intMuiFrmLessThanOrEqual = intMuiFrmLessThanOrEqual;
	}

	public void setIntMuiFrms(List<Integer> intMuiFrms) {
		this.intMuiFrms = intMuiFrms;
	}

	public void setIntNoShow(Integer intNoShow) {
		this.intNoShow = intNoShow;
	}

	public void setIntNoShowGreaterThanOrEqual(
			Integer intNoShowGreaterThanOrEqual) {
		this.intNoShowGreaterThanOrEqual = intNoShowGreaterThanOrEqual;
	}

	public void setIntNoShowLessThanOrEqual(Integer intNoShowLessThanOrEqual) {
		this.intNoShowLessThanOrEqual = intNoShowLessThanOrEqual;
	}

	public void setIntNoShows(List<Integer> intNoShows) {
		this.intNoShows = intNoShows;
	}

	public void setTypeBaseTable(String typeBaseTable) {
		this.typeBaseTable = typeBaseTable;
	}

	public void setTypeBaseTableLike(String typeBaseTableLike) {
		this.typeBaseTableLike = typeBaseTableLike;
	}

	public void setTypeBaseTables(List<String> typeBaseTables) {
		this.typeBaseTables = typeBaseTables;
	}

	public void setTypeIndex(Integer typeIndex) {
		this.typeIndex = typeIndex;
	}

	public void setTypeIndexGreaterThanOrEqual(
			Integer typeIndexGreaterThanOrEqual) {
		this.typeIndexGreaterThanOrEqual = typeIndexGreaterThanOrEqual;
	}

	public void setTypeIndexLessThanOrEqual(Integer typeIndexLessThanOrEqual) {
		this.typeIndexLessThanOrEqual = typeIndexLessThanOrEqual;
	}

	public void setTypeIndexs(List<Integer> typeIndexs) {
		this.typeIndexs = typeIndexs;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public void setGidLike(String gidLike) {
		this.gidLike = gidLike;
	}

	public void setGids(List<String> gids) {
		this.gids = gids;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileNameLike(String fileNameLike) {
		this.fileNameLike = fileNameLike;
	}

	public void setFileNames(List<String> fileNames) {
		this.fileNames = fileNames;
	}

	public void setLinkFileContent(String linkFileContent) {
		this.linkFileContent = linkFileContent;
	}

	public void setLinkFileContentLike(String linkFileContentLike) {
		this.linkFileContentLike = linkFileContentLike;
	}

	public void setLinkFileContents(List<String> linkFileContents) {
		this.linkFileContents = linkFileContents;
	}

	public void setLinkFileName(String linkFileName) {
		this.linkFileName = linkFileName;
	}

	public void setLinkFileNameLike(String linkFileNameLike) {
		this.linkFileNameLike = linkFileNameLike;
	}

	public void setLinkFileNames(List<String> linkFileNames) {
		this.linkFileNames = linkFileNames;
	}

	public CellTreeDotQuery id(String id) {
		if (id == null) {
			throw new RuntimeException("id is null");
		}
		this.id = id;
		return this;
	}

	public CellTreeDotQuery idLike(String idLike) {
		if (idLike == null) {
			throw new RuntimeException("id is null");
		}
		this.idLike = idLike;
		return this;
	}

	public CellTreeDotQuery ids(List<String> ids) {
		if (ids == null) {
			throw new RuntimeException("ids is empty ");
		}
		this.ids = ids;
		return this;
	}

	// public CellTreeDotQuery parentId(Integer parentId) {
	// if (parentId == null) {
	// throw new RuntimeException("parentId is null");
	// }
	// this.parentId = parentId;
	// return this;
	// }
	//
	// public CellTreeDotQuery parentIdGreaterThanOrEqual(Integer
	// parentIdGreaterThanOrEqual) {
	// if (parentIdGreaterThanOrEqual == null) {
	// throw new RuntimeException("parentId is null");
	// }
	// this.parentIdGreaterThanOrEqual = parentIdGreaterThanOrEqual;
	// return this;
	// }
	//
	// public CellTreeDotQuery parentIdLessThanOrEqual(Integer
	// parentIdLessThanOrEqual) {
	// if (parentIdLessThanOrEqual == null) {
	// throw new RuntimeException("parentId is null");
	// }
	// this.parentIdLessThanOrEqual = parentIdLessThanOrEqual;
	// return this;
	// }
	//
	// public CellTreeDotQuery parentIds(List<Integer> parentIds) {
	// if (parentIds == null) {
	// throw new RuntimeException("parentIds is empty ");
	// }
	// this.parentIds = parentIds;
	// return this;
	// }

	public CellTreeDotQuery indexName(String indexName) {
		if (indexName == null) {
			throw new RuntimeException("indexName is null");
		}
		this.indexName = indexName;
		return this;
	}

	public CellTreeDotQuery indexNameLike(String indexNameLike) {
		if (indexNameLike == null) {
			throw new RuntimeException("indexName is null");
		}
		this.indexNameLike = indexNameLike;
		return this;
	}

	public CellTreeDotQuery indexNames(List<String> indexNames) {
		if (indexNames == null) {
			throw new RuntimeException("indexNames is empty ");
		}
		this.indexNames = indexNames;
		return this;
	}

	public CellTreeDotQuery level(Integer level) {
		if (level == null) {
			throw new RuntimeException("level is null");
		}
		this.level = level;
		return this;
	}

	public CellTreeDotQuery levelGreaterThanOrEqual(
			Integer levelGreaterThanOrEqual) {
		if (levelGreaterThanOrEqual == null) {
			throw new RuntimeException("level is null");
		}
		this.levelGreaterThanOrEqual = levelGreaterThanOrEqual;
		return this;
	}

	public CellTreeDotQuery levelLessThanOrEqual(Integer levelLessThanOrEqual) {
		if (levelLessThanOrEqual == null) {
			throw new RuntimeException("level is null");
		}
		this.levelLessThanOrEqual = levelLessThanOrEqual;
		return this;
	}

	public CellTreeDotQuery levels(List<Integer> levels) {
		if (levels == null) {
			throw new RuntimeException("levels is empty ");
		}
		this.levels = levels;
		return this;
	}

	public CellTreeDotQuery num(String num) {
		if (num == null) {
			throw new RuntimeException("num is null");
		}
		this.num = num;
		return this;
	}

	public CellTreeDotQuery numLike(String numLike) {
		if (numLike == null) {
			throw new RuntimeException("num is null");
		}
		this.numLike = numLike;
		return this;
	}

	public CellTreeDotQuery nums(List<String> nums) {
		if (nums == null) {
			throw new RuntimeException("nums is empty ");
		}
		this.nums = nums;
		return this;
	}

	public CellTreeDotQuery content(String content) {
		if (content == null) {
			throw new RuntimeException("content is null");
		}
		this.content = content;
		return this;
	}

	public CellTreeDotQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public CellTreeDotQuery contents(List<String> contents) {
		if (contents == null) {
			throw new RuntimeException("contents is empty ");
		}
		this.contents = contents;
		return this;
	}

	public CellTreeDotQuery nodeIco(Integer nodeIco) {
		if (nodeIco == null) {
			throw new RuntimeException("nodeIco is null");
		}
		this.nodeIco = nodeIco;
		return this;
	}

	public CellTreeDotQuery nodeIcoGreaterThanOrEqual(
			Integer nodeIcoGreaterThanOrEqual) {
		if (nodeIcoGreaterThanOrEqual == null) {
			throw new RuntimeException("nodeIco is null");
		}
		this.nodeIcoGreaterThanOrEqual = nodeIcoGreaterThanOrEqual;
		return this;
	}

	public CellTreeDotQuery nodeIcoLessThanOrEqual(
			Integer nodeIcoLessThanOrEqual) {
		if (nodeIcoLessThanOrEqual == null) {
			throw new RuntimeException("nodeIco is null");
		}
		this.nodeIcoLessThanOrEqual = nodeIcoLessThanOrEqual;
		return this;
	}

	public CellTreeDotQuery nodeIcos(List<Integer> nodeIcos) {
		if (nodeIcos == null) {
			throw new RuntimeException("nodeIcos is empty ");
		}
		this.nodeIcos = nodeIcos;
		return this;
	}

	public CellTreeDotQuery sindexName(String sindexName) {
		if (sindexName == null) {
			throw new RuntimeException("sindexName is null");
		}
		this.sindexName = sindexName;
		return this;
	}

	public CellTreeDotQuery sindexNameLike(String sindexNameLike) {
		if (sindexNameLike == null) {
			throw new RuntimeException("sindexName is null");
		}
		this.sindexNameLike = sindexNameLike;
		return this;
	}

	public CellTreeDotQuery sindexNames(List<String> sindexNames) {
		if (sindexNames == null) {
			throw new RuntimeException("sindexNames is empty ");
		}
		this.sindexNames = sindexNames;
		return this;
	}

	public CellTreeDotQuery listNo(Integer listNo) {
		if (listNo == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNo = listNo;
		return this;
	}

	public CellTreeDotQuery listNoGreaterThanOrEqual(
			Integer listNoGreaterThanOrEqual) {
		if (listNoGreaterThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoGreaterThanOrEqual = listNoGreaterThanOrEqual;
		return this;
	}

	public CellTreeDotQuery listNoLessThanOrEqual(Integer listNoLessThanOrEqual) {
		if (listNoLessThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoLessThanOrEqual = listNoLessThanOrEqual;
		return this;
	}

	public CellTreeDotQuery listNos(List<Integer> listNos) {
		if (listNos == null) {
			throw new RuntimeException("listNos is empty ");
		}
		this.listNos = listNos;
		return this;
	}

	public CellTreeDotQuery viewType(Integer viewType) {
		if (viewType == null) {
			throw new RuntimeException("viewType is null");
		}
		this.viewType = viewType;
		return this;
	}

	public CellTreeDotQuery viewTypeGreaterThanOrEqual(
			Integer viewTypeGreaterThanOrEqual) {
		if (viewTypeGreaterThanOrEqual == null) {
			throw new RuntimeException("viewType is null");
		}
		this.viewTypeGreaterThanOrEqual = viewTypeGreaterThanOrEqual;
		return this;
	}

	public CellTreeDotQuery viewTypeLessThanOrEqual(
			Integer viewTypeLessThanOrEqual) {
		if (viewTypeLessThanOrEqual == null) {
			throw new RuntimeException("viewType is null");
		}
		this.viewTypeLessThanOrEqual = viewTypeLessThanOrEqual;
		return this;
	}

	public CellTreeDotQuery viewTypes(List<Integer> viewTypes) {
		if (viewTypes == null) {
			throw new RuntimeException("viewTypes is empty ");
		}
		this.viewTypes = viewTypes;
		return this;
	}

	public CellTreeDotQuery isMode(String isMode) {
		if (isMode == null) {
			throw new RuntimeException("isMode is null");
		}
		this.isMode = isMode;
		return this;
	}

	public CellTreeDotQuery isModeLike(String isModeLike) {
		if (isModeLike == null) {
			throw new RuntimeException("isMode is null");
		}
		this.isModeLike = isModeLike;
		return this;
	}

	public CellTreeDotQuery isModes(List<String> isModes) {
		if (isModes == null) {
			throw new RuntimeException("isModes is empty ");
		}
		this.isModes = isModes;
		return this;
	}

	public CellTreeDotQuery modeTableId(String modeTableId) {
		if (modeTableId == null) {
			throw new RuntimeException("modeTableId is null");
		}
		this.modeTableId = modeTableId;
		return this;
	}

	public CellTreeDotQuery modeTableIdLike(String modeTableIdLike) {
		if (modeTableIdLike == null) {
			throw new RuntimeException("modeTableId is null");
		}
		this.modeTableIdLike = modeTableIdLike;
		return this;
	}

	public CellTreeDotQuery modeTableIds(List<String> modeTableIds) {
		if (modeTableIds == null) {
			throw new RuntimeException("modeTableIds is empty ");
		}
		this.modeTableIds = modeTableIds;
		return this;
	}

	public CellTreeDotQuery isSystem(Integer isSystem) {
		if (isSystem == null) {
			throw new RuntimeException("isSystem is null");
		}
		this.isSystem = isSystem;
		return this;
	}

	public CellTreeDotQuery isSystemGreaterThanOrEqual(
			Integer isSystemGreaterThanOrEqual) {
		if (isSystemGreaterThanOrEqual == null) {
			throw new RuntimeException("isSystem is null");
		}
		this.isSystemGreaterThanOrEqual = isSystemGreaterThanOrEqual;
		return this;
	}

	public CellTreeDotQuery isSystemLessThanOrEqual(
			Integer isSystemLessThanOrEqual) {
		if (isSystemLessThanOrEqual == null) {
			throw new RuntimeException("isSystem is null");
		}
		this.isSystemLessThanOrEqual = isSystemLessThanOrEqual;
		return this;
	}

	public CellTreeDotQuery isSystems(List<Integer> isSystems) {
		if (isSystems == null) {
			throw new RuntimeException("isSystems is empty ");
		}
		this.isSystems = isSystems;
		return this;
	}

	public CellTreeDotQuery customData(String customData) {
		if (customData == null) {
			throw new RuntimeException("customData is null");
		}
		this.customData = customData;
		return this;
	}

	public CellTreeDotQuery customDataLike(String customDataLike) {
		if (customDataLike == null) {
			throw new RuntimeException("customData is null");
		}
		this.customDataLike = customDataLike;
		return this;
	}

	public CellTreeDotQuery customDatas(List<String> customDatas) {
		if (customDatas == null) {
			throw new RuntimeException("customDatas is empty ");
		}
		this.customDatas = customDatas;
		return this;
	}

	public CellTreeDotQuery intSystemSelect(Integer intSystemSelect) {
		if (intSystemSelect == null) {
			throw new RuntimeException("intSystemSelect is null");
		}
		this.intSystemSelect = intSystemSelect;
		return this;
	}

	public CellTreeDotQuery intSystemSelectGreaterThanOrEqual(
			Integer intSystemSelectGreaterThanOrEqual) {
		if (intSystemSelectGreaterThanOrEqual == null) {
			throw new RuntimeException("intSystemSelect is null");
		}
		this.intSystemSelectGreaterThanOrEqual = intSystemSelectGreaterThanOrEqual;
		return this;
	}

	public CellTreeDotQuery intSystemSelectLessThanOrEqual(
			Integer intSystemSelectLessThanOrEqual) {
		if (intSystemSelectLessThanOrEqual == null) {
			throw new RuntimeException("intSystemSelect is null");
		}
		this.intSystemSelectLessThanOrEqual = intSystemSelectLessThanOrEqual;
		return this;
	}

	public CellTreeDotQuery intSystemSelects(List<Integer> intSystemSelects) {
		if (intSystemSelects == null) {
			throw new RuntimeException("intSystemSelects is empty ");
		}
		this.intSystemSelects = intSystemSelects;
		return this;
	}

	public CellTreeDotQuery intUsed(Integer intUsed) {
		if (intUsed == null) {
			throw new RuntimeException("intUsed is null");
		}
		this.intUsed = intUsed;
		return this;
	}

	public CellTreeDotQuery intUsedGreaterThanOrEqual(
			Integer intUsedGreaterThanOrEqual) {
		if (intUsedGreaterThanOrEqual == null) {
			throw new RuntimeException("intUsed is null");
		}
		this.intUsedGreaterThanOrEqual = intUsedGreaterThanOrEqual;
		return this;
	}

	public CellTreeDotQuery intUsedLessThanOrEqual(
			Integer intUsedLessThanOrEqual) {
		if (intUsedLessThanOrEqual == null) {
			throw new RuntimeException("intUsed is null");
		}
		this.intUsedLessThanOrEqual = intUsedLessThanOrEqual;
		return this;
	}

	public CellTreeDotQuery intUseds(List<Integer> intUseds) {
		if (intUseds == null) {
			throw new RuntimeException("intUseds is empty ");
		}
		this.intUseds = intUseds;
		return this;
	}

	public CellTreeDotQuery intDel(Integer intDel) {
		if (intDel == null) {
			throw new RuntimeException("intDel is null");
		}
		this.intDel = intDel;
		return this;
	}

	public CellTreeDotQuery intDelGreaterThanOrEqual(
			Integer intDelGreaterThanOrEqual) {
		if (intDelGreaterThanOrEqual == null) {
			throw new RuntimeException("intDel is null");
		}
		this.intDelGreaterThanOrEqual = intDelGreaterThanOrEqual;
		return this;
	}

	public CellTreeDotQuery intDelLessThanOrEqual(Integer intDelLessThanOrEqual) {
		if (intDelLessThanOrEqual == null) {
			throw new RuntimeException("intDel is null");
		}
		this.intDelLessThanOrEqual = intDelLessThanOrEqual;
		return this;
	}

	public CellTreeDotQuery intDels(List<Integer> intDels) {
		if (intDels == null) {
			throw new RuntimeException("intDels is empty ");
		}
		this.intDels = intDels;
		return this;
	}

	public CellTreeDotQuery typeTableName(String typeTableName) {
		if (typeTableName == null) {
			throw new RuntimeException("typeTableName is null");
		}
		this.typeTableName = typeTableName;
		return this;
	}

	public CellTreeDotQuery typeTableNameLike(String typeTableNameLike) {
		if (typeTableNameLike == null) {
			throw new RuntimeException("typeTableName is null");
		}
		this.typeTableNameLike = typeTableNameLike;
		return this;
	}

	public CellTreeDotQuery typeTableNames(List<String> typeTableNames) {
		if (typeTableNames == null) {
			throw new RuntimeException("typeTableNames is empty ");
		}
		this.typeTableNames = typeTableNames;
		return this;
	}

	public CellTreeDotQuery intOperation(Integer intOperation) {
		if (intOperation == null) {
			throw new RuntimeException("intOperation is null");
		}
		this.intOperation = intOperation;
		return this;
	}

	public CellTreeDotQuery intOperationGreaterThanOrEqual(
			Integer intOperationGreaterThanOrEqual) {
		if (intOperationGreaterThanOrEqual == null) {
			throw new RuntimeException("intOperation is null");
		}
		this.intOperationGreaterThanOrEqual = intOperationGreaterThanOrEqual;
		return this;
	}

	public CellTreeDotQuery intOperationLessThanOrEqual(
			Integer intOperationLessThanOrEqual) {
		if (intOperationLessThanOrEqual == null) {
			throw new RuntimeException("intOperation is null");
		}
		this.intOperationLessThanOrEqual = intOperationLessThanOrEqual;
		return this;
	}

	public CellTreeDotQuery intOperations(List<Integer> intOperations) {
		if (intOperations == null) {
			throw new RuntimeException("intOperations is empty ");
		}
		this.intOperations = intOperations;
		return this;
	}

	public CellTreeDotQuery picFile(String picFile) {
		if (picFile == null) {
			throw new RuntimeException("picFile is null");
		}
		this.picFile = picFile;
		return this;
	}

	public CellTreeDotQuery picFileLike(String picFileLike) {
		if (picFileLike == null) {
			throw new RuntimeException("picFile is null");
		}
		this.picFileLike = picFileLike;
		return this;
	}

	public CellTreeDotQuery picFiles(List<String> picFiles) {
		if (picFiles == null) {
			throw new RuntimeException("picFiles is empty ");
		}
		this.picFiles = picFiles;
		return this;
	}

	public CellTreeDotQuery fileContent(String fileContent) {
		if (fileContent == null) {
			throw new RuntimeException("fileContent is null");
		}
		this.fileContent = fileContent;
		return this;
	}

	public CellTreeDotQuery fileContentLike(String fileContentLike) {
		if (fileContentLike == null) {
			throw new RuntimeException("fileContent is null");
		}
		this.fileContentLike = fileContentLike;
		return this;
	}

	public CellTreeDotQuery fileContents(List<String> fileContents) {
		if (fileContents == null) {
			throw new RuntimeException("fileContents is empty ");
		}
		this.fileContents = fileContents;
		return this;
	}

	public CellTreeDotQuery intMuiFrm(Integer intMuiFrm) {
		if (intMuiFrm == null) {
			throw new RuntimeException("intMuiFrm is null");
		}
		this.intMuiFrm = intMuiFrm;
		return this;
	}

	public CellTreeDotQuery intMuiFrmGreaterThanOrEqual(
			Integer intMuiFrmGreaterThanOrEqual) {
		if (intMuiFrmGreaterThanOrEqual == null) {
			throw new RuntimeException("intMuiFrm is null");
		}
		this.intMuiFrmGreaterThanOrEqual = intMuiFrmGreaterThanOrEqual;
		return this;
	}

	public CellTreeDotQuery intMuiFrmLessThanOrEqual(
			Integer intMuiFrmLessThanOrEqual) {
		if (intMuiFrmLessThanOrEqual == null) {
			throw new RuntimeException("intMuiFrm is null");
		}
		this.intMuiFrmLessThanOrEqual = intMuiFrmLessThanOrEqual;
		return this;
	}

	public CellTreeDotQuery intMuiFrms(List<Integer> intMuiFrms) {
		if (intMuiFrms == null) {
			throw new RuntimeException("intMuiFrms is empty ");
		}
		this.intMuiFrms = intMuiFrms;
		return this;
	}

	public CellTreeDotQuery intNoShow(Integer intNoShow) {
		if (intNoShow == null) {
			throw new RuntimeException("intNoShow is null");
		}
		this.intNoShow = intNoShow;
		return this;
	}

	public CellTreeDotQuery intNoShowGreaterThanOrEqual(
			Integer intNoShowGreaterThanOrEqual) {
		if (intNoShowGreaterThanOrEqual == null) {
			throw new RuntimeException("intNoShow is null");
		}
		this.intNoShowGreaterThanOrEqual = intNoShowGreaterThanOrEqual;
		return this;
	}

	public CellTreeDotQuery intNoShowLessThanOrEqual(
			Integer intNoShowLessThanOrEqual) {
		if (intNoShowLessThanOrEqual == null) {
			throw new RuntimeException("intNoShow is null");
		}
		this.intNoShowLessThanOrEqual = intNoShowLessThanOrEqual;
		return this;
	}

	public CellTreeDotQuery intNoShows(List<Integer> intNoShows) {
		if (intNoShows == null) {
			throw new RuntimeException("intNoShows is empty ");
		}
		this.intNoShows = intNoShows;
		return this;
	}

	public CellTreeDotQuery typeBaseTable(String typeBaseTable) {
		if (typeBaseTable == null) {
			throw new RuntimeException("typeBaseTable is null");
		}
		this.typeBaseTable = typeBaseTable;
		return this;
	}

	public CellTreeDotQuery typeBaseTableLike(String typeBaseTableLike) {
		if (typeBaseTableLike == null) {
			throw new RuntimeException("typeBaseTable is null");
		}
		this.typeBaseTableLike = typeBaseTableLike;
		return this;
	}

	public CellTreeDotQuery typeBaseTables(List<String> typeBaseTables) {
		if (typeBaseTables == null) {
			throw new RuntimeException("typeBaseTables is empty ");
		}
		this.typeBaseTables = typeBaseTables;
		return this;
	}

	public CellTreeDotQuery typeIndex(Integer typeIndex) {
		if (typeIndex == null) {
			throw new RuntimeException("typeIndex is null");
		}
		this.typeIndex = typeIndex;
		return this;
	}

	public CellTreeDotQuery typeIndexGreaterThanOrEqual(
			Integer typeIndexGreaterThanOrEqual) {
		if (typeIndexGreaterThanOrEqual == null) {
			throw new RuntimeException("typeIndex is null");
		}
		this.typeIndexGreaterThanOrEqual = typeIndexGreaterThanOrEqual;
		return this;
	}

	public CellTreeDotQuery typeIndexLessThanOrEqual(
			Integer typeIndexLessThanOrEqual) {
		if (typeIndexLessThanOrEqual == null) {
			throw new RuntimeException("typeIndex is null");
		}
		this.typeIndexLessThanOrEqual = typeIndexLessThanOrEqual;
		return this;
	}

	public CellTreeDotQuery typeIndexs(List<Integer> typeIndexs) {
		if (typeIndexs == null) {
			throw new RuntimeException("typeIndexs is empty ");
		}
		this.typeIndexs = typeIndexs;
		return this;
	}

	public CellTreeDotQuery gid(String gid) {
		if (gid == null) {
			throw new RuntimeException("gid is null");
		}
		this.gid = gid;
		return this;
	}

	public CellTreeDotQuery gidLike(String gidLike) {
		if (gidLike == null) {
			throw new RuntimeException("gid is null");
		}
		this.gidLike = gidLike;
		return this;
	}

	public CellTreeDotQuery gids(List<String> gids) {
		if (gids == null) {
			throw new RuntimeException("gids is empty ");
		}
		this.gids = gids;
		return this;
	}

	public CellTreeDotQuery fileName(String fileName) {
		if (fileName == null) {
			throw new RuntimeException("fileName is null");
		}
		this.fileName = fileName;
		return this;
	}

	public CellTreeDotQuery fileNameLike(String fileNameLike) {
		if (fileNameLike == null) {
			throw new RuntimeException("fileName is null");
		}
		this.fileNameLike = fileNameLike;
		return this;
	}

	public CellTreeDotQuery fileNames(List<String> fileNames) {
		if (fileNames == null) {
			throw new RuntimeException("fileNames is empty ");
		}
		this.fileNames = fileNames;
		return this;
	}

	public CellTreeDotQuery linkFileContent(String linkFileContent) {
		if (linkFileContent == null) {
			throw new RuntimeException("linkFileContent is null");
		}
		this.linkFileContent = linkFileContent;
		return this;
	}

	public CellTreeDotQuery linkFileContentLike(String linkFileContentLike) {
		if (linkFileContentLike == null) {
			throw new RuntimeException("linkFileContent is null");
		}
		this.linkFileContentLike = linkFileContentLike;
		return this;
	}

	public CellTreeDotQuery linkFileContents(List<String> linkFileContents) {
		if (linkFileContents == null) {
			throw new RuntimeException("linkFileContents is empty ");
		}
		this.linkFileContents = linkFileContents;
		return this;
	}

	public CellTreeDotQuery linkFileName(String linkFileName) {
		if (linkFileName == null) {
			throw new RuntimeException("linkFileName is null");
		}
		this.linkFileName = linkFileName;
		return this;
	}

	public CellTreeDotQuery linkFileNameLike(String linkFileNameLike) {
		if (linkFileNameLike == null) {
			throw new RuntimeException("linkFileName is null");
		}
		this.linkFileNameLike = linkFileNameLike;
		return this;
	}

	public CellTreeDotQuery linkFileNames(List<String> linkFileNames) {
		if (linkFileNames == null) {
			throw new RuntimeException("linkFileNames is empty ");
		}
		this.linkFileNames = linkFileNames;
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

			if ("parentId".equals(sortColumn)) {
				orderBy = "E.PARENT_ID" + a_x;
			}

			if ("indexName".equals(sortColumn)) {
				orderBy = "E.INDEX_NAME" + a_x;
			}

			if ("level".equals(sortColumn)) {
				orderBy = "E.NLEVEL" + a_x;
			}

			if ("num".equals(sortColumn)) {
				orderBy = "E.NUM" + a_x;
			}

			if ("content".equals(sortColumn)) {
				orderBy = "E.CONTENT" + a_x;
			}

			if ("nodeIco".equals(sortColumn)) {
				orderBy = "E.NODEICO" + a_x;
			}

			if ("sindexName".equals(sortColumn)) {
				orderBy = "E.SINDEX_NAME" + a_x;
			}

			if ("listNo".equals(sortColumn)) {
				orderBy = "E.LISTNO" + a_x;
			}

			if ("viewType".equals(sortColumn)) {
				orderBy = "E.VIEWTYPE" + a_x;
			}

			if ("isMode".equals(sortColumn)) {
				orderBy = "E.ISMODE" + a_x;
			}

			if ("modeTableId".equals(sortColumn)) {
				orderBy = "E.MODETABLE_ID" + a_x;
			}

			if ("isSystem".equals(sortColumn)) {
				orderBy = "E.ISSYSTEM" + a_x;
			}

			if ("customData".equals(sortColumn)) {
				orderBy = "E.CUSTOMDATA" + a_x;
			}

			if ("intSystemSelect".equals(sortColumn)) {
				orderBy = "E.INTSYSTEMSELECT" + a_x;
			}

			if ("intUsed".equals(sortColumn)) {
				orderBy = "E.INTUSED" + a_x;
			}

			if ("intDel".equals(sortColumn)) {
				orderBy = "E.INTDEL" + a_x;
			}

			if ("typeTableName".equals(sortColumn)) {
				orderBy = "E.TYPE_TABLENAME" + a_x;
			}

			if ("intOperation".equals(sortColumn)) {
				orderBy = "E.INTOPERATION" + a_x;
			}

			if ("picFile".equals(sortColumn)) {
				orderBy = "E.PICFILE" + a_x;
			}

			if ("fileContent".equals(sortColumn)) {
				orderBy = "E.FILE_CONTENT" + a_x;
			}

			if ("intMuiFrm".equals(sortColumn)) {
				orderBy = "E.INTMUIFRM" + a_x;
			}

			if ("intNoShow".equals(sortColumn)) {
				orderBy = "E.INTNOSHOW" + a_x;
			}

			if ("typeBaseTable".equals(sortColumn)) {
				orderBy = "E.TYPE_BASETABLE" + a_x;
			}

			if ("typeIndex".equals(sortColumn)) {
				orderBy = "E.TYPE_INDEX" + a_x;
			}

			if ("gid".equals(sortColumn)) {
				orderBy = "E.GID" + a_x;
			}

			if ("fileName".equals(sortColumn)) {
				orderBy = "E.FILE_NAME" + a_x;
			}

			if ("linkFileContent".equals(sortColumn)) {
				orderBy = "E.LINK_FILE_CONTENT" + a_x;
			}

			if ("linkFileName".equals(sortColumn)) {
				orderBy = "E.LINK_FILE_NAME" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("indexId", "INDEX_ID");
		addColumn("id", "ID");
		addColumn("parentId", "PARENT_ID");
		addColumn("indexName", "INDEX_NAME");
		addColumn("level", "NLEVEL");
		addColumn("num", "NUM");
		addColumn("content", "CONTENT");
		addColumn("nodeIco", "NODEICO");
		addColumn("sindexName", "SINDEX_NAME");
		addColumn("listNo", "LISTNO");
		addColumn("viewType", "VIEWTYPE");
		addColumn("isMode", "ISMODE");
		addColumn("modeTableId", "MODETABLE_ID");
		addColumn("isSystem", "ISSYSTEM");
		addColumn("customData", "CUSTOMDATA");
		addColumn("intSystemSelect", "INTSYSTEMSELECT");
		addColumn("intUsed", "INTUSED");
		addColumn("intDel", "INTDEL");
		addColumn("typeTableName", "TYPE_TABLENAME");
		addColumn("intOperation", "INTOPERATION");
		addColumn("picFile", "PICFILE");
		addColumn("fileContent", "FILE_CONTENT");
		addColumn("intMuiFrm", "INTMUIFRM");
		addColumn("intNoShow", "INTNOSHOW");
		addColumn("typeBaseTable", "TYPE_BASETABLE");
		addColumn("typeIndex", "TYPE_INDEX");
		addColumn("gid", "GID");
		addColumn("fileName", "FILE_NAME");
		addColumn("linkFileContent", "LINK_FILE_CONTENT");
		addColumn("linkFileName", "LINK_FILE_NAME");
	}

}