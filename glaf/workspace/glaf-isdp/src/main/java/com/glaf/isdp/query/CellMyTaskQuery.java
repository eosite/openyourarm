package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class CellMyTaskQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected String topId;
	protected String topIdLike;
	protected List<String> topIds;
	protected String fillFormId;
	protected String fillFormIdLike;
	protected List<String> fillFormIds;
	protected Date ctimeGreaterThanOrEqual;
	protected Date ctimeLessThanOrEqual;
	protected Integer indexId;
	protected Integer indexIdGreaterThanOrEqual;
	protected Integer indexIdLessThanOrEqual;
	protected List<Integer> indexIds;
	protected String taskId;
	protected String taskIdLike;
	protected List<String> taskIds;
	protected String fileDotFileId;
	protected String fileDotFileIdLike;
	protected List<String> fileDotFileIds;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String projName;
	protected String projNameLike;
	protected List<String> projNames;
	protected Integer listNo;
	protected Integer listNoGreaterThanOrEqual;
	protected Integer listNoLessThanOrEqual;
	protected List<Integer> listNos;
	protected Integer typeIndexId;
	protected Integer typeIndexIdGreaterThanOrEqual;
	protected Integer typeIndexIdLessThanOrEqual;
	protected List<Integer> typeIndexIds;
	protected Integer page;
	protected Integer pageGreaterThanOrEqual;
	protected Integer pageLessThanOrEqual;
	protected List<Integer> pages;
	protected Integer finishInt;
	protected Integer finishIntGreaterThanOrEqual;
	protected Integer finishIntLessThanOrEqual;
	protected List<Integer> finishInts;
	protected Integer formTypeInt;
	protected Integer formTypeIntGreaterThanOrEqual;
	protected Integer formTypeIntLessThanOrEqual;
	protected List<Integer> formTypeInts;
	protected Integer flagInt;
	protected Integer flagIntGreaterThanOrEqual;
	protected Integer flagIntLessThanOrEqual;
	protected List<Integer> flagInts;
	protected Integer intInFlow;
	protected Integer intInFlowGreaterThanOrEqual;
	protected Integer intInFlowLessThanOrEqual;
	protected List<Integer> intInFlows;
	protected String mainId;
	protected String mainIdLike;
	protected List<String> mainIds;
	protected Integer intLastPage;
	protected Integer intLastPageGreaterThanOrEqual;
	protected Integer intLastPageLessThanOrEqual;
	protected List<Integer> intLastPages;

	public CellMyTaskQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getTopId() {
		return topId;
	}

	public String getTopIdLike() {
		if (topIdLike != null && topIdLike.trim().length() > 0) {
			if (!topIdLike.startsWith("%")) {
				topIdLike = "%" + topIdLike;
			}
			if (!topIdLike.endsWith("%")) {
				topIdLike = topIdLike + "%";
			}
		}
		return topIdLike;
	}

	public List<String> getTopIds() {
		return topIds;
	}

	public String getFillFormId() {
		return fillFormId;
	}

	public String getFillFormIdLike() {
		if (fillFormIdLike != null && fillFormIdLike.trim().length() > 0) {
			if (!fillFormIdLike.startsWith("%")) {
				fillFormIdLike = "%" + fillFormIdLike;
			}
			if (!fillFormIdLike.endsWith("%")) {
				fillFormIdLike = fillFormIdLike + "%";
			}
		}
		return fillFormIdLike;
	}

	public List<String> getFillFormIds() {
		return fillFormIds;
	}

	public Date getCtimeGreaterThanOrEqual() {
		return ctimeGreaterThanOrEqual;
	}

	public Date getCtimeLessThanOrEqual() {
		return ctimeLessThanOrEqual;
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

	public String getTaskId() {
		return taskId;
	}

	public String getTaskIdLike() {
		if (taskIdLike != null && taskIdLike.trim().length() > 0) {
			if (!taskIdLike.startsWith("%")) {
				taskIdLike = "%" + taskIdLike;
			}
			if (!taskIdLike.endsWith("%")) {
				taskIdLike = taskIdLike + "%";
			}
		}
		return taskIdLike;
	}

	public List<String> getTaskIds() {
		return taskIds;
	}

	public String getFileDotFileId() {
		return fileDotFileId;
	}

	public String getFileDotFileIdLike() {
		if (fileDotFileIdLike != null && fileDotFileIdLike.trim().length() > 0) {
			if (!fileDotFileIdLike.startsWith("%")) {
				fileDotFileIdLike = "%" + fileDotFileIdLike;
			}
			if (!fileDotFileIdLike.endsWith("%")) {
				fileDotFileIdLike = fileDotFileIdLike + "%";
			}
		}
		return fileDotFileIdLike;
	}

	public List<String> getFileDotFileIds() {
		return fileDotFileIds;
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

	public String getProjName() {
		return projName;
	}

	public String getProjNameLike() {
		if (projNameLike != null && projNameLike.trim().length() > 0) {
			if (!projNameLike.startsWith("%")) {
				projNameLike = "%" + projNameLike;
			}
			if (!projNameLike.endsWith("%")) {
				projNameLike = projNameLike + "%";
			}
		}
		return projNameLike;
	}

	public List<String> getProjNames() {
		return projNames;
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

	public Integer getTypeIndexId() {
		return typeIndexId;
	}

	public Integer getTypeIndexIdGreaterThanOrEqual() {
		return typeIndexIdGreaterThanOrEqual;
	}

	public Integer getTypeIndexIdLessThanOrEqual() {
		return typeIndexIdLessThanOrEqual;
	}

	public List<Integer> getTypeIndexIds() {
		return typeIndexIds;
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

	public Integer getFinishInt() {
		return finishInt;
	}

	public Integer getFinishIntGreaterThanOrEqual() {
		return finishIntGreaterThanOrEqual;
	}

	public Integer getFinishIntLessThanOrEqual() {
		return finishIntLessThanOrEqual;
	}

	public List<Integer> getFinishInts() {
		return finishInts;
	}

	public Integer getFormTypeInt() {
		return formTypeInt;
	}

	public Integer getFormTypeIntGreaterThanOrEqual() {
		return formTypeIntGreaterThanOrEqual;
	}

	public Integer getFormTypeIntLessThanOrEqual() {
		return formTypeIntLessThanOrEqual;
	}

	public List<Integer> getFormTypeInts() {
		return formTypeInts;
	}

	public Integer getFlagInt() {
		return flagInt;
	}

	public Integer getFlagIntGreaterThanOrEqual() {
		return flagIntGreaterThanOrEqual;
	}

	public Integer getFlagIntLessThanOrEqual() {
		return flagIntLessThanOrEqual;
	}

	public List<Integer> getFlagInts() {
		return flagInts;
	}

	public Integer getIntInFlow() {
		return intInFlow;
	}

	public Integer getIntInFlowGreaterThanOrEqual() {
		return intInFlowGreaterThanOrEqual;
	}

	public Integer getIntInFlowLessThanOrEqual() {
		return intInFlowLessThanOrEqual;
	}

	public List<Integer> getIntInFlows() {
		return intInFlows;
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

	public Integer getIntLastPage() {
		return intLastPage;
	}

	public Integer getIntLastPageGreaterThanOrEqual() {
		return intLastPageGreaterThanOrEqual;
	}

	public Integer getIntLastPageLessThanOrEqual() {
		return intLastPageLessThanOrEqual;
	}

	public List<Integer> getIntLastPages() {
		return intLastPages;
	}

	public void setTopId(String topId) {
		this.topId = topId;
	}

	public void setTopIdLike(String topIdLike) {
		this.topIdLike = topIdLike;
	}

	public void setTopIds(List<String> topIds) {
		this.topIds = topIds;
	}

	public void setFillFormId(String fillFormId) {
		this.fillFormId = fillFormId;
	}

	public void setFillFormIdLike(String fillFormIdLike) {
		this.fillFormIdLike = fillFormIdLike;
	}

	public void setFillFormIds(List<String> fillFormIds) {
		this.fillFormIds = fillFormIds;
	}

	public void setCtimeGreaterThanOrEqual(Date ctimeGreaterThanOrEqual) {
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
	}

	public void setCtimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
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

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setTaskIdLike(String taskIdLike) {
		this.taskIdLike = taskIdLike;
	}

	public void setTaskIds(List<String> taskIds) {
		this.taskIds = taskIds;
	}

	public void setFileDotFileId(String fileDotFileId) {
		this.fileDotFileId = fileDotFileId;
	}

	public void setFileDotFileIdLike(String fileDotFileIdLike) {
		this.fileDotFileIdLike = fileDotFileIdLike;
	}

	public void setFileDotFileIds(List<String> fileDotFileIds) {
		this.fileDotFileIds = fileDotFileIds;
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

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public void setProjNameLike(String projNameLike) {
		this.projNameLike = projNameLike;
	}

	public void setProjNames(List<String> projNames) {
		this.projNames = projNames;
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

	public void setTypeIndexId(Integer typeIndexId) {
		this.typeIndexId = typeIndexId;
	}

	public void setTypeIndexIdGreaterThanOrEqual(
			Integer typeIndexIdGreaterThanOrEqual) {
		this.typeIndexIdGreaterThanOrEqual = typeIndexIdGreaterThanOrEqual;
	}

	public void setTypeIndexIdLessThanOrEqual(Integer typeIndexIdLessThanOrEqual) {
		this.typeIndexIdLessThanOrEqual = typeIndexIdLessThanOrEqual;
	}

	public void setTypeIndexIds(List<Integer> typeIndexIds) {
		this.typeIndexIds = typeIndexIds;
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

	public void setFinishInt(Integer finishInt) {
		this.finishInt = finishInt;
	}

	public void setFinishIntGreaterThanOrEqual(
			Integer finishIntGreaterThanOrEqual) {
		this.finishIntGreaterThanOrEqual = finishIntGreaterThanOrEqual;
	}

	public void setFinishIntLessThanOrEqual(Integer finishIntLessThanOrEqual) {
		this.finishIntLessThanOrEqual = finishIntLessThanOrEqual;
	}

	public void setFinishInts(List<Integer> finishInts) {
		this.finishInts = finishInts;
	}

	public void setFormTypeInt(Integer formTypeInt) {
		this.formTypeInt = formTypeInt;
	}

	public void setFormTypeIntGreaterThanOrEqual(
			Integer formTypeIntGreaterThanOrEqual) {
		this.formTypeIntGreaterThanOrEqual = formTypeIntGreaterThanOrEqual;
	}

	public void setFormTypeIntLessThanOrEqual(Integer formTypeIntLessThanOrEqual) {
		this.formTypeIntLessThanOrEqual = formTypeIntLessThanOrEqual;
	}

	public void setFormTypeInts(List<Integer> formTypeInts) {
		this.formTypeInts = formTypeInts;
	}

	public void setFlagInt(Integer flagInt) {
		this.flagInt = flagInt;
	}

	public void setFlagIntGreaterThanOrEqual(Integer flagIntGreaterThanOrEqual) {
		this.flagIntGreaterThanOrEqual = flagIntGreaterThanOrEqual;
	}

	public void setFlagIntLessThanOrEqual(Integer flagIntLessThanOrEqual) {
		this.flagIntLessThanOrEqual = flagIntLessThanOrEqual;
	}

	public void setFlagInts(List<Integer> flagInts) {
		this.flagInts = flagInts;
	}

	public void setIntInFlow(Integer intInFlow) {
		this.intInFlow = intInFlow;
	}

	public void setIntInFlowGreaterThanOrEqual(
			Integer intInFlowGreaterThanOrEqual) {
		this.intInFlowGreaterThanOrEqual = intInFlowGreaterThanOrEqual;
	}

	public void setIntInFlowLessThanOrEqual(Integer intInFlowLessThanOrEqual) {
		this.intInFlowLessThanOrEqual = intInFlowLessThanOrEqual;
	}

	public void setIntInFlows(List<Integer> intInFlows) {
		this.intInFlows = intInFlows;
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

	public void setIntLastPage(Integer intLastPage) {
		this.intLastPage = intLastPage;
	}

	public void setIntLastPageGreaterThanOrEqual(
			Integer intLastPageGreaterThanOrEqual) {
		this.intLastPageGreaterThanOrEqual = intLastPageGreaterThanOrEqual;
	}

	public void setIntLastPageLessThanOrEqual(Integer intLastPageLessThanOrEqual) {
		this.intLastPageLessThanOrEqual = intLastPageLessThanOrEqual;
	}

	public void setIntLastPages(List<Integer> intLastPages) {
		this.intLastPages = intLastPages;
	}

	public CellMyTaskQuery topId(String topId) {
		if (topId == null) {
			throw new RuntimeException("topId is null");
		}
		this.topId = topId;
		return this;
	}

	public CellMyTaskQuery topIdLike(String topIdLike) {
		if (topIdLike == null) {
			throw new RuntimeException("topId is null");
		}
		this.topIdLike = topIdLike;
		return this;
	}

	public CellMyTaskQuery topIds(List<String> topIds) {
		if (topIds == null) {
			throw new RuntimeException("topIds is empty ");
		}
		this.topIds = topIds;
		return this;
	}

	public CellMyTaskQuery fillFormId(String fillFormId) {
		if (fillFormId == null) {
			throw new RuntimeException("fillFormId is null");
		}
		this.fillFormId = fillFormId;
		return this;
	}

	public CellMyTaskQuery fillFormIdLike(String fillFormIdLike) {
		if (fillFormIdLike == null) {
			throw new RuntimeException("fillFormId is null");
		}
		this.fillFormIdLike = fillFormIdLike;
		return this;
	}

	public CellMyTaskQuery fillFormIds(List<String> fillFormIds) {
		if (fillFormIds == null) {
			throw new RuntimeException("fillFormIds is empty ");
		}
		this.fillFormIds = fillFormIds;
		return this;
	}

	public CellMyTaskQuery ctimeGreaterThanOrEqual(Date ctimeGreaterThanOrEqual) {
		if (ctimeGreaterThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
		return this;
	}

	public CellMyTaskQuery ctimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		if (ctimeLessThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
		return this;
	}

	public CellMyTaskQuery indexId(Integer indexId) {
		if (indexId == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexId = indexId;
		return this;
	}

	public CellMyTaskQuery indexIdGreaterThanOrEqual(
			Integer indexIdGreaterThanOrEqual) {
		if (indexIdGreaterThanOrEqual == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexIdGreaterThanOrEqual = indexIdGreaterThanOrEqual;
		return this;
	}

	public CellMyTaskQuery indexIdLessThanOrEqual(Integer indexIdLessThanOrEqual) {
		if (indexIdLessThanOrEqual == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexIdLessThanOrEqual = indexIdLessThanOrEqual;
		return this;
	}

	public CellMyTaskQuery indexIds(List<Integer> indexIds) {
		if (indexIds == null) {
			throw new RuntimeException("indexIds is empty ");
		}
		this.indexIds = indexIds;
		return this;
	}

	public CellMyTaskQuery taskId(String taskId) {
		if (taskId == null) {
			throw new RuntimeException("taskId is null");
		}
		this.taskId = taskId;
		return this;
	}

	public CellMyTaskQuery taskIdLike(String taskIdLike) {
		if (taskIdLike == null) {
			throw new RuntimeException("taskId is null");
		}
		this.taskIdLike = taskIdLike;
		return this;
	}

	public CellMyTaskQuery taskIds(List<String> taskIds) {
		if (taskIds == null) {
			throw new RuntimeException("taskIds is empty ");
		}
		this.taskIds = taskIds;
		return this;
	}

	public CellMyTaskQuery fileDotFileId(String fileDotFileId) {
		if (fileDotFileId == null) {
			throw new RuntimeException("fileDotFileId is null");
		}
		this.fileDotFileId = fileDotFileId;
		return this;
	}

	public CellMyTaskQuery fileDotFileIdLike(String fileDotFileIdLike) {
		if (fileDotFileIdLike == null) {
			throw new RuntimeException("fileDotFileId is null");
		}
		this.fileDotFileIdLike = fileDotFileIdLike;
		return this;
	}

	public CellMyTaskQuery fileDotFileIds(List<String> fileDotFileIds) {
		if (fileDotFileIds == null) {
			throw new RuntimeException("fileDotFileIds is empty ");
		}
		this.fileDotFileIds = fileDotFileIds;
		return this;
	}

	public CellMyTaskQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public CellMyTaskQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public CellMyTaskQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public CellMyTaskQuery projName(String projName) {
		if (projName == null) {
			throw new RuntimeException("projName is null");
		}
		this.projName = projName;
		return this;
	}

	public CellMyTaskQuery projNameLike(String projNameLike) {
		if (projNameLike == null) {
			throw new RuntimeException("projName is null");
		}
		this.projNameLike = projNameLike;
		return this;
	}

	public CellMyTaskQuery projNames(List<String> projNames) {
		if (projNames == null) {
			throw new RuntimeException("projNames is empty ");
		}
		this.projNames = projNames;
		return this;
	}

	public CellMyTaskQuery listNo(Integer listNo) {
		if (listNo == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNo = listNo;
		return this;
	}

	public CellMyTaskQuery listNoGreaterThanOrEqual(
			Integer listNoGreaterThanOrEqual) {
		if (listNoGreaterThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoGreaterThanOrEqual = listNoGreaterThanOrEqual;
		return this;
	}

	public CellMyTaskQuery listNoLessThanOrEqual(Integer listNoLessThanOrEqual) {
		if (listNoLessThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoLessThanOrEqual = listNoLessThanOrEqual;
		return this;
	}

	public CellMyTaskQuery listNos(List<Integer> listNos) {
		if (listNos == null) {
			throw new RuntimeException("listNos is empty ");
		}
		this.listNos = listNos;
		return this;
	}

	public CellMyTaskQuery typeIndexId(Integer typeIndexId) {
		if (typeIndexId == null) {
			throw new RuntimeException("typeIndexId is null");
		}
		this.typeIndexId = typeIndexId;
		return this;
	}

	public CellMyTaskQuery typeIndexIdGreaterThanOrEqual(
			Integer typeIndexIdGreaterThanOrEqual) {
		if (typeIndexIdGreaterThanOrEqual == null) {
			throw new RuntimeException("typeIndexId is null");
		}
		this.typeIndexIdGreaterThanOrEqual = typeIndexIdGreaterThanOrEqual;
		return this;
	}

	public CellMyTaskQuery typeIndexIdLessThanOrEqual(
			Integer typeIndexIdLessThanOrEqual) {
		if (typeIndexIdLessThanOrEqual == null) {
			throw new RuntimeException("typeIndexId is null");
		}
		this.typeIndexIdLessThanOrEqual = typeIndexIdLessThanOrEqual;
		return this;
	}

	public CellMyTaskQuery typeIndexIds(List<Integer> typeIndexIds) {
		if (typeIndexIds == null) {
			throw new RuntimeException("typeIndexIds is empty ");
		}
		this.typeIndexIds = typeIndexIds;
		return this;
	}

	public CellMyTaskQuery page(Integer page) {
		if (page == null) {
			throw new RuntimeException("page is null");
		}
		this.page = page;
		return this;
	}

	public CellMyTaskQuery pageGreaterThanOrEqual(Integer pageGreaterThanOrEqual) {
		if (pageGreaterThanOrEqual == null) {
			throw new RuntimeException("page is null");
		}
		this.pageGreaterThanOrEqual = pageGreaterThanOrEqual;
		return this;
	}

	public CellMyTaskQuery pageLessThanOrEqual(Integer pageLessThanOrEqual) {
		if (pageLessThanOrEqual == null) {
			throw new RuntimeException("page is null");
		}
		this.pageLessThanOrEqual = pageLessThanOrEqual;
		return this;
	}

	public CellMyTaskQuery pages(List<Integer> pages) {
		if (pages == null) {
			throw new RuntimeException("pages is empty ");
		}
		this.pages = pages;
		return this;
	}

	public CellMyTaskQuery finishInt(Integer finishInt) {
		if (finishInt == null) {
			throw new RuntimeException("finishInt is null");
		}
		this.finishInt = finishInt;
		return this;
	}

	public CellMyTaskQuery finishIntGreaterThanOrEqual(
			Integer finishIntGreaterThanOrEqual) {
		if (finishIntGreaterThanOrEqual == null) {
			throw new RuntimeException("finishInt is null");
		}
		this.finishIntGreaterThanOrEqual = finishIntGreaterThanOrEqual;
		return this;
	}

	public CellMyTaskQuery finishIntLessThanOrEqual(
			Integer finishIntLessThanOrEqual) {
		if (finishIntLessThanOrEqual == null) {
			throw new RuntimeException("finishInt is null");
		}
		this.finishIntLessThanOrEqual = finishIntLessThanOrEqual;
		return this;
	}

	public CellMyTaskQuery finishInts(List<Integer> finishInts) {
		if (finishInts == null) {
			throw new RuntimeException("finishInts is empty ");
		}
		this.finishInts = finishInts;
		return this;
	}

	public CellMyTaskQuery formTypeInt(Integer formTypeInt) {
		if (formTypeInt == null) {
			throw new RuntimeException("formTypeInt is null");
		}
		this.formTypeInt = formTypeInt;
		return this;
	}

	public CellMyTaskQuery formTypeIntGreaterThanOrEqual(
			Integer formTypeIntGreaterThanOrEqual) {
		if (formTypeIntGreaterThanOrEqual == null) {
			throw new RuntimeException("formTypeInt is null");
		}
		this.formTypeIntGreaterThanOrEqual = formTypeIntGreaterThanOrEqual;
		return this;
	}

	public CellMyTaskQuery formTypeIntLessThanOrEqual(
			Integer formTypeIntLessThanOrEqual) {
		if (formTypeIntLessThanOrEqual == null) {
			throw new RuntimeException("formTypeInt is null");
		}
		this.formTypeIntLessThanOrEqual = formTypeIntLessThanOrEqual;
		return this;
	}

	public CellMyTaskQuery formTypeInts(List<Integer> formTypeInts) {
		if (formTypeInts == null) {
			throw new RuntimeException("formTypeInts is empty ");
		}
		this.formTypeInts = formTypeInts;
		return this;
	}

	public CellMyTaskQuery flagInt(Integer flagInt) {
		if (flagInt == null) {
			throw new RuntimeException("flagInt is null");
		}
		this.flagInt = flagInt;
		return this;
	}

	public CellMyTaskQuery flagIntGreaterThanOrEqual(
			Integer flagIntGreaterThanOrEqual) {
		if (flagIntGreaterThanOrEqual == null) {
			throw new RuntimeException("flagInt is null");
		}
		this.flagIntGreaterThanOrEqual = flagIntGreaterThanOrEqual;
		return this;
	}

	public CellMyTaskQuery flagIntLessThanOrEqual(Integer flagIntLessThanOrEqual) {
		if (flagIntLessThanOrEqual == null) {
			throw new RuntimeException("flagInt is null");
		}
		this.flagIntLessThanOrEqual = flagIntLessThanOrEqual;
		return this;
	}

	public CellMyTaskQuery flagInts(List<Integer> flagInts) {
		if (flagInts == null) {
			throw new RuntimeException("flagInts is empty ");
		}
		this.flagInts = flagInts;
		return this;
	}

	public CellMyTaskQuery intInFlow(Integer intInFlow) {
		if (intInFlow == null) {
			throw new RuntimeException("intInFlow is null");
		}
		this.intInFlow = intInFlow;
		return this;
	}

	public CellMyTaskQuery intInFlowGreaterThanOrEqual(
			Integer intInFlowGreaterThanOrEqual) {
		if (intInFlowGreaterThanOrEqual == null) {
			throw new RuntimeException("intInFlow is null");
		}
		this.intInFlowGreaterThanOrEqual = intInFlowGreaterThanOrEqual;
		return this;
	}

	public CellMyTaskQuery intInFlowLessThanOrEqual(
			Integer intInFlowLessThanOrEqual) {
		if (intInFlowLessThanOrEqual == null) {
			throw new RuntimeException("intInFlow is null");
		}
		this.intInFlowLessThanOrEqual = intInFlowLessThanOrEqual;
		return this;
	}

	public CellMyTaskQuery intInFlows(List<Integer> intInFlows) {
		if (intInFlows == null) {
			throw new RuntimeException("intInFlows is empty ");
		}
		this.intInFlows = intInFlows;
		return this;
	}

	public CellMyTaskQuery mainId(String mainId) {
		if (mainId == null) {
			throw new RuntimeException("mainId is null");
		}
		this.mainId = mainId;
		return this;
	}

	public CellMyTaskQuery mainIdLike(String mainIdLike) {
		if (mainIdLike == null) {
			throw new RuntimeException("mainId is null");
		}
		this.mainIdLike = mainIdLike;
		return this;
	}

	public CellMyTaskQuery mainIds(List<String> mainIds) {
		if (mainIds == null) {
			throw new RuntimeException("mainIds is empty ");
		}
		this.mainIds = mainIds;
		return this;
	}

	public CellMyTaskQuery intLastPage(Integer intLastPage) {
		if (intLastPage == null) {
			throw new RuntimeException("intLastPage is null");
		}
		this.intLastPage = intLastPage;
		return this;
	}

	public CellMyTaskQuery intLastPageGreaterThanOrEqual(
			Integer intLastPageGreaterThanOrEqual) {
		if (intLastPageGreaterThanOrEqual == null) {
			throw new RuntimeException("intLastPage is null");
		}
		this.intLastPageGreaterThanOrEqual = intLastPageGreaterThanOrEqual;
		return this;
	}

	public CellMyTaskQuery intLastPageLessThanOrEqual(
			Integer intLastPageLessThanOrEqual) {
		if (intLastPageLessThanOrEqual == null) {
			throw new RuntimeException("intLastPage is null");
		}
		this.intLastPageLessThanOrEqual = intLastPageLessThanOrEqual;
		return this;
	}

	public CellMyTaskQuery intLastPages(List<Integer> intLastPages) {
		if (intLastPages == null) {
			throw new RuntimeException("intLastPages is empty ");
		}
		this.intLastPages = intLastPages;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("topId".equals(sortColumn)) {
				orderBy = "E.TOPID" + a_x;
			}

			if ("fillFormId".equals(sortColumn)) {
				orderBy = "E.FILLFORM_ID" + a_x;
			}

			if ("ctime".equals(sortColumn)) {
				orderBy = "E.CTIME" + a_x;
			}

			if ("indexId".equals(sortColumn)) {
				orderBy = "E.INDEX_ID" + a_x;
			}

			if ("taskId".equals(sortColumn)) {
				orderBy = "E.TASK_ID" + a_x;
			}

			if ("fileDotFileId".equals(sortColumn)) {
				orderBy = "E.FILEDOT_FILEID" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME" + a_x;
			}

			if ("projName".equals(sortColumn)) {
				orderBy = "E.PROJNAME" + a_x;
			}

			if ("listNo".equals(sortColumn)) {
				orderBy = "E.LISTNO" + a_x;
			}

			if ("typeIndexId".equals(sortColumn)) {
				orderBy = "E.TYPE_INDEX_ID" + a_x;
			}

			if ("page".equals(sortColumn)) {
				orderBy = "E.PAGE" + a_x;
			}

			if ("finishInt".equals(sortColumn)) {
				orderBy = "E.FINISHINT" + a_x;
			}

			if ("formTypeInt".equals(sortColumn)) {
				orderBy = "E.FORMTYPEINT" + a_x;
			}

			if ("flagInt".equals(sortColumn)) {
				orderBy = "E.FLAGINT" + a_x;
			}

			if ("intInFlow".equals(sortColumn)) {
				orderBy = "E.INTINFLOW" + a_x;
			}

			if ("mainId".equals(sortColumn)) {
				orderBy = "E.MAIN_ID" + a_x;
			}

			if ("intLastPage".equals(sortColumn)) {
				orderBy = "E.INTLASTPAGE" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("topId", "TOPID");
		addColumn("fillFormId", "FILLFORM_ID");
		addColumn("ctime", "CTIME");
		addColumn("indexId", "INDEX_ID");
		addColumn("taskId", "TASK_ID");
		addColumn("fileDotFileId", "FILEDOT_FILEID");
		addColumn("name", "NAME");
		addColumn("projName", "PROJNAME");
		addColumn("listNo", "LISTNO");
		addColumn("typeIndexId", "TYPE_INDEX_ID");
		addColumn("page", "PAGE");
		addColumn("finishInt", "FINISHINT");
		addColumn("formTypeInt", "FORMTYPEINT");
		addColumn("flagInt", "FLAGINT");
		addColumn("intInFlow", "INTINFLOW");
		addColumn("mainId", "MAIN_ID");
		addColumn("intLastPage", "INTLASTPAGE");
	}

}