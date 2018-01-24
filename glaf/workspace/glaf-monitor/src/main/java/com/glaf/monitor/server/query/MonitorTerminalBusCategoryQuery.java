package com.glaf.monitor.server.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class MonitorTerminalBusCategoryQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> terminalIds;
	protected String terminalId;
	protected Collection<String> appActorIds;
  	protected Integer categoryId;
  	protected Integer categoryIdGreaterThanOrEqual;
  	protected Integer categoryIdLessThanOrEqual;
  	protected List<Integer> categoryIds;
  	protected String createby;
  	protected String createbyLike;
  	protected List<String> createbys;
        protected Date createtimeGreaterThanOrEqual;
  	protected Date createtimeLessThanOrEqual;

    public MonitorTerminalBusCategoryQuery() {

    }
    public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public Integer getCategoryId(){
        return categoryId;
    }

    public Integer getCategoryIdGreaterThanOrEqual(){
        return categoryIdGreaterThanOrEqual;
    }

    public Integer getCategoryIdLessThanOrEqual(){
	return categoryIdLessThanOrEqual;
    }

    public List<Integer> getCategoryIds(){
	return categoryIds;
    }

    public String getCreateby(){
        return createby;
    }

    public String getCreatebyLike(){
	    if (createbyLike != null && createbyLike.trim().length() > 0) {
		if (!createbyLike.startsWith("%")) {
		    createbyLike = "%" + createbyLike;
		}
		if (!createbyLike.endsWith("%")) {
		   createbyLike = createbyLike + "%";
		}
	    }
	return createbyLike;
    }

    public List<String> getCreatebys(){
	return createbys;
    }


    public Date getCreatetimeGreaterThanOrEqual(){
        return createtimeGreaterThanOrEqual;
    }

    public Date getCreatetimeLessThanOrEqual(){
	return createtimeLessThanOrEqual;
    }


 

    public void setCategoryId(Integer categoryId){
        this.categoryId = categoryId;
    }

    public void setCategoryIdGreaterThanOrEqual(Integer categoryIdGreaterThanOrEqual){
        this.categoryIdGreaterThanOrEqual = categoryIdGreaterThanOrEqual;
    }

    public void setCategoryIdLessThanOrEqual(Integer categoryIdLessThanOrEqual){
	this.categoryIdLessThanOrEqual = categoryIdLessThanOrEqual;
    }

    public void setCategoryIds(List<Integer> categoryIds){
        this.categoryIds = categoryIds;
    }


    public void setCreateby(String createby){
        this.createby = createby;
    }

    public void setCreatebyLike( String createbyLike){
	this.createbyLike = createbyLike;
    }

    public void setCreatebys(List<String> createbys){
        this.createbys = createbys;
    }


    public void setCreatetimeGreaterThanOrEqual(Date createtimeGreaterThanOrEqual){
        this.createtimeGreaterThanOrEqual = createtimeGreaterThanOrEqual;
    }

    public void setCreatetimeLessThanOrEqual(Date createtimeLessThanOrEqual){
	this.createtimeLessThanOrEqual = createtimeLessThanOrEqual;
    }




    public MonitorTerminalBusCategoryQuery categoryId(Integer categoryId){
	if (categoryId == null) {
            throw new RuntimeException("categoryId is null");
        }         
	this.categoryId = categoryId;
	return this;
    }

    public MonitorTerminalBusCategoryQuery categoryIdGreaterThanOrEqual(Integer categoryIdGreaterThanOrEqual){
	if (categoryIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("categoryId is null");
        }         
	this.categoryIdGreaterThanOrEqual = categoryIdGreaterThanOrEqual;
        return this;
    }

    public MonitorTerminalBusCategoryQuery categoryIdLessThanOrEqual(Integer categoryIdLessThanOrEqual){
        if (categoryIdLessThanOrEqual == null) {
            throw new RuntimeException("categoryId is null");
        }
        this.categoryIdLessThanOrEqual = categoryIdLessThanOrEqual;
        return this;
    }

    public MonitorTerminalBusCategoryQuery categoryIds(List<Integer> categoryIds){
        if (categoryIds == null) {
            throw new RuntimeException("categoryIds is empty ");
        }
        this.categoryIds = categoryIds;
        return this;
    }


    public MonitorTerminalBusCategoryQuery createby(String createby){
	if (createby == null) {
	    throw new RuntimeException("createby is null");
        }         
	this.createby = createby;
	return this;
    }

    public MonitorTerminalBusCategoryQuery createbyLike( String createbyLike){
        if (createbyLike == null) {
            throw new RuntimeException("createby is null");
        }
        this.createbyLike = createbyLike;
        return this;
    }

    public MonitorTerminalBusCategoryQuery createbys(List<String> createbys){
        if (createbys == null) {
            throw new RuntimeException("createbys is empty ");
        }
        this.createbys = createbys;
        return this;
    }



    public MonitorTerminalBusCategoryQuery createtimeGreaterThanOrEqual(Date createtimeGreaterThanOrEqual){
	if (createtimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createtime is null");
        }         
	this.createtimeGreaterThanOrEqual = createtimeGreaterThanOrEqual;
        return this;
    }

    public MonitorTerminalBusCategoryQuery createtimeLessThanOrEqual(Date createtimeLessThanOrEqual){
        if (createtimeLessThanOrEqual == null) {
            throw new RuntimeException("createtime is null");
        }
        this.createtimeLessThanOrEqual = createtimeLessThanOrEqual;
        return this;
    }




    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("categoryId".equals(sortColumn)) {
                orderBy = "E.CATEGORY_ID_" + a_x;
            } 

            if ("createby".equals(sortColumn)) {
                orderBy = "E.CREATEBY_" + a_x;
            } 

            if ("createtime".equals(sortColumn)) {
                orderBy = "E.CREATETIME_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("terminalId", "TERMINAL_ID_");
        addColumn("categoryId", "CATEGORY_ID_");
        addColumn("createby", "CREATEBY_");
        addColumn("createtime", "CREATETIME_");
    }

}