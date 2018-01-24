package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FormNodeMessageQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
  	protected String telephone;
  	protected String telephoneLike;
  	protected List<String> telephones;
  	protected String msg;
  	protected String msgLike;
  	protected List<String> msgs;
  	protected Integer state;
  	protected Integer stateGreaterThanOrEqual;
  	protected Integer stateLessThanOrEqual;
  	protected List<Integer> states;
  	protected String creator;
  	protected String creatorLike;
  	protected List<String> creators;
        protected Date createDateGreaterThanOrEqual;
  	protected Date createDateLessThanOrEqual;
  	protected String result;
  	protected String resultLike;
  	protected List<String> results;

    public FormNodeMessageQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getTelephone(){
        return telephone;
    }

    public String getTelephoneLike(){
	    if (telephoneLike != null && telephoneLike.trim().length() > 0) {
		if (!telephoneLike.startsWith("%")) {
		    telephoneLike = "%" + telephoneLike;
		}
		if (!telephoneLike.endsWith("%")) {
		   telephoneLike = telephoneLike + "%";
		}
	    }
	return telephoneLike;
    }

    public List<String> getTelephones(){
	return telephones;
    }


    public String getMsg(){
        return msg;
    }

    public String getMsgLike(){
	    if (msgLike != null && msgLike.trim().length() > 0) {
		if (!msgLike.startsWith("%")) {
		    msgLike = "%" + msgLike;
		}
		if (!msgLike.endsWith("%")) {
		   msgLike = msgLike + "%";
		}
	    }
	return msgLike;
    }

    public List<String> getMsgs(){
	return msgs;
    }


    public Integer getState(){
        return state;
    }

    public Integer getStateGreaterThanOrEqual(){
        return stateGreaterThanOrEqual;
    }

    public Integer getStateLessThanOrEqual(){
	return stateLessThanOrEqual;
    }

    public List<Integer> getStates(){
	return states;
    }

    public String getCreator(){
        return creator;
    }

    public String getCreatorLike(){
	    if (creatorLike != null && creatorLike.trim().length() > 0) {
		if (!creatorLike.startsWith("%")) {
		    creatorLike = "%" + creatorLike;
		}
		if (!creatorLike.endsWith("%")) {
		   creatorLike = creatorLike + "%";
		}
	    }
	return creatorLike;
    }

    public List<String> getCreators(){
	return creators;
    }


    public Date getCreateDateGreaterThanOrEqual(){
        return createDateGreaterThanOrEqual;
    }

    public Date getCreateDateLessThanOrEqual(){
	return createDateLessThanOrEqual;
    }


    public String getResult(){
        return result;
    }

    public String getResultLike(){
	    if (resultLike != null && resultLike.trim().length() > 0) {
		if (!resultLike.startsWith("%")) {
		    resultLike = "%" + resultLike;
		}
		if (!resultLike.endsWith("%")) {
		   resultLike = resultLike + "%";
		}
	    }
	return resultLike;
    }

    public List<String> getResults(){
	return results;
    }


 

    public void setTelephone(String telephone){
        this.telephone = telephone;
    }

    public void setTelephoneLike( String telephoneLike){
	this.telephoneLike = telephoneLike;
    }

    public void setTelephones(List<String> telephones){
        this.telephones = telephones;
    }


    public void setMsg(String msg){
        this.msg = msg;
    }

    public void setMsgLike( String msgLike){
	this.msgLike = msgLike;
    }

    public void setMsgs(List<String> msgs){
        this.msgs = msgs;
    }


    public void setState(Integer state){
        this.state = state;
    }

    public void setStateGreaterThanOrEqual(Integer stateGreaterThanOrEqual){
        this.stateGreaterThanOrEqual = stateGreaterThanOrEqual;
    }

    public void setStateLessThanOrEqual(Integer stateLessThanOrEqual){
	this.stateLessThanOrEqual = stateLessThanOrEqual;
    }

    public void setStates(List<Integer> states){
        this.states = states;
    }


    public void setCreator(String creator){
        this.creator = creator;
    }

    public void setCreatorLike( String creatorLike){
	this.creatorLike = creatorLike;
    }

    public void setCreators(List<String> creators){
        this.creators = creators;
    }


    public void setCreateDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
        this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
    }

    public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual){
	this.createDateLessThanOrEqual = createDateLessThanOrEqual;
    }


    public void setResult(String result){
        this.result = result;
    }

    public void setResultLike( String resultLike){
	this.resultLike = resultLike;
    }

    public void setResults(List<String> results){
        this.results = results;
    }




    public FormNodeMessageQuery telephone(String telephone){
	if (telephone == null) {
	    throw new RuntimeException("telephone is null");
        }         
	this.telephone = telephone;
	return this;
    }

    public FormNodeMessageQuery telephoneLike( String telephoneLike){
        if (telephoneLike == null) {
            throw new RuntimeException("telephone is null");
        }
        this.telephoneLike = telephoneLike;
        return this;
    }

    public FormNodeMessageQuery telephones(List<String> telephones){
        if (telephones == null) {
            throw new RuntimeException("telephones is empty ");
        }
        this.telephones = telephones;
        return this;
    }


    public FormNodeMessageQuery msg(String msg){
	if (msg == null) {
	    throw new RuntimeException("msg is null");
        }         
	this.msg = msg;
	return this;
    }

    public FormNodeMessageQuery msgLike( String msgLike){
        if (msgLike == null) {
            throw new RuntimeException("msg is null");
        }
        this.msgLike = msgLike;
        return this;
    }

    public FormNodeMessageQuery msgs(List<String> msgs){
        if (msgs == null) {
            throw new RuntimeException("msgs is empty ");
        }
        this.msgs = msgs;
        return this;
    }


    public FormNodeMessageQuery state(Integer state){
	if (state == null) {
            throw new RuntimeException("state is null");
        }         
	this.state = state;
	return this;
    }

    public FormNodeMessageQuery stateGreaterThanOrEqual(Integer stateGreaterThanOrEqual){
	if (stateGreaterThanOrEqual == null) {
	    throw new RuntimeException("state is null");
        }         
	this.stateGreaterThanOrEqual = stateGreaterThanOrEqual;
        return this;
    }

    public FormNodeMessageQuery stateLessThanOrEqual(Integer stateLessThanOrEqual){
        if (stateLessThanOrEqual == null) {
            throw new RuntimeException("state is null");
        }
        this.stateLessThanOrEqual = stateLessThanOrEqual;
        return this;
    }

    public FormNodeMessageQuery states(List<Integer> states){
        if (states == null) {
            throw new RuntimeException("states is empty ");
        }
        this.states = states;
        return this;
    }


    public FormNodeMessageQuery creator(String creator){
	if (creator == null) {
	    throw new RuntimeException("creator is null");
        }         
	this.creator = creator;
	return this;
    }

    public FormNodeMessageQuery creatorLike( String creatorLike){
        if (creatorLike == null) {
            throw new RuntimeException("creator is null");
        }
        this.creatorLike = creatorLike;
        return this;
    }

    public FormNodeMessageQuery creators(List<String> creators){
        if (creators == null) {
            throw new RuntimeException("creators is empty ");
        }
        this.creators = creators;
        return this;
    }



    public FormNodeMessageQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
	if (createDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDate is null");
        }         
	this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
        return this;
    }

    public FormNodeMessageQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual){
        if (createDateLessThanOrEqual == null) {
            throw new RuntimeException("createDate is null");
        }
        this.createDateLessThanOrEqual = createDateLessThanOrEqual;
        return this;
    }



    public FormNodeMessageQuery result(String result){
	if (result == null) {
	    throw new RuntimeException("result is null");
        }         
	this.result = result;
	return this;
    }

    public FormNodeMessageQuery resultLike( String resultLike){
        if (resultLike == null) {
            throw new RuntimeException("result is null");
        }
        this.resultLike = resultLike;
        return this;
    }

    public FormNodeMessageQuery results(List<String> results){
        if (results == null) {
            throw new RuntimeException("results is empty ");
        }
        this.results = results;
        return this;
    }



    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("telephone".equals(sortColumn)) {
                orderBy = "E.TELEPHONE_" + a_x;
            } 

            if ("msg".equals(sortColumn)) {
                orderBy = "E.MSG_" + a_x;
            } 

            if ("state".equals(sortColumn)) {
                orderBy = "E.STATE_" + a_x;
            } 

            if ("creator".equals(sortColumn)) {
                orderBy = "E.CREATOR_" + a_x;
            } 

            if ("createDate".equals(sortColumn)) {
                orderBy = "E.CREATEDATE_" + a_x;
            } 

            if ("result".equals(sortColumn)) {
                orderBy = "E.RESULT_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("telephone", "TELEPHONE_");
        addColumn("msg", "MSG_");
        addColumn("state", "STATE_");
        addColumn("creator", "CREATOR_");
        addColumn("createDate", "CREATEDATE_");
        addColumn("result", "RESULT_");
    }

}