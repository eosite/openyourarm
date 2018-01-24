package com.glaf.convert.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ConvertPageTmplQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> cvtIds;
	protected Collection<String> appActorIds;
	protected String fileDotFieldId;
	protected String fileDotFieldIdLike;
	protected List<String> fileDotFieldIds;
	protected String cvtType;
	protected String cvtTypeLike;
	protected List<String> cvtTypes;
	protected String cvtSrcFileName;
	protected String cvtSrcFileNameLike;
	protected List<String> cvtSrcFileNames;
	protected String cvtSrcName;
	protected String cvtSrcNameLike;
	protected List<String> cvtSrcNames;
	protected String cvtDesExt;
	protected String cvtDesExtLike;
	protected List<String> cvtDesExts;
	protected List<Integer> statuss;
	protected Integer effectiveFlag;
	protected Integer effectiveFlagGreaterThanOrEqual;
	protected Integer effectiveFlagLessThanOrEqual;
	protected List<Integer> effectiveFlags;
	protected Integer cvtStatus;
	protected Integer cvtStatusGreaterThanOrEqual;
	protected Integer cvtStatusLessThanOrEqual;
	protected List<Integer> cvtStatuss;
	protected Date createDatetimeGreaterThanOrEqual;
	protected Date createDatetimeLessThanOrEqual;
	protected Date modifyDatetimeGreaterThanOrEqual;
	protected Date modifyDatetimeLessThanOrEqual;

	public ConvertPageTmplQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getFileDotFieldId() {
		return fileDotFieldId;
	}

	public String getFileDotFieldIdLike() {
		if (fileDotFieldIdLike != null && fileDotFieldIdLike.trim().length() > 0) {
			if (!fileDotFieldIdLike.startsWith("%")) {
				fileDotFieldIdLike = "%" + fileDotFieldIdLike;
			}
			if (!fileDotFieldIdLike.endsWith("%")) {
				fileDotFieldIdLike = fileDotFieldIdLike + "%";
			}
		}
		return fileDotFieldIdLike;
	}

	public List<String> getFileDotFieldIds() {
		return fileDotFieldIds;
	}

	public String getCvtType() {
		return cvtType;
	}

	public String getCvtTypeLike() {
		if (cvtTypeLike != null && cvtTypeLike.trim().length() > 0) {
			if (!cvtTypeLike.startsWith("%")) {
				cvtTypeLike = "%" + cvtTypeLike;
			}
			if (!cvtTypeLike.endsWith("%")) {
				cvtTypeLike = cvtTypeLike + "%";
			}
		}
		return cvtTypeLike;
	}

	public List<String> getCvtTypes() {
		return cvtTypes;
	}

	public String getCvtSrcFileName() {
		return cvtSrcFileName;
	}

	public String getCvtSrcFileNameLike() {
		if (cvtSrcFileNameLike != null && cvtSrcFileNameLike.trim().length() > 0) {
			if (!cvtSrcFileNameLike.startsWith("%")) {
				cvtSrcFileNameLike = "%" + cvtSrcFileNameLike;
			}
			if (!cvtSrcFileNameLike.endsWith("%")) {
				cvtSrcFileNameLike = cvtSrcFileNameLike + "%";
			}
		}
		return cvtSrcFileNameLike;
	}

	public List<String> getCvtSrcFileNames() {
		return cvtSrcFileNames;
	}

	public String getCvtSrcName() {
		return cvtSrcName;
	}

	public String getCvtSrcNameLike() {
		if (cvtSrcNameLike != null && cvtSrcNameLike.trim().length() > 0) {
			if (!cvtSrcNameLike.startsWith("%")) {
				cvtSrcNameLike = "%" + cvtSrcNameLike;
			}
			if (!cvtSrcNameLike.endsWith("%")) {
				cvtSrcNameLike = cvtSrcNameLike + "%";
			}
		}
		return cvtSrcNameLike;
	}

	public List<String> getCvtSrcNames() {
		return cvtSrcNames;
	}

	public String getCvtDesExt() {
		return cvtDesExt;
	}

	public String getCvtDesExtLike() {
		if (cvtDesExtLike != null && cvtDesExtLike.trim().length() > 0) {
			if (!cvtDesExtLike.startsWith("%")) {
				cvtDesExtLike = "%" + cvtDesExtLike;
			}
			if (!cvtDesExtLike.endsWith("%")) {
				cvtDesExtLike = cvtDesExtLike + "%";
			}
		}
		return cvtDesExtLike;
	}

	public List<String> getCvtDesExts() {
		return cvtDesExts;
	}

	public Integer getStatus() {
		return status;
	}

	public Integer getStatusGreaterThanOrEqual() {
		return statusGreaterThanOrEqual;
	}

	public Integer getStatusLessThanOrEqual() {
		return statusLessThanOrEqual;
	}

	public List<Integer> getStatuss() {
		return statuss;
	}

	public Integer getEffectiveFlag() {
		return effectiveFlag;
	}

	public Integer getEffectiveFlagGreaterThanOrEqual() {
		return effectiveFlagGreaterThanOrEqual;
	}

	public Integer getEffectiveFlagLessThanOrEqual() {
		return effectiveFlagLessThanOrEqual;
	}

	public List<Integer> getEffectiveFlags() {
		return effectiveFlags;
	}

	public Integer getCvtStatus() {
		return cvtStatus;
	}

	public Integer getCvtStatusGreaterThanOrEqual() {
		return cvtStatusGreaterThanOrEqual;
	}

	public Integer getCvtStatusLessThanOrEqual() {
		return cvtStatusLessThanOrEqual;
	}

	public List<Integer> getCvtStatuss() {
		return cvtStatuss;
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

	public void setFileDotFieldId(String fileDotFieldId) {
		this.fileDotFieldId = fileDotFieldId;
	}

	public void setFileDotFieldIdLike(String fileDotFieldIdLike) {
		this.fileDotFieldIdLike = fileDotFieldIdLike;
	}

	public void setFileDotFieldIds(List<String> fileDotFieldIds) {
		this.fileDotFieldIds = fileDotFieldIds;
	}

	public void setCvtType(String cvtType) {
		this.cvtType = cvtType;
	}

	public void setCvtTypeLike(String cvtTypeLike) {
		this.cvtTypeLike = cvtTypeLike;
	}

	public void setCvtTypes(List<String> cvtTypes) {
		this.cvtTypes = cvtTypes;
	}

	public void setCvtSrcFileName(String cvtSrcFileName) {
		this.cvtSrcFileName = cvtSrcFileName;
	}

	public void setCvtSrcFileNameLike(String cvtSrcFileNameLike) {
		this.cvtSrcFileNameLike = cvtSrcFileNameLike;
	}

	public void setCvtSrcFileNames(List<String> cvtSrcFileNames) {
		this.cvtSrcFileNames = cvtSrcFileNames;
	}

	public void setCvtSrcName(String cvtSrcName) {
		this.cvtSrcName = cvtSrcName;
	}

	public void setCvtSrcNameLike(String cvtSrcNameLike) {
		this.cvtSrcNameLike = cvtSrcNameLike;
	}

	public void setCvtSrcNames(List<String> cvtSrcNames) {
		this.cvtSrcNames = cvtSrcNames;
	}

	public void setCvtDesExt(String cvtDesExt) {
		this.cvtDesExt = cvtDesExt;
	}

	public void setCvtDesExtLike(String cvtDesExtLike) {
		this.cvtDesExtLike = cvtDesExtLike;
	}

	public void setCvtDesExts(List<String> cvtDesExts) {
		this.cvtDesExts = cvtDesExts;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setStatusGreaterThanOrEqual(Integer statusGreaterThanOrEqual) {
		this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
	}

	public void setStatusLessThanOrEqual(Integer statusLessThanOrEqual) {
		this.statusLessThanOrEqual = statusLessThanOrEqual;
	}

	public void setStatuss(List<Integer> statuss) {
		this.statuss = statuss;
	}

	public void setEffectiveFlag(Integer effectiveFlag) {
		this.effectiveFlag = effectiveFlag;
	}

	public void setEffectiveFlagGreaterThanOrEqual(Integer effectiveFlagGreaterThanOrEqual) {
		this.effectiveFlagGreaterThanOrEqual = effectiveFlagGreaterThanOrEqual;
	}

	public void setEffectiveFlagLessThanOrEqual(Integer effectiveFlagLessThanOrEqual) {
		this.effectiveFlagLessThanOrEqual = effectiveFlagLessThanOrEqual;
	}

	public void setEffectiveFlags(List<Integer> effectiveFlags) {
		this.effectiveFlags = effectiveFlags;
	}

	public void setCvtStatus(Integer cvtStatus) {
		this.cvtStatus = cvtStatus;
	}

	public void setCvtStatusGreaterThanOrEqual(Integer cvtStatusGreaterThanOrEqual) {
		this.cvtStatusGreaterThanOrEqual = cvtStatusGreaterThanOrEqual;
	}

	public void setCvtStatusLessThanOrEqual(Integer cvtStatusLessThanOrEqual) {
		this.cvtStatusLessThanOrEqual = cvtStatusLessThanOrEqual;
	}

	public void setCvtStatuss(List<Integer> cvtStatuss) {
		this.cvtStatuss = cvtStatuss;
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

	public ConvertPageTmplQuery fileDotFieldId(String fileDotFieldId) {
		if (fileDotFieldId == null) {
			throw new RuntimeException("fileDotFieldId is null");
		}
		this.fileDotFieldId = fileDotFieldId;
		return this;
	}

	public ConvertPageTmplQuery fileDotFieldIdLike(String fileDotFieldIdLike) {
		if (fileDotFieldIdLike == null) {
			throw new RuntimeException("fileDotFieldId is null");
		}
		this.fileDotFieldIdLike = fileDotFieldIdLike;
		return this;
	}

	public ConvertPageTmplQuery fileDotFieldIds(List<String> fileDotFieldIds) {
		if (fileDotFieldIds == null) {
			throw new RuntimeException("fileDotFieldIds is empty ");
		}
		this.fileDotFieldIds = fileDotFieldIds;
		return this;
	}

	public ConvertPageTmplQuery cvtType(String cvtType) {
		if (cvtType == null) {
			throw new RuntimeException("cvtType is null");
		}
		this.cvtType = cvtType;
		return this;
	}

	public ConvertPageTmplQuery cvtTypeLike(String cvtTypeLike) {
		if (cvtTypeLike == null) {
			throw new RuntimeException("cvtType is null");
		}
		this.cvtTypeLike = cvtTypeLike;
		return this;
	}

	public ConvertPageTmplQuery cvtTypes(List<String> cvtTypes) {
		if (cvtTypes == null) {
			throw new RuntimeException("cvtTypes is empty ");
		}
		this.cvtTypes = cvtTypes;
		return this;
	}

	public ConvertPageTmplQuery cvtSrcFileName(String cvtSrcFileName) {
		if (cvtSrcFileName == null) {
			throw new RuntimeException("cvtSrcFileName is null");
		}
		this.cvtSrcFileName = cvtSrcFileName;
		return this;
	}

	public ConvertPageTmplQuery cvtSrcFileNameLike(String cvtSrcFileNameLike) {
		if (cvtSrcFileNameLike == null) {
			throw new RuntimeException("cvtSrcFileName is null");
		}
		this.cvtSrcFileNameLike = cvtSrcFileNameLike;
		return this;
	}

	public ConvertPageTmplQuery cvtSrcFileNames(List<String> cvtSrcFileNames) {
		if (cvtSrcFileNames == null) {
			throw new RuntimeException("cvtSrcFileNames is empty ");
		}
		this.cvtSrcFileNames = cvtSrcFileNames;
		return this;
	}

	public ConvertPageTmplQuery cvtSrcName(String cvtSrcName) {
		if (cvtSrcName == null) {
			throw new RuntimeException("cvtSrcName is null");
		}
		this.cvtSrcName = cvtSrcName;
		return this;
	}

	public ConvertPageTmplQuery cvtSrcNameLike(String cvtSrcNameLike) {
		if (cvtSrcNameLike == null) {
			throw new RuntimeException("cvtSrcName is null");
		}
		this.cvtSrcNameLike = cvtSrcNameLike;
		return this;
	}

	public ConvertPageTmplQuery cvtSrcNames(List<String> cvtSrcNames) {
		if (cvtSrcNames == null) {
			throw new RuntimeException("cvtSrcNames is empty ");
		}
		this.cvtSrcNames = cvtSrcNames;
		return this;
	}

	public ConvertPageTmplQuery cvtDesExt(String cvtDesExt) {
		if (cvtDesExt == null) {
			throw new RuntimeException("cvtDesExt is null");
		}
		this.cvtDesExt = cvtDesExt;
		return this;
	}

	public ConvertPageTmplQuery cvtDesExtLike(String cvtDesExtLike) {
		if (cvtDesExtLike == null) {
			throw new RuntimeException("cvtDesExt is null");
		}
		this.cvtDesExtLike = cvtDesExtLike;
		return this;
	}

	public ConvertPageTmplQuery cvtDesExts(List<String> cvtDesExts) {
		if (cvtDesExts == null) {
			throw new RuntimeException("cvtDesExts is empty ");
		}
		this.cvtDesExts = cvtDesExts;
		return this;
	}

	public ConvertPageTmplQuery status(Integer status) {
		if (status == null) {
			throw new RuntimeException("status is null");
		}
		this.status = status;
		return this;
	}

	public ConvertPageTmplQuery statusGreaterThanOrEqual(Integer statusGreaterThanOrEqual) {
		if (statusGreaterThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
		return this;
	}

	public ConvertPageTmplQuery statusLessThanOrEqual(Integer statusLessThanOrEqual) {
		if (statusLessThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusLessThanOrEqual = statusLessThanOrEqual;
		return this;
	}

	public ConvertPageTmplQuery statuss(List<Integer> statuss) {
		if (statuss == null) {
			throw new RuntimeException("statuss is empty ");
		}
		this.statuss = statuss;
		return this;
	}

	public ConvertPageTmplQuery effectiveFlag(Integer effectiveFlag) {
		if (effectiveFlag == null) {
			throw new RuntimeException("effectiveFlag is null");
		}
		this.effectiveFlag = effectiveFlag;
		return this;
	}

	public ConvertPageTmplQuery effectiveFlagGreaterThanOrEqual(Integer effectiveFlagGreaterThanOrEqual) {
		if (effectiveFlagGreaterThanOrEqual == null) {
			throw new RuntimeException("effectiveFlag is null");
		}
		this.effectiveFlagGreaterThanOrEqual = effectiveFlagGreaterThanOrEqual;
		return this;
	}

	public ConvertPageTmplQuery effectiveFlagLessThanOrEqual(Integer effectiveFlagLessThanOrEqual) {
		if (effectiveFlagLessThanOrEqual == null) {
			throw new RuntimeException("effectiveFlag is null");
		}
		this.effectiveFlagLessThanOrEqual = effectiveFlagLessThanOrEqual;
		return this;
	}

	public ConvertPageTmplQuery effectiveFlags(List<Integer> effectiveFlags) {
		if (effectiveFlags == null) {
			throw new RuntimeException("effectiveFlags is empty ");
		}
		this.effectiveFlags = effectiveFlags;
		return this;
	}

	public ConvertPageTmplQuery cvtStatus(Integer cvtStatus) {
		if (cvtStatus == null) {
			throw new RuntimeException("cvtStatus is null");
		}
		this.cvtStatus = cvtStatus;
		return this;
	}

	public ConvertPageTmplQuery cvtStatusGreaterThanOrEqual(Integer cvtStatusGreaterThanOrEqual) {
		if (cvtStatusGreaterThanOrEqual == null) {
			throw new RuntimeException("cvtStatus is null");
		}
		this.cvtStatusGreaterThanOrEqual = cvtStatusGreaterThanOrEqual;
		return this;
	}

	public ConvertPageTmplQuery cvtStatusLessThanOrEqual(Integer cvtStatusLessThanOrEqual) {
		if (cvtStatusLessThanOrEqual == null) {
			throw new RuntimeException("cvtStatus is null");
		}
		this.cvtStatusLessThanOrEqual = cvtStatusLessThanOrEqual;
		return this;
	}

	public ConvertPageTmplQuery cvtStatuss(List<Integer> cvtStatuss) {
		if (cvtStatuss == null) {
			throw new RuntimeException("cvtStatuss is empty ");
		}
		this.cvtStatuss = cvtStatuss;
		return this;
	}

	public ConvertPageTmplQuery createDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual) {
		if (createDatetimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createDatetime is null");
		}
		this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
		return this;
	}

	public ConvertPageTmplQuery createDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual) {
		if (createDatetimeLessThanOrEqual == null) {
			throw new RuntimeException("createDatetime is null");
		}
		this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
		return this;
	}

	public ConvertPageTmplQuery modifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual) {
		if (modifyDatetimeGreaterThanOrEqual == null) {
			throw new RuntimeException("modifyDatetime is null");
		}
		this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
		return this;
	}

	public ConvertPageTmplQuery modifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual) {
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

			if ("fileDotFieldId".equals(sortColumn)) {
				orderBy = "E.FILEDOT_FIELD_ID_" + a_x;
			}

			if ("cvtType".equals(sortColumn)) {
				orderBy = "E.CVT_TYPE_" + a_x;
			}

			if ("cvtSrcContent".equals(sortColumn)) {
				orderBy = "E.CVT_SRC_CONTENT_" + a_x;
			}

			if ("cvtSrcFileName".equals(sortColumn)) {
				orderBy = "E.CVT_SRC_FILENAME_" + a_x;
			}

			if ("cvtSrcName".equals(sortColumn)) {
				orderBy = "E.CVT_SRC_NAME_" + a_x;
			}

			if ("cvtXmlContent".equals(sortColumn)) {
				orderBy = "E.CVT_XML_CONTENT_" + a_x;
			}

			if ("cvtDesContent".equals(sortColumn)) {
				orderBy = "E.CVT_DES_CONTENT_" + a_x;
			}

			if ("cvtDesExt".equals(sortColumn)) {
				orderBy = "E.CVT_DES_EXT_" + a_x;
			}

			if ("status".equals(sortColumn)) {
				orderBy = "E.STATUS_" + a_x;
			}

			if ("effectiveFlag".equals(sortColumn)) {
				orderBy = "E.EFFECTIVE_FLAG_" + a_x;
			}

			if ("cvtStatus".equals(sortColumn)) {
				orderBy = "E.CVT_STATUS_" + a_x;
			}

			if ("createDatetime".equals(sortColumn)) {
				orderBy = "E.CREAT_DATETIME_" + a_x;
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
		addColumn("cvtId", "CVT_ID_");
		addColumn("fileDotFieldId", "FILEDOT_FIELD_ID_");
		addColumn("cvtType", "CVT_TYPE_");
		addColumn("cvtSrcFileName", "CVT_SRC_FILENAME_");
		addColumn("cvtSrcName", "CVT_SRC_NAME_");
		addColumn("cvtDesExt", "CVT_DES_EXT_");
		addColumn("status", "STATUS_");
		addColumn("effectiveFlag", "EFFECTIVE_FLAG_");
		addColumn("cvtStatus", "CVT_STATUS_");
		addColumn("createDatetime", "CREAT_DATETIME_");
		addColumn("modifyDatetime", "MODIFY_DATETIME_");
	}

}