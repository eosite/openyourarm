package com.glaf.oa.doc.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ReceiveDocApplyQuery extends DataQuery {
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
        protected Date receiveDocTimeGreaterThanOrEqual;
  	protected Date receiveDocTimeLessThanOrEqual;
  	protected String docType;
  	protected String docTypeLike;
  	protected List<String> docTypes;
  	protected String fromCompany;
  	protected String fromCompanyLike;
  	protected List<String> fromCompanys;
  	protected String serialNumber;
  	protected String serialNumberLike;
  	protected List<String> serialNumbers;
  	protected String fromDocNo;
  	protected String fromDocNoLike;
  	protected List<String> fromDocNos;
  	protected String receiveDocNo;
  	protected String receiveDocNoLike;
  	protected List<String> receiveDocNos;
  	protected String distributeCompany;
  	protected String distributeCompanyLike;
  	protected List<String> distributeCompanys;
  	protected String nibanOption;
  	protected String nibanOptionLike;
  	protected List<String> nibanOptions;
  	protected String leadOption;
  	protected String leadOptionLike;
  	protected List<String> leadOptions;
  	protected String chengbanOption;
  	protected String chengbanOptionLike;
  	protected List<String> chengbanOptions;
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

    public ReceiveDocApplyQuery() {

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

    public Date getReceiveDocTimeGreaterThanOrEqual(){
        return receiveDocTimeGreaterThanOrEqual;
    }

    public Date getReceiveDocTimeLessThanOrEqual(){
	return receiveDocTimeLessThanOrEqual;
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


    public String getFromDocNo(){
        return fromDocNo;
    }

    public String getFromDocNoLike(){
	    if (fromDocNoLike != null && fromDocNoLike.trim().length() > 0) {
		if (!fromDocNoLike.startsWith("%")) {
		    fromDocNoLike = "%" + fromDocNoLike;
		}
		if (!fromDocNoLike.endsWith("%")) {
		   fromDocNoLike = fromDocNoLike + "%";
		}
	    }
	return fromDocNoLike;
    }

    public List<String> getFromDocNos(){
	return fromDocNos;
    }


    public String getReceiveDocNo(){
        return receiveDocNo;
    }

    public String getReceiveDocNoLike(){
	    if (receiveDocNoLike != null && receiveDocNoLike.trim().length() > 0) {
		if (!receiveDocNoLike.startsWith("%")) {
		    receiveDocNoLike = "%" + receiveDocNoLike;
		}
		if (!receiveDocNoLike.endsWith("%")) {
		   receiveDocNoLike = receiveDocNoLike + "%";
		}
	    }
	return receiveDocNoLike;
    }

    public List<String> getReceiveDocNos(){
	return receiveDocNos;
    }


    public String getDistributeCompany(){
        return distributeCompany;
    }

    public String getDistributeCompanyLike(){
	    if (distributeCompanyLike != null && distributeCompanyLike.trim().length() > 0) {
		if (!distributeCompanyLike.startsWith("%")) {
		    distributeCompanyLike = "%" + distributeCompanyLike;
		}
		if (!distributeCompanyLike.endsWith("%")) {
		   distributeCompanyLike = distributeCompanyLike + "%";
		}
	    }
	return distributeCompanyLike;
    }

    public List<String> getDistributeCompanys(){
	return distributeCompanys;
    }


    public String getNibanOption(){
        return nibanOption;
    }

    public String getNibanOptionLike(){
	    if (nibanOptionLike != null && nibanOptionLike.trim().length() > 0) {
		if (!nibanOptionLike.startsWith("%")) {
		    nibanOptionLike = "%" + nibanOptionLike;
		}
		if (!nibanOptionLike.endsWith("%")) {
		   nibanOptionLike = nibanOptionLike + "%";
		}
	    }
	return nibanOptionLike;
    }

    public List<String> getNibanOptions(){
	return nibanOptions;
    }


    public String getLeadOption(){
        return leadOption;
    }

    public String getLeadOptionLike(){
	    if (leadOptionLike != null && leadOptionLike.trim().length() > 0) {
		if (!leadOptionLike.startsWith("%")) {
		    leadOptionLike = "%" + leadOptionLike;
		}
		if (!leadOptionLike.endsWith("%")) {
		   leadOptionLike = leadOptionLike + "%";
		}
	    }
	return leadOptionLike;
    }

    public List<String> getLeadOptions(){
	return leadOptions;
    }


    public String getChengbanOption(){
        return chengbanOption;
    }

    public String getChengbanOptionLike(){
	    if (chengbanOptionLike != null && chengbanOptionLike.trim().length() > 0) {
		if (!chengbanOptionLike.startsWith("%")) {
		    chengbanOptionLike = "%" + chengbanOptionLike;
		}
		if (!chengbanOptionLike.endsWith("%")) {
		   chengbanOptionLike = chengbanOptionLike + "%";
		}
	    }
	return chengbanOptionLike;
    }

    public List<String> getChengbanOptions(){
	return chengbanOptions;
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


    public void setReceiveDocTimeGreaterThanOrEqual(Date receiveDocTimeGreaterThanOrEqual){
        this.receiveDocTimeGreaterThanOrEqual = receiveDocTimeGreaterThanOrEqual;
    }

    public void setReceiveDocTimeLessThanOrEqual(Date receiveDocTimeLessThanOrEqual){
	this.receiveDocTimeLessThanOrEqual = receiveDocTimeLessThanOrEqual;
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


    public void setFromCompany(String fromCompany){
        this.fromCompany = fromCompany;
    }

    public void setFromCompanyLike( String fromCompanyLike){
	this.fromCompanyLike = fromCompanyLike;
    }

    public void setFromCompanys(List<String> fromCompanys){
        this.fromCompanys = fromCompanys;
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


    public void setFromDocNo(String fromDocNo){
        this.fromDocNo = fromDocNo;
    }

    public void setFromDocNoLike( String fromDocNoLike){
	this.fromDocNoLike = fromDocNoLike;
    }

    public void setFromDocNos(List<String> fromDocNos){
        this.fromDocNos = fromDocNos;
    }


    public void setReceiveDocNo(String receiveDocNo){
        this.receiveDocNo = receiveDocNo;
    }

    public void setReceiveDocNoLike( String receiveDocNoLike){
	this.receiveDocNoLike = receiveDocNoLike;
    }

    public void setReceiveDocNos(List<String> receiveDocNos){
        this.receiveDocNos = receiveDocNos;
    }


    public void setDistributeCompany(String distributeCompany){
        this.distributeCompany = distributeCompany;
    }

    public void setDistributeCompanyLike( String distributeCompanyLike){
	this.distributeCompanyLike = distributeCompanyLike;
    }

    public void setDistributeCompanys(List<String> distributeCompanys){
        this.distributeCompanys = distributeCompanys;
    }


    public void setNibanOption(String nibanOption){
        this.nibanOption = nibanOption;
    }

    public void setNibanOptionLike( String nibanOptionLike){
	this.nibanOptionLike = nibanOptionLike;
    }

    public void setNibanOptions(List<String> nibanOptions){
        this.nibanOptions = nibanOptions;
    }


    public void setLeadOption(String leadOption){
        this.leadOption = leadOption;
    }

    public void setLeadOptionLike( String leadOptionLike){
	this.leadOptionLike = leadOptionLike;
    }

    public void setLeadOptions(List<String> leadOptions){
        this.leadOptions = leadOptions;
    }


    public void setChengbanOption(String chengbanOption){
        this.chengbanOption = chengbanOption;
    }

    public void setChengbanOptionLike( String chengbanOptionLike){
	this.chengbanOptionLike = chengbanOptionLike;
    }

    public void setChengbanOptions(List<String> chengbanOptions){
        this.chengbanOptions = chengbanOptions;
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




    public ReceiveDocApplyQuery subject(String subject){
	if (subject == null) {
	    throw new RuntimeException("subject is null");
        }         
	this.subject = subject;
	return this;
    }

    public ReceiveDocApplyQuery subjectLike( String subjectLike){
        if (subjectLike == null) {
            throw new RuntimeException("subject is null");
        }
        this.subjectLike = subjectLike;
        return this;
    }

    public ReceiveDocApplyQuery subjects(List<String> subjects){
        if (subjects == null) {
            throw new RuntimeException("subjects is empty ");
        }
        this.subjects = subjects;
        return this;
    }


    public ReceiveDocApplyQuery securityLevel(Integer securityLevel){
	if (securityLevel == null) {
            throw new RuntimeException("securityLevel is null");
        }         
	this.securityLevel = securityLevel;
	return this;
    }

    public ReceiveDocApplyQuery securityLevelGreaterThanOrEqual(Integer securityLevelGreaterThanOrEqual){
	if (securityLevelGreaterThanOrEqual == null) {
	    throw new RuntimeException("securityLevel is null");
        }         
	this.securityLevelGreaterThanOrEqual = securityLevelGreaterThanOrEqual;
        return this;
    }

    public ReceiveDocApplyQuery securityLevelLessThanOrEqual(Integer securityLevelLessThanOrEqual){
        if (securityLevelLessThanOrEqual == null) {
            throw new RuntimeException("securityLevel is null");
        }
        this.securityLevelLessThanOrEqual = securityLevelLessThanOrEqual;
        return this;
    }

    public ReceiveDocApplyQuery securityLevels(List<Integer> securityLevels){
        if (securityLevels == null) {
            throw new RuntimeException("securityLevels is empty ");
        }
        this.securityLevels = securityLevels;
        return this;
    }


    public ReceiveDocApplyQuery urgencyLevel(Integer urgencyLevel){
	if (urgencyLevel == null) {
            throw new RuntimeException("urgencyLevel is null");
        }         
	this.urgencyLevel = urgencyLevel;
	return this;
    }

    public ReceiveDocApplyQuery urgencyLevelGreaterThanOrEqual(Integer urgencyLevelGreaterThanOrEqual){
	if (urgencyLevelGreaterThanOrEqual == null) {
	    throw new RuntimeException("urgencyLevel is null");
        }         
	this.urgencyLevelGreaterThanOrEqual = urgencyLevelGreaterThanOrEqual;
        return this;
    }

    public ReceiveDocApplyQuery urgencyLevelLessThanOrEqual(Integer urgencyLevelLessThanOrEqual){
        if (urgencyLevelLessThanOrEqual == null) {
            throw new RuntimeException("urgencyLevel is null");
        }
        this.urgencyLevelLessThanOrEqual = urgencyLevelLessThanOrEqual;
        return this;
    }

    public ReceiveDocApplyQuery urgencyLevels(List<Integer> urgencyLevels){
        if (urgencyLevels == null) {
            throw new RuntimeException("urgencyLevels is empty ");
        }
        this.urgencyLevels = urgencyLevels;
        return this;
    }



    public ReceiveDocApplyQuery receiveDocTimeGreaterThanOrEqual(Date receiveDocTimeGreaterThanOrEqual){
	if (receiveDocTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("receiveDocTime is null");
        }         
	this.receiveDocTimeGreaterThanOrEqual = receiveDocTimeGreaterThanOrEqual;
        return this;
    }

    public ReceiveDocApplyQuery receiveDocTimeLessThanOrEqual(Date receiveDocTimeLessThanOrEqual){
        if (receiveDocTimeLessThanOrEqual == null) {
            throw new RuntimeException("receiveDocTime is null");
        }
        this.receiveDocTimeLessThanOrEqual = receiveDocTimeLessThanOrEqual;
        return this;
    }



    public ReceiveDocApplyQuery docType(String docType){
	if (docType == null) {
	    throw new RuntimeException("docType is null");
        }         
	this.docType = docType;
	return this;
    }

    public ReceiveDocApplyQuery docTypeLike( String docTypeLike){
        if (docTypeLike == null) {
            throw new RuntimeException("docType is null");
        }
        this.docTypeLike = docTypeLike;
        return this;
    }

    public ReceiveDocApplyQuery docTypes(List<String> docTypes){
        if (docTypes == null) {
            throw new RuntimeException("docTypes is empty ");
        }
        this.docTypes = docTypes;
        return this;
    }


    public ReceiveDocApplyQuery fromCompany(String fromCompany){
	if (fromCompany == null) {
	    throw new RuntimeException("fromCompany is null");
        }         
	this.fromCompany = fromCompany;
	return this;
    }

    public ReceiveDocApplyQuery fromCompanyLike( String fromCompanyLike){
        if (fromCompanyLike == null) {
            throw new RuntimeException("fromCompany is null");
        }
        this.fromCompanyLike = fromCompanyLike;
        return this;
    }

    public ReceiveDocApplyQuery fromCompanys(List<String> fromCompanys){
        if (fromCompanys == null) {
            throw new RuntimeException("fromCompanys is empty ");
        }
        this.fromCompanys = fromCompanys;
        return this;
    }


    public ReceiveDocApplyQuery serialNumber(String serialNumber){
	if (serialNumber == null) {
	    throw new RuntimeException("serialNumber is null");
        }         
	this.serialNumber = serialNumber;
	return this;
    }

    public ReceiveDocApplyQuery serialNumberLike( String serialNumberLike){
        if (serialNumberLike == null) {
            throw new RuntimeException("serialNumber is null");
        }
        this.serialNumberLike = serialNumberLike;
        return this;
    }

    public ReceiveDocApplyQuery serialNumbers(List<String> serialNumbers){
        if (serialNumbers == null) {
            throw new RuntimeException("serialNumbers is empty ");
        }
        this.serialNumbers = serialNumbers;
        return this;
    }


    public ReceiveDocApplyQuery fromDocNo(String fromDocNo){
	if (fromDocNo == null) {
	    throw new RuntimeException("fromDocNo is null");
        }         
	this.fromDocNo = fromDocNo;
	return this;
    }

    public ReceiveDocApplyQuery fromDocNoLike( String fromDocNoLike){
        if (fromDocNoLike == null) {
            throw new RuntimeException("fromDocNo is null");
        }
        this.fromDocNoLike = fromDocNoLike;
        return this;
    }

    public ReceiveDocApplyQuery fromDocNos(List<String> fromDocNos){
        if (fromDocNos == null) {
            throw new RuntimeException("fromDocNos is empty ");
        }
        this.fromDocNos = fromDocNos;
        return this;
    }


    public ReceiveDocApplyQuery receiveDocNo(String receiveDocNo){
	if (receiveDocNo == null) {
	    throw new RuntimeException("receiveDocNo is null");
        }         
	this.receiveDocNo = receiveDocNo;
	return this;
    }

    public ReceiveDocApplyQuery receiveDocNoLike( String receiveDocNoLike){
        if (receiveDocNoLike == null) {
            throw new RuntimeException("receiveDocNo is null");
        }
        this.receiveDocNoLike = receiveDocNoLike;
        return this;
    }

    public ReceiveDocApplyQuery receiveDocNos(List<String> receiveDocNos){
        if (receiveDocNos == null) {
            throw new RuntimeException("receiveDocNos is empty ");
        }
        this.receiveDocNos = receiveDocNos;
        return this;
    }


    public ReceiveDocApplyQuery distributeCompany(String distributeCompany){
	if (distributeCompany == null) {
	    throw new RuntimeException("distributeCompany is null");
        }         
	this.distributeCompany = distributeCompany;
	return this;
    }

    public ReceiveDocApplyQuery distributeCompanyLike( String distributeCompanyLike){
        if (distributeCompanyLike == null) {
            throw new RuntimeException("distributeCompany is null");
        }
        this.distributeCompanyLike = distributeCompanyLike;
        return this;
    }

    public ReceiveDocApplyQuery distributeCompanys(List<String> distributeCompanys){
        if (distributeCompanys == null) {
            throw new RuntimeException("distributeCompanys is empty ");
        }
        this.distributeCompanys = distributeCompanys;
        return this;
    }


    public ReceiveDocApplyQuery nibanOption(String nibanOption){
	if (nibanOption == null) {
	    throw new RuntimeException("nibanOption is null");
        }         
	this.nibanOption = nibanOption;
	return this;
    }

    public ReceiveDocApplyQuery nibanOptionLike( String nibanOptionLike){
        if (nibanOptionLike == null) {
            throw new RuntimeException("nibanOption is null");
        }
        this.nibanOptionLike = nibanOptionLike;
        return this;
    }

    public ReceiveDocApplyQuery nibanOptions(List<String> nibanOptions){
        if (nibanOptions == null) {
            throw new RuntimeException("nibanOptions is empty ");
        }
        this.nibanOptions = nibanOptions;
        return this;
    }


    public ReceiveDocApplyQuery leadOption(String leadOption){
	if (leadOption == null) {
	    throw new RuntimeException("leadOption is null");
        }         
	this.leadOption = leadOption;
	return this;
    }

    public ReceiveDocApplyQuery leadOptionLike( String leadOptionLike){
        if (leadOptionLike == null) {
            throw new RuntimeException("leadOption is null");
        }
        this.leadOptionLike = leadOptionLike;
        return this;
    }

    public ReceiveDocApplyQuery leadOptions(List<String> leadOptions){
        if (leadOptions == null) {
            throw new RuntimeException("leadOptions is empty ");
        }
        this.leadOptions = leadOptions;
        return this;
    }


    public ReceiveDocApplyQuery chengbanOption(String chengbanOption){
	if (chengbanOption == null) {
	    throw new RuntimeException("chengbanOption is null");
        }         
	this.chengbanOption = chengbanOption;
	return this;
    }

    public ReceiveDocApplyQuery chengbanOptionLike( String chengbanOptionLike){
        if (chengbanOptionLike == null) {
            throw new RuntimeException("chengbanOption is null");
        }
        this.chengbanOptionLike = chengbanOptionLike;
        return this;
    }

    public ReceiveDocApplyQuery chengbanOptions(List<String> chengbanOptions){
        if (chengbanOptions == null) {
            throw new RuntimeException("chengbanOptions is empty ");
        }
        this.chengbanOptions = chengbanOptions;
        return this;
    }


    public ReceiveDocApplyQuery remark(String remark){
	if (remark == null) {
	    throw new RuntimeException("remark is null");
        }         
	this.remark = remark;
	return this;
    }

    public ReceiveDocApplyQuery remarkLike( String remarkLike){
        if (remarkLike == null) {
            throw new RuntimeException("remark is null");
        }
        this.remarkLike = remarkLike;
        return this;
    }

    public ReceiveDocApplyQuery remarks(List<String> remarks){
        if (remarks == null) {
            throw new RuntimeException("remarks is empty ");
        }
        this.remarks = remarks;
        return this;
    }


    @Override
	public ReceiveDocApplyQuery status(Integer status){
	if (status == null) {
            throw new RuntimeException("status is null");
        }         
	this.status = status;
	return this;
    }

    @Override
	public ReceiveDocApplyQuery statusGreaterThanOrEqual(Integer statusGreaterThanOrEqual){
	if (statusGreaterThanOrEqual == null) {
	    throw new RuntimeException("status is null");
        }         
	this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
        return this;
    }

    @Override
	public ReceiveDocApplyQuery statusLessThanOrEqual(Integer statusLessThanOrEqual){
        if (statusLessThanOrEqual == null) {
            throw new RuntimeException("status is null");
        }
        this.statusLessThanOrEqual = statusLessThanOrEqual;
        return this;
    }

    public ReceiveDocApplyQuery statuss(List<Integer> statuss){
        if (statuss == null) {
            throw new RuntimeException("statuss is empty ");
        }
        this.statuss = statuss;
        return this;
    }



    public ReceiveDocApplyQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
	if (createDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDate is null");
        }         
	this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
        return this;
    }

    public ReceiveDocApplyQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual){
        if (createDateLessThanOrEqual == null) {
            throw new RuntimeException("createDate is null");
        }
        this.createDateLessThanOrEqual = createDateLessThanOrEqual;
        return this;
    }




    public ReceiveDocApplyQuery updateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual){
	if (updateDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateDate is null");
        }         
	this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
        return this;
    }

    public ReceiveDocApplyQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual){
        if (updateDateLessThanOrEqual == null) {
            throw new RuntimeException("updateDate is null");
        }
        this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
        return this;
    }



    @Override
	public ReceiveDocApplyQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public ReceiveDocApplyQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public ReceiveDocApplyQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }


    public ReceiveDocApplyQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public ReceiveDocApplyQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public ReceiveDocApplyQuery updateBys(List<String> updateBys){
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

            if ("receiveDocTime".equals(sortColumn)) {
                orderBy = "E.RECEIVEDOCTIME" + a_x;
            } 

            if ("docType".equals(sortColumn)) {
                orderBy = "E.DOCTYPE" + a_x;
            } 

            if ("fromCompany".equals(sortColumn)) {
                orderBy = "E.FROMCOMPANY" + a_x;
            } 

            if ("serialNumber".equals(sortColumn)) {
                orderBy = "E.SERIALNUMBER" + a_x;
            } 

            if ("fromDocNo".equals(sortColumn)) {
                orderBy = "E.FROMDOCNO" + a_x;
            } 

            if ("receiveDocNo".equals(sortColumn)) {
                orderBy = "E.RECEIVEDOCNO" + a_x;
            } 

            if ("distributeCompany".equals(sortColumn)) {
                orderBy = "E.DISTRIBUTECOMPANY" + a_x;
            } 

            if ("nibanOption".equals(sortColumn)) {
                orderBy = "E.NIBANOPTION" + a_x;
            } 

            if ("leadOption".equals(sortColumn)) {
                orderBy = "E.LEADOPTION" + a_x;
            } 

            if ("chengbanOption".equals(sortColumn)) {
                orderBy = "E.CHENGBANOPTION" + a_x;
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
        addColumn("receiveDocTime", "RECEIVEDOCTIME");
        addColumn("docType", "DOCTYPE");
        addColumn("fromCompany", "FROMCOMPANY");
        addColumn("serialNumber", "SERIALNUMBER");
        addColumn("fromDocNo", "FROMDOCNO");
        addColumn("receiveDocNo", "RECEIVEDOCNO");
        addColumn("distributeCompany", "DISTRIBUTECOMPANY");
        addColumn("nibanOption", "NIBANOPTION");
        addColumn("leadOption", "LEADOPTION");
        addColumn("chengbanOption", "CHENGBANOPTION");
        addColumn("remark", "REMARK");
        addColumn("status", "STATUS");
        addColumn("createDate", "CREATEDATE");
        addColumn("updateDate", "UPDATEDATE");
        addColumn("createBy", "CREATEBY");
        addColumn("updateBy", "UPDATEBY");
    }

}