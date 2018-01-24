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
import com.glaf.isdp.util.PinfoJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "PINFO")
public class Pinfo implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected String id;

	@Column(name = "INDEX_ID")
	protected Integer indexId;

	@Column(name = "ITEMNUM", length = 50)
	protected String itemNum;

	@Column(name = "DTAG", length = 1)
	protected String dtag;

	@Column(name = "FTAG", length = 1)
	protected String ftag;

	@Column(name = "NAME", length = 100)
	protected String name;

	@Column(name = "ALLNAME", length = 100)
	protected String allName;

	@Column(name = "BCOMPANY", length = 100)
	protected String bcompany;

	@Column(name = "CCOMPANY", length = 100)
	protected String ccompany;

	@Column(name = "DCOMPANY", length = 100)
	protected String dcompany;

	@Column(name = "CONCOMPANY", length = 100)
	protected String conCompany;

	@Column(name = "ICOMPANY", length = 100)
	protected String icompany;

	@Column(name = "CMARK", length = 30)
	protected String cmark;

	@Column(name = "PMARK", length = 30)
	protected String pmark;

	@Column(name = "TPMARK", length = 30)
	protected String tpmark;

	@Column(name = "CONMARK", length = 30)
	protected String conMark;

	@Column(name = "MAPNUM", length = 30)
	protected String mapNum;

	@Column(name = "CONSTART", length = 100)
	protected String conStart;

	@Column(name = "CONEND", length = 100)
	protected String conEnd;

	@Column(name = "TOTALLEN")
	protected Double totalLen;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STARTDATE")
	protected Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENDDATE")
	protected Date endDate;

	@Column(name = "COST")
	protected Double cost;

	@Column(name = "BALANCE")
	protected Double balance;

	@Column(name = "PMANNAGER", length = 20)
	protected String pmannager;

	@Column(name = "ENGINEE", length = 20)
	protected String enginee;

	@Column(name = "OWNER", length = 20)
	protected String owner;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PLANDATE")
	protected Date planDate;

	@Column(name = "HINTDAY")
	protected Integer hintDay;

	@Column(name = "DUTY", length = 20)
	protected String duty;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REMOVEDATE")
	protected Date removeDate;

	@Column(name = "REMOVEDUTY", length = 20)
	protected String removeDuty;

	@Column(name = "REMOVEFILE", length = 50)
	protected String removeFile;

	@Column(name = "PARTPROJ", length = 100)
	protected String partProj;

	@Column(name = "CNUM", length = 20)
	protected String cnum;

	@Column(name = "CONCOMPANY2", length = 100)
	protected String concompany2;

	@Column(name = "ICOMPANY2", length = 100)
	protected String icompany2;

	@Column(name = "MILEAGE")
	protected Double mileAge;

	@Column(name = "PLACE", length = 100)
	protected String place;

	@Column(name = "PLACE1")
	protected Double place1;

	@Column(name = "PLACE2")
	protected Double place2;

	@Column(name = "PLACE3")
	protected Double place3;

	@Column(name = "PLACE4")
	protected Double place4;

	@Column(name = "SETPLACE", length = 20)
	protected String setPlace;

	@Column(name = "SETTEMP", length = 20)
	protected String setTemp;

	@Column(name = "BDNUM", length = 50)
	protected String bdNum;

	@Column(name = "DTNUM", length = 50)
	protected String dtNum;

	@Column(name = "PINFO_USER2", length = 100)
	protected String pinfoUser2;

	@Column(name = "PINFO_USER3")
	protected Double pinfoUser3;

	@Column(name = "PINFO_USER4")
	protected Double pinfoUser4;

	@Column(name = "PINFO_USER5", length = 20)
	protected String pinfoUser5;

	@Column(name = "PINFO_USER6")
	protected Integer pinfoUser6;

	@Column(name = "PINFO_USER7")
	protected Double pinfoUser7;

	@Column(name = "PINFO_USER8")
	protected Double pinfoUser8;

	@Column(name = "PINFO_USER9", length = 100)
	protected String pinfoUser9;

	public Pinfo() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getIndexId() {
		return this.indexId;
	}

	public String getItemNum() {
		return this.itemNum;
	}

	public String getDtag() {
		return this.dtag;
	}

	public String getFtag() {
		return this.ftag;
	}

	public String getName() {
		return this.name;
	}

	public String getAllName() {
		return this.allName;
	}

	public String getBcompany() {
		return this.bcompany;
	}

	public String getCcompany() {
		return this.ccompany;
	}

	public String getDcompany() {
		return this.dcompany;
	}

	public String getConCompany() {
		return this.conCompany;
	}

	public String getIcompany() {
		return this.icompany;
	}

	public String getCmark() {
		return this.cmark;
	}

	public String getPmark() {
		return this.pmark;
	}

	public String getTpmark() {
		return this.tpmark;
	}

	public String getConMark() {
		return this.conMark;
	}

	public String getMapNum() {
		return this.mapNum;
	}

	public String getConStart() {
		return this.conStart;
	}

	public String getConEnd() {
		return this.conEnd;
	}

	public Double getTotalLen() {
		return this.totalLen;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public String getStartDateString() {
		if (this.startDate != null) {
			return DateUtils.getDateTime(this.startDate);
		}
		return "";
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public String getEndDateString() {
		if (this.endDate != null) {
			return DateUtils.getDateTime(this.endDate);
		}
		return "";
	}

	public Double getCost() {
		return this.cost;
	}

	public Double getBalance() {
		return this.balance;
	}

	public String getPmannager() {
		return this.pmannager;
	}

	public String getEnginee() {
		return this.enginee;
	}

	public String getOwner() {
		return this.owner;
	}

	public Date getPlanDate() {
		return this.planDate;
	}

	public String getPlanDateString() {
		if (this.planDate != null) {
			return DateUtils.getDateTime(this.planDate);
		}
		return "";
	}

	public Integer getHintDay() {
		return this.hintDay;
	}

	public String getDuty() {
		return this.duty;
	}

	public Date getRemoveDate() {
		return this.removeDate;
	}

	public String getRemoveDateString() {
		if (this.removeDate != null) {
			return DateUtils.getDateTime(this.removeDate);
		}
		return "";
	}

	public String getRemoveDuty() {
		return this.removeDuty;
	}

	public String getRemoveFile() {
		return this.removeFile;
	}

	public String getPartProj() {
		return this.partProj;
	}

	public String getCnum() {
		return this.cnum;
	}

	public String getConcompany2() {
		return this.concompany2;
	}

	public String getIcompany2() {
		return this.icompany2;
	}

	public Double getMileAge() {
		return this.mileAge;
	}

	public String getPlace() {
		return this.place;
	}

	public Double getPlace1() {
		return this.place1;
	}

	public Double getPlace2() {
		return this.place2;
	}

	public Double getPlace3() {
		return this.place3;
	}

	public Double getPlace4() {
		return this.place4;
	}

	public String getSetPlace() {
		return this.setPlace;
	}

	public String getSetTemp() {
		return this.setTemp;
	}

	public String getBdNum() {
		return this.bdNum;
	}

	public String getDtNum() {
		return this.dtNum;
	}

	public String getPinfoUser2() {
		return this.pinfoUser2;
	}

	public Double getPinfoUser3() {
		return this.pinfoUser3;
	}

	public Double getPinfoUser4() {
		return this.pinfoUser4;
	}

	public String getPinfoUser5() {
		return this.pinfoUser5;
	}

	public Integer getPinfoUser6() {
		return this.pinfoUser6;
	}

	public Double getPinfoUser7() {
		return this.pinfoUser7;
	}

	public Double getPinfoUser8() {
		return this.pinfoUser8;
	}

	public String getPinfoUser9() {
		return this.pinfoUser9;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}

	public void setDtag(String dtag) {
		this.dtag = dtag;
	}

	public void setFtag(String ftag) {
		this.ftag = ftag;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAllName(String allName) {
		this.allName = allName;
	}

	public void setBcompany(String bcompany) {
		this.bcompany = bcompany;
	}

	public void setCcompany(String ccompany) {
		this.ccompany = ccompany;
	}

	public void setDcompany(String dcompany) {
		this.dcompany = dcompany;
	}

	public void setConCompany(String conCompany) {
		this.conCompany = conCompany;
	}

	public void setIcompany(String icompany) {
		this.icompany = icompany;
	}

	public void setCmark(String cmark) {
		this.cmark = cmark;
	}

	public void setPmark(String pmark) {
		this.pmark = pmark;
	}

	public void setTpmark(String tpmark) {
		this.tpmark = tpmark;
	}

	public void setConMark(String conMark) {
		this.conMark = conMark;
	}

	public void setMapNum(String mapNum) {
		this.mapNum = mapNum;
	}

	public void setConStart(String conStart) {
		this.conStart = conStart;
	}

	public void setConEnd(String conEnd) {
		this.conEnd = conEnd;
	}

	public void setTotalLen(Double totalLen) {
		this.totalLen = totalLen;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public void setPmannager(String pmannager) {
		this.pmannager = pmannager;
	}

	public void setEnginee(String enginee) {
		this.enginee = enginee;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public void setHintDay(Integer hintDay) {
		this.hintDay = hintDay;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public void setRemoveDate(Date removeDate) {
		this.removeDate = removeDate;
	}

	public void setRemoveDuty(String removeDuty) {
		this.removeDuty = removeDuty;
	}

	public void setRemoveFile(String removeFile) {
		this.removeFile = removeFile;
	}

	public void setPartProj(String partProj) {
		this.partProj = partProj;
	}

	public void setCnum(String cnum) {
		this.cnum = cnum;
	}

	public void setConcompany2(String concompany2) {
		this.concompany2 = concompany2;
	}

	public void setIcompany2(String icompany2) {
		this.icompany2 = icompany2;
	}

	public void setMileAge(Double mileAge) {
		this.mileAge = mileAge;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public void setPlace1(Double place1) {
		this.place1 = place1;
	}

	public void setPlace2(Double place2) {
		this.place2 = place2;
	}

	public void setPlace3(Double place3) {
		this.place3 = place3;
	}

	public void setPlace4(Double place4) {
		this.place4 = place4;
	}

	public void setSetPlace(String setPlace) {
		this.setPlace = setPlace;
	}

	public void setSetTemp(String setTemp) {
		this.setTemp = setTemp;
	}

	public void setBdNum(String bdNum) {
		this.bdNum = bdNum;
	}

	public void setDtNum(String dtNum) {
		this.dtNum = dtNum;
	}

	public void setPinfoUser2(String pinfoUser2) {
		this.pinfoUser2 = pinfoUser2;
	}

	public void setPinfoUser3(Double pinfoUser3) {
		this.pinfoUser3 = pinfoUser3;
	}

	public void setPinfoUser4(Double pinfoUser4) {
		this.pinfoUser4 = pinfoUser4;
	}

	public void setPinfoUser5(String pinfoUser5) {
		this.pinfoUser5 = pinfoUser5;
	}

	public void setPinfoUser6(Integer pinfoUser6) {
		this.pinfoUser6 = pinfoUser6;
	}

	public void setPinfoUser7(Double pinfoUser7) {
		this.pinfoUser7 = pinfoUser7;
	}

	public void setPinfoUser8(Double pinfoUser8) {
		this.pinfoUser8 = pinfoUser8;
	}

	public void setPinfoUser9(String pinfoUser9) {
		this.pinfoUser9 = pinfoUser9;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pinfo other = (Pinfo) obj;
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

	public Pinfo jsonToObject(JSONObject jsonObject) {
		return PinfoJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return PinfoJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return PinfoJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
