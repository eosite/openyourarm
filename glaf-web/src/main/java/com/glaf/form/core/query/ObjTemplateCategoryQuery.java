package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ObjTemplateCategoryQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> categoryIds;
	protected Collection<String> appActorIds;
  	protected Long templateId;
  	protected Long templateIdGreaterThanOrEqual;
  	protected Long templateIdLessThanOrEqual;
  	protected List<Long> templateIds;
  	protected String creator;
  	protected String creatorLike;
  	protected List<String> creators;
        protected Date createTimeGreaterThanOrEqual;
  	protected Date createTimeLessThanOrEqual;
  	protected String modifier;
  	protected String modifierLike;
  	protected List<String> modifiers;
        protected Date updateTimeGreaterThanOrEqual;
  	protected Date updateTimeLessThanOrEqual;

    public ObjTemplateCategoryQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public Long getTemplateId(){
        return templateId;
    }

    public Long getTemplateIdGreaterThanOrEqual(){
        return templateIdGreaterThanOrEqual;
    }

    public Long getTemplateIdLessThanOrEqual(){
	return templateIdLessThanOrEqual;
    }

    public List<Long> getTemplateIds(){
	return templateIds;
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


    public Date getCreateTimeGreaterThanOrEqual(){
        return createTimeGreaterThanOrEqual;
    }

    public Date getCreateTimeLessThanOrEqual(){
	return createTimeLessThanOrEqual;
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


    public Date getUpdateTimeGreaterThanOrEqual(){
        return updateTimeGreaterThanOrEqual;
    }

    public Date getUpdateTimeLessThanOrEqual(){
	return updateTimeLessThanOrEqual;
    }


 

    public void setTemplateId(Long templateId){
        this.templateId = templateId;
    }

    public void setTemplateIdGreaterThanOrEqual(Long templateIdGreaterThanOrEqual){
        this.templateIdGreaterThanOrEqual = templateIdGreaterThanOrEqual;
    }

    public void setTemplateIdLessThanOrEqual(Long templateIdLessThanOrEqual){
	this.templateIdLessThanOrEqual = templateIdLessThanOrEqual;
    }

    public void setTemplateIds(List<Long> templateIds){
        this.templateIds = templateIds;
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


    public void setCreateTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
        this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
    }

    public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
	this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
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


    public void setUpdateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
        this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
    }

    public void setUpdateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
	this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
    }




    public ObjTemplateCategoryQuery templateId(Long templateId){
	if (templateId == null) {
            throw new RuntimeException("templateId is null");
        }         
	this.templateId = templateId;
	return this;
    }

    public ObjTemplateCategoryQuery templateIdGreaterThanOrEqual(Long templateIdGreaterThanOrEqual){
	if (templateIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("templateId is null");
        }         
	this.templateIdGreaterThanOrEqual = templateIdGreaterThanOrEqual;
        return this;
    }

    public ObjTemplateCategoryQuery templateIdLessThanOrEqual(Long templateIdLessThanOrEqual){
        if (templateIdLessThanOrEqual == null) {
            throw new RuntimeException("templateId is null");
        }
        this.templateIdLessThanOrEqual = templateIdLessThanOrEqual;
        return this;
    }

    public ObjTemplateCategoryQuery templateIds(List<Long> templateIds){
        if (templateIds == null) {
            throw new RuntimeException("templateIds is empty ");
        }
        this.templateIds = templateIds;
        return this;
    }


    public ObjTemplateCategoryQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public ObjTemplateCategoryQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public ObjTemplateCategoryQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public ObjTemplateCategoryQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
	if (createTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime is null");
        }         
	this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
        return this;
    }

    public ObjTemplateCategoryQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
        if (createTimeLessThanOrEqual == null) {
            throw new RuntimeException("createTime is null");
        }
        this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
        return this;
    }



    public ObjTemplateCategoryQuery modifier(String modifier){
	if (modifier == null) {
	    throw new RuntimeException("modifier is null");
        }         
	this.modifier = modifier;
	return this;
    }

    public ObjTemplateCategoryQuery modifierLike( String modifierLike){
        if (modifierLike == null) {
            throw new RuntimeException("modifier is null");
        }
        this.modifierLike = modifierLike;
        return this;
    }

    public ObjTemplateCategoryQuery modifiers(List<String> modifiers){
        if (modifiers == null) {
            throw new RuntimeException("modifiers is empty ");
        }
        this.modifiers = modifiers;
        return this;
    }



    public ObjTemplateCategoryQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
	if (updateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime is null");
        }         
	this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
        return this;
    }

    public ObjTemplateCategoryQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
        if (updateTimeLessThanOrEqual == null) {
            throw new RuntimeException("updateTime is null");
        }
        this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
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

            if ("creator".equals(sortColumn)) {
                orderBy = "E.CREATOR_" + a_x;
            } 

            if ("createTime".equals(sortColumn)) {
                orderBy = "E.CREATETIME_" + a_x;
            } 

            if ("modifier".equals(sortColumn)) {
                orderBy = "E.MODIFIER_" + a_x;
            } 

            if ("updateTime".equals(sortColumn)) {
                orderBy = "E.UPDATETIME_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("categoryId", "CATEGORY_ID_");
        addColumn("templateId", "TEMPLATE_ID_");
        addColumn("creator", "CREATOR_");
        addColumn("createTime", "CREATETIME_");
        addColumn("modifier", "MODIFIER_");
        addColumn("updateTime", "UPDATETIME_");
    }

}