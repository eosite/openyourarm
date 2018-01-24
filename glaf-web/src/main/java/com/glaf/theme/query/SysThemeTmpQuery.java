package com.glaf.theme.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SysThemeTmpQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> themeTmpIds;
	protected Collection<String> appActorIds;
  	protected String themeTmpName;
  	protected String themeTmpNameLike;
  	protected List<String> themeTmpNames;
  	protected String themeTmpCode;
  	protected String themeTmpCodeLike;
  	protected List<String> themeTmpCodes;
  	protected String ui;
  	protected String uiLike;
  	protected List<String> uis;
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
  	protected String publisher;
  	protected String publisherLike;
  	protected List<String> publishers;
        protected Date publishTimeGreaterThanOrEqual;
  	protected Date publishTimeLessThanOrEqual;
  	protected Integer status;
  	protected Integer statusGreaterThanOrEqual;
  	protected Integer statusLessThanOrEqual;
  	protected List<Integer> statuss;
  	protected Integer ver;
  	protected Integer verGreaterThanOrEqual;
  	protected Integer verLessThanOrEqual;
  	protected List<Integer> vers;
  	protected Integer defaultFlag;

    public SysThemeTmpQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }

    public Integer getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(Integer defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public String getThemeTmpName(){
        return themeTmpName;
    }

    public String getThemeTmpNameLike(){
	    if (themeTmpNameLike != null && themeTmpNameLike.trim().length() > 0) {
		if (!themeTmpNameLike.startsWith("%")) {
		    themeTmpNameLike = "%" + themeTmpNameLike;
		}
		if (!themeTmpNameLike.endsWith("%")) {
		   themeTmpNameLike = themeTmpNameLike + "%";
		}
	    }
	return themeTmpNameLike;
    }

    public List<String> getThemeTmpNames(){
	return themeTmpNames;
    }


    public String getThemeTmpCode(){
        return themeTmpCode;
    }

    public String getThemeTmpCodeLike(){
	    if (themeTmpCodeLike != null && themeTmpCodeLike.trim().length() > 0) {
		if (!themeTmpCodeLike.startsWith("%")) {
		    themeTmpCodeLike = "%" + themeTmpCodeLike;
		}
		if (!themeTmpCodeLike.endsWith("%")) {
		   themeTmpCodeLike = themeTmpCodeLike + "%";
		}
	    }
	return themeTmpCodeLike;
    }

    public List<String> getThemeTmpCodes(){
	return themeTmpCodes;
    }


    public String getUi(){
        return ui;
    }

    public String getUiLike(){
	    if (uiLike != null && uiLike.trim().length() > 0) {
		if (!uiLike.startsWith("%")) {
		    uiLike = "%" + uiLike;
		}
		if (!uiLike.endsWith("%")) {
		   uiLike = uiLike + "%";
		}
	    }
	return uiLike;
    }

    public List<String> getUis(){
	return uis;
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

    public String getPublisher(){
        return publisher;
    }

    public String getPublisherLike(){
	    if (publisherLike != null && publisherLike.trim().length() > 0) {
		if (!publisherLike.startsWith("%")) {
		    publisherLike = "%" + publisherLike;
		}
		if (!publisherLike.endsWith("%")) {
		   publisherLike = publisherLike + "%";
		}
	    }
	return publisherLike;
    }

    public List<String> getPublishers(){
	return publishers;
    }


    public Date getPublishTimeGreaterThanOrEqual(){
        return publishTimeGreaterThanOrEqual;
    }

    public Date getPublishTimeLessThanOrEqual(){
	return publishTimeLessThanOrEqual;
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

    public Integer getVer(){
        return ver;
    }

    public Integer getVerGreaterThanOrEqual(){
        return verGreaterThanOrEqual;
    }

    public Integer getVerLessThanOrEqual(){
	return verLessThanOrEqual;
    }

    public List<Integer> getVers(){
	return vers;
    }

 

    public void setThemeTmpName(String themeTmpName){
        this.themeTmpName = themeTmpName;
    }

    public void setThemeTmpNameLike( String themeTmpNameLike){
	this.themeTmpNameLike = themeTmpNameLike;
    }

    public void setThemeTmpNames(List<String> themeTmpNames){
        this.themeTmpNames = themeTmpNames;
    }


    public void setThemeTmpCode(String themeTmpCode){
        this.themeTmpCode = themeTmpCode;
    }

    public void setThemeTmpCodeLike( String themeTmpCodeLike){
	this.themeTmpCodeLike = themeTmpCodeLike;
    }

    public void setThemeTmpCodes(List<String> themeTmpCodes){
        this.themeTmpCodes = themeTmpCodes;
    }


    public void setUi(String ui){
        this.ui = ui;
    }

    public void setUiLike( String uiLike){
	this.uiLike = uiLike;
    }

    public void setUis(List<String> uis){
        this.uis = uis;
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


    public void setPublisher(String publisher){
        this.publisher = publisher;
    }

    public void setPublisherLike( String publisherLike){
	this.publisherLike = publisherLike;
    }

    public void setPublishers(List<String> publishers){
        this.publishers = publishers;
    }


    public void setPublishTimeGreaterThanOrEqual(Date publishTimeGreaterThanOrEqual){
        this.publishTimeGreaterThanOrEqual = publishTimeGreaterThanOrEqual;
    }

    public void setPublishTimeLessThanOrEqual(Date publishTimeLessThanOrEqual){
	this.publishTimeLessThanOrEqual = publishTimeLessThanOrEqual;
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


    public void setVer(Integer ver){
        this.ver = ver;
    }

    public void setVerGreaterThanOrEqual(Integer verGreaterThanOrEqual){
        this.verGreaterThanOrEqual = verGreaterThanOrEqual;
    }

    public void setVerLessThanOrEqual(Integer verLessThanOrEqual){
	this.verLessThanOrEqual = verLessThanOrEqual;
    }

    public void setVers(List<Integer> vers){
        this.vers = vers;
    }




    public SysThemeTmpQuery themeTmpName(String themeTmpName){
	if (themeTmpName == null) {
	    throw new RuntimeException("themeTmpName is null");
        }         
	this.themeTmpName = themeTmpName;
	return this;
    }

    public SysThemeTmpQuery themeTmpNameLike( String themeTmpNameLike){
        if (themeTmpNameLike == null) {
            throw new RuntimeException("themeTmpName is null");
        }
        this.themeTmpNameLike = themeTmpNameLike;
        return this;
    }

    public SysThemeTmpQuery themeTmpNames(List<String> themeTmpNames){
        if (themeTmpNames == null) {
            throw new RuntimeException("themeTmpNames is empty ");
        }
        this.themeTmpNames = themeTmpNames;
        return this;
    }


    public SysThemeTmpQuery themeTmpCode(String themeTmpCode){
	if (themeTmpCode == null) {
	    throw new RuntimeException("themeTmpCode is null");
        }         
	this.themeTmpCode = themeTmpCode;
	return this;
    }

    public SysThemeTmpQuery themeTmpCodeLike( String themeTmpCodeLike){
        if (themeTmpCodeLike == null) {
            throw new RuntimeException("themeTmpCode is null");
        }
        this.themeTmpCodeLike = themeTmpCodeLike;
        return this;
    }

    public SysThemeTmpQuery themeTmpCodes(List<String> themeTmpCodes){
        if (themeTmpCodes == null) {
            throw new RuntimeException("themeTmpCodes is empty ");
        }
        this.themeTmpCodes = themeTmpCodes;
        return this;
    }


    public SysThemeTmpQuery ui(String ui){
	if (ui == null) {
	    throw new RuntimeException("ui is null");
        }         
	this.ui = ui;
	return this;
    }

    public SysThemeTmpQuery uiLike( String uiLike){
        if (uiLike == null) {
            throw new RuntimeException("ui is null");
        }
        this.uiLike = uiLike;
        return this;
    }

    public SysThemeTmpQuery uis(List<String> uis){
        if (uis == null) {
            throw new RuntimeException("uis is empty ");
        }
        this.uis = uis;
        return this;
    }


    public SysThemeTmpQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public SysThemeTmpQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public SysThemeTmpQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public SysThemeTmpQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
	if (createTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime is null");
        }         
	this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
        return this;
    }

    public SysThemeTmpQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
        if (createTimeLessThanOrEqual == null) {
            throw new RuntimeException("createTime is null");
        }
        this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
        return this;
    }



    public SysThemeTmpQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public SysThemeTmpQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public SysThemeTmpQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public SysThemeTmpQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
	if (updateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime is null");
        }         
	this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
        return this;
    }

    public SysThemeTmpQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
        if (updateTimeLessThanOrEqual == null) {
            throw new RuntimeException("updateTime is null");
        }
        this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
        return this;
    }



    public SysThemeTmpQuery deleteFlag(Integer deleteFlag){
	if (deleteFlag == null) {
            throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlag = deleteFlag;
	return this;
    }

    public SysThemeTmpQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
	if (deleteFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
        return this;
    }

    public SysThemeTmpQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
        if (deleteFlagLessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag is null");
        }
        this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
        return this;
    }

    public SysThemeTmpQuery deleteFlags(List<Integer> deleteFlags){
        if (deleteFlags == null) {
            throw new RuntimeException("deleteFlags is empty ");
        }
        this.deleteFlags = deleteFlags;
        return this;
    }


    public SysThemeTmpQuery publisher(String publisher){
	if (publisher == null) {
	    throw new RuntimeException("publisher is null");
        }         
	this.publisher = publisher;
	return this;
    }

    public SysThemeTmpQuery publisherLike( String publisherLike){
        if (publisherLike == null) {
            throw new RuntimeException("publisher is null");
        }
        this.publisherLike = publisherLike;
        return this;
    }

    public SysThemeTmpQuery publishers(List<String> publishers){
        if (publishers == null) {
            throw new RuntimeException("publishers is empty ");
        }
        this.publishers = publishers;
        return this;
    }



    public SysThemeTmpQuery publishTimeGreaterThanOrEqual(Date publishTimeGreaterThanOrEqual){
	if (publishTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("publishTime is null");
        }         
	this.publishTimeGreaterThanOrEqual = publishTimeGreaterThanOrEqual;
        return this;
    }

    public SysThemeTmpQuery publishTimeLessThanOrEqual(Date publishTimeLessThanOrEqual){
        if (publishTimeLessThanOrEqual == null) {
            throw new RuntimeException("publishTime is null");
        }
        this.publishTimeLessThanOrEqual = publishTimeLessThanOrEqual;
        return this;
    }



    public SysThemeTmpQuery status(Integer status){
	if (status == null) {
            throw new RuntimeException("status is null");
        }         
	this.status = status;
	return this;
    }

    public SysThemeTmpQuery statusGreaterThanOrEqual(Integer statusGreaterThanOrEqual){
	if (statusGreaterThanOrEqual == null) {
	    throw new RuntimeException("status is null");
        }         
	this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
        return this;
    }

    public SysThemeTmpQuery statusLessThanOrEqual(Integer statusLessThanOrEqual){
        if (statusLessThanOrEqual == null) {
            throw new RuntimeException("status is null");
        }
        this.statusLessThanOrEqual = statusLessThanOrEqual;
        return this;
    }

    public SysThemeTmpQuery statuss(List<Integer> statuss){
        if (statuss == null) {
            throw new RuntimeException("statuss is empty ");
        }
        this.statuss = statuss;
        return this;
    }


    public SysThemeTmpQuery ver(Integer ver){
	if (ver == null) {
            throw new RuntimeException("ver is null");
        }         
	this.ver = ver;
	return this;
    }

    public SysThemeTmpQuery verGreaterThanOrEqual(Integer verGreaterThanOrEqual){
	if (verGreaterThanOrEqual == null) {
	    throw new RuntimeException("ver is null");
        }         
	this.verGreaterThanOrEqual = verGreaterThanOrEqual;
        return this;
    }

    public SysThemeTmpQuery verLessThanOrEqual(Integer verLessThanOrEqual){
        if (verLessThanOrEqual == null) {
            throw new RuntimeException("ver is null");
        }
        this.verLessThanOrEqual = verLessThanOrEqual;
        return this;
    }

    public SysThemeTmpQuery vers(List<Integer> vers){
        if (vers == null) {
            throw new RuntimeException("vers is empty ");
        }
        this.vers = vers;
        return this;
    }



    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("themeTmpName".equals(sortColumn)) {
                orderBy = "E.THEME_TMP_NAME_" + a_x;
            } 

            if ("themeTmpCode".equals(sortColumn)) {
                orderBy = "E.THEME_TMP_CODE_" + a_x;
            } 

            if ("ui".equals(sortColumn)) {
                orderBy = "E.UI_" + a_x;
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

            if ("publisher".equals(sortColumn)) {
                orderBy = "E.PUBLISHER_" + a_x;
            } 

            if ("publishTime".equals(sortColumn)) {
                orderBy = "E.PUBLISH_TIME_" + a_x;
            } 

            if ("status".equals(sortColumn)) {
                orderBy = "E.STATUS_" + a_x;
            } 

            if ("ver".equals(sortColumn)) {
                orderBy = "E.VER_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("themeTmpId", "THEME_TMP_ID_");
        addColumn("themeTmpName", "THEME_TMP_NAME_");
        addColumn("themeTmpCode", "THEME_TMP_CODE_");
        addColumn("ui", "UI_");
        addColumn("createBy", "CREATEBY_");
        addColumn("createTime", "CREATETIME_");
        addColumn("updateBy", "UPDATEBY_");
        addColumn("updateTime", "UPDATETIME_");
        addColumn("deleteFlag", "DELETE_FLAG_");
        addColumn("publisher", "PUBLISHER_");
        addColumn("publishTime", "PUBLISH_TIME_");
        addColumn("status", "STATUS_");
        addColumn("ver", "VER_");
    }

}