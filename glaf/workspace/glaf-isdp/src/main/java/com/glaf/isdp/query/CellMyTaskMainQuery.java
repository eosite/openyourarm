package com.glaf.isdp.query;

import java.util.*;

import com.glaf.core.query.DataQuery;

public class CellMyTaskMainQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected Date ctimeGreaterThanOrEqual;
	protected Date ctimeLessThanOrEqual;
	protected Integer indexId;
	protected Integer indexIdGreaterThanOrEqual;
	protected Integer indexIdLessThanOrEqual;
	protected List<Integer> indexIds;
	protected String taskId;
	protected String taskIdLike;
	protected List<String> taskIds;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String projName;
	protected String projNameLike;
	protected List<String> projNames;
	protected Integer listNo;
	protected Integer listNoGreaterThanOrEqual;
	protected Integer listNoLessThanOrEqual;
	protected List<Integer> listNos;
	protected Integer typeIndexId;
	protected Integer typeIndexIdGreaterThanOrEqual;
	protected Integer typeIndexIdLessThanOrEqual;
	protected List<Integer> typeIndexIds;
	protected Integer flagInt;
	protected Integer flagIntGreaterThanOrEqual;
	protected Integer flagIntLessThanOrEqual;
	protected List<Integer> flagInts;
	protected String myCellTsksId;
	protected String myCellTsksIdLike;
	protected List<String> myCellTsksIds;
	protected String fromTasksId;
	protected String fromTasksIdLike;
	protected List<String> fromTasksIds;
	protected String toTaskId;
	protected String toTaskIdLike;
	protected List<String> toTaskIds;
	protected Integer intFinish;
	protected Integer intFinishGreaterThanOrEqual;
	protected Integer intFinishLessThanOrEqual;
	protected List<Integer> intFinishs;
	protected String fileContent;
	protected String fileContentLike;
	protected List<String> fileContents;
	protected String typeTableName;
	protected String typeTableNameLike;
	protected List<String> typeTableNames;
	protected String typeId;
	protected String typeIdLike;
	protected List<String> typeIds;
	protected String userId;
	protected String userIdLike;
	protected List<String> userIds;
	protected String netRoleId;
	protected String netRoleIdLike;
	protected List<String> netRoleIds;
	protected Integer intIsFlow;
	protected Integer intIsFlowGreaterThanOrEqual;
	protected Integer intIsFlowLessThanOrEqual;
	protected List<Integer> intIsFlows;
	protected Integer intStop;
	protected Integer intStopGreaterThanOrEqual;
	protected Integer intStopLessThanOrEqual;
	protected List<Integer> intStops;
	protected Integer fileTypeIndex;
	protected Integer fileTypeIndexGreaterThanOrEqual;
	protected Integer fileTypeIndexLessThanOrEqual;
	protected List<Integer> fileTypeIndexs;
	protected String SQLCondition;

	public CellMyTaskMainQuery() {

	}

	public String getSQLCondition() {
		return SQLCondition;
	}

	public void setSQLCondition(String sQLCondition) {
		SQLCondition = sQLCondition;
	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public Date getCtimeGreaterThanOrEqual() {
		return ctimeGreaterThanOrEqual;
	}

	public Date getCtimeLessThanOrEqual() {
		return ctimeLessThanOrEqual;
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

	public String getProjName() {
		return projName;
	}

	public String getProjNameLike() {
		if (projNameLike != null && projNameLike.trim().length() > 0) {
			if (!projNameLike.startsWith("%")) {
				projNameLike = "%" + projNameLike;
			}
			if (!projNameLike.endsWith("%")) {
				projNameLike = projNameLike + "%";
			}
		}
		return projNameLike;
	}

	public List<String> getProjNames() {
		return projNames;
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

	public Integer getFlagInt() {
		return flagInt;
	}

	public Integer getFlagIntGreaterThanOrEqual() {
		return flagIntGreaterThanOrEqual;
	}

	public Integer getFlagIntLessThanOrEqual() {
		return flagIntLessThanOrEqual;
	}

	public List<Integer> getFlagInts() {
		return flagInts;
	}

	public String getMyCellTsksId() {
		return myCellTsksId;
	}

	public String getMyCellTsksIdLike() {
		if (myCellTsksIdLike != null && myCellTsksIdLike.trim().length() > 0) {
			if (!myCellTsksIdLike.startsWith("%")) {
				myCellTsksIdLike = "%" + myCellTsksIdLike;
			}
			if (!myCellTsksIdLike.endsWith("%")) {
				myCellTsksIdLike = myCellTsksIdLike + "%";
			}
		}
		return myCellTsksIdLike;
	}

	public List<String> getMyCellTsksIds() {
		return myCellTsksIds;
	}

	public String getFromTasksId() {
		return fromTasksId;
	}

	public String getFromTasksIdLike() {
		if (fromTasksIdLike != null && fromTasksIdLike.trim().length() > 0) {
			if (!fromTasksIdLike.startsWith("%")) {
				fromTasksIdLike = "%" + fromTasksIdLike;
			}
			if (!fromTasksIdLike.endsWith("%")) {
				fromTasksIdLike = fromTasksIdLike + "%";
			}
		}
		return fromTasksIdLike;
	}

	public List<String> getFromTasksIds() {
		return fromTasksIds;
	}

	public String getToTaskId() {
		return toTaskId;
	}

	public String getToTaskIdLike() {
		if (toTaskIdLike != null && toTaskIdLike.trim().length() > 0) {
			if (!toTaskIdLike.startsWith("%")) {
				toTaskIdLike = "%" + toTaskIdLike;
			}
			if (!toTaskIdLike.endsWith("%")) {
				toTaskIdLike = toTaskIdLike + "%";
			}
		}
		return toTaskIdLike;
	}

	public List<String> getToTaskIds() {
		return toTaskIds;
	}

	public Integer getIntFinish() {
		return intFinish;
	}

	public Integer getIntFinishGreaterThanOrEqual() {
		return intFinishGreaterThanOrEqual;
	}

	public Integer getIntFinishLessThanOrEqual() {
		return intFinishLessThanOrEqual;
	}

	public List<Integer> getIntFinishs() {
		return intFinishs;
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

	public String getNetRoleId() {
		return netRoleId;
	}

	public String getNetRoleIdLike() {
		if (netRoleIdLike != null && netRoleIdLike.trim().length() > 0) {
			if (!netRoleIdLike.startsWith("%")) {
				netRoleIdLike = "%" + netRoleIdLike;
			}
			if (!netRoleIdLike.endsWith("%")) {
				netRoleIdLike = netRoleIdLike + "%";
			}
		}
		return netRoleIdLike;
	}

	public List<String> getNetRoleIds() {
		return netRoleIds;
	}

	public Integer getIntIsFlow() {
		return intIsFlow;
	}

	public Integer getIntIsFlowGreaterThanOrEqual() {
		return intIsFlowGreaterThanOrEqual;
	}

	public Integer getIntIsFlowLessThanOrEqual() {
		return intIsFlowLessThanOrEqual;
	}

	public List<Integer> getIntIsFlows() {
		return intIsFlows;
	}

	public Integer getIntStop() {
		return intStop;
	}

	public Integer getIntStopGreaterThanOrEqual() {
		return intStopGreaterThanOrEqual;
	}

	public Integer getIntStopLessThanOrEqual() {
		return intStopLessThanOrEqual;
	}

	public List<Integer> getIntStops() {
		return intStops;
	}

	public Integer getFileTypeIndex() {
		return fileTypeIndex;
	}

	public Integer getFileTypeIndexGreaterThanOrEqual() {
		return fileTypeIndexGreaterThanOrEqual;
	}

	public Integer getFileTypeIndexLessThanOrEqual() {
		return fileTypeIndexLessThanOrEqual;
	}

	public List<Integer> getFileTypeIndexs() {
		return fileTypeIndexs;
	}

	public void setCtimeGreaterThanOrEqual(Date ctimeGreaterThanOrEqual) {
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
	}

	public void setCtimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public void setProjNameLike(String projNameLike) {
		this.projNameLike = projNameLike;
	}

	public void setProjNames(List<String> projNames) {
		this.projNames = projNames;
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

	public void setFlagInt(Integer flagInt) {
		this.flagInt = flagInt;
	}

	public void setFlagIntGreaterThanOrEqual(Integer flagIntGreaterThanOrEqual) {
		this.flagIntGreaterThanOrEqual = flagIntGreaterThanOrEqual;
	}

	public void setFlagIntLessThanOrEqual(Integer flagIntLessThanOrEqual) {
		this.flagIntLessThanOrEqual = flagIntLessThanOrEqual;
	}

	public void setFlagInts(List<Integer> flagInts) {
		this.flagInts = flagInts;
	}

	public void setMyCellTsksId(String myCellTsksId) {
		this.myCellTsksId = myCellTsksId;
	}

	public void setMyCellTsksIdLike(String myCellTsksIdLike) {
		this.myCellTsksIdLike = myCellTsksIdLike;
	}

	public void setMyCellTsksIds(List<String> myCellTsksIds) {
		this.myCellTsksIds = myCellTsksIds;
	}

	public void setFromTasksId(String fromTasksId) {
		this.fromTasksId = fromTasksId;
	}

	public void setFromTasksIdLike(String fromTasksIdLike) {
		this.fromTasksIdLike = fromTasksIdLike;
	}

	public void setFromTasksIds(List<String> fromTasksIds) {
		this.fromTasksIds = fromTasksIds;
	}

	public void setToTaskId(String toTaskId) {
		this.toTaskId = toTaskId;
	}

	public void setToTaskIdLike(String toTaskIdLike) {
		this.toTaskIdLike = toTaskIdLike;
	}

	public void setToTaskIds(List<String> toTaskIds) {
		this.toTaskIds = toTaskIds;
	}

	public void setIntFinish(Integer intFinish) {
		this.intFinish = intFinish;
	}

	public void setIntFinishGreaterThanOrEqual(
			Integer intFinishGreaterThanOrEqual) {
		this.intFinishGreaterThanOrEqual = intFinishGreaterThanOrEqual;
	}

	public void setIntFinishLessThanOrEqual(Integer intFinishLessThanOrEqual) {
		this.intFinishLessThanOrEqual = intFinishLessThanOrEqual;
	}

	public void setIntFinishs(List<Integer> intFinishs) {
		this.intFinishs = intFinishs;
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

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserIdLike(String userIdLike) {
		this.userIdLike = userIdLike;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	public void setNetRoleId(String netRoleId) {
		this.netRoleId = netRoleId;
	}

	public void setNetRoleIdLike(String netRoleIdLike) {
		this.netRoleIdLike = netRoleIdLike;
	}

	public void setNetRoleIds(List<String> netRoleIds) {
		this.netRoleIds = netRoleIds;
	}

	public void setIntIsFlow(Integer intIsFlow) {
		this.intIsFlow = intIsFlow;
	}

	public void setIntIsFlowGreaterThanOrEqual(
			Integer intIsFlowGreaterThanOrEqual) {
		this.intIsFlowGreaterThanOrEqual = intIsFlowGreaterThanOrEqual;
	}

	public void setIntIsFlowLessThanOrEqual(Integer intIsFlowLessThanOrEqual) {
		this.intIsFlowLessThanOrEqual = intIsFlowLessThanOrEqual;
	}

	public void setIntIsFlows(List<Integer> intIsFlows) {
		this.intIsFlows = intIsFlows;
	}

	public void setIntStop(Integer intStop) {
		this.intStop = intStop;
	}

	public void setIntStopGreaterThanOrEqual(Integer intStopGreaterThanOrEqual) {
		this.intStopGreaterThanOrEqual = intStopGreaterThanOrEqual;
	}

	public void setIntStopLessThanOrEqual(Integer intStopLessThanOrEqual) {
		this.intStopLessThanOrEqual = intStopLessThanOrEqual;
	}

	public void setIntStops(List<Integer> intStops) {
		this.intStops = intStops;
	}

	public void setFileTypeIndex(Integer fileTypeIndex) {
		this.fileTypeIndex = fileTypeIndex;
	}

	public void setFileTypeIndexGreaterThanOrEqual(
			Integer fileTypeIndexGreaterThanOrEqual) {
		this.fileTypeIndexGreaterThanOrEqual = fileTypeIndexGreaterThanOrEqual;
	}

	public void setFileTypeIndexLessThanOrEqual(
			Integer fileTypeIndexLessThanOrEqual) {
		this.fileTypeIndexLessThanOrEqual = fileTypeIndexLessThanOrEqual;
	}

	public void setFileTypeIndexs(List<Integer> fileTypeIndexs) {
		this.fileTypeIndexs = fileTypeIndexs;
	}

	public CellMyTaskMainQuery ctimeGreaterThanOrEqual(
			Date ctimeGreaterThanOrEqual) {
		if (ctimeGreaterThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
		return this;
	}

	public CellMyTaskMainQuery ctimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		if (ctimeLessThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
		return this;
	}

	public CellMyTaskMainQuery indexId(Integer indexId) {
		if (indexId == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexId = indexId;
		return this;
	}

	public CellMyTaskMainQuery indexIdGreaterThanOrEqual(
			Integer indexIdGreaterThanOrEqual) {
		if (indexIdGreaterThanOrEqual == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexIdGreaterThanOrEqual = indexIdGreaterThanOrEqual;
		return this;
	}

	public CellMyTaskMainQuery indexIdLessThanOrEqual(
			Integer indexIdLessThanOrEqual) {
		if (indexIdLessThanOrEqual == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexIdLessThanOrEqual = indexIdLessThanOrEqual;
		return this;
	}

	public CellMyTaskMainQuery indexIds(List<Integer> indexIds) {
		if (indexIds == null) {
			throw new RuntimeException("indexIds is empty ");
		}
		this.indexIds = indexIds;
		return this;
	}

	public CellMyTaskMainQuery taskId(String taskId) {
		if (taskId == null) {
			throw new RuntimeException("taskId is null");
		}
		this.taskId = taskId;
		return this;
	}

	public CellMyTaskMainQuery taskIdLike(String taskIdLike) {
		if (taskIdLike == null) {
			throw new RuntimeException("taskId is null");
		}
		this.taskIdLike = taskIdLike;
		return this;
	}

	public CellMyTaskMainQuery taskIds(List<String> taskIds) {
		if (taskIds == null) {
			throw new RuntimeException("taskIds is empty ");
		}
		this.taskIds = taskIds;
		return this;
	}

	public CellMyTaskMainQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public CellMyTaskMainQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public CellMyTaskMainQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public CellMyTaskMainQuery projName(String projName) {
		if (projName == null) {
			throw new RuntimeException("projName is null");
		}
		this.projName = projName;
		return this;
	}

	public CellMyTaskMainQuery projNameLike(String projNameLike) {
		if (projNameLike == null) {
			throw new RuntimeException("projName is null");
		}
		this.projNameLike = projNameLike;
		return this;
	}

	public CellMyTaskMainQuery projNames(List<String> projNames) {
		if (projNames == null) {
			throw new RuntimeException("projNames is empty ");
		}
		this.projNames = projNames;
		return this;
	}

	public CellMyTaskMainQuery listNo(Integer listNo) {
		if (listNo == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNo = listNo;
		return this;
	}

	public CellMyTaskMainQuery listNoGreaterThanOrEqual(
			Integer listNoGreaterThanOrEqual) {
		if (listNoGreaterThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoGreaterThanOrEqual = listNoGreaterThanOrEqual;
		return this;
	}

	public CellMyTaskMainQuery listNoLessThanOrEqual(
			Integer listNoLessThanOrEqual) {
		if (listNoLessThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoLessThanOrEqual = listNoLessThanOrEqual;
		return this;
	}

	public CellMyTaskMainQuery listNos(List<Integer> listNos) {
		if (listNos == null) {
			throw new RuntimeException("listNos is empty ");
		}
		this.listNos = listNos;
		return this;
	}

	public CellMyTaskMainQuery typeIndexId(Integer typeIndexId) {
		if (typeIndexId == null) {
			throw new RuntimeException("typeIndexId is null");
		}
		this.typeIndexId = typeIndexId;
		return this;
	}

	public CellMyTaskMainQuery typeIndexIdGreaterThanOrEqual(
			Integer typeIndexIdGreaterThanOrEqual) {
		if (typeIndexIdGreaterThanOrEqual == null) {
			throw new RuntimeException("typeIndexId is null");
		}
		this.typeIndexIdGreaterThanOrEqual = typeIndexIdGreaterThanOrEqual;
		return this;
	}

	public CellMyTaskMainQuery typeIndexIdLessThanOrEqual(
			Integer typeIndexIdLessThanOrEqual) {
		if (typeIndexIdLessThanOrEqual == null) {
			throw new RuntimeException("typeIndexId is null");
		}
		this.typeIndexIdLessThanOrEqual = typeIndexIdLessThanOrEqual;
		return this;
	}

	public CellMyTaskMainQuery typeIndexIds(List<Integer> typeIndexIds) {
		if (typeIndexIds == null) {
			throw new RuntimeException("typeIndexIds is empty ");
		}
		this.typeIndexIds = typeIndexIds;
		return this;
	}

	public CellMyTaskMainQuery flagInt(Integer flagInt) {
		if (flagInt == null) {
			throw new RuntimeException("flagInt is null");
		}
		this.flagInt = flagInt;
		return this;
	}

	public CellMyTaskMainQuery flagIntGreaterThanOrEqual(
			Integer flagIntGreaterThanOrEqual) {
		if (flagIntGreaterThanOrEqual == null) {
			throw new RuntimeException("flagInt is null");
		}
		this.flagIntGreaterThanOrEqual = flagIntGreaterThanOrEqual;
		return this;
	}

	public CellMyTaskMainQuery flagIntLessThanOrEqual(
			Integer flagIntLessThanOrEqual) {
		if (flagIntLessThanOrEqual == null) {
			throw new RuntimeException("flagInt is null");
		}
		this.flagIntLessThanOrEqual = flagIntLessThanOrEqual;
		return this;
	}

	public CellMyTaskMainQuery flagInts(List<Integer> flagInts) {
		if (flagInts == null) {
			throw new RuntimeException("flagInts is empty ");
		}
		this.flagInts = flagInts;
		return this;
	}

	public CellMyTaskMainQuery myCellTsksId(String myCellTsksId) {
		if (myCellTsksId == null) {
			throw new RuntimeException("myCellTsksId is null");
		}
		this.myCellTsksId = myCellTsksId;
		return this;
	}

	public CellMyTaskMainQuery myCellTsksIdLike(String myCellTsksIdLike) {
		if (myCellTsksIdLike == null) {
			throw new RuntimeException("myCellTsksId is null");
		}
		this.myCellTsksIdLike = myCellTsksIdLike;
		return this;
	}

	public CellMyTaskMainQuery myCellTsksIds(List<String> myCellTsksIds) {
		if (myCellTsksIds == null) {
			throw new RuntimeException("myCellTsksIds is empty ");
		}
		this.myCellTsksIds = myCellTsksIds;
		return this;
	}

	public CellMyTaskMainQuery fromTasksId(String fromTasksId) {
		if (fromTasksId == null) {
			throw new RuntimeException("fromTasksId is null");
		}
		this.fromTasksId = fromTasksId;
		return this;
	}

	public CellMyTaskMainQuery fromTasksIdLike(String fromTasksIdLike) {
		if (fromTasksIdLike == null) {
			throw new RuntimeException("fromTasksId is null");
		}
		this.fromTasksIdLike = fromTasksIdLike;
		return this;
	}

	public CellMyTaskMainQuery fromTasksIds(List<String> fromTasksIds) {
		if (fromTasksIds == null) {
			throw new RuntimeException("fromTasksIds is empty ");
		}
		this.fromTasksIds = fromTasksIds;
		return this;
	}

	public CellMyTaskMainQuery toTaskId(String toTaskId) {
		if (toTaskId == null) {
			throw new RuntimeException("toTaskId is null");
		}
		this.toTaskId = toTaskId;
		return this;
	}

	public CellMyTaskMainQuery toTaskIdLike(String toTaskIdLike) {
		if (toTaskIdLike == null) {
			throw new RuntimeException("toTaskId is null");
		}
		this.toTaskIdLike = toTaskIdLike;
		return this;
	}

	public CellMyTaskMainQuery toTaskIds(List<String> toTaskIds) {
		if (toTaskIds == null) {
			throw new RuntimeException("toTaskIds is empty ");
		}
		this.toTaskIds = toTaskIds;
		return this;
	}

	public CellMyTaskMainQuery intFinish(Integer intFinish) {
		if (intFinish == null) {
			throw new RuntimeException("intFinish is null");
		}
		this.intFinish = intFinish;
		return this;
	}

	public CellMyTaskMainQuery intFinishGreaterThanOrEqual(
			Integer intFinishGreaterThanOrEqual) {
		if (intFinishGreaterThanOrEqual == null) {
			throw new RuntimeException("intFinish is null");
		}
		this.intFinishGreaterThanOrEqual = intFinishGreaterThanOrEqual;
		return this;
	}

	public CellMyTaskMainQuery intFinishLessThanOrEqual(
			Integer intFinishLessThanOrEqual) {
		if (intFinishLessThanOrEqual == null) {
			throw new RuntimeException("intFinish is null");
		}
		this.intFinishLessThanOrEqual = intFinishLessThanOrEqual;
		return this;
	}

	public CellMyTaskMainQuery intFinishs(List<Integer> intFinishs) {
		if (intFinishs == null) {
			throw new RuntimeException("intFinishs is empty ");
		}
		this.intFinishs = intFinishs;
		return this;
	}

	public CellMyTaskMainQuery fileContent(String fileContent) {
		if (fileContent == null) {
			throw new RuntimeException("fileContent is null");
		}
		this.fileContent = fileContent;
		return this;
	}

	public CellMyTaskMainQuery fileContentLike(String fileContentLike) {
		if (fileContentLike == null) {
			throw new RuntimeException("fileContent is null");
		}
		this.fileContentLike = fileContentLike;
		return this;
	}

	public CellMyTaskMainQuery fileContents(List<String> fileContents) {
		if (fileContents == null) {
			throw new RuntimeException("fileContents is empty ");
		}
		this.fileContents = fileContents;
		return this;
	}

	public CellMyTaskMainQuery typeTableName(String typeTableName) {
		if (typeTableName == null) {
			throw new RuntimeException("typeTableName is null");
		}
		this.typeTableName = typeTableName;
		return this;
	}

	public CellMyTaskMainQuery typeTableNameLike(String typeTableNameLike) {
		if (typeTableNameLike == null) {
			throw new RuntimeException("typeTableName is null");
		}
		this.typeTableNameLike = typeTableNameLike;
		return this;
	}

	public CellMyTaskMainQuery typeTableNames(List<String> typeTableNames) {
		if (typeTableNames == null) {
			throw new RuntimeException("typeTableNames is empty ");
		}
		this.typeTableNames = typeTableNames;
		return this;
	}

	public CellMyTaskMainQuery typeId(String typeId) {
		if (typeId == null) {
			throw new RuntimeException("typeId is null");
		}
		this.typeId = typeId;
		return this;
	}

	public CellMyTaskMainQuery typeIdLike(String typeIdLike) {
		if (typeIdLike == null) {
			throw new RuntimeException("typeId is null");
		}
		this.typeIdLike = typeIdLike;
		return this;
	}

	public CellMyTaskMainQuery typeIds(List<String> typeIds) {
		if (typeIds == null) {
			throw new RuntimeException("typeIds is empty ");
		}
		this.typeIds = typeIds;
		return this;
	}

	public CellMyTaskMainQuery userId(String userId) {
		if (userId == null) {
			throw new RuntimeException("userId is null");
		}
		this.userId = userId;
		return this;
	}

	public CellMyTaskMainQuery userIdLike(String userIdLike) {
		if (userIdLike == null) {
			throw new RuntimeException("userId is null");
		}
		this.userIdLike = userIdLike;
		return this;
	}

	public CellMyTaskMainQuery userIds(List<String> userIds) {
		if (userIds == null) {
			throw new RuntimeException("userIds is empty ");
		}
		this.userIds = userIds;
		return this;
	}

	public CellMyTaskMainQuery netRoleId(String netRoleId) {
		if (netRoleId == null) {
			throw new RuntimeException("netRoleId is null");
		}
		this.netRoleId = netRoleId;
		return this;
	}

	public CellMyTaskMainQuery netRoleIdLike(String netRoleIdLike) {
		if (netRoleIdLike == null) {
			throw new RuntimeException("netRoleId is null");
		}
		this.netRoleIdLike = netRoleIdLike;
		return this;
	}

	public CellMyTaskMainQuery netRoleIds(List<String> netRoleIds) {
		if (netRoleIds == null) {
			throw new RuntimeException("netRoleIds is empty ");
		}
		this.netRoleIds = netRoleIds;
		return this;
	}

	public CellMyTaskMainQuery intIsFlow(Integer intIsFlow) {
		if (intIsFlow == null) {
			throw new RuntimeException("intIsFlow is null");
		}
		this.intIsFlow = intIsFlow;
		return this;
	}

	public CellMyTaskMainQuery intIsFlowGreaterThanOrEqual(
			Integer intIsFlowGreaterThanOrEqual) {
		if (intIsFlowGreaterThanOrEqual == null) {
			throw new RuntimeException("intIsFlow is null");
		}
		this.intIsFlowGreaterThanOrEqual = intIsFlowGreaterThanOrEqual;
		return this;
	}

	public CellMyTaskMainQuery intIsFlowLessThanOrEqual(
			Integer intIsFlowLessThanOrEqual) {
		if (intIsFlowLessThanOrEqual == null) {
			throw new RuntimeException("intIsFlow is null");
		}
		this.intIsFlowLessThanOrEqual = intIsFlowLessThanOrEqual;
		return this;
	}

	public CellMyTaskMainQuery intIsFlows(List<Integer> intIsFlows) {
		if (intIsFlows == null) {
			throw new RuntimeException("intIsFlows is empty ");
		}
		this.intIsFlows = intIsFlows;
		return this;
	}

	public CellMyTaskMainQuery intStop(Integer intStop) {
		if (intStop == null) {
			throw new RuntimeException("intStop is null");
		}
		this.intStop = intStop;
		return this;
	}

	public CellMyTaskMainQuery intStopGreaterThanOrEqual(
			Integer intStopGreaterThanOrEqual) {
		if (intStopGreaterThanOrEqual == null) {
			throw new RuntimeException("intStop is null");
		}
		this.intStopGreaterThanOrEqual = intStopGreaterThanOrEqual;
		return this;
	}

	public CellMyTaskMainQuery intStopLessThanOrEqual(
			Integer intStopLessThanOrEqual) {
		if (intStopLessThanOrEqual == null) {
			throw new RuntimeException("intStop is null");
		}
		this.intStopLessThanOrEqual = intStopLessThanOrEqual;
		return this;
	}

	public CellMyTaskMainQuery intStops(List<Integer> intStops) {
		if (intStops == null) {
			throw new RuntimeException("intStops is empty ");
		}
		this.intStops = intStops;
		return this;
	}

	public CellMyTaskMainQuery fileTypeIndex(Integer fileTypeIndex) {
		if (fileTypeIndex == null) {
			throw new RuntimeException("fileTypeIndex is null");
		}
		this.fileTypeIndex = fileTypeIndex;
		return this;
	}

	public CellMyTaskMainQuery fileTypeIndexGreaterThanOrEqual(
			Integer fileTypeIndexGreaterThanOrEqual) {
		if (fileTypeIndexGreaterThanOrEqual == null) {
			throw new RuntimeException("fileTypeIndex is null");
		}
		this.fileTypeIndexGreaterThanOrEqual = fileTypeIndexGreaterThanOrEqual;
		return this;
	}

	public CellMyTaskMainQuery fileTypeIndexLessThanOrEqual(
			Integer fileTypeIndexLessThanOrEqual) {
		if (fileTypeIndexLessThanOrEqual == null) {
			throw new RuntimeException("fileTypeIndex is null");
		}
		this.fileTypeIndexLessThanOrEqual = fileTypeIndexLessThanOrEqual;
		return this;
	}

	public CellMyTaskMainQuery fileTypeIndexs(List<Integer> fileTypeIndexs) {
		if (fileTypeIndexs == null) {
			throw new RuntimeException("fileTypeIndexs is empty ");
		}
		this.fileTypeIndexs = fileTypeIndexs;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("ctime".equals(sortColumn)) {
				orderBy = "E.CTIME" + a_x;
			}

			if ("indexId".equals(sortColumn)) {
				orderBy = "E.INDEX_ID" + a_x;
			}

			if ("taskId".equals(sortColumn)) {
				orderBy = "E.TASK_ID" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME" + a_x;
			}

			if ("projName".equals(sortColumn)) {
				orderBy = "E.PROJNAME" + a_x;
			}

			if ("listNo".equals(sortColumn)) {
				orderBy = "E.LISTNO" + a_x;
			}

			if ("typeIndexId".equals(sortColumn)) {
				orderBy = "E.TYPE_INDEX_ID" + a_x;
			}

			if ("flagInt".equals(sortColumn)) {
				orderBy = "E.FLAGINT" + a_x;
			}

			if ("myCellTsksId".equals(sortColumn)) {
				orderBy = "E.MYCELL_TSKS_ID" + a_x;
			}

			if ("fromTasksId".equals(sortColumn)) {
				orderBy = "E.FROMTASKSID" + a_x;
			}

			if ("toTaskId".equals(sortColumn)) {
				orderBy = "E.TOTASKID" + a_x;
			}

			if ("intFinish".equals(sortColumn)) {
				orderBy = "E.INTFINISH" + a_x;
			}

			if ("fileContent".equals(sortColumn)) {
				orderBy = "E.FILE_CONTENT" + a_x;
			}

			if ("typeTableName".equals(sortColumn)) {
				orderBy = "E.TYPE_TABLENAME" + a_x;
			}

			if ("typeId".equals(sortColumn)) {
				orderBy = "E.TYPE_ID" + a_x;
			}

			if ("userId".equals(sortColumn)) {
				orderBy = "E.USERID" + a_x;
			}

			if ("netRoleId".equals(sortColumn)) {
				orderBy = "E.NETROLEID" + a_x;
			}

			if ("intIsFlow".equals(sortColumn)) {
				orderBy = "E.INTISFLOW" + a_x;
			}

			if ("intStop".equals(sortColumn)) {
				orderBy = "E.INTSTOP" + a_x;
			}

			if ("fileTypeIndex".equals(sortColumn)) {
				orderBy = "E.FILETYPE_INDEX" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("ctime", "CTIME");
		addColumn("indexId", "INDEX_ID");
		addColumn("taskId", "TASK_ID");
		addColumn("name", "NAME");
		addColumn("projName", "PROJNAME");
		addColumn("listNo", "LISTNO");
		addColumn("typeIndexId", "TYPE_INDEX_ID");
		addColumn("flagInt", "FLAGINT");
		addColumn("myCellTsksId", "MYCELL_TSKS_ID");
		addColumn("fromTasksId", "FROMTASKSID");
		addColumn("toTaskId", "TOTASKID");
		addColumn("intFinish", "INTFINISH");
		addColumn("fileContent", "FILE_CONTENT");
		addColumn("typeTableName", "TYPE_TABLENAME");
		addColumn("typeId", "TYPE_ID");
		addColumn("userId", "USERID");
		addColumn("netRoleId", "NETROLEID");
		addColumn("intIsFlow", "INTISFLOW");
		addColumn("intStop", "INTSTOP");
		addColumn("fileTypeIndex", "FILETYPE_INDEX");
	}

}