package com.glaf.convert.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ConvertElemTmplValidQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> validRuleIds;
	protected Collection<String> appActorIds;
	protected Long cvtElemId;
	protected Long cvtElemIdGreaterThanOrEqual;
	protected Long cvtElemIdLessThanOrEqual;
	protected List<Long> cvtElemIds;
	protected String validType;
	protected String validTypeLike;
	protected List<String> validTypes;
	protected String expression;
	protected String expressionLike;
	protected List<String> expressions;
	protected String dType;
	protected String dTypeLike;
	protected List<String> dTypes;
	protected Integer len;
	protected Integer lenGreaterThanOrEqual;
	protected Integer lenLessThanOrEqual;
	protected List<Integer> lens;
	protected String rangeUpper;
	protected String rangeUpperLike;
	protected List<String> rangeUppers;
	protected String rangeLower;
	protected String rangeLowerLike;
	protected List<String> rangeLowers;
	protected String useCondition;
	protected String useConditionLike;
	protected List<String> useConditions;
	protected Integer seq;
	protected Integer seqGreaterThanOrEqual;
	protected Integer seqLessThanOrEqual;
	protected List<Integer> seqs;
	protected Long parentRuleId;
	protected Long parentRuleIdGreaterThanOrEqual;
	protected Long parentRuleIdLessThanOrEqual;
	protected List<Long> parentRuleIds;
	protected List<String> treeIds;
	protected Date createDatetimeGreaterThanOrEqual;
	protected Date createDatetimeLessThanOrEqual;
	protected Date modifyDatetimeGreaterThanOrEqual;
	protected Date modifyDatetimeLessThanOrEqual;

	public ConvertElemTmplValidQuery() {

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

	public String getValidType() {
		return validType;
	}

	public String getValidTypeLike() {
		if (validTypeLike != null && validTypeLike.trim().length() > 0) {
			if (!validTypeLike.startsWith("%")) {
				validTypeLike = "%" + validTypeLike;
			}
			if (!validTypeLike.endsWith("%")) {
				validTypeLike = validTypeLike + "%";
			}
		}
		return validTypeLike;
	}

	public List<String> getValidTypes() {
		return validTypes;
	}

	public String getExpression() {
		return expression;
	}

	public String getExpressionLike() {
		if (expressionLike != null && expressionLike.trim().length() > 0) {
			if (!expressionLike.startsWith("%")) {
				expressionLike = "%" + expressionLike;
			}
			if (!expressionLike.endsWith("%")) {
				expressionLike = expressionLike + "%";
			}
		}
		return expressionLike;
	}

	public List<String> getExpressions() {
		return expressions;
	}

	public String getDType() {
		return dType;
	}

	public String getDTypeLike() {
		if (dTypeLike != null && dTypeLike.trim().length() > 0) {
			if (!dTypeLike.startsWith("%")) {
				dTypeLike = "%" + dTypeLike;
			}
			if (!dTypeLike.endsWith("%")) {
				dTypeLike = dTypeLike + "%";
			}
		}
		return dTypeLike;
	}

	public List<String> getDTypes() {
		return dTypes;
	}

	public Integer getLen() {
		return len;
	}

	public Integer getLenGreaterThanOrEqual() {
		return lenGreaterThanOrEqual;
	}

	public Integer getLenLessThanOrEqual() {
		return lenLessThanOrEqual;
	}

	public List<Integer> getLens() {
		return lens;
	}

	public String getRangeUpper() {
		return rangeUpper;
	}

	public String getRangeUpperLike() {
		if (rangeUpperLike != null && rangeUpperLike.trim().length() > 0) {
			if (!rangeUpperLike.startsWith("%")) {
				rangeUpperLike = "%" + rangeUpperLike;
			}
			if (!rangeUpperLike.endsWith("%")) {
				rangeUpperLike = rangeUpperLike + "%";
			}
		}
		return rangeUpperLike;
	}

	public List<String> getRangeUppers() {
		return rangeUppers;
	}

	public String getRangeLower() {
		return rangeLower;
	}

	public String getRangeLowerLike() {
		if (rangeLowerLike != null && rangeLowerLike.trim().length() > 0) {
			if (!rangeLowerLike.startsWith("%")) {
				rangeLowerLike = "%" + rangeLowerLike;
			}
			if (!rangeLowerLike.endsWith("%")) {
				rangeLowerLike = rangeLowerLike + "%";
			}
		}
		return rangeLowerLike;
	}

	public List<String> getRangeLowers() {
		return rangeLowers;
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

	public Integer getSeq() {
		return seq;
	}

	public Integer getSeqGreaterThanOrEqual() {
		return seqGreaterThanOrEqual;
	}

	public Integer getSeqLessThanOrEqual() {
		return seqLessThanOrEqual;
	}

	public List<Integer> getSeqs() {
		return seqs;
	}

	public Long getParentRuleId() {
		return parentRuleId;
	}

	public Long getParentRuleIdGreaterThanOrEqual() {
		return parentRuleIdGreaterThanOrEqual;
	}

	public Long getParentRuleIdLessThanOrEqual() {
		return parentRuleIdLessThanOrEqual;
	}

	public List<Long> getParentRuleIds() {
		return parentRuleIds;
	}

	public String getTreeId() {
		return treeId;
	}

	public String getTreeIdLike() {
		if (treeIdLike != null && treeIdLike.trim().length() > 0) {
			if (!treeIdLike.startsWith("%")) {
				treeIdLike = "%" + treeIdLike;
			}
			if (!treeIdLike.endsWith("%")) {
				treeIdLike = treeIdLike + "%";
			}
		}
		return treeIdLike;
	}

	public List<String> getTreeIds() {
		return treeIds;
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

	public void setValidType(String validType) {
		this.validType = validType;
	}

	public void setValidTypeLike(String validTypeLike) {
		this.validTypeLike = validTypeLike;
	}

	public void setValidTypes(List<String> validTypes) {
		this.validTypes = validTypes;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void setExpressionLike(String expressionLike) {
		this.expressionLike = expressionLike;
	}

	public void setExpressions(List<String> expressions) {
		this.expressions = expressions;
	}

	public void setDType(String dType) {
		this.dType = dType;
	}

	public void setDTypeLike(String dTypeLike) {
		this.dTypeLike = dTypeLike;
	}

	public void setDTypes(List<String> dTypes) {
		this.dTypes = dTypes;
	}

	public void setLen(Integer len) {
		this.len = len;
	}

	public void setLenGreaterThanOrEqual(Integer lenGreaterThanOrEqual) {
		this.lenGreaterThanOrEqual = lenGreaterThanOrEqual;
	}

	public void setLenLessThanOrEqual(Integer lenLessThanOrEqual) {
		this.lenLessThanOrEqual = lenLessThanOrEqual;
	}

	public void setLens(List<Integer> lens) {
		this.lens = lens;
	}

	public void setRangeUpper(String rangeUpper) {
		this.rangeUpper = rangeUpper;
	}

	public void setRangeUpperLike(String rangeUpperLike) {
		this.rangeUpperLike = rangeUpperLike;
	}

	public void setRangeUppers(List<String> rangeUppers) {
		this.rangeUppers = rangeUppers;
	}

	public void setRangeLower(String rangeLower) {
		this.rangeLower = rangeLower;
	}

	public void setRangeLowerLike(String rangeLowerLike) {
		this.rangeLowerLike = rangeLowerLike;
	}

	public void setRangeLowers(List<String> rangeLowers) {
		this.rangeLowers = rangeLowers;
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

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public void setSeqGreaterThanOrEqual(Integer seqGreaterThanOrEqual) {
		this.seqGreaterThanOrEqual = seqGreaterThanOrEqual;
	}

	public void setSeqLessThanOrEqual(Integer seqLessThanOrEqual) {
		this.seqLessThanOrEqual = seqLessThanOrEqual;
	}

	public void setSeqs(List<Integer> seqs) {
		this.seqs = seqs;
	}

	public void setParentRuleId(Long parentRuleId) {
		this.parentRuleId = parentRuleId;
	}

	public void setParentRuleIdGreaterThanOrEqual(Long parentRuleIdGreaterThanOrEqual) {
		this.parentRuleIdGreaterThanOrEqual = parentRuleIdGreaterThanOrEqual;
	}

	public void setParentRuleIdLessThanOrEqual(Long parentRuleIdLessThanOrEqual) {
		this.parentRuleIdLessThanOrEqual = parentRuleIdLessThanOrEqual;
	}

	public void setParentRuleIds(List<Long> parentRuleIds) {
		this.parentRuleIds = parentRuleIds;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public void setTreeIdLike(String treeIdLike) {
		this.treeIdLike = treeIdLike;
	}

	public void setTreeIds(List<String> treeIds) {
		this.treeIds = treeIds;
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

	public ConvertElemTmplValidQuery cvtElemId(Long cvtElemId) {
		if (cvtElemId == null) {
			throw new RuntimeException("cvtElemId is null");
		}
		this.cvtElemId = cvtElemId;
		return this;
	}

	public ConvertElemTmplValidQuery cvtElemIdGreaterThanOrEqual(Long cvtElemIdGreaterThanOrEqual) {
		if (cvtElemIdGreaterThanOrEqual == null) {
			throw new RuntimeException("cvtElemId is null");
		}
		this.cvtElemIdGreaterThanOrEqual = cvtElemIdGreaterThanOrEqual;
		return this;
	}

	public ConvertElemTmplValidQuery cvtElemIdLessThanOrEqual(Long cvtElemIdLessThanOrEqual) {
		if (cvtElemIdLessThanOrEqual == null) {
			throw new RuntimeException("cvtElemId is null");
		}
		this.cvtElemIdLessThanOrEqual = cvtElemIdLessThanOrEqual;
		return this;
	}

	public ConvertElemTmplValidQuery cvtElemIds(List<Long> cvtElemIds) {
		if (cvtElemIds == null) {
			throw new RuntimeException("cvtElemIds is empty ");
		}
		this.cvtElemIds = cvtElemIds;
		return this;
	}

	public ConvertElemTmplValidQuery validType(String validType) {
		if (validType == null) {
			throw new RuntimeException("validType is null");
		}
		this.validType = validType;
		return this;
	}

	public ConvertElemTmplValidQuery validTypeLike(String validTypeLike) {
		if (validTypeLike == null) {
			throw new RuntimeException("validType is null");
		}
		this.validTypeLike = validTypeLike;
		return this;
	}

	public ConvertElemTmplValidQuery validTypes(List<String> validTypes) {
		if (validTypes == null) {
			throw new RuntimeException("validTypes is empty ");
		}
		this.validTypes = validTypes;
		return this;
	}

	public ConvertElemTmplValidQuery expression(String expression) {
		if (expression == null) {
			throw new RuntimeException("expression is null");
		}
		this.expression = expression;
		return this;
	}

	public ConvertElemTmplValidQuery expressionLike(String expressionLike) {
		if (expressionLike == null) {
			throw new RuntimeException("expression is null");
		}
		this.expressionLike = expressionLike;
		return this;
	}

	public ConvertElemTmplValidQuery expressions(List<String> expressions) {
		if (expressions == null) {
			throw new RuntimeException("expressions is empty ");
		}
		this.expressions = expressions;
		return this;
	}

	public ConvertElemTmplValidQuery dType(String dType) {
		if (dType == null) {
			throw new RuntimeException("dType is null");
		}
		this.dType = dType;
		return this;
	}

	public ConvertElemTmplValidQuery dTypeLike(String dTypeLike) {
		if (dTypeLike == null) {
			throw new RuntimeException("dType is null");
		}
		this.dTypeLike = dTypeLike;
		return this;
	}

	public ConvertElemTmplValidQuery dTypes(List<String> dTypes) {
		if (dTypes == null) {
			throw new RuntimeException("dTypes is empty ");
		}
		this.dTypes = dTypes;
		return this;
	}

	public ConvertElemTmplValidQuery len(Integer len) {
		if (len == null) {
			throw new RuntimeException("len is null");
		}
		this.len = len;
		return this;
	}

	public ConvertElemTmplValidQuery lenGreaterThanOrEqual(Integer lenGreaterThanOrEqual) {
		if (lenGreaterThanOrEqual == null) {
			throw new RuntimeException("len is null");
		}
		this.lenGreaterThanOrEqual = lenGreaterThanOrEqual;
		return this;
	}

	public ConvertElemTmplValidQuery lenLessThanOrEqual(Integer lenLessThanOrEqual) {
		if (lenLessThanOrEqual == null) {
			throw new RuntimeException("len is null");
		}
		this.lenLessThanOrEqual = lenLessThanOrEqual;
		return this;
	}

	public ConvertElemTmplValidQuery lens(List<Integer> lens) {
		if (lens == null) {
			throw new RuntimeException("lens is empty ");
		}
		this.lens = lens;
		return this;
	}

	public ConvertElemTmplValidQuery rangeUpper(String rangeUpper) {
		if (rangeUpper == null) {
			throw new RuntimeException("rangeUpper is null");
		}
		this.rangeUpper = rangeUpper;
		return this;
	}

	public ConvertElemTmplValidQuery rangeUpperLike(String rangeUpperLike) {
		if (rangeUpperLike == null) {
			throw new RuntimeException("rangeUpper is null");
		}
		this.rangeUpperLike = rangeUpperLike;
		return this;
	}

	public ConvertElemTmplValidQuery rangeUppers(List<String> rangeUppers) {
		if (rangeUppers == null) {
			throw new RuntimeException("rangeUppers is empty ");
		}
		this.rangeUppers = rangeUppers;
		return this;
	}

	public ConvertElemTmplValidQuery rangeLower(String rangeLower) {
		if (rangeLower == null) {
			throw new RuntimeException("rangeLower is null");
		}
		this.rangeLower = rangeLower;
		return this;
	}

	public ConvertElemTmplValidQuery rangeLowerLike(String rangeLowerLike) {
		if (rangeLowerLike == null) {
			throw new RuntimeException("rangeLower is null");
		}
		this.rangeLowerLike = rangeLowerLike;
		return this;
	}

	public ConvertElemTmplValidQuery rangeLowers(List<String> rangeLowers) {
		if (rangeLowers == null) {
			throw new RuntimeException("rangeLowers is empty ");
		}
		this.rangeLowers = rangeLowers;
		return this;
	}

	public ConvertElemTmplValidQuery useCondition(String useCondition) {
		if (useCondition == null) {
			throw new RuntimeException("useCondition is null");
		}
		this.useCondition = useCondition;
		return this;
	}

	public ConvertElemTmplValidQuery useConditionLike(String useConditionLike) {
		if (useConditionLike == null) {
			throw new RuntimeException("useCondition is null");
		}
		this.useConditionLike = useConditionLike;
		return this;
	}

	public ConvertElemTmplValidQuery useConditions(List<String> useConditions) {
		if (useConditions == null) {
			throw new RuntimeException("useConditions is empty ");
		}
		this.useConditions = useConditions;
		return this;
	}

	public ConvertElemTmplValidQuery seq(Integer seq) {
		if (seq == null) {
			throw new RuntimeException("seq is null");
		}
		this.seq = seq;
		return this;
	}

	public ConvertElemTmplValidQuery seqGreaterThanOrEqual(Integer seqGreaterThanOrEqual) {
		if (seqGreaterThanOrEqual == null) {
			throw new RuntimeException("seq is null");
		}
		this.seqGreaterThanOrEqual = seqGreaterThanOrEqual;
		return this;
	}

	public ConvertElemTmplValidQuery seqLessThanOrEqual(Integer seqLessThanOrEqual) {
		if (seqLessThanOrEqual == null) {
			throw new RuntimeException("seq is null");
		}
		this.seqLessThanOrEqual = seqLessThanOrEqual;
		return this;
	}

	public ConvertElemTmplValidQuery seqs(List<Integer> seqs) {
		if (seqs == null) {
			throw new RuntimeException("seqs is empty ");
		}
		this.seqs = seqs;
		return this;
	}

	public ConvertElemTmplValidQuery parentRuleId(Long parentRuleId) {
		if (parentRuleId == null) {
			throw new RuntimeException("parentRuleId is null");
		}
		this.parentRuleId = parentRuleId;
		return this;
	}

	public ConvertElemTmplValidQuery parentRuleIdGreaterThanOrEqual(Long parentRuleIdGreaterThanOrEqual) {
		if (parentRuleIdGreaterThanOrEqual == null) {
			throw new RuntimeException("parentRuleId is null");
		}
		this.parentRuleIdGreaterThanOrEqual = parentRuleIdGreaterThanOrEqual;
		return this;
	}

	public ConvertElemTmplValidQuery parentRuleIdLessThanOrEqual(Long parentRuleIdLessThanOrEqual) {
		if (parentRuleIdLessThanOrEqual == null) {
			throw new RuntimeException("parentRuleId is null");
		}
		this.parentRuleIdLessThanOrEqual = parentRuleIdLessThanOrEqual;
		return this;
	}

	public ConvertElemTmplValidQuery parentRuleIds(List<Long> parentRuleIds) {
		if (parentRuleIds == null) {
			throw new RuntimeException("parentRuleIds is empty ");
		}
		this.parentRuleIds = parentRuleIds;
		return this;
	}

	public ConvertElemTmplValidQuery treeId(String treeId) {
		if (treeId == null) {
			throw new RuntimeException("treeId is null");
		}
		this.treeId = treeId;
		return this;
	}

	public ConvertElemTmplValidQuery treeIdLike(String treeIdLike) {
		if (treeIdLike == null) {
			throw new RuntimeException("treeId is null");
		}
		this.treeIdLike = treeIdLike;
		return this;
	}

	public ConvertElemTmplValidQuery treeIds(List<String> treeIds) {
		if (treeIds == null) {
			throw new RuntimeException("treeIds is empty ");
		}
		this.treeIds = treeIds;
		return this;
	}

	public ConvertElemTmplValidQuery createDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual) {
		if (createDatetimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createDatetime is null");
		}
		this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
		return this;
	}

	public ConvertElemTmplValidQuery createDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual) {
		if (createDatetimeLessThanOrEqual == null) {
			throw new RuntimeException("createDatetime is null");
		}
		this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
		return this;
	}

	public ConvertElemTmplValidQuery modifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual) {
		if (modifyDatetimeGreaterThanOrEqual == null) {
			throw new RuntimeException("modifyDatetime is null");
		}
		this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
		return this;
	}

	public ConvertElemTmplValidQuery modifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual) {
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

			if ("validType".equals(sortColumn)) {
				orderBy = "E.VALID_TYPE_" + a_x;
			}

			if ("expression".equals(sortColumn)) {
				orderBy = "E.EXPRESSION_" + a_x;
			}

			if ("dType".equals(sortColumn)) {
				orderBy = "E.DTYPE_" + a_x;
			}

			if ("len".equals(sortColumn)) {
				orderBy = "E.LEN_" + a_x;
			}

			if ("rangeUpper".equals(sortColumn)) {
				orderBy = "E.RANGE_UPPER_" + a_x;
			}

			if ("rangeLower".equals(sortColumn)) {
				orderBy = "E.RANGE_LOWER_" + a_x;
			}

			if ("useCondition".equals(sortColumn)) {
				orderBy = "E.USECONDITION_" + a_x;
			}

			if ("seq".equals(sortColumn)) {
				orderBy = "E.SEQ_" + a_x;
			}

			if ("parentRuleId".equals(sortColumn)) {
				orderBy = "E.PARENT_RULE_ID_" + a_x;
			}

			if ("treeId".equals(sortColumn)) {
				orderBy = "E.TREEID_" + a_x;
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
		addColumn("validRuleId", "VALID_RULE_ID_");
		addColumn("cvtElemId", "CVT_ELEM_ID_");
		addColumn("validType", "VALID_TYPE_");
		addColumn("expression", "EXPRESSION_");
		addColumn("dType", "DTYPE_");
		addColumn("len", "LEN_");
		addColumn("rangeUpper", "RANGE_UPPER_");
		addColumn("rangeLower", "RANGE_LOWER_");
		addColumn("useCondition", "USECONDITION_");
		addColumn("seq", "SEQ_");
		addColumn("parentRuleId", "PARENT_RULE_ID_");
		addColumn("treeId", "TREEID_");
		addColumn("createDatetime", "CREATE_DATETIME_");
		addColumn("modifyDatetime", "MODIFY_DATETIME_");
	}

}