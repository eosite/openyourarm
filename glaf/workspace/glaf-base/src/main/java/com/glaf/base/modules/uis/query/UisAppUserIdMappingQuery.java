package com.glaf.base.modules.uis.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class UisAppUserIdMappingQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
  	protected String appUserId;
  	protected String appUserIdLike;
  	protected List<String> appUserIds;
  	protected String userId;
  	protected String userIdLike;
  	protected List<String> userIds;
  	protected String appId;
  	protected String appIdLike;
  	protected List<String> appIds;

    public UisAppUserIdMappingQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getAppUserId(){
        return appUserId;
    }

    public String getAppUserIdLike(){
	    if (appUserIdLike != null && appUserIdLike.trim().length() > 0) {
		if (!appUserIdLike.startsWith("%")) {
		    appUserIdLike = "%" + appUserIdLike;
		}
		if (!appUserIdLike.endsWith("%")) {
		   appUserIdLike = appUserIdLike + "%";
		}
	    }
	return appUserIdLike;
    }

    public List<String> getAppUserIds(){
	return appUserIds;
    }


    public String getUserId(){
        return userId;
    }

    public String getUserIdLike(){
	    if (userIdLike != null && userIdLike.trim().length() > 0) {
		if (!userIdLike.startsWith("%")) {
		    userIdLike = "%" + userIdLike;
		}
		if (!userIdLike.endsWith("%")) {
		   userIdLike = userIdLike + "%";
		}
	    }
	return userIdLike;
    }

    public List<String> getUserIds(){
	return userIds;
    }


    public String getAppId(){
        return appId;
    }

    public String getAppIdLike(){
	    if (appIdLike != null && appIdLike.trim().length() > 0) {
		if (!appIdLike.startsWith("%")) {
		    appIdLike = "%" + appIdLike;
		}
		if (!appIdLike.endsWith("%")) {
		   appIdLike = appIdLike + "%";
		}
	    }
	return appIdLike;
    }

    public List<String> getAppIds(){
	return appIds;
    }


 

    public void setAppUserId(String appUserId){
        this.appUserId = appUserId;
    }

    public void setAppUserIdLike( String appUserIdLike){
	this.appUserIdLike = appUserIdLike;
    }

    public void setAppUserIds(List<String> appUserIds){
        this.appUserIds = appUserIds;
    }


    public void setUserId(String userId){
        this.userId = userId;
    }

    public void setUserIdLike( String userIdLike){
	this.userIdLike = userIdLike;
    }

    public void setUserIds(List<String> userIds){
        this.userIds = userIds;
    }


    public void setAppId(String appId){
        this.appId = appId;
    }

    public void setAppIdLike( String appIdLike){
	this.appIdLike = appIdLike;
    }

    public void setAppIds(List<String> appIds){
        this.appIds = appIds;
    }




    public UisAppUserIdMappingQuery appUserId(String appUserId){
	if (appUserId == null) {
	    throw new RuntimeException("appUserId is null");
        }         
	this.appUserId = appUserId;
	return this;
    }

    public UisAppUserIdMappingQuery appUserIdLike( String appUserIdLike){
        if (appUserIdLike == null) {
            throw new RuntimeException("appUserId is null");
        }
        this.appUserIdLike = appUserIdLike;
        return this;
    }

    public UisAppUserIdMappingQuery appUserIds(List<String> appUserIds){
        if (appUserIds == null) {
            throw new RuntimeException("appUserIds is empty ");
        }
        this.appUserIds = appUserIds;
        return this;
    }


    public UisAppUserIdMappingQuery userId(String userId){
	if (userId == null) {
	    throw new RuntimeException("userId is null");
        }         
	this.userId = userId;
	return this;
    }

    public UisAppUserIdMappingQuery userIdLike( String userIdLike){
        if (userIdLike == null) {
            throw new RuntimeException("userId is null");
        }
        this.userIdLike = userIdLike;
        return this;
    }

    public UisAppUserIdMappingQuery userIds(List<String> userIds){
        if (userIds == null) {
            throw new RuntimeException("userIds is empty ");
        }
        this.userIds = userIds;
        return this;
    }


    public UisAppUserIdMappingQuery appId(String appId){
	if (appId == null) {
	    throw new RuntimeException("appId is null");
        }         
	this.appId = appId;
	return this;
    }

    public UisAppUserIdMappingQuery appIdLike( String appIdLike){
        if (appIdLike == null) {
            throw new RuntimeException("appId is null");
        }
        this.appIdLike = appIdLike;
        return this;
    }

    public UisAppUserIdMappingQuery appIds(List<String> appIds){
        if (appIds == null) {
            throw new RuntimeException("appIds is empty ");
        }
        this.appIds = appIds;
        return this;
    }



    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("appUserId".equals(sortColumn)) {
                orderBy = "E.APP_USER_ID_" + a_x;
            } 

            if ("userId".equals(sortColumn)) {
                orderBy = "E.USER_ID_" + a_x;
            } 

            if ("appId".equals(sortColumn)) {
                orderBy = "E.APP_ID_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("appUserId", "APP_USER_ID_");
        addColumn("userId", "USER_ID_");
        addColumn("appId", "APP_ID_");
    }

}