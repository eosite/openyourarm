package com.glaf.monitor.server.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class MonitorProcUserQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String procId;
  	protected String procIdLike;
  	protected List<String> procIds;
  	protected String role;
  	protected String roleLike;
  	protected List<String> roles;
  	protected String username;
  	protected String usernameLike;
  	protected List<String> usernames;
  	protected String tel;
  	protected String telLike;
  	protected List<String> tels;
  	protected String phone;
  	protected String phoneLike;
  	protected List<String> phones;
  	protected String email;
  	protected String emailLike;
  	protected List<String> emails;
  	protected String createby;
  	protected String createbyLike;
  	protected List<String> createbys;
        protected Date createtimeGreaterThanOrEqual;
  	protected Date createtimeLessThanOrEqual;
  	protected String updateby;
  	protected String updatebyLike;
  	protected List<String> updatebys;
        protected Date updatetimeGreaterThanOrEqual;
  	protected Date updatetimeLessThanOrEqual;
  	protected Integer deleteFlag;
  	protected Integer deleteFlagGreaterThanOrEqual;
  	protected Integer deleteFlagLessThanOrEqual;
  	protected List<Integer> deleteFlags;

    public MonitorProcUserQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getProcId(){
        return procId;
    }

    public String getProcIdLike(){
	    if (procIdLike != null && procIdLike.trim().length() > 0) {
		if (!procIdLike.startsWith("%")) {
		    procIdLike = "%" + procIdLike;
		}
		if (!procIdLike.endsWith("%")) {
		   procIdLike = procIdLike + "%";
		}
	    }
	return procIdLike;
    }

    public List<String> getProcIds(){
	return procIds;
    }


    public String getRole(){
        return role;
    }

    public String getRoleLike(){
	    if (roleLike != null && roleLike.trim().length() > 0) {
		if (!roleLike.startsWith("%")) {
		    roleLike = "%" + roleLike;
		}
		if (!roleLike.endsWith("%")) {
		   roleLike = roleLike + "%";
		}
	    }
	return roleLike;
    }

    public List<String> getRoles(){
	return roles;
    }


    public String getUsername(){
        return username;
    }

    public String getUsernameLike(){
	    if (usernameLike != null && usernameLike.trim().length() > 0) {
		if (!usernameLike.startsWith("%")) {
		    usernameLike = "%" + usernameLike;
		}
		if (!usernameLike.endsWith("%")) {
		   usernameLike = usernameLike + "%";
		}
	    }
	return usernameLike;
    }

    public List<String> getUsernames(){
	return usernames;
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


    public String getPhone(){
        return phone;
    }

    public String getPhoneLike(){
	    if (phoneLike != null && phoneLike.trim().length() > 0) {
		if (!phoneLike.startsWith("%")) {
		    phoneLike = "%" + phoneLike;
		}
		if (!phoneLike.endsWith("%")) {
		   phoneLike = phoneLike + "%";
		}
	    }
	return phoneLike;
    }

    public List<String> getPhones(){
	return phones;
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


    public String getCreateby(){
        return createby;
    }

    public String getCreatebyLike(){
	    if (createbyLike != null && createbyLike.trim().length() > 0) {
		if (!createbyLike.startsWith("%")) {
		    createbyLike = "%" + createbyLike;
		}
		if (!createbyLike.endsWith("%")) {
		   createbyLike = createbyLike + "%";
		}
	    }
	return createbyLike;
    }

    public List<String> getCreatebys(){
	return createbys;
    }


    public Date getCreatetimeGreaterThanOrEqual(){
        return createtimeGreaterThanOrEqual;
    }

    public Date getCreatetimeLessThanOrEqual(){
	return createtimeLessThanOrEqual;
    }


    public String getUpdateby(){
        return updateby;
    }

    public String getUpdatebyLike(){
	    if (updatebyLike != null && updatebyLike.trim().length() > 0) {
		if (!updatebyLike.startsWith("%")) {
		    updatebyLike = "%" + updatebyLike;
		}
		if (!updatebyLike.endsWith("%")) {
		   updatebyLike = updatebyLike + "%";
		}
	    }
	return updatebyLike;
    }

    public List<String> getUpdatebys(){
	return updatebys;
    }


    public Date getUpdatetimeGreaterThanOrEqual(){
        return updatetimeGreaterThanOrEqual;
    }

    public Date getUpdatetimeLessThanOrEqual(){
	return updatetimeLessThanOrEqual;
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

 

    public void setProcId(String procId){
        this.procId = procId;
    }

    public void setProcIdLike( String procIdLike){
	this.procIdLike = procIdLike;
    }

    public void setProcIds(List<String> procIds){
        this.procIds = procIds;
    }


    public void setRole(String role){
        this.role = role;
    }

    public void setRoleLike( String roleLike){
	this.roleLike = roleLike;
    }

    public void setRoles(List<String> roles){
        this.roles = roles;
    }


    public void setUsername(String username){
        this.username = username;
    }

    public void setUsernameLike( String usernameLike){
	this.usernameLike = usernameLike;
    }

    public void setUsernames(List<String> usernames){
        this.usernames = usernames;
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


    public void setPhone(String phone){
        this.phone = phone;
    }

    public void setPhoneLike( String phoneLike){
	this.phoneLike = phoneLike;
    }

    public void setPhones(List<String> phones){
        this.phones = phones;
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


    public void setCreateby(String createby){
        this.createby = createby;
    }

    public void setCreatebyLike( String createbyLike){
	this.createbyLike = createbyLike;
    }

    public void setCreatebys(List<String> createbys){
        this.createbys = createbys;
    }


    public void setCreatetimeGreaterThanOrEqual(Date createtimeGreaterThanOrEqual){
        this.createtimeGreaterThanOrEqual = createtimeGreaterThanOrEqual;
    }

    public void setCreatetimeLessThanOrEqual(Date createtimeLessThanOrEqual){
	this.createtimeLessThanOrEqual = createtimeLessThanOrEqual;
    }


    public void setUpdateby(String updateby){
        this.updateby = updateby;
    }

    public void setUpdatebyLike( String updatebyLike){
	this.updatebyLike = updatebyLike;
    }

    public void setUpdatebys(List<String> updatebys){
        this.updatebys = updatebys;
    }


    public void setUpdatetimeGreaterThanOrEqual(Date updatetimeGreaterThanOrEqual){
        this.updatetimeGreaterThanOrEqual = updatetimeGreaterThanOrEqual;
    }

    public void setUpdatetimeLessThanOrEqual(Date updatetimeLessThanOrEqual){
	this.updatetimeLessThanOrEqual = updatetimeLessThanOrEqual;
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




    public MonitorProcUserQuery procId(String procId){
	if (procId == null) {
	    throw new RuntimeException("procId is null");
        }         
	this.procId = procId;
	return this;
    }

    public MonitorProcUserQuery procIdLike( String procIdLike){
        if (procIdLike == null) {
            throw new RuntimeException("procId is null");
        }
        this.procIdLike = procIdLike;
        return this;
    }

    public MonitorProcUserQuery procIds(List<String> procIds){
        if (procIds == null) {
            throw new RuntimeException("procIds is empty ");
        }
        this.procIds = procIds;
        return this;
    }


    public MonitorProcUserQuery role(String role){
	if (role == null) {
	    throw new RuntimeException("role is null");
        }         
	this.role = role;
	return this;
    }

    public MonitorProcUserQuery roleLike( String roleLike){
        if (roleLike == null) {
            throw new RuntimeException("role is null");
        }
        this.roleLike = roleLike;
        return this;
    }

    public MonitorProcUserQuery roles(List<String> roles){
        if (roles == null) {
            throw new RuntimeException("roles is empty ");
        }
        this.roles = roles;
        return this;
    }


    public MonitorProcUserQuery username(String username){
	if (username == null) {
	    throw new RuntimeException("username is null");
        }         
	this.username = username;
	return this;
    }

    public MonitorProcUserQuery usernameLike( String usernameLike){
        if (usernameLike == null) {
            throw new RuntimeException("username is null");
        }
        this.usernameLike = usernameLike;
        return this;
    }

    public MonitorProcUserQuery usernames(List<String> usernames){
        if (usernames == null) {
            throw new RuntimeException("usernames is empty ");
        }
        this.usernames = usernames;
        return this;
    }


    public MonitorProcUserQuery tel(String tel){
	if (tel == null) {
	    throw new RuntimeException("tel is null");
        }         
	this.tel = tel;
	return this;
    }

    public MonitorProcUserQuery telLike( String telLike){
        if (telLike == null) {
            throw new RuntimeException("tel is null");
        }
        this.telLike = telLike;
        return this;
    }

    public MonitorProcUserQuery tels(List<String> tels){
        if (tels == null) {
            throw new RuntimeException("tels is empty ");
        }
        this.tels = tels;
        return this;
    }


    public MonitorProcUserQuery phone(String phone){
	if (phone == null) {
	    throw new RuntimeException("phone is null");
        }         
	this.phone = phone;
	return this;
    }

    public MonitorProcUserQuery phoneLike( String phoneLike){
        if (phoneLike == null) {
            throw new RuntimeException("phone is null");
        }
        this.phoneLike = phoneLike;
        return this;
    }

    public MonitorProcUserQuery phones(List<String> phones){
        if (phones == null) {
            throw new RuntimeException("phones is empty ");
        }
        this.phones = phones;
        return this;
    }


    public MonitorProcUserQuery email(String email){
	if (email == null) {
	    throw new RuntimeException("email is null");
        }         
	this.email = email;
	return this;
    }

    public MonitorProcUserQuery emailLike( String emailLike){
        if (emailLike == null) {
            throw new RuntimeException("email is null");
        }
        this.emailLike = emailLike;
        return this;
    }

    public MonitorProcUserQuery emails(List<String> emails){
        if (emails == null) {
            throw new RuntimeException("emails is empty ");
        }
        this.emails = emails;
        return this;
    }


    public MonitorProcUserQuery createby(String createby){
	if (createby == null) {
	    throw new RuntimeException("createby is null");
        }         
	this.createby = createby;
	return this;
    }

    public MonitorProcUserQuery createbyLike( String createbyLike){
        if (createbyLike == null) {
            throw new RuntimeException("createby is null");
        }
        this.createbyLike = createbyLike;
        return this;
    }

    public MonitorProcUserQuery createbys(List<String> createbys){
        if (createbys == null) {
            throw new RuntimeException("createbys is empty ");
        }
        this.createbys = createbys;
        return this;
    }



    public MonitorProcUserQuery createtimeGreaterThanOrEqual(Date createtimeGreaterThanOrEqual){
	if (createtimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createtime is null");
        }         
	this.createtimeGreaterThanOrEqual = createtimeGreaterThanOrEqual;
        return this;
    }

    public MonitorProcUserQuery createtimeLessThanOrEqual(Date createtimeLessThanOrEqual){
        if (createtimeLessThanOrEqual == null) {
            throw new RuntimeException("createtime is null");
        }
        this.createtimeLessThanOrEqual = createtimeLessThanOrEqual;
        return this;
    }



    public MonitorProcUserQuery updateby(String updateby){
	if (updateby == null) {
	    throw new RuntimeException("updateby is null");
        }         
	this.updateby = updateby;
	return this;
    }

    public MonitorProcUserQuery updatebyLike( String updatebyLike){
        if (updatebyLike == null) {
            throw new RuntimeException("updateby is null");
        }
        this.updatebyLike = updatebyLike;
        return this;
    }

    public MonitorProcUserQuery updatebys(List<String> updatebys){
        if (updatebys == null) {
            throw new RuntimeException("updatebys is empty ");
        }
        this.updatebys = updatebys;
        return this;
    }



    public MonitorProcUserQuery updatetimeGreaterThanOrEqual(Date updatetimeGreaterThanOrEqual){
	if (updatetimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updatetime is null");
        }         
	this.updatetimeGreaterThanOrEqual = updatetimeGreaterThanOrEqual;
        return this;
    }

    public MonitorProcUserQuery updatetimeLessThanOrEqual(Date updatetimeLessThanOrEqual){
        if (updatetimeLessThanOrEqual == null) {
            throw new RuntimeException("updatetime is null");
        }
        this.updatetimeLessThanOrEqual = updatetimeLessThanOrEqual;
        return this;
    }



    public MonitorProcUserQuery deleteFlag(Integer deleteFlag){
	if (deleteFlag == null) {
            throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlag = deleteFlag;
	return this;
    }

    public MonitorProcUserQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual){
	if (deleteFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag is null");
        }         
	this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
        return this;
    }

    public MonitorProcUserQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual){
        if (deleteFlagLessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag is null");
        }
        this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
        return this;
    }

    public MonitorProcUserQuery deleteFlags(List<Integer> deleteFlags){
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

            if ("procId".equals(sortColumn)) {
                orderBy = "E.PROC_ID_" + a_x;
            } 

            if ("role".equals(sortColumn)) {
                orderBy = "E.ROLE_" + a_x;
            } 

            if ("username".equals(sortColumn)) {
                orderBy = "E.USERNAME_" + a_x;
            } 

            if ("tel".equals(sortColumn)) {
                orderBy = "E.TEL_" + a_x;
            } 

            if ("phone".equals(sortColumn)) {
                orderBy = "E.PHONE_" + a_x;
            } 

            if ("email".equals(sortColumn)) {
                orderBy = "E.EMAIL_" + a_x;
            } 

            if ("createby".equals(sortColumn)) {
                orderBy = "E.CREATEBY_" + a_x;
            } 

            if ("createtime".equals(sortColumn)) {
                orderBy = "E.CREATETIME_" + a_x;
            } 

            if ("updateby".equals(sortColumn)) {
                orderBy = "E.UPDATEBY_" + a_x;
            } 

            if ("updatetime".equals(sortColumn)) {
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
        addColumn("procId", "PROC_ID_");
        addColumn("role", "ROLE_");
        addColumn("username", "USERNAME_");
        addColumn("tel", "TEL_");
        addColumn("phone", "PHONE_");
        addColumn("email", "EMAIL_");
        addColumn("createby", "CREATEBY_");
        addColumn("createtime", "CREATETIME_");
        addColumn("updateby", "UPDATEBY_");
        addColumn("updatetime", "UPDATETIME_");
        addColumn("deleteFlag", "DELETE_FLAG_");
    }

}