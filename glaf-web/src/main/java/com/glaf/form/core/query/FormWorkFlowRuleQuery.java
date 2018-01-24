package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FormWorkFlowRuleQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
	protected Long actResDefId;
	protected Long actResDefIdGreaterThanOrEqual;
	protected Long actResDefIdLessThanOrEqual;
	protected List<Long> actResDefIds;
	protected String actResourceId;
	protected String actResourceIdLike;
	protected List<String> actResourceIds;
	protected String actTaskId;
	protected String actTaskIdLike;
	protected List<String> actTaskIds;
	protected String actTaskName;
	protected String actTaskNameLike;
	protected List<String> actTaskNames;
	protected String creator;
	protected String creatorLike;
	protected List<String> creators;
	protected Date createDatetimeGreaterThanOrEqual;
	protected Date createDatetimeLessThanOrEqual;
	protected String modifier;
	protected String modifierLike;
	protected List<String> modifiers;
	protected Date modifyDatetimeGreaterThanOrEqual;
	protected Date modifyDatetimeLessThanOrEqual;
	protected String pageId;
	protected String pageIdLike;
	protected List<String> pageIds;
	protected Integer version;
	protected Integer versionGreaterThanOrEqual;
	protected Integer versionLessThanOrEqual;
	protected List<Integer> versions;
	protected String defId;
	protected String defIdLike;

	public FormWorkFlowRuleQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public Long getActResDefId() {
		return actResDefId;
	}

	public Long getActResDefIdGreaterThanOrEqual() {
		return actResDefIdGreaterThanOrEqual;
	}

	public Long getActResDefIdLessThanOrEqual() {
		return actResDefIdLessThanOrEqual;
	}

	public List<Long> getActResDefIds() {
		return actResDefIds;
	}

	public String getActResourceId() {
		return actResourceId;
	}

	public String getActResourceIdLike() {
		if (actResourceIdLike != null && actResourceIdLike.trim().length() > 0) {
			if (!actResourceIdLike.startsWith("%")) {
				actResourceIdLike = "%" + actResourceIdLike;
			}
			if (!actResourceIdLike.endsWith("%")) {
				actResourceIdLike = actResourceIdLike + "%";
			}
		}
		return actResourceIdLike;
	}

	public List<String> getActResourceIds() {
		return actResourceIds;
	}

	public String getActTaskId() {
		return actTaskId;
	}

	public String getActTaskIdLike() {
		if (actTaskIdLike != null && actTaskIdLike.trim().length() > 0) {
			if (!actTaskIdLike.startsWith("%")) {
				actTaskIdLike = "%" + actTaskIdLike;
			}
			if (!actTaskIdLike.endsWith("%")) {
				actTaskIdLike = actTaskIdLike + "%";
			}
		}
		return actTaskIdLike;
	}

	public List<String> getActTaskIds() {
		return actTaskIds;
	}

	public String getActTaskName() {
		return actTaskName;
	}

	public String getActTaskNameLike() {
		if (actTaskNameLike != null && actTaskNameLike.trim().length() > 0) {
			if (!actTaskNameLike.startsWith("%")) {
				actTaskNameLike = "%" + actTaskNameLike;
			}
			if (!actTaskNameLike.endsWith("%")) {
				actTaskNameLike = actTaskNameLike + "%";
			}
		}
		return actTaskNameLike;
	}

	public List<String> getActTaskNames() {
		return actTaskNames;
	}

	public String getCreator() {
		return creator;
	}

	public String getCreatorLike() {
		if (creatorLike != null && creatorLike.trim().length() > 0) {
			if (!creatorLike.startsWith("%")) {
				creatorLike = "%" + creatorLike;
			}
			if (!creatorLike.endsWith("%")) {
				creatorLike = creatorLike + "%";
			}
		}
		return creatorLike;
	}

	public List<String> getCreators() {
		return creators;
	}

	public Date getCreateDatetimeGreaterThanOrEqual() {
		return createDatetimeGreaterThanOrEqual;
	}

	public Date getCreateDatetimeLessThanOrEqual() {
		return createDatetimeLessThanOrEqual;
	}

	public String getModifier() {
		return modifier;
	}

	public String getModifierLike() {
		if (modifierLike != null && modifierLike.trim().length() > 0) {
			if (!modifierLike.startsWith("%")) {
				modifierLike = "%" + modifierLike;
			}
			if (!modifierLike.endsWith("%")) {
				modifierLike = modifierLike + "%";
			}
		}
		return modifierLike;
	}

	public List<String> getModifiers() {
		return modifiers;
	}

	public Date getModifyDatetimeGreaterThanOrEqual() {
		return modifyDatetimeGreaterThanOrEqual;
	}

	public Date getModifyDatetimeLessThanOrEqual() {
		return modifyDatetimeLessThanOrEqual;
	}

	public String getDefId() {
		return defId;
	}

	public void setDefId(String defId) {
		this.defId = defId;
	}

	public String getDefIdLike() {
		if (defIdLike != null && defIdLike.trim().length() > 0) {
			if (!defIdLike.startsWith("%")) {
				defIdLike = "%" + defIdLike;
			}
			if (!defIdLike.endsWith("%")) {
				defIdLike = defIdLike + "%";
			}
		}
		return defIdLike;
	}

	public void setDefIdLike(String defIdLike) {
		this.defIdLike = defIdLike;
	}

	public String getPageId() {
		return pageId;
	}

	public String getPageIdLike() {
		if (pageIdLike != null && pageIdLike.trim().length() > 0) {
			if (!pageIdLike.startsWith("%")) {
				pageIdLike = "%" + pageIdLike;
			}
			if (!pageIdLike.endsWith("%")) {
				pageIdLike = pageIdLike + "%";
			}
		}
		return pageIdLike;
	}

	public List<String> getPageIds() {
		return pageIds;
	}

	public Integer getVersion() {
		return version;
	}

	public Integer getVersionGreaterThanOrEqual() {
		return versionGreaterThanOrEqual;
	}

	public Integer getVersionLessThanOrEqual() {
		return versionLessThanOrEqual;
	}

	public List<Integer> getVersions() {
		return versions;
	}

	public void setActResDefId(Long actResDefId) {
		this.actResDefId = actResDefId;
	}

	public void setActResDefIdGreaterThanOrEqual(Long actResDefIdGreaterThanOrEqual) {
		this.actResDefIdGreaterThanOrEqual = actResDefIdGreaterThanOrEqual;
	}

	public void setActResDefIdLessThanOrEqual(Long actResDefIdLessThanOrEqual) {
		this.actResDefIdLessThanOrEqual = actResDefIdLessThanOrEqual;
	}

	public void setActResDefIds(List<Long> actResDefIds) {
		this.actResDefIds = actResDefIds;
	}

	public void setActResourceId(String actResourceId) {
		this.actResourceId = actResourceId;
	}

	public void setActResourceIdLike(String actResourceIdLike) {
		this.actResourceIdLike = actResourceIdLike;
	}

	public void setActResourceIds(List<String> actResourceIds) {
		this.actResourceIds = actResourceIds;
	}

	public void setActTaskId(String actTaskId) {
		this.actTaskId = actTaskId;
	}

	public void setActTaskIdLike(String actTaskIdLike) {
		this.actTaskIdLike = actTaskIdLike;
	}

	public void setActTaskIds(List<String> actTaskIds) {
		this.actTaskIds = actTaskIds;
	}

	public void setActTaskName(String actTaskName) {
		this.actTaskName = actTaskName;
	}

	public void setActTaskNameLike(String actTaskNameLike) {
		this.actTaskNameLike = actTaskNameLike;
	}

	public void setActTaskNames(List<String> actTaskNames) {
		this.actTaskNames = actTaskNames;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setCreatorLike(String creatorLike) {
		this.creatorLike = creatorLike;
	}

	public void setCreators(List<String> creators) {
		this.creators = creators;
	}

	public void setCreateDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual) {
		this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
	}

	public void setCreateDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual) {
		this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public void setModifierLike(String modifierLike) {
		this.modifierLike = modifierLike;
	}

	public void setModifiers(List<String> modifiers) {
		this.modifiers = modifiers;
	}

	public void setModifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual) {
		this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
	}

	public void setModifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual) {
		this.modifyDatetimeLessThanOrEqual = modifyDatetimeLessThanOrEqual;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public void setPageIdLike(String pageIdLike) {
		this.pageIdLike = pageIdLike;
	}

	public void setPageIds(List<String> pageIds) {
		this.pageIds = pageIds;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public void setVersionGreaterThanOrEqual(Integer versionGreaterThanOrEqual) {
		this.versionGreaterThanOrEqual = versionGreaterThanOrEqual;
	}

	public void setVersionLessThanOrEqual(Integer versionLessThanOrEqual) {
		this.versionLessThanOrEqual = versionLessThanOrEqual;
	}

	public void setVersions(List<Integer> versions) {
		this.versions = versions;
	}

	public FormWorkFlowRuleQuery actResDefId(Long actResDefId) {
		if (actResDefId == null) {
			throw new RuntimeException("actResDefId is null");
		}
		this.actResDefId = actResDefId;
		return this;
	}

	public FormWorkFlowRuleQuery actResDefIdGreaterThanOrEqual(Long actResDefIdGreaterThanOrEqual) {
		if (actResDefIdGreaterThanOrEqual == null) {
			throw new RuntimeException("actResDefId is null");
		}
		this.actResDefIdGreaterThanOrEqual = actResDefIdGreaterThanOrEqual;
		return this;
	}

	public FormWorkFlowRuleQuery actResDefIdLessThanOrEqual(Long actResDefIdLessThanOrEqual) {
		if (actResDefIdLessThanOrEqual == null) {
			throw new RuntimeException("actResDefId is null");
		}
		this.actResDefIdLessThanOrEqual = actResDefIdLessThanOrEqual;
		return this;
	}

	public FormWorkFlowRuleQuery actResDefIds(List<Long> actResDefIds) {
		if (actResDefIds == null) {
			throw new RuntimeException("actResDefIds is empty ");
		}
		this.actResDefIds = actResDefIds;
		return this;
	}

	public FormWorkFlowRuleQuery actResourceId(String actResourceId) {
		if (actResourceId == null) {
			throw new RuntimeException("actResourceId is null");
		}
		this.actResourceId = actResourceId;
		return this;
	}

	public FormWorkFlowRuleQuery actResourceIdLike(String actResourceIdLike) {
		if (actResourceIdLike == null) {
			throw new RuntimeException("actResourceId is null");
		}
		this.actResourceIdLike = actResourceIdLike;
		return this;
	}

	public FormWorkFlowRuleQuery actResourceIds(List<String> actResourceIds) {
		if (actResourceIds == null) {
			throw new RuntimeException("actResourceIds is empty ");
		}
		this.actResourceIds = actResourceIds;
		return this;
	}

	public FormWorkFlowRuleQuery actTaskId(String actTaskId) {
		if (actTaskId == null) {
			throw new RuntimeException("actTaskId is null");
		}
		this.actTaskId = actTaskId;
		return this;
	}

	public FormWorkFlowRuleQuery actTaskIdLike(String actTaskIdLike) {
		if (actTaskIdLike == null) {
			throw new RuntimeException("actTaskId is null");
		}
		this.actTaskIdLike = actTaskIdLike;
		return this;
	}

	public FormWorkFlowRuleQuery actTaskIds(List<String> actTaskIds) {
		if (actTaskIds == null) {
			throw new RuntimeException("actTaskIds is empty ");
		}
		this.actTaskIds = actTaskIds;
		return this;
	}

	public FormWorkFlowRuleQuery actTaskName(String actTaskName) {
		if (actTaskName == null) {
			throw new RuntimeException("actTaskName is null");
		}
		this.actTaskName = actTaskName;
		return this;
	}

	public FormWorkFlowRuleQuery actTaskNameLike(String actTaskNameLike) {
		if (actTaskNameLike == null) {
			throw new RuntimeException("actTaskName is null");
		}
		this.actTaskNameLike = actTaskNameLike;
		return this;
	}

	public FormWorkFlowRuleQuery actTaskNames(List<String> actTaskNames) {
		if (actTaskNames == null) {
			throw new RuntimeException("actTaskNames is empty ");
		}
		this.actTaskNames = actTaskNames;
		return this;
	}

	public FormWorkFlowRuleQuery creator(String creator) {
		if (creator == null) {
			throw new RuntimeException("creator is null");
		}
		this.creator = creator;
		return this;
	}

	public FormWorkFlowRuleQuery creatorLike(String creatorLike) {
		if (creatorLike == null) {
			throw new RuntimeException("creator is null");
		}
		this.creatorLike = creatorLike;
		return this;
	}

	public FormWorkFlowRuleQuery creators(List<String> creators) {
		if (creators == null) {
			throw new RuntimeException("creators is empty ");
		}
		this.creators = creators;
		return this;
	}

	public FormWorkFlowRuleQuery createDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual) {
		if (createDatetimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createDatetime is null");
		}
		this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
		return this;
	}

	public FormWorkFlowRuleQuery createDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual) {
		if (createDatetimeLessThanOrEqual == null) {
			throw new RuntimeException("createDatetime is null");
		}
		this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
		return this;
	}

	public FormWorkFlowRuleQuery modifier(String modifier) {
		if (modifier == null) {
			throw new RuntimeException("modifier is null");
		}
		this.modifier = modifier;
		return this;
	}

	public FormWorkFlowRuleQuery modifierLike(String modifierLike) {
		if (modifierLike == null) {
			throw new RuntimeException("modifier is null");
		}
		this.modifierLike = modifierLike;
		return this;
	}

	public FormWorkFlowRuleQuery modifiers(List<String> modifiers) {
		if (modifiers == null) {
			throw new RuntimeException("modifiers is empty ");
		}
		this.modifiers = modifiers;
		return this;
	}

	public FormWorkFlowRuleQuery modifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual) {
		if (modifyDatetimeGreaterThanOrEqual == null) {
			throw new RuntimeException("modifyDatetime is null");
		}
		this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
		return this;
	}

	public FormWorkFlowRuleQuery modifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual) {
		if (modifyDatetimeLessThanOrEqual == null) {
			throw new RuntimeException("modifyDatetime is null");
		}
		this.modifyDatetimeLessThanOrEqual = modifyDatetimeLessThanOrEqual;
		return this;
	}

	public FormWorkFlowRuleQuery pageId(String pageId) {
		if (pageId == null) {
			throw new RuntimeException("pageId is null");
		}
		this.pageId = pageId;
		return this;
	}

	public FormWorkFlowRuleQuery pageIdLike(String pageIdLike) {
		if (pageIdLike == null) {
			throw new RuntimeException("pageId is null");
		}
		this.pageIdLike = pageIdLike;
		return this;
	}

	public FormWorkFlowRuleQuery pageIds(List<String> pageIds) {
		if (pageIds == null) {
			throw new RuntimeException("pageIds is empty ");
		}
		this.pageIds = pageIds;
		return this;
	}

	public FormWorkFlowRuleQuery version(Integer version) {
		if (version == null) {
			throw new RuntimeException("version is null");
		}
		this.version = version;
		return this;
	}

	public FormWorkFlowRuleQuery versionGreaterThanOrEqual(Integer versionGreaterThanOrEqual) {
		if (versionGreaterThanOrEqual == null) {
			throw new RuntimeException("version is null");
		}
		this.versionGreaterThanOrEqual = versionGreaterThanOrEqual;
		return this;
	}

	public FormWorkFlowRuleQuery versionLessThanOrEqual(Integer versionLessThanOrEqual) {
		if (versionLessThanOrEqual == null) {
			throw new RuntimeException("version is null");
		}
		this.versionLessThanOrEqual = versionLessThanOrEqual;
		return this;
	}

	public FormWorkFlowRuleQuery versions(List<Integer> versions) {
		if (versions == null) {
			throw new RuntimeException("versions is empty ");
		}
		this.versions = versions;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("actResDefId".equals(sortColumn)) {
				orderBy = "E.ACT_RESDEF_ID_" + a_x;
			}

			if ("actResourceId".equals(sortColumn)) {
				orderBy = "E.ACT_RESOURCE_ID_" + a_x;
			}

			if ("actTaskId".equals(sortColumn)) {
				orderBy = "E.ACT_TASKID_" + a_x;
			}

			if ("actTaskName".equals(sortColumn)) {
				orderBy = "E.ACT_TASKNAME_" + a_x;
			}

			if ("creator".equals(sortColumn)) {
				orderBy = "E.CREATOR_" + a_x;
			}

			if ("createDatetime".equals(sortColumn)) {
				orderBy = "E.CREATEDATETIME_" + a_x;
			}

			if ("modifier".equals(sortColumn)) {
				orderBy = "E.MODIFIER_" + a_x;
			}

			if ("modifyDatetime".equals(sortColumn)) {
				orderBy = "E.MODIFYDATETIME_" + a_x;
			}

			if ("pageId".equals(sortColumn)) {
				orderBy = "E.PAGE_ID_" + a_x;
			}

			if ("version".equals(sortColumn)) {
				orderBy = "E.VERSION_" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("actResDefId", "ACT_RESDEF_ID_");
		addColumn("actResourceId", "ACT_RESOURCE_ID_");
		addColumn("actTaskId", "ACT_TASKID_");
		addColumn("actTaskName", "ACT_TASKNAME_");
		addColumn("creator", "CREATOR_");
		addColumn("createDatetime", "CREATEDATETIME_");
		addColumn("modifier", "MODIFIER_");
		addColumn("modifyDatetime", "MODIFYDATETIME_");
		addColumn("pageId", "PAGE_ID_");
		addColumn("version", "VERSION_");
	}

}