package com.glaf.report.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ReportTmpColQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String dataSetId;
  	protected String dataSetIdLike;
  	protected List<String> dataSetIds;
  	protected String code;
  	protected String codeLike;
  	protected List<String> codes;
  	protected String name;
  	protected String nameLike;
  	protected List<String> names;
  	protected String title;
  	protected String titleLike;
  	protected List<String> titles;
  	protected String dtype;
  	protected String dtypeLike;
  	protected List<String> dtypes;
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

    public ReportTmpColQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getDataSetId(){
        return dataSetId;
    }

    public String getDataSetIdLike(){
	    if (dataSetIdLike != null && dataSetIdLike.trim().length() > 0) {
		if (!dataSetIdLike.startsWith("%")) {
		    dataSetIdLike = "%" + dataSetIdLike;
		}
		if (!dataSetIdLike.endsWith("%")) {
		   dataSetIdLike = dataSetIdLike + "%";
		}
	    }
	return dataSetIdLike;
    }

    public List<String> getDataSetIds(){
	return dataSetIds;
    }


    public String getCode(){
        return code;
    }

    public String getCodeLike(){
	    if (codeLike != null && codeLike.trim().length() > 0) {
		if (!codeLike.startsWith("%")) {
		    codeLike = "%" + codeLike;
		}
		if (!codeLike.endsWith("%")) {
		   codeLike = codeLike + "%";
		}
	    }
	return codeLike;
    }

    public List<String> getCodes(){
	return codes;
    }


    public String getName(){
        return name;
    }

    public String getNameLike(){
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

    public List<String> getNames(){
	return names;
    }


    public String getTitle(){
        return title;
    }

    public String getTitleLike(){
	    if (titleLike != null && titleLike.trim().length() > 0) {
		if (!titleLike.startsWith("%")) {
		    titleLike = "%" + titleLike;
		}
		if (!titleLike.endsWith("%")) {
		   titleLike = titleLike + "%";
		}
	    }
	return titleLike;
    }

    public List<String> getTitles(){
	return titles;
    }


    public String getDtype(){
        return dtype;
    }

    public String getDtypeLike(){
	    if (dtypeLike != null && dtypeLike.trim().length() > 0) {
		if (!dtypeLike.startsWith("%")) {
		    dtypeLike = "%" + dtypeLike;
		}
		if (!dtypeLike.endsWith("%")) {
		   dtypeLike = dtypeLike + "%";
		}
	    }
	return dtypeLike;
    }

    public List<String> getDtypes(){
	return dtypes;
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


 

    public void setDataSetId(String dataSetId){
        this.dataSetId = dataSetId;
    }

    public void setDataSetIdLike( String dataSetIdLike){
	this.dataSetIdLike = dataSetIdLike;
    }

    public void setDataSetIds(List<String> dataSetIds){
        this.dataSetIds = dataSetIds;
    }


    public void setCode(String code){
        this.code = code;
    }

    public void setCodeLike( String codeLike){
	this.codeLike = codeLike;
    }

    public void setCodes(List<String> codes){
        this.codes = codes;
    }


    public void setName(String name){
        this.name = name;
    }

    public void setNameLike( String nameLike){
	this.nameLike = nameLike;
    }

    public void setNames(List<String> names){
        this.names = names;
    }


    public void setTitle(String title){
        this.title = title;
    }

    public void setTitleLike( String titleLike){
	this.titleLike = titleLike;
    }

    public void setTitles(List<String> titles){
        this.titles = titles;
    }


    public void setDtype(String dtype){
        this.dtype = dtype;
    }

    public void setDtypeLike( String dtypeLike){
	this.dtypeLike = dtypeLike;
    }

    public void setDtypes(List<String> dtypes){
        this.dtypes = dtypes;
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




    public ReportTmpColQuery dataSetId(String dataSetId){
	if (dataSetId == null) {
	    throw new RuntimeException("dataSetId is null");
        }         
	this.dataSetId = dataSetId;
	return this;
    }

    public ReportTmpColQuery dataSetIdLike( String dataSetIdLike){
        if (dataSetIdLike == null) {
            throw new RuntimeException("dataSetId is null");
        }
        this.dataSetIdLike = dataSetIdLike;
        return this;
    }

    public ReportTmpColQuery dataSetIds(List<String> dataSetIds){
        if (dataSetIds == null) {
            throw new RuntimeException("dataSetIds is empty ");
        }
        this.dataSetIds = dataSetIds;
        return this;
    }


    public ReportTmpColQuery code(String code){
	if (code == null) {
	    throw new RuntimeException("code is null");
        }         
	this.code = code;
	return this;
    }

    public ReportTmpColQuery codeLike( String codeLike){
        if (codeLike == null) {
            throw new RuntimeException("code is null");
        }
        this.codeLike = codeLike;
        return this;
    }

    public ReportTmpColQuery codes(List<String> codes){
        if (codes == null) {
            throw new RuntimeException("codes is empty ");
        }
        this.codes = codes;
        return this;
    }


    public ReportTmpColQuery name(String name){
	if (name == null) {
	    throw new RuntimeException("name is null");
        }         
	this.name = name;
	return this;
    }

    public ReportTmpColQuery nameLike( String nameLike){
        if (nameLike == null) {
            throw new RuntimeException("name is null");
        }
        this.nameLike = nameLike;
        return this;
    }

    public ReportTmpColQuery names(List<String> names){
        if (names == null) {
            throw new RuntimeException("names is empty ");
        }
        this.names = names;
        return this;
    }


    public ReportTmpColQuery title(String title){
	if (title == null) {
	    throw new RuntimeException("title is null");
        }         
	this.title = title;
	return this;
    }

    public ReportTmpColQuery titleLike( String titleLike){
        if (titleLike == null) {
            throw new RuntimeException("title is null");
        }
        this.titleLike = titleLike;
        return this;
    }

    public ReportTmpColQuery titles(List<String> titles){
        if (titles == null) {
            throw new RuntimeException("titles is empty ");
        }
        this.titles = titles;
        return this;
    }


    public ReportTmpColQuery dtype(String dtype){
	if (dtype == null) {
	    throw new RuntimeException("dtype is null");
        }         
	this.dtype = dtype;
	return this;
    }

    public ReportTmpColQuery dtypeLike( String dtypeLike){
        if (dtypeLike == null) {
            throw new RuntimeException("dtype is null");
        }
        this.dtypeLike = dtypeLike;
        return this;
    }

    public ReportTmpColQuery dtypes(List<String> dtypes){
        if (dtypes == null) {
            throw new RuntimeException("dtypes is empty ");
        }
        this.dtypes = dtypes;
        return this;
    }


    public ReportTmpColQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public ReportTmpColQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public ReportTmpColQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public ReportTmpColQuery createDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual){
	if (createDatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDatetime is null");
        }         
	this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
        return this;
    }

    public ReportTmpColQuery createDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual){
        if (createDatetimeLessThanOrEqual == null) {
            throw new RuntimeException("createDatetime is null");
        }
        this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
        return this;
    }



    public ReportTmpColQuery modifier(String modifier){
	if (modifier == null) {
	    throw new RuntimeException("modifier is null");
        }         
	this.modifier = modifier;
	return this;
    }

    public ReportTmpColQuery modifierLike( String modifierLike){
        if (modifierLike == null) {
            throw new RuntimeException("modifier is null");
        }
        this.modifierLike = modifierLike;
        return this;
    }

    public ReportTmpColQuery modifiers(List<String> modifiers){
        if (modifiers == null) {
            throw new RuntimeException("modifiers is empty ");
        }
        this.modifiers = modifiers;
        return this;
    }



    public ReportTmpColQuery modifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual){
	if (modifyDatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("modifyDatetime is null");
        }         
	this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
        return this;
    }

    public ReportTmpColQuery modifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual){
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

            if ("dataSetId".equals(sortColumn)) {
                orderBy = "E.DATASET_ID_" + a_x;
            } 

            if ("code".equals(sortColumn)) {
                orderBy = "E.CODE_" + a_x;
            } 

            if ("name".equals(sortColumn)) {
                orderBy = "E.NAME_" + a_x;
            } 

            if ("title".equals(sortColumn)) {
                orderBy = "E.TITLE_" + a_x;
            } 

            if ("dtype".equals(sortColumn)) {
                orderBy = "E.DTYPE_" + a_x;
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
        addColumn("dataSetId", "DATASET_ID_");
        addColumn("code", "CODE_");
        addColumn("name", "NAME_");
        addColumn("title", "TITLE_");
        addColumn("dtype", "DTYPE_");
        addColumn("creator", "CREATOR_");
        addColumn("createDatetime", "CREATEDATETIME_");
        addColumn("modifier", "MODIFIER_");
        addColumn("modifyDatetime", "MODIFYDATETIME_");
    }

}