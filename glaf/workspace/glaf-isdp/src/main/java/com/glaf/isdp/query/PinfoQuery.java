package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class PinfoQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected Integer indexId;
	protected Integer indexIdGreaterThanOrEqual;
	protected Integer indexIdLessThanOrEqual;
	protected List<Integer> indexIds;
	protected String itemNum;
	protected String itemNumLike;
	protected List<String> itemNums;
	protected String dtag;
	protected String dtagLike;
	protected List<String> dtags;
	protected String ftag;
	protected String ftagLike;
	protected List<String> ftags;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String allName;
	protected String allNameLike;
	protected List<String> allNames;
	protected String bcompany;
	protected String bcompanyLike;
	protected List<String> bcompanys;
	protected String ccompany;
	protected String ccompanyLike;
	protected List<String> ccompanys;
	protected String dcompany;
	protected String dcompanyLike;
	protected List<String> dcompanys;
	protected String conCompany;
	protected String conCompanyLike;
	protected List<String> conCompanys;
	protected String icompany;
	protected String icompanyLike;
	protected List<String> icompanys;
	protected String cmark;
	protected String cmarkLike;
	protected List<String> cmarks;
	protected String pmark;
	protected String pmarkLike;
	protected List<String> pmarks;
	protected String tpmark;
	protected String tpmarkLike;
	protected List<String> tpmarks;
	protected String conMark;
	protected String conMarkLike;
	protected List<String> conMarks;
	protected String mapNum;
	protected String mapNumLike;
	protected List<String> mapNums;
	protected String conStart;
	protected String conStartLike;
	protected List<String> conStarts;
	protected String conEnd;
	protected String conEndLike;
	protected List<String> conEnds;
	protected Double totalLenGreaterThanOrEqual;
	protected Double totalLenLessThanOrEqual;
	protected Date startDateGreaterThanOrEqual;
	protected Date startDateLessThanOrEqual;
	protected Date endDateGreaterThanOrEqual;
	protected Date endDateLessThanOrEqual;
	protected Double costGreaterThanOrEqual;
	protected Double costLessThanOrEqual;
	protected Double balanceGreaterThanOrEqual;
	protected Double balanceLessThanOrEqual;
	protected String pmannager;
	protected String pmannagerLike;
	protected List<String> pmannagers;
	protected String enginee;
	protected String engineeLike;
	protected List<String> enginees;
	protected String owner;
	protected String ownerLike;
	protected List<String> owners;
	protected Date planDateGreaterThanOrEqual;
	protected Date planDateLessThanOrEqual;
	protected Integer hintDay;
	protected Integer hintDayGreaterThanOrEqual;
	protected Integer hintDayLessThanOrEqual;
	protected List<Integer> hintDays;
	protected String duty;
	protected String dutyLike;
	protected List<String> dutys;
	protected Date removeDateGreaterThanOrEqual;
	protected Date removeDateLessThanOrEqual;
	protected String removeDuty;
	protected String removeDutyLike;
	protected List<String> removeDutys;
	protected String removeFile;
	protected String removeFileLike;
	protected List<String> removeFiles;
	protected String partProj;
	protected String partProjLike;
	protected List<String> partProjs;
	protected String cnum;
	protected String cnumLike;
	protected List<String> cnums;
	protected String concompany2;
	protected String concompany2Like;
	protected List<String> concompany2s;
	protected String icompany2;
	protected String icompany2Like;
	protected List<String> icompany2s;
	protected Double mileAgeGreaterThanOrEqual;
	protected Double mileAgeLessThanOrEqual;
	protected String place;
	protected String placeLike;
	protected List<String> places;
	protected Double place1GreaterThanOrEqual;
	protected Double place1LessThanOrEqual;
	protected Double place2GreaterThanOrEqual;
	protected Double place2LessThanOrEqual;
	protected Double place3GreaterThanOrEqual;
	protected Double place3LessThanOrEqual;
	protected Double place4GreaterThanOrEqual;
	protected Double place4LessThanOrEqual;
	protected String setPlace;
	protected String setPlaceLike;
	protected List<String> setPlaces;
	protected String setTemp;
	protected String setTempLike;
	protected List<String> setTemps;
	protected String bdNum;
	protected String bdNumLike;
	protected List<String> bdNums;
	protected String dtNum;
	protected String dtNumLike;
	protected List<String> dtNums;
	protected String pinfoUser2;
	protected String pinfoUser2Like;
	protected List<String> pinfoUser2s;
	protected Double pinfoUser3GreaterThanOrEqual;
	protected Double pinfoUser3LessThanOrEqual;
	protected Double pinfoUser4GreaterThanOrEqual;
	protected Double pinfoUser4LessThanOrEqual;
	protected String pinfoUser5;
	protected String pinfoUser5Like;
	protected List<String> pinfoUser5s;
	protected Integer pinfoUser6;
	protected Integer pinfoUser6GreaterThanOrEqual;
	protected Integer pinfoUser6LessThanOrEqual;
	protected List<Integer> pinfoUser6s;
	protected Double pinfoUser7GreaterThanOrEqual;
	protected Double pinfoUser7LessThanOrEqual;
	protected Double pinfoUser8GreaterThanOrEqual;
	protected Double pinfoUser8LessThanOrEqual;
	protected String pinfoUser9;
	protected String pinfoUser9Like;
	protected List<String> pinfoUser9s;

	public PinfoQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public Integer getIndexId() {
		return indexId;
	}

	public Integer getIndexIdGreaterThanOrEqual() {
		return indexIdGreaterThanOrEqual;
	}

	public Integer getIndexIdLessThanOrEqual() {
		return indexIdLessThanOrEqual;
	}

	public List<Integer> getIndexIds() {
		return indexIds;
	}

	public String getItemNum() {
		return itemNum;
	}

	public String getItemNumLike() {
		if (itemNumLike != null && itemNumLike.trim().length() > 0) {
			if (!itemNumLike.startsWith("%")) {
				itemNumLike = "%" + itemNumLike;
			}
			if (!itemNumLike.endsWith("%")) {
				itemNumLike = itemNumLike + "%";
			}
		}
		return itemNumLike;
	}

	public List<String> getItemNums() {
		return itemNums;
	}

	public String getDtag() {
		return dtag;
	}

	public String getDtagLike() {
		if (dtagLike != null && dtagLike.trim().length() > 0) {
			if (!dtagLike.startsWith("%")) {
				dtagLike = "%" + dtagLike;
			}
			if (!dtagLike.endsWith("%")) {
				dtagLike = dtagLike + "%";
			}
		}
		return dtagLike;
	}

	public List<String> getDtags() {
		return dtags;
	}

	public String getFtag() {
		return ftag;
	}

	public String getFtagLike() {
		if (ftagLike != null && ftagLike.trim().length() > 0) {
			if (!ftagLike.startsWith("%")) {
				ftagLike = "%" + ftagLike;
			}
			if (!ftagLike.endsWith("%")) {
				ftagLike = ftagLike + "%";
			}
		}
		return ftagLike;
	}

	public List<String> getFtags() {
		return ftags;
	}

	public String getName() {
		return name;
	}

	public String getNameLike() {
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

	public List<String> getNames() {
		return names;
	}

	public String getAllName() {
		return allName;
	}

	public String getAllNameLike() {
		if (allNameLike != null && allNameLike.trim().length() > 0) {
			if (!allNameLike.startsWith("%")) {
				allNameLike = "%" + allNameLike;
			}
			if (!allNameLike.endsWith("%")) {
				allNameLike = allNameLike + "%";
			}
		}
		return allNameLike;
	}

	public List<String> getAllNames() {
		return allNames;
	}

	public String getBcompany() {
		return bcompany;
	}

	public String getBcompanyLike() {
		if (bcompanyLike != null && bcompanyLike.trim().length() > 0) {
			if (!bcompanyLike.startsWith("%")) {
				bcompanyLike = "%" + bcompanyLike;
			}
			if (!bcompanyLike.endsWith("%")) {
				bcompanyLike = bcompanyLike + "%";
			}
		}
		return bcompanyLike;
	}

	public List<String> getBcompanys() {
		return bcompanys;
	}

	public String getCcompany() {
		return ccompany;
	}

	public String getCcompanyLike() {
		if (ccompanyLike != null && ccompanyLike.trim().length() > 0) {
			if (!ccompanyLike.startsWith("%")) {
				ccompanyLike = "%" + ccompanyLike;
			}
			if (!ccompanyLike.endsWith("%")) {
				ccompanyLike = ccompanyLike + "%";
			}
		}
		return ccompanyLike;
	}

	public List<String> getCcompanys() {
		return ccompanys;
	}

	public String getDcompany() {
		return dcompany;
	}

	public String getDcompanyLike() {
		if (dcompanyLike != null && dcompanyLike.trim().length() > 0) {
			if (!dcompanyLike.startsWith("%")) {
				dcompanyLike = "%" + dcompanyLike;
			}
			if (!dcompanyLike.endsWith("%")) {
				dcompanyLike = dcompanyLike + "%";
			}
		}
		return dcompanyLike;
	}

	public List<String> getDcompanys() {
		return dcompanys;
	}

	public String getConCompany() {
		return conCompany;
	}

	public String getConCompanyLike() {
		if (conCompanyLike != null && conCompanyLike.trim().length() > 0) {
			if (!conCompanyLike.startsWith("%")) {
				conCompanyLike = "%" + conCompanyLike;
			}
			if (!conCompanyLike.endsWith("%")) {
				conCompanyLike = conCompanyLike + "%";
			}
		}
		return conCompanyLike;
	}

	public List<String> getConCompanys() {
		return conCompanys;
	}

	public String getIcompany() {
		return icompany;
	}

	public String getIcompanyLike() {
		if (icompanyLike != null && icompanyLike.trim().length() > 0) {
			if (!icompanyLike.startsWith("%")) {
				icompanyLike = "%" + icompanyLike;
			}
			if (!icompanyLike.endsWith("%")) {
				icompanyLike = icompanyLike + "%";
			}
		}
		return icompanyLike;
	}

	public List<String> getIcompanys() {
		return icompanys;
	}

	public String getCmark() {
		return cmark;
	}

	public String getCmarkLike() {
		if (cmarkLike != null && cmarkLike.trim().length() > 0) {
			if (!cmarkLike.startsWith("%")) {
				cmarkLike = "%" + cmarkLike;
			}
			if (!cmarkLike.endsWith("%")) {
				cmarkLike = cmarkLike + "%";
			}
		}
		return cmarkLike;
	}

	public List<String> getCmarks() {
		return cmarks;
	}

	public String getPmark() {
		return pmark;
	}

	public String getPmarkLike() {
		if (pmarkLike != null && pmarkLike.trim().length() > 0) {
			if (!pmarkLike.startsWith("%")) {
				pmarkLike = "%" + pmarkLike;
			}
			if (!pmarkLike.endsWith("%")) {
				pmarkLike = pmarkLike + "%";
			}
		}
		return pmarkLike;
	}

	public List<String> getPmarks() {
		return pmarks;
	}

	public String getTpmark() {
		return tpmark;
	}

	public String getTpmarkLike() {
		if (tpmarkLike != null && tpmarkLike.trim().length() > 0) {
			if (!tpmarkLike.startsWith("%")) {
				tpmarkLike = "%" + tpmarkLike;
			}
			if (!tpmarkLike.endsWith("%")) {
				tpmarkLike = tpmarkLike + "%";
			}
		}
		return tpmarkLike;
	}

	public List<String> getTpmarks() {
		return tpmarks;
	}

	public String getConMark() {
		return conMark;
	}

	public String getConMarkLike() {
		if (conMarkLike != null && conMarkLike.trim().length() > 0) {
			if (!conMarkLike.startsWith("%")) {
				conMarkLike = "%" + conMarkLike;
			}
			if (!conMarkLike.endsWith("%")) {
				conMarkLike = conMarkLike + "%";
			}
		}
		return conMarkLike;
	}

	public List<String> getConMarks() {
		return conMarks;
	}

	public String getMapNum() {
		return mapNum;
	}

	public String getMapNumLike() {
		if (mapNumLike != null && mapNumLike.trim().length() > 0) {
			if (!mapNumLike.startsWith("%")) {
				mapNumLike = "%" + mapNumLike;
			}
			if (!mapNumLike.endsWith("%")) {
				mapNumLike = mapNumLike + "%";
			}
		}
		return mapNumLike;
	}

	public List<String> getMapNums() {
		return mapNums;
	}

	public String getConStart() {
		return conStart;
	}

	public String getConStartLike() {
		if (conStartLike != null && conStartLike.trim().length() > 0) {
			if (!conStartLike.startsWith("%")) {
				conStartLike = "%" + conStartLike;
			}
			if (!conStartLike.endsWith("%")) {
				conStartLike = conStartLike + "%";
			}
		}
		return conStartLike;
	}

	public List<String> getConStarts() {
		return conStarts;
	}

	public String getConEnd() {
		return conEnd;
	}

	public String getConEndLike() {
		if (conEndLike != null && conEndLike.trim().length() > 0) {
			if (!conEndLike.startsWith("%")) {
				conEndLike = "%" + conEndLike;
			}
			if (!conEndLike.endsWith("%")) {
				conEndLike = conEndLike + "%";
			}
		}
		return conEndLike;
	}

	public List<String> getConEnds() {
		return conEnds;
	}

	public Double getTotalLenGreaterThanOrEqual() {
		return totalLenGreaterThanOrEqual;
	}

	public Double getTotalLenLessThanOrEqual() {
		return totalLenLessThanOrEqual;
	}

	public Date getStartDateGreaterThanOrEqual() {
		return startDateGreaterThanOrEqual;
	}

	public Date getStartDateLessThanOrEqual() {
		return startDateLessThanOrEqual;
	}

	public Date getEndDateGreaterThanOrEqual() {
		return endDateGreaterThanOrEqual;
	}

	public Date getEndDateLessThanOrEqual() {
		return endDateLessThanOrEqual;
	}

	public Double getCostGreaterThanOrEqual() {
		return costGreaterThanOrEqual;
	}

	public Double getCostLessThanOrEqual() {
		return costLessThanOrEqual;
	}

	public Double getBalanceGreaterThanOrEqual() {
		return balanceGreaterThanOrEqual;
	}

	public Double getBalanceLessThanOrEqual() {
		return balanceLessThanOrEqual;
	}

	public String getPmannager() {
		return pmannager;
	}

	public String getPmannagerLike() {
		if (pmannagerLike != null && pmannagerLike.trim().length() > 0) {
			if (!pmannagerLike.startsWith("%")) {
				pmannagerLike = "%" + pmannagerLike;
			}
			if (!pmannagerLike.endsWith("%")) {
				pmannagerLike = pmannagerLike + "%";
			}
		}
		return pmannagerLike;
	}

	public List<String> getPmannagers() {
		return pmannagers;
	}

	public String getEnginee() {
		return enginee;
	}

	public String getEngineeLike() {
		if (engineeLike != null && engineeLike.trim().length() > 0) {
			if (!engineeLike.startsWith("%")) {
				engineeLike = "%" + engineeLike;
			}
			if (!engineeLike.endsWith("%")) {
				engineeLike = engineeLike + "%";
			}
		}
		return engineeLike;
	}

	public List<String> getEnginees() {
		return enginees;
	}

	public String getOwner() {
		return owner;
	}

	public String getOwnerLike() {
		if (ownerLike != null && ownerLike.trim().length() > 0) {
			if (!ownerLike.startsWith("%")) {
				ownerLike = "%" + ownerLike;
			}
			if (!ownerLike.endsWith("%")) {
				ownerLike = ownerLike + "%";
			}
		}
		return ownerLike;
	}

	public List<String> getOwners() {
		return owners;
	}

	public Date getPlanDateGreaterThanOrEqual() {
		return planDateGreaterThanOrEqual;
	}

	public Date getPlanDateLessThanOrEqual() {
		return planDateLessThanOrEqual;
	}

	public Integer getHintDay() {
		return hintDay;
	}

	public Integer getHintDayGreaterThanOrEqual() {
		return hintDayGreaterThanOrEqual;
	}

	public Integer getHintDayLessThanOrEqual() {
		return hintDayLessThanOrEqual;
	}

	public List<Integer> getHintDays() {
		return hintDays;
	}

	public String getDuty() {
		return duty;
	}

	public String getDutyLike() {
		if (dutyLike != null && dutyLike.trim().length() > 0) {
			if (!dutyLike.startsWith("%")) {
				dutyLike = "%" + dutyLike;
			}
			if (!dutyLike.endsWith("%")) {
				dutyLike = dutyLike + "%";
			}
		}
		return dutyLike;
	}

	public List<String> getDutys() {
		return dutys;
	}

	public Date getRemoveDateGreaterThanOrEqual() {
		return removeDateGreaterThanOrEqual;
	}

	public Date getRemoveDateLessThanOrEqual() {
		return removeDateLessThanOrEqual;
	}

	public String getRemoveDuty() {
		return removeDuty;
	}

	public String getRemoveDutyLike() {
		if (removeDutyLike != null && removeDutyLike.trim().length() > 0) {
			if (!removeDutyLike.startsWith("%")) {
				removeDutyLike = "%" + removeDutyLike;
			}
			if (!removeDutyLike.endsWith("%")) {
				removeDutyLike = removeDutyLike + "%";
			}
		}
		return removeDutyLike;
	}

	public List<String> getRemoveDutys() {
		return removeDutys;
	}

	public String getRemoveFile() {
		return removeFile;
	}

	public String getRemoveFileLike() {
		if (removeFileLike != null && removeFileLike.trim().length() > 0) {
			if (!removeFileLike.startsWith("%")) {
				removeFileLike = "%" + removeFileLike;
			}
			if (!removeFileLike.endsWith("%")) {
				removeFileLike = removeFileLike + "%";
			}
		}
		return removeFileLike;
	}

	public List<String> getRemoveFiles() {
		return removeFiles;
	}

	public String getPartProj() {
		return partProj;
	}

	public String getPartProjLike() {
		if (partProjLike != null && partProjLike.trim().length() > 0) {
			if (!partProjLike.startsWith("%")) {
				partProjLike = "%" + partProjLike;
			}
			if (!partProjLike.endsWith("%")) {
				partProjLike = partProjLike + "%";
			}
		}
		return partProjLike;
	}

	public List<String> getPartProjs() {
		return partProjs;
	}

	public String getCnum() {
		return cnum;
	}

	public String getCnumLike() {
		if (cnumLike != null && cnumLike.trim().length() > 0) {
			if (!cnumLike.startsWith("%")) {
				cnumLike = "%" + cnumLike;
			}
			if (!cnumLike.endsWith("%")) {
				cnumLike = cnumLike + "%";
			}
		}
		return cnumLike;
	}

	public List<String> getCnums() {
		return cnums;
	}

	public String getConcompany2() {
		return concompany2;
	}

	public String getConcompany2Like() {
		if (concompany2Like != null && concompany2Like.trim().length() > 0) {
			if (!concompany2Like.startsWith("%")) {
				concompany2Like = "%" + concompany2Like;
			}
			if (!concompany2Like.endsWith("%")) {
				concompany2Like = concompany2Like + "%";
			}
		}
		return concompany2Like;
	}

	public List<String> getConcompany2s() {
		return concompany2s;
	}

	public String getIcompany2() {
		return icompany2;
	}

	public String getIcompany2Like() {
		if (icompany2Like != null && icompany2Like.trim().length() > 0) {
			if (!icompany2Like.startsWith("%")) {
				icompany2Like = "%" + icompany2Like;
			}
			if (!icompany2Like.endsWith("%")) {
				icompany2Like = icompany2Like + "%";
			}
		}
		return icompany2Like;
	}

	public List<String> getIcompany2s() {
		return icompany2s;
	}

	public Double getMileAgeGreaterThanOrEqual() {
		return mileAgeGreaterThanOrEqual;
	}

	public Double getMileAgeLessThanOrEqual() {
		return mileAgeLessThanOrEqual;
	}

	public String getPlace() {
		return place;
	}

	public String getPlaceLike() {
		if (placeLike != null && placeLike.trim().length() > 0) {
			if (!placeLike.startsWith("%")) {
				placeLike = "%" + placeLike;
			}
			if (!placeLike.endsWith("%")) {
				placeLike = placeLike + "%";
			}
		}
		return placeLike;
	}

	public List<String> getPlaces() {
		return places;
	}

	public Double getPlace1GreaterThanOrEqual() {
		return place1GreaterThanOrEqual;
	}

	public Double getPlace1LessThanOrEqual() {
		return place1LessThanOrEqual;
	}

	public Double getPlace2GreaterThanOrEqual() {
		return place2GreaterThanOrEqual;
	}

	public Double getPlace2LessThanOrEqual() {
		return place2LessThanOrEqual;
	}

	public Double getPlace3GreaterThanOrEqual() {
		return place3GreaterThanOrEqual;
	}

	public Double getPlace3LessThanOrEqual() {
		return place3LessThanOrEqual;
	}

	public Double getPlace4GreaterThanOrEqual() {
		return place4GreaterThanOrEqual;
	}

	public Double getPlace4LessThanOrEqual() {
		return place4LessThanOrEqual;
	}

	public String getSetPlace() {
		return setPlace;
	}

	public String getSetPlaceLike() {
		if (setPlaceLike != null && setPlaceLike.trim().length() > 0) {
			if (!setPlaceLike.startsWith("%")) {
				setPlaceLike = "%" + setPlaceLike;
			}
			if (!setPlaceLike.endsWith("%")) {
				setPlaceLike = setPlaceLike + "%";
			}
		}
		return setPlaceLike;
	}

	public List<String> getSetPlaces() {
		return setPlaces;
	}

	public String getSetTemp() {
		return setTemp;
	}

	public String getSetTempLike() {
		if (setTempLike != null && setTempLike.trim().length() > 0) {
			if (!setTempLike.startsWith("%")) {
				setTempLike = "%" + setTempLike;
			}
			if (!setTempLike.endsWith("%")) {
				setTempLike = setTempLike + "%";
			}
		}
		return setTempLike;
	}

	public List<String> getSetTemps() {
		return setTemps;
	}

	public String getBdNum() {
		return bdNum;
	}

	public String getBdNumLike() {
		if (bdNumLike != null && bdNumLike.trim().length() > 0) {
			if (!bdNumLike.startsWith("%")) {
				bdNumLike = "%" + bdNumLike;
			}
			if (!bdNumLike.endsWith("%")) {
				bdNumLike = bdNumLike + "%";
			}
		}
		return bdNumLike;
	}

	public List<String> getBdNums() {
		return bdNums;
	}

	public String getDtNum() {
		return dtNum;
	}

	public String getDtNumLike() {
		if (dtNumLike != null && dtNumLike.trim().length() > 0) {
			if (!dtNumLike.startsWith("%")) {
				dtNumLike = "%" + dtNumLike;
			}
			if (!dtNumLike.endsWith("%")) {
				dtNumLike = dtNumLike + "%";
			}
		}
		return dtNumLike;
	}

	public List<String> getDtNums() {
		return dtNums;
	}

	public String getPinfoUser2() {
		return pinfoUser2;
	}

	public String getPinfoUser2Like() {
		if (pinfoUser2Like != null && pinfoUser2Like.trim().length() > 0) {
			if (!pinfoUser2Like.startsWith("%")) {
				pinfoUser2Like = "%" + pinfoUser2Like;
			}
			if (!pinfoUser2Like.endsWith("%")) {
				pinfoUser2Like = pinfoUser2Like + "%";
			}
		}
		return pinfoUser2Like;
	}

	public List<String> getPinfoUser2s() {
		return pinfoUser2s;
	}

	public Double getPinfoUser3GreaterThanOrEqual() {
		return pinfoUser3GreaterThanOrEqual;
	}

	public Double getPinfoUser3LessThanOrEqual() {
		return pinfoUser3LessThanOrEqual;
	}

	public Double getPinfoUser4GreaterThanOrEqual() {
		return pinfoUser4GreaterThanOrEqual;
	}

	public Double getPinfoUser4LessThanOrEqual() {
		return pinfoUser4LessThanOrEqual;
	}

	public String getPinfoUser5() {
		return pinfoUser5;
	}

	public String getPinfoUser5Like() {
		if (pinfoUser5Like != null && pinfoUser5Like.trim().length() > 0) {
			if (!pinfoUser5Like.startsWith("%")) {
				pinfoUser5Like = "%" + pinfoUser5Like;
			}
			if (!pinfoUser5Like.endsWith("%")) {
				pinfoUser5Like = pinfoUser5Like + "%";
			}
		}
		return pinfoUser5Like;
	}

	public List<String> getPinfoUser5s() {
		return pinfoUser5s;
	}

	public Integer getPinfoUser6() {
		return pinfoUser6;
	}

	public Integer getPinfoUser6GreaterThanOrEqual() {
		return pinfoUser6GreaterThanOrEqual;
	}

	public Integer getPinfoUser6LessThanOrEqual() {
		return pinfoUser6LessThanOrEqual;
	}

	public List<Integer> getPinfoUser6s() {
		return pinfoUser6s;
	}

	public Double getPinfoUser7GreaterThanOrEqual() {
		return pinfoUser7GreaterThanOrEqual;
	}

	public Double getPinfoUser7LessThanOrEqual() {
		return pinfoUser7LessThanOrEqual;
	}

	public Double getPinfoUser8GreaterThanOrEqual() {
		return pinfoUser8GreaterThanOrEqual;
	}

	public Double getPinfoUser8LessThanOrEqual() {
		return pinfoUser8LessThanOrEqual;
	}

	public String getPinfoUser9() {
		return pinfoUser9;
	}

	public String getPinfoUser9Like() {
		if (pinfoUser9Like != null && pinfoUser9Like.trim().length() > 0) {
			if (!pinfoUser9Like.startsWith("%")) {
				pinfoUser9Like = "%" + pinfoUser9Like;
			}
			if (!pinfoUser9Like.endsWith("%")) {
				pinfoUser9Like = pinfoUser9Like + "%";
			}
		}
		return pinfoUser9Like;
	}

	public List<String> getPinfoUser9s() {
		return pinfoUser9s;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public void setIndexIdGreaterThanOrEqual(Integer indexIdGreaterThanOrEqual) {
		this.indexIdGreaterThanOrEqual = indexIdGreaterThanOrEqual;
	}

	public void setIndexIdLessThanOrEqual(Integer indexIdLessThanOrEqual) {
		this.indexIdLessThanOrEqual = indexIdLessThanOrEqual;
	}

	public void setIndexIds(List<Integer> indexIds) {
		this.indexIds = indexIds;
	}

	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}

	public void setItemNumLike(String itemNumLike) {
		this.itemNumLike = itemNumLike;
	}

	public void setItemNums(List<String> itemNums) {
		this.itemNums = itemNums;
	}

	public void setDtag(String dtag) {
		this.dtag = dtag;
	}

	public void setDtagLike(String dtagLike) {
		this.dtagLike = dtagLike;
	}

	public void setDtags(List<String> dtags) {
		this.dtags = dtags;
	}

	public void setFtag(String ftag) {
		this.ftag = ftag;
	}

	public void setFtagLike(String ftagLike) {
		this.ftagLike = ftagLike;
	}

	public void setFtags(List<String> ftags) {
		this.ftags = ftags;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public void setAllName(String allName) {
		this.allName = allName;
	}

	public void setAllNameLike(String allNameLike) {
		this.allNameLike = allNameLike;
	}

	public void setAllNames(List<String> allNames) {
		this.allNames = allNames;
	}

	public void setBcompany(String bcompany) {
		this.bcompany = bcompany;
	}

	public void setBcompanyLike(String bcompanyLike) {
		this.bcompanyLike = bcompanyLike;
	}

	public void setBcompanys(List<String> bcompanys) {
		this.bcompanys = bcompanys;
	}

	public void setCcompany(String ccompany) {
		this.ccompany = ccompany;
	}

	public void setCcompanyLike(String ccompanyLike) {
		this.ccompanyLike = ccompanyLike;
	}

	public void setCcompanys(List<String> ccompanys) {
		this.ccompanys = ccompanys;
	}

	public void setDcompany(String dcompany) {
		this.dcompany = dcompany;
	}

	public void setDcompanyLike(String dcompanyLike) {
		this.dcompanyLike = dcompanyLike;
	}

	public void setDcompanys(List<String> dcompanys) {
		this.dcompanys = dcompanys;
	}

	public void setConCompany(String conCompany) {
		this.conCompany = conCompany;
	}

	public void setConCompanyLike(String conCompanyLike) {
		this.conCompanyLike = conCompanyLike;
	}

	public void setConCompanys(List<String> conCompanys) {
		this.conCompanys = conCompanys;
	}

	public void setIcompany(String icompany) {
		this.icompany = icompany;
	}

	public void setIcompanyLike(String icompanyLike) {
		this.icompanyLike = icompanyLike;
	}

	public void setIcompanys(List<String> icompanys) {
		this.icompanys = icompanys;
	}

	public void setCmark(String cmark) {
		this.cmark = cmark;
	}

	public void setCmarkLike(String cmarkLike) {
		this.cmarkLike = cmarkLike;
	}

	public void setCmarks(List<String> cmarks) {
		this.cmarks = cmarks;
	}

	public void setPmark(String pmark) {
		this.pmark = pmark;
	}

	public void setPmarkLike(String pmarkLike) {
		this.pmarkLike = pmarkLike;
	}

	public void setPmarks(List<String> pmarks) {
		this.pmarks = pmarks;
	}

	public void setTpmark(String tpmark) {
		this.tpmark = tpmark;
	}

	public void setTpmarkLike(String tpmarkLike) {
		this.tpmarkLike = tpmarkLike;
	}

	public void setTpmarks(List<String> tpmarks) {
		this.tpmarks = tpmarks;
	}

	public void setConMark(String conMark) {
		this.conMark = conMark;
	}

	public void setConMarkLike(String conMarkLike) {
		this.conMarkLike = conMarkLike;
	}

	public void setConMarks(List<String> conMarks) {
		this.conMarks = conMarks;
	}

	public void setMapNum(String mapNum) {
		this.mapNum = mapNum;
	}

	public void setMapNumLike(String mapNumLike) {
		this.mapNumLike = mapNumLike;
	}

	public void setMapNums(List<String> mapNums) {
		this.mapNums = mapNums;
	}

	public void setConStart(String conStart) {
		this.conStart = conStart;
	}

	public void setConStartLike(String conStartLike) {
		this.conStartLike = conStartLike;
	}

	public void setConStarts(List<String> conStarts) {
		this.conStarts = conStarts;
	}

	public void setConEnd(String conEnd) {
		this.conEnd = conEnd;
	}

	public void setConEndLike(String conEndLike) {
		this.conEndLike = conEndLike;
	}

	public void setConEnds(List<String> conEnds) {
		this.conEnds = conEnds;
	}

	public void setTotalLenGreaterThanOrEqual(Double totalLenGreaterThanOrEqual) {
		this.totalLenGreaterThanOrEqual = totalLenGreaterThanOrEqual;
	}

	public void setTotalLenLessThanOrEqual(Double totalLenLessThanOrEqual) {
		this.totalLenLessThanOrEqual = totalLenLessThanOrEqual;
	}

	public void setStartDateGreaterThanOrEqual(Date startDateGreaterThanOrEqual) {
		this.startDateGreaterThanOrEqual = startDateGreaterThanOrEqual;
	}

	public void setStartDateLessThanOrEqual(Date startDateLessThanOrEqual) {
		this.startDateLessThanOrEqual = startDateLessThanOrEqual;
	}

	public void setEndDateGreaterThanOrEqual(Date endDateGreaterThanOrEqual) {
		this.endDateGreaterThanOrEqual = endDateGreaterThanOrEqual;
	}

	public void setEndDateLessThanOrEqual(Date endDateLessThanOrEqual) {
		this.endDateLessThanOrEqual = endDateLessThanOrEqual;
	}

	public void setCostGreaterThanOrEqual(Double costGreaterThanOrEqual) {
		this.costGreaterThanOrEqual = costGreaterThanOrEqual;
	}

	public void setCostLessThanOrEqual(Double costLessThanOrEqual) {
		this.costLessThanOrEqual = costLessThanOrEqual;
	}

	public void setBalanceGreaterThanOrEqual(Double balanceGreaterThanOrEqual) {
		this.balanceGreaterThanOrEqual = balanceGreaterThanOrEqual;
	}

	public void setBalanceLessThanOrEqual(Double balanceLessThanOrEqual) {
		this.balanceLessThanOrEqual = balanceLessThanOrEqual;
	}

	public void setPmannager(String pmannager) {
		this.pmannager = pmannager;
	}

	public void setPmannagerLike(String pmannagerLike) {
		this.pmannagerLike = pmannagerLike;
	}

	public void setPmannagers(List<String> pmannagers) {
		this.pmannagers = pmannagers;
	}

	public void setEnginee(String enginee) {
		this.enginee = enginee;
	}

	public void setEngineeLike(String engineeLike) {
		this.engineeLike = engineeLike;
	}

	public void setEnginees(List<String> enginees) {
		this.enginees = enginees;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setOwnerLike(String ownerLike) {
		this.ownerLike = ownerLike;
	}

	public void setOwners(List<String> owners) {
		this.owners = owners;
	}

	public void setPlanDateGreaterThanOrEqual(Date planDateGreaterThanOrEqual) {
		this.planDateGreaterThanOrEqual = planDateGreaterThanOrEqual;
	}

	public void setPlanDateLessThanOrEqual(Date planDateLessThanOrEqual) {
		this.planDateLessThanOrEqual = planDateLessThanOrEqual;
	}

	public void setHintDay(Integer hintDay) {
		this.hintDay = hintDay;
	}

	public void setHintDayGreaterThanOrEqual(Integer hintDayGreaterThanOrEqual) {
		this.hintDayGreaterThanOrEqual = hintDayGreaterThanOrEqual;
	}

	public void setHintDayLessThanOrEqual(Integer hintDayLessThanOrEqual) {
		this.hintDayLessThanOrEqual = hintDayLessThanOrEqual;
	}

	public void setHintDays(List<Integer> hintDays) {
		this.hintDays = hintDays;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public void setDutyLike(String dutyLike) {
		this.dutyLike = dutyLike;
	}

	public void setDutys(List<String> dutys) {
		this.dutys = dutys;
	}

	public void setRemoveDateGreaterThanOrEqual(
			Date removeDateGreaterThanOrEqual) {
		this.removeDateGreaterThanOrEqual = removeDateGreaterThanOrEqual;
	}

	public void setRemoveDateLessThanOrEqual(Date removeDateLessThanOrEqual) {
		this.removeDateLessThanOrEqual = removeDateLessThanOrEqual;
	}

	public void setRemoveDuty(String removeDuty) {
		this.removeDuty = removeDuty;
	}

	public void setRemoveDutyLike(String removeDutyLike) {
		this.removeDutyLike = removeDutyLike;
	}

	public void setRemoveDutys(List<String> removeDutys) {
		this.removeDutys = removeDutys;
	}

	public void setRemoveFile(String removeFile) {
		this.removeFile = removeFile;
	}

	public void setRemoveFileLike(String removeFileLike) {
		this.removeFileLike = removeFileLike;
	}

	public void setRemoveFiles(List<String> removeFiles) {
		this.removeFiles = removeFiles;
	}

	public void setPartProj(String partProj) {
		this.partProj = partProj;
	}

	public void setPartProjLike(String partProjLike) {
		this.partProjLike = partProjLike;
	}

	public void setPartProjs(List<String> partProjs) {
		this.partProjs = partProjs;
	}

	public void setCnum(String cnum) {
		this.cnum = cnum;
	}

	public void setCnumLike(String cnumLike) {
		this.cnumLike = cnumLike;
	}

	public void setCnums(List<String> cnums) {
		this.cnums = cnums;
	}

	public void setConcompany2(String concompany2) {
		this.concompany2 = concompany2;
	}

	public void setConcompany2Like(String concompany2Like) {
		this.concompany2Like = concompany2Like;
	}

	public void setConcompany2s(List<String> concompany2s) {
		this.concompany2s = concompany2s;
	}

	public void setIcompany2(String icompany2) {
		this.icompany2 = icompany2;
	}

	public void setIcompany2Like(String icompany2Like) {
		this.icompany2Like = icompany2Like;
	}

	public void setIcompany2s(List<String> icompany2s) {
		this.icompany2s = icompany2s;
	}

	public void setMileAgeGreaterThanOrEqual(Double mileAgeGreaterThanOrEqual) {
		this.mileAgeGreaterThanOrEqual = mileAgeGreaterThanOrEqual;
	}

	public void setMileAgeLessThanOrEqual(Double mileAgeLessThanOrEqual) {
		this.mileAgeLessThanOrEqual = mileAgeLessThanOrEqual;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public void setPlaceLike(String placeLike) {
		this.placeLike = placeLike;
	}

	public void setPlaces(List<String> places) {
		this.places = places;
	}

	public void setPlace1GreaterThanOrEqual(Double place1GreaterThanOrEqual) {
		this.place1GreaterThanOrEqual = place1GreaterThanOrEqual;
	}

	public void setPlace1LessThanOrEqual(Double place1LessThanOrEqual) {
		this.place1LessThanOrEqual = place1LessThanOrEqual;
	}

	public void setPlace2GreaterThanOrEqual(Double place2GreaterThanOrEqual) {
		this.place2GreaterThanOrEqual = place2GreaterThanOrEqual;
	}

	public void setPlace2LessThanOrEqual(Double place2LessThanOrEqual) {
		this.place2LessThanOrEqual = place2LessThanOrEqual;
	}

	public void setPlace3GreaterThanOrEqual(Double place3GreaterThanOrEqual) {
		this.place3GreaterThanOrEqual = place3GreaterThanOrEqual;
	}

	public void setPlace3LessThanOrEqual(Double place3LessThanOrEqual) {
		this.place3LessThanOrEqual = place3LessThanOrEqual;
	}

	public void setPlace4GreaterThanOrEqual(Double place4GreaterThanOrEqual) {
		this.place4GreaterThanOrEqual = place4GreaterThanOrEqual;
	}

	public void setPlace4LessThanOrEqual(Double place4LessThanOrEqual) {
		this.place4LessThanOrEqual = place4LessThanOrEqual;
	}

	public void setSetPlace(String setPlace) {
		this.setPlace = setPlace;
	}

	public void setSetPlaceLike(String setPlaceLike) {
		this.setPlaceLike = setPlaceLike;
	}

	public void setSetPlaces(List<String> setPlaces) {
		this.setPlaces = setPlaces;
	}

	public void setSetTemp(String setTemp) {
		this.setTemp = setTemp;
	}

	public void setSetTempLike(String setTempLike) {
		this.setTempLike = setTempLike;
	}

	public void setSetTemps(List<String> setTemps) {
		this.setTemps = setTemps;
	}

	public void setBdNum(String bdNum) {
		this.bdNum = bdNum;
	}

	public void setBdNumLike(String bdNumLike) {
		this.bdNumLike = bdNumLike;
	}

	public void setBdNums(List<String> bdNums) {
		this.bdNums = bdNums;
	}

	public void setDtNum(String dtNum) {
		this.dtNum = dtNum;
	}

	public void setDtNumLike(String dtNumLike) {
		this.dtNumLike = dtNumLike;
	}

	public void setDtNums(List<String> dtNums) {
		this.dtNums = dtNums;
	}

	public void setPinfoUser2(String pinfoUser2) {
		this.pinfoUser2 = pinfoUser2;
	}

	public void setPinfoUser2Like(String pinfoUser2Like) {
		this.pinfoUser2Like = pinfoUser2Like;
	}

	public void setPinfoUser2s(List<String> pinfoUser2s) {
		this.pinfoUser2s = pinfoUser2s;
	}

	public void setPinfoUser3GreaterThanOrEqual(
			Double pinfoUser3GreaterThanOrEqual) {
		this.pinfoUser3GreaterThanOrEqual = pinfoUser3GreaterThanOrEqual;
	}

	public void setPinfoUser3LessThanOrEqual(Double pinfoUser3LessThanOrEqual) {
		this.pinfoUser3LessThanOrEqual = pinfoUser3LessThanOrEqual;
	}

	public void setPinfoUser4GreaterThanOrEqual(
			Double pinfoUser4GreaterThanOrEqual) {
		this.pinfoUser4GreaterThanOrEqual = pinfoUser4GreaterThanOrEqual;
	}

	public void setPinfoUser4LessThanOrEqual(Double pinfoUser4LessThanOrEqual) {
		this.pinfoUser4LessThanOrEqual = pinfoUser4LessThanOrEqual;
	}

	public void setPinfoUser5(String pinfoUser5) {
		this.pinfoUser5 = pinfoUser5;
	}

	public void setPinfoUser5Like(String pinfoUser5Like) {
		this.pinfoUser5Like = pinfoUser5Like;
	}

	public void setPinfoUser5s(List<String> pinfoUser5s) {
		this.pinfoUser5s = pinfoUser5s;
	}

	public void setPinfoUser6(Integer pinfoUser6) {
		this.pinfoUser6 = pinfoUser6;
	}

	public void setPinfoUser6GreaterThanOrEqual(
			Integer pinfoUser6GreaterThanOrEqual) {
		this.pinfoUser6GreaterThanOrEqual = pinfoUser6GreaterThanOrEqual;
	}

	public void setPinfoUser6LessThanOrEqual(Integer pinfoUser6LessThanOrEqual) {
		this.pinfoUser6LessThanOrEqual = pinfoUser6LessThanOrEqual;
	}

	public void setPinfoUser6s(List<Integer> pinfoUser6s) {
		this.pinfoUser6s = pinfoUser6s;
	}

	public void setPinfoUser7GreaterThanOrEqual(
			Double pinfoUser7GreaterThanOrEqual) {
		this.pinfoUser7GreaterThanOrEqual = pinfoUser7GreaterThanOrEqual;
	}

	public void setPinfoUser7LessThanOrEqual(Double pinfoUser7LessThanOrEqual) {
		this.pinfoUser7LessThanOrEqual = pinfoUser7LessThanOrEqual;
	}

	public void setPinfoUser8GreaterThanOrEqual(
			Double pinfoUser8GreaterThanOrEqual) {
		this.pinfoUser8GreaterThanOrEqual = pinfoUser8GreaterThanOrEqual;
	}

	public void setPinfoUser8LessThanOrEqual(Double pinfoUser8LessThanOrEqual) {
		this.pinfoUser8LessThanOrEqual = pinfoUser8LessThanOrEqual;
	}

	public void setPinfoUser9(String pinfoUser9) {
		this.pinfoUser9 = pinfoUser9;
	}

	public void setPinfoUser9Like(String pinfoUser9Like) {
		this.pinfoUser9Like = pinfoUser9Like;
	}

	public void setPinfoUser9s(List<String> pinfoUser9s) {
		this.pinfoUser9s = pinfoUser9s;
	}

	public PinfoQuery indexId(Integer indexId) {
		if (indexId == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexId = indexId;
		return this;
	}

	public PinfoQuery indexIdGreaterThanOrEqual(
			Integer indexIdGreaterThanOrEqual) {
		if (indexIdGreaterThanOrEqual == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexIdGreaterThanOrEqual = indexIdGreaterThanOrEqual;
		return this;
	}

	public PinfoQuery indexIdLessThanOrEqual(Integer indexIdLessThanOrEqual) {
		if (indexIdLessThanOrEqual == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexIdLessThanOrEqual = indexIdLessThanOrEqual;
		return this;
	}

	public PinfoQuery indexIds(List<Integer> indexIds) {
		if (indexIds == null) {
			throw new RuntimeException("indexIds is empty ");
		}
		this.indexIds = indexIds;
		return this;
	}

	public PinfoQuery itemNum(String itemNum) {
		if (itemNum == null) {
			throw new RuntimeException("itemNum is null");
		}
		this.itemNum = itemNum;
		return this;
	}

	public PinfoQuery itemNumLike(String itemNumLike) {
		if (itemNumLike == null) {
			throw new RuntimeException("itemNum is null");
		}
		this.itemNumLike = itemNumLike;
		return this;
	}

	public PinfoQuery itemNums(List<String> itemNums) {
		if (itemNums == null) {
			throw new RuntimeException("itemNums is empty ");
		}
		this.itemNums = itemNums;
		return this;
	}

	public PinfoQuery dtag(String dtag) {
		if (dtag == null) {
			throw new RuntimeException("dtag is null");
		}
		this.dtag = dtag;
		return this;
	}

	public PinfoQuery dtagLike(String dtagLike) {
		if (dtagLike == null) {
			throw new RuntimeException("dtag is null");
		}
		this.dtagLike = dtagLike;
		return this;
	}

	public PinfoQuery dtags(List<String> dtags) {
		if (dtags == null) {
			throw new RuntimeException("dtags is empty ");
		}
		this.dtags = dtags;
		return this;
	}

	public PinfoQuery ftag(String ftag) {
		if (ftag == null) {
			throw new RuntimeException("ftag is null");
		}
		this.ftag = ftag;
		return this;
	}

	public PinfoQuery ftagLike(String ftagLike) {
		if (ftagLike == null) {
			throw new RuntimeException("ftag is null");
		}
		this.ftagLike = ftagLike;
		return this;
	}

	public PinfoQuery ftags(List<String> ftags) {
		if (ftags == null) {
			throw new RuntimeException("ftags is empty ");
		}
		this.ftags = ftags;
		return this;
	}

	public PinfoQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public PinfoQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public PinfoQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public PinfoQuery allName(String allName) {
		if (allName == null) {
			throw new RuntimeException("allName is null");
		}
		this.allName = allName;
		return this;
	}

	public PinfoQuery allNameLike(String allNameLike) {
		if (allNameLike == null) {
			throw new RuntimeException("allName is null");
		}
		this.allNameLike = allNameLike;
		return this;
	}

	public PinfoQuery allNames(List<String> allNames) {
		if (allNames == null) {
			throw new RuntimeException("allNames is empty ");
		}
		this.allNames = allNames;
		return this;
	}

	public PinfoQuery bcompany(String bcompany) {
		if (bcompany == null) {
			throw new RuntimeException("bcompany is null");
		}
		this.bcompany = bcompany;
		return this;
	}

	public PinfoQuery bcompanyLike(String bcompanyLike) {
		if (bcompanyLike == null) {
			throw new RuntimeException("bcompany is null");
		}
		this.bcompanyLike = bcompanyLike;
		return this;
	}

	public PinfoQuery bcompanys(List<String> bcompanys) {
		if (bcompanys == null) {
			throw new RuntimeException("bcompanys is empty ");
		}
		this.bcompanys = bcompanys;
		return this;
	}

	public PinfoQuery ccompany(String ccompany) {
		if (ccompany == null) {
			throw new RuntimeException("ccompany is null");
		}
		this.ccompany = ccompany;
		return this;
	}

	public PinfoQuery ccompanyLike(String ccompanyLike) {
		if (ccompanyLike == null) {
			throw new RuntimeException("ccompany is null");
		}
		this.ccompanyLike = ccompanyLike;
		return this;
	}

	public PinfoQuery ccompanys(List<String> ccompanys) {
		if (ccompanys == null) {
			throw new RuntimeException("ccompanys is empty ");
		}
		this.ccompanys = ccompanys;
		return this;
	}

	public PinfoQuery dcompany(String dcompany) {
		if (dcompany == null) {
			throw new RuntimeException("dcompany is null");
		}
		this.dcompany = dcompany;
		return this;
	}

	public PinfoQuery dcompanyLike(String dcompanyLike) {
		if (dcompanyLike == null) {
			throw new RuntimeException("dcompany is null");
		}
		this.dcompanyLike = dcompanyLike;
		return this;
	}

	public PinfoQuery dcompanys(List<String> dcompanys) {
		if (dcompanys == null) {
			throw new RuntimeException("dcompanys is empty ");
		}
		this.dcompanys = dcompanys;
		return this;
	}

	public PinfoQuery conCompany(String conCompany) {
		if (conCompany == null) {
			throw new RuntimeException("conCompany is null");
		}
		this.conCompany = conCompany;
		return this;
	}

	public PinfoQuery conCompanyLike(String conCompanyLike) {
		if (conCompanyLike == null) {
			throw new RuntimeException("conCompany is null");
		}
		this.conCompanyLike = conCompanyLike;
		return this;
	}

	public PinfoQuery conCompanys(List<String> conCompanys) {
		if (conCompanys == null) {
			throw new RuntimeException("conCompanys is empty ");
		}
		this.conCompanys = conCompanys;
		return this;
	}

	public PinfoQuery icompany(String icompany) {
		if (icompany == null) {
			throw new RuntimeException("icompany is null");
		}
		this.icompany = icompany;
		return this;
	}

	public PinfoQuery icompanyLike(String icompanyLike) {
		if (icompanyLike == null) {
			throw new RuntimeException("icompany is null");
		}
		this.icompanyLike = icompanyLike;
		return this;
	}

	public PinfoQuery icompanys(List<String> icompanys) {
		if (icompanys == null) {
			throw new RuntimeException("icompanys is empty ");
		}
		this.icompanys = icompanys;
		return this;
	}

	public PinfoQuery cmark(String cmark) {
		if (cmark == null) {
			throw new RuntimeException("cmark is null");
		}
		this.cmark = cmark;
		return this;
	}

	public PinfoQuery cmarkLike(String cmarkLike) {
		if (cmarkLike == null) {
			throw new RuntimeException("cmark is null");
		}
		this.cmarkLike = cmarkLike;
		return this;
	}

	public PinfoQuery cmarks(List<String> cmarks) {
		if (cmarks == null) {
			throw new RuntimeException("cmarks is empty ");
		}
		this.cmarks = cmarks;
		return this;
	}

	public PinfoQuery pmark(String pmark) {
		if (pmark == null) {
			throw new RuntimeException("pmark is null");
		}
		this.pmark = pmark;
		return this;
	}

	public PinfoQuery pmarkLike(String pmarkLike) {
		if (pmarkLike == null) {
			throw new RuntimeException("pmark is null");
		}
		this.pmarkLike = pmarkLike;
		return this;
	}

	public PinfoQuery pmarks(List<String> pmarks) {
		if (pmarks == null) {
			throw new RuntimeException("pmarks is empty ");
		}
		this.pmarks = pmarks;
		return this;
	}

	public PinfoQuery tpmark(String tpmark) {
		if (tpmark == null) {
			throw new RuntimeException("tpmark is null");
		}
		this.tpmark = tpmark;
		return this;
	}

	public PinfoQuery tpmarkLike(String tpmarkLike) {
		if (tpmarkLike == null) {
			throw new RuntimeException("tpmark is null");
		}
		this.tpmarkLike = tpmarkLike;
		return this;
	}

	public PinfoQuery tpmarks(List<String> tpmarks) {
		if (tpmarks == null) {
			throw new RuntimeException("tpmarks is empty ");
		}
		this.tpmarks = tpmarks;
		return this;
	}

	public PinfoQuery conMark(String conMark) {
		if (conMark == null) {
			throw new RuntimeException("conMark is null");
		}
		this.conMark = conMark;
		return this;
	}

	public PinfoQuery conMarkLike(String conMarkLike) {
		if (conMarkLike == null) {
			throw new RuntimeException("conMark is null");
		}
		this.conMarkLike = conMarkLike;
		return this;
	}

	public PinfoQuery conMarks(List<String> conMarks) {
		if (conMarks == null) {
			throw new RuntimeException("conMarks is empty ");
		}
		this.conMarks = conMarks;
		return this;
	}

	public PinfoQuery mapNum(String mapNum) {
		if (mapNum == null) {
			throw new RuntimeException("mapNum is null");
		}
		this.mapNum = mapNum;
		return this;
	}

	public PinfoQuery mapNumLike(String mapNumLike) {
		if (mapNumLike == null) {
			throw new RuntimeException("mapNum is null");
		}
		this.mapNumLike = mapNumLike;
		return this;
	}

	public PinfoQuery mapNums(List<String> mapNums) {
		if (mapNums == null) {
			throw new RuntimeException("mapNums is empty ");
		}
		this.mapNums = mapNums;
		return this;
	}

	public PinfoQuery conStart(String conStart) {
		if (conStart == null) {
			throw new RuntimeException("conStart is null");
		}
		this.conStart = conStart;
		return this;
	}

	public PinfoQuery conStartLike(String conStartLike) {
		if (conStartLike == null) {
			throw new RuntimeException("conStart is null");
		}
		this.conStartLike = conStartLike;
		return this;
	}

	public PinfoQuery conStarts(List<String> conStarts) {
		if (conStarts == null) {
			throw new RuntimeException("conStarts is empty ");
		}
		this.conStarts = conStarts;
		return this;
	}

	public PinfoQuery conEnd(String conEnd) {
		if (conEnd == null) {
			throw new RuntimeException("conEnd is null");
		}
		this.conEnd = conEnd;
		return this;
	}

	public PinfoQuery conEndLike(String conEndLike) {
		if (conEndLike == null) {
			throw new RuntimeException("conEnd is null");
		}
		this.conEndLike = conEndLike;
		return this;
	}

	public PinfoQuery conEnds(List<String> conEnds) {
		if (conEnds == null) {
			throw new RuntimeException("conEnds is empty ");
		}
		this.conEnds = conEnds;
		return this;
	}

	public PinfoQuery totalLenGreaterThanOrEqual(
			Double totalLenGreaterThanOrEqual) {
		if (totalLenGreaterThanOrEqual == null) {
			throw new RuntimeException("totalLen is null");
		}
		this.totalLenGreaterThanOrEqual = totalLenGreaterThanOrEqual;
		return this;
	}

	public PinfoQuery totalLenLessThanOrEqual(Double totalLenLessThanOrEqual) {
		if (totalLenLessThanOrEqual == null) {
			throw new RuntimeException("totalLen is null");
		}
		this.totalLenLessThanOrEqual = totalLenLessThanOrEqual;
		return this;
	}

	public PinfoQuery startDateGreaterThanOrEqual(
			Date startDateGreaterThanOrEqual) {
		if (startDateGreaterThanOrEqual == null) {
			throw new RuntimeException("startDate is null");
		}
		this.startDateGreaterThanOrEqual = startDateGreaterThanOrEqual;
		return this;
	}

	public PinfoQuery startDateLessThanOrEqual(Date startDateLessThanOrEqual) {
		if (startDateLessThanOrEqual == null) {
			throw new RuntimeException("startDate is null");
		}
		this.startDateLessThanOrEqual = startDateLessThanOrEqual;
		return this;
	}

	public PinfoQuery endDateGreaterThanOrEqual(Date endDateGreaterThanOrEqual) {
		if (endDateGreaterThanOrEqual == null) {
			throw new RuntimeException("endDate is null");
		}
		this.endDateGreaterThanOrEqual = endDateGreaterThanOrEqual;
		return this;
	}

	public PinfoQuery endDateLessThanOrEqual(Date endDateLessThanOrEqual) {
		if (endDateLessThanOrEqual == null) {
			throw new RuntimeException("endDate is null");
		}
		this.endDateLessThanOrEqual = endDateLessThanOrEqual;
		return this;
	}

	public PinfoQuery costGreaterThanOrEqual(Double costGreaterThanOrEqual) {
		if (costGreaterThanOrEqual == null) {
			throw new RuntimeException("cost is null");
		}
		this.costGreaterThanOrEqual = costGreaterThanOrEqual;
		return this;
	}

	public PinfoQuery costLessThanOrEqual(Double costLessThanOrEqual) {
		if (costLessThanOrEqual == null) {
			throw new RuntimeException("cost is null");
		}
		this.costLessThanOrEqual = costLessThanOrEqual;
		return this;
	}

	public PinfoQuery balanceGreaterThanOrEqual(Double balanceGreaterThanOrEqual) {
		if (balanceGreaterThanOrEqual == null) {
			throw new RuntimeException("balance is null");
		}
		this.balanceGreaterThanOrEqual = balanceGreaterThanOrEqual;
		return this;
	}

	public PinfoQuery balanceLessThanOrEqual(Double balanceLessThanOrEqual) {
		if (balanceLessThanOrEqual == null) {
			throw new RuntimeException("balance is null");
		}
		this.balanceLessThanOrEqual = balanceLessThanOrEqual;
		return this;
	}

	public PinfoQuery pmannager(String pmannager) {
		if (pmannager == null) {
			throw new RuntimeException("pmannager is null");
		}
		this.pmannager = pmannager;
		return this;
	}

	public PinfoQuery pmannagerLike(String pmannagerLike) {
		if (pmannagerLike == null) {
			throw new RuntimeException("pmannager is null");
		}
		this.pmannagerLike = pmannagerLike;
		return this;
	}

	public PinfoQuery pmannagers(List<String> pmannagers) {
		if (pmannagers == null) {
			throw new RuntimeException("pmannagers is empty ");
		}
		this.pmannagers = pmannagers;
		return this;
	}

	public PinfoQuery enginee(String enginee) {
		if (enginee == null) {
			throw new RuntimeException("enginee is null");
		}
		this.enginee = enginee;
		return this;
	}

	public PinfoQuery engineeLike(String engineeLike) {
		if (engineeLike == null) {
			throw new RuntimeException("enginee is null");
		}
		this.engineeLike = engineeLike;
		return this;
	}

	public PinfoQuery enginees(List<String> enginees) {
		if (enginees == null) {
			throw new RuntimeException("enginees is empty ");
		}
		this.enginees = enginees;
		return this;
	}

	public PinfoQuery owner(String owner) {
		if (owner == null) {
			throw new RuntimeException("owner is null");
		}
		this.owner = owner;
		return this;
	}

	public PinfoQuery ownerLike(String ownerLike) {
		if (ownerLike == null) {
			throw new RuntimeException("owner is null");
		}
		this.ownerLike = ownerLike;
		return this;
	}

	public PinfoQuery owners(List<String> owners) {
		if (owners == null) {
			throw new RuntimeException("owners is empty ");
		}
		this.owners = owners;
		return this;
	}

	public PinfoQuery planDateGreaterThanOrEqual(Date planDateGreaterThanOrEqual) {
		if (planDateGreaterThanOrEqual == null) {
			throw new RuntimeException("planDate is null");
		}
		this.planDateGreaterThanOrEqual = planDateGreaterThanOrEqual;
		return this;
	}

	public PinfoQuery planDateLessThanOrEqual(Date planDateLessThanOrEqual) {
		if (planDateLessThanOrEqual == null) {
			throw new RuntimeException("planDate is null");
		}
		this.planDateLessThanOrEqual = planDateLessThanOrEqual;
		return this;
	}

	public PinfoQuery hintDay(Integer hintDay) {
		if (hintDay == null) {
			throw new RuntimeException("hintDay is null");
		}
		this.hintDay = hintDay;
		return this;
	}

	public PinfoQuery hintDayGreaterThanOrEqual(
			Integer hintDayGreaterThanOrEqual) {
		if (hintDayGreaterThanOrEqual == null) {
			throw new RuntimeException("hintDay is null");
		}
		this.hintDayGreaterThanOrEqual = hintDayGreaterThanOrEqual;
		return this;
	}

	public PinfoQuery hintDayLessThanOrEqual(Integer hintDayLessThanOrEqual) {
		if (hintDayLessThanOrEqual == null) {
			throw new RuntimeException("hintDay is null");
		}
		this.hintDayLessThanOrEqual = hintDayLessThanOrEqual;
		return this;
	}

	public PinfoQuery hintDays(List<Integer> hintDays) {
		if (hintDays == null) {
			throw new RuntimeException("hintDays is empty ");
		}
		this.hintDays = hintDays;
		return this;
	}

	public PinfoQuery duty(String duty) {
		if (duty == null) {
			throw new RuntimeException("duty is null");
		}
		this.duty = duty;
		return this;
	}

	public PinfoQuery dutyLike(String dutyLike) {
		if (dutyLike == null) {
			throw new RuntimeException("duty is null");
		}
		this.dutyLike = dutyLike;
		return this;
	}

	public PinfoQuery dutys(List<String> dutys) {
		if (dutys == null) {
			throw new RuntimeException("dutys is empty ");
		}
		this.dutys = dutys;
		return this;
	}

	public PinfoQuery removeDateGreaterThanOrEqual(
			Date removeDateGreaterThanOrEqual) {
		if (removeDateGreaterThanOrEqual == null) {
			throw new RuntimeException("removeDate is null");
		}
		this.removeDateGreaterThanOrEqual = removeDateGreaterThanOrEqual;
		return this;
	}

	public PinfoQuery removeDateLessThanOrEqual(Date removeDateLessThanOrEqual) {
		if (removeDateLessThanOrEqual == null) {
			throw new RuntimeException("removeDate is null");
		}
		this.removeDateLessThanOrEqual = removeDateLessThanOrEqual;
		return this;
	}

	public PinfoQuery removeDuty(String removeDuty) {
		if (removeDuty == null) {
			throw new RuntimeException("removeDuty is null");
		}
		this.removeDuty = removeDuty;
		return this;
	}

	public PinfoQuery removeDutyLike(String removeDutyLike) {
		if (removeDutyLike == null) {
			throw new RuntimeException("removeDuty is null");
		}
		this.removeDutyLike = removeDutyLike;
		return this;
	}

	public PinfoQuery removeDutys(List<String> removeDutys) {
		if (removeDutys == null) {
			throw new RuntimeException("removeDutys is empty ");
		}
		this.removeDutys = removeDutys;
		return this;
	}

	public PinfoQuery removeFile(String removeFile) {
		if (removeFile == null) {
			throw new RuntimeException("removeFile is null");
		}
		this.removeFile = removeFile;
		return this;
	}

	public PinfoQuery removeFileLike(String removeFileLike) {
		if (removeFileLike == null) {
			throw new RuntimeException("removeFile is null");
		}
		this.removeFileLike = removeFileLike;
		return this;
	}

	public PinfoQuery removeFiles(List<String> removeFiles) {
		if (removeFiles == null) {
			throw new RuntimeException("removeFiles is empty ");
		}
		this.removeFiles = removeFiles;
		return this;
	}

	public PinfoQuery partProj(String partProj) {
		if (partProj == null) {
			throw new RuntimeException("partProj is null");
		}
		this.partProj = partProj;
		return this;
	}

	public PinfoQuery partProjLike(String partProjLike) {
		if (partProjLike == null) {
			throw new RuntimeException("partProj is null");
		}
		this.partProjLike = partProjLike;
		return this;
	}

	public PinfoQuery partProjs(List<String> partProjs) {
		if (partProjs == null) {
			throw new RuntimeException("partProjs is empty ");
		}
		this.partProjs = partProjs;
		return this;
	}

	public PinfoQuery cnum(String cnum) {
		if (cnum == null) {
			throw new RuntimeException("cnum is null");
		}
		this.cnum = cnum;
		return this;
	}

	public PinfoQuery cnumLike(String cnumLike) {
		if (cnumLike == null) {
			throw new RuntimeException("cnum is null");
		}
		this.cnumLike = cnumLike;
		return this;
	}

	public PinfoQuery cnums(List<String> cnums) {
		if (cnums == null) {
			throw new RuntimeException("cnums is empty ");
		}
		this.cnums = cnums;
		return this;
	}

	public PinfoQuery concompany2(String concompany2) {
		if (concompany2 == null) {
			throw new RuntimeException("concompany2 is null");
		}
		this.concompany2 = concompany2;
		return this;
	}

	public PinfoQuery concompany2Like(String concompany2Like) {
		if (concompany2Like == null) {
			throw new RuntimeException("concompany2 is null");
		}
		this.concompany2Like = concompany2Like;
		return this;
	}

	public PinfoQuery concompany2s(List<String> concompany2s) {
		if (concompany2s == null) {
			throw new RuntimeException("concompany2s is empty ");
		}
		this.concompany2s = concompany2s;
		return this;
	}

	public PinfoQuery icompany2(String icompany2) {
		if (icompany2 == null) {
			throw new RuntimeException("icompany2 is null");
		}
		this.icompany2 = icompany2;
		return this;
	}

	public PinfoQuery icompany2Like(String icompany2Like) {
		if (icompany2Like == null) {
			throw new RuntimeException("icompany2 is null");
		}
		this.icompany2Like = icompany2Like;
		return this;
	}

	public PinfoQuery icompany2s(List<String> icompany2s) {
		if (icompany2s == null) {
			throw new RuntimeException("icompany2s is empty ");
		}
		this.icompany2s = icompany2s;
		return this;
	}

	public PinfoQuery mileAgeGreaterThanOrEqual(Double mileAgeGreaterThanOrEqual) {
		if (mileAgeGreaterThanOrEqual == null) {
			throw new RuntimeException("mileAge is null");
		}
		this.mileAgeGreaterThanOrEqual = mileAgeGreaterThanOrEqual;
		return this;
	}

	public PinfoQuery mileAgeLessThanOrEqual(Double mileAgeLessThanOrEqual) {
		if (mileAgeLessThanOrEqual == null) {
			throw new RuntimeException("mileAge is null");
		}
		this.mileAgeLessThanOrEqual = mileAgeLessThanOrEqual;
		return this;
	}

	public PinfoQuery place(String place) {
		if (place == null) {
			throw new RuntimeException("place is null");
		}
		this.place = place;
		return this;
	}

	public PinfoQuery placeLike(String placeLike) {
		if (placeLike == null) {
			throw new RuntimeException("place is null");
		}
		this.placeLike = placeLike;
		return this;
	}

	public PinfoQuery places(List<String> places) {
		if (places == null) {
			throw new RuntimeException("places is empty ");
		}
		this.places = places;
		return this;
	}

	public PinfoQuery place1GreaterThanOrEqual(Double place1GreaterThanOrEqual) {
		if (place1GreaterThanOrEqual == null) {
			throw new RuntimeException("place1 is null");
		}
		this.place1GreaterThanOrEqual = place1GreaterThanOrEqual;
		return this;
	}

	public PinfoQuery place1LessThanOrEqual(Double place1LessThanOrEqual) {
		if (place1LessThanOrEqual == null) {
			throw new RuntimeException("place1 is null");
		}
		this.place1LessThanOrEqual = place1LessThanOrEqual;
		return this;
	}

	public PinfoQuery place2GreaterThanOrEqual(Double place2GreaterThanOrEqual) {
		if (place2GreaterThanOrEqual == null) {
			throw new RuntimeException("place2 is null");
		}
		this.place2GreaterThanOrEqual = place2GreaterThanOrEqual;
		return this;
	}

	public PinfoQuery place2LessThanOrEqual(Double place2LessThanOrEqual) {
		if (place2LessThanOrEqual == null) {
			throw new RuntimeException("place2 is null");
		}
		this.place2LessThanOrEqual = place2LessThanOrEqual;
		return this;
	}

	public PinfoQuery place3GreaterThanOrEqual(Double place3GreaterThanOrEqual) {
		if (place3GreaterThanOrEqual == null) {
			throw new RuntimeException("place3 is null");
		}
		this.place3GreaterThanOrEqual = place3GreaterThanOrEqual;
		return this;
	}

	public PinfoQuery place3LessThanOrEqual(Double place3LessThanOrEqual) {
		if (place3LessThanOrEqual == null) {
			throw new RuntimeException("place3 is null");
		}
		this.place3LessThanOrEqual = place3LessThanOrEqual;
		return this;
	}

	public PinfoQuery place4GreaterThanOrEqual(Double place4GreaterThanOrEqual) {
		if (place4GreaterThanOrEqual == null) {
			throw new RuntimeException("place4 is null");
		}
		this.place4GreaterThanOrEqual = place4GreaterThanOrEqual;
		return this;
	}

	public PinfoQuery place4LessThanOrEqual(Double place4LessThanOrEqual) {
		if (place4LessThanOrEqual == null) {
			throw new RuntimeException("place4 is null");
		}
		this.place4LessThanOrEqual = place4LessThanOrEqual;
		return this;
	}

	// public PinfoQuery setPlace(String setPlace) {
	// if (setPlace == null) {
	// throw new RuntimeException("setPlace is null");
	// }
	// this.setPlace = setPlace;
	// return this;
	// }
	//
	// public PinfoQuery setPlaceLike(String setPlaceLike) {
	// if (setPlaceLike == null) {
	// throw new RuntimeException("setPlace is null");
	// }
	// this.setPlaceLike = setPlaceLike;
	// return this;
	// }
	//
	// public PinfoQuery setPlaces(List<String> setPlaces) {
	// if (setPlaces == null) {
	// throw new RuntimeException("setPlaces is empty ");
	// }
	// this.setPlaces = setPlaces;
	// return this;
	// }

	public PinfoQuery setTemp(String setTemp) {
		if (setTemp == null) {
			throw new RuntimeException("setTemp is null");
		}
		this.setTemp = setTemp;
		return this;
	}

	public PinfoQuery setTempLike(String setTempLike) {
		if (setTempLike == null) {
			throw new RuntimeException("setTemp is null");
		}
		this.setTempLike = setTempLike;
		return this;
	}

	public PinfoQuery setTemps(List<String> setTemps) {
		if (setTemps == null) {
			throw new RuntimeException("setTemps is empty ");
		}
		this.setTemps = setTemps;
		return this;
	}

	public PinfoQuery bdNum(String bdNum) {
		if (bdNum == null) {
			throw new RuntimeException("bdNum is null");
		}
		this.bdNum = bdNum;
		return this;
	}

	public PinfoQuery bdNumLike(String bdNumLike) {
		if (bdNumLike == null) {
			throw new RuntimeException("bdNum is null");
		}
		this.bdNumLike = bdNumLike;
		return this;
	}

	public PinfoQuery bdNums(List<String> bdNums) {
		if (bdNums == null) {
			throw new RuntimeException("bdNums is empty ");
		}
		this.bdNums = bdNums;
		return this;
	}

	public PinfoQuery dtNum(String dtNum) {
		if (dtNum == null) {
			throw new RuntimeException("dtNum is null");
		}
		this.dtNum = dtNum;
		return this;
	}

	public PinfoQuery dtNumLike(String dtNumLike) {
		if (dtNumLike == null) {
			throw new RuntimeException("dtNum is null");
		}
		this.dtNumLike = dtNumLike;
		return this;
	}

	public PinfoQuery dtNums(List<String> dtNums) {
		if (dtNums == null) {
			throw new RuntimeException("dtNums is empty ");
		}
		this.dtNums = dtNums;
		return this;
	}

	public PinfoQuery pinfoUser2(String pinfoUser2) {
		if (pinfoUser2 == null) {
			throw new RuntimeException("pinfoUser2 is null");
		}
		this.pinfoUser2 = pinfoUser2;
		return this;
	}

	public PinfoQuery pinfoUser2Like(String pinfoUser2Like) {
		if (pinfoUser2Like == null) {
			throw new RuntimeException("pinfoUser2 is null");
		}
		this.pinfoUser2Like = pinfoUser2Like;
		return this;
	}

	public PinfoQuery pinfoUser2s(List<String> pinfoUser2s) {
		if (pinfoUser2s == null) {
			throw new RuntimeException("pinfoUser2s is empty ");
		}
		this.pinfoUser2s = pinfoUser2s;
		return this;
	}

	public PinfoQuery pinfoUser3GreaterThanOrEqual(
			Double pinfoUser3GreaterThanOrEqual) {
		if (pinfoUser3GreaterThanOrEqual == null) {
			throw new RuntimeException("pinfoUser3 is null");
		}
		this.pinfoUser3GreaterThanOrEqual = pinfoUser3GreaterThanOrEqual;
		return this;
	}

	public PinfoQuery pinfoUser3LessThanOrEqual(Double pinfoUser3LessThanOrEqual) {
		if (pinfoUser3LessThanOrEqual == null) {
			throw new RuntimeException("pinfoUser3 is null");
		}
		this.pinfoUser3LessThanOrEqual = pinfoUser3LessThanOrEqual;
		return this;
	}

	public PinfoQuery pinfoUser4GreaterThanOrEqual(
			Double pinfoUser4GreaterThanOrEqual) {
		if (pinfoUser4GreaterThanOrEqual == null) {
			throw new RuntimeException("pinfoUser4 is null");
		}
		this.pinfoUser4GreaterThanOrEqual = pinfoUser4GreaterThanOrEqual;
		return this;
	}

	public PinfoQuery pinfoUser4LessThanOrEqual(Double pinfoUser4LessThanOrEqual) {
		if (pinfoUser4LessThanOrEqual == null) {
			throw new RuntimeException("pinfoUser4 is null");
		}
		this.pinfoUser4LessThanOrEqual = pinfoUser4LessThanOrEqual;
		return this;
	}

	public PinfoQuery pinfoUser5(String pinfoUser5) {
		if (pinfoUser5 == null) {
			throw new RuntimeException("pinfoUser5 is null");
		}
		this.pinfoUser5 = pinfoUser5;
		return this;
	}

	public PinfoQuery pinfoUser5Like(String pinfoUser5Like) {
		if (pinfoUser5Like == null) {
			throw new RuntimeException("pinfoUser5 is null");
		}
		this.pinfoUser5Like = pinfoUser5Like;
		return this;
	}

	public PinfoQuery pinfoUser5s(List<String> pinfoUser5s) {
		if (pinfoUser5s == null) {
			throw new RuntimeException("pinfoUser5s is empty ");
		}
		this.pinfoUser5s = pinfoUser5s;
		return this;
	}

	public PinfoQuery pinfoUser6(Integer pinfoUser6) {
		if (pinfoUser6 == null) {
			throw new RuntimeException("pinfoUser6 is null");
		}
		this.pinfoUser6 = pinfoUser6;
		return this;
	}

	public PinfoQuery pinfoUser6GreaterThanOrEqual(
			Integer pinfoUser6GreaterThanOrEqual) {
		if (pinfoUser6GreaterThanOrEqual == null) {
			throw new RuntimeException("pinfoUser6 is null");
		}
		this.pinfoUser6GreaterThanOrEqual = pinfoUser6GreaterThanOrEqual;
		return this;
	}

	public PinfoQuery pinfoUser6LessThanOrEqual(
			Integer pinfoUser6LessThanOrEqual) {
		if (pinfoUser6LessThanOrEqual == null) {
			throw new RuntimeException("pinfoUser6 is null");
		}
		this.pinfoUser6LessThanOrEqual = pinfoUser6LessThanOrEqual;
		return this;
	}

	public PinfoQuery pinfoUser6s(List<Integer> pinfoUser6s) {
		if (pinfoUser6s == null) {
			throw new RuntimeException("pinfoUser6s is empty ");
		}
		this.pinfoUser6s = pinfoUser6s;
		return this;
	}

	public PinfoQuery pinfoUser7GreaterThanOrEqual(
			Double pinfoUser7GreaterThanOrEqual) {
		if (pinfoUser7GreaterThanOrEqual == null) {
			throw new RuntimeException("pinfoUser7 is null");
		}
		this.pinfoUser7GreaterThanOrEqual = pinfoUser7GreaterThanOrEqual;
		return this;
	}

	public PinfoQuery pinfoUser7LessThanOrEqual(Double pinfoUser7LessThanOrEqual) {
		if (pinfoUser7LessThanOrEqual == null) {
			throw new RuntimeException("pinfoUser7 is null");
		}
		this.pinfoUser7LessThanOrEqual = pinfoUser7LessThanOrEqual;
		return this;
	}

	public PinfoQuery pinfoUser8GreaterThanOrEqual(
			Double pinfoUser8GreaterThanOrEqual) {
		if (pinfoUser8GreaterThanOrEqual == null) {
			throw new RuntimeException("pinfoUser8 is null");
		}
		this.pinfoUser8GreaterThanOrEqual = pinfoUser8GreaterThanOrEqual;
		return this;
	}

	public PinfoQuery pinfoUser8LessThanOrEqual(Double pinfoUser8LessThanOrEqual) {
		if (pinfoUser8LessThanOrEqual == null) {
			throw new RuntimeException("pinfoUser8 is null");
		}
		this.pinfoUser8LessThanOrEqual = pinfoUser8LessThanOrEqual;
		return this;
	}

	public PinfoQuery pinfoUser9(String pinfoUser9) {
		if (pinfoUser9 == null) {
			throw new RuntimeException("pinfoUser9 is null");
		}
		this.pinfoUser9 = pinfoUser9;
		return this;
	}

	public PinfoQuery pinfoUser9Like(String pinfoUser9Like) {
		if (pinfoUser9Like == null) {
			throw new RuntimeException("pinfoUser9 is null");
		}
		this.pinfoUser9Like = pinfoUser9Like;
		return this;
	}

	public PinfoQuery pinfoUser9s(List<String> pinfoUser9s) {
		if (pinfoUser9s == null) {
			throw new RuntimeException("pinfoUser9s is empty ");
		}
		this.pinfoUser9s = pinfoUser9s;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("indexId".equals(sortColumn)) {
				orderBy = "E.INDEX_ID" + a_x;
			}

			if ("itemNum".equals(sortColumn)) {
				orderBy = "E.ITEMNUM" + a_x;
			}

			if ("dtag".equals(sortColumn)) {
				orderBy = "E.DTAG" + a_x;
			}

			if ("ftag".equals(sortColumn)) {
				orderBy = "E.FTAG" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME" + a_x;
			}

			if ("allName".equals(sortColumn)) {
				orderBy = "E.ALLNAME" + a_x;
			}

			if ("bcompany".equals(sortColumn)) {
				orderBy = "E.BCOMPANY" + a_x;
			}

			if ("ccompany".equals(sortColumn)) {
				orderBy = "E.CCOMPANY" + a_x;
			}

			if ("dcompany".equals(sortColumn)) {
				orderBy = "E.DCOMPANY" + a_x;
			}

			if ("conCompany".equals(sortColumn)) {
				orderBy = "E.CONCOMPANY" + a_x;
			}

			if ("icompany".equals(sortColumn)) {
				orderBy = "E.ICOMPANY" + a_x;
			}

			if ("cmark".equals(sortColumn)) {
				orderBy = "E.CMARK" + a_x;
			}

			if ("pmark".equals(sortColumn)) {
				orderBy = "E.PMARK" + a_x;
			}

			if ("tpmark".equals(sortColumn)) {
				orderBy = "E.TPMARK" + a_x;
			}

			if ("conMark".equals(sortColumn)) {
				orderBy = "E.CONMARK" + a_x;
			}

			if ("mapNum".equals(sortColumn)) {
				orderBy = "E.MAPNUM" + a_x;
			}

			if ("conStart".equals(sortColumn)) {
				orderBy = "E.CONSTART" + a_x;
			}

			if ("conEnd".equals(sortColumn)) {
				orderBy = "E.CONEND" + a_x;
			}

			if ("totalLen".equals(sortColumn)) {
				orderBy = "E.TOTALLEN" + a_x;
			}

			if ("startDate".equals(sortColumn)) {
				orderBy = "E.STARTDATE" + a_x;
			}

			if ("endDate".equals(sortColumn)) {
				orderBy = "E.ENDDATE" + a_x;
			}

			if ("cost".equals(sortColumn)) {
				orderBy = "E.COST" + a_x;
			}

			if ("balance".equals(sortColumn)) {
				orderBy = "E.BALANCE" + a_x;
			}

			if ("pmannager".equals(sortColumn)) {
				orderBy = "E.PMANNAGER" + a_x;
			}

			if ("enginee".equals(sortColumn)) {
				orderBy = "E.ENGINEE" + a_x;
			}

			if ("owner".equals(sortColumn)) {
				orderBy = "E.OWNER" + a_x;
			}

			if ("planDate".equals(sortColumn)) {
				orderBy = "E.PLANDATE" + a_x;
			}

			if ("hintDay".equals(sortColumn)) {
				orderBy = "E.HINTDAY" + a_x;
			}

			if ("duty".equals(sortColumn)) {
				orderBy = "E.DUTY" + a_x;
			}

			if ("removeDate".equals(sortColumn)) {
				orderBy = "E.REMOVEDATE" + a_x;
			}

			if ("removeDuty".equals(sortColumn)) {
				orderBy = "E.REMOVEDUTY" + a_x;
			}

			if ("removeFile".equals(sortColumn)) {
				orderBy = "E.REMOVEFILE" + a_x;
			}

			if ("partProj".equals(sortColumn)) {
				orderBy = "E.PARTPROJ" + a_x;
			}

			if ("cnum".equals(sortColumn)) {
				orderBy = "E.CNUM" + a_x;
			}

			if ("concompany2".equals(sortColumn)) {
				orderBy = "E.CONCOMPANY2" + a_x;
			}

			if ("icompany2".equals(sortColumn)) {
				orderBy = "E.ICOMPANY2" + a_x;
			}

			if ("mileAge".equals(sortColumn)) {
				orderBy = "E.MILEAGE" + a_x;
			}

			if ("place".equals(sortColumn)) {
				orderBy = "E.PLACE" + a_x;
			}

			if ("place1".equals(sortColumn)) {
				orderBy = "E.PLACE1" + a_x;
			}

			if ("place2".equals(sortColumn)) {
				orderBy = "E.PLACE2" + a_x;
			}

			if ("place3".equals(sortColumn)) {
				orderBy = "E.PLACE3" + a_x;
			}

			if ("place4".equals(sortColumn)) {
				orderBy = "E.PLACE4" + a_x;
			}

			if ("setPlace".equals(sortColumn)) {
				orderBy = "E.SETPLACE" + a_x;
			}

			if ("setTemp".equals(sortColumn)) {
				orderBy = "E.SETTEMP" + a_x;
			}

			if ("bdNum".equals(sortColumn)) {
				orderBy = "E.BDNUM" + a_x;
			}

			if ("dtNum".equals(sortColumn)) {
				orderBy = "E.DTNUM" + a_x;
			}

			if ("pinfoUser2".equals(sortColumn)) {
				orderBy = "E.PINFO_USER2" + a_x;
			}

			if ("pinfoUser3".equals(sortColumn)) {
				orderBy = "E.PINFO_USER3" + a_x;
			}

			if ("pinfoUser4".equals(sortColumn)) {
				orderBy = "E.PINFO_USER4" + a_x;
			}

			if ("pinfoUser5".equals(sortColumn)) {
				orderBy = "E.PINFO_USER5" + a_x;
			}

			if ("pinfoUser6".equals(sortColumn)) {
				orderBy = "E.PINFO_USER6" + a_x;
			}

			if ("pinfoUser7".equals(sortColumn)) {
				orderBy = "E.PINFO_USER7" + a_x;
			}

			if ("pinfoUser8".equals(sortColumn)) {
				orderBy = "E.PINFO_USER8" + a_x;
			}

			if ("pinfoUser9".equals(sortColumn)) {
				orderBy = "E.PINFO_USER9" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("indexId", "INDEX_ID");
		addColumn("itemNum", "ITEMNUM");
		addColumn("dtag", "DTAG");
		addColumn("ftag", "FTAG");
		addColumn("name", "NAME");
		addColumn("allName", "ALLNAME");
		addColumn("bcompany", "BCOMPANY");
		addColumn("ccompany", "CCOMPANY");
		addColumn("dcompany", "DCOMPANY");
		addColumn("conCompany", "CONCOMPANY");
		addColumn("icompany", "ICOMPANY");
		addColumn("cmark", "CMARK");
		addColumn("pmark", "PMARK");
		addColumn("tpmark", "TPMARK");
		addColumn("conMark", "CONMARK");
		addColumn("mapNum", "MAPNUM");
		addColumn("conStart", "CONSTART");
		addColumn("conEnd", "CONEND");
		addColumn("totalLen", "TOTALLEN");
		addColumn("startDate", "STARTDATE");
		addColumn("endDate", "ENDDATE");
		addColumn("cost", "COST");
		addColumn("balance", "BALANCE");
		addColumn("pmannager", "PMANNAGER");
		addColumn("enginee", "ENGINEE");
		addColumn("owner", "OWNER");
		addColumn("planDate", "PLANDATE");
		addColumn("hintDay", "HINTDAY");
		addColumn("duty", "DUTY");
		addColumn("removeDate", "REMOVEDATE");
		addColumn("removeDuty", "REMOVEDUTY");
		addColumn("removeFile", "REMOVEFILE");
		addColumn("partProj", "PARTPROJ");
		addColumn("cnum", "CNUM");
		addColumn("concompany2", "CONCOMPANY2");
		addColumn("icompany2", "ICOMPANY2");
		addColumn("mileAge", "MILEAGE");
		addColumn("place", "PLACE");
		addColumn("place1", "PLACE1");
		addColumn("place2", "PLACE2");
		addColumn("place3", "PLACE3");
		addColumn("place4", "PLACE4");
		addColumn("setPlace", "SETPLACE");
		addColumn("setTemp", "SETTEMP");
		addColumn("bdNum", "BDNUM");
		addColumn("dtNum", "DTNUM");
		addColumn("pinfoUser2", "PINFO_USER2");
		addColumn("pinfoUser3", "PINFO_USER3");
		addColumn("pinfoUser4", "PINFO_USER4");
		addColumn("pinfoUser5", "PINFO_USER5");
		addColumn("pinfoUser6", "PINFO_USER6");
		addColumn("pinfoUser7", "PINFO_USER7");
		addColumn("pinfoUser8", "PINFO_USER8");
		addColumn("pinfoUser9", "PINFO_USER9");
	}

}