package com.glaf.convert.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ConvertElemTmplFormlQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> formlRuleIds;
	protected Collection<String> appActorIds;
	protected Long cvtElemId;
	protected Long cvtElemIdGreaterThanOrEqual;
	protected Long cvtElemIdLessThanOrEqual;
	protected List<Long> cvtElemIds;
	protected String formlName;
	protected String formlNameLike;
	protected List<String> formlNames;
	protected String formlContent;
	protected String formlContentLike;
	protected List<String> formlContents;
	protected String useConditon;
	protected String useConditonLike;
	protected List<String> useConditons;
	protected Date createDatetimeGreaterThanOrEqual;
	protected Date createDatetimeLessThanOrEqual;
	protected Date modifyDatetimeGreaterThanOrEqual;
	protected Date modifyDatetimeLessThanOrEqual;

	public ConvertElemTmplFormlQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public Long getCvtElemId() {
		return cvtElemId;
	}

	public Long getCvtElemIdGreaterThanOrEqual() {
		return cvtElemIdGreaterThanOrEqual;
	}

	public Long getCvtElemIdLessThanOrEqual() {
		return cvtElemIdLessThanOrEqual;
	}

	public List<Long> getCvtElemIds() {
		return cvtElemIds;
	}

	public String getFormlName() {
		return formlName;
	}

	public String getFormlNameLike() {
		if (formlNameLike != null && formlNameLike.trim().length() > 0) {
			if (!formlNameLike.startsWith("%")) {
				formlNameLike = "%" + formlNameLike;
			}
			if (!formlNameLike.endsWith("%")) {
				formlNameLike = formlNameLike + "%";
			}
		}
		return formlNameLike;
	}

	public List<String> getFormlNames() {
		return formlNames;
	}

	public String getFormlContent() {
		return formlContent;
	}

	public String getFormlContentLike() {
		if (formlContentLike != null && formlContentLike.trim().length() > 0) {
			if (!formlContentLike.startsWith("%")) {
				formlContentLike = "%" + formlContentLike;
			}
			if (!formlContentLike.endsWith("%")) {
				formlContentLike = formlContentLike + "%";
			}
		}
		return formlContentLike;
	}

	public List<String> getFormlContents() {
		return formlContents;
	}

	public String getUseConditon() {
		return useConditon;
	}

	public String getUseConditonLike() {
		if (useConditonLike != null && useConditonLike.trim().length() > 0) {
			if (!useConditonLike.startsWith("%")) {
				useConditonLike = "%" + useConditonLike;
			}
			if (!useConditonLike.endsWith("%")) {
				useConditonLike = useConditonLike + "%";
			}
		}
		return useConditonLike;
	}

	public List<String> getUseConditons() {
		return useConditons;
	}

	public Date getCreateDatetimeGreaterThanOrEqual() {
		return createDatetimeGreaterThanOrEqual;
	}

	public Date getCreateDatetimeLessThanOrEqual() {
		return createDatetimeLessThanOrEqual;
	}

	public Date getModifyDatetimeGreaterThanOrEqual() {
		return modifyDatetimeGreaterThanOrEqual;
	}

	public Date getModifyDatetimeLessThanOrEqual() {
		return modifyDatetimeLessThanOrEqual;
	}

	public void setCvtElemId(Long cvtElemId) {
		this.cvtElemId = cvtElemId;
	}

	public void setCvtElemIdGreaterThanOrEqual(Long cvtElemIdGreaterThanOrEqual) {
		this.cvtElemIdGreaterThanOrEqual = cvtElemIdGreaterThanOrEqual;
	}

	public void setCvtElemIdLessThanOrEqual(Long cvtElemIdLessThanOrEqual) {
		this.cvtElemIdLessThanOrEqual = cvtElemIdLessThanOrEqual;
	}

	public void setCvtElemIds(List<Long> cvtElemIds) {
		this.cvtElemIds = cvtElemIds;
	}

	public void setFormlName(String formlName) {
		this.formlName = formlName;
	}

	public void setFormlNameLike(String formlNameLike) {
		this.formlNameLike = formlNameLike;
	}

	public void setFormlNames(List<String> formlNames) {
		this.formlNames = formlNames;
	}

	public void setFormlContent(String formlContent) {
		this.formlContent = formlContent;
	}

	public void setFormlContentLike(String formlContentLike) {
		this.formlContentLike = formlContentLike;
	}

	public void setFormlContents(List<String> formlContents) {
		this.formlContents = formlContents;
	}

	public void setUseConditon(String useConditon) {
		this.useConditon = useConditon;
	}

	public void setUseConditonLike(String useConditonLike) {
		this.useConditonLike = useConditonLike;
	}

	public void setUseConditons(List<String> useConditons) {
		this.useConditons = useConditons;
	}

	public void setCreateDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual) {
		this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
	}

	public void setCreateDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual) {
		this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
	}

	public void setModifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual) {
		this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
	}

	public void setModifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual) {
		this.modifyDatetimeLessThanOrEqual = modifyDatetimeLessThanOrEqual;
	}

	public ConvertElemTmplFormlQuery cvtElemId(Long cvtElemId) {
		if (cvtElemId == null) {
			throw new RuntimeException("cvtElemId is null");
		}
		this.cvtElemId = cvtElemId;
		return this;
	}

	public ConvertElemTmplFormlQuery cvtElemIdGreaterThanOrEqual(Long cvtElemIdGreaterThanOrEqual) {
		if (cvtElemIdGreaterThanOrEqual == null) {
			throw new RuntimeException("cvtElemId is null");
		}
		this.cvtElemIdGreaterThanOrEqual = cvtElemIdGreaterThanOrEqual;
		return this;
	}

	public ConvertElemTmplFormlQuery cvtElemIdLessThanOrEqual(Long cvtElemIdLessThanOrEqual) {
		if (cvtElemIdLessThanOrEqual == null) {
			throw new RuntimeException("cvtElemId is null");
		}
		this.cvtElemIdLessThanOrEqual = cvtElemIdLessThanOrEqual;
		return this;
	}

	public ConvertElemTmplFormlQuery cvtElemIds(List<Long> cvtElemIds) {
		if (cvtElemIds == null) {
			throw new RuntimeException("cvtElemIds is empty ");
		}
		this.cvtElemIds = cvtElemIds;
		return this;
	}

	public ConvertElemTmplFormlQuery formlName(String formlName) {
		if (formlName == null) {
			throw new RuntimeException("formlName is null");
		}
		this.formlName = formlName;
		return this;
	}

	public ConvertElemTmplFormlQuery formlNameLike(String formlNameLike) {
		if (formlNameLike == null) {
			throw new RuntimeException("formlName is null");
		}
		this.formlNameLike = formlNameLike;
		return this;
	}

	public ConvertElemTmplFormlQuery formlNames(List<String> formlNames) {
		if (formlNames == null) {
			throw new RuntimeException("formlNames is empty ");
		}
		this.formlNames = formlNames;
		return this;
	}

	public ConvertElemTmplFormlQuery formlContent(String formlContent) {
		if (formlContent == null) {
			throw new RuntimeException("formlContent is null");
		}
		this.formlContent = formlContent;
		return this;
	}

	public ConvertElemTmplFormlQuery formlContentLike(String formlContentLike) {
		if (formlContentLike == null) {
			throw new RuntimeException("formlContent is null");
		}
		this.formlContentLike = formlContentLike;
		return this;
	}

	public ConvertElemTmplFormlQuery formlContents(List<String> formlContents) {
		if (formlContents == null) {
			throw new RuntimeException("formlContents is empty ");
		}
		this.formlContents = formlContents;
		return this;
	}

	public ConvertElemTmplFormlQuery useConditon(String useConditon) {
		if (useConditon == null) {
			throw new RuntimeException("useConditon is null");
		}
		this.useConditon = useConditon;
		return this;
	}

	public ConvertElemTmplFormlQuery useConditonLike(String useConditonLike) {
		if (useConditonLike == null) {
			throw new RuntimeException("useConditon is null");
		}
		this.useConditonLike = useConditonLike;
		return this;
	}

	public ConvertElemTmplFormlQuery useConditons(List<String> useConditons) {
		if (useConditons == null) {
			throw new RuntimeException("useConditons is empty ");
		}
		this.useConditons = useConditons;
		return this;
	}

	public ConvertElemTmplFormlQuery createDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual) {
		if (createDatetimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createDatetime is null");
		}
		this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
		return this;
	}

	public ConvertElemTmplFormlQuery createDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual) {
		if (createDatetimeLessThanOrEqual == null) {
			throw new RuntimeException("createDatetime is null");
		}
		this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
		return this;
	}

	public ConvertElemTmplFormlQuery modifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual) {
		if (modifyDatetimeGreaterThanOrEqual == null) {
			throw new RuntimeException("modifyDatetime is null");
		}
		this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
		return this;
	}

	public ConvertElemTmplFormlQuery modifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual) {
		if (modifyDatetimeLessThanOrEqual == null) {
			throw new RuntimeException("modifyDatetime is null");
		}
		this.modifyDatetimeLessThanOrEqual = modifyDatetimeLessThanOrEqual;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("cvtElemId".equals(sortColumn)) {
				orderBy = "E.CVT_ELEM_ID_" + a_x;
			}

			if ("formlName".equals(sortColumn)) {
				orderBy = "E.FORML_NAME_" + a_x;
			}

			if ("formlContent".equals(sortColumn)) {
				orderBy = "E.FORML_CONTENT_" + a_x;
			}

			if ("useConditon".equals(sortColumn)) {
				orderBy = "E.USECONDITION_" + a_x;
			}

			if ("createDatetime".equals(sortColumn)) {
				orderBy = "E.CREATE_DATETIME_" + a_x;
			}

			if ("modifyDatetime".equals(sortColumn)) {
				orderBy = "E.MODIFY_DATETIME_" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("formlRuleId", "FORML_RULE_ID_");
		addColumn("cvtElemId", "CVT_ELEM_ID_");
		addColumn("formlName", "FORML_NAME_");
		addColumn("formlContent", "FORML_CONTENT_");
		addColumn("useConditon", "USECONDITION_");
		addColumn("createDatetime", "CREATE_DATETIME_");
		addColumn("modifyDatetime", "MODIFY_DATETIME_");
	}

}