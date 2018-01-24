package com.glaf.form.core.query;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.glaf.core.query.DataQuery;

public class FormEventQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected String diagram;
	protected String diagramLike;
	protected List<String> diagrams;
	protected String rule;
	protected String ruleLike;
	protected List<String> rules;
	protected String pageId;
	protected String pageIdLike;
	protected List<String> pageIds;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected String createBy;
	protected String createByLike;
	protected List<String> createBys;
	protected String updateBy;
	protected String updateByLike;
	protected List<String> updateBys;
	protected Date updateDateGreaterThanOrEqual;
	protected Date updateDateLessThanOrEqual;

	protected String eleId;
	protected String version;

	public FormEventQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getDiagram() {
		return diagram;
	}

	public String getDiagramLike() {
		if (diagramLike != null && diagramLike.trim().length() > 0) {
			if (!diagramLike.startsWith("%")) {
				diagramLike = "%" + diagramLike;
			}
			if (!diagramLike.endsWith("%")) {
				diagramLike = diagramLike + "%";
			}
		}
		return diagramLike;
	}

	public List<String> getDiagrams() {
		return diagrams;
	}

	public String getRule() {
		return rule;
	}

	public String getRuleLike() {
		if (ruleLike != null && ruleLike.trim().length() > 0) {
			if (!ruleLike.startsWith("%")) {
				ruleLike = "%" + ruleLike;
			}
			if (!ruleLike.endsWith("%")) {
				ruleLike = ruleLike + "%";
			}
		}
		return ruleLike;
	}

	public List<String> getRules() {
		return rules;
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

	public Date getCreateDateGreaterThanOrEqual() {
		return createDateGreaterThanOrEqual;
	}

	public Date getCreateDateLessThanOrEqual() {
		return createDateLessThanOrEqual;
	}

	public String getCreateBy() {
		return createBy;
	}

	public String getCreateByLike() {
		if (createByLike != null && createByLike.trim().length() > 0) {
			if (!createByLike.startsWith("%")) {
				createByLike = "%" + createByLike;
			}
			if (!createByLike.endsWith("%")) {
				createByLike = createByLike + "%";
			}
		}
		return createByLike;
	}

	public List<String> getCreateBys() {
		return createBys;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public String getUpdateByLike() {
		if (updateByLike != null && updateByLike.trim().length() > 0) {
			if (!updateByLike.startsWith("%")) {
				updateByLike = "%" + updateByLike;
			}
			if (!updateByLike.endsWith("%")) {
				updateByLike = updateByLike + "%";
			}
		}
		return updateByLike;
	}

	public List<String> getUpdateBys() {
		return updateBys;
	}

	public Date getUpdateDateGreaterThanOrEqual() {
		return updateDateGreaterThanOrEqual;
	}

	public Date getUpdateDateLessThanOrEqual() {
		return updateDateLessThanOrEqual;
	}

	public void setDiagram(String diagram) {
		this.diagram = diagram;
	}

	public void setDiagramLike(String diagramLike) {
		this.diagramLike = diagramLike;
	}

	public void setDiagrams(List<String> diagrams) {
		this.diagrams = diagrams;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public void setRuleLike(String ruleLike) {
		this.ruleLike = ruleLike;
	}

	public void setRules(List<String> rules) {
		this.rules = rules;
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

	public void setCreateDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual) {
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
	}

	public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateByLike(String createByLike) {
		this.createByLike = createByLike;
	}

	public void setCreateBys(List<String> createBys) {
		this.createBys = createBys;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateByLike(String updateByLike) {
		this.updateByLike = updateByLike;
	}

	public void setUpdateBys(List<String> updateBys) {
		this.updateBys = updateBys;
	}

	public void setUpdateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual) {
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
	}

	public void setUpdateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
	}

	public FormEventQuery diagram(String diagram) {
		if (diagram == null) {
			throw new RuntimeException("diagram is null");
		}
		this.diagram = diagram;
		return this;
	}

	public FormEventQuery diagramLike(String diagramLike) {
		if (diagramLike == null) {
			throw new RuntimeException("diagram is null");
		}
		this.diagramLike = diagramLike;
		return this;
	}

	public FormEventQuery diagrams(List<String> diagrams) {
		if (diagrams == null) {
			throw new RuntimeException("diagrams is empty ");
		}
		this.diagrams = diagrams;
		return this;
	}

	public FormEventQuery rule(String rule) {
		if (rule == null) {
			throw new RuntimeException("rule is null");
		}
		this.rule = rule;
		return this;
	}

	public FormEventQuery ruleLike(String ruleLike) {
		if (ruleLike == null) {
			throw new RuntimeException("rule is null");
		}
		this.ruleLike = ruleLike;
		return this;
	}

	public FormEventQuery rules(List<String> rules) {
		if (rules == null) {
			throw new RuntimeException("rules is empty ");
		}
		this.rules = rules;
		return this;
	}

	public FormEventQuery pageId(String pageId) {
		if (pageId == null) {
			throw new RuntimeException("pageId is null");
		}
		this.pageId = pageId;
		return this;
	}

	public FormEventQuery pageIdLike(String pageIdLike) {
		if (pageIdLike == null) {
			throw new RuntimeException("pageId is null");
		}
		this.pageIdLike = pageIdLike;
		return this;
	}

	public FormEventQuery pageIds(List<String> pageIds) {
		if (pageIds == null) {
			throw new RuntimeException("pageIds is empty ");
		}
		this.pageIds = pageIds;
		return this;
	}

	public FormEventQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public FormEventQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public FormEventQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public FormEventQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public FormEventQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public FormEventQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public FormEventQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public FormEventQuery updateBys(List<String> updateBys) {
		if (updateBys == null) {
			throw new RuntimeException("updateBys is empty ");
		}
		this.updateBys = updateBys;
		return this;
	}

	public FormEventQuery updateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public FormEventQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("diagram".equals(sortColumn)) {
				orderBy = "E.DIAGRAM_" + a_x;
			}

			if ("rule".equals(sortColumn)) {
				orderBy = "E.RULE_" + a_x;
			}

			if ("pageId".equals(sortColumn)) {
				orderBy = "E.PAGEID_" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.CREATEDATE_" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

			if ("updateBy".equals(sortColumn)) {
				orderBy = "E.UPDATEBY_" + a_x;
			}

			if ("updateDate".equals(sortColumn)) {
				orderBy = "E.UPDATEDATE_" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("diagram", "DIAGRAM_");
		addColumn("rule", "RULE_");
		addColumn("pageId", "PAGEID_");
		addColumn("createDate", "CREATEDATE_");
		addColumn("createBy", "CREATEBY_");
		addColumn("updateBy", "UPDATEBY_");
		addColumn("updateDate", "UPDATEDATE_");
	}

	public String getEleId() {
		return eleId;
	}

	public void setEleId(String eleId) {
		this.eleId = eleId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}