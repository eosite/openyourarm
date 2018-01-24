package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class RdataTableQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected String tablename;
	protected String tablenameLike;
	protected List<String> tablenames;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected Integer addtype;
	protected Integer addtypeGreaterThanOrEqual;
	protected Integer addtypeLessThanOrEqual;
	protected List<Integer> addtypes;
	protected Integer maxuser;
	protected Integer maxuserGreaterThanOrEqual;
	protected Integer maxuserLessThanOrEqual;
	protected List<Integer> maxusers;
	protected Integer maxsys;
	protected Integer maxsysGreaterThanOrEqual;
	protected Integer maxsysLessThanOrEqual;
	protected List<Integer> maxsyss;
	protected String userid;
	protected String useridLike;
	protected List<String> userids;
	protected Date ctimeGreaterThanOrEqual;
	protected Date ctimeLessThanOrEqual;
	protected String content;
	protected String contentLike;
	protected List<String> contents;
	protected String sysnum;
	protected String sysnumLike;
	protected List<String> sysnums;
	protected String issubtable;
	protected String issubtableLike;
	protected List<String> issubtables;
	protected String topid;
	protected String topidLike;
	protected List<String> topids;
	protected String filedotFileid;
	protected String filedotFileidLike;
	protected List<String> filedotFileids;
	protected Integer indexId;
	protected Integer indexIdGreaterThanOrEqual;
	protected Integer indexIdLessThanOrEqual;
	protected List<Integer> indexIds;
	protected Integer winWidth;
	protected Integer winWidthGreaterThanOrEqual;
	protected Integer winWidthLessThanOrEqual;
	protected List<Integer> winWidths;
	protected Integer winHeight;
	protected Integer winHeightGreaterThanOrEqual;
	protected Integer winHeightLessThanOrEqual;
	protected List<Integer> winHeights;
	protected Integer intQuote;
	protected Integer intQuoteGreaterThanOrEqual;
	protected Integer intQuoteLessThanOrEqual;
	protected List<Integer> intQuotes;
	protected Integer intLineEdit;
	protected Integer intLineEditGreaterThanOrEqual;
	protected Integer intLineEditLessThanOrEqual;
	protected List<Integer> intLineEdits;
	protected String printfileid;
	protected String printfileidLike;
	protected List<String> printfileids;
	protected Integer iNTUSESTREEWBS;
	protected Integer iNTUSESTREEWBSGreaterThanOrEqual;
	protected Integer iNTUSESTREEWBSLessThanOrEqual;
	protected List<Integer> iNTUSESTREEWBSs;
	protected Integer intUseIf;
	protected Integer intUseIfGreaterThanOrEqual;
	protected Integer intUseIfLessThanOrEqual;
	protected List<Integer> intUseIfs;

	public RdataTableQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getTablename() {
		return tablename;
	}

	public String getTablenameLike() {
		if (tablenameLike != null && tablenameLike.trim().length() > 0) {
			if (!tablenameLike.startsWith("%")) {
				tablenameLike = "%" + tablenameLike;
			}
			if (!tablenameLike.endsWith("%")) {
				tablenameLike = tablenameLike + "%";
			}
		}
		return tablenameLike;
	}

	public List<String> getTablenames() {
		return tablenames;
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

	public Integer getAddtype() {
		return addtype;
	}

	public Integer getAddtypeGreaterThanOrEqual() {
		return addtypeGreaterThanOrEqual;
	}

	public Integer getAddtypeLessThanOrEqual() {
		return addtypeLessThanOrEqual;
	}

	public List<Integer> getAddtypes() {
		return addtypes;
	}

	public Integer getMaxuser() {
		return maxuser;
	}

	public Integer getMaxuserGreaterThanOrEqual() {
		return maxuserGreaterThanOrEqual;
	}

	public Integer getMaxuserLessThanOrEqual() {
		return maxuserLessThanOrEqual;
	}

	public List<Integer> getMaxusers() {
		return maxusers;
	}

	public Integer getMaxsys() {
		return maxsys;
	}

	public Integer getMaxsysGreaterThanOrEqual() {
		return maxsysGreaterThanOrEqual;
	}

	public Integer getMaxsysLessThanOrEqual() {
		return maxsysLessThanOrEqual;
	}

	public List<Integer> getMaxsyss() {
		return maxsyss;
	}

	public String getUserid() {
		return userid;
	}

	public String getUseridLike() {
		if (useridLike != null && useridLike.trim().length() > 0) {
			if (!useridLike.startsWith("%")) {
				useridLike = "%" + useridLike;
			}
			if (!useridLike.endsWith("%")) {
				useridLike = useridLike + "%";
			}
		}
		return useridLike;
	}

	public List<String> getUserids() {
		return userids;
	}

	public Date getCtimeGreaterThanOrEqual() {
		return ctimeGreaterThanOrEqual;
	}

	public Date getCtimeLessThanOrEqual() {
		return ctimeLessThanOrEqual;
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

	public String getSysnum() {
		return sysnum;
	}

	public String getSysnumLike() {
		if (sysnumLike != null && sysnumLike.trim().length() > 0) {
			if (!sysnumLike.startsWith("%")) {
				sysnumLike = "%" + sysnumLike;
			}
			if (!sysnumLike.endsWith("%")) {
				sysnumLike = sysnumLike + "%";
			}
		}
		return sysnumLike;
	}

	public List<String> getSysnums() {
		return sysnums;
	}

	public String getIssubtable() {
		return issubtable;
	}

	public String getIssubtableLike() {
		if (issubtableLike != null && issubtableLike.trim().length() > 0) {
			if (!issubtableLike.startsWith("%")) {
				issubtableLike = "%" + issubtableLike;
			}
			if (!issubtableLike.endsWith("%")) {
				issubtableLike = issubtableLike + "%";
			}
		}
		return issubtableLike;
	}

	public List<String> getIssubtables() {
		return issubtables;
	}

	public String getTopid() {
		return topid;
	}

	public String getTopidLike() {
		if (topidLike != null && topidLike.trim().length() > 0) {
			if (!topidLike.startsWith("%")) {
				topidLike = "%" + topidLike;
			}
			if (!topidLike.endsWith("%")) {
				topidLike = topidLike + "%";
			}
		}
		return topidLike;
	}

	public List<String> getTopids() {
		return topids;
	}

	public String getFiledotFileid() {
		return filedotFileid;
	}

	public String getFiledotFileidLike() {
		if (filedotFileidLike != null && filedotFileidLike.trim().length() > 0) {
			if (!filedotFileidLike.startsWith("%")) {
				filedotFileidLike = "%" + filedotFileidLike;
			}
			if (!filedotFileidLike.endsWith("%")) {
				filedotFileidLike = filedotFileidLike + "%";
			}
		}
		return filedotFileidLike;
	}

	public List<String> getFiledotFileids() {
		return filedotFileids;
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

	public Integer getIntQuote() {
		return intQuote;
	}

	public Integer getIntQuoteGreaterThanOrEqual() {
		return intQuoteGreaterThanOrEqual;
	}

	public Integer getIntQuoteLessThanOrEqual() {
		return intQuoteLessThanOrEqual;
	}

	public List<Integer> getIntQuotes() {
		return intQuotes;
	}

	public Integer getIntLineEdit() {
		return intLineEdit;
	}

	public Integer getIntLineEditGreaterThanOrEqual() {
		return intLineEditGreaterThanOrEqual;
	}

	public Integer getIntLineEditLessThanOrEqual() {
		return intLineEditLessThanOrEqual;
	}

	public List<Integer> getIntLineEdits() {
		return intLineEdits;
	}

	public String getPrintfileid() {
		return printfileid;
	}

	public String getPrintfileidLike() {
		if (printfileidLike != null && printfileidLike.trim().length() > 0) {
			if (!printfileidLike.startsWith("%")) {
				printfileidLike = "%" + printfileidLike;
			}
			if (!printfileidLike.endsWith("%")) {
				printfileidLike = printfileidLike + "%";
			}
		}
		return printfileidLike;
	}

	public List<String> getPrintfileids() {
		return printfileids;
	}

	public Integer getINTUSESTREEWBS() {
		return iNTUSESTREEWBS;
	}

	public Integer getINTUSESTREEWBSGreaterThanOrEqual() {
		return iNTUSESTREEWBSGreaterThanOrEqual;
	}

	public Integer getINTUSESTREEWBSLessThanOrEqual() {
		return iNTUSESTREEWBSLessThanOrEqual;
	}

	public List<Integer> getINTUSESTREEWBSs() {
		return iNTUSESTREEWBSs;
	}

	public Integer getIntUseIf() {
		return intUseIf;
	}

	public Integer getIntUseIfGreaterThanOrEqual() {
		return intUseIfGreaterThanOrEqual;
	}

	public Integer getIntUseIfLessThanOrEqual() {
		return intUseIfLessThanOrEqual;
	}

	public List<Integer> getIntUseIfs() {
		return intUseIfs;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public void setTablenameLike(String tablenameLike) {
		this.tablenameLike = tablenameLike;
	}

	public void setTablenames(List<String> tablenames) {
		this.tablenames = tablenames;
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

	public void setAddtype(Integer addtype) {
		this.addtype = addtype;
	}

	public void setAddtypeGreaterThanOrEqual(Integer addtypeGreaterThanOrEqual) {
		this.addtypeGreaterThanOrEqual = addtypeGreaterThanOrEqual;
	}

	public void setAddtypeLessThanOrEqual(Integer addtypeLessThanOrEqual) {
		this.addtypeLessThanOrEqual = addtypeLessThanOrEqual;
	}

	public void setAddtypes(List<Integer> addtypes) {
		this.addtypes = addtypes;
	}

	public void setMaxuser(Integer maxuser) {
		this.maxuser = maxuser;
	}

	public void setMaxuserGreaterThanOrEqual(Integer maxuserGreaterThanOrEqual) {
		this.maxuserGreaterThanOrEqual = maxuserGreaterThanOrEqual;
	}

	public void setMaxuserLessThanOrEqual(Integer maxuserLessThanOrEqual) {
		this.maxuserLessThanOrEqual = maxuserLessThanOrEqual;
	}

	public void setMaxusers(List<Integer> maxusers) {
		this.maxusers = maxusers;
	}

	public void setMaxsys(Integer maxsys) {
		this.maxsys = maxsys;
	}

	public void setMaxsysGreaterThanOrEqual(Integer maxsysGreaterThanOrEqual) {
		this.maxsysGreaterThanOrEqual = maxsysGreaterThanOrEqual;
	}

	public void setMaxsysLessThanOrEqual(Integer maxsysLessThanOrEqual) {
		this.maxsysLessThanOrEqual = maxsysLessThanOrEqual;
	}

	public void setMaxsyss(List<Integer> maxsyss) {
		this.maxsyss = maxsyss;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setUseridLike(String useridLike) {
		this.useridLike = useridLike;
	}

	public void setUserids(List<String> userids) {
		this.userids = userids;
	}

	public void setCtimeGreaterThanOrEqual(Date ctimeGreaterThanOrEqual) {
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
	}

	public void setCtimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
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

	public void setSysnum(String sysnum) {
		this.sysnum = sysnum;
	}

	public void setSysnumLike(String sysnumLike) {
		this.sysnumLike = sysnumLike;
	}

	public void setSysnums(List<String> sysnums) {
		this.sysnums = sysnums;
	}

	public void setIssubtable(String issubtable) {
		this.issubtable = issubtable;
	}

	public void setIssubtableLike(String issubtableLike) {
		this.issubtableLike = issubtableLike;
	}

	public void setIssubtables(List<String> issubtables) {
		this.issubtables = issubtables;
	}

	public void setTopid(String topid) {
		this.topid = topid;
	}

	public void setTopidLike(String topidLike) {
		this.topidLike = topidLike;
	}

	public void setTopids(List<String> topids) {
		this.topids = topids;
	}

	public void setFiledotFileid(String filedotFileid) {
		this.filedotFileid = filedotFileid;
	}

	public void setFiledotFileidLike(String filedotFileidLike) {
		this.filedotFileidLike = filedotFileidLike;
	}

	public void setFiledotFileids(List<String> filedotFileids) {
		this.filedotFileids = filedotFileids;
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

	public void setIntQuote(Integer intQuote) {
		this.intQuote = intQuote;
	}

	public void setIntQuoteGreaterThanOrEqual(Integer intQuoteGreaterThanOrEqual) {
		this.intQuoteGreaterThanOrEqual = intQuoteGreaterThanOrEqual;
	}

	public void setIntQuoteLessThanOrEqual(Integer intQuoteLessThanOrEqual) {
		this.intQuoteLessThanOrEqual = intQuoteLessThanOrEqual;
	}

	public void setIntQuotes(List<Integer> intQuotes) {
		this.intQuotes = intQuotes;
	}

	public void setIntLineEdit(Integer intLineEdit) {
		this.intLineEdit = intLineEdit;
	}

	public void setIntLineEditGreaterThanOrEqual(Integer intLineEditGreaterThanOrEqual) {
		this.intLineEditGreaterThanOrEqual = intLineEditGreaterThanOrEqual;
	}

	public void setIntLineEditLessThanOrEqual(Integer intLineEditLessThanOrEqual) {
		this.intLineEditLessThanOrEqual = intLineEditLessThanOrEqual;
	}

	public void setIntLineEdits(List<Integer> intLineEdits) {
		this.intLineEdits = intLineEdits;
	}

	public void setPrintfileid(String printfileid) {
		this.printfileid = printfileid;
	}

	public void setPrintfileidLike(String printfileidLike) {
		this.printfileidLike = printfileidLike;
	}

	public void setPrintfileids(List<String> printfileids) {
		this.printfileids = printfileids;
	}

	public void setINTUSESTREEWBS(Integer iNTUSESTREEWBS) {
		this.iNTUSESTREEWBS = iNTUSESTREEWBS;
	}

	public void setINTUSESTREEWBSGreaterThanOrEqual(Integer iNTUSESTREEWBSGreaterThanOrEqual) {
		this.iNTUSESTREEWBSGreaterThanOrEqual = iNTUSESTREEWBSGreaterThanOrEqual;
	}

	public void setINTUSESTREEWBSLessThanOrEqual(Integer iNTUSESTREEWBSLessThanOrEqual) {
		this.iNTUSESTREEWBSLessThanOrEqual = iNTUSESTREEWBSLessThanOrEqual;
	}

	public void setINTUSESTREEWBSs(List<Integer> iNTUSESTREEWBSs) {
		this.iNTUSESTREEWBSs = iNTUSESTREEWBSs;
	}

	public void setIntUseIf(Integer intUseIf) {
		this.intUseIf = intUseIf;
	}

	public void setIntUseIfGreaterThanOrEqual(Integer intUseIfGreaterThanOrEqual) {
		this.intUseIfGreaterThanOrEqual = intUseIfGreaterThanOrEqual;
	}

	public void setIntUseIfLessThanOrEqual(Integer intUseIfLessThanOrEqual) {
		this.intUseIfLessThanOrEqual = intUseIfLessThanOrEqual;
	}

	public void setIntUseIfs(List<Integer> intUseIfs) {
		this.intUseIfs = intUseIfs;
	}

	public RdataTableQuery tablename(String tablename) {
		if (tablename == null) {
			throw new RuntimeException("tablename is null");
		}
		this.tablename = tablename;
		return this;
	}

	public RdataTableQuery tablenameLike(String tablenameLike) {
		if (tablenameLike == null) {
			throw new RuntimeException("tablename is null");
		}
		this.tablenameLike = tablenameLike;
		return this;
	}

	public RdataTableQuery tablenames(List<String> tablenames) {
		if (tablenames == null) {
			throw new RuntimeException("tablenames is empty ");
		}
		this.tablenames = tablenames;
		return this;
	}

	public RdataTableQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public RdataTableQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public RdataTableQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public RdataTableQuery addtype(Integer addtype) {
		if (addtype == null) {
			throw new RuntimeException("addtype is null");
		}
		this.addtype = addtype;
		return this;
	}

	public RdataTableQuery addtypeGreaterThanOrEqual(Integer addtypeGreaterThanOrEqual) {
		if (addtypeGreaterThanOrEqual == null) {
			throw new RuntimeException("addtype is null");
		}
		this.addtypeGreaterThanOrEqual = addtypeGreaterThanOrEqual;
		return this;
	}

	public RdataTableQuery addtypeLessThanOrEqual(Integer addtypeLessThanOrEqual) {
		if (addtypeLessThanOrEqual == null) {
			throw new RuntimeException("addtype is null");
		}
		this.addtypeLessThanOrEqual = addtypeLessThanOrEqual;
		return this;
	}

	public RdataTableQuery addtypes(List<Integer> addtypes) {
		if (addtypes == null) {
			throw new RuntimeException("addtypes is empty ");
		}
		this.addtypes = addtypes;
		return this;
	}

	public RdataTableQuery maxuser(Integer maxuser) {
		if (maxuser == null) {
			throw new RuntimeException("maxuser is null");
		}
		this.maxuser = maxuser;
		return this;
	}

	public RdataTableQuery maxuserGreaterThanOrEqual(Integer maxuserGreaterThanOrEqual) {
		if (maxuserGreaterThanOrEqual == null) {
			throw new RuntimeException("maxuser is null");
		}
		this.maxuserGreaterThanOrEqual = maxuserGreaterThanOrEqual;
		return this;
	}

	public RdataTableQuery maxuserLessThanOrEqual(Integer maxuserLessThanOrEqual) {
		if (maxuserLessThanOrEqual == null) {
			throw new RuntimeException("maxuser is null");
		}
		this.maxuserLessThanOrEqual = maxuserLessThanOrEqual;
		return this;
	}

	public RdataTableQuery maxusers(List<Integer> maxusers) {
		if (maxusers == null) {
			throw new RuntimeException("maxusers is empty ");
		}
		this.maxusers = maxusers;
		return this;
	}

	public RdataTableQuery maxsys(Integer maxsys) {
		if (maxsys == null) {
			throw new RuntimeException("maxsys is null");
		}
		this.maxsys = maxsys;
		return this;
	}

	public RdataTableQuery maxsysGreaterThanOrEqual(Integer maxsysGreaterThanOrEqual) {
		if (maxsysGreaterThanOrEqual == null) {
			throw new RuntimeException("maxsys is null");
		}
		this.maxsysGreaterThanOrEqual = maxsysGreaterThanOrEqual;
		return this;
	}

	public RdataTableQuery maxsysLessThanOrEqual(Integer maxsysLessThanOrEqual) {
		if (maxsysLessThanOrEqual == null) {
			throw new RuntimeException("maxsys is null");
		}
		this.maxsysLessThanOrEqual = maxsysLessThanOrEqual;
		return this;
	}

	public RdataTableQuery maxsyss(List<Integer> maxsyss) {
		if (maxsyss == null) {
			throw new RuntimeException("maxsyss is empty ");
		}
		this.maxsyss = maxsyss;
		return this;
	}

	public RdataTableQuery userid(String userid) {
		if (userid == null) {
			throw new RuntimeException("userid is null");
		}
		this.userid = userid;
		return this;
	}

	public RdataTableQuery useridLike(String useridLike) {
		if (useridLike == null) {
			throw new RuntimeException("userid is null");
		}
		this.useridLike = useridLike;
		return this;
	}

	public RdataTableQuery userids(List<String> userids) {
		if (userids == null) {
			throw new RuntimeException("userids is empty ");
		}
		this.userids = userids;
		return this;
	}

	public RdataTableQuery ctimeGreaterThanOrEqual(Date ctimeGreaterThanOrEqual) {
		if (ctimeGreaterThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
		return this;
	}

	public RdataTableQuery ctimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		if (ctimeLessThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
		return this;
	}

	public RdataTableQuery content(String content) {
		if (content == null) {
			throw new RuntimeException("content is null");
		}
		this.content = content;
		return this;
	}

	public RdataTableQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public RdataTableQuery contents(List<String> contents) {
		if (contents == null) {
			throw new RuntimeException("contents is empty ");
		}
		this.contents = contents;
		return this;
	}

	public RdataTableQuery sysnum(String sysnum) {
		if (sysnum == null) {
			throw new RuntimeException("sysnum is null");
		}
		this.sysnum = sysnum;
		return this;
	}

	public RdataTableQuery sysnumLike(String sysnumLike) {
		if (sysnumLike == null) {
			throw new RuntimeException("sysnum is null");
		}
		this.sysnumLike = sysnumLike;
		return this;
	}

	public RdataTableQuery sysnums(List<String> sysnums) {
		if (sysnums == null) {
			throw new RuntimeException("sysnums is empty ");
		}
		this.sysnums = sysnums;
		return this;
	}

	public RdataTableQuery issubtable(String issubtable) {
		if (issubtable == null) {
			throw new RuntimeException("issubtable is null");
		}
		this.issubtable = issubtable;
		return this;
	}

	public RdataTableQuery issubtableLike(String issubtableLike) {
		if (issubtableLike == null) {
			throw new RuntimeException("issubtable is null");
		}
		this.issubtableLike = issubtableLike;
		return this;
	}

	public RdataTableQuery issubtables(List<String> issubtables) {
		if (issubtables == null) {
			throw new RuntimeException("issubtables is empty ");
		}
		this.issubtables = issubtables;
		return this;
	}

	public RdataTableQuery topid(String topid) {
		if (topid == null) {
			throw new RuntimeException("topid is null");
		}
		this.topid = topid;
		return this;
	}

	public RdataTableQuery topidLike(String topidLike) {
		if (topidLike == null) {
			throw new RuntimeException("topid is null");
		}
		this.topidLike = topidLike;
		return this;
	}

	public RdataTableQuery topids(List<String> topids) {
		if (topids == null) {
			throw new RuntimeException("topids is empty ");
		}
		this.topids = topids;
		return this;
	}

	public RdataTableQuery filedotFileid(String filedotFileid) {
		if (filedotFileid == null) {
			throw new RuntimeException("filedotFileid is null");
		}
		this.filedotFileid = filedotFileid;
		return this;
	}

	public RdataTableQuery filedotFileidLike(String filedotFileidLike) {
		if (filedotFileidLike == null) {
			throw new RuntimeException("filedotFileid is null");
		}
		this.filedotFileidLike = filedotFileidLike;
		return this;
	}

	public RdataTableQuery filedotFileids(List<String> filedotFileids) {
		if (filedotFileids == null) {
			throw new RuntimeException("filedotFileids is empty ");
		}
		this.filedotFileids = filedotFileids;
		return this;
	}

	public RdataTableQuery indexId(Integer indexId) {
		if (indexId == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexId = indexId;
		return this;
	}

	public RdataTableQuery indexIdGreaterThanOrEqual(Integer indexIdGreaterThanOrEqual) {
		if (indexIdGreaterThanOrEqual == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexIdGreaterThanOrEqual = indexIdGreaterThanOrEqual;
		return this;
	}

	public RdataTableQuery indexIdLessThanOrEqual(Integer indexIdLessThanOrEqual) {
		if (indexIdLessThanOrEqual == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexIdLessThanOrEqual = indexIdLessThanOrEqual;
		return this;
	}

	public RdataTableQuery indexIds(List<Integer> indexIds) {
		if (indexIds == null) {
			throw new RuntimeException("indexIds is empty ");
		}
		this.indexIds = indexIds;
		return this;
	}

	public RdataTableQuery winWidth(Integer winWidth) {
		if (winWidth == null) {
			throw new RuntimeException("winWidth is null");
		}
		this.winWidth = winWidth;
		return this;
	}

	public RdataTableQuery winWidthGreaterThanOrEqual(Integer winWidthGreaterThanOrEqual) {
		if (winWidthGreaterThanOrEqual == null) {
			throw new RuntimeException("winWidth is null");
		}
		this.winWidthGreaterThanOrEqual = winWidthGreaterThanOrEqual;
		return this;
	}

	public RdataTableQuery winWidthLessThanOrEqual(Integer winWidthLessThanOrEqual) {
		if (winWidthLessThanOrEqual == null) {
			throw new RuntimeException("winWidth is null");
		}
		this.winWidthLessThanOrEqual = winWidthLessThanOrEqual;
		return this;
	}

	public RdataTableQuery winWidths(List<Integer> winWidths) {
		if (winWidths == null) {
			throw new RuntimeException("winWidths is empty ");
		}
		this.winWidths = winWidths;
		return this;
	}

	public RdataTableQuery winHeight(Integer winHeight) {
		if (winHeight == null) {
			throw new RuntimeException("winHeight is null");
		}
		this.winHeight = winHeight;
		return this;
	}

	public RdataTableQuery winHeightGreaterThanOrEqual(Integer winHeightGreaterThanOrEqual) {
		if (winHeightGreaterThanOrEqual == null) {
			throw new RuntimeException("winHeight is null");
		}
		this.winHeightGreaterThanOrEqual = winHeightGreaterThanOrEqual;
		return this;
	}

	public RdataTableQuery winHeightLessThanOrEqual(Integer winHeightLessThanOrEqual) {
		if (winHeightLessThanOrEqual == null) {
			throw new RuntimeException("winHeight is null");
		}
		this.winHeightLessThanOrEqual = winHeightLessThanOrEqual;
		return this;
	}

	public RdataTableQuery winHeights(List<Integer> winHeights) {
		if (winHeights == null) {
			throw new RuntimeException("winHeights is empty ");
		}
		this.winHeights = winHeights;
		return this;
	}

	public RdataTableQuery intQuote(Integer intQuote) {
		if (intQuote == null) {
			throw new RuntimeException("intQuote is null");
		}
		this.intQuote = intQuote;
		return this;
	}

	public RdataTableQuery intQuoteGreaterThanOrEqual(Integer intQuoteGreaterThanOrEqual) {
		if (intQuoteGreaterThanOrEqual == null) {
			throw new RuntimeException("intQuote is null");
		}
		this.intQuoteGreaterThanOrEqual = intQuoteGreaterThanOrEqual;
		return this;
	}

	public RdataTableQuery intQuoteLessThanOrEqual(Integer intQuoteLessThanOrEqual) {
		if (intQuoteLessThanOrEqual == null) {
			throw new RuntimeException("intQuote is null");
		}
		this.intQuoteLessThanOrEqual = intQuoteLessThanOrEqual;
		return this;
	}

	public RdataTableQuery intQuotes(List<Integer> intQuotes) {
		if (intQuotes == null) {
			throw new RuntimeException("intQuotes is empty ");
		}
		this.intQuotes = intQuotes;
		return this;
	}

	public RdataTableQuery intLineEdit(Integer intLineEdit) {
		if (intLineEdit == null) {
			throw new RuntimeException("intLineEdit is null");
		}
		this.intLineEdit = intLineEdit;
		return this;
	}

	public RdataTableQuery intLineEditGreaterThanOrEqual(Integer intLineEditGreaterThanOrEqual) {
		if (intLineEditGreaterThanOrEqual == null) {
			throw new RuntimeException("intLineEdit is null");
		}
		this.intLineEditGreaterThanOrEqual = intLineEditGreaterThanOrEqual;
		return this;
	}

	public RdataTableQuery intLineEditLessThanOrEqual(Integer intLineEditLessThanOrEqual) {
		if (intLineEditLessThanOrEqual == null) {
			throw new RuntimeException("intLineEdit is null");
		}
		this.intLineEditLessThanOrEqual = intLineEditLessThanOrEqual;
		return this;
	}

	public RdataTableQuery intLineEdits(List<Integer> intLineEdits) {
		if (intLineEdits == null) {
			throw new RuntimeException("intLineEdits is empty ");
		}
		this.intLineEdits = intLineEdits;
		return this;
	}

	public RdataTableQuery printfileid(String printfileid) {
		if (printfileid == null) {
			throw new RuntimeException("printfileid is null");
		}
		this.printfileid = printfileid;
		return this;
	}

	public RdataTableQuery printfileidLike(String printfileidLike) {
		if (printfileidLike == null) {
			throw new RuntimeException("printfileid is null");
		}
		this.printfileidLike = printfileidLike;
		return this;
	}

	public RdataTableQuery printfileids(List<String> printfileids) {
		if (printfileids == null) {
			throw new RuntimeException("printfileids is empty ");
		}
		this.printfileids = printfileids;
		return this;
	}

	public RdataTableQuery iNTUSESTREEWBS(Integer iNTUSESTREEWBS) {
		if (iNTUSESTREEWBS == null) {
			throw new RuntimeException("iNTUSESTREEWBS is null");
		}
		this.iNTUSESTREEWBS = iNTUSESTREEWBS;
		return this;
	}

	public RdataTableQuery iNTUSESTREEWBSGreaterThanOrEqual(Integer iNTUSESTREEWBSGreaterThanOrEqual) {
		if (iNTUSESTREEWBSGreaterThanOrEqual == null) {
			throw new RuntimeException("iNTUSESTREEWBS is null");
		}
		this.iNTUSESTREEWBSGreaterThanOrEqual = iNTUSESTREEWBSGreaterThanOrEqual;
		return this;
	}

	public RdataTableQuery iNTUSESTREEWBSLessThanOrEqual(Integer iNTUSESTREEWBSLessThanOrEqual) {
		if (iNTUSESTREEWBSLessThanOrEqual == null) {
			throw new RuntimeException("iNTUSESTREEWBS is null");
		}
		this.iNTUSESTREEWBSLessThanOrEqual = iNTUSESTREEWBSLessThanOrEqual;
		return this;
	}

	public RdataTableQuery iNTUSESTREEWBSs(List<Integer> iNTUSESTREEWBSs) {
		if (iNTUSESTREEWBSs == null) {
			throw new RuntimeException("iNTUSESTREEWBSs is empty ");
		}
		this.iNTUSESTREEWBSs = iNTUSESTREEWBSs;
		return this;
	}

	public RdataTableQuery intUseIf(Integer intUseIf) {
		if (intUseIf == null) {
			throw new RuntimeException("intUseIf is null");
		}
		this.intUseIf = intUseIf;
		return this;
	}

	public RdataTableQuery intUseIfGreaterThanOrEqual(Integer intUseIfGreaterThanOrEqual) {
		if (intUseIfGreaterThanOrEqual == null) {
			throw new RuntimeException("intUseIf is null");
		}
		this.intUseIfGreaterThanOrEqual = intUseIfGreaterThanOrEqual;
		return this;
	}

	public RdataTableQuery intUseIfLessThanOrEqual(Integer intUseIfLessThanOrEqual) {
		if (intUseIfLessThanOrEqual == null) {
			throw new RuntimeException("intUseIf is null");
		}
		this.intUseIfLessThanOrEqual = intUseIfLessThanOrEqual;
		return this;
	}

	public RdataTableQuery intUseIfs(List<Integer> intUseIfs) {
		if (intUseIfs == null) {
			throw new RuntimeException("intUseIfs is empty ");
		}
		this.intUseIfs = intUseIfs;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("tablename".equals(sortColumn)) {
				orderBy = "E.TABLENAME" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME" + a_x;
			}

			if ("addtype".equals(sortColumn)) {
				orderBy = "E.ADDTYPE" + a_x;
			}

			if ("maxuser".equals(sortColumn)) {
				orderBy = "E.MAXUSER" + a_x;
			}

			if ("maxsys".equals(sortColumn)) {
				orderBy = "E.MAXSYS" + a_x;
			}

			if ("userid".equals(sortColumn)) {
				orderBy = "E.USERID" + a_x;
			}

			if ("ctime".equals(sortColumn)) {
				orderBy = "E.CTIME" + a_x;
			}

			if ("content".equals(sortColumn)) {
				orderBy = "E.CONTENT" + a_x;
			}

			if ("sysnum".equals(sortColumn)) {
				orderBy = "E.SYSNUM" + a_x;
			}

			if ("issubtable".equals(sortColumn)) {
				orderBy = "E.ISSUBTABLE" + a_x;
			}

			if ("topid".equals(sortColumn)) {
				orderBy = "E.TOPID" + a_x;
			}

			if ("filedotFileid".equals(sortColumn)) {
				orderBy = "E.FILEDOT_FILEID" + a_x;
			}

			if ("indexId".equals(sortColumn)) {
				orderBy = "E.INDEX_ID" + a_x;
			}

			if ("winWidth".equals(sortColumn)) {
				orderBy = "E.WIN_WIDTH" + a_x;
			}

			if ("winHeight".equals(sortColumn)) {
				orderBy = "E.WIN_HEIGHT" + a_x;
			}

			if ("intQuote".equals(sortColumn)) {
				orderBy = "E.INTQUOTE" + a_x;
			}

			if ("intLineEdit".equals(sortColumn)) {
				orderBy = "E.INTLINEEDIT" + a_x;
			}

			if ("printfileid".equals(sortColumn)) {
				orderBy = "E.PRINTFILEID" + a_x;
			}

			if ("iNTUSESTREEWBS".equals(sortColumn)) {
				orderBy = "E.INTUSESTREEWBS" + a_x;
			}

			if ("intUseIf".equals(sortColumn)) {
				orderBy = "E.INTUSEIF" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("tablename", "TABLENAME");
		addColumn("name", "NAME");
		addColumn("addtype", "ADDTYPE");
		addColumn("maxuser", "MAXUSER");
		addColumn("maxsys", "MAXSYS");
		addColumn("userid", "USERID");
		addColumn("ctime", "CTIME");
		addColumn("content", "CONTENT");
		addColumn("sysnum", "SYSNUM");
		addColumn("issubtable", "ISSUBTABLE");
		addColumn("topid", "TOPID");
		addColumn("filedotFileid", "FILEDOT_FILEID");
		addColumn("indexId", "INDEX_ID");
		addColumn("winWidth", "WIN_WIDTH");
		addColumn("winHeight", "WIN_HEIGHT");
		addColumn("intQuote", "INTQUOTE");
		addColumn("intLineEdit", "INTLINEEDIT");
		addColumn("printfileid", "PRINTFILEID");
		addColumn("iNTUSESTREEWBS", "INTUSESTREEWBS");
		addColumn("intUseIf", "INTUSEIF");
	}

}