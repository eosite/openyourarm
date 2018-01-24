package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ActReBusinessQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
	protected String bustbName;
	protected String bustbNameLike;
	protected List<String> bustbNames;
	protected String bustbId;
	protected String bustbIdLike;
	protected List<String> bustbIds;
	protected String busValue;
	protected String busValueLike;
	protected List<String> busValues;
	protected String processId;
	protected String processIdLike;
	protected List<String> processIds;
	protected String processName;
	protected String processNameLike;
	protected List<String> processNames;
	protected String pageId;
	protected String pageIdLike;
	protected List<String> pageIds;
	protected String key;
	protected String keyLike;
	protected List<String> keys;
	protected String url;
	protected String urlLike;
	protected List<String> urls;
	protected String createBy;
	protected String createByLike;
	protected List<String> createBys;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected String updateBy;
	protected String updateByLike;
	protected List<String> updateBys;
	protected Date updateDateGreaterThanOrEqual;
	protected Date updateDateLessThanOrEqual;
	protected String defId;
	protected String defIdLike;

	public ActReBusinessQuery() {

	}

	public String getDefId() {
		return defId;
	}

	public String getDefIdLike() {
		if (defIdLike != null && defIdLike.trim().length() > 0) {
			if (!defIdLike.startsWith("%")) {
				defIdLike = "%" + defIdLike;
			}
			if (!defIdLike.endsWith("%")) {
				defIdLike = defIdLike + "%";
			}
		}
		return defIdLike;
	}

	public void setDefId(String defId) {
		this.defId = defId;
	}

	public void setDefIdLike(String defIdLike) {
		this.defIdLike = defIdLike;
	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getBustbName() {
		return bustbName;
	}

	public String getBustbNameLike() {
		if (bustbNameLike != null && bustbNameLike.trim().length() > 0) {
			if (!bustbNameLike.startsWith("%")) {
				bustbNameLike = "%" + bustbNameLike;
			}
			if (!bustbNameLike.endsWith("%")) {
				bustbNameLike = bustbNameLike + "%";
			}
		}
		return bustbNameLike;
	}

	public List<String> getBustbNames() {
		return bustbNames;
	}

	public String getBustbId() {
		return bustbId;
	}

	public String getBustbIdLike() {
		if (bustbIdLike != null && bustbIdLike.trim().length() > 0) {
			if (!bustbIdLike.startsWith("%")) {
				bustbIdLike = "%" + bustbIdLike;
			}
			if (!bustbIdLike.endsWith("%")) {
				bustbIdLike = bustbIdLike + "%";
			}
		}
		return bustbIdLike;
	}

	public List<String> getBustbIds() {
		return bustbIds;
	}

	public String getBusValue() {
		return busValue;
	}

	public String getBusValueLike() {
		if (busValueLike != null && busValueLike.trim().length() > 0) {
			if (!busValueLike.startsWith("%")) {
				busValueLike = "%" + busValueLike;
			}
			if (!busValueLike.endsWith("%")) {
				busValueLike = busValueLike + "%";
			}
		}
		return busValueLike;
	}

	public List<String> getBusValues() {
		return busValues;
	}

	public String getProcessId() {
		return processId;
	}

	public String getProcessIdLike() {
		if (processIdLike != null && processIdLike.trim().length() > 0) {
			if (!processIdLike.startsWith("%")) {
				processIdLike = "%" + processIdLike;
			}
			if (!processIdLike.endsWith("%")) {
				processIdLike = processIdLike + "%";
			}
		}
		return processIdLike;
	}

	public List<String> getProcessIds() {
		return processIds;
	}

	public String getProcessName() {
		return processName;
	}

	public String getProcessNameLike() {
		if (processNameLike != null && processNameLike.trim().length() > 0) {
			if (!processNameLike.startsWith("%")) {
				processNameLike = "%" + processNameLike;
			}
			if (!processNameLike.endsWith("%")) {
				processNameLike = processNameLike + "%";
			}
		}
		return processNameLike;
	}

	public List<String> getProcessNames() {
		return processNames;
	}

	public String getPageId() {
		return pageId;
	}

	public String getPageIdLike() {
		if (pageIdLike != null && pageIdLike.trim().length() > 0) {
			if (!pageIdLike.startsWith("%")) {
				pageIdLike = "%" + pageIdLike;
			}
			if (!pageIdLike.endsWith("%")) {
				pageIdLike = pageIdLike + "%";
			}
		}
		return pageIdLike;
	}

	public List<String> getPageIds() {
		return pageIds;
	}

	public String getKey() {
		return key;
	}

	public String getKeyLike() {
		if (keyLike != null && keyLike.trim().length() > 0) {
			if (!keyLike.startsWith("%")) {
				keyLike = "%" + keyLike;
			}
			if (!keyLike.endsWith("%")) {
				keyLike = keyLike + "%";
			}
		}
		return keyLike;
	}

	public List<String> getKeys() {
		return keys;
	}

	public String getUrl() {
		return url;
	}

	public String getUrlLike() {
		if (urlLike != null && urlLike.trim().length() > 0) {
			if (!urlLike.startsWith("%")) {
				urlLike = "%" + urlLike;
			}
			if (!urlLike.endsWith("%")) {
				urlLike = urlLike + "%";
			}
		}
		return urlLike;
	}

	public List<String> getUrls() {
		return urls;
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

	public Date getCreateDateGreaterThanOrEqual() {
		return createDateGreaterThanOrEqual;
	}

	public Date getCreateDateLessThanOrEqual() {
		return createDateLessThanOrEqual;
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

	public Date getUpdateDateGreaterThanOrEqual() {
		return updateDateGreaterThanOrEqual;
	}

	public Date getUpdateDateLessThanOrEqual() {
		return updateDateLessThanOrEqual;
	}

	public void setBustbName(String bustbName) {
		this.bustbName = bustbName;
	}

	public void setBustbNameLike(String bustbNameLike) {
		this.bustbNameLike = bustbNameLike;
	}

	public void setBustbNames(List<String> bustbNames) {
		this.bustbNames = bustbNames;
	}

	public void setBustbId(String bustbId) {
		this.bustbId = bustbId;
	}

	public void setBustbIdLike(String bustbIdLike) {
		this.bustbIdLike = bustbIdLike;
	}

	public void setBustbIds(List<String> bustbIds) {
		this.bustbIds = bustbIds;
	}

	public void setBusValue(String busValue) {
		this.busValue = busValue;
	}

	public void setBusValueLike(String busValueLike) {
		this.busValueLike = busValueLike;
	}

	public void setBusValues(List<String> busValues) {
		this.busValues = busValues;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public void setProcessIdLike(String processIdLike) {
		this.processIdLike = processIdLike;
	}

	public void setProcessIds(List<String> processIds) {
		this.processIds = processIds;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setProcessNameLike(String processNameLike) {
		this.processNameLike = processNameLike;
	}

	public void setProcessNames(List<String> processNames) {
		this.processNames = processNames;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public void setPageIdLike(String pageIdLike) {
		this.pageIdLike = pageIdLike;
	}

	public void setPageIds(List<String> pageIds) {
		this.pageIds = pageIds;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setKeyLike(String keyLike) {
		this.keyLike = keyLike;
	}

	public void setKeys(List<String> keys) {
		this.keys = keys;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUrlLike(String urlLike) {
		this.urlLike = urlLike;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
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

	public void setCreateDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual) {
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
	}

	public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
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

	public void setUpdateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual) {
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
	}

	public void setUpdateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
	}

	public ActReBusinessQuery bustbName(String bustbName) {
		if (bustbName == null) {
			throw new RuntimeException("bustbName is null");
		}
		this.bustbName = bustbName;
		return this;
	}

	public ActReBusinessQuery bustbNameLike(String bustbNameLike) {
		if (bustbNameLike == null) {
			throw new RuntimeException("bustbName is null");
		}
		this.bustbNameLike = bustbNameLike;
		return this;
	}

	public ActReBusinessQuery bustbNames(List<String> bustbNames) {
		if (bustbNames == null) {
			throw new RuntimeException("bustbNames is empty ");
		}
		this.bustbNames = bustbNames;
		return this;
	}

	public ActReBusinessQuery bustbId(String bustbId) {
		if (bustbId == null) {
			throw new RuntimeException("bustbId is null");
		}
		this.bustbId = bustbId;
		return this;
	}

	public ActReBusinessQuery bustbIdLike(String bustbIdLike) {
		if (bustbIdLike == null) {
			throw new RuntimeException("bustbId is null");
		}
		this.bustbIdLike = bustbIdLike;
		return this;
	}

	public ActReBusinessQuery bustbIds(List<String> bustbIds) {
		if (bustbIds == null) {
			throw new RuntimeException("bustbIds is empty ");
		}
		this.bustbIds = bustbIds;
		return this;
	}

	public ActReBusinessQuery busValue(String busValue) {
		if (busValue == null) {
			throw new RuntimeException("busValue is null");
		}
		this.busValue = busValue;
		return this;
	}

	public ActReBusinessQuery busValueLike(String busValueLike) {
		if (busValueLike == null) {
			throw new RuntimeException("busValue is null");
		}
		this.busValueLike = busValueLike;
		return this;
	}

	public ActReBusinessQuery busValues(List<String> busValues) {
		if (busValues == null) {
			throw new RuntimeException("busValues is empty ");
		}
		this.busValues = busValues;
		return this;
	}

	public ActReBusinessQuery processId(String processId) {
		if (processId == null) {
			throw new RuntimeException("processId is null");
		}
		this.processId = processId;
		return this;
	}

	public ActReBusinessQuery processIdLike(String processIdLike) {
		if (processIdLike == null) {
			throw new RuntimeException("processId is null");
		}
		this.processIdLike = processIdLike;
		return this;
	}

	public ActReBusinessQuery processIds(List<String> processIds) {
		if (processIds == null) {
			throw new RuntimeException("processIds is empty ");
		}
		this.processIds = processIds;
		return this;
	}

	public ActReBusinessQuery processName(String processName) {
		if (processName == null) {
			throw new RuntimeException("processName is null");
		}
		this.processName = processName;
		return this;
	}

	public ActReBusinessQuery processNameLike(String processNameLike) {
		if (processNameLike == null) {
			throw new RuntimeException("processName is null");
		}
		this.processNameLike = processNameLike;
		return this;
	}

	public ActReBusinessQuery processNames(List<String> processNames) {
		if (processNames == null) {
			throw new RuntimeException("processNames is empty ");
		}
		this.processNames = processNames;
		return this;
	}

	public ActReBusinessQuery pageId(String pageId) {
		if (pageId == null) {
			throw new RuntimeException("pageId is null");
		}
		this.pageId = pageId;
		return this;
	}

	public ActReBusinessQuery pageIdLike(String pageIdLike) {
		if (pageIdLike == null) {
			throw new RuntimeException("pageId is null");
		}
		this.pageIdLike = pageIdLike;
		return this;
	}

	public ActReBusinessQuery pageIds(List<String> pageIds) {
		if (pageIds == null) {
			throw new RuntimeException("pageIds is empty ");
		}
		this.pageIds = pageIds;
		return this;
	}

	public ActReBusinessQuery key(String key) {
		if (key == null) {
			throw new RuntimeException("key is null");
		}
		this.key = key;
		return this;
	}

	public ActReBusinessQuery keyLike(String keyLike) {
		if (keyLike == null) {
			throw new RuntimeException("key is null");
		}
		this.keyLike = keyLike;
		return this;
	}

	public ActReBusinessQuery keys(List<String> keys) {
		if (keys == null) {
			throw new RuntimeException("keys is empty ");
		}
		this.keys = keys;
		return this;
	}

	public ActReBusinessQuery url(String url) {
		if (url == null) {
			throw new RuntimeException("url is null");
		}
		this.url = url;
		return this;
	}

	public ActReBusinessQuery urlLike(String urlLike) {
		if (urlLike == null) {
			throw new RuntimeException("url is null");
		}
		this.urlLike = urlLike;
		return this;
	}

	public ActReBusinessQuery urls(List<String> urls) {
		if (urls == null) {
			throw new RuntimeException("urls is empty ");
		}
		this.urls = urls;
		return this;
	}

	public ActReBusinessQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public ActReBusinessQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public ActReBusinessQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public ActReBusinessQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public ActReBusinessQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public ActReBusinessQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public ActReBusinessQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public ActReBusinessQuery updateBys(List<String> updateBys) {
		if (updateBys == null) {
			throw new RuntimeException("updateBys is empty ");
		}
		this.updateBys = updateBys;
		return this;
	}

	public ActReBusinessQuery updateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public ActReBusinessQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("bustbName".equals(sortColumn)) {
				orderBy = "E.BUSTBNAME_" + a_x;
			}

			if ("bustbId".equals(sortColumn)) {
				orderBy = "E.BUSTBID_" + a_x;
			}

			if ("busValue".equals(sortColumn)) {
				orderBy = "E.BUSVALUE_" + a_x;
			}

			if ("processId".equals(sortColumn)) {
				orderBy = "E.PROCESSID_" + a_x;
			}

			if ("processName".equals(sortColumn)) {
				orderBy = "E.PROCESSNAME_" + a_x;
			}

			if ("pageId".equals(sortColumn)) {
				orderBy = "E.PAGEID_" + a_x;
			}

			if ("key".equals(sortColumn)) {
				orderBy = "E.KEY_" + a_x;
			}

			if ("url".equals(sortColumn)) {
				orderBy = "E.URL_" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.CREATEDATE_" + a_x;
			}

			if ("updateBy".equals(sortColumn)) {
				orderBy = "E.UPDATEBY_" + a_x;
			}

			if ("updateDate".equals(sortColumn)) {
				orderBy = "E.UPDATEDATE_" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("bustbName", "BUSTBNAME_");
		addColumn("bustbId", "BUSTBID_");
		addColumn("busValue", "BUSVALUE_");
		addColumn("processId", "PROCESSID_");
		addColumn("processName", "PROCESSNAME_");
		addColumn("pageId", "PAGEID_");
		addColumn("key", "KEY_");
		addColumn("url", "URL_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createDate", "CREATEDATE_");
		addColumn("updateBy", "UPDATEBY_");
		addColumn("updateDate", "UPDATEDATE_");
	}

}