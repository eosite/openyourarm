package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class CellDataFieldQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected String tableId;
	protected String tableIdLike;
	protected List<String> tableIds;
	protected String fieldName;
	protected String fieldNameLike;
	protected List<String> fieldNames;
	protected String userId;
	protected String userIdLike;
	protected List<String> userIds;
	protected Integer maxUser;
	protected Integer maxUserGreaterThanOrEqual;
	protected Integer maxUserLessThanOrEqual;
	protected List<Integer> maxUsers;
	protected Integer maxSys;
	protected Integer maxSysGreaterThanOrEqual;
	protected Integer maxSysLessThanOrEqual;
	protected List<Integer> maxSyss;
	protected Date ctimeGreaterThanOrEqual;
	protected Date ctimeLessThanOrEqual;
	protected String sysNum;
	protected String sysNumLike;
	protected List<String> sysNums;
	protected String tableName;
	protected String tableNameLike;
	protected List<String> tableNames;
	protected String dname;
	protected String dnameLike;
	protected List<String> dnames;
	protected String userIndex;
	protected String userIndexLike;
	protected List<String> userIndexs;
	protected String treeTableNameB;
	protected String treeTableNameBLike;
	protected List<String> treeTableNameBs;

	public CellDataFieldQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getTableId() {
		return tableId;
	}

	public String getTableIdLike() {
		if (tableIdLike != null && tableIdLike.trim().length() > 0) {
			if (!tableIdLike.startsWith("%")) {
				tableIdLike = "%" + tableIdLike;
			}
			if (!tableIdLike.endsWith("%")) {
				tableIdLike = tableIdLike + "%";
			}
		}
		return tableIdLike;
	}

	public List<String> getTableIds() {
		return tableIds;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getFieldNameLike() {
		if (fieldNameLike != null && fieldNameLike.trim().length() > 0) {
			if (!fieldNameLike.startsWith("%")) {
				fieldNameLike = "%" + fieldNameLike;
			}
			if (!fieldNameLike.endsWith("%")) {
				fieldNameLike = fieldNameLike + "%";
			}
		}
		return fieldNameLike;
	}

	public List<String> getFieldNames() {
		return fieldNames;
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

	public Date getCtimeGreaterThanOrEqual() {
		return ctimeGreaterThanOrEqual;
	}

	public Date getCtimeLessThanOrEqual() {
		return ctimeLessThanOrEqual;
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

	public String getDname() {
		return dname;
	}

	public String getDnameLike() {
		if (dnameLike != null && dnameLike.trim().length() > 0) {
			if (!dnameLike.startsWith("%")) {
				dnameLike = "%" + dnameLike;
			}
			if (!dnameLike.endsWith("%")) {
				dnameLike = dnameLike + "%";
			}
		}
		return dnameLike;
	}

	public List<String> getDnames() {
		return dnames;
	}

	public String getUserIndex() {
		return userIndex;
	}

	public String getUserIndexLike() {
		if (userIndexLike != null && userIndexLike.trim().length() > 0) {
			if (!userIndexLike.startsWith("%")) {
				userIndexLike = "%" + userIndexLike;
			}
			if (!userIndexLike.endsWith("%")) {
				userIndexLike = userIndexLike + "%";
			}
		}
		return userIndexLike;
	}

	public List<String> getUserIndexs() {
		return userIndexs;
	}

	public String getTreeTableNameB() {
		return treeTableNameB;
	}

	public String getTreeTableNameBLike() {
		if (treeTableNameBLike != null
				&& treeTableNameBLike.trim().length() > 0) {
			if (!treeTableNameBLike.startsWith("%")) {
				treeTableNameBLike = "%" + treeTableNameBLike;
			}
			if (!treeTableNameBLike.endsWith("%")) {
				treeTableNameBLike = treeTableNameBLike + "%";
			}
		}
		return treeTableNameBLike;
	}

	public List<String> getTreeTableNameBs() {
		return treeTableNameBs;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public void setTableIdLike(String tableIdLike) {
		this.tableIdLike = tableIdLike;
	}

	public void setTableIds(List<String> tableIds) {
		this.tableIds = tableIds;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setFieldNameLike(String fieldNameLike) {
		this.fieldNameLike = fieldNameLike;
	}

	public void setFieldNames(List<String> fieldNames) {
		this.fieldNames = fieldNames;
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

	public void setCtimeGreaterThanOrEqual(Date ctimeGreaterThanOrEqual) {
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
	}

	public void setCtimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
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

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTableNameLike(String tableNameLike) {
		this.tableNameLike = tableNameLike;
	}

	public void setTableNames(List<String> tableNames) {
		this.tableNames = tableNames;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public void setDnameLike(String dnameLike) {
		this.dnameLike = dnameLike;
	}

	public void setDnames(List<String> dnames) {
		this.dnames = dnames;
	}

	public void setUserIndex(String userIndex) {
		this.userIndex = userIndex;
	}

	public void setUserIndexLike(String userIndexLike) {
		this.userIndexLike = userIndexLike;
	}

	public void setUserIndexs(List<String> userIndexs) {
		this.userIndexs = userIndexs;
	}

	public void setTreeTableNameB(String treeTableNameB) {
		this.treeTableNameB = treeTableNameB;
	}

	public void setTreeTableNameBLike(String treeTableNameBLike) {
		this.treeTableNameBLike = treeTableNameBLike;
	}

	public void setTreeTableNameBs(List<String> treeTableNameBs) {
		this.treeTableNameBs = treeTableNameBs;
	}

	public CellDataFieldQuery tableId(String tableId) {
		if (tableId == null) {
			throw new RuntimeException("tableId is null");
		}
		this.tableId = tableId;
		return this;
	}

	public CellDataFieldQuery tableIdLike(String tableIdLike) {
		if (tableIdLike == null) {
			throw new RuntimeException("tableId is null");
		}
		this.tableIdLike = tableIdLike;
		return this;
	}

	public CellDataFieldQuery tableIds(List<String> tableIds) {
		if (tableIds == null) {
			throw new RuntimeException("tableIds is empty ");
		}
		this.tableIds = tableIds;
		return this;
	}

	public CellDataFieldQuery fieldName(String fieldName) {
		if (fieldName == null) {
			throw new RuntimeException("fieldName is null");
		}
		this.fieldName = fieldName;
		return this;
	}

	public CellDataFieldQuery fieldNameLike(String fieldNameLike) {
		if (fieldNameLike == null) {
			throw new RuntimeException("fieldName is null");
		}
		this.fieldNameLike = fieldNameLike;
		return this;
	}

	public CellDataFieldQuery fieldNames(List<String> fieldNames) {
		if (fieldNames == null) {
			throw new RuntimeException("fieldNames is empty ");
		}
		this.fieldNames = fieldNames;
		return this;
	}

	public CellDataFieldQuery userId(String userId) {
		if (userId == null) {
			throw new RuntimeException("userId is null");
		}
		this.userId = userId;
		return this;
	}

	public CellDataFieldQuery userIdLike(String userIdLike) {
		if (userIdLike == null) {
			throw new RuntimeException("userId is null");
		}
		this.userIdLike = userIdLike;
		return this;
	}

	public CellDataFieldQuery userIds(List<String> userIds) {
		if (userIds == null) {
			throw new RuntimeException("userIds is empty ");
		}
		this.userIds = userIds;
		return this;
	}

	public CellDataFieldQuery maxUser(Integer maxUser) {
		if (maxUser == null) {
			throw new RuntimeException("maxUser is null");
		}
		this.maxUser = maxUser;
		return this;
	}

	public CellDataFieldQuery maxUserGreaterThanOrEqual(
			Integer maxUserGreaterThanOrEqual) {
		if (maxUserGreaterThanOrEqual == null) {
			throw new RuntimeException("maxUser is null");
		}
		this.maxUserGreaterThanOrEqual = maxUserGreaterThanOrEqual;
		return this;
	}

	public CellDataFieldQuery maxUserLessThanOrEqual(
			Integer maxUserLessThanOrEqual) {
		if (maxUserLessThanOrEqual == null) {
			throw new RuntimeException("maxUser is null");
		}
		this.maxUserLessThanOrEqual = maxUserLessThanOrEqual;
		return this;
	}

	public CellDataFieldQuery maxUsers(List<Integer> maxUsers) {
		if (maxUsers == null) {
			throw new RuntimeException("maxUsers is empty ");
		}
		this.maxUsers = maxUsers;
		return this;
	}

	public CellDataFieldQuery maxSys(Integer maxSys) {
		if (maxSys == null) {
			throw new RuntimeException("maxSys is null");
		}
		this.maxSys = maxSys;
		return this;
	}

	public CellDataFieldQuery maxSysGreaterThanOrEqual(
			Integer maxSysGreaterThanOrEqual) {
		if (maxSysGreaterThanOrEqual == null) {
			throw new RuntimeException("maxSys is null");
		}
		this.maxSysGreaterThanOrEqual = maxSysGreaterThanOrEqual;
		return this;
	}

	public CellDataFieldQuery maxSysLessThanOrEqual(
			Integer maxSysLessThanOrEqual) {
		if (maxSysLessThanOrEqual == null) {
			throw new RuntimeException("maxSys is null");
		}
		this.maxSysLessThanOrEqual = maxSysLessThanOrEqual;
		return this;
	}

	public CellDataFieldQuery maxSyss(List<Integer> maxSyss) {
		if (maxSyss == null) {
			throw new RuntimeException("maxSyss is empty ");
		}
		this.maxSyss = maxSyss;
		return this;
	}

	public CellDataFieldQuery ctimeGreaterThanOrEqual(
			Date ctimeGreaterThanOrEqual) {
		if (ctimeGreaterThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
		return this;
	}

	public CellDataFieldQuery ctimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		if (ctimeLessThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
		return this;
	}

	public CellDataFieldQuery sysNum(String sysNum) {
		if (sysNum == null) {
			throw new RuntimeException("sysNum is null");
		}
		this.sysNum = sysNum;
		return this;
	}

	public CellDataFieldQuery sysNumLike(String sysNumLike) {
		if (sysNumLike == null) {
			throw new RuntimeException("sysNum is null");
		}
		this.sysNumLike = sysNumLike;
		return this;
	}

	public CellDataFieldQuery sysNums(List<String> sysNums) {
		if (sysNums == null) {
			throw new RuntimeException("sysNums is empty ");
		}
		this.sysNums = sysNums;
		return this;
	}

	public CellDataFieldQuery tableName(String tableName) {
		if (tableName == null) {
			throw new RuntimeException("tableName is null");
		}
		this.tableName = tableName;
		return this;
	}

	public CellDataFieldQuery tableNameLike(String tableNameLike) {
		if (tableNameLike == null) {
			throw new RuntimeException("tableName is null");
		}
		this.tableNameLike = tableNameLike;
		return this;
	}

	public CellDataFieldQuery tableNames(List<String> tableNames) {
		if (tableNames == null) {
			throw new RuntimeException("tableNames is empty ");
		}
		this.tableNames = tableNames;
		return this;
	}

	public CellDataFieldQuery dname(String dname) {
		if (dname == null) {
			throw new RuntimeException("dname is null");
		}
		this.dname = dname;
		return this;
	}

	public CellDataFieldQuery dnameLike(String dnameLike) {
		if (dnameLike == null) {
			throw new RuntimeException("dname is null");
		}
		this.dnameLike = dnameLike;
		return this;
	}

	public CellDataFieldQuery dnames(List<String> dnames) {
		if (dnames == null) {
			throw new RuntimeException("dnames is empty ");
		}
		this.dnames = dnames;
		return this;
	}

	public CellDataFieldQuery userIndex(String userIndex) {
		if (userIndex == null) {
			throw new RuntimeException("userIndex is null");
		}
		this.userIndex = userIndex;
		return this;
	}

	public CellDataFieldQuery userIndexLike(String userIndexLike) {
		if (userIndexLike == null) {
			throw new RuntimeException("userIndex is null");
		}
		this.userIndexLike = userIndexLike;
		return this;
	}

	public CellDataFieldQuery userIndexs(List<String> userIndexs) {
		if (userIndexs == null) {
			throw new RuntimeException("userIndexs is empty ");
		}
		this.userIndexs = userIndexs;
		return this;
	}

	public CellDataFieldQuery treeTableNameB(String treeTableNameB) {
		if (treeTableNameB == null) {
			throw new RuntimeException("treeTableNameB is null");
		}
		this.treeTableNameB = treeTableNameB;
		return this;
	}

	public CellDataFieldQuery treeTableNameBLike(String treeTableNameBLike) {
		if (treeTableNameBLike == null) {
			throw new RuntimeException("treeTableNameB is null");
		}
		this.treeTableNameBLike = treeTableNameBLike;
		return this;
	}

	public CellDataFieldQuery treeTableNameBs(List<String> treeTableNameBs) {
		if (treeTableNameBs == null) {
			throw new RuntimeException("treeTableNameBs is empty ");
		}
		this.treeTableNameBs = treeTableNameBs;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("tableId".equals(sortColumn)) {
				orderBy = "E.TABLEID" + a_x;
			}

			if ("fieldName".equals(sortColumn)) {
				orderBy = "E.FIELDNAME" + a_x;
			}

			if ("userId".equals(sortColumn)) {
				orderBy = "E.USERID" + a_x;
			}

			if ("maxUser".equals(sortColumn)) {
				orderBy = "E.MAXUSER" + a_x;
			}

			if ("maxSys".equals(sortColumn)) {
				orderBy = "E.MAXSYS" + a_x;
			}

			if ("ctime".equals(sortColumn)) {
				orderBy = "E.CTIME" + a_x;
			}

			if ("sysNum".equals(sortColumn)) {
				orderBy = "E.SYSNUM" + a_x;
			}

			if ("tableName".equals(sortColumn)) {
				orderBy = "E.TABLENAME" + a_x;
			}

			if ("dname".equals(sortColumn)) {
				orderBy = "E.DNAME" + a_x;
			}

			if ("userIndex".equals(sortColumn)) {
				orderBy = "E.USERINDEX" + a_x;
			}

			if ("treeTableNameB".equals(sortColumn)) {
				orderBy = "E.TREETABLENAME_B" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("tableId", "TABLEID");
		addColumn("fieldName", "FIELDNAME");
		addColumn("userId", "USERID");
		addColumn("maxUser", "MAXUSER");
		addColumn("maxSys", "MAXSYS");
		addColumn("ctime", "CTIME");
		addColumn("sysNum", "SYSNUM");
		addColumn("tableName", "TABLENAME");
		addColumn("dname", "DNAME");
		addColumn("userIndex", "USERINDEX");
		addColumn("treeTableNameB", "TREETABLENAME_B");
	}

}