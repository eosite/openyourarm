package com.glaf.isdp.query;

import java.util.*;

import com.glaf.core.query.DataQuery;

public class CellFillFormQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected String topId;
	protected String topIdLike;
	protected List<String> topIds;
	protected Integer indexId;
	protected Integer indexIdGreaterThanOrEqual;
	protected Integer indexIdLessThanOrEqual;
	protected List<Integer> indexIds;
	protected String taskId;
	protected String taskIdLike;
	protected List<String> taskIds;
	protected Integer pfileFlag;
	protected Integer pfileFlagGreaterThanOrEqual;
	protected Integer pfileFlagLessThanOrEqual;
	protected List<Integer> pfileFlags;
	protected String fileDotFileId;
	protected String fileDotFileIdLike;
	protected List<String> fileDotFileIds;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String chkNum;
	protected String chkNumLike;
	protected List<String> chkNums;
	protected Integer listNo;
	protected Integer listNoGreaterThanOrEqual;
	protected Integer listNoLessThanOrEqual;
	protected List<Integer> listNos;
	protected Integer chkTotal;
	protected Integer chkTotalGreaterThanOrEqual;
	protected Integer chkTotalLessThanOrEqual;
	protected List<Integer> chkTotals;
	protected Integer chkResult;
	protected Integer chkResultGreaterThanOrEqual;
	protected Integer chkResultLessThanOrEqual;
	protected List<Integer> chkResults;
	protected String pfileId;
	protected String pfileIdLike;
	protected List<String> pfileIds;
	protected Integer tempSave;
	protected Integer tempSaveGreaterThanOrEqual;
	protected Integer tempSaveLessThanOrEqual;
	protected List<Integer> tempSaves;
	protected String userId;
	protected String userIdLike;
	protected List<String> userIds;
	protected Integer refillFlag;
	protected Integer refillFlagGreaterThanOrEqual;
	protected Integer refillFlagLessThanOrEqual;
	protected List<Integer> refillFlags;
	protected Integer groupId;
	protected Integer groupIdGreaterThanOrEqual;
	protected Integer groupIdLessThanOrEqual;
	protected List<Integer> groupIds;
	protected String oldId;
	protected String oldIdLike;
	protected List<String> oldIds;
	protected Integer roleId;
	protected Integer roleIdGreaterThanOrEqual;
	protected Integer roleIdLessThanOrEqual;
	protected List<Integer> roleIds;
	protected Integer isFinish;
	protected Integer isFinishGreaterThanOrEqual;
	protected Integer isFinishLessThanOrEqual;
	protected List<Integer> isFinishs;
	protected String typeTableName;
	protected String typeTableNameLike;
	protected List<String> typeTableNames;
	protected String typeId;
	protected String typeIdLike;
	protected List<String> typeIds;
	protected Integer typeIndexId;
	protected Integer typeIndexIdGreaterThanOrEqual;
	protected Integer typeIndexIdLessThanOrEqual;
	protected List<Integer> typeIndexIds;
	protected String mainId;
	protected String mainIdLike;
	protected List<String> mainIds;
	protected Integer intLastPage;
	protected Integer intLastPageGreaterThanOrEqual;
	protected Integer intLastPageLessThanOrEqual;
	protected List<Integer> intLastPages;
	protected Integer intSheets;
	protected Integer intSheetsGreaterThanOrEqual;
	protected Integer intSheetsLessThanOrEqual;
	protected List<Integer> intSheetss;
	
	protected String queryStartDate;
	protected String queryStartDateGreaterThanOrEqual;
	protected String queryStartDateLessThanOrEqual;
	protected String queryEndDate;
	protected String queryEndDateGreaterThanOrEqual;
	protected String queryEndDateLessThanOrEqual;
	protected String treepinfoIdLike;
	protected String nameNotLike;
	
	protected String data_id;
	
	

	public CellFillFormQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getTopId() {
		return topId;
	}

	public String getTopIdLike() {
		if (topIdLike != null && topIdLike.trim().length() > 0) {
			if (!topIdLike.startsWith("%")) {
				topIdLike = "%" + topIdLike;
			}
			if (!topIdLike.endsWith("%")) {
				topIdLike = topIdLike + "%";
			}
		}
		return topIdLike;
	}

	public List<String> getTopIds() {
		return topIds;
	}

	public Integer getIndexId() {
		return indexId;
	}

	public Integer getIndexIdGreaterThanOrEqual() {
		return indexIdGreaterThanOrEqual;
	}

	public Integer getIndexIdLessThanOrEqual() {
		return indexIdLessThanOrEqual;
	}

	public List<Integer> getIndexIds() {
		return indexIds;
	}

	public String getTaskId() {
		return taskId;
	}

	public String getTaskIdLike() {
		if (taskIdLike != null && taskIdLike.trim().length() > 0) {
			if (!taskIdLike.startsWith("%")) {
				taskIdLike = "%" + taskIdLike;
			}
			if (!taskIdLike.endsWith("%")) {
				taskIdLike = taskIdLike + "%";
			}
		}
		return taskIdLike;
	}

	public List<String> getTaskIds() {
		return taskIds;
	}

	public Integer getPfileFlag() {
		return pfileFlag;
	}

	public Integer getPfileFlagGreaterThanOrEqual() {
		return pfileFlagGreaterThanOrEqual;
	}

	public Integer getPfileFlagLessThanOrEqual() {
		return pfileFlagLessThanOrEqual;
	}

	public List<Integer> getPfileFlags() {
		return pfileFlags;
	}

	public String getFileDotFileId() {
		return fileDotFileId;
	}

	public String getFileDotFileIdLike() {
		if (fileDotFileIdLike != null && fileDotFileIdLike.trim().length() > 0) {
			if (!fileDotFileIdLike.startsWith("%")) {
				fileDotFileIdLike = "%" + fileDotFileIdLike;
			}
			if (!fileDotFileIdLike.endsWith("%")) {
				fileDotFileIdLike = fileDotFileIdLike + "%";
			}
		}
		return fileDotFileIdLike;
	}

	public List<String> getFileDotFileIds() {
		return fileDotFileIds;
	}

	public String getName() {
		return name;
	}

	public String getNameLike() {
		if (nameLike != null && nameLike.trim().length() > 0) {
			if (!nameLike.startsWith("%")) {
				nameLike = "%" + nameLike;
			}
			if (!nameLike.endsWith("%")) {
				nameLike = nameLike + "%";
			}
		}
		return nameLike;
	}

	public List<String> getNames() {
		return names;
	}

	public String getChkNum() {
		return chkNum;
	}

	public String getChkNumLike() {
		if (chkNumLike != null && chkNumLike.trim().length() > 0) {
			if (!chkNumLike.startsWith("%")) {
				chkNumLike = "%" + chkNumLike;
			}
			if (!chkNumLike.endsWith("%")) {
				chkNumLike = chkNumLike + "%";
			}
		}
		return chkNumLike;
	}

	public List<String> getChkNums() {
		return chkNums;
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

	public Integer getChkTotal() {
		return chkTotal;
	}

	public Integer getChkTotalGreaterThanOrEqual() {
		return chkTotalGreaterThanOrEqual;
	}

	public Integer getChkTotalLessThanOrEqual() {
		return chkTotalLessThanOrEqual;
	}

	public List<Integer> getChkTotals() {
		return chkTotals;
	}

	public Integer getChkResult() {
		return chkResult;
	}

	public Integer getChkResultGreaterThanOrEqual() {
		return chkResultGreaterThanOrEqual;
	}

	public Integer getChkResultLessThanOrEqual() {
		return chkResultLessThanOrEqual;
	}

	public List<Integer> getChkResults() {
		return chkResults;
	}

	public String getPfileId() {
		return pfileId;
	}

	public String getPfileIdLike() {
		if (pfileIdLike != null && pfileIdLike.trim().length() > 0) {
			if (!pfileIdLike.startsWith("%")) {
				pfileIdLike = "%" + pfileIdLike;
			}
			if (!pfileIdLike.endsWith("%")) {
				pfileIdLike = pfileIdLike + "%";
			}
		}
		return pfileIdLike;
	}

	public List<String> getPfileIds() {
		return pfileIds;
	}

	public Integer getTempSave() {
		return tempSave;
	}

	public Integer getTempSaveGreaterThanOrEqual() {
		return tempSaveGreaterThanOrEqual;
	}

	public Integer getTempSaveLessThanOrEqual() {
		return tempSaveLessThanOrEqual;
	}

	public List<Integer> getTempSaves() {
		return tempSaves;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserIdLike() {
		if (userIdLike != null && userIdLike.trim().length() > 0) {
			if (!userIdLike.startsWith("%")) {
				userIdLike = "%" + userIdLike;
			}
			if (!userIdLike.endsWith("%")) {
				userIdLike = userIdLike + "%";
			}
		}
		return userIdLike;
	}

	public List<String> getUserIds() {
		return userIds;
	}

	public Integer getRefillFlag() {
		return refillFlag;
	}

	public Integer getRefillFlagGreaterThanOrEqual() {
		return refillFlagGreaterThanOrEqual;
	}

	public Integer getRefillFlagLessThanOrEqual() {
		return refillFlagLessThanOrEqual;
	}

	public List<Integer> getRefillFlags() {
		return refillFlags;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public Integer getGroupIdGreaterThanOrEqual() {
		return groupIdGreaterThanOrEqual;
	}

	public Integer getGroupIdLessThanOrEqual() {
		return groupIdLessThanOrEqual;
	}

	public List<Integer> getGroupIds() {
		return groupIds;
	}

	public String getOldId() {
		return oldId;
	}

	public String getOldIdLike() {
		if (oldIdLike != null && oldIdLike.trim().length() > 0) {
			if (!oldIdLike.startsWith("%")) {
				oldIdLike = "%" + oldIdLike;
			}
			if (!oldIdLike.endsWith("%")) {
				oldIdLike = oldIdLike + "%";
			}
		}
		return oldIdLike;
	}

	public List<String> getOldIds() {
		return oldIds;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public Integer getRoleIdGreaterThanOrEqual() {
		return roleIdGreaterThanOrEqual;
	}

	public Integer getRoleIdLessThanOrEqual() {
		return roleIdLessThanOrEqual;
	}

	public List<Integer> getRoleIds() {
		return roleIds;
	}

	public Integer getIsFinish() {
		return isFinish;
	}

	public Integer getIsFinishGreaterThanOrEqual() {
		return isFinishGreaterThanOrEqual;
	}

	public Integer getIsFinishLessThanOrEqual() {
		return isFinishLessThanOrEqual;
	}

	public List<Integer> getIsFinishs() {
		return isFinishs;
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

	public Integer getTypeIndexId() {
		return typeIndexId;
	}

	public Integer getTypeIndexIdGreaterThanOrEqual() {
		return typeIndexIdGreaterThanOrEqual;
	}

	public Integer getTypeIndexIdLessThanOrEqual() {
		return typeIndexIdLessThanOrEqual;
	}

	public List<Integer> getTypeIndexIds() {
		return typeIndexIds;
	}

	public String getMainId() {
		return mainId;
	}

	public String getMainIdLike() {
		if (mainIdLike != null && mainIdLike.trim().length() > 0) {
			if (!mainIdLike.startsWith("%")) {
				mainIdLike = "%" + mainIdLike;
			}
			if (!mainIdLike.endsWith("%")) {
				mainIdLike = mainIdLike + "%";
			}
		}
		return mainIdLike;
	}

	public List<String> getMainIds() {
		return mainIds;
	}

	public Integer getIntLastPage() {
		return intLastPage;
	}

	public Integer getIntLastPageGreaterThanOrEqual() {
		return intLastPageGreaterThanOrEqual;
	}

	public Integer getIntLastPageLessThanOrEqual() {
		return intLastPageLessThanOrEqual;
	}

	public List<Integer> getIntLastPages() {
		return intLastPages;
	}

	public Integer getIntSheets() {
		return intSheets;
	}

	public Integer getIntSheetsGreaterThanOrEqual() {
		return intSheetsGreaterThanOrEqual;
	}

	public Integer getIntSheetsLessThanOrEqual() {
		return intSheetsLessThanOrEqual;
	}

	public List<Integer> getIntSheetss() {
		return intSheetss;
	}

	public void setTopId(String topId) {
		this.topId = topId;
	}

	public void setTopIdLike(String topIdLike) {
		this.topIdLike = topIdLike;
	}

	public void setTopIds(List<String> topIds) {
		this.topIds = topIds;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public void setIndexIdGreaterThanOrEqual(Integer indexIdGreaterThanOrEqual) {
		this.indexIdGreaterThanOrEqual = indexIdGreaterThanOrEqual;
	}

	public void setIndexIdLessThanOrEqual(Integer indexIdLessThanOrEqual) {
		this.indexIdLessThanOrEqual = indexIdLessThanOrEqual;
	}

	public void setIndexIds(List<Integer> indexIds) {
		this.indexIds = indexIds;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setTaskIdLike(String taskIdLike) {
		this.taskIdLike = taskIdLike;
	}

	public void setTaskIds(List<String> taskIds) {
		this.taskIds = taskIds;
	}

	public void setPfileFlag(Integer pfileFlag) {
		this.pfileFlag = pfileFlag;
	}

	public void setPfileFlagGreaterThanOrEqual(
			Integer pfileFlagGreaterThanOrEqual) {
		this.pfileFlagGreaterThanOrEqual = pfileFlagGreaterThanOrEqual;
	}

	public void setPfileFlagLessThanOrEqual(Integer pfileFlagLessThanOrEqual) {
		this.pfileFlagLessThanOrEqual = pfileFlagLessThanOrEqual;
	}

	public void setPfileFlags(List<Integer> pfileFlags) {
		this.pfileFlags = pfileFlags;
	}

	public void setFileDotFileId(String fileDotFileId) {
		this.fileDotFileId = fileDotFileId;
	}

	public void setFileDotFileIdLike(String fileDotFileIdLike) {
		this.fileDotFileIdLike = fileDotFileIdLike;
	}

	public void setFileDotFileIds(List<String> fileDotFileIds) {
		this.fileDotFileIds = fileDotFileIds;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public void setChkNum(String chkNum) {
		this.chkNum = chkNum;
	}

	public void setChkNumLike(String chkNumLike) {
		this.chkNumLike = chkNumLike;
	}

	public void setChkNums(List<String> chkNums) {
		this.chkNums = chkNums;
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

	public void setChkTotal(Integer chkTotal) {
		this.chkTotal = chkTotal;
	}

	public void setChkTotalGreaterThanOrEqual(Integer chkTotalGreaterThanOrEqual) {
		this.chkTotalGreaterThanOrEqual = chkTotalGreaterThanOrEqual;
	}

	public void setChkTotalLessThanOrEqual(Integer chkTotalLessThanOrEqual) {
		this.chkTotalLessThanOrEqual = chkTotalLessThanOrEqual;
	}

	public void setChkTotals(List<Integer> chkTotals) {
		this.chkTotals = chkTotals;
	}

	public void setChkResult(Integer chkResult) {
		this.chkResult = chkResult;
	}

	public void setChkResultGreaterThanOrEqual(
			Integer chkResultGreaterThanOrEqual) {
		this.chkResultGreaterThanOrEqual = chkResultGreaterThanOrEqual;
	}

	public void setChkResultLessThanOrEqual(Integer chkResultLessThanOrEqual) {
		this.chkResultLessThanOrEqual = chkResultLessThanOrEqual;
	}

	public void setChkResults(List<Integer> chkResults) {
		this.chkResults = chkResults;
	}

	public void setPfileId(String pfileId) {
		this.pfileId = pfileId;
	}

	public void setPfileIdLike(String pfileIdLike) {
		this.pfileIdLike = pfileIdLike;
	}

	public void setPfileIds(List<String> pfileIds) {
		this.pfileIds = pfileIds;
	}

	public void setTempSave(Integer tempSave) {
		this.tempSave = tempSave;
	}

	public void setTempSaveGreaterThanOrEqual(Integer tempSaveGreaterThanOrEqual) {
		this.tempSaveGreaterThanOrEqual = tempSaveGreaterThanOrEqual;
	}

	public void setTempSaveLessThanOrEqual(Integer tempSaveLessThanOrEqual) {
		this.tempSaveLessThanOrEqual = tempSaveLessThanOrEqual;
	}

	public void setTempSaves(List<Integer> tempSaves) {
		this.tempSaves = tempSaves;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserIdLike(String userIdLike) {
		this.userIdLike = userIdLike;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	public void setRefillFlag(Integer refillFlag) {
		this.refillFlag = refillFlag;
	}

	public void setRefillFlagGreaterThanOrEqual(
			Integer refillFlagGreaterThanOrEqual) {
		this.refillFlagGreaterThanOrEqual = refillFlagGreaterThanOrEqual;
	}

	public void setRefillFlagLessThanOrEqual(Integer refillFlagLessThanOrEqual) {
		this.refillFlagLessThanOrEqual = refillFlagLessThanOrEqual;
	}

	public void setRefillFlags(List<Integer> refillFlags) {
		this.refillFlags = refillFlags;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public void setGroupIdGreaterThanOrEqual(Integer groupIdGreaterThanOrEqual) {
		this.groupIdGreaterThanOrEqual = groupIdGreaterThanOrEqual;
	}

	public void setGroupIdLessThanOrEqual(Integer groupIdLessThanOrEqual) {
		this.groupIdLessThanOrEqual = groupIdLessThanOrEqual;
	}

	public void setGroupIds(List<Integer> groupIds) {
		this.groupIds = groupIds;
	}

	public void setOldId(String oldId) {
		this.oldId = oldId;
	}

	public void setOldIdLike(String oldIdLike) {
		this.oldIdLike = oldIdLike;
	}

	public void setOldIds(List<String> oldIds) {
		this.oldIds = oldIds;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public void setRoleIdGreaterThanOrEqual(Integer roleIdGreaterThanOrEqual) {
		this.roleIdGreaterThanOrEqual = roleIdGreaterThanOrEqual;
	}

	public void setRoleIdLessThanOrEqual(Integer roleIdLessThanOrEqual) {
		this.roleIdLessThanOrEqual = roleIdLessThanOrEqual;
	}

	public void setRoleIds(List<Integer> roleIds) {
		this.roleIds = roleIds;
	}

	public void setIsFinish(Integer isFinish) {
		this.isFinish = isFinish;
	}

	public void setIsFinishGreaterThanOrEqual(Integer isFinishGreaterThanOrEqual) {
		this.isFinishGreaterThanOrEqual = isFinishGreaterThanOrEqual;
	}

	public void setIsFinishLessThanOrEqual(Integer isFinishLessThanOrEqual) {
		this.isFinishLessThanOrEqual = isFinishLessThanOrEqual;
	}

	public void setIsFinishs(List<Integer> isFinishs) {
		this.isFinishs = isFinishs;
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

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public void setTypeIdLike(String typeIdLike) {
		this.typeIdLike = typeIdLike;
	}

	public void setTypeIds(List<String> typeIds) {
		this.typeIds = typeIds;
	}

	public void setTypeIndexId(Integer typeIndexId) {
		this.typeIndexId = typeIndexId;
	}

	public void setTypeIndexIdGreaterThanOrEqual(
			Integer typeIndexIdGreaterThanOrEqual) {
		this.typeIndexIdGreaterThanOrEqual = typeIndexIdGreaterThanOrEqual;
	}

	public void setTypeIndexIdLessThanOrEqual(Integer typeIndexIdLessThanOrEqual) {
		this.typeIndexIdLessThanOrEqual = typeIndexIdLessThanOrEqual;
	}

	public void setTypeIndexIds(List<Integer> typeIndexIds) {
		this.typeIndexIds = typeIndexIds;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	public void setMainIdLike(String mainIdLike) {
		this.mainIdLike = mainIdLike;
	}

	public void setMainIds(List<String> mainIds) {
		this.mainIds = mainIds;
	}

	public void setIntLastPage(Integer intLastPage) {
		this.intLastPage = intLastPage;
	}

	public void setIntLastPageGreaterThanOrEqual(
			Integer intLastPageGreaterThanOrEqual) {
		this.intLastPageGreaterThanOrEqual = intLastPageGreaterThanOrEqual;
	}

	public void setIntLastPageLessThanOrEqual(Integer intLastPageLessThanOrEqual) {
		this.intLastPageLessThanOrEqual = intLastPageLessThanOrEqual;
	}

	public void setIntLastPages(List<Integer> intLastPages) {
		this.intLastPages = intLastPages;
	}

	public void setIntSheets(Integer intSheets) {
		this.intSheets = intSheets;
	}

	public void setIntSheetsGreaterThanOrEqual(
			Integer intSheetsGreaterThanOrEqual) {
		this.intSheetsGreaterThanOrEqual = intSheetsGreaterThanOrEqual;
	}

	public void setIntSheetsLessThanOrEqual(Integer intSheetsLessThanOrEqual) {
		this.intSheetsLessThanOrEqual = intSheetsLessThanOrEqual;
	}

	public void setIntSheetss(List<Integer> intSheetss) {
		this.intSheetss = intSheetss;
	}

	public String getQueryStartDate() {
		return queryStartDate;
	}

	public void setQueryStartDate(String queryStartDate) {
		this.queryStartDate = queryStartDate;
	}

	public String getQueryStartDateGreaterThanOrEqual() {
		return queryStartDateGreaterThanOrEqual;
	}

	public void setQueryStartDateGreaterThanOrEqual(
			String queryStartDateGreaterThanOrEqual) {
		this.queryStartDateGreaterThanOrEqual = queryStartDateGreaterThanOrEqual;
	}

	public String getQueryStartDateLessThanOrEqual() {
		return queryStartDateLessThanOrEqual;
	}

	public void setQueryStartDateLessThanOrEqual(
			String queryStartDateLessThanOrEqual) {
		this.queryStartDateLessThanOrEqual = queryStartDateLessThanOrEqual;
	}

	public String getQueryEndDate() {
		return queryEndDate;
	}

	public void setQueryEndDate(String queryEndDate) {
		this.queryEndDate = queryEndDate;
	}

	public String getQueryEndDateGreaterThanOrEqual() {
		return queryEndDateGreaterThanOrEqual;
	}

	public void setQueryEndDateGreaterThanOrEqual(
			String queryEndDateGreaterThanOrEqual) {
		this.queryEndDateGreaterThanOrEqual = queryEndDateGreaterThanOrEqual;
	}

	public String getQueryEndDateLessThanOrEqual() {
		return queryEndDateLessThanOrEqual;
	}

	public void setQueryEndDateLessThanOrEqual(String queryEndDateLessThanOrEqual) {
		this.queryEndDateLessThanOrEqual = queryEndDateLessThanOrEqual;
	}

	public String getTreepinfoIdLike() {
		if (treepinfoIdLike != null && treepinfoIdLike.trim().length() > 0) {
			if (!treepinfoIdLike.endsWith("%")) {
				treepinfoIdLike = treepinfoIdLike + "%";
			}
		}
		return treepinfoIdLike;
	}

	public void setTreepinfoIdLike(String treepinfoIdLike) {
		this.treepinfoIdLike = treepinfoIdLike;
	}

	public String getNameNotLike() {
		if (nameNotLike != null && nameNotLike.trim().length() > 0) {
			if (!nameNotLike.startsWith("%")) {
				nameNotLike = "%" + nameNotLike;
			}
			if (!nameNotLike.endsWith("%")) {
				nameNotLike = nameNotLike + "%";
			}
		}
		return nameNotLike;
	}

	public void setNameNotLike(String nameNotLike) {
		this.nameNotLike = nameNotLike;
	}

	public CellFillFormQuery topId(String topId) {
		if (topId == null) {
			throw new RuntimeException("topId is null");
		}
		this.topId = topId;
		return this;
	}

	public CellFillFormQuery topIdLike(String topIdLike) {
		if (topIdLike == null) {
			throw new RuntimeException("topId is null");
		}
		this.topIdLike = topIdLike;
		return this;
	}

	public CellFillFormQuery topIds(List<String> topIds) {
		if (topIds == null) {
			throw new RuntimeException("topIds is empty ");
		}
		this.topIds = topIds;
		return this;
	}

	public CellFillFormQuery indexId(Integer indexId) {
		if (indexId == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexId = indexId;
		return this;
	}

	public CellFillFormQuery indexIdGreaterThanOrEqual(
			Integer indexIdGreaterThanOrEqual) {
		if (indexIdGreaterThanOrEqual == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexIdGreaterThanOrEqual = indexIdGreaterThanOrEqual;
		return this;
	}

	public CellFillFormQuery indexIdLessThanOrEqual(
			Integer indexIdLessThanOrEqual) {
		if (indexIdLessThanOrEqual == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexIdLessThanOrEqual = indexIdLessThanOrEqual;
		return this;
	}

	public CellFillFormQuery indexIds(List<Integer> indexIds) {
		if (indexIds == null) {
			throw new RuntimeException("indexIds is empty ");
		}
		this.indexIds = indexIds;
		return this;
	}

	public CellFillFormQuery taskId(String taskId) {
		if (taskId == null) {
			throw new RuntimeException("taskId is null");
		}
		this.taskId = taskId;
		return this;
	}

	public CellFillFormQuery taskIdLike(String taskIdLike) {
		if (taskIdLike == null) {
			throw new RuntimeException("taskId is null");
		}
		this.taskIdLike = taskIdLike;
		return this;
	}

	public CellFillFormQuery taskIds(List<String> taskIds) {
		if (taskIds == null) {
			throw new RuntimeException("taskIds is empty ");
		}
		this.taskIds = taskIds;
		return this;
	}

	public CellFillFormQuery pfileFlag(Integer pfileFlag) {
		if (pfileFlag == null) {
			throw new RuntimeException("pfileFlag is null");
		}
		this.pfileFlag = pfileFlag;
		return this;
	}

	public CellFillFormQuery pfileFlagGreaterThanOrEqual(
			Integer pfileFlagGreaterThanOrEqual) {
		if (pfileFlagGreaterThanOrEqual == null) {
			throw new RuntimeException("pfileFlag is null");
		}
		this.pfileFlagGreaterThanOrEqual = pfileFlagGreaterThanOrEqual;
		return this;
	}

	public CellFillFormQuery pfileFlagLessThanOrEqual(
			Integer pfileFlagLessThanOrEqual) {
		if (pfileFlagLessThanOrEqual == null) {
			throw new RuntimeException("pfileFlag is null");
		}
		this.pfileFlagLessThanOrEqual = pfileFlagLessThanOrEqual;
		return this;
	}

	public CellFillFormQuery pfileFlags(List<Integer> pfileFlags) {
		if (pfileFlags == null) {
			throw new RuntimeException("pfileFlags is empty ");
		}
		this.pfileFlags = pfileFlags;
		return this;
	}

	public CellFillFormQuery fileDotFileId(String fileDotFileId) {
		if (fileDotFileId == null) {
			throw new RuntimeException("fileDotFileId is null");
		}
		this.fileDotFileId = fileDotFileId;
		return this;
	}

	public CellFillFormQuery fileDotFileIdLike(String fileDotFileIdLike) {
		if (fileDotFileIdLike == null) {
			throw new RuntimeException("fileDotFileId is null");
		}
		this.fileDotFileIdLike = fileDotFileIdLike;
		return this;
	}

	public CellFillFormQuery fileDotFileIds(List<String> fileDotFileIds) {
		if (fileDotFileIds == null) {
			throw new RuntimeException("fileDotFileIds is empty ");
		}
		this.fileDotFileIds = fileDotFileIds;
		return this;
	}

	public CellFillFormQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public CellFillFormQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public CellFillFormQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public CellFillFormQuery chkNum(String chkNum) {
		if (chkNum == null) {
			throw new RuntimeException("chkNum is null");
		}
		this.chkNum = chkNum;
		return this;
	}

	public CellFillFormQuery chkNumLike(String chkNumLike) {
		if (chkNumLike == null) {
			throw new RuntimeException("chkNum is null");
		}
		this.chkNumLike = chkNumLike;
		return this;
	}

	public CellFillFormQuery chkNums(List<String> chkNums) {
		if (chkNums == null) {
			throw new RuntimeException("chkNums is empty ");
		}
		this.chkNums = chkNums;
		return this;
	}

	public CellFillFormQuery listNo(Integer listNo) {
		if (listNo == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNo = listNo;
		return this;
	}

	public CellFillFormQuery listNoGreaterThanOrEqual(
			Integer listNoGreaterThanOrEqual) {
		if (listNoGreaterThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoGreaterThanOrEqual = listNoGreaterThanOrEqual;
		return this;
	}

	public CellFillFormQuery listNoLessThanOrEqual(Integer listNoLessThanOrEqual) {
		if (listNoLessThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoLessThanOrEqual = listNoLessThanOrEqual;
		return this;
	}

	public CellFillFormQuery listNos(List<Integer> listNos) {
		if (listNos == null) {
			throw new RuntimeException("listNos is empty ");
		}
		this.listNos = listNos;
		return this;
	}

	public CellFillFormQuery chkTotal(Integer chkTotal) {
		if (chkTotal == null) {
			throw new RuntimeException("chkTotal is null");
		}
		this.chkTotal = chkTotal;
		return this;
	}

	public CellFillFormQuery chkTotalGreaterThanOrEqual(
			Integer chkTotalGreaterThanOrEqual) {
		if (chkTotalGreaterThanOrEqual == null) {
			throw new RuntimeException("chkTotal is null");
		}
		this.chkTotalGreaterThanOrEqual = chkTotalGreaterThanOrEqual;
		return this;
	}

	public CellFillFormQuery chkTotalLessThanOrEqual(
			Integer chkTotalLessThanOrEqual) {
		if (chkTotalLessThanOrEqual == null) {
			throw new RuntimeException("chkTotal is null");
		}
		this.chkTotalLessThanOrEqual = chkTotalLessThanOrEqual;
		return this;
	}

	public CellFillFormQuery chkTotals(List<Integer> chkTotals) {
		if (chkTotals == null) {
			throw new RuntimeException("chkTotals is empty ");
		}
		this.chkTotals = chkTotals;
		return this;
	}

	public CellFillFormQuery chkResult(Integer chkResult) {
		if (chkResult == null) {
			throw new RuntimeException("chkResult is null");
		}
		this.chkResult = chkResult;
		return this;
	}

	public CellFillFormQuery chkResultGreaterThanOrEqual(
			Integer chkResultGreaterThanOrEqual) {
		if (chkResultGreaterThanOrEqual == null) {
			throw new RuntimeException("chkResult is null");
		}
		this.chkResultGreaterThanOrEqual = chkResultGreaterThanOrEqual;
		return this;
	}

	public CellFillFormQuery chkResultLessThanOrEqual(
			Integer chkResultLessThanOrEqual) {
		if (chkResultLessThanOrEqual == null) {
			throw new RuntimeException("chkResult is null");
		}
		this.chkResultLessThanOrEqual = chkResultLessThanOrEqual;
		return this;
	}

	public CellFillFormQuery chkResults(List<Integer> chkResults) {
		if (chkResults == null) {
			throw new RuntimeException("chkResults is empty ");
		}
		this.chkResults = chkResults;
		return this;
	}

	public CellFillFormQuery pfileId(String pfileId) {
		if (pfileId == null) {
			throw new RuntimeException("pfileId is null");
		}
		this.pfileId = pfileId;
		return this;
	}

	public CellFillFormQuery pfileIdLike(String pfileIdLike) {
		if (pfileIdLike == null) {
			throw new RuntimeException("pfileId is null");
		}
		this.pfileIdLike = pfileIdLike;
		return this;
	}

	public CellFillFormQuery pfileIds(List<String> pfileIds) {
		if (pfileIds == null) {
			throw new RuntimeException("pfileIds is empty ");
		}
		this.pfileIds = pfileIds;
		return this;
	}

	public CellFillFormQuery tempSave(Integer tempSave) {
		if (tempSave == null) {
			throw new RuntimeException("tempSave is null");
		}
		this.tempSave = tempSave;
		return this;
	}

	public CellFillFormQuery tempSaveGreaterThanOrEqual(
			Integer tempSaveGreaterThanOrEqual) {
		if (tempSaveGreaterThanOrEqual == null) {
			throw new RuntimeException("tempSave is null");
		}
		this.tempSaveGreaterThanOrEqual = tempSaveGreaterThanOrEqual;
		return this;
	}

	public CellFillFormQuery tempSaveLessThanOrEqual(
			Integer tempSaveLessThanOrEqual) {
		if (tempSaveLessThanOrEqual == null) {
			throw new RuntimeException("tempSave is null");
		}
		this.tempSaveLessThanOrEqual = tempSaveLessThanOrEqual;
		return this;
	}

	public CellFillFormQuery tempSaves(List<Integer> tempSaves) {
		if (tempSaves == null) {
			throw new RuntimeException("tempSaves is empty ");
		}
		this.tempSaves = tempSaves;
		return this;
	}

	public CellFillFormQuery userId(String userId) {
		if (userId == null) {
			throw new RuntimeException("userId is null");
		}
		this.userId = userId;
		return this;
	}

	public CellFillFormQuery userIdLike(String userIdLike) {
		if (userIdLike == null) {
			throw new RuntimeException("userId is null");
		}
		this.userIdLike = userIdLike;
		return this;
	}

	public CellFillFormQuery userIds(List<String> userIds) {
		if (userIds == null) {
			throw new RuntimeException("userIds is empty ");
		}
		this.userIds = userIds;
		return this;
	}

	public CellFillFormQuery refillFlag(Integer refillFlag) {
		if (refillFlag == null) {
			throw new RuntimeException("refillFlag is null");
		}
		this.refillFlag = refillFlag;
		return this;
	}

	public CellFillFormQuery refillFlagGreaterThanOrEqual(
			Integer refillFlagGreaterThanOrEqual) {
		if (refillFlagGreaterThanOrEqual == null) {
			throw new RuntimeException("refillFlag is null");
		}
		this.refillFlagGreaterThanOrEqual = refillFlagGreaterThanOrEqual;
		return this;
	}

	public CellFillFormQuery refillFlagLessThanOrEqual(
			Integer refillFlagLessThanOrEqual) {
		if (refillFlagLessThanOrEqual == null) {
			throw new RuntimeException("refillFlag is null");
		}
		this.refillFlagLessThanOrEqual = refillFlagLessThanOrEqual;
		return this;
	}

	public CellFillFormQuery refillFlags(List<Integer> refillFlags) {
		if (refillFlags == null) {
			throw new RuntimeException("refillFlags is empty ");
		}
		this.refillFlags = refillFlags;
		return this;
	}

	public CellFillFormQuery groupId(Integer groupId) {
		if (groupId == null) {
			throw new RuntimeException("groupId is null");
		}
		this.groupId = groupId;
		return this;
	}

	public CellFillFormQuery groupIdGreaterThanOrEqual(
			Integer groupIdGreaterThanOrEqual) {
		if (groupIdGreaterThanOrEqual == null) {
			throw new RuntimeException("groupId is null");
		}
		this.groupIdGreaterThanOrEqual = groupIdGreaterThanOrEqual;
		return this;
	}

	public CellFillFormQuery groupIdLessThanOrEqual(
			Integer groupIdLessThanOrEqual) {
		if (groupIdLessThanOrEqual == null) {
			throw new RuntimeException("groupId is null");
		}
		this.groupIdLessThanOrEqual = groupIdLessThanOrEqual;
		return this;
	}

	public CellFillFormQuery groupIds(List<Integer> groupIds) {
		if (groupIds == null) {
			throw new RuntimeException("groupIds is empty ");
		}
		this.groupIds = groupIds;
		return this;
	}

	public CellFillFormQuery oldId(String oldId) {
		if (oldId == null) {
			throw new RuntimeException("oldId is null");
		}
		this.oldId = oldId;
		return this;
	}

	public CellFillFormQuery oldIdLike(String oldIdLike) {
		if (oldIdLike == null) {
			throw new RuntimeException("oldId is null");
		}
		this.oldIdLike = oldIdLike;
		return this;
	}

	public CellFillFormQuery oldIds(List<String> oldIds) {
		if (oldIds == null) {
			throw new RuntimeException("oldIds is empty ");
		}
		this.oldIds = oldIds;
		return this;
	}

	public CellFillFormQuery roleId(Integer roleId) {
		if (roleId == null) {
			throw new RuntimeException("roleId is null");
		}
		this.roleId = roleId;
		return this;
	}

	public CellFillFormQuery roleIdGreaterThanOrEqual(
			Integer roleIdGreaterThanOrEqual) {
		if (roleIdGreaterThanOrEqual == null) {
			throw new RuntimeException("roleId is null");
		}
		this.roleIdGreaterThanOrEqual = roleIdGreaterThanOrEqual;
		return this;
	}

	public CellFillFormQuery roleIdLessThanOrEqual(Integer roleIdLessThanOrEqual) {
		if (roleIdLessThanOrEqual == null) {
			throw new RuntimeException("roleId is null");
		}
		this.roleIdLessThanOrEqual = roleIdLessThanOrEqual;
		return this;
	}

	public CellFillFormQuery roleIds(List<Integer> roleIds) {
		if (roleIds == null) {
			throw new RuntimeException("roleIds is empty ");
		}
		this.roleIds = roleIds;
		return this;
	}

	public CellFillFormQuery isFinish(Integer isFinish) {
		if (isFinish == null) {
			throw new RuntimeException("isFinish is null");
		}
		this.isFinish = isFinish;
		return this;
	}

	public CellFillFormQuery isFinishGreaterThanOrEqual(
			Integer isFinishGreaterThanOrEqual) {
		if (isFinishGreaterThanOrEqual == null) {
			throw new RuntimeException("isFinish is null");
		}
		this.isFinishGreaterThanOrEqual = isFinishGreaterThanOrEqual;
		return this;
	}

	public CellFillFormQuery isFinishLessThanOrEqual(
			Integer isFinishLessThanOrEqual) {
		if (isFinishLessThanOrEqual == null) {
			throw new RuntimeException("isFinish is null");
		}
		this.isFinishLessThanOrEqual = isFinishLessThanOrEqual;
		return this;
	}

	public CellFillFormQuery isFinishs(List<Integer> isFinishs) {
		if (isFinishs == null) {
			throw new RuntimeException("isFinishs is empty ");
		}
		this.isFinishs = isFinishs;
		return this;
	}

	public CellFillFormQuery typeTableName(String typeTableName) {
		if (typeTableName == null) {
			throw new RuntimeException("typeTableName is null");
		}
		this.typeTableName = typeTableName;
		return this;
	}

	public CellFillFormQuery typeTableNameLike(String typeTableNameLike) {
		if (typeTableNameLike == null) {
			throw new RuntimeException("typeTableName is null");
		}
		this.typeTableNameLike = typeTableNameLike;
		return this;
	}

	public CellFillFormQuery typeTableNames(List<String> typeTableNames) {
		if (typeTableNames == null) {
			throw new RuntimeException("typeTableNames is empty ");
		}
		this.typeTableNames = typeTableNames;
		return this;
	}

	public CellFillFormQuery typeId(String typeId) {
		if (typeId == null) {
			throw new RuntimeException("typeId is null");
		}
		this.typeId = typeId;
		return this;
	}

	public CellFillFormQuery typeIdLike(String typeIdLike) {
		if (typeIdLike == null) {
			throw new RuntimeException("typeId is null");
		}
		this.typeIdLike = typeIdLike;
		return this;
	}

	public CellFillFormQuery typeIds(List<String> typeIds) {
		if (typeIds == null) {
			throw new RuntimeException("typeIds is empty ");
		}
		this.typeIds = typeIds;
		return this;
	}

	public CellFillFormQuery typeIndexId(Integer typeIndexId) {
		if (typeIndexId == null) {
			throw new RuntimeException("typeIndexId is null");
		}
		this.typeIndexId = typeIndexId;
		return this;
	}

	public CellFillFormQuery typeIndexIdGreaterThanOrEqual(
			Integer typeIndexIdGreaterThanOrEqual) {
		if (typeIndexIdGreaterThanOrEqual == null) {
			throw new RuntimeException("typeIndexId is null");
		}
		this.typeIndexIdGreaterThanOrEqual = typeIndexIdGreaterThanOrEqual;
		return this;
	}

	public CellFillFormQuery typeIndexIdLessThanOrEqual(
			Integer typeIndexIdLessThanOrEqual) {
		if (typeIndexIdLessThanOrEqual == null) {
			throw new RuntimeException("typeIndexId is null");
		}
		this.typeIndexIdLessThanOrEqual = typeIndexIdLessThanOrEqual;
		return this;
	}

	public CellFillFormQuery typeIndexIds(List<Integer> typeIndexIds) {
		if (typeIndexIds == null) {
			throw new RuntimeException("typeIndexIds is empty ");
		}
		this.typeIndexIds = typeIndexIds;
		return this;
	}

	public CellFillFormQuery mainId(String mainId) {
		if (mainId == null) {
			throw new RuntimeException("mainId is null");
		}
		this.mainId = mainId;
		return this;
	}

	public CellFillFormQuery mainIdLike(String mainIdLike) {
		if (mainIdLike == null) {
			throw new RuntimeException("mainId is null");
		}
		this.mainIdLike = mainIdLike;
		return this;
	}

	public CellFillFormQuery mainIds(List<String> mainIds) {
		if (mainIds == null) {
			throw new RuntimeException("mainIds is empty ");
		}
		this.mainIds = mainIds;
		return this;
	}

	public CellFillFormQuery intLastPage(Integer intLastPage) {
		if (intLastPage == null) {
			throw new RuntimeException("intLastPage is null");
		}
		this.intLastPage = intLastPage;
		return this;
	}

	public CellFillFormQuery intLastPageGreaterThanOrEqual(
			Integer intLastPageGreaterThanOrEqual) {
		if (intLastPageGreaterThanOrEqual == null) {
			throw new RuntimeException("intLastPage is null");
		}
		this.intLastPageGreaterThanOrEqual = intLastPageGreaterThanOrEqual;
		return this;
	}

	public CellFillFormQuery intLastPageLessThanOrEqual(
			Integer intLastPageLessThanOrEqual) {
		if (intLastPageLessThanOrEqual == null) {
			throw new RuntimeException("intLastPage is null");
		}
		this.intLastPageLessThanOrEqual = intLastPageLessThanOrEqual;
		return this;
	}

	public CellFillFormQuery intLastPages(List<Integer> intLastPages) {
		if (intLastPages == null) {
			throw new RuntimeException("intLastPages is empty ");
		}
		this.intLastPages = intLastPages;
		return this;
	}

	public CellFillFormQuery intSheets(Integer intSheets) {
		if (intSheets == null) {
			throw new RuntimeException("intSheets is null");
		}
		this.intSheets = intSheets;
		return this;
	}

	public CellFillFormQuery intSheetsGreaterThanOrEqual(
			Integer intSheetsGreaterThanOrEqual) {
		if (intSheetsGreaterThanOrEqual == null) {
			throw new RuntimeException("intSheets is null");
		}
		this.intSheetsGreaterThanOrEqual = intSheetsGreaterThanOrEqual;
		return this;
	}

	public CellFillFormQuery intSheetsLessThanOrEqual(
			Integer intSheetsLessThanOrEqual) {
		if (intSheetsLessThanOrEqual == null) {
			throw new RuntimeException("intSheets is null");
		}
		this.intSheetsLessThanOrEqual = intSheetsLessThanOrEqual;
		return this;
	}

	public CellFillFormQuery intSheetss(List<Integer> intSheetss) {
		if (intSheetss == null) {
			throw new RuntimeException("intSheetss is empty ");
		}
		this.intSheetss = intSheetss;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("topId".equals(sortColumn)) {
				orderBy = "E.TOPID" + a_x;
			}

			if ("indexId".equals(sortColumn)) {
				orderBy = "E.INDEX_ID" + a_x;
			}

			if ("taskId".equals(sortColumn)) {
				orderBy = "E.TASK_ID" + a_x;
			}

			if ("pfileFlag".equals(sortColumn)) {
				orderBy = "E.PFILEFLAG" + a_x;
			}

			if ("fileDotFileId".equals(sortColumn)) {
				orderBy = "E.FILEDOT_FILEID" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME" + a_x;
			}

			if ("chkNum".equals(sortColumn)) {
				orderBy = "E.CHKNUM" + a_x;
			}

			if ("listNo".equals(sortColumn)) {
				orderBy = "E.LISTNO" + a_x;
			}

			if ("chkTotal".equals(sortColumn)) {
				orderBy = "E.CHKTOTAL" + a_x;
			}

			if ("chkResult".equals(sortColumn)) {
				orderBy = "E.CHKRESULT" + a_x;
			}

			if ("pfileId".equals(sortColumn)) {
				orderBy = "E.PFILE_ID" + a_x;
			}

			if ("tempSave".equals(sortColumn)) {
				orderBy = "E.TEMPSAVE" + a_x;
			}

			if ("userId".equals(sortColumn)) {
				orderBy = "E.USERID" + a_x;
			}

			if ("refillFlag".equals(sortColumn)) {
				orderBy = "E.REFILLFLAG" + a_x;
			}

			if ("groupId".equals(sortColumn)) {
				orderBy = "E.GROUPID" + a_x;
			}

			if ("oldId".equals(sortColumn)) {
				orderBy = "E.OLD_ID" + a_x;
			}

			if ("roleId".equals(sortColumn)) {
				orderBy = "E.ROLE_ID" + a_x;
			}

			if ("isFinish".equals(sortColumn)) {
				orderBy = "E.ISFINISH" + a_x;
			}

			if ("typeTableName".equals(sortColumn)) {
				orderBy = "E.TYPE_TABLENAME" + a_x;
			}

			if ("typeId".equals(sortColumn)) {
				orderBy = "E.TYPE_ID" + a_x;
			}

			if ("typeIndexId".equals(sortColumn)) {
				orderBy = "E.TYPE_INDEX_ID" + a_x;
			}

			if ("mainId".equals(sortColumn)) {
				orderBy = "E.MAIN_ID" + a_x;
			}

			if ("intLastPage".equals(sortColumn)) {
				orderBy = "E.INTLASTPAGE" + a_x;
			}

			if ("intSheets".equals(sortColumn)) {
				orderBy = "E.INTSHEETS" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("topId", "TOPID");
		addColumn("indexId", "INDEX_ID");
		addColumn("taskId", "TASK_ID");
		addColumn("pfileFlag", "PFILEFLAG");
		addColumn("fileDotFileId", "FILEDOT_FILEID");
		addColumn("name", "NAME");
		addColumn("chkNum", "CHKNUM");
		addColumn("listNo", "LISTNO");
		addColumn("chkTotal", "CHKTOTAL");
		addColumn("chkResult", "CHKRESULT");
		addColumn("pfileId", "PFILE_ID");
		addColumn("tempSave", "TEMPSAVE");
		addColumn("userId", "USERID");
		addColumn("refillFlag", "REFILLFLAG");
		addColumn("groupId", "GROUPID");
		addColumn("oldId", "OLD_ID");
		addColumn("roleId", "ROLE_ID");
		addColumn("isFinish", "ISFINISH");
		addColumn("typeTableName", "TYPE_TABLENAME");
		addColumn("typeId", "TYPE_ID");
		addColumn("typeIndexId", "TYPE_INDEX_ID");
		addColumn("mainId", "MAIN_ID");
		addColumn("intLastPage", "INTLASTPAGE");
		addColumn("intSheets", "INTSHEETS");
	}

	public String getData_id() {
		return data_id;
	}

	public void setData_id(String data_id) {
		this.data_id = data_id;
	}

}