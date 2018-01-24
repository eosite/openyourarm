package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DotUseQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> fileIDs;
	protected Collection<String> appActorIds;
	protected Integer indexId;
	protected Integer indexIdGreaterThanOrEqual;
	protected Integer indexIdLessThanOrEqual;
	protected List<Integer> indexIds;
	protected Integer projId;
	protected Integer projIdGreaterThanOrEqual;
	protected Integer projIdLessThanOrEqual;
	protected List<Integer> projIds;
	protected Integer pid;
	protected Integer pidGreaterThanOrEqual;
	protected Integer pidLessThanOrEqual;
	protected List<Integer> pids;
	protected String dotId;
	protected String dotIdLike;
	protected List<String> dotIds;
	protected String num;
	protected String numLike;
	protected List<String> nums;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String cman;
	protected String cmanLike;
	protected List<String> cmans;
	protected Date ctimeGreaterThanOrEqual;
	protected Date ctimeLessThanOrEqual;
	protected String fileName;
	protected String fileNameLike;
	protected List<String> fileNames;
	protected String fileContent;
	protected String fileContentLike;
	protected List<String> fileContents;
	protected Integer fileSize;
	protected Integer fileSizeGreaterThanOrEqual;
	protected Integer fileSizeLessThanOrEqual;
	protected List<Integer> fileSizes;
	protected String vision;
	protected String visionLike;
	protected List<String> visions;
	protected String saveTime;
	protected String saveTimeLike;
	protected List<String> saveTimes;
	protected String remark;
	protected String remarkLike;
	protected List<String> remarks;
	protected Integer dwid;
	protected Integer dwidGreaterThanOrEqual;
	protected Integer dwidLessThanOrEqual;
	protected List<Integer> dwids;
	protected Integer fbid;
	protected Integer fbidGreaterThanOrEqual;
	protected Integer fbidLessThanOrEqual;
	protected List<Integer> fbids;
	protected Integer fxid;
	protected Integer fxidGreaterThanOrEqual;
	protected Integer fxidLessThanOrEqual;
	protected List<Integer> fxids;
	protected String jid;
	protected String jidLike;
	protected List<String> jids;
	protected String flid;
	protected String flidLike;
	protected List<String> flids;
	protected String topNode;
	protected String topNodeLike;
	protected List<String> topNodes;
	protected String topId;
	protected String topIdLike;
	protected List<String> topIds;
	protected Integer type;
	protected Integer typeGreaterThanOrEqual;
	protected Integer typeLessThanOrEqual;
	protected List<Integer> types;
	protected String fname;
	protected String fnameLike;
	protected List<String> fnames;
	protected String isInk;
	protected String isInkLike;
	protected List<String> isInks;
	protected String oldId;
	protected String oldIdLike;
	protected List<String> oldIds;
	protected String taskId;
	protected String taskIdLike;
	protected List<String> taskIds;
	protected Integer isCheck;
	protected Integer isCheckGreaterThanOrEqual;
	protected Integer isCheckLessThanOrEqual;
	protected List<Integer> isChecks;

	public DotUseQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
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

	public Integer getProjId() {
		return projId;
	}

	public Integer getProjIdGreaterThanOrEqual() {
		return projIdGreaterThanOrEqual;
	}

	public Integer getProjIdLessThanOrEqual() {
		return projIdLessThanOrEqual;
	}

	public List<Integer> getProjIds() {
		return projIds;
	}

	public Integer getPid() {
		return pid;
	}

	public Integer getPidGreaterThanOrEqual() {
		return pidGreaterThanOrEqual;
	}

	public Integer getPidLessThanOrEqual() {
		return pidLessThanOrEqual;
	}

	public List<Integer> getPids() {
		return pids;
	}

	public String getDotId() {
		return dotId;
	}

	public String getDotIdLike() {
		if (dotIdLike != null && dotIdLike.trim().length() > 0) {
			if (!dotIdLike.startsWith("%")) {
				dotIdLike = "%" + dotIdLike;
			}
			if (!dotIdLike.endsWith("%")) {
				dotIdLike = dotIdLike + "%";
			}
		}
		return dotIdLike;
	}

	public List<String> getDotIds() {
		return dotIds;
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

	public String getCman() {
		return cman;
	}

	public String getCmanLike() {
		if (cmanLike != null && cmanLike.trim().length() > 0) {
			if (!cmanLike.startsWith("%")) {
				cmanLike = "%" + cmanLike;
			}
			if (!cmanLike.endsWith("%")) {
				cmanLike = cmanLike + "%";
			}
		}
		return cmanLike;
	}

	public List<String> getCmans() {
		return cmans;
	}

	public Date getCtimeGreaterThanOrEqual() {
		return ctimeGreaterThanOrEqual;
	}

	public Date getCtimeLessThanOrEqual() {
		return ctimeLessThanOrEqual;
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

	public Integer getFileSize() {
		return fileSize;
	}

	public Integer getFileSizeGreaterThanOrEqual() {
		return fileSizeGreaterThanOrEqual;
	}

	public Integer getFileSizeLessThanOrEqual() {
		return fileSizeLessThanOrEqual;
	}

	public List<Integer> getFileSizes() {
		return fileSizes;
	}

	public String getVision() {
		return vision;
	}

	public String getVisionLike() {
		if (visionLike != null && visionLike.trim().length() > 0) {
			if (!visionLike.startsWith("%")) {
				visionLike = "%" + visionLike;
			}
			if (!visionLike.endsWith("%")) {
				visionLike = visionLike + "%";
			}
		}
		return visionLike;
	}

	public List<String> getVisions() {
		return visions;
	}

	public String getSaveTime() {
		return saveTime;
	}

	public String getSaveTimeLike() {
		if (saveTimeLike != null && saveTimeLike.trim().length() > 0) {
			if (!saveTimeLike.startsWith("%")) {
				saveTimeLike = "%" + saveTimeLike;
			}
			if (!saveTimeLike.endsWith("%")) {
				saveTimeLike = saveTimeLike + "%";
			}
		}
		return saveTimeLike;
	}

	public List<String> getSaveTimes() {
		return saveTimes;
	}

	public String getRemark() {
		return remark;
	}

	public String getRemarkLike() {
		if (remarkLike != null && remarkLike.trim().length() > 0) {
			if (!remarkLike.startsWith("%")) {
				remarkLike = "%" + remarkLike;
			}
			if (!remarkLike.endsWith("%")) {
				remarkLike = remarkLike + "%";
			}
		}
		return remarkLike;
	}

	public List<String> getRemarks() {
		return remarks;
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

	public Integer getType() {
		return type;
	}

	public Integer getTypeGreaterThanOrEqual() {
		return typeGreaterThanOrEqual;
	}

	public Integer getTypeLessThanOrEqual() {
		return typeLessThanOrEqual;
	}

	public List<Integer> getTypes() {
		return types;
	}

	public String getFname() {
		return fname;
	}

	public String getFnameLike() {
		if (fnameLike != null && fnameLike.trim().length() > 0) {
			if (!fnameLike.startsWith("%")) {
				fnameLike = "%" + fnameLike;
			}
			if (!fnameLike.endsWith("%")) {
				fnameLike = fnameLike + "%";
			}
		}
		return fnameLike;
	}

	public List<String> getFnames() {
		return fnames;
	}

	public String getIsInk() {
		return isInk;
	}

	public String getIsInkLike() {
		if (isInkLike != null && isInkLike.trim().length() > 0) {
			if (!isInkLike.startsWith("%")) {
				isInkLike = "%" + isInkLike;
			}
			if (!isInkLike.endsWith("%")) {
				isInkLike = isInkLike + "%";
			}
		}
		return isInkLike;
	}

	public List<String> getIsInks() {
		return isInks;
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

	public Integer getIsCheck() {
		return isCheck;
	}

	public Integer getIsCheckGreaterThanOrEqual() {
		return isCheckGreaterThanOrEqual;
	}

	public Integer getIsCheckLessThanOrEqual() {
		return isCheckLessThanOrEqual;
	}

	public List<Integer> getIsChecks() {
		return isChecks;
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

	public void setProjId(Integer projId) {
		this.projId = projId;
	}

	public void setProjIdGreaterThanOrEqual(Integer projIdGreaterThanOrEqual) {
		this.projIdGreaterThanOrEqual = projIdGreaterThanOrEqual;
	}

	public void setProjIdLessThanOrEqual(Integer projIdLessThanOrEqual) {
		this.projIdLessThanOrEqual = projIdLessThanOrEqual;
	}

	public void setProjIds(List<Integer> projIds) {
		this.projIds = projIds;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public void setPidGreaterThanOrEqual(Integer pidGreaterThanOrEqual) {
		this.pidGreaterThanOrEqual = pidGreaterThanOrEqual;
	}

	public void setPidLessThanOrEqual(Integer pidLessThanOrEqual) {
		this.pidLessThanOrEqual = pidLessThanOrEqual;
	}

	public void setPids(List<Integer> pids) {
		this.pids = pids;
	}

	public void setDotId(String dotId) {
		this.dotId = dotId;
	}

	public void setDotIdLike(String dotIdLike) {
		this.dotIdLike = dotIdLike;
	}

	public void setDotIds(List<String> dotIds) {
		this.dotIds = dotIds;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public void setCman(String cman) {
		this.cman = cman;
	}

	public void setCmanLike(String cmanLike) {
		this.cmanLike = cmanLike;
	}

	public void setCmans(List<String> cmans) {
		this.cmans = cmans;
	}

	public void setCtimeGreaterThanOrEqual(Date ctimeGreaterThanOrEqual) {
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
	}

	public void setCtimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
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

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public void setFileContentLike(String fileContentLike) {
		this.fileContentLike = fileContentLike;
	}

	public void setFileContents(List<String> fileContents) {
		this.fileContents = fileContents;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public void setFileSizeGreaterThanOrEqual(Integer fileSizeGreaterThanOrEqual) {
		this.fileSizeGreaterThanOrEqual = fileSizeGreaterThanOrEqual;
	}

	public void setFileSizeLessThanOrEqual(Integer fileSizeLessThanOrEqual) {
		this.fileSizeLessThanOrEqual = fileSizeLessThanOrEqual;
	}

	public void setFileSizes(List<Integer> fileSizes) {
		this.fileSizes = fileSizes;
	}

	public void setVision(String vision) {
		this.vision = vision;
	}

	public void setVisionLike(String visionLike) {
		this.visionLike = visionLike;
	}

	public void setVisions(List<String> visions) {
		this.visions = visions;
	}

	public void setSaveTime(String saveTime) {
		this.saveTime = saveTime;
	}

	public void setSaveTimeLike(String saveTimeLike) {
		this.saveTimeLike = saveTimeLike;
	}

	public void setSaveTimes(List<String> saveTimes) {
		this.saveTimes = saveTimes;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setRemarkLike(String remarkLike) {
		this.remarkLike = remarkLike;
	}

	public void setRemarks(List<String> remarks) {
		this.remarks = remarks;
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

	public void setTopId(String topId) {
		this.topId = topId;
	}

	public void setTopIdLike(String topIdLike) {
		this.topIdLike = topIdLike;
	}

	public void setTopIds(List<String> topIds) {
		this.topIds = topIds;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setTypeGreaterThanOrEqual(Integer typeGreaterThanOrEqual) {
		this.typeGreaterThanOrEqual = typeGreaterThanOrEqual;
	}

	public void setTypeLessThanOrEqual(Integer typeLessThanOrEqual) {
		this.typeLessThanOrEqual = typeLessThanOrEqual;
	}

	public void setTypes(List<Integer> types) {
		this.types = types;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public void setFnameLike(String fnameLike) {
		this.fnameLike = fnameLike;
	}

	public void setFnames(List<String> fnames) {
		this.fnames = fnames;
	}

	public void setIsInk(String isInk) {
		this.isInk = isInk;
	}

	public void setIsInkLike(String isInkLike) {
		this.isInkLike = isInkLike;
	}

	public void setIsInks(List<String> isInks) {
		this.isInks = isInks;
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

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setTaskIdLike(String taskIdLike) {
		this.taskIdLike = taskIdLike;
	}

	public void setTaskIds(List<String> taskIds) {
		this.taskIds = taskIds;
	}

	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
	}

	public void setIsCheckGreaterThanOrEqual(Integer isCheckGreaterThanOrEqual) {
		this.isCheckGreaterThanOrEqual = isCheckGreaterThanOrEqual;
	}

	public void setIsCheckLessThanOrEqual(Integer isCheckLessThanOrEqual) {
		this.isCheckLessThanOrEqual = isCheckLessThanOrEqual;
	}

	public void setIsChecks(List<Integer> isChecks) {
		this.isChecks = isChecks;
	}

	public DotUseQuery indexId(Integer indexId) {
		if (indexId == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexId = indexId;
		return this;
	}

	public DotUseQuery indexIdGreaterThanOrEqual(
			Integer indexIdGreaterThanOrEqual) {
		if (indexIdGreaterThanOrEqual == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexIdGreaterThanOrEqual = indexIdGreaterThanOrEqual;
		return this;
	}

	public DotUseQuery indexIdLessThanOrEqual(Integer indexIdLessThanOrEqual) {
		if (indexIdLessThanOrEqual == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexIdLessThanOrEqual = indexIdLessThanOrEqual;
		return this;
	}

	public DotUseQuery indexIds(List<Integer> indexIds) {
		if (indexIds == null) {
			throw new RuntimeException("indexIds is empty ");
		}
		this.indexIds = indexIds;
		return this;
	}

	public DotUseQuery projId(Integer projId) {
		if (projId == null) {
			throw new RuntimeException("projId is null");
		}
		this.projId = projId;
		return this;
	}

	public DotUseQuery projIdGreaterThanOrEqual(Integer projIdGreaterThanOrEqual) {
		if (projIdGreaterThanOrEqual == null) {
			throw new RuntimeException("projId is null");
		}
		this.projIdGreaterThanOrEqual = projIdGreaterThanOrEqual;
		return this;
	}

	public DotUseQuery projIdLessThanOrEqual(Integer projIdLessThanOrEqual) {
		if (projIdLessThanOrEqual == null) {
			throw new RuntimeException("projId is null");
		}
		this.projIdLessThanOrEqual = projIdLessThanOrEqual;
		return this;
	}

	public DotUseQuery projIds(List<Integer> projIds) {
		if (projIds == null) {
			throw new RuntimeException("projIds is empty ");
		}
		this.projIds = projIds;
		return this;
	}

	public DotUseQuery pid(Integer pid) {
		if (pid == null) {
			throw new RuntimeException("pid is null");
		}
		this.pid = pid;
		return this;
	}

	public DotUseQuery pidGreaterThanOrEqual(Integer pidGreaterThanOrEqual) {
		if (pidGreaterThanOrEqual == null) {
			throw new RuntimeException("pid is null");
		}
		this.pidGreaterThanOrEqual = pidGreaterThanOrEqual;
		return this;
	}

	public DotUseQuery pidLessThanOrEqual(Integer pidLessThanOrEqual) {
		if (pidLessThanOrEqual == null) {
			throw new RuntimeException("pid is null");
		}
		this.pidLessThanOrEqual = pidLessThanOrEqual;
		return this;
	}

	public DotUseQuery pids(List<Integer> pids) {
		if (pids == null) {
			throw new RuntimeException("pids is empty ");
		}
		this.pids = pids;
		return this;
	}

	public DotUseQuery dotId(String dotId) {
		if (dotId == null) {
			throw new RuntimeException("dotId is null");
		}
		this.dotId = dotId;
		return this;
	}

	public DotUseQuery dotIdLike(String dotIdLike) {
		if (dotIdLike == null) {
			throw new RuntimeException("dotId is null");
		}
		this.dotIdLike = dotIdLike;
		return this;
	}

	public DotUseQuery dotIds(List<String> dotIds) {
		if (dotIds == null) {
			throw new RuntimeException("dotIds is empty ");
		}
		this.dotIds = dotIds;
		return this;
	}

	public DotUseQuery num(String num) {
		if (num == null) {
			throw new RuntimeException("num is null");
		}
		this.num = num;
		return this;
	}

	public DotUseQuery numLike(String numLike) {
		if (numLike == null) {
			throw new RuntimeException("num is null");
		}
		this.numLike = numLike;
		return this;
	}

	public DotUseQuery nums(List<String> nums) {
		if (nums == null) {
			throw new RuntimeException("nums is empty ");
		}
		this.nums = nums;
		return this;
	}

	public DotUseQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public DotUseQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public DotUseQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public DotUseQuery cman(String cman) {
		if (cman == null) {
			throw new RuntimeException("cman is null");
		}
		this.cman = cman;
		return this;
	}

	public DotUseQuery cmanLike(String cmanLike) {
		if (cmanLike == null) {
			throw new RuntimeException("cman is null");
		}
		this.cmanLike = cmanLike;
		return this;
	}

	public DotUseQuery cmans(List<String> cmans) {
		if (cmans == null) {
			throw new RuntimeException("cmans is empty ");
		}
		this.cmans = cmans;
		return this;
	}

	public DotUseQuery ctimeGreaterThanOrEqual(Date ctimeGreaterThanOrEqual) {
		if (ctimeGreaterThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
		return this;
	}

	public DotUseQuery ctimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		if (ctimeLessThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
		return this;
	}

	public DotUseQuery fileName(String fileName) {
		if (fileName == null) {
			throw new RuntimeException("fileName is null");
		}
		this.fileName = fileName;
		return this;
	}

	public DotUseQuery fileNameLike(String fileNameLike) {
		if (fileNameLike == null) {
			throw new RuntimeException("fileName is null");
		}
		this.fileNameLike = fileNameLike;
		return this;
	}

	public DotUseQuery fileNames(List<String> fileNames) {
		if (fileNames == null) {
			throw new RuntimeException("fileNames is empty ");
		}
		this.fileNames = fileNames;
		return this;
	}

	public DotUseQuery fileContent(String fileContent) {
		if (fileContent == null) {
			throw new RuntimeException("fileContent is null");
		}
		this.fileContent = fileContent;
		return this;
	}

	public DotUseQuery fileContentLike(String fileContentLike) {
		if (fileContentLike == null) {
			throw new RuntimeException("fileContent is null");
		}
		this.fileContentLike = fileContentLike;
		return this;
	}

	public DotUseQuery fileContents(List<String> fileContents) {
		if (fileContents == null) {
			throw new RuntimeException("fileContents is empty ");
		}
		this.fileContents = fileContents;
		return this;
	}

	public DotUseQuery fileSize(Integer fileSize) {
		if (fileSize == null) {
			throw new RuntimeException("fileSize is null");
		}
		this.fileSize = fileSize;
		return this;
	}

	public DotUseQuery fileSizeGreaterThanOrEqual(
			Integer fileSizeGreaterThanOrEqual) {
		if (fileSizeGreaterThanOrEqual == null) {
			throw new RuntimeException("fileSize is null");
		}
		this.fileSizeGreaterThanOrEqual = fileSizeGreaterThanOrEqual;
		return this;
	}

	public DotUseQuery fileSizeLessThanOrEqual(Integer fileSizeLessThanOrEqual) {
		if (fileSizeLessThanOrEqual == null) {
			throw new RuntimeException("fileSize is null");
		}
		this.fileSizeLessThanOrEqual = fileSizeLessThanOrEqual;
		return this;
	}

	public DotUseQuery fileSizes(List<Integer> fileSizes) {
		if (fileSizes == null) {
			throw new RuntimeException("fileSizes is empty ");
		}
		this.fileSizes = fileSizes;
		return this;
	}

	public DotUseQuery vision(String vision) {
		if (vision == null) {
			throw new RuntimeException("vision is null");
		}
		this.vision = vision;
		return this;
	}

	public DotUseQuery visionLike(String visionLike) {
		if (visionLike == null) {
			throw new RuntimeException("vision is null");
		}
		this.visionLike = visionLike;
		return this;
	}

	public DotUseQuery visions(List<String> visions) {
		if (visions == null) {
			throw new RuntimeException("visions is empty ");
		}
		this.visions = visions;
		return this;
	}

	public DotUseQuery saveTime(String saveTime) {
		if (saveTime == null) {
			throw new RuntimeException("saveTime is null");
		}
		this.saveTime = saveTime;
		return this;
	}

	public DotUseQuery saveTimeLike(String saveTimeLike) {
		if (saveTimeLike == null) {
			throw new RuntimeException("saveTime is null");
		}
		this.saveTimeLike = saveTimeLike;
		return this;
	}

	public DotUseQuery saveTimes(List<String> saveTimes) {
		if (saveTimes == null) {
			throw new RuntimeException("saveTimes is empty ");
		}
		this.saveTimes = saveTimes;
		return this;
	}

	public DotUseQuery remark(String remark) {
		if (remark == null) {
			throw new RuntimeException("remark is null");
		}
		this.remark = remark;
		return this;
	}

	public DotUseQuery remarkLike(String remarkLike) {
		if (remarkLike == null) {
			throw new RuntimeException("remark is null");
		}
		this.remarkLike = remarkLike;
		return this;
	}

	public DotUseQuery remarks(List<String> remarks) {
		if (remarks == null) {
			throw new RuntimeException("remarks is empty ");
		}
		this.remarks = remarks;
		return this;
	}

	public DotUseQuery dwid(Integer dwid) {
		if (dwid == null) {
			throw new RuntimeException("dwid is null");
		}
		this.dwid = dwid;
		return this;
	}

	public DotUseQuery dwidGreaterThanOrEqual(Integer dwidGreaterThanOrEqual) {
		if (dwidGreaterThanOrEqual == null) {
			throw new RuntimeException("dwid is null");
		}
		this.dwidGreaterThanOrEqual = dwidGreaterThanOrEqual;
		return this;
	}

	public DotUseQuery dwidLessThanOrEqual(Integer dwidLessThanOrEqual) {
		if (dwidLessThanOrEqual == null) {
			throw new RuntimeException("dwid is null");
		}
		this.dwidLessThanOrEqual = dwidLessThanOrEqual;
		return this;
	}

	public DotUseQuery dwids(List<Integer> dwids) {
		if (dwids == null) {
			throw new RuntimeException("dwids is empty ");
		}
		this.dwids = dwids;
		return this;
	}

	public DotUseQuery fbid(Integer fbid) {
		if (fbid == null) {
			throw new RuntimeException("fbid is null");
		}
		this.fbid = fbid;
		return this;
	}

	public DotUseQuery fbidGreaterThanOrEqual(Integer fbidGreaterThanOrEqual) {
		if (fbidGreaterThanOrEqual == null) {
			throw new RuntimeException("fbid is null");
		}
		this.fbidGreaterThanOrEqual = fbidGreaterThanOrEqual;
		return this;
	}

	public DotUseQuery fbidLessThanOrEqual(Integer fbidLessThanOrEqual) {
		if (fbidLessThanOrEqual == null) {
			throw new RuntimeException("fbid is null");
		}
		this.fbidLessThanOrEqual = fbidLessThanOrEqual;
		return this;
	}

	public DotUseQuery fbids(List<Integer> fbids) {
		if (fbids == null) {
			throw new RuntimeException("fbids is empty ");
		}
		this.fbids = fbids;
		return this;
	}

	public DotUseQuery fxid(Integer fxid) {
		if (fxid == null) {
			throw new RuntimeException("fxid is null");
		}
		this.fxid = fxid;
		return this;
	}

	public DotUseQuery fxidGreaterThanOrEqual(Integer fxidGreaterThanOrEqual) {
		if (fxidGreaterThanOrEqual == null) {
			throw new RuntimeException("fxid is null");
		}
		this.fxidGreaterThanOrEqual = fxidGreaterThanOrEqual;
		return this;
	}

	public DotUseQuery fxidLessThanOrEqual(Integer fxidLessThanOrEqual) {
		if (fxidLessThanOrEqual == null) {
			throw new RuntimeException("fxid is null");
		}
		this.fxidLessThanOrEqual = fxidLessThanOrEqual;
		return this;
	}

	public DotUseQuery fxids(List<Integer> fxids) {
		if (fxids == null) {
			throw new RuntimeException("fxids is empty ");
		}
		this.fxids = fxids;
		return this;
	}

	public DotUseQuery jid(String jid) {
		if (jid == null) {
			throw new RuntimeException("jid is null");
		}
		this.jid = jid;
		return this;
	}

	public DotUseQuery jidLike(String jidLike) {
		if (jidLike == null) {
			throw new RuntimeException("jid is null");
		}
		this.jidLike = jidLike;
		return this;
	}

	public DotUseQuery jids(List<String> jids) {
		if (jids == null) {
			throw new RuntimeException("jids is empty ");
		}
		this.jids = jids;
		return this;
	}

	public DotUseQuery flid(String flid) {
		if (flid == null) {
			throw new RuntimeException("flid is null");
		}
		this.flid = flid;
		return this;
	}

	public DotUseQuery flidLike(String flidLike) {
		if (flidLike == null) {
			throw new RuntimeException("flid is null");
		}
		this.flidLike = flidLike;
		return this;
	}

	public DotUseQuery flids(List<String> flids) {
		if (flids == null) {
			throw new RuntimeException("flids is empty ");
		}
		this.flids = flids;
		return this;
	}

	public DotUseQuery topNode(String topNode) {
		if (topNode == null) {
			throw new RuntimeException("topNode is null");
		}
		this.topNode = topNode;
		return this;
	}

	public DotUseQuery topNodeLike(String topNodeLike) {
		if (topNodeLike == null) {
			throw new RuntimeException("topNode is null");
		}
		this.topNodeLike = topNodeLike;
		return this;
	}

	public DotUseQuery topNodes(List<String> topNodes) {
		if (topNodes == null) {
			throw new RuntimeException("topNodes is empty ");
		}
		this.topNodes = topNodes;
		return this;
	}

	public DotUseQuery topId(String topId) {
		if (topId == null) {
			throw new RuntimeException("topId is null");
		}
		this.topId = topId;
		return this;
	}

	public DotUseQuery topIdLike(String topIdLike) {
		if (topIdLike == null) {
			throw new RuntimeException("topId is null");
		}
		this.topIdLike = topIdLike;
		return this;
	}

	public DotUseQuery topIds(List<String> topIds) {
		if (topIds == null) {
			throw new RuntimeException("topIds is empty ");
		}
		this.topIds = topIds;
		return this;
	}

	public DotUseQuery type(Integer type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public DotUseQuery typeGreaterThanOrEqual(Integer typeGreaterThanOrEqual) {
		if (typeGreaterThanOrEqual == null) {
			throw new RuntimeException("type is null");
		}
		this.typeGreaterThanOrEqual = typeGreaterThanOrEqual;
		return this;
	}

	public DotUseQuery typeLessThanOrEqual(Integer typeLessThanOrEqual) {
		if (typeLessThanOrEqual == null) {
			throw new RuntimeException("type is null");
		}
		this.typeLessThanOrEqual = typeLessThanOrEqual;
		return this;
	}

	public DotUseQuery types(List<Integer> types) {
		if (types == null) {
			throw new RuntimeException("types is empty ");
		}
		this.types = types;
		return this;
	}

	public DotUseQuery fname(String fname) {
		if (fname == null) {
			throw new RuntimeException("fname is null");
		}
		this.fname = fname;
		return this;
	}

	public DotUseQuery fnameLike(String fnameLike) {
		if (fnameLike == null) {
			throw new RuntimeException("fname is null");
		}
		this.fnameLike = fnameLike;
		return this;
	}

	public DotUseQuery fnames(List<String> fnames) {
		if (fnames == null) {
			throw new RuntimeException("fnames is empty ");
		}
		this.fnames = fnames;
		return this;
	}

	public DotUseQuery isInk(String isInk) {
		if (isInk == null) {
			throw new RuntimeException("isInk is null");
		}
		this.isInk = isInk;
		return this;
	}

	public DotUseQuery isInkLike(String isInkLike) {
		if (isInkLike == null) {
			throw new RuntimeException("isInk is null");
		}
		this.isInkLike = isInkLike;
		return this;
	}

	public DotUseQuery isInks(List<String> isInks) {
		if (isInks == null) {
			throw new RuntimeException("isInks is empty ");
		}
		this.isInks = isInks;
		return this;
	}

	public DotUseQuery oldId(String oldId) {
		if (oldId == null) {
			throw new RuntimeException("oldId is null");
		}
		this.oldId = oldId;
		return this;
	}

	public DotUseQuery oldIdLike(String oldIdLike) {
		if (oldIdLike == null) {
			throw new RuntimeException("oldId is null");
		}
		this.oldIdLike = oldIdLike;
		return this;
	}

	public DotUseQuery oldIds(List<String> oldIds) {
		if (oldIds == null) {
			throw new RuntimeException("oldIds is empty ");
		}
		this.oldIds = oldIds;
		return this;
	}

	public DotUseQuery taskId(String taskId) {
		if (taskId == null) {
			throw new RuntimeException("taskId is null");
		}
		this.taskId = taskId;
		return this;
	}

	public DotUseQuery taskIdLike(String taskIdLike) {
		if (taskIdLike == null) {
			throw new RuntimeException("taskId is null");
		}
		this.taskIdLike = taskIdLike;
		return this;
	}

	public DotUseQuery taskIds(List<String> taskIds) {
		if (taskIds == null) {
			throw new RuntimeException("taskIds is empty ");
		}
		this.taskIds = taskIds;
		return this;
	}

	public DotUseQuery isCheck(Integer isCheck) {
		if (isCheck == null) {
			throw new RuntimeException("isCheck is null");
		}
		this.isCheck = isCheck;
		return this;
	}

	public DotUseQuery isCheckGreaterThanOrEqual(
			Integer isCheckGreaterThanOrEqual) {
		if (isCheckGreaterThanOrEqual == null) {
			throw new RuntimeException("isCheck is null");
		}
		this.isCheckGreaterThanOrEqual = isCheckGreaterThanOrEqual;
		return this;
	}

	public DotUseQuery isCheckLessThanOrEqual(Integer isCheckLessThanOrEqual) {
		if (isCheckLessThanOrEqual == null) {
			throw new RuntimeException("isCheck is null");
		}
		this.isCheckLessThanOrEqual = isCheckLessThanOrEqual;
		return this;
	}

	public DotUseQuery isChecks(List<Integer> isChecks) {
		if (isChecks == null) {
			throw new RuntimeException("isChecks is empty ");
		}
		this.isChecks = isChecks;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("indexId".equals(sortColumn)) {
				orderBy = "E.INDEX_ID" + a_x;
			}

			if ("projId".equals(sortColumn)) {
				orderBy = "E.PROJID" + a_x;
			}

			if ("pid".equals(sortColumn)) {
				orderBy = "E.PID" + a_x;
			}

			if ("dotId".equals(sortColumn)) {
				orderBy = "E.DOTID" + a_x;
			}

			if ("num".equals(sortColumn)) {
				orderBy = "E.NUM" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME" + a_x;
			}

			if ("cman".equals(sortColumn)) {
				orderBy = "E.CMAN" + a_x;
			}

			if ("ctime".equals(sortColumn)) {
				orderBy = "E.CTIME" + a_x;
			}

			if ("fileName".equals(sortColumn)) {
				orderBy = "E.FILE_NAME" + a_x;
			}

			if ("fileContent".equals(sortColumn)) {
				orderBy = "E.FILE_CONTENT" + a_x;
			}

			if ("fileSize".equals(sortColumn)) {
				orderBy = "E.FILESIZE" + a_x;
			}

			if ("vision".equals(sortColumn)) {
				orderBy = "E.VISION" + a_x;
			}

			if ("saveTime".equals(sortColumn)) {
				orderBy = "E.SAVETIME" + a_x;
			}

			if ("remark".equals(sortColumn)) {
				orderBy = "E.REMARK" + a_x;
			}

			if ("dwid".equals(sortColumn)) {
				orderBy = "E.DWID" + a_x;
			}

			if ("fbid".equals(sortColumn)) {
				orderBy = "E.FBID" + a_x;
			}

			if ("fxid".equals(sortColumn)) {
				orderBy = "E.FXID" + a_x;
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

			if ("topId".equals(sortColumn)) {
				orderBy = "E.TOPID" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE" + a_x;
			}

			if ("fname".equals(sortColumn)) {
				orderBy = "E.FNAME" + a_x;
			}

			if ("isInk".equals(sortColumn)) {
				orderBy = "E.ISINK" + a_x;
			}

			if ("oldId".equals(sortColumn)) {
				orderBy = "E.OLD_ID" + a_x;
			}

			if ("taskId".equals(sortColumn)) {
				orderBy = "E.TASK_ID" + a_x;
			}

			if ("isCheck".equals(sortColumn)) {
				orderBy = "E.ISCHECK" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("fileID", "FILEID");
		addColumn("indexId", "INDEX_ID");
		addColumn("projId", "PROJID");
		addColumn("pid", "PID");
		addColumn("dotId", "DOTID");
		addColumn("num", "NUM");
		addColumn("name", "NAME");
		addColumn("cman", "CMAN");
		addColumn("ctime", "CTIME");
		addColumn("fileName", "FILE_NAME");
		addColumn("fileContent", "FILE_CONTENT");
		addColumn("fileSize", "FILESIZE");
		addColumn("vision", "VISION");
		addColumn("saveTime", "SAVETIME");
		addColumn("remark", "REMARK");
		addColumn("dwid", "DWID");
		addColumn("fbid", "FBID");
		addColumn("fxid", "FXID");
		addColumn("jid", "JID");
		addColumn("flid", "FLID");
		addColumn("topNode", "TOPNODE");
		addColumn("topId", "TOPID");
		addColumn("type", "TYPE");
		addColumn("fname", "FNAME");
		addColumn("isInk", "ISINK");
		addColumn("oldId", "OLD_ID");
		addColumn("taskId", "TASK_ID");
		addColumn("isCheck", "ISCHECK");
	}

}