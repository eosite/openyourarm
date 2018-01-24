package com.glaf.workflow.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ProcessInsMappingQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> iDs;
	protected Collection<String> appActorIds;
  	protected Integer srcWbsDefId;
  	protected Integer srcWbsDefIdGreaterThanOrEqual;
  	protected Integer srcWbsDefIdLessThanOrEqual;
  	protected List<Integer> srcWbsDefIds;
  	protected Integer srcWbsInsId;
  	protected Integer srcWbsInsIdGreaterThanOrEqual;
  	protected Integer srcWbsInsIdLessThanOrEqual;
  	protected List<Integer> srcWbsInsIds;
  	protected String srcProcDefId;
  	protected String srcProcDefIdLike;
  	protected List<String> srcProcDefIds;
  	protected String srcProcInsId;
  	protected String srcProcInsIdLike;
  	protected List<String> srcProcInsIds;
  	protected Integer desWbsDefId;
  	protected Integer desWbsDefIdGreaterThanOrEqual;
  	protected Integer desWbsDefIdLessThanOrEqual;
  	protected List<Integer> desWbsDefIds;
  	protected Integer desWbsInsId;
  	protected Integer desWbsInsIdGreaterThanOrEqual;
  	protected Integer desWbsInsIdLessThanOrEqual;
  	protected List<Integer> desWbsInsIds;
  	protected String desProcDefId;
  	protected String desProcDefIdLike;
  	protected List<String> desProcDefIds;
  	protected String desProcInsId;
  	protected String desProcInsIdLike;
  	protected List<String> desProcInsIds;
  	protected String srcSysId;
  	protected String srcSysIdLike;
  	protected List<String> srcSysIds;
  	protected String desSysId;
  	protected String desSysIdLike;
  	protected List<String> desSysIds;
  	protected Integer procStatus;
  	protected Integer procStatusGreaterThanOrEqual;
  	protected Integer procStatusLessThanOrEqual;
  	protected List<Integer> procStatuss;
  	protected Integer procResult;
  	protected Integer procResultGreaterThanOrEqual;
  	protected Integer procResultLessThanOrEqual;
  	protected List<Integer> procResults;
  	protected Integer transType;
  	protected Integer transTypeGreaterThanOrEqual;
  	protected Integer transTypeLessThanOrEqual;
  	protected List<Integer> transTypes;
  	protected Integer replyFlag;
  	protected Integer replyFlagGreaterThanOrEqual;
  	protected Integer replyFlagLessThanOrEqual;
  	protected List<Integer> replyFlags;
        protected Date procStartTimeGreaterThanOrEqual;
  	protected Date procStartTimeLessThanOrEqual;
        protected Date procCompTimeGreaterThanOrEqual;
  	protected Date procCompTimeLessThanOrEqual;

    public ProcessInsMappingQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public Integer getSrcWbsDefId(){
        return srcWbsDefId;
    }

    public Integer getSrcWbsDefIdGreaterThanOrEqual(){
        return srcWbsDefIdGreaterThanOrEqual;
    }

    public Integer getSrcWbsDefIdLessThanOrEqual(){
	return srcWbsDefIdLessThanOrEqual;
    }

    public List<Integer> getSrcWbsDefIds(){
	return srcWbsDefIds;
    }

    public Integer getSrcWbsInsId(){
        return srcWbsInsId;
    }

    public Integer getSrcWbsInsIdGreaterThanOrEqual(){
        return srcWbsInsIdGreaterThanOrEqual;
    }

    public Integer getSrcWbsInsIdLessThanOrEqual(){
	return srcWbsInsIdLessThanOrEqual;
    }

    public List<Integer> getSrcWbsInsIds(){
	return srcWbsInsIds;
    }

    public String getSrcProcDefId(){
        return srcProcDefId;
    }

    public String getSrcProcDefIdLike(){
	    if (srcProcDefIdLike != null && srcProcDefIdLike.trim().length() > 0) {
		if (!srcProcDefIdLike.startsWith("%")) {
		    srcProcDefIdLike = "%" + srcProcDefIdLike;
		}
		if (!srcProcDefIdLike.endsWith("%")) {
		   srcProcDefIdLike = srcProcDefIdLike + "%";
		}
	    }
	return srcProcDefIdLike;
    }

    public List<String> getSrcProcDefIds(){
	return srcProcDefIds;
    }


    public String getSrcProcInsId(){
        return srcProcInsId;
    }

    public String getSrcProcInsIdLike(){
	    if (srcProcInsIdLike != null && srcProcInsIdLike.trim().length() > 0) {
		if (!srcProcInsIdLike.startsWith("%")) {
		    srcProcInsIdLike = "%" + srcProcInsIdLike;
		}
		if (!srcProcInsIdLike.endsWith("%")) {
		   srcProcInsIdLike = srcProcInsIdLike + "%";
		}
	    }
	return srcProcInsIdLike;
    }

    public List<String> getSrcProcInsIds(){
	return srcProcInsIds;
    }


    public Integer getDesWbsDefId(){
        return desWbsDefId;
    }

    public Integer getDesWbsDefIdGreaterThanOrEqual(){
        return desWbsDefIdGreaterThanOrEqual;
    }

    public Integer getDesWbsDefIdLessThanOrEqual(){
	return desWbsDefIdLessThanOrEqual;
    }

    public List<Integer> getDesWbsDefIds(){
	return desWbsDefIds;
    }

    public Integer getDesWbsInsId(){
        return desWbsInsId;
    }

    public Integer getDesWbsInsIdGreaterThanOrEqual(){
        return desWbsInsIdGreaterThanOrEqual;
    }

    public Integer getDesWbsInsIdLessThanOrEqual(){
	return desWbsInsIdLessThanOrEqual;
    }

    public List<Integer> getDesWbsInsIds(){
	return desWbsInsIds;
    }

    public String getDesProcDefId(){
        return desProcDefId;
    }

    public String getDesProcDefIdLike(){
	    if (desProcDefIdLike != null && desProcDefIdLike.trim().length() > 0) {
		if (!desProcDefIdLike.startsWith("%")) {
		    desProcDefIdLike = "%" + desProcDefIdLike;
		}
		if (!desProcDefIdLike.endsWith("%")) {
		   desProcDefIdLike = desProcDefIdLike + "%";
		}
	    }
	return desProcDefIdLike;
    }

    public List<String> getDesProcDefIds(){
	return desProcDefIds;
    }


    public String getDesProcInsId(){
        return desProcInsId;
    }

    public String getDesProcInsIdLike(){
	    if (desProcInsIdLike != null && desProcInsIdLike.trim().length() > 0) {
		if (!desProcInsIdLike.startsWith("%")) {
		    desProcInsIdLike = "%" + desProcInsIdLike;
		}
		if (!desProcInsIdLike.endsWith("%")) {
		   desProcInsIdLike = desProcInsIdLike + "%";
		}
	    }
	return desProcInsIdLike;
    }

    public List<String> getDesProcInsIds(){
	return desProcInsIds;
    }


    public String getSrcSysId(){
        return srcSysId;
    }

    public String getSrcSysIdLike(){
	    if (srcSysIdLike != null && srcSysIdLike.trim().length() > 0) {
		if (!srcSysIdLike.startsWith("%")) {
		    srcSysIdLike = "%" + srcSysIdLike;
		}
		if (!srcSysIdLike.endsWith("%")) {
		   srcSysIdLike = srcSysIdLike + "%";
		}
	    }
	return srcSysIdLike;
    }

    public List<String> getSrcSysIds(){
	return srcSysIds;
    }


    public String getDesSysId(){
        return desSysId;
    }

    public String getDesSysIdLike(){
	    if (desSysIdLike != null && desSysIdLike.trim().length() > 0) {
		if (!desSysIdLike.startsWith("%")) {
		    desSysIdLike = "%" + desSysIdLike;
		}
		if (!desSysIdLike.endsWith("%")) {
		   desSysIdLike = desSysIdLike + "%";
		}
	    }
	return desSysIdLike;
    }

    public List<String> getDesSysIds(){
	return desSysIds;
    }


    public Integer getProcStatus(){
        return procStatus;
    }

    public Integer getProcStatusGreaterThanOrEqual(){
        return procStatusGreaterThanOrEqual;
    }

    public Integer getProcStatusLessThanOrEqual(){
	return procStatusLessThanOrEqual;
    }

    public List<Integer> getProcStatuss(){
	return procStatuss;
    }

    public Integer getProcResult(){
        return procResult;
    }

    public Integer getProcResultGreaterThanOrEqual(){
        return procResultGreaterThanOrEqual;
    }

    public Integer getProcResultLessThanOrEqual(){
	return procResultLessThanOrEqual;
    }

    public List<Integer> getProcResults(){
	return procResults;
    }

    public Integer getTransType(){
        return transType;
    }

    public Integer getTransTypeGreaterThanOrEqual(){
        return transTypeGreaterThanOrEqual;
    }

    public Integer getTransTypeLessThanOrEqual(){
	return transTypeLessThanOrEqual;
    }

    public List<Integer> getTransTypes(){
	return transTypes;
    }

    public Integer getReplyFlag(){
        return replyFlag;
    }

    public Integer getReplyFlagGreaterThanOrEqual(){
        return replyFlagGreaterThanOrEqual;
    }

    public Integer getReplyFlagLessThanOrEqual(){
	return replyFlagLessThanOrEqual;
    }

    public List<Integer> getReplyFlags(){
	return replyFlags;
    }

    public Date getProcStartTimeGreaterThanOrEqual(){
        return procStartTimeGreaterThanOrEqual;
    }

    public Date getProcStartTimeLessThanOrEqual(){
	return procStartTimeLessThanOrEqual;
    }


    public Date getProcCompTimeGreaterThanOrEqual(){
        return procCompTimeGreaterThanOrEqual;
    }

    public Date getProcCompTimeLessThanOrEqual(){
	return procCompTimeLessThanOrEqual;
    }


 

    public void setSrcWbsDefId(Integer srcWbsDefId){
        this.srcWbsDefId = srcWbsDefId;
    }

    public void setSrcWbsDefIdGreaterThanOrEqual(Integer srcWbsDefIdGreaterThanOrEqual){
        this.srcWbsDefIdGreaterThanOrEqual = srcWbsDefIdGreaterThanOrEqual;
    }

    public void setSrcWbsDefIdLessThanOrEqual(Integer srcWbsDefIdLessThanOrEqual){
	this.srcWbsDefIdLessThanOrEqual = srcWbsDefIdLessThanOrEqual;
    }

    public void setSrcWbsDefIds(List<Integer> srcWbsDefIds){
        this.srcWbsDefIds = srcWbsDefIds;
    }


    public void setSrcWbsInsId(Integer srcWbsInsId){
        this.srcWbsInsId = srcWbsInsId;
    }

    public void setSrcWbsInsIdGreaterThanOrEqual(Integer srcWbsInsIdGreaterThanOrEqual){
        this.srcWbsInsIdGreaterThanOrEqual = srcWbsInsIdGreaterThanOrEqual;
    }

    public void setSrcWbsInsIdLessThanOrEqual(Integer srcWbsInsIdLessThanOrEqual){
	this.srcWbsInsIdLessThanOrEqual = srcWbsInsIdLessThanOrEqual;
    }

    public void setSrcWbsInsIds(List<Integer> srcWbsInsIds){
        this.srcWbsInsIds = srcWbsInsIds;
    }


    public void setSrcProcDefId(String srcProcDefId){
        this.srcProcDefId = srcProcDefId;
    }

    public void setSrcProcDefIdLike( String srcProcDefIdLike){
	this.srcProcDefIdLike = srcProcDefIdLike;
    }

    public void setSrcProcDefIds(List<String> srcProcDefIds){
        this.srcProcDefIds = srcProcDefIds;
    }


    public void setSrcProcInsId(String srcProcInsId){
        this.srcProcInsId = srcProcInsId;
    }

    public void setSrcProcInsIdLike( String srcProcInsIdLike){
	this.srcProcInsIdLike = srcProcInsIdLike;
    }

    public void setSrcProcInsIds(List<String> srcProcInsIds){
        this.srcProcInsIds = srcProcInsIds;
    }


    public void setDesWbsDefId(Integer desWbsDefId){
        this.desWbsDefId = desWbsDefId;
    }

    public void setDesWbsDefIdGreaterThanOrEqual(Integer desWbsDefIdGreaterThanOrEqual){
        this.desWbsDefIdGreaterThanOrEqual = desWbsDefIdGreaterThanOrEqual;
    }

    public void setDesWbsDefIdLessThanOrEqual(Integer desWbsDefIdLessThanOrEqual){
	this.desWbsDefIdLessThanOrEqual = desWbsDefIdLessThanOrEqual;
    }

    public void setDesWbsDefIds(List<Integer> desWbsDefIds){
        this.desWbsDefIds = desWbsDefIds;
    }


    public void setDesWbsInsId(Integer desWbsInsId){
        this.desWbsInsId = desWbsInsId;
    }

    public void setDesWbsInsIdGreaterThanOrEqual(Integer desWbsInsIdGreaterThanOrEqual){
        this.desWbsInsIdGreaterThanOrEqual = desWbsInsIdGreaterThanOrEqual;
    }

    public void setDesWbsInsIdLessThanOrEqual(Integer desWbsInsIdLessThanOrEqual){
	this.desWbsInsIdLessThanOrEqual = desWbsInsIdLessThanOrEqual;
    }

    public void setDesWbsInsIds(List<Integer> desWbsInsIds){
        this.desWbsInsIds = desWbsInsIds;
    }


    public void setDesProcDefId(String desProcDefId){
        this.desProcDefId = desProcDefId;
    }

    public void setDesProcDefIdLike( String desProcDefIdLike){
	this.desProcDefIdLike = desProcDefIdLike;
    }

    public void setDesProcDefIds(List<String> desProcDefIds){
        this.desProcDefIds = desProcDefIds;
    }


    public void setDesProcInsId(String desProcInsId){
        this.desProcInsId = desProcInsId;
    }

    public void setDesProcInsIdLike( String desProcInsIdLike){
	this.desProcInsIdLike = desProcInsIdLike;
    }

    public void setDesProcInsIds(List<String> desProcInsIds){
        this.desProcInsIds = desProcInsIds;
    }


    public void setSrcSysId(String srcSysId){
        this.srcSysId = srcSysId;
    }

    public void setSrcSysIdLike( String srcSysIdLike){
	this.srcSysIdLike = srcSysIdLike;
    }

    public void setSrcSysIds(List<String> srcSysIds){
        this.srcSysIds = srcSysIds;
    }


    public void setDesSysId(String desSysId){
        this.desSysId = desSysId;
    }

    public void setDesSysIdLike( String desSysIdLike){
	this.desSysIdLike = desSysIdLike;
    }

    public void setDesSysIds(List<String> desSysIds){
        this.desSysIds = desSysIds;
    }


    public void setProcStatus(Integer procStatus){
        this.procStatus = procStatus;
    }

    public void setProcStatusGreaterThanOrEqual(Integer procStatusGreaterThanOrEqual){
        this.procStatusGreaterThanOrEqual = procStatusGreaterThanOrEqual;
    }

    public void setProcStatusLessThanOrEqual(Integer procStatusLessThanOrEqual){
	this.procStatusLessThanOrEqual = procStatusLessThanOrEqual;
    }

    public void setProcStatuss(List<Integer> procStatuss){
        this.procStatuss = procStatuss;
    }


    public void setProcResult(Integer procResult){
        this.procResult = procResult;
    }

    public void setProcResultGreaterThanOrEqual(Integer procResultGreaterThanOrEqual){
        this.procResultGreaterThanOrEqual = procResultGreaterThanOrEqual;
    }

    public void setProcResultLessThanOrEqual(Integer procResultLessThanOrEqual){
	this.procResultLessThanOrEqual = procResultLessThanOrEqual;
    }

    public void setProcResults(List<Integer> procResults){
        this.procResults = procResults;
    }


    public void setTransType(Integer transType){
        this.transType = transType;
    }

    public void setTransTypeGreaterThanOrEqual(Integer transTypeGreaterThanOrEqual){
        this.transTypeGreaterThanOrEqual = transTypeGreaterThanOrEqual;
    }

    public void setTransTypeLessThanOrEqual(Integer transTypeLessThanOrEqual){
	this.transTypeLessThanOrEqual = transTypeLessThanOrEqual;
    }

    public void setTransTypes(List<Integer> transTypes){
        this.transTypes = transTypes;
    }


    public void setReplyFlag(Integer replyFlag){
        this.replyFlag = replyFlag;
    }

    public void setReplyFlagGreaterThanOrEqual(Integer replyFlagGreaterThanOrEqual){
        this.replyFlagGreaterThanOrEqual = replyFlagGreaterThanOrEqual;
    }

    public void setReplyFlagLessThanOrEqual(Integer replyFlagLessThanOrEqual){
	this.replyFlagLessThanOrEqual = replyFlagLessThanOrEqual;
    }

    public void setReplyFlags(List<Integer> replyFlags){
        this.replyFlags = replyFlags;
    }


    public void setProcStartTimeGreaterThanOrEqual(Date procStartTimeGreaterThanOrEqual){
        this.procStartTimeGreaterThanOrEqual = procStartTimeGreaterThanOrEqual;
    }

    public void setProcStartTimeLessThanOrEqual(Date procStartTimeLessThanOrEqual){
	this.procStartTimeLessThanOrEqual = procStartTimeLessThanOrEqual;
    }


    public void setProcCompTimeGreaterThanOrEqual(Date procCompTimeGreaterThanOrEqual){
        this.procCompTimeGreaterThanOrEqual = procCompTimeGreaterThanOrEqual;
    }

    public void setProcCompTimeLessThanOrEqual(Date procCompTimeLessThanOrEqual){
	this.procCompTimeLessThanOrEqual = procCompTimeLessThanOrEqual;
    }




    public ProcessInsMappingQuery srcWbsDefId(Integer srcWbsDefId){
	if (srcWbsDefId == null) {
            throw new RuntimeException("srcWbsDefId is null");
        }         
	this.srcWbsDefId = srcWbsDefId;
	return this;
    }

    public ProcessInsMappingQuery srcWbsDefIdGreaterThanOrEqual(Integer srcWbsDefIdGreaterThanOrEqual){
	if (srcWbsDefIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("srcWbsDefId is null");
        }         
	this.srcWbsDefIdGreaterThanOrEqual = srcWbsDefIdGreaterThanOrEqual;
        return this;
    }

    public ProcessInsMappingQuery srcWbsDefIdLessThanOrEqual(Integer srcWbsDefIdLessThanOrEqual){
        if (srcWbsDefIdLessThanOrEqual == null) {
            throw new RuntimeException("srcWbsDefId is null");
        }
        this.srcWbsDefIdLessThanOrEqual = srcWbsDefIdLessThanOrEqual;
        return this;
    }

    public ProcessInsMappingQuery srcWbsDefIds(List<Integer> srcWbsDefIds){
        if (srcWbsDefIds == null) {
            throw new RuntimeException("srcWbsDefIds is empty ");
        }
        this.srcWbsDefIds = srcWbsDefIds;
        return this;
    }


    public ProcessInsMappingQuery srcWbsInsId(Integer srcWbsInsId){
	if (srcWbsInsId == null) {
            throw new RuntimeException("srcWbsInsId is null");
        }         
	this.srcWbsInsId = srcWbsInsId;
	return this;
    }

    public ProcessInsMappingQuery srcWbsInsIdGreaterThanOrEqual(Integer srcWbsInsIdGreaterThanOrEqual){
	if (srcWbsInsIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("srcWbsInsId is null");
        }         
	this.srcWbsInsIdGreaterThanOrEqual = srcWbsInsIdGreaterThanOrEqual;
        return this;
    }

    public ProcessInsMappingQuery srcWbsInsIdLessThanOrEqual(Integer srcWbsInsIdLessThanOrEqual){
        if (srcWbsInsIdLessThanOrEqual == null) {
            throw new RuntimeException("srcWbsInsId is null");
        }
        this.srcWbsInsIdLessThanOrEqual = srcWbsInsIdLessThanOrEqual;
        return this;
    }

    public ProcessInsMappingQuery srcWbsInsIds(List<Integer> srcWbsInsIds){
        if (srcWbsInsIds == null) {
            throw new RuntimeException("srcWbsInsIds is empty ");
        }
        this.srcWbsInsIds = srcWbsInsIds;
        return this;
    }


    public ProcessInsMappingQuery srcProcDefId(String srcProcDefId){
	if (srcProcDefId == null) {
	    throw new RuntimeException("srcProcDefId is null");
        }         
	this.srcProcDefId = srcProcDefId;
	return this;
    }

    public ProcessInsMappingQuery srcProcDefIdLike( String srcProcDefIdLike){
        if (srcProcDefIdLike == null) {
            throw new RuntimeException("srcProcDefId is null");
        }
        this.srcProcDefIdLike = srcProcDefIdLike;
        return this;
    }

    public ProcessInsMappingQuery srcProcDefIds(List<String> srcProcDefIds){
        if (srcProcDefIds == null) {
            throw new RuntimeException("srcProcDefIds is empty ");
        }
        this.srcProcDefIds = srcProcDefIds;
        return this;
    }


    public ProcessInsMappingQuery srcProcInsId(String srcProcInsId){
	if (srcProcInsId == null) {
	    throw new RuntimeException("srcProcInsId is null");
        }         
	this.srcProcInsId = srcProcInsId;
	return this;
    }

    public ProcessInsMappingQuery srcProcInsIdLike( String srcProcInsIdLike){
        if (srcProcInsIdLike == null) {
            throw new RuntimeException("srcProcInsId is null");
        }
        this.srcProcInsIdLike = srcProcInsIdLike;
        return this;
    }

    public ProcessInsMappingQuery srcProcInsIds(List<String> srcProcInsIds){
        if (srcProcInsIds == null) {
            throw new RuntimeException("srcProcInsIds is empty ");
        }
        this.srcProcInsIds = srcProcInsIds;
        return this;
    }


    public ProcessInsMappingQuery desWbsDefId(Integer desWbsDefId){
	if (desWbsDefId == null) {
            throw new RuntimeException("desWbsDefId is null");
        }         
	this.desWbsDefId = desWbsDefId;
	return this;
    }

    public ProcessInsMappingQuery desWbsDefIdGreaterThanOrEqual(Integer desWbsDefIdGreaterThanOrEqual){
	if (desWbsDefIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("desWbsDefId is null");
        }         
	this.desWbsDefIdGreaterThanOrEqual = desWbsDefIdGreaterThanOrEqual;
        return this;
    }

    public ProcessInsMappingQuery desWbsDefIdLessThanOrEqual(Integer desWbsDefIdLessThanOrEqual){
        if (desWbsDefIdLessThanOrEqual == null) {
            throw new RuntimeException("desWbsDefId is null");
        }
        this.desWbsDefIdLessThanOrEqual = desWbsDefIdLessThanOrEqual;
        return this;
    }

    public ProcessInsMappingQuery desWbsDefIds(List<Integer> desWbsDefIds){
        if (desWbsDefIds == null) {
            throw new RuntimeException("desWbsDefIds is empty ");
        }
        this.desWbsDefIds = desWbsDefIds;
        return this;
    }


    public ProcessInsMappingQuery desWbsInsId(Integer desWbsInsId){
	if (desWbsInsId == null) {
            throw new RuntimeException("desWbsInsId is null");
        }         
	this.desWbsInsId = desWbsInsId;
	return this;
    }

    public ProcessInsMappingQuery desWbsInsIdGreaterThanOrEqual(Integer desWbsInsIdGreaterThanOrEqual){
	if (desWbsInsIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("desWbsInsId is null");
        }         
	this.desWbsInsIdGreaterThanOrEqual = desWbsInsIdGreaterThanOrEqual;
        return this;
    }

    public ProcessInsMappingQuery desWbsInsIdLessThanOrEqual(Integer desWbsInsIdLessThanOrEqual){
        if (desWbsInsIdLessThanOrEqual == null) {
            throw new RuntimeException("desWbsInsId is null");
        }
        this.desWbsInsIdLessThanOrEqual = desWbsInsIdLessThanOrEqual;
        return this;
    }

    public ProcessInsMappingQuery desWbsInsIds(List<Integer> desWbsInsIds){
        if (desWbsInsIds == null) {
            throw new RuntimeException("desWbsInsIds is empty ");
        }
        this.desWbsInsIds = desWbsInsIds;
        return this;
    }


    public ProcessInsMappingQuery desProcDefId(String desProcDefId){
	if (desProcDefId == null) {
	    throw new RuntimeException("desProcDefId is null");
        }         
	this.desProcDefId = desProcDefId;
	return this;
    }

    public ProcessInsMappingQuery desProcDefIdLike( String desProcDefIdLike){
        if (desProcDefIdLike == null) {
            throw new RuntimeException("desProcDefId is null");
        }
        this.desProcDefIdLike = desProcDefIdLike;
        return this;
    }

    public ProcessInsMappingQuery desProcDefIds(List<String> desProcDefIds){
        if (desProcDefIds == null) {
            throw new RuntimeException("desProcDefIds is empty ");
        }
        this.desProcDefIds = desProcDefIds;
        return this;
    }


    public ProcessInsMappingQuery desProcInsId(String desProcInsId){
	if (desProcInsId == null) {
	    throw new RuntimeException("desProcInsId is null");
        }         
	this.desProcInsId = desProcInsId;
	return this;
    }

    public ProcessInsMappingQuery desProcInsIdLike( String desProcInsIdLike){
        if (desProcInsIdLike == null) {
            throw new RuntimeException("desProcInsId is null");
        }
        this.desProcInsIdLike = desProcInsIdLike;
        return this;
    }

    public ProcessInsMappingQuery desProcInsIds(List<String> desProcInsIds){
        if (desProcInsIds == null) {
            throw new RuntimeException("desProcInsIds is empty ");
        }
        this.desProcInsIds = desProcInsIds;
        return this;
    }


    public ProcessInsMappingQuery srcSysId(String srcSysId){
	if (srcSysId == null) {
	    throw new RuntimeException("srcSysId is null");
        }         
	this.srcSysId = srcSysId;
	return this;
    }

    public ProcessInsMappingQuery srcSysIdLike( String srcSysIdLike){
        if (srcSysIdLike == null) {
            throw new RuntimeException("srcSysId is null");
        }
        this.srcSysIdLike = srcSysIdLike;
        return this;
    }

    public ProcessInsMappingQuery srcSysIds(List<String> srcSysIds){
        if (srcSysIds == null) {
            throw new RuntimeException("srcSysIds is empty ");
        }
        this.srcSysIds = srcSysIds;
        return this;
    }


    public ProcessInsMappingQuery desSysId(String desSysId){
	if (desSysId == null) {
	    throw new RuntimeException("desSysId is null");
        }         
	this.desSysId = desSysId;
	return this;
    }

    public ProcessInsMappingQuery desSysIdLike( String desSysIdLike){
        if (desSysIdLike == null) {
            throw new RuntimeException("desSysId is null");
        }
        this.desSysIdLike = desSysIdLike;
        return this;
    }

    public ProcessInsMappingQuery desSysIds(List<String> desSysIds){
        if (desSysIds == null) {
            throw new RuntimeException("desSysIds is empty ");
        }
        this.desSysIds = desSysIds;
        return this;
    }


    public ProcessInsMappingQuery procStatus(Integer procStatus){
	if (procStatus == null) {
            throw new RuntimeException("procStatus is null");
        }         
	this.procStatus = procStatus;
	return this;
    }

    public ProcessInsMappingQuery procStatusGreaterThanOrEqual(Integer procStatusGreaterThanOrEqual){
	if (procStatusGreaterThanOrEqual == null) {
	    throw new RuntimeException("procStatus is null");
        }         
	this.procStatusGreaterThanOrEqual = procStatusGreaterThanOrEqual;
        return this;
    }

    public ProcessInsMappingQuery procStatusLessThanOrEqual(Integer procStatusLessThanOrEqual){
        if (procStatusLessThanOrEqual == null) {
            throw new RuntimeException("procStatus is null");
        }
        this.procStatusLessThanOrEqual = procStatusLessThanOrEqual;
        return this;
    }

    public ProcessInsMappingQuery procStatuss(List<Integer> procStatuss){
        if (procStatuss == null) {
            throw new RuntimeException("procStatuss is empty ");
        }
        this.procStatuss = procStatuss;
        return this;
    }


    public ProcessInsMappingQuery procResult(Integer procResult){
	if (procResult == null) {
            throw new RuntimeException("procResult is null");
        }         
	this.procResult = procResult;
	return this;
    }

    public ProcessInsMappingQuery procResultGreaterThanOrEqual(Integer procResultGreaterThanOrEqual){
	if (procResultGreaterThanOrEqual == null) {
	    throw new RuntimeException("procResult is null");
        }         
	this.procResultGreaterThanOrEqual = procResultGreaterThanOrEqual;
        return this;
    }

    public ProcessInsMappingQuery procResultLessThanOrEqual(Integer procResultLessThanOrEqual){
        if (procResultLessThanOrEqual == null) {
            throw new RuntimeException("procResult is null");
        }
        this.procResultLessThanOrEqual = procResultLessThanOrEqual;
        return this;
    }

    public ProcessInsMappingQuery procResults(List<Integer> procResults){
        if (procResults == null) {
            throw new RuntimeException("procResults is empty ");
        }
        this.procResults = procResults;
        return this;
    }


    public ProcessInsMappingQuery transType(Integer transType){
	if (transType == null) {
            throw new RuntimeException("transType is null");
        }         
	this.transType = transType;
	return this;
    }

    public ProcessInsMappingQuery transTypeGreaterThanOrEqual(Integer transTypeGreaterThanOrEqual){
	if (transTypeGreaterThanOrEqual == null) {
	    throw new RuntimeException("transType is null");
        }         
	this.transTypeGreaterThanOrEqual = transTypeGreaterThanOrEqual;
        return this;
    }

    public ProcessInsMappingQuery transTypeLessThanOrEqual(Integer transTypeLessThanOrEqual){
        if (transTypeLessThanOrEqual == null) {
            throw new RuntimeException("transType is null");
        }
        this.transTypeLessThanOrEqual = transTypeLessThanOrEqual;
        return this;
    }

    public ProcessInsMappingQuery transTypes(List<Integer> transTypes){
        if (transTypes == null) {
            throw new RuntimeException("transTypes is empty ");
        }
        this.transTypes = transTypes;
        return this;
    }


    public ProcessInsMappingQuery replyFlag(Integer replyFlag){
	if (replyFlag == null) {
            throw new RuntimeException("replyFlag is null");
        }         
	this.replyFlag = replyFlag;
	return this;
    }

    public ProcessInsMappingQuery replyFlagGreaterThanOrEqual(Integer replyFlagGreaterThanOrEqual){
	if (replyFlagGreaterThanOrEqual == null) {
	    throw new RuntimeException("replyFlag is null");
        }         
	this.replyFlagGreaterThanOrEqual = replyFlagGreaterThanOrEqual;
        return this;
    }

    public ProcessInsMappingQuery replyFlagLessThanOrEqual(Integer replyFlagLessThanOrEqual){
        if (replyFlagLessThanOrEqual == null) {
            throw new RuntimeException("replyFlag is null");
        }
        this.replyFlagLessThanOrEqual = replyFlagLessThanOrEqual;
        return this;
    }

    public ProcessInsMappingQuery replyFlags(List<Integer> replyFlags){
        if (replyFlags == null) {
            throw new RuntimeException("replyFlags is empty ");
        }
        this.replyFlags = replyFlags;
        return this;
    }



    public ProcessInsMappingQuery procStartTimeGreaterThanOrEqual(Date procStartTimeGreaterThanOrEqual){
	if (procStartTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("procStartTime is null");
        }         
	this.procStartTimeGreaterThanOrEqual = procStartTimeGreaterThanOrEqual;
        return this;
    }

    public ProcessInsMappingQuery procStartTimeLessThanOrEqual(Date procStartTimeLessThanOrEqual){
        if (procStartTimeLessThanOrEqual == null) {
            throw new RuntimeException("procStartTime is null");
        }
        this.procStartTimeLessThanOrEqual = procStartTimeLessThanOrEqual;
        return this;
    }




    public ProcessInsMappingQuery procCompTimeGreaterThanOrEqual(Date procCompTimeGreaterThanOrEqual){
	if (procCompTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("procCompTime is null");
        }         
	this.procCompTimeGreaterThanOrEqual = procCompTimeGreaterThanOrEqual;
        return this;
    }

    public ProcessInsMappingQuery procCompTimeLessThanOrEqual(Date procCompTimeLessThanOrEqual){
        if (procCompTimeLessThanOrEqual == null) {
            throw new RuntimeException("procCompTime is null");
        }
        this.procCompTimeLessThanOrEqual = procCompTimeLessThanOrEqual;
        return this;
    }




    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("srcWbsDefId".equals(sortColumn)) {
                orderBy = "E.SRC_WBS_DEF_ID_" + a_x;
            } 

            if ("srcWbsInsId".equals(sortColumn)) {
                orderBy = "E.SRC_WBS_INS_ID_" + a_x;
            } 

            if ("srcProcDefId".equals(sortColumn)) {
                orderBy = "E.SRC_PROC_DEF_ID_" + a_x;
            } 

            if ("srcProcInsId".equals(sortColumn)) {
                orderBy = "E.SRC_PROC_INS_ID_" + a_x;
            } 

            if ("desWbsDefId".equals(sortColumn)) {
                orderBy = "E.DES_WBS_DEF_ID_" + a_x;
            } 

            if ("desWbsInsId".equals(sortColumn)) {
                orderBy = "E.DES_WBS_INS_ID_" + a_x;
            } 

            if ("desProcDefId".equals(sortColumn)) {
                orderBy = "E.DES_PROC_DEF_ID_" + a_x;
            } 

            if ("desProcInsId".equals(sortColumn)) {
                orderBy = "E.DES_PROC_INS_ID_" + a_x;
            } 

            if ("srcSysId".equals(sortColumn)) {
                orderBy = "E.SRC_SYS_ID_" + a_x;
            } 

            if ("desSysId".equals(sortColumn)) {
                orderBy = "E.DES_SYS_ID_" + a_x;
            } 

            if ("procStatus".equals(sortColumn)) {
                orderBy = "E.PROC_STATUS_" + a_x;
            } 

            if ("procResult".equals(sortColumn)) {
                orderBy = "E.PROC_RESULT_" + a_x;
            } 

            if ("transType".equals(sortColumn)) {
                orderBy = "E.TRANSTYPE_" + a_x;
            } 

            if ("replyFlag".equals(sortColumn)) {
                orderBy = "E.REPLYFLAG_" + a_x;
            } 

            if ("procStartTime".equals(sortColumn)) {
                orderBy = "E.PROC_STARTTIME_" + a_x;
            } 

            if ("procCompTime".equals(sortColumn)) {
                orderBy = "E.PROC_COMPTIME_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("iD", "ID_");
        addColumn("srcWbsDefId", "SRC_WBS_DEF_ID_");
        addColumn("srcWbsInsId", "SRC_WBS_INS_ID_");
        addColumn("srcProcDefId", "SRC_PROC_DEF_ID_");
        addColumn("srcProcInsId", "SRC_PROC_INS_ID_");
        addColumn("desWbsDefId", "DES_WBS_DEF_ID_");
        addColumn("desWbsInsId", "DES_WBS_INS_ID_");
        addColumn("desProcDefId", "DES_PROC_DEF_ID_");
        addColumn("desProcInsId", "DES_PROC_INS_ID_");
        addColumn("srcSysId", "SRC_SYS_ID_");
        addColumn("desSysId", "DES_SYS_ID_");
        addColumn("procStatus", "PROC_STATUS_");
        addColumn("procResult", "PROC_RESULT_");
        addColumn("transType", "TRANSTYPE_");
        addColumn("replyFlag", "REPLYFLAG_");
        addColumn("procStartTime", "PROC_STARTTIME_");
        addColumn("procCompTime", "PROC_COMPTIME_");
    }

}