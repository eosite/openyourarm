package com.glaf.web.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class PageResourceQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> resIds;
	protected Collection<String> appActorIds;
  	protected String resPath;
  	protected String resPathLike;
  	protected List<String> resPaths;
  	protected String resFileName;
  	protected String resFileNameLike;
  	protected List<String> resFileNames;
  	protected String resName;
  	protected String resNameLike;
  	protected List<String> resNames;
  	protected String resType;
  	protected String resTypeLike;
  	protected List<String> resTypes;
  	protected String resMime;
  	protected String resMimeLike;
  	protected List<String> resMimes;
        protected Date resCrDatetimeGreaterThanOrEqual;
  	protected Date resCrDatetimeLessThanOrEqual;

    public PageResourceQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getResPath(){
        return resPath;
    }

    public String getResPathLike(){
	    if (resPathLike != null && resPathLike.trim().length() > 0) {
		if (!resPathLike.startsWith("%")) {
		    resPathLike = "%" + resPathLike;
		}
		if (!resPathLike.endsWith("%")) {
		   resPathLike = resPathLike + "%";
		}
	    }
	return resPathLike;
    }

    public List<String> getResPaths(){
	return resPaths;
    }


    public String getResFileName(){
        return resFileName;
    }

    public String getResFileNameLike(){
	    if (resFileNameLike != null && resFileNameLike.trim().length() > 0) {
		if (!resFileNameLike.startsWith("%")) {
		    resFileNameLike = "%" + resFileNameLike;
		}
		if (!resFileNameLike.endsWith("%")) {
		   resFileNameLike = resFileNameLike + "%";
		}
	    }
	return resFileNameLike;
    }

    public List<String> getResFileNames(){
	return resFileNames;
    }


    public String getResName(){
        return resName;
    }

    public String getResNameLike(){
	    if (resNameLike != null && resNameLike.trim().length() > 0) {
		if (!resNameLike.startsWith("%")) {
		    resNameLike = "%" + resNameLike;
		}
		if (!resNameLike.endsWith("%")) {
		   resNameLike = resNameLike + "%";
		}
	    }
	return resNameLike;
    }

    public List<String> getResNames(){
	return resNames;
    }


    public String getResType(){
        return resType;
    }

    public String getResTypeLike(){
	    if (resTypeLike != null && resTypeLike.trim().length() > 0) {
		if (!resTypeLike.startsWith("%")) {
		    resTypeLike = "%" + resTypeLike;
		}
		if (!resTypeLike.endsWith("%")) {
		   resTypeLike = resTypeLike + "%";
		}
	    }
	return resTypeLike;
    }

    public List<String> getResTypes(){
	return resTypes;
    }


    public String getResMime(){
        return resMime;
    }

    public String getResMimeLike(){
	    if (resMimeLike != null && resMimeLike.trim().length() > 0) {
		if (!resMimeLike.startsWith("%")) {
		    resMimeLike = "%" + resMimeLike;
		}
		if (!resMimeLike.endsWith("%")) {
		   resMimeLike = resMimeLike + "%";
		}
	    }
	return resMimeLike;
    }

    public List<String> getResMimes(){
	return resMimes;
    }


    public Date getResCrDatetimeGreaterThanOrEqual(){
        return resCrDatetimeGreaterThanOrEqual;
    }

    public Date getResCrDatetimeLessThanOrEqual(){
	return resCrDatetimeLessThanOrEqual;
    }


 

    public void setResPath(String resPath){
        this.resPath = resPath;
    }

    public void setResPathLike( String resPathLike){
	this.resPathLike = resPathLike;
    }

    public void setResPaths(List<String> resPaths){
        this.resPaths = resPaths;
    }


    public void setResFileName(String resFileName){
        this.resFileName = resFileName;
    }

    public void setResFileNameLike( String resFileNameLike){
	this.resFileNameLike = resFileNameLike;
    }

    public void setResFileNames(List<String> resFileNames){
        this.resFileNames = resFileNames;
    }


    public void setResName(String resName){
        this.resName = resName;
    }

    public void setResNameLike( String resNameLike){
	this.resNameLike = resNameLike;
    }

    public void setResNames(List<String> resNames){
        this.resNames = resNames;
    }


    public void setResType(String resType){
        this.resType = resType;
    }

    public void setResTypeLike( String resTypeLike){
	this.resTypeLike = resTypeLike;
    }

    public void setResTypes(List<String> resTypes){
        this.resTypes = resTypes;
    }


    public void setResMime(String resMime){
        this.resMime = resMime;
    }

    public void setResMimeLike( String resMimeLike){
	this.resMimeLike = resMimeLike;
    }

    public void setResMimes(List<String> resMimes){
        this.resMimes = resMimes;
    }


    public void setResCrDatetimeGreaterThanOrEqual(Date resCrDatetimeGreaterThanOrEqual){
        this.resCrDatetimeGreaterThanOrEqual = resCrDatetimeGreaterThanOrEqual;
    }

    public void setResCrDatetimeLessThanOrEqual(Date resCrDatetimeLessThanOrEqual){
	this.resCrDatetimeLessThanOrEqual = resCrDatetimeLessThanOrEqual;
    }




    public PageResourceQuery resPath(String resPath){
	if (resPath == null) {
	    throw new RuntimeException("resPath is null");
        }         
	this.resPath = resPath;
	return this;
    }

    public PageResourceQuery resPathLike( String resPathLike){
        if (resPathLike == null) {
            throw new RuntimeException("resPath is null");
        }
        this.resPathLike = resPathLike;
        return this;
    }

    public PageResourceQuery resPaths(List<String> resPaths){
        if (resPaths == null) {
            throw new RuntimeException("resPaths is empty ");
        }
        this.resPaths = resPaths;
        return this;
    }


    public PageResourceQuery resFileName(String resFileName){
	if (resFileName == null) {
	    throw new RuntimeException("resFileName is null");
        }         
	this.resFileName = resFileName;
	return this;
    }

    public PageResourceQuery resFileNameLike( String resFileNameLike){
        if (resFileNameLike == null) {
            throw new RuntimeException("resFileName is null");
        }
        this.resFileNameLike = resFileNameLike;
        return this;
    }

    public PageResourceQuery resFileNames(List<String> resFileNames){
        if (resFileNames == null) {
            throw new RuntimeException("resFileNames is empty ");
        }
        this.resFileNames = resFileNames;
        return this;
    }


    public PageResourceQuery resName(String resName){
	if (resName == null) {
	    throw new RuntimeException("resName is null");
        }         
	this.resName = resName;
	return this;
    }

    public PageResourceQuery resNameLike( String resNameLike){
        if (resNameLike == null) {
            throw new RuntimeException("resName is null");
        }
        this.resNameLike = resNameLike;
        return this;
    }

    public PageResourceQuery resNames(List<String> resNames){
        if (resNames == null) {
            throw new RuntimeException("resNames is empty ");
        }
        this.resNames = resNames;
        return this;
    }


    public PageResourceQuery resType(String resType){
	if (resType == null) {
	    throw new RuntimeException("resType is null");
        }         
	this.resType = resType;
	return this;
    }

    public PageResourceQuery resTypeLike( String resTypeLike){
        if (resTypeLike == null) {
            throw new RuntimeException("resType is null");
        }
        this.resTypeLike = resTypeLike;
        return this;
    }

    public PageResourceQuery resTypes(List<String> resTypes){
        if (resTypes == null) {
            throw new RuntimeException("resTypes is empty ");
        }
        this.resTypes = resTypes;
        return this;
    }


    public PageResourceQuery resMime(String resMime){
	if (resMime == null) {
	    throw new RuntimeException("resMime is null");
        }         
	this.resMime = resMime;
	return this;
    }

    public PageResourceQuery resMimeLike( String resMimeLike){
        if (resMimeLike == null) {
            throw new RuntimeException("resMime is null");
        }
        this.resMimeLike = resMimeLike;
        return this;
    }

    public PageResourceQuery resMimes(List<String> resMimes){
        if (resMimes == null) {
            throw new RuntimeException("resMimes is empty ");
        }
        this.resMimes = resMimes;
        return this;
    }



    public PageResourceQuery resCrDatetimeGreaterThanOrEqual(Date resCrDatetimeGreaterThanOrEqual){
	if (resCrDatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("resCrDatetime is null");
        }         
	this.resCrDatetimeGreaterThanOrEqual = resCrDatetimeGreaterThanOrEqual;
        return this;
    }

    public PageResourceQuery resCrDatetimeLessThanOrEqual(Date resCrDatetimeLessThanOrEqual){
        if (resCrDatetimeLessThanOrEqual == null) {
            throw new RuntimeException("resCrDatetime is null");
        }
        this.resCrDatetimeLessThanOrEqual = resCrDatetimeLessThanOrEqual;
        return this;
    }




    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("resPath".equals(sortColumn)) {
                orderBy = "E.RES_PATH_" + a_x;
            } 

            if ("resFileName".equals(sortColumn)) {
                orderBy = "E.RES_FILENAME_" + a_x;
            } 

            if ("resName".equals(sortColumn)) {
                orderBy = "E.RES_NAME_" + a_x;
            } 

            if ("resType".equals(sortColumn)) {
                orderBy = "E.RES_TYPE_" + a_x;
            } 

            if ("resMime".equals(sortColumn)) {
                orderBy = "E.RES_MIME_" + a_x;
            } 

            if ("resCrDatetime".equals(sortColumn)) {
                orderBy = "E.RES_CRDATETIME_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("resId", "RES_ID_");
        addColumn("resPath", "RES_PATH_");
        addColumn("resFileName", "RES_FILENAME_");
        addColumn("resName", "RES_NAME_");
        addColumn("resType", "RES_TYPE_");
        addColumn("resMime", "RES_MIME_");
        addColumn("resCrDatetime", "RES_CRDATETIME_");
    }

}