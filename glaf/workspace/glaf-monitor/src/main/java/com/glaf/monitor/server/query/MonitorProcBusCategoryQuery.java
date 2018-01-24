package com.glaf.monitor.server.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class MonitorProcBusCategoryQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected String id;
	protected Collection<String> appActorIds;
  	protected Integer procCategoryId;
  	protected Integer procCategoryIdGreaterThanOrEqual;
  	protected Integer procCategoryIdLessThanOrEqual;
  	protected List<Integer> procCategoryIds;
  	protected String createby;
  	protected String createbyLike;
  	protected List<String> createbys;
        protected Date createtimeGreaterThanOrEqual;
  	protected Date createtimeLessThanOrEqual;

    public MonitorProcBusCategoryQuery() {

    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public Integer getProcCategoryId(){
        return procCategoryId;
    }

    public Integer getProcCategoryIdGreaterThanOrEqual(){
        return procCategoryIdGreaterThanOrEqual;
    }

    public Integer getProcCategoryIdLessThanOrEqual(){
	return procCategoryIdLessThanOrEqual;
    }

    public List<Integer> getProcCategoryIds(){
	return procCategoryIds;
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


 

    public void setProcCategoryId(Integer procCategoryId){
        this.procCategoryId = procCategoryId;
    }

    public void setProcCategoryIdGreaterThanOrEqual(Integer procCategoryIdGreaterThanOrEqual){
        this.procCategoryIdGreaterThanOrEqual = procCategoryIdGreaterThanOrEqual;
    }

    public void setProcCategoryIdLessThanOrEqual(Integer procCategoryIdLessThanOrEqual){
	this.procCategoryIdLessThanOrEqual = procCategoryIdLessThanOrEqual;
    }

    public void setProcCategoryIds(List<Integer> procCategoryIds){
        this.procCategoryIds = procCategoryIds;
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




    public MonitorProcBusCategoryQuery procCategoryId(Integer procCategoryId){
	if (procCategoryId == null) {
            throw new RuntimeException("procCategoryId is null");
        }         
	this.procCategoryId = procCategoryId;
	return this;
    }

    public MonitorProcBusCategoryQuery procCategoryIdGreaterThanOrEqual(Integer procCategoryIdGreaterThanOrEqual){
	if (procCategoryIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("procCategoryId is null");
        }         
	this.procCategoryIdGreaterThanOrEqual = procCategoryIdGreaterThanOrEqual;
        return this;
    }

    public MonitorProcBusCategoryQuery procCategoryIdLessThanOrEqual(Integer procCategoryIdLessThanOrEqual){
        if (procCategoryIdLessThanOrEqual == null) {
            throw new RuntimeException("procCategoryId is null");
        }
        this.procCategoryIdLessThanOrEqual = procCategoryIdLessThanOrEqual;
        return this;
    }

    public MonitorProcBusCategoryQuery procCategoryIds(List<Integer> procCategoryIds){
        if (procCategoryIds == null) {
            throw new RuntimeException("procCategoryIds is empty ");
        }
        this.procCategoryIds = procCategoryIds;
        return this;
    }


    public MonitorProcBusCategoryQuery createby(String createby){
	if (createby == null) {
	    throw new RuntimeException("createby is null");
        }         
	this.createby = createby;
	return this;
    }

    public MonitorProcBusCategoryQuery createbyLike( String createbyLike){
        if (createbyLike == null) {
            throw new RuntimeException("createby is null");
        }
        this.createbyLike = createbyLike;
        return this;
    }

    public MonitorProcBusCategoryQuery createbys(List<String> createbys){
        if (createbys == null) {
            throw new RuntimeException("createbys is empty ");
        }
        this.createbys = createbys;
        return this;
    }



    public MonitorProcBusCategoryQuery createtimeGreaterThanOrEqual(Date createtimeGreaterThanOrEqual){
	if (createtimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createtime is null");
        }         
	this.createtimeGreaterThanOrEqual = createtimeGreaterThanOrEqual;
        return this;
    }

    public MonitorProcBusCategoryQuery createtimeLessThanOrEqual(Date createtimeLessThanOrEqual){
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

            if ("procCategoryId".equals(sortColumn)) {
                orderBy = "E.PROC_CATEGORY_ID_" + a_x;
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
        addColumn("id", "ID_");
        addColumn("procCategoryId", "PROC_CATEGORY_ID_");
        addColumn("createby", "CREATEBY_");
        addColumn("createtime", "CREATETIME_");
    }

}