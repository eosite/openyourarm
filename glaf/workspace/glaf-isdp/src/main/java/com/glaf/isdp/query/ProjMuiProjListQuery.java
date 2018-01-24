package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ProjMuiProjListQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Integer> indexIds;
	protected Collection<String> appActorIds;
	protected String id;
	protected String idLike;
	protected List<String> ids;
	protected Integer intFlag;
	protected Integer intFlagGreaterThanOrEqual;
	protected Integer intFlagLessThanOrEqual;
	protected List<Integer> intFlags;
	protected String sysId;
	protected String sysIdLike;
	protected List<String> sysIds;
	protected String projName;
	protected String projNameLike;
	protected List<String> projNames;
	protected String num;
	protected String numLike;
	protected List<String> nums;
	protected Date ctimeGreaterThanOrEqual;
	protected Date ctimeLessThanOrEqual;
	protected String content;
	protected String contentLike;
	protected List<String> contents;
	protected String dbName;
	protected String dbNameLike;
	protected List<String> dbNames;
	protected String serverName;
	protected String serverNameLike;
	protected List<String> serverNames;
	protected String user;
	protected String userLike;
	protected List<String> users;
	protected String password;
	protected String passwordLike;
	protected List<String> passwords;
	protected Integer listNo;
	protected Integer listNoGreaterThanOrEqual;
	protected Integer listNoLessThanOrEqual;
	protected List<Integer> listNos;
	protected String email;
	protected String emailLike;
	protected List<String> emailList;

	protected Integer nodeIco;
	protected Integer nodeIcoGreaterThanOrEqual;
	protected Integer nodeIcoLessThanOrEqual;
	protected List<Integer> nodeIcos;
	protected Integer intLine;
	protected Integer intLineGreaterThanOrEqual;
	protected Integer intLineLessThanOrEqual;
	protected List<Integer> intLines;
	protected Integer domainIndex;
	protected Integer domainIndexGreaterThanOrEqual;
	protected Integer domainIndexLessThanOrEqual;
	protected List<Integer> domainIndexs;
	protected Integer intLocal;
	protected Integer intLocalGreaterThanOrEqual;
	protected Integer intLocalLessThanOrEqual;
	protected List<Integer> intLocals;
	protected String emailPSW;
	protected String emailPSWLike;
	protected List<String> emailPSWs;
	protected Integer intConnected;
	protected Integer intConnectedGreaterThanOrEqual;
	protected Integer intConnectedLessThanOrEqual;
	protected List<Integer> intConnecteds;
	protected String emails;
	protected String emailsLike;
	protected List<String> emailss;
	protected Integer intorgLevel;
	protected Integer intorgLevelGreaterThanOrEqual;
	protected Integer intorgLevelLessThanOrEqual;
	protected List<Integer> intorgLevels;
	protected Integer intSendType;
	protected Integer intSendTypeGreaterThanOrEqual;
	protected Integer intSendTypeLessThanOrEqual;
	protected List<Integer> intSendTypes;
	protected String emailBackup;
	protected String emailBackupLike;
	protected List<String> emailBackups;
	protected String emailImplement;
	protected String emailImplementLike;
	protected List<String> emailImplements;

	public ProjMuiProjListQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getId() {
		return id;
	}

	public String getIdLike() {
		if (idLike != null && idLike.trim().length() > 0) {
			if (!idLike.startsWith("%")) {
				idLike = "%" + idLike;
			}
			if (!idLike.endsWith("%")) {
				idLike = idLike + "%";
			}
		}
		return idLike;
	}

	public List<String> getIds() {
		return ids;
	}

	public Integer getIntFlag() {
		return intFlag;
	}

	public Integer getIntFlagGreaterThanOrEqual() {
		return intFlagGreaterThanOrEqual;
	}

	public Integer getIntFlagLessThanOrEqual() {
		return intFlagLessThanOrEqual;
	}

	public List<Integer> getIntFlags() {
		return intFlags;
	}

	public String getSysId() {
		return sysId;
	}

	public String getSysIdLike() {
		if (sysIdLike != null && sysIdLike.trim().length() > 0) {
			if (!sysIdLike.startsWith("%")) {
				sysIdLike = "%" + sysIdLike;
			}
			if (!sysIdLike.endsWith("%")) {
				sysIdLike = sysIdLike + "%";
			}
		}
		return sysIdLike;
	}

	public List<String> getSysIds() {
		return sysIds;
	}

	public String getProjName() {
		return projName;
	}

	public String getProjNameLike() {
		if (projNameLike != null && projNameLike.trim().length() > 0) {
			if (!projNameLike.startsWith("%")) {
				projNameLike = "%" + projNameLike;
			}
			if (!projNameLike.endsWith("%")) {
				projNameLike = projNameLike + "%";
			}
		}
		return projNameLike;
	}

	public List<String> getProjNames() {
		return projNames;
	}

	public String getNum() {
		return num;
	}

	public String getNumLike() {
		if (numLike != null && numLike.trim().length() > 0) {
			if (!numLike.startsWith("%")) {
				numLike = "%" + numLike;
			}
			if (!numLike.endsWith("%")) {
				numLike = numLike + "%";
			}
		}
		return numLike;
	}

	public List<String> getNums() {
		return nums;
	}

	public Date getCtimeGreaterThanOrEqual() {
		return ctimeGreaterThanOrEqual;
	}

	public Date getCtimeLessThanOrEqual() {
		return ctimeLessThanOrEqual;
	}

	public String getContent() {
		return content;
	}

	public String getContentLike() {
		if (contentLike != null && contentLike.trim().length() > 0) {
			if (!contentLike.startsWith("%")) {
				contentLike = "%" + contentLike;
			}
			if (!contentLike.endsWith("%")) {
				contentLike = contentLike + "%";
			}
		}
		return contentLike;
	}

	public List<String> getContents() {
		return contents;
	}

	public String getDbName() {
		return dbName;
	}

	public String getDbNameLike() {
		if (dbNameLike != null && dbNameLike.trim().length() > 0) {
			if (!dbNameLike.startsWith("%")) {
				dbNameLike = "%" + dbNameLike;
			}
			if (!dbNameLike.endsWith("%")) {
				dbNameLike = dbNameLike + "%";
			}
		}
		return dbNameLike;
	}

	public List<String> getDbNames() {
		return dbNames;
	}

	public String getServerName() {
		return serverName;
	}

	public String getServerNameLike() {
		if (serverNameLike != null && serverNameLike.trim().length() > 0) {
			if (!serverNameLike.startsWith("%")) {
				serverNameLike = "%" + serverNameLike;
			}
			if (!serverNameLike.endsWith("%")) {
				serverNameLike = serverNameLike + "%";
			}
		}
		return serverNameLike;
	}

	public List<String> getServerNames() {
		return serverNames;
	}

	public String getUser() {
		return user;
	}

	public String getUserLike() {
		if (userLike != null && userLike.trim().length() > 0) {
			if (!userLike.startsWith("%")) {
				userLike = "%" + userLike;
			}
			if (!userLike.endsWith("%")) {
				userLike = userLike + "%";
			}
		}
		return userLike;
	}

	public List<String> getUsers() {
		return users;
	}

	public String getPassword() {
		return password;
	}

	public String getPasswordLike() {
		if (passwordLike != null && passwordLike.trim().length() > 0) {
			if (!passwordLike.startsWith("%")) {
				passwordLike = "%" + passwordLike;
			}
			if (!passwordLike.endsWith("%")) {
				passwordLike = passwordLike + "%";
			}
		}
		return passwordLike;
	}

	public List<String> getPasswords() {
		return passwords;
	}

	public Integer getListNo() {
		return listNo;
	}

	public Integer getListNoGreaterThanOrEqual() {
		return listNoGreaterThanOrEqual;
	}

	public Integer getListNoLessThanOrEqual() {
		return listNoLessThanOrEqual;
	}

	public List<Integer> getListNos() {
		return listNos;
	}

	public String getEmail() {
		return email;
	}

	public String getEmailLike() {
		if (emailLike != null && emailLike.trim().length() > 0) {
			if (!emailLike.startsWith("%")) {
				emailLike = "%" + emailLike;
			}
			if (!emailLike.endsWith("%")) {
				emailLike = emailLike + "%";
			}
		}
		return emailLike;
	}

	public List<String> getEmailList() {
		return emailList;
	}

	// public Integer getParentId() {
	// return parentId;
	// }
	//
	// public Integer getParentIdGreaterThanOrEqual() {
	// return parentIdGreaterThanOrEqual;
	// }
	//
	// public Integer getParentIdLessThanOrEqual() {
	// return parentIdLessThanOrEqual;
	// }
	//
	// public List<Integer> getParentIds() {
	// return parentIds;
	// }

	public Integer getNodeIco() {
		return nodeIco;
	}

	public Integer getNodeIcoGreaterThanOrEqual() {
		return nodeIcoGreaterThanOrEqual;
	}

	public Integer getNodeIcoLessThanOrEqual() {
		return nodeIcoLessThanOrEqual;
	}

	public List<Integer> getNodeIcos() {
		return nodeIcos;
	}

	public Integer getIntLine() {
		return intLine;
	}

	public Integer getIntLineGreaterThanOrEqual() {
		return intLineGreaterThanOrEqual;
	}

	public Integer getIntLineLessThanOrEqual() {
		return intLineLessThanOrEqual;
	}

	public List<Integer> getIntLines() {
		return intLines;
	}

	public Integer getDomainIndex() {
		return domainIndex;
	}

	public Integer getDomainIndexGreaterThanOrEqual() {
		return domainIndexGreaterThanOrEqual;
	}

	public Integer getDomainIndexLessThanOrEqual() {
		return domainIndexLessThanOrEqual;
	}

	public List<Integer> getDomainIndexs() {
		return domainIndexs;
	}

	public Integer getIntLocal() {
		return intLocal;
	}

	public Integer getIntLocalGreaterThanOrEqual() {
		return intLocalGreaterThanOrEqual;
	}

	public Integer getIntLocalLessThanOrEqual() {
		return intLocalLessThanOrEqual;
	}

	public List<Integer> getIntLocals() {
		return intLocals;
	}

	public String getEmailPSW() {
		return emailPSW;
	}

	public String getEmailPSWLike() {
		if (emailPSWLike != null && emailPSWLike.trim().length() > 0) {
			if (!emailPSWLike.startsWith("%")) {
				emailPSWLike = "%" + emailPSWLike;
			}
			if (!emailPSWLike.endsWith("%")) {
				emailPSWLike = emailPSWLike + "%";
			}
		}
		return emailPSWLike;
	}

	public List<String> getEmailPSWs() {
		return emailPSWs;
	}

	public Integer getIntConnected() {
		return intConnected;
	}

	public Integer getIntConnectedGreaterThanOrEqual() {
		return intConnectedGreaterThanOrEqual;
	}

	public Integer getIntConnectedLessThanOrEqual() {
		return intConnectedLessThanOrEqual;
	}

	public List<Integer> getIntConnecteds() {
		return intConnecteds;
	}

	public String getEmails() {
		return emails;
	}

	public String getEmailsLike() {
		if (emailsLike != null && emailsLike.trim().length() > 0) {
			if (!emailsLike.startsWith("%")) {
				emailsLike = "%" + emailsLike;
			}
			if (!emailsLike.endsWith("%")) {
				emailsLike = emailsLike + "%";
			}
		}
		return emailsLike;
	}

	public List<String> getEmailss() {
		return emailss;
	}

	public Integer getIntorgLevel() {
		return intorgLevel;
	}

	public Integer getIntorgLevelGreaterThanOrEqual() {
		return intorgLevelGreaterThanOrEqual;
	}

	public Integer getIntorgLevelLessThanOrEqual() {
		return intorgLevelLessThanOrEqual;
	}

	public List<Integer> getIntorgLevels() {
		return intorgLevels;
	}

	public Integer getIntSendType() {
		return intSendType;
	}

	public Integer getIntSendTypeGreaterThanOrEqual() {
		return intSendTypeGreaterThanOrEqual;
	}

	public Integer getIntSendTypeLessThanOrEqual() {
		return intSendTypeLessThanOrEqual;
	}

	public List<Integer> getIntSendTypes() {
		return intSendTypes;
	}

	public String getEmailBackup() {
		return emailBackup;
	}

	public String getEmailBackupLike() {
		if (emailBackupLike != null && emailBackupLike.trim().length() > 0) {
			if (!emailBackupLike.startsWith("%")) {
				emailBackupLike = "%" + emailBackupLike;
			}
			if (!emailBackupLike.endsWith("%")) {
				emailBackupLike = emailBackupLike + "%";
			}
		}
		return emailBackupLike;
	}

	public List<String> getEmailBackups() {
		return emailBackups;
	}

	public String getEmailImplement() {
		return emailImplement;
	}

	public String getEmailImplementLike() {
		if (emailImplementLike != null
				&& emailImplementLike.trim().length() > 0) {
			if (!emailImplementLike.startsWith("%")) {
				emailImplementLike = "%" + emailImplementLike;
			}
			if (!emailImplementLike.endsWith("%")) {
				emailImplementLike = emailImplementLike + "%";
			}
		}
		return emailImplementLike;
	}

	public List<String> getEmailImplements() {
		return emailImplements;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIdLike(String idLike) {
		this.idLike = idLike;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public void setIntFlag(Integer intFlag) {
		this.intFlag = intFlag;
	}

	public void setIntFlagGreaterThanOrEqual(Integer intFlagGreaterThanOrEqual) {
		this.intFlagGreaterThanOrEqual = intFlagGreaterThanOrEqual;
	}

	public void setIntFlagLessThanOrEqual(Integer intFlagLessThanOrEqual) {
		this.intFlagLessThanOrEqual = intFlagLessThanOrEqual;
	}

	public void setIntFlags(List<Integer> intFlags) {
		this.intFlags = intFlags;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public void setSysIdLike(String sysIdLike) {
		this.sysIdLike = sysIdLike;
	}

	public void setSysIds(List<String> sysIds) {
		this.sysIds = sysIds;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public void setProjNameLike(String projNameLike) {
		this.projNameLike = projNameLike;
	}

	public void setProjNames(List<String> projNames) {
		this.projNames = projNames;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public void setNumLike(String numLike) {
		this.numLike = numLike;
	}

	public void setNums(List<String> nums) {
		this.nums = nums;
	}

	public void setCtimeGreaterThanOrEqual(Date ctimeGreaterThanOrEqual) {
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
	}

	public void setCtimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setContentLike(String contentLike) {
		this.contentLike = contentLike;
	}

	public void setContents(List<String> contents) {
		this.contents = contents;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public void setDbNameLike(String dbNameLike) {
		this.dbNameLike = dbNameLike;
	}

	public void setDbNames(List<String> dbNames) {
		this.dbNames = dbNames;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public void setServerNameLike(String serverNameLike) {
		this.serverNameLike = serverNameLike;
	}

	public void setServerNames(List<String> serverNames) {
		this.serverNames = serverNames;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setUserLike(String userLike) {
		this.userLike = userLike;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPasswordLike(String passwordLike) {
		this.passwordLike = passwordLike;
	}

	public void setPasswords(List<String> passwords) {
		this.passwords = passwords;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public void setListNoGreaterThanOrEqual(Integer listNoGreaterThanOrEqual) {
		this.listNoGreaterThanOrEqual = listNoGreaterThanOrEqual;
	}

	public void setListNoLessThanOrEqual(Integer listNoLessThanOrEqual) {
		this.listNoLessThanOrEqual = listNoLessThanOrEqual;
	}

	public void setListNos(List<Integer> listNos) {
		this.listNos = listNos;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEmailLike(String emailLike) {
		this.emailLike = emailLike;
	}

	public void setEmails(List<String> emails) {
		this.emailList = emails;
	}

	// public void setParentId(Integer parentId) {
	// this.parentId = parentId;
	// }
	//
	// public void setParentIdGreaterThanOrEqual(Integer
	// parentIdGreaterThanOrEqual) {
	// this.parentIdGreaterThanOrEqual = parentIdGreaterThanOrEqual;
	// }
	//
	// public void setParentIdLessThanOrEqual(Integer parentIdLessThanOrEqual) {
	// this.parentIdLessThanOrEqual = parentIdLessThanOrEqual;
	// }
	//
	// public void setParentIds(List<Integer> parentIds) {
	// this.parentIds = parentIds;
	// }

	public void setNodeIco(Integer nodeIco) {
		this.nodeIco = nodeIco;
	}

	public void setNodeIcoGreaterThanOrEqual(Integer nodeIcoGreaterThanOrEqual) {
		this.nodeIcoGreaterThanOrEqual = nodeIcoGreaterThanOrEqual;
	}

	public void setNodeIcoLessThanOrEqual(Integer nodeIcoLessThanOrEqual) {
		this.nodeIcoLessThanOrEqual = nodeIcoLessThanOrEqual;
	}

	public void setNodeIcos(List<Integer> nodeIcos) {
		this.nodeIcos = nodeIcos;
	}

	public void setIntLine(Integer intLine) {
		this.intLine = intLine;
	}

	public void setIntLineGreaterThanOrEqual(Integer intLineGreaterThanOrEqual) {
		this.intLineGreaterThanOrEqual = intLineGreaterThanOrEqual;
	}

	public void setIntLineLessThanOrEqual(Integer intLineLessThanOrEqual) {
		this.intLineLessThanOrEqual = intLineLessThanOrEqual;
	}

	public void setIntLines(List<Integer> intLines) {
		this.intLines = intLines;
	}

	public void setDomainIndex(Integer domainIndex) {
		this.domainIndex = domainIndex;
	}

	public void setDomainIndexGreaterThanOrEqual(
			Integer domainIndexGreaterThanOrEqual) {
		this.domainIndexGreaterThanOrEqual = domainIndexGreaterThanOrEqual;
	}

	public void setDomainIndexLessThanOrEqual(Integer domainIndexLessThanOrEqual) {
		this.domainIndexLessThanOrEqual = domainIndexLessThanOrEqual;
	}

	public void setDomainIndexs(List<Integer> domainIndexs) {
		this.domainIndexs = domainIndexs;
	}

	public void setIntLocal(Integer intLocal) {
		this.intLocal = intLocal;
	}

	public void setIntLocalGreaterThanOrEqual(Integer intLocalGreaterThanOrEqual) {
		this.intLocalGreaterThanOrEqual = intLocalGreaterThanOrEqual;
	}

	public void setIntLocalLessThanOrEqual(Integer intLocalLessThanOrEqual) {
		this.intLocalLessThanOrEqual = intLocalLessThanOrEqual;
	}

	public void setIntLocals(List<Integer> intLocals) {
		this.intLocals = intLocals;
	}

	public void setEmailPSW(String emailPSW) {
		this.emailPSW = emailPSW;
	}

	public void setEmailPSWLike(String emailPSWLike) {
		this.emailPSWLike = emailPSWLike;
	}

	public void setEmailPSWs(List<String> emailPSWs) {
		this.emailPSWs = emailPSWs;
	}

	public void setIntConnected(Integer intConnected) {
		this.intConnected = intConnected;
	}

	public void setIntConnectedGreaterThanOrEqual(
			Integer intConnectedGreaterThanOrEqual) {
		this.intConnectedGreaterThanOrEqual = intConnectedGreaterThanOrEqual;
	}

	public void setIntConnectedLessThanOrEqual(
			Integer intConnectedLessThanOrEqual) {
		this.intConnectedLessThanOrEqual = intConnectedLessThanOrEqual;
	}

	public void setIntConnecteds(List<Integer> intConnecteds) {
		this.intConnecteds = intConnecteds;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

	public void setEmailsLike(String emailsLike) {
		this.emailsLike = emailsLike;
	}

	public void setEmailss(List<String> emailss) {
		this.emailss = emailss;
	}

	public void setIntorgLevel(Integer intorgLevel) {
		this.intorgLevel = intorgLevel;
	}

	public void setIntorgLevelGreaterThanOrEqual(
			Integer intorgLevelGreaterThanOrEqual) {
		this.intorgLevelGreaterThanOrEqual = intorgLevelGreaterThanOrEqual;
	}

	public void setIntorgLevelLessThanOrEqual(Integer intorgLevelLessThanOrEqual) {
		this.intorgLevelLessThanOrEqual = intorgLevelLessThanOrEqual;
	}

	public void setIntorgLevels(List<Integer> intorgLevels) {
		this.intorgLevels = intorgLevels;
	}

	public void setIntSendType(Integer intSendType) {
		this.intSendType = intSendType;
	}

	public void setIntSendTypeGreaterThanOrEqual(
			Integer intSendTypeGreaterThanOrEqual) {
		this.intSendTypeGreaterThanOrEqual = intSendTypeGreaterThanOrEqual;
	}

	public void setIntSendTypeLessThanOrEqual(Integer intSendTypeLessThanOrEqual) {
		this.intSendTypeLessThanOrEqual = intSendTypeLessThanOrEqual;
	}

	public void setIntSendTypes(List<Integer> intSendTypes) {
		this.intSendTypes = intSendTypes;
	}

	public void setEmailBackup(String emailBackup) {
		this.emailBackup = emailBackup;
	}

	public void setEmailBackupLike(String emailBackupLike) {
		this.emailBackupLike = emailBackupLike;
	}

	public void setEmailBackups(List<String> emailBackups) {
		this.emailBackups = emailBackups;
	}

	public void setEmailImplement(String emailImplement) {
		this.emailImplement = emailImplement;
	}

	public void setEmailImplementLike(String emailImplementLike) {
		this.emailImplementLike = emailImplementLike;
	}

	public void setEmailImplements(List<String> emailImplements) {
		this.emailImplements = emailImplements;
	}

	public ProjMuiProjListQuery id(String id) {
		if (id == null) {
			throw new RuntimeException("id is null");
		}
		this.id = id;
		return this;
	}

	public ProjMuiProjListQuery idLike(String idLike) {
		if (idLike == null) {
			throw new RuntimeException("id is null");
		}
		this.idLike = idLike;
		return this;
	}

	public ProjMuiProjListQuery ids(List<String> ids) {
		if (ids == null) {
			throw new RuntimeException("ids is empty ");
		}
		this.ids = ids;
		return this;
	}

	public ProjMuiProjListQuery intFlag(Integer intFlag) {
		if (intFlag == null) {
			throw new RuntimeException("intFlag is null");
		}
		this.intFlag = intFlag;
		return this;
	}

	public ProjMuiProjListQuery intFlagGreaterThanOrEqual(
			Integer intFlagGreaterThanOrEqual) {
		if (intFlagGreaterThanOrEqual == null) {
			throw new RuntimeException("intFlag is null");
		}
		this.intFlagGreaterThanOrEqual = intFlagGreaterThanOrEqual;
		return this;
	}

	public ProjMuiProjListQuery intFlagLessThanOrEqual(
			Integer intFlagLessThanOrEqual) {
		if (intFlagLessThanOrEqual == null) {
			throw new RuntimeException("intFlag is null");
		}
		this.intFlagLessThanOrEqual = intFlagLessThanOrEqual;
		return this;
	}

	public ProjMuiProjListQuery intFlags(List<Integer> intFlags) {
		if (intFlags == null) {
			throw new RuntimeException("intFlags is empty ");
		}
		this.intFlags = intFlags;
		return this;
	}

	public ProjMuiProjListQuery sysId(String sysId) {
		if (sysId == null) {
			throw new RuntimeException("sysId is null");
		}
		this.sysId = sysId;
		return this;
	}

	public ProjMuiProjListQuery sysIdLike(String sysIdLike) {
		if (sysIdLike == null) {
			throw new RuntimeException("sysId is null");
		}
		this.sysIdLike = sysIdLike;
		return this;
	}

	public ProjMuiProjListQuery sysIds(List<String> sysIds) {
		if (sysIds == null) {
			throw new RuntimeException("sysIds is empty ");
		}
		this.sysIds = sysIds;
		return this;
	}

	public ProjMuiProjListQuery projName(String projName) {
		if (projName == null) {
			throw new RuntimeException("projName is null");
		}
		this.projName = projName;
		return this;
	}

	public ProjMuiProjListQuery projNameLike(String projNameLike) {
		if (projNameLike == null) {
			throw new RuntimeException("projName is null");
		}
		this.projNameLike = projNameLike;
		return this;
	}

	public ProjMuiProjListQuery projNames(List<String> projNames) {
		if (projNames == null) {
			throw new RuntimeException("projNames is empty ");
		}
		this.projNames = projNames;
		return this;
	}

	public ProjMuiProjListQuery num(String num) {
		if (num == null) {
			throw new RuntimeException("num is null");
		}
		this.num = num;
		return this;
	}

	public ProjMuiProjListQuery numLike(String numLike) {
		if (numLike == null) {
			throw new RuntimeException("num is null");
		}
		this.numLike = numLike;
		return this;
	}

	public ProjMuiProjListQuery nums(List<String> nums) {
		if (nums == null) {
			throw new RuntimeException("nums is empty ");
		}
		this.nums = nums;
		return this;
	}

	public ProjMuiProjListQuery ctimeGreaterThanOrEqual(
			Date ctimeGreaterThanOrEqual) {
		if (ctimeGreaterThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
		return this;
	}

	public ProjMuiProjListQuery ctimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		if (ctimeLessThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
		return this;
	}

	public ProjMuiProjListQuery content(String content) {
		if (content == null) {
			throw new RuntimeException("content is null");
		}
		this.content = content;
		return this;
	}

	public ProjMuiProjListQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public ProjMuiProjListQuery contents(List<String> contents) {
		if (contents == null) {
			throw new RuntimeException("contents is empty ");
		}
		this.contents = contents;
		return this;
	}

	public ProjMuiProjListQuery dbName(String dbName) {
		if (dbName == null) {
			throw new RuntimeException("dbName is null");
		}
		this.dbName = dbName;
		return this;
	}

	public ProjMuiProjListQuery dbNameLike(String dbNameLike) {
		if (dbNameLike == null) {
			throw new RuntimeException("dbName is null");
		}
		this.dbNameLike = dbNameLike;
		return this;
	}

	public ProjMuiProjListQuery dbNames(List<String> dbNames) {
		if (dbNames == null) {
			throw new RuntimeException("dbNames is empty ");
		}
		this.dbNames = dbNames;
		return this;
	}

	public ProjMuiProjListQuery serverName(String serverName) {
		if (serverName == null) {
			throw new RuntimeException("serverName is null");
		}
		this.serverName = serverName;
		return this;
	}

	public ProjMuiProjListQuery serverNameLike(String serverNameLike) {
		if (serverNameLike == null) {
			throw new RuntimeException("serverName is null");
		}
		this.serverNameLike = serverNameLike;
		return this;
	}

	public ProjMuiProjListQuery serverNames(List<String> serverNames) {
		if (serverNames == null) {
			throw new RuntimeException("serverNames is empty ");
		}
		this.serverNames = serverNames;
		return this;
	}

	public ProjMuiProjListQuery user(String user) {
		if (user == null) {
			throw new RuntimeException("user is null");
		}
		this.user = user;
		return this;
	}

	public ProjMuiProjListQuery userLike(String userLike) {
		if (userLike == null) {
			throw new RuntimeException("user is null");
		}
		this.userLike = userLike;
		return this;
	}

	public ProjMuiProjListQuery users(List<String> users) {
		if (users == null) {
			throw new RuntimeException("users is empty ");
		}
		this.users = users;
		return this;
	}

	public ProjMuiProjListQuery password(String password) {
		if (password == null) {
			throw new RuntimeException("password is null");
		}
		this.password = password;
		return this;
	}

	public ProjMuiProjListQuery passwordLike(String passwordLike) {
		if (passwordLike == null) {
			throw new RuntimeException("password is null");
		}
		this.passwordLike = passwordLike;
		return this;
	}

	public ProjMuiProjListQuery passwords(List<String> passwords) {
		if (passwords == null) {
			throw new RuntimeException("passwords is empty ");
		}
		this.passwords = passwords;
		return this;
	}

	public ProjMuiProjListQuery listNo(Integer listNo) {
		if (listNo == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNo = listNo;
		return this;
	}

	public ProjMuiProjListQuery listNoGreaterThanOrEqual(
			Integer listNoGreaterThanOrEqual) {
		if (listNoGreaterThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoGreaterThanOrEqual = listNoGreaterThanOrEqual;
		return this;
	}

	public ProjMuiProjListQuery listNoLessThanOrEqual(
			Integer listNoLessThanOrEqual) {
		if (listNoLessThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoLessThanOrEqual = listNoLessThanOrEqual;
		return this;
	}

	public ProjMuiProjListQuery listNos(List<Integer> listNos) {
		if (listNos == null) {
			throw new RuntimeException("listNos is empty ");
		}
		this.listNos = listNos;
		return this;
	}

	public ProjMuiProjListQuery email(String email) {
		if (email == null) {
			throw new RuntimeException("email is null");
		}
		this.email = email;
		return this;
	}

	public ProjMuiProjListQuery emailLike(String emailLike) {
		if (emailLike == null) {
			throw new RuntimeException("email is null");
		}
		this.emailLike = emailLike;
		return this;
	}

	public ProjMuiProjListQuery emails(List<String> emails) {
		if (emails == null) {
			throw new RuntimeException("emails is empty ");
		}
		this.emailList = emails;
		return this;
	}

	public ProjMuiProjListQuery nodeIco(Integer nodeIco) {
		if (nodeIco == null) {
			throw new RuntimeException("nodeIco is null");
		}
		this.nodeIco = nodeIco;
		return this;
	}

	public ProjMuiProjListQuery nodeIcoGreaterThanOrEqual(
			Integer nodeIcoGreaterThanOrEqual) {
		if (nodeIcoGreaterThanOrEqual == null) {
			throw new RuntimeException("nodeIco is null");
		}
		this.nodeIcoGreaterThanOrEqual = nodeIcoGreaterThanOrEqual;
		return this;
	}

	public ProjMuiProjListQuery nodeIcoLessThanOrEqual(
			Integer nodeIcoLessThanOrEqual) {
		if (nodeIcoLessThanOrEqual == null) {
			throw new RuntimeException("nodeIco is null");
		}
		this.nodeIcoLessThanOrEqual = nodeIcoLessThanOrEqual;
		return this;
	}

	public ProjMuiProjListQuery nodeIcos(List<Integer> nodeIcos) {
		if (nodeIcos == null) {
			throw new RuntimeException("nodeIcos is empty ");
		}
		this.nodeIcos = nodeIcos;
		return this;
	}

	public ProjMuiProjListQuery intLine(Integer intLine) {
		if (intLine == null) {
			throw new RuntimeException("intLine is null");
		}
		this.intLine = intLine;
		return this;
	}

	public ProjMuiProjListQuery intLineGreaterThanOrEqual(
			Integer intLineGreaterThanOrEqual) {
		if (intLineGreaterThanOrEqual == null) {
			throw new RuntimeException("intLine is null");
		}
		this.intLineGreaterThanOrEqual = intLineGreaterThanOrEqual;
		return this;
	}

	public ProjMuiProjListQuery intLineLessThanOrEqual(
			Integer intLineLessThanOrEqual) {
		if (intLineLessThanOrEqual == null) {
			throw new RuntimeException("intLine is null");
		}
		this.intLineLessThanOrEqual = intLineLessThanOrEqual;
		return this;
	}

	public ProjMuiProjListQuery intLines(List<Integer> intLines) {
		if (intLines == null) {
			throw new RuntimeException("intLines is empty ");
		}
		this.intLines = intLines;
		return this;
	}

	public ProjMuiProjListQuery domainIndex(Integer domainIndex) {
		if (domainIndex == null) {
			throw new RuntimeException("domainIndex is null");
		}
		this.domainIndex = domainIndex;
		return this;
	}

	public ProjMuiProjListQuery domainIndexGreaterThanOrEqual(
			Integer domainIndexGreaterThanOrEqual) {
		if (domainIndexGreaterThanOrEqual == null) {
			throw new RuntimeException("domainIndex is null");
		}
		this.domainIndexGreaterThanOrEqual = domainIndexGreaterThanOrEqual;
		return this;
	}

	public ProjMuiProjListQuery domainIndexLessThanOrEqual(
			Integer domainIndexLessThanOrEqual) {
		if (domainIndexLessThanOrEqual == null) {
			throw new RuntimeException("domainIndex is null");
		}
		this.domainIndexLessThanOrEqual = domainIndexLessThanOrEqual;
		return this;
	}

	public ProjMuiProjListQuery domainIndexs(List<Integer> domainIndexs) {
		if (domainIndexs == null) {
			throw new RuntimeException("domainIndexs is empty ");
		}
		this.domainIndexs = domainIndexs;
		return this;
	}

	public ProjMuiProjListQuery intLocal(Integer intLocal) {
		if (intLocal == null) {
			throw new RuntimeException("intLocal is null");
		}
		this.intLocal = intLocal;
		return this;
	}

	public ProjMuiProjListQuery intLocalGreaterThanOrEqual(
			Integer intLocalGreaterThanOrEqual) {
		if (intLocalGreaterThanOrEqual == null) {
			throw new RuntimeException("intLocal is null");
		}
		this.intLocalGreaterThanOrEqual = intLocalGreaterThanOrEqual;
		return this;
	}

	public ProjMuiProjListQuery intLocalLessThanOrEqual(
			Integer intLocalLessThanOrEqual) {
		if (intLocalLessThanOrEqual == null) {
			throw new RuntimeException("intLocal is null");
		}
		this.intLocalLessThanOrEqual = intLocalLessThanOrEqual;
		return this;
	}

	public ProjMuiProjListQuery intLocals(List<Integer> intLocals) {
		if (intLocals == null) {
			throw new RuntimeException("intLocals is empty ");
		}
		this.intLocals = intLocals;
		return this;
	}

	public ProjMuiProjListQuery emailPSW(String emailPSW) {
		if (emailPSW == null) {
			throw new RuntimeException("emailPSW is null");
		}
		this.emailPSW = emailPSW;
		return this;
	}

	public ProjMuiProjListQuery emailPSWLike(String emailPSWLike) {
		if (emailPSWLike == null) {
			throw new RuntimeException("emailPSW is null");
		}
		this.emailPSWLike = emailPSWLike;
		return this;
	}

	public ProjMuiProjListQuery emailPSWs(List<String> emailPSWs) {
		if (emailPSWs == null) {
			throw new RuntimeException("emailPSWs is empty ");
		}
		this.emailPSWs = emailPSWs;
		return this;
	}

	public ProjMuiProjListQuery intConnected(Integer intConnected) {
		if (intConnected == null) {
			throw new RuntimeException("intConnected is null");
		}
		this.intConnected = intConnected;
		return this;
	}

	public ProjMuiProjListQuery intConnectedGreaterThanOrEqual(
			Integer intConnectedGreaterThanOrEqual) {
		if (intConnectedGreaterThanOrEqual == null) {
			throw new RuntimeException("intConnected is null");
		}
		this.intConnectedGreaterThanOrEqual = intConnectedGreaterThanOrEqual;
		return this;
	}

	public ProjMuiProjListQuery intConnectedLessThanOrEqual(
			Integer intConnectedLessThanOrEqual) {
		if (intConnectedLessThanOrEqual == null) {
			throw new RuntimeException("intConnected is null");
		}
		this.intConnectedLessThanOrEqual = intConnectedLessThanOrEqual;
		return this;
	}

	public ProjMuiProjListQuery intConnecteds(List<Integer> intConnecteds) {
		if (intConnecteds == null) {
			throw new RuntimeException("intConnecteds is empty ");
		}
		this.intConnecteds = intConnecteds;
		return this;
	}

	public ProjMuiProjListQuery emails(String emails) {
		if (emails == null) {
			throw new RuntimeException("emails is null");
		}
		this.emails = emails;
		return this;
	}

	public ProjMuiProjListQuery emailsLike(String emailsLike) {
		if (emailsLike == null) {
			throw new RuntimeException("emails is null");
		}
		this.emailsLike = emailsLike;
		return this;
	}

	public ProjMuiProjListQuery emailss(List<String> emailss) {
		if (emailss == null) {
			throw new RuntimeException("emailss is empty ");
		}
		this.emailss = emailss;
		return this;
	}

	public ProjMuiProjListQuery intorgLevel(Integer intorgLevel) {
		if (intorgLevel == null) {
			throw new RuntimeException("intorgLevel is null");
		}
		this.intorgLevel = intorgLevel;
		return this;
	}

	public ProjMuiProjListQuery intorgLevelGreaterThanOrEqual(
			Integer intorgLevelGreaterThanOrEqual) {
		if (intorgLevelGreaterThanOrEqual == null) {
			throw new RuntimeException("intorgLevel is null");
		}
		this.intorgLevelGreaterThanOrEqual = intorgLevelGreaterThanOrEqual;
		return this;
	}

	public ProjMuiProjListQuery intorgLevelLessThanOrEqual(
			Integer intorgLevelLessThanOrEqual) {
		if (intorgLevelLessThanOrEqual == null) {
			throw new RuntimeException("intorgLevel is null");
		}
		this.intorgLevelLessThanOrEqual = intorgLevelLessThanOrEqual;
		return this;
	}

	public ProjMuiProjListQuery intorgLevels(List<Integer> intorgLevels) {
		if (intorgLevels == null) {
			throw new RuntimeException("intorgLevels is empty ");
		}
		this.intorgLevels = intorgLevels;
		return this;
	}

	public ProjMuiProjListQuery intSendType(Integer intSendType) {
		if (intSendType == null) {
			throw new RuntimeException("intSendType is null");
		}
		this.intSendType = intSendType;
		return this;
	}

	public ProjMuiProjListQuery intSendTypeGreaterThanOrEqual(
			Integer intSendTypeGreaterThanOrEqual) {
		if (intSendTypeGreaterThanOrEqual == null) {
			throw new RuntimeException("intSendType is null");
		}
		this.intSendTypeGreaterThanOrEqual = intSendTypeGreaterThanOrEqual;
		return this;
	}

	public ProjMuiProjListQuery intSendTypeLessThanOrEqual(
			Integer intSendTypeLessThanOrEqual) {
		if (intSendTypeLessThanOrEqual == null) {
			throw new RuntimeException("intSendType is null");
		}
		this.intSendTypeLessThanOrEqual = intSendTypeLessThanOrEqual;
		return this;
	}

	public ProjMuiProjListQuery intSendTypes(List<Integer> intSendTypes) {
		if (intSendTypes == null) {
			throw new RuntimeException("intSendTypes is empty ");
		}
		this.intSendTypes = intSendTypes;
		return this;
	}

	public ProjMuiProjListQuery emailBackup(String emailBackup) {
		if (emailBackup == null) {
			throw new RuntimeException("emailBackup is null");
		}
		this.emailBackup = emailBackup;
		return this;
	}

	public ProjMuiProjListQuery emailBackupLike(String emailBackupLike) {
		if (emailBackupLike == null) {
			throw new RuntimeException("emailBackup is null");
		}
		this.emailBackupLike = emailBackupLike;
		return this;
	}

	public ProjMuiProjListQuery emailBackups(List<String> emailBackups) {
		if (emailBackups == null) {
			throw new RuntimeException("emailBackups is empty ");
		}
		this.emailBackups = emailBackups;
		return this;
	}

	public ProjMuiProjListQuery emailImplement(String emailImplement) {
		if (emailImplement == null) {
			throw new RuntimeException("emailImplement is null");
		}
		this.emailImplement = emailImplement;
		return this;
	}

	public ProjMuiProjListQuery emailImplementLike(String emailImplementLike) {
		if (emailImplementLike == null) {
			throw new RuntimeException("emailImplement is null");
		}
		this.emailImplementLike = emailImplementLike;
		return this;
	}

	public ProjMuiProjListQuery emailImplements(List<String> emailImplements) {
		if (emailImplements == null) {
			throw new RuntimeException("emailImplements is empty ");
		}
		this.emailImplements = emailImplements;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("id".equals(sortColumn)) {
				orderBy = "E.ID" + a_x;
			}

			if ("intFlag".equals(sortColumn)) {
				orderBy = "E.INTFLAG" + a_x;
			}

			if ("sysId".equals(sortColumn)) {
				orderBy = "E.SYS_ID" + a_x;
			}

			if ("projName".equals(sortColumn)) {
				orderBy = "E.PROJNAME" + a_x;
			}

			if ("num".equals(sortColumn)) {
				orderBy = "E.NUM" + a_x;
			}

			if ("ctime".equals(sortColumn)) {
				orderBy = "E.CTIME" + a_x;
			}

			if ("content".equals(sortColumn)) {
				orderBy = "E.CONTENT" + a_x;
			}

			if ("dbName".equals(sortColumn)) {
				orderBy = "E.SDBNAME" + a_x;
			}

			if ("serverName".equals(sortColumn)) {
				orderBy = "E.SSERVERNAME" + a_x;
			}

			if ("user".equals(sortColumn)) {
				orderBy = "E.SUSER" + a_x;
			}

			if ("password".equals(sortColumn)) {
				orderBy = "E.SPASSWORD" + a_x;
			}

			if ("listNo".equals(sortColumn)) {
				orderBy = "E.LISTNO" + a_x;
			}

			if ("email".equals(sortColumn)) {
				orderBy = "E.EMAIL" + a_x;
			}

			if ("parentId".equals(sortColumn)) {
				orderBy = "E.PARENT_ID" + a_x;
			}

			if ("nodeIco".equals(sortColumn)) {
				orderBy = "E.NODEICO" + a_x;
			}

			if ("intLine".equals(sortColumn)) {
				orderBy = "E.INTLINE" + a_x;
			}

			if ("domainIndex".equals(sortColumn)) {
				orderBy = "E.DOMAIN_INDEX" + a_x;
			}

			if ("intLocal".equals(sortColumn)) {
				orderBy = "E.INTLOCAL" + a_x;
			}

			if ("emailPSW".equals(sortColumn)) {
				orderBy = "E.EMAIL_PSW" + a_x;
			}

			if ("intConnected".equals(sortColumn)) {
				orderBy = "E.INTCONNECTED" + a_x;
			}

			if ("emails".equals(sortColumn)) {
				orderBy = "E.EMAIL_S" + a_x;
			}

			if ("intorgLevel".equals(sortColumn)) {
				orderBy = "E.INTORGLEVEL" + a_x;
			}

			if ("intSendType".equals(sortColumn)) {
				orderBy = "E.INTSENDTYPE" + a_x;
			}

			if ("emailBackup".equals(sortColumn)) {
				orderBy = "E.EMAIL_BACKUP" + a_x;
			}

			if ("emailImplement".equals(sortColumn)) {
				orderBy = "E.EMAIL_IMPLEMENT" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("indexId", "INDEX_ID");
		addColumn("id", "ID");
		addColumn("intFlag", "INTFLAG");
		addColumn("sysId", "SYS_ID");
		addColumn("projName", "PROJNAME");
		addColumn("num", "NUM");
		addColumn("ctime", "CTIME");
		addColumn("content", "CONTENT");
		addColumn("dbName", "SDBNAME");
		addColumn("serverName", "SSERVERNAME");
		addColumn("user", "SUSER");
		addColumn("password", "SPASSWORD");
		addColumn("listNo", "LISTNO");
		addColumn("email", "EMAIL");
		addColumn("parentId", "PARENT_ID");
		addColumn("nodeIco", "NODEICO");
		addColumn("intLine", "INTLINE");
		addColumn("domainIndex", "DOMAIN_INDEX");
		addColumn("intLocal", "INTLOCAL");
		addColumn("emailPSW", "EMAIL_PSW");
		addColumn("intConnected", "INTCONNECTED");
		addColumn("emails", "EMAIL_S");
		addColumn("intorgLevel", "INTORGLEVEL");
		addColumn("intSendType", "INTSENDTYPE");
		addColumn("emailBackup", "EMAIL_BACKUP");
		addColumn("emailImplement", "EMAIL_IMPLEMENT");
	}

}