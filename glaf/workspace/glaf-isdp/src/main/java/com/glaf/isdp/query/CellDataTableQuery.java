package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class CellDataTableQuery extends DataQuery {

	private static final long serialVersionUID = 1L;

	protected List<String> ids;

	protected Collection<String> appActorIds;

	protected String tableName;

	protected String tableNameLike;

	protected List<String> tableNames;

	protected String name;

	protected String nameLike;

	protected List<String> names;

	protected Integer addType;

	protected Integer addTypeGreaterThanOrEqual;

	protected Integer addTypeLessThanOrEqual;

	protected List<Integer> addTypes;

	protected Integer maxUser;

	protected Integer maxUserGreaterThanOrEqual;

	protected Integer maxUserLessThanOrEqual;

	protected List<Integer> maxUsers;

	protected Integer maxSys;

	protected Integer maxSysGreaterThanOrEqual;

	protected Integer maxSysLessThanOrEqual;

	protected List<Integer> maxSyss;

	protected String userId;

	protected String userIdLike;

	protected List<String> userIds;

	protected Date ctimeGreaterThanOrEqual;

	protected Date ctimeLessThanOrEqual;

	protected String content;

	protected String contentLike;

	protected List<String> contents;

	protected String sysNum;

	protected String sysNumLike;

	protected List<String> sysNums;

	protected String isSubTable;

	protected String isSubTableLike;

	protected List<String> isSubTables;

	protected String topId;

	protected String topIdLike;

	protected List<String> topIds;

	protected String fileDotFileId;

	protected String fileDotFileIdLike;

	protected List<String> fileDotFileIds;

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

	protected String printFileId;

	protected String printFileIdLike;

	protected List<String> printFileIds;

	protected Integer intUseSTreeWBS;

	protected Integer intUseSTreeWBSGreaterThanOrEqual;

	protected Integer intUseSTreeWBSLessThanOrEqual;

	protected List<Integer> intUseSTreeWBSs;

	protected Integer treedotIndexId;

	protected List<Integer> treedotIndexIds;

	protected boolean searchChildTable;

	protected String sqlCondition;

	public CellDataTableQuery() {

	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getTableName() {
		return tableName;
	}

	public String getTableNameLike() {
		if (tableNameLike != null && tableNameLike.trim().length() > 0) {
			if (!tableNameLike.startsWith("%")) {
				tableNameLike = "%" + tableNameLike;
			}
			if (!tableNameLike.endsWith("%")) {
				tableNameLike = tableNameLike + "%";
			}
		}
		return tableNameLike;
	}

	public List<String> getTableNames() {
		return tableNames;
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

	public Integer getAddType() {
		return addType;
	}

	public Integer getAddTypeGreaterThanOrEqual() {
		return addTypeGreaterThanOrEqual;
	}

	public Integer getAddTypeLessThanOrEqual() {
		return addTypeLessThanOrEqual;
	}

	public List<Integer> getAddTypes() {
		return addTypes;
	}

	public Integer getMaxUser() {
		return maxUser;
	}

	public Integer getMaxUserGreaterThanOrEqual() {
		return maxUserGreaterThanOrEqual;
	}

	public Integer getMaxUserLessThanOrEqual() {
		return maxUserLessThanOrEqual;
	}

	public List<Integer> getMaxUsers() {
		return maxUsers;
	}

	public Integer getMaxSys() {
		return maxSys;
	}

	public Integer getMaxSysGreaterThanOrEqual() {
		return maxSysGreaterThanOrEqual;
	}

	public Integer getMaxSysLessThanOrEqual() {
		return maxSysLessThanOrEqual;
	}

	public List<Integer> getMaxSyss() {
		return maxSyss;
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

	public String getSysNum() {
		return sysNum;
	}

	public String getSysNumLike() {
		if (sysNumLike != null && sysNumLike.trim().length() > 0) {
			if (!sysNumLike.startsWith("%")) {
				sysNumLike = "%" + sysNumLike;
			}
			if (!sysNumLike.endsWith("%")) {
				sysNumLike = sysNumLike + "%";
			}
		}
		return sysNumLike;
	}

	public List<String> getSysNums() {
		return sysNums;
	}

	public String getIsSubTable() {
		return isSubTable;
	}

	public String getIsSubTableLike() {
		if (isSubTableLike != null && isSubTableLike.trim().length() > 0) {
			if (!isSubTableLike.startsWith("%")) {
				isSubTableLike = "%" + isSubTableLike;
			}
			if (!isSubTableLike.endsWith("%")) {
				isSubTableLike = isSubTableLike + "%";
			}
		}
		return isSubTableLike;
	}

	public List<String> getIsSubTables() {
		return isSubTables;
	}

	public String getTopId() {
		return topId;
	}

	public String getTopIdLike() {
		if (topIdLike != null && topIdLike.trim().length() > 0) {
			if (!topIdLike.startsWith("%")) {
				topIdLike = "%" + topIdLike;
			}
			if (!topIdLike.endsWith("%")) {
				topIdLike = topIdLike + "%";
			}
		}
		return topIdLike;
	}

	public List<String> getTopIds() {
		return topIds;
	}

	public String getFileDotFileId() {
		return fileDotFileId;
	}

	public String getFileDotFileIdLike() {
		if (fileDotFileIdLike != null && fileDotFileIdLike.trim().length() > 0) {
			if (!fileDotFileIdLike.startsWith("%")) {
				fileDotFileIdLike = "%" + fileDotFileIdLike;
			}
			if (!fileDotFileIdLike.endsWith("%")) {
				fileDotFileIdLike = fileDotFileIdLike + "%";
			}
		}
		return fileDotFileIdLike;
	}

	public List<String> getFileDotFileIds() {
		return fileDotFileIds;
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

	public String getPrintFileId() {
		return printFileId;
	}

	public String getPrintFileIdLike() {
		if (printFileIdLike != null && printFileIdLike.trim().length() > 0) {
			if (!printFileIdLike.startsWith("%")) {
				printFileIdLike = "%" + printFileIdLike;
			}
			if (!printFileIdLike.endsWith("%")) {
				printFileIdLike = printFileIdLike + "%";
			}
		}
		return printFileIdLike;
	}

	public List<String> getPrintFileIds() {
		return printFileIds;
	}

	public Integer getIntUseSTreeWBS() {
		return intUseSTreeWBS;
	}

	public Integer getIntUseSTreeWBSGreaterThanOrEqual() {
		return intUseSTreeWBSGreaterThanOrEqual;
	}

	public Integer getIntUseSTreeWBSLessThanOrEqual() {
		return intUseSTreeWBSLessThanOrEqual;
	}

	public List<Integer> getIntUseSTreeWBSs() {
		return intUseSTreeWBSs;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTableNameLike(String tableNameLike) {
		this.tableNameLike = tableNameLike;
	}

	public void setTableNames(List<String> tableNames) {
		this.tableNames = tableNames;
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

	public void setAddType(Integer addType) {
		this.addType = addType;
	}

	public void setAddTypeGreaterThanOrEqual(Integer addTypeGreaterThanOrEqual) {
		this.addTypeGreaterThanOrEqual = addTypeGreaterThanOrEqual;
	}

	public void setAddTypeLessThanOrEqual(Integer addTypeLessThanOrEqual) {
		this.addTypeLessThanOrEqual = addTypeLessThanOrEqual;
	}

	public void setAddTypes(List<Integer> addTypes) {
		this.addTypes = addTypes;
	}

	public void setMaxUser(Integer maxUser) {
		this.maxUser = maxUser;
	}

	public void setMaxUserGreaterThanOrEqual(Integer maxUserGreaterThanOrEqual) {
		this.maxUserGreaterThanOrEqual = maxUserGreaterThanOrEqual;
	}

	public void setMaxUserLessThanOrEqual(Integer maxUserLessThanOrEqual) {
		this.maxUserLessThanOrEqual = maxUserLessThanOrEqual;
	}

	public void setMaxUsers(List<Integer> maxUsers) {
		this.maxUsers = maxUsers;
	}

	public void setMaxSys(Integer maxSys) {
		this.maxSys = maxSys;
	}

	public void setMaxSysGreaterThanOrEqual(Integer maxSysGreaterThanOrEqual) {
		this.maxSysGreaterThanOrEqual = maxSysGreaterThanOrEqual;
	}

	public void setMaxSysLessThanOrEqual(Integer maxSysLessThanOrEqual) {
		this.maxSysLessThanOrEqual = maxSysLessThanOrEqual;
	}

	public void setMaxSyss(List<Integer> maxSyss) {
		this.maxSyss = maxSyss;
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

	public void setSysNum(String sysNum) {
		this.sysNum = sysNum;
	}

	public void setSysNumLike(String sysNumLike) {
		this.sysNumLike = sysNumLike;
	}

	public void setSysNums(List<String> sysNums) {
		this.sysNums = sysNums;
	}

	public void setIsSubTable(String isSubTable) {
		this.isSubTable = isSubTable;
	}

	public void setIsSubTableLike(String isSubTableLike) {
		this.isSubTableLike = isSubTableLike;
	}

	public void setIsSubTables(List<String> isSubTables) {
		this.isSubTables = isSubTables;
	}

	public void setTopId(String topId) {
		this.topId = topId;
	}

	public void setTopIdLike(String topIdLike) {
		this.topIdLike = topIdLike;
	}

	public void setTopIds(List<String> topIds) {
		this.topIds = topIds;
	}

	public void setFileDotFileId(String fileDotFileId) {
		this.fileDotFileId = fileDotFileId;
	}

	public void setFileDotFileIdLike(String fileDotFileIdLike) {
		this.fileDotFileIdLike = fileDotFileIdLike;
	}

	public void setFileDotFileIds(List<String> fileDotFileIds) {
		this.fileDotFileIds = fileDotFileIds;
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

	public void setWinHeightGreaterThanOrEqual(
			Integer winHeightGreaterThanOrEqual) {
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

	public void setIntLineEditGreaterThanOrEqual(
			Integer intLineEditGreaterThanOrEqual) {
		this.intLineEditGreaterThanOrEqual = intLineEditGreaterThanOrEqual;
	}

	public void setIntLineEditLessThanOrEqual(Integer intLineEditLessThanOrEqual) {
		this.intLineEditLessThanOrEqual = intLineEditLessThanOrEqual;
	}

	public void setIntLineEdits(List<Integer> intLineEdits) {
		this.intLineEdits = intLineEdits;
	}

	public void setPrintFileId(String printFileId) {
		this.printFileId = printFileId;
	}

	public void setPrintFileIdLike(String printFileIdLike) {
		this.printFileIdLike = printFileIdLike;
	}

	public void setPrintFileIds(List<String> printFileIds) {
		this.printFileIds = printFileIds;
	}

	public void setIntUseSTreeWBS(Integer intUseSTreeWBS) {
		this.intUseSTreeWBS = intUseSTreeWBS;
	}

	public void setIntUseSTreeWBSGreaterThanOrEqual(
			Integer intUseSTreeWBSGreaterThanOrEqual) {
		this.intUseSTreeWBSGreaterThanOrEqual = intUseSTreeWBSGreaterThanOrEqual;
	}

	public void setIntUseSTreeWBSLessThanOrEqual(
			Integer intUseSTreeWBSLessThanOrEqual) {
		this.intUseSTreeWBSLessThanOrEqual = intUseSTreeWBSLessThanOrEqual;
	}

	public void setIntUseSTreeWBSs(List<Integer> intUseSTreeWBSs) {
		this.intUseSTreeWBSs = intUseSTreeWBSs;
	}

	public Integer getTreedotIndexId() {
		return treedotIndexId;
	}

	public void setTreedotIndexId(Integer treedotIndexId) {
		this.treedotIndexId = treedotIndexId;
	}

	public List<Integer> getTreedotIndexIds() {
		return treedotIndexIds;
	}

	public void setTreedotIndexIds(List<Integer> treedotIndexIds) {
		this.treedotIndexIds = treedotIndexIds;
	}

	public String getSqlCondition() {
		return sqlCondition;
	}

	public void setSqlCondition(String sqlCondition) {
		this.sqlCondition = sqlCondition;
	}

	public Boolean getSearchChildTable() {
		return searchChildTable;
	}

	public void setSearchChildTable(boolean searchChildTable) {
		this.searchChildTable = searchChildTable;
	}

	public CellDataTableQuery tableName(String tableName) {
		if (tableName == null) {
			throw new RuntimeException("tableName is null");
		}
		this.tableName = tableName;
		return this;
	}

	public CellDataTableQuery tableNameLike(String tableNameLike) {
		if (tableNameLike == null) {
			throw new RuntimeException("tableName is null");
		}
		this.tableNameLike = tableNameLike;
		return this;
	}

	public CellDataTableQuery tableNames(List<String> tableNames) {
		if (tableNames == null) {
			throw new RuntimeException("tableNames is empty ");
		}
		this.tableNames = tableNames;
		return this;
	}

	public CellDataTableQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public CellDataTableQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public CellDataTableQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public CellDataTableQuery addType(Integer addType) {
		if (addType == null) {
			throw new RuntimeException("addType is null");
		}
		this.addType = addType;
		return this;
	}

	public CellDataTableQuery addTypeGreaterThanOrEqual(
			Integer addTypeGreaterThanOrEqual) {
		if (addTypeGreaterThanOrEqual == null) {
			throw new RuntimeException("addType is null");
		}
		this.addTypeGreaterThanOrEqual = addTypeGreaterThanOrEqual;
		return this;
	}

	public CellDataTableQuery addTypeLessThanOrEqual(
			Integer addTypeLessThanOrEqual) {
		if (addTypeLessThanOrEqual == null) {
			throw new RuntimeException("addType is null");
		}
		this.addTypeLessThanOrEqual = addTypeLessThanOrEqual;
		return this;
	}

	public CellDataTableQuery addTypes(List<Integer> addTypes) {
		if (addTypes == null) {
			throw new RuntimeException("addTypes is empty ");
		}
		this.addTypes = addTypes;
		return this;
	}

	public CellDataTableQuery maxUser(Integer maxUser) {
		if (maxUser == null) {
			throw new RuntimeException("maxUser is null");
		}
		this.maxUser = maxUser;
		return this;
	}

	public CellDataTableQuery maxUserGreaterThanOrEqual(
			Integer maxUserGreaterThanOrEqual) {
		if (maxUserGreaterThanOrEqual == null) {
			throw new RuntimeException("maxUser is null");
		}
		this.maxUserGreaterThanOrEqual = maxUserGreaterThanOrEqual;
		return this;
	}

	public CellDataTableQuery maxUserLessThanOrEqual(
			Integer maxUserLessThanOrEqual) {
		if (maxUserLessThanOrEqual == null) {
			throw new RuntimeException("maxUser is null");
		}
		this.maxUserLessThanOrEqual = maxUserLessThanOrEqual;
		return this;
	}

	public CellDataTableQuery maxUsers(List<Integer> maxUsers) {
		if (maxUsers == null) {
			throw new RuntimeException("maxUsers is empty ");
		}
		this.maxUsers = maxUsers;
		return this;
	}

	public CellDataTableQuery maxSys(Integer maxSys) {
		if (maxSys == null) {
			throw new RuntimeException("maxSys is null");
		}
		this.maxSys = maxSys;
		return this;
	}

	public CellDataTableQuery maxSysGreaterThanOrEqual(
			Integer maxSysGreaterThanOrEqual) {
		if (maxSysGreaterThanOrEqual == null) {
			throw new RuntimeException("maxSys is null");
		}
		this.maxSysGreaterThanOrEqual = maxSysGreaterThanOrEqual;
		return this;
	}

	public CellDataTableQuery maxSysLessThanOrEqual(
			Integer maxSysLessThanOrEqual) {
		if (maxSysLessThanOrEqual == null) {
			throw new RuntimeException("maxSys is null");
		}
		this.maxSysLessThanOrEqual = maxSysLessThanOrEqual;
		return this;
	}

	public CellDataTableQuery maxSyss(List<Integer> maxSyss) {
		if (maxSyss == null) {
			throw new RuntimeException("maxSyss is empty ");
		}
		this.maxSyss = maxSyss;
		return this;
	}

	public CellDataTableQuery userId(String userId) {
		if (userId == null) {
			throw new RuntimeException("userId is null");
		}
		this.userId = userId;
		return this;
	}

	public CellDataTableQuery userIdLike(String userIdLike) {
		if (userIdLike == null) {
			throw new RuntimeException("userId is null");
		}
		this.userIdLike = userIdLike;
		return this;
	}

	public CellDataTableQuery userIds(List<String> userIds) {
		if (userIds == null) {
			throw new RuntimeException("userIds is empty ");
		}
		this.userIds = userIds;
		return this;
	}

	public CellDataTableQuery ctimeGreaterThanOrEqual(
			Date ctimeGreaterThanOrEqual) {
		if (ctimeGreaterThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
		return this;
	}

	public CellDataTableQuery ctimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		if (ctimeLessThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
		return this;
	}

	public CellDataTableQuery content(String content) {
		if (content == null) {
			throw new RuntimeException("content is null");
		}
		this.content = content;
		return this;
	}

	public CellDataTableQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public CellDataTableQuery contents(List<String> contents) {
		if (contents == null) {
			throw new RuntimeException("contents is empty ");
		}
		this.contents = contents;
		return this;
	}

	public CellDataTableQuery sysNum(String sysNum) {
		if (sysNum == null) {
			throw new RuntimeException("sysNum is null");
		}
		this.sysNum = sysNum;
		return this;
	}

	public CellDataTableQuery sysNumLike(String sysNumLike) {
		if (sysNumLike == null) {
			throw new RuntimeException("sysNum is null");
		}
		this.sysNumLike = sysNumLike;
		return this;
	}

	public CellDataTableQuery sysNums(List<String> sysNums) {
		if (sysNums == null) {
			throw new RuntimeException("sysNums is empty ");
		}
		this.sysNums = sysNums;
		return this;
	}

	public CellDataTableQuery isSubTable(String isSubTable) {
		if (isSubTable == null) {
			throw new RuntimeException("isSubTable is null");
		}
		this.isSubTable = isSubTable;
		return this;
	}

	public CellDataTableQuery isSubTableLike(String isSubTableLike) {
		if (isSubTableLike == null) {
			throw new RuntimeException("isSubTable is null");
		}
		this.isSubTableLike = isSubTableLike;
		return this;
	}

	public CellDataTableQuery isSubTables(List<String> isSubTables) {
		if (isSubTables == null) {
			throw new RuntimeException("isSubTables is empty ");
		}
		this.isSubTables = isSubTables;
		return this;
	}

	public CellDataTableQuery topId(String topId) {
		if (topId == null) {
			throw new RuntimeException("topId is null");
		}
		this.topId = topId;
		return this;
	}

	public CellDataTableQuery topIdLike(String topIdLike) {
		if (topIdLike == null) {
			throw new RuntimeException("topId is null");
		}
		this.topIdLike = topIdLike;
		return this;
	}

	public CellDataTableQuery topIds(List<String> topIds) {
		if (topIds == null) {
			throw new RuntimeException("topIds is empty ");
		}
		this.topIds = topIds;
		return this;
	}

	public CellDataTableQuery fileDotFileId(String fileDotFileId) {
		if (fileDotFileId == null) {
			throw new RuntimeException("fileDotFileId is null");
		}
		this.fileDotFileId = fileDotFileId;
		return this;
	}

	public CellDataTableQuery fileDotFileIdLike(String fileDotFileIdLike) {
		if (fileDotFileIdLike == null) {
			throw new RuntimeException("fileDotFileId is null");
		}
		this.fileDotFileIdLike = fileDotFileIdLike;
		return this;
	}

	public CellDataTableQuery fileDotFileIds(List<String> fileDotFileIds) {
		if (fileDotFileIds == null) {
			throw new RuntimeException("fileDotFileIds is empty ");
		}
		this.fileDotFileIds = fileDotFileIds;
		return this;
	}

	public CellDataTableQuery indexId(Integer indexId) {
		if (indexId == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexId = indexId;
		return this;
	}

	public CellDataTableQuery indexIdGreaterThanOrEqual(
			Integer indexIdGreaterThanOrEqual) {
		if (indexIdGreaterThanOrEqual == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexIdGreaterThanOrEqual = indexIdGreaterThanOrEqual;
		return this;
	}

	public CellDataTableQuery indexIdLessThanOrEqual(
			Integer indexIdLessThanOrEqual) {
		if (indexIdLessThanOrEqual == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexIdLessThanOrEqual = indexIdLessThanOrEqual;
		return this;
	}

	public CellDataTableQuery indexIds(List<Integer> indexIds) {
		if (indexIds == null) {
			throw new RuntimeException("indexIds is empty ");
		}
		this.indexIds = indexIds;
		return this;
	}

	public CellDataTableQuery winWidth(Integer winWidth) {
		if (winWidth == null) {
			throw new RuntimeException("winWidth is null");
		}
		this.winWidth = winWidth;
		return this;
	}

	public CellDataTableQuery winWidthGreaterThanOrEqual(
			Integer winWidthGreaterThanOrEqual) {
		if (winWidthGreaterThanOrEqual == null) {
			throw new RuntimeException("winWidth is null");
		}
		this.winWidthGreaterThanOrEqual = winWidthGreaterThanOrEqual;
		return this;
	}

	public CellDataTableQuery winWidthLessThanOrEqual(
			Integer winWidthLessThanOrEqual) {
		if (winWidthLessThanOrEqual == null) {
			throw new RuntimeException("winWidth is null");
		}
		this.winWidthLessThanOrEqual = winWidthLessThanOrEqual;
		return this;
	}

	public CellDataTableQuery winWidths(List<Integer> winWidths) {
		if (winWidths == null) {
			throw new RuntimeException("winWidths is empty ");
		}
		this.winWidths = winWidths;
		return this;
	}

	public CellDataTableQuery winHeight(Integer winHeight) {
		if (winHeight == null) {
			throw new RuntimeException("winHeight is null");
		}
		this.winHeight = winHeight;
		return this;
	}

	public CellDataTableQuery winHeightGreaterThanOrEqual(
			Integer winHeightGreaterThanOrEqual) {
		if (winHeightGreaterThanOrEqual == null) {
			throw new RuntimeException("winHeight is null");
		}
		this.winHeightGreaterThanOrEqual = winHeightGreaterThanOrEqual;
		return this;
	}

	public CellDataTableQuery winHeightLessThanOrEqual(
			Integer winHeightLessThanOrEqual) {
		if (winHeightLessThanOrEqual == null) {
			throw new RuntimeException("winHeight is null");
		}
		this.winHeightLessThanOrEqual = winHeightLessThanOrEqual;
		return this;
	}

	public CellDataTableQuery winHeights(List<Integer> winHeights) {
		if (winHeights == null) {
			throw new RuntimeException("winHeights is empty ");
		}
		this.winHeights = winHeights;
		return this;
	}

	public CellDataTableQuery intQuote(Integer intQuote) {
		if (intQuote == null) {
			throw new RuntimeException("intQuote is null");
		}
		this.intQuote = intQuote;
		return this;
	}

	public CellDataTableQuery intQuoteGreaterThanOrEqual(
			Integer intQuoteGreaterThanOrEqual) {
		if (intQuoteGreaterThanOrEqual == null) {
			throw new RuntimeException("intQuote is null");
		}
		this.intQuoteGreaterThanOrEqual = intQuoteGreaterThanOrEqual;
		return this;
	}

	public CellDataTableQuery intQuoteLessThanOrEqual(
			Integer intQuoteLessThanOrEqual) {
		if (intQuoteLessThanOrEqual == null) {
			throw new RuntimeException("intQuote is null");
		}
		this.intQuoteLessThanOrEqual = intQuoteLessThanOrEqual;
		return this;
	}

	public CellDataTableQuery intQuotes(List<Integer> intQuotes) {
		if (intQuotes == null) {
			throw new RuntimeException("intQuotes is empty ");
		}
		this.intQuotes = intQuotes;
		return this;
	}

	public CellDataTableQuery intLineEdit(Integer intLineEdit) {
		if (intLineEdit == null) {
			throw new RuntimeException("intLineEdit is null");
		}
		this.intLineEdit = intLineEdit;
		return this;
	}

	public CellDataTableQuery intLineEditGreaterThanOrEqual(
			Integer intLineEditGreaterThanOrEqual) {
		if (intLineEditGreaterThanOrEqual == null) {
			throw new RuntimeException("intLineEdit is null");
		}
		this.intLineEditGreaterThanOrEqual = intLineEditGreaterThanOrEqual;
		return this;
	}

	public CellDataTableQuery intLineEditLessThanOrEqual(
			Integer intLineEditLessThanOrEqual) {
		if (intLineEditLessThanOrEqual == null) {
			throw new RuntimeException("intLineEdit is null");
		}
		this.intLineEditLessThanOrEqual = intLineEditLessThanOrEqual;
		return this;
	}

	public CellDataTableQuery intLineEdits(List<Integer> intLineEdits) {
		if (intLineEdits == null) {
			throw new RuntimeException("intLineEdits is empty ");
		}
		this.intLineEdits = intLineEdits;
		return this;
	}

	public CellDataTableQuery printFileId(String printFileId) {
		if (printFileId == null) {
			throw new RuntimeException("printFileId is null");
		}
		this.printFileId = printFileId;
		return this;
	}

	public CellDataTableQuery printFileIdLike(String printFileIdLike) {
		if (printFileIdLike == null) {
			throw new RuntimeException("printFileId is null");
		}
		this.printFileIdLike = printFileIdLike;
		return this;
	}

	public CellDataTableQuery printFileIds(List<String> printFileIds) {
		if (printFileIds == null) {
			throw new RuntimeException("printFileIds is empty ");
		}
		this.printFileIds = printFileIds;
		return this;
	}

	public CellDataTableQuery intUseSTreeWBS(Integer intUseSTreeWBS) {
		if (intUseSTreeWBS == null) {
			throw new RuntimeException("intUseSTreeWBS is null");
		}
		this.intUseSTreeWBS = intUseSTreeWBS;
		return this;
	}

	public CellDataTableQuery intUseSTreeWBSGreaterThanOrEqual(
			Integer intUseSTreeWBSGreaterThanOrEqual) {
		if (intUseSTreeWBSGreaterThanOrEqual == null) {
			throw new RuntimeException("intUseSTreeWBS is null");
		}
		this.intUseSTreeWBSGreaterThanOrEqual = intUseSTreeWBSGreaterThanOrEqual;
		return this;
	}

	public CellDataTableQuery intUseSTreeWBSLessThanOrEqual(
			Integer intUseSTreeWBSLessThanOrEqual) {
		if (intUseSTreeWBSLessThanOrEqual == null) {
			throw new RuntimeException("intUseSTreeWBS is null");
		}
		this.intUseSTreeWBSLessThanOrEqual = intUseSTreeWBSLessThanOrEqual;
		return this;
	}

	public CellDataTableQuery intUseSTreeWBSs(List<Integer> intUseSTreeWBSs) {
		if (intUseSTreeWBSs == null) {
			throw new RuntimeException("intUseSTreeWBSs is empty ");
		}
		this.intUseSTreeWBSs = intUseSTreeWBSs;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("tableName".equals(sortColumn)) {
				orderBy = "E.TABLENAME" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME" + a_x;
			}

			if ("addType".equals(sortColumn)) {
				orderBy = "E.ADDTYPE" + a_x;
			}

			if ("maxUser".equals(sortColumn)) {
				orderBy = "E.MAXUSER" + a_x;
			}

			if ("maxSys".equals(sortColumn)) {
				orderBy = "E.MAXSYS" + a_x;
			}

			if ("userId".equals(sortColumn)) {
				orderBy = "E.USERID" + a_x;
			}

			if ("ctime".equals(sortColumn)) {
				orderBy = "E.CTIME" + a_x;
			}

			if ("content".equals(sortColumn)) {
				orderBy = "E.CONTENT" + a_x;
			}

			if ("sysNum".equals(sortColumn)) {
				orderBy = "E.SYSNUM" + a_x;
			}

			if ("isSubTable".equals(sortColumn)) {
				orderBy = "E.ISSUBTABLE" + a_x;
			}

			if ("topId".equals(sortColumn)) {
				orderBy = "E.TOPID" + a_x;
			}

			if ("fileDotFileId".equals(sortColumn)) {
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

			if ("printFileId".equals(sortColumn)) {
				orderBy = "E.PRINTFILEID" + a_x;
			}

			if ("intUseSTreeWBS".equals(sortColumn)) {
				orderBy = "E.INTUSESTREEWBS" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("tableName", "TABLENAME");
		addColumn("name", "NAME");
		addColumn("addType", "ADDTYPE");
		addColumn("maxUser", "MAXUSER");
		addColumn("maxSys", "MAXSYS");
		addColumn("userId", "USERID");
		addColumn("ctime", "CTIME");
		addColumn("content", "CONTENT");
		addColumn("sysNum", "SYSNUM");
		addColumn("isSubTable", "ISSUBTABLE");
		addColumn("topId", "TOPID");
		addColumn("fileDotFileId", "FILEDOT_FILEID");
		addColumn("indexId", "INDEX_ID");
		addColumn("winWidth", "WIN_WIDTH");
		addColumn("winHeight", "WIN_HEIGHT");
		addColumn("intQuote", "INTQUOTE");
		addColumn("intLineEdit", "INTLINEEDIT");
		addColumn("printFileId", "PRINTFILEID");
		addColumn("intUseSTreeWBS", "INTUSESTREEWBS");
	}

}