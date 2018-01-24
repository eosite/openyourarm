package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FormWorkflowTreeQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
  	protected String defId;
  	protected String defIdLike;
  	protected List<String> defIds;
  	protected String p_defId;
  	protected String p_defIdLike;
  	protected List<String> p_defIds;
  	protected String p_processdefId;
  	protected String p_processdefIdLike;
  	protected List<String> p_processdefIds;
  	protected String processdefId;
  	protected String processdefIdLike;
  	protected List<String> processdefIds;

    public FormWorkflowTreeQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
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


    public String getP_defId(){
        return p_defId;
    }

    public String getP_defIdLike(){
	    if (p_defIdLike != null && p_defIdLike.trim().length() > 0) {
		if (!p_defIdLike.startsWith("%")) {
		    p_defIdLike = "%" + p_defIdLike;
		}
		if (!p_defIdLike.endsWith("%")) {
		   p_defIdLike = p_defIdLike + "%";
		}
	    }
	return p_defIdLike;
    }

    public List<String> getP_defIds(){
	return p_defIds;
    }


    public String getP_processdefId(){
        return p_processdefId;
    }

    public String getP_processdefIdLike(){
	    if (p_processdefIdLike != null && p_processdefIdLike.trim().length() > 0) {
		if (!p_processdefIdLike.startsWith("%")) {
		    p_processdefIdLike = "%" + p_processdefIdLike;
		}
		if (!p_processdefIdLike.endsWith("%")) {
		   p_processdefIdLike = p_processdefIdLike + "%";
		}
	    }
	return p_processdefIdLike;
    }

    public List<String> getP_processdefIds(){
	return p_processdefIds;
    }


    public String getProcessdefId(){
        return processdefId;
    }

    public String getProcessdefIdLike(){
	    if (processdefIdLike != null && processdefIdLike.trim().length() > 0) {
		if (!processdefIdLike.startsWith("%")) {
		    processdefIdLike = "%" + processdefIdLike;
		}
		if (!processdefIdLike.endsWith("%")) {
		   processdefIdLike = processdefIdLike + "%";
		}
	    }
	return processdefIdLike;
    }

    public List<String> getProcessdefIds(){
	return processdefIds;
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


    public void setP_defId(String p_defId){
        this.p_defId = p_defId;
    }

    public void setP_defIdLike( String p_defIdLike){
	this.p_defIdLike = p_defIdLike;
    }

    public void setP_defIds(List<String> p_defIds){
        this.p_defIds = p_defIds;
    }


    public void setP_processdefId(String p_processdefId){
        this.p_processdefId = p_processdefId;
    }

    public void setP_processdefIdLike( String p_processdefIdLike){
	this.p_processdefIdLike = p_processdefIdLike;
    }

    public void setP_processdefIds(List<String> p_processdefIds){
        this.p_processdefIds = p_processdefIds;
    }


    public void setProcessdefId(String processdefId){
        this.processdefId = processdefId;
    }

    public void setProcessdefIdLike( String processdefIdLike){
	this.processdefIdLike = processdefIdLike;
    }

    public void setProcessdefIds(List<String> processdefIds){
        this.processdefIds = processdefIds;
    }




    public FormWorkflowTreeQuery defId(String defId){
	if (defId == null) {
	    throw new RuntimeException("defId is null");
        }         
	this.defId = defId;
	return this;
    }

    public FormWorkflowTreeQuery defIdLike( String defIdLike){
        if (defIdLike == null) {
            throw new RuntimeException("defId is null");
        }
        this.defIdLike = defIdLike;
        return this;
    }

    public FormWorkflowTreeQuery defIds(List<String> defIds){
        if (defIds == null) {
            throw new RuntimeException("defIds is empty ");
        }
        this.defIds = defIds;
        return this;
    }


    public FormWorkflowTreeQuery p_defId(String p_defId){
	if (p_defId == null) {
	    throw new RuntimeException("p_defId is null");
        }         
	this.p_defId = p_defId;
	return this;
    }

    public FormWorkflowTreeQuery p_defIdLike( String p_defIdLike){
        if (p_defIdLike == null) {
            throw new RuntimeException("p_defId is null");
        }
        this.p_defIdLike = p_defIdLike;
        return this;
    }

    public FormWorkflowTreeQuery p_defIds(List<String> p_defIds){
        if (p_defIds == null) {
            throw new RuntimeException("p_defIds is empty ");
        }
        this.p_defIds = p_defIds;
        return this;
    }


    public FormWorkflowTreeQuery p_processdefId(String p_processdefId){
	if (p_processdefId == null) {
	    throw new RuntimeException("p_processdefId is null");
        }         
	this.p_processdefId = p_processdefId;
	return this;
    }

    public FormWorkflowTreeQuery p_processdefIdLike( String p_processdefIdLike){
        if (p_processdefIdLike == null) {
            throw new RuntimeException("p_processdefId is null");
        }
        this.p_processdefIdLike = p_processdefIdLike;
        return this;
    }

    public FormWorkflowTreeQuery p_processdefIds(List<String> p_processdefIds){
        if (p_processdefIds == null) {
            throw new RuntimeException("p_processdefIds is empty ");
        }
        this.p_processdefIds = p_processdefIds;
        return this;
    }


    public FormWorkflowTreeQuery processdefId(String processdefId){
	if (processdefId == null) {
	    throw new RuntimeException("processdefId is null");
        }         
	this.processdefId = processdefId;
	return this;
    }

    public FormWorkflowTreeQuery processdefIdLike( String processdefIdLike){
        if (processdefIdLike == null) {
            throw new RuntimeException("processdefId is null");
        }
        this.processdefIdLike = processdefIdLike;
        return this;
    }

    public FormWorkflowTreeQuery processdefIds(List<String> processdefIds){
        if (processdefIds == null) {
            throw new RuntimeException("processdefIds is empty ");
        }
        this.processdefIds = processdefIds;
        return this;
    }



    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("defId".equals(sortColumn)) {
                orderBy = "E.DEF_ID_" + a_x;
            } 

            if ("p_defId".equals(sortColumn)) {
                orderBy = "E.P_DEFID_" + a_x;
            } 

            if ("p_processdefId".equals(sortColumn)) {
                orderBy = "E.P_PROCESSDEF_ID_" + a_x;
            } 

            if ("processdefId".equals(sortColumn)) {
                orderBy = "E.PROCESSDEF_ID_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("defId", "DEF_ID_");
        addColumn("p_defId", "P_DEFID_");
        addColumn("p_processdefId", "P_PROCESSDEF_ID_");
        addColumn("processdefId", "PROCESSDEF_ID_");
    }

}