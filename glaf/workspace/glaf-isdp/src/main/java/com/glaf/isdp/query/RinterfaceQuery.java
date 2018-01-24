package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class RinterfaceQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
	protected String indexId;
	protected String indexIdLike;
	protected List<String> indexIds;
	protected String frmtype;
	protected String frmtypeLike;
	protected List<String> frmtypes;
	protected String listId;
	protected String listIdLike;
	protected List<String> listIds;
	protected String issystem;
	protected String issystemLike;
	protected List<String> issystems;
	protected String fname;
	protected String fnameLike;
	protected List<String> fnames;
	protected String dname;
	protected String dnameLike;
	protected List<String> dnames;
	protected String dtype;
	protected String dtypeLike;
	protected List<String> dtypes;
	protected String showtype;
	protected String showtypeLike;
	protected List<String> showtypes;
	protected Integer strlen;
	protected Integer strlenGreaterThanOrEqual;
	protected Integer strlenLessThanOrEqual;
	protected List<Integer> strlens;
	protected String form;
	protected String formLike;
	protected List<String> forms;
	protected String intype;
	protected String intypeLike;
	protected List<String> intypes;
	protected String hintID;
	protected String hintIDLike;
	protected List<String> hintIDs;
	protected Integer listno;
	protected Integer listnoGreaterThanOrEqual;
	protected Integer listnoLessThanOrEqual;
	protected List<Integer> listnos;
	protected String ztype;
	protected String ztypeLike;
	protected List<String> ztypes;
	protected String ismustfill;
	protected String ismustfillLike;
	protected List<String> ismustfills;
	protected String isListShow;
	protected String isListShowLike;
	protected List<String> isListShows;
	protected Integer listweigth;
	protected Integer listweigthGreaterThanOrEqual;
	protected Integer listweigthLessThanOrEqual;
	protected List<Integer> listweigths;
	protected String isallwidth;
	protected String isallwidthLike;
	protected List<String> isallwidths;
	protected String istname;
	protected String istnameLike;
	protected List<String> istnames;
	protected Integer importType;
	protected Integer importTypeGreaterThanOrEqual;
	protected Integer importTypeLessThanOrEqual;
	protected List<Integer> importTypes;
	protected Integer datapoint;
	protected Integer datapointGreaterThanOrEqual;
	protected Integer datapointLessThanOrEqual;
	protected List<Integer> datapoints;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected Date updateDateGreaterThanOrEqual;
	protected Date updateDateLessThanOrEqual;
	protected String createBy;
	protected String createByLike;
	protected List<String> createBys;
	protected String updateBy;
	protected String updateByLike;
	protected List<String> updateBys;
	protected String isPrimaryKey;
	protected String isPrimaryKeyLike;
	protected List<String> isPrimaryKeys;
	protected String isGroupBy;
	protected String isGroupByLike;
	protected List<String> isGroupBys;

	public RinterfaceQuery() {

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

	public String getFrmtype() {
		return frmtype;
	}

	public String getFrmtypeLike() {
		if (frmtypeLike != null && frmtypeLike.trim().length() > 0) {
			if (!frmtypeLike.startsWith("%")) {
				frmtypeLike = "%" + frmtypeLike;
			}
			if (!frmtypeLike.endsWith("%")) {
				frmtypeLike = frmtypeLike + "%";
			}
		}
		return frmtypeLike;
	}

	public List<String> getFrmtypes() {
		return frmtypes;
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

	public String getIssystem() {
		return issystem;
	}

	public String getIssystemLike() {
		if (issystemLike != null && issystemLike.trim().length() > 0) {
			if (!issystemLike.startsWith("%")) {
				issystemLike = "%" + issystemLike;
			}
			if (!issystemLike.endsWith("%")) {
				issystemLike = issystemLike + "%";
			}
		}
		return issystemLike;
	}

	public List<String> getIssystems() {
		return issystems;
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

	public String getShowtype() {
		return showtype;
	}

	public String getShowtypeLike() {
		if (showtypeLike != null && showtypeLike.trim().length() > 0) {
			if (!showtypeLike.startsWith("%")) {
				showtypeLike = "%" + showtypeLike;
			}
			if (!showtypeLike.endsWith("%")) {
				showtypeLike = showtypeLike + "%";
			}
		}
		return showtypeLike;
	}

	public List<String> getShowtypes() {
		return showtypes;
	}

	public Integer getStrlen() {
		return strlen;
	}

	public Integer getStrlenGreaterThanOrEqual() {
		return strlenGreaterThanOrEqual;
	}

	public Integer getStrlenLessThanOrEqual() {
		return strlenLessThanOrEqual;
	}

	public List<Integer> getStrlens() {
		return strlens;
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

	public String getIntype() {
		return intype;
	}

	public String getIntypeLike() {
		if (intypeLike != null && intypeLike.trim().length() > 0) {
			if (!intypeLike.startsWith("%")) {
				intypeLike = "%" + intypeLike;
			}
			if (!intypeLike.endsWith("%")) {
				intypeLike = intypeLike + "%";
			}
		}
		return intypeLike;
	}

	public List<String> getIntypes() {
		return intypes;
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

	public Integer getListno() {
		return listno;
	}

	public Integer getListnoGreaterThanOrEqual() {
		return listnoGreaterThanOrEqual;
	}

	public Integer getListnoLessThanOrEqual() {
		return listnoLessThanOrEqual;
	}

	public List<Integer> getListnos() {
		return listnos;
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

	public String getIsmustfill() {
		return ismustfill;
	}

	public String getIsmustfillLike() {
		if (ismustfillLike != null && ismustfillLike.trim().length() > 0) {
			if (!ismustfillLike.startsWith("%")) {
				ismustfillLike = "%" + ismustfillLike;
			}
			if (!ismustfillLike.endsWith("%")) {
				ismustfillLike = ismustfillLike + "%";
			}
		}
		return ismustfillLike;
	}

	public List<String> getIsmustfills() {
		return ismustfills;
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

	public Integer getListweigth() {
		return listweigth;
	}

	public Integer getListweigthGreaterThanOrEqual() {
		return listweigthGreaterThanOrEqual;
	}

	public Integer getListweigthLessThanOrEqual() {
		return listweigthLessThanOrEqual;
	}

	public List<Integer> getListweigths() {
		return listweigths;
	}

	public String getIsallwidth() {
		return isallwidth;
	}

	public String getIsallwidthLike() {
		if (isallwidthLike != null && isallwidthLike.trim().length() > 0) {
			if (!isallwidthLike.startsWith("%")) {
				isallwidthLike = "%" + isallwidthLike;
			}
			if (!isallwidthLike.endsWith("%")) {
				isallwidthLike = isallwidthLike + "%";
			}
		}
		return isallwidthLike;
	}

	public List<String> getIsallwidths() {
		return isallwidths;
	}

	public String getIstname() {
		return istname;
	}

	public String getIstnameLike() {
		if (istnameLike != null && istnameLike.trim().length() > 0) {
			if (!istnameLike.startsWith("%")) {
				istnameLike = "%" + istnameLike;
			}
			if (!istnameLike.endsWith("%")) {
				istnameLike = istnameLike + "%";
			}
		}
		return istnameLike;
	}

	public List<String> getIstnames() {
		return istnames;
	}

	public Integer getImportType() {
		return importType;
	}

	public Integer getImportTypeGreaterThanOrEqual() {
		return importTypeGreaterThanOrEqual;
	}

	public Integer getImportTypeLessThanOrEqual() {
		return importTypeLessThanOrEqual;
	}

	public List<Integer> getImportTypes() {
		return importTypes;
	}

	public Integer getDatapoint() {
		return datapoint;
	}

	public Integer getDatapointGreaterThanOrEqual() {
		return datapointGreaterThanOrEqual;
	}

	public Integer getDatapointLessThanOrEqual() {
		return datapointLessThanOrEqual;
	}

	public List<Integer> getDatapoints() {
		return datapoints;
	}

	public Date getCreateDateGreaterThanOrEqual() {
		return createDateGreaterThanOrEqual;
	}

	public Date getCreateDateLessThanOrEqual() {
		return createDateLessThanOrEqual;
	}

	public Date getUpdateDateGreaterThanOrEqual() {
		return updateDateGreaterThanOrEqual;
	}

	public Date getUpdateDateLessThanOrEqual() {
		return updateDateLessThanOrEqual;
	}

	public String getCreateBy() {
		return createBy;
	}

	public String getCreateByLike() {
		if (createByLike != null && createByLike.trim().length() > 0) {
			if (!createByLike.startsWith("%")) {
				createByLike = "%" + createByLike;
			}
			if (!createByLike.endsWith("%")) {
				createByLike = createByLike + "%";
			}
		}
		return createByLike;
	}

	public List<String> getCreateBys() {
		return createBys;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public String getUpdateByLike() {
		if (updateByLike != null && updateByLike.trim().length() > 0) {
			if (!updateByLike.startsWith("%")) {
				updateByLike = "%" + updateByLike;
			}
			if (!updateByLike.endsWith("%")) {
				updateByLike = updateByLike + "%";
			}
		}
		return updateByLike;
	}

	public List<String> getUpdateBys() {
		return updateBys;
	}

	public String getIsPrimaryKey() {
		return isPrimaryKey;
	}

	public String getIsPrimaryKeyLike() {
		if (isPrimaryKeyLike != null && isPrimaryKeyLike.trim().length() > 0) {
			if (!isPrimaryKeyLike.startsWith("%")) {
				isPrimaryKeyLike = "%" + isPrimaryKeyLike;
			}
			if (!isPrimaryKeyLike.endsWith("%")) {
				isPrimaryKeyLike = isPrimaryKeyLike + "%";
			}
		}
		return isPrimaryKeyLike;
	}

	public List<String> getIsPrimaryKeys() {
		return isPrimaryKeys;
	}

	public String getIsGroupBy() {
		return isGroupBy;
	}

	public String getIsGroupByLike() {
		if (isGroupByLike != null && isGroupByLike.trim().length() > 0) {
			if (!isGroupByLike.startsWith("%")) {
				isGroupByLike = "%" + isGroupByLike;
			}
			if (!isGroupByLike.endsWith("%")) {
				isGroupByLike = isGroupByLike + "%";
			}
		}
		return isGroupByLike;
	}

	public List<String> getIsGroupBys() {
		return isGroupBys;
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

	public void setFrmtype(String frmtype) {
		this.frmtype = frmtype;
	}

	public void setFrmtypeLike(String frmtypeLike) {
		this.frmtypeLike = frmtypeLike;
	}

	public void setFrmtypes(List<String> frmtypes) {
		this.frmtypes = frmtypes;
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

	public void setIssystem(String issystem) {
		this.issystem = issystem;
	}

	public void setIssystemLike(String issystemLike) {
		this.issystemLike = issystemLike;
	}

	public void setIssystems(List<String> issystems) {
		this.issystems = issystems;
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

	public void setShowtype(String showtype) {
		this.showtype = showtype;
	}

	public void setShowtypeLike(String showtypeLike) {
		this.showtypeLike = showtypeLike;
	}

	public void setShowtypes(List<String> showtypes) {
		this.showtypes = showtypes;
	}

	public void setStrlen(Integer strlen) {
		this.strlen = strlen;
	}

	public void setStrlenGreaterThanOrEqual(Integer strlenGreaterThanOrEqual) {
		this.strlenGreaterThanOrEqual = strlenGreaterThanOrEqual;
	}

	public void setStrlenLessThanOrEqual(Integer strlenLessThanOrEqual) {
		this.strlenLessThanOrEqual = strlenLessThanOrEqual;
	}

	public void setStrlens(List<Integer> strlens) {
		this.strlens = strlens;
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

	public void setIntype(String intype) {
		this.intype = intype;
	}

	public void setIntypeLike(String intypeLike) {
		this.intypeLike = intypeLike;
	}

	public void setIntypes(List<String> intypes) {
		this.intypes = intypes;
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

	public void setListno(Integer listno) {
		this.listno = listno;
	}

	public void setListnoGreaterThanOrEqual(Integer listnoGreaterThanOrEqual) {
		this.listnoGreaterThanOrEqual = listnoGreaterThanOrEqual;
	}

	public void setListnoLessThanOrEqual(Integer listnoLessThanOrEqual) {
		this.listnoLessThanOrEqual = listnoLessThanOrEqual;
	}

	public void setListnos(List<Integer> listnos) {
		this.listnos = listnos;
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

	public void setIsmustfill(String ismustfill) {
		this.ismustfill = ismustfill;
	}

	public void setIsmustfillLike(String ismustfillLike) {
		this.ismustfillLike = ismustfillLike;
	}

	public void setIsmustfills(List<String> ismustfills) {
		this.ismustfills = ismustfills;
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

	public void setListweigth(Integer listweigth) {
		this.listweigth = listweigth;
	}

	public void setListweigthGreaterThanOrEqual(Integer listweigthGreaterThanOrEqual) {
		this.listweigthGreaterThanOrEqual = listweigthGreaterThanOrEqual;
	}

	public void setListweigthLessThanOrEqual(Integer listweigthLessThanOrEqual) {
		this.listweigthLessThanOrEqual = listweigthLessThanOrEqual;
	}

	public void setListweigths(List<Integer> listweigths) {
		this.listweigths = listweigths;
	}

	public void setIsallwidth(String isallwidth) {
		this.isallwidth = isallwidth;
	}

	public void setIsallwidthLike(String isallwidthLike) {
		this.isallwidthLike = isallwidthLike;
	}

	public void setIsallwidths(List<String> isallwidths) {
		this.isallwidths = isallwidths;
	}

	public void setIstname(String istname) {
		this.istname = istname;
	}

	public void setIstnameLike(String istnameLike) {
		this.istnameLike = istnameLike;
	}

	public void setIstnames(List<String> istnames) {
		this.istnames = istnames;
	}

	public void setImportType(Integer importType) {
		this.importType = importType;
	}

	public void setImportTypeGreaterThanOrEqual(Integer importTypeGreaterThanOrEqual) {
		this.importTypeGreaterThanOrEqual = importTypeGreaterThanOrEqual;
	}

	public void setImportTypeLessThanOrEqual(Integer importTypeLessThanOrEqual) {
		this.importTypeLessThanOrEqual = importTypeLessThanOrEqual;
	}

	public void setImportTypes(List<Integer> importTypes) {
		this.importTypes = importTypes;
	}

	public void setDatapoint(Integer datapoint) {
		this.datapoint = datapoint;
	}

	public void setDatapointGreaterThanOrEqual(Integer datapointGreaterThanOrEqual) {
		this.datapointGreaterThanOrEqual = datapointGreaterThanOrEqual;
	}

	public void setDatapointLessThanOrEqual(Integer datapointLessThanOrEqual) {
		this.datapointLessThanOrEqual = datapointLessThanOrEqual;
	}

	public void setDatapoints(List<Integer> datapoints) {
		this.datapoints = datapoints;
	}

	public void setCreateDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual) {
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
	}

	public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
	}

	public void setUpdateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual) {
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
	}

	public void setUpdateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateByLike(String createByLike) {
		this.createByLike = createByLike;
	}

	public void setCreateBys(List<String> createBys) {
		this.createBys = createBys;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateByLike(String updateByLike) {
		this.updateByLike = updateByLike;
	}

	public void setUpdateBys(List<String> updateBys) {
		this.updateBys = updateBys;
	}

	public void setIsPrimaryKey(String isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public void setIsPrimaryKeyLike(String isPrimaryKeyLike) {
		this.isPrimaryKeyLike = isPrimaryKeyLike;
	}

	public void setIsPrimaryKeys(List<String> isPrimaryKeys) {
		this.isPrimaryKeys = isPrimaryKeys;
	}

	public void setIsGroupBy(String isGroupBy) {
		this.isGroupBy = isGroupBy;
	}

	public void setIsGroupByLike(String isGroupByLike) {
		this.isGroupByLike = isGroupByLike;
	}

	public void setIsGroupBys(List<String> isGroupBys) {
		this.isGroupBys = isGroupBys;
	}

	public RinterfaceQuery indexId(String indexId) {
		if (indexId == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexId = indexId;
		return this;
	}

	public RinterfaceQuery indexIdLike(String indexIdLike) {
		if (indexIdLike == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexIdLike = indexIdLike;
		return this;
	}

	public RinterfaceQuery indexIds(List<String> indexIds) {
		if (indexIds == null) {
			throw new RuntimeException("indexIds is empty ");
		}
		this.indexIds = indexIds;
		return this;
	}

	public RinterfaceQuery frmtype(String frmtype) {
		if (frmtype == null) {
			throw new RuntimeException("frmtype is null");
		}
		this.frmtype = frmtype;
		return this;
	}

	public RinterfaceQuery frmtypeLike(String frmtypeLike) {
		if (frmtypeLike == null) {
			throw new RuntimeException("frmtype is null");
		}
		this.frmtypeLike = frmtypeLike;
		return this;
	}

	public RinterfaceQuery frmtypes(List<String> frmtypes) {
		if (frmtypes == null) {
			throw new RuntimeException("frmtypes is empty ");
		}
		this.frmtypes = frmtypes;
		return this;
	}

	public RinterfaceQuery listId(String listId) {
		if (listId == null) {
			throw new RuntimeException("listId is null");
		}
		this.listId = listId;
		return this;
	}

	public RinterfaceQuery listIdLike(String listIdLike) {
		if (listIdLike == null) {
			throw new RuntimeException("listId is null");
		}
		this.listIdLike = listIdLike;
		return this;
	}

	public RinterfaceQuery listIds(List<String> listIds) {
		if (listIds == null) {
			throw new RuntimeException("listIds is empty ");
		}
		this.listIds = listIds;
		return this;
	}

	public RinterfaceQuery issystem(String issystem) {
		if (issystem == null) {
			throw new RuntimeException("issystem is null");
		}
		this.issystem = issystem;
		return this;
	}

	public RinterfaceQuery issystemLike(String issystemLike) {
		if (issystemLike == null) {
			throw new RuntimeException("issystem is null");
		}
		this.issystemLike = issystemLike;
		return this;
	}

	public RinterfaceQuery issystems(List<String> issystems) {
		if (issystems == null) {
			throw new RuntimeException("issystems is empty ");
		}
		this.issystems = issystems;
		return this;
	}

	public RinterfaceQuery fname(String fname) {
		if (fname == null) {
			throw new RuntimeException("fname is null");
		}
		this.fname = fname;
		return this;
	}

	public RinterfaceQuery fnameLike(String fnameLike) {
		if (fnameLike == null) {
			throw new RuntimeException("fname is null");
		}
		this.fnameLike = fnameLike;
		return this;
	}

	public RinterfaceQuery fnames(List<String> fnames) {
		if (fnames == null) {
			throw new RuntimeException("fnames is empty ");
		}
		this.fnames = fnames;
		return this;
	}

	public RinterfaceQuery dname(String dname) {
		if (dname == null) {
			throw new RuntimeException("dname is null");
		}
		this.dname = dname;
		return this;
	}

	public RinterfaceQuery dnameLike(String dnameLike) {
		if (dnameLike == null) {
			throw new RuntimeException("dname is null");
		}
		this.dnameLike = dnameLike;
		return this;
	}

	public RinterfaceQuery dnames(List<String> dnames) {
		if (dnames == null) {
			throw new RuntimeException("dnames is empty ");
		}
		this.dnames = dnames;
		return this;
	}

	public RinterfaceQuery dtype(String dtype) {
		if (dtype == null) {
			throw new RuntimeException("dtype is null");
		}
		this.dtype = dtype;
		return this;
	}

	public RinterfaceQuery dtypeLike(String dtypeLike) {
		if (dtypeLike == null) {
			throw new RuntimeException("dtype is null");
		}
		this.dtypeLike = dtypeLike;
		return this;
	}

	public RinterfaceQuery dtypes(List<String> dtypes) {
		if (dtypes == null) {
			throw new RuntimeException("dtypes is empty ");
		}
		this.dtypes = dtypes;
		return this;
	}

	public RinterfaceQuery showtype(String showtype) {
		if (showtype == null) {
			throw new RuntimeException("showtype is null");
		}
		this.showtype = showtype;
		return this;
	}

	public RinterfaceQuery showtypeLike(String showtypeLike) {
		if (showtypeLike == null) {
			throw new RuntimeException("showtype is null");
		}
		this.showtypeLike = showtypeLike;
		return this;
	}

	public RinterfaceQuery showtypes(List<String> showtypes) {
		if (showtypes == null) {
			throw new RuntimeException("showtypes is empty ");
		}
		this.showtypes = showtypes;
		return this;
	}

	public RinterfaceQuery strlen(Integer strlen) {
		if (strlen == null) {
			throw new RuntimeException("strlen is null");
		}
		this.strlen = strlen;
		return this;
	}

	public RinterfaceQuery strlenGreaterThanOrEqual(Integer strlenGreaterThanOrEqual) {
		if (strlenGreaterThanOrEqual == null) {
			throw new RuntimeException("strlen is null");
		}
		this.strlenGreaterThanOrEqual = strlenGreaterThanOrEqual;
		return this;
	}

	public RinterfaceQuery strlenLessThanOrEqual(Integer strlenLessThanOrEqual) {
		if (strlenLessThanOrEqual == null) {
			throw new RuntimeException("strlen is null");
		}
		this.strlenLessThanOrEqual = strlenLessThanOrEqual;
		return this;
	}

	public RinterfaceQuery strlens(List<Integer> strlens) {
		if (strlens == null) {
			throw new RuntimeException("strlens is empty ");
		}
		this.strlens = strlens;
		return this;
	}

	public RinterfaceQuery form(String form) {
		if (form == null) {
			throw new RuntimeException("form is null");
		}
		this.form = form;
		return this;
	}

	public RinterfaceQuery formLike(String formLike) {
		if (formLike == null) {
			throw new RuntimeException("form is null");
		}
		this.formLike = formLike;
		return this;
	}

	public RinterfaceQuery forms(List<String> forms) {
		if (forms == null) {
			throw new RuntimeException("forms is empty ");
		}
		this.forms = forms;
		return this;
	}

	public RinterfaceQuery intype(String intype) {
		if (intype == null) {
			throw new RuntimeException("intype is null");
		}
		this.intype = intype;
		return this;
	}

	public RinterfaceQuery intypeLike(String intypeLike) {
		if (intypeLike == null) {
			throw new RuntimeException("intype is null");
		}
		this.intypeLike = intypeLike;
		return this;
	}

	public RinterfaceQuery intypes(List<String> intypes) {
		if (intypes == null) {
			throw new RuntimeException("intypes is empty ");
		}
		this.intypes = intypes;
		return this;
	}

	public RinterfaceQuery hintID(String hintID) {
		if (hintID == null) {
			throw new RuntimeException("hintID is null");
		}
		this.hintID = hintID;
		return this;
	}

	public RinterfaceQuery hintIDLike(String hintIDLike) {
		if (hintIDLike == null) {
			throw new RuntimeException("hintID is null");
		}
		this.hintIDLike = hintIDLike;
		return this;
	}

	public RinterfaceQuery hintIDs(List<String> hintIDs) {
		if (hintIDs == null) {
			throw new RuntimeException("hintIDs is empty ");
		}
		this.hintIDs = hintIDs;
		return this;
	}

	public RinterfaceQuery listno(Integer listno) {
		if (listno == null) {
			throw new RuntimeException("listno is null");
		}
		this.listno = listno;
		return this;
	}

	public RinterfaceQuery listnoGreaterThanOrEqual(Integer listnoGreaterThanOrEqual) {
		if (listnoGreaterThanOrEqual == null) {
			throw new RuntimeException("listno is null");
		}
		this.listnoGreaterThanOrEqual = listnoGreaterThanOrEqual;
		return this;
	}

	public RinterfaceQuery listnoLessThanOrEqual(Integer listnoLessThanOrEqual) {
		if (listnoLessThanOrEqual == null) {
			throw new RuntimeException("listno is null");
		}
		this.listnoLessThanOrEqual = listnoLessThanOrEqual;
		return this;
	}

	public RinterfaceQuery listnos(List<Integer> listnos) {
		if (listnos == null) {
			throw new RuntimeException("listnos is empty ");
		}
		this.listnos = listnos;
		return this;
	}

	public RinterfaceQuery ztype(String ztype) {
		if (ztype == null) {
			throw new RuntimeException("ztype is null");
		}
		this.ztype = ztype;
		return this;
	}

	public RinterfaceQuery ztypeLike(String ztypeLike) {
		if (ztypeLike == null) {
			throw new RuntimeException("ztype is null");
		}
		this.ztypeLike = ztypeLike;
		return this;
	}

	public RinterfaceQuery ztypes(List<String> ztypes) {
		if (ztypes == null) {
			throw new RuntimeException("ztypes is empty ");
		}
		this.ztypes = ztypes;
		return this;
	}

	public RinterfaceQuery ismustfill(String ismustfill) {
		if (ismustfill == null) {
			throw new RuntimeException("ismustfill is null");
		}
		this.ismustfill = ismustfill;
		return this;
	}

	public RinterfaceQuery ismustfillLike(String ismustfillLike) {
		if (ismustfillLike == null) {
			throw new RuntimeException("ismustfill is null");
		}
		this.ismustfillLike = ismustfillLike;
		return this;
	}

	public RinterfaceQuery ismustfills(List<String> ismustfills) {
		if (ismustfills == null) {
			throw new RuntimeException("ismustfills is empty ");
		}
		this.ismustfills = ismustfills;
		return this;
	}

	public RinterfaceQuery isListShow(String isListShow) {
		if (isListShow == null) {
			throw new RuntimeException("isListShow is null");
		}
		this.isListShow = isListShow;
		return this;
	}

	public RinterfaceQuery isListShowLike(String isListShowLike) {
		if (isListShowLike == null) {
			throw new RuntimeException("isListShow is null");
		}
		this.isListShowLike = isListShowLike;
		return this;
	}

	public RinterfaceQuery isListShows(List<String> isListShows) {
		if (isListShows == null) {
			throw new RuntimeException("isListShows is empty ");
		}
		this.isListShows = isListShows;
		return this;
	}

	public RinterfaceQuery listweigth(Integer listweigth) {
		if (listweigth == null) {
			throw new RuntimeException("listweigth is null");
		}
		this.listweigth = listweigth;
		return this;
	}

	public RinterfaceQuery listweigthGreaterThanOrEqual(Integer listweigthGreaterThanOrEqual) {
		if (listweigthGreaterThanOrEqual == null) {
			throw new RuntimeException("listweigth is null");
		}
		this.listweigthGreaterThanOrEqual = listweigthGreaterThanOrEqual;
		return this;
	}

	public RinterfaceQuery listweigthLessThanOrEqual(Integer listweigthLessThanOrEqual) {
		if (listweigthLessThanOrEqual == null) {
			throw new RuntimeException("listweigth is null");
		}
		this.listweigthLessThanOrEqual = listweigthLessThanOrEqual;
		return this;
	}

	public RinterfaceQuery listweigths(List<Integer> listweigths) {
		if (listweigths == null) {
			throw new RuntimeException("listweigths is empty ");
		}
		this.listweigths = listweigths;
		return this;
	}

	public RinterfaceQuery isallwidth(String isallwidth) {
		if (isallwidth == null) {
			throw new RuntimeException("isallwidth is null");
		}
		this.isallwidth = isallwidth;
		return this;
	}

	public RinterfaceQuery isallwidthLike(String isallwidthLike) {
		if (isallwidthLike == null) {
			throw new RuntimeException("isallwidth is null");
		}
		this.isallwidthLike = isallwidthLike;
		return this;
	}

	public RinterfaceQuery isallwidths(List<String> isallwidths) {
		if (isallwidths == null) {
			throw new RuntimeException("isallwidths is empty ");
		}
		this.isallwidths = isallwidths;
		return this;
	}

	public RinterfaceQuery istname(String istname) {
		if (istname == null) {
			throw new RuntimeException("istname is null");
		}
		this.istname = istname;
		return this;
	}

	public RinterfaceQuery istnameLike(String istnameLike) {
		if (istnameLike == null) {
			throw new RuntimeException("istname is null");
		}
		this.istnameLike = istnameLike;
		return this;
	}

	public RinterfaceQuery istnames(List<String> istnames) {
		if (istnames == null) {
			throw new RuntimeException("istnames is empty ");
		}
		this.istnames = istnames;
		return this;
	}

	public RinterfaceQuery importType(Integer importType) {
		if (importType == null) {
			throw new RuntimeException("importType is null");
		}
		this.importType = importType;
		return this;
	}

	public RinterfaceQuery importTypeGreaterThanOrEqual(Integer importTypeGreaterThanOrEqual) {
		if (importTypeGreaterThanOrEqual == null) {
			throw new RuntimeException("importType is null");
		}
		this.importTypeGreaterThanOrEqual = importTypeGreaterThanOrEqual;
		return this;
	}

	public RinterfaceQuery importTypeLessThanOrEqual(Integer importTypeLessThanOrEqual) {
		if (importTypeLessThanOrEqual == null) {
			throw new RuntimeException("importType is null");
		}
		this.importTypeLessThanOrEqual = importTypeLessThanOrEqual;
		return this;
	}

	public RinterfaceQuery importTypes(List<Integer> importTypes) {
		if (importTypes == null) {
			throw new RuntimeException("importTypes is empty ");
		}
		this.importTypes = importTypes;
		return this;
	}

	public RinterfaceQuery datapoint(Integer datapoint) {
		if (datapoint == null) {
			throw new RuntimeException("datapoint is null");
		}
		this.datapoint = datapoint;
		return this;
	}

	public RinterfaceQuery datapointGreaterThanOrEqual(Integer datapointGreaterThanOrEqual) {
		if (datapointGreaterThanOrEqual == null) {
			throw new RuntimeException("datapoint is null");
		}
		this.datapointGreaterThanOrEqual = datapointGreaterThanOrEqual;
		return this;
	}

	public RinterfaceQuery datapointLessThanOrEqual(Integer datapointLessThanOrEqual) {
		if (datapointLessThanOrEqual == null) {
			throw new RuntimeException("datapoint is null");
		}
		this.datapointLessThanOrEqual = datapointLessThanOrEqual;
		return this;
	}

	public RinterfaceQuery datapoints(List<Integer> datapoints) {
		if (datapoints == null) {
			throw new RuntimeException("datapoints is empty ");
		}
		this.datapoints = datapoints;
		return this;
	}

	public RinterfaceQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public RinterfaceQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public RinterfaceQuery updateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public RinterfaceQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public RinterfaceQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public RinterfaceQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public RinterfaceQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public RinterfaceQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public RinterfaceQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public RinterfaceQuery updateBys(List<String> updateBys) {
		if (updateBys == null) {
			throw new RuntimeException("updateBys is empty ");
		}
		this.updateBys = updateBys;
		return this;
	}

	public RinterfaceQuery isPrimaryKey(String isPrimaryKey) {
		if (isPrimaryKey == null) {
			throw new RuntimeException("isPrimaryKey is null");
		}
		this.isPrimaryKey = isPrimaryKey;
		return this;
	}

	public RinterfaceQuery isPrimaryKeyLike(String isPrimaryKeyLike) {
		if (isPrimaryKeyLike == null) {
			throw new RuntimeException("isPrimaryKey is null");
		}
		this.isPrimaryKeyLike = isPrimaryKeyLike;
		return this;
	}

	public RinterfaceQuery isPrimaryKeys(List<String> isPrimaryKeys) {
		if (isPrimaryKeys == null) {
			throw new RuntimeException("isPrimaryKeys is empty ");
		}
		this.isPrimaryKeys = isPrimaryKeys;
		return this;
	}

	public RinterfaceQuery isGroupBy(String isGroupBy) {
		if (isGroupBy == null) {
			throw new RuntimeException("isGroupBy is null");
		}
		this.isGroupBy = isGroupBy;
		return this;
	}

	public RinterfaceQuery isGroupByLike(String isGroupByLike) {
		if (isGroupByLike == null) {
			throw new RuntimeException("isGroupBy is null");
		}
		this.isGroupByLike = isGroupByLike;
		return this;
	}

	public RinterfaceQuery isGroupBys(List<String> isGroupBys) {
		if (isGroupBys == null) {
			throw new RuntimeException("isGroupBys is empty ");
		}
		this.isGroupBys = isGroupBys;
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

			if ("frmtype".equals(sortColumn)) {
				orderBy = "E.FRMTYPE" + a_x;
			}

			if ("listId".equals(sortColumn)) {
				orderBy = "E.LISTID" + a_x;
			}

			if ("issystem".equals(sortColumn)) {
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

			if ("showtype".equals(sortColumn)) {
				orderBy = "E.SHOWTYPE" + a_x;
			}

			if ("strlen".equals(sortColumn)) {
				orderBy = "E.STRLEN" + a_x;
			}

			if ("form".equals(sortColumn)) {
				orderBy = "E.FORM" + a_x;
			}

			if ("intype".equals(sortColumn)) {
				orderBy = "E.INTYPE" + a_x;
			}

			if ("hintID".equals(sortColumn)) {
				orderBy = "E.HINTID" + a_x;
			}

			if ("listno".equals(sortColumn)) {
				orderBy = "E.LISTNO" + a_x;
			}

			if ("ztype".equals(sortColumn)) {
				orderBy = "E.ZTYPE" + a_x;
			}

			if ("ismustfill".equals(sortColumn)) {
				orderBy = "E.ISMUSTFILL" + a_x;
			}

			if ("isListShow".equals(sortColumn)) {
				orderBy = "E.ISLISTSHOW" + a_x;
			}

			if ("listweigth".equals(sortColumn)) {
				orderBy = "E.LISTWEIGTH" + a_x;
			}

			if ("isallwidth".equals(sortColumn)) {
				orderBy = "E.ISALLWIDTH" + a_x;
			}

			if ("istname".equals(sortColumn)) {
				orderBy = "E.ISTNAME" + a_x;
			}

			if ("importType".equals(sortColumn)) {
				orderBy = "E.IMPORT_TYPE" + a_x;
			}

			if ("datapoint".equals(sortColumn)) {
				orderBy = "E.DATAPOINT" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.CREATEDATE" + a_x;
			}

			if ("updateDate".equals(sortColumn)) {
				orderBy = "E.UPDATEDATE" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY" + a_x;
			}

			if ("updateBy".equals(sortColumn)) {
				orderBy = "E.UPDATEBY" + a_x;
			}

			if ("isPrimaryKey".equals(sortColumn)) {
				orderBy = "E.ISPRIMARYKEY" + a_x;
			}

			if ("isGroupBy".equals(sortColumn)) {
				orderBy = "E.ISGROUPBY" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("indexId", "INDEX_ID");
		addColumn("frmtype", "FRMTYPE");
		addColumn("listId", "LISTID");
		addColumn("issystem", "ISSYSTEM");
		addColumn("fname", "FNAME");
		addColumn("dname", "DNAME");
		addColumn("dtype", "DTYPE");
		addColumn("showtype", "SHOWTYPE");
		addColumn("strlen", "STRLEN");
		addColumn("form", "FORM");
		addColumn("intype", "INTYPE");
		addColumn("hintID", "HINTID");
		addColumn("listno", "LISTNO");
		addColumn("ztype", "ZTYPE");
		addColumn("ismustfill", "ISMUSTFILL");
		addColumn("isListShow", "ISLISTSHOW");
		addColumn("listweigth", "LISTWEIGTH");
		addColumn("isallwidth", "ISALLWIDTH");
		addColumn("istname", "ISTNAME");
		addColumn("importType", "IMPORT_TYPE");
		addColumn("datapoint", "DATAPOINT");
		addColumn("createDate", "CREATEDATE");
		addColumn("updateDate", "UPDATEDATE");
		addColumn("createBy", "CREATEBY");
		addColumn("updateBy", "UPDATEBY");
		addColumn("isPrimaryKey", "ISPRIMARYKEY");
		addColumn("isGroupBy", "ISGROUPBY");
	}

}