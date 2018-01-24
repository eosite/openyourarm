package com.glaf.form.core.query;
 
import com.glaf.core.query.DataQuery;

public class PageUrlConversionQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String srcUrlLike;
	protected String destUrlLike;

	public PageUrlConversionQuery() {

	}

	public PageUrlConversionQuery destUrlLike(String destUrlLike) {
		if (destUrlLike == null) {
			throw new RuntimeException("destUrl is null");
		}
		this.destUrlLike = destUrlLike;
		return this;
	}

	public String getDestUrlLike() {
		if (destUrlLike != null && destUrlLike.trim().length() > 0) {
			if (!destUrlLike.startsWith("%")) {
				destUrlLike = "%" + destUrlLike;
			}
			if (!destUrlLike.endsWith("%")) {
				destUrlLike = destUrlLike + "%";
			}
		}
		return destUrlLike;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("srcUrl".equals(sortColumn)) {
				orderBy = "E.SRCURL_" + a_x;
			}

			if ("destUrl".equals(sortColumn)) {
				orderBy = "E.DESTURL_" + a_x;
			}

			if ("locked".equals(sortColumn)) {
				orderBy = "E.LOCKED_" + a_x;
			}

		}
		return orderBy;
	}

	public String getSrcUrlLike() {
		if (srcUrlLike != null && srcUrlLike.trim().length() > 0) {
			if (!srcUrlLike.startsWith("%")) {
				srcUrlLike = "%" + srcUrlLike;
			}
			if (!srcUrlLike.endsWith("%")) {
				srcUrlLike = srcUrlLike + "%";
			}
		}
		return srcUrlLike;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("srcUrl", "SRCURL_");
		addColumn("destUrl", "DESTURL_");
		addColumn("locked", "LOCKED_");
	}

	public PageUrlConversionQuery locked(Integer locked) {
		if (locked == null) {
			throw new RuntimeException("locked is null");
		}
		this.locked = locked;
		return this;
	}

	public void setDestUrlLike(String destUrlLike) {
		this.destUrlLike = destUrlLike;
	}

	public void setSrcUrlLike(String srcUrlLike) {
		this.srcUrlLike = srcUrlLike;
	}

	public PageUrlConversionQuery srcUrlLike(String srcUrlLike) {
		if (srcUrlLike == null) {
			throw new RuntimeException("srcUrl is null");
		}
		this.srcUrlLike = srcUrlLike;
		return this;
	}

}