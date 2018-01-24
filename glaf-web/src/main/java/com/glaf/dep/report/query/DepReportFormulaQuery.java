package com.glaf.dep.report.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DepReportFormulaQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
  	protected Long cellId;
  	protected Long cellIdGreaterThanOrEqual;
  	protected Long cellIdLessThanOrEqual;
  	protected List<Long> cellIds;
  	protected String expression;
  	protected String expressionLike;
  	protected List<String> expressions;
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

    public DepReportFormulaQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public Long getCellId(){
        return cellId;
    }

    public Long getCellIdGreaterThanOrEqual(){
        return cellIdGreaterThanOrEqual;
    }

    public Long getCellIdLessThanOrEqual(){
	return cellIdLessThanOrEqual;
    }

    public List<Long> getCellIds(){
	return cellIds;
    }

    public String getExpression(){
        return expression;
    }

    public String getExpressionLike(){
	    if (expressionLike != null && expressionLike.trim().length() > 0) {
		if (!expressionLike.startsWith("%")) {
		    expressionLike = "%" + expressionLike;
		}
		if (!expressionLike.endsWith("%")) {
		   expressionLike = expressionLike + "%";
		}
	    }
	return expressionLike;
    }

    public List<String> getExpressions(){
	return expressions;
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


 

    public void setCellId(Long cellId){
        this.cellId = cellId;
    }

    public void setCellIdGreaterThanOrEqual(Long cellIdGreaterThanOrEqual){
        this.cellIdGreaterThanOrEqual = cellIdGreaterThanOrEqual;
    }

    public void setCellIdLessThanOrEqual(Long cellIdLessThanOrEqual){
	this.cellIdLessThanOrEqual = cellIdLessThanOrEqual;
    }

    public void setCellIds(List<Long> cellIds){
        this.cellIds = cellIds;
    }


    public void setExpression(String expression){
        this.expression = expression;
    }

    public void setExpressionLike( String expressionLike){
	this.expressionLike = expressionLike;
    }

    public void setExpressions(List<String> expressions){
        this.expressions = expressions;
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




    public DepReportFormulaQuery cellId(Long cellId){
	if (cellId == null) {
            throw new RuntimeException("cellId is null");
        }         
	this.cellId = cellId;
	return this;
    }

    public DepReportFormulaQuery cellIdGreaterThanOrEqual(Long cellIdGreaterThanOrEqual){
	if (cellIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("cellId is null");
        }         
	this.cellIdGreaterThanOrEqual = cellIdGreaterThanOrEqual;
        return this;
    }

    public DepReportFormulaQuery cellIdLessThanOrEqual(Long cellIdLessThanOrEqual){
        if (cellIdLessThanOrEqual == null) {
            throw new RuntimeException("cellId is null");
        }
        this.cellIdLessThanOrEqual = cellIdLessThanOrEqual;
        return this;
    }

    public DepReportFormulaQuery cellIds(List<Long> cellIds){
        if (cellIds == null) {
            throw new RuntimeException("cellIds is empty ");
        }
        this.cellIds = cellIds;
        return this;
    }


    public DepReportFormulaQuery expression(String expression){
	if (expression == null) {
	    throw new RuntimeException("expression is null");
        }         
	this.expression = expression;
	return this;
    }

    public DepReportFormulaQuery expressionLike( String expressionLike){
        if (expressionLike == null) {
            throw new RuntimeException("expression is null");
        }
        this.expressionLike = expressionLike;
        return this;
    }

    public DepReportFormulaQuery expressions(List<String> expressions){
        if (expressions == null) {
            throw new RuntimeException("expressions is empty ");
        }
        this.expressions = expressions;
        return this;
    }


    public DepReportFormulaQuery enCondition(String enCondition){
	if (enCondition == null) {
	    throw new RuntimeException("enCondition is null");
        }         
	this.enCondition = enCondition;
	return this;
    }

    public DepReportFormulaQuery enConditionLike( String enConditionLike){
        if (enConditionLike == null) {
            throw new RuntimeException("enCondition is null");
        }
        this.enConditionLike = enConditionLike;
        return this;
    }

    public DepReportFormulaQuery enConditions(List<String> enConditions){
        if (enConditions == null) {
            throw new RuntimeException("enConditions is empty ");
        }
        this.enConditions = enConditions;
        return this;
    }


    public DepReportFormulaQuery ruleJson(String ruleJson){
	if (ruleJson == null) {
	    throw new RuntimeException("ruleJson is null");
        }         
	this.ruleJson = ruleJson;
	return this;
    }

    public DepReportFormulaQuery ruleJsonLike( String ruleJsonLike){
        if (ruleJsonLike == null) {
            throw new RuntimeException("ruleJson is null");
        }
        this.ruleJsonLike = ruleJsonLike;
        return this;
    }

    public DepReportFormulaQuery ruleJsons(List<String> ruleJsons){
        if (ruleJsons == null) {
            throw new RuntimeException("ruleJsons is empty ");
        }
        this.ruleJsons = ruleJsons;
        return this;
    }


    public DepReportFormulaQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public DepReportFormulaQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public DepReportFormulaQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public DepReportFormulaQuery createDateTimeGreaterThanOrEqual(Date createDateTimeGreaterThanOrEqual){
	if (createDateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDateTime is null");
        }         
	this.createDateTimeGreaterThanOrEqual = createDateTimeGreaterThanOrEqual;
        return this;
    }

    public DepReportFormulaQuery createDateTimeLessThanOrEqual(Date createDateTimeLessThanOrEqual){
        if (createDateTimeLessThanOrEqual == null) {
            throw new RuntimeException("createDateTime is null");
        }
        this.createDateTimeLessThanOrEqual = createDateTimeLessThanOrEqual;
        return this;
    }



    public DepReportFormulaQuery modifier(String modifier){
	if (modifier == null) {
	    throw new RuntimeException("modifier is null");
        }         
	this.modifier = modifier;
	return this;
    }

    public DepReportFormulaQuery modifierLike( String modifierLike){
        if (modifierLike == null) {
            throw new RuntimeException("modifier is null");
        }
        this.modifierLike = modifierLike;
        return this;
    }

    public DepReportFormulaQuery modifiers(List<String> modifiers){
        if (modifiers == null) {
            throw new RuntimeException("modifiers is empty ");
        }
        this.modifiers = modifiers;
        return this;
    }



    public DepReportFormulaQuery modifyDateTimeGreaterThanOrEqual(Date modifyDateTimeGreaterThanOrEqual){
	if (modifyDateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("modifyDateTime is null");
        }         
	this.modifyDateTimeGreaterThanOrEqual = modifyDateTimeGreaterThanOrEqual;
        return this;
    }

    public DepReportFormulaQuery modifyDateTimeLessThanOrEqual(Date modifyDateTimeLessThanOrEqual){
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

            if ("cellId".equals(sortColumn)) {
                orderBy = "E.CELL_ID_" + a_x;
            } 

            if ("expression".equals(sortColumn)) {
                orderBy = "E.EXPRESSION_" + a_x;
            } 

            if ("enCondition".equals(sortColumn)) {
                orderBy = "E.ENCONDITION_" + a_x;
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
        addColumn("cellId", "CELL_ID_");
        addColumn("expression", "EXPRESSION_");
        addColumn("enCondition", "ENCONDITION_");
        addColumn("ruleJson", "RULEJSON_");
        addColumn("creator", "CREATOR_");
        addColumn("createDateTime", "CREATEDATETIME_");
        addColumn("modifier", "MODIFIER_");
        addColumn("modifyDateTime", "MODIFYDATETIME_");
    }

}