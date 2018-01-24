package com.glaf.base.modules.sys.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FiledotUseQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> fileIDs;
	protected Collection<String> appActorIds;
	protected Integer indexId;

	protected List<Integer> indexIds;
	protected Integer projid;

	protected Integer pid;

	protected String dotid;

	protected String name;
	protected String nameLike;

	protected String fileName;
	protected String fileNameLike;

	protected String topnode;
	protected String topnodeLike;

	protected String topid;
	protected String topidLike;

	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;

	protected String taskId;

	protected Integer type;

	public FiledotUseQuery() {

	}

	public FiledotUseQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public FiledotUseQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public FiledotUseQuery dotid(String dotid) {
		if (dotid == null) {
			throw new RuntimeException("dotid is null");
		}
		this.dotid = dotid;
		return this;
	}

	public FiledotUseQuery fileName(String fileName) {
		if (fileName == null) {
			throw new RuntimeException("fileName is null");
		}
		this.fileName = fileName;
		return this;
	}

	public FiledotUseQuery fileNameLike(String fileNameLike) {
		if (fileNameLike == null) {
			throw new RuntimeException("fileName is null");
		}
		this.fileNameLike = fileNameLike;
		return this;
	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public Date getCreateDateGreaterThanOrEqual() {
		return createDateGreaterThanOrEqual;
	}

	public Date getCreateDateLessThanOrEqual() {
		return createDateLessThanOrEqual;
	}

	public String getDotid() {
		return dotid;
	}

	public List<String> getFileIDs() {
		return fileIDs;
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

	public Integer getIndexId() {
		return indexId;
	}

	public List<Integer> getIndexIds() {
		return indexIds;
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

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("indexId".equals(sortColumn)) {
				orderBy = "E.INDEX_ID" + a_x;
			}

			if ("projid".equals(sortColumn)) {
				orderBy = "E.PROJID" + a_x;
			}

			if ("pid".equals(sortColumn)) {
				orderBy = "E.PID" + a_x;
			}

			if ("dotid".equals(sortColumn)) {
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

			if ("filesize".equals(sortColumn)) {
				orderBy = "E.FILESIZE" + a_x;
			}

			if ("vision".equals(sortColumn)) {
				orderBy = "E.VISION" + a_x;
			}

			if ("savetime".equals(sortColumn)) {
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

			if ("topnode".equals(sortColumn)) {
				orderBy = "E.TOPNODE" + a_x;
			}

			if ("topid".equals(sortColumn)) {
				orderBy = "E.TOPID" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.CREATEDATE" + a_x;
			}

			if ("fNAME".equals(sortColumn)) {
				orderBy = "E.FNAME" + a_x;
			}

			if ("iSCHECK".equals(sortColumn)) {
				orderBy = "E.ISCHECK" + a_x;
			}

			if ("iSINK".equals(sortColumn)) {
				orderBy = "E.ISINK" + a_x;
			}

			if ("oLDID".equals(sortColumn)) {
				orderBy = "E.OLD_ID" + a_x;
			}

			if ("tASKID".equals(sortColumn)) {
				orderBy = "E.TASK_ID" + a_x;
			}

			if ("tYPE".equals(sortColumn)) {
				orderBy = "E.TYPE" + a_x;
			}

			if ("updateDate".equals(sortColumn)) {
				orderBy = "E.UPDATEDATE" + a_x;
			}

		}
		return orderBy;
	}

	public Integer getPid() {
		return pid;
	}

	public Integer getProjid() {
		return projid;
	}

	public String getTaskId() {
		return taskId;
	}

	public String getTopid() {
		return topid;
	}

	public String getTopidLike() {
		if (topidLike != null && topidLike.trim().length() > 0) {
			if (!topidLike.startsWith("%")) {
				topidLike = "%" + topidLike;
			}
			if (!topidLike.endsWith("%")) {
				topidLike = topidLike + "%";
			}
		}
		return topidLike;
	}

	public String getTopnode() {
		return topnode;
	}

	public String getTopnodeLike() {
		if (topnodeLike != null && topnodeLike.trim().length() > 0) {
			if (!topnodeLike.startsWith("%")) {
				topnodeLike = "%" + topnodeLike;
			}
			if (!topnodeLike.endsWith("%")) {
				topnodeLike = topnodeLike + "%";
			}
		}
		return topnodeLike;
	}

	public Integer getType() {
		return type;
	}

	public FiledotUseQuery indexId(Integer indexId) {
		if (indexId == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexId = indexId;
		return this;
	}

	public FiledotUseQuery indexIds(List<Integer> indexIds) {
		if (indexIds == null) {
			throw new RuntimeException("indexIds is empty ");
		}
		this.indexIds = indexIds;
		return this;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("fileID", "FILEID");
		addColumn("indexId", "INDEX_ID");
		addColumn("projid", "PROJID");
		addColumn("pid", "PID");
		addColumn("dotid", "DOTID");
		addColumn("num", "NUM");
		addColumn("name", "NAME");
		addColumn("cman", "CMAN");
		addColumn("ctime", "CTIME");
		addColumn("fileName", "FILE_NAME");
		addColumn("filesize", "FILESIZE");
		addColumn("vision", "VISION");
		addColumn("savetime", "SAVETIME");
		addColumn("remark", "REMARK");
		addColumn("dwid", "DWID");
		addColumn("fbid", "FBID");
		addColumn("fxid", "FXID");
		addColumn("jid", "JID");
		addColumn("flid", "FLID");
		addColumn("topnode", "TOPNODE");
		addColumn("topid", "TOPID");
		addColumn("createDate", "CREATEDATE");
		addColumn("fNAME", "FNAME");
		addColumn("iSCHECK", "ISCHECK");
		addColumn("iSINK", "ISINK");
		addColumn("oLDID", "OLD_ID");
		addColumn("tASKID", "TASK_ID");
		addColumn("tYPE", "TYPE");
		addColumn("updateDate", "UPDATEDATE");
	}

	public FiledotUseQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public FiledotUseQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public FiledotUseQuery pid(Integer pid) {
		if (pid == null) {
			throw new RuntimeException("pid is null");
		}
		this.pid = pid;
		return this;
	}

	public FiledotUseQuery projid(Integer projid) {
		if (projid == null) {
			throw new RuntimeException("projid is null");
		}
		this.projid = projid;
		return this;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public void setCreateDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
	}

	public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
	}

	public void setDotid(String dotid) {
		this.dotid = dotid;
	}

	public void setFileIDs(List<String> fileIDs) {
		this.fileIDs = fileIDs;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileNameLike(String fileNameLike) {
		this.fileNameLike = fileNameLike;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public void setIndexIds(List<Integer> indexIds) {
		this.indexIds = indexIds;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public void setProjid(Integer projid) {
		this.projid = projid;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setTopid(String topid) {
		this.topid = topid;
	}

	public void setTopidLike(String topidLike) {
		this.topidLike = topidLike;
	}

	public void setTopnode(String topnode) {
		this.topnode = topnode;
	}

	public void setTopnodeLike(String topnodeLike) {
		this.topnodeLike = topnodeLike;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public FiledotUseQuery topid(String topid) {
		if (topid == null) {
			throw new RuntimeException("topid is null");
		}
		this.topid = topid;
		return this;
	}

	public FiledotUseQuery topidLike(String topidLike) {
		if (topidLike == null) {
			throw new RuntimeException("topid is null");
		}
		this.topidLike = topidLike;
		return this;
	}

	public FiledotUseQuery topnode(String topnode) {
		if (topnode == null) {
			throw new RuntimeException("topnode is null");
		}
		this.topnode = topnode;
		return this;
	}

	public FiledotUseQuery topnodeLike(String topnodeLike) {
		if (topnodeLike == null) {
			throw new RuntimeException("topnode is null");
		}
		this.topnodeLike = topnodeLike;
		return this;
	}

}