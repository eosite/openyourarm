package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FileAttQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> fileIDs;
	protected Collection<String> appActorIds;
	protected String topId;
	protected String topIdLike;
	protected List<String> topIds;
	protected String num;
	protected String numLike;
	protected List<String> nums;
	protected String actor;
	protected String actorLike;
	protected List<String> actors;
	protected String fname;
	protected String fnameLike;
	protected List<String> fnames;
	protected String fileName;
	protected String fileNameLike;
	protected List<String> fileNames;
	protected String fileContent;
	protected String fileContentLike;
	protected List<String> fileContents;
	protected String vision;
	protected String visionLike;
	protected List<String> visions;
	protected Date ctimeGreaterThanOrEqual;
	protected Date ctimeLessThanOrEqual;
	protected Integer fileSize;
	protected Integer fileSizeGreaterThanOrEqual;
	protected Integer fileSizeLessThanOrEqual;
	protected List<Integer> fileSizes;
	protected String visID;
	protected String visIDLike;
	protected List<String> visIDs;
	protected String attID;
	protected String attIDLike;
	protected List<String> attIDs;
	protected Integer isTextContent;
	protected Integer isTextContentGreaterThanOrEqual;
	protected Integer isTextContentLessThanOrEqual;
	protected List<Integer> isTextContents;
	protected String textContent;
	protected String textContentLike;
	protected List<String> textContents;

	public FileAttQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getTopId() {
		return topId;
	}

	public String getTopIdLike() {
		if (topIdLike != null && topIdLike.trim().length() > 0) {
			if (!topIdLike.startsWith("%")) {
				topIdLike = "%" + topIdLike;
			}
			if (!topIdLike.endsWith("%")) {
				topIdLike = topIdLike + "%";
			}
		}
		return topIdLike;
	}

	public List<String> getTopIds() {
		return topIds;
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

	public String getActor() {
		return actor;
	}

	public String getActorLike() {
		if (actorLike != null && actorLike.trim().length() > 0) {
			if (!actorLike.startsWith("%")) {
				actorLike = "%" + actorLike;
			}
			if (!actorLike.endsWith("%")) {
				actorLike = actorLike + "%";
			}
		}
		return actorLike;
	}

	public List<String> getActors() {
		return actors;
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

	public String getFileContent() {
		return fileContent;
	}

	public String getFileContentLike() {
		if (fileContentLike != null && fileContentLike.trim().length() > 0) {
			if (!fileContentLike.startsWith("%")) {
				fileContentLike = "%" + fileContentLike;
			}
			if (!fileContentLike.endsWith("%")) {
				fileContentLike = fileContentLike + "%";
			}
		}
		return fileContentLike;
	}

	public List<String> getFileContents() {
		return fileContents;
	}

	public String getVision() {
		return vision;
	}

	public String getVisionLike() {
		if (visionLike != null && visionLike.trim().length() > 0) {
			if (!visionLike.startsWith("%")) {
				visionLike = "%" + visionLike;
			}
			if (!visionLike.endsWith("%")) {
				visionLike = visionLike + "%";
			}
		}
		return visionLike;
	}

	public List<String> getVisions() {
		return visions;
	}

	public Date getCtimeGreaterThanOrEqual() {
		return ctimeGreaterThanOrEqual;
	}

	public Date getCtimeLessThanOrEqual() {
		return ctimeLessThanOrEqual;
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

	public String getVisID() {
		return visID;
	}

	public String getVisIDLike() {
		if (visIDLike != null && visIDLike.trim().length() > 0) {
			if (!visIDLike.startsWith("%")) {
				visIDLike = "%" + visIDLike;
			}
			if (!visIDLike.endsWith("%")) {
				visIDLike = visIDLike + "%";
			}
		}
		return visIDLike;
	}

	public List<String> getVisIDs() {
		return visIDs;
	}

	public String getAttID() {
		return attID;
	}

	public String getAttIDLike() {
		if (attIDLike != null && attIDLike.trim().length() > 0) {
			if (!attIDLike.startsWith("%")) {
				attIDLike = "%" + attIDLike;
			}
			if (!attIDLike.endsWith("%")) {
				attIDLike = attIDLike + "%";
			}
		}
		return attIDLike;
	}

	public List<String> getAttIDs() {
		return attIDs;
	}

	public Integer getIsTextContent() {
		return isTextContent;
	}

	public Integer getIsTextContentGreaterThanOrEqual() {
		return isTextContentGreaterThanOrEqual;
	}

	public Integer getIsTextContentLessThanOrEqual() {
		return isTextContentLessThanOrEqual;
	}

	public List<Integer> getIsTextContents() {
		return isTextContents;
	}

	public String getTextContent() {
		return textContent;
	}

	public String getTextContentLike() {
		if (textContentLike != null && textContentLike.trim().length() > 0) {
			if (!textContentLike.startsWith("%")) {
				textContentLike = "%" + textContentLike;
			}
			if (!textContentLike.endsWith("%")) {
				textContentLike = textContentLike + "%";
			}
		}
		return textContentLike;
	}

	public List<String> getTextContents() {
		return textContents;
	}

	public void setTopId(String topId) {
		this.topId = topId;
	}

	public void setTopIdLike(String topIdLike) {
		this.topIdLike = topIdLike;
	}

	public void setTopIds(List<String> topIds) {
		this.topIds = topIds;
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

	public void setActor(String actor) {
		this.actor = actor;
	}

	public void setActorLike(String actorLike) {
		this.actorLike = actorLike;
	}

	public void setActors(List<String> actors) {
		this.actors = actors;
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

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileNameLike(String fileNameLike) {
		this.fileNameLike = fileNameLike;
	}

	public void setFileNames(List<String> fileNames) {
		this.fileNames = fileNames;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public void setFileContentLike(String fileContentLike) {
		this.fileContentLike = fileContentLike;
	}

	public void setFileContents(List<String> fileContents) {
		this.fileContents = fileContents;
	}

	public void setVision(String vision) {
		this.vision = vision;
	}

	public void setVisionLike(String visionLike) {
		this.visionLike = visionLike;
	}

	public void setVisions(List<String> visions) {
		this.visions = visions;
	}

	public void setCtimeGreaterThanOrEqual(Date ctimeGreaterThanOrEqual) {
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
	}

	public void setCtimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
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

	public void setVisID(String visID) {
		this.visID = visID;
	}

	public void setVisIDLike(String visIDLike) {
		this.visIDLike = visIDLike;
	}

	public void setVisIDs(List<String> visIDs) {
		this.visIDs = visIDs;
	}

	public void setAttID(String attID) {
		this.attID = attID;
	}

	public void setAttIDLike(String attIDLike) {
		this.attIDLike = attIDLike;
	}

	public void setAttIDs(List<String> attIDs) {
		this.attIDs = attIDs;
	}

	public void setIsTextContent(Integer isTextContent) {
		this.isTextContent = isTextContent;
	}

	public void setIsTextContentGreaterThanOrEqual(
			Integer isTextContentGreaterThanOrEqual) {
		this.isTextContentGreaterThanOrEqual = isTextContentGreaterThanOrEqual;
	}

	public void setIsTextContentLessThanOrEqual(
			Integer isTextContentLessThanOrEqual) {
		this.isTextContentLessThanOrEqual = isTextContentLessThanOrEqual;
	}

	public void setIsTextContents(List<Integer> isTextContents) {
		this.isTextContents = isTextContents;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public void setTextContentLike(String textContentLike) {
		this.textContentLike = textContentLike;
	}

	public void setTextContents(List<String> textContents) {
		this.textContents = textContents;
	}

	public FileAttQuery topId(String topId) {
		if (topId == null) {
			throw new RuntimeException("topId is null");
		}
		this.topId = topId;
		return this;
	}

	public FileAttQuery topIdLike(String topIdLike) {
		if (topIdLike == null) {
			throw new RuntimeException("topId is null");
		}
		this.topIdLike = topIdLike;
		return this;
	}

	public FileAttQuery topIds(List<String> topIds) {
		if (topIds == null) {
			throw new RuntimeException("topIds is empty ");
		}
		this.topIds = topIds;
		return this;
	}

	public FileAttQuery num(String num) {
		if (num == null) {
			throw new RuntimeException("num is null");
		}
		this.num = num;
		return this;
	}

	public FileAttQuery numLike(String numLike) {
		if (numLike == null) {
			throw new RuntimeException("num is null");
		}
		this.numLike = numLike;
		return this;
	}

	public FileAttQuery nums(List<String> nums) {
		if (nums == null) {
			throw new RuntimeException("nums is empty ");
		}
		this.nums = nums;
		return this;
	}

	public FileAttQuery actor(String actor) {
		if (actor == null) {
			throw new RuntimeException("actor is null");
		}
		this.actor = actor;
		return this;
	}

	public FileAttQuery actorLike(String actorLike) {
		if (actorLike == null) {
			throw new RuntimeException("actor is null");
		}
		this.actorLike = actorLike;
		return this;
	}

	public FileAttQuery actors(List<String> actors) {
		if (actors == null) {
			throw new RuntimeException("actors is empty ");
		}
		this.actors = actors;
		return this;
	}

	public FileAttQuery fname(String fname) {
		if (fname == null) {
			throw new RuntimeException("fname is null");
		}
		this.fname = fname;
		return this;
	}

	public FileAttQuery fnameLike(String fnameLike) {
		if (fnameLike == null) {
			throw new RuntimeException("fname is null");
		}
		this.fnameLike = fnameLike;
		return this;
	}

	public FileAttQuery fnames(List<String> fnames) {
		if (fnames == null) {
			throw new RuntimeException("fnames is empty ");
		}
		this.fnames = fnames;
		return this;
	}

	public FileAttQuery fileName(String fileName) {
		if (fileName == null) {
			throw new RuntimeException("fileName is null");
		}
		this.fileName = fileName;
		return this;
	}

	public FileAttQuery fileNameLike(String fileNameLike) {
		if (fileNameLike == null) {
			throw new RuntimeException("fileName is null");
		}
		this.fileNameLike = fileNameLike;
		return this;
	}

	public FileAttQuery fileNames(List<String> fileNames) {
		if (fileNames == null) {
			throw new RuntimeException("fileNames is empty ");
		}
		this.fileNames = fileNames;
		return this;
	}

	public FileAttQuery fileContent(String fileContent) {
		if (fileContent == null) {
			throw new RuntimeException("fileContent is null");
		}
		this.fileContent = fileContent;
		return this;
	}

	public FileAttQuery fileContentLike(String fileContentLike) {
		if (fileContentLike == null) {
			throw new RuntimeException("fileContent is null");
		}
		this.fileContentLike = fileContentLike;
		return this;
	}

	public FileAttQuery fileContents(List<String> fileContents) {
		if (fileContents == null) {
			throw new RuntimeException("fileContents is empty ");
		}
		this.fileContents = fileContents;
		return this;
	}

	public FileAttQuery vision(String vision) {
		if (vision == null) {
			throw new RuntimeException("vision is null");
		}
		this.vision = vision;
		return this;
	}

	public FileAttQuery visionLike(String visionLike) {
		if (visionLike == null) {
			throw new RuntimeException("vision is null");
		}
		this.visionLike = visionLike;
		return this;
	}

	public FileAttQuery visions(List<String> visions) {
		if (visions == null) {
			throw new RuntimeException("visions is empty ");
		}
		this.visions = visions;
		return this;
	}

	public FileAttQuery ctimeGreaterThanOrEqual(Date ctimeGreaterThanOrEqual) {
		if (ctimeGreaterThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
		return this;
	}

	public FileAttQuery ctimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		if (ctimeLessThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
		return this;
	}

	public FileAttQuery fileSize(Integer fileSize) {
		if (fileSize == null) {
			throw new RuntimeException("fileSize is null");
		}
		this.fileSize = fileSize;
		return this;
	}

	public FileAttQuery fileSizeGreaterThanOrEqual(
			Integer fileSizeGreaterThanOrEqual) {
		if (fileSizeGreaterThanOrEqual == null) {
			throw new RuntimeException("fileSize is null");
		}
		this.fileSizeGreaterThanOrEqual = fileSizeGreaterThanOrEqual;
		return this;
	}

	public FileAttQuery fileSizeLessThanOrEqual(Integer fileSizeLessThanOrEqual) {
		if (fileSizeLessThanOrEqual == null) {
			throw new RuntimeException("fileSize is null");
		}
		this.fileSizeLessThanOrEqual = fileSizeLessThanOrEqual;
		return this;
	}

	public FileAttQuery fileSizes(List<Integer> fileSizes) {
		if (fileSizes == null) {
			throw new RuntimeException("fileSizes is empty ");
		}
		this.fileSizes = fileSizes;
		return this;
	}

	public FileAttQuery visID(String visID) {
		if (visID == null) {
			throw new RuntimeException("visID is null");
		}
		this.visID = visID;
		return this;
	}

	public FileAttQuery visIDLike(String visIDLike) {
		if (visIDLike == null) {
			throw new RuntimeException("visID is null");
		}
		this.visIDLike = visIDLike;
		return this;
	}

	public FileAttQuery visIDs(List<String> visIDs) {
		if (visIDs == null) {
			throw new RuntimeException("visIDs is empty ");
		}
		this.visIDs = visIDs;
		return this;
	}

	public FileAttQuery attID(String attID) {
		if (attID == null) {
			throw new RuntimeException("attID is null");
		}
		this.attID = attID;
		return this;
	}

	public FileAttQuery attIDLike(String attIDLike) {
		if (attIDLike == null) {
			throw new RuntimeException("attID is null");
		}
		this.attIDLike = attIDLike;
		return this;
	}

	public FileAttQuery attIDs(List<String> attIDs) {
		if (attIDs == null) {
			throw new RuntimeException("attIDs is empty ");
		}
		this.attIDs = attIDs;
		return this;
	}

	public FileAttQuery isTextContent(Integer isTextContent) {
		if (isTextContent == null) {
			throw new RuntimeException("isTextContent is null");
		}
		this.isTextContent = isTextContent;
		return this;
	}

	public FileAttQuery isTextContentGreaterThanOrEqual(
			Integer isTextContentGreaterThanOrEqual) {
		if (isTextContentGreaterThanOrEqual == null) {
			throw new RuntimeException("isTextContent is null");
		}
		this.isTextContentGreaterThanOrEqual = isTextContentGreaterThanOrEqual;
		return this;
	}

	public FileAttQuery isTextContentLessThanOrEqual(
			Integer isTextContentLessThanOrEqual) {
		if (isTextContentLessThanOrEqual == null) {
			throw new RuntimeException("isTextContent is null");
		}
		this.isTextContentLessThanOrEqual = isTextContentLessThanOrEqual;
		return this;
	}

	public FileAttQuery isTextContents(List<Integer> isTextContents) {
		if (isTextContents == null) {
			throw new RuntimeException("isTextContents is empty ");
		}
		this.isTextContents = isTextContents;
		return this;
	}

	public FileAttQuery textContent(String textContent) {
		if (textContent == null) {
			throw new RuntimeException("textContent is null");
		}
		this.textContent = textContent;
		return this;
	}

	public FileAttQuery textContentLike(String textContentLike) {
		if (textContentLike == null) {
			throw new RuntimeException("textContent is null");
		}
		this.textContentLike = textContentLike;
		return this;
	}

	public FileAttQuery textContents(List<String> textContents) {
		if (textContents == null) {
			throw new RuntimeException("textContents is empty ");
		}
		this.textContents = textContents;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("topId".equals(sortColumn)) {
				orderBy = "E.TOPID" + a_x;
			}

			if ("num".equals(sortColumn)) {
				orderBy = "E.NUM" + a_x;
			}

			if ("actor".equals(sortColumn)) {
				orderBy = "E.ACTOR" + a_x;
			}

			if ("fname".equals(sortColumn)) {
				orderBy = "E.FNAME" + a_x;
			}

			if ("fileName".equals(sortColumn)) {
				orderBy = "E.FILE_NAME" + a_x;
			}

			if ("fileContent".equals(sortColumn)) {
				orderBy = "E.FILE_CONTENT" + a_x;
			}

			if ("vision".equals(sortColumn)) {
				orderBy = "E.VISION" + a_x;
			}

			if ("ctime".equals(sortColumn)) {
				orderBy = "E.CTIME" + a_x;
			}

			if ("fileSize".equals(sortColumn)) {
				orderBy = "E.FILESIZE" + a_x;
			}

			if ("visID".equals(sortColumn)) {
				orderBy = "E.VISID" + a_x;
			}

			if ("attID".equals(sortColumn)) {
				orderBy = "E.ATTID" + a_x;
			}

			if ("isTextContent".equals(sortColumn)) {
				orderBy = "E.ISTEXTCONTENT" + a_x;
			}

			if ("textContent".equals(sortColumn)) {
				orderBy = "E.TEXTCONTENT" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("fileID", "FILEID");
		addColumn("topId", "TOPID");
		addColumn("num", "NUM");
		addColumn("actor", "ACTOR");
		addColumn("fname", "FNAME");
		addColumn("fileName", "FILE_NAME");
		addColumn("fileContent", "FILE_CONTENT");
		addColumn("vision", "VISION");
		addColumn("ctime", "CTIME");
		addColumn("fileSize", "FILESIZE");
		addColumn("visID", "VISID");
		addColumn("attID", "ATTID");
		addColumn("isTextContent", "ISTEXTCONTENT");
		addColumn("textContent", "TEXTCONTENT");
	}

}