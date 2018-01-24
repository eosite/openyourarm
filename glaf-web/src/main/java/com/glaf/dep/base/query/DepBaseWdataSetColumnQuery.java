package com.glaf.dep.base.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DepBaseWdataSetColumnQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
  	protected Long wdataSetId;
  	protected Long wdataSetIdGreaterThanOrEqual;
  	protected Long wdataSetIdLessThanOrEqual;
  	protected List<Long> wdataSetIds;
  	protected String columnName;
  	protected String columnNameLike;
  	protected List<String> columnNames;
  	protected String dataColumnName;
  	protected String dataColumnNameLike;
  	protected List<String> dataColumnNames;
  	protected String defaultVal;
  	protected String defaultValLike;
  	protected List<String> defaultVals;
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

    public DepBaseWdataSetColumnQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public Long getWdataSetId(){
        return wdataSetId;
    }

    public Long getWdataSetIdGreaterThanOrEqual(){
        return wdataSetIdGreaterThanOrEqual;
    }

    public Long getWdataSetIdLessThanOrEqual(){
	return wdataSetIdLessThanOrEqual;
    }

    public List<Long> getWdataSetIds(){
	return wdataSetIds;
    }

    public String getColumnName(){
        return columnName;
    }

    public String getColumnNameLike(){
	    if (columnNameLike != null && columnNameLike.trim().length() > 0) {
		if (!columnNameLike.startsWith("%")) {
		    columnNameLike = "%" + columnNameLike;
		}
		if (!columnNameLike.endsWith("%")) {
		   columnNameLike = columnNameLike + "%";
		}
	    }
	return columnNameLike;
    }

    public List<String> getColumnNames(){
	return columnNames;
    }


    public String getDataColumnName(){
        return dataColumnName;
    }

    public String getDataColumnNameLike(){
	    if (dataColumnNameLike != null && dataColumnNameLike.trim().length() > 0) {
		if (!dataColumnNameLike.startsWith("%")) {
		    dataColumnNameLike = "%" + dataColumnNameLike;
		}
		if (!dataColumnNameLike.endsWith("%")) {
		   dataColumnNameLike = dataColumnNameLike + "%";
		}
	    }
	return dataColumnNameLike;
    }

    public List<String> getDataColumnNames(){
	return dataColumnNames;
    }


    public String getDefaultVal(){
        return defaultVal;
    }

    public String getDefaultValLike(){
	    if (defaultValLike != null && defaultValLike.trim().length() > 0) {
		if (!defaultValLike.startsWith("%")) {
		    defaultValLike = "%" + defaultValLike;
		}
		if (!defaultValLike.endsWith("%")) {
		   defaultValLike = defaultValLike + "%";
		}
	    }
	return defaultValLike;
    }

    public List<String> getDefaultVals(){
	return defaultVals;
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


 

    public void setWdataSetId(Long wdataSetId){
        this.wdataSetId = wdataSetId;
    }

    public void setWdataSetIdGreaterThanOrEqual(Long wdataSetIdGreaterThanOrEqual){
        this.wdataSetIdGreaterThanOrEqual = wdataSetIdGreaterThanOrEqual;
    }

    public void setWdataSetIdLessThanOrEqual(Long wdataSetIdLessThanOrEqual){
	this.wdataSetIdLessThanOrEqual = wdataSetIdLessThanOrEqual;
    }

    public void setWdataSetIds(List<Long> wdataSetIds){
        this.wdataSetIds = wdataSetIds;
    }


    public void setColumnName(String columnName){
        this.columnName = columnName;
    }

    public void setColumnNameLike( String columnNameLike){
	this.columnNameLike = columnNameLike;
    }

    public void setColumnNames(List<String> columnNames){
        this.columnNames = columnNames;
    }


    public void setDataColumnName(String dataColumnName){
        this.dataColumnName = dataColumnName;
    }

    public void setDataColumnNameLike( String dataColumnNameLike){
	this.dataColumnNameLike = dataColumnNameLike;
    }

    public void setDataColumnNames(List<String> dataColumnNames){
        this.dataColumnNames = dataColumnNames;
    }


    public void setDefaultVal(String defaultVal){
        this.defaultVal = defaultVal;
    }

    public void setDefaultValLike( String defaultValLike){
	this.defaultValLike = defaultValLike;
    }

    public void setDefaultVals(List<String> defaultVals){
        this.defaultVals = defaultVals;
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




    public DepBaseWdataSetColumnQuery wdataSetId(Long wdataSetId){
	if (wdataSetId == null) {
            throw new RuntimeException("wdataSetId is null");
        }         
	this.wdataSetId = wdataSetId;
	return this;
    }

    public DepBaseWdataSetColumnQuery wdataSetIdGreaterThanOrEqual(Long wdataSetIdGreaterThanOrEqual){
	if (wdataSetIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("wdataSetId is null");
        }         
	this.wdataSetIdGreaterThanOrEqual = wdataSetIdGreaterThanOrEqual;
        return this;
    }

    public DepBaseWdataSetColumnQuery wdataSetIdLessThanOrEqual(Long wdataSetIdLessThanOrEqual){
        if (wdataSetIdLessThanOrEqual == null) {
            throw new RuntimeException("wdataSetId is null");
        }
        this.wdataSetIdLessThanOrEqual = wdataSetIdLessThanOrEqual;
        return this;
    }

    public DepBaseWdataSetColumnQuery wdataSetIds(List<Long> wdataSetIds){
        if (wdataSetIds == null) {
            throw new RuntimeException("wdataSetIds is empty ");
        }
        this.wdataSetIds = wdataSetIds;
        return this;
    }


    public DepBaseWdataSetColumnQuery columnName(String columnName){
	if (columnName == null) {
	    throw new RuntimeException("columnName is null");
        }         
	this.columnName = columnName;
	return this;
    }

    public DepBaseWdataSetColumnQuery columnNameLike( String columnNameLike){
        if (columnNameLike == null) {
            throw new RuntimeException("columnName is null");
        }
        this.columnNameLike = columnNameLike;
        return this;
    }

    public DepBaseWdataSetColumnQuery columnNames(List<String> columnNames){
        if (columnNames == null) {
            throw new RuntimeException("columnNames is empty ");
        }
        this.columnNames = columnNames;
        return this;
    }


    public DepBaseWdataSetColumnQuery dataColumnName(String dataColumnName){
	if (dataColumnName == null) {
	    throw new RuntimeException("dataColumnName is null");
        }         
	this.dataColumnName = dataColumnName;
	return this;
    }

    public DepBaseWdataSetColumnQuery dataColumnNameLike( String dataColumnNameLike){
        if (dataColumnNameLike == null) {
            throw new RuntimeException("dataColumnName is null");
        }
        this.dataColumnNameLike = dataColumnNameLike;
        return this;
    }

    public DepBaseWdataSetColumnQuery dataColumnNames(List<String> dataColumnNames){
        if (dataColumnNames == null) {
            throw new RuntimeException("dataColumnNames is empty ");
        }
        this.dataColumnNames = dataColumnNames;
        return this;
    }


    public DepBaseWdataSetColumnQuery defaultVal(String defaultVal){
	if (defaultVal == null) {
	    throw new RuntimeException("defaultVal is null");
        }         
	this.defaultVal = defaultVal;
	return this;
    }

    public DepBaseWdataSetColumnQuery defaultValLike( String defaultValLike){
        if (defaultValLike == null) {
            throw new RuntimeException("defaultVal is null");
        }
        this.defaultValLike = defaultValLike;
        return this;
    }

    public DepBaseWdataSetColumnQuery defaultVals(List<String> defaultVals){
        if (defaultVals == null) {
            throw new RuntimeException("defaultVals is empty ");
        }
        this.defaultVals = defaultVals;
        return this;
    }


    public DepBaseWdataSetColumnQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public DepBaseWdataSetColumnQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public DepBaseWdataSetColumnQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public DepBaseWdataSetColumnQuery createDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual){
	if (createDatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDatetime is null");
        }         
	this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
        return this;
    }

    public DepBaseWdataSetColumnQuery createDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual){
        if (createDatetimeLessThanOrEqual == null) {
            throw new RuntimeException("createDatetime is null");
        }
        this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
        return this;
    }



    public DepBaseWdataSetColumnQuery modifier(String modifier){
	if (modifier == null) {
	    throw new RuntimeException("modifier is null");
        }         
	this.modifier = modifier;
	return this;
    }

    public DepBaseWdataSetColumnQuery modifierLike( String modifierLike){
        if (modifierLike == null) {
            throw new RuntimeException("modifier is null");
        }
        this.modifierLike = modifierLike;
        return this;
    }

    public DepBaseWdataSetColumnQuery modifiers(List<String> modifiers){
        if (modifiers == null) {
            throw new RuntimeException("modifiers is empty ");
        }
        this.modifiers = modifiers;
        return this;
    }



    public DepBaseWdataSetColumnQuery modifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual){
	if (modifyDatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("modifyDatetime is null");
        }         
	this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
        return this;
    }

    public DepBaseWdataSetColumnQuery modifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual){
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

            if ("wdataSetId".equals(sortColumn)) {
                orderBy = "E.WDATASET_ID_" + a_x;
            } 

            if ("columnName".equals(sortColumn)) {
                orderBy = "E.COLUMN_NAME_" + a_x;
            } 

            if ("dataColumnName".equals(sortColumn)) {
                orderBy = "E.DATACOLUMN_NAME_" + a_x;
            } 

            if ("defaultVal".equals(sortColumn)) {
                orderBy = "E.DEFAULTVAL_" + a_x;
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
        addColumn("wdataSetId", "WDATASET_ID_");
        addColumn("columnName", "COLUMN_NAME_");
        addColumn("dataColumnName", "DATACOLUMN_NAME_");
        addColumn("defaultVal", "DEFAULTVAL_");
        addColumn("creator", "CREATOR_");
        addColumn("createDatetime", "CREATEDATETIME_");
        addColumn("modifier", "MODIFIER_");
        addColumn("modifyDatetime", "MODIFYDATETIME_");
    }

}