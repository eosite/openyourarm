package com.glaf.dep.report.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DepReportValidationQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
  	protected Long cellId;
  	protected Long cellIdGreaterThanOrEqual;
  	protected Long cellIdLessThanOrEqual;
  	protected List<Long> cellIds;
  	protected String enConditon;
  	protected String enConditonLike;
  	protected List<String> enConditons;
  	protected String expression;
  	protected String expressionLike;
  	protected List<String> expressions;
  	protected String alertTmp;
  	protected String alertTmpLike;
  	protected List<String> alertTmps;
  	protected String alertType;
  	protected String alertTypeLike;
  	protected List<String> alertTypes;
  	protected String trrigerType;
  	protected String trrigerTypeLike;
  	protected List<String> trrigerTypes;
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
        protected Date modifydateTimeGreaterThanOrEqual;
  	protected Date modifydateTimeLessThanOrEqual;

    public DepReportValidationQuery() {

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

    public String getEnConditon(){
        return enConditon;
    }

    public String getEnConditonLike(){
	    if (enConditonLike != null && enConditonLike.trim().length() > 0) {
		if (!enConditonLike.startsWith("%")) {
		    enConditonLike = "%" + enConditonLike;
		}
		if (!enConditonLike.endsWith("%")) {
		   enConditonLike = enConditonLike + "%";
		}
	    }
	return enConditonLike;
    }

    public List<String> getEnConditons(){
	return enConditons;
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


    public String getAlertTmp(){
        return alertTmp;
    }

    public String getAlertTmpLike(){
	    if (alertTmpLike != null && alertTmpLike.trim().length() > 0) {
		if (!alertTmpLike.startsWith("%")) {
		    alertTmpLike = "%" + alertTmpLike;
		}
		if (!alertTmpLike.endsWith("%")) {
		   alertTmpLike = alertTmpLike + "%";
		}
	    }
	return alertTmpLike;
    }

    public List<String> getAlertTmps(){
	return alertTmps;
    }


    public String getAlertType(){
        return alertType;
    }

    public String getAlertTypeLike(){
	    if (alertTypeLike != null && alertTypeLike.trim().length() > 0) {
		if (!alertTypeLike.startsWith("%")) {
		    alertTypeLike = "%" + alertTypeLike;
		}
		if (!alertTypeLike.endsWith("%")) {
		   alertTypeLike = alertTypeLike + "%";
		}
	    }
	return alertTypeLike;
    }

    public List<String> getAlertTypes(){
	return alertTypes;
    }


    public String getTrrigerType(){
        return trrigerType;
    }

    public String getTrrigerTypeLike(){
	    if (trrigerTypeLike != null && trrigerTypeLike.trim().length() > 0) {
		if (!trrigerTypeLike.startsWith("%")) {
		    trrigerTypeLike = "%" + trrigerTypeLike;
		}
		if (!trrigerTypeLike.endsWith("%")) {
		   trrigerTypeLike = trrigerTypeLike + "%";
		}
	    }
	return trrigerTypeLike;
    }

    public List<String> getTrrigerTypes(){
	return trrigerTypes;
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


    public Date getModifydateTimeGreaterThanOrEqual(){
        return modifydateTimeGreaterThanOrEqual;
    }

    public Date getModifydateTimeLessThanOrEqual(){
	return modifydateTimeLessThanOrEqual;
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


    public void setEnConditon(String enConditon){
        this.enConditon = enConditon;
    }

    public void setEnConditonLike( String enConditonLike){
	this.enConditonLike = enConditonLike;
    }

    public void setEnConditons(List<String> enConditons){
        this.enConditons = enConditons;
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


    public void setAlertTmp(String alertTmp){
        this.alertTmp = alertTmp;
    }

    public void setAlertTmpLike( String alertTmpLike){
	this.alertTmpLike = alertTmpLike;
    }

    public void setAlertTmps(List<String> alertTmps){
        this.alertTmps = alertTmps;
    }


    public void setAlertType(String alertType){
        this.alertType = alertType;
    }

    public void setAlertTypeLike( String alertTypeLike){
	this.alertTypeLike = alertTypeLike;
    }

    public void setAlertTypes(List<String> alertTypes){
        this.alertTypes = alertTypes;
    }


    public void setTrrigerType(String trrigerType){
        this.trrigerType = trrigerType;
    }

    public void setTrrigerTypeLike( String trrigerTypeLike){
	this.trrigerTypeLike = trrigerTypeLike;
    }

    public void setTrrigerTypes(List<String> trrigerTypes){
        this.trrigerTypes = trrigerTypes;
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


    public void setModifydateTimeGreaterThanOrEqual(Date modifydateTimeGreaterThanOrEqual){
        this.modifydateTimeGreaterThanOrEqual = modifydateTimeGreaterThanOrEqual;
    }

    public void setModifydateTimeLessThanOrEqual(Date modifydateTimeLessThanOrEqual){
	this.modifydateTimeLessThanOrEqual = modifydateTimeLessThanOrEqual;
    }




    public DepReportValidationQuery cellId(Long cellId){
	if (cellId == null) {
            throw new RuntimeException("cellId is null");
        }         
	this.cellId = cellId;
	return this;
    }

    public DepReportValidationQuery cellIdGreaterThanOrEqual(Long cellIdGreaterThanOrEqual){
	if (cellIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("cellId is null");
        }         
	this.cellIdGreaterThanOrEqual = cellIdGreaterThanOrEqual;
        return this;
    }

    public DepReportValidationQuery cellIdLessThanOrEqual(Long cellIdLessThanOrEqual){
        if (cellIdLessThanOrEqual == null) {
            throw new RuntimeException("cellId is null");
        }
        this.cellIdLessThanOrEqual = cellIdLessThanOrEqual;
        return this;
    }

    public DepReportValidationQuery cellIds(List<Long> cellIds){
        if (cellIds == null) {
            throw new RuntimeException("cellIds is empty ");
        }
        this.cellIds = cellIds;
        return this;
    }


    public DepReportValidationQuery enConditon(String enConditon){
	if (enConditon == null) {
	    throw new RuntimeException("enConditon is null");
        }         
	this.enConditon = enConditon;
	return this;
    }

    public DepReportValidationQuery enConditonLike( String enConditonLike){
        if (enConditonLike == null) {
            throw new RuntimeException("enConditon is null");
        }
        this.enConditonLike = enConditonLike;
        return this;
    }

    public DepReportValidationQuery enConditons(List<String> enConditons){
        if (enConditons == null) {
            throw new RuntimeException("enConditons is empty ");
        }
        this.enConditons = enConditons;
        return this;
    }


    public DepReportValidationQuery expression(String expression){
	if (expression == null) {
	    throw new RuntimeException("expression is null");
        }         
	this.expression = expression;
	return this;
    }

    public DepReportValidationQuery expressionLike( String expressionLike){
        if (expressionLike == null) {
            throw new RuntimeException("expression is null");
        }
        this.expressionLike = expressionLike;
        return this;
    }

    public DepReportValidationQuery expressions(List<String> expressions){
        if (expressions == null) {
            throw new RuntimeException("expressions is empty ");
        }
        this.expressions = expressions;
        return this;
    }


    public DepReportValidationQuery alertTmp(String alertTmp){
	if (alertTmp == null) {
	    throw new RuntimeException("alertTmp is null");
        }         
	this.alertTmp = alertTmp;
	return this;
    }

    public DepReportValidationQuery alertTmpLike( String alertTmpLike){
        if (alertTmpLike == null) {
            throw new RuntimeException("alertTmp is null");
        }
        this.alertTmpLike = alertTmpLike;
        return this;
    }

    public DepReportValidationQuery alertTmps(List<String> alertTmps){
        if (alertTmps == null) {
            throw new RuntimeException("alertTmps is empty ");
        }
        this.alertTmps = alertTmps;
        return this;
    }


    public DepReportValidationQuery alertType(String alertType){
	if (alertType == null) {
	    throw new RuntimeException("alertType is null");
        }         
	this.alertType = alertType;
	return this;
    }

    public DepReportValidationQuery alertTypeLike( String alertTypeLike){
        if (alertTypeLike == null) {
            throw new RuntimeException("alertType is null");
        }
        this.alertTypeLike = alertTypeLike;
        return this;
    }

    public DepReportValidationQuery alertTypes(List<String> alertTypes){
        if (alertTypes == null) {
            throw new RuntimeException("alertTypes is empty ");
        }
        this.alertTypes = alertTypes;
        return this;
    }


    public DepReportValidationQuery trrigerType(String trrigerType){
	if (trrigerType == null) {
	    throw new RuntimeException("trrigerType is null");
        }         
	this.trrigerType = trrigerType;
	return this;
    }

    public DepReportValidationQuery trrigerTypeLike( String trrigerTypeLike){
        if (trrigerTypeLike == null) {
            throw new RuntimeException("trrigerType is null");
        }
        this.trrigerTypeLike = trrigerTypeLike;
        return this;
    }

    public DepReportValidationQuery trrigerTypes(List<String> trrigerTypes){
        if (trrigerTypes == null) {
            throw new RuntimeException("trrigerTypes is empty ");
        }
        this.trrigerTypes = trrigerTypes;
        return this;
    }


    public DepReportValidationQuery ruleJson(String ruleJson){
	if (ruleJson == null) {
	    throw new RuntimeException("ruleJson is null");
        }         
	this.ruleJson = ruleJson;
	return this;
    }

    public DepReportValidationQuery ruleJsonLike( String ruleJsonLike){
        if (ruleJsonLike == null) {
            throw new RuntimeException("ruleJson is null");
        }
        this.ruleJsonLike = ruleJsonLike;
        return this;
    }

    public DepReportValidationQuery ruleJsons(List<String> ruleJsons){
        if (ruleJsons == null) {
            throw new RuntimeException("ruleJsons is empty ");
        }
        this.ruleJsons = ruleJsons;
        return this;
    }


    public DepReportValidationQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public DepReportValidationQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public DepReportValidationQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public DepReportValidationQuery createDateTimeGreaterThanOrEqual(Date createDateTimeGreaterThanOrEqual){
	if (createDateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDateTime is null");
        }         
	this.createDateTimeGreaterThanOrEqual = createDateTimeGreaterThanOrEqual;
        return this;
    }

    public DepReportValidationQuery createDateTimeLessThanOrEqual(Date createDateTimeLessThanOrEqual){
        if (createDateTimeLessThanOrEqual == null) {
            throw new RuntimeException("createDateTime is null");
        }
        this.createDateTimeLessThanOrEqual = createDateTimeLessThanOrEqual;
        return this;
    }



    public DepReportValidationQuery modifier(String modifier){
	if (modifier == null) {
	    throw new RuntimeException("modifier is null");
        }         
	this.modifier = modifier;
	return this;
    }

    public DepReportValidationQuery modifierLike( String modifierLike){
        if (modifierLike == null) {
            throw new RuntimeException("modifier is null");
        }
        this.modifierLike = modifierLike;
        return this;
    }

    public DepReportValidationQuery modifiers(List<String> modifiers){
        if (modifiers == null) {
            throw new RuntimeException("modifiers is empty ");
        }
        this.modifiers = modifiers;
        return this;
    }



    public DepReportValidationQuery modifydateTimeGreaterThanOrEqual(Date modifydateTimeGreaterThanOrEqual){
	if (modifydateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("modifydateTime is null");
        }         
	this.modifydateTimeGreaterThanOrEqual = modifydateTimeGreaterThanOrEqual;
        return this;
    }

    public DepReportValidationQuery modifydateTimeLessThanOrEqual(Date modifydateTimeLessThanOrEqual){
        if (modifydateTimeLessThanOrEqual == null) {
            throw new RuntimeException("modifydateTime is null");
        }
        this.modifydateTimeLessThanOrEqual = modifydateTimeLessThanOrEqual;
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

            if ("enConditon".equals(sortColumn)) {
                orderBy = "E.ENCONDITON_" + a_x;
            } 

            if ("expression".equals(sortColumn)) {
                orderBy = "E.EXPRESSION_" + a_x;
            } 

            if ("alertTmp".equals(sortColumn)) {
                orderBy = "E.ALERT_TMP_" + a_x;
            } 

            if ("alertType".equals(sortColumn)) {
                orderBy = "E.ALERT_TYPE_" + a_x;
            } 

            if ("trrigerType".equals(sortColumn)) {
                orderBy = "E.TRRIGER_TYPE_" + a_x;
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

            if ("modifydateTime".equals(sortColumn)) {
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
        addColumn("enConditon", "ENCONDITON_");
        addColumn("expression", "EXPRESSION_");
        addColumn("alertTmp", "ALERT_TMP_");
        addColumn("alertType", "ALERT_TYPE_");
        addColumn("trrigerType", "TRRIGER_TYPE_");
        addColumn("ruleJson", "RULEJSON_");
        addColumn("creator", "CREATOR_");
        addColumn("createDateTime", "CREATEDATETIME_");
        addColumn("modifier", "MODIFIER_");
        addColumn("modifydateTime", "MODIFYDATETIME_");
    }

}