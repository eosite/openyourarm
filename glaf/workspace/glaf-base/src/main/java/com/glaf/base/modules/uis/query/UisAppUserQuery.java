package com.glaf.base.modules.uis.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class UisAppUserQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String userId;
  	protected String userIdLike;
  	protected List<String> userIds;
  	protected String appId;
  	protected String appIdLike;
  	protected List<String> appIds;
  	protected String userName;
  	protected String userNameLike;
  	protected List<String> userNames;
  	protected String email;
  	protected String emailLike;
  	protected List<String> emails;
  	protected String tel;
  	protected String telLike;
  	protected List<String> tels;
  	protected String mobile;
  	protected String mobileLike;
  	protected List<String> mobiles;
  	protected Integer age;
  	protected Integer ageGreaterThanOrEqual;
  	protected Integer ageLessThanOrEqual;
  	protected List<Integer> ages;
  	protected Integer sex;
  	protected Integer sexGreaterThanOrEqual;
  	protected Integer sexLessThanOrEqual;
  	protected List<Integer> sexs;
  	protected String qq;
  	protected String qqLike;
  	protected List<String> qqs;
  	protected String weq;
  	protected String weqLike;
  	protected List<String> weqs;
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

    public UisAppUserQuery() {

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


    public String getUserName(){
        return userName;
    }

    public String getUserNameLike(){
	    if (userNameLike != null && userNameLike.trim().length() > 0) {
		if (!userNameLike.startsWith("%")) {
		    userNameLike = "%" + userNameLike;
		}
		if (!userNameLike.endsWith("%")) {
		   userNameLike = userNameLike + "%";
		}
	    }
	return userNameLike;
    }

    public List<String> getUserNames(){
	return userNames;
    }


    public String getEmail(){
        return email;
    }

    public String getEmailLike(){
	    if (emailLike != null && emailLike.trim().length() > 0) {
		if (!emailLike.startsWith("%")) {
		    emailLike = "%" + emailLike;
		}
		if (!emailLike.endsWith("%")) {
		   emailLike = emailLike + "%";
		}
	    }
	return emailLike;
    }

    public List<String> getEmails(){
	return emails;
    }


    public String getTel(){
        return tel;
    }

    public String getTelLike(){
	    if (telLike != null && telLike.trim().length() > 0) {
		if (!telLike.startsWith("%")) {
		    telLike = "%" + telLike;
		}
		if (!telLike.endsWith("%")) {
		   telLike = telLike + "%";
		}
	    }
	return telLike;
    }

    public List<String> getTels(){
	return tels;
    }


    public String getMobile(){
        return mobile;
    }

    public String getMobileLike(){
	    if (mobileLike != null && mobileLike.trim().length() > 0) {
		if (!mobileLike.startsWith("%")) {
		    mobileLike = "%" + mobileLike;
		}
		if (!mobileLike.endsWith("%")) {
		   mobileLike = mobileLike + "%";
		}
	    }
	return mobileLike;
    }

    public List<String> getMobiles(){
	return mobiles;
    }


    public Integer getAge(){
        return age;
    }

    public Integer getAgeGreaterThanOrEqual(){
        return ageGreaterThanOrEqual;
    }

    public Integer getAgeLessThanOrEqual(){
	return ageLessThanOrEqual;
    }

    public List<Integer> getAges(){
	return ages;
    }

    public Integer getSex(){
        return sex;
    }

    public Integer getSexGreaterThanOrEqual(){
        return sexGreaterThanOrEqual;
    }

    public Integer getSexLessThanOrEqual(){
	return sexLessThanOrEqual;
    }

    public List<Integer> getSexs(){
	return sexs;
    }

    public String getQq(){
        return qq;
    }

    public String getQqLike(){
	    if (qqLike != null && qqLike.trim().length() > 0) {
		if (!qqLike.startsWith("%")) {
		    qqLike = "%" + qqLike;
		}
		if (!qqLike.endsWith("%")) {
		   qqLike = qqLike + "%";
		}
	    }
	return qqLike;
    }

    public List<String> getQqs(){
	return qqs;
    }


    public String getWeq(){
        return weq;
    }

    public String getWeqLike(){
	    if (weqLike != null && weqLike.trim().length() > 0) {
		if (!weqLike.startsWith("%")) {
		    weqLike = "%" + weqLike;
		}
		if (!weqLike.endsWith("%")) {
		   weqLike = weqLike + "%";
		}
	    }
	return weqLike;
    }

    public List<String> getWeqs(){
	return weqs;
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


    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setUserNameLike( String userNameLike){
	this.userNameLike = userNameLike;
    }

    public void setUserNames(List<String> userNames){
        this.userNames = userNames;
    }


    public void setEmail(String email){
        this.email = email;
    }

    public void setEmailLike( String emailLike){
	this.emailLike = emailLike;
    }

    public void setEmails(List<String> emails){
        this.emails = emails;
    }


    public void setTel(String tel){
        this.tel = tel;
    }

    public void setTelLike( String telLike){
	this.telLike = telLike;
    }

    public void setTels(List<String> tels){
        this.tels = tels;
    }


    public void setMobile(String mobile){
        this.mobile = mobile;
    }

    public void setMobileLike( String mobileLike){
	this.mobileLike = mobileLike;
    }

    public void setMobiles(List<String> mobiles){
        this.mobiles = mobiles;
    }


    public void setAge(Integer age){
        this.age = age;
    }

    public void setAgeGreaterThanOrEqual(Integer ageGreaterThanOrEqual){
        this.ageGreaterThanOrEqual = ageGreaterThanOrEqual;
    }

    public void setAgeLessThanOrEqual(Integer ageLessThanOrEqual){
	this.ageLessThanOrEqual = ageLessThanOrEqual;
    }

    public void setAges(List<Integer> ages){
        this.ages = ages;
    }


    public void setSex(Integer sex){
        this.sex = sex;
    }

    public void setSexGreaterThanOrEqual(Integer sexGreaterThanOrEqual){
        this.sexGreaterThanOrEqual = sexGreaterThanOrEqual;
    }

    public void setSexLessThanOrEqual(Integer sexLessThanOrEqual){
	this.sexLessThanOrEqual = sexLessThanOrEqual;
    }

    public void setSexs(List<Integer> sexs){
        this.sexs = sexs;
    }


    public void setQq(String qq){
        this.qq = qq;
    }

    public void setQqLike( String qqLike){
	this.qqLike = qqLike;
    }

    public void setQqs(List<String> qqs){
        this.qqs = qqs;
    }


    public void setWeq(String weq){
        this.weq = weq;
    }

    public void setWeqLike( String weqLike){
	this.weqLike = weqLike;
    }

    public void setWeqs(List<String> weqs){
        this.weqs = weqs;
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




    public UisAppUserQuery userId(String userId){
	if (userId == null) {
	    throw new RuntimeException("userId is null");
        }         
	this.userId = userId;
	return this;
    }

    public UisAppUserQuery userIdLike( String userIdLike){
        if (userIdLike == null) {
            throw new RuntimeException("userId is null");
        }
        this.userIdLike = userIdLike;
        return this;
    }

    public UisAppUserQuery userIds(List<String> userIds){
        if (userIds == null) {
            throw new RuntimeException("userIds is empty ");
        }
        this.userIds = userIds;
        return this;
    }


    public UisAppUserQuery appId(String appId){
	if (appId == null) {
	    throw new RuntimeException("appId is null");
        }         
	this.appId = appId;
	return this;
    }

    public UisAppUserQuery appIdLike( String appIdLike){
        if (appIdLike == null) {
            throw new RuntimeException("appId is null");
        }
        this.appIdLike = appIdLike;
        return this;
    }

    public UisAppUserQuery appIds(List<String> appIds){
        if (appIds == null) {
            throw new RuntimeException("appIds is empty ");
        }
        this.appIds = appIds;
        return this;
    }


    public UisAppUserQuery userName(String userName){
	if (userName == null) {
	    throw new RuntimeException("userName is null");
        }         
	this.userName = userName;
	return this;
    }

    public UisAppUserQuery userNameLike( String userNameLike){
        if (userNameLike == null) {
            throw new RuntimeException("userName is null");
        }
        this.userNameLike = userNameLike;
        return this;
    }

    public UisAppUserQuery userNames(List<String> userNames){
        if (userNames == null) {
            throw new RuntimeException("userNames is empty ");
        }
        this.userNames = userNames;
        return this;
    }


    public UisAppUserQuery email(String email){
	if (email == null) {
	    throw new RuntimeException("email is null");
        }         
	this.email = email;
	return this;
    }

    public UisAppUserQuery emailLike( String emailLike){
        if (emailLike == null) {
            throw new RuntimeException("email is null");
        }
        this.emailLike = emailLike;
        return this;
    }

    public UisAppUserQuery emails(List<String> emails){
        if (emails == null) {
            throw new RuntimeException("emails is empty ");
        }
        this.emails = emails;
        return this;
    }


    public UisAppUserQuery tel(String tel){
	if (tel == null) {
	    throw new RuntimeException("tel is null");
        }         
	this.tel = tel;
	return this;
    }

    public UisAppUserQuery telLike( String telLike){
        if (telLike == null) {
            throw new RuntimeException("tel is null");
        }
        this.telLike = telLike;
        return this;
    }

    public UisAppUserQuery tels(List<String> tels){
        if (tels == null) {
            throw new RuntimeException("tels is empty ");
        }
        this.tels = tels;
        return this;
    }


    public UisAppUserQuery mobile(String mobile){
	if (mobile == null) {
	    throw new RuntimeException("mobile is null");
        }         
	this.mobile = mobile;
	return this;
    }

    public UisAppUserQuery mobileLike( String mobileLike){
        if (mobileLike == null) {
            throw new RuntimeException("mobile is null");
        }
        this.mobileLike = mobileLike;
        return this;
    }

    public UisAppUserQuery mobiles(List<String> mobiles){
        if (mobiles == null) {
            throw new RuntimeException("mobiles is empty ");
        }
        this.mobiles = mobiles;
        return this;
    }


    public UisAppUserQuery age(Integer age){
	if (age == null) {
            throw new RuntimeException("age is null");
        }         
	this.age = age;
	return this;
    }

    public UisAppUserQuery ageGreaterThanOrEqual(Integer ageGreaterThanOrEqual){
	if (ageGreaterThanOrEqual == null) {
	    throw new RuntimeException("age is null");
        }         
	this.ageGreaterThanOrEqual = ageGreaterThanOrEqual;
        return this;
    }

    public UisAppUserQuery ageLessThanOrEqual(Integer ageLessThanOrEqual){
        if (ageLessThanOrEqual == null) {
            throw new RuntimeException("age is null");
        }
        this.ageLessThanOrEqual = ageLessThanOrEqual;
        return this;
    }

    public UisAppUserQuery ages(List<Integer> ages){
        if (ages == null) {
            throw new RuntimeException("ages is empty ");
        }
        this.ages = ages;
        return this;
    }


    public UisAppUserQuery sex(Integer sex){
	if (sex == null) {
            throw new RuntimeException("sex is null");
        }         
	this.sex = sex;
	return this;
    }

    public UisAppUserQuery sexGreaterThanOrEqual(Integer sexGreaterThanOrEqual){
	if (sexGreaterThanOrEqual == null) {
	    throw new RuntimeException("sex is null");
        }         
	this.sexGreaterThanOrEqual = sexGreaterThanOrEqual;
        return this;
    }

    public UisAppUserQuery sexLessThanOrEqual(Integer sexLessThanOrEqual){
        if (sexLessThanOrEqual == null) {
            throw new RuntimeException("sex is null");
        }
        this.sexLessThanOrEqual = sexLessThanOrEqual;
        return this;
    }

    public UisAppUserQuery sexs(List<Integer> sexs){
        if (sexs == null) {
            throw new RuntimeException("sexs is empty ");
        }
        this.sexs = sexs;
        return this;
    }


    public UisAppUserQuery qq(String qq){
	if (qq == null) {
	    throw new RuntimeException("qq is null");
        }         
	this.qq = qq;
	return this;
    }

    public UisAppUserQuery qqLike( String qqLike){
        if (qqLike == null) {
            throw new RuntimeException("qq is null");
        }
        this.qqLike = qqLike;
        return this;
    }

    public UisAppUserQuery qqs(List<String> qqs){
        if (qqs == null) {
            throw new RuntimeException("qqs is empty ");
        }
        this.qqs = qqs;
        return this;
    }


    public UisAppUserQuery weq(String weq){
	if (weq == null) {
	    throw new RuntimeException("weq is null");
        }         
	this.weq = weq;
	return this;
    }

    public UisAppUserQuery weqLike( String weqLike){
        if (weqLike == null) {
            throw new RuntimeException("weq is null");
        }
        this.weqLike = weqLike;
        return this;
    }

    public UisAppUserQuery weqs(List<String> weqs){
        if (weqs == null) {
            throw new RuntimeException("weqs is empty ");
        }
        this.weqs = weqs;
        return this;
    }


    public UisAppUserQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public UisAppUserQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public UisAppUserQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public UisAppUserQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
	if (createTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime is null");
        }         
	this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
        return this;
    }

    public UisAppUserQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
        if (createTimeLessThanOrEqual == null) {
            throw new RuntimeException("createTime is null");
        }
        this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
        return this;
    }



    public UisAppUserQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public UisAppUserQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public UisAppUserQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public UisAppUserQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
	if (updateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime is null");
        }         
	this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
        return this;
    }

    public UisAppUserQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
        if (updateTimeLessThanOrEqual == null) {
            throw new RuntimeException("updateTime is null");
        }
        this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
        return this;
    }



    public UisAppUserQuery deleteFlag(Integer deleteFlag){
	if (deleteFlag == null) {
            throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlag = deleteFlag;
	return this;
    }

    public UisAppUserQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
	if (deleteFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
        return this;
    }

    public UisAppUserQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
        if (deleteFlagLessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag is null");
        }
        this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
        return this;
    }

    public UisAppUserQuery deleteFlags(List<Integer> deleteFlags){
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

            if ("userId".equals(sortColumn)) {
                orderBy = "E.USER_ID_" + a_x;
            } 

            if ("appId".equals(sortColumn)) {
                orderBy = "E.APP_ID_" + a_x;
            } 

            if ("userName".equals(sortColumn)) {
                orderBy = "E.USER_NAME_" + a_x;
            } 

            if ("email".equals(sortColumn)) {
                orderBy = "E.EMAIL_" + a_x;
            } 

            if ("tel".equals(sortColumn)) {
                orderBy = "E.TEL_" + a_x;
            } 

            if ("mobile".equals(sortColumn)) {
                orderBy = "E.MOBILE_" + a_x;
            } 

            if ("age".equals(sortColumn)) {
                orderBy = "E.AGE_" + a_x;
            } 

            if ("sex".equals(sortColumn)) {
                orderBy = "E.SEX_" + a_x;
            } 

            if ("qq".equals(sortColumn)) {
                orderBy = "E.QQ_" + a_x;
            } 

            if ("weq".equals(sortColumn)) {
                orderBy = "E.WEQ_" + a_x;
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
        addColumn("id", "ID_");
        addColumn("userId", "USER_ID_");
        addColumn("appId", "APP_ID_");
        addColumn("userName", "USER_NAME_");
        addColumn("email", "EMAIL_");
        addColumn("tel", "TEL_");
        addColumn("mobile", "MOBILE_");
        addColumn("age", "AGE_");
        addColumn("sex", "SEX_");
        addColumn("qq", "QQ_");
        addColumn("weq", "WEQ_");
        addColumn("createBy", "CREATEBY_");
        addColumn("createTime", "CREATETIME_");
        addColumn("updateBy", "UPDATEBY_");
        addColumn("updateTime", "UPDATETIME_");
        addColumn("deleteFlag", "DELETE_FLAG_");
    }

}