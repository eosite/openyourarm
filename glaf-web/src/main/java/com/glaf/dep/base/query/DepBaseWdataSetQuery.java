package com.glaf.dep.base.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DepBaseWdataSetQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
  	protected String dataSetCode;
  	protected String dataSetCodeLike;
  	protected List<String> dataSetCodes;
  	protected String dataSetName;
  	protected String dataSetNameLike;
  	protected List<String> dataSetNames;
  	protected String dataSetDesc;
  	protected String dataSetDescLike;
  	protected List<String> dataSetDescs;
  	protected String ruleJson;
  	protected String ruleJsonLike;
  	protected List<String> ruleJsons;
  	protected String tableName;
  	protected String tableNameLike;
  	protected List<String> tableNames;
  	protected String dataTableName;
  	protected String dataTableNameLike;
  	protected List<String> dataTableNames;
  	protected String wtype;
  	protected String wtypeLike;
  	protected List<String> wtypes;
  	protected Integer ver;
  	protected Integer verGreaterThanOrEqual;
  	protected Integer verLessThanOrEqual;
  	protected List<Integer> vers;
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
  	protected String delFlag;
  	protected String delFlagLike;
  	protected List<String> delFlags;

    public DepBaseWdataSetQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getDataSetCode(){
        return dataSetCode;
    }

    public String getDataSetCodeLike(){
	    if (dataSetCodeLike != null && dataSetCodeLike.trim().length() > 0) {
		if (!dataSetCodeLike.startsWith("%")) {
		    dataSetCodeLike = "%" + dataSetCodeLike;
		}
		if (!dataSetCodeLike.endsWith("%")) {
		   dataSetCodeLike = dataSetCodeLike + "%";
		}
	    }
	return dataSetCodeLike;
    }

    public List<String> getDataSetCodes(){
	return dataSetCodes;
    }


    public String getDataSetName(){
        return dataSetName;
    }

    public String getDataSetNameLike(){
	    if (dataSetNameLike != null && dataSetNameLike.trim().length() > 0) {
		if (!dataSetNameLike.startsWith("%")) {
		    dataSetNameLike = "%" + dataSetNameLike;
		}
		if (!dataSetNameLike.endsWith("%")) {
		   dataSetNameLike = dataSetNameLike + "%";
		}
	    }
	return dataSetNameLike;
    }

    public List<String> getDataSetNames(){
	return dataSetNames;
    }


    public String getDataSetDesc(){
        return dataSetDesc;
    }

    public String getDataSetDescLike(){
	    if (dataSetDescLike != null && dataSetDescLike.trim().length() > 0) {
		if (!dataSetDescLike.startsWith("%")) {
		    dataSetDescLike = "%" + dataSetDescLike;
		}
		if (!dataSetDescLike.endsWith("%")) {
		   dataSetDescLike = dataSetDescLike + "%";
		}
	    }
	return dataSetDescLike;
    }

    public List<String> getDataSetDescs(){
	return dataSetDescs;
    }


    public String getRuleJson(){
        return ruleJson;
    }

    public String getRuleJsonLike(){
	    if (ruleJsonLike != null && ruleJsonLike.trim().length() > 0) {
		if (!ruleJsonLike.startsWith("%")) {
		    ruleJsonLike = "%" + ruleJsonLike;
		}
		if (!ruleJsonLike.endsWith("%")) {
		   ruleJsonLike = ruleJsonLike + "%";
		}
	    }
	return ruleJsonLike;
    }

    public List<String> getRuleJsons(){
	return ruleJsons;
    }


    public String getTableName(){
        return tableName;
    }

    public String getTableNameLike(){
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

    public List<String> getTableNames(){
	return tableNames;
    }


    public String getDataTableName(){
        return dataTableName;
    }

    public String getDataTableNameLike(){
	    if (dataTableNameLike != null && dataTableNameLike.trim().length() > 0) {
		if (!dataTableNameLike.startsWith("%")) {
		    dataTableNameLike = "%" + dataTableNameLike;
		}
		if (!dataTableNameLike.endsWith("%")) {
		   dataTableNameLike = dataTableNameLike + "%";
		}
	    }
	return dataTableNameLike;
    }

    public List<String> getDataTableNames(){
	return dataTableNames;
    }


    public String getWtype(){
        return wtype;
    }

    public String getWtypeLike(){
	    if (wtypeLike != null && wtypeLike.trim().length() > 0) {
		if (!wtypeLike.startsWith("%")) {
		    wtypeLike = "%" + wtypeLike;
		}
		if (!wtypeLike.endsWith("%")) {
		   wtypeLike = wtypeLike + "%";
		}
	    }
	return wtypeLike;
    }

    public List<String> getWtypes(){
	return wtypes;
    }


    public Integer getVer(){
        return ver;
    }

    public Integer getVerGreaterThanOrEqual(){
        return verGreaterThanOrEqual;
    }

    public Integer getVerLessThanOrEqual(){
	return verLessThanOrEqual;
    }

    public List<Integer> getVers(){
	return vers;
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


    public String getDelFlag(){
        return delFlag;
    }

    public String getDelFlagLike(){
	    if (delFlagLike != null && delFlagLike.trim().length() > 0) {
		if (!delFlagLike.startsWith("%")) {
		    delFlagLike = "%" + delFlagLike;
		}
		if (!delFlagLike.endsWith("%")) {
		   delFlagLike = delFlagLike + "%";
		}
	    }
	return delFlagLike;
    }

    public List<String> getDelFlags(){
	return delFlags;
    }


 

    public void setDataSetCode(String dataSetCode){
        this.dataSetCode = dataSetCode;
    }

    public void setDataSetCodeLike( String dataSetCodeLike){
	this.dataSetCodeLike = dataSetCodeLike;
    }

    public void setDataSetCodes(List<String> dataSetCodes){
        this.dataSetCodes = dataSetCodes;
    }


    public void setDataSetName(String dataSetName){
        this.dataSetName = dataSetName;
    }

    public void setDataSetNameLike( String dataSetNameLike){
	this.dataSetNameLike = dataSetNameLike;
    }

    public void setDataSetNames(List<String> dataSetNames){
        this.dataSetNames = dataSetNames;
    }


    public void setDataSetDesc(String dataSetDesc){
        this.dataSetDesc = dataSetDesc;
    }

    public void setDataSetDescLike( String dataSetDescLike){
	this.dataSetDescLike = dataSetDescLike;
    }

    public void setDataSetDescs(List<String> dataSetDescs){
        this.dataSetDescs = dataSetDescs;
    }


    public void setRuleJson(String ruleJson){
        this.ruleJson = ruleJson;
    }

    public void setRuleJsonLike( String ruleJsonLike){
	this.ruleJsonLike = ruleJsonLike;
    }

    public void setRuleJsons(List<String> ruleJsons){
        this.ruleJsons = ruleJsons;
    }


    public void setTableName(String tableName){
        this.tableName = tableName;
    }

    public void setTableNameLike( String tableNameLike){
	this.tableNameLike = tableNameLike;
    }

    public void setTableNames(List<String> tableNames){
        this.tableNames = tableNames;
    }


    public void setDataTableName(String dataTableName){
        this.dataTableName = dataTableName;
    }

    public void setDataTableNameLike( String dataTableNameLike){
	this.dataTableNameLike = dataTableNameLike;
    }

    public void setDataTableNames(List<String> dataTableNames){
        this.dataTableNames = dataTableNames;
    }


    public void setWtype(String wtype){
        this.wtype = wtype;
    }

    public void setWtypeLike( String wtypeLike){
	this.wtypeLike = wtypeLike;
    }

    public void setWtypes(List<String> wtypes){
        this.wtypes = wtypes;
    }


    public void setVer(Integer ver){
        this.ver = ver;
    }

    public void setVerGreaterThanOrEqual(Integer verGreaterThanOrEqual){
        this.verGreaterThanOrEqual = verGreaterThanOrEqual;
    }

    public void setVerLessThanOrEqual(Integer verLessThanOrEqual){
	this.verLessThanOrEqual = verLessThanOrEqual;
    }

    public void setVers(List<Integer> vers){
        this.vers = vers;
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


    public void setDelFlag(String delFlag){
        this.delFlag = delFlag;
    }

    public void setDelFlagLike( String delFlagLike){
	this.delFlagLike = delFlagLike;
    }

    public void setDelFlags(List<String> delFlags){
        this.delFlags = delFlags;
    }




    public DepBaseWdataSetQuery dataSetCode(String dataSetCode){
	if (dataSetCode == null) {
	    throw new RuntimeException("dataSetCode is null");
        }         
	this.dataSetCode = dataSetCode;
	return this;
    }

    public DepBaseWdataSetQuery dataSetCodeLike( String dataSetCodeLike){
        if (dataSetCodeLike == null) {
            throw new RuntimeException("dataSetCode is null");
        }
        this.dataSetCodeLike = dataSetCodeLike;
        return this;
    }

    public DepBaseWdataSetQuery dataSetCodes(List<String> dataSetCodes){
        if (dataSetCodes == null) {
            throw new RuntimeException("dataSetCodes is empty ");
        }
        this.dataSetCodes = dataSetCodes;
        return this;
    }


    public DepBaseWdataSetQuery dataSetName(String dataSetName){
	if (dataSetName == null) {
	    throw new RuntimeException("dataSetName is null");
        }         
	this.dataSetName = dataSetName;
	return this;
    }

    public DepBaseWdataSetQuery dataSetNameLike( String dataSetNameLike){
        if (dataSetNameLike == null) {
            throw new RuntimeException("dataSetName is null");
        }
        this.dataSetNameLike = dataSetNameLike;
        return this;
    }

    public DepBaseWdataSetQuery dataSetNames(List<String> dataSetNames){
        if (dataSetNames == null) {
            throw new RuntimeException("dataSetNames is empty ");
        }
        this.dataSetNames = dataSetNames;
        return this;
    }


    public DepBaseWdataSetQuery dataSetDesc(String dataSetDesc){
	if (dataSetDesc == null) {
	    throw new RuntimeException("dataSetDesc is null");
        }         
	this.dataSetDesc = dataSetDesc;
	return this;
    }

    public DepBaseWdataSetQuery dataSetDescLike( String dataSetDescLike){
        if (dataSetDescLike == null) {
            throw new RuntimeException("dataSetDesc is null");
        }
        this.dataSetDescLike = dataSetDescLike;
        return this;
    }

    public DepBaseWdataSetQuery dataSetDescs(List<String> dataSetDescs){
        if (dataSetDescs == null) {
            throw new RuntimeException("dataSetDescs is empty ");
        }
        this.dataSetDescs = dataSetDescs;
        return this;
    }


    public DepBaseWdataSetQuery ruleJson(String ruleJson){
	if (ruleJson == null) {
	    throw new RuntimeException("ruleJson is null");
        }         
	this.ruleJson = ruleJson;
	return this;
    }

    public DepBaseWdataSetQuery ruleJsonLike( String ruleJsonLike){
        if (ruleJsonLike == null) {
            throw new RuntimeException("ruleJson is null");
        }
        this.ruleJsonLike = ruleJsonLike;
        return this;
    }

    public DepBaseWdataSetQuery ruleJsons(List<String> ruleJsons){
        if (ruleJsons == null) {
            throw new RuntimeException("ruleJsons is empty ");
        }
        this.ruleJsons = ruleJsons;
        return this;
    }


    public DepBaseWdataSetQuery tableName(String tableName){
	if (tableName == null) {
	    throw new RuntimeException("tableName is null");
        }         
	this.tableName = tableName;
	return this;
    }

    public DepBaseWdataSetQuery tableNameLike( String tableNameLike){
        if (tableNameLike == null) {
            throw new RuntimeException("tableName is null");
        }
        this.tableNameLike = tableNameLike;
        return this;
    }

    public DepBaseWdataSetQuery tableNames(List<String> tableNames){
        if (tableNames == null) {
            throw new RuntimeException("tableNames is empty ");
        }
        this.tableNames = tableNames;
        return this;
    }


    public DepBaseWdataSetQuery dataTableName(String dataTableName){
	if (dataTableName == null) {
	    throw new RuntimeException("dataTableName is null");
        }         
	this.dataTableName = dataTableName;
	return this;
    }

    public DepBaseWdataSetQuery dataTableNameLike( String dataTableNameLike){
        if (dataTableNameLike == null) {
            throw new RuntimeException("dataTableName is null");
        }
        this.dataTableNameLike = dataTableNameLike;
        return this;
    }

    public DepBaseWdataSetQuery dataTableNames(List<String> dataTableNames){
        if (dataTableNames == null) {
            throw new RuntimeException("dataTableNames is empty ");
        }
        this.dataTableNames = dataTableNames;
        return this;
    }


    public DepBaseWdataSetQuery wtype(String wtype){
	if (wtype == null) {
	    throw new RuntimeException("wtype is null");
        }         
	this.wtype = wtype;
	return this;
    }

    public DepBaseWdataSetQuery wtypeLike( String wtypeLike){
        if (wtypeLike == null) {
            throw new RuntimeException("wtype is null");
        }
        this.wtypeLike = wtypeLike;
        return this;
    }

    public DepBaseWdataSetQuery wtypes(List<String> wtypes){
        if (wtypes == null) {
            throw new RuntimeException("wtypes is empty ");
        }
        this.wtypes = wtypes;
        return this;
    }


    public DepBaseWdataSetQuery ver(Integer ver){
	if (ver == null) {
            throw new RuntimeException("ver is null");
        }         
	this.ver = ver;
	return this;
    }

    public DepBaseWdataSetQuery verGreaterThanOrEqual(Integer verGreaterThanOrEqual){
	if (verGreaterThanOrEqual == null) {
	    throw new RuntimeException("ver is null");
        }         
	this.verGreaterThanOrEqual = verGreaterThanOrEqual;
        return this;
    }

    public DepBaseWdataSetQuery verLessThanOrEqual(Integer verLessThanOrEqual){
        if (verLessThanOrEqual == null) {
            throw new RuntimeException("ver is null");
        }
        this.verLessThanOrEqual = verLessThanOrEqual;
        return this;
    }

    public DepBaseWdataSetQuery vers(List<Integer> vers){
        if (vers == null) {
            throw new RuntimeException("vers is empty ");
        }
        this.vers = vers;
        return this;
    }


    public DepBaseWdataSetQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public DepBaseWdataSetQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public DepBaseWdataSetQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public DepBaseWdataSetQuery createDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual){
	if (createDatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDatetime is null");
        }         
	this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
        return this;
    }

    public DepBaseWdataSetQuery createDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual){
        if (createDatetimeLessThanOrEqual == null) {
            throw new RuntimeException("createDatetime is null");
        }
        this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
        return this;
    }



    public DepBaseWdataSetQuery modifier(String modifier){
	if (modifier == null) {
	    throw new RuntimeException("modifier is null");
        }         
	this.modifier = modifier;
	return this;
    }

    public DepBaseWdataSetQuery modifierLike( String modifierLike){
        if (modifierLike == null) {
            throw new RuntimeException("modifier is null");
        }
        this.modifierLike = modifierLike;
        return this;
    }

    public DepBaseWdataSetQuery modifiers(List<String> modifiers){
        if (modifiers == null) {
            throw new RuntimeException("modifiers is empty ");
        }
        this.modifiers = modifiers;
        return this;
    }



    public DepBaseWdataSetQuery modifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual){
	if (modifyDatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("modifyDatetime is null");
        }         
	this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
        return this;
    }

    public DepBaseWdataSetQuery modifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual){
        if (modifyDatetimeLessThanOrEqual == null) {
            throw new RuntimeException("modifyDatetime is null");
        }
        this.modifyDatetimeLessThanOrEqual = modifyDatetimeLessThanOrEqual;
        return this;
    }



    public DepBaseWdataSetQuery delFlag(String delFlag){
	if (delFlag == null) {
	    throw new RuntimeException("delFlag is null");
        }         
	this.delFlag = delFlag;
	return this;
    }

    public DepBaseWdataSetQuery delFlagLike( String delFlagLike){
        if (delFlagLike == null) {
            throw new RuntimeException("delFlag is null");
        }
        this.delFlagLike = delFlagLike;
        return this;
    }

    public DepBaseWdataSetQuery delFlags(List<String> delFlags){
        if (delFlags == null) {
            throw new RuntimeException("delFlags is empty ");
        }
        this.delFlags = delFlags;
        return this;
    }



    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("dataSetCode".equals(sortColumn)) {
                orderBy = "E.DATASET_CODE_" + a_x;
            } 

            if ("dataSetName".equals(sortColumn)) {
                orderBy = "E.DATASET_NAME_" + a_x;
            } 

            if ("dataSetDesc".equals(sortColumn)) {
                orderBy = "E.DATASET_DESC_" + a_x;
            } 

            if ("ruleJson".equals(sortColumn)) {
                orderBy = "E.RULEJSON_" + a_x;
            } 

            if ("tableName".equals(sortColumn)) {
                orderBy = "E.TABLE_NAME_" + a_x;
            } 

            if ("dataTableName".equals(sortColumn)) {
                orderBy = "E.DATATABLE_NAME_" + a_x;
            } 

            if ("wtype".equals(sortColumn)) {
                orderBy = "E.WTYPE_" + a_x;
            } 

            if ("ver".equals(sortColumn)) {
                orderBy = "E.VER_" + a_x;
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

            if ("delFlag".equals(sortColumn)) {
                orderBy = "E.DELFLAG_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("dataSetCode", "DATASET_CODE_");
        addColumn("dataSetName", "DATASET_NAME_");
        addColumn("dataSetDesc", "DATASET_DESC_");
        addColumn("ruleJson", "RULEJSON_");
        addColumn("tableName", "TABLE_NAME_");
        addColumn("dataTableName", "DATATABLE_NAME_");
        addColumn("wtype", "WTYPE_");
        addColumn("ver", "VER_");
        addColumn("creator", "CREATOR_");
        addColumn("createDatetime", "CREATEDATETIME_");
        addColumn("modifier", "MODIFIER_");
        addColumn("modifyDatetime", "MODIFYDATETIME_");
        addColumn("delFlag", "DELFLAG_");
    }

}