package com.glaf.dep.report.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DepReportPrintQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
  	protected Long cellId;
  	protected Long cellIdGreaterThanOrEqual;
  	protected Long cellIdLessThanOrEqual;
  	protected List<Long> cellIds;
  	protected String printFlag;
  	protected String printFlagLike;
  	protected List<String> printFlags;
  	protected Integer pageCells;
  	protected Integer pageCellsGreaterThanOrEqual;
  	protected Integer pageCellsLessThanOrEqual;
  	protected List<Integer> pageCellss;
  	protected String loopFlag;
  	protected String loopFlagLike;
  	protected List<String> loopFlags;
  	protected String enCondition;
  	protected String enConditionLike;
  	protected List<String> enConditions;
  	protected String creator;
  	protected String creatorLike;
  	protected List<String> creators;
        protected Date createDateTimeGreaterThanOrEqual;
  	protected Date createDateTimeLessThanOrEqual;
  	protected String modifier;
  	protected String modifierLike;
  	protected List<String> modifiers;
        protected Date modifyDateTimeGreaterThanOrEqual;
  	protected Date modifyDateTimeLessThanOrEqual;

    public DepReportPrintQuery() {

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

    public String getPrintFlag(){
        return printFlag;
    }

    public String getPrintFlagLike(){
	    if (printFlagLike != null && printFlagLike.trim().length() > 0) {
		if (!printFlagLike.startsWith("%")) {
		    printFlagLike = "%" + printFlagLike;
		}
		if (!printFlagLike.endsWith("%")) {
		   printFlagLike = printFlagLike + "%";
		}
	    }
	return printFlagLike;
    }

    public List<String> getPrintFlags(){
	return printFlags;
    }


    public Integer getPageCells(){
        return pageCells;
    }

    public Integer getPageCellsGreaterThanOrEqual(){
        return pageCellsGreaterThanOrEqual;
    }

    public Integer getPageCellsLessThanOrEqual(){
	return pageCellsLessThanOrEqual;
    }

    public List<Integer> getPageCellss(){
	return pageCellss;
    }

    public String getLoopFlag(){
        return loopFlag;
    }

    public String getLoopFlagLike(){
	    if (loopFlagLike != null && loopFlagLike.trim().length() > 0) {
		if (!loopFlagLike.startsWith("%")) {
		    loopFlagLike = "%" + loopFlagLike;
		}
		if (!loopFlagLike.endsWith("%")) {
		   loopFlagLike = loopFlagLike + "%";
		}
	    }
	return loopFlagLike;
    }

    public List<String> getLoopFlags(){
	return loopFlags;
    }


    public String getEnCondition(){
        return enCondition;
    }

    public String getEnConditionLike(){
	    if (enConditionLike != null && enConditionLike.trim().length() > 0) {
		if (!enConditionLike.startsWith("%")) {
		    enConditionLike = "%" + enConditionLike;
		}
		if (!enConditionLike.endsWith("%")) {
		   enConditionLike = enConditionLike + "%";
		}
	    }
	return enConditionLike;
    }

    public List<String> getEnConditions(){
	return enConditions;
    }


    public String getCreator(){
        return creator;
    }

    public String getCreatorLike(){
	    if (creatorLike != null && creatorLike.trim().length() > 0) {
		if (!creatorLike.startsWith("%")) {
		    creatorLike = "%" + creatorLike;
		}
		if (!creatorLike.endsWith("%")) {
		   creatorLike = creatorLike + "%";
		}
	    }
	return creatorLike;
    }

    public List<String> getCreators(){
	return creators;
    }


    public Date getCreateDateTimeGreaterThanOrEqual(){
        return createDateTimeGreaterThanOrEqual;
    }

    public Date getCreateDateTimeLessThanOrEqual(){
	return createDateTimeLessThanOrEqual;
    }


    public String getModifier(){
        return modifier;
    }

    public String getModifierLike(){
	    if (modifierLike != null && modifierLike.trim().length() > 0) {
		if (!modifierLike.startsWith("%")) {
		    modifierLike = "%" + modifierLike;
		}
		if (!modifierLike.endsWith("%")) {
		   modifierLike = modifierLike + "%";
		}
	    }
	return modifierLike;
    }

    public List<String> getModifiers(){
	return modifiers;
    }


    public Date getModifyDateTimeGreaterThanOrEqual(){
        return modifyDateTimeGreaterThanOrEqual;
    }

    public Date getModifyDateTimeLessThanOrEqual(){
	return modifyDateTimeLessThanOrEqual;
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


    public void setPrintFlag(String printFlag){
        this.printFlag = printFlag;
    }

    public void setPrintFlagLike( String printFlagLike){
	this.printFlagLike = printFlagLike;
    }

    public void setPrintFlags(List<String> printFlags){
        this.printFlags = printFlags;
    }


    public void setPageCells(Integer pageCells){
        this.pageCells = pageCells;
    }

    public void setPageCellsGreaterThanOrEqual(Integer pageCellsGreaterThanOrEqual){
        this.pageCellsGreaterThanOrEqual = pageCellsGreaterThanOrEqual;
    }

    public void setPageCellsLessThanOrEqual(Integer pageCellsLessThanOrEqual){
	this.pageCellsLessThanOrEqual = pageCellsLessThanOrEqual;
    }

    public void setPageCellss(List<Integer> pageCellss){
        this.pageCellss = pageCellss;
    }


    public void setLoopFlag(String loopFlag){
        this.loopFlag = loopFlag;
    }

    public void setLoopFlagLike( String loopFlagLike){
	this.loopFlagLike = loopFlagLike;
    }

    public void setLoopFlags(List<String> loopFlags){
        this.loopFlags = loopFlags;
    }


    public void setEnCondition(String enCondition){
        this.enCondition = enCondition;
    }

    public void setEnConditionLike( String enConditionLike){
	this.enConditionLike = enConditionLike;
    }

    public void setEnConditions(List<String> enConditions){
        this.enConditions = enConditions;
    }


    public void setCreator(String creator){
        this.creator = creator;
    }

    public void setCreatorLike( String creatorLike){
	this.creatorLike = creatorLike;
    }

    public void setCreators(List<String> creators){
        this.creators = creators;
    }


    public void setCreateDateTimeGreaterThanOrEqual(Date createDateTimeGreaterThanOrEqual){
        this.createDateTimeGreaterThanOrEqual = createDateTimeGreaterThanOrEqual;
    }

    public void setCreateDateTimeLessThanOrEqual(Date createDateTimeLessThanOrEqual){
	this.createDateTimeLessThanOrEqual = createDateTimeLessThanOrEqual;
    }


    public void setModifier(String modifier){
        this.modifier = modifier;
    }

    public void setModifierLike( String modifierLike){
	this.modifierLike = modifierLike;
    }

    public void setModifiers(List<String> modifiers){
        this.modifiers = modifiers;
    }


    public void setModifyDateTimeGreaterThanOrEqual(Date modifyDateTimeGreaterThanOrEqual){
        this.modifyDateTimeGreaterThanOrEqual = modifyDateTimeGreaterThanOrEqual;
    }

    public void setModifyDateTimeLessThanOrEqual(Date modifyDateTimeLessThanOrEqual){
	this.modifyDateTimeLessThanOrEqual = modifyDateTimeLessThanOrEqual;
    }




    public DepReportPrintQuery cellId(Long cellId){
	if (cellId == null) {
            throw new RuntimeException("cellId is null");
        }         
	this.cellId = cellId;
	return this;
    }

    public DepReportPrintQuery cellIdGreaterThanOrEqual(Long cellIdGreaterThanOrEqual){
	if (cellIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("cellId is null");
        }         
	this.cellIdGreaterThanOrEqual = cellIdGreaterThanOrEqual;
        return this;
    }

    public DepReportPrintQuery cellIdLessThanOrEqual(Long cellIdLessThanOrEqual){
        if (cellIdLessThanOrEqual == null) {
            throw new RuntimeException("cellId is null");
        }
        this.cellIdLessThanOrEqual = cellIdLessThanOrEqual;
        return this;
    }

    public DepReportPrintQuery cellIds(List<Long> cellIds){
        if (cellIds == null) {
            throw new RuntimeException("cellIds is empty ");
        }
        this.cellIds = cellIds;
        return this;
    }


    public DepReportPrintQuery printFlag(String printFlag){
	if (printFlag == null) {
	    throw new RuntimeException("printFlag is null");
        }         
	this.printFlag = printFlag;
	return this;
    }

    public DepReportPrintQuery printFlagLike( String printFlagLike){
        if (printFlagLike == null) {
            throw new RuntimeException("printFlag is null");
        }
        this.printFlagLike = printFlagLike;
        return this;
    }

    public DepReportPrintQuery printFlags(List<String> printFlags){
        if (printFlags == null) {
            throw new RuntimeException("printFlags is empty ");
        }
        this.printFlags = printFlags;
        return this;
    }


    public DepReportPrintQuery pageCells(Integer pageCells){
	if (pageCells == null) {
            throw new RuntimeException("pageCells is null");
        }         
	this.pageCells = pageCells;
	return this;
    }

    public DepReportPrintQuery pageCellsGreaterThanOrEqual(Integer pageCellsGreaterThanOrEqual){
	if (pageCellsGreaterThanOrEqual == null) {
	    throw new RuntimeException("pageCells is null");
        }         
	this.pageCellsGreaterThanOrEqual = pageCellsGreaterThanOrEqual;
        return this;
    }

    public DepReportPrintQuery pageCellsLessThanOrEqual(Integer pageCellsLessThanOrEqual){
        if (pageCellsLessThanOrEqual == null) {
            throw new RuntimeException("pageCells is null");
        }
        this.pageCellsLessThanOrEqual = pageCellsLessThanOrEqual;
        return this;
    }

    public DepReportPrintQuery pageCellss(List<Integer> pageCellss){
        if (pageCellss == null) {
            throw new RuntimeException("pageCellss is empty ");
        }
        this.pageCellss = pageCellss;
        return this;
    }


    public DepReportPrintQuery loopFlag(String loopFlag){
	if (loopFlag == null) {
	    throw new RuntimeException("loopFlag is null");
        }         
	this.loopFlag = loopFlag;
	return this;
    }

    public DepReportPrintQuery loopFlagLike( String loopFlagLike){
        if (loopFlagLike == null) {
            throw new RuntimeException("loopFlag is null");
        }
        this.loopFlagLike = loopFlagLike;
        return this;
    }

    public DepReportPrintQuery loopFlags(List<String> loopFlags){
        if (loopFlags == null) {
            throw new RuntimeException("loopFlags is empty ");
        }
        this.loopFlags = loopFlags;
        return this;
    }


    public DepReportPrintQuery enCondition(String enCondition){
	if (enCondition == null) {
	    throw new RuntimeException("enCondition is null");
        }         
	this.enCondition = enCondition;
	return this;
    }

    public DepReportPrintQuery enConditionLike( String enConditionLike){
        if (enConditionLike == null) {
            throw new RuntimeException("enCondition is null");
        }
        this.enConditionLike = enConditionLike;
        return this;
    }

    public DepReportPrintQuery enConditions(List<String> enConditions){
        if (enConditions == null) {
            throw new RuntimeException("enConditions is empty ");
        }
        this.enConditions = enConditions;
        return this;
    }


    public DepReportPrintQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public DepReportPrintQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public DepReportPrintQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public DepReportPrintQuery createDateTimeGreaterThanOrEqual(Date createDateTimeGreaterThanOrEqual){
	if (createDateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDateTime is null");
        }         
	this.createDateTimeGreaterThanOrEqual = createDateTimeGreaterThanOrEqual;
        return this;
    }

    public DepReportPrintQuery createDateTimeLessThanOrEqual(Date createDateTimeLessThanOrEqual){
        if (createDateTimeLessThanOrEqual == null) {
            throw new RuntimeException("createDateTime is null");
        }
        this.createDateTimeLessThanOrEqual = createDateTimeLessThanOrEqual;
        return this;
    }



    public DepReportPrintQuery modifier(String modifier){
	if (modifier == null) {
	    throw new RuntimeException("modifier is null");
        }         
	this.modifier = modifier;
	return this;
    }

    public DepReportPrintQuery modifierLike( String modifierLike){
        if (modifierLike == null) {
            throw new RuntimeException("modifier is null");
        }
        this.modifierLike = modifierLike;
        return this;
    }

    public DepReportPrintQuery modifiers(List<String> modifiers){
        if (modifiers == null) {
            throw new RuntimeException("modifiers is empty ");
        }
        this.modifiers = modifiers;
        return this;
    }



    public DepReportPrintQuery modifyDateTimeGreaterThanOrEqual(Date modifyDateTimeGreaterThanOrEqual){
	if (modifyDateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("modifyDateTime is null");
        }         
	this.modifyDateTimeGreaterThanOrEqual = modifyDateTimeGreaterThanOrEqual;
        return this;
    }

    public DepReportPrintQuery modifyDateTimeLessThanOrEqual(Date modifyDateTimeLessThanOrEqual){
        if (modifyDateTimeLessThanOrEqual == null) {
            throw new RuntimeException("modifyDateTime is null");
        }
        this.modifyDateTimeLessThanOrEqual = modifyDateTimeLessThanOrEqual;
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

            if ("printFlag".equals(sortColumn)) {
                orderBy = "E.PRINTFLAG_" + a_x;
            } 

            if ("pageCells".equals(sortColumn)) {
                orderBy = "E.PAGECELLS_" + a_x;
            } 

            if ("loopFlag".equals(sortColumn)) {
                orderBy = "E.LOOPFLAG_" + a_x;
            } 

            if ("enCondition".equals(sortColumn)) {
                orderBy = "E.ENCONDITON_" + a_x;
            } 

            if ("creator".equals(sortColumn)) {
                orderBy = "E.CREATOR_" + a_x;
            } 

            if ("createDateTime".equals(sortColumn)) {
                orderBy = "E.CREATEDATETIME_" + a_x;
            } 

            if ("modifier".equals(sortColumn)) {
                orderBy = "E.MODIFIER_" + a_x;
            } 

            if ("modifyDateTime".equals(sortColumn)) {
                orderBy = "E.MODIFYDATETIME_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("cellId", "CELL_ID_");
        addColumn("printFlag", "PRINTFLAG_");
        addColumn("pageCells", "PAGECELLS_");
        addColumn("loopFlag", "LOOPFLAG_");
        addColumn("enCondition", "ENCONDITON_");
        addColumn("creator", "CREATOR_");
        addColumn("createDateTime", "CREATEDATETIME_");
        addColumn("modifier", "MODIFIER_");
        addColumn("modifyDateTime", "MODIFYDATETIME_");
    }

}