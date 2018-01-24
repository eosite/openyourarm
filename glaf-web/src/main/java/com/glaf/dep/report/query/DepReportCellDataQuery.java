package com.glaf.dep.report.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DepReportCellDataQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
  	protected Long cellId;
  	protected Long cellIdGreaterThanOrEqual;
  	protected Long cellIdLessThanOrEqual;
  	protected List<Long> cellIds;
  	protected String inputMode;
  	protected String inputModeLike;
  	protected List<String> inputModes;
  	protected String readOnly;
  	protected String readOnlyLike;
  	protected List<String> readOnlys;
  	protected String dtype;
  	protected String dtypeLike;
  	protected List<String> dtypes;
  	protected String defVal;
  	protected String defValLike;
  	protected List<String> defVals;
  	protected String ruleJson;
  	protected String ruleJsonLike;
  	protected List<String> ruleJsons;
  	protected String enCondition;
  	protected String enConditionLike;
  	protected List<String> enConditions;
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

    public DepReportCellDataQuery() {

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

    public String getInputMode(){
        return inputMode;
    }

    public String getInputModeLike(){
	    if (inputModeLike != null && inputModeLike.trim().length() > 0) {
		if (!inputModeLike.startsWith("%")) {
		    inputModeLike = "%" + inputModeLike;
		}
		if (!inputModeLike.endsWith("%")) {
		   inputModeLike = inputModeLike + "%";
		}
	    }
	return inputModeLike;
    }

    public List<String> getInputModes(){
	return inputModes;
    }


    public String getReadOnly(){
        return readOnly;
    }

    public String getReadOnlyLike(){
	    if (readOnlyLike != null && readOnlyLike.trim().length() > 0) {
		if (!readOnlyLike.startsWith("%")) {
		    readOnlyLike = "%" + readOnlyLike;
		}
		if (!readOnlyLike.endsWith("%")) {
		   readOnlyLike = readOnlyLike + "%";
		}
	    }
	return readOnlyLike;
    }

    public List<String> getReadOnlys(){
	return readOnlys;
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


    public String getDefVal(){
        return defVal;
    }

    public String getDefValLike(){
	    if (defValLike != null && defValLike.trim().length() > 0) {
		if (!defValLike.startsWith("%")) {
		    defValLike = "%" + defValLike;
		}
		if (!defValLike.endsWith("%")) {
		   defValLike = defValLike + "%";
		}
	    }
	return defValLike;
    }

    public List<String> getDefVals(){
	return defVals;
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


    public void setInputMode(String inputMode){
        this.inputMode = inputMode;
    }

    public void setInputModeLike( String inputModeLike){
	this.inputModeLike = inputModeLike;
    }

    public void setInputModes(List<String> inputModes){
        this.inputModes = inputModes;
    }


    public void setReadOnly(String readOnly){
        this.readOnly = readOnly;
    }

    public void setReadOnlyLike( String readOnlyLike){
	this.readOnlyLike = readOnlyLike;
    }

    public void setReadOnlys(List<String> readOnlys){
        this.readOnlys = readOnlys;
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


    public void setDefVal(String defVal){
        this.defVal = defVal;
    }

    public void setDefValLike( String defValLike){
	this.defValLike = defValLike;
    }

    public void setDefVals(List<String> defVals){
        this.defVals = defVals;
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


    public void setEnCondition(String enCondition){
        this.enCondition = enCondition;
    }

    public void setEnConditionLike( String enConditionLike){
	this.enConditionLike = enConditionLike;
    }

    public void setEnConditions(List<String> enConditions){
        this.enConditions = enConditions;
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




    public DepReportCellDataQuery cellId(Long cellId){
	if (cellId == null) {
            throw new RuntimeException("cellId is null");
        }         
	this.cellId = cellId;
	return this;
    }

    public DepReportCellDataQuery cellIdGreaterThanOrEqual(Long cellIdGreaterThanOrEqual){
	if (cellIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("cellId is null");
        }         
	this.cellIdGreaterThanOrEqual = cellIdGreaterThanOrEqual;
        return this;
    }

    public DepReportCellDataQuery cellIdLessThanOrEqual(Long cellIdLessThanOrEqual){
        if (cellIdLessThanOrEqual == null) {
            throw new RuntimeException("cellId is null");
        }
        this.cellIdLessThanOrEqual = cellIdLessThanOrEqual;
        return this;
    }

    public DepReportCellDataQuery cellIds(List<Long> cellIds){
        if (cellIds == null) {
            throw new RuntimeException("cellIds is empty ");
        }
        this.cellIds = cellIds;
        return this;
    }


    public DepReportCellDataQuery inputMode(String inputMode){
	if (inputMode == null) {
	    throw new RuntimeException("inputMode is null");
        }         
	this.inputMode = inputMode;
	return this;
    }

    public DepReportCellDataQuery inputModeLike( String inputModeLike){
        if (inputModeLike == null) {
            throw new RuntimeException("inputMode is null");
        }
        this.inputModeLike = inputModeLike;
        return this;
    }

    public DepReportCellDataQuery inputModes(List<String> inputModes){
        if (inputModes == null) {
            throw new RuntimeException("inputModes is empty ");
        }
        this.inputModes = inputModes;
        return this;
    }


    public DepReportCellDataQuery readOnly(String readOnly){
	if (readOnly == null) {
	    throw new RuntimeException("readOnly is null");
        }         
	this.readOnly = readOnly;
	return this;
    }

    public DepReportCellDataQuery readOnlyLike( String readOnlyLike){
        if (readOnlyLike == null) {
            throw new RuntimeException("readOnly is null");
        }
        this.readOnlyLike = readOnlyLike;
        return this;
    }

    public DepReportCellDataQuery readOnlys(List<String> readOnlys){
        if (readOnlys == null) {
            throw new RuntimeException("readOnlys is empty ");
        }
        this.readOnlys = readOnlys;
        return this;
    }


    public DepReportCellDataQuery dtype(String dtype){
	if (dtype == null) {
	    throw new RuntimeException("dtype is null");
        }         
	this.dtype = dtype;
	return this;
    }

    public DepReportCellDataQuery dtypeLike( String dtypeLike){
        if (dtypeLike == null) {
            throw new RuntimeException("dtype is null");
        }
        this.dtypeLike = dtypeLike;
        return this;
    }

    public DepReportCellDataQuery dtypes(List<String> dtypes){
        if (dtypes == null) {
            throw new RuntimeException("dtypes is empty ");
        }
        this.dtypes = dtypes;
        return this;
    }


    public DepReportCellDataQuery defVal(String defVal){
	if (defVal == null) {
	    throw new RuntimeException("defVal is null");
        }         
	this.defVal = defVal;
	return this;
    }

    public DepReportCellDataQuery defValLike( String defValLike){
        if (defValLike == null) {
            throw new RuntimeException("defVal is null");
        }
        this.defValLike = defValLike;
        return this;
    }

    public DepReportCellDataQuery defVals(List<String> defVals){
        if (defVals == null) {
            throw new RuntimeException("defVals is empty ");
        }
        this.defVals = defVals;
        return this;
    }


    public DepReportCellDataQuery ruleJson(String ruleJson){
	if (ruleJson == null) {
	    throw new RuntimeException("ruleJson is null");
        }         
	this.ruleJson = ruleJson;
	return this;
    }

    public DepReportCellDataQuery ruleJsonLike( String ruleJsonLike){
        if (ruleJsonLike == null) {
            throw new RuntimeException("ruleJson is null");
        }
        this.ruleJsonLike = ruleJsonLike;
        return this;
    }

    public DepReportCellDataQuery ruleJsons(List<String> ruleJsons){
        if (ruleJsons == null) {
            throw new RuntimeException("ruleJsons is empty ");
        }
        this.ruleJsons = ruleJsons;
        return this;
    }


    public DepReportCellDataQuery enCondition(String enCondition){
	if (enCondition == null) {
	    throw new RuntimeException("enCondition is null");
        }         
	this.enCondition = enCondition;
	return this;
    }

    public DepReportCellDataQuery enConditionLike( String enConditionLike){
        if (enConditionLike == null) {
            throw new RuntimeException("enCondition is null");
        }
        this.enConditionLike = enConditionLike;
        return this;
    }

    public DepReportCellDataQuery enConditions(List<String> enConditions){
        if (enConditions == null) {
            throw new RuntimeException("enConditions is empty ");
        }
        this.enConditions = enConditions;
        return this;
    }


    public DepReportCellDataQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public DepReportCellDataQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public DepReportCellDataQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public DepReportCellDataQuery createDateTimeGreaterThanOrEqual(Date createDateTimeGreaterThanOrEqual){
	if (createDateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDateTime is null");
        }         
	this.createDateTimeGreaterThanOrEqual = createDateTimeGreaterThanOrEqual;
        return this;
    }

    public DepReportCellDataQuery createDateTimeLessThanOrEqual(Date createDateTimeLessThanOrEqual){
        if (createDateTimeLessThanOrEqual == null) {
            throw new RuntimeException("createDateTime is null");
        }
        this.createDateTimeLessThanOrEqual = createDateTimeLessThanOrEqual;
        return this;
    }



    public DepReportCellDataQuery modifier(String modifier){
	if (modifier == null) {
	    throw new RuntimeException("modifier is null");
        }         
	this.modifier = modifier;
	return this;
    }

    public DepReportCellDataQuery modifierLike( String modifierLike){
        if (modifierLike == null) {
            throw new RuntimeException("modifier is null");
        }
        this.modifierLike = modifierLike;
        return this;
    }

    public DepReportCellDataQuery modifiers(List<String> modifiers){
        if (modifiers == null) {
            throw new RuntimeException("modifiers is empty ");
        }
        this.modifiers = modifiers;
        return this;
    }



    public DepReportCellDataQuery modifyDateTimeGreaterThanOrEqual(Date modifyDateTimeGreaterThanOrEqual){
	if (modifyDateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("modifyDateTime is null");
        }         
	this.modifyDateTimeGreaterThanOrEqual = modifyDateTimeGreaterThanOrEqual;
        return this;
    }

    public DepReportCellDataQuery modifyDateTimeLessThanOrEqual(Date modifyDateTimeLessThanOrEqual){
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

            if ("inputMode".equals(sortColumn)) {
                orderBy = "E.INPUTMODE_" + a_x;
            } 

            if ("readOnly".equals(sortColumn)) {
                orderBy = "E.READONLY_" + a_x;
            } 

            if ("dtype".equals(sortColumn)) {
                orderBy = "E.DTYPE_" + a_x;
            } 

            if ("defVal".equals(sortColumn)) {
                orderBy = "E.DEFVAL_" + a_x;
            } 

            if ("ruleJson".equals(sortColumn)) {
                orderBy = "E.RULEJSON_" + a_x;
            } 

            if ("enCondition".equals(sortColumn)) {
                orderBy = "E.ENCONDITON_" + a_x;
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
        addColumn("inputMode", "INPUTMODE_");
        addColumn("readOnly", "READONLY_");
        addColumn("dtype", "DTYPE_");
        addColumn("defVal", "DEFVAL_");
        addColumn("ruleJson", "RULEJSON_");
        addColumn("enCondition", "ENCONDITON_");
        addColumn("creator", "CREATOR_");
        addColumn("createDateTime", "CREATEDATETIME_");
        addColumn("modifier", "MODIFIER_");
        addColumn("modifyDateTime", "MODIFYDATETIME_");
    }

}