package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class MsgValidHistoryQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String telephone;
  	protected String telephoneLike;
  	protected List<String> telephones;
        protected Date sendDateGreaterThanOrEqual;
  	protected Date sendDateLessThanOrEqual;
  	protected Integer type;
  	protected Integer typeGreaterThanOrEqual;
  	protected Integer typeLessThanOrEqual;
  	protected List<Integer> types;
  	protected String typeName;
  	protected String typeNameLike;
  	protected List<String> typeNames;
  	protected String url;
  	protected String urlLike;
  	protected List<String> urls;
  	protected String msg;
  	protected String msgLike;
  	protected List<String> msgs;
  	protected Integer status;
  	protected Integer statusGreaterThanOrEqual;
  	protected Integer statusLessThanOrEqual;
  	protected List<Integer> statuss;
  	protected String statusName;
  	protected String statusNameLike;
  	protected List<String> statusNames;
  	protected String result;
  	protected String resultLike;
  	protected List<String> results;

    public MsgValidHistoryQuery() {

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


    public Date getSendDateGreaterThanOrEqual(){
        return sendDateGreaterThanOrEqual;
    }

    public Date getSendDateLessThanOrEqual(){
	return sendDateLessThanOrEqual;
    }


    public Integer getType(){
        return type;
    }

    public Integer getTypeGreaterThanOrEqual(){
        return typeGreaterThanOrEqual;
    }

    public Integer getTypeLessThanOrEqual(){
	return typeLessThanOrEqual;
    }

    public List<Integer> getTypes(){
	return types;
    }

    public String getTypeName(){
        return typeName;
    }

    public String getTypeNameLike(){
	    if (typeNameLike != null && typeNameLike.trim().length() > 0) {
		if (!typeNameLike.startsWith("%")) {
		    typeNameLike = "%" + typeNameLike;
		}
		if (!typeNameLike.endsWith("%")) {
		   typeNameLike = typeNameLike + "%";
		}
	    }
	return typeNameLike;
    }

    public List<String> getTypeNames(){
	return typeNames;
    }


    public String getUrl(){
        return url;
    }

    public String getUrlLike(){
	    if (urlLike != null && urlLike.trim().length() > 0) {
		if (!urlLike.startsWith("%")) {
		    urlLike = "%" + urlLike;
		}
		if (!urlLike.endsWith("%")) {
		   urlLike = urlLike + "%";
		}
	    }
	return urlLike;
    }

    public List<String> getUrls(){
	return urls;
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

    public String getStatusName(){
        return statusName;
    }

    public String getStatusNameLike(){
	    if (statusNameLike != null && statusNameLike.trim().length() > 0) {
		if (!statusNameLike.startsWith("%")) {
		    statusNameLike = "%" + statusNameLike;
		}
		if (!statusNameLike.endsWith("%")) {
		   statusNameLike = statusNameLike + "%";
		}
	    }
	return statusNameLike;
    }

    public List<String> getStatusNames(){
	return statusNames;
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


    public void setSendDateGreaterThanOrEqual(Date sendDateGreaterThanOrEqual){
        this.sendDateGreaterThanOrEqual = sendDateGreaterThanOrEqual;
    }

    public void setSendDateLessThanOrEqual(Date sendDateLessThanOrEqual){
	this.sendDateLessThanOrEqual = sendDateLessThanOrEqual;
    }


    public void setType(Integer type){
        this.type = type;
    }

    public void setTypeGreaterThanOrEqual(Integer typeGreaterThanOrEqual){
        this.typeGreaterThanOrEqual = typeGreaterThanOrEqual;
    }

    public void setTypeLessThanOrEqual(Integer typeLessThanOrEqual){
	this.typeLessThanOrEqual = typeLessThanOrEqual;
    }

    public void setTypes(List<Integer> types){
        this.types = types;
    }


    public void setTypeName(String typeName){
        this.typeName = typeName;
    }

    public void setTypeNameLike( String typeNameLike){
	this.typeNameLike = typeNameLike;
    }

    public void setTypeNames(List<String> typeNames){
        this.typeNames = typeNames;
    }


    public void setUrl(String url){
        this.url = url;
    }

    public void setUrlLike( String urlLike){
	this.urlLike = urlLike;
    }

    public void setUrls(List<String> urls){
        this.urls = urls;
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


    public void setStatusName(String statusName){
        this.statusName = statusName;
    }

    public void setStatusNameLike( String statusNameLike){
	this.statusNameLike = statusNameLike;
    }

    public void setStatusNames(List<String> statusNames){
        this.statusNames = statusNames;
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




    public MsgValidHistoryQuery telephone(String telephone){
	if (telephone == null) {
	    throw new RuntimeException("telephone is null");
        }         
	this.telephone = telephone;
	return this;
    }

    public MsgValidHistoryQuery telephoneLike( String telephoneLike){
        if (telephoneLike == null) {
            throw new RuntimeException("telephone is null");
        }
        this.telephoneLike = telephoneLike;
        return this;
    }

    public MsgValidHistoryQuery telephones(List<String> telephones){
        if (telephones == null) {
            throw new RuntimeException("telephones is empty ");
        }
        this.telephones = telephones;
        return this;
    }



    public MsgValidHistoryQuery sendDateGreaterThanOrEqual(Date sendDateGreaterThanOrEqual){
	if (sendDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("sendDate is null");
        }         
	this.sendDateGreaterThanOrEqual = sendDateGreaterThanOrEqual;
        return this;
    }

    public MsgValidHistoryQuery sendDateLessThanOrEqual(Date sendDateLessThanOrEqual){
        if (sendDateLessThanOrEqual == null) {
            throw new RuntimeException("sendDate is null");
        }
        this.sendDateLessThanOrEqual = sendDateLessThanOrEqual;
        return this;
    }



    public MsgValidHistoryQuery type(Integer type){
	if (type == null) {
            throw new RuntimeException("type is null");
        }         
	this.type = type;
	return this;
    }

    public MsgValidHistoryQuery typeGreaterThanOrEqual(Integer typeGreaterThanOrEqual){
	if (typeGreaterThanOrEqual == null) {
	    throw new RuntimeException("type is null");
        }         
	this.typeGreaterThanOrEqual = typeGreaterThanOrEqual;
        return this;
    }

    public MsgValidHistoryQuery typeLessThanOrEqual(Integer typeLessThanOrEqual){
        if (typeLessThanOrEqual == null) {
            throw new RuntimeException("type is null");
        }
        this.typeLessThanOrEqual = typeLessThanOrEqual;
        return this;
    }

    public MsgValidHistoryQuery types(List<Integer> types){
        if (types == null) {
            throw new RuntimeException("types is empty ");
        }
        this.types = types;
        return this;
    }


    public MsgValidHistoryQuery typeName(String typeName){
	if (typeName == null) {
	    throw new RuntimeException("typeName is null");
        }         
	this.typeName = typeName;
	return this;
    }

    public MsgValidHistoryQuery typeNameLike( String typeNameLike){
        if (typeNameLike == null) {
            throw new RuntimeException("typeName is null");
        }
        this.typeNameLike = typeNameLike;
        return this;
    }

    public MsgValidHistoryQuery typeNames(List<String> typeNames){
        if (typeNames == null) {
            throw new RuntimeException("typeNames is empty ");
        }
        this.typeNames = typeNames;
        return this;
    }


    public MsgValidHistoryQuery url(String url){
	if (url == null) {
	    throw new RuntimeException("url is null");
        }         
	this.url = url;
	return this;
    }

    public MsgValidHistoryQuery urlLike( String urlLike){
        if (urlLike == null) {
            throw new RuntimeException("url is null");
        }
        this.urlLike = urlLike;
        return this;
    }

    public MsgValidHistoryQuery urls(List<String> urls){
        if (urls == null) {
            throw new RuntimeException("urls is empty ");
        }
        this.urls = urls;
        return this;
    }


    public MsgValidHistoryQuery msg(String msg){
	if (msg == null) {
	    throw new RuntimeException("msg is null");
        }         
	this.msg = msg;
	return this;
    }

    public MsgValidHistoryQuery msgLike( String msgLike){
        if (msgLike == null) {
            throw new RuntimeException("msg is null");
        }
        this.msgLike = msgLike;
        return this;
    }

    public MsgValidHistoryQuery msgs(List<String> msgs){
        if (msgs == null) {
            throw new RuntimeException("msgs is empty ");
        }
        this.msgs = msgs;
        return this;
    }


    public MsgValidHistoryQuery status(Integer status){
	if (status == null) {
            throw new RuntimeException("status is null");
        }         
	this.status = status;
	return this;
    }

    public MsgValidHistoryQuery statusGreaterThanOrEqual(Integer statusGreaterThanOrEqual){
	if (statusGreaterThanOrEqual == null) {
	    throw new RuntimeException("status is null");
        }         
	this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
        return this;
    }

    public MsgValidHistoryQuery statusLessThanOrEqual(Integer statusLessThanOrEqual){
        if (statusLessThanOrEqual == null) {
            throw new RuntimeException("status is null");
        }
        this.statusLessThanOrEqual = statusLessThanOrEqual;
        return this;
    }

    public MsgValidHistoryQuery statuss(List<Integer> statuss){
        if (statuss == null) {
            throw new RuntimeException("statuss is empty ");
        }
        this.statuss = statuss;
        return this;
    }


    public MsgValidHistoryQuery statusName(String statusName){
	if (statusName == null) {
	    throw new RuntimeException("statusName is null");
        }         
	this.statusName = statusName;
	return this;
    }

    public MsgValidHistoryQuery statusNameLike( String statusNameLike){
        if (statusNameLike == null) {
            throw new RuntimeException("statusName is null");
        }
        this.statusNameLike = statusNameLike;
        return this;
    }

    public MsgValidHistoryQuery statusNames(List<String> statusNames){
        if (statusNames == null) {
            throw new RuntimeException("statusNames is empty ");
        }
        this.statusNames = statusNames;
        return this;
    }


    public MsgValidHistoryQuery result(String result){
	if (result == null) {
	    throw new RuntimeException("result is null");
        }         
	this.result = result;
	return this;
    }

    public MsgValidHistoryQuery resultLike( String resultLike){
        if (resultLike == null) {
            throw new RuntimeException("result is null");
        }
        this.resultLike = resultLike;
        return this;
    }

    public MsgValidHistoryQuery results(List<String> results){
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

            if ("sendDate".equals(sortColumn)) {
                orderBy = "E.SENDDATE_" + a_x;
            } 

            if ("type".equals(sortColumn)) {
                orderBy = "E.TYPE_" + a_x;
            } 

            if ("typeName".equals(sortColumn)) {
                orderBy = "E.TYPENAME_" + a_x;
            } 

            if ("url".equals(sortColumn)) {
                orderBy = "E.URL_" + a_x;
            } 

            if ("msg".equals(sortColumn)) {
                orderBy = "E.MSG_" + a_x;
            } 

            if ("status".equals(sortColumn)) {
                orderBy = "E.STATUS_" + a_x;
            } 

            if ("statusName".equals(sortColumn)) {
                orderBy = "E.STATUSNAME_" + a_x;
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
        addColumn("sendDate", "SENDDATE_");
        addColumn("type", "TYPE_");
        addColumn("typeName", "TYPENAME_");
        addColumn("url", "URL_");
        addColumn("msg", "MSG_");
        addColumn("status", "STATUS_");
        addColumn("statusName", "STATUSNAME_");
        addColumn("result", "RESULT_");
    }

}