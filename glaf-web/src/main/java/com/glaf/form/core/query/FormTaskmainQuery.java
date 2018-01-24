package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FormTaskmainQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
  	protected Long planId;
  	protected Long planIdGreaterThanOrEqual;
  	protected Long planIdLessThanOrEqual;
  	protected List<Long> planIds;
  	protected String defId;
  	protected String defIdLike;
  	protected List<String> defIds;
  	protected String definedId;
  	protected String definedIdLike;
  	protected List<String> definedIds;
  	protected String processId;
  	protected String p_processId;
  	protected String processIdLike;
  	protected List<String> processIds;
  	protected String variableVal;
  	protected String variableValLike;
  	protected List<String> variableVals;
  	protected String name;
  	protected String nameLike;
  	protected List<String> names;
  	protected Integer status;
  	protected Integer statusGreaterThanOrEqual;
  	protected Integer statusLessThanOrEqual;
  	protected List<Integer> statuss;
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

    public FormTaskmainQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public Long getPlanId(){
        return planId;
    }

    public Long getPlanIdGreaterThanOrEqual(){
        return planIdGreaterThanOrEqual;
    }

    public Long getPlanIdLessThanOrEqual(){
	return planIdLessThanOrEqual;
    }

    public List<Long> getPlanIds(){
	return planIds;
    }

    public String getDefId(){
        return defId;
    }

    public String getDefIdLike(){
	    if (defIdLike != null && defIdLike.trim().length() > 0) {
		if (!defIdLike.startsWith("%")) {
		    defIdLike = "%" + defIdLike;
		}
		if (!defIdLike.endsWith("%")) {
		   defIdLike = defIdLike + "%";
		}
	    }
	return defIdLike;
    }

    public List<String> getDefIds(){
	return defIds;
    }


    public String getDefinedId(){
        return definedId;
    }

    public String getDefinedIdLike(){
	    if (definedIdLike != null && definedIdLike.trim().length() > 0) {
		if (!definedIdLike.startsWith("%")) {
		    definedIdLike = "%" + definedIdLike;
		}
		if (!definedIdLike.endsWith("%")) {
		   definedIdLike = definedIdLike + "%";
		}
	    }
	return definedIdLike;
    }

    public List<String> getDefinedIds(){
	return definedIds;
    }


    public String getProcessId(){
        return processId;
    }

    public String getProcessIdLike(){
	    if (processIdLike != null && processIdLike.trim().length() > 0) {
		if (!processIdLike.startsWith("%")) {
		    processIdLike = "%" + processIdLike;
		}
		if (!processIdLike.endsWith("%")) {
		   processIdLike = processIdLike + "%";
		}
	    }
	return processIdLike;
    }

    public List<String> getProcessIds(){
	return processIds;
    }


    public String getVariableVal(){
        return variableVal;
    }

    public String getVariableValLike(){
	    if (variableValLike != null && variableValLike.trim().length() > 0) {
		if (!variableValLike.startsWith("%")) {
		    variableValLike = "%" + variableValLike;
		}
		if (!variableValLike.endsWith("%")) {
		   variableValLike = variableValLike + "%";
		}
	    }
	return variableValLike;
    }

    public List<String> getVariableVals(){
	return variableVals;
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


 

    public void setPlanId(Long planId){
        this.planId = planId;
    }

    public void setPlanIdGreaterThanOrEqual(Long planIdGreaterThanOrEqual){
        this.planIdGreaterThanOrEqual = planIdGreaterThanOrEqual;
    }

    public void setPlanIdLessThanOrEqual(Long planIdLessThanOrEqual){
	this.planIdLessThanOrEqual = planIdLessThanOrEqual;
    }

    public void setPlanIds(List<Long> planIds){
        this.planIds = planIds;
    }


    public void setDefId(String defId){
        this.defId = defId;
    }

    public void setDefIdLike( String defIdLike){
	this.defIdLike = defIdLike;
    }

    public void setDefIds(List<String> defIds){
        this.defIds = defIds;
    }


    public void setDefinedId(String definedId){
        this.definedId = definedId;
    }

    public void setDefinedIdLike( String definedIdLike){
	this.definedIdLike = definedIdLike;
    }

    public void setDefinedIds(List<String> definedIds){
        this.definedIds = definedIds;
    }


    public void setProcessId(String processId){
        this.processId = processId;
    }

    public void setProcessIdLike( String processIdLike){
	this.processIdLike = processIdLike;
    }

    public void setProcessIds(List<String> processIds){
        this.processIds = processIds;
    }


    public void setVariableVal(String variableVal){
        this.variableVal = variableVal;
    }

    public void setVariableValLike( String variableValLike){
	this.variableValLike = variableValLike;
    }

    public void setVariableVals(List<String> variableVals){
        this.variableVals = variableVals;
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




    public FormTaskmainQuery planId(Long planId){
	if (planId == null) {
            throw new RuntimeException("planId is null");
        }         
	this.planId = planId;
	return this;
    }

    public FormTaskmainQuery planIdGreaterThanOrEqual(Long planIdGreaterThanOrEqual){
	if (planIdGreaterThanOrEqual == null) {
	    throw new RuntimeException("planId is null");
        }         
	this.planIdGreaterThanOrEqual = planIdGreaterThanOrEqual;
        return this;
    }

    public FormTaskmainQuery planIdLessThanOrEqual(Long planIdLessThanOrEqual){
        if (planIdLessThanOrEqual == null) {
            throw new RuntimeException("planId is null");
        }
        this.planIdLessThanOrEqual = planIdLessThanOrEqual;
        return this;
    }

    public FormTaskmainQuery planIds(List<Long> planIds){
        if (planIds == null) {
            throw new RuntimeException("planIds is empty ");
        }
        this.planIds = planIds;
        return this;
    }


    public FormTaskmainQuery defId(String defId){
	if (defId == null) {
	    throw new RuntimeException("defId is null");
        }         
	this.defId = defId;
	return this;
    }

    public FormTaskmainQuery defIdLike( String defIdLike){
        if (defIdLike == null) {
            throw new RuntimeException("defId is null");
        }
        this.defIdLike = defIdLike;
        return this;
    }

    public FormTaskmainQuery defIds(List<String> defIds){
        if (defIds == null) {
            throw new RuntimeException("defIds is empty ");
        }
        this.defIds = defIds;
        return this;
    }


    public FormTaskmainQuery definedId(String definedId){
	if (definedId == null) {
	    throw new RuntimeException("definedId is null");
        }         
	this.definedId = definedId;
	return this;
    }

    public FormTaskmainQuery definedIdLike( String definedIdLike){
        if (definedIdLike == null) {
            throw new RuntimeException("definedId is null");
        }
        this.definedIdLike = definedIdLike;
        return this;
    }

    public FormTaskmainQuery definedIds(List<String> definedIds){
        if (definedIds == null) {
            throw new RuntimeException("definedIds is empty ");
        }
        this.definedIds = definedIds;
        return this;
    }


    public FormTaskmainQuery processId(String processId){
	if (processId == null) {
	    throw new RuntimeException("processId is null");
        }         
	this.processId = processId;
	return this;
    }

    public FormTaskmainQuery processIdLike( String processIdLike){
        if (processIdLike == null) {
            throw new RuntimeException("processId is null");
        }
        this.processIdLike = processIdLike;
        return this;
    }

    public FormTaskmainQuery processIds(List<String> processIds){
        if (processIds == null) {
            throw new RuntimeException("processIds is empty ");
        }
        this.processIds = processIds;
        return this;
    }


    public FormTaskmainQuery variableVal(String variableVal){
	if (variableVal == null) {
	    throw new RuntimeException("variableVal is null");
        }         
	this.variableVal = variableVal;
	return this;
    }

    public FormTaskmainQuery variableValLike( String variableValLike){
        if (variableValLike == null) {
            throw new RuntimeException("variableVal is null");
        }
        this.variableValLike = variableValLike;
        return this;
    }

    public FormTaskmainQuery variableVals(List<String> variableVals){
        if (variableVals == null) {
            throw new RuntimeException("variableVals is empty ");
        }
        this.variableVals = variableVals;
        return this;
    }


    public FormTaskmainQuery name(String name){
	if (name == null) {
	    throw new RuntimeException("name is null");
        }         
	this.name = name;
	return this;
    }

    public FormTaskmainQuery nameLike( String nameLike){
        if (nameLike == null) {
            throw new RuntimeException("name is null");
        }
        this.nameLike = nameLike;
        return this;
    }

    public FormTaskmainQuery names(List<String> names){
        if (names == null) {
            throw new RuntimeException("names is empty ");
        }
        this.names = names;
        return this;
    }


    public FormTaskmainQuery status(Integer status){
	if (status == null) {
            throw new RuntimeException("status is null");
        }         
	this.status = status;
	return this;
    }

    public FormTaskmainQuery statusGreaterThanOrEqual(Integer statusGreaterThanOrEqual){
	if (statusGreaterThanOrEqual == null) {
	    throw new RuntimeException("status is null");
        }         
	this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
        return this;
    }

    public FormTaskmainQuery statusLessThanOrEqual(Integer statusLessThanOrEqual){
        if (statusLessThanOrEqual == null) {
            throw new RuntimeException("status is null");
        }
        this.statusLessThanOrEqual = statusLessThanOrEqual;
        return this;
    }

    public FormTaskmainQuery statuss(List<Integer> statuss){
        if (statuss == null) {
            throw new RuntimeException("statuss is empty ");
        }
        this.statuss = statuss;
        return this;
    }


    public FormTaskmainQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public FormTaskmainQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public FormTaskmainQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public FormTaskmainQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
	if (createDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDate is null");
        }         
	this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
        return this;
    }

    public FormTaskmainQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual){
        if (createDateLessThanOrEqual == null) {
            throw new RuntimeException("createDate is null");
        }
        this.createDateLessThanOrEqual = createDateLessThanOrEqual;
        return this;
    }



    public FormTaskmainQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public FormTaskmainQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public FormTaskmainQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public FormTaskmainQuery updateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual){
	if (updateDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateDate is null");
        }         
	this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
        return this;
    }

    public FormTaskmainQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual){
        if (updateDateLessThanOrEqual == null) {
            throw new RuntimeException("updateDate is null");
        }
        this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
        return this;
    }




    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("planId".equals(sortColumn)) {
                orderBy = "E.PLANID_" + a_x;
            } 

            if ("defId".equals(sortColumn)) {
                orderBy = "E.DEFID_" + a_x;
            } 

            if ("definedId".equals(sortColumn)) {
                orderBy = "E.DEFINEDID_" + a_x;
            } 

            if ("processId".equals(sortColumn)) {
                orderBy = "E.PROCESSID_" + a_x;
            } 

            if ("variableVal".equals(sortColumn)) {
                orderBy = "E.VARIABLEVAL_" + a_x;
            } 

            if ("name".equals(sortColumn)) {
                orderBy = "E.NAME_" + a_x;
            } 

            if ("status".equals(sortColumn)) {
                orderBy = "E.STATUS_" + a_x;
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

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("planId", "PLANID_");
        addColumn("defId", "DEFID_");
        addColumn("definedId", "DEFINEDID_");
        addColumn("processId", "PROCESSID_");
        addColumn("variableVal", "VARIABLEVAL_");
        addColumn("name", "NAME_");
        addColumn("status", "STATUS_");
        addColumn("createBy", "CREATEBY_");
        addColumn("createDate", "CREATEDATE_");
        addColumn("updateBy", "UPDATEBY_");
        addColumn("updateDate", "UPDATEDATE_");
    }

	public String getP_processId() {
		return p_processId;
	}

	public void setP_processId(String p_processId) {
		this.p_processId = p_processId;
	}

}