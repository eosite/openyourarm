package com.glaf.isdp.query;

import java.util.*;

import com.glaf.core.query.DataQuery;

public class FlowActivDQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected String processId;
	protected String processIdLike;
	protected List<String> processIds;
	protected String typeOfAct;
	protected String typeOfActLike;
	protected List<String> typeOfActs;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String content;
	protected String contentLike;
	protected List<String> contents;
	protected String strFuntion;
	protected String strFuntionLike;
	protected List<String> strFuntions;
	protected String netRoleId;
	protected String netRoleIdLike;
	protected List<String> netRoleIds;
	protected Integer listNo;
	protected Integer listNoGreaterThanOrEqual;
	protected Integer listNoLessThanOrEqual;
	protected List<Integer> listNos;
	protected Double timeLimitGreaterThanOrEqual;
	protected Double timeLimitLessThanOrEqual;
	protected Integer isSave;
	protected Integer isSaveGreaterThanOrEqual;
	protected Integer isSaveLessThanOrEqual;
	protected List<Integer> isSaves;
	protected Integer isDel;
	protected Integer isDelGreaterThanOrEqual;
	protected Integer isDelLessThanOrEqual;
	protected List<Integer> isDels;
	protected Integer intSelectUser;
	protected Integer intSelectUserGreaterThanOrEqual;
	protected Integer intSelectUserLessThanOrEqual;
	protected List<Integer> intSelectUsers;
	protected Integer intUseDomain;
	protected Integer intUseDomainGreaterThanOrEqual;
	protected Integer intUseDomainLessThanOrEqual;
	protected List<Integer> intUseDomains;
	
	protected String sqlCondition;

	public FlowActivDQuery() {

	}

	public String getSqlCondition() {
		return sqlCondition;
	}

	public void setSqlCondition(String sqlCondition) {
		this.sqlCondition = sqlCondition;
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

	public String getTypeOfAct() {
		return typeOfAct;
	}

	public String getTypeOfActLike() {
		if (typeOfActLike != null && typeOfActLike.trim().length() > 0) {
			if (!typeOfActLike.startsWith("%")) {
				typeOfActLike = "%" + typeOfActLike;
			}
			if (!typeOfActLike.endsWith("%")) {
				typeOfActLike = typeOfActLike + "%";
			}
		}
		return typeOfActLike;
	}

	public List<String> getTypeOfActs() {
		return typeOfActs;
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

	public String getContent() {
		return content;
	}

	public String getContentLike() {
		if (contentLike != null && contentLike.trim().length() > 0) {
			if (!contentLike.startsWith("%")) {
				contentLike = "%" + contentLike;
			}
			if (!contentLike.endsWith("%")) {
				contentLike = contentLike + "%";
			}
		}
		return contentLike;
	}

	public List<String> getContents() {
		return contents;
	}

	public String getStrFuntion() {
		return strFuntion;
	}

	public String getStrFuntionLike() {
		if (strFuntionLike != null && strFuntionLike.trim().length() > 0) {
			if (!strFuntionLike.startsWith("%")) {
				strFuntionLike = "%" + strFuntionLike;
			}
			if (!strFuntionLike.endsWith("%")) {
				strFuntionLike = strFuntionLike + "%";
			}
		}
		return strFuntionLike;
	}

	public List<String> getStrFuntions() {
		return strFuntions;
	}

	public String getNetRoleId() {
		return netRoleId;
	}

	public String getNetRoleIdLike() {
		if (netRoleIdLike != null && netRoleIdLike.trim().length() > 0) {
			if (!netRoleIdLike.startsWith("%")) {
				netRoleIdLike = "%" + netRoleIdLike;
			}
			if (!netRoleIdLike.endsWith("%")) {
				netRoleIdLike = netRoleIdLike + "%";
			}
		}
		return netRoleIdLike;
	}

	public List<String> getNetRoleIds() {
		return netRoleIds;
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

	public Double getTimeLimitGreaterThanOrEqual() {
		return timeLimitGreaterThanOrEqual;
	}

	public Double getTimeLimitLessThanOrEqual() {
		return timeLimitLessThanOrEqual;
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

	public Integer getIntSelectUser() {
		return intSelectUser;
	}

	public Integer getIntSelectUserGreaterThanOrEqual() {
		return intSelectUserGreaterThanOrEqual;
	}

	public Integer getIntSelectUserLessThanOrEqual() {
		return intSelectUserLessThanOrEqual;
	}

	public List<Integer> getIntSelectUsers() {
		return intSelectUsers;
	}

	public Integer getIntUseDomain() {
		return intUseDomain;
	}

	public Integer getIntUseDomainGreaterThanOrEqual() {
		return intUseDomainGreaterThanOrEqual;
	}

	public Integer getIntUseDomainLessThanOrEqual() {
		return intUseDomainLessThanOrEqual;
	}

	public List<Integer> getIntUseDomains() {
		return intUseDomains;
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

	public void setTypeOfAct(String typeOfAct) {
		this.typeOfAct = typeOfAct;
	}

	public void setTypeOfActLike(String typeOfActLike) {
		this.typeOfActLike = typeOfActLike;
	}

	public void setTypeOfActs(List<String> typeOfActs) {
		this.typeOfActs = typeOfActs;
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

	public void setContent(String content) {
		this.content = content;
	}

	public void setContentLike(String contentLike) {
		this.contentLike = contentLike;
	}

	public void setContents(List<String> contents) {
		this.contents = contents;
	}

	public void setStrFuntion(String strFuntion) {
		this.strFuntion = strFuntion;
	}

	public void setStrFuntionLike(String strFuntionLike) {
		this.strFuntionLike = strFuntionLike;
	}

	public void setStrFuntions(List<String> strFuntions) {
		this.strFuntions = strFuntions;
	}

	public void setNetRoleId(String netRoleId) {
		this.netRoleId = netRoleId;
	}

	public void setNetRoleIdLike(String netRoleIdLike) {
		this.netRoleIdLike = netRoleIdLike;
	}

	public void setNetRoleIds(List<String> netRoleIds) {
		this.netRoleIds = netRoleIds;
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

	public void setTimeLimitGreaterThanOrEqual(
			Double timeLimitGreaterThanOrEqual) {
		this.timeLimitGreaterThanOrEqual = timeLimitGreaterThanOrEqual;
	}

	public void setTimeLimitLessThanOrEqual(Double timeLimitLessThanOrEqual) {
		this.timeLimitLessThanOrEqual = timeLimitLessThanOrEqual;
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

	public void setIntSelectUser(Integer intSelectUser) {
		this.intSelectUser = intSelectUser;
	}

	public void setIntSelectUserGreaterThanOrEqual(
			Integer intSelectUserGreaterThanOrEqual) {
		this.intSelectUserGreaterThanOrEqual = intSelectUserGreaterThanOrEqual;
	}

	public void setIntSelectUserLessThanOrEqual(
			Integer intSelectUserLessThanOrEqual) {
		this.intSelectUserLessThanOrEqual = intSelectUserLessThanOrEqual;
	}

	public void setIntSelectUsers(List<Integer> intSelectUsers) {
		this.intSelectUsers = intSelectUsers;
	}

	public void setIntUseDomain(Integer intUseDomain) {
		this.intUseDomain = intUseDomain;
	}

	public void setIntUseDomainGreaterThanOrEqual(
			Integer intUseDomainGreaterThanOrEqual) {
		this.intUseDomainGreaterThanOrEqual = intUseDomainGreaterThanOrEqual;
	}

	public void setIntUseDomainLessThanOrEqual(
			Integer intUseDomainLessThanOrEqual) {
		this.intUseDomainLessThanOrEqual = intUseDomainLessThanOrEqual;
	}

	public void setIntUseDomains(List<Integer> intUseDomains) {
		this.intUseDomains = intUseDomains;
	}

	public FlowActivDQuery processId(String processId) {
		if (processId == null) {
			throw new RuntimeException("processId is null");
		}
		this.processId = processId;
		return this;
	}

	public FlowActivDQuery processIdLike(String processIdLike) {
		if (processIdLike == null) {
			throw new RuntimeException("processId is null");
		}
		this.processIdLike = processIdLike;
		return this;
	}

	public FlowActivDQuery processIds(List<String> processIds) {
		if (processIds == null) {
			throw new RuntimeException("processIds is empty ");
		}
		this.processIds = processIds;
		return this;
	}

	public FlowActivDQuery typeOfAct(String typeOfAct) {
		if (typeOfAct == null) {
			throw new RuntimeException("typeOfAct is null");
		}
		this.typeOfAct = typeOfAct;
		return this;
	}

	public FlowActivDQuery typeOfActLike(String typeOfActLike) {
		if (typeOfActLike == null) {
			throw new RuntimeException("typeOfAct is null");
		}
		this.typeOfActLike = typeOfActLike;
		return this;
	}

	public FlowActivDQuery typeOfActs(List<String> typeOfActs) {
		if (typeOfActs == null) {
			throw new RuntimeException("typeOfActs is empty ");
		}
		this.typeOfActs = typeOfActs;
		return this;
	}

	public FlowActivDQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public FlowActivDQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public FlowActivDQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public FlowActivDQuery content(String content) {
		if (content == null) {
			throw new RuntimeException("content is null");
		}
		this.content = content;
		return this;
	}

	public FlowActivDQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public FlowActivDQuery contents(List<String> contents) {
		if (contents == null) {
			throw new RuntimeException("contents is empty ");
		}
		this.contents = contents;
		return this;
	}

	public FlowActivDQuery strFuntion(String strFuntion) {
		if (strFuntion == null) {
			throw new RuntimeException("strFuntion is null");
		}
		this.strFuntion = strFuntion;
		return this;
	}

	public FlowActivDQuery strFuntionLike(String strFuntionLike) {
		if (strFuntionLike == null) {
			throw new RuntimeException("strFuntion is null");
		}
		this.strFuntionLike = strFuntionLike;
		return this;
	}

	public FlowActivDQuery strFuntions(List<String> strFuntions) {
		if (strFuntions == null) {
			throw new RuntimeException("strFuntions is empty ");
		}
		this.strFuntions = strFuntions;
		return this;
	}

	public FlowActivDQuery netRoleId(String netRoleId) {
		if (netRoleId == null) {
			throw new RuntimeException("netRoleId is null");
		}
		this.netRoleId = netRoleId;
		return this;
	}

	public FlowActivDQuery netRoleIdLike(String netRoleIdLike) {
		if (netRoleIdLike == null) {
			throw new RuntimeException("netRoleId is null");
		}
		this.netRoleIdLike = netRoleIdLike;
		return this;
	}

	public FlowActivDQuery netRoleIds(List<String> netRoleIds) {
		if (netRoleIds == null) {
			throw new RuntimeException("netRoleIds is empty ");
		}
		this.netRoleIds = netRoleIds;
		return this;
	}

	public FlowActivDQuery listNo(Integer listNo) {
		if (listNo == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNo = listNo;
		return this;
	}

	public FlowActivDQuery listNoGreaterThanOrEqual(
			Integer listNoGreaterThanOrEqual) {
		if (listNoGreaterThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoGreaterThanOrEqual = listNoGreaterThanOrEqual;
		return this;
	}

	public FlowActivDQuery listNoLessThanOrEqual(Integer listNoLessThanOrEqual) {
		if (listNoLessThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoLessThanOrEqual = listNoLessThanOrEqual;
		return this;
	}

	public FlowActivDQuery listNos(List<Integer> listNos) {
		if (listNos == null) {
			throw new RuntimeException("listNos is empty ");
		}
		this.listNos = listNos;
		return this;
	}

	public FlowActivDQuery timeLimitGreaterThanOrEqual(
			Double timeLimitGreaterThanOrEqual) {
		if (timeLimitGreaterThanOrEqual == null) {
			throw new RuntimeException("timeLimit is null");
		}
		this.timeLimitGreaterThanOrEqual = timeLimitGreaterThanOrEqual;
		return this;
	}

	public FlowActivDQuery timeLimitLessThanOrEqual(
			Double timeLimitLessThanOrEqual) {
		if (timeLimitLessThanOrEqual == null) {
			throw new RuntimeException("timeLimit is null");
		}
		this.timeLimitLessThanOrEqual = timeLimitLessThanOrEqual;
		return this;
	}

	public FlowActivDQuery isSave(Integer isSave) {
		if (isSave == null) {
			throw new RuntimeException("isSave is null");
		}
		this.isSave = isSave;
		return this;
	}

	public FlowActivDQuery isSaveGreaterThanOrEqual(
			Integer isSaveGreaterThanOrEqual) {
		if (isSaveGreaterThanOrEqual == null) {
			throw new RuntimeException("isSave is null");
		}
		this.isSaveGreaterThanOrEqual = isSaveGreaterThanOrEqual;
		return this;
	}

	public FlowActivDQuery isSaveLessThanOrEqual(Integer isSaveLessThanOrEqual) {
		if (isSaveLessThanOrEqual == null) {
			throw new RuntimeException("isSave is null");
		}
		this.isSaveLessThanOrEqual = isSaveLessThanOrEqual;
		return this;
	}

	public FlowActivDQuery isSaves(List<Integer> isSaves) {
		if (isSaves == null) {
			throw new RuntimeException("isSaves is empty ");
		}
		this.isSaves = isSaves;
		return this;
	}

	public FlowActivDQuery isDel(Integer isDel) {
		if (isDel == null) {
			throw new RuntimeException("isDel is null");
		}
		this.isDel = isDel;
		return this;
	}

	public FlowActivDQuery isDelGreaterThanOrEqual(
			Integer isDelGreaterThanOrEqual) {
		if (isDelGreaterThanOrEqual == null) {
			throw new RuntimeException("isDel is null");
		}
		this.isDelGreaterThanOrEqual = isDelGreaterThanOrEqual;
		return this;
	}

	public FlowActivDQuery isDelLessThanOrEqual(Integer isDelLessThanOrEqual) {
		if (isDelLessThanOrEqual == null) {
			throw new RuntimeException("isDel is null");
		}
		this.isDelLessThanOrEqual = isDelLessThanOrEqual;
		return this;
	}

	public FlowActivDQuery isDels(List<Integer> isDels) {
		if (isDels == null) {
			throw new RuntimeException("isDels is empty ");
		}
		this.isDels = isDels;
		return this;
	}

	public FlowActivDQuery intSelectUser(Integer intSelectUser) {
		if (intSelectUser == null) {
			throw new RuntimeException("intSelectUser is null");
		}
		this.intSelectUser = intSelectUser;
		return this;
	}

	public FlowActivDQuery intSelectUserGreaterThanOrEqual(
			Integer intSelectUserGreaterThanOrEqual) {
		if (intSelectUserGreaterThanOrEqual == null) {
			throw new RuntimeException("intSelectUser is null");
		}
		this.intSelectUserGreaterThanOrEqual = intSelectUserGreaterThanOrEqual;
		return this;
	}

	public FlowActivDQuery intSelectUserLessThanOrEqual(
			Integer intSelectUserLessThanOrEqual) {
		if (intSelectUserLessThanOrEqual == null) {
			throw new RuntimeException("intSelectUser is null");
		}
		this.intSelectUserLessThanOrEqual = intSelectUserLessThanOrEqual;
		return this;
	}

	public FlowActivDQuery intSelectUsers(List<Integer> intSelectUsers) {
		if (intSelectUsers == null) {
			throw new RuntimeException("intSelectUsers is empty ");
		}
		this.intSelectUsers = intSelectUsers;
		return this;
	}

	public FlowActivDQuery intUseDomain(Integer intUseDomain) {
		if (intUseDomain == null) {
			throw new RuntimeException("intUseDomain is null");
		}
		this.intUseDomain = intUseDomain;
		return this;
	}

	public FlowActivDQuery intUseDomainGreaterThanOrEqual(
			Integer intUseDomainGreaterThanOrEqual) {
		if (intUseDomainGreaterThanOrEqual == null) {
			throw new RuntimeException("intUseDomain is null");
		}
		this.intUseDomainGreaterThanOrEqual = intUseDomainGreaterThanOrEqual;
		return this;
	}

	public FlowActivDQuery intUseDomainLessThanOrEqual(
			Integer intUseDomainLessThanOrEqual) {
		if (intUseDomainLessThanOrEqual == null) {
			throw new RuntimeException("intUseDomain is null");
		}
		this.intUseDomainLessThanOrEqual = intUseDomainLessThanOrEqual;
		return this;
	}

	public FlowActivDQuery intUseDomains(List<Integer> intUseDomains) {
		if (intUseDomains == null) {
			throw new RuntimeException("intUseDomains is empty ");
		}
		this.intUseDomains = intUseDomains;
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

			if ("typeOfAct".equals(sortColumn)) {
				orderBy = "E.TYPEOFACT" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME" + a_x;
			}

			if ("content".equals(sortColumn)) {
				orderBy = "E.CONTENT" + a_x;
			}

			if ("strFuntion".equals(sortColumn)) {
				orderBy = "E.STRFUNTION" + a_x;
			}

			if ("netRoleId".equals(sortColumn)) {
				orderBy = "E.NETROLEID" + a_x;
			}

			if ("listNo".equals(sortColumn)) {
				orderBy = "E.LISTNO" + a_x;
			}

			if ("timeLimit".equals(sortColumn)) {
				orderBy = "E.TIMELIMIT" + a_x;
			}

			if ("isSave".equals(sortColumn)) {
				orderBy = "E.ISSAVE" + a_x;
			}

			if ("isDel".equals(sortColumn)) {
				orderBy = "E.ISDEL" + a_x;
			}

			if ("intSelectUser".equals(sortColumn)) {
				orderBy = "E.INTSELECTUSER" + a_x;
			}

			if ("intUseDomain".equals(sortColumn)) {
				orderBy = "E.INTUSEDOMAIN" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("processId", "PROCESS_ID");
		addColumn("typeOfAct", "TYPEOFACT");
		addColumn("name", "NAME");
		addColumn("content", "CONTENT");
		addColumn("strFuntion", "STRFUNTION");
		addColumn("netRoleId", "NETROLEID");
		addColumn("listNo", "LISTNO");
		addColumn("timeLimit", "TIMELIMIT");
		addColumn("isSave", "ISSAVE");
		addColumn("isDel", "ISDEL");
		addColumn("intSelectUser", "INTSELECTUSER");
		addColumn("intUseDomain", "INTUSEDOMAIN");
	}

}