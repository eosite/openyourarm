package com.glaf.base.modules.uis.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class UisAppRegisterQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> appIds;
	protected Collection<String> appActorIds;
  	protected String appName;
  	protected String appNameLike;
  	protected List<String> appNames;
  	protected String loginAddress;
  	protected String loginAddressLike;
  	protected List<String> loginAddresss;
  	protected String ssoServiceId;
  	protected String ssoServiceIdLike;
  	protected List<String> ssoServiceIds;
  	protected Integer status;
  	protected Integer statusGreaterThanOrEqual;
  	protected Integer statusLessThanOrEqual;
  	protected List<Integer> statuss;
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
  	protected Integer deleteFlag;
  	protected Integer deleteFlagGreaterThanOrEqual;
  	protected Integer deleteFlagLessThanOrEqual;
  	protected List<Integer> deleteFlags;

    public UisAppRegisterQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getAppName(){
        return appName;
    }

    public String getAppNameLike(){
	    if (appNameLike != null && appNameLike.trim().length() > 0) {
		if (!appNameLike.startsWith("%")) {
		    appNameLike = "%" + appNameLike;
		}
		if (!appNameLike.endsWith("%")) {
		   appNameLike = appNameLike + "%";
		}
	    }
	return appNameLike;
    }

    public List<String> getAppNames(){
	return appNames;
    }


    public String getLoginAddress(){
        return loginAddress;
    }

    public String getLoginAddressLike(){
	    if (loginAddressLike != null && loginAddressLike.trim().length() > 0) {
		if (!loginAddressLike.startsWith("%")) {
		    loginAddressLike = "%" + loginAddressLike;
		}
		if (!loginAddressLike.endsWith("%")) {
		   loginAddressLike = loginAddressLike + "%";
		}
	    }
	return loginAddressLike;
    }

    public List<String> getLoginAddresss(){
	return loginAddresss;
    }


    public String getSsoServiceId(){
        return ssoServiceId;
    }

    public String getSsoServiceIdLike(){
	    if (ssoServiceIdLike != null && ssoServiceIdLike.trim().length() > 0) {
		if (!ssoServiceIdLike.startsWith("%")) {
		    ssoServiceIdLike = "%" + ssoServiceIdLike;
		}
		if (!ssoServiceIdLike.endsWith("%")) {
		   ssoServiceIdLike = ssoServiceIdLike + "%";
		}
	    }
	return ssoServiceIdLike;
    }

    public List<String> getSsoServiceIds(){
	return ssoServiceIds;
    }


    public Integer getStatus(){
        return status;
    }

    public Integer getStatusGreaterThanOrEqual(){
        return statusGreaterThanOrEqual;
    }

    public Integer getStatusLessThanOrEqual(){
	return statusLessThanOrEqual;
    }

    public List<Integer> getStatuss(){
	return statuss;
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


    public Integer getDeleteFlag(){
        return deleteFlag;
    }

    public Integer getDeleteFlagGreaterThanOrEqual(){
        return deleteFlagGreaterThanOrEqual;
    }

    public Integer getDeleteFlagLessThanOrEqual(){
	return deleteFlagLessThanOrEqual;
    }

    public List<Integer> getDeleteFlags(){
	return deleteFlags;
    }

 

    public void setAppName(String appName){
        this.appName = appName;
    }

    public void setAppNameLike( String appNameLike){
	this.appNameLike = appNameLike;
    }

    public void setAppNames(List<String> appNames){
        this.appNames = appNames;
    }


    public void setLoginAddress(String loginAddress){
        this.loginAddress = loginAddress;
    }

    public void setLoginAddressLike( String loginAddressLike){
	this.loginAddressLike = loginAddressLike;
    }

    public void setLoginAddresss(List<String> loginAddresss){
        this.loginAddresss = loginAddresss;
    }


    public void setSsoServiceId(String ssoServiceId){
        this.ssoServiceId = ssoServiceId;
    }

    public void setSsoServiceIdLike( String ssoServiceIdLike){
	this.ssoServiceIdLike = ssoServiceIdLike;
    }

    public void setSsoServiceIds(List<String> ssoServiceIds){
        this.ssoServiceIds = ssoServiceIds;
    }


    public void setStatus(Integer status){
        this.status = status;
    }

    public void setStatusGreaterThanOrEqual(Integer statusGreaterThanOrEqual){
        this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
    }

    public void setStatusLessThanOrEqual(Integer statusLessThanOrEqual){
	this.statusLessThanOrEqual = statusLessThanOrEqual;
    }

    public void setStatuss(List<Integer> statuss){
        this.statuss = statuss;
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


    public void setDeleteFlag(Integer deleteFlag){
        this.deleteFlag = deleteFlag;
    }

    public void setDeleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
        this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
    }

    public void setDeleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
	this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
    }

    public void setDeleteFlags(List<Integer> deleteFlags){
        this.deleteFlags = deleteFlags;
    }




    public UisAppRegisterQuery appName(String appName){
	if (appName == null) {
	    throw new RuntimeException("appName is null");
        }         
	this.appName = appName;
	return this;
    }

    public UisAppRegisterQuery appNameLike( String appNameLike){
        if (appNameLike == null) {
            throw new RuntimeException("appName is null");
        }
        this.appNameLike = appNameLike;
        return this;
    }

    public UisAppRegisterQuery appNames(List<String> appNames){
        if (appNames == null) {
            throw new RuntimeException("appNames is empty ");
        }
        this.appNames = appNames;
        return this;
    }


    public UisAppRegisterQuery loginAddress(String loginAddress){
	if (loginAddress == null) {
	    throw new RuntimeException("loginAddress is null");
        }         
	this.loginAddress = loginAddress;
	return this;
    }

    public UisAppRegisterQuery loginAddressLike( String loginAddressLike){
        if (loginAddressLike == null) {
            throw new RuntimeException("loginAddress is null");
        }
        this.loginAddressLike = loginAddressLike;
        return this;
    }

    public UisAppRegisterQuery loginAddresss(List<String> loginAddresss){
        if (loginAddresss == null) {
            throw new RuntimeException("loginAddresss is empty ");
        }
        this.loginAddresss = loginAddresss;
        return this;
    }


    public UisAppRegisterQuery ssoServiceId(String ssoServiceId){
	if (ssoServiceId == null) {
	    throw new RuntimeException("ssoServiceId is null");
        }         
	this.ssoServiceId = ssoServiceId;
	return this;
    }

    public UisAppRegisterQuery ssoServiceIdLike( String ssoServiceIdLike){
        if (ssoServiceIdLike == null) {
            throw new RuntimeException("ssoServiceId is null");
        }
        this.ssoServiceIdLike = ssoServiceIdLike;
        return this;
    }

    public UisAppRegisterQuery ssoServiceIds(List<String> ssoServiceIds){
        if (ssoServiceIds == null) {
            throw new RuntimeException("ssoServiceIds is empty ");
        }
        this.ssoServiceIds = ssoServiceIds;
        return this;
    }


    public UisAppRegisterQuery status(Integer status){
	if (status == null) {
            throw new RuntimeException("status is null");
        }         
	this.status = status;
	return this;
    }

    public UisAppRegisterQuery statusGreaterThanOrEqual(Integer statusGreaterThanOrEqual){
	if (statusGreaterThanOrEqual == null) {
	    throw new RuntimeException("status is null");
        }         
	this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
        return this;
    }

    public UisAppRegisterQuery statusLessThanOrEqual(Integer statusLessThanOrEqual){
        if (statusLessThanOrEqual == null) {
            throw new RuntimeException("status is null");
        }
        this.statusLessThanOrEqual = statusLessThanOrEqual;
        return this;
    }

    public UisAppRegisterQuery statuss(List<Integer> statuss){
        if (statuss == null) {
            throw new RuntimeException("statuss is empty ");
        }
        this.statuss = statuss;
        return this;
    }


    public UisAppRegisterQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public UisAppRegisterQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public UisAppRegisterQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public UisAppRegisterQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
	if (createTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime is null");
        }         
	this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
        return this;
    }

    public UisAppRegisterQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
        if (createTimeLessThanOrEqual == null) {
            throw new RuntimeException("createTime is null");
        }
        this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
        return this;
    }



    public UisAppRegisterQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public UisAppRegisterQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public UisAppRegisterQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public UisAppRegisterQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
	if (updateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime is null");
        }         
	this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
        return this;
    }

    public UisAppRegisterQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
        if (updateTimeLessThanOrEqual == null) {
            throw new RuntimeException("updateTime is null");
        }
        this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
        return this;
    }



    public UisAppRegisterQuery deleteFlag(Integer deleteFlag){
	if (deleteFlag == null) {
            throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlag = deleteFlag;
	return this;
    }

    public UisAppRegisterQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
	if (deleteFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
        return this;
    }

    public UisAppRegisterQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
        if (deleteFlagLessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag is null");
        }
        this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
        return this;
    }

    public UisAppRegisterQuery deleteFlags(List<Integer> deleteFlags){
        if (deleteFlags == null) {
            throw new RuntimeException("deleteFlags is empty ");
        }
        this.deleteFlags = deleteFlags;
        return this;
    }



    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("appName".equals(sortColumn)) {
                orderBy = "E.APP_NAME_" + a_x;
            } 

            if ("loginAddress".equals(sortColumn)) {
                orderBy = "E.LOGIN_ADDRESS_" + a_x;
            } 

            if ("ssoServiceId".equals(sortColumn)) {
                orderBy = "E.SSO_SERVICE_ID_" + a_x;
            } 

            if ("status".equals(sortColumn)) {
                orderBy = "E.STATUS_" + a_x;
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

            if ("deleteFlag".equals(sortColumn)) {
                orderBy = "E.DELETE_FLAG_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("appId", "APP_ID_");
        addColumn("appName", "APP_NAME_");
        addColumn("loginAddress", "LOGIN_ADDRESS_");
        addColumn("ssoServiceId", "SSO_SERVICE_ID_");
        addColumn("status", "STATUS_");
        addColumn("createBy", "CREATEBY_");
        addColumn("createTime", "CREATETIME_");
        addColumn("updateBy", "UPDATEBY_");
        addColumn("updateTime", "UPDATETIME_");
        addColumn("deleteFlag", "DELETE_FLAG_");
    }

}