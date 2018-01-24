package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FileDotQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> fileIDs;
	protected Collection<String> appActorIds;
	protected String listId;
	protected String listIdLike;
	protected List<String> listIds;
	protected Integer indexId;
	protected Integer indexIdGreaterThanOrEqual;
	protected Integer indexIdLessThanOrEqual;
	protected List<Integer> indexIds;
	protected String fbelong;
	protected String fbelongLike;
	protected List<String> fbelongs;
	protected String fnum;
	protected String fnumLike;
	protected List<String> fnums;
	protected String pbelong;
	protected String pbelongLike;
	protected List<String> pbelongs;
	protected String num;
	protected String numLike;
	protected List<String> nums;
	protected String fname;
	protected String fnameLike;
	protected List<String> fnames;
	protected String dotName;
	protected String dotNameLike;
	protected List<String> dotNames;
	protected Date ctimeGreaterThanOrEqual;
	protected Date ctimeLessThanOrEqual;
	protected Date dtimeGreaterThanOrEqual;
	protected Date dtimeLessThanOrEqual;
	protected String fileName;
	protected String fileNameLike;
	protected List<String> fileNames;
	protected Integer fileSize;
	protected Integer fileSizeGreaterThanOrEqual;
	protected Integer fileSizeLessThanOrEqual;
	protected List<Integer> fileSizes;
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
	protected String cman;
	protected String cmanLike;
	protected List<String> cmans;
	protected String content;
	protected String contentLike;
	protected List<String> contents;
	protected String listFlag;
	protected String listFlagLike;
	protected List<String> listFlags;
	protected Integer toFile;
	protected Integer toFileGreaterThanOrEqual;
	protected Integer toFileLessThanOrEqual;
	protected List<Integer> toFiles;
	protected String isInk;
	protected String isInkLike;
	protected List<String> isInks;
	protected Integer dotType;
	protected Integer dotTypeGreaterThanOrEqual;
	protected Integer dotTypeLessThanOrEqual;
	protected List<Integer> dotTypes;
	protected String ctimeDName;
	protected String ctimeDNameLike;
	protected List<String> ctimeDNames;
	protected Integer type;
	protected Integer typeGreaterThanOrEqual;
	protected Integer typeLessThanOrEqual;
	protected List<Integer> types;
	protected Integer listNo;
	protected Integer listNoGreaterThanOrEqual;
	protected Integer listNoLessThanOrEqual;
	protected List<Integer> listNos;
	protected Integer utreeIndex;
	protected Integer utreeIndexGreaterThanOrEqual;
	protected Integer utreeIndexLessThanOrEqual;
	protected List<Integer> utreeIndexs;
	protected String isQuan;
	protected String isQuanLike;
	protected List<String> isQuans;
	protected Integer intSysForm;
	protected Integer intSysFormGreaterThanOrEqual;
	protected Integer intSysFormLessThanOrEqual;
	protected List<Integer> intSysForms;

	public FileDotQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
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

	public String getFbelong() {
		return fbelong;
	}

	public String getFbelongLike() {
		if (fbelongLike != null && fbelongLike.trim().length() > 0) {
			if (!fbelongLike.startsWith("%")) {
				fbelongLike = "%" + fbelongLike;
			}
			if (!fbelongLike.endsWith("%")) {
				fbelongLike = fbelongLike + "%";
			}
		}
		return fbelongLike;
	}

	public List<String> getFbelongs() {
		return fbelongs;
	}

	public String getFnum() {
		return fnum;
	}

	public String getFnumLike() {
		if (fnumLike != null && fnumLike.trim().length() > 0) {
			if (!fnumLike.startsWith("%")) {
				fnumLike = "%" + fnumLike;
			}
			if (!fnumLike.endsWith("%")) {
				fnumLike = fnumLike + "%";
			}
		}
		return fnumLike;
	}

	public List<String> getFnums() {
		return fnums;
	}

	public String getPbelong() {
		return pbelong;
	}

	public String getPbelongLike() {
		if (pbelongLike != null && pbelongLike.trim().length() > 0) {
			if (!pbelongLike.startsWith("%")) {
				pbelongLike = "%" + pbelongLike;
			}
			if (!pbelongLike.endsWith("%")) {
				pbelongLike = pbelongLike + "%";
			}
		}
		return pbelongLike;
	}

	public List<String> getPbelongs() {
		return pbelongs;
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

	public String getFname() {
		return fname;
	}

	public String getFnameLike() {
		if (fnameLike != null && fnameLike.trim().length() > 0) {
			if (!fnameLike.startsWith("%")) {
				fnameLike = "%" + fnameLike;
			}
			if (!fnameLike.endsWith("%")) {
				fnameLike = fnameLike + "%";
			}
		}
		return fnameLike;
	}

	public List<String> getFnames() {
		return fnames;
	}

	public String getDotName() {
		return dotName;
	}

	public String getDotNameLike() {
		if (dotNameLike != null && dotNameLike.trim().length() > 0) {
			if (!dotNameLike.startsWith("%")) {
				dotNameLike = "%" + dotNameLike;
			}
			if (!dotNameLike.endsWith("%")) {
				dotNameLike = dotNameLike + "%";
			}
		}
		return dotNameLike;
	}

	public List<String> getDotNames() {
		return dotNames;
	}

	public Date getCtimeGreaterThanOrEqual() {
		return ctimeGreaterThanOrEqual;
	}

	public Date getCtimeLessThanOrEqual() {
		return ctimeLessThanOrEqual;
	}

	public Date getDtimeGreaterThanOrEqual() {
		return dtimeGreaterThanOrEqual;
	}

	public Date getDtimeLessThanOrEqual() {
		return dtimeLessThanOrEqual;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileNameLike() {
		if (fileNameLike != null && fileNameLike.trim().length() > 0) {
			if (!fileNameLike.startsWith("%")) {
				fileNameLike = "%" + fileNameLike;
			}
			if (!fileNameLike.endsWith("%")) {
				fileNameLike = fileNameLike + "%";
			}
		}
		return fileNameLike;
	}

	public List<String> getFileNames() {
		return fileNames;
	}

	public Integer getFileSize() {
		return fileSize;
	}

	public Integer getFileSizeGreaterThanOrEqual() {
		return fileSizeGreaterThanOrEqual;
	}

	public Integer getFileSizeLessThanOrEqual() {
		return fileSizeLessThanOrEqual;
	}

	public List<Integer> getFileSizes() {
		return fileSizes;
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

	public String getCman() {
		return cman;
	}

	public String getCmanLike() {
		if (cmanLike != null && cmanLike.trim().length() > 0) {
			if (!cmanLike.startsWith("%")) {
				cmanLike = "%" + cmanLike;
			}
			if (!cmanLike.endsWith("%")) {
				cmanLike = cmanLike + "%";
			}
		}
		return cmanLike;
	}

	public List<String> getCmans() {
		return cmans;
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

	public String getListFlag() {
		return listFlag;
	}

	public String getListFlagLike() {
		if (listFlagLike != null && listFlagLike.trim().length() > 0) {
			if (!listFlagLike.startsWith("%")) {
				listFlagLike = "%" + listFlagLike;
			}
			if (!listFlagLike.endsWith("%")) {
				listFlagLike = listFlagLike + "%";
			}
		}
		return listFlagLike;
	}

	public List<String> getListFlags() {
		return listFlags;
	}

	public Integer getToFile() {
		return toFile;
	}

	public Integer getToFileGreaterThanOrEqual() {
		return toFileGreaterThanOrEqual;
	}

	public Integer getToFileLessThanOrEqual() {
		return toFileLessThanOrEqual;
	}

	public List<Integer> getToFiles() {
		return toFiles;
	}

	public String getIsInk() {
		return isInk;
	}

	public String getIsInkLike() {
		if (isInkLike != null && isInkLike.trim().length() > 0) {
			if (!isInkLike.startsWith("%")) {
				isInkLike = "%" + isInkLike;
			}
			if (!isInkLike.endsWith("%")) {
				isInkLike = isInkLike + "%";
			}
		}
		return isInkLike;
	}

	public List<String> getIsInks() {
		return isInks;
	}

	public Integer getDotType() {
		return dotType;
	}

	public Integer getDotTypeGreaterThanOrEqual() {
		return dotTypeGreaterThanOrEqual;
	}

	public Integer getDotTypeLessThanOrEqual() {
		return dotTypeLessThanOrEqual;
	}

	public List<Integer> getDotTypes() {
		return dotTypes;
	}

	public String getCtimeDName() {
		return ctimeDName;
	}

	public String getCtimeDNameLike() {
		if (ctimeDNameLike != null && ctimeDNameLike.trim().length() > 0) {
			if (!ctimeDNameLike.startsWith("%")) {
				ctimeDNameLike = "%" + ctimeDNameLike;
			}
			if (!ctimeDNameLike.endsWith("%")) {
				ctimeDNameLike = ctimeDNameLike + "%";
			}
		}
		return ctimeDNameLike;
	}

	public List<String> getCtimeDNames() {
		return ctimeDNames;
	}

	public Integer getType() {
		return type;
	}

	public Integer getTypeGreaterThanOrEqual() {
		return typeGreaterThanOrEqual;
	}

	public Integer getTypeLessThanOrEqual() {
		return typeLessThanOrEqual;
	}

	public List<Integer> getTypes() {
		return types;
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

	public Integer getUtreeIndex() {
		return utreeIndex;
	}

	public Integer getUtreeIndexGreaterThanOrEqual() {
		return utreeIndexGreaterThanOrEqual;
	}

	public Integer getUtreeIndexLessThanOrEqual() {
		return utreeIndexLessThanOrEqual;
	}

	public List<Integer> getUtreeIndexs() {
		return utreeIndexs;
	}

	public String getIsQuan() {
		return isQuan;
	}

	public String getIsQuanLike() {
		if (isQuanLike != null && isQuanLike.trim().length() > 0) {
			if (!isQuanLike.startsWith("%")) {
				isQuanLike = "%" + isQuanLike;
			}
			if (!isQuanLike.endsWith("%")) {
				isQuanLike = isQuanLike + "%";
			}
		}
		return isQuanLike;
	}

	public List<String> getIsQuans() {
		return isQuans;
	}

	public Integer getIntSysForm() {
		return intSysForm;
	}

	public Integer getIntSysFormGreaterThanOrEqual() {
		return intSysFormGreaterThanOrEqual;
	}

	public Integer getIntSysFormLessThanOrEqual() {
		return intSysFormLessThanOrEqual;
	}

	public List<Integer> getIntSysForms() {
		return intSysForms;
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

	public void setFbelong(String fbelong) {
		this.fbelong = fbelong;
	}

	public void setFbelongLike(String fbelongLike) {
		this.fbelongLike = fbelongLike;
	}

	public void setFbelongs(List<String> fbelongs) {
		this.fbelongs = fbelongs;
	}

	public void setFnum(String fnum) {
		this.fnum = fnum;
	}

	public void setFnumLike(String fnumLike) {
		this.fnumLike = fnumLike;
	}

	public void setFnums(List<String> fnums) {
		this.fnums = fnums;
	}

	public void setPbelong(String pbelong) {
		this.pbelong = pbelong;
	}

	public void setPbelongLike(String pbelongLike) {
		this.pbelongLike = pbelongLike;
	}

	public void setPbelongs(List<String> pbelongs) {
		this.pbelongs = pbelongs;
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

	public void setFname(String fname) {
		this.fname = fname;
	}

	public void setFnameLike(String fnameLike) {
		this.fnameLike = fnameLike;
	}

	public void setFnames(List<String> fnames) {
		this.fnames = fnames;
	}

	public void setDotName(String dotName) {
		this.dotName = dotName;
	}

	public void setDotNameLike(String dotNameLike) {
		this.dotNameLike = dotNameLike;
	}

	public void setDotNames(List<String> dotNames) {
		this.dotNames = dotNames;
	}

	public void setCtimeGreaterThanOrEqual(Date ctimeGreaterThanOrEqual) {
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
	}

	public void setCtimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
	}

	public void setDtimeGreaterThanOrEqual(Date dtimeGreaterThanOrEqual) {
		this.dtimeGreaterThanOrEqual = dtimeGreaterThanOrEqual;
	}

	public void setDtimeLessThanOrEqual(Date dtimeLessThanOrEqual) {
		this.dtimeLessThanOrEqual = dtimeLessThanOrEqual;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileNameLike(String fileNameLike) {
		this.fileNameLike = fileNameLike;
	}

	public void setFileNames(List<String> fileNames) {
		this.fileNames = fileNames;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public void setFileSizeGreaterThanOrEqual(Integer fileSizeGreaterThanOrEqual) {
		this.fileSizeGreaterThanOrEqual = fileSizeGreaterThanOrEqual;
	}

	public void setFileSizeLessThanOrEqual(Integer fileSizeLessThanOrEqual) {
		this.fileSizeLessThanOrEqual = fileSizeLessThanOrEqual;
	}

	public void setFileSizes(List<Integer> fileSizes) {
		this.fileSizes = fileSizes;
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

	public void setCman(String cman) {
		this.cman = cman;
	}

	public void setCmanLike(String cmanLike) {
		this.cmanLike = cmanLike;
	}

	public void setCmans(List<String> cmans) {
		this.cmans = cmans;
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

	public void setListFlag(String listFlag) {
		this.listFlag = listFlag;
	}

	public void setListFlagLike(String listFlagLike) {
		this.listFlagLike = listFlagLike;
	}

	public void setListFlags(List<String> listFlags) {
		this.listFlags = listFlags;
	}

	public void setToFile(Integer toFile) {
		this.toFile = toFile;
	}

	public void setToFileGreaterThanOrEqual(Integer toFileGreaterThanOrEqual) {
		this.toFileGreaterThanOrEqual = toFileGreaterThanOrEqual;
	}

	public void setToFileLessThanOrEqual(Integer toFileLessThanOrEqual) {
		this.toFileLessThanOrEqual = toFileLessThanOrEqual;
	}

	public void setToFiles(List<Integer> toFiles) {
		this.toFiles = toFiles;
	}

	public void setIsInk(String isInk) {
		this.isInk = isInk;
	}

	public void setIsInkLike(String isInkLike) {
		this.isInkLike = isInkLike;
	}

	public void setIsInks(List<String> isInks) {
		this.isInks = isInks;
	}

	public void setDotType(Integer dotType) {
		this.dotType = dotType;
	}

	public void setDotTypeGreaterThanOrEqual(Integer dotTypeGreaterThanOrEqual) {
		this.dotTypeGreaterThanOrEqual = dotTypeGreaterThanOrEqual;
	}

	public void setDotTypeLessThanOrEqual(Integer dotTypeLessThanOrEqual) {
		this.dotTypeLessThanOrEqual = dotTypeLessThanOrEqual;
	}

	public void setDotTypes(List<Integer> dotTypes) {
		this.dotTypes = dotTypes;
	}

	public void setCtimeDName(String ctimeDName) {
		this.ctimeDName = ctimeDName;
	}

	public void setCtimeDNameLike(String ctimeDNameLike) {
		this.ctimeDNameLike = ctimeDNameLike;
	}

	public void setCtimeDNames(List<String> ctimeDNames) {
		this.ctimeDNames = ctimeDNames;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setTypeGreaterThanOrEqual(Integer typeGreaterThanOrEqual) {
		this.typeGreaterThanOrEqual = typeGreaterThanOrEqual;
	}

	public void setTypeLessThanOrEqual(Integer typeLessThanOrEqual) {
		this.typeLessThanOrEqual = typeLessThanOrEqual;
	}

	public void setTypes(List<Integer> types) {
		this.types = types;
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

	public void setUtreeIndex(Integer utreeIndex) {
		this.utreeIndex = utreeIndex;
	}

	public void setUtreeIndexGreaterThanOrEqual(
			Integer utreeIndexGreaterThanOrEqual) {
		this.utreeIndexGreaterThanOrEqual = utreeIndexGreaterThanOrEqual;
	}

	public void setUtreeIndexLessThanOrEqual(Integer utreeIndexLessThanOrEqual) {
		this.utreeIndexLessThanOrEqual = utreeIndexLessThanOrEqual;
	}

	public void setUtreeIndexs(List<Integer> utreeIndexs) {
		this.utreeIndexs = utreeIndexs;
	}

	public void setIsQuan(String isQuan) {
		this.isQuan = isQuan;
	}

	public void setIsQuanLike(String isQuanLike) {
		this.isQuanLike = isQuanLike;
	}

	public void setIsQuans(List<String> isQuans) {
		this.isQuans = isQuans;
	}

	public void setIntSysForm(Integer intSysForm) {
		this.intSysForm = intSysForm;
	}

	public void setIntSysFormGreaterThanOrEqual(
			Integer intSysFormGreaterThanOrEqual) {
		this.intSysFormGreaterThanOrEqual = intSysFormGreaterThanOrEqual;
	}

	public void setIntSysFormLessThanOrEqual(Integer intSysFormLessThanOrEqual) {
		this.intSysFormLessThanOrEqual = intSysFormLessThanOrEqual;
	}

	public void setIntSysForms(List<Integer> intSysForms) {
		this.intSysForms = intSysForms;
	}

	public FileDotQuery listId(String listId) {
		if (listId == null) {
			throw new RuntimeException("listId is null");
		}
		this.listId = listId;
		return this;
	}

	public FileDotQuery listIdLike(String listIdLike) {
		if (listIdLike == null) {
			throw new RuntimeException("listId is null");
		}
		this.listIdLike = listIdLike;
		return this;
	}

	public FileDotQuery listIds(List<String> listIds) {
		if (listIds == null) {
			throw new RuntimeException("listIds is empty ");
		}
		this.listIds = listIds;
		return this;
	}

	public FileDotQuery indexId(Integer indexId) {
		if (indexId == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexId = indexId;
		return this;
	}

	public FileDotQuery indexIdGreaterThanOrEqual(
			Integer indexIdGreaterThanOrEqual) {
		if (indexIdGreaterThanOrEqual == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexIdGreaterThanOrEqual = indexIdGreaterThanOrEqual;
		return this;
	}

	public FileDotQuery indexIdLessThanOrEqual(Integer indexIdLessThanOrEqual) {
		if (indexIdLessThanOrEqual == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexIdLessThanOrEqual = indexIdLessThanOrEqual;
		return this;
	}

	public FileDotQuery indexIds(List<Integer> indexIds) {
		if (indexIds == null) {
			throw new RuntimeException("indexIds is empty ");
		}
		this.indexIds = indexIds;
		return this;
	}

	public FileDotQuery fbelong(String fbelong) {
		if (fbelong == null) {
			throw new RuntimeException("fbelong is null");
		}
		this.fbelong = fbelong;
		return this;
	}

	public FileDotQuery fbelongLike(String fbelongLike) {
		if (fbelongLike == null) {
			throw new RuntimeException("fbelong is null");
		}
		this.fbelongLike = fbelongLike;
		return this;
	}

	public FileDotQuery fbelongs(List<String> fbelongs) {
		if (fbelongs == null) {
			throw new RuntimeException("fbelongs is empty ");
		}
		this.fbelongs = fbelongs;
		return this;
	}

	public FileDotQuery fnum(String fnum) {
		if (fnum == null) {
			throw new RuntimeException("fnum is null");
		}
		this.fnum = fnum;
		return this;
	}

	public FileDotQuery fnumLike(String fnumLike) {
		if (fnumLike == null) {
			throw new RuntimeException("fnum is null");
		}
		this.fnumLike = fnumLike;
		return this;
	}

	public FileDotQuery fnums(List<String> fnums) {
		if (fnums == null) {
			throw new RuntimeException("fnums is empty ");
		}
		this.fnums = fnums;
		return this;
	}

	public FileDotQuery pbelong(String pbelong) {
		if (pbelong == null) {
			throw new RuntimeException("pbelong is null");
		}
		this.pbelong = pbelong;
		return this;
	}

	public FileDotQuery pbelongLike(String pbelongLike) {
		if (pbelongLike == null) {
			throw new RuntimeException("pbelong is null");
		}
		this.pbelongLike = pbelongLike;
		return this;
	}

	public FileDotQuery pbelongs(List<String> pbelongs) {
		if (pbelongs == null) {
			throw new RuntimeException("pbelongs is empty ");
		}
		this.pbelongs = pbelongs;
		return this;
	}

	public FileDotQuery num(String num) {
		if (num == null) {
			throw new RuntimeException("num is null");
		}
		this.num = num;
		return this;
	}

	public FileDotQuery numLike(String numLike) {
		if (numLike == null) {
			throw new RuntimeException("num is null");
		}
		this.numLike = numLike;
		return this;
	}

	public FileDotQuery nums(List<String> nums) {
		if (nums == null) {
			throw new RuntimeException("nums is empty ");
		}
		this.nums = nums;
		return this;
	}

	public FileDotQuery fname(String fname) {
		if (fname == null) {
			throw new RuntimeException("fname is null");
		}
		this.fname = fname;
		return this;
	}

	public FileDotQuery fnameLike(String fnameLike) {
		if (fnameLike == null) {
			throw new RuntimeException("fname is null");
		}
		this.fnameLike = fnameLike;
		return this;
	}

	public FileDotQuery fnames(List<String> fnames) {
		if (fnames == null) {
			throw new RuntimeException("fnames is empty ");
		}
		this.fnames = fnames;
		return this;
	}

	public FileDotQuery dotName(String dotName) {
		if (dotName == null) {
			throw new RuntimeException("dotName is null");
		}
		this.dotName = dotName;
		return this;
	}

	public FileDotQuery dotNameLike(String dotNameLike) {
		if (dotNameLike == null) {
			throw new RuntimeException("dotName is null");
		}
		this.dotNameLike = dotNameLike;
		return this;
	}

	public FileDotQuery dotNames(List<String> dotNames) {
		if (dotNames == null) {
			throw new RuntimeException("dotNames is empty ");
		}
		this.dotNames = dotNames;
		return this;
	}

	public FileDotQuery ctimeGreaterThanOrEqual(Date ctimeGreaterThanOrEqual) {
		if (ctimeGreaterThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
		return this;
	}

	public FileDotQuery ctimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		if (ctimeLessThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
		return this;
	}

	public FileDotQuery dtimeGreaterThanOrEqual(Date dtimeGreaterThanOrEqual) {
		if (dtimeGreaterThanOrEqual == null) {
			throw new RuntimeException("dtime is null");
		}
		this.dtimeGreaterThanOrEqual = dtimeGreaterThanOrEqual;
		return this;
	}

	public FileDotQuery dtimeLessThanOrEqual(Date dtimeLessThanOrEqual) {
		if (dtimeLessThanOrEqual == null) {
			throw new RuntimeException("dtime is null");
		}
		this.dtimeLessThanOrEqual = dtimeLessThanOrEqual;
		return this;
	}

	public FileDotQuery fileName(String fileName) {
		if (fileName == null) {
			throw new RuntimeException("fileName is null");
		}
		this.fileName = fileName;
		return this;
	}

	public FileDotQuery fileNameLike(String fileNameLike) {
		if (fileNameLike == null) {
			throw new RuntimeException("fileName is null");
		}
		this.fileNameLike = fileNameLike;
		return this;
	}

	public FileDotQuery fileNames(List<String> fileNames) {
		if (fileNames == null) {
			throw new RuntimeException("fileNames is empty ");
		}
		this.fileNames = fileNames;
		return this;
	}

	public FileDotQuery fileSize(Integer fileSize) {
		if (fileSize == null) {
			throw new RuntimeException("fileSize is null");
		}
		this.fileSize = fileSize;
		return this;
	}

	public FileDotQuery fileSizeGreaterThanOrEqual(
			Integer fileSizeGreaterThanOrEqual) {
		if (fileSizeGreaterThanOrEqual == null) {
			throw new RuntimeException("fileSize is null");
		}
		this.fileSizeGreaterThanOrEqual = fileSizeGreaterThanOrEqual;
		return this;
	}

	public FileDotQuery fileSizeLessThanOrEqual(Integer fileSizeLessThanOrEqual) {
		if (fileSizeLessThanOrEqual == null) {
			throw new RuntimeException("fileSize is null");
		}
		this.fileSizeLessThanOrEqual = fileSizeLessThanOrEqual;
		return this;
	}

	public FileDotQuery fileSizes(List<Integer> fileSizes) {
		if (fileSizes == null) {
			throw new RuntimeException("fileSizes is empty ");
		}
		this.fileSizes = fileSizes;
		return this;
	}

	public FileDotQuery dwid(Integer dwid) {
		if (dwid == null) {
			throw new RuntimeException("dwid is null");
		}
		this.dwid = dwid;
		return this;
	}

	public FileDotQuery dwidGreaterThanOrEqual(Integer dwidGreaterThanOrEqual) {
		if (dwidGreaterThanOrEqual == null) {
			throw new RuntimeException("dwid is null");
		}
		this.dwidGreaterThanOrEqual = dwidGreaterThanOrEqual;
		return this;
	}

	public FileDotQuery dwidLessThanOrEqual(Integer dwidLessThanOrEqual) {
		if (dwidLessThanOrEqual == null) {
			throw new RuntimeException("dwid is null");
		}
		this.dwidLessThanOrEqual = dwidLessThanOrEqual;
		return this;
	}

	public FileDotQuery dwids(List<Integer> dwids) {
		if (dwids == null) {
			throw new RuntimeException("dwids is empty ");
		}
		this.dwids = dwids;
		return this;
	}

	public FileDotQuery fbid(Integer fbid) {
		if (fbid == null) {
			throw new RuntimeException("fbid is null");
		}
		this.fbid = fbid;
		return this;
	}

	public FileDotQuery fbidGreaterThanOrEqual(Integer fbidGreaterThanOrEqual) {
		if (fbidGreaterThanOrEqual == null) {
			throw new RuntimeException("fbid is null");
		}
		this.fbidGreaterThanOrEqual = fbidGreaterThanOrEqual;
		return this;
	}

	public FileDotQuery fbidLessThanOrEqual(Integer fbidLessThanOrEqual) {
		if (fbidLessThanOrEqual == null) {
			throw new RuntimeException("fbid is null");
		}
		this.fbidLessThanOrEqual = fbidLessThanOrEqual;
		return this;
	}

	public FileDotQuery fbids(List<Integer> fbids) {
		if (fbids == null) {
			throw new RuntimeException("fbids is empty ");
		}
		this.fbids = fbids;
		return this;
	}

	public FileDotQuery fxid(Integer fxid) {
		if (fxid == null) {
			throw new RuntimeException("fxid is null");
		}
		this.fxid = fxid;
		return this;
	}

	public FileDotQuery fxidGreaterThanOrEqual(Integer fxidGreaterThanOrEqual) {
		if (fxidGreaterThanOrEqual == null) {
			throw new RuntimeException("fxid is null");
		}
		this.fxidGreaterThanOrEqual = fxidGreaterThanOrEqual;
		return this;
	}

	public FileDotQuery fxidLessThanOrEqual(Integer fxidLessThanOrEqual) {
		if (fxidLessThanOrEqual == null) {
			throw new RuntimeException("fxid is null");
		}
		this.fxidLessThanOrEqual = fxidLessThanOrEqual;
		return this;
	}

	public FileDotQuery fxids(List<Integer> fxids) {
		if (fxids == null) {
			throw new RuntimeException("fxids is empty ");
		}
		this.fxids = fxids;
		return this;
	}

	public FileDotQuery jid(String jid) {
		if (jid == null) {
			throw new RuntimeException("jid is null");
		}
		this.jid = jid;
		return this;
	}

	public FileDotQuery jidLike(String jidLike) {
		if (jidLike == null) {
			throw new RuntimeException("jid is null");
		}
		this.jidLike = jidLike;
		return this;
	}

	public FileDotQuery jids(List<String> jids) {
		if (jids == null) {
			throw new RuntimeException("jids is empty ");
		}
		this.jids = jids;
		return this;
	}

	public FileDotQuery flid(String flid) {
		if (flid == null) {
			throw new RuntimeException("flid is null");
		}
		this.flid = flid;
		return this;
	}

	public FileDotQuery flidLike(String flidLike) {
		if (flidLike == null) {
			throw new RuntimeException("flid is null");
		}
		this.flidLike = flidLike;
		return this;
	}

	public FileDotQuery flids(List<String> flids) {
		if (flids == null) {
			throw new RuntimeException("flids is empty ");
		}
		this.flids = flids;
		return this;
	}

	public FileDotQuery topNode(String topNode) {
		if (topNode == null) {
			throw new RuntimeException("topNode is null");
		}
		this.topNode = topNode;
		return this;
	}

	public FileDotQuery topNodeLike(String topNodeLike) {
		if (topNodeLike == null) {
			throw new RuntimeException("topNode is null");
		}
		this.topNodeLike = topNodeLike;
		return this;
	}

	public FileDotQuery topNodes(List<String> topNodes) {
		if (topNodes == null) {
			throw new RuntimeException("topNodes is empty ");
		}
		this.topNodes = topNodes;
		return this;
	}

	public FileDotQuery cman(String cman) {
		if (cman == null) {
			throw new RuntimeException("cman is null");
		}
		this.cman = cman;
		return this;
	}

	public FileDotQuery cmanLike(String cmanLike) {
		if (cmanLike == null) {
			throw new RuntimeException("cman is null");
		}
		this.cmanLike = cmanLike;
		return this;
	}

	public FileDotQuery cmans(List<String> cmans) {
		if (cmans == null) {
			throw new RuntimeException("cmans is empty ");
		}
		this.cmans = cmans;
		return this;
	}

	public FileDotQuery content(String content) {
		if (content == null) {
			throw new RuntimeException("content is null");
		}
		this.content = content;
		return this;
	}

	public FileDotQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public FileDotQuery contents(List<String> contents) {
		if (contents == null) {
			throw new RuntimeException("contents is empty ");
		}
		this.contents = contents;
		return this;
	}

	public FileDotQuery listFlag(String listFlag) {
		if (listFlag == null) {
			throw new RuntimeException("listFlag is null");
		}
		this.listFlag = listFlag;
		return this;
	}

	public FileDotQuery listFlagLike(String listFlagLike) {
		if (listFlagLike == null) {
			throw new RuntimeException("listFlag is null");
		}
		this.listFlagLike = listFlagLike;
		return this;
	}

	public FileDotQuery listFlags(List<String> listFlags) {
		if (listFlags == null) {
			throw new RuntimeException("listFlags is empty ");
		}
		this.listFlags = listFlags;
		return this;
	}

	public FileDotQuery toFile(Integer toFile) {
		if (toFile == null) {
			throw new RuntimeException("toFile is null");
		}
		this.toFile = toFile;
		return this;
	}

	public FileDotQuery toFileGreaterThanOrEqual(
			Integer toFileGreaterThanOrEqual) {
		if (toFileGreaterThanOrEqual == null) {
			throw new RuntimeException("toFile is null");
		}
		this.toFileGreaterThanOrEqual = toFileGreaterThanOrEqual;
		return this;
	}

	public FileDotQuery toFileLessThanOrEqual(Integer toFileLessThanOrEqual) {
		if (toFileLessThanOrEqual == null) {
			throw new RuntimeException("toFile is null");
		}
		this.toFileLessThanOrEqual = toFileLessThanOrEqual;
		return this;
	}

	public FileDotQuery toFiles(List<Integer> toFiles) {
		if (toFiles == null) {
			throw new RuntimeException("toFiles is empty ");
		}
		this.toFiles = toFiles;
		return this;
	}

	public FileDotQuery isInk(String isInk) {
		if (isInk == null) {
			throw new RuntimeException("isInk is null");
		}
		this.isInk = isInk;
		return this;
	}

	public FileDotQuery isInkLike(String isInkLike) {
		if (isInkLike == null) {
			throw new RuntimeException("isInk is null");
		}
		this.isInkLike = isInkLike;
		return this;
	}

	public FileDotQuery isInks(List<String> isInks) {
		if (isInks == null) {
			throw new RuntimeException("isInks is empty ");
		}
		this.isInks = isInks;
		return this;
	}

	public FileDotQuery dotType(Integer dotType) {
		if (dotType == null) {
			throw new RuntimeException("dotType is null");
		}
		this.dotType = dotType;
		return this;
	}

	public FileDotQuery dotTypeGreaterThanOrEqual(
			Integer dotTypeGreaterThanOrEqual) {
		if (dotTypeGreaterThanOrEqual == null) {
			throw new RuntimeException("dotType is null");
		}
		this.dotTypeGreaterThanOrEqual = dotTypeGreaterThanOrEqual;
		return this;
	}

	public FileDotQuery dotTypeLessThanOrEqual(Integer dotTypeLessThanOrEqual) {
		if (dotTypeLessThanOrEqual == null) {
			throw new RuntimeException("dotType is null");
		}
		this.dotTypeLessThanOrEqual = dotTypeLessThanOrEqual;
		return this;
	}

	public FileDotQuery dotTypes(List<Integer> dotTypes) {
		if (dotTypes == null) {
			throw new RuntimeException("dotTypes is empty ");
		}
		this.dotTypes = dotTypes;
		return this;
	}

	public FileDotQuery ctimeDName(String ctimeDName) {
		if (ctimeDName == null) {
			throw new RuntimeException("ctimeDName is null");
		}
		this.ctimeDName = ctimeDName;
		return this;
	}

	public FileDotQuery ctimeDNameLike(String ctimeDNameLike) {
		if (ctimeDNameLike == null) {
			throw new RuntimeException("ctimeDName is null");
		}
		this.ctimeDNameLike = ctimeDNameLike;
		return this;
	}

	public FileDotQuery ctimeDNames(List<String> ctimeDNames) {
		if (ctimeDNames == null) {
			throw new RuntimeException("ctimeDNames is empty ");
		}
		this.ctimeDNames = ctimeDNames;
		return this;
	}

	public FileDotQuery type(Integer type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public FileDotQuery typeGreaterThanOrEqual(Integer typeGreaterThanOrEqual) {
		if (typeGreaterThanOrEqual == null) {
			throw new RuntimeException("type is null");
		}
		this.typeGreaterThanOrEqual = typeGreaterThanOrEqual;
		return this;
	}

	public FileDotQuery typeLessThanOrEqual(Integer typeLessThanOrEqual) {
		if (typeLessThanOrEqual == null) {
			throw new RuntimeException("type is null");
		}
		this.typeLessThanOrEqual = typeLessThanOrEqual;
		return this;
	}

	public FileDotQuery types(List<Integer> types) {
		if (types == null) {
			throw new RuntimeException("types is empty ");
		}
		this.types = types;
		return this;
	}

	public FileDotQuery listNo(Integer listNo) {
		if (listNo == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNo = listNo;
		return this;
	}

	public FileDotQuery listNoGreaterThanOrEqual(
			Integer listNoGreaterThanOrEqual) {
		if (listNoGreaterThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoGreaterThanOrEqual = listNoGreaterThanOrEqual;
		return this;
	}

	public FileDotQuery listNoLessThanOrEqual(Integer listNoLessThanOrEqual) {
		if (listNoLessThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoLessThanOrEqual = listNoLessThanOrEqual;
		return this;
	}

	public FileDotQuery listNos(List<Integer> listNos) {
		if (listNos == null) {
			throw new RuntimeException("listNos is empty ");
		}
		this.listNos = listNos;
		return this;
	}

	public FileDotQuery utreeIndex(Integer utreeIndex) {
		if (utreeIndex == null) {
			throw new RuntimeException("utreeIndex is null");
		}
		this.utreeIndex = utreeIndex;
		return this;
	}

	public FileDotQuery utreeIndexGreaterThanOrEqual(
			Integer utreeIndexGreaterThanOrEqual) {
		if (utreeIndexGreaterThanOrEqual == null) {
			throw new RuntimeException("utreeIndex is null");
		}
		this.utreeIndexGreaterThanOrEqual = utreeIndexGreaterThanOrEqual;
		return this;
	}

	public FileDotQuery utreeIndexLessThanOrEqual(
			Integer utreeIndexLessThanOrEqual) {
		if (utreeIndexLessThanOrEqual == null) {
			throw new RuntimeException("utreeIndex is null");
		}
		this.utreeIndexLessThanOrEqual = utreeIndexLessThanOrEqual;
		return this;
	}

	public FileDotQuery utreeIndexs(List<Integer> utreeIndexs) {
		if (utreeIndexs == null) {
			throw new RuntimeException("utreeIndexs is empty ");
		}
		this.utreeIndexs = utreeIndexs;
		return this;
	}

	public FileDotQuery isQuan(String isQuan) {
		if (isQuan == null) {
			throw new RuntimeException("isQuan is null");
		}
		this.isQuan = isQuan;
		return this;
	}

	public FileDotQuery isQuanLike(String isQuanLike) {
		if (isQuanLike == null) {
			throw new RuntimeException("isQuan is null");
		}
		this.isQuanLike = isQuanLike;
		return this;
	}

	public FileDotQuery isQuans(List<String> isQuans) {
		if (isQuans == null) {
			throw new RuntimeException("isQuans is empty ");
		}
		this.isQuans = isQuans;
		return this;
	}

	public FileDotQuery intSysForm(Integer intSysForm) {
		if (intSysForm == null) {
			throw new RuntimeException("intSysForm is null");
		}
		this.intSysForm = intSysForm;
		return this;
	}

	public FileDotQuery intSysFormGreaterThanOrEqual(
			Integer intSysFormGreaterThanOrEqual) {
		if (intSysFormGreaterThanOrEqual == null) {
			throw new RuntimeException("intSysForm is null");
		}
		this.intSysFormGreaterThanOrEqual = intSysFormGreaterThanOrEqual;
		return this;
	}

	public FileDotQuery intSysFormLessThanOrEqual(
			Integer intSysFormLessThanOrEqual) {
		if (intSysFormLessThanOrEqual == null) {
			throw new RuntimeException("intSysForm is null");
		}
		this.intSysFormLessThanOrEqual = intSysFormLessThanOrEqual;
		return this;
	}

	public FileDotQuery intSysForms(List<Integer> intSysForms) {
		if (intSysForms == null) {
			throw new RuntimeException("intSysForms is empty ");
		}
		this.intSysForms = intSysForms;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("listId".equals(sortColumn)) {
				orderBy = "E.LISTID" + a_x;
			}

			if ("indexId".equals(sortColumn)) {
				orderBy = "E.INDEXID" + a_x;
			}

			if ("fbelong".equals(sortColumn)) {
				orderBy = "E.FBELONG" + a_x;
			}

			if ("fnum".equals(sortColumn)) {
				orderBy = "E.FNUM" + a_x;
			}

			if ("pbelong".equals(sortColumn)) {
				orderBy = "E.PBELONG" + a_x;
			}

			if ("num".equals(sortColumn)) {
				orderBy = "E.NUM" + a_x;
			}

			if ("fname".equals(sortColumn)) {
				orderBy = "E.FNAME" + a_x;
			}

			if ("dotName".equals(sortColumn)) {
				orderBy = "E.DOTNAME" + a_x;
			}

			if ("ctime".equals(sortColumn)) {
				orderBy = "E.CTIME" + a_x;
			}

			if ("dtime".equals(sortColumn)) {
				orderBy = "E.DTIME" + a_x;
			}

			if ("fileName".equals(sortColumn)) {
				orderBy = "E.FILE_NAME" + a_x;
			}

			if ("fileContent".equals(sortColumn)) {
				orderBy = "E.FILE_CONTENT" + a_x;
			}

			if ("fileSize".equals(sortColumn)) {
				orderBy = "E.FILESIZE" + a_x;
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

			if ("cman".equals(sortColumn)) {
				orderBy = "E.CMAN" + a_x;
			}

			if ("content".equals(sortColumn)) {
				orderBy = "E.CONTENT" + a_x;
			}

			if ("listFlag".equals(sortColumn)) {
				orderBy = "E.LISTFLAG" + a_x;
			}

			if ("toFile".equals(sortColumn)) {
				orderBy = "E.TOFILE" + a_x;
			}

			if ("isInk".equals(sortColumn)) {
				orderBy = "E.ISINK" + a_x;
			}

			if ("dotType".equals(sortColumn)) {
				orderBy = "E.DOTTYPE" + a_x;
			}

			if ("ctimeDName".equals(sortColumn)) {
				orderBy = "E.CTIMEDNAME" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE" + a_x;
			}

			if ("listNo".equals(sortColumn)) {
				orderBy = "E.LISTNO" + a_x;
			}

			if ("utreeIndex".equals(sortColumn)) {
				orderBy = "E.UTREE_INDEX" + a_x;
			}

			if ("isQuan".equals(sortColumn)) {
				orderBy = "E.ISQUAN" + a_x;
			}

			if ("intSysForm".equals(sortColumn)) {
				orderBy = "E.INTSYSFORM" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("fileID", "FILEID");
		addColumn("listId", "LISTID");
		addColumn("indexId", "INDEXID");
		addColumn("fbelong", "FBELONG");
		addColumn("fnum", "FNUM");
		addColumn("pbelong", "PBELONG");
		addColumn("num", "NUM");
		addColumn("fname", "FNAME");
		addColumn("dotName", "DOTNAME");
		addColumn("ctime", "CTIME");
		addColumn("dtime", "DTIME");
		addColumn("fileName", "FILE_NAME");
		addColumn("fileContent", "FILE_CONTENT");
		addColumn("fileSize", "FILESIZE");
		addColumn("dwid", "DWID");
		addColumn("fbid", "FBID");
		addColumn("fxid", "FXID");
		addColumn("jid", "JID");
		addColumn("flid", "FLID");
		addColumn("topNode", "TOPNODE");
		addColumn("cman", "CMAN");
		addColumn("content", "CONTENT");
		addColumn("listFlag", "LISTFLAG");
		addColumn("toFile", "TOFILE");
		addColumn("isInk", "ISINK");
		addColumn("dotType", "DOTTYPE");
		addColumn("ctimeDName", "CTIMEDNAME");
		addColumn("type", "TYPE");
		addColumn("listNo", "LISTNO");
		addColumn("utreeIndex", "UTREE_INDEX");
		addColumn("isQuan", "ISQUAN");
		addColumn("intSysForm", "INTSYSFORM");
	}

}