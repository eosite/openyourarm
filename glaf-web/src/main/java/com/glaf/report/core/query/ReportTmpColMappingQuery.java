package com.glaf.report.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ReportTmpColMappingQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String dataSetMappingId;
  	protected String dataSetMappingIdLike;
  	protected List<String> dataSetMappingIds;
  	protected String colCode;
  	protected String colCodeLike;
  	protected List<String> colCodes;
  	protected String colName;
  	protected String colNameLike;
  	protected List<String> colNames;
  	protected String colTitle;
  	protected String colTitleLike;
  	protected List<String> colTitles;
  	protected String colDtype;
  	protected String colDtypeLike;
  	protected List<String> colDtypes;
  	protected String colMappingCode;
  	protected String colMappingCodeLike;
  	protected List<String> colMappingCodes;
  	protected String colMappingName;
  	protected String colMappingNameLike;
  	protected List<String> colMappingNames;
  	protected String colMappingTitle;
  	protected String colMappingTitleLike;
  	protected List<String> colMappingTitles;
  	protected String colMappingDtype;
  	protected String colMappingDtypeLike;
  	protected List<String> colMappingDtypes;
  	protected String creator;
  	protected String creatorLike;
  	protected List<String> creators;
        protected Date createDatetimeGreaterThanOrEqual;
  	protected Date createDatetimeLessThanOrEqual;
  	protected String modifier;
  	protected String modifierLike;
  	protected List<String> modifiers;
        protected Date modifyDatetimeGreaterThanOrEqual;
  	protected Date modifyDatetimeLessThanOrEqual;

    public ReportTmpColMappingQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getDataSetMappingId(){
        return dataSetMappingId;
    }

    public String getDataSetMappingIdLike(){
	    if (dataSetMappingIdLike != null && dataSetMappingIdLike.trim().length() > 0) {
		if (!dataSetMappingIdLike.startsWith("%")) {
		    dataSetMappingIdLike = "%" + dataSetMappingIdLike;
		}
		if (!dataSetMappingIdLike.endsWith("%")) {
		   dataSetMappingIdLike = dataSetMappingIdLike + "%";
		}
	    }
	return dataSetMappingIdLike;
    }

    public List<String> getDataSetMappingIds(){
	return dataSetMappingIds;
    }


    public String getColCode(){
        return colCode;
    }

    public String getColCodeLike(){
	    if (colCodeLike != null && colCodeLike.trim().length() > 0) {
		if (!colCodeLike.startsWith("%")) {
		    colCodeLike = "%" + colCodeLike;
		}
		if (!colCodeLike.endsWith("%")) {
		   colCodeLike = colCodeLike + "%";
		}
	    }
	return colCodeLike;
    }

    public List<String> getColCodes(){
	return colCodes;
    }


    public String getColName(){
        return colName;
    }

    public String getColNameLike(){
	    if (colNameLike != null && colNameLike.trim().length() > 0) {
		if (!colNameLike.startsWith("%")) {
		    colNameLike = "%" + colNameLike;
		}
		if (!colNameLike.endsWith("%")) {
		   colNameLike = colNameLike + "%";
		}
	    }
	return colNameLike;
    }

    public List<String> getColNames(){
	return colNames;
    }


    public String getColTitle(){
        return colTitle;
    }

    public String getColTitleLike(){
	    if (colTitleLike != null && colTitleLike.trim().length() > 0) {
		if (!colTitleLike.startsWith("%")) {
		    colTitleLike = "%" + colTitleLike;
		}
		if (!colTitleLike.endsWith("%")) {
		   colTitleLike = colTitleLike + "%";
		}
	    }
	return colTitleLike;
    }

    public List<String> getColTitles(){
	return colTitles;
    }


    public String getColDtype(){
        return colDtype;
    }

    public String getColDtypeLike(){
	    if (colDtypeLike != null && colDtypeLike.trim().length() > 0) {
		if (!colDtypeLike.startsWith("%")) {
		    colDtypeLike = "%" + colDtypeLike;
		}
		if (!colDtypeLike.endsWith("%")) {
		   colDtypeLike = colDtypeLike + "%";
		}
	    }
	return colDtypeLike;
    }

    public List<String> getColDtypes(){
	return colDtypes;
    }


    public String getColMappingCode(){
        return colMappingCode;
    }

    public String getColMappingCodeLike(){
	    if (colMappingCodeLike != null && colMappingCodeLike.trim().length() > 0) {
		if (!colMappingCodeLike.startsWith("%")) {
		    colMappingCodeLike = "%" + colMappingCodeLike;
		}
		if (!colMappingCodeLike.endsWith("%")) {
		   colMappingCodeLike = colMappingCodeLike + "%";
		}
	    }
	return colMappingCodeLike;
    }

    public List<String> getColMappingCodes(){
	return colMappingCodes;
    }


    public String getColMappingName(){
        return colMappingName;
    }

    public String getColMappingNameLike(){
	    if (colMappingNameLike != null && colMappingNameLike.trim().length() > 0) {
		if (!colMappingNameLike.startsWith("%")) {
		    colMappingNameLike = "%" + colMappingNameLike;
		}
		if (!colMappingNameLike.endsWith("%")) {
		   colMappingNameLike = colMappingNameLike + "%";
		}
	    }
	return colMappingNameLike;
    }

    public List<String> getColMappingNames(){
	return colMappingNames;
    }


    public String getColMappingTitle(){
        return colMappingTitle;
    }

    public String getColMappingTitleLike(){
	    if (colMappingTitleLike != null && colMappingTitleLike.trim().length() > 0) {
		if (!colMappingTitleLike.startsWith("%")) {
		    colMappingTitleLike = "%" + colMappingTitleLike;
		}
		if (!colMappingTitleLike.endsWith("%")) {
		   colMappingTitleLike = colMappingTitleLike + "%";
		}
	    }
	return colMappingTitleLike;
    }

    public List<String> getColMappingTitles(){
	return colMappingTitles;
    }


    public String getColMappingDtype(){
        return colMappingDtype;
    }

    public String getColMappingDtypeLike(){
	    if (colMappingDtypeLike != null && colMappingDtypeLike.trim().length() > 0) {
		if (!colMappingDtypeLike.startsWith("%")) {
		    colMappingDtypeLike = "%" + colMappingDtypeLike;
		}
		if (!colMappingDtypeLike.endsWith("%")) {
		   colMappingDtypeLike = colMappingDtypeLike + "%";
		}
	    }
	return colMappingDtypeLike;
    }

    public List<String> getColMappingDtypes(){
	return colMappingDtypes;
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


    public Date getCreateDatetimeGreaterThanOrEqual(){
        return createDatetimeGreaterThanOrEqual;
    }

    public Date getCreateDatetimeLessThanOrEqual(){
	return createDatetimeLessThanOrEqual;
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


    public Date getModifyDatetimeGreaterThanOrEqual(){
        return modifyDatetimeGreaterThanOrEqual;
    }

    public Date getModifyDatetimeLessThanOrEqual(){
	return modifyDatetimeLessThanOrEqual;
    }


 

    public void setDataSetMappingId(String dataSetMappingId){
        this.dataSetMappingId = dataSetMappingId;
    }

    public void setDataSetMappingIdLike( String dataSetMappingIdLike){
	this.dataSetMappingIdLike = dataSetMappingIdLike;
    }

    public void setDataSetMappingIds(List<String> dataSetMappingIds){
        this.dataSetMappingIds = dataSetMappingIds;
    }


    public void setColCode(String colCode){
        this.colCode = colCode;
    }

    public void setColCodeLike( String colCodeLike){
	this.colCodeLike = colCodeLike;
    }

    public void setColCodes(List<String> colCodes){
        this.colCodes = colCodes;
    }


    public void setColName(String colName){
        this.colName = colName;
    }

    public void setColNameLike( String colNameLike){
	this.colNameLike = colNameLike;
    }

    public void setColNames(List<String> colNames){
        this.colNames = colNames;
    }


    public void setColTitle(String colTitle){
        this.colTitle = colTitle;
    }

    public void setColTitleLike( String colTitleLike){
	this.colTitleLike = colTitleLike;
    }

    public void setColTitles(List<String> colTitles){
        this.colTitles = colTitles;
    }


    public void setColDtype(String colDtype){
        this.colDtype = colDtype;
    }

    public void setColDtypeLike( String colDtypeLike){
	this.colDtypeLike = colDtypeLike;
    }

    public void setColDtypes(List<String> colDtypes){
        this.colDtypes = colDtypes;
    }


    public void setColMappingCode(String colMappingCode){
        this.colMappingCode = colMappingCode;
    }

    public void setColMappingCodeLike( String colMappingCodeLike){
	this.colMappingCodeLike = colMappingCodeLike;
    }

    public void setColMappingCodes(List<String> colMappingCodes){
        this.colMappingCodes = colMappingCodes;
    }


    public void setColMappingName(String colMappingName){
        this.colMappingName = colMappingName;
    }

    public void setColMappingNameLike( String colMappingNameLike){
	this.colMappingNameLike = colMappingNameLike;
    }

    public void setColMappingNames(List<String> colMappingNames){
        this.colMappingNames = colMappingNames;
    }


    public void setColMappingTitle(String colMappingTitle){
        this.colMappingTitle = colMappingTitle;
    }

    public void setColMappingTitleLike( String colMappingTitleLike){
	this.colMappingTitleLike = colMappingTitleLike;
    }

    public void setColMappingTitles(List<String> colMappingTitles){
        this.colMappingTitles = colMappingTitles;
    }


    public void setColMappingDtype(String colMappingDtype){
        this.colMappingDtype = colMappingDtype;
    }

    public void setColMappingDtypeLike( String colMappingDtypeLike){
	this.colMappingDtypeLike = colMappingDtypeLike;
    }

    public void setColMappingDtypes(List<String> colMappingDtypes){
        this.colMappingDtypes = colMappingDtypes;
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


    public void setCreateDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual){
        this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
    }

    public void setCreateDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual){
	this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
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


    public void setModifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual){
        this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
    }

    public void setModifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual){
	this.modifyDatetimeLessThanOrEqual = modifyDatetimeLessThanOrEqual;
    }




    public ReportTmpColMappingQuery dataSetMappingId(String dataSetMappingId){
	if (dataSetMappingId == null) {
	    throw new RuntimeException("dataSetMappingId is null");
        }         
	this.dataSetMappingId = dataSetMappingId;
	return this;
    }

    public ReportTmpColMappingQuery dataSetMappingIdLike( String dataSetMappingIdLike){
        if (dataSetMappingIdLike == null) {
            throw new RuntimeException("dataSetMappingId is null");
        }
        this.dataSetMappingIdLike = dataSetMappingIdLike;
        return this;
    }

    public ReportTmpColMappingQuery dataSetMappingIds(List<String> dataSetMappingIds){
        if (dataSetMappingIds == null) {
            throw new RuntimeException("dataSetMappingIds is empty ");
        }
        this.dataSetMappingIds = dataSetMappingIds;
        return this;
    }


    public ReportTmpColMappingQuery colCode(String colCode){
	if (colCode == null) {
	    throw new RuntimeException("colCode is null");
        }         
	this.colCode = colCode;
	return this;
    }

    public ReportTmpColMappingQuery colCodeLike( String colCodeLike){
        if (colCodeLike == null) {
            throw new RuntimeException("colCode is null");
        }
        this.colCodeLike = colCodeLike;
        return this;
    }

    public ReportTmpColMappingQuery colCodes(List<String> colCodes){
        if (colCodes == null) {
            throw new RuntimeException("colCodes is empty ");
        }
        this.colCodes = colCodes;
        return this;
    }


    public ReportTmpColMappingQuery colName(String colName){
	if (colName == null) {
	    throw new RuntimeException("colName is null");
        }         
	this.colName = colName;
	return this;
    }

    public ReportTmpColMappingQuery colNameLike( String colNameLike){
        if (colNameLike == null) {
            throw new RuntimeException("colName is null");
        }
        this.colNameLike = colNameLike;
        return this;
    }

    public ReportTmpColMappingQuery colNames(List<String> colNames){
        if (colNames == null) {
            throw new RuntimeException("colNames is empty ");
        }
        this.colNames = colNames;
        return this;
    }


    public ReportTmpColMappingQuery colTitle(String colTitle){
	if (colTitle == null) {
	    throw new RuntimeException("colTitle is null");
        }         
	this.colTitle = colTitle;
	return this;
    }

    public ReportTmpColMappingQuery colTitleLike( String colTitleLike){
        if (colTitleLike == null) {
            throw new RuntimeException("colTitle is null");
        }
        this.colTitleLike = colTitleLike;
        return this;
    }

    public ReportTmpColMappingQuery colTitles(List<String> colTitles){
        if (colTitles == null) {
            throw new RuntimeException("colTitles is empty ");
        }
        this.colTitles = colTitles;
        return this;
    }


    public ReportTmpColMappingQuery colDtype(String colDtype){
	if (colDtype == null) {
	    throw new RuntimeException("colDtype is null");
        }         
	this.colDtype = colDtype;
	return this;
    }

    public ReportTmpColMappingQuery colDtypeLike( String colDtypeLike){
        if (colDtypeLike == null) {
            throw new RuntimeException("colDtype is null");
        }
        this.colDtypeLike = colDtypeLike;
        return this;
    }

    public ReportTmpColMappingQuery colDtypes(List<String> colDtypes){
        if (colDtypes == null) {
            throw new RuntimeException("colDtypes is empty ");
        }
        this.colDtypes = colDtypes;
        return this;
    }


    public ReportTmpColMappingQuery colMappingCode(String colMappingCode){
	if (colMappingCode == null) {
	    throw new RuntimeException("colMappingCode is null");
        }         
	this.colMappingCode = colMappingCode;
	return this;
    }

    public ReportTmpColMappingQuery colMappingCodeLike( String colMappingCodeLike){
        if (colMappingCodeLike == null) {
            throw new RuntimeException("colMappingCode is null");
        }
        this.colMappingCodeLike = colMappingCodeLike;
        return this;
    }

    public ReportTmpColMappingQuery colMappingCodes(List<String> colMappingCodes){
        if (colMappingCodes == null) {
            throw new RuntimeException("colMappingCodes is empty ");
        }
        this.colMappingCodes = colMappingCodes;
        return this;
    }


    public ReportTmpColMappingQuery colMappingName(String colMappingName){
	if (colMappingName == null) {
	    throw new RuntimeException("colMappingName is null");
        }         
	this.colMappingName = colMappingName;
	return this;
    }

    public ReportTmpColMappingQuery colMappingNameLike( String colMappingNameLike){
        if (colMappingNameLike == null) {
            throw new RuntimeException("colMappingName is null");
        }
        this.colMappingNameLike = colMappingNameLike;
        return this;
    }

    public ReportTmpColMappingQuery colMappingNames(List<String> colMappingNames){
        if (colMappingNames == null) {
            throw new RuntimeException("colMappingNames is empty ");
        }
        this.colMappingNames = colMappingNames;
        return this;
    }


    public ReportTmpColMappingQuery colMappingTitle(String colMappingTitle){
	if (colMappingTitle == null) {
	    throw new RuntimeException("colMappingTitle is null");
        }         
	this.colMappingTitle = colMappingTitle;
	return this;
    }

    public ReportTmpColMappingQuery colMappingTitleLike( String colMappingTitleLike){
        if (colMappingTitleLike == null) {
            throw new RuntimeException("colMappingTitle is null");
        }
        this.colMappingTitleLike = colMappingTitleLike;
        return this;
    }

    public ReportTmpColMappingQuery colMappingTitles(List<String> colMappingTitles){
        if (colMappingTitles == null) {
            throw new RuntimeException("colMappingTitles is empty ");
        }
        this.colMappingTitles = colMappingTitles;
        return this;
    }


    public ReportTmpColMappingQuery colMappingDtype(String colMappingDtype){
	if (colMappingDtype == null) {
	    throw new RuntimeException("colMappingDtype is null");
        }         
	this.colMappingDtype = colMappingDtype;
	return this;
    }

    public ReportTmpColMappingQuery colMappingDtypeLike( String colMappingDtypeLike){
        if (colMappingDtypeLike == null) {
            throw new RuntimeException("colMappingDtype is null");
        }
        this.colMappingDtypeLike = colMappingDtypeLike;
        return this;
    }

    public ReportTmpColMappingQuery colMappingDtypes(List<String> colMappingDtypes){
        if (colMappingDtypes == null) {
            throw new RuntimeException("colMappingDtypes is empty ");
        }
        this.colMappingDtypes = colMappingDtypes;
        return this;
    }


    public ReportTmpColMappingQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public ReportTmpColMappingQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public ReportTmpColMappingQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public ReportTmpColMappingQuery createDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual){
	if (createDatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDatetime is null");
        }         
	this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
        return this;
    }

    public ReportTmpColMappingQuery createDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual){
        if (createDatetimeLessThanOrEqual == null) {
            throw new RuntimeException("createDatetime is null");
        }
        this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
        return this;
    }



    public ReportTmpColMappingQuery modifier(String modifier){
	if (modifier == null) {
	    throw new RuntimeException("modifier is null");
        }         
	this.modifier = modifier;
	return this;
    }

    public ReportTmpColMappingQuery modifierLike( String modifierLike){
        if (modifierLike == null) {
            throw new RuntimeException("modifier is null");
        }
        this.modifierLike = modifierLike;
        return this;
    }

    public ReportTmpColMappingQuery modifiers(List<String> modifiers){
        if (modifiers == null) {
            throw new RuntimeException("modifiers is empty ");
        }
        this.modifiers = modifiers;
        return this;
    }



    public ReportTmpColMappingQuery modifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual){
	if (modifyDatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("modifyDatetime is null");
        }         
	this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
        return this;
    }

    public ReportTmpColMappingQuery modifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual){
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

            if ("dataSetMappingId".equals(sortColumn)) {
                orderBy = "E.DATASET_MAPPING_ID_" + a_x;
            } 

            if ("colCode".equals(sortColumn)) {
                orderBy = "E.COL_CODE_" + a_x;
            } 

            if ("colName".equals(sortColumn)) {
                orderBy = "E.COL_NAME_" + a_x;
            } 

            if ("colTitle".equals(sortColumn)) {
                orderBy = "E.COL_TITLE_" + a_x;
            } 

            if ("colDtype".equals(sortColumn)) {
                orderBy = "E.COL_DTYPE_" + a_x;
            } 

            if ("colMappingCode".equals(sortColumn)) {
                orderBy = "E.COL_MAPPING_CODE_" + a_x;
            } 

            if ("colMappingName".equals(sortColumn)) {
                orderBy = "E.COL_MAPPING_NAME_" + a_x;
            } 

            if ("colMappingTitle".equals(sortColumn)) {
                orderBy = "E.COL_MAPPING_TITLE_" + a_x;
            } 

            if ("colMappingDtype".equals(sortColumn)) {
                orderBy = "E.COL_MAPPING_DTYPE_" + a_x;
            } 

            if ("creator".equals(sortColumn)) {
                orderBy = "E.CREATOR_" + a_x;
            } 

            if ("createDatetime".equals(sortColumn)) {
                orderBy = "E.CREATEDATETIME_" + a_x;
            } 

            if ("modifier".equals(sortColumn)) {
                orderBy = "E.MODIFIER_" + a_x;
            } 

            if ("modifyDatetime".equals(sortColumn)) {
                orderBy = "E.MODIFYDATETIME_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("dataSetMappingId", "DATASET_MAPPING_ID_");
        addColumn("colCode", "COL_CODE_");
        addColumn("colName", "COL_NAME_");
        addColumn("colTitle", "COL_TITLE_");
        addColumn("colDtype", "COL_DTYPE_");
        addColumn("colMappingCode", "COL_MAPPING_CODE_");
        addColumn("colMappingName", "COL_MAPPING_NAME_");
        addColumn("colMappingTitle", "COL_MAPPING_TITLE_");
        addColumn("colMappingDtype", "COL_MAPPING_DTYPE_");
        addColumn("creator", "CREATOR_");
        addColumn("createDatetime", "CREATEDATETIME_");
        addColumn("modifier", "MODIFIER_");
        addColumn("modifyDatetime", "MODIFYDATETIME_");
    }

}