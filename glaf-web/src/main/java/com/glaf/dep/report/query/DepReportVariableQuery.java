package com.glaf.dep.report.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DepReportVariableQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
  	protected Long repTemplateId;
  	protected Long repTemplateIdGreaterThanOrEqual;
  	protected Long repTemplateIdLessThanOrEqual;
  	protected List<Long> repTemplateIds;
  	protected String name;
  	protected String nameLike;
  	protected List<String> names;
  	protected String dtype;
  	protected String dtypeLike;
  	protected List<String> dtypes;
  	protected String defaultVal;
  	protected String defaultValLike;
  	protected List<String> defaultVals;
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

    public DepReportVariableQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public Long getRepTemplateId(){
        return repTemplateId;
    }

    public Long getRepTemplateIdGreaterThanOrEqual(){
        return repTemplateIdGreaterThanOrEqual;
    }

    public Long getRepTemplateIdLessThanOrEqual(){
	return repTemplateIdLessThanOrEqual;
    }

    public List<Long> getRepTemplateIds(){
	return repTemplateIds;
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


 

    public void setRepTemplateId(Long repTemplateId){
        this.repTemplateId = repTemplateId;
    }

    public void setRepTemplateIdGreaterThanOrEqual(Long repTemplateIdGreaterThanOrEqual){
        this.repTemplateIdGreaterThanOrEqual = repTemplateIdGreaterThanOrEqual;
    }

    public void setRepTemplateIdLessThanOrEqual(Long repTemplateIdLessThanOrEqual){
	this.repTemplateIdLessThanOrEqual = repTemplateIdLessThanOrEqual;
    }

    public void setRepTemplateIds(List<Long> repTemplateIds){
        this.repTemplateIds = repTemplateIds;
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


    public void setDtype(String dtype){
        this.dtype = dtype;
    }

    public void setDtypeLike( String dtypeLike){
	this.dtypeLike = dtypeLike;
    }

    public void setDtypes(List<String> dtypes){
        this.dtypes = dtypes;
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




    public DepReportVariableQuery repTemplateId(Long repTemplateId){
	if (repTemplateId == null) {
            throw new RuntimeException("repTemplateId is null");
        }         
	this.repTemplateId = repTemplateId;
	return this;
    }

    public DepReportVariableQuery repTemplateIdGreaterThanOrEqual(Long repTemplateIdGreaterThanOrEqual){
	if (repTemplateIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("repTemplateId is null");
        }         
	this.repTemplateIdGreaterThanOrEqual = repTemplateIdGreaterThanOrEqual;
        return this;
    }

    public DepReportVariableQuery repTemplateIdLessThanOrEqual(Long repTemplateIdLessThanOrEqual){
        if (repTemplateIdLessThanOrEqual == null) {
            throw new RuntimeException("repTemplateId is null");
        }
        this.repTemplateIdLessThanOrEqual = repTemplateIdLessThanOrEqual;
        return this;
    }

    public DepReportVariableQuery repTemplateIds(List<Long> repTemplateIds){
        if (repTemplateIds == null) {
            throw new RuntimeException("repTemplateIds is empty ");
        }
        this.repTemplateIds = repTemplateIds;
        return this;
    }


    public DepReportVariableQuery name(String name){
	if (name == null) {
	    throw new RuntimeException("name is null");
        }         
	this.name = name;
	return this;
    }

    public DepReportVariableQuery nameLike( String nameLike){
        if (nameLike == null) {
            throw new RuntimeException("name is null");
        }
        this.nameLike = nameLike;
        return this;
    }

    public DepReportVariableQuery names(List<String> names){
        if (names == null) {
            throw new RuntimeException("names is empty ");
        }
        this.names = names;
        return this;
    }


    public DepReportVariableQuery dtype(String dtype){
	if (dtype == null) {
	    throw new RuntimeException("dtype is null");
        }         
	this.dtype = dtype;
	return this;
    }

    public DepReportVariableQuery dtypeLike( String dtypeLike){
        if (dtypeLike == null) {
            throw new RuntimeException("dtype is null");
        }
        this.dtypeLike = dtypeLike;
        return this;
    }

    public DepReportVariableQuery dtypes(List<String> dtypes){
        if (dtypes == null) {
            throw new RuntimeException("dtypes is empty ");
        }
        this.dtypes = dtypes;
        return this;
    }


    public DepReportVariableQuery defaultVal(String defaultVal){
	if (defaultVal == null) {
	    throw new RuntimeException("defaultVal is null");
        }         
	this.defaultVal = defaultVal;
	return this;
    }

    public DepReportVariableQuery defaultValLike( String defaultValLike){
        if (defaultValLike == null) {
            throw new RuntimeException("defaultVal is null");
        }
        this.defaultValLike = defaultValLike;
        return this;
    }

    public DepReportVariableQuery defaultVals(List<String> defaultVals){
        if (defaultVals == null) {
            throw new RuntimeException("defaultVals is empty ");
        }
        this.defaultVals = defaultVals;
        return this;
    }


    public DepReportVariableQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public DepReportVariableQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public DepReportVariableQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public DepReportVariableQuery createDateTimeGreaterThanOrEqual(Date createDateTimeGreaterThanOrEqual){
	if (createDateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDateTime is null");
        }         
	this.createDateTimeGreaterThanOrEqual = createDateTimeGreaterThanOrEqual;
        return this;
    }

    public DepReportVariableQuery createDateTimeLessThanOrEqual(Date createDateTimeLessThanOrEqual){
        if (createDateTimeLessThanOrEqual == null) {
            throw new RuntimeException("createDateTime is null");
        }
        this.createDateTimeLessThanOrEqual = createDateTimeLessThanOrEqual;
        return this;
    }



    public DepReportVariableQuery modifier(String modifier){
	if (modifier == null) {
	    throw new RuntimeException("modifier is null");
        }         
	this.modifier = modifier;
	return this;
    }

    public DepReportVariableQuery modifierLike( String modifierLike){
        if (modifierLike == null) {
            throw new RuntimeException("modifier is null");
        }
        this.modifierLike = modifierLike;
        return this;
    }

    public DepReportVariableQuery modifiers(List<String> modifiers){
        if (modifiers == null) {
            throw new RuntimeException("modifiers is empty ");
        }
        this.modifiers = modifiers;
        return this;
    }



    public DepReportVariableQuery modifyDateTimeGreaterThanOrEqual(Date modifyDateTimeGreaterThanOrEqual){
	if (modifyDateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("modifyDateTime is null");
        }         
	this.modifyDateTimeGreaterThanOrEqual = modifyDateTimeGreaterThanOrEqual;
        return this;
    }

    public DepReportVariableQuery modifyDateTimeLessThanOrEqual(Date modifyDateTimeLessThanOrEqual){
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

            if ("repTemplateId".equals(sortColumn)) {
                orderBy = "E.REPTEMPLATE_ID_" + a_x;
            } 

            if ("name".equals(sortColumn)) {
                orderBy = "E.NAME_" + a_x;
            } 

            if ("dtype".equals(sortColumn)) {
                orderBy = "E.DTYPE_" + a_x;
            } 

            if ("defaultVal".equals(sortColumn)) {
                orderBy = "E.DEFAULTVAL_" + a_x;
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
        addColumn("repTemplateId", "REPTEMPLATE_ID_");
        addColumn("name", "NAME_");
        addColumn("dtype", "DTYPE_");
        addColumn("defaultVal", "DEFAULTVAL_");
        addColumn("creator", "CREATOR_");
        addColumn("createDateTime", "CREATEDATETIME_");
        addColumn("modifier", "MODIFIER_");
        addColumn("modifyDateTime", "MODIFYDATETIME_");
    }

}