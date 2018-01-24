package com.glaf.report.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SysReportTemplateQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected String reportTemplateName;
	protected String reportTemplateNameLike;
	protected List<String> reportTemplateNames;
	protected Date ctimeGreaterThanOrEqual;
	protected Date ctimeLessThanOrEqual;
	protected Date utimeGreaterThanOrEqual;
	protected Date utimeLessThanOrEqual;

	public SysReportTemplateQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getReportTemplateName() {
		return reportTemplateName;
	}

	public String getReportTemplateNameLike() {
		if (reportTemplateNameLike != null
				&& reportTemplateNameLike.trim().length() > 0) {
			if (!reportTemplateNameLike.startsWith("%")) {
				reportTemplateNameLike = "%" + reportTemplateNameLike;
			}
			if (!reportTemplateNameLike.endsWith("%")) {
				reportTemplateNameLike = reportTemplateNameLike + "%";
			}
		}
		return reportTemplateNameLike;
	}

	public List<String> getReportTemplateNames() {
		return reportTemplateNames;
	}

	public Date getCtimeGreaterThanOrEqual() {
		return ctimeGreaterThanOrEqual;
	}

	public Date getCtimeLessThanOrEqual() {
		return ctimeLessThanOrEqual;
	}

	public Date getUtimeGreaterThanOrEqual() {
		return utimeGreaterThanOrEqual;
	}

	public Date getUtimeLessThanOrEqual() {
		return utimeLessThanOrEqual;
	}

	public void setReportTemplateName(String reportTemplateName) {
		this.reportTemplateName = reportTemplateName;
	}

	public void setReportTemplateNameLike(String reportTemplateNameLike) {
		this.reportTemplateNameLike = reportTemplateNameLike;
	}

	public void setReportTemplateNames(List<String> reportTemplateNames) {
		this.reportTemplateNames = reportTemplateNames;
	}

	public void setCtimeGreaterThanOrEqual(Date ctimeGreaterThanOrEqual) {
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
	}

	public void setCtimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
	}

	public void setUtimeGreaterThanOrEqual(Date utimeGreaterThanOrEqual) {
		this.utimeGreaterThanOrEqual = utimeGreaterThanOrEqual;
	}

	public void setUtimeLessThanOrEqual(Date utimeLessThanOrEqual) {
		this.utimeLessThanOrEqual = utimeLessThanOrEqual;
	}

	public SysReportTemplateQuery reportTemplateName(String reportTemplateName) {
		if (reportTemplateName == null) {
			throw new RuntimeException("reportTemplateName is null");
		}
		this.reportTemplateName = reportTemplateName;
		return this;
	}

	public SysReportTemplateQuery reportTemplateNameLike(
			String reportTemplateNameLike) {
		if (reportTemplateNameLike == null) {
			throw new RuntimeException("reportTemplateName is null");
		}
		this.reportTemplateNameLike = reportTemplateNameLike;
		return this;
	}

	public SysReportTemplateQuery reportTemplateNames(
			List<String> reportTemplateNames) {
		if (reportTemplateNames == null) {
			throw new RuntimeException("reportTemplateNames is empty ");
		}
		this.reportTemplateNames = reportTemplateNames;
		return this;
	}

	public SysReportTemplateQuery ctimeGreaterThanOrEqual(
			Date ctimeGreaterThanOrEqual) {
		if (ctimeGreaterThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
		return this;
	}

	public SysReportTemplateQuery ctimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		if (ctimeLessThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
		return this;
	}

	public SysReportTemplateQuery utimeGreaterThanOrEqual(
			Date utimeGreaterThanOrEqual) {
		if (utimeGreaterThanOrEqual == null) {
			throw new RuntimeException("utime is null");
		}
		this.utimeGreaterThanOrEqual = utimeGreaterThanOrEqual;
		return this;
	}

	public SysReportTemplateQuery utimeLessThanOrEqual(Date utimeLessThanOrEqual) {
		if (utimeLessThanOrEqual == null) {
			throw new RuntimeException("utime is null");
		}
		this.utimeLessThanOrEqual = utimeLessThanOrEqual;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("reportTemplateName".equals(sortColumn)) {
				orderBy = "E.REPORT_TEMPLATE_NAME" + a_x;
			}

			if ("ctime".equals(sortColumn)) {
				orderBy = "E.CTIME" + a_x;
			}

			if ("utime".equals(sortColumn)) {
				orderBy = "E.UTIME" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("reportTemplateName", "REPORT_TEMPLATE_NAME");
		addColumn("ctime", "CTIME");
		addColumn("utime", "UTIME");
	}

}