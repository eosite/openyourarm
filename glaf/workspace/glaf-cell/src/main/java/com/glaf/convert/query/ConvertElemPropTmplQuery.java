package com.glaf.convert.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ConvertElemPropTmplQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> elemPropIds;
	protected Collection<String> appActorIds;
	protected Long cvtId;
	protected Long cvtIdGreaterThanOrEqual;
	protected Long cvtIdLessThanOrEqual;
	protected List<Long> cvtIds;
	protected Integer rowIndex;
	protected Integer rowIndexGreaterThanOrEqual;
	protected Integer rowIndexLessThanOrEqual;
	protected List<Integer> rowIndexs;
	protected Integer colIndex;
	protected Integer colIndexGreaterThanOrEqual;
	protected Integer colIndexLessThanOrEqual;
	protected List<Integer> colIndexs;
	protected String cellPropVal;
	protected String cellPropValLike;
	protected List<String> cellPropVals;
	protected String cellProp;
	protected String cellPropLike;
	protected List<String> cellProps;
	protected Date createDatetimeGreaterThanOrEqual;
	protected Date createDatetimeLessThanOrEqual;
	protected Date modifyDatetimeGreaterThanOrEqual;
	protected Date modifyDatetimeLessThanOrEqual;

	public ConvertElemPropTmplQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public Long getCvtId() {
		return cvtId;
	}

	public Long getCvtIdGreaterThanOrEqual() {
		return cvtIdGreaterThanOrEqual;
	}

	public Long getCvtIdLessThanOrEqual() {
		return cvtIdLessThanOrEqual;
	}

	public List<Long> getCvtIds() {
		return cvtIds;
	}

	public Integer getRowIndex() {
		return rowIndex;
	}

	public Integer getRowIndexGreaterThanOrEqual() {
		return rowIndexGreaterThanOrEqual;
	}

	public Integer getRowIndexLessThanOrEqual() {
		return rowIndexLessThanOrEqual;
	}

	public List<Integer> getRowIndexs() {
		return rowIndexs;
	}

	public Integer getColIndex() {
		return colIndex;
	}

	public Integer getColIndexGreaterThanOrEqual() {
		return colIndexGreaterThanOrEqual;
	}

	public Integer getColIndexLessThanOrEqual() {
		return colIndexLessThanOrEqual;
	}

	public List<Integer> getColIndexs() {
		return colIndexs;
	}

	public String getCellPropVal() {
		return cellPropVal;
	}

	public String getCellPropValLike() {
		if (cellPropValLike != null && cellPropValLike.trim().length() > 0) {
			if (!cellPropValLike.startsWith("%")) {
				cellPropValLike = "%" + cellPropValLike;
			}
			if (!cellPropValLike.endsWith("%")) {
				cellPropValLike = cellPropValLike + "%";
			}
		}
		return cellPropValLike;
	}

	public List<String> getCellPropVals() {
		return cellPropVals;
	}

	public String getCellProp() {
		return cellProp;
	}

	public String getCellPropLike() {
		if (cellPropLike != null && cellPropLike.trim().length() > 0) {
			if (!cellPropLike.startsWith("%")) {
				cellPropLike = "%" + cellPropLike;
			}
			if (!cellPropLike.endsWith("%")) {
				cellPropLike = cellPropLike + "%";
			}
		}
		return cellPropLike;
	}

	public List<String> getCellProps() {
		return cellProps;
	}

	public Date getCreateDatetimeGreaterThanOrEqual() {
		return createDatetimeGreaterThanOrEqual;
	}

	public Date getCreateDatetimeLessThanOrEqual() {
		return createDatetimeLessThanOrEqual;
	}

	public Date getModifyDatetimeGreaterThanOrEqual() {
		return modifyDatetimeGreaterThanOrEqual;
	}

	public Date getModifyDatetimeLessThanOrEqual() {
		return modifyDatetimeLessThanOrEqual;
	}

	public void setCvtId(Long cvtId) {
		this.cvtId = cvtId;
	}

	public void setCvtIdGreaterThanOrEqual(Long cvtIdGreaterThanOrEqual) {
		this.cvtIdGreaterThanOrEqual = cvtIdGreaterThanOrEqual;
	}

	public void setCvtIdLessThanOrEqual(Long cvtIdLessThanOrEqual) {
		this.cvtIdLessThanOrEqual = cvtIdLessThanOrEqual;
	}

	public void setCvtIds(List<Long> cvtIds) {
		this.cvtIds = cvtIds;
	}

	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}

	public void setRowIndexGreaterThanOrEqual(Integer rowIndexGreaterThanOrEqual) {
		this.rowIndexGreaterThanOrEqual = rowIndexGreaterThanOrEqual;
	}

	public void setRowIndexLessThanOrEqual(Integer rowIndexLessThanOrEqual) {
		this.rowIndexLessThanOrEqual = rowIndexLessThanOrEqual;
	}

	public void setRowIndexs(List<Integer> rowIndexs) {
		this.rowIndexs = rowIndexs;
	}

	public void setColIndex(Integer colIndex) {
		this.colIndex = colIndex;
	}

	public void setColIndexGreaterThanOrEqual(Integer colIndexGreaterThanOrEqual) {
		this.colIndexGreaterThanOrEqual = colIndexGreaterThanOrEqual;
	}

	public void setColIndexLessThanOrEqual(Integer colIndexLessThanOrEqual) {
		this.colIndexLessThanOrEqual = colIndexLessThanOrEqual;
	}

	public void setColIndexs(List<Integer> colIndexs) {
		this.colIndexs = colIndexs;
	}

	public void setCellPropVal(String cellPropVal) {
		this.cellPropVal = cellPropVal;
	}

	public void setCellPropValLike(String cellPropValLike) {
		this.cellPropValLike = cellPropValLike;
	}

	public void setCellPropVals(List<String> cellPropVals) {
		this.cellPropVals = cellPropVals;
	}

	public void setCellProp(String cellProp) {
		this.cellProp = cellProp;
	}

	public void setCellPropLike(String cellPropLike) {
		this.cellPropLike = cellPropLike;
	}

	public void setCellProps(List<String> cellProps) {
		this.cellProps = cellProps;
	}

	public void setCreateDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual) {
		this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
	}

	public void setCreateDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual) {
		this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
	}

	public void setModifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual) {
		this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
	}

	public void setModifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual) {
		this.modifyDatetimeLessThanOrEqual = modifyDatetimeLessThanOrEqual;
	}

	public ConvertElemPropTmplQuery cvtId(Long cvtId) {
		if (cvtId == null) {
			throw new RuntimeException("cvtId is null");
		}
		this.cvtId = cvtId;
		return this;
	}

	public ConvertElemPropTmplQuery cvtIdGreaterThanOrEqual(Long cvtIdGreaterThanOrEqual) {
		if (cvtIdGreaterThanOrEqual == null) {
			throw new RuntimeException("cvtId is null");
		}
		this.cvtIdGreaterThanOrEqual = cvtIdGreaterThanOrEqual;
		return this;
	}

	public ConvertElemPropTmplQuery cvtIdLessThanOrEqual(Long cvtIdLessThanOrEqual) {
		if (cvtIdLessThanOrEqual == null) {
			throw new RuntimeException("cvtId is null");
		}
		this.cvtIdLessThanOrEqual = cvtIdLessThanOrEqual;
		return this;
	}

	public ConvertElemPropTmplQuery cvtIds(List<Long> cvtIds) {
		if (cvtIds == null) {
			throw new RuntimeException("cvtIds is empty ");
		}
		this.cvtIds = cvtIds;
		return this;
	}

	public ConvertElemPropTmplQuery rowIndex(Integer rowIndex) {
		if (rowIndex == null) {
			throw new RuntimeException("rowIndex is null");
		}
		this.rowIndex = rowIndex;
		return this;
	}

	public ConvertElemPropTmplQuery rowIndexGreaterThanOrEqual(Integer rowIndexGreaterThanOrEqual) {
		if (rowIndexGreaterThanOrEqual == null) {
			throw new RuntimeException("rowIndex is null");
		}
		this.rowIndexGreaterThanOrEqual = rowIndexGreaterThanOrEqual;
		return this;
	}

	public ConvertElemPropTmplQuery rowIndexLessThanOrEqual(Integer rowIndexLessThanOrEqual) {
		if (rowIndexLessThanOrEqual == null) {
			throw new RuntimeException("rowIndex is null");
		}
		this.rowIndexLessThanOrEqual = rowIndexLessThanOrEqual;
		return this;
	}

	public ConvertElemPropTmplQuery rowIndexs(List<Integer> rowIndexs) {
		if (rowIndexs == null) {
			throw new RuntimeException("rowIndexs is empty ");
		}
		this.rowIndexs = rowIndexs;
		return this;
	}

	public ConvertElemPropTmplQuery colIndex(Integer colIndex) {
		if (colIndex == null) {
			throw new RuntimeException("colIndex is null");
		}
		this.colIndex = colIndex;
		return this;
	}

	public ConvertElemPropTmplQuery colIndexGreaterThanOrEqual(Integer colIndexGreaterThanOrEqual) {
		if (colIndexGreaterThanOrEqual == null) {
			throw new RuntimeException("colIndex is null");
		}
		this.colIndexGreaterThanOrEqual = colIndexGreaterThanOrEqual;
		return this;
	}

	public ConvertElemPropTmplQuery colIndexLessThanOrEqual(Integer colIndexLessThanOrEqual) {
		if (colIndexLessThanOrEqual == null) {
			throw new RuntimeException("colIndex is null");
		}
		this.colIndexLessThanOrEqual = colIndexLessThanOrEqual;
		return this;
	}

	public ConvertElemPropTmplQuery colIndexs(List<Integer> colIndexs) {
		if (colIndexs == null) {
			throw new RuntimeException("colIndexs is empty ");
		}
		this.colIndexs = colIndexs;
		return this;
	}

	public ConvertElemPropTmplQuery cellPropVal(String cellPropVal) {
		if (cellPropVal == null) {
			throw new RuntimeException("cellPropVal is null");
		}
		this.cellPropVal = cellPropVal;
		return this;
	}

	public ConvertElemPropTmplQuery cellPropValLike(String cellPropValLike) {
		if (cellPropValLike == null) {
			throw new RuntimeException("cellPropVal is null");
		}
		this.cellPropValLike = cellPropValLike;
		return this;
	}

	public ConvertElemPropTmplQuery cellPropVals(List<String> cellPropVals) {
		if (cellPropVals == null) {
			throw new RuntimeException("cellPropVals is empty ");
		}
		this.cellPropVals = cellPropVals;
		return this;
	}

	public ConvertElemPropTmplQuery cellProp(String cellProp) {
		if (cellProp == null) {
			throw new RuntimeException("cellProp is null");
		}
		this.cellProp = cellProp;
		return this;
	}

	public ConvertElemPropTmplQuery cellPropLike(String cellPropLike) {
		if (cellPropLike == null) {
			throw new RuntimeException("cellProp is null");
		}
		this.cellPropLike = cellPropLike;
		return this;
	}

	public ConvertElemPropTmplQuery cellProps(List<String> cellProps) {
		if (cellProps == null) {
			throw new RuntimeException("cellProps is empty ");
		}
		this.cellProps = cellProps;
		return this;
	}

	public ConvertElemPropTmplQuery createDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual) {
		if (createDatetimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createDatetime is null");
		}
		this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
		return this;
	}

	public ConvertElemPropTmplQuery createDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual) {
		if (createDatetimeLessThanOrEqual == null) {
			throw new RuntimeException("createDatetime is null");
		}
		this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
		return this;
	}

	public ConvertElemPropTmplQuery modifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual) {
		if (modifyDatetimeGreaterThanOrEqual == null) {
			throw new RuntimeException("modifyDatetime is null");
		}
		this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
		return this;
	}

	public ConvertElemPropTmplQuery modifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual) {
		if (modifyDatetimeLessThanOrEqual == null) {
			throw new RuntimeException("modifyDatetime is null");
		}
		this.modifyDatetimeLessThanOrEqual = modifyDatetimeLessThanOrEqual;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("cvtId".equals(sortColumn)) {
				orderBy = "E.CVT_ID_" + a_x;
			}

			if ("rowIndex".equals(sortColumn)) {
				orderBy = "E.ROW_INDEX_" + a_x;
			}

			if ("colIndex".equals(sortColumn)) {
				orderBy = "E.COL_INDEX_" + a_x;
			}

			if ("cellPropVal".equals(sortColumn)) {
				orderBy = "E.CELL_PROP_VAL_" + a_x;
			}

			if ("cellProp".equals(sortColumn)) {
				orderBy = "E.CELL_PROP_" + a_x;
			}

			if ("createDatetime".equals(sortColumn)) {
				orderBy = "E.CREATE_DATETIME_" + a_x;
			}

			if ("modifyDatetime".equals(sortColumn)) {
				orderBy = "E.MODIFY_DATETIME_" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("elemPropId", "ELEMPROP_ID_");
		addColumn("cvtId", "CVT_ID_");
		addColumn("rowIndex", "ROW_INDEX_");
		addColumn("colIndex", "COL_INDEX_");
		addColumn("cellPropVal", "CELL_PROP_VAL_");
		addColumn("cellProp", "CELL_PROP_");
		addColumn("createDatetime", "CREATE_DATETIME_");
		addColumn("modifyDatetime", "MODIFY_DATETIME_");
	}

}