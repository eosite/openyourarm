package com.glaf.teim.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class EimServerTmpQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> tmpIds;
	protected Collection<String> appActorIds;
  	protected Long categoryId;
  	protected Long categoryIdGreaterThanOrEqual;
  	protected Long categoryIdLessThanOrEqual;
  	protected List<Long> categoryIds;
  	protected String name;
  	protected String nameLike;
  	protected List<String> names;
  	protected String path_;
  	protected String path_Like;
  	protected List<String> path_s;
  	protected String reqUrlParam;
  	protected String reqUrlParamLike;
  	protected List<String> reqUrlParams;
  	protected String reqType;
  	protected String reqTypeLike;
  	protected List<String> reqTypes;
  	protected String reqHeader;
  	protected String reqHeaderLike;
  	protected List<String> reqHeaders;
  	protected String reqContentType;
  	protected String reqContentTypeLike;
  	protected List<String> reqContentTypes;
  	protected String resContentType;
  	protected String resContentTypeLike;
  	protected List<String> resContentTypes;
  	protected String reqBody;
  	protected String reqBodyLike;
  	protected List<String> reqBodys;
  	protected String response_;
  	protected String response_Like;
  	protected List<String> response_s;
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

    public EimServerTmpQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public Long getCategoryId(){
        return categoryId;
    }

    public Long getCategoryIdGreaterThanOrEqual(){
        return categoryIdGreaterThanOrEqual;
    }

    public Long getCategoryIdLessThanOrEqual(){
	return categoryIdLessThanOrEqual;
    }

    public List<Long> getCategoryIds(){
	return categoryIds;
    }

    public String getName(){
        return name;
    }

    public String getNameLike(){
	    if (nameLike != null && nameLike.trim().length() > 0) {
		if (!nameLike.startsWith("%")) {
		    nameLike = "%" + nameLike;
		}
		if (!nameLike.endsWith("%")) {
		   nameLike = nameLike + "%";
		}
	    }
	return nameLike;
    }

    public List<String> getNames(){
	return names;
    }


    public String getPath_(){
        return path_;
    }

    public String getPath_Like(){
	    if (path_Like != null && path_Like.trim().length() > 0) {
		if (!path_Like.startsWith("%")) {
		    path_Like = "%" + path_Like;
		}
		if (!path_Like.endsWith("%")) {
		   path_Like = path_Like + "%";
		}
	    }
	return path_Like;
    }

    public List<String> getPath_s(){
	return path_s;
    }


    public String getReqUrlParam(){
        return reqUrlParam;
    }

    public String getReqUrlParamLike(){
	    if (reqUrlParamLike != null && reqUrlParamLike.trim().length() > 0) {
		if (!reqUrlParamLike.startsWith("%")) {
		    reqUrlParamLike = "%" + reqUrlParamLike;
		}
		if (!reqUrlParamLike.endsWith("%")) {
		   reqUrlParamLike = reqUrlParamLike + "%";
		}
	    }
	return reqUrlParamLike;
    }

    public List<String> getReqUrlParams(){
	return reqUrlParams;
    }


    public String getReqType(){
        return reqType;
    }

    public String getReqTypeLike(){
	    if (reqTypeLike != null && reqTypeLike.trim().length() > 0) {
		if (!reqTypeLike.startsWith("%")) {
		    reqTypeLike = "%" + reqTypeLike;
		}
		if (!reqTypeLike.endsWith("%")) {
		   reqTypeLike = reqTypeLike + "%";
		}
	    }
	return reqTypeLike;
    }

    public List<String> getReqTypes(){
	return reqTypes;
    }


    public String getReqHeader(){
        return reqHeader;
    }

    public String getReqHeaderLike(){
	    if (reqHeaderLike != null && reqHeaderLike.trim().length() > 0) {
		if (!reqHeaderLike.startsWith("%")) {
		    reqHeaderLike = "%" + reqHeaderLike;
		}
		if (!reqHeaderLike.endsWith("%")) {
		   reqHeaderLike = reqHeaderLike + "%";
		}
	    }
	return reqHeaderLike;
    }

    public List<String> getReqHeaders(){
	return reqHeaders;
    }


    public String getReqContentType(){
        return reqContentType;
    }

    public String getReqContentTypeLike(){
	    if (reqContentTypeLike != null && reqContentTypeLike.trim().length() > 0) {
		if (!reqContentTypeLike.startsWith("%")) {
		    reqContentTypeLike = "%" + reqContentTypeLike;
		}
		if (!reqContentTypeLike.endsWith("%")) {
		   reqContentTypeLike = reqContentTypeLike + "%";
		}
	    }
	return reqContentTypeLike;
    }

    public List<String> getReqContentTypes(){
	return reqContentTypes;
    }


    public String getResContentType(){
        return resContentType;
    }

    public String getResContentTypeLike(){
	    if (resContentTypeLike != null && resContentTypeLike.trim().length() > 0) {
		if (!resContentTypeLike.startsWith("%")) {
		    resContentTypeLike = "%" + resContentTypeLike;
		}
		if (!resContentTypeLike.endsWith("%")) {
		   resContentTypeLike = resContentTypeLike + "%";
		}
	    }
	return resContentTypeLike;
    }

    public List<String> getResContentTypes(){
	return resContentTypes;
    }


    public String getReqBody(){
        return reqBody;
    }

    public String getReqBodyLike(){
	    if (reqBodyLike != null && reqBodyLike.trim().length() > 0) {
		if (!reqBodyLike.startsWith("%")) {
		    reqBodyLike = "%" + reqBodyLike;
		}
		if (!reqBodyLike.endsWith("%")) {
		   reqBodyLike = reqBodyLike + "%";
		}
	    }
	return reqBodyLike;
    }

    public List<String> getReqBodys(){
	return reqBodys;
    }


    public String getResponse_(){
        return response_;
    }

    public String getResponse_Like(){
	    if (response_Like != null && response_Like.trim().length() > 0) {
		if (!response_Like.startsWith("%")) {
		    response_Like = "%" + response_Like;
		}
		if (!response_Like.endsWith("%")) {
		   response_Like = response_Like + "%";
		}
	    }
	return response_Like;
    }

    public List<String> getResponse_s(){
	return response_s;
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

 

    public void setCategoryId(Long categoryId){
        this.categoryId = categoryId;
    }

    public void setCategoryIdGreaterThanOrEqual(Long categoryIdGreaterThanOrEqual){
        this.categoryIdGreaterThanOrEqual = categoryIdGreaterThanOrEqual;
    }

    public void setCategoryIdLessThanOrEqual(Long categoryIdLessThanOrEqual){
	this.categoryIdLessThanOrEqual = categoryIdLessThanOrEqual;
    }

    public void setCategoryIds(List<Long> categoryIds){
        this.categoryIds = categoryIds;
    }


    public void setName(String name){
        this.name = name;
    }

    public void setNameLike( String nameLike){
	this.nameLike = nameLike;
    }

    public void setNames(List<String> names){
        this.names = names;
    }


    public void setPath_(String path_){
        this.path_ = path_;
    }

    public void setPath_Like( String path_Like){
	this.path_Like = path_Like;
    }

    public void setPath_s(List<String> path_s){
        this.path_s = path_s;
    }


    public void setReqUrlParam(String reqUrlParam){
        this.reqUrlParam = reqUrlParam;
    }

    public void setReqUrlParamLike( String reqUrlParamLike){
	this.reqUrlParamLike = reqUrlParamLike;
    }

    public void setReqUrlParams(List<String> reqUrlParams){
        this.reqUrlParams = reqUrlParams;
    }


    public void setReqType(String reqType){
        this.reqType = reqType;
    }

    public void setReqTypeLike( String reqTypeLike){
	this.reqTypeLike = reqTypeLike;
    }

    public void setReqTypes(List<String> reqTypes){
        this.reqTypes = reqTypes;
    }


    public void setReqHeader(String reqHeader){
        this.reqHeader = reqHeader;
    }

    public void setReqHeaderLike( String reqHeaderLike){
	this.reqHeaderLike = reqHeaderLike;
    }

    public void setReqHeaders(List<String> reqHeaders){
        this.reqHeaders = reqHeaders;
    }


    public void setReqContentType(String reqContentType){
        this.reqContentType = reqContentType;
    }

    public void setReqContentTypeLike( String reqContentTypeLike){
	this.reqContentTypeLike = reqContentTypeLike;
    }

    public void setReqContentTypes(List<String> reqContentTypes){
        this.reqContentTypes = reqContentTypes;
    }


    public void setResContentType(String resContentType){
        this.resContentType = resContentType;
    }

    public void setResContentTypeLike( String resContentTypeLike){
	this.resContentTypeLike = resContentTypeLike;
    }

    public void setResContentTypes(List<String> resContentTypes){
        this.resContentTypes = resContentTypes;
    }


    public void setReqBody(String reqBody){
        this.reqBody = reqBody;
    }

    public void setReqBodyLike( String reqBodyLike){
	this.reqBodyLike = reqBodyLike;
    }

    public void setReqBodys(List<String> reqBodys){
        this.reqBodys = reqBodys;
    }


    public void setResponse_(String response_){
        this.response_ = response_;
    }

    public void setResponse_Like( String response_Like){
	this.response_Like = response_Like;
    }

    public void setResponse_s(List<String> response_s){
        this.response_s = response_s;
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




    public EimServerTmpQuery categoryId(Long categoryId){
	if (categoryId == null) {
            throw new RuntimeException("categoryId is null");
        }         
	this.categoryId = categoryId;
	return this;
    }

    public EimServerTmpQuery categoryIdGreaterThanOrEqual(Long categoryIdGreaterThanOrEqual){
	if (categoryIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("categoryId is null");
        }         
	this.categoryIdGreaterThanOrEqual = categoryIdGreaterThanOrEqual;
        return this;
    }

    public EimServerTmpQuery categoryIdLessThanOrEqual(Long categoryIdLessThanOrEqual){
        if (categoryIdLessThanOrEqual == null) {
            throw new RuntimeException("categoryId is null");
        }
        this.categoryIdLessThanOrEqual = categoryIdLessThanOrEqual;
        return this;
    }

    public EimServerTmpQuery categoryIds(List<Long> categoryIds){
        if (categoryIds == null) {
            throw new RuntimeException("categoryIds is empty ");
        }
        this.categoryIds = categoryIds;
        return this;
    }


    public EimServerTmpQuery name(String name){
	if (name == null) {
	    throw new RuntimeException("name is null");
        }         
	this.name = name;
	return this;
    }

    public EimServerTmpQuery nameLike( String nameLike){
        if (nameLike == null) {
            throw new RuntimeException("name is null");
        }
        this.nameLike = nameLike;
        return this;
    }

    public EimServerTmpQuery names(List<String> names){
        if (names == null) {
            throw new RuntimeException("names is empty ");
        }
        this.names = names;
        return this;
    }


    public EimServerTmpQuery path_(String path_){
	if (path_ == null) {
	    throw new RuntimeException("path_ is null");
        }         
	this.path_ = path_;
	return this;
    }

    public EimServerTmpQuery path_Like( String path_Like){
        if (path_Like == null) {
            throw new RuntimeException("path_ is null");
        }
        this.path_Like = path_Like;
        return this;
    }

    public EimServerTmpQuery path_s(List<String> path_s){
        if (path_s == null) {
            throw new RuntimeException("path_s is empty ");
        }
        this.path_s = path_s;
        return this;
    }


    public EimServerTmpQuery reqUrlParam(String reqUrlParam){
	if (reqUrlParam == null) {
	    throw new RuntimeException("reqUrlParam is null");
        }         
	this.reqUrlParam = reqUrlParam;
	return this;
    }

    public EimServerTmpQuery reqUrlParamLike( String reqUrlParamLike){
        if (reqUrlParamLike == null) {
            throw new RuntimeException("reqUrlParam is null");
        }
        this.reqUrlParamLike = reqUrlParamLike;
        return this;
    }

    public EimServerTmpQuery reqUrlParams(List<String> reqUrlParams){
        if (reqUrlParams == null) {
            throw new RuntimeException("reqUrlParams is empty ");
        }
        this.reqUrlParams = reqUrlParams;
        return this;
    }


    public EimServerTmpQuery reqType(String reqType){
	if (reqType == null) {
	    throw new RuntimeException("reqType is null");
        }         
	this.reqType = reqType;
	return this;
    }

    public EimServerTmpQuery reqTypeLike( String reqTypeLike){
        if (reqTypeLike == null) {
            throw new RuntimeException("reqType is null");
        }
        this.reqTypeLike = reqTypeLike;
        return this;
    }

    public EimServerTmpQuery reqTypes(List<String> reqTypes){
        if (reqTypes == null) {
            throw new RuntimeException("reqTypes is empty ");
        }
        this.reqTypes = reqTypes;
        return this;
    }


    public EimServerTmpQuery reqHeader(String reqHeader){
	if (reqHeader == null) {
	    throw new RuntimeException("reqHeader is null");
        }         
	this.reqHeader = reqHeader;
	return this;
    }

    public EimServerTmpQuery reqHeaderLike( String reqHeaderLike){
        if (reqHeaderLike == null) {
            throw new RuntimeException("reqHeader is null");
        }
        this.reqHeaderLike = reqHeaderLike;
        return this;
    }

    public EimServerTmpQuery reqHeaders(List<String> reqHeaders){
        if (reqHeaders == null) {
            throw new RuntimeException("reqHeaders is empty ");
        }
        this.reqHeaders = reqHeaders;
        return this;
    }


    public EimServerTmpQuery reqContentType(String reqContentType){
	if (reqContentType == null) {
	    throw new RuntimeException("reqContentType is null");
        }         
	this.reqContentType = reqContentType;
	return this;
    }

    public EimServerTmpQuery reqContentTypeLike( String reqContentTypeLike){
        if (reqContentTypeLike == null) {
            throw new RuntimeException("reqContentType is null");
        }
        this.reqContentTypeLike = reqContentTypeLike;
        return this;
    }

    public EimServerTmpQuery reqContentTypes(List<String> reqContentTypes){
        if (reqContentTypes == null) {
            throw new RuntimeException("reqContentTypes is empty ");
        }
        this.reqContentTypes = reqContentTypes;
        return this;
    }


    public EimServerTmpQuery resContentType(String resContentType){
	if (resContentType == null) {
	    throw new RuntimeException("resContentType is null");
        }         
	this.resContentType = resContentType;
	return this;
    }

    public EimServerTmpQuery resContentTypeLike( String resContentTypeLike){
        if (resContentTypeLike == null) {
            throw new RuntimeException("resContentType is null");
        }
        this.resContentTypeLike = resContentTypeLike;
        return this;
    }

    public EimServerTmpQuery resContentTypes(List<String> resContentTypes){
        if (resContentTypes == null) {
            throw new RuntimeException("resContentTypes is empty ");
        }
        this.resContentTypes = resContentTypes;
        return this;
    }


    public EimServerTmpQuery reqBody(String reqBody){
	if (reqBody == null) {
	    throw new RuntimeException("reqBody is null");
        }         
	this.reqBody = reqBody;
	return this;
    }

    public EimServerTmpQuery reqBodyLike( String reqBodyLike){
        if (reqBodyLike == null) {
            throw new RuntimeException("reqBody is null");
        }
        this.reqBodyLike = reqBodyLike;
        return this;
    }

    public EimServerTmpQuery reqBodys(List<String> reqBodys){
        if (reqBodys == null) {
            throw new RuntimeException("reqBodys is empty ");
        }
        this.reqBodys = reqBodys;
        return this;
    }


    public EimServerTmpQuery response_(String response_){
	if (response_ == null) {
	    throw new RuntimeException("response_ is null");
        }         
	this.response_ = response_;
	return this;
    }

    public EimServerTmpQuery response_Like( String response_Like){
        if (response_Like == null) {
            throw new RuntimeException("response_ is null");
        }
        this.response_Like = response_Like;
        return this;
    }

    public EimServerTmpQuery response_s(List<String> response_s){
        if (response_s == null) {
            throw new RuntimeException("response_s is empty ");
        }
        this.response_s = response_s;
        return this;
    }


    public EimServerTmpQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public EimServerTmpQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public EimServerTmpQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public EimServerTmpQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
	if (createTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime is null");
        }         
	this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
        return this;
    }

    public EimServerTmpQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
        if (createTimeLessThanOrEqual == null) {
            throw new RuntimeException("createTime is null");
        }
        this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
        return this;
    }



    public EimServerTmpQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public EimServerTmpQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public EimServerTmpQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public EimServerTmpQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
	if (updateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime is null");
        }         
	this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
        return this;
    }

    public EimServerTmpQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
        if (updateTimeLessThanOrEqual == null) {
            throw new RuntimeException("updateTime is null");
        }
        this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
        return this;
    }



    public EimServerTmpQuery deleteFlag(Integer deleteFlag){
	if (deleteFlag == null) {
            throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlag = deleteFlag;
	return this;
    }

    public EimServerTmpQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
	if (deleteFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
        return this;
    }

    public EimServerTmpQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
        if (deleteFlagLessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag is null");
        }
        this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
        return this;
    }

    public EimServerTmpQuery deleteFlags(List<Integer> deleteFlags){
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

            if ("categoryId".equals(sortColumn)) {
                orderBy = "E.CATEGORY_ID_" + a_x;
            } 

            if ("name".equals(sortColumn)) {
                orderBy = "E.NAME_" + a_x;
            } 

            if ("path_".equals(sortColumn)) {
                orderBy = "E.PATH_" + a_x;
            } 

            if ("reqUrlParam".equals(sortColumn)) {
                orderBy = "E.REQ_URL_PARAM_" + a_x;
            } 

            if ("reqType".equals(sortColumn)) {
                orderBy = "E.REQ_TYPE_" + a_x;
            } 

            if ("reqHeader".equals(sortColumn)) {
                orderBy = "E.REQ_HEADER_" + a_x;
            } 

            if ("reqContentType".equals(sortColumn)) {
                orderBy = "E.REQ_CONTENT_TYPE_" + a_x;
            } 

            if ("resContentType".equals(sortColumn)) {
                orderBy = "E.RES_CONTENT_TYPE_" + a_x;
            } 

            if ("reqBody".equals(sortColumn)) {
                orderBy = "E.REQ_BODY_" + a_x;
            } 

            if ("response_".equals(sortColumn)) {
                orderBy = "E.RESPONSE_" + a_x;
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
        addColumn("tmpId", "TMP_ID_");
        addColumn("categoryId", "CATEGORY_ID_");
        addColumn("name", "NAME_");
        addColumn("path_", "PATH_");
        addColumn("reqUrlParam", "REQ_URL_PARAM_");
        addColumn("reqType", "REQ_TYPE_");
        addColumn("reqHeader", "REQ_HEADER_");
        addColumn("reqContentType", "REQ_CONTENT_TYPE_");
        addColumn("resContentType", "RES_CONTENT_TYPE_");
        addColumn("reqBody", "REQ_BODY_");
        addColumn("response_", "RESPONSE_");
        addColumn("createBy", "CREATEBY_");
        addColumn("createTime", "CREATETIME_");
        addColumn("updateBy", "UPDATEBY_");
        addColumn("updateTime", "UPDATETIME_");
        addColumn("deleteFlag", "DELETE_FLAG_");
    }

}