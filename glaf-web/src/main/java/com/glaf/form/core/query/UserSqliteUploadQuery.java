package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class UserSqliteUploadQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String userId;
  	protected String userIdLike;
  	protected List<String> userIds;
  	protected String fileName;
  	protected String fileNameLike;
  	protected List<String> fileNames;
  	protected String filePath;
  	protected String filePathLike;
  	protected List<String> filePaths;
  	protected String logfilePath;
  	protected String logfilePathLike;
  	protected List<String> logfilePaths;
  	protected Integer status;
  	protected Integer statusGreaterThanOrEqual;
  	protected Integer statusLessThanOrEqual;
  	protected List<Integer> statuss;
        protected Date uploadDateGreaterThanOrEqual;
  	protected Date uploadDateLessThanOrEqual;

    public UserSqliteUploadQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
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


    public String getFileName(){
        return fileName;
    }

    public String getFileNameLike(){
	    if (fileNameLike != null && fileNameLike.trim().length() > 0) {
		if (!fileNameLike.startsWith("%")) {
		    fileNameLike = "%" + fileNameLike;
		}
		if (!fileNameLike.endsWith("%")) {
		   fileNameLike = fileNameLike + "%";
		}
	    }
	return fileNameLike;
    }

    public List<String> getFileNames(){
	return fileNames;
    }


    public String getFilePath(){
        return filePath;
    }

    public String getFilePathLike(){
	    if (filePathLike != null && filePathLike.trim().length() > 0) {
		if (!filePathLike.startsWith("%")) {
		    filePathLike = "%" + filePathLike;
		}
		if (!filePathLike.endsWith("%")) {
		   filePathLike = filePathLike + "%";
		}
	    }
	return filePathLike;
    }

    public List<String> getFilePaths(){
	return filePaths;
    }


    public String getLogfilePath(){
        return logfilePath;
    }

    public String getLogfilePathLike(){
	    if (logfilePathLike != null && logfilePathLike.trim().length() > 0) {
		if (!logfilePathLike.startsWith("%")) {
		    logfilePathLike = "%" + logfilePathLike;
		}
		if (!logfilePathLike.endsWith("%")) {
		   logfilePathLike = logfilePathLike + "%";
		}
	    }
	return logfilePathLike;
    }

    public List<String> getLogfilePaths(){
	return logfilePaths;
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

    public Date getUploadDateGreaterThanOrEqual(){
        return uploadDateGreaterThanOrEqual;
    }

    public Date getUploadDateLessThanOrEqual(){
	return uploadDateLessThanOrEqual;
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


    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public void setFileNameLike( String fileNameLike){
	this.fileNameLike = fileNameLike;
    }

    public void setFileNames(List<String> fileNames){
        this.fileNames = fileNames;
    }


    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public void setFilePathLike( String filePathLike){
	this.filePathLike = filePathLike;
    }

    public void setFilePaths(List<String> filePaths){
        this.filePaths = filePaths;
    }


    public void setLogfilePath(String logfilePath){
        this.logfilePath = logfilePath;
    }

    public void setLogfilePathLike( String logfilePathLike){
	this.logfilePathLike = logfilePathLike;
    }

    public void setLogfilePaths(List<String> logfilePaths){
        this.logfilePaths = logfilePaths;
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


    public void setUploadDateGreaterThanOrEqual(Date uploadDateGreaterThanOrEqual){
        this.uploadDateGreaterThanOrEqual = uploadDateGreaterThanOrEqual;
    }

    public void setUploadDateLessThanOrEqual(Date uploadDateLessThanOrEqual){
	this.uploadDateLessThanOrEqual = uploadDateLessThanOrEqual;
    }




    public UserSqliteUploadQuery userId(String userId){
	if (userId == null) {
	    throw new RuntimeException("userId is null");
        }         
	this.userId = userId;
	return this;
    }

    public UserSqliteUploadQuery userIdLike( String userIdLike){
        if (userIdLike == null) {
            throw new RuntimeException("userId is null");
        }
        this.userIdLike = userIdLike;
        return this;
    }

    public UserSqliteUploadQuery userIds(List<String> userIds){
        if (userIds == null) {
            throw new RuntimeException("userIds is empty ");
        }
        this.userIds = userIds;
        return this;
    }


    public UserSqliteUploadQuery fileName(String fileName){
	if (fileName == null) {
	    throw new RuntimeException("fileName is null");
        }         
	this.fileName = fileName;
	return this;
    }

    public UserSqliteUploadQuery fileNameLike( String fileNameLike){
        if (fileNameLike == null) {
            throw new RuntimeException("fileName is null");
        }
        this.fileNameLike = fileNameLike;
        return this;
    }

    public UserSqliteUploadQuery fileNames(List<String> fileNames){
        if (fileNames == null) {
            throw new RuntimeException("fileNames is empty ");
        }
        this.fileNames = fileNames;
        return this;
    }


    public UserSqliteUploadQuery filePath(String filePath){
	if (filePath == null) {
	    throw new RuntimeException("filePath is null");
        }         
	this.filePath = filePath;
	return this;
    }

    public UserSqliteUploadQuery filePathLike( String filePathLike){
        if (filePathLike == null) {
            throw new RuntimeException("filePath is null");
        }
        this.filePathLike = filePathLike;
        return this;
    }

    public UserSqliteUploadQuery filePaths(List<String> filePaths){
        if (filePaths == null) {
            throw new RuntimeException("filePaths is empty ");
        }
        this.filePaths = filePaths;
        return this;
    }


    public UserSqliteUploadQuery logfilePath(String logfilePath){
	if (logfilePath == null) {
	    throw new RuntimeException("logfilePath is null");
        }         
	this.logfilePath = logfilePath;
	return this;
    }

    public UserSqliteUploadQuery logfilePathLike( String logfilePathLike){
        if (logfilePathLike == null) {
            throw new RuntimeException("logfilePath is null");
        }
        this.logfilePathLike = logfilePathLike;
        return this;
    }

    public UserSqliteUploadQuery logfilePaths(List<String> logfilePaths){
        if (logfilePaths == null) {
            throw new RuntimeException("logfilePaths is empty ");
        }
        this.logfilePaths = logfilePaths;
        return this;
    }


    public UserSqliteUploadQuery status(Integer status){
	if (status == null) {
            throw new RuntimeException("status is null");
        }         
	this.status = status;
	return this;
    }

    public UserSqliteUploadQuery statusGreaterThanOrEqual(Integer statusGreaterThanOrEqual){
	if (statusGreaterThanOrEqual == null) {
	    throw new RuntimeException("status is null");
        }         
	this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
        return this;
    }

    public UserSqliteUploadQuery statusLessThanOrEqual(Integer statusLessThanOrEqual){
        if (statusLessThanOrEqual == null) {
            throw new RuntimeException("status is null");
        }
        this.statusLessThanOrEqual = statusLessThanOrEqual;
        return this;
    }

    public UserSqliteUploadQuery statuss(List<Integer> statuss){
        if (statuss == null) {
            throw new RuntimeException("statuss is empty ");
        }
        this.statuss = statuss;
        return this;
    }



    public UserSqliteUploadQuery uploadDateGreaterThanOrEqual(Date uploadDateGreaterThanOrEqual){
	if (uploadDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("uploadDate is null");
        }         
	this.uploadDateGreaterThanOrEqual = uploadDateGreaterThanOrEqual;
        return this;
    }

    public UserSqliteUploadQuery uploadDateLessThanOrEqual(Date uploadDateLessThanOrEqual){
        if (uploadDateLessThanOrEqual == null) {
            throw new RuntimeException("uploadDate is null");
        }
        this.uploadDateLessThanOrEqual = uploadDateLessThanOrEqual;
        return this;
    }




    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("userId".equals(sortColumn)) {
                orderBy = "E.USERID_" + a_x;
            } 

            if ("fileName".equals(sortColumn)) {
                orderBy = "E.FILENAME_" + a_x;
            } 

            if ("filePath".equals(sortColumn)) {
                orderBy = "E.FILEPATH_" + a_x;
            } 

            if ("logfilePath".equals(sortColumn)) {
                orderBy = "E.LOGFILEPATH_" + a_x;
            } 

            if ("status".equals(sortColumn)) {
                orderBy = "E.STATUS_" + a_x;
            } 

            if ("uploadDate".equals(sortColumn)) {
                orderBy = "E.UPLOADDATE_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("userId", "USERID_");
        addColumn("fileName", "FILENAME_");
        addColumn("filePath", "FILEPATH_");
        addColumn("logfilePath", "LOGFILEPATH_");
        addColumn("status", "STATUS_");
        addColumn("uploadDate", "UPLOADDATE_");
    }

}