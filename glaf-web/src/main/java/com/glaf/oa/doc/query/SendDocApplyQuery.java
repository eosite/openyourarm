package com.glaf.oa.doc.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SendDocApplyQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Integer> ids;
	protected Collection<String> appActorIds;
  	protected String subject;
  	protected String subjectLike;
  	protected List<String> subjects;
  	protected Integer securityLevel;
  	protected Integer securityLevelGreaterThanOrEqual;
  	protected Integer securityLevelLessThanOrEqual;
  	protected List<Integer> securityLevels;
  	protected Integer urgencyLevel;
  	protected Integer urgencyLevelGreaterThanOrEqual;
  	protected Integer urgencyLevelLessThanOrEqual;
  	protected List<Integer> urgencyLevels;
  	protected String docNo;
  	protected String docNoLike;
  	protected List<String> docNos;
  	protected String docType;
  	protected String docTypeLike;
  	protected List<String> docTypes;
  	protected String draftName;
  	protected String draftNameLike;
  	protected List<String> draftNames;
        protected Date draftDateGreaterThanOrEqual;
  	protected Date draftDateLessThanOrEqual;
        protected Date sendDocDateGreaterThanOrEqual;
  	protected Date sendDocDateLessThanOrEqual;
  	protected String serialNumber;
  	protected String serialNumberLike;
  	protected List<String> serialNumbers;
  	protected String fromCompany;
  	protected String fromCompanyLike;
  	protected List<String> fromCompanys;
  	protected String keywords;
  	protected String keywordsLike;
  	protected List<String> keywordss;
  	protected String docToCompany;
  	protected String docToCompanyLike;
  	protected List<String> docToCompanys;
  	protected String docCCCompany;
  	protected String docCCCompanyLike;
  	protected List<String> docCCCompanys;
  	protected String checkOpinion;
  	protected String checkOpinionLike;
  	protected List<String> checkOpinions;
  	protected String countersignOpinion;
  	protected String countersignOpinionLike;
  	protected List<String> countersignOpinions;
  	protected String signAndIssueOpinion;
  	protected String signAndIssueOpinionLike;
  	protected List<String> signAndIssueOpinions;
  	protected String remark;
  	protected String remarkLike;
  	protected List<String> remarks;
  	protected Integer status;
  	protected Integer statusGreaterThanOrEqual;
  	protected Integer statusLessThanOrEqual;
  	protected List<Integer> statuss;
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

    public SendDocApplyQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getSubject(){
        return subject;
    }

    public String getSubjectLike(){
	    if (subjectLike != null && subjectLike.trim().length() > 0) {
		if (!subjectLike.startsWith("%")) {
		    subjectLike = "%" + subjectLike;
		}
		if (!subjectLike.endsWith("%")) {
		   subjectLike = subjectLike + "%";
		}
	    }
	return subjectLike;
    }

    public List<String> getSubjects(){
	return subjects;
    }


    public Integer getSecurityLevel(){
        return securityLevel;
    }

    public Integer getSecurityLevelGreaterThanOrEqual(){
        return securityLevelGreaterThanOrEqual;
    }

    public Integer getSecurityLevelLessThanOrEqual(){
	return securityLevelLessThanOrEqual;
    }

    public List<Integer> getSecurityLevels(){
	return securityLevels;
    }

    public Integer getUrgencyLevel(){
        return urgencyLevel;
    }

    public Integer getUrgencyLevelGreaterThanOrEqual(){
        return urgencyLevelGreaterThanOrEqual;
    }

    public Integer getUrgencyLevelLessThanOrEqual(){
	return urgencyLevelLessThanOrEqual;
    }

    public List<Integer> getUrgencyLevels(){
	return urgencyLevels;
    }

    public String getDocNo(){
        return docNo;
    }

    public String getDocNoLike(){
	    if (docNoLike != null && docNoLike.trim().length() > 0) {
		if (!docNoLike.startsWith("%")) {
		    docNoLike = "%" + docNoLike;
		}
		if (!docNoLike.endsWith("%")) {
		   docNoLike = docNoLike + "%";
		}
	    }
	return docNoLike;
    }

    public List<String> getDocNos(){
	return docNos;
    }


    public String getDocType(){
        return docType;
    }

    public String getDocTypeLike(){
	    if (docTypeLike != null && docTypeLike.trim().length() > 0) {
		if (!docTypeLike.startsWith("%")) {
		    docTypeLike = "%" + docTypeLike;
		}
		if (!docTypeLike.endsWith("%")) {
		   docTypeLike = docTypeLike + "%";
		}
	    }
	return docTypeLike;
    }

    public List<String> getDocTypes(){
	return docTypes;
    }


    public String getDraftName(){
        return draftName;
    }

    public String getDraftNameLike(){
	    if (draftNameLike != null && draftNameLike.trim().length() > 0) {
		if (!draftNameLike.startsWith("%")) {
		    draftNameLike = "%" + draftNameLike;
		}
		if (!draftNameLike.endsWith("%")) {
		   draftNameLike = draftNameLike + "%";
		}
	    }
	return draftNameLike;
    }

    public List<String> getDraftNames(){
	return draftNames;
    }


    public Date getDraftDateGreaterThanOrEqual(){
        return draftDateGreaterThanOrEqual;
    }

    public Date getDraftDateLessThanOrEqual(){
	return draftDateLessThanOrEqual;
    }


    public Date getSendDocDateGreaterThanOrEqual(){
        return sendDocDateGreaterThanOrEqual;
    }

    public Date getSendDocDateLessThanOrEqual(){
	return sendDocDateLessThanOrEqual;
    }


    public String getSerialNumber(){
        return serialNumber;
    }

    public String getSerialNumberLike(){
	    if (serialNumberLike != null && serialNumberLike.trim().length() > 0) {
		if (!serialNumberLike.startsWith("%")) {
		    serialNumberLike = "%" + serialNumberLike;
		}
		if (!serialNumberLike.endsWith("%")) {
		   serialNumberLike = serialNumberLike + "%";
		}
	    }
	return serialNumberLike;
    }

    public List<String> getSerialNumbers(){
	return serialNumbers;
    }


    public String getFromCompany(){
        return fromCompany;
    }

    public String getFromCompanyLike(){
	    if (fromCompanyLike != null && fromCompanyLike.trim().length() > 0) {
		if (!fromCompanyLike.startsWith("%")) {
		    fromCompanyLike = "%" + fromCompanyLike;
		}
		if (!fromCompanyLike.endsWith("%")) {
		   fromCompanyLike = fromCompanyLike + "%";
		}
	    }
	return fromCompanyLike;
    }

    public List<String> getFromCompanys(){
	return fromCompanys;
    }


    public String getKeywords(){
        return keywords;
    }

    public String getKeywordsLike(){
	    if (keywordsLike != null && keywordsLike.trim().length() > 0) {
		if (!keywordsLike.startsWith("%")) {
		    keywordsLike = "%" + keywordsLike;
		}
		if (!keywordsLike.endsWith("%")) {
		   keywordsLike = keywordsLike + "%";
		}
	    }
	return keywordsLike;
    }

    public List<String> getKeywordss(){
	return keywordss;
    }


    public String getDocToCompany(){
        return docToCompany;
    }

    public String getDocToCompanyLike(){
	    if (docToCompanyLike != null && docToCompanyLike.trim().length() > 0) {
		if (!docToCompanyLike.startsWith("%")) {
		    docToCompanyLike = "%" + docToCompanyLike;
		}
		if (!docToCompanyLike.endsWith("%")) {
		   docToCompanyLike = docToCompanyLike + "%";
		}
	    }
	return docToCompanyLike;
    }

    public List<String> getDocToCompanys(){
	return docToCompanys;
    }


    public String getDocCCCompany(){
        return docCCCompany;
    }

    public String getDocCCCompanyLike(){
	    if (docCCCompanyLike != null && docCCCompanyLike.trim().length() > 0) {
		if (!docCCCompanyLike.startsWith("%")) {
		    docCCCompanyLike = "%" + docCCCompanyLike;
		}
		if (!docCCCompanyLike.endsWith("%")) {
		   docCCCompanyLike = docCCCompanyLike + "%";
		}
	    }
	return docCCCompanyLike;
    }

    public List<String> getDocCCCompanys(){
	return docCCCompanys;
    }


    public String getCheckOpinion(){
        return checkOpinion;
    }

    public String getCheckOpinionLike(){
	    if (checkOpinionLike != null && checkOpinionLike.trim().length() > 0) {
		if (!checkOpinionLike.startsWith("%")) {
		    checkOpinionLike = "%" + checkOpinionLike;
		}
		if (!checkOpinionLike.endsWith("%")) {
		   checkOpinionLike = checkOpinionLike + "%";
		}
	    }
	return checkOpinionLike;
    }

    public List<String> getCheckOpinions(){
	return checkOpinions;
    }


    public String getCountersignOpinion(){
        return countersignOpinion;
    }

    public String getCountersignOpinionLike(){
	    if (countersignOpinionLike != null && countersignOpinionLike.trim().length() > 0) {
		if (!countersignOpinionLike.startsWith("%")) {
		    countersignOpinionLike = "%" + countersignOpinionLike;
		}
		if (!countersignOpinionLike.endsWith("%")) {
		   countersignOpinionLike = countersignOpinionLike + "%";
		}
	    }
	return countersignOpinionLike;
    }

    public List<String> getCountersignOpinions(){
	return countersignOpinions;
    }


    public String getSignAndIssueOpinion(){
        return signAndIssueOpinion;
    }

    public String getSignAndIssueOpinionLike(){
	    if (signAndIssueOpinionLike != null && signAndIssueOpinionLike.trim().length() > 0) {
		if (!signAndIssueOpinionLike.startsWith("%")) {
		    signAndIssueOpinionLike = "%" + signAndIssueOpinionLike;
		}
		if (!signAndIssueOpinionLike.endsWith("%")) {
		   signAndIssueOpinionLike = signAndIssueOpinionLike + "%";
		}
	    }
	return signAndIssueOpinionLike;
    }

    public List<String> getSignAndIssueOpinions(){
	return signAndIssueOpinions;
    }


    public String getRemark(){
        return remark;
    }

    public String getRemarkLike(){
	    if (remarkLike != null && remarkLike.trim().length() > 0) {
		if (!remarkLike.startsWith("%")) {
		    remarkLike = "%" + remarkLike;
		}
		if (!remarkLike.endsWith("%")) {
		   remarkLike = remarkLike + "%";
		}
	    }
	return remarkLike;
    }

    public List<String> getRemarks(){
	return remarks;
    }


    @Override
	public Integer getStatus(){
        return status;
    }

    @Override
	public Integer getStatusGreaterThanOrEqual(){
        return statusGreaterThanOrEqual;
    }

    @Override
	public Integer getStatusLessThanOrEqual(){
	return statusLessThanOrEqual;
    }

    public List<Integer> getStatuss(){
	return statuss;
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


    @Override
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


 

    public void setSubject(String subject){
        this.subject = subject;
    }

    public void setSubjectLike( String subjectLike){
	this.subjectLike = subjectLike;
    }

    public void setSubjects(List<String> subjects){
        this.subjects = subjects;
    }


    public void setSecurityLevel(Integer securityLevel){
        this.securityLevel = securityLevel;
    }

    public void setSecurityLevelGreaterThanOrEqual(Integer securityLevelGreaterThanOrEqual){
        this.securityLevelGreaterThanOrEqual = securityLevelGreaterThanOrEqual;
    }

    public void setSecurityLevelLessThanOrEqual(Integer securityLevelLessThanOrEqual){
	this.securityLevelLessThanOrEqual = securityLevelLessThanOrEqual;
    }

    public void setSecurityLevels(List<Integer> securityLevels){
        this.securityLevels = securityLevels;
    }


    public void setUrgencyLevel(Integer urgencyLevel){
        this.urgencyLevel = urgencyLevel;
    }

    public void setUrgencyLevelGreaterThanOrEqual(Integer urgencyLevelGreaterThanOrEqual){
        this.urgencyLevelGreaterThanOrEqual = urgencyLevelGreaterThanOrEqual;
    }

    public void setUrgencyLevelLessThanOrEqual(Integer urgencyLevelLessThanOrEqual){
	this.urgencyLevelLessThanOrEqual = urgencyLevelLessThanOrEqual;
    }

    public void setUrgencyLevels(List<Integer> urgencyLevels){
        this.urgencyLevels = urgencyLevels;
    }


    public void setDocNo(String docNo){
        this.docNo = docNo;
    }

    public void setDocNoLike( String docNoLike){
	this.docNoLike = docNoLike;
    }

    public void setDocNos(List<String> docNos){
        this.docNos = docNos;
    }


    public void setDocType(String docType){
        this.docType = docType;
    }

    public void setDocTypeLike( String docTypeLike){
	this.docTypeLike = docTypeLike;
    }

    public void setDocTypes(List<String> docTypes){
        this.docTypes = docTypes;
    }


    public void setDraftName(String draftName){
        this.draftName = draftName;
    }

    public void setDraftNameLike( String draftNameLike){
	this.draftNameLike = draftNameLike;
    }

    public void setDraftNames(List<String> draftNames){
        this.draftNames = draftNames;
    }


    public void setDraftDateGreaterThanOrEqual(Date draftDateGreaterThanOrEqual){
        this.draftDateGreaterThanOrEqual = draftDateGreaterThanOrEqual;
    }

    public void setDraftDateLessThanOrEqual(Date draftDateLessThanOrEqual){
	this.draftDateLessThanOrEqual = draftDateLessThanOrEqual;
    }


    public void setSendDocDateGreaterThanOrEqual(Date sendDocDateGreaterThanOrEqual){
        this.sendDocDateGreaterThanOrEqual = sendDocDateGreaterThanOrEqual;
    }

    public void setSendDocDateLessThanOrEqual(Date sendDocDateLessThanOrEqual){
	this.sendDocDateLessThanOrEqual = sendDocDateLessThanOrEqual;
    }


    public void setSerialNumber(String serialNumber){
        this.serialNumber = serialNumber;
    }

    public void setSerialNumberLike( String serialNumberLike){
	this.serialNumberLike = serialNumberLike;
    }

    public void setSerialNumbers(List<String> serialNumbers){
        this.serialNumbers = serialNumbers;
    }


    public void setFromCompany(String fromCompany){
        this.fromCompany = fromCompany;
    }

    public void setFromCompanyLike( String fromCompanyLike){
	this.fromCompanyLike = fromCompanyLike;
    }

    public void setFromCompanys(List<String> fromCompanys){
        this.fromCompanys = fromCompanys;
    }


    public void setKeywords(String keywords){
        this.keywords = keywords;
    }

    public void setKeywordsLike( String keywordsLike){
	this.keywordsLike = keywordsLike;
    }

    public void setKeywordss(List<String> keywordss){
        this.keywordss = keywordss;
    }


    public void setDocToCompany(String docToCompany){
        this.docToCompany = docToCompany;
    }

    public void setDocToCompanyLike( String docToCompanyLike){
	this.docToCompanyLike = docToCompanyLike;
    }

    public void setDocToCompanys(List<String> docToCompanys){
        this.docToCompanys = docToCompanys;
    }


    public void setDocCCCompany(String docCCCompany){
        this.docCCCompany = docCCCompany;
    }

    public void setDocCCCompanyLike( String docCCCompanyLike){
	this.docCCCompanyLike = docCCCompanyLike;
    }

    public void setDocCCCompanys(List<String> docCCCompanys){
        this.docCCCompanys = docCCCompanys;
    }


    public void setCheckOpinion(String checkOpinion){
        this.checkOpinion = checkOpinion;
    }

    public void setCheckOpinionLike( String checkOpinionLike){
	this.checkOpinionLike = checkOpinionLike;
    }

    public void setCheckOpinions(List<String> checkOpinions){
        this.checkOpinions = checkOpinions;
    }


    public void setCountersignOpinion(String countersignOpinion){
        this.countersignOpinion = countersignOpinion;
    }

    public void setCountersignOpinionLike( String countersignOpinionLike){
	this.countersignOpinionLike = countersignOpinionLike;
    }

    public void setCountersignOpinions(List<String> countersignOpinions){
        this.countersignOpinions = countersignOpinions;
    }


    public void setSignAndIssueOpinion(String signAndIssueOpinion){
        this.signAndIssueOpinion = signAndIssueOpinion;
    }

    public void setSignAndIssueOpinionLike( String signAndIssueOpinionLike){
	this.signAndIssueOpinionLike = signAndIssueOpinionLike;
    }

    public void setSignAndIssueOpinions(List<String> signAndIssueOpinions){
        this.signAndIssueOpinions = signAndIssueOpinions;
    }


    public void setRemark(String remark){
        this.remark = remark;
    }

    public void setRemarkLike( String remarkLike){
	this.remarkLike = remarkLike;
    }

    public void setRemarks(List<String> remarks){
        this.remarks = remarks;
    }


    @Override
	public void setStatus(Integer status){
        this.status = status;
    }

    @Override
	public void setStatusGreaterThanOrEqual(Integer statusGreaterThanOrEqual){
        this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
    }

    @Override
	public void setStatusLessThanOrEqual(Integer statusLessThanOrEqual){
	this.statusLessThanOrEqual = statusLessThanOrEqual;
    }

    public void setStatuss(List<Integer> statuss){
        this.statuss = statuss;
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


    @Override
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




    public SendDocApplyQuery subject(String subject){
	if (subject == null) {
	    throw new RuntimeException("subject is null");
        }         
	this.subject = subject;
	return this;
    }

    public SendDocApplyQuery subjectLike( String subjectLike){
        if (subjectLike == null) {
            throw new RuntimeException("subject is null");
        }
        this.subjectLike = subjectLike;
        return this;
    }

    public SendDocApplyQuery subjects(List<String> subjects){
        if (subjects == null) {
            throw new RuntimeException("subjects is empty ");
        }
        this.subjects = subjects;
        return this;
    }


    public SendDocApplyQuery securityLevel(Integer securityLevel){
	if (securityLevel == null) {
            throw new RuntimeException("securityLevel is null");
        }         
	this.securityLevel = securityLevel;
	return this;
    }

    public SendDocApplyQuery securityLevelGreaterThanOrEqual(Integer securityLevelGreaterThanOrEqual){
	if (securityLevelGreaterThanOrEqual == null) {
	    throw new RuntimeException("securityLevel is null");
        }         
	this.securityLevelGreaterThanOrEqual = securityLevelGreaterThanOrEqual;
        return this;
    }

    public SendDocApplyQuery securityLevelLessThanOrEqual(Integer securityLevelLessThanOrEqual){
        if (securityLevelLessThanOrEqual == null) {
            throw new RuntimeException("securityLevel is null");
        }
        this.securityLevelLessThanOrEqual = securityLevelLessThanOrEqual;
        return this;
    }

    public SendDocApplyQuery securityLevels(List<Integer> securityLevels){
        if (securityLevels == null) {
            throw new RuntimeException("securityLevels is empty ");
        }
        this.securityLevels = securityLevels;
        return this;
    }


    public SendDocApplyQuery urgencyLevel(Integer urgencyLevel){
	if (urgencyLevel == null) {
            throw new RuntimeException("urgencyLevel is null");
        }         
	this.urgencyLevel = urgencyLevel;
	return this;
    }

    public SendDocApplyQuery urgencyLevelGreaterThanOrEqual(Integer urgencyLevelGreaterThanOrEqual){
	if (urgencyLevelGreaterThanOrEqual == null) {
	    throw new RuntimeException("urgencyLevel is null");
        }         
	this.urgencyLevelGreaterThanOrEqual = urgencyLevelGreaterThanOrEqual;
        return this;
    }

    public SendDocApplyQuery urgencyLevelLessThanOrEqual(Integer urgencyLevelLessThanOrEqual){
        if (urgencyLevelLessThanOrEqual == null) {
            throw new RuntimeException("urgencyLevel is null");
        }
        this.urgencyLevelLessThanOrEqual = urgencyLevelLessThanOrEqual;
        return this;
    }

    public SendDocApplyQuery urgencyLevels(List<Integer> urgencyLevels){
        if (urgencyLevels == null) {
            throw new RuntimeException("urgencyLevels is empty ");
        }
        this.urgencyLevels = urgencyLevels;
        return this;
    }


    public SendDocApplyQuery docNo(String docNo){
	if (docNo == null) {
	    throw new RuntimeException("docNo is null");
        }         
	this.docNo = docNo;
	return this;
    }

    public SendDocApplyQuery docNoLike( String docNoLike){
        if (docNoLike == null) {
            throw new RuntimeException("docNo is null");
        }
        this.docNoLike = docNoLike;
        return this;
    }

    public SendDocApplyQuery docNos(List<String> docNos){
        if (docNos == null) {
            throw new RuntimeException("docNos is empty ");
        }
        this.docNos = docNos;
        return this;
    }


    public SendDocApplyQuery docType(String docType){
	if (docType == null) {
	    throw new RuntimeException("docType is null");
        }         
	this.docType = docType;
	return this;
    }

    public SendDocApplyQuery docTypeLike( String docTypeLike){
        if (docTypeLike == null) {
            throw new RuntimeException("docType is null");
        }
        this.docTypeLike = docTypeLike;
        return this;
    }

    public SendDocApplyQuery docTypes(List<String> docTypes){
        if (docTypes == null) {
            throw new RuntimeException("docTypes is empty ");
        }
        this.docTypes = docTypes;
        return this;
    }


    public SendDocApplyQuery draftName(String draftName){
	if (draftName == null) {
	    throw new RuntimeException("draftName is null");
        }         
	this.draftName = draftName;
	return this;
    }

    public SendDocApplyQuery draftNameLike( String draftNameLike){
        if (draftNameLike == null) {
            throw new RuntimeException("draftName is null");
        }
        this.draftNameLike = draftNameLike;
        return this;
    }

    public SendDocApplyQuery draftNames(List<String> draftNames){
        if (draftNames == null) {
            throw new RuntimeException("draftNames is empty ");
        }
        this.draftNames = draftNames;
        return this;
    }



    public SendDocApplyQuery draftDateGreaterThanOrEqual(Date draftDateGreaterThanOrEqual){
	if (draftDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("draftDate is null");
        }         
	this.draftDateGreaterThanOrEqual = draftDateGreaterThanOrEqual;
        return this;
    }

    public SendDocApplyQuery draftDateLessThanOrEqual(Date draftDateLessThanOrEqual){
        if (draftDateLessThanOrEqual == null) {
            throw new RuntimeException("draftDate is null");
        }
        this.draftDateLessThanOrEqual = draftDateLessThanOrEqual;
        return this;
    }




    public SendDocApplyQuery sendDocDateGreaterThanOrEqual(Date sendDocDateGreaterThanOrEqual){
	if (sendDocDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("sendDocDate is null");
        }         
	this.sendDocDateGreaterThanOrEqual = sendDocDateGreaterThanOrEqual;
        return this;
    }

    public SendDocApplyQuery sendDocDateLessThanOrEqual(Date sendDocDateLessThanOrEqual){
        if (sendDocDateLessThanOrEqual == null) {
            throw new RuntimeException("sendDocDate is null");
        }
        this.sendDocDateLessThanOrEqual = sendDocDateLessThanOrEqual;
        return this;
    }



    public SendDocApplyQuery serialNumber(String serialNumber){
	if (serialNumber == null) {
	    throw new RuntimeException("serialNumber is null");
        }         
	this.serialNumber = serialNumber;
	return this;
    }

    public SendDocApplyQuery serialNumberLike( String serialNumberLike){
        if (serialNumberLike == null) {
            throw new RuntimeException("serialNumber is null");
        }
        this.serialNumberLike = serialNumberLike;
        return this;
    }

    public SendDocApplyQuery serialNumbers(List<String> serialNumbers){
        if (serialNumbers == null) {
            throw new RuntimeException("serialNumbers is empty ");
        }
        this.serialNumbers = serialNumbers;
        return this;
    }


    public SendDocApplyQuery fromCompany(String fromCompany){
	if (fromCompany == null) {
	    throw new RuntimeException("fromCompany is null");
        }         
	this.fromCompany = fromCompany;
	return this;
    }

    public SendDocApplyQuery fromCompanyLike( String fromCompanyLike){
        if (fromCompanyLike == null) {
            throw new RuntimeException("fromCompany is null");
        }
        this.fromCompanyLike = fromCompanyLike;
        return this;
    }

    public SendDocApplyQuery fromCompanys(List<String> fromCompanys){
        if (fromCompanys == null) {
            throw new RuntimeException("fromCompanys is empty ");
        }
        this.fromCompanys = fromCompanys;
        return this;
    }


    public SendDocApplyQuery keywords(String keywords){
	if (keywords == null) {
	    throw new RuntimeException("keywords is null");
        }         
	this.keywords = keywords;
	return this;
    }

    public SendDocApplyQuery keywordsLike( String keywordsLike){
        if (keywordsLike == null) {
            throw new RuntimeException("keywords is null");
        }
        this.keywordsLike = keywordsLike;
        return this;
    }

    public SendDocApplyQuery keywordss(List<String> keywordss){
        if (keywordss == null) {
            throw new RuntimeException("keywordss is empty ");
        }
        this.keywordss = keywordss;
        return this;
    }


    public SendDocApplyQuery docToCompany(String docToCompany){
	if (docToCompany == null) {
	    throw new RuntimeException("docToCompany is null");
        }         
	this.docToCompany = docToCompany;
	return this;
    }

    public SendDocApplyQuery docToCompanyLike( String docToCompanyLike){
        if (docToCompanyLike == null) {
            throw new RuntimeException("docToCompany is null");
        }
        this.docToCompanyLike = docToCompanyLike;
        return this;
    }

    public SendDocApplyQuery docToCompanys(List<String> docToCompanys){
        if (docToCompanys == null) {
            throw new RuntimeException("docToCompanys is empty ");
        }
        this.docToCompanys = docToCompanys;
        return this;
    }


    public SendDocApplyQuery docCCCompany(String docCCCompany){
	if (docCCCompany == null) {
	    throw new RuntimeException("docCCCompany is null");
        }         
	this.docCCCompany = docCCCompany;
	return this;
    }

    public SendDocApplyQuery docCCCompanyLike( String docCCCompanyLike){
        if (docCCCompanyLike == null) {
            throw new RuntimeException("docCCCompany is null");
        }
        this.docCCCompanyLike = docCCCompanyLike;
        return this;
    }

    public SendDocApplyQuery docCCCompanys(List<String> docCCCompanys){
        if (docCCCompanys == null) {
            throw new RuntimeException("docCCCompanys is empty ");
        }
        this.docCCCompanys = docCCCompanys;
        return this;
    }


    public SendDocApplyQuery checkOpinion(String checkOpinion){
	if (checkOpinion == null) {
	    throw new RuntimeException("checkOpinion is null");
        }         
	this.checkOpinion = checkOpinion;
	return this;
    }

    public SendDocApplyQuery checkOpinionLike( String checkOpinionLike){
        if (checkOpinionLike == null) {
            throw new RuntimeException("checkOpinion is null");
        }
        this.checkOpinionLike = checkOpinionLike;
        return this;
    }

    public SendDocApplyQuery checkOpinions(List<String> checkOpinions){
        if (checkOpinions == null) {
            throw new RuntimeException("checkOpinions is empty ");
        }
        this.checkOpinions = checkOpinions;
        return this;
    }


    public SendDocApplyQuery countersignOpinion(String countersignOpinion){
	if (countersignOpinion == null) {
	    throw new RuntimeException("countersignOpinion is null");
        }         
	this.countersignOpinion = countersignOpinion;
	return this;
    }

    public SendDocApplyQuery countersignOpinionLike( String countersignOpinionLike){
        if (countersignOpinionLike == null) {
            throw new RuntimeException("countersignOpinion is null");
        }
        this.countersignOpinionLike = countersignOpinionLike;
        return this;
    }

    public SendDocApplyQuery countersignOpinions(List<String> countersignOpinions){
        if (countersignOpinions == null) {
            throw new RuntimeException("countersignOpinions is empty ");
        }
        this.countersignOpinions = countersignOpinions;
        return this;
    }


    public SendDocApplyQuery signAndIssueOpinion(String signAndIssueOpinion){
	if (signAndIssueOpinion == null) {
	    throw new RuntimeException("signAndIssueOpinion is null");
        }         
	this.signAndIssueOpinion = signAndIssueOpinion;
	return this;
    }

    public SendDocApplyQuery signAndIssueOpinionLike( String signAndIssueOpinionLike){
        if (signAndIssueOpinionLike == null) {
            throw new RuntimeException("signAndIssueOpinion is null");
        }
        this.signAndIssueOpinionLike = signAndIssueOpinionLike;
        return this;
    }

    public SendDocApplyQuery signAndIssueOpinions(List<String> signAndIssueOpinions){
        if (signAndIssueOpinions == null) {
            throw new RuntimeException("signAndIssueOpinions is empty ");
        }
        this.signAndIssueOpinions = signAndIssueOpinions;
        return this;
    }


    public SendDocApplyQuery remark(String remark){
	if (remark == null) {
	    throw new RuntimeException("remark is null");
        }         
	this.remark = remark;
	return this;
    }

    public SendDocApplyQuery remarkLike( String remarkLike){
        if (remarkLike == null) {
            throw new RuntimeException("remark is null");
        }
        this.remarkLike = remarkLike;
        return this;
    }

    public SendDocApplyQuery remarks(List<String> remarks){
        if (remarks == null) {
            throw new RuntimeException("remarks is empty ");
        }
        this.remarks = remarks;
        return this;
    }


    @Override
	public SendDocApplyQuery status(Integer status){
	if (status == null) {
            throw new RuntimeException("status is null");
        }         
	this.status = status;
	return this;
    }

    @Override
	public SendDocApplyQuery statusGreaterThanOrEqual(Integer statusGreaterThanOrEqual){
	if (statusGreaterThanOrEqual == null) {
	    throw new RuntimeException("status is null");
        }         
	this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
        return this;
    }

    @Override
	public SendDocApplyQuery statusLessThanOrEqual(Integer statusLessThanOrEqual){
        if (statusLessThanOrEqual == null) {
            throw new RuntimeException("status is null");
        }
        this.statusLessThanOrEqual = statusLessThanOrEqual;
        return this;
    }

    public SendDocApplyQuery statuss(List<Integer> statuss){
        if (statuss == null) {
            throw new RuntimeException("statuss is empty ");
        }
        this.statuss = statuss;
        return this;
    }



    public SendDocApplyQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
	if (createDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDate is null");
        }         
	this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
        return this;
    }

    public SendDocApplyQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual){
        if (createDateLessThanOrEqual == null) {
            throw new RuntimeException("createDate is null");
        }
        this.createDateLessThanOrEqual = createDateLessThanOrEqual;
        return this;
    }




    public SendDocApplyQuery updateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual){
	if (updateDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateDate is null");
        }         
	this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
        return this;
    }

    public SendDocApplyQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual){
        if (updateDateLessThanOrEqual == null) {
            throw new RuntimeException("updateDate is null");
        }
        this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
        return this;
    }



    @Override
	public SendDocApplyQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public SendDocApplyQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public SendDocApplyQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }


    public SendDocApplyQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public SendDocApplyQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public SendDocApplyQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    @Override
	public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("subject".equals(sortColumn)) {
                orderBy = "E.SUBJECT" + a_x;
            } 

            if ("securityLevel".equals(sortColumn)) {
                orderBy = "E.SECURITYLEVEL" + a_x;
            } 

            if ("urgencyLevel".equals(sortColumn)) {
                orderBy = "E.URGENCYLEVEL" + a_x;
            } 

            if ("docNo".equals(sortColumn)) {
                orderBy = "E.DOCNO" + a_x;
            } 

            if ("docType".equals(sortColumn)) {
                orderBy = "E.DOCTYPE" + a_x;
            } 

            if ("draftName".equals(sortColumn)) {
                orderBy = "E.DRAFTNAME" + a_x;
            } 

            if ("draftDate".equals(sortColumn)) {
                orderBy = "E.DRAFTDATE" + a_x;
            } 

            if ("sendDocDate".equals(sortColumn)) {
                orderBy = "E.SENDDOCDATE" + a_x;
            } 

            if ("serialNumber".equals(sortColumn)) {
                orderBy = "E.SERIALNUMBER" + a_x;
            } 

            if ("fromCompany".equals(sortColumn)) {
                orderBy = "E.FROMCOMPANY" + a_x;
            } 

            if ("keywords".equals(sortColumn)) {
                orderBy = "E.KEYWORDS" + a_x;
            } 

            if ("docToCompany".equals(sortColumn)) {
                orderBy = "E.DOCTOCOMPANY" + a_x;
            } 

            if ("docCCCompany".equals(sortColumn)) {
                orderBy = "E.DOCCCCOMPANY" + a_x;
            } 

            if ("checkOpinion".equals(sortColumn)) {
                orderBy = "E.CHECKOPINION" + a_x;
            } 

            if ("countersignOpinion".equals(sortColumn)) {
                orderBy = "E.COUNTERSIGNOPINION" + a_x;
            } 

            if ("signAndIssueOpinion".equals(sortColumn)) {
                orderBy = "E.SIGNANDISSUEOPINION" + a_x;
            } 

            if ("remark".equals(sortColumn)) {
                orderBy = "E.REMARK" + a_x;
            } 

            if ("status".equals(sortColumn)) {
                orderBy = "E.STATUS" + a_x;
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

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID");
        addColumn("subject", "SUBJECT");
        addColumn("securityLevel", "SECURITYLEVEL");
        addColumn("urgencyLevel", "URGENCYLEVEL");
        addColumn("docNo", "DOCNO");
        addColumn("docType", "DOCTYPE");
        addColumn("draftName", "DRAFTNAME");
        addColumn("draftDate", "DRAFTDATE");
        addColumn("sendDocDate", "SENDDOCDATE");
        addColumn("serialNumber", "SERIALNUMBER");
        addColumn("fromCompany", "FROMCOMPANY");
        addColumn("keywords", "KEYWORDS");
        addColumn("docToCompany", "DOCTOCOMPANY");
        addColumn("docCCCompany", "DOCCCCOMPANY");
        addColumn("checkOpinion", "CHECKOPINION");
        addColumn("countersignOpinion", "COUNTERSIGNOPINION");
        addColumn("signAndIssueOpinion", "SIGNANDISSUEOPINION");
        addColumn("remark", "REMARK");
        addColumn("status", "STATUS");
        addColumn("createDate", "CREATEDATE");
        addColumn("updateDate", "UPDATEDATE");
        addColumn("createBy", "CREATEBY");
        addColumn("updateBy", "UPDATEBY");
    }

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

}