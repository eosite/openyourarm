package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class CellRepInfo2Query extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected String frmType;
	protected String frmTypeLike;
	protected List<String> frmTypes;
	protected Integer type;
	protected Integer typeGreaterThanOrEqual;
	protected Integer typeLessThanOrEqual;
	protected List<Integer> types;
	protected String content;
	protected String contentLike;
	protected List<String> contents;
	protected String formula;
	protected String formulaLike;
	protected List<String> formulas;
	protected String ostTableName;
	protected String ostTableNameLike;
	protected List<String> ostTableNames;
	protected Integer ostRow;
	protected Integer ostRowGreaterThanOrEqual;
	protected Integer ostRowLessThanOrEqual;
	protected List<Integer> ostRows;
	protected Integer ostCol;
	protected Integer ostColGreaterThanOrEqual;
	protected Integer ostColLessThanOrEqual;
	protected List<Integer> ostCols;
	protected Integer ostRowEnd;
	protected Integer ostRowEndGreaterThanOrEqual;
	protected Integer ostRowEndLessThanOrEqual;
	protected List<Integer> ostRowEnds;
	protected Integer ostColEnd;
	protected Integer ostColEndGreaterThanOrEqual;
	protected Integer ostColEndLessThanOrEqual;
	protected List<Integer> ostColEnds;
	protected String ostCellId;
	protected String ostCellIdLike;
	protected List<String> ostCellIds;
	protected String fileDotFileId;
	protected String fileDotFileIdLike;
	protected List<String> fileDotFileIds;
	protected Integer ostColor;
	protected Integer ostColorGreaterThanOrEqual;
	protected Integer ostColorLessThanOrEqual;
	protected List<Integer> ostColors;
	protected Integer ostWay;
	protected Integer ostWayGreaterThanOrEqual;
	protected Integer ostWayLessThanOrEqual;
	protected List<Integer> ostWays;
	protected Integer roleId;
	protected Integer roleIdGreaterThanOrEqual;
	protected Integer roleIdLessThanOrEqual;
	protected List<Integer> roleIds;
	protected String tableName;
	protected String tableNameLike;
	protected List<String> tableNames;
	protected String fname;
	protected String fnameLike;
	protected List<String> fnames;
	protected String dname;
	protected String dnameLike;
	protected List<String> dnames;
	protected String isSubTable;
	protected String isSubTableLike;
	protected List<String> isSubTables;
	protected String tableName2;
	protected String tableName2Like;
	protected List<String> tableName2s;
	protected Integer intAutoinValue;
	protected Integer intAutoinValueGreaterThanOrEqual;
	protected Integer intAutoinValueLessThanOrEqual;
	protected List<Integer> intAutoinValues;
	protected Integer intSelfClick;
	protected Integer intSelfClickGreaterThanOrEqual;
	protected Integer intSelfClickLessThanOrEqual;
	protected List<Integer> intSelfClicks;

	public CellRepInfo2Query() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getFrmType() {
		return frmType;
	}

	public String getFrmTypeLike() {
		if (frmTypeLike != null && frmTypeLike.trim().length() > 0) {
			if (!frmTypeLike.startsWith("%")) {
				frmTypeLike = "%" + frmTypeLike;
			}
			if (!frmTypeLike.endsWith("%")) {
				frmTypeLike = frmTypeLike + "%";
			}
		}
		return frmTypeLike;
	}

	public List<String> getFrmTypes() {
		return frmTypes;
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

	public String getFormula() {
		return formula;
	}

	public String getFormulaLike() {
		if (formulaLike != null && formulaLike.trim().length() > 0) {
			if (!formulaLike.startsWith("%")) {
				formulaLike = "%" + formulaLike;
			}
			if (!formulaLike.endsWith("%")) {
				formulaLike = formulaLike + "%";
			}
		}
		return formulaLike;
	}

	public List<String> getFormulas() {
		return formulas;
	}

	public String getOstTableName() {
		return ostTableName;
	}

	public String getOstTableNameLike() {
		if (ostTableNameLike != null && ostTableNameLike.trim().length() > 0) {
			if (!ostTableNameLike.startsWith("%")) {
				ostTableNameLike = "%" + ostTableNameLike;
			}
			if (!ostTableNameLike.endsWith("%")) {
				ostTableNameLike = ostTableNameLike + "%";
			}
		}
		return ostTableNameLike;
	}

	public List<String> getOstTableNames() {
		return ostTableNames;
	}

	public Integer getOstRow() {
		return ostRow;
	}

	public Integer getOstRowGreaterThanOrEqual() {
		return ostRowGreaterThanOrEqual;
	}

	public Integer getOstRowLessThanOrEqual() {
		return ostRowLessThanOrEqual;
	}

	public List<Integer> getOstRows() {
		return ostRows;
	}

	public Integer getOstCol() {
		return ostCol;
	}

	public Integer getOstColGreaterThanOrEqual() {
		return ostColGreaterThanOrEqual;
	}

	public Integer getOstColLessThanOrEqual() {
		return ostColLessThanOrEqual;
	}

	public List<Integer> getOstCols() {
		return ostCols;
	}

	public Integer getOstRowEnd() {
		return ostRowEnd;
	}

	public Integer getOstRowEndGreaterThanOrEqual() {
		return ostRowEndGreaterThanOrEqual;
	}

	public Integer getOstRowEndLessThanOrEqual() {
		return ostRowEndLessThanOrEqual;
	}

	public List<Integer> getOstRowEnds() {
		return ostRowEnds;
	}

	public Integer getOstColEnd() {
		return ostColEnd;
	}

	public Integer getOstColEndGreaterThanOrEqual() {
		return ostColEndGreaterThanOrEqual;
	}

	public Integer getOstColEndLessThanOrEqual() {
		return ostColEndLessThanOrEqual;
	}

	public List<Integer> getOstColEnds() {
		return ostColEnds;
	}

	public String getOstCellId() {
		return ostCellId;
	}

	public String getOstCellIdLike() {
		if (ostCellIdLike != null && ostCellIdLike.trim().length() > 0) {
			if (!ostCellIdLike.startsWith("%")) {
				ostCellIdLike = "%" + ostCellIdLike;
			}
			if (!ostCellIdLike.endsWith("%")) {
				ostCellIdLike = ostCellIdLike + "%";
			}
		}
		return ostCellIdLike;
	}

	public List<String> getOstCellIds() {
		return ostCellIds;
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

	public Integer getOstColor() {
		return ostColor;
	}

	public Integer getOstColorGreaterThanOrEqual() {
		return ostColorGreaterThanOrEqual;
	}

	public Integer getOstColorLessThanOrEqual() {
		return ostColorLessThanOrEqual;
	}

	public List<Integer> getOstColors() {
		return ostColors;
	}

	public Integer getOstWay() {
		return ostWay;
	}

	public Integer getOstWayGreaterThanOrEqual() {
		return ostWayGreaterThanOrEqual;
	}

	public Integer getOstWayLessThanOrEqual() {
		return ostWayLessThanOrEqual;
	}

	public List<Integer> getOstWays() {
		return ostWays;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public Integer getRoleIdGreaterThanOrEqual() {
		return roleIdGreaterThanOrEqual;
	}

	public Integer getRoleIdLessThanOrEqual() {
		return roleIdLessThanOrEqual;
	}

	public List<Integer> getRoleIds() {
		return roleIds;
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

	public String getFname() {
		return fname;
	}

	public String getFnameLike() {
		if (fnameLike != null && fnameLike.trim().length() > 0) {
			if (!fnameLike.startsWith("%")) {
				fnameLike = "%" + fnameLike;
			}
			if (!fnameLike.endsWith("%")) {
				fnameLike = fnameLike + "%";
			}
		}
		return fnameLike;
	}

	public List<String> getFnames() {
		return fnames;
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

	public String getTableName2() {
		return tableName2;
	}

	public String getTableName2Like() {
		if (tableName2Like != null && tableName2Like.trim().length() > 0) {
			if (!tableName2Like.startsWith("%")) {
				tableName2Like = "%" + tableName2Like;
			}
			if (!tableName2Like.endsWith("%")) {
				tableName2Like = tableName2Like + "%";
			}
		}
		return tableName2Like;
	}

	public List<String> getTableName2s() {
		return tableName2s;
	}

	public Integer getIntAutoinValue() {
		return intAutoinValue;
	}

	public Integer getIntAutoinValueGreaterThanOrEqual() {
		return intAutoinValueGreaterThanOrEqual;
	}

	public Integer getIntAutoinValueLessThanOrEqual() {
		return intAutoinValueLessThanOrEqual;
	}

	public List<Integer> getIntAutoinValues() {
		return intAutoinValues;
	}

	public Integer getIntSelfClick() {
		return intSelfClick;
	}

	public Integer getIntSelfClickGreaterThanOrEqual() {
		return intSelfClickGreaterThanOrEqual;
	}

	public Integer getIntSelfClickLessThanOrEqual() {
		return intSelfClickLessThanOrEqual;
	}

	public List<Integer> getIntSelfClicks() {
		return intSelfClicks;
	}

	public void setFrmType(String frmType) {
		this.frmType = frmType;
	}

	public void setFrmTypeLike(String frmTypeLike) {
		this.frmTypeLike = frmTypeLike;
	}

	public void setFrmTypes(List<String> frmTypes) {
		this.frmTypes = frmTypes;
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

	public void setContent(String content) {
		this.content = content;
	}

	public void setContentLike(String contentLike) {
		this.contentLike = contentLike;
	}

	public void setContents(List<String> contents) {
		this.contents = contents;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public void setFormulaLike(String formulaLike) {
		this.formulaLike = formulaLike;
	}

	public void setFormulas(List<String> formulas) {
		this.formulas = formulas;
	}

	public void setOstTableName(String ostTableName) {
		this.ostTableName = ostTableName;
	}

	public void setOstTableNameLike(String ostTableNameLike) {
		this.ostTableNameLike = ostTableNameLike;
	}

	public void setOstTableNames(List<String> ostTableNames) {
		this.ostTableNames = ostTableNames;
	}

	public void setOstRow(Integer ostRow) {
		this.ostRow = ostRow;
	}

	public void setOstRowGreaterThanOrEqual(Integer ostRowGreaterThanOrEqual) {
		this.ostRowGreaterThanOrEqual = ostRowGreaterThanOrEqual;
	}

	public void setOstRowLessThanOrEqual(Integer ostRowLessThanOrEqual) {
		this.ostRowLessThanOrEqual = ostRowLessThanOrEqual;
	}

	public void setOstRows(List<Integer> ostRows) {
		this.ostRows = ostRows;
	}

	public void setOstCol(Integer ostCol) {
		this.ostCol = ostCol;
	}

	public void setOstColGreaterThanOrEqual(Integer ostColGreaterThanOrEqual) {
		this.ostColGreaterThanOrEqual = ostColGreaterThanOrEqual;
	}

	public void setOstColLessThanOrEqual(Integer ostColLessThanOrEqual) {
		this.ostColLessThanOrEqual = ostColLessThanOrEqual;
	}

	public void setOstCols(List<Integer> ostCols) {
		this.ostCols = ostCols;
	}

	public void setOstRowEnd(Integer ostRowEnd) {
		this.ostRowEnd = ostRowEnd;
	}

	public void setOstRowEndGreaterThanOrEqual(
			Integer ostRowEndGreaterThanOrEqual) {
		this.ostRowEndGreaterThanOrEqual = ostRowEndGreaterThanOrEqual;
	}

	public void setOstRowEndLessThanOrEqual(Integer ostRowEndLessThanOrEqual) {
		this.ostRowEndLessThanOrEqual = ostRowEndLessThanOrEqual;
	}

	public void setOstRowEnds(List<Integer> ostRowEnds) {
		this.ostRowEnds = ostRowEnds;
	}

	public void setOstColEnd(Integer ostColEnd) {
		this.ostColEnd = ostColEnd;
	}

	public void setOstColEndGreaterThanOrEqual(
			Integer ostColEndGreaterThanOrEqual) {
		this.ostColEndGreaterThanOrEqual = ostColEndGreaterThanOrEqual;
	}

	public void setOstColEndLessThanOrEqual(Integer ostColEndLessThanOrEqual) {
		this.ostColEndLessThanOrEqual = ostColEndLessThanOrEqual;
	}

	public void setOstColEnds(List<Integer> ostColEnds) {
		this.ostColEnds = ostColEnds;
	}

	public void setOstCellId(String ostCellId) {
		this.ostCellId = ostCellId;
	}

	public void setOstCellIdLike(String ostCellIdLike) {
		this.ostCellIdLike = ostCellIdLike;
	}

	public void setOstCellIds(List<String> ostCellIds) {
		this.ostCellIds = ostCellIds;
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

	public void setOstColor(Integer ostColor) {
		this.ostColor = ostColor;
	}

	public void setOstColorGreaterThanOrEqual(Integer ostColorGreaterThanOrEqual) {
		this.ostColorGreaterThanOrEqual = ostColorGreaterThanOrEqual;
	}

	public void setOstColorLessThanOrEqual(Integer ostColorLessThanOrEqual) {
		this.ostColorLessThanOrEqual = ostColorLessThanOrEqual;
	}

	public void setOstColors(List<Integer> ostColors) {
		this.ostColors = ostColors;
	}

	public void setOstWay(Integer ostWay) {
		this.ostWay = ostWay;
	}

	public void setOstWayGreaterThanOrEqual(Integer ostWayGreaterThanOrEqual) {
		this.ostWayGreaterThanOrEqual = ostWayGreaterThanOrEqual;
	}

	public void setOstWayLessThanOrEqual(Integer ostWayLessThanOrEqual) {
		this.ostWayLessThanOrEqual = ostWayLessThanOrEqual;
	}

	public void setOstWays(List<Integer> ostWays) {
		this.ostWays = ostWays;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public void setRoleIdGreaterThanOrEqual(Integer roleIdGreaterThanOrEqual) {
		this.roleIdGreaterThanOrEqual = roleIdGreaterThanOrEqual;
	}

	public void setRoleIdLessThanOrEqual(Integer roleIdLessThanOrEqual) {
		this.roleIdLessThanOrEqual = roleIdLessThanOrEqual;
	}

	public void setRoleIds(List<Integer> roleIds) {
		this.roleIds = roleIds;
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

	public void setFname(String fname) {
		this.fname = fname;
	}

	public void setFnameLike(String fnameLike) {
		this.fnameLike = fnameLike;
	}

	public void setFnames(List<String> fnames) {
		this.fnames = fnames;
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

	public void setIsSubTable(String isSubTable) {
		this.isSubTable = isSubTable;
	}

	public void setIsSubTableLike(String isSubTableLike) {
		this.isSubTableLike = isSubTableLike;
	}

	public void setIsSubTables(List<String> isSubTables) {
		this.isSubTables = isSubTables;
	}

	public void setTableName2(String tableName2) {
		this.tableName2 = tableName2;
	}

	public void setTableName2Like(String tableName2Like) {
		this.tableName2Like = tableName2Like;
	}

	public void setTableName2s(List<String> tableName2s) {
		this.tableName2s = tableName2s;
	}

	public void setIntAutoinValue(Integer intAutoinValue) {
		this.intAutoinValue = intAutoinValue;
	}

	public void setIntAutoinValueGreaterThanOrEqual(
			Integer intAutoinValueGreaterThanOrEqual) {
		this.intAutoinValueGreaterThanOrEqual = intAutoinValueGreaterThanOrEqual;
	}

	public void setIntAutoinValueLessThanOrEqual(
			Integer intAutoinValueLessThanOrEqual) {
		this.intAutoinValueLessThanOrEqual = intAutoinValueLessThanOrEqual;
	}

	public void setIntAutoinValues(List<Integer> intAutoinValues) {
		this.intAutoinValues = intAutoinValues;
	}

	public void setIntSelfClick(Integer intSelfClick) {
		this.intSelfClick = intSelfClick;
	}

	public void setIntSelfClickGreaterThanOrEqual(
			Integer intSelfClickGreaterThanOrEqual) {
		this.intSelfClickGreaterThanOrEqual = intSelfClickGreaterThanOrEqual;
	}

	public void setIntSelfClickLessThanOrEqual(
			Integer intSelfClickLessThanOrEqual) {
		this.intSelfClickLessThanOrEqual = intSelfClickLessThanOrEqual;
	}

	public void setIntSelfClicks(List<Integer> intSelfClicks) {
		this.intSelfClicks = intSelfClicks;
	}

	public CellRepInfo2Query frmType(String frmType) {
		if (frmType == null) {
			throw new RuntimeException("frmType is null");
		}
		this.frmType = frmType;
		return this;
	}

	public CellRepInfo2Query frmTypeLike(String frmTypeLike) {
		if (frmTypeLike == null) {
			throw new RuntimeException("frmType is null");
		}
		this.frmTypeLike = frmTypeLike;
		return this;
	}

	public CellRepInfo2Query frmTypes(List<String> frmTypes) {
		if (frmTypes == null) {
			throw new RuntimeException("frmTypes is empty ");
		}
		this.frmTypes = frmTypes;
		return this;
	}

	public CellRepInfo2Query type(Integer type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public CellRepInfo2Query typeGreaterThanOrEqual(
			Integer typeGreaterThanOrEqual) {
		if (typeGreaterThanOrEqual == null) {
			throw new RuntimeException("type is null");
		}
		this.typeGreaterThanOrEqual = typeGreaterThanOrEqual;
		return this;
	}

	public CellRepInfo2Query typeLessThanOrEqual(Integer typeLessThanOrEqual) {
		if (typeLessThanOrEqual == null) {
			throw new RuntimeException("type is null");
		}
		this.typeLessThanOrEqual = typeLessThanOrEqual;
		return this;
	}

	public CellRepInfo2Query types(List<Integer> types) {
		if (types == null) {
			throw new RuntimeException("types is empty ");
		}
		this.types = types;
		return this;
	}

	public CellRepInfo2Query content(String content) {
		if (content == null) {
			throw new RuntimeException("content is null");
		}
		this.content = content;
		return this;
	}

	public CellRepInfo2Query contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public CellRepInfo2Query contents(List<String> contents) {
		if (contents == null) {
			throw new RuntimeException("contents is empty ");
		}
		this.contents = contents;
		return this;
	}

	public CellRepInfo2Query formula(String formula) {
		if (formula == null) {
			throw new RuntimeException("formula is null");
		}
		this.formula = formula;
		return this;
	}

	public CellRepInfo2Query formulaLike(String formulaLike) {
		if (formulaLike == null) {
			throw new RuntimeException("formula is null");
		}
		this.formulaLike = formulaLike;
		return this;
	}

	public CellRepInfo2Query formulas(List<String> formulas) {
		if (formulas == null) {
			throw new RuntimeException("formulas is empty ");
		}
		this.formulas = formulas;
		return this;
	}

	public CellRepInfo2Query ostTableName(String ostTableName) {
		if (ostTableName == null) {
			throw new RuntimeException("ostTableName is null");
		}
		this.ostTableName = ostTableName;
		return this;
	}

	public CellRepInfo2Query ostTableNameLike(String ostTableNameLike) {
		if (ostTableNameLike == null) {
			throw new RuntimeException("ostTableName is null");
		}
		this.ostTableNameLike = ostTableNameLike;
		return this;
	}

	public CellRepInfo2Query ostTableNames(List<String> ostTableNames) {
		if (ostTableNames == null) {
			throw new RuntimeException("ostTableNames is empty ");
		}
		this.ostTableNames = ostTableNames;
		return this;
	}

	public CellRepInfo2Query ostRow(Integer ostRow) {
		if (ostRow == null) {
			throw new RuntimeException("ostRow is null");
		}
		this.ostRow = ostRow;
		return this;
	}

	public CellRepInfo2Query ostRowGreaterThanOrEqual(
			Integer ostRowGreaterThanOrEqual) {
		if (ostRowGreaterThanOrEqual == null) {
			throw new RuntimeException("ostRow is null");
		}
		this.ostRowGreaterThanOrEqual = ostRowGreaterThanOrEqual;
		return this;
	}

	public CellRepInfo2Query ostRowLessThanOrEqual(Integer ostRowLessThanOrEqual) {
		if (ostRowLessThanOrEqual == null) {
			throw new RuntimeException("ostRow is null");
		}
		this.ostRowLessThanOrEqual = ostRowLessThanOrEqual;
		return this;
	}

	public CellRepInfo2Query ostRows(List<Integer> ostRows) {
		if (ostRows == null) {
			throw new RuntimeException("ostRows is empty ");
		}
		this.ostRows = ostRows;
		return this;
	}

	public CellRepInfo2Query ostCol(Integer ostCol) {
		if (ostCol == null) {
			throw new RuntimeException("ostCol is null");
		}
		this.ostCol = ostCol;
		return this;
	}

	public CellRepInfo2Query ostColGreaterThanOrEqual(
			Integer ostColGreaterThanOrEqual) {
		if (ostColGreaterThanOrEqual == null) {
			throw new RuntimeException("ostCol is null");
		}
		this.ostColGreaterThanOrEqual = ostColGreaterThanOrEqual;
		return this;
	}

	public CellRepInfo2Query ostColLessThanOrEqual(Integer ostColLessThanOrEqual) {
		if (ostColLessThanOrEqual == null) {
			throw new RuntimeException("ostCol is null");
		}
		this.ostColLessThanOrEqual = ostColLessThanOrEqual;
		return this;
	}

	public CellRepInfo2Query ostCols(List<Integer> ostCols) {
		if (ostCols == null) {
			throw new RuntimeException("ostCols is empty ");
		}
		this.ostCols = ostCols;
		return this;
	}

	public CellRepInfo2Query ostRowEnd(Integer ostRowEnd) {
		if (ostRowEnd == null) {
			throw new RuntimeException("ostRowEnd is null");
		}
		this.ostRowEnd = ostRowEnd;
		return this;
	}

	public CellRepInfo2Query ostRowEndGreaterThanOrEqual(
			Integer ostRowEndGreaterThanOrEqual) {
		if (ostRowEndGreaterThanOrEqual == null) {
			throw new RuntimeException("ostRowEnd is null");
		}
		this.ostRowEndGreaterThanOrEqual = ostRowEndGreaterThanOrEqual;
		return this;
	}

	public CellRepInfo2Query ostRowEndLessThanOrEqual(
			Integer ostRowEndLessThanOrEqual) {
		if (ostRowEndLessThanOrEqual == null) {
			throw new RuntimeException("ostRowEnd is null");
		}
		this.ostRowEndLessThanOrEqual = ostRowEndLessThanOrEqual;
		return this;
	}

	public CellRepInfo2Query ostRowEnds(List<Integer> ostRowEnds) {
		if (ostRowEnds == null) {
			throw new RuntimeException("ostRowEnds is empty ");
		}
		this.ostRowEnds = ostRowEnds;
		return this;
	}

	public CellRepInfo2Query ostColEnd(Integer ostColEnd) {
		if (ostColEnd == null) {
			throw new RuntimeException("ostColEnd is null");
		}
		this.ostColEnd = ostColEnd;
		return this;
	}

	public CellRepInfo2Query ostColEndGreaterThanOrEqual(
			Integer ostColEndGreaterThanOrEqual) {
		if (ostColEndGreaterThanOrEqual == null) {
			throw new RuntimeException("ostColEnd is null");
		}
		this.ostColEndGreaterThanOrEqual = ostColEndGreaterThanOrEqual;
		return this;
	}

	public CellRepInfo2Query ostColEndLessThanOrEqual(
			Integer ostColEndLessThanOrEqual) {
		if (ostColEndLessThanOrEqual == null) {
			throw new RuntimeException("ostColEnd is null");
		}
		this.ostColEndLessThanOrEqual = ostColEndLessThanOrEqual;
		return this;
	}

	public CellRepInfo2Query ostColEnds(List<Integer> ostColEnds) {
		if (ostColEnds == null) {
			throw new RuntimeException("ostColEnds is empty ");
		}
		this.ostColEnds = ostColEnds;
		return this;
	}

	public CellRepInfo2Query ostCellId(String ostCellId) {
		if (ostCellId == null) {
			throw new RuntimeException("ostCellId is null");
		}
		this.ostCellId = ostCellId;
		return this;
	}

	public CellRepInfo2Query ostCellIdLike(String ostCellIdLike) {
		if (ostCellIdLike == null) {
			throw new RuntimeException("ostCellId is null");
		}
		this.ostCellIdLike = ostCellIdLike;
		return this;
	}

	public CellRepInfo2Query ostCellIds(List<String> ostCellIds) {
		if (ostCellIds == null) {
			throw new RuntimeException("ostCellIds is empty ");
		}
		this.ostCellIds = ostCellIds;
		return this;
	}

	public CellRepInfo2Query fileDotFileId(String fileDotFileId) {
		if (fileDotFileId == null) {
			throw new RuntimeException("fileDotFileId is null");
		}
		this.fileDotFileId = fileDotFileId;
		return this;
	}

	public CellRepInfo2Query fileDotFileIdLike(String fileDotFileIdLike) {
		if (fileDotFileIdLike == null) {
			throw new RuntimeException("fileDotFileId is null");
		}
		this.fileDotFileIdLike = fileDotFileIdLike;
		return this;
	}

	public CellRepInfo2Query fileDotFileIds(List<String> fileDotFileIds) {
		if (fileDotFileIds == null) {
			throw new RuntimeException("fileDotFileIds is empty ");
		}
		this.fileDotFileIds = fileDotFileIds;
		return this;
	}

	public CellRepInfo2Query ostColor(Integer ostColor) {
		if (ostColor == null) {
			throw new RuntimeException("ostColor is null");
		}
		this.ostColor = ostColor;
		return this;
	}

	public CellRepInfo2Query ostColorGreaterThanOrEqual(
			Integer ostColorGreaterThanOrEqual) {
		if (ostColorGreaterThanOrEqual == null) {
			throw new RuntimeException("ostColor is null");
		}
		this.ostColorGreaterThanOrEqual = ostColorGreaterThanOrEqual;
		return this;
	}

	public CellRepInfo2Query ostColorLessThanOrEqual(
			Integer ostColorLessThanOrEqual) {
		if (ostColorLessThanOrEqual == null) {
			throw new RuntimeException("ostColor is null");
		}
		this.ostColorLessThanOrEqual = ostColorLessThanOrEqual;
		return this;
	}

	public CellRepInfo2Query ostColors(List<Integer> ostColors) {
		if (ostColors == null) {
			throw new RuntimeException("ostColors is empty ");
		}
		this.ostColors = ostColors;
		return this;
	}

	public CellRepInfo2Query ostWay(Integer ostWay) {
		if (ostWay == null) {
			throw new RuntimeException("ostWay is null");
		}
		this.ostWay = ostWay;
		return this;
	}

	public CellRepInfo2Query ostWayGreaterThanOrEqual(
			Integer ostWayGreaterThanOrEqual) {
		if (ostWayGreaterThanOrEqual == null) {
			throw new RuntimeException("ostWay is null");
		}
		this.ostWayGreaterThanOrEqual = ostWayGreaterThanOrEqual;
		return this;
	}

	public CellRepInfo2Query ostWayLessThanOrEqual(Integer ostWayLessThanOrEqual) {
		if (ostWayLessThanOrEqual == null) {
			throw new RuntimeException("ostWay is null");
		}
		this.ostWayLessThanOrEqual = ostWayLessThanOrEqual;
		return this;
	}

	public CellRepInfo2Query ostWays(List<Integer> ostWays) {
		if (ostWays == null) {
			throw new RuntimeException("ostWays is empty ");
		}
		this.ostWays = ostWays;
		return this;
	}

	public CellRepInfo2Query roleId(Integer roleId) {
		if (roleId == null) {
			throw new RuntimeException("roleId is null");
		}
		this.roleId = roleId;
		return this;
	}

	public CellRepInfo2Query roleIdGreaterThanOrEqual(
			Integer roleIdGreaterThanOrEqual) {
		if (roleIdGreaterThanOrEqual == null) {
			throw new RuntimeException("roleId is null");
		}
		this.roleIdGreaterThanOrEqual = roleIdGreaterThanOrEqual;
		return this;
	}

	public CellRepInfo2Query roleIdLessThanOrEqual(Integer roleIdLessThanOrEqual) {
		if (roleIdLessThanOrEqual == null) {
			throw new RuntimeException("roleId is null");
		}
		this.roleIdLessThanOrEqual = roleIdLessThanOrEqual;
		return this;
	}

	public CellRepInfo2Query roleIds(List<Integer> roleIds) {
		if (roleIds == null) {
			throw new RuntimeException("roleIds is empty ");
		}
		this.roleIds = roleIds;
		return this;
	}

	public CellRepInfo2Query tableName(String tableName) {
		if (tableName == null) {
			throw new RuntimeException("tableName is null");
		}
		this.tableName = tableName;
		return this;
	}

	public CellRepInfo2Query tableNameLike(String tableNameLike) {
		if (tableNameLike == null) {
			throw new RuntimeException("tableName is null");
		}
		this.tableNameLike = tableNameLike;
		return this;
	}

	public CellRepInfo2Query tableNames(List<String> tableNames) {
		if (tableNames == null) {
			throw new RuntimeException("tableNames is empty ");
		}
		this.tableNames = tableNames;
		return this;
	}

	public CellRepInfo2Query fname(String fname) {
		if (fname == null) {
			throw new RuntimeException("fname is null");
		}
		this.fname = fname;
		return this;
	}

	public CellRepInfo2Query fnameLike(String fnameLike) {
		if (fnameLike == null) {
			throw new RuntimeException("fname is null");
		}
		this.fnameLike = fnameLike;
		return this;
	}

	public CellRepInfo2Query fnames(List<String> fnames) {
		if (fnames == null) {
			throw new RuntimeException("fnames is empty ");
		}
		this.fnames = fnames;
		return this;
	}

	public CellRepInfo2Query dname(String dname) {
		if (dname == null) {
			throw new RuntimeException("dname is null");
		}
		this.dname = dname;
		return this;
	}

	public CellRepInfo2Query dnameLike(String dnameLike) {
		if (dnameLike == null) {
			throw new RuntimeException("dname is null");
		}
		this.dnameLike = dnameLike;
		return this;
	}

	public CellRepInfo2Query dnames(List<String> dnames) {
		if (dnames == null) {
			throw new RuntimeException("dnames is empty ");
		}
		this.dnames = dnames;
		return this;
	}

	public CellRepInfo2Query isSubTable(String isSubTable) {
		if (isSubTable == null) {
			throw new RuntimeException("isSubTable is null");
		}
		this.isSubTable = isSubTable;
		return this;
	}

	public CellRepInfo2Query isSubTableLike(String isSubTableLike) {
		if (isSubTableLike == null) {
			throw new RuntimeException("isSubTable is null");
		}
		this.isSubTableLike = isSubTableLike;
		return this;
	}

	public CellRepInfo2Query isSubTables(List<String> isSubTables) {
		if (isSubTables == null) {
			throw new RuntimeException("isSubTables is empty ");
		}
		this.isSubTables = isSubTables;
		return this;
	}

	public CellRepInfo2Query tableName2(String tableName2) {
		if (tableName2 == null) {
			throw new RuntimeException("tableName2 is null");
		}
		this.tableName2 = tableName2;
		return this;
	}

	public CellRepInfo2Query tableName2Like(String tableName2Like) {
		if (tableName2Like == null) {
			throw new RuntimeException("tableName2 is null");
		}
		this.tableName2Like = tableName2Like;
		return this;
	}

	public CellRepInfo2Query tableName2s(List<String> tableName2s) {
		if (tableName2s == null) {
			throw new RuntimeException("tableName2s is empty ");
		}
		this.tableName2s = tableName2s;
		return this;
	}

	public CellRepInfo2Query intAutoinValue(Integer intAutoinValue) {
		if (intAutoinValue == null) {
			throw new RuntimeException("intAutoinValue is null");
		}
		this.intAutoinValue = intAutoinValue;
		return this;
	}

	public CellRepInfo2Query intAutoinValueGreaterThanOrEqual(
			Integer intAutoinValueGreaterThanOrEqual) {
		if (intAutoinValueGreaterThanOrEqual == null) {
			throw new RuntimeException("intAutoinValue is null");
		}
		this.intAutoinValueGreaterThanOrEqual = intAutoinValueGreaterThanOrEqual;
		return this;
	}

	public CellRepInfo2Query intAutoinValueLessThanOrEqual(
			Integer intAutoinValueLessThanOrEqual) {
		if (intAutoinValueLessThanOrEqual == null) {
			throw new RuntimeException("intAutoinValue is null");
		}
		this.intAutoinValueLessThanOrEqual = intAutoinValueLessThanOrEqual;
		return this;
	}

	public CellRepInfo2Query intAutoinValues(List<Integer> intAutoinValues) {
		if (intAutoinValues == null) {
			throw new RuntimeException("intAutoinValues is empty ");
		}
		this.intAutoinValues = intAutoinValues;
		return this;
	}

	public CellRepInfo2Query intSelfClick(Integer intSelfClick) {
		if (intSelfClick == null) {
			throw new RuntimeException("intSelfClick is null");
		}
		this.intSelfClick = intSelfClick;
		return this;
	}

	public CellRepInfo2Query intSelfClickGreaterThanOrEqual(
			Integer intSelfClickGreaterThanOrEqual) {
		if (intSelfClickGreaterThanOrEqual == null) {
			throw new RuntimeException("intSelfClick is null");
		}
		this.intSelfClickGreaterThanOrEqual = intSelfClickGreaterThanOrEqual;
		return this;
	}

	public CellRepInfo2Query intSelfClickLessThanOrEqual(
			Integer intSelfClickLessThanOrEqual) {
		if (intSelfClickLessThanOrEqual == null) {
			throw new RuntimeException("intSelfClick is null");
		}
		this.intSelfClickLessThanOrEqual = intSelfClickLessThanOrEqual;
		return this;
	}

	public CellRepInfo2Query intSelfClicks(List<Integer> intSelfClicks) {
		if (intSelfClicks == null) {
			throw new RuntimeException("intSelfClicks is empty ");
		}
		this.intSelfClicks = intSelfClicks;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("frmType".equals(sortColumn)) {
				orderBy = "E.FRMTYPE" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE" + a_x;
			}

			if ("content".equals(sortColumn)) {
				orderBy = "E.CONTENT" + a_x;
			}

			if ("formula".equals(sortColumn)) {
				orderBy = "E.FORMULA" + a_x;
			}

			if ("ostTableName".equals(sortColumn)) {
				orderBy = "E.OST_TABLENAME" + a_x;
			}

			if ("ostRow".equals(sortColumn)) {
				orderBy = "E.OST_ROW" + a_x;
			}

			if ("ostCol".equals(sortColumn)) {
				orderBy = "E.OST_COL" + a_x;
			}

			if ("ostRowEnd".equals(sortColumn)) {
				orderBy = "E.OST_ROWEND" + a_x;
			}

			if ("ostColEnd".equals(sortColumn)) {
				orderBy = "E.OST_COLEND" + a_x;
			}

			if ("ostCellId".equals(sortColumn)) {
				orderBy = "E.OST_CELLID" + a_x;
			}

			if ("fileDotFileId".equals(sortColumn)) {
				orderBy = "E.FILEDOT_FILEID" + a_x;
			}

			if ("ostColor".equals(sortColumn)) {
				orderBy = "E.OST_COLOR" + a_x;
			}

			if ("ostWay".equals(sortColumn)) {
				orderBy = "E.OST_WAY" + a_x;
			}

			if ("roleId".equals(sortColumn)) {
				orderBy = "E.ROLE_ID" + a_x;
			}

			if ("tableName".equals(sortColumn)) {
				orderBy = "E.TABLENAME" + a_x;
			}

			if ("fname".equals(sortColumn)) {
				orderBy = "E.FNAME" + a_x;
			}

			if ("dname".equals(sortColumn)) {
				orderBy = "E.DNAME" + a_x;
			}

			if ("isSubTable".equals(sortColumn)) {
				orderBy = "E.ISSUBTABLE" + a_x;
			}

			if ("tableName2".equals(sortColumn)) {
				orderBy = "E.TABLENAME2" + a_x;
			}

			if ("intAutoinValue".equals(sortColumn)) {
				orderBy = "E.INTAUTOINVALUE" + a_x;
			}

			if ("intSelfClick".equals(sortColumn)) {
				orderBy = "E.INTSELFCLICK" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("frmType", "FRMTYPE");
		addColumn("type", "TYPE");
		addColumn("content", "CONTENT");
		addColumn("formula", "FORMULA");
		addColumn("ostTableName", "OST_TABLENAME");
		addColumn("ostRow", "OST_ROW");
		addColumn("ostCol", "OST_COL");
		addColumn("ostRowEnd", "OST_ROWEND");
		addColumn("ostColEnd", "OST_COLEND");
		addColumn("ostCellId", "OST_CELLID");
		addColumn("fileDotFileId", "FILEDOT_FILEID");
		addColumn("ostColor", "OST_COLOR");
		addColumn("ostWay", "OST_WAY");
		addColumn("roleId", "ROLE_ID");
		addColumn("tableName", "TABLENAME");
		addColumn("fname", "FNAME");
		addColumn("dname", "DNAME");
		addColumn("isSubTable", "ISSUBTABLE");
		addColumn("tableName2", "TABLENAME2");
		addColumn("intAutoinValue", "INTAUTOINVALUE");
		addColumn("intSelfClick", "INTSELFCLICK");
	}

}