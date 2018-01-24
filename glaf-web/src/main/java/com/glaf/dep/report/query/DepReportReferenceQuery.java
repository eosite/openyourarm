package com.glaf.dep.report.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DepReportReferenceQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
  	protected Long repDataSetId;
  	protected Long repDataSetIdGreaterThanOrEqual;
  	protected Long repDataSetIdLessThanOrEqual;
  	protected List<Long> repDataSetIds;
  	protected Long repDataId;
  	protected Long repDataIdGreaterThanOrEqual;
  	protected Long repDataIdLessThanOrEqual;
  	protected List<Long> repDataIds;
  	protected String refType;
  	protected String refTypeLike;
  	protected List<String> refTypes;
  	protected String refMode;
  	protected String refModeLike;
  	protected List<String> refModes;
  	protected String enCondition;
  	protected String enConditionLike;
  	protected List<String> enConditions;
  	protected String columnName;
  	protected String columnNameLike;
  	protected List<String> columnNames;
  	protected String tableName;
  	protected String tableNameLike;
  	protected List<String> tableNames;
  	protected String reportId;
  	protected String reportIdLike;
  	protected List<String> reportIds;
  	protected String reportCellId;
  	protected String reportCellIdLike;
  	protected List<String> reportCellIds;
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

    public DepReportReferenceQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public Long getRepDataSetId(){
        return repDataSetId;
    }

    public Long getRepDataSetIdGreaterThanOrEqual(){
        return repDataSetIdGreaterThanOrEqual;
    }

    public Long getRepDataSetIdLessThanOrEqual(){
	return repDataSetIdLessThanOrEqual;
    }

    public List<Long> getRepDataSetIds(){
	return repDataSetIds;
    }

    public Long getRepDataId(){
        return repDataId;
    }

    public Long getRepDataIdGreaterThanOrEqual(){
        return repDataIdGreaterThanOrEqual;
    }

    public Long getRepDataIdLessThanOrEqual(){
	return repDataIdLessThanOrEqual;
    }

    public List<Long> getRepDataIds(){
	return repDataIds;
    }

    public String getRefType(){
        return refType;
    }

    public String getRefTypeLike(){
	    if (refTypeLike != null && refTypeLike.trim().length() > 0) {
		if (!refTypeLike.startsWith("%")) {
		    refTypeLike = "%" + refTypeLike;
		}
		if (!refTypeLike.endsWith("%")) {
		   refTypeLike = refTypeLike + "%";
		}
	    }
	return refTypeLike;
    }

    public List<String> getRefTypes(){
	return refTypes;
    }


    public String getRefMode(){
        return refMode;
    }

    public String getRefModeLike(){
	    if (refModeLike != null && refModeLike.trim().length() > 0) {
		if (!refModeLike.startsWith("%")) {
		    refModeLike = "%" + refModeLike;
		}
		if (!refModeLike.endsWith("%")) {
		   refModeLike = refModeLike + "%";
		}
	    }
	return refModeLike;
    }

    public List<String> getRefModes(){
	return refModes;
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


    public String getColumnName(){
        return columnName;
    }

    public String getColumnNameLike(){
	    if (columnNameLike != null && columnNameLike.trim().length() > 0) {
		if (!columnNameLike.startsWith("%")) {
		    columnNameLike = "%" + columnNameLike;
		}
		if (!columnNameLike.endsWith("%")) {
		   columnNameLike = columnNameLike + "%";
		}
	    }
	return columnNameLike;
    }

    public List<String> getColumnNames(){
	return columnNames;
    }


    public String getTableName(){
        return tableName;
    }

    public String getTableNameLike(){
	    if (tableNameLike != null && tableNameLike.trim().length() > 0) {
		if (!tableNameLike.startsWith("%")) {
		    tableNameLike = "%" + tableNameLike;
		}
		if (!tableNameLike.endsWith("%")) {
		   tableNameLike = tableNameLike + "%";
		}
	    }
	return tableNameLike;
    }

    public List<String> getTableNames(){
	return tableNames;
    }


    public String getReportId(){
        return reportId;
    }

    public String getReportIdLike(){
	    if (reportIdLike != null && reportIdLike.trim().length() > 0) {
		if (!reportIdLike.startsWith("%")) {
		    reportIdLike = "%" + reportIdLike;
		}
		if (!reportIdLike.endsWith("%")) {
		   reportIdLike = reportIdLike + "%";
		}
	    }
	return reportIdLike;
    }

    public List<String> getReportIds(){
	return reportIds;
    }


    public String getReportCellId(){
        return reportCellId;
    }

    public String getReportCellIdLike(){
	    if (reportCellIdLike != null && reportCellIdLike.trim().length() > 0) {
		if (!reportCellIdLike.startsWith("%")) {
		    reportCellIdLike = "%" + reportCellIdLike;
		}
		if (!reportCellIdLike.endsWith("%")) {
		   reportCellIdLike = reportCellIdLike + "%";
		}
	    }
	return reportCellIdLike;
    }

    public List<String> getReportCellIds(){
	return reportCellIds;
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


 

    public void setRepDataSetId(Long repDataSetId){
        this.repDataSetId = repDataSetId;
    }

    public void setRepDataSetIdGreaterThanOrEqual(Long repDataSetIdGreaterThanOrEqual){
        this.repDataSetIdGreaterThanOrEqual = repDataSetIdGreaterThanOrEqual;
    }

    public void setRepDataSetIdLessThanOrEqual(Long repDataSetIdLessThanOrEqual){
	this.repDataSetIdLessThanOrEqual = repDataSetIdLessThanOrEqual;
    }

    public void setRepDataSetIds(List<Long> repDataSetIds){
        this.repDataSetIds = repDataSetIds;
    }


    public void setRepDataId(Long repDataId){
        this.repDataId = repDataId;
    }

    public void setRepDataIdGreaterThanOrEqual(Long repDataIdGreaterThanOrEqual){
        this.repDataIdGreaterThanOrEqual = repDataIdGreaterThanOrEqual;
    }

    public void setRepDataIdLessThanOrEqual(Long repDataIdLessThanOrEqual){
	this.repDataIdLessThanOrEqual = repDataIdLessThanOrEqual;
    }

    public void setRepDataIds(List<Long> repDataIds){
        this.repDataIds = repDataIds;
    }


    public void setRefType(String refType){
        this.refType = refType;
    }

    public void setRefTypeLike( String refTypeLike){
	this.refTypeLike = refTypeLike;
    }

    public void setRefTypes(List<String> refTypes){
        this.refTypes = refTypes;
    }


    public void setRefMode(String refMode){
        this.refMode = refMode;
    }

    public void setRefModeLike( String refModeLike){
	this.refModeLike = refModeLike;
    }

    public void setRefModes(List<String> refModes){
        this.refModes = refModes;
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


    public void setColumnName(String columnName){
        this.columnName = columnName;
    }

    public void setColumnNameLike( String columnNameLike){
	this.columnNameLike = columnNameLike;
    }

    public void setColumnNames(List<String> columnNames){
        this.columnNames = columnNames;
    }


    public void setTableName(String tableName){
        this.tableName = tableName;
    }

    public void setTableNameLike( String tableNameLike){
	this.tableNameLike = tableNameLike;
    }

    public void setTableNames(List<String> tableNames){
        this.tableNames = tableNames;
    }


    public void setReportId(String reportId){
        this.reportId = reportId;
    }

    public void setReportIdLike( String reportIdLike){
	this.reportIdLike = reportIdLike;
    }

    public void setReportIds(List<String> reportIds){
        this.reportIds = reportIds;
    }


    public void setReportCellId(String reportCellId){
        this.reportCellId = reportCellId;
    }

    public void setReportCellIdLike( String reportCellIdLike){
	this.reportCellIdLike = reportCellIdLike;
    }

    public void setReportCellIds(List<String> reportCellIds){
        this.reportCellIds = reportCellIds;
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




    public DepReportReferenceQuery repDataSetId(Long repDataSetId){
	if (repDataSetId == null) {
            throw new RuntimeException("repDataSetId is null");
        }         
	this.repDataSetId = repDataSetId;
	return this;
    }

    public DepReportReferenceQuery repDataSetIdGreaterThanOrEqual(Long repDataSetIdGreaterThanOrEqual){
	if (repDataSetIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("repDataSetId is null");
        }         
	this.repDataSetIdGreaterThanOrEqual = repDataSetIdGreaterThanOrEqual;
        return this;
    }

    public DepReportReferenceQuery repDataSetIdLessThanOrEqual(Long repDataSetIdLessThanOrEqual){
        if (repDataSetIdLessThanOrEqual == null) {
            throw new RuntimeException("repDataSetId is null");
        }
        this.repDataSetIdLessThanOrEqual = repDataSetIdLessThanOrEqual;
        return this;
    }

    public DepReportReferenceQuery repDataSetIds(List<Long> repDataSetIds){
        if (repDataSetIds == null) {
            throw new RuntimeException("repDataSetIds is empty ");
        }
        this.repDataSetIds = repDataSetIds;
        return this;
    }


    public DepReportReferenceQuery repDataId(Long repDataId){
	if (repDataId == null) {
            throw new RuntimeException("repDataId is null");
        }         
	this.repDataId = repDataId;
	return this;
    }

    public DepReportReferenceQuery repDataIdGreaterThanOrEqual(Long repDataIdGreaterThanOrEqual){
	if (repDataIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("repDataId is null");
        }         
	this.repDataIdGreaterThanOrEqual = repDataIdGreaterThanOrEqual;
        return this;
    }

    public DepReportReferenceQuery repDataIdLessThanOrEqual(Long repDataIdLessThanOrEqual){
        if (repDataIdLessThanOrEqual == null) {
            throw new RuntimeException("repDataId is null");
        }
        this.repDataIdLessThanOrEqual = repDataIdLessThanOrEqual;
        return this;
    }

    public DepReportReferenceQuery repDataIds(List<Long> repDataIds){
        if (repDataIds == null) {
            throw new RuntimeException("repDataIds is empty ");
        }
        this.repDataIds = repDataIds;
        return this;
    }


    public DepReportReferenceQuery refType(String refType){
	if (refType == null) {
	    throw new RuntimeException("refType is null");
        }         
	this.refType = refType;
	return this;
    }

    public DepReportReferenceQuery refTypeLike( String refTypeLike){
        if (refTypeLike == null) {
            throw new RuntimeException("refType is null");
        }
        this.refTypeLike = refTypeLike;
        return this;
    }

    public DepReportReferenceQuery refTypes(List<String> refTypes){
        if (refTypes == null) {
            throw new RuntimeException("refTypes is empty ");
        }
        this.refTypes = refTypes;
        return this;
    }


    public DepReportReferenceQuery refMode(String refMode){
	if (refMode == null) {
	    throw new RuntimeException("refMode is null");
        }         
	this.refMode = refMode;
	return this;
    }

    public DepReportReferenceQuery refModeLike( String refModeLike){
        if (refModeLike == null) {
            throw new RuntimeException("refMode is null");
        }
        this.refModeLike = refModeLike;
        return this;
    }

    public DepReportReferenceQuery refModes(List<String> refModes){
        if (refModes == null) {
            throw new RuntimeException("refModes is empty ");
        }
        this.refModes = refModes;
        return this;
    }


    public DepReportReferenceQuery enCondition(String enCondition){
	if (enCondition == null) {
	    throw new RuntimeException("enCondition is null");
        }         
	this.enCondition = enCondition;
	return this;
    }

    public DepReportReferenceQuery enConditionLike( String enConditionLike){
        if (enConditionLike == null) {
            throw new RuntimeException("enCondition is null");
        }
        this.enConditionLike = enConditionLike;
        return this;
    }

    public DepReportReferenceQuery enConditions(List<String> enConditions){
        if (enConditions == null) {
            throw new RuntimeException("enConditions is empty ");
        }
        this.enConditions = enConditions;
        return this;
    }


    public DepReportReferenceQuery columnName(String columnName){
	if (columnName == null) {
	    throw new RuntimeException("columnName is null");
        }         
	this.columnName = columnName;
	return this;
    }

    public DepReportReferenceQuery columnNameLike( String columnNameLike){
        if (columnNameLike == null) {
            throw new RuntimeException("columnName is null");
        }
        this.columnNameLike = columnNameLike;
        return this;
    }

    public DepReportReferenceQuery columnNames(List<String> columnNames){
        if (columnNames == null) {
            throw new RuntimeException("columnNames is empty ");
        }
        this.columnNames = columnNames;
        return this;
    }


    public DepReportReferenceQuery tableName(String tableName){
	if (tableName == null) {
	    throw new RuntimeException("tableName is null");
        }         
	this.tableName = tableName;
	return this;
    }

    public DepReportReferenceQuery tableNameLike( String tableNameLike){
        if (tableNameLike == null) {
            throw new RuntimeException("tableName is null");
        }
        this.tableNameLike = tableNameLike;
        return this;
    }

    public DepReportReferenceQuery tableNames(List<String> tableNames){
        if (tableNames == null) {
            throw new RuntimeException("tableNames is empty ");
        }
        this.tableNames = tableNames;
        return this;
    }


    public DepReportReferenceQuery reportId(String reportId){
	if (reportId == null) {
	    throw new RuntimeException("reportId is null");
        }         
	this.reportId = reportId;
	return this;
    }

    public DepReportReferenceQuery reportIdLike( String reportIdLike){
        if (reportIdLike == null) {
            throw new RuntimeException("reportId is null");
        }
        this.reportIdLike = reportIdLike;
        return this;
    }

    public DepReportReferenceQuery reportIds(List<String> reportIds){
        if (reportIds == null) {
            throw new RuntimeException("reportIds is empty ");
        }
        this.reportIds = reportIds;
        return this;
    }


    public DepReportReferenceQuery reportCellId(String reportCellId){
	if (reportCellId == null) {
	    throw new RuntimeException("reportCellId is null");
        }         
	this.reportCellId = reportCellId;
	return this;
    }

    public DepReportReferenceQuery reportCellIdLike( String reportCellIdLike){
        if (reportCellIdLike == null) {
            throw new RuntimeException("reportCellId is null");
        }
        this.reportCellIdLike = reportCellIdLike;
        return this;
    }

    public DepReportReferenceQuery reportCellIds(List<String> reportCellIds){
        if (reportCellIds == null) {
            throw new RuntimeException("reportCellIds is empty ");
        }
        this.reportCellIds = reportCellIds;
        return this;
    }


    public DepReportReferenceQuery ruleJson(String ruleJson){
	if (ruleJson == null) {
	    throw new RuntimeException("ruleJson is null");
        }         
	this.ruleJson = ruleJson;
	return this;
    }

    public DepReportReferenceQuery ruleJsonLike( String ruleJsonLike){
        if (ruleJsonLike == null) {
            throw new RuntimeException("ruleJson is null");
        }
        this.ruleJsonLike = ruleJsonLike;
        return this;
    }

    public DepReportReferenceQuery ruleJsons(List<String> ruleJsons){
        if (ruleJsons == null) {
            throw new RuntimeException("ruleJsons is empty ");
        }
        this.ruleJsons = ruleJsons;
        return this;
    }


    public DepReportReferenceQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public DepReportReferenceQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public DepReportReferenceQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public DepReportReferenceQuery createDateTimeGreaterThanOrEqual(Date createDateTimeGreaterThanOrEqual){
	if (createDateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDateTime is null");
        }         
	this.createDateTimeGreaterThanOrEqual = createDateTimeGreaterThanOrEqual;
        return this;
    }

    public DepReportReferenceQuery createDateTimeLessThanOrEqual(Date createDateTimeLessThanOrEqual){
        if (createDateTimeLessThanOrEqual == null) {
            throw new RuntimeException("createDateTime is null");
        }
        this.createDateTimeLessThanOrEqual = createDateTimeLessThanOrEqual;
        return this;
    }



    public DepReportReferenceQuery modifier(String modifier){
	if (modifier == null) {
	    throw new RuntimeException("modifier is null");
        }         
	this.modifier = modifier;
	return this;
    }

    public DepReportReferenceQuery modifierLike( String modifierLike){
        if (modifierLike == null) {
            throw new RuntimeException("modifier is null");
        }
        this.modifierLike = modifierLike;
        return this;
    }

    public DepReportReferenceQuery modifiers(List<String> modifiers){
        if (modifiers == null) {
            throw new RuntimeException("modifiers is empty ");
        }
        this.modifiers = modifiers;
        return this;
    }



    public DepReportReferenceQuery modifyDateTimeGreaterThanOrEqual(Date modifyDateTimeGreaterThanOrEqual){
	if (modifyDateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("modifyDateTime is null");
        }         
	this.modifyDateTimeGreaterThanOrEqual = modifyDateTimeGreaterThanOrEqual;
        return this;
    }

    public DepReportReferenceQuery modifyDateTimeLessThanOrEqual(Date modifyDateTimeLessThanOrEqual){
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

            if ("repDataSetId".equals(sortColumn)) {
                orderBy = "E.REPDATASET_ID_" + a_x;
            } 

            if ("repDataId".equals(sortColumn)) {
                orderBy = "E.REPDATA_ID_" + a_x;
            } 

            if ("refType".equals(sortColumn)) {
                orderBy = "E.REFTYPE_" + a_x;
            } 

            if ("refMode".equals(sortColumn)) {
                orderBy = "E.REFMODE_" + a_x;
            } 

            if ("enCondition".equals(sortColumn)) {
                orderBy = "E.ENCONDITON_" + a_x;
            } 

            if ("columnName".equals(sortColumn)) {
                orderBy = "E.COLUMNNAME_" + a_x;
            } 

            if ("tableName".equals(sortColumn)) {
                orderBy = "E.TABLENAME_" + a_x;
            } 

            if ("reportId".equals(sortColumn)) {
                orderBy = "E.REPORT_ID_" + a_x;
            } 

            if ("reportCellId".equals(sortColumn)) {
                orderBy = "E.REPORT_CELL_ID_" + a_x;
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
        addColumn("repDataSetId", "REPDATASET_ID_");
        addColumn("repDataId", "REPDATA_ID_");
        addColumn("refType", "REFTYPE_");
        addColumn("refMode", "REFMODE_");
        addColumn("enCondition", "ENCONDITON_");
        addColumn("columnName", "COLUMNNAME_");
        addColumn("tableName", "TABLENAME_");
        addColumn("reportId", "REPORT_ID_");
        addColumn("reportCellId", "REPORT_CELL_ID_");
        addColumn("ruleJson", "RULEJSON_");
        addColumn("creator", "CREATOR_");
        addColumn("createDateTime", "CREATEDATETIME_");
        addColumn("modifier", "MODIFIER_");
        addColumn("modifyDateTime", "MODIFYDATETIME_");
    }

}