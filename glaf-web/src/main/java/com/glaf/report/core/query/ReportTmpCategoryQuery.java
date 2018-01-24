package com.glaf.report.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ReportTmpCategoryQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
  	protected String templateId;
  	protected String templateIdLike;
  	protected List<String> templateIds;
  	protected Long categoryId;
  	protected Long categoryIdGreaterThanOrEqual;
  	protected Long categoryIdLessThanOrEqual;
  	protected List<Long> categoryIds;
  	protected String creator;
  	protected String creatorLike;
  	protected List<String> creators;
        protected Date createDatetimeGreaterThanOrEqual;
  	protected Date createDatetimeLessThanOrEqual;

    public ReportTmpCategoryQuery() {

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


    public Long getCategoryId(){
        return categoryId;
    }

    public Long getCategoryIdGreaterThanOrEqual(){
        return categoryIdGreaterThanOrEqual;
    }

    public Long getCategoryIdLessThanOrEqual(){
	return categoryIdLessThanOrEqual;
    }

    public List<Long> getCategoryIds(){
	return categoryIds;
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


 

    public void setTemplateId(String templateId){
        this.templateId = templateId;
    }

    public void setTemplateIdLike( String templateIdLike){
	this.templateIdLike = templateIdLike;
    }

    public void setTemplateIds(List<String> templateIds){
        this.templateIds = templateIds;
    }


    public void setCategoryId(Long categoryId){
        this.categoryId = categoryId;
    }

    public void setCategoryIdGreaterThanOrEqual(Long categoryIdGreaterThanOrEqual){
        this.categoryIdGreaterThanOrEqual = categoryIdGreaterThanOrEqual;
    }

    public void setCategoryIdLessThanOrEqual(Long categoryIdLessThanOrEqual){
	this.categoryIdLessThanOrEqual = categoryIdLessThanOrEqual;
    }

    public void setCategoryIds(List<Long> categoryIds){
        this.categoryIds = categoryIds;
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




    public ReportTmpCategoryQuery templateId(String templateId){
	if (templateId == null) {
	    throw new RuntimeException("templateId is null");
        }         
	this.templateId = templateId;
	return this;
    }

    public ReportTmpCategoryQuery templateIdLike( String templateIdLike){
        if (templateIdLike == null) {
            throw new RuntimeException("templateId is null");
        }
        this.templateIdLike = templateIdLike;
        return this;
    }

    public ReportTmpCategoryQuery templateIds(List<String> templateIds){
        if (templateIds == null) {
            throw new RuntimeException("templateIds is empty ");
        }
        this.templateIds = templateIds;
        return this;
    }


    public ReportTmpCategoryQuery categoryId(Long categoryId){
	if (categoryId == null) {
            throw new RuntimeException("categoryId is null");
        }         
	this.categoryId = categoryId;
	return this;
    }

    public ReportTmpCategoryQuery categoryIdGreaterThanOrEqual(Long categoryIdGreaterThanOrEqual){
	if (categoryIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("categoryId is null");
        }         
	this.categoryIdGreaterThanOrEqual = categoryIdGreaterThanOrEqual;
        return this;
    }

    public ReportTmpCategoryQuery categoryIdLessThanOrEqual(Long categoryIdLessThanOrEqual){
        if (categoryIdLessThanOrEqual == null) {
            throw new RuntimeException("categoryId is null");
        }
        this.categoryIdLessThanOrEqual = categoryIdLessThanOrEqual;
        return this;
    }

    public ReportTmpCategoryQuery categoryIds(List<Long> categoryIds){
        if (categoryIds == null) {
            throw new RuntimeException("categoryIds is empty ");
        }
        this.categoryIds = categoryIds;
        return this;
    }


    public ReportTmpCategoryQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public ReportTmpCategoryQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public ReportTmpCategoryQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public ReportTmpCategoryQuery createDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual){
	if (createDatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDatetime is null");
        }         
	this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
        return this;
    }

    public ReportTmpCategoryQuery createDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual){
        if (createDatetimeLessThanOrEqual == null) {
            throw new RuntimeException("createDatetime is null");
        }
        this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
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

            if ("categoryId".equals(sortColumn)) {
                orderBy = "E.CATEGORY_ID_" + a_x;
            } 

            if ("creator".equals(sortColumn)) {
                orderBy = "E.CREATOR_" + a_x;
            } 

            if ("createDatetime".equals(sortColumn)) {
                orderBy = "E.CREATEDATETIME_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("templateId", "TEMPLATE_ID_");
        addColumn("categoryId", "CATEGORY_ID_");
        addColumn("creator", "CREATOR_");
        addColumn("createDatetime", "CREATEDATETIME_");
    }

}