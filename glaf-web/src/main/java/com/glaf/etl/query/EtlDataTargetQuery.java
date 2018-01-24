package com.glaf.etl.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class EtlDataTargetQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> targetId_s;
	protected Collection<String> appActorIds;
  	protected String targetName_;
  	protected String targetName_Like;
  	protected List<String> targetName_s;
  	protected Long databaseId_;
  	protected Long databaseId_GreaterThanOrEqual;
  	protected Long databaseId_LessThanOrEqual;

  	protected List<Long> databaseId_s;
  	protected String databaseName_;
  	protected String databaseName_Like;
  	protected List<String> databaseName_s;
  	protected String tableName_;
  	protected String tableName_Like;
  	protected List<String> tableName_s;
  	protected String createBy_;
  	protected String createBy_Like;
  	protected List<String> createBy_s;
        protected Date createTime_GreaterThanOrEqual;
  	protected Date createTime_LessThanOrEqual;
  	protected String updateBy_;
  	protected String updateBy_Like;
  	protected List<String> updateBy_s;
        protected Date updateTime_GreaterThanOrEqual;
  	protected Date updateTime_LessThanOrEqual;

    public EtlDataTargetQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getTargetName_(){
        return targetName_;
    }

    public String getTargetName_Like(){
	    if (targetName_Like != null && targetName_Like.trim().length() > 0) {
		if (!targetName_Like.startsWith("%")) {
		    targetName_Like = "%" + targetName_Like;
		}
		if (!targetName_Like.endsWith("%")) {
		   targetName_Like = targetName_Like + "%";
		}
	    }
	return targetName_Like;
    }

    public List<String> getTargetName_s(){
	return targetName_s;
    }


    public Long getDatabaseId_(){
        return databaseId_;
    }

    public Long getDatabaseId_GreaterThanOrEqual(){
        return databaseId_GreaterThanOrEqual;
    }

    public Long getDatabaseId_LessThanOrEqual(){








	return databaseId_LessThanOrEqual;
    }

    public List<Long> getDatabaseId_s(){
	return databaseId_s;
    }


    public String getDatabaseName_(){
        return databaseName_;
    }

    public String getDatabaseName_Like(){
	    if (databaseName_Like != null && databaseName_Like.trim().length() > 0) {
		if (!databaseName_Like.startsWith("%")) {
		    databaseName_Like = "%" + databaseName_Like;
		}
		if (!databaseName_Like.endsWith("%")) {
		   databaseName_Like = databaseName_Like + "%";
		}
	    }
	return databaseName_Like;
    }

    public List<String> getDatabaseName_s(){
	return databaseName_s;
    }


    public String getTableName_(){
        return tableName_;
    }

    public String getTableName_Like(){
	    if (tableName_Like != null && tableName_Like.trim().length() > 0) {
		if (!tableName_Like.startsWith("%")) {
		    tableName_Like = "%" + tableName_Like;
		}
		if (!tableName_Like.endsWith("%")) {
		   tableName_Like = tableName_Like + "%";
		}
	    }
	return tableName_Like;
    }

    public List<String> getTableName_s(){
	return tableName_s;
    }


    public String getCreateBy_(){
        return createBy_;
    }

    public String getCreateBy_Like(){
	    if (createBy_Like != null && createBy_Like.trim().length() > 0) {
		if (!createBy_Like.startsWith("%")) {
		    createBy_Like = "%" + createBy_Like;
		}
		if (!createBy_Like.endsWith("%")) {
		   createBy_Like = createBy_Like + "%";
		}
	    }
	return createBy_Like;
    }

    public List<String> getCreateBy_s(){
	return createBy_s;
    }


    public Date getCreateTime_GreaterThanOrEqual(){
        return createTime_GreaterThanOrEqual;
    }

    public Date getCreateTime_LessThanOrEqual(){
	return createTime_LessThanOrEqual;
    }


    public String getUpdateBy_(){
        return updateBy_;
    }

    public String getUpdateBy_Like(){
	    if (updateBy_Like != null && updateBy_Like.trim().length() > 0) {
		if (!updateBy_Like.startsWith("%")) {
		    updateBy_Like = "%" + updateBy_Like;
		}
		if (!updateBy_Like.endsWith("%")) {
		   updateBy_Like = updateBy_Like + "%";
		}
	    }
	return updateBy_Like;
    }

    public List<String> getUpdateBy_s(){
	return updateBy_s;
    }


    public Date getUpdateTime_GreaterThanOrEqual(){
        return updateTime_GreaterThanOrEqual;
    }

    public Date getUpdateTime_LessThanOrEqual(){
	return updateTime_LessThanOrEqual;
    }


 

    public void setTargetName_(String targetName_){
        this.targetName_ = targetName_;
    }

    public void setTargetName_Like( String targetName_Like){
	this.targetName_Like = targetName_Like;
    }

    public void setTargetName_s(List<String> targetName_s){
        this.targetName_s = targetName_s;
    }


    public void setDatabaseId_(Long databaseId_){
        this.databaseId_ = databaseId_;
    }

    public void setDatabaseId_GreaterThanOrEqual(Long databaseId_GreaterThanOrEqual){
        this.databaseId_GreaterThanOrEqual = databaseId_GreaterThanOrEqual;
    }

    public void setDatabaseId_LessThanOrEqual(Long databaseId_LessThanOrEqual){
	this.databaseId_LessThanOrEqual = databaseId_LessThanOrEqual;
    }

    public void setDatabaseId_s(List<Long> databaseId_s){
        this.databaseId_s = databaseId_s;
    }


    public void setDatabaseName_(String databaseName_){
        this.databaseName_ = databaseName_;
    }

    public void setDatabaseName_Like( String databaseName_Like){
	this.databaseName_Like = databaseName_Like;
    }

    public void setDatabaseName_s(List<String> databaseName_s){
        this.databaseName_s = databaseName_s;
    }


    public void setTableName_(String tableName_){
        this.tableName_ = tableName_;
    }

    public void setTableName_Like( String tableName_Like){
	this.tableName_Like = tableName_Like;
    }

    public void setTableName_s(List<String> tableName_s){
        this.tableName_s = tableName_s;
    }


    public void setCreateBy_(String createBy_){
        this.createBy_ = createBy_;
    }

    public void setCreateBy_Like( String createBy_Like){
	this.createBy_Like = createBy_Like;
    }

    public void setCreateBy_s(List<String> createBy_s){
        this.createBy_s = createBy_s;
    }


    public void setCreateTime_GreaterThanOrEqual(Date createTime_GreaterThanOrEqual){
        this.createTime_GreaterThanOrEqual = createTime_GreaterThanOrEqual;
    }

    public void setCreateTime_LessThanOrEqual(Date createTime_LessThanOrEqual){
	this.createTime_LessThanOrEqual = createTime_LessThanOrEqual;
    }


    public void setUpdateBy_(String updateBy_){
        this.updateBy_ = updateBy_;
    }

    public void setUpdateBy_Like( String updateBy_Like){
	this.updateBy_Like = updateBy_Like;
    }

    public void setUpdateBy_s(List<String> updateBy_s){
        this.updateBy_s = updateBy_s;
    }


    public void setUpdateTime_GreaterThanOrEqual(Date updateTime_GreaterThanOrEqual){
        this.updateTime_GreaterThanOrEqual = updateTime_GreaterThanOrEqual;
    }

    public void setUpdateTime_LessThanOrEqual(Date updateTime_LessThanOrEqual){
	this.updateTime_LessThanOrEqual = updateTime_LessThanOrEqual;
    }




    public EtlDataTargetQuery targetName_(String targetName_){
	if (targetName_ == null) {
	    throw new RuntimeException("targetName_ is null");
        }         
	this.targetName_ = targetName_;
	return this;
    }

    public EtlDataTargetQuery targetName_Like( String targetName_Like){
        if (targetName_Like == null) {
            throw new RuntimeException("targetName_ is null");
        }
        this.targetName_Like = targetName_Like;
        return this;
    }

    public EtlDataTargetQuery targetName_s(List<String> targetName_s){
        if (targetName_s == null) {
            throw new RuntimeException("targetName_s is empty ");
        }
        this.targetName_s = targetName_s;
        return this;
    }


    public EtlDataTargetQuery databaseId_(Long databaseId_){
	if (databaseId_ == null) {
            throw new RuntimeException("databaseId_ is null");

        }         
	this.databaseId_ = databaseId_;
	return this;
    }

    public EtlDataTargetQuery databaseId_GreaterThanOrEqual(Long databaseId_GreaterThanOrEqual){
	if (databaseId_GreaterThanOrEqual == null) {
	    throw new RuntimeException("databaseId_ is null");
        }         
	this.databaseId_GreaterThanOrEqual = databaseId_GreaterThanOrEqual;
        return this;
    }

    public EtlDataTargetQuery databaseId_LessThanOrEqual(Long databaseId_LessThanOrEqual){
        if (databaseId_LessThanOrEqual == null) {
            throw new RuntimeException("databaseId_ is null");
        }
        this.databaseId_LessThanOrEqual = databaseId_LessThanOrEqual;
        return this;
    }

    public EtlDataTargetQuery databaseId_s(List<Long> databaseId_s){
        if (databaseId_s == null) {
            throw new RuntimeException("databaseId_s is empty ");
        }
        this.databaseId_s = databaseId_s;
        return this;
    }


    public EtlDataTargetQuery databaseName_(String databaseName_){
	if (databaseName_ == null) {
	    throw new RuntimeException("databaseName_ is null");
        }         
	this.databaseName_ = databaseName_;
	return this;
    }

    public EtlDataTargetQuery databaseName_Like( String databaseName_Like){
        if (databaseName_Like == null) {
            throw new RuntimeException("databaseName_ is null");
        }
        this.databaseName_Like = databaseName_Like;
        return this;
    }

    public EtlDataTargetQuery databaseName_s(List<String> databaseName_s){
        if (databaseName_s == null) {
            throw new RuntimeException("databaseName_s is empty ");
        }
        this.databaseName_s = databaseName_s;
        return this;
    }


    public EtlDataTargetQuery tableName_(String tableName_){
	if (tableName_ == null) {
	    throw new RuntimeException("tableName_ is null");
        }         
	this.tableName_ = tableName_;
	return this;
    }

    public EtlDataTargetQuery tableName_Like( String tableName_Like){
        if (tableName_Like == null) {
            throw new RuntimeException("tableName_ is null");
        }
        this.tableName_Like = tableName_Like;
        return this;
    }

    public EtlDataTargetQuery tableName_s(List<String> tableName_s){
        if (tableName_s == null) {
            throw new RuntimeException("tableName_s is empty ");
        }
        this.tableName_s = tableName_s;
        return this;
    }


    public EtlDataTargetQuery createBy_(String createBy_){
	if (createBy_ == null) {
	    throw new RuntimeException("createBy_ is null");
        }         
	this.createBy_ = createBy_;
	return this;
    }

    public EtlDataTargetQuery createBy_Like( String createBy_Like){
        if (createBy_Like == null) {
            throw new RuntimeException("createBy_ is null");
        }
        this.createBy_Like = createBy_Like;
        return this;
    }

    public EtlDataTargetQuery createBy_s(List<String> createBy_s){
        if (createBy_s == null) {
            throw new RuntimeException("createBy_s is empty ");
        }
        this.createBy_s = createBy_s;
        return this;
    }



    public EtlDataTargetQuery createTime_GreaterThanOrEqual(Date createTime_GreaterThanOrEqual){
	if (createTime_GreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime_ is null");
        }         
	this.createTime_GreaterThanOrEqual = createTime_GreaterThanOrEqual;
        return this;
    }

    public EtlDataTargetQuery createTime_LessThanOrEqual(Date createTime_LessThanOrEqual){
        if (createTime_LessThanOrEqual == null) {
            throw new RuntimeException("createTime_ is null");
        }
        this.createTime_LessThanOrEqual = createTime_LessThanOrEqual;
        return this;
    }



    public EtlDataTargetQuery updateBy_(String updateBy_){
	if (updateBy_ == null) {
	    throw new RuntimeException("updateBy_ is null");
        }         
	this.updateBy_ = updateBy_;
	return this;
    }

    public EtlDataTargetQuery updateBy_Like( String updateBy_Like){
        if (updateBy_Like == null) {
            throw new RuntimeException("updateBy_ is null");
        }
        this.updateBy_Like = updateBy_Like;
        return this;
    }

    public EtlDataTargetQuery updateBy_s(List<String> updateBy_s){
        if (updateBy_s == null) {
            throw new RuntimeException("updateBy_s is empty ");
        }
        this.updateBy_s = updateBy_s;
        return this;
    }



    public EtlDataTargetQuery updateTime_GreaterThanOrEqual(Date updateTime_GreaterThanOrEqual){
	if (updateTime_GreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime_ is null");
        }         
	this.updateTime_GreaterThanOrEqual = updateTime_GreaterThanOrEqual;
        return this;
    }

    public EtlDataTargetQuery updateTime_LessThanOrEqual(Date updateTime_LessThanOrEqual){
        if (updateTime_LessThanOrEqual == null) {
            throw new RuntimeException("updateTime_ is null");
        }
        this.updateTime_LessThanOrEqual = updateTime_LessThanOrEqual;
        return this;
    }




    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("targetName_".equals(sortColumn)) {
                orderBy = "E.TARGET_NAME_" + a_x;
            } 

            if ("databaseId_".equals(sortColumn)) {
                orderBy = "E.DATABASEID_" + a_x;
            } 

            if ("databaseName_".equals(sortColumn)) {
                orderBy = "E.DATABASENAME_" + a_x;
            } 

            if ("tableName_".equals(sortColumn)) {
                orderBy = "E.TABLENAME_" + a_x;
            } 

            if ("createBy_".equals(sortColumn)) {
                orderBy = "E.CREATEBY_" + a_x;
            } 

            if ("createTime_".equals(sortColumn)) {
                orderBy = "E.CREATETIME_" + a_x;
            } 

            if ("updateBy_".equals(sortColumn)) {
                orderBy = "E.UPDATEBY_" + a_x;
            } 

            if ("updateTime_".equals(sortColumn)) {
                orderBy = "E.UPDATETIME_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("targetId_", "TARGET_ID_");
        addColumn("targetName_", "TARGET_NAME_");
        addColumn("databaseId_", "DATABASEID_");
        addColumn("databaseName_", "DATABASENAME_");
        addColumn("tableName_", "TABLENAME_");
        addColumn("createBy_", "CREATEBY_");
        addColumn("createTime_", "CREATETIME_");
        addColumn("updateBy_", "UPDATEBY_");
        addColumn("updateTime_", "UPDATETIME_");
    }

}