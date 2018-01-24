package com.glaf.dep.base.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DepBaseWdataSetParamQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
  	protected Long wdataSetId;
  	protected Long wdataSetIdGreaterThanOrEqual;
  	protected Long wdataSetIdLessThanOrEqual;
  	protected List<Long> wdataSetIds;
  	protected String code;
  	protected String codeLike;
  	protected List<String> codes;
  	protected String name;
  	protected String nameLike;
  	protected List<String> names;
  	protected String dType;
  	protected String dTypeLike;
  	protected List<String> dTypes;
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

    public DepBaseWdataSetParamQuery() {

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


    public String getDType(){
        return dType;
    }

    public String getDTypeLike(){
	    if (dTypeLike != null && dTypeLike.trim().length() > 0) {
		if (!dTypeLike.startsWith("%")) {
		    dTypeLike = "%" + dTypeLike;
		}
		if (!dTypeLike.endsWith("%")) {
		   dTypeLike = dTypeLike + "%";
		}
	    }
	return dTypeLike;
    }

    public List<String> getDTypes(){
	return dTypes;
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


    public void setDType(String dType){
        this.dType = dType;
    }

    public void setDTypeLike( String dTypeLike){
	this.dTypeLike = dTypeLike;
    }

    public void setDTypes(List<String> dTypes){
        this.dTypes = dTypes;
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




    public DepBaseWdataSetParamQuery wdataSetId(Long wdataSetId){
	if (wdataSetId == null) {
            throw new RuntimeException("wdataSetId is null");
        }         
	this.wdataSetId = wdataSetId;
	return this;
    }

    public DepBaseWdataSetParamQuery wdataSetIdGreaterThanOrEqual(Long wdataSetIdGreaterThanOrEqual){
	if (wdataSetIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("wdataSetId is null");
        }         
	this.wdataSetIdGreaterThanOrEqual = wdataSetIdGreaterThanOrEqual;
        return this;
    }

    public DepBaseWdataSetParamQuery wdataSetIdLessThanOrEqual(Long wdataSetIdLessThanOrEqual){
        if (wdataSetIdLessThanOrEqual == null) {
            throw new RuntimeException("wdataSetId is null");
        }
        this.wdataSetIdLessThanOrEqual = wdataSetIdLessThanOrEqual;
        return this;
    }

    public DepBaseWdataSetParamQuery wdataSetIds(List<Long> wdataSetIds){
        if (wdataSetIds == null) {
            throw new RuntimeException("wdataSetIds is empty ");
        }
        this.wdataSetIds = wdataSetIds;
        return this;
    }


    public DepBaseWdataSetParamQuery code(String code){
	if (code == null) {
	    throw new RuntimeException("code is null");
        }         
	this.code = code;
	return this;
    }

    public DepBaseWdataSetParamQuery codeLike( String codeLike){
        if (codeLike == null) {
            throw new RuntimeException("code is null");
        }
        this.codeLike = codeLike;
        return this;
    }

    public DepBaseWdataSetParamQuery codes(List<String> codes){
        if (codes == null) {
            throw new RuntimeException("codes is empty ");
        }
        this.codes = codes;
        return this;
    }


    public DepBaseWdataSetParamQuery name(String name){
	if (name == null) {
	    throw new RuntimeException("name is null");
        }         
	this.name = name;
	return this;
    }

    public DepBaseWdataSetParamQuery nameLike( String nameLike){
        if (nameLike == null) {
            throw new RuntimeException("name is null");
        }
        this.nameLike = nameLike;
        return this;
    }

    public DepBaseWdataSetParamQuery names(List<String> names){
        if (names == null) {
            throw new RuntimeException("names is empty ");
        }
        this.names = names;
        return this;
    }


    public DepBaseWdataSetParamQuery dType(String dType){
	if (dType == null) {
	    throw new RuntimeException("dType is null");
        }         
	this.dType = dType;
	return this;
    }

    public DepBaseWdataSetParamQuery dTypeLike( String dTypeLike){
        if (dTypeLike == null) {
            throw new RuntimeException("dType is null");
        }
        this.dTypeLike = dTypeLike;
        return this;
    }

    public DepBaseWdataSetParamQuery dTypes(List<String> dTypes){
        if (dTypes == null) {
            throw new RuntimeException("dTypes is empty ");
        }
        this.dTypes = dTypes;
        return this;
    }


    public DepBaseWdataSetParamQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public DepBaseWdataSetParamQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public DepBaseWdataSetParamQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public DepBaseWdataSetParamQuery createDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual){
	if (createDatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDatetime is null");
        }         
	this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
        return this;
    }

    public DepBaseWdataSetParamQuery createDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual){
        if (createDatetimeLessThanOrEqual == null) {
            throw new RuntimeException("createDatetime is null");
        }
        this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
        return this;
    }



    public DepBaseWdataSetParamQuery modifier(String modifier){
	if (modifier == null) {
	    throw new RuntimeException("modifier is null");
        }         
	this.modifier = modifier;
	return this;
    }

    public DepBaseWdataSetParamQuery modifierLike( String modifierLike){
        if (modifierLike == null) {
            throw new RuntimeException("modifier is null");
        }
        this.modifierLike = modifierLike;
        return this;
    }

    public DepBaseWdataSetParamQuery modifiers(List<String> modifiers){
        if (modifiers == null) {
            throw new RuntimeException("modifiers is empty ");
        }
        this.modifiers = modifiers;
        return this;
    }



    public DepBaseWdataSetParamQuery modifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual){
	if (modifyDatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("modifyDatetime is null");
        }         
	this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
        return this;
    }

    public DepBaseWdataSetParamQuery modifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual){
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

            if ("code".equals(sortColumn)) {
                orderBy = "E.CODE_" + a_x;
            } 

            if ("name".equals(sortColumn)) {
                orderBy = "E.NAME_" + a_x;
            } 

            if ("dType".equals(sortColumn)) {
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
        addColumn("wdataSetId", "WDATASET_ID_");
        addColumn("code", "CODE_");
        addColumn("name", "NAME_");
        addColumn("dType", "DTYPE_");
        addColumn("creator", "CREATOR_");
        addColumn("createDatetime", "CREATEDATETIME_");
        addColumn("modifier", "MODIFIER_");
        addColumn("modifyDatetime", "MODIFYDATETIME_");
    }

}