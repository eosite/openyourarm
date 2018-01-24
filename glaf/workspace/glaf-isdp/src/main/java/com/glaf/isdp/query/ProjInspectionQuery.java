package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ProjInspectionQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected Integer indexId;
	protected Integer indexIdGreaterThanOrEqual;
	protected Integer indexIdLessThanOrEqual;
	protected List<Integer> indexIds;
	protected Integer intFlag;
	protected Integer intFlagGreaterThanOrEqual;
	protected Integer intFlagLessThanOrEqual;
	protected List<Integer> intFlags;
	protected String cellTmpFileTypeId;
	protected String cellTmpFileTypeIdLike;
	protected List<String> cellTmpFileTypeIds;
	protected Integer listNo;
	protected Integer listNoGreaterThanOrEqual;
	protected Integer listNoLessThanOrEqual;
	protected List<Integer> listNos;
	protected Integer chkResult;
	protected Integer chkResultGreaterThanOrEqual;
	protected Integer chkResultLessThanOrEqual;
	protected List<Integer> chkResults;
	protected String pfileId;
	protected String pfileIdLike;
	protected List<String> pfileIds;
	protected Integer refillFlag;
	protected Integer refillFlagGreaterThanOrEqual;
	protected Integer refillFlagLessThanOrEqual;
	protected List<Integer> refillFlags;
	protected Integer groupId;
	protected Integer groupIdGreaterThanOrEqual;
	protected Integer groupIdLessThanOrEqual;
	protected List<Integer> groupIds;
	protected String oldId;
	protected String oldIdLike;
	protected List<String> oldIds;
	protected String emailId;
	protected String emailIdLike;
	protected List<String> emailIds;
	protected String recemailId;
	protected String recemailIdLike;
	protected List<String> recemailIds;
	protected String mainId;
	protected String mainIdLike;
	protected List<String> mainIds;
	protected String tagNum;
	protected String tagNumLike;
	protected List<String> tagNums;
	protected Date ctimeGreaterThanOrEqual;
	protected Date ctimeLessThanOrEqual;
	protected String tname;
	protected String tnameLike;
	protected List<String> tnames;
	protected Integer page;
	protected Integer pageGreaterThanOrEqual;
	protected Integer pageLessThanOrEqual;
	protected List<Integer> pages;
	protected String duty;
	protected String dutyLike;
	protected List<String> dutys;
	protected String thematic;
	protected String thematicLike;
	protected List<String> thematics;
	protected String annotations;
	protected String annotationsLike;
	protected List<String> annotationss;

	public ProjInspectionQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public Integer getIndexId() {
		return indexId;
	}

	public Integer getIndexIdGreaterThanOrEqual() {
		return indexIdGreaterThanOrEqual;
	}

	public Integer getIndexIdLessThanOrEqual() {
		return indexIdLessThanOrEqual;
	}

	public List<Integer> getIndexIds() {
		return indexIds;
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

	public String getCellTmpFileTypeId() {
		return cellTmpFileTypeId;
	}

	public String getCellTmpFileTypeIdLike() {
		if (cellTmpFileTypeIdLike != null
				&& cellTmpFileTypeIdLike.trim().length() > 0) {
			if (!cellTmpFileTypeIdLike.startsWith("%")) {
				cellTmpFileTypeIdLike = "%" + cellTmpFileTypeIdLike;
			}
			if (!cellTmpFileTypeIdLike.endsWith("%")) {
				cellTmpFileTypeIdLike = cellTmpFileTypeIdLike + "%";
			}
		}
		return cellTmpFileTypeIdLike;
	}

	public List<String> getCellTmpFileTypeIds() {
		return cellTmpFileTypeIds;
	}

	public Integer getListNo() {
		return listNo;
	}

	public Integer getListNoGreaterThanOrEqual() {
		return listNoGreaterThanOrEqual;
	}

	public Integer getListNoLessThanOrEqual() {
		return listNoLessThanOrEqual;
	}

	public List<Integer> getListNos() {
		return listNos;
	}

	public Integer getChkResult() {
		return chkResult;
	}

	public Integer getChkResultGreaterThanOrEqual() {
		return chkResultGreaterThanOrEqual;
	}

	public Integer getChkResultLessThanOrEqual() {
		return chkResultLessThanOrEqual;
	}

	public List<Integer> getChkResults() {
		return chkResults;
	}

	public String getPfileId() {
		return pfileId;
	}

	public String getPfileIdLike() {
		if (pfileIdLike != null && pfileIdLike.trim().length() > 0) {
			if (!pfileIdLike.startsWith("%")) {
				pfileIdLike = "%" + pfileIdLike;
			}
			if (!pfileIdLike.endsWith("%")) {
				pfileIdLike = pfileIdLike + "%";
			}
		}
		return pfileIdLike;
	}

	public List<String> getPfileIds() {
		return pfileIds;
	}

	public Integer getRefillFlag() {
		return refillFlag;
	}

	public Integer getRefillFlagGreaterThanOrEqual() {
		return refillFlagGreaterThanOrEqual;
	}

	public Integer getRefillFlagLessThanOrEqual() {
		return refillFlagLessThanOrEqual;
	}

	public List<Integer> getRefillFlags() {
		return refillFlags;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public Integer getGroupIdGreaterThanOrEqual() {
		return groupIdGreaterThanOrEqual;
	}

	public Integer getGroupIdLessThanOrEqual() {
		return groupIdLessThanOrEqual;
	}

	public List<Integer> getGroupIds() {
		return groupIds;
	}

	public String getOldId() {
		return oldId;
	}

	public String getOldIdLike() {
		if (oldIdLike != null && oldIdLike.trim().length() > 0) {
			if (!oldIdLike.startsWith("%")) {
				oldIdLike = "%" + oldIdLike;
			}
			if (!oldIdLike.endsWith("%")) {
				oldIdLike = oldIdLike + "%";
			}
		}
		return oldIdLike;
	}

	public List<String> getOldIds() {
		return oldIds;
	}

	public String getEmailId() {
		return emailId;
	}

	public String getEmailIdLike() {
		if (emailIdLike != null && emailIdLike.trim().length() > 0) {
			if (!emailIdLike.startsWith("%")) {
				emailIdLike = "%" + emailIdLike;
			}
			if (!emailIdLike.endsWith("%")) {
				emailIdLike = emailIdLike + "%";
			}
		}
		return emailIdLike;
	}

	public List<String> getEmailIds() {
		return emailIds;
	}

	public String getRecemailId() {
		return recemailId;
	}

	public String getRecemailIdLike() {
		if (recemailIdLike != null && recemailIdLike.trim().length() > 0) {
			if (!recemailIdLike.startsWith("%")) {
				recemailIdLike = "%" + recemailIdLike;
			}
			if (!recemailIdLike.endsWith("%")) {
				recemailIdLike = recemailIdLike + "%";
			}
		}
		return recemailIdLike;
	}

	public List<String> getRecemailIds() {
		return recemailIds;
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

	public String getTagNum() {
		return tagNum;
	}

	public String getTagNumLike() {
		if (tagNumLike != null && tagNumLike.trim().length() > 0) {
			if (!tagNumLike.startsWith("%")) {
				tagNumLike = "%" + tagNumLike;
			}
			if (!tagNumLike.endsWith("%")) {
				tagNumLike = tagNumLike + "%";
			}
		}
		return tagNumLike;
	}

	public List<String> getTagNums() {
		return tagNums;
	}

	public Date getCtimeGreaterThanOrEqual() {
		return ctimeGreaterThanOrEqual;
	}

	public Date getCtimeLessThanOrEqual() {
		return ctimeLessThanOrEqual;
	}

	public String getTname() {
		return tname;
	}

	public String getTnameLike() {
		if (tnameLike != null && tnameLike.trim().length() > 0) {
			if (!tnameLike.startsWith("%")) {
				tnameLike = "%" + tnameLike;
			}
			if (!tnameLike.endsWith("%")) {
				tnameLike = tnameLike + "%";
			}
		}
		return tnameLike;
	}

	public List<String> getTnames() {
		return tnames;
	}

	public Integer getPage() {
		return page;
	}

	public Integer getPageGreaterThanOrEqual() {
		return pageGreaterThanOrEqual;
	}

	public Integer getPageLessThanOrEqual() {
		return pageLessThanOrEqual;
	}

	public List<Integer> getPages() {
		return pages;
	}

	public String getDuty() {
		return duty;
	}

	public String getDutyLike() {
		if (dutyLike != null && dutyLike.trim().length() > 0) {
			if (!dutyLike.startsWith("%")) {
				dutyLike = "%" + dutyLike;
			}
			if (!dutyLike.endsWith("%")) {
				dutyLike = dutyLike + "%";
			}
		}
		return dutyLike;
	}

	public List<String> getDutys() {
		return dutys;
	}

	public String getThematic() {
		return thematic;
	}

	public String getThematicLike() {
		if (thematicLike != null && thematicLike.trim().length() > 0) {
			if (!thematicLike.startsWith("%")) {
				thematicLike = "%" + thematicLike;
			}
			if (!thematicLike.endsWith("%")) {
				thematicLike = thematicLike + "%";
			}
		}
		return thematicLike;
	}

	public List<String> getThematics() {
		return thematics;
	}

	public String getAnnotations() {
		return annotations;
	}

	public String getAnnotationsLike() {
		if (annotationsLike != null && annotationsLike.trim().length() > 0) {
			if (!annotationsLike.startsWith("%")) {
				annotationsLike = "%" + annotationsLike;
			}
			if (!annotationsLike.endsWith("%")) {
				annotationsLike = annotationsLike + "%";
			}
		}
		return annotationsLike;
	}

	public List<String> getAnnotationss() {
		return annotationss;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public void setIndexIdGreaterThanOrEqual(Integer indexIdGreaterThanOrEqual) {
		this.indexIdGreaterThanOrEqual = indexIdGreaterThanOrEqual;
	}

	public void setIndexIdLessThanOrEqual(Integer indexIdLessThanOrEqual) {
		this.indexIdLessThanOrEqual = indexIdLessThanOrEqual;
	}

	public void setIndexIds(List<Integer> indexIds) {
		this.indexIds = indexIds;
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

	public void setCellTmpFileTypeId(String cellTmpFileTypeId) {
		this.cellTmpFileTypeId = cellTmpFileTypeId;
	}

	public void setCellTmpFileTypeIdLike(String cellTmpFileTypeIdLike) {
		this.cellTmpFileTypeIdLike = cellTmpFileTypeIdLike;
	}

	public void setCellTmpFileTypeIds(List<String> cellTmpFileTypeIds) {
		this.cellTmpFileTypeIds = cellTmpFileTypeIds;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public void setListNoGreaterThanOrEqual(Integer listNoGreaterThanOrEqual) {
		this.listNoGreaterThanOrEqual = listNoGreaterThanOrEqual;
	}

	public void setListNoLessThanOrEqual(Integer listNoLessThanOrEqual) {
		this.listNoLessThanOrEqual = listNoLessThanOrEqual;
	}

	public void setListNos(List<Integer> listNos) {
		this.listNos = listNos;
	}

	public void setChkResult(Integer chkResult) {
		this.chkResult = chkResult;
	}

	public void setChkResultGreaterThanOrEqual(
			Integer chkResultGreaterThanOrEqual) {
		this.chkResultGreaterThanOrEqual = chkResultGreaterThanOrEqual;
	}

	public void setChkResultLessThanOrEqual(Integer chkResultLessThanOrEqual) {
		this.chkResultLessThanOrEqual = chkResultLessThanOrEqual;
	}

	public void setChkResults(List<Integer> chkResults) {
		this.chkResults = chkResults;
	}

	public void setPfileId(String pfileId) {
		this.pfileId = pfileId;
	}

	public void setPfileIdLike(String pfileIdLike) {
		this.pfileIdLike = pfileIdLike;
	}

	public void setPfileIds(List<String> pfileIds) {
		this.pfileIds = pfileIds;
	}

	public void setRefillFlag(Integer refillFlag) {
		this.refillFlag = refillFlag;
	}

	public void setRefillFlagGreaterThanOrEqual(
			Integer refillFlagGreaterThanOrEqual) {
		this.refillFlagGreaterThanOrEqual = refillFlagGreaterThanOrEqual;
	}

	public void setRefillFlagLessThanOrEqual(Integer refillFlagLessThanOrEqual) {
		this.refillFlagLessThanOrEqual = refillFlagLessThanOrEqual;
	}

	public void setRefillFlags(List<Integer> refillFlags) {
		this.refillFlags = refillFlags;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public void setGroupIdGreaterThanOrEqual(Integer groupIdGreaterThanOrEqual) {
		this.groupIdGreaterThanOrEqual = groupIdGreaterThanOrEqual;
	}

	public void setGroupIdLessThanOrEqual(Integer groupIdLessThanOrEqual) {
		this.groupIdLessThanOrEqual = groupIdLessThanOrEqual;
	}

	public void setGroupIds(List<Integer> groupIds) {
		this.groupIds = groupIds;
	}

	public void setOldId(String oldId) {
		this.oldId = oldId;
	}

	public void setOldIdLike(String oldIdLike) {
		this.oldIdLike = oldIdLike;
	}

	public void setOldIds(List<String> oldIds) {
		this.oldIds = oldIds;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public void setEmailIdLike(String emailIdLike) {
		this.emailIdLike = emailIdLike;
	}

	public void setEmailIds(List<String> emailIds) {
		this.emailIds = emailIds;
	}

	public void setRecemailId(String recemailId) {
		this.recemailId = recemailId;
	}

	public void setRecemailIdLike(String recemailIdLike) {
		this.recemailIdLike = recemailIdLike;
	}

	public void setRecemailIds(List<String> recemailIds) {
		this.recemailIds = recemailIds;
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

	public void setTagNum(String tagNum) {
		this.tagNum = tagNum;
	}

	public void setTagNumLike(String tagNumLike) {
		this.tagNumLike = tagNumLike;
	}

	public void setTagNums(List<String> tagNums) {
		this.tagNums = tagNums;
	}

	public void setCtimeGreaterThanOrEqual(Date ctimeGreaterThanOrEqual) {
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
	}

	public void setCtimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public void setTnameLike(String tnameLike) {
		this.tnameLike = tnameLike;
	}

	public void setTnames(List<String> tnames) {
		this.tnames = tnames;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setPageGreaterThanOrEqual(Integer pageGreaterThanOrEqual) {
		this.pageGreaterThanOrEqual = pageGreaterThanOrEqual;
	}

	public void setPageLessThanOrEqual(Integer pageLessThanOrEqual) {
		this.pageLessThanOrEqual = pageLessThanOrEqual;
	}

	public void setPages(List<Integer> pages) {
		this.pages = pages;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public void setDutyLike(String dutyLike) {
		this.dutyLike = dutyLike;
	}

	public void setDutys(List<String> dutys) {
		this.dutys = dutys;
	}

	public void setThematic(String thematic) {
		this.thematic = thematic;
	}

	public void setThematicLike(String thematicLike) {
		this.thematicLike = thematicLike;
	}

	public void setThematics(List<String> thematics) {
		this.thematics = thematics;
	}

	public void setAnnotations(String annotations) {
		this.annotations = annotations;
	}

	public void setAnnotationsLike(String annotationsLike) {
		this.annotationsLike = annotationsLike;
	}

	public void setAnnotationss(List<String> annotationss) {
		this.annotationss = annotationss;
	}

	public ProjInspectionQuery indexId(Integer indexId) {
		if (indexId == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexId = indexId;
		return this;
	}

	public ProjInspectionQuery indexIdGreaterThanOrEqual(
			Integer indexIdGreaterThanOrEqual) {
		if (indexIdGreaterThanOrEqual == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexIdGreaterThanOrEqual = indexIdGreaterThanOrEqual;
		return this;
	}

	public ProjInspectionQuery indexIdLessThanOrEqual(
			Integer indexIdLessThanOrEqual) {
		if (indexIdLessThanOrEqual == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexIdLessThanOrEqual = indexIdLessThanOrEqual;
		return this;
	}

	public ProjInspectionQuery indexIds(List<Integer> indexIds) {
		if (indexIds == null) {
			throw new RuntimeException("indexIds is empty ");
		}
		this.indexIds = indexIds;
		return this;
	}

	public ProjInspectionQuery intFlag(Integer intFlag) {
		if (intFlag == null) {
			throw new RuntimeException("intFlag is null");
		}
		this.intFlag = intFlag;
		return this;
	}

	public ProjInspectionQuery intFlagGreaterThanOrEqual(
			Integer intFlagGreaterThanOrEqual) {
		if (intFlagGreaterThanOrEqual == null) {
			throw new RuntimeException("intFlag is null");
		}
		this.intFlagGreaterThanOrEqual = intFlagGreaterThanOrEqual;
		return this;
	}

	public ProjInspectionQuery intFlagLessThanOrEqual(
			Integer intFlagLessThanOrEqual) {
		if (intFlagLessThanOrEqual == null) {
			throw new RuntimeException("intFlag is null");
		}
		this.intFlagLessThanOrEqual = intFlagLessThanOrEqual;
		return this;
	}

	public ProjInspectionQuery intFlags(List<Integer> intFlags) {
		if (intFlags == null) {
			throw new RuntimeException("intFlags is empty ");
		}
		this.intFlags = intFlags;
		return this;
	}

	public ProjInspectionQuery cellTmpFileTypeId(String cellTmpFileTypeId) {
		if (cellTmpFileTypeId == null) {
			throw new RuntimeException("cellTmpFileTypeId is null");
		}
		this.cellTmpFileTypeId = cellTmpFileTypeId;
		return this;
	}

	public ProjInspectionQuery cellTmpFileTypeIdLike(
			String cellTmpFileTypeIdLike) {
		if (cellTmpFileTypeIdLike == null) {
			throw new RuntimeException("cellTmpFileTypeId is null");
		}
		this.cellTmpFileTypeIdLike = cellTmpFileTypeIdLike;
		return this;
	}

	public ProjInspectionQuery cellTmpFileTypeIds(
			List<String> cellTmpFileTypeIds) {
		if (cellTmpFileTypeIds == null) {
			throw new RuntimeException("cellTmpFileTypeIds is empty ");
		}
		this.cellTmpFileTypeIds = cellTmpFileTypeIds;
		return this;
	}

	public ProjInspectionQuery listNo(Integer listNo) {
		if (listNo == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNo = listNo;
		return this;
	}

	public ProjInspectionQuery listNoGreaterThanOrEqual(
			Integer listNoGreaterThanOrEqual) {
		if (listNoGreaterThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoGreaterThanOrEqual = listNoGreaterThanOrEqual;
		return this;
	}

	public ProjInspectionQuery listNoLessThanOrEqual(
			Integer listNoLessThanOrEqual) {
		if (listNoLessThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoLessThanOrEqual = listNoLessThanOrEqual;
		return this;
	}

	public ProjInspectionQuery listNos(List<Integer> listNos) {
		if (listNos == null) {
			throw new RuntimeException("listNos is empty ");
		}
		this.listNos = listNos;
		return this;
	}

	public ProjInspectionQuery chkResult(Integer chkResult) {
		if (chkResult == null) {
			throw new RuntimeException("chkResult is null");
		}
		this.chkResult = chkResult;
		return this;
	}

	public ProjInspectionQuery chkResultGreaterThanOrEqual(
			Integer chkResultGreaterThanOrEqual) {
		if (chkResultGreaterThanOrEqual == null) {
			throw new RuntimeException("chkResult is null");
		}
		this.chkResultGreaterThanOrEqual = chkResultGreaterThanOrEqual;
		return this;
	}

	public ProjInspectionQuery chkResultLessThanOrEqual(
			Integer chkResultLessThanOrEqual) {
		if (chkResultLessThanOrEqual == null) {
			throw new RuntimeException("chkResult is null");
		}
		this.chkResultLessThanOrEqual = chkResultLessThanOrEqual;
		return this;
	}

	public ProjInspectionQuery chkResults(List<Integer> chkResults) {
		if (chkResults == null) {
			throw new RuntimeException("chkResults is empty ");
		}
		this.chkResults = chkResults;
		return this;
	}

	public ProjInspectionQuery pfileId(String pfileId) {
		if (pfileId == null) {
			throw new RuntimeException("pfileId is null");
		}
		this.pfileId = pfileId;
		return this;
	}

	public ProjInspectionQuery pfileIdLike(String pfileIdLike) {
		if (pfileIdLike == null) {
			throw new RuntimeException("pfileId is null");
		}
		this.pfileIdLike = pfileIdLike;
		return this;
	}

	public ProjInspectionQuery pfileIds(List<String> pfileIds) {
		if (pfileIds == null) {
			throw new RuntimeException("pfileIds is empty ");
		}
		this.pfileIds = pfileIds;
		return this;
	}

	public ProjInspectionQuery refillFlag(Integer refillFlag) {
		if (refillFlag == null) {
			throw new RuntimeException("refillFlag is null");
		}
		this.refillFlag = refillFlag;
		return this;
	}

	public ProjInspectionQuery refillFlagGreaterThanOrEqual(
			Integer refillFlagGreaterThanOrEqual) {
		if (refillFlagGreaterThanOrEqual == null) {
			throw new RuntimeException("refillFlag is null");
		}
		this.refillFlagGreaterThanOrEqual = refillFlagGreaterThanOrEqual;
		return this;
	}

	public ProjInspectionQuery refillFlagLessThanOrEqual(
			Integer refillFlagLessThanOrEqual) {
		if (refillFlagLessThanOrEqual == null) {
			throw new RuntimeException("refillFlag is null");
		}
		this.refillFlagLessThanOrEqual = refillFlagLessThanOrEqual;
		return this;
	}

	public ProjInspectionQuery refillFlags(List<Integer> refillFlags) {
		if (refillFlags == null) {
			throw new RuntimeException("refillFlags is empty ");
		}
		this.refillFlags = refillFlags;
		return this;
	}

	public ProjInspectionQuery groupId(Integer groupId) {
		if (groupId == null) {
			throw new RuntimeException("groupId is null");
		}
		this.groupId = groupId;
		return this;
	}

	public ProjInspectionQuery groupIdGreaterThanOrEqual(
			Integer groupIdGreaterThanOrEqual) {
		if (groupIdGreaterThanOrEqual == null) {
			throw new RuntimeException("groupId is null");
		}
		this.groupIdGreaterThanOrEqual = groupIdGreaterThanOrEqual;
		return this;
	}

	public ProjInspectionQuery groupIdLessThanOrEqual(
			Integer groupIdLessThanOrEqual) {
		if (groupIdLessThanOrEqual == null) {
			throw new RuntimeException("groupId is null");
		}
		this.groupIdLessThanOrEqual = groupIdLessThanOrEqual;
		return this;
	}

	public ProjInspectionQuery groupIds(List<Integer> groupIds) {
		if (groupIds == null) {
			throw new RuntimeException("groupIds is empty ");
		}
		this.groupIds = groupIds;
		return this;
	}

	public ProjInspectionQuery oldId(String oldId) {
		if (oldId == null) {
			throw new RuntimeException("oldId is null");
		}
		this.oldId = oldId;
		return this;
	}

	public ProjInspectionQuery oldIdLike(String oldIdLike) {
		if (oldIdLike == null) {
			throw new RuntimeException("oldId is null");
		}
		this.oldIdLike = oldIdLike;
		return this;
	}

	public ProjInspectionQuery oldIds(List<String> oldIds) {
		if (oldIds == null) {
			throw new RuntimeException("oldIds is empty ");
		}
		this.oldIds = oldIds;
		return this;
	}

	public ProjInspectionQuery emailId(String emailId) {
		if (emailId == null) {
			throw new RuntimeException("emailId is null");
		}
		this.emailId = emailId;
		return this;
	}

	public ProjInspectionQuery emailIdLike(String emailIdLike) {
		if (emailIdLike == null) {
			throw new RuntimeException("emailId is null");
		}
		this.emailIdLike = emailIdLike;
		return this;
	}

	public ProjInspectionQuery emailIds(List<String> emailIds) {
		if (emailIds == null) {
			throw new RuntimeException("emailIds is empty ");
		}
		this.emailIds = emailIds;
		return this;
	}

	public ProjInspectionQuery recemailId(String recemailId) {
		if (recemailId == null) {
			throw new RuntimeException("recemailId is null");
		}
		this.recemailId = recemailId;
		return this;
	}

	public ProjInspectionQuery recemailIdLike(String recemailIdLike) {
		if (recemailIdLike == null) {
			throw new RuntimeException("recemailId is null");
		}
		this.recemailIdLike = recemailIdLike;
		return this;
	}

	public ProjInspectionQuery recemailIds(List<String> recemailIds) {
		if (recemailIds == null) {
			throw new RuntimeException("recemailIds is empty ");
		}
		this.recemailIds = recemailIds;
		return this;
	}

	public ProjInspectionQuery mainId(String mainId) {
		if (mainId == null) {
			throw new RuntimeException("mainId is null");
		}
		this.mainId = mainId;
		return this;
	}

	public ProjInspectionQuery mainIdLike(String mainIdLike) {
		if (mainIdLike == null) {
			throw new RuntimeException("mainId is null");
		}
		this.mainIdLike = mainIdLike;
		return this;
	}

	public ProjInspectionQuery mainIds(List<String> mainIds) {
		if (mainIds == null) {
			throw new RuntimeException("mainIds is empty ");
		}
		this.mainIds = mainIds;
		return this;
	}

	public ProjInspectionQuery tagNum(String tagNum) {
		if (tagNum == null) {
			throw new RuntimeException("tagNum is null");
		}
		this.tagNum = tagNum;
		return this;
	}

	public ProjInspectionQuery tagNumLike(String tagNumLike) {
		if (tagNumLike == null) {
			throw new RuntimeException("tagNum is null");
		}
		this.tagNumLike = tagNumLike;
		return this;
	}

	public ProjInspectionQuery tagNums(List<String> tagNums) {
		if (tagNums == null) {
			throw new RuntimeException("tagNums is empty ");
		}
		this.tagNums = tagNums;
		return this;
	}

	public ProjInspectionQuery ctimeGreaterThanOrEqual(
			Date ctimeGreaterThanOrEqual) {
		if (ctimeGreaterThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
		return this;
	}

	public ProjInspectionQuery ctimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		if (ctimeLessThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
		return this;
	}

	public ProjInspectionQuery tname(String tname) {
		if (tname == null) {
			throw new RuntimeException("tname is null");
		}
		this.tname = tname;
		return this;
	}

	public ProjInspectionQuery tnameLike(String tnameLike) {
		if (tnameLike == null) {
			throw new RuntimeException("tname is null");
		}
		this.tnameLike = tnameLike;
		return this;
	}

	public ProjInspectionQuery tnames(List<String> tnames) {
		if (tnames == null) {
			throw new RuntimeException("tnames is empty ");
		}
		this.tnames = tnames;
		return this;
	}

	public ProjInspectionQuery page(Integer page) {
		if (page == null) {
			throw new RuntimeException("page is null");
		}
		this.page = page;
		return this;
	}

	public ProjInspectionQuery pageGreaterThanOrEqual(
			Integer pageGreaterThanOrEqual) {
		if (pageGreaterThanOrEqual == null) {
			throw new RuntimeException("page is null");
		}
		this.pageGreaterThanOrEqual = pageGreaterThanOrEqual;
		return this;
	}

	public ProjInspectionQuery pageLessThanOrEqual(Integer pageLessThanOrEqual) {
		if (pageLessThanOrEqual == null) {
			throw new RuntimeException("page is null");
		}
		this.pageLessThanOrEqual = pageLessThanOrEqual;
		return this;
	}

	public ProjInspectionQuery pages(List<Integer> pages) {
		if (pages == null) {
			throw new RuntimeException("pages is empty ");
		}
		this.pages = pages;
		return this;
	}

	public ProjInspectionQuery duty(String duty) {
		if (duty == null) {
			throw new RuntimeException("duty is null");
		}
		this.duty = duty;
		return this;
	}

	public ProjInspectionQuery dutyLike(String dutyLike) {
		if (dutyLike == null) {
			throw new RuntimeException("duty is null");
		}
		this.dutyLike = dutyLike;
		return this;
	}

	public ProjInspectionQuery dutys(List<String> dutys) {
		if (dutys == null) {
			throw new RuntimeException("dutys is empty ");
		}
		this.dutys = dutys;
		return this;
	}

	public ProjInspectionQuery thematic(String thematic) {
		if (thematic == null) {
			throw new RuntimeException("thematic is null");
		}
		this.thematic = thematic;
		return this;
	}

	public ProjInspectionQuery thematicLike(String thematicLike) {
		if (thematicLike == null) {
			throw new RuntimeException("thematic is null");
		}
		this.thematicLike = thematicLike;
		return this;
	}

	public ProjInspectionQuery thematics(List<String> thematics) {
		if (thematics == null) {
			throw new RuntimeException("thematics is empty ");
		}
		this.thematics = thematics;
		return this;
	}

	public ProjInspectionQuery annotations(String annotations) {
		if (annotations == null) {
			throw new RuntimeException("annotations is null");
		}
		this.annotations = annotations;
		return this;
	}

	public ProjInspectionQuery annotationsLike(String annotationsLike) {
		if (annotationsLike == null) {
			throw new RuntimeException("annotations is null");
		}
		this.annotationsLike = annotationsLike;
		return this;
	}

	public ProjInspectionQuery annotationss(List<String> annotationss) {
		if (annotationss == null) {
			throw new RuntimeException("annotationss is empty ");
		}
		this.annotationss = annotationss;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("indexId".equals(sortColumn)) {
				orderBy = "E.INDEX_ID" + a_x;
			}

			if ("intFlag".equals(sortColumn)) {
				orderBy = "E.INTFLAG" + a_x;
			}

			if ("cellTmpFileTypeId".equals(sortColumn)) {
				orderBy = "E.CELL_TMPFILETYPE_ID" + a_x;
			}

			if ("listNo".equals(sortColumn)) {
				orderBy = "E.LISTNO" + a_x;
			}

			if ("chkResult".equals(sortColumn)) {
				orderBy = "E.CHKRESULT" + a_x;
			}

			if ("pfileId".equals(sortColumn)) {
				orderBy = "E.PFILE_ID" + a_x;
			}

			if ("refillFlag".equals(sortColumn)) {
				orderBy = "E.REFILLFLAG" + a_x;
			}

			if ("groupId".equals(sortColumn)) {
				orderBy = "E.GROUPID" + a_x;
			}

			if ("oldId".equals(sortColumn)) {
				orderBy = "E.OLD_ID" + a_x;
			}

			if ("emailId".equals(sortColumn)) {
				orderBy = "E.EMAIL_ID" + a_x;
			}

			if ("recemailId".equals(sortColumn)) {
				orderBy = "E.RECEMAIL_ID" + a_x;
			}

			if ("mainId".equals(sortColumn)) {
				orderBy = "E.MAIN_ID" + a_x;
			}

			if ("tagNum".equals(sortColumn)) {
				orderBy = "E.TAGNUM" + a_x;
			}

			if ("ctime".equals(sortColumn)) {
				orderBy = "E.CTIME" + a_x;
			}

			if ("tname".equals(sortColumn)) {
				orderBy = "E.TNAME" + a_x;
			}

			if ("page".equals(sortColumn)) {
				orderBy = "E.PAGE" + a_x;
			}

			if ("duty".equals(sortColumn)) {
				orderBy = "E.DUTY" + a_x;
			}

			if ("thematic".equals(sortColumn)) {
				orderBy = "E.THEMATIC" + a_x;
			}

			if ("annotations".equals(sortColumn)) {
				orderBy = "E.ANNOTATIONS" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("indexId", "INDEX_ID");
		addColumn("intFlag", "INTFLAG");
		addColumn("cellTmpFileTypeId", "CELL_TMPFILETYPE_ID");
		addColumn("listNo", "LISTNO");
		addColumn("chkResult", "CHKRESULT");
		addColumn("pfileId", "PFILE_ID");
		addColumn("refillFlag", "REFILLFLAG");
		addColumn("groupId", "GROUPID");
		addColumn("oldId", "OLD_ID");
		addColumn("emailId", "EMAIL_ID");
		addColumn("recemailId", "RECEMAIL_ID");
		addColumn("mainId", "MAIN_ID");
		addColumn("tagNum", "TAGNUM");
		addColumn("ctime", "CTIME");
		addColumn("tname", "TNAME");
		addColumn("page", "PAGE");
		addColumn("duty", "DUTY");
		addColumn("thematic", "THEMATIC");
		addColumn("annotations", "ANNOTATIONS");
	}

}