package com.glaf.convert.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ConvertElemTmplRefQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> refRuleIds;
	protected Collection<String> appActorIds;
	protected Long cvtElemId;
	protected Long cvtElemIdGreaterThanOrEqual;
	protected Long cvtElemIdLessThanOrEqual;
	protected List<Long> cvtElemIds;
	protected String refType;
	protected String refTypeLike;
	protected List<String> refTypes;
	protected String refContent;
	protected String refContentLike;
	protected List<String> refContents;
	protected String refCondition;
	protected String refConditionLike;
	protected List<String> refConditions;
	protected String refFieldId;
	protected String refFieldIdLike;
	protected List<String> refFieldIds;
	protected String useCondition;
	protected String useConditionLike;
	protected List<String> useConditions;
	protected String transtionFlag;
	protected String transtionFlagLike;
	protected List<String> transtionFlags;
	protected Date createDatetimeGreaterThanOrEqual;
	protected Date createDatetimeLessThanOrEqual;
	protected Date modifyDatetimeGreaterThanOrEqual;
	protected Date modifyDatetimeLessThanOrEqual;

	public ConvertElemTmplRefQuery() {

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

	public String getRefType() {
		return refType;
	}

	public String getRefTypeLike() {
		if (refTypeLike != null && refTypeLike.trim().length() > 0) {
			if (!refTypeLike.startsWith("%")) {
				refTypeLike = "%" + refTypeLike;
			}
			if (!refTypeLike.endsWith("%")) {
				refTypeLike = refTypeLike + "%";
			}
		}
		return refTypeLike;
	}

	public List<String> getRefTypes() {
		return refTypes;
	}

	public String getRefContent() {
		return refContent;
	}

	public String getRefContentLike() {
		if (refContentLike != null && refContentLike.trim().length() > 0) {
			if (!refContentLike.startsWith("%")) {
				refContentLike = "%" + refContentLike;
			}
			if (!refContentLike.endsWith("%")) {
				refContentLike = refContentLike + "%";
			}
		}
		return refContentLike;
	}

	public List<String> getRefContents() {
		return refContents;
	}

	public String getRefCondition() {
		return refCondition;
	}

	public String getRefConditionLike() {
		if (refConditionLike != null && refConditionLike.trim().length() > 0) {
			if (!refConditionLike.startsWith("%")) {
				refConditionLike = "%" + refConditionLike;
			}
			if (!refConditionLike.endsWith("%")) {
				refConditionLike = refConditionLike + "%";
			}
		}
		return refConditionLike;
	}

	public List<String> getRefConditions() {
		return refConditions;
	}

	public String getRefFieldId() {
		return refFieldId;
	}

	public String getRefFieldIdLike() {
		if (refFieldIdLike != null && refFieldIdLike.trim().length() > 0) {
			if (!refFieldIdLike.startsWith("%")) {
				refFieldIdLike = "%" + refFieldIdLike;
			}
			if (!refFieldIdLike.endsWith("%")) {
				refFieldIdLike = refFieldIdLike + "%";
			}
		}
		return refFieldIdLike;
	}

	public List<String> getRefFieldIds() {
		return refFieldIds;
	}

	public String getUseCondition() {
		return useCondition;
	}

	public String getUseConditionLike() {
		if (useConditionLike != null && useConditionLike.trim().length() > 0) {
			if (!useConditionLike.startsWith("%")) {
				useConditionLike = "%" + useConditionLike;
			}
			if (!useConditionLike.endsWith("%")) {
				useConditionLike = useConditionLike + "%";
			}
		}
		return useConditionLike;
	}

	public List<String> getUseConditions() {
		return useConditions;
	}

	public String getTranstionFlag() {
		return transtionFlag;
	}

	public String getTranstionFlagLike() {
		if (transtionFlagLike != null && transtionFlagLike.trim().length() > 0) {
			if (!transtionFlagLike.startsWith("%")) {
				transtionFlagLike = "%" + transtionFlagLike;
			}
			if (!transtionFlagLike.endsWith("%")) {
				transtionFlagLike = transtionFlagLike + "%";
			}
		}
		return transtionFlagLike;
	}

	public List<String> getTranstionFlags() {
		return transtionFlags;
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

	public void setRefType(String refType) {
		this.refType = refType;
	}

	public void setRefTypeLike(String refTypeLike) {
		this.refTypeLike = refTypeLike;
	}

	public void setRefTypes(List<String> refTypes) {
		this.refTypes = refTypes;
	}

	public void setRefContent(String refContent) {
		this.refContent = refContent;
	}

	public void setRefContentLike(String refContentLike) {
		this.refContentLike = refContentLike;
	}

	public void setRefContents(List<String> refContents) {
		this.refContents = refContents;
	}

	public void setRefCondition(String refCondition) {
		this.refCondition = refCondition;
	}

	public void setRefConditionLike(String refConditionLike) {
		this.refConditionLike = refConditionLike;
	}

	public void setRefConditions(List<String> refConditions) {
		this.refConditions = refConditions;
	}

	public void setRefFieldId(String refFieldId) {
		this.refFieldId = refFieldId;
	}

	public void setRefFieldIdLike(String refFieldIdLike) {
		this.refFieldIdLike = refFieldIdLike;
	}

	public void setRefFieldIds(List<String> refFieldIds) {
		this.refFieldIds = refFieldIds;
	}

	public void setUseCondition(String useCondition) {
		this.useCondition = useCondition;
	}

	public void setUseConditionLike(String useConditionLike) {
		this.useConditionLike = useConditionLike;
	}

	public void setUseConditions(List<String> useConditions) {
		this.useConditions = useConditions;
	}

	public void setTranstionFlag(String transtionFlag) {
		this.transtionFlag = transtionFlag;
	}

	public void setTranstionFlagLike(String transtionFlagLike) {
		this.transtionFlagLike = transtionFlagLike;
	}

	public void setTranstionFlags(List<String> transtionFlags) {
		this.transtionFlags = transtionFlags;
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

	public ConvertElemTmplRefQuery cvtElemId(Long cvtElemId) {
		if (cvtElemId == null) {
			throw new RuntimeException("cvtElemId is null");
		}
		this.cvtElemId = cvtElemId;
		return this;
	}

	public ConvertElemTmplRefQuery cvtElemIdGreaterThanOrEqual(Long cvtElemIdGreaterThanOrEqual) {
		if (cvtElemIdGreaterThanOrEqual == null) {
			throw new RuntimeException("cvtElemId is null");
		}
		this.cvtElemIdGreaterThanOrEqual = cvtElemIdGreaterThanOrEqual;
		return this;
	}

	public ConvertElemTmplRefQuery cvtElemIdLessThanOrEqual(Long cvtElemIdLessThanOrEqual) {
		if (cvtElemIdLessThanOrEqual == null) {
			throw new RuntimeException("cvtElemId is null");
		}
		this.cvtElemIdLessThanOrEqual = cvtElemIdLessThanOrEqual;
		return this;
	}

	public ConvertElemTmplRefQuery cvtElemIds(List<Long> cvtElemIds) {
		if (cvtElemIds == null) {
			throw new RuntimeException("cvtElemIds is empty ");
		}
		this.cvtElemIds = cvtElemIds;
		return this;
	}

	public ConvertElemTmplRefQuery refType(String refType) {
		if (refType == null) {
			throw new RuntimeException("refType is null");
		}
		this.refType = refType;
		return this;
	}

	public ConvertElemTmplRefQuery refTypeLike(String refTypeLike) {
		if (refTypeLike == null) {
			throw new RuntimeException("refType is null");
		}
		this.refTypeLike = refTypeLike;
		return this;
	}

	public ConvertElemTmplRefQuery refTypes(List<String> refTypes) {
		if (refTypes == null) {
			throw new RuntimeException("refTypes is empty ");
		}
		this.refTypes = refTypes;
		return this;
	}

	public ConvertElemTmplRefQuery refContent(String refContent) {
		if (refContent == null) {
			throw new RuntimeException("refContent is null");
		}
		this.refContent = refContent;
		return this;
	}

	public ConvertElemTmplRefQuery refContentLike(String refContentLike) {
		if (refContentLike == null) {
			throw new RuntimeException("refContent is null");
		}
		this.refContentLike = refContentLike;
		return this;
	}

	public ConvertElemTmplRefQuery refContents(List<String> refContents) {
		if (refContents == null) {
			throw new RuntimeException("refContents is empty ");
		}
		this.refContents = refContents;
		return this;
	}

	public ConvertElemTmplRefQuery refCondition(String refCondition) {
		if (refCondition == null) {
			throw new RuntimeException("refCondition is null");
		}
		this.refCondition = refCondition;
		return this;
	}

	public ConvertElemTmplRefQuery refConditionLike(String refConditionLike) {
		if (refConditionLike == null) {
			throw new RuntimeException("refCondition is null");
		}
		this.refConditionLike = refConditionLike;
		return this;
	}

	public ConvertElemTmplRefQuery refConditions(List<String> refConditions) {
		if (refConditions == null) {
			throw new RuntimeException("refConditions is empty ");
		}
		this.refConditions = refConditions;
		return this;
	}

	public ConvertElemTmplRefQuery refFieldId(String refFieldId) {
		if (refFieldId == null) {
			throw new RuntimeException("refFieldId is null");
		}
		this.refFieldId = refFieldId;
		return this;
	}

	public ConvertElemTmplRefQuery refFieldIdLike(String refFieldIdLike) {
		if (refFieldIdLike == null) {
			throw new RuntimeException("refFieldId is null");
		}
		this.refFieldIdLike = refFieldIdLike;
		return this;
	}

	public ConvertElemTmplRefQuery refFieldIds(List<String> refFieldIds) {
		if (refFieldIds == null) {
			throw new RuntimeException("refFieldIds is empty ");
		}
		this.refFieldIds = refFieldIds;
		return this;
	}

	public ConvertElemTmplRefQuery useCondition(String useCondition) {
		if (useCondition == null) {
			throw new RuntimeException("useCondition is null");
		}
		this.useCondition = useCondition;
		return this;
	}

	public ConvertElemTmplRefQuery useConditionLike(String useConditionLike) {
		if (useConditionLike == null) {
			throw new RuntimeException("useCondition is null");
		}
		this.useConditionLike = useConditionLike;
		return this;
	}

	public ConvertElemTmplRefQuery useConditions(List<String> useConditions) {
		if (useConditions == null) {
			throw new RuntimeException("useConditions is empty ");
		}
		this.useConditions = useConditions;
		return this;
	}

	public ConvertElemTmplRefQuery transtionFlag(String transtionFlag) {
		if (transtionFlag == null) {
			throw new RuntimeException("transtionFlag is null");
		}
		this.transtionFlag = transtionFlag;
		return this;
	}

	public ConvertElemTmplRefQuery transtionFlagLike(String transtionFlagLike) {
		if (transtionFlagLike == null) {
			throw new RuntimeException("transtionFlag is null");
		}
		this.transtionFlagLike = transtionFlagLike;
		return this;
	}

	public ConvertElemTmplRefQuery transtionFlags(List<String> transtionFlags) {
		if (transtionFlags == null) {
			throw new RuntimeException("transtionFlags is empty ");
		}
		this.transtionFlags = transtionFlags;
		return this;
	}

	public ConvertElemTmplRefQuery createDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual) {
		if (createDatetimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createDatetime is null");
		}
		this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
		return this;
	}

	public ConvertElemTmplRefQuery createDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual) {
		if (createDatetimeLessThanOrEqual == null) {
			throw new RuntimeException("createDatetime is null");
		}
		this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
		return this;
	}

	public ConvertElemTmplRefQuery modifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual) {
		if (modifyDatetimeGreaterThanOrEqual == null) {
			throw new RuntimeException("modifyDatetime is null");
		}
		this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
		return this;
	}

	public ConvertElemTmplRefQuery modifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual) {
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

			if ("refType".equals(sortColumn)) {
				orderBy = "E.REF_TYPE_" + a_x;
			}

			if ("refContent".equals(sortColumn)) {
				orderBy = "E.REF_CONTENT_" + a_x;
			}

			if ("refCondition".equals(sortColumn)) {
				orderBy = "E.REF_CONDITON_" + a_x;
			}

			if ("refFieldId".equals(sortColumn)) {
				orderBy = "E.REF_FIELD_ID_" + a_x;
			}

			if ("useCondition".equals(sortColumn)) {
				orderBy = "E.USECONDITION_" + a_x;
			}

			if ("transtionFlag".equals(sortColumn)) {
				orderBy = "E.TRANSTION_FLAG_" + a_x;
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
		addColumn("refRuleId", "REF_RULE_ID_");
		addColumn("cvtElemId", "CVT_ELEM_ID_");
		addColumn("refType", "REF_TYPE_");
		addColumn("refContent", "REF_CONTENT_");
		addColumn("refCondition", "REF_CONDITON_");
		addColumn("refFieldId", "REF_FIELD_ID_");
		addColumn("useCondition", "USECONDITION_");
		addColumn("transtionFlag", "TRANSTION_FLAG_");
		addColumn("createDatetime", "CREATE_DATETIME_");
		addColumn("modifyDatetime", "MODIFY_DATETIME_");
	}

}