package com.glaf.convert.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ConvertElemTmplQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> cvtElemIds;
	protected Collection<String> appActorIds;
	protected Long cvtId;
	protected Long cvtIdGreaterThanOrEqual;
	protected Long cvtIdLessThanOrEqual;
	protected List<Long> cvtIds;
	protected String elemType;
	protected String elemTypeLike;
	protected List<String> elemTypes;
	protected String elemName;
	protected String elemNameLike;
	protected List<String> elemNames;
	protected String elemId;
	protected String elemIdLike;
	protected List<String> elemIds;
	protected String dType;
	protected String dTypeLike;
	protected List<String> dTypes;
	protected Integer len;
	protected Integer lenGreaterThanOrEqual;
	protected Integer lenLessThanOrEqual;
	protected List<Integer> lens;
	protected Integer digit;
	protected Integer digitGreaterThanOrEqual;
	protected Integer digitLessThanOrEqual;
	protected List<Integer> digits;
	protected String defaultVal;
	protected String defaultValLike;
	protected List<String> defaultVals;
	protected Integer rowIndex;
	protected Integer rowIndexGreaterThanOrEqual;
	protected Integer rowIndexLessThanOrEqual;
	protected List<Integer> rowIndexs;
	protected Integer colIndex;
	protected Integer colIndexGreaterThanOrEqual;
	protected Integer colIndexLessThanOrEqual;
	protected List<Integer> colIndexs;
	protected String readOnly;
	protected String readOnlyLike;
	protected List<String> readOnlys;
	protected String print;
	protected String printLike;
	protected List<String> prints;
	protected String display;
	protected String displayLike;
	protected List<String> displays;
	protected String isMustFill;
	protected String isMustFillLike;
	protected List<String> isMustFills;
	protected String isDataOnly;
	protected String isDataOnlyLike;
	protected List<String> isDataOnlys;
	protected String direction;
	protected String directionLike;
	protected List<String> directions;
	protected String vararea;
	protected String varareaLike;
	protected List<String> varareas;
	protected Integer endRowIndex;
	protected Integer endRowIndexGreaterThanOrEqual;
	protected Integer endRowIndexLessThanOrEqual;
	protected List<Integer> endRowIndexs;
	protected Integer endColIndex;
	protected Integer endColIndexGreaterThanOrEqual;
	protected Integer endColIndexLessThanOrEqual;
	protected List<Integer> endColIndexs;
	protected Date createDatetimeGreaterThanOrEqual;
	protected Date createDatetimeLessThanOrEqual;
	protected Date modifyDatetimeGreaterThanOrEqual;
	protected Date modifyDatetimeLessThanOrEqual;
	protected String repinfoListId;
	protected String repinfoListIdLike;
	protected List<String> repinfoListIds;

	public ConvertElemTmplQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public Long getCvtId() {
		return cvtId;
	}

	public Long getCvtIdGreaterThanOrEqual() {
		return cvtIdGreaterThanOrEqual;
	}

	public Long getCvtIdLessThanOrEqual() {
		return cvtIdLessThanOrEqual;
	}

	public List<Long> getCvtIds() {
		return cvtIds;
	}

	public String getElemType() {
		return elemType;
	}

	public String getElemTypeLike() {
		if (elemTypeLike != null && elemTypeLike.trim().length() > 0) {
			if (!elemTypeLike.startsWith("%")) {
				elemTypeLike = "%" + elemTypeLike;
			}
			if (!elemTypeLike.endsWith("%")) {
				elemTypeLike = elemTypeLike + "%";
			}
		}
		return elemTypeLike;
	}

	public List<String> getElemTypes() {
		return elemTypes;
	}

	public String getElemName() {
		return elemName;
	}

	public String getElemNameLike() {
		if (elemNameLike != null && elemNameLike.trim().length() > 0) {
			if (!elemNameLike.startsWith("%")) {
				elemNameLike = "%" + elemNameLike;
			}
			if (!elemNameLike.endsWith("%")) {
				elemNameLike = elemNameLike + "%";
			}
		}
		return elemNameLike;
	}

	public List<String> getElemNames() {
		return elemNames;
	}

	public String getElemId() {
		return elemId;
	}

	public String getElemIdLike() {
		if (elemIdLike != null && elemIdLike.trim().length() > 0) {
			if (!elemIdLike.startsWith("%")) {
				elemIdLike = "%" + elemIdLike;
			}
			if (!elemIdLike.endsWith("%")) {
				elemIdLike = elemIdLike + "%";
			}
		}
		return elemIdLike;
	}

	public List<String> getElemIds() {
		return elemIds;
	}

	public String getDType() {
		return dType;
	}

	public String getDTypeLike() {
		if (dTypeLike != null && dTypeLike.trim().length() > 0) {
			if (!dTypeLike.startsWith("%")) {
				dTypeLike = "%" + dTypeLike;
			}
			if (!dTypeLike.endsWith("%")) {
				dTypeLike = dTypeLike + "%";
			}
		}
		return dTypeLike;
	}

	public List<String> getDTypes() {
		return dTypes;
	}

	public Integer getLen() {
		return len;
	}

	public Integer getLenGreaterThanOrEqual() {
		return lenGreaterThanOrEqual;
	}

	public Integer getLenLessThanOrEqual() {
		return lenLessThanOrEqual;
	}

	public List<Integer> getLens() {
		return lens;
	}

	public Integer getDigit() {
		return digit;
	}

	public Integer getDigitGreaterThanOrEqual() {
		return digitGreaterThanOrEqual;
	}

	public Integer getDigitLessThanOrEqual() {
		return digitLessThanOrEqual;
	}

	public List<Integer> getDigits() {
		return digits;
	}

	public String getDefaultVal() {
		return defaultVal;
	}

	public String getDefaultValLike() {
		if (defaultValLike != null && defaultValLike.trim().length() > 0) {
			if (!defaultValLike.startsWith("%")) {
				defaultValLike = "%" + defaultValLike;
			}
			if (!defaultValLike.endsWith("%")) {
				defaultValLike = defaultValLike + "%";
			}
		}
		return defaultValLike;
	}

	public List<String> getDefaultVals() {
		return defaultVals;
	}

	public Integer getRowIndex() {
		return rowIndex;
	}

	public Integer getRowIndexGreaterThanOrEqual() {
		return rowIndexGreaterThanOrEqual;
	}

	public Integer getRowIndexLessThanOrEqual() {
		return rowIndexLessThanOrEqual;
	}

	public List<Integer> getRowIndexs() {
		return rowIndexs;
	}

	public Integer getColIndex() {
		return colIndex;
	}

	public Integer getColIndexGreaterThanOrEqual() {
		return colIndexGreaterThanOrEqual;
	}

	public Integer getColIndexLessThanOrEqual() {
		return colIndexLessThanOrEqual;
	}

	public List<Integer> getColIndexs() {
		return colIndexs;
	}

	public String getReadOnly() {
		return readOnly;
	}

	public String getReadOnlyLike() {
		if (readOnlyLike != null && readOnlyLike.trim().length() > 0) {
			if (!readOnlyLike.startsWith("%")) {
				readOnlyLike = "%" + readOnlyLike;
			}
			if (!readOnlyLike.endsWith("%")) {
				readOnlyLike = readOnlyLike + "%";
			}
		}
		return readOnlyLike;
	}

	public List<String> getReadOnlys() {
		return readOnlys;
	}

	public String getPrint() {
		return print;
	}

	public String getPrintLike() {
		if (printLike != null && printLike.trim().length() > 0) {
			if (!printLike.startsWith("%")) {
				printLike = "%" + printLike;
			}
			if (!printLike.endsWith("%")) {
				printLike = printLike + "%";
			}
		}
		return printLike;
	}

	public List<String> getPrints() {
		return prints;
	}

	public String getDisplay() {
		return display;
	}

	public String getDisplayLike() {
		if (displayLike != null && displayLike.trim().length() > 0) {
			if (!displayLike.startsWith("%")) {
				displayLike = "%" + displayLike;
			}
			if (!displayLike.endsWith("%")) {
				displayLike = displayLike + "%";
			}
		}
		return displayLike;
	}

	public List<String> getDisplays() {
		return displays;
	}

	public String getIsMustFill() {
		return isMustFill;
	}

	public String getIsMustFillLike() {
		if (isMustFillLike != null && isMustFillLike.trim().length() > 0) {
			if (!isMustFillLike.startsWith("%")) {
				isMustFillLike = "%" + isMustFillLike;
			}
			if (!isMustFillLike.endsWith("%")) {
				isMustFillLike = isMustFillLike + "%";
			}
		}
		return isMustFillLike;
	}

	public List<String> getIsMustFills() {
		return isMustFills;
	}

	public String getIsDataOnly() {
		return isDataOnly;
	}

	public String getIsDataOnlyLike() {
		if (isDataOnlyLike != null && isDataOnlyLike.trim().length() > 0) {
			if (!isDataOnlyLike.startsWith("%")) {
				isDataOnlyLike = "%" + isDataOnlyLike;
			}
			if (!isDataOnlyLike.endsWith("%")) {
				isDataOnlyLike = isDataOnlyLike + "%";
			}
		}
		return isDataOnlyLike;
	}

	public List<String> getIsDataOnlys() {
		return isDataOnlys;
	}

	public String getDirection() {
		return direction;
	}

	public String getDirectionLike() {
		if (directionLike != null && directionLike.trim().length() > 0) {
			if (!directionLike.startsWith("%")) {
				directionLike = "%" + directionLike;
			}
			if (!directionLike.endsWith("%")) {
				directionLike = directionLike + "%";
			}
		}
		return directionLike;
	}

	public List<String> getDirections() {
		return directions;
	}

	public String getVararea() {
		return vararea;
	}

	public String getVarareaLike() {
		if (varareaLike != null && varareaLike.trim().length() > 0) {
			if (!varareaLike.startsWith("%")) {
				varareaLike = "%" + varareaLike;
			}
			if (!varareaLike.endsWith("%")) {
				varareaLike = varareaLike + "%";
			}
		}
		return varareaLike;
	}

	public List<String> getVarareas() {
		return varareas;
	}

	public Integer getEndRowIndex() {
		return endRowIndex;
	}

	public Integer getEndRowIndexGreaterThanOrEqual() {
		return endRowIndexGreaterThanOrEqual;
	}

	public Integer getEndRowIndexLessThanOrEqual() {
		return endRowIndexLessThanOrEqual;
	}

	public List<Integer> getEndRowIndexs() {
		return endRowIndexs;
	}

	public Integer getEndColIndex() {
		return endColIndex;
	}

	public Integer getEndColIndexGreaterThanOrEqual() {
		return endColIndexGreaterThanOrEqual;
	}

	public Integer getEndColIndexLessThanOrEqual() {
		return endColIndexLessThanOrEqual;
	}

	public List<Integer> getEndColIndexs() {
		return endColIndexs;
	}

	public Date getCreateDatetimeGreaterThanOrEqual() {
		return createDatetimeGreaterThanOrEqual;
	}

	public Date getCreateDatetimeLessThanOrEqual() {
		return createDatetimeLessThanOrEqual;
	}

	public Date getModifyDatetimeGreaterThanOrEqual() {
		return modifyDatetimeGreaterThanOrEqual;
	}

	public Date getModifyDatetimeLessThanOrEqual() {
		return modifyDatetimeLessThanOrEqual;
	}

	public String getRepinfoListId() {
		return repinfoListId;
	}

	public String getRepinfoListIdLike() {
		if (repinfoListIdLike != null && repinfoListIdLike.trim().length() > 0) {
			if (!repinfoListIdLike.startsWith("%")) {
				repinfoListIdLike = "%" + repinfoListIdLike;
			}
			if (!repinfoListIdLike.endsWith("%")) {
				repinfoListIdLike = repinfoListIdLike + "%";
			}
		}
		return repinfoListIdLike;
	}

	public List<String> getRepinfoListIds() {
		return repinfoListIds;
	}

	public void setCvtId(Long cvtId) {
		this.cvtId = cvtId;
	}

	public void setCvtIdGreaterThanOrEqual(Long cvtIdGreaterThanOrEqual) {
		this.cvtIdGreaterThanOrEqual = cvtIdGreaterThanOrEqual;
	}

	public void setCvtIdLessThanOrEqual(Long cvtIdLessThanOrEqual) {
		this.cvtIdLessThanOrEqual = cvtIdLessThanOrEqual;
	}

	public void setCvtIds(List<Long> cvtIds) {
		this.cvtIds = cvtIds;
	}

	public void setElemType(String elemType) {
		this.elemType = elemType;
	}

	public void setElemTypeLike(String elemTypeLike) {
		this.elemTypeLike = elemTypeLike;
	}

	public void setElemTypes(List<String> elemTypes) {
		this.elemTypes = elemTypes;
	}

	public void setElemName(String elemName) {
		this.elemName = elemName;
	}

	public void setElemNameLike(String elemNameLike) {
		this.elemNameLike = elemNameLike;
	}

	public void setElemNames(List<String> elemNames) {
		this.elemNames = elemNames;
	}

	public void setElemId(String elemId) {
		this.elemId = elemId;
	}

	public void setElemIdLike(String elemIdLike) {
		this.elemIdLike = elemIdLike;
	}

	public void setElemIds(List<String> elemIds) {
		this.elemIds = elemIds;
	}

	public void setDType(String dType) {
		this.dType = dType;
	}

	public void setDTypeLike(String dTypeLike) {
		this.dTypeLike = dTypeLike;
	}

	public void setDTypes(List<String> dTypes) {
		this.dTypes = dTypes;
	}

	public void setLen(Integer len) {
		this.len = len;
	}

	public void setLenGreaterThanOrEqual(Integer lenGreaterThanOrEqual) {
		this.lenGreaterThanOrEqual = lenGreaterThanOrEqual;
	}

	public void setLenLessThanOrEqual(Integer lenLessThanOrEqual) {
		this.lenLessThanOrEqual = lenLessThanOrEqual;
	}

	public void setLens(List<Integer> lens) {
		this.lens = lens;
	}

	public void setDigit(Integer digit) {
		this.digit = digit;
	}

	public void setDigitGreaterThanOrEqual(Integer digitGreaterThanOrEqual) {
		this.digitGreaterThanOrEqual = digitGreaterThanOrEqual;
	}

	public void setDigitLessThanOrEqual(Integer digitLessThanOrEqual) {
		this.digitLessThanOrEqual = digitLessThanOrEqual;
	}

	public void setDigits(List<Integer> digits) {
		this.digits = digits;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}

	public void setDefaultValLike(String defaultValLike) {
		this.defaultValLike = defaultValLike;
	}

	public void setDefaultVals(List<String> defaultVals) {
		this.defaultVals = defaultVals;
	}

	public void setRowIndex(Integer rowIndex) {
		this.rowIndex = rowIndex;
	}

	public void setRowIndexGreaterThanOrEqual(Integer rowIndexGreaterThanOrEqual) {
		this.rowIndexGreaterThanOrEqual = rowIndexGreaterThanOrEqual;
	}

	public void setRowIndexLessThanOrEqual(Integer rowIndexLessThanOrEqual) {
		this.rowIndexLessThanOrEqual = rowIndexLessThanOrEqual;
	}

	public void setRowIndexs(List<Integer> rowIndexs) {
		this.rowIndexs = rowIndexs;
	}

	public void setColIndex(Integer colIndex) {
		this.colIndex = colIndex;
	}

	public void setColIndexGreaterThanOrEqual(Integer colIndexGreaterThanOrEqual) {
		this.colIndexGreaterThanOrEqual = colIndexGreaterThanOrEqual;
	}

	public void setColIndexLessThanOrEqual(Integer colIndexLessThanOrEqual) {
		this.colIndexLessThanOrEqual = colIndexLessThanOrEqual;
	}

	public void setColIndexs(List<Integer> colIndexs) {
		this.colIndexs = colIndexs;
	}

	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	public void setReadOnlyLike(String readOnlyLike) {
		this.readOnlyLike = readOnlyLike;
	}

	public void setReadOnlys(List<String> readOnlys) {
		this.readOnlys = readOnlys;
	}

	public void setPrint(String print) {
		this.print = print;
	}

	public void setPrintLike(String printLike) {
		this.printLike = printLike;
	}

	public void setPrints(List<String> prints) {
		this.prints = prints;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public void setDisplayLike(String displayLike) {
		this.displayLike = displayLike;
	}

	public void setDisplays(List<String> displays) {
		this.displays = displays;
	}

	public void setIsMustFill(String isMustFill) {
		this.isMustFill = isMustFill;
	}

	public void setIsMustFillLike(String isMustFillLike) {
		this.isMustFillLike = isMustFillLike;
	}

	public void setIsMustFills(List<String> isMustFills) {
		this.isMustFills = isMustFills;
	}

	public void setIsDataOnly(String isDataOnly) {
		this.isDataOnly = isDataOnly;
	}

	public void setIsDataOnlyLike(String isDataOnlyLike) {
		this.isDataOnlyLike = isDataOnlyLike;
	}

	public void setIsDataOnlys(List<String> isDataOnlys) {
		this.isDataOnlys = isDataOnlys;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void setDirectionLike(String directionLike) {
		this.directionLike = directionLike;
	}

	public void setDirections(List<String> directions) {
		this.directions = directions;
	}

	public void setVararea(String vararea) {
		this.vararea = vararea;
	}

	public void setVarareaLike(String varareaLike) {
		this.varareaLike = varareaLike;
	}

	public void setVarareas(List<String> varareas) {
		this.varareas = varareas;
	}

	public void setEndRowIndex(Integer endRowIndex) {
		this.endRowIndex = endRowIndex;
	}

	public void setEndRowIndexGreaterThanOrEqual(Integer endRowIndexGreaterThanOrEqual) {
		this.endRowIndexGreaterThanOrEqual = endRowIndexGreaterThanOrEqual;
	}

	public void setEndRowIndexLessThanOrEqual(Integer endRowIndexLessThanOrEqual) {
		this.endRowIndexLessThanOrEqual = endRowIndexLessThanOrEqual;
	}

	public void setEndRowIndexs(List<Integer> endRowIndexs) {
		this.endRowIndexs = endRowIndexs;
	}

	public void setEndColIndex(Integer endColIndex) {
		this.endColIndex = endColIndex;
	}

	public void setEndColIndexGreaterThanOrEqual(Integer endColIndexGreaterThanOrEqual) {
		this.endColIndexGreaterThanOrEqual = endColIndexGreaterThanOrEqual;
	}

	public void setEndColIndexLessThanOrEqual(Integer endColIndexLessThanOrEqual) {
		this.endColIndexLessThanOrEqual = endColIndexLessThanOrEqual;
	}

	public void setEndColIndexs(List<Integer> endColIndexs) {
		this.endColIndexs = endColIndexs;
	}

	public void setCreateDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual) {
		this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
	}

	public void setCreateDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual) {
		this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
	}

	public void setModifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual) {
		this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
	}

	public void setModifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual) {
		this.modifyDatetimeLessThanOrEqual = modifyDatetimeLessThanOrEqual;
	}

	public void setRepinfoListId(String repinfoListId) {
		this.repinfoListId = repinfoListId;
	}

	public void setRepinfoListIdLike(String repinfoListIdLike) {
		this.repinfoListIdLike = repinfoListIdLike;
	}

	public void setRepinfoListIds(List<String> repinfoListIds) {
		this.repinfoListIds = repinfoListIds;
	}

	public ConvertElemTmplQuery cvtId(Long cvtId) {
		if (cvtId == null) {
			throw new RuntimeException("cvtId is null");
		}
		this.cvtId = cvtId;
		return this;
	}

	public ConvertElemTmplQuery cvtIdGreaterThanOrEqual(Long cvtIdGreaterThanOrEqual) {
		if (cvtIdGreaterThanOrEqual == null) {
			throw new RuntimeException("cvtId is null");
		}
		this.cvtIdGreaterThanOrEqual = cvtIdGreaterThanOrEqual;
		return this;
	}

	public ConvertElemTmplQuery cvtIdLessThanOrEqual(Long cvtIdLessThanOrEqual) {
		if (cvtIdLessThanOrEqual == null) {
			throw new RuntimeException("cvtId is null");
		}
		this.cvtIdLessThanOrEqual = cvtIdLessThanOrEqual;
		return this;
	}

	public ConvertElemTmplQuery cvtIds(List<Long> cvtIds) {
		if (cvtIds == null) {
			throw new RuntimeException("cvtIds is empty ");
		}
		this.cvtIds = cvtIds;
		return this;
	}

	public ConvertElemTmplQuery elemType(String elemType) {
		if (elemType == null) {
			throw new RuntimeException("elemType is null");
		}
		this.elemType = elemType;
		return this;
	}

	public ConvertElemTmplQuery elemTypeLike(String elemTypeLike) {
		if (elemTypeLike == null) {
			throw new RuntimeException("elemType is null");
		}
		this.elemTypeLike = elemTypeLike;
		return this;
	}

	public ConvertElemTmplQuery elemTypes(List<String> elemTypes) {
		if (elemTypes == null) {
			throw new RuntimeException("elemTypes is empty ");
		}
		this.elemTypes = elemTypes;
		return this;
	}

	public ConvertElemTmplQuery elemName(String elemName) {
		if (elemName == null) {
			throw new RuntimeException("elemName is null");
		}
		this.elemName = elemName;
		return this;
	}

	public ConvertElemTmplQuery elemNameLike(String elemNameLike) {
		if (elemNameLike == null) {
			throw new RuntimeException("elemName is null");
		}
		this.elemNameLike = elemNameLike;
		return this;
	}

	public ConvertElemTmplQuery elemNames(List<String> elemNames) {
		if (elemNames == null) {
			throw new RuntimeException("elemNames is empty ");
		}
		this.elemNames = elemNames;
		return this;
	}

	public ConvertElemTmplQuery elemId(String elemId) {
		if (elemId == null) {
			throw new RuntimeException("elemId is null");
		}
		this.elemId = elemId;
		return this;
	}

	public ConvertElemTmplQuery elemIdLike(String elemIdLike) {
		if (elemIdLike == null) {
			throw new RuntimeException("elemId is null");
		}
		this.elemIdLike = elemIdLike;
		return this;
	}

	public ConvertElemTmplQuery elemIds(List<String> elemIds) {
		if (elemIds == null) {
			throw new RuntimeException("elemIds is empty ");
		}
		this.elemIds = elemIds;
		return this;
	}

	public ConvertElemTmplQuery dType(String dType) {
		if (dType == null) {
			throw new RuntimeException("dType is null");
		}
		this.dType = dType;
		return this;
	}

	public ConvertElemTmplQuery dTypeLike(String dTypeLike) {
		if (dTypeLike == null) {
			throw new RuntimeException("dType is null");
		}
		this.dTypeLike = dTypeLike;
		return this;
	}

	public ConvertElemTmplQuery dTypes(List<String> dTypes) {
		if (dTypes == null) {
			throw new RuntimeException("dTypes is empty ");
		}
		this.dTypes = dTypes;
		return this;
	}

	public ConvertElemTmplQuery len(Integer len) {
		if (len == null) {
			throw new RuntimeException("len is null");
		}
		this.len = len;
		return this;
	}

	public ConvertElemTmplQuery lenGreaterThanOrEqual(Integer lenGreaterThanOrEqual) {
		if (lenGreaterThanOrEqual == null) {
			throw new RuntimeException("len is null");
		}
		this.lenGreaterThanOrEqual = lenGreaterThanOrEqual;
		return this;
	}

	public ConvertElemTmplQuery lenLessThanOrEqual(Integer lenLessThanOrEqual) {
		if (lenLessThanOrEqual == null) {
			throw new RuntimeException("len is null");
		}
		this.lenLessThanOrEqual = lenLessThanOrEqual;
		return this;
	}

	public ConvertElemTmplQuery lens(List<Integer> lens) {
		if (lens == null) {
			throw new RuntimeException("lens is empty ");
		}
		this.lens = lens;
		return this;
	}

	public ConvertElemTmplQuery digit(Integer digit) {
		if (digit == null) {
			throw new RuntimeException("digit is null");
		}
		this.digit = digit;
		return this;
	}

	public ConvertElemTmplQuery digitGreaterThanOrEqual(Integer digitGreaterThanOrEqual) {
		if (digitGreaterThanOrEqual == null) {
			throw new RuntimeException("digit is null");
		}
		this.digitGreaterThanOrEqual = digitGreaterThanOrEqual;
		return this;
	}

	public ConvertElemTmplQuery digitLessThanOrEqual(Integer digitLessThanOrEqual) {
		if (digitLessThanOrEqual == null) {
			throw new RuntimeException("digit is null");
		}
		this.digitLessThanOrEqual = digitLessThanOrEqual;
		return this;
	}

	public ConvertElemTmplQuery digits(List<Integer> digits) {
		if (digits == null) {
			throw new RuntimeException("digits is empty ");
		}
		this.digits = digits;
		return this;
	}

	public ConvertElemTmplQuery defaultVal(String defaultVal) {
		if (defaultVal == null) {
			throw new RuntimeException("defaultVal is null");
		}
		this.defaultVal = defaultVal;
		return this;
	}

	public ConvertElemTmplQuery defaultValLike(String defaultValLike) {
		if (defaultValLike == null) {
			throw new RuntimeException("defaultVal is null");
		}
		this.defaultValLike = defaultValLike;
		return this;
	}

	public ConvertElemTmplQuery defaultVals(List<String> defaultVals) {
		if (defaultVals == null) {
			throw new RuntimeException("defaultVals is empty ");
		}
		this.defaultVals = defaultVals;
		return this;
	}

	public ConvertElemTmplQuery rowIndex(Integer rowIndex) {
		if (rowIndex == null) {
			throw new RuntimeException("rowIndex is null");
		}
		this.rowIndex = rowIndex;
		return this;
	}

	public ConvertElemTmplQuery rowIndexGreaterThanOrEqual(Integer rowIndexGreaterThanOrEqual) {
		if (rowIndexGreaterThanOrEqual == null) {
			throw new RuntimeException("rowIndex is null");
		}
		this.rowIndexGreaterThanOrEqual = rowIndexGreaterThanOrEqual;
		return this;
	}

	public ConvertElemTmplQuery rowIndexLessThanOrEqual(Integer rowIndexLessThanOrEqual) {
		if (rowIndexLessThanOrEqual == null) {
			throw new RuntimeException("rowIndex is null");
		}
		this.rowIndexLessThanOrEqual = rowIndexLessThanOrEqual;
		return this;
	}

	public ConvertElemTmplQuery rowIndexs(List<Integer> rowIndexs) {
		if (rowIndexs == null) {
			throw new RuntimeException("rowIndexs is empty ");
		}
		this.rowIndexs = rowIndexs;
		return this;
	}

	public ConvertElemTmplQuery colIndex(Integer colIndex) {
		if (colIndex == null) {
			throw new RuntimeException("colIndex is null");
		}
		this.colIndex = colIndex;
		return this;
	}

	public ConvertElemTmplQuery colIndexGreaterThanOrEqual(Integer colIndexGreaterThanOrEqual) {
		if (colIndexGreaterThanOrEqual == null) {
			throw new RuntimeException("colIndex is null");
		}
		this.colIndexGreaterThanOrEqual = colIndexGreaterThanOrEqual;
		return this;
	}

	public ConvertElemTmplQuery colIndexLessThanOrEqual(Integer colIndexLessThanOrEqual) {
		if (colIndexLessThanOrEqual == null) {
			throw new RuntimeException("colIndex is null");
		}
		this.colIndexLessThanOrEqual = colIndexLessThanOrEqual;
		return this;
	}

	public ConvertElemTmplQuery colIndexs(List<Integer> colIndexs) {
		if (colIndexs == null) {
			throw new RuntimeException("colIndexs is empty ");
		}
		this.colIndexs = colIndexs;
		return this;
	}

	public ConvertElemTmplQuery readOnly(String readOnly) {
		if (readOnly == null) {
			throw new RuntimeException("readOnly is null");
		}
		this.readOnly = readOnly;
		return this;
	}

	public ConvertElemTmplQuery readOnlyLike(String readOnlyLike) {
		if (readOnlyLike == null) {
			throw new RuntimeException("readOnly is null");
		}
		this.readOnlyLike = readOnlyLike;
		return this;
	}

	public ConvertElemTmplQuery readOnlys(List<String> readOnlys) {
		if (readOnlys == null) {
			throw new RuntimeException("readOnlys is empty ");
		}
		this.readOnlys = readOnlys;
		return this;
	}

	public ConvertElemTmplQuery print(String print) {
		if (print == null) {
			throw new RuntimeException("print is null");
		}
		this.print = print;
		return this;
	}

	public ConvertElemTmplQuery printLike(String printLike) {
		if (printLike == null) {
			throw new RuntimeException("print is null");
		}
		this.printLike = printLike;
		return this;
	}

	public ConvertElemTmplQuery prints(List<String> prints) {
		if (prints == null) {
			throw new RuntimeException("prints is empty ");
		}
		this.prints = prints;
		return this;
	}

	public ConvertElemTmplQuery display(String display) {
		if (display == null) {
			throw new RuntimeException("display is null");
		}
		this.display = display;
		return this;
	}

	public ConvertElemTmplQuery displayLike(String displayLike) {
		if (displayLike == null) {
			throw new RuntimeException("display is null");
		}
		this.displayLike = displayLike;
		return this;
	}

	public ConvertElemTmplQuery displays(List<String> displays) {
		if (displays == null) {
			throw new RuntimeException("displays is empty ");
		}
		this.displays = displays;
		return this;
	}

	public ConvertElemTmplQuery isMustFill(String isMustFill) {
		if (isMustFill == null) {
			throw new RuntimeException("isMustFill is null");
		}
		this.isMustFill = isMustFill;
		return this;
	}

	public ConvertElemTmplQuery isMustFillLike(String isMustFillLike) {
		if (isMustFillLike == null) {
			throw new RuntimeException("isMustFill is null");
		}
		this.isMustFillLike = isMustFillLike;
		return this;
	}

	public ConvertElemTmplQuery isMustFills(List<String> isMustFills) {
		if (isMustFills == null) {
			throw new RuntimeException("isMustFills is empty ");
		}
		this.isMustFills = isMustFills;
		return this;
	}

	public ConvertElemTmplQuery isDataOnly(String isDataOnly) {
		if (isDataOnly == null) {
			throw new RuntimeException("isDataOnly is null");
		}
		this.isDataOnly = isDataOnly;
		return this;
	}

	public ConvertElemTmplQuery isDataOnlyLike(String isDataOnlyLike) {
		if (isDataOnlyLike == null) {
			throw new RuntimeException("isDataOnly is null");
		}
		this.isDataOnlyLike = isDataOnlyLike;
		return this;
	}

	public ConvertElemTmplQuery isDataOnlys(List<String> isDataOnlys) {
		if (isDataOnlys == null) {
			throw new RuntimeException("isDataOnlys is empty ");
		}
		this.isDataOnlys = isDataOnlys;
		return this;
	}

	public ConvertElemTmplQuery direction(String direction) {
		if (direction == null) {
			throw new RuntimeException("direction is null");
		}
		this.direction = direction;
		return this;
	}

	public ConvertElemTmplQuery directionLike(String directionLike) {
		if (directionLike == null) {
			throw new RuntimeException("direction is null");
		}
		this.directionLike = directionLike;
		return this;
	}

	public ConvertElemTmplQuery directions(List<String> directions) {
		if (directions == null) {
			throw new RuntimeException("directions is empty ");
		}
		this.directions = directions;
		return this;
	}

	public ConvertElemTmplQuery vararea(String vararea) {
		if (vararea == null) {
			throw new RuntimeException("vararea is null");
		}
		this.vararea = vararea;
		return this;
	}

	public ConvertElemTmplQuery varareaLike(String varareaLike) {
		if (varareaLike == null) {
			throw new RuntimeException("vararea is null");
		}
		this.varareaLike = varareaLike;
		return this;
	}

	public ConvertElemTmplQuery varareas(List<String> varareas) {
		if (varareas == null) {
			throw new RuntimeException("varareas is empty ");
		}
		this.varareas = varareas;
		return this;
	}

	public ConvertElemTmplQuery endRowIndex(Integer endRowIndex) {
		if (endRowIndex == null) {
			throw new RuntimeException("endRowIndex is null");
		}
		this.endRowIndex = endRowIndex;
		return this;
	}

	public ConvertElemTmplQuery endRowIndexGreaterThanOrEqual(Integer endRowIndexGreaterThanOrEqual) {
		if (endRowIndexGreaterThanOrEqual == null) {
			throw new RuntimeException("endRowIndex is null");
		}
		this.endRowIndexGreaterThanOrEqual = endRowIndexGreaterThanOrEqual;
		return this;
	}

	public ConvertElemTmplQuery endRowIndexLessThanOrEqual(Integer endRowIndexLessThanOrEqual) {
		if (endRowIndexLessThanOrEqual == null) {
			throw new RuntimeException("endRowIndex is null");
		}
		this.endRowIndexLessThanOrEqual = endRowIndexLessThanOrEqual;
		return this;
	}

	public ConvertElemTmplQuery endRowIndexs(List<Integer> endRowIndexs) {
		if (endRowIndexs == null) {
			throw new RuntimeException("endRowIndexs is empty ");
		}
		this.endRowIndexs = endRowIndexs;
		return this;
	}

	public ConvertElemTmplQuery endColIndex(Integer endColIndex) {
		if (endColIndex == null) {
			throw new RuntimeException("endColIndex is null");
		}
		this.endColIndex = endColIndex;
		return this;
	}

	public ConvertElemTmplQuery endColIndexGreaterThanOrEqual(Integer endColIndexGreaterThanOrEqual) {
		if (endColIndexGreaterThanOrEqual == null) {
			throw new RuntimeException("endColIndex is null");
		}
		this.endColIndexGreaterThanOrEqual = endColIndexGreaterThanOrEqual;
		return this;
	}

	public ConvertElemTmplQuery endColIndexLessThanOrEqual(Integer endColIndexLessThanOrEqual) {
		if (endColIndexLessThanOrEqual == null) {
			throw new RuntimeException("endColIndex is null");
		}
		this.endColIndexLessThanOrEqual = endColIndexLessThanOrEqual;
		return this;
	}

	public ConvertElemTmplQuery endColIndexs(List<Integer> endColIndexs) {
		if (endColIndexs == null) {
			throw new RuntimeException("endColIndexs is empty ");
		}
		this.endColIndexs = endColIndexs;
		return this;
	}

	public ConvertElemTmplQuery createDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual) {
		if (createDatetimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createDatetime is null");
		}
		this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
		return this;
	}

	public ConvertElemTmplQuery createDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual) {
		if (createDatetimeLessThanOrEqual == null) {
			throw new RuntimeException("createDatetime is null");
		}
		this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
		return this;
	}

	public ConvertElemTmplQuery modifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual) {
		if (modifyDatetimeGreaterThanOrEqual == null) {
			throw new RuntimeException("modifyDatetime is null");
		}
		this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
		return this;
	}

	public ConvertElemTmplQuery modifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual) {
		if (modifyDatetimeLessThanOrEqual == null) {
			throw new RuntimeException("modifyDatetime is null");
		}
		this.modifyDatetimeLessThanOrEqual = modifyDatetimeLessThanOrEqual;
		return this;
	}

	public ConvertElemTmplQuery repinfoListId(String repinfoListId) {
		if (repinfoListId == null) {
			throw new RuntimeException("repinfoListId is null");
		}
		this.repinfoListId = repinfoListId;
		return this;
	}

	public ConvertElemTmplQuery repinfoListIdLike(String repinfoListIdLike) {
		if (repinfoListIdLike == null) {
			throw new RuntimeException("repinfoListId is null");
		}
		this.repinfoListIdLike = repinfoListIdLike;
		return this;
	}

	public ConvertElemTmplQuery repinfoListIds(List<String> repinfoListIds) {
		if (repinfoListIds == null) {
			throw new RuntimeException("repinfoListIds is empty ");
		}
		this.repinfoListIds = repinfoListIds;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("cvtId".equals(sortColumn)) {
				orderBy = "E.CVT_ID_" + a_x;
			}

			if ("elemType".equals(sortColumn)) {
				orderBy = "E.ELEM_TYPE_" + a_x;
			}

			if ("elemName".equals(sortColumn)) {
				orderBy = "E.ELEM_NAME_" + a_x;
			}

			if ("elemId".equals(sortColumn)) {
				orderBy = "E.ELEM_ID_" + a_x;
			}

			if ("dType".equals(sortColumn)) {
				orderBy = "E.DTYPE_" + a_x;
			}

			if ("len".equals(sortColumn)) {
				orderBy = "E.LEN_" + a_x;
			}

			if ("digit".equals(sortColumn)) {
				orderBy = "E.DIGIT_" + a_x;
			}

			if ("defaultVal".equals(sortColumn)) {
				orderBy = "E.DEFAULT_VAL_" + a_x;
			}

			if ("rowIndex".equals(sortColumn)) {
				orderBy = "E.ROW_INDEX_" + a_x;
			}

			if ("colIndex".equals(sortColumn)) {
				orderBy = "E.COL_INDEX_" + a_x;
			}

			if ("readOnly".equals(sortColumn)) {
				orderBy = "E.READONLY_" + a_x;
			}

			if ("print".equals(sortColumn)) {
				orderBy = "E.PRINT_" + a_x;
			}

			if ("display".equals(sortColumn)) {
				orderBy = "E.DISPLAY_" + a_x;
			}

			if ("isMustFill".equals(sortColumn)) {
				orderBy = "E.ISMUSTFILL_" + a_x;
			}

			if ("isDataOnly".equals(sortColumn)) {
				orderBy = "E.ISDATAONLY_" + a_x;
			}

			if ("direction".equals(sortColumn)) {
				orderBy = "E.DIRECTION_" + a_x;
			}

			if ("vararea".equals(sortColumn)) {
				orderBy = "E.VAR_AREA_" + a_x;
			}

			if ("endRowIndex".equals(sortColumn)) {
				orderBy = "E.END_ROW_INDEX_" + a_x;
			}

			if ("endColIndex".equals(sortColumn)) {
				orderBy = "E.END_COL_INDEX_" + a_x;
			}

			if ("createDatetime".equals(sortColumn)) {
				orderBy = "E.CREATE_DATETIME_" + a_x;
			}

			if ("modifyDatetime".equals(sortColumn)) {
				orderBy = "E.MODIFY_DATETIME_" + a_x;
			}

			if ("repinfoListId".equals(sortColumn)) {
				orderBy = "E.REPINFO_LISTID_" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("cvtElemId", "CVT_ELEM_ID_");
		addColumn("cvtId", "CVT_ID_");
		addColumn("elemType", "ELEM_TYPE_");
		addColumn("elemName", "ELEM_NAME_");
		addColumn("elemId", "ELEM_ID_");
		addColumn("dType", "DTYPE_");
		addColumn("len", "LEN_");
		addColumn("digit", "DIGIT_");
		addColumn("defaultVal", "DEFAULT_VAL_");
		addColumn("rowIndex", "ROW_INDEX_");
		addColumn("colIndex", "COL_INDEX_");
		addColumn("readOnly", "READONLY_");
		addColumn("print", "PRINT_");
		addColumn("display", "DISPLAY_");
		addColumn("isMustFill", "ISMUSTFILL_");
		addColumn("isDataOnly", "ISDATAONLY_");
		addColumn("direction", "DIRECTION_");
		addColumn("vararea", "VAR_AREA_");
		addColumn("endRowIndex", "END_ROW_INDEX_");
		addColumn("endColIndex", "END_COL_INDEX_");
		addColumn("createDatetime", "CREATE_DATETIME_");
		addColumn("modifyDatetime", "MODIFY_DATETIME_");
		addColumn("repinfoListId", "REPINFO_LISTID_");
	}

}