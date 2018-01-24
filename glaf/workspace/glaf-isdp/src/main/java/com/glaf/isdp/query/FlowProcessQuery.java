package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FlowProcessQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected String processDId;
	protected String processDIdLike;
	protected List<String> processDIds;
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
	protected Integer state;
	protected Integer stateGreaterThanOrEqual;
	protected Integer stateLessThanOrEqual;
	protected List<Integer> states;

	public FlowProcessQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getProcessDId() {
		return processDId;
	}

	public String getProcessDIdLike() {
		if (processDIdLike != null && processDIdLike.trim().length() > 0) {
			if (!processDIdLike.startsWith("%")) {
				processDIdLike = "%" + processDIdLike;
			}
			if (!processDIdLike.endsWith("%")) {
				processDIdLike = processDIdLike + "%";
			}
		}
		return processDIdLike;
	}

	public List<String> getProcessDIds() {
		return processDIds;
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

	public Integer getState() {
		return state;
	}

	public Integer getStateGreaterThanOrEqual() {
		return stateGreaterThanOrEqual;
	}

	public Integer getStateLessThanOrEqual() {
		return stateLessThanOrEqual;
	}

	public List<Integer> getStates() {
		return states;
	}

	public void setProcessDId(String processDId) {
		this.processDId = processDId;
	}

	public void setProcessDIdLike(String processDIdLike) {
		this.processDIdLike = processDIdLike;
	}

	public void setProcessDIds(List<String> processDIds) {
		this.processDIds = processDIds;
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

	public void setState(Integer state) {
		this.state = state;
	}

	public void setStateGreaterThanOrEqual(Integer stateGreaterThanOrEqual) {
		this.stateGreaterThanOrEqual = stateGreaterThanOrEqual;
	}

	public void setStateLessThanOrEqual(Integer stateLessThanOrEqual) {
		this.stateLessThanOrEqual = stateLessThanOrEqual;
	}

	public void setStates(List<Integer> states) {
		this.states = states;
	}

	public FlowProcessQuery processDId(String processDId) {
		if (processDId == null) {
			throw new RuntimeException("processDId is null");
		}
		this.processDId = processDId;
		return this;
	}

	public FlowProcessQuery processDIdLike(String processDIdLike) {
		if (processDIdLike == null) {
			throw new RuntimeException("processDId is null");
		}
		this.processDIdLike = processDIdLike;
		return this;
	}

	public FlowProcessQuery processDIds(List<String> processDIds) {
		if (processDIds == null) {
			throw new RuntimeException("processDIds is empty ");
		}
		this.processDIds = processDIds;
		return this;
	}

	public FlowProcessQuery mainId(String mainId) {
		if (mainId == null) {
			throw new RuntimeException("mainId is null");
		}
		this.mainId = mainId;
		return this;
	}

	public FlowProcessQuery mainIdLike(String mainIdLike) {
		if (mainIdLike == null) {
			throw new RuntimeException("mainId is null");
		}
		this.mainIdLike = mainIdLike;
		return this;
	}

	public FlowProcessQuery mainIds(List<String> mainIds) {
		if (mainIds == null) {
			throw new RuntimeException("mainIds is empty ");
		}
		this.mainIds = mainIds;
		return this;
	}

	public FlowProcessQuery fileId(String fileId) {
		if (fileId == null) {
			throw new RuntimeException("fileId is null");
		}
		this.fileId = fileId;
		return this;
	}

	public FlowProcessQuery fileIdLike(String fileIdLike) {
		if (fileIdLike == null) {
			throw new RuntimeException("fileId is null");
		}
		this.fileIdLike = fileIdLike;
		return this;
	}

	public FlowProcessQuery fileIds(List<String> fileIds) {
		if (fileIds == null) {
			throw new RuntimeException("fileIds is empty ");
		}
		this.fileIds = fileIds;
		return this;
	}

	public FlowProcessQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public FlowProcessQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public FlowProcessQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public FlowProcessQuery content(String content) {
		if (content == null) {
			throw new RuntimeException("content is null");
		}
		this.content = content;
		return this;
	}

	public FlowProcessQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public FlowProcessQuery contents(List<String> contents) {
		if (contents == null) {
			throw new RuntimeException("contents is empty ");
		}
		this.contents = contents;
		return this;
	}

	public FlowProcessQuery actor(String actor) {
		if (actor == null) {
			throw new RuntimeException("actor is null");
		}
		this.actor = actor;
		return this;
	}

	public FlowProcessQuery actorLike(String actorLike) {
		if (actorLike == null) {
			throw new RuntimeException("actor is null");
		}
		this.actorLike = actorLike;
		return this;
	}

	public FlowProcessQuery actors(List<String> actors) {
		if (actors == null) {
			throw new RuntimeException("actors is empty ");
		}
		this.actors = actors;
		return this;
	}

	public FlowProcessQuery ctimeGreaterThanOrEqual(Date ctimeGreaterThanOrEqual) {
		if (ctimeGreaterThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
		return this;
	}

	public FlowProcessQuery ctimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		if (ctimeLessThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
		return this;
	}

	public FlowProcessQuery version(String version) {
		if (version == null) {
			throw new RuntimeException("version is null");
		}
		this.version = version;
		return this;
	}

	public FlowProcessQuery versionLike(String versionLike) {
		if (versionLike == null) {
			throw new RuntimeException("version is null");
		}
		this.versionLike = versionLike;
		return this;
	}

	public FlowProcessQuery versions(List<String> versions) {
		if (versions == null) {
			throw new RuntimeException("versions is empty ");
		}
		this.versions = versions;
		return this;
	}

	public FlowProcessQuery state(Integer state) {
		if (state == null) {
			throw new RuntimeException("state is null");
		}
		this.state = state;
		return this;
	}

	public FlowProcessQuery stateGreaterThanOrEqual(
			Integer stateGreaterThanOrEqual) {
		if (stateGreaterThanOrEqual == null) {
			throw new RuntimeException("state is null");
		}
		this.stateGreaterThanOrEqual = stateGreaterThanOrEqual;
		return this;
	}

	public FlowProcessQuery stateLessThanOrEqual(Integer stateLessThanOrEqual) {
		if (stateLessThanOrEqual == null) {
			throw new RuntimeException("state is null");
		}
		this.stateLessThanOrEqual = stateLessThanOrEqual;
		return this;
	}

	public FlowProcessQuery states(List<Integer> states) {
		if (states == null) {
			throw new RuntimeException("states is empty ");
		}
		this.states = states;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("processDId".equals(sortColumn)) {
				orderBy = "E.PROCESS_D_ID" + a_x;
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

			if ("state".equals(sortColumn)) {
				orderBy = "E.STATE" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("processDId", "PROCESS_D_ID");
		addColumn("mainId", "MAIN_ID");
		addColumn("fileId", "FILEID");
		addColumn("name", "NAME");
		addColumn("content", "CONTENT");
		addColumn("actor", "ACTOR");
		addColumn("ctime", "CTIME");
		addColumn("version", "VERSION");
		addColumn("state", "STATE");
	}

}