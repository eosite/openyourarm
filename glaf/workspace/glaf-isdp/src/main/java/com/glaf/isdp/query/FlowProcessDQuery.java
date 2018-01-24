package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FlowProcessDQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected String mainId;
	protected String mainIdLike;
	protected List<String> mainIds;
	protected String fileId;
	protected String fileIdLike;
	protected List<String> fileIds;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String content;
	protected String contentLike;
	protected List<String> contents;
	protected String actor;
	protected String actorLike;
	protected List<String> actors;
	protected Date ctimeGreaterThanOrEqual;
	protected Date ctimeLessThanOrEqual;
	protected String version;
	protected String versionLike;
	protected List<String> versions;
	protected String tcadFile;
	protected String tcadFileLike;
	protected List<String> tcadFiles;
	protected Integer isSave;
	protected Integer isSaveGreaterThanOrEqual;
	protected Integer isSaveLessThanOrEqual;
	protected List<Integer> isSaves;
	protected Integer intFlag;
	protected Integer intFlagGreaterThanOrEqual;
	protected Integer intFlagLessThanOrEqual;
	protected List<Integer> intFlags;

	public FlowProcessDQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
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

	public String getFileId() {
		return fileId;
	}

	public String getFileIdLike() {
		if (fileIdLike != null && fileIdLike.trim().length() > 0) {
			if (!fileIdLike.startsWith("%")) {
				fileIdLike = "%" + fileIdLike;
			}
			if (!fileIdLike.endsWith("%")) {
				fileIdLike = fileIdLike + "%";
			}
		}
		return fileIdLike;
	}

	public List<String> getFileIds() {
		return fileIds;
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

	public String getActor() {
		return actor;
	}

	public String getActorLike() {
		if (actorLike != null && actorLike.trim().length() > 0) {
			if (!actorLike.startsWith("%")) {
				actorLike = "%" + actorLike;
			}
			if (!actorLike.endsWith("%")) {
				actorLike = actorLike + "%";
			}
		}
		return actorLike;
	}

	public List<String> getActors() {
		return actors;
	}

	public Date getCtimeGreaterThanOrEqual() {
		return ctimeGreaterThanOrEqual;
	}

	public Date getCtimeLessThanOrEqual() {
		return ctimeLessThanOrEqual;
	}

	public String getVersion() {
		return version;
	}

	public String getVersionLike() {
		if (versionLike != null && versionLike.trim().length() > 0) {
			if (!versionLike.startsWith("%")) {
				versionLike = "%" + versionLike;
			}
			if (!versionLike.endsWith("%")) {
				versionLike = versionLike + "%";
			}
		}
		return versionLike;
	}

	public List<String> getVersions() {
		return versions;
	}

	public String getTcadFile() {
		return tcadFile;
	}

	public String getTcadFileLike() {
		if (tcadFileLike != null && tcadFileLike.trim().length() > 0) {
			if (!tcadFileLike.startsWith("%")) {
				tcadFileLike = "%" + tcadFileLike;
			}
			if (!tcadFileLike.endsWith("%")) {
				tcadFileLike = tcadFileLike + "%";
			}
		}
		return tcadFileLike;
	}

	public List<String> getTcadFiles() {
		return tcadFiles;
	}

	public Integer getIsSave() {
		return isSave;
	}

	public Integer getIsSaveGreaterThanOrEqual() {
		return isSaveGreaterThanOrEqual;
	}

	public Integer getIsSaveLessThanOrEqual() {
		return isSaveLessThanOrEqual;
	}

	public List<Integer> getIsSaves() {
		return isSaves;
	}

	public Integer getIntFlag() {
		return intFlag;
	}

	public Integer getIntFlagGreaterThanOrEqual() {
		return intFlagGreaterThanOrEqual;
	}

	public Integer getIntFlagLessThanOrEqual() {
		return intFlagLessThanOrEqual;
	}

	public List<Integer> getIntFlags() {
		return intFlags;
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

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public void setFileIdLike(String fileIdLike) {
		this.fileIdLike = fileIdLike;
	}

	public void setFileIds(List<String> fileIds) {
		this.fileIds = fileIds;
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

	public void setContent(String content) {
		this.content = content;
	}

	public void setContentLike(String contentLike) {
		this.contentLike = contentLike;
	}

	public void setContents(List<String> contents) {
		this.contents = contents;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public void setActorLike(String actorLike) {
		this.actorLike = actorLike;
	}

	public void setActors(List<String> actors) {
		this.actors = actors;
	}

	public void setCtimeGreaterThanOrEqual(Date ctimeGreaterThanOrEqual) {
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
	}

	public void setCtimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setVersionLike(String versionLike) {
		this.versionLike = versionLike;
	}

	public void setVersions(List<String> versions) {
		this.versions = versions;
	}

	public void setTcadFile(String tcadFile) {
		this.tcadFile = tcadFile;
	}

	public void setTcadFileLike(String tcadFileLike) {
		this.tcadFileLike = tcadFileLike;
	}

	public void setTcadFiles(List<String> tcadFiles) {
		this.tcadFiles = tcadFiles;
	}

	public void setIsSave(Integer isSave) {
		this.isSave = isSave;
	}

	public void setIsSaveGreaterThanOrEqual(Integer isSaveGreaterThanOrEqual) {
		this.isSaveGreaterThanOrEqual = isSaveGreaterThanOrEqual;
	}

	public void setIsSaveLessThanOrEqual(Integer isSaveLessThanOrEqual) {
		this.isSaveLessThanOrEqual = isSaveLessThanOrEqual;
	}

	public void setIsSaves(List<Integer> isSaves) {
		this.isSaves = isSaves;
	}

	public void setIntFlag(Integer intFlag) {
		this.intFlag = intFlag;
	}

	public void setIntFlagGreaterThanOrEqual(Integer intFlagGreaterThanOrEqual) {
		this.intFlagGreaterThanOrEqual = intFlagGreaterThanOrEqual;
	}

	public void setIntFlagLessThanOrEqual(Integer intFlagLessThanOrEqual) {
		this.intFlagLessThanOrEqual = intFlagLessThanOrEqual;
	}

	public void setIntFlags(List<Integer> intFlags) {
		this.intFlags = intFlags;
	}

	public FlowProcessDQuery mainId(String mainId) {
		if (mainId == null) {
			throw new RuntimeException("mainId is null");
		}
		this.mainId = mainId;
		return this;
	}

	public FlowProcessDQuery mainIdLike(String mainIdLike) {
		if (mainIdLike == null) {
			throw new RuntimeException("mainId is null");
		}
		this.mainIdLike = mainIdLike;
		return this;
	}

	public FlowProcessDQuery mainIds(List<String> mainIds) {
		if (mainIds == null) {
			throw new RuntimeException("mainIds is empty ");
		}
		this.mainIds = mainIds;
		return this;
	}

	public FlowProcessDQuery fileId(String fileId) {
		if (fileId == null) {
			throw new RuntimeException("fileId is null");
		}
		this.fileId = fileId;
		return this;
	}

	public FlowProcessDQuery fileIdLike(String fileIdLike) {
		if (fileIdLike == null) {
			throw new RuntimeException("fileId is null");
		}
		this.fileIdLike = fileIdLike;
		return this;
	}

	public FlowProcessDQuery fileIds(List<String> fileIds) {
		if (fileIds == null) {
			throw new RuntimeException("fileIds is empty ");
		}
		this.fileIds = fileIds;
		return this;
	}

	public FlowProcessDQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public FlowProcessDQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public FlowProcessDQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public FlowProcessDQuery content(String content) {
		if (content == null) {
			throw new RuntimeException("content is null");
		}
		this.content = content;
		return this;
	}

	public FlowProcessDQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public FlowProcessDQuery contents(List<String> contents) {
		if (contents == null) {
			throw new RuntimeException("contents is empty ");
		}
		this.contents = contents;
		return this;
	}

	public FlowProcessDQuery actor(String actor) {
		if (actor == null) {
			throw new RuntimeException("actor is null");
		}
		this.actor = actor;
		return this;
	}

	public FlowProcessDQuery actorLike(String actorLike) {
		if (actorLike == null) {
			throw new RuntimeException("actor is null");
		}
		this.actorLike = actorLike;
		return this;
	}

	public FlowProcessDQuery actors(List<String> actors) {
		if (actors == null) {
			throw new RuntimeException("actors is empty ");
		}
		this.actors = actors;
		return this;
	}

	public FlowProcessDQuery ctimeGreaterThanOrEqual(
			Date ctimeGreaterThanOrEqual) {
		if (ctimeGreaterThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
		return this;
	}

	public FlowProcessDQuery ctimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		if (ctimeLessThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
		return this;
	}

	public FlowProcessDQuery version(String version) {
		if (version == null) {
			throw new RuntimeException("version is null");
		}
		this.version = version;
		return this;
	}

	public FlowProcessDQuery versionLike(String versionLike) {
		if (versionLike == null) {
			throw new RuntimeException("version is null");
		}
		this.versionLike = versionLike;
		return this;
	}

	public FlowProcessDQuery versions(List<String> versions) {
		if (versions == null) {
			throw new RuntimeException("versions is empty ");
		}
		this.versions = versions;
		return this;
	}

	public FlowProcessDQuery tcadFile(String tcadFile) {
		if (tcadFile == null) {
			throw new RuntimeException("tcadFile is null");
		}
		this.tcadFile = tcadFile;
		return this;
	}

	public FlowProcessDQuery tcadFileLike(String tcadFileLike) {
		if (tcadFileLike == null) {
			throw new RuntimeException("tcadFile is null");
		}
		this.tcadFileLike = tcadFileLike;
		return this;
	}

	public FlowProcessDQuery tcadFiles(List<String> tcadFiles) {
		if (tcadFiles == null) {
			throw new RuntimeException("tcadFiles is empty ");
		}
		this.tcadFiles = tcadFiles;
		return this;
	}

	public FlowProcessDQuery isSave(Integer isSave) {
		if (isSave == null) {
			throw new RuntimeException("isSave is null");
		}
		this.isSave = isSave;
		return this;
	}

	public FlowProcessDQuery isSaveGreaterThanOrEqual(
			Integer isSaveGreaterThanOrEqual) {
		if (isSaveGreaterThanOrEqual == null) {
			throw new RuntimeException("isSave is null");
		}
		this.isSaveGreaterThanOrEqual = isSaveGreaterThanOrEqual;
		return this;
	}

	public FlowProcessDQuery isSaveLessThanOrEqual(Integer isSaveLessThanOrEqual) {
		if (isSaveLessThanOrEqual == null) {
			throw new RuntimeException("isSave is null");
		}
		this.isSaveLessThanOrEqual = isSaveLessThanOrEqual;
		return this;
	}

	public FlowProcessDQuery isSaves(List<Integer> isSaves) {
		if (isSaves == null) {
			throw new RuntimeException("isSaves is empty ");
		}
		this.isSaves = isSaves;
		return this;
	}

	public FlowProcessDQuery intFlag(Integer intFlag) {
		if (intFlag == null) {
			throw new RuntimeException("intFlag is null");
		}
		this.intFlag = intFlag;
		return this;
	}

	public FlowProcessDQuery intFlagGreaterThanOrEqual(
			Integer intFlagGreaterThanOrEqual) {
		if (intFlagGreaterThanOrEqual == null) {
			throw new RuntimeException("intFlag is null");
		}
		this.intFlagGreaterThanOrEqual = intFlagGreaterThanOrEqual;
		return this;
	}

	public FlowProcessDQuery intFlagLessThanOrEqual(
			Integer intFlagLessThanOrEqual) {
		if (intFlagLessThanOrEqual == null) {
			throw new RuntimeException("intFlag is null");
		}
		this.intFlagLessThanOrEqual = intFlagLessThanOrEqual;
		return this;
	}

	public FlowProcessDQuery intFlags(List<Integer> intFlags) {
		if (intFlags == null) {
			throw new RuntimeException("intFlags is empty ");
		}
		this.intFlags = intFlags;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("mainId".equals(sortColumn)) {
				orderBy = "E.MAIN_ID" + a_x;
			}

			if ("fileId".equals(sortColumn)) {
				orderBy = "E.FILEID" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME" + a_x;
			}

			if ("content".equals(sortColumn)) {
				orderBy = "E.CONTENT" + a_x;
			}

			if ("actor".equals(sortColumn)) {
				orderBy = "E.ACTOR" + a_x;
			}

			if ("ctime".equals(sortColumn)) {
				orderBy = "E.CTIME" + a_x;
			}

			if ("version".equals(sortColumn)) {
				orderBy = "E.VERSION" + a_x;
			}

			if ("tcadFile".equals(sortColumn)) {
				orderBy = "E.TCADFILE" + a_x;
			}

			if ("isSave".equals(sortColumn)) {
				orderBy = "E.ISSAVE" + a_x;
			}

			if ("intFlag".equals(sortColumn)) {
				orderBy = "E.INTFLAG" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("mainId", "MAIN_ID");
		addColumn("fileId", "FILEID");
		addColumn("name", "NAME");
		addColumn("content", "CONTENT");
		addColumn("actor", "ACTOR");
		addColumn("ctime", "CTIME");
		addColumn("version", "VERSION");
		addColumn("tcadFile", "TCADFILE");
		addColumn("isSave", "ISSAVE");
		addColumn("intFlag", "INTFLAG");
	}

}