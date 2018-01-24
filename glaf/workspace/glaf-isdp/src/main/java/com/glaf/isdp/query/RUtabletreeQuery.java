package com.glaf.isdp.query;

import java.util.Collection;
import java.util.List;

import com.glaf.core.query.DataQuery;

public class RUtabletreeQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Integer> indexIds;
	protected Collection<String> appActorIds;
	protected String id;
	protected String idLike;
	protected List<String> ids;
	protected Integer parentId;
	protected Integer parentIdGreaterThanOrEqual;
	protected Integer parentIdLessThanOrEqual;
	protected List<Integer> parentIds;
	protected String indexName;
	protected String indexNameLike;
	protected List<String> indexNames;
	protected Integer nlevel;
	protected Integer nlevelGreaterThanOrEqual;
	protected Integer nlevelLessThanOrEqual;
	protected List<Integer> nlevels;
	protected Integer nodeico;
	protected Integer nodeicoGreaterThanOrEqual;
	protected Integer nodeicoLessThanOrEqual;
	protected List<Integer> nodeicos;
	protected Integer listno;
	protected Integer listnoGreaterThanOrEqual;
	protected Integer listnoLessThanOrEqual;
	protected List<Integer> listnos;
	protected Integer tabletype;
	protected Integer tabletypeGreaterThanOrEqual;
	protected Integer tabletypeLessThanOrEqual;
	protected List<Integer> tabletypes;
	protected Integer intdel;
	protected Integer intdelGreaterThanOrEqual;
	protected Integer intdelLessThanOrEqual;
	protected List<Integer> intdels;
	protected String busiessId;
	protected String busiessIdLike;
	protected List<String> busiessIds;
	protected String content;
	protected String contentLike;
	protected List<String> contents;
	protected String num;
	protected String numLike;
	protected List<String> nums;
	protected Integer menuindex;
	protected Integer menuindexGreaterThanOrEqual;
	protected Integer menuindexLessThanOrEqual;
	protected List<Integer> menuindexs;
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

	public RUtabletreeQuery() {

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

	public Integer getParentIdGreaterThanOrEqual() {
		return parentIdGreaterThanOrEqual;
	}

	public Integer getParentIdLessThanOrEqual() {
		return parentIdLessThanOrEqual;
	}

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

	public Integer getNodeico() {
		return nodeico;
	}

	public Integer getNodeicoGreaterThanOrEqual() {
		return nodeicoGreaterThanOrEqual;
	}

	public Integer getNodeicoLessThanOrEqual() {
		return nodeicoLessThanOrEqual;
	}

	public List<Integer> getNodeicos() {
		return nodeicos;
	}

	public Integer getListno() {
		return listno;
	}

	public Integer getListnoGreaterThanOrEqual() {
		return listnoGreaterThanOrEqual;
	}

	public Integer getListnoLessThanOrEqual() {
		return listnoLessThanOrEqual;
	}

	public List<Integer> getListnos() {
		return listnos;
	}

	public Integer getTabletype() {
		return tabletype;
	}

	public Integer getTabletypeGreaterThanOrEqual() {
		return tabletypeGreaterThanOrEqual;
	}

	public Integer getTabletypeLessThanOrEqual() {
		return tabletypeLessThanOrEqual;
	}

	public List<Integer> getTabletypes() {
		return tabletypes;
	}

	public Integer getIntdel() {
		return intdel;
	}

	public Integer getIntdelGreaterThanOrEqual() {
		return intdelGreaterThanOrEqual;
	}

	public Integer getIntdelLessThanOrEqual() {
		return intdelLessThanOrEqual;
	}

	public List<Integer> getIntdels() {
		return intdels;
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

	public Integer getMenuindex() {
		return menuindex;
	}

	public Integer getMenuindexGreaterThanOrEqual() {
		return menuindexGreaterThanOrEqual;
	}

	public Integer getMenuindexLessThanOrEqual() {
		return menuindexLessThanOrEqual;
	}

	public List<Integer> getMenuindexs() {
		return menuindexs;
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

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public void setParentIdGreaterThanOrEqual(Integer parentIdGreaterThanOrEqual) {
		this.parentIdGreaterThanOrEqual = parentIdGreaterThanOrEqual;
	}

	public void setParentIdLessThanOrEqual(Integer parentIdLessThanOrEqual) {
		this.parentIdLessThanOrEqual = parentIdLessThanOrEqual;
	}

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

	public void setNodeico(Integer nodeico) {
		this.nodeico = nodeico;
	}

	public void setNodeicoGreaterThanOrEqual(Integer nodeicoGreaterThanOrEqual) {
		this.nodeicoGreaterThanOrEqual = nodeicoGreaterThanOrEqual;
	}

	public void setNodeicoLessThanOrEqual(Integer nodeicoLessThanOrEqual) {
		this.nodeicoLessThanOrEqual = nodeicoLessThanOrEqual;
	}

	public void setNodeicos(List<Integer> nodeicos) {
		this.nodeicos = nodeicos;
	}

	public void setListno(Integer listno) {
		this.listno = listno;
	}

	public void setListnoGreaterThanOrEqual(Integer listnoGreaterThanOrEqual) {
		this.listnoGreaterThanOrEqual = listnoGreaterThanOrEqual;
	}

	public void setListnoLessThanOrEqual(Integer listnoLessThanOrEqual) {
		this.listnoLessThanOrEqual = listnoLessThanOrEqual;
	}

	public void setListnos(List<Integer> listnos) {
		this.listnos = listnos;
	}

	public void setTabletype(Integer tabletype) {
		this.tabletype = tabletype;
	}

	public void setTabletypeGreaterThanOrEqual(Integer tabletypeGreaterThanOrEqual) {
		this.tabletypeGreaterThanOrEqual = tabletypeGreaterThanOrEqual;
	}

	public void setTabletypeLessThanOrEqual(Integer tabletypeLessThanOrEqual) {
		this.tabletypeLessThanOrEqual = tabletypeLessThanOrEqual;
	}

	public void setTabletypes(List<Integer> tabletypes) {
		this.tabletypes = tabletypes;
	}

	public void setIntdel(Integer intdel) {
		this.intdel = intdel;
	}

	public void setIntdelGreaterThanOrEqual(Integer intdelGreaterThanOrEqual) {
		this.intdelGreaterThanOrEqual = intdelGreaterThanOrEqual;
	}

	public void setIntdelLessThanOrEqual(Integer intdelLessThanOrEqual) {
		this.intdelLessThanOrEqual = intdelLessThanOrEqual;
	}

	public void setIntdels(List<Integer> intdels) {
		this.intdels = intdels;
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

	public void setMenuindex(Integer menuindex) {
		this.menuindex = menuindex;
	}

	public void setMenuindexGreaterThanOrEqual(Integer menuindexGreaterThanOrEqual) {
		this.menuindexGreaterThanOrEqual = menuindexGreaterThanOrEqual;
	}

	public void setMenuindexLessThanOrEqual(Integer menuindexLessThanOrEqual) {
		this.menuindexLessThanOrEqual = menuindexLessThanOrEqual;
	}

	public void setMenuindexs(List<Integer> menuindexs) {
		this.menuindexs = menuindexs;
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

	public RUtabletreeQuery id(String id) {
		if (id == null) {
			throw new RuntimeException("id is null");
		}
		this.id = id;
		return this;
	}

	public RUtabletreeQuery idLike(String idLike) {
		if (idLike == null) {
			throw new RuntimeException("id is null");
		}
		this.idLike = idLike;
		return this;
	}

	public RUtabletreeQuery ids(List<String> ids) {
		if (ids == null) {
			throw new RuntimeException("ids is empty ");
		}
		this.ids = ids;
		return this;
	}

	public RUtabletreeQuery parentId(Integer parentId) {
		if (parentId == null) {
			throw new RuntimeException("parentId is null");
		}
		this.parentId = parentId;
		return this;
	}

	public RUtabletreeQuery parentIdGreaterThanOrEqual(Integer parentIdGreaterThanOrEqual) {
		if (parentIdGreaterThanOrEqual == null) {
			throw new RuntimeException("parentId is null");
		}
		this.parentIdGreaterThanOrEqual = parentIdGreaterThanOrEqual;
		return this;
	}

	public RUtabletreeQuery parentIdLessThanOrEqual(Integer parentIdLessThanOrEqual) {
		if (parentIdLessThanOrEqual == null) {
			throw new RuntimeException("parentId is null");
		}
		this.parentIdLessThanOrEqual = parentIdLessThanOrEqual;
		return this;
	}

	public RUtabletreeQuery indexName(String indexName) {
		if (indexName == null) {
			throw new RuntimeException("indexName is null");
		}
		this.indexName = indexName;
		return this;
	}

	public RUtabletreeQuery indexNameLike(String indexNameLike) {
		if (indexNameLike == null) {
			throw new RuntimeException("indexName is null");
		}
		this.indexNameLike = indexNameLike;
		return this;
	}

	public RUtabletreeQuery indexNames(List<String> indexNames) {
		if (indexNames == null) {
			throw new RuntimeException("indexNames is empty ");
		}
		this.indexNames = indexNames;
		return this;
	}

	public RUtabletreeQuery nlevel(Integer nlevel) {
		if (nlevel == null) {
			throw new RuntimeException("nlevel is null");
		}
		this.nlevel = nlevel;
		return this;
	}

	public RUtabletreeQuery nlevelGreaterThanOrEqual(Integer nlevelGreaterThanOrEqual) {
		if (nlevelGreaterThanOrEqual == null) {
			throw new RuntimeException("nlevel is null");
		}
		this.nlevelGreaterThanOrEqual = nlevelGreaterThanOrEqual;
		return this;
	}

	public RUtabletreeQuery nlevelLessThanOrEqual(Integer nlevelLessThanOrEqual) {
		if (nlevelLessThanOrEqual == null) {
			throw new RuntimeException("nlevel is null");
		}
		this.nlevelLessThanOrEqual = nlevelLessThanOrEqual;
		return this;
	}

	public RUtabletreeQuery nlevels(List<Integer> nlevels) {
		if (nlevels == null) {
			throw new RuntimeException("nlevels is empty ");
		}
		this.nlevels = nlevels;
		return this;
	}

	public RUtabletreeQuery nodeico(Integer nodeico) {
		if (nodeico == null) {
			throw new RuntimeException("nodeico is null");
		}
		this.nodeico = nodeico;
		return this;
	}

	public RUtabletreeQuery nodeicoGreaterThanOrEqual(Integer nodeicoGreaterThanOrEqual) {
		if (nodeicoGreaterThanOrEqual == null) {
			throw new RuntimeException("nodeico is null");
		}
		this.nodeicoGreaterThanOrEqual = nodeicoGreaterThanOrEqual;
		return this;
	}

	public RUtabletreeQuery nodeicoLessThanOrEqual(Integer nodeicoLessThanOrEqual) {
		if (nodeicoLessThanOrEqual == null) {
			throw new RuntimeException("nodeico is null");
		}
		this.nodeicoLessThanOrEqual = nodeicoLessThanOrEqual;
		return this;
	}

	public RUtabletreeQuery nodeicos(List<Integer> nodeicos) {
		if (nodeicos == null) {
			throw new RuntimeException("nodeicos is empty ");
		}
		this.nodeicos = nodeicos;
		return this;
	}

	public RUtabletreeQuery listno(Integer listno) {
		if (listno == null) {
			throw new RuntimeException("listno is null");
		}
		this.listno = listno;
		return this;
	}

	public RUtabletreeQuery listnoGreaterThanOrEqual(Integer listnoGreaterThanOrEqual) {
		if (listnoGreaterThanOrEqual == null) {
			throw new RuntimeException("listno is null");
		}
		this.listnoGreaterThanOrEqual = listnoGreaterThanOrEqual;
		return this;
	}

	public RUtabletreeQuery listnoLessThanOrEqual(Integer listnoLessThanOrEqual) {
		if (listnoLessThanOrEqual == null) {
			throw new RuntimeException("listno is null");
		}
		this.listnoLessThanOrEqual = listnoLessThanOrEqual;
		return this;
	}

	public RUtabletreeQuery listnos(List<Integer> listnos) {
		if (listnos == null) {
			throw new RuntimeException("listnos is empty ");
		}
		this.listnos = listnos;
		return this;
	}

	public RUtabletreeQuery tabletype(Integer tabletype) {
		if (tabletype == null) {
			throw new RuntimeException("tabletype is null");
		}
		this.tabletype = tabletype;
		return this;
	}

	public RUtabletreeQuery tabletypeGreaterThanOrEqual(Integer tabletypeGreaterThanOrEqual) {
		if (tabletypeGreaterThanOrEqual == null) {
			throw new RuntimeException("tabletype is null");
		}
		this.tabletypeGreaterThanOrEqual = tabletypeGreaterThanOrEqual;
		return this;
	}

	public RUtabletreeQuery tabletypeLessThanOrEqual(Integer tabletypeLessThanOrEqual) {
		if (tabletypeLessThanOrEqual == null) {
			throw new RuntimeException("tabletype is null");
		}
		this.tabletypeLessThanOrEqual = tabletypeLessThanOrEqual;
		return this;
	}

	public RUtabletreeQuery tabletypes(List<Integer> tabletypes) {
		if (tabletypes == null) {
			throw new RuntimeException("tabletypes is empty ");
		}
		this.tabletypes = tabletypes;
		return this;
	}

	public RUtabletreeQuery intdel(Integer intdel) {
		if (intdel == null) {
			throw new RuntimeException("intdel is null");
		}
		this.intdel = intdel;
		return this;
	}

	public RUtabletreeQuery intdelGreaterThanOrEqual(Integer intdelGreaterThanOrEqual) {
		if (intdelGreaterThanOrEqual == null) {
			throw new RuntimeException("intdel is null");
		}
		this.intdelGreaterThanOrEqual = intdelGreaterThanOrEqual;
		return this;
	}

	public RUtabletreeQuery intdelLessThanOrEqual(Integer intdelLessThanOrEqual) {
		if (intdelLessThanOrEqual == null) {
			throw new RuntimeException("intdel is null");
		}
		this.intdelLessThanOrEqual = intdelLessThanOrEqual;
		return this;
	}

	public RUtabletreeQuery intdels(List<Integer> intdels) {
		if (intdels == null) {
			throw new RuntimeException("intdels is empty ");
		}
		this.intdels = intdels;
		return this;
	}

	public RUtabletreeQuery busiessId(String busiessId) {
		if (busiessId == null) {
			throw new RuntimeException("busiessId is null");
		}
		this.busiessId = busiessId;
		return this;
	}

	public RUtabletreeQuery busiessIdLike(String busiessIdLike) {
		if (busiessIdLike == null) {
			throw new RuntimeException("busiessId is null");
		}
		this.busiessIdLike = busiessIdLike;
		return this;
	}

	public RUtabletreeQuery busiessIds(List<String> busiessIds) {
		if (busiessIds == null) {
			throw new RuntimeException("busiessIds is empty ");
		}
		this.busiessIds = busiessIds;
		return this;
	}

	public RUtabletreeQuery content(String content) {
		if (content == null) {
			throw new RuntimeException("content is null");
		}
		this.content = content;
		return this;
	}

	public RUtabletreeQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public RUtabletreeQuery contents(List<String> contents) {
		if (contents == null) {
			throw new RuntimeException("contents is empty ");
		}
		this.contents = contents;
		return this;
	}

	public RUtabletreeQuery num(String num) {
		if (num == null) {
			throw new RuntimeException("num is null");
		}
		this.num = num;
		return this;
	}

	public RUtabletreeQuery numLike(String numLike) {
		if (numLike == null) {
			throw new RuntimeException("num is null");
		}
		this.numLike = numLike;
		return this;
	}

	public RUtabletreeQuery nums(List<String> nums) {
		if (nums == null) {
			throw new RuntimeException("nums is empty ");
		}
		this.nums = nums;
		return this;
	}

	public RUtabletreeQuery menuindex(Integer menuindex) {
		if (menuindex == null) {
			throw new RuntimeException("menuindex is null");
		}
		this.menuindex = menuindex;
		return this;
	}

	public RUtabletreeQuery menuindexGreaterThanOrEqual(Integer menuindexGreaterThanOrEqual) {
		if (menuindexGreaterThanOrEqual == null) {
			throw new RuntimeException("menuindex is null");
		}
		this.menuindexGreaterThanOrEqual = menuindexGreaterThanOrEqual;
		return this;
	}

	public RUtabletreeQuery menuindexLessThanOrEqual(Integer menuindexLessThanOrEqual) {
		if (menuindexLessThanOrEqual == null) {
			throw new RuntimeException("menuindex is null");
		}
		this.menuindexLessThanOrEqual = menuindexLessThanOrEqual;
		return this;
	}

	public RUtabletreeQuery menuindexs(List<Integer> menuindexs) {
		if (menuindexs == null) {
			throw new RuntimeException("menuindexs is empty ");
		}
		this.menuindexs = menuindexs;
		return this;
	}

	public RUtabletreeQuery domainIndex(Integer domainIndex) {
		if (domainIndex == null) {
			throw new RuntimeException("domainIndex is null");
		}
		this.domainIndex = domainIndex;
		return this;
	}

	public RUtabletreeQuery domainIndexGreaterThanOrEqual(Integer domainIndexGreaterThanOrEqual) {
		if (domainIndexGreaterThanOrEqual == null) {
			throw new RuntimeException("domainIndex is null");
		}
		this.domainIndexGreaterThanOrEqual = domainIndexGreaterThanOrEqual;
		return this;
	}

	public RUtabletreeQuery domainIndexLessThanOrEqual(Integer domainIndexLessThanOrEqual) {
		if (domainIndexLessThanOrEqual == null) {
			throw new RuntimeException("domainIndex is null");
		}
		this.domainIndexLessThanOrEqual = domainIndexLessThanOrEqual;
		return this;
	}

	public RUtabletreeQuery domainIndexs(List<Integer> domainIndexs) {
		if (domainIndexs == null) {
			throw new RuntimeException("domainIndexs is empty ");
		}
		this.domainIndexs = domainIndexs;
		return this;
	}

	public RUtabletreeQuery winWidth(Integer winWidth) {
		if (winWidth == null) {
			throw new RuntimeException("winWidth is null");
		}
		this.winWidth = winWidth;
		return this;
	}

	public RUtabletreeQuery winWidthGreaterThanOrEqual(Integer winWidthGreaterThanOrEqual) {
		if (winWidthGreaterThanOrEqual == null) {
			throw new RuntimeException("winWidth is null");
		}
		this.winWidthGreaterThanOrEqual = winWidthGreaterThanOrEqual;
		return this;
	}

	public RUtabletreeQuery winWidthLessThanOrEqual(Integer winWidthLessThanOrEqual) {
		if (winWidthLessThanOrEqual == null) {
			throw new RuntimeException("winWidth is null");
		}
		this.winWidthLessThanOrEqual = winWidthLessThanOrEqual;
		return this;
	}

	public RUtabletreeQuery winWidths(List<Integer> winWidths) {
		if (winWidths == null) {
			throw new RuntimeException("winWidths is empty ");
		}
		this.winWidths = winWidths;
		return this;
	}

	public RUtabletreeQuery winHeight(Integer winHeight) {
		if (winHeight == null) {
			throw new RuntimeException("winHeight is null");
		}
		this.winHeight = winHeight;
		return this;
	}

	public RUtabletreeQuery winHeightGreaterThanOrEqual(Integer winHeightGreaterThanOrEqual) {
		if (winHeightGreaterThanOrEqual == null) {
			throw new RuntimeException("winHeight is null");
		}
		this.winHeightGreaterThanOrEqual = winHeightGreaterThanOrEqual;
		return this;
	}

	public RUtabletreeQuery winHeightLessThanOrEqual(Integer winHeightLessThanOrEqual) {
		if (winHeightLessThanOrEqual == null) {
			throw new RuntimeException("winHeight is null");
		}
		this.winHeightLessThanOrEqual = winHeightLessThanOrEqual;
		return this;
	}

	public RUtabletreeQuery winHeights(List<Integer> winHeights) {
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

			if ("nodeico".equals(sortColumn)) {
				orderBy = "E.NODEICO" + a_x;
			}

			if ("listno".equals(sortColumn)) {
				orderBy = "E.LISTNO" + a_x;
			}

			if ("tabletype".equals(sortColumn)) {
				orderBy = "E.TABLETYPE" + a_x;
			}

			if ("intdel".equals(sortColumn)) {
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

			if ("menuindex".equals(sortColumn)) {
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
		addColumn("nodeico", "NODEICO");
		addColumn("listno", "LISTNO");
		addColumn("tabletype", "TABLETYPE");
		addColumn("intdel", "INTDEL");
		addColumn("busiessId", "BUSIESS_ID");
		addColumn("content", "CONTENT");
		addColumn("num", "NUM");
		addColumn("menuindex", "MENUINDEX");
		addColumn("domainIndex", "DOMAIN_INDEX");
		addColumn("winWidth", "WIN_WIDTH");
		addColumn("winHeight", "WIN_HEIGHT");
	}

}