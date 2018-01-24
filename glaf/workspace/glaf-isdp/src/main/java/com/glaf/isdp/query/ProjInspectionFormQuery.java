package com.glaf.isdp.query;

import java.util.*;

import com.glaf.core.query.DataQuery;

public class ProjInspectionFormQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected String projInspectionId;
	protected String projInspectionIdLike;
	protected List<String> projInspectionIds;
	protected String cellFormId;
	protected String cellFormIdLike;
	protected List<String> cellFormIds;
	protected String dotUseId;
	protected String dotUseIdLike;
	protected List<String> dotUseIds;
	protected Integer listNo;
	protected Integer listNoGreaterThanOrEqual;
	protected Integer listNoLessThanOrEqual;
	protected List<Integer> listNos;
	protected Integer intIsCheck;
	protected Integer intIsCheckGreaterThanOrEqual;
	protected Integer intIsCheckLessThanOrEqual;
	protected List<Integer> intIsChecks;
	protected Integer intMust;
	protected Integer intMustGreaterThanOrEqual;
	protected Integer intMustLessThanOrEqual;
	protected List<Integer> intMusts;
	protected String treepInfoIdLike;
	protected String idDateGreaterThanOrEqual;
	protected String idDateLessThanOrEqual;

	public ProjInspectionFormQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getProjInspectionId() {
		return projInspectionId;
	}

	public String getProjInspectionIdLike() {
		if (projInspectionIdLike != null
				&& projInspectionIdLike.trim().length() > 0) {
			if (!projInspectionIdLike.startsWith("%")) {
				projInspectionIdLike = "%" + projInspectionIdLike;
			}
			if (!projInspectionIdLike.endsWith("%")) {
				projInspectionIdLike = projInspectionIdLike + "%";
			}
		}
		return projInspectionIdLike;
	}

	public List<String> getProjInspectionIds() {
		return projInspectionIds;
	}

	public String getCellFormId() {
		return cellFormId;
	}

	public String getCellFormIdLike() {
		if (cellFormIdLike != null && cellFormIdLike.trim().length() > 0) {
			if (!cellFormIdLike.startsWith("%")) {
				cellFormIdLike = "%" + cellFormIdLike;
			}
			if (!cellFormIdLike.endsWith("%")) {
				cellFormIdLike = cellFormIdLike + "%";
			}
		}
		return cellFormIdLike;
	}

	public List<String> getCellFormIds() {
		return cellFormIds;
	}

	public String getDotUseId() {
		return dotUseId;
	}

	public String getDotUseIdLike() {
		if (dotUseIdLike != null && dotUseIdLike.trim().length() > 0) {
			if (!dotUseIdLike.startsWith("%")) {
				dotUseIdLike = "%" + dotUseIdLike;
			}
			if (!dotUseIdLike.endsWith("%")) {
				dotUseIdLike = dotUseIdLike + "%";
			}
		}
		return dotUseIdLike;
	}

	public List<String> getDotUseIds() {
		return dotUseIds;
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

	public Integer getIntIsCheck() {
		return intIsCheck;
	}

	public Integer getIntIsCheckGreaterThanOrEqual() {
		return intIsCheckGreaterThanOrEqual;
	}

	public Integer getIntIsCheckLessThanOrEqual() {
		return intIsCheckLessThanOrEqual;
	}

	public List<Integer> getIntIsChecks() {
		return intIsChecks;
	}

	public Integer getIntMust() {
		return intMust;
	}

	public Integer getIntMustGreaterThanOrEqual() {
		return intMustGreaterThanOrEqual;
	}

	public Integer getIntMustLessThanOrEqual() {
		return intMustLessThanOrEqual;
	}

	public List<Integer> getIntMusts() {
		return intMusts;
	}

	public void setProjInspectionId(String projInspectionId) {
		this.projInspectionId = projInspectionId;
	}

	public void setProjInspectionIdLike(String projInspectionIdLike) {
		this.projInspectionIdLike = projInspectionIdLike;
	}

	public void setProjInspectionIds(List<String> projInspectionIds) {
		this.projInspectionIds = projInspectionIds;
	}

	public void setCellFormId(String cellFormId) {
		this.cellFormId = cellFormId;
	}

	public void setCellFormIdLike(String cellFormIdLike) {
		this.cellFormIdLike = cellFormIdLike;
	}

	public void setCellFormIds(List<String> cellFormIds) {
		this.cellFormIds = cellFormIds;
	}

	public void setDotUseId(String dotUseId) {
		this.dotUseId = dotUseId;
	}

	public void setDotUseIdLike(String dotUseIdLike) {
		this.dotUseIdLike = dotUseIdLike;
	}

	public void setDotUseIds(List<String> dotUseIds) {
		this.dotUseIds = dotUseIds;
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

	public void setIntIsCheck(Integer intIsCheck) {
		this.intIsCheck = intIsCheck;
	}

	public void setIntIsCheckGreaterThanOrEqual(
			Integer intIsCheckGreaterThanOrEqual) {
		this.intIsCheckGreaterThanOrEqual = intIsCheckGreaterThanOrEqual;
	}

	public void setIntIsCheckLessThanOrEqual(Integer intIsCheckLessThanOrEqual) {
		this.intIsCheckLessThanOrEqual = intIsCheckLessThanOrEqual;
	}

	public void setIntIsChecks(List<Integer> intIsChecks) {
		this.intIsChecks = intIsChecks;
	}

	public void setIntMust(Integer intMust) {
		this.intMust = intMust;
	}

	public void setIntMustGreaterThanOrEqual(Integer intMustGreaterThanOrEqual) {
		this.intMustGreaterThanOrEqual = intMustGreaterThanOrEqual;
	}

	public void setIntMustLessThanOrEqual(Integer intMustLessThanOrEqual) {
		this.intMustLessThanOrEqual = intMustLessThanOrEqual;
	}

	public void setIntMusts(List<Integer> intMusts) {
		this.intMusts = intMusts;
	}

	public String getTreepInfoIdLike() {
		return treepInfoIdLike;
	}

	public void setTreepInfoIdLike(String treepInfoIdLike) {
		this.treepInfoIdLike = treepInfoIdLike;
	}

	public String getIdDateGreaterThanOrEqual() {
		return idDateGreaterThanOrEqual;
	}

	public void setIdDateGreaterThanOrEqual(String idDateGreaterThanOrEqual) {
		this.idDateGreaterThanOrEqual = idDateGreaterThanOrEqual;
	}

	public String getIdDateLessThanOrEqual() {
		return idDateLessThanOrEqual;
	}

	public void setIdDateLessThanOrEqual(String idDateLessThanOrEqual) {
		this.idDateLessThanOrEqual = idDateLessThanOrEqual;
	}

	public ProjInspectionFormQuery projInspectionId(String projInspectionId) {
		if (projInspectionId == null) {
			throw new RuntimeException("projInspectionId is null");
		}
		this.projInspectionId = projInspectionId;
		return this;
	}

	public ProjInspectionFormQuery projInspectionIdLike(
			String projInspectionIdLike) {
		if (projInspectionIdLike == null) {
			throw new RuntimeException("projInspectionId is null");
		}
		this.projInspectionIdLike = projInspectionIdLike;
		return this;
	}

	public ProjInspectionFormQuery projInspectionIds(
			List<String> projInspectionIds) {
		if (projInspectionIds == null) {
			throw new RuntimeException("projInspectionIds is empty ");
		}
		this.projInspectionIds = projInspectionIds;
		return this;
	}

	public ProjInspectionFormQuery cellFormId(String cellFormId) {
		if (cellFormId == null) {
			throw new RuntimeException("cellFormId is null");
		}
		this.cellFormId = cellFormId;
		return this;
	}

	public ProjInspectionFormQuery cellFormIdLike(String cellFormIdLike) {
		if (cellFormIdLike == null) {
			throw new RuntimeException("cellFormId is null");
		}
		this.cellFormIdLike = cellFormIdLike;
		return this;
	}

	public ProjInspectionFormQuery cellFormIds(List<String> cellFormIds) {
		if (cellFormIds == null) {
			throw new RuntimeException("cellFormIds is empty ");
		}
		this.cellFormIds = cellFormIds;
		return this;
	}

	public ProjInspectionFormQuery dotUseId(String dotUseId) {
		if (dotUseId == null) {
			throw new RuntimeException("dotUseId is null");
		}
		this.dotUseId = dotUseId;
		return this;
	}

	public ProjInspectionFormQuery dotUseIdLike(String dotUseIdLike) {
		if (dotUseIdLike == null) {
			throw new RuntimeException("dotUseId is null");
		}
		this.dotUseIdLike = dotUseIdLike;
		return this;
	}

	public ProjInspectionFormQuery dotUseIds(List<String> dotUseIds) {
		if (dotUseIds == null) {
			throw new RuntimeException("dotUseIds is empty ");
		}
		this.dotUseIds = dotUseIds;
		return this;
	}

	public ProjInspectionFormQuery listNo(Integer listNo) {
		if (listNo == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNo = listNo;
		return this;
	}

	public ProjInspectionFormQuery listNoGreaterThanOrEqual(
			Integer listNoGreaterThanOrEqual) {
		if (listNoGreaterThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoGreaterThanOrEqual = listNoGreaterThanOrEqual;
		return this;
	}

	public ProjInspectionFormQuery listNoLessThanOrEqual(
			Integer listNoLessThanOrEqual) {
		if (listNoLessThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoLessThanOrEqual = listNoLessThanOrEqual;
		return this;
	}

	public ProjInspectionFormQuery listNos(List<Integer> listNos) {
		if (listNos == null) {
			throw new RuntimeException("listNos is empty ");
		}
		this.listNos = listNos;
		return this;
	}

	public ProjInspectionFormQuery intIsCheck(Integer intIsCheck) {
		if (intIsCheck == null) {
			throw new RuntimeException("intIsCheck is null");
		}
		this.intIsCheck = intIsCheck;
		return this;
	}

	public ProjInspectionFormQuery intIsCheckGreaterThanOrEqual(
			Integer intIsCheckGreaterThanOrEqual) {
		if (intIsCheckGreaterThanOrEqual == null) {
			throw new RuntimeException("intIsCheck is null");
		}
		this.intIsCheckGreaterThanOrEqual = intIsCheckGreaterThanOrEqual;
		return this;
	}

	public ProjInspectionFormQuery intIsCheckLessThanOrEqual(
			Integer intIsCheckLessThanOrEqual) {
		if (intIsCheckLessThanOrEqual == null) {
			throw new RuntimeException("intIsCheck is null");
		}
		this.intIsCheckLessThanOrEqual = intIsCheckLessThanOrEqual;
		return this;
	}

	public ProjInspectionFormQuery intIsChecks(List<Integer> intIsChecks) {
		if (intIsChecks == null) {
			throw new RuntimeException("intIsChecks is empty ");
		}
		this.intIsChecks = intIsChecks;
		return this;
	}

	public ProjInspectionFormQuery intMust(Integer intMust) {
		if (intMust == null) {
			throw new RuntimeException("intMust is null");
		}
		this.intMust = intMust;
		return this;
	}

	public ProjInspectionFormQuery intMustGreaterThanOrEqual(
			Integer intMustGreaterThanOrEqual) {
		if (intMustGreaterThanOrEqual == null) {
			throw new RuntimeException("intMust is null");
		}
		this.intMustGreaterThanOrEqual = intMustGreaterThanOrEqual;
		return this;
	}

	public ProjInspectionFormQuery intMustLessThanOrEqual(
			Integer intMustLessThanOrEqual) {
		if (intMustLessThanOrEqual == null) {
			throw new RuntimeException("intMust is null");
		}
		this.intMustLessThanOrEqual = intMustLessThanOrEqual;
		return this;
	}

	public ProjInspectionFormQuery intMusts(List<Integer> intMusts) {
		if (intMusts == null) {
			throw new RuntimeException("intMusts is empty ");
		}
		this.intMusts = intMusts;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("projInspectionId".equals(sortColumn)) {
				orderBy = "E.PROJ_INSPECTION_ID" + a_x;
			}

			if ("cellFormId".equals(sortColumn)) {
				orderBy = "E.CELL_FORM_ID" + a_x;
			}

			if ("dotUseId".equals(sortColumn)) {
				orderBy = "E.DOTUSE_ID" + a_x;
			}

			if ("listNo".equals(sortColumn)) {
				orderBy = "E.LISTNO" + a_x;
			}

			if ("intIsCheck".equals(sortColumn)) {
				orderBy = "E.INTISCHECK" + a_x;
			}

			if ("intMust".equals(sortColumn)) {
				orderBy = "E.INTMUST" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("projInspectionId", "PROJ_INSPECTION_ID");
		addColumn("cellFormId", "CELL_FORM_ID");
		addColumn("dotUseId", "DOTUSE_ID");
		addColumn("listNo", "LISTNO");
		addColumn("intIsCheck", "INTISCHECK");
		addColumn("intMust", "INTMUST");
	}

}