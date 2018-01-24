package com.glaf.maildata.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class EmailSendQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected String msgId;
	protected String msgIdLike;
	protected List<String> msgIds;
	protected String inReplyTo;
	protected String inReplyToLike;
	protected List<String> inReplyTos;
	protected String from;
	protected String fromLike;
	protected List<String> froms;
	protected String to;
	protected String toLike;
	protected List<String> tos;
	protected String cc;
	protected String ccLike;
	protected List<String> ccs;
	protected Date dateGreaterThanOrEqual;
	protected Date dateLessThanOrEqual;
	protected String subject;
	protected String subjectLike;
	protected List<String> subjects;
	protected String replyTo;
	protected String replyToLike;
	protected List<String> replyTos;
	protected Integer intFlag;
	protected Integer intFlagGreaterThanOrEqual;
	protected Integer intFlagLessThanOrEqual;
	protected List<Integer> intFlags;
	protected String email;
	protected String emailLike;
	protected List<String> emails;
	protected String fromSysId;
	protected String fromSysIdLike;
	protected List<String> fromSysIds;
	protected Integer intAction;
	protected Integer intActionGreaterThanOrEqual;
	protected Integer intActionLessThanOrEqual;
	protected List<Integer> intActions;
	protected Integer intOperat;
	protected Integer intOperatGreaterThanOrEqual;
	protected Integer intOperatLessThanOrEqual;
	protected List<Integer> intOperats;
	protected Integer listNo;
	protected Integer listNoGreaterThanOrEqual;
	protected Integer listNoLessThanOrEqual;
	protected List<Integer> listNos;
	protected String toSysId;
	protected String toSysIdLike;
	protected List<String> toSysIds;
	protected String searchCondition;

	public EmailSendQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getMsgId() {
		return msgId;
	}

	public String getMsgIdLike() {
		if (msgIdLike != null && msgIdLike.trim().length() > 0) {
			if (!msgIdLike.startsWith("%")) {
				msgIdLike = "%" + msgIdLike;
			}
			if (!msgIdLike.endsWith("%")) {
				msgIdLike = msgIdLike + "%";
			}
		}
		return msgIdLike;
	}

	public List<String> getMsgIds() {
		return msgIds;
	}

	public String getInReplyTo() {
		return inReplyTo;
	}

	public String getInReplyToLike() {
		if (inReplyToLike != null && inReplyToLike.trim().length() > 0) {
			if (!inReplyToLike.startsWith("%")) {
				inReplyToLike = "%" + inReplyToLike;
			}
			if (!inReplyToLike.endsWith("%")) {
				inReplyToLike = inReplyToLike + "%";
			}
		}
		return inReplyToLike;
	}

	public List<String> getInReplyTos() {
		return inReplyTos;
	}

	public String getFrom() {
		return from;
	}

	public String getFromLike() {
		if (fromLike != null && fromLike.trim().length() > 0) {
			if (!fromLike.startsWith("%")) {
				fromLike = "%" + fromLike;
			}
			if (!fromLike.endsWith("%")) {
				fromLike = fromLike + "%";
			}
		}
		return fromLike;
	}

	public List<String> getFroms() {
		return froms;
	}

	public String getTo() {
		return to;
	}

	public String getToLike() {
		if (toLike != null && toLike.trim().length() > 0) {
			if (!toLike.startsWith("%")) {
				toLike = "%" + toLike;
			}
			if (!toLike.endsWith("%")) {
				toLike = toLike + "%";
			}
		}
		return toLike;
	}

	public List<String> getTos() {
		return tos;
	}

	public String getCc() {
		return cc;
	}

	public String getCcLike() {
		if (ccLike != null && ccLike.trim().length() > 0) {
			if (!ccLike.startsWith("%")) {
				ccLike = "%" + ccLike;
			}
			if (!ccLike.endsWith("%")) {
				ccLike = ccLike + "%";
			}
		}
		return ccLike;
	}

	public List<String> getCcs() {
		return ccs;
	}

	public Date getDateGreaterThanOrEqual() {
		return dateGreaterThanOrEqual;
	}

	public Date getDateLessThanOrEqual() {
		return dateLessThanOrEqual;
	}

	public String getSubject() {
		return subject;
	}

	public String getSubjectLike() {
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

	public List<String> getSubjects() {
		return subjects;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public String getReplyToLike() {
		if (replyToLike != null && replyToLike.trim().length() > 0) {
			if (!replyToLike.startsWith("%")) {
				replyToLike = "%" + replyToLike;
			}
			if (!replyToLike.endsWith("%")) {
				replyToLike = replyToLike + "%";
			}
		}
		return replyToLike;
	}

	public List<String> getReplyTos() {
		return replyTos;
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

	public List<String> getEmails() {
		return emails;
	}

	public String getFromSysId() {
		return fromSysId;
	}

	public String getFromSysIdLike() {
		if (fromSysIdLike != null && fromSysIdLike.trim().length() > 0) {
			if (!fromSysIdLike.startsWith("%")) {
				fromSysIdLike = "%" + fromSysIdLike;
			}
			if (!fromSysIdLike.endsWith("%")) {
				fromSysIdLike = fromSysIdLike + "%";
			}
		}
		return fromSysIdLike;
	}

	public List<String> getFromSysIds() {
		return fromSysIds;
	}

	public Integer getIntAction() {
		return intAction;
	}

	public Integer getIntActionGreaterThanOrEqual() {
		return intActionGreaterThanOrEqual;
	}

	public Integer getIntActionLessThanOrEqual() {
		return intActionLessThanOrEqual;
	}

	public List<Integer> getIntActions() {
		return intActions;
	}

	public Integer getIntOperat() {
		return intOperat;
	}

	public Integer getIntOperatGreaterThanOrEqual() {
		return intOperatGreaterThanOrEqual;
	}

	public Integer getIntOperatLessThanOrEqual() {
		return intOperatLessThanOrEqual;
	}

	public List<Integer> getIntOperats() {
		return intOperats;
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

	public String getToSysId() {
		return toSysId;
	}

	public String getToSysIdLike() {
		if (toSysIdLike != null && toSysIdLike.trim().length() > 0) {
			if (!toSysIdLike.startsWith("%")) {
				toSysIdLike = "%" + toSysIdLike;
			}
			if (!toSysIdLike.endsWith("%")) {
				toSysIdLike = toSysIdLike + "%";
			}
		}
		return toSysIdLike;
	}

	public List<String> getToSysIds() {
		return toSysIds;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public void setMsgIdLike(String msgIdLike) {
		this.msgIdLike = msgIdLike;
	}

	public void setMsgIds(List<String> msgIds) {
		this.msgIds = msgIds;
	}

	public void setInReplyTo(String inReplyTo) {
		this.inReplyTo = inReplyTo;
	}

	public void setInReplyToLike(String inReplyToLike) {
		this.inReplyToLike = inReplyToLike;
	}

	public void setInReplyTos(List<String> inReplyTos) {
		this.inReplyTos = inReplyTos;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setFromLike(String fromLike) {
		this.fromLike = fromLike;
	}

	public void setFroms(List<String> froms) {
		this.froms = froms;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setToLike(String toLike) {
		this.toLike = toLike;
	}

	public void setTos(List<String> tos) {
		this.tos = tos;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public void setCcLike(String ccLike) {
		this.ccLike = ccLike;
	}

	public void setCcs(List<String> ccs) {
		this.ccs = ccs;
	}

	public void setDateGreaterThanOrEqual(Date dateGreaterThanOrEqual) {
		this.dateGreaterThanOrEqual = dateGreaterThanOrEqual;
	}

	public void setDateLessThanOrEqual(Date dateLessThanOrEqual) {
		this.dateLessThanOrEqual = dateLessThanOrEqual;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setSubjectLike(String subjectLike) {
		this.subjectLike = subjectLike;
	}

	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	public void setReplyToLike(String replyToLike) {
		this.replyToLike = replyToLike;
	}

	public void setReplyTos(List<String> replyTos) {
		this.replyTos = replyTos;
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

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEmailLike(String emailLike) {
		this.emailLike = emailLike;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	public void setFromSysId(String fromSysId) {
		this.fromSysId = fromSysId;
	}

	public void setFromSysIdLike(String fromSysIdLike) {
		this.fromSysIdLike = fromSysIdLike;
	}

	public void setFromSysIds(List<String> fromSysIds) {
		this.fromSysIds = fromSysIds;
	}

	public void setIntAction(Integer intAction) {
		this.intAction = intAction;
	}

	public void setIntActionGreaterThanOrEqual(Integer intActionGreaterThanOrEqual) {
		this.intActionGreaterThanOrEqual = intActionGreaterThanOrEqual;
	}

	public void setIntActionLessThanOrEqual(Integer intActionLessThanOrEqual) {
		this.intActionLessThanOrEqual = intActionLessThanOrEqual;
	}

	public void setIntActions(List<Integer> intActions) {
		this.intActions = intActions;
	}

	public void setIntOperat(Integer intOperat) {
		this.intOperat = intOperat;
	}

	public void setIntOperatGreaterThanOrEqual(Integer intOperatGreaterThanOrEqual) {
		this.intOperatGreaterThanOrEqual = intOperatGreaterThanOrEqual;
	}

	public void setIntOperatLessThanOrEqual(Integer intOperatLessThanOrEqual) {
		this.intOperatLessThanOrEqual = intOperatLessThanOrEqual;
	}

	public void setIntOperats(List<Integer> intOperats) {
		this.intOperats = intOperats;
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

	public void setToSysId(String toSysId) {
		this.toSysId = toSysId;
	}

	public void setToSysIdLike(String toSysIdLike) {
		this.toSysIdLike = toSysIdLike;
	}

	public void setToSysIds(List<String> toSysIds) {
		this.toSysIds = toSysIds;
	}

	public String getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	public EmailSendQuery searchCondition(String searchCondition) {
		if (searchCondition == null) {
			throw new RuntimeException("searchCondition is null");
		}
		this.searchCondition = searchCondition;
		return this;
	}

	public EmailSendQuery msgId(String msgId) {
		if (msgId == null) {
			throw new RuntimeException("msgId is null");
		}
		this.msgId = msgId;
		return this;
	}

	public EmailSendQuery msgIdLike(String msgIdLike) {
		if (msgIdLike == null) {
			throw new RuntimeException("msgId is null");
		}
		this.msgIdLike = msgIdLike;
		return this;
	}

	public EmailSendQuery msgIds(List<String> msgIds) {
		if (msgIds == null) {
			throw new RuntimeException("msgIds is empty ");
		}
		this.msgIds = msgIds;
		return this;
	}

	public EmailSendQuery inReplyTo(String inReplyTo) {
		if (inReplyTo == null) {
			throw new RuntimeException("inReplyTo is null");
		}
		this.inReplyTo = inReplyTo;
		return this;
	}

	public EmailSendQuery inReplyToLike(String inReplyToLike) {
		if (inReplyToLike == null) {
			throw new RuntimeException("inReplyTo is null");
		}
		this.inReplyToLike = inReplyToLike;
		return this;
	}

	public EmailSendQuery inReplyTos(List<String> inReplyTos) {
		if (inReplyTos == null) {
			throw new RuntimeException("inReplyTos is empty ");
		}
		this.inReplyTos = inReplyTos;
		return this;
	}

	public EmailSendQuery from(String from) {
		if (from == null) {
			throw new RuntimeException("from is null");
		}
		this.from = from;
		return this;
	}

	public EmailSendQuery fromLike(String fromLike) {
		if (fromLike == null) {
			throw new RuntimeException("from is null");
		}
		this.fromLike = fromLike;
		return this;
	}

	public EmailSendQuery froms(List<String> froms) {
		if (froms == null) {
			throw new RuntimeException("froms is empty ");
		}
		this.froms = froms;
		return this;
	}

	public EmailSendQuery to(String to) {
		if (to == null) {
			throw new RuntimeException("to is null");
		}
		this.to = to;
		return this;
	}

	public EmailSendQuery toLike(String toLike) {
		if (toLike == null) {
			throw new RuntimeException("to is null");
		}
		this.toLike = toLike;
		return this;
	}

	public EmailSendQuery tos(List<String> tos) {
		if (tos == null) {
			throw new RuntimeException("tos is empty ");
		}
		this.tos = tos;
		return this;
	}

	public EmailSendQuery cc(String cc) {
		if (cc == null) {
			throw new RuntimeException("cc is null");
		}
		this.cc = cc;
		return this;
	}

	public EmailSendQuery ccLike(String ccLike) {
		if (ccLike == null) {
			throw new RuntimeException("cc is null");
		}
		this.ccLike = ccLike;
		return this;
	}

	public EmailSendQuery ccs(List<String> ccs) {
		if (ccs == null) {
			throw new RuntimeException("ccs is empty ");
		}
		this.ccs = ccs;
		return this;
	}

	public EmailSendQuery dateGreaterThanOrEqual(Date dateGreaterThanOrEqual) {
		if (dateGreaterThanOrEqual == null) {
			throw new RuntimeException("date is null");
		}
		this.dateGreaterThanOrEqual = dateGreaterThanOrEqual;
		return this;
	}

	public EmailSendQuery dateLessThanOrEqual(Date dateLessThanOrEqual) {
		if (dateLessThanOrEqual == null) {
			throw new RuntimeException("date is null");
		}
		this.dateLessThanOrEqual = dateLessThanOrEqual;
		return this;
	}

	public EmailSendQuery subject(String subject) {
		if (subject == null) {
			throw new RuntimeException("subject is null");
		}
		this.subject = subject;
		return this;
	}

	public EmailSendQuery subjectLike(String subjectLike) {
		if (subjectLike == null) {
			throw new RuntimeException("subject is null");
		}
		this.subjectLike = subjectLike;
		return this;
	}

	public EmailSendQuery subjects(List<String> subjects) {
		if (subjects == null) {
			throw new RuntimeException("subjects is empty ");
		}
		this.subjects = subjects;
		return this;
	}

	public EmailSendQuery replyTo(String replyTo) {
		if (replyTo == null) {
			throw new RuntimeException("replyTo is null");
		}
		this.replyTo = replyTo;
		return this;
	}

	public EmailSendQuery replyToLike(String replyToLike) {
		if (replyToLike == null) {
			throw new RuntimeException("replyTo is null");
		}
		this.replyToLike = replyToLike;
		return this;
	}

	public EmailSendQuery replyTos(List<String> replyTos) {
		if (replyTos == null) {
			throw new RuntimeException("replyTos is empty ");
		}
		this.replyTos = replyTos;
		return this;
	}

	public EmailSendQuery intFlag(Integer intFlag) {
		if (intFlag == null) {
			throw new RuntimeException("intFlag is null");
		}
		this.intFlag = intFlag;
		return this;
	}

	public EmailSendQuery intFlagGreaterThanOrEqual(Integer intFlagGreaterThanOrEqual) {
		if (intFlagGreaterThanOrEqual == null) {
			throw new RuntimeException("intFlag is null");
		}
		this.intFlagGreaterThanOrEqual = intFlagGreaterThanOrEqual;
		return this;
	}

	public EmailSendQuery intFlagLessThanOrEqual(Integer intFlagLessThanOrEqual) {
		if (intFlagLessThanOrEqual == null) {
			throw new RuntimeException("intFlag is null");
		}
		this.intFlagLessThanOrEqual = intFlagLessThanOrEqual;
		return this;
	}

	public EmailSendQuery intFlags(List<Integer> intFlags) {
		if (intFlags == null) {
			throw new RuntimeException("intFlags is empty ");
		}
		this.intFlags = intFlags;
		return this;
	}

	public EmailSendQuery email(String email) {
		if (email == null) {
			throw new RuntimeException("email is null");
		}
		this.email = email;
		return this;
	}

	public EmailSendQuery emailLike(String emailLike) {
		if (emailLike == null) {
			throw new RuntimeException("email is null");
		}
		this.emailLike = emailLike;
		return this;
	}

	public EmailSendQuery emails(List<String> emails) {
		if (emails == null) {
			throw new RuntimeException("emails is empty ");
		}
		this.emails = emails;
		return this;
	}

	public EmailSendQuery fromSysId(String fromSysId) {
		if (fromSysId == null) {
			throw new RuntimeException("fromSysId is null");
		}
		this.fromSysId = fromSysId;
		return this;
	}

	public EmailSendQuery fromSysIdLike(String fromSysIdLike) {
		if (fromSysIdLike == null) {
			throw new RuntimeException("fromSysId is null");
		}
		this.fromSysIdLike = fromSysIdLike;
		return this;
	}

	public EmailSendQuery fromSysIds(List<String> fromSysIds) {
		if (fromSysIds == null) {
			throw new RuntimeException("fromSysIds is empty ");
		}
		this.fromSysIds = fromSysIds;
		return this;
	}

	public EmailSendQuery intAction(Integer intAction) {
		if (intAction == null) {
			throw new RuntimeException("intAction is null");
		}
		this.intAction = intAction;
		return this;
	}

	public EmailSendQuery intActionGreaterThanOrEqual(Integer intActionGreaterThanOrEqual) {
		if (intActionGreaterThanOrEqual == null) {
			throw new RuntimeException("intAction is null");
		}
		this.intActionGreaterThanOrEqual = intActionGreaterThanOrEqual;
		return this;
	}

	public EmailSendQuery intActionLessThanOrEqual(Integer intActionLessThanOrEqual) {
		if (intActionLessThanOrEqual == null) {
			throw new RuntimeException("intAction is null");
		}
		this.intActionLessThanOrEqual = intActionLessThanOrEqual;
		return this;
	}

	public EmailSendQuery intActions(List<Integer> intActions) {
		if (intActions == null) {
			throw new RuntimeException("intActions is empty ");
		}
		this.intActions = intActions;
		return this;
	}

	public EmailSendQuery intOperat(Integer intOperat) {
		if (intOperat == null) {
			throw new RuntimeException("intOperat is null");
		}
		this.intOperat = intOperat;
		return this;
	}

	public EmailSendQuery intOperatGreaterThanOrEqual(Integer intOperatGreaterThanOrEqual) {
		if (intOperatGreaterThanOrEqual == null) {
			throw new RuntimeException("intOperat is null");
		}
		this.intOperatGreaterThanOrEqual = intOperatGreaterThanOrEqual;
		return this;
	}

	public EmailSendQuery intOperatLessThanOrEqual(Integer intOperatLessThanOrEqual) {
		if (intOperatLessThanOrEqual == null) {
			throw new RuntimeException("intOperat is null");
		}
		this.intOperatLessThanOrEqual = intOperatLessThanOrEqual;
		return this;
	}

	public EmailSendQuery intOperats(List<Integer> intOperats) {
		if (intOperats == null) {
			throw new RuntimeException("intOperats is empty ");
		}
		this.intOperats = intOperats;
		return this;
	}

	public EmailSendQuery listNo(Integer listNo) {
		if (listNo == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNo = listNo;
		return this;
	}

	public EmailSendQuery listNoGreaterThanOrEqual(Integer listNoGreaterThanOrEqual) {
		if (listNoGreaterThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoGreaterThanOrEqual = listNoGreaterThanOrEqual;
		return this;
	}

	public EmailSendQuery listNoLessThanOrEqual(Integer listNoLessThanOrEqual) {
		if (listNoLessThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoLessThanOrEqual = listNoLessThanOrEqual;
		return this;
	}

	public EmailSendQuery listNos(List<Integer> listNos) {
		if (listNos == null) {
			throw new RuntimeException("listNos is empty ");
		}
		this.listNos = listNos;
		return this;
	}

	public EmailSendQuery toSysId(String toSysId) {
		if (toSysId == null) {
			throw new RuntimeException("toSysId is null");
		}
		this.toSysId = toSysId;
		return this;
	}

	public EmailSendQuery toSysIdLike(String toSysIdLike) {
		if (toSysIdLike == null) {
			throw new RuntimeException("toSysId is null");
		}
		this.toSysIdLike = toSysIdLike;
		return this;
	}

	public EmailSendQuery toSysIds(List<String> toSysIds) {
		if (toSysIds == null) {
			throw new RuntimeException("toSysIds is empty ");
		}
		this.toSysIds = toSysIds;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("msgId".equals(sortColumn)) {
				orderBy = "E.MSGID" + a_x;
			}

			if ("inReplyTo".equals(sortColumn)) {
				orderBy = "E.INREPLYTO" + a_x;
			}

			if ("from".equals(sortColumn)) {
				orderBy = "E.FROM" + a_x;
			}

			if ("to".equals(sortColumn)) {
				orderBy = "E.TO" + a_x;
			}

			if ("cc".equals(sortColumn)) {
				orderBy = "E.CC" + a_x;
			}

			if ("date".equals(sortColumn)) {
				orderBy = "E.DATE" + a_x;
			}

			if ("subject".equals(sortColumn)) {
				orderBy = "E.SUBJECT" + a_x;
			}

			if ("replyTo".equals(sortColumn)) {
				orderBy = "E.REPLYTO" + a_x;
			}

			if ("intFlag".equals(sortColumn)) {
				orderBy = "E.INTFLAG" + a_x;
			}

			if ("email".equals(sortColumn)) {
				orderBy = "E.EMAIL" + a_x;
			}

			if ("fromSysId".equals(sortColumn)) {
				orderBy = "E.FROMSYSID" + a_x;
			}

			if ("intAction".equals(sortColumn)) {
				orderBy = "E.INTACTION" + a_x;
			}

			if ("intOperat".equals(sortColumn)) {
				orderBy = "E.INTOPERAT" + a_x;
			}

			if ("listNo".equals(sortColumn)) {
				orderBy = "E.LISTNO" + a_x;
			}

			if ("toSysId".equals(sortColumn)) {
				orderBy = "E.TOSYSID" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("msgId", "MSGID");
		addColumn("inReplyTo", "INREPLYTO");
		addColumn("from", "FROM");
		addColumn("to", "TO");
		addColumn("cc", "CC");
		addColumn("date", "DATE");
		addColumn("subject", "SUBJECT");
		addColumn("replyTo", "REPLYTO");
		addColumn("intFlag", "INTFLAG");
		addColumn("email", "EMAIL");
		addColumn("fromSysId", "FROMSYSID");
		addColumn("intAction", "INTACTION");
		addColumn("intOperat", "INTOPERAT");
		addColumn("listNo", "LISTNO");
		addColumn("toSysId", "TOSYSID");
	}

}