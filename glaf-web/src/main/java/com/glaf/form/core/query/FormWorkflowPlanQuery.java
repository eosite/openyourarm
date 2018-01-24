package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FormWorkflowPlanQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected Long processDefId;
	protected Long processDefIdGreaterThanOrEqual;
	protected Long processDefIdLessThanOrEqual;
	protected List<Long> processDefIds;
	protected String pageId;
	protected String key;
	protected List<String> keys;
	protected String pageIdLike;
	protected List<String> pageIds;
	protected String creator;
	protected String creatorLike;
	protected List<String> creators;
	protected Date createDateTimeGreaterThanOrEqual;
	protected Date createDateTimeLessThanOrEqual;
	protected String modifier;
	protected String modifierLike;
	protected List<String> modifiers;
	protected Date modifyDateTimeGreaterThanOrEqual;
	protected Date modifyDateTimeLessThanOrEqual;
	protected String defId;
	protected String defIdLike;
	protected List<String> defIds;
	protected Integer version;
	protected Integer versionGreaterThanOrEqual;
	protected Integer versionLessThanOrEqual;
	protected List<Integer> versions;

	public FormWorkflowPlanQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public Long getProcessDefId() {
		return processDefId;
	}

	public Long getProcessDefIdGreaterThanOrEqual() {
		return processDefIdGreaterThanOrEqual;
	}

	public Long getProcessDefIdLessThanOrEqual() {
		return processDefIdLessThanOrEqual;
	}

	public List<Long> getProcessDefIds() {
		return processDefIds;
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

	public Date getCreateDateTimeGreaterThanOrEqual() {
		return createDateTimeGreaterThanOrEqual;
	}

	public Date getCreateDateTimeLessThanOrEqual() {
		return createDateTimeLessThanOrEqual;
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

	public Date getModifyDateTimeGreaterThanOrEqual() {
		return modifyDateTimeGreaterThanOrEqual;
	}

	public Date getModifyDateTimeLessThanOrEqual() {
		return modifyDateTimeLessThanOrEqual;
	}

	public String getDefId() {
		return defId;
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

	public List<String> getDefIds() {
		return defIds;
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

	public void setProcessDefId(Long processDefId) {
		this.processDefId = processDefId;
	}

	public void setProcessDefIdGreaterThanOrEqual(Long processDefIdGreaterThanOrEqual) {
		this.processDefIdGreaterThanOrEqual = processDefIdGreaterThanOrEqual;
	}

	public void setProcessDefIdLessThanOrEqual(Long processDefIdLessThanOrEqual) {
		this.processDefIdLessThanOrEqual = processDefIdLessThanOrEqual;
	}

	public void setProcessDefIds(List<Long> processDefIds) {
		this.processDefIds = processDefIds;
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

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setCreatorLike(String creatorLike) {
		this.creatorLike = creatorLike;
	}

	public void setCreators(List<String> creators) {
		this.creators = creators;
	}

	public void setCreateDateTimeGreaterThanOrEqual(Date createDateTimeGreaterThanOrEqual) {
		this.createDateTimeGreaterThanOrEqual = createDateTimeGreaterThanOrEqual;
	}

	public void setCreateDateTimeLessThanOrEqual(Date createDateTimeLessThanOrEqual) {
		this.createDateTimeLessThanOrEqual = createDateTimeLessThanOrEqual;
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

	public void setModifyDateTimeGreaterThanOrEqual(Date modifyDateTimeGreaterThanOrEqual) {
		this.modifyDateTimeGreaterThanOrEqual = modifyDateTimeGreaterThanOrEqual;
	}

	public void setModifyDateTimeLessThanOrEqual(Date modifyDateTimeLessThanOrEqual) {
		this.modifyDateTimeLessThanOrEqual = modifyDateTimeLessThanOrEqual;
	}

	public void setDefId(String defId) {
		this.defId = defId;
	}

	public void setDefIdLike(String defIdLike) {
		this.defIdLike = defIdLike;
	}

	public void setDefIds(List<String> defIds) {
		this.defIds = defIds;
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

	public FormWorkflowPlanQuery processDefId(Long processDefId) {
		if (processDefId == null) {
			throw new RuntimeException("processDefId is null");
		}
		this.processDefId = processDefId;
		return this;
	}

	public FormWorkflowPlanQuery processDefIdGreaterThanOrEqual(Long processDefIdGreaterThanOrEqual) {
		if (processDefIdGreaterThanOrEqual == null) {
			throw new RuntimeException("processDefId is null");
		}
		this.processDefIdGreaterThanOrEqual = processDefIdGreaterThanOrEqual;
		return this;
	}

	public FormWorkflowPlanQuery processDefIdLessThanOrEqual(Long processDefIdLessThanOrEqual) {
		if (processDefIdLessThanOrEqual == null) {
			throw new RuntimeException("processDefId is null");
		}
		this.processDefIdLessThanOrEqual = processDefIdLessThanOrEqual;
		return this;
	}

	public FormWorkflowPlanQuery processDefIds(List<Long> processDefIds) {
		if (processDefIds == null) {
			throw new RuntimeException("processDefIds is empty ");
		}
		this.processDefIds = processDefIds;
		return this;
	}

	public FormWorkflowPlanQuery pageId(String pageId) {
		if (pageId == null) {
			throw new RuntimeException("pageId is null");
		}
		this.pageId = pageId;
		return this;
	}

	public FormWorkflowPlanQuery pageIdLike(String pageIdLike) {
		if (pageIdLike == null) {
			throw new RuntimeException("pageId is null");
		}
		this.pageIdLike = pageIdLike;
		return this;
	}

	public FormWorkflowPlanQuery pageIds(List<String> pageIds) {
		if (pageIds == null) {
			throw new RuntimeException("pageIds is empty ");
		}
		this.pageIds = pageIds;
		return this;
	}

	public FormWorkflowPlanQuery creator(String creator) {
		if (creator == null) {
			throw new RuntimeException("creator is null");
		}
		this.creator = creator;
		return this;
	}

	public FormWorkflowPlanQuery creatorLike(String creatorLike) {
		if (creatorLike == null) {
			throw new RuntimeException("creator is null");
		}
		this.creatorLike = creatorLike;
		return this;
	}

	public FormWorkflowPlanQuery creators(List<String> creators) {
		if (creators == null) {
			throw new RuntimeException("creators is empty ");
		}
		this.creators = creators;
		return this;
	}

	public FormWorkflowPlanQuery createDateTimeGreaterThanOrEqual(Date createDateTimeGreaterThanOrEqual) {
		if (createDateTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createDateTime is null");
		}
		this.createDateTimeGreaterThanOrEqual = createDateTimeGreaterThanOrEqual;
		return this;
	}

	public FormWorkflowPlanQuery createDateTimeLessThanOrEqual(Date createDateTimeLessThanOrEqual) {
		if (createDateTimeLessThanOrEqual == null) {
			throw new RuntimeException("createDateTime is null");
		}
		this.createDateTimeLessThanOrEqual = createDateTimeLessThanOrEqual;
		return this;
	}

	public FormWorkflowPlanQuery modifier(String modifier) {
		if (modifier == null) {
			throw new RuntimeException("modifier is null");
		}
		this.modifier = modifier;
		return this;
	}

	public FormWorkflowPlanQuery modifierLike(String modifierLike) {
		if (modifierLike == null) {
			throw new RuntimeException("modifier is null");
		}
		this.modifierLike = modifierLike;
		return this;
	}

	public FormWorkflowPlanQuery modifiers(List<String> modifiers) {
		if (modifiers == null) {
			throw new RuntimeException("modifiers is empty ");
		}
		this.modifiers = modifiers;
		return this;
	}

	public FormWorkflowPlanQuery modifyDateTimeGreaterThanOrEqual(Date modifyDateTimeGreaterThanOrEqual) {
		if (modifyDateTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("modifyDateTime is null");
		}
		this.modifyDateTimeGreaterThanOrEqual = modifyDateTimeGreaterThanOrEqual;
		return this;
	}

	public FormWorkflowPlanQuery modifyDateTimeLessThanOrEqual(Date modifyDateTimeLessThanOrEqual) {
		if (modifyDateTimeLessThanOrEqual == null) {
			throw new RuntimeException("modifyDateTime is null");
		}
		this.modifyDateTimeLessThanOrEqual = modifyDateTimeLessThanOrEqual;
		return this;
	}

	public FormWorkflowPlanQuery defId(String defId) {
		if (defId == null) {
			throw new RuntimeException("defId is null");
		}
		this.defId = defId;
		return this;
	}

	public FormWorkflowPlanQuery defIdLike(String defIdLike) {
		if (defIdLike == null) {
			throw new RuntimeException("defId is null");
		}
		this.defIdLike = defIdLike;
		return this;
	}

	public FormWorkflowPlanQuery defIds(List<String> defIds) {
		if (defIds == null) {
			throw new RuntimeException("defIds is empty ");
		}
		this.defIds = defIds;
		return this;
	}

	public FormWorkflowPlanQuery version(Integer version) {
		if (version == null) {
			throw new RuntimeException("version is null");
		}
		this.version = version;
		return this;
	}

	public FormWorkflowPlanQuery versionGreaterThanOrEqual(Integer versionGreaterThanOrEqual) {
		if (versionGreaterThanOrEqual == null) {
			throw new RuntimeException("version is null");
		}
		this.versionGreaterThanOrEqual = versionGreaterThanOrEqual;
		return this;
	}

	public FormWorkflowPlanQuery versionLessThanOrEqual(Integer versionLessThanOrEqual) {
		if (versionLessThanOrEqual == null) {
			throw new RuntimeException("version is null");
		}
		this.versionLessThanOrEqual = versionLessThanOrEqual;
		return this;
	}

	public FormWorkflowPlanQuery versions(List<Integer> versions) {
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

			if ("processDefId".equals(sortColumn)) {
				orderBy = "E.PROCESSDEF_ID_" + a_x;
			}

			if ("pageId".equals(sortColumn)) {
				orderBy = "E.PAGE_ID_" + a_x;
			}

			if ("creator".equals(sortColumn)) {
				orderBy = "E.CREATOR_" + a_x;
			}

			if ("createDateTime".equals(sortColumn)) {
				orderBy = "E.CREATEDATETIME_" + a_x;
			}

			if ("modifier".equals(sortColumn)) {
				orderBy = "E.MODIFIER_" + a_x;
			}

			if ("modifyDateTime".equals(sortColumn)) {
				orderBy = "E.MODIFYDATETIME_" + a_x;
			}

			if ("defId".equals(sortColumn)) {
				orderBy = "E.DEF_ID_" + a_x;
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
		addColumn("processDefId", "PROCESSDEF_ID_");
		addColumn("pageId", "PAGE_ID_");
		addColumn("creator", "CREATOR_");
		addColumn("createDateTime", "CREATEDATETIME_");
		addColumn("modifier", "MODIFIER_");
		addColumn("modifyDateTime", "MODIFYDATETIME_");
		addColumn("defId", "DEF_ID_");
		addColumn("version", "VERSION_");
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<String> getKeys() {
		return keys;
	}

	public void setKeys(List<String> keys) {
		this.keys = keys;
	}

}