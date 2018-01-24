package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class TreepNameQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Integer> indexIds;
	protected Collection<String> appActorIds;
	protected String id;
	protected String idLike;
	protected List<String> ids;
	protected String num;
	protected String numLike;
	protected List<String> nums;
	// protected Integer parentId;
	// protected Integer parentIdGreaterThanOrEqual;
	// protected Integer parentIdLessThanOrEqual;
	// protected List<Integer> parentIds;
	protected String indexName;
	protected String indexNameLike;
	protected List<String> indexNames;
	protected Integer level;
	protected Integer levelGreaterThanOrEqual;
	protected Integer levelLessThanOrEqual;
	protected List<Integer> levels;
	protected Integer showId;
	protected Integer showIdGreaterThanOrEqual;
	protected Integer showIdLessThanOrEqual;
	protected List<Integer> showIds;
	protected String ruleId;
	protected String ruleIdLike;
	protected List<String> ruleIds;
	protected Integer nodeIco;
	protected Integer nodeIcoGreaterThanOrEqual;
	protected Integer nodeIcoLessThanOrEqual;
	protected List<Integer> nodeIcos;
	protected String fruleId;
	protected String fruleIdLike;
	protected List<String> fruleIds;
	protected String wcompany;
	protected String wcompanyLike;
	protected List<String> wcompanys;
	protected Integer listNo;
	protected Integer listNoGreaterThanOrEqual;
	protected Integer listNoLessThanOrEqual;
	protected List<Integer> listNos;
	protected String sysId;
	protected String sysIdLike;
	protected List<String> sysIds;
	protected Integer domainIndex;
	protected Integer domainIndexGreaterThanOrEqual;
	protected Integer domainIndexLessThanOrEqual;
	protected List<Integer> domainIndexs;

	public TreepNameQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getId() {
		return id;
	}

	public String getIdLike() {
		if (idLike != null && idLike.trim().length() > 0) {
			if (!idLike.startsWith("%")) {
				idLike = "%" + idLike;
			}
			if (!idLike.endsWith("%")) {
				idLike = idLike + "%";
			}
		}
		return idLike;
	}

	public List<String> getIds() {
		return ids;
	}

	public String getNum() {
		return num;
	}

	public String getNumLike() {
		if (numLike != null && numLike.trim().length() > 0) {
			if (!numLike.startsWith("%")) {
				numLike = "%" + numLike;
			}
			if (!numLike.endsWith("%")) {
				numLike = numLike + "%";
			}
		}
		return numLike;
	}

	public List<String> getNums() {
		return nums;
	}

	// public Integer getParentId() {
	// return parentId;
	// }
	//
	// public Integer getParentIdGreaterThanOrEqual() {
	// return parentIdGreaterThanOrEqual;
	// }
	//
	// public Integer getParentIdLessThanOrEqual() {
	// return parentIdLessThanOrEqual;
	// }
	//
	// public List<Integer> getParentIds() {
	// return parentIds;
	// }

	public String getIndexName() {
		return indexName;
	}

	public String getIndexNameLike() {
		if (indexNameLike != null && indexNameLike.trim().length() > 0) {
			if (!indexNameLike.startsWith("%")) {
				indexNameLike = "%" + indexNameLike;
			}
			if (!indexNameLike.endsWith("%")) {
				indexNameLike = indexNameLike + "%";
			}
		}
		return indexNameLike;
	}

	public List<String> getIndexNames() {
		return indexNames;
	}

	public Integer getLevel() {
		return level;
	}

	public Integer getLevelGreaterThanOrEqual() {
		return levelGreaterThanOrEqual;
	}

	public Integer getLevelLessThanOrEqual() {
		return levelLessThanOrEqual;
	}

	public List<Integer> getLevels() {
		return levels;
	}

	public Integer getShowId() {
		return showId;
	}

	public Integer getShowIdGreaterThanOrEqual() {
		return showIdGreaterThanOrEqual;
	}

	public Integer getShowIdLessThanOrEqual() {
		return showIdLessThanOrEqual;
	}

	public List<Integer> getShowIds() {
		return showIds;
	}

	public String getRuleId() {
		return ruleId;
	}

	public String getRuleIdLike() {
		if (ruleIdLike != null && ruleIdLike.trim().length() > 0) {
			if (!ruleIdLike.startsWith("%")) {
				ruleIdLike = "%" + ruleIdLike;
			}
			if (!ruleIdLike.endsWith("%")) {
				ruleIdLike = ruleIdLike + "%";
			}
		}
		return ruleIdLike;
	}

	public List<String> getRuleIds() {
		return ruleIds;
	}

	public Integer getNodeIco() {
		return nodeIco;
	}

	public Integer getNodeIcoGreaterThanOrEqual() {
		return nodeIcoGreaterThanOrEqual;
	}

	public Integer getNodeIcoLessThanOrEqual() {
		return nodeIcoLessThanOrEqual;
	}

	public List<Integer> getNodeIcos() {
		return nodeIcos;
	}

	public String getFruleId() {
		return fruleId;
	}

	public String getFruleIdLike() {
		if (fruleIdLike != null && fruleIdLike.trim().length() > 0) {
			if (!fruleIdLike.startsWith("%")) {
				fruleIdLike = "%" + fruleIdLike;
			}
			if (!fruleIdLike.endsWith("%")) {
				fruleIdLike = fruleIdLike + "%";
			}
		}
		return fruleIdLike;
	}

	public List<String> getFruleIds() {
		return fruleIds;
	}

	public String getWcompany() {
		return wcompany;
	}

	public String getWcompanyLike() {
		if (wcompanyLike != null && wcompanyLike.trim().length() > 0) {
			if (!wcompanyLike.startsWith("%")) {
				wcompanyLike = "%" + wcompanyLike;
			}
			if (!wcompanyLike.endsWith("%")) {
				wcompanyLike = wcompanyLike + "%";
			}
		}
		return wcompanyLike;
	}

	public List<String> getWcompanys() {
		return wcompanys;
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

	public Integer getDomainIndex() {
		return domainIndex;
	}

	public Integer getDomainIndexGreaterThanOrEqual() {
		return domainIndexGreaterThanOrEqual;
	}

	public Integer getDomainIndexLessThanOrEqual() {
		return domainIndexLessThanOrEqual;
	}

	public List<Integer> getDomainIndexs() {
		return domainIndexs;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIdLike(String idLike) {
		this.idLike = idLike;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public void setNumLike(String numLike) {
		this.numLike = numLike;
	}

	public void setNums(List<String> nums) {
		this.nums = nums;
	}

	// public void setParentId(Integer parentId) {
	// this.parentId = parentId;
	// }
	//
	// public void setParentIdGreaterThanOrEqual(Integer
	// parentIdGreaterThanOrEqual) {
	// this.parentIdGreaterThanOrEqual = parentIdGreaterThanOrEqual;
	// }
	//
	// public void setParentIdLessThanOrEqual(Integer parentIdLessThanOrEqual) {
	// this.parentIdLessThanOrEqual = parentIdLessThanOrEqual;
	// }
	//
	// public void setParentIds(List<Integer> parentIds) {
	// this.parentIds = parentIds;
	// }

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public void setIndexNameLike(String indexNameLike) {
		this.indexNameLike = indexNameLike;
	}

	public void setIndexNames(List<String> indexNames) {
		this.indexNames = indexNames;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public void setLevelGreaterThanOrEqual(Integer levelGreaterThanOrEqual) {
		this.levelGreaterThanOrEqual = levelGreaterThanOrEqual;
	}

	public void setLevelLessThanOrEqual(Integer levelLessThanOrEqual) {
		this.levelLessThanOrEqual = levelLessThanOrEqual;
	}

	public void setLevels(List<Integer> levels) {
		this.levels = levels;
	}

	public void setShowId(Integer showId) {
		this.showId = showId;
	}

	public void setShowIdGreaterThanOrEqual(Integer showIdGreaterThanOrEqual) {
		this.showIdGreaterThanOrEqual = showIdGreaterThanOrEqual;
	}

	public void setShowIdLessThanOrEqual(Integer showIdLessThanOrEqual) {
		this.showIdLessThanOrEqual = showIdLessThanOrEqual;
	}

	public void setShowIds(List<Integer> showIds) {
		this.showIds = showIds;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public void setRuleIdLike(String ruleIdLike) {
		this.ruleIdLike = ruleIdLike;
	}

	public void setRuleIds(List<String> ruleIds) {
		this.ruleIds = ruleIds;
	}

	public void setNodeIco(Integer nodeIco) {
		this.nodeIco = nodeIco;
	}

	public void setNodeIcoGreaterThanOrEqual(Integer nodeIcoGreaterThanOrEqual) {
		this.nodeIcoGreaterThanOrEqual = nodeIcoGreaterThanOrEqual;
	}

	public void setNodeIcoLessThanOrEqual(Integer nodeIcoLessThanOrEqual) {
		this.nodeIcoLessThanOrEqual = nodeIcoLessThanOrEqual;
	}

	public void setNodeIcos(List<Integer> nodeIcos) {
		this.nodeIcos = nodeIcos;
	}

	public void setFruleId(String fruleId) {
		this.fruleId = fruleId;
	}

	public void setFruleIdLike(String fruleIdLike) {
		this.fruleIdLike = fruleIdLike;
	}

	public void setFruleIds(List<String> fruleIds) {
		this.fruleIds = fruleIds;
	}

	public void setWcompany(String wcompany) {
		this.wcompany = wcompany;
	}

	public void setWcompanyLike(String wcompanyLike) {
		this.wcompanyLike = wcompanyLike;
	}

	public void setWcompanys(List<String> wcompanys) {
		this.wcompanys = wcompanys;
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

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public void setSysIdLike(String sysIdLike) {
		this.sysIdLike = sysIdLike;
	}

	public void setSysIds(List<String> sysIds) {
		this.sysIds = sysIds;
	}

	public void setDomainIndex(Integer domainIndex) {
		this.domainIndex = domainIndex;
	}

	public void setDomainIndexGreaterThanOrEqual(
			Integer domainIndexGreaterThanOrEqual) {
		this.domainIndexGreaterThanOrEqual = domainIndexGreaterThanOrEqual;
	}

	public void setDomainIndexLessThanOrEqual(Integer domainIndexLessThanOrEqual) {
		this.domainIndexLessThanOrEqual = domainIndexLessThanOrEqual;
	}

	public void setDomainIndexs(List<Integer> domainIndexs) {
		this.domainIndexs = domainIndexs;
	}

	public TreepNameQuery id(String id) {
		if (id == null) {
			throw new RuntimeException("id is null");
		}
		this.id = id;
		return this;
	}

	public TreepNameQuery idLike(String idLike) {
		if (idLike == null) {
			throw new RuntimeException("id is null");
		}
		this.idLike = idLike;
		return this;
	}

	public TreepNameQuery ids(List<String> ids) {
		if (ids == null) {
			throw new RuntimeException("ids is empty ");
		}
		this.ids = ids;
		return this;
	}

	public TreepNameQuery num(String num) {
		if (num == null) {
			throw new RuntimeException("num is null");
		}
		this.num = num;
		return this;
	}

	public TreepNameQuery numLike(String numLike) {
		if (numLike == null) {
			throw new RuntimeException("num is null");
		}
		this.numLike = numLike;
		return this;
	}

	public TreepNameQuery nums(List<String> nums) {
		if (nums == null) {
			throw new RuntimeException("nums is empty ");
		}
		this.nums = nums;
		return this;
	}

	// public TreepNameQuery parentId(Integer parentId) {
	// if (parentId == null) {
	// throw new RuntimeException("parentId is null");
	// }
	// this.parentId = parentId;
	// return this;
	// }
	//
	// public TreepNameQuery parentIdGreaterThanOrEqual(Integer
	// parentIdGreaterThanOrEqual) {
	// if (parentIdGreaterThanOrEqual == null) {
	// throw new RuntimeException("parentId is null");
	// }
	// this.parentIdGreaterThanOrEqual = parentIdGreaterThanOrEqual;
	// return this;
	// }
	//
	// public TreepNameQuery parentIdLessThanOrEqual(Integer
	// parentIdLessThanOrEqual) {
	// if (parentIdLessThanOrEqual == null) {
	// throw new RuntimeException("parentId is null");
	// }
	// this.parentIdLessThanOrEqual = parentIdLessThanOrEqual;
	// return this;
	// }
	//
	// public TreepNameQuery parentIds(List<Integer> parentIds) {
	// if (parentIds == null) {
	// throw new RuntimeException("parentIds is empty ");
	// }
	// this.parentIds = parentIds;
	// return this;
	// }

	public TreepNameQuery indexName(String indexName) {
		if (indexName == null) {
			throw new RuntimeException("indexName is null");
		}
		this.indexName = indexName;
		return this;
	}

	public TreepNameQuery indexNameLike(String indexNameLike) {
		if (indexNameLike == null) {
			throw new RuntimeException("indexName is null");
		}
		this.indexNameLike = indexNameLike;
		return this;
	}

	public TreepNameQuery indexNames(List<String> indexNames) {
		if (indexNames == null) {
			throw new RuntimeException("indexNames is empty ");
		}
		this.indexNames = indexNames;
		return this;
	}

	public TreepNameQuery level(Integer level) {
		if (level == null) {
			throw new RuntimeException("level is null");
		}
		this.level = level;
		return this;
	}

	public TreepNameQuery levelGreaterThanOrEqual(
			Integer levelGreaterThanOrEqual) {
		if (levelGreaterThanOrEqual == null) {
			throw new RuntimeException("level is null");
		}
		this.levelGreaterThanOrEqual = levelGreaterThanOrEqual;
		return this;
	}

	public TreepNameQuery levelLessThanOrEqual(Integer levelLessThanOrEqual) {
		if (levelLessThanOrEqual == null) {
			throw new RuntimeException("level is null");
		}
		this.levelLessThanOrEqual = levelLessThanOrEqual;
		return this;
	}

	public TreepNameQuery levels(List<Integer> levels) {
		if (levels == null) {
			throw new RuntimeException("levels is empty ");
		}
		this.levels = levels;
		return this;
	}

	public TreepNameQuery showId(Integer showId) {
		if (showId == null) {
			throw new RuntimeException("showId is null");
		}
		this.showId = showId;
		return this;
	}

	public TreepNameQuery showIdGreaterThanOrEqual(
			Integer showIdGreaterThanOrEqual) {
		if (showIdGreaterThanOrEqual == null) {
			throw new RuntimeException("showId is null");
		}
		this.showIdGreaterThanOrEqual = showIdGreaterThanOrEqual;
		return this;
	}

	public TreepNameQuery showIdLessThanOrEqual(Integer showIdLessThanOrEqual) {
		if (showIdLessThanOrEqual == null) {
			throw new RuntimeException("showId is null");
		}
		this.showIdLessThanOrEqual = showIdLessThanOrEqual;
		return this;
	}

	public TreepNameQuery showIds(List<Integer> showIds) {
		if (showIds == null) {
			throw new RuntimeException("showIds is empty ");
		}
		this.showIds = showIds;
		return this;
	}

	public TreepNameQuery ruleId(String ruleId) {
		if (ruleId == null) {
			throw new RuntimeException("ruleId is null");
		}
		this.ruleId = ruleId;
		return this;
	}

	public TreepNameQuery ruleIdLike(String ruleIdLike) {
		if (ruleIdLike == null) {
			throw new RuntimeException("ruleId is null");
		}
		this.ruleIdLike = ruleIdLike;
		return this;
	}

	public TreepNameQuery ruleIds(List<String> ruleIds) {
		if (ruleIds == null) {
			throw new RuntimeException("ruleIds is empty ");
		}
		this.ruleIds = ruleIds;
		return this;
	}

	public TreepNameQuery nodeIco(Integer nodeIco) {
		if (nodeIco == null) {
			throw new RuntimeException("nodeIco is null");
		}
		this.nodeIco = nodeIco;
		return this;
	}

	public TreepNameQuery nodeIcoGreaterThanOrEqual(
			Integer nodeIcoGreaterThanOrEqual) {
		if (nodeIcoGreaterThanOrEqual == null) {
			throw new RuntimeException("nodeIco is null");
		}
		this.nodeIcoGreaterThanOrEqual = nodeIcoGreaterThanOrEqual;
		return this;
	}

	public TreepNameQuery nodeIcoLessThanOrEqual(Integer nodeIcoLessThanOrEqual) {
		if (nodeIcoLessThanOrEqual == null) {
			throw new RuntimeException("nodeIco is null");
		}
		this.nodeIcoLessThanOrEqual = nodeIcoLessThanOrEqual;
		return this;
	}

	public TreepNameQuery nodeIcos(List<Integer> nodeIcos) {
		if (nodeIcos == null) {
			throw new RuntimeException("nodeIcos is empty ");
		}
		this.nodeIcos = nodeIcos;
		return this;
	}

	public TreepNameQuery fruleId(String fruleId) {
		if (fruleId == null) {
			throw new RuntimeException("fruleId is null");
		}
		this.fruleId = fruleId;
		return this;
	}

	public TreepNameQuery fruleIdLike(String fruleIdLike) {
		if (fruleIdLike == null) {
			throw new RuntimeException("fruleId is null");
		}
		this.fruleIdLike = fruleIdLike;
		return this;
	}

	public TreepNameQuery fruleIds(List<String> fruleIds) {
		if (fruleIds == null) {
			throw new RuntimeException("fruleIds is empty ");
		}
		this.fruleIds = fruleIds;
		return this;
	}

	public TreepNameQuery wcompany(String wcompany) {
		if (wcompany == null) {
			throw new RuntimeException("wcompany is null");
		}
		this.wcompany = wcompany;
		return this;
	}

	public TreepNameQuery wcompanyLike(String wcompanyLike) {
		if (wcompanyLike == null) {
			throw new RuntimeException("wcompany is null");
		}
		this.wcompanyLike = wcompanyLike;
		return this;
	}

	public TreepNameQuery wcompanys(List<String> wcompanys) {
		if (wcompanys == null) {
			throw new RuntimeException("wcompanys is empty ");
		}
		this.wcompanys = wcompanys;
		return this;
	}

	public TreepNameQuery listNo(Integer listNo) {
		if (listNo == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNo = listNo;
		return this;
	}

	public TreepNameQuery listNoGreaterThanOrEqual(
			Integer listNoGreaterThanOrEqual) {
		if (listNoGreaterThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoGreaterThanOrEqual = listNoGreaterThanOrEqual;
		return this;
	}

	public TreepNameQuery listNoLessThanOrEqual(Integer listNoLessThanOrEqual) {
		if (listNoLessThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoLessThanOrEqual = listNoLessThanOrEqual;
		return this;
	}

	public TreepNameQuery listNos(List<Integer> listNos) {
		if (listNos == null) {
			throw new RuntimeException("listNos is empty ");
		}
		this.listNos = listNos;
		return this;
	}

	public TreepNameQuery sysId(String sysId) {
		if (sysId == null) {
			throw new RuntimeException("sysId is null");
		}
		this.sysId = sysId;
		return this;
	}

	public TreepNameQuery sysIdLike(String sysIdLike) {
		if (sysIdLike == null) {
			throw new RuntimeException("sysId is null");
		}
		this.sysIdLike = sysIdLike;
		return this;
	}

	public TreepNameQuery sysIds(List<String> sysIds) {
		if (sysIds == null) {
			throw new RuntimeException("sysIds is empty ");
		}
		this.sysIds = sysIds;
		return this;
	}

	public TreepNameQuery domainIndex(Integer domainIndex) {
		if (domainIndex == null) {
			throw new RuntimeException("domainIndex is null");
		}
		this.domainIndex = domainIndex;
		return this;
	}

	public TreepNameQuery domainIndexGreaterThanOrEqual(
			Integer domainIndexGreaterThanOrEqual) {
		if (domainIndexGreaterThanOrEqual == null) {
			throw new RuntimeException("domainIndex is null");
		}
		this.domainIndexGreaterThanOrEqual = domainIndexGreaterThanOrEqual;
		return this;
	}

	public TreepNameQuery domainIndexLessThanOrEqual(
			Integer domainIndexLessThanOrEqual) {
		if (domainIndexLessThanOrEqual == null) {
			throw new RuntimeException("domainIndex is null");
		}
		this.domainIndexLessThanOrEqual = domainIndexLessThanOrEqual;
		return this;
	}

	public TreepNameQuery domainIndexs(List<Integer> domainIndexs) {
		if (domainIndexs == null) {
			throw new RuntimeException("domainIndexs is empty ");
		}
		this.domainIndexs = domainIndexs;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("id".equals(sortColumn)) {
				orderBy = "E.ID" + a_x;
			}

			if ("num".equals(sortColumn)) {
				orderBy = "E.NUM" + a_x;
			}

			if ("parentId".equals(sortColumn)) {
				orderBy = "E.PARENT_ID" + a_x;
			}

			if ("indexName".equals(sortColumn)) {
				orderBy = "E.INDEX_NAME" + a_x;
			}

			if ("level".equals(sortColumn)) {
				orderBy = "E.NLEVEL" + a_x;
			}

			if ("showId".equals(sortColumn)) {
				orderBy = "E.SHOWID" + a_x;
			}

			if ("ruleId".equals(sortColumn)) {
				orderBy = "E.RULEID" + a_x;
			}

			if ("nodeIco".equals(sortColumn)) {
				orderBy = "E.NODEICO" + a_x;
			}

			if ("fruleId".equals(sortColumn)) {
				orderBy = "E.FRULEID" + a_x;
			}

			if ("wcompany".equals(sortColumn)) {
				orderBy = "E.WCOMPANY" + a_x;
			}

			if ("listNo".equals(sortColumn)) {
				orderBy = "E.LISTNO" + a_x;
			}

			if ("sysId".equals(sortColumn)) {
				orderBy = "E.SYS_ID" + a_x;
			}

			if ("domainIndex".equals(sortColumn)) {
				orderBy = "E.DOMAIN_INDEX" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("indexId", "INDEX_ID");
		addColumn("id", "ID");
		addColumn("num", "NUM");
		addColumn("parentId", "PARENT_ID");
		addColumn("indexName", "INDEX_NAME");
		addColumn("level", "NLEVEL");
		addColumn("showId", "SHOWID");
		addColumn("ruleId", "RULEID");
		addColumn("nodeIco", "NODEICO");
		addColumn("fruleId", "FRULEID");
		addColumn("wcompany", "WCOMPANY");
		addColumn("listNo", "LISTNO");
		addColumn("sysId", "SYS_ID");
		addColumn("domainIndex", "DOMAIN_INDEX");
	}

}