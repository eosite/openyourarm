package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class PfileQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected Integer efileNum;
	protected Integer efileNumGreaterThanOrEqual;
	protected Integer efileNumLessThanOrEqual;
	protected List<Integer> efileNums;
	protected String listNum;
	protected String listNumLike;
	protected List<String> listNums;
	protected String listId;
	protected String listIdLike;
	protected List<String> listIds;
	protected Integer pid;
	protected Integer pidGreaterThanOrEqual;
	protected Integer pidLessThanOrEqual;
	protected List<Integer> pids;
	protected Integer projId;
	protected Integer projIdGreaterThanOrEqual;
	protected Integer projIdLessThanOrEqual;
	protected List<Integer> projIds;
	protected Integer dwid;
	protected Integer dwidGreaterThanOrEqual;
	protected Integer dwidLessThanOrEqual;
	protected List<Integer> dwids;
	protected Integer fbid;
	protected Integer fbidGreaterThanOrEqual;
	protected Integer fbidLessThanOrEqual;
	protected List<Integer> fbids;
	protected Integer fxid;
	protected Integer fxidGreaterThanOrEqual;
	protected Integer fxidLessThanOrEqual;
	protected List<Integer> fxids;
	protected String jid;
	protected String jidLike;
	protected List<String> jids;
	protected String flid;
	protected String flidLike;
	protected List<String> flids;
	protected String topNode;
	protected String topNodeLike;
	protected List<String> topNodes;
	protected String topNodeM;
	protected String topNodeMLike;
	protected List<String> topNodeMs;
	protected String vid;
	protected String vidLike;
	protected List<String> vids;
	protected String oldVid;
	protected String oldVidLike;
	protected List<String> oldVids;
	protected String visFlag;
	protected String visFlagLike;
	protected List<String> visFlags;
	protected Integer listNo;
	protected Integer listNoGreaterThanOrEqual;
	protected Integer listNoLessThanOrEqual;
	protected List<Integer> listNos;
	protected String filingFlag;
	protected String filingFlagLike;
	protected List<String> filingFlags;
	protected String saveFlag;
	protected String saveFlagLike;
	protected List<String> saveFlags;
	protected String openFlag;
	protected String openFlagLike;
	protected List<String> openFlags;
	protected String checkupFlag;
	protected String checkupFlagLike;
	protected List<String> checkupFlags;
	protected String finishFlag;
	protected String finishFlagLike;
	protected List<String> finishFlags;
	protected String fromID;
	protected String fromIDLike;
	protected List<String> fromIDs;
	protected String tname;
	protected String tnameLike;
	protected List<String> tnames;
	protected String duty;
	protected String dutyLike;
	protected List<String> dutys;
	protected String tagnum;
	protected String tagnumLike;
	protected List<String> tagnums;
	protected String fileNum;
	protected String fileNumLike;
	protected List<String> fileNums;
	protected String thematic;
	protected String thematicLike;
	protected List<String> thematics;
	protected Date ctimeGreaterThanOrEqual;
	protected Date ctimeLessThanOrEqual;
	protected String pageno;
	protected String pagenoLike;
	protected List<String> pagenos;
	protected String level;
	protected String levelLike;
	protected List<String> levels;
	protected Integer page;
	protected Integer pageGreaterThanOrEqual;
	protected Integer pageLessThanOrEqual;
	protected List<Integer> pages;
	protected String fileType;
	protected String fileTypeLike;
	protected List<String> fileTypes;
	protected String fontsNum;
	protected String fontsNumLike;
	protected List<String> fontsNums;
	protected String saveTime;
	protected String saveTimeLike;
	protected List<String> saveTimes;
	protected String company;
	protected String companyLike;
	protected List<String> companys;
	protected Integer year;
	protected Integer yearGreaterThanOrEqual;
	protected Integer yearLessThanOrEqual;
	protected List<Integer> years;
	protected String fileAtt;
	protected String fileAttLike;
	protected List<String> fileAtts;
	protected String annotations;
	protected String annotationsLike;
	protected List<String> annotationss;
	protected String carrierType;
	protected String carrierTypeLike;
	protected List<String> carrierTypes;
	protected String summary;
	protected String summaryLike;
	protected List<String> summarys;
	protected String ptext;
	protected String ptextLike;
	protected List<String> ptexts;
	protected String carrieru;
	protected String carrieruLike;
	protected List<String> carrierus;
	protected String glideNum;
	protected String glideNumLike;
	protected List<String> glideNums;
	protected String efile;
	protected String efileLike;
	protected List<String> efiles;
	protected Date ftimeGreaterThanOrEqual;
	protected Date ftimeLessThanOrEqual;
	protected String totalNum;
	protected String totalNumLike;
	protected List<String> totalNums;
	protected String inputMan;
	protected String inputManLike;
	protected List<String> inputMans;
	protected Date etimeGreaterThanOrEqual;
	protected Date etimeLessThanOrEqual;
	protected String dotNum;
	protected String dotNumLike;
	protected List<String> dotNums;
	protected String picNum;
	protected String picNumLike;
	protected List<String> picNums;
	protected String recNum;
	protected String recNumLike;
	protected List<String> recNums;
	protected Integer total;
	protected Integer totalGreaterThanOrEqual;
	protected Integer totalLessThanOrEqual;
	protected List<Integer> totals;
	protected String pageType;
	protected String pageTypeLike;
	protected List<String> pageTypes;
	protected String isCom;
	protected String isComLike;
	protected List<String> isComs;
	protected String isLocate;
	protected String isLocateLike;
	protected List<String> isLocates;
	protected String wcompany;
	protected String wcompanyLike;
	protected List<String> wcompanys;
	protected String wcompanyID;
	protected String wcompanyIDLike;
	protected List<String> wcompanyIDs;
	protected String sendFlag;
	protected String sendFlagLike;
	protected List<String> sendFlags;
	protected String lcontent;
	protected String lcontentLike;
	protected List<String> lcontents;
	protected String lcompany;
	protected String lcompanyLike;
	protected List<String> lcompanys;
	protected String lman;
	protected String lmanLike;
	protected List<String> lmans;
	protected String llen;
	protected String llenLike;
	protected List<String> llens;
	protected Date ldateGreaterThanOrEqual;
	protected Date ldateLessThanOrEqual;
	protected String jconten;
	protected String jcontenLike;
	protected List<String> jcontens;
	protected String jplace;
	protected String jplaceLike;
	protected List<String> jplaces;
	protected String jman;
	protected String jmanLike;
	protected List<String> jmans;
	protected String jback;
	protected String jbackLike;
	protected List<String> jbacks;
	protected String jactor;
	protected String jactorLike;
	protected List<String> jactors;
	protected String jnum;
	protected String jnumLike;
	protected List<String> jnums;
	protected String jbnum;
	protected String jbnumLike;
	protected List<String> jbnums;
	protected String tnum;
	protected String tnumLike;
	protected List<String> tnums;
	protected String tzoom;
	protected String tzoomLike;
	protected List<String> tzooms;
	protected String tflag;
	protected String tflagLike;
	protected List<String> tflags;
	protected String tdesigner;
	protected String tdesignerLike;
	protected List<String> tdesigners;
	protected String tviewer;
	protected String tviewerLike;
	protected List<String> tviewers;
	protected String tassessor;
	protected String tassessorLike;
	protected List<String> tassessors;
	protected String tvision;
	protected String tvisionLike;
	protected List<String> tvisions;
	protected Integer clistNo;
	protected Integer clistNoGreaterThanOrEqual;
	protected Integer clistNoLessThanOrEqual;
	protected List<Integer> clistNos;
	protected String cpageNo;
	protected String cpageNoLike;
	protected List<String> cpageNos;
	protected String vnum;
	protected String vnumLike;
	protected List<String> vnums;
	protected String cvnum;
	protected String cvnumLike;
	protected List<String> cvnums;
	protected String treedListStr;
	protected String treedListStrLike;
	protected List<String> treedListStrs;
	protected Date ctimeEndGreaterThanOrEqual;
	protected Date ctimeEndLessThanOrEqual;
	protected Integer projIndex;
	protected Integer projIndexGreaterThanOrEqual;
	protected Integer projIndexLessThanOrEqual;
	protected List<Integer> projIndexs;
	protected Integer treeParent;
	protected Integer treeParentGreaterThanOrEqual;
	protected Integer treeParentLessThanOrEqual;
	protected List<Integer> treeParents;
	protected Integer treeList;
	protected Integer treeListGreaterThanOrEqual;
	protected Integer treeListLessThanOrEqual;
	protected List<Integer> treeLists;

	public PfileQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public Integer getEfileNum() {
		return efileNum;
	}

	public Integer getEfileNumGreaterThanOrEqual() {
		return efileNumGreaterThanOrEqual;
	}

	public Integer getEfileNumLessThanOrEqual() {
		return efileNumLessThanOrEqual;
	}

	public List<Integer> getEfileNums() {
		return efileNums;
	}

	public String getListNum() {
		return listNum;
	}

	public String getListNumLike() {
		if (listNumLike != null && listNumLike.trim().length() > 0) {
			if (!listNumLike.startsWith("%")) {
				listNumLike = "%" + listNumLike;
			}
			if (!listNumLike.endsWith("%")) {
				listNumLike = listNumLike + "%";
			}
		}
		return listNumLike;
	}

	public List<String> getListNums() {
		return listNums;
	}

	public String getListId() {
		return listId;
	}

	public String getListIdLike() {
		if (listIdLike != null && listIdLike.trim().length() > 0) {
			if (!listIdLike.startsWith("%")) {
				listIdLike = "%" + listIdLike;
			}
			if (!listIdLike.endsWith("%")) {
				listIdLike = listIdLike + "%";
			}
		}
		return listIdLike;
	}

	public List<String> getListIds() {
		return listIds;
	}

	public Integer getPid() {
		return pid;
	}

	public Integer getPidGreaterThanOrEqual() {
		return pidGreaterThanOrEqual;
	}

	public Integer getPidLessThanOrEqual() {
		return pidLessThanOrEqual;
	}

	public List<Integer> getPids() {
		return pids;
	}

	public Integer getProjId() {
		return projId;
	}

	public Integer getProjIdGreaterThanOrEqual() {
		return projIdGreaterThanOrEqual;
	}

	public Integer getProjIdLessThanOrEqual() {
		return projIdLessThanOrEqual;
	}

	public List<Integer> getProjIds() {
		return projIds;
	}

	public Integer getDwid() {
		return dwid;
	}

	public Integer getDwidGreaterThanOrEqual() {
		return dwidGreaterThanOrEqual;
	}

	public Integer getDwidLessThanOrEqual() {
		return dwidLessThanOrEqual;
	}

	public List<Integer> getDwids() {
		return dwids;
	}

	public Integer getFbid() {
		return fbid;
	}

	public Integer getFbidGreaterThanOrEqual() {
		return fbidGreaterThanOrEqual;
	}

	public Integer getFbidLessThanOrEqual() {
		return fbidLessThanOrEqual;
	}

	public List<Integer> getFbids() {
		return fbids;
	}

	public Integer getFxid() {
		return fxid;
	}

	public Integer getFxidGreaterThanOrEqual() {
		return fxidGreaterThanOrEqual;
	}

	public Integer getFxidLessThanOrEqual() {
		return fxidLessThanOrEqual;
	}

	public List<Integer> getFxids() {
		return fxids;
	}

	public String getJid() {
		return jid;
	}

	public String getJidLike() {
		if (jidLike != null && jidLike.trim().length() > 0) {
			if (!jidLike.startsWith("%")) {
				jidLike = "%" + jidLike;
			}
			if (!jidLike.endsWith("%")) {
				jidLike = jidLike + "%";
			}
		}
		return jidLike;
	}

	public List<String> getJids() {
		return jids;
	}

	public String getFlid() {
		return flid;
	}

	public String getFlidLike() {
		if (flidLike != null && flidLike.trim().length() > 0) {
			if (!flidLike.startsWith("%")) {
				flidLike = "%" + flidLike;
			}
			if (!flidLike.endsWith("%")) {
				flidLike = flidLike + "%";
			}
		}
		return flidLike;
	}

	public List<String> getFlids() {
		return flids;
	}

	public String getTopNode() {
		return topNode;
	}

	public String getTopNodeLike() {
		if (topNodeLike != null && topNodeLike.trim().length() > 0) {
			if (!topNodeLike.startsWith("%")) {
				topNodeLike = "%" + topNodeLike;
			}
			if (!topNodeLike.endsWith("%")) {
				topNodeLike = topNodeLike + "%";
			}
		}
		return topNodeLike;
	}

	public List<String> getTopNodes() {
		return topNodes;
	}

	public String getTopNodeM() {
		return topNodeM;
	}

	public String getTopNodeMLike() {
		if (topNodeMLike != null && topNodeMLike.trim().length() > 0) {
			if (!topNodeMLike.startsWith("%")) {
				topNodeMLike = "%" + topNodeMLike;
			}
			if (!topNodeMLike.endsWith("%")) {
				topNodeMLike = topNodeMLike + "%";
			}
		}
		return topNodeMLike;
	}

	public List<String> getTopNodeMs() {
		return topNodeMs;
	}

	public String getVid() {
		return vid;
	}

	public String getVidLike() {
		if (vidLike != null && vidLike.trim().length() > 0) {
			if (!vidLike.startsWith("%")) {
				vidLike = "%" + vidLike;
			}
			if (!vidLike.endsWith("%")) {
				vidLike = vidLike + "%";
			}
		}
		return vidLike;
	}

	public List<String> getVids() {
		return vids;
	}

	public String getOldVid() {
		return oldVid;
	}

	public String getOldVidLike() {
		if (oldVidLike != null && oldVidLike.trim().length() > 0) {
			if (!oldVidLike.startsWith("%")) {
				oldVidLike = "%" + oldVidLike;
			}
			if (!oldVidLike.endsWith("%")) {
				oldVidLike = oldVidLike + "%";
			}
		}
		return oldVidLike;
	}

	public List<String> getOldVids() {
		return oldVids;
	}

	public String getVisFlag() {
		return visFlag;
	}

	public String getVisFlagLike() {
		if (visFlagLike != null && visFlagLike.trim().length() > 0) {
			if (!visFlagLike.startsWith("%")) {
				visFlagLike = "%" + visFlagLike;
			}
			if (!visFlagLike.endsWith("%")) {
				visFlagLike = visFlagLike + "%";
			}
		}
		return visFlagLike;
	}

	public List<String> getVisFlags() {
		return visFlags;
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

	public String getFilingFlag() {
		return filingFlag;
	}

	public String getFilingFlagLike() {
		if (filingFlagLike != null && filingFlagLike.trim().length() > 0) {
			if (!filingFlagLike.startsWith("%")) {
				filingFlagLike = "%" + filingFlagLike;
			}
			if (!filingFlagLike.endsWith("%")) {
				filingFlagLike = filingFlagLike + "%";
			}
		}
		return filingFlagLike;
	}

	public List<String> getFilingFlags() {
		return filingFlags;
	}

	public String getSaveFlag() {
		return saveFlag;
	}

	public String getSaveFlagLike() {
		if (saveFlagLike != null && saveFlagLike.trim().length() > 0) {
			if (!saveFlagLike.startsWith("%")) {
				saveFlagLike = "%" + saveFlagLike;
			}
			if (!saveFlagLike.endsWith("%")) {
				saveFlagLike = saveFlagLike + "%";
			}
		}
		return saveFlagLike;
	}

	public List<String> getSaveFlags() {
		return saveFlags;
	}

	public String getOpenFlag() {
		return openFlag;
	}

	public String getOpenFlagLike() {
		if (openFlagLike != null && openFlagLike.trim().length() > 0) {
			if (!openFlagLike.startsWith("%")) {
				openFlagLike = "%" + openFlagLike;
			}
			if (!openFlagLike.endsWith("%")) {
				openFlagLike = openFlagLike + "%";
			}
		}
		return openFlagLike;
	}

	public List<String> getOpenFlags() {
		return openFlags;
	}

	public String getCheckupFlag() {
		return checkupFlag;
	}

	public String getCheckupFlagLike() {
		if (checkupFlagLike != null && checkupFlagLike.trim().length() > 0) {
			if (!checkupFlagLike.startsWith("%")) {
				checkupFlagLike = "%" + checkupFlagLike;
			}
			if (!checkupFlagLike.endsWith("%")) {
				checkupFlagLike = checkupFlagLike + "%";
			}
		}
		return checkupFlagLike;
	}

	public List<String> getCheckupFlags() {
		return checkupFlags;
	}

	public String getFinishFlag() {
		return finishFlag;
	}

	public String getFinishFlagLike() {
		if (finishFlagLike != null && finishFlagLike.trim().length() > 0) {
			if (!finishFlagLike.startsWith("%")) {
				finishFlagLike = "%" + finishFlagLike;
			}
			if (!finishFlagLike.endsWith("%")) {
				finishFlagLike = finishFlagLike + "%";
			}
		}
		return finishFlagLike;
	}

	public List<String> getFinishFlags() {
		return finishFlags;
	}

	public String getFromID() {
		return fromID;
	}

	public String getFromIDLike() {
		if (fromIDLike != null && fromIDLike.trim().length() > 0) {
			if (!fromIDLike.startsWith("%")) {
				fromIDLike = "%" + fromIDLike;
			}
			if (!fromIDLike.endsWith("%")) {
				fromIDLike = fromIDLike + "%";
			}
		}
		return fromIDLike;
	}

	public List<String> getFromIDs() {
		return fromIDs;
	}

	public String getTname() {
		return tname;
	}

	public String getTnameLike() {
		if (tnameLike != null && tnameLike.trim().length() > 0) {
			if (!tnameLike.startsWith("%")) {
				tnameLike = "%" + tnameLike;
			}
			if (!tnameLike.endsWith("%")) {
				tnameLike = tnameLike + "%";
			}
		}
		return tnameLike;
	}

	public List<String> getTnames() {
		return tnames;
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

	public String getTagnum() {
		return tagnum;
	}

	public String getTagnumLike() {
		if (tagnumLike != null && tagnumLike.trim().length() > 0) {
			if (!tagnumLike.startsWith("%")) {
				tagnumLike = "%" + tagnumLike;
			}
			if (!tagnumLike.endsWith("%")) {
				tagnumLike = tagnumLike + "%";
			}
		}
		return tagnumLike;
	}

	public List<String> getTagnums() {
		return tagnums;
	}

	public String getFileNum() {
		return fileNum;
	}

	public String getFileNumLike() {
		if (fileNumLike != null && fileNumLike.trim().length() > 0) {
			if (!fileNumLike.startsWith("%")) {
				fileNumLike = "%" + fileNumLike;
			}
			if (!fileNumLike.endsWith("%")) {
				fileNumLike = fileNumLike + "%";
			}
		}
		return fileNumLike;
	}

	public List<String> getFileNums() {
		return fileNums;
	}

	public String getThematic() {
		return thematic;
	}

	public String getThematicLike() {
		if (thematicLike != null && thematicLike.trim().length() > 0) {
			if (!thematicLike.startsWith("%")) {
				thematicLike = "%" + thematicLike;
			}
			if (!thematicLike.endsWith("%")) {
				thematicLike = thematicLike + "%";
			}
		}
		return thematicLike;
	}

	public List<String> getThematics() {
		return thematics;
	}

	public Date getCtimeGreaterThanOrEqual() {
		return ctimeGreaterThanOrEqual;
	}

	public Date getCtimeLessThanOrEqual() {
		return ctimeLessThanOrEqual;
	}

	public String getPageno() {
		return pageno;
	}

	public String getPagenoLike() {
		if (pagenoLike != null && pagenoLike.trim().length() > 0) {
			if (!pagenoLike.startsWith("%")) {
				pagenoLike = "%" + pagenoLike;
			}
			if (!pagenoLike.endsWith("%")) {
				pagenoLike = pagenoLike + "%";
			}
		}
		return pagenoLike;
	}

	public List<String> getPagenos() {
		return pagenos;
	}

	public String getLevel() {
		return level;
	}

	public String getLevelLike() {
		if (levelLike != null && levelLike.trim().length() > 0) {
			if (!levelLike.startsWith("%")) {
				levelLike = "%" + levelLike;
			}
			if (!levelLike.endsWith("%")) {
				levelLike = levelLike + "%";
			}
		}
		return levelLike;
	}

	public List<String> getLevels() {
		return levels;
	}

	public Integer getPage() {
		return page;
	}

	public Integer getPageGreaterThanOrEqual() {
		return pageGreaterThanOrEqual;
	}

	public Integer getPageLessThanOrEqual() {
		return pageLessThanOrEqual;
	}

	public List<Integer> getPages() {
		return pages;
	}

	public String getFileType() {
		return fileType;
	}

	public String getFileTypeLike() {
		if (fileTypeLike != null && fileTypeLike.trim().length() > 0) {
			if (!fileTypeLike.startsWith("%")) {
				fileTypeLike = "%" + fileTypeLike;
			}
			if (!fileTypeLike.endsWith("%")) {
				fileTypeLike = fileTypeLike + "%";
			}
		}
		return fileTypeLike;
	}

	public List<String> getFileTypes() {
		return fileTypes;
	}

	public String getFontsNum() {
		return fontsNum;
	}

	public String getFontsNumLike() {
		if (fontsNumLike != null && fontsNumLike.trim().length() > 0) {
			if (!fontsNumLike.startsWith("%")) {
				fontsNumLike = "%" + fontsNumLike;
			}
			if (!fontsNumLike.endsWith("%")) {
				fontsNumLike = fontsNumLike + "%";
			}
		}
		return fontsNumLike;
	}

	public List<String> getFontsNums() {
		return fontsNums;
	}

	public String getSaveTime() {
		return saveTime;
	}

	public String getSaveTimeLike() {
		if (saveTimeLike != null && saveTimeLike.trim().length() > 0) {
			if (!saveTimeLike.startsWith("%")) {
				saveTimeLike = "%" + saveTimeLike;
			}
			if (!saveTimeLike.endsWith("%")) {
				saveTimeLike = saveTimeLike + "%";
			}
		}
		return saveTimeLike;
	}

	public List<String> getSaveTimes() {
		return saveTimes;
	}

	public String getCompany() {
		return company;
	}

	public String getCompanyLike() {
		if (companyLike != null && companyLike.trim().length() > 0) {
			if (!companyLike.startsWith("%")) {
				companyLike = "%" + companyLike;
			}
			if (!companyLike.endsWith("%")) {
				companyLike = companyLike + "%";
			}
		}
		return companyLike;
	}

	public List<String> getCompanys() {
		return companys;
	}

	public Integer getYear() {
		return year;
	}

	public Integer getYearGreaterThanOrEqual() {
		return yearGreaterThanOrEqual;
	}

	public Integer getYearLessThanOrEqual() {
		return yearLessThanOrEqual;
	}

	public List<Integer> getYears() {
		return years;
	}

	public String getFileAtt() {
		return fileAtt;
	}

	public String getFileAttLike() {
		if (fileAttLike != null && fileAttLike.trim().length() > 0) {
			if (!fileAttLike.startsWith("%")) {
				fileAttLike = "%" + fileAttLike;
			}
			if (!fileAttLike.endsWith("%")) {
				fileAttLike = fileAttLike + "%";
			}
		}
		return fileAttLike;
	}

	public List<String> getFileAtts() {
		return fileAtts;
	}

	public String getAnnotations() {
		return annotations;
	}

	public String getAnnotationsLike() {
		if (annotationsLike != null && annotationsLike.trim().length() > 0) {
			if (!annotationsLike.startsWith("%")) {
				annotationsLike = "%" + annotationsLike;
			}
			if (!annotationsLike.endsWith("%")) {
				annotationsLike = annotationsLike + "%";
			}
		}
		return annotationsLike;
	}

	public List<String> getAnnotationss() {
		return annotationss;
	}

	public String getCarrierType() {
		return carrierType;
	}

	public String getCarrierTypeLike() {
		if (carrierTypeLike != null && carrierTypeLike.trim().length() > 0) {
			if (!carrierTypeLike.startsWith("%")) {
				carrierTypeLike = "%" + carrierTypeLike;
			}
			if (!carrierTypeLike.endsWith("%")) {
				carrierTypeLike = carrierTypeLike + "%";
			}
		}
		return carrierTypeLike;
	}

	public List<String> getCarrierTypes() {
		return carrierTypes;
	}

	public String getSummary() {
		return summary;
	}

	public String getSummaryLike() {
		if (summaryLike != null && summaryLike.trim().length() > 0) {
			if (!summaryLike.startsWith("%")) {
				summaryLike = "%" + summaryLike;
			}
			if (!summaryLike.endsWith("%")) {
				summaryLike = summaryLike + "%";
			}
		}
		return summaryLike;
	}

	public List<String> getSummarys() {
		return summarys;
	}

	public String getPtext() {
		return ptext;
	}

	public String getPtextLike() {
		if (ptextLike != null && ptextLike.trim().length() > 0) {
			if (!ptextLike.startsWith("%")) {
				ptextLike = "%" + ptextLike;
			}
			if (!ptextLike.endsWith("%")) {
				ptextLike = ptextLike + "%";
			}
		}
		return ptextLike;
	}

	public List<String> getPtexts() {
		return ptexts;
	}

	public String getCarrieru() {
		return carrieru;
	}

	public String getCarrieruLike() {
		if (carrieruLike != null && carrieruLike.trim().length() > 0) {
			if (!carrieruLike.startsWith("%")) {
				carrieruLike = "%" + carrieruLike;
			}
			if (!carrieruLike.endsWith("%")) {
				carrieruLike = carrieruLike + "%";
			}
		}
		return carrieruLike;
	}

	public List<String> getCarrierus() {
		return carrierus;
	}

	public String getGlideNum() {
		return glideNum;
	}

	public String getGlideNumLike() {
		if (glideNumLike != null && glideNumLike.trim().length() > 0) {
			if (!glideNumLike.startsWith("%")) {
				glideNumLike = "%" + glideNumLike;
			}
			if (!glideNumLike.endsWith("%")) {
				glideNumLike = glideNumLike + "%";
			}
		}
		return glideNumLike;
	}

	public List<String> getGlideNums() {
		return glideNums;
	}

	public String getEfile() {
		return efile;
	}

	public String getEfileLike() {
		if (efileLike != null && efileLike.trim().length() > 0) {
			if (!efileLike.startsWith("%")) {
				efileLike = "%" + efileLike;
			}
			if (!efileLike.endsWith("%")) {
				efileLike = efileLike + "%";
			}
		}
		return efileLike;
	}

	public List<String> getEfiles() {
		return efiles;
	}

	public Date getFtimeGreaterThanOrEqual() {
		return ftimeGreaterThanOrEqual;
	}

	public Date getFtimeLessThanOrEqual() {
		return ftimeLessThanOrEqual;
	}

	public String getTotalNum() {
		return totalNum;
	}

	public String getTotalNumLike() {
		if (totalNumLike != null && totalNumLike.trim().length() > 0) {
			if (!totalNumLike.startsWith("%")) {
				totalNumLike = "%" + totalNumLike;
			}
			if (!totalNumLike.endsWith("%")) {
				totalNumLike = totalNumLike + "%";
			}
		}
		return totalNumLike;
	}

	public List<String> getTotalNums() {
		return totalNums;
	}

	public String getInputMan() {
		return inputMan;
	}

	public String getInputManLike() {
		if (inputManLike != null && inputManLike.trim().length() > 0) {
			if (!inputManLike.startsWith("%")) {
				inputManLike = "%" + inputManLike;
			}
			if (!inputManLike.endsWith("%")) {
				inputManLike = inputManLike + "%";
			}
		}
		return inputManLike;
	}

	public List<String> getInputMans() {
		return inputMans;
	}

	public Date getEtimeGreaterThanOrEqual() {
		return etimeGreaterThanOrEqual;
	}

	public Date getEtimeLessThanOrEqual() {
		return etimeLessThanOrEqual;
	}

	public String getDotNum() {
		return dotNum;
	}

	public String getDotNumLike() {
		if (dotNumLike != null && dotNumLike.trim().length() > 0) {
			if (!dotNumLike.startsWith("%")) {
				dotNumLike = "%" + dotNumLike;
			}
			if (!dotNumLike.endsWith("%")) {
				dotNumLike = dotNumLike + "%";
			}
		}
		return dotNumLike;
	}

	public List<String> getDotNums() {
		return dotNums;
	}

	public String getPicNum() {
		return picNum;
	}

	public String getPicNumLike() {
		if (picNumLike != null && picNumLike.trim().length() > 0) {
			if (!picNumLike.startsWith("%")) {
				picNumLike = "%" + picNumLike;
			}
			if (!picNumLike.endsWith("%")) {
				picNumLike = picNumLike + "%";
			}
		}
		return picNumLike;
	}

	public List<String> getPicNums() {
		return picNums;
	}

	public String getRecNum() {
		return recNum;
	}

	public String getRecNumLike() {
		if (recNumLike != null && recNumLike.trim().length() > 0) {
			if (!recNumLike.startsWith("%")) {
				recNumLike = "%" + recNumLike;
			}
			if (!recNumLike.endsWith("%")) {
				recNumLike = recNumLike + "%";
			}
		}
		return recNumLike;
	}

	public List<String> getRecNums() {
		return recNums;
	}

	public Integer getTotal() {
		return total;
	}

	public Integer getTotalGreaterThanOrEqual() {
		return totalGreaterThanOrEqual;
	}

	public Integer getTotalLessThanOrEqual() {
		return totalLessThanOrEqual;
	}

	public List<Integer> getTotals() {
		return totals;
	}

	public String getPageType() {
		return pageType;
	}

	public String getPageTypeLike() {
		if (pageTypeLike != null && pageTypeLike.trim().length() > 0) {
			if (!pageTypeLike.startsWith("%")) {
				pageTypeLike = "%" + pageTypeLike;
			}
			if (!pageTypeLike.endsWith("%")) {
				pageTypeLike = pageTypeLike + "%";
			}
		}
		return pageTypeLike;
	}

	public List<String> getPageTypes() {
		return pageTypes;
	}

	public String getIsCom() {
		return isCom;
	}

	public String getIsComLike() {
		if (isComLike != null && isComLike.trim().length() > 0) {
			if (!isComLike.startsWith("%")) {
				isComLike = "%" + isComLike;
			}
			if (!isComLike.endsWith("%")) {
				isComLike = isComLike + "%";
			}
		}
		return isComLike;
	}

	public List<String> getIsComs() {
		return isComs;
	}

	public String getIsLocate() {
		return isLocate;
	}

	public String getIsLocateLike() {
		if (isLocateLike != null && isLocateLike.trim().length() > 0) {
			if (!isLocateLike.startsWith("%")) {
				isLocateLike = "%" + isLocateLike;
			}
			if (!isLocateLike.endsWith("%")) {
				isLocateLike = isLocateLike + "%";
			}
		}
		return isLocateLike;
	}

	public List<String> getIsLocates() {
		return isLocates;
	}

	public String getWcompany() {
		return wcompany;
	}

	public String getWcompanyLike() {
		if (wcompanyLike != null && wcompanyLike.trim().length() > 0) {
			if (!wcompanyLike.startsWith("%")) {
				wcompanyLike = "%" + wcompanyLike;
			}
			if (!wcompanyLike.endsWith("%")) {
				wcompanyLike = wcompanyLike + "%";
			}
		}
		return wcompanyLike;
	}

	public List<String> getWcompanys() {
		return wcompanys;
	}

	public String getWcompanyID() {
		return wcompanyID;
	}

	public String getWcompanyIDLike() {
		if (wcompanyIDLike != null && wcompanyIDLike.trim().length() > 0) {
			if (!wcompanyIDLike.startsWith("%")) {
				wcompanyIDLike = "%" + wcompanyIDLike;
			}
			if (!wcompanyIDLike.endsWith("%")) {
				wcompanyIDLike = wcompanyIDLike + "%";
			}
		}
		return wcompanyIDLike;
	}

	public List<String> getWcompanyIDs() {
		return wcompanyIDs;
	}

	public String getSendFlag() {
		return sendFlag;
	}

	public String getSendFlagLike() {
		if (sendFlagLike != null && sendFlagLike.trim().length() > 0) {
			if (!sendFlagLike.startsWith("%")) {
				sendFlagLike = "%" + sendFlagLike;
			}
			if (!sendFlagLike.endsWith("%")) {
				sendFlagLike = sendFlagLike + "%";
			}
		}
		return sendFlagLike;
	}

	public List<String> getSendFlags() {
		return sendFlags;
	}

	public String getLcontent() {
		return lcontent;
	}

	public String getLcontentLike() {
		if (lcontentLike != null && lcontentLike.trim().length() > 0) {
			if (!lcontentLike.startsWith("%")) {
				lcontentLike = "%" + lcontentLike;
			}
			if (!lcontentLike.endsWith("%")) {
				lcontentLike = lcontentLike + "%";
			}
		}
		return lcontentLike;
	}

	public List<String> getLcontents() {
		return lcontents;
	}

	public String getLcompany() {
		return lcompany;
	}

	public String getLcompanyLike() {
		if (lcompanyLike != null && lcompanyLike.trim().length() > 0) {
			if (!lcompanyLike.startsWith("%")) {
				lcompanyLike = "%" + lcompanyLike;
			}
			if (!lcompanyLike.endsWith("%")) {
				lcompanyLike = lcompanyLike + "%";
			}
		}
		return lcompanyLike;
	}

	public List<String> getLcompanys() {
		return lcompanys;
	}

	public String getLman() {
		return lman;
	}

	public String getLmanLike() {
		if (lmanLike != null && lmanLike.trim().length() > 0) {
			if (!lmanLike.startsWith("%")) {
				lmanLike = "%" + lmanLike;
			}
			if (!lmanLike.endsWith("%")) {
				lmanLike = lmanLike + "%";
			}
		}
		return lmanLike;
	}

	public List<String> getLmans() {
		return lmans;
	}

	public String getLlen() {
		return llen;
	}

	public String getLlenLike() {
		if (llenLike != null && llenLike.trim().length() > 0) {
			if (!llenLike.startsWith("%")) {
				llenLike = "%" + llenLike;
			}
			if (!llenLike.endsWith("%")) {
				llenLike = llenLike + "%";
			}
		}
		return llenLike;
	}

	public List<String> getLlens() {
		return llens;
	}

	public Date getLdateGreaterThanOrEqual() {
		return ldateGreaterThanOrEqual;
	}

	public Date getLdateLessThanOrEqual() {
		return ldateLessThanOrEqual;
	}

	public String getJconten() {
		return jconten;
	}

	public String getJcontenLike() {
		if (jcontenLike != null && jcontenLike.trim().length() > 0) {
			if (!jcontenLike.startsWith("%")) {
				jcontenLike = "%" + jcontenLike;
			}
			if (!jcontenLike.endsWith("%")) {
				jcontenLike = jcontenLike + "%";
			}
		}
		return jcontenLike;
	}

	public List<String> getJcontens() {
		return jcontens;
	}

	public String getJplace() {
		return jplace;
	}

	public String getJplaceLike() {
		if (jplaceLike != null && jplaceLike.trim().length() > 0) {
			if (!jplaceLike.startsWith("%")) {
				jplaceLike = "%" + jplaceLike;
			}
			if (!jplaceLike.endsWith("%")) {
				jplaceLike = jplaceLike + "%";
			}
		}
		return jplaceLike;
	}

	public List<String> getJplaces() {
		return jplaces;
	}

	public String getJman() {
		return jman;
	}

	public String getJmanLike() {
		if (jmanLike != null && jmanLike.trim().length() > 0) {
			if (!jmanLike.startsWith("%")) {
				jmanLike = "%" + jmanLike;
			}
			if (!jmanLike.endsWith("%")) {
				jmanLike = jmanLike + "%";
			}
		}
		return jmanLike;
	}

	public List<String> getJmans() {
		return jmans;
	}

	public String getJback() {
		return jback;
	}

	public String getJbackLike() {
		if (jbackLike != null && jbackLike.trim().length() > 0) {
			if (!jbackLike.startsWith("%")) {
				jbackLike = "%" + jbackLike;
			}
			if (!jbackLike.endsWith("%")) {
				jbackLike = jbackLike + "%";
			}
		}
		return jbackLike;
	}

	public List<String> getJbacks() {
		return jbacks;
	}

	public String getJactor() {
		return jactor;
	}

	public String getJactorLike() {
		if (jactorLike != null && jactorLike.trim().length() > 0) {
			if (!jactorLike.startsWith("%")) {
				jactorLike = "%" + jactorLike;
			}
			if (!jactorLike.endsWith("%")) {
				jactorLike = jactorLike + "%";
			}
		}
		return jactorLike;
	}

	public List<String> getJactors() {
		return jactors;
	}

	public String getJnum() {
		return jnum;
	}

	public String getJnumLike() {
		if (jnumLike != null && jnumLike.trim().length() > 0) {
			if (!jnumLike.startsWith("%")) {
				jnumLike = "%" + jnumLike;
			}
			if (!jnumLike.endsWith("%")) {
				jnumLike = jnumLike + "%";
			}
		}
		return jnumLike;
	}

	public List<String> getJnums() {
		return jnums;
	}

	public String getJbnum() {
		return jbnum;
	}

	public String getJbnumLike() {
		if (jbnumLike != null && jbnumLike.trim().length() > 0) {
			if (!jbnumLike.startsWith("%")) {
				jbnumLike = "%" + jbnumLike;
			}
			if (!jbnumLike.endsWith("%")) {
				jbnumLike = jbnumLike + "%";
			}
		}
		return jbnumLike;
	}

	public List<String> getJbnums() {
		return jbnums;
	}

	public String getTnum() {
		return tnum;
	}

	public String getTnumLike() {
		if (tnumLike != null && tnumLike.trim().length() > 0) {
			if (!tnumLike.startsWith("%")) {
				tnumLike = "%" + tnumLike;
			}
			if (!tnumLike.endsWith("%")) {
				tnumLike = tnumLike + "%";
			}
		}
		return tnumLike;
	}

	public List<String> getTnums() {
		return tnums;
	}

	public String getTzoom() {
		return tzoom;
	}

	public String getTzoomLike() {
		if (tzoomLike != null && tzoomLike.trim().length() > 0) {
			if (!tzoomLike.startsWith("%")) {
				tzoomLike = "%" + tzoomLike;
			}
			if (!tzoomLike.endsWith("%")) {
				tzoomLike = tzoomLike + "%";
			}
		}
		return tzoomLike;
	}

	public List<String> getTzooms() {
		return tzooms;
	}

	public String getTflag() {
		return tflag;
	}

	public String getTflagLike() {
		if (tflagLike != null && tflagLike.trim().length() > 0) {
			if (!tflagLike.startsWith("%")) {
				tflagLike = "%" + tflagLike;
			}
			if (!tflagLike.endsWith("%")) {
				tflagLike = tflagLike + "%";
			}
		}
		return tflagLike;
	}

	public List<String> getTflags() {
		return tflags;
	}

	public String getTdesigner() {
		return tdesigner;
	}

	public String getTdesignerLike() {
		if (tdesignerLike != null && tdesignerLike.trim().length() > 0) {
			if (!tdesignerLike.startsWith("%")) {
				tdesignerLike = "%" + tdesignerLike;
			}
			if (!tdesignerLike.endsWith("%")) {
				tdesignerLike = tdesignerLike + "%";
			}
		}
		return tdesignerLike;
	}

	public List<String> getTdesigners() {
		return tdesigners;
	}

	public String getTviewer() {
		return tviewer;
	}

	public String getTviewerLike() {
		if (tviewerLike != null && tviewerLike.trim().length() > 0) {
			if (!tviewerLike.startsWith("%")) {
				tviewerLike = "%" + tviewerLike;
			}
			if (!tviewerLike.endsWith("%")) {
				tviewerLike = tviewerLike + "%";
			}
		}
		return tviewerLike;
	}

	public List<String> getTviewers() {
		return tviewers;
	}

	public String getTassessor() {
		return tassessor;
	}

	public String getTassessorLike() {
		if (tassessorLike != null && tassessorLike.trim().length() > 0) {
			if (!tassessorLike.startsWith("%")) {
				tassessorLike = "%" + tassessorLike;
			}
			if (!tassessorLike.endsWith("%")) {
				tassessorLike = tassessorLike + "%";
			}
		}
		return tassessorLike;
	}

	public List<String> getTassessors() {
		return tassessors;
	}

	public String getTvision() {
		return tvision;
	}

	public String getTvisionLike() {
		if (tvisionLike != null && tvisionLike.trim().length() > 0) {
			if (!tvisionLike.startsWith("%")) {
				tvisionLike = "%" + tvisionLike;
			}
			if (!tvisionLike.endsWith("%")) {
				tvisionLike = tvisionLike + "%";
			}
		}
		return tvisionLike;
	}

	public List<String> getTvisions() {
		return tvisions;
	}

	public Integer getClistNo() {
		return clistNo;
	}

	public Integer getClistNoGreaterThanOrEqual() {
		return clistNoGreaterThanOrEqual;
	}

	public Integer getClistNoLessThanOrEqual() {
		return clistNoLessThanOrEqual;
	}

	public List<Integer> getClistNos() {
		return clistNos;
	}

	public String getCpageNo() {
		return cpageNo;
	}

	public String getCpageNoLike() {
		if (cpageNoLike != null && cpageNoLike.trim().length() > 0) {
			if (!cpageNoLike.startsWith("%")) {
				cpageNoLike = "%" + cpageNoLike;
			}
			if (!cpageNoLike.endsWith("%")) {
				cpageNoLike = cpageNoLike + "%";
			}
		}
		return cpageNoLike;
	}

	public List<String> getCpageNos() {
		return cpageNos;
	}

	public String getVnum() {
		return vnum;
	}

	public String getVnumLike() {
		if (vnumLike != null && vnumLike.trim().length() > 0) {
			if (!vnumLike.startsWith("%")) {
				vnumLike = "%" + vnumLike;
			}
			if (!vnumLike.endsWith("%")) {
				vnumLike = vnumLike + "%";
			}
		}
		return vnumLike;
	}

	public List<String> getVnums() {
		return vnums;
	}

	public String getCvnum() {
		return cvnum;
	}

	public String getCvnumLike() {
		if (cvnumLike != null && cvnumLike.trim().length() > 0) {
			if (!cvnumLike.startsWith("%")) {
				cvnumLike = "%" + cvnumLike;
			}
			if (!cvnumLike.endsWith("%")) {
				cvnumLike = cvnumLike + "%";
			}
		}
		return cvnumLike;
	}

	public List<String> getCvnums() {
		return cvnums;
	}

	public String getTreedListStr() {
		return treedListStr;
	}

	public String getTreedListStrLike() {
		if (treedListStrLike != null && treedListStrLike.trim().length() > 0) {
			if (!treedListStrLike.startsWith("%")) {
				treedListStrLike = "%" + treedListStrLike;
			}
			if (!treedListStrLike.endsWith("%")) {
				treedListStrLike = treedListStrLike + "%";
			}
		}
		return treedListStrLike;
	}

	public List<String> getTreedListStrs() {
		return treedListStrs;
	}

	public Date getCtimeEndGreaterThanOrEqual() {
		return ctimeEndGreaterThanOrEqual;
	}

	public Date getCtimeEndLessThanOrEqual() {
		return ctimeEndLessThanOrEqual;
	}

	public Integer getProjIndex() {
		return projIndex;
	}

	public Integer getProjIndexGreaterThanOrEqual() {
		return projIndexGreaterThanOrEqual;
	}

	public Integer getProjIndexLessThanOrEqual() {
		return projIndexLessThanOrEqual;
	}

	public List<Integer> getProjIndexs() {
		return projIndexs;
	}

	public Integer getTreeParent() {
		return treeParent;
	}

	public Integer getTreeParentGreaterThanOrEqual() {
		return treeParentGreaterThanOrEqual;
	}

	public Integer getTreeParentLessThanOrEqual() {
		return treeParentLessThanOrEqual;
	}

	public List<Integer> getTreeParents() {
		return treeParents;
	}

	public Integer getTreeList() {
		return treeList;
	}

	public Integer getTreeListGreaterThanOrEqual() {
		return treeListGreaterThanOrEqual;
	}

	public Integer getTreeListLessThanOrEqual() {
		return treeListLessThanOrEqual;
	}

	public List<Integer> getTreeLists() {
		return treeLists;
	}

	public void setEfileNum(Integer efileNum) {
		this.efileNum = efileNum;
	}

	public void setEfileNumGreaterThanOrEqual(Integer efileNumGreaterThanOrEqual) {
		this.efileNumGreaterThanOrEqual = efileNumGreaterThanOrEqual;
	}

	public void setEfileNumLessThanOrEqual(Integer efileNumLessThanOrEqual) {
		this.efileNumLessThanOrEqual = efileNumLessThanOrEqual;
	}

	public void setEfileNums(List<Integer> efileNums) {
		this.efileNums = efileNums;
	}

	public void setListNum(String listNum) {
		this.listNum = listNum;
	}

	public void setListNumLike(String listNumLike) {
		this.listNumLike = listNumLike;
	}

	public void setListNums(List<String> listNums) {
		this.listNums = listNums;
	}

	public void setListId(String listId) {
		this.listId = listId;
	}

	public void setListIdLike(String listIdLike) {
		this.listIdLike = listIdLike;
	}

	public void setListIds(List<String> listIds) {
		this.listIds = listIds;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public void setPidGreaterThanOrEqual(Integer pidGreaterThanOrEqual) {
		this.pidGreaterThanOrEqual = pidGreaterThanOrEqual;
	}

	public void setPidLessThanOrEqual(Integer pidLessThanOrEqual) {
		this.pidLessThanOrEqual = pidLessThanOrEqual;
	}

	public void setPids(List<Integer> pids) {
		this.pids = pids;
	}

	public void setProjId(Integer projId) {
		this.projId = projId;
	}

	public void setProjIdGreaterThanOrEqual(Integer projIdGreaterThanOrEqual) {
		this.projIdGreaterThanOrEqual = projIdGreaterThanOrEqual;
	}

	public void setProjIdLessThanOrEqual(Integer projIdLessThanOrEqual) {
		this.projIdLessThanOrEqual = projIdLessThanOrEqual;
	}

	public void setProjIds(List<Integer> projIds) {
		this.projIds = projIds;
	}

	public void setDwid(Integer dwid) {
		this.dwid = dwid;
	}

	public void setDwidGreaterThanOrEqual(Integer dwidGreaterThanOrEqual) {
		this.dwidGreaterThanOrEqual = dwidGreaterThanOrEqual;
	}

	public void setDwidLessThanOrEqual(Integer dwidLessThanOrEqual) {
		this.dwidLessThanOrEqual = dwidLessThanOrEqual;
	}

	public void setDwids(List<Integer> dwids) {
		this.dwids = dwids;
	}

	public void setFbid(Integer fbid) {
		this.fbid = fbid;
	}

	public void setFbidGreaterThanOrEqual(Integer fbidGreaterThanOrEqual) {
		this.fbidGreaterThanOrEqual = fbidGreaterThanOrEqual;
	}

	public void setFbidLessThanOrEqual(Integer fbidLessThanOrEqual) {
		this.fbidLessThanOrEqual = fbidLessThanOrEqual;
	}

	public void setFbids(List<Integer> fbids) {
		this.fbids = fbids;
	}

	public void setFxid(Integer fxid) {
		this.fxid = fxid;
	}

	public void setFxidGreaterThanOrEqual(Integer fxidGreaterThanOrEqual) {
		this.fxidGreaterThanOrEqual = fxidGreaterThanOrEqual;
	}

	public void setFxidLessThanOrEqual(Integer fxidLessThanOrEqual) {
		this.fxidLessThanOrEqual = fxidLessThanOrEqual;
	}

	public void setFxids(List<Integer> fxids) {
		this.fxids = fxids;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}

	public void setJidLike(String jidLike) {
		this.jidLike = jidLike;
	}

	public void setJids(List<String> jids) {
		this.jids = jids;
	}

	public void setFlid(String flid) {
		this.flid = flid;
	}

	public void setFlidLike(String flidLike) {
		this.flidLike = flidLike;
	}

	public void setFlids(List<String> flids) {
		this.flids = flids;
	}

	public void setTopNode(String topNode) {
		this.topNode = topNode;
	}

	public void setTopNodeLike(String topNodeLike) {
		this.topNodeLike = topNodeLike;
	}

	public void setTopNodes(List<String> topNodes) {
		this.topNodes = topNodes;
	}

	public void setTopNodeM(String topNodeM) {
		this.topNodeM = topNodeM;
	}

	public void setTopNodeMLike(String topNodeMLike) {
		this.topNodeMLike = topNodeMLike;
	}

	public void setTopNodeMs(List<String> topNodeMs) {
		this.topNodeMs = topNodeMs;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}

	public void setVidLike(String vidLike) {
		this.vidLike = vidLike;
	}

	public void setVids(List<String> vids) {
		this.vids = vids;
	}

	public void setOldVid(String oldVid) {
		this.oldVid = oldVid;
	}

	public void setOldVidLike(String oldVidLike) {
		this.oldVidLike = oldVidLike;
	}

	public void setOldVids(List<String> oldVids) {
		this.oldVids = oldVids;
	}

	public void setVisFlag(String visFlag) {
		this.visFlag = visFlag;
	}

	public void setVisFlagLike(String visFlagLike) {
		this.visFlagLike = visFlagLike;
	}

	public void setVisFlags(List<String> visFlags) {
		this.visFlags = visFlags;
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

	public void setFilingFlag(String filingFlag) {
		this.filingFlag = filingFlag;
	}

	public void setFilingFlagLike(String filingFlagLike) {
		this.filingFlagLike = filingFlagLike;
	}

	public void setFilingFlags(List<String> filingFlags) {
		this.filingFlags = filingFlags;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}

	public void setSaveFlagLike(String saveFlagLike) {
		this.saveFlagLike = saveFlagLike;
	}

	public void setSaveFlags(List<String> saveFlags) {
		this.saveFlags = saveFlags;
	}

	public void setOpenFlag(String openFlag) {
		this.openFlag = openFlag;
	}

	public void setOpenFlagLike(String openFlagLike) {
		this.openFlagLike = openFlagLike;
	}

	public void setOpenFlags(List<String> openFlags) {
		this.openFlags = openFlags;
	}

	public void setCheckupFlag(String checkupFlag) {
		this.checkupFlag = checkupFlag;
	}

	public void setCheckupFlagLike(String checkupFlagLike) {
		this.checkupFlagLike = checkupFlagLike;
	}

	public void setCheckupFlags(List<String> checkupFlags) {
		this.checkupFlags = checkupFlags;
	}

	public void setFinishFlag(String finishFlag) {
		this.finishFlag = finishFlag;
	}

	public void setFinishFlagLike(String finishFlagLike) {
		this.finishFlagLike = finishFlagLike;
	}

	public void setFinishFlags(List<String> finishFlags) {
		this.finishFlags = finishFlags;
	}

	public void setFromID(String fromID) {
		this.fromID = fromID;
	}

	public void setFromIDLike(String fromIDLike) {
		this.fromIDLike = fromIDLike;
	}

	public void setFromIDs(List<String> fromIDs) {
		this.fromIDs = fromIDs;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public void setTnameLike(String tnameLike) {
		this.tnameLike = tnameLike;
	}

	public void setTnames(List<String> tnames) {
		this.tnames = tnames;
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

	public void setTagnum(String tagnum) {
		this.tagnum = tagnum;
	}

	public void setTagnumLike(String tagnumLike) {
		this.tagnumLike = tagnumLike;
	}

	public void setTagnums(List<String> tagnums) {
		this.tagnums = tagnums;
	}

	public void setFileNum(String fileNum) {
		this.fileNum = fileNum;
	}

	public void setFileNumLike(String fileNumLike) {
		this.fileNumLike = fileNumLike;
	}

	public void setFileNums(List<String> fileNums) {
		this.fileNums = fileNums;
	}

	public void setThematic(String thematic) {
		this.thematic = thematic;
	}

	public void setThematicLike(String thematicLike) {
		this.thematicLike = thematicLike;
	}

	public void setThematics(List<String> thematics) {
		this.thematics = thematics;
	}

	public void setCtimeGreaterThanOrEqual(Date ctimeGreaterThanOrEqual) {
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
	}

	public void setCtimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
	}

	public void setPageno(String pageno) {
		this.pageno = pageno;
	}

	public void setPagenoLike(String pagenoLike) {
		this.pagenoLike = pagenoLike;
	}

	public void setPagenos(List<String> pagenos) {
		this.pagenos = pagenos;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void setLevelLike(String levelLike) {
		this.levelLike = levelLike;
	}

	public void setLevels(List<String> levels) {
		this.levels = levels;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setPageGreaterThanOrEqual(Integer pageGreaterThanOrEqual) {
		this.pageGreaterThanOrEqual = pageGreaterThanOrEqual;
	}

	public void setPageLessThanOrEqual(Integer pageLessThanOrEqual) {
		this.pageLessThanOrEqual = pageLessThanOrEqual;
	}

	public void setPages(List<Integer> pages) {
		this.pages = pages;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public void setFileTypeLike(String fileTypeLike) {
		this.fileTypeLike = fileTypeLike;
	}

	public void setFileTypes(List<String> fileTypes) {
		this.fileTypes = fileTypes;
	}

	public void setFontsNum(String fontsNum) {
		this.fontsNum = fontsNum;
	}

	public void setFontsNumLike(String fontsNumLike) {
		this.fontsNumLike = fontsNumLike;
	}

	public void setFontsNums(List<String> fontsNums) {
		this.fontsNums = fontsNums;
	}

	public void setSaveTime(String saveTime) {
		this.saveTime = saveTime;
	}

	public void setSaveTimeLike(String saveTimeLike) {
		this.saveTimeLike = saveTimeLike;
	}

	public void setSaveTimes(List<String> saveTimes) {
		this.saveTimes = saveTimes;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setCompanyLike(String companyLike) {
		this.companyLike = companyLike;
	}

	public void setCompanys(List<String> companys) {
		this.companys = companys;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public void setYearGreaterThanOrEqual(Integer yearGreaterThanOrEqual) {
		this.yearGreaterThanOrEqual = yearGreaterThanOrEqual;
	}

	public void setYearLessThanOrEqual(Integer yearLessThanOrEqual) {
		this.yearLessThanOrEqual = yearLessThanOrEqual;
	}

	public void setYears(List<Integer> years) {
		this.years = years;
	}

	public void setFileAtt(String fileAtt) {
		this.fileAtt = fileAtt;
	}

	public void setFileAttLike(String fileAttLike) {
		this.fileAttLike = fileAttLike;
	}

	public void setFileAtts(List<String> fileAtts) {
		this.fileAtts = fileAtts;
	}

	public void setAnnotations(String annotations) {
		this.annotations = annotations;
	}

	public void setAnnotationsLike(String annotationsLike) {
		this.annotationsLike = annotationsLike;
	}

	public void setAnnotationss(List<String> annotationss) {
		this.annotationss = annotationss;
	}

	public void setCarrierType(String carrierType) {
		this.carrierType = carrierType;
	}

	public void setCarrierTypeLike(String carrierTypeLike) {
		this.carrierTypeLike = carrierTypeLike;
	}

	public void setCarrierTypes(List<String> carrierTypes) {
		this.carrierTypes = carrierTypes;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setSummaryLike(String summaryLike) {
		this.summaryLike = summaryLike;
	}

	public void setSummarys(List<String> summarys) {
		this.summarys = summarys;
	}

	public void setPtext(String ptext) {
		this.ptext = ptext;
	}

	public void setPtextLike(String ptextLike) {
		this.ptextLike = ptextLike;
	}

	public void setPtexts(List<String> ptexts) {
		this.ptexts = ptexts;
	}

	public void setCarrieru(String carrieru) {
		this.carrieru = carrieru;
	}

	public void setCarrieruLike(String carrieruLike) {
		this.carrieruLike = carrieruLike;
	}

	public void setCarrierus(List<String> carrierus) {
		this.carrierus = carrierus;
	}

	public void setGlideNum(String glideNum) {
		this.glideNum = glideNum;
	}

	public void setGlideNumLike(String glideNumLike) {
		this.glideNumLike = glideNumLike;
	}

	public void setGlideNums(List<String> glideNums) {
		this.glideNums = glideNums;
	}

	public void setEfile(String efile) {
		this.efile = efile;
	}

	public void setEfileLike(String efileLike) {
		this.efileLike = efileLike;
	}

	public void setEfiles(List<String> efiles) {
		this.efiles = efiles;
	}

	public void setFtimeGreaterThanOrEqual(Date ftimeGreaterThanOrEqual) {
		this.ftimeGreaterThanOrEqual = ftimeGreaterThanOrEqual;
	}

	public void setFtimeLessThanOrEqual(Date ftimeLessThanOrEqual) {
		this.ftimeLessThanOrEqual = ftimeLessThanOrEqual;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public void setTotalNumLike(String totalNumLike) {
		this.totalNumLike = totalNumLike;
	}

	public void setTotalNums(List<String> totalNums) {
		this.totalNums = totalNums;
	}

	public void setInputMan(String inputMan) {
		this.inputMan = inputMan;
	}

	public void setInputManLike(String inputManLike) {
		this.inputManLike = inputManLike;
	}

	public void setInputMans(List<String> inputMans) {
		this.inputMans = inputMans;
	}

	public void setEtimeGreaterThanOrEqual(Date etimeGreaterThanOrEqual) {
		this.etimeGreaterThanOrEqual = etimeGreaterThanOrEqual;
	}

	public void setEtimeLessThanOrEqual(Date etimeLessThanOrEqual) {
		this.etimeLessThanOrEqual = etimeLessThanOrEqual;
	}

	public void setDotNum(String dotNum) {
		this.dotNum = dotNum;
	}

	public void setDotNumLike(String dotNumLike) {
		this.dotNumLike = dotNumLike;
	}

	public void setDotNums(List<String> dotNums) {
		this.dotNums = dotNums;
	}

	public void setPicNum(String picNum) {
		this.picNum = picNum;
	}

	public void setPicNumLike(String picNumLike) {
		this.picNumLike = picNumLike;
	}

	public void setPicNums(List<String> picNums) {
		this.picNums = picNums;
	}

	public void setRecNum(String recNum) {
		this.recNum = recNum;
	}

	public void setRecNumLike(String recNumLike) {
		this.recNumLike = recNumLike;
	}

	public void setRecNums(List<String> recNums) {
		this.recNums = recNums;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public void setTotalGreaterThanOrEqual(Integer totalGreaterThanOrEqual) {
		this.totalGreaterThanOrEqual = totalGreaterThanOrEqual;
	}

	public void setTotalLessThanOrEqual(Integer totalLessThanOrEqual) {
		this.totalLessThanOrEqual = totalLessThanOrEqual;
	}

	public void setTotals(List<Integer> totals) {
		this.totals = totals;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public void setPageTypeLike(String pageTypeLike) {
		this.pageTypeLike = pageTypeLike;
	}

	public void setPageTypes(List<String> pageTypes) {
		this.pageTypes = pageTypes;
	}

	public void setIsCom(String isCom) {
		this.isCom = isCom;
	}

	public void setIsComLike(String isComLike) {
		this.isComLike = isComLike;
	}

	public void setIsComs(List<String> isComs) {
		this.isComs = isComs;
	}

	public void setIsLocate(String isLocate) {
		this.isLocate = isLocate;
	}

	public void setIsLocateLike(String isLocateLike) {
		this.isLocateLike = isLocateLike;
	}

	public void setIsLocates(List<String> isLocates) {
		this.isLocates = isLocates;
	}

	public void setWcompany(String wcompany) {
		this.wcompany = wcompany;
	}

	public void setWcompanyLike(String wcompanyLike) {
		this.wcompanyLike = wcompanyLike;
	}

	public void setWcompanys(List<String> wcompanys) {
		this.wcompanys = wcompanys;
	}

	public void setWcompanyID(String wcompanyID) {
		this.wcompanyID = wcompanyID;
	}

	public void setWcompanyIDLike(String wcompanyIDLike) {
		this.wcompanyIDLike = wcompanyIDLike;
	}

	public void setWcompanyIDs(List<String> wcompanyIDs) {
		this.wcompanyIDs = wcompanyIDs;
	}

	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}

	public void setSendFlagLike(String sendFlagLike) {
		this.sendFlagLike = sendFlagLike;
	}

	public void setSendFlags(List<String> sendFlags) {
		this.sendFlags = sendFlags;
	}

	public void setLcontent(String lcontent) {
		this.lcontent = lcontent;
	}

	public void setLcontentLike(String lcontentLike) {
		this.lcontentLike = lcontentLike;
	}

	public void setLcontents(List<String> lcontents) {
		this.lcontents = lcontents;
	}

	public void setLcompany(String lcompany) {
		this.lcompany = lcompany;
	}

	public void setLcompanyLike(String lcompanyLike) {
		this.lcompanyLike = lcompanyLike;
	}

	public void setLcompanys(List<String> lcompanys) {
		this.lcompanys = lcompanys;
	}

	public void setLman(String lman) {
		this.lman = lman;
	}

	public void setLmanLike(String lmanLike) {
		this.lmanLike = lmanLike;
	}

	public void setLmans(List<String> lmans) {
		this.lmans = lmans;
	}

	public void setLlen(String llen) {
		this.llen = llen;
	}

	public void setLlenLike(String llenLike) {
		this.llenLike = llenLike;
	}

	public void setLlens(List<String> llens) {
		this.llens = llens;
	}

	public void setLdateGreaterThanOrEqual(Date ldateGreaterThanOrEqual) {
		this.ldateGreaterThanOrEqual = ldateGreaterThanOrEqual;
	}

	public void setLdateLessThanOrEqual(Date ldateLessThanOrEqual) {
		this.ldateLessThanOrEqual = ldateLessThanOrEqual;
	}

	public void setJconten(String jconten) {
		this.jconten = jconten;
	}

	public void setJcontenLike(String jcontenLike) {
		this.jcontenLike = jcontenLike;
	}

	public void setJcontens(List<String> jcontens) {
		this.jcontens = jcontens;
	}

	public void setJplace(String jplace) {
		this.jplace = jplace;
	}

	public void setJplaceLike(String jplaceLike) {
		this.jplaceLike = jplaceLike;
	}

	public void setJplaces(List<String> jplaces) {
		this.jplaces = jplaces;
	}

	public void setJman(String jman) {
		this.jman = jman;
	}

	public void setJmanLike(String jmanLike) {
		this.jmanLike = jmanLike;
	}

	public void setJmans(List<String> jmans) {
		this.jmans = jmans;
	}

	public void setJback(String jback) {
		this.jback = jback;
	}

	public void setJbackLike(String jbackLike) {
		this.jbackLike = jbackLike;
	}

	public void setJbacks(List<String> jbacks) {
		this.jbacks = jbacks;
	}

	public void setJactor(String jactor) {
		this.jactor = jactor;
	}

	public void setJactorLike(String jactorLike) {
		this.jactorLike = jactorLike;
	}

	public void setJactors(List<String> jactors) {
		this.jactors = jactors;
	}

	public void setJnum(String jnum) {
		this.jnum = jnum;
	}

	public void setJnumLike(String jnumLike) {
		this.jnumLike = jnumLike;
	}

	public void setJnums(List<String> jnums) {
		this.jnums = jnums;
	}

	public void setJbnum(String jbnum) {
		this.jbnum = jbnum;
	}

	public void setJbnumLike(String jbnumLike) {
		this.jbnumLike = jbnumLike;
	}

	public void setJbnums(List<String> jbnums) {
		this.jbnums = jbnums;
	}

	public void setTnum(String tnum) {
		this.tnum = tnum;
	}

	public void setTnumLike(String tnumLike) {
		this.tnumLike = tnumLike;
	}

	public void setTnums(List<String> tnums) {
		this.tnums = tnums;
	}

	public void setTzoom(String tzoom) {
		this.tzoom = tzoom;
	}

	public void setTzoomLike(String tzoomLike) {
		this.tzoomLike = tzoomLike;
	}

	public void setTzooms(List<String> tzooms) {
		this.tzooms = tzooms;
	}

	public void setTflag(String tflag) {
		this.tflag = tflag;
	}

	public void setTflagLike(String tflagLike) {
		this.tflagLike = tflagLike;
	}

	public void setTflags(List<String> tflags) {
		this.tflags = tflags;
	}

	public void setTdesigner(String tdesigner) {
		this.tdesigner = tdesigner;
	}

	public void setTdesignerLike(String tdesignerLike) {
		this.tdesignerLike = tdesignerLike;
	}

	public void setTdesigners(List<String> tdesigners) {
		this.tdesigners = tdesigners;
	}

	public void setTviewer(String tviewer) {
		this.tviewer = tviewer;
	}

	public void setTviewerLike(String tviewerLike) {
		this.tviewerLike = tviewerLike;
	}

	public void setTviewers(List<String> tviewers) {
		this.tviewers = tviewers;
	}

	public void setTassessor(String tassessor) {
		this.tassessor = tassessor;
	}

	public void setTassessorLike(String tassessorLike) {
		this.tassessorLike = tassessorLike;
	}

	public void setTassessors(List<String> tassessors) {
		this.tassessors = tassessors;
	}

	public void setTvision(String tvision) {
		this.tvision = tvision;
	}

	public void setTvisionLike(String tvisionLike) {
		this.tvisionLike = tvisionLike;
	}

	public void setTvisions(List<String> tvisions) {
		this.tvisions = tvisions;
	}

	public void setClistNo(Integer clistNo) {
		this.clistNo = clistNo;
	}

	public void setClistNoGreaterThanOrEqual(Integer clistNoGreaterThanOrEqual) {
		this.clistNoGreaterThanOrEqual = clistNoGreaterThanOrEqual;
	}

	public void setClistNoLessThanOrEqual(Integer clistNoLessThanOrEqual) {
		this.clistNoLessThanOrEqual = clistNoLessThanOrEqual;
	}

	public void setClistNos(List<Integer> clistNos) {
		this.clistNos = clistNos;
	}

	public void setCpageNo(String cpageNo) {
		this.cpageNo = cpageNo;
	}

	public void setCpageNoLike(String cpageNoLike) {
		this.cpageNoLike = cpageNoLike;
	}

	public void setCpageNos(List<String> cpageNos) {
		this.cpageNos = cpageNos;
	}

	public void setVnum(String vnum) {
		this.vnum = vnum;
	}

	public void setVnumLike(String vnumLike) {
		this.vnumLike = vnumLike;
	}

	public void setVnums(List<String> vnums) {
		this.vnums = vnums;
	}

	public void setCvnum(String cvnum) {
		this.cvnum = cvnum;
	}

	public void setCvnumLike(String cvnumLike) {
		this.cvnumLike = cvnumLike;
	}

	public void setCvnums(List<String> cvnums) {
		this.cvnums = cvnums;
	}

	public void setTreedListStr(String treedListStr) {
		this.treedListStr = treedListStr;
	}

	public void setTreedListStrLike(String treedListStrLike) {
		this.treedListStrLike = treedListStrLike;
	}

	public void setTreedListStrs(List<String> treedListStrs) {
		this.treedListStrs = treedListStrs;
	}

	public void setCtimeEndGreaterThanOrEqual(Date ctimeEndGreaterThanOrEqual) {
		this.ctimeEndGreaterThanOrEqual = ctimeEndGreaterThanOrEqual;
	}

	public void setCtimeEndLessThanOrEqual(Date ctimeEndLessThanOrEqual) {
		this.ctimeEndLessThanOrEqual = ctimeEndLessThanOrEqual;
	}

	public void setProjIndex(Integer projIndex) {
		this.projIndex = projIndex;
	}

	public void setProjIndexGreaterThanOrEqual(
			Integer projIndexGreaterThanOrEqual) {
		this.projIndexGreaterThanOrEqual = projIndexGreaterThanOrEqual;
	}

	public void setProjIndexLessThanOrEqual(Integer projIndexLessThanOrEqual) {
		this.projIndexLessThanOrEqual = projIndexLessThanOrEqual;
	}

	public void setProjIndexs(List<Integer> projIndexs) {
		this.projIndexs = projIndexs;
	}

	public void setTreeParent(Integer treeParent) {
		this.treeParent = treeParent;
	}

	public void setTreeParentGreaterThanOrEqual(
			Integer treeParentGreaterThanOrEqual) {
		this.treeParentGreaterThanOrEqual = treeParentGreaterThanOrEqual;
	}

	public void setTreeParentLessThanOrEqual(Integer treeParentLessThanOrEqual) {
		this.treeParentLessThanOrEqual = treeParentLessThanOrEqual;
	}

	public void setTreeParents(List<Integer> treeParents) {
		this.treeParents = treeParents;
	}

	public void setTreeList(Integer treeList) {
		this.treeList = treeList;
	}

	public void setTreeListGreaterThanOrEqual(Integer treeListGreaterThanOrEqual) {
		this.treeListGreaterThanOrEqual = treeListGreaterThanOrEqual;
	}

	public void setTreeListLessThanOrEqual(Integer treeListLessThanOrEqual) {
		this.treeListLessThanOrEqual = treeListLessThanOrEqual;
	}

	public void setTreeLists(List<Integer> treeLists) {
		this.treeLists = treeLists;
	}

	public PfileQuery efileNum(Integer efileNum) {
		if (efileNum == null) {
			throw new RuntimeException("efileNum is null");
		}
		this.efileNum = efileNum;
		return this;
	}

	public PfileQuery efileNumGreaterThanOrEqual(
			Integer efileNumGreaterThanOrEqual) {
		if (efileNumGreaterThanOrEqual == null) {
			throw new RuntimeException("efileNum is null");
		}
		this.efileNumGreaterThanOrEqual = efileNumGreaterThanOrEqual;
		return this;
	}

	public PfileQuery efileNumLessThanOrEqual(Integer efileNumLessThanOrEqual) {
		if (efileNumLessThanOrEqual == null) {
			throw new RuntimeException("efileNum is null");
		}
		this.efileNumLessThanOrEqual = efileNumLessThanOrEqual;
		return this;
	}

	public PfileQuery efileNums(List<Integer> efileNums) {
		if (efileNums == null) {
			throw new RuntimeException("efileNums is empty ");
		}
		this.efileNums = efileNums;
		return this;
	}

	public PfileQuery listNum(String listNum) {
		if (listNum == null) {
			throw new RuntimeException("listNum is null");
		}
		this.listNum = listNum;
		return this;
	}

	public PfileQuery listNumLike(String listNumLike) {
		if (listNumLike == null) {
			throw new RuntimeException("listNum is null");
		}
		this.listNumLike = listNumLike;
		return this;
	}

	public PfileQuery listNums(List<String> listNums) {
		if (listNums == null) {
			throw new RuntimeException("listNums is empty ");
		}
		this.listNums = listNums;
		return this;
	}

	public PfileQuery listId(String listId) {
		if (listId == null) {
			throw new RuntimeException("listId is null");
		}
		this.listId = listId;
		return this;
	}

	public PfileQuery listIdLike(String listIdLike) {
		if (listIdLike == null) {
			throw new RuntimeException("listId is null");
		}
		this.listIdLike = listIdLike;
		return this;
	}

	public PfileQuery listIds(List<String> listIds) {
		if (listIds == null) {
			throw new RuntimeException("listIds is empty ");
		}
		this.listIds = listIds;
		return this;
	}

	public PfileQuery pid(Integer pid) {
		if (pid == null) {
			throw new RuntimeException("pid is null");
		}
		this.pid = pid;
		return this;
	}

	public PfileQuery pidGreaterThanOrEqual(Integer pidGreaterThanOrEqual) {
		if (pidGreaterThanOrEqual == null) {
			throw new RuntimeException("pid is null");
		}
		this.pidGreaterThanOrEqual = pidGreaterThanOrEqual;
		return this;
	}

	public PfileQuery pidLessThanOrEqual(Integer pidLessThanOrEqual) {
		if (pidLessThanOrEqual == null) {
			throw new RuntimeException("pid is null");
		}
		this.pidLessThanOrEqual = pidLessThanOrEqual;
		return this;
	}

	public PfileQuery pids(List<Integer> pids) {
		if (pids == null) {
			throw new RuntimeException("pids is empty ");
		}
		this.pids = pids;
		return this;
	}

	public PfileQuery projId(Integer projId) {
		if (projId == null) {
			throw new RuntimeException("projId is null");
		}
		this.projId = projId;
		return this;
	}

	public PfileQuery projIdGreaterThanOrEqual(Integer projIdGreaterThanOrEqual) {
		if (projIdGreaterThanOrEqual == null) {
			throw new RuntimeException("projId is null");
		}
		this.projIdGreaterThanOrEqual = projIdGreaterThanOrEqual;
		return this;
	}

	public PfileQuery projIdLessThanOrEqual(Integer projIdLessThanOrEqual) {
		if (projIdLessThanOrEqual == null) {
			throw new RuntimeException("projId is null");
		}
		this.projIdLessThanOrEqual = projIdLessThanOrEqual;
		return this;
	}

	public PfileQuery projIds(List<Integer> projIds) {
		if (projIds == null) {
			throw new RuntimeException("projIds is empty ");
		}
		this.projIds = projIds;
		return this;
	}

	public PfileQuery dwid(Integer dwid) {
		if (dwid == null) {
			throw new RuntimeException("dwid is null");
		}
		this.dwid = dwid;
		return this;
	}

	public PfileQuery dwidGreaterThanOrEqual(Integer dwidGreaterThanOrEqual) {
		if (dwidGreaterThanOrEqual == null) {
			throw new RuntimeException("dwid is null");
		}
		this.dwidGreaterThanOrEqual = dwidGreaterThanOrEqual;
		return this;
	}

	public PfileQuery dwidLessThanOrEqual(Integer dwidLessThanOrEqual) {
		if (dwidLessThanOrEqual == null) {
			throw new RuntimeException("dwid is null");
		}
		this.dwidLessThanOrEqual = dwidLessThanOrEqual;
		return this;
	}

	public PfileQuery dwids(List<Integer> dwids) {
		if (dwids == null) {
			throw new RuntimeException("dwids is empty ");
		}
		this.dwids = dwids;
		return this;
	}

	public PfileQuery fbid(Integer fbid) {
		if (fbid == null) {
			throw new RuntimeException("fbid is null");
		}
		this.fbid = fbid;
		return this;
	}

	public PfileQuery fbidGreaterThanOrEqual(Integer fbidGreaterThanOrEqual) {
		if (fbidGreaterThanOrEqual == null) {
			throw new RuntimeException("fbid is null");
		}
		this.fbidGreaterThanOrEqual = fbidGreaterThanOrEqual;
		return this;
	}

	public PfileQuery fbidLessThanOrEqual(Integer fbidLessThanOrEqual) {
		if (fbidLessThanOrEqual == null) {
			throw new RuntimeException("fbid is null");
		}
		this.fbidLessThanOrEqual = fbidLessThanOrEqual;
		return this;
	}

	public PfileQuery fbids(List<Integer> fbids) {
		if (fbids == null) {
			throw new RuntimeException("fbids is empty ");
		}
		this.fbids = fbids;
		return this;
	}

	public PfileQuery fxid(Integer fxid) {
		if (fxid == null) {
			throw new RuntimeException("fxid is null");
		}
		this.fxid = fxid;
		return this;
	}

	public PfileQuery fxidGreaterThanOrEqual(Integer fxidGreaterThanOrEqual) {
		if (fxidGreaterThanOrEqual == null) {
			throw new RuntimeException("fxid is null");
		}
		this.fxidGreaterThanOrEqual = fxidGreaterThanOrEqual;
		return this;
	}

	public PfileQuery fxidLessThanOrEqual(Integer fxidLessThanOrEqual) {
		if (fxidLessThanOrEqual == null) {
			throw new RuntimeException("fxid is null");
		}
		this.fxidLessThanOrEqual = fxidLessThanOrEqual;
		return this;
	}

	public PfileQuery fxids(List<Integer> fxids) {
		if (fxids == null) {
			throw new RuntimeException("fxids is empty ");
		}
		this.fxids = fxids;
		return this;
	}

	public PfileQuery jid(String jid) {
		if (jid == null) {
			throw new RuntimeException("jid is null");
		}
		this.jid = jid;
		return this;
	}

	public PfileQuery jidLike(String jidLike) {
		if (jidLike == null) {
			throw new RuntimeException("jid is null");
		}
		this.jidLike = jidLike;
		return this;
	}

	public PfileQuery jids(List<String> jids) {
		if (jids == null) {
			throw new RuntimeException("jids is empty ");
		}
		this.jids = jids;
		return this;
	}

	public PfileQuery flid(String flid) {
		if (flid == null) {
			throw new RuntimeException("flid is null");
		}
		this.flid = flid;
		return this;
	}

	public PfileQuery flidLike(String flidLike) {
		if (flidLike == null) {
			throw new RuntimeException("flid is null");
		}
		this.flidLike = flidLike;
		return this;
	}

	public PfileQuery flids(List<String> flids) {
		if (flids == null) {
			throw new RuntimeException("flids is empty ");
		}
		this.flids = flids;
		return this;
	}

	public PfileQuery topNode(String topNode) {
		if (topNode == null) {
			throw new RuntimeException("topNode is null");
		}
		this.topNode = topNode;
		return this;
	}

	public PfileQuery topNodeLike(String topNodeLike) {
		if (topNodeLike == null) {
			throw new RuntimeException("topNode is null");
		}
		this.topNodeLike = topNodeLike;
		return this;
	}

	public PfileQuery topNodes(List<String> topNodes) {
		if (topNodes == null) {
			throw new RuntimeException("topNodes is empty ");
		}
		this.topNodes = topNodes;
		return this;
	}

	public PfileQuery topNodeM(String topNodeM) {
		if (topNodeM == null) {
			throw new RuntimeException("topNodeM is null");
		}
		this.topNodeM = topNodeM;
		return this;
	}

	public PfileQuery topNodeMLike(String topNodeMLike) {
		if (topNodeMLike == null) {
			throw new RuntimeException("topNodeM is null");
		}
		this.topNodeMLike = topNodeMLike;
		return this;
	}

	public PfileQuery topNodeMs(List<String> topNodeMs) {
		if (topNodeMs == null) {
			throw new RuntimeException("topNodeMs is empty ");
		}
		this.topNodeMs = topNodeMs;
		return this;
	}

	public PfileQuery vid(String vid) {
		if (vid == null) {
			throw new RuntimeException("vid is null");
		}
		this.vid = vid;
		return this;
	}

	public PfileQuery vidLike(String vidLike) {
		if (vidLike == null) {
			throw new RuntimeException("vid is null");
		}
		this.vidLike = vidLike;
		return this;
	}

	public PfileQuery vids(List<String> vids) {
		if (vids == null) {
			throw new RuntimeException("vids is empty ");
		}
		this.vids = vids;
		return this;
	}

	public PfileQuery oldVid(String oldVid) {
		if (oldVid == null) {
			throw new RuntimeException("oldVid is null");
		}
		this.oldVid = oldVid;
		return this;
	}

	public PfileQuery oldVidLike(String oldVidLike) {
		if (oldVidLike == null) {
			throw new RuntimeException("oldVid is null");
		}
		this.oldVidLike = oldVidLike;
		return this;
	}

	public PfileQuery oldVids(List<String> oldVids) {
		if (oldVids == null) {
			throw new RuntimeException("oldVids is empty ");
		}
		this.oldVids = oldVids;
		return this;
	}

	public PfileQuery visFlag(String visFlag) {
		if (visFlag == null) {
			throw new RuntimeException("visFlag is null");
		}
		this.visFlag = visFlag;
		return this;
	}

	public PfileQuery visFlagLike(String visFlagLike) {
		if (visFlagLike == null) {
			throw new RuntimeException("visFlag is null");
		}
		this.visFlagLike = visFlagLike;
		return this;
	}

	public PfileQuery visFlags(List<String> visFlags) {
		if (visFlags == null) {
			throw new RuntimeException("visFlags is empty ");
		}
		this.visFlags = visFlags;
		return this;
	}

	public PfileQuery listNo(Integer listNo) {
		if (listNo == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNo = listNo;
		return this;
	}

	public PfileQuery listNoGreaterThanOrEqual(Integer listNoGreaterThanOrEqual) {
		if (listNoGreaterThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoGreaterThanOrEqual = listNoGreaterThanOrEqual;
		return this;
	}

	public PfileQuery listNoLessThanOrEqual(Integer listNoLessThanOrEqual) {
		if (listNoLessThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoLessThanOrEqual = listNoLessThanOrEqual;
		return this;
	}

	public PfileQuery listNos(List<Integer> listNos) {
		if (listNos == null) {
			throw new RuntimeException("listNos is empty ");
		}
		this.listNos = listNos;
		return this;
	}

	public PfileQuery filingFlag(String filingFlag) {
		if (filingFlag == null) {
			throw new RuntimeException("filingFlag is null");
		}
		this.filingFlag = filingFlag;
		return this;
	}

	public PfileQuery filingFlagLike(String filingFlagLike) {
		if (filingFlagLike == null) {
			throw new RuntimeException("filingFlag is null");
		}
		this.filingFlagLike = filingFlagLike;
		return this;
	}

	public PfileQuery filingFlags(List<String> filingFlags) {
		if (filingFlags == null) {
			throw new RuntimeException("filingFlags is empty ");
		}
		this.filingFlags = filingFlags;
		return this;
	}

	public PfileQuery saveFlag(String saveFlag) {
		if (saveFlag == null) {
			throw new RuntimeException("saveFlag is null");
		}
		this.saveFlag = saveFlag;
		return this;
	}

	public PfileQuery saveFlagLike(String saveFlagLike) {
		if (saveFlagLike == null) {
			throw new RuntimeException("saveFlag is null");
		}
		this.saveFlagLike = saveFlagLike;
		return this;
	}

	public PfileQuery saveFlags(List<String> saveFlags) {
		if (saveFlags == null) {
			throw new RuntimeException("saveFlags is empty ");
		}
		this.saveFlags = saveFlags;
		return this;
	}

	public PfileQuery openFlag(String openFlag) {
		if (openFlag == null) {
			throw new RuntimeException("openFlag is null");
		}
		this.openFlag = openFlag;
		return this;
	}

	public PfileQuery openFlagLike(String openFlagLike) {
		if (openFlagLike == null) {
			throw new RuntimeException("openFlag is null");
		}
		this.openFlagLike = openFlagLike;
		return this;
	}

	public PfileQuery openFlags(List<String> openFlags) {
		if (openFlags == null) {
			throw new RuntimeException("openFlags is empty ");
		}
		this.openFlags = openFlags;
		return this;
	}

	public PfileQuery checkupFlag(String checkupFlag) {
		if (checkupFlag == null) {
			throw new RuntimeException("checkupFlag is null");
		}
		this.checkupFlag = checkupFlag;
		return this;
	}

	public PfileQuery checkupFlagLike(String checkupFlagLike) {
		if (checkupFlagLike == null) {
			throw new RuntimeException("checkupFlag is null");
		}
		this.checkupFlagLike = checkupFlagLike;
		return this;
	}

	public PfileQuery checkupFlags(List<String> checkupFlags) {
		if (checkupFlags == null) {
			throw new RuntimeException("checkupFlags is empty ");
		}
		this.checkupFlags = checkupFlags;
		return this;
	}

	public PfileQuery finishFlag(String finishFlag) {
		if (finishFlag == null) {
			throw new RuntimeException("finishFlag is null");
		}
		this.finishFlag = finishFlag;
		return this;
	}

	public PfileQuery finishFlagLike(String finishFlagLike) {
		if (finishFlagLike == null) {
			throw new RuntimeException("finishFlag is null");
		}
		this.finishFlagLike = finishFlagLike;
		return this;
	}

	public PfileQuery finishFlags(List<String> finishFlags) {
		if (finishFlags == null) {
			throw new RuntimeException("finishFlags is empty ");
		}
		this.finishFlags = finishFlags;
		return this;
	}

	public PfileQuery fromID(String fromID) {
		if (fromID == null) {
			throw new RuntimeException("fromID is null");
		}
		this.fromID = fromID;
		return this;
	}

	public PfileQuery fromIDLike(String fromIDLike) {
		if (fromIDLike == null) {
			throw new RuntimeException("fromID is null");
		}
		this.fromIDLike = fromIDLike;
		return this;
	}

	public PfileQuery fromIDs(List<String> fromIDs) {
		if (fromIDs == null) {
			throw new RuntimeException("fromIDs is empty ");
		}
		this.fromIDs = fromIDs;
		return this;
	}

	public PfileQuery tname(String tname) {
		if (tname == null) {
			throw new RuntimeException("tname is null");
		}
		this.tname = tname;
		return this;
	}

	public PfileQuery tnameLike(String tnameLike) {
		if (tnameLike == null) {
			throw new RuntimeException("tname is null");
		}
		this.tnameLike = tnameLike;
		return this;
	}

	public PfileQuery tnames(List<String> tnames) {
		if (tnames == null) {
			throw new RuntimeException("tnames is empty ");
		}
		this.tnames = tnames;
		return this;
	}

	public PfileQuery duty(String duty) {
		if (duty == null) {
			throw new RuntimeException("duty is null");
		}
		this.duty = duty;
		return this;
	}

	public PfileQuery dutyLike(String dutyLike) {
		if (dutyLike == null) {
			throw new RuntimeException("duty is null");
		}
		this.dutyLike = dutyLike;
		return this;
	}

	public PfileQuery dutys(List<String> dutys) {
		if (dutys == null) {
			throw new RuntimeException("dutys is empty ");
		}
		this.dutys = dutys;
		return this;
	}

	public PfileQuery tagnum(String tagnum) {
		if (tagnum == null) {
			throw new RuntimeException("tagnum is null");
		}
		this.tagnum = tagnum;
		return this;
	}

	public PfileQuery tagnumLike(String tagnumLike) {
		if (tagnumLike == null) {
			throw new RuntimeException("tagnum is null");
		}
		this.tagnumLike = tagnumLike;
		return this;
	}

	public PfileQuery tagnums(List<String> tagnums) {
		if (tagnums == null) {
			throw new RuntimeException("tagnums is empty ");
		}
		this.tagnums = tagnums;
		return this;
	}

	public PfileQuery fileNum(String fileNum) {
		if (fileNum == null) {
			throw new RuntimeException("fileNum is null");
		}
		this.fileNum = fileNum;
		return this;
	}

	public PfileQuery fileNumLike(String fileNumLike) {
		if (fileNumLike == null) {
			throw new RuntimeException("fileNum is null");
		}
		this.fileNumLike = fileNumLike;
		return this;
	}

	public PfileQuery fileNums(List<String> fileNums) {
		if (fileNums == null) {
			throw new RuntimeException("fileNums is empty ");
		}
		this.fileNums = fileNums;
		return this;
	}

	public PfileQuery thematic(String thematic) {
		if (thematic == null) {
			throw new RuntimeException("thematic is null");
		}
		this.thematic = thematic;
		return this;
	}

	public PfileQuery thematicLike(String thematicLike) {
		if (thematicLike == null) {
			throw new RuntimeException("thematic is null");
		}
		this.thematicLike = thematicLike;
		return this;
	}

	public PfileQuery thematics(List<String> thematics) {
		if (thematics == null) {
			throw new RuntimeException("thematics is empty ");
		}
		this.thematics = thematics;
		return this;
	}

	public PfileQuery ctimeGreaterThanOrEqual(Date ctimeGreaterThanOrEqual) {
		if (ctimeGreaterThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
		return this;
	}

	public PfileQuery ctimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		if (ctimeLessThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
		return this;
	}

	public PfileQuery pageno(String pageno) {
		if (pageno == null) {
			throw new RuntimeException("pageno is null");
		}
		this.pageno = pageno;
		return this;
	}

	public PfileQuery pagenoLike(String pagenoLike) {
		if (pagenoLike == null) {
			throw new RuntimeException("pagenoLike is null");
		}
		this.pagenoLike = pagenoLike;
		return this;
	}

	public PfileQuery pagenos(List<String> pagenos) {
		if (pagenos == null) {
			throw new RuntimeException("pagenos is empty ");
		}
		this.pagenos = pagenos;
		return this;
	}

	public PfileQuery level(String level) {
		if (level == null) {
			throw new RuntimeException("level is null");
		}
		this.level = level;
		return this;
	}

	public PfileQuery levelLike(String levelLike) {
		if (levelLike == null) {
			throw new RuntimeException("level is null");
		}
		this.levelLike = levelLike;
		return this;
	}

	public PfileQuery levels(List<String> levels) {
		if (levels == null) {
			throw new RuntimeException("levels is empty ");
		}
		this.levels = levels;
		return this;
	}

	public PfileQuery page(Integer page) {
		if (page == null) {
			throw new RuntimeException("page is null");
		}
		this.page = page;
		return this;
	}

	public PfileQuery pageGreaterThanOrEqual(Integer pageGreaterThanOrEqual) {
		if (pageGreaterThanOrEqual == null) {
			throw new RuntimeException("page is null");
		}
		this.pageGreaterThanOrEqual = pageGreaterThanOrEqual;
		return this;
	}

	public PfileQuery pageLessThanOrEqual(Integer pageLessThanOrEqual) {
		if (pageLessThanOrEqual == null) {
			throw new RuntimeException("page is null");
		}
		this.pageLessThanOrEqual = pageLessThanOrEqual;
		return this;
	}

	public PfileQuery pages(List<Integer> pages) {
		if (pages == null) {
			throw new RuntimeException("pages is empty ");
		}
		this.pages = pages;
		return this;
	}

	public PfileQuery fileType(String fileType) {
		if (fileType == null) {
			throw new RuntimeException("fileType is null");
		}
		this.fileType = fileType;
		return this;
	}

	public PfileQuery fileTypeLike(String fileTypeLike) {
		if (fileTypeLike == null) {
			throw new RuntimeException("fileType is null");
		}
		this.fileTypeLike = fileTypeLike;
		return this;
	}

	public PfileQuery fileTypes(List<String> fileTypes) {
		if (fileTypes == null) {
			throw new RuntimeException("fileTypes is empty ");
		}
		this.fileTypes = fileTypes;
		return this;
	}

	public PfileQuery fontsNum(String fontsNum) {
		if (fontsNum == null) {
			throw new RuntimeException("fontsNum is null");
		}
		this.fontsNum = fontsNum;
		return this;
	}

	public PfileQuery fontsNumLike(String fontsNumLike) {
		if (fontsNumLike == null) {
			throw new RuntimeException("fontsNum is null");
		}
		this.fontsNumLike = fontsNumLike;
		return this;
	}

	public PfileQuery fontsNums(List<String> fontsNums) {
		if (fontsNums == null) {
			throw new RuntimeException("fontsNums is empty ");
		}
		this.fontsNums = fontsNums;
		return this;
	}

	public PfileQuery saveTime(String saveTime) {
		if (saveTime == null) {
			throw new RuntimeException("saveTime is null");
		}
		this.saveTime = saveTime;
		return this;
	}

	public PfileQuery saveTimeLike(String saveTimeLike) {
		if (saveTimeLike == null) {
			throw new RuntimeException("saveTime is null");
		}
		this.saveTimeLike = saveTimeLike;
		return this;
	}

	public PfileQuery saveTimes(List<String> saveTimes) {
		if (saveTimes == null) {
			throw new RuntimeException("saveTimes is empty ");
		}
		this.saveTimes = saveTimes;
		return this;
	}

	public PfileQuery company(String company) {
		if (company == null) {
			throw new RuntimeException("company is null");
		}
		this.company = company;
		return this;
	}

	public PfileQuery companyLike(String companyLike) {
		if (companyLike == null) {
			throw new RuntimeException("company is null");
		}
		this.companyLike = companyLike;
		return this;
	}

	public PfileQuery companys(List<String> companys) {
		if (companys == null) {
			throw new RuntimeException("companys is empty ");
		}
		this.companys = companys;
		return this;
	}

	public PfileQuery year(Integer year) {
		if (year == null) {
			throw new RuntimeException("year is null");
		}
		this.year = year;
		return this;
	}

	public PfileQuery yearGreaterThanOrEqual(Integer yearGreaterThanOrEqual) {
		if (yearGreaterThanOrEqual == null) {
			throw new RuntimeException("year is null");
		}
		this.yearGreaterThanOrEqual = yearGreaterThanOrEqual;
		return this;
	}

	public PfileQuery yearLessThanOrEqual(Integer yearLessThanOrEqual) {
		if (yearLessThanOrEqual == null) {
			throw new RuntimeException("year is null");
		}
		this.yearLessThanOrEqual = yearLessThanOrEqual;
		return this;
	}

	public PfileQuery years(List<Integer> years) {
		if (years == null) {
			throw new RuntimeException("years is empty ");
		}
		this.years = years;
		return this;
	}

	public PfileQuery fileAtt(String fileAtt) {
		if (fileAtt == null) {
			throw new RuntimeException("fileAtt is null");
		}
		this.fileAtt = fileAtt;
		return this;
	}

	public PfileQuery fileAttLike(String fileAttLike) {
		if (fileAttLike == null) {
			throw new RuntimeException("fileAtt is null");
		}
		this.fileAttLike = fileAttLike;
		return this;
	}

	public PfileQuery fileAtts(List<String> fileAtts) {
		if (fileAtts == null) {
			throw new RuntimeException("fileAtts is empty ");
		}
		this.fileAtts = fileAtts;
		return this;
	}

	public PfileQuery annotations(String annotations) {
		if (annotations == null) {
			throw new RuntimeException("annotations is null");
		}
		this.annotations = annotations;
		return this;
	}

	public PfileQuery annotationsLike(String annotationsLike) {
		if (annotationsLike == null) {
			throw new RuntimeException("annotations is null");
		}
		this.annotationsLike = annotationsLike;
		return this;
	}

	public PfileQuery annotationss(List<String> annotationss) {
		if (annotationss == null) {
			throw new RuntimeException("annotationss is empty ");
		}
		this.annotationss = annotationss;
		return this;
	}

	public PfileQuery carrierType(String carrierType) {
		if (carrierType == null) {
			throw new RuntimeException("carrierType is null");
		}
		this.carrierType = carrierType;
		return this;
	}

	public PfileQuery carrierTypeLike(String carrierTypeLike) {
		if (carrierTypeLike == null) {
			throw new RuntimeException("carrierType is null");
		}
		this.carrierTypeLike = carrierTypeLike;
		return this;
	}

	public PfileQuery carrierTypes(List<String> carrierTypes) {
		if (carrierTypes == null) {
			throw new RuntimeException("carrierTypes is empty ");
		}
		this.carrierTypes = carrierTypes;
		return this;
	}

	public PfileQuery summary(String summary) {
		if (summary == null) {
			throw new RuntimeException("summary is null");
		}
		this.summary = summary;
		return this;
	}

	public PfileQuery summaryLike(String summaryLike) {
		if (summaryLike == null) {
			throw new RuntimeException("summary is null");
		}
		this.summaryLike = summaryLike;
		return this;
	}

	public PfileQuery summarys(List<String> summarys) {
		if (summarys == null) {
			throw new RuntimeException("summarys is empty ");
		}
		this.summarys = summarys;
		return this;
	}

	public PfileQuery ptext(String ptext) {
		if (ptext == null) {
			throw new RuntimeException("ptext is null");
		}
		this.ptext = ptext;
		return this;
	}

	public PfileQuery ptextLike(String ptextLike) {
		if (ptextLike == null) {
			throw new RuntimeException("ptext is null");
		}
		this.ptextLike = ptextLike;
		return this;
	}

	public PfileQuery ptexts(List<String> ptexts) {
		if (ptexts == null) {
			throw new RuntimeException("ptexts is empty ");
		}
		this.ptexts = ptexts;
		return this;
	}

	public PfileQuery carrieru(String carrieru) {
		if (carrieru == null) {
			throw new RuntimeException("carrieru is null");
		}
		this.carrieru = carrieru;
		return this;
	}

	public PfileQuery carrieruLike(String carrieruLike) {
		if (carrieruLike == null) {
			throw new RuntimeException("carrieru is null");
		}
		this.carrieruLike = carrieruLike;
		return this;
	}

	public PfileQuery carrierus(List<String> carrierus) {
		if (carrierus == null) {
			throw new RuntimeException("carrierus is empty ");
		}
		this.carrierus = carrierus;
		return this;
	}

	public PfileQuery glideNum(String glideNum) {
		if (glideNum == null) {
			throw new RuntimeException("glideNum is null");
		}
		this.glideNum = glideNum;
		return this;
	}

	public PfileQuery glideNumLike(String glideNumLike) {
		if (glideNumLike == null) {
			throw new RuntimeException("glideNum is null");
		}
		this.glideNumLike = glideNumLike;
		return this;
	}

	public PfileQuery glideNums(List<String> glideNums) {
		if (glideNums == null) {
			throw new RuntimeException("glideNums is empty ");
		}
		this.glideNums = glideNums;
		return this;
	}

	public PfileQuery efile(String efile) {
		if (efile == null) {
			throw new RuntimeException("efile is null");
		}
		this.efile = efile;
		return this;
	}

	public PfileQuery efileLike(String efileLike) {
		if (efileLike == null) {
			throw new RuntimeException("efile is null");
		}
		this.efileLike = efileLike;
		return this;
	}

	public PfileQuery efiles(List<String> efiles) {
		if (efiles == null) {
			throw new RuntimeException("efiles is empty ");
		}
		this.efiles = efiles;
		return this;
	}

	public PfileQuery ftimeGreaterThanOrEqual(Date ftimeGreaterThanOrEqual) {
		if (ftimeGreaterThanOrEqual == null) {
			throw new RuntimeException("ftime is null");
		}
		this.ftimeGreaterThanOrEqual = ftimeGreaterThanOrEqual;
		return this;
	}

	public PfileQuery ftimeLessThanOrEqual(Date ftimeLessThanOrEqual) {
		if (ftimeLessThanOrEqual == null) {
			throw new RuntimeException("ftime is null");
		}
		this.ftimeLessThanOrEqual = ftimeLessThanOrEqual;
		return this;
	}

	public PfileQuery totalNum(String totalNum) {
		if (totalNum == null) {
			throw new RuntimeException("totalNum is null");
		}
		this.totalNum = totalNum;
		return this;
	}

	public PfileQuery totalNumLike(String totalNumLike) {
		if (totalNumLike == null) {
			throw new RuntimeException("totalNum is null");
		}
		this.totalNumLike = totalNumLike;
		return this;
	}

	public PfileQuery totalNums(List<String> totalNums) {
		if (totalNums == null) {
			throw new RuntimeException("totalNums is empty ");
		}
		this.totalNums = totalNums;
		return this;
	}

	public PfileQuery inputMan(String inputMan) {
		if (inputMan == null) {
			throw new RuntimeException("inputMan is null");
		}
		this.inputMan = inputMan;
		return this;
	}

	public PfileQuery inputManLike(String inputManLike) {
		if (inputManLike == null) {
			throw new RuntimeException("inputMan is null");
		}
		this.inputManLike = inputManLike;
		return this;
	}

	public PfileQuery inputMans(List<String> inputMans) {
		if (inputMans == null) {
			throw new RuntimeException("inputMans is empty ");
		}
		this.inputMans = inputMans;
		return this;
	}

	public PfileQuery etimeGreaterThanOrEqual(Date etimeGreaterThanOrEqual) {
		if (etimeGreaterThanOrEqual == null) {
			throw new RuntimeException("etime is null");
		}
		this.etimeGreaterThanOrEqual = etimeGreaterThanOrEqual;
		return this;
	}

	public PfileQuery etimeLessThanOrEqual(Date etimeLessThanOrEqual) {
		if (etimeLessThanOrEqual == null) {
			throw new RuntimeException("etime is null");
		}
		this.etimeLessThanOrEqual = etimeLessThanOrEqual;
		return this;
	}

	public PfileQuery dotNum(String dotNum) {
		if (dotNum == null) {
			throw new RuntimeException("dotNum is null");
		}
		this.dotNum = dotNum;
		return this;
	}

	public PfileQuery dotNumLike(String dotNumLike) {
		if (dotNumLike == null) {
			throw new RuntimeException("dotNum is null");
		}
		this.dotNumLike = dotNumLike;
		return this;
	}

	public PfileQuery dotNums(List<String> dotNums) {
		if (dotNums == null) {
			throw new RuntimeException("dotNums is empty ");
		}
		this.dotNums = dotNums;
		return this;
	}

	public PfileQuery picNum(String picNum) {
		if (picNum == null) {
			throw new RuntimeException("picNum is null");
		}
		this.picNum = picNum;
		return this;
	}

	public PfileQuery picNumLike(String picNumLike) {
		if (picNumLike == null) {
			throw new RuntimeException("picNum is null");
		}
		this.picNumLike = picNumLike;
		return this;
	}

	public PfileQuery picNums(List<String> picNums) {
		if (picNums == null) {
			throw new RuntimeException("picNums is empty ");
		}
		this.picNums = picNums;
		return this;
	}

	public PfileQuery recNum(String recNum) {
		if (recNum == null) {
			throw new RuntimeException("recNum is null");
		}
		this.recNum = recNum;
		return this;
	}

	public PfileQuery recNumLike(String recNumLike) {
		if (recNumLike == null) {
			throw new RuntimeException("recNum is null");
		}
		this.recNumLike = recNumLike;
		return this;
	}

	public PfileQuery recNums(List<String> recNums) {
		if (recNums == null) {
			throw new RuntimeException("recNums is empty ");
		}
		this.recNums = recNums;
		return this;
	}

	public PfileQuery total(Integer total) {
		if (total == null) {
			throw new RuntimeException("total is null");
		}
		this.total = total;
		return this;
	}

	public PfileQuery totalGreaterThanOrEqual(Integer totalGreaterThanOrEqual) {
		if (totalGreaterThanOrEqual == null) {
			throw new RuntimeException("total is null");
		}
		this.totalGreaterThanOrEqual = totalGreaterThanOrEqual;
		return this;
	}

	public PfileQuery totalLessThanOrEqual(Integer totalLessThanOrEqual) {
		if (totalLessThanOrEqual == null) {
			throw new RuntimeException("total is null");
		}
		this.totalLessThanOrEqual = totalLessThanOrEqual;
		return this;
	}

	public PfileQuery totals(List<Integer> totals) {
		if (totals == null) {
			throw new RuntimeException("totals is empty ");
		}
		this.totals = totals;
		return this;
	}

	public PfileQuery pageType(String pageType) {
		if (pageType == null) {
			throw new RuntimeException("pageType is null");
		}
		this.pageType = pageType;
		return this;
	}

	public PfileQuery pageTypeLike(String pageTypeLike) {
		if (pageTypeLike == null) {
			throw new RuntimeException("pageType is null");
		}
		this.pageTypeLike = pageTypeLike;
		return this;
	}

	public PfileQuery pageTypes(List<String> pageTypes) {
		if (pageTypes == null) {
			throw new RuntimeException("pageTypes is empty ");
		}
		this.pageTypes = pageTypes;
		return this;
	}

	public PfileQuery isCom(String isCom) {
		if (isCom == null) {
			throw new RuntimeException("isCom is null");
		}
		this.isCom = isCom;
		return this;
	}

	public PfileQuery isComLike(String isComLike) {
		if (isComLike == null) {
			throw new RuntimeException("isCom is null");
		}
		this.isComLike = isComLike;
		return this;
	}

	public PfileQuery isComs(List<String> isComs) {
		if (isComs == null) {
			throw new RuntimeException("isComs is empty ");
		}
		this.isComs = isComs;
		return this;
	}

	public PfileQuery isLocate(String isLocate) {
		if (isLocate == null) {
			throw new RuntimeException("isLocate is null");
		}
		this.isLocate = isLocate;
		return this;
	}

	public PfileQuery isLocateLike(String isLocateLike) {
		if (isLocateLike == null) {
			throw new RuntimeException("isLocate is null");
		}
		this.isLocateLike = isLocateLike;
		return this;
	}

	public PfileQuery isLocates(List<String> isLocates) {
		if (isLocates == null) {
			throw new RuntimeException("isLocates is empty ");
		}
		this.isLocates = isLocates;
		return this;
	}

	public PfileQuery wcompany(String wcompany) {
		if (wcompany == null) {
			throw new RuntimeException("wcompany is null");
		}
		this.wcompany = wcompany;
		return this;
	}

	public PfileQuery wcompanyLike(String wcompanyLike) {
		if (wcompanyLike == null) {
			throw new RuntimeException("wcompany is null");
		}
		this.wcompanyLike = wcompanyLike;
		return this;
	}

	public PfileQuery wcompanys(List<String> wcompanys) {
		if (wcompanys == null) {
			throw new RuntimeException("wcompanys is empty ");
		}
		this.wcompanys = wcompanys;
		return this;
	}

	public PfileQuery wcompanyID(String wcompanyID) {
		if (wcompanyID == null) {
			throw new RuntimeException("wcompanyID is null");
		}
		this.wcompanyID = wcompanyID;
		return this;
	}

	public PfileQuery wcompanyIDLike(String wcompanyIDLike) {
		if (wcompanyIDLike == null) {
			throw new RuntimeException("wcompanyID is null");
		}
		this.wcompanyIDLike = wcompanyIDLike;
		return this;
	}

	public PfileQuery wcompanyIDs(List<String> wcompanyIDs) {
		if (wcompanyIDs == null) {
			throw new RuntimeException("wcompanyIDs is empty ");
		}
		this.wcompanyIDs = wcompanyIDs;
		return this;
	}

	public PfileQuery sendFlag(String sendFlag) {
		if (sendFlag == null) {
			throw new RuntimeException("sendFlag is null");
		}
		this.sendFlag = sendFlag;
		return this;
	}

	public PfileQuery sendFlagLike(String sendFlagLike) {
		if (sendFlagLike == null) {
			throw new RuntimeException("sendFlag is null");
		}
		this.sendFlagLike = sendFlagLike;
		return this;
	}

	public PfileQuery sendFlags(List<String> sendFlags) {
		if (sendFlags == null) {
			throw new RuntimeException("sendFlags is empty ");
		}
		this.sendFlags = sendFlags;
		return this;
	}

	public PfileQuery lcontent(String lcontent) {
		if (lcontent == null) {
			throw new RuntimeException("lcontent is null");
		}
		this.lcontent = lcontent;
		return this;
	}

	public PfileQuery lcontentLike(String lcontentLike) {
		if (lcontentLike == null) {
			throw new RuntimeException("lcontent is null");
		}
		this.lcontentLike = lcontentLike;
		return this;
	}

	public PfileQuery lcontents(List<String> lcontents) {
		if (lcontents == null) {
			throw new RuntimeException("lcontents is empty ");
		}
		this.lcontents = lcontents;
		return this;
	}

	public PfileQuery lcompany(String lcompany) {
		if (lcompany == null) {
			throw new RuntimeException("lcompany is null");
		}
		this.lcompany = lcompany;
		return this;
	}

	public PfileQuery lcompanyLike(String lcompanyLike) {
		if (lcompanyLike == null) {
			throw new RuntimeException("lcompany is null");
		}
		this.lcompanyLike = lcompanyLike;
		return this;
	}

	public PfileQuery lcompanys(List<String> lcompanys) {
		if (lcompanys == null) {
			throw new RuntimeException("lcompanys is empty ");
		}
		this.lcompanys = lcompanys;
		return this;
	}

	public PfileQuery lman(String lman) {
		if (lman == null) {
			throw new RuntimeException("lman is null");
		}
		this.lman = lman;
		return this;
	}

	public PfileQuery lmanLike(String lmanLike) {
		if (lmanLike == null) {
			throw new RuntimeException("lman is null");
		}
		this.lmanLike = lmanLike;
		return this;
	}

	public PfileQuery lmans(List<String> lmans) {
		if (lmans == null) {
			throw new RuntimeException("lmans is empty ");
		}
		this.lmans = lmans;
		return this;
	}

	public PfileQuery llen(String llen) {
		if (llen == null) {
			throw new RuntimeException("llen is null");
		}
		this.llen = llen;
		return this;
	}

	public PfileQuery llenLike(String llenLike) {
		if (llenLike == null) {
			throw new RuntimeException("llen is null");
		}
		this.llenLike = llenLike;
		return this;
	}

	public PfileQuery llens(List<String> llens) {
		if (llens == null) {
			throw new RuntimeException("llens is empty ");
		}
		this.llens = llens;
		return this;
	}

	public PfileQuery ldateGreaterThanOrEqual(Date ldateGreaterThanOrEqual) {
		if (ldateGreaterThanOrEqual == null) {
			throw new RuntimeException("ldate is null");
		}
		this.ldateGreaterThanOrEqual = ldateGreaterThanOrEqual;
		return this;
	}

	public PfileQuery ldateLessThanOrEqual(Date ldateLessThanOrEqual) {
		if (ldateLessThanOrEqual == null) {
			throw new RuntimeException("ldate is null");
		}
		this.ldateLessThanOrEqual = ldateLessThanOrEqual;
		return this;
	}

	public PfileQuery jconten(String jconten) {
		if (jconten == null) {
			throw new RuntimeException("jconten is null");
		}
		this.jconten = jconten;
		return this;
	}

	public PfileQuery jcontenLike(String jcontenLike) {
		if (jcontenLike == null) {
			throw new RuntimeException("jconten is null");
		}
		this.jcontenLike = jcontenLike;
		return this;
	}

	public PfileQuery jcontens(List<String> jcontens) {
		if (jcontens == null) {
			throw new RuntimeException("jcontens is empty ");
		}
		this.jcontens = jcontens;
		return this;
	}

	public PfileQuery jplace(String jplace) {
		if (jplace == null) {
			throw new RuntimeException("jplace is null");
		}
		this.jplace = jplace;
		return this;
	}

	public PfileQuery jplaceLike(String jplaceLike) {
		if (jplaceLike == null) {
			throw new RuntimeException("jplace is null");
		}
		this.jplaceLike = jplaceLike;
		return this;
	}

	public PfileQuery jplaces(List<String> jplaces) {
		if (jplaces == null) {
			throw new RuntimeException("jplaces is empty ");
		}
		this.jplaces = jplaces;
		return this;
	}

	public PfileQuery jman(String jman) {
		if (jman == null) {
			throw new RuntimeException("jman is null");
		}
		this.jman = jman;
		return this;
	}

	public PfileQuery jmanLike(String jmanLike) {
		if (jmanLike == null) {
			throw new RuntimeException("jman is null");
		}
		this.jmanLike = jmanLike;
		return this;
	}

	public PfileQuery jmans(List<String> jmans) {
		if (jmans == null) {
			throw new RuntimeException("jmans is empty ");
		}
		this.jmans = jmans;
		return this;
	}

	public PfileQuery jback(String jback) {
		if (jback == null) {
			throw new RuntimeException("jback is null");
		}
		this.jback = jback;
		return this;
	}

	public PfileQuery jbackLike(String jbackLike) {
		if (jbackLike == null) {
			throw new RuntimeException("jback is null");
		}
		this.jbackLike = jbackLike;
		return this;
	}

	public PfileQuery jbacks(List<String> jbacks) {
		if (jbacks == null) {
			throw new RuntimeException("jbacks is empty ");
		}
		this.jbacks = jbacks;
		return this;
	}

	public PfileQuery jactor(String jactor) {
		if (jactor == null) {
			throw new RuntimeException("jactor is null");
		}
		this.jactor = jactor;
		return this;
	}

	public PfileQuery jactorLike(String jactorLike) {
		if (jactorLike == null) {
			throw new RuntimeException("jactor is null");
		}
		this.jactorLike = jactorLike;
		return this;
	}

	public PfileQuery jactors(List<String> jactors) {
		if (jactors == null) {
			throw new RuntimeException("jactors is empty ");
		}
		this.jactors = jactors;
		return this;
	}

	public PfileQuery jnum(String jnum) {
		if (jnum == null) {
			throw new RuntimeException("jnum is null");
		}
		this.jnum = jnum;
		return this;
	}

	public PfileQuery jnumLike(String jnumLike) {
		if (jnumLike == null) {
			throw new RuntimeException("jnum is null");
		}
		this.jnumLike = jnumLike;
		return this;
	}

	public PfileQuery jnums(List<String> jnums) {
		if (jnums == null) {
			throw new RuntimeException("jnums is empty ");
		}
		this.jnums = jnums;
		return this;
	}

	public PfileQuery jbnum(String jbnum) {
		if (jbnum == null) {
			throw new RuntimeException("jbnum is null");
		}
		this.jbnum = jbnum;
		return this;
	}

	public PfileQuery jbnumLike(String jbnumLike) {
		if (jbnumLike == null) {
			throw new RuntimeException("jbnum is null");
		}
		this.jbnumLike = jbnumLike;
		return this;
	}

	public PfileQuery jbnums(List<String> jbnums) {
		if (jbnums == null) {
			throw new RuntimeException("jbnums is empty ");
		}
		this.jbnums = jbnums;
		return this;
	}

	public PfileQuery tnum(String tnum) {
		if (tnum == null) {
			throw new RuntimeException("tnum is null");
		}
		this.tnum = tnum;
		return this;
	}

	public PfileQuery tnumLike(String tnumLike) {
		if (tnumLike == null) {
			throw new RuntimeException("tnum is null");
		}
		this.tnumLike = tnumLike;
		return this;
	}

	public PfileQuery tnums(List<String> tnums) {
		if (tnums == null) {
			throw new RuntimeException("tnums is empty ");
		}
		this.tnums = tnums;
		return this;
	}

	public PfileQuery tzoom(String tzoom) {
		if (tzoom == null) {
			throw new RuntimeException("tzoom is null");
		}
		this.tzoom = tzoom;
		return this;
	}

	public PfileQuery tzoomLike(String tzoomLike) {
		if (tzoomLike == null) {
			throw new RuntimeException("tzoom is null");
		}
		this.tzoomLike = tzoomLike;
		return this;
	}

	public PfileQuery tzooms(List<String> tzooms) {
		if (tzooms == null) {
			throw new RuntimeException("tzooms is empty ");
		}
		this.tzooms = tzooms;
		return this;
	}

	public PfileQuery tflag(String tflag) {
		if (tflag == null) {
			throw new RuntimeException("tflag is null");
		}
		this.tflag = tflag;
		return this;
	}

	public PfileQuery tflagLike(String tflagLike) {
		if (tflagLike == null) {
			throw new RuntimeException("tflag is null");
		}
		this.tflagLike = tflagLike;
		return this;
	}

	public PfileQuery tflags(List<String> tflags) {
		if (tflags == null) {
			throw new RuntimeException("tflags is empty ");
		}
		this.tflags = tflags;
		return this;
	}

	public PfileQuery tdesigner(String tdesigner) {
		if (tdesigner == null) {
			throw new RuntimeException("tdesigner is null");
		}
		this.tdesigner = tdesigner;
		return this;
	}

	public PfileQuery tdesignerLike(String tdesignerLike) {
		if (tdesignerLike == null) {
			throw new RuntimeException("tdesigner is null");
		}
		this.tdesignerLike = tdesignerLike;
		return this;
	}

	public PfileQuery tdesigners(List<String> tdesigners) {
		if (tdesigners == null) {
			throw new RuntimeException("tdesigners is empty ");
		}
		this.tdesigners = tdesigners;
		return this;
	}

	public PfileQuery tviewer(String tviewer) {
		if (tviewer == null) {
			throw new RuntimeException("tviewer is null");
		}
		this.tviewer = tviewer;
		return this;
	}

	public PfileQuery tviewerLike(String tviewerLike) {
		if (tviewerLike == null) {
			throw new RuntimeException("tviewer is null");
		}
		this.tviewerLike = tviewerLike;
		return this;
	}

	public PfileQuery tviewers(List<String> tviewers) {
		if (tviewers == null) {
			throw new RuntimeException("tviewers is empty ");
		}
		this.tviewers = tviewers;
		return this;
	}

	public PfileQuery tassessor(String tassessor) {
		if (tassessor == null) {
			throw new RuntimeException("tassessor is null");
		}
		this.tassessor = tassessor;
		return this;
	}

	public PfileQuery tassessorLike(String tassessorLike) {
		if (tassessorLike == null) {
			throw new RuntimeException("tassessor is null");
		}
		this.tassessorLike = tassessorLike;
		return this;
	}

	public PfileQuery tassessors(List<String> tassessors) {
		if (tassessors == null) {
			throw new RuntimeException("tassessors is empty ");
		}
		this.tassessors = tassessors;
		return this;
	}

	public PfileQuery tvision(String tvision) {
		if (tvision == null) {
			throw new RuntimeException("tvision is null");
		}
		this.tvision = tvision;
		return this;
	}

	public PfileQuery tvisionLike(String tvisionLike) {
		if (tvisionLike == null) {
			throw new RuntimeException("tvision is null");
		}
		this.tvisionLike = tvisionLike;
		return this;
	}

	public PfileQuery tvisions(List<String> tvisions) {
		if (tvisions == null) {
			throw new RuntimeException("tvisions is empty ");
		}
		this.tvisions = tvisions;
		return this;
	}

	public PfileQuery clistNo(Integer clistNo) {
		if (clistNo == null) {
			throw new RuntimeException("clistNo is null");
		}
		this.clistNo = clistNo;
		return this;
	}

	public PfileQuery clistNoGreaterThanOrEqual(
			Integer clistNoGreaterThanOrEqual) {
		if (clistNoGreaterThanOrEqual == null) {
			throw new RuntimeException("clistNo is null");
		}
		this.clistNoGreaterThanOrEqual = clistNoGreaterThanOrEqual;
		return this;
	}

	public PfileQuery clistNoLessThanOrEqual(Integer clistNoLessThanOrEqual) {
		if (clistNoLessThanOrEqual == null) {
			throw new RuntimeException("clistNo is null");
		}
		this.clistNoLessThanOrEqual = clistNoLessThanOrEqual;
		return this;
	}

	public PfileQuery clistNos(List<Integer> clistNos) {
		if (clistNos == null) {
			throw new RuntimeException("clistNos is empty ");
		}
		this.clistNos = clistNos;
		return this;
	}

	public PfileQuery cpageNo(String cpageNo) {
		if (cpageNo == null) {
			throw new RuntimeException("cpageNo is null");
		}
		this.cpageNo = cpageNo;
		return this;
	}

	public PfileQuery cpageNoLike(String cpageNoLike) {
		if (cpageNoLike == null) {
			throw new RuntimeException("cpageNo is null");
		}
		this.cpageNoLike = cpageNoLike;
		return this;
	}

	public PfileQuery cpageNos(List<String> cpageNos) {
		if (cpageNos == null) {
			throw new RuntimeException("cpageNos is empty ");
		}
		this.cpageNos = cpageNos;
		return this;
	}

	public PfileQuery vnum(String vnum) {
		if (vnum == null) {
			throw new RuntimeException("vnum is null");
		}
		this.vnum = vnum;
		return this;
	}

	public PfileQuery vnumLike(String vnumLike) {
		if (vnumLike == null) {
			throw new RuntimeException("vnum is null");
		}
		this.vnumLike = vnumLike;
		return this;
	}

	public PfileQuery vnums(List<String> vnums) {
		if (vnums == null) {
			throw new RuntimeException("vnums is empty ");
		}
		this.vnums = vnums;
		return this;
	}

	public PfileQuery cvnum(String cvnum) {
		if (cvnum == null) {
			throw new RuntimeException("cvnum is null");
		}
		this.cvnum = cvnum;
		return this;
	}

	public PfileQuery cvnumLike(String cvnumLike) {
		if (cvnumLike == null) {
			throw new RuntimeException("cvnum is null");
		}
		this.cvnumLike = cvnumLike;
		return this;
	}

	public PfileQuery cvnums(List<String> cvnums) {
		if (cvnums == null) {
			throw new RuntimeException("cvnums is empty ");
		}
		this.cvnums = cvnums;
		return this;
	}

	public PfileQuery treedListStr(String treedListStr) {
		if (treedListStr == null) {
			throw new RuntimeException("treedListStr is null");
		}
		this.treedListStr = treedListStr;
		return this;
	}

	public PfileQuery treedListStrLike(String treedListStrLike) {
		if (treedListStrLike == null) {
			throw new RuntimeException("treedListStr is null");
		}
		this.treedListStrLike = treedListStrLike;
		return this;
	}

	public PfileQuery treedListStrs(List<String> treedListStrs) {
		if (treedListStrs == null) {
			throw new RuntimeException("treedListStrs is empty ");
		}
		this.treedListStrs = treedListStrs;
		return this;
	}

	public PfileQuery ctimeEndGreaterThanOrEqual(Date ctimeEndGreaterThanOrEqual) {
		if (ctimeEndGreaterThanOrEqual == null) {
			throw new RuntimeException("ctimeEnd is null");
		}
		this.ctimeEndGreaterThanOrEqual = ctimeEndGreaterThanOrEqual;
		return this;
	}

	public PfileQuery ctimeEndLessThanOrEqual(Date ctimeEndLessThanOrEqual) {
		if (ctimeEndLessThanOrEqual == null) {
			throw new RuntimeException("ctimeEnd is null");
		}
		this.ctimeEndLessThanOrEqual = ctimeEndLessThanOrEqual;
		return this;
	}

	public PfileQuery projIndex(Integer projIndex) {
		if (projIndex == null) {
			throw new RuntimeException("projIndex is null");
		}
		this.projIndex = projIndex;
		return this;
	}

	public PfileQuery projIndexGreaterThanOrEqual(
			Integer projIndexGreaterThanOrEqual) {
		if (projIndexGreaterThanOrEqual == null) {
			throw new RuntimeException("projIndex is null");
		}
		this.projIndexGreaterThanOrEqual = projIndexGreaterThanOrEqual;
		return this;
	}

	public PfileQuery projIndexLessThanOrEqual(Integer projIndexLessThanOrEqual) {
		if (projIndexLessThanOrEqual == null) {
			throw new RuntimeException("projIndex is null");
		}
		this.projIndexLessThanOrEqual = projIndexLessThanOrEqual;
		return this;
	}

	public PfileQuery projIndexs(List<Integer> projIndexs) {
		if (projIndexs == null) {
			throw new RuntimeException("projIndexs is empty ");
		}
		this.projIndexs = projIndexs;
		return this;
	}

	public PfileQuery treeParent(Integer treeParent) {
		if (treeParent == null) {
			throw new RuntimeException("treeParent is null");
		}
		this.treeParent = treeParent;
		return this;
	}

	public PfileQuery treeParentGreaterThanOrEqual(
			Integer treeParentGreaterThanOrEqual) {
		if (treeParentGreaterThanOrEqual == null) {
			throw new RuntimeException("treeParent is null");
		}
		this.treeParentGreaterThanOrEqual = treeParentGreaterThanOrEqual;
		return this;
	}

	public PfileQuery treeParentLessThanOrEqual(
			Integer treeParentLessThanOrEqual) {
		if (treeParentLessThanOrEqual == null) {
			throw new RuntimeException("treeParent is null");
		}
		this.treeParentLessThanOrEqual = treeParentLessThanOrEqual;
		return this;
	}

	public PfileQuery treeParents(List<Integer> treeParents) {
		if (treeParents == null) {
			throw new RuntimeException("treeParents is empty ");
		}
		this.treeParents = treeParents;
		return this;
	}

	public PfileQuery treeList(Integer treeList) {
		if (treeList == null) {
			throw new RuntimeException("treeList is null");
		}
		this.treeList = treeList;
		return this;
	}

	public PfileQuery treeListGreaterThanOrEqual(
			Integer treeListGreaterThanOrEqual) {
		if (treeListGreaterThanOrEqual == null) {
			throw new RuntimeException("treeList is null");
		}
		this.treeListGreaterThanOrEqual = treeListGreaterThanOrEqual;
		return this;
	}

	public PfileQuery treeListLessThanOrEqual(Integer treeListLessThanOrEqual) {
		if (treeListLessThanOrEqual == null) {
			throw new RuntimeException("treeList is null");
		}
		this.treeListLessThanOrEqual = treeListLessThanOrEqual;
		return this;
	}

	public PfileQuery treeLists(List<Integer> treeLists) {
		if (treeLists == null) {
			throw new RuntimeException("treeLists is empty ");
		}
		this.treeLists = treeLists;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("efileNum".equals(sortColumn)) {
				orderBy = "E.EFILENUM" + a_x;
			}

			if ("listNum".equals(sortColumn)) {
				orderBy = "E.LISTNUM" + a_x;
			}

			if ("listId".equals(sortColumn)) {
				orderBy = "E.LIST_ID" + a_x;
			}

			if ("pid".equals(sortColumn)) {
				orderBy = "E.PID" + a_x;
			}

			if ("projId".equals(sortColumn)) {
				orderBy = "E.PROJID" + a_x;
			}

			if ("dwid".equals(sortColumn)) {
				orderBy = "E.DWID" + a_x;
			}

			if ("fbid".equals(sortColumn)) {
				orderBy = "E.FBID" + a_x;
			}

			if ("fxid".equals(sortColumn)) {
				orderBy = "E.FXID" + a_x;
			}

			if ("jid".equals(sortColumn)) {
				orderBy = "E.JID" + a_x;
			}

			if ("flid".equals(sortColumn)) {
				orderBy = "E.FLID" + a_x;
			}

			if ("topNode".equals(sortColumn)) {
				orderBy = "E.TOPNODE" + a_x;
			}

			if ("topNodeM".equals(sortColumn)) {
				orderBy = "E.TOPNODEM" + a_x;
			}

			if ("vid".equals(sortColumn)) {
				orderBy = "E.VID" + a_x;
			}

			if ("oldVid".equals(sortColumn)) {
				orderBy = "E.OLDVID" + a_x;
			}

			if ("visFlag".equals(sortColumn)) {
				orderBy = "E.VISFLAG" + a_x;
			}

			if ("listNo".equals(sortColumn)) {
				orderBy = "E.LISTNO" + a_x;
			}

			if ("filingFlag".equals(sortColumn)) {
				orderBy = "E.FILINGFLAG" + a_x;
			}

			if ("saveFlag".equals(sortColumn)) {
				orderBy = "E.SAVEFLAG" + a_x;
			}

			if ("openFlag".equals(sortColumn)) {
				orderBy = "E.OPENFLAG" + a_x;
			}

			if ("checkupFlag".equals(sortColumn)) {
				orderBy = "E.CHECKUPFLAG" + a_x;
			}

			if ("finishFlag".equals(sortColumn)) {
				orderBy = "E.FINISHFLAG" + a_x;
			}

			if ("fromID".equals(sortColumn)) {
				orderBy = "E.FROMID" + a_x;
			}

			if ("tname".equals(sortColumn)) {
				orderBy = "E.TNAME" + a_x;
			}

			if ("duty".equals(sortColumn)) {
				orderBy = "E.DUTY" + a_x;
			}

			if ("tagnum".equals(sortColumn)) {
				orderBy = "E.TAGNUM" + a_x;
			}

			if ("fileNum".equals(sortColumn)) {
				orderBy = "E.FILENUM" + a_x;
			}

			if ("thematic".equals(sortColumn)) {
				orderBy = "E.THEMATIC" + a_x;
			}

			if ("ctime".equals(sortColumn)) {
				orderBy = "E.CTIME" + a_x;
			}

			if ("pageNo".equals(sortColumn)) {
				orderBy = "E.PAGENO" + a_x;
			}

			if ("level".equals(sortColumn)) {
				orderBy = "E.SLEVEL" + a_x;
			}

			if ("page".equals(sortColumn)) {
				orderBy = "E.PAGE" + a_x;
			}

			if ("fileType".equals(sortColumn)) {
				orderBy = "E.FILETYPE" + a_x;
			}

			if ("fontsNum".equals(sortColumn)) {
				orderBy = "E.FONTSNUM" + a_x;
			}

			if ("saveTime".equals(sortColumn)) {
				orderBy = "E.SAVETIME" + a_x;
			}

			if ("company".equals(sortColumn)) {
				orderBy = "E.COMPANY" + a_x;
			}

			if ("year".equals(sortColumn)) {
				orderBy = "E.YEAR" + a_x;
			}

			if ("fileAtt".equals(sortColumn)) {
				orderBy = "E.FILEATT" + a_x;
			}

			if ("annotations".equals(sortColumn)) {
				orderBy = "E.ANNOTATIONS" + a_x;
			}

			if ("carrierType".equals(sortColumn)) {
				orderBy = "E.CARRIERTYPE" + a_x;
			}

			if ("summary".equals(sortColumn)) {
				orderBy = "E.SUMMARY" + a_x;
			}

			if ("ptext".equals(sortColumn)) {
				orderBy = "E.PTEXT" + a_x;
			}

			if ("carrieru".equals(sortColumn)) {
				orderBy = "E.CARRIERU" + a_x;
			}

			if ("glideNum".equals(sortColumn)) {
				orderBy = "E.GLIDENUM" + a_x;
			}

			if ("efile".equals(sortColumn)) {
				orderBy = "E.EFILE" + a_x;
			}

			if ("ftime".equals(sortColumn)) {
				orderBy = "E.FTIME" + a_x;
			}

			if ("totalNum".equals(sortColumn)) {
				orderBy = "E.TOTALNUM" + a_x;
			}

			if ("inputMan".equals(sortColumn)) {
				orderBy = "E.INPUTMAN" + a_x;
			}

			if ("etime".equals(sortColumn)) {
				orderBy = "E.ETIME" + a_x;
			}

			if ("dotNum".equals(sortColumn)) {
				orderBy = "E.DOTNUM" + a_x;
			}

			if ("picNum".equals(sortColumn)) {
				orderBy = "E.PICNUM" + a_x;
			}

			if ("recNum".equals(sortColumn)) {
				orderBy = "E.RECNUM" + a_x;
			}

			if ("total".equals(sortColumn)) {
				orderBy = "E.TOTAL" + a_x;
			}

			if ("pageType".equals(sortColumn)) {
				orderBy = "E.PAGETYPE" + a_x;
			}

			if ("isCom".equals(sortColumn)) {
				orderBy = "E.ISCOM" + a_x;
			}

			if ("isLocate".equals(sortColumn)) {
				orderBy = "E.ISLOCATE" + a_x;
			}

			if ("wcompany".equals(sortColumn)) {
				orderBy = "E.WCOMPANY" + a_x;
			}

			if ("wcompanyID".equals(sortColumn)) {
				orderBy = "E.WCOMPANYID" + a_x;
			}

			if ("sendFlag".equals(sortColumn)) {
				orderBy = "E.SENDFLAG" + a_x;
			}

			if ("lcontent".equals(sortColumn)) {
				orderBy = "E.LCONTENT" + a_x;
			}

			if ("lcompany".equals(sortColumn)) {
				orderBy = "E.LCOMPANY" + a_x;
			}

			if ("lman".equals(sortColumn)) {
				orderBy = "E.LMAN" + a_x;
			}

			if ("llen".equals(sortColumn)) {
				orderBy = "E.LLEN" + a_x;
			}

			if ("ldate".equals(sortColumn)) {
				orderBy = "E.LDATE" + a_x;
			}

			if ("jconten".equals(sortColumn)) {
				orderBy = "E.JCONTEN" + a_x;
			}

			if ("jplace".equals(sortColumn)) {
				orderBy = "E.JPLACE" + a_x;
			}

			if ("jman".equals(sortColumn)) {
				orderBy = "E.JMAN" + a_x;
			}

			if ("jback".equals(sortColumn)) {
				orderBy = "E.JBACK" + a_x;
			}

			if ("jactor".equals(sortColumn)) {
				orderBy = "E.JACTOR" + a_x;
			}

			if ("jnum".equals(sortColumn)) {
				orderBy = "E.JNUM" + a_x;
			}

			if ("jbnum".equals(sortColumn)) {
				orderBy = "E.JBNUM" + a_x;
			}

			if ("tnum".equals(sortColumn)) {
				orderBy = "E.TNUM" + a_x;
			}

			if ("tzoom".equals(sortColumn)) {
				orderBy = "E.TZOOM" + a_x;
			}

			if ("tflag".equals(sortColumn)) {
				orderBy = "E.TFLAG" + a_x;
			}

			if ("tdesigner".equals(sortColumn)) {
				orderBy = "E.TDESIGNER" + a_x;
			}

			if ("tviewer".equals(sortColumn)) {
				orderBy = "E.TVIEWER" + a_x;
			}

			if ("tassessor".equals(sortColumn)) {
				orderBy = "E.TASSESSOR" + a_x;
			}

			if ("tvision".equals(sortColumn)) {
				orderBy = "E.TVISION" + a_x;
			}

			if ("clistNo".equals(sortColumn)) {
				orderBy = "E.CLISTNO" + a_x;
			}

			if ("cpageNo".equals(sortColumn)) {
				orderBy = "E.CPAGENO" + a_x;
			}

			if ("vnum".equals(sortColumn)) {
				orderBy = "E.VNUM" + a_x;
			}

			if ("cvnum".equals(sortColumn)) {
				orderBy = "E.CVNUM" + a_x;
			}

			if ("treedListStr".equals(sortColumn)) {
				orderBy = "E.TREE_DLISTSTR" + a_x;
			}

			if ("ctimeEnd".equals(sortColumn)) {
				orderBy = "E.CTIME_END" + a_x;
			}

			if ("projIndex".equals(sortColumn)) {
				orderBy = "E.PROJ_INDEX" + a_x;
			}

			if ("treeParent".equals(sortColumn)) {
				orderBy = "E.TREE_PARENT" + a_x;
			}

			if ("treeList".equals(sortColumn)) {
				orderBy = "E.TREE_LIST" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("efileNum", "EFILENUM");
		addColumn("listNum", "LISTNUM");
		addColumn("listId", "LIST_ID");
		addColumn("pid", "PID");
		addColumn("projId", "PROJID");
		addColumn("dwid", "DWID");
		addColumn("fbid", "FBID");
		addColumn("fxid", "FXID");
		addColumn("jid", "JID");
		addColumn("flid", "FLID");
		addColumn("topNode", "TOPNODE");
		addColumn("topNodeM", "TOPNODEM");
		addColumn("vid", "VID");
		addColumn("oldVid", "OLDVID");
		addColumn("visFlag", "VISFLAG");
		addColumn("listNo", "LISTNO");
		addColumn("filingFlag", "FILINGFLAG");
		addColumn("saveFlag", "SAVEFLAG");
		addColumn("openFlag", "OPENFLAG");
		addColumn("checkupFlag", "CHECKUPFLAG");
		addColumn("finishFlag", "FINISHFLAG");
		addColumn("fromID", "FROMID");
		addColumn("tname", "TNAME");
		addColumn("duty", "DUTY");
		addColumn("tagnum", "TAGNUM");
		addColumn("fileNum", "FILENUM");
		addColumn("thematic", "THEMATIC");
		addColumn("ctime", "CTIME");
		addColumn("pageNo", "PAGENO");
		addColumn("level", "SLEVEL");
		addColumn("page", "PAGE");
		addColumn("fileType", "FILETYPE");
		addColumn("fontsNum", "FONTSNUM");
		addColumn("saveTime", "SAVETIME");
		addColumn("company", "COMPANY");
		addColumn("year", "YEAR");
		addColumn("fileAtt", "FILEATT");
		addColumn("annotations", "ANNOTATIONS");
		addColumn("carrierType", "CARRIERTYPE");
		addColumn("summary", "SUMMARY");
		addColumn("ptext", "PTEXT");
		addColumn("carrieru", "CARRIERU");
		addColumn("glideNum", "GLIDENUM");
		addColumn("efile", "EFILE");
		addColumn("ftime", "FTIME");
		addColumn("totalNum", "TOTALNUM");
		addColumn("inputMan", "INPUTMAN");
		addColumn("etime", "ETIME");
		addColumn("dotNum", "DOTNUM");
		addColumn("picNum", "PICNUM");
		addColumn("recNum", "RECNUM");
		addColumn("total", "TOTAL");
		addColumn("pageType", "PAGETYPE");
		addColumn("isCom", "ISCOM");
		addColumn("isLocate", "ISLOCATE");
		addColumn("wcompany", "WCOMPANY");
		addColumn("wcompanyID", "WCOMPANYID");
		addColumn("sendFlag", "SENDFLAG");
		addColumn("lcontent", "LCONTENT");
		addColumn("lcompany", "LCOMPANY");
		addColumn("lman", "LMAN");
		addColumn("llen", "LLEN");
		addColumn("ldate", "LDATE");
		addColumn("jconten", "JCONTEN");
		addColumn("jplace", "JPLACE");
		addColumn("jman", "JMAN");
		addColumn("jback", "JBACK");
		addColumn("jactor", "JACTOR");
		addColumn("jnum", "JNUM");
		addColumn("jbnum", "JBNUM");
		addColumn("tnum", "TNUM");
		addColumn("tzoom", "TZOOM");
		addColumn("tflag", "TFLAG");
		addColumn("tdesigner", "TDESIGNER");
		addColumn("tviewer", "TVIEWER");
		addColumn("tassessor", "TASSESSOR");
		addColumn("tvision", "TVISION");
		addColumn("clistNo", "CLISTNO");
		addColumn("cpageNo", "CPAGENO");
		addColumn("vnum", "VNUM");
		addColumn("cvnum", "CVNUM");
		addColumn("treedListStr", "TREE_DLISTSTR");
		addColumn("ctimeEnd", "CTIME_END");
		addColumn("projIndex", "PROJ_INDEX");
		addColumn("treeParent", "TREE_PARENT");
		addColumn("treeList", "TREE_LIST");
	}

}