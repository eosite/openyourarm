package com.glaf.teim.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class EimBaseInfoQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String ip;
  	protected String ipLike;
  	protected List<String> ips;
  	protected String name;
  	protected String nameLike;
  	protected List<String> names;
  	protected String host;
  	protected String hostLike;
  	protected List<String> hosts;
  	protected String secret;
  	protected String secretLike;
  	protected List<String> secrets;
  	protected String paasId;
  	protected String paasIdLike;
  	protected List<String> paasIds;
  	protected String createBy;
  	protected String createByLike;
  	protected List<String> createBys;
        protected Date createTimeGreaterThanOrEqual;
  	protected Date createTimeLessThanOrEqual;
  	protected String updateBy;
  	protected String updateByLike;
  	protected List<String> updateBys;
        protected Date updateTimeGreaterThanOrEqual;
  	protected Date updateTimeLessThanOrEqual;

    public EimBaseInfoQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
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


    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameLike() {
		return nameLike;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public String getHost(){
        return host;
    }

    public String getHostLike(){
	    if (hostLike != null && hostLike.trim().length() > 0) {
		if (!hostLike.startsWith("%")) {
		    hostLike = "%" + hostLike;
		}
		if (!hostLike.endsWith("%")) {
		   hostLike = hostLike + "%";
		}
	    }
	return hostLike;
    }

    public List<String> getHosts(){
	return hosts;
    }


    public String getSecret(){
        return secret;
    }

    public String getSecretLike(){
	    if (secretLike != null && secretLike.trim().length() > 0) {
		if (!secretLike.startsWith("%")) {
		    secretLike = "%" + secretLike;
		}
		if (!secretLike.endsWith("%")) {
		   secretLike = secretLike + "%";
		}
	    }
	return secretLike;
    }

    public List<String> getSecrets(){
	return secrets;
    }


    public String getPaasId(){
        return paasId;
    }

    public String getPaasIdLike(){
	    if (paasIdLike != null && paasIdLike.trim().length() > 0) {
		if (!paasIdLike.startsWith("%")) {
		    paasIdLike = "%" + paasIdLike;
		}
		if (!paasIdLike.endsWith("%")) {
		   paasIdLike = paasIdLike + "%";
		}
	    }
	return paasIdLike;
    }

    public List<String> getPaasIds(){
	return paasIds;
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


    public Date getCreateTimeGreaterThanOrEqual(){
        return createTimeGreaterThanOrEqual;
    }

    public Date getCreateTimeLessThanOrEqual(){
	return createTimeLessThanOrEqual;
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


    public Date getUpdateTimeGreaterThanOrEqual(){
        return updateTimeGreaterThanOrEqual;
    }

    public Date getUpdateTimeLessThanOrEqual(){
	return updateTimeLessThanOrEqual;
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


    public void setHost(String host){
        this.host = host;
    }

    public void setHostLike( String hostLike){
	this.hostLike = hostLike;
    }

    public void setHosts(List<String> hosts){
        this.hosts = hosts;
    }


    public void setSecret(String secret){
        this.secret = secret;
    }

    public void setSecretLike( String secretLike){
	this.secretLike = secretLike;
    }

    public void setSecrets(List<String> secrets){
        this.secrets = secrets;
    }


    public void setPaasId(String paasId){
        this.paasId = paasId;
    }

    public void setPaasIdLike( String paasIdLike){
	this.paasIdLike = paasIdLike;
    }

    public void setPaasIds(List<String> paasIds){
        this.paasIds = paasIds;
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


    public void setCreateTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
        this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
    }

    public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
	this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
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


    public void setUpdateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
        this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
    }

    public void setUpdateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
	this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
    }




    public EimBaseInfoQuery ip(String ip){
	if (ip == null) {
	    throw new RuntimeException("ip is null");
        }         
	this.ip = ip;
	return this;
    }

    public EimBaseInfoQuery ipLike( String ipLike){
        if (ipLike == null) {
            throw new RuntimeException("ip is null");
        }
        this.ipLike = ipLike;
        return this;
    }

    public EimBaseInfoQuery ips(List<String> ips){
        if (ips == null) {
            throw new RuntimeException("ips is empty ");
        }
        this.ips = ips;
        return this;
    }


    public EimBaseInfoQuery host(String host){
	if (host == null) {
	    throw new RuntimeException("host is null");
        }         
	this.host = host;
	return this;
    }

    public EimBaseInfoQuery hostLike( String hostLike){
        if (hostLike == null) {
            throw new RuntimeException("host is null");
        }
        this.hostLike = hostLike;
        return this;
    }

    public EimBaseInfoQuery hosts(List<String> hosts){
        if (hosts == null) {
            throw new RuntimeException("hosts is empty ");
        }
        this.hosts = hosts;
        return this;
    }


    public EimBaseInfoQuery secret(String secret){
	if (secret == null) {
	    throw new RuntimeException("secret is null");
        }         
	this.secret = secret;
	return this;
    }

    public EimBaseInfoQuery secretLike( String secretLike){
        if (secretLike == null) {
            throw new RuntimeException("secret is null");
        }
        this.secretLike = secretLike;
        return this;
    }

    public EimBaseInfoQuery secrets(List<String> secrets){
        if (secrets == null) {
            throw new RuntimeException("secrets is empty ");
        }
        this.secrets = secrets;
        return this;
    }


    public EimBaseInfoQuery paasId(String paasId){
	if (paasId == null) {
	    throw new RuntimeException("paasId is null");
        }         
	this.paasId = paasId;
	return this;
    }

    public EimBaseInfoQuery paasIdLike( String paasIdLike){
        if (paasIdLike == null) {
            throw new RuntimeException("paasId is null");
        }
        this.paasIdLike = paasIdLike;
        return this;
    }

    public EimBaseInfoQuery paasIds(List<String> paasIds){
        if (paasIds == null) {
            throw new RuntimeException("paasIds is empty ");
        }
        this.paasIds = paasIds;
        return this;
    }


    public EimBaseInfoQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public EimBaseInfoQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public EimBaseInfoQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public EimBaseInfoQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual){
	if (createTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime is null");
        }         
	this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
        return this;
    }

    public EimBaseInfoQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual){
        if (createTimeLessThanOrEqual == null) {
            throw new RuntimeException("createTime is null");
        }
        this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
        return this;
    }



    public EimBaseInfoQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public EimBaseInfoQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public EimBaseInfoQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public EimBaseInfoQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual){
	if (updateTimeGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime is null");
        }         
	this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
        return this;
    }

    public EimBaseInfoQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual){
        if (updateTimeLessThanOrEqual == null) {
            throw new RuntimeException("updateTime is null");
        }
        this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
        return this;
    }




    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("ip".equals(sortColumn)) {
                orderBy = "E.IP_" + a_x;
            } 

            if ("host".equals(sortColumn)) {
                orderBy = "E.HOST_" + a_x;
            } 

            if ("secret".equals(sortColumn)) {
                orderBy = "E.SECRET_" + a_x;
            } 

            if ("paasId".equals(sortColumn)) {
                orderBy = "E.PAASID_" + a_x;
            } 

            if ("createBy".equals(sortColumn)) {
                orderBy = "E.CREATEBY_" + a_x;
            } 

            if ("createTime".equals(sortColumn)) {
                orderBy = "E.CREATETIME_" + a_x;
            } 

            if ("updateBy".equals(sortColumn)) {
                orderBy = "E.UPDATEBY_" + a_x;
            } 

            if ("updateTime".equals(sortColumn)) {
                orderBy = "E.UPDATETIME_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("ip", "IP_");
        addColumn("host", "HOST_");
        addColumn("secret", "SECRET_");
        addColumn("paasId", "PAASID_");
        addColumn("createBy", "CREATEBY_");
        addColumn("createTime", "CREATETIME_");
        addColumn("updateBy", "UPDATEBY_");
        addColumn("updateTime", "UPDATETIME_");
    }

}