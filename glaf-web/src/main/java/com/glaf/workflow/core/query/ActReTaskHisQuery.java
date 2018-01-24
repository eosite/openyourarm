package com.glaf.workflow.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ActReTaskHisQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Integer> ids;
	protected Collection<String> appActorIds;
  	protected String actorId;
  	protected String actorIdLike;
  	protected List<String> actorIds;
  	protected String taskId;
  	protected String taskIdLike;
  	protected List<String> taskIds;
  	protected String taskName;
  	protected String taskNameLike;
  	protected List<String> taskNames;
  	protected String taskKey;
  	protected String taskKeyLike;
  	protected List<String> taskKeys;
  	protected String processId;
  	protected String processIdLike;
  	protected List<String> processIds;
  	protected String fromKey;
  	protected String fromKeyLike;
  	protected List<String> fromKeys;
  	protected String fromId;
  	protected String fromIdLike;
  	protected List<String> fromIds;
  	protected String fromName;
  	protected String fromNameLike;
  	protected List<String> fromNames;
  	protected String fromActorId;
  	protected String fromActorIdLike;
  	protected List<String> fromActorIds;
  	protected String variables;
  	protected String variablesLike;
  	protected List<String> variabless;
  	protected String subType;
  	protected String subTypeLike;
  	protected List<String> subTypes;
        protected Date createDateGreaterThanOrEqual;
  	protected Date createDateLessThanOrEqual;

    public ActReTaskHisQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getActorId(){
        return actorId;
    }

    public String getActorIdLike(){
	    if (actorIdLike != null && actorIdLike.trim().length() > 0) {
		if (!actorIdLike.startsWith("%")) {
		    actorIdLike = "%" + actorIdLike;
		}
		if (!actorIdLike.endsWith("%")) {
		   actorIdLike = actorIdLike + "%";
		}
	    }
	return actorIdLike;
    }

    public List<String> getActorIds(){
	return actorIds;
    }


    public String getTaskId(){
        return taskId;
    }

    public String getTaskIdLike(){
	    if (taskIdLike != null && taskIdLike.trim().length() > 0) {
		if (!taskIdLike.startsWith("%")) {
		    taskIdLike = "%" + taskIdLike;
		}
		if (!taskIdLike.endsWith("%")) {
		   taskIdLike = taskIdLike + "%";
		}
	    }
	return taskIdLike;
    }

    public List<String> getTaskIds(){
	return taskIds;
    }


    public String getTaskName(){
        return taskName;
    }

    public String getTaskNameLike(){
	    if (taskNameLike != null && taskNameLike.trim().length() > 0) {
		if (!taskNameLike.startsWith("%")) {
		    taskNameLike = "%" + taskNameLike;
		}
		if (!taskNameLike.endsWith("%")) {
		   taskNameLike = taskNameLike + "%";
		}
	    }
	return taskNameLike;
    }

    public List<String> getTaskNames(){
	return taskNames;
    }


    public String getTaskKey(){
        return taskKey;
    }

    public String getTaskKeyLike(){
	    if (taskKeyLike != null && taskKeyLike.trim().length() > 0) {
		if (!taskKeyLike.startsWith("%")) {
		    taskKeyLike = "%" + taskKeyLike;
		}
		if (!taskKeyLike.endsWith("%")) {
		   taskKeyLike = taskKeyLike + "%";
		}
	    }
	return taskKeyLike;
    }

    public List<String> getTaskKeys(){
	return taskKeys;
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


    public String getFromKey(){
        return fromKey;
    }

    public String getFromKeyLike(){
	    if (fromKeyLike != null && fromKeyLike.trim().length() > 0) {
		if (!fromKeyLike.startsWith("%")) {
		    fromKeyLike = "%" + fromKeyLike;
		}
		if (!fromKeyLike.endsWith("%")) {
		   fromKeyLike = fromKeyLike + "%";
		}
	    }
	return fromKeyLike;
    }

    public List<String> getFromKeys(){
	return fromKeys;
    }


    public String getFromId(){
        return fromId;
    }

    public String getFromIdLike(){
	    if (fromIdLike != null && fromIdLike.trim().length() > 0) {
		if (!fromIdLike.startsWith("%")) {
		    fromIdLike = "%" + fromIdLike;
		}
		if (!fromIdLike.endsWith("%")) {
		   fromIdLike = fromIdLike + "%";
		}
	    }
	return fromIdLike;
    }

    public List<String> getFromIds(){
	return fromIds;
    }


    public String getFromName(){
        return fromName;
    }

    public String getFromNameLike(){
	    if (fromNameLike != null && fromNameLike.trim().length() > 0) {
		if (!fromNameLike.startsWith("%")) {
		    fromNameLike = "%" + fromNameLike;
		}
		if (!fromNameLike.endsWith("%")) {
		   fromNameLike = fromNameLike + "%";
		}
	    }
	return fromNameLike;
    }

    public List<String> getFromNames(){
	return fromNames;
    }


    public String getFromActorId(){
        return fromActorId;
    }

    public String getFromActorIdLike(){
	    if (fromActorIdLike != null && fromActorIdLike.trim().length() > 0) {
		if (!fromActorIdLike.startsWith("%")) {
		    fromActorIdLike = "%" + fromActorIdLike;
		}
		if (!fromActorIdLike.endsWith("%")) {
		   fromActorIdLike = fromActorIdLike + "%";
		}
	    }
	return fromActorIdLike;
    }

    public List<String> getFromActorIds(){
	return fromActorIds;
    }


    public String getVariables(){
        return variables;
    }

    public String getVariablesLike(){
	    if (variablesLike != null && variablesLike.trim().length() > 0) {
		if (!variablesLike.startsWith("%")) {
		    variablesLike = "%" + variablesLike;
		}
		if (!variablesLike.endsWith("%")) {
		   variablesLike = variablesLike + "%";
		}
	    }
	return variablesLike;
    }

    public List<String> getVariabless(){
	return variabless;
    }


    public String getSubType(){
        return subType;
    }

    public String getSubTypeLike(){
	    if (subTypeLike != null && subTypeLike.trim().length() > 0) {
		if (!subTypeLike.startsWith("%")) {
		    subTypeLike = "%" + subTypeLike;
		}
		if (!subTypeLike.endsWith("%")) {
		   subTypeLike = subTypeLike + "%";
		}
	    }
	return subTypeLike;
    }

    public List<String> getSubTypes(){
	return subTypes;
    }


    public Date getCreateDateGreaterThanOrEqual(){
        return createDateGreaterThanOrEqual;
    }

    public Date getCreateDateLessThanOrEqual(){
	return createDateLessThanOrEqual;
    }


 

    public void setActorId(String actorId){
        this.actorId = actorId;
    }

    public void setActorIdLike( String actorIdLike){
	this.actorIdLike = actorIdLike;
    }

    public void setActorIds(List<String> actorIds){
        this.actorIds = actorIds;
    }


    public void setTaskId(String taskId){
        this.taskId = taskId;
    }

    public void setTaskIdLike( String taskIdLike){
	this.taskIdLike = taskIdLike;
    }

    public void setTaskIds(List<String> taskIds){
        this.taskIds = taskIds;
    }


    public void setTaskName(String taskName){
        this.taskName = taskName;
    }

    public void setTaskNameLike( String taskNameLike){
	this.taskNameLike = taskNameLike;
    }

    public void setTaskNames(List<String> taskNames){
        this.taskNames = taskNames;
    }


    public void setTaskKey(String taskKey){
        this.taskKey = taskKey;
    }

    public void setTaskKeyLike( String taskKeyLike){
	this.taskKeyLike = taskKeyLike;
    }

    public void setTaskKeys(List<String> taskKeys){
        this.taskKeys = taskKeys;
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


    public void setFromKey(String fromKey){
        this.fromKey = fromKey;
    }

    public void setFromKeyLike( String fromKeyLike){
	this.fromKeyLike = fromKeyLike;
    }

    public void setFromKeys(List<String> fromKeys){
        this.fromKeys = fromKeys;
    }


    public void setFromId(String fromId){
        this.fromId = fromId;
    }

    public void setFromIdLike( String fromIdLike){
	this.fromIdLike = fromIdLike;
    }

    public void setFromIds(List<String> fromIds){
        this.fromIds = fromIds;
    }


    public void setFromName(String fromName){
        this.fromName = fromName;
    }

    public void setFromNameLike( String fromNameLike){
	this.fromNameLike = fromNameLike;
    }

    public void setFromNames(List<String> fromNames){
        this.fromNames = fromNames;
    }


    public void setFromActorId(String fromActorId){
        this.fromActorId = fromActorId;
    }

    public void setFromActorIdLike( String fromActorIdLike){
	this.fromActorIdLike = fromActorIdLike;
    }

    public void setFromActorIds(List<String> fromActorIds){
        this.fromActorIds = fromActorIds;
    }


    public void setVariables(String variables){
        this.variables = variables;
    }

    public void setVariablesLike( String variablesLike){
	this.variablesLike = variablesLike;
    }

    public void setVariabless(List<String> variabless){
        this.variabless = variabless;
    }


    public void setSubType(String subType){
        this.subType = subType;
    }

    public void setSubTypeLike( String subTypeLike){
	this.subTypeLike = subTypeLike;
    }

    public void setSubTypes(List<String> subTypes){
        this.subTypes = subTypes;
    }


    public void setCreateDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
        this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
    }

    public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual){
	this.createDateLessThanOrEqual = createDateLessThanOrEqual;
    }




    public ActReTaskHisQuery actorId(String actorId){
	if (actorId == null) {
	    throw new RuntimeException("actorId is null");
        }         
	this.actorId = actorId;
	return this;
    }

    public ActReTaskHisQuery actorIdLike( String actorIdLike){
        if (actorIdLike == null) {
            throw new RuntimeException("actorId is null");
        }
        this.actorIdLike = actorIdLike;
        return this;
    }

    public ActReTaskHisQuery actorIds(List<String> actorIds){
        if (actorIds == null) {
            throw new RuntimeException("actorIds is empty ");
        }
        this.actorIds = actorIds;
        return this;
    }


    public ActReTaskHisQuery taskId(String taskId){
	if (taskId == null) {
	    throw new RuntimeException("taskId is null");
        }         
	this.taskId = taskId;
	return this;
    }

    public ActReTaskHisQuery taskIdLike( String taskIdLike){
        if (taskIdLike == null) {
            throw new RuntimeException("taskId is null");
        }
        this.taskIdLike = taskIdLike;
        return this;
    }

    public ActReTaskHisQuery taskIds(List<String> taskIds){
        if (taskIds == null) {
            throw new RuntimeException("taskIds is empty ");
        }
        this.taskIds = taskIds;
        return this;
    }


    public ActReTaskHisQuery taskName(String taskName){
	if (taskName == null) {
	    throw new RuntimeException("taskName is null");
        }         
	this.taskName = taskName;
	return this;
    }

    public ActReTaskHisQuery taskNameLike( String taskNameLike){
        if (taskNameLike == null) {
            throw new RuntimeException("taskName is null");
        }
        this.taskNameLike = taskNameLike;
        return this;
    }

    public ActReTaskHisQuery taskNames(List<String> taskNames){
        if (taskNames == null) {
            throw new RuntimeException("taskNames is empty ");
        }
        this.taskNames = taskNames;
        return this;
    }


    public ActReTaskHisQuery taskKey(String taskKey){
	if (taskKey == null) {
	    throw new RuntimeException("taskKey is null");
        }         
	this.taskKey = taskKey;
	return this;
    }

    public ActReTaskHisQuery taskKeyLike( String taskKeyLike){
        if (taskKeyLike == null) {
            throw new RuntimeException("taskKey is null");
        }
        this.taskKeyLike = taskKeyLike;
        return this;
    }

    public ActReTaskHisQuery taskKeys(List<String> taskKeys){
        if (taskKeys == null) {
            throw new RuntimeException("taskKeys is empty ");
        }
        this.taskKeys = taskKeys;
        return this;
    }


    public ActReTaskHisQuery processId(String processId){
	if (processId == null) {
	    throw new RuntimeException("processId is null");
        }         
	this.processId = processId;
	return this;
    }

    public ActReTaskHisQuery processIdLike( String processIdLike){
        if (processIdLike == null) {
            throw new RuntimeException("processId is null");
        }
        this.processIdLike = processIdLike;
        return this;
    }

    public ActReTaskHisQuery processIds(List<String> processIds){
        if (processIds == null) {
            throw new RuntimeException("processIds is empty ");
        }
        this.processIds = processIds;
        return this;
    }


    public ActReTaskHisQuery fromKey(String fromKey){
	if (fromKey == null) {
	    throw new RuntimeException("fromKey is null");
        }         
	this.fromKey = fromKey;
	return this;
    }

    public ActReTaskHisQuery fromKeyLike( String fromKeyLike){
        if (fromKeyLike == null) {
            throw new RuntimeException("fromKey is null");
        }
        this.fromKeyLike = fromKeyLike;
        return this;
    }

    public ActReTaskHisQuery fromKeys(List<String> fromKeys){
        if (fromKeys == null) {
            throw new RuntimeException("fromKeys is empty ");
        }
        this.fromKeys = fromKeys;
        return this;
    }


    public ActReTaskHisQuery fromId(String fromId){
	if (fromId == null) {
	    throw new RuntimeException("fromId is null");
        }         
	this.fromId = fromId;
	return this;
    }

    public ActReTaskHisQuery fromIdLike( String fromIdLike){
        if (fromIdLike == null) {
            throw new RuntimeException("fromId is null");
        }
        this.fromIdLike = fromIdLike;
        return this;
    }

    public ActReTaskHisQuery fromIds(List<String> fromIds){
        if (fromIds == null) {
            throw new RuntimeException("fromIds is empty ");
        }
        this.fromIds = fromIds;
        return this;
    }


    public ActReTaskHisQuery fromName(String fromName){
	if (fromName == null) {
	    throw new RuntimeException("fromName is null");
        }         
	this.fromName = fromName;
	return this;
    }

    public ActReTaskHisQuery fromNameLike( String fromNameLike){
        if (fromNameLike == null) {
            throw new RuntimeException("fromName is null");
        }
        this.fromNameLike = fromNameLike;
        return this;
    }

    public ActReTaskHisQuery fromNames(List<String> fromNames){
        if (fromNames == null) {
            throw new RuntimeException("fromNames is empty ");
        }
        this.fromNames = fromNames;
        return this;
    }


    public ActReTaskHisQuery fromActorId(String fromActorId){
	if (fromActorId == null) {
	    throw new RuntimeException("fromActorId is null");
        }         
	this.fromActorId = fromActorId;
	return this;
    }

    public ActReTaskHisQuery fromActorIdLike( String fromActorIdLike){
        if (fromActorIdLike == null) {
            throw new RuntimeException("fromActorId is null");
        }
        this.fromActorIdLike = fromActorIdLike;
        return this;
    }

    public ActReTaskHisQuery fromActorIds(List<String> fromActorIds){
        if (fromActorIds == null) {
            throw new RuntimeException("fromActorIds is empty ");
        }
        this.fromActorIds = fromActorIds;
        return this;
    }


    public ActReTaskHisQuery variables(String variables){
	if (variables == null) {
	    throw new RuntimeException("variables is null");
        }         
	this.variables = variables;
	return this;
    }

    public ActReTaskHisQuery variablesLike( String variablesLike){
        if (variablesLike == null) {
            throw new RuntimeException("variables is null");
        }
        this.variablesLike = variablesLike;
        return this;
    }

    public ActReTaskHisQuery variabless(List<String> variabless){
        if (variabless == null) {
            throw new RuntimeException("variabless is empty ");
        }
        this.variabless = variabless;
        return this;
    }


    public ActReTaskHisQuery subType(String subType){
	if (subType == null) {
	    throw new RuntimeException("subType is null");
        }         
	this.subType = subType;
	return this;
    }

    public ActReTaskHisQuery subTypeLike( String subTypeLike){
        if (subTypeLike == null) {
            throw new RuntimeException("subType is null");
        }
        this.subTypeLike = subTypeLike;
        return this;
    }

    public ActReTaskHisQuery subTypes(List<String> subTypes){
        if (subTypes == null) {
            throw new RuntimeException("subTypes is empty ");
        }
        this.subTypes = subTypes;
        return this;
    }



    public ActReTaskHisQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
	if (createDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDate is null");
        }         
	this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
        return this;
    }

    public ActReTaskHisQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual){
        if (createDateLessThanOrEqual == null) {
            throw new RuntimeException("createDate is null");
        }
        this.createDateLessThanOrEqual = createDateLessThanOrEqual;
        return this;
    }




    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("actorId".equals(sortColumn)) {
                orderBy = "E.ACTORID_" + a_x;
            } 

            if ("taskId".equals(sortColumn)) {
                orderBy = "E.TASKID_" + a_x;
            } 

            if ("taskName".equals(sortColumn)) {
                orderBy = "E.TASKNAME_" + a_x;
            } 

            if ("taskKey".equals(sortColumn)) {
                orderBy = "E.TASKKEY_" + a_x;
            } 

            if ("processId".equals(sortColumn)) {
                orderBy = "E.PROCESSID_" + a_x;
            } 

            if ("fromKey".equals(sortColumn)) {
                orderBy = "E.FROMKEY_" + a_x;
            } 

            if ("fromId".equals(sortColumn)) {
                orderBy = "E.FROMID_" + a_x;
            } 

            if ("fromName".equals(sortColumn)) {
                orderBy = "E.FROMNAME_" + a_x;
            } 

            if ("fromActorId".equals(sortColumn)) {
                orderBy = "E.FROMACTORID_" + a_x;
            } 

            if ("variables".equals(sortColumn)) {
                orderBy = "E.VARIABLES_" + a_x;
            } 

            if ("subType".equals(sortColumn)) {
                orderBy = "E.SUBTYPE_" + a_x;
            } 

            if ("createDate".equals(sortColumn)) {
                orderBy = "E.CREATEDATE_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("actorId", "ACTORID_");
        addColumn("taskId", "TASKID_");
        addColumn("taskName", "TASKNAME_");
        addColumn("taskKey", "TASKKEY_");
        addColumn("processId", "PROCESSID_");
        addColumn("fromKey", "FROMKEY_");
        addColumn("fromId", "FROMID_");
        addColumn("fromName", "FROMNAME_");
        addColumn("fromActorId", "FROMACTORID_");
        addColumn("variables", "VARIABLES_");
        addColumn("subType", "SUBTYPE_");
        addColumn("createDate", "CREATEDATE_");
    }

}