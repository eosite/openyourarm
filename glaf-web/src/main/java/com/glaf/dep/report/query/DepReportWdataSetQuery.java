package com.glaf.dep.report.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DepReportWdataSetQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
  	protected Long wdatasetId;
  	protected Long wdatasetIdGreaterThanOrEqual;
  	protected Long wdatasetIdLessThanOrEqual;
  	protected List<Long> wdatasetIds;
  	protected Long repTemplateId;
  	protected Long repTemplateIdGreaterThanOrEqual;
  	protected Long repTemplateIdLessThanOrEqual;
  	protected List<Long> repTemplateIds;
  	protected String code;
  	protected String codeLike;
  	protected List<String> codes;
  	protected String name;
  	protected String nameLike;
  	protected List<String> names;
  	protected String tableName;
  	protected String tableNameLike;
  	protected List<String> tableNames;
  	protected String dataTableName;
  	protected String dataTableNameLike;
  	protected List<String> dataTableNames;
  	protected String enConditon;
  	protected String enConditonLike;
  	protected List<String> enConditons;
  	protected Integer order;
  	protected Integer orderGreaterThanOrEqual;
  	protected Integer orderLessThanOrEqual;
  	protected List<Integer> orders;
  	protected String ruleJson;
  	protected String ruleJsonLike;
  	protected List<String> ruleJsons;
  	protected String psql;
  	protected String psqlLike;
  	protected List<String> psqls;
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

    public DepReportWdataSetQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public Long getWdatasetId(){
        return wdatasetId;
    }

    public Long getWdatasetIdGreaterThanOrEqual(){
        return wdatasetIdGreaterThanOrEqual;
    }

    public Long getWdatasetIdLessThanOrEqual(){
	return wdatasetIdLessThanOrEqual;
    }

    public List<Long> getWdatasetIds(){
	return wdatasetIds;
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


    public String getDataTableName(){
        return dataTableName;
    }

    public String getDataTableNameLike(){
	    if (dataTableNameLike != null && dataTableNameLike.trim().length() > 0) {
		if (!dataTableNameLike.startsWith("%")) {
		    dataTableNameLike = "%" + dataTableNameLike;
		}
		if (!dataTableNameLike.endsWith("%")) {
		   dataTableNameLike = dataTableNameLike + "%";
		}
	    }
	return dataTableNameLike;
    }

    public List<String> getDataTableNames(){
	return dataTableNames;
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


    public Integer getOrder(){
        return order;
    }

    public Integer getOrderGreaterThanOrEqual(){
        return orderGreaterThanOrEqual;
    }

    public Integer getOrderLessThanOrEqual(){
	return orderLessThanOrEqual;
    }

    public List<Integer> getOrders(){
	return orders;
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


    public String getPsql(){
        return psql;
    }

    public String getPsqlLike(){
	    if (psqlLike != null && psqlLike.trim().length() > 0) {
		if (!psqlLike.startsWith("%")) {
		    psqlLike = "%" + psqlLike;
		}
		if (!psqlLike.endsWith("%")) {
		   psqlLike = psqlLike + "%";
		}
	    }
	return psqlLike;
    }

    public List<String> getPsqls(){
	return psqls;
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


 

    public void setWdatasetId(Long wdatasetId){
        this.wdatasetId = wdatasetId;
    }

    public void setWdatasetIdGreaterThanOrEqual(Long wdatasetIdGreaterThanOrEqual){
        this.wdatasetIdGreaterThanOrEqual = wdatasetIdGreaterThanOrEqual;
    }

    public void setWdatasetIdLessThanOrEqual(Long wdatasetIdLessThanOrEqual){
	this.wdatasetIdLessThanOrEqual = wdatasetIdLessThanOrEqual;
    }

    public void setWdatasetIds(List<Long> wdatasetIds){
        this.wdatasetIds = wdatasetIds;
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


    public void setTableName(String tableName){
        this.tableName = tableName;
    }

    public void setTableNameLike( String tableNameLike){
	this.tableNameLike = tableNameLike;
    }

    public void setTableNames(List<String> tableNames){
        this.tableNames = tableNames;
    }


    public void setDataTableName(String dataTableName){
        this.dataTableName = dataTableName;
    }

    public void setDataTableNameLike( String dataTableNameLike){
	this.dataTableNameLike = dataTableNameLike;
    }

    public void setDataTableNames(List<String> dataTableNames){
        this.dataTableNames = dataTableNames;
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


    public void setOrder(Integer order){
        this.order = order;
    }

    public void setOrderGreaterThanOrEqual(Integer orderGreaterThanOrEqual){
        this.orderGreaterThanOrEqual = orderGreaterThanOrEqual;
    }

    public void setOrderLessThanOrEqual(Integer orderLessThanOrEqual){
	this.orderLessThanOrEqual = orderLessThanOrEqual;
    }

    public void setOrders(List<Integer> orders){
        this.orders = orders;
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


    public void setPsql(String psql){
        this.psql = psql;
    }

    public void setPsqlLike( String psqlLike){
	this.psqlLike = psqlLike;
    }

    public void setPsqls(List<String> psqls){
        this.psqls = psqls;
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




    public DepReportWdataSetQuery wdatasetId(Long wdatasetId){
	if (wdatasetId == null) {
            throw new RuntimeException("wdatasetId is null");
        }         
	this.wdatasetId = wdatasetId;
	return this;
    }

    public DepReportWdataSetQuery wdatasetIdGreaterThanOrEqual(Long wdatasetIdGreaterThanOrEqual){
	if (wdatasetIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("wdatasetId is null");
        }         
	this.wdatasetIdGreaterThanOrEqual = wdatasetIdGreaterThanOrEqual;
        return this;
    }

    public DepReportWdataSetQuery wdatasetIdLessThanOrEqual(Long wdatasetIdLessThanOrEqual){
        if (wdatasetIdLessThanOrEqual == null) {
            throw new RuntimeException("wdatasetId is null");
        }
        this.wdatasetIdLessThanOrEqual = wdatasetIdLessThanOrEqual;
        return this;
    }

    public DepReportWdataSetQuery wdatasetIds(List<Long> wdatasetIds){
        if (wdatasetIds == null) {
            throw new RuntimeException("wdatasetIds is empty ");
        }
        this.wdatasetIds = wdatasetIds;
        return this;
    }


    public DepReportWdataSetQuery repTemplateId(Long repTemplateId){
	if (repTemplateId == null) {
            throw new RuntimeException("repTemplateId is null");
        }         
	this.repTemplateId = repTemplateId;
	return this;
    }

    public DepReportWdataSetQuery repTemplateIdGreaterThanOrEqual(Long repTemplateIdGreaterThanOrEqual){
	if (repTemplateIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("repTemplateId is null");
        }         
	this.repTemplateIdGreaterThanOrEqual = repTemplateIdGreaterThanOrEqual;
        return this;
    }

    public DepReportWdataSetQuery repTemplateIdLessThanOrEqual(Long repTemplateIdLessThanOrEqual){
        if (repTemplateIdLessThanOrEqual == null) {
            throw new RuntimeException("repTemplateId is null");
        }
        this.repTemplateIdLessThanOrEqual = repTemplateIdLessThanOrEqual;
        return this;
    }

    public DepReportWdataSetQuery repTemplateIds(List<Long> repTemplateIds){
        if (repTemplateIds == null) {
            throw new RuntimeException("repTemplateIds is empty ");
        }
        this.repTemplateIds = repTemplateIds;
        return this;
    }


    public DepReportWdataSetQuery code(String code){
	if (code == null) {
	    throw new RuntimeException("code is null");
        }         
	this.code = code;
	return this;
    }

    public DepReportWdataSetQuery codeLike( String codeLike){
        if (codeLike == null) {
            throw new RuntimeException("code is null");
        }
        this.codeLike = codeLike;
        return this;
    }

    public DepReportWdataSetQuery codes(List<String> codes){
        if (codes == null) {
            throw new RuntimeException("codes is empty ");
        }
        this.codes = codes;
        return this;
    }


    public DepReportWdataSetQuery name(String name){
	if (name == null) {
	    throw new RuntimeException("name is null");
        }         
	this.name = name;
	return this;
    }

    public DepReportWdataSetQuery nameLike( String nameLike){
        if (nameLike == null) {
            throw new RuntimeException("name is null");
        }
        this.nameLike = nameLike;
        return this;
    }

    public DepReportWdataSetQuery names(List<String> names){
        if (names == null) {
            throw new RuntimeException("names is empty ");
        }
        this.names = names;
        return this;
    }


    public DepReportWdataSetQuery tableName(String tableName){
	if (tableName == null) {
	    throw new RuntimeException("tableName is null");
        }         
	this.tableName = tableName;
	return this;
    }

    public DepReportWdataSetQuery tableNameLike( String tableNameLike){
        if (tableNameLike == null) {
            throw new RuntimeException("tableName is null");
        }
        this.tableNameLike = tableNameLike;
        return this;
    }

    public DepReportWdataSetQuery tableNames(List<String> tableNames){
        if (tableNames == null) {
            throw new RuntimeException("tableNames is empty ");
        }
        this.tableNames = tableNames;
        return this;
    }


    public DepReportWdataSetQuery dataTableName(String dataTableName){
	if (dataTableName == null) {
	    throw new RuntimeException("dataTableName is null");
        }         
	this.dataTableName = dataTableName;
	return this;
    }

    public DepReportWdataSetQuery dataTableNameLike( String dataTableNameLike){
        if (dataTableNameLike == null) {
            throw new RuntimeException("dataTableName is null");
        }
        this.dataTableNameLike = dataTableNameLike;
        return this;
    }

    public DepReportWdataSetQuery dataTableNames(List<String> dataTableNames){
        if (dataTableNames == null) {
            throw new RuntimeException("dataTableNames is empty ");
        }
        this.dataTableNames = dataTableNames;
        return this;
    }


    public DepReportWdataSetQuery enConditon(String enConditon){
	if (enConditon == null) {
	    throw new RuntimeException("enConditon is null");
        }         
	this.enConditon = enConditon;
	return this;
    }

    public DepReportWdataSetQuery enConditonLike( String enConditonLike){
        if (enConditonLike == null) {
            throw new RuntimeException("enConditon is null");
        }
        this.enConditonLike = enConditonLike;
        return this;
    }

    public DepReportWdataSetQuery enConditons(List<String> enConditons){
        if (enConditons == null) {
            throw new RuntimeException("enConditons is empty ");
        }
        this.enConditons = enConditons;
        return this;
    }


    public DepReportWdataSetQuery order(Integer order){
	if (order == null) {
            throw new RuntimeException("order is null");
        }         
	this.order = order;
	return this;
    }

    public DepReportWdataSetQuery orderGreaterThanOrEqual(Integer orderGreaterThanOrEqual){
	if (orderGreaterThanOrEqual == null) {
	    throw new RuntimeException("order is null");
        }         
	this.orderGreaterThanOrEqual = orderGreaterThanOrEqual;
        return this;
    }

    public DepReportWdataSetQuery orderLessThanOrEqual(Integer orderLessThanOrEqual){
        if (orderLessThanOrEqual == null) {
            throw new RuntimeException("order is null");
        }
        this.orderLessThanOrEqual = orderLessThanOrEqual;
        return this;
    }

    public DepReportWdataSetQuery orders(List<Integer> orders){
        if (orders == null) {
            throw new RuntimeException("orders is empty ");
        }
        this.orders = orders;
        return this;
    }


    public DepReportWdataSetQuery ruleJson(String ruleJson){
	if (ruleJson == null) {
	    throw new RuntimeException("ruleJson is null");
        }         
	this.ruleJson = ruleJson;
	return this;
    }

    public DepReportWdataSetQuery ruleJsonLike( String ruleJsonLike){
        if (ruleJsonLike == null) {
            throw new RuntimeException("ruleJson is null");
        }
        this.ruleJsonLike = ruleJsonLike;
        return this;
    }

    public DepReportWdataSetQuery ruleJsons(List<String> ruleJsons){
        if (ruleJsons == null) {
            throw new RuntimeException("ruleJsons is empty ");
        }
        this.ruleJsons = ruleJsons;
        return this;
    }


    public DepReportWdataSetQuery psql(String psql){
	if (psql == null) {
	    throw new RuntimeException("psql is null");
        }         
	this.psql = psql;
	return this;
    }

    public DepReportWdataSetQuery psqlLike( String psqlLike){
        if (psqlLike == null) {
            throw new RuntimeException("psql is null");
        }
        this.psqlLike = psqlLike;
        return this;
    }

    public DepReportWdataSetQuery psqls(List<String> psqls){
        if (psqls == null) {
            throw new RuntimeException("psqls is empty ");
        }
        this.psqls = psqls;
        return this;
    }


    public DepReportWdataSetQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public DepReportWdataSetQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public DepReportWdataSetQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public DepReportWdataSetQuery createDateTimeGreaterThanOrEqual(Date createDateTimeGreaterThanOrEqual){
	if (createDateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDateTime is null");
        }         
	this.createDateTimeGreaterThanOrEqual = createDateTimeGreaterThanOrEqual;
        return this;
    }

    public DepReportWdataSetQuery createDateTimeLessThanOrEqual(Date createDateTimeLessThanOrEqual){
        if (createDateTimeLessThanOrEqual == null) {
            throw new RuntimeException("createDateTime is null");
        }
        this.createDateTimeLessThanOrEqual = createDateTimeLessThanOrEqual;
        return this;
    }



    public DepReportWdataSetQuery modifier(String modifier){
	if (modifier == null) {
	    throw new RuntimeException("modifier is null");
        }         
	this.modifier = modifier;
	return this;
    }

    public DepReportWdataSetQuery modifierLike( String modifierLike){
        if (modifierLike == null) {
            throw new RuntimeException("modifier is null");
        }
        this.modifierLike = modifierLike;
        return this;
    }

    public DepReportWdataSetQuery modifiers(List<String> modifiers){
        if (modifiers == null) {
            throw new RuntimeException("modifiers is empty ");
        }
        this.modifiers = modifiers;
        return this;
    }



    public DepReportWdataSetQuery modifyDateTimeGreaterThanOrEqual(Date modifyDateTimeGreaterThanOrEqual){
	if (modifyDateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("modifyDateTime is null");
        }         
	this.modifyDateTimeGreaterThanOrEqual = modifyDateTimeGreaterThanOrEqual;
        return this;
    }

    public DepReportWdataSetQuery modifyDateTimeLessThanOrEqual(Date modifyDateTimeLessThanOrEqual){
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

            if ("wdatasetId".equals(sortColumn)) {
                orderBy = "E.WDATASET_ID_" + a_x;
            } 

            if ("repTemplateId".equals(sortColumn)) {
                orderBy = "E.REPTEMPLATE_ID_" + a_x;
            } 

            if ("code".equals(sortColumn)) {
                orderBy = "E.CODE_" + a_x;
            } 

            if ("name".equals(sortColumn)) {
                orderBy = "E.NAME_" + a_x;
            } 

            if ("tableName".equals(sortColumn)) {
                orderBy = "E.TABLE_NAME_" + a_x;
            } 

            if ("dataTableName".equals(sortColumn)) {
                orderBy = "E.DATATABLE_NAME_" + a_x;
            } 

            if ("enConditon".equals(sortColumn)) {
                orderBy = "E.ENCONDITON_" + a_x;
            } 

            if ("order".equals(sortColumn)) {
                orderBy = "E.ORDER_" + a_x;
            } 

            if ("ruleJson".equals(sortColumn)) {
                orderBy = "E.RULEJSON_" + a_x;
            } 

            if ("psql".equals(sortColumn)) {
                orderBy = "E.PSQL_" + a_x;
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
        addColumn("wdatasetId", "WDATASET_ID_");
        addColumn("repTemplateId", "REPTEMPLATE_ID_");
        addColumn("code", "CODE_");
        addColumn("name", "NAME_");
        addColumn("tableName", "TABLE_NAME_");
        addColumn("dataTableName", "DATATABLE_NAME_");
        addColumn("enConditon", "ENCONDITON_");
        addColumn("order", "ORDER_");
        addColumn("ruleJson", "RULEJSON_");
        addColumn("psql", "PSQL_");
        addColumn("creator", "CREATOR_");
        addColumn("createDateTime", "CREATEDATETIME_");
        addColumn("modifier", "MODIFIER_");
        addColumn("modifyDateTime", "MODIFYDATETIME_");
    }

}