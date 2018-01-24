package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class CellUTableTreeQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected Integer indexId;
	protected List<Integer> indexIds;
	protected Collection<String> appActorIds;
	protected String id;
	protected String idLike;
	protected List<String> ids;
	// protected Integer parentId;
	// protected Integer parentIdGreaterThanOrEqual;
	// protected Integer parentIdLessThanOrEqual;
	// protected List<Integer> parentIds;
	protected String indexName;
	protected String indexNameLike;
	protected List<String> indexNames;
	protected Integer nlevel;
	protected Integer nlevelGreaterThanOrEqual;
	protected Integer nlevelLessThanOrEqual;
	protected List<Integer> nlevels;
	protected Integer nodeIco;
	protected Integer nodeIcoGreaterThanOrEqual;
	protected Integer nodeIcoLessThanOrEqual;
	protected List<Integer> nodeIcos;
	protected Integer listNo;
	protected Integer listNoGreaterThanOrEqual;
	protected Integer listNoLessThanOrEqual;
	protected List<Integer> listNos;
	protected Integer tableType;
	protected Integer tableTypeGreaterThanOrEqual;
	protected Integer tableTypeLessThanOrEqual;
	protected List<Integer> tableTypes;
	protected Integer intDel;
	protected Integer intDelGreaterThanOrEqual;
	protected Integer intDelLessThanOrEqual;
	protected List<Integer> intDels;
	protected String busiessId;
	protected String busiessIdLike;
	protected List<String> busiessIds;
	protected String content;
	protected String contentLike;
	protected List<String> contents;
	protected String num;
	protected String numLike;
	protected List<String> nums;
	protected Integer menuIndex;
	protected Integer menuIndexGreaterThanOrEqual;
	protected Integer menuIndexLessThanOrEqual;
	protected List<Integer> menuIndexs;
	protected Integer domainIndex;
	protected Integer domainIndexGreaterThanOrEqual;
	protected Integer domainIndexLessThanOrEqual;
	protected List<Integer> domainIndexs;
	protected Integer winWidth;
	protected Integer winWidthGreaterThanOrEqual;
	protected Integer winWidthLessThanOrEqual;
	protected List<Integer> winWidths;
	protected Integer winHeight;
	protected Integer winHeightGreaterThanOrEqual;
	protected Integer winHeightLessThanOrEqual;
	protected List<Integer> winHeights;
	
	protected String sqlCondition;

	public CellUTableTreeQuery() {

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

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public List<Integer> getIndexIds() {
		return indexIds;
	}

	public void setIndexIds(List<Integer> indexIds) {
		this.indexIds = indexIds;
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

	//
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

	public Integer getNlevel() {
		return nlevel;
	}

	public Integer getNlevelGreaterThanOrEqual() {
		return nlevelGreaterThanOrEqual;
	}

	public Integer getNlevelLessThanOrEqual() {
		return nlevelLessThanOrEqual;
	}

	public List<Integer> getNlevels() {
		return nlevels;
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

	public Integer getTableType() {
		return tableType;
	}

	public Integer getTableTypeGreaterThanOrEqual() {
		return tableTypeGreaterThanOrEqual;
	}

	public Integer getTableTypeLessThanOrEqual() {
		return tableTypeLessThanOrEqual;
	}

	public List<Integer> getTableTypes() {
		return tableTypes;
	}

	public Integer getIntDel() {
		return intDel;
	}

	public Integer getIntDelGreaterThanOrEqual() {
		return intDelGreaterThanOrEqual;
	}

	public Integer getIntDelLessThanOrEqual() {
		return intDelLessThanOrEqual;
	}

	public List<Integer> getIntDels() {
		return intDels;
	}

	public String getBusiessId() {
		return busiessId;
	}

	public String getBusiessIdLike() {
		if (busiessIdLike != null && busiessIdLike.trim().length() > 0) {
			if (!busiessIdLike.startsWith("%")) {
				busiessIdLike = "%" + busiessIdLike;
			}
			if (!busiessIdLike.endsWith("%")) {
				busiessIdLike = busiessIdLike + "%";
			}
		}
		return busiessIdLike;
	}

	public List<String> getBusiessIds() {
		return busiessIds;
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

	public Integer getMenuIndex() {
		return menuIndex;
	}

	public Integer getMenuIndexGreaterThanOrEqual() {
		return menuIndexGreaterThanOrEqual;
	}

	public Integer getMenuIndexLessThanOrEqual() {
		return menuIndexLessThanOrEqual;
	}

	public List<Integer> getMenuIndexs() {
		return menuIndexs;
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

	public Integer getWinWidth() {
		return winWidth;
	}

	public Integer getWinWidthGreaterThanOrEqual() {
		return winWidthGreaterThanOrEqual;
	}

	public Integer getWinWidthLessThanOrEqual() {
		return winWidthLessThanOrEqual;
	}

	public List<Integer> getWinWidths() {
		return winWidths;
	}

	public Integer getWinHeight() {
		return winHeight;
	}

	public Integer getWinHeightGreaterThanOrEqual() {
		return winHeightGreaterThanOrEqual;
	}

	public Integer getWinHeightLessThanOrEqual() {
		return winHeightLessThanOrEqual;
	}

	public List<Integer> getWinHeights() {
		return winHeights;
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

	//
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

	public void setNlevel(Integer nlevel) {
		this.nlevel = nlevel;
	}

	public void setNlevelGreaterThanOrEqual(Integer nlevelGreaterThanOrEqual) {
		this.nlevelGreaterThanOrEqual = nlevelGreaterThanOrEqual;
	}

	public void setNlevelLessThanOrEqual(Integer nlevelLessThanOrEqual) {
		this.nlevelLessThanOrEqual = nlevelLessThanOrEqual;
	}

	public void setNlevels(List<Integer> nlevels) {
		this.nlevels = nlevels;
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

	public void setTableType(Integer tableType) {
		this.tableType = tableType;
	}

	public void setTableTypeGreaterThanOrEqual(Integer tableTypeGreaterThanOrEqual) {
		this.tableTypeGreaterThanOrEqual = tableTypeGreaterThanOrEqual;
	}

	public void setTableTypeLessThanOrEqual(Integer tableTypeLessThanOrEqual) {
		this.tableTypeLessThanOrEqual = tableTypeLessThanOrEqual;
	}

	public void setTableTypes(List<Integer> tableTypes) {
		this.tableTypes = tableTypes;
	}

	public void setIntDel(Integer intDel) {
		this.intDel = intDel;
	}

	public void setIntDelGreaterThanOrEqual(Integer intDelGreaterThanOrEqual) {
		this.intDelGreaterThanOrEqual = intDelGreaterThanOrEqual;
	}

	public void setIntDelLessThanOrEqual(Integer intDelLessThanOrEqual) {
		this.intDelLessThanOrEqual = intDelLessThanOrEqual;
	}

	public void setIntDels(List<Integer> intDels) {
		this.intDels = intDels;
	}

	public void setBusiessId(String busiessId) {
		this.busiessId = busiessId;
	}

	public void setBusiessIdLike(String busiessIdLike) {
		this.busiessIdLike = busiessIdLike;
	}

	public void setBusiessIds(List<String> busiessIds) {
		this.busiessIds = busiessIds;
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

	public void setNum(String num) {
		this.num = num;
	}

	public void setNumLike(String numLike) {
		this.numLike = numLike;
	}

	public void setNums(List<String> nums) {
		this.nums = nums;
	}

	public void setMenuIndex(Integer menuIndex) {
		this.menuIndex = menuIndex;
	}

	public void setMenuIndexGreaterThanOrEqual(Integer menuIndexGreaterThanOrEqual) {
		this.menuIndexGreaterThanOrEqual = menuIndexGreaterThanOrEqual;
	}

	public void setMenuIndexLessThanOrEqual(Integer menuIndexLessThanOrEqual) {
		this.menuIndexLessThanOrEqual = menuIndexLessThanOrEqual;
	}

	public void setMenuIndexs(List<Integer> menuIndexs) {
		this.menuIndexs = menuIndexs;
	}

	public void setDomainIndex(Integer domainIndex) {
		this.domainIndex = domainIndex;
	}

	public void setDomainIndexGreaterThanOrEqual(Integer domainIndexGreaterThanOrEqual) {
		this.domainIndexGreaterThanOrEqual = domainIndexGreaterThanOrEqual;
	}

	public void setDomainIndexLessThanOrEqual(Integer domainIndexLessThanOrEqual) {
		this.domainIndexLessThanOrEqual = domainIndexLessThanOrEqual;
	}

	public void setDomainIndexs(List<Integer> domainIndexs) {
		this.domainIndexs = domainIndexs;
	}

	public void setWinWidth(Integer winWidth) {
		this.winWidth = winWidth;
	}

	public void setWinWidthGreaterThanOrEqual(Integer winWidthGreaterThanOrEqual) {
		this.winWidthGreaterThanOrEqual = winWidthGreaterThanOrEqual;
	}

	public void setWinWidthLessThanOrEqual(Integer winWidthLessThanOrEqual) {
		this.winWidthLessThanOrEqual = winWidthLessThanOrEqual;
	}

	public void setWinWidths(List<Integer> winWidths) {
		this.winWidths = winWidths;
	}

	public void setWinHeight(Integer winHeight) {
		this.winHeight = winHeight;
	}

	public void setWinHeightGreaterThanOrEqual(Integer winHeightGreaterThanOrEqual) {
		this.winHeightGreaterThanOrEqual = winHeightGreaterThanOrEqual;
	}

	public void setWinHeightLessThanOrEqual(Integer winHeightLessThanOrEqual) {
		this.winHeightLessThanOrEqual = winHeightLessThanOrEqual;
	}

	public void setWinHeights(List<Integer> winHeights) {
		this.winHeights = winHeights;
	}

	public String getSqlCondition() {
		return sqlCondition;
	}

	public void setSqlCondition(String sqlCondition) {
		this.sqlCondition = sqlCondition;
	}

	public CellUTableTreeQuery id(String id) {
		if (id == null) {
			throw new RuntimeException("id is null");
		}
		this.id = id;
		return this;
	}

	public CellUTableTreeQuery idLike(String idLike) {
		if (idLike == null) {
			throw new RuntimeException("id is null");
		}
		this.idLike = idLike;
		return this;
	}

	public CellUTableTreeQuery ids(List<String> ids) {
		if (ids == null) {
			throw new RuntimeException("ids is empty ");
		}
		this.ids = ids;
		return this;
	}

	// public CellUTableTreeQuery parentId(Integer parentId) {
	// if (parentId == null) {
	// throw new RuntimeException("parentId is null");
	// }
	// this.parentId = parentId;
	// return this;
	// }
	//
	// public CellUTableTreeQuery parentIdGreaterThanOrEqual(Integer
	// parentIdGreaterThanOrEqual) {
	// if (parentIdGreaterThanOrEqual == null) {
	// throw new RuntimeException("parentId is null");
	// }
	// this.parentIdGreaterThanOrEqual = parentIdGreaterThanOrEqual;
	// return this;
	// }
	//
	// public CellUTableTreeQuery parentIdLessThanOrEqual(Integer
	// parentIdLessThanOrEqual) {
	// if (parentIdLessThanOrEqual == null) {
	// throw new RuntimeException("parentId is null");
	// }
	// this.parentIdLessThanOrEqual = parentIdLessThanOrEqual;
	// return this;
	// }
	//
	// public CellUTableTreeQuery parentIds(List<Integer> parentIds) {
	// if (parentIds == null) {
	// throw new RuntimeException("parentIds is empty ");
	// }
	// this.parentIds = parentIds;
	// return this;
	// }

	public CellUTableTreeQuery indexName(String indexName) {
		if (indexName == null) {
			throw new RuntimeException("indexName is null");
		}
		this.indexName = indexName;
		return this;
	}

	public CellUTableTreeQuery indexNameLike(String indexNameLike) {
		if (indexNameLike == null) {
			throw new RuntimeException("indexName is null");
		}
		this.indexNameLike = indexNameLike;
		return this;
	}

	public CellUTableTreeQuery indexNames(List<String> indexNames) {
		if (indexNames == null) {
			throw new RuntimeException("indexNames is empty ");
		}
		this.indexNames = indexNames;
		return this;
	}

	public CellUTableTreeQuery nlevel(Integer nlevel) {
		if (nlevel == null) {
			throw new RuntimeException("nlevel is null");
		}
		this.nlevel = nlevel;
		return this;
	}

	public CellUTableTreeQuery nlevelGreaterThanOrEqual(Integer nlevelGreaterThanOrEqual) {
		if (nlevelGreaterThanOrEqual == null) {
			throw new RuntimeException("nlevel is null");
		}
		this.nlevelGreaterThanOrEqual = nlevelGreaterThanOrEqual;
		return this;
	}

	public CellUTableTreeQuery nlevelLessThanOrEqual(Integer nlevelLessThanOrEqual) {
		if (nlevelLessThanOrEqual == null) {
			throw new RuntimeException("nlevel is null");
		}
		this.nlevelLessThanOrEqual = nlevelLessThanOrEqual;
		return this;
	}

	public CellUTableTreeQuery nlevels(List<Integer> nlevels) {
		if (nlevels == null) {
			throw new RuntimeException("nlevels is empty ");
		}
		this.nlevels = nlevels;
		return this;
	}

	public CellUTableTreeQuery nodeIco(Integer nodeIco) {
		if (nodeIco == null) {
			throw new RuntimeException("nodeIco is null");
		}
		this.nodeIco = nodeIco;
		return this;
	}

	public CellUTableTreeQuery nodeIcoGreaterThanOrEqual(Integer nodeIcoGreaterThanOrEqual) {
		if (nodeIcoGreaterThanOrEqual == null) {
			throw new RuntimeException("nodeIco is null");
		}
		this.nodeIcoGreaterThanOrEqual = nodeIcoGreaterThanOrEqual;
		return this;
	}

	public CellUTableTreeQuery nodeIcoLessThanOrEqual(Integer nodeIcoLessThanOrEqual) {
		if (nodeIcoLessThanOrEqual == null) {
			throw new RuntimeException("nodeIco is null");
		}
		this.nodeIcoLessThanOrEqual = nodeIcoLessThanOrEqual;
		return this;
	}

	public CellUTableTreeQuery nodeIcos(List<Integer> nodeIcos) {
		if (nodeIcos == null) {
			throw new RuntimeException("nodeIcos is empty ");
		}
		this.nodeIcos = nodeIcos;
		return this;
	}

	public CellUTableTreeQuery listNo(Integer listNo) {
		if (listNo == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNo = listNo;
		return this;
	}

	public CellUTableTreeQuery listNoGreaterThanOrEqual(Integer listNoGreaterThanOrEqual) {
		if (listNoGreaterThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoGreaterThanOrEqual = listNoGreaterThanOrEqual;
		return this;
	}

	public CellUTableTreeQuery listNoLessThanOrEqual(Integer listNoLessThanOrEqual) {
		if (listNoLessThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoLessThanOrEqual = listNoLessThanOrEqual;
		return this;
	}

	public CellUTableTreeQuery listNos(List<Integer> listNos) {
		if (listNos == null) {
			throw new RuntimeException("listNos is empty ");
		}
		this.listNos = listNos;
		return this;
	}

	public CellUTableTreeQuery tableType(Integer tableType) {
		if (tableType == null) {
			throw new RuntimeException("tableType is null");
		}
		this.tableType = tableType;
		return this;
	}

	public CellUTableTreeQuery tableTypeGreaterThanOrEqual(Integer tableTypeGreaterThanOrEqual) {
		if (tableTypeGreaterThanOrEqual == null) {
			throw new RuntimeException("tableType is null");
		}
		this.tableTypeGreaterThanOrEqual = tableTypeGreaterThanOrEqual;
		return this;
	}

	public CellUTableTreeQuery tableTypeLessThanOrEqual(Integer tableTypeLessThanOrEqual) {
		if (tableTypeLessThanOrEqual == null) {
			throw new RuntimeException("tableType is null");
		}
		this.tableTypeLessThanOrEqual = tableTypeLessThanOrEqual;
		return this;
	}

	public CellUTableTreeQuery tableTypes(List<Integer> tableTypes) {
		if (tableTypes == null) {
			throw new RuntimeException("tableTypes is empty ");
		}
		this.tableTypes = tableTypes;
		return this;
	}

	public CellUTableTreeQuery intDel(Integer intDel) {
		if (intDel == null) {
			throw new RuntimeException("intDel is null");
		}
		this.intDel = intDel;
		return this;
	}

	public CellUTableTreeQuery intDelGreaterThanOrEqual(Integer intDelGreaterThanOrEqual) {
		if (intDelGreaterThanOrEqual == null) {
			throw new RuntimeException("intDel is null");
		}
		this.intDelGreaterThanOrEqual = intDelGreaterThanOrEqual;
		return this;
	}

	public CellUTableTreeQuery intDelLessThanOrEqual(Integer intDelLessThanOrEqual) {
		if (intDelLessThanOrEqual == null) {
			throw new RuntimeException("intDel is null");
		}
		this.intDelLessThanOrEqual = intDelLessThanOrEqual;
		return this;
	}

	public CellUTableTreeQuery intDels(List<Integer> intDels) {
		if (intDels == null) {
			throw new RuntimeException("intDels is empty ");
		}
		this.intDels = intDels;
		return this;
	}

	public CellUTableTreeQuery busiessId(String busiessId) {
		if (busiessId == null) {
			throw new RuntimeException("busiessId is null");
		}
		this.busiessId = busiessId;
		return this;
	}

	public CellUTableTreeQuery busiessIdLike(String busiessIdLike) {
		if (busiessIdLike == null) {
			throw new RuntimeException("busiessId is null");
		}
		this.busiessIdLike = busiessIdLike;
		return this;
	}

	public CellUTableTreeQuery busiessIds(List<String> busiessIds) {
		if (busiessIds == null) {
			throw new RuntimeException("busiessIds is empty ");
		}
		this.busiessIds = busiessIds;
		return this;
	}

	public CellUTableTreeQuery content(String content) {
		if (content == null) {
			throw new RuntimeException("content is null");
		}
		this.content = content;
		return this;
	}

	public CellUTableTreeQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public CellUTableTreeQuery contents(List<String> contents) {
		if (contents == null) {
			throw new RuntimeException("contents is empty ");
		}
		this.contents = contents;
		return this;
	}

	public CellUTableTreeQuery num(String num) {
		if (num == null) {
			throw new RuntimeException("num is null");
		}
		this.num = num;
		return this;
	}

	public CellUTableTreeQuery numLike(String numLike) {
		if (numLike == null) {
			throw new RuntimeException("num is null");
		}
		this.numLike = numLike;
		return this;
	}

	public CellUTableTreeQuery nums(List<String> nums) {
		if (nums == null) {
			throw new RuntimeException("nums is empty ");
		}
		this.nums = nums;
		return this;
	}

	public CellUTableTreeQuery menuIndex(Integer menuIndex) {
		if (menuIndex == null) {
			throw new RuntimeException("menuIndex is null");
		}
		this.menuIndex = menuIndex;
		return this;
	}

	public CellUTableTreeQuery menuIndexGreaterThanOrEqual(Integer menuIndexGreaterThanOrEqual) {
		if (menuIndexGreaterThanOrEqual == null) {
			throw new RuntimeException("menuIndex is null");
		}
		this.menuIndexGreaterThanOrEqual = menuIndexGreaterThanOrEqual;
		return this;
	}

	public CellUTableTreeQuery menuIndexLessThanOrEqual(Integer menuIndexLessThanOrEqual) {
		if (menuIndexLessThanOrEqual == null) {
			throw new RuntimeException("menuIndex is null");
		}
		this.menuIndexLessThanOrEqual = menuIndexLessThanOrEqual;
		return this;
	}

	public CellUTableTreeQuery menuIndexs(List<Integer> menuIndexs) {
		if (menuIndexs == null) {
			throw new RuntimeException("menuIndexs is empty ");
		}
		this.menuIndexs = menuIndexs;
		return this;
	}

	public CellUTableTreeQuery domainIndex(Integer domainIndex) {
		if (domainIndex == null) {
			throw new RuntimeException("domainIndex is null");
		}
		this.domainIndex = domainIndex;
		return this;
	}

	public CellUTableTreeQuery domainIndexGreaterThanOrEqual(Integer domainIndexGreaterThanOrEqual) {
		if (domainIndexGreaterThanOrEqual == null) {
			throw new RuntimeException("domainIndex is null");
		}
		this.domainIndexGreaterThanOrEqual = domainIndexGreaterThanOrEqual;
		return this;
	}

	public CellUTableTreeQuery domainIndexLessThanOrEqual(Integer domainIndexLessThanOrEqual) {
		if (domainIndexLessThanOrEqual == null) {
			throw new RuntimeException("domainIndex is null");
		}
		this.domainIndexLessThanOrEqual = domainIndexLessThanOrEqual;
		return this;
	}

	public CellUTableTreeQuery domainIndexs(List<Integer> domainIndexs) {
		if (domainIndexs == null) {
			throw new RuntimeException("domainIndexs is empty ");
		}
		this.domainIndexs = domainIndexs;
		return this;
	}

	public CellUTableTreeQuery winWidth(Integer winWidth) {
		if (winWidth == null) {
			throw new RuntimeException("winWidth is null");
		}
		this.winWidth = winWidth;
		return this;
	}

	public CellUTableTreeQuery winWidthGreaterThanOrEqual(Integer winWidthGreaterThanOrEqual) {
		if (winWidthGreaterThanOrEqual == null) {
			throw new RuntimeException("winWidth is null");
		}
		this.winWidthGreaterThanOrEqual = winWidthGreaterThanOrEqual;
		return this;
	}

	public CellUTableTreeQuery winWidthLessThanOrEqual(Integer winWidthLessThanOrEqual) {
		if (winWidthLessThanOrEqual == null) {
			throw new RuntimeException("winWidth is null");
		}
		this.winWidthLessThanOrEqual = winWidthLessThanOrEqual;
		return this;
	}

	public CellUTableTreeQuery winWidths(List<Integer> winWidths) {
		if (winWidths == null) {
			throw new RuntimeException("winWidths is empty ");
		}
		this.winWidths = winWidths;
		return this;
	}

	public CellUTableTreeQuery winHeight(Integer winHeight) {
		if (winHeight == null) {
			throw new RuntimeException("winHeight is null");
		}
		this.winHeight = winHeight;
		return this;
	}

	public CellUTableTreeQuery winHeightGreaterThanOrEqual(Integer winHeightGreaterThanOrEqual) {
		if (winHeightGreaterThanOrEqual == null) {
			throw new RuntimeException("winHeight is null");
		}
		this.winHeightGreaterThanOrEqual = winHeightGreaterThanOrEqual;
		return this;
	}

	public CellUTableTreeQuery winHeightLessThanOrEqual(Integer winHeightLessThanOrEqual) {
		if (winHeightLessThanOrEqual == null) {
			throw new RuntimeException("winHeight is null");
		}
		this.winHeightLessThanOrEqual = winHeightLessThanOrEqual;
		return this;
	}

	public CellUTableTreeQuery winHeights(List<Integer> winHeights) {
		if (winHeights == null) {
			throw new RuntimeException("winHeights is empty ");
		}
		this.winHeights = winHeights;
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

			if ("parentId".equals(sortColumn)) {
				orderBy = "E.PARENT_ID" + a_x;
			}

			if ("indexName".equals(sortColumn)) {
				orderBy = "E.INDEX_NAME" + a_x;
			}

			if ("nlevel".equals(sortColumn)) {
				orderBy = "E.NLEVEL" + a_x;
			}

			if ("nodeIco".equals(sortColumn)) {
				orderBy = "E.NODEICO" + a_x;
			}

			if ("listNo".equals(sortColumn)) {
				orderBy = "E.LISTNO" + a_x;
			}

			if ("tableType".equals(sortColumn)) {
				orderBy = "E.TABLETYPE" + a_x;
			}

			if ("intDel".equals(sortColumn)) {
				orderBy = "E.INTDEL" + a_x;
			}

			if ("busiessId".equals(sortColumn)) {
				orderBy = "E.BUSIESS_ID" + a_x;
			}

			if ("content".equals(sortColumn)) {
				orderBy = "E.CONTENT" + a_x;
			}

			if ("num".equals(sortColumn)) {
				orderBy = "E.NUM" + a_x;
			}

			if ("menuIndex".equals(sortColumn)) {
				orderBy = "E.MENUINDEX" + a_x;
			}

			if ("domainIndex".equals(sortColumn)) {
				orderBy = "E.DOMAIN_INDEX" + a_x;
			}

			if ("winWidth".equals(sortColumn)) {
				orderBy = "E.WIN_WIDTH" + a_x;
			}

			if ("winHeight".equals(sortColumn)) {
				orderBy = "E.WIN_HEIGHT" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("indexId", "INDEX_ID");
		addColumn("id", "ID");
		addColumn("parentId", "PARENT_ID");
		addColumn("indexName", "INDEX_NAME");
		addColumn("nlevel", "NLEVEL");
		addColumn("nodeIco", "NODEICO");
		addColumn("listNo", "LISTNO");
		addColumn("tableType", "TABLETYPE");
		addColumn("intDel", "INTDEL");
		addColumn("busiessId", "BUSIESS_ID");
		addColumn("content", "CONTENT");
		addColumn("num", "NUM");
		addColumn("menuIndex", "MENUINDEX");
		addColumn("domainIndex", "DOMAIN_INDEX");
		addColumn("winWidth", "WIN_WIDTH");
		addColumn("winHeight", "WIN_HEIGHT");
	}

}