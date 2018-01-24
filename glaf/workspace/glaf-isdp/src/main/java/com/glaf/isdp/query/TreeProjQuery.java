package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class TreeProjQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Integer> indexIds;
	protected Collection<String> appActorIds;
	protected String projType;
	protected String projTypeLike;
	protected List<String> projTypes;
	protected String id;
	protected String idLike;
	protected List<String> ids;
	protected Integer topId;
	protected Integer topIdGreaterThanOrEqual;
	protected Integer topIdLessThanOrEqual;
	protected List<Integer> topIds;
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
	protected String num;
	protected String numLike;
	protected List<String> nums;
	protected String content;
	protected String contentLike;
	protected List<String> contents;
	protected String useId;
	protected String useIdLike;
	protected List<String> useIds;
	protected String sindexName;
	protected String sindexNameLike;
	protected List<String> sindexNames;
	protected String content2;
	protected String content2Like;
	protected List<String> content2s;
	protected String topNode;
	protected String topNodeLike;
	protected List<String> topNodes;
	protected Integer nodeIco;
	protected Integer nodeIcoGreaterThanOrEqual;
	protected Integer nodeIcoLessThanOrEqual;
	protected List<Integer> nodeIcos;
	protected String unitNum;
	protected String unitNumLike;
	protected List<String> unitNums;
	protected Integer showId;
	protected Integer showIdGreaterThanOrEqual;
	protected Integer showIdLessThanOrEqual;
	protected List<Integer> showIds;
	protected Double scaleQGreaterThanOrEqual;
	protected Double scaleQLessThanOrEqual;
	protected String isPegWork;
	protected String isPegWorkLike;
	protected List<String> isPegWorks;
	protected String treeProjUser2;
	protected String treeProjUser2Like;
	protected List<String> treeProjUser2s;

	public TreeProjQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getProjType() {
		return projType;
	}

	public String getProjTypeLike() {
		if (projTypeLike != null && projTypeLike.trim().length() > 0) {
			if (!projTypeLike.startsWith("%")) {
				projTypeLike = "%" + projTypeLike;
			}
			if (!projTypeLike.endsWith("%")) {
				projTypeLike = projTypeLike + "%";
			}
		}
		return projTypeLike;
	}

	public List<String> getProjTypes() {
		return projTypes;
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

	public Integer getTopId() {
		return topId;
	}

	public Integer getTopIdGreaterThanOrEqual() {
		return topIdGreaterThanOrEqual;
	}

	public Integer getTopIdLessThanOrEqual() {
		return topIdLessThanOrEqual;
	}

	public List<Integer> getTopIds() {
		return topIds;
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

	public String getUseId() {
		return useId;
	}

	public String getUseIdLike() {
		if (useIdLike != null && useIdLike.trim().length() > 0) {
			if (!useIdLike.startsWith("%")) {
				useIdLike = "%" + useIdLike;
			}
			if (!useIdLike.endsWith("%")) {
				useIdLike = useIdLike + "%";
			}
		}
		return useIdLike;
	}

	public List<String> getUseIds() {
		return useIds;
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

	public String getContent2() {
		return content2;
	}

	public String getContent2Like() {
		if (content2Like != null && content2Like.trim().length() > 0) {
			if (!content2Like.startsWith("%")) {
				content2Like = "%" + content2Like;
			}
			if (!content2Like.endsWith("%")) {
				content2Like = content2Like + "%";
			}
		}
		return content2Like;
	}

	public List<String> getContent2s() {
		return content2s;
	}

	public String getTopNode() {
		return topNode;
	}

	public String getTopNodeLike() {
		if (topNodeLike != null && topNodeLike.trim().length() > 0) {
			if (!topNodeLike.startsWith("%")) {
				topNodeLike = "%" + topNodeLike;
			}
			if (!topNodeLike.endsWith("%")) {
				topNodeLike = topNodeLike + "%";
			}
		}
		return topNodeLike;
	}

	public List<String> getTopNodes() {
		return topNodes;
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

	public String getUnitNum() {
		return unitNum;
	}

	public String getUnitNumLike() {
		if (unitNumLike != null && unitNumLike.trim().length() > 0) {
			if (!unitNumLike.startsWith("%")) {
				unitNumLike = "%" + unitNumLike;
			}
			if (!unitNumLike.endsWith("%")) {
				unitNumLike = unitNumLike + "%";
			}
		}
		return unitNumLike;
	}

	public List<String> getUnitNums() {
		return unitNums;
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

	public Double getScaleQGreaterThanOrEqual() {
		return scaleQGreaterThanOrEqual;
	}

	public Double getScaleQLessThanOrEqual() {
		return scaleQLessThanOrEqual;
	}

	public String getIsPegWork() {
		return isPegWork;
	}

	public String getIsPegWorkLike() {
		if (isPegWorkLike != null && isPegWorkLike.trim().length() > 0) {
			if (!isPegWorkLike.startsWith("%")) {
				isPegWorkLike = "%" + isPegWorkLike;
			}
			if (!isPegWorkLike.endsWith("%")) {
				isPegWorkLike = isPegWorkLike + "%";
			}
		}
		return isPegWorkLike;
	}

	public List<String> getIsPegWorks() {
		return isPegWorks;
	}

	public String getTreeProjUser2() {
		return treeProjUser2;
	}

	public String getTreeProjUser2Like() {
		if (treeProjUser2Like != null && treeProjUser2Like.trim().length() > 0) {
			if (!treeProjUser2Like.startsWith("%")) {
				treeProjUser2Like = "%" + treeProjUser2Like;
			}
			if (!treeProjUser2Like.endsWith("%")) {
				treeProjUser2Like = treeProjUser2Like + "%";
			}
		}
		return treeProjUser2Like;
	}

	public List<String> getTreeProjUser2s() {
		return treeProjUser2s;
	}

	public void setProjType(String projType) {
		this.projType = projType;
	}

	public void setProjTypeLike(String projTypeLike) {
		this.projTypeLike = projTypeLike;
	}

	public void setProjTypes(List<String> projTypes) {
		this.projTypes = projTypes;
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

	public void setTopId(Integer topId) {
		this.topId = topId;
	}

	public void setTopIdGreaterThanOrEqual(Integer topIdGreaterThanOrEqual) {
		this.topIdGreaterThanOrEqual = topIdGreaterThanOrEqual;
	}

	public void setTopIdLessThanOrEqual(Integer topIdLessThanOrEqual) {
		this.topIdLessThanOrEqual = topIdLessThanOrEqual;
	}

	public void setTopIds(List<Integer> topIds) {
		this.topIds = topIds;
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

	public void setUseId(String useId) {
		this.useId = useId;
	}

	public void setUseIdLike(String useIdLike) {
		this.useIdLike = useIdLike;
	}

	public void setUseIds(List<String> useIds) {
		this.useIds = useIds;
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

	public void setContent2(String content2) {
		this.content2 = content2;
	}

	public void setContent2Like(String content2Like) {
		this.content2Like = content2Like;
	}

	public void setContent2s(List<String> content2s) {
		this.content2s = content2s;
	}

	public void setTopNode(String topNode) {
		this.topNode = topNode;
	}

	public void setTopNodeLike(String topNodeLike) {
		this.topNodeLike = topNodeLike;
	}

	public void setTopNodes(List<String> topNodes) {
		this.topNodes = topNodes;
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

	public void setUnitNum(String unitNum) {
		this.unitNum = unitNum;
	}

	public void setUnitNumLike(String unitNumLike) {
		this.unitNumLike = unitNumLike;
	}

	public void setUnitNums(List<String> unitNums) {
		this.unitNums = unitNums;
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

	public void setScaleQGreaterThanOrEqual(Double scaleQGreaterThanOrEqual) {
		this.scaleQGreaterThanOrEqual = scaleQGreaterThanOrEqual;
	}

	public void setScaleQLessThanOrEqual(Double scaleQLessThanOrEqual) {
		this.scaleQLessThanOrEqual = scaleQLessThanOrEqual;
	}

	public void setIsPegWork(String isPegWork) {
		this.isPegWork = isPegWork;
	}

	public void setIsPegWorkLike(String isPegWorkLike) {
		this.isPegWorkLike = isPegWorkLike;
	}

	public void setIsPegWorks(List<String> isPegWorks) {
		this.isPegWorks = isPegWorks;
	}

	public void setTreeProjUser2(String treeProjUser2) {
		this.treeProjUser2 = treeProjUser2;
	}

	public void setTreeProjUser2Like(String treeProjUser2Like) {
		this.treeProjUser2Like = treeProjUser2Like;
	}

	public void setTreeProjUser2s(List<String> treeProjUser2s) {
		this.treeProjUser2s = treeProjUser2s;
	}

	public TreeProjQuery projType(String projType) {
		if (projType == null) {
			throw new RuntimeException("projType is null");
		}
		this.projType = projType;
		return this;
	}

	public TreeProjQuery projTypeLike(String projTypeLike) {
		if (projTypeLike == null) {
			throw new RuntimeException("projType is null");
		}
		this.projTypeLike = projTypeLike;
		return this;
	}

	public TreeProjQuery projTypes(List<String> projTypes) {
		if (projTypes == null) {
			throw new RuntimeException("projTypes is empty ");
		}
		this.projTypes = projTypes;
		return this;
	}

	public TreeProjQuery id(String id) {
		if (id == null) {
			throw new RuntimeException("id is null");
		}
		this.id = id;
		return this;
	}

	public TreeProjQuery idLike(String idLike) {
		if (idLike == null) {
			throw new RuntimeException("id is null");
		}
		this.idLike = idLike;
		return this;
	}

	public TreeProjQuery ids(List<String> ids) {
		if (ids == null) {
			throw new RuntimeException("ids is empty ");
		}
		this.ids = ids;
		return this;
	}

	public TreeProjQuery topId(Integer topId) {
		if (topId == null) {
			throw new RuntimeException("topId is null");
		}
		this.topId = topId;
		return this;
	}

	public TreeProjQuery topIdGreaterThanOrEqual(Integer topIdGreaterThanOrEqual) {
		if (topIdGreaterThanOrEqual == null) {
			throw new RuntimeException("topId is null");
		}
		this.topIdGreaterThanOrEqual = topIdGreaterThanOrEqual;
		return this;
	}

	public TreeProjQuery topIdLessThanOrEqual(Integer topIdLessThanOrEqual) {
		if (topIdLessThanOrEqual == null) {
			throw new RuntimeException("topId is null");
		}
		this.topIdLessThanOrEqual = topIdLessThanOrEqual;
		return this;
	}

	public TreeProjQuery topIds(List<Integer> topIds) {
		if (topIds == null) {
			throw new RuntimeException("topIds is empty ");
		}
		this.topIds = topIds;
		return this;
	}

	// public TreeProjQuery parentId(Integer parentId) {
	// if (parentId == null) {
	// throw new RuntimeException("parentId is null");
	// }
	// this.parentId = parentId;
	// return this;
	// }
	//
	// public TreeProjQuery parentIdGreaterThanOrEqual(Integer
	// parentIdGreaterThanOrEqual) {
	// if (parentIdGreaterThanOrEqual == null) {
	// throw new RuntimeException("parentId is null");
	// }
	// this.parentIdGreaterThanOrEqual = parentIdGreaterThanOrEqual;
	// return this;
	// }
	//
	// public TreeProjQuery parentIdLessThanOrEqual(Integer
	// parentIdLessThanOrEqual) {
	// if (parentIdLessThanOrEqual == null) {
	// throw new RuntimeException("parentId is null");
	// }
	// this.parentIdLessThanOrEqual = parentIdLessThanOrEqual;
	// return this;
	// }
	//
	// public TreeProjQuery parentIds(List<Integer> parentIds) {
	// if (parentIds == null) {
	// throw new RuntimeException("parentIds is empty ");
	// }
	// this.parentIds = parentIds;
	// return this;
	// }

	public TreeProjQuery indexName(String indexName) {
		if (indexName == null) {
			throw new RuntimeException("indexName is null");
		}
		this.indexName = indexName;
		return this;
	}

	public TreeProjQuery indexNameLike(String indexNameLike) {
		if (indexNameLike == null) {
			throw new RuntimeException("indexName is null");
		}
		this.indexNameLike = indexNameLike;
		return this;
	}

	public TreeProjQuery indexNames(List<String> indexNames) {
		if (indexNames == null) {
			throw new RuntimeException("indexNames is empty ");
		}
		this.indexNames = indexNames;
		return this;
	}

	public TreeProjQuery level(Integer level) {
		if (level == null) {
			throw new RuntimeException("level is null");
		}
		this.level = level;
		return this;
	}

	public TreeProjQuery levelGreaterThanOrEqual(Integer levelGreaterThanOrEqual) {
		if (levelGreaterThanOrEqual == null) {
			throw new RuntimeException("level is null");
		}
		this.levelGreaterThanOrEqual = levelGreaterThanOrEqual;
		return this;
	}

	public TreeProjQuery levelLessThanOrEqual(Integer levelLessThanOrEqual) {
		if (levelLessThanOrEqual == null) {
			throw new RuntimeException("level is null");
		}
		this.levelLessThanOrEqual = levelLessThanOrEqual;
		return this;
	}

	public TreeProjQuery levels(List<Integer> levels) {
		if (levels == null) {
			throw new RuntimeException("levels is empty ");
		}
		this.levels = levels;
		return this;
	}

	public TreeProjQuery num(String num) {
		if (num == null) {
			throw new RuntimeException("num is null");
		}
		this.num = num;
		return this;
	}

	public TreeProjQuery numLike(String numLike) {
		if (numLike == null) {
			throw new RuntimeException("num is null");
		}
		this.numLike = numLike;
		return this;
	}

	public TreeProjQuery nums(List<String> nums) {
		if (nums == null) {
			throw new RuntimeException("nums is empty ");
		}
		this.nums = nums;
		return this;
	}

	public TreeProjQuery content(String content) {
		if (content == null) {
			throw new RuntimeException("content is null");
		}
		this.content = content;
		return this;
	}

	public TreeProjQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public TreeProjQuery contents(List<String> contents) {
		if (contents == null) {
			throw new RuntimeException("contents is empty ");
		}
		this.contents = contents;
		return this;
	}

	public TreeProjQuery useId(String useId) {
		if (useId == null) {
			throw new RuntimeException("useId is null");
		}
		this.useId = useId;
		return this;
	}

	public TreeProjQuery useIdLike(String useIdLike) {
		if (useIdLike == null) {
			throw new RuntimeException("useId is null");
		}
		this.useIdLike = useIdLike;
		return this;
	}

	public TreeProjQuery useIds(List<String> useIds) {
		if (useIds == null) {
			throw new RuntimeException("useIds is empty ");
		}
		this.useIds = useIds;
		return this;
	}

	public TreeProjQuery sindexName(String sindexName) {
		if (sindexName == null) {
			throw new RuntimeException("sindexName is null");
		}
		this.sindexName = sindexName;
		return this;
	}

	public TreeProjQuery sindexNameLike(String sindexNameLike) {
		if (sindexNameLike == null) {
			throw new RuntimeException("sindexName is null");
		}
		this.sindexNameLike = sindexNameLike;
		return this;
	}

	public TreeProjQuery sindexNames(List<String> sindexNames) {
		if (sindexNames == null) {
			throw new RuntimeException("sindexNames is empty ");
		}
		this.sindexNames = sindexNames;
		return this;
	}

	public TreeProjQuery content2(String content2) {
		if (content2 == null) {
			throw new RuntimeException("content2 is null");
		}
		this.content2 = content2;
		return this;
	}

	public TreeProjQuery content2Like(String content2Like) {
		if (content2Like == null) {
			throw new RuntimeException("content2 is null");
		}
		this.content2Like = content2Like;
		return this;
	}

	public TreeProjQuery content2s(List<String> content2s) {
		if (content2s == null) {
			throw new RuntimeException("content2s is empty ");
		}
		this.content2s = content2s;
		return this;
	}

	public TreeProjQuery topNode(String topNode) {
		if (topNode == null) {
			throw new RuntimeException("topNode is null");
		}
		this.topNode = topNode;
		return this;
	}

	public TreeProjQuery topNodeLike(String topNodeLike) {
		if (topNodeLike == null) {
			throw new RuntimeException("topNode is null");
		}
		this.topNodeLike = topNodeLike;
		return this;
	}

	public TreeProjQuery topNodes(List<String> topNodes) {
		if (topNodes == null) {
			throw new RuntimeException("topNodes is empty ");
		}
		this.topNodes = topNodes;
		return this;
	}

	public TreeProjQuery nodeIco(Integer nodeIco) {
		if (nodeIco == null) {
			throw new RuntimeException("nodeIco is null");
		}
		this.nodeIco = nodeIco;
		return this;
	}

	public TreeProjQuery nodeIcoGreaterThanOrEqual(
			Integer nodeIcoGreaterThanOrEqual) {
		if (nodeIcoGreaterThanOrEqual == null) {
			throw new RuntimeException("nodeIco is null");
		}
		this.nodeIcoGreaterThanOrEqual = nodeIcoGreaterThanOrEqual;
		return this;
	}

	public TreeProjQuery nodeIcoLessThanOrEqual(Integer nodeIcoLessThanOrEqual) {
		if (nodeIcoLessThanOrEqual == null) {
			throw new RuntimeException("nodeIco is null");
		}
		this.nodeIcoLessThanOrEqual = nodeIcoLessThanOrEqual;
		return this;
	}

	public TreeProjQuery nodeIcos(List<Integer> nodeIcos) {
		if (nodeIcos == null) {
			throw new RuntimeException("nodeIcos is empty ");
		}
		this.nodeIcos = nodeIcos;
		return this;
	}

	public TreeProjQuery unitNum(String unitNum) {
		if (unitNum == null) {
			throw new RuntimeException("unitNum is null");
		}
		this.unitNum = unitNum;
		return this;
	}

	public TreeProjQuery unitNumLike(String unitNumLike) {
		if (unitNumLike == null) {
			throw new RuntimeException("unitNum is null");
		}
		this.unitNumLike = unitNumLike;
		return this;
	}

	public TreeProjQuery unitNums(List<String> unitNums) {
		if (unitNums == null) {
			throw new RuntimeException("unitNums is empty ");
		}
		this.unitNums = unitNums;
		return this;
	}

	public TreeProjQuery showId(Integer showId) {
		if (showId == null) {
			throw new RuntimeException("showId is null");
		}
		this.showId = showId;
		return this;
	}

	public TreeProjQuery showIdGreaterThanOrEqual(
			Integer showIdGreaterThanOrEqual) {
		if (showIdGreaterThanOrEqual == null) {
			throw new RuntimeException("showId is null");
		}
		this.showIdGreaterThanOrEqual = showIdGreaterThanOrEqual;
		return this;
	}

	public TreeProjQuery showIdLessThanOrEqual(Integer showIdLessThanOrEqual) {
		if (showIdLessThanOrEqual == null) {
			throw new RuntimeException("showId is null");
		}
		this.showIdLessThanOrEqual = showIdLessThanOrEqual;
		return this;
	}

	public TreeProjQuery showIds(List<Integer> showIds) {
		if (showIds == null) {
			throw new RuntimeException("showIds is empty ");
		}
		this.showIds = showIds;
		return this;
	}

	public TreeProjQuery scaleQGreaterThanOrEqual(
			Double scaleQGreaterThanOrEqual) {
		if (scaleQGreaterThanOrEqual == null) {
			throw new RuntimeException("scaleQ is null");
		}
		this.scaleQGreaterThanOrEqual = scaleQGreaterThanOrEqual;
		return this;
	}

	public TreeProjQuery scaleQLessThanOrEqual(Double scaleQLessThanOrEqual) {
		if (scaleQLessThanOrEqual == null) {
			throw new RuntimeException("scaleQ is null");
		}
		this.scaleQLessThanOrEqual = scaleQLessThanOrEqual;
		return this;
	}

	public TreeProjQuery isPegWork(String isPegWork) {
		if (isPegWork == null) {
			throw new RuntimeException("isPegWork is null");
		}
		this.isPegWork = isPegWork;
		return this;
	}

	public TreeProjQuery isPegWorkLike(String isPegWorkLike) {
		if (isPegWorkLike == null) {
			throw new RuntimeException("isPegWork is null");
		}
		this.isPegWorkLike = isPegWorkLike;
		return this;
	}

	public TreeProjQuery isPegWorks(List<String> isPegWorks) {
		if (isPegWorks == null) {
			throw new RuntimeException("isPegWorks is empty ");
		}
		this.isPegWorks = isPegWorks;
		return this;
	}

	public TreeProjQuery treeProjUser2(String treeProjUser2) {
		if (treeProjUser2 == null) {
			throw new RuntimeException("treeProjUser2 is null");
		}
		this.treeProjUser2 = treeProjUser2;
		return this;
	}

	public TreeProjQuery treeProjUser2Like(String treeProjUser2Like) {
		if (treeProjUser2Like == null) {
			throw new RuntimeException("treeProjUser2 is null");
		}
		this.treeProjUser2Like = treeProjUser2Like;
		return this;
	}

	public TreeProjQuery treeProjUser2s(List<String> treeProjUser2s) {
		if (treeProjUser2s == null) {
			throw new RuntimeException("treeProjUser2s is empty ");
		}
		this.treeProjUser2s = treeProjUser2s;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("projType".equals(sortColumn)) {
				orderBy = "E.PROJTYPE" + a_x;
			}

			if ("id".equals(sortColumn)) {
				orderBy = "E.ID" + a_x;
			}

			if ("topId".equals(sortColumn)) {
				orderBy = "E.TOPID" + a_x;
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

			if ("num".equals(sortColumn)) {
				orderBy = "E.NUM" + a_x;
			}

			if ("content".equals(sortColumn)) {
				orderBy = "E.CONTENT" + a_x;
			}

			if ("useId".equals(sortColumn)) {
				orderBy = "E.USEID" + a_x;
			}

			if ("sindexName".equals(sortColumn)) {
				orderBy = "E.SINDEX_NAME" + a_x;
			}

			if ("content2".equals(sortColumn)) {
				orderBy = "E.CONTENT2" + a_x;
			}

			if ("topNode".equals(sortColumn)) {
				orderBy = "E.TOPNODE" + a_x;
			}

			if ("nodeIco".equals(sortColumn)) {
				orderBy = "E.NODEICO" + a_x;
			}

			if ("unitNum".equals(sortColumn)) {
				orderBy = "E.UNITNUM" + a_x;
			}

			if ("showId".equals(sortColumn)) {
				orderBy = "E.SHOWID" + a_x;
			}

			if ("scaleQ".equals(sortColumn)) {
				orderBy = "E.SCALE_Q" + a_x;
			}

			if ("isPegWork".equals(sortColumn)) {
				orderBy = "E.ISPEGWORK" + a_x;
			}

			if ("treeProjUser2".equals(sortColumn)) {
				orderBy = "E.TREEPROJ_USER2" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("indexId", "INDEX_ID");
		addColumn("projType", "PROJTYPE");
		addColumn("id", "ID");
		addColumn("topId", "TOPID");
		addColumn("parentId", "PARENT_ID");
		addColumn("indexName", "INDEX_NAME");
		addColumn("level", "NLEVEL");
		addColumn("num", "NUM");
		addColumn("content", "CONTENT");
		addColumn("useId", "USEID");
		addColumn("sindexName", "SINDEX_NAME");
		addColumn("content2", "CONTENT2");
		addColumn("topNode", "TOPNODE");
		addColumn("nodeIco", "NODEICO");
		addColumn("unitNum", "UNITNUM");
		addColumn("showId", "SHOWID");
		addColumn("scaleQ", "SCALE_Q");
		addColumn("isPegWork", "ISPEGWORK");
		addColumn("treeProjUser2", "TREEPROJ_USER2");
	}

}