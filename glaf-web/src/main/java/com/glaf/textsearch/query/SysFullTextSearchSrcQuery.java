package com.glaf.textsearch.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SysFullTextSearchSrcQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String serviceName_;
  	protected String serviceName_Like;
  	protected List<String> serviceName_s;
  	protected String serviceAddress_;
  	protected String serviceAddress_Like;
  	protected List<String> serviceAddress_s;
  	protected String fullTextServer_;
  	protected String fullTextServer_Like;
  	protected List<String> fullTextServer_s;
  	protected String indexName_;
  	protected String indexName_Like;
  	protected List<String> indexName_s;
  	protected String typeName_;
  	protected String typeName_Like;
  	protected List<String> typeName_s;
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
  	protected Integer deleteFlag_;
  	protected Integer deleteFlag_GreaterThanOrEqual;
  	protected Integer deleteFlag_LessThanOrEqual;
  	protected List<Integer> deleteFlag_s;

    public SysFullTextSearchSrcQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getServiceName_(){
        return serviceName_;
    }

    public String getServiceName_Like(){
	    if (serviceName_Like != null && serviceName_Like.trim().length() > 0) {
		if (!serviceName_Like.startsWith("%")) {
		    serviceName_Like = "%" + serviceName_Like;
		}
		if (!serviceName_Like.endsWith("%")) {
		   serviceName_Like = serviceName_Like + "%";
		}
	    }
	return serviceName_Like;
    }

    public List<String> getServiceName_s(){
	return serviceName_s;
    }


    public String getServiceAddress_(){
        return serviceAddress_;
    }

    public String getServiceAddress_Like(){
	    if (serviceAddress_Like != null && serviceAddress_Like.trim().length() > 0) {
		if (!serviceAddress_Like.startsWith("%")) {
		    serviceAddress_Like = "%" + serviceAddress_Like;
		}
		if (!serviceAddress_Like.endsWith("%")) {
		   serviceAddress_Like = serviceAddress_Like + "%";
		}
	    }
	return serviceAddress_Like;
    }

    public List<String> getServiceAddress_s(){
	return serviceAddress_s;
    }


    public String getFullTextServer_(){
        return fullTextServer_;
    }

    public String getFullTextServer_Like(){
	    if (fullTextServer_Like != null && fullTextServer_Like.trim().length() > 0) {
		if (!fullTextServer_Like.startsWith("%")) {
		    fullTextServer_Like = "%" + fullTextServer_Like;
		}
		if (!fullTextServer_Like.endsWith("%")) {
		   fullTextServer_Like = fullTextServer_Like + "%";
		}
	    }
	return fullTextServer_Like;
    }

    public List<String> getFullTextServer_s(){
	return fullTextServer_s;
    }


    public String getIndexName_(){
        return indexName_;
    }

    public String getIndexName_Like(){
	    if (indexName_Like != null && indexName_Like.trim().length() > 0) {
		if (!indexName_Like.startsWith("%")) {
		    indexName_Like = "%" + indexName_Like;
		}
		if (!indexName_Like.endsWith("%")) {
		   indexName_Like = indexName_Like + "%";
		}
	    }
	return indexName_Like;
    }

    public List<String> getIndexName_s(){
	return indexName_s;
    }


    public String getTypeName_(){
        return typeName_;
    }

    public String getTypeName_Like(){
	    if (typeName_Like != null && typeName_Like.trim().length() > 0) {
		if (!typeName_Like.startsWith("%")) {
		    typeName_Like = "%" + typeName_Like;
		}
		if (!typeName_Like.endsWith("%")) {
		   typeName_Like = typeName_Like + "%";
		}
	    }
	return typeName_Like;
    }

    public List<String> getTypeName_s(){
	return typeName_s;
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


    public Integer getDeleteFlag_(){
        return deleteFlag_;
    }

    public Integer getDeleteFlag_GreaterThanOrEqual(){
        return deleteFlag_GreaterThanOrEqual;
    }

    public Integer getDeleteFlag_LessThanOrEqual(){
	return deleteFlag_LessThanOrEqual;
    }

    public List<Integer> getDeleteFlag_s(){
	return deleteFlag_s;
    }

 

    public void setServiceName_(String serviceName_){
        this.serviceName_ = serviceName_;
    }

    public void setServiceName_Like( String serviceName_Like){
	this.serviceName_Like = serviceName_Like;
    }

    public void setServiceName_s(List<String> serviceName_s){
        this.serviceName_s = serviceName_s;
    }


    public void setServiceAddress_(String serviceAddress_){
        this.serviceAddress_ = serviceAddress_;
    }

    public void setServiceAddress_Like( String serviceAddress_Like){
	this.serviceAddress_Like = serviceAddress_Like;
    }

    public void setServiceAddress_s(List<String> serviceAddress_s){
        this.serviceAddress_s = serviceAddress_s;
    }


    public void setFullTextServer_(String fullTextServer_){
        this.fullTextServer_ = fullTextServer_;
    }

    public void setFullTextServer_Like( String fullTextServer_Like){
	this.fullTextServer_Like = fullTextServer_Like;
    }

    public void setFullTextServer_s(List<String> fullTextServer_s){
        this.fullTextServer_s = fullTextServer_s;
    }


    public void setIndexName_(String indexName_){
        this.indexName_ = indexName_;
    }

    public void setIndexName_Like( String indexName_Like){
	this.indexName_Like = indexName_Like;
    }

    public void setIndexName_s(List<String> indexName_s){
        this.indexName_s = indexName_s;
    }


    public void setTypeName_(String typeName_){
        this.typeName_ = typeName_;
    }

    public void setTypeName_Like( String typeName_Like){
	this.typeName_Like = typeName_Like;
    }

    public void setTypeName_s(List<String> typeName_s){
        this.typeName_s = typeName_s;
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


    public void setDeleteFlag_(Integer deleteFlag_){
        this.deleteFlag_ = deleteFlag_;
    }

    public void setDeleteFlag_GreaterThanOrEqual(Integer deleteFlag_GreaterThanOrEqual){
        this.deleteFlag_GreaterThanOrEqual = deleteFlag_GreaterThanOrEqual;
    }

    public void setDeleteFlag_LessThanOrEqual(Integer deleteFlag_LessThanOrEqual){
	this.deleteFlag_LessThanOrEqual = deleteFlag_LessThanOrEqual;
    }

    public void setDeleteFlag_s(List<Integer> deleteFlag_s){
        this.deleteFlag_s = deleteFlag_s;
    }




    public SysFullTextSearchSrcQuery serviceName_(String serviceName_){
	if (serviceName_ == null) {
	    throw new RuntimeException("serviceName_ is null");
        }         
	this.serviceName_ = serviceName_;
	return this;
    }

    public SysFullTextSearchSrcQuery serviceName_Like( String serviceName_Like){
        if (serviceName_Like == null) {
            throw new RuntimeException("serviceName_ is null");
        }
        this.serviceName_Like = serviceName_Like;
        return this;
    }

    public SysFullTextSearchSrcQuery serviceName_s(List<String> serviceName_s){
        if (serviceName_s == null) {
            throw new RuntimeException("serviceName_s is empty ");
        }
        this.serviceName_s = serviceName_s;
        return this;
    }


    public SysFullTextSearchSrcQuery serviceAddress_(String serviceAddress_){
	if (serviceAddress_ == null) {
	    throw new RuntimeException("serviceAddress_ is null");
        }         
	this.serviceAddress_ = serviceAddress_;
	return this;
    }

    public SysFullTextSearchSrcQuery serviceAddress_Like( String serviceAddress_Like){
        if (serviceAddress_Like == null) {
            throw new RuntimeException("serviceAddress_ is null");
        }
        this.serviceAddress_Like = serviceAddress_Like;
        return this;
    }

    public SysFullTextSearchSrcQuery serviceAddress_s(List<String> serviceAddress_s){
        if (serviceAddress_s == null) {
            throw new RuntimeException("serviceAddress_s is empty ");
        }
        this.serviceAddress_s = serviceAddress_s;
        return this;
    }


    public SysFullTextSearchSrcQuery fullTextServer_(String fullTextServer_){
	if (fullTextServer_ == null) {
	    throw new RuntimeException("fullTextServer_ is null");
        }         
	this.fullTextServer_ = fullTextServer_;
	return this;
    }

    public SysFullTextSearchSrcQuery fullTextServer_Like( String fullTextServer_Like){
        if (fullTextServer_Like == null) {
            throw new RuntimeException("fullTextServer_ is null");
        }
        this.fullTextServer_Like = fullTextServer_Like;
        return this;
    }

    public SysFullTextSearchSrcQuery fullTextServer_s(List<String> fullTextServer_s){
        if (fullTextServer_s == null) {
            throw new RuntimeException("fullTextServer_s is empty ");
        }
        this.fullTextServer_s = fullTextServer_s;
        return this;
    }


    public SysFullTextSearchSrcQuery indexName_(String indexName_){
	if (indexName_ == null) {
	    throw new RuntimeException("indexName_ is null");
        }         
	this.indexName_ = indexName_;
	return this;
    }

    public SysFullTextSearchSrcQuery indexName_Like( String indexName_Like){
        if (indexName_Like == null) {
            throw new RuntimeException("indexName_ is null");
        }
        this.indexName_Like = indexName_Like;
        return this;
    }

    public SysFullTextSearchSrcQuery indexName_s(List<String> indexName_s){
        if (indexName_s == null) {
            throw new RuntimeException("indexName_s is empty ");
        }
        this.indexName_s = indexName_s;
        return this;
    }


    public SysFullTextSearchSrcQuery typeName_(String typeName_){
	if (typeName_ == null) {
	    throw new RuntimeException("typeName_ is null");
        }         
	this.typeName_ = typeName_;
	return this;
    }

    public SysFullTextSearchSrcQuery typeName_Like( String typeName_Like){
        if (typeName_Like == null) {
            throw new RuntimeException("typeName_ is null");
        }
        this.typeName_Like = typeName_Like;
        return this;
    }

    public SysFullTextSearchSrcQuery typeName_s(List<String> typeName_s){
        if (typeName_s == null) {
            throw new RuntimeException("typeName_s is empty ");
        }
        this.typeName_s = typeName_s;
        return this;
    }


    public SysFullTextSearchSrcQuery createBy_(String createBy_){
	if (createBy_ == null) {
	    throw new RuntimeException("createBy_ is null");
        }         
	this.createBy_ = createBy_;
	return this;
    }

    public SysFullTextSearchSrcQuery createBy_Like( String createBy_Like){
        if (createBy_Like == null) {
            throw new RuntimeException("createBy_ is null");
        }
        this.createBy_Like = createBy_Like;
        return this;
    }

    public SysFullTextSearchSrcQuery createBy_s(List<String> createBy_s){
        if (createBy_s == null) {
            throw new RuntimeException("createBy_s is empty ");
        }
        this.createBy_s = createBy_s;
        return this;
    }



    public SysFullTextSearchSrcQuery createTime_GreaterThanOrEqual(Date createTime_GreaterThanOrEqual){
	if (createTime_GreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime_ is null");
        }         
	this.createTime_GreaterThanOrEqual = createTime_GreaterThanOrEqual;
        return this;
    }

    public SysFullTextSearchSrcQuery createTime_LessThanOrEqual(Date createTime_LessThanOrEqual){
        if (createTime_LessThanOrEqual == null) {
            throw new RuntimeException("createTime_ is null");
        }
        this.createTime_LessThanOrEqual = createTime_LessThanOrEqual;
        return this;
    }



    public SysFullTextSearchSrcQuery updateBy_(String updateBy_){
	if (updateBy_ == null) {
	    throw new RuntimeException("updateBy_ is null");
        }         
	this.updateBy_ = updateBy_;
	return this;
    }

    public SysFullTextSearchSrcQuery updateBy_Like( String updateBy_Like){
        if (updateBy_Like == null) {
            throw new RuntimeException("updateBy_ is null");
        }
        this.updateBy_Like = updateBy_Like;
        return this;
    }

    public SysFullTextSearchSrcQuery updateBy_s(List<String> updateBy_s){
        if (updateBy_s == null) {
            throw new RuntimeException("updateBy_s is empty ");
        }
        this.updateBy_s = updateBy_s;
        return this;
    }



    public SysFullTextSearchSrcQuery updateTime_GreaterThanOrEqual(Date updateTime_GreaterThanOrEqual){
	if (updateTime_GreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime_ is null");
        }         
	this.updateTime_GreaterThanOrEqual = updateTime_GreaterThanOrEqual;
        return this;
    }

    public SysFullTextSearchSrcQuery updateTime_LessThanOrEqual(Date updateTime_LessThanOrEqual){
        if (updateTime_LessThanOrEqual == null) {
            throw new RuntimeException("updateTime_ is null");
        }
        this.updateTime_LessThanOrEqual = updateTime_LessThanOrEqual;
        return this;
    }



    public SysFullTextSearchSrcQuery deleteFlag_(Integer deleteFlag_){
	if (deleteFlag_ == null) {
            throw new RuntimeException("deleteFlag_ is null");
        }         
	this.deleteFlag_ = deleteFlag_;
	return this;
    }

    public SysFullTextSearchSrcQuery deleteFlag_GreaterThanOrEqual(Integer deleteFlag_GreaterThanOrEqual){
	if (deleteFlag_GreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag_ is null");
        }         
	this.deleteFlag_GreaterThanOrEqual = deleteFlag_GreaterThanOrEqual;
        return this;
    }

    public SysFullTextSearchSrcQuery deleteFlag_LessThanOrEqual(Integer deleteFlag_LessThanOrEqual){
        if (deleteFlag_LessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag_ is null");
        }
        this.deleteFlag_LessThanOrEqual = deleteFlag_LessThanOrEqual;
        return this;
    }

    public SysFullTextSearchSrcQuery deleteFlag_s(List<Integer> deleteFlag_s){
        if (deleteFlag_s == null) {
            throw new RuntimeException("deleteFlag_s is empty ");
        }
        this.deleteFlag_s = deleteFlag_s;
        return this;
    }



    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("serviceName_".equals(sortColumn)) {
                orderBy = "E.SERVICE_NAME_" + a_x;
            } 

            if ("serviceAddress_".equals(sortColumn)) {
                orderBy = "E.SERVICE_ADDRESS_" + a_x;
            } 

            if ("fullTextServer_".equals(sortColumn)) {
                orderBy = "E.FULLTEXT_SERVER_" + a_x;
            } 

            if ("indexName_".equals(sortColumn)) {
                orderBy = "E.INDEX_NAME_" + a_x;
            } 

            if ("typeName_".equals(sortColumn)) {
                orderBy = "E.TYPE_NAME_" + a_x;
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

            if ("deleteFlag_".equals(sortColumn)) {
                orderBy = "E.DELETE_FLAG_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("serviceName_", "SERVICE_NAME_");
        addColumn("serviceAddress_", "SERVICE_ADDRESS_");
        addColumn("fullTextServer_", "FULLTEXT_SERVER_");
        addColumn("indexName_", "INDEX_NAME_");
        addColumn("typeName_", "TYPE_NAME_");
        addColumn("createBy_", "CREATEBY_");
        addColumn("createTime_", "CREATETIME_");
        addColumn("updateBy_", "UPDATEBY_");
        addColumn("updateTime_", "UPDATETIME_");
        addColumn("deleteFlag_", "DELETE_FLAG_");
    }

}