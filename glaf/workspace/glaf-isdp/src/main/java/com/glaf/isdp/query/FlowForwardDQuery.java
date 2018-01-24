package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FlowForwardDQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected String processId;
	protected String processIdLike;
	protected List<String> processIds;
	protected String activId;
	protected String activIdLike;
	protected List<String> activIds;
	protected String activPre;
	protected String activPreLike;
	protected List<String> activPres;
	protected String activNext;
	protected String activNextLike;
	protected List<String> activNexts;
	protected Integer isSave;
	protected Integer isSaveGreaterThanOrEqual;
	protected Integer isSaveLessThanOrEqual;
	protected List<Integer> isSaves;
	protected Integer isDel;
	protected Integer isDelGreaterThanOrEqual;
	protected Integer isDelLessThanOrEqual;
	protected List<Integer> isDels;
	protected Integer listNo;
	protected Integer listNoGreaterThanOrEqual;
	protected Integer listNoLessThanOrEqual;
	protected List<Integer> listNos;

	public FlowForwardDQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getProcessId() {
		return processId;
	}

	public String getProcessIdLike() {
		if (processIdLike != null && processIdLike.trim().length() > 0) {
			if (!processIdLike.startsWith("%")) {
				processIdLike = "%" + processIdLike;
			}
			if (!processIdLike.endsWith("%")) {
				processIdLike = processIdLike + "%";
			}
		}
		return processIdLike;
	}

	public List<String> getProcessIds() {
		return processIds;
	}

	public String getActivId() {
		return activId;
	}

	public String getActivIdLike() {
		if (activIdLike != null && activIdLike.trim().length() > 0) {
			if (!activIdLike.startsWith("%")) {
				activIdLike = "%" + activIdLike;
			}
			if (!activIdLike.endsWith("%")) {
				activIdLike = activIdLike + "%";
			}
		}
		return activIdLike;
	}

	public List<String> getActivIds() {
		return activIds;
	}

	public String getActivPre() {
		return activPre;
	}

	public String getActivPreLike() {
		if (activPreLike != null && activPreLike.trim().length() > 0) {
			if (!activPreLike.startsWith("%")) {
				activPreLike = "%" + activPreLike;
			}
			if (!activPreLike.endsWith("%")) {
				activPreLike = activPreLike + "%";
			}
		}
		return activPreLike;
	}

	public List<String> getActivPres() {
		return activPres;
	}

	public String getActivNext() {
		return activNext;
	}

	public String getActivNextLike() {
		if (activNextLike != null && activNextLike.trim().length() > 0) {
			if (!activNextLike.startsWith("%")) {
				activNextLike = "%" + activNextLike;
			}
			if (!activNextLike.endsWith("%")) {
				activNextLike = activNextLike + "%";
			}
		}
		return activNextLike;
	}

	public List<String> getActivNexts() {
		return activNexts;
	}

	public Integer getIsSave() {
		return isSave;
	}

	public Integer getIsSaveGreaterThanOrEqual() {
		return isSaveGreaterThanOrEqual;
	}

	public Integer getIsSaveLessThanOrEqual() {
		return isSaveLessThanOrEqual;
	}

	public List<Integer> getIsSaves() {
		return isSaves;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public Integer getIsDelGreaterThanOrEqual() {
		return isDelGreaterThanOrEqual;
	}

	public Integer getIsDelLessThanOrEqual() {
		return isDelLessThanOrEqual;
	}

	public List<Integer> getIsDels() {
		return isDels;
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

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public void setProcessIdLike(String processIdLike) {
		this.processIdLike = processIdLike;
	}

	public void setProcessIds(List<String> processIds) {
		this.processIds = processIds;
	}

	public void setActivId(String activId) {
		this.activId = activId;
	}

	public void setActivIdLike(String activIdLike) {
		this.activIdLike = activIdLike;
	}

	public void setActivIds(List<String> activIds) {
		this.activIds = activIds;
	}

	public void setActivPre(String activPre) {
		this.activPre = activPre;
	}

	public void setActivPreLike(String activPreLike) {
		this.activPreLike = activPreLike;
	}

	public void setActivPres(List<String> activPres) {
		this.activPres = activPres;
	}

	public void setActivNext(String activNext) {
		this.activNext = activNext;
	}

	public void setActivNextLike(String activNextLike) {
		this.activNextLike = activNextLike;
	}

	public void setActivNexts(List<String> activNexts) {
		this.activNexts = activNexts;
	}

	public void setIsSave(Integer isSave) {
		this.isSave = isSave;
	}

	public void setIsSaveGreaterThanOrEqual(Integer isSaveGreaterThanOrEqual) {
		this.isSaveGreaterThanOrEqual = isSaveGreaterThanOrEqual;
	}

	public void setIsSaveLessThanOrEqual(Integer isSaveLessThanOrEqual) {
		this.isSaveLessThanOrEqual = isSaveLessThanOrEqual;
	}

	public void setIsSaves(List<Integer> isSaves) {
		this.isSaves = isSaves;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public void setIsDelGreaterThanOrEqual(Integer isDelGreaterThanOrEqual) {
		this.isDelGreaterThanOrEqual = isDelGreaterThanOrEqual;
	}

	public void setIsDelLessThanOrEqual(Integer isDelLessThanOrEqual) {
		this.isDelLessThanOrEqual = isDelLessThanOrEqual;
	}

	public void setIsDels(List<Integer> isDels) {
		this.isDels = isDels;
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

	public FlowForwardDQuery processId(String processId) {
		if (processId == null) {
			throw new RuntimeException("processId is null");
		}
		this.processId = processId;
		return this;
	}

	public FlowForwardDQuery processIdLike(String processIdLike) {
		if (processIdLike == null) {
			throw new RuntimeException("processId is null");
		}
		this.processIdLike = processIdLike;
		return this;
	}

	public FlowForwardDQuery processIds(List<String> processIds) {
		if (processIds == null) {
			throw new RuntimeException("processIds is empty ");
		}
		this.processIds = processIds;
		return this;
	}

	public FlowForwardDQuery activId(String activId) {
		if (activId == null) {
			throw new RuntimeException("activId is null");
		}
		this.activId = activId;
		return this;
	}

	public FlowForwardDQuery activIdLike(String activIdLike) {
		if (activIdLike == null) {
			throw new RuntimeException("activId is null");
		}
		this.activIdLike = activIdLike;
		return this;
	}

	public FlowForwardDQuery activIds(List<String> activIds) {
		if (activIds == null) {
			throw new RuntimeException("activIds is empty ");
		}
		this.activIds = activIds;
		return this;
	}

	public FlowForwardDQuery activPre(String activPre) {
		if (activPre == null) {
			throw new RuntimeException("activPre is null");
		}
		this.activPre = activPre;
		return this;
	}

	public FlowForwardDQuery activPreLike(String activPreLike) {
		if (activPreLike == null) {
			throw new RuntimeException("activPre is null");
		}
		this.activPreLike = activPreLike;
		return this;
	}

	public FlowForwardDQuery activPres(List<String> activPres) {
		if (activPres == null) {
			throw new RuntimeException("activPres is empty ");
		}
		this.activPres = activPres;
		return this;
	}

	public FlowForwardDQuery activNext(String activNext) {
		if (activNext == null) {
			throw new RuntimeException("activNext is null");
		}
		this.activNext = activNext;
		return this;
	}

	public FlowForwardDQuery activNextLike(String activNextLike) {
		if (activNextLike == null) {
			throw new RuntimeException("activNext is null");
		}
		this.activNextLike = activNextLike;
		return this;
	}

	public FlowForwardDQuery activNexts(List<String> activNexts) {
		if (activNexts == null) {
			throw new RuntimeException("activNexts is empty ");
		}
		this.activNexts = activNexts;
		return this;
	}

	public FlowForwardDQuery isSave(Integer isSave) {
		if (isSave == null) {
			throw new RuntimeException("isSave is null");
		}
		this.isSave = isSave;
		return this;
	}

	public FlowForwardDQuery isSaveGreaterThanOrEqual(
			Integer isSaveGreaterThanOrEqual) {
		if (isSaveGreaterThanOrEqual == null) {
			throw new RuntimeException("isSave is null");
		}
		this.isSaveGreaterThanOrEqual = isSaveGreaterThanOrEqual;
		return this;
	}

	public FlowForwardDQuery isSaveLessThanOrEqual(Integer isSaveLessThanOrEqual) {
		if (isSaveLessThanOrEqual == null) {
			throw new RuntimeException("isSave is null");
		}
		this.isSaveLessThanOrEqual = isSaveLessThanOrEqual;
		return this;
	}

	public FlowForwardDQuery isSaves(List<Integer> isSaves) {
		if (isSaves == null) {
			throw new RuntimeException("isSaves is empty ");
		}
		this.isSaves = isSaves;
		return this;
	}

	public FlowForwardDQuery isDel(Integer isDel) {
		if (isDel == null) {
			throw new RuntimeException("isDel is null");
		}
		this.isDel = isDel;
		return this;
	}

	public FlowForwardDQuery isDelGreaterThanOrEqual(
			Integer isDelGreaterThanOrEqual) {
		if (isDelGreaterThanOrEqual == null) {
			throw new RuntimeException("isDel is null");
		}
		this.isDelGreaterThanOrEqual = isDelGreaterThanOrEqual;
		return this;
	}

	public FlowForwardDQuery isDelLessThanOrEqual(Integer isDelLessThanOrEqual) {
		if (isDelLessThanOrEqual == null) {
			throw new RuntimeException("isDel is null");
		}
		this.isDelLessThanOrEqual = isDelLessThanOrEqual;
		return this;
	}

	public FlowForwardDQuery isDels(List<Integer> isDels) {
		if (isDels == null) {
			throw new RuntimeException("isDels is empty ");
		}
		this.isDels = isDels;
		return this;
	}

	public FlowForwardDQuery listNo(Integer listNo) {
		if (listNo == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNo = listNo;
		return this;
	}

	public FlowForwardDQuery listNoGreaterThanOrEqual(
			Integer listNoGreaterThanOrEqual) {
		if (listNoGreaterThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoGreaterThanOrEqual = listNoGreaterThanOrEqual;
		return this;
	}

	public FlowForwardDQuery listNoLessThanOrEqual(Integer listNoLessThanOrEqual) {
		if (listNoLessThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoLessThanOrEqual = listNoLessThanOrEqual;
		return this;
	}

	public FlowForwardDQuery listNos(List<Integer> listNos) {
		if (listNos == null) {
			throw new RuntimeException("listNos is empty ");
		}
		this.listNos = listNos;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("processId".equals(sortColumn)) {
				orderBy = "E.PROCESS_ID" + a_x;
			}

			if ("activId".equals(sortColumn)) {
				orderBy = "E.ACTIV_ID" + a_x;
			}

			if ("activPre".equals(sortColumn)) {
				orderBy = "E.ACTIV_PRE" + a_x;
			}

			if ("activNext".equals(sortColumn)) {
				orderBy = "E.ACTIV_NEXT" + a_x;
			}

			if ("isSave".equals(sortColumn)) {
				orderBy = "E.ISSAVE" + a_x;
			}

			if ("isDel".equals(sortColumn)) {
				orderBy = "E.ISDEL" + a_x;
			}

			if ("listNo".equals(sortColumn)) {
				orderBy = "E.LISTNO" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("processId", "PROCESS_ID");
		addColumn("activId", "ACTIV_ID");
		addColumn("activPre", "ACTIV_PRE");
		addColumn("activNext", "ACTIV_NEXT");
		addColumn("isSave", "ISSAVE");
		addColumn("isDel", "ISDEL");
		addColumn("listNo", "LISTNO");
	}

}