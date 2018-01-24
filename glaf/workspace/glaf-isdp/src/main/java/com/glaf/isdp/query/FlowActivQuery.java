package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FlowActivQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected String activDId;
	protected String activDIdLike;
	protected List<String> activDIds;
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
	protected String userId;
	protected String userIdLike;
	protected List<String> userIds;
	protected Integer listNo;
	protected Integer listNoGreaterThanOrEqual;
	protected Integer listNoLessThanOrEqual;
	protected List<Integer> listNos;
	protected Double timeLimitGreaterThanOrEqual;
	protected Double timeLimitLessThanOrEqual;
	protected Date ctimeStartGreaterThanOrEqual;
	protected Date ctimeStartLessThanOrEqual;
	protected Date ctimeEndGreaterThanOrEqual;
	protected Date ctimeEndLessThanOrEqual;
	protected Integer state;
	protected Integer stateGreaterThanOrEqual;
	protected Integer stateLessThanOrEqual;
	protected List<Integer> states;
	protected Integer intBack;
	protected Integer intBackGreaterThanOrEqual;
	protected Integer intBackLessThanOrEqual;
	protected List<Integer> intBacks;
	protected String sysId;
	protected String sysIdLike;
	protected List<String> sysIds;

	public FlowActivQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getActivDId() {
		return activDId;
	}

	public String getActivDIdLike() {
		if (activDIdLike != null && activDIdLike.trim().length() > 0) {
			if (!activDIdLike.startsWith("%")) {
				activDIdLike = "%" + activDIdLike;
			}
			if (!activDIdLike.endsWith("%")) {
				activDIdLike = activDIdLike + "%";
			}
		}
		return activDIdLike;
	}

	public List<String> getActivDIds() {
		return activDIds;
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

	public String getUserId() {
		return userId;
	}

	public String getUserIdLike() {
		if (userIdLike != null && userIdLike.trim().length() > 0) {
			if (!userIdLike.startsWith("%")) {
				userIdLike = "%" + userIdLike;
			}
			if (!userIdLike.endsWith("%")) {
				userIdLike = userIdLike + "%";
			}
		}
		return userIdLike;
	}

	public List<String> getUserIds() {
		return userIds;
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

	public Date getCtimeStartGreaterThanOrEqual() {
		return ctimeStartGreaterThanOrEqual;
	}

	public Date getCtimeStartLessThanOrEqual() {
		return ctimeStartLessThanOrEqual;
	}

	public Date getCtimeEndGreaterThanOrEqual() {
		return ctimeEndGreaterThanOrEqual;
	}

	public Date getCtimeEndLessThanOrEqual() {
		return ctimeEndLessThanOrEqual;
	}

	public Integer getState() {
		return state;
	}

	public Integer getStateGreaterThanOrEqual() {
		return stateGreaterThanOrEqual;
	}

	public Integer getStateLessThanOrEqual() {
		return stateLessThanOrEqual;
	}

	public List<Integer> getStates() {
		return states;
	}

	public Integer getIntBack() {
		return intBack;
	}

	public Integer getIntBackGreaterThanOrEqual() {
		return intBackGreaterThanOrEqual;
	}

	public Integer getIntBackLessThanOrEqual() {
		return intBackLessThanOrEqual;
	}

	public List<Integer> getIntBacks() {
		return intBacks;
	}

	public String getSysId() {
		return sysId;
	}

	public String getSysIdLike() {
		if (sysIdLike != null && sysIdLike.trim().length() > 0) {
			if (!sysIdLike.startsWith("%")) {
				sysIdLike = "%" + sysIdLike;
			}
			if (!sysIdLike.endsWith("%")) {
				sysIdLike = sysIdLike + "%";
			}
		}
		return sysIdLike;
	}

	public List<String> getSysIds() {
		return sysIds;
	}

	public void setActivDId(String activDId) {
		this.activDId = activDId;
	}

	public void setActivDIdLike(String activDIdLike) {
		this.activDIdLike = activDIdLike;
	}

	public void setActivDIds(List<String> activDIds) {
		this.activDIds = activDIds;
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

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserIdLike(String userIdLike) {
		this.userIdLike = userIdLike;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
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

	public void setCtimeStartGreaterThanOrEqual(
			Date ctimeStartGreaterThanOrEqual) {
		this.ctimeStartGreaterThanOrEqual = ctimeStartGreaterThanOrEqual;
	}

	public void setCtimeStartLessThanOrEqual(Date ctimeStartLessThanOrEqual) {
		this.ctimeStartLessThanOrEqual = ctimeStartLessThanOrEqual;
	}

	public void setCtimeEndGreaterThanOrEqual(Date ctimeEndGreaterThanOrEqual) {
		this.ctimeEndGreaterThanOrEqual = ctimeEndGreaterThanOrEqual;
	}

	public void setCtimeEndLessThanOrEqual(Date ctimeEndLessThanOrEqual) {
		this.ctimeEndLessThanOrEqual = ctimeEndLessThanOrEqual;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setStateGreaterThanOrEqual(Integer stateGreaterThanOrEqual) {
		this.stateGreaterThanOrEqual = stateGreaterThanOrEqual;
	}

	public void setStateLessThanOrEqual(Integer stateLessThanOrEqual) {
		this.stateLessThanOrEqual = stateLessThanOrEqual;
	}

	public void setStates(List<Integer> states) {
		this.states = states;
	}

	public void setIntBack(Integer intBack) {
		this.intBack = intBack;
	}

	public void setIntBackGreaterThanOrEqual(Integer intBackGreaterThanOrEqual) {
		this.intBackGreaterThanOrEqual = intBackGreaterThanOrEqual;
	}

	public void setIntBackLessThanOrEqual(Integer intBackLessThanOrEqual) {
		this.intBackLessThanOrEqual = intBackLessThanOrEqual;
	}

	public void setIntBacks(List<Integer> intBacks) {
		this.intBacks = intBacks;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public void setSysIdLike(String sysIdLike) {
		this.sysIdLike = sysIdLike;
	}

	public void setSysIds(List<String> sysIds) {
		this.sysIds = sysIds;
	}

	public FlowActivQuery activDId(String activDId) {
		if (activDId == null) {
			throw new RuntimeException("activDId is null");
		}
		this.activDId = activDId;
		return this;
	}

	public FlowActivQuery activDIdLike(String activDIdLike) {
		if (activDIdLike == null) {
			throw new RuntimeException("activDId is null");
		}
		this.activDIdLike = activDIdLike;
		return this;
	}

	public FlowActivQuery activDIds(List<String> activDIds) {
		if (activDIds == null) {
			throw new RuntimeException("activDIds is empty ");
		}
		this.activDIds = activDIds;
		return this;
	}

	public FlowActivQuery processId(String processId) {
		if (processId == null) {
			throw new RuntimeException("processId is null");
		}
		this.processId = processId;
		return this;
	}

	public FlowActivQuery processIdLike(String processIdLike) {
		if (processIdLike == null) {
			throw new RuntimeException("processId is null");
		}
		this.processIdLike = processIdLike;
		return this;
	}

	public FlowActivQuery processIds(List<String> processIds) {
		if (processIds == null) {
			throw new RuntimeException("processIds is empty ");
		}
		this.processIds = processIds;
		return this;
	}

	public FlowActivQuery typeOfAct(String typeOfAct) {
		if (typeOfAct == null) {
			throw new RuntimeException("typeOfAct is null");
		}
		this.typeOfAct = typeOfAct;
		return this;
	}

	public FlowActivQuery typeOfActLike(String typeOfActLike) {
		if (typeOfActLike == null) {
			throw new RuntimeException("typeOfAct is null");
		}
		this.typeOfActLike = typeOfActLike;
		return this;
	}

	public FlowActivQuery typeOfActs(List<String> typeOfActs) {
		if (typeOfActs == null) {
			throw new RuntimeException("typeOfActs is empty ");
		}
		this.typeOfActs = typeOfActs;
		return this;
	}

	public FlowActivQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public FlowActivQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public FlowActivQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public FlowActivQuery content(String content) {
		if (content == null) {
			throw new RuntimeException("content is null");
		}
		this.content = content;
		return this;
	}

	public FlowActivQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public FlowActivQuery contents(List<String> contents) {
		if (contents == null) {
			throw new RuntimeException("contents is empty ");
		}
		this.contents = contents;
		return this;
	}

	public FlowActivQuery strFuntion(String strFuntion) {
		if (strFuntion == null) {
			throw new RuntimeException("strFuntion is null");
		}
		this.strFuntion = strFuntion;
		return this;
	}

	public FlowActivQuery strFuntionLike(String strFuntionLike) {
		if (strFuntionLike == null) {
			throw new RuntimeException("strFuntion is null");
		}
		this.strFuntionLike = strFuntionLike;
		return this;
	}

	public FlowActivQuery strFuntions(List<String> strFuntions) {
		if (strFuntions == null) {
			throw new RuntimeException("strFuntions is empty ");
		}
		this.strFuntions = strFuntions;
		return this;
	}

	public FlowActivQuery netRoleId(String netRoleId) {
		if (netRoleId == null) {
			throw new RuntimeException("netRoleId is null");
		}
		this.netRoleId = netRoleId;
		return this;
	}

	public FlowActivQuery netRoleIdLike(String netRoleIdLike) {
		if (netRoleIdLike == null) {
			throw new RuntimeException("netRoleId is null");
		}
		this.netRoleIdLike = netRoleIdLike;
		return this;
	}

	public FlowActivQuery netRoleIds(List<String> netRoleIds) {
		if (netRoleIds == null) {
			throw new RuntimeException("netRoleIds is empty ");
		}
		this.netRoleIds = netRoleIds;
		return this;
	}

	public FlowActivQuery userId(String userId) {
		if (userId == null) {
			throw new RuntimeException("userId is null");
		}
		this.userId = userId;
		return this;
	}

	public FlowActivQuery userIdLike(String userIdLike) {
		if (userIdLike == null) {
			throw new RuntimeException("userId is null");
		}
		this.userIdLike = userIdLike;
		return this;
	}

	public FlowActivQuery userIds(List<String> userIds) {
		if (userIds == null) {
			throw new RuntimeException("userIds is empty ");
		}
		this.userIds = userIds;
		return this;
	}

	public FlowActivQuery listNo(Integer listNo) {
		if (listNo == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNo = listNo;
		return this;
	}

	public FlowActivQuery listNoGreaterThanOrEqual(
			Integer listNoGreaterThanOrEqual) {
		if (listNoGreaterThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoGreaterThanOrEqual = listNoGreaterThanOrEqual;
		return this;
	}

	public FlowActivQuery listNoLessThanOrEqual(Integer listNoLessThanOrEqual) {
		if (listNoLessThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoLessThanOrEqual = listNoLessThanOrEqual;
		return this;
	}

	public FlowActivQuery listNos(List<Integer> listNos) {
		if (listNos == null) {
			throw new RuntimeException("listNos is empty ");
		}
		this.listNos = listNos;
		return this;
	}

	public FlowActivQuery timeLimitGreaterThanOrEqual(
			Double timeLimitGreaterThanOrEqual) {
		if (timeLimitGreaterThanOrEqual == null) {
			throw new RuntimeException("timeLimit is null");
		}
		this.timeLimitGreaterThanOrEqual = timeLimitGreaterThanOrEqual;
		return this;
	}

	public FlowActivQuery timeLimitLessThanOrEqual(
			Double timeLimitLessThanOrEqual) {
		if (timeLimitLessThanOrEqual == null) {
			throw new RuntimeException("timeLimit is null");
		}
		this.timeLimitLessThanOrEqual = timeLimitLessThanOrEqual;
		return this;
	}

	public FlowActivQuery ctimeStartGreaterThanOrEqual(
			Date ctimeStartGreaterThanOrEqual) {
		if (ctimeStartGreaterThanOrEqual == null) {
			throw new RuntimeException("ctimeStart is null");
		}
		this.ctimeStartGreaterThanOrEqual = ctimeStartGreaterThanOrEqual;
		return this;
	}

	public FlowActivQuery ctimeStartLessThanOrEqual(
			Date ctimeStartLessThanOrEqual) {
		if (ctimeStartLessThanOrEqual == null) {
			throw new RuntimeException("ctimeStart is null");
		}
		this.ctimeStartLessThanOrEqual = ctimeStartLessThanOrEqual;
		return this;
	}

	public FlowActivQuery ctimeEndGreaterThanOrEqual(
			Date ctimeEndGreaterThanOrEqual) {
		if (ctimeEndGreaterThanOrEqual == null) {
			throw new RuntimeException("ctimeEnd is null");
		}
		this.ctimeEndGreaterThanOrEqual = ctimeEndGreaterThanOrEqual;
		return this;
	}

	public FlowActivQuery ctimeEndLessThanOrEqual(Date ctimeEndLessThanOrEqual) {
		if (ctimeEndLessThanOrEqual == null) {
			throw new RuntimeException("ctimeEnd is null");
		}
		this.ctimeEndLessThanOrEqual = ctimeEndLessThanOrEqual;
		return this;
	}

	public FlowActivQuery state(Integer state) {
		if (state == null) {
			throw new RuntimeException("state is null");
		}
		this.state = state;
		return this;
	}

	public FlowActivQuery stateGreaterThanOrEqual(
			Integer stateGreaterThanOrEqual) {
		if (stateGreaterThanOrEqual == null) {
			throw new RuntimeException("state is null");
		}
		this.stateGreaterThanOrEqual = stateGreaterThanOrEqual;
		return this;
	}

	public FlowActivQuery stateLessThanOrEqual(Integer stateLessThanOrEqual) {
		if (stateLessThanOrEqual == null) {
			throw new RuntimeException("state is null");
		}
		this.stateLessThanOrEqual = stateLessThanOrEqual;
		return this;
	}

	public FlowActivQuery states(List<Integer> states) {
		if (states == null) {
			throw new RuntimeException("states is empty ");
		}
		this.states = states;
		return this;
	}

	public FlowActivQuery intBack(Integer intBack) {
		if (intBack == null) {
			throw new RuntimeException("intBack is null");
		}
		this.intBack = intBack;
		return this;
	}

	public FlowActivQuery intBackGreaterThanOrEqual(
			Integer intBackGreaterThanOrEqual) {
		if (intBackGreaterThanOrEqual == null) {
			throw new RuntimeException("intBack is null");
		}
		this.intBackGreaterThanOrEqual = intBackGreaterThanOrEqual;
		return this;
	}

	public FlowActivQuery intBackLessThanOrEqual(Integer intBackLessThanOrEqual) {
		if (intBackLessThanOrEqual == null) {
			throw new RuntimeException("intBack is null");
		}
		this.intBackLessThanOrEqual = intBackLessThanOrEqual;
		return this;
	}

	public FlowActivQuery intBacks(List<Integer> intBacks) {
		if (intBacks == null) {
			throw new RuntimeException("intBacks is empty ");
		}
		this.intBacks = intBacks;
		return this;
	}

	public FlowActivQuery sysId(String sysId) {
		if (sysId == null) {
			throw new RuntimeException("sysId is null");
		}
		this.sysId = sysId;
		return this;
	}

	public FlowActivQuery sysIdLike(String sysIdLike) {
		if (sysIdLike == null) {
			throw new RuntimeException("sysId is null");
		}
		this.sysIdLike = sysIdLike;
		return this;
	}

	public FlowActivQuery sysIds(List<String> sysIds) {
		if (sysIds == null) {
			throw new RuntimeException("sysIds is empty ");
		}
		this.sysIds = sysIds;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("activDId".equals(sortColumn)) {
				orderBy = "E.ACTIV_D_ID" + a_x;
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

			if ("userId".equals(sortColumn)) {
				orderBy = "E.USERID" + a_x;
			}

			if ("listNo".equals(sortColumn)) {
				orderBy = "E.LISTNO" + a_x;
			}

			if ("timeLimit".equals(sortColumn)) {
				orderBy = "E.TIMELIMIT" + a_x;
			}

			if ("ctimeStart".equals(sortColumn)) {
				orderBy = "E.CTIME_START" + a_x;
			}

			if ("ctimeEnd".equals(sortColumn)) {
				orderBy = "E.CTIME_END" + a_x;
			}

			if ("state".equals(sortColumn)) {
				orderBy = "E.STATE" + a_x;
			}

			if ("intBack".equals(sortColumn)) {
				orderBy = "E.INTBACK" + a_x;
			}

			if ("sysId".equals(sortColumn)) {
				orderBy = "E.SYS_ID" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("activDId", "ACTIV_D_ID");
		addColumn("processId", "PROCESS_ID");
		addColumn("typeOfAct", "TYPEOFACT");
		addColumn("name", "NAME");
		addColumn("content", "CONTENT");
		addColumn("strFuntion", "STRFUNTION");
		addColumn("netRoleId", "NETROLEID");
		addColumn("userId", "USERID");
		addColumn("listNo", "LISTNO");
		addColumn("timeLimit", "TIMELIMIT");
		addColumn("ctimeStart", "CTIME_START");
		addColumn("ctimeEnd", "CTIME_END");
		addColumn("state", "STATE");
		addColumn("intBack", "INTBACK");
		addColumn("sysId", "SYS_ID");
	}

}