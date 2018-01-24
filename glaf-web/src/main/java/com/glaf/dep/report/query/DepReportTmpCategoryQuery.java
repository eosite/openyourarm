package com.glaf.dep.report.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DepReportTmpCategoryQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
  	protected Long depId;
  	protected Long depIdGreaterThanOrEqual;
  	protected Long depIdLessThanOrEqual;
  	protected List<Long> depIds;
  	protected Long tmpId;
  	protected Long tmpIdGreaterThanOrEqual;
  	protected Long tmpIdLessThanOrEqual;
  	protected List<Long> tmpIds;

    public DepReportTmpCategoryQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public Long getDepId(){
        return depId;
    }

    public Long getDepIdGreaterThanOrEqual(){
        return depIdGreaterThanOrEqual;
    }

    public Long getDepIdLessThanOrEqual(){
	return depIdLessThanOrEqual;
    }

    public List<Long> getDepIds(){
	return depIds;
    }

    public Long getTmpId(){
        return tmpId;
    }

    public Long getTmpIdGreaterThanOrEqual(){
        return tmpIdGreaterThanOrEqual;
    }

    public Long getTmpIdLessThanOrEqual(){
	return tmpIdLessThanOrEqual;
    }

    public List<Long> getTmpIds(){
	return tmpIds;
    }

 

    public void setDepId(Long depId){
        this.depId = depId;
    }

    public void setDepIdGreaterThanOrEqual(Long depIdGreaterThanOrEqual){
        this.depIdGreaterThanOrEqual = depIdGreaterThanOrEqual;
    }

    public void setDepIdLessThanOrEqual(Long depIdLessThanOrEqual){
	this.depIdLessThanOrEqual = depIdLessThanOrEqual;
    }

    public void setDepIds(List<Long> depIds){
        this.depIds = depIds;
    }


    public void setTmpId(Long tmpId){
        this.tmpId = tmpId;
    }

    public void setTmpIdGreaterThanOrEqual(Long tmpIdGreaterThanOrEqual){
        this.tmpIdGreaterThanOrEqual = tmpIdGreaterThanOrEqual;
    }

    public void setTmpIdLessThanOrEqual(Long tmpIdLessThanOrEqual){
	this.tmpIdLessThanOrEqual = tmpIdLessThanOrEqual;
    }

    public void setTmpIds(List<Long> tmpIds){
        this.tmpIds = tmpIds;
    }




    public DepReportTmpCategoryQuery depId(Long depId){
	if (depId == null) {
            throw new RuntimeException("depId is null");
        }         
	this.depId = depId;
	return this;
    }

    public DepReportTmpCategoryQuery depIdGreaterThanOrEqual(Long depIdGreaterThanOrEqual){
	if (depIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("depId is null");
        }         
	this.depIdGreaterThanOrEqual = depIdGreaterThanOrEqual;
        return this;
    }

    public DepReportTmpCategoryQuery depIdLessThanOrEqual(Long depIdLessThanOrEqual){
        if (depIdLessThanOrEqual == null) {
            throw new RuntimeException("depId is null");
        }
        this.depIdLessThanOrEqual = depIdLessThanOrEqual;
        return this;
    }

    public DepReportTmpCategoryQuery depIds(List<Long> depIds){
        if (depIds == null) {
            throw new RuntimeException("depIds is empty ");
        }
        this.depIds = depIds;
        return this;
    }


    public DepReportTmpCategoryQuery tmpId(Long tmpId){
	if (tmpId == null) {
            throw new RuntimeException("tmpId is null");
        }         
	this.tmpId = tmpId;
	return this;
    }

    public DepReportTmpCategoryQuery tmpIdGreaterThanOrEqual(Long tmpIdGreaterThanOrEqual){
	if (tmpIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("tmpId is null");
        }         
	this.tmpIdGreaterThanOrEqual = tmpIdGreaterThanOrEqual;
        return this;
    }

    public DepReportTmpCategoryQuery tmpIdLessThanOrEqual(Long tmpIdLessThanOrEqual){
        if (tmpIdLessThanOrEqual == null) {
            throw new RuntimeException("tmpId is null");
        }
        this.tmpIdLessThanOrEqual = tmpIdLessThanOrEqual;
        return this;
    }

    public DepReportTmpCategoryQuery tmpIds(List<Long> tmpIds){
        if (tmpIds == null) {
            throw new RuntimeException("tmpIds is empty ");
        }
        this.tmpIds = tmpIds;
        return this;
    }



    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("depId".equals(sortColumn)) {
                orderBy = "E.DEP_ID_" + a_x;
            } 

            if ("tmpId".equals(sortColumn)) {
                orderBy = "E.TMP_ID_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("depId", "DEP_ID_");
        addColumn("tmpId", "TMP_ID_");
    }

}