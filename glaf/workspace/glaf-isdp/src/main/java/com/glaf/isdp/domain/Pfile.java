package com.glaf.isdp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.util.DateUtils;
import com.glaf.isdp.util.PfileJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "PFILE")
public class Pfile implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected String id;

	@Column(name = "EFILENUM")
	protected Integer efileNum;

	@Column(name = "LISTNUM", length = 100)
	protected String listNum;

	@Column(name = "LIST_ID", length = 19)
	protected String listId;

	@Column(name = "PID")
	protected Integer pid;

	@Column(name = "PROJID")
	protected Integer projId;

	@Column(name = "DWID")
	protected Integer dwid;

	@Column(name = "FBID")
	protected Integer fbid;

	@Column(name = "FXID")
	protected Integer fxid;

	@Column(name = "JID", length = 50)
	protected String jid;

	@Column(name = "FLID", length = 50)
	protected String flid;

	@Column(name = "TOPNODE", length = 150)
	protected String topNode;

	@Column(name = "TOPNODEM", length = 150)
	protected String topNodeM;

	@Column(name = "VID", length = 50)
	protected String vid;

	@Column(name = "OLDVID", length = 50)
	protected String oldVid;

	@Column(name = "VISFLAG", length = 1)
	protected String visFlag;

	@Column(name = "LISTNO")
	protected Integer listNo;

	@Column(name = "FILINGFLAG", length = 1)
	protected String filingFlag;

	@Column(name = "SAVEFLAG", length = 1)
	protected String saveFlag;

	@Column(name = "OPENFLAG", length = 1)
	protected String openFlag;

	@Column(name = "CHECKUPFLAG", length = 1)
	protected String checkupFlag;

	@Column(name = "FINISHFLAG", length = 1)
	protected String finishFlag;

	@Column(name = "FROMID", length = 50)
	protected String fromID;

	@Column(name = "TNAME", length = 250)
	protected String tname;

	@Column(name = "DUTY", length = 100)
	protected String duty;

	@Column(name = "TAGNUM", length = 50)
	protected String tagnum;

	@Column(name = "FILENUM", length = 50)
	protected String fileNum;

	@Column(name = "THEMATIC", length = 100)
	protected String thematic;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CTIME")
	protected Date ctime;

	@Column(name = "PAGENO", length = 20)
	protected String pageNo;

	@Column(name = "SLEVEL", length = 30)
	protected String Level;

	@Column(name = "PAGE")
	protected Integer page;

	@Column(name = "FILETYPE", length = 50)
	protected String fileType;

	@Column(name = "FONTSNUM", length = 30)
	protected String fontsNum;

	@Column(name = "SAVETIME", length = 10)
	protected String saveTime;

	@Column(name = "COMPANY", length = 250)
	protected String company;

	@Column(name = "YEAR")
	protected Integer year;

	@Column(name = "FILEATT", length = 50)
	protected String fileAtt;

	@Column(name = "ANNOTATIONS", length = 100)
	protected String annotations;

	@Column(name = "CARRIERTYPE", length = 50)
	protected String carrierType;

	@Column(name = "SUMMARY", length = 200)
	protected String summary;

	@Column(name = "PTEXT", length = 30)
	protected String ptext;

	@Column(name = "CARRIERU", length = 50)
	protected String carrieru;

	@Column(name = "GLIDENUM", length = 50)
	protected String glideNum;

	@Column(name = "EFILE", length = 50)
	protected String efile;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FTIME")
	protected Date ftime;

	@Column(name = "TOTALNUM", length = 50)
	protected String totalNum;

	@Column(name = "INPUTMAN", length = 20)
	protected String inputMan;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ETIME")
	protected Date etime;

	@Column(name = "DOTNUM", length = 20)
	protected String dotNum;

	@Column(name = "PICNUM", length = 30)
	protected String picNum;

	@Column(name = "RECNUM", length = 20)
	protected String recNum;

	@Column(name = "TOTAL")
	protected Integer total;

	@Column(name = "PAGETYPE", length = 100)
	protected String pageType;

	@Column(name = "ISCOM", length = 1)
	protected String isCom;

	@Column(name = "ISLOCATE", length = 1)
	protected String isLocate;

	@Column(name = "WCOMPANY", length = 200)
	protected String wcompany;

	@Column(name = "WCOMPANYID", length = 50)
	protected String wcompanyID;

	@Column(name = "SENDFLAG", length = 1)
	protected String sendFlag;

	@Column(name = "LCONTENT", length = 100)
	protected String lcontent;

	@Column(name = "LCOMPANY", length = 250)
	protected String lcompany;

	@Column(name = "LMAN", length = 50)
	protected String lman;

	@Column(name = "LLEN", length = 50)
	protected String llen;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LDATE")
	protected Date ldate;

	@Column(name = "JCONTEN", length = 100)
	protected String jconten;

	@Column(name = "JPLACE", length = 100)
	protected String jplace;

	@Column(name = "JMAN", length = 50)
	protected String jman;

	@Column(name = "JBACK", length = 50)
	protected String jback;

	@Column(name = "JACTOR", length = 50)
	protected String jactor;

	@Column(name = "JNUM", length = 30)
	protected String jnum;

	@Column(name = "JBNUM", length = 30)
	protected String jbnum;

	@Column(name = "TNUM", length = 50)
	protected String tnum;

	@Column(name = "TZOOM", length = 20)
	protected String tzoom;

	@Column(name = "TFLAG", length = 30)
	protected String tflag;

	@Column(name = "TDESIGNER", length = 20)
	protected String tdesigner;

	@Column(name = "TVIEWER", length = 200)
	protected String tviewer;

	@Column(name = "TASSESSOR", length = 20)
	protected String tassessor;

	@Column(name = "TVISION", length = 20)
	protected String tvision;

	@Column(name = "CLISTNO")
	protected Integer clistNo;

	@Column(name = "CPAGENO", length = 20)
	protected String cpageNo;

	@Column(name = "VNUM", length = 200)
	protected String vnum;

	@Column(name = "CVNUM", length = 200)
	protected String cvnum;

	@Column(name = "TREE_DLISTSTR", length = 50)
	protected String treedListStr;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CTIME_END")
	protected Date ctimeEnd;

	@Column(name = "PROJ_INDEX")
	protected Integer projIndex;

	@Column(name = "TREE_PARENT")
	protected Integer treeParent;

	@Column(name = "TREE_LIST")
	protected Integer treeList;

	public Pfile() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getEfileNum() {
		return this.efileNum;
	}

	public String getListNum() {
		return this.listNum;
	}

	public String getListId() {
		return this.listId;
	}

	public Integer getPid() {
		return this.pid;
	}

	public Integer getProjId() {
		return this.projId;
	}

	public Integer getDwid() {
		return this.dwid;
	}

	public Integer getFbid() {
		return this.fbid;
	}

	public Integer getFxid() {
		return this.fxid;
	}

	public String getJid() {
		return this.jid;
	}

	public String getFlid() {
		return this.flid;
	}

	public String getTopNode() {
		return this.topNode;
	}

	public String getTopNodeM() {
		return this.topNodeM;
	}

	public String getVid() {
		return this.vid;
	}

	public String getOldVid() {
		return this.oldVid;
	}

	public String getVisFlag() {
		return this.visFlag;
	}

	public Integer getListNo() {
		return this.listNo;
	}

	public String getFilingFlag() {
		return this.filingFlag;
	}

	public String getSaveFlag() {
		return this.saveFlag;
	}

	public String getOpenFlag() {
		return this.openFlag;
	}

	public String getCheckupFlag() {
		return this.checkupFlag;
	}

	public String getFinishFlag() {
		return this.finishFlag;
	}

	public String getFromID() {
		return this.fromID;
	}

	public String getTname() {
		return this.tname;
	}

	public String getDuty() {
		return this.duty;
	}

	public String getTagnum() {
		return this.tagnum;
	}

	public String getFileNum() {
		return this.fileNum;
	}

	public String getThematic() {
		return this.thematic;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public String getCtimeString() {
		if (this.ctime != null) {
			return DateUtils.getDateTime(this.ctime);
		}
		return "";
	}

	public String getPageNo() {
		return this.pageNo;
	}

	public String getLevel() {
		return this.Level;
	}

	public Integer getPage() {
		return this.page;
	}

	public String getFileType() {
		return this.fileType;
	}

	public String getFontsNum() {
		return this.fontsNum;
	}

	public String getSaveTime() {
		return this.saveTime;
	}

	public String getCompany() {
		return this.company;
	}

	public Integer getYear() {
		return this.year;
	}

	public String getFileAtt() {
		return this.fileAtt;
	}

	public String getAnnotations() {
		return this.annotations;
	}

	public String getCarrierType() {
		return this.carrierType;
	}

	public String getSummary() {
		return this.summary;
	}

	public String getPtext() {
		return this.ptext;
	}

	public String getCarrieru() {
		return this.carrieru;
	}

	public String getGlideNum() {
		return this.glideNum;
	}

	public String getEfile() {
		return this.efile;
	}

	public Date getFtime() {
		return this.ftime;
	}

	public String getFtimeString() {
		if (this.ftime != null) {
			return DateUtils.getDateTime(this.ftime);
		}
		return "";
	}

	public String getTotalNum() {
		return this.totalNum;
	}

	public String getInputMan() {
		return this.inputMan;
	}

	public Date getEtime() {
		return this.etime;
	}

	public String getEtimeString() {
		if (this.etime != null) {
			return DateUtils.getDateTime(this.etime);
		}
		return "";
	}

	public String getDotNum() {
		return this.dotNum;
	}

	public String getPicNum() {
		return this.picNum;
	}

	public String getRecNum() {
		return this.recNum;
	}

	public Integer getTotal() {
		return this.total;
	}

	public String getPageType() {
		return this.pageType;
	}

	public String getIsCom() {
		return this.isCom;
	}

	public String getIsLocate() {
		return this.isLocate;
	}

	public String getWcompany() {
		return this.wcompany;
	}

	public String getWcompanyID() {
		return this.wcompanyID;
	}

	public String getSendFlag() {
		return this.sendFlag;
	}

	public String getLcontent() {
		return this.lcontent;
	}

	public String getLcompany() {
		return this.lcompany;
	}

	public String getLman() {
		return this.lman;
	}

	public String getLlen() {
		return this.llen;
	}

	public Date getLdate() {
		return this.ldate;
	}

	public String getLdateString() {
		if (this.ldate != null) {
			return DateUtils.getDateTime(this.ldate);
		}
		return "";
	}

	public String getJconten() {
		return this.jconten;
	}

	public String getJplace() {
		return this.jplace;
	}

	public String getJman() {
		return this.jman;
	}

	public String getJback() {
		return this.jback;
	}

	public String getJactor() {
		return this.jactor;
	}

	public String getJnum() {
		return this.jnum;
	}

	public String getJbnum() {
		return this.jbnum;
	}

	public String getTnum() {
		return this.tnum;
	}

	public String getTzoom() {
		return this.tzoom;
	}

	public String getTflag() {
		return this.tflag;
	}

	public String getTdesigner() {
		return this.tdesigner;
	}

	public String getTviewer() {
		return this.tviewer;
	}

	public String getTassessor() {
		return this.tassessor;
	}

	public String getTvision() {
		return this.tvision;
	}

	public Integer getClistNo() {
		return this.clistNo;
	}

	public String getCpageNo() {
		return this.cpageNo;
	}

	public String getVnum() {
		return this.vnum;
	}

	public String getCvnum() {
		return this.cvnum;
	}

	public String getTreedListStr() {
		return this.treedListStr;
	}

	public Date getCtimeEnd() {
		return this.ctimeEnd;
	}

	public String getCtimeEndString() {
		if (this.ctimeEnd != null) {
			return DateUtils.getDateTime(this.ctimeEnd);
		}
		return "";
	}

	public Integer getProjIndex() {
		return this.projIndex;
	}

	public Integer getTreeParent() {
		return this.treeParent;
	}

	public Integer getTreeList() {
		return this.treeList;
	}

	public void setEfileNum(Integer efileNum) {
		this.efileNum = efileNum;
	}

	public void setListNum(String listNum) {
		this.listNum = listNum;
	}

	public void setListId(String listId) {
		this.listId = listId;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public void setProjId(Integer projId) {
		this.projId = projId;
	}

	public void setDwid(Integer dwid) {
		this.dwid = dwid;
	}

	public void setFbid(Integer fbid) {
		this.fbid = fbid;
	}

	public void setFxid(Integer fxid) {
		this.fxid = fxid;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}

	public void setFlid(String flid) {
		this.flid = flid;
	}

	public void setTopNode(String topNode) {
		this.topNode = topNode;
	}

	public void setTopNodeM(String topNodeM) {
		this.topNodeM = topNodeM;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}

	public void setOldVid(String oldVid) {
		this.oldVid = oldVid;
	}

	public void setVisFlag(String visFlag) {
		this.visFlag = visFlag;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public void setFilingFlag(String filingFlag) {
		this.filingFlag = filingFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}

	public void setOpenFlag(String openFlag) {
		this.openFlag = openFlag;
	}

	public void setCheckupFlag(String checkupFlag) {
		this.checkupFlag = checkupFlag;
	}

	public void setFinishFlag(String finishFlag) {
		this.finishFlag = finishFlag;
	}

	public void setFromID(String fromID) {
		this.fromID = fromID;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public void setTagnum(String tagnum) {
		this.tagnum = tagnum;
	}

	public void setFileNum(String fileNum) {
		this.fileNum = fileNum;
	}

	public void setThematic(String thematic) {
		this.thematic = thematic;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public void setLevel(String Level) {
		this.Level = Level;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public void setFontsNum(String fontsNum) {
		this.fontsNum = fontsNum;
	}

	public void setSaveTime(String saveTime) {
		this.saveTime = saveTime;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public void setFileAtt(String fileAtt) {
		this.fileAtt = fileAtt;
	}

	public void setAnnotations(String annotations) {
		this.annotations = annotations;
	}

	public void setCarrierType(String carrierType) {
		this.carrierType = carrierType;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setPtext(String ptext) {
		this.ptext = ptext;
	}

	public void setCarrieru(String carrieru) {
		this.carrieru = carrieru;
	}

	public void setGlideNum(String glideNum) {
		this.glideNum = glideNum;
	}

	public void setEfile(String efile) {
		this.efile = efile;
	}

	public void setFtime(Date ftime) {
		this.ftime = ftime;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public void setInputMan(String inputMan) {
		this.inputMan = inputMan;
	}

	public void setEtime(Date etime) {
		this.etime = etime;
	}

	public void setDotNum(String dotNum) {
		this.dotNum = dotNum;
	}

	public void setPicNum(String picNum) {
		this.picNum = picNum;
	}

	public void setRecNum(String recNum) {
		this.recNum = recNum;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public void setIsCom(String isCom) {
		this.isCom = isCom;
	}

	public void setIsLocate(String isLocate) {
		this.isLocate = isLocate;
	}

	public void setWcompany(String wcompany) {
		this.wcompany = wcompany;
	}

	public void setWcompanyID(String wcompanyID) {
		this.wcompanyID = wcompanyID;
	}

	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}

	public void setLcontent(String lcontent) {
		this.lcontent = lcontent;
	}

	public void setLcompany(String lcompany) {
		this.lcompany = lcompany;
	}

	public void setLman(String lman) {
		this.lman = lman;
	}

	public void setLlen(String llen) {
		this.llen = llen;
	}

	public void setLdate(Date ldate) {
		this.ldate = ldate;
	}

	public void setJconten(String jconten) {
		this.jconten = jconten;
	}

	public void setJplace(String jplace) {
		this.jplace = jplace;
	}

	public void setJman(String jman) {
		this.jman = jman;
	}

	public void setJback(String jback) {
		this.jback = jback;
	}

	public void setJactor(String jactor) {
		this.jactor = jactor;
	}

	public void setJnum(String jnum) {
		this.jnum = jnum;
	}

	public void setJbnum(String jbnum) {
		this.jbnum = jbnum;
	}

	public void setTnum(String tnum) {
		this.tnum = tnum;
	}

	public void setTzoom(String tzoom) {
		this.tzoom = tzoom;
	}

	public void setTflag(String tflag) {
		this.tflag = tflag;
	}

	public void setTdesigner(String tdesigner) {
		this.tdesigner = tdesigner;
	}

	public void setTviewer(String tviewer) {
		this.tviewer = tviewer;
	}

	public void setTassessor(String tassessor) {
		this.tassessor = tassessor;
	}

	public void setTvision(String tvision) {
		this.tvision = tvision;
	}

	public void setClistNo(Integer clistNo) {
		this.clistNo = clistNo;
	}

	public void setCpageNo(String cpageNo) {
		this.cpageNo = cpageNo;
	}

	public void setVnum(String vnum) {
		this.vnum = vnum;
	}

	public void setCvnum(String cvnum) {
		this.cvnum = cvnum;
	}

	public void setTreedListStr(String treedListStr) {
		this.treedListStr = treedListStr;
	}

	public void setCtimeEnd(Date ctimeEnd) {
		this.ctimeEnd = ctimeEnd;
	}

	public void setProjIndex(Integer projIndex) {
		this.projIndex = projIndex;
	}

	public void setTreeParent(Integer treeParent) {
		this.treeParent = treeParent;
	}

	public void setTreeList(Integer treeList) {
		this.treeList = treeList;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pfile other = (Pfile) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public Pfile jsonToObject(JSONObject jsonObject) {
		return PfileJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return PfileJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return PfileJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
