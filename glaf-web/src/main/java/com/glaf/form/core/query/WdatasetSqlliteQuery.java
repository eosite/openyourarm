package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class WdatasetSqlliteQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
  	protected String sqlliteRuleCode;
  	protected String sqlliteRuleCodeLike;
  	protected List<String> sqlliteRuleCodes;
  	protected String sqlliteRuleDesc;
  	protected String sqlliteRuleDescLike;
  	protected List<String> sqlliteRuleDescs;
  	protected String sqlliteRuleName;
  	protected String sqlliteRuleNameLike;
  	protected List<String> sqlliteRuleNames;
  	protected String dataSetsName;
  	protected String dataSetsNameLike;
  	protected List<String> dataSetsNames;
  	protected String delflag;
  	protected String delflagLike;
  	protected List<String> delflags;
  	protected String ruleJson;
  	protected String ruleJsonLike;
  	protected List<String> ruleJsons;
  	protected String createBy;
  	protected String createByLike;
  	protected List<String> createBys;
        protected Date createDateGreaterThanOrEqual;
  	protected Date createDateLessThanOrEqual;
  	protected String updateBy;
  	protected String updateByLike;
  	protected List<String> updateBys;
        protected Date updateDateGreaterThanOrEqual;
  	protected Date updateDateLessThanOrEqual;

    public WdatasetSqlliteQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getSqlliteRuleCode(){
        return sqlliteRuleCode;
    }

    public String getSqlliteRuleCodeLike(){
	    if (sqlliteRuleCodeLike != null && sqlliteRuleCodeLike.trim().length() > 0) {
		if (!sqlliteRuleCodeLike.startsWith("%")) {
		    sqlliteRuleCodeLike = "%" + sqlliteRuleCodeLike;
		}
		if (!sqlliteRuleCodeLike.endsWith("%")) {
		   sqlliteRuleCodeLike = sqlliteRuleCodeLike + "%";
		}
	    }
	return sqlliteRuleCodeLike;
    }

    public List<String> getSqlliteRuleCodes(){
	return sqlliteRuleCodes;
    }


    public String getSqlliteRuleDesc(){
        return sqlliteRuleDesc;
    }

    public String getSqlliteRuleDescLike(){
	    if (sqlliteRuleDescLike != null && sqlliteRuleDescLike.trim().length() > 0) {
		if (!sqlliteRuleDescLike.startsWith("%")) {
		    sqlliteRuleDescLike = "%" + sqlliteRuleDescLike;
		}
		if (!sqlliteRuleDescLike.endsWith("%")) {
		   sqlliteRuleDescLike = sqlliteRuleDescLike + "%";
		}
	    }
	return sqlliteRuleDescLike;
    }

    public List<String> getSqlliteRuleDescs(){
	return sqlliteRuleDescs;
    }


    public String getSqlliteRuleName(){
        return sqlliteRuleName;
    }

    public String getSqlliteRuleNameLike(){
	    if (sqlliteRuleNameLike != null && sqlliteRuleNameLike.trim().length() > 0) {
		if (!sqlliteRuleNameLike.startsWith("%")) {
		    sqlliteRuleNameLike = "%" + sqlliteRuleNameLike;
		}
		if (!sqlliteRuleNameLike.endsWith("%")) {
		   sqlliteRuleNameLike = sqlliteRuleNameLike + "%";
		}
	    }
	return sqlliteRuleNameLike;
    }

    public List<String> getSqlliteRuleNames(){
	return sqlliteRuleNames;
    }


    public String getDataSetsName(){
        return dataSetsName;
    }

    public String getDataSetsNameLike(){
	    if (dataSetsNameLike != null && dataSetsNameLike.trim().length() > 0) {
		if (!dataSetsNameLike.startsWith("%")) {
		    dataSetsNameLike = "%" + dataSetsNameLike;
		}
		if (!dataSetsNameLike.endsWith("%")) {
		   dataSetsNameLike = dataSetsNameLike + "%";
		}
	    }
	return dataSetsNameLike;
    }

    public List<String> getDataSetsNames(){
	return dataSetsNames;
    }


    public String getDelflag(){
        return delflag;
    }

    public String getDelflagLike(){
	    if (delflagLike != null && delflagLike.trim().length() > 0) {
		if (!delflagLike.startsWith("%")) {
		    delflagLike = "%" + delflagLike;
		}
		if (!delflagLike.endsWith("%")) {
		   delflagLike = delflagLike + "%";
		}
	    }
	return delflagLike;
    }

    public List<String> getDelflags(){
	return delflags;
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


    public String getCreateBy(){
        return createBy;
    }

    public String getCreateByLike(){
	    if (createByLike != null && createByLike.trim().length() > 0) {
		if (!createByLike.startsWith("%")) {
		    createByLike = "%" + createByLike;
		}
		if (!createByLike.endsWith("%")) {
		   createByLike = createByLike + "%";
		}
	    }
	return createByLike;
    }

    public List<String> getCreateBys(){
	return createBys;
    }


    public Date getCreateDateGreaterThanOrEqual(){
        return createDateGreaterThanOrEqual;
    }

    public Date getCreateDateLessThanOrEqual(){
	return createDateLessThanOrEqual;
    }


    public String getUpdateBy(){
        return updateBy;
    }

    public String getUpdateByLike(){
	    if (updateByLike != null && updateByLike.trim().length() > 0) {
		if (!updateByLike.startsWith("%")) {
		    updateByLike = "%" + updateByLike;
		}
		if (!updateByLike.endsWith("%")) {
		   updateByLike = updateByLike + "%";
		}
	    }
	return updateByLike;
    }

    public List<String> getUpdateBys(){
	return updateBys;
    }


    public Date getUpdateDateGreaterThanOrEqual(){
        return updateDateGreaterThanOrEqual;
    }

    public Date getUpdateDateLessThanOrEqual(){
	return updateDateLessThanOrEqual;
    }


 

    public void setSqlliteRuleCode(String sqlliteRuleCode){
        this.sqlliteRuleCode = sqlliteRuleCode;
    }

    public void setSqlliteRuleCodeLike( String sqlliteRuleCodeLike){
	this.sqlliteRuleCodeLike = sqlliteRuleCodeLike;
    }

    public void setSqlliteRuleCodes(List<String> sqlliteRuleCodes){
        this.sqlliteRuleCodes = sqlliteRuleCodes;
    }


    public void setSqlliteRuleDesc(String sqlliteRuleDesc){
        this.sqlliteRuleDesc = sqlliteRuleDesc;
    }

    public void setSqlliteRuleDescLike( String sqlliteRuleDescLike){
	this.sqlliteRuleDescLike = sqlliteRuleDescLike;
    }

    public void setSqlliteRuleDescs(List<String> sqlliteRuleDescs){
        this.sqlliteRuleDescs = sqlliteRuleDescs;
    }


    public void setSqlliteRuleName(String sqlliteRuleName){
        this.sqlliteRuleName = sqlliteRuleName;
    }

    public void setSqlliteRuleNameLike( String sqlliteRuleNameLike){
	this.sqlliteRuleNameLike = sqlliteRuleNameLike;
    }

    public void setSqlliteRuleNames(List<String> sqlliteRuleNames){
        this.sqlliteRuleNames = sqlliteRuleNames;
    }


    public void setDataSetsName(String dataSetsName){
        this.dataSetsName = dataSetsName;
    }

    public void setDataSetsNameLike( String dataSetsNameLike){
	this.dataSetsNameLike = dataSetsNameLike;
    }

    public void setDataSetsNames(List<String> dataSetsNames){
        this.dataSetsNames = dataSetsNames;
    }


    public void setDelflag(String delflag){
        this.delflag = delflag;
    }

    public void setDelflagLike( String delflagLike){
	this.delflagLike = delflagLike;
    }

    public void setDelflags(List<String> delflags){
        this.delflags = delflags;
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


    public void setCreateBy(String createBy){
        this.createBy = createBy;
    }

    public void setCreateByLike( String createByLike){
	this.createByLike = createByLike;
    }

    public void setCreateBys(List<String> createBys){
        this.createBys = createBys;
    }


    public void setCreateDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
        this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
    }

    public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual){
	this.createDateLessThanOrEqual = createDateLessThanOrEqual;
    }


    public void setUpdateBy(String updateBy){
        this.updateBy = updateBy;
    }

    public void setUpdateByLike( String updateByLike){
	this.updateByLike = updateByLike;
    }

    public void setUpdateBys(List<String> updateBys){
        this.updateBys = updateBys;
    }


    public void setUpdateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual){
        this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
    }

    public void setUpdateDateLessThanOrEqual(Date updateDateLessThanOrEqual){
	this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
    }




    public WdatasetSqlliteQuery sqlliteRuleCode(String sqlliteRuleCode){
	if (sqlliteRuleCode == null) {
	    throw new RuntimeException("sqlliteRuleCode is null");
        }         
	this.sqlliteRuleCode = sqlliteRuleCode;
	return this;
    }

    public WdatasetSqlliteQuery sqlliteRuleCodeLike( String sqlliteRuleCodeLike){
        if (sqlliteRuleCodeLike == null) {
            throw new RuntimeException("sqlliteRuleCode is null");
        }
        this.sqlliteRuleCodeLike = sqlliteRuleCodeLike;
        return this;
    }

    public WdatasetSqlliteQuery sqlliteRuleCodes(List<String> sqlliteRuleCodes){
        if (sqlliteRuleCodes == null) {
            throw new RuntimeException("sqlliteRuleCodes is empty ");
        }
        this.sqlliteRuleCodes = sqlliteRuleCodes;
        return this;
    }


    public WdatasetSqlliteQuery sqlliteRuleDesc(String sqlliteRuleDesc){
	if (sqlliteRuleDesc == null) {
	    throw new RuntimeException("sqlliteRuleDesc is null");
        }         
	this.sqlliteRuleDesc = sqlliteRuleDesc;
	return this;
    }

    public WdatasetSqlliteQuery sqlliteRuleDescLike( String sqlliteRuleDescLike){
        if (sqlliteRuleDescLike == null) {
            throw new RuntimeException("sqlliteRuleDesc is null");
        }
        this.sqlliteRuleDescLike = sqlliteRuleDescLike;
        return this;
    }

    public WdatasetSqlliteQuery sqlliteRuleDescs(List<String> sqlliteRuleDescs){
        if (sqlliteRuleDescs == null) {
            throw new RuntimeException("sqlliteRuleDescs is empty ");
        }
        this.sqlliteRuleDescs = sqlliteRuleDescs;
        return this;
    }


    public WdatasetSqlliteQuery sqlliteRuleName(String sqlliteRuleName){
	if (sqlliteRuleName == null) {
	    throw new RuntimeException("sqlliteRuleName is null");
        }         
	this.sqlliteRuleName = sqlliteRuleName;
	return this;
    }

    public WdatasetSqlliteQuery sqlliteRuleNameLike( String sqlliteRuleNameLike){
        if (sqlliteRuleNameLike == null) {
            throw new RuntimeException("sqlliteRuleName is null");
        }
        this.sqlliteRuleNameLike = sqlliteRuleNameLike;
        return this;
    }

    public WdatasetSqlliteQuery sqlliteRuleNames(List<String> sqlliteRuleNames){
        if (sqlliteRuleNames == null) {
            throw new RuntimeException("sqlliteRuleNames is empty ");
        }
        this.sqlliteRuleNames = sqlliteRuleNames;
        return this;
    }


    public WdatasetSqlliteQuery dataSetsName(String dataSetsName){
	if (dataSetsName == null) {
	    throw new RuntimeException("dataSetsName is null");
        }         
	this.dataSetsName = dataSetsName;
	return this;
    }

    public WdatasetSqlliteQuery dataSetsNameLike( String dataSetsNameLike){
        if (dataSetsNameLike == null) {
            throw new RuntimeException("dataSetsName is null");
        }
        this.dataSetsNameLike = dataSetsNameLike;
        return this;
    }

    public WdatasetSqlliteQuery dataSetsNames(List<String> dataSetsNames){
        if (dataSetsNames == null) {
            throw new RuntimeException("dataSetsNames is empty ");
        }
        this.dataSetsNames = dataSetsNames;
        return this;
    }


    public WdatasetSqlliteQuery delflag(String delflag){
	if (delflag == null) {
	    throw new RuntimeException("delflag is null");
        }         
	this.delflag = delflag;
	return this;
    }

    public WdatasetSqlliteQuery delflagLike( String delflagLike){
        if (delflagLike == null) {
            throw new RuntimeException("delflag is null");
        }
        this.delflagLike = delflagLike;
        return this;
    }

    public WdatasetSqlliteQuery delflags(List<String> delflags){
        if (delflags == null) {
            throw new RuntimeException("delflags is empty ");
        }
        this.delflags = delflags;
        return this;
    }


    public WdatasetSqlliteQuery ruleJson(String ruleJson){
	if (ruleJson == null) {
	    throw new RuntimeException("ruleJson is null");
        }         
	this.ruleJson = ruleJson;
	return this;
    }

    public WdatasetSqlliteQuery ruleJsonLike( String ruleJsonLike){
        if (ruleJsonLike == null) {
            throw new RuntimeException("ruleJson is null");
        }
        this.ruleJsonLike = ruleJsonLike;
        return this;
    }

    public WdatasetSqlliteQuery ruleJsons(List<String> ruleJsons){
        if (ruleJsons == null) {
            throw new RuntimeException("ruleJsons is empty ");
        }
        this.ruleJsons = ruleJsons;
        return this;
    }


    public WdatasetSqlliteQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public WdatasetSqlliteQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public WdatasetSqlliteQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public WdatasetSqlliteQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
	if (createDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDate is null");
        }         
	this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
        return this;
    }

    public WdatasetSqlliteQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual){
        if (createDateLessThanOrEqual == null) {
            throw new RuntimeException("createDate is null");
        }
        this.createDateLessThanOrEqual = createDateLessThanOrEqual;
        return this;
    }



    public WdatasetSqlliteQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public WdatasetSqlliteQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public WdatasetSqlliteQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public WdatasetSqlliteQuery updateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual){
	if (updateDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateDate is null");
        }         
	this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
        return this;
    }

    public WdatasetSqlliteQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual){
        if (updateDateLessThanOrEqual == null) {
            throw new RuntimeException("updateDate is null");
        }
        this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
        return this;
    }




    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("sqlliteRuleCode".equals(sortColumn)) {
                orderBy = "E.SQLLITE_RULE_CODE_" + a_x;
            } 

            if ("sqlliteRuleDesc".equals(sortColumn)) {
                orderBy = "E.SQLLITE_RULE_DESC_" + a_x;
            } 

            if ("sqlliteRuleName".equals(sortColumn)) {
                orderBy = "E.SQLLITE_RULE_NAME_" + a_x;
            } 

            if ("dataSetsName".equals(sortColumn)) {
                orderBy = "E.DATASETS_NAME_" + a_x;
            } 

            if ("delflag".equals(sortColumn)) {
                orderBy = "E.DELFLAG_" + a_x;
            } 

            if ("ruleJson".equals(sortColumn)) {
                orderBy = "E.RULEJSON_" + a_x;
            } 

            if ("createBy".equals(sortColumn)) {
                orderBy = "E.CREATEBY" + a_x;
            } 

            if ("createDate".equals(sortColumn)) {
                orderBy = "E.CREATEDATE" + a_x;
            } 

            if ("updateBy".equals(sortColumn)) {
                orderBy = "E.UPDATEBY" + a_x;
            } 

            if ("updateDate".equals(sortColumn)) {
                orderBy = "E.UPDATEDATE" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("sqlliteRuleCode", "SQLLITE_RULE_CODE_");
        addColumn("sqlliteRuleDesc", "SQLLITE_RULE_DESC_");
        addColumn("sqlliteRuleName", "SQLLITE_RULE_NAME_");
        addColumn("dataSetsName", "DATASETS_NAME_");
        addColumn("delflag", "DELFLAG_");
        addColumn("ruleJson", "RULEJSON_");
        addColumn("createBy", "CREATEBY");
        addColumn("createDate", "CREATEDATE");
        addColumn("updateBy", "UPDATEBY");
        addColumn("updateDate", "UPDATEDATE");
    }

}