package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class UserSqliteQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String userId;
  	protected String userIdLike;
  	protected List<String> userIds;
  	protected String name;
  	protected String nameLike;
  	protected List<String> names;
  	protected String desc;
  	protected String descLike;
  	protected List<String> descs;
  	protected String sqliteCode;
  	protected String sqliteCodeLike;
  	protected List<String> sqliteCodes;
  	protected String ruleJson;
  	protected String ruleJsonLike;
  	protected List<String> ruleJsons;
  	protected String createBy;
  	protected String createByLike;
  	protected List<String> createBys;
        protected Date createDateGreaterThanOrEqual;
  	protected Date createDateLessThanOrEqual;
  	protected String updateBy;
  	protected String updateByLike;
  	protected List<String> updateBys;
        protected Date updateDateGreaterThanOrEqual;
  	protected Date updateDateLessThanOrEqual;
        protected Date fileDateGreaterThanOrEqual;
  	protected Date fileDateLessThanOrEqual;
  	protected Integer downloadNum;
  	protected Integer downloadNumGreaterThanOrEqual;
  	protected Integer downloadNumLessThanOrEqual;
  	protected List<Integer> downloadNums;
  	protected Integer status;
  	protected Integer statusGreaterThanOrEqual;
  	protected Integer statusLessThanOrEqual;
  	protected List<Integer> statuss;
  	protected String errorMessage;
  	protected String errorMessageLike;
  	protected List<String> errorMessages;

    public UserSqliteQuery() {

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


    public String getDesc(){
        return desc;
    }

    public String getDescLike(){
	    if (descLike != null && descLike.trim().length() > 0) {
		if (!descLike.startsWith("%")) {
		    descLike = "%" + descLike;
		}
		if (!descLike.endsWith("%")) {
		   descLike = descLike + "%";
		}
	    }
	return descLike;
    }

    public List<String> getDescs(){
	return descs;
    }


    public String getSqliteCode(){
        return sqliteCode;
    }

    public String getSqliteCodeLike(){
	    if (sqliteCodeLike != null && sqliteCodeLike.trim().length() > 0) {
		if (!sqliteCodeLike.startsWith("%")) {
		    sqliteCodeLike = "%" + sqliteCodeLike;
		}
		if (!sqliteCodeLike.endsWith("%")) {
		   sqliteCodeLike = sqliteCodeLike + "%";
		}
	    }
	return sqliteCodeLike;
    }

    public List<String> getSqliteCodes(){
	return sqliteCodes;
    }


    public String getRuleJson(){
        return ruleJson;
    }

    public String getRuleJsonLike(){
	    if (ruleJsonLike != null && ruleJsonLike.trim().length() > 0) {
		if (!ruleJsonLike.startsWith("%")) {
		    ruleJsonLike = "%" + ruleJsonLike;
		}
		if (!ruleJsonLike.endsWith("%")) {
		   ruleJsonLike = ruleJsonLike + "%";
		}
	    }
	return ruleJsonLike;
    }

    public List<String> getRuleJsons(){
	return ruleJsons;
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


    public Date getCreateDateGreaterThanOrEqual(){
        return createDateGreaterThanOrEqual;
    }

    public Date getCreateDateLessThanOrEqual(){
	return createDateLessThanOrEqual;
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


    public Date getUpdateDateGreaterThanOrEqual(){
        return updateDateGreaterThanOrEqual;
    }

    public Date getUpdateDateLessThanOrEqual(){
	return updateDateLessThanOrEqual;
    }


    public Date getFileDateGreaterThanOrEqual(){
        return fileDateGreaterThanOrEqual;
    }

    public Date getFileDateLessThanOrEqual(){
	return fileDateLessThanOrEqual;
    }


    public Integer getDownloadNum(){
        return downloadNum;
    }

    public Integer getDownloadNumGreaterThanOrEqual(){
        return downloadNumGreaterThanOrEqual;
    }

    public Integer getDownloadNumLessThanOrEqual(){
	return downloadNumLessThanOrEqual;
    }

    public List<Integer> getDownloadNums(){
	return downloadNums;
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

    public String getErrorMessage(){
        return errorMessage;
    }

    public String getErrorMessageLike(){
	    if (errorMessageLike != null && errorMessageLike.trim().length() > 0) {
		if (!errorMessageLike.startsWith("%")) {
		    errorMessageLike = "%" + errorMessageLike;
		}
		if (!errorMessageLike.endsWith("%")) {
		   errorMessageLike = errorMessageLike + "%";
		}
	    }
	return errorMessageLike;
    }

    public List<String> getErrorMessages(){
	return errorMessages;
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


    public void setName(String name){
        this.name = name;
    }

    public void setNameLike( String nameLike){
	this.nameLike = nameLike;
    }

    public void setNames(List<String> names){
        this.names = names;
    }


    public void setDesc(String desc){
        this.desc = desc;
    }

    public void setDescLike( String descLike){
	this.descLike = descLike;
    }

    public void setDescs(List<String> descs){
        this.descs = descs;
    }


    public void setSqliteCode(String sqliteCode){
        this.sqliteCode = sqliteCode;
    }

    public void setSqliteCodeLike( String sqliteCodeLike){
	this.sqliteCodeLike = sqliteCodeLike;
    }

    public void setSqliteCodes(List<String> sqliteCodes){
        this.sqliteCodes = sqliteCodes;
    }


    public void setRuleJson(String ruleJson){
        this.ruleJson = ruleJson;
    }

    public void setRuleJsonLike( String ruleJsonLike){
	this.ruleJsonLike = ruleJsonLike;
    }

    public void setRuleJsons(List<String> ruleJsons){
        this.ruleJsons = ruleJsons;
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


    public void setCreateDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
        this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
    }

    public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual){
	this.createDateLessThanOrEqual = createDateLessThanOrEqual;
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


    public void setUpdateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual){
        this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
    }

    public void setUpdateDateLessThanOrEqual(Date updateDateLessThanOrEqual){
	this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
    }


    public void setFileDateGreaterThanOrEqual(Date fileDateGreaterThanOrEqual){
        this.fileDateGreaterThanOrEqual = fileDateGreaterThanOrEqual;
    }

    public void setFileDateLessThanOrEqual(Date fileDateLessThanOrEqual){
	this.fileDateLessThanOrEqual = fileDateLessThanOrEqual;
    }


    public void setDownloadNum(Integer downloadNum){
        this.downloadNum = downloadNum;
    }

    public void setDownloadNumGreaterThanOrEqual(Integer downloadNumGreaterThanOrEqual){
        this.downloadNumGreaterThanOrEqual = downloadNumGreaterThanOrEqual;
    }

    public void setDownloadNumLessThanOrEqual(Integer downloadNumLessThanOrEqual){
	this.downloadNumLessThanOrEqual = downloadNumLessThanOrEqual;
    }

    public void setDownloadNums(List<Integer> downloadNums){
        this.downloadNums = downloadNums;
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


    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public void setErrorMessageLike( String errorMessageLike){
	this.errorMessageLike = errorMessageLike;
    }

    public void setErrorMessages(List<String> errorMessages){
        this.errorMessages = errorMessages;
    }




    public UserSqliteQuery userId(String userId){
	if (userId == null) {
	    throw new RuntimeException("userId is null");
        }         
	this.userId = userId;
	return this;
    }

    public UserSqliteQuery userIdLike( String userIdLike){
        if (userIdLike == null) {
            throw new RuntimeException("userId is null");
        }
        this.userIdLike = userIdLike;
        return this;
    }

    public UserSqliteQuery userIds(List<String> userIds){
        if (userIds == null) {
            throw new RuntimeException("userIds is empty ");
        }
        this.userIds = userIds;
        return this;
    }


    public UserSqliteQuery name(String name){
	if (name == null) {
	    throw new RuntimeException("name is null");
        }         
	this.name = name;
	return this;
    }

    public UserSqliteQuery nameLike( String nameLike){
        if (nameLike == null) {
            throw new RuntimeException("name is null");
        }
        this.nameLike = nameLike;
        return this;
    }

    public UserSqliteQuery names(List<String> names){
        if (names == null) {
            throw new RuntimeException("names is empty ");
        }
        this.names = names;
        return this;
    }


    public UserSqliteQuery desc(String desc){
	if (desc == null) {
	    throw new RuntimeException("desc is null");
        }         
	this.desc = desc;
	return this;
    }

    public UserSqliteQuery descLike( String descLike){
        if (descLike == null) {
            throw new RuntimeException("desc is null");
        }
        this.descLike = descLike;
        return this;
    }

    public UserSqliteQuery descs(List<String> descs){
        if (descs == null) {
            throw new RuntimeException("descs is empty ");
        }
        this.descs = descs;
        return this;
    }


    public UserSqliteQuery sqliteCode(String sqliteCode){
	if (sqliteCode == null) {
	    throw new RuntimeException("sqliteCode is null");
        }         
	this.sqliteCode = sqliteCode;
	return this;
    }

    public UserSqliteQuery sqliteCodeLike( String sqliteCodeLike){
        if (sqliteCodeLike == null) {
            throw new RuntimeException("sqliteCode is null");
        }
        this.sqliteCodeLike = sqliteCodeLike;
        return this;
    }

    public UserSqliteQuery sqliteCodes(List<String> sqliteCodes){
        if (sqliteCodes == null) {
            throw new RuntimeException("sqliteCodes is empty ");
        }
        this.sqliteCodes = sqliteCodes;
        return this;
    }


    public UserSqliteQuery ruleJson(String ruleJson){
	if (ruleJson == null) {
	    throw new RuntimeException("ruleJson is null");
        }         
	this.ruleJson = ruleJson;
	return this;
    }

    public UserSqliteQuery ruleJsonLike( String ruleJsonLike){
        if (ruleJsonLike == null) {
            throw new RuntimeException("ruleJson is null");
        }
        this.ruleJsonLike = ruleJsonLike;
        return this;
    }

    public UserSqliteQuery ruleJsons(List<String> ruleJsons){
        if (ruleJsons == null) {
            throw new RuntimeException("ruleJsons is empty ");
        }
        this.ruleJsons = ruleJsons;
        return this;
    }


    public UserSqliteQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public UserSqliteQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public UserSqliteQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public UserSqliteQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
	if (createDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDate is null");
        }         
	this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
        return this;
    }

    public UserSqliteQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual){
        if (createDateLessThanOrEqual == null) {
            throw new RuntimeException("createDate is null");
        }
        this.createDateLessThanOrEqual = createDateLessThanOrEqual;
        return this;
    }



    public UserSqliteQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public UserSqliteQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public UserSqliteQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public UserSqliteQuery updateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual){
	if (updateDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateDate is null");
        }         
	this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
        return this;
    }

    public UserSqliteQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual){
        if (updateDateLessThanOrEqual == null) {
            throw new RuntimeException("updateDate is null");
        }
        this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
        return this;
    }




    public UserSqliteQuery fileDateGreaterThanOrEqual(Date fileDateGreaterThanOrEqual){
	if (fileDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("fileDate is null");
        }         
	this.fileDateGreaterThanOrEqual = fileDateGreaterThanOrEqual;
        return this;
    }

    public UserSqliteQuery fileDateLessThanOrEqual(Date fileDateLessThanOrEqual){
        if (fileDateLessThanOrEqual == null) {
            throw new RuntimeException("fileDate is null");
        }
        this.fileDateLessThanOrEqual = fileDateLessThanOrEqual;
        return this;
    }



    public UserSqliteQuery downloadNum(Integer downloadNum){
	if (downloadNum == null) {
            throw new RuntimeException("downloadNum is null");
        }         
	this.downloadNum = downloadNum;
	return this;
    }

    public UserSqliteQuery downloadNumGreaterThanOrEqual(Integer downloadNumGreaterThanOrEqual){
	if (downloadNumGreaterThanOrEqual == null) {
	    throw new RuntimeException("downloadNum is null");
        }         
	this.downloadNumGreaterThanOrEqual = downloadNumGreaterThanOrEqual;
        return this;
    }

    public UserSqliteQuery downloadNumLessThanOrEqual(Integer downloadNumLessThanOrEqual){
        if (downloadNumLessThanOrEqual == null) {
            throw new RuntimeException("downloadNum is null");
        }
        this.downloadNumLessThanOrEqual = downloadNumLessThanOrEqual;
        return this;
    }

    public UserSqliteQuery downloadNums(List<Integer> downloadNums){
        if (downloadNums == null) {
            throw new RuntimeException("downloadNums is empty ");
        }
        this.downloadNums = downloadNums;
        return this;
    }


    public UserSqliteQuery status(Integer status){
	if (status == null) {
            throw new RuntimeException("status is null");
        }         
	this.status = status;
	return this;
    }

    public UserSqliteQuery statusGreaterThanOrEqual(Integer statusGreaterThanOrEqual){
	if (statusGreaterThanOrEqual == null) {
	    throw new RuntimeException("status is null");
        }         
	this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
        return this;
    }

    public UserSqliteQuery statusLessThanOrEqual(Integer statusLessThanOrEqual){
        if (statusLessThanOrEqual == null) {
            throw new RuntimeException("status is null");
        }
        this.statusLessThanOrEqual = statusLessThanOrEqual;
        return this;
    }

    public UserSqliteQuery statuss(List<Integer> statuss){
        if (statuss == null) {
            throw new RuntimeException("statuss is empty ");
        }
        this.statuss = statuss;
        return this;
    }


    public UserSqliteQuery errorMessage(String errorMessage){
	if (errorMessage == null) {
	    throw new RuntimeException("errorMessage is null");
        }         
	this.errorMessage = errorMessage;
	return this;
    }

    public UserSqliteQuery errorMessageLike( String errorMessageLike){
        if (errorMessageLike == null) {
            throw new RuntimeException("errorMessage is null");
        }
        this.errorMessageLike = errorMessageLike;
        return this;
    }

    public UserSqliteQuery errorMessages(List<String> errorMessages){
        if (errorMessages == null) {
            throw new RuntimeException("errorMessages is empty ");
        }
        this.errorMessages = errorMessages;
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

            if ("name".equals(sortColumn)) {
                orderBy = "E.NAME_" + a_x;
            } 

            if ("desc".equals(sortColumn)) {
                orderBy = "E.DESC_" + a_x;
            } 

            if ("sqliteCode".equals(sortColumn)) {
                orderBy = "E.SQLITECODE_" + a_x;
            } 

            if ("ruleJson".equals(sortColumn)) {
                orderBy = "E.RULEJSON_" + a_x;
            } 

            if ("createBy".equals(sortColumn)) {
                orderBy = "E.CREATEBY_" + a_x;
            } 

            if ("createDate".equals(sortColumn)) {
                orderBy = "E.CREATEDATE_" + a_x;
            } 

            if ("updateBy".equals(sortColumn)) {
                orderBy = "E.UPDATEBY_" + a_x;
            } 

            if ("updateDate".equals(sortColumn)) {
                orderBy = "E.UPDATEDATE_" + a_x;
            } 

            if ("fileDate".equals(sortColumn)) {
                orderBy = "E.FILEDATE_" + a_x;
            } 

            if ("downloadNum".equals(sortColumn)) {
                orderBy = "E.DOWNLOADNUM_" + a_x;
            } 

            if ("status".equals(sortColumn)) {
                orderBy = "E.STATUS_" + a_x;
            } 

            if ("errorMessage".equals(sortColumn)) {
                orderBy = "E.ERROR_MESSAGE_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("userId", "USERID_");
        addColumn("name", "NAME_");
        addColumn("desc", "DESC_");
        addColumn("sqliteCode", "SQLITECODE_");
        addColumn("ruleJson", "RULEJSON_");
        addColumn("createBy", "CREATEBY_");
        addColumn("createDate", "CREATEDATE_");
        addColumn("updateBy", "UPDATEBY_");
        addColumn("updateDate", "UPDATEDATE_");
        addColumn("fileDate", "FILEDATE_");
        addColumn("downloadNum", "DOWNLOADNUM_");
        addColumn("status", "STATUS_");
        addColumn("errorMessage", "ERROR_MESSAGE_");
    }

}