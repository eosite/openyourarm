package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class TreeDotQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
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
	protected String num;
	protected String numLike;
	protected List<String> nums;
	protected String content;
	protected String contentLike;
	protected List<String> contents;
	protected String sindexName;
	protected String sindexNameLike;
	protected List<String> sindexNames;
	protected Integer nodeIco;
	protected Integer nodeIcoGreaterThanOrEqual;
	protected Integer nodeIcoLessThanOrEqual;
	protected List<Integer> nodeIcos;
	protected Integer listNo;
	protected Integer listNoGreaterThanOrEqual;
	protected Integer listNoLessThanOrEqual;
	protected List<Integer> listNos;
	protected Integer menuId;
	protected Integer menuIdGreaterThanOrEqual;
	protected Integer menuIdLessThanOrEqual;
	protected List<Integer> menuIds;
	protected Integer isEnd;
	protected Integer isEndGreaterThanOrEqual;
	protected Integer isEndLessThanOrEqual;
	protected List<Integer> isEnds;
	protected String sysMenuId;
	protected String sysMenuIdLike;
	protected List<String> sysMenuIds;
	protected Integer type;
	protected Integer typeGreaterThanOrEqual;
	protected Integer typeLessThanOrEqual;
	protected List<Integer> types;
	protected String fileNumId;
	protected String fileNumIdLike;
	protected List<String> fileNumIds;
	protected String fileNumId2;
	protected String fileNumId2Like;
	protected List<String> fileNumId2s;
	protected Integer projIndex;
	protected Integer projIndexGreaterThanOrEqual;
	protected Integer projIndexLessThanOrEqual;
	protected List<Integer> projIndexs;
	protected Integer domainIndex;
	protected Integer domainIndexGreaterThanOrEqual;
	protected Integer domainIndexLessThanOrEqual;
	protected List<Integer> domainIndexs;

	public TreeDotQuery() {

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

	public String getSindexName() {
		return sindexName;
	}

	public String getSindexNameLike() {
		if (sindexNameLike != null && sindexNameLike.trim().length() > 0) {
			if (!sindexNameLike.startsWith("%")) {
				sindexNameLike = "%" + sindexNameLike;
			}
			if (!sindexNameLike.endsWith("%")) {
				sindexNameLike = sindexNameLike + "%";
			}
		}
		return sindexNameLike;
	}

	public List<String> getSindexNames() {
		return sindexNames;
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

	public Integer getMenuId() {
		return menuId;
	}

	public Integer getMenuIdGreaterThanOrEqual() {
		return menuIdGreaterThanOrEqual;
	}

	public Integer getMenuIdLessThanOrEqual() {
		return menuIdLessThanOrEqual;
	}

	public List<Integer> getMenuIds() {
		return menuIds;
	}

	public Integer getIsEnd() {
		return isEnd;
	}

	public Integer getIsEndGreaterThanOrEqual() {
		return isEndGreaterThanOrEqual;
	}

	public Integer getIsEndLessThanOrEqual() {
		return isEndLessThanOrEqual;
	}

	public List<Integer> getIsEnds() {
		return isEnds;
	}

	public String getSysMenuId() {
		return sysMenuId;
	}

	public String getSysMenuIdLike() {
		if (sysMenuIdLike != null && sysMenuIdLike.trim().length() > 0) {
			if (!sysMenuIdLike.startsWith("%")) {
				sysMenuIdLike = "%" + sysMenuIdLike;
			}
			if (!sysMenuIdLike.endsWith("%")) {
				sysMenuIdLike = sysMenuIdLike + "%";
			}
		}
		return sysMenuIdLike;
	}

	public List<String> getSysMenuIds() {
		return sysMenuIds;
	}

	public Integer getType() {
		return type;
	}

	public Integer getTypeGreaterThanOrEqual() {
		return typeGreaterThanOrEqual;
	}

	public Integer getTypeLessThanOrEqual() {
		return typeLessThanOrEqual;
	}

	public List<Integer> getTypes() {
		return types;
	}

	public String getFileNumId() {
		return fileNumId;
	}

	public String getFileNumIdLike() {
		if (fileNumIdLike != null && fileNumIdLike.trim().length() > 0) {
			if (!fileNumIdLike.startsWith("%")) {
				fileNumIdLike = "%" + fileNumIdLike;
			}
			if (!fileNumIdLike.endsWith("%")) {
				fileNumIdLike = fileNumIdLike + "%";
			}
		}
		return fileNumIdLike;
	}

	public List<String> getFileNumIds() {
		return fileNumIds;
	}

	public String getFileNumId2() {
		return fileNumId2;
	}

	public String getFileNumId2Like() {
		if (fileNumId2Like != null && fileNumId2Like.trim().length() > 0) {
			if (!fileNumId2Like.startsWith("%")) {
				fileNumId2Like = "%" + fileNumId2Like;
			}
			if (!fileNumId2Like.endsWith("%")) {
				fileNumId2Like = fileNumId2Like + "%";
			}
		}
		return fileNumId2Like;
	}

	public List<String> getFileNumId2s() {
		return fileNumId2s;
	}

	public Integer getProjIndex() {
		return projIndex;
	}

	public Integer getProjIndexGreaterThanOrEqual() {
		return projIndexGreaterThanOrEqual;
	}

	public Integer getProjIndexLessThanOrEqual() {
		return projIndexLessThanOrEqual;
	}

	public List<Integer> getProjIndexs() {
		return projIndexs;
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

	public void setNum(String num) {
		this.num = num;
	}

	public void setNumLike(String numLike) {
		this.numLike = numLike;
	}

	public void setNums(List<String> nums) {
		this.nums = nums;
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

	public void setSindexName(String sindexName) {
		this.sindexName = sindexName;
	}

	public void setSindexNameLike(String sindexNameLike) {
		this.sindexNameLike = sindexNameLike;
	}

	public void setSindexNames(List<String> sindexNames) {
		this.sindexNames = sindexNames;
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

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public void setMenuIdGreaterThanOrEqual(Integer menuIdGreaterThanOrEqual) {
		this.menuIdGreaterThanOrEqual = menuIdGreaterThanOrEqual;
	}

	public void setMenuIdLessThanOrEqual(Integer menuIdLessThanOrEqual) {
		this.menuIdLessThanOrEqual = menuIdLessThanOrEqual;
	}

	public void setMenuIds(List<Integer> menuIds) {
		this.menuIds = menuIds;
	}

	public void setIsEnd(Integer isEnd) {
		this.isEnd = isEnd;
	}

	public void setIsEndGreaterThanOrEqual(Integer isEndGreaterThanOrEqual) {
		this.isEndGreaterThanOrEqual = isEndGreaterThanOrEqual;
	}

	public void setIsEndLessThanOrEqual(Integer isEndLessThanOrEqual) {
		this.isEndLessThanOrEqual = isEndLessThanOrEqual;
	}

	public void setIsEnds(List<Integer> isEnds) {
		this.isEnds = isEnds;
	}

	public void setSysMenuId(String sysMenuId) {
		this.sysMenuId = sysMenuId;
	}

	public void setSysMenuIdLike(String sysMenuIdLike) {
		this.sysMenuIdLike = sysMenuIdLike;
	}

	public void setSysMenuIds(List<String> sysMenuIds) {
		this.sysMenuIds = sysMenuIds;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setTypeGreaterThanOrEqual(Integer typeGreaterThanOrEqual) {
		this.typeGreaterThanOrEqual = typeGreaterThanOrEqual;
	}

	public void setTypeLessThanOrEqual(Integer typeLessThanOrEqual) {
		this.typeLessThanOrEqual = typeLessThanOrEqual;
	}

	public void setTypes(List<Integer> types) {
		this.types = types;
	}

	public void setFileNumId(String fileNumId) {
		this.fileNumId = fileNumId;
	}

	public void setFileNumIdLike(String fileNumIdLike) {
		this.fileNumIdLike = fileNumIdLike;
	}

	public void setFileNumIds(List<String> fileNumIds) {
		this.fileNumIds = fileNumIds;
	}

	public void setFileNumId2(String fileNumId2) {
		this.fileNumId2 = fileNumId2;
	}

	public void setFileNumId2Like(String fileNumId2Like) {
		this.fileNumId2Like = fileNumId2Like;
	}

	public void setFileNumId2s(List<String> fileNumId2s) {
		this.fileNumId2s = fileNumId2s;
	}

	public void setProjIndex(Integer projIndex) {
		this.projIndex = projIndex;
	}

	public void setProjIndexGreaterThanOrEqual(
			Integer projIndexGreaterThanOrEqual) {
		this.projIndexGreaterThanOrEqual = projIndexGreaterThanOrEqual;
	}

	public void setProjIndexLessThanOrEqual(Integer projIndexLessThanOrEqual) {
		this.projIndexLessThanOrEqual = projIndexLessThanOrEqual;
	}

	public void setProjIndexs(List<Integer> projIndexs) {
		this.projIndexs = projIndexs;
	}

	public void setDomainIndex(Integer domainIndex) {
		this.domainIndex = domainIndex;
	}

	public void setDomainIndexGreaterThanOrEqual(
			Integer domainIndexGreaterThanOrEqual) {
		this.domainIndexGreaterThanOrEqual = domainIndexGreaterThanOrEqual;
	}

	public void setDomainIndexLessThanOrEqual(
			Integer domainIndexLessThanOrEqual) {
		this.domainIndexLessThanOrEqual = domainIndexLessThanOrEqual;
	}

	public void setDomainIndexs(List<Integer> domainIndexs) {
		this.domainIndexs = domainIndexs;
	}

	public TreeDotQuery id(String id) {
		if (id == null) {
			throw new RuntimeException("id is null");
		}
		this.id = id;
		return this;
	}

	public TreeDotQuery idLike(String idLike) {
		if (idLike == null) {
			throw new RuntimeException("id is null");
		}
		this.idLike = idLike;
		return this;
	}

	public TreeDotQuery ids(List<String> ids) {
		if (ids == null) {
			throw new RuntimeException("ids is empty ");
		}
		this.ids = ids;
		return this;
	}

	//
	// public TreeDotQuery parentId(Integer parentId) {
	// if (parentId == null) {
	// throw new RuntimeException("parentId is null");
	// }
	// this.parentId = parentId;
	// return this;
	// }
	//
	// public TreeDotQuery parentIdGreaterThanOrEqual(Integer
	// parentIdGreaterThanOrEqual) {
	// if (parentIdGreaterThanOrEqual == null) {
	// throw new RuntimeException("parentId is null");
	// }
	// this.parentIdGreaterThanOrEqual = parentIdGreaterThanOrEqual;
	// return this;
	// }
	//
	// public TreeDotQuery parentIdLessThanOrEqual(Integer
	// parentIdLessThanOrEqual) {
	// if (parentIdLessThanOrEqual == null) {
	// throw new RuntimeException("parentId is null");
	// }
	// this.parentIdLessThanOrEqual = parentIdLessThanOrEqual;
	// return this;
	// }
	//
	// public TreeDotQuery parentIds(List<Integer> parentIds) {
	// if (parentIds == null) {
	// throw new RuntimeException("parentIds is empty ");
	// }
	// this.parentIds = parentIds;
	// return this;
	// }

	public TreeDotQuery indexName(String indexName) {
		if (indexName == null) {
			throw new RuntimeException("indexName is null");
		}
		this.indexName = indexName;
		return this;
	}

	public TreeDotQuery indexNameLike(String indexNameLike) {
		if (indexNameLike == null) {
			throw new RuntimeException("indexName is null");
		}
		this.indexNameLike = indexNameLike;
		return this;
	}

	public TreeDotQuery indexNames(List<String> indexNames) {
		if (indexNames == null) {
			throw new RuntimeException("indexNames is empty ");
		}
		this.indexNames = indexNames;
		return this;
	}

	public TreeDotQuery nlevel(Integer nlevel) {
		if (nlevel == null) {
			throw new RuntimeException("nlevel is null");
		}
		this.nlevel = nlevel;
		return this;
	}

	public TreeDotQuery nlevelGreaterThanOrEqual(Integer nlevelGreaterThanOrEqual) {
		if (nlevelGreaterThanOrEqual == null) {
			throw new RuntimeException("nlevel is null");
		}
		this.nlevelGreaterThanOrEqual = nlevelGreaterThanOrEqual;
		return this;
	}

	public TreeDotQuery nlevelLessThanOrEqual(Integer nlevelLessThanOrEqual) {
		if (nlevelLessThanOrEqual == null) {
			throw new RuntimeException("nlevel is null");
		}
		this.nlevelLessThanOrEqual = nlevelLessThanOrEqual;
		return this;
	}

	public TreeDotQuery nlevels(List<Integer> nlevels) {
		if (nlevels == null) {
			throw new RuntimeException("nlevels is empty ");
		}
		this.nlevels = nlevels;
		return this;
	}

	public TreeDotQuery num(String num) {
		if (num == null) {
			throw new RuntimeException("num is null");
		}
		this.num = num;
		return this;
	}

	public TreeDotQuery numLike(String numLike) {
		if (numLike == null) {
			throw new RuntimeException("num is null");
		}
		this.numLike = numLike;
		return this;
	}

	public TreeDotQuery nums(List<String> nums) {
		if (nums == null) {
			throw new RuntimeException("nums is empty ");
		}
		this.nums = nums;
		return this;
	}

	public TreeDotQuery content(String content) {
		if (content == null) {
			throw new RuntimeException("content is null");
		}
		this.content = content;
		return this;
	}

	public TreeDotQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public TreeDotQuery contents(List<String> contents) {
		if (contents == null) {
			throw new RuntimeException("contents is empty ");
		}
		this.contents = contents;
		return this;
	}

	public TreeDotQuery sindexName(String sindexName) {
		if (sindexName == null) {
			throw new RuntimeException("sindexName is null");
		}
		this.sindexName = sindexName;
		return this;
	}

	public TreeDotQuery sindexNameLike(String sindexNameLike) {
		if (sindexNameLike == null) {
			throw new RuntimeException("sindexName is null");
		}
		this.sindexNameLike = sindexNameLike;
		return this;
	}

	public TreeDotQuery sindexNames(List<String> sindexNames) {
		if (sindexNames == null) {
			throw new RuntimeException("sindexNames is empty ");
		}
		this.sindexNames = sindexNames;
		return this;
	}

	public TreeDotQuery nodeIco(Integer nodeIco) {
		if (nodeIco == null) {
			throw new RuntimeException("nodeIco is null");
		}
		this.nodeIco = nodeIco;
		return this;
	}

	public TreeDotQuery nodeIcoGreaterThanOrEqual(
			Integer nodeIcoGreaterThanOrEqual) {
		if (nodeIcoGreaterThanOrEqual == null) {
			throw new RuntimeException("nodeIco is null");
		}
		this.nodeIcoGreaterThanOrEqual = nodeIcoGreaterThanOrEqual;
		return this;
	}

	public TreeDotQuery nodeIcoLessThanOrEqual(Integer nodeIcoLessThanOrEqual) {
		if (nodeIcoLessThanOrEqual == null) {
			throw new RuntimeException("nodeIco is null");
		}
		this.nodeIcoLessThanOrEqual = nodeIcoLessThanOrEqual;
		return this;
	}

	public TreeDotQuery nodeIcos(List<Integer> nodeIcos) {
		if (nodeIcos == null) {
			throw new RuntimeException("nodeIcos is empty ");
		}
		this.nodeIcos = nodeIcos;
		return this;
	}

	public TreeDotQuery listNo(Integer listNo) {
		if (listNo == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNo = listNo;
		return this;
	}

	public TreeDotQuery listNoGreaterThanOrEqual(
			Integer listNoGreaterThanOrEqual) {
		if (listNoGreaterThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoGreaterThanOrEqual = listNoGreaterThanOrEqual;
		return this;
	}

	public TreeDotQuery listNoLessThanOrEqual(Integer listNoLessThanOrEqual) {
		if (listNoLessThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoLessThanOrEqual = listNoLessThanOrEqual;
		return this;
	}

	public TreeDotQuery listNos(List<Integer> listNos) {
		if (listNos == null) {
			throw new RuntimeException("listNos is empty ");
		}
		this.listNos = listNos;
		return this;
	}

	public TreeDotQuery menuId(Integer menuId) {
		if (menuId == null) {
			throw new RuntimeException("menuId is null");
		}
		this.menuId = menuId;
		return this;
	}

	public TreeDotQuery menuIdGreaterThanOrEqual(
			Integer menuIdGreaterThanOrEqual) {
		if (menuIdGreaterThanOrEqual == null) {
			throw new RuntimeException("menuId is null");
		}
		this.menuIdGreaterThanOrEqual = menuIdGreaterThanOrEqual;
		return this;
	}

	public TreeDotQuery menuIdLessThanOrEqual(Integer menuIdLessThanOrEqual) {
		if (menuIdLessThanOrEqual == null) {
			throw new RuntimeException("menuId is null");
		}
		this.menuIdLessThanOrEqual = menuIdLessThanOrEqual;
		return this;
	}

	public TreeDotQuery menuIds(List<Integer> menuIds) {
		if (menuIds == null) {
			throw new RuntimeException("menuIds is empty ");
		}
		this.menuIds = menuIds;
		return this;
	}

	public TreeDotQuery isEnd(Integer isEnd) {
		if (isEnd == null) {
			throw new RuntimeException("isEnd is null");
		}
		this.isEnd = isEnd;
		return this;
	}

	public TreeDotQuery isEndGreaterThanOrEqual(Integer isEndGreaterThanOrEqual) {
		if (isEndGreaterThanOrEqual == null) {
			throw new RuntimeException("isEnd is null");
		}
		this.isEndGreaterThanOrEqual = isEndGreaterThanOrEqual;
		return this;
	}

	public TreeDotQuery isEndLessThanOrEqual(Integer isEndLessThanOrEqual) {
		if (isEndLessThanOrEqual == null) {
			throw new RuntimeException("isEnd is null");
		}
		this.isEndLessThanOrEqual = isEndLessThanOrEqual;
		return this;
	}

	public TreeDotQuery isEnds(List<Integer> isEnds) {
		if (isEnds == null) {
			throw new RuntimeException("isEnds is empty ");
		}
		this.isEnds = isEnds;
		return this;
	}

	public TreeDotQuery sysMenuId(String sysMenuId) {
		if (sysMenuId == null) {
			throw new RuntimeException("sysMenuId is null");
		}
		this.sysMenuId = sysMenuId;
		return this;
	}

	public TreeDotQuery sysMenuIdLike(String sysMenuIdLike) {
		if (sysMenuIdLike == null) {
			throw new RuntimeException("sysMenuId is null");
		}
		this.sysMenuIdLike = sysMenuIdLike;
		return this;
	}

	public TreeDotQuery sysMenuIds(List<String> sysMenuIds) {
		if (sysMenuIds == null) {
			throw new RuntimeException("sysMenuIds is empty ");
		}
		this.sysMenuIds = sysMenuIds;
		return this;
	}

	public TreeDotQuery type(Integer type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public TreeDotQuery typeGreaterThanOrEqual(Integer typeGreaterThanOrEqual) {
		if (typeGreaterThanOrEqual == null) {
			throw new RuntimeException("type is null");
		}
		this.typeGreaterThanOrEqual = typeGreaterThanOrEqual;
		return this;
	}

	public TreeDotQuery typeLessThanOrEqual(Integer typeLessThanOrEqual) {
		if (typeLessThanOrEqual == null) {
			throw new RuntimeException("type is null");
		}
		this.typeLessThanOrEqual = typeLessThanOrEqual;
		return this;
	}

	public TreeDotQuery types(List<Integer> types) {
		if (types == null) {
			throw new RuntimeException("types is empty ");
		}
		this.types = types;
		return this;
	}

	public TreeDotQuery fileNumId(String fileNumId) {
		if (fileNumId == null) {
			throw new RuntimeException("fileNumId is null");
		}
		this.fileNumId = fileNumId;
		return this;
	}

	public TreeDotQuery fileNumIdLike(String fileNumIdLike) {
		if (fileNumIdLike == null) {
			throw new RuntimeException("fileNumId is null");
		}
		this.fileNumIdLike = fileNumIdLike;
		return this;
	}

	public TreeDotQuery fileNumIds(List<String> fileNumIds) {
		if (fileNumIds == null) {
			throw new RuntimeException("fileNumIds is empty ");
		}
		this.fileNumIds = fileNumIds;
		return this;
	}

	public TreeDotQuery fileNumId2(String fileNumId2) {
		if (fileNumId2 == null) {
			throw new RuntimeException("fileNumId2 is null");
		}
		this.fileNumId2 = fileNumId2;
		return this;
	}

	public TreeDotQuery fileNumId2Like(String fileNumId2Like) {
		if (fileNumId2Like == null) {
			throw new RuntimeException("fileNumId2 is null");
		}
		this.fileNumId2Like = fileNumId2Like;
		return this;
	}

	public TreeDotQuery fileNumId2s(List<String> fileNumId2s) {
		if (fileNumId2s == null) {
			throw new RuntimeException("fileNumId2s is empty ");
		}
		this.fileNumId2s = fileNumId2s;
		return this;
	}

	public TreeDotQuery projIndex(Integer projIndex) {
		if (projIndex == null) {
			throw new RuntimeException("projIndex is null");
		}
		this.projIndex = projIndex;
		return this;
	}

	public TreeDotQuery projIndexGreaterThanOrEqual(
			Integer projIndexGreaterThanOrEqual) {
		if (projIndexGreaterThanOrEqual == null) {
			throw new RuntimeException("projIndex is null");
		}
		this.projIndexGreaterThanOrEqual = projIndexGreaterThanOrEqual;
		return this;
	}

	public TreeDotQuery projIndexLessThanOrEqual(
			Integer projIndexLessThanOrEqual) {
		if (projIndexLessThanOrEqual == null) {
			throw new RuntimeException("projIndex is null");
		}
		this.projIndexLessThanOrEqual = projIndexLessThanOrEqual;
		return this;
	}

	public TreeDotQuery projIndexs(List<Integer> projIndexs) {
		if (projIndexs == null) {
			throw new RuntimeException("projIndexs is empty ");
		}
		this.projIndexs = projIndexs;
		return this;
	}

	public TreeDotQuery domainIndex(Integer domainIndex) {
		if (domainIndex == null) {
			throw new RuntimeException("domainIndex is null");
		}
		this.domainIndex = domainIndex;
		return this;
	}

	public TreeDotQuery domainIndexGreaterThanOrEqual(
			Integer domainIndexGreaterThanOrEqual) {
		if (domainIndexGreaterThanOrEqual == null) {
			throw new RuntimeException("domainIndex is null");
		}
		this.domainIndexGreaterThanOrEqual = domainIndexGreaterThanOrEqual;
		return this;
	}

	public TreeDotQuery domainIndexLessThanOrEqual(
			Integer domainIndexLessThanOrEqual) {
		if (domainIndexLessThanOrEqual == null) {
			throw new RuntimeException("domainIndex is null");
		}
		this.domainIndexLessThanOrEqual = domainIndexLessThanOrEqual;
		return this;
	}

	public TreeDotQuery domainIndexs(List<Integer> domainIndexs) {
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

			if ("parentId".equals(sortColumn)) {
				orderBy = "E.PARENT_ID" + a_x;
			}

			if ("indexName".equals(sortColumn)) {
				orderBy = "E.INDEX_NAME" + a_x;
			}

			if ("nlevel".equals(sortColumn)) {
				orderBy = "E.NLEVEL" + a_x;
			}

			if ("num".equals(sortColumn)) {
				orderBy = "E.NUM" + a_x;
			}

			if ("content".equals(sortColumn)) {
				orderBy = "E.CONTENT" + a_x;
			}

			if ("sindexName".equals(sortColumn)) {
				orderBy = "E.SINDEX_NAME" + a_x;
			}

			if ("nodeIco".equals(sortColumn)) {
				orderBy = "E.NODEICO" + a_x;
			}

			if ("listNo".equals(sortColumn)) {
				orderBy = "E.LISTNO" + a_x;
			}

			if ("menuId".equals(sortColumn)) {
				orderBy = "E.MENUID" + a_x;
			}

			if ("isEnd".equals(sortColumn)) {
				orderBy = "E.ISEND" + a_x;
			}

			if ("sysMenuId".equals(sortColumn)) {
				orderBy = "E.SYSMENUID" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE" + a_x;
			}

			if ("fileNumId".equals(sortColumn)) {
				orderBy = "E.FILENUMID" + a_x;
			}

			if ("fileNumId2".equals(sortColumn)) {
				orderBy = "E.FILENUMID2" + a_x;
			}

			if ("projIndex".equals(sortColumn)) {
				orderBy = "E.PROJ_INDEX" + a_x;
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
		addColumn("parentId", "PARENT_ID");
		addColumn("indexName", "INDEX_NAME");
		addColumn("nlevel", "NLEVEL");
		addColumn("num", "NUM");
		addColumn("content", "CONTENT");
		addColumn("sindexName", "SINDEX_NAME");
		addColumn("nodeIco", "NODEICO");
		addColumn("listNo", "LISTNO");
		addColumn("menuId", "MENUID");
		addColumn("isEnd", "ISEND");
		addColumn("sysMenuId", "SYSMENUID");
		addColumn("type", "TYPE");
		addColumn("fileNumId", "FILENUMID");
		addColumn("fileNumId2", "FILENUMID2");
		addColumn("projIndex", "PROJ_INDEX");
		addColumn("domainIndex", "DOMAIN_INDEX");
	}

}