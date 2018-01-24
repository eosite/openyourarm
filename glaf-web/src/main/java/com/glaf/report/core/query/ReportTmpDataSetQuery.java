package com.glaf.report.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ReportTmpDataSetQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String templateId;
  	protected String templateIdLike;
  	protected List<String> templateIds;
  	protected String code;
  	protected String codeLike;
  	protected List<String> codes;
  	protected String name;
  	protected String nameLike;
  	protected List<String> names;
  	protected String title;
  	protected String titleLike;
  	protected List<String> titles;
  	protected String type;
  	protected String typeLike;
  	protected List<String> types;
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

    public ReportTmpDataSetQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getTemplateId(){
        return templateId;
    }

    public String getTemplateIdLike(){
	    if (templateIdLike != null && templateIdLike.trim().length() > 0) {
		if (!templateIdLike.startsWith("%")) {
		    templateIdLike = "%" + templateIdLike;
		}
		if (!templateIdLike.endsWith("%")) {
		   templateIdLike = templateIdLike + "%";
		}
	    }
	return templateIdLike;
    }

    public List<String> getTemplateIds(){
	return templateIds;
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


    public String getType(){
        return type;
    }

    public String getTypeLike(){
	    if (typeLike != null && typeLike.trim().length() > 0) {
		if (!typeLike.startsWith("%")) {
		    typeLike = "%" + typeLike;
		}
		if (!typeLike.endsWith("%")) {
		   typeLike = typeLike + "%";
		}
	    }
	return typeLike;
    }

    public List<String> getTypes(){
	return types;
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


 

    public void setTemplateId(String templateId){
        this.templateId = templateId;
    }

    public void setTemplateIdLike( String templateIdLike){
	this.templateIdLike = templateIdLike;
    }

    public void setTemplateIds(List<String> templateIds){
        this.templateIds = templateIds;
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


    public void setType(String type){
        this.type = type;
    }

    public void setTypeLike( String typeLike){
	this.typeLike = typeLike;
    }

    public void setTypes(List<String> types){
        this.types = types;
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




    public ReportTmpDataSetQuery templateId(String templateId){
	if (templateId == null) {
	    throw new RuntimeException("templateId is null");
        }         
	this.templateId = templateId;
	return this;
    }

    public ReportTmpDataSetQuery templateIdLike( String templateIdLike){
        if (templateIdLike == null) {
            throw new RuntimeException("templateId is null");
        }
        this.templateIdLike = templateIdLike;
        return this;
    }

    public ReportTmpDataSetQuery templateIds(List<String> templateIds){
        if (templateIds == null) {
            throw new RuntimeException("templateIds is empty ");
        }
        this.templateIds = templateIds;
        return this;
    }


    public ReportTmpDataSetQuery code(String code){
	if (code == null) {
	    throw new RuntimeException("code is null");
        }         
	this.code = code;
	return this;
    }

    public ReportTmpDataSetQuery codeLike( String codeLike){
        if (codeLike == null) {
            throw new RuntimeException("code is null");
        }
        this.codeLike = codeLike;
        return this;
    }

    public ReportTmpDataSetQuery codes(List<String> codes){
        if (codes == null) {
            throw new RuntimeException("codes is empty ");
        }
        this.codes = codes;
        return this;
    }


    public ReportTmpDataSetQuery name(String name){
	if (name == null) {
	    throw new RuntimeException("name is null");
        }         
	this.name = name;
	return this;
    }

    public ReportTmpDataSetQuery nameLike( String nameLike){
        if (nameLike == null) {
            throw new RuntimeException("name is null");
        }
        this.nameLike = nameLike;
        return this;
    }

    public ReportTmpDataSetQuery names(List<String> names){
        if (names == null) {
            throw new RuntimeException("names is empty ");
        }
        this.names = names;
        return this;
    }


    public ReportTmpDataSetQuery title(String title){
	if (title == null) {
	    throw new RuntimeException("title is null");
        }         
	this.title = title;
	return this;
    }

    public ReportTmpDataSetQuery titleLike( String titleLike){
        if (titleLike == null) {
            throw new RuntimeException("title is null");
        }
        this.titleLike = titleLike;
        return this;
    }

    public ReportTmpDataSetQuery titles(List<String> titles){
        if (titles == null) {
            throw new RuntimeException("titles is empty ");
        }
        this.titles = titles;
        return this;
    }


    public ReportTmpDataSetQuery type(String type){
	if (type == null) {
	    throw new RuntimeException("type is null");
        }         
	this.type = type;
	return this;
    }

    public ReportTmpDataSetQuery typeLike( String typeLike){
        if (typeLike == null) {
            throw new RuntimeException("type is null");
        }
        this.typeLike = typeLike;
        return this;
    }

    public ReportTmpDataSetQuery types(List<String> types){
        if (types == null) {
            throw new RuntimeException("types is empty ");
        }
        this.types = types;
        return this;
    }


    public ReportTmpDataSetQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public ReportTmpDataSetQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public ReportTmpDataSetQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public ReportTmpDataSetQuery createDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual){
	if (createDatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDatetime is null");
        }         
	this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
        return this;
    }

    public ReportTmpDataSetQuery createDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual){
        if (createDatetimeLessThanOrEqual == null) {
            throw new RuntimeException("createDatetime is null");
        }
        this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
        return this;
    }



    public ReportTmpDataSetQuery modifier(String modifier){
	if (modifier == null) {
	    throw new RuntimeException("modifier is null");
        }         
	this.modifier = modifier;
	return this;
    }

    public ReportTmpDataSetQuery modifierLike( String modifierLike){
        if (modifierLike == null) {
            throw new RuntimeException("modifier is null");
        }
        this.modifierLike = modifierLike;
        return this;
    }

    public ReportTmpDataSetQuery modifiers(List<String> modifiers){
        if (modifiers == null) {
            throw new RuntimeException("modifiers is empty ");
        }
        this.modifiers = modifiers;
        return this;
    }



    public ReportTmpDataSetQuery modifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual){
	if (modifyDatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("modifyDatetime is null");
        }         
	this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
        return this;
    }

    public ReportTmpDataSetQuery modifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual){
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

            if ("templateId".equals(sortColumn)) {
                orderBy = "E.TEMPLATE_ID_" + a_x;
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

            if ("type".equals(sortColumn)) {
                orderBy = "E.TYPE_" + a_x;
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
        addColumn("templateId", "TEMPLATE_ID_");
        addColumn("code", "CODE_");
        addColumn("name", "NAME_");
        addColumn("title", "TITLE_");
        addColumn("type", "TYPE_");
        addColumn("creator", "CREATOR_");
        addColumn("createDatetime", "CREATEDATETIME_");
        addColumn("modifier", "MODIFIER_");
        addColumn("modifyDatetime", "MODIFYDATETIME_");
    }

}