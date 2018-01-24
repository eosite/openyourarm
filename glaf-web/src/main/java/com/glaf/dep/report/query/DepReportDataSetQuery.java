package com.glaf.dep.report.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DepReportDataSetQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
  	protected Long repTemplateId;
  	protected Long repTemplateIdGreaterThanOrEqual;
  	protected Long repTemplateIdLessThanOrEqual;
  	protected List<Long> repTemplateIds;
  	protected Long dataSetId;
  	protected Long dataSetIdGreaterThanOrEqual;
  	protected Long dataSetIdLessThanOrEqual;
  	protected List<Long> dataSetIds;
  	protected String enCondition;
  	protected String enConditionLike;
  	protected List<String> enConditions;
  	protected String ruleJson;
  	protected String ruleJsonLike;
  	protected List<String> ruleJsons;
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

    public DepReportDataSetQuery() {

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

    public Long getDataSetId(){
        return dataSetId;
    }

    public Long getDataSetIdGreaterThanOrEqual(){
        return dataSetIdGreaterThanOrEqual;
    }

    public Long getDataSetIdLessThanOrEqual(){
	return dataSetIdLessThanOrEqual;
    }

    public List<Long> getDataSetIds(){
	return dataSetIds;
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


    public void setDataSetId(Long dataSetId){
        this.dataSetId = dataSetId;
    }

    public void setDataSetIdGreaterThanOrEqual(Long dataSetIdGreaterThanOrEqual){
        this.dataSetIdGreaterThanOrEqual = dataSetIdGreaterThanOrEqual;
    }

    public void setDataSetIdLessThanOrEqual(Long dataSetIdLessThanOrEqual){
	this.dataSetIdLessThanOrEqual = dataSetIdLessThanOrEqual;
    }

    public void setDataSetIds(List<Long> dataSetIds){
        this.dataSetIds = dataSetIds;
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


    public void setRuleJson(String ruleJson){
        this.ruleJson = ruleJson;
    }

    public void setRuleJsonLike( String ruleJsonLike){
	this.ruleJsonLike = ruleJsonLike;
    }

    public void setRuleJsons(List<String> ruleJsons){
        this.ruleJsons = ruleJsons;
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




    public DepReportDataSetQuery repTemplateId(Long repTemplateId){
	if (repTemplateId == null) {
            throw new RuntimeException("repTemplateId is null");
        }         
	this.repTemplateId = repTemplateId;
	return this;
    }

    public DepReportDataSetQuery repTemplateIdGreaterThanOrEqual(Long repTemplateIdGreaterThanOrEqual){
	if (repTemplateIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("repTemplateId is null");
        }         
	this.repTemplateIdGreaterThanOrEqual = repTemplateIdGreaterThanOrEqual;
        return this;
    }

    public DepReportDataSetQuery repTemplateIdLessThanOrEqual(Long repTemplateIdLessThanOrEqual){
        if (repTemplateIdLessThanOrEqual == null) {
            throw new RuntimeException("repTemplateId is null");
        }
        this.repTemplateIdLessThanOrEqual = repTemplateIdLessThanOrEqual;
        return this;
    }

    public DepReportDataSetQuery repTemplateIds(List<Long> repTemplateIds){
        if (repTemplateIds == null) {
            throw new RuntimeException("repTemplateIds is empty ");
        }
        this.repTemplateIds = repTemplateIds;
        return this;
    }


    public DepReportDataSetQuery dataSetId(Long dataSetId){
	if (dataSetId == null) {
            throw new RuntimeException("dataSetId is null");
        }         
	this.dataSetId = dataSetId;
	return this;
    }

    public DepReportDataSetQuery dataSetIdGreaterThanOrEqual(Long dataSetIdGreaterThanOrEqual){
	if (dataSetIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("dataSetId is null");
        }         
	this.dataSetIdGreaterThanOrEqual = dataSetIdGreaterThanOrEqual;
        return this;
    }

    public DepReportDataSetQuery dataSetIdLessThanOrEqual(Long dataSetIdLessThanOrEqual){
        if (dataSetIdLessThanOrEqual == null) {
            throw new RuntimeException("dataSetId is null");
        }
        this.dataSetIdLessThanOrEqual = dataSetIdLessThanOrEqual;
        return this;
    }

    public DepReportDataSetQuery dataSetIds(List<Long> dataSetIds){
        if (dataSetIds == null) {
            throw new RuntimeException("dataSetIds is empty ");
        }
        this.dataSetIds = dataSetIds;
        return this;
    }


    public DepReportDataSetQuery enCondition(String enCondition){
	if (enCondition == null) {
	    throw new RuntimeException("enCondition is null");
        }         
	this.enCondition = enCondition;
	return this;
    }

    public DepReportDataSetQuery enConditionLike( String enConditionLike){
        if (enConditionLike == null) {
            throw new RuntimeException("enCondition is null");
        }
        this.enConditionLike = enConditionLike;
        return this;
    }

    public DepReportDataSetQuery enConditions(List<String> enConditions){
        if (enConditions == null) {
            throw new RuntimeException("enConditions is empty ");
        }
        this.enConditions = enConditions;
        return this;
    }


    public DepReportDataSetQuery ruleJson(String ruleJson){
	if (ruleJson == null) {
	    throw new RuntimeException("ruleJson is null");
        }         
	this.ruleJson = ruleJson;
	return this;
    }

    public DepReportDataSetQuery ruleJsonLike( String ruleJsonLike){
        if (ruleJsonLike == null) {
            throw new RuntimeException("ruleJson is null");
        }
        this.ruleJsonLike = ruleJsonLike;
        return this;
    }

    public DepReportDataSetQuery ruleJsons(List<String> ruleJsons){
        if (ruleJsons == null) {
            throw new RuntimeException("ruleJsons is empty ");
        }
        this.ruleJsons = ruleJsons;
        return this;
    }


    public DepReportDataSetQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public DepReportDataSetQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public DepReportDataSetQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public DepReportDataSetQuery createDateTimeGreaterThanOrEqual(Date createDateTimeGreaterThanOrEqual){
	if (createDateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDateTime is null");
        }         
	this.createDateTimeGreaterThanOrEqual = createDateTimeGreaterThanOrEqual;
        return this;
    }

    public DepReportDataSetQuery createDateTimeLessThanOrEqual(Date createDateTimeLessThanOrEqual){
        if (createDateTimeLessThanOrEqual == null) {
            throw new RuntimeException("createDateTime is null");
        }
        this.createDateTimeLessThanOrEqual = createDateTimeLessThanOrEqual;
        return this;
    }



    public DepReportDataSetQuery modifier(String modifier){
	if (modifier == null) {
	    throw new RuntimeException("modifier is null");
        }         
	this.modifier = modifier;
	return this;
    }

    public DepReportDataSetQuery modifierLike( String modifierLike){
        if (modifierLike == null) {
            throw new RuntimeException("modifier is null");
        }
        this.modifierLike = modifierLike;
        return this;
    }

    public DepReportDataSetQuery modifiers(List<String> modifiers){
        if (modifiers == null) {
            throw new RuntimeException("modifiers is empty ");
        }
        this.modifiers = modifiers;
        return this;
    }



    public DepReportDataSetQuery modifyDateTimeGreaterThanOrEqual(Date modifyDateTimeGreaterThanOrEqual){
	if (modifyDateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("modifyDateTime is null");
        }         
	this.modifyDateTimeGreaterThanOrEqual = modifyDateTimeGreaterThanOrEqual;
        return this;
    }

    public DepReportDataSetQuery modifyDateTimeLessThanOrEqual(Date modifyDateTimeLessThanOrEqual){
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

            if ("dataSetId".equals(sortColumn)) {
                orderBy = "E.DATASET_ID_" + a_x;
            } 

            if ("enCondition".equals(sortColumn)) {
                orderBy = "E.ENCONDITON_" + a_x;
            } 

            if ("ruleJson".equals(sortColumn)) {
                orderBy = "E.RULEJSON_" + a_x;
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
        addColumn("dataSetId", "DATASET_ID_");
        addColumn("enCondition", "ENCONDITON_");
        addColumn("ruleJson", "RULEJSON_");
        addColumn("creator", "CREATOR_");
        addColumn("createDateTime", "CREATEDATETIME_");
        addColumn("modifier", "MODIFIER_");
        addColumn("modifyDateTime", "MODIFYDATETIME_");
    }

}