package com.glaf.dep.report.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DepReportCellPosQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
  	protected Long cellId;
  	protected Long cellIdGreaterThanOrEqual;
  	protected Long cellIdLessThanOrEqual;
  	protected List<Long> cellIds;
  	protected Integer rowIndex;
  	protected Integer rowIndexGreaterThanOrEqual;
  	protected Integer rowIndexLessThanOrEqual;
  	protected List<Integer> rowIndexs;
  	protected Integer colIndex;
  	protected Integer colIndexGreaterThanOrEqual;
  	protected Integer colIndexLessThanOrEqual;
  	protected List<Integer> colIndexs;
  	protected String varFalg;
  	protected String varFalgLike;
  	protected List<String> varFalgs;
  	protected String direction;
  	protected String directionLike;
  	protected List<String> directions;
  	protected Integer endRowIndex;
  	protected Integer endRowIndexGreaterThanOrEqual;
  	protected Integer endRowIndexLessThanOrEqual;
  	protected List<Integer> endRowIndexs;
  	protected Integer endColIndex;
  	protected Integer endColIndexGreaterThanOrEqual;
  	protected Integer endColIndexLessThanOrEqual;
  	protected List<Integer> endColIndexs;

    public DepReportCellPosQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public Long getCellId(){
        return cellId;
    }

    public Long getCellIdGreaterThanOrEqual(){
        return cellIdGreaterThanOrEqual;
    }

    public Long getCellIdLessThanOrEqual(){
	return cellIdLessThanOrEqual;
    }

    public List<Long> getCellIds(){
	return cellIds;
    }

    public Integer getRowIndex(){
        return rowIndex;
    }

    public Integer getRowIndexGreaterThanOrEqual(){
        return rowIndexGreaterThanOrEqual;
    }

    public Integer getRowIndexLessThanOrEqual(){
	return rowIndexLessThanOrEqual;
    }

    public List<Integer> getRowIndexs(){
	return rowIndexs;
    }

    public Integer getColIndex(){
        return colIndex;
    }

    public Integer getColIndexGreaterThanOrEqual(){
        return colIndexGreaterThanOrEqual;
    }

    public Integer getColIndexLessThanOrEqual(){
	return colIndexLessThanOrEqual;
    }

    public List<Integer> getColIndexs(){
	return colIndexs;
    }

    public String getVarFalg(){
        return varFalg;
    }

    public String getVarFalgLike(){
	    if (varFalgLike != null && varFalgLike.trim().length() > 0) {
		if (!varFalgLike.startsWith("%")) {
		    varFalgLike = "%" + varFalgLike;
		}
		if (!varFalgLike.endsWith("%")) {
		   varFalgLike = varFalgLike + "%";
		}
	    }
	return varFalgLike;
    }

    public List<String> getVarFalgs(){
	return varFalgs;
    }


    public String getDirection(){
        return direction;
    }

    public String getDirectionLike(){
	    if (directionLike != null && directionLike.trim().length() > 0) {
		if (!directionLike.startsWith("%")) {
		    directionLike = "%" + directionLike;
		}
		if (!directionLike.endsWith("%")) {
		   directionLike = directionLike + "%";
		}
	    }
	return directionLike;
    }

    public List<String> getDirections(){
	return directions;
    }


    public Integer getEndRowIndex(){
        return endRowIndex;
    }

    public Integer getEndRowIndexGreaterThanOrEqual(){
        return endRowIndexGreaterThanOrEqual;
    }

    public Integer getEndRowIndexLessThanOrEqual(){
	return endRowIndexLessThanOrEqual;
    }

    public List<Integer> getEndRowIndexs(){
	return endRowIndexs;
    }

    public Integer getEndColIndex(){
        return endColIndex;
    }

    public Integer getEndColIndexGreaterThanOrEqual(){
        return endColIndexGreaterThanOrEqual;
    }

    public Integer getEndColIndexLessThanOrEqual(){
	return endColIndexLessThanOrEqual;
    }

    public List<Integer> getEndColIndexs(){
	return endColIndexs;
    }

 

    public void setCellId(Long cellId){
        this.cellId = cellId;
    }

    public void setCellIdGreaterThanOrEqual(Long cellIdGreaterThanOrEqual){
        this.cellIdGreaterThanOrEqual = cellIdGreaterThanOrEqual;
    }

    public void setCellIdLessThanOrEqual(Long cellIdLessThanOrEqual){
	this.cellIdLessThanOrEqual = cellIdLessThanOrEqual;
    }

    public void setCellIds(List<Long> cellIds){
        this.cellIds = cellIds;
    }


    public void setRowIndex(Integer rowIndex){
        this.rowIndex = rowIndex;
    }

    public void setRowIndexGreaterThanOrEqual(Integer rowIndexGreaterThanOrEqual){
        this.rowIndexGreaterThanOrEqual = rowIndexGreaterThanOrEqual;
    }

    public void setRowIndexLessThanOrEqual(Integer rowIndexLessThanOrEqual){
	this.rowIndexLessThanOrEqual = rowIndexLessThanOrEqual;
    }

    public void setRowIndexs(List<Integer> rowIndexs){
        this.rowIndexs = rowIndexs;
    }


    public void setColIndex(Integer colIndex){
        this.colIndex = colIndex;
    }

    public void setColIndexGreaterThanOrEqual(Integer colIndexGreaterThanOrEqual){
        this.colIndexGreaterThanOrEqual = colIndexGreaterThanOrEqual;
    }

    public void setColIndexLessThanOrEqual(Integer colIndexLessThanOrEqual){
	this.colIndexLessThanOrEqual = colIndexLessThanOrEqual;
    }

    public void setColIndexs(List<Integer> colIndexs){
        this.colIndexs = colIndexs;
    }


    public void setVarFalg(String varFalg){
        this.varFalg = varFalg;
    }

    public void setVarFalgLike( String varFalgLike){
	this.varFalgLike = varFalgLike;
    }

    public void setVarFalgs(List<String> varFalgs){
        this.varFalgs = varFalgs;
    }


    public void setDirection(String direction){
        this.direction = direction;
    }

    public void setDirectionLike( String directionLike){
	this.directionLike = directionLike;
    }

    public void setDirections(List<String> directions){
        this.directions = directions;
    }


    public void setEndRowIndex(Integer endRowIndex){
        this.endRowIndex = endRowIndex;
    }

    public void setEndRowIndexGreaterThanOrEqual(Integer endRowIndexGreaterThanOrEqual){
        this.endRowIndexGreaterThanOrEqual = endRowIndexGreaterThanOrEqual;
    }

    public void setEndRowIndexLessThanOrEqual(Integer endRowIndexLessThanOrEqual){
	this.endRowIndexLessThanOrEqual = endRowIndexLessThanOrEqual;
    }

    public void setEndRowIndexs(List<Integer> endRowIndexs){
        this.endRowIndexs = endRowIndexs;
    }


    public void setEndColIndex(Integer endColIndex){
        this.endColIndex = endColIndex;
    }

    public void setEndColIndexGreaterThanOrEqual(Integer endColIndexGreaterThanOrEqual){
        this.endColIndexGreaterThanOrEqual = endColIndexGreaterThanOrEqual;
    }

    public void setEndColIndexLessThanOrEqual(Integer endColIndexLessThanOrEqual){
	this.endColIndexLessThanOrEqual = endColIndexLessThanOrEqual;
    }

    public void setEndColIndexs(List<Integer> endColIndexs){
        this.endColIndexs = endColIndexs;
    }




    public DepReportCellPosQuery cellId(Long cellId){
	if (cellId == null) {
            throw new RuntimeException("cellId is null");
        }         
	this.cellId = cellId;
	return this;
    }

    public DepReportCellPosQuery cellIdGreaterThanOrEqual(Long cellIdGreaterThanOrEqual){
	if (cellIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("cellId is null");
        }         
	this.cellIdGreaterThanOrEqual = cellIdGreaterThanOrEqual;
        return this;
    }

    public DepReportCellPosQuery cellIdLessThanOrEqual(Long cellIdLessThanOrEqual){
        if (cellIdLessThanOrEqual == null) {
            throw new RuntimeException("cellId is null");
        }
        this.cellIdLessThanOrEqual = cellIdLessThanOrEqual;
        return this;
    }

    public DepReportCellPosQuery cellIds(List<Long> cellIds){
        if (cellIds == null) {
            throw new RuntimeException("cellIds is empty ");
        }
        this.cellIds = cellIds;
        return this;
    }


    public DepReportCellPosQuery rowIndex(Integer rowIndex){
	if (rowIndex == null) {
            throw new RuntimeException("rowIndex is null");
        }         
	this.rowIndex = rowIndex;
	return this;
    }

    public DepReportCellPosQuery rowIndexGreaterThanOrEqual(Integer rowIndexGreaterThanOrEqual){
	if (rowIndexGreaterThanOrEqual == null) {
	    throw new RuntimeException("rowIndex is null");
        }         
	this.rowIndexGreaterThanOrEqual = rowIndexGreaterThanOrEqual;
        return this;
    }

    public DepReportCellPosQuery rowIndexLessThanOrEqual(Integer rowIndexLessThanOrEqual){
        if (rowIndexLessThanOrEqual == null) {
            throw new RuntimeException("rowIndex is null");
        }
        this.rowIndexLessThanOrEqual = rowIndexLessThanOrEqual;
        return this;
    }

    public DepReportCellPosQuery rowIndexs(List<Integer> rowIndexs){
        if (rowIndexs == null) {
            throw new RuntimeException("rowIndexs is empty ");
        }
        this.rowIndexs = rowIndexs;
        return this;
    }


    public DepReportCellPosQuery colIndex(Integer colIndex){
	if (colIndex == null) {
            throw new RuntimeException("colIndex is null");
        }         
	this.colIndex = colIndex;
	return this;
    }

    public DepReportCellPosQuery colIndexGreaterThanOrEqual(Integer colIndexGreaterThanOrEqual){
	if (colIndexGreaterThanOrEqual == null) {
	    throw new RuntimeException("colIndex is null");
        }         
	this.colIndexGreaterThanOrEqual = colIndexGreaterThanOrEqual;
        return this;
    }

    public DepReportCellPosQuery colIndexLessThanOrEqual(Integer colIndexLessThanOrEqual){
        if (colIndexLessThanOrEqual == null) {
            throw new RuntimeException("colIndex is null");
        }
        this.colIndexLessThanOrEqual = colIndexLessThanOrEqual;
        return this;
    }

    public DepReportCellPosQuery colIndexs(List<Integer> colIndexs){
        if (colIndexs == null) {
            throw new RuntimeException("colIndexs is empty ");
        }
        this.colIndexs = colIndexs;
        return this;
    }


    public DepReportCellPosQuery varFalg(String varFalg){
	if (varFalg == null) {
	    throw new RuntimeException("varFalg is null");
        }         
	this.varFalg = varFalg;
	return this;
    }

    public DepReportCellPosQuery varFalgLike( String varFalgLike){
        if (varFalgLike == null) {
            throw new RuntimeException("varFalg is null");
        }
        this.varFalgLike = varFalgLike;
        return this;
    }

    public DepReportCellPosQuery varFalgs(List<String> varFalgs){
        if (varFalgs == null) {
            throw new RuntimeException("varFalgs is empty ");
        }
        this.varFalgs = varFalgs;
        return this;
    }


    public DepReportCellPosQuery direction(String direction){
	if (direction == null) {
	    throw new RuntimeException("direction is null");
        }         
	this.direction = direction;
	return this;
    }

    public DepReportCellPosQuery directionLike( String directionLike){
        if (directionLike == null) {
            throw new RuntimeException("direction is null");
        }
        this.directionLike = directionLike;
        return this;
    }

    public DepReportCellPosQuery directions(List<String> directions){
        if (directions == null) {
            throw new RuntimeException("directions is empty ");
        }
        this.directions = directions;
        return this;
    }


    public DepReportCellPosQuery endRowIndex(Integer endRowIndex){
	if (endRowIndex == null) {
            throw new RuntimeException("endRowIndex is null");
        }         
	this.endRowIndex = endRowIndex;
	return this;
    }

    public DepReportCellPosQuery endRowIndexGreaterThanOrEqual(Integer endRowIndexGreaterThanOrEqual){
	if (endRowIndexGreaterThanOrEqual == null) {
	    throw new RuntimeException("endRowIndex is null");
        }         
	this.endRowIndexGreaterThanOrEqual = endRowIndexGreaterThanOrEqual;
        return this;
    }

    public DepReportCellPosQuery endRowIndexLessThanOrEqual(Integer endRowIndexLessThanOrEqual){
        if (endRowIndexLessThanOrEqual == null) {
            throw new RuntimeException("endRowIndex is null");
        }
        this.endRowIndexLessThanOrEqual = endRowIndexLessThanOrEqual;
        return this;
    }

    public DepReportCellPosQuery endRowIndexs(List<Integer> endRowIndexs){
        if (endRowIndexs == null) {
            throw new RuntimeException("endRowIndexs is empty ");
        }
        this.endRowIndexs = endRowIndexs;
        return this;
    }


    public DepReportCellPosQuery endColIndex(Integer endColIndex){
	if (endColIndex == null) {
            throw new RuntimeException("endColIndex is null");
        }         
	this.endColIndex = endColIndex;
	return this;
    }

    public DepReportCellPosQuery endColIndexGreaterThanOrEqual(Integer endColIndexGreaterThanOrEqual){
	if (endColIndexGreaterThanOrEqual == null) {
	    throw new RuntimeException("endColIndex is null");
        }         
	this.endColIndexGreaterThanOrEqual = endColIndexGreaterThanOrEqual;
        return this;
    }

    public DepReportCellPosQuery endColIndexLessThanOrEqual(Integer endColIndexLessThanOrEqual){
        if (endColIndexLessThanOrEqual == null) {
            throw new RuntimeException("endColIndex is null");
        }
        this.endColIndexLessThanOrEqual = endColIndexLessThanOrEqual;
        return this;
    }

    public DepReportCellPosQuery endColIndexs(List<Integer> endColIndexs){
        if (endColIndexs == null) {
            throw new RuntimeException("endColIndexs is empty ");
        }
        this.endColIndexs = endColIndexs;
        return this;
    }



    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("cellId".equals(sortColumn)) {
                orderBy = "E.CELL_ID_" + a_x;
            } 

            if ("rowIndex".equals(sortColumn)) {
                orderBy = "E.ROW_INDEX_" + a_x;
            } 

            if ("colIndex".equals(sortColumn)) {
                orderBy = "E.COL_INDEX_" + a_x;
            } 

            if ("varFalg".equals(sortColumn)) {
                orderBy = "E.VARFALG_" + a_x;
            } 

            if ("direction".equals(sortColumn)) {
                orderBy = "E.DIRECTION_" + a_x;
            } 

            if ("endRowIndex".equals(sortColumn)) {
                orderBy = "E.END_ROW_INDEX_" + a_x;
            } 

            if ("endColIndex".equals(sortColumn)) {
                orderBy = "E.END_COL_INDEX_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("cellId", "CELL_ID_");
        addColumn("rowIndex", "ROW_INDEX_");
        addColumn("colIndex", "COL_INDEX_");
        addColumn("varFalg", "VARFALG_");
        addColumn("direction", "DIRECTION_");
        addColumn("endRowIndex", "END_ROW_INDEX_");
        addColumn("endColIndex", "END_COL_INDEX_");
    }

}