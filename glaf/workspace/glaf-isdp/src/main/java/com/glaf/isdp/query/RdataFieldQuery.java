package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class RdataFieldQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected String tableid;
	protected String tableidLike;
	protected List<String> tableids;
	protected String fieldname;
	protected String fieldnameLike;
	protected List<String> fieldnames;
	protected String userid;
	protected String useridLike;
	protected List<String> userids;
	protected Integer maxuser;
	protected Integer maxuserGreaterThanOrEqual;
	protected Integer maxuserLessThanOrEqual;
	protected List<Integer> maxusers;
	protected Integer maxsys;
	protected Integer maxsysGreaterThanOrEqual;
	protected Integer maxsysLessThanOrEqual;
	protected List<Integer> maxsyss;
	protected Date ctimeGreaterThanOrEqual;
	protected Date ctimeLessThanOrEqual;
	protected String sysnum;
	protected String sysnumLike;
	protected List<String> sysnums;
	protected String tablename;
	protected String tablenameLike;
	protected List<String> tablenames;
	protected String dname;
	protected String dnameLike;
	protected List<String> dnames;
	protected String userindex;
	protected String userindexLike;
	protected List<String> userindexs;
	protected String treetablenameB;
	protected String treetablenameBLike;
	protected List<String> treetablenameBs;
	protected String formula;
	protected String formulaLike;
	protected List<String> formulas;
	protected String lgcexpress;
	protected String lgcexpressLike;
	protected List<String> lgcexpresss;

	public RdataFieldQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getTableid() {
		return tableid;
	}

	public String getTableidLike() {
		if (tableidLike != null && tableidLike.trim().length() > 0) {
			if (!tableidLike.startsWith("%")) {
				tableidLike = "%" + tableidLike;
			}
			if (!tableidLike.endsWith("%")) {
				tableidLike = tableidLike + "%";
			}
		}
		return tableidLike;
	}

	public List<String> getTableids() {
		return tableids;
	}

	public String getFieldname() {
		return fieldname;
	}

	public String getFieldnameLike() {
		if (fieldnameLike != null && fieldnameLike.trim().length() > 0) {
			if (!fieldnameLike.startsWith("%")) {
				fieldnameLike = "%" + fieldnameLike;
			}
			if (!fieldnameLike.endsWith("%")) {
				fieldnameLike = fieldnameLike + "%";
			}
		}
		return fieldnameLike;
	}

	public List<String> getFieldnames() {
		return fieldnames;
	}

	public String getUserid() {
		return userid;
	}

	public String getUseridLike() {
		if (useridLike != null && useridLike.trim().length() > 0) {
			if (!useridLike.startsWith("%")) {
				useridLike = "%" + useridLike;
			}
			if (!useridLike.endsWith("%")) {
				useridLike = useridLike + "%";
			}
		}
		return useridLike;
	}

	public List<String> getUserids() {
		return userids;
	}

	public Integer getMaxuser() {
		return maxuser;
	}

	public Integer getMaxuserGreaterThanOrEqual() {
		return maxuserGreaterThanOrEqual;
	}

	public Integer getMaxuserLessThanOrEqual() {
		return maxuserLessThanOrEqual;
	}

	public List<Integer> getMaxusers() {
		return maxusers;
	}

	public Integer getMaxsys() {
		return maxsys;
	}

	public Integer getMaxsysGreaterThanOrEqual() {
		return maxsysGreaterThanOrEqual;
	}

	public Integer getMaxsysLessThanOrEqual() {
		return maxsysLessThanOrEqual;
	}

	public List<Integer> getMaxsyss() {
		return maxsyss;
	}

	public Date getCtimeGreaterThanOrEqual() {
		return ctimeGreaterThanOrEqual;
	}

	public Date getCtimeLessThanOrEqual() {
		return ctimeLessThanOrEqual;
	}

	public String getSysnum() {
		return sysnum;
	}

	public String getSysnumLike() {
		if (sysnumLike != null && sysnumLike.trim().length() > 0) {
			if (!sysnumLike.startsWith("%")) {
				sysnumLike = "%" + sysnumLike;
			}
			if (!sysnumLike.endsWith("%")) {
				sysnumLike = sysnumLike + "%";
			}
		}
		return sysnumLike;
	}

	public List<String> getSysnums() {
		return sysnums;
	}

	public String getTablename() {
		return tablename;
	}

	public String getTablenameLike() {
		if (tablenameLike != null && tablenameLike.trim().length() > 0) {
			if (!tablenameLike.startsWith("%")) {
				tablenameLike = "%" + tablenameLike;
			}
			if (!tablenameLike.endsWith("%")) {
				tablenameLike = tablenameLike + "%";
			}
		}
		return tablenameLike;
	}

	public List<String> getTablenames() {
		return tablenames;
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

	public String getUserindex() {
		return userindex;
	}

	public String getUserindexLike() {
		if (userindexLike != null && userindexLike.trim().length() > 0) {
			if (!userindexLike.startsWith("%")) {
				userindexLike = "%" + userindexLike;
			}
			if (!userindexLike.endsWith("%")) {
				userindexLike = userindexLike + "%";
			}
		}
		return userindexLike;
	}

	public List<String> getUserindexs() {
		return userindexs;
	}

	public String getTreetablenameB() {
		return treetablenameB;
	}

	public String getTreetablenameBLike() {
		if (treetablenameBLike != null && treetablenameBLike.trim().length() > 0) {
			if (!treetablenameBLike.startsWith("%")) {
				treetablenameBLike = "%" + treetablenameBLike;
			}
			if (!treetablenameBLike.endsWith("%")) {
				treetablenameBLike = treetablenameBLike + "%";
			}
		}
		return treetablenameBLike;
	}

	public List<String> getTreetablenameBs() {
		return treetablenameBs;
	}

	public String getFormula() {
		return formula;
	}

	public String getFormulaLike() {
		if (formulaLike != null && formulaLike.trim().length() > 0) {
			if (!formulaLike.startsWith("%")) {
				formulaLike = "%" + formulaLike;
			}
			if (!formulaLike.endsWith("%")) {
				formulaLike = formulaLike + "%";
			}
		}
		return formulaLike;
	}

	public List<String> getFormulas() {
		return formulas;
	}

	public String getLgcexpress() {
		return lgcexpress;
	}

	public String getLgcexpressLike() {
		if (lgcexpressLike != null && lgcexpressLike.trim().length() > 0) {
			if (!lgcexpressLike.startsWith("%")) {
				lgcexpressLike = "%" + lgcexpressLike;
			}
			if (!lgcexpressLike.endsWith("%")) {
				lgcexpressLike = lgcexpressLike + "%";
			}
		}
		return lgcexpressLike;
	}

	public List<String> getLgcexpresss() {
		return lgcexpresss;
	}

	public void setTableid(String tableid) {
		this.tableid = tableid;
	}

	public void setTableidLike(String tableidLike) {
		this.tableidLike = tableidLike;
	}

	public void setTableids(List<String> tableids) {
		this.tableids = tableids;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public void setFieldnameLike(String fieldnameLike) {
		this.fieldnameLike = fieldnameLike;
	}

	public void setFieldnames(List<String> fieldnames) {
		this.fieldnames = fieldnames;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setUseridLike(String useridLike) {
		this.useridLike = useridLike;
	}

	public void setUserids(List<String> userids) {
		this.userids = userids;
	}

	public void setMaxuser(Integer maxuser) {
		this.maxuser = maxuser;
	}

	public void setMaxuserGreaterThanOrEqual(Integer maxuserGreaterThanOrEqual) {
		this.maxuserGreaterThanOrEqual = maxuserGreaterThanOrEqual;
	}

	public void setMaxuserLessThanOrEqual(Integer maxuserLessThanOrEqual) {
		this.maxuserLessThanOrEqual = maxuserLessThanOrEqual;
	}

	public void setMaxusers(List<Integer> maxusers) {
		this.maxusers = maxusers;
	}

	public void setMaxsys(Integer maxsys) {
		this.maxsys = maxsys;
	}

	public void setMaxsysGreaterThanOrEqual(Integer maxsysGreaterThanOrEqual) {
		this.maxsysGreaterThanOrEqual = maxsysGreaterThanOrEqual;
	}

	public void setMaxsysLessThanOrEqual(Integer maxsysLessThanOrEqual) {
		this.maxsysLessThanOrEqual = maxsysLessThanOrEqual;
	}

	public void setMaxsyss(List<Integer> maxsyss) {
		this.maxsyss = maxsyss;
	}

	public void setCtimeGreaterThanOrEqual(Date ctimeGreaterThanOrEqual) {
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
	}

	public void setCtimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
	}

	public void setSysnum(String sysnum) {
		this.sysnum = sysnum;
	}

	public void setSysnumLike(String sysnumLike) {
		this.sysnumLike = sysnumLike;
	}

	public void setSysnums(List<String> sysnums) {
		this.sysnums = sysnums;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public void setTablenameLike(String tablenameLike) {
		this.tablenameLike = tablenameLike;
	}

	public void setTablenames(List<String> tablenames) {
		this.tablenames = tablenames;
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

	public void setUserindex(String userindex) {
		this.userindex = userindex;
	}

	public void setUserindexLike(String userindexLike) {
		this.userindexLike = userindexLike;
	}

	public void setUserindexs(List<String> userindexs) {
		this.userindexs = userindexs;
	}

	public void setTreetablenameB(String treetablenameB) {
		this.treetablenameB = treetablenameB;
	}

	public void setTreetablenameBLike(String treetablenameBLike) {
		this.treetablenameBLike = treetablenameBLike;
	}

	public void setTreetablenameBs(List<String> treetablenameBs) {
		this.treetablenameBs = treetablenameBs;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public void setFormulaLike(String formulaLike) {
		this.formulaLike = formulaLike;
	}

	public void setFormulas(List<String> formulas) {
		this.formulas = formulas;
	}

	public void setLgcexpress(String lgcexpress) {
		this.lgcexpress = lgcexpress;
	}

	public void setLgcexpressLike(String lgcexpressLike) {
		this.lgcexpressLike = lgcexpressLike;
	}

	public void setLgcexpresss(List<String> lgcexpresss) {
		this.lgcexpresss = lgcexpresss;
	}

	public RdataFieldQuery tableid(String tableid) {
		if (tableid == null) {
			throw new RuntimeException("tableid is null");
		}
		this.tableid = tableid;
		return this;
	}

	public RdataFieldQuery tableidLike(String tableidLike) {
		if (tableidLike == null) {
			throw new RuntimeException("tableid is null");
		}
		this.tableidLike = tableidLike;
		return this;
	}

	public RdataFieldQuery tableids(List<String> tableids) {
		if (tableids == null) {
			throw new RuntimeException("tableids is empty ");
		}
		this.tableids = tableids;
		return this;
	}

	public RdataFieldQuery fieldname(String fieldname) {
		if (fieldname == null) {
			throw new RuntimeException("fieldname is null");
		}
		this.fieldname = fieldname;
		return this;
	}

	public RdataFieldQuery fieldnameLike(String fieldnameLike) {
		if (fieldnameLike == null) {
			throw new RuntimeException("fieldname is null");
		}
		this.fieldnameLike = fieldnameLike;
		return this;
	}

	public RdataFieldQuery fieldnames(List<String> fieldnames) {
		if (fieldnames == null) {
			throw new RuntimeException("fieldnames is empty ");
		}
		this.fieldnames = fieldnames;
		return this;
	}

	public RdataFieldQuery userid(String userid) {
		if (userid == null) {
			throw new RuntimeException("userid is null");
		}
		this.userid = userid;
		return this;
	}

	public RdataFieldQuery useridLike(String useridLike) {
		if (useridLike == null) {
			throw new RuntimeException("userid is null");
		}
		this.useridLike = useridLike;
		return this;
	}

	public RdataFieldQuery userids(List<String> userids) {
		if (userids == null) {
			throw new RuntimeException("userids is empty ");
		}
		this.userids = userids;
		return this;
	}

	public RdataFieldQuery maxuser(Integer maxuser) {
		if (maxuser == null) {
			throw new RuntimeException("maxuser is null");
		}
		this.maxuser = maxuser;
		return this;
	}

	public RdataFieldQuery maxuserGreaterThanOrEqual(Integer maxuserGreaterThanOrEqual) {
		if (maxuserGreaterThanOrEqual == null) {
			throw new RuntimeException("maxuser is null");
		}
		this.maxuserGreaterThanOrEqual = maxuserGreaterThanOrEqual;
		return this;
	}

	public RdataFieldQuery maxuserLessThanOrEqual(Integer maxuserLessThanOrEqual) {
		if (maxuserLessThanOrEqual == null) {
			throw new RuntimeException("maxuser is null");
		}
		this.maxuserLessThanOrEqual = maxuserLessThanOrEqual;
		return this;
	}

	public RdataFieldQuery maxusers(List<Integer> maxusers) {
		if (maxusers == null) {
			throw new RuntimeException("maxusers is empty ");
		}
		this.maxusers = maxusers;
		return this;
	}

	public RdataFieldQuery maxsys(Integer maxsys) {
		if (maxsys == null) {
			throw new RuntimeException("maxsys is null");
		}
		this.maxsys = maxsys;
		return this;
	}

	public RdataFieldQuery maxsysGreaterThanOrEqual(Integer maxsysGreaterThanOrEqual) {
		if (maxsysGreaterThanOrEqual == null) {
			throw new RuntimeException("maxsys is null");
		}
		this.maxsysGreaterThanOrEqual = maxsysGreaterThanOrEqual;
		return this;
	}

	public RdataFieldQuery maxsysLessThanOrEqual(Integer maxsysLessThanOrEqual) {
		if (maxsysLessThanOrEqual == null) {
			throw new RuntimeException("maxsys is null");
		}
		this.maxsysLessThanOrEqual = maxsysLessThanOrEqual;
		return this;
	}

	public RdataFieldQuery maxsyss(List<Integer> maxsyss) {
		if (maxsyss == null) {
			throw new RuntimeException("maxsyss is empty ");
		}
		this.maxsyss = maxsyss;
		return this;
	}

	public RdataFieldQuery ctimeGreaterThanOrEqual(Date ctimeGreaterThanOrEqual) {
		if (ctimeGreaterThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeGreaterThanOrEqual = ctimeGreaterThanOrEqual;
		return this;
	}

	public RdataFieldQuery ctimeLessThanOrEqual(Date ctimeLessThanOrEqual) {
		if (ctimeLessThanOrEqual == null) {
			throw new RuntimeException("ctime is null");
		}
		this.ctimeLessThanOrEqual = ctimeLessThanOrEqual;
		return this;
	}

	public RdataFieldQuery sysnum(String sysnum) {
		if (sysnum == null) {
			throw new RuntimeException("sysnum is null");
		}
		this.sysnum = sysnum;
		return this;
	}

	public RdataFieldQuery sysnumLike(String sysnumLike) {
		if (sysnumLike == null) {
			throw new RuntimeException("sysnum is null");
		}
		this.sysnumLike = sysnumLike;
		return this;
	}

	public RdataFieldQuery sysnums(List<String> sysnums) {
		if (sysnums == null) {
			throw new RuntimeException("sysnums is empty ");
		}
		this.sysnums = sysnums;
		return this;
	}

	public RdataFieldQuery tablename(String tablename) {
		if (tablename == null) {
			throw new RuntimeException("tablename is null");
		}
		this.tablename = tablename;
		return this;
	}

	public RdataFieldQuery tablenameLike(String tablenameLike) {
		if (tablenameLike == null) {
			throw new RuntimeException("tablename is null");
		}
		this.tablenameLike = tablenameLike;
		return this;
	}

	public RdataFieldQuery tablenames(List<String> tablenames) {
		if (tablenames == null) {
			throw new RuntimeException("tablenames is empty ");
		}
		this.tablenames = tablenames;
		return this;
	}

	public RdataFieldQuery dname(String dname) {
		if (dname == null) {
			throw new RuntimeException("dname is null");
		}
		this.dname = dname;
		return this;
	}

	public RdataFieldQuery dnameLike(String dnameLike) {
		if (dnameLike == null) {
			throw new RuntimeException("dname is null");
		}
		this.dnameLike = dnameLike;
		return this;
	}

	public RdataFieldQuery dnames(List<String> dnames) {
		if (dnames == null) {
			throw new RuntimeException("dnames is empty ");
		}
		this.dnames = dnames;
		return this;
	}

	public RdataFieldQuery userindex(String userindex) {
		if (userindex == null) {
			throw new RuntimeException("userindex is null");
		}
		this.userindex = userindex;
		return this;
	}

	public RdataFieldQuery userindexLike(String userindexLike) {
		if (userindexLike == null) {
			throw new RuntimeException("userindex is null");
		}
		this.userindexLike = userindexLike;
		return this;
	}

	public RdataFieldQuery userindexs(List<String> userindexs) {
		if (userindexs == null) {
			throw new RuntimeException("userindexs is empty ");
		}
		this.userindexs = userindexs;
		return this;
	}

	public RdataFieldQuery treetablenameB(String treetablenameB) {
		if (treetablenameB == null) {
			throw new RuntimeException("treetablenameB is null");
		}
		this.treetablenameB = treetablenameB;
		return this;
	}

	public RdataFieldQuery treetablenameBLike(String treetablenameBLike) {
		if (treetablenameBLike == null) {
			throw new RuntimeException("treetablenameB is null");
		}
		this.treetablenameBLike = treetablenameBLike;
		return this;
	}

	public RdataFieldQuery treetablenameBs(List<String> treetablenameBs) {
		if (treetablenameBs == null) {
			throw new RuntimeException("treetablenameBs is empty ");
		}
		this.treetablenameBs = treetablenameBs;
		return this;
	}

	public RdataFieldQuery formula(String formula) {
		if (formula == null) {
			throw new RuntimeException("formula is null");
		}
		this.formula = formula;
		return this;
	}

	public RdataFieldQuery formulaLike(String formulaLike) {
		if (formulaLike == null) {
			throw new RuntimeException("formula is null");
		}
		this.formulaLike = formulaLike;
		return this;
	}

	public RdataFieldQuery formulas(List<String> formulas) {
		if (formulas == null) {
			throw new RuntimeException("formulas is empty ");
		}
		this.formulas = formulas;
		return this;
	}

	public RdataFieldQuery lgcexpress(String lgcexpress) {
		if (lgcexpress == null) {
			throw new RuntimeException("lgcexpress is null");
		}
		this.lgcexpress = lgcexpress;
		return this;
	}

	public RdataFieldQuery lgcexpressLike(String lgcexpressLike) {
		if (lgcexpressLike == null) {
			throw new RuntimeException("lgcexpress is null");
		}
		this.lgcexpressLike = lgcexpressLike;
		return this;
	}

	public RdataFieldQuery lgcexpresss(List<String> lgcexpresss) {
		if (lgcexpresss == null) {
			throw new RuntimeException("lgcexpresss is empty ");
		}
		this.lgcexpresss = lgcexpresss;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("tableid".equals(sortColumn)) {
				orderBy = "E.TABLEID" + a_x;
			}

			if ("fieldname".equals(sortColumn)) {
				orderBy = "E.FIELDNAME" + a_x;
			}

			if ("userid".equals(sortColumn)) {
				orderBy = "E.USERID" + a_x;
			}

			if ("maxuser".equals(sortColumn)) {
				orderBy = "E.MAXUSER" + a_x;
			}

			if ("maxsys".equals(sortColumn)) {
				orderBy = "E.MAXSYS" + a_x;
			}

			if ("ctime".equals(sortColumn)) {
				orderBy = "E.CTIME" + a_x;
			}

			if ("sysnum".equals(sortColumn)) {
				orderBy = "E.SYSNUM" + a_x;
			}

			if ("tablename".equals(sortColumn)) {
				orderBy = "E.TABLENAME" + a_x;
			}

			if ("dname".equals(sortColumn)) {
				orderBy = "E.DNAME" + a_x;
			}

			if ("userindex".equals(sortColumn)) {
				orderBy = "E.USERINDEX" + a_x;
			}

			if ("treetablenameB".equals(sortColumn)) {
				orderBy = "E.TREETABLENAME_B" + a_x;
			}

			if ("formula".equals(sortColumn)) {
				orderBy = "E.FORMULA" + a_x;
			}

			if ("lgcexpress".equals(sortColumn)) {
				orderBy = "E.LGCEXPRESS" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("tableid", "TABLEID");
		addColumn("fieldname", "FIELDNAME");
		addColumn("userid", "USERID");
		addColumn("maxuser", "MAXUSER");
		addColumn("maxsys", "MAXSYS");
		addColumn("ctime", "CTIME");
		addColumn("sysnum", "SYSNUM");
		addColumn("tablename", "TABLENAME");
		addColumn("dname", "DNAME");
		addColumn("userindex", "USERINDEX");
		addColumn("treetablenameB", "TREETABLENAME_B");
		addColumn("formula", "FORMULA");
		addColumn("lgcexpress", "LGCEXPRESS");
	}

}