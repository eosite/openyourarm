package com.glaf.convert.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ConvertPageParamQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> cvtParamIds;
	protected Collection<String> appActorIds;
	protected Long cvtId;
	protected Long cvtIdGreaterThanOrEqual;
	protected Long cvtIdLessThanOrEqual;
	protected List<Long> cvtIds;
	protected String paramName;
	protected String paramNameLike;
	protected List<String> paramNames;
	protected String paramCode;
	protected String paramCodeLike;
	protected List<String> paramCodes;
	protected String paramType;
	protected String paramTypeLike;
	protected List<String> paramTypes;
	protected Date createDatetimeGreaterThanOrEqual;
	protected Date createDatetimeLessThanOrEqual;
	protected Date modifyDatetimeGreaterThanOrEqual;
	protected Date modifyDatetimeLessThanOrEqual;

	public ConvertPageParamQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public Long getCvtId() {
		return cvtId;
	}

	public Long getCvtIdGreaterThanOrEqual() {
		return cvtIdGreaterThanOrEqual;
	}

	public Long getCvtIdLessThanOrEqual() {
		return cvtIdLessThanOrEqual;
	}

	public List<Long> getCvtIds() {
		return cvtIds;
	}

	public String getParamName() {
		return paramName;
	}

	public String getParamNameLike() {
		if (paramNameLike != null && paramNameLike.trim().length() > 0) {
			if (!paramNameLike.startsWith("%")) {
				paramNameLike = "%" + paramNameLike;
			}
			if (!paramNameLike.endsWith("%")) {
				paramNameLike = paramNameLike + "%";
			}
		}
		return paramNameLike;
	}

	public List<String> getParamNames() {
		return paramNames;
	}

	public String getParamCode() {
		return paramCode;
	}

	public String getParamCodeLike() {
		if (paramCodeLike != null && paramCodeLike.trim().length() > 0) {
			if (!paramCodeLike.startsWith("%")) {
				paramCodeLike = "%" + paramCodeLike;
			}
			if (!paramCodeLike.endsWith("%")) {
				paramCodeLike = paramCodeLike + "%";
			}
		}
		return paramCodeLike;
	}

	public List<String> getParamCodes() {
		return paramCodes;
	}

	public String getParamType() {
		return paramType;
	}

	public String getParamTypeLike() {
		if (paramTypeLike != null && paramTypeLike.trim().length() > 0) {
			if (!paramTypeLike.startsWith("%")) {
				paramTypeLike = "%" + paramTypeLike;
			}
			if (!paramTypeLike.endsWith("%")) {
				paramTypeLike = paramTypeLike + "%";
			}
		}
		return paramTypeLike;
	}

	public List<String> getParamTypes() {
		return paramTypes;
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

	public void setCvtId(Long cvtId) {
		this.cvtId = cvtId;
	}

	public void setCvtIdGreaterThanOrEqual(Long cvtIdGreaterThanOrEqual) {
		this.cvtIdGreaterThanOrEqual = cvtIdGreaterThanOrEqual;
	}

	public void setCvtIdLessThanOrEqual(Long cvtIdLessThanOrEqual) {
		this.cvtIdLessThanOrEqual = cvtIdLessThanOrEqual;
	}

	public void setCvtIds(List<Long> cvtIds) {
		this.cvtIds = cvtIds;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public void setParamNameLike(String paramNameLike) {
		this.paramNameLike = paramNameLike;
	}

	public void setParamNames(List<String> paramNames) {
		this.paramNames = paramNames;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public void setParamCodeLike(String paramCodeLike) {
		this.paramCodeLike = paramCodeLike;
	}

	public void setParamCodes(List<String> paramCodes) {
		this.paramCodes = paramCodes;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public void setParamTypeLike(String paramTypeLike) {
		this.paramTypeLike = paramTypeLike;
	}

	public void setParamTypes(List<String> paramTypes) {
		this.paramTypes = paramTypes;
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

	public ConvertPageParamQuery cvtId(Long cvtId) {
		if (cvtId == null) {
			throw new RuntimeException("cvtId is null");
		}
		this.cvtId = cvtId;
		return this;
	}

	public ConvertPageParamQuery cvtIdGreaterThanOrEqual(Long cvtIdGreaterThanOrEqual) {
		if (cvtIdGreaterThanOrEqual == null) {
			throw new RuntimeException("cvtId is null");
		}
		this.cvtIdGreaterThanOrEqual = cvtIdGreaterThanOrEqual;
		return this;
	}

	public ConvertPageParamQuery cvtIdLessThanOrEqual(Long cvtIdLessThanOrEqual) {
		if (cvtIdLessThanOrEqual == null) {
			throw new RuntimeException("cvtId is null");
		}
		this.cvtIdLessThanOrEqual = cvtIdLessThanOrEqual;
		return this;
	}

	public ConvertPageParamQuery cvtIds(List<Long> cvtIds) {
		if (cvtIds == null) {
			throw new RuntimeException("cvtIds is empty ");
		}
		this.cvtIds = cvtIds;
		return this;
	}

	public ConvertPageParamQuery paramName(String paramName) {
		if (paramName == null) {
			throw new RuntimeException("paramName is null");
		}
		this.paramName = paramName;
		return this;
	}

	public ConvertPageParamQuery paramNameLike(String paramNameLike) {
		if (paramNameLike == null) {
			throw new RuntimeException("paramName is null");
		}
		this.paramNameLike = paramNameLike;
		return this;
	}

	public ConvertPageParamQuery paramNames(List<String> paramNames) {
		if (paramNames == null) {
			throw new RuntimeException("paramNames is empty ");
		}
		this.paramNames = paramNames;
		return this;
	}

	public ConvertPageParamQuery paramCode(String paramCode) {
		if (paramCode == null) {
			throw new RuntimeException("paramCode is null");
		}
		this.paramCode = paramCode;
		return this;
	}

	public ConvertPageParamQuery paramCodeLike(String paramCodeLike) {
		if (paramCodeLike == null) {
			throw new RuntimeException("paramCode is null");
		}
		this.paramCodeLike = paramCodeLike;
		return this;
	}

	public ConvertPageParamQuery paramCodes(List<String> paramCodes) {
		if (paramCodes == null) {
			throw new RuntimeException("paramCodes is empty ");
		}
		this.paramCodes = paramCodes;
		return this;
	}

	public ConvertPageParamQuery paramType(String paramType) {
		if (paramType == null) {
			throw new RuntimeException("paramType is null");
		}
		this.paramType = paramType;
		return this;
	}

	public ConvertPageParamQuery paramTypeLike(String paramTypeLike) {
		if (paramTypeLike == null) {
			throw new RuntimeException("paramType is null");
		}
		this.paramTypeLike = paramTypeLike;
		return this;
	}

	public ConvertPageParamQuery paramTypes(List<String> paramTypes) {
		if (paramTypes == null) {
			throw new RuntimeException("paramTypes is empty ");
		}
		this.paramTypes = paramTypes;
		return this;
	}

	public ConvertPageParamQuery createDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual) {
		if (createDatetimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createDatetime is null");
		}
		this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
		return this;
	}

	public ConvertPageParamQuery createDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual) {
		if (createDatetimeLessThanOrEqual == null) {
			throw new RuntimeException("createDatetime is null");
		}
		this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
		return this;
	}

	public ConvertPageParamQuery modifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual) {
		if (modifyDatetimeGreaterThanOrEqual == null) {
			throw new RuntimeException("modifyDatetime is null");
		}
		this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
		return this;
	}

	public ConvertPageParamQuery modifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual) {
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

			if ("cvtId".equals(sortColumn)) {
				orderBy = "E.CVT_ID_" + a_x;
			}

			if ("paramName".equals(sortColumn)) {
				orderBy = "E.PARAM_NAME_" + a_x;
			}

			if ("paramCode".equals(sortColumn)) {
				orderBy = "E.PARAM_CODE_" + a_x;
			}

			if ("paramType".equals(sortColumn)) {
				orderBy = "E.PARAM_TYPE_" + a_x;
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
		addColumn("cvtParamId", "CVT_PARAM_ID_");
		addColumn("cvtId", "CVT_ID_");
		addColumn("paramName", "PARAM_NAME_");
		addColumn("paramCode", "PARAM_CODE_");
		addColumn("paramType", "PARAM_TYPE_");
		addColumn("createDatetime", "CREATE_DATETIME_");
		addColumn("modifyDatetime", "MODIFY_DATETIME_");
	}

}