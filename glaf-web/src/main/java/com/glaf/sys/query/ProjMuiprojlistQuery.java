package com.glaf.sys.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ProjMuiprojlistQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Integer> indexIds;
	protected Collection<String> appActorIds;
  	protected String id;
  	protected String idLike;
  	protected List<String> ids;
  	protected Integer intFlag;
  	protected Integer intFlagGreaterThanOrEqual;
  	protected Integer intFlagLessThanOrEqual;
  	protected List<Integer> intFlags;
  	protected String sysId;
  	protected String sysIdLike;
  	protected List<String> sysIds;
  	protected String projName;
  	protected String projNameLike;
  	protected List<String> projNames;
  	protected String num;
  	protected String numLike;
  	protected List<String> nums;
        protected Date cTimeGreaterThanOrEqual;
  	protected Date cTimeLessThanOrEqual;
  	protected String content;
  	protected String contentLike;
  	protected List<String> contents;
  	protected String sDbName;
  	protected String sDbNameLike;
  	protected List<String> sDbNames;
  	protected String sServerName;
  	protected String sServerNameLike;
  	protected List<String> sServerNames;
  	protected String sUser;
  	protected String sUserLike;
  	protected List<String> sUsers;
  	protected String spassword;
  	protected String spasswordLike;
  	protected List<String> spasswords;
  	protected Integer listNo;
  	protected Integer listNoGreaterThanOrEqual;
  	protected Integer listNoLessThanOrEqual;
  	protected List<Integer> listNos;
  	protected String email;
  	protected String emailLike;
  	protected List<String> emails;
  	protected Integer iParentId;
  	protected Integer iParentIdGreaterThanOrEqual;
  	protected Integer iParentIdLessThanOrEqual;
  	protected List<Integer> iParentIds;
  	protected Integer nodeIco;
  	protected Integer nodeIcoGreaterThanOrEqual;
  	protected Integer nodeIcoLessThanOrEqual;
  	protected List<Integer> nodeIcos;
  	protected Integer intLine;
  	protected Integer intLineGreaterThanOrEqual;
  	protected Integer intLineLessThanOrEqual;
  	protected List<Integer> intLines;
  	protected Integer domainIndex;
  	protected Integer domainIndexGreaterThanOrEqual;
  	protected Integer domainIndexLessThanOrEqual;
  	protected List<Integer> domainIndexs;
  	protected Integer inLocal;
  	protected Integer inLocalGreaterThanOrEqual;
  	protected Integer inLocalLessThanOrEqual;
  	protected List<Integer> inLocals;
  	protected String emailPsw;
  	protected String emailPswLike;
  	protected List<String> emailPsws;
  	protected Integer intConnected;
  	protected Integer intConnectedGreaterThanOrEqual;
  	protected Integer intConnectedLessThanOrEqual;
  	protected List<Integer> intConnecteds;
  	protected String emailsStr;
  	protected String emailsStrLike;
  	protected List<String> emailsStrs;
  	protected Integer intOrgLevel;
  	protected Integer intOrgLevelGreaterThanOrEqual;
  	protected Integer intOrgLevelLessThanOrEqual;
  	protected List<Integer> intOrgLevels;
  	protected Integer intSendType;
  	protected Integer intSendTypeGreaterThanOrEqual;
  	protected Integer intSendTypeLessThanOrEqual;
  	protected List<Integer> intSendTypes;
  	protected String emailBaskUp;
  	protected String emailBaskUpLike;
  	protected List<String> emailBaskUps;
  	protected String emailImplement;
  	protected String emailImplementLike;
  	protected List<String> emailImplements;
        protected Date createDateGreaterThanOrEqual;
  	protected Date createDateLessThanOrEqual;
        protected Date updateDateGreaterThanOrEqual;
  	protected Date updateDateLessThanOrEqual;
  	protected String createBy;
  	protected String createByLike;
  	protected List<String> createBys;
  	protected String updateBy;
  	protected String updateByLike;
  	protected List<String> updateBys;
  	protected String smsUrl;
  	protected String smsUrlLike;
  	protected List<String> smsUrls;
  	protected String toDbName;
  	protected String toDbNameLike;
  	protected List<String> toDbNames;
  	protected String toServerName;
  	protected String toServerNameLike;
  	protected List<String> toServerNames;
  	protected String toUser;
  	protected String toUserLike;
  	protected List<String> toUsers;
  	protected String toPassword;
  	protected String toPasswordLike;
  	protected List<String> toPasswords;

    public ProjMuiprojlistQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getId(){
        return id;
    }

    public String getIdLike(){
	    if (idLike != null && idLike.trim().length() > 0) {
		if (!idLike.startsWith("%")) {
		    idLike = "%" + idLike;
		}
		if (!idLike.endsWith("%")) {
		   idLike = idLike + "%";
		}
	    }
	return idLike;
    }

    public List<String> getIds(){
	return ids;
    }


    public Integer getIntFlag(){
        return intFlag;
    }

    public Integer getIntFlagGreaterThanOrEqual(){
        return intFlagGreaterThanOrEqual;
    }

    public Integer getIntFlagLessThanOrEqual(){
	return intFlagLessThanOrEqual;
    }

    public List<Integer> getIntFlags(){
	return intFlags;
    }

    public String getSysId(){
        return sysId;
    }

    public String getSysIdLike(){
	    if (sysIdLike != null && sysIdLike.trim().length() > 0) {
		if (!sysIdLike.startsWith("%")) {
		    sysIdLike = "%" + sysIdLike;
		}
		if (!sysIdLike.endsWith("%")) {
		   sysIdLike = sysIdLike + "%";
		}
	    }
	return sysIdLike;
    }

    public List<String> getSysIds(){
	return sysIds;
    }


    public String getProjName(){
        return projName;
    }

    public String getProjNameLike(){
	    if (projNameLike != null && projNameLike.trim().length() > 0) {
		if (!projNameLike.startsWith("%")) {
		    projNameLike = "%" + projNameLike;
		}
		if (!projNameLike.endsWith("%")) {
		   projNameLike = projNameLike + "%";
		}
	    }
	return projNameLike;
    }

    public List<String> getProjNames(){
	return projNames;
    }


    public String getNum(){
        return num;
    }

    public String getNumLike(){
	    if (numLike != null && numLike.trim().length() > 0) {
		if (!numLike.startsWith("%")) {
		    numLike = "%" + numLike;
		}
		if (!numLike.endsWith("%")) {
		   numLike = numLike + "%";
		}
	    }
	return numLike;
    }

    public List<String> getNums(){
	return nums;
    }


    public Date getCTimeGreaterThanOrEqual(){
        return cTimeGreaterThanOrEqual;
    }

    public Date getCTimeLessThanOrEqual(){
	return cTimeLessThanOrEqual;
    }


    public String getContent(){
        return content;
    }

    public String getContentLike(){
	    if (contentLike != null && contentLike.trim().length() > 0) {
		if (!contentLike.startsWith("%")) {
		    contentLike = "%" + contentLike;
		}
		if (!contentLike.endsWith("%")) {
		   contentLike = contentLike + "%";
		}
	    }
	return contentLike;
    }

    public List<String> getContents(){
	return contents;
    }


    public String getSDbName(){
        return sDbName;
    }

    public String getSDbNameLike(){
	    if (sDbNameLike != null && sDbNameLike.trim().length() > 0) {
		if (!sDbNameLike.startsWith("%")) {
		    sDbNameLike = "%" + sDbNameLike;
		}
		if (!sDbNameLike.endsWith("%")) {
		   sDbNameLike = sDbNameLike + "%";
		}
	    }
	return sDbNameLike;
    }

    public List<String> getSDbNames(){
	return sDbNames;
    }


    public String getSServerName(){
        return sServerName;
    }

    public String getSServerNameLike(){
	    if (sServerNameLike != null && sServerNameLike.trim().length() > 0) {
		if (!sServerNameLike.startsWith("%")) {
		    sServerNameLike = "%" + sServerNameLike;
		}
		if (!sServerNameLike.endsWith("%")) {
		   sServerNameLike = sServerNameLike + "%";
		}
	    }
	return sServerNameLike;
    }

    public List<String> getSServerNames(){
	return sServerNames;
    }


    public String getSUser(){
        return sUser;
    }

    public String getSUserLike(){
	    if (sUserLike != null && sUserLike.trim().length() > 0) {
		if (!sUserLike.startsWith("%")) {
		    sUserLike = "%" + sUserLike;
		}
		if (!sUserLike.endsWith("%")) {
		   sUserLike = sUserLike + "%";
		}
	    }
	return sUserLike;
    }

    public List<String> getSUsers(){
	return sUsers;
    }


    public String getSpassword(){
        return spassword;
    }

    public String getSpasswordLike(){
	    if (spasswordLike != null && spasswordLike.trim().length() > 0) {
		if (!spasswordLike.startsWith("%")) {
		    spasswordLike = "%" + spasswordLike;
		}
		if (!spasswordLike.endsWith("%")) {
		   spasswordLike = spasswordLike + "%";
		}
	    }
	return spasswordLike;
    }

    public List<String> getSpasswords(){
	return spasswords;
    }


    public Integer getListNo(){
        return listNo;
    }

    public Integer getListNoGreaterThanOrEqual(){
        return listNoGreaterThanOrEqual;
    }

    public Integer getListNoLessThanOrEqual(){
	return listNoLessThanOrEqual;
    }

    public List<Integer> getListNos(){
	return listNos;
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


    public Integer getIParentId(){
        return iParentId;
    }

    public Integer getIParentIdGreaterThanOrEqual(){
        return iParentIdGreaterThanOrEqual;
    }

    public Integer getIParentIdLessThanOrEqual(){
	return iParentIdLessThanOrEqual;
    }

    public List<Integer> getIParentIds(){
	return iParentIds;
    }

    public Integer getNodeIco(){
        return nodeIco;
    }

    public Integer getNodeIcoGreaterThanOrEqual(){
        return nodeIcoGreaterThanOrEqual;
    }

    public Integer getNodeIcoLessThanOrEqual(){
	return nodeIcoLessThanOrEqual;
    }

    public List<Integer> getNodeIcos(){
	return nodeIcos;
    }

    public Integer getIntLine(){
        return intLine;
    }

    public Integer getIntLineGreaterThanOrEqual(){
        return intLineGreaterThanOrEqual;
    }

    public Integer getIntLineLessThanOrEqual(){
	return intLineLessThanOrEqual;
    }

    public List<Integer> getIntLines(){
	return intLines;
    }

    public Integer getDomainIndex(){
        return domainIndex;
    }

    public Integer getDomainIndexGreaterThanOrEqual(){
        return domainIndexGreaterThanOrEqual;
    }

    public Integer getDomainIndexLessThanOrEqual(){
	return domainIndexLessThanOrEqual;
    }

    public List<Integer> getDomainIndexs(){
	return domainIndexs;
    }

    public Integer getInLocal(){
        return inLocal;
    }

    public Integer getInLocalGreaterThanOrEqual(){
        return inLocalGreaterThanOrEqual;
    }

    public Integer getInLocalLessThanOrEqual(){
	return inLocalLessThanOrEqual;
    }

    public List<Integer> getInLocals(){
	return inLocals;
    }

    public String getEmailPsw(){
        return emailPsw;
    }

    public String getEmailPswLike(){
	    if (emailPswLike != null && emailPswLike.trim().length() > 0) {
		if (!emailPswLike.startsWith("%")) {
		    emailPswLike = "%" + emailPswLike;
		}
		if (!emailPswLike.endsWith("%")) {
		   emailPswLike = emailPswLike + "%";
		}
	    }
	return emailPswLike;
    }

    public List<String> getEmailPsws(){
	return emailPsws;
    }


    public Integer getIntConnected(){
        return intConnected;
    }

    public Integer getIntConnectedGreaterThanOrEqual(){
        return intConnectedGreaterThanOrEqual;
    }

    public Integer getIntConnectedLessThanOrEqual(){
	return intConnectedLessThanOrEqual;
    }

    public List<Integer> getIntConnecteds(){
	return intConnecteds;
    }

    public String getEmailsStr(){
        return emailsStr;
    }

    public String getEmailsStrLike(){
	    if (emailsStrLike != null && emailsStrLike.trim().length() > 0) {
		if (!emailsStrLike.startsWith("%")) {
		    emailsStrLike = "%" + emailsStrLike;
		}
		if (!emailsStrLike.endsWith("%")) {
		   emailsStrLike = emailsStrLike + "%";
		}
	    }
	return emailsStrLike;
    }

    public List<String> getEmailsStrs(){
	return emailsStrs;
    }


    public Integer getIntOrgLevel(){
        return intOrgLevel;
    }

    public Integer getIntOrgLevelGreaterThanOrEqual(){
        return intOrgLevelGreaterThanOrEqual;
    }

    public Integer getIntOrgLevelLessThanOrEqual(){
	return intOrgLevelLessThanOrEqual;
    }

    public List<Integer> getIntOrgLevels(){
	return intOrgLevels;
    }

    public Integer getIntSendType(){
        return intSendType;
    }

    public Integer getIntSendTypeGreaterThanOrEqual(){
        return intSendTypeGreaterThanOrEqual;
    }

    public Integer getIntSendTypeLessThanOrEqual(){
	return intSendTypeLessThanOrEqual;
    }

    public List<Integer> getIntSendTypes(){
	return intSendTypes;
    }

    public String getEmailBaskUp(){
        return emailBaskUp;
    }

    public String getEmailBaskUpLike(){
	    if (emailBaskUpLike != null && emailBaskUpLike.trim().length() > 0) {
		if (!emailBaskUpLike.startsWith("%")) {
		    emailBaskUpLike = "%" + emailBaskUpLike;
		}
		if (!emailBaskUpLike.endsWith("%")) {
		   emailBaskUpLike = emailBaskUpLike + "%";
		}
	    }
	return emailBaskUpLike;
    }

    public List<String> getEmailBaskUps(){
	return emailBaskUps;
    }


    public String getEmailImplement(){
        return emailImplement;
    }

    public String getEmailImplementLike(){
	    if (emailImplementLike != null && emailImplementLike.trim().length() > 0) {
		if (!emailImplementLike.startsWith("%")) {
		    emailImplementLike = "%" + emailImplementLike;
		}
		if (!emailImplementLike.endsWith("%")) {
		   emailImplementLike = emailImplementLike + "%";
		}
	    }
	return emailImplementLike;
    }

    public List<String> getEmailImplements(){
	return emailImplements;
    }


    public Date getCreateDateGreaterThanOrEqual(){
        return createDateGreaterThanOrEqual;
    }

    public Date getCreateDateLessThanOrEqual(){
	return createDateLessThanOrEqual;
    }


    public Date getUpdateDateGreaterThanOrEqual(){
        return updateDateGreaterThanOrEqual;
    }

    public Date getUpdateDateLessThanOrEqual(){
	return updateDateLessThanOrEqual;
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


    public String getSmsUrl(){
        return smsUrl;
    }

    public String getSmsUrlLike(){
	    if (smsUrlLike != null && smsUrlLike.trim().length() > 0) {
		if (!smsUrlLike.startsWith("%")) {
		    smsUrlLike = "%" + smsUrlLike;
		}
		if (!smsUrlLike.endsWith("%")) {
		   smsUrlLike = smsUrlLike + "%";
		}
	    }
	return smsUrlLike;
    }

    public List<String> getSmsUrls(){
	return smsUrls;
    }


    public String getToDbName(){
        return toDbName;
    }

    public String getToDbNameLike(){
	    if (toDbNameLike != null && toDbNameLike.trim().length() > 0) {
		if (!toDbNameLike.startsWith("%")) {
		    toDbNameLike = "%" + toDbNameLike;
		}
		if (!toDbNameLike.endsWith("%")) {
		   toDbNameLike = toDbNameLike + "%";
		}
	    }
	return toDbNameLike;
    }

    public List<String> getToDbNames(){
	return toDbNames;
    }


    public String getToServerName(){
        return toServerName;
    }

    public String getToServerNameLike(){
	    if (toServerNameLike != null && toServerNameLike.trim().length() > 0) {
		if (!toServerNameLike.startsWith("%")) {
		    toServerNameLike = "%" + toServerNameLike;
		}
		if (!toServerNameLike.endsWith("%")) {
		   toServerNameLike = toServerNameLike + "%";
		}
	    }
	return toServerNameLike;
    }

    public List<String> getToServerNames(){
	return toServerNames;
    }


    public String getToUser(){
        return toUser;
    }

    public String getToUserLike(){
	    if (toUserLike != null && toUserLike.trim().length() > 0) {
		if (!toUserLike.startsWith("%")) {
		    toUserLike = "%" + toUserLike;
		}
		if (!toUserLike.endsWith("%")) {
		   toUserLike = toUserLike + "%";
		}
	    }
	return toUserLike;
    }

    public List<String> getToUsers(){
	return toUsers;
    }


    public String getToPassword(){
        return toPassword;
    }

    public String getToPasswordLike(){
	    if (toPasswordLike != null && toPasswordLike.trim().length() > 0) {
		if (!toPasswordLike.startsWith("%")) {
		    toPasswordLike = "%" + toPasswordLike;
		}
		if (!toPasswordLike.endsWith("%")) {
		   toPasswordLike = toPasswordLike + "%";
		}
	    }
	return toPasswordLike;
    }

    public List<String> getToPasswords(){
	return toPasswords;
    }


 

    public void setId(String id){
        this.id = id;
    }

    public void setIdLike( String idLike){
	this.idLike = idLike;
    }

    public void setIds(List<String> ids){
        this.ids = ids;
    }


    public void setIntFlag(Integer intFlag){
        this.intFlag = intFlag;
    }

    public void setIntFlagGreaterThanOrEqual(Integer intFlagGreaterThanOrEqual){
        this.intFlagGreaterThanOrEqual = intFlagGreaterThanOrEqual;
    }

    public void setIntFlagLessThanOrEqual(Integer intFlagLessThanOrEqual){
	this.intFlagLessThanOrEqual = intFlagLessThanOrEqual;
    }

    public void setIntFlags(List<Integer> intFlags){
        this.intFlags = intFlags;
    }


    public void setSysId(String sysId){
        this.sysId = sysId;
    }

    public void setSysIdLike( String sysIdLike){
	this.sysIdLike = sysIdLike;
    }

    public void setSysIds(List<String> sysIds){
        this.sysIds = sysIds;
    }


    public void setProjName(String projName){
        this.projName = projName;
    }

    public void setProjNameLike( String projNameLike){
	this.projNameLike = projNameLike;
    }

    public void setProjNames(List<String> projNames){
        this.projNames = projNames;
    }


    public void setNum(String num){
        this.num = num;
    }

    public void setNumLike( String numLike){
	this.numLike = numLike;
    }

    public void setNums(List<String> nums){
        this.nums = nums;
    }


    public void setCTimeGreaterThanOrEqual(Date cTimeGreaterThanOrEqual){
        this.cTimeGreaterThanOrEqual = cTimeGreaterThanOrEqual;
    }

    public void setCTimeLessThanOrEqual(Date cTimeLessThanOrEqual){
	this.cTimeLessThanOrEqual = cTimeLessThanOrEqual;
    }


    public void setContent(String content){
        this.content = content;
    }

    public void setContentLike( String contentLike){
	this.contentLike = contentLike;
    }

    public void setContents(List<String> contents){
        this.contents = contents;
    }


    public void setSDbName(String sDbName){
        this.sDbName = sDbName;
    }

    public void setSDbNameLike( String sDbNameLike){
	this.sDbNameLike = sDbNameLike;
    }

    public void setSDbNames(List<String> sDbNames){
        this.sDbNames = sDbNames;
    }


    public void setSServerName(String sServerName){
        this.sServerName = sServerName;
    }

    public void setSServerNameLike( String sServerNameLike){
	this.sServerNameLike = sServerNameLike;
    }

    public void setSServerNames(List<String> sServerNames){
        this.sServerNames = sServerNames;
    }


    public void setSUser(String sUser){
        this.sUser = sUser;
    }

    public void setSUserLike( String sUserLike){
	this.sUserLike = sUserLike;
    }

    public void setSUsers(List<String> sUsers){
        this.sUsers = sUsers;
    }


    public void setSpassword(String spassword){
        this.spassword = spassword;
    }

    public void setSpasswordLike( String spasswordLike){
	this.spasswordLike = spasswordLike;
    }

    public void setSpasswords(List<String> spasswords){
        this.spasswords = spasswords;
    }


    public void setListNo(Integer listNo){
        this.listNo = listNo;
    }

    public void setListNoGreaterThanOrEqual(Integer listNoGreaterThanOrEqual){
        this.listNoGreaterThanOrEqual = listNoGreaterThanOrEqual;
    }

    public void setListNoLessThanOrEqual(Integer listNoLessThanOrEqual){
	this.listNoLessThanOrEqual = listNoLessThanOrEqual;
    }

    public void setListNos(List<Integer> listNos){
        this.listNos = listNos;
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


    public void setIParentId(Integer iParentId){
        this.iParentId = iParentId;
    }

    public void setIParentIdGreaterThanOrEqual(Integer iParentIdGreaterThanOrEqual){
        this.iParentIdGreaterThanOrEqual = iParentIdGreaterThanOrEqual;
    }

    public void setIParentIdLessThanOrEqual(Integer iParentIdLessThanOrEqual){
	this.iParentIdLessThanOrEqual = iParentIdLessThanOrEqual;
    }

    public void setIParentIds(List<Integer> iParentIds){
        this.iParentIds = iParentIds;
    }


    public void setNodeIco(Integer nodeIco){
        this.nodeIco = nodeIco;
    }

    public void setNodeIcoGreaterThanOrEqual(Integer nodeIcoGreaterThanOrEqual){
        this.nodeIcoGreaterThanOrEqual = nodeIcoGreaterThanOrEqual;
    }

    public void setNodeIcoLessThanOrEqual(Integer nodeIcoLessThanOrEqual){
	this.nodeIcoLessThanOrEqual = nodeIcoLessThanOrEqual;
    }

    public void setNodeIcos(List<Integer> nodeIcos){
        this.nodeIcos = nodeIcos;
    }


    public void setIntLine(Integer intLine){
        this.intLine = intLine;
    }

    public void setIntLineGreaterThanOrEqual(Integer intLineGreaterThanOrEqual){
        this.intLineGreaterThanOrEqual = intLineGreaterThanOrEqual;
    }

    public void setIntLineLessThanOrEqual(Integer intLineLessThanOrEqual){
	this.intLineLessThanOrEqual = intLineLessThanOrEqual;
    }

    public void setIntLines(List<Integer> intLines){
        this.intLines = intLines;
    }


    public void setDomainIndex(Integer domainIndex){
        this.domainIndex = domainIndex;
    }

    public void setDomainIndexGreaterThanOrEqual(Integer domainIndexGreaterThanOrEqual){
        this.domainIndexGreaterThanOrEqual = domainIndexGreaterThanOrEqual;
    }

    public void setDomainIndexLessThanOrEqual(Integer domainIndexLessThanOrEqual){
	this.domainIndexLessThanOrEqual = domainIndexLessThanOrEqual;
    }

    public void setDomainIndexs(List<Integer> domainIndexs){
        this.domainIndexs = domainIndexs;
    }


    public void setInLocal(Integer inLocal){
        this.inLocal = inLocal;
    }

    public void setInLocalGreaterThanOrEqual(Integer inLocalGreaterThanOrEqual){
        this.inLocalGreaterThanOrEqual = inLocalGreaterThanOrEqual;
    }

    public void setInLocalLessThanOrEqual(Integer inLocalLessThanOrEqual){
	this.inLocalLessThanOrEqual = inLocalLessThanOrEqual;
    }

    public void setInLocals(List<Integer> inLocals){
        this.inLocals = inLocals;
    }


    public void setEmailPsw(String emailPsw){
        this.emailPsw = emailPsw;
    }

    public void setEmailPswLike( String emailPswLike){
	this.emailPswLike = emailPswLike;
    }

    public void setEmailPsws(List<String> emailPsws){
        this.emailPsws = emailPsws;
    }


    public void setIntConnected(Integer intConnected){
        this.intConnected = intConnected;
    }

    public void setIntConnectedGreaterThanOrEqual(Integer intConnectedGreaterThanOrEqual){
        this.intConnectedGreaterThanOrEqual = intConnectedGreaterThanOrEqual;
    }

    public void setIntConnectedLessThanOrEqual(Integer intConnectedLessThanOrEqual){
	this.intConnectedLessThanOrEqual = intConnectedLessThanOrEqual;
    }

    public void setIntConnecteds(List<Integer> intConnecteds){
        this.intConnecteds = intConnecteds;
    }


    public void setEmailsStr(String emailsStr){
        this.emailsStr = emailsStr;
    }

    public void setEmailsStrLike( String emailsStrLike){
	this.emailsStrLike = emailsStrLike;
    }

    public void setEmailsStrs(List<String> emailsStrs){
        this.emailsStrs = emailsStrs;
    }


    public void setIntOrgLevel(Integer intOrgLevel){
        this.intOrgLevel = intOrgLevel;
    }

    public void setIntOrgLevelGreaterThanOrEqual(Integer intOrgLevelGreaterThanOrEqual){
        this.intOrgLevelGreaterThanOrEqual = intOrgLevelGreaterThanOrEqual;
    }

    public void setIntOrgLevelLessThanOrEqual(Integer intOrgLevelLessThanOrEqual){
	this.intOrgLevelLessThanOrEqual = intOrgLevelLessThanOrEqual;
    }

    public void setIntOrgLevels(List<Integer> intOrgLevels){
        this.intOrgLevels = intOrgLevels;
    }


    public void setIntSendType(Integer intSendType){
        this.intSendType = intSendType;
    }

    public void setIntSendTypeGreaterThanOrEqual(Integer intSendTypeGreaterThanOrEqual){
        this.intSendTypeGreaterThanOrEqual = intSendTypeGreaterThanOrEqual;
    }

    public void setIntSendTypeLessThanOrEqual(Integer intSendTypeLessThanOrEqual){
	this.intSendTypeLessThanOrEqual = intSendTypeLessThanOrEqual;
    }

    public void setIntSendTypes(List<Integer> intSendTypes){
        this.intSendTypes = intSendTypes;
    }


    public void setEmailBaskUp(String emailBaskUp){
        this.emailBaskUp = emailBaskUp;
    }

    public void setEmailBaskUpLike( String emailBaskUpLike){
	this.emailBaskUpLike = emailBaskUpLike;
    }

    public void setEmailBaskUps(List<String> emailBaskUps){
        this.emailBaskUps = emailBaskUps;
    }


    public void setEmailImplement(String emailImplement){
        this.emailImplement = emailImplement;
    }

    public void setEmailImplementLike( String emailImplementLike){
	this.emailImplementLike = emailImplementLike;
    }

    public void setEmailImplements(List<String> emailImplements){
        this.emailImplements = emailImplements;
    }


    public void setCreateDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
        this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
    }

    public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual){
	this.createDateLessThanOrEqual = createDateLessThanOrEqual;
    }


    public void setUpdateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual){
        this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
    }

    public void setUpdateDateLessThanOrEqual(Date updateDateLessThanOrEqual){
	this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
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


    public void setUpdateBy(String updateBy){
        this.updateBy = updateBy;
    }

    public void setUpdateByLike( String updateByLike){
	this.updateByLike = updateByLike;
    }

    public void setUpdateBys(List<String> updateBys){
        this.updateBys = updateBys;
    }


    public void setSmsUrl(String smsUrl){
        this.smsUrl = smsUrl;
    }

    public void setSmsUrlLike( String smsUrlLike){
	this.smsUrlLike = smsUrlLike;
    }

    public void setSmsUrls(List<String> smsUrls){
        this.smsUrls = smsUrls;
    }


    public void setToDbName(String toDbName){
        this.toDbName = toDbName;
    }

    public void setToDbNameLike( String toDbNameLike){
	this.toDbNameLike = toDbNameLike;
    }

    public void setToDbNames(List<String> toDbNames){
        this.toDbNames = toDbNames;
    }


    public void setToServerName(String toServerName){
        this.toServerName = toServerName;
    }

    public void setToServerNameLike( String toServerNameLike){
	this.toServerNameLike = toServerNameLike;
    }

    public void setToServerNames(List<String> toServerNames){
        this.toServerNames = toServerNames;
    }


    public void setToUser(String toUser){
        this.toUser = toUser;
    }

    public void setToUserLike( String toUserLike){
	this.toUserLike = toUserLike;
    }

    public void setToUsers(List<String> toUsers){
        this.toUsers = toUsers;
    }


    public void setToPassword(String toPassword){
        this.toPassword = toPassword;
    }

    public void setToPasswordLike( String toPasswordLike){
	this.toPasswordLike = toPasswordLike;
    }

    public void setToPasswords(List<String> toPasswords){
        this.toPasswords = toPasswords;
    }




    public ProjMuiprojlistQuery id(String id){
	if (id == null) {
	    throw new RuntimeException("id is null");
        }         
	this.id = id;
	return this;
    }

    public ProjMuiprojlistQuery idLike( String idLike){
        if (idLike == null) {
            throw new RuntimeException("id is null");
        }
        this.idLike = idLike;
        return this;
    }

    public ProjMuiprojlistQuery ids(List<String> ids){
        if (ids == null) {
            throw new RuntimeException("ids is empty ");
        }
        this.ids = ids;
        return this;
    }


    public ProjMuiprojlistQuery intFlag(Integer intFlag){
	if (intFlag == null) {
            throw new RuntimeException("intFlag is null");
        }         
	this.intFlag = intFlag;
	return this;
    }

    public ProjMuiprojlistQuery intFlagGreaterThanOrEqual(Integer intFlagGreaterThanOrEqual){
	if (intFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("intFlag is null");
        }         
	this.intFlagGreaterThanOrEqual = intFlagGreaterThanOrEqual;
        return this;
    }

    public ProjMuiprojlistQuery intFlagLessThanOrEqual(Integer intFlagLessThanOrEqual){
        if (intFlagLessThanOrEqual == null) {
            throw new RuntimeException("intFlag is null");
        }
        this.intFlagLessThanOrEqual = intFlagLessThanOrEqual;
        return this;
    }

    public ProjMuiprojlistQuery intFlags(List<Integer> intFlags){
        if (intFlags == null) {
            throw new RuntimeException("intFlags is empty ");
        }
        this.intFlags = intFlags;
        return this;
    }


    public ProjMuiprojlistQuery sysId(String sysId){
	if (sysId == null) {
	    throw new RuntimeException("sysId is null");
        }         
	this.sysId = sysId;
	return this;
    }

    public ProjMuiprojlistQuery sysIdLike( String sysIdLike){
        if (sysIdLike == null) {
            throw new RuntimeException("sysId is null");
        }
        this.sysIdLike = sysIdLike;
        return this;
    }

    public ProjMuiprojlistQuery sysIds(List<String> sysIds){
        if (sysIds == null) {
            throw new RuntimeException("sysIds is empty ");
        }
        this.sysIds = sysIds;
        return this;
    }


    public ProjMuiprojlistQuery projName(String projName){
	if (projName == null) {
	    throw new RuntimeException("projName is null");
        }         
	this.projName = projName;
	return this;
    }

    public ProjMuiprojlistQuery projNameLike( String projNameLike){
        if (projNameLike == null) {
            throw new RuntimeException("projName is null");
        }
        this.projNameLike = projNameLike;
        return this;
    }

    public ProjMuiprojlistQuery projNames(List<String> projNames){
        if (projNames == null) {
            throw new RuntimeException("projNames is empty ");
        }
        this.projNames = projNames;
        return this;
    }


    public ProjMuiprojlistQuery num(String num){
	if (num == null) {
	    throw new RuntimeException("num is null");
        }         
	this.num = num;
	return this;
    }

    public ProjMuiprojlistQuery numLike( String numLike){
        if (numLike == null) {
            throw new RuntimeException("num is null");
        }
        this.numLike = numLike;
        return this;
    }

    public ProjMuiprojlistQuery nums(List<String> nums){
        if (nums == null) {
            throw new RuntimeException("nums is empty ");
        }
        this.nums = nums;
        return this;
    }



    public ProjMuiprojlistQuery cTimeGreaterThanOrEqual(Date cTimeGreaterThanOrEqual){
	if (cTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("cTime is null");
        }         
	this.cTimeGreaterThanOrEqual = cTimeGreaterThanOrEqual;
        return this;
    }

    public ProjMuiprojlistQuery cTimeLessThanOrEqual(Date cTimeLessThanOrEqual){
        if (cTimeLessThanOrEqual == null) {
            throw new RuntimeException("cTime is null");
        }
        this.cTimeLessThanOrEqual = cTimeLessThanOrEqual;
        return this;
    }



    public ProjMuiprojlistQuery content(String content){
	if (content == null) {
	    throw new RuntimeException("content is null");
        }         
	this.content = content;
	return this;
    }

    public ProjMuiprojlistQuery contentLike( String contentLike){
        if (contentLike == null) {
            throw new RuntimeException("content is null");
        }
        this.contentLike = contentLike;
        return this;
    }

    public ProjMuiprojlistQuery contents(List<String> contents){
        if (contents == null) {
            throw new RuntimeException("contents is empty ");
        }
        this.contents = contents;
        return this;
    }


    public ProjMuiprojlistQuery sDbName(String sDbName){
	if (sDbName == null) {
	    throw new RuntimeException("sDbName is null");
        }         
	this.sDbName = sDbName;
	return this;
    }

    public ProjMuiprojlistQuery sDbNameLike( String sDbNameLike){
        if (sDbNameLike == null) {
            throw new RuntimeException("sDbName is null");
        }
        this.sDbNameLike = sDbNameLike;
        return this;
    }

    public ProjMuiprojlistQuery sDbNames(List<String> sDbNames){
        if (sDbNames == null) {
            throw new RuntimeException("sDbNames is empty ");
        }
        this.sDbNames = sDbNames;
        return this;
    }


    public ProjMuiprojlistQuery sServerName(String sServerName){
	if (sServerName == null) {
	    throw new RuntimeException("sServerName is null");
        }         
	this.sServerName = sServerName;
	return this;
    }

    public ProjMuiprojlistQuery sServerNameLike( String sServerNameLike){
        if (sServerNameLike == null) {
            throw new RuntimeException("sServerName is null");
        }
        this.sServerNameLike = sServerNameLike;
        return this;
    }

    public ProjMuiprojlistQuery sServerNames(List<String> sServerNames){
        if (sServerNames == null) {
            throw new RuntimeException("sServerNames is empty ");
        }
        this.sServerNames = sServerNames;
        return this;
    }


    public ProjMuiprojlistQuery sUser(String sUser){
	if (sUser == null) {
	    throw new RuntimeException("sUser is null");
        }         
	this.sUser = sUser;
	return this;
    }

    public ProjMuiprojlistQuery sUserLike( String sUserLike){
        if (sUserLike == null) {
            throw new RuntimeException("sUser is null");
        }
        this.sUserLike = sUserLike;
        return this;
    }

    public ProjMuiprojlistQuery sUsers(List<String> sUsers){
        if (sUsers == null) {
            throw new RuntimeException("sUsers is empty ");
        }
        this.sUsers = sUsers;
        return this;
    }


    public ProjMuiprojlistQuery spassword(String spassword){
	if (spassword == null) {
	    throw new RuntimeException("spassword is null");
        }         
	this.spassword = spassword;
	return this;
    }

    public ProjMuiprojlistQuery spasswordLike( String spasswordLike){
        if (spasswordLike == null) {
            throw new RuntimeException("spassword is null");
        }
        this.spasswordLike = spasswordLike;
        return this;
    }

    public ProjMuiprojlistQuery spasswords(List<String> spasswords){
        if (spasswords == null) {
            throw new RuntimeException("spasswords is empty ");
        }
        this.spasswords = spasswords;
        return this;
    }


    public ProjMuiprojlistQuery listNo(Integer listNo){
	if (listNo == null) {
            throw new RuntimeException("listNo is null");
        }         
	this.listNo = listNo;
	return this;
    }

    public ProjMuiprojlistQuery listNoGreaterThanOrEqual(Integer listNoGreaterThanOrEqual){
	if (listNoGreaterThanOrEqual == null) {
	    throw new RuntimeException("listNo is null");
        }         
	this.listNoGreaterThanOrEqual = listNoGreaterThanOrEqual;
        return this;
    }

    public ProjMuiprojlistQuery listNoLessThanOrEqual(Integer listNoLessThanOrEqual){
        if (listNoLessThanOrEqual == null) {
            throw new RuntimeException("listNo is null");
        }
        this.listNoLessThanOrEqual = listNoLessThanOrEqual;
        return this;
    }

    public ProjMuiprojlistQuery listNos(List<Integer> listNos){
        if (listNos == null) {
            throw new RuntimeException("listNos is empty ");
        }
        this.listNos = listNos;
        return this;
    }


    public ProjMuiprojlistQuery email(String email){
	if (email == null) {
	    throw new RuntimeException("email is null");
        }         
	this.email = email;
	return this;
    }

    public ProjMuiprojlistQuery emailLike( String emailLike){
        if (emailLike == null) {
            throw new RuntimeException("email is null");
        }
        this.emailLike = emailLike;
        return this;
    }

    public ProjMuiprojlistQuery emails(List<String> emails){
        if (emails == null) {
            throw new RuntimeException("emails is empty ");
        }
        this.emails = emails;
        return this;
    }


    public ProjMuiprojlistQuery iParentId(Integer iParentId){
	if (iParentId == null) {
            throw new RuntimeException("iParentId is null");
        }         
	this.iParentId = iParentId;
	return this;
    }

    public ProjMuiprojlistQuery iParentIdGreaterThanOrEqual(Integer iParentIdGreaterThanOrEqual){
	if (iParentIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("iParentId is null");
        }         
	this.iParentIdGreaterThanOrEqual = iParentIdGreaterThanOrEqual;
        return this;
    }

    public ProjMuiprojlistQuery iParentIdLessThanOrEqual(Integer iParentIdLessThanOrEqual){
        if (iParentIdLessThanOrEqual == null) {
            throw new RuntimeException("iParentId is null");
        }
        this.iParentIdLessThanOrEqual = iParentIdLessThanOrEqual;
        return this;
    }

    public ProjMuiprojlistQuery iParentIds(List<Integer> iParentIds){
        if (iParentIds == null) {
            throw new RuntimeException("iParentIds is empty ");
        }
        this.iParentIds = iParentIds;
        return this;
    }


    public ProjMuiprojlistQuery nodeIco(Integer nodeIco){
	if (nodeIco == null) {
            throw new RuntimeException("nodeIco is null");
        }         
	this.nodeIco = nodeIco;
	return this;
    }

    public ProjMuiprojlistQuery nodeIcoGreaterThanOrEqual(Integer nodeIcoGreaterThanOrEqual){
	if (nodeIcoGreaterThanOrEqual == null) {
	    throw new RuntimeException("nodeIco is null");
        }         
	this.nodeIcoGreaterThanOrEqual = nodeIcoGreaterThanOrEqual;
        return this;
    }

    public ProjMuiprojlistQuery nodeIcoLessThanOrEqual(Integer nodeIcoLessThanOrEqual){
        if (nodeIcoLessThanOrEqual == null) {
            throw new RuntimeException("nodeIco is null");
        }
        this.nodeIcoLessThanOrEqual = nodeIcoLessThanOrEqual;
        return this;
    }

    public ProjMuiprojlistQuery nodeIcos(List<Integer> nodeIcos){
        if (nodeIcos == null) {
            throw new RuntimeException("nodeIcos is empty ");
        }
        this.nodeIcos = nodeIcos;
        return this;
    }


    public ProjMuiprojlistQuery intLine(Integer intLine){
	if (intLine == null) {
            throw new RuntimeException("intLine is null");
        }         
	this.intLine = intLine;
	return this;
    }

    public ProjMuiprojlistQuery intLineGreaterThanOrEqual(Integer intLineGreaterThanOrEqual){
	if (intLineGreaterThanOrEqual == null) {
	    throw new RuntimeException("intLine is null");
        }         
	this.intLineGreaterThanOrEqual = intLineGreaterThanOrEqual;
        return this;
    }

    public ProjMuiprojlistQuery intLineLessThanOrEqual(Integer intLineLessThanOrEqual){
        if (intLineLessThanOrEqual == null) {
            throw new RuntimeException("intLine is null");
        }
        this.intLineLessThanOrEqual = intLineLessThanOrEqual;
        return this;
    }

    public ProjMuiprojlistQuery intLines(List<Integer> intLines){
        if (intLines == null) {
            throw new RuntimeException("intLines is empty ");
        }
        this.intLines = intLines;
        return this;
    }


    public ProjMuiprojlistQuery domainIndex(Integer domainIndex){
	if (domainIndex == null) {
            throw new RuntimeException("domainIndex is null");
        }         
	this.domainIndex = domainIndex;
	return this;
    }

    public ProjMuiprojlistQuery domainIndexGreaterThanOrEqual(Integer domainIndexGreaterThanOrEqual){
	if (domainIndexGreaterThanOrEqual == null) {
	    throw new RuntimeException("domainIndex is null");
        }         
	this.domainIndexGreaterThanOrEqual = domainIndexGreaterThanOrEqual;
        return this;
    }

    public ProjMuiprojlistQuery domainIndexLessThanOrEqual(Integer domainIndexLessThanOrEqual){
        if (domainIndexLessThanOrEqual == null) {
            throw new RuntimeException("domainIndex is null");
        }
        this.domainIndexLessThanOrEqual = domainIndexLessThanOrEqual;
        return this;
    }

    public ProjMuiprojlistQuery domainIndexs(List<Integer> domainIndexs){
        if (domainIndexs == null) {
            throw new RuntimeException("domainIndexs is empty ");
        }
        this.domainIndexs = domainIndexs;
        return this;
    }


    public ProjMuiprojlistQuery inLocal(Integer inLocal){
	if (inLocal == null) {
            throw new RuntimeException("inLocal is null");
        }         
	this.inLocal = inLocal;
	return this;
    }

    public ProjMuiprojlistQuery inLocalGreaterThanOrEqual(Integer inLocalGreaterThanOrEqual){
	if (inLocalGreaterThanOrEqual == null) {
	    throw new RuntimeException("inLocal is null");
        }         
	this.inLocalGreaterThanOrEqual = inLocalGreaterThanOrEqual;
        return this;
    }

    public ProjMuiprojlistQuery inLocalLessThanOrEqual(Integer inLocalLessThanOrEqual){
        if (inLocalLessThanOrEqual == null) {
            throw new RuntimeException("inLocal is null");
        }
        this.inLocalLessThanOrEqual = inLocalLessThanOrEqual;
        return this;
    }

    public ProjMuiprojlistQuery inLocals(List<Integer> inLocals){
        if (inLocals == null) {
            throw new RuntimeException("inLocals is empty ");
        }
        this.inLocals = inLocals;
        return this;
    }


    public ProjMuiprojlistQuery emailPsw(String emailPsw){
	if (emailPsw == null) {
	    throw new RuntimeException("emailPsw is null");
        }         
	this.emailPsw = emailPsw;
	return this;
    }

    public ProjMuiprojlistQuery emailPswLike( String emailPswLike){
        if (emailPswLike == null) {
            throw new RuntimeException("emailPsw is null");
        }
        this.emailPswLike = emailPswLike;
        return this;
    }

    public ProjMuiprojlistQuery emailPsws(List<String> emailPsws){
        if (emailPsws == null) {
            throw new RuntimeException("emailPsws is empty ");
        }
        this.emailPsws = emailPsws;
        return this;
    }


    public ProjMuiprojlistQuery intConnected(Integer intConnected){
	if (intConnected == null) {
            throw new RuntimeException("intConnected is null");
        }         
	this.intConnected = intConnected;
	return this;
    }

    public ProjMuiprojlistQuery intConnectedGreaterThanOrEqual(Integer intConnectedGreaterThanOrEqual){
	if (intConnectedGreaterThanOrEqual == null) {
	    throw new RuntimeException("intConnected is null");
        }         
	this.intConnectedGreaterThanOrEqual = intConnectedGreaterThanOrEqual;
        return this;
    }

    public ProjMuiprojlistQuery intConnectedLessThanOrEqual(Integer intConnectedLessThanOrEqual){
        if (intConnectedLessThanOrEqual == null) {
            throw new RuntimeException("intConnected is null");
        }
        this.intConnectedLessThanOrEqual = intConnectedLessThanOrEqual;
        return this;
    }

    public ProjMuiprojlistQuery intConnecteds(List<Integer> intConnecteds){
        if (intConnecteds == null) {
            throw new RuntimeException("intConnecteds is empty ");
        }
        this.intConnecteds = intConnecteds;
        return this;
    }


    public ProjMuiprojlistQuery emailsStr(String emailsStr){
	if (emailsStr == null) {
	    throw new RuntimeException("emailsStr is null");
        }         
	this.emailsStr = emailsStr;
	return this;
    }

    public ProjMuiprojlistQuery emailsStrLike( String emailsStrLike){
        if (emailsStrLike == null) {
            throw new RuntimeException("emailsStr is null");
        }
        this.emailsStrLike = emailsStrLike;
        return this;
    }

    public ProjMuiprojlistQuery emailsStrs(List<String> emailsStrs){
        if (emailsStrs == null) {
            throw new RuntimeException("emailsStrs is empty ");
        }
        this.emailsStrs = emailsStrs;
        return this;
    }


    public ProjMuiprojlistQuery intOrgLevel(Integer intOrgLevel){
	if (intOrgLevel == null) {
            throw new RuntimeException("intOrgLevel is null");
        }         
	this.intOrgLevel = intOrgLevel;
	return this;
    }

    public ProjMuiprojlistQuery intOrgLevelGreaterThanOrEqual(Integer intOrgLevelGreaterThanOrEqual){
	if (intOrgLevelGreaterThanOrEqual == null) {
	    throw new RuntimeException("intOrgLevel is null");
        }         
	this.intOrgLevelGreaterThanOrEqual = intOrgLevelGreaterThanOrEqual;
        return this;
    }

    public ProjMuiprojlistQuery intOrgLevelLessThanOrEqual(Integer intOrgLevelLessThanOrEqual){
        if (intOrgLevelLessThanOrEqual == null) {
            throw new RuntimeException("intOrgLevel is null");
        }
        this.intOrgLevelLessThanOrEqual = intOrgLevelLessThanOrEqual;
        return this;
    }

    public ProjMuiprojlistQuery intOrgLevels(List<Integer> intOrgLevels){
        if (intOrgLevels == null) {
            throw new RuntimeException("intOrgLevels is empty ");
        }
        this.intOrgLevels = intOrgLevels;
        return this;
    }


    public ProjMuiprojlistQuery intSendType(Integer intSendType){
	if (intSendType == null) {
            throw new RuntimeException("intSendType is null");
        }         
	this.intSendType = intSendType;
	return this;
    }

    public ProjMuiprojlistQuery intSendTypeGreaterThanOrEqual(Integer intSendTypeGreaterThanOrEqual){
	if (intSendTypeGreaterThanOrEqual == null) {
	    throw new RuntimeException("intSendType is null");
        }         
	this.intSendTypeGreaterThanOrEqual = intSendTypeGreaterThanOrEqual;
        return this;
    }

    public ProjMuiprojlistQuery intSendTypeLessThanOrEqual(Integer intSendTypeLessThanOrEqual){
        if (intSendTypeLessThanOrEqual == null) {
            throw new RuntimeException("intSendType is null");
        }
        this.intSendTypeLessThanOrEqual = intSendTypeLessThanOrEqual;
        return this;
    }

    public ProjMuiprojlistQuery intSendTypes(List<Integer> intSendTypes){
        if (intSendTypes == null) {
            throw new RuntimeException("intSendTypes is empty ");
        }
        this.intSendTypes = intSendTypes;
        return this;
    }


    public ProjMuiprojlistQuery emailBaskUp(String emailBaskUp){
	if (emailBaskUp == null) {
	    throw new RuntimeException("emailBaskUp is null");
        }         
	this.emailBaskUp = emailBaskUp;
	return this;
    }

    public ProjMuiprojlistQuery emailBaskUpLike( String emailBaskUpLike){
        if (emailBaskUpLike == null) {
            throw new RuntimeException("emailBaskUp is null");
        }
        this.emailBaskUpLike = emailBaskUpLike;
        return this;
    }

    public ProjMuiprojlistQuery emailBaskUps(List<String> emailBaskUps){
        if (emailBaskUps == null) {
            throw new RuntimeException("emailBaskUps is empty ");
        }
        this.emailBaskUps = emailBaskUps;
        return this;
    }


    public ProjMuiprojlistQuery emailImplement(String emailImplement){
	if (emailImplement == null) {
	    throw new RuntimeException("emailImplement is null");
        }         
	this.emailImplement = emailImplement;
	return this;
    }

    public ProjMuiprojlistQuery emailImplementLike( String emailImplementLike){
        if (emailImplementLike == null) {
            throw new RuntimeException("emailImplement is null");
        }
        this.emailImplementLike = emailImplementLike;
        return this;
    }

    public ProjMuiprojlistQuery emailImplements(List<String> emailImplements){
        if (emailImplements == null) {
            throw new RuntimeException("emailImplements is empty ");
        }
        this.emailImplements = emailImplements;
        return this;
    }



    public ProjMuiprojlistQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
	if (createDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDate is null");
        }         
	this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
        return this;
    }

    public ProjMuiprojlistQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual){
        if (createDateLessThanOrEqual == null) {
            throw new RuntimeException("createDate is null");
        }
        this.createDateLessThanOrEqual = createDateLessThanOrEqual;
        return this;
    }




    public ProjMuiprojlistQuery updateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual){
	if (updateDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateDate is null");
        }         
	this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
        return this;
    }

    public ProjMuiprojlistQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual){
        if (updateDateLessThanOrEqual == null) {
            throw new RuntimeException("updateDate is null");
        }
        this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
        return this;
    }



    public ProjMuiprojlistQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public ProjMuiprojlistQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public ProjMuiprojlistQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }


    public ProjMuiprojlistQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public ProjMuiprojlistQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public ProjMuiprojlistQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }


    public ProjMuiprojlistQuery smsUrl(String smsUrl){
	if (smsUrl == null) {
	    throw new RuntimeException("smsUrl is null");
        }         
	this.smsUrl = smsUrl;
	return this;
    }

    public ProjMuiprojlistQuery smsUrlLike( String smsUrlLike){
        if (smsUrlLike == null) {
            throw new RuntimeException("smsUrl is null");
        }
        this.smsUrlLike = smsUrlLike;
        return this;
    }

    public ProjMuiprojlistQuery smsUrls(List<String> smsUrls){
        if (smsUrls == null) {
            throw new RuntimeException("smsUrls is empty ");
        }
        this.smsUrls = smsUrls;
        return this;
    }


    public ProjMuiprojlistQuery toDbName(String toDbName){
	if (toDbName == null) {
	    throw new RuntimeException("toDbName is null");
        }         
	this.toDbName = toDbName;
	return this;
    }

    public ProjMuiprojlistQuery toDbNameLike( String toDbNameLike){
        if (toDbNameLike == null) {
            throw new RuntimeException("toDbName is null");
        }
        this.toDbNameLike = toDbNameLike;
        return this;
    }

    public ProjMuiprojlistQuery toDbNames(List<String> toDbNames){
        if (toDbNames == null) {
            throw new RuntimeException("toDbNames is empty ");
        }
        this.toDbNames = toDbNames;
        return this;
    }


    public ProjMuiprojlistQuery toServerName(String toServerName){
	if (toServerName == null) {
	    throw new RuntimeException("toServerName is null");
        }         
	this.toServerName = toServerName;
	return this;
    }

    public ProjMuiprojlistQuery toServerNameLike( String toServerNameLike){
        if (toServerNameLike == null) {
            throw new RuntimeException("toServerName is null");
        }
        this.toServerNameLike = toServerNameLike;
        return this;
    }

    public ProjMuiprojlistQuery toServerNames(List<String> toServerNames){
        if (toServerNames == null) {
            throw new RuntimeException("toServerNames is empty ");
        }
        this.toServerNames = toServerNames;
        return this;
    }


    public ProjMuiprojlistQuery toUser(String toUser){
	if (toUser == null) {
	    throw new RuntimeException("toUser is null");
        }         
	this.toUser = toUser;
	return this;
    }

    public ProjMuiprojlistQuery toUserLike( String toUserLike){
        if (toUserLike == null) {
            throw new RuntimeException("toUser is null");
        }
        this.toUserLike = toUserLike;
        return this;
    }

    public ProjMuiprojlistQuery toUsers(List<String> toUsers){
        if (toUsers == null) {
            throw new RuntimeException("toUsers is empty ");
        }
        this.toUsers = toUsers;
        return this;
    }


    public ProjMuiprojlistQuery toPassword(String toPassword){
	if (toPassword == null) {
	    throw new RuntimeException("toPassword is null");
        }         
	this.toPassword = toPassword;
	return this;
    }

    public ProjMuiprojlistQuery toPasswordLike( String toPasswordLike){
        if (toPasswordLike == null) {
            throw new RuntimeException("toPassword is null");
        }
        this.toPasswordLike = toPasswordLike;
        return this;
    }

    public ProjMuiprojlistQuery toPasswords(List<String> toPasswords){
        if (toPasswords == null) {
            throw new RuntimeException("toPasswords is empty ");
        }
        this.toPasswords = toPasswords;
        return this;
    }



    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("id".equals(sortColumn)) {
                orderBy = "E.ID" + a_x;
            } 

            if ("intFlag".equals(sortColumn)) {
                orderBy = "E.INTFLAG" + a_x;
            } 

            if ("sysId".equals(sortColumn)) {
                orderBy = "E.SYS_ID" + a_x;
            } 

            if ("projName".equals(sortColumn)) {
                orderBy = "E.PROJNAME" + a_x;
            } 

            if ("num".equals(sortColumn)) {
                orderBy = "E.NUM" + a_x;
            } 

            if ("cTime".equals(sortColumn)) {
                orderBy = "E.CTIME" + a_x;
            } 

            if ("content".equals(sortColumn)) {
                orderBy = "E.CONTENT" + a_x;
            } 

            if ("sDbName".equals(sortColumn)) {
                orderBy = "E.SDBNAME" + a_x;
            } 

            if ("sServerName".equals(sortColumn)) {
                orderBy = "E.SSERVERNAME" + a_x;
            } 

            if ("sUser".equals(sortColumn)) {
                orderBy = "E.SUSER" + a_x;
            } 

            if ("spassword".equals(sortColumn)) {
                orderBy = "E.SPASSWORD" + a_x;
            } 

            if ("listNo".equals(sortColumn)) {
                orderBy = "E.LISTNO" + a_x;
            } 

            if ("email".equals(sortColumn)) {
                orderBy = "E.EMAIL" + a_x;
            } 

            if ("iParentId".equals(sortColumn)) {
                orderBy = "E.PARENT_ID" + a_x;
            } 

            if ("nodeIco".equals(sortColumn)) {
                orderBy = "E.NODEICO" + a_x;
            } 

            if ("intLine".equals(sortColumn)) {
                orderBy = "E.INTLINE" + a_x;
            } 

            if ("domainIndex".equals(sortColumn)) {
                orderBy = "E.DOMAIN_INDEX" + a_x;
            } 

            if ("inLocal".equals(sortColumn)) {
                orderBy = "E.INTLOCAL" + a_x;
            } 

            if ("emailPsw".equals(sortColumn)) {
                orderBy = "E.EMAIL_PSW" + a_x;
            } 

            if ("intConnected".equals(sortColumn)) {
                orderBy = "E.INTCONNECTED" + a_x;
            } 

            if ("emailsStr".equals(sortColumn)) {
                orderBy = "E.EMAIL_S" + a_x;
            } 

            if ("intOrgLevel".equals(sortColumn)) {
                orderBy = "E.INTORGLEVEL" + a_x;
            } 

            if ("intSendType".equals(sortColumn)) {
                orderBy = "E.INTSENDTYPE" + a_x;
            } 

            if ("emailBaskUp".equals(sortColumn)) {
                orderBy = "E.EMAIL_BACKUP" + a_x;
            } 

            if ("emailImplement".equals(sortColumn)) {
                orderBy = "E.EMAIL_IMPLEMENT" + a_x;
            } 

            if ("createDate".equals(sortColumn)) {
                orderBy = "E.CREATEDATE" + a_x;
            } 

            if ("updateDate".equals(sortColumn)) {
                orderBy = "E.UPDATEDATE" + a_x;
            } 

            if ("createBy".equals(sortColumn)) {
                orderBy = "E.CREATEBY" + a_x;
            } 

            if ("updateBy".equals(sortColumn)) {
                orderBy = "E.UPDATEBY" + a_x;
            } 

            if ("smsUrl".equals(sortColumn)) {
                orderBy = "E.SMS_URL" + a_x;
            } 

            if ("toDbName".equals(sortColumn)) {
                orderBy = "E.TODBNAME" + a_x;
            } 

            if ("toServerName".equals(sortColumn)) {
                orderBy = "E.TOSERVERNAME" + a_x;
            } 

            if ("toUser".equals(sortColumn)) {
                orderBy = "E.TOUSER" + a_x;
            } 

            if ("toPassword".equals(sortColumn)) {
                orderBy = "E.TOPASSWORD" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("indexId", "INDEX_ID");
        addColumn("id", "ID");
        addColumn("intFlag", "INTFLAG");
        addColumn("sysId", "SYS_ID");
        addColumn("projName", "PROJNAME");
        addColumn("num", "NUM");
        addColumn("cTime", "CTIME");
        addColumn("content", "CONTENT");
        addColumn("sDbName", "SDBNAME");
        addColumn("sServerName", "SSERVERNAME");
        addColumn("sUser", "SUSER");
        addColumn("spassword", "SPASSWORD");
        addColumn("listNo", "LISTNO");
        addColumn("email", "EMAIL");
        addColumn("iParentId", "PARENT_ID");
        addColumn("nodeIco", "NODEICO");
        addColumn("intLine", "INTLINE");
        addColumn("domainIndex", "DOMAIN_INDEX");
        addColumn("inLocal", "INTLOCAL");
        addColumn("emailPsw", "EMAIL_PSW");
        addColumn("intConnected", "INTCONNECTED");
        addColumn("emailsStr", "EMAIL_S");
        addColumn("intOrgLevel", "INTORGLEVEL");
        addColumn("intSendType", "INTSENDTYPE");
        addColumn("emailBaskUp", "EMAIL_BACKUP");
        addColumn("emailImplement", "EMAIL_IMPLEMENT");
        addColumn("createDate", "CREATEDATE");
        addColumn("updateDate", "UPDATEDATE");
        addColumn("createBy", "CREATEBY");
        addColumn("updateBy", "UPDATEBY");
        addColumn("smsUrl", "SMS_URL");
        addColumn("toDbName", "TODBNAME");
        addColumn("toServerName", "TOSERVERNAME");
        addColumn("toUser", "TOUSER");
        addColumn("toPassword", "TOPASSWORD");
    }

}