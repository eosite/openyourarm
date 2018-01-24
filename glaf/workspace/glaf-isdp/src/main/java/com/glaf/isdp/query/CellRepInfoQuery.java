package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class CellRepInfoQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> listIds;
	protected Collection<String> appActorIds;
	protected String indexId;
	protected String indexIdLike;
	protected List<String> indexIds;
	protected String frmType;
	protected String frmTypeLike;
	protected List<String> frmTypes;
	protected String isSystem;
	protected String isSystemLike;
	protected List<String> isSystems;
	protected String fname;
	protected String fnameLike;
	protected List<String> fnames;
	protected String dname;
	protected String dnameLike;
	protected List<String> dnames;
	protected String dtype;
	protected String dtypeLike;
	protected List<String> dtypes;
	protected String showType;
	protected String showTypeLike;
	protected List<String> showTypes;
	protected Integer strLen;
	protected Integer strLenGreaterThanOrEqual;
	protected Integer strLenLessThanOrEqual;
	protected List<Integer> strLens;
	protected String form;
	protected String formLike;
	protected List<String> forms;
	protected String inType;
	protected String inTypeLike;
	protected List<String> inTypes;
	protected String hintID;
	protected String hintIDLike;
	protected List<String> hintIDs;
	protected Integer listNo;
	protected Integer listNoGreaterThanOrEqual;
	protected Integer listNoLessThanOrEqual;
	protected List<Integer> listNos;
	protected String ztype;
	protected String ztypeLike;
	protected List<String> ztypes;
	protected String isMustFill;
	protected String isMustFillLike;
	protected List<String> isMustFills;
	protected String isListShow;
	protected String isListShowLike;
	protected List<String> isListShows;
	protected Integer listWeigth;
	protected Integer listWeigthGreaterThanOrEqual;
	protected Integer listWeigthLessThanOrEqual;
	protected List<Integer> listWeigths;
	protected String panid;
	protected String panidLike;
	protected List<String> panids;
	protected String isAllWidth;
	protected String isAllWidthLike;
	protected List<String> isAllWidths;
	protected String istName;
	protected String istNameLike;
	protected List<String> istNames;
	protected String fileDotFileId;
	protected String fileDotFileIdLike;
	protected List<String> fileDotFileIds;
	protected Integer dataPoint;
	protected Integer dataPointGreaterThanOrEqual;
	protected Integer dataPointLessThanOrEqual;
	protected List<Integer> dataPoints;
	protected String defaultValue;
	protected String defaultValueLike;
	protected List<String> defaultValues;
	protected String isSubTable;
	protected String isSubTableLike;
	protected List<String> isSubTables;
	protected String isDataOnly;
	protected String isDataOnlyLike;
	protected List<String> isDataOnlys;
	protected Integer checkType;
	protected Integer checkTypeGreaterThanOrEqual;
	protected Integer checkTypeLessThanOrEqual;
	protected List<Integer> checkTypes;
	protected String orderId;
	protected String orderIdLike;
	protected List<String> orderIds;
	protected Integer cellType;
	protected Integer cellTypeGreaterThanOrEqual;
	protected Integer cellTypeLessThanOrEqual;
	protected List<Integer> cellTypes;
	protected String ostTableName;
	protected String ostTableNameLike;
	protected List<String> ostTableNames;
	protected Integer ostRow;
	protected Integer ostRowGreaterThanOrEqual;
	protected Integer ostRowLessThanOrEqual;
	protected List<Integer> ostRows;
	protected Integer ostCol;
	protected Integer ostColGreaterThanOrEqual;
	protected Integer ostColLessThanOrEqual;
	protected List<Integer> ostCols;
	protected String hintList;
	protected String hintListLike;
	protected List<String> hintLists;
	protected String ostCellId;
	protected String ostCellIdLike;
	protected List<String> ostCellIds;
	protected String oldDName;
	protected String oldDNameLike;
	protected List<String> oldDNames;
	protected String ostCellName;
	protected String ostCellNameLike;
	protected List<String> ostCellNames;
	protected String ostFormula;
	protected String ostFormulaLike;
	protected List<String> ostFormulas;
	protected Integer ostColor;
	protected Integer ostColorGreaterThanOrEqual;
	protected Integer ostColorLessThanOrEqual;
	protected List<Integer> ostColors;
	protected Integer ostSumType;
	protected Integer ostSumTypeGreaterThanOrEqual;
	protected Integer ostSumTypeLessThanOrEqual;
	protected List<Integer> ostSumTypes;
	protected String dataTableName;
	protected String dataTableNameLike;
	protected List<String> dataTableNames;
	protected String dataFieldName;
	protected String dataFieldNameLike;
	protected List<String> dataFieldNames;
	protected Integer orderCon;
	protected Integer orderConGreaterThanOrEqual;
	protected Integer orderConLessThanOrEqual;
	protected List<Integer> orderCons;
	protected Integer conNum;
	protected Integer conNumGreaterThanOrEqual;
	protected Integer conNumLessThanOrEqual;
	protected List<Integer> conNums;
	protected Integer nodeIndex;
	protected Integer nodeIndexGreaterThanOrEqual;
	protected Integer nodeIndexLessThanOrEqual;
	protected List<Integer> nodeIndexs;
	protected String typeId;
	protected String typeIdLike;
	protected List<String> typeIds;
	protected String userIndex;
	protected String userIndexLike;
	protected List<String> userIndexs;
	protected Integer orderIndex;
	protected Integer orderIndexGreaterThanOrEqual;
	protected Integer orderIndexLessThanOrEqual;
	protected List<Integer> orderIndexs;
	protected String parentDName;
	protected String parentDNameLike;
	protected List<String> parentDNames;
	protected Integer sysAuto;
	protected Integer sysAutoGreaterThanOrEqual;
	protected Integer sysAutoLessThanOrEqual;
	protected List<Integer> sysAutos;
	protected Integer orderIndexF;
	protected Integer orderIndexFGreaterThanOrEqual;
	protected Integer orderIndexFLessThanOrEqual;
	protected List<Integer> orderIndexFs;
	protected String orderIdF;
	protected String orderIdFLike;
	protected List<String> orderIdFs;
	protected Integer orderConF;
	protected Integer orderConFGreaterThanOrEqual;
	protected Integer orderConFLessThanOrEqual;
	protected List<Integer> orderConFs;
	protected Integer isPrintValue;
	protected Integer isPrintValueGreaterThanOrEqual;
	protected Integer isPrintValueLessThanOrEqual;
	protected List<Integer> isPrintValues;
	protected Integer isShowValueOnLast;
	protected Integer isShowValueOnLastGreaterThanOrEqual;
	protected Integer isShowValueOnLastLessThanOrEqual;
	protected List<Integer> isShowValueOnLasts;
	protected Integer isBankRoundType;
	protected Integer isBankRoundTypeGreaterThanOrEqual;
	protected Integer isBankRoundTypeLessThanOrEqual;
	protected List<Integer> isBankRoundTypes;
	protected Double floatBankRoundGreaterThanOrEqual;
	protected Double floatBankRoundLessThanOrEqual;
	protected Integer intUseWBSPlace;
	protected Integer intUseWBSPlaceGreaterThanOrEqual;
	protected Integer intUseWBSPlaceLessThanOrEqual;
	protected List<Integer> intUseWBSPlaces;

	public CellRepInfoQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getIndexId() {
		return indexId;
	}

	public String getIndexIdLike() {
		if (indexIdLike != null && indexIdLike.trim().length() > 0) {
			if (!indexIdLike.startsWith("%")) {
				indexIdLike = "%" + indexIdLike;
			}
			if (!indexIdLike.endsWith("%")) {
				indexIdLike = indexIdLike + "%";
			}
		}
		return indexIdLike;
	}

	public List<String> getIndexIds() {
		return indexIds;
	}

	public String getFrmType() {
		return frmType;
	}

	public String getFrmTypeLike() {
		if (frmTypeLike != null && frmTypeLike.trim().length() > 0) {
			if (!frmTypeLike.startsWith("%")) {
				frmTypeLike = "%" + frmTypeLike;
			}
			if (!frmTypeLike.endsWith("%")) {
				frmTypeLike = frmTypeLike + "%";
			}
		}
		return frmTypeLike;
	}

	public List<String> getFrmTypes() {
		return frmTypes;
	}

	public String getIsSystem() {
		return isSystem;
	}

	public String getIsSystemLike() {
		if (isSystemLike != null && isSystemLike.trim().length() > 0) {
			if (!isSystemLike.startsWith("%")) {
				isSystemLike = "%" + isSystemLike;
			}
			if (!isSystemLike.endsWith("%")) {
				isSystemLike = isSystemLike + "%";
			}
		}
		return isSystemLike;
	}

	public List<String> getIsSystems() {
		return isSystems;
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

	public String getDname() {
		return dname;
	}

	public String getDnameLike() {
		if (dnameLike != null && dnameLike.trim().length() > 0) {
			if (!dnameLike.startsWith("%")) {
				dnameLike = "%" + dnameLike;
			}
			if (!dnameLike.endsWith("%")) {
				dnameLike = dnameLike + "%";
			}
		}
		return dnameLike;
	}

	public List<String> getDnames() {
		return dnames;
	}

	public String getDtype() {
		return dtype;
	}

	public String getDtypeLike() {
		if (dtypeLike != null && dtypeLike.trim().length() > 0) {
			if (!dtypeLike.startsWith("%")) {
				dtypeLike = "%" + dtypeLike;
			}
			if (!dtypeLike.endsWith("%")) {
				dtypeLike = dtypeLike + "%";
			}
		}
		return dtypeLike;
	}

	public List<String> getDtypes() {
		return dtypes;
	}

	public String getShowType() {
		return showType;
	}

	public String getShowTypeLike() {
		if (showTypeLike != null && showTypeLike.trim().length() > 0) {
			if (!showTypeLike.startsWith("%")) {
				showTypeLike = "%" + showTypeLike;
			}
			if (!showTypeLike.endsWith("%")) {
				showTypeLike = showTypeLike + "%";
			}
		}
		return showTypeLike;
	}

	public List<String> getShowTypes() {
		return showTypes;
	}

	public Integer getStrLen() {
		return strLen;
	}

	public Integer getStrLenGreaterThanOrEqual() {
		return strLenGreaterThanOrEqual;
	}

	public Integer getStrLenLessThanOrEqual() {
		return strLenLessThanOrEqual;
	}

	public List<Integer> getStrLens() {
		return strLens;
	}

	public String getForm() {
		return form;
	}

	public String getFormLike() {
		if (formLike != null && formLike.trim().length() > 0) {
			if (!formLike.startsWith("%")) {
				formLike = "%" + formLike;
			}
			if (!formLike.endsWith("%")) {
				formLike = formLike + "%";
			}
		}
		return formLike;
	}

	public List<String> getForms() {
		return forms;
	}

	public String getInType() {
		return inType;
	}

	public String getInTypeLike() {
		if (inTypeLike != null && inTypeLike.trim().length() > 0) {
			if (!inTypeLike.startsWith("%")) {
				inTypeLike = "%" + inTypeLike;
			}
			if (!inTypeLike.endsWith("%")) {
				inTypeLike = inTypeLike + "%";
			}
		}
		return inTypeLike;
	}

	public List<String> getInTypes() {
		return inTypes;
	}

	public String getHintID() {
		return hintID;
	}

	public String getHintIDLike() {
		if (hintIDLike != null && hintIDLike.trim().length() > 0) {
			if (!hintIDLike.startsWith("%")) {
				hintIDLike = "%" + hintIDLike;
			}
			if (!hintIDLike.endsWith("%")) {
				hintIDLike = hintIDLike + "%";
			}
		}
		return hintIDLike;
	}

	public List<String> getHintIDs() {
		return hintIDs;
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

	public String getZtype() {
		return ztype;
	}

	public String getZtypeLike() {
		if (ztypeLike != null && ztypeLike.trim().length() > 0) {
			if (!ztypeLike.startsWith("%")) {
				ztypeLike = "%" + ztypeLike;
			}
			if (!ztypeLike.endsWith("%")) {
				ztypeLike = ztypeLike + "%";
			}
		}
		return ztypeLike;
	}

	public List<String> getZtypes() {
		return ztypes;
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

	public String getIsListShow() {
		return isListShow;
	}

	public String getIsListShowLike() {
		if (isListShowLike != null && isListShowLike.trim().length() > 0) {
			if (!isListShowLike.startsWith("%")) {
				isListShowLike = "%" + isListShowLike;
			}
			if (!isListShowLike.endsWith("%")) {
				isListShowLike = isListShowLike + "%";
			}
		}
		return isListShowLike;
	}

	public List<String> getIsListShows() {
		return isListShows;
	}

	public Integer getListWeigth() {
		return listWeigth;
	}

	public Integer getListWeigthGreaterThanOrEqual() {
		return listWeigthGreaterThanOrEqual;
	}

	public Integer getListWeigthLessThanOrEqual() {
		return listWeigthLessThanOrEqual;
	}

	public List<Integer> getListWeigths() {
		return listWeigths;
	}

	public String getPanid() {
		return panid;
	}

	public String getPanidLike() {
		if (panidLike != null && panidLike.trim().length() > 0) {
			if (!panidLike.startsWith("%")) {
				panidLike = "%" + panidLike;
			}
			if (!panidLike.endsWith("%")) {
				panidLike = panidLike + "%";
			}
		}
		return panidLike;
	}

	public List<String> getPanids() {
		return panids;
	}

	public String getIsAllWidth() {
		return isAllWidth;
	}

	public String getIsAllWidthLike() {
		if (isAllWidthLike != null && isAllWidthLike.trim().length() > 0) {
			if (!isAllWidthLike.startsWith("%")) {
				isAllWidthLike = "%" + isAllWidthLike;
			}
			if (!isAllWidthLike.endsWith("%")) {
				isAllWidthLike = isAllWidthLike + "%";
			}
		}
		return isAllWidthLike;
	}

	public List<String> getIsAllWidths() {
		return isAllWidths;
	}

	public String getIstName() {
		return istName;
	}

	public String getIstNameLike() {
		if (istNameLike != null && istNameLike.trim().length() > 0) {
			if (!istNameLike.startsWith("%")) {
				istNameLike = "%" + istNameLike;
			}
			if (!istNameLike.endsWith("%")) {
				istNameLike = istNameLike + "%";
			}
		}
		return istNameLike;
	}

	public List<String> getIstNames() {
		return istNames;
	}

	public String getFileDotFileId() {
		return fileDotFileId;
	}

	public String getFileDotFileIdLike() {
		if (fileDotFileIdLike != null && fileDotFileIdLike.trim().length() > 0) {
			if (!fileDotFileIdLike.startsWith("%")) {
				fileDotFileIdLike = "%" + fileDotFileIdLike;
			}
			if (!fileDotFileIdLike.endsWith("%")) {
				fileDotFileIdLike = fileDotFileIdLike + "%";
			}
		}
		return fileDotFileIdLike;
	}

	public List<String> getFileDotFileIds() {
		return fileDotFileIds;
	}

	public Integer getDataPoint() {
		return dataPoint;
	}

	public Integer getDataPointGreaterThanOrEqual() {
		return dataPointGreaterThanOrEqual;
	}

	public Integer getDataPointLessThanOrEqual() {
		return dataPointLessThanOrEqual;
	}

	public List<Integer> getDataPoints() {
		return dataPoints;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public String getDefaultValueLike() {
		if (defaultValueLike != null && defaultValueLike.trim().length() > 0) {
			if (!defaultValueLike.startsWith("%")) {
				defaultValueLike = "%" + defaultValueLike;
			}
			if (!defaultValueLike.endsWith("%")) {
				defaultValueLike = defaultValueLike + "%";
			}
		}
		return defaultValueLike;
	}

	public List<String> getDefaultValues() {
		return defaultValues;
	}

	public String getIsSubTable() {
		return isSubTable;
	}

	public String getIsSubTableLike() {
		if (isSubTableLike != null && isSubTableLike.trim().length() > 0) {
			if (!isSubTableLike.startsWith("%")) {
				isSubTableLike = "%" + isSubTableLike;
			}
			if (!isSubTableLike.endsWith("%")) {
				isSubTableLike = isSubTableLike + "%";
			}
		}
		return isSubTableLike;
	}

	public List<String> getIsSubTables() {
		return isSubTables;
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

	public Integer getCheckType() {
		return checkType;
	}

	public Integer getCheckTypeGreaterThanOrEqual() {
		return checkTypeGreaterThanOrEqual;
	}

	public Integer getCheckTypeLessThanOrEqual() {
		return checkTypeLessThanOrEqual;
	}

	public List<Integer> getCheckTypes() {
		return checkTypes;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getOrderIdLike() {
		if (orderIdLike != null && orderIdLike.trim().length() > 0) {
			if (!orderIdLike.startsWith("%")) {
				orderIdLike = "%" + orderIdLike;
			}
			if (!orderIdLike.endsWith("%")) {
				orderIdLike = orderIdLike + "%";
			}
		}
		return orderIdLike;
	}

	public List<String> getOrderIds() {
		return orderIds;
	}

	public Integer getCellType() {
		return cellType;
	}

	public Integer getCellTypeGreaterThanOrEqual() {
		return cellTypeGreaterThanOrEqual;
	}

	public Integer getCellTypeLessThanOrEqual() {
		return cellTypeLessThanOrEqual;
	}

	public List<Integer> getCellTypes() {
		return cellTypes;
	}

	public String getOstTableName() {
		return ostTableName;
	}

	public String getOstTableNameLike() {
		if (ostTableNameLike != null && ostTableNameLike.trim().length() > 0) {
			if (!ostTableNameLike.startsWith("%")) {
				ostTableNameLike = "%" + ostTableNameLike;
			}
			if (!ostTableNameLike.endsWith("%")) {
				ostTableNameLike = ostTableNameLike + "%";
			}
		}
		return ostTableNameLike;
	}

	public List<String> getOstTableNames() {
		return ostTableNames;
	}

	public Integer getOstRow() {
		return ostRow;
	}

	public Integer getOstRowGreaterThanOrEqual() {
		return ostRowGreaterThanOrEqual;
	}

	public Integer getOstRowLessThanOrEqual() {
		return ostRowLessThanOrEqual;
	}

	public List<Integer> getOstRows() {
		return ostRows;
	}

	public Integer getOstCol() {
		return ostCol;
	}

	public Integer getOstColGreaterThanOrEqual() {
		return ostColGreaterThanOrEqual;
	}

	public Integer getOstColLessThanOrEqual() {
		return ostColLessThanOrEqual;
	}

	public List<Integer> getOstCols() {
		return ostCols;
	}

	public String getHintList() {
		return hintList;
	}

	public String getHintListLike() {
		if (hintListLike != null && hintListLike.trim().length() > 0) {
			if (!hintListLike.startsWith("%")) {
				hintListLike = "%" + hintListLike;
			}
			if (!hintListLike.endsWith("%")) {
				hintListLike = hintListLike + "%";
			}
		}
		return hintListLike;
	}

	public List<String> getHintLists() {
		return hintLists;
	}

	public String getOstCellId() {
		return ostCellId;
	}

	public String getOstCellIdLike() {
		if (ostCellIdLike != null && ostCellIdLike.trim().length() > 0) {
			if (!ostCellIdLike.startsWith("%")) {
				ostCellIdLike = "%" + ostCellIdLike;
			}
			if (!ostCellIdLike.endsWith("%")) {
				ostCellIdLike = ostCellIdLike + "%";
			}
		}
		return ostCellIdLike;
	}

	public List<String> getOstCellIds() {
		return ostCellIds;
	}

	public String getOldDName() {
		return oldDName;
	}

	public String getOldDNameLike() {
		if (oldDNameLike != null && oldDNameLike.trim().length() > 0) {
			if (!oldDNameLike.startsWith("%")) {
				oldDNameLike = "%" + oldDNameLike;
			}
			if (!oldDNameLike.endsWith("%")) {
				oldDNameLike = oldDNameLike + "%";
			}
		}
		return oldDNameLike;
	}

	public List<String> getOldDNames() {
		return oldDNames;
	}

	public String getOstCellName() {
		return ostCellName;
	}

	public String getOstCellNameLike() {
		if (ostCellNameLike != null && ostCellNameLike.trim().length() > 0) {
			if (!ostCellNameLike.startsWith("%")) {
				ostCellNameLike = "%" + ostCellNameLike;
			}
			if (!ostCellNameLike.endsWith("%")) {
				ostCellNameLike = ostCellNameLike + "%";
			}
		}
		return ostCellNameLike;
	}

	public List<String> getOstCellNames() {
		return ostCellNames;
	}

	public String getOstFormula() {
		return ostFormula;
	}

	public String getOstFormulaLike() {
		if (ostFormulaLike != null && ostFormulaLike.trim().length() > 0) {
			if (!ostFormulaLike.startsWith("%")) {
				ostFormulaLike = "%" + ostFormulaLike;
			}
			if (!ostFormulaLike.endsWith("%")) {
				ostFormulaLike = ostFormulaLike + "%";
			}
		}
		return ostFormulaLike;
	}

	public List<String> getOstFormulas() {
		return ostFormulas;
	}

	public Integer getOstColor() {
		return ostColor;
	}

	public Integer getOstColorGreaterThanOrEqual() {
		return ostColorGreaterThanOrEqual;
	}

	public Integer getOstColorLessThanOrEqual() {
		return ostColorLessThanOrEqual;
	}

	public List<Integer> getOstColors() {
		return ostColors;
	}

	public Integer getOstSumType() {
		return ostSumType;
	}

	public Integer getOstSumTypeGreaterThanOrEqual() {
		return ostSumTypeGreaterThanOrEqual;
	}

	public Integer getOstSumTypeLessThanOrEqual() {
		return ostSumTypeLessThanOrEqual;
	}

	public List<Integer> getOstSumTypes() {
		return ostSumTypes;
	}

	public String getDataTableName() {
		return dataTableName;
	}

	public String getDataTableNameLike() {
		if (dataTableNameLike != null && dataTableNameLike.trim().length() > 0) {
			if (!dataTableNameLike.startsWith("%")) {
				dataTableNameLike = "%" + dataTableNameLike;
			}
			if (!dataTableNameLike.endsWith("%")) {
				dataTableNameLike = dataTableNameLike + "%";
			}
		}
		return dataTableNameLike;
	}

	public List<String> getDataTableNames() {
		return dataTableNames;
	}

	public String getDataFieldName() {
		return dataFieldName;
	}

	public String getDataFieldNameLike() {
		if (dataFieldNameLike != null && dataFieldNameLike.trim().length() > 0) {
			if (!dataFieldNameLike.startsWith("%")) {
				dataFieldNameLike = "%" + dataFieldNameLike;
			}
			if (!dataFieldNameLike.endsWith("%")) {
				dataFieldNameLike = dataFieldNameLike + "%";
			}
		}
		return dataFieldNameLike;
	}

	public List<String> getDataFieldNames() {
		return dataFieldNames;
	}

	public Integer getOrderCon() {
		return orderCon;
	}

	public Integer getOrderConGreaterThanOrEqual() {
		return orderConGreaterThanOrEqual;
	}

	public Integer getOrderConLessThanOrEqual() {
		return orderConLessThanOrEqual;
	}

	public List<Integer> getOrderCons() {
		return orderCons;
	}

	public Integer getConNum() {
		return conNum;
	}

	public Integer getConNumGreaterThanOrEqual() {
		return conNumGreaterThanOrEqual;
	}

	public Integer getConNumLessThanOrEqual() {
		return conNumLessThanOrEqual;
	}

	public List<Integer> getConNums() {
		return conNums;
	}

	public Integer getNodeIndex() {
		return nodeIndex;
	}

	public Integer getNodeIndexGreaterThanOrEqual() {
		return nodeIndexGreaterThanOrEqual;
	}

	public Integer getNodeIndexLessThanOrEqual() {
		return nodeIndexLessThanOrEqual;
	}

	public List<Integer> getNodeIndexs() {
		return nodeIndexs;
	}

	public String getTypeId() {
		return typeId;
	}

	public String getTypeIdLike() {
		if (typeIdLike != null && typeIdLike.trim().length() > 0) {
			if (!typeIdLike.startsWith("%")) {
				typeIdLike = "%" + typeIdLike;
			}
			if (!typeIdLike.endsWith("%")) {
				typeIdLike = typeIdLike + "%";
			}
		}
		return typeIdLike;
	}

	public List<String> getTypeIds() {
		return typeIds;
	}

	public String getUserIndex() {
		return userIndex;
	}

	public String getUserIndexLike() {
		if (userIndexLike != null && userIndexLike.trim().length() > 0) {
			if (!userIndexLike.startsWith("%")) {
				userIndexLike = "%" + userIndexLike;
			}
			if (!userIndexLike.endsWith("%")) {
				userIndexLike = userIndexLike + "%";
			}
		}
		return userIndexLike;
	}

	public List<String> getUserIndexs() {
		return userIndexs;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public Integer getOrderIndexGreaterThanOrEqual() {
		return orderIndexGreaterThanOrEqual;
	}

	public Integer getOrderIndexLessThanOrEqual() {
		return orderIndexLessThanOrEqual;
	}

	public List<Integer> getOrderIndexs() {
		return orderIndexs;
	}

	public String getParentDName() {
		return parentDName;
	}

	public String getParentDNameLike() {
		if (parentDNameLike != null && parentDNameLike.trim().length() > 0) {
			if (!parentDNameLike.startsWith("%")) {
				parentDNameLike = "%" + parentDNameLike;
			}
			if (!parentDNameLike.endsWith("%")) {
				parentDNameLike = parentDNameLike + "%";
			}
		}
		return parentDNameLike;
	}

	public List<String> getParentDNames() {
		return parentDNames;
	}

	public Integer getSysAuto() {
		return sysAuto;
	}

	public Integer getSysAutoGreaterThanOrEqual() {
		return sysAutoGreaterThanOrEqual;
	}

	public Integer getSysAutoLessThanOrEqual() {
		return sysAutoLessThanOrEqual;
	}

	public List<Integer> getSysAutos() {
		return sysAutos;
	}

	public Integer getOrderIndexF() {
		return orderIndexF;
	}

	public Integer getOrderIndexFGreaterThanOrEqual() {
		return orderIndexFGreaterThanOrEqual;
	}

	public Integer getOrderIndexFLessThanOrEqual() {
		return orderIndexFLessThanOrEqual;
	}

	public List<Integer> getOrderIndexFs() {
		return orderIndexFs;
	}

	public String getOrderIdF() {
		return orderIdF;
	}

	public String getOrderIdFLike() {
		if (orderIdFLike != null && orderIdFLike.trim().length() > 0) {
			if (!orderIdFLike.startsWith("%")) {
				orderIdFLike = "%" + orderIdFLike;
			}
			if (!orderIdFLike.endsWith("%")) {
				orderIdFLike = orderIdFLike + "%";
			}
		}
		return orderIdFLike;
	}

	public List<String> getOrderIdFs() {
		return orderIdFs;
	}

	public Integer getOrderConF() {
		return orderConF;
	}

	public Integer getOrderConFGreaterThanOrEqual() {
		return orderConFGreaterThanOrEqual;
	}

	public Integer getOrderConFLessThanOrEqual() {
		return orderConFLessThanOrEqual;
	}

	public List<Integer> getOrderConFs() {
		return orderConFs;
	}

	public Integer getIsPrintValue() {
		return isPrintValue;
	}

	public Integer getIsPrintValueGreaterThanOrEqual() {
		return isPrintValueGreaterThanOrEqual;
	}

	public Integer getIsPrintValueLessThanOrEqual() {
		return isPrintValueLessThanOrEqual;
	}

	public List<Integer> getIsPrintValues() {
		return isPrintValues;
	}

	public Integer getIsShowValueOnLast() {
		return isShowValueOnLast;
	}

	public Integer getIsShowValueOnLastGreaterThanOrEqual() {
		return isShowValueOnLastGreaterThanOrEqual;
	}

	public Integer getIsShowValueOnLastLessThanOrEqual() {
		return isShowValueOnLastLessThanOrEqual;
	}

	public List<Integer> getIsShowValueOnLasts() {
		return isShowValueOnLasts;
	}

	public Integer getIsBankRoundType() {
		return isBankRoundType;
	}

	public Integer getIsBankRoundTypeGreaterThanOrEqual() {
		return isBankRoundTypeGreaterThanOrEqual;
	}

	public Integer getIsBankRoundTypeLessThanOrEqual() {
		return isBankRoundTypeLessThanOrEqual;
	}

	public List<Integer> getIsBankRoundTypes() {
		return isBankRoundTypes;
	}

	public Double getFloatBankRoundGreaterThanOrEqual() {
		return floatBankRoundGreaterThanOrEqual;
	}

	public Double getFloatBankRoundLessThanOrEqual() {
		return floatBankRoundLessThanOrEqual;
	}

	public Integer getIntUseWBSPlace() {
		return intUseWBSPlace;
	}

	public Integer getIntUseWBSPlaceGreaterThanOrEqual() {
		return intUseWBSPlaceGreaterThanOrEqual;
	}

	public Integer getIntUseWBSPlaceLessThanOrEqual() {
		return intUseWBSPlaceLessThanOrEqual;
	}

	public List<Integer> getIntUseWBSPlaces() {
		return intUseWBSPlaces;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	public void setIndexIdLike(String indexIdLike) {
		this.indexIdLike = indexIdLike;
	}

	public void setIndexIds(List<String> indexIds) {
		this.indexIds = indexIds;
	}

	public void setFrmType(String frmType) {
		this.frmType = frmType;
	}

	public void setFrmTypeLike(String frmTypeLike) {
		this.frmTypeLike = frmTypeLike;
	}

	public void setFrmTypes(List<String> frmTypes) {
		this.frmTypes = frmTypes;
	}

	public void setIsSystem(String isSystem) {
		this.isSystem = isSystem;
	}

	public void setIsSystemLike(String isSystemLike) {
		this.isSystemLike = isSystemLike;
	}

	public void setIsSystems(List<String> isSystems) {
		this.isSystems = isSystems;
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

	public void setDname(String dname) {
		this.dname = dname;
	}

	public void setDnameLike(String dnameLike) {
		this.dnameLike = dnameLike;
	}

	public void setDnames(List<String> dnames) {
		this.dnames = dnames;
	}

	public void setDtype(String dtype) {
		this.dtype = dtype;
	}

	public void setDtypeLike(String dtypeLike) {
		this.dtypeLike = dtypeLike;
	}

	public void setDtypes(List<String> dtypes) {
		this.dtypes = dtypes;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public void setShowTypeLike(String showTypeLike) {
		this.showTypeLike = showTypeLike;
	}

	public void setShowTypes(List<String> showTypes) {
		this.showTypes = showTypes;
	}

	public void setStrLen(Integer strLen) {
		this.strLen = strLen;
	}

	public void setStrLenGreaterThanOrEqual(Integer strLenGreaterThanOrEqual) {
		this.strLenGreaterThanOrEqual = strLenGreaterThanOrEqual;
	}

	public void setStrLenLessThanOrEqual(Integer strLenLessThanOrEqual) {
		this.strLenLessThanOrEqual = strLenLessThanOrEqual;
	}

	public void setStrLens(List<Integer> strLens) {
		this.strLens = strLens;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public void setFormLike(String formLike) {
		this.formLike = formLike;
	}

	public void setForms(List<String> forms) {
		this.forms = forms;
	}

	public void setInType(String inType) {
		this.inType = inType;
	}

	public void setInTypeLike(String inTypeLike) {
		this.inTypeLike = inTypeLike;
	}

	public void setInTypes(List<String> inTypes) {
		this.inTypes = inTypes;
	}

	public void setHintID(String hintID) {
		this.hintID = hintID;
	}

	public void setHintIDLike(String hintIDLike) {
		this.hintIDLike = hintIDLike;
	}

	public void setHintIDs(List<String> hintIDs) {
		this.hintIDs = hintIDs;
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

	public void setZtype(String ztype) {
		this.ztype = ztype;
	}

	public void setZtypeLike(String ztypeLike) {
		this.ztypeLike = ztypeLike;
	}

	public void setZtypes(List<String> ztypes) {
		this.ztypes = ztypes;
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

	public void setIsListShow(String isListShow) {
		this.isListShow = isListShow;
	}

	public void setIsListShowLike(String isListShowLike) {
		this.isListShowLike = isListShowLike;
	}

	public void setIsListShows(List<String> isListShows) {
		this.isListShows = isListShows;
	}

	public void setListWeigth(Integer listWeigth) {
		this.listWeigth = listWeigth;
	}

	public void setListWeigthGreaterThanOrEqual(
			Integer listWeigthGreaterThanOrEqual) {
		this.listWeigthGreaterThanOrEqual = listWeigthGreaterThanOrEqual;
	}

	public void setListWeigthLessThanOrEqual(Integer listWeigthLessThanOrEqual) {
		this.listWeigthLessThanOrEqual = listWeigthLessThanOrEqual;
	}

	public void setListWeigths(List<Integer> listWeigths) {
		this.listWeigths = listWeigths;
	}

	public void setPanid(String panid) {
		this.panid = panid;
	}

	public void setPanidLike(String panidLike) {
		this.panidLike = panidLike;
	}

	public void setPanids(List<String> panids) {
		this.panids = panids;
	}

	public void setIsAllWidth(String isAllWidth) {
		this.isAllWidth = isAllWidth;
	}

	public void setIsAllWidthLike(String isAllWidthLike) {
		this.isAllWidthLike = isAllWidthLike;
	}

	public void setIsAllWidths(List<String> isAllWidths) {
		this.isAllWidths = isAllWidths;
	}

	public void setIstName(String istName) {
		this.istName = istName;
	}

	public void setIstNameLike(String istNameLike) {
		this.istNameLike = istNameLike;
	}

	public void setIstNames(List<String> istNames) {
		this.istNames = istNames;
	}

	public void setFileDotFileId(String fileDotFileId) {
		this.fileDotFileId = fileDotFileId;
	}

	public void setFileDotFileIdLike(String fileDotFileIdLike) {
		this.fileDotFileIdLike = fileDotFileIdLike;
	}

	public void setFileDotFileIds(List<String> fileDotFileIds) {
		this.fileDotFileIds = fileDotFileIds;
	}

	public void setDataPoint(Integer dataPoint) {
		this.dataPoint = dataPoint;
	}

	public void setDataPointGreaterThanOrEqual(
			Integer dataPointGreaterThanOrEqual) {
		this.dataPointGreaterThanOrEqual = dataPointGreaterThanOrEqual;
	}

	public void setDataPointLessThanOrEqual(Integer dataPointLessThanOrEqual) {
		this.dataPointLessThanOrEqual = dataPointLessThanOrEqual;
	}

	public void setDataPoints(List<Integer> dataPoints) {
		this.dataPoints = dataPoints;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setDefaultValueLike(String defaultValueLike) {
		this.defaultValueLike = defaultValueLike;
	}

	public void setDefaultValues(List<String> defaultValues) {
		this.defaultValues = defaultValues;
	}

	public void setIsSubTable(String isSubTable) {
		this.isSubTable = isSubTable;
	}

	public void setIsSubTableLike(String isSubTableLike) {
		this.isSubTableLike = isSubTableLike;
	}

	public void setIsSubTables(List<String> isSubTables) {
		this.isSubTables = isSubTables;
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

	public void setCheckType(Integer checkType) {
		this.checkType = checkType;
	}

	public void setCheckTypeGreaterThanOrEqual(
			Integer checkTypeGreaterThanOrEqual) {
		this.checkTypeGreaterThanOrEqual = checkTypeGreaterThanOrEqual;
	}

	public void setCheckTypeLessThanOrEqual(Integer checkTypeLessThanOrEqual) {
		this.checkTypeLessThanOrEqual = checkTypeLessThanOrEqual;
	}

	public void setCheckTypes(List<Integer> checkTypes) {
		this.checkTypes = checkTypes;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setOrderIdLike(String orderIdLike) {
		this.orderIdLike = orderIdLike;
	}

	public void setOrderIds(List<String> orderIds) {
		this.orderIds = orderIds;
	}

	public void setCellType(Integer cellType) {
		this.cellType = cellType;
	}

	public void setCellTypeGreaterThanOrEqual(Integer cellTypeGreaterThanOrEqual) {
		this.cellTypeGreaterThanOrEqual = cellTypeGreaterThanOrEqual;
	}

	public void setCellTypeLessThanOrEqual(Integer cellTypeLessThanOrEqual) {
		this.cellTypeLessThanOrEqual = cellTypeLessThanOrEqual;
	}

	public void setCellTypes(List<Integer> cellTypes) {
		this.cellTypes = cellTypes;
	}

	public void setOstTableName(String ostTableName) {
		this.ostTableName = ostTableName;
	}

	public void setOstTableNameLike(String ostTableNameLike) {
		this.ostTableNameLike = ostTableNameLike;
	}

	public void setOstTableNames(List<String> ostTableNames) {
		this.ostTableNames = ostTableNames;
	}

	public void setOstRow(Integer ostRow) {
		this.ostRow = ostRow;
	}

	public void setOstRowGreaterThanOrEqual(Integer ostRowGreaterThanOrEqual) {
		this.ostRowGreaterThanOrEqual = ostRowGreaterThanOrEqual;
	}

	public void setOstRowLessThanOrEqual(Integer ostRowLessThanOrEqual) {
		this.ostRowLessThanOrEqual = ostRowLessThanOrEqual;
	}

	public void setOstRows(List<Integer> ostRows) {
		this.ostRows = ostRows;
	}

	public void setOstCol(Integer ostCol) {
		this.ostCol = ostCol;
	}

	public void setOstColGreaterThanOrEqual(Integer ostColGreaterThanOrEqual) {
		this.ostColGreaterThanOrEqual = ostColGreaterThanOrEqual;
	}

	public void setOstColLessThanOrEqual(Integer ostColLessThanOrEqual) {
		this.ostColLessThanOrEqual = ostColLessThanOrEqual;
	}

	public void setOstCols(List<Integer> ostCols) {
		this.ostCols = ostCols;
	}

	public void setHintList(String hintList) {
		this.hintList = hintList;
	}

	public void setHintListLike(String hintListLike) {
		this.hintListLike = hintListLike;
	}

	public void setHintLists(List<String> hintLists) {
		this.hintLists = hintLists;
	}

	public void setOstCellId(String ostCellId) {
		this.ostCellId = ostCellId;
	}

	public void setOstCellIdLike(String ostCellIdLike) {
		this.ostCellIdLike = ostCellIdLike;
	}

	public void setOstCellIds(List<String> ostCellIds) {
		this.ostCellIds = ostCellIds;
	}

	public void setOldDName(String oldDName) {
		this.oldDName = oldDName;
	}

	public void setOldDNameLike(String oldDNameLike) {
		this.oldDNameLike = oldDNameLike;
	}

	public void setOldDNames(List<String> oldDNames) {
		this.oldDNames = oldDNames;
	}

	public void setOstCellName(String ostCellName) {
		this.ostCellName = ostCellName;
	}

	public void setOstCellNameLike(String ostCellNameLike) {
		this.ostCellNameLike = ostCellNameLike;
	}

	public void setOstCellNames(List<String> ostCellNames) {
		this.ostCellNames = ostCellNames;
	}

	public void setOstFormula(String ostFormula) {
		this.ostFormula = ostFormula;
	}

	public void setOstFormulaLike(String ostFormulaLike) {
		this.ostFormulaLike = ostFormulaLike;
	}

	public void setOstFormulas(List<String> ostFormulas) {
		this.ostFormulas = ostFormulas;
	}

	public void setOstColor(Integer ostColor) {
		this.ostColor = ostColor;
	}

	public void setOstColorGreaterThanOrEqual(Integer ostColorGreaterThanOrEqual) {
		this.ostColorGreaterThanOrEqual = ostColorGreaterThanOrEqual;
	}

	public void setOstColorLessThanOrEqual(Integer ostColorLessThanOrEqual) {
		this.ostColorLessThanOrEqual = ostColorLessThanOrEqual;
	}

	public void setOstColors(List<Integer> ostColors) {
		this.ostColors = ostColors;
	}

	public void setOstSumType(Integer ostSumType) {
		this.ostSumType = ostSumType;
	}

	public void setOstSumTypeGreaterThanOrEqual(
			Integer ostSumTypeGreaterThanOrEqual) {
		this.ostSumTypeGreaterThanOrEqual = ostSumTypeGreaterThanOrEqual;
	}

	public void setOstSumTypeLessThanOrEqual(Integer ostSumTypeLessThanOrEqual) {
		this.ostSumTypeLessThanOrEqual = ostSumTypeLessThanOrEqual;
	}

	public void setOstSumTypes(List<Integer> ostSumTypes) {
		this.ostSumTypes = ostSumTypes;
	}

	public void setDataTableName(String dataTableName) {
		this.dataTableName = dataTableName;
	}

	public void setDataTableNameLike(String dataTableNameLike) {
		this.dataTableNameLike = dataTableNameLike;
	}

	public void setDataTableNames(List<String> dataTableNames) {
		this.dataTableNames = dataTableNames;
	}

	public void setDataFieldName(String dataFieldName) {
		this.dataFieldName = dataFieldName;
	}

	public void setDataFieldNameLike(String dataFieldNameLike) {
		this.dataFieldNameLike = dataFieldNameLike;
	}

	public void setDataFieldNames(List<String> dataFieldNames) {
		this.dataFieldNames = dataFieldNames;
	}

	public void setOrderCon(Integer orderCon) {
		this.orderCon = orderCon;
	}

	public void setOrderConGreaterThanOrEqual(Integer orderConGreaterThanOrEqual) {
		this.orderConGreaterThanOrEqual = orderConGreaterThanOrEqual;
	}

	public void setOrderConLessThanOrEqual(Integer orderConLessThanOrEqual) {
		this.orderConLessThanOrEqual = orderConLessThanOrEqual;
	}

	public void setOrderCons(List<Integer> orderCons) {
		this.orderCons = orderCons;
	}

	public void setConNum(Integer conNum) {
		this.conNum = conNum;
	}

	public void setConNumGreaterThanOrEqual(Integer conNumGreaterThanOrEqual) {
		this.conNumGreaterThanOrEqual = conNumGreaterThanOrEqual;
	}

	public void setConNumLessThanOrEqual(Integer conNumLessThanOrEqual) {
		this.conNumLessThanOrEqual = conNumLessThanOrEqual;
	}

	public void setConNums(List<Integer> conNums) {
		this.conNums = conNums;
	}

	public void setNodeIndex(Integer nodeIndex) {
		this.nodeIndex = nodeIndex;
	}

	public void setNodeIndexGreaterThanOrEqual(
			Integer nodeIndexGreaterThanOrEqual) {
		this.nodeIndexGreaterThanOrEqual = nodeIndexGreaterThanOrEqual;
	}

	public void setNodeIndexLessThanOrEqual(Integer nodeIndexLessThanOrEqual) {
		this.nodeIndexLessThanOrEqual = nodeIndexLessThanOrEqual;
	}

	public void setNodeIndexs(List<Integer> nodeIndexs) {
		this.nodeIndexs = nodeIndexs;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public void setTypeIdLike(String typeIdLike) {
		this.typeIdLike = typeIdLike;
	}

	public void setTypeIds(List<String> typeIds) {
		this.typeIds = typeIds;
	}

	public void setUserIndex(String userIndex) {
		this.userIndex = userIndex;
	}

	public void setUserIndexLike(String userIndexLike) {
		this.userIndexLike = userIndexLike;
	}

	public void setUserIndexs(List<String> userIndexs) {
		this.userIndexs = userIndexs;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public void setOrderIndexGreaterThanOrEqual(
			Integer orderIndexGreaterThanOrEqual) {
		this.orderIndexGreaterThanOrEqual = orderIndexGreaterThanOrEqual;
	}

	public void setOrderIndexLessThanOrEqual(Integer orderIndexLessThanOrEqual) {
		this.orderIndexLessThanOrEqual = orderIndexLessThanOrEqual;
	}

	public void setOrderIndexs(List<Integer> orderIndexs) {
		this.orderIndexs = orderIndexs;
	}

	public void setParentDName(String parentDName) {
		this.parentDName = parentDName;
	}

	public void setParentDNameLike(String parentDNameLike) {
		this.parentDNameLike = parentDNameLike;
	}

	public void setParentDNames(List<String> parentDNames) {
		this.parentDNames = parentDNames;
	}

	public void setSysAuto(Integer sysAuto) {
		this.sysAuto = sysAuto;
	}

	public void setSysAutoGreaterThanOrEqual(Integer sysAutoGreaterThanOrEqual) {
		this.sysAutoGreaterThanOrEqual = sysAutoGreaterThanOrEqual;
	}

	public void setSysAutoLessThanOrEqual(Integer sysAutoLessThanOrEqual) {
		this.sysAutoLessThanOrEqual = sysAutoLessThanOrEqual;
	}

	public void setSysAutos(List<Integer> sysAutos) {
		this.sysAutos = sysAutos;
	}

	public void setOrderIndexF(Integer orderIndexF) {
		this.orderIndexF = orderIndexF;
	}

	public void setOrderIndexFGreaterThanOrEqual(
			Integer orderIndexFGreaterThanOrEqual) {
		this.orderIndexFGreaterThanOrEqual = orderIndexFGreaterThanOrEqual;
	}

	public void setOrderIndexFLessThanOrEqual(Integer orderIndexFLessThanOrEqual) {
		this.orderIndexFLessThanOrEqual = orderIndexFLessThanOrEqual;
	}

	public void setOrderIndexFs(List<Integer> orderIndexFs) {
		this.orderIndexFs = orderIndexFs;
	}

	public void setOrderIdF(String orderIdF) {
		this.orderIdF = orderIdF;
	}

	public void setOrderIdFLike(String orderIdFLike) {
		this.orderIdFLike = orderIdFLike;
	}

	public void setOrderIdFs(List<String> orderIdFs) {
		this.orderIdFs = orderIdFs;
	}

	public void setOrderConF(Integer orderConF) {
		this.orderConF = orderConF;
	}

	public void setOrderConFGreaterThanOrEqual(
			Integer orderConFGreaterThanOrEqual) {
		this.orderConFGreaterThanOrEqual = orderConFGreaterThanOrEqual;
	}

	public void setOrderConFLessThanOrEqual(Integer orderConFLessThanOrEqual) {
		this.orderConFLessThanOrEqual = orderConFLessThanOrEqual;
	}

	public void setOrderConFs(List<Integer> orderConFs) {
		this.orderConFs = orderConFs;
	}

	public void setIsPrintValue(Integer isPrintValue) {
		this.isPrintValue = isPrintValue;
	}

	public void setIsPrintValueGreaterThanOrEqual(
			Integer isPrintValueGreaterThanOrEqual) {
		this.isPrintValueGreaterThanOrEqual = isPrintValueGreaterThanOrEqual;
	}

	public void setIsPrintValueLessThanOrEqual(
			Integer isPrintValueLessThanOrEqual) {
		this.isPrintValueLessThanOrEqual = isPrintValueLessThanOrEqual;
	}

	public void setIsPrintValues(List<Integer> isPrintValues) {
		this.isPrintValues = isPrintValues;
	}

	public void setIsShowValueOnLast(Integer isShowValueOnLast) {
		this.isShowValueOnLast = isShowValueOnLast;
	}

	public void setIsShowValueOnLastGreaterThanOrEqual(
			Integer isShowValueOnLastGreaterThanOrEqual) {
		this.isShowValueOnLastGreaterThanOrEqual = isShowValueOnLastGreaterThanOrEqual;
	}

	public void setIsShowValueOnLastLessThanOrEqual(
			Integer isShowValueOnLastLessThanOrEqual) {
		this.isShowValueOnLastLessThanOrEqual = isShowValueOnLastLessThanOrEqual;
	}

	public void setIsShowValueOnLasts(List<Integer> isShowValueOnLasts) {
		this.isShowValueOnLasts = isShowValueOnLasts;
	}

	public void setIsBankRoundType(Integer isBankRoundType) {
		this.isBankRoundType = isBankRoundType;
	}

	public void setIsBankRoundTypeGreaterThanOrEqual(
			Integer isBankRoundTypeGreaterThanOrEqual) {
		this.isBankRoundTypeGreaterThanOrEqual = isBankRoundTypeGreaterThanOrEqual;
	}

	public void setIsBankRoundTypeLessThanOrEqual(
			Integer isBankRoundTypeLessThanOrEqual) {
		this.isBankRoundTypeLessThanOrEqual = isBankRoundTypeLessThanOrEqual;
	}

	public void setIsBankRoundTypes(List<Integer> isBankRoundTypes) {
		this.isBankRoundTypes = isBankRoundTypes;
	}

	public void setFloatBankRoundGreaterThanOrEqual(
			Double floatBankRoundGreaterThanOrEqual) {
		this.floatBankRoundGreaterThanOrEqual = floatBankRoundGreaterThanOrEqual;
	}

	public void setFloatBankRoundLessThanOrEqual(
			Double floatBankRoundLessThanOrEqual) {
		this.floatBankRoundLessThanOrEqual = floatBankRoundLessThanOrEqual;
	}

	public void setIntUseWBSPlace(Integer intUseWBSPlace) {
		this.intUseWBSPlace = intUseWBSPlace;
	}

	public void setIntUseWBSPlaceGreaterThanOrEqual(
			Integer intUseWBSPlaceGreaterThanOrEqual) {
		this.intUseWBSPlaceGreaterThanOrEqual = intUseWBSPlaceGreaterThanOrEqual;
	}

	public void setIntUseWBSPlaceLessThanOrEqual(
			Integer intUseWBSPlaceLessThanOrEqual) {
		this.intUseWBSPlaceLessThanOrEqual = intUseWBSPlaceLessThanOrEqual;
	}

	public void setIntUseWBSPlaces(List<Integer> intUseWBSPlaces) {
		this.intUseWBSPlaces = intUseWBSPlaces;
	}

	public CellRepInfoQuery indexId(String indexId) {
		if (indexId == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexId = indexId;
		return this;
	}

	public CellRepInfoQuery indexIdLike(String indexIdLike) {
		if (indexIdLike == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexIdLike = indexIdLike;
		return this;
	}

	public CellRepInfoQuery indexIds(List<String> indexIds) {
		if (indexIds == null) {
			throw new RuntimeException("indexIds is empty ");
		}
		this.indexIds = indexIds;
		return this;
	}

	public CellRepInfoQuery frmType(String frmType) {
		if (frmType == null) {
			throw new RuntimeException("frmType is null");
		}
		this.frmType = frmType;
		return this;
	}

	public CellRepInfoQuery frmTypeLike(String frmTypeLike) {
		if (frmTypeLike == null) {
			throw new RuntimeException("frmType is null");
		}
		this.frmTypeLike = frmTypeLike;
		return this;
	}

	public CellRepInfoQuery frmTypes(List<String> frmTypes) {
		if (frmTypes == null) {
			throw new RuntimeException("frmTypes is empty ");
		}
		this.frmTypes = frmTypes;
		return this;
	}

	public CellRepInfoQuery isSystem(String isSystem) {
		if (isSystem == null) {
			throw new RuntimeException("isSystem is null");
		}
		this.isSystem = isSystem;
		return this;
	}

	public CellRepInfoQuery isSystemLike(String isSystemLike) {
		if (isSystemLike == null) {
			throw new RuntimeException("isSystem is null");
		}
		this.isSystemLike = isSystemLike;
		return this;
	}

	public CellRepInfoQuery isSystems(List<String> isSystems) {
		if (isSystems == null) {
			throw new RuntimeException("isSystems is empty ");
		}
		this.isSystems = isSystems;
		return this;
	}

	public CellRepInfoQuery fname(String fname) {
		if (fname == null) {
			throw new RuntimeException("fname is null");
		}
		this.fname = fname;
		return this;
	}

	public CellRepInfoQuery fnameLike(String fnameLike) {
		if (fnameLike == null) {
			throw new RuntimeException("fname is null");
		}
		this.fnameLike = fnameLike;
		return this;
	}

	public CellRepInfoQuery fnames(List<String> fnames) {
		if (fnames == null) {
			throw new RuntimeException("fnames is empty ");
		}
		this.fnames = fnames;
		return this;
	}

	public CellRepInfoQuery dname(String dname) {
		if (dname == null) {
			throw new RuntimeException("dname is null");
		}
		this.dname = dname;
		return this;
	}

	public CellRepInfoQuery dnameLike(String dnameLike) {
		if (dnameLike == null) {
			throw new RuntimeException("dname is null");
		}
		this.dnameLike = dnameLike;
		return this;
	}

	public CellRepInfoQuery dnames(List<String> dnames) {
		if (dnames == null) {
			throw new RuntimeException("dnames is empty ");
		}
		this.dnames = dnames;
		return this;
	}

	public CellRepInfoQuery dtype(String dtype) {
		if (dtype == null) {
			throw new RuntimeException("dtype is null");
		}
		this.dtype = dtype;
		return this;
	}

	public CellRepInfoQuery dtypeLike(String dtypeLike) {
		if (dtypeLike == null) {
			throw new RuntimeException("dtype is null");
		}
		this.dtypeLike = dtypeLike;
		return this;
	}

	public CellRepInfoQuery dtypes(List<String> dtypes) {
		if (dtypes == null) {
			throw new RuntimeException("dtypes is empty ");
		}
		this.dtypes = dtypes;
		return this;
	}

	public CellRepInfoQuery showType(String showType) {
		if (showType == null) {
			throw new RuntimeException("showType is null");
		}
		this.showType = showType;
		return this;
	}

	public CellRepInfoQuery showTypeLike(String showTypeLike) {
		if (showTypeLike == null) {
			throw new RuntimeException("showType is null");
		}
		this.showTypeLike = showTypeLike;
		return this;
	}

	public CellRepInfoQuery showTypes(List<String> showTypes) {
		if (showTypes == null) {
			throw new RuntimeException("showTypes is empty ");
		}
		this.showTypes = showTypes;
		return this;
	}

	public CellRepInfoQuery strLen(Integer strLen) {
		if (strLen == null) {
			throw new RuntimeException("strLen is null");
		}
		this.strLen = strLen;
		return this;
	}

	public CellRepInfoQuery strLenGreaterThanOrEqual(
			Integer strLenGreaterThanOrEqual) {
		if (strLenGreaterThanOrEqual == null) {
			throw new RuntimeException("strLen is null");
		}
		this.strLenGreaterThanOrEqual = strLenGreaterThanOrEqual;
		return this;
	}

	public CellRepInfoQuery strLenLessThanOrEqual(Integer strLenLessThanOrEqual) {
		if (strLenLessThanOrEqual == null) {
			throw new RuntimeException("strLen is null");
		}
		this.strLenLessThanOrEqual = strLenLessThanOrEqual;
		return this;
	}

	public CellRepInfoQuery strLens(List<Integer> strLens) {
		if (strLens == null) {
			throw new RuntimeException("strLens is empty ");
		}
		this.strLens = strLens;
		return this;
	}

	public CellRepInfoQuery form(String form) {
		if (form == null) {
			throw new RuntimeException("form is null");
		}
		this.form = form;
		return this;
	}

	public CellRepInfoQuery formLike(String formLike) {
		if (formLike == null) {
			throw new RuntimeException("form is null");
		}
		this.formLike = formLike;
		return this;
	}

	public CellRepInfoQuery forms(List<String> forms) {
		if (forms == null) {
			throw new RuntimeException("forms is empty ");
		}
		this.forms = forms;
		return this;
	}

	public CellRepInfoQuery inType(String inType) {
		if (inType == null) {
			throw new RuntimeException("inType is null");
		}
		this.inType = inType;
		return this;
	}

	public CellRepInfoQuery inTypeLike(String inTypeLike) {
		if (inTypeLike == null) {
			throw new RuntimeException("inType is null");
		}
		this.inTypeLike = inTypeLike;
		return this;
	}

	public CellRepInfoQuery inTypes(List<String> inTypes) {
		if (inTypes == null) {
			throw new RuntimeException("inTypes is empty ");
		}
		this.inTypes = inTypes;
		return this;
	}

	public CellRepInfoQuery hintID(String hintID) {
		if (hintID == null) {
			throw new RuntimeException("hintID is null");
		}
		this.hintID = hintID;
		return this;
	}

	public CellRepInfoQuery hintIDLike(String hintIDLike) {
		if (hintIDLike == null) {
			throw new RuntimeException("hintID is null");
		}
		this.hintIDLike = hintIDLike;
		return this;
	}

	public CellRepInfoQuery hintIDs(List<String> hintIDs) {
		if (hintIDs == null) {
			throw new RuntimeException("hintIDs is empty ");
		}
		this.hintIDs = hintIDs;
		return this;
	}

	public CellRepInfoQuery listNo(Integer listNo) {
		if (listNo == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNo = listNo;
		return this;
	}

	public CellRepInfoQuery listNoGreaterThanOrEqual(
			Integer listNoGreaterThanOrEqual) {
		if (listNoGreaterThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoGreaterThanOrEqual = listNoGreaterThanOrEqual;
		return this;
	}

	public CellRepInfoQuery listNoLessThanOrEqual(Integer listNoLessThanOrEqual) {
		if (listNoLessThanOrEqual == null) {
			throw new RuntimeException("listNo is null");
		}
		this.listNoLessThanOrEqual = listNoLessThanOrEqual;
		return this;
	}

	public CellRepInfoQuery listNos(List<Integer> listNos) {
		if (listNos == null) {
			throw new RuntimeException("listNos is empty ");
		}
		this.listNos = listNos;
		return this;
	}

	public CellRepInfoQuery ztype(String ztype) {
		if (ztype == null) {
			throw new RuntimeException("ztype is null");
		}
		this.ztype = ztype;
		return this;
	}

	public CellRepInfoQuery ztypeLike(String ztypeLike) {
		if (ztypeLike == null) {
			throw new RuntimeException("ztype is null");
		}
		this.ztypeLike = ztypeLike;
		return this;
	}

	public CellRepInfoQuery ztypes(List<String> ztypes) {
		if (ztypes == null) {
			throw new RuntimeException("ztypes is empty ");
		}
		this.ztypes = ztypes;
		return this;
	}

	public CellRepInfoQuery isMustFill(String isMustFill) {
		if (isMustFill == null) {
			throw new RuntimeException("isMustFill is null");
		}
		this.isMustFill = isMustFill;
		return this;
	}

	public CellRepInfoQuery isMustFillLike(String isMustFillLike) {
		if (isMustFillLike == null) {
			throw new RuntimeException("isMustFill is null");
		}
		this.isMustFillLike = isMustFillLike;
		return this;
	}

	public CellRepInfoQuery isMustFills(List<String> isMustFills) {
		if (isMustFills == null) {
			throw new RuntimeException("isMustFills is empty ");
		}
		this.isMustFills = isMustFills;
		return this;
	}

	public CellRepInfoQuery isListShow(String isListShow) {
		if (isListShow == null) {
			throw new RuntimeException("isListShow is null");
		}
		this.isListShow = isListShow;
		return this;
	}

	public CellRepInfoQuery isListShowLike(String isListShowLike) {
		if (isListShowLike == null) {
			throw new RuntimeException("isListShow is null");
		}
		this.isListShowLike = isListShowLike;
		return this;
	}

	public CellRepInfoQuery isListShows(List<String> isListShows) {
		if (isListShows == null) {
			throw new RuntimeException("isListShows is empty ");
		}
		this.isListShows = isListShows;
		return this;
	}

	public CellRepInfoQuery listWeigth(Integer listWeigth) {
		if (listWeigth == null) {
			throw new RuntimeException("listWeigth is null");
		}
		this.listWeigth = listWeigth;
		return this;
	}

	public CellRepInfoQuery listWeigthGreaterThanOrEqual(
			Integer listWeigthGreaterThanOrEqual) {
		if (listWeigthGreaterThanOrEqual == null) {
			throw new RuntimeException("listWeigth is null");
		}
		this.listWeigthGreaterThanOrEqual = listWeigthGreaterThanOrEqual;
		return this;
	}

	public CellRepInfoQuery listWeigthLessThanOrEqual(
			Integer listWeigthLessThanOrEqual) {
		if (listWeigthLessThanOrEqual == null) {
			throw new RuntimeException("listWeigth is null");
		}
		this.listWeigthLessThanOrEqual = listWeigthLessThanOrEqual;
		return this;
	}

	public CellRepInfoQuery listWeigths(List<Integer> listWeigths) {
		if (listWeigths == null) {
			throw new RuntimeException("listWeigths is empty ");
		}
		this.listWeigths = listWeigths;
		return this;
	}

	public CellRepInfoQuery panid(String panid) {
		if (panid == null) {
			throw new RuntimeException("panid is null");
		}
		this.panid = panid;
		return this;
	}

	public CellRepInfoQuery panidLike(String panidLike) {
		if (panidLike == null) {
			throw new RuntimeException("panid is null");
		}
		this.panidLike = panidLike;
		return this;
	}

	public CellRepInfoQuery panids(List<String> panids) {
		if (panids == null) {
			throw new RuntimeException("panids is empty ");
		}
		this.panids = panids;
		return this;
	}

	public CellRepInfoQuery isAllWidth(String isAllWidth) {
		if (isAllWidth == null) {
			throw new RuntimeException("isAllWidth is null");
		}
		this.isAllWidth = isAllWidth;
		return this;
	}

	public CellRepInfoQuery isAllWidthLike(String isAllWidthLike) {
		if (isAllWidthLike == null) {
			throw new RuntimeException("isAllWidth is null");
		}
		this.isAllWidthLike = isAllWidthLike;
		return this;
	}

	public CellRepInfoQuery isAllWidths(List<String> isAllWidths) {
		if (isAllWidths == null) {
			throw new RuntimeException("isAllWidths is empty ");
		}
		this.isAllWidths = isAllWidths;
		return this;
	}

	public CellRepInfoQuery istName(String istName) {
		if (istName == null) {
			throw new RuntimeException("istName is null");
		}
		this.istName = istName;
		return this;
	}

	public CellRepInfoQuery istNameLike(String istNameLike) {
		if (istNameLike == null) {
			throw new RuntimeException("istName is null");
		}
		this.istNameLike = istNameLike;
		return this;
	}

	public CellRepInfoQuery istNames(List<String> istNames) {
		if (istNames == null) {
			throw new RuntimeException("istNames is empty ");
		}
		this.istNames = istNames;
		return this;
	}

	public CellRepInfoQuery fileDotFileId(String fileDotFileId) {
		if (fileDotFileId == null) {
			throw new RuntimeException("fileDotFileId is null");
		}
		this.fileDotFileId = fileDotFileId;
		return this;
	}

	public CellRepInfoQuery fileDotFileIdLike(String fileDotFileIdLike) {
		if (fileDotFileIdLike == null) {
			throw new RuntimeException("fileDotFileId is null");
		}
		this.fileDotFileIdLike = fileDotFileIdLike;
		return this;
	}

	public CellRepInfoQuery fileDotFileIds(List<String> fileDotFileIds) {
		if (fileDotFileIds == null) {
			throw new RuntimeException("fileDotFileIds is empty ");
		}
		this.fileDotFileIds = fileDotFileIds;
		return this;
	}

	public CellRepInfoQuery dataPoint(Integer dataPoint) {
		if (dataPoint == null) {
			throw new RuntimeException("dataPoint is null");
		}
		this.dataPoint = dataPoint;
		return this;
	}

	public CellRepInfoQuery dataPointGreaterThanOrEqual(
			Integer dataPointGreaterThanOrEqual) {
		if (dataPointGreaterThanOrEqual == null) {
			throw new RuntimeException("dataPoint is null");
		}
		this.dataPointGreaterThanOrEqual = dataPointGreaterThanOrEqual;
		return this;
	}

	public CellRepInfoQuery dataPointLessThanOrEqual(
			Integer dataPointLessThanOrEqual) {
		if (dataPointLessThanOrEqual == null) {
			throw new RuntimeException("dataPoint is null");
		}
		this.dataPointLessThanOrEqual = dataPointLessThanOrEqual;
		return this;
	}

	public CellRepInfoQuery dataPoints(List<Integer> dataPoints) {
		if (dataPoints == null) {
			throw new RuntimeException("dataPoints is empty ");
		}
		this.dataPoints = dataPoints;
		return this;
	}

	public CellRepInfoQuery defaultValue(String defaultValue) {
		if (defaultValue == null) {
			throw new RuntimeException("defaultValue is null");
		}
		this.defaultValue = defaultValue;
		return this;
	}

	public CellRepInfoQuery defaultValueLike(String defaultValueLike) {
		if (defaultValueLike == null) {
			throw new RuntimeException("defaultValue is null");
		}
		this.defaultValueLike = defaultValueLike;
		return this;
	}

	public CellRepInfoQuery defaultValues(List<String> defaultValues) {
		if (defaultValues == null) {
			throw new RuntimeException("defaultValues is empty ");
		}
		this.defaultValues = defaultValues;
		return this;
	}

	public CellRepInfoQuery isSubTable(String isSubTable) {
		if (isSubTable == null) {
			throw new RuntimeException("isSubTable is null");
		}
		this.isSubTable = isSubTable;
		return this;
	}

	public CellRepInfoQuery isSubTableLike(String isSubTableLike) {
		if (isSubTableLike == null) {
			throw new RuntimeException("isSubTable is null");
		}
		this.isSubTableLike = isSubTableLike;
		return this;
	}

	public CellRepInfoQuery isSubTables(List<String> isSubTables) {
		if (isSubTables == null) {
			throw new RuntimeException("isSubTables is empty ");
		}
		this.isSubTables = isSubTables;
		return this;
	}

	public CellRepInfoQuery isDataOnly(String isDataOnly) {
		if (isDataOnly == null) {
			throw new RuntimeException("isDataOnly is null");
		}
		this.isDataOnly = isDataOnly;
		return this;
	}

	public CellRepInfoQuery isDataOnlyLike(String isDataOnlyLike) {
		if (isDataOnlyLike == null) {
			throw new RuntimeException("isDataOnly is null");
		}
		this.isDataOnlyLike = isDataOnlyLike;
		return this;
	}

	public CellRepInfoQuery isDataOnlys(List<String> isDataOnlys) {
		if (isDataOnlys == null) {
			throw new RuntimeException("isDataOnlys is empty ");
		}
		this.isDataOnlys = isDataOnlys;
		return this;
	}

	public CellRepInfoQuery checkType(Integer checkType) {
		if (checkType == null) {
			throw new RuntimeException("checkType is null");
		}
		this.checkType = checkType;
		return this;
	}

	public CellRepInfoQuery checkTypeGreaterThanOrEqual(
			Integer checkTypeGreaterThanOrEqual) {
		if (checkTypeGreaterThanOrEqual == null) {
			throw new RuntimeException("checkType is null");
		}
		this.checkTypeGreaterThanOrEqual = checkTypeGreaterThanOrEqual;
		return this;
	}

	public CellRepInfoQuery checkTypeLessThanOrEqual(
			Integer checkTypeLessThanOrEqual) {
		if (checkTypeLessThanOrEqual == null) {
			throw new RuntimeException("checkType is null");
		}
		this.checkTypeLessThanOrEqual = checkTypeLessThanOrEqual;
		return this;
	}

	public CellRepInfoQuery checkTypes(List<Integer> checkTypes) {
		if (checkTypes == null) {
			throw new RuntimeException("checkTypes is empty ");
		}
		this.checkTypes = checkTypes;
		return this;
	}

	public CellRepInfoQuery orderId(String orderId) {
		if (orderId == null) {
			throw new RuntimeException("orderId is null");
		}
		this.orderId = orderId;
		return this;
	}

	public CellRepInfoQuery orderIdLike(String orderIdLike) {
		if (orderIdLike == null) {
			throw new RuntimeException("orderId is null");
		}
		this.orderIdLike = orderIdLike;
		return this;
	}

	public CellRepInfoQuery orderIds(List<String> orderIds) {
		if (orderIds == null) {
			throw new RuntimeException("orderIds is empty ");
		}
		this.orderIds = orderIds;
		return this;
	}

	public CellRepInfoQuery cellType(Integer cellType) {
		if (cellType == null) {
			throw new RuntimeException("cellType is null");
		}
		this.cellType = cellType;
		return this;
	}

	public CellRepInfoQuery cellTypeGreaterThanOrEqual(
			Integer cellTypeGreaterThanOrEqual) {
		if (cellTypeGreaterThanOrEqual == null) {
			throw new RuntimeException("cellType is null");
		}
		this.cellTypeGreaterThanOrEqual = cellTypeGreaterThanOrEqual;
		return this;
	}

	public CellRepInfoQuery cellTypeLessThanOrEqual(
			Integer cellTypeLessThanOrEqual) {
		if (cellTypeLessThanOrEqual == null) {
			throw new RuntimeException("cellType is null");
		}
		this.cellTypeLessThanOrEqual = cellTypeLessThanOrEqual;
		return this;
	}

	public CellRepInfoQuery cellTypes(List<Integer> cellTypes) {
		if (cellTypes == null) {
			throw new RuntimeException("cellTypes is empty ");
		}
		this.cellTypes = cellTypes;
		return this;
	}

	public CellRepInfoQuery ostTableName(String ostTableName) {
		if (ostTableName == null) {
			throw new RuntimeException("ostTableName is null");
		}
		this.ostTableName = ostTableName;
		return this;
	}

	public CellRepInfoQuery ostTableNameLike(String ostTableNameLike) {
		if (ostTableNameLike == null) {
			throw new RuntimeException("ostTableName is null");
		}
		this.ostTableNameLike = ostTableNameLike;
		return this;
	}

	public CellRepInfoQuery ostTableNames(List<String> ostTableNames) {
		if (ostTableNames == null) {
			throw new RuntimeException("ostTableNames is empty ");
		}
		this.ostTableNames = ostTableNames;
		return this;
	}

	public CellRepInfoQuery ostRow(Integer ostRow) {
		if (ostRow == null) {
			throw new RuntimeException("ostRow is null");
		}
		this.ostRow = ostRow;
		return this;
	}

	public CellRepInfoQuery ostRowGreaterThanOrEqual(
			Integer ostRowGreaterThanOrEqual) {
		if (ostRowGreaterThanOrEqual == null) {
			throw new RuntimeException("ostRow is null");
		}
		this.ostRowGreaterThanOrEqual = ostRowGreaterThanOrEqual;
		return this;
	}

	public CellRepInfoQuery ostRowLessThanOrEqual(Integer ostRowLessThanOrEqual) {
		if (ostRowLessThanOrEqual == null) {
			throw new RuntimeException("ostRow is null");
		}
		this.ostRowLessThanOrEqual = ostRowLessThanOrEqual;
		return this;
	}

	public CellRepInfoQuery ostRows(List<Integer> ostRows) {
		if (ostRows == null) {
			throw new RuntimeException("ostRows is empty ");
		}
		this.ostRows = ostRows;
		return this;
	}

	public CellRepInfoQuery ostCol(Integer ostCol) {
		if (ostCol == null) {
			throw new RuntimeException("ostCol is null");
		}
		this.ostCol = ostCol;
		return this;
	}

	public CellRepInfoQuery ostColGreaterThanOrEqual(
			Integer ostColGreaterThanOrEqual) {
		if (ostColGreaterThanOrEqual == null) {
			throw new RuntimeException("ostCol is null");
		}
		this.ostColGreaterThanOrEqual = ostColGreaterThanOrEqual;
		return this;
	}

	public CellRepInfoQuery ostColLessThanOrEqual(Integer ostColLessThanOrEqual) {
		if (ostColLessThanOrEqual == null) {
			throw new RuntimeException("ostCol is null");
		}
		this.ostColLessThanOrEqual = ostColLessThanOrEqual;
		return this;
	}

	public CellRepInfoQuery ostCols(List<Integer> ostCols) {
		if (ostCols == null) {
			throw new RuntimeException("ostCols is empty ");
		}
		this.ostCols = ostCols;
		return this;
	}

	public CellRepInfoQuery hintList(String hintList) {
		if (hintList == null) {
			throw new RuntimeException("hintList is null");
		}
		this.hintList = hintList;
		return this;
	}

	public CellRepInfoQuery hintListLike(String hintListLike) {
		if (hintListLike == null) {
			throw new RuntimeException("hintList is null");
		}
		this.hintListLike = hintListLike;
		return this;
	}

	public CellRepInfoQuery hintLists(List<String> hintLists) {
		if (hintLists == null) {
			throw new RuntimeException("hintLists is empty ");
		}
		this.hintLists = hintLists;
		return this;
	}

	public CellRepInfoQuery ostCellId(String ostCellId) {
		if (ostCellId == null) {
			throw new RuntimeException("ostCellId is null");
		}
		this.ostCellId = ostCellId;
		return this;
	}

	public CellRepInfoQuery ostCellIdLike(String ostCellIdLike) {
		if (ostCellIdLike == null) {
			throw new RuntimeException("ostCellId is null");
		}
		this.ostCellIdLike = ostCellIdLike;
		return this;
	}

	public CellRepInfoQuery ostCellIds(List<String> ostCellIds) {
		if (ostCellIds == null) {
			throw new RuntimeException("ostCellIds is empty ");
		}
		this.ostCellIds = ostCellIds;
		return this;
	}

	public CellRepInfoQuery oldDName(String oldDName) {
		if (oldDName == null) {
			throw new RuntimeException("oldDName is null");
		}
		this.oldDName = oldDName;
		return this;
	}

	public CellRepInfoQuery oldDNameLike(String oldDNameLike) {
		if (oldDNameLike == null) {
			throw new RuntimeException("oldDName is null");
		}
		this.oldDNameLike = oldDNameLike;
		return this;
	}

	public CellRepInfoQuery oldDNames(List<String> oldDNames) {
		if (oldDNames == null) {
			throw new RuntimeException("oldDNames is empty ");
		}
		this.oldDNames = oldDNames;
		return this;
	}

	public CellRepInfoQuery ostCellName(String ostCellName) {
		if (ostCellName == null) {
			throw new RuntimeException("ostCellName is null");
		}
		this.ostCellName = ostCellName;
		return this;
	}

	public CellRepInfoQuery ostCellNameLike(String ostCellNameLike) {
		if (ostCellNameLike == null) {
			throw new RuntimeException("ostCellName is null");
		}
		this.ostCellNameLike = ostCellNameLike;
		return this;
	}

	public CellRepInfoQuery ostCellNames(List<String> ostCellNames) {
		if (ostCellNames == null) {
			throw new RuntimeException("ostCellNames is empty ");
		}
		this.ostCellNames = ostCellNames;
		return this;
	}

	public CellRepInfoQuery ostFormula(String ostFormula) {
		if (ostFormula == null) {
			throw new RuntimeException("ostFormula is null");
		}
		this.ostFormula = ostFormula;
		return this;
	}

	public CellRepInfoQuery ostFormulaLike(String ostFormulaLike) {
		if (ostFormulaLike == null) {
			throw new RuntimeException("ostFormula is null");
		}
		this.ostFormulaLike = ostFormulaLike;
		return this;
	}

	public CellRepInfoQuery ostFormulas(List<String> ostFormulas) {
		if (ostFormulas == null) {
			throw new RuntimeException("ostFormulas is empty ");
		}
		this.ostFormulas = ostFormulas;
		return this;
	}

	public CellRepInfoQuery ostColor(Integer ostColor) {
		if (ostColor == null) {
			throw new RuntimeException("ostColor is null");
		}
		this.ostColor = ostColor;
		return this;
	}

	public CellRepInfoQuery ostColorGreaterThanOrEqual(
			Integer ostColorGreaterThanOrEqual) {
		if (ostColorGreaterThanOrEqual == null) {
			throw new RuntimeException("ostColor is null");
		}
		this.ostColorGreaterThanOrEqual = ostColorGreaterThanOrEqual;
		return this;
	}

	public CellRepInfoQuery ostColorLessThanOrEqual(
			Integer ostColorLessThanOrEqual) {
		if (ostColorLessThanOrEqual == null) {
			throw new RuntimeException("ostColor is null");
		}
		this.ostColorLessThanOrEqual = ostColorLessThanOrEqual;
		return this;
	}

	public CellRepInfoQuery ostColors(List<Integer> ostColors) {
		if (ostColors == null) {
			throw new RuntimeException("ostColors is empty ");
		}
		this.ostColors = ostColors;
		return this;
	}

	public CellRepInfoQuery ostSumType(Integer ostSumType) {
		if (ostSumType == null) {
			throw new RuntimeException("ostSumType is null");
		}
		this.ostSumType = ostSumType;
		return this;
	}

	public CellRepInfoQuery ostSumTypeGreaterThanOrEqual(
			Integer ostSumTypeGreaterThanOrEqual) {
		if (ostSumTypeGreaterThanOrEqual == null) {
			throw new RuntimeException("ostSumType is null");
		}
		this.ostSumTypeGreaterThanOrEqual = ostSumTypeGreaterThanOrEqual;
		return this;
	}

	public CellRepInfoQuery ostSumTypeLessThanOrEqual(
			Integer ostSumTypeLessThanOrEqual) {
		if (ostSumTypeLessThanOrEqual == null) {
			throw new RuntimeException("ostSumType is null");
		}
		this.ostSumTypeLessThanOrEqual = ostSumTypeLessThanOrEqual;
		return this;
	}

	public CellRepInfoQuery ostSumTypes(List<Integer> ostSumTypes) {
		if (ostSumTypes == null) {
			throw new RuntimeException("ostSumTypes is empty ");
		}
		this.ostSumTypes = ostSumTypes;
		return this;
	}

	public CellRepInfoQuery dataTableName(String dataTableName) {
		if (dataTableName == null) {
			throw new RuntimeException("dataTableName is null");
		}
		this.dataTableName = dataTableName;
		return this;
	}

	public CellRepInfoQuery dataTableNameLike(String dataTableNameLike) {
		if (dataTableNameLike == null) {
			throw new RuntimeException("dataTableName is null");
		}
		this.dataTableNameLike = dataTableNameLike;
		return this;
	}

	public CellRepInfoQuery dataTableNames(List<String> dataTableNames) {
		if (dataTableNames == null) {
			throw new RuntimeException("dataTableNames is empty ");
		}
		this.dataTableNames = dataTableNames;
		return this;
	}

	public CellRepInfoQuery dataFieldName(String dataFieldName) {
		if (dataFieldName == null) {
			throw new RuntimeException("dataFieldName is null");
		}
		this.dataFieldName = dataFieldName;
		return this;
	}

	public CellRepInfoQuery dataFieldNameLike(String dataFieldNameLike) {
		if (dataFieldNameLike == null) {
			throw new RuntimeException("dataFieldName is null");
		}
		this.dataFieldNameLike = dataFieldNameLike;
		return this;
	}

	public CellRepInfoQuery dataFieldNames(List<String> dataFieldNames) {
		if (dataFieldNames == null) {
			throw new RuntimeException("dataFieldNames is empty ");
		}
		this.dataFieldNames = dataFieldNames;
		return this;
	}

	public CellRepInfoQuery orderCon(Integer orderCon) {
		if (orderCon == null) {
			throw new RuntimeException("orderCon is null");
		}
		this.orderCon = orderCon;
		return this;
	}

	public CellRepInfoQuery orderConGreaterThanOrEqual(
			Integer orderConGreaterThanOrEqual) {
		if (orderConGreaterThanOrEqual == null) {
			throw new RuntimeException("orderCon is null");
		}
		this.orderConGreaterThanOrEqual = orderConGreaterThanOrEqual;
		return this;
	}

	public CellRepInfoQuery orderConLessThanOrEqual(
			Integer orderConLessThanOrEqual) {
		if (orderConLessThanOrEqual == null) {
			throw new RuntimeException("orderCon is null");
		}
		this.orderConLessThanOrEqual = orderConLessThanOrEqual;
		return this;
	}

	public CellRepInfoQuery orderCons(List<Integer> orderCons) {
		if (orderCons == null) {
			throw new RuntimeException("orderCons is empty ");
		}
		this.orderCons = orderCons;
		return this;
	}

	public CellRepInfoQuery conNum(Integer conNum) {
		if (conNum == null) {
			throw new RuntimeException("conNum is null");
		}
		this.conNum = conNum;
		return this;
	}

	public CellRepInfoQuery conNumGreaterThanOrEqual(
			Integer conNumGreaterThanOrEqual) {
		if (conNumGreaterThanOrEqual == null) {
			throw new RuntimeException("conNum is null");
		}
		this.conNumGreaterThanOrEqual = conNumGreaterThanOrEqual;
		return this;
	}

	public CellRepInfoQuery conNumLessThanOrEqual(Integer conNumLessThanOrEqual) {
		if (conNumLessThanOrEqual == null) {
			throw new RuntimeException("conNum is null");
		}
		this.conNumLessThanOrEqual = conNumLessThanOrEqual;
		return this;
	}

	public CellRepInfoQuery conNums(List<Integer> conNums) {
		if (conNums == null) {
			throw new RuntimeException("conNums is empty ");
		}
		this.conNums = conNums;
		return this;
	}

	public CellRepInfoQuery nodeIndex(Integer nodeIndex) {
		if (nodeIndex == null) {
			throw new RuntimeException("nodeIndex is null");
		}
		this.nodeIndex = nodeIndex;
		return this;
	}

	public CellRepInfoQuery nodeIndexGreaterThanOrEqual(
			Integer nodeIndexGreaterThanOrEqual) {
		if (nodeIndexGreaterThanOrEqual == null) {
			throw new RuntimeException("nodeIndex is null");
		}
		this.nodeIndexGreaterThanOrEqual = nodeIndexGreaterThanOrEqual;
		return this;
	}

	public CellRepInfoQuery nodeIndexLessThanOrEqual(
			Integer nodeIndexLessThanOrEqual) {
		if (nodeIndexLessThanOrEqual == null) {
			throw new RuntimeException("nodeIndex is null");
		}
		this.nodeIndexLessThanOrEqual = nodeIndexLessThanOrEqual;
		return this;
	}

	public CellRepInfoQuery nodeIndexs(List<Integer> nodeIndexs) {
		if (nodeIndexs == null) {
			throw new RuntimeException("nodeIndexs is empty ");
		}
		this.nodeIndexs = nodeIndexs;
		return this;
	}

	public CellRepInfoQuery typeId(String typeId) {
		if (typeId == null) {
			throw new RuntimeException("typeId is null");
		}
		this.typeId = typeId;
		return this;
	}

	public CellRepInfoQuery typeIdLike(String typeIdLike) {
		if (typeIdLike == null) {
			throw new RuntimeException("typeId is null");
		}
		this.typeIdLike = typeIdLike;
		return this;
	}

	public CellRepInfoQuery typeIds(List<String> typeIds) {
		if (typeIds == null) {
			throw new RuntimeException("typeIds is empty ");
		}
		this.typeIds = typeIds;
		return this;
	}

	public CellRepInfoQuery userIndex(String userIndex) {
		if (userIndex == null) {
			throw new RuntimeException("userIndex is null");
		}
		this.userIndex = userIndex;
		return this;
	}

	public CellRepInfoQuery userIndexLike(String userIndexLike) {
		if (userIndexLike == null) {
			throw new RuntimeException("userIndex is null");
		}
		this.userIndexLike = userIndexLike;
		return this;
	}

	public CellRepInfoQuery userIndexs(List<String> userIndexs) {
		if (userIndexs == null) {
			throw new RuntimeException("userIndexs is empty ");
		}
		this.userIndexs = userIndexs;
		return this;
	}

	public CellRepInfoQuery orderIndex(Integer orderIndex) {
		if (orderIndex == null) {
			throw new RuntimeException("orderIndex is null");
		}
		this.orderIndex = orderIndex;
		return this;
	}

	public CellRepInfoQuery orderIndexGreaterThanOrEqual(
			Integer orderIndexGreaterThanOrEqual) {
		if (orderIndexGreaterThanOrEqual == null) {
			throw new RuntimeException("orderIndex is null");
		}
		this.orderIndexGreaterThanOrEqual = orderIndexGreaterThanOrEqual;
		return this;
	}

	public CellRepInfoQuery orderIndexLessThanOrEqual(
			Integer orderIndexLessThanOrEqual) {
		if (orderIndexLessThanOrEqual == null) {
			throw new RuntimeException("orderIndex is null");
		}
		this.orderIndexLessThanOrEqual = orderIndexLessThanOrEqual;
		return this;
	}

	public CellRepInfoQuery orderIndexs(List<Integer> orderIndexs) {
		if (orderIndexs == null) {
			throw new RuntimeException("orderIndexs is empty ");
		}
		this.orderIndexs = orderIndexs;
		return this;
	}

	public CellRepInfoQuery parentDName(String parentDName) {
		if (parentDName == null) {
			throw new RuntimeException("parentDName is null");
		}
		this.parentDName = parentDName;
		return this;
	}

	public CellRepInfoQuery parentDNameLike(String parentDNameLike) {
		if (parentDNameLike == null) {
			throw new RuntimeException("parentDName is null");
		}
		this.parentDNameLike = parentDNameLike;
		return this;
	}

	public CellRepInfoQuery parentDNames(List<String> parentDNames) {
		if (parentDNames == null) {
			throw new RuntimeException("parentDNames is empty ");
		}
		this.parentDNames = parentDNames;
		return this;
	}

	public CellRepInfoQuery sysAuto(Integer sysAuto) {
		if (sysAuto == null) {
			throw new RuntimeException("sysAuto is null");
		}
		this.sysAuto = sysAuto;
		return this;
	}

	public CellRepInfoQuery sysAutoGreaterThanOrEqual(
			Integer sysAutoGreaterThanOrEqual) {
		if (sysAutoGreaterThanOrEqual == null) {
			throw new RuntimeException("sysAuto is null");
		}
		this.sysAutoGreaterThanOrEqual = sysAutoGreaterThanOrEqual;
		return this;
	}

	public CellRepInfoQuery sysAutoLessThanOrEqual(
			Integer sysAutoLessThanOrEqual) {
		if (sysAutoLessThanOrEqual == null) {
			throw new RuntimeException("sysAuto is null");
		}
		this.sysAutoLessThanOrEqual = sysAutoLessThanOrEqual;
		return this;
	}

	public CellRepInfoQuery sysAutos(List<Integer> sysAutos) {
		if (sysAutos == null) {
			throw new RuntimeException("sysAutos is empty ");
		}
		this.sysAutos = sysAutos;
		return this;
	}

	public CellRepInfoQuery orderIndexF(Integer orderIndexF) {
		if (orderIndexF == null) {
			throw new RuntimeException("orderIndexF is null");
		}
		this.orderIndexF = orderIndexF;
		return this;
	}

	public CellRepInfoQuery orderIndexFGreaterThanOrEqual(
			Integer orderIndexFGreaterThanOrEqual) {
		if (orderIndexFGreaterThanOrEqual == null) {
			throw new RuntimeException("orderIndexF is null");
		}
		this.orderIndexFGreaterThanOrEqual = orderIndexFGreaterThanOrEqual;
		return this;
	}

	public CellRepInfoQuery orderIndexFLessThanOrEqual(
			Integer orderIndexFLessThanOrEqual) {
		if (orderIndexFLessThanOrEqual == null) {
			throw new RuntimeException("orderIndexF is null");
		}
		this.orderIndexFLessThanOrEqual = orderIndexFLessThanOrEqual;
		return this;
	}

	public CellRepInfoQuery orderIndexFs(List<Integer> orderIndexFs) {
		if (orderIndexFs == null) {
			throw new RuntimeException("orderIndexFs is empty ");
		}
		this.orderIndexFs = orderIndexFs;
		return this;
	}

	public CellRepInfoQuery orderIdF(String orderIdF) {
		if (orderIdF == null) {
			throw new RuntimeException("orderIdF is null");
		}
		this.orderIdF = orderIdF;
		return this;
	}

	public CellRepInfoQuery orderIdFLike(String orderIdFLike) {
		if (orderIdFLike == null) {
			throw new RuntimeException("orderIdF is null");
		}
		this.orderIdFLike = orderIdFLike;
		return this;
	}

	public CellRepInfoQuery orderIdFs(List<String> orderIdFs) {
		if (orderIdFs == null) {
			throw new RuntimeException("orderIdFs is empty ");
		}
		this.orderIdFs = orderIdFs;
		return this;
	}

	public CellRepInfoQuery orderConF(Integer orderConF) {
		if (orderConF == null) {
			throw new RuntimeException("orderConF is null");
		}
		this.orderConF = orderConF;
		return this;
	}

	public CellRepInfoQuery orderConFGreaterThanOrEqual(
			Integer orderConFGreaterThanOrEqual) {
		if (orderConFGreaterThanOrEqual == null) {
			throw new RuntimeException("orderConF is null");
		}
		this.orderConFGreaterThanOrEqual = orderConFGreaterThanOrEqual;
		return this;
	}

	public CellRepInfoQuery orderConFLessThanOrEqual(
			Integer orderConFLessThanOrEqual) {
		if (orderConFLessThanOrEqual == null) {
			throw new RuntimeException("orderConF is null");
		}
		this.orderConFLessThanOrEqual = orderConFLessThanOrEqual;
		return this;
	}

	public CellRepInfoQuery orderConFs(List<Integer> orderConFs) {
		if (orderConFs == null) {
			throw new RuntimeException("orderConFs is empty ");
		}
		this.orderConFs = orderConFs;
		return this;
	}

	public CellRepInfoQuery isPrintValue(Integer isPrintValue) {
		if (isPrintValue == null) {
			throw new RuntimeException("isPrintValue is null");
		}
		this.isPrintValue = isPrintValue;
		return this;
	}

	public CellRepInfoQuery isPrintValueGreaterThanOrEqual(
			Integer isPrintValueGreaterThanOrEqual) {
		if (isPrintValueGreaterThanOrEqual == null) {
			throw new RuntimeException("isPrintValue is null");
		}
		this.isPrintValueGreaterThanOrEqual = isPrintValueGreaterThanOrEqual;
		return this;
	}

	public CellRepInfoQuery isPrintValueLessThanOrEqual(
			Integer isPrintValueLessThanOrEqual) {
		if (isPrintValueLessThanOrEqual == null) {
			throw new RuntimeException("isPrintValue is null");
		}
		this.isPrintValueLessThanOrEqual = isPrintValueLessThanOrEqual;
		return this;
	}

	public CellRepInfoQuery isPrintValues(List<Integer> isPrintValues) {
		if (isPrintValues == null) {
			throw new RuntimeException("isPrintValues is empty ");
		}
		this.isPrintValues = isPrintValues;
		return this;
	}

	public CellRepInfoQuery isShowValueOnLast(Integer isShowValueOnLast) {
		if (isShowValueOnLast == null) {
			throw new RuntimeException("isShowValueOnLast is null");
		}
		this.isShowValueOnLast = isShowValueOnLast;
		return this;
	}

	public CellRepInfoQuery isShowValueOnLastGreaterThanOrEqual(
			Integer isShowValueOnLastGreaterThanOrEqual) {
		if (isShowValueOnLastGreaterThanOrEqual == null) {
			throw new RuntimeException("isShowValueOnLast is null");
		}
		this.isShowValueOnLastGreaterThanOrEqual = isShowValueOnLastGreaterThanOrEqual;
		return this;
	}

	public CellRepInfoQuery isShowValueOnLastLessThanOrEqual(
			Integer isShowValueOnLastLessThanOrEqual) {
		if (isShowValueOnLastLessThanOrEqual == null) {
			throw new RuntimeException("isShowValueOnLast is null");
		}
		this.isShowValueOnLastLessThanOrEqual = isShowValueOnLastLessThanOrEqual;
		return this;
	}

	public CellRepInfoQuery isShowValueOnLasts(List<Integer> isShowValueOnLasts) {
		if (isShowValueOnLasts == null) {
			throw new RuntimeException("isShowValueOnLasts is empty ");
		}
		this.isShowValueOnLasts = isShowValueOnLasts;
		return this;
	}

	public CellRepInfoQuery isBankRoundType(Integer isBankRoundType) {
		if (isBankRoundType == null) {
			throw new RuntimeException("isBankRoundType is null");
		}
		this.isBankRoundType = isBankRoundType;
		return this;
	}

	public CellRepInfoQuery isBankRoundTypeGreaterThanOrEqual(
			Integer isBankRoundTypeGreaterThanOrEqual) {
		if (isBankRoundTypeGreaterThanOrEqual == null) {
			throw new RuntimeException("isBankRoundType is null");
		}
		this.isBankRoundTypeGreaterThanOrEqual = isBankRoundTypeGreaterThanOrEqual;
		return this;
	}

	public CellRepInfoQuery isBankRoundTypeLessThanOrEqual(
			Integer isBankRoundTypeLessThanOrEqual) {
		if (isBankRoundTypeLessThanOrEqual == null) {
			throw new RuntimeException("isBankRoundType is null");
		}
		this.isBankRoundTypeLessThanOrEqual = isBankRoundTypeLessThanOrEqual;
		return this;
	}

	public CellRepInfoQuery isBankRoundTypes(List<Integer> isBankRoundTypes) {
		if (isBankRoundTypes == null) {
			throw new RuntimeException("isBankRoundTypes is empty ");
		}
		this.isBankRoundTypes = isBankRoundTypes;
		return this;
	}

	public CellRepInfoQuery floatBankRoundGreaterThanOrEqual(
			Double floatBankRoundGreaterThanOrEqual) {
		if (floatBankRoundGreaterThanOrEqual == null) {
			throw new RuntimeException("floatBankRound is null");
		}
		this.floatBankRoundGreaterThanOrEqual = floatBankRoundGreaterThanOrEqual;
		return this;
	}

	public CellRepInfoQuery floatBankRoundLessThanOrEqual(
			Double floatBankRoundLessThanOrEqual) {
		if (floatBankRoundLessThanOrEqual == null) {
			throw new RuntimeException("floatBankRound is null");
		}
		this.floatBankRoundLessThanOrEqual = floatBankRoundLessThanOrEqual;
		return this;
	}

	public CellRepInfoQuery intUseWBSPlace(Integer intUseWBSPlace) {
		if (intUseWBSPlace == null) {
			throw new RuntimeException("intUseWBSPlace is null");
		}
		this.intUseWBSPlace = intUseWBSPlace;
		return this;
	}

	public CellRepInfoQuery intUseWBSPlaceGreaterThanOrEqual(
			Integer intUseWBSPlaceGreaterThanOrEqual) {
		if (intUseWBSPlaceGreaterThanOrEqual == null) {
			throw new RuntimeException("intUseWBSPlace is null");
		}
		this.intUseWBSPlaceGreaterThanOrEqual = intUseWBSPlaceGreaterThanOrEqual;
		return this;
	}

	public CellRepInfoQuery intUseWBSPlaceLessThanOrEqual(
			Integer intUseWBSPlaceLessThanOrEqual) {
		if (intUseWBSPlaceLessThanOrEqual == null) {
			throw new RuntimeException("intUseWBSPlace is null");
		}
		this.intUseWBSPlaceLessThanOrEqual = intUseWBSPlaceLessThanOrEqual;
		return this;
	}

	public CellRepInfoQuery intUseWBSPlaces(List<Integer> intUseWBSPlaces) {
		if (intUseWBSPlaces == null) {
			throw new RuntimeException("intUseWBSPlaces is empty ");
		}
		this.intUseWBSPlaces = intUseWBSPlaces;
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

			if ("frmType".equals(sortColumn)) {
				orderBy = "E.FRMTYPE" + a_x;
			}

			if ("isSystem".equals(sortColumn)) {
				orderBy = "E.ISSYSTEM" + a_x;
			}

			if ("fname".equals(sortColumn)) {
				orderBy = "E.FNAME" + a_x;
			}

			if ("dname".equals(sortColumn)) {
				orderBy = "E.DNAME" + a_x;
			}

			if ("dtype".equals(sortColumn)) {
				orderBy = "E.DTYPE" + a_x;
			}

			if ("showType".equals(sortColumn)) {
				orderBy = "E.SHOWTYPE" + a_x;
			}

			if ("strLen".equals(sortColumn)) {
				orderBy = "E.STRLEN" + a_x;
			}

			if ("form".equals(sortColumn)) {
				orderBy = "E.FORM" + a_x;
			}

			if ("inType".equals(sortColumn)) {
				orderBy = "E.INTYPE" + a_x;
			}

			if ("hintID".equals(sortColumn)) {
				orderBy = "E.HINTID" + a_x;
			}

			if ("listNo".equals(sortColumn)) {
				orderBy = "E.LISTNO" + a_x;
			}

			if ("ztype".equals(sortColumn)) {
				orderBy = "E.ZTYPE" + a_x;
			}

			if ("isMustFill".equals(sortColumn)) {
				orderBy = "E.ISMUSTFILL" + a_x;
			}

			if ("isListShow".equals(sortColumn)) {
				orderBy = "E.ISLISTSHOW" + a_x;
			}

			if ("listWeigth".equals(sortColumn)) {
				orderBy = "E.LISTWEIGTH" + a_x;
			}

			if ("panid".equals(sortColumn)) {
				orderBy = "E.PANID" + a_x;
			}

			if ("isAllWidth".equals(sortColumn)) {
				orderBy = "E.ISALLWIDTH" + a_x;
			}

			if ("istName".equals(sortColumn)) {
				orderBy = "E.ISTNAME" + a_x;
			}

			if ("fileDotFileId".equals(sortColumn)) {
				orderBy = "E.FILEDOT_FILEID" + a_x;
			}

			if ("dataPoint".equals(sortColumn)) {
				orderBy = "E.DATAPOINT" + a_x;
			}

			if ("defaultValue".equals(sortColumn)) {
				orderBy = "E.DEFAULTVALUE" + a_x;
			}

			if ("isSubTable".equals(sortColumn)) {
				orderBy = "E.ISSUBTABLE" + a_x;
			}

			if ("isDataOnly".equals(sortColumn)) {
				orderBy = "E.ISDATAONLY" + a_x;
			}

			if ("checkType".equals(sortColumn)) {
				orderBy = "E.CHECKTYPE" + a_x;
			}

			if ("orderId".equals(sortColumn)) {
				orderBy = "E.ORDERID" + a_x;
			}

			if ("cellType".equals(sortColumn)) {
				orderBy = "E.CELLTYPE" + a_x;
			}

			if ("ostTableName".equals(sortColumn)) {
				orderBy = "E.OST_TABLENAME" + a_x;
			}

			if ("ostRow".equals(sortColumn)) {
				orderBy = "E.OST_ROW" + a_x;
			}

			if ("ostCol".equals(sortColumn)) {
				orderBy = "E.OST_COL" + a_x;
			}

			if ("hintList".equals(sortColumn)) {
				orderBy = "E.HINTLIST" + a_x;
			}

			if ("ostCellId".equals(sortColumn)) {
				orderBy = "E.OST_CELLID" + a_x;
			}

			if ("oldDName".equals(sortColumn)) {
				orderBy = "E.OLDDNAME" + a_x;
			}

			if ("ostCellName".equals(sortColumn)) {
				orderBy = "E.OST_CELLNAME" + a_x;
			}

			if ("ostFormula".equals(sortColumn)) {
				orderBy = "E.OST_FORMULA" + a_x;
			}

			if ("ostColor".equals(sortColumn)) {
				orderBy = "E.OST_COLOR" + a_x;
			}

			if ("ostSumType".equals(sortColumn)) {
				orderBy = "E.OST_SUMTYPE" + a_x;
			}

			if ("dataTableName".equals(sortColumn)) {
				orderBy = "E.DATA_TABLENAME" + a_x;
			}

			if ("dataFieldName".equals(sortColumn)) {
				orderBy = "E.DATA_FIELDNAME" + a_x;
			}

			if ("orderCon".equals(sortColumn)) {
				orderBy = "E.ORDER_CON" + a_x;
			}

			if ("conNum".equals(sortColumn)) {
				orderBy = "E.CONNUM" + a_x;
			}

			if ("nodeIndex".equals(sortColumn)) {
				orderBy = "E.NODE_INDEX" + a_x;
			}

			if ("typeId".equals(sortColumn)) {
				orderBy = "E.TYPE_ID" + a_x;
			}

			if ("userIndex".equals(sortColumn)) {
				orderBy = "E.USERINDEX" + a_x;
			}

			if ("orderIndex".equals(sortColumn)) {
				orderBy = "E.ORDER_INDEX" + a_x;
			}

			if ("parentDName".equals(sortColumn)) {
				orderBy = "E.PARENT_DNAME" + a_x;
			}

			if ("sysAuto".equals(sortColumn)) {
				orderBy = "E.SYSAUTO" + a_x;
			}

			if ("orderIndexF".equals(sortColumn)) {
				orderBy = "E.ORDER_INDEX_F" + a_x;
			}

			if ("orderIdF".equals(sortColumn)) {
				orderBy = "E.ORDERID_F" + a_x;
			}

			if ("orderConF".equals(sortColumn)) {
				orderBy = "E.ORDER_CON_F" + a_x;
			}

			if ("isPrintValue".equals(sortColumn)) {
				orderBy = "E.ISPRINTVALUE" + a_x;
			}

			if ("isShowValueOnLast".equals(sortColumn)) {
				orderBy = "E.ISSHOWVALUEONLAST" + a_x;
			}

			if ("isBankRoundType".equals(sortColumn)) {
				orderBy = "E.ISBANKROUNDTYPE" + a_x;
			}

			if ("floatBankRound".equals(sortColumn)) {
				orderBy = "E.FLOATBANKROUND" + a_x;
			}

			if ("intUseWBSPlace".equals(sortColumn)) {
				orderBy = "E.INTUSEWBSPLACE" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("listId", "LISTID");
		addColumn("indexId", "INDEX_ID");
		addColumn("frmType", "FRMTYPE");
		addColumn("isSystem", "ISSYSTEM");
		addColumn("fname", "FNAME");
		addColumn("dname", "DNAME");
		addColumn("dtype", "DTYPE");
		addColumn("showType", "SHOWTYPE");
		addColumn("strLen", "STRLEN");
		addColumn("form", "FORM");
		addColumn("inType", "INTYPE");
		addColumn("hintID", "HINTID");
		addColumn("listNo", "LISTNO");
		addColumn("ztype", "ZTYPE");
		addColumn("isMustFill", "ISMUSTFILL");
		addColumn("isListShow", "ISLISTSHOW");
		addColumn("listWeigth", "LISTWEIGTH");
		addColumn("panid", "PANID");
		addColumn("isAllWidth", "ISALLWIDTH");
		addColumn("istName", "ISTNAME");
		addColumn("fileDotFileId", "FILEDOT_FILEID");
		addColumn("dataPoint", "DATAPOINT");
		addColumn("defaultValue", "DEFAULTVALUE");
		addColumn("isSubTable", "ISSUBTABLE");
		addColumn("isDataOnly", "ISDATAONLY");
		addColumn("checkType", "CHECKTYPE");
		addColumn("orderId", "ORDERID");
		addColumn("cellType", "CELLTYPE");
		addColumn("ostTableName", "OST_TABLENAME");
		addColumn("ostRow", "OST_ROW");
		addColumn("ostCol", "OST_COL");
		addColumn("hintList", "HINTLIST");
		addColumn("ostCellId", "OST_CELLID");
		addColumn("oldDName", "OLDDNAME");
		addColumn("ostCellName", "OST_CELLNAME");
		addColumn("ostFormula", "OST_FORMULA");
		addColumn("ostColor", "OST_COLOR");
		addColumn("ostSumType", "OST_SUMTYPE");
		addColumn("dataTableName", "DATA_TABLENAME");
		addColumn("dataFieldName", "DATA_FIELDNAME");
		addColumn("orderCon", "ORDER_CON");
		addColumn("conNum", "CONNUM");
		addColumn("nodeIndex", "NODE_INDEX");
		addColumn("typeId", "TYPE_ID");
		addColumn("userIndex", "USERINDEX");
		addColumn("orderIndex", "ORDER_INDEX");
		addColumn("parentDName", "PARENT_DNAME");
		addColumn("sysAuto", "SYSAUTO");
		addColumn("orderIndexF", "ORDER_INDEX_F");
		addColumn("orderIdF", "ORDERID_F");
		addColumn("orderConF", "ORDER_CON_F");
		addColumn("isPrintValue", "ISPRINTVALUE");
		addColumn("isShowValueOnLast", "ISSHOWVALUEONLAST");
		addColumn("isBankRoundType", "ISBANKROUNDTYPE");
		addColumn("floatBankRound", "FLOATBANKROUND");
		addColumn("intUseWBSPlace", "INTUSEWBSPLACE");
	}

}