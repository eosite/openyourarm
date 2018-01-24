package com.glaf.etl.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ETLDataSrcQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected Long databaseId;
  	protected Long databaseIdGreaterThanOrEqual;
  	protected Long databaseIdLessThanOrEqual;
  	protected List<Long> databaseIds;
  	protected String databaseName;
  	protected String databaseNameLike;
  	protected List<String> databaseNames;
  	protected String sourceName;
  	protected String sourceNameLike;
  	protected List<String> sourceNames;
  	protected String tableName;
  	protected String tableNameLike;
  	protected List<String> tableNames;
  	protected String createBy;
  	protected String createByLike;
  	protected List<String> createBys;
        protected Date createTimeGreaterThanOrEqual;
  	protected Date createTimeLessThanOrEqual;
  	protected String updateBy;
  	protected String updateByLike;
  	protected List<String> updateBys;
        protected Date updateTimeGreaterThanOrEqual;
  	protected Date updateTimeLessThanOrEqual;
  	protected String keywordsLike;
  	
    public ETLDataSrcQuery() {

    }

    public String getKeywordsLike() {
		if (keywordsLike != null && keywordsLike.trim().length() > 0) {
			if (!keywordsLike.startsWith("%")) {
				keywordsLike = "%" + keywordsLike;
			}
			if (!keywordsLike.endsWith("%")) {
				keywordsLike = keywordsLike + "%";
			}
		}
		return keywordsLike;
	}
    
    public void setKeywordsLike(String keywordsLike) {
		this.keywordsLike = keywordsLike;
	}
    
    
    
    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public Long getDatabaseId(){
        return databaseId;
    }

    public Long getDatabaseIdGreaterThanOrEqual(){
        return databaseIdGreaterThanOrEqual;
    }

    public Long getDatabaseIdLessThanOrEqual(){
	return databaseIdLessThanOrEqual;
    }

    public List<Long> getDatabaseIds(){
	return databaseIds;
    }

    public String getDatabaseName(){
        return databaseName;
    }

    public String getDatabaseNameLike(){
	    if (databaseNameLike != null && databaseNameLike.trim().length() > 0) {
		if (!databaseNameLike.startsWith("%")) {
		    databaseNameLike = "%" + databaseNameLike;
		}
		if (!databaseNameLike.endsWith("%")) {
		   databaseNameLike = databaseNameLike + "%";
		}
	    }
	return databaseNameLike;
    }

    public List<String> getDatabaseNames(){
	return databaseNames;
    }


    public String getSourceName(){
        return sourceName;
    }

    public String getSourceNameLike(){
	    if (sourceNameLike != null && sourceNameLike.trim().length() > 0) {
		if (!sourceNameLike.startsWith("%")) {
		    sourceNameLike = "%" + sourceNameLike;
		}
		if (!sourceNameLike.endsWith("%")) {
		   sourceNameLike = sourceNameLike + "%";
		}
	    }
	return sourceNameLike;
    }

    public List<String> getSourceNames(){
	return sourceNames;
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


    public Date getCreateTimeGreaterThanOrEqual(){
        return createTimeGreaterThanOrEqual;
    }

    public Date getCreateTimeLessThanOrEqual(){
	return createTimeLessThanOrEqual;
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


    public Date getUpdateTimeGreaterThanOrEqual(){
        return updateTimeGreaterThanOrEqual;
    }

    public Date getUpdateTimeLessThanOrEqual(){
	return updateTimeLessThanOrEqual;
    }


 

    public void setDatabaseId(Long databaseId){
        this.databaseId = databaseId;
    }

    public void setDatabaseIdGreaterThanOrEqual(Long databaseIdGreaterThanOrEqual){
        this.databaseIdGreaterThanOrEqual = databaseIdGreaterThanOrEqual;
    }

    public void setDatabaseIdLessThanOrEqual(Long databaseIdLessThanOrEqual){
	this.databaseIdLessThanOrEqual = databaseIdLessThanOrEqual;
    }

    public void setDatabaseIds(List<Long> databaseIds){
        this.databaseIds = databaseIds;
    }


    public void setDatabaseName(String databaseName){
        this.databaseName = databaseName;
    }

    public void setDatabaseNameLike( String databaseNameLike){
	this.databaseNameLike = databaseNameLike;
    }

    public void setDatabaseNames(List<String> databaseNames){
        this.databaseNames = databaseNames;
    }


    public void setSourceName(String sourceName){
        this.sourceName = sourceName;
    }

    public void setSourceNameLike( String sourceNameLike){
	this.sourceNameLike = sourceNameLike;
    }

    public void setSourceNames(List<String> sourceNames){
        this.sourceNames = sourceNames;
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


    public void setCreateBy(String createBy){
        this.createBy = createBy;
    }

    public void setCreateByLike( String createByLike){
	this.createByLike = createByLike;
    }

    public void setCreateBys(List<String> createBys){
        this.createBys = createBys;
    }


    public void setCreateTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
        this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
    }

    public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
	this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
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


    public void setUpdateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
        this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
    }

    public void setUpdateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
	this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
    }




    public ETLDataSrcQuery databaseId(Long databaseId){
	if (databaseId == null) {
            throw new RuntimeException("databaseId is null");
        }         
	this.databaseId = databaseId;
	return this;
    }

    public ETLDataSrcQuery databaseIdGreaterThanOrEqual(Long databaseIdGreaterThanOrEqual){
	if (databaseIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("databaseId is null");
        }         
	this.databaseIdGreaterThanOrEqual = databaseIdGreaterThanOrEqual;
        return this;
    }

    public ETLDataSrcQuery databaseIdLessThanOrEqual(Long databaseIdLessThanOrEqual){
        if (databaseIdLessThanOrEqual == null) {
            throw new RuntimeException("databaseId is null");
        }
        this.databaseIdLessThanOrEqual = databaseIdLessThanOrEqual;
        return this;
    }

    public ETLDataSrcQuery databaseIds(List<Long> databaseIds){
        if (databaseIds == null) {
            throw new RuntimeException("databaseIds is empty ");
        }
        this.databaseIds = databaseIds;
        return this;
    }


    public ETLDataSrcQuery databaseName(String databaseName){
	if (databaseName == null) {
	    throw new RuntimeException("databaseName is null");
        }         
	this.databaseName = databaseName;
	return this;
    }

    public ETLDataSrcQuery databaseNameLike( String databaseNameLike){
        if (databaseNameLike == null) {
            throw new RuntimeException("databaseName is null");
        }
        this.databaseNameLike = databaseNameLike;
        return this;
    }

    public ETLDataSrcQuery databaseNames(List<String> databaseNames){
        if (databaseNames == null) {
            throw new RuntimeException("databaseNames is empty ");
        }
        this.databaseNames = databaseNames;
        return this;
    }


    public ETLDataSrcQuery sourceName(String sourceName){
	if (sourceName == null) {
	    throw new RuntimeException("sourceName is null");
        }         
	this.sourceName = sourceName;
	return this;
    }

    public ETLDataSrcQuery sourceNameLike( String sourceNameLike){
        if (sourceNameLike == null) {
            throw new RuntimeException("sourceName is null");
        }
        this.sourceNameLike = sourceNameLike;
        return this;
    }

    public ETLDataSrcQuery sourceNames(List<String> sourceNames){
        if (sourceNames == null) {
            throw new RuntimeException("sourceNames is empty ");
        }
        this.sourceNames = sourceNames;
        return this;
    }


    public ETLDataSrcQuery tableName(String tableName){
	if (tableName == null) {
	    throw new RuntimeException("tableName is null");
        }         
	this.tableName = tableName;
	return this;
    }

    public ETLDataSrcQuery tableNameLike( String tableNameLike){
        if (tableNameLike == null) {
            throw new RuntimeException("tableName is null");
        }
        this.tableNameLike = tableNameLike;
        return this;
    }

    public ETLDataSrcQuery tableNames(List<String> tableNames){
        if (tableNames == null) {
            throw new RuntimeException("tableNames is empty ");
        }
        this.tableNames = tableNames;
        return this;
    }


    public ETLDataSrcQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public ETLDataSrcQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public ETLDataSrcQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public ETLDataSrcQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
	if (createTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime is null");
        }         
	this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
        return this;
    }

    public ETLDataSrcQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
        if (createTimeLessThanOrEqual == null) {
            throw new RuntimeException("createTime is null");
        }
        this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
        return this;
    }



    public ETLDataSrcQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public ETLDataSrcQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public ETLDataSrcQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public ETLDataSrcQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
	if (updateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime is null");
        }         
	this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
        return this;
    }

    public ETLDataSrcQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
        if (updateTimeLessThanOrEqual == null) {
            throw new RuntimeException("updateTime is null");
        }
        this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
        return this;
    }




    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("databaseId".equals(sortColumn)) {
                orderBy = "E.DATABASEID_" + a_x;
            } 

            if ("databaseName".equals(sortColumn)) {
                orderBy = "E.DATABASENAME_" + a_x;
            } 

            if ("sourceName".equals(sortColumn)) {
                orderBy = "E.SOURCENAME_" + a_x;
            } 

            if ("tableName".equals(sortColumn)) {
                orderBy = "E.TABLENAME_" + a_x;
            } 

            if ("createBy".equals(sortColumn)) {
                orderBy = "E.CREATEBY_" + a_x;
            } 

            if ("createTime".equals(sortColumn)) {
                orderBy = "E.CREATETIME_" + a_x;
            } 

            if ("updateBy".equals(sortColumn)) {
                orderBy = "E.UPDATEBY_" + a_x;
            } 

            if ("updateTime".equals(sortColumn)) {
                orderBy = "E.UPDATETIME_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("databaseId", "DATABASEID_");
        addColumn("databaseName", "DATABASENAME_");
        addColumn("sourceName", "SOURCENAME_");
        addColumn("tableName", "TABLENAME_");
        addColumn("createBy", "CREATEBY_");
        addColumn("createTime", "CREATETIME_");
        addColumn("updateBy", "UPDATEBY_");
        addColumn("updateTime", "UPDATETIME_");
    }

}