package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FormVideoQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
  	protected String name;
  	protected String nameLike;
  	protected List<String> names;
  	protected String ip;
  	protected String ipLike;
  	protected List<String> ips;
  	protected String port;
  	protected String portLike;
  	protected List<String> ports;
  	protected Integer status;
  	protected Integer statusGreaterThanOrEqual;
  	protected Integer statusLessThanOrEqual;
  	protected List<Integer> statuss;
  	protected Integer valided;
  	protected Integer validedGreaterThanOrEqual;
  	protected Integer validedLessThanOrEqual;
  	protected List<Integer> valideds;
  	protected String userName;
  	protected String userNameLike;
  	protected List<String> userNames;
  	protected String pwd;
  	protected String pwdLike;
  	protected List<String> pwds;
  	protected String updateBy;
  	protected String updateByLike;
  	protected List<String> updateBys;
        protected Date createDateGreaterThanOrEqual;
  	protected Date createDateLessThanOrEqual;
        protected Date updateDateGreaterThanOrEqual;
  	protected Date updateDateLessThanOrEqual;
  	protected String createBy;
  	protected String createByLike;
  	protected List<String> createBys;

    public FormVideoQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
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


    public String getIp(){
        return ip;
    }

    public String getIpLike(){
	    if (ipLike != null && ipLike.trim().length() > 0) {
		if (!ipLike.startsWith("%")) {
		    ipLike = "%" + ipLike;
		}
		if (!ipLike.endsWith("%")) {
		   ipLike = ipLike + "%";
		}
	    }
	return ipLike;
    }

    public List<String> getIps(){
	return ips;
    }


    public String getPort(){
        return port;
    }

    public String getPortLike(){
	    if (portLike != null && portLike.trim().length() > 0) {
		if (!portLike.startsWith("%")) {
		    portLike = "%" + portLike;
		}
		if (!portLike.endsWith("%")) {
		   portLike = portLike + "%";
		}
	    }
	return portLike;
    }

    public List<String> getPorts(){
	return ports;
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

    public Integer getValided(){
        return valided;
    }

    public Integer getValidedGreaterThanOrEqual(){
        return validedGreaterThanOrEqual;
    }

    public Integer getValidedLessThanOrEqual(){
	return validedLessThanOrEqual;
    }

    public List<Integer> getValideds(){
	return valideds;
    }

    public String getUserName(){
        return userName;
    }

    public String getUserNameLike(){
	    if (userNameLike != null && userNameLike.trim().length() > 0) {
		if (!userNameLike.startsWith("%")) {
		    userNameLike = "%" + userNameLike;
		}
		if (!userNameLike.endsWith("%")) {
		   userNameLike = userNameLike + "%";
		}
	    }
	return userNameLike;
    }

    public List<String> getUserNames(){
	return userNames;
    }


    public String getPwd(){
        return pwd;
    }

    public String getPwdLike(){
	    if (pwdLike != null && pwdLike.trim().length() > 0) {
		if (!pwdLike.startsWith("%")) {
		    pwdLike = "%" + pwdLike;
		}
		if (!pwdLike.endsWith("%")) {
		   pwdLike = pwdLike + "%";
		}
	    }
	return pwdLike;
    }

    public List<String> getPwds(){
	return pwds;
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


 

    public void setName(String name){
        this.name = name;
    }

    public void setNameLike( String nameLike){
	this.nameLike = nameLike;
    }

    public void setNames(List<String> names){
        this.names = names;
    }


    public void setIp(String ip){
        this.ip = ip;
    }

    public void setIpLike( String ipLike){
	this.ipLike = ipLike;
    }

    public void setIps(List<String> ips){
        this.ips = ips;
    }


    public void setPort(String port){
        this.port = port;
    }

    public void setPortLike( String portLike){
	this.portLike = portLike;
    }

    public void setPorts(List<String> ports){
        this.ports = ports;
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


    public void setValided(Integer valided){
        this.valided = valided;
    }

    public void setValidedGreaterThanOrEqual(Integer validedGreaterThanOrEqual){
        this.validedGreaterThanOrEqual = validedGreaterThanOrEqual;
    }

    public void setValidedLessThanOrEqual(Integer validedLessThanOrEqual){
	this.validedLessThanOrEqual = validedLessThanOrEqual;
    }

    public void setValideds(List<Integer> valideds){
        this.valideds = valideds;
    }


    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setUserNameLike( String userNameLike){
	this.userNameLike = userNameLike;
    }

    public void setUserNames(List<String> userNames){
        this.userNames = userNames;
    }


    public void setPwd(String pwd){
        this.pwd = pwd;
    }

    public void setPwdLike( String pwdLike){
	this.pwdLike = pwdLike;
    }

    public void setPwds(List<String> pwds){
        this.pwds = pwds;
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




    public FormVideoQuery name(String name){
	if (name == null) {
	    throw new RuntimeException("name is null");
        }         
	this.name = name;
	return this;
    }

    public FormVideoQuery nameLike( String nameLike){
        if (nameLike == null) {
            throw new RuntimeException("name is null");
        }
        this.nameLike = nameLike;
        return this;
    }

    public FormVideoQuery names(List<String> names){
        if (names == null) {
            throw new RuntimeException("names is empty ");
        }
        this.names = names;
        return this;
    }


    public FormVideoQuery ip(String ip){
	if (ip == null) {
	    throw new RuntimeException("ip is null");
        }         
	this.ip = ip;
	return this;
    }

    public FormVideoQuery ipLike( String ipLike){
        if (ipLike == null) {
            throw new RuntimeException("ip is null");
        }
        this.ipLike = ipLike;
        return this;
    }

    public FormVideoQuery ips(List<String> ips){
        if (ips == null) {
            throw new RuntimeException("ips is empty ");
        }
        this.ips = ips;
        return this;
    }


    public FormVideoQuery port(String port){
	if (port == null) {
	    throw new RuntimeException("port is null");
        }         
	this.port = port;
	return this;
    }

    public FormVideoQuery portLike( String portLike){
        if (portLike == null) {
            throw new RuntimeException("port is null");
        }
        this.portLike = portLike;
        return this;
    }

    public FormVideoQuery ports(List<String> ports){
        if (ports == null) {
            throw new RuntimeException("ports is empty ");
        }
        this.ports = ports;
        return this;
    }


    public FormVideoQuery status(Integer status){
	if (status == null) {
            throw new RuntimeException("status is null");
        }         
	this.status = status;
	return this;
    }

    public FormVideoQuery statusGreaterThanOrEqual(Integer statusGreaterThanOrEqual){
	if (statusGreaterThanOrEqual == null) {
	    throw new RuntimeException("status is null");
        }         
	this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
        return this;
    }

    public FormVideoQuery statusLessThanOrEqual(Integer statusLessThanOrEqual){
        if (statusLessThanOrEqual == null) {
            throw new RuntimeException("status is null");
        }
        this.statusLessThanOrEqual = statusLessThanOrEqual;
        return this;
    }

    public FormVideoQuery statuss(List<Integer> statuss){
        if (statuss == null) {
            throw new RuntimeException("statuss is empty ");
        }
        this.statuss = statuss;
        return this;
    }


    public FormVideoQuery valided(Integer valided){
	if (valided == null) {
            throw new RuntimeException("valided is null");
        }         
	this.valided = valided;
	return this;
    }

    public FormVideoQuery validedGreaterThanOrEqual(Integer validedGreaterThanOrEqual){
	if (validedGreaterThanOrEqual == null) {
	    throw new RuntimeException("valided is null");
        }         
	this.validedGreaterThanOrEqual = validedGreaterThanOrEqual;
        return this;
    }

    public FormVideoQuery validedLessThanOrEqual(Integer validedLessThanOrEqual){
        if (validedLessThanOrEqual == null) {
            throw new RuntimeException("valided is null");
        }
        this.validedLessThanOrEqual = validedLessThanOrEqual;
        return this;
    }

    public FormVideoQuery valideds(List<Integer> valideds){
        if (valideds == null) {
            throw new RuntimeException("valideds is empty ");
        }
        this.valideds = valideds;
        return this;
    }


    public FormVideoQuery userName(String userName){
	if (userName == null) {
	    throw new RuntimeException("userName is null");
        }         
	this.userName = userName;
	return this;
    }

    public FormVideoQuery userNameLike( String userNameLike){
        if (userNameLike == null) {
            throw new RuntimeException("userName is null");
        }
        this.userNameLike = userNameLike;
        return this;
    }

    public FormVideoQuery userNames(List<String> userNames){
        if (userNames == null) {
            throw new RuntimeException("userNames is empty ");
        }
        this.userNames = userNames;
        return this;
    }


    public FormVideoQuery pwd(String pwd){
	if (pwd == null) {
	    throw new RuntimeException("pwd is null");
        }         
	this.pwd = pwd;
	return this;
    }

    public FormVideoQuery pwdLike( String pwdLike){
        if (pwdLike == null) {
            throw new RuntimeException("pwd is null");
        }
        this.pwdLike = pwdLike;
        return this;
    }

    public FormVideoQuery pwds(List<String> pwds){
        if (pwds == null) {
            throw new RuntimeException("pwds is empty ");
        }
        this.pwds = pwds;
        return this;
    }


    public FormVideoQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public FormVideoQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public FormVideoQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public FormVideoQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
	if (createDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDate is null");
        }         
	this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
        return this;
    }

    public FormVideoQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual){
        if (createDateLessThanOrEqual == null) {
            throw new RuntimeException("createDate is null");
        }
        this.createDateLessThanOrEqual = createDateLessThanOrEqual;
        return this;
    }




    public FormVideoQuery updateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual){
	if (updateDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateDate is null");
        }         
	this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
        return this;
    }

    public FormVideoQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual){
        if (updateDateLessThanOrEqual == null) {
            throw new RuntimeException("updateDate is null");
        }
        this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
        return this;
    }



    public FormVideoQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public FormVideoQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public FormVideoQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("name".equals(sortColumn)) {
                orderBy = "E.NAME_" + a_x;
            } 

            if ("ip".equals(sortColumn)) {
                orderBy = "E.IP_" + a_x;
            } 

            if ("port".equals(sortColumn)) {
                orderBy = "E.PORT_" + a_x;
            } 

            if ("status".equals(sortColumn)) {
                orderBy = "E.STATUS_" + a_x;
            } 

            if ("valided".equals(sortColumn)) {
                orderBy = "E.VALIDED_" + a_x;
            } 

            if ("userName".equals(sortColumn)) {
                orderBy = "E.USERNAME_" + a_x;
            } 

            if ("pwd".equals(sortColumn)) {
                orderBy = "E.PWD_" + a_x;
            } 

            if ("updateBy".equals(sortColumn)) {
                orderBy = "E.UPDATEBY_" + a_x;
            } 

            if ("createDate".equals(sortColumn)) {
                orderBy = "E.CREATEDATE_" + a_x;
            } 

            if ("updateDate".equals(sortColumn)) {
                orderBy = "E.UPDATEDATE_" + a_x;
            } 

            if ("createBy".equals(sortColumn)) {
                orderBy = "E.CREATEBY_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("name", "NAME_");
        addColumn("ip", "IP_");
        addColumn("port", "PORT_");
        addColumn("status", "STATUS_");
        addColumn("valided", "VALIDED_");
        addColumn("userName", "USERNAME_");
        addColumn("pwd", "PWD_");
        addColumn("updateBy", "UPDATEBY_");
        addColumn("createDate", "CREATEDATE_");
        addColumn("updateDate", "UPDATEDATE_");
        addColumn("createBy", "CREATEBY_");
    }

}